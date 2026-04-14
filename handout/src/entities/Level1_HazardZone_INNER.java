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
public static class Level1.HazardZone {
    public float startX;
    public float startY;
    public float endX;
    public float endY;
    public String hazardType;
    public int damagePerFrame;

    public Level1.HazardZone(float f, float f2, float f3, float f4, String string, int n) {
        this.startX = f;
        this.startY = f2;
        this.endX = f3;
        this.endY = f4;
        this.hazardType = string;
        this.damagePerFrame = n;
    }

    public boolean contains(float f, float f2) {
        return f >= this.startX && f <= this.endX && f2 >= this.startY && f2 <= this.endY;
    }
}


*/
