/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public static class AnimationAndSpriteLoader.GridSpritesheetLoader
extends AnimationAndSpriteLoader.AssetType {
    private int frameWidth;
    private int frameHeight;
    private int columns;
    private int rows;
    private List<BufferedImage> frames;

    public AnimationAndSpriteLoader.GridSpritesheetLoader(String string, String string2, int n, int n2, int n3, int n4) {
        super(string, string2);
        this.frameWidth = n;
        this.frameHeight = n2;
        this.columns = n3;
        this.rows = n4;
        this.frames = new ArrayList<BufferedImage>();
    }

    @Override
    public boolean load() {
        try {
            this.loadImageFile();
            this.extractGridFrames();
            AnimationAndSpriteLoader.log("\u2713 Grid spritesheet loaded: " + this.assetName);
            AnimationAndSpriteLoader.log("  Grid: " + this.columns + "x" + this.rows + " | Frame size: " + this.frameWidth + "x" + this.frameHeight + "px");
            AnimationAndSpriteLoader.log("  Total frames: " + this.frames.size() + " | Source: " + this.width + "x" + this.height + "px");
            return true;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to load grid spritesheet: " + this.assetName);
            AnimationAndSpriteLoader.logError("Path: " + this.filePath);
            AnimationAndSpriteLoader.logError("Config: " + this.frameWidth + "x" + this.frameHeight + ", Grid: " + this.columns + "x" + this.rows);
            AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
            return false;
        }
    }

    private void extractGridFrames() {
        this.frames.clear();
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.columns; ++j) {
                int n = j * this.frameWidth;
                int n2 = i * this.frameHeight;
                if (n + this.frameWidth > this.width || n2 + this.frameHeight > this.height) {
                    AnimationAndSpriteLoader.log("Skipping frame at grid[" + i + "][" + j + "] - exceeds bounds");
                    continue;
                }
                BufferedImage bufferedImage = this.image.getSubimage(n, n2, this.frameWidth, this.frameHeight);
                this.frames.add(bufferedImage);
            }
        }
    }

    @Override
    public BufferedImage getFrame(int n) {
        if (n < 0 || n >= this.frames.size()) {
            AnimationAndSpriteLoader.logError("Frame index out of bounds: " + n + " (total: " + this.frames.size() + ")");
            return null;
        }
        return this.frames.get(n);
    }

    @Override
    public int getFrameCount() {
        return this.frames.size();
    }

    @Override
    public int getFrameWidth() {
        return this.frameWidth;
    }

    @Override
    public int getFrameHeight() {
        return this.frameHeight;
    }
}
