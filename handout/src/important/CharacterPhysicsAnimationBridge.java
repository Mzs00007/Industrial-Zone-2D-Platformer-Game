package important;
import game2D.*;

import managers.Core;
import managers.GameplayAnimationController;
import vfx.GameplayVFXCoordinator;

/**
 * PHASE 3: PHYSICS-ANIMATION COUPLING BRIDGE
 * 
 * Links physics calculations (velocity, collision, knockback) to animation state changes.
 * Ensures character animation matches actual physical movement.
 * 
 * Physics to Animation Mapping:
 * - velocity.x = 0.0     → IDLE
 * - velocity.x ≠ 0.0     → WALK (on ground)
 * - velocity.x > 5.0 + SPRINT → RUN
 * - velocity.y < 0.0     → JUMP (rising)
 * - velocity.y > 0.0     → JUMP (falling)
 * - velocity.y > 10.0    → FALLING (terminal velocity)
 * - isGrounded = false   → JUMP state
 * - collision detected   → HURT animation + knockback
 * 
 * Timeline: Physics updates (60fps) → Animation sync (16ms intervals)
 * Integration Points: Called from physics system collision detection
 */
public class CharacterPhysicsAnimationBridge {
    
    // ===== CHARACTER PHYSICS PROFILE =====
    /**
     * Contains all physics parameters for character movement.
     */
    public static class CharacterPhysicsProfile {
        // Velocity components
        public float velocityX = 0.0f;
        public float velocityY = 0.0f;
        
        // Acceleration due to gravity
        public static final float GRAVITY = 0.163f;  // 9.8 m/s² ÷ 60fps
        
        // Movement constraints
        public static final float MAX_SPEED_WALK = 5.0f;
        public static final float MAX_SPEED_RUN = 7.5f;
        public static final float JUMP_FORCE = -12.0f;  // Negative = upward
        public static final float TERMINAL_VELOCITY = 15.0f;
        
        // Friction (smooth deceleration)
        public static final float FRICTION = 0.85f;
        public static final float AIR_FRICTION = 0.95f;
        
        // Position in world
        public float x = 0.0f;
        public float y = 0.0f;
        
        // Collision state
        public boolean isGrounded = true;
        public boolean isCollidingLeft = false;
        public boolean isCollidingRight = false;
        public boolean isCollidingTop = false;
        public boolean isCollidingBottom = false;
        
        // Knockback state
        public float knockbackX = 0.0f;
        public float knockbackY = 0.0f;
        public long knockbackDurationMs = 0;
        public long knockbackStartTimeMs = 0;
    }
    
    // ===== KNOCKBACK DATA =====
    /**
     * Stores knockback parameters for when character is hit.
     */
    public class KnockbackData {
        public float forceX = 0.0f;      // Horizontal knockback
        public float forceY = 0.0f;      // Vertical knockback
        public long durationMs = 200;   // How long knockback persists
        public boolean isActive = false;
        
        public KnockbackData(float forceX, float forceY, long durationMs) {
            this.forceX = forceX;
            this.forceY = forceY;
            this.durationMs = durationMs;
            this.isActive = true;
        }
    }
    
    // ===== INSTANCE VARIABLES =====
    private GameplayAnimationController animationController;
    private CharacterPhysicsProfile physicsProfile;
    private GameplayVFXCoordinator vfxCoordinator;
    private KnockbackData currentKnockback = null;
    
    // State tracking
    private GameplayAnimationController.CharacterState previousAnimationState = null;
    private boolean wasGrounded = true;
    private float lastVelocityX = 0.0f;
    
    // ===== CONSTRUCTOR =====
    public CharacterPhysicsAnimationBridge(GameplayAnimationController animationController,
                                          CharacterPhysicsProfile physicsProfile,
                                          GameplayVFXCoordinator vfxCoordinator) {
        this.animationController = animationController;
        this.physicsProfile = physicsProfile;
        this.vfxCoordinator = vfxCoordinator;
        
        Core.logger.info("[PhysicsAnimationBridge] Initialized - Syncing physics to animation");
    }
    
    // ===== CORE UPDATE =====
    
    /**
     * Called every frame from physics system.
     * Updates animation based on physical state changes.
     */
    public void update(long deltaTimeMs) {
        // Check for state changes that require animation updates
        updateAnimationFromPhysics();
        
        // Apply knockback if active
        if (currentKnockback != null && currentKnockback.isActive) {
            updateKnockback(deltaTimeMs);
        }
        
        // Update friction/deceleration
        applyFriction(deltaTimeMs);
        
        // Track state for next frame
        lastVelocityX = physicsProfile.velocityX;
        wasGrounded = physicsProfile.isGrounded;
    }
    
    // ===== ANIMATION SYNCHRONIZATION =====
    
    /**
     * Update animation state based on physics state.
     * Called every frame to keep animation synchronized with movement.
     */
    private void updateAnimationFromPhysics() {
        GameplayAnimationController.CharacterState targetState;
        
        // Determine target animation state from physics
        if (!physicsProfile.isGrounded) {
            // In air - use JUMP animation
            targetState = GameplayAnimationController.CharacterState.JUMP;
        } else if (Math.abs(physicsProfile.velocityX) < 0.1f) {
            // Standing still (minimal velocity)
            targetState = GameplayAnimationController.CharacterState.IDLE;
        } else if (Math.abs(physicsProfile.velocityX) > 5.0f) {
            // Moving fast (sprint)
            targetState = GameplayAnimationController.CharacterState.RUN;
        } else {
            // Moving slowly (walk)
            targetState = GameplayAnimationController.CharacterState.WALK;
        }
        
        // Only change if different
        if (targetState != animationController.getCurrentState()) {
            animationController.setCharacterState(targetState);
            Core.logger.debug("[PhysicsAnimationBridge] Animation changed to " + targetState.name() + 
                             " (velocity.x = " + physicsProfile.velocityX + ")");
        }
        
        // Update facing direction based on movement
        if (physicsProfile.velocityX > 0.1f) {
            animationController.setFacingRight(true);
        } else if (physicsProfile.velocityX < -0.1f) {
            animationController.setFacingRight(false);
        }
        
        previousAnimationState = targetState;
    }
    
    // ===== GRAVITY & JUMPING =====
    
    /**
     * Apply gravity to vertical velocity.
     * Called every physics frame.
     */
    public void applyGravity(long deltaTimeMs) {
        if (!physicsProfile.isGrounded) {
            // Falling or jumping
            physicsProfile.velocityY += CharacterPhysicsProfile.GRAVITY * deltaTimeMs / 16.0f;
            
            // Terminal velocity cap
            if (physicsProfile.velocityY > CharacterPhysicsProfile.TERMINAL_VELOCITY) {
                physicsProfile.velocityY = CharacterPhysicsProfile.TERMINAL_VELOCITY;
            }
        } else {
            // On ground - reset vertical velocity
            physicsProfile.velocityY = 0.0f;
        }
    }
    
    /**
     * Apply friction/deceleration based on movement.
     * Smooth stop when no input.
     */
    private void applyFriction(long deltaTimeMs) {
        if (physicsProfile.isGrounded && Math.abs(physicsProfile.velocityX) > 0.1f) {
            // Apply friction when on ground
            physicsProfile.velocityX *= CharacterPhysicsProfile.FRICTION;
        } else if (!physicsProfile.isGrounded) {
            // Apply air friction (less friction in air)
            physicsProfile.velocityX *= CharacterPhysicsProfile.AIR_FRICTION;
        }
    }
    
    /**
     * Apply jump physics.
     * Called when jump input received.
     */
    public void applyJumpForce() {
        if (physicsProfile.isGrounded) {
            physicsProfile.velocityY = CharacterPhysicsProfile.JUMP_FORCE;
            physicsProfile.isGrounded = false;
            Core.logger.debug("[PhysicsAnimationBridge] Jump applied - velocity.y = " + 
                             CharacterPhysicsProfile.JUMP_FORCE);
        }
    }
    
    // ===== COLLISION DETECTION =====
    
    /**
     * Called when collision with object/enemy detected.
     * Triggers HURT animation and knockback.
     */
    public void onCollisionWithEnemy(float damageForce, boolean isKnockbackLeft) {
        // Calculate knockback direction based on collision
        float knockbackX = isKnockbackLeft ? -damageForce : damageForce;
        float knockbackY = -2.0f;  // Slight upward knockback
        
        // Apply knockback
        applyKnockback(knockbackX, knockbackY, 300);  // 300ms knockback duration
        
        // Trigger HURT animation
        animationController.setCharacterState(GameplayAnimationController.CharacterState.HURT);
        
        // Spawn blood VFX at character position
        if (vfxCoordinator != null) {
            vfxCoordinator.spawnVFX(GameplayVFXCoordinator.VFXType.BLOOD_SPLATTER,
                                   physicsProfile.x, physicsProfile.y + 10);
        }
        
        Core.logger.debug("[PhysicsAnimationBridge] Enemy collision - Knockback applied: " + 
                         knockbackX + ", " + knockbackY);
    }
    
    /**
     * Called when collision with ground detected.
     */
    public void onGroundCollision() {
        boolean wasAirborne = !wasGrounded;
        physicsProfile.isGrounded = true;
        
        // If just landed from jump, trigger landing effects
        if (wasAirborne) {
            Core.logger.debug("[PhysicsAnimationBridge] Player landed - Grounded = true");
            
            // Spawn landing particles
            if (vfxCoordinator != null) {
                vfxCoordinator.spawnVFX(GameplayVFXCoordinator.VFXType.FOOTSTEP_PARTICLES,
                                       physicsProfile.x, physicsProfile.y + 20);
            }
            
            // Trigger animation update
            updateAnimationFromPhysics();
        }
    }
    
    /**
     * Called when collision with wall/obstacle detected.
     */
    public void onWallCollision(boolean collisionLeft) {
        if (collisionLeft) {
            physicsProfile.velocityX = 0.0f;
            physicsProfile.isCollidingLeft = true;
            Core.logger.debug("[PhysicsAnimationBridge] Wall collision on LEFT");
        } else {
            physicsProfile.velocityX = 0.0f;
            physicsProfile.isCollidingRight = true;
            Core.logger.debug("[PhysicsAnimationBridge] Wall collision on RIGHT");
        }
    }
    
    // ===== KNOCKBACK MANAGEMENT =====
    
    /**
     * Apply knockback force to character.
     * Used when hit by enemy or impact effect.
     */
    public void applyKnockback(float forceX, float forceY, long durationMs) {
        currentKnockback = new KnockbackData(forceX, forceY, durationMs);
        currentKnockback.isActive = true;
        Core.logger.debug("[PhysicsAnimationBridge] Knockback applied: " + forceX + ", " + forceY);
    }
    
    /**
     * Update knockback effect each frame.
     */
    private void updateKnockback(long deltaTimeMs) {
        if (currentKnockback == null) {
            return;
        }
        
        long elapsedMs = System.currentTimeMillis() - currentKnockback.knockbackStartTimeMs;
        
        // Apply knockback with decay
        float decayFactor = Math.max(0.0f, 1.0f - (float)elapsedMs / currentKnockback.durationMs);
        
        // Apply forces
        physicsProfile.velocityX += currentKnockback.forceX * decayFactor;
        physicsProfile.velocityY += currentKnockback.forceY * decayFactor;
        
        // Check if knockback finished
        if (elapsedMs >= currentKnockback.durationMs) {
            currentKnockback.isActive = false;
            currentKnockback = null;
            Core.logger.debug("[PhysicsAnimationBridge] Knockback ended");
        }
    }
    
    // ===== DAMAGE & DEATH =====
    
    /**
     * Trigger death animation and sequence.
     */
    public void onDeath() {
        // Stop all movement
        physicsProfile.velocityX = 0.0f;
        physicsProfile.velocityY = 0.0f;
        
        // Play death animation
        animationController.setCharacterState(GameplayAnimationController.CharacterState.DEATH);
        
        // Spawn death particles
        if (vfxCoordinator != null) {
            vfxCoordinator.spawnVFX(GameplayVFXCoordinator.VFXType.BLOOD_SPLATTER,
                                   physicsProfile.x, physicsProfile.y);
        }
        
        Core.logger.info("[PhysicsAnimationBridge] Player died - Death animation triggered");
    }
    
    // ===== POSITION UPDATES =====
    
    /**
     * Update character position based on velocity.
     * Called from physics system update cycle.
     */
    public void updatePosition(long deltaTimeMs) {
        // Update X position
        float deltaSeconds = deltaTimeMs / 1000.0f;
        physicsProfile.x += physicsProfile.velocityX * deltaSeconds;
        
        // Update Y position (falling/jumping)
        if (!physicsProfile.isGrounded) {
            physicsProfile.y += physicsProfile.velocityY * deltaSeconds;
        }
    }
    
    // ===== GETTERS =====
    public CharacterPhysicsProfile getPhysicsProfile() {
        return physicsProfile;
    }
    
    public boolean isKnockedBack() {
        return currentKnockback != null && currentKnockback.isActive;
    }
    
    public boolean isJumping() {
        return !physicsProfile.isGrounded;
    }
    
    public float getCurrentVelocityX() {
        return physicsProfile.velocityX;
    }
    
    public float getCurrentVelocityY() {
        return physicsProfile.velocityY;
    }
    
    // ===== SETTERS =====
    public void setPhysicsProfile(CharacterPhysicsProfile profile) {
        this.physicsProfile = profile;
    }
    
    public void setGrounded(boolean grounded) {
        physicsProfile.isGrounded = grounded;
    }
    
    public void setPosition(float x, float y) {
        physicsProfile.x = x;
        physicsProfile.y = y;
    }
    
    // ===== DEBUG =====
    public String getDebugInfo() {
        return String.format(
            "Vel: (%.1f, %.1f) | Grnd: %s | KnkBck: %s",
            physicsProfile.velocityX,
            physicsProfile.velocityY,
            physicsProfile.isGrounded,
            isKnockedBack()
        );
    }
}
