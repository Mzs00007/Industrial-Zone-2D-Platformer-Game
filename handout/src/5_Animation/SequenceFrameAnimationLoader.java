/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

public static class AnimationAndSpriteLoader.SequenceFrameAnimationLoader
extends AnimationAndSpriteLoader.AssetType {
    private int expectedFrameCount;
    private List<BufferedImage> frames;
    private List<String> framePaths;

    public AnimationAndSpriteLoader.SequenceFrameAnimationLoader(String string, String string2, int n) {
        super(string, string2);
        this.expectedFrameCount = n;
        this.frames = new ArrayList<BufferedImage>();
        this.framePaths = new ArrayList<String>();
    }

    @Override
    public boolean load() {
        try {
            File file = new File(this.filePath);
            if (!file.isDirectory()) {
                throw new IOException("Path is not a directory: " + this.filePath);
            }
            this.loadSequenceFrames(file);
            if (this.frames.isEmpty()) {
                throw new IOException("No frame files found in directory");
            }
            AnimationAndSpriteLoader.log("\u2713 Sequence animation loaded: " + this.assetName);
            AnimationAndSpriteLoader.log("  Frames loaded: " + this.frames.size() + " (expected: " + this.expectedFrameCount + ")");
            if (!this.frames.isEmpty()) {
                BufferedImage bufferedImage = this.frames.get(0);
                AnimationAndSpriteLoader.log("  Frame size: " + bufferedImage.getWidth() + "x" + bufferedImage.getHeight() + "px");
            }
            return true;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to load sequence animation: " + this.assetName);
            AnimationAndSpriteLoader.logError("Path: " + this.filePath);
            AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
            return false;
        }
    }

    private void loadSequenceFrames(File file3) throws IOException {
        File[] fileArray = file3.listFiles();
        if (fileArray == null) {
            throw new IOException("Unable to list files in directory");
        }
        Arrays.sort(fileArray, (file, file2) -> {
            int n = this.extractFrameNumber(file.getName());
            int n2 = this.extractFrameNumber(file2.getName());
            return Integer.compare(n, n2);
        });
        this.frames.clear();
        this.framePaths.clear();
        for (File file4 : fileArray) {
            if (!file4.isFile() || !this.isPNGFile(file4.getName())) continue;
            try {
                BufferedImage bufferedImage = ImageIO.read(file4);
                if (bufferedImage == null) continue;
                this.frames.add(bufferedImage);
                this.framePaths.add(file4.getAbsolutePath());
            }
            catch (IOException iOException) {
                AnimationAndSpriteLoader.logError("Skipped frame file: " + file4.getName() + " - " + iOException.getMessage());
            }
        }
    }

    private int extractFrameNumber(String string) {
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return Integer.MAX_VALUE;
    }

    private boolean isPNGFile(String string) {
        return string.toLowerCase().endsWith(".png");
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
        if (this.frames.isEmpty()) {
            return 0;
        }
        return this.frames.get(0).getWidth();
    }

    @Override
    public int getFrameHeight() {
        if (this.frames.isEmpty()) {
            return 0;
        }
        return this.frames.get(0).getHeight();
    }

    public List<String> getFramePaths() {
        return new ArrayList<String>(this.framePaths);
    }
}
