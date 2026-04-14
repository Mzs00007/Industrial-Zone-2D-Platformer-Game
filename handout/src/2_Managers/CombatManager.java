package managers;

import combat.CombatSystem;
import java.util.HashMap;
import java.util.Map;

/**
 * CombatManager - Consolidated combat system
 * 
 * Merges:
 * - combat/CombatSystem.java
 * 
 * Manages combat calculations, damage, and combat mechanics.
 */
public class CombatManager {
    private CombatSystem combatSystem;
    private boolean isInitialized = false;
    
    /**
     * Constructor - instantiable manager (no static getInstance)
     */
    public CombatManager() {
        this.combatSystem = new CombatSystem();
    }
    
    /**
     * Initialize combat system
     */
    public void initialize() {
        if (isInitialized) return;
        try {
            System.out.println("[CombatManager] Initializing combat system...");
            combatSystem.initialize();
            isInitialized = true;
            System.out.println("[CombatManager] ✓ Combat system initialized");
        } catch (Exception e) {
            System.err.println("[CombatManager ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Update combat state
     */
    public void update(long deltaMillis) {
        if (!isInitialized) return;
        combatSystem.update(deltaMillis);
    }
    
    /**
     * Calculate damage between two entities
     */
    public float calculateDamage(Object attacker, Object defender) {
        if (combatSystem != null) {
            return combatSystem.calculateDamage(attacker, defender);
        }
        return 0f;
    }
    
    /**
     * Apply damage to target
     */
    public void applyDamage(Object target, float damage) {
        if (combatSystem != null) {
            combatSystem.applyDamage(target, damage);
        }
    }
    
    /**
     * Check if hit is critical
     */
    public boolean isCriticalHit(Object attacker, Object defender) {
        if (combatSystem != null) {
            return combatSystem.isCriticalHit(attacker, defender);
        }
        return false;
    }
    
    /**
     * Register entity in combat system
     */
    public void registerEntity(Object entity) {
        if (combatSystem != null) {
            combatSystem.registerEntity(entity);
        }
    }
    
    /**
     * Unregister entity from combat system
     */
    public void unregisterEntity(Object entity) {
        if (combatSystem != null) {
            combatSystem.unregisterEntity(entity);
        }
    }
    
    /**
     * Shutdown combat system
     */
    public void shutdown() {
        if (combatSystem != null) {
            combatSystem.shutdown();
        }
        isInitialized = false;
    }
}
