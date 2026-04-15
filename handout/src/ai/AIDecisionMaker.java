/*
 * Decompiled with CFR 0.152.
 */
package ai;

import ai.AI;
public class AIDecisionMaker {
    private DecisionContext context;

    public void makeDecision(AI.AIAgent aIAgent) {
        this.context = new DecisionContext(aIAgent);
        AI.AIState aIState = this.evaluateState(aIAgent);
        if (aIState != aIAgent.getState()) {
            aIAgent.setState(aIState);
        }
    }

    private AI.AIState evaluateState(AI.AIAgent aIAgent) {
        AI.AIState aIState = aIAgent.getState();
        if (aIState == AI.AIState.DEAD) {
            return AI.AIState.DEAD;
        }
        if (aIState == AI.AIState.STUNNED) {
            return AI.AIState.STUNNED;
        }
        if (this.shouldEnterCombat(aIAgent)) {
            return AI.AIState.ATTACK;
        }
        if (this.shouldChase(aIAgent)) {
            return AI.AIState.CHASE;
        }
        if (this.shouldAlert(aIAgent)) {
            return AI.AIState.ALERT;
        }
        if (this.shouldFlee(aIAgent)) {
            return AI.AIState.FLEE;
        }
        if (this.shouldPatrol(aIAgent)) {
            return AI.AIState.PATROL;
        }
        return AI.AIState.IDLE;
    }

    protected boolean shouldEnterCombat(AI.AIAgent aIAgent) {
        float f = this.getDistanceToTarget(aIAgent);
        return f < 50.0f && this.context.canSeePlayer;
    }

    protected boolean shouldChase(AI.AIAgent aIAgent) {
        float f = this.getDistanceToTarget(aIAgent);
        return f < 300.0f && this.context.canSeePlayer;
    }

    protected boolean shouldAlert(AI.AIAgent aIAgent) {
        float f = this.getDistanceToTarget(aIAgent);
        return f < 150.0f && !this.context.canSeePlayer && this.context.isAlerted;
    }

    protected boolean shouldFlee(AI.AIAgent aIAgent) {
        return this.context.getHealthPercent() < 0.2f && this.context.isInCombat;
    }

    protected boolean shouldPatrol(AI.AIAgent aIAgent) {
        return aIAgent.getState() == AI.AIState.IDLE || aIAgent.getState() == AI.AIState.PATROL;
    }

    protected float getDistanceToTarget(AI.AIAgent aIAgent) {
        if (this.context.playerPos == null) {
            return Float.MAX_VALUE;
        }
        float[] fArray = aIAgent.getPosition();
        float f = fArray[0] - this.context.playerPos[0];
        float f2 = fArray[1] - this.context.playerPos[1];
        return (float)Math.sqrt(f * f + f2 * f2);
    }

    protected boolean hasLineOfSight(AI.AIAgent aIAgent, float[] fArray) {
        return this.getDistanceToTarget(aIAgent) < 200.0f;
    }

    public void updateContext(AI.AIAgent aIAgent, float[] fArray, float f, float f2) {
        if (this.context != null && this.context.agent == aIAgent) {
            this.context.playerPos = fArray;
            this.context.health = f;
            this.context.maxHealth = f2;
            this.context.distanceToPlayer = this.getDistanceToTarget(aIAgent);
            this.context.canSeePlayer = this.hasLineOfSight(aIAgent, fArray);
        }
    }

    public DecisionContext getContext() {
        return this.context;
    }
public class DecisionContext {
        public AI.AIAgent agent;
        public float[] agentPos;
        public float[] playerPos;
        public float distanceToPlayer = Float.MAX_VALUE;
        public boolean canSeePlayer = false;
        public boolean isAlerted = false;
        public boolean isInCombat = false;
        public float health = 100.0f;
        public float maxHealth = 100.0f;

        public DecisionContext(AI.AIAgent aIAgent) {
            this.agent = aIAgent;
            this.agentPos = aIAgent.getPosition();
        }

        public float getHealthPercent() {
            return this.health / this.maxHealth;
        }
    }
}
