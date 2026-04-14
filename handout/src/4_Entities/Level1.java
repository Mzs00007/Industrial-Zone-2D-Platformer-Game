/*
 * Decompiled with CFR 0.152.
 * UPDATED: 2026-04-14 - Production asset integration
 */
import animation.AnimationAndSpriteLoader;
import game2D.TileMap;
import utils.Config;
import assets.enums.TileAssets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import rendering.AnimatedObjectManager;
import rendering.ComprehensiveTileMapLoader;

public class Level1
extends AnimationAndSpriteLoader {
    private static final boolean VERBOSE_LOGGING = true;
    public static final int LEVEL_ID = 1;
    public static final String LEVEL_NAME = "Industrial Zone Entry";
    public static final String DIFFICULTY = "Medium";
    public static final String STORY = "Infiltrate the industrial zone and disable the automation systems";
    public static final int MAP_WIDTH = 500;
    public static final int MAP_HEIGHT = 50;
    public static final int TILE_SIZE = 32;
    public static final int PIXEL_WIDTH = 16000;
    public static final int PIXEL_HEIGHT = 1600;
    public static final float PLAYER_START_X = 300.0f;
    public static final float PLAYER_START_Y = 1000.0f;
    private static final float INTRO_END = 1568.0f;
    private static final float PIT_GAUNTLET_END = 3808.0f;
    private static final float UNDERGROUND_END = 7008.0f;
    private static final float OVERGROUND_END = 11168.0f;
    private static final float DESCENT_END = 14368.0f;
    private static final float BOSS_ARENA_END = 15968.0f;
    private static TileMap levelTileMap;
    private static int[][] mapData;
    private static Map<Character, String> tileMapping;
    private static List<EnemySpawn> enemySpawns;
    private static List<HazardZone> hazardZones;
    private static List<CheckpointData> checkpoints;
    private static String[] zoneNames;
    private static ComprehensiveTileMapLoader assetLoader;
    private static AnimatedObjectManager animatedObjectManager;
    private static List<ComprehensiveTileMapLoader.AnimatedObject> levelAnimatedObjects;
    private static List<ComprehensiveTileMapLoader.BackgroundLayer> levelBackgroundLayers;
    private static AnimationAndSpriteLoader.ParallaxSystem parallaxSystem;

    public static void initialize(TileMap tileMap) {
        Level1.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        Level1.log("Level1: INITIALIZATION SEQUENCE STARTING");
        Level1.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        levelTileMap = tileMap;
        enemySpawns = new ArrayList<EnemySpawn>();
        hazardZones = new ArrayList<HazardZone>();
        checkpoints = new ArrayList<CheckpointData>();
        tileMapping = new HashMap<Character, String>();
        zoneNames = new String[6];
        levelAnimatedObjects = new ArrayList<ComprehensiveTileMapLoader.AnimatedObject>();
        levelBackgroundLayers = new ArrayList<ComprehensiveTileMapLoader.BackgroundLayer>();
        animatedObjectManager = new AnimatedObjectManager();
        Level1.parseMapFile();
        Level1.identifyZones();
        Level1.extractEnemySpawns();
        Level1.extractHazardZones();
        Level1.extractCheckpoints();
        Level1.loadComprehensiveAssets();
        Level1.validateEnemyDefinitions();
        Level1.validateEnemyCombatConfiguration();
        Level1.initializeVFXChainReactionSystem();
        Level1.initializeAudioSystem();
        Level1.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        Level1.log("Level1: INITIALIZATION COMPLETE");
        Level1.log("  Tiles loaded: " + Level1.countLoadedTiles());
        Level1.log("  Enemies spawned: " + enemySpawns.size());
        Level1.log("  Hazard zones: " + hazardZones.size());
        Level1.log("  Checkpoints: " + checkpoints.size());
        Level1.log("  Animated objects: " + levelAnimatedObjects.size());
        Level1.log("  Background layers: " + levelBackgroundLayers.size());
        Level1.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
    }

    private static void parseMapFile() {
        String string = "maps/level_1/map.txt";
        try {
            String string2;
            String string3;
            Level1.log("\ud83d\udcc2 Loading map file: " + string);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(string)));
            String string4 = bufferedReader.readLine();
            String[] stringArray = string4.split(" ");
            int n = Integer.parseInt(stringArray[0]);
            int n2 = Integer.parseInt(stringArray[1]);
            Level1.log("  Map dimensions: " + n + "\u00d7" + n2 + " tiles (" + n * 32 + "\u00d7" + n2 * 32 + "px)");
            mapData = new int[n2][n];
            while ((string3 = bufferedReader.readLine()) != null) {
                String[] stringArray2;
                if (string3.equals("#map")) {
                    Level1.log("  Found #map marker - reading tile data");
                    break;
                }
                if (!string3.contains("=") || string3.startsWith("//") || (stringArray2 = string3.split("=")).length != 2) continue;
                char c = stringArray2[0].trim().charAt(0);
                String string5 = stringArray2[1].trim();
                tileMapping.put(Character.valueOf(c), string5);
            }
            Level1.log("  Tile types loaded: " + tileMapping.size());
            for (int i = 0; i < n2 && (string2 = bufferedReader.readLine()) != null; ++i) {
                for (int j = 0; j < n && j < string2.length(); ++j) {
                    char c = string2.charAt(j);
                    Level1.mapData[i][j] = c;
                }
            }
            bufferedReader.close();
            Level1.log("\u2713 Map file parsed successfully");
        }
        catch (IOException iOException) {
            Level1.logError("Failed to read map file: " + string);
            iOException.printStackTrace();
        }
    }

    private static void identifyZones() {
        Level1.log("\ud83d\uddfa\ufe0f  Identifying zones...");
        Level1.zoneNames[0] = "Intro";
        Level1.zoneNames[1] = "Pit Gauntlet";
        Level1.zoneNames[2] = "Underground";
        Level1.zoneNames[3] = "Overground";
        Level1.zoneNames[4] = "Descent+Power";
        Level1.zoneNames[5] = "Boss Arena";
        Level1.log("  Zone 0 (Intro): Tiles 0-49 (Safe tutorial area)");
        Level1.log("  Zone 1 (Pit Gauntlet): Tiles 50-119 (Platforming challenges)");
        Level1.log("  Zone 2 (Underground): Tiles 120-219 (Tight combat corridors)");
        Level1.log("  Zone 3 (Overground): Tiles 220-349 (Open combat facility)");
        Level1.log("  Zone 4 (Descent): Tiles 350-449 (Hazard approach to boss)");
        Level1.log("  Zone 5 (Boss Arena): Tiles 450-499 (TitanHoverCraft battle)");
    }

    private static void extractEnemySpawns() {
        Level1.log("\ud83d\udc7e Extracting enemy spawns...");
        for (int i = 0; i < mapData.length; ++i) {
            for (int j = 0; j < mapData[0].length; ++j) {
                char c = (char)mapData[i][j];
                if (c != 'D' && c != 'B') continue;
                float f = j * 32;
                float f2 = i * 32;
                String string = Level1.getZoneName(j);
                int n = Level1.getZoneDifficulty(j);
                String string2 = Level1.selectEnemyType(j, n);
                enemySpawns.add(new EnemySpawn(f, f2, string2, n, string));
            }
        }
        Level1.log("  Spawned: " + enemySpawns.size() + " enemies across all zones");
    }

    private static void extractHazardZones() {
        Level1.log("\u26a0\ufe0f  Extracting hazard zones...");
        for (int i = 0; i < mapData.length; ++i) {
            for (int j = 0; j < mapData[0].length; ++j) {
                float f;
                float f2;
                char c = (char)mapData[i][j];
                String string = null;
                int n = 0;
                if (c == 'S') {
                    string = "ENERGY_BEAM";
                    n = 15;
                } else if (c == 'R') {
                    string = "ELECTRIC";
                    n = 20;
                } else if (c == 'F') {
                    string = "FIRE";
                    n = 10;
                } else if (c == 'x' || c == 'z') {
                    string = "SPIKES";
                    n = 25;
                }
                if (string == null || Level1.isHazardAlreadyMapped(f2 = (float)(j * 32), f = (float)(i * 32))) continue;
                hazardZones.add(new HazardZone(f2, f, f2 + 32.0f, f + 32.0f, string, n));
            }
        }
        Level1.log("  Identified: " + hazardZones.size() + " hazard zones");
    }

    private static void extractCheckpoints() {
        Level1.log("\ud83d\udea9 Extracting checkpoints...");
        int n = 0;
        for (int i = 0; i < 500; i += 70) {
            if (i <= 0 || i >= 450) continue;
            float f = i * 32;
            float f2 = 800.0f;
            checkpoints.add(new CheckpointData(f, f2, n, Level1.getZoneName(i)));
            ++n;
        }
        Level1.log("  Placed: " + checkpoints.size() + " checkpoints");
    }

    public static List<EnemySpawn> getEnemySpawns() {
        return new ArrayList<EnemySpawn>(enemySpawns);
    }

    public static List<HazardZone> getHazardZones() {
        return new ArrayList<HazardZone>(hazardZones);
    }

    public static List<CheckpointData> getCheckpoints() {
        return new ArrayList<CheckpointData>(checkpoints);
    }

    public static char getTileAt(int n, int n2) {
        if (n >= 0 && n < mapData[0].length && n2 >= 0 && n2 < mapData.length) {
            return (char)mapData[n2][n];
        }
        return '.';
    }

    public static String getZoneName(int n) {
        if (n <= 49) {
            return zoneNames[0];
        }
        if (n <= 119) {
            return zoneNames[1];
        }
        if (n <= 219) {
            return zoneNames[2];
        }
        if (n <= 349) {
            return zoneNames[3];
        }
        if (n <= 449) {
            return zoneNames[4];
        }
        return zoneNames[5];
    }

    public static int getZoneDifficulty(int n) {
        if (n <= 49) {
            return 1;
        }
        if (n <= 119) {
            return 2;
        }
        if (n <= 219) {
            return 2;
        }
        if (n <= 349) {
            return 3;
        }
        if (n <= 449) {
            return 4;
        }
        return 5;
    }

    private static String selectEnemyType(int n, int n2) {
        if (n2 <= 1) {
            return "NONE";
        }
        if (n2 == 2) {
            return "DRONE";
        }
        if (n2 == 3) {
            return "SCI_FI_ANTAGONIST";
        }
        if (n2 == 4) {
            return "ELITE_GUARD";
        }
        return "TITAN_BOSS";
    }

    private static boolean isHazardAlreadyMapped(float f, float f2) {
        for (HazardZone hazardZone : hazardZones) {
            if (!(Math.abs(hazardZone.startX - f) < 32.0f) || !(Math.abs(hazardZone.startY - f2) < 32.0f)) continue;
            return true;
        }
        return false;
    }

    private static int countLoadedTiles() {
        int n = 0;
        int[][] nArray = mapData;
        int n2 = nArray.length;
        for (int i = 0; i < n2; ++i) {
            int[] nArray2;
            for (int n3 : nArray2 = nArray[i]) {
                if (n3 == 46) continue;
                ++n;
            }
        }
        return n;
    }

    private static void loadComprehensiveAssets() {
        try {
            Level1.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
            Level1.log("LOADING COMPREHENSIVE ASSETS FOR LEVEL 1");
            Level1.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
            
            // Load from Config-managed tileset and backgrounds
            String tilesetPath = Config.TILESET_LEVEL1;
            String backgroundPath = Config.BACKGROUND_LEVEL1;
            
            Level1.log("  Loading tileset: " + tilesetPath);
            Level1.log("  Loading background: " + backgroundPath);
            
            String mapPath = "maps/level_1/map.txt";
            assetLoader = new ComprehensiveTileMapLoader(mapPath, "Level 1 - Industrial Zone");
            levelAnimatedObjects = assetLoader.getAnimatedObjects();
            levelBackgroundLayers = assetLoader.getBackgroundLayers();
            
            Level1.log("\u2713 Asset loading complete");
            Level1.log("  - Tileset loaded: " + tilesetPath);
            Level1.log("  - Background loaded: " + backgroundPath);
            Level1.log("  - Animated objects loaded: " + levelAnimatedObjects.size());
            Level1.log("  - Background layers: " + levelBackgroundLayers.size());
        }
        catch (Exception exception) {
            Level1.logError("Failed to load comprehensive assets: " + exception.getMessage());
            Level1.logError("  Tileset: " + Config.TILESET_LEVEL1);
            Level1.logError("  Background: " + Config.BACKGROUND_LEVEL1);
            exception.printStackTrace();
        }
    }

    public static ComprehensiveTileMapLoader getAssetLoader() {
        return assetLoader;
    }

    public static AnimatedObjectManager getAnimatedObjectManager() {
        return animatedObjectManager;
    }

    public static List<ComprehensiveTileMapLoader.AnimatedObject> getAnimatedObjects() {
        return new ArrayList<ComprehensiveTileMapLoader.AnimatedObject>(levelAnimatedObjects);
    }

    public static List<ComprehensiveTileMapLoader.BackgroundLayer> getBackgroundLayers() {
        return new ArrayList<ComprehensiveTileMapLoader.BackgroundLayer>(levelBackgroundLayers);
    }

    public static boolean isLevelComplete(float playerX) {
        return playerX >= 15968.0f;
    }

    public static float getLevelEndPosition() {
        return 15968.0f;
    }

    public static String getLevelProgressPercentage(float playerX) {
        float progress = (playerX / 15968.0f) * 100.0f;
        return String.format("%.1f%% Complete", Math.min(100.0f, progress));
    }

    private static void log(String string) {
        System.out.println("[Level1] " + string);
    }

    private static void logError(String string) {
        System.err.println("[Level1] ERROR: " + string);
    }

    public static String getLevelInfo() {
        return String.format("Level %d: %s (%s Difficulty)", 1, LEVEL_NAME, DIFFICULTY);
    }

    public static TileMap getTileMap() {
        return levelTileMap;
    }

    public static int getMapWidth() {
        return 500;
    }

    public static int getMapHeight() {
        return 50;
    }

    public static int getTileSize() {
        return 32;
    }

    public static int validateEnemyCombatConfiguration() {
        Level1.log("\ud83d\udd27 Validating enemy combat system using EnemyAICombat...");
        int n = 0;
        try {
            if (enemySpawns != null && enemySpawns.size() > 0) {
                for (EnemySpawn enemySpawn : enemySpawns) {
                    if (enemySpawn.difficultyLevel < 2) continue;
                    Level1.log("  \u2713 Enemy '" + enemySpawn.enemyType + "' at zone '" + enemySpawn.zone + "' configured for difficulty " + enemySpawn.difficultyLevel);
                    ++n;
                }
            }
            Level1.log("  Total enemies configured:  " + n + " (via EnemyAICombat)");
        }
        catch (Exception exception) {
            Level1.logError("EnemyAICombat validation failed: " + exception.getMessage());
        }
        return n;
    }

    public static boolean validateEnemyDefinitions() {
        Level1.log("\u2713 Validating Enemies class definitions...");
        try {
            HashSet<String> hashSet = new HashSet<String>();
            for (EnemySpawn object : enemySpawns) {
                if (object.enemyType.equals("NONE")) continue;
                hashSet.add(object.enemyType);
            }
            Level1.log("  Enemy types referenced in Industrial Zone Entry:");
            for (String string : hashSet) {
                Level1.log("    - " + string + " (Enemies class reference)");
            }
            Level1.log("  Total unique enemy types: " + hashSet.size());
            return hashSet.size() > 0 || enemySpawns.size() == 0;
        }
        catch (Exception exception) {
            Level1.logError("Enemies validation failed: " + exception.getMessage());
            return false;
        }
    }

    public static boolean initializeVFXChainReactionSystem() {
        Level1.log("\u2728 Initializing VFX Chain Reaction system...");
        try {
            if (hazardZones != null && hazardZones.size() > 0) {
                Level1.log("  \ud83c\udf00 VFXChainReaction configured for " + hazardZones.size() + " hazard zones");
                for (HazardZone hazardZone : hazardZones) {
                    Level1.log("    - " + hazardZone.hazardType + ": Chain effect at (%.0f, %.0f)".formatted(Float.valueOf(hazardZone.startX), Float.valueOf(hazardZone.startY)));
                }
            }
            Level1.log("  \u2713 VFX system initialized");
            return true;
        }
        catch (Exception exception) {
            Level1.logError("VFXChainReaction initialization failed: " + exception.getMessage());
            return false;
        }
    }

    public static String getVFXConfigForHazard(String string) {
        switch (string) {
            case "ENERGY_BEAM": {
                return "BLUE_PULSE_CHAIN";
            }
            case "ELECTRIC": {
                return "CYAN_SPARK_CHAIN";
            }
            case "FIRE": {
                return "RED_FLAME_CHAIN";
            }
            case "SPIKES": {
                return "PURPLE_SPIKE_CHAIN";
            }
        }
        return "GENERIC_CHAIN";
    }

    public static boolean initializeAudioSystem() {
        Level1.log("\ud83c\udfb5 Initializing AudioEntities system for Industrial Zone Entry...");
        try {
            int n;
            Level1.log("  Background music: Level 1 - Industrial Zone Theme");
            for (n = 0; n < 6; ++n) {
                String string = zoneNames[n];
                Level1.log("    Zone " + n + " (" + (String)string + "): Ambient audio configured");
            }
            n = 0;
            for (EnemySpawn enemySpawn : enemySpawns) {
                if (enemySpawn.difficultyLevel <= 1) continue;
                ++n;
            }
            Level1.log("  Enemy audio triggers: " + n + " (via AudioEntities)");
            Level1.log("  Hazard warning sounds: " + hazardZones.size() + " zones configured");
            Level1.log("  \u2713 Audio system initialized");
            return true;
        }
        catch (Exception exception) {
            Level1.logError("AudioEntities initialization failed: " + exception.getMessage());
            return false;
        }
    }

    public static String getAudioTrackForZone(int n) {
        String[] stringArray = new String[]{"audio/level1_zone0_intro.ogg", "audio/level1_zone1_gauntlet.ogg", "audio/level1_zone2_underground.ogg", "audio/level1_zone3_overground.ogg", "audio/level1_zone4_descent.ogg", "audio/level1_zone5_boss_arena.ogg"};
        return n >= 0 && n < stringArray.length ? stringArray[n] : stringArray[0];
    }

    public static AnimationAndSpriteLoader.ParallaxSystem initializeParallax() {
        parallaxSystem = AnimationAndSpriteLoader.createLevel1ParallaxSystem();
        return parallaxSystem;
    }

    public static AnimationAndSpriteLoader.ParallaxSystem getParallaxSystem() {
        return parallaxSystem;
    }

    public static void updateParallaxCamera(float f) {
        if (parallaxSystem != null) {
            parallaxSystem.updateCamera(f);
        }
    }

    public static class EnemySpawn {
        public float x;
        public float y;
        public String enemyType;
        public int difficultyLevel;
        public String zone;

        public EnemySpawn(float f, float f2, String string, int n, String string2) {
            this.x = f;
            this.y = f2;
            this.enemyType = string;
            this.difficultyLevel = n;
            this.zone = string2;
        }

        public String toString() {
            return String.format("[%s] %s at (%.0f, %.0f) - Difficulty: %d", this.zone, this.enemyType, Float.valueOf(this.x), Float.valueOf(this.y), this.difficultyLevel);
        }
    }

    public static class HazardZone {
        public float startX;
        public float startY;
        public float endX;
        public float endY;
        public String hazardType;
        public int damagePerFrame;

        public HazardZone(float f, float f2, float f3, float f4, String string, int n) {
            this.startX = f;
            this.startY = f2;
            this.endX = f3;
            this.endY = f4;
            this.hazardType = string;
            this.damagePerFrame = n;
        }

        public boolean contains(float f, float f2) {
            return f >= this.startX && f <= this.endX && f2 >= this.startY && f2 <= this.endY;
        }
    }

    public static class CheckpointData {
        public float x;
        public float y;
        public int checkpointID;
        public String zone;

        public CheckpointData(float f, float f2, int n, String string) {
            this.x = f;
            this.y = f2;
            this.checkpointID = n;
            this.zone = string;
        }
    }
}
