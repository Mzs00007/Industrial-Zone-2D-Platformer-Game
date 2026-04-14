/*
 * Decompiled with CFR 0.152.
 */
package managers;

public static class EnemyWaveManager.CheckpointData {
    public int waveIndex;
    public int wavesCompleted;
    public float progress;
    public long timestamp;

    public EnemyWaveManager.CheckpointData(int n, int n2, float f, long l) {
        this.waveIndex = n;
        this.wavesCompleted = n2;
        this.progress = f;
        this.timestamp = l;
    }
}
