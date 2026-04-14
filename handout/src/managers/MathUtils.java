package managers;

/**
 * STATIC UTILITIES - Shared by ALL entities
 * 
 * ✅ WRITE ONCE, USE EVERYWHERE:
 * - PlayerController, EnemyController, BossController
 * - All use the SAME MathUtils methods
 * - No duplication of math functions
 * 
 * ✅ BENEFITS:
 * - Bug fix once → fixes all entities
 * - Optimization once → benefits all entities
 * - Consistent implementation everywhere
 */
public class MathUtils {
    
    /**
     * Calculate distance between two points
     * Used by:
     * - PlayerController: Check distance to enemies
     * - EnemyController: Check distance to player, patrol boundaries
     * - BossController: Calculate range for special attacks
     */
    public static float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Linear interpolation
     * Used by:
     * - Animation: Smooth transitions between frames
     * - AI: Smooth movement changes
     * - Camera: Smooth following of player
     */
    public static float lerp(float a, float b, float t) {
        // Clamp t between 0 and 1
        t = Math.max(0, Math.min(1, t));
        return a + (b - a) * t;
    }
    
    /**
     * Clamp value between min and max
     * Used by:
     * - Health: Keep health between 0 and maxHealth
     * - Velocity: Keep velocity under terminal velocity
     * - Animation: Keep animation frame between 0 and maxFrames
     */
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
    
    /**
     * Check if point is in rectangle
     * Used by:
     * - Collision detection
     * - UI button clicking
     * - Projectile hit detection
     */
    public static boolean pointInRect(float px, float py, float rx, float ry, float rw, float rh) {
        return px >= rx && px <= rx + rw && py >= ry && py <= ry + rh;
    }
    
    /**
     * Check if two rectangles collide
     * Used by:
     * - Player collision with enemies
     * - Player collision with projectiles
     * - Platform collision detection
     */
    public static boolean rectCollides(
            float x1, float y1, float w1, float h1,
            float x2, float y2, float w2, float h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 
            && y1 < y2 + h2 && y1 + h1 > y2;
    }
    
    /**
     * Normalize vector (unit vector)
     * Used by:
     * - Movement direction calculation
     * - Projectile direction
     * - AI pathfinding
     */
    public static float[] normalize(float x, float y) {
        float len = (float) Math.sqrt(x * x + y * y);
        if (len == 0) return new float[]{0, 0};
        return new float[]{x / len, y / len};
    }
    
    /**
     * Angle between two points (in radians)
     * Used by:
     * - Boss attack direction
     * - Enemy aim
     * - Camera rotation
     */
    public static float angleBetween(float x1, float y1, float x2, float y2) {
        return (float) Math.atan2(y2 - y1, x2 - x1);
    }
    
    /**
     * Random between min and max
     * Used by:
     * - Enemy spawn positions
     * - Projectile spread
     * - AI decision making
     */
    public static float random(float min, float max) {
        return min + (float) Math.random() * (max - min);
    }
    
    /**
     * Smooth interpolation (easing)
     * Used by:
     * - Animation curves
     * - Camera following (smoothing)
     * - UI transitions
     */
    public static float smoothstep(float edge0, float edge1, float x) {
        x = clamp((x - edge0) / (edge1 - edge0), 0, 1);
        return x * x * (3 - 2 * x);
    }
}
