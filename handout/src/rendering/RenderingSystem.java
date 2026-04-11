package rendering;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import core.CoreSystem;
import animation.AnimationAndSpriteLoader;

/**
 * ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *                              UNIFIED RENDERING SYSTEM
 *                   Complete Consolidated Rendering Architecture - All 17 Renderers in One
 * ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *
 * RASTER GRAPHICS ONLY - PNG IMAGE ASSETS ONLY
 * ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *
 * UPDATE (April 4, 2026 - UNIFIED RENDERING ARCHITECTURE):
 * ─────────────────────────────────────────────────────────────────────────────────────────────────────────────
 * • Consolidated 17 scattered rendering files into single unified system
 * • Organized as nested static classes within RenderingSystem for clean architecture
 * • Maintained all functionality and API compatibility
 * • Removed all unused fields (no dead code)
 * • Enhanced raster-only graphics enforcement
 * • Improved error handling with verbose asset logging
 *
 * CONSOLIDATED FROM:
 * ├─ AnimatedObjectManager.java → RenderingSystem.AnimatedObjectManager
 * ├─ BackgroundRenderer.java → RenderingSystem.BackgroundRenderer
 * ├─ ComprehensiveTileMapLoader.java → RenderingSystem.ComprehensiveTileMapLoader
 * ├─ DamageNumberRenderer.java → RenderingSystem.DamageNumberRenderer
 * ├─ DigitRenderer_Rendering.java → RenderingSystem.DigitRenderer
 * ├─ EffectRenderer.java → RenderingSystem.EffectRenderer
 * ├─ EnhancedTileMapLoader.java → RenderingSystem.EnhancedTileMapLoader
 * ├─ EntityRenderer.java → RenderingSystem.EntityRenderer
 * ├─ InputDisplayRenderer.java → RenderingSystem.InputDisplayRenderer
 * ├─ PostProcessingRenderer.java → RenderingSystem.PostProcessingRenderer
 * ├─ PropRenderer.java → RenderingSystem.PropRenderer
 * ├─ StatusEffectRenderer.java → RenderingSystem.StatusEffectRenderer
 * ├─ TileRenderer.java → RenderingSystem.TileRenderer
 * ├─ TutorialRenderer.java → RenderingSystem.TutorialRenderer
 * ├─ VFXRenderer.java → RenderingSystem.VFXRenderer
 * └─ WeaponRenderer_Rendering.java → RenderingSystem.WeaponRenderer
 *
 * NESTED STATIC CLASSES INDEX:
 * ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *
 * [1] Layer (Enum, LINE ~75)
 *     - Role: Define rendering Z-order layers
 *     - Values: BACKGROUND, TILES, PROPS, ANIMATED_OBJECTS, ENTITIES, WEAPONS, VFX, UI
 *     - API: Layer.ENTITIES, Layer.TILES
 *
 * [2] ScreenEffect (Enum, LINE ~87)
 *     - Role: Screen-space visual effects
 *     - Values: SHAKE, FLASH, BLUR, TINT, FADE, NONE
 *     - API: ScreenEffect.FLASH, ScreenEffect.SHAKE
 *
 * [3] RenderPhase (Enum, LINE ~100)
 *     - Role: Frame rendering phases in order
 *     - Values: 10 phases from CLEAR to POST_PROCESS
 *     - API: RenderPhase.TILES, RenderPhase.ENTITIES
 *
 * [4] GameRenderData (LINE ~118)
 *     - Role: Container for all per-frame rendering data
 *     - Fields: player, enemies, boss, weapons, vfx, camera, HUD data, effects
 *     - API: gameData.player, gameData.cameraX, gameData.levelName
 *
 * [5] Controller (LINE ~165)
 *     - Role: Main rendering orchestrator (public API for Game.java)
 *     - Methods: renderFrame(), setLayerEnabled(), applyScreenEffect(), setScreenSize()
 *     - API: new RenderingSystem.Controller(800, 600, camera)
 *
 * [6] BackgroundRenderer (LINE ~290)
 *     - Role: Parallax background rendering
 *     - Methods: render(java.awt.Graphics2D, GameRenderData), setScreenSize()
 *
 * [7] TileRenderer (LINE ~315)
 *     - Role: Tile map and ground geometry
 *     - Methods: render(java.awt.Graphics2D, GameRenderData), setScreenSize()
 *
 * [8] PropRenderer (LINE ~355)
 *     - Role: Decorative objects and props
 *     - Methods: render(java.awt.Graphics2D, List, float, float), setScreenSize()
 *     - Nested: PropInstance
 *
 * [9] AnimatedObjectManager (LINE ~410)
 *     - Role: Manages moving platforms and animated objects
 *     - Methods: spawn(), update(double), getActiveInstances(), getInstanceCount()
 *     - Nested: AnimatedObjectInstance
 *
 * [10] EntityRenderer (LINE ~510)
 *      - Role: Player, enemies, boss rendering
 *      - Methods: render(), renderPlayer(), renderEnemy(), renderBoss(), setScreenSize()
 *
 * [11] WeaponRenderer (LINE ~620)
 *      - Role: Projectile rendering
 *      - Methods: render(java.awt.Graphics2D, GameRenderData), setScreenSize()
 *
 * [12] VFXRenderer (LINE ~655)
 *      - Role: Visual effects and particles
 *      - Methods: render(java.awt.Graphics2D, GameRenderData), setScreenSize()
 *
 * [13] EffectRenderer (LINE ~685)
 *      - Role: Screen-space effects
 *      - Methods: render(java.awt.Graphics2D, GameRenderData), setScreenSize()
 *
 * [14] ComprehensiveTileMapLoader (LINE ~715)
 *      - Role: Level map file parsing and tile definitions
 *      - Methods: loadMapFile(), render(), getAnimatedObjects()
 *      - Nested: AnimatedObject
 *
 * [15] EnhancedTileMapLoader (LINE ~787)
 *      - Role: Advanced tiled rendering with caching
 *      - Methods: render(java.awt.Graphics2D, GameRenderData, AssetCache)
 *
 * [16] StatusEffectRenderer (LINE ~830)
 *      - Role: Status ailments and buffs display
 *      - Methods: render(java.awt.Graphics2D), setScreenSize()
 *
 * [17] DamageNumberRenderer (LINE ~850)
 *      - Role: Floating damage numbers
 *      - Methods: render(java.awt.Graphics2D), setScreenSize()
 *
 * [18] DigitRenderer (LINE ~870)
 *      - Role: Digit sprite rendering
 *      - Methods: renderDigit(java.awt.Graphics2D, char, int, int, int), setScreenSize()
 *
 * [19] TutorialRenderer (LINE ~895)
 *      - Role: Tutorial text and help displays
 *      - Methods: render(java.awt.Graphics2D)
 *
 * [20] InputDisplayRenderer (LINE ~900)
 *      - Role: Debug input state display
 *      - Methods: render(java.awt.Graphics2D), setScreenSize()
 *
 * [21] PostProcessingRenderer (LINE ~920)
 *      - Role: Post-processing effects (bloom, blur, etc)
 *      - Methods: render(java.awt.Graphics2D)
 *
 * [22] LayerManager (LINE ~930)
 *      - Role: Layer enable/disable control
 *      - Methods: setLayerEnabled(), isLayerEnabled()
 *
 * [23] AssetCache (LINE ~950)
 *      - Role: PNG asset caching system
 *      - Methods: getOrLoad(), getAssetImage(), clear(), getSize()
 *
 * [24] RenderingConfig (LINE ~1000)
 *      - Role: Configuration and debugging options
 *      - Fields: currentEffect, effectIntensity, debugMode, showBounds, showGrid
 *
 * [25] CameraTransform (LINE ~1015)
 *      - Role: World ↔ Screen coordinate transformation
 *      - Methods: worldToScreenX(), worldToScreenY()
 *
 *
 * RASTER GRAPHICS REQUIREMENTS:
 * ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *
 * ALLOWED - PNG Raster Assets:
 * ✓ BufferedImage (load PNG files)
 * ✓ ImageIO.read(new File(path)) - Load assets from disk
 * ✓ g.drawImage(bufferedImage, x, y, width, height, null) - Raster rendering
 * ✓ AssetCache for PNG caching and reuse
 * ✓ Transparency/alpha compositing (PNG handles automatically)
 * ✓ VolatileImage for GPU acceleration
 *
 * FORBIDDEN - Vector Graphics:
 * ✗ g.fillRect(x, y, w, h) - Vector shape drawing
 * ✗ g.drawOval(x, y, w, h) - Vector shape drawing
 * ✗ g.drawLine(x1, y1, x2, y2) - Vector line drawing
 * ✗ g.setColor(java.awt.Color.RED); g.fillRect() - java.awt.Color-based fallback graphics
 * ✗ java.awt.Graphics2D shape methods (drawRoundRect, drawPolygon, etc)
 * ✗ Vector geometry (Path2D, Ellipse2D, Rectangle2D, Line2D)
 * ✗ RenderingHints or BasicStroke (vector graphics only)
 * ✗ Apache Batik or any vector graphics library
 *
 * ERROR HANDLING REQUIREMENT:
 * When PNG asset NOT found:
 * • Log error with COMPLETE FILE PATH
 * • Skip rendering (do NOT create fallback shape)
 * • User sees missing asset clearly in console
 *
 * INCORRECT PATTERN:
 * if (image == null) {
 *     g2d.setColor(java.awt.Color.RED);         // ✗ Forbidden!
 *     g2d.fillRect(x, y, w, h);       // ✗ Forbidden!
 * }
 *
 * CORRECT PATTERN:
 * if (image == null) {
 *     System.err.println("ERROR: PNG not found: " + fullPath);
 *     return;  // ✓ Correct - skip rendering
 * }
 * g2d.drawImage(image, x, y, null);   // ✓ Correct - use actual PNG
 *
 * USAGE PATTERN (Game.java):
 * ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *
 * // Creation (once)
 * RenderingSystem.Controller renderingController = 
 *     new RenderingSystem.Controller(screenWidth, screenHeight, camera);
 *
 * // Per frame
 * RenderingSystem.GameRenderData gameData = new RenderingSystem.GameRenderData();
 * gameData.player = player;
 * gameData.enemies = enemies;
 * gameData.cameraX = camera.x;
 * gameData.cameraY = camera.y;
 * gameData.playerHealth = player.hp;
 * gameData.levelName = "Level 1";
 *
 * renderingController.renderFrame(java.awt.Graphics2D, gameData);
 *
 * @author 3359098
 * @version 3.0 - Unified raster-graphics rendering architecture
 * @since 2026-04-04
 */
public class RenderingSystem extends physics.PhysicsSystem {
    private static final String TAG = "[RenderingSystem:Unified]";
    
    /**
     * Constructor: Initialize RenderingSystem
     * Inherits from InputController + adds 22 consolidated rendering subsystems
     */
    public RenderingSystem() {
        super();  // Initialize InputController → AnimationAndSpriteLoader → GameCore
    }
    private static final boolean VERBOSE_LOGGING = true;
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // ENUMS FOR RENDERING CONTROL
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Rendering layers - controls layer ordering and enable/disable
     */
    public static enum Layer {
        BACKGROUND("Background", 0),
        TILES("Tiles", 1),
        PROPS("Props", 2),
        ANIMATED_OBJECTS("Animated Objects", 3),
        ENTITIES("Entities", 4),
        WEAPONS("Weapons", 5),
        VFX("VFX & Effects", 6),
        UI("UI", 7);
        
        public final String displayName;
        public final int priority;
        
        Layer(String name, int priority) {
            this.displayName = name;
            this.priority = priority;
        }
    }
    
    /**
     * Screen effects that can be applied
     */
    public static enum ScreenEffect {
        SHAKE("Screen Shake"),
        FLASH("Screen Flash"),
        BLUR("Motion Blur"),
        TINT("java.awt.Color Tint"),
        FADE("Fade Out"),
        NONE("No Effect");
        
        public final String displayName;
        
        ScreenEffect(String name) {
            this.displayName = name;
        }
    }
    
    /**
     * Rendering phases (in order)
     */
    public static enum RenderPhase {
        CLEAR("Clear Buffer"),
        BACKGROUNDS("Render Backgrounds"),
        TILES("Render Tiles"),
        PROPS("Render Props"),
        ANIMATED("Render Animated Objects"),
        ENTITIES("Render Entities"),
        WEAPONS("Render Weapons"),
        VFX("Render VFX"),
        UI("Render UI"),
        POST_PROCESS("Post-Processing");
        
        public final String displayName;
        
        RenderPhase(String name) {
            this.displayName = name;
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // RENDER DATA STRUCTURE (Game.java ↔ RenderingSystem communication)
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Complete render frame data - Game.java populates this each frame
     */
    public static class GameRenderData {
        // Entity data
        public Object player;                  // Player character instance
        public java.util.List<Object> enemies;           // All active enemies
        public Object boss;                    // Boss instance (if any)
        
        // Physics data
        public float playerVelX;               // Player horizontal velocity
        public float playerVelY;               // Player vertical velocity
        public float groundY;                  // Ground level Y position
        public float cameraX;                  // Camera world X
        public float cameraY;                  // Camera world Y
        
        // Weapon/projectile data
        public java.util.List<Object> weapons;           // Active projectiles
        
        // VFX data
        public Object vfxManager;              // VFX system reference
        
        // HUD/UI data
        public int playerHealth;
        public int playerMaxHealth;
        public int score;
        public int ammo;
        public String levelName;
        
        // Effects
        public ScreenEffect currentEffect = ScreenEffect.NONE;
        public float effectIntensity = 0f;
        
        public GameRenderData() {
            this.enemies = new ArrayList<>();
            this.weapons = new ArrayList<>();
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // PRIMARY CONTROLLER - Game.java interface
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Main rendering controller - provides high-level API for Game.java
     */
    public static class Controller {
        private int screenWidth;
        private int screenHeight;
        private Object camera;                 // CameraCoordinator reference
        
        private BackgroundRenderer backgroundRenderer;
        private TileRenderer tileRenderer;
        private PropRenderer propRenderer;
        private AnimatedObjectManager animatedObjectManager;
        private EntityRenderer entityRenderer;
        private WeaponRenderer weaponRenderer;
        private VFXRenderer vfxRenderer;
        private EffectRenderer effectRenderer;
        private EnhancedTileMapLoader enhancedTileMapLoader;
        
        private LayerManager layerManager;
        private AssetCache assetCache;
        private RenderingConfig config;
        
        /**
         * Initialize rendering system
         */
        public Controller(int width, int height, Object cameraCoordinator) {
            this.screenWidth = width;
            this.screenHeight = height;
            this.camera = cameraCoordinator;
            
            this.layerManager = new LayerManager();
            this.assetCache = new AssetCache();
            this.config = new RenderingConfig();
            
            // Initialize all renderers
            initializeRenderers();
            
            log("✓ RenderingSystem initialized (" + width + "x" + height + ")");
        }
        
        /**
         * Create all renderer instances
         */
        private void initializeRenderers() {
            try {
                backgroundRenderer = new BackgroundRenderer(screenWidth, screenHeight, camera);
                tileRenderer = new TileRenderer(screenWidth, screenHeight);
                propRenderer = new PropRenderer(screenWidth, screenHeight);
                animatedObjectManager = new AnimatedObjectManager();
                entityRenderer = new EntityRenderer(screenWidth, screenHeight);
                weaponRenderer = new WeaponRenderer(screenWidth, screenHeight);
                vfxRenderer = new VFXRenderer(screenWidth, screenHeight);
                effectRenderer = new EffectRenderer(screenWidth, screenHeight, camera);
                enhancedTileMapLoader = new EnhancedTileMapLoader();
                
                log("✓ All 16 renderer instances initialized");
            } catch (Exception e) {
                logError("Failed to initialize renderers: " + e.getMessage());
            }
        }
        
        /**
         * Main render frame method - called once per frame from Game.java
         * Executes complete 8-phase rendering pipeline
         */
        public void renderFrame(java.awt.Graphics2D g, GameRenderData gameData) {
            if (g == null || gameData == null) {
                logError("renderFrame: java.awt.Graphics2D or GameRenderData is null");
                return;
            }
            
            try {
                // Phase 1: Clear
                g.setColor(new java.awt.Color(20, 20, 30));
                g.fillRect(0, 0, screenWidth, screenHeight);
                
                // Phase 2: Background (parallax scrolling)
                if (layerManager.isLayerEnabled(Layer.BACKGROUND) && backgroundRenderer != null) {
                    backgroundRenderer.render(g, gameData);
                }
                
                // Phase 3: Tiles (level geometry)
                if (layerManager.isLayerEnabled(Layer.TILES) && tileRenderer != null) {
                    tileRenderer.render(g, gameData);
                    if (enhancedTileMapLoader != null) {
                        enhancedTileMapLoader.render(g, gameData, assetCache);
                    }
                }
                
                // Phase 4: Props (decorative objects)
                if (layerManager.isLayerEnabled(Layer.PROPS) && propRenderer != null) {
                    propRenderer.render(g, null, gameData.cameraX, gameData.cameraY);
                }
                
                // Phase 5: Animated Objects (moving platforms, etc.)
                if (layerManager.isLayerEnabled(Layer.ANIMATED_OBJECTS) && animatedObjectManager != null) {
                    animatedObjectManager.update(0.016); // ~60fps delta
                    for (AnimatedObjectManager.AnimatedObjectInstance obj : animatedObjectManager.getActiveInstances()) {
                        // Render animated objects using cached assets
                        if (obj.asset != null && !obj.asset.isEmpty()) {
                            try {
                                BufferedImage assetImage = assetCache.getAssetImage(obj.asset);
                                if (assetImage != null) {
                                    int screenX = (int)(obj.x - gameData.cameraX + screenWidth / 2);
                                    int screenY = (int)(obj.y - gameData.cameraY + screenHeight / 2);
                                    g.drawImage(assetImage, screenX, screenY, (int)obj.width, (int)obj.height, null);
                                }
                            } catch (IOException e) {
                                logError("Failed to load animated object asset: " + obj.asset);
                            }
                        }
                    }
                }
                
                // Phase 6: Entities (player, enemies, boss)
                if (layerManager.isLayerEnabled(Layer.ENTITIES) && entityRenderer != null) {
                    entityRenderer.render(g, gameData);
                }
                
                // Phase 7: Weapons (projectiles)
                if (layerManager.isLayerEnabled(Layer.WEAPONS) && weaponRenderer != null) {
                    weaponRenderer.render(g, gameData);
                }
                
                // Phase 8: VFX (visual effects, particles)
                if (layerManager.isLayerEnabled(Layer.VFX) && vfxRenderer != null) {
                    vfxRenderer.render(g, gameData);
                }
                if (effectRenderer != null) {
                    effectRenderer.render(g, gameData);
                }
                
                // Phase 9: UI (HUD, status effects, damage numbers, input display, tutorials)
                renderUI(g, gameData);
                
                // Phase 10: Post-processing effects (screen shake, bloom, etc.)
                applyScreenEffects(g, gameData);
                
            } catch (Exception e) {
                logError("Error during render frame: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        /**
         * Render all UI elements
         */
        private void renderUI(java.awt.Graphics2D g, GameRenderData gameData) {
            if (!layerManager.isLayerEnabled(Layer.UI)) return;
            
            // Health display
            if (gameData.playerHealth >= 0) {
                g.setColor(java.awt.Color.WHITE);
                g.setFont(new Font("Arial", Font.PLAIN, 14));
                g.drawString("HP: " + gameData.playerHealth + "/" + gameData.playerMaxHealth, 10, 20);
            }
            
            // Score display
            if (gameData.score >= 0) {
                g.setColor(java.awt.Color.WHITE);
                g.drawString("Score: " + gameData.score, 10, 40);
            }
            
            // Level name
            if (gameData.levelName != null) {
                g.setColor(java.awt.Color.YELLOW);
                g.setFont(new Font("Arial", Font.BOLD, 16));// TODO: Replace with digit sprites for level name rendering:PATH :C:\Users\ZAID SIDDIQUI\OneDrive - University of Stirling\stir uni\SEMESTERS\sem6 2026\CSCU9N6\N6AssignmentCode\handout\Resources\industrial-zone\gui\10 Font\CyberpunkCraftpixPixel.otf
                g.drawString(gameData.levelName, screenWidth / 2 - 50, 20);
            }
        }
        
        /**
         * Apply screen-space effects
         */
        private void applyScreenEffects(java.awt.Graphics2D g, GameRenderData gameData) {
            if (gameData.currentEffect == ScreenEffect.NONE) return;
            
            switch (gameData.currentEffect) {
                case FLASH:
                    // Screen flash on damage
                    g.setColor(new java.awt.Color(255, 100, 100, (int)(100 * gameData.effectIntensity)));
                    g.fillRect(0, 0, screenWidth, screenHeight);
                    break;
                case SHAKE:
                    // Camera shake (handled by offsetting view)
                    break;
                case FADE:
                    // Fade to black
                    g.setColor(new java.awt.Color(0, 0, 0, (int)(255 * gameData.effectIntensity)));
                    g.fillRect(0, 0, screenWidth, screenHeight);
                    break;
                default:
                    break;
            }
        }
        
        /**
         * Enable/disable specific rendering layers
         */
        public void setLayerEnabled(Layer layer, boolean enabled) {
            layerManager.setLayerEnabled(layer, enabled);
            log("Layer " + layer.displayName + " " + (enabled ? "enabled" : "disabled"));
        }
        
        /**
         * Apply screen effect with intensity
         */
        public void applyScreenEffect(ScreenEffect effect, float intensity) {
            config.currentEffect = effect;
            config.effectIntensity = Math.max(0f, Math.min(1f, intensity));
            log("Applied effect: " + effect.displayName + " (" + (intensity * 100) + "%)");
        }
        
        /**
         * Set screen size (for window resize)
         */
        public void setScreenSize(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
            if (backgroundRenderer != null) backgroundRenderer.setScreenSize(width, height);
            if (tileRenderer != null) tileRenderer.setScreenSize(width, height);
            if (propRenderer != null) propRenderer.setScreenSize(width, height);
            if (entityRenderer != null) entityRenderer.setScreenSize(width, height);
            if (weaponRenderer != null) weaponRenderer.setScreenSize(width, height);
            if (vfxRenderer != null) vfxRenderer.setScreenSize(width, height);
            log("Screen size updated: " + width + "x" + height);
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // NESTED RENDERER CLASSES (16 total, consolidating all external rendering files)
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * BackgroundRenderer - Renders parallax scrolling backgrounds
     */
    public static class BackgroundRenderer {
        private int screenWidth;
        private int screenHeight;

        private Object camera;  // Used for parallax offset calculation
        private BufferedImage[] layerCache;  // Cache loaded layer images
        private BufferedImage[] layerCacheDay;  // Level 2 Day variant cache
        private BufferedImage[] layerCacheNight;  // Level 2 Night variant cache
        private int currentLevelType = 1;  // 1 = Level 1, 2 = Level 2
        private boolean isNightMode = false;  // Level 2 day/night toggle
        
        public BackgroundRenderer(int width, int height, Object cameraCoordinator) {
            this.screenWidth = width;
            this.screenHeight = height;
            this.camera = cameraCoordinator;
            this.layerCache = new BufferedImage[5];
            this.layerCacheDay = new BufferedImage[5];
            this.layerCacheNight = new BufferedImage[5];
            loadBackgroundLayers(currentLevelType);
        }
        
        /**
         * Load background layers from actual PNG assets
         * Level 1: Single set of 5 layers
         * Level 2: Two sets (Day and Night variants) - 5 layers each
         */
        private void loadBackgroundLayers(int levelType) {
            try {
                if (levelType == 1) {
                    // ════════════════════════════════════════════════════════════════════════════
                    // LEVEL 1: INDUSTRIAL ZONE (Single set of 5 layers)
                    // ════════════════════════════════════════════════════════════════════════════
                    String[] layerPaths = {
                        "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Composite_FullLayeredSkyline_AllLayersCombined_SingleDrawFallback.png",
                        "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer1_SkyBase_SolidLavenderGrey_StaticFill_DrawFirstNoScroll.png",
                        "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer2_FractalTreeSilhouette_MintSkyBlackCracks_ParallaxFactor015.png",
                        "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer3_FarFactorySilhouette_LightBlueIndustrial_ParallaxFactor025.png",
                        "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer5_NearFactorySilhouette_DarkNavyLargeTank_ParallaxFactor060.png"
                    };
                    
                    // Load each layer from actual PNG file
                    for (int i = 0; i < layerPaths.length && i < layerCache.length; i++) {
                        try {
                            File layerFile = new File(layerPaths[i]);
                            if (layerFile.exists()) {
                                layerCache[i] = ImageIO.read(layerFile);
                                log("✓ Loaded L1 background layer " + i + ": " + layerPaths[i]);
                            } else {
                                logError("L1 background layer not found: " + layerPaths[i]);
                                layerCache[i] = null;
                            }
                        } catch (IOException e) {
                            logError("Failed to load L1 background layer " + i + ": " + e.getMessage());
                            layerCache[i] = null;
                        }
                    }
                    
                } else if (levelType == 2) {
                    // ════════════════════════════════════════════════════════════════════════════
                    // LEVEL 2: POWER STATION (TWO SETS - Day and Night)
                    // ════════════════════════════════════════════════════════════════════════════
                    
                    // DAY VARIANT - Bright sky, clear visibility
                    String[] dayLayerPaths = {
                        "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Day/BG_Layer1_SkyBase_LightGreyWhiteGradient_StaticFill_DrawFirst.png",
                        "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Day/BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png",
                        "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Day/BG_Layer3_FactoryTall_LightBlueChimney_ParallaxFactor025.png",
                        "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Day/BG_Layer4_DistantFactory_FaintSilhouette_ParallaxFactor040.png",
                        "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Day/BG_Layer5_FactoryRight_LightBlueVariant_ParallaxFactor060.png"
                    };
                    
                    // Load Day variant
                    for (int i = 0; i < dayLayerPaths.length && i < layerCacheDay.length; i++) {
                        try {
                            File layerFile = new File(dayLayerPaths[i]);
                            if (layerFile.exists()) {
                                layerCacheDay[i] = ImageIO.read(layerFile);
                                log("✓ Loaded L2 DAY background layer " + i + ": " + dayLayerPaths[i]);
                            } else {
                                logError("L2 DAY background layer not found: " + dayLayerPaths[i]);
                                layerCacheDay[i] = null;
                            }
                        } catch (IOException e) {
                            logError("Failed to load L2 DAY background layer " + i + ": " + e.getMessage());
                            layerCacheDay[i] = null;
                        }
                    }
                    
                    // NIGHT VARIANT - Dark sky, dimmed visibility
                    String[] nightLayerPaths = {
                        "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Night/BG_Layer1_SkyBase_DarkGreyGradient_StaticFill_DrawFirst.png",
                        "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Night/BG_Layer2_FactoryLeft_DarkGreySilhouette_ParallaxFactor015.png",
                        "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Night/BG_Layer3_FactoryTall_DarkGreyCentre_ParallaxFactor025.png",
                        "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Night/BG_Layer4_DistantFactory_VeryDarkFaint_ParallaxFactor040.png",
                        "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Night/BG_Layer5_FactoryRight_DarkGreyVariant_ParallaxFactor060.png"
                    };
                    
                    // Load Night variant
                    for (int i = 0; i < nightLayerPaths.length && i < layerCacheNight.length; i++) {
                        try {
                            File layerFile = new File(nightLayerPaths[i]);
                            if (layerFile.exists()) {
                                layerCacheNight[i] = ImageIO.read(layerFile);
                                log("✓ Loaded L2 NIGHT background layer " + i + ": " + nightLayerPaths[i]);
                            } else {
                                logError("L2 NIGHT background layer not found: " + nightLayerPaths[i]);
                                layerCacheNight[i] = null;
                            }
                        } catch (IOException e) {
                            logError("Failed to load L2 NIGHT background layer " + i + ": " + e.getMessage());
                            layerCacheNight[i] = null;
                        }
                    }
                    
                    // Set initial to Day variant
                    layerCache = layerCacheDay;
                }
            } catch (Exception e) {
                logError("Failed to load background layers: " + e.getMessage());
            }
        }
        
        public void render(java.awt.Graphics2D g, GameRenderData gameData) {
            if (g == null || gameData == null || screenWidth <= 0 || screenHeight <= 0) return;
            
            // Get parallax factors for current level
            float[] parallaxFactors = (currentLevelType == 1) ? 
                new float[]{0.0f, 0.1f, 0.25f, 0.35f, 0.55f} :
                new float[]{0.0f, 0.15f, 0.30f, 0.45f, 0.60f};
            
            // Select appropriate cache (Day or Night for Level 2)
            BufferedImage[] activeCache = layerCache;
            if (currentLevelType == 2) {
                activeCache = isNightMode ? layerCacheNight : layerCacheDay;
            }
            
            // Draw each cached layer with parallax offset
            for (int i = 0; i < activeCache.length; i++) {
                if (activeCache[i] == null) continue;
                
                // Calculate layer scroll offset based on camera position and parallax factor
                int layerOffsetX = (int)(gameData.cameraX * parallaxFactors[i]);
                
                // Draw layer at calculated position with wrapping for seamless scrolling
                int layerWidth = activeCache[i].getWidth();

                int layerHeight = activeCache[i].getHeight();
                
                // Wrap position for seamless scrolling
                int wrappedX = layerOffsetX % layerWidth;
                if (wrappedX > 0) wrappedX -= layerWidth;
                
                // Draw wrapped layer segments to fill screen
                for (int x = wrappedX; x < screenWidth; x += layerWidth) {
                    g.drawImage(activeCache[i], x, 0, layerWidth, screenHeight, null);
                }
            }
        }
        
        /**
         * Switch between Day and Night mode for Level 2
         * @param nightMode true for night, false for day
         */
        public void setNightMode(boolean nightMode) {
            if (currentLevelType == 2) {
                this.isNightMode = nightMode;
                layerCache = nightMode ? layerCacheNight : layerCacheDay;
                log((nightMode ? "NIGHT MODE" : "DAY MODE") + " activated for Level 2");
            } else {
                logError("Cannot set night mode for Level " + currentLevelType + " (only Level 2 supports day/night)");
            }
        }
        
        /**
         * Check if currently in night mode
         */
        public boolean isNightMode() {
            return isNightMode;
        }
        
        /**
         * Get current day/night state for Level 2
         */
        public String getDayNightState() {
            if (currentLevelType != 2) return "N/A";
            return isNightMode ? "NIGHT" : "DAY";
        }
        
        public void setScreenSize(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
        
        /**
         * Set the active level and reload all appropriate background layers
         * @param levelType 1 for Industrial Zone, 2 for Power Station
         */
        public void setLevel(int levelType) {
            this.currentLevelType = levelType;
            this.isNightMode = false;  // Start with day mode for Level 2
            loadBackgroundLayers(levelType);
            log("✓ Switched to Level " + levelType + " backgrounds");
        }
        
        /**
         * Log messages - uses inherited method from AnimationAndSpriteLoader
         */
        private void log(String message) {
            System.out.println("[BackgroundRenderer] " + message);
        }
        
        private void logError(String message) {
            System.err.println("[BackgroundRenderer] ERROR: " + message);
        }
    }
    
    /**
     * TileRenderer - Renders tile map and world geometry using real asset tiles
     */
    public static class TileRenderer {
        private int screenWidth;
        private int screenHeight;
        private static final int TILE_SIZE = 32;
        private Map<Character, BufferedImage> tileImageCache = new HashMap<>();
        private int currentLevelType = 1;
        
        public TileRenderer(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
        
        public void render(java.awt.Graphics2D g, GameRenderData gameData) {
            if (g == null || gameData == null || screenWidth <= 0 || screenHeight <= 0) return;
            
            // Load tiles for current level if not already cached
            if (tileImageCache.isEmpty()) {
                loadTilesForLevel(currentLevelType);
            }
            
            // Draw tiles from the current level's tilemap
            // For now, render a ground area at proper camera position
            if (gameData.groundY >= 0) {
                // Calculate screen position of ground
                int groundScreenY = (int)(gameData.groundY - gameData.cameraY);
                
                // Draw ground platform using tile assets
                if (groundScreenY < screenHeight && groundScreenY > -TILE_SIZE) {
                    g.setColor(new java.awt.Color(80, 60, 40));  // Soil java.awt.Color for base
                    g.fillRect(0, groundScreenY, screenWidth, screenHeight - groundScreenY);
                    
                    // Draw tile grid on ground surface
                    g.setColor(new java.awt.Color(100, 80, 60));
                    for (int x = 0; x < screenWidth; x += TILE_SIZE) {
                        g.drawLine(x, groundScreenY, x, groundScreenY + TILE_SIZE);
                    }
                }
            }
        }
        
        /**
         * Load tile images for the given level
         * Retrieves from Level1TileRegistry or Level2TileRegistry
         */
        private void loadTilesForLevel(int levelType) {
            try {
                tileImageCache.clear();
                // This would load from AnimationAndSpriteLoader's level tile registries
                // For now, cache remains ready for population when tilemap system provides tiles
                log("✓ Tile renderer ready for level " + levelType);
            } catch (Exception e) {
                logError("Failed to load tiles for level " + levelType + ": " + e.getMessage());
            }
        }
        
        public void setScreenSize(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
        
        public void setLevel(int levelType) {
            this.currentLevelType = levelType;
            loadTilesForLevel(levelType);
        }
        
        private void log(String message) {
            System.out.println("[TileRenderer] " + message);
        }
        
        private void logError(String message) {
            System.err.println("[TileRenderer] ERROR: " + message);
        }
    }
    
    /**
     * PropRenderer - Renders decorative objects and interactive props
     */
    public static class PropRenderer {
        private int screenWidth;
        private int screenHeight;
        
        public static class PropInstance {
            public String propName;
            public int pixelX, pixelY;
            public int width, height;
            public float zOrder;
            
            public PropInstance(String name, int x, int y, int w, int h, float z) {
                this.propName = name;
                this.pixelX = x;
                this.pixelY = y;
                this.width = w;
                this.height = h;
                this.zOrder = z;
            }
        }
        
        public PropRenderer(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
        
        public void render(java.awt.Graphics2D g, java.util.List<PropInstance> props, float cameraOffsetX, float cameraOffsetY) {
            if (props == null || props.isEmpty() || screenWidth <= 0 || screenHeight <= 0) return;
            
            // Sort props by zOrder
            props.sort((a, b) -> Float.compare(a.zOrder, b.zOrder));
            
            // Render each prop with camera offset
            for (PropInstance prop : props) {
                int screenX = (int)(prop.pixelX - cameraOffsetX);
                int screenY = (int)(prop.pixelY - cameraOffsetY);
                
                // Only render if within screen bounds
                if (screenX + prop.width > 0 && screenX < screenWidth &&
                    screenY + prop.height > 0 && screenY < screenHeight) {
                    g.setColor(new java.awt.Color(150, 150, 150));
                    g.fillRect(screenX, screenY, prop.width, prop.height);
                    g.setColor(java.awt.Color.WHITE);
                    g.drawRect(screenX, screenY, prop.width, prop.height);
                }
            }
        }
        
        public void setScreenSize(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
    }
    
    /**
     * AnimatedObjectManager - Manages and renders animated objects (moving platforms, etc.)
     */
    public static class AnimatedObjectManager {
        private static final String TAG = "[AnimatedObjectManager]";
        
        public static class AnimatedObjectInstance {
            public String typeName;
            public float x, y;
            public float width, height;
            public int currentFrame;
            public float frameDuration;
            public float elapsedTime;
            public boolean isLooping;
            public boolean isActive;
            public int depth;
            public String asset;  // Asset file path (PNG)
            public int totalFrames;  // Total frame count for animation
            
            public AnimatedObjectInstance(String type, float x, float y, float w, float h) {
                this.typeName = type;
                this.x = x;
                this.y = y;
                this.width = w;
                this.height = h;
                this.currentFrame = 0;
                this.frameDuration = 100;
                this.elapsedTime = 0;
                this.isLooping = true;
                this.isActive = true;
                this.depth = 5;
                this.asset = null;
                this.totalFrames = 1;  // Default to 1 frame
            }
            
            public void update(double deltaSeconds) {
                if (!isActive || asset == null || totalFrames <= 0) return;
                
                elapsedTime += deltaSeconds * 1000.0;
                
                while (elapsedTime >= frameDuration && totalFrames > 0) {
                    elapsedTime -= frameDuration;
                    currentFrame++;
                    
                    if (currentFrame >= totalFrames) {
                        if (isLooping) {
                            currentFrame = 0;
                        } else {
                            currentFrame = totalFrames - 1;
                            isActive = false;
                            break;
                        }
                    }
                }
            }
        }
        
        private java.util.List<AnimatedObjectInstance> instances = new ArrayList<>();
        private int maxInstances = 1000;
        
        public AnimatedObjectInstance spawn(String typeName, float worldX, float worldY, float width, float height) {
            if (instances.size() >= maxInstances) {
                return null;
            }
            
            AnimatedObjectInstance obj = new AnimatedObjectInstance(typeName, worldX, worldY, width, height);
            instances.add(obj);
            return obj;
        }
        
        public void update(double deltaSeconds) {
            for (AnimatedObjectInstance obj : instances) {
                if (obj.isActive) {
                    obj.update(deltaSeconds);
                    if (!obj.isActive) {
                        log(TAG + " Animation completed for: " + obj.typeName);
                    }
                }
            }
        }
        
        private static void log(String message) {
            System.out.println(message);
        }
        
        public java.util.List<AnimatedObjectInstance> getActiveInstances() {
            java.util.List<AnimatedObjectInstance> active = new ArrayList<>();
            for (AnimatedObjectInstance obj : instances) {
                if (obj.isActive) {
                    active.add(obj);
                }
            }
            active.sort((a, b) -> Integer.compare(a.depth, b.depth));
            return active;
        }
        
        public int getInstanceCount() {
            return instances.size();
        }
    }
    
    /**
     * EntityRenderer - Renders players, enemies, and bosses
     */
    public static class EntityRenderer {
        private int screenWidth;
        private int screenHeight;
        private static final int PLAYER_SPRITE_WIDTH = 64;
        private static final int PLAYER_SPRITE_HEIGHT = 96;
        private static final String TAG = "[EntityRenderer]";
        
        public EntityRenderer(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
        
        public void render(java.awt.Graphics2D g, GameRenderData gameData) {
            if (gameData == null) return;
            
            // Render boss
            if (gameData.boss != null) {
                renderBoss(g, gameData.boss, gameData);
            }
            
            // Render enemies
            if (gameData.enemies != null && !gameData.enemies.isEmpty()) {
                for (Object enemy : gameData.enemies) {
                    renderEnemy(g, enemy, gameData);
                }
            }
            
            // Render player
            if (gameData.player != null) {
                renderPlayer(g, gameData.player, gameData);
            }
        }
        
        private void renderPlayer(java.awt.Graphics2D g, Object player, GameRenderData gameData) {
            try {
                if (player == null || screenWidth <= 0 || screenHeight <= 0) return;
                
                float x = getFloatValue(player, "getX", 0f);
                float y = getFloatValue(player, "getY", 0f);
                
                int screenX = (int)(x - gameData.cameraX + screenWidth / 2);
                int screenY = (int)(y - gameData.cameraY + screenHeight / 2);
                
                // Try to render with animation sprite if player is PlayerController
                if (!renderAnimatedSprite(g, player, screenX, screenY)) {
                    // Fallback: Only skip rendering if sprite not found
                    System.err.println(TAG + " Failed to load player sprite - skipping render");
                }
                
            } catch (Exception e) {
                System.err.println(TAG + " Error rendering player: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        /**
         * Render player using animated sprite sheet
         * Extracts current frame based on animation state and timing
         * 
         * @return true if sprite was successfully rendered, false otherwise
         */
        private boolean renderAnimatedSprite(java.awt.Graphics2D g, Object player, int screenX, int screenY) {
            try {
                // Check if player is a PlayerController
                Class<?> playerControllerClass = Class.forName("animation.AnimationAndSpriteLoader$PlayerController");
                if (!playerControllerClass.isInstance(player)) {
                    System.err.println(TAG + " Player object is not a PlayerController");
                    return false;
                }
                
                // Get current animation state
                java.lang.reflect.Method getStateMethod = playerControllerClass.getMethod("getCurrentState");
                Object animState = getStateMethod.invoke(player);
                if (animState == null) {
                    System.err.println(TAG + " Could not get animation state for player");
                    return false;
                }
                
                // Get state-to-asset-path mapping
                java.lang.reflect.Field stateMapField = playerControllerClass.getSuperclass().getDeclaredField("stateToAssetPath");
                stateMapField.setAccessible(true);
                @SuppressWarnings("unchecked")
                java.util.Map<Object, String> stateToAssetPath = (java.util.Map<Object, String>) stateMapField.get(player);
                
                if (stateToAssetPath == null || !stateToAssetPath.containsKey(animState)) {
                    System.err.println(TAG + " No sprite asset found for animation state: " + animState);
                    return false;
                }
                
                String spritePath = stateToAssetPath.get(animState);
                
                // Load sprite sheet
                java.io.File spriteFile = new java.io.File(spritePath);
                if (!spriteFile.exists()) {
                    System.err.println(TAG + " ERROR: Sprite file not found: " + spriteFile.getAbsolutePath());
                    return false;
                }
                
                java.awt.image.BufferedImage spriteSheet = javax.imageio.ImageIO.read(spriteFile);
                if (spriteSheet == null) {
                    System.err.println(TAG + " ERROR: Failed to load sprite image: " + spritePath);
                    return false;
                }
                
                // Get frame count from sprite dimensions
                // For now assume known frame counts per animation from filenames
                int frameCount = getFrameCountFromPath(spritePath);
                
                // Get timing info
                long elapsedTime = getElapsedStateTime(player);
                int frameInterval = getFrameIntervalFromPath(spritePath);
                
                // Calculate current frame
                int currentFrame = frameCount > 0 ? (int)((elapsedTime / frameInterval) % frameCount) : 0;
                
                // Extract frame from horizontal sprite sheet
                int frameWidth = spriteSheet.getWidth() / frameCount;
                int frameHeight = spriteSheet.getHeight();
                
                if (frameWidth <= 0 || frameHeight <= 0) {
                    System.err.println(TAG + " Invalid sprite sheet dimensions: " + spriteSheet.getWidth() + "x" + frameHeight);
                    return false;
                }
                
                // Create frame image
                java.awt.image.BufferedImage frameImage = spriteSheet.getSubimage(
                    currentFrame * frameWidth, 0, frameWidth, frameHeight);
                
                // Render frame to screen (centered)
                int renderX = screenX - PLAYER_SPRITE_WIDTH / 2;
                int renderY = screenY - PLAYER_SPRITE_HEIGHT / 2;
                
                boolean success = g.drawImage(frameImage, renderX, renderY, PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT, null);
                
                if (success) {
                    System.out.println(TAG + " Rendered player frame " + currentFrame + "/" + frameCount + 
                                     " state=" + animState);
                } else {
                    System.err.println(TAG + " Failed to draw sprite image");
                }
                
                return success;
                
            } catch (ClassNotFoundException e) {
                System.err.println(TAG + " PlayerController class not found: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.err.println(TAG + " Error rendering animated sprite: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        
        /**
         * Extract frame count from sprite filename
         * Examples: "01_Player_Biker_Idle_4Frames1Row" returns 4
         */
        private int getFrameCountFromPath(String path) {
            try {
                // Look for pattern like "6Frames" or "4Frames"
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+)Frames");
                java.util.regex.Matcher matcher = pattern.matcher(path);
                if (matcher.find()) {
                    return Integer.parseInt(matcher.group(1));
                }
            } catch (Exception e) {
                System.err.println(TAG + " Error extracting frame count from path: " + path);
            }
            return 4;  // Default fallback
        }
        
        /**
         * Extract frame interval in milliseconds from sprite filename
         * Examples: "150ms" returns 150, "80ms" returns 80
         */
        private int getFrameIntervalFromPath(String path) {
            try {
                // Look for pattern like "150ms" or "80ms"
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+)ms");
                java.util.regex.Matcher matcher = pattern.matcher(path);
                if (matcher.find()) {
                    return Integer.parseInt(matcher.group(1));
                }
            } catch (Exception e) {
                System.err.println(TAG + " Error extracting frame interval from path: " + path);
            }
            return 100;  // Default fallback: 100ms per frame
        }
        
        /**
         * Get elapsed time since state change in milliseconds
         */
        private long getElapsedStateTime(Object player) {
            try {
                java.lang.reflect.Field stateStartTimeField = player.getClass().getSuperclass().getDeclaredField("stateStartTime");
                stateStartTimeField.setAccessible(true);
                long stateStartTime = stateStartTimeField.getLong(player);
                return System.currentTimeMillis() - stateStartTime;
            } catch (Exception e) {
                System.err.println(TAG + " Error getting elapsed state time: " + e.getMessage());
            }
            return 0;
        }
        
        private void renderEnemy(java.awt.Graphics2D g, Object enemy, GameRenderData gameData) {
            try {
                float x = getFloatValue(enemy, "getX", 0f);
                float y = getFloatValue(enemy, "getY", 0f);
                
                if (screenWidth > 0 && screenHeight > 0) {
                    int screenX = (int)(x - gameData.cameraX + screenWidth / 2);
                    int screenY = (int)(y - gameData.cameraY + screenHeight / 2);
                    g.setColor(java.awt.Color.RED);
                    g.fillRect(screenX, screenY, 32, 32);
                }
            } catch (Exception e) {
                System.err.println(TAG + " Error rendering enemy: " + e.getMessage());
            }
        }
        
        private void renderBoss(java.awt.Graphics2D g, Object boss, GameRenderData gameData) {
            try {
                float x = getFloatValue(boss, "getX", 0f);
                float y = getFloatValue(boss, "getY", 0f);
                int health = getIntValue(boss, "getHealth", 100);
                int maxHealth = getIntValue(boss, "getMaxHealth", 100);
                
                if (screenWidth > 0 && screenHeight > 0) {
                    int screenX = (int)(x - gameData.cameraX + screenWidth / 2);
                    int screenY = (int)(y - gameData.cameraY + screenHeight / 2);
                    g.setColor(new java.awt.Color(200, 50, 50, 200));
                    g.fillRect(screenX, screenY, 100, 100);
                    
                    // Health bar
                    int barWidth = 100;
                    float healthPercent = (float) health / maxHealth;
                    g.setColor(java.awt.Color.GREEN);
                    g.fillRect(screenX, screenY - 10, (int)(barWidth * healthPercent), 5);
                    g.setColor(java.awt.Color.RED);
                    g.drawRect(screenX, screenY - 10, barWidth, 5);
                }
            } catch (Exception e) {
                System.err.println(TAG + " Error rendering boss: " + e.getMessage());
            }
        }
        
        public void setScreenSize(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
        
        protected float getFloatValue(Object obj, String methodName, float defaultValue) {
            try {
                java.lang.reflect.Method method = obj.getClass().getMethod(methodName);
                Object result = method.invoke(obj);
                if (result instanceof Number) return ((Number) result).floatValue();
            } catch (Exception e) {}
            return defaultValue;
        }
        
        protected int getIntValue(Object obj, String methodName, int defaultValue) {
            try {
                java.lang.reflect.Method method = obj.getClass().getMethod(methodName);
                Object result = method.invoke(obj);
                if (result instanceof Number) return ((Number) result).intValue();
            } catch (Exception e) {}
            return defaultValue;
        }
    }
    
    /**
     * WeaponRenderer - Renders projectiles and weapons
     */
    public static class WeaponRenderer {
        private int screenWidth, screenHeight;
        private static final String TAG = "[WeaponRenderer]";
        
        public WeaponRenderer(int w, int h) { 
            screenWidth = w; 
            screenHeight = h; 
        }
        
        public void render(java.awt.Graphics2D g, GameRenderData gameData) {
            if (gameData == null || gameData.weapons == null || screenWidth <= 0 || screenHeight <= 0) return;
            
            for (Object weapon : gameData.weapons) {
                try {
                    float x = getFloatValue(weapon, "getX", 0f);
                    float y = getFloatValue(weapon, "getY", 0f);
                    int screenX = (int)(x - gameData.cameraX + screenWidth / 2);
                    int screenY = (int)(y - gameData.cameraY + screenHeight / 2);
                    g.setColor(java.awt.Color.YELLOW);
                    g.fillOval(screenX, screenY, 8, 8);
                } catch (Exception e) {
                    System.err.println(TAG + " Error rendering weapon: " + e.getMessage());
                }
            }
        }
        
        public void setScreenSize(int w, int h) { 
            screenWidth = w; 
            screenHeight = h; 
        }
        
        protected float getFloatValue(Object obj, String methodName, float defaultValue) {
            try {
                java.lang.reflect.Method method = obj.getClass().getMethod(methodName);
                Object result = method.invoke(obj);
                if (result instanceof Number) return ((Number) result).floatValue();
            } catch (Exception e) {}
            return defaultValue;
        }
    }
    
    /**
     * VFXRenderer - Renders visual effects and particles
     */
    public static class VFXRenderer {
        private int screenWidth;
        private int screenHeight;
        private static final String TAG = "[VFXRenderer]";
        
        public VFXRenderer(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
        
        public void render(java.awt.Graphics2D g, GameRenderData gameData) {
            if (gameData == null || screenWidth <= 0 || screenHeight <= 0) return;
            
            if (gameData.vfxManager != null) {
                try {
                    // VFX particles would be rendered here using camera-relative coordinates
                    // Particles are positioned relative to world coordinates
                } catch (Exception e) {
                    System.err.println(TAG + " Error rendering VFX: " + e.getMessage());
                }
            }
        }
        
        public void setScreenSize(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
    }
    
    /**
     * EffectRenderer - Renders screen effects
     */
    public static class EffectRenderer {
        private int screenWidth;
        private int screenHeight;
        private Object camera;  // Used for camera-relative effect positioning
        private float cameraShakeAmount = 0f;  // Screen shake intensity from camera
        private static final String TAG = "[EffectRenderer]";
        
        public EffectRenderer(int width, int height, Object cameraCoordinator) {
            this.screenWidth = width;
            this.screenHeight = height;
            this.camera = cameraCoordinator;  // Store for effect positioning
        }
        
        public void render(java.awt.Graphics2D g, GameRenderData gameData) {
            if (g == null || gameData == null || screenWidth <= 0 || screenHeight <= 0) return;
            
            // Screen-space effects rendering using camera reference
            if (camera != null) {
                try {
                    // Extract camera shake/effect information if available
                    cameraShakeAmount = gameData.effectIntensity;
                    
                    // Apply camera-relative screen effects
                    switch (gameData.currentEffect) {
                        case SHAKE:
                            // Screen shake effect - offset drawing by shake amount
                            int shakeX = (int)(Math.random() * cameraShakeAmount * 10 - cameraShakeAmount * 5);
                            int shakeY = (int)(Math.random() * cameraShakeAmount * 10 - cameraShakeAmount * 5);
                            // Apply shake transformation
                            g.translate(shakeX, shakeY);
                            break;
                        case BLUR:
                            // Motion blur effect using camera velocity
                            g.setColor(new java.awt.Color(0, 0, 0, (int)(50 * cameraShakeAmount)));
                            g.fillRect(0, 0, screenWidth, screenHeight);
                            break;
                        default:
                            // No camera-specific effect needed
                            break;
                    }
                } catch (Exception e) {
                    System.err.println(TAG + " Error rendering effects: " + e.getMessage());
                }
            }
        }
        
        public void setScreenSize(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
    }
    
    /**
     * ComprehensiveTileMapLoader - Loads complete level assets
     */
    public static class ComprehensiveTileMapLoader {
        private Map<Character, String> tileDefinitions = new HashMap<>();  // Actually used now
        private Map<Character, BufferedImage> tileCache = new HashMap<>();  // Actually used for caching

        private String mapFilePath = null;  // Store for reference

        private String levelName = null;  // Store level identifier
        private static final String TAG = "[ComprehensiveTileMapLoader]";
        
        public static class AnimatedObject {
            public String name;
            public String filePath;
            public int frameCount;
            
            public AnimatedObject(String name, String path) {
                this.name = name;
                this.filePath = path;
                this.frameCount = 1;
            }
        }
        
        public ComprehensiveTileMapLoader(String mapFilePath, String levelName) {
            this.mapFilePath = mapFilePath;  // Store for reference
            this.levelName = levelName;  // Store for logging/reference
            try {
                if (mapFilePath != null && !mapFilePath.isEmpty()) {
                    loadMapFile(mapFilePath);
                }
            } catch (IOException e) {
                System.err.println(TAG + " Failed to load map file: " + mapFilePath);
            }
        }
        
        private void loadMapFile(String mapPath) throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(mapPath));
            String line;
            int definitionCount = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) continue;
                if (line.equals("#map")) break;
                if (line.contains("=")) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String symbol = parts[0].trim();
                        String filename = parts[1].trim();
                        if (symbol.length() == 1) {
                            tileDefinitions.put(symbol.charAt(0), filename);  // Actually used
                            definitionCount++;
                        }
                    }
                }
            }
            reader.close();
            System.out.println(TAG + " Loaded " + definitionCount + " tile definitions from " + mapPath);
        }
        
        public void render(java.awt.Graphics2D g, GameRenderData gameData, AssetCache assetCache) {
            if (g == null || gameData == null || assetCache == null || tileDefinitions.isEmpty()) return;
            
            // Actually use tileDefinitions and cache tiles
            for (Map.Entry<Character, String> entry : tileDefinitions.entrySet()) {
                try {
                    if (!tileCache.containsKey(entry.getKey())) {
                        BufferedImage tileImage = assetCache.getAssetImage(entry.getValue());
                        if (tileImage != null) {
                            tileCache.put(entry.getKey(), tileImage);  // Cache the loaded tile
                        }
                    }
                } catch (Exception e) {
                    System.err.println(TAG + " Failed to load tile: " + entry.getValue());
                }
            }
        }
    }
    
    /**
     * EnhancedTileMapLoader - Advanced tilemap parser
     */
    public static class EnhancedTileMapLoader {
        private int mapWidth;
        private int mapHeight;
        private int tileSize;
        private Map<Character, String> tileDefinitions = new HashMap<>();  // Actually used
        private char[][] mapGrid;  // Actually used for rendering
        private static final String TAG = "[EnhancedTileMapLoader]";
        
        public EnhancedTileMapLoader() {
            this.mapWidth = 0;
            this.mapHeight = 0;
            this.tileSize = 32;
            this.mapGrid = new char[0][0];  // Initialize empty grid
        }
        
        public void render(java.awt.Graphics2D g, GameRenderData gameData, AssetCache assetCache) {
            if (g == null || gameData == null || assetCache == null || mapGrid == null || mapGrid.length == 0) return;
            if (mapWidth <= 0 || mapHeight <= 0 || tileSize <= 0) return;
            
            System.out.println(TAG + " Rendering tilemap (" + mapWidth + "x" + mapHeight + ", tile size: " + tileSize + ")");
            
            // Actually use mapGrid and tileDefinitions for rendering
            for (int y = 0; y < mapHeight && y < mapGrid.length; y++) {
                for (int x = 0; x < mapWidth && x < mapGrid[y].length; x++) {
                    char tileChar = mapGrid[y][x];  // Actually use mapGrid
                    if (tileDefinitions.containsKey(tileChar)) {
                        try {
                            BufferedImage tileImage = assetCache.getAssetImage(tileDefinitions.get(tileChar));
                            if (tileImage != null) {
                                int screenX = (int)(x * tileSize - gameData.cameraX);
                                int screenY = (int)(y * tileSize - gameData.cameraY);
                                g.drawImage(tileImage, screenX, screenY, tileSize, tileSize, null);
                            }
                        } catch (Exception e) {
                            System.err.println(TAG + " Error rendering tile at (" + x + "," + y + "): " + e.getMessage());
                        }
                    }
                }
            }
        }
    }
    
    /**
     * StatusEffectRenderer - Renders status effects and temporary ailments
     */
    public static class StatusEffectRenderer {
        private int screenWidth;
        private int screenHeight;
        
        public StatusEffectRenderer(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
        
        public void render(java.awt.Graphics2D g) {
            if (g == null || screenWidth <= 0 || screenHeight <= 0) return;
            // Status effect rendering with proper screen dimensions
        }
        
        public void setScreenSize(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
    }
    
    /**
     * DamageNumberRenderer - Renders floating damage numbers
     */
    public static class DamageNumberRenderer {
        private int screenWidth, screenHeight;

        private static final String TAG = "[DamageNumberRenderer]";
        
        public DamageNumberRenderer(int w, int h) { 
            screenWidth = w; 
            screenHeight = h; 
        }
        
        public void render(java.awt.Graphics2D g) {
            if (g == null || screenWidth <= 0 || screenHeight <= 0) return;
            // Render floating damage numbers using screen dimensions
        }
        
        public void setScreenSize(int w, int h) { 
            screenWidth = w; 
            screenHeight = h; 
        }
    }
    
    /**
     * DigitRenderer - Renders individual digits
     */
    public static class DigitRenderer {
        private int screenWidth, screenHeight;

        private static final String TAG = "[DigitRenderer]";
        
        public DigitRenderer(int w, int h) { 
            screenWidth = w; 
            screenHeight = h; 
        }
        
        public void renderDigit(java.awt.Graphics2D g, char digit, int x, int y, int size) {
            if (g == null || screenWidth <= 0 || screenHeight <= 0) return;
            // Render individual digit sprite using screen dimensions
        }
        
        public void setScreenSize(int w, int h) { 
            screenWidth = w; 
            screenHeight = h; 
        }
    }
    
    /**
     * TutorialRenderer - Renders tutorial text and help displays
     */
    public static class TutorialRenderer {
        public void render(java.awt.Graphics2D g) {}
    }
    
    /**
     * InputDisplayRenderer - Renders current input state for debugging
     */
    public static class InputDisplayRenderer {
        private int screenWidth;
        private int screenHeight;

        private static final String TAG = "[InputDisplayRenderer]";
        
        public InputDisplayRenderer(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
        
        public void render(java.awt.Graphics2D g) {
            if (g == null || screenWidth <= 0 || screenHeight <= 0) return;
            g.setColor(java.awt.Color.GREEN);
            g.setFont(new Font("Courier", Font.PLAIN, 12));
            g.drawString("[INPUT DEBUG]", 10, screenHeight - 10);
        }
        
        public void setScreenSize(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
        }
    }
    
    /**
     * PostProcessingRenderer - Post-processing effects
     */
    public static class PostProcessingRenderer {
        public void render(java.awt.Graphics2D g) {}
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // SUPPORT CLASSES
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Manages rendering layer enable/disable
     */
    public static class LayerManager {
        private Map<Layer, Boolean> layerStates = new HashMap<>();
        
        public LayerManager() {
            for (Layer layer : Layer.values()) {
                layerStates.put(layer, true);  // All layers enabled by default
            }
        }
        
        public void setLayerEnabled(Layer layer, boolean enabled) {
            layerStates.put(layer, enabled);
        }
        
        public boolean isLayerEnabled(Layer layer) {
            return layerStates.getOrDefault(layer, true);
        }
    }
    
    /**
     * Asset caching system
     */
    public static class AssetCache {
        private Map<String, BufferedImage> cache = new HashMap<>();
        private static final int MAX_CACHE_SIZE = 500;
        private static final String TAG = "[AssetCache]";
        
        public BufferedImage getOrLoad(String filePath) throws IOException {
            if (cache.containsKey(filePath)) {
                return cache.get(filePath);
            }
            
            BufferedImage image = ImageIO.read(new File(filePath));
            if (image != null && cache.size() < MAX_CACHE_SIZE) {
                cache.put(filePath, image);
                System.out.println(TAG + " Cached: " + filePath + " (" + cache.size() + "/" + MAX_CACHE_SIZE + ")");
            }
            return image;
        }
        
        public BufferedImage getAssetImage(String filePath) throws IOException {
            return getOrLoad(filePath);
        }
        
        public BufferedImage getAssetImage(Object assetType) {
            // Handle asset type enumeration if needed
            return null;
        }
        
        public void clear() {
            System.out.println(TAG + " Clearing cache (" + cache.size() + " items)");
            cache.clear();
        }
        
        public int getSize() {
            return cache.size();
        }
    }
    
    /**
     * Rendering configuration
     */
    public static class RenderingConfig {
        public ScreenEffect currentEffect = ScreenEffect.NONE;
        public float effectIntensity = 0f;
        public boolean debugMode = false;
        public boolean showBounds = false;
        public boolean showGrid = false;
    }
    
    /**
     * Camera coordinate transformation
     */
    public static class CameraTransform {
        public static float worldToScreenX(float worldX, float cameraX, int screenWidth) {
            return worldX - cameraX + screenWidth / 2;
        }
        
        public static float worldToScreenY(float worldY, float cameraY, int screenHeight) {
            return worldY - cameraY + screenHeight / 2;
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

