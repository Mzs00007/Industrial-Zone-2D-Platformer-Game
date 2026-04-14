/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import animation.AnimationAndSpriteLoader;
import utilities.MidiTuner;
import managers.GameState;
import controllers.InteractiveButton;
import controllers.Screen;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;
import vfx.SparkEffectSystem;

public class MenuScreen
extends Screen {
    private MidiTuner backgroundMusic;
    private SparkEffectSystem sparkSystem;
    private AnimationAndSpriteLoader animLoader;
    private Map<String, InteractiveButton> buttons;
    private String selectedButtonName = null;
    private static final String[] BUTTON_NAMES = new String[]{"Play", "Settings", "Controls", "Credits", "Exit"};
    private static final int[] BUTTON_VARIANTS = new int[]{1, 5, 3, 2, 8};
    private static final int[] SPARK_TYPES = new int[]{5, 2, 7, 3, 4};
    private int centerX = this.screenWidth / 2;
    private int centerY = this.screenHeight / 2;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 60;
    private static final int BUTTON_SPACING = 80;
    private Image backgroundImage = null;
    private static final String BACKGROUND_PATH = "Resources/industrial-zone/backgrounds/menu_bg.png";

    public MenuScreen() {
        super(GameState.MAIN_MENU);
        this.initializeAssets();
        this.createButtons();
        this.startBackgroundMusic();
        System.out.println("[MenuScreen] \u2713 Initialized");
    }

    private void initializeAssets() {
        try {
            this.animLoader = new AnimationAndSpriteLoader();
            this.sparkSystem = new SparkEffectSystem(this.animLoader);
            this.backgroundMusic = new MidiTuner("MenuTheme.mid", -1);
            System.out.println("[MenuScreen] \u2713 Assets initialized");
        }
        catch (Exception exception) {
            System.out.println("[MenuScreen] \u2717 Asset initialization error: " + exception.getMessage());
        }
    }

    private void createButtons() {
        this.buttons = new LinkedHashMap<String, InteractiveButton>();
        int n = this.centerY - (BUTTON_NAMES.length - 1) * 80 / 2;
        for (int i = 0; i < BUTTON_NAMES.length; ++i) {
            String string = BUTTON_NAMES[i];
            int n2 = BUTTON_VARIANTS[i];
            int n3 = SPARK_TYPES[i];
            int n4 = n + i * 80;
            int n5 = this.centerX - 100;
            try {
                String string2 = String.format("Resources/industrial-zone/gui/6 Buttons/Variant_%02d Button.png", n2);
                AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader("Button_" + string, string2, 200, 60, 4);
                InteractiveButton interactiveButton = new InteractiveButton(string, horizontalSpritesheetLoader, n5, n4, 200, 60);
                MidiTuner midiTuner = new MidiTuner("click_" + string.toLowerCase() + ".wav", 1);
                interactiveButton.setAudio(midiTuner);
                interactiveButton.setSparkEffect(this.sparkSystem, n3);
                switch (string) {
                    case "Play": {
                        interactiveButton.setOnClick(() -> this.onPlayButtonClicked());
                        break;
                    }
                    case "Settings": {
                        interactiveButton.setOnClick(() -> this.onSettingsButtonClicked());
                        break;
                    }
                    case "Controls": {
                        interactiveButton.setOnClick(() -> this.onControlsButtonClicked());
                        break;
                    }
                    case "Credits": {
                        interactiveButton.setOnClick(() -> this.onCreditsButtonClicked());
                        break;
                    }
                    case "Exit": {
                        interactiveButton.setOnClick(() -> this.onExitButtonClicked());
                    }
                }
                this.buttons.put(string, interactiveButton);
                System.out.println("[MenuScreen] \u2713 Created button: " + string + " (Variant " + n2 + ")");
                continue;
            }
            catch (Exception exception) {
                System.out.println("[MenuScreen] \u2717 Failed to create button " + string + ": " + exception.getMessage());
            }
        }
    }

    private void startBackgroundMusic() {
        if (this.backgroundMusic != null) {
            this.backgroundMusic.setVolume(0.6f);
            this.backgroundMusic.play();
            System.out.println("[MenuScreen] \u25b6 Background music started");
        }
    }

    @Override
    public void update(float f) {
        for (InteractiveButton interactiveButton : this.buttons.values()) {
            interactiveButton.update();
        }
    }

    @Override
    protected void onDimensionsChanged(int n, int n2) {
        this.centerX = n / 2;
        this.centerY = n2 / 2;
        int n3 = this.centerY - (BUTTON_NAMES.length - 1) * 80 / 2;
        int n4 = 0;
        for (InteractiveButton interactiveButton : this.buttons.values()) {
            int n5 = n3 + n4 * 80;
            int n6 = this.centerX - 100;
            ++n4;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public BufferedImage render(int n, int n2) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 1);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            graphics2D.setColor(new Color(30, 30, 40));
            graphics2D.fillRect(0, 0, n, n2);
            this.drawTitle(graphics2D, n, n2);
            for (InteractiveButton object : this.buttons.values()) {
                object.render(graphics2D);
            }
            if (this.sparkSystem != null) {
                for (SparkEffectSystem.ActiveSparkEffect activeSparkEffect : this.sparkSystem.getActiveEffects()) {
                    try {
                        BufferedImage bufferedImage2 = activeSparkEffect.getLoader().getFrame(activeSparkEffect.getCurrentFrame());
                        if (bufferedImage2 == null) continue;
                        graphics2D.drawImage(bufferedImage2, activeSparkEffect.getScreenX() - 32, activeSparkEffect.getScreenY() - 32, 64, 64, null);
                    }
                    catch (Exception exception) {}
                }
            }
        }
        finally {
            graphics2D.dispose();
        }
        return bufferedImage;
    }

    private void drawTitle(Graphics2D graphics2D, int n, int n2) {
        graphics2D.setFont(new Font("Arial", 1, 48));
        String string = "MAIN MENU";
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n3 = (n - fontMetrics.stringWidth(string)) / 2;
        int n4 = n2 / 4;
        graphics2D.setColor(new Color(0, 0, 0, 180));
        graphics2D.drawString(string, n3 + 2, n4 + 2);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(string, n3, n4);
    }

    @Override
    public void handleMouseMoved(MouseEvent mouseEvent) {
        super.handleMouseMoved(mouseEvent);
        int n = mouseEvent.getX();
        int n2 = mouseEvent.getY();
        for (InteractiveButton interactiveButton : this.buttons.values()) {
            interactiveButton.handleMouseMove(n, n2);
        }
    }

    @Override
    public void handleMouseClicked(MouseEvent mouseEvent) {
        super.handleMouseClicked(mouseEvent);
        int n = mouseEvent.getX();
        int n2 = mouseEvent.getY();
        for (InteractiveButton interactiveButton : this.buttons.values()) {
            interactiveButton.handleMouseClick(n, n2);
        }
    }

    private void onPlayButtonClicked() {
        System.out.println("[MenuScreen] Play button clicked - starting character select");
        this.fadeOutAndTransition(GameState.CHARACTER_SELECT);
    }

    private void onSettingsButtonClicked() {
        System.out.println("[MenuScreen] Settings button clicked");
        this.fadeOutAndTransition(GameState.SETTINGS);
    }

    private void onControlsButtonClicked() {
        System.out.println("[MenuScreen] Controls button clicked");
        this.fadeOutAndTransition(GameState.HOW_TO_PLAY);
    }

    private void onCreditsButtonClicked() {
        System.out.println("[MenuScreen] Credits button clicked");
    }

    private void onExitButtonClicked() {
        System.out.println("[MenuScreen] Exit button clicked - quitting game");
        System.exit(0);
    }

    private void fadeOutAndTransition(GameState gameState) {
        if (this.backgroundMusic != null) {
            this.backgroundMusic.fadeOut(500);
        }
        if (this.stateListener != null) {
            this.stateListener.onStateTransition(gameState);
        }
    }

    public void close() {
        if (this.backgroundMusic != null) {
            this.backgroundMusic.close();
        }
        if (this.sparkSystem != null) {
            this.sparkSystem.close();
        }
        System.out.println("[MenuScreen] Closed");
    }
}
