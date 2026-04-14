/*
 * Decompiled with CFR 0.152.
 */
package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public static class Core.LevelManager {
    private static Core.LevelManager instance = null;
    private int currentLevel = 1;
    private Object tileMap;
    private Map<Character, String> tileCharacterMapping = new HashMap<Character, String>();
    private float playerSpawnX = 100.0f;
    private float playerSpawnY = 100.0f;
    private List<EnemySpawn> enemySpawns = new ArrayList<EnemySpawn>();

    public static synchronized Core.LevelManager getInstance() {
        if (instance == null) {
            instance = new Core.LevelManager();
        }
        return instance;
    }

    private Core.LevelManager() {
        System.out.println("[LevelManager] Initialized");
    }

    public void loadLevel(int n) {
        this.currentLevel = n;
        if (n == 1) {
            String string = "maps/level_1/map.txt";
        } else if (n == 2) {
            String string = "maps/level_2/map.txt";
        } else {
            System.err.println("[LevelManager] Unknown level: " + n);
            return;
        }
        System.out.println("[LevelManager] Loaded level " + n);
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public Object getTileMap() {
        return this.tileMap;
    }

    public float getPlayerSpawnX() {
        return this.playerSpawnX;
    }

    public float getPlayerSpawnY() {
        return this.playerSpawnY;
    }

    public List<EnemySpawn> getEnemySpawns() {
        return this.enemySpawns;
    }

    public static class EnemySpawn {
        public float x;
        public float y;
        public String type;

        public EnemySpawn(float f, float f2, String string) {
            this.x = f;
            this.y = f2;
            this.type = string;
        }
    }
}
