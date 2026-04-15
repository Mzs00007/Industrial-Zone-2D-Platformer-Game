/*
 * Decompiled with CFR 0.152.
 */
package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import physics.CollisionDetector;
public class AIBehaviorSystem {
    private Map<Integer, AIAgent> agents = new HashMap<Integer, AIAgent>();
    private int agentIdCounter = 0;
    private Random random = new Random();
    private CollisionDetector collisionDetector;

    public AIBehaviorSystem(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
    }

    public int createAgent(float f, float f2) {
        int n = this.agentIdCounter++;
        AIAgent aIAgent = new AIAgent(n, f, f2);
        this.agents.put(n, aIAgent);
        return n;
    }

    public int createAgent(float f, float f2, Difficulty difficulty, String string) {
        int n = this.createAgent(f, f2);
        AIAgent aIAgent = this.agents.get(n);
        aIAgent.difficulty = difficulty;
        aIAgent.type = string;
        return n;
    }

    public AIAgent getAgent(int n) {
        return this.agents.get(n);
    }

    public void update(long l, float f, float f2, float f3) {
        for (AIAgent aIAgent : this.agents.values()) {
            if (!aIAgent.isActive()) continue;
            this.updatePerception(aIAgent, f, f2, f3);
            this.updateBehavior(aIAgent, l, f, f2);
            aIAgent.x += aIAgent.vx * ((float)l / 1000.0f);
            aIAgent.y += aIAgent.vy * ((float)l / 1000.0f);
        }
    }

    private void updatePerception(AIAgent aIAgent, float f, float f2, float f3) {
        float f4 = f - aIAgent.x;
        float f5 = f2 - aIAgent.y;
        float f6 = (float)Math.sqrt(f4 * f4 + f5 * f5);
        aIAgent.canSeePlayer = f6 <= aIAgent.sensorRange;
    }

    private void updateBehavior(AIAgent aIAgent, long l, float f, float f2) {
        this.updateState(aIAgent, f, f2);
        switch (aIAgent.state.ordinal()) {
            case 0: {
                this.behaviorIdle(aIAgent);
                break;
            }
            case 1: {
                this.behaviorPatrol(aIAgent);
                break;
            }
            case 2: {
                this.behaviorChase(aIAgent, f, f2);
                break;
            }
            case 3: {
                this.behaviorAttack(aIAgent, f, f2, l);
                break;
            }
            case 4: {
                this.behaviorFlee(aIAgent, f, f2);
                break;
            }
            case 5: {
                this.behaviorDead(aIAgent);
            }
        }
    }

    private void updateState(AIAgent aIAgent, float f, float f2) {
        AIState aIState = aIAgent.state;
        if (aIAgent.state == AIState.DEAD) {
            return;
        }
        float f3 = f - aIAgent.x;
        float f4 = f2 - aIAgent.y;
        float f5 = (float)Math.sqrt(f3 * f3 + f4 * f4);
        if (aIAgent.canSeePlayer) {
            aIState = f5 < aIAgent.attackRange ? AIState.ATTACK : AIState.CHASE;
        } else if (aIAgent.state == AIState.CHASE || aIAgent.state == AIState.ATTACK) {
            aIState = AIState.PATROL;
        } else if (aIAgent.state == AIState.IDLE && this.random.nextFloat() < 0.01f) {
            aIState = AIState.PATROL;
        }
        if (aIAgent.getHealthPercent() < 0.3f && aIAgent.state != AIState.FLEE && aIAgent.canSeePlayer) {
            aIState = AIState.FLEE;
        }
        if (aIState != aIAgent.state) {
            aIAgent.previousState = aIAgent.state;
            aIAgent.state = aIState;
            aIAgent.stateEnteredTime = System.currentTimeMillis();
        }
    }

    private void behaviorIdle(AIAgent aIAgent) {
        aIAgent.vx = 0.0f;
        aIAgent.vy = 0.0f;
    }

    private void behaviorPatrol(AIAgent aIAgent) {
        float f = aIAgent.patrolX - aIAgent.x;
        if (Math.abs(f) < 10.0f) {
            aIAgent.patrollingLeft = !aIAgent.patrollingLeft;
            aIAgent.patrolX = aIAgent.x + (aIAgent.patrollingLeft ? -aIAgent.patrolDistance : aIAgent.patrolDistance);
        }
        aIAgent.vx = aIAgent.patrollingLeft ? -aIAgent.speed : aIAgent.speed;
        aIAgent.vy = 0.0f;
    }

    private void behaviorChase(AIAgent aIAgent, float f, float f2) {
        float f3 = f - aIAgent.x;
        float f4 = f2 - aIAgent.y;
        float f5 = (float)Math.sqrt(f3 * f3 + f4 * f4);
        if (f5 > 0.0f) {
            aIAgent.vx = f3 / f5 * aIAgent.speed;
            aIAgent.vy = f4 / f5 * aIAgent.speed * 0.5f;
        } else {
            aIAgent.vx = 0.0f;
            aIAgent.vy = 0.0f;
        }
    }

    private void behaviorAttack(AIAgent aIAgent, float f, float f2, long l) {
        aIAgent.vx = 0.0f;
        aIAgent.vy = 0.0f;
        aIAgent.lastAttackTime = System.currentTimeMillis();
    }

    private void behaviorFlee(AIAgent aIAgent, float f, float f2) {
        float f3 = aIAgent.x - f;
        float f4 = aIAgent.y - f2;
        float f5 = (float)Math.sqrt(f3 * f3 + f4 * f4);
        if (f5 > 0.0f) {
            aIAgent.vx = f3 / f5 * aIAgent.speed;
            aIAgent.vy = f4 / f5 * aIAgent.speed * 0.5f;
        } else {
            aIAgent.vx = 0.0f;
            aIAgent.vy = 0.0f;
        }
    }

    private void behaviorDead(AIAgent aIAgent) {
        aIAgent.vx = 0.0f;
        aIAgent.vy = 0.0f;
    }

    public void removeAgent(int n) {
        this.agents.remove(n);
    }

    public List<AIAgent> getActiveAgents() {
        ArrayList<AIAgent> arrayList = new ArrayList<AIAgent>();
        for (AIAgent aIAgent : this.agents.values()) {
            if (!aIAgent.isActive()) continue;
            arrayList.add(aIAgent);
        }
        return arrayList;
    }

    public List<AIAgent> getAllAgents() {
        return new ArrayList<AIAgent>(this.agents.values());
    }

    public void clear() {
        this.agents.clear();
        this.agentIdCounter = 0;
    }

    public int getAgentCount() {
        return this.agents.size();
    }

    public int getActiveAgentCount() {
        return (int)this.agents.values().stream().filter(aIAgent -> aIAgent.isActive()).count();
    }
public class AIAgent {
        public int agentId;
        public AIState state = AIState.IDLE;
        public AIState previousState = AIState.IDLE;
        public float x;
        public float y;
        public float vx;
        public float vy;
        public float speed = 100.0f;
        public float maxHealth = 50.0f;
        public float currentHealth = 50.0f;
        public boolean isAlive = true;
        public float sensorRange = 300.0f;
        public float attackRange = 100.0f;
        public boolean canSeePlayer = false;
        public long stateEnteredTime;
        public long lastAttackTime;
        public long lastDecisionTime;
        public float patrolX = 0.0f;
        public float patrolY = 0.0f;
        public boolean patrollingLeft = true;
        public float patrolDistance = 200.0f;
        public Difficulty difficulty = Difficulty.NORMAL;
        public String type = "basic";

        public AIAgent(int n, float f, float f2) {
            this.agentId = n;
            this.x = f;
            this.y = f2;
            this.patrolX = f;
            this.patrolY = f2;
            this.stateEnteredTime = System.currentTimeMillis();
            this.lastAttackTime = System.currentTimeMillis();
            this.lastDecisionTime = System.currentTimeMillis();
        }

        public boolean isActive() {
            return this.isAlive && this.currentHealth > 0.0f && this.state != AIState.DEAD;
        }

        public void takeDamage(float f) {
            this.currentHealth -= f;
            if (this.currentHealth <= 0.0f) {
                this.currentHealth = 0.0f;
                this.isAlive = false;
                this.state = AIState.DEAD;
            }
        }

        public long getTimeInState() {
            return System.currentTimeMillis() - this.stateEnteredTime;
        }

        public float getHealthPercent() {
            return this.currentHealth / this.maxHealth;
        }
    }
public enum Difficulty {
        EASY(0.5f, 200.0f, 5000L),
        NORMAL(0.75f, 300.0f, 3000L),
        HARD(0.95f, 500.0f, 1000L);

        public final float accuracy;
        public final float detectionRange;
        public final long reactionTimeMs;

        private Difficulty(float f, float f2, long l) {
            this.accuracy = f;
            this.detectionRange = f2;
            this.reactionTimeMs = l;
        }
    }
public enum AIState {
        IDLE(0),
        PATROL(1),
        CHASE(2),
        ATTACK(3),
        FLEE(4),
        DEAD(5);

        public final int value;

        private AIState(int n2) {
            this.value = n2;
        }
    }
}
