package managers;

/**
 * COMPOSITION PATTERN - CollisionDetector
 * 
 * ✅ HAS-A Relationship:
 * - PlayerController HAS-A CollisionDetector (not IS-A)
 * - EnemyController HAS-A CollisionDetector
 * - BossController HAS-A CollisionDetector
 * 
 * ✅ WHY COMPOSITION OVER INHERITANCE:
 * - If we did: class PlayerController extends CollisionDetector
 *   Then enemy would ALSO need to extend CollisionDetector
 *   Can't extend both GameEntity and CollisionDetector (multiple inheritance)
 * 
 * ✅ SOLUTION: Composition (HAS-A):
 * - PlayerController extends GameEntity
 * - PlayerController contains (has) CollisionDetector
 * - All entities can use SAME CollisionDetector code
 * 
 * Example Usage:
 * 
 * public class PlayerController extends GameEntity {
 *     private CollisionDetector collisionDetector = new CollisionDetector();
 *     
 *     @Override
 *     public void update(float deltaTime) {
 *         super.update(deltaTime);
 *         
 *         if (collisionDetector.checkGroundCollision(y, groundY)) {
 *             isGrounded = true;
 *         }
 *         
 *         if (collisionDetector.checkWallCollision(x, wallX)) {
 *             bounceBack();
 *         }
 *     }
 * }
 */
class CollisionDetector {
    
    /**
     * Check if entity is on ground
     */
    public boolean checkGroundCollision(float entityY, float groundY) {
        return Math.abs(entityY - groundY) < 5;  // Within 5 pixels of ground
    }
    
    /**
     * Check if entity hits wall
     */
    public boolean checkWallCollision(float entityX, float wallX) {
        return Math.abs(entityX - wallX) < 10;  // Within 10 pixels of wall
    }
    
    /**
     * Rectangle collision (used by all entities)
     */
    public boolean rectangleCollides(
            float x1, float y1, float w1, float h1,
            float x2, float y2, float w2, float h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 
            && y1 < y2 + h2 && y1 + h1 > y2;
    }
    
    /**
     * Check if entity is in range of another entity
     */
    public boolean inRange(float x1, float y1, float x2, float y2, float range) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance <= range;
    }
}

/**
 * COMPOSITION PATTERN - AnimationManager
 * 
 * ✅ HAS-A Relationship:
 * - PlayerController HAS-A AnimationManager
 * - EnemyController HAS-A AnimationManager
 * - BossController HAS-A AnimationManager
 * 
 * ✅ SAME CODE, DIFFERENT CONFIGURATIONS:
 * - Player: 4 states (IDLE, WALK, JUMP, FALL)
 * - Enemy: 2 states (IDLE, WALK)
 * - Boss: 3 states (IDLE, WALK, ATTACK)
 * 
 * All use the SAME AnimationManager class!
 */
class AnimationManager {
    
    private int currentFrame = 0;
    private int totalFrames = 0;
    private long lastFrameTime = 0;
    private int frameDelayMs = 100;
    
    public AnimationManager(int frameCount, int delayMs) {
        this.totalFrames = frameCount;
        this.frameDelayMs = delayMs;
    }
    
    /**
     * Advance animation frame
     */
    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= frameDelayMs) {
            currentFrame = (currentFrame + 1) % totalFrames;
            lastFrameTime = currentTime;
        }
    }
    
    /**
     * Get current frame index
     */
    public int getCurrentFrame() {
        return currentFrame;
    }
    
    /**
     * Reset animation
     */
    public void reset() {
        currentFrame = 0;
        lastFrameTime = System.currentTimeMillis();
    }
    
    /**
     * Set animation speed
     */
    public void setSpeed(int delayMs) {
        frameDelayMs = delayMs;
    }
}

/**
 * COMPOSITION PATTERN - HealthSystem
 * 
 * ✅ HAS-A Relationship:
 * - PlayerController HAS-A HealthSystem
 * - EnemyController HAS-A HealthSystem
 * - BossController HAS-A HealthSystem
 * 
 * ✅ BETTER THAN INHERITANCE:
 * - Each entity can customize health behavior
 * - Multiple entities can share same health implementation
 * - Easy to add health regeneration, shields, etc.
 */
public class HealthSystem {
    
    private int currentHealth;
    private int maxHealth;
    
    public HealthSystem(int max) {
        this.maxHealth = max;
        this.currentHealth = max;
    }
    
    /**
     * Take damage
     */
    public void takeDamage(int amount) {
        currentHealth -= amount;
        if (currentHealth < 0) currentHealth = 0;
    }
    
    /**
     * Heal
     */
    public void heal(int amount) {
        currentHealth = Math.min(currentHealth + amount, maxHealth);
    }
    
    /**
     * Check if alive
     */
    public boolean isAlive() {
        return currentHealth > 0;
    }
    
    /**
     * Get health percentage (0-100)
     */
    public float getHealthPercent() {
        return (currentHealth * 100f) / maxHealth;
    }
    
    public int getCurrentHealth() { return currentHealth; }
    public int getMaxHealth() { return maxHealth; }
}
