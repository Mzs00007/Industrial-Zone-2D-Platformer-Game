/*
 * Decompiled with CFR 0.152.
 */
package ai;

import ai.AI;

public static abstract class AI.AIAgent {
    protected String agentId;
    protected AI.AIState currentState;
    protected AI.AIBehavior behavior;
    protected boolean active = true;
    protected boolean paused = false;
    protected float x;
    protected float y;
    protected float vx;
    protected float vy;

    public AI.AIAgent(String string) {
        this.agentId = string;
        this.currentState = AI.AIState.IDLE;
    }

    public abstract void initialize();

    public abstract void shutdown();

    public abstract void executeBehavior(float var1);

    public abstract void updateMovement(float var1);

    protected abstract void onStateEnter(AI.AIState var1);

    protected abstract void onStateExit(AI.AIState var1);

    public float[] getPosition() {
        return new float[]{this.x, this.y};
    }

    public void setPosition(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public float[] getVelocity() {
        return new float[]{this.vx, this.vy};
    }

    public void setVelocity(float f, float f2) {
        this.vx = f;
        this.vy = f2;
    }

    public void setState(AI.AIState aIState) {
        if (this.currentState != aIState) {
            this.onStateExit(this.currentState);
            this.currentState = aIState;
            this.onStateEnter(aIState);
        }
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
    }

    public void deactivate() {
        this.active = false;
    }

    public void reactivate() {
        this.active = true;
    }

    public String getId() {
        return this.agentId;
    }

    public AI.AIState getState() {
        return this.currentState;
    }

    public boolean isActive() {
        return this.active && !this.paused;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getVelX() {
        return this.vx;
    }

    public float getVelY() {
        return this.vy;
    }

    public AI.AIBehavior getBehavior() {
        return this.behavior;
    }

    public void setBehavior(AI.AIBehavior aIBehavior) {
        this.behavior = aIBehavior;
    }
}
