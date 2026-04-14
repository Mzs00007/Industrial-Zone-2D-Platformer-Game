/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static enum AnimationAndSpriteLoader.EnemyProjectileRegistry.EnemyProjectileType {
    RUGBY_BALL("RugbyBall", "Resources/industrial-zone/characters/RugbyGuy/03_Boss_RugbyGuy_Projectile_1Frame_RugbyBallThrow_RangedAttackProjectile_Single_Instant.png", AnimationAndSpriteLoader.WeaponSystemCore.TrajectoryType.ARC, 30, 10.0),
    GHOST_ORB("GhostOrb", "Resources/industrial-zone/characters/2/02_Enemy_ArmouredKnight_Projectile_1Frame1Row_SingleProjectileSprite_Projectile_Loop_100ms.png", AnimationAndSpriteLoader.WeaponSystemCore.TrajectoryType.HOMING, 20, 8.0),
    CAPSULE_PROJECTILE("CapsuleProjectile", "Resources/industrial-zone/characters/6/04_EnemyDrone_HoverPlatform_CapsuleProjectileAttack_7Frames1Row.png", AnimationAndSpriteLoader.WeaponSystemCore.TrajectoryType.STRAIGHT, 40, 5.0),
    COMBAT_LASER("CombatLaser", "Resources/industrial-zone/characters/1/03_Enemy_CombatTank_Attack1_4Frames1Row_TurretStraightShot_RangedAttack_PlayOnce_80ms.png", AnimationAndSpriteLoader.WeaponSystemCore.TrajectoryType.STRAIGHT, 25, 12.0);

    public final String name;
    public final String spritePath;
    public final AnimationAndSpriteLoader.WeaponSystemCore.TrajectoryType trajectoryType;
    public final int baseDamage;
    public final double velocity;

    private AnimationAndSpriteLoader.EnemyProjectileRegistry.EnemyProjectileType(String string2, String string3, AnimationAndSpriteLoader.WeaponSystemCore.TrajectoryType trajectoryType, int n2, double d) {
        this.name = string2;
        this.spritePath = string3;
        this.trajectoryType = trajectoryType;
        this.baseDamage = n2;
        this.velocity = d;
    }
}
