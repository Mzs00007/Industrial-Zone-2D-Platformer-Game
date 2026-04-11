package ui;

import java.awt.image.BufferedImage;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * SCREEN MANAGER - COMPLETE GAME FLOW
 * Splash → Menu → Level Select → Character Select → Gameplay → Game Over
 * ════════════════════════════════════════════════════════════════════════════
 */
public class ScreenManager {
    
    public enum Screen {
        SPLASH, MENU, LEVEL_SELECT, CHARACTER_SELECT, GAMEPLAY, GAME_OVER
    }
    
    private Screen currentScreen;
    private SplashScreen splashScreen;
    private MainMenuScreen menuScreen;
    private LevelSelectScreen levelSelectScreen;
    private UISystem.CharacterSelectScreen characterSelectScreen;
    private GameplayScreenV2 gameplayScreen;
    private GameOverScreen gameOverScreen;
    
    private String selectedCharacter = "PUNK";
    private String selectedLevel = "Industrial_zone_level_1";
    
    private int screenWidth = 1024;
    private int screenHeight = 768;
    
    /**
     * Constructor
     */
    public ScreenManager(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.currentScreen = Screen.SPLASH;
        
        // Initialize ALL screens
        this.splashScreen = new SplashScreen(screenWidth, screenHeight);
        this.menuScreen = new MainMenuScreen(screenWidth, screenHeight);
        this.levelSelectScreen = new LevelSelectScreen(screenWidth, screenHeight);
        this.characterSelectScreen = new UISystem.CharacterSelectScreen(screenWidth, screenHeight);
        this.gameplayScreen = new GameplayScreenV2(screenWidth, screenHeight);
        this.gameOverScreen = new GameOverScreen(screenWidth, screenHeight);
        
        System.out.println("[ScreenManager] ✅ FULL GAME FLOW Initialized");
    }
    
    /**
     * Switch to a different screen
     */
    public void switchScreen(Screen screen) {
        this.currentScreen = screen;
        System.out.println("[ScreenManager] → Screen: " + screen);
        
        switch(screen) {
            case SPLASH:
                break;
            case MENU:
                System.out.println("[ScreenManager] 📋 Main Menu - Press SPACE to start");
                break;
            case LEVEL_SELECT:
                System.out.println("[ScreenManager] 🗺️ Level Select - Arrow keys: 1 or 2, ENTER: select");
                break;
            case CHARACTER_SELECT:
                System.out.println("[ScreenManager] 🎪 Character Select - Arrow keys: select, ENTER: play");
                break;
            case GAMEPLAY:
                gameplayScreen.loadCharacter(selectedCharacter);
                gameplayScreen.loadLevel(selectedLevel);
                System.out.println("[ScreenManager] ✅ GAMEPLAY START: " + selectedCharacter + " in " + selectedLevel);
                System.out.println("[ScreenManager] 🎮 Controls: WASD move, SPACE jump, ESC menu");
                break;
            case GAME_OVER:
                System.out.println("[ScreenManager] Game Over - Press SPACE to continue");
                break;
        }
    }
    
    /**
     * Render current screen
     */
    public void render(BufferedImage dest) {
        switch(currentScreen) {
            case SPLASH:
                splashScreen.render(dest);
                if (splashScreen.isFinished()) {
                    switchScreen(Screen.MENU);
                }
                break;
                
            case MENU:
                menuScreen.render(dest);
                break;
                
            case LEVEL_SELECT:
                levelSelectScreen.render(dest);
                break;
                
            case CHARACTER_SELECT:
                characterSelectScreen.render(dest);
                break;
                
            case GAMEPLAY:
                gameplayScreen.update(16);
                gameplayScreen.render(dest);
                break;
                
            case GAME_OVER:
                gameOverScreen.render(dest);
                break;
        }
    }
    
    /**
     * Handle key DOWN event
     */
    public void handleKeyDown(int keyCode) {
        switch(currentScreen) {
            case MENU:
                if (keyCode == ' ') switchScreen(Screen.LEVEL_SELECT);
                break;
                
            case LEVEL_SELECT:
                if (keyCode == '1') {
                    selectedLevel = "Industrial_zone_level_1";
                    switchScreen(Screen.CHARACTER_SELECT);
                } else if (keyCode == '2') {
                    selectedLevel = "power-station-level-2";
                    switchScreen(Screen.CHARACTER_SELECT);
                }
                break;
                
            case CHARACTER_SELECT:
                if (keyCode == java.awt.event.KeyEvent.VK_ENTER) {
                    switchScreen(Screen.GAMEPLAY);
                } else if (keyCode == java.awt.event.KeyEvent.VK_LEFT) {
                    cycleCharacterLeft();
                } else if (keyCode == java.awt.event.KeyEvent.VK_RIGHT) {
                    cycleCharacterRight();
                }
                break;
                
            case GAMEPLAY:
                gameplayScreen.setKeyPressed(keyCode, true);
                if (keyCode == java.awt.event.KeyEvent.VK_ESCAPE) {
                    switchScreen(Screen.CHARACTER_SELECT);
                }
                break;
                
            case GAME_OVER:
                if (keyCode == ' ') {
                    switchScreen(Screen.CHARACTER_SELECT);
                }
                break;
        }
    }
    
    /**
     * Wrapper for backward compatibility
     */
    public void handleKeyPress(int keyCode) {
        handleKeyDown(keyCode);
    }
    
    /**
     * Handle key UP event
     */
    public void handleKeyUp(int keyCode) {
        if (currentScreen == Screen.GAMEPLAY) {
            gameplayScreen.setKeyPressed(keyCode, false);
        }
    }
    
    /**
     * Handle mouse move
     */
    public void handleMouseMove(int x, int y) {
        if (currentScreen == Screen.GAMEPLAY) {
            gameplayScreen.setMousePosition(x, y);
        }
    }
    
    /**
     * Cycle to next character
     */
    private void cycleCharacterRight() {
        String[] chars = {"PUNK", "BIKER", "CYBORG"};
        for (int i = 0; i < chars.length; i++) {
            if (chars[i].equals(selectedCharacter)) {
                selectedCharacter = chars[(i + 1) % chars.length];
                break;
            }
        }
    }
    
    /**
     * Cycle to previous character
     */
    private void cycleCharacterLeft() {
        String[] chars = {"PUNK", "BIKER", "CYBORG"};
        for (int i = 0; i < chars.length; i++) {
            if (chars[i].equals(selectedCharacter)) {
                selectedCharacter = chars[(i - 1 + chars.length) % chars.length];
                break;
            }
        }
    }
    
    /**
     * Set selected character
     */
    public void setSelectedCharacter(String character) {
        this.selectedCharacter = character;
        System.out.println("[ScreenManager] Selected character: " + character);
    }
    
    /**
     * Set selected level before switching to gameplay
     */
    public void setSelectedLevel(String level) {
        this.selectedLevel = level;
        System.out.println("[ScreenManager] Selected level: " + level);
    }
    
    /**
     * Get current screen
     */
    public Screen getCurrentScreen() { return currentScreen; }
    
    /**
     * Get current character
     */
    public String getSelectedCharacter() { return selectedCharacter; }
    
    /**
     * Get current level
     */
    public String getSelectedLevel() { return selectedLevel; }
}
