/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.screens.Screen;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Phase15SettingsScreen
extends Screen {
    private static final int TAB_WIDTH = 120;
    private static final int SLIDER_WIDTH = 200;
    private static final int SLIDER_HEIGHT = 20;
    private static final int SETTING_SPACING = 40;
    private List<SettingTab> tabs = new ArrayList<SettingTab>();
    private int selectedTabIndex = 0;
    private String masterVolume = "80";
    private String musicVolume = "70";
    private String sfxVolume = "80";
    private String graphicsQuality = "High";
    private String difficulty = "Normal";
    private boolean vsyncEnabled = true;
    private boolean fullscreenEnabled = false;
    private boolean isDisplayed = false;
    private int selectedSettingIndex = 0;

    public Phase15SettingsScreen() {
        super(null);
        this.initializeSettings();
    }

    private void initializeSettings() {
        SettingTab settingTab = new SettingTab("AUDIO", "Audio Settings");
        Setting setting = new Setting("Master Volume", "80", SettingType.SLIDER);
        setting.minValue = 0;
        setting.maxValue = 100;
        settingTab.settings.add(setting);
        Setting setting2 = new Setting("Music Volume", "70", SettingType.SLIDER);
        setting2.minValue = 0;
        setting2.maxValue = 100;
        settingTab.settings.add(setting2);
        Setting setting3 = new Setting("SFX Volume", "80", SettingType.SLIDER);
        setting3.minValue = 0;
        setting3.maxValue = 100;
        settingTab.settings.add(setting3);
        settingTab.settings.add(new Setting("Subtitles", "On", SettingType.TOGGLE));
        this.tabs.add(settingTab);
        SettingTab settingTab2 = new SettingTab("VIDEO", "Video Settings");
        Setting setting4 = new Setting("Graphics Quality", "High", SettingType.DROPDOWN);
        setting4.options = new ArrayList<String>();
        setting4.options.add("Low");
        setting4.options.add("Medium");
        setting4.options.add("High");
        setting4.options.add("Ultra");
        settingTab2.settings.add(setting4);
        settingTab2.settings.add(new Setting("VSync", "On", SettingType.TOGGLE));
        settingTab2.settings.add(new Setting("Fullscreen", "Off", SettingType.TOGGLE));
        Setting setting5 = new Setting("Field of View", "90", SettingType.SLIDER);
        setting5.minValue = 60;
        setting5.maxValue = 120;
        settingTab2.settings.add(setting5);
        this.tabs.add(settingTab2);
        SettingTab settingTab3 = new SettingTab("GAMEPLAY", "Gameplay Settings");
        Setting setting6 = new Setting("Difficulty", "Normal", SettingType.DROPDOWN);
        setting6.options = new ArrayList<String>();
        setting6.options.add("Easy");
        setting6.options.add("Normal");
        setting6.options.add("Hard");
        setting6.options.add("Nightmare");
        settingTab3.settings.add(setting6);
        settingTab3.settings.add(new Setting("Show Tutorials", "On", SettingType.TOGGLE));
        settingTab3.settings.add(new Setting("Animation Quality", "High", SettingType.DROPDOWN));
        settingTab3.settings.add(new Setting("Autosave", "On", SettingType.TOGGLE));
        this.tabs.add(settingTab3);
    }

    @Override
    public void update(float f) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public BufferedImage render(int n, int n2) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 2);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            graphics2D.setColor(new Color(20, 20, 35, 240));
            graphics2D.fillRect(0, 0, n, n2);
            this.renderSettingsHeader(graphics2D, n);
            int n3 = 60;
            this.renderTabs(graphics2D, n, n3);
            int n4 = n3 + 50;
            this.renderTabContent(graphics2D, n, n4, n2);
            this.renderFooter(graphics2D, n, n2);
        }
        finally {
            graphics2D.dispose();
        }
        return bufferedImage;
    }

    private void renderSettingsHeader(Graphics2D graphics2D, int n) {
        graphics2D.setColor(new Color(60, 100, 140, 200));
        graphics2D.fillRect(0, 0, n, 50);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 28));
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        String string = "SETTINGS";
        int n2 = (n - fontMetrics.stringWidth(string)) / 2;
        graphics2D.drawString(string, n2, 38);
    }

    private void renderTabs(Graphics2D graphics2D, int n, int n2) {
        int n3 = (n - this.tabs.size() * 120) / 2;
        for (int i = 0; i < this.tabs.size(); ++i) {
            boolean bl = i == this.selectedTabIndex;
            int n4 = n3 + i * 120;
            graphics2D.setColor(bl ? new Color(100, 150, 200, 200) : new Color(60, 90, 130, 150));
            graphics2D.fillRoundRect(n4, n2, 115, 40, 8, 8);
            Color color = bl ? new Color(150, 200, 255, 255) : new Color(80, 120, 160, 200);
            graphics2D.setColor(color);
            graphics2D.setStroke(new BasicStroke(2.0f));
            graphics2D.drawRoundRect(n4, n2, 115, 40, 8, 8);
            graphics2D.setColor(bl ? Color.WHITE : new Color(150, 180, 220));
            graphics2D.setFont(new Font("Arial", bl ? 1 : 0, 12));
            FontMetrics fontMetrics = graphics2D.getFontMetrics();
            String string = this.tabs.get((int)i).name;
            int n5 = n4 + (115 - fontMetrics.stringWidth(string)) / 2;
            int n6 = n2 + (40 - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
            graphics2D.drawString(string, n5, n6);
        }
    }

    private void renderTabContent(Graphics2D graphics2D, int n, int n2, int n3) {
        if (this.selectedTabIndex >= 0 && this.selectedTabIndex < this.tabs.size()) {
            SettingTab settingTab = this.tabs.get(this.selectedTabIndex);
            int n4 = 80;
            int n5 = n2 + 20;
            for (Setting setting : settingTab.settings) {
                this.renderSetting(graphics2D, setting, n4, n5, n - 160);
                if ((n5 += 40) <= n3 - 120) continue;
                break;
            }
        }
    }

    private void renderSetting(Graphics2D graphics2D, Setting setting, int n, int n2, int n3) {
        graphics2D.setColor(new Color(200, 200, 220));
        graphics2D.setFont(new Font("Arial", 0, 14));
        graphics2D.drawString(setting.label, n, n2 + 15);
        int n4 = n + 300;
        switch (setting.type.ordinal()) {
            case 0: {
                this.renderSlider(graphics2D, setting, n4, n2, n3 - 300);
                break;
            }
            case 1: {
                this.renderToggle(graphics2D, setting, n4, n2);
                break;
            }
            case 2: {
                this.renderDropdown(graphics2D, setting, n4, n2);
                break;
            }
            case 3: {
                graphics2D.setColor(new Color(150, 180, 220));
                graphics2D.drawString(setting.value, n4, n2 + 15);
            }
        }
    }

    private void renderSlider(Graphics2D graphics2D, Setting setting, int n, int n2, int n3) {
        graphics2D.setColor(new Color(40, 60, 90, 150));
        graphics2D.fillRoundRect(n, n2 + 3, 200, 20, 5, 5);
        try {
            int n4 = Integer.parseInt(setting.value);
            float f = (float)(n4 - setting.minValue) / (float)(setting.maxValue - setting.minValue);
            int n5 = (int)(200.0f * f);
            graphics2D.setColor(new Color(100, 150, 200, 200));
            graphics2D.fillRoundRect(n, n2 + 3, n5, 20, 5, 5);
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        graphics2D.setColor(new Color(80, 130, 180, 200));
        graphics2D.setStroke(new BasicStroke(1.0f));
        graphics2D.drawRoundRect(n, n2 + 3, 200, 20, 5, 5);
        graphics2D.setColor(new Color(200, 200, 220));
        graphics2D.setFont(new Font("Arial", 0, 12));
        String string = setting.value;
        graphics2D.drawString(string, n + 200 + 10, n2 + 15);
    }

    private void renderToggle(Graphics2D graphics2D, Setting setting, int n, int n2) {
        boolean bl = setting.value.equalsIgnoreCase("On");
        graphics2D.setColor(bl ? new Color(100, 200, 100, 180) : new Color(80, 80, 120, 180));
        graphics2D.fillRoundRect(n, n2 + 2, 40, 20, 5, 5);
        graphics2D.setColor(bl ? new Color(150, 255, 150) : new Color(100, 100, 160));
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRoundRect(n, n2 + 2, 40, 20, 5, 5);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 11));
        graphics2D.drawString(bl ? "ON" : "OFF", n + 10, n2 + 15);
    }

    private void renderDropdown(Graphics2D graphics2D, Setting setting, int n, int n2) {
        graphics2D.setColor(new Color(60, 90, 130, 180));
        graphics2D.fillRoundRect(n, n2 + 2, 120, 20, 5, 5);
        graphics2D.setColor(new Color(100, 150, 200, 200));
        graphics2D.setStroke(new BasicStroke(1.0f));
        graphics2D.drawRoundRect(n, n2 + 2, 120, 20, 5, 5);
        graphics2D.setColor(new Color(200, 200, 220));
        graphics2D.setFont(new Font("Arial", 0, 11));
        graphics2D.drawString(setting.value, n + 8, n2 + 15);
        graphics2D.setColor(new Color(100, 150, 200));
        graphics2D.drawString("\u25bc", n + 105, n2 + 14);
    }

    private void renderFooter(Graphics2D graphics2D, int n, int n2) {
        int n3 = n2 - 45;
        graphics2D.setColor(new Color(60, 90, 130, 200));
        graphics2D.fillRect(0, n3, n, 45);
        int n4 = n / 2 - 110;
        this.renderButton(graphics2D, "SAVE", n4, n3 + 10, true);
        int n5 = n / 2 + 10;
        this.renderButton(graphics2D, "RESET", n5, n3 + 10, false);
    }

    private void renderButton(Graphics2D graphics2D, String string, int n, int n2, boolean bl) {
        graphics2D.setColor(bl ? new Color(100, 150, 200, 180) : new Color(80, 80, 120, 180));
        graphics2D.fillRoundRect(n, n2, 90, 25, 5, 5);
        graphics2D.setColor(bl ? new Color(150, 200, 255) : new Color(100, 120, 160));
        graphics2D.setStroke(new BasicStroke(1.0f));
        graphics2D.drawRoundRect(n, n2, 90, 25, 5, 5);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 12));
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n3 = n + (90 - fontMetrics.stringWidth(string)) / 2;
        int n4 = n2 + (25 - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
        graphics2D.drawString(string, n3, n4);
    }

    public void nextTab() {
        if (this.selectedTabIndex < this.tabs.size() - 1) {
            ++this.selectedTabIndex;
        }
    }

    public void previousTab() {
        if (this.selectedTabIndex > 0) {
            --this.selectedTabIndex;
        }
    }

    public SettingTab getCurrentTab() {
        if (this.selectedTabIndex >= 0 && this.selectedTabIndex < this.tabs.size()) {
            return this.tabs.get(this.selectedTabIndex);
        }
        return null;
    }

    public void saveSettings() {
        System.out.println("[\u2713] Settings saved");
    }

    public void resetToDefaults() {
        this.initializeSettings();
        System.out.println("[\u2713] Settings reset to defaults");
    }

    public void setDisplayed(boolean bl) {
        this.isDisplayed = bl;
    }

    public boolean isDisplayed() {
        return this.isDisplayed;
    }

    public void selectPreviousSetting() {
        SettingTab settingTab = this.getCurrentTab();
        if (settingTab != null && this.selectedSettingIndex > 0) {
            --this.selectedSettingIndex;
            System.out.println("[Settings] Selected previous setting: " + settingTab.settings.get((int)this.selectedSettingIndex).label);
        }
    }

    public void selectNextSetting() {
        SettingTab settingTab = this.getCurrentTab();
        if (settingTab != null && this.selectedSettingIndex < settingTab.settings.size() - 1) {
            ++this.selectedSettingIndex;
            System.out.println("[Settings] Selected next setting: " + settingTab.settings.get((int)this.selectedSettingIndex).label);
        }
    }

    public void adjustCurrentSetting(int n) {
        int n2;
        int n3;
        SettingTab settingTab = this.getCurrentTab();
        if (settingTab == null || this.selectedSettingIndex >= settingTab.settings.size()) {
            return;
        }
        Setting setting = settingTab.settings.get(this.selectedSettingIndex);
        if (setting.type == SettingType.SLIDER) {
            try {
                int n4 = Integer.parseInt(setting.value);
                int n5 = 5;
                n4 += n * n5;
                n4 = Math.max(setting.minValue, Math.min(setting.maxValue, n4));
                setting.value = String.valueOf(n4);
                System.out.println("[Settings] " + setting.label + " = " + n4);
            }
            catch (NumberFormatException numberFormatException) {}
        } else if (setting.type == SettingType.DROPDOWN && setting.options != null && (n3 = (n2 = setting.options.indexOf(setting.value)) + n) >= 0 && n3 < setting.options.size()) {
            setting.value = setting.options.get(n3);
            System.out.println("[Settings] " + setting.label + " = " + setting.value);
        }
    }

    public void confirmCurrentSetting() {
        SettingTab settingTab = this.getCurrentTab();
        if (settingTab == null || this.selectedSettingIndex >= settingTab.settings.size()) {
            return;
        }
        Setting setting = settingTab.settings.get(this.selectedSettingIndex);
        if (setting.type == SettingType.TOGGLE) {
            boolean bl = setting.value.equals("ON");
            setting.value = bl ? "OFF" : "ON";
            System.out.println("[Settings] " + setting.label + " = " + setting.value);
        }
    }

    public Setting getSelectedSetting() {
        SettingTab settingTab = this.getCurrentTab();
        if (settingTab != null && this.selectedSettingIndex < settingTab.settings.size()) {
            return settingTab.settings.get(this.selectedSettingIndex);
        }
        return null;
    }

    public static class SettingTab {
        public String name;
        public String category;
        public List<Setting> settings;

        public SettingTab(String string, String string2) {
            this.name = string;
            this.category = string2;
            this.settings = new ArrayList<Setting>();
        }
    }

    public static class Setting {
        public String label;
        public String value;
        public SettingType type;
        public int minValue;
        public int maxValue;
        public List<String> options;

        public Setting(String string, String string2, SettingType settingType) {
            this.label = string;
            this.value = string2;
            this.type = settingType;
        }
    }

    public static enum SettingType {
        SLIDER,
        TOGGLE,
        DROPDOWN,
        INFO;

    }
}
