/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class GridFrameAnimationLoader
extends AnimationAndSpriteLoader.AssetType {
    private int frameWidth;
    private int frameHeight;
    private int frameCount;
    private int columns;
    private List<BufferedImage> frames = new ArrayList<BufferedImage>();

    public GridFrameAnimationLoader(String string, String string2) {
        super(string, string2);
    }

    @Override
    public boolean load() {
        try {
            this.loadImageFile();
            this.parseGridConfigFromFilename();
            this.extractGridFrames();
            AnimationAndSpriteLoader.log("\u2713 Grid frame animation loaded: " + this.assetName);
            AnimationAndSpriteLoader.log("  Grid layout: " + this.columns + " columns | " + this.frameCount + " total frames");
            AnimationAndSpriteLoader.log("  Frame size: " + this.frameWidth + "x" + this.frameHeight + "px");
            AnimationAndSpriteLoader.log("  Source: " + this.width + "x" + this.height + "px");
            return true;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to load grid frame animation: " + this.assetName);
            AnimationAndSpriteLoader.logError("Path: " + this.filePath);
            AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
            return false;
        }
    }

    private void parseGridConfigFromFilename() throws IllegalArgumentException {
        String string = new File(this.filePath).getName();
        String string2 = string.toLowerCase();
        Pattern pattern = Pattern.compile("(\\d+)Frames(\\d+)(?:Row|Rows|Col|Cols)", 2);
        Matcher matcher = pattern.matcher(string);
        if (string2.contains("1row") || string2.contains("1rows")) {
            int[] nArray = new int[]{2, 4, 6, 8, 10, 12, 14, 16, 20, 24};
            int n = 4;
            int n2 = this.width / 4;
            double d = Double.MAX_VALUE;
            for (int n3 : nArray) {
                double d2;
                double d3;
                double d4;
                int n4;
                if (this.width % n3 != 0 || (n4 = this.width / n3) < 32 || n4 > 256 || !((d4 = (d3 = Math.abs((d2 = (double)n4 / (double)this.height) - 1.0)) * 1000.0) < d)) continue;
                d = d4;
                n = n3;
                n2 = n4;
            }
            this.frameCount = n;
            this.columns = n;
            this.frameWidth = n2;
            this.frameHeight = this.height;
            AnimationAndSpriteLoader.logError("[METADATA-FIRST] Horizontal layout: analyzing image metadata");
            AnimationAndSpriteLoader.logError("  Image: " + this.width + "x" + this.height + ", Detected frames: " + this.frameCount + " (" + this.frameWidth + "x" + this.frameHeight + " each)");
            return;
        }
        if (matcher.find()) {
            int n = Integer.parseInt(matcher.group(1));
            int n5 = Integer.parseInt(matcher.group(2));
            if (string.contains("Row") || string.contains("row")) {
                this.columns = this.frameCount = n;
                this.frameWidth = this.width / this.columns;
                this.frameHeight = this.height;
            } else if (string.contains("Col") || string.contains("col")) {
                this.frameCount = n;
                this.columns = n5;
                this.frameWidth = this.width / this.columns;
                this.frameHeight = this.height / (this.frameCount / this.columns);
            } else {
                this.columns = this.frameCount = n;
                this.frameWidth = this.width / this.columns;
                this.frameHeight = this.height;
            }
        } else {
            throw new IllegalArgumentException("Cannot parse grid layout from filename: " + string + "\nExpected format like: '4Frames1Row' or '6Frames2Rows'");
        }
    }

    private void extractGridFrames() {
        this.frames.clear();
        int n = (this.frameCount + this.columns - 1) / this.columns;
        for (int i = 0; i < this.frameCount; ++i) {
            int n2 = i / this.columns;
            int n3 = i % this.columns;
            int n4 = n3 * this.frameWidth;
            int n5 = n2 * this.frameHeight;
            if (n4 + this.frameWidth > this.width || n5 + this.frameHeight > this.height) {
                AnimationAndSpriteLoader.logError("Frame " + i + " exceeds bounds at (" + n4 + "," + n5 + ")");
                continue;
            }
            BufferedImage bufferedImage = this.image.getSubimage(n4, n5, this.frameWidth, this.frameHeight);
            this.frames.add(bufferedImage);
        }
        AnimationAndSpriteLoader.log("  Extracted " + this.frames.size() + " frames from " + n + " rows, " + this.columns + " columns");
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
