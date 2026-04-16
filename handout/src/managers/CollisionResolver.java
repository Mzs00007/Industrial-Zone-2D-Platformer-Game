package managers;

import entities.PlayerBase;
import entities.AnimatedObject;

/**
 * CollisionResolver — Enhanced collision detection with impact integration
 * 
 * Detects collisions between:
 * - Player ↔ Enemy
 * - Player ↔ Animated Objects (traps, hazards)
 * - Enemy ↔ Player projectiles
 * - Enemy ↔ Animated Objects
 * 
 * Applies ImpactSystem for consistent damage/sound/VFX/knockback feedback.
 */
public class CollisionResolver {
    
    private static final String TAG = "[CollisionResolver]";
    private ImpactSystem impactSystem;
    
    // Collision detection thresholds
    private static final float PLAYER_WIDTH = 64f;
    private static final float PLAYER_HEIGHT = 64f;
    private static final float COLLISION_SHRINK = 8f;  // Shrink hitbox for precision
    
    public CollisionResolver(ImpactSystem impactSystem) {
        this.impactSystem = impactSystem;
    }
    
    /**
     * Check AABB collision between two rectangles
     */
    public static boolean aabbOverlap(float x1, float y1, float w1, float h1,
                                      float x2, float y2, float w2, float h2) {
        return !(x1 + w1 <= x2 || x2 + w2 <= x1 ||
                y1 + h1 <= y2 || y2 + h2 <= y1);
    }
    
    /**
     * Detect player-enemy collision and apply impact
     */
    public boolean checkPlayerEnemyCollision(PlayerBase player, Object enemyObj) {
        // Check if object has required enemy methods (works with any Enemy class)
        if (!hasEnemyMethods(enemyObj)) {
            return false;
        }
        
        try {
            boolean isAlive = (Boolean) enemyObj.getClass().getMethod("isAlive").invoke(enemyObj);
            if (!player.isAlive() || !isAlive) return false;
            
            float px = player.getX() + COLLISION_SHRINK;
            float py = player.getY() + COLLISION_SHRINK;
            float pw = PLAYER_WIDTH - COLLISION_SHRINK * 2;
            float ph = PLAYER_HEIGHT - COLLISION_SHRINK * 2;
            
            float ex = (Float) enemyObj.getClass().getMethod("getX").invoke(enemyObj);
            float ey = (Float) enemyObj.getClass().getMethod("getY").invoke(enemyObj);
            float ew = (Float) enemyObj.getClass().getMethod("getWidth").invoke(enemyObj);
            float eh = (Float) enemyObj.getClass().getMethod("getHeight").invoke(enemyObj);
            
            if (!aabbOverlap(px, py, pw, ph, ex, ey, ew, eh)) {
                return false;  // No collision
            }
            
            // Collision detected - apply impact
            applyEnemyToPlayerImpact(player, enemyObj, px, py);
            return true;
        } catch (Exception e) {
            System.err.println(TAG + " Error checking enemy collision: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if object has required enemy methods
     */
    private boolean hasEnemyMethods(Object obj) {
        try {
            obj.getClass().getMethod("isAlive");
            obj.getClass().getMethod("getX");
            obj.getClass().getMethod("getY");
            obj.getClass().getMethod("getWidth");
            obj.getClass().getMethod("getHeight");
            obj.getClass().getMethod("takeDamage", int.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
    
    /**
     * Apply enemy attack impact to player
     */
    private void applyEnemyToPlayerImpact(PlayerBase player, Object enemyObj, 
                                         float impactX, float impactY) {
        try {
            // Determine attack type from enemy (default to melee punch)
            ImpactSystem.ImpactType impactType = ImpactSystem.ImpactType.MELEE_PUNCH;
            
            // Create damage context
            ImpactSystem.DamageContext context = new ImpactSystem.DamageContext(impactType, 1.0f);
            context.defenderArmor = 0.2f;  // Player has some natural armor
            
            // Determine direction (is enemy to left of player?)
            float ex = (Float) enemyObj.getClass().getMethod("getX").invoke(enemyObj);
            boolean isFromLeft = ex < player.getX();
            
            // Trigger impact
            ImpactSystem.ImpactResult result = impactSystem.trigger(
                impactType, impactX, impactY, context, isFromLeft
            );
            
            // Apply to player
            player.takeDamage(result.damage);
            player.applyKnockback(result.knockbackX, result.knockbackY, 
                                 result.knockbackDuration);
        } catch (Exception e) {
            System.err.println(TAG + " Error applying enemy impact: " + e.getMessage());
        }
    }
    
    /**
     * Detect player-animated object collision (traps, hazards)
     */
    public boolean checkPlayerObjectCollision(PlayerBase player, AnimatedObject obj) {
        if (!player.isAlive() || !obj.isActive()) return false;
        
        if (!obj.isDamaging()) return false;  // Non-damaging object
        
        float px = player.getX() + COLLISION_SHRINK;
        float py = player.getY() + COLLISION_SHRINK;
        float pw = PLAYER_WIDTH - COLLISION_SHRINK * 2;
        float ph = PLAYER_HEIGHT - COLLISION_SHRINK * 2;
        
        float ox = obj.getX();
        float oy = obj.getY();
        float ow = obj.getWidth();
        float oh = obj.getHeight();
        
        if (!aabbOverlap(px, py, pw, ph, ox, oy, ow, oh)) {
            return false;
        }
        
        // Collision detected - apply impact
        applyObjectToPlayerImpact(player, obj, px, py);
        return true;
    }
    
    /**
     * Apply trap/hazard impact to player
     */
    private void applyObjectToPlayerImpact(PlayerBase player, AnimatedObject obj, 
                                          float impactX, float impactY) {
        // Determine trap type from object properties
        ImpactSystem.ImpactType impactType = getObjectImpactType(obj);
        
        // Create damage context
        ImpactSystem.DamageContext context = new ImpactSystem.DamageContext(impactType, 1.0f);
        context.defenderArmor = 0.1f;  // Traps have less armor consideration
        
        // Traps push from their position
        boolean isFromLeft = obj.getX() < player.getX();
        
        // Trigger impact
        ImpactSystem.ImpactResult result = impactSystem.trigger(
            impactType, impactX, impactY, context, isFromLeft
        );
        
        // Apply to player
        player.takeDamage(result.damage);
        player.applyKnockback(result.knockbackX, result.knockbackY, 
                             result.knockbackDuration);
    }
    
    /**
     * Determine impact type from animated object based on type enum
     */
    private ImpactSystem.ImpactType getObjectImpactType(AnimatedObject obj) {
        // Get object type - ObjType has names like HAZARD_HAMMER, HAZARD_TURRET, etc.
        String typeName = obj.getType().toString();
        
        return switch (typeName) {
            case "HAZARD_HAMMER", "CRUSHER" -> ImpactSystem.ImpactType.TRAP_HAMMER;
            case "HAZARD_SPIKE" -> ImpactSystem.ImpactType.TRAP_SPIKE;
            case "HAZARD_TURRET" -> ImpactSystem.ImpactType.PROJECTILE_MEDIUM;
            default -> ImpactSystem.ImpactType.ENVIRONMENTAL_HAZARD;
        };
    }
    
    /**
     * Setup collision check with cooldown to prevent repeated triggers
     */
    public static class CollisionCooldown {
        private long lastCollisionTime = 0;
        private long cooldownMs = 300;  // Prevent same collision every frame
        
        public boolean canTrigger() {
            long now = System.currentTimeMillis();
            if (now - lastCollisionTime >= cooldownMs) {
                lastCollisionTime = now;
                return true;
            }
            return false;
        }
        
        public void setCooldown(long ms) {
            this.cooldownMs = ms;
        }
    }
}
