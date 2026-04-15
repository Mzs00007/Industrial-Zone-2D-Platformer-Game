package managers;
import game2D.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * CHILD CLASS 1: PlayerController
 * 
 * ✅ Inherits from GameEntity:
 * - x, y (position)
 * - velocityX, velocityY (physics)
 * - health, isAlive, isGrounded (state)
 * - update() for physics (gravity, friction, collision)
 * - takeDamage() for damage system
 * 
 * ✅ NO CODE DUPLICATION:
 * - Uses parent's applyGravity()
 * - Uses parent's applyFriction()
 * - Uses parent's updatePosition()
 * - Uses parent's handleCollisions()
 * - Uses parent's takeDamage()
 * 
 * ✅ ONLY CUSTOM CODE:
 * - Player-specific animation (IDLE, WALK, JUMP, FALL)
 * - Player-specific drawing (render character)
 * - Player-specific death (game over)
 * - Player input handling (keyboard control)
 */
public class PlayerController extends GameEntity {
    
    // ════════════════════════════════════════════════════════════════
    // PLAYER-SPECIFIC PROPERTIES (only player has these)
    // ════════════════════════════════════════════════════════════════
    private int score = 0;
    private int ammo = 100;
    private boolean canJump = true;
    
    // Animation properties
    private BufferedImage[] idleFrames;
    private BufferedImage[] walkFrames;
    private BufferedImage[] jumpFrames;
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final int FRAME_DELAY = 100;  // ms per frame
    
    // Input flags
    private boolean movingLeft = false;
    private boolean movingRight = false;
    
    private static final float MOVE_SPEED = 5.0f;      // pixels per frame
    private static final float JUMP_POWER = -12.0f;    // pixels per frame
    
    // ════════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ════════════════════════════════════════════════════════════════
    public PlayerController(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.health = 100;
        this.maxHealth = 100;
        this.width = 32;
        this.height = 48;
        this.isAlive = true;
        this.animationState = 0;  // Start in IDLE
    }
    
    // ════════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS - Player-specific implementations
    // ════════════════════════════════════════════════════════════════
    
    /**
     * Player-specific update with input handling
     * Calls parent update() for shared physics
     */
    @Override
    public void update(float deltaTime) {
        // Call parent update for physics (gravity, friction, collisions, animation)
        super.update(deltaTime);
        
        // ONLY player-specific: handle keyboard input
        handlePlayerInput(deltaTime);
    }
    
    /**
     * Handle player keyboard input
     */
    private void handlePlayerInput(float deltaTime) {
        // Horizontal movement
        if (movingLeft) {
            velocityX = -MOVE_SPEED;
        } else if (movingRight) {
            velocityX = MOVE_SPEED;
        } else {
            velocityX = 0;  // Stop moving if no input
        }
        
        // Jumping (only if grounded)
        if (canJump && isGrounded) {
            velocityY = JUMP_POWER;
            isGrounded = false;
            canJump = false;
        }
    }
    
    public void setMovingLeft(boolean moving) { movingLeft = moving; }
    public void setMovingRight(boolean moving) { movingRight = moving; }
    public void setJumping(boolean jumping) { canJump = jumping; }
    
    /**
     * Player-specific animation update
     * Transitions between IDLE, WALK, JUMP, FALL states
     */
    @Override
    protected void updateAnimation(float deltaTime) {
        // Determine animation state based on motion
        if (isGrounded) {
            if (velocityX == 0) {
                animationState = 0;  // IDLE
            } else {
                animationState = 1;  // WALK
            }
        } else {
            if (velocityY < 0) {
                animationState = 2;  // JUMP (going up)
            } else {
                animationState = 3;  // FALL (going down)
            }
        }
        
        // Advance animation frame
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= FRAME_DELAY) {
            currentFrame++;
            lastFrameTime = currentTime;
            
            // Wrap frame back to 0 (animation loops)
            int frameCount = (animationState == 0) ? 4 : 8;  // IDLE=4, WALK=8, etc
            if (currentFrame >= frameCount) {
                currentFrame = 0;
            }
        }
    }
    
    /**
     * Player-specific rendering
     */
    @Override
    public void draw(Graphics2D g) {
        if (!isAlive) return;
        
        // Draw player sprite
        if (currentFrame < 90) {  // Placeholder for actual sprite
            g.setColor(Color.BLUE);
        } else {
            g.setColor(new Color(0, 100, 200));
        }
        g.fillRect((int)x, (int)y, (int)width, (int)height);
        
        // Draw health bar
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)(y - 10), (int)width, 5);
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)(y - 10), (int)(width * health / 100), 5);
    }
    
    /**
     * Player-specific death
     */
    @Override
    protected void onDeath() {
        System.out.println("GAME OVER! Player defeated at score: " + score);
        // Trigger game over screen
    }
    
    @Override
    public int getMaxHealth() {
        return 100;
    }
    
    // ════════════════════════════════════════════════════════════════
    // PLAYER-SPECIFIC METHODS
    // ════════════════════════════════════════════════════════════════
    
    public int getScore() { return score; }
    public void addScore(int points) { score += points; }
    
    public int getAmmo() { return ammo; }
    public void fireWeapon() {
        if (ammo > 0) {
            ammo--;
            System.out.println("BANG! Ammo: " + ammo);
        }
    }
}
