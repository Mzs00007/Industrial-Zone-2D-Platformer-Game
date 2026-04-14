package core.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * UNIFIED ASSET MANAGER v1.0
 * 
 * Consolidates all asset loading, caching, and sprite sheet operations
 * Replaces: AssetLoader + GUIAssetManager (removes duplicate code)
 * 
 * Features:
 * ✓ Intelligent caching (images + sprite sheets)
 * ✓ Automatic frame slicing using manifest metadata
 * ✓ Error logging with full paths
 * ✓ Memory tracking
 * ════════════════════════════════════════════════════════════════════════════
 */
public class AssetManager_001_UnifiedLoader {
    
    private static final Logger LOG = Logger.getLogger(AssetManager_001_UnifiedLoader.class.getName());
    private static AssetManager_001_UnifiedLoader instance = null;
    private static final Object LOCK = new Object();
    
    // Cache maps
    private Map<String, BufferedImage> imageCache = new HashMap<>();
    private Map<String, BufferedImage[]> spriteSheetCache = new HashMap<>();
    
    // Statistics
    private int totalLoads = 0;
    private int cacheHits = 0;
    private long totalBytesLoaded = 0;
    private static final String RESOURCES_BASE = "Resources/";
    
    // ════════════════════════════════════════════════════════════════════════
    // SINGLETON INSTANCE
    // ════════════════════════════════════════════════════════════════════════
    
    public static AssetManager_001_UnifiedLoader getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new AssetManager_001_UnifiedLoader();
                    LOG.info("[AssetManager] Initialized - Ready for asset loading");
                }
            }
        }
        return instance;
    }
    
    private AssetManager_001_UnifiedLoader() {}
    
    // ════════════════════════════════════════════════════════════════════════
    // SINGLE IMAGE LOADING (with caching)
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Load a single PNG image with automatic caching
     * @param relativePath Path relative to Resources/ directory
     *                     Example: "industrial-zone/characters/player/biker/idle_01.png"
     * @return BufferedImage or null if not found/error
     */
    public BufferedImage loadImage(String relativePath) {
        String fullPath = RESOURCES_BASE + relativePath;
        
        // Check cache first
        if (imageCache.containsKey(fullPath)) {
            cacheHits++;
            return imageCache.get(fullPath);
        }
        
        try {
            File imageFile = new File(fullPath);
            if (!imageFile.exists()) {
                LOG.warning("[AssetManager] NOT FOUND: " + fullPath);
                return null;
            }
            
            BufferedImage image = ImageIO.read(imageFile);
            if (image == null) {
                LOG.warning("[AssetManager] FAILED READ: " + fullPath);
                return null;
            }
            
            // Cache and track
            imageCache.put(fullPath, image);
            totalLoads++;
            totalBytesLoaded += (long) image.getWidth() * image.getHeight() * 4;
            
            LOG.info("[AssetManager] LOADED: " + relativePath + 
                    " (" + image.getWidth() + "×" + image.getHeight() + ")");
            return image;
            
        } catch (Exception e) {
            LOG.severe("[AssetManager] ERROR loading " + fullPath + ": " + e.getMessage());
            return null;
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // SPRITE SHEET FRAME EXTRACTION (with automatic slicing)
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Extract a single frame from a sprite sheet
     * Assumes horizontal sprite strip (all frames in one row)
     * 
     * @param spriteSheetPath Path to sprite sheet image
     * @param frameCount Total number of frames in sheet
     * @param frameIndex Which frame to extract (0-based)
     * @return Single frame as BufferedImage
     */
    public BufferedImage getSpriteFrame(String spriteSheetPath, int frameCount, int frameIndex) {
        if (frameIndex < 0 || frameIndex >= frameCount) {
            LOG.warning("[AssetManager] Invalid frame index " + frameIndex + 
                       " (sheet has " + frameCount + " frames)");
            return null;
        }
        
        BufferedImage spriteSheet = loadImage(spriteSheetPath);
        if (spriteSheet == null) {
            return null;
        }
        
        try {
            int frameWidth = spriteSheet.getWidth() / frameCount;
            int frameHeight = spriteSheet.getHeight();
            int x = frameIndex * frameWidth;
            
            BufferedImage frame = spriteSheet.getSubimage(x, 0, frameWidth, frameHeight);
            LOG.fine("[AssetManager] Extracted frame " + frameIndex + " (" + 
                    frameWidth + "×" + frameHeight + ")");
            return frame;
            
        } catch (Exception e) {
            LOG.severe("[AssetManager] ERROR extracting frame: " + e.getMessage());
            return null;
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // SPRITE SHEET FULL SLICING (with caching)
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Extract all frames from a sprite sheet at once
     * Automatically uses manifest metadata for frame count
     * Returns cached array if already loaded
     * 
     * @param spriteSheetPath Path to sprite sheet
     * @param frameCount Number of frames (from AssetMetadata.AnimFrames.*)
     * @return Array of BufferedImage frames
     */
    public BufferedImage[] getSpriteFrames(String spriteSheetPath, int frameCount) {
        String cacheKey = spriteSheetPath + "_" + frameCount;
        
        // Check cache
        if (spriteSheetCache.containsKey(cacheKey)) {
            cacheHits++;
            LOG.fine("[AssetManager] Cache HIT for sprite sheet: " + spriteSheetPath);
            return spriteSheetCache.get(cacheKey);
        }
        
        BufferedImage spriteSheet = loadImage(spriteSheetPath);
        if (spriteSheet == null) {
            return null;
        }
        
        try {
            BufferedImage[] frames = new BufferedImage[frameCount];
            int frameWidth = spriteSheet.getWidth() / frameCount;
            int frameHeight = spriteSheet.getHeight();
            
            for (int i = 0; i < frameCount; i++) {
                int x = i * frameWidth;
                frames[i] = spriteSheet.getSubimage(x, 0, frameWidth, frameHeight);
            }
            
            // Cache and track
            spriteSheetCache.put(cacheKey, frames);
            LOG.info("[AssetManager] SLICED " + frameCount + " frames from: " + spriteSheetPath);
            return frames;
            
        } catch (Exception e) {
            LOG.severe("[AssetManager] ERROR slicing sprite sheet: " + e.getMessage());
            return null;
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // BATCH LOADING (multiple assets at once)
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Load multiple images at once
     * Useful for preloading entire category
     * 
     * @param paths Array of relative paths
     * @return Array of loaded BufferedImages (nulls allowed for failures)
     */
    public BufferedImage[] loadImages(String... paths) {
        BufferedImage[] images = new BufferedImage[paths.length];
        for (int i = 0; i < paths.length; i++) {
            images[i] = loadImage(paths[i]);
        }
        return images;
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // DIRECTORY BATCH LOADING
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Load all PNG files from a directory
     * @param relativePath Path to directory relative to Resources/
     * @return Array of loaded images
     */
    public BufferedImage[] loadDirectory(String relativePath) {
        File dir = new File(RESOURCES_BASE + relativePath);
        if (!dir.exists() || !dir.isDirectory()) {
            LOG.warning("[AssetManager] Directory not found: " + dir.getAbsolutePath());
            return new BufferedImage[0];
        }
        
        File[] pngFiles = dir.listFiles((f) -> f.getName().toLowerCase().endsWith(".png"));
        if (pngFiles == null || pngFiles.length == 0) {
            LOG.warning("[AssetManager] No PNG files in: " + relativePath);
            return new BufferedImage[0];
        }
        
        BufferedImage[] images = new BufferedImage[pngFiles.length];
        for (int i = 0; i < pngFiles.length; i++) {
            try {
                images[i] = ImageIO.read(pngFiles[i]);
                totalLoads++;
                totalBytesLoaded += (long) images[i].getWidth() * images[i].getHeight() * 4;
            } catch (Exception e) {
                LOG.warning("[AssetManager] Failed to load: " + pngFiles[i].getName());
            }
        }
        LOG.info("[AssetManager] Loaded " + pngFiles.length + " images from: " + relativePath);
        return images;
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // CACHE MANAGEMENT
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Clear all caches (useful for memory management)
     */
    public void clearAllCaches() {
        imageCache.clear();
        spriteSheetCache.clear();
        LOG.info("[AssetManager] All caches cleared");
    }
    
    /**
     * Clear only image cache (keeps sprite sheets)
     */
    public void clearImageCache() {
        imageCache.clear();
        LOG.info("[AssetManager] Image cache cleared");
    }
    
    /**
     * Clear only sprite sheet cache
     */
    public void clearSpriteSheetCache() {
        spriteSheetCache.clear();
        LOG.info("[AssetManager] Sprite sheet cache cleared");
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // STATISTICS & DEBUGGING
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Get cache statistics for debugging
     */
    public String getCacheStats() {
        String stats = String.format(
            "[AssetManager Stats]\n" +
            "  Total Loads: %d\n" +
            "  Cache Hits: %d\n" +
            "  Hit Rate: %.1f%%\n" +
            "  Memory Used: %s\n" +
            "  Image Cache Size: %d\n" +
            "  Sprite Sheet Cache Size: %d",
            totalLoads, cacheHits,
            totalLoads > 0 ? (cacheHits * 100.0 / totalLoads) : 0,
            formatBytes(totalBytesLoaded),
            imageCache.size(),
            spriteSheetCache.size()
        );
        return stats;
    }
    
    /**
     * Print cache statistics to console
     */
    public void printCacheStats() {
        System.out.println(getCacheStats());
    }
    
    private String formatBytes(long bytes) {
        if (bytes <= 0) return "0 B";
        final String[] units = new String[] {"B", "KB", "MB", "GB"};
        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        return String.format("%.1f %s", bytes / Math.pow(1024, digitGroups), units[digitGroups]);
    }
}
