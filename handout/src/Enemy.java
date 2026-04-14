import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Enemy - Basic enemy class using real drone sprites from Resources
 * Drone Type 1: UFO Saucer Hovering
 */
public class Enemy {
    
    // Enemy properties
    private float x, y;
    private float vx, vy;
    private float width = 32, height = 32;
    private int health = 3;
    private int maxHealth = 3;
    private boolean alive = true;
    
    // Sprite animations (real assets from Resources)
    private BufferedImage idleSprite;
    private BufferedImage traverseSprite;
    private BufferedImage attackSprite;
    private long lastFrameTime = 0;
    private int currentFrame = 0;
    private int frameCount = 4; // Idle has 4 frames
    private long frameDuration = 150; // 150ms per frame
    
    // Behavior
    private long spawnTime;
    private int moveDirection = 1; // 1 = right, -1 = left
    private float moveSpeed = 2.0f;
    private boolean isAttacking = false;
    private long attackCooldown = 0;
    
    /**
     * Create enemy at position (droneType 1 = UFO Saucer)
     */
    public Enemy(float x, float y) {
        this.x = x;
        this.y = y;
        this.spawnTime = System.currentTimeMillis();
        this.vx = moveSpeed * moveDirection;
        this.vy = 0;
        
        loadSprites();
    }
    
    /**
     * Load real drone sprite assets from Resources
     * Using Drone Type 1 (UFO Saucer Hovering)
     */
    private void loadSprites() {
        try {
            String basePath = "Resources/industrial-zone/characters/enemies/drones/1/";
            
            // Try to load idle sprite
            File idleFile = new File(basePath + "01_EnemyDrone_UfoSaucerHovering_Idle_4Frames1Row.png");
            if (idleFile.exists()) {
                idleSprite = ImageIO.read(idleFile);
                frameCount = 4; // 4 frames for idle
            } else {
                System.err.println("[Enemy] Could not find idle sprite: " + idleFile.getAbsolutePath());
                createFallbackSprite();
            }
            
            // Load traverse sprite
            File traverseFile = new File(basePath + "02_EnemyDrone_UfoSaucerHovering_Traverse_4Frames1Row.png");
            if (traverseFile.exists()) {
                traverseSprite = ImageIO.read(traverseFile);
            }
            
            // Load attack sprite
            File attackFile = new File(basePath + "03_EnemyDrone_UfoSaucerHovering_ScanBeamAttack_8Frames1Row.png");
            if (attackFile.exists()) {
                attackSprite = ImageIO.read(attackFile);
            }
            
        } catch (Exception e) {
            System.err.println("[Enemy] Failed to load sprites: " + e.getMessage());
            createFallbackSprite();
        }
    }
    
    /**
     * Create minimal fallback for missing sprite (red rectangle)
     * This is ONLY used if real assets are missing - should never happen in normal gameplay
     */
    private void createFallbackSprite() {
        idleSprite = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                idleSprite.setRGB(i, j, 0xFFCC0000); // Red to indicate missing asset
            }
        }
        System.err.println("[Enemy] ⚠️  FALLBACK SPRITE - Real drone image not loaded!");
    }
    
    /**
     * Update enemy state and animation
     */
    public void update(long deltaTime) {
        if (!alive) return;
        
        // Simple movement - patrol back and forth
        x += vx;
        
        // Bounce at level edges (rough bounds)
        if (x < 0 || x > 8960 - 32) {
            moveDirection *= -1;
            vx = moveSpeed * moveDirection;
        }
        
        // Update animation frame
        lastFrameTime += deltaTime;
        if (lastFrameTime > frameDuration) {
            currentFrame = (currentFrame + 1) % frameCount;
            lastFrameTime = 0;
        }
        
        // Attack cooldown
        if (attackCooldown > 0) {
            attackCooldown -= deltaTime;
        }
    }
    
    /**
     * Render enemy to screen using real sprite, accounting for camera offset
     */
    public void render(java.awt.Graphics2D g, float cameraX, float cameraY) {
        if (!alive || idleSprite == null) return;
        
        float drawX = x - cameraX;
        float drawY = y - cameraY;
        
        // Get current frame from spritesheet
        if (idleSprite != null) {
            int frameWidth = idleSprite.getWidth() / frameCount;
            int frameHeight = idleSprite.getHeight();
            
            BufferedImage frame = idleSprite.getSubimage(
                currentFrame * frameWidth, 0,
                frameWidth, frameHeight
            );
            
            g.drawImage(frame, (int)drawX, (int)drawY, (int)width, (int)height, null);
            
            // Debug: show health
            g.setColor(java.awt.Color.RED);
            g.drawString("HP:" + health, (int)drawX, (int)drawY - 5);
        }
    }
    
    /**
     * Take damage from projectile or attack
     */
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            alive = false;
        }
    }
    
    // Getters
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public int getHealth() { return health; }
    public boolean isAlive() { return alive; }
    
    // Setters for debugging
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
