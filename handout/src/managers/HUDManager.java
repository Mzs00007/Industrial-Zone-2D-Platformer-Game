package managers;

import entities.PlayerBase;
import entities.Enemy;
import java.util.List;

/**
 * HUDManager — Unified state management for all HUD elements
 * 
 * Tracks and provides access to:
 * - Player stats (health, name, level, weapons)
 * - Combat info (current target health, combo counter)
 * - Collectibles (money, cards)
 * - Area indicators (mini-map position, level name)
 * - Performance metrics (FPS)
 * 
 * All data is collected per frame and made available to HUD renderers
 */
public class HUDManager {

    // ===== PLAYER INFO =====
    private PlayerBase player;
    private String playerName = "UNKNOWN";
    private int currentLevel = 1;
    private int playerHealth = 100;
    private int playerMaxHealth = 100;
    private String equippedWeapon = "NONE";
    private int equipAmmoCurrent = 0;
    private int equipAmmoMax = 0;
    
    // ===== COLLECTIBLES =====
    private int cashCollected = 0;
    private int cardsCollected = 0;
    private int totalScore = 0;
    
    // ===== COMBAT INFO =====
    private Enemy targetEnemy = null;
    private int targetEnemyHealth = 0;
    private int targetEnemyMaxHealth = 0;
    private String targetEnemyType = "";
    private int comboCounter = 0;
    private float comboTimer = 0f;
    private static final float COMBO_DECAY_TIME = 2.5f;  // seconds
    
    // ===== AREA INFO =====
    private String levelName = "INDUSTRIAL ZONE ENTRY";
    private String areaName = "START ZONE";
    private int currentCheckpoint = 0;
    
    // ===== PERFORMANCE =====
    private int framesPerSecond = 60;
    private long lastFrameTimeMs = System.currentTimeMillis();
    private int frameCount = 0;
    
    // ===== COSMETICS =====
    private float hudAlpha = 1.0f;
    private boolean showDebugInfo = false;
    private boolean showComboDamage = true;
    
    // ===== CONSTRUCTOR =====
    public HUDManager(PlayerBase player, int levelId, String levelName, String startAreaName) {
        this.player = player;
        this.currentLevel = levelId;
        this.levelName = levelName;
        this.areaName = startAreaName;
        this.playerMaxHealth = 100;  // default, will be updated from player
        this.playerHealth = 100;
        updatePlayerInfo();
    }
    
    // ===== UPDATE CYCLE =====
    /** Called once per game frame to update all HUD data */
    public void update(float delta) {
        updatePlayerInfo();
        updateCombatInfo(delta);
        updatePerformance();
    }
    
    private void updatePlayerInfo() {
        if (player == null) return;
        
        // Basic stats
        playerHealth = player.getHealth();
        playerMaxHealth = player.getMaxHealth();
        
        // Character name from type
        PlayerBase.CharacterType charType = player.getCharacterType();
        if (charType != null) {
            playerName = charType.toString();
        }
        
        // Weapon info
        PlayerBase.WeaponType weapon = player.getActiveWeapon();
        if (weapon != null) {
            equippedWeapon = weapon.toString();
            equipAmmoCurrent = player.getActiveAmmo();
            equipAmmoMax = weapon.maxAmmo;
        } else {
            equippedWeapon = "NONE";
            equipAmmoCurrent = 0;
            equipAmmoMax = 0;
        }
        
        // Collectibles (if player has these methods)
        try {
            cashCollected = (Integer) player.getClass().getMethod("getCash").invoke(player);
            cardsCollected = (Integer) player.getClass().getMethod("getCards").invoke(player);
        } catch (Exception e) {
            // Methods don't exist or other issue - use defaults
        }
    }
    
    private void updateCombatInfo(float delta) {
        // Decay combo timer
        if (comboTimer > 0) {
            comboTimer -= delta;
            if (comboTimer <= 0) {
                comboCounter = 0;  // reset combo
                comboTimer = 0;
            }
        }
    }
    
    private void updatePerformance() {
        frameCount++;
        long now = System.currentTimeMillis();
        long elapsed = now - lastFrameTimeMs;
        
        // Update FPS every 500ms
        if (elapsed >= 500) {
            framesPerSecond = (int) (frameCount * 1000 / elapsed);
            frameCount = 0;
            lastFrameTimeMs = now;
        }
    }
    
    // ===== SETTERS FOR COMBAT/EVENTS =====
    public void setTargetEnemy(Enemy enemy) {
        targetEnemy = enemy;
        if (enemy != null) {
            targetEnemyHealth = enemy.getHealth();
            // For now, estimate max health as 100 (will be updated from player's tracking)
            targetEnemyMaxHealth = 100;
            // Get enemy type name from class
            targetEnemyType = "ENEMY";
        }
    }
    
    public void incrementCombo() {
        comboCounter++;
        comboTimer = COMBO_DECAY_TIME;
    }
    
    public void setAreaName(String area) {
        areaName = area;
    }
    
    public void setCheckpoint(int checkpointIndex) {
        currentCheckpoint = checkpointIndex;
    }
    
    public void setTotalScore(int score) {
        totalScore = score;
    }
    
    public void setShowDebugInfo(boolean show) {
        showDebugInfo = show;
    }
    
    // ===== GETTERS =====
    public String getPlayerName() { return playerName; }
    public int getCurrentLevel() { return currentLevel; }
    public int getPlayerHealth() { return playerHealth; }
    public int getPlayerMaxHealth() { return playerMaxHealth; }
    public float getHealthPercent() { return (float) playerHealth / playerMaxHealth; }
    
    public String getEquippedWeapon() { return equippedWeapon; }
    public int getEquipAmmoCurrent() { return equipAmmoCurrent; }
    public int getEquipAmmoMax() { return equipAmmoMax; }
    public String getAmmoDisplay() { 
        return equipAmmoCurrent >= 0 ? equipAmmoCurrent + "/" + equipAmmoMax : "∞";
    }
    
    public int getCashCollected() { return cashCollected; }
    public int getCardsCollected() { return cardsCollected; }
    public int getTotalScore() { return totalScore; }
    
    public String getLevelName() { return levelName; }
    public String getAreaName() { return areaName; }
    public int getCurrentCheckpoint() { return currentCheckpoint; }
    
    public Enemy getTargetEnemy() { return targetEnemy; }
    public int getTargetEnemyHealth() { return targetEnemyHealth; }
    public int getTargetEnemyMaxHealth() { return targetEnemyMaxHealth; }
    public float getTargetHealthPercent() {
        if (targetEnemyMaxHealth <= 0) return 0;
        return (float) targetEnemyHealth / targetEnemyMaxHealth;
    }
    public String getTargetEnemyType() { return targetEnemyType; }
    
    public int getComboCounter() { return comboCounter; }
    public float getComboTimer() { return comboTimer; }
    public boolean hasActiveCombo() { return comboCounter > 0; }
    
    public int getFramesPerSecond() { return framesPerSecond; }
    public float getHudAlpha() { return hudAlpha; }
    public boolean isShowingDebugInfo() { return showDebugInfo; }
    public boolean isShowingComboDamage() { return showComboDamage; }
}
