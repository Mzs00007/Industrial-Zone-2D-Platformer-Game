package entities;

/**
 * Contract for level-specific data.  Level1 and Level2 each implement this
 * so that Game.java can delegate instead of hard-coding level arrays.
 */
public interface LevelData {

    // ── metadata ──
    int    getLevelId();
    String getLevelName();
    String getDifficulty();
    int    getPixelWidth();

    // ── player spawn ──
    float  getPlayerStartX();
    float  getPlayerStartY();

    // ── world geometry  [x, y, width, height] ──
    float[][] getPlatforms();
    
    // ── platform types  0=normal, 1=one-way (passable from below), 2=moving ──
    default int[] getPlatformTypes() { 
        float[][] plats = getPlatforms();
        int[] types = new int[plats.length];
        for (int i = 0; i < types.length; i++) types[i] = 0;  // default: all normal
        return types;
    }

    // ── enemies  [x, y, typeId] ──
    float[][] getEnemySpawns();

    // ── animated objects  [typeId, x, y, w, h] ──
    float[][] getAnimatedObjects();

    // ── animated-asset paths ──
    String getAnimAssetDir();
    String getAnimAssetPath(AnimatedObject.ObjType type);

    // ── background / parallax ──
    String   getBgDir();
    /** Explicit file list, or null to auto-scan the directory. */
    String[] getBgFiles();
    float[]  getScrollFactors();

    /** Day-time background layers (for levels with day/night cycle). Null = not supported. */
    default String[] getBgDayFiles()   { return null; }
    /** Night-time background layers (for levels with day/night cycle). Null = not supported. */
    default String[] getBgNightFiles() { return null; }
    /** Overlay image drawn on top of both day and night layers. Null = none. */
    default String   getBgOverlay()    { return null; }

    // ── tile map ──
    String   getMapFilePath();
    String[] getTileMapDirs();
    int      getTileMapOffsetY();

    // ── level completion ──
    int     getCompletionScore();
    String  getCompletionTitle();
    String  getNextLevelPrompt();
    boolean hasNextLevel();
    /** Narrative lines shown on the level-complete overlay (can be empty). */
    default String[] getStoryLines() { return new String[0]; }

    // ── weapon spawns  [weaponTypeId, x, y]  (0=PISTOL 1=SMG 2=RIFLE 3=SHOTGUN) ──
    default float[][] getWeaponSpawns() { return new float[0][]; }

    // ── ladder zones  [x, y, width, height] ──
    default float[][] getLadderZones() { return new float[0][]; }

    // ── world width for camera clamping ──
    default float getWorldWidth() { return (float) getPixelWidth(); }
}
