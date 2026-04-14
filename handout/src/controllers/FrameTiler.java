/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.GUIAssetManager;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class FrameTiler {
    private GUIAssetManager assetManager = GUIAssetManager.getInstance();
    private static final String FRAMES_DIR = "Resources/industrial-zone/gui/1 Frames";
    private static final String CORNER_TL = "01_GUI_Frame_CornerTopLeft_TallLShapePiece_WindowCorner.png";
    private static final String CORNER_TR = "03_GUI_Frame_CornerTopRight_TallLShapeMirror_WindowCorner.png";
    private static final String CORNER_BL = "19_GUI_Frame_CornerBottomLeft_LShapeCorner_WindowCorner.png";
    private static final String CORNER_BR = "27_GUI_Frame_CornerBottomRight_DiagonalAngleTrim_WindowCorner.png";
    private static final String EDGE_TOP = "02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png";
    private static final String EDGE_BOTTOM = "20_GUI_Frame_EdgeBottomBar_PlainDarkStrip_WindowBottomEdge.png";
    private static final String EDGE_LEFT = "05_GUI_Frame_EdgeLeftStrip_TallNarrowVerticalBar_WindowLeftEdge.png";
    private static final String EDGE_RIGHT = "06_GUI_Frame_EdgeRightStrip_TallNarrowVerticalBar_WindowRightEdge.png";
    private static final String FILL = "07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png";

    public BufferedImage buildFrame(int n, int n2) {
        try {
            int n3;
            int n4;
            int n5;
            BufferedImage bufferedImage = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/01_GUI_Frame_CornerTopLeft_TallLShapePiece_WindowCorner.png");
            BufferedImage bufferedImage2 = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/03_GUI_Frame_CornerTopRight_TallLShapeMirror_WindowCorner.png");
            BufferedImage bufferedImage3 = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/19_GUI_Frame_CornerBottomLeft_LShapeCorner_WindowCorner.png");
            BufferedImage bufferedImage4 = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/27_GUI_Frame_CornerBottomRight_DiagonalAngleTrim_WindowCorner.png");
            BufferedImage bufferedImage5 = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png");
            BufferedImage bufferedImage6 = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/20_GUI_Frame_EdgeBottomBar_PlainDarkStrip_WindowBottomEdge.png");
            BufferedImage bufferedImage7 = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/05_GUI_Frame_EdgeLeftStrip_TallNarrowVerticalBar_WindowLeftEdge.png");
            BufferedImage bufferedImage8 = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/06_GUI_Frame_EdgeRightStrip_TallNarrowVerticalBar_WindowRightEdge.png");
            BufferedImage bufferedImage9 = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png");
            if (bufferedImage == null || bufferedImage2 == null || bufferedImage3 == null || bufferedImage4 == null || bufferedImage5 == null || bufferedImage6 == null || bufferedImage7 == null || bufferedImage8 == null || bufferedImage9 == null) {
                System.err.println("[FrameTiler] FATAL: Missing frame tiles");
                return null;
            }
            BufferedImage bufferedImage10 = new BufferedImage(n, n2, 2);
            Graphics2D graphics2D = bufferedImage10.createGraphics();
            int n6 = bufferedImage.getWidth();
            int n7 = bufferedImage.getHeight();
            int n8 = bufferedImage7.getWidth();
            int n9 = bufferedImage5.getHeight();
            int n10 = bufferedImage6.getHeight();
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
            graphics2D.drawImage((Image)bufferedImage2, n - n6, 0, null);
            graphics2D.drawImage((Image)bufferedImage3, 0, n2 - n7, null);
            graphics2D.drawImage((Image)bufferedImage4, n - n6, n2 - n7, null);
            int n11 = n - n6 * 2;
            if (n11 > 0) {
                BufferedImage bufferedImage11 = this.assetManager.scaleImage(bufferedImage5, n11, n9);
                graphics2D.drawImage((Image)bufferedImage11, n6, 0, null);
            }
            if ((n5 = n - n6 * 2) > 0) {
                BufferedImage bufferedImage12 = this.assetManager.scaleImage(bufferedImage6, n5, n10);
                graphics2D.drawImage((Image)bufferedImage12, n6, n2 - n10, null);
            }
            if ((n4 = n2 - n7 * 2) > 0) {
                BufferedImage bufferedImage13 = this.assetManager.scaleImage(bufferedImage7, n8, n4);
                graphics2D.drawImage((Image)bufferedImage13, 0, n7, null);
            }
            if ((n3 = n2 - n7 * 2) > 0) {
                BufferedImage bufferedImage14 = this.assetManager.scaleImage(bufferedImage8, n8, n3);
                graphics2D.drawImage((Image)bufferedImage14, n - n8, n7, null);
            }
            int n12 = n6;
            int n13 = n7;
            int n14 = n - n6 * 2;
            int n15 = n2 - n7 * 2;
            if (n14 > 0 && n15 > 0) {
                BufferedImage bufferedImage15 = this.assetManager.scaleImage(bufferedImage9, n14, n15);
                graphics2D.drawImage((Image)bufferedImage15, n12, n13, null);
            }
            graphics2D.dispose();
            return bufferedImage10;
        }
        catch (Exception exception) {
            System.err.println("[FrameTiler] Error building frame: " + exception.getMessage());
            return null;
        }
    }

    public BufferedImage buildCardFrame(int n, int n2, boolean bl) {
        return this.buildFrame(n, n2);
    }

    public BufferedImage buildPanelFrame(int n, int n2) {
        return this.buildFrame(n, n2);
    }
}
