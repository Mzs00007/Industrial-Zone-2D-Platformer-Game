/*
 * Decompiled with CFR 0.152.
 */
package core;

import core.GameplayEnhancementSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyWaveManager {
    private List<Wave> waves = new ArrayList<Wave>();
    private int currentWaveIndex = 0;
    private int totalWavesCompleted = 0;
    private float waveTimer = 0.0f;
    private boolean waveInProgress = false;
    private boolean allWavesCompleted = false;
    private int enemiesRemainingInWave = 0;
    private Random random = new Random();
    private static final int BASE_SPAWN_X = 50;
    private static final int BASE_SPAWN_Y = 450;

    public void createWave(int n, List<GameplayEnhancementSystem.EnemyEncounter> list) {
        Wave wave = new Wave(n, (float)n * 0.5f, 0.3f);
        for (GameplayEnhancementSystem.EnemyEncounter enemyEncounter : list) {
            wave.addEnemy(enemyEncounter.enemyType, enemyEncounter.xPosition, 450);
        }
        this.waves.add(wave);
        this.enemiesRemainingInWave = wave.enemies.size();
    }

    public void createWave(int n, int[] nArray, float f) {
        Wave wave = new Wave(n, (float)n * 0.5f, 0.3f);
        int n2 = 100;
        for (int i = 0; i < nArray.length; ++i) {
            int n3 = 50 + i * n2;
            EnemySpawn enemySpawn = new EnemySpawn(nArray[i], n3, 450);
            enemySpawn.difficulty = f;
            wave.enemies.add(enemySpawn);
        }
        this.waves.add(wave);
    }

    public void clearWaves() {
        this.waves.clear();
        this.currentWaveIndex = 0;
        this.waveTimer = 0.0f;
        this.waveInProgress = false;
    }

    public void startWaves() {
        if (!this.waves.isEmpty()) {
            this.currentWaveIndex = 0;
            this.waveTimer = 0.0f;
            this.waveInProgress = true;
            this.allWavesCompleted = false;
        }
    }

    public void update(long l) {
        if (!this.waveInProgress || this.allWavesCompleted) {
            return;
        }
        float f = (float)l / 1000.0f;
        this.waveTimer += f;
        if (this.currentWaveIndex < this.waves.size()) {
            Wave wave = this.waves.get(this.currentWaveIndex);
            for (EnemySpawn enemySpawn : wave.enemies) {
                if (enemySpawn.spawned || !(this.waveTimer >= wave.spawnStartDelay) || !this.shouldSpawnEnemy(enemySpawn)) continue;
                enemySpawn.spawned = true;
            }
        }
    }

    private boolean shouldSpawnEnemy(EnemySpawn enemySpawn) {
        return true;
    }

    public List<EnemySpawn> getEnemiesToSpawn() {
        ArrayList<EnemySpawn> arrayList = new ArrayList<EnemySpawn>();
        if (this.currentWaveIndex >= this.waves.size()) {
            return arrayList;
        }
        Wave wave = this.waves.get(this.currentWaveIndex);
        for (EnemySpawn enemySpawn : wave.enemies) {
            if (enemySpawn.spawned || !(this.waveTimer >= wave.spawnStartDelay)) continue;
            arrayList.add(enemySpawn);
            enemySpawn.spawned = true;
        }
        return arrayList;
    }

    public void enemyDefeated() {
        --this.enemiesRemainingInWave;
        if (this.enemiesRemainingInWave <= 0) {
            this.completeCurrentWave();
        }
    }

    private void completeCurrentWave() {
        ++this.totalWavesCompleted;
        if (this.currentWaveIndex < this.waves.size() - 1) {
            ++this.currentWaveIndex;
            this.waveTimer = 0.0f;
            this.enemiesRemainingInWave = this.waves.get((int)this.currentWaveIndex).enemies.size();
        } else {
            this.allWavesCompleted = true;
            this.waveInProgress = false;
        }
    }

    public int getCurrentWaveNumber() {
        return this.currentWaveIndex + 1;
    }

    public int getTotalWaves() {
        return this.waves.size();
    }

    public int getEnemiesRemaining() {
        return this.enemiesRemainingInWave;
    }

    public boolean isWaveInProgress() {
        return this.waveInProgress;
    }

    public boolean areAllWavesCompleted() {
        return this.allWavesCompleted;
    }

    public float getWaveProgress() {
        if (this.waves.isEmpty()) {
            return 1.0f;
        }
        return (float)this.totalWavesCompleted / (float)this.waves.size();
    }

    public void scaleDifficulty(float f) {
        for (Wave wave : this.waves) {
            for (EnemySpawn enemySpawn : wave.enemies) {
                enemySpawn.difficulty *= f;
            }
        }
    }

    public float getWaveDifficulty(int n) {
        if (n < 0 || n >= this.waves.size()) {
            return 1.0f;
        }
        Wave wave = this.waves.get(n);
        if (wave.enemies.isEmpty()) {
            return 1.0f;
        }
        float f = 0.0f;
        for (EnemySpawn enemySpawn : wave.enemies) {
            f += enemySpawn.difficulty;
        }
        return f / (float)wave.enemies.size();
    }

    public boolean shouldCheckpointAfterWave() {
        if (this.currentWaveIndex >= this.waves.size()) {
            return false;
        }
        Wave wave = this.waves.get(this.currentWaveIndex);
        int n = this.currentWaveIndex % wave.minCheckpointWaves;
        return n == 0 && this.currentWaveIndex > 0;
    }

    public CheckpointData getCheckpointData() {
        return new CheckpointData(this.currentWaveIndex, this.totalWavesCompleted, this.getWaveProgress(), System.currentTimeMillis());
    }

    public WaveStatistics getStatistics() {
        int n = 0;
        for (Wave wave : this.waves) {
            n += wave.enemies.size();
        }
        return new WaveStatistics(this.waves.size(), this.totalWavesCompleted, n, this.getWaveProgress());
    }

    public void reset() {
        this.currentWaveIndex = 0;
        this.totalWavesCompleted = 0;
        this.waveTimer = 0.0f;
        this.waveInProgress = false;
        this.allWavesCompleted = false;
        this.enemiesRemainingInWave = 0;
        for (Wave wave : this.waves) {
            for (EnemySpawn enemySpawn : wave.enemies) {
                enemySpawn.spawned = false;
            }
        }
    }

    public static class Wave {
        public int waveNumber;
        public List<EnemySpawn> enemies;
        public float spawnStartDelay;
        public float spawnInterval;
        public int minCheckpointWaves = 1;

        public Wave(int n, float f, float f2) {
            this.waveNumber = n;
            this.enemies = new ArrayList<EnemySpawn>();
            this.spawnStartDelay = f;
            this.spawnInterval = f2;
        }

        public void addEnemy(int n, int n2, int n3) {
            this.enemies.add(new EnemySpawn(n, n2, n3));
        }
    }

    public static class EnemySpawn {
        public int enemyType;
        public int xPosition;
        public int yPosition;
        public boolean spawned;
        public float difficulty;

        public EnemySpawn(int n, int n2, int n3) {
            this.enemyType = n;
            this.xPosition = n2;
            this.yPosition = n3;
            this.spawned = false;
            this.difficulty = 1.0f;
        }
    }

    public static class CheckpointData {
        public int waveIndex;
        public int wavesCompleted;
        public float progress;
        public long timestamp;

        public CheckpointData(int n, int n2, float f, long l) {
            this.waveIndex = n;
            this.wavesCompleted = n2;
            this.progress = f;
            this.timestamp = l;
        }
    }

    public static class WaveStatistics {
        public int totalWaves;
        public int completedWaves;
        public int totalEnemies;
        public float progress;

        public WaveStatistics(int n, int n2, int n3, float f) {
            this.totalWaves = n;
            this.completedWaves = n2;
            this.totalEnemies = n3;
            this.progress = f;
        }

        public String toString() {
            return String.format("Waves: %d/%d | Enemies: %d | Progress: %.1f%%", this.completedWaves, this.totalWaves, this.totalEnemies, Float.valueOf(this.progress * 100.0f));
        }
    }
}
