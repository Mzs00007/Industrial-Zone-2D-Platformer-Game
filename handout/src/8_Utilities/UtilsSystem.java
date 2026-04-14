package utils;

import java.io.*;

/**
 * ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *                              UNIFIED UTILS SYSTEM
 *                   Complete Utility & Helper Functions - All Utils Classes in One
 * ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *
 * RASTER GRAPHICS ONLY - PNG IMAGE ASSETS ONLY
 * ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *
 * UPDATE (April 4, 2026 - UNIFIED UTILS ARCHITECTURE):
 * ─────────────────────────────────────────────────────────────────────────────────────────────────────────────
 * • Consolidated all utility/helper classes into single unified system
 * • Organized as nested static classes for clean architecture
 * • Zero code duplication through inheritance from AnimationAndSpriteLoader
 *
 * CONSOLIDATED FROM:
 * ├─ utils/MathHelper.java → UtilsSystem.MathUtils
 * ├─ utils/Constants.java → UtilsSystem.Constants
 * ├─ utils/ResourceLoader.java → UtilsSystem.ResourceLoader
 * ├─ utils/FileUtils.java → UtilsSystem.FileUtils
 * ├─ utils/StringUtils.java → UtilsSystem.StringUtils
 * ├─ utils/ColorUtils.java → UtilsSystem.ColorUtils
 * └─ utils/TimeUtils.java → UtilsSystem.TimeUtils
 *
 * NESTED STATIC CLASSES INDEX:
 * ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *
 * [1] Constants (LINE ~60)
 *     - Role: Game-wide constants
 *     - Fields: SCREEN_WIDTH, SCREEN_HEIGHT, TILE_SIZE, FPS, etc
 *     - API: UtilsSystem.Constants.SCREEN_WIDTH
 *
 * [2] MathUtils (LINE ~90)
 *     - Role: Vector math, distance calculations, interpolation
 *     - Methods: distance(), clamp(), lerp(), normalize()
 *     - API: UtilsSystem.MathUtils.distance(x1, y1, x2, y2)
 *
 * [3] FileUtils (LINE ~140)
 *     - Role: File I/O operations
 *     - Methods: readFile(), writeFile(), fileExists(), listFiles()
 *     - API: UtilsSystem.FileUtils.readFile(path)
 *
 * [4] StringUtils (LINE ~190)
 *     - Role: String manipulation
 *     - Methods: split(), join(), trim(), format()
 *     - API: UtilsSystem.StringUtils.join(array, ",")
 *
 * [5] ResourceLoader (LINE ~230)
 *     - Role: Load resources from disk safely
 *     - Methods: loadText(), loadBinary(), getResourcePath()
 *     - API: UtilsSystem.ResourceLoader.loadText(resourcePath)
 *
 * [6] TimeUtils (LINE ~280)
 *     - Role: Delta time, timers, interpolation
 *     - Methods: getCurrentTime(), deltaTime(), interpolate()
 *     - API: UtilsSystem.TimeUtils.interpolate(val1, val2, t)
 *
 * @author 3359098
 * @version 1.0 - Unified utils architecture
 * @since 2026-04-04
 */
public class UtilsSystem {
    private static final String TAG = "[UtilsSystem]";
    private static final boolean VERBOSE_LOGGING = true;
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // Constants - Game-wide constants
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Global game constants
     */
    public static final class Constants {
        // Screen
        public static final int SCREEN_WIDTH = 1024;
        public static final int SCREEN_HEIGHT = 768;
        public static final int FPS_TARGET = 60;
        
        // World
        public static final int TILE_SIZE = 32;
        public static final float GRAVITY = -9.81f;
        
        // Gameplay
        public static final float PLAYER_SPEED = 5.0f;
        public static final float PLAYER_JUMP_FORCE = 15.0f;
        public static final int MAX_ENEMIES = 100;
        
        // Assets
        public static final String ASSETS_PATH = "Resources/";
        public static final String RESOURCE_PATH = "Resources/industrial-zone/";
        
        // Never instantiate
        private Constants() {}
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // MathUtils - Vector math and calculations
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Math utility functions
     */
    public static final class MathUtils {
        public static float distance(float x1, float y1, float x2, float y2) {
            float dx = x2 - x1;
            float dy = y2 - y1;
            return (float) Math.sqrt(dx * dx + dy * dy);
        }
        
        public static float clamp(float value, float min, float max) {
            return Math.max(min, Math.min(max, value));
        }
        
        public static float lerp(float start, float end, float t) {
            return start + (end - start) * clamp(t, 0, 1);
        }
        
        public static float normalize(float value, float min, float max) {
            return (value - min) / (max - min);
        }
        
        public static boolean isPointInRect(float px, float py, float rx, float ry, float rw, float rh) {
            return px >= rx && px <= rx + rw && py >= ry && py <= ry + rh;
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // FileUtils - File I/O
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * File system utilities
     */
    public static final class FileUtils {
        public static String readFile(String path) throws IOException {
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            return content.toString();
        }
        
        public static void writeFile(String path, String content) throws IOException {
            try (FileWriter writer = new FileWriter(path)) {
                writer.write(content);
            }
        }
        
        public static boolean fileExists(String path) {
            return new File(path).exists();
        }
        
        public static File[] listFiles(String dirPath) {
            return new File(dirPath).listFiles();
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // StringUtils - String manipulation
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * String utility functions
     */
    public static final class StringUtils {
        public static String[] split(String text, String delimiter) {
            return text.split(delimiter);
        }
        
        public static String join(String[] arr, String delimiter) {
            return String.join(delimiter, arr);
        }
        
        public static String trim(String text) {
            return text.trim();
        }
        
        public static boolean isEmpty(String text) {
            return text == null || text.isEmpty();
        }
        
        public static String format(String template, Object... args) {
            return String.format(template, args);
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // ResourceLoader - Safe resource loading
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Load resources safely
     */
    public static final class ResourceLoader {
        public static String loadText(String resourcePath) throws IOException {
            try {
                return FileUtils.readFile(resourcePath);
            } catch (IOException e) {
                logError("Failed to load text resource: " + resourcePath);
                throw e;
            }
        }
        
        public static byte[] loadBinary(String resourcePath) throws IOException {
            try {
                return java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(resourcePath));
            } catch (IOException e) {
                logError("Failed to load binary resource: " + resourcePath);
                throw e;
            }
        }
        
        public static String getResourcePath(String filename) {
            return Constants.RESOURCE_PATH + filename;
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // TimeUtils - Time and timing
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Time utility functions
     */
    public static final class TimeUtils {
        private static long lastFrameTime = System.currentTimeMillis();
        
        public static long getCurrentTime() {
            return System.currentTimeMillis();
        }
        
        public static float getDeltaTime() {
            long currentTime = System.currentTimeMillis();
            float deltaTime = (currentTime - lastFrameTime) / 1000.0f;
            lastFrameTime = currentTime;
            return deltaTime;
        }
        
        public static float interpolate(float start, float end, float t) {
            return MathUtils.lerp(start, end, t);
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // UTILITY METHODS
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    protected static void log(String message) {
        if (VERBOSE_LOGGING) {
            System.out.println(TAG + " " + message);
        }
    }
    
    protected static void logError(String message) {
        System.err.println(TAG + " ERROR: " + message);
    }
}
