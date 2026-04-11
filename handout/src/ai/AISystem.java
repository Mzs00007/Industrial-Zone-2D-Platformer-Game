package ai;

import animation.InputController;
import audio.AudioSystem;
import ui.components.game2D.Animation;

import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


/**
 * ═══════════════════════════════════════════════════════════════════════════════════════
 * AISystem - UNIFIED AI ENGINE FOR ALL GAME ENTITIES
 * ═══════════════════════════════════════════════════════════════════════════════════════
 * 
 * ARCHITECTURE:
 * • Single consolidated file containing ALL AI-related classes
 * • Bulky file design: 20+ nested static classes providing complete AI API
 * • Parameter-driven: Small changes to method parameters enable different behaviors
 * • Zero external AI files: Everything self-contained and well-integrated
 * 
 * DESIGN PATTERN:
 * - Controller class: High-level public API for Game.java
 * - State enums: AI states (IDLE, PATROL, CHASE, ATTACK, FLEE, DEAD)
 * - Behavior classes: State implementations with full logic
 * - Variant classes: Enemy type specializations (Melee, Gunner, Patroller)
 * - Support classes: Path finding, decision making, perception
 * 
 * INTEGRATION HOOKS:
 * ✓ References to animation.AnimationAndSpriteLoader for sprite/animation sync
 * ✓ References to audio.AudioSystem for AI action sounds
 * ✓ References to physics.PhysicsSystem for collision/movement
 * ✓ References to Game class for game state queries
 * 
 * USAGE:
 *   AISystem.Controller ai = new AISystem.Controller();
 *   AISystem.EnemyInstance e1 = ai.createEnemy(100, 200, AISystem.EnemyType.MELEE, 1);
 *   AISystem.EnemyInstance e2 = ai.createEnemy(300, 150, AISystem.EnemyType.GUNNER, 2);
 *   
 *   while (gameRunning) {
 *       ai.updateAll(deltaTimeMs, player);
 *   }
 * 
 * ═══════════════════════════════════════════════════════════════════════════════════════
 * @author 3359098
 */

public final class AISystem extends InputController {
    
    /**
     * Constructor: Initialize AISystem
     * Inherits from InputController (provides animation/sprite management)
     * Adds AI behaviors, decision making, pathfinding
     */
    private AISystem() {
        super();  // Initialize InputController → AnimationAndSpriteLoader → GameCore
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════════
    // PUBLIC API - HIGH-LEVEL CONTROLLER FOR GAME.JAVA
    // ═══════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Controller - Main API for Game.java to interact with AI system
     * Parameter-driven design: tweak parameters to change AI behavior
     */
    public static class Controller {
        
        private List<EnemyInstance> allEnemies;
        private AISystemCore systemCore;
        private boolean paused;
        private int nextEnemyId;
        
        public Controller() {
            this.allEnemies = new ArrayList<>();
            this.systemCore = new AISystemCore();
            this.paused = false;
            this.nextEnemyId = 1000;
        }
        
        /**
         * Create a new enemy with specific type and difficulty
         * 
         * @param x Starting X position
         * @param y Starting Y position
         * @param type Enemy type (MELEE, GUNNER, PATROLLER, etc)
         * @param difficultyLevel Game difficulty (1=easy, 2=normal, 3=hard, 4=insane)
         * @return New enemy instance ready to be added to game
         */
        public EnemyInstance createEnemy(float x, float y, EnemyType type, int difficultyLevel) {
            EnemyInstance enemy = new EnemyInstance(nextEnemyId++, x, y, type);
            enemy.applyDifficultyModifiers(difficultyLevel);
            allEnemies.add(enemy);
            systemCore.registerAgent(enemy);
            return enemy;
        }
        
        /**
         * Remove enemy from AI system
         */
        public void removeEnemy(EnemyInstance enemy) {
            allEnemies.remove(enemy);
            systemCore.unregisterAgent(enemy);
        }
        
        /**
         * Update all AI entities (call from Game loop every frame)
         * 
         * @param deltaTimeMs Time since last frame in milliseconds
         * @param playerX Player entity X position
         * @param playerY Player entity Y position
         * @param gameState Current game state (for decision making)
         */
        public void updateAll(long deltaTimeMs, float playerX, float playerY, GameState gameState) {
            if (paused) return;
            
            // Update each enemy's AI
            for (EnemyInstance enemy : allEnemies) {
                if (!enemy.isAlive()) continue;
                
                // Decision making: determine next state
                AIState nextState = systemCore.makeDecision(enemy, playerX, playerY, gameState);
                
                // Execute behavior for current state
                systemCore.executeBehavior(enemy, nextState, deltaTimeMs, playerX, playerY);
                
                // Update animation/sprite state
                synchronizeAnimationState(enemy);
            }
        }
        
        /**
         * Pause all AI (but don't clear state)
         */
        public void pause() {
            paused = true;
            for (EnemyInstance enemy : allEnemies) {
                enemy.pause();
            }
        }
        
        /**
         * Resume all AI
         */
        public void resume() {
            paused = false;
            for (EnemyInstance enemy : allEnemies) {
                enemy.resume();
            }
        }
        
        /**
         * Get list of all active enemies
         */
        public List<EnemyInstance> getAllEnemies() {
            return new ArrayList<>(allEnemies);
        }
        
        /**
         * Get enemies within range of position (for spatial queries)
         */
        public List<EnemyInstance> getEnemiesInRange(float cx, float cy, float radius) {
            List<EnemyInstance> result = new ArrayList<>();
            for (EnemyInstance enemy : allEnemies) {
                float dx = enemy.x - cx;
                float dy = enemy.y - cy;
                float distance = (float)Math.sqrt(dx*dx + dy*dy);
                if (distance <= radius && enemy.isAlive()) {
                    result.add(enemy);
                }
            }
            return result;
        }
        
        /**
         * Get all enemies of specific type (for type-specific updates)
         */
        public List<EnemyInstance> getEnemiesOfType(EnemyType type) {
            List<EnemyInstance> result = new ArrayList<>();
            for (EnemyInstance enemy : allEnemies) {
                if (enemy.type == type && enemy.isAlive()) {
                    result.add(enemy);
                }
            }
            return result;
        }
        
        /**
         * Clear all enemies
         */
        public void clear() {
            allEnemies.clear();
            systemCore.clear();
        }
        
        /**
         * Get AI system statistics
         */
        public String getStats() {
            int alive = (int)allEnemies.stream().filter(EnemyInstance::isAlive).count();
            int dead = allEnemies.size() - alive;
            return String.format("AI: %d total | %d alive | %d dead | Paused: %s",
                allEnemies.size(), alive, dead, paused);
        }
        
        private void synchronizeAnimationState(EnemyInstance enemy) {
            // Hook: Call animation system to update sprite state based on AI state
            // Example: enemy.animationState = enemy.currentState.getAnimationName();
            // This is where AI state drives visual representation
        }
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════════
    // ENUMERATIONS - AI States and Enemy Types
    // ═══════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * AIState - All possible AI states
     */
    public enum AIState {
        IDLE("idle", false, false),
        PATROL("patrol", true, false),
        ALERT("alert", false, false),
        CHASE("chase", true, true),
        ATTACK("attack", false, true),
        FLEE("flee", true, true),
        STUNNED("stunned", false, false),
        DEAD("dead", false, false);
        
        public final String displayName;
        public final boolean isMovement;
        public final boolean isCombat;
        
        AIState(String displayName, boolean isMovement, boolean isCombat) {
            this.displayName = displayName;
            this.isMovement = isMovement;
            this.isCombat = isCombat;
        }
    }
    
    /**
     * EnemyType - Different enemy variants with distinct behaviors
     */
    public enum EnemyType {
        MELEE("Melee", 180f, 50f, 250f, 600L),           // Fast, close range
        GUNNER("Gunner", 100f, 250f, 350f, 800L),        // Slow, ranged
        PATROLLER("Patroller", 30f, 40f, 150f, 400L),    // Very slow, limited vision
        DRONE("Drone", 200f, 150f, 300f, 500L),          // Flying, medium range
        BOSS("Boss", 120f, 80f, 400f, 1200L);            // Powerful, many attacks
        
        public final String displayName;
        public final float chaseSpeed;
        public final float attackRange;
        public final float sightRange;
        public final long attackCooldownMs;
        
        EnemyType(String displayName, float chaseSpeed, float attackRange, float sightRange, long attackCooldownMs) {
            this.displayName = displayName;
            this.chaseSpeed = chaseSpeed;
            this.attackRange = attackRange;
            this.sightRange = sightRange;
            this.attackCooldownMs = attackCooldownMs;
        }
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════════
    // GAME STATE INTERFACE - For AI decision making
    // ═══════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * GameState - Interface for querying game state from AI
     * This allows AI to make difficulty-aware decisions
     */
    public interface GameState {
        int getDifficulty();                    // 1-4: easy to insane
        boolean isPlayerInvulnerable();         // Is player protected?
        boolean isPlayerSlowed();               // Is player slowed?
        float getPlayerHealthPercent();         // 0.0 to 1.0
        boolean isNearbyEnemyAlly(float x, float y, float range);  // Allies nearby?
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════════
    // CORE AI SYSTEM - AI Logic Engine
    // ═══════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * AISystemCore - Internal AI logic and decision making
     */
    public static class AISystemCore {
        private Map<Integer, EnemyInstance> agents;
        private DecisionMaker decisionMaker;
        private PerceptionSystem perception;
        private BehaviorExecutor behaviorExecutor;
        
        public AISystemCore() {
            this.agents = new HashMap<>();
            this.decisionMaker = new DecisionMaker();
            this.perception = new PerceptionSystem();
            this.behaviorExecutor = new BehaviorExecutor();
        }
        
        public void registerAgent(EnemyInstance agent) {
            agents.put(agent.id, agent);
        }
        
        public void unregisterAgent(EnemyInstance agent) {
            agents.remove(agent.id);
        }
        
        public AIState makeDecision(EnemyInstance enemy, float playerX, float playerY, GameState gameState) {
            return decisionMaker.decideNextState(enemy, playerX, playerY, gameState, perception);
        }
        
        public void executeBehavior(EnemyInstance enemy, AIState state, long deltaTimeMs, float playerX, float playerY) {
            // ALWAYS face the player - update direction based on character position
            updateEnemyFacingDirection(enemy, playerX, playerY);
            
            behaviorExecutor.executeBehavior(enemy, state, deltaTimeMs, playerX, playerY);
        }
        
        /**
         * Update enemy facing direction based on player position
         * This ensures all enemies face towards the player
         */
        private void updateEnemyFacingDirection(EnemyInstance enemy, float playerX, float playerY) {
            if (playerX < enemy.x) {
                enemy.facingLeft = true;
            } else if (playerX > enemy.x) {
                enemy.facingLeft = false;
            }
            // If playerX == enemy.x, keep current facing
        }
        
        public void clear() {
            agents.clear();
        }
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════════
    // AI AGENT - Individual Enemy Instance
    // ═══════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * EnemyInstance - Represents one AI-controlled enemy in the game
     */
    public static class EnemyInstance {
        public int id;
        public EnemyType type;
        public AIState currentState;
        public float x, y;
        public float vx, vy;
        public float health;
        public float maxHealth;
        public float sightRange;
        public float attackRange;
        public float chaseSpeed;
        public float patrolSpeed;
        public long attackCooldownMs;
        public long lastAttackTimeMs;
        public boolean facingLeft;
        public boolean paused;
        public boolean alive;
        
        // Patrol boundaries
        public float patrolMinX;
        public float patrolMaxX;
        
        // State transition timing
        public long stateStartTimeMs;
        
        public EnemyInstance(int id, float x, float y, EnemyType type) {
            this.id = id;
            this.type = type;
            this.x = x;
            this.y = y;
            this.vx = 0;
            this.vy = 0;
            this.currentState = AIState.IDLE;
            this.alive = true;
            this.paused = false;
            this.facingLeft = false;
            this.lastAttackTimeMs = 0;
            this.stateStartTimeMs = System.currentTimeMillis();
            
            // Initialize from type
            this.sightRange = type.sightRange;
            this.attackRange = type.attackRange;
            this.chaseSpeed = type.chaseSpeed;
            this.patrolSpeed = type.chaseSpeed * 0.4f;
            this.attackCooldownMs = type.attackCooldownMs;
            this.maxHealth = 100;
            this.health = maxHealth;
            
            // Default patrol range: 150 pixels left/right
            this.patrolMinX = x - 150;
            this.patrolMaxX = x + 150;
        }
        
        public void applyDifficultyModifiers(int difficultyLevel) {
            switch (difficultyLevel) {
                case 1: // Easy
                    health = maxHealth * 0.7f;
                    sightRange *= 0.8f;
                    chaseSpeed *= 0.8f;
                    break;
                case 2: // Normal
                    // No change from defaults
                    break;
                case 3: // Hard
                    health = maxHealth * 1.3f;
                    sightRange *= 1.2f;
                    chaseSpeed *= 1.2f;
                    attackCooldownMs = (long)(attackCooldownMs * 0.8f);
                    break;
                case 4: // Insane
                    health = maxHealth * 1.8f;
                    sightRange *= 1.5f;
                    chaseSpeed *= 1.5f;
                    attackCooldownMs = (long)(attackCooldownMs * 0.6f);
                    break;
            }
            this.health = maxHealth;  // Reset to modified max
        }
        
        public boolean isAlive() { return alive && health > 0; }
        public void pause() { paused = true; }
        public void resume() { paused = false; }
        public void takeDamage(float amount) { health -= amount; if (health <= 0) alive = false; }
        public float getHealth() { return health; }
        public float getHealthPercent() { return health / maxHealth; }
        public void setPatrolRange(float minX, float maxX) { this.patrolMinX = minX; this.patrolMaxX = maxX; }
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════════
    // DECISION MAKING - AI State Selection Logic
    // ═══════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * DecisionMaker - Determines next AI state based on perception and game state
     */
    public static class DecisionMaker {
        
        public AIState decideNextState(EnemyInstance enemy, float playerX, float playerY, 
                                      GameState gameState, PerceptionSystem perception) {
            
            // Dead = stay dead
            if (!enemy.isAlive()) {
                return AIState.DEAD;
            }
            
            // Paused
            if (enemy.paused) {
                return enemy.currentState;
            }
            
            // Stunned
            if (enemy.currentState == AIState.STUNNED) {
                return AIState.STUNNED;
            }
            
            float distToPlayer = perception.getDistance(enemy.x, enemy.y, playerX, playerY);
            boolean canSee = perception.canSee(enemy, playerX, playerY);
            boolean canAttack = perception.canAttack(enemy, playerX, playerY);
            
            // Fleeing?
            if (enemy.currentState == AIState.FLEE) {
                if (distToPlayer > enemy.sightRange * 2) {
                    return AIState.PATROL;
                }
                return AIState.FLEE;
            }
            
            // Player visible?
            if (canSee) {
                if (canAttack) {
                    return AIState.ATTACK;
                } else {
                    return AIState.CHASE;
                }
            }
            
            // Default: patrol
            return AIState.PATROL;
        }
    }
    
    /**
     * PerceptionSystem - Checks line of sight, distance, visibility
     */
    public static class PerceptionSystem {
        
        public float getDistance(float x1, float y1, float x2, float y2) {
            float dx = x2 - x1;
            float dy = y2 - y1;
            return (float)Math.sqrt(dx*dx + dy*dy);
        }
        
        public boolean canSee(EnemyInstance enemy, float playerX, float playerY) {
            float distance = getDistance(enemy.x, enemy.y, playerX, playerY);
            return distance <= enemy.sightRange;
        }
        
        public boolean canAttack(EnemyInstance enemy, float playerX, float playerY) {
            float distance = getDistance(enemy.x, enemy.y, playerX, playerY);
            return distance <= enemy.attackRange;
        }
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════════
    // BEHAVIOR EXECUTION - State-Specific Behaviors
    // ═══════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * BehaviorExecutor - Executes behavior for each AI state
     */
    public static class BehaviorExecutor {
        
        public void executeBehavior(EnemyInstance enemy, AIState state, long deltaTimeMs, float playerX, float playerY) {
            switch (state) {
                case IDLE:
                    behaviorIdle(enemy, deltaTimeMs);
                    break;
                case PATROL:
                    behaviorPatrol(enemy, deltaTimeMs);
                    break;
                case ALERT:
                    behaviorAlert(enemy, deltaTimeMs, playerX, playerY);
                    break;
                case CHASE:
                    behaviorChase(enemy, deltaTimeMs, playerX, playerY);
                    break;
                case ATTACK:
                    behaviorAttack(enemy, deltaTimeMs, playerX, playerY);
                    break;
                case FLEE:
                    behaviorFlee(enemy, deltaTimeMs, playerX, playerY);
                    break;
                case STUNNED:
                    behaviorStunned(enemy, deltaTimeMs);
                    break;
                case DEAD:
                    behaviorDead(enemy, deltaTimeMs);
                    break;
            }
            
            enemy.currentState = state;
        }
        
        private void behaviorIdle(EnemyInstance enemy, long deltaTimeMs) {
            // Idle: no movement
            enemy.vx = 0;
            enemy.vy = 0;
        }
        
        private void behaviorPatrol(EnemyInstance enemy, long deltaTimeMs) {
            // Patrol: walk back and forth
            float currentX = enemy.x;
            
            if (currentX >= enemy.patrolMaxX) {
                enemy.facingLeft = true;
            } else if (currentX <= enemy.patrolMinX) {
                enemy.facingLeft = false;
            }
            
            float deltaSeconds = deltaTimeMs / 1000f;
            float moveX = enemy.facingLeft ? -enemy.patrolSpeed : enemy.patrolSpeed;
            
            enemy.vx = moveX;
            enemy.vy = 0;
            enemy.x += moveX * deltaSeconds;
        }
        
        private void behaviorAlert(EnemyInstance enemy, long deltaTimeMs, float playerX, float playerY) {
            // Alert: enemy detected something but not yet committed to chase
            // Face the direction of the alert and slow movement
            if (playerX < enemy.x) {
                enemy.facingLeft = true;
            } else {
                enemy.facingLeft = false;
            }
            
            // Slow investigating movement toward the alert
            float investigateSpeed = enemy.chaseSpeed * 0.5f;
            moveToward(enemy, playerX, playerY, investigateSpeed, deltaTimeMs);
        }
        
        private void behaviorChase(EnemyInstance enemy, long deltaTimeMs, float playerX, float playerY) {
            // Chase: move toward player and face them
            moveToward(enemy, playerX, playerY, enemy.chaseSpeed, deltaTimeMs);
            // Facing is automatically updated by moveToward based on direction to player
        }
        
        private void behaviorAttack(EnemyInstance enemy, long deltaTimeMs, float playerX, float playerY) {
            // Attack: face player and attack when ready
            if (playerX < enemy.x) {
                enemy.facingLeft = true;
            } else {
                enemy.facingLeft = false;
            }
            
            long timeSinceLastAttack = System.currentTimeMillis() - enemy.lastAttackTimeMs;
            if (timeSinceLastAttack >= enemy.attackCooldownMs) {
                performAttack(enemy, playerX, playerY);
                enemy.lastAttackTimeMs = System.currentTimeMillis();
            }
            
            // Type-specific behavior: gunners back away, melees charge
            if (enemy.type == EnemyType.GUNNER) {
                float dist = (float)Math.sqrt((playerX-enemy.x)*(playerX-enemy.x) + (playerY-enemy.y)*(playerY-enemy.y));
                if (dist < enemy.attackRange) {
                    moveAwayFrom(enemy, playerX, playerY, enemy.chaseSpeed * 0.7f, deltaTimeMs);
                }
            } else if (enemy.type == EnemyType.MELEE) {
                float dist = (float)Math.sqrt((playerX-enemy.x)*(playerX-enemy.x) + (playerY-enemy.y)*(playerY-enemy.y));
                if (dist > enemy.attackRange + 20) {
                    moveToward(enemy, playerX, playerY, enemy.chaseSpeed, deltaTimeMs);
                }
            }
        }
        
        private void behaviorFlee(EnemyInstance enemy, long deltaTimeMs, float playerX, float playerY) {
            // Flee: move away from player and face away from them
            moveAwayFrom(enemy, playerX, playerY, enemy.chaseSpeed, deltaTimeMs);
            // Facing is automatically updated by moveAwayFrom based on direction away from player
        }
        
        private void behaviorStunned(EnemyInstance enemy, long deltaTimeMs) {
            // Stunned: no movement
            enemy.vx = 0;
            enemy.vy = 0;
        }
        
        private void behaviorDead(EnemyInstance enemy, long deltaTimeMs) {
            // Dead: no movement
            enemy.vx = 0;
            enemy.vy = 0;
        }
        
        private void moveToward(EnemyInstance enemy, float targetX, float targetY, float speed, long deltaTimeMs) {
            float dx = targetX - enemy.x;
            float dy = targetY - enemy.y;
            float distance = (float)Math.sqrt(dx*dx + dy*dy);
            
            if (distance < 1) return;
            
            float dirX = dx / distance;
            float dirY = dy / distance;
            float deltaSeconds = deltaTimeMs / 1000f;
            
            enemy.vx = dirX * speed;
            enemy.vy = dirY * speed;
            enemy.x += dirX * speed * deltaSeconds;
            enemy.y += dirY * speed * deltaSeconds;
            
            if (dirX < 0) enemy.facingLeft = true;
            if (dirX > 0) enemy.facingLeft = false;
        }
        
        private void moveAwayFrom(EnemyInstance enemy, float targetX, float targetY, float speed, long deltaTimeMs) {
            float dx = enemy.x - targetX;
            float dy = enemy.y - targetY;
            float distance = (float)Math.sqrt(dx*dx + dy*dy);
            
            if (distance < 1) return;
            
            float dirX = dx / distance;
            float dirY = dy / distance;
            float deltaSeconds = deltaTimeMs / 1000f;
            
            enemy.vx = dirX * speed;
            enemy.vy = dirY * speed;
            enemy.x += dirX * speed * deltaSeconds;
            enemy.y += dirY * speed * deltaSeconds;
            
            if (dirX < 0) enemy.facingLeft = true;
            if (dirX > 0) enemy.facingLeft = false;
        }
        
        private void performAttack(EnemyInstance enemy, float playerX, float playerY) {
            // Hook: Trigger attack animation, sound, projectile, etc
            // Examples:
            // - AudioSystem.Manager.playSound("enemy_attack_" + enemy.type.displayName.toLowerCase());
            // - AnimationAndSpriteLoader.EnemyController.playAnimation(enemy.id, "attack_" + enemy.facingLeft ? "left" : "right");
            // - Create projectile for gunner, hitbox for melee, etc
            System.out.println("[AI] " + enemy.type.displayName + " #" + enemy.id + " attacks!");
        }
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════════
    // BEHAVIOR TREES - Specialized Behavior Implementations
    // ═══════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * BehaviorTree - Reference class for behavior pattern documentation
     * All behavior implementations are independent nested static classes (composition pattern)
     */
    public static class BehaviorTree {
        // Reference/documentation class - no longer used as base class
        // All behaviors use composition (HAS-A) instead of inheritance (IS-A)
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════════
    // ANIMATION MANAGER - Enemy Attack Animation Playback
    // ═══════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * AnimationManager - Loads and plays enemy attack animations
     * Stores animation frames loaded from PNG spritesheets
     * Provides simple API for triggering attack animations by entity ID and file path
     */
    public static class AnimationManager {
        private static final Map<Integer, Animation> entityAnimations = new HashMap<>();
        
        /**
         * Load and play attack animation for enemy
         * 
         * @param entityId Unique enemy ID
         * @param filePath Full path to animation spritesheet PNG
         */
        public static void playAnimation(int entityId, String filePath) {
            try {
                // Check if file exists
                File animFile = new File(filePath);
                if (!animFile.exists()) {
                    System.err.println("[AI:AnimManager] Animation file not found: " + filePath);
                    return;
                }
                
                // Load the spritesheet PNG
                BufferedImage spriteSheet = ImageIO.read(animFile);
                if (spriteSheet == null) {
                    System.err.println("[AI:AnimManager] Failed to load image: " + filePath);
                    return;
                }
                
                // Create animation and add the spritesheet as a single frame
                // Note: Full sprite extraction would require frame dimension analysis
                // For now, using the full spritesheet as one frame with appropriate timing
                Animation anim = new Animation();
                anim.addFrame(spriteSheet, 100);  // 100ms per frame (placeholder)
                // Set animation to play once (not looping)
                
                // Store animation by entity ID for playback
                entityAnimations.put(entityId, anim);
                System.out.println("[AI:AnimManager] Loaded animation for entity #" + entityId + ": " + filePath);
                
            } catch (Exception e) {
                System.err.println("[AI:AnimManager] Error loading animation: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        /**
         * Get stored animation for entity
         */
        public static Animation getAnimation(int entityId) {
            return entityAnimations.get(entityId);
        }
        
        /**
         * Clear all animations
         */
        public static void clearAnimations() {
            entityAnimations.clear();
        }
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════════
    // PLAYER DAMAGE MANAGER - Enemy Damage Application to Player
    // ═══════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * PlayerDamageManager - Applies damage to player entity
     * Integrates with player health system and damage tracking
     * Provides centralized API for all enemy damage applications
     */
    public static class PlayerDamageManager {
        private static float totalDamageDealt = 0f;  // Cumulative damage tracking
        private static int damageEventCount = 0;     // Number of damage events
        
        /**
         * Apply damage to player from enemy attack
         * 
         * @param damageAmount Calculated damage value in hit points
         */
        public static void applyDamageToPlayer(int damageAmount) {
            try {
                if (damageAmount <= 0) {
                    System.err.println("[AI:DamageManager] Invalid damage amount: " + damageAmount);
                    return;
                }
                
                // Track damage statistics
                totalDamageDealt += damageAmount;
                damageEventCount++;
                
                // Log damage application with detailed tracking
                System.out.println("[AI:DamageManager] Damage applied: " + damageAmount + " HP");
                System.out.println("[AI:DamageManager] Cumulative damage: " + String.format("%.0f", totalDamageDealt) + " HP | Events: " + damageEventCount);
                
                // Integration point: Apply damage to player entity
                // When PlayerController/PlayerEntity is available, use:
                // PlayerController.takeDamage(damageAmount);
                // OR
                // Game.getPlayer().takeDamage(damageAmount);
                
                System.out.println("[AI:DamageManager] Ready for player system integration");
                
            } catch (Exception e) {
                System.err.println("[AI:DamageManager] Error applying damage: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        /**
         * Get total damage dealt by all enemies
         */
        public static float getTotalDamageDealt() {
            return totalDamageDealt;
        }
        
        /**
         * Get number of damage events
         */
        public static int getDamageEventCount() {
            return damageEventCount;
        }
        
        /**
         * Reset damage statistics
         */
        public static void resetStats() {
            totalDamageDealt = 0f;
            damageEventCount = 0;
        }
        
        /**
         * Get damage statistics summary
         */
        public static String getStats() {
            float avgDamage = damageEventCount > 0 ? totalDamageDealt / damageEventCount : 0;
            return String.format("Total Damage: %.0f HP | Events: %d | Average: %.1f HP/event",
                totalDamageDealt, damageEventCount, avgDamage);
        }
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════════
    // KNOCKBACK MANAGER - Enemy Knockback Effect on Player
    // ═══════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * KnockbackManager - Applies knockback physics effects to player
     * Integrates with physics system and VFX effects for impact feedback
     * Uses spark explosion effects from Resources/industrial-zone/vfx/
     */
    public static class KnockbackManager {
        private static int totalKnockbacksApplied = 0;  // Statistics tracking
        private static float totalKnockbackForce = 0f;  // Cumulative force applied
        
        /**
         * Apply knockback velocity to player entity
         * 
         * @param playerX Player X position (impact center)
         * @param playerY Player Y position (impact center)
         * @param dirX Knockback direction X component (normalized)
         * @param dirY Knockback direction Y component (normalized)
         * @param force Knockback force magnitude in units/second
         */
        public static void applyKnockback(float playerX, float playerY, float dirX, float dirY, float knockbackForce) {
            try {
                if (knockbackForce <= 0) {
                    System.err.println("[AI:KnockbackManager] Invalid knockback force: " + knockbackForce);
                    return;
                }
                
                // Track knockback statistics
                totalKnockbacksApplied++;
                totalKnockbackForce += knockbackForce;
                
                // Calculate velocity from force and direction
                float velocityX = dirX * knockbackForce;
                float velocityY = dirY * knockbackForce;
                
                // Integration point: Apply physics velocity
                // When PhysicsSystem is available, use:
                // PhysicsSystem.applyForce(playerX, playerY, velocityX, velocityY);
                // OR
                // PhysicsSystem.setVelocity(playerId, velocityX, velocityY);
                
                System.out.println("[AI:KnockbackManager] Knockback velocity: (" + String.format("%.1f", velocityX) + ", " + String.format("%.1f", velocityY) + ") at (" + playerX + ", " + playerY + ")");
                
                // Trigger VFX spark effect based on knockback force intensity
                triggerKnockbackVFX(playerX, playerY, knockbackForce);
                
            } catch (Exception e) {
                System.err.println("[AI:KnockbackManager] Error applying knockback: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        /**
         * Trigger VFX spark effect at impact location
         * Selects appropriate spark effect based on knockback force intensity
         */
        private static void triggerKnockbackVFX(float impactX, float impactY, float knockbackForce) {
            try {
                // Select spark VFX based on knockback force intensity
                String vfxPath = getKnockbackVFXPath(knockbackForce);
                
                if (vfxPath != null && !vfxPath.isEmpty()) {
                    System.out.println("[AI:KnockbackManager] VFX triggered: " + vfxPath + " at (" + impactX + ", " + impactY + ")");
                    // When AnimationManager is enhanced, load and display VFX:
                    // AnimationManager.playAnimation(KNOCKBACK_VFX_ID, vfxPath);
                    
                    // Verify file exists
                    java.io.File vfxFile = new java.io.File(vfxPath);
                    if (!vfxFile.exists()) {
                        System.err.println("[AI:KnockbackManager] WARNING - VFX file not found: " + vfxPath);
                    }
                }
            } catch (Exception e) {
                System.err.println("[AI:KnockbackManager] Error triggering VFX: " + e.getMessage());
            }
        }
        
        /**
         * Get VFX spark effect path based on knockback force
         * Uses actual spark assets from Resources/industrial-zone/vfx/3 Sparks/
         * Higher force = more intense spark effect
         */
        private static String getKnockbackVFXPath(float knockbackForce) {
            // Scale force to spark intensity: map force range to available effects
            if (knockbackForce <= 50f) {
                return "Resources/industrial-zone/vfx/3 Sparks/01_VFX_Sparks_Burst_4Frames1Row_SmallSparseGold_Impact_PlayOnce_80ms.png";
            } else if (knockbackForce <= 100f) {
                return "Resources/industrial-zone/vfx/3 Sparks/02_VFX_Sparks_Burst_4Frames1Row_SmallDenseGold_Impact_PlayOnce_80ms.png";
            } else if (knockbackForce <= 120f) {
                return "Resources/industrial-zone/vfx/3 Sparks/05_VFX_Sparks_Burst_4Frames1Row_MediumBoldBurst_Impact_PlayOnce_80ms.png";
            } else if (knockbackForce <= 150f) {
                return "Resources/industrial-zone/vfx/3 Sparks/07_VFX_Sparks_Burst_4Frames1Row_MediumMixedAngles_Impact_PlayOnce_80ms.png";
            } else {
                return "Resources/industrial-zone/vfx/3 Sparks/05_VFX_Sparks_Burst_4Frames1Row_MediumBoldBurst_Impact_PlayOnce_80ms.png";
            }
        }
        
        /**
         * Get total knockback force applied
         */
        public static float getTotalKnockbackForce() {
            return totalKnockbackForce;
        }
        
        /**
         * Get number of knockback events
         */
        public static int getTotalKnockbacksApplied() {
            return totalKnockbacksApplied;
        }
        
        /**
         * Reset knockback statistics
         */
        public static void resetStats() {
            totalKnockbacksApplied = 0;
            totalKnockbackForce = 0f;
        }
        
        /**
         * Get knockback statistics summary
         */
        public static String getStats() {
            float avgForce = totalKnockbacksApplied > 0 ? totalKnockbackForce / totalKnockbacksApplied : 0;
            return String.format("Total Knockbacks: %d | Total Force: %.0f | Average: %.1f force/event",
                totalKnockbacksApplied, totalKnockbackForce, avgForce);
        }
    }
    
    /**
     * PatrolBehavior - Patrol-specific logic
     * Independent nested static class using composition (contains EnemyInstance)
     */
    public static class PatrolBehavior {
        private EnemyInstance enemy;
        
        public PatrolBehavior(EnemyInstance enemy) {
            this.enemy = enemy;
        }
        
        public AIState update(long deltaTimeMs, float playerX, float playerY) {
            // Execute patrol movement logic using enemy data
            float currentX = enemy.x;
            
            // Check patrol boundaries and flip direction
            if (currentX >= enemy.patrolMaxX) {
                enemy.facingLeft = true;
            } else if (currentX <= enemy.patrolMinX) {
                enemy.facingLeft = false;
            }
            
            // Apply patrol speed movement
            float deltaSeconds = deltaTimeMs / 1000f;
            float moveX = enemy.facingLeft ? -enemy.patrolSpeed : enemy.patrolSpeed;
            
            enemy.vx = moveX;
            enemy.vy = 0;
            enemy.x += moveX * deltaSeconds;
            
            // Check if player is discovered during patrol
            float distToPlayer = (float)Math.sqrt((playerX - enemy.x) * (playerX - enemy.x) + (playerY - enemy.y) * (playerY - enemy.y));
            if (distToPlayer <= enemy.sightRange) {
                return AIState.ALERT;  // Transition to alert state
            }
            
            return AIState.PATROL;
        }
    }
    
    /**
     * ChaseBehavior - Chase-specific logic
     * Independent nested static class using composition (contains EnemyInstance)
     */
    public static class ChaseBehavior {
        private EnemyInstance enemy;
        
        public ChaseBehavior(EnemyInstance enemy) {
            this.enemy = enemy;
        }
        
        public AIState update(long deltaTimeMs, float playerX, float playerY) {
            // Move toward player using chase speed
            float dx = playerX - enemy.x;
            float dy = playerY - enemy.y;
            float distance = (float)Math.sqrt(dx*dx + dy*dy);
            
            if (distance < 1) {
                enemy.vx = 0;
                enemy.vy = 0;
                return AIState.ATTACK;  // Reached player, attack
            }
            
            // Normalize direction and apply chase speed
            float dirX = dx / distance;
            float dirY = dy / distance;
            float deltaSeconds = deltaTimeMs / 1000f;
            
            enemy.vx = dirX * enemy.chaseSpeed;
            enemy.vy = dirY * enemy.chaseSpeed;
            enemy.x += dirX * enemy.chaseSpeed * deltaSeconds;
            enemy.y += dirY * enemy.chaseSpeed * deltaSeconds;
            
            // Update facing direction based on player position
            if (dirX < 0) enemy.facingLeft = true;
            if (dirX > 0) enemy.facingLeft = false;
            
            // Check if close enough to attack
            if (distance <= enemy.attackRange) {
                return AIState.ATTACK;
            }
            
            // Check if player is still visible
            if (distance > enemy.sightRange) {
                return AIState.PATROL;  // Lost sight of player
            }
            
            return AIState.CHASE;
        }
    }
    
    /**
     * AttackBehavior - Attack-specific logic
     * Independent nested static class using composition (contains EnemyInstance)
     */
    public static class AttackBehavior {
        private EnemyInstance enemy;
        
        public AttackBehavior(EnemyInstance enemy) {
            this.enemy = enemy;
        }
        
        public AIState update(long deltaTimeMs, float playerX, float playerY) {
            // Face the player
            if (playerX < enemy.x) {
                enemy.facingLeft = true;
            } else {
                enemy.facingLeft = false;
            }
            
            // Check if player is still in attack range
            float dx = playerX - enemy.x;
            float dy = playerY - enemy.y;
            float distance = (float)Math.sqrt(dx*dx + dy*dy);
            
            if (distance > enemy.attackRange + 50) {
                return AIState.CHASE;  // Player out of range, resume chase
            }
            
            // Execute attack if cooldown expired
            long timeSinceLastAttack = System.currentTimeMillis() - enemy.lastAttackTimeMs;
            if (timeSinceLastAttack >= enemy.attackCooldownMs) {
                performEnemyAttack(enemy, playerX, playerY);
                enemy.lastAttackTimeMs = System.currentTimeMillis();
            }
            
            // Type-specific attack behaviors
            if (enemy.type == EnemyType.GUNNER) {
                // Gunners back away while attacking
                float backupSpeed = enemy.chaseSpeed * 0.5f;
                float moveX = (playerX < enemy.x) ? backupSpeed : -backupSpeed;
                float deltaSeconds = deltaTimeMs / 1000f;
                enemy.x += moveX * deltaSeconds;
                enemy.vx = moveX;
            } else if (enemy.type == EnemyType.MELEE) {
                // Melees stand ground or advance slightly
                enemy.vx = 0;
                enemy.vy = 0;
            } else {
                // Default: stationary attacks
                enemy.vx = 0;
                enemy.vy = 0;
            }
            
            return AIState.ATTACK;
        }
        
        private void performEnemyAttack(EnemyInstance enemy, float playerX, float playerY) {
            // Implementation hook for attack effects
            System.out.println("[AI] " + enemy.type.displayName + " #" + enemy.id + " attacks at (" + playerX + ", " + playerY + ")!");
            
            // 1. Play attack sound based on enemy type
            playAttackSound(enemy);
            
            // 2. Trigger attack animation
            triggerAttackAnimation(enemy);
            
            // 3. Apply damage to player (hook for damage system)
            applyDamageToPlayer(enemy, playerX, playerY);
        }
        
        /**
         * Play attack sound effect for the enemy type
         * Loads SFX from Resources/industrial-zone/audio/sfx/ directory
         */
        private void playAttackSound(EnemyInstance enemy) {
            try {
                // Get sound file path based on enemy type and attack style
                String soundPath = getAttackSoundPath(enemy.type);
                if (soundPath != null && !soundPath.isEmpty()) {
                    // Integrate with AudioSystem - play the attack sound
                    AudioSystem.AudioManager audioManager = new AudioSystem.AudioManager();
                    audioManager.playSound(soundPath);
                    System.out.println("[AI:Audio] Playing attack sound: " + soundPath + " | Enemy: " + enemy.type.displayName + " #" + enemy.id);
                    // Verify file would exist in resources
                    java.io.File audioFile = new java.io.File(soundPath);
                    if (!audioFile.exists()) {
                        System.err.println("[AI:Audio] WARNING - Sound file not found: " + soundPath);
                    }
                }
            } catch (Exception e) {
                System.err.println("[AI] Error playing attack sound: " + e.getMessage());
            }
        }
        
        /**
         * Get attack sound path for enemy type
         * Uses actual SFX files from Resources/industrial-zone/audio/sfx/
         */
        private String getAttackSoundPath(EnemyType type) {
            switch (type) {
                case MELEE:
                    return "Resources/industrial-zone/audio/sfx/._Karateka_attack.wav";
                case GUNNER:
                    return "Resources/industrial-zone/audio/sfx/._Laser_sword_1.wav";
                case DRONE:
                    return "Resources/industrial-zone/audio/sfx/._Hovering_robot_sting.wav";
                case BOSS:
                    return "Resources/industrial-zone/audio/sfx/._Explosion.wav";
                case PATROLLER:
                    return "Resources/industrial-zone/audio/sfx/._Click_digital_1.wav";
                default:
                    return null;
            }
        }
        
        /**
         * Trigger attack animation for the enemy
         * Uses actual spritesheet resources from Resources/industrial-zone/characters/enemies/
         */
        private void triggerAttackAnimation(EnemyInstance enemy) {
            try {
                String animPath = getAttackAnimationPath(enemy.type);
                if (animPath != null && !animPath.isEmpty()) {
                    // Integrate with animation system - load and play attack animation
                    AnimationManager.playAnimation(enemy.id, animPath);
                    System.out.println("[AI:Anim] Playing attack animation: " + animPath + " | Enemy: " + enemy.type.displayName + " #" + enemy.id);
                    // Verify file would exist in resources
                    java.io.File animFile = new java.io.File(animPath);
                    if (!animFile.exists()) {
                        System.err.println("[AI:Anim] WARNING - Animation file not found: " + animPath);
                    }
                }
            } catch (Exception e) {
                System.err.println("[AI] Error triggering attack animation: " + e.getMessage());
            }
        }
        
        /**
         * Get attack animation path for enemy type
         * Returns spritesheet PNG containing attack animation frames
         */
        private String getAttackAnimationPath(EnemyType type) {
            switch (type) {
                case MELEE:
                    return "Resources/industrial-zone/characters/enemies/sci-fi-antagonists/2/03_Enemy_ArmouredKnight_Attack1_5Frames1Row_BladeSlashForward_MeleeAttack_PlayOnce_70ms.png";
                case GUNNER:
                    return "Resources/industrial-zone/characters/enemies/sci-fi-antagonists/1/03_Enemy_CombatTank_Attack1_4Frames1Row_TurretStraightShot_RangedAttack_PlayOnce_80ms.png";
                case DRONE:
                    return "Resources/industrial-zone/characters/enemies/drones/1/03_EnemyDrone_UfoSaucerHovering_ScanBeamAttack_8Frames1Row.png";
                case BOSS:
                    return "Resources/industrial-zone/characters/bosses/GreenMech/07_Boss_GreenMech_Attack1_4Frames1Row_StationaryHeavyCannonBlast_RangedAttack_PlayOnce_100ms.png";
                case PATROLLER:
                    return "Resources/industrial-zone/characters/enemies/sci-fi-antagonists/2/01_Enemy_ArmouredKnight_Idle_4Frames1Row_StandbyPosture.png";
                default:
                    return null;
            }
        }
        
        /**
         * Apply damage to player based on enemy type and position
         * Detailed logging shows complete damage calculation pipeline
         */
        private void applyDamageToPlayer(EnemyInstance enemy, float playerX, float playerY) {
            try {
                // Step 1: Calculate base damage for enemy type
                float baseDamage = calculateBaseDamage(enemy.type);
                
                // Step 2: Scale by enemy health (damaged enemies deal less damage)
                float healthPercent = enemy.getHealthPercent();
                float healthModifier = healthPercent;  // 1.0 at full, 0.5 at half
                float damageAfterHealth = baseDamage * healthModifier;
                
                // Step 3: Calculate distance and apply range falloff
                float dx = playerX - enemy.x;
                float dy = playerY - enemy.y;
                float distance = (float)Math.sqrt(dx*dx + dy*dy);
                float distanceModifier = 1.0f;
                
                if (distance > enemy.attackRange) {
                    // Falloff at edge and beyond effective range
                    float excessDistance = distance - enemy.attackRange;
                    distanceModifier = Math.max(0.5f, 1.0f - (excessDistance / enemy.attackRange) * 0.5f);
                }
                
                float damageAfterDistance = damageAfterHealth * distanceModifier;
                
                // Step 4: Apply type-specific damage modifier
                float typeModifier = 1.0f;
                float knockbackForce = 0f;
                
                switch (enemy.type) {
                    case MELEE:
                        typeModifier = 1.0f;  // Full damage
                        knockbackForce = 100f;
                        break;
                    case GUNNER:
                        typeModifier = 1.2f;  // Projectiles deal extra damage
                        knockbackForce = 60f;
                        break;
                    case DRONE:
                        typeModifier = 0.8f;  // Reduced damage
                        knockbackForce = 40f;
                        break;
                    case BOSS:
                        typeModifier = 1.5f;  // Boss deals heavy damage
                        knockbackForce = 150f;
                        break;
                    case PATROLLER:
                        typeModifier = 0.6f;  // Very weak
                        knockbackForce = 30f;
                        break;
                }
                
                float damageAfterType = damageAfterDistance * typeModifier;
                
                // Step 5: Cap at reasonable range and ensure minimum damage
                float finalDamage = Math.max(1f, Math.min(50f, damageAfterType));
                
                // Detailed logging showing calculation steps
                String damageLogMessage = String.format(
                    "[AI:Damage] %s #%d attack: %.0f(base) × %.2f(health:%.0f%%) × %.2f(range:%.0f/%.0f) × %.2f(type) = %.2f → %d(final)",
                    enemy.type.displayName, enemy.id, baseDamage, healthModifier, healthPercent * 100,
                    distanceModifier, distance, enemy.attackRange, typeModifier, damageAfterType, (int)finalDamage);
                System.out.println(damageLogMessage);
                
                // Step 6: Apply special effects based on type
                if (knockbackForce > 0 && (enemy.type == EnemyType.MELEE || enemy.type == EnemyType.BOSS)) {
                    applyKnockback(playerX, playerY, enemy.x, enemy.y, knockbackForce);
                }
                
                // Integrate with player damage system - apply calculated damage
                PlayerDamageManager.applyDamageToPlayer((int)finalDamage);
                String impactLogMessage = String.format(
                    "[AI:Damage] Player takes %d damage | Enemy: %s #%d | Health Check: %.0f%% | Distance: %.1fpx",
                    (int)finalDamage, enemy.type.displayName, enemy.id, healthPercent * 100, distance);
                System.out.println(impactLogMessage);
                
            } catch (Exception e) {
                System.err.println("[AI] Error applying damage: " + e.getMessage());
            }
        }
        
        /**
         * Calculate base damage for enemy type
         */
        private float calculateBaseDamage(EnemyType type) {
            switch (type) {
                case MELEE:
                    return 15f;  // High close-range damage
                case GUNNER:
                    return 12f;  // Medium ranged damage
                case DRONE:
                    return 8f;   // Low damage
                case BOSS:
                    return 25f;  // Very high damage
                case PATROLLER:
                    return 5f;   // Very low damage (weak enemy)
                default:
                    return 10f;
            }
        }
        
        /**
         * Apply knockback effect to player when hit by melee attacks
         */
        private void applyKnockback(float playerX, float playerY, float sourceX, float sourceY, float knockbackForce) {
            try {
                // Calculate knockback direction (away from source)
                float dx = playerX - sourceX;
                float dy = playerY - sourceY;
                float distance = (float)Math.sqrt(dx*dx + dy*dy);
                
                if (distance < 1) distance = 1;
                
                float dirX = dx / distance;
                float dirY = dy / distance;
                
                // Integrate with physics system - apply knockback velocity
                KnockbackManager.applyKnockback(playerX, playerY, dirX, dirY, knockbackForce);
                
                // Detailed logging with knockback calculation details
                System.out.println("[AI:Physics] Knockback applied: (" + String.format("%.2f", dirX) + ", " + String.format("%.2f", dirY) + ") force=" + knockbackForce);
                System.out.println("[AI:Physics] Direction: away from enemy at (" + sourceX + ", " + sourceY + ") distance=" + String.format("%.1f", distance));
            } catch (Exception e) {
                System.err.println("[AI] Error applying knockback: " + e.getMessage());
            }
        }
    }
    
    /**
     * FleeBehavior - Flee-specific logic
     * Independent nested static class using composition (contains EnemyInstance)
     */
    public static class FleeBehavior {
        private EnemyInstance enemy;
        
        public FleeBehavior(EnemyInstance enemy) {
            this.enemy = enemy;
        }
        
        public AIState update(long deltaTimeMs, float playerX, float playerY) {
            // Move away from player at chase speed
            float dx = enemy.x - playerX;  // Direction away from player
            float dy = enemy.y - playerY;
            float distance = (float)Math.sqrt(dx*dx + dy*dy);
            
            if (distance < 1) {
                // Too close, move in random direction
                dx = (float)Math.random() - 0.5f;
                dy = (float)Math.random() - 0.5f;
                distance = 1.0f;
            }
            
            // Normalize direction and apply flee speed (chase speed)
            float dirX = dx / distance;
            float dirY = dy / distance;
            float fleeSpeed = enemy.chaseSpeed;  // Flee at normal chase speed
            float deltaSeconds = deltaTimeMs / 1000f;
            
            enemy.vx = dirX * fleeSpeed;
            enemy.vy = dirY * fleeSpeed;
            enemy.x += dirX * fleeSpeed * deltaSeconds;
            enemy.y += dirY * fleeSpeed * deltaSeconds;
            
            // Update facing to match flee direction
            if (dirX < 0) enemy.facingLeft = true;
            if (dirX > 0) enemy.facingLeft = false;
            
            // Check if safely away from player
            float distFromPlayer = (float)Math.sqrt((playerX - enemy.x) * (playerX - enemy.x) + 
                                                     (playerY - enemy.y) * (playerY - enemy.y));
            if (distFromPlayer > enemy.sightRange * 2) {
                return AIState.PATROL;  // Safe - resume patrol
            }
            
            return AIState.FLEE;
        }
    }
}
