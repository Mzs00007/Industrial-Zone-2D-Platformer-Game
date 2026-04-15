package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * ASSET LOADER - Quick asset loading from Resources directory
 * 
 * ✅ RASTER GRAPHICS ONLY - Pure PNG loading
 * ❌ NO Graphics2D, NO forbidden imports
 * ════════════════════════════════════════════════════════════════════════════
 */
public class AssetLoader {
    
    private static final String RESOURCES_BASE = "Resources/industrial-zone/";
    private static Map<String, BufferedImage> assetCache = new HashMap<>();
    private static int cacheHits = 0;
    private static int cacheMisses = 0;
    
    /**
     * Load an asset PNG file by path (relative to Resources base)
     */
    public static BufferedImage loadAsset(String relativePath) {
        String fullPath = RESOURCES_BASE + relativePath;
        
        // Check cache first
        if (assetCache.containsKey(fullPath)) {
            cacheHits++;
            return assetCache.get(fullPath);
        }
        
        try {
            File f = new File(fullPath);
            if (!f.exists()) {
                System.err.println("[AssetLoader] ✗ Not found: " + fullPath);
                cacheMisses++;
                return null;
            }
            
            BufferedImage img = ImageIO.read(f);
            assetCache.put(fullPath, img);
            System.out.println("[AssetLoader] ✓ Loaded: " + relativePath + " (" + img.getWidth() + "×" + img.getHeight() + ")");
            return img;
        } catch (Exception e) {
            System.err.println("[AssetLoader] ✗ Error loading " + fullPath + ": " + e.getMessage());
            cacheMisses++;
            return null;
        }
    }
    
    /**
     * Load all PNG files from a directory
     */
    public static BufferedImage[] loadDirectory(String relativePath) {
        File dir = new File(RESOURCES_BASE + relativePath);
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("[AssetLoader] ✗ Directory not found: " + dir.getAbsolutePath());
            return new BufferedImage[0];
        }
        
        File[] pngFiles = dir.listFiles((f) -> f.getName().endsWith(".png"));
        if (pngFiles == null || pngFiles.length == 0) {
            System.err.println("[AssetLoader] ✗ No PNG files in: " + relativePath);
            return new BufferedImage[0];
        }
        
        BufferedImage[] images = new BufferedImage[pngFiles.length];
        for (int i = 0; i < pngFiles.length; i++) {
            try {
                images[i] = ImageIO.read(pngFiles[i]);
                System.out.println("[AssetLoader] ✓ Loaded: " + pngFiles[i].getName());
            } catch (Exception e) {
                System.err.println("[AssetLoader] ✗ Failed to load: " + pngFiles[i].getName());
            }
        }
        
        return images;
    }
    
    /**
     * Load character animation frames - finds first PNG in character directory
     */
    public static BufferedImage loadCharacterIdle(String characterName) {
        String charDir = "characters/player/" + characterName.toLowerCase() + "/";
        File dir = new File(RESOURCES_BASE + charDir);
        
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("[AssetLoader] ✗ Character directory not found: " + characterName);
            return null;
        }
        
        File[] pngFiles = dir.listFiles((f) -> f.getName().endsWith(".png"));
        if (pngFiles == null || pngFiles.length == 0) {
            System.err.println("[AssetLoader] ✗ No sprite files for: " + characterName);
            return null;
        }
        
        try {
            BufferedImage img = ImageIO.read(pngFiles[0]);
            System.out.println("[AssetLoader] ✓ Loaded character sprite: " + characterName + " (" + img.getWidth() + "×" + img.getHeight() + ")");
            return img;
        } catch (Exception e) {
            System.err.println("[AssetLoader] ✗ Failed to load " + characterName + ": " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Load level background/tilemap
     */
    public static BufferedImage loadLevelBackground(String levelName) {
        String levelDir = "levels/" + levelName + "/";
        File dir = new File(RESOURCES_BASE + levelDir);
        
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("[AssetLoader] ✗ Level directory not found: " + levelName);
            return null;
        }
        
        // Look for background PNG
        File[] pngFiles = dir.listFiles((f) -> f.getName().toLowerCase().contains("background") && f.getName().endsWith(".png"));
        if (pngFiles == null || pngFiles.length == 0) {
            // Fallback: load any PNG
            pngFiles = dir.listFiles((f) -> f.getName().endsWith(".png"));
        }
        
        if (pngFiles == null || pngFiles.length == 0) {
            System.err.println("[AssetLoader] ✗ No background files for: " + levelName);
            return null;
        }
        
        try {
            BufferedImage img = ImageIO.read(pngFiles[0]);
            System.out.println("[AssetLoader] ✓ Loaded level background: " + levelName + " (" + img.getWidth() + "×" + img.getHeight() + ")");
            return img;
        } catch (Exception e) {
            System.err.println("[AssetLoader] ✗ Failed to load level " + levelName + ": " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Clear asset cache
     */
    public static void clearCache() {
        assetCache.clear();
        System.out.println("[AssetLoader] Cache cleared (was " + assetCache.size() + " items)");
    }
    
    /**
     * Print cache statistics
     */
    public static void printStats() {
        System.out.println("\n[AssetLoader Stats]");
        System.out.println("  Cache size: " + assetCache.size() + " items");
        System.out.println("  Cache hits: " + cacheHits);
        System.out.println("  Cache misses: " + cacheMisses);
        if (cacheHits + cacheMisses > 0) {
            double hitRate = (cacheHits * 100.0) / (cacheHits + cacheMisses);
            System.out.println("  Hit rate: " + String.format("%.1f%%", hitRate));
        }
    }
}
