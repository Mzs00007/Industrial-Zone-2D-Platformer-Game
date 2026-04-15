/*
 * Decompiled with CFR 0.152.
 */
package ai;

import ai.AI;
public class EnemyAI
extends AI.AIAgent {
    private AI.AIPathfinder pathfinder;
    private AI.AIPathfinder.Path currentPath;
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

    public EnemyAI(String string, AI.AIPathfinder aIPathfinder) {
        super(string);
        this.pathfinder = aIPathfinder;
        this.behavior = new EnemyBehavior(this);
    }

    @Override
    public void initialize() {
        this.setState(AI.AIState.IDLE);
    }

    @Override
    public void shutdown() {
        this.setState(AI.AIState.DEAD);
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
    protected void onStateEnter(AI.AIState aIState) {
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
    protected void onStateExit(AI.AIState aIState) {
    }

    private void startPatrol() {
        if (this.currentPath != null && this.currentPath.getWaypointCount() > 0) {
            this.currentWaypointIndex = 0;
        } else {
            this.currentPath = new AI.AIPathfinder.Path("default_patrol");
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
            this.setState(AI.AIState.PATROL);
        }
    }

    private void executePatrol(float f) {
        AI.AIPathfinder.Waypoint waypoint;
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
                        this.setState(AI.AIState.IDLE);
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
            this.setState(AI.AIState.PATROL);
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
            this.setState(AI.AIState.DEAD);
        } else if (this.health < this.maxHealth * 0.3f) {
            this.setState(AI.AIState.FLEE);
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

    public AI.AIPathfinder.Path getCurrentPath() {
        return this.currentPath;
    }

    public void setCurrentPath(AI.AIPathfinder.Path path) {
        this.currentPath = path;
        this.currentWaypointIndex = 0;
    }
class EnemyBehavior
    implements AI.AIBehavior {
        private EnemyBehavior(EnemyAI enemyAI) {
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
}
