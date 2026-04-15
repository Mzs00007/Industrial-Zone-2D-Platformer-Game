/*
 * Decompiled with CFR 0.152.
 */
package ai;

import ai.AI;
import java.util.ArrayList;
import java.util.List;
public class AISystem {
    private List<AI.AIAgent> agents = new ArrayList<AI.AIAgent>();
    private AI.AIPathfinder pathfinder = new AI.AIPathfinder();
    private AI.AIDecisionMaker decisionMaker = new AI.AIDecisionMaker();
    private boolean paused = false;
    private float updateInterval = 0.1f;
    private float timeSinceLastUpdate = 0.0f;

    public void addAgent(AI.AIAgent aIAgent) {
        if (!this.agents.contains(aIAgent)) {
            this.agents.add(aIAgent);
            aIAgent.initialize();
        }
    }

    public void removeAgent(AI.AIAgent aIAgent) {
        this.agents.remove(aIAgent);
        aIAgent.shutdown();
    }

    public void update(long l) {
        if (this.paused) {
            return;
        }
        float f = (float)l / 1000.0f;
        this.timeSinceLastUpdate += f;
        if (this.timeSinceLastUpdate >= this.updateInterval) {
            this.updateAIInternal(this.timeSinceLastUpdate);
            this.timeSinceLastUpdate = 0.0f;
        }
        for (AI.AIAgent aIAgent : this.agents) {
            aIAgent.updateMovement(f);
        }
    }

    private void updateAIInternal(float f) {
        for (AI.AIAgent aIAgent : this.agents) {
            if (!aIAgent.isActive()) continue;
            this.decisionMaker.makeDecision(aIAgent);
            aIAgent.executeBehavior(f);
        }
    }

    public AI.AIPathfinder getPathfinder() {
        return this.pathfinder;
    }

    public AI.AIDecisionMaker getDecisionMaker() {
        return this.decisionMaker;
    }

    public void pause() {
        this.paused = true;
        for (AI.AIAgent aIAgent : this.agents) {
            aIAgent.pause();
        }
    }

    public void resume() {
        this.paused = false;
        for (AI.AIAgent aIAgent : this.agents) {
            aIAgent.resume();
        }
    }

    public List<AI.AIAgent> getAgents() {
        return new ArrayList<AI.AIAgent>(this.agents);
    }

    public <T extends AI.AIAgent> List<T> getAgentsOfType(Class<T> clazz) {
        ArrayList<AI.AIAgent> arrayList = new ArrayList<AI.AIAgent>();
        for (AI.AIAgent aIAgent : this.agents) {
            if (!clazz.isInstance(aIAgent)) continue;
            arrayList.add((AI.AIAgent)clazz.cast(aIAgent));
        }
        return arrayList;
    }

    public void clear() {
        for (AI.AIAgent aIAgent : this.agents) {
            aIAgent.shutdown();
        }
        this.agents.clear();
    }

    public String getStats() {
        return String.format("AI Agents: %d | Paused: %s | Update Interval: %.2fs", this.agents.size(), this.paused, Float.valueOf(this.updateInterval));
    }

    public boolean isPaused() {
        return this.paused;
    }

    public float getUpdateInterval() {
        return this.updateInterval;
    }

    public void setUpdateInterval(float f) {
        this.updateInterval = f;
    }

    public int getAgentCount() {
        return this.agents.size();
    }

    public void updateCombatBehaviors(Object object, Object object2, long l, Object object3) {
    }
}
