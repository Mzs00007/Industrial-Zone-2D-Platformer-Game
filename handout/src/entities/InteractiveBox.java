package entities;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

/**
 * InteractiveBox - Chest/Box that spawns cards on E-key interaction
 * Features:
 *   - Shows "Press E" prompt when near player
 *   - Opens to spawn 1-3 cards when interacted
 *   - Stays open (does not reset)
 *   - Has collision box
 */
public class InteractiveBox {
    
    private float x, y;
    private int width = 48, height = 48;
    
    private BufferedImage[] frames;
    private int frameIdx = 0;      // 0 = closed, 1+ = opening animation
    private float frameTimer = 0;
    private static final float FRAME_DURATION = 0.1f;
    private boolean opened = false;
    
    private float interactPromptTimer = 0;  // for blinking prompt
    private static final float INTERACT_RANGE = 80f;
    
    private int cardCount = 3;  // number of cards to spawn
    
    public InteractiveBox(float px, float py, BufferedImage[] boxFrames) {
        this.x = px;
        this.y = py;
        this.frames = boxFrames;
    }
    
    public void update(float delta) {
        // Animation
        if (opened && frameIdx < (frames != null ? frames.length - 1 : 0)) {
            frameTimer += delta;
            if (frameTimer >= FRAME_DURATION) {
                frameTimer -= FRAME_DURATION;
                frameIdx++;
            }
        }
        
        // Prompt blinking
        interactPromptTimer += delta;
        if (interactPromptTimer > 0.6f) {
            interactPromptTimer -= 0.6f;
        }
    }
    
    public void render(Graphics2D g, int camX, int camY) {
        if (frames == null || frames.length == 0) return;
        
        int sx = (int)(x - camX);
        int sy = (int)(y - camY);
        
        int idx = Math.min(frameIdx, frames.length - 1);
        BufferedImage img = frames[idx];
        if (img != null) {
            g.drawImage(img, sx, sy, width, height, null);
        }
    }
    
    public void drawInteractPrompt(Graphics2D g, float playerX, float playerY, int camX, int camY) {
        if (opened) return;  // No prompt after opening
        
        // Check distance to player
        float dx = playerX - x;
        float dy = playerY - y;
        float dist = (float)Math.sqrt(dx*dx + dy*dy);
        
        if (dist > INTERACT_RANGE) return;  // Too far
        
        // Blink effect
        if (interactPromptTimer < 0.3f) {
            int sx = (int)(x - camX) - 20;
            int sy = (int)(y - camY) - 40;
            
            g.setFont(new Font("Consolas", Font.BOLD, 12));
            g.setColor(new Color(100, 255, 150));
            g.drawString("[ E ]", sx, sy);
        }
    }
    
    // Interact with box (called when E is pressed)
    public void interact() {
        if (opened) return;
        opened = true;
        frameIdx = 1;  // Start opening animation
    }
    
    // Get cards to spawn (call only once when opened)
    public Card[] getCardsToSpawn(BufferedImage[] cardFrames) {
        if (!opened || frameIdx < frames.length - 1) {
            return new Card[0];  // Don't spawn until fully opened
        }
        
        // Spawn cards
        Card[] cards = new Card[cardCount];
        for (int i = 0; i < cardCount; i++) {
            float offsetX = (float)(Math.random() - 0.5f) * 60;
            cards[i] = new Card(x + width/2 + offsetX, y - 20, cardFrames);
        }
        return cards;
    }
    
    // ===== COLLISION =====
    public boolean intersects(float px, float py, float pw, float ph) {
        return !(px + pw < x || px > x + width ||
                 py + ph < y || py > y + height);
    }
    
    // ===== GETTERS =====
    public float getX() { return x; }
    public float getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isOpened() { return opened; }
    public float getInteractRange() { return INTERACT_RANGE; }
}
