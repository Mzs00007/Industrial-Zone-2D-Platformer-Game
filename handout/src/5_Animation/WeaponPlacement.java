/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.LevelWeaponPlacementSystem.WeaponPlacement {
    public String gunFile;
    public int screenX;
    public int screenY;
    public int tileX;
    public int tileY;
    public String placementTile;
    public String validationReason;
    public boolean isValid;

    public AnimationAndSpriteLoader.LevelWeaponPlacementSystem.WeaponPlacement(String string, int n, int n2, int n3, int n4) {
        this.gunFile = string;
        this.screenX = n;
        this.screenY = n2;
        this.tileX = n3;
        this.tileY = n4;
        this.isValid = false;
    }
}
