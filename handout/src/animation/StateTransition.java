/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.StateTransition {
    public AnimationAndSpriteLoader.AnimationState fromState;
    public AnimationAndSpriteLoader.AnimationState toState;
    public float gravityMultiplier;
    public float velocityMultiplier;
    public boolean canInterrupt;
    public String description;

    public AnimationAndSpriteLoader.StateTransition(AnimationAndSpriteLoader.AnimationState animationState, AnimationAndSpriteLoader.AnimationState animationState2, float f, float f2, boolean bl, String string) {
        this.fromState = animationState;
        this.toState = animationState2;
        this.gravityMultiplier = f;
        this.velocityMultiplier = f2;
        this.canInterrupt = bl;
        this.description = string;
    }

    public String toString() {
        return String.format("%s \u2192 %s (gravity:%.2f, vel:%.2f, interrupt:%s)", new Object[]{this.fromState, this.toState, Float.valueOf(this.gravityMultiplier), Float.valueOf(this.velocityMultiplier), this.canInterrupt});
    }
}
