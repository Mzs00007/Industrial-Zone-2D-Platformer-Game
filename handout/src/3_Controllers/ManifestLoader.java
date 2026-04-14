package ui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * MANIFESTLOADER - Loads assets using assets-manifest.json reference
 *
 * Uses relative paths from manifist to correctly locate all PNG assets:
 * - Characters: Resources/industrial-zone/characters/player/{name}/
 * - Levels: Resources/industrial-zone/1 Tiles/{name}/2 Background_{name}/
 * - GUI: Resources/industrial-zone/gui/{category}/
 * - VFX: Resources/industrial-zone/vfx/
 *
 * ✅ RASTER GRAPHICS ONLY - Pure PNG loading
 * ❌ NO Graphics2D, Font, Color, or forbidden imports
 * ════════════════════════════════════════════════════════════════════════════
 */
public class ManifestLoader {
    
    private static final String BASE_PATH = "Resources/industrial-zone/";
    private static final Map<String, BufferedImage> cache = new HashMap<>();
    
    private static int cacheHits = 0;
    private static int cacheMisses = 0;
    
    // Asset path patterns based on manifest structure
    private static final String CHAR_PATTERN = "characters/player/{name}/";
    private static final String LEVEL_PATTERN = "1 Tiles/{name}/2 Background_{name}/";
    private static final String GUI_PATTERN = "gui/{category}/";
    private static final String VFX_PATTERN = "vfx/{type}/";
    
    /**
     * Load character idle sprite by name (PUNK, BIKER, CYBORG)
     * Searches for first _Idle_ PNG file in character directory
     */
    public static BufferedImage loadCharacterIdle(String characterName) {
        String dirPath = BASE_PATH + "characters/player/" + characterName.toLowerCase() + "/";
        File dir = new File(dirPath);
        
        if (!dir.exists()) {
            System.err.println("[❌ ManifestLoader] Character directory not found: " + dirPath);
            return null;
        }
        
        // Find first idle animation file
        File[] idleFiles = dir.listFiles((d, name) -> 
            name.toLowerCase().contains("idle") && name.endsWith(".png"));
        
        if (idleFiles != null && idleFiles.length > 0) {
            try {
                BufferedImage img = ImageIO.read(idleFiles[0]);
                String cacheKey = "char_" + characterName + "_idle";
                cache.put(cacheKey, img);
                System.out.println("[✅ ManifestLoader] Loaded " + characterName + " idle: " + idleFiles[0].getName());
                return img;
            } catch (IOException e) {
                System.err.println("[❌ ManifestLoader] Failed to load character idle: " + e.getMessage());
                return null;
            }
        }
        
        System.err.println("[❌ ManifestLoader] No idle animation found for: " + characterName);
        return null;
    }
    
    /**
     * Load level background by name
     * Looks for BG_Composite_* file in level background directory
     */
    public static BufferedImage loadLevelBackground(String levelName) {
        // Try to find the level directory
        String baseLevelDir = BASE_PATH + "1 Tiles/" + levelName + "/2 Background_";
        
        // Try exact match and with suffix
        String[] possibleDirs = {
            BASE_PATH + "1 Tiles/" + levelName + "/2 Background_" + levelName + "/",
            BASE_PATH + "1 Tiles/Industrial_zone_level_1/2 Background_level_1/",
            BASE_PATH + "1 Tiles/power-station-level-2/2 Background_level_2/"
        };
        
        for (String dirPath : possibleDirs) {
            File dir = new File(dirPath);
            if (dir.exists()) {
                // Find composite background
                File[] bgFiles = dir.listFiles((d, name) -> 
                    name.contains("Composite") && name.endsWith(".png"));
                
                if (bgFiles != null && bgFiles.length > 0) {
                    try {
                        BufferedImage img = ImageIO.read(bgFiles[0]);
                        String cacheKey = "level_" + levelName + "_bg";
                        cache.put(cacheKey, img);
                        System.out.println("[✅ ManifestLoader] Loaded level background: " + bgFiles[0].getName());
                        return img;
                    } catch (IOException e) {
                        System.err.println("[❌ ManifestLoader] Failed to load level: " + e.getMessage());
                    }
                }
            }
        }
        
        System.err.println("[❌ ManifestLoader] Level background not found: " + levelName);
        return null;
    }
    
    /**
     * Load a specific GUI frame asset by path
     * Path format: "gui/1 Frames/01_GUI_Frame_CornerTopLeft_..."
     */
    public static BufferedImage loadGuiAsset(String relativePath) {
        if (cache.containsKey(relativePath)) {
            cacheHits++;
            return cache.get(relativePath);
        }
        
        String fullPath = BASE_PATH + relativePath;
        File file = new File(fullPath);
        
        if (!file.exists()) {
            System.err.println("[❌ ManifestLoader] GUI asset not found: " + fullPath);
            cacheMisses++;
            return null;
        }
        
        try {
            BufferedImage img = ImageIO.read(file);
            cache.put(relativePath, img);
            cacheMisses++;
            System.out.println("[✅ ManifestLoader] Loaded GUI asset: " + relativePath);
            return img;
        } catch (IOException e) {
            System.err.println("[❌ ManifestLoader] Failed to load GUI asset: " + e.getMessage());
            cacheMisses++;
            return null;
        }
    }
    
    /**
     * Load a specific asset by full relative path from manifest
     */
    public static BufferedImage loadAsset(String relativePath) {
        if (cache.containsKey(relativePath)) {
            cacheHits++;
            return cache.get(relativePath);
        }
        
        // Convert Windows paths to system-appropriate paths
        String cleanPath = relativePath.replace("\\", File.separator);
        String fullPath = BASE_PATH + cleanPath;
        File file = new File(fullPath);
        
        if (!file.exists()) {
            System.err.println("[❌ ManifestLoader] Asset not found: " + fullPath);
            cacheMisses++;
            return null;
        }
        
        try {
            BufferedImage img = ImageIO.read(file);
            cache.put(relativePath, img);
            cacheMisses++;
            System.out.println("[✅ ManifestLoader] Loaded asset: " + relativePath);
            return img;
        } catch (IOException e) {
            System.err.println("[❌ ManifestLoader] Failed to load asset: " + e.getMessage());
            cacheMisses++;
            return null;
        }
    }
    
    /**
     * Get all character names available
     */
    public static String[] getAvailableCharacters() {
        File charDir = new File(BASE_PATH + "characters/player/");
        if (charDir.exists() && charDir.isDirectory()) {
            File[] dirs = charDir.listFiles(File::isDirectory);
            if (dirs != null) {
                String[] names = new String[dirs.length];
                for (int i = 0; i < dirs.length; i++) {
                    names[i] = dirs[i].getName().toUpperCase();
                }
                return names;
            }
        }
        return new String[]{"PUNK", "BIKER", "CYBORG"}; // Fallback
    }
    
    /**
     * Get all level names available
     */
    public static String[] getAvailableLevels() {
        File tilesDir = new File(BASE_PATH + "1 Tiles/");
        if (tilesDir.exists() && tilesDir.isDirectory()) {
            File[] dirs = tilesDir.listFiles(File::isDirectory);
            if (dirs != null) {
                java.util.List<String> levels = new java.util.ArrayList<>();
                for (File dir : dirs) {
                    if (!dir.getName().startsWith(".")) {
                        levels.add(dir.getName());
                    }
                }
                return levels.toArray(new String[0]);
            }
        }
        return new String[]{"Industrial_zone_level_1", "power-station-level-2"}; // Fallback
    }
    
    /**
     * Clear asset cache
     */
    public static void clearCache() {
        cache.clear();
        System.out.println("[🧹 ManifestLoader] Asset cache cleared");
    }
    
    /**
     * Print cache statistics
     */
    public static void printStats() {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("  MANIFESTLOADER CACHE STATISTICS");
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  Cached Assets: " + cache.size());
        System.out.println("  Cache Hits: " + cacheHits);
        System.out.println("  Cache Misses: " + cacheMisses);
        System.out.println("  Hit Rate: " + (cacheHits + cacheMisses > 0 ? 
            (cacheHits * 100 / (cacheHits + cacheMisses)) + "%" : "N/A"));
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }
    
    /**
     * Verify all critical game assets exist
     */
    public static boolean verifyAssetsExist() {
        System.out.println("[🔍 ManifestLoader] Verifying assets...");
        
        boolean hasCharacters = true;
        for (String charName : getAvailableCharacters()) {
            File charDir = new File(BASE_PATH + "characters/player/" + charName.toLowerCase() + "/");
            if (!charDir.exists()) {
                System.err.println("  ❌ Character directory missing: " + charName);
                hasCharacters = false;
            } else {
                System.out.println("  ☑ Character available: " + charName);
            }
        }
        
        boolean hasLevels = true;
        for (String levelName : getAvailableLevels()) {
            File levelDir = new File(BASE_PATH + "1 Tiles/" + levelName + "/");
            if (!levelDir.exists()) {
                System.err.println("  ❌ Level directory missing: " + levelName);
                hasLevels = false;
            } else {
                System.out.println("  ☑ Level available: " + levelName);
            }
        }
        
        boolean hasGui = new File(BASE_PATH + "gui/").exists();
        if (hasGui) {
            System.out.println("  ☑ GUI assets available");
        } else {
            System.err.println("  ❌ GUI directory missing");
        }
        
        return hasCharacters && hasLevels && hasGui;
    }
}
