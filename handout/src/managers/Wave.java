/*
 * Decompiled with CFR 0.152.
 */
package managers;

import managers.EnemyWaveManager;
import java.util.ArrayList;
import java.util.List;

public static class EnemyWaveManager.Wave {
    public int waveNumber;
    public List<EnemyWaveManager.EnemySpawn> enemies;
    public float spawnStartDelay;
    public float spawnInterval;
    public int minCheckpointWaves = 1;

    public EnemyWaveManager.Wave(int n, float f, float f2) {
        this.waveNumber = n;
        this.enemies = new ArrayList<EnemyWaveManager.EnemySpawn>();
        this.spawnStartDelay = f;
        this.spawnInterval = f2;
    }

    public void addEnemy(int n, int n2, int n3) {
        this.enemies.add(new EnemyWaveManager.EnemySpawn(n, n2, n3));
    }
}
