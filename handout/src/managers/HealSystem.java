package managers;

import entities.PlayerBase;
import java.util.*;

/**
 * HealSystem - Manages player healing with cooldown tracking and VFX
 * Features:
 *   - H key triggers heal with 8s cooldown
 *   - HAPPY animation plays on successful heal
 *   - Heal amount: +20 HP (max 100)
 *   - Tracks cooldown progress for HUD display
 */
public class HealSystem {

    private PlayerBase player;
    private float cooldownProgress = 1f;  // 0-1, 1 = ready to heal
    
    public HealSystem(PlayerBase player) {
        this.player = player;
    }
    
    /**
     * Try to heal the player (respects cooldown)
     * Returns true if heal was successful
     */
    public boolean heal() {
        if (player == null) return false;
        return player.tryHeal();
    }
    
    /**
     * Update heal cooldown progress
     */
    public void update(float delta) {
        if (player == null) return;
        cooldownProgress = player.getHealCooldownPct();
    }
    
    // ===== GETTERS =====
    public boolean canHeal() { 
        return cooldownProgress >= 1f && player != null && player.isAlive(); 
    }
    
    public float getCooldownProgress() { 
        return cooldownProgress; 
    }
    
    public String getCooldownStatus() {
        if (cooldownProgress >= 1f) return "READY";
        int seconds = (int)((1f - cooldownProgress) * 8f);
        return seconds + "s";
    }
}
