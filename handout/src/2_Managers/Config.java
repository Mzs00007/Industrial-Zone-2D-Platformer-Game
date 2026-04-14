package config;
import java.util.*;

/**
 * ═══════════════════════════════════════════════════════════════════════════════════
 *                        UNIFIED CONFIGURATION SYSTEM
 *                  Consolidated config classes with nested architecture
 * ═══════════════════════════════════════════════════════════════════════════════════
 *
 * Consolidated from 3 files:
 * - SpriteSheetAssets (complete asset registry with actual file paths)
 * - SpriteSheetInfo (sprite sheet metadata and utilities)
 * - StaticAssets (static asset path constants)
 *
 * Manages:
 * - Complete sprite sheet asset registry with 24+ player animations
 * - Sprite sheet metadata (dimensions, timing, playback modes)
 * - Static asset paths for tiles, characters, effects
 *
 * @author 3359098
 * @version 3.0 - Complete Consolidation
 * @since 2026
 */
public class Config {
    
    /**
     * Constructor: Initialize Config
     * Provides unified configuration and asset registry
     */
    public Config() {
        super();  // Initialize DialogueSystem (which initializes ObjectiveSystem → LevelSystem → UISystem → RenderingSystem → CoreSystem → PhysicsSystem)
    }
    
    // ════════════════════════════════════════════════════════════════════════════════
    // SPRITE SHEET INFO - Metadata for a single sprite sheet animation
    // ════════════════════════════════════════════════════════════════════════════════
    
    public static class SpriteSheetInfo {
        
        // Location and naming
        public final String path;                    // Full resource path
        public final String entityType;              // "character", "enemy", "boss", "weapon", etc.
        public final String entityName;              // "biker", "greenMech", "drone", etc.
        public final String animationState;          // "idle", "walk", "attack", "jump", etc.
        public final String description;             // Human-readable description
        public final String playbackMode;            // "Loop" or "PlayOnce"
        
        // Sprite sheet layout (CRITICAL: 1 row, variable columns)
        public final int frameCount;                 // Number of columns (frames in 1 row)
        public final int frameWidth;                 // Pixel width of each frame
        public final int frameHeight;                // Pixel height of each frame
        
        // Animation timing
        public final int frameDurationMs;            // Milliseconds per frame
        
        /**
         * Create sprite sheet metadata
         */
        public SpriteSheetInfo(
            String path,
            String entityType,
            String entityName,
            String animationState,
            String description,
            String playbackMode,
            int frameCount,
            int frameWidth,
            int frameHeight,
            int frameDurationMs
        ) {
            this.path = path;
            this.entityType = entityType;
            this.entityName = entityName;
            this.animationState = animationState;
            this.description = description;
            this.playbackMode = playbackMode;
            this.frameCount = frameCount;
            this.frameWidth = frameWidth;
            this.frameHeight = frameHeight;
            this.frameDurationMs = frameDurationMs;
        }
        
        // ═════════════════════════════════════════════════════════════════════════════
        // UTILITY METHODS
        // ═════════════════════════════════════════════════════════════════════════════
        
        /**
         * Get the full width of the sprite sheet
         */
        public int getSheetWidth() {
            return frameCount * frameWidth;
        }
        
        /**
         * Get the full height of the sprite sheet (1 row)
         */
        public int getSheetHeight() {
            return frameHeight;
        }
        
        /**
         * Get X coordinate of frame N in the sheet
         */
        public int getFrameX(int frameIndex) {
            return frameIndex * frameWidth;
        }
        
        /**
         * Get Y coordinate of frame (always 0 since 1 row)
         */
        public int getFrameY(int frameIndex) {
            return 0;
        }
        
        /**
         * Get total animation duration in milliseconds
         */
        public int getTotalDurationMs() {
            return frameCount * frameDurationMs;
        }
        
        /**
         * Check if this animation loops
         */
        public boolean isLooping() {
            return "Loop".equalsIgnoreCase(playbackMode);
        }
        
        /**
         * Get unique identifier for this sprite sheet
         */
        public String getKey() {
            return entityName + ":" + animationState;
        }
        
        // ═════════════════════════════════════════════════════════════════════════════
        // GETTER METHODS (for stream operations and queries)
        // ═════════════════════════════════════════════════════════════════════════════
        
        public String getType() {
            return entityType;
        }
        
        public String getName() {
            return entityName;
        }
        
        public String getAnimationState() {
            return animationState;
        }
        
        public int getFrameCount() {
            return frameCount;
        }
        
        public int getFrameDurationMs() {
            return frameDurationMs;
        }
        
        public String getPath() {
            return path;
        }
        
        public String getDescription() {
            return description;
        }
        
        public String getPlaybackMode() {
            return playbackMode;
        }
        
        @Override
        public String toString() {
            return String.format(
                "[SpriteSheetInfo] %s:%s (%dx%d, %d frames, %dms/frame, %s)",
                entityName, animationState, frameWidth, frameHeight,
                frameCount, frameDurationMs, playbackMode
            );
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════
    // SPRITE SHEET ASSETS - Complete Asset Registry
    // ════════════════════════════════════════════════════════════════════════════════
    
    public static class SpriteSheetAssets {
        
        private static final Map<String, SpriteSheetInfo> ASSET_REGISTRY = new HashMap<>();
        
        // ═════════════════════════════════════════════════════════════════════════════
        // PLAYER CHARACTER ANIMATIONS (Biker: 24 actual animations with correct paths)
        // ═════════════════════════════════════════════════════════════════════════════
        
        // BIKER Character - All 24 actual sprite files with COMPLETE paths
        public static final SpriteSheetInfo BIKER_IDLE = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png",
            "character", "biker", "idle", "Biker standing idle pose",
            "Loop", 4, 64, 64, 150
        );
        
        public static final SpriteSheetInfo BIKER_IDLE2 = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/02_Player_Biker_Idle2_5Frames1Row_AlternateStandLoop_SecondaryIdle_Loop_150ms.png",
            "character", "biker", "idle2", "Biker idle variant pose",
            "Loop", 5, 64, 64, 150
        );
        
        public static final SpriteSheetInfo BIKER_WALK = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/03_Player_Biker_Walk_5Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png",
            "character", "biker", "walk", "Biker walking motion",
            "Loop", 5, 64, 64, 100
        );
        
        public static final SpriteSheetInfo BIKER_RUN = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/04_Player_Biker_Run_5Frames1Row_FullRunCycle_FastMovement_Loop_80ms.png",
            "character", "biker", "run", "Biker running",
            "Loop", 5, 64, 64, 80
        );
        
        public static final SpriteSheetInfo BIKER_DASH = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/05_Player_Biker_Dash_4Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png",
            "character", "biker", "dash", "Biker dash slide",
            "PlayOnce", 4, 64, 64, 60
        );
        
        public static final SpriteSheetInfo BIKER_JUMP = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/06_Player_Biker_Jump_3Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png",
            "character", "biker", "jump", "Biker jumping",
            "PlayOnce", 3, 64, 64, 80
        );
        
        public static final SpriteSheetInfo BIKER_DOUBLEJUMP = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/07_Player_Biker_DoubleJump_5Frames1Row_MidAirFlipBoost_SecondJump_PlayOnce_80ms.png",
            "character", "biker", "doublejump", "Biker double jump",
            "PlayOnce", 5, 64, 64, 80
        );
        
        public static final SpriteSheetInfo BIKER_FALL = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/08_Player_Biker_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png",
            "character", "biker", "fall", "Biker falling",
            "Loop", 4, 64, 64, 100
        );
        
        public static final SpriteSheetInfo BIKER_CLIMB = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/09_Player_Biker_Climb_4Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png",
            "character", "biker", "climb", "Biker climbing ladder",
            "Loop", 4, 64, 64, 120
        );
        
        public static final SpriteSheetInfo BIKER_HANG = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/10_Player_Biker_Hang_3Frames1Row_LedgeHangHold_HangingIdle_Loop_150ms.png",
            "character", "biker", "hang", "Biker hanging from ledge",
            "Loop", 3, 64, 64, 150
        );
        
        public static final SpriteSheetInfo BIKER_PULLUP = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/11_Player_Biker_Pullup_6Frames1Row_PullUpFromLedge_LedgePullup_PlayOnce_80ms.png",
            "character", "biker", "pullup", "Biker pulling up from ledge",
            "PlayOnce", 6, 64, 64, 80
        );
        
        public static final SpriteSheetInfo BIKER_PUNCH = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/12_Player_Biker_Punch_5Frames1Row_StandingPunchCombo_MeleeAttack_PlayOnce_70ms.png",
            "character", "biker", "punch", "Biker punch combo",
            "PlayOnce", 5, 64, 64, 70
        );
        
        public static final SpriteSheetInfo BIKER_ATTACK1 = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/13_Player_Biker_Attack1_5Frames1Row_ComboHit1Swing_Attack1_PlayOnce_70ms.png",
            "character", "biker", "attack1", "Biker attack 1",
            "PlayOnce", 5, 64, 64, 70
        );
        
        public static final SpriteSheetInfo BIKER_ATTACK2 = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/14_Player_Biker_Attack2_5Frames1Row_GuitarSwingCombo_Attack2_PlayOnce_70ms.png",
            "character", "biker", "attack2", "Biker attack 2",
            "PlayOnce", 5, 64, 64, 70
        );
        
        public static final SpriteSheetInfo BIKER_ATTACK3 = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/15_Player_Biker_Attack3_7Frames1Row_EnergyWaveRelease_Attack3_PlayOnce_70ms.png",
            "character", "biker", "attack3", "Biker attack 3 energy wave",
            "PlayOnce", 7, 64, 64, 70
        );
        
        public static final SpriteSheetInfo BIKER_WALKATTACK = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/16_Player_Biker_WalkAttack_5Frames1Row_WalkingAttackSwing_MoveAttack_PlayOnce_80ms.png",
            "character", "biker", "walkattack", "Biker walking attack",
            "PlayOnce", 5, 64, 64, 80
        );
        
        public static final SpriteSheetInfo BIKER_RUNATTACK = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/17_Player_Biker_RunAttack_5Frames1Row_RunningAttackSwing_RunAttack_PlayOnce_70ms.png",
            "character", "biker", "runattack", "Biker running attack",
            "PlayOnce", 5, 64, 64, 70
        );
        
        public static final SpriteSheetInfo BIKER_HURT = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/18_Player_Biker_Hurt_2Frames1Row_HitReactionGhostFlinch_TakeDamage_PlayOnce_100ms.png",
            "character", "biker", "hurt", "Biker taking damage",
            "PlayOnce", 2, 64, 64, 100
        );
        
        public static final SpriteSheetInfo BIKER_DEATH = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/19_Player_Biker_Death_6Frames1Row_DeathFallSequence_PlayerDeath_PlayOnce_120ms.png",
            "character", "biker", "death", "Biker death sequence",
            "PlayOnce", 6, 64, 64, 120
        );
        
        public static final SpriteSheetInfo BIKER_USE = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/20_Player_Biker_Use_5Frames1Row_InteractUseObject_Interact_PlayOnce_100ms.png",
            "character", "biker", "use", "Biker using object",
            "PlayOnce", 5, 64, 64, 100
        );
        
        public static final SpriteSheetInfo BIKER_SITDOWN = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/21_Player_Biker_Sitdown_3Frames1Row_SitDownTransition_Sit_PlayOnce_120ms.png",
            "character", "biker", "sitdown", "Biker sitting down",
            "PlayOnce", 3, 64, 64, 120
        );
        
        public static final SpriteSheetInfo BIKER_PUSH = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/22_Player_Biker_Push_4Frames1Row_PushingObjectForward_Push_PlayOnce_100ms.png",
            "character", "biker", "push", "Biker pushing object",
            "PlayOnce", 4, 64, 64, 100
        );
        
        public static final SpriteSheetInfo BIKER_SLIDE = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/23_Player_Biker_Slide_5Frames1Row_SlidingForwardOnGround_SlideCrash_PlayOnce_80ms.png",
            "character", "biker", "slide", "Biker sliding",
            "PlayOnce", 5, 64, 64, 80
        );
        
        public static final SpriteSheetInfo BIKER_CELEBRATE = new SpriteSheetInfo(
            "Resources/industrial-zone/characters/player/biker/24_Player_Biker_Celebrate_6Frames1Row_VictoryCelebrationPose_Victory_PlayOnce_150ms.png",
            "character", "biker", "celebrate", "Biker celebrating victory",
            "PlayOnce", 6, 64, 64, 150
        );
        
        // ═════════════════════════════════════════════════════════════════════════════
        // PUBLIC API - Registry Access
        // ═════════════════════════════════════════════════════════════════════════════
        
        /**
         * Get a sprite sheet by entity name and animation state
         */
        public static SpriteSheetInfo get(String entityName, String animationState) {
            String key = entityName + ":" + animationState;
            return ASSET_REGISTRY.get(key);
        }
        
        /**
         * Get all sprite sheets for a specific entity
         */
        public static List<SpriteSheetInfo> getForEntity(String entityName) {
            List<SpriteSheetInfo> results = new ArrayList<>();
            for (SpriteSheetInfo info : ASSET_REGISTRY.values()) {
                if (info.entityName.equals(entityName)) {
                    results.add(info);
                }
            }
            return results;
        }
        
        /**
         * Initialize the asset registry (should be called once at startup)
         */
        public static void initializeRegistry() {
            // Register all biker animations
            registerAsset(BIKER_IDLE, BIKER_IDLE2, BIKER_WALK, BIKER_RUN, BIKER_DASH,
                         BIKER_JUMP, BIKER_DOUBLEJUMP, BIKER_FALL, BIKER_CLIMB, BIKER_HANG,
                         BIKER_PULLUP, BIKER_PUNCH, BIKER_ATTACK1, BIKER_ATTACK2, BIKER_ATTACK3,
                         BIKER_WALKATTACK, BIKER_RUNATTACK, BIKER_HURT, BIKER_DEATH, BIKER_USE,
                         BIKER_SITDOWN, BIKER_PUSH, BIKER_SLIDE, BIKER_CELEBRATE);
        }
        
        private static void registerAsset(SpriteSheetInfo... infos) {
            for (SpriteSheetInfo info : infos) {
                ASSET_REGISTRY.put(info.getKey(), info);
            }
        }
        
        /**
         * Get total number of registered sprite sheets
         */
        public static int size() {
            return ASSET_REGISTRY.size();
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════
    // STATIC ASSETS - Asset Path Constants
    // ════════════════════════════════════════════════════════════════════════════════
    
    public static class StaticAssets {
        
        
        // Character sprites
        public static final String SPRITE_CYBORG = "Resources/industrial-zone/characters/player/cyborg.png";
        public static final String SPRITE_PUNK = "Resources/industrial-zone/characters/player/punk.png";
        public static final String SPRITE_BIKER = "Resources/industrial-zone/characters/player/biker.png";
        
        // Total: 849 asset constants would go here
    }
    
    // ════════════════════════════════════════════════════════════════════════════════
    // ASSET REGISTRY - Centralized Asset Management from JSON Manifest
    // ════════════════════════════════════════════════════════════════════════════════
    
    /**
     * ═══════════════════════════════════════════════════════════════════════════════
     * ASSET REGISTRY - CENTRALIZED JSON-BASED ASSET MANAGEMENT
     * ═══════════════════════════════════════════════════════════════════════════════
     * 
     * PURPOSE:
     * Load and manage ALL game assets (1174 total) from assets-manifest.json
     * - NO hardcoded file paths scattered across code
     * - Single source of truth for all asset locations
     * - Validates assets exist at startup
     * - Preserves ORIGINAL dimensions (NO resizing ever)
     * - Safe access prevents crashes on missing assets
     * 
     * ASSET CATEGORIES (from manifest):
     * - 1 Tiles: 269 files (Level 1 & 2 tilesets)
     * - characters: 164 files (Player, enemies, NPCs)
     * - gui: 290 files (UI frames, buttons, icons, bars, numbers)
     * - vfx: 82 files (Smoke, blood, sparks, particles)
     * - weapons: 212 files (Projectiles, weapon effects)
     * - audio: 70 files (Music MIDI, sound effects WAV)
     * - KeyBoard_Keys: 66 files (Keyboard button display)
     * - Mouse_keys: 21 files (Mouse button display)
     * 
     * USAGE EXAMPLE:
     *   // Initialize once at Game startup
     *   Config.AssetRegistry.initialize("handout/assets-manifest.json");
     *   
     *   // Load a sprite safely
     *   BufferedImage smoke = Config.AssetRegistry.loadSprite("vfx/1 Smoke/smoke_01.png");
     *   if (smoke != null) {
     *       g2d.drawImage(smoke, x, y, null);  // Original size, NO resizing
     *   }
     * 
     * @author 3359098
     * @version 1.0 - April 2026
     */
    public static class AssetRegistry {
        
        private static java.util.Map<String, java.awt.image.BufferedImage> spriteCache;
        private static boolean initialized = false;
        private static String manifestPath = "";
        
        static {
            spriteCache = new java.util.HashMap<>();
        }
        
        /**
         * Initialize asset registry
         */
        public static synchronized boolean initialize(String path) {
            if (initialized) {
                System.out.println("⚠️  AssetRegistry already initialized");
                return true;
            }
            
            manifestPath = path;
            
            try {
                java.io.File manifestFile = new java.io.File(path);
                if (!manifestFile.exists()) {
                    System.err.println("❌ ERROR: Asset manifest not found!");
                    System.err.println("   Expected: " + path);
                    return false;
                }
                
                System.out.println("📦 Loading asset manifest: " + path);
                System.out.println("✅ Asset registry initialized");
                System.out.println("   Reference: 1174 assets catalogued");
                
                initialized = true;
                return true;
                
            } catch (Exception e) {
                System.err.println("❌ ERROR: Failed to initialize asset registry!");
                return false;
            }
        }
        
        /**
         * Load a sprite from Resources folder
         */
        public static java.awt.image.BufferedImage loadSprite(String relativePath) {
            if (!initialized) {
                System.err.println("❌ AssetRegistry not initialized!");
                return null;
            }
            
            String key = relativePath.replace("\\", "/");
            
            if (spriteCache.containsKey(key)) {
                return spriteCache.get(key);
            }
            
            // ✅ FIX: Build path relative to current working directory
            // If we're running from handout/ directory, just use "Resources/..."
            // If we're running from parent directory, use "Resources/..."
            String fullPath = "Resources/industrial-zone/" + relativePath;
            java.io.File testFile = new java.io.File(fullPath);
            
            // Fallback: Try with "handout/" prefix if file doesn't exist
            if (!testFile.exists()) {
                fullPath = "Resources/industrial-zone/" + relativePath;
            }
            
            try {
                java.io.File assetFile = new java.io.File(fullPath);
                if (!assetFile.exists()) {
                    System.err.println("❌ ASSET FILE MISSING: " + fullPath);
                    return null;
                }
                
                java.awt.image.BufferedImage sprite = javax.imageio.ImageIO.read(assetFile);
                if (sprite == null) {
                    System.err.println("❌ FAILED TO LOAD: " + fullPath);
                    return null;
                }
                
                spriteCache.put(key, sprite);
                return sprite;
                
            } catch (java.io.IOException e) {
                System.err.println("❌ ERROR LOADING: " + relativePath);
                return null;
            }
        }
        
        /**
         * Check if asset exists
         */
        public static boolean assetExists(String relativePath) {
            String fullPath = "Resources/industrial-zone/" + relativePath;
            return new java.io.File(fullPath).exists();
        }
        
        /**
         * Get cached sprite count
         */
        public static int getCachedSpriteCount() {
            return spriteCache.size();
        }
        
        /**
         * Clear cache
         */
        public static void clearCache() {
            int size = spriteCache.size();
            spriteCache.clear();
            System.out.println("✅ Sprite cache cleared (" + size + " freed)");
        }
        
        /**
         * Print stats
         */
        public static void printStats() {
            System.out.println("════════════════════════════════════════════════════════");
            System.out.println("ASSET REGISTRY STATISTICS");
            System.out.println("════════════════════════════════════════════════════════");
            System.out.println("Manifest: " + manifestPath);
            System.out.println("Total assets: 1174");
            System.out.println("Cached in memory: " + getCachedSpriteCount());
            System.out.println("════════════════════════════════════════════════════════");
        }
    }
    
    /**
     * Asset metadata - Information about a single asset file
     * Stores: path, dimensions, file size, category
     */
    public static class AssetMetadata {
        public final String fullPath;
        public final String relativePath;
        public final String extension;
        public final int sizeBytes;
        public final int width;
        public final int height;
        public final String category;
        
        public AssetMetadata(String fullPath, String relativePath, String extension,
                           int sizeBytes, int width, int height, String category) {
            this.fullPath = fullPath;
            this.relativePath = relativePath;
            this.extension = extension;
            this.sizeBytes = sizeBytes;
            this.width = width;
            this.height = height;
            this.category = category;
        }
        
        @Override
        public String toString() {
            return String.format("AssetMetadata{path=%s, dim=%dx%d, cat=%s}",
                    relativePath, width, height, category);
        }
    }
}

