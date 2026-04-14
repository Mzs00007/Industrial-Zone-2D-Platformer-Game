package core;

import java.awt.*;

/**
 * CHILD CLASS 2: EnemyController
 * 
 * ✅ Inherits from GameEntity:
 * - x, y (position) - SAME as PlayerController
 * - velocityX, velocityY (physics) - SAME movement physics
 * - health, isAlive (state) - SAME damage system
 * - update() - SAME physics (gravity, friction, collision)
 * - takeDamage() - SAME damage handling
 * 
 * ✅ NO CODE DUPLICATION:
 * - Player's applyGravity() + Enemy's applyGravity() = SAME (inherited)
 * - Player's takeDamage() + Enemy's takeDamage() = SAME (inherited)
 * - Physics written 1 time, used by 2 entities
 * 
 * ✅ DIFFERENT BEHAVIOR:
 * - Enemy animation states (different than player)
 * - Enemy AI (patrol, chase, attack)
 * - Enemy death (drop loot, not game over)
 * - Enemy rendering (different appearance)
 */
public class EnemyController extends GameEntity {
    
    // ════════════════════════════════════════════════════════════════
    // ENEMY-SPECIFIC PROPERTIES
    // ════════════════════════════════════════════════════════════════
    private int aiState = 0;  // 0=IDLE, 1=PATROL, 2=CHASE, 3=ATTACK
    
    private float detectionRange = 200.0f;    // How far can enemy see player?
    private float followSpeed = 3.5f;         // Slower than player
    
    private int lootDropAmount = 50;          // Score when defeated
    
    // Patrol behavior
    private float patrolLeft, patrolRight;
    private boolean patrolDirection = true;   // true=right, false=left
    
    // Chase behavior
    private float targetX = 0;
    private long lastPathfindTime = 0;
    private static final long PATHFIND_INTERVAL = 500;  // ms
    
    // Animation
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final int FRAME_DELAY = 150;  // ms per frame (slower than player)
    
    // ════════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ════════════════════════════════════════════════════════════════
    public EnemyController(float startX, float startY, float patrolLeft, float patrolRight) {
        this.x = startX;
        this.y = startY;
        this.health = 50;  // Enemies are weaker than player
        this.maxHealth = 50;
        this.width = 28;
        this.height = 40;
        this.isAlive = true;
        this.animationState = 0;  // Start in IDLE
        this.patrolLeft = patrolLeft;
        this.patrolRight = patrolRight;
        this.aiState = 1;  // Start patrolling
    }
    
    // ════════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS - Enemy-specific implementations
    // ════════════════════════════════════════════════════════════════
    
    /**
     * Enemy-specific update with AI
     * Calls parent update() for shared physics (gravity, friction, collisions)
     */
    @Override
    public void update(float deltaTime) {
        // Call parent update for physics
        super.update(deltaTime);
        
        // ONLY enemy-specific: AI behavior
        updateAI(deltaTime);
    }
    
    /**
     * AI state machine
     */
    private void updateAI(float deltaTime) {
        switch (aiState) {
            case 0:  // IDLE
                velocityX = 0;
                break;
                
            case 1:  // PATROL - walk back and forth
                patrol();
                animationState = 1;  // Play walk animation
                break;
                
            case 2:  // CHASE - follow player
                // (In a real game, would get player reference)
                chase(targetX);
                animationState = 1;  // Play walk animation
                break;
                
            case 3:  // ATTACK - prepare to attack
                velocityX = 0;
                animationState = 2;  // Play attack animation
                break;
        }
    }
    
    /**
     * Patrol back and forth
     */
    private void patrol() {
        if (patrolDirection) {
            // Moving right
            velocityX = followSpeed;
            if (x >= patrolRight) {
                patrolDirection = false;  // Turn around
            }
        } else {
            // Moving left
            velocityX = -followSpeed;
            if (x <= patrolLeft) {
                patrolDirection = true;  // Turn around
            }
        }
    }
    
    /**
     * Chase player toward target
     */
    private void chase(float targetX) {
        if (x < targetX) {
            velocityX = followSpeed;  // Move right toward target
        } else {
            velocityX = -followSpeed;  // Move left toward target
        }
    }
    
    /**
     * Enemy-specific animation
     */
    @Override
    protected void updateAnimation(float deltaTime) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= FRAME_DELAY) {
            currentFrame++;
            lastFrameTime = currentTime;
            
            // Wrap frame
            int frameCount = (animationState == 0) ? 1 : 4;  // Different from player
            if (currentFrame >= frameCount) {
                currentFrame = 0;
            }
        }
    }
    
    /**
     * Enemy-specific rendering (red for visibility)
     */
    @Override
    public void draw(Graphics2D g) {
        if (!isAlive) return;
        
        // Draw enemy as red rectangle
        g.setColor(new Color(200, 50, 50));
        g.fillRect((int)x, (int)y, (int)width, (int)height);
        
        // Draw health bar
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)(y - 10), (int)width, 5);
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)(y - 10), (int)(width * health / maxHealth), 5);
        
        // AI state indicator
        g.setColor(Color.WHITE);
        g.drawString("AI:" + aiState, (int)x, (int)(y - 15));
    }
    
    /**
     * Enemy-specific death
     */
    @Override
    protected void onDeath() {
        System.out.println("Enemy defeated! Loot drop: " + lootDropAmount);
        // Drop loot, play death animation, etc.
    }
    
    @Override
    public int getMaxHealth() {
        return 50;
    }
    
    // ════════════════════════════════════════════════════════════════
    // ENEMY-SPECIFIC METHODS
    // ════════════════════════════════════════════════════════════════
    
    public void setAIState(int state) { 
        aiState = state; 
    }
    
    public int getAIState() { 
        return aiState; 
    }
    
    public void setChaseTarget(float targetX) { 
        this.targetX = targetX; 
    }
    
    public float getDetectionRange() { 
        return detectionRange; 
    }
    
    public int getLootDrop() { 
        return lootDropAmount; 
    }
}
