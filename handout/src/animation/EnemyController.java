/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.EnemyController
extends AnimationAndSpriteLoader.EntityAnimationController {
    private float detectionRadius;
    private boolean isAlerted;
    private AnimationAndSpriteLoader.AIBehavior aiBehavior;

    public AnimationAndSpriteLoader.EnemyController(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody, float f) {
        super(physicsBody);
        this.detectionRadius = f;
        this.isAlerted = false;
        this.aiBehavior = null;
    }

    public void setAIBehavior(AnimationAndSpriteLoader.AIBehavior aIBehavior) {
        this.aiBehavior = aIBehavior;
    }

    @Override
    protected void initializeAssets() {
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.ENEMY_IDLE, "Resources/industrial-zone/characters/enemies/drones/1/01_EnemyDrone_UfoSaucer_Idle_3Frames1Row_HoveringStationary_DefaultIdle_Loop_150ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.ENEMY_WALK, "Resources/industrial-zone/characters/enemies/drones/1/02_EnemyDrone_UfoSaucer_Movement_4Frames1Row_SmoothHoveringMove_Movement_Loop_100ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.ENEMY_CHASE, "Resources/industrial-zone/characters/enemies/drones/1/03_EnemyDrone_UfoSaucer_Chase_4Frames1Row_FastHoveringPursuit_Chase_Loop_80ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.ENEMY_ATTACK, "Resources/industrial-zone/characters/enemies/drones/1/04_EnemyDrone_UfoSaucer_Attack_3Frames1Row_EnergyBeamCharge_Attack_PlayOnce_100ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.ENEMY_HURT, "Resources/industrial-zone/characters/enemies/drones/1/04_EnemyDrone_UfoSaucer_Attack_3Frames1Row_EnergyBeamCharge_Attack_PlayOnce_100ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.ENEMY_DEATH, "Resources/industrial-zone/characters/enemies/drones/1/05_EnemyDrone_UfoSaucer_Death_4Frames1Row_ExplosionDisappear_Death_PlayOnce_120ms.png");
    }

    @Override
    protected void initializeTransitions() {
    }

    @Override
    protected void updatePhysicsForState(AnimationAndSpriteLoader.AnimationState animationState, float f) {
        switch (animationState.ordinal()) {
            case 16: {
                this.physics.isAffectedByGravity = true;
                this.physics.velocity.x = 0.0f;
                break;
            }
            case 18: {
                this.physics.isAffectedByGravity = true;
                this.physics.velocity.x = 4.0f;
                break;
            }
            case 19: {
                this.physics.velocity.x = 0.0f;
                break;
            }
            default: {
                this.physics.isAffectedByGravity = true;
            }
        }
    }

    public void updateAI(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody) {
        if (this.aiBehavior != null) {
            AnimationAndSpriteLoader.AnimationState animationState = this.aiBehavior.updateBehavior(physicsBody.position);
            this.transitionTo(animationState);
        } else {
            float f = Math.abs(this.physics.position.x - physicsBody.position.x);
            if (f < this.detectionRadius) {
                if (!this.isAlerted) {
                    this.isAlerted = true;
                    this.physics.velocity.x *= 1.25f;
                }
                this.transitionTo(AnimationAndSpriteLoader.AnimationState.ENEMY_CHASE);
            } else {
                if (this.isAlerted) {
                    this.isAlerted = false;
                    this.physics.velocity.x *= 0.8f;
                }
                this.transitionTo(AnimationAndSpriteLoader.AnimationState.ENEMY_IDLE);
            }
        }
    }

    public boolean isAlerted() {
        return this.isAlerted;
    }

    public float getDetectionRadius() {
        return this.detectionRadius;
    }

    public static String getEnemyAssetPath(EnemyType enemyType, String string) {
        switch (enemyType.ordinal()) {
            case 0: {
                return "Resources/industrial-zone/characters/enemies/drones/1/" + AnimationAndSpriteLoader.EnemyController.getUfoSaucerAsset(string);
            }
            case 1: {
                return "Resources/industrial-zone/characters/enemies/drones/2/" + AnimationAndSpriteLoader.EnemyController.getJetDroneAsset(string);
            }
            case 2: {
                return "Resources/industrial-zone/characters/enemies/drones/3/" + AnimationAndSpriteLoader.EnemyController.getTransportDroneAsset(string);
            }
            case 3: {
                return "Resources/industrial-zone/characters/enemies/punks/" + AnimationAndSpriteLoader.EnemyController.getPunkAsset(string);
            }
            case 4: {
                return "Resources/industrial-zone/characters/enemies/rugby/" + AnimationAndSpriteLoader.EnemyController.getRugbyPlayerAsset(string);
            }
        }
        return "Resources/industrial-zone/characters/enemies/drones/1/" + AnimationAndSpriteLoader.EnemyController.getUfoSaucerAsset(string);
    }

    private static String getUfoSaucerAsset(String string) {
        switch (string.toUpperCase()) {
            case "IDLE": {
                return "01_EnemyDrone_UfoSaucer_Idle_3Frames1Row_HoveringStationary_DefaultIdle_Loop_150ms.png";
            }
            case "WALK": {
                return "02_EnemyDrone_UfoSaucer_Movement_4Frames1Row_SmoothHoveringMove_Movement_Loop_100ms.png";
            }
            case "CHASE": {
                return "03_EnemyDrone_UfoSaucer_Chase_4Frames1Row_FastHoveringPursuit_Chase_Loop_80ms.png";
            }
            case "ATTACK": {
                return "04_EnemyDrone_UfoSaucer_Attack_3Frames1Row_EnergyBeamCharge_Attack_PlayOnce_100ms.png";
            }
            case "HURT": {
                return "04_EnemyDrone_UfoSaucer_Attack_3Frames1Row_EnergyBeamCharge_Attack_PlayOnce_100ms.png";
            }
            case "DEATH": {
                return "05_EnemyDrone_UfoSaucer_Death_4Frames1Row_ExplosionDisappear_Death_PlayOnce_120ms.png";
            }
        }
        return "01_EnemyDrone_UfoSaucer_Idle_3Frames1Row_HoveringStationary_DefaultIdle_Loop_150ms.png";
    }

    private static String getJetDroneAsset(String string) {
        switch (string.toUpperCase()) {
            case "IDLE": {
                return "01_EnemyDrone_JetDrone_Idle_Hovering_FastMovingUnit_Loop_150ms.png";
            }
            case "WALK": {
                return "02_EnemyDrone_JetDrone_Flight_QuickZoom_HighSpeedMovement_Loop_80ms.png";
            }
            case "CHASE": {
                return "03_EnemyDrone_JetDrone_Pursuit_ZoomRush_InterceptTarget_Loop_60ms.png";
            }
            case "ATTACK": {
                return "04_EnemyDrone_JetDrone_BeamAttack_MissileShot_RangedHit_PlayOnce_100ms.png";
            }
            case "HURT": {
                return "05_EnemyDrone_JetDrone_ReactDamage_JetFlutter_ShieldFlash_PlayOnce_100ms.png";
            }
            case "DEATH": {
                return "06_EnemyDrone_JetDrone_Explode_CrashBurn_Destruction_PlayOnce_120ms.png";
            }
        }
        return "01_EnemyDrone_JetDrone_Idle_Hovering_FastMovingUnit_Loop_150ms.png";
    }

    private static String getTransportDroneAsset(String string) {
        switch (string.toUpperCase()) {
            case "IDLE": {
                return "01_EnemyDrone_TransportDrone_Idle_Stationary_CargoHold_Loop_200ms.png";
            }
            case "WALK": {
                return "02_EnemyDrone_TransportDrone_Lumber_SlowMovement_HeavyLoad_Loop_120ms.png";
            }
            case "CHASE": {
                return "03_EnemyDrone_TransportDrone_Pursue_ModerateSpeed_LoadedPursuit_Loop_100ms.png";
            }
            case "ATTACK": {
                return "04_EnemyDrone_TransportDrone_UnloadAttack_CargoRelease_AreaDamage_PlayOnce_110ms.png";
            }
            case "HURT": {
                return "05_EnemyDrone_TransportDrone_DamageTaken_ShakeLurch_Rumble_PlayOnce_100ms.png";
            }
            case "DEATH": {
                return "06_EnemyDrone_TransportDrone_CrashLanding_Explosion_DebrisSpray_PlayOnce_150ms.png";
            }
        }
        return "01_EnemyDrone_TransportDrone_Idle_Stationary_CargoHold_Loop_200ms.png";
    }

    private static String getPunkAsset(String string) {
        switch (string.toUpperCase()) {
            case "IDLE": {
                return "01_Enemy_Punk_Idle_2Frames_AggressiveStance_WaitingFight_Loop_150ms.png";
            }
            case "WALK": {
                return "02_Enemy_Punk_Walk_4Frames_RegularPace_PatrolMovement_Loop_120ms.png";
            }
            case "CHASE": {
                return "03_Enemy_Punk_Run_6Frames_SprintAttacking_PursuitRun_Loop_80ms.png";
            }
            case "ATTACK": {
                return "04_Enemy_Punk_Punch_5Frames_ComboStrike_MeleeBeat_PlayOnce_80ms.png";
            }
            case "HURT": {
                return "05_Enemy_Punk_TakeDamage_3Frames_Stagger_DamageFlinch_PlayOnce_100ms.png";
            }
            case "DEATH": {
                return "06_Enemy_Punk_Knockout_4Frames_FallSequence_Collapse_PlayOnce_120ms.png";
            }
        }
        return "01_Enemy_Punk_Idle_2Frames_AggressiveStance_WaitingFight_Loop_150ms.png";
    }

    private static String getRugbyPlayerAsset(String string) {
        switch (string.toUpperCase()) {
            case "IDLE": {
                return "01_Enemy_RugbyPlayer_Idle_3Frames_PowerStance_ReadyCharge_Loop_150ms.png";
            }
            case "WALK": {
                return "02_Enemy_RugbyPlayer_Walk_4Frames_HeavyStep_GroundShake_Loop_120ms.png";
            }
            case "CHASE": {
                return "03_Enemy_RugbyPlayer_Charge_5Frames_RushAttack_FullSpeedCharge_Loop_90ms.png";
            }
            case "ATTACK": {
                return "04_Enemy_RugbyPlayer_Tackle_6Frames_BodySlam_GroundImpact_PlayOnce_90ms.png";
            }
            case "HURT": {
                return "05_Enemy_RugbyPlayer_Stun_3Frames_KnockBack_DamageStagger_PlayOnce_100ms.png";
            }
            case "DEATH": {
                return "06_Enemy_RugbyPlayer_Defeat_5Frames_FallDown_FinalCollapse_PlayOnce_120ms.png";
            }
        }
        return "01_Enemy_RugbyPlayer_Idle_3Frames_PowerStance_ReadyCharge_Loop_150ms.png";
    }

    public static enum EnemyType {
        UFO_SAUCER,
        JET_DRONE,
        TRANSPORT_DRONE,
        PUNK,
        RUGBY_PLAYER;

    }
}
