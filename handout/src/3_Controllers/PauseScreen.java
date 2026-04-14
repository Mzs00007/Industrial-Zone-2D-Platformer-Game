/*
 * Decompiled with CFR 0.152.
 */
package gui;

import animation.AnimationAndSpriteLoader;
import audio.MidiTuner;
import core.GameState;
import gui.InteractiveButton;
import gui.Screen;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;
import vfx.SparkEffectSystem;

public class PauseScreen
extends Screen {
    private MidiTuner pauseMusic;
    private SparkEffectSystem sparkSystem;
    private AnimationAndSpriteLoader animLoader;
    private Map<String, InteractiveButton> buttons;
    private static final String[] BUTTON_NAMES = new String[]{"Resume", "Quit"};
    private static final int[] BUTTON_VARIANTS = new int[]{2, 8};
    private static final int[] SPARK_TYPES = new int[]{2, 4};
    private int centerX = this.screenWidth / 2;
    private int centerY = this.screenHeight / 2;
    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 70;
    private static final int BUTTON_SPACING = 100;
    private float overlayAlpha = 0.7f;

    public PauseScreen() {
        super(GameState.PAUSED);
        this.initializeAssets();
        this.createButtons();
        System.out.println("[PauseScreen] \u2713 Initialized");
    }

    private void initializeAssets() {
        try {
            this.animLoader = new AnimationAndSpriteLoader();
            this.sparkSystem = new SparkEffectSystem(this.animLoader);
            this.pauseMusic = new MidiTuner("PauseTheme.mid", -1);
            System.out.println("[PauseScreen] \u2713 Assets initialized");
        }
        catch (Exception exception) {
            System.out.println("[PauseScreen] \u2717 Asset initialization error: " + exception.getMessage());
        }
    }

    private void createButtons() {
        this.buttons = new LinkedHashMap<String, InteractiveButton>();
        int n = this.centerY - 50;
        for (int i = 0; i < BUTTON_NAMES.length; ++i) {
            String string = BUTTON_NAMES[i];
            int n2 = BUTTON_VARIANTS[i];
            int n3 = SPARK_TYPES[i];
            int n4 = n + i * 100;
            int n5 = this.centerX - 125;
            try {
                String string2 = String.format("Resources/industrial-zone/gui/6 Buttons/Variant_%02d Button.png", n2);
                AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader("Button_" + string, string2, 250, 70, 4);
                InteractiveButton interactiveButton = new InteractiveButton(string, horizontalSpritesheetLoader, n5, n4, 250, 70);
                MidiTuner midiTuner = new MidiTuner("click_" + string.toLowerCase() + ".wav", 1);
                interactiveButton.setAudio(midiTuner);
                interactiveButton.setSparkEffect(this.sparkSystem, n3);
                switch (string) {
                    case "Resume": {
                        interactiveButton.setOnClick(() -> this.onResumeButtonClicked());
                        break;
                    }
                    case "Quit": {
                        interactiveButton.setOnClick(() -> this.onQuitButtonClicked());
                    }
                }
                this.buttons.put(string, interactiveButton);
                System.out.println("[PauseScreen] \u2713 Created button: " + string);
                continue;
            }
            catch (Exception exception) {
                System.out.println("[PauseScreen] \u2717 Failed to create button " + string + ": " + exception.getMessage());
            }
        }
    }

    @Override
    public void update(float f) {
        for (InteractiveButton interactiveButton : this.buttons.values()) {
            interactiveButton.update();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public BufferedImage render(int n, int n2) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 2);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            graphics2D.setColor(new Color(0, 0, 0, (int)(this.overlayAlpha * 255.0f)));
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
        graphics2D.setFont(new Font("Arial", 1, 56));
        String string = "GAME PAUSED";
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n3 = (n - fontMetrics.stringWidth(string)) / 2;
        int n4 = this.centerY - 150;
        graphics2D.setColor(new Color(0, 0, 0, 200));
        graphics2D.drawString(string, n3 + 2, n4 + 2);
        graphics2D.setColor(new Color(255, 200, 0));
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

    @Override
    public void handleKeyPressed(KeyEvent keyEvent) {
        super.handleKeyPressed(keyEvent);
        if (keyEvent.getKeyCode() == 27) {
            this.onResumeButtonClicked();
        }
    }

    private void onResumeButtonClicked() {
        System.out.println("[PauseScreen] Resume clicked - returning to gameplay");
        if (this.pauseMusic != null) {
            this.pauseMusic.stop();
        }
        if (this.stateListener != null) {
            this.stateListener.onStateTransition(GameState.PLAYING);
        }
    }

    private void onQuitButtonClicked() {
        System.out.println("[PauseScreen] Quit clicked - returning to main menu");
        if (this.pauseMusic != null) {
            this.pauseMusic.stop();
        }
        if (this.stateListener != null) {
            this.stateListener.onStateTransition(GameState.MAIN_MENU);
        }
    }

    public void close() {
        if (this.pauseMusic != null) {
            this.pauseMusic.close();
        }
        if (this.sparkSystem != null) {
            this.sparkSystem.close();
        }
        System.out.println("[PauseScreen] Closed");
    }
}
