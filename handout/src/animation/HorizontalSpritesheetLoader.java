/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public static class AnimationAndSpriteLoader.HorizontalSpritesheetLoader
extends AnimationAndSpriteLoader.AssetType {
    private int frameWidth;
    private int frameHeight;
    private int frameCount;
    private List<BufferedImage> frames;

    public AnimationAndSpriteLoader.HorizontalSpritesheetLoader(String string, String string2, int n, int n2, int n3) {
        block9: {
            super(string, string2);
            this.frameWidth = n;
            this.frameHeight = n2;
            if (n3 <= 0) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(new File(string2));
                    if (bufferedImage != null) {
                        int[] nArray = new int[]{2, 4, 6, 8, 10, 12, 14, 16, 20, 24};
                        int n4 = 4;
                        double d = Double.MAX_VALUE;
                        for (int n5 : nArray) {
                            double d2;
                            double d3;
                            double d4;
                            int n6;
                            if (bufferedImage.getWidth() % n5 != 0 || (n6 = bufferedImage.getWidth() / n5) < 32 || n6 > 256 || !((d4 = (d3 = Math.abs((d2 = (double)n6 / (double)bufferedImage.getHeight()) - 1.0)) * 1000.0) < d)) continue;
                            d = d4;
                            n4 = n5;
                        }
                        this.frameCount = n4;
                        this.frameWidth = bufferedImage.getWidth() / n4;
                        if (this.frameHeight <= 0) {
                            this.frameHeight = bufferedImage.getHeight();
                        }
                    } else {
                        this.frameCount = 4;
                        if (this.frameHeight <= 0) {
                            this.frameHeight = 48;
                        }
                    }
                    break block9;
                }
                catch (Exception exception) {
                    this.frameCount = 4;
                    if (this.frameHeight <= 0) {
                        this.frameHeight = 48;
                    }
                    break block9;
                }
            }
            this.frameCount = n3;
        }
        this.frames = new ArrayList<BufferedImage>();
    }

    @Override
    public boolean load() {
        try {
            this.loadImageFile();
            if (this.frameHeight <= 0) {
                this.frameHeight = this.height;
            }
            if (this.frameWidth <= 0 && this.frameCount > 0) {
                this.frameWidth = this.width / this.frameCount;
            }
            this.extractFrames();
            AnimationAndSpriteLoader.log("\u2713 Horizontal spritesheet loaded: " + this.assetName);
            AnimationAndSpriteLoader.log("  Frames: " + this.frameCount + " | Size: " + this.frameWidth + "x" + this.frameHeight + "px");
            AnimationAndSpriteLoader.log("  Source: " + this.width + "x" + this.height + "px");
            return true;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to load horizontal spritesheet: " + this.assetName);
            AnimationAndSpriteLoader.logError("Path: " + this.filePath);
            AnimationAndSpriteLoader.logError("Config: " + this.frameWidth + "x" + this.frameHeight + ", " + this.frameCount + " frames");
            AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
            return false;
        }
    }

    private void extractFrames() {
        this.frames.clear();
        for (int i = 0; i < this.frameCount; ++i) {
            int n = i * this.frameWidth;
            if (n + this.frameWidth > this.width) {
                AnimationAndSpriteLoader.logError("Frame " + i + " exceeds image width at x=" + n);
                continue;
            }
            BufferedImage bufferedImage = this.image.getSubimage(n, 0, this.frameWidth, this.frameHeight);
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

    public AnimationAndSpriteLoader.SpriteMetadata getMetadata() {
        return new AnimationAndSpriteLoader.SpriteMetadata(this.width, this.height, this.frameCount, this.frameWidth, this.frameHeight);
    }

    public String getAnalysisReport() {
        AnimationAndSpriteLoader.SpriteMetadata spriteMetadata = this.getMetadata();
        return spriteMetadata.toString() + "  Suggested Timing: " + spriteMetadata.suggestedMs + "ms/frame (for complexity: " + spriteMetadata.complexity + ")\n";
    }
}
