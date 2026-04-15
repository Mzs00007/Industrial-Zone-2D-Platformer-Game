/*
 * Decompiled with CFR 0.152.
 */
package ai;

import ai.AI;
class EnemyBehavior
implements AI.AIBehavior {
    private EnemyBehavior(AI.EnemyAI enemyAI) {
    }

    @Override
    public void initialize() {
    }

    @Override
    public AI.AIBehavior.AIAction execute(AI.AIAgent aIAgent, float f) {
        return null;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public void reset() {
    }

    @Override
    public void stop() {
    }

    @Override
    public String getName() {
        return "enemy_behavior";
    }
}
