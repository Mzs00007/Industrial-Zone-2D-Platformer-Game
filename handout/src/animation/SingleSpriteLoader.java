/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.io.IOException;
public class SingleSpriteLoader
extends AnimationAndSpriteLoader.AssetType {
    public SingleSpriteLoader(String string, String string2) {
        super(string, string2);
    }

    @Override
    public boolean load() {
        try {
            this.loadImageFile();
            AnimationAndSpriteLoader.log("\u2713 Single sprite loaded: " + this.assetName + " (" + this.width + "x" + this.height + "px)");
            return true;
        }
        catch (IOException iOException) {
            AnimationAndSpriteLoader.logError("Failed to load single sprite: " + this.assetName);
            AnimationAndSpriteLoader.logError("Path: " + this.filePath);
            AnimationAndSpriteLoader.logError("Reason: " + iOException.getMessage());
            return false;
        }
    }

    @Override
    public BufferedImage getFrame(int n) {
        if (n != 0) {
            AnimationAndSpriteLoader.logError("SingleSpriteLoader only has 1 frame, requested: " + n);
            return null;
        }
        return this.image;
    }

    @Override
    public int getFrameCount() {
        return 1;
    }

    @Override
    public int getFrameWidth() {
        return this.width;
    }

    @Override
    public int getFrameHeight() {
        return this.height;
    }
}
