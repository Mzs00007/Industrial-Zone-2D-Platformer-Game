/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.BossController
extends AnimationAndSpriteLoader.EntityAnimationController {
    private float healthPercent = 1.0f;
    private int attackPatternIndex = 0;
    private AnimationAndSpriteLoader.AIBehavior aiBehavior = null;

    public AnimationAndSpriteLoader.BossController(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody) {
        super(physicsBody);
    }

    public void setAIBehavior(AnimationAndSpriteLoader.AIBehavior aIBehavior) {
        this.aiBehavior = aIBehavior;
    }

    @Override
    protected void initializeAssets() {
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.BOSS_IDLE, "Resources/industrial-zone/characters/bosses/GreenMech/01_Boss_GreenMech_Idle_4Frames1Row_MechStandingStationary_DefaultIdle_Loop_150ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.BOSS_ATTACK_PHASE1, "Resources/industrial-zone/characters/bosses/GreenMech/07_Boss_GreenMech_Attack_DirectStrike_MeleePhase1_PlayOnce_90ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.BOSS_ATTACK_PHASE2, "Resources/industrial-zone/characters/bosses/GreenMech/08_Boss_GreenMech_Attack2_AdvancedCombo_MeleePhase2_PlayOnce_80ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.BOSS_SPECIAL, "Resources/industrial-zone/characters/bosses/GreenMech/06_Boss_GreenMech_Charge_PoweringUp_SpecialAttack_PlayOnce_100ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.BOSS_WEAK, "Resources/industrial-zone/characters/bosses/GreenMech/09_Boss_GreenMech_Hit_DamageTaken_BossWeak_PlayOnce_100ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.BOSS_DEATH, "Resources/industrial-zone/characters/bosses/GreenMech/10_Boss_GreenMech_Death_MechCollapse_BossDeath_PlayOnce_120ms.png");
    }

    @Override
    protected void initializeTransitions() {
    }

    @Override
    protected void updatePhysicsForState(AnimationAndSpriteLoader.AnimationState animationState, float f) {
        switch (animationState.ordinal()) {
            case 23: 
            case 24: 
            case 25: {
                this.physics.velocity.x = 0.0f;
                break;
            }
            case 22: {
                this.physics.velocity.x = 0.0f;
                break;
            }
        }
    }

    public void updateBehavior(AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D vector2D) {
        if (this.aiBehavior != null) {
            AnimationAndSpriteLoader.AnimationState animationState = this.aiBehavior.updateBehavior(vector2D);
            this.transitionTo(animationState);
        } else {
            int n = 0;
            n = this.healthPercent > 0.5f ? 0 : (this.healthPercent > 0.25f ? 1 : 2);
            this.attackPatternIndex = (this.attackPatternIndex + 1) % 3;
            switch (n) {
                case 0: {
                    if (this.attackPatternIndex == 0) {
                        this.transitionTo(AnimationAndSpriteLoader.AnimationState.BOSS_ATTACK_PHASE1);
                        break;
                    }
                    this.transitionTo(AnimationAndSpriteLoader.AnimationState.BOSS_IDLE);
                    break;
                }
                case 1: {
                    if (this.attackPatternIndex <= 1) {
                        this.transitionTo(AnimationAndSpriteLoader.AnimationState.BOSS_ATTACK_PHASE2);
                        break;
                    }
                    this.transitionTo(AnimationAndSpriteLoader.AnimationState.BOSS_IDLE);
                    break;
                }
                case 2: {
                    if (this.attackPatternIndex == 0) {
                        this.transitionTo(AnimationAndSpriteLoader.AnimationState.BOSS_SPECIAL);
                        break;
                    }
                    if (this.attackPatternIndex == 1) {
                        this.transitionTo(AnimationAndSpriteLoader.AnimationState.BOSS_ATTACK_PHASE2);
                        break;
                    }
                    this.transitionTo(AnimationAndSpriteLoader.AnimationState.BOSS_IDLE);
                }
            }
        }
    }

    public void takeDamage(float f) {
        this.healthPercent -= f;
        if (this.healthPercent < 0.0f) {
            this.healthPercent = 0.0f;
        }
        this.transitionTo(AnimationAndSpriteLoader.AnimationState.BOSS_WEAK);
    }

    public int getAttackPatternIndex() {
        return this.attackPatternIndex;
    }

    public float getHealthPercent() {
        return this.healthPercent;
    }

    public boolean isPhase2() {
        return this.healthPercent <= 0.5f && this.healthPercent > 0.25f;
    }

    public boolean isPhase3() {
        return this.healthPercent <= 0.25f;
    }

    public static String getBossAssetPath(BossType bossType, String string) {
        String string2 = AnimationAndSpriteLoader.BOSS_BASE;
        switch (bossType.ordinal()) {
            case 0: {
                return string2 + "GreenMech/" + AnimationAndSpriteLoader.BossController.getGreenMechAsset(string);
            }
            case 1: {
                return string2 + "GolfCartSoldier/" + AnimationAndSpriteLoader.BossController.getGolfCartSoldierAsset(string);
            }
            case 2: {
                return string2 + "RugbyGuy/" + AnimationAndSpriteLoader.BossController.getRugbyGuyAsset(string);
            }
        }
        return string2 + "GreenMech/" + AnimationAndSpriteLoader.BossController.getGreenMechAsset(string);
    }

    private static String getGreenMechAsset(String string) {
        switch (string.toUpperCase()) {
            case "IDLE": {
                return "01_Boss_GreenMech_Idle_4Frames1Row_MechStandingStationary_DefaultIdle_Loop_150ms.png";
            }
            case "ATTACK1": {
                return "07_Boss_GreenMech_Attack_DirectStrike_MeleePhase1_PlayOnce_90ms.png";
            }
            case "ATTACK2": {
                return "08_Boss_GreenMech_Attack2_AdvancedCombo_MeleePhase2_PlayOnce_80ms.png";
            }
            case "CHARGE": {
                return "06_Boss_GreenMech_Charge_PoweringUp_SpecialAttack_PlayOnce_100ms.png";
            }
            case "HIT": {
                return "09_Boss_GreenMech_Hit_DamageTaken_BossWeak_PlayOnce_100ms.png";
            }
            case "DEATH": {
                return "10_Boss_GreenMech_Death_MechCollapse_BossDeath_PlayOnce_120ms.png";
            }
        }
        return "01_Boss_GreenMech_Idle_4Frames1Row_MechStandingStationary_DefaultIdle_Loop_150ms.png";
    }

    private static String getGolfCartSoldierAsset(String string) {
        switch (string.toUpperCase()) {
            case "IDLE": {
                return "01_Boss_GolfCartSoldier_Idle_[STATEINDEX]_Aiming_RangedIdle_Loop_150ms.png";
            }
            case "ATTACK1": {
                return "03_Boss_GolfCartSoldier_Attack_SingleShot_RangedAttack_PlayOnce_100ms.png";
            }
            case "ATTACK2": {
                return "04_Boss_GolfCartSoldier_Attack2_DoubleShot_RangedPhase2_PlayOnce_90ms.png";
            }
            case "CHARGE": {
                return "05_Boss_GolfCartSoldier_Charge_AmmoPack_SpecialAttack_PlayOnce_120ms.png";
            }
            case "HIT": {
                return "08_Boss_GolfCartSoldier_Hit_ShieldFlash_DamageSoak_PlayOnce_100ms.png";
            }
            case "DEATH": {
                return "11_Boss_GolfCartSoldier_Death_VehicleExplosion_BossDeath_PlayOnce_150ms.png";
            }
        }
        return "01_Boss_GolfCartSoldier_Idle_1_Aiming_RangedIdle_Loop_150ms.png";
    }

    private static String getRugbyGuyAsset(String string) {
        switch (string.toUpperCase()) {
            case "IDLE": {
                return "01_Boss_RugbyGuy_Idle_3Frames_PowerStance_ReadyForRush_Loop_150ms.png";
            }
            case "ATTACK1": {
                return "02_Boss_RugbyGuy_Charge_OnRush_MeleeCharge_PlayOnce_70ms.png";
            }
            case "ATTACK2": {
                return "03_Boss_RugbyGuy_GrappleStart_TackleInitiate_GrappleMelee_PlayOnce_80ms.png";
            }
            case "CHARGE": {
                return "04_Boss_RugbyGuy_PowerUp_MuscleTense_SpecialRush_PlayOnce_100ms.png";
            }
            case "HIT": {
                return "05_Boss_RugbyGuy_Knockback_StaggerReaction_DamageTaken_PlayOnce_100ms.png";
            }
            case "DEATH": {
                return "06_Boss_RugbyGuy_Collapse_FinalBlow_BossDeath_PlayOnce_120ms.png";
            }
        }
        return "01_Boss_RugbyGuy_Idle_3Frames_PowerStance_ReadyForRush_Loop_150ms.png";
    }

    public static enum BossType {
        GREEN_MECH,
        GOLF_CART_SOLDIER,
        RUGBY_GUY;

    }
}
