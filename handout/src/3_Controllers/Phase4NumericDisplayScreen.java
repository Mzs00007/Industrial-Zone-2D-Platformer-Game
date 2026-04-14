/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import managers.GameState;
import controllers.screens.AssetDrivenScreen;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Phase4NumericDisplayScreen
extends AssetDrivenScreen {
    private Map<Integer, BufferedImage> digitImages = new HashMap<Integer, BufferedImage>();
    private BufferedImage dotImage;
    private BufferedImage commaImage;
    private BufferedImage kImage;
    private BufferedImage mImage;
    private BufferedImage plusImage;
    private BufferedImage bImage;
    private static final String[] DIGIT_FILES = new String[]{"GUI_Number_Digit0_Zero.png", "01_GUI_Number_Digit1_StyledGlyph_Decorative.png", "02_GUI_Number_Digit2_StyledGlyph_Decorative.png", "03_GUI_Number_Digit3_StyledGlyph_Decorative.png", "04_GUI_Number_Digit4_StyledGlyph_Decorative.png", "05_GUI_Number_Digit5_StyledGlyph_Decorative.png", "06_GUI_Number_Digit6_StyledGlyph_Decorative.png", "07_GUI_Number_Digit7_StyledGlyph_Decorative.png", "08_GUI_Number_Digit8_StyledGlyph_Decorative.png", "09_GUI_Number_Digit9_StyledGlyph_Decorative.png"};
    private static final String NUMBERS_DIRECTORY = "Resources/industrial-zone/gui/7 Numbers";
    private int healthValue = 100;
    private int energyValue = 80;
    private int ammoValue = 3;

    public Phase4NumericDisplayScreen() {
        super(GameState.PLAYING);
        this.loadAssetsOnce();
    }

    private void loadAssetsOnce() {
        for (int i = 0; i < DIGIT_FILES.length; ++i) {
            String string = "Resources/industrial-zone/gui/7 Numbers/" + DIGIT_FILES[i];
            BufferedImage bufferedImage = this.loadImage(string);
            this.digitImages.put(i, bufferedImage);
            if (bufferedImage == null) {
                System.err.println("[Phase4NumericDisplayScreen] \u2717 Failed to load digit: " + i + " from " + string);
                continue;
            }
            System.out.println("[Phase4NumericDisplayScreen] \u2713 Loaded digit " + i + ": " + bufferedImage.getWidth() + "x" + bufferedImage.getHeight());
        }
        this.dotImage = this.loadImage("Resources/industrial-zone/gui/7 Numbers/GUI_Number_Symbol_Dot_Decimal.png");
        this.commaImage = this.loadImage("Resources/industrial-zone/gui/7 Numbers/GUI_Number_Symbol_Comma_Separator.png");
        this.kImage = this.loadImage("Resources/industrial-zone/gui/7 Numbers/GUI_Number_Symbol_K_Suffix.png");
        this.mImage = this.loadImage("Resources/industrial-zone/gui/7 Numbers/GUI_Number_Symbol_M_Million.png");
        this.plusImage = this.loadImage("Resources/industrial-zone/gui/7 Numbers/GUI_Number_Symbol_Plus_Addition.png");
        this.bImage = this.loadImage("Resources/industrial-zone/gui/7 Numbers/GUI_Number_Symbol_B_Thousand.png");
        if (this.dotImage == null) {
            System.err.println("[Phase4NumericDisplayScreen] \u2717 Failed to load dot symbol");
        } else {
            System.out.println("[Phase4NumericDisplayScreen] \u2713 Loaded dot symbol");
        }
    }

    public void setHealthValue(int n) {
        this.healthValue = Math.max(0, Math.min(999, n));
    }

    public void setEnergyValue(int n) {
        this.energyValue = Math.max(0, Math.min(999, n));
    }

    public void setAmmoValue(int n) {
        this.ammoValue = Math.max(0, Math.min(999, n));
    }

    @Override
    public void update(float f) {
    }

    @Override
    public void renderBitmap(Graphics2D graphics2D, int n, int n2) {
        BufferedImage bufferedImage = this.digitImages.get(0);
        if (bufferedImage == null) {
            return;
        }
        int n3 = bufferedImage.getWidth();
        int n4 = bufferedImage.getHeight();
        int n5 = 4;
        int n6 = 80;
        int n7 = 50;
        this.renderNumber(graphics2D, this.healthValue, n7, n6, n3, n4, n5);
        int n8 = 120;
        int n9 = 50;
        this.renderNumber(graphics2D, this.energyValue, n9, n8, n3, n4, n5);
        int n10 = 160;
        int n11 = 50;
        this.renderNumber(graphics2D, this.ammoValue, n11, n10, n3, n4, n5);
    }

    private void renderNumber(Graphics2D graphics2D, int n, int n2, int n3, int n4, int n5, int n6) {
        String string = String.valueOf(n);
        int n7 = n2;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            int n8 = Character.getNumericValue(c);
            BufferedImage bufferedImage = this.digitImages.get(n8);
            if (bufferedImage != null) {
                graphics2D.drawImage(bufferedImage, n7, n3, n7 + n4, n3 + n5, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
            }
            n7 += n4 + n6;
        }
    }
}
