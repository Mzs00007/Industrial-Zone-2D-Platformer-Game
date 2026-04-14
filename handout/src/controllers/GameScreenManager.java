package ui.components.game2D;

import java.awt.event.KeyEvent;
import java.io.File;
import levels.LevelMapLoader;
import levels.Level1;

/**
 * PHASE 1 REFACTOR: Pure State Machine
 * - NO graphics imports (all moved to GamePanelRenderer)
 * - NO BufferedImage handling
 * - Pure orchestration and state management
 * - Clean separation of concerns
 */
public class GameScreenManager {
    
    public enum ScreenState {
        SPLASH, MAIN_MENU, LEVEL_SELECT, CHARACTER_SELECT, 
        GAMEPLAY, PAUSED, SETTINGS, GAME_OVER
    }
    
    // State management
    private ScreenState currentState = ScreenState.SPLASH;
    private ScreenState nextState = null;
    private long stateStartTime = 0;
    private static final long SPLASH_DURATION = 3000;  // 3 seconds
    
    // Game state
    private int currentLevel = 1;
    private int maxLevels = 20;
    private LevelMapLoader levelMapLoader = null;
    private Level1 currentLevelInstance = null;
    private boolean gameplayInitialized = false;
    
    // Asset state (for sync with renderer)
    private boolean assetsLoaded = false;
    
    public GameScreenManager() {
        initializeAssets();
        stateStartTime = System.currentTimeMillis();
    }
    
    private void initializeAssets() {
        System.out.println("[Assets] Loading game assets...");
        
        // Load level map file presence
        String mapsDir = "handout/maps/";
        for (int i = 1; i <= maxLevels; i++) {
            String levelMapFile = mapsDir + "level_" + i + "/map.txt";
            File mapFile = new File(levelMapFile);
            if (mapFile.exists()) {
                System.out.println("✅ Level " + i + " map found: " + levelMapFile);
            } else {
                System.out.println("ℹ️ Level " + i + " map not yet created");
            }
        }
        
        assetsLoaded = true;
    }
    
    /**
     * Update game state every frame
     */
    public void update(long deltaTime) {
        // Auto-transition splash to menu after duration
        if (currentState == ScreenState.SPLASH) {
            long elapsed = System.currentTimeMillis() - stateStartTime;
            if (elapsed >= SPLASH_DURATION) {
                transitionTo(ScreenState.MAIN_MENU);
            }
        }
        
        // Update gameplay
        if (currentState == ScreenState.GAMEPLAY && currentLevelInstance != null) {
            currentLevelInstance.update(deltaTime / 1000.0f);
        }
        
        // Process pending state change
        if (nextState != null) {
            currentState = nextState;
            nextState = null;
            stateStartTime = System.currentTimeMillis();
            System.out.println("✅ Transitioned to: " + currentState);
        }
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════
    // INPUT HANDLING
    // ═══════════════════════════════════════════════════════════════════════════════════
    
    public void handleKeyPress(int keyCode) {
        System.out.println("[Input] Key pressed: " + KeyEvent.getKeyText(keyCode));
        
        // Global pause key
        if (keyCode == KeyEvent.VK_P && currentState == ScreenState.GAMEPLAY) {
            transitionTo(ScreenState.PAUSED);
            return;
        }
        
        // Resume from pause
        if (keyCode == KeyEvent.VK_R && currentState == ScreenState.PAUSED) {
            transitionTo(ScreenState.GAMEPLAY);
            return;
        }
        
        // Global escape key
        if (keyCode == KeyEvent.VK_ESCAPE) {
            if (currentState == ScreenState.GAMEPLAY || currentState == ScreenState.PAUSED) {
                transitionTo(ScreenState.MAIN_MENU);
            } else if (currentState != ScreenState.MAIN_MENU) {
                transitionTo(ScreenState.MAIN_MENU);
            }
            return;
        }
        
        // State-specific keys
        switch (currentState) {
            case MAIN_MENU:
                handleMainMenuKey(keyCode);
                break;
            case LEVEL_SELECT:
                handleLevelSelectKey(keyCode);
                break;
            case CHARACTER_SELECT:
                if (keyCode == KeyEvent.VK_ENTER) {
                    transitionTo(ScreenState.LEVEL_SELECT);
                }
                break;
            case GAMEPLAY:
                if (currentLevelInstance != null) {
                    currentLevelInstance.handleKeyPress(keyCode);
                }
                break;
            case PAUSED:
                handlePauseMenuKey(keyCode);
                break;
            case SETTINGS:
                break;
            case SPLASH:
                transitionTo(ScreenState.MAIN_MENU);
                break;
        }
    }
    
    public void handleKeyRelease(int keyCode) {
        if (currentState == ScreenState.GAMEPLAY && currentLevelInstance != null) {
            currentLevelInstance.handleKeyRelease(keyCode);
        }
    }
    
    public void handleMouseClick(int x, int y) {
        System.out.println("[Input] Mouse clicked at: " + x + ", " + y);
        
        switch (currentState) {
            case SPLASH:
                transitionTo(ScreenState.MAIN_MENU);
                break;
            case MAIN_MENU:
                handleMainMenuClick(x, y);
                break;
            case LEVEL_SELECT:
                break;
            case CHARACTER_SELECT:
                break;
            case GAMEPLAY:
                // Note: Level1 mouse handling not yet implemented
                // if (currentLevelInstance != null) {
                //     currentLevelInstance.handleMouseClick(x, y);
                // }
                break;
            case PAUSED:
                handlePauseMenuClick(x, y);
                break;
            case SETTINGS:
                break;
            case GAME_OVER:
                transitionTo(ScreenState.MAIN_MENU);
                break;
        }
    }
    
    private void handleMainMenuKey(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_1:
                System.out.println("✅ Starting game...");
                transitionTo(ScreenState.CHARACTER_SELECT);
                break;
            case KeyEvent.VK_2:
                System.out.println("✅ Opening level select...");
                transitionTo(ScreenState.LEVEL_SELECT);
                break;
            case KeyEvent.VK_3:
                System.out.println("✅ Opening settings...");
                transitionTo(ScreenState.SETTINGS);
                break;
            case KeyEvent.VK_4:
                System.out.println("✅ Quitting game...");
                System.exit(0);
                break;
        }
    }
    
    private void handleMainMenuClick(int x, int y) {
        if (y >= 180 && y <= 220) {
            transitionTo(ScreenState.CHARACTER_SELECT);
        }
    }
    
    private void handleLevelSelectKey(int keyCode) {
        if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_9) {
            int level = keyCode - KeyEvent.VK_0;
            if (level <= maxLevels) {
                startLevel(level);
            }
        } else if (keyCode == KeyEvent.VK_SPACE) {
            startLevel(1);
        }
    }
    
    private void handlePauseMenuKey(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_R:
                System.out.println("✅ Resuming gameplay...");
                transitionTo(ScreenState.GAMEPLAY);
                break;
            case KeyEvent.VK_S:
                System.out.println("✅ Opening settings from pause menu...");
                transitionTo(ScreenState.SETTINGS);
                break;
            case KeyEvent.VK_M:
                System.out.println("✅ Returning to main menu...");
                transitionTo(ScreenState.MAIN_MENU);
                break;
        }
    }
    
    private void handlePauseMenuClick(int x, int y) {
        if (x >= 300 && x <= 500 && y >= 380 && y <= 420) {
            transitionTo(ScreenState.GAMEPLAY);
        }
        else if (x >= 300 && x <= 500 && y >= 430 && y <= 470) {
            transitionTo(ScreenState.SETTINGS);
        }
        else if (x >= 300 && x <= 500 && y >= 480 && y <= 520) {
            transitionTo(ScreenState.MAIN_MENU);
        }
    }
    
    private void startLevel(int level) {
        currentLevel = level;
        System.out.println("✅ Starting Level " + currentLevel + "...");
        System.out.println("📍 Loading tilemap from: handout/maps/level_" + currentLevel + "/map.txt");
        
        if (currentLevel == 1) {
            try {
                currentLevelInstance = new Level1();
                System.out.println("✅ Created Level1 with PlayerController and sprite assets");
                gameplayInitialized = true;
            } catch (Exception e) {
                System.err.println("❌ Failed to create Level1: " + e.getMessage());
                e.printStackTrace();
                gameplayInitialized = false;
                return;
            }
        } else {
            levelMapLoader = new LevelMapLoader(currentLevel);
            System.out.println("🎮 Initialized level tilemap with player spawn at (100, 100)");
            gameplayInitialized = true;
        }
        
        transitionTo(ScreenState.GAMEPLAY);
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════
    // GETTERS AND STATE MANAGEMENT
    // ═══════════════════════════════════════════════════════════════════════════════════
    
    public void transitionTo(ScreenState newState) {
        nextState = newState;
    }
    
    public ScreenState getCurrentState() {
        return currentState;
    }
    
    public int getCurrentLevel() {
        return currentLevel;
    }
    
    public int getMaxLevels() {
        return maxLevels;
    }
    
    public Level1 getCurrentLevelInstance() {
        return currentLevelInstance;
    }
    
    public LevelMapLoader getLevelMapLoader() {
        return levelMapLoader;
    }
    
    public boolean isGameplayInitialized() {
        return gameplayInitialized;
    }
    
    public boolean isAssetsLoaded() {
        return assetsLoaded;
    }
}
