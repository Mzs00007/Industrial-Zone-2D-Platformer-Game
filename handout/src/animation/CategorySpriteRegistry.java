/*
 * Decompiled with CFR 0.152.
 */
package animation;
import game2D.*;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
public class CategorySpriteRegistry
extends AnimationAndSpriteLoader.AssetType {
    private String dirPath;
    private Map<String, BufferedImage> spriteRegistry;
    private Map<String, String> spriteToPath;

    public CategorySpriteRegistry(String string, String string2) {
        super(string, string2);
        this.dirPath = string2;
        this.spriteRegistry = new HashMap<String, BufferedImage>();
        this.spriteToPath = new HashMap<String, String>();
    }

    @Override
    public boolean load() {
        try {
            File file = new File(this.dirPath);
            if (!file.isDirectory()) {
                throw new IOException("Path is not a directory: " + this.dirPath);
            }
            this.buildRegistry(file);
            if (this.spriteRegistry.isEmpty()) {
                throw new IOException("No sprites found in directory");
            }
            AnimationAndSpriteLoader.log("\u2713 Category sprite registry initialized: " + this.assetName);
            AnimationAndSpriteLoader.log("  Sprites registered: " + this.spriteRegistry.size());
            AnimationAndSpriteLoader.log("  Directory: " + this.dirPath);
            return true;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to initialize sprite registry: " + this.assetName);
            AnimationAndSpriteLoader.logError("Path: " + this.dirPath);
            AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
            return false;
        }
    }

    private void buildRegistry(File file2) throws IOException {
        File[] fileArray = file2.listFiles(file -> file.isFile() && this.isPNGFile(file.getName()));
        if (fileArray == null) {
            fileArray = new File[]{};
        }
        for (File file3 : fileArray) {
            try {
                BufferedImage bufferedImage = ImageIO.read(file3);
                if (bufferedImage == null) continue;
                String string = this.generateSpriteKey(file3.getName());
                this.spriteRegistry.put(string, bufferedImage);
                this.spriteToPath.put(string, file3.getAbsolutePath());
            }
            catch (IOException iOException) {
                AnimationAndSpriteLoader.logError("Skipped sprite: " + file3.getName() + " - " + iOException.getMessage());
            }
        }
    }

    private String generateSpriteKey(String string) {
        String string2 = string.replaceAll("\\.png$", "");
        string2 = string2.toLowerCase().replaceAll("\\s+", "_");
        string2 = string2.replaceAll("[^a-z0-9_]", "");
        return string2;
    }

    private boolean isPNGFile(String string) {
        return string.toLowerCase().endsWith(".png");
    }

    public BufferedImage getSprite(String string) {
        BufferedImage bufferedImage = this.spriteRegistry.get(string);
        if (bufferedImage != null) {
            return bufferedImage;
        }
        for (String string2 : this.spriteRegistry.keySet()) {
            if (!string2.equalsIgnoreCase(string) && !string2.contains(string)) continue;
            return this.spriteRegistry.get(string2);
        }
        AnimationAndSpriteLoader.logError("Sprite not found: " + string);
        return null;
    }

    public String[] listSprites() {
        return this.spriteRegistry.keySet().toArray(new String[0]);
    }

    public String getSpritePath(String string) {
        return this.spriteToPath.get(string);
    }

    @Override
    public BufferedImage getFrame(int n) {
        String[] stringArray = this.spriteRegistry.keySet().toArray(new String[0]);
        if (n < 0 || n >= stringArray.length) {
            AnimationAndSpriteLoader.logError("Sprite index out of bounds: " + n + " (total: " + stringArray.length + ")");
            return null;
        }
        return this.spriteRegistry.get(stringArray[n]);
    }

    @Override
    public int getFrameCount() {
        return this.spriteRegistry.size();
    }

    @Override
    public int getFrameWidth() {
        if (this.spriteRegistry.isEmpty()) {
            return 0;
        }
        BufferedImage bufferedImage = this.spriteRegistry.values().iterator().next();
        return bufferedImage.getWidth();
    }

    @Override
    public int getFrameHeight() {
        if (this.spriteRegistry.isEmpty()) {
            return 0;
        }
        BufferedImage bufferedImage = this.spriteRegistry.values().iterator().next();
        return bufferedImage.getHeight();
    }
}
