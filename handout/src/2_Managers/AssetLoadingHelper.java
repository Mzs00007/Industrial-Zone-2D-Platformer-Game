package core.assets;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * ASSET LOADING HELPER & USAGE EXAMPLES
 * 
 * Shows best practices for loading assets using the unified system
 * Replace direct path constants with these methods
 * 
 * MIGRATION EXAMPLES:
 * ════════════════════════════════════════════════════════════════════════════
 */
public class AssetLoadingHelper {
    
    private static final Logger LOG = Logger.getLogger(AssetLoadingHelper.class.getName());
    private static final AssetManager_001_UnifiedLoader MANAGER = AssetManager_001_UnifiedLoader.getInstance();
    private static final AssetMetadata METADATA = AssetMetadata.getInstance();
    private static final AssetPathBridge PATHS = new AssetPathBridge();
    
    // ════════════════════════════════════════════════════════════════════════
    // CHARACTER LOADING - Clean API
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Load player idle sprite animation
     * OLD: Direct string constant + frame count hardcoded
     * NEW: Uses bridge + metadata together
     */
    public static BufferedImage[] loadPlayerIdleAnimation() {
        String basePath = AssetPathBridge.Characters.PLAYER_BASE + "biker/idle_01.png";
        int frames = METADATA.AnimFrames.WEAPON_BIKER_IDLE_A;
        int timing = METADATA.AnimTiming.WEAPON_BIKER_IDLE;
        
        LOG.info("[AssetLoadingHelper] Loading player idle: " + frames + " frames @ " + timing + "ms");
        return MANAGER.getSpriteFrames(basePath, frames);
    }
    
    /**
     * Load boss attack animation
     * Uses metadata for accurate frame counts from manifest
     */
    public static BufferedImage[] loadBossAttackAnimation(String bossType) {
        String basePath = AssetPathBridge.Characters.BOSS_BASE + bossType.toLowerCase() + "/";
        int frames = METADATA.AnimFrames.BOSS_GOLF_SOLDIER_ATTACK;  // from manifest
        int timing = METADATA.AnimTiming.BOSS_ATTACK;  // 70ms (fast)
        boolean loops = METADATA.AnimBehavior.ONCE_ATTACK;  // false = play once
        
        LOG.info("[AssetLoadingHelper] Loading " + bossType + " attack: " + frames + 
                " frames @ " + timing + "ms (loops: " + loops + ")");
        return MANAGER.getSpriteFrames(basePath, frames);
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // LEVEL TILE LOADING - Organized by level
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Load Level 1 (Industrial Zone) tile set
     * Automatically uses stored metadata for all level 1 assets
     */
    public static BufferedImage loadLevel1Tile(String tileFileName) {
        String tilePath = AssetPathBridge.Level1Tiles.L1_TILES_BASE + tileFileName;
        return MANAGER.loadImage(tilePath);
    }
    
    /**
     * Load Level 1 background parallax layers
     * Extracts all background layer variants
     */
    public static BufferedImage[] loadLevel1BackgroundLayers() {
        return MANAGER.loadDirectory(AssetPathBridge.Level1Tiles.L1_BG_BASE);
    }
    
    /**
     * Load Level 2 (Power Station) - Day variant
     * Different from night variant - uses bridge organization
     */
    public static BufferedImage[] loadLevel2BackgroundDay() {
        return MANAGER.loadDirectory(AssetPathBridge.Level2Tiles.L2_BG_DAY);
    }
    
    /**
     * Load Level 2 (Power Station) - Night variant
     */
    public static BufferedImage[] loadLevel2BackgroundNight() {
        return MANAGER.loadDirectory(AssetPathBridge.Level2Tiles.L2_BG_NIGHT);
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // WEAPON LOADING - Organized by character and weapon set
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Load weapon set 1 for a specific character
     * Example: Get all "Biker with Weapon Set 1" sprites
     */
    public static BufferedImage[] loadWeapon1BikerSprites() {
        String weaponDir = AssetPathBridge.Weapons.WEAPON_1_CHAR_BIKER;
        return MANAGER.loadDirectory(weaponDir);
    }
    
    /**
     * Load weapon hand poses for a character
     * Shows weapon grip variations (vertical, horizontal, diagonal, etc.)
     */
    public static BufferedImage[] loadWeapon1BikerHandPoses() {
        String handDir = AssetPathBridge.Weapons.WEAPON_1_HANDS_BIKER;
        return MANAGER.loadDirectory(handDir);
    }
    
    /**
     * Load weapon bullets/projectiles
     */
    public static BufferedImage[] loadWeapon1Bullets() {
        String bulletDir = AssetPathBridge.Weapons.WEAPON_1_BULLETS;
        return MANAGER.loadDirectory(bulletDir);
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // VFX LOADING - Visual Effects
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Load smoke effect animation
     * Uses metadata: 18 frames @ 80ms with loop
     */
    public static BufferedImage[] loadSmokeEffect() {
        String effectPath = AssetPathBridge.VFX.VFX_SMOKE + "smoke_anim.png";
        int frames = METADATA.AnimFrames.VFX_SMOKE_DENSE;  // 18 from manifest
        int timing = METADATA.AnimTiming.VFX_SMOKE;        // 80ms
        boolean loops = METADATA.AnimBehavior.LOOP_VFX;    // true
        
        LOG.info("[AssetLoadingHelper] Loading smoke effect: " + frames + 
                " frames @ " + timing + "ms (loops: " + loops + ")");
        return MANAGER.getSpriteFrames(effectPath, frames);
    }
    
    /**
     * Load blood splatter effect
     * Uses metadata: 4 frames @ 80ms
     */
    public static BufferedImage[] loadBloodEffect() {
        String effectPath = AssetPathBridge.VFX.VFX_BLOOD + "blood_splat.png";
        int frames = METADATA.AnimFrames.VFX_BLOOD_SPLATTER;  // 4 from manifest
        int timing = METADATA.AnimTiming.VFX_BLOOD;           // 80ms
        
        LOG.info("[AssetLoadingHelper] Loading blood effect: " + frames + 
                " frames @ " + timing + "ms");
        return MANAGER.getSpriteFrames(effectPath, frames);
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // GUI LOADING - User interface elements
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Load GUI buttons
     */
    public static BufferedImage[] loadGUIButtons() {
        return MANAGER.loadDirectory(AssetPathBridge.GUI.GUI_BUTTONS);
    }
    
    /**
     * Load GUI icons (for inventory, skills, etc.)
     */
    public static BufferedImage[] loadGUIIcons() {
        return MANAGER.loadDirectory(AssetPathBridge.GUI.GUI_ICONS);
    }
    
    /**
     * Load font characters
     */
    public static BufferedImage[] loadFontCharacters() {
        return MANAGER.loadDirectory(AssetPathBridge.GUI.GUI_FONT_IMAGES);
    }
    
    /**
     * Load health/mana bars
     */
    public static BufferedImage[] loadGUIBars() {
        return MANAGER.loadDirectory(AssetPathBridge.GUI.GUI_BARS);
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // AUDIO LOADING - Sound effects and music
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Load music files (WAV format)
     */
    public static java.io.File[] loadMusicFiles() {
        String musicDir = AssetPathBridge.Audio.AUDIO_MUSIC_WAV;
        java.io.File dir = new java.io.File(musicDir);
        return dir.listFiles();
    }
    
    /**
     * Load sound effects
     */
    public static java.io.File[] loadSoundEffects() {
        String sfxDir = AssetPathBridge.Audio.AUDIO_SFX;
        java.io.File dir = new java.io.File(sfxDir);
        return dir.listFiles();
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // CACHE MANAGEMENT
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Get current cache statistics
     */
    public static void printCacheStats() {
        MANAGER.printCacheStats();
    }
    
    /**
     * Clear all asset caches if memory is needed
     */
    public static void clearCache() {
        MANAGER.clearAllCaches();
        LOG.info("[AssetLoadingHelper] Cache cleared");
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // MIGRATION CHECKLIST
    // ════════════════════════════════════════════════════════════════════════
    /*
     * To migrate existing code to use this system:
     *
     * 1. OLD CODE:
     *    BufferedImage[][] playerAnimations = new BufferedImage[5][];
     *    playerAnimations[IDLE] = loadImages(PLAYER_BASE + "idle_01.png", 4);
     *    playerAnimations[WALK] = loadImages(PLAYER_BASE + "walk_01.png", 4);
     *
     * 2. NEW CODE:
     *    BufferedImage[] idleAnimation = AssetLoadingHelper.loadPlayerIdleAnimation();
     *    BufferedImage[] walkAnimation = MANAGER.getSpriteFrames(
     *        AssetPathBridge.Characters.PLAYER_BASE + "walk_01.png",
     *        METADATA.AnimFrames.WEAPON_BIKER_WALK_A
     *    );
     *
     * 3. BENEFITS:
     *    - Single source of metadata (no duplicate frame counts)
     *    - Automatic caching at manager level
     *    - Type-safe constants (no magic strings)
     *    - Logging of all asset operations
     *    - Easy cache inspection & statistics
     */
}
