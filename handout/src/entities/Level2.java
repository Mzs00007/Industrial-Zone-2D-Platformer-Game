/*
 * Level 2 — Power Station
 * All level-specific data: platforms, enemies, animated objects, backgrounds, tile maps.
 * Game.java delegates to this class via the LevelData interface.
 */
package entities;

public class Level2 implements LevelData {

    // ── metadata ──
    public static final int    ID          = 2;
    public static final String NAME        = "Power Station";
    public static final String DIFF        = "Hard";
    public static final int    WORLD_W     = 28800;
    public static final float  START_X     = 150f;
    public static final float  START_Y     = 456f;   // ground(520) - 64

    // ── platforms  [x, y, width, height] ──
    private static final float[][] PLATFORMS = {
        // Ground
        {   0,  520, 28800, 800 },
        // Section 1: Power station entrance
        { 200,  440,  250,  20 },
        { 500,  370,  200,  20 },
        { 780,  300,  240,  20 },
        {1000,  230,  180,  20 },
        // Section 2: Reactor corridor
        {1300,  400,  300,  20 },
        {1650,  330,  200,  20 },
        {1900,  260,  250,  20 },
        {2160,  190,  200,  20 },
        {2160,  430,  300,  20 },
        // Section 3: Vertical shaft
        {2500,  460,  160,  20 },
        {2580,  380,  140,  20 },
        {2660,  300,  120,  20 },
        {2750,  220,  140,  20 },
        {2850,  160,  180,  20 },
        // Section 4: Catwalk gauntlet
        {3100,  350,  200,  20 },
        {3380,  280,  180,  20 },
        {3600,  210,  220,  20 },
        {3850,  280,  160,  20 },
        {4050,  350,  200,  20 },
        {4050,  160,  140,  20 },
        // Section 5: Mini-boss arena
        {4300,  420,  500,  20 },
        {4500,  260,  160,  20 },
        // Section 6: Final gauntlet
        {4900,  380,  200,  20 },
        {5150,  300,  220,  20 },
        {5400,  220,  200,  20 },
        {5400,  450,  300,  20 },
        // Section 7: Final boss arena
        {5750,  480,  700,  20 },
        {5900,  300,  150,  20 },
        {6100,  200,  120,  20 },
    };

    // ── enemies  [x, y, typeId]  0=UFO 1=JET 2=HOVER 3=LAND_TANK 4=LAND_KNIGHT 5=LAND_WARRIOR 6=BOSS_GOLF_CART 7=BOSS_GREEN_MECH 8=BOSS_RUGBY_GUY ──
    private static final float[][] ENEMIES = {
        // Drones
        { 350, 340, 1}, { 740, 280, 0}, {1510, 300, 1},
        {1870, 240, 0}, {2250, 200, 1}, {2950, 220, 0},
        {3300, 280, 1}, {4030, 260, 0}, {4400, 230, 1},
        {5100, 250, 1}, {5450, 270, 0},
        // Ground drones
        {1120, 472, 2}, {2600, 472, 2}, {3680, 472, 2}, {4750, 472, 2},
        // Land enemies (sci-fi-antagonists)
        {1600, 456, 3},   // CombatTank in reactor corridor
        {3200, 456, 4},   // ArmouredKnight at catwalk gauntlet
        {4900, 456, 5},   // WingedWarrior at final gauntlet
        {5300, 456, 3},   // CombatTank before final arena
        // Mid-boss: GreenMech at mini-boss arena
        {4300, 424, 7},
        // Final boss: RugbyGuy at final arena
        {5800, 424, 8},
    };

    // ── animated objects  [typeId, x, y, w, h] ──
    private static final float[][] OBJECTS = {
        // Money
        {1, 300,488, 32,32},{1, 650,488, 32,32},{1,1050,488, 32,32},{1,1500,488, 32,32},
        {1,2000,488, 32,32},{1,2500,488, 32,32},{1,3000,488, 32,32},{1,3500,488, 32,32},
        {1,4500,488, 32,32},{1,5200,488, 32,32},{1,5600,488, 32,32},{1,6000,488, 32,32},
        // Cards
        {0, 520,338, 32,32},{0, 810,268, 32,32},{0,1920,228, 32,32},
        {0,2680,268, 32,32},{0,3620,178, 32,32},
        // Chests
        {2,1200,472, 48,48},{2,2800,472, 48,48},{2,4200,472, 48,48},{2,5500,472, 48,48},
        // Turret hazards
        {8,3200,472, 48,48},{8,4800,472, 48,48},
        // Portals (checkpoints + exit)
        {5,2400,440, 64,80},{5,4600,440, 64,80},{5,6400,440, 64,80},
    };

    // ── animated-asset directory ──
    private static final String ANIM_DIR =
        "Resources/industrial-zone/1 Tiles/power-station-level-2/4 Animated objects/";
    private static final String ANIM_DIR_L1 =
        "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/";

    // ── weapon spawns  [weaponTypeId, x, y]  0=PISTOL 1=SMG 2=RIFLE 3=SHOTGUN ──
    private static final float[][] WEAPON_SPAWNS = {
        {0,  400, 488},   // Pistol early
        {1, 1650, 298},   // SMG on platform
        {2, 3100, 318},   // Rifle at catwalk
        {1, 4300, 388},   // SMG at mini-boss arena
        {3, 5400, 188},   // Shotgun at final gauntlet
    };

    // ── ladder zones  [x, y, width, height] ──
    private static final float[][] LADDER_ZONES = {
        {2480, 190, 32, 330},   // Vertical shaft area
        {3580, 210, 32, 310},   // Catwalk gauntlet
        {5380, 220, 32, 300},   // Final gauntlet
    };

    // ── background (day/night cycle) ──
    private static final String BG_DIR =
        "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/";
    private static final String[] BG_DAY_FILES = {
        "Day/BG_Layer1_SkyBase_LightGreyWhiteGradient_StaticFill_DrawFirst.png",
        "Day/BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png",
        "Day/BG_Layer3_FactoryTall_LightBlueChimney_ParallaxFactor025.png",
        "Day/BG_Layer4_DistantFactory_FaintSilhouette_ParallaxFactor040.png",
        "Day/BG_Layer5_FactoryRight_LightBlueVariant_ParallaxFactor060.png",
    };
    private static final String[] BG_NIGHT_FILES = {
        "Night/BG_Layer1_SkyBase_DarkGreyGradient_StaticFill_DrawFirst.png",
        "Night/BG_Layer2_FactoryLeft_DarkGreySilhouette_ParallaxFactor015.png",
        "Night/BG_Layer3_FactoryTall_DarkGreyCentre_ParallaxFactor025.png",
        "Night/BG_Layer4_DistantFactory_VeryDarkFaint_ParallaxFactor040.png",
        "Night/BG_Layer5_FactoryRight_DarkGreyVariant_ParallaxFactor060.png",
    };
    private static final String BG_OVERLAY =
        "BG_Overlay_BlueYellowDiagonalGradient_ColourAtmosphere_DrawLast.png";
    private static final float[] SCROLL = { 0.0f, 0.08f, 0.18f, 0.30f, 0.50f };

    // ── tile map ──
    private static final String MAP_FILE = "maps/level_2/map.txt";
    private static final String[] TILE_DIRS = {
        "Resources/industrial-zone/1 Tiles/power-station-level-2/1 Tiles/",
        "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/",
        "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/",
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
    @Override public float[][] getEnemySpawns()     { return ENEMIES; }
    @Override public float[][] getAnimatedObjects() { return OBJECTS; }

    @Override public String   getAnimAssetDir()   { return ANIM_DIR; }
    @Override public String   getBgDir()           { return BG_DIR; }
    @Override public String[] getBgFiles()          { return BG_DAY_FILES; } // default to day
    @Override public float[]  getScrollFactors()    { return SCROLL; }

    @Override public String[] getBgDayFiles()   { return BG_DAY_FILES; }
    @Override public String[] getBgNightFiles() { return BG_NIGHT_FILES; }
    @Override public String   getBgOverlay()    { return BG_OVERLAY; }

    @Override public String   getMapFilePath()     { return MAP_FILE; }
    @Override public String[] getTileMapDirs()      { return TILE_DIRS; }
    @Override public int      getTileMapOffsetY()   { return MAP_OFFSET_Y; }

    @Override public int     getCompletionScore()  { return 1500; }
    @Override public String  getCompletionTitle()  { return "CYBER RUNNER — VICTORIOUS!"; }
    @Override public String  getNextLevelPrompt()  { return "PRESS ENTER FOR MAIN MENU"; }
    @Override public boolean hasNextLevel()         { return false; }
    @Override public String[] getStoryLines() {
        return new String[] {
            "POWER STATION OFFLINE.",
            "The Mech Commander has been destroyed.",
            "The city power grid is fully restored.",
            "All hostile units: neutralized.",
            "CYBER RUNNER 2067  —  MISSION COMPLETE."
        };
    }

    @Override public float[][] getWeaponSpawns()    { return WEAPON_SPAWNS; }
    @Override public float[][] getLadderZones()      { return LADDER_ZONES; }

    @Override
    public String getAnimAssetPath(AnimatedObject.ObjType type) {
        switch (type) {
            case COLLECTIBLE_CARD:
                return ANIM_DIR + "Anim_Collectible_Card_6Frames1Row_BlueSpinningFloat_PickupItem_Loop80ms.png";
            case COLLECTIBLE_MONEY:
                return ANIM_DIR + "Anim_Collectible_Money_6Frames1Row_GreenBanknotesSpinFlip_CurrencyPickup_Loop80ms.png";
            case CHEST:
                return ANIM_DIR + "Anim_Interactive_Chest_8Frames1Row_BlueTealLidOpenSequence_PlayOnce100ms.png";
            case CONVEYOR:
                return ANIM_DIR_L1 + "Anim_Platform_ConveyorFull_4Frames1Row_FullWidthBeltRunning_MovesPlayerRight_Loop80ms.png";
            case CONVEYOR_REVERSE:
                return ANIM_DIR_L1 + "Anim_Platform_ConveyorFastVariant_4Frames1Row_AltSpeedOrDirection_Loop60ms.png";
            case PORTAL:
                return ANIM_DIR_L1 + "Anim_Portal_LevelEntry_4Frames1Row_RedChevronGateOpening_LevelTransition_PlayOnce120ms.png";
            case SCREEN_DECO:
                return ANIM_DIR_L1 + "Anim_Deco_Screen1_4Frames1Row_BlueMonitorFlicker_WallPanelTechDeco_Loop150ms.png";
            case HAZARD_HAMMER:
                return ANIM_DIR_L1 + "Anim_Hazard_Hammer_6Frames1Row_RedOrangeSwingArc_DamageFrames3to5_Loop90ms.png";
            case HAZARD_TURRET:
                return ANIM_DIR + "Anim_Hazard_Turret_MultiFrame1Row_TurretFiringProjectile_DamageOnFire_Loop120ms.png";
            case MOVING_PLATFORM:
                return ANIM_DIR_L1 + "Anim_Platform_MovingRed_6Frames1Row_SlidingLeftRight_PlayerRideable_Loop100ms.png";
            default: return null;
        }
    }
}
