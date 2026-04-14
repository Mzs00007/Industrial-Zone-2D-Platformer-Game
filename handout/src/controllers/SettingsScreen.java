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
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import vfx.SparkEffectSystem;

public class SettingsScreen
extends Screen {
    private MidiTuner backgroundMusic;
    private SparkEffectSystem sparkSystem;
    private AnimationAndSpriteLoader animLoader;
    private Map<String, InteractiveButton> buttons;
    private Map<String, SettingToggle> settingToggles;
    private static final String[] TOGGLE_NAMES = new String[]{"Background Music", "Sound Effects", "Screen Shake", "Fullscreen"};
    private static final int[] BUTTON_VARIANTS = new int[]{3, 4, 5, 6};
    private static final int[] SPARK_TYPES = new int[]{3, 4, 2, 1};
    private boolean[] toggleStates = new boolean[]{true, true, true, false};
    private int centerX = this.screenWidth / 2;
    private int centerY = this.screenHeight / 2;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 60;
    private static final int BUTTON_SPACING = 100;
    private static final int BACK_BUTTON_MARGIN = 50;

    public SettingsScreen() {
        super(GameState.SETTINGS);
        this.buttons = new LinkedHashMap<String, InteractiveButton>();
        this.settingToggles = new LinkedHashMap<String, SettingToggle>();
        this.initializeAssets();
        this.createToggles();
        this.createBackButton();
        this.startBackgroundMusic();
        System.out.println("[SettingsScreen] \u2713 Initialized");
    }

    private void initializeAssets() {
        try {
            this.animLoader = new AnimationAndSpriteLoader();
            this.sparkSystem = new SparkEffectSystem(this.animLoader);
            this.backgroundMusic = new MidiTuner("SettingsTheme.mid", -1);
            System.out.println("[SettingsScreen] \u2713 Assets initialized");
        }
        catch (Exception exception) {
            System.out.println("[SettingsScreen] \u2717 Asset initialization error: " + exception.getMessage());
        }
    }

    private void createToggles() {
        int n = this.centerY - (TOGGLE_NAMES.length - 1) * 100 / 2;
        for (int i = 0; i < TOGGLE_NAMES.length; ++i) {
            String string = TOGGLE_NAMES[i];
            int n2 = BUTTON_VARIANTS[i];
            int n3 = SPARK_TYPES[i];
            int n4 = n + i * 100;
            int n5 = this.centerX - 100;
            try {
                String string2 = String.format("Resources/industrial-zone/gui/6 Buttons/Variant_%02d Button.png", n2);
                AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader("Button_" + string, string2, 200, 60, 4);
                InteractiveButton interactiveButton = new InteractiveButton(string, horizontalSpritesheetLoader, n5, n4, 200, 60);
                MidiTuner midiTuner = new MidiTuner("click_toggle.wav", 1);
                interactiveButton.setAudio(midiTuner);
                interactiveButton.setSparkEffect(this.sparkSystem, n3);
                int n6 = i;
                interactiveButton.setOnClick(() -> {
                    this.toggleStates[n] = !this.toggleStates[n6];
                    System.out.println("[SettingsScreen] Toggled " + string + ": " + this.toggleStates[n6]);
                });
                this.buttons.put(string, interactiveButton);
                this.settingToggles.put(string, new SettingToggle(string, this.toggleStates[i]));
                System.out.println("[SettingsScreen] \u2713 Created toggle: " + string);
                continue;
            }
            catch (Exception exception) {
                System.out.println("[SettingsScreen] \u2717 Failed to create toggle " + string + ": " + exception.getMessage());
            }
        }
    }

    private void createBackButton() {
        try {
            int n = 50;
            int n2 = this.screenHeight - 50 - 60;
            String string = "Resources/industrial-zone/gui/6 Buttons/Variant_07 Button.png";
            AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader("Button_Back", string, 200, 60, 4);
            InteractiveButton interactiveButton = new InteractiveButton("Back", horizontalSpritesheetLoader, n, n2, 200, 60);
            MidiTuner midiTuner = new MidiTuner("click_back.wav", 1);
            interactiveButton.setAudio(midiTuner);
            interactiveButton.setSparkEffect(this.sparkSystem, 6);
            interactiveButton.setOnClick(() -> this.onBackButtonClicked());
            this.buttons.put("Back", interactiveButton);
            System.out.println("[SettingsScreen] \u2713 Created back button");
        }
        catch (Exception exception) {
            System.out.println("[SettingsScreen] \u2717 Failed to create back button: " + exception.getMessage());
        }
    }

    private void startBackgroundMusic() {
        if (this.backgroundMusic != null) {
            this.backgroundMusic.setVolume(0.5f);
            this.backgroundMusic.play();
            System.out.println("[SettingsScreen] \u25b6 Background music started");
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
        BufferedImage bufferedImage = new BufferedImage(n, n2, 1);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            graphics2D.setColor(new Color(30, 30, 40));
            graphics2D.fillRect(0, 0, n, n2);
            this.drawTitle(graphics2D, n, n2);
            for (InteractiveButton object : this.buttons.values()) {
                object.render(graphics2D);
            }
            this.drawToggleStatus(graphics2D);
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
        String string = "SETTINGS";
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n3 = (n - fontMetrics.stringWidth(string)) / 2;
        int n4 = n2 / 6;
        graphics2D.setColor(new Color(0, 0, 0, 180));
        graphics2D.drawString(string, n3 + 2, n4 + 2);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(string, n3, n4);
    }

    private void drawToggleStatus(Graphics2D graphics2D) {
        graphics2D.setFont(new Font("Arial", 1, 16));
        int n = this.centerY - (TOGGLE_NAMES.length - 1) * 100 / 2;
        for (int i = 0; i < TOGGLE_NAMES.length; ++i) {
            int n2 = n + i * 100;
            int n3 = this.centerX + 100 + 50;
            int n4 = n2 + 30 + 5;
            String string = this.toggleStates[i] ? "ON" : "OFF";
            Color color = this.toggleStates[i] ? new Color(100, 255, 100) : new Color(255, 100, 100);
            graphics2D.setColor(color);
            graphics2D.drawString(string, n3, n4);
        }
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

    private void onBackButtonClicked() {
        System.out.println("[SettingsScreen] Back button clicked");
        if (this.backgroundMusic != null) {
            this.backgroundMusic.fadeOut(300);
        }
        if (this.stateListener != null) {
            this.stateListener.onStateTransition(GameState.MAIN_MENU);
        }
    }

    public Map<String, Boolean> getSettings() {
        HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();
        for (int i = 0; i < TOGGLE_NAMES.length; ++i) {
            hashMap.put(TOGGLE_NAMES[i], this.toggleStates[i]);
        }
        return hashMap;
    }

    public void close() {
        if (this.backgroundMusic != null) {
            this.backgroundMusic.close();
        }
        if (this.sparkSystem != null) {
            this.sparkSystem.close();
        }
        System.out.println("[SettingsScreen] Closed");
    }

    private static class SettingToggle {
        String settingName;
        boolean isEnabled;

        public SettingToggle(String string, boolean bl) {
            this.settingName = string;
            this.isEnabled = bl;
        }

        public String toString() {
            return this.settingName + ": " + (this.isEnabled ? "ON" : "OFF");
        }
    }
}
