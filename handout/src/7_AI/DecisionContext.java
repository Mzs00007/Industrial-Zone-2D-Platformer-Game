/*
 * Decompiled with CFR 0.152.
 */
package ai;

import ai.AI;

public static class AI.AIDecisionMaker.DecisionContext {
    public AI.AIAgent agent;
    public float[] agentPos;
    public float[] playerPos;
    public float distanceToPlayer = Float.MAX_VALUE;
    public boolean canSeePlayer = false;
    public boolean isAlerted = false;
    public boolean isInCombat = false;
    public float health = 100.0f;
    public float maxHealth = 100.0f;

    public AI.AIDecisionMaker.DecisionContext(AI.AIAgent aIAgent) {
        this.agent = aIAgent;
        this.agentPos = aIAgent.getPosition();
    }

    public float getHealthPercent() {
        return this.health / this.maxHealth;
    }
}
