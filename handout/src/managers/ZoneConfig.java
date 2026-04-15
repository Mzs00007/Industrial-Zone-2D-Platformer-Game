/*
 * Decompiled with CFR 0.152.
 */
package managers;
public class ZoneConfig {
    public int zoneNumber;
    public int enemyCount;
    public int hazardCount;
    public int collectibleCount;
    public float difficultyMultiplier;
    public String theme;
    public boolean hasCheckpoint;
    public int checkpointIndex;

    public ZoneConfig(int n, int n2, int n3, int n4, float f, String string, boolean bl, int n5) {
        this.zoneNumber = n;
        this.enemyCount = n2;
        this.hazardCount = n3;
        this.collectibleCount = n4;
        this.difficultyMultiplier = f;
        this.theme = string;
        this.hasCheckpoint = bl;
        this.checkpointIndex = n5;
    }
}
