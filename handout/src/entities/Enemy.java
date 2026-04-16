package entities;

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
    private long lastAttackMs = 0;
    private static final long ATTACK_COOLDOWN_MS = 800L;
    private static final int  CONTACT_DAMAGE     = 15;

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
        }
    }

    /**
     * Update enemy state - called by Game with float seconds delta and player position.
     * @param delta     seconds since last frame
     * @param playerX   player world X (used for chasing)
     * @param playerY   player world Y
     */
    public void update(float delta, float playerX, float playerY) {
        if (!alive) return;

        // Convert seconds → ms for legacy animation counter
        long deltaMs = (long)(delta * 1000f);

        // Simple movement - patrol back and forth
        x += vx;

        // Bounce at level edges
        if (x < 0 || x > 8960 - 32) {
            moveDirection *= -1;
            vx = moveSpeed * moveDirection;
        }

        // Update animation frame
        lastFrameTime += deltaMs;
        if (lastFrameTime > frameDuration) {
            currentFrame = (currentFrame + 1) % frameCount;
            lastFrameTime = 0;
        }

        // Attack cooldown
        if (attackCooldown > 0) {
            attackCooldown -= deltaMs;
        }
    }

    /**
     * Deal contact damage to the player (called by Game when bounding boxes overlap).
     */
    public void attackPlayer(PlayerBase player) {
        long now = System.currentTimeMillis();
        if (now - lastAttackMs >= ATTACK_COOLDOWN_MS) {
            player.takeDamage(CONTACT_DAMAGE);
            lastAttackMs = now;
        }
    }

    /**
     * Render enemy to screen using real sprite, accounting for camera offset.
     */
    public void render(java.awt.Graphics2D g, float cameraX, float cameraY) {
        if (!alive || idleSprite == null) return;

        float drawX = x - cameraX;
        float drawY = y - cameraY;

        int frameWidth = idleSprite.getWidth() / frameCount;
        int frameHeight = idleSprite.getHeight();

        BufferedImage frame = idleSprite.getSubimage(
            currentFrame * frameWidth, 0,
            frameWidth, frameHeight
        );

        g.drawImage(frame, (int)drawX, (int)drawY, (int)width, (int)height, null);

        // Health indicator
        g.setColor(java.awt.Color.RED);
        g.fillRect((int)drawX, (int)drawY - 6, (int)width, 4);
        g.setColor(java.awt.Color.GREEN);
        g.fillRect((int)drawX, (int)drawY - 6, (int)(width * health / maxHealth), 4);
    }

    /**
     * Take damage from a projectile.
     */
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            alive = false;
        }
    }

    // Getters
    public float getX()       { return x; }
    public float getY()       { return y; }
    public float getWidth()   { return width; }
    public float getHeight()  { return height; }
    public int   getHealth()  { return health; }
    public boolean isAlive()  { return alive; }
    
    /**
     * Get enemy type for impact system classification
     * Default type is "DRONE" - can be overridden by subclasses
     */
    public String getType() { return "DRONE"; }

    // Setters
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
