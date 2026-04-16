package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Card - High-value collectible that spawns from chests
 * Features:
 *   - Rotating animation while floating
 *   - Auto-collect on player touch
 *   - Worth 25 points + adds to card count
 */
public class Card {
    
    private float x, y;
    private float vx, vy;           // velocity
    private float lifetime;         // fade out timer
    private static final float DURATION = 8.0f;  // exists for 8 seconds then disappears
    private static final float GRAVITY = 200f;
    private static final float PICKUP_RANGE = 40f;
    
    // Animation
    private BufferedImage[] frames;
    private int frameIdx = 0;
    private float frameTimer = 0;
    private static final float FRAME_DURATION = 0.08f;  // 80ms per frame
    
    // Rotation animation
    private float rotationAngle = 0;
    private static final float ROTATION_SPEED = 4.0f;   // radians per second
    
    private boolean collected = false;
    
    public Card(float px, float py, BufferedImage[] cardFrames) {
        this.x = px;
        this.y = py;
        this.frames = cardFrames;
        this.lifetime = 0;
        
        // Spawn with upward velocity
        this.vx = (float)(Math.random() - 0.5f) * 100;  // -50 to +50
        this.vy = -200f;  // upward
    }
    
    public void update(float delta) {
        if (collected) return;
        
        lifetime += delta;
        
        // Physics
        x += vx * delta;
        y += vy * delta;
        vy += GRAVITY * delta;  // gravity
        
        // Animation
        frameTimer += delta;
        if (frameTimer >= FRAME_DURATION && frames != null && frames.length > 0) {
            frameTimer -= FRAME_DURATION;
            frameIdx = (frameIdx + 1) % frames.length;
        }
        
        // Rotation
        rotationAngle += ROTATION_SPEED * delta;
        
        // Fade out after DURATION
        if (lifetime >= DURATION) {
            collected = true;  // mark for removal
        }
    }
    
    public void render(Graphics2D g, int camX, int camY) {
        if (collected || frames == null || frames.length == 0) return;
        
        int sx = (int)(x - camX);
        int sy = (int)(y - camY);
        
        BufferedImage img = frames[frameIdx];
        if (img == null) return;
        
        int w = img.getWidth();
        int h = img.getHeight();
        
        // Draw with rotation
        g.translate(sx + w/2, sy + h/2);
        g.rotate(rotationAngle);
        g.drawImage(img, -w/2, -h/2, w, h, null);
        g.rotate(-rotationAngle);
        g.translate(-(sx + w/2), -(sy + h/2));
    }
    
    // Check if player can collect this card
    public boolean canCollect(float playerX, float playerY) {
        float dx = playerX - x;
        float dy = playerY - y;
        float dist = (float)Math.sqrt(dx*dx + dy*dy);
        return dist < PICKUP_RANGE && !collected;
    }
    
    public void collect() {
        collected = true;
    }
    
    // ===== GETTERS =====
    public boolean isCollected() { return collected; }
    public float getX() { return x; }
    public float getY() { return y; }
    public boolean isAlive() { return lifetime < DURATION; }
}
