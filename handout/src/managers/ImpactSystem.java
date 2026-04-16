package managers;

import rendering.VfxSystem;
import java.util.*;

/**
 * ImpactSystem — Unified impact effect management
 * 
 * When character/enemy/projectile collides, triggers:
 * - Sound effect (impact sound based on type)
 * - Damage (calculated from attacker power)
 * - Knockback/Pushback (physics velocity change)
 * - VFX Effect (blood splatter, sparks, etc.)
 * 
 * Replaces scattered collision handlers with centralized system.
 */
public class ImpactSystem {
    
    private static final String TAG = "[ImpactSystem]";
    
    private VfxSystem vfxSystem;
    private AudioManager audioManager;
    private Random rng = new Random();
    
    // Impact type definitions
    public enum ImpactType {
        MELEE_PUNCH(15, 200, 300, "punch_impact", "blood"),
        MELEE_SLASH(18, 250, 350, "slash_impact", "blood"),
        PROJECTILE_SMALL(12, 180, 250, "impact_small", "spark"),
        PROJECTILE_MEDIUM(18, 220, 400, "impact_medium", "blood"),
        PROJECTILE_LARGE(25, 300, 500, "impact_large", "explosion"),
        EXPLOSION(30, 400, 600, "explosion", "explosion"),
        TRAP_SPIKE(20, 150, 200, "trap_impact", "blood"),
        TRAP_HAMMER(22, 280, 400, "hammer_impact", "blood"),
        ENVIRONMENTAL_HAZARD(15, 100, 150, "hazard_impact", "spark"),
        BOSS_ATTACK(35, 350, 500, "boss_impact", "blood_heavy");
        
        public final int baseDamage;
        public final int pushbackForce;      // Horizontal force (px/s)
        public final int knockbackDuration;  // Milliseconds
        public final String soundEffect;
        public final String vfxType;
        
        ImpactType(int damage, int pushback, int duration, String sound, String vfx) {
            this.baseDamage = damage;
            this.pushbackForce = pushback;
            this.knockbackDuration = duration;
            this.soundEffect = sound;
            this.vfxType = vfx;
        }
    }
    
    // Damage modifiers
    public static class DamageContext {
        public ImpactType impactType;
        public float attackerStrength;  // 0.5 to 2.0 multiplier
        public float defenderArmor;     // 0 to 1.0 reduction factor
        public float distanceFalloff;   // 0.0 to 1.0 (for area effects)
        public boolean isCritical;      // Critical hit (1.5x damage)
        
        public DamageContext(ImpactType type, float strength) {
            this.impactType = type;
            this.attackerStrength = strength;
            this.defenderArmor = 0.0f;
            this.distanceFalloff = 1.0f;
            this.isCritical = false;
        }
    }
    
    // Constructor
    public ImpactSystem(VfxSystem vfxSystem, AudioManager audioManager) {
        this.vfxSystem = vfxSystem;
        this.audioManager = audioManager;
    }
    
    /**
     * Main impact trigger - handles all collision effects
     * 
     * @param impactType Type of impact (determines damage/sound/vfx)
     * @param impactX World X coordinate of impact
     * @param impactY World Y coordinate of impact
     * @param context Additional damage/force modifiers
     * @param isFromLeft Direction character is coming from (for knockback)
     */
    public ImpactResult trigger(ImpactType impactType, float impactX, float impactY, 
                                DamageContext context, boolean isFromLeft) {
        ImpactResult result = new ImpactResult();
        
        // 1. CALCULATE DAMAGE
        int damage = calculateDamage(context);
        result.damage = damage;
        
        // 2. CALCULATE KNOCKBACK
        float knockbackX = calculateKnockbackX(impactType, isFromLeft);
        float knockbackY = calculateKnockbackY(impactType);
        result.knockbackX = knockbackX;
        result.knockbackY = knockbackY;
        result.knockbackDuration = impactType.knockbackDuration;
        
        // 3. EMIT SOUND
        playImpactSound(impactType);
        
        // 4. EMIT VFX
        emitImpactVfx(impactType, impactX, impactY, damage);
        
        // 5. LOG IMPACT
        logImpact(impactType, impactX, impactY, damage, knockbackX, knockbackY);
        
        result.success = true;
        return result;
    }
    
    /**
     * Calculate final damage with modifiers
     */
    private int calculateDamage(DamageContext ctx) {
        int baseDamage = ctx.impactType.baseDamage;
        
        // Apply attacker strength modifier
        float damageWithStrength = baseDamage * ctx.attackerStrength;
        
        // Apply armor reduction
        float armorReduction = 1.0f - ctx.defenderArmor;
        float damageAfterArmor = damageWithStrength * armorReduction;
        
        // Apply distance falloff (for area effects)
        float finalDamage = damageAfterArmor * ctx.distanceFalloff;
        
        // Apply critical hit multiplier
        if (ctx.isCritical) {
            finalDamage *= 1.5f;
        }
        
        return Math.max(1, (int) finalDamage);
    }
    
    /**
     * Calculate knockback horizontal force (considering direction)
     */
    private float calculateKnockbackX(ImpactType impactType, boolean isFromLeft) {
        float force = impactType.pushbackForce;
        // Randomize slightly ±10% for variety
        float variation = 1.0f + (rng.nextFloat() - 0.5f) * 0.2f;
        force *= variation;
        return isFromLeft ? -force : force;
    }
    
    /**
     * Calculate knockback vertical force
     */
    private float calculateKnockbackY(ImpactType impactType) {
        // Most impacts push upward slightly
        return -150f;  // Negative = upward
    }
    
    /**
     * Play impact sound effect
     */
    private void playImpactSound(ImpactType impactType) {
        if (audioManager == null) return;
        
        try {
            audioManager.playSoundEffect(impactType.soundEffect);
        } catch (Exception e) {
            System.out.println(TAG + " Failed to play sound: " + impactType.soundEffect);
        }
    }
    
    /**
     * Emit visual effects for impact
     */
    private void emitImpactVfx(ImpactType impactType, float x, float y, int damage) {
        if (vfxSystem == null) return;
        
        switch (impactType.vfxType) {
            case "blood" -> {
                vfxSystem.emitBlood(x, y);
                // Add secondary spark burst for emphasis
                vfxSystem.emitHitSparks(x + 10, y + 10);
            }
            case "blood_heavy" -> {
                // Boss attacks: emit multiple blood splatters
                for (int i = 0; i < 3; i++) {
                    vfxSystem.emitBlood(x + rng.nextFloat() * 20 - 10, y + rng.nextFloat() * 10);
                }
                vfxSystem.emitHitSparks(x, y);
            }
            case "spark" -> {
                vfxSystem.emitHitSparks(x, y);
            }
            case "explosion" -> {
                // Emit multiple spark bursts for explosion effect
                for (int i = 0; i < 2; i++) {
                    vfxSystem.emitHitSparks(x, y);
                }
            }
        }
    }
    
    /**
     * Log impact for debugging
     */
    private void logImpact(ImpactType type, float x, float y, int damage, 
                          float kbX, float kbY) {
        System.out.println(TAG + " [" + type.name() + "] " +
                         "pos=(" + (int)x + "," + (int)y + ") " +
                         "damage=" + damage + " " +
                         "knockback=(" + (int)kbX + "," + (int)kbY + ")");
    }
    
    /**
     * Result of impact calculation
     */
    public static class ImpactResult {
        public boolean success = false;
        public int damage = 0;
        public float knockbackX = 0;
        public float knockbackY = 0;
        public int knockbackDuration = 0;
        
        @Override
        public String toString() {
            return "ImpactResult{" +
                   "damage=" + damage +
                   ", kb=(" + (int)knockbackX + "," + (int)knockbackY + ")" +
                   ", duration=" + knockbackDuration + "ms" +
                   '}';
        }
    }
}
