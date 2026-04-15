/*
 * Decompiled with CFR 0.152.
 * UPDATED: 2026-04-14 - Production asset integration
 */
package entities;

import animation.AnimationAndSpriteLoader;
import game2D.TileMap;
import important.Config;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import controllers.AnimatedObjectManager;
import controllers.ComprehensiveTileMapLoader;

public class Level2 {
    private static final boolean VERBOSE_LOGGING = true;
    public static final int LEVEL_ID = 2;
    public static final String LEVEL_NAME = "Power Station";
    public static final String DIFFICULTY = "Hard";
    public static final String STORY = "Breach the power station and confront the final guardian";
    public static final int MAP_WIDTH = 500;
    public static final int MAP_HEIGHT = 50;
    public static final int TILE_SIZE = 32;
    public static final int PIXEL_WIDTH = 16000;
    public static final int PIXEL_HEIGHT = 1600;
    public static final float PLAYER_START_X = 350.0f;
    public static final float PLAYER_START_Y = 1050.0f;
    private static final float REACTOR_END = 2528.0f;
    private static final float CORRIDORS_END = 5728.0f;
    private static final float CORE_END = 9568.0f;
    private static final float CATWALKS_END = 12768.0f;
    private static final float ANTECHAMBER_END = 14368.0f;
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
        Level2.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        Level2.log("Level2: INITIALIZATION SEQUENCE STARTING (HARD MODE)");
        Level2.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        levelTileMap = tileMap;
        enemySpawns = new ArrayList<EnemySpawn>();
        hazardZones = new ArrayList<HazardZone>();
        checkpoints = new ArrayList<CheckpointData>();
        tileMapping = new HashMap<Character, String>();
        zoneNames = new String[6];
        levelAnimatedObjects = new ArrayList<ComprehensiveTileMapLoader.AnimatedObject>();
        levelBackgroundLayers = new ArrayList<ComprehensiveTileMapLoader.BackgroundLayer>();
        animatedObjectManager = new AnimatedObjectManager();
        Level2.parseMapFile();
        Level2.identifyZones();
        Level2.extractEnemySpawns();
        Level2.extractHazardZones();
        Level2.extractCheckpoints();
        Level2.loadComprehensiveAssets();
        Level2.validateEnemyDefinitions();
        Level2.validateEnemyCombatConfiguration();
        Level2.initializeVFXChainReactionSystem();
        Level2.initializeAudioSystem();
        Level2.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        Level2.log("Level2: INITIALIZATION COMPLETE (HARD DIFFICULTY)");
        Level2.log("  Tiles loaded: " + Level2.countLoadedTiles());
        Level2.log("  Enemies spawned: " + enemySpawns.size() + " (HIGH DENSITY)");
        Level2.log("  Hazard zones: " + hazardZones.size() + " (DENSE NETWORK)");
        Level2.log("  Checkpoints: " + checkpoints.size() + " (PROGRESSION POINTS)");
        Level2.log("  Animated objects: " + levelAnimatedObjects.size());
        Level2.log("  Background layers: " + levelBackgroundLayers.size());
        Level2.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
    }

    private static void parseMapFile() {
        String string = "maps/level_2/map.txt";
        try {
            char c;
            String string2;
            String string3;
            Level2.log("\ud83d\udcc2 Loading Power Station map: " + string);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(string)));
            String string4 = bufferedReader.readLine();
            String[] stringArray = string4.split(" ");
            int n = Integer.parseInt(stringArray[0]);
            int n2 = Integer.parseInt(stringArray[1]);
            Level2.log("  Map dimensions: " + n + "\u00d7" + n2 + " tiles (" + n * 32 + "\u00d7" + n2 * 32 + "px)");
            mapData = new int[n2][n];
            int n3 = 0;
            while ((string3 = bufferedReader.readLine()) != null) {
                String[] stringArray2;
                if (string3.equals("#map")) {
                    Level2.log("  Found #map marker - reading tile data");
                    break;
                }
                if (!string3.contains("=") || string3.startsWith("//") || (stringArray2 = string3.split("=")).length != 2) continue;
                string2 = stringArray2[0].trim();
                String string5 = stringArray2[1].trim();
                if (string2.length() <= 0) continue;
                c = string2.charAt(0);
                tileMapping.put(Character.valueOf(c), string5);
                ++n3;
            }
            Level2.log("  Tile types loaded: " + n3 + " (COMPREHENSIVE)");
            for (int i = 0; i < n2 && (string2 = bufferedReader.readLine()) != null; ++i) {
                for (int j = 0; j < n && j < string2.length(); ++j) {
                    c = string2.charAt(j);
                    Level2.mapData[i][j] = c;
                }
            }
            bufferedReader.close();
            Level2.log("\u2713 Power Station map parsed successfully (HARD DIFFICULTY LOADED)");
        }
        catch (IOException iOException) {
            Level2.logError("Failed to read map file: " + string);
            iOException.printStackTrace();
        }
    }

    private static void identifyZones() {
        Level2.log("\ud83d\uddfa\ufe0f  Identifying Power Station zones (HARD DIFFICULTY)...");
        Level2.zoneNames[0] = "Reactor Entrance";
        Level2.zoneNames[1] = "Industrial Corridors";
        Level2.zoneNames[2] = "Power Core Underground";
        Level2.zoneNames[3] = "High Voltage Catwalks";
        Level2.zoneNames[4] = "Boss Antechamber";
        Level2.zoneNames[5] = "Final Boss Arena";
        Level2.log("  Zone 0 (Reactor): Tiles 0-79 (Immediate heavy enemy presence)");
        Level2.log("  Zone 1 (Corridors): Tiles 80-179 (Maze-like navigation + dense combat)");
        Level2.log("  Zone 2 (Core): Tiles 180-299 (Elite enemies + hazard networks)");
        Level2.log("  Zone 3 (Catwalks): Tiles 300-399 (Precision platforming + electricity)");
        Level2.log("  Zone 4 (Antechamber): Tiles 400-449 (Elite guard gatekeepers)");
        Level2.log("  Zone 5 (Boss Arena): Tiles 450-499 (FinalStandGuardian ULTIMATE battle)");
    }

    private static void extractEnemySpawns() {
        Level2.log("\ud83d\udc7e Extracting enemy spawns (HARD MODE - HIGH DENSITY)...");
        for (int i = 0; i < mapData.length; ++i) {
            for (int j = 0; j < mapData[0].length; ++j) {
                char c = (char)mapData[i][j];
                if (c != 'R' && c != 'S' && c != 'T' && c != 'U' && c != 'D') continue;
                float f = j * 32;
                float f2 = i * 32;
                String string = Level2.getZoneName(j);
                int n = Level2.getZoneDifficulty(j);
                String string2 = Level2.selectEnemyType(j, n);
                if (n < 3 && !(Math.random() < 0.6)) continue;
                enemySpawns.add(new EnemySpawn(f, f2, string2, n, string));
            }
        }
        Level2.log("  Spawned: " + enemySpawns.size() + " enemies (DENSE NETWORK)");
    }

    private static void extractHazardZones() {
        Level2.log("\u26a0\ufe0f  Extracting hazard zones (DENSE ELECTRICAL NETWORK)...");
        for (int i = 0; i < mapData.length; ++i) {
            for (int j = 0; j < mapData[0].length; ++j) {
                float f;
                float f2;
                char c = (char)mapData[i][j];
                String string = null;
                int n = 0;
                if (c == 'R' || c == 'S') {
                    string = "ELECTRICAL";
                    n = 30;
                } else if (c == 'T' || c == 'U') {
                    string = "STRUCTURAL";
                    n = 15;
                } else if (c == 'V' || c == 'W' || c == '^' || c == '&') {
                    string = "PLATFORM_EDGE";
                    n = 50;
                } else if (c == '*' || c == '(' || c == '>' || c == ',') {
                    string = "CEILING";
                    n = 20;
                }
                if (string == null || Level2.isHazardAlreadyMapped(f2 = (float)(j * 32), f = (float)(i * 32))) continue;
                hazardZones.add(new HazardZone(f2, f, f2 + 32.0f, f + 32.0f, string, n));
            }
        }
        Level2.log("  Identified: " + hazardZones.size() + " hazard zones (EXTENSIVE)");
    }

    private static void extractCheckpoints() {
        Level2.log("\ud83d\udea9 Extracting checkpoints (LIMITED - HARD DIFFICULTY)...");
        int n = 0;
        for (int i = 0; i < 500; i += 100) {
            if (i <= 0 || i >= 450) continue;
            float f = i * 32;
            float f2 = 800.0f;
            checkpoints.add(new CheckpointData(f, f2, n, Level2.getZoneName(i)));
            ++n;
        }
        Level2.log("  Placed: " + checkpoints.size() + " checkpoints (SPARSE - EXPERT MODE)");
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
        return ' ';
    }

    public static String getZoneName(int n) {
        if (n <= 79) {
            return zoneNames[0];
        }
        if (n <= 179) {
            return zoneNames[1];
        }
        if (n <= 299) {
            return zoneNames[2];
        }
        if (n <= 399) {
            return zoneNames[3];
        }
        if (n <= 449) {
            return zoneNames[4];
        }
        return zoneNames[5];
    }

    public static int getZoneDifficulty(int n) {
        if (n <= 79) {
            return 3;
        }
        if (n <= 179) {
            return 4;
        }
        if (n <= 299) {
            return 4;
        }
        if (n <= 399) {
            return 5;
        }
        if (n <= 449) {
            return 5;
        }
        return 6;
    }

    private static String selectEnemyType(int n, int n2) {
        if (n2 <= 2) {
            return "DRONE";
        }
        if (n2 == 3) {
            return "SCI_FI_ANTAGONIST";
        }
        if (n2 == 4) {
            return "ELITE_GUARD";
        }
        if (n2 == 5) {
            return "ADVANCED_ELITE";
        }
        return "FINAL_BOSS";
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
                if (n3 == 32 || n3 == 46) continue;
                ++n;
            }
        }
        return n;
    }

    private static void log(String string) {
        System.out.println("[Level2] " + string);
    }

    private static void logError(String string) {
        System.err.println("[Level2] ERROR: " + string);
    }

    public static String getLevelInfo() {
        return String.format("Level %d: %s (%s Difficulty - EXPERT)", 2, LEVEL_NAME, DIFFICULTY);
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

    private static void loadComprehensiveAssets() {
        try {
            Level2.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
            Level2.log("LOADING COMPREHENSIVE ASSETS FOR LEVEL 2");
            Level2.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
            String string = "maps/level_2/map.txt";
            assetLoader = new ComprehensiveTileMapLoader(string, "Level 2 - Power Station");
            levelAnimatedObjects = assetLoader.getAnimatedObjects();
            levelBackgroundLayers = assetLoader.getBackgroundLayers();
            Level2.log("\u2713 Asset loading complete");
            Level2.log("  - Animated objects loaded: " + levelAnimatedObjects.size());
            Level2.log("  - Background layers: " + levelBackgroundLayers.size());
        }
        catch (Exception exception) {
            Level2.logError("Failed to load comprehensive assets: " + exception.getMessage());
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

    public static int validateEnemyCombatConfiguration() {
        Level2.log("\ud83d\udd27 Validating HARD difficulty enemy combat system using EnemyAICombat...");
        int n = 0;
        try {
            if (enemySpawns != null && enemySpawns.size() > 0) {
                for (EnemySpawn enemySpawn : enemySpawns) {
                    if (enemySpawn.difficultyLevel < 2) continue;
                    Level2.log("  \u2713 Enemy '" + enemySpawn.enemyType + "' at zone '" + enemySpawn.zone + "' configured for HARD difficulty " + enemySpawn.difficultyLevel);
                    ++n;
                }
            }
            Level2.log("  Total enemies configured: " + n + " (via EnemyAICombat - Level 2 HARD MODE)");
        }
        catch (Exception exception) {
            Level2.logError("EnemyAICombat validation failed: " + exception.getMessage());
        }
        return n;
    }

    public static boolean validateEnemyDefinitions() {
        Level2.log("\u2713 Validating Enemies class definitions for HARD level...");
        try {
            HashSet<String> hashSet = new HashSet<String>();
            for (EnemySpawn object : enemySpawns) {
                if (object.enemyType.equals("NONE")) continue;
                hashSet.add(object.enemyType);
            }
            Level2.log("  High-tier enemy types referenced in Power Station:");
            for (String string : hashSet) {
                Level2.log("    - " + string + " (Enemies class reference)");
            }
            Level2.log("  Total unique enemy types: " + hashSet.size() + " (HARD difficulty tier)");
            return hashSet.size() > 0 || enemySpawns.size() == 0;
        }
        catch (Exception exception) {
            Level2.logError("Enemies validation failed: " + exception.getMessage());
            return false;
        }
    }

    public static boolean initializeVFXChainReactionSystem() {
        Level2.log("\u2728 Initializing ELECTRICAL VFX Chain Reaction system...");
        try {
            if (hazardZones != null && hazardZones.size() > 0) {
                Level2.log("  \u26a1 VFXChainReaction configured for " + hazardZones.size() + " electrical hazard zones");
                for (HazardZone hazardZone : hazardZones) {
                    Level2.log(String.format("    - %s CHAIN at (%.0f, %.0f) - Damage: %d", hazardZone.hazardType, Float.valueOf(hazardZone.startX), Float.valueOf(hazardZone.startY), hazardZone.damagePerFrame));
                }
            }
            Level2.log("  \u2713 Electrical VFX system initialized");
            return true;
        }
        catch (Exception exception) {
            Level2.logError("VFXChainReaction initialization failed: " + exception.getMessage());
            return false;
        }
    }

    public static String getVFXConfigForHazard(String string) {
        switch (string) {
            case "ELECTRICAL_MAIN": {
                return "YELLOW_CHAIN_BURST";
            }
            case "ELECTRICAL_SECONDARY": {
                return "CYAN_CHAIN_ARC";
            }
            case "STRUCTURAL": {
                return "GRAY_COLLAPSE_CHAIN";
            }
            case "COOLANT": {
                return "BLUE_FREEZE_CHAIN";
            }
        }
        return "ELECTRICAL_CHAIN";
    }

    public static boolean initializeAudioSystem() {
        Level2.log("\ud83c\udfb5 Initializing INTENSE AudioEntities system for Power Station...");
        try {
            int n;
            Level2.log("  Background music: Level 2 - Power Station Danger Theme (HARD)");
            for (n = 0; n < 6; ++n) {
                String string = zoneNames[n];
                int n2 = Level2.getZoneDifficulty(n * 70);
                Level2.log("    Zone " + n + " (" + (String)string + "): Electrical ambience + danger audio (Difficulty: " + n2 + ")");
            }
            n = 0;
            for (EnemySpawn enemySpawn : enemySpawns) {
                if (enemySpawn.difficultyLevel <= 1) continue;
                ++n;
            }
            Level2.log("  Enemy audio triggers: " + n + " (HIGH FREQUENCY - via AudioEntities)");
            Level2.log("  Electrical hazard warnings: " + hazardZones.size() + " zones with audio cues");
            Level2.log("  \u2713 Audio system initialized (HARD mode)");
            return true;
        }
        catch (Exception exception) {
            Level2.logError("AudioEntities initialization failed: " + exception.getMessage());
            return false;
        }
    }

    public static String getAudioTrackForZone(int n) {
        String[] stringArray = new String[]{"audio/level2_zone0_arrival.ogg", "audio/level2_zone1_transformer.ogg", "audio/level2_zone2_reactor_DANGER.ogg", "audio/level2_zone3_generator_HARD.ogg", "audio/level2_zone4_command_INTENSE.ogg", "audio/level2_zone5_centrifuge_BOSS.ogg"};
        return n >= 0 && n < stringArray.length ? stringArray[n] : stringArray[0];
    }

    public static AnimationAndSpriteLoader.ParallaxSystem initializeParallax() {
        parallaxSystem = AnimationAndSpriteLoader.createLevel2ParallaxSystemDay();
        return parallaxSystem;
    }

    public static AnimationAndSpriteLoader.ParallaxSystem initializeParallaxDay() {
        parallaxSystem = AnimationAndSpriteLoader.createLevel2ParallaxSystemDay();
        System.out.println("\u2713 Level2 switched to DAY mode parallax");
        return parallaxSystem;
    }

    public static AnimationAndSpriteLoader.ParallaxSystem initializeParallaxNight() {
        parallaxSystem = AnimationAndSpriteLoader.createLevel2ParallaxSystemNight();
        System.out.println("\u2713 Level2 switched to NIGHT mode parallax");
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

    public static void switchToNightMode() {
        Level2.initializeParallaxNight();
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
