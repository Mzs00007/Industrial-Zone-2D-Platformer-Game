package managers;

import managers.Core;
import managers.CoreSystem;
import managers.BossCombatPhaseManager;
import managers.BossController;
import managers.EnemyController;
import managers.EnemyWaveManager;
import managers.GameEntity;
import managers.GameplayEnhancementSystem;
import managers.GameState;
import managers.GameStateManager;
import managers.LevelDesignOptimizer;
import managers.PlayerController;
import managers.SystemsContainer;
import managers.SystemEnums;
import java.util.HashMap;
import java.util.Map;

/**
 * CoreManager - Consolidated core game system
 * 
 * Merges:
 * - core/Core.java
 * - core/CoreSystem.java
 * - core/BossCombatPhaseManager.java
 * - core/BossController.java
 * - core/EnemyController.java
 * - core/EnemyWaveManager.java
 * - core/GameEntity.java
 * - core/GameplayEnhancementSystem.java
 * - core/GameState.java
 * - core/GameStateManager.java
 * - core/LevelDesignOptimizer.java
 * - core/PlayerController.java
 * - core/SystemsContainer.java
 * - core/SystemEnums.java (14 core files)
 * 
 * Manages core game state, entities, gameplay mechanics, and boss systems.
 */
public class CoreManager {
    private CoreSystem coreSystem;
    private GameplayEnhancementSystem gameplaySystem;
    private BossController bossController;
    private EnemyController enemyController;
    private EnemyWaveManager waveManager;
    private PlayerController playerController;
    private GameStateManager gameStateManager;
    private boolean isInitialized = false;
    
    /**
     * Constructor - instantiable manager (no static getInstance)
     */
    public CoreManager() {
        this.coreSystem = new CoreSystem();
        this.gameplaySystem = new GameplayEnhancementSystem();
        this.bossController = new BossController();
        this.enemyController = new EnemyController();
        this.waveManager = new EnemyWaveManager();
        this.playerController = new PlayerController();
        this.gameStateManager = new GameStateManager();
    }
    
    /**
     * Initialize core system
     */
    public void initialize() {
        if (isInitialized) return;
        try {
            System.out.println("[CoreManager] Initializing core game systems...");
            coreSystem.initialize();
            gameplaySystem.initialize();
            bossController.initialize();
            enemyController.initialize();
            waveManager.initialize();
            playerController.initialize();
            gameStateManager.initialize();
            isInitialized = true;
            System.out.println("[CoreManager] ✓ Core systems initialized");
        } catch (Exception e) {
            System.err.println("[CoreManager ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Update core systems
     */
    public void update(long deltaMillis) {
        if (!isInitialized) return;
        try {
            coreSystem.update(deltaMillis);
            gameplaySystem.update(deltaMillis);
            bossController.update(deltaMillis);
            enemyController.update(deltaMillis);
            waveManager.update(deltaMillis);
            playerController.update(deltaMillis);
            gameStateManager.update(deltaMillis);
        } catch (Exception e) {
            System.err.println("[CoreManager ERROR] Update failed: " + e.getMessage());
        }
    }
    
    /**
     * Set difficulty level
     */
    public void setDifficulty(int difficulty) {
        if (gameplaySystem != null) {
            gameplaySystem.setDifficulty(difficulty);
        }
    }
    
    /**
     * Get difficulty level
     */
    public int getDifficulty() {
        if (gameplaySystem != null) {
            return gameplaySystem.getDifficulty();
        }
        return 2; // DIFFICULTY_NORMAL
    }
    
    /**
     * Get current game state
     */
    public GameState getGameState() {
        if (gameStateManager != null) {
            return gameStateManager.getGameState();
        }
        return null;
    }
    
    /**
     * Get boss controller
     */
    public BossController getBossController() {
        return bossController;
    }
    
    /**
     * Get enemy controller
     */
    public EnemyController getEnemyController() {
        return enemyController;
    }
    
    /**
     * Get enemy wave manager
     */
    public EnemyWaveManager getWaveManager() {
        return waveManager;
    }
    
    /**
     * Get player controller
     */
    public PlayerController getPlayerController() {
        return playerController;
    }
    
    /**
     * Get core system
     */
    public CoreSystem getCoreSystem() {
        return coreSystem;
    }
    
    /**
     * Get gameplay enhancement system
     */
    public GameplayEnhancementSystem getGameplaySystem() {
        return gameplaySystem;
    }
    
    /**
     * Register game entity
     */
    public void registerEntity(GameEntity entity) {
        if (coreSystem != null) {
            coreSystem.registerEntity(entity);
        }
    }
    
    /**
     * Unregister game entity
     */
    public void unregisterEntity(GameEntity entity) {
        if (coreSystem != null) {
            coreSystem.unregisterEntity(entity);
        }
    }
    
    /**
     * Shutdown core systems
     */
    public void shutdown() {
        try {
            if (coreSystem != null) coreSystem.shutdown();
            if (bossController != null) bossController.shutdown();
            if (enemyController != null) enemyController.shutdown();
            if (waveManager != null) waveManager.shutdown();
            if (playerController != null) playerController.shutdown();
            if (gameStateManager != null) gameStateManager.shutdown();
        } catch (Exception e) {
            System.err.println("[CoreManager ERROR] Shutdown failed: " + e.getMessage());
        }
        isInitialized = false;
    }
}
