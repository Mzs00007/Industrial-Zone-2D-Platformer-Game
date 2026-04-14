/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.BossAIBehavior
extends AnimationAndSpriteLoader.AIBehavior {
    private float healthPercent = 1.0f;
    private BossPhase currentPhase = BossPhase.PHASE_1;
    private int attackPattern = 0;
    private float combatDistance = 3.0f;

    public AnimationAndSpriteLoader.BossAIBehavior(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody, float f, float f2) {
        super(physicsBody, f, f2);
    }

    @Override
    public AnimationAndSpriteLoader.AnimationState updateBehavior(AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D vector2D) {
        float f = this.getDistanceTo(vector2D);
        this.updatePhase();
        if (f > this.attackRange) {
            int n = this.getDirectionTo(vector2D);
            this.entityBody.velocity.x = n > 0 ? 2.5f : -2.5f;
            this.currentBehaviorState = AnimationAndSpriteLoader.AnimationState.ENEMY_CHASE;
        } else {
            this.entityBody.velocity.x = 0.0f;
            this.currentBehaviorState = this.selectAttackState();
        }
        return this.currentBehaviorState;
    }

    private void updatePhase() {
        this.currentPhase = this.healthPercent > 0.75f ? BossPhase.PHASE_1 : (this.healthPercent > 0.25f ? BossPhase.PHASE_2 : BossPhase.PHASE_3);
    }

    private AnimationAndSpriteLoader.AnimationState selectAttackState() {
        this.attackPattern = (this.attackPattern + 1) % 3;
        switch (this.currentPhase.ordinal()) {
            case 0: {
                return AnimationAndSpriteLoader.AnimationState.BOSS_ATTACK_PHASE1;
            }
            case 1: {
                return AnimationAndSpriteLoader.AnimationState.BOSS_ATTACK_PHASE2;
            }
            case 2: {
                return AnimationAndSpriteLoader.AnimationState.BOSS_SPECIAL;
            }
        }
        return AnimationAndSpriteLoader.AnimationState.BOSS_IDLE;
    }

    public void updateHealth(float f) {
        this.healthPercent = Math.max(0.0f, Math.min(1.0f, f));
    }

    public float getHealthPercent() {
        return this.healthPercent;
    }

    public BossPhase getCurrentPhase() {
        return this.currentPhase;
    }

    public static enum BossPhase {
        PHASE_1,
        PHASE_2,
        PHASE_3;

    }
}
