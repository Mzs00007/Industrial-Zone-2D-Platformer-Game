package framework;

import game2D.GameCore;
import managers.Config;
import utilities.AudioManager;
import managers.CameraManager;
import managers.CombatManager;
import managers.CoreManager;
import managers.EventManager;
import managers.ObjectiveManager;
import managers.OptimizationManager;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * GameFramework - Pure game loop foundation
 * 
 * RESPONSIBILITY: Core game loop, update/render coordination
 * 
 * This class contains ONLY:
 * - Game loop initialization
 * - Update/render cycle coordination
 * - Delta time calculation
 * - Input event handling (delegated)
 * 
 * DOES NOT contain:
 * - GUI initialization (delegated to GUIManager)
 * - Level management (delegated to LevelManager)
 * - Screen coordination (delegated to ScreenManager)
 * - Rendering implementation (delegated to specific renderers)
 */
public class GameFramework extends GameCore {
    
    // Framework state
    private long lastUpdateTime = 0;
    private float deltaTime = 0.0f;
    private boolean isRunning = true;
    
    // Dependency injection - all managers passed in
    private final LevelManager levelManager;
    private final GUIManager guiManager;
    private final ScreenManager screenManager;
    
    // Phase 4 System Managers (new integration)
    private final AudioManager audioManager;
    private final CameraManager cameraManager;
    private final CombatManager combatManager;
    private final ConfigManager configManager;
    private final CoreManager coreManager;
    private final EventManager eventManager;
    private final ObjectiveManager objectiveManager;
    private final OptimizationManager optimizationManager;
    
    /**
     * Constructor with dependency injection
     */
    public GameFramework(LevelManager levelManager, GUIManager guiManager, ScreenManager screenManager) {
        this.levelManager = levelManager;
        this.guiManager = guiManager;
        this.screenManager = screenManager;
        
        // Phase 4: Initialize all system managers
        this.audioManager = new AudioManager();
        this.cameraManager = new CameraManager();
        this.combatManager = new CombatManager();
        this.configManager = new ConfigManager();
        this.coreManager = new CoreManager();
        this.eventManager = new EventManager();
        this.objectiveManager = new ObjectiveManager();
        this.optimizationManager = new OptimizationManager();
        
        // JFrame setup only
        this.setTitle("CSCU9N6 Industrial Zone Platformer - Option A Architecture");
        this.setDefaultCloseOperation(3);
        this.setResizable(true);
    }
    
    /**
     * Entry point - creates framework with managers
     */
    public static void main(String[] args) {
        // Get screen dimensions
        java.awt.Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = dimension.width;
        int screenHeight = dimension.height;
        
        // Create managers
        LevelManager levelMgr = new LevelManager();
        GUIManager guiMgr = new GUIManager();
        ScreenManager screenMgr = new ScreenManager();
        
        // Create framework with DI
        GameFramework framework = new GameFramework(levelMgr, guiMgr, screenMgr);
        
        // Initialize all subsystems
        framework.initialize();
        
        // Start game loop
        framework.run(false, screenWidth, screenHeight);
    }
    
    /**
     * Initialize all game systems
     */
    private void initialize() {
        System.out.println("[GameFramework] Initializing game systems...");
        System.out.println("════════════════════════════════════════════════");
        
        // Phase 1: Initialize base framework managers
        levelManager.initialize();
        System.out.println("[GameFramework] ✓ Level system initialized");
        
        guiManager.initialize(this);
        System.out.println("[GameFramework] ✓ GUI system initialized");
        
        screenManager.initialize();
        System.out.println("[GameFramework] ✓ Screen system initialized");
        
        // Phase 4: Initialize system managers
        System.out.println("\n[GameFramework] Phase 4: Initializing system managers...");
        
        configManager.initialize();
        System.out.println("[GameFramework] ✓ Configuration manager initialized");
        
        audioManager.initialize();
        System.out.println("[GameFramework] ✓ Audio manager initialized");
        
        cameraManager.initialize();
        System.out.println("[GameFramework] ✓ Camera manager initialized");
        
        eventManager.initialize();
        System.out.println("[GameFramework] ✓ Event manager initialized");
        
        combatManager.initialize();
        System.out.println("[GameFramework] ✓ Combat manager initialized");
        
        coreManager.initialize();
        System.out.println("[GameFramework] ✓ Core manager initialized");
        
        objectiveManager.initialize();
        System.out.println("[GameFramework] ✓ Objective manager initialized");
        
        optimizationManager.initialize();
        System.out.println("[GameFramework] ✓ Optimization manager initialized");
        
        System.out.println("════════════════════════════════════════════════");
        System.out.println("[GameFramework] ✓ All systems ready!");
        System.out.println("GAME READY - Press ESC to switch levels, ARROW KEYS to move");
        System.out.println("════════════════════════════════════════════════");
    }
    
    /**
     * Update loop - called every frame
     * Delegates to managers
     */
    @Override
    public void update(long deltaMillis) {
        this.deltaTime = deltaMillis / 1000.0f;
        
        // Update framework managers
        if (levelManager != null) {
            levelManager.update(deltaMillis);
        }
        if (guiManager != null) {
            guiManager.update(deltaMillis);
        }
        if (screenManager != null) {
            screenManager.update(deltaMillis);
        }
        
        // Update Phase 4 system managers
        if (audioManager != null) {
            audioManager.update(deltaMillis);
        }
        if (cameraManager != null) {
            cameraManager.update(deltaMillis);
        }
        if (combatManager != null) {
            combatManager.update(deltaMillis);
        }
        if (coreManager != null) {
            coreManager.update(deltaMillis);
        }
        if (eventManager != null) {
            eventManager.update(deltaMillis);
        }
        if (objectiveManager != null) {
            objectiveManager.update(deltaMillis);
        }
        if (optimizationManager != null) {
            optimizationManager.update(deltaMillis);
        }
    }
    
    /**
     * Render loop - called every frame
     * Delegates to managers
     */
    @Override
    public void draw(Graphics2D g) {
        // Render level system (parallax, tilemap, background)
        if (levelManager != null) {
            levelManager.render(g, this.getWidth(), this.getHeight());
        }
        
        // Render GUI system (HUD, panels, components)
        if (guiManager != null) {
            guiManager.render(g, this.getWidth(), this.getHeight());
        }
        
        // Render screen system (UI screens, menus, dialogs)
        if (screenManager != null) {
            screenManager.render(g, this.getWidth(), this.getHeight());
        }
    }
    
    /**
     * Input handling - delegated to screen manager
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 27) { // ESC key
            // Level switching
            if (levelManager != null) {
                levelManager.switchLevel();
            }
        } else {
            // Delegate other key events
            if (screenManager != null) {
                screenManager.handleKeyEvent(e);
            }
        }
    }
    
    // ==================== GETTERS FOR MANAGERS ====================
    // Allows other classes to access managers if needed
    
    public LevelManager getLevelManager() {
        return levelManager;
    }
    
    public GUIManager getGUIManager() {
        return guiManager;
    }
    
    public ScreenManager getScreenManager() {
        return screenManager;
    }
    
    // Phase 4 System Managers
    
    public AudioManager getAudioManager() {
        return audioManager;
    }
    
    public CameraManager getCameraManager() {
        return cameraManager;
    }
    
    public CombatManager getCombatManager() {
        return combatManager;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public CoreManager getCoreManager() {
        return coreManager;
    }
    
    public EventManager getEventManager() {
        return eventManager;
    }
    
    public ObjectiveManager getObjectiveManager() {
        return objectiveManager;
    }
    
    public OptimizationManager getOptimizationManager() {
        return optimizationManager;
    }
    
    public float getDeltaTime() {
        return deltaTime;
    }
}
