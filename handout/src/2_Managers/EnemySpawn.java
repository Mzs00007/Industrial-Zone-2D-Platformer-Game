/*
 * Decompiled with CFR 0.152.
 */
package core;

public static class EnemyWaveManager.EnemySpawn {
    public int enemyType;
    public int xPosition;
    public int yPosition;
    public boolean spawned;
    public float difficulty;

    public EnemyWaveManager.EnemySpawn(int n, int n2, int n3) {
        this.enemyType = n;
        this.xPosition = n2;
        this.yPosition = n3;
        this.spawned = false;
        this.difficulty = 1.0f;
    }
}
