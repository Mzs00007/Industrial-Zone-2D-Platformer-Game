package managers;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * ASSET METADATA v1.0 - Auto-extracted from assets-manifest.json
 * 
 * Contains all animation timing, frame counts, and sprite properties
 * ════════════════════════════════════════════════════════════════════════════
 */
public class AssetMetadata {
    
    private static final AssetMetadata instance = new AssetMetadata();
    
    private AssetMetadata() {}
    
    public static AssetMetadata getInstance() {
        return instance;
    }
    
    /**
     * Animation Frame Counts - extracted from manifest
     * Format: "XFrames" in description → stored as int
     */
    public static class AnimFrames {
        // VFX Animations
        public static final int VFX_SMOKE_DENSE = 18;
        public static final int VFX_BLOOD_SPLATTER = 4;
        
        // Weapon Animations - Biker
        public static final int WEAPON_BIKER_IDLE_A = 4;
        public static final int WEAPON_BIKER_IDLE_B = 4;
        public static final int WEAPON_BIKER_JUMP_A = 3;
        public static final int WEAPON_BIKER_JUMP_B = 3;
        public static final int WEAPON_BIKER_RUN_A = 5;
        public static final int WEAPON_BIKER_RUN_B = 5;
        public static final int WEAPON_BIKER_WALK_A = 4;
        public static final int WEAPON_BIKER_WALK_B = 4;
        
        // Weapon Animations - Punk
        public static final int WEAPON_PUNK_IDLE_A = 4;
        public static final int WEAPON_PUNK_IDLE_B = 4;
        public static final int WEAPON_PUNK_JUMP_A = 3;
        public static final int WEAPON_PUNK_JUMP_B = 3;
        public static final int WEAPON_PUNK_RUN_A = 5;
        public static final int WEAPON_PUNK_RUN_B = 5;
        public static final int WEAPON_PUNK_WALK_A = 4;
        public static final int WEAPON_PUNK_WALK_B = 4;
        
        // Weapon Animations - Cyborg
        public static final int WEAPON_CYBORG_IDLE_A = 3;
        public static final int WEAPON_CYBORG_IDLE_B = 3;
        public static final int WEAPON_CYBORG_JUMP_A = 3;
        public static final int WEAPON_CYBORG_JUMP_B = 3;
        public static final int WEAPON_CYBORG_RUN_A = 5;
        public static final int WEAPON_CYBORG_RUN_B = 5;
        public static final int WEAPON_CYBORG_WALK_A = 4;
        public static final int WEAPON_CYBORG_WALK_B = 4;
        
        // Boss Animations - Golf Soldier
        public static final int BOSS_GOLF_SOLDIER_IDLE = 4;
        public static final int BOSS_GOLF_SOLDIER_ATTACK = 5;
        public static final int BOSS_GOLF_SOLDIER_DEATH = 6;
        
        // Boss Animations - Green Mech
        public static final int BOSS_GREEN_MECH_IDLE = 4;
        public static final int BOSS_GREEN_MECH_ATTACK = 6;
        public static final int BOSS_GREEN_MECH_DEATH = 7;
        
        // Boss Animations - Rugby Guy
        public static final int BOSS_RUGBY_GUY_IDLE = 4;
        public static final int BOSS_RUGBY_GUY_ATTACK = 5;
        public static final int BOSS_RUGBY_GUY_DEATH = 5;
    }
    
    /**
     * Animation Frame Durations (milliseconds) - extracted from manifest
     * Format: "XYZms" in description → stored as int
     */
    public static class AnimTiming {
        // Standard timing values  
        public static final int MS_SLOW = 150;      // Idle animations
        public static final int MS_NORMAL = 100;    // Walk animations
        public static final int MS_FAST = 80;       // Run, jump, effects
        public static final int MS_INSTANT = 70;    // Attack animations
        
        // VFX Timings
        public static final int VFX_SMOKE = MS_FAST;        // 80ms
        public static final int VFX_BLOOD = MS_FAST;        // 80ms
        
        // Weapon Timings - Biker
        public static final int WEAPON_BIKER_IDLE = MS_SLOW;      // 150ms
        public static final int WEAPON_BIKER_JUMP = MS_FAST;      // 80ms
        public static final int WEAPON_BIKER_RUN = MS_FAST;       // 80ms
        public static final int WEAPON_BIKER_WALK = MS_NORMAL;    // 100ms
        
        // Weapon Timings - Punk
        public static final int WEAPON_PUNK_IDLE = MS_SLOW;       // 150ms
        public static final int WEAPON_PUNK_JUMP = MS_FAST;       // 80ms
        public static final int WEAPON_PUNK_RUN = MS_FAST;        // 80ms
        public static final int WEAPON_PUNK_WALK = MS_NORMAL;     // 100ms
        
        // Weapon Timings - Cyborg
        public static final int WEAPON_CYBORG_IDLE = MS_SLOW;     // 150ms
        public static final int WEAPON_CYBORG_JUMP = MS_FAST;     // 80ms
        public static final int WEAPON_CYBORG_RUN = MS_FAST;      // 80ms
        public static final int WEAPON_CYBORG_WALK = MS_NORMAL;   // 100ms
        
        // Boss Timings
        public static final int BOSS_IDLE = MS_SLOW;              // 150ms
        public static final int BOSS_ATTACK = MS_INSTANT;         // 70ms
        public static final int BOSS_DEATH = MS_NORMAL;           // 100ms
    }
    
    /**
     * Animation Loop Behavior - extracted from manifest
     * "Loop" → true, "PlayOnce" → false
     */
    public static class AnimBehavior {
        // Loops
        public static final boolean LOOP_IDLE = true;
        public static final boolean LOOP_WALK = true;
        public static final boolean LOOP_RUN = true;
        public static final boolean LOOP_VFX = true;
        
        // Play Once
        public static final boolean ONCE_JUMP = false;
        public static final boolean ONCE_ATTACK = false;
        public static final boolean ONCE_DEATH = false;
        public static final boolean ONCE_HIT = false;
    }
    
    /**
     * Sprite Dimensions (width x height in pixels) - extracted from manifest
     */
    public static class SpriteDimensions {
        // Standard sizes by category
        public static final int WIDTH_CHARACTER = 192;       // Characters, weapons
        public static final int HEIGHT_CHARACTER = 48;
        
        public static final int WIDTH_VFX = 192;             // Effects, impact
        public static final int HEIGHT_VFX = 48;
        
        public static final int WIDTH_GUI = 32;              // UI elements
        public static final int HEIGHT_GUI = 32;
        
        public static final int WIDTH_TILE = 32;             // Tiles
        public static final int HEIGHT_TILE = 32;
        
        // Wide effect variants
        public static final int WIDTH_VFX_WIDE = 288;
        public static final int HEIGHT_VFX_WIDE = 48;
    }
    
    /**
     * Summary statistics from manifest
     * Category counts auto-generated
     */
    public static class CategoryStats {
        public static final int TOTAL_VFX_ASSETS = 82;
        public static final int TOTAL_GUI_ASSETS = 290;
        public static final int TOTAL_AUDIO_ASSETS = 70;
        public static final int TOTAL_MOUSE_KEYS = 21;
        public static final int TOTAL_WEAPONS = 212;
        public static final int TOTAL_KEYBOARD_KEYS = 66;
        public static final int TOTAL_CHARACTER_ASSETS = 164;
        public static final int TOTAL_1_TILES = 269;
        
        // Animated vs Static breakdown
        public static final int VFX_ANIMATED = 79;
        public static final int VFX_STATIC = 3;
        public static final int WEAPONS_ANIMATED = 30;
        public static final int WEAPONS_STATIC = 182;
        public static final int CHARACTERS_ANIMATED = 147;
        public static final int CHARACTERS_STATIC = 17;
        public static final int GUI_ANIMATED = 88;
        public static final int GUI_STATIC = 202;
    }
}
