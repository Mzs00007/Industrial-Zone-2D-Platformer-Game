/*
 * Decompiled with CFR 0.152.
 */
package animation;

import java.awt.image.BufferedImage;

public static class GUIComponentsSystem.HUDBarSystem {
    public static final String UI_TYPE = "hud_bar";
    public static final String DIRECTORY = "Resources/industrial-zone/gui/2 Bars";
    public static final int TOTAL_STATES = 13;
    public static final String RENDERING_FORMAT = "Static PNG, no animation";

    public static class EnergyBarStates {
        public static final String BAR_TYPE = "energy_bar";
        public static final String COLOR = "Blue/Cyan #00DDFF";
        public static final String PURPOSE = "Display player energy/mana percentage";
        public static final String[] ENERGY_BAR_FILES = new String[]{"09_GUI_Bar_EnergyBar_Full100pct_BlueCyanFillDarkFrame_HUD.png", "10_GUI_Bar_EnergyBar_80pct_BlueFill_HUD.png", "11_GUI_Bar_EnergyBar_60pct_CyanFill_HUD.png", "12_GUI_Bar_EnergyBar_40pct_LightBlueFill_HUD.png", "13_GUI_Bar_EnergyBar_20pct_OrangeFlashing_HUD.png", "14_GUI_Bar_EnergyBar_Empty0pct_GreyEmpty_HUD.png"};
        public static final int[] PERCENTAGE_VALUES = new int[]{100, 80, 60, 40, 20, 0};
        public static final String[] STATE_NAMES = new String[]{"Full", "Good", "Okay", "Low", "Critical", "Empty"};
    }

    public static class HealthBarStates {
        public static final String BAR_TYPE = "health_bar";
        public static final String COLOR = "Red/Orange #FF6B35";
        public static final String PURPOSE = "Display player health percentage";
        public static final String[] HEALTH_BAR_FILES = new String[]{"01_GUI_Bar_HealthBar_Full100pct_RedOrangeFillDarkFrame_HUD.png", "02_GUI_Bar_HealthBar_80pct_RedOrangeFill_HUD.png", "03_GUI_Bar_HealthBar_60pct_OrangeFill_HUD.png", "04_GUI_Bar_HealthBar_40pct_OrangeFill_HUD.png", "05_GUI_Bar_HealthBar_20pct_DarkRedFill_HUD.png", "06_GUI_Bar_HealthBar_Critical5pct_RedFlashing_HUD.png", "07_GUI_Bar_HealthBar_Empty0pct_GreyEmpty_HUD.png"};
        public static final int[] PERCENTAGE_VALUES = new int[]{100, 80, 60, 40, 20, 5, 0};
        public static final String[] STATE_NAMES = new String[]{"Full", "Good", "Okay", "Low", "Critical", "UltraCritical", "Dead"};

        public static BufferedImage getHealthBar(int n) {
            if (n >= 100) {
                return null;
            }
            if (n >= 80) {
                return null;
            }
            if (n >= 60) {
                return null;
            }
            if (n >= 40) {
                return null;
            }
            if (n >= 20) {
                return null;
            }
            if (n > 0) {
                return null;
            }
            return null;
        }
    }
}
