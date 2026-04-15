package entities;

/*
 * Projectile - Bullet / energy shot fired by the player
 *
 * Renders as a glowing cyan rectangle (upgradeable to a sprite later).
 * Gravity is applied so shots arc slightly downwards.
 * The projectile expires after a fixed lifetime or when kill() is called.
 */

import java.awt.Color;
import java.awt.Graphics2D;

public class Projectile {

    // =========================================================================
    //  Constants
    // =========================================================================
    private static final int   W         = 10;
    private static final int   H         = 5;
    private static final float GRAVITY   = 120f;   // gentle arc (px/s²)
    private static final float MAX_SPEED = 620f;

    // =========================================================================
    //  Fields
    // =========================================================================
    private float x, y;
    private float velX, velY;
    private final float damage;
    private final long  spawnMs;
    private final float lifetimeSec;
    private boolean alive = true;

    // =========================================================================
    //  Constructor
    // =========================================================================
    /**
     * @param startX      world-space origin X
     * @param startY      world-space origin Y
     * @param velX        horizontal velocity (px/s)
     * @param velY        vertical velocity (px/s, negative = up)
     * @param damage      damage dealt on hit
     * @param lifetimeSec seconds before auto-expiry
     */
    public Projectile(float startX, float startY,
                      float velX, float velY,
                      float damage, float lifetimeSec) {
        this.x           = startX;
        this.y           = startY;
        this.velX        = velX;
        this.velY        = velY;
        this.damage      = damage;
        this.lifetimeSec = lifetimeSec;
        this.spawnMs     = System.currentTimeMillis();
    }

    // =========================================================================
    //  Update (delta in SECONDS)
    // =========================================================================
    public void update(float delta) {
        if (!alive) return;

        // Light gravity arc
        velY += GRAVITY * delta;
        if (velY > MAX_SPEED) velY = MAX_SPEED;

        x += velX * delta;
        y += velY * delta;

        // Expire if out-of-bounds vertically
        if (y > 1600 || y < -200) alive = false;
    }

    // =========================================================================
    //  Render
    // =========================================================================
    public void render(Graphics2D g, int camX, int camY) {
        if (!alive) return;

        int sx = (int)(x - camX);
        int sy = (int)(y - camY);

        // Glow: outer halo
        g.setColor(new Color(0, 200, 255, 60));
        g.fillOval(sx - 4, sy - 4, W + 8, H + 8);

        // Core shot
        g.setColor(new Color(0, 230, 255));
        g.fillRoundRect(sx, sy, W, H, 3, 3);

        // Bright centre highlight
        g.setColor(new Color(180, 255, 255, 200));
        g.fillRect(sx + 2, sy + 1, W - 4, H - 2);
    }

    // =========================================================================
    //  Lifetime check
    // =========================================================================
    public boolean isAlive() {
        if (!alive) return false;
        long elapsedMs = System.currentTimeMillis() - spawnMs;
        if (elapsedMs >= (long)(lifetimeSec * 1000f)) {
            alive = false;
            return false;
        }
        return true;
    }

    /** Immediately destroy the projectile (e.g. on hit). */
    public void kill()    { alive = false; }

    /** Alias for kill() — kept for compatibility. */
    public void destroy() { alive = false; }

    // =========================================================================
    //  Getters
    // =========================================================================
    public float getX()      { return x; }
    public float getY()      { return y; }
    public float getDamage() { return damage; }
    public int   getW()      { return W; }
    public int   getH()      { return H; }
}
