/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class WeaponFireChain {
    public static final String CHAIN_NAME = "weapon_fire";
    public static final String START_POINT = "GunProperties.GUN_[TYPE]_[VARIANT]";
    public static final String STEP_1_WEAPON_SELECT = "GunProperties - Select specific gun file";
    public static final String STEP_2_CHARACTER_EQUIP = "WeaponCharacterAnimations - Load character weapon idle animation";
    public static final String STEP_2_DETAIL = "   Example: WeaponBikerAnimations.IDLE_VARIANT_A";
    public static final String STEP_3_HAND_GRIP = "WeaponHandPoses - Apply grip pose overlay";
    public static final String STEP_3_DETAIL = "   Example: WeaponHandPoses.BikerHands.GRIP_HORIZONTAL";
    public static final String STEP_4_AIMING = "WeaponHandPoses - Cycle through 10 grip angles as player aims";
    public static final String STEP_4_DETAIL = "   Grips 0-9 cover full 360\u00b0 aiming circle";
    public static final String STEP_5_FIRE = "WeaponCharacterAnimations - Play fire/attack animation";
    public static final String STEP_5_DETAIL = "   Example: WeaponBikerAnimations.FIRE_VARIANT_A";
    public static final String STEP_6_PROJECTILE = "BulletProperties - Spawn ammo sprite";
    public static final String STEP_6_DETAIL = "   Example: BulletProperties.BULLET_TYPE_A (brass round)";
    public static final String STEP_7_TRACER = "ProjectileTracerProperties - Show bullet trajectory";
    public static final String STEP_7_DETAIL = "   Example: ProjectileTracerProperties.TRACER_TYPE_A_VARIANT_NARROW";
    public static final String STEP_8_IMPACT = "VfxAssetProperties - Play collision effect";
    public static final String STEP_8_DETAIL = "   Example: VfxAssetProperties.IMPACT_SPARK_BURST_GOLD_VAR1";
    public static final String STEP_9_DAMAGE = "Character properties - Apply damage value";
    public static final String STEP_9_DETAIL = "   Determines gameplay outcome";
}
