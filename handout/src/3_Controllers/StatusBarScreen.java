/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import managers.GameState;
import controllers.screens.AssetDrivenScreen;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class StatusBarScreen
extends AssetDrivenScreen {
    private BufferedImage[] healthBars;
    private BufferedImage[] energyBars;
    private int currentHealthPercent = 100;
    private int currentEnergyPercent = 100;
    private static final int BAR_DISPLAY_WIDTH = 300;
    private static final int BAR_DISPLAY_HEIGHT = 40;
    private static final String[] HEALTH_BAR_FILES = new String[]{"01_GUI_Bar_HealthBar_Full100pct_RedOrangeFillDarkFrame_HUD.png", "02_GUI_Bar_HealthBar_80pct_RedOrangeFill_HUD.png", "03_GUI_Bar_HealthBar_60pct_OrangeFill_HUD.png", "04_GUI_Bar_HealthBar_40pct_OrangeFill_HUD.png", "05_GUI_Bar_HealthBar_20pct_DarkRedFill_HUD.png", "06_GUI_Bar_HealthBar_Critical5pct_RedFlashing_HUD.png", "07_GUI_Bar_HealthBar_Empty0pct_GreyEmpty_HUD.png"};
    private static final String[] ENERGY_BAR_FILES = new String[]{"09_GUI_Bar_EnergyBar_Full100pct_BlueCyanFillDarkFrame_HUD.png", "10_GUI_Bar_EnergyBar_80pct_BlueFill_HUD.png", "11_GUI_Bar_EnergyBar_60pct_CyanFill_HUD.png", "12_GUI_Bar_EnergyBar_40pct_LightBlueFill_HUD.png", "13_GUI_Bar_EnergyBar_20pct_OrangeFlashing_HUD.png", "14_GUI_Bar_EnergyBar_Empty0pct_GreyEmpty_HUD.png"};
    private static final String BAR_DIRECTORY = "Resources/industrial-zone/gui/2 Bars";

    public StatusBarScreen() {
        super(GameState.PLAYING);
        this.loadAssets();
    }

    private void loadAssets() {
        String string;
        int n;
        this.healthBars = new BufferedImage[HEALTH_BAR_FILES.length];
        for (n = 0; n < HEALTH_BAR_FILES.length; ++n) {
            string = "Resources/industrial-zone/gui/2 Bars/" + HEALTH_BAR_FILES[n];
            this.healthBars[n] = this.loadImage(string);
            if (this.healthBars[n] == null) {
                System.err.println("[StatusBarScreen] \u2717 Failed to load health bar: " + HEALTH_BAR_FILES[n]);
                continue;
            }
            System.out.println("[StatusBarScreen] \u2713 Loaded health bar " + (n + 1));
        }
        this.energyBars = new BufferedImage[ENERGY_BAR_FILES.length];
        for (n = 0; n < ENERGY_BAR_FILES.length; ++n) {
            string = "Resources/industrial-zone/gui/2 Bars/" + ENERGY_BAR_FILES[n];
            this.energyBars[n] = this.loadImage(string);
            if (this.energyBars[n] == null) {
                System.err.println("[StatusBarScreen] \u2717 Failed to load energy bar: " + ENERGY_BAR_FILES[n]);
                continue;
            }
            System.out.println("[StatusBarScreen] \u2713 Loaded energy bar " + (n + 1));
        }
    }

    @Override
    public void update(float f) {
        if (this.currentHealthPercent > 0) {
            --this.currentHealthPercent;
        }
        if (this.currentEnergyPercent > 0) {
            --this.currentEnergyPercent;
        }
    }

    @Override
    public void renderBitmap(Graphics2D graphics2D, int n, int n2) {
        BufferedImage bufferedImage;
        BufferedImage bufferedImage2 = this.getHealthBarImage(this.currentHealthPercent);
        if (bufferedImage2 != null) {
            graphics2D.drawImage(bufferedImage2, 50, 50, 350, 90, 0, 0, bufferedImage2.getWidth(), bufferedImage2.getHeight(), null);
        }
        if ((bufferedImage = this.getEnergyBarImage(this.currentEnergyPercent)) != null) {
            graphics2D.drawImage(bufferedImage, 50, 120, 350, 160, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        }
    }

    private BufferedImage getHealthBarImage(int n) {
        if (this.healthBars == null || this.healthBars.length == 0) {
            return null;
        }
        int n2 = 0;
        n2 = n >= 100 ? 0 : (n >= 80 ? 1 : (n >= 60 ? 2 : (n >= 40 ? 3 : (n >= 20 ? 4 : (n > 0 ? 5 : 6)))));
        return n2 < this.healthBars.length ? this.healthBars[n2] : null;
    }

    private BufferedImage getEnergyBarImage(int n) {
        if (this.energyBars == null || this.energyBars.length == 0) {
            return null;
        }
        int n2 = 0;
        n2 = n >= 100 ? 0 : (n >= 80 ? 1 : (n >= 60 ? 2 : (n >= 40 ? 3 : (n >= 20 ? 4 : 5))));
        return n2 < this.energyBars.length ? this.energyBars[n2] : null;
    }

    public void setHealth(int n) {
        this.currentHealthPercent = Math.max(0, Math.min(100, n));
    }

    public void setEnergy(int n) {
        this.currentEnergyPercent = Math.max(0, Math.min(100, n));
    }
}
