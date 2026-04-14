/*
 * Decompiled with CFR 0.152.
 */
package managers;

public static class EnemyWaveManager.WaveStatistics {
    public int totalWaves;
    public int completedWaves;
    public int totalEnemies;
    public float progress;

    public EnemyWaveManager.WaveStatistics(int n, int n2, int n3, float f) {
        this.totalWaves = n;
        this.completedWaves = n2;
        this.totalEnemies = n3;
        this.progress = f;
    }

    public String toString() {
        return String.format("Waves: %d/%d | Enemies: %d | Progress: %.1f%%", this.completedWaves, this.totalWaves, this.totalEnemies, Float.valueOf(this.progress * 100.0f));
    }
}
