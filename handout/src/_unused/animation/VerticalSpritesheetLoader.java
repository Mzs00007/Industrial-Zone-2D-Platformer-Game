/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
public class VerticalSpritesheetLoader
extends AnimationAndSpriteLoader.AssetType {
    private int frameWidth;
    private int frameHeight;
    private int frameCount;
    private List<BufferedImage> frames;

    public VerticalSpritesheetLoader(String string, String string2, int n, int n2, int n3) {
        super(string, string2);
        this.frameWidth = n;
        this.frameHeight = n2;
        this.frameCount = n3;
        this.frames = new ArrayList<BufferedImage>();
    }

    @Override
    public boolean load() {
        try {
            this.loadImageFile();
            this.extractFrames();
            AnimationAndSpriteLoader.log("\u2713 Vertical spritesheet loaded: " + this.assetName);
            AnimationAndSpriteLoader.log("  Frames: " + this.frameCount + " | Size: " + this.frameWidth + "x" + this.frameHeight + "px");
            AnimationAndSpriteLoader.log("  Source: " + this.width + "x" + this.height + "px");
            return true;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to load vertical spritesheet: " + this.assetName);
            AnimationAndSpriteLoader.logError("Path: " + this.filePath);
            AnimationAndSpriteLoader.logError("Config: " + this.frameWidth + "x" + this.frameHeight + ", " + this.frameCount + " frames");
            AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
            return false;
        }
    }

    private void extractFrames() {
        this.frames.clear();
        for (int i = 0; i < this.frameCount; ++i) {
            int n = i * this.frameHeight;
            if (n + this.frameHeight > this.height) {
                AnimationAndSpriteLoader.logError("Frame " + i + " exceeds image height at y=" + n);
                continue;
            }
            BufferedImage bufferedImage = this.image.getSubimage(0, n, this.frameWidth, this.frameHeight);
            this.frames.add(bufferedImage);
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
        return this.frameCount;
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
