package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Config.java - PRODUCTION ASSET PATHS & CONFIGURATION
 * 
 * CRITICAL: All asset paths use REAL production assets from manifest
 * NO placeholder colors, NO dummy graphics - ONLY actual PNG/WAV files
 * 
 * Asset Base: Resources/industrial-zone/
 * Total Assets: 1,174 files across 8 categories
 * 
 * FAIL-FAST on missing assets - game will NOT start with incomplete assets
 */
public class Config {
    
    private static final String TAG = "[Config]";
    private static final boolean VERBOSE = true;
    
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    // ASSET BASE PATHS - Real production folders from manifest
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /** Main asset base directory */
    public static final String ASSET_BASE = "Resources/industrial-zone/";
    
    /** Character sprites (164 files) - Playable & Enemies */
    public static final String ASSET_CHARACTERS = ASSET_BASE + "characters/";
    
    /** Tileset graphics (hundreds of tiles) */
    public static final String ASSET_TILES = ASSET_BASE + "1 Tiles/";
    
    /** VFX animations (smoke, blood, explosions, impacts) */
    public static final String ASSET_VFX = ASSET_BASE + "vfx/";
    
    /** Audio files (music tracks + sound effects) */
    public static final String ASSET_AUDIO = ASSET_BASE + "audio/";
    
    /** GUI elements (buttons, panels, UI assets) */
    public static final String ASSET_GUI = ASSET_BASE + "gui/";
    
    /** Weapon sprite graphics (pistol, SMG, rifle, shotgun) */
    public static final String ASSET_WEAPONS = ASSET_BASE + "weapons/";
    
    /** Keyboard key visualization assets */
    public static final String ASSET_KEYBOARD_KEYS = ASSET_BASE + "KeyBoard_Keys/";
    
    /** Mouse button visualization assets */
    public static final String ASSET_MOUSE_KEYS = ASSET_BASE + "Mouse_keys/";
    
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    // CHARACTER ASSET PATHS - 3 Playable Characters
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    public static final String CHARACTER_BIKER = ASSET_CHARACTERS + "playable/Biker/";
    public static final String CHARACTER_PUNK = ASSET_CHARACTERS + "playable/Punk/";
    public static final String CHARACTER_CYBORG = ASSET_CHARACTERS + "playable/Cyborg/";
    
    // Character animation states (each character has these)
    public static final String ANIM_STATE_IDLE = "idle/";
    public static final String ANIM_STATE_WALK = "walk/";
    public static final String ANIM_STATE_JUMP = "jump/";
    public static final String ANIM_STATE_ATTACK = "attack/";
    public static final String ANIM_STATE_HIT = "hit/";
    public static final String ANIM_STATE_DEATH = "death/";
    
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    // ENEMY ASSET PATHS - 13 Total Enemy Types (Infantry + Drones + Bosses)
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    // Infantry enemies (3)
    public static final String ENEMY_INFANTRY_BASIC = ASSET_CHARACTERS + "enemies/infantry/1/";
    public static final String ENEMY_INFANTRY_RIFLE = ASSET_CHARACTERS + "enemies/infantry/2/";
    public static final String ENEMY_INFANTRY_HEAVY = ASSET_CHARACTERS + "enemies/infantry/3/";
    
    // Drone enemies (7)
    public static final String ENEMY_DRONE_UFO = ASSET_CHARACTERS + "enemies/drones/1/";
    public static final String ENEMY_DRONE_JET = ASSET_CHARACTERS + "enemies/drones/2/";
    public static final String ENEMY_DRONE_TRANSPORT = ASSET_CHARACTERS + "enemies/drones/3/";
    public static final String ENEMY_DRONE_SPHERE = ASSET_CHARACTERS + "enemies/drones/4/";
    public static final String ENEMY_DRONE_TWIN = ASSET_CHARACTERS + "enemies/drones/5/";
    public static final String ENEMY_DRONE_MISSILE = ASSET_CHARACTERS + "enemies/drones/6/";
    public static final String ENEMY_DRONE_TANK = ASSET_CHARACTERS + "enemies/drones/7/";
    
    // Boss enemies (3)
    public static final String ENEMY_BOSS_GOLF_SOLDIER = ASSET_CHARACTERS + "bosses/GolfCartSoldier/";
    public static final String ENEMY_BOSS_GREEN_MECH = ASSET_CHARACTERS + "bosses/GreenMech/";
    public static final String ENEMY_BOSS_RUGBY_GUY = ASSET_CHARACTERS + "bosses/RugbyGuy/";
    
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    // TILESET ASSET PATHS - Level tile graphics
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    public static final String TILESET_LEVEL1 = ASSET_TILES + "Level_Tilesets/Level1_IndustrialZone/";
    public static final String TILESET_LEVEL2 = ASSET_TILES + "Level_Tilesets/Level2_PowerStation/";
    public static final String BACKGROUND_LEVEL1 = ASSET_TILES + "Backgrounds/Level1_IndustrialZone/";
    public static final String BACKGROUND_LEVEL2 = ASSET_TILES + "Backgrounds/Level2_PowerStation/";
    
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    // VFX ANIMATION ASSET PATHS - Particle effects
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    public static final String VFX_SMOKE = ASSET_VFX + "1 Smoke/";
    public static final String VFX_BLOOD = ASSET_VFX + "2 Blood/";
    public static final String VFX_EXPLOSION = ASSET_VFX + "3 Explosions/";
    public static final String VFX_IMPACT = ASSET_VFX + "4 Impacts/";
    public static final String VFX_TELEPORT = ASSET_VFX + "5 Teleport/";
    
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    // AUDIO ASSET PATHS - Music & Sound Effects (OGG/WAV format)
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    public static final String AUDIO_MUSIC = ASSET_AUDIO + "music/";
    public static final String AUDIO_SFX = ASSET_AUDIO + "sfx/";
    
    // Music track names (5 tracks)
    public static final String MUSIC_MENU = "menu_theme.ogg";
    public static final String MUSIC_LEVEL1 = "level1_industrial.ogg";
    public static final String MUSIC_LEVEL2 = "level2_powerstation.ogg";
    public static final String MUSIC_BOSS_BATTLE = "boss_battle.ogg";
    public static final String MUSIC_VICTORY = "victory_theme.ogg";
    
    // Sound effects (11 types)
    public static final String SFX_JUMP = "sfx_jump.wav";
    public static final String SFX_SHOOT = "sfx_shoot.wav";
    public static final String SFX_DAMAGE = "sfx_damage.wav";
    public static final String SFX_EXPLODE = "sfx_explosion.wav";
    public static final String SFX_PICKUP = "sfx_pickup.wav";
    public static final String SFX_MENU_SELECT = "sfx_menu_select.wav";
    public static final String SFX_MENU_NAVIGATE = "sfx_menu_navigate.wav";
    public static final String SFX_LEVEL_COMPLETE = "sfx_level_complete.wav";
    public static final String SFX_GAME_OVER = "sfx_game_over.wav";
    public static final String SFX_DASH = "sfx_dash.wav";
    public static final String SFX_ENEMY_DEATH = "sfx_enemy_death.wav";
    
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    // GUI BUTTON ASSET PATHS - 9 Button Types
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    public static final String GUI_BUTTON_STANDARD = ASSET_GUI + "buttons/standard/";
    public static final String GUI_BUTTON_CYAN = ASSET_GUI + "buttons/cyan/";
    public static final String GUI_BUTTON_GREEN = ASSET_GUI + "buttons/green/";
    public static final String GUI_BUTTON_RED = ASSET_GUI + "buttons/red/";
    public static final String GUI_BUTTON_ORANGE = ASSET_GUI + "buttons/orange/";
    public static final String GUI_BUTTON_METAL = ASSET_GUI + "buttons/metal/";
    public static final String GUI_BUTTON_GLASS = ASSET_GUI + "buttons/glass/";
    public static final String GUI_BUTTON_HOLO = ASSET_GUI + "buttons/holo/";
    public static final String GUI_BUTTON_PRESSURE_PLATE = ASSET_GUI + "buttons/pressure_plate/";
    
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    // GAME MECHANICS CONSTANTS
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    // Display settings
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;
    public static final int TARGET_FPS = 60;
    public static final float TARGET_DELTA_TIME = 1.0f / TARGET_FPS; // ~0.0167 seconds
    
    // Physics constants
    public static final float GRAVITY = 0.6f;
    public static final float TERMINAL_VELOCITY = 12.0f;
    public static final float PLAYER_MOVE_SPEED = 4.0f;
    public static final float PLAYER_JUMP_POWER = -12.0f;
    
    // Tile constants
    public static final int TILE_SIZE = 32;
    public static final int TILES_WIDE = SCREEN_WIDTH / TILE_SIZE;  // 32 tiles
    public static final int TILES_TALL = SCREEN_HEIGHT / TILE_SIZE;  // 24 tiles
    
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    // ASSET VERIFICATION - CRITICAL FOR PRODUCTION
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Verify all critical asset folders exist before game starts
     * FAIL-FAST: Exit with code 1 if any critical asset missing
     * 
     * @return true if all critical assets present
     */
    public static boolean verifyAssetsExist() {
        List<String> criticalAssets = new ArrayList<>();
        criticalAssets.add(ASSET_CHARACTERS);
        criticalAssets.add(ASSET_TILES);
        criticalAssets.add(ASSET_VFX);
        criticalAssets.add(ASSET_AUDIO);
        criticalAssets.add(ASSET_GUI);
        criticalAssets.add(CHARACTER_BIKER);
        criticalAssets.add(CHARACTER_PUNK);
        criticalAssets.add(CHARACTER_CYBORG);
        criticalAssets.add(TILESET_LEVEL1);
        criticalAssets.add(TILESET_LEVEL2);
        
        boolean allExist = true;
        for (String assetPath : criticalAssets) {
            File assetFile = new File(assetPath);
            if (!assetFile.exists()) {
                System.err.println("CRITICAL: Missing asset folder - " + assetFile.getAbsolutePath());
                allExist = false;
            } else if (VERBOSE) {
                System.out.println(TAG + " ✓ Found: " + assetPath);
            }
        }
        
        if (!allExist) {
            System.err.println("\n" + TAG + " FATAL: Not all critical assets found!");
            System.err.println("Expected base: " + new File(ASSET_BASE).getAbsolutePath());
            return false;
        }
        
        if (VERBOSE) {
            System.out.println(TAG + " ✓ ALL CRITICAL ASSETS VERIFIED");
        }
        return true;
    }
    
    /**
     * Get full path to an asset file with existence check
     * 
     * @param path Relative asset path
     * @return Full path, null if file doesn't exist
     */
    public static String getAssetPath(String path) {
        File assetFile = new File(path);
        if (!assetFile.exists()) {
            System.err.println("WARNING: Asset file not found - " + assetFile.getAbsolutePath());
            return null;
        }
        return assetFile.getAbsolutePath();
    }
    
    /**
     * List all PNG files in asset folder (used for animation frame loading)
     * 
     * @param folderPath Path to folder containing frames
     * @return Array of PNG file paths sorted by name
     */
    public static String[] getAnimationFrames(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("ERROR: Animation folder not found - " + folderPath);
            return new String[0];
        }
        
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".png"));
        if (files == null) files = new File[0];
        
        // Sort by filename to maintain frame order
        java.util.Arrays.sort(files);
        
        String[] paths = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            paths[i] = files[i].getAbsolutePath();
        }
        return paths;
    }
}
