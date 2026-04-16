/*
 * Level 1 — Industrial Zone Entry
 * All level-specific data: platforms, enemies, animated objects, backgrounds, tile maps.
 * Game.java delegates to this class via the LevelData interface.
 */
package entities;

public class Level1 implements LevelData {

    // ── metadata ──
    public static final int    ID          = 1;
    public static final String NAME        = "Industrial Zone Entry";
    public static final String DIFF        = "Medium";
    public static final int    WORLD_W     = 16000;
    public static final float  START_X     = 150f;
    public static final float  START_Y     = 456f;   // ground(520) - 64

    // ── platforms  [x, y, width, height] ──
    // Ground floor is broken into segments — cliffs (pits) between them.
    // Pit ranges:  [1900..2100], [3100..3300], [4400..4600]
    //   Player falls through → triggers respawn at checkpoint.
    //   Ground enemies use cliff-edge detection so they don't walk into pits.
    private static final float[][] PLATFORMS = {
        // Ground segments (NOT continuous — cliffs in between)
        {   0,  520, 1900, 800 },   // Segment A: start zone
        {2100,  520, 1000, 800 },   // Segment B: mid zone
        {3300,  520, 1100, 800 },   // Segment C: zigzag base
        {4600,  520, 11400, 800 },  // Segment D: boss + endgame (long)
        // Section 1: Gentle start
        { 250,  440,  200,  20 },
        { 520,  400,  180,  20 },
        { 780,  350,  220,  20 },
        // Section 2: Step-up sequence
        {1060,  300,  160,  20 },
        {1280,  250,  200,  20 },
        {1520,  200,  180,  20 },
        {1520,  440,  250,  20 },
        {1780,  280,  220,  20 },
        // Pit-A bridge platforms (help cross 1900..2100 gap) — ONE-WAY
        {1920,  460,  160,  20 },
        // Section 3: Gap challenge
        {2100,  350,  150,  20 },
        {2350,  280,  150,  20 },
        {2600,  220,  200,  20 },
        {2600,  420,  300,  20 },
        // Section 4: Zigzag ascent
        {2950,  380,  180,  20 },
        // Pit-B bridge platforms — ONE-WAY
        {3130,  470,  150,  20 },
        {3200,  310,  160,  20 },
        {3420,  240,  200,  20 },
        {3680,  180,  180,  20 },
        {3680,  400,  250,  20 },
        // Section 5: Boss arena
        {4000,  350,  400,  20 },
        {4100,  200,  140,  20 },
        // Pit-C bridge platforms — ONE-WAY
        {4420,  460,  160,  20 },
        {4500,  280,  250,  20 },
        {4800,  480,  600,  20 },
        {4950,  320,  120,  20 },
    };

    // ── platform types  0=normal, 1=one-way (passable from below), 2=moving ──
    // Type mapping by platform index:
    //   0-3:  ground segments — NORMAL
    //   4-11: small platforms — NORMAL
    //   12:   Pit-A bridge — ONE-WAY
    //   13-16: gap challenge platforms — NORMAL
    //   17:   zigzag ascent — NORMAL
    //   18:   Pit-B bridge — ONE-WAY
    //   19-22: remaining platforms — NORMAL
    //   23-24: boss arena platforms — NORMAL
    //   25:   Pit-C bridge — ONE-WAY
    //   26-28: final platforms — NORMAL
    private static final int[] PLATFORM_TYPES = {
        0, 0, 0, 0,  // Ground segments: NORMAL
        0, 0, 0,     // Section 1: NORMAL
        0, 0, 0, 0, 0,  // Section 2: NORMAL
        1,           // Pit-A bridge: ONE-WAY
        0, 0, 0, 0,  // Gap challenge: NORMAL
        0,           // Zigzag: NORMAL
        1,           // Pit-B bridge: ONE-WAY
        0, 0, 0, 0,  // Remaining: NORMAL
        0, 0,        // Boss arena: NORMAL
        1,           // Pit-C bridge: ONE-WAY
        0, 0, 0,     // Final platforms: NORMAL
    };

    // ── enemies  [x, y, typeId]  0=UFO 1=JET 2=HOVER 3=LAND_TANK 4=LAND_KNIGHT 5=LAND_WARRIOR 6=BOSS_GOLF_CART 7=BOSS_GREEN_MECH 8=BOSS_RUGBY_GUY ──
    // RULE: air enemies (UFO/JET) are sparse in ground-enemy zones to avoid overwhelm.
    //       HOVER drones and LAND enemies are paired on the ground floor.
    //       Enemies are positioned BETWEEN pits so they don't fall in.
    private static final float[][] ENEMIES = {
        // Air drones (thinned out — only where no ground troops)
        { 480, 360, 0}, { 850, 300, 1},
        {1780, 280, 0}, {2720, 240, 0},
        {3430, 250, 0},
        // Ground floor HOVER drones (skim low)
        { 680, 472, 2}, {1480, 472, 2}, {2650, 472, 2}, {3780, 472, 2},
        // Land troops — patrol BETWEEN pits
        { 400, 456, 5},   // WingedWarrior early scout (Segment A)
        {1300, 456, 3},   // CombatTank mid-Segment A
        {1680, 456, 4},   // ArmouredKnight near Pit-A
        {2400, 456, 3},   // CombatTank in Segment B
        {2900, 456, 4},   // ArmouredKnight before Pit-B
        {3600, 456, 5},   // WingedWarrior in Segment C
        {4050, 456, 4},   // Knight at boss ramp
        // BOSS: GolfCartSoldier at boss arena (Segment D)
        {4800, 424, 6},
    };

    // ── animated objects  [typeId, x, y, w, h] ──
    // Traps (hammers + turrets) placed near pits and on ground floor.
    private static final float[][] OBJECTS = {
        // Money — placed in each ground segment (avoid pits)
        {1, 350,488, 32,32},{1, 700,488, 32,32},{1,1100,488, 32,32},{1,1600,488, 32,32},
        {1,2200,488, 32,32},{1,2800,488, 32,32},{1,3500,488, 32,32},{1,3900,488, 32,32},
        {1,4700,488, 32,32},{1,5000,488, 32,32},
        // NOTE: Cards are NOT pre-placed. They are spawned when the player
        // opens a chest with E — enforcing the invariant:
        //     # of boxes (chests)  ==  # of cards  ==  # of checkpoints
        // Chests (on ground in each segment) — 4 chests → 4 cards → 4 portals.
        {2, 950,472, 48,48},{2,1800,472, 48,48},{2,2850,472, 48,48},{2,3850,472, 48,48},
        // Conveyors
        {3,2600,388, 160,32},{3,3680,368, 160,32},
        // Portals (checkpoints) — placed in each ground segment
        {5, 700,440, 64,80},   // Checkpoint 1 — early (free save)
        {5,2650,440, 64,80},   // Checkpoint 2 — mid (after Pit-A)
        {5,3850,440, 64,80},   // Checkpoint 3 — before boss ramp
        {5,5200,440, 64,80},   // Level exit portal
        // Screens (decor)
        {6, 200,456, 48,64},{6,1400,456, 48,64},{6,3700,456, 48,64},
        // Hammer hazards — at pit edges to punish bad jumps
        {7,1870,472, 48,48},   // edge of Pit-A
        {7,2080,472, 48,48},   // other side of Pit-A
        {7,3080,472, 48,48},   // edge of Pit-B
        {7,3300,472, 48,48},   // other side of Pit-B
        {7,4380,472, 48,48},   // edge of Pit-C
        // Turret hazards — ground-mounted gunners
        {8,2450,472, 48,48},
        {8,3600,472, 48,48},
        // Moving platform (crosses Pit-A)
        {9,1880,440, 96,24},
    };

    // ── animated-asset directory ──
    private static final String ANIM_DIR =
        "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/";

    // ── weapon spawns  [weaponTypeId, x, y]  0=PISTOL 1=SMG 2=RIFLE 3=SHOTGUN ──
    private static final float[][] WEAPON_SPAWNS = {
        {0,  600, 488},   // Pistol near start
        {1, 1800, 248},   // SMG on high platform
        {2, 3200, 278},   // Rifle mid-level
        {3, 4500, 248},   // Shotgun near boss
    };

    // ── ladder zones  [x, y, width, height] ──
    private static final float[][] LADDER_ZONES = {
        {1040, 300, 32, 220},   // Ladder up to section 2 platforms
        {2580, 220, 32, 300},   // Ladder at gap challenge area
        {3660, 180, 32, 340},   // Ladder at zigzag ascent
    };

    // ── background ──
    private static final String BG_DIR =
        "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/";
    private static final String[] BG_FILES = {
        "BG_Layer1_SkyBase_SolidLavenderGrey_StaticFill_DrawFirstNoScroll.png",
        "BG_Layer2_FractalTreeSilhouette_MintSkyBlackCracks_ParallaxFactor015.png",
        "BG_Layer3_FarFactorySilhouette_LightBlueIndustrial_ParallaxFactor025.png",
        "BG_Layer4_MidFactorySilhouette_MediumBluePipeDetail_ParallaxFactor040.png",
        "BG_Layer5_NearFactorySilhouette_DarkNavyLargeTank_ParallaxFactor060.png",
    };
    private static final float[] SCROLL = { 0.0f, 0.08f, 0.18f, 0.30f, 0.50f };

    // ── tile map ──
    private static final String MAP_FILE = "maps/level_1/map.txt";
    private static final String[] TILE_DIRS = {
        "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/",
        "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/",
    };
    private static final int MAP_OFFSET_Y = 424;

    // ======================================================================
    //  LevelData implementation
    // ======================================================================
    @Override public int    getLevelId()       { return ID; }
    @Override public String getLevelName()     { return NAME; }
    @Override public String getDifficulty()    { return DIFF; }
    @Override public int    getPixelWidth()    { return WORLD_W; }
    @Override public float  getPlayerStartX()  { return START_X; }
    @Override public float  getPlayerStartY()  { return START_Y; }

    @Override public float[][] getPlatforms()       { return PLATFORMS; }
    @Override public int[]     getPlatformTypes()   { return PLATFORM_TYPES; }
    @Override public float[][] getEnemySpawns()     { return ENEMIES; }
    @Override public float[][] getAnimatedObjects() { return OBJECTS; }

    @Override public String   getAnimAssetDir()   { return ANIM_DIR; }
    @Override public String   getBgDir()           { return BG_DIR; }
    @Override public String[] getBgFiles()          { return BG_FILES; }
    @Override public float[]  getScrollFactors()    { return SCROLL; }

    @Override public String   getMapFilePath()     { return MAP_FILE; }
    @Override public String[] getTileMapDirs()      { return TILE_DIRS; }
    @Override public int      getTileMapOffsetY()   { return MAP_OFFSET_Y; }

    @Override public int     getCompletionScore()  { return 500; }
    @Override public String  getCompletionTitle()  { return "INDUSTRIAL ZONE — SECURED"; }
    @Override public String  getNextLevelPrompt()  { return "PRESS ENTER FOR LEVEL 2"; }
    @Override public boolean hasNextLevel()         { return true; }
    @Override public String[] getStoryLines() {
        return new String[] {
            "MISSION COMPLETE — Zone Alpha cleared.",
            "Enemy drone patrols have been neutralized.",
            "Industrial data core: successfully extracted.",
            "New objective: Infiltrate the Power Station.",
            "The real threat still lies ahead..."
        };
    }

    @Override public float[][] getWeaponSpawns()    { return WEAPON_SPAWNS; }
    @Override public float[][] getLadderZones()      { return LADDER_ZONES; }

    @Override
    public String getAnimAssetPath(AnimatedObject.ObjType type) {
        switch (type) {
            case COLLECTIBLE_CARD:
                return ANIM_DIR + "Anim_Collectible_Card_6Frames1Row_WhiteBlueSpinningFloat_PickupItem_Loop80ms.png";
            case COLLECTIBLE_MONEY:
                return ANIM_DIR + "Anim_Collectible_Money_6Frames1Row_GreenBanknotesSpinFlip_CurrencyPickup_Loop80ms.png";
            case CHEST:
                return ANIM_DIR + "Anim_Interactive_Chest_8Frames1Row_OrangeRedLidOpenSequence_PlayOnce100ms.png";
            case CONVEYOR:
                return ANIM_DIR + "Anim_Platform_ConveyorFull_4Frames1Row_FullWidthBeltRunning_MovesPlayerRight_Loop80ms.png";
            case CONVEYOR_REVERSE:
                return ANIM_DIR + "Anim_Platform_ConveyorFastVariant_4Frames1Row_AltSpeedOrDirection_Loop60ms.png";
            case PORTAL:
                return ANIM_DIR + "Anim_Portal_LevelEntry_4Frames1Row_RedChevronGateOpening_LevelTransition_PlayOnce120ms.png";
            case SCREEN_DECO:
                return ANIM_DIR + "Anim_Deco_Screen1_4Frames1Row_BlueMonitorFlicker_WallPanelTechDeco_Loop150ms.png";
            case HAZARD_HAMMER:
                return ANIM_DIR + "Anim_Hazard_Hammer_6Frames1Row_RedOrangeSwingArc_DamageFrames3to5_Loop90ms.png";
            case HAZARD_TURRET:
                return ANIM_DIR + "Anim_Hazard_Turret_MultiFrame1Row_TurretFiringProjectile_DamageOnFire_Loop120ms.png";
            case MOVING_PLATFORM:
                return ANIM_DIR + "Anim_Platform_MovingRed_6Frames1Row_SlidingLeftRight_PlayerRideable_Loop100ms.png";
            default: return null;
        }
    }
}
