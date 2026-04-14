/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.EnemyAIBehavior
extends AnimationAndSpriteLoader.AIBehavior {
    private EnemyPattern pattern;
    private float patrolDistance = 5.0f;
    private float patrolStartX;
    private boolean patrolDirectionRight = true;

    public AnimationAndSpriteLoader.EnemyAIBehavior(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody, float f, float f2, EnemyPattern enemyPattern) {
        super(physicsBody, f, f2);
        this.pattern = enemyPattern;
        this.patrolStartX = physicsBody.position.x;
    }

    @Override
    public AnimationAndSpriteLoader.AnimationState updateBehavior(AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D vector2D) {
        float f = this.getDistanceTo(vector2D);
        if (f < this.detectionRadius) {
            this.isAlerted = true;
            this.alertTime = System.currentTimeMillis();
            if (f < this.attackRange) {
                this.currentBehaviorState = AnimationAndSpriteLoader.AnimationState.ENEMY_ATTACK;
                this.entityBody.velocity.x = 0.0f;
            } else {
                this.currentBehaviorState = AnimationAndSpriteLoader.AnimationState.ENEMY_CHASE;
                int n = this.getDirectionTo(vector2D);
                this.entityBody.velocity.x = n > 0 ? 4.0f : -4.0f;
            }
        } else {
            this.isAlerted = false;
            this.currentBehaviorState = AnimationAndSpriteLoader.AnimationState.ENEMY_IDLE;
            this.entityBody.velocity.x = 0.0f;
            switch (this.pattern.ordinal()) {
                case 0: {
                    this.updatePatrol();
                    break;
                }
                case 1: {
                    break;
                }
                case 2: {
                    break;
                }
            }
        }
        return this.currentBehaviorState;
    }

    private void updatePatrol() {
        float f = Math.abs(this.entityBody.position.x - this.patrolStartX);
        if (f >= this.patrolDistance) {
            this.patrolDirectionRight = !this.patrolDirectionRight;
        }
        this.entityBody.velocity.x = this.patrolDirectionRight ? 1.5f : -1.5f;
        this.currentBehaviorState = AnimationAndSpriteLoader.AnimationState.ENEMY_WALK;
    }

    public void setPattern(EnemyPattern enemyPattern) {
        this.pattern = enemyPattern;
    }

    public static enum EnemyPattern {
        PATROL_HORIZONTAL,
        PATROL_STATIONARY,
        AGGRESSIVE,
        TACTICAL;

    }
}
