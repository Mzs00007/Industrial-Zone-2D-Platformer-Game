/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.screens.Screen;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Phase10TooltipScreen
extends Screen {
    private static final int FADE_IN_TIME = 300;
    private static final int TOOLTIP_TIMEOUT = 5000;
    private static final int MIN_WIDTH = 150;
    private static final int MAX_WIDTH = 300;
    private static final int PADDING = 12;
    private String tooltipTitle = "";
    private String tooltipDescription = "";
    private String tooltipStats = "";
    private Color tooltipColor = new Color(100, 150, 200);
    private int tooltipX = 0;
    private int tooltipY = 0;
    private boolean isVisible = false;
    private float fadeProgress = 0.0f;
    private long lastShowTime = 0L;
    private String tooltipCategory = "ITEM";

    public Phase10TooltipScreen() {
        super(null);
    }

    @Override
    public void update(float f) {
        if (this.isVisible) {
            if (this.fadeProgress < 1.0f) {
                this.fadeProgress += f * 1000.0f / 300.0f;
                if (this.fadeProgress > 1.0f) {
                    this.fadeProgress = 1.0f;
                }
            }
            if (System.currentTimeMillis() - this.lastShowTime > 5000L) {
                this.hideTooltip();
            }
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
            if (this.isVisible) {
                this.renderTooltip(graphics2D);
            }
        }
        finally {
            graphics2D.dispose();
        }
        return bufferedImage;
    }

    private void renderTooltip(Graphics2D graphics2D) {
        List<String> list = this.wrapText(this.tooltipDescription, 276);
        int n = 30;
        n += list.size() * 16;
        if (!this.tooltipStats.isEmpty()) {
            n += 16;
        }
        n += 24;
        int n2 = 150;
        int n3 = (int)(this.fadeProgress * 220.0f);
        Color color = new Color(10, 10, 20, n3);
        Color color2 = new Color(this.tooltipColor.getRed(), this.tooltipColor.getGreen(), this.tooltipColor.getBlue(), n3);
        graphics2D.setColor(color);
        graphics2D.fillRoundRect(this.tooltipX, this.tooltipY, n2, n, 8, 8);
        graphics2D.setColor(color2);
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRoundRect(this.tooltipX, this.tooltipY, n2, n, 8, 8);
        Color color3 = new Color(this.tooltipColor.getRed(), this.tooltipColor.getGreen(), this.tooltipColor.getBlue(), n3);
        graphics2D.setColor(color3);
        graphics2D.fillRect(this.tooltipX, this.tooltipY, n2, 4);
        graphics2D.setColor(new Color(220, 220, 255, n3));
        graphics2D.setFont(new Font("Arial", 1, 12));
        graphics2D.drawString(this.tooltipTitle, this.tooltipX + 12, this.tooltipY + 22);
        graphics2D.setFont(new Font("Arial", 0, 8));
        graphics2D.setColor(new Color(150, 150, 190, n3));
        graphics2D.drawString("[" + this.tooltipCategory + "]", this.tooltipX + n2 - 60, this.tooltipY + 22);
        int n4 = this.tooltipY + 40;
        graphics2D.setFont(new Font("Arial", 0, 10));
        graphics2D.setColor(new Color(190, 190, 210, n3));
        for (String string : list) {
            if (n4 + 16 >= this.tooltipY + n - 12) continue;
            graphics2D.drawString(string, this.tooltipX + 12, n4);
            n4 += 16;
        }
        if (!this.tooltipStats.isEmpty()) {
            graphics2D.setColor(new Color(150, 200, 100, n3));
            graphics2D.setFont(new Font("Arial", 1, 9));
            graphics2D.drawString(this.tooltipStats, this.tooltipX + 12, n4 + 16);
        }
    }

    private List<String> wrapText(String string, int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String[] stringArray = string.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : stringArray) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append(string2);
                continue;
            }
            String string3 = stringBuilder.toString() + " " + string2;
            if (string3.length() > n / 8) {
                arrayList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder(string2);
                continue;
            }
            stringBuilder.append(" ").append(string2);
        }
        if (stringBuilder.length() > 0) {
            arrayList.add(stringBuilder.toString());
        }
        return arrayList;
    }

    public void showTooltip(TooltipData tooltipData, int n, int n2) {
        this.tooltipTitle = tooltipData.title;
        this.tooltipDescription = tooltipData.description;
        this.tooltipStats = tooltipData.stats;
        this.tooltipColor = tooltipData.categoryColor;
        this.tooltipCategory = tooltipData.category;
        this.tooltipX = n;
        this.tooltipY = n2;
        this.isVisible = true;
        this.fadeProgress = 0.0f;
        this.lastShowTime = System.currentTimeMillis();
    }

    public void updatePosition(int n, int n2) {
        this.tooltipX = n;
        this.tooltipY = n2;
    }

    public void hideTooltip() {
        this.isVisible = false;
        this.fadeProgress = 0.0f;
    }

    public boolean isTooltipVisible() {
        return this.isVisible;
    }

    public static class TooltipData {
        public String title;
        public String description;
        public String stats;
        public Color categoryColor;
        public String category;

        public TooltipData(String string, String string2, String string3, Color color, String string4) {
            this.title = string;
            this.description = string2;
            this.stats = string3;
            this.categoryColor = color;
            this.category = string4;
        }
    }
}
