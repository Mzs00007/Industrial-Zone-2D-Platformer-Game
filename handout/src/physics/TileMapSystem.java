/*
 * Decompiled with CFR 0.152.
 */
package physics;
import game2D.*;

import java.util.HashMap;
import java.util.Map;
import important.Level1TileRegistry;
import important.Level2TileRegistry;

public class TileMapSystem {
    private int currentLevel;
    private Object[][] tileAssets;
    private Map<Character, Object[]> tileMap = new HashMap<Character, Object[]>();
    
    // Static instance for entity collision checks
    private static TileMapSystem currentLevelInstance = null;

    public TileMapSystem(int n) {
        this.currentLevel = n;
        TileMapSystem.currentLevelInstance = this;
        this.loadTiles(n);
    }

    private void loadTiles(int n) {
        if (n == 1) {
            this.tileAssets = Level1TileRegistry.TILES_ASSETS;
            System.out.println("[TileMapSystem] Loaded Level 1 Tile Registry (81 tiles)");
        } else if (n == 2) {
            this.tileAssets = Level2TileRegistry.TILES_ASSETS;
            System.out.println("[TileMapSystem] Loaded Level 2 Tile Registry (64 tiles)");
        } else {
            throw new IllegalArgumentException("Invalid level ID: " + n + ". Expected 1 or 2.");
        }
        for (Object[] objectArray : this.tileAssets) {
            Character c = (Character)objectArray[0];
            this.tileMap.put(c, objectArray);
        }
        System.out.printf("[TileMapSystem] \u2713 Indexed %d tiles for fast lookup%n", this.tileMap.size());
    }

    public Object[] getTile(char c) {
        Object[] objectArray = this.tileMap.get(Character.valueOf(c));
        if (objectArray == null) {
            System.err.printf("[TileMapSystem] \u2717 Unknown tile ID: '%c' in Level %d%n", Character.valueOf(c), this.currentLevel);
            return null;
        }
        return objectArray;
    }

    public boolean isSolid(char c) {
        Object[] objectArray = this.getTile(c);
        if (objectArray == null) {
            return false;
        }
        return (Boolean)objectArray[5];
    }

    public boolean isHazard(char c) {
        Object[] objectArray = this.getTile(c);
        if (objectArray == null) {
            return false;
        }
        return (Boolean)objectArray[6];
    }

    public String getPhysicsType(char c) {
        Object[] objectArray = this.getTile(c);
        if (objectArray == null) {
            return "STATIC";
        }
        return (String)objectArray[7];
    }

    public float getFriction(char c) {
        Object[] objectArray = this.getTile(c);
        if (objectArray == null) {
            return 0.85f;
        }
        return ((Float)objectArray[8]).floatValue();
    }

    public int getDamage(char c) {
        Object[] objectArray = this.getTile(c);
        if (objectArray == null) {
            return 0;
        }
        return (Integer)objectArray[9];
    }

    public int getAnimationFrames(char c) {
        Object[] objectArray = this.getTile(c);
        if (objectArray == null) {
            return 0;
        }
        return (Integer)objectArray[10];
    }

    public int getFrameTiming(char c) {
        Object[] objectArray = this.getTile(c);
        if (objectArray == null) {
            return 0;
        }
        return (Integer)objectArray[11];
    }

    public String getTag(char c) {
        Object[] objectArray = this.getTile(c);
        if (objectArray == null) {
            return "unknown";
        }
        return (String)objectArray[12];
    }

    public String getTileName(char c) {
        Object[] objectArray = this.getTile(c);
        if (objectArray == null) {
            return "UNKNOWN";
        }
        return (String)objectArray[1];
    }

    public String getFilePath(char c) {
        Object[] objectArray = this.getTile(c);
        if (objectArray == null) {
            return null;
        }
        return (String)objectArray[2];
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public int getTileCount() {
        return this.tileMap.size();
    }

    public void printTileInfo(char c) {
        Object[] objectArray = this.getTile(c);
        if (objectArray == null) {
            System.out.printf("Tile '%c' not found in Level %d%n", Character.valueOf(c), this.currentLevel);
            return;
        }
        System.out.printf("Tile '%c' - %s:%n", Character.valueOf(c), this.getTileName(c));
        System.out.printf("  Physics: %s, Solid: %s, Hazard: %s%n", this.getPhysicsType(c), this.isSolid(c), this.isHazard(c));
        System.out.printf("  Friction: %.2f, Damage: %d%n", Float.valueOf(this.getFriction(c)), this.getDamage(c));
        System.out.printf("  Animation: %d frames, %dms per frame%n", this.getAnimationFrames(c), this.getFrameTiming(c));
        System.out.printf("  Tag: %s%n", this.getTag(c));
    }

    public void printAllTiles() {
        System.out.printf("\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501%n", new Object[0]);
        System.out.printf("LEVEL %d TILE REGISTRY - %d tiles%n", this.currentLevel, this.getTileCount());
        System.out.printf("\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501%n", new Object[0]);
        for (Object[] objectArray : this.tileAssets) {
            char c = ((Character)objectArray[0]).charValue();
            String string = (String)objectArray[1];
            boolean bl = (Boolean)objectArray[5];
            boolean bl2 = (Boolean)objectArray[6];
            String string2 = (String)objectArray[12];
            System.out.printf("'%c' %-30s [%s] %s%s%n", Character.valueOf(c), string, string2, bl ? "SOLID " : "", bl2 ? "HAZARD" : "");
        }
        System.out.printf("\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501%n", new Object[0]);
    }
    
    /**
     * Static helper for entity collision checks
     * UPDATED: 2026-04-14
     */
    public static boolean isSolidTile(int tileX, int tileY) {
        if (currentLevelInstance == null) {
            return false;
        }
        char tileChar = (char)currentLevelInstance.currentLevel; // This would need proper map access
        return currentLevelInstance.isSolid(tileChar);
    }
    
    /**
     * Static helper to check if tile is hazard
     */
    public static boolean isHazardTile(int tileX, int tileY) {
        if (currentLevelInstance == null) {
            return false;
        }
        char tileChar = (char)currentLevelInstance.currentLevel;
        return currentLevelInstance.isHazard(tileChar);
    }
}
