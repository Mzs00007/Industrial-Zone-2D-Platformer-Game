/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.WeaponFireSystem.FireSequence {
    public String characterId;
    public String gunFile;
    public int aimAngle;
    public float firingX;
    public float firingY;
    public AnimationAndSpriteLoader.BulletSpawner.BulletInstance spawnedBullet;
    public String handGripUsed;
    public long fireTime;

    public AnimationAndSpriteLoader.WeaponFireSystem.FireSequence(String string, float f, float f2, int n) {
        this.characterId = string;
        this.firingX = f;
        this.firingY = f2;
        this.aimAngle = n;
        this.fireTime = System.currentTimeMillis();
    }
}
