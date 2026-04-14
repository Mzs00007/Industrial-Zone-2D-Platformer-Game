import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Projectile - Simple bullet/projectile entity
 * 
 * Tracks position, velocity, damage, and lifetime
 * Rendered as simple colored rectangle (can be upgraded to sprites later)
 */
public class Projectile {
    private float x;
    private float y;
    private float velocityX;
    private float velocityY;
    private float damage;
    private long spawnTime;
    private float lifetime;
    private static final float GRAVITY = 300.0f;
    private static final int PROJECTILE_WIDTH = 8;
    private static final int PROJECTILE_HEIGHT = 4;
    
    /**
     * Create a projectile fired from the player
     */
    public Projectile(float startX, float startY,  float velocityX, float velocityY, float damage, float lifetime) {
        this.x = startX;
        this.y = startY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.damage = damage;
        this.lifetime = lifetime;
        this.spawnTime = System.currentTimeMillis();
    }
    
    /**
     * Update projectile physics
     */
    public void update(float deltaTime) {
        // Apply gravity
        this.velocityY += GRAVITY * deltaTime;
        
        // Update position
        this.x += this.velocityX * deltaTime;
        this.y += this.velocityY * deltaTime;
    }
    
    /**
     * Check if projectile is still alive
     */
    public boolean isAlive() {
        long elapsedMs = System.currentTimeMillis() - spawnTime;
        return elapsedMs < (lifetime * 1000.0f) && this.y < 2000;
    }
    
    /**
     * Render projectile
     */
    public void render(Graphics2D g, int cameraOffsetX, int cameraOffsetY) {
        int screenX = (int)(this.x - cameraOffsetX);
        int screenY = (int)(this.y - cameraOffsetY);
        
        // Draw projectile as yellow rectangle
        g.setColor(Color.YELLOW);
        g.fillRect(screenX, screenY, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
    }
    
    // Getters
    public float getX() { return x; }
    public float getY() { return y; }
    public float getDamage() { return damage; }
    public float getWidth() { return PROJECTILE_WIDTH; }
    public float getHeight() { return PROJECTILE_HEIGHT; }
    
    /**
     * Kill projectile immediately (used on collision)
     */
    public void kill() {
        this.lifetime = -1; // Negative lifetime makes isAlive() return false
    }
}
