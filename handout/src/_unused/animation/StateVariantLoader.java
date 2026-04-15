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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
public class StateVariantLoader
extends AnimationAndSpriteLoader.AssetType {
    private String[] stateKeys;
    private Map<String, BufferedImage> variantMap;
    private List<BufferedImage> variantList;
    private String dirPath;

    public StateVariantLoader(String string, String string2, String[] stringArray) {
        super(string, string2);
        this.dirPath = string2;
        this.stateKeys = stringArray;
        this.variantMap = new HashMap<String, BufferedImage>();
        this.variantList = new ArrayList<BufferedImage>();
    }

    @Override
    public boolean load() {
        try {
            File file = new File(this.dirPath);
            if (!file.isDirectory()) {
                throw new IOException("Path is not a directory: " + this.dirPath);
            }
            this.loadVariantSprites(file);
            if (this.variantMap.isEmpty()) {
                throw new IOException("No variant sprites found");
            }
            AnimationAndSpriteLoader.log("\u2713 State variant loader initialized: " + this.assetName);
            AnimationAndSpriteLoader.log("  States loaded: " + this.variantMap.size());
            for (String string : this.variantMap.keySet()) {
                BufferedImage bufferedImage = this.variantMap.get(string);
                AnimationAndSpriteLoader.log("    [" + string + "] " + bufferedImage.getWidth() + "x" + bufferedImage.getHeight() + "px");
            }
            return true;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to load state variants: " + this.assetName);
            AnimationAndSpriteLoader.logError("Path: " + this.dirPath);
            AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
            return false;
        }
    }

    private void loadVariantSprites(File file2) throws IOException {
        Object[] objectArray = file2.listFiles(file -> file.isFile() && this.isPNGFile(file.getName()));
        if (objectArray == null) {
            objectArray = new File[]{};
        }
        Arrays.sort(objectArray);
        for (Object object : objectArray) {
            BufferedImage bufferedImage = ImageIO.read((File)object);
            if (bufferedImage == null) continue;
            String string = ((File)object).getName();
            for (String string2 : this.stateKeys) {
                if (!string.contains(string2)) continue;
                this.variantMap.put(string2, bufferedImage);
                this.variantList.add(bufferedImage);
                AnimationAndSpriteLoader.log("  Loaded: " + string2 + " <- " + string);
                break;
            }
            if (this.variantMap.containsValue(bufferedImage)) continue;
            String string3 = "variant_" + this.variantList.size();
            this.variantMap.put(string3, bufferedImage);
            this.variantList.add(bufferedImage);
        }
    }

    private boolean isPNGFile(String string) {
        return string.toLowerCase().endsWith(".png");
    }

    public BufferedImage getVariant(String string) {
        BufferedImage bufferedImage = this.variantMap.get(string);
        if (bufferedImage == null) {
            AnimationAndSpriteLoader.logError("State variant not found: " + string);
        }
        return bufferedImage;
    }

    public BufferedImage getVariant(int n) {
        if (n < 0 || n >= this.variantList.size()) {
            AnimationAndSpriteLoader.logError("Variant index out of bounds: " + n + " (total: " + this.variantList.size() + ")");
            return null;
        }
        return this.variantList.get(n);
    }

    @Override
    public BufferedImage getFrame(int n) {
        return this.getVariant(n);
    }

    @Override
    public int getFrameCount() {
        return this.variantList.size();
    }

    @Override
    public int getFrameWidth() {
        if (this.variantList.isEmpty()) {
            return 0;
        }
        return this.variantList.get(0).getWidth();
    }

    @Override
    public int getFrameHeight() {
        if (this.variantList.isEmpty()) {
            return 0;
        }
        return this.variantList.get(0).getHeight();
    }

    public int getVariantCount() {
        return this.variantMap.size();
    }
}
