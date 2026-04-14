/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static abstract class AnimationAndSpriteLoader.AIBehavior {
    protected AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody entityBody;
    protected AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D targetPosition;
    protected AnimationAndSpriteLoader.AnimationState currentBehaviorState = AnimationAndSpriteLoader.AnimationState.ENEMY_IDLE;
    protected float detectionRadius;
    protected float attackRange;
    protected boolean isAlerted;
    protected long alertTime;

    public AnimationAndSpriteLoader.AIBehavior(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody, float f, float f2) {
        this.entityBody = physicsBody;
        this.detectionRadius = f;
        this.attackRange = f2;
        this.isAlerted = false;
        this.alertTime = System.currentTimeMillis();
    }

    public abstract AnimationAndSpriteLoader.AnimationState updateBehavior(AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D var1);

    protected float getDistanceTo(AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D vector2D) {
        if (vector2D == null) {
            return Float.MAX_VALUE;
        }
        float f = this.entityBody.position.x - vector2D.x;
        float f2 = this.entityBody.position.y - vector2D.y;
        return (float)Math.sqrt(f * f + f2 * f2);
    }

    protected int getDirectionTo(AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D vector2D) {
        if (vector2D == null) {
            return 0;
        }
        float f = vector2D.x - this.entityBody.position.x;
        if (Math.abs(f) < 0.1f) {
            return 0;
        }
        return f > 0.0f ? 1 : -1;
    }

    public AnimationAndSpriteLoader.AnimationState getCurrentState() {
        return this.currentBehaviorState;
    }

    public boolean isAlerted() {
        return this.isAlerted;
    }

    public void setAlerted(boolean bl) {
        this.isAlerted = bl;
        this.alertTime = System.currentTimeMillis();
    }
}
