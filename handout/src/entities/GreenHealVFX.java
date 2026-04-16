package entities;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * GreenHealVFX - Green particle effect for healing
 * Features:
 *   - Green sparkle particles
 *   - Emit upward from player position
 *   - Fade out over 1 second
 *   - Auto-expire when done
 */
public class GreenHealVFX {

    private float x, y;              // Center position
    private float lifetime;          // Current lifetime (0 -> duration)
    private static final float DURATION = 1.0f;  // 1 second
    
    // Particle data
    private Particle[] particles;
    private static final int PARTICLE_COUNT = 12;
    
    private class Particle {
        float x, y;
        float vx, vy;  // velocity
        float life;    // 0-1
        float size;
    }
    
    public GreenHealVFX(float px, float py) {
        this.x = px;
        this.y = py;
        this.lifetime = 0;
        
        // Create particles
        particles = new Particle[PARTICLE_COUNT];
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            particles[i] = new Particle();
            initParticle(particles[i]);
        }
    }
    
    private void initParticle(Particle p) {
        p.x = x + (float)(Math.random() - 0.5f) * 40;
        p.y = y + (float)(Math.random() - 0.5f) * 40;
        
        // Radial outward + upward velocity
        double angle = Math.random() * Math.PI * 2;
        float speed = 100f + (float)(Math.random() * 150f);
        p.vx = (float)(Math.cos(angle) * speed);
        p.vy = (float)(Math.sin(angle) * speed) - 80f;  // bias upward
        
        p.life = 1f;
        p.size = 4f + (float)(Math.random() * 2f);
    }
    
    public void update(float delta) {
        lifetime += delta;
        
        for (Particle p : particles) {
            // Physics
            p.x += p.vx * delta;
            p.y += p.vy * delta;
            p.vy += 120f * delta;  // gravity
            
            // Fade out
            float progress = lifetime / DURATION;
            p.life = Math.max(0, 1f - progress);
        }
    }
    
    public void render(Graphics2D g, int camX, int camY) {
        for (Particle p : particles) {
            if (p.life <= 0) continue;
            
            int sx = (int)(p.x - camX);
            int sy = (int)(p.y - camY);
            int size = (int)p.size;
            
            // Green with alpha fade
            int alpha = (int)(p.life * 200);
            Color c = new Color(100, 255, 150, alpha);
            g.setColor(c);
            g.fillOval(sx - size/2, sy - size/2, size, size);
            
            // Bright border
            g.setColor(new Color(150, 255, 200, alpha));
            g.drawOval(sx - size/2, sy - size/2, size, size);
        }
    }
    
    public boolean isAlive() {
        return lifetime < DURATION;
    }
}
