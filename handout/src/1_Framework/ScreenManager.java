package framework;

import controllers.MenuInputHandler;
import controllers.screens.Phase2CharacterIdleScreen;
import controllers.screens.Phase3StatusBarScreen;
import controllers.screens.Phase4NumericDisplayScreen;
import controllers.screens.Phase5ButtonScreen;
import controllers.screens.Phase6DecorationScreen;
import controllers.screens.Phase7ItemInventoryScreen;
import controllers.screens.Phase8MinimapScreen;
import controllers.screens.Phase9DialogueScreen;
import controllers.screens.Phase10TooltipScreen;
import controllers.screens.Phase11NotificationScreen;
import controllers.screens.Phase12QuestTrackerScreen;
import controllers.screens.Phase13MainMenuScreen;
import controllers.screens.Phase14PauseMenuScreen;
import controllers.screens.Phase15SettingsScreen;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * ScreenManager - Screen-based UI and menu lifecycle management
 * 
 * RESPONSIBILITY: All Phase 2-15 screen initialization, update, and rendering
 * 
 * This class manages:
 * - All 14 Phase screens (2-15)
 * - Menu input handling (main, pause, settings)
 * - Screen state coordination
 * - Screen rendering with proper layering
 * 
 * DOES NOT manage:
 * - Basic GUI components (delegated to GUIManager)
 * - Level systems (delegated to LevelManager)
 * - Game framework (delegated to GameFramework)
 */
public class ScreenManager {
    
    // All Phase 2-15 screens
    private Phase2CharacterIdleScreen characterIdleScreen;
    private Phase3StatusBarScreen statusBarScreen;
    private Phase4NumericDisplayScreen numericDisplayScreen;
    private Phase5ButtonScreen buttonScreen;
    private Phase6DecorationScreen decorationScreen;
    private Phase7ItemInventoryScreen itemInventoryScreen;
    private Phase8MinimapScreen minimapScreen;
    private Phase9DialogueScreen dialogueScreen;
    private Phase10TooltipScreen tooltipScreen;
    private Phase11NotificationScreen notificationScreen;
    private Phase12QuestTrackerScreen questTrackerScreen;
    private Phase13MainMenuScreen mainMenuScreen;
    private Phase14PauseMenuScreen pauseMenuScreen;
    private Phase15SettingsScreen settingsScreen;
    
    // Menu input handler
    private MenuInputHandler menuInputHandler;
    
    /**
     * Initialize all screen systems
     */
    public void initialize() {
        System.out.println("[ScreenManager] Initializing screen systems...");
        
        try {
            // Initialize Phase 2-15 screens in order
            initializeCharacterIdleScreen();
            initializeStatusBarScreen();
            initializeNumericDisplayScreen();
            initializeButtonScreen();
            initializeDecorationScreen();
            initializeItemInventoryScreen();
            initializeMinimapScreen();
            initializeDialogueScreen();
            initializeTooltipScreen();
            initializeNotificationScreen();
            initializeQuestTrackerScreen();
            initializeMainMenuScreen();
            initializePauseMenuScreen();
            initializeSettingsScreen();
            
            // Initialize menu input handler
            initializeMenuInputHandler();
            
            System.out.println("[ScreenManager] ✓ All 14 Phase screens initialized");
        } catch (Exception e) {
            System.out.println("[ScreenManager ERROR] Screen initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void initializeCharacterIdleScreen() {
        characterIdleScreen = new Phase2CharacterIdleScreen();
        System.out.println("[ScreenManager] ✓ Phase 2 Character Idle Screen initialized");
    }
    
    private void initializeStatusBarScreen() {
        statusBarScreen = new Phase3StatusBarScreen();
        statusBarScreen.setHealthPercent(100);
        statusBarScreen.setEnergyPercent(80);
        System.out.println("[ScreenManager] ✓ Phase 3 Status Bar Screen initialized");
    }
    
    private void initializeNumericDisplayScreen() {
        numericDisplayScreen = new Phase4NumericDisplayScreen();
        System.out.println("[ScreenManager] ✓ Phase 4 Numeric Display Screen initialized");
    }
    
    private void initializeButtonScreen() {
        buttonScreen = new Phase5ButtonScreen();
        System.out.println("[ScreenManager] ✓ Phase 5 Button Screen initialized");
    }
    
    private void initializeDecorationScreen() {
        decorationScreen = new Phase6DecorationScreen();
        System.out.println("[ScreenManager] ✓ Phase 6 Decoration Screen initialized");
    }
    
    private void initializeItemInventoryScreen() {
        itemInventoryScreen = new Phase7ItemInventoryScreen();
        System.out.println("[ScreenManager] ✓ Phase 7 Item Inventory Screen initialized");
    }
    
    private void initializeMinimapScreen() {
        minimapScreen = new Phase8MinimapScreen();
        System.out.println("[ScreenManager] ✓ Phase 8 Minimap Screen initialized");
    }
    
    private void initializeDialogueScreen() {
        dialogueScreen = new Phase9DialogueScreen();
        System.out.println("[ScreenManager] ✓ Phase 9 Dialogue Screen initialized");
    }
    
    private void initializeTooltipScreen() {
        tooltipScreen = new Phase10TooltipScreen();
        System.out.println("[ScreenManager] ✓ Phase 10 Tooltip Screen initialized");
    }
    
    private void initializeNotificationScreen() {
        notificationScreen = new Phase11NotificationScreen();
        System.out.println("[ScreenManager] ✓ Phase 11 Notification Screen initialized");
    }
    
    private void initializeQuestTrackerScreen() {
        questTrackerScreen = new Phase12QuestTrackerScreen();
        System.out.println("[ScreenManager] ✓ Phase 12 Quest Tracker Screen initialized");
    }
    
    private void initializeMainMenuScreen() {
        mainMenuScreen = new Phase13MainMenuScreen();
        System.out.println("[ScreenManager] ✓ Phase 13 Main Menu Screen initialized");
    }
    
    private void initializePauseMenuScreen() {
        pauseMenuScreen = new Phase14PauseMenuScreen();
        System.out.println("[ScreenManager] ✓ Phase 14 Pause Menu Screen initialized");
    }
    
    private void initializeSettingsScreen() {
        settingsScreen = new Phase15SettingsScreen();
        System.out.println("[ScreenManager] ✓ Phase 15 Settings Screen initialized");
    }
    
    private void initializeMenuInputHandler() {
        menuInputHandler = new MenuInputHandler(mainMenuScreen, pauseMenuScreen, settingsScreen);
        menuInputHandler.setActionCallback(new MenuInputHandler.MenuActionCallback(){
            @Override
            public void onMainMenuAction(Phase13MainMenuScreen.MenuAction action) {
                handleMainMenuAction(action);
            }
            
            @Override
            public void onPauseMenuAction(Phase14PauseMenuScreen.PauseAction action) {
                handlePauseMenuAction(action);
            }
            
            @Override
            public void onSettingsChanged(String setting, Object value) {
                System.out.println("[ScreenManager] Settings: " + setting + " = " + String.valueOf(value));
            }
            
            @Override
            public void onMenuStateChanged(MenuInputHandler.MenuState oldState, MenuInputHandler.MenuState newState) {
                System.out.println("[ScreenManager] MenuState: " + String.valueOf(oldState) + " → " + String.valueOf(newState));
            }
        });
        System.out.println("[ScreenManager] ✓ MenuInputHandler initialized");
    }
    
    /**
     * Handle main menu actions
     */
    private void handleMainMenuAction(Phase13MainMenuScreen.MenuAction action) {
        if (action == null) return;
        
        switch (action) {
            case NEW_GAME:
                System.out.println("[ScreenManager] Main Menu: Starting new game...");
                break;
            case CONTINUE:
                System.out.println("[ScreenManager] Main Menu: Loading saved game...");
                break;
            case SETTINGS:
                System.out.println("[ScreenManager] Main Menu: Opened settings");
                break;
            case CREDITS:
                System.out.println("[ScreenManager] Main Menu: Showing credits...");
                break;
            case EXIT:
                System.out.println("[ScreenManager] Main Menu: Exiting game...");
                System.exit(0);
                break;
        }
    }
    
    /**
     * Handle pause menu actions
     */
    private void handlePauseMenuAction(Phase14PauseMenuScreen.PauseAction action) {
        if (action == null) return;
        
        switch (action) {
            case RESUME:
                System.out.println("[ScreenManager] Pause Menu: Resuming game...");
                break;
            case SETTINGS:
                System.out.println("[ScreenManager] Pause Menu: Opened settings");
                break;
            case HELP:
                System.out.println("[ScreenManager] Pause Menu: Showing help...");
                break;
            case SAVE_EXIT:
                System.out.println("[ScreenManager] Pause Menu: Saving and exiting...");
                break;
        }
    }
    
    /**
     * Update all screens each frame
     */
    public void update(long deltaMillis) {
        float deltaSeconds = deltaMillis / 1000.0f;
        
        // Update all Phase screens
        if (characterIdleScreen != null) characterIdleScreen.update(deltaSeconds);
        if (statusBarScreen != null) statusBarScreen.update(deltaSeconds);
        if (numericDisplayScreen != null) numericDisplayScreen.update(deltaSeconds);
        if (buttonScreen != null) buttonScreen.update(deltaSeconds);
        if (decorationScreen != null) decorationScreen.update(deltaSeconds);
        if (itemInventoryScreen != null) itemInventoryScreen.update(deltaSeconds);
        if (minimapScreen != null) minimapScreen.update(deltaSeconds);
        if (dialogueScreen != null) dialogueScreen.update(deltaSeconds);
        if (tooltipScreen != null) tooltipScreen.update(deltaSeconds);
        if (notificationScreen != null) notificationScreen.update(deltaSeconds);
        if (questTrackerScreen != null) questTrackerScreen.update(deltaSeconds);
        if (mainMenuScreen != null) mainMenuScreen.update(deltaSeconds);
        if (pauseMenuScreen != null) pauseMenuScreen.update(deltaSeconds);
        if (settingsScreen != null) settingsScreen.update(deltaSeconds);
    }
    
    /**
     * Render all screens in proper layering order
     */
    public void render(Graphics2D g, int width, int height) {
        BufferedImage screenBuffer = null;
        
        // Render Phase 2-15 screens in order (bottom-to-top layering)
        if (characterIdleScreen != null && (screenBuffer = characterIdleScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (statusBarScreen != null && (screenBuffer = statusBarScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (numericDisplayScreen != null && (screenBuffer = numericDisplayScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (buttonScreen != null && (screenBuffer = buttonScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (decorationScreen != null && (screenBuffer = decorationScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (itemInventoryScreen != null && (screenBuffer = itemInventoryScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (minimapScreen != null && (screenBuffer = minimapScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (dialogueScreen != null && (screenBuffer = dialogueScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (tooltipScreen != null && (screenBuffer = tooltipScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (notificationScreen != null && (screenBuffer = notificationScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (questTrackerScreen != null && (screenBuffer = questTrackerScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (mainMenuScreen != null && mainMenuScreen.isDisplayed() && (screenBuffer = mainMenuScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (pauseMenuScreen != null && pauseMenuScreen.isPaused() && (screenBuffer = pauseMenuScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
        
        if (settingsScreen != null && (screenBuffer = settingsScreen.render(width, height)) != null) {
            g.drawImage(screenBuffer, 0, 0, null);
        }
    }
    
    /**
     * Handle key events (delegated from GameFramework)
     */
    public void handleKeyEvent(KeyEvent e) {
        if (menuInputHandler != null) {
            menuInputHandler.handleKeyEvent(e);
        }
    }
    
    // ==================== GETTERS ====================
    
    public MenuInputHandler getMenuInputHandler() {
        return menuInputHandler;
    }
    
    public Phase13MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }
    
    public Phase14PauseMenuScreen getPauseMenuScreen() {
        return pauseMenuScreen;
    }
    
    public Phase15SettingsScreen getSettingsScreen() {
        return settingsScreen;
    }
}
