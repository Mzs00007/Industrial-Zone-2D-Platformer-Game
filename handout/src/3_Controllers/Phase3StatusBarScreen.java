/*
 * Decompiled with CFR 0.152.
 */
package gui.screens;

import core.GameState;
import gui.screens.AssetDrivenScreen;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Phase3StatusBarScreen
extends AssetDrivenScreen {
    private BufferedImage[] healthBars = new BufferedImage[7];
    private int[] healthBarPercentages = new int[]{100, 80, 60, 40, 20, 5, 0};
    private static final String[] HEALTH_BAR_FILES = new String[]{"01_GUI_Bar_HealthBar_Full100pct_RedOrangeFillDarkFrame_HUD.png", "02_GUI_Bar_HealthBar_80pct_RedOrangeFill_HUD.png", "03_GUI_Bar_HealthBar_60pct_OrangeFill_HUD.png", "04_GUI_Bar_HealthBar_40pct_OrangeFill_HUD.png", "05_GUI_Bar_HealthBar_20pct_DarkRedFill_HUD.png", "06_GUI_Bar_HealthBar_Critical5pct_RedFlashing_HUD.png", "07_GUI_Bar_HealthBar_Empty0pct_GreyEmpty_HUD.png"};
    private BufferedImage[] energyBars = new BufferedImage[6];
    private int[] energyBarPercentages = new int[]{100, 80, 60, 40, 20, 0};
    private static final String[] ENERGY_BAR_FILES = new String[]{"09_GUI_Bar_EnergyBar_Full100pct_BlueCyanFillDarkFrame_HUD.png", "10_GUI_Bar_EnergyBar_80pct_BlueFill_HUD.png", "11_GUI_Bar_EnergyBar_60pct_CyanFill_HUD.png", "12_GUI_Bar_EnergyBar_40pct_LightBlueFill_HUD.png", "13_GUI_Bar_EnergyBar_20pct_OrangeFlashing_HUD.png", "14_GUI_Bar_EnergyBar_Empty0pct_GreyEmpty_HUD.png"};
    private int currentHealthPercent = 100;
    private int currentEnergyPercent = 100;
    private static final String BAR_DIRECTORY = "Resources/industrial-zone/gui/2 Bars";

    public Phase3StatusBarScreen() {
        super(GameState.PLAYING);
        this.loadAssetsOnce();
    }

    private void loadAssetsOnce() {
        String string;
        int n;
        for (n = 0; n < HEALTH_BAR_FILES.length; ++n) {
            string = "Resources/industrial-zone/gui/2 Bars/" + HEALTH_BAR_FILES[n];
            this.healthBars[n] = this.loadImage(string);
            if (this.healthBars[n] == null) {
                System.err.println("[Phase3StatusBarScreen] \u2717 FAILED to load health bar: " + string);
                continue;
            }
            System.out.println("[Phase3StatusBarScreen] \u2713 Loaded health bar[" + n + "] " + this.healthBarPercentages[n] + "%: " + string);
        }
        for (n = 0; n < ENERGY_BAR_FILES.length; ++n) {
            string = "Resources/industrial-zone/gui/2 Bars/" + ENERGY_BAR_FILES[n];
            this.energyBars[n] = this.loadImage(string);
            if (this.energyBars[n] == null) {
                System.err.println("[Phase3StatusBarScreen] \u2717 FAILED to load energy bar: " + string);
                continue;
            }
            System.out.println("[Phase3StatusBarScreen] \u2713 Loaded energy bar[" + n + "] " + this.energyBarPercentages[n] + "%: " + string);
        }
    }

    public void setHealthPercent(int n) {
        this.currentHealthPercent = Math.max(0, Math.min(100, n));
    }

    public void setEnergyPercent(int n) {
        this.currentEnergyPercent = Math.max(0, Math.min(100, n));
    }

    private int getHealthBarIndex(int n) {
        if (n >= 100) {
            return 0;
        }
        if (n >= 80) {
            return 1;
        }
        if (n >= 60) {
            return 2;
        }
        if (n >= 40) {
            return 3;
        }
        if (n >= 20) {
            return 4;
        }
        if (n > 0) {
            return 5;
        }
        return 6;
    }

    private int getEnergyBarIndex(int n) {
        if (n >= 100) {
            return 0;
        }
        if (n >= 80) {
            return 1;
        }
        if (n >= 60) {
            return 2;
        }
        if (n >= 40) {
            return 3;
        }
        if (n >= 20) {
            return 4;
        }
        return 5;
    }

    @Override
    public void update(float f) {
    }

    @Override
    public void renderBitmap(Graphics2D graphics2D, int n, int n2) {
        BufferedImage bufferedImage;
        int n3;
        int n4 = this.getHealthBarIndex(this.currentHealthPercent);
        BufferedImage bufferedImage2 = this.healthBars[n4];
        if (bufferedImage2 != null) {
            n3 = 50;
            int n5 = 50;
            graphics2D.drawImage(bufferedImage2, n3, n5, n3 + bufferedImage2.getWidth(), n5 + bufferedImage2.getHeight(), 0, 0, bufferedImage2.getWidth(), bufferedImage2.getHeight(), null);
        }
        if ((bufferedImage = this.energyBars[n3 = this.getEnergyBarIndex(this.currentEnergyPercent)]) != null) {
            int n6 = 50;
            int n7 = 50 + this.healthBars[0].getHeight() + 20;
            graphics2D.drawImage(bufferedImage, n6, n7, n6 + bufferedImage.getWidth(), n7 + bufferedImage.getHeight(), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        }
    }
}
