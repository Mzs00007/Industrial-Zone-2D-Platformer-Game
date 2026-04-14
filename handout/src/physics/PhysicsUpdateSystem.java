package physics;

/**
 * PHYSICS UPDATE SYSTEM
 * Handles gravity, velocity, acceleration, and physics calculations
 * Applied to player and all dynamic entities
 */
public class PhysicsUpdateSystem {
    
    // Physics constants
    public static final float GRAVITY = 980f;           // pixels/second²
    public static final float TERMINAL_VELOCITY = 700f; // max fall speed
    public static final float FRICTION = 0.85f;         // ground friction
    public static final float AIR_RESISTANCE = 0.95f;   // air drag
    
    /**
     * Apply gravity to an entity
     * @param velocityY Current Y velocity
     * @param deltaSeconds Time since last update
     * @return New Y velocity after gravity
     */
    public static float applyGravity(float velocityY, float deltaSeconds) {
        float newVelocity = velocityY + (GRAVITY * deltaSeconds);
        
        // Cap at terminal velocity
        if (newVelocity > TERMINAL_VELOCITY) {
            newVelocity = TERMINAL_VELOCITY;
        }
        
        return newVelocity;
    }
    
    /**
     * Apply friction when standing on ground
     * @param velocityX Current X velocity
     * @param deltaSeconds Time since last update
     * @return New X velocity with friction applied
     */
    public static float applyGroundFriction(float velocityX, float deltaSeconds) {
        return velocityX * FRICTION;
    }
    
    /**
     * Apply air resistance to movement
     * @param velocity Current velocity vector
     * @return Velocity with air resistance applied
     */
    public static float applyAirResistance(float velocity) {
        return velocity * AIR_RESISTANCE;
    }
    
    /**
     * Update position based on velocity
     * @param position Current position
     * @param velocity Current velocity
     * @param deltaSeconds Time since last update
     * @return New position
     */
    public static float updatePosition(float position, float velocity, float deltaSeconds) {
        return position + (velocity * deltaSeconds);
    }
    
    /**
     * Apply acceleration to velocity
     * @param velocity Current velocity
     * @param acceleration Acceleration to apply
     * @param deltaSeconds Time since last update
     * @return New velocity
     */
    public static float applyAcceleration(float velocity, float acceleration, float deltaSeconds) {
        return velocity + (acceleration * deltaSeconds);
    }
    
    /**
     * Clamp velocity to maximum speed
     * @param velocity Current velocity
     * @param maxSpeed Maximum allowed speed
     * @return Clamped velocity
     */
    public static float clampVelocity(float velocity, float maxSpeed) {
        if (Math.abs(velocity) > maxSpeed) {
            return velocity > 0 ? maxSpeed : -maxSpeed;
        }
        return velocity;
    }
    
    /**
     * Calculate jump velocity given jump height
     * @param jumpHeight Height to reach in pixels
     * @return Initial velocity needed for that height
     */
    public static float calculateJumpVelocity(float jumpHeight) {
        // v = sqrt(2 * g * h)
        return -(float)Math.sqrt(2 * GRAVITY * jumpHeight);
    }
    
    /**
     * Check if velocity indicates falling
     * @param velocityY Y velocity
     * @return True if falling
     */
    public static boolean isFalling(float velocityY) {
        return velocityY > 0;
    }
    
    /**
     * Stop movement completely
     * @return Zero velocity
     */
    public static float stopMovement() {
        return 0f;
    }
}
