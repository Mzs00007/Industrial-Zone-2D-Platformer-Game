/*
 * Decompiled with CFR 0.152.
 */
package animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public static abstract class AnimationAndSpriteLoader.AssetType {
    protected String assetName;
    protected String filePath;
    protected BufferedImage image;
    protected int width;
    protected int height;

    public AnimationAndSpriteLoader.AssetType(String string, String string2) {
        this.assetName = string;
        this.filePath = string2;
    }

    public abstract boolean load();

    public abstract BufferedImage getFrame(int var1);

    public abstract int getFrameCount();

    public abstract int getFrameWidth();

    public abstract int getFrameHeight();

    public String getAssetName() {
        return this.assetName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public BufferedImage getSourceImage() {
        return this.image;
    }

    protected void loadImageFile() throws IOException {
        this.image = ImageIO.read(new File(this.filePath));
        if (this.image == null) {
            throw new IOException("Failed to load image: " + this.filePath);
        }
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }
}
