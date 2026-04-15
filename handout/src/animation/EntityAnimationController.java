/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.HashMap;
import java.util.Map;
public abstract class EntityAnimationController {
    protected AnimationAndSpriteLoader.AnimationState currentState;
    protected AnimationAndSpriteLoader.AnimationState previousState;
    protected long stateStartTime;
    protected int currentFrame;
    protected AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physics;
    protected float stateAnimationSpeed;
    protected Map<AnimationAndSpriteLoader.AnimationState, String> stateToAssetPath;
    protected Map<AnimationAndSpriteLoader.AnimationState, AnimationAndSpriteLoader.StateTransition> validTransitions;

    public EntityAnimationController(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody) {
        this.physics = physicsBody;
        this.currentState = AnimationAndSpriteLoader.AnimationState.IDLE;
        this.previousState = AnimationAndSpriteLoader.AnimationState.IDLE;
        this.stateStartTime = System.currentTimeMillis();
        this.currentFrame = 0;
        this.stateToAssetPath = new HashMap<AnimationAndSpriteLoader.AnimationState, String>();
        this.validTransitions = new HashMap<AnimationAndSpriteLoader.AnimationState, AnimationAndSpriteLoader.StateTransition>();
        this.initializeAssets();
        this.initializeTransitions();
    }

    protected abstract void initializeAssets();

    protected abstract void initializeTransitions();

    public void update(float f) {
        long l = System.currentTimeMillis() - this.stateStartTime;
        AnimationAndSpriteLoader.AnimationState animationState = this.currentState;
        int n = animationState.frameTimingMs;
        int n2 = animationState.frameCount;
        this.currentFrame = (int)(l / (long)n) % n2;
    }

    protected abstract void updatePhysicsForState(AnimationAndSpriteLoader.AnimationState var1, float var2);

    public boolean transitionTo(AnimationAndSpriteLoader.AnimationState animationState) {
        if (!this.isValidTransition(this.currentState, animationState)) {
            return false;
        }
        this.previousState = this.currentState;
        this.currentState = animationState;
        this.stateStartTime = System.currentTimeMillis();
        this.currentFrame = 0;
        return true;
    }

    protected boolean isValidTransition(AnimationAndSpriteLoader.AnimationState animationState, AnimationAndSpriteLoader.AnimationState animationState2) {
        return true;
    }

    public AnimationAndSpriteLoader.AnimationState getCurrentState() {
        return this.currentState;
    }

    public int getCurrentFrame() {
        return this.currentFrame;
    }

    public String getAssetPath() {
        return this.stateToAssetPath.getOrDefault((Object)this.currentState, "");
    }
}
