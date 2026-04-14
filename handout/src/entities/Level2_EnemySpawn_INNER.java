/*
 * DEPRECATED: This is a compiled inner class artifact (decompiled).
 * The actual implementation is in the parent Level class.
 * 
 * DO NOT USE - Instead use the parent class's inner class:
 * For example: Level1.CheckpointData instead of Level1_Checkpoint_INNER.CheckpointData
 */

/*  COMMENTED OUT - USE PARENT CLASS INNER CLASS INSTEAD

/*
 * Decompiled with CFR 0.152.
 */
public static class Level2.EnemySpawn {
    public float x;
    public float y;
    public String enemyType;
    public int difficultyLevel;
    public String zone;

    public Level2.EnemySpawn(float f, float f2, String string, int n, String string2) {
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


*/
