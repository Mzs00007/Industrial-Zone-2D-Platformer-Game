/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.PlayerController
extends AnimationAndSpriteLoader.EntityAnimationController {
    private AnimationAndSpriteLoader.InputHandler baseInput;
    private AnimationAndSpriteLoader.InputController inputController;
    private boolean isGrounded;
    private boolean isInAir;
    private int jumpCount;

    public AnimationAndSpriteLoader.PlayerController(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody, AnimationAndSpriteLoader.InputHandler inputHandler) {
        super(physicsBody);
        this.baseInput = inputHandler;
        this.inputController = new AnimationAndSpriteLoader.InputController(inputHandler);
        this.isGrounded = true;
        this.isInAir = false;
        this.jumpCount = 0;
    }

    @Override
    protected void initializeAssets() {
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.IDLE, "Resources/industrial-zone/characters/biker/01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.WALK_LEFT, "Resources/industrial-zone/characters/biker/03_Player_Biker_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.WALK_RIGHT, "Resources/industrial-zone/characters/biker/03_Player_Biker_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.JUMP, "Resources/industrial-zone/characters/biker/06_Player_Biker_Jump_4Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.DOUBLE_JUMP, "Resources/industrial-zone/characters/biker/07_Player_Biker_DoubleJump_6Frames1Row_MidAirFlipBoost_SecondJump_PlayOnce_80ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.FALL, "Resources/industrial-zone/characters/biker/08_Player_Biker_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.DASH_LEFT, "Resources/industrial-zone/characters/biker/05_Player_Biker_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.DASH_RIGHT, "Resources/industrial-zone/characters/biker/05_Player_Biker_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.CLIMB, "Resources/industrial-zone/characters/biker/09_Player_Biker_Climb_6Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.HANG, "Resources/industrial-zone/characters/biker/10_Player_Biker_Hang_3Frames1Row_LedgeHangHold_HangingIdle_Loop_150ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.ATTACK_MELEE, "Resources/industrial-zone/characters/biker/12_Player_Biker_Punch_5Frames1Row_StandingPunchCombo_MeleeAttack_PlayOnce_70ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.ATTACK_RANGE, "Resources/industrial-zone/characters/biker/15_Player_Biker_Attack3_7Frames1Row_EnergyWaveRelease_Attack3_PlayOnce_70ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.HURT, "Resources/industrial-zone/characters/biker/18_Player_Biker_Hurt_2Frames1Row_HitReactionGhostFlinch_TakeDamage_PlayOnce_100ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.DEATH, "Resources/industrial-zone/characters/biker/19_Player_Biker_Death_6Frames1Row_DeathFallSequence_PlayerDeath_PlayOnce_120ms.png");
    }

    @Override
    protected void initializeTransitions() {
        this.validTransitions.put(AnimationAndSpriteLoader.AnimationState.IDLE, new AnimationAndSpriteLoader.StateTransition(AnimationAndSpriteLoader.AnimationState.IDLE, AnimationAndSpriteLoader.AnimationState.WALK_RIGHT, 1.0f, 1.0f, true, "Idle to walk right"));
    }

    @Override
    public void update(float f) {
        super.update(f);
        this.updateAirState(f);
        this.handleInput();
    }

    private void updateAirState(float f) {
        if (this.isInAir && this.physics.velocity.y < 0.0f && this.currentState != AnimationAndSpriteLoader.AnimationState.FALL) {
            this.transitionTo(AnimationAndSpriteLoader.AnimationState.FALL);
        }
        if (this.isInAir && this.isGrounded && this.currentState != AnimationAndSpriteLoader.AnimationState.LAND) {
            this.transitionTo(AnimationAndSpriteLoader.AnimationState.LAND);
            this.isInAir = false;
        }
    }

    private void handleInput() {
        AnimationAndSpriteLoader.AnimationState animationState = this.inputController.updateAndGetState();
        if (animationState != this.currentState) {
            this.transitionTo(animationState);
        }
        this.applyPhysicsForState(animationState);
    }

    private void applyPhysicsForState(AnimationAndSpriteLoader.AnimationState animationState) {
        switch (animationState.ordinal()) {
            case 1: {
                this.physics.velocity.x = -5.0f;
                break;
            }
            case 2: {
                this.physics.velocity.x = 5.0f;
                break;
            }
            case 7: {
                this.physics.velocity.x = -15.0f;
                break;
            }
            case 8: {
                this.physics.velocity.x = 15.0f;
                break;
            }
            case 3: {
                this.physics.velocity.y = AnimationAndSpriteLoader.PhysicsUnitSystem.STANDARD_JUMP_VELOCITY;
                this.isInAir = true;
                this.isGrounded = false;
                break;
            }
            case 4: {
                this.physics.velocity.y = AnimationAndSpriteLoader.PhysicsUnitSystem.HIGH_JUMP_VELOCITY;
                break;
            }
            case 5: {
                this.physics.isAffectedByGravity = true;
                break;
            }
            case 12: 
            case 13: {
                this.physics.velocity.x = 0.0f;
                break;
            }
            case 9: {
                this.physics.isAffectedByGravity = false;
                this.physics.velocity.y = -3.0f;
                break;
            }
            case 10: {
                this.physics.velocity.x = 0.0f;
                this.physics.velocity.y = 0.0f;
                this.physics.isAffectedByGravity = false;
                break;
            }
            case 11: {
                this.physics.velocity.x = 0.0f;
                this.physics.velocity.y = -1.5f;
                this.physics.isAffectedByGravity = false;
                break;
            }
            case 0: {
                this.physics.velocity.x = 0.0f;
                break;
            }
        }
    }

    @Override
    protected void updatePhysicsForState(AnimationAndSpriteLoader.AnimationState animationState, float f) {
        switch (animationState.ordinal()) {
            case 3: {
                this.physics.isAffectedByGravity = true;
                this.physics.isGrounded = false;
                break;
            }
            case 4: {
                this.physics.isAffectedByGravity = true;
                this.physics.isGrounded = false;
                break;
            }
            case 5: {
                this.physics.isAffectedByGravity = true;
                break;
            }
            case 9: {
                this.physics.isAffectedByGravity = false;
                this.physics.velocity.y = -3.0f;
                break;
            }
            case 0: 
            case 1: 
            case 2: {
                this.physics.isGrounded = true;
                this.physics.velocity.y = 0.0f;
                break;
            }
        }
    }

    public void setGrounded(boolean bl) {
        this.isGrounded = bl;
        if (bl) {
            this.jumpCount = 0;
            this.isInAir = false;
        } else {
            this.isInAir = true;
        }
    }

    public boolean isGrounded() {
        return this.isGrounded;
    }

    public boolean isInAir() {
        return this.isInAir;
    }

    public int getJumpCount() {
        return this.jumpCount;
    }
}
