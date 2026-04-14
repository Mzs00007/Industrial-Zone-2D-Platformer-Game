/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public static class AnimationAndSpriteLoader.FrameTileBuilder {
    private String frameName;
    private String cornerTopLeft;
    private String cornerTopRight;
    private String cornerBottomLeft;
    private String cornerBottomRight;
    private String edgeTop;
    private String edgeBottom;
    private String edgeLeft;
    private String edgeRight;
    private String interior;
    private BufferedImage imgCornerTL;
    private BufferedImage imgCornerTR;
    private BufferedImage imgCornerBL;
    private BufferedImage imgCornerBR;
    private BufferedImage imgEdgeTop;
    private BufferedImage imgEdgeBottom;
    private BufferedImage imgEdgeLeft;
    private BufferedImage imgEdgeRight;
    private BufferedImage imgInterior;

    public AnimationAndSpriteLoader.FrameTileBuilder(String string) {
        this.frameName = string;
    }

    public AnimationAndSpriteLoader.FrameTileBuilder setCornerTopLeft(String string) {
        this.cornerTopLeft = string;
        return this;
    }

    public AnimationAndSpriteLoader.FrameTileBuilder setCornerTopRight(String string) {
        this.cornerTopRight = string;
        return this;
    }

    public AnimationAndSpriteLoader.FrameTileBuilder setCornerBottomLeft(String string) {
        this.cornerBottomLeft = string;
        return this;
    }

    public AnimationAndSpriteLoader.FrameTileBuilder setCornerBottomRight(String string) {
        this.cornerBottomRight = string;
        return this;
    }

    public AnimationAndSpriteLoader.FrameTileBuilder setEdgeTop(String string) {
        this.edgeTop = string;
        return this;
    }

    public AnimationAndSpriteLoader.FrameTileBuilder setEdgeBottom(String string) {
        this.edgeBottom = string;
        return this;
    }

    public AnimationAndSpriteLoader.FrameTileBuilder setEdgeLeft(String string) {
        this.edgeLeft = string;
        return this;
    }

    public AnimationAndSpriteLoader.FrameTileBuilder setEdgeRight(String string) {
        this.edgeRight = string;
        return this;
    }

    public AnimationAndSpriteLoader.FrameTileBuilder setInterior(String string) {
        this.interior = string;
        return this;
    }

    public void validate() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.cornerTopLeft == null || this.cornerTopLeft.isEmpty()) {
            stringBuilder.append("\u2717 Corner-TopLeft missing\n");
        }
        if (this.cornerTopRight == null || this.cornerTopRight.isEmpty()) {
            stringBuilder.append("\u2717 Corner-TopRight missing\n");
        }
        if (this.cornerBottomLeft == null || this.cornerBottomLeft.isEmpty()) {
            stringBuilder.append("\u2717 Corner-BottomLeft missing\n");
        }
        if (this.cornerBottomRight == null || this.cornerBottomRight.isEmpty()) {
            stringBuilder.append("\u2717 Corner-BottomRight missing\n");
        }
        if (this.edgeTop == null || this.edgeTop.isEmpty()) {
            stringBuilder.append("\u2717 Edge-Top missing\n");
        }
        if (this.edgeBottom == null || this.edgeBottom.isEmpty()) {
            stringBuilder.append("\u2717 Edge-Bottom missing\n");
        }
        if (this.edgeLeft == null || this.edgeLeft.isEmpty()) {
            stringBuilder.append("\u2717 Edge-Left missing\n");
        }
        if (this.edgeRight == null || this.edgeRight.isEmpty()) {
            stringBuilder.append("\u2717 Edge-Right missing\n");
        }
        if (this.interior == null || this.interior.isEmpty()) {
            stringBuilder.append("\u2717 Interior missing\n");
        }
        if (stringBuilder.length() > 0) {
            throw new IllegalStateException("Frame '" + this.frameName + "' is incomplete:\n" + stringBuilder.toString());
        }
        AnimationAndSpriteLoader.log("\u2713 Frame '" + this.frameName + "' validated successfully");
    }

    public boolean loadAll() {
        this.validate();
        String string = "Resources/industrial-zone/gui/1 Frames";
        try {
            this.imgCornerTL = ImageIO.read(new File(string + "/" + this.cornerTopLeft));
            this.imgCornerTR = ImageIO.read(new File(string + "/" + this.cornerTopRight));
            this.imgCornerBL = ImageIO.read(new File(string + "/" + this.cornerBottomLeft));
            this.imgCornerBR = ImageIO.read(new File(string + "/" + this.cornerBottomRight));
            this.imgEdgeTop = ImageIO.read(new File(string + "/" + this.edgeTop));
            this.imgEdgeBottom = ImageIO.read(new File(string + "/" + this.edgeBottom));
            this.imgEdgeLeft = ImageIO.read(new File(string + "/" + this.edgeLeft));
            this.imgEdgeRight = ImageIO.read(new File(string + "/" + this.edgeRight));
            this.imgInterior = ImageIO.read(new File(string + "/" + this.interior));
            AnimationAndSpriteLoader.log("\u2713 Frame '" + this.frameName + "' loaded: all 9 tiles");
            return true;
        }
        catch (IOException iOException) {
            AnimationAndSpriteLoader.logError("Failed to load frame '" + this.frameName + "': " + iOException.getMessage());
            return false;
        }
    }

    public BufferedImage getCornerTopLeft() {
        return this.imgCornerTL;
    }

    public BufferedImage getCornerTopRight() {
        return this.imgCornerTR;
    }

    public BufferedImage getCornerBottomLeft() {
        return this.imgCornerBL;
    }

    public BufferedImage getCornerBottomRight() {
        return this.imgCornerBR;
    }

    public BufferedImage getEdgeTop() {
        return this.imgEdgeTop;
    }

    public BufferedImage getEdgeBottom() {
        return this.imgEdgeBottom;
    }

    public BufferedImage getEdgeLeft() {
        return this.imgEdgeLeft;
    }

    public BufferedImage getEdgeRight() {
        return this.imgEdgeRight;
    }

    public BufferedImage getInterior() {
        return this.imgInterior;
    }
}
