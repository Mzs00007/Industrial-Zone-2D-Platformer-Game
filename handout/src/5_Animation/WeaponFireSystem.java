/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.WeaponFireSystem {
    public static final String SYSTEM_TYPE = "weapon_fire_system";

    public static FireSequence executeFire(String string, float f, float f2, int n, AnimationAndSpriteLoader.CharacterAnimationStateMachine.CharacterAnimationState characterAnimationState) {
        String string2 = AnimationAndSpriteLoader.CharacterWeaponState.EquippedWeapons.getEquippedWeapon(string);
        String string3 = AnimationAndSpriteLoader.HandGripSelector.selectGripPose(string, n);
        AnimationAndSpriteLoader.BulletSpawner.BulletInstance bulletInstance = AnimationAndSpriteLoader.BulletSpawner.fireWeapon(string2, f, f2, n);
        characterAnimationState.transitionTo(3);
        characterAnimationState.fire();
        FireSequence fireSequence = new FireSequence(string, f, f2, n);
        fireSequence.gunFile = string2;
        fireSequence.spawnedBullet = bulletInstance;
        fireSequence.handGripUsed = string3;
        return fireSequence;
    }

    public static void logFireSequence(FireSequence fireSequence) {
        System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        System.out.println("WEAPON FIRE SEQUENCE EXECUTED");
        System.out.println("Character: " + fireSequence.characterId);
        System.out.println("Gun: " + fireSequence.gunFile);
        System.out.println("Aim Angle: " + fireSequence.aimAngle + "/10");
        System.out.println("Hand Grip: " + fireSequence.handGripUsed);
        System.out.println("Bullet Type: " + fireSequence.spawnedBullet.bulletType);
        System.out.println("Spawn Pos: (" + fireSequence.firingX + ", " + fireSequence.firingY + ")");
        System.out.println("Velocity: (" + fireSequence.spawnedBullet.velocityX + ", " + fireSequence.spawnedBullet.velocityY + ")");
        System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
    }

    public static class FireSequence {
        public String characterId;
        public String gunFile;
        public int aimAngle;
        public float firingX;
        public float firingY;
        public AnimationAndSpriteLoader.BulletSpawner.BulletInstance spawnedBullet;
        public String handGripUsed;
        public long fireTime;

        public FireSequence(String string, float f, float f2, int n) {
            this.characterId = string;
            this.firingX = f;
            this.firingY = f2;
            this.aimAngle = n;
            this.fireTime = System.currentTimeMillis();
        }
    }
}
