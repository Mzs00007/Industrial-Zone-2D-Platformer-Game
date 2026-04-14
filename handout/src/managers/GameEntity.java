package managers;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * BASE CLASS - All common game entity logic lives here (ONE PLACE ONLY!)
 * 
 * ✅ INHERITANCE PATTERN:
 * - PlayerController extends GameEntity
 * - EnemyController extends GameEntity
 * - BossController extends GameEntity
 * 
 * ✅ CODE REUSE:
 * - Physics (update, applyGravity, handleCollisions) written ONCE
 * - Animation management written ONCE
 * - Damage system written ONCE
 * - All 3 classes use these WITHOUT DUPLICATION
 */
public abstract class GameEntity {
    
    // ════════════════════════════════════════════════════════════════
    // SHARED PROPERTIES (Used by ALL entities - Player, Enemy, Boss)
    // ════════════════════════════════════════════════════════════════
    protected float x, y;                    // Position in world
    protected float velocityX, velocityY;    // Velocity (pixels per frame)
    protected float width, height;           // Dimensions
    
    protected int health;                    // Health (all entities have this)
    protected int maxHealth;                 // Max health varies per entity
    protected boolean isAlive = true;        // Alive status
    protected boolean isGrounded = false;    // On ground?
    
    protected BufferedImage currentFrame;    // Current animation frame
    protected int animationState = 0;        // 0=IDLE, 1=WALK, 2=JUMP, 3=FALL
    
    // Physics constants (shared by all)
    protected static final float GRAVITY = -9.81f;      // m/s²
    protected static final float FRICTION = 0.08f;      // Velocity damping
    protected static final float TERMINAL_VELOCITY = 15.0f;  // Max fall speed
    protected static final float GROUND_LEVEL = 600.0f;  // Pixels from top
    
    // ════════════════════════════════════════════════════════════════
    // SHARED METHODS - Written ONCE, used by ALL entities
    // ════════════════════════════════════════════════════════════════
    
    /**
     * Main physics update - Called 60 times per second
     * ONE implementation handles Player, Enemy, and Boss physics
     */
    public void update(float deltaTime) {
        if (!isAlive) return;
        
        // Apply gravity to all entities
        applyGravity(deltaTime);
        
        // Apply friction to all entities
        applyFriction(deltaTime);
        
        // Update position based on velocity
        updatePosition(deltaTime);
        
        // Check collisions with ground/platforms
        handleCollisions();
        
        // Update animation based on state
        updateAnimation(deltaTime);
    }
    
    /**
     * Apply gravity to all entities - Written ONCE
     * Player, Enemy, Boss all use this same code
     */
    protected void applyGravity(float deltaTime) {
        if (!isGrounded) {
            velocityY += GRAVITY * deltaTime;
            if (velocityY < -TERMINAL_VELOCITY) {
                velocityY = -TERMINAL_VELOCITY;
            }
        }
    }
    
    /**
     * Apply friction to slow down movement - Written ONCE
     * Shared by all 3 entity types
     */
    protected void applyFriction(float deltaTime) {
        velocityX *= (1 - FRICTION);
    }
    
    /**
     * Update position from velocity - Written ONCE
     * All entities update position the same way
     */
    protected void updatePosition(float deltaTime) {
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
    }
    
    /**
     * Collision detection with ground - Written ONCE
     * Player, Enemy, Boss all check collisions the same way
     */
    protected void handleCollisions() {
        if (y >= GROUND_LEVEL) {
            y = GROUND_LEVEL;
            velocityY = 0;
            isGrounded = true;
        } else {
            isGrounded = false;
        }
    }
    
    /**
     * Damage system - All entities take damage the same way
     * Polymorphism: onDeath() is customized per entity
     */
    public void takeDamage(int amount) {
        if (!isAlive) return;
        
        health -= amount;
        
        if (health <= 0) {
            isAlive = false;
            onDeath();  // Each entity handles death differently
        }
    }
    
    /**
     * Healing system - Shared by all entities
     */
    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth);
    }
    
    // ════════════════════════════════════════════════════════════════
    // ABSTRACT METHODS - Each entity implements entity-specific behavior
    // ════════════════════════════════════════════════════════════════
    
    /**
     * Animation update - Each entity handles animations differently
     * Player might have 8 walk frames, Enemy might have 6
     */
    protected abstract void updateAnimation(float deltaTime);
    
    /**
     * Rendering - Each entity looks different
     */
    public abstract void draw(Graphics2D g);
    
    /**
     * Death handler - Each entity dies differently
     * Player: trigger game over
     * Enemy: drop loot
     * Boss: special sequence
     */
    protected abstract void onDeath();
    
    /**
     * Each entity has different max health
     */
    public abstract int getMaxHealth();
    
    // ════════════════════════════════════════════════════════════════
    // GETTERS/SETTERS - Consistent interface for all entities
    // ════════════════════════════════════════════════════════════════
    
    public float getX() { return x; }
    public float getY() { return y; }
    public void setPosition(float newX, float newY) { 
        x = newX; 
        y = newY; 
    }
    
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    
    public int getHealth() { return health; }
    public boolean isAlive() { return isAlive; }
    
    public void setVelocity(float vx, float vy) {
        velocityX = vx;
        velocityY = vy;
    }
    
    public void applyVelocity(float vx, float vy) {
        velocityX += vx;
        velocityY += vy;
    }
}
