package framework;

import gui.ButtonPanel;
import gui.GUIAssetManager;
import gui.GameState;
import gui.HUDPanel;
import gui.LeftSidebar;
import gui.MouseInputHandler;
import gui.TopBarPanel;
import java.awt.Graphics2D;
import javax.swing.JFrame;

/**
 * GUIManager - GUI component lifecycle management
 * 
 * RESPONSIBILITY: All GUI component initialization, update, and rendering
 * 
 * This class manages:
 * - Basic GUI components (TopBar, HUD, Sidebar, Buttons)
 * - Game state tracking
 * - Mouse input handling
 * - GUI component updates and rendering
 * 
 * DOES NOT manage:
 * - Screen-based UI (Phase 2-15 screens) - delegated to ScreenManager
 * - Menu input - delegated to ScreenManager
 */
public class GUIManager {
    
    // Game state
    private GameState gameState;
    
    // GUI Components
    private TopBarPanel topBarPanel;
    private HUDPanel hudPanel;
    private LeftSidebar leftSidebar;
    private ButtonPanel buttonPanel;
    private MouseInputHandler mouseInputHandler;
    
    // Frame reference (set during initialize)
    private JFrame frame;
    
    /**
     * Initialize all GUI systems
     */
    public void initialize(JFrame gameFrame) {
        this.frame = gameFrame;
        System.out.println("[GUIManager] Initializing GUI systems...");
        
        // Initialize gamestate
        initializeGameState();
        System.out.println("[GUIManager] ✓ GameState initialized");
        
        // Initialize basic GUI components
        initializeBasicGUI();
        System.out.println("[GUIManager] ✓ Basic GUI components initialized");
        
        // Initialize Phase 2 GUI
        initializePhase2GUI();
        System.out.println("[GUIManager] ✓ Phase 2 GUI components initialized");
        
        System.out.println("[GUIManager] ✓ All GUI ready");
    }
    
    /**
     * Initialize game state with default values
     */
    private void initializeGameState() {
        try {
            gameState = new GameState();
            gameState.currentLevel = "LEVEL_1";
            gameState.levelName = "INDUSTRIAL ZONE";
            gameState.health = 100;
            gameState.maxHealth = 100;
            gameState.energy = 80;
            gameState.maxEnergy = 100;
            gameState.armor = 50;
            gameState.ammo = 3;
            gameState.ammoMax = 12;
            gameState.totalElapsedSeconds = 0;
            gameState.timeRemainingSeconds = 300; // 5 minutes default
        } catch (Exception e) {
            System.out.println("[GUIManager ERROR] GameState initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Initialize basic GUI components (TopBar, HUD, etc.)
     */
    private void initializeBasicGUI() {
        try {
            int frameWidth = frame.getWidth() > 0 ? frame.getWidth() : 1080;
            int frameHeight = frame.getHeight() > 0 ? frame.getHeight() : 720;
            
            // Create TopBar
            topBarPanel = new TopBarPanel(frameWidth);
            topBarPanel.loadAssets();
            
            // Create HUD
            hudPanel = new HUDPanel(frameWidth, frameHeight);
            hudPanel.loadAssets();
            
            System.out.println("[GUIManager] ✓ Basic GUI components created");
        } catch (Exception e) {
            System.out.println("[GUIManager ERROR] Basic GUI initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Initialize Phase 2 GUI (Sidebar, Buttons, Mouse input)
     */
    private void initializePhase2GUI() {
        try {
            int frameWidth = frame.getWidth() > 0 ? frame.getWidth() : 1080;
            int frameHeight = frame.getHeight() > 0 ? frame.getHeight() : 720;
            
            GUIAssetManager assetManager = GUIAssetManager.getInstance();
            
            // Create LeftSidebar
            leftSidebar = new LeftSidebar(frameWidth, frameHeight, assetManager);
            leftSidebar.loadAssets();
            
            // Create ButtonPanel
            buttonPanel = new ButtonPanel(frameWidth, frameHeight, assetManager);
            buttonPanel.loadAssets();
            
            // Setup MouseInputHandler
            mouseInputHandler = new MouseInputHandler(buttonPanel);
            frame.addMouseListener(mouseInputHandler);
            frame.addMouseMotionListener(mouseInputHandler);
            
            System.out.println("[GUIManager] ✓ Phase 2 GUI components created");
        } catch (Exception e) {
            System.out.println("[GUIManager ERROR] Phase 2 GUI initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Update all GUI components each frame
     */
    public void update(long deltaMillis) {
        if (gameState == null) {
            return;
        }
        
        int deltaSeconds = (int)(deltaMillis / 1000L);
        
        // Update game state timers
        gameState.totalElapsedSeconds += deltaSeconds;
        gameState.timeRemainingSeconds = Math.max(0, gameState.timeRemainingSeconds - deltaSeconds);
        
        // Update GUI components
        if (topBarPanel != null) {
            topBarPanel.update(deltaMillis, gameState);
        }
        
        if (hudPanel != null) {
            hudPanel.update(deltaMillis, gameState);
        }
        
        if (leftSidebar != null) {
            leftSidebar.update(deltaMillis, gameState);
        }
        
        if (buttonPanel != null) {
            buttonPanel.update(deltaMillis, gameState);
        }
    }
    
    /**
     * Render all GUI components
     */
    public void render(Graphics2D g, int width, int height) {
        // Render all GUI components in layer order
        if (topBarPanel != null) {
            topBarPanel.render(g);
        }
        
        if (hudPanel != null) {
            hudPanel.render(g);
            if (gameState != null) {
                hudPanel.updateWithGameState(g, gameState);
            }
        }
        
        if (leftSidebar != null) {
            leftSidebar.render(g);
        }
        
        if (buttonPanel != null) {
            buttonPanel.render(g);
        }
    }
    
    /**
     * Update GUI with new gamestate values (called during render phase)
     */
    public void updateGameState(Graphics2D g, GameState newState) {
        if (hudPanel != null && newState != null) {
            gameState = newState;
            hudPanel.updateWithGameState(g, gameState);
        }
    }
    
    // ==================== GETTERS ====================
    
    public GameState getGameState() {
        return gameState;
    }
    
    public TopBarPanel getTopBarPanel() {
        return topBarPanel;
    }
    
    public HUDPanel getHUDPanel() {
        return hudPanel;
    }
    
    public LeftSidebar getLeftSidebar() {
        return leftSidebar;
    }
    
    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }
    
    public MouseInputHandler getMouseInputHandler() {
        return mouseInputHandler;
    }
}
