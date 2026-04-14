/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import managers.GameState;
import controllers.screens.AssetDrivenScreen;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Phase6DecorationScreen
extends AssetDrivenScreen {
    private BufferedImage frameCornerTopLeft;
    private BufferedImage frameCornerTopRight;
    private BufferedImage frameCornerBottomLeft;
    private BufferedImage frameCornerBottomRight;
    private BufferedImage frameEdgeTop;
    private BufferedImage frameEdgeBottom;
    private BufferedImage frameEdgeLeft;
    private BufferedImage frameEdgeRight;
    private BufferedImage borderGoldenTrim;
    private BufferedImage borderSilverTrim;
    private BufferedImage decorativeLogo;
    private BufferedImage emblemIcon;
    private Map<String, BufferedImage> decorations = new HashMap<String, BufferedImage>();
    private static final String DECOR_DIRECTORY = "Resources/industrial-zone/gui/9 Other/1 Decor";

    public Phase6DecorationScreen() {
        super(GameState.PLAYING);
        this.loadAssetsOnce();
    }

    private void loadAssetsOnce() {
        this.frameCornerTopLeft = this.loadImage("Resources/industrial-zone/gui/9 Other/1 Decor/01_GUI_Frame_Corner_TopLeft_Golden.png");
        this.frameCornerTopRight = this.loadImage("Resources/industrial-zone/gui/9 Other/1 Decor/02_GUI_Frame_Corner_TopRight_Golden.png");
        this.frameCornerBottomLeft = this.loadImage("Resources/industrial-zone/gui/9 Other/1 Decor/03_GUI_Frame_Corner_BottomLeft_Golden.png");
        this.frameCornerBottomRight = this.loadImage("Resources/industrial-zone/gui/9 Other/1 Decor/04_GUI_Frame_Corner_BottomRight_Golden.png");
        if (this.frameCornerTopLeft != null) {
            System.out.println("[Phase6DecorationScreen] \u2713 Loaded frame corners");
        }
        this.frameEdgeTop = this.loadImage("Resources/industrial-zone/gui/9 Other/1 Decor/05_GUI_Frame_Edge_Top_Golden_Repeatable.png");
        this.frameEdgeBottom = this.loadImage("Resources/industrial-zone/gui/9 Other/1 Decor/06_GUI_Frame_Edge_Bottom_Golden_Repeatable.png");
        this.frameEdgeLeft = this.loadImage("Resources/industrial-zone/gui/9 Other/1 Decor/07_GUI_Frame_Edge_Left_Golden_Repeatable.png");
        this.frameEdgeRight = this.loadImage("Resources/industrial-zone/gui/9 Other/1 Decor/08_GUI_Frame_Edge_Right_Golden_Repeatable.png");
        if (this.frameEdgeTop != null) {
            System.out.println("[Phase6DecorationScreen] \u2713 Loaded frame edges");
        }
        this.borderGoldenTrim = this.loadImage("Resources/industrial-zone/gui/9 Other/1 Decor/09_GUI_Border_GoldenTrim_Decorative.png");
        this.borderSilverTrim = this.loadImage("Resources/industrial-zone/gui/9 Other/1 Decor/10_GUI_Border_SilverTrim_Decorative.png");
        this.decorativeLogo = this.loadImage("Resources/industrial-zone/gui/9 Other/1 Decor/11_GUI_Decor_Logo_IndustrialZone_Emblem.png");
        this.emblemIcon = this.loadImage("Resources/industrial-zone/gui/9 Other/1 Decor/12_GUI_Decor_Emblem_Icon_Ornamental.png");
        if (this.decorativeLogo != null) {
            System.out.println("[Phase6DecorationScreen] \u2713 Loaded decorative elements");
        }
    }

    @Override
    public void update(float f) {
    }

    @Override
    public void renderBitmap(Graphics2D graphics2D, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = 16;
        if (this.frameCornerTopLeft != null) {
            graphics2D.drawImage((Image)this.frameCornerTopLeft, 0, 0, null);
        }
        if (this.frameCornerTopRight != null) {
            n6 = n - this.frameCornerTopRight.getWidth();
            graphics2D.drawImage((Image)this.frameCornerTopRight, n6, 0, null);
        }
        if (this.frameCornerBottomLeft != null) {
            n6 = n2 - this.frameCornerBottomLeft.getHeight();
            graphics2D.drawImage((Image)this.frameCornerBottomLeft, 0, n6, null);
        }
        if (this.frameCornerBottomRight != null) {
            n6 = n - this.frameCornerBottomRight.getWidth();
            n5 = n2 - this.frameCornerBottomRight.getHeight();
            graphics2D.drawImage((Image)this.frameCornerBottomRight, n6, n5, null);
        }
        if (this.frameEdgeTop != null) {
            n6 = this.frameEdgeTop.getWidth();
            n5 = this.frameEdgeTop.getHeight();
            for (n4 = n7; n4 < n - n7; n4 += n6) {
                graphics2D.drawImage(this.frameEdgeTop, n4, 0, Math.min(n4 + n6, n), n5, 0, 0, n6, n5, null);
            }
        }
        if (this.frameEdgeBottom != null) {
            n6 = this.frameEdgeBottom.getWidth();
            n5 = this.frameEdgeBottom.getHeight();
            n4 = n2 - n5;
            for (n3 = n7; n3 < n - n7; n3 += n6) {
                graphics2D.drawImage(this.frameEdgeBottom, n3, n4, Math.min(n3 + n6, n), n2, 0, 0, n6, n5, null);
            }
        }
        if (this.frameEdgeLeft != null) {
            n6 = this.frameEdgeLeft.getWidth();
            n5 = this.frameEdgeLeft.getHeight();
            for (n4 = n7; n4 < n2 - n7; n4 += n5) {
                graphics2D.drawImage(this.frameEdgeLeft, 0, n4, n6, Math.min(n4 + n5, n2), 0, 0, n6, n5, null);
            }
        }
        if (this.frameEdgeRight != null) {
            n6 = this.frameEdgeRight.getWidth();
            n5 = this.frameEdgeRight.getHeight();
            n4 = n - n6;
            for (n3 = n7; n3 < n2 - n7; n3 += n5) {
                graphics2D.drawImage(this.frameEdgeRight, n4, n3, n, Math.min(n3 + n5, n2), 0, 0, n6, n5, null);
            }
        }
        if (this.decorativeLogo != null) {
            n6 = (n - this.decorativeLogo.getWidth()) / 2;
            n5 = 10;
            graphics2D.drawImage((Image)this.decorativeLogo, n6, n5, null);
        }
        if (this.borderGoldenTrim != null && n > 400 && n2 > 300) {
            n6 = n2 - this.borderGoldenTrim.getHeight() - 50;
            n5 = (n - this.borderGoldenTrim.getWidth()) / 2;
            graphics2D.drawImage((Image)this.borderGoldenTrim, n5, n6, null);
        }
    }
}
