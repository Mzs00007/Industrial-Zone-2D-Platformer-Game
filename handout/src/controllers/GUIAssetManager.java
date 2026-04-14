/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import animation.AnimationAndSpriteLoader;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class GUIAssetManager
extends AnimationAndSpriteLoader {
    private static GUIAssetManager instance = null;
    private static final Object LOCK = new Object();
    private Map<String, BufferedImage> imageCache = new HashMap<String, BufferedImage>();
    private Map<String, BufferedImage[]> spriteSheetCache = new HashMap<String, BufferedImage[]>();
    private int loadCount = 0;
    private int cacheHitCount = 0;
    private long totalMemoryUsed = 0L;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static GUIAssetManager getInstance() {
        if (instance == null) {
            Object object = LOCK;
            synchronized (object) {
                if (instance == null) {
                    instance = new GUIAssetManager();
                }
            }
        }
        return instance;
    }

    private GUIAssetManager() {
        System.out.println("[GUIAssetManager] Initialized - Ready to load assets");
    }

    public BufferedImage getImage(String string) {
        if (this.imageCache.containsKey(string)) {
            ++this.cacheHitCount;
            return this.imageCache.get(string);
        }
        BufferedImage bufferedImage = this.loadImageFromDisk(string);
        if (bufferedImage != null) {
            this.imageCache.put(string, bufferedImage);
            ++this.loadCount;
            this.totalMemoryUsed += (long)bufferedImage.getWidth() * (long)bufferedImage.getHeight() * 4L;
        } else {
            System.err.println("[GUIAssetManager] FAILED TO LOAD: " + string);
        }
        return bufferedImage;
    }

    public BufferedImage getFrameFromSpriteSheet(String string, int n, int n2) {
        BufferedImage bufferedImage = this.getImage(string);
        if (bufferedImage == null) {
            System.err.println("[GUIAssetManager] Failed to load sprite sheet: " + string);
            return null;
        }
        if (n2 < 0 || n2 >= n) {
            System.err.println("[GUIAssetManager] Invalid frame index " + n2 + " for sheet with " + n + " frames");
            return null;
        }
        int n3 = bufferedImage.getWidth() / n;
        int n4 = bufferedImage.getHeight();
        try {
            int n5 = n2 * n3;
            BufferedImage bufferedImage2 = bufferedImage.getSubimage(n5, 0, n3, n4);
            return bufferedImage2;
        }
        catch (Exception exception) {
            System.err.println("[GUIAssetManager] Error extracting frame: " + exception.getMessage());
            return null;
        }
    }

    public BufferedImage[] getSpriteSheetFrames(String string, int n) {
        if (this.spriteSheetCache.containsKey(string)) {
            ++this.cacheHitCount;
            return this.spriteSheetCache.get(string);
        }
        BufferedImage bufferedImage = this.getImage(string);
        if (bufferedImage == null) {
            return null;
        }
        BufferedImage[] bufferedImageArray = new BufferedImage[n];
        int n2 = bufferedImage.getWidth() / n;
        int n3 = bufferedImage.getHeight();
        try {
            for (int i = 0; i < n; ++i) {
                int n4 = i * n2;
                bufferedImageArray[i] = bufferedImage.getSubimage(n4, 0, n2, n3);
            }
            this.spriteSheetCache.put(string, bufferedImageArray);
            ++this.loadCount;
            return bufferedImageArray;
        }
        catch (Exception exception) {
            System.err.println("[GUIAssetManager] Error loading sprite sheet frames: " + exception.getMessage());
            return null;
        }
    }

    public BufferedImage scaleImage(BufferedImage bufferedImage, int n, int n2) {
        if (bufferedImage == null) {
            return null;
        }
        BufferedImage bufferedImage2 = new BufferedImage(n, n2, 2);
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics2D.drawImage(bufferedImage, 0, 0, n, n2, null);
        graphics2D.dispose();
        return bufferedImage2;
    }

    public void clearCache() {
        this.imageCache.clear();
        this.spriteSheetCache.clear();
        System.out.println("[GUIAssetManager] Cache cleared - Memory freed");
    }

    public void printStats() {
        System.out.println("\n" + "\u2550".repeat(70));
        System.out.println("GUIAssetManager Statistics");
        System.out.println("\u2550".repeat(70));
        System.out.println("Images loaded: " + this.loadCount);
        System.out.println("Cache hits: " + this.cacheHitCount);
        System.out.println("Cached images: " + this.imageCache.size());
        System.out.println("Cached sprite sheets: " + this.spriteSheetCache.size());
        System.out.println("Total memory (estimated): " + String.format("%.2f MB", (double)this.totalMemoryUsed / 1024.0 / 1024.0));
        System.out.println("\u2550".repeat(70) + "\n");
    }

    private BufferedImage loadImageFromDisk(String string) {
        try {
            File file = new File(string);
            if (!file.exists()) {
                System.err.println("[GUIAssetManager] File not found: " + string);
                return null;
            }
            BufferedImage bufferedImage = ImageIO.read(file);
            if (bufferedImage == null) {
                System.err.println("[GUIAssetManager] Failed to read image (unsupported format?): " + string);
                return null;
            }
            return bufferedImage;
        }
        catch (IOException iOException) {
            System.err.println("[GUIAssetManager] IOException loading " + string + ": " + iOException.getMessage());
            return null;
        }
        catch (Exception exception) {
            System.err.println("[GUIAssetManager] Unexpected error loading " + string + ": " + exception.getMessage());
            return null;
        }
    }

    public void preloadEssentialAssets() {
        System.out.println("\n[GUIAssetManager] Pre-loading essential assets...\n");
        this.preloadButtonAssets();
        this.preloadFrameAssets();
        this.preloadNumberAssets();
        this.preloadIconAssets();
        this.printStats();
    }

    private void preloadButtonAssets() {
        String[] stringArray = new String[]{"btn_play", "btn_pause", "btn_settings", "btn_help", "btn_wpn_1", "btn_wpn_2", "btn_wpn_3", "btn_quit", "btn_continue"};
        String string = "Resources/industrial-zone/gui/6 Buttons/";
        for (String string2 : stringArray) {
            this.getImage(string + string2 + ".png");
            this.getImage(string + string2 + "_hover.png");
            this.getImage(string + string2 + "_pressed.png");
        }
        System.out.println("[GUIAssetManager] Pre-loaded " + stringArray.length * 3 + " button assets");
    }

    private void preloadFrameAssets() {
        String string = "Resources/industrial-zone/gui/1 Frames/";
        String[] stringArray = new String[]{"theme_1", "theme_2", "theme_3", "theme_4", "theme_5", "theme_6", "theme_7"};
        String[] stringArray2 = new String[]{"corner_tl", "corner_tr", "corner_bl", "corner_br", "edge_top", "edge_bottom", "edge_left", "edge_right", "fill"};
        for (String string2 : stringArray) {
            for (String string3 : stringArray2) {
                this.getImage(string + "frame_" + string2 + "_" + string3 + ".png");
            }
        }
        System.out.println("[GUIAssetManager] Pre-loaded frame assets (7 themes \u00d7 9 tiles)");
    }

    private void preloadNumberAssets() {
        String string = "Resources/industrial-zone/gui/7 Numbers/";
        for (int i = 0; i <= 9; ++i) {
            this.getImage(string + "digit_" + i + ".png");
        }
        System.out.println("[GUIAssetManager] Pre-loaded digit assets (0-9)");
    }

    private void preloadIconAssets() {
        String[] stringArray;
        String string = "Resources/industrial-zone/gui/3 Icons/";
        for (String string2 : stringArray = new String[]{"health.png", "ammo.png", "mana.png"}) {
            this.getImage(string + string2);
        }
        System.out.println("[GUIAssetManager] Pre-loaded icon assets");
    }
}
