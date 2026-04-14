package framework;

import animation.AnimationAndSpriteLoader;
import game2D.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * LevelManager - Level lifecycle and parallax management
 * 
 * RESPONSIBILITY: Level loading, parallax systems, camera management
 * 
 * This class manages:
 * - Level 1 & Level 2 TileMaps
 * - Parallax systems (Level 1, Level 2 Day, Level 2 Night)
 * - Camera position and bounds
 * - Level switching
 * - Background rendering
 */
public class LevelManager {
    
    // Level state
    private int currentLevel = 1;
    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private boolean isLevel1Active = true;
    
    // Level tilemaps
    private TileMap level1TileMap;
    private TileMap level2TileMap;
    
    // Parallax systems
    private AnimationAndSpriteLoader.ParallaxSystem level1Parallax;
    private AnimationAndSpriteLoader.ParallaxSystem level2ParallaxDay;
    private AnimationAndSpriteLoader.ParallaxSystem level2ParallaxNight;
    
    // Camera
    private float cameraX = 0.0f;
    private float cameraY = 0.0f;
    private static final float CAMERA_SPEED = 200.0f;
    private static final int MAX_LEVEL_WIDTH = 22400;
    
    // Raster assets
    private BufferedImage backgroundBlack;
    private BufferedImage hudPanelImage;
    
    /**
     * Initialize level systems
     */
    public void initialize() {
        System.out.println("[LevelManager] Initializing levels and parallax systems...");
        
        // Initialize levels
        initializeLevels();
        
        // Initialize parallax
        initializeParallaxSystems();
        
        // Initialize raster assets
        initializeRasterAssets();
        
        System.out.println("[LevelManager] ✓ Levels ready");
    }
    
    /**
     * Initialize Level 1 & Level 2 TileMaps
     */
    private void initializeLevels() {
        System.out.println("[LevelManager] Initializing Level 1...");
        level1TileMap = new TileMap();
        Level1.initialize(level1TileMap);
        System.out.println("[LevelManager] ✓ Level 1 initialized");
        
        System.out.println("[LevelManager] Initializing Level 2...");
        level2TileMap = new TileMap();
        Level2.initialize(level2TileMap);
        System.out.println("[LevelManager] ✓ Level 2 initialized");
    }
    
    /**
     * Initialize parallax systems for visual depth
     */
    private void initializeParallaxSystems() {
        System.out.println("[LevelManager] Loading Level 1 parallax system...");
        level1Parallax = AnimationAndSpriteLoader.createLevel1ParallaxSystem();
        System.out.println("[LevelManager] ✓ Level 1 parallax: " + level1Parallax.getLayerCount() + " layers");
        
        System.out.println("[LevelManager] Loading Level 2 Day parallax system...");
        level2ParallaxDay = AnimationAndSpriteLoader.createLevel2ParallaxSystemDay();
        System.out.println("[LevelManager] ✓ Level 2 Day parallax: " + level2ParallaxDay.getLayerCount() + " layers");
        
        System.out.println("[LevelManager] Loading Level 2 Night parallax system...");
        level2ParallaxNight = AnimationAndSpriteLoader.createLevel2ParallexSystemNight();
        System.out.println("[LevelManager] ✓ Level 2 Night parallax: " + level2ParallaxNight.getLayerCount() + " layers");
    }
    
    /**
     * Create raster background assets
     */
    private void initializeRasterAssets() {
        // Create black background tile
        backgroundBlack = new BufferedImage(32, 32, 1);
        int[] blackPixels = new int[1024];
        Arrays.fill(blackPixels, -16777216); // Black color
        backgroundBlack.setRGB(0, 0, 32, 32, blackPixels, 0, 32);
        
        // Create HUD panel background
        hudPanelImage = new BufferedImage(1024, 50, 2);
        int[] hudPixels = new int[51200];
        Arrays.fill(hudPixels, -869125582); // Dark gray color
        hudPanelImage.setRGB(0, 0, 1024, 50, hudPixels, 0, 1024);
    }
    
    /**
     * Update level state each frame
     */
    public void update(long deltaMillis) {
        // Update camera position
        updateCamera(deltaMillis);
        
        // Update parallax based on camera
        if (isLevel1Active && level1Parallax != null) {
            level1Parallax.updateCamera(cameraX);
        } else if (!isLevel1Active && level2ParallaxDay != null) {
            level2ParallaxDay.updateCamera(cameraX);
        }
    }
    
    /**
     * Smooth camera movement
     */
    private void updateCamera(long deltaMillis) {
        float deltaSeconds = deltaMillis / 1000.0f;
        float cameraMovement = CAMERA_SPEED * deltaSeconds;
        
        cameraX += cameraMovement;
        
        // Clamp camera to level bounds
        if (cameraX < 0.0f) {
            cameraX = 0.0f;
        }
        if (cameraX > MAX_LEVEL_WIDTH) {
            cameraX = MAX_LEVEL_WIDTH;
        }
    }
    
    /**
     * Render level (parallax + tilemap + background)
     */
    public void render(Graphics2D g, int screenWidth, int screenHeight) {
        // Render black background
        renderBackgroundRaster(g, screenWidth, screenHeight);
        
        // Render parallax layers
        renderParallaxRaster(g, screenWidth, screenHeight);
        
        // Render HUD panel background
        renderHUDRaster(g, screenWidth, screenHeight);
    }
    
    /**
     * Render tiled black background
     */
    private void renderBackgroundRaster(Graphics2D g, int width, int height) {
        if (backgroundBlack == null) return;
        
        int tileW = backgroundBlack.getWidth();
        int tileH = backgroundBlack.getHeight();
        
        for (int y = 0; y < height; y += tileH) {
            for (int x = 0; x < width; x += tileW) {
                g.drawImage(backgroundBlack, x, y, null);
            }
        }
    }
    
    /**
     * Render parallax system with depth effect
     */
    private void renderParallaxRaster(Graphics2D g, int width, int height) {
        try {
            if (isLevel1Active && level1Parallax != null) {
                level1Parallax.render(g, width, height);
            } else if (!isLevel1Active && level2ParallaxDay != null) {
                level2ParallaxDay.render(g, width, height);
            }
        } catch (Exception e) {
            System.out.println("[LevelManager ERROR] Parallax render failed: " + e.getMessage());
        }
    }
    
    /**
     * Render HUD panel background bar
     */
    private void renderHUDRaster(Graphics2D g, int width, int height) {
        if (hudPanelImage == null) return;
        
        int hudY = height - hudPanelImage.getHeight();
        g.drawImage(hudPanelImage, 0, hudY, width, hudPanelImage.getHeight(), null);
    }
    
    /**
     * Switch to next level
     */
    public void switchLevel() {
        isLevel1Active = !isLevel1Active;
        cameraX = 0.0f;
        String levelName = isLevel1Active ? "Level 1" : "Level 2";
        System.out.println("[LevelManager] Switched to " + levelName);
    }
    
    /**
     * Get current tilemap
     */
    public TileMap getCurrentTileMap() {
        return isLevel1Active ? level1TileMap : level2TileMap;
    }
    
    /**
     * Get current parallax system
     */
    public AnimationAndSpriteLoader.ParallaxSystem getCurrentParallaxSystem() {
        return isLevel1Active ? level1Parallax : level2ParallaxDay;
    }
    
    // ==================== GETTERS ====================
    
    public int getCurrentLevel() {
        return currentLevel;
    }
    
    public boolean isLevel1Active() {
        return isLevel1Active;
    }
    
    public float getCameraX() {
        return cameraX;
    }
    
    public float getCameraY() {
        return cameraY;
    }
}
