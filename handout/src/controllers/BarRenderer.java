/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.GUIAssets;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class BarRenderer {
    private Map<String, BufferedImage> barImageCache = new HashMap<String, BufferedImage>();
    private Map<String, Integer> lastHealth = new HashMap<String, Integer>();
    private Map<String, Integer> lastEnergy = new HashMap<String, Integer>();
    private boolean verbose = true;

    public BarRenderer() {
        this(true);
    }

    public BarRenderer(boolean bl) {
        this.verbose = bl;
        this.preloadBarVariants();
        this.log("BarRenderer initialized");
    }

    public void renderHealthBar(Graphics2D graphics2D, int n, int n2, int n3) {
        this.renderHealthBar(graphics2D, n, n2, n3, "health_bar");
    }

    public void renderHealthBar(Graphics2D graphics2D, int n, int n2, int n3, String string) {
        try {
            String string2 = GUIAssets.getHealthBarForPercentage(n3);
            BufferedImage bufferedImage = this.loadBarImage(string2);
            if (bufferedImage != null) {
                graphics2D.drawImage((Image)bufferedImage, n, n2, null);
                graphics2D.setFont(new Font("Arial", 1, 12));
                graphics2D.setColor(Color.WHITE);
                String string3 = n3 + "%";
                FontMetrics fontMetrics = graphics2D.getFontMetrics();
                int n4 = n + (bufferedImage.getWidth() - fontMetrics.stringWidth(string3)) / 2;
                int n5 = n2 + bufferedImage.getHeight() / 2 + fontMetrics.getAscent() / 2;
                graphics2D.drawString(string3, n4, n5);
            }
            this.lastHealth.put(string, n3);
        }
        catch (Exception exception) {
            this.logError("Failed to render health bar: " + exception.getMessage());
            this.drawFallbackBar(graphics2D, n, n2, n3, true);
        }
    }

    public void renderEnergyBar(Graphics2D graphics2D, int n, int n2, int n3) {
        this.renderEnergyBar(graphics2D, n, n2, n3, "energy_bar");
    }

    public void renderEnergyBar(Graphics2D graphics2D, int n, int n2, int n3, String string) {
        try {
            String string2 = GUIAssets.getEnergyBarForPercentage(n3);
            BufferedImage bufferedImage = this.loadBarImage(string2);
            if (bufferedImage != null) {
                graphics2D.drawImage((Image)bufferedImage, n, n2, null);
                graphics2D.setFont(new Font("Arial", 1, 12));
                graphics2D.setColor(Color.CYAN);
                String string3 = n3 + "%";
                FontMetrics fontMetrics = graphics2D.getFontMetrics();
                int n4 = n + (bufferedImage.getWidth() - fontMetrics.stringWidth(string3)) / 2;
                int n5 = n2 + bufferedImage.getHeight() / 2 + fontMetrics.getAscent() / 2;
                graphics2D.drawString(string3, n4, n5);
            }
            this.lastEnergy.put(string, n3);
        }
        catch (Exception exception) {
            this.logError("Failed to render energy bar: " + exception.getMessage());
            this.drawFallbackBar(graphics2D, n, n2, n3, false);
        }
    }

    public void renderCustomBar(Graphics2D graphics2D, int n, int n2, int n3, int n4, int n5, Color color, String string) {
        graphics2D.setColor(new Color(40, 40, 60));
        graphics2D.fillRect(n, n2, n3, n4);
        graphics2D.setColor(new Color(100, 100, 150));
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRect(n, n2, n3, n4);
        int n6 = n3 * n5 / 100;
        graphics2D.setColor(color);
        graphics2D.fillRect(n + 1, n2 + 1, n6 - 2, n4 - 2);
        if (string != null && !string.isEmpty()) {
            graphics2D.setFont(new Font("Arial", 1, 12));
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(string, n + 5, n2 + n4 / 2 + 5);
        }
    }

    public void renderScrollBar(Graphics2D graphics2D, int n, int n2, int n3) {
        try {
            BufferedImage bufferedImage = this.loadBarImage("Resources/industrial-zone/gui/2 Bars/17_GUI_Bar_ScrollBar_NeonPinkBlueGlowTall_ScrollControl_HUD.png");
            if (bufferedImage != null) {
                graphics2D.drawImage((Image)bufferedImage, n, n2, null);
            }
        }
        catch (Exception exception) {
            this.logError("Failed to render scroll bar: " + exception.getMessage());
        }
    }

    public void clearCaches() {
        this.barImageCache.clear();
        this.lastHealth.clear();
        this.lastEnergy.clear();
        this.log("Bar caches cleared");
    }

    public String getCacheStats() {
        return String.format("BarCache: %d images, %d health bars tracking, %d energy bars tracking", this.barImageCache.size(), this.lastHealth.size(), this.lastEnergy.size());
    }

    private void preloadBarVariants() {
        try {
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/01_GUI_Bar_HealthBar_Full100pct_RedOrangeFillDarkFrame_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/02_GUI_Bar_HealthBar_80pct_RedOrangeFill_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/03_GUI_Bar_HealthBar_60pct_RedOrangeFill_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/04_GUI_Bar_HealthBar_40pct_RedOrangeFill_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/05_GUI_Bar_HealthBar_20pct_RedOrangeFill_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/06_GUI_Bar_HealthBar_5pctCritical_RedOrangeFill_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/07_GUI_Bar_HealthBar_EmptyFrame_NoFillContainer_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/09_GUI_Bar_EnergyBar_Full100pct_BlueCyanFillDarkFrame_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/10_GUI_Bar_EnergyBar_80pct_BlueCyanFill_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/11_GUI_Bar_EnergyBar_60pct_BlueCyanFill_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/12_GUI_Bar_EnergyBar_40pct_BlueCyanFill_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/13_GUI_Bar_EnergyBar_20pct_BlueCyanFill_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/14_GUI_Bar_EnergyBar_5pctCritical_BlueCyanFill_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/15_GUI_Bar_EnergyBar_EmptyFrame_NoFillContainer_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/17_GUI_Bar_ScrollBar_NeonPinkBlueGlowTall_ScrollControl_HUD.png");
            this.loadBarImage("Resources/industrial-zone/gui/2 Bars/19_GUI_Bar_ScrollBar_PlainBlueTallStrip_ScrollControl_HUD.png");
            this.log("Preloaded all bar variants");
        }
        catch (Exception exception) {
            this.logError("Failed to preload bar variants: " + exception.getMessage());
        }
    }

    private BufferedImage loadBarImage(String string) throws Exception {
        if (this.barImageCache.containsKey(string)) {
            return this.barImageCache.get(string);
        }
        File file = new File(string);
        if (!file.exists()) {
            this.logError("Bar image not found: " + string);
            return null;
        }
        BufferedImage bufferedImage = ImageIO.read(file);
        if (bufferedImage != null) {
            this.barImageCache.put(string, bufferedImage);
            this.log("Loaded bar image: " + new File(string).getName());
        }
        return bufferedImage;
    }

    private void drawFallbackBar(Graphics2D graphics2D, int n, int n2, int n3, boolean bl) {
        int n4 = 200;
        int n5 = 20;
        graphics2D.setColor(new Color(30, 30, 50));
        graphics2D.fillRect(n, n2, n4, n5);
        graphics2D.setColor(new Color(100, 100, 150));
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRect(n, n2, n4, n5);
        int n6 = n4 * n3 / 100;
        Color color = bl ? new Color(200, 80, 80) : new Color(80, 150, 200);
        graphics2D.setColor(color);
        graphics2D.fillRect(n + 2, n2 + 2, n6 - 4, n5 - 4);
        graphics2D.setFont(new Font("Arial", 1, 12));
        graphics2D.setColor(Color.WHITE);
        String string = n3 + "%";
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        graphics2D.drawString(string, n + (n4 - fontMetrics.stringWidth(string)) / 2, n2 + n5 / 2 + 5);
    }

    private void log(String string) {
        if (this.verbose) {
            System.out.println("[BarRenderer] " + string);
        }
    }

    private void logError(String string) {
        System.err.println("[BarRenderer] ERROR: " + string);
    }

    public void setVerboseLogging(boolean bl) {
        this.verbose = bl;
    }
}
