/*
 * Decompiled with CFR 0.152.
 */
package ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import physics.CollisionDetector;

public final class AI {
    private AI() {
    }

    public static class Waypoint {
        public float x;
        public float y;
        public String type;
        public float radius;
        public long stayTime;

        public Waypoint(float f, float f2) {
            this.x = f;
            this.y = f2;
            this.type = "patrol";
            this.radius = 10.0f;
            this.stayTime = 0L;
        }

        public Waypoint(float f, float f2, String string, float f3, long l) {
            this.x = f;
            this.y = f2;
            this.type = string;
            this.radius = f3;
            this.stayTime = l;
        }

        public boolean isAtWaypoint(float f, float f2) {
            float f3 = f - this.x;
            float f4 = f2 - this.y;
            return Math.sqrt(f3 * f3 + f4 * f4) <= (double)this.radius;
        }

        public float getDistance(float f, float f2) {
            float f3 = f - this.x;
            float f4 = f2 - this.y;
            return (float)Math.sqrt(f3 * f3 + f4 * f4);
        }
    }

    public static class AIManager {
        private static final float MAX_FORCE = 1.5f;
        private static final float MAX_VELOCITY = 3.0f;
        private static final float MAX_WANDER_DISTANCE = 100.0f;
        private static float wanderAngle = 0.0f;

        public static float[] seek(float f, float f2, float f3, float f4) {
            float f5 = f3 - f;
            float f6 = f4 - f2;
            float f7 = (float)Math.sqrt(f5 * f5 + f6 * f6);
            if (f7 < 0.1f) {
                return new float[]{0.0f, 0.0f};
            }
            float f8 = f5 / f7 * 3.0f;
            float f9 = f6 / f7 * 3.0f;
            return new float[]{Math.min(1.5f, f8), Math.min(1.5f, f9)};
        }

        public static float[] flee(float f, float f2, float f3, float f4) {
            float f5 = f - f3;
            float f6 = f2 - f4;
            float f7 = (float)Math.sqrt(f5 * f5 + f6 * f6);
            if (f7 > 300.0f) {
                return new float[]{0.0f, 0.0f};
            }
            if (f7 < 0.1f) {
                return new float[]{1.5f, 1.5f};
            }
            float f8 = f5 / f7 * 3.0f;
            float f9 = f6 / f7 * 3.0f;
            return new float[]{f8, f9};
        }

        public static float[] pursuit(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
            float f9 = 0.5f;
            float f10 = f5 + f7 * f9;
            float f11 = f6 + f8 * f9;
            return AIManager.seek(f, f2, f10, f11);
        }

        public static float[] evade(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
            float f9 = 0.5f;
            float f10 = f5 + f7 * f9;
            float f11 = f6 + f8 * f9;
            return AIManager.flee(f, f2, f10, f11);
        }

        public static float[] wander(float f, float f2, float f3, float f4) {
            wanderAngle = (float)((double)wanderAngle + (Math.random() - 0.5) * 2.0);
            float f5 = (float)(Math.cos(wanderAngle) * 100.0);
            float f6 = (float)(Math.sin(wanderAngle) * 100.0);
            return AIManager.seek(f, f2, f + f5, f2 + f6);
        }

        public static float[] avoidObstacle(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            float f8;
            float f9 = f - f5;
            float f10 = f2 - f6;
            float f11 = (float)Math.sqrt(f9 * f9 + f10 * f10);
            if (f11 > (f8 = f7 + 100.0f)) {
                return new float[]{0.0f, 0.0f};
            }
            float f12 = f9 / f11 * 3.0f;
            float f13 = f10 / f11 * 3.0f;
            return new float[]{f12, f13};
        }

        public static float[] separation(float f, float f2, List<float[]> list) {
            float f3;
            float f4 = 0.0f;
            float f5 = 0.0f;
            int n = 0;
            float f6 = 50.0f;
            for (float[] fArray : list) {
                float f7;
                float f8 = fArray[0] - f;
                float f9 = (float)Math.sqrt(f8 * f8 + (f7 = fArray[1] - f2) * f7);
                if (!(f9 < f6) || !(f9 > 0.1f)) continue;
                f4 -= f8 / f9;
                f5 -= f7 / f9;
                ++n;
            }
            if (n > 0 && (f3 = (float)Math.sqrt((f4 /= (float)n) * f4 + (f5 /= (float)n) * f5)) > 0.1f) {
                f4 = f4 / f3 * 3.0f;
                f5 = f5 / f3 * 3.0f;
            }
            return new float[]{f4, f5};
        }

        public static float[] alignment(float f, float f2, List<float[]> list) {
            float f3;
            float f4 = f;
            float f5 = f2;
            for (float[] fArray : list) {
                if (fArray.length < 4) continue;
                f4 += fArray[2];
                f5 += fArray[3];
            }
            if ((f3 = (float)Math.sqrt((f4 /= (float)(list.size() + 1)) * f4 + (f5 /= (float)(list.size() + 1)) * f5)) > 0.1f) {
                f4 = f4 / f3 * 3.0f;
                f5 = f5 / f3 * 3.0f;
            }
            return new float[]{f4, f5};
        }

        public static float[] cohesion(float f, float f2, List<float[]> list) {
            if (list.isEmpty()) {
                return new float[]{0.0f, 0.0f};
            }
            float f3 = f;
            float f4 = f2;
            for (float[] fArray : list) {
                f3 += fArray[0];
                f4 += fArray[1];
            }
            return AIManager.seek(f, f2, f3 /= (float)(list.size() + 1), f4 /= (float)(list.size() + 1));
        }
    }

    public static class AIBehaviorSystem {
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

        public static class AIAgent {
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

        public static enum Difficulty {
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

        public static enum AIState {
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

    public static class EnemyAI
    extends AIAgent {
        private AIPathfinder pathfinder;
        private AIPathfinder.Path currentPath;
        private int currentWaypointIndex = 0;
        private float health = 100.0f;
        private float maxHealth = 100.0f;
        private float detectionRange = 200.0f;
        private float chaseSpeed = 2.0f;
        private float patrolSpeed = 0.5f;
        private boolean canSeek = true;
        private float stateTime = 0.0f;
        private float attackCooldown = 0.0f;
        private float attackRange = 40.0f;

        public EnemyAI(String string, AIPathfinder aIPathfinder) {
            super(string);
            this.pathfinder = aIPathfinder;
            this.behavior = new EnemyBehavior(this);
        }

        @Override
        public void initialize() {
            this.setState(AIState.IDLE);
        }

        @Override
        public void shutdown() {
            this.setState(AIState.DEAD);
        }

        @Override
        public void executeBehavior(float f) {
            this.stateTime += f;
            if (this.attackCooldown > 0.0f) {
                this.attackCooldown -= f;
            }
            switch (this.currentState.ordinal()) {
                case 0: {
                    this.executedIdle(f);
                    break;
                }
                case 1: {
                    this.executePatrol(f);
                    break;
                }
                case 2: {
                    this.executeAlert(f);
                    break;
                }
                case 3: {
                    this.executeChase(f);
                    break;
                }
                case 4: {
                    this.executeAttack(f);
                    break;
                }
                case 5: {
                    this.executeFlee(f);
                    break;
                }
            }
        }

        @Override
        public void updateMovement(float f) {
            this.x += this.vx * f;
            this.y += this.vy * f;
        }

        @Override
        protected void onStateEnter(AIState aIState) {
            this.stateTime = 0.0f;
            switch (aIState.ordinal()) {
                case 1: {
                    if (this.currentPath != null) break;
                    this.startPatrol();
                    break;
                }
                case 4: {
                    this.vx = 0.0f;
                    this.vy = 0.0f;
                    break;
                }
                case 0: {
                    this.vx = 0.0f;
                    this.vy = 0.0f;
                    break;
                }
            }
        }

        @Override
        protected void onStateExit(AIState aIState) {
        }

        private void startPatrol() {
            if (this.currentPath != null && this.currentPath.getWaypointCount() > 0) {
                this.currentWaypointIndex = 0;
            } else {
                this.currentPath = new AIPathfinder.Path("default_patrol");
                this.currentPath.loop = true;
                this.currentPath.addWaypoint(this.x, this.y);
                this.currentPath.addWaypoint(this.x + 100.0f, this.y);
                this.currentPath.addWaypoint(this.x, this.y);
            }
        }

        private void executedIdle(float f) {
            this.vx *= 0.9f;
            this.vy *= 0.9f;
            if (this.stateTime > 2.0f) {
                this.setState(AIState.PATROL);
            }
        }

        private void executePatrol(float f) {
            AIPathfinder.Waypoint waypoint;
            if (this.currentPath == null || this.currentPath.getWaypointCount() == 0) {
                this.startPatrol();
            }
            if ((waypoint = this.currentPath.getWaypoint(this.currentWaypointIndex)) == null) {
                this.currentWaypointIndex = 0;
                waypoint = this.currentPath.getWaypoint(this.currentWaypointIndex);
            }
            if (waypoint != null) {
                if (this.pathfinder.isCloseEnough(this.x, this.y, waypoint.x, waypoint.y)) {
                    ++this.currentWaypointIndex;
                    if (this.currentWaypointIndex >= this.currentPath.getWaypointCount()) {
                        if (this.currentPath.loop) {
                            this.currentWaypointIndex = 0;
                        } else {
                            this.setState(AIState.IDLE);
                            return;
                        }
                    }
                    waypoint = this.currentPath.getWaypoint(this.currentWaypointIndex);
                }
                if (waypoint != null) {
                    float[] fArray = this.pathfinder.getDirectionTo(this.x, this.y, waypoint.x, waypoint.y);
                    this.vx = fArray[0] * this.patrolSpeed;
                    this.vy = fArray[1] * this.patrolSpeed;
                }
            }
        }

        private void executeAlert(float f) {
            this.vx *= 0.95f;
            this.vy *= 0.95f;
            if (this.stateTime > 3.0f) {
                this.setState(AIState.PATROL);
            }
        }

        private void executeChase(float f) {
            this.vx *= 0.95f;
            this.vy *= 0.95f;
        }

        private void executeAttack(float f) {
            this.vx *= 0.9f;
            this.vy *= 0.9f;
            if (this.attackCooldown <= 0.0f && this.stateTime < 2.0f) {
                this.performAttack();
            }
        }

        private void executeFlee(float f) {
            this.vx *= 0.95f;
            this.vy *= 0.95f;
        }

        private void performAttack() {
            this.attackCooldown = 1.0f;
        }

        public void takeDamage(float f) {
            this.health -= f;
            if (this.health <= 0.0f) {
                this.setState(AIState.DEAD);
            } else if (this.health < this.maxHealth * 0.3f) {
                this.setState(AIState.FLEE);
            }
        }

        public void heal(float f) {
            this.health = Math.min(this.health + f, this.maxHealth);
        }

        public float getHealth() {
            return this.health;
        }

        public float getMaxHealth() {
            return this.maxHealth;
        }

        public void setHealth(float f) {
            this.health = Math.min(f, this.maxHealth);
        }

        public float getDetectionRange() {
            return this.detectionRange;
        }

        public void setDetectionRange(float f) {
            this.detectionRange = f;
        }

        public float getChaseSpeed() {
            return this.chaseSpeed;
        }

        public void setChaseSpeed(float f) {
            this.chaseSpeed = f;
        }

        public float getPatrolSpeed() {
            return this.patrolSpeed;
        }

        public void setPatrolSpeed(float f) {
            this.patrolSpeed = f;
        }

        public float getAttackRange() {
            return this.attackRange;
        }

        public void setAttackRange(float f) {
            this.attackRange = f;
        }

        public AIPathfinder.Path getCurrentPath() {
            return this.currentPath;
        }

        public void setCurrentPath(AIPathfinder.Path path) {
            this.currentPath = path;
            this.currentWaypointIndex = 0;
        }

        private class EnemyBehavior
        implements AIBehavior {
            private EnemyBehavior(EnemyAI enemyAI) {
            }

            @Override
            public void initialize() {
            }

            @Override
            public AIBehavior.AIAction execute(AIAgent aIAgent, float f) {
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
    }

    public static class AIPathfinder {
        private Map<String, Path> paths = new HashMap<String, Path>();
        private float pathfindingDistance = 50.0f;
        private float stoppingDistance = 10.0f;

        public void registerPath(Path path) {
            this.paths.put(path.name, path);
        }

        public Path getPath(String string) {
            return this.paths.get(string);
        }

        public Path findPath(float f, float f2, float f3, float f4) {
            Path path = new Path("dynamic_path");
            float f5 = f3 - f;
            float f6 = f4 - f2;
            float f7 = (float)Math.sqrt(f5 * f5 + f6 * f6);
            if (f7 > 0.0f) {
                float f8 = f7 / this.pathfindingDistance;
                float f9 = f5 / f8;
                float f10 = f6 / f8;
                int n = 0;
                while ((float)n < f8) {
                    path.addWaypoint(f + f9 * (float)n, f2 + f10 * (float)n);
                    ++n;
                }
                path.addWaypoint(f3, f4);
            }
            return path;
        }

        public Waypoint getNextWaypoint(Path path, int n) {
            if (path == null || path.getWaypointCount() == 0) {
                return null;
            }
            int n2 = n + 1;
            if (n2 >= path.getWaypointCount()) {
                if (path.loop) {
                    n2 = 0;
                } else {
                    return null;
                }
            }
            return path.getWaypoint(n2);
        }

        public float[] getDirectionTo(float f, float f2, float f3, float f4) {
            float f5 = f3 - f;
            float f6 = f4 - f2;
            float f7 = (float)Math.sqrt(f5 * f5 + f6 * f6);
            if (f7 > 0.0f) {
                return new float[]{f5 / f7, f6 / f7};
            }
            return new float[]{0.0f, 0.0f};
        }

        public boolean isCloseEnough(float f, float f2, float f3, float f4) {
            float f5 = f3 - f;
            float f6 = f4 - f2;
            float f7 = (float)Math.sqrt(f5 * f5 + f6 * f6);
            return f7 <= this.stoppingDistance;
        }

        public Collection<Path> getAllPaths() {
            return this.paths.values();
        }

        public void clearPaths() {
            this.paths.clear();
        }

        public float getPathfindingDistance() {
            return this.pathfindingDistance;
        }

        public void setPathfindingDistance(float f) {
            this.pathfindingDistance = f;
        }

        public float getStoppingDistance() {
            return this.stoppingDistance;
        }

        public void setStoppingDistance(float f) {
            this.stoppingDistance = f;
        }

        public static class Path {
            public List<Waypoint> waypoints = new ArrayList<Waypoint>();
            public boolean loop = false;
            public String name;

            public Path(String string) {
                this.name = string;
            }

            public void addWaypoint(Waypoint waypoint) {
                this.waypoints.add(waypoint);
            }

            public void addWaypoint(float f, float f2) {
                this.waypoints.add(new Waypoint(f, f2));
            }

            public Waypoint getWaypoint(int n) {
                if (n >= 0 && n < this.waypoints.size()) {
                    return this.waypoints.get(n);
                }
                return null;
            }

            public int getWaypointCount() {
                return this.waypoints.size();
            }
        }

        public static class Waypoint {
            public float x;
            public float y;
            public String name;
            public boolean isCheckpoint = false;

            public Waypoint(float f, float f2) {
                this.x = f;
                this.y = f2;
            }

            public Waypoint(float f, float f2, String string) {
                this.x = f;
                this.y = f2;
                this.name = string;
            }

            public float distanceTo(float f, float f2) {
                float f3 = this.x - f;
                float f4 = this.y - f2;
                return (float)Math.sqrt(f3 * f3 + f4 * f4);
            }
        }
    }

    public static class AIDecisionMaker {
        private DecisionContext context;

        public void makeDecision(AIAgent aIAgent) {
            this.context = new DecisionContext(aIAgent);
            AIState aIState = this.evaluateState(aIAgent);
            if (aIState != aIAgent.getState()) {
                aIAgent.setState(aIState);
            }
        }

        private AIState evaluateState(AIAgent aIAgent) {
            AIState aIState = aIAgent.getState();
            if (aIState == AIState.DEAD) {
                return AIState.DEAD;
            }
            if (aIState == AIState.STUNNED) {
                return AIState.STUNNED;
            }
            if (this.shouldEnterCombat(aIAgent)) {
                return AIState.ATTACK;
            }
            if (this.shouldChase(aIAgent)) {
                return AIState.CHASE;
            }
            if (this.shouldAlert(aIAgent)) {
                return AIState.ALERT;
            }
            if (this.shouldFlee(aIAgent)) {
                return AIState.FLEE;
            }
            if (this.shouldPatrol(aIAgent)) {
                return AIState.PATROL;
            }
            return AIState.IDLE;
        }

        protected boolean shouldEnterCombat(AIAgent aIAgent) {
            float f = this.getDistanceToTarget(aIAgent);
            return f < 50.0f && this.context.canSeePlayer;
        }

        protected boolean shouldChase(AIAgent aIAgent) {
            float f = this.getDistanceToTarget(aIAgent);
            return f < 300.0f && this.context.canSeePlayer;
        }

        protected boolean shouldAlert(AIAgent aIAgent) {
            float f = this.getDistanceToTarget(aIAgent);
            return f < 150.0f && !this.context.canSeePlayer && this.context.isAlerted;
        }

        protected boolean shouldFlee(AIAgent aIAgent) {
            return this.context.getHealthPercent() < 0.2f && this.context.isInCombat;
        }

        protected boolean shouldPatrol(AIAgent aIAgent) {
            return aIAgent.getState() == AIState.IDLE || aIAgent.getState() == AIState.PATROL;
        }

        protected float getDistanceToTarget(AIAgent aIAgent) {
            if (this.context.playerPos == null) {
                return Float.MAX_VALUE;
            }
            float[] fArray = aIAgent.getPosition();
            float f = fArray[0] - this.context.playerPos[0];
            float f2 = fArray[1] - this.context.playerPos[1];
            return (float)Math.sqrt(f * f + f2 * f2);
        }

        protected boolean hasLineOfSight(AIAgent aIAgent, float[] fArray) {
            return this.getDistanceToTarget(aIAgent) < 200.0f;
        }

        public void updateContext(AIAgent aIAgent, float[] fArray, float f, float f2) {
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

        public static class DecisionContext {
            public AIAgent agent;
            public float[] agentPos;
            public float[] playerPos;
            public float distanceToPlayer = Float.MAX_VALUE;
            public boolean canSeePlayer = false;
            public boolean isAlerted = false;
            public boolean isInCombat = false;
            public float health = 100.0f;
            public float maxHealth = 100.0f;

            public DecisionContext(AIAgent aIAgent) {
                this.agent = aIAgent;
                this.agentPos = aIAgent.getPosition();
            }

            public float getHealthPercent() {
                return this.health / this.maxHealth;
            }
        }
    }

    public static abstract class AIAgent {
        protected String agentId;
        protected AIState currentState;
        protected AIBehavior behavior;
        protected boolean active = true;
        protected boolean paused = false;
        protected float x;
        protected float y;
        protected float vx;
        protected float vy;

        public AIAgent(String string) {
            this.agentId = string;
            this.currentState = AIState.IDLE;
        }

        public abstract void initialize();

        public abstract void shutdown();

        public abstract void executeBehavior(float var1);

        public abstract void updateMovement(float var1);

        protected abstract void onStateEnter(AIState var1);

        protected abstract void onStateExit(AIState var1);

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

        public void setState(AIState aIState) {
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

        public AIState getState() {
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

        public AIBehavior getBehavior() {
            return this.behavior;
        }

        public void setBehavior(AIBehavior aIBehavior) {
            this.behavior = aIBehavior;
        }
    }

    public static interface AIBehavior {
        public void initialize();

        public AIAction execute(AIAgent var1, float var2);

        public boolean isComplete();

        public void reset();

        public void stop();

        public String getName();

        public static abstract class SimpleAction
        implements AIAction {
            protected String name;
            protected boolean complete = false;

            public SimpleAction(String string) {
                this.name = string;
            }

            @Override
            public String getName() {
                return this.name;
            }

            @Override
            public boolean isComplete() {
                return this.complete;
            }
        }

        public static abstract class SimpleBehavior
        implements AIBehavior {
            protected String name;
            protected boolean complete = false;
            protected float duration = 0.0f;
            protected float elapsedTime = 0.0f;

            public SimpleBehavior(String string) {
                this.name = string;
            }

            public SimpleBehavior(String string, float f) {
                this.name = string;
                this.duration = f;
            }

            @Override
            public String getName() {
                return this.name;
            }

            @Override
            public boolean isComplete() {
                if (this.duration > 0.0f) {
                    return this.elapsedTime >= this.duration;
                }
                return this.complete;
            }

            @Override
            public void reset() {
                this.complete = false;
                this.elapsedTime = 0.0f;
            }

            @Override
            public void stop() {
                this.complete = true;
            }

            @Override
            public void initialize() {
            }
        }

        public static interface AIAction {
            public void execute(AIAgent var1);

            public boolean isComplete();

            public String getName();
        }
    }

    public static enum AIState {
        IDLE("idle"),
        PATROL("patrol"),
        ALERT("alert"),
        CHASE("chase"),
        ATTACK("attack"),
        FLEE("flee"),
        STUNNED("stunned"),
        DEAD("dead"),
        CUSTOM("custom");

        private final String displayName;

        private AIState(String string2) {
            this.displayName = string2;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public boolean isCombat() {
            return this == CHASE || this == ATTACK;
        }

        public boolean isMovement() {
            return this == PATROL || this == CHASE || this == FLEE;
        }

        public boolean isActive() {
            return this != IDLE && this != DEAD && this != STUNNED;
        }
    }

    public static class AISystem {
        private List<AIAgent> agents = new ArrayList<AIAgent>();
        private AIPathfinder pathfinder = new AIPathfinder();
        private AIDecisionMaker decisionMaker = new AIDecisionMaker();
        private boolean paused = false;
        private float updateInterval = 0.1f;
        private float timeSinceLastUpdate = 0.0f;

        public void addAgent(AIAgent aIAgent) {
            if (!this.agents.contains(aIAgent)) {
                this.agents.add(aIAgent);
                aIAgent.initialize();
            }
        }

        public void removeAgent(AIAgent aIAgent) {
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
            for (AIAgent aIAgent : this.agents) {
                aIAgent.updateMovement(f);
            }
        }

        private void updateAIInternal(float f) {
            for (AIAgent aIAgent : this.agents) {
                if (!aIAgent.isActive()) continue;
                this.decisionMaker.makeDecision(aIAgent);
                aIAgent.executeBehavior(f);
            }
        }

        public AIPathfinder getPathfinder() {
            return this.pathfinder;
        }

        public AIDecisionMaker getDecisionMaker() {
            return this.decisionMaker;
        }

        public void pause() {
            this.paused = true;
            for (AIAgent aIAgent : this.agents) {
                aIAgent.pause();
            }
        }

        public void resume() {
            this.paused = false;
            for (AIAgent aIAgent : this.agents) {
                aIAgent.resume();
            }
        }

        public List<AIAgent> getAgents() {
            return new ArrayList<AIAgent>(this.agents);
        }

        public <T extends AIAgent> List<T> getAgentsOfType(Class<T> clazz) {
            ArrayList<AIAgent> arrayList = new ArrayList<AIAgent>();
            for (AIAgent aIAgent : this.agents) {
                if (!clazz.isInstance(aIAgent)) continue;
                arrayList.add((AIAgent)clazz.cast(aIAgent));
            }
            return arrayList;
        }

        public void clear() {
            for (AIAgent aIAgent : this.agents) {
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
}
