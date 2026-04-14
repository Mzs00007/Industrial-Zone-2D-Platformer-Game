/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.DroneController
extends AnimationAndSpriteLoader.EntityAnimationController {
    private static final float DRONE_HEIGHT_OFFSET = 1.5f;
    private float detectionRadius;
    private boolean isAlerted;
    private AnimationAndSpriteLoader.AIBehavior aiBehavior;
    private float baseGroundY;

    public AnimationAndSpriteLoader.DroneController(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody, float f, float f2) {
        super(physicsBody);
        this.detectionRadius = f;
        this.isAlerted = false;
        this.baseGroundY = f2;
        this.aiBehavior = null;
        this.physics.position.y = f2 - 1.5f;
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
        this.physics.isAffectedByGravity = false;
        switch (animationState.ordinal()) {
            case 16: {
                this.physics.velocity.x = 0.0f;
                this.physics.velocity.y = 0.0f;
                break;
            }
            case 17: {
                this.physics.velocity.x = 2.0f;
                this.physics.velocity.y = 0.0f;
                break;
            }
            case 18: {
                this.physics.velocity.x = 5.0f;
                this.physics.velocity.y = 0.0f;
                break;
            }
            case 19: {
                this.physics.velocity.x = 0.0f;
                this.physics.velocity.y = 0.0f;
                break;
            }
            default: {
                this.physics.velocity.y = 0.0f;
            }
        }
    }

    public void updateAI(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody) {
        this.physics.position.y = this.baseGroundY - 1.5f;
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

    public float getAltitude() {
        return 1.5f;
    }
}
