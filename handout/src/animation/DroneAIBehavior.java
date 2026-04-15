/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
public class DroneAIBehavior
extends AnimationAndSpriteLoader.AIBehavior {
    public static final float DRONE_HEIGHT_OFFSET = 1.5f;
    private DronePattern pattern;
    private float sweepDistance = 8.0f;
    private float hoverAltitude = 1.5f;

    public DroneAIBehavior(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody, float f, float f2, DronePattern dronePattern) {
        super(physicsBody, f, f2);
        this.pattern = dronePattern;
        this.hoverAltitude = physicsBody.position.y + 1.5f;
    }

    @Override
    public AnimationAndSpriteLoader.AnimationState updateBehavior(AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D vector2D) {
        float f = this.getDistanceTo(vector2D);
        this.maintainFlightAltitude();
        if (f < this.detectionRadius) {
            this.isAlerted = true;
            if (f < this.attackRange) {
                this.currentBehaviorState = AnimationAndSpriteLoader.AnimationState.ENEMY_ATTACK;
            } else {
                this.currentBehaviorState = AnimationAndSpriteLoader.AnimationState.ENEMY_CHASE;
                int n = this.getDirectionTo(vector2D);
                this.entityBody.velocity.x = n > 0 ? 5.5f : -5.5f;
            }
        } else {
            this.isAlerted = false;
            switch (this.pattern.ordinal()) {
                case 0: {
                    this.currentBehaviorState = AnimationAndSpriteLoader.AnimationState.ENEMY_IDLE;
                    this.entityBody.velocity.x = 0.0f;
                    break;
                }
                case 1: {
                    this.updateSweepPattern();
                    break;
                }
                case 2: {
                    this.updateSpiralPattern();
                    break;
                }
                case 3: {
                    this.currentBehaviorState = AnimationAndSpriteLoader.AnimationState.ENEMY_IDLE;
                }
            }
        }
        return this.currentBehaviorState;
    }

    private void maintainFlightAltitude() {
        this.entityBody.velocity.y = 0.0f;
        this.entityBody.position.y = this.hoverAltitude;
    }

    private void updateSweepPattern() {
        float f = this.entityBody.position.x;
        this.entityBody.velocity.x = f < this.hoverAltitude - this.sweepDistance ? 1.5f : (f > this.hoverAltitude + this.sweepDistance ? -1.5f : 1.5f);
        this.currentBehaviorState = AnimationAndSpriteLoader.AnimationState.ENEMY_WALK;
    }

    private void updateSpiralPattern() {
        long l = System.currentTimeMillis() % 4000L;
        float f = (float)l / 4000.0f * 2.0f * (float)Math.PI;
        this.entityBody.velocity.x = (float)Math.cos(f) * 2.0f;
        this.currentBehaviorState = AnimationAndSpriteLoader.AnimationState.ENEMY_WALK;
    }

    public void setPattern(DronePattern dronePattern) {
        this.pattern = dronePattern;
    }
public enum DronePattern {
        HOVER,
        SWEEP,
        SPIRAL,
        AGGRESSIVE_PURSUIT;

    }
}
