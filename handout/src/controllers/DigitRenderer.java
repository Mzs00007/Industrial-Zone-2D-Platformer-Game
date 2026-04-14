/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import animation.AnimationAndSpriteLoader;
import controllers.GUIAssetManager;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class DigitRenderer
extends AnimationAndSpriteLoader {
    private BufferedImage[] digits = new BufferedImage[10];
    private BufferedImage slash;
    private BufferedImage colon;
    private BufferedImage dot;
    private int digitWidth = 24;
    private int digitHeight = 32;

    public void loadAssets(GUIAssetManager gUIAssetManager) {
        try {
            int n;
            this.digits[0] = gUIAssetManager.getImage("Resources/industrial-zone/gui/numbers/GUI_Number_Digit0_Zero.png");
            for (n = 1; n <= 9; ++n) {
                BufferedImage[] bufferedImageArray = String.format("Resources/industrial-zone/gui/numbers/%02d_GUI_Number_Digit%d_StyledGlyph_Decorative.png", n, n);
                this.digits[n] = gUIAssetManager.getImage((String)bufferedImageArray);
            }
            this.slash = gUIAssetManager.getImage("Resources/industrial-zone/gui/numbers/GUI_Number_Symbol_Slash_Separator.png");
            this.colon = gUIAssetManager.getImage("Resources/industrial-zone/gui/numbers/GUI_Number_Symbol_Colon_Separator.png");
            this.dot = gUIAssetManager.getImage("Resources/industrial-zone/gui/numbers/GUI_Number_Symbol_Dot_Decimal.png");
            n = 0;
            for (BufferedImage bufferedImage : this.digits) {
                if (bufferedImage == null) continue;
                ++n;
            }
            if (this.slash != null) {
                ++n;
            }
            if (this.colon != null) {
                ++n;
            }
            if (this.dot != null) {
                ++n;
            }
            System.out.println("[DigitRenderer] Loaded " + n + " digit/separator assets");
        }
        catch (Exception exception) {
            System.out.println("[WARN] DigitRenderer: Failed to load assets: " + exception.getMessage());
        }
    }

    public void renderNumber(Graphics2D graphics2D, int n, int n2, int n3) {
        if (n < 0) {
            return;
        }
        String string = String.valueOf(n);
        this.renderString(graphics2D, string, n2, n3);
    }

    public void renderString(Graphics2D graphics2D, String string, int n, int n2) {
        int n3 = n;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            BufferedImage bufferedImage = null;
            if (c >= '0' && c <= '9') {
                int n4 = c - 48;
                bufferedImage = this.digits[n4];
            } else if (c == '/') {
                bufferedImage = this.slash;
            } else if (c == ':') {
                bufferedImage = this.colon;
            } else {
                if (c != '.') continue;
                bufferedImage = this.dot;
            }
            if (bufferedImage != null) {
                graphics2D.drawImage(bufferedImage, n3, n2, this.digitWidth, this.digitHeight, null);
            }
            n3 += this.digitWidth;
        }
    }

    public void renderAmmo(Graphics2D graphics2D, int n, int n2, int n3, int n4) {
        String string = n + "/" + n2;
        this.renderString(graphics2D, string, n3, n4);
    }

    public void renderTime(Graphics2D graphics2D, int n, int n2, int n3) {
        int n4 = n / 60;
        int n5 = n % 60;
        String string = String.format("%d:%02d", n4, n5);
        this.renderString(graphics2D, string, n2, n3);
    }

    public void setDigitSpacing(int n, int n2) {
        this.digitWidth = n;
        this.digitHeight = n2;
    }

    public boolean isReady() {
        return this.digits[0] != null;
    }
}
