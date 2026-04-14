/*
 * Decompiled with CFR 0.152.
 */
package managers;

public static class GameplayEnhancementSystem.HazardPlacement {
    public String hazardType;
    public int xPosition;
    public int yPosition;
    public boolean isRotating;

    public GameplayEnhancementSystem.HazardPlacement(String string, int n, int n2, boolean bl) {
        this.hazardType = string;
        this.xPosition = n;
        this.yPosition = n2;
        this.isRotating = bl;
    }
}
