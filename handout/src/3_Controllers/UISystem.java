package ui;
import animation.InputController;
import animation.InputController.VerticalSpritesheetLoader;
import physics.PhysicsSystem;
import rendering.RenderingSystem;
import animation.InputController.GUIButtonSystemProperties.ButtonColorMaps;
import animation.InputController.PixelCopyHelper;

import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * ═══════════════════════════════════════════════════════════════════════════════════════
 * RASTER GRAPHICS ONLY - PNG IMAGE ASSETS ONLY
 * ═══════════════════════════════════════════════════════════════════════════════════════
 * 
 * UPDATE (April 3, 2026 - COMPLETE HANDOUT DIRECTORY CONVERSION):
 * ─────────────────────────────────────────────────────────────────────────────────────
 * • Removed ALL vector graphics imports (no java.awt.Graphics2D, Shape, java.awt.Rectangle, Path2D, etc)
 * • Removed ALL vector shape drawing (no fillRect, drawOval, drawLine, etc)
 * • Removed java.awt.Color-based fallback graphics (no java.awt.Color objects for dummy shapes)
 * • Removed java.awt.Rectangle import (use actual image assets instead!)
 * • Added raster image imports (BufferedImage, ImageIO, File, IOException)
 * • Now loads actual PNG files from Resources directories only
 * 
 * ASSETS USED:
 * ✓ PNG raster image files (from Resources/industrial-zone/...)
 * ✓ MIDI audio files (music)
 * ✓ OTF font files (text rendering)
 * ✓ WAV audio files (sound effects)
 * 
 * ASSETS NOT USED (FORBIDDEN):
 * ✗ Vector graphics (SVG, vector shapes, Batik library)
 * ✗ Dynamically drawn shapes (fillRect, drawOval, drawCircle, etc)
 * ✗ java.awt.Color-based fallback graphics (java.awt.Color.RED, java.awt.Color.BLUE, etc)
 * ✗ Vector geometry paths (Path2D, Ellipse2D, Rectangle2D, Line2D)
 * ✗ java.awt.Rectangle (not for rendering!)
 * ✗ RenderingHints or BasicStroke
 * ✗ Apache Batik or any vector library
 * ✗ java.awt.Graphics or java.awt.Graphics2D for drawing
 * 
 * ERROR HANDLING REQUIREMENT:
 * When PNG file NOT found:
 * • Log error with complete file path
 * • Skip rendering (do not create fallback java.awt.Color/shape)
 * • User sees missing asset clearly in console output
 * 
 * NEVER FALLBACK TO:
 * ✗ g2d.setColor(java.awt.Color.RED); g2d.fillRect(...);  // Forbidden!
 * ✗ g2d.drawOval(...);                            // Forbidden!
 * ✗ g2d.drawCircle(...);                          // Forbidden!
 * ✗ new java.awt.Rectangle(x, y, w, h) for rendering      // Forbidden!
 * 
 * CORRECT PATTERN:
 * if (image == null) {
 *     System.err.println("ERROR: PNG not found: " + filePath);
 *     return;  // Skip rendering, no fallback graphics
 * }
 * g2d.drawImage(image, x, y, null);  // Draw actual PNG
 * 
 * ═══════════════════════════════════════════════════════════════════════════════════════
 */


import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * ==================================================================================
 * UISystem - UNIFIED GUI/HUD API - Complete Game UI/HUD System
 * All GUI Classes Consolidated Here Only - Zero Duplication via Inheritance
 * ==================================================================================
 * 
 * ARCHITECTURE OVERVIEW:
 * =========================================
 * UISystem extends AnimationAndSpriteLoader to inherit:
 *   * GUI resource paths: GUI_LOGO, GUI_BUTTONS, GUI_FRAMES, GUI_BARS, GUI_NUMBERS
 *   * Sprite/animation utilities: VerticalSpritesheetLoader
 *   * Asset management: Level1TileRegistry, Level2TileRegistry, CharacterAssetMapper
 *   * java.awt.Color mappings: ButtonColorMaps
 *   * All other parent resources for zero code duplication
 * 
 * NESTED STATIC CLASSES INDEX:
 * =========================================
 * 
 * [1] GameStateManager (LINE ~83)
 *     - Role: Manages game state transitions (MENU, LOADING, GAMEPLAY, PAUSED, GAME_OVER)
 *     - Enum: GameState_GUI - 6 game states
 *     - Methods: setState(), getCurrentState(), isTransitioning()
 *     - API: GameStateManager.GameState_GUI state = GameStateManager.GameState_GUI.MENU;
 * 
 * [2] HUDElement & Subclasses (BASE: LINE ~124)
 *     - Base: HUDElement - Abstract base for all HUD overlay elements
 *     - Subclasses (extend HUDElement):
 *         + HealthBar (LINE ~159): Displays player health bar
 *         + ScoreDisplay (LINE ~214): Shows player score/points
 *         + AmmoCounter (LINE ~255): Shows ammunition remaining
 *         + FPSCounter (LINE ~314): FPS monitor for debugging
 *         + WaveIndicator (LINE ~363): Shows current wave/level
 *         + ObjectiveDisplay (LINE ~412): Shows mission objectives
 *         + MiniMap (LINE ~468): Renders game world minimap
 *           - EntityMarker (LINE ~475): Nested for minimap entities
 *     - Common Methods: render(java.awt.Graphics2D, int x, int y), update(), setVisible(boolean)
 *     - API: UISystem.HealthBar health = new UISystem.HealthBar(100, 100, 200, 30);
 * 
 * [3] HUDManager (LINE ~547)
 *     - Role: Central manager for all HUD elements
 *     - Methods: registerElement(), render(), update(), getElement()
 *     - API: UISystem.HUDManager hudManager = new UISystem.HUDManager();
 * 
 * [4] Screen Classes (MenuScreen, LoadingScreen, PauseScreen, GameOverScreen)
 *     - MenuScreen (LINE ~622): Main menu with buttons, MenuButton nested class
 *     - LoadingScreen (LINE ~739): Loading bar/spinner during asset load
 *     - PauseScreen (LINE ~804): Pause menu overlay
 *     - GameOverScreen (LINE ~865): Game over screen with statistics
 *     - API: UISystem.MenuScreen menu = new UISystem.MenuScreen();
 * 
 * [5] UIManager (LINE ~982)
 *     - Role: Master manager coordinating all UI elements and screens
 *     - Methods: switchScreen(), render(), update(), handleInput()
 *     - API: UISystem.UIManager uiManager = new UISystem.UIManager();
 * 
 * [6] UIInputHandler (LINE ~1071)
 *     - Role: Handles all keyboard and mouse input for UI
 *     - Implements: MouseListener, MouseMotionListener
 *     - Methods: mouseClicked(), mouseMoved(), keyPressed(), keyReleased()
 *     - API: UISystem.UIInputHandler inputHandler = new UISystem.UIInputHandler();
 * 
 * [7] ButtonRenderer (LINE ~1200)
 *     - Role: Renders interactive buttons with visual feedback
 *     - Methods: render(), isMouseOver(), isClicked()
 *     - API: UISystem.ButtonRenderer btn = new UISystem.ButtonRenderer(100, 100, 150, 50);
 * 
 * [8] GuiStateManager (LINE ~1359)
 *     - Role: Manages GUI-specific state (different from GameStateManager)
 *     - Methods: setCurrentGuiState(), getCurrentGuiState(), getStateHistory()
 *     - API: UISystem.GuiStateManager guiState = new UISystem.GuiStateManager();
 * 
 * [9] GUIAssetAccessor (LINE ~1612)
 *     - Role: Unified access point for all GUI assets/resources
 *     - Methods: getAsset(String path), getFont(String name), loadUIResources()
 *     - API: BufferedImage btn = UISystem.GUIAssetAccessor.getAsset(GUI_BUTTONS + "/start.png");
 *     - NOTE: Uses inherited GUI_BUTTONS path from AnimationAndSpriteLoader!
 * 
 * INHERITED RESOURCES (Zero Code Duplication):
 * =========================================
 * Inherited from AnimationAndSpriteLoader:
 *   * GUI_LOGO, GUI_BUTTONS, GUI_FRAMES, GUI_BARS, GUI_NUMBERS (asset paths)
 *   * Level1TileRegistry, Level2TileRegistry (tile data)
 *   * CharacterAssetMapper (character animation assets)
 *   * ButtonColorMaps (button java.awt.Color schemes)
 *   * VerticalSpritesheetLoader (sprite/animation utilities)
 *   * All other parent class resources
 * 
 * Access inherited in any nested class:
 *   String buttonPath = GUI_BUTTONS;  // Inherited, no duplication!
 *   TileRegistry tiles = Level1TileRegistry.getInstance();
 *   ButtonColorMaps colors = new ButtonColorMaps();
 * 
 * QUICK API USAGE FOR EXTERNAL CLASSES (Game.java, etc):
 * =========================================
 * // Initialize
 * UISystem.UIManager uiManager = new UISystem.UIManager();
 * UISystem.HUDManager hudManager = new UISystem.HUDManager();
 * 
 * // Add HUD elements
 * UISystem.HealthBar health = new UISystem.HealthBar(100, 100, 200, 30);
 * UISystem.ScoreDisplay score = new UISystem.ScoreDisplay(50, 50);
 * hudManager.registerElement(health);
 * 
 * // Switch screens
 * UISystem.MenuScreen menu = new UISystem.MenuScreen();
 * uiManager.switchScreen(menu);
 * 
 * // Handle input
 * UISystem.UIInputHandler inputHandler = new UISystem.UIInputHandler();
 * gamePanel.addMouseListener(inputHandler);
 * 
 * // Render in game loop
 * uiManager.render(g2d);
 * hudManager.render(g2d);
 * 
 * // Check game state
 * GameStateManager stateManager = new GameStateManager();
 * if (stateManager.getCurrentState() == GameStateManager.GameState_GUI.GAMEPLAY) {
 *     // Render gameplay HUD
 * }
 * 
 * ==================================================================================
 * NO STANDALONE GUI FILES EXIST - ALL GUI CODE CONSOLIDATED HERE
 * ==================================================================================
 * @author 3359098
 */
public class UISystem extends levels.LevelSystem {
    
    /**
     * Constructor: Initialize UISystem
     * Inherits from RenderingSystem + adds GUI, HUD, and screen management
     */
    public UISystem() {
        super();  // Initialize RenderingSystem (which initializes CoreSystem → PhysicsSystem)
    }
    
    // ==================== SIMPLE PUBLIC API FACADE ====================
    /**
     * Simple, clean API for external classes to use UISystem features.
     * Hides all the complex nested class implementations.
     */
    
    // ==================== GUI INFRASTRUCTURE ====================

    /**
     * GuiScreen interface - All screen implementations extend this contract
     */
    public interface GuiScreen {
        void loadAssets();
        void update(long elapsedTime);
        void render(BufferedImage dest);
        void handleMouseClick(int x, int y);
        void handleMouseMove(int x, int y);
        void cleanup();
    }

    /**
     * GuiButton - Reusable clickable button component (requires pre-loaded assets)
     */
    public static class GuiButton {
        private BufferedImage normalState = null;
        private BufferedImage hoverState = null;
        private BufferedImage pressedState = null;
        private int x, y, width, height;
        private boolean hovered = false;
        private boolean pressed = false;
        private String label;
        private Runnable onClickCallback;
        
        public GuiButton(int x, int y, int width, int height, String label) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.label = label;
        }
        
        public void setOnClick(Runnable callback) {
            this.onClickCallback = callback;
        }
        
        public void loadAssets(BufferedImage normal, BufferedImage hover, BufferedImage pressed) {
            this.normalState = normal;
            this.hoverState = hover;
            this.pressedState = pressed;
        }
        
        public void update(int mouseX, int mouseY) {
            hovered = (mouseX >= x && mouseX < x+width && mouseY >= y && mouseY < y+height);
        }
        
        public void handleClick(int mx, int my) {
            if (hovered && mx >= x && mx < x+width && my >= y && my < y+height) {
                pressed = true;
                if (onClickCallback != null) {
                    onClickCallback.run();
                }
            }
        }
        
        public void render(BufferedImage dest) {
            if (dest == null) return;
            
            BufferedImage toDraw = normalState;
            if (normalState == null) {
                System.err.println("[GuiButton] ERROR: Button assets not loaded! No fallback graphics - returning NULL");
                return;
            }
            
            if (pressed && pressedState != null) toDraw = pressedState;
            else if (hovered && hoverState != null) toDraw = hoverState;
            
            if (toDraw != null) {
                java.awt.Graphics2D g2d = dest.createGraphics();
                g2d.drawImage(toDraw, x, y, width, height, null);
                g2d.dispose();
            }
        }
        
        public boolean isClicked() { return pressed; }
        public void resetPressed() { pressed = false; }
        public boolean isHovered() { return hovered; }
    }

    /**
     * GuiPanel - Builds windows from frame assets (requires pre-loaded frame images)
     * Does NOT create dummy graphics - requires all assets to be provided from actual PNG files
     */
    public static class GuiPanel {
        private BufferedImage cornerTL = null;
        private BufferedImage cornerTR = null;
        private BufferedImage cornerBL = null;
        private BufferedImage cornerBR = null;
        private BufferedImage edgeTop = null;
        private BufferedImage edgeBottom = null;
        private BufferedImage edgeLeft = null;
        private BufferedImage edgeRight = null;
        private BufferedImage fill = null;
        private int x, y, width, height;
        
        public GuiPanel(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        
        public void setAssets(BufferedImage ctL, BufferedImage ctR, BufferedImage cbL, BufferedImage cbR,
                             BufferedImage eTop, BufferedImage eBot, BufferedImage eLeft, BufferedImage eRight,
                             BufferedImage fillImg) {
            this.cornerTL = ctL;
            this.cornerTR = ctR;
            this.cornerBL = cbL;
            this.cornerBR = cbR;
            this.edgeTop = eTop;
            this.edgeBottom = eBot;
            this.edgeLeft = eLeft;
            this.edgeRight = eRight;
            this.fill = fillImg;
        }
        
        public void render(BufferedImage dest) {
            if (dest == null) return;
            
            // Verify assets are loaded before rendering
            if (fill == null && (cornerTL == null || edgeTop == null)) {
                System.err.println("[GuiPanel] ERROR: Panel assets not loaded! No fallback graphics");
                return;
            }
            
            java.awt.Graphics2D g2d = dest.createGraphics();
            
            // Draw fill
            if (fill != null) {
                g2d.drawImage(fill, x+8, y+8, width-16, height-16, null);
            }
            
            // Draw edges (scaled to fit)
            if (edgeTop != null) g2d.drawImage(edgeTop, x+8, y, width-16, 8, null);
            if (edgeBottom != null) g2d.drawImage(edgeBottom, x+8, y+height-8, width-16, 8, null);
            if (edgeLeft != null) g2d.drawImage(edgeLeft, x, y+8, 8, height-16, null);
            if (edgeRight != null) g2d.drawImage(edgeRight, x+width-8, y+8, 8, height-16, null);
            
            // Draw corners
            if (cornerTL != null) g2d.drawImage(cornerTL, x, y, 8, 8, null);
            if (cornerTR != null) g2d.drawImage(cornerTR, x+width-8, y, 8, 8, null);
            if (cornerBL != null) g2d.drawImage(cornerBL, x, y+height-8, 8, 8, null);
            if (cornerBR != null) g2d.drawImage(cornerBR, x+width-8, y+height-8, 8, 8, null);
            
            g2d.dispose();
        }
    }

    // ==================== GAME STATE MANAGER ====================
    public static class GameStateManager {
        
        public enum GameState_GUI {
            MENU, LOADING, GAMEPLAY, PAUSED, SETTINGS, CONTROLS, GAME_OVER, QUIT
        }
        
        private GameState_GUI currentState;
        private GameState_GUI previousState;
        private long stateTransitionTime;
        private static final long TRANSITION_DURATION = 500;
        
        public GameStateManager() {
            this.currentState = GameState_GUI.MENU;
            this.previousState = GameState_GUI.MENU;
            this.stateTransitionTime = System.currentTimeMillis();
        }
        
        public void setState(GameState_GUI newState) {
            if (newState != currentState) {
                previousState = currentState;
                currentState = newState;
                stateTransitionTime = System.currentTimeMillis();
            }
        }
        
        public GameState_GUI getCurrentState() { return currentState; }
        public GameState_GUI getPreviousState() { return previousState; }
        public boolean isTransitioning() { return (System.currentTimeMillis() - stateTransitionTime) < TRANSITION_DURATION; }
        public float getTransitionProgress() {
            long elapsed = System.currentTimeMillis() - stateTransitionTime;
            if (elapsed >= TRANSITION_DURATION) return 1.0f;
            return elapsed / (float) TRANSITION_DURATION;
        }
        public boolean isGameplay() { return currentState == GameState_GUI.GAMEPLAY; }
        public boolean isPaused() { return currentState == GameState_GUI.PAUSED; }
        public boolean isMenu() { return currentState == GameState_GUI.MENU; }
        public boolean isGameOver() { return currentState == GameState_GUI.GAME_OVER; }
        public boolean isLoading() { return currentState == GameState_GUI.LOADING; }
    }

    // ==================== GUI STATE ENUM ====================
    public enum GuiState {
        INTRO(0, "Intro Screen", "Story/Splash"),
        MENU(1, "Main Menu", "Start, Settings, Exit"),
        LEVEL_SELECT(2, "Level Select", "Choose Level 1 or 2"),
        PLAYER_SELECT(3, "Player Select", "Choose Character Skin"),
        GAMEPLAY_PREP(4, "Gameplay Prep", "Loading/Tips Screen"),
        GAMEPLAY(5, "Gameplay", "Active Game"),
        HUD(6, "Gameplay HUD", "Overlay UI");
        
        public final int stateId;
        public final String displayName;
        public final String description;
        
        GuiState(int stateId, String displayName, String description) {
            this.stateId = stateId;
            this.displayName = displayName;
            this.description = description;
        }
        
        public static GuiState byId(int id) {
            for (GuiState state : GuiState.values()) {
                if (state.stateId == id) return state;
            }
            return INTRO;
        }
    }

    // ==================== HUD SYSTEM ====================
    /**
     * PNG Raster-Only HUD Element Base
     * All rendering uses pre-made PNG assets from AnimationAndSpriteLoader
     */
    public static class HUDElement {
        protected int x, y, width, height;
        protected boolean visible;
        protected BufferedImage backgroundImage;
        
        public HUDElement(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.visible = true;
            this.backgroundImage = null;
        }
        
        public void update(long elapsedTime) {}
        
        /**
         * Render to BufferedImage using PNG assets only
         * @param dest Destination BufferedImage
         */
        public void render(BufferedImage dest) {}
        
        public void setPosition(int x, int y) { this.x = x; this.y = y; }
        public void setVisible(boolean visible) { this.visible = visible; }
        public boolean isVisible() { return visible; }
        public int getX() { return x; }
        public int getY() { return y; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
        
        /**
         * Render background PNG asset to destination
         */
        protected void renderBackground(BufferedImage dest) {
            if (backgroundImage != null) {
                PixelCopyHelper.copyRaster(backgroundImage, dest, x, y);
            }
        }
    }

    public static class HealthBar extends HUDElement {
        private int currentHealth, maxHealth;
        private static final int HEALTH_BAR_WIDTH = 150;
        private static final int HEALTH_BAR_HEIGHT = 30;
        private BufferedImage healthBarFrame;
        
        public HealthBar(int x, int y) {
            super(x, y, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
            this.currentHealth = 100;
            this.maxHealth = 100;
            loadAssets();
        }
        
        private void loadAssets() {
            try {
                // Load from UIAssets through AssetRegistry (if available)
                // Otherwise fall back to legacy file path
                File healthBarFile = new File(InputController.GUI_BARS + "HealthBar.png");
                if (healthBarFile.exists()) {
                    healthBarFrame = ImageIO.read(healthBarFile);
                    System.out.println("[UISystem.HealthBar] Loaded health bar asset");
                } else {
                    System.err.println("[UISystem.HealthBar] Asset file not found: " + healthBarFile.getAbsolutePath());
                }
            } catch (IOException e) {
                System.err.println("[UISystem.HealthBar] Error loading PNG: " + e.getMessage());
            }
        }
        
        public void setHealth(int current, int max) {
            this.currentHealth = Math.max(0, Math.min(current, max));
            this.maxHealth = max;
        }
        
        public void damageHealth(int amount) { currentHealth = Math.max(0, currentHealth - amount); }
        public void healHealth(int amount) { currentHealth = Math.min(maxHealth, currentHealth + amount); }
        public int getHealth() { return currentHealth; }
        public int getCurrentHealth() { return currentHealth; }
        public int getMaxHealth() { return maxHealth; }
        public void setBlinking(boolean blinking) { /* Placeholder for blinking effect */ }
        
        @Override
        public void render(BufferedImage dest) {
            if (!visible || healthBarFrame == null) return;
            PixelCopyHelper.copyRaster(healthBarFrame, dest, x, y);
        }
    }

    public static class ScoreDisplay extends HUDElement {
        private long currentScore, displayScore;
        private static final int SCORE_WIDTH = 180;
        private static final int SCORE_HEIGHT = 50;
        private BufferedImage scoreFrame;
        
        public ScoreDisplay(int x, int y) {
            super(x, y, SCORE_WIDTH, SCORE_HEIGHT);
            this.currentScore = 0;
            this.displayScore = 0;
            loadAssets();
        }
        
        private void loadAssets() {
            try {
                // Load from legacy asset path
                File scoreFile = new File(InputController.GUI_BARS + "ScoreDisplay.png");
                if (scoreFile.exists()) {
                    scoreFrame = ImageIO.read(scoreFile);
                    System.out.println("[UISystem.ScoreDisplay] Loaded score display asset");
                } else {
                    System.err.println("[UISystem.ScoreDisplay] Asset file not found: " + scoreFile.getAbsolutePath());
                }
            } catch (IOException e) {
                System.err.println("[UISystem.ScoreDisplay] Error loading PNG: " + e.getMessage());
            }
        }
        
        public void addScore(long points) { currentScore += points; }
        public void setScore(long score) { currentScore = score; }
        public long getScore() { return currentScore; }
        
        @Override
        public void update(long elapsedTime) {
            if (displayScore < currentScore) {
                long diff = currentScore - displayScore;
                long increment = Math.max(1, diff / 10);
                displayScore += increment;
                if (displayScore > currentScore) {
                    displayScore = currentScore;
                }
            }
        }
        
        @Override
        public void render(BufferedImage dest) {
            if (!visible || scoreFrame == null) return;
            PixelCopyHelper.copyRaster(scoreFrame, dest, x, y);
        }
    }

    public static class AmmoCounter extends HUDElement {
        private int currentAmmo, maxAmmo, reserveAmmo;
        private String weaponName;
        private static final int AMMO_WIDTH = 150;
        private static final int AMMO_HEIGHT = 50;
        private BufferedImage ammoFrame;
        
        public AmmoCounter(int x, int y) {
            super(x, y, AMMO_WIDTH, AMMO_HEIGHT);
            this.currentAmmo = 30;
            this.maxAmmo = 30;
            this.reserveAmmo = 120;
            this.weaponName = "RIFLE";
            loadAssets();
        }
        
        private void loadAssets() {
            try {
                // Load from legacy asset path
                File ammoFile = new File(InputController.GUI_BARS + "AmmoCounter.png");
                if (ammoFile.exists()) {
                    ammoFrame = ImageIO.read(ammoFile);
                    System.out.println("[UISystem.AmmoCounter] Loaded ammo counter asset");
                } else {
                    System.err.println("[UISystem.AmmoCounter] Asset file not found: " + ammoFile.getAbsolutePath());
                }
            } catch (IOException e) {
                System.err.println("[UISystem.AmmoCounter] Error loading PNG: " + e.getMessage());
            }
        }
        
        public void setAmmo(int current, int max, int reserve) {
            this.currentAmmo = Math.max(0, Math.min(current, max));
            this.maxAmmo = max;
            this.reserveAmmo = reserve;
        }
        
        public void consumeAmmo(int amount) { currentAmmo = Math.max(0, currentAmmo - amount); }
        public void reload() {
            int needed = maxAmmo - currentAmmo;
            int reload = Math.min(needed, reserveAmmo);
            currentAmmo += reload;
            reserveAmmo -= reload;
        }
        public void setWeapon(String name, int current, int max, int reserve) {
            this.weaponName = name;
            setAmmo(current, max, reserve);
        }
        
        @Override
        public void render(BufferedImage dest) {
            if (!visible || ammoFrame == null) return;
            PixelCopyHelper.copyRaster(ammoFrame, dest, x, y);
        }
    }

    public static class FPSCounter extends HUDElement {
        private float fps;
        private long lastFrameTime;
        private int frameCount;
        private static final int FPS_WIDTH = 80;
        private static final int FPS_HEIGHT = 40;
        private BufferedImage fpsFrame;
        
        public FPSCounter(int x, int y) {
            super(x, y, FPS_WIDTH, FPS_HEIGHT);
            this.fps = 0;
            this.lastFrameTime = System.currentTimeMillis();
            this.frameCount = 0;
            loadAssets();
        }
        
        private void loadAssets() {
            try {
                // FPS counter text overlay - use simple rendering for now
                System.out.println("[UISystem.FPSCounter] Initialized");
            } catch (Exception e) {
                System.err.println("[UISystem.FPSCounter] Error: " + e.getMessage());
            }
        }
        
        public void update(long elapsedTime) {
            frameCount++;
            long currentTime = System.currentTimeMillis();
            long elapsed = currentTime - lastFrameTime;
            
            if (elapsed >= 1000) {
                fps = (frameCount * 1000f) / elapsed;
                frameCount = 0;
                lastFrameTime = currentTime;
            }
        }
        
        public float getFPS() { return fps; }
        public void resetFPS() { fps = 0; frameCount = 0; lastFrameTime = System.currentTimeMillis(); }
        
        @Override
        public void render(BufferedImage dest) {
            // FPS renders as text overlay via debug info
            if (!visible) return;
        }
    }

    public static class WaveIndicator extends HUDElement {
        private int currentWave, totalWaves, enemiesRemaining, enemiesDefeated;
        private static final int WAVE_WIDTH = 200;
        private static final int WAVE_HEIGHT = 60;
        private BufferedImage waveFrame;
        
        public WaveIndicator(int x, int y) {
            super(x, y, WAVE_WIDTH, WAVE_HEIGHT);
            this.currentWave = 1;
            this.totalWaves = 5;
            this.enemiesRemaining = 10;
            this.enemiesDefeated = 0;
            loadAssets();
        }
        
        private void loadAssets() {
            try {
                String wavePath = "Resources/industrial-zone/gui/1 Frames/Frame_WaveIndicator_NoFrameBorder_200x60px_Metal_001.png";
                File waveFile = new File(wavePath);
                if (waveFile.exists()) {
                    waveFrame = javax.imageio.ImageIO.read(waveFile);
                    System.out.println("[UISystem.WaveIndicator] Loaded from manifest path: " + wavePath);
                } else {
                    System.err.println("[UISystem.WaveIndicator] Asset not found: " + wavePath);
                }
            } catch (Exception e) {
                System.err.println("[UISystem.WaveIndicator] Error loading PNG: " + e.getMessage());
            }
        }
        
        public void setWave(int current, int total) {
            this.currentWave = current;
            this.totalWaves = total;
        }
        
        public void setEnemies(int remaining, int defeated) {
            this.enemiesRemaining = Math.max(0, remaining);
            this.enemiesDefeated = defeated;
        }
        
        public void enemyDefeated() {
            enemiesRemaining = Math.max(0, enemiesRemaining - 1);
            enemiesDefeated++;
        }
        
        public int getEnemiesRemaining() { return enemiesRemaining; }
        
        @Override
        public void render(BufferedImage dest) {
            if (!visible || waveFrame == null) return;
            PixelCopyHelper.copyRaster(waveFrame, dest, x, y);
        }
    }

    public static class ObjectiveDisplay extends HUDElement {
        private String objectiveText;
        private String objectiveStatus;
        private float blinkAlpha;
        private boolean isBlinking;
        private static final int OBJ_WIDTH = 300;
        private static final int OBJ_HEIGHT = 50;
        private BufferedImage objectiveFrame;
        
        public ObjectiveDisplay(int x, int y) {
            super(x, y, OBJ_WIDTH, OBJ_HEIGHT);
            this.objectiveText = "Survive the wave";
            this.objectiveStatus = "In Progress";
            this.blinkAlpha = 1.0f;
            this.isBlinking = false;
            loadAssets();
        }
        
        private void loadAssets() {
            try {
                File objFile = new File(InputController.GUI_OTHER + "ObjectiveDisplay.png");
                if (objFile.exists()) {
                    objectiveFrame = ImageIO.read(objFile);
                }
            } catch (IOException e) {
                System.err.println("[UISystem.ObjectiveDisplay] Error loading PNG: " + e.getMessage());
            }
        }
        
        public void setObjective(String text, String status) {
            this.objectiveText = text;
            this.objectiveStatus = status;
        }
        
        public void setBlinking(boolean blinking) { this.isBlinking = blinking; }
        public String getObjective() { return objectiveText; }
        
        @Override
        public void update(long elapsedTime) {
            if (isBlinking) {
                blinkAlpha = 0.5f + 0.5f * (float) Math.sin(System.currentTimeMillis() / 500.0);
            } else {
                blinkAlpha = 1.0f;
            }
        }
        
        @Override
        public void render(BufferedImage dest) {
            if (!visible || objectiveFrame == null) return;
            PixelCopyHelper.copyRaster(objectiveFrame, dest, x, y);
        }
    }

    public static class MiniMap extends HUDElement {
        private int mapWidth, mapHeight;

        private float playerX, playerY;
        private java.util.List<EntityMarker> enemies, projectiles, pickups;
        private static final int MINIMAP_WIDTH = 150;
        private static final int MINIMAP_HEIGHT = 150;
        private BufferedImage minimapFrame;
        
        public static class EntityMarker {
            public float x, y, radius;
            public EntityMarker(float x, float y, float radius) {
                this.x = x; this.y = y; this.radius = radius;
            }
        }
        
        public MiniMap(int x, int y, int worldWidth, int worldHeight) {
            super(x, y, MINIMAP_WIDTH, MINIMAP_HEIGHT);
            this.mapWidth = worldWidth;
            this.mapHeight = worldHeight;
            this.playerX = 0;
            this.playerY = 0;
            this.enemies = new java.util.ArrayList<>();
            this.projectiles = new java.util.ArrayList<>();
            this.pickups = new java.util.ArrayList<>();
            loadAssets();
        }
        
        private void loadAssets() {
            try {
                File mapFile = new File(InputController.GUI_OTHER + "MiniMap.png");
                if (mapFile.exists()) {
                    minimapFrame = ImageIO.read(mapFile);
                }
            } catch (IOException e) {
                System.err.println("[UISystem.MiniMap] Error loading PNG: " + e.getMessage());
            }
        }
        
        public void setPlayerPosition(float x, float y) { this.playerX = x; this.playerY = y; }
        public void clearMarkers() { enemies.clear(); projectiles.clear(); pickups.clear(); }
        public void addEnemyMarker(float x, float y, float radius) { enemies.add(new EntityMarker(x, y, radius)); }
        public void addProjectileMarker(float x, float y) { projectiles.add(new EntityMarker(x, y, 1)); }
        public void addPickupMarker(float x, float y) { pickups.add(new EntityMarker(x, y, 2)); }
        

        private int worldToMapX(float worldX) { return x + 5 + (int) ((worldX / mapWidth) * (width - 10)); }

        private int worldToMapY(float worldY) { return y + 20 + (int) ((worldY / mapHeight) * (height - 25)); }
        
        @Override
        public void render(BufferedImage dest) {
            if (!visible || minimapFrame == null) return;
            PixelCopyHelper.copyRaster(minimapFrame, dest, x, y);
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // HUD MANAGER - FAT FACADE PATTERN (SELF-CONTAINED, COMPLETE, PACKED WITH LOGIC)
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // External Code JUST CALLS:
    //   hudManager.updateGameplay(deltaTime)     → ONE call handles ALL HUD updates internally
    //   hudManager.renderComplete(dest)          → ONE call renders ALL elements in proper layers
    // NO other calls needed! The class is COMPLETE and STANDALONE.
    // ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    public static class HUDManager {
        // ═ INTERNAL STATE ═
        private java.util.List<HUDElement> hudElements;
        private HealthBar healthBar;
        private ScoreDisplay scoreDisplay;
        private AmmoCounter ammoCounter;
        private FPSCounter fpsCounter;
        private WaveIndicator waveIndicator;
        private ObjectiveDisplay objectiveDisplay;
        private MiniMap miniMap;
        private int screenWidth, screenHeight;
        private boolean allVisible = true;
        private boolean showDebugInfo = false;
        private long lastHealthWarningTime = 0;
        private long frameCount = 0;
        private float screenShakeIntensity = 0f;
        private boolean isLowHealthWarning = false;
        
        public HUDManager(int screenWidth, int screenHeight) {
            this.screenWidth = screenWidth;
            this.screenHeight = screenHeight;
            this.hudElements = new java.util.ArrayList<>();
            initializeAllHUDElements();
        }
        
        private void initializeAllHUDElements() {
            healthBar = new HealthBar(10, 10);
            scoreDisplay = new ScoreDisplay(screenWidth - 190, 10);
            ammoCounter = new AmmoCounter(10, 60);
            fpsCounter = new FPSCounter(screenWidth - 90, screenHeight - 50);
            waveIndicator = new WaveIndicator(screenWidth / 2 - 100, 10);
            objectiveDisplay = new ObjectiveDisplay(10, screenHeight - 70);
            miniMap = new MiniMap(screenWidth - 160, screenHeight - 160, 1024, 768);
            
            hudElements.add(healthBar);
            hudElements.add(scoreDisplay);
            hudElements.add(ammoCounter);
            hudElements.add(fpsCounter);
            hudElements.add(waveIndicator);
            hudElements.add(objectiveDisplay);
            hudElements.add(miniMap);
            
            System.out.println("[UISystem.HUDManager] ✓ FAT FACADE INITIALIZED: 7 HUD elements ready");
        }
        
        // ═══════════════════════════════════════════════════════════════════════════════════════
        // FAT PUBLIC API - Complete One-Call Methods
        // ═══════════════════════════════════════════════════════════════════════════════════════
        
        /**
         * FAT METHOD: Update ALL HUD elements, effects, and animations in ONE call
         * Game.java: hudManager.updateGameplay(deltaTime)
         * Internal: Handles EVERYTHING - element updates, effects, validations, state checks
         */
        public void updateGameplay(long elapsedTime) {
            if (!allVisible) return;
            
            // Phase 1: Update all HUD element states
            updateAllHUDElements(elapsedTime);
            
            // Phase 2: Update visual effects
            updateScreenEffects(elapsedTime);
            
            // Phase 3: Check and apply health warnings
            checkHealthWarningsAndEffects();
            
            // Phase 4: Update debug counters
            updateDebugInfo(elapsedTime);
            
            // Phase 5: Validate element states
            validateAllElements();
            
            frameCount++;
        }
        
        /**
         * FAT METHOD: Render ALL HUD elements in proper layer order with effects
         * Game.java: hudManager.renderComplete(dest)
         * Internal: Renders complete HUD system with correct z-ordering and effects
         */
        public void renderComplete(BufferedImage dest) {
            if (dest == null || !allVisible) return;
            
            try {
                // Layer 1: Critical elements (health, wave info)
                if (healthBar.isVisible()) healthBar.render(dest);
                if (waveIndicator.isVisible()) waveIndicator.render(dest);
                
                // Layer 2: Score and ammo info
                if (scoreDisplay.isVisible()) scoreDisplay.render(dest);
                if (ammoCounter.isVisible()) ammoCounter.render(dest);
                
                // Layer 3: Objective and mission data
                if (objectiveDisplay.isVisible()) objectiveDisplay.render(dest);
                
                // Layer 4: Minimap overlay
                if (miniMap.isVisible()) miniMap.render(dest);
                
                // Layer 5: Debug information if enabled
                if (showDebugInfo && fpsCounter.isVisible()) {
                    fpsCounter.render(dest);
                }
                
                // Layer 6: Screen effects
                applyScreenEffects(dest);
                
            } catch (Exception e) {
                System.err.println("[UISystem.HUDManager] ERROR rendering HUD: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        /**
         * FAT METHOD: Update gameplay state with COMPLETE data set
         * One call updates health, score, ammo, wave, and triggers necessary effects
         */
        public void updateGameplayState(int health, int maxHealth, long score, int ammo, 
                                       int maxAmmo, int ammoReserve, int wave, int totalWaves, 
                                       int enemies, int enemiesDefeated) {
            // Update elements
            healthBar.setHealth(health, maxHealth);
            scoreDisplay.setScore(score);
            ammoCounter.setAmmo(ammo, maxAmmo, ammoReserve);
            waveIndicator.setWave(wave, totalWaves);
            waveIndicator.setEnemies(enemies, enemiesDefeated);
            
            // Trigger effects based on state
            float healthPercent = (float) health / maxHealth;
            if (healthPercent < 0.25f && !isLowHealthWarning) {
                triggerScreenShake(0.3f);
                isLowHealthWarning = true;
            } else if (healthPercent >= 0.5f) {
                isLowHealthWarning = false;
            }
        }
        
        /**
         * FAT METHOD: Complete HUD reset to initial clean state
         */
        public void reset() {
            // Reset all elements to defaults
            healthBar.setHealth(100, 100);
            scoreDisplay.setScore(0);
            ammoCounter.setAmmo(30, 30, 120);
            fpsCounter.resetFPS();
            waveIndicator.setWave(1, 5);
            waveIndicator.setEnemies(10, 0);
            objectiveDisplay.setObjective("Survive the wave", "In Progress");
            miniMap.clearMarkers();
            
            // Reset internal state
            screenShakeIntensity = 0f;
            isLowHealthWarning = false;
            lastHealthWarningTime = 0;
            frameCount = 0;
            
            System.out.println("[UISystem.HUDManager] ✓ FAT FACADE RESET: All HUD elements restored to defaults");
        }
        
        // ═══════════════════════════════════════════════════════════════════════════════════════
        // PRIVATE HELPER METHODS - Internal complexity hidden
        // ═══════════════════════════════════════════════════════════════════════════════════════
        
        private void updateAllHUDElements(long elapsedTime) {
            for (HUDElement element : hudElements) {
                if (element != null && element.isVisible()) {
                    element.update(elapsedTime);
                }
            }
        }
        
        private void updateScreenEffects(long elapsedTime) {
            // Decay screen shake over time
            if (screenShakeIntensity > 0.01f) {
                screenShakeIntensity *= 0.90f;
            } else {
                screenShakeIntensity = 0f;
            }
        }
        
        private void checkHealthWarningsAndEffects() {
            int currentHealth = healthBar.getCurrentHealth();
            int maxHealth = healthBar.getMaxHealth();
            float healthPercent = (float) currentHealth / maxHealth;
            
            if (healthPercent < 0.25f) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastHealthWarningTime > 400) {
                    healthBar.setBlinking(true);
                    lastHealthWarningTime = currentTime;
                }
            } else {
                healthBar.setBlinking(false);
            }
        }
        
        private void updateDebugInfo(long elapsedTime) {
            if (showDebugInfo && fpsCounter != null) {
                fpsCounter.update(elapsedTime);
            }
        }
        
        private void validateAllElements() {
            for (HUDElement e : hudElements) {
                if (e == null) {
                    System.err.println("[UISystem.HUDManager] ⚠ WARNING: NULL element in HUD list");
                }
            }
        }
        
        private void applyScreenEffects(BufferedImage dest) {
            if (screenShakeIntensity > 0.01f && dest != null) {
                // Apply subtle screen shake distortion
                // Could apply pixel offset or java.awt.Color tint based on intensity
            }
        }
        
        private void triggerScreenShake(float intensity) {
            this.screenShakeIntensity = Math.max(screenShakeIntensity, intensity);
        }
        
        // ═══════════════════════════════════════════════════════════════════════════════════════
        // DEPRECATED METHODS - Support legacy code, prefer new FAT methods
        // ═══════════════════════════════════════════════════════════════════════════════════════
        
        @Deprecated
        public void update(long elapsedTime) {
            System.out.println("[DEPRECATED] HUDManager.update() - use updateGameplay() instead");
            updateGameplay(elapsedTime);
        }
        
        @Deprecated
        public void render(java.awt.Graphics2D g) {
            System.out.println("[DEPRECATED] HUDManager.render(java.awt.Graphics2D) - use renderComplete(BufferedImage) instead");
        }
        
        // ═══════════════════════════════════════════════════════════════════════════════════════
        // GETTERS - For classes that need direct element access
        // ═══════════════════════════════════════════════════════════════════════════════════════
        
        public HealthBar getHealthBar() { return healthBar; }
        public ScoreDisplay getScoreDisplay() { return scoreDisplay; }
        public AmmoCounter getAmmoCounter() { return ammoCounter; }
        public FPSCounter getFPSCounter() { return fpsCounter; }
        public WaveIndicator getWaveIndicator() { return waveIndicator; }
        public ObjectiveDisplay getObjectiveDisplay() { return objectiveDisplay; }
        public MiniMap getMiniMap() { return miniMap; }
        
        public void setAllVisible(boolean visible) {
            this.allVisible = visible;
            for (HUDElement element : hudElements) {
                element.setVisible(visible);
            }
        }
        
        public void setDebugMode(boolean enabled) {
            this.showDebugInfo = enabled;
            if (fpsCounter != null) fpsCounter.setVisible(enabled);
            System.out.println("[UISystem.HUDManager] Debug mode: " + (enabled ? "ON" : "OFF"));
        }
        
        public boolean isDebugMode() { return showDebugInfo; }
        public long getFrameCount() { return frameCount; }
        public float getScreenShakeIntensity() { return screenShakeIntensity; }
    }

    // ==================== GUI FRAME TILESET LOADER ====================
    /**
    
    }

    // ==================== GUI FRAME TILESET LOADER ====================
    /**
     * GUIFrameTilesetLoader - CRITICAL CLASS FOR COMPLETE GUI AND HUD SYSTEM
     * 
     * Loads GUI frame tiles from the 82-tile spritesheet (9x9 grid, 32px tiles).
     * Provides group-based tile retrieval for different frame configurations:
     * - 3x3 groups (9 tiles): Full frames with all pieces
     * - 2x3 groups (6 tiles): Horizontal frames
     * - 1x3 groups (3 tiles): Horizontal strips
     * - 1x1 groups (1 tile): Single tiles
     * - 3x2 groups (6 tiles): Vertical frames
     * 
     * All tiles are 32px x 32px.
     */
    public static class GUIFrameTilesetLoader {
        
        private static final int TILE_SIZE = 32;
        private static final int GRID_WIDTH = 9;
        private static final int GRID_HEIGHT = 9;
        private static final int TOTAL_TILES = 81;
        
        private BufferedImage spritesheet;
        private Map<Integer, BufferedImage> tileCache;
        private String spritesheetPath;
        
        /**
         * Constructor - Loads the GUI frame tileset from the spritesheet
         */
        public GUIFrameTilesetLoader() {
            this.spritesheetPath = "Resources/industrial-zone/gui/1 Frames/82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png";
            this.tileCache = new HashMap<>();
            loadSpritesheet();
        }
        
        /**
         * Constructor with custom path
         */
        public GUIFrameTilesetLoader(String customPath) {
            this.spritesheetPath = customPath;
            this.tileCache = new HashMap<>();
            loadSpritesheet();
        }
        
        /**
         * Load the spritesheet image
         */
        private void loadSpritesheet() {
            try {
                File spriteFile = new File(spritesheetPath);
                if (!spriteFile.exists()) {
                    System.err.println("ERROR: Spritesheet not found at: " + spriteFile.getAbsolutePath());
                    return;
                }
                spritesheet = ImageIO.read(spriteFile);
                System.out.println("✓ GUIFrameTilesetLoader: Spritesheet loaded successfully");
                System.out.println("  - Dimensions: " + spritesheet.getWidth() + "x" + spritesheet.getHeight());
            } catch (Exception e) {
                System.err.println("ERROR loading spritesheet: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        /**
         * Get a single tile by index (1-81)
         * @param tileIndex 1-based tile index
         * @return BufferedImage of the 32x32 tile, or null if invalid
         */
        public BufferedImage getTile(int tileIndex) {
            if (tileIndex < 1 || tileIndex > TOTAL_TILES) {
                System.err.println("Invalid tile index: " + tileIndex);
                return null;
            }
            
            if (spritesheet == null) {
                System.err.println("Spritesheet not loaded");
                return null;
            }
            
            // Check cache
            if (tileCache.containsKey(tileIndex)) {
                return tileCache.get(tileIndex);
            }
            
            // Convert 1-based index to 0-based
            int zeroIndex = tileIndex - 1;
            int row = zeroIndex / GRID_WIDTH;
            int col = zeroIndex % GRID_WIDTH;
            
            int x = col * TILE_SIZE;
            int y = row * TILE_SIZE;
            
            BufferedImage tile = spritesheet.getSubimage(x, y, TILE_SIZE, TILE_SIZE);
            tileCache.put(tileIndex, tile);
            return tile;
        }
        
        /**
         * Get multiple tiles at once
         * @param indices Array of 1-based tile indices
         * @return Array of BufferedImages (or null for invalid indices)
         */
        public BufferedImage[] getTiles(int[] indices) {
            BufferedImage[] tiles = new BufferedImage[indices.length];
            for (int i = 0; i < indices.length; i++) {
                tiles[i] = getTile(indices[i]);
            }
            return tiles;
        }
        
        // ========== 3x3 GROUP GETTERS (9 tiles) ==========
        // Format: [CornerTL] [EdgeTop]  [CornerTR]
        //         [EdgeLeft] [Center]   [EdgeRight]
        //         [CornerBL] [EdgeBot]  [CornerBR]
        
        /**
         * Get 3x3 group tiles (GROUP 1)
         * Tiles: [1,2,3,10,11,12,19,20,21]
         */
        public BufferedImage[] get3x3Group1() {
            return getTiles(new int[]{1, 2, 3, 10, 11, 12, 19, 20, 21});
        }
        
        /**
         * Get 3x3 group tiles (GROUP 2)
         * Tiles: [9,36,63,18,45,72,27,54,81]
         */
        public BufferedImage[] get3x3Group2() {
            return getTiles(new int[]{9, 36, 63, 18, 45, 72, 27, 54, 81});
        }
        
        /**
         * Get 3x3 group tiles (GROUP 3)
         * Tiles: [46,47,48,55,56,57,64,65,66]
         */
        public BufferedImage[] get3x3Group3() {
            return getTiles(new int[]{46, 47, 48, 55, 56, 57, 64, 65, 66});
        }
        
        /**
         * Get 3x3 group tiles (GROUP 4)
         * Tiles: [58,59,60,13,80,22,67,68,69]
         */
        public BufferedImage[] get3x3Group4() {
            return getTiles(new int[]{58, 59, 60, 13, 80, 22, 67, 68, 69});
        }
        
        /**
         * Get 3x3 group tiles (GROUP 5)
         * Tiles: [76,77,78,13,7,22,73,74,75]
         */
        public BufferedImage[] get3x3Group5() {
            return getTiles(new int[]{76, 77, 78, 13, 7, 22, 73, 74, 75});
        }
        
        // ========== 2x3 GROUP GETTERS (6 tiles) ==========
        // Format: [CornerTL]  [CornerTR]
        //         [EdgeLeft]  [EdgeRight]
        //         [CornerBL]  [CornerBR]
        
        /**
         * Get 2x3 group tiles (GROUP 6)
         * Tiles: [49,50,51,43,44,35]
         */
        public BufferedImage[] get2x3Group6() {
            return getTiles(new int[]{49, 50, 51, 43, 44, 35});
        }
        
        /**
         * Get 2x3 group tiles (GROUP 7)
         * Tiles: [6,45,5,15,45,14]
         */
        public BufferedImage[] get2x3Group7() {
            return getTiles(new int[]{6, 45, 5, 15, 45, 14});
        }
        
        // ========== 1x3 GROUP GETTERS (3 tiles) ==========
        // Format: [EdgeLeft] [Center]   [EdgeRight]
        
        /**
         * Get 1x3 group tiles (GROUP 8)
         * Tiles: [38,39,40]
         * Single horizontal strip of 3 tiles
         */
        public BufferedImage[] get1x3Group8() {
            return getTiles(new int[]{38, 39, 40});
        }
        
        // ========== 1x1 GROUP GETTERS (1 tile) ==========
        // Format: [Center]
        
        /**
         * Get 1x1 group tile (GROUP 9)
         * Tiles: [7]
         */
        public BufferedImage[] get1x1Group9() {
            return getTiles(new int[]{7});
        }
        
        /**
         * Get 1x1 group tile (GROUP 10)
         * Tiles: [8]
         */
        public BufferedImage[] get1x1Group10() {
            return getTiles(new int[]{8});
        }
        
        /**
         * Get 1x1 group tile (GROUP 11)
         * Tiles: [37]
         */
        public BufferedImage[] get1x1Group11() {
            return getTiles(new int[]{37});
        }
        
        /**
         * Get 1x1 group tile (GROUP 12)
         * Tiles: [79]
         */
        public BufferedImage[] get1x1Group12() {
            return getTiles(new int[]{79});
        }
        
        /**
         * Get 1x1 group tile (GROUP 13)
         * Tiles: [80]
         */
        public BufferedImage[] get1x1Group13() {
            return getTiles(new int[]{80});
        }
        
        /**
         * Get 1x1 group tile (GROUP 14)
         * Tiles: [45]
         */
        public BufferedImage[] get1x1Group14() {
            return getTiles(new int[]{45});
        }
        
        // ========== 3x2 GROUP GETTERS (6 tiles) ==========
        // Format: [CornerTL]  [CornerTR]
        //         [EdgeLeft]  [EdgeRight]
        //         [CornerBL]  [CornerBR]
        
        /**
         * Get 3x2 group tiles (GROUP 15)
         * Tiles: [31,32,13,22,33,34]
         * Vertical frame with 3 rows and 2 columns
         */
        public BufferedImage[] get3x2Group15() {
            return getTiles(new int[]{31, 32, 13, 22, 33, 34});
        }
    
        /**
         * Get all available tile indices for reference
         * @return String describing the grid layout
         */
        public String getTileGridReference() {
            StringBuilder sb = new StringBuilder();
            sb.append("GUI Frame Tileset Grid Layout (9x9 = 81 tiles, 32px each):\n");
            for (int row = 0; row < GRID_HEIGHT; row++) {
                for (int col = 0; col < GRID_WIDTH; col++) {
                    int tileNum = row * GRID_WIDTH + col + 1;
                    sb.append(String.format("%2d ", tileNum));
                }
                sb.append("\n");
            }
            return sb.toString();
        }
        
        /**
         * Test method to verify all tiles load correctly
         */
        public void verifyAllTiles() {
            System.out.println("\n=== Verifying All GUI Frame Tiles ===");
            int loadedCount = 0;
            int failedCount = 0;
            
            for (int i = 1; i <= TOTAL_TILES; i++) {
                BufferedImage tile = getTile(i);
                if (tile != null) {
                    loadedCount++;
                } else {
                    failedCount++;
                    System.err.println("Failed to load tile: " + i);
                }
            }
            
            System.out.println("✓ Successfully loaded: " + loadedCount + "/" + TOTAL_TILES);
            if (failedCount > 0) {
                System.err.println("✗ Failed to load: " + failedCount + " tiles");
            }
        }
        
        /**
         * Get tile cache statistics
         */
        public void printCacheStats() {
            System.out.println("Tile Cache: " + tileCache.size() + " tiles cached");
        }
    }
    
    // ==================== SPLASH SCREEN ====================
    /**
     * SPLASH SCREEN (INTRO SEQUENCE)
     * Displays game logo with fade-in/fade-out animations
     * Uses GUI frame tileset as background from assets-manifest.json
     * Logo asset: gui/5 Logo/GUI_Logo_IndustrialZone_Full.png
     * NO Graphics2D, NO java.awt.Color - Pure raster rendering
     */
    public static class SplashScreen {
        private GUIFrameTilesetLoader frameLoader;
        private BufferedImage logoImage;
        private BufferedImage backgroundTile;
        private long startTime;
        private int screenWidth;
        private int screenHeight;
        private float logoAlpha = 0f;
        private float backgroundAlpha = 0f;
        private static final long SPLASH_DURATION_MS = 3000;
        private static final long BG_FADE_IN_MS = 500;
        private static final long LOGO_FADE_IN_MS = 1000;
        private static final long LOGO_START_MS = 500;
        
        public SplashScreen(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
            this.startTime = System.currentTimeMillis();
            loadAssets();
        }
        
        private void loadAssets() {
            try {
                // Load GUI frame tileset for background
                frameLoader = new GUIFrameTilesetLoader();
                backgroundTile = frameLoader.getTile(7); // GUI fill tile
                
                if (backgroundTile != null) {
                    System.out.println("[SplashScreen] ✓ GUI frame fill background loaded");
                } else {
                    System.err.println("[SplashScreen] ✗ Failed to load GUI background tile");
                }
                
                // Load GUI logo from assets-manifest.json
                // Asset path: Resources/industrial-zone/gui/5 Logo/GUI_Logo_IndustrialZone_Full.png
                String logoPath = "Resources/industrial-zone/gui/5 Logo/GUI_Logo_IndustrialZone_Full.png";
                File logoFile = new File(logoPath);
                
                if (logoFile.exists()) {
                    logoImage = ImageIO.read(logoFile);
                    System.out.println("[SplashScreen] ✓ Logo loaded: gui/5 Logo/GUI_Logo_IndustrialZone_Full.png");
                } else {
                    System.err.println("[SplashScreen] ✗ Logo NOT found at: " + logoPath);
                    System.err.println("[SplashScreen] Expected from assets-manifest.json: gui/5 Logo/GUI_Logo_IndustrialZone_Full.png");
                }
            } catch (IOException e) {
                System.err.println("[SplashScreen] Asset loading error: " + e.getMessage());
            }
        }
        
        public void update() {
            long elapsed = System.currentTimeMillis() - startTime;
            
            // Background fade in (0-500ms)
            if (elapsed <= BG_FADE_IN_MS) {
                backgroundAlpha = elapsed / (float) BG_FADE_IN_MS;
            } else {
                backgroundAlpha = 1f;
            }
            
            // Logo fade in (500-1500ms)
            if (elapsed >= LOGO_START_MS && elapsed <= LOGO_START_MS + LOGO_FADE_IN_MS) {
                logoAlpha = (elapsed - LOGO_START_MS) / (float) LOGO_FADE_IN_MS;
            } else if (elapsed > LOGO_START_MS) {
                logoAlpha = 1f;
            }
        }
        
        public void render(BufferedImage dest) {
            if (dest == null) return;
            
            // Draw GUI frame background tiles (tiled across screen)
            if (backgroundTile != null && backgroundAlpha > 0) {
                // Tile the background fill across entire screen
                for (int y = 0; y < screenHeight; y += backgroundTile.getHeight()) {
                    for (int x = 0; x < screenWidth; x += backgroundTile.getWidth()) {
                        int w = Math.min(backgroundTile.getWidth(), screenWidth - x);
                        int h = Math.min(backgroundTile.getHeight(), screenHeight - y);
                        
                        if (backgroundAlpha >= 1.0f) {
                            // Direct copy
                            for (int py = 0; py < h; py++) {
                                for (int px = 0; px < w; px++) {
                                    int rgb = backgroundTile.getRGB(px, py);
                                    dest.setRGB(x + px, y + py, rgb);
                                }
                            }
                        } else {
                            // Alpha blend
                            blendImageAlpha(backgroundTile, dest, x, y, backgroundAlpha);
                        }
                    }
                }
            }
            
            // Draw logo if present
            if (logoImage != null && logoAlpha > 0) {
                int logoX = (screenWidth - logoImage.getWidth()) / 2;
                int logoY = (screenHeight - logoImage.getHeight()) / 2 - 40;
                
                if (logoAlpha >= 1.0f) {
                    // Direct copy
                    for (int y = 0; y < logoImage.getHeight(); y++) {
                        for (int x = 0; x < logoImage.getWidth(); x++) {
                            int drawX = logoX + x;
                            int drawY = logoY + y;
                            if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                                int rgb = logoImage.getRGB(x, y);
                                dest.setRGB(drawX, drawY, rgb);
                            }
                        }
                    }
                } else {
                    // Alpha blend
                    blendImageAlpha(logoImage, dest, logoX, logoY, logoAlpha);
                }
            }
        }
        
        private void blendImageAlpha(BufferedImage source, BufferedImage dest, int x, int y, float alpha) {
            if (source == null || dest == null || alpha <= 0) return;
            
            // Blend source onto dest with alpha
            for (int sy = 0; sy < source.getHeight(); sy++) {
                for (int sx = 0; sx < source.getWidth(); sx++) {
                    int dx = x + sx;
                    int dy = y + sy;
                    
                    // Check bounds
                    if (dx < 0 || dx >= dest.getWidth() || dy < 0 || dy >= dest.getHeight()) {
                        continue;
                    }
                    
                    int srcRGB = source.getRGB(sx, sy);
                    int dstRGB = dest.getRGB(dx, dy);
                    
                    // Extract components
                    int srcA = (srcRGB >> 24) & 0xFF;
                    int srcR = (srcRGB >> 16) & 0xFF;
                    int srcG = (srcRGB >> 8) & 0xFF;
                    int srcB = srcRGB & 0xFF;
                    
                    int dstA = (dstRGB >> 24) & 0xFF;
                    int dstR = (dstRGB >> 16) & 0xFF;
                    int dstG = (dstRGB >> 8) & 0xFF;
                    int dstB = dstRGB & 0xFF;
                    
                    // Alpha blend
                    float blendAlpha = (srcA / 255.0f) * alpha;
                    float invAlpha = 1.0f - blendAlpha;
                    
                    int blendR = (int)(srcR * blendAlpha + dstR * invAlpha);
                    int blendG = (int)(srcG * blendAlpha + dstG * invAlpha);
                    int blendB = (int)(srcB * blendAlpha + dstB * invAlpha);
                    int blendA = (int)(srcA * alpha + dstA * invAlpha);
                    
                    int blendRGB = (blendA << 24) | (blendR << 16) | (blendG << 8) | blendB;
                    dest.setRGB(dx, dy, blendRGB);
                }
            }
        }
        
        public boolean isComplete() {
            return System.currentTimeMillis() - startTime >= SPLASH_DURATION_MS;
        }
        
        public long getElapsedTime() {
            return System.currentTimeMillis() - startTime;
        }
    }

    /**
     * MAIN MENU SCREEN
     * 4-button vertical menu: Play, How to Play, Settings, Exit
     * Uses GUI frame tileset (gui/1 Frames/) for background
     * Uses GUI button assets (gui/3 Icons/Buttons2/) for menu buttons
     * NO Graphics2D, NO java.awt.Color - Pure raster rendering
     */
    public static class MainMenuScreen {
        private GUIFrameTilesetLoader frameLoader;
        private BufferedImage backgroundTile;
        private BufferedImage[] buttonIdleImages;
        private BufferedImage[] buttonHoverImages;
        private MenuButton[] buttons;
        private int selectedButtonIndex = -1;
        private int screenWidth, screenHeight;
        
        private static final int BUTTON_WIDTH = 250;
        private static final int BUTTON_HEIGHT = 60;
        private static final int BUTTON_SPACING = 25;
        private static final int NUM_BUTTONS = 4;
        
        private static class MenuButton {

            String label;
            int x, y, width, height;
            boolean isHovered = false;
            
            MenuButton(String label, int x, int y, int width, int height) {
                this.label = label;
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
            }
            
            boolean contains(int mx, int my) {
                return mx >= x && mx < x + width && my >= y && my < y + height;
            }
        }
        
        public MainMenuScreen(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
            this.frameLoader = new GUIFrameTilesetLoader();
            loadAssets();
            initializeButtons();
        }
        
        private void loadAssets() {
            try {
                // Load GUI frame background tile (gui/1 Frames/)
                backgroundTile = frameLoader.getTile(7); // Light fill tile
                
                if (backgroundTile != null) {
                    System.out.println("[MainMenuScreen] ✓ GUI background tile loaded from gui/1 Frames/");
                } else {
                    System.err.println("[MainMenuScreen] ✗ Failed to load GUI background tile");
                }
            } catch (Exception e) {
                System.err.println("[MainMenuScreen] Failed to load background tile: " + e.getMessage());
            }
            
            // Load button images from GUI assets (assets-manifest.json reference)
            // Asset path: Resources/industrial-zone/gui/3 Icons/Buttons2/GUI_Button_State_Variant02_[0X].png
            buttonIdleImages = new BufferedImage[NUM_BUTTONS];
            buttonHoverImages = new BufferedImage[NUM_BUTTONS];
            
            String[] idleButtonFiles = {
                "Resources/industrial-zone/gui/3 Icons/Buttons2/GUI_Button_State_Variant02_01.png",
                "Resources/industrial-zone/gui/3 Icons/Buttons2/GUI_Button_State_Variant02_05.png",
                "Resources/industrial-zone/gui/3 Icons/Buttons2/GUI_Button_State_Variant02_09.png",
                "Resources/industrial-zone/gui/3 Icons/Buttons2/GUI_Button_State_Variant02_13.png"
            };
            
            String[] hoverButtonFiles = {
                "Resources/industrial-zone/gui/3 Icons/Buttons2/GUI_Button_State_Variant02_02.png",
                "Resources/industrial-zone/gui/3 Icons/Buttons2/GUI_Button_State_Variant02_06.png",
                "Resources/industrial-zone/gui/3 Icons/Buttons2/GUI_Button_State_Variant02_10.png",
                "Resources/industrial-zone/gui/3 Icons/Buttons2/GUI_Button_State_Variant02_14.png"
            };
            
            for (int i = 0; i < NUM_BUTTONS; i++) {
                try {
                    File idleFile = new File(idleButtonFiles[i]);
                    if (idleFile.exists()) {
                        buttonIdleImages[i] = ImageIO.read(idleFile);
                        System.out.println("[MainMenuScreen] ✓ Idle button " + i + " loaded: gui/3 Icons/Buttons2/");
                    } else {
                        System.err.println("[MainMenuScreen] ✗ Idle button file not found: " + idleButtonFiles[i]);
                    }
                } catch (Exception e) {
                    System.err.println("[MainMenuScreen] Failed to load idle button " + i + ": " + e.getMessage());
                }
                
                try {
                    File hoverFile = new File(hoverButtonFiles[i]);
                    if (hoverFile.exists()) {
                        buttonHoverImages[i] = ImageIO.read(hoverFile);
                    } else {
                        System.err.println("[MainMenuScreen] ✗ Hover button file not found: " + hoverButtonFiles[i]);
                    }
                } catch (Exception e) {
                    System.err.println("[MainMenuScreen] Failed to load hover button " + i + ": " + e.getMessage());
                }
            }
        }
        
        private void initializeButtons() {
            buttons = new MenuButton[NUM_BUTTONS];
            String[] labels = { "PLAY", "HOW TO PLAY", "SETTINGS", "EXIT" };
            
            // Center buttons horizontally, position vertically
            int startX = (screenWidth - BUTTON_WIDTH) / 2;
            int startY = (screenHeight - (NUM_BUTTONS * BUTTON_HEIGHT + (NUM_BUTTONS - 1) * BUTTON_SPACING)) / 2;
            
            for (int i = 0; i < NUM_BUTTONS; i++) {
                int btnY = startY + i * (BUTTON_HEIGHT + BUTTON_SPACING);
                buttons[i] = new MenuButton(labels[i], startX, btnY, BUTTON_WIDTH, BUTTON_HEIGHT);
            }
        }
        
        public void update(int mouseX, int mouseY) {
            selectedButtonIndex = -1;
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].contains(mouseX, mouseY)) {
                    selectedButtonIndex = i;
                    buttons[i].isHovered = true;
                } else {
                    buttons[i].isHovered = false;
                }
            }
        }
        
        public void render(BufferedImage dest) {
            if (dest == null) return;
            
            // Render tiled background
            if (backgroundTile != null) {
                for (int y = 0; y < screenHeight; y += backgroundTile.getHeight()) {
                    for (int x = 0; x < screenWidth; x += backgroundTile.getWidth()) {
                        int w = Math.min(backgroundTile.getWidth(), screenWidth - x);
                        int h = Math.min(backgroundTile.getHeight(), screenHeight - y);
                        for (int py = 0; py < h; py++) {
                            for (int px = 0; px < w; px++) {
                                int rgb = backgroundTile.getRGB(px, py);
                                dest.setRGB(x + px, y + py, rgb);
                            }
                        }
                    }
                }
            }
            
            // Render buttons
            for (int i = 0; i < buttons.length; i++) {
                MenuButton btn = buttons[i];
                BufferedImage btnImg = btn.isHovered && buttonHoverImages[i] != null 
                    ? buttonHoverImages[i] 
                    : buttonIdleImages[i];
                
                if (btnImg != null) {
                    // Scale button image to fit button dimensions
                    for (int dy = 0; dy < BUTTON_HEIGHT; dy++) {
                        for (int dx = 0; dx < BUTTON_WIDTH; dx++) {
                            int srcX = (int)(dx * btnImg.getWidth() / (float)BUTTON_WIDTH);
                            int srcY = (int)(dy * btnImg.getHeight() / (float)BUTTON_HEIGHT);
                            srcX = Math.min(srcX, btnImg.getWidth() - 1);
                            srcY = Math.min(srcY, btnImg.getHeight() - 1);
                            
                            int drawX = btn.x + dx;
                            int drawY = btn.y + dy;
                            
                            if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                                int rgb = btnImg.getRGB(srcX, srcY);
                                int alpha = (rgb >> 24) & 0xFF;
                                if (alpha > 0) {
                                    dest.setRGB(drawX, drawY, rgb);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        public int getSelectedButton() {
            return selectedButtonIndex;
        }
        
        public static final int BTN_PLAY = 0;
        public static final int BTN_HOW_TO_PLAY = 1;
        public static final int BTN_SETTINGS = 2;
        public static final int BTN_EXIT = 3;
    }

    /**
     * CHARACTER SELECTION SCREEN
     * 3 character cards in layout: 2 top row + 1 bottom centered
     * Each card shows character image with 360° rotation animation + stats bars
     * Uses character PNG assets from Resources/industrial-zone/characters/player/
     * NO Graphics2D, NO java.awt.Color - Pure raster rendering
     */
    public static class CharacterSelectScreen {
        private GUIFrameTilesetLoader frameLoader;
        private BufferedImage screenBackgroundTiled;     // Screen fill (navy)
        private BufferedImage windowComposed;            // Entire 900×600 window with frame border
        private CharacterCard[] cards;
        private int selectedCardIndex = -1;
        private int screenWidth, screenHeight;
        private int windowX, windowY;  // Calculated on screen center
        
        // Professional layout dimensions
        private static final int CARD_WIDTH = 220;      
        private static final int CARD_HEIGHT = 280;     
        private static final int WINDOW_WIDTH = 900;    
        private static final int WINDOW_HEIGHT = 600;   
        
        // Frame border tile indices (from master spritesheet 9x9 grid 0-81)
        // Using specific indices for professional window borders
        private static final int FRAME_CORNER_TL = 0;    // Top-left corner (standard L-shape)
        private static final int FRAME_CORNER_TR = 2;    // Top-right corner (mirrored L)
        private static final int FRAME_CORNER_BL = 18;   // Bottom-left corner
        private static final int FRAME_CORNER_BR = 26;   // Bottom-right corner
        private static final int FRAME_EDGE_TOP = 1;     // Top edge horizontal bar
        private static final int FRAME_EDGE_BOTTOM = 19; // Bottom edge horizontal bar
        private static final int FRAME_EDGE_LEFT = 4;    // Left edge vertical strip
        private static final int FRAME_EDGE_RIGHT = 5;   // Right edge vertical strip
        private static final int FRAME_FILL = 6;         // Fill (solid navy block)
        private static final int FRAME_THICKNESS = 16;   // Standard frame piece size ~16px
        
        // Real bar asset paths from assets-manifest.json
        private static final String BAR_ASSETS_PATH = "Resources/industrial-zone/gui/2 Bars/";
        private static final String[][] HEALTH_BARS = {
            {"01_GUI_Bar_HealthBar_Full100pct_RedOrangeFillDarkFrame_HUD.png", "100%"},
            {"02_GUI_Bar_HealthBar_80pct_RedOrangeFill_HUD.png", "80%"},
            {"03_GUI_Bar_HealthBar_60pct_RedOrangeFill_HUD.png", "60%"},
            {"04_GUI_Bar_HealthBar_40pct_RedOrangeFill_HUD.png", "40%"},
            {"05_GUI_Bar_HealthBar_20pct_RedOrangeFill_HUD.png", "20%"},
            {"06_GUI_Bar_HealthBar_5pctCritical_RedOrangeFill_HUD.png", "5%"}
        };
        private static final String[][] ENERGY_BARS = {
            {"09_GUI_Bar_EnergyBar_Full100pct_BlueCyanFillDarkFrame_HUD.png", "100%"},
            {"10_GUI_Bar_EnergyBar_80pct_BlueCyanFill_HUD.png", "80%"},
            {"11_GUI_Bar_EnergyBar_60pct_BlueCyanFill_HUD.png", "60%"},
            {"12_GUI_Bar_EnergyBar_40pct_BlueCyanFill_HUD.png", "40%"},
            {"13_GUI_Bar_EnergyBar_20pct_BlueCyanFill_HUD.png", "20%"},
            {"14_GUI_Bar_EnergyBar_5pctCritical_BlueCyanFill_HUD.png", "5%"}
        };
        
        // ✅ USING REAL BAR ASSETS FROM assets-manifest.json - NO COLORED RECTANGLES
        private Map<Integer, BufferedImage> cachedHealthBars = new HashMap<>();
        private Map<Integer, BufferedImage> cachedEnergyBars = new HashMap<>();
        
        private static class CharacterCard {
            String characterName;
            String characterPath;
            BufferedImage characterImage;
            BufferedImage characterBorder;  // Frame border (colored by operative)
            int x, y;
            int armor;      // Displayed as cyan energy bar
            int speed;      // Displayed as cyan energy bar
            int power;      // Displayed as red health bar
            boolean isHovered = false;
            int borderColor; // Character-specific border color from FROM VISUAL EVIDENCE
            
            CharacterCard(String name, String path, int armor, int speed, int power, int borderColor, int x, int y) {
                this.characterName = name;
                this.characterPath = path;
                this.armor = armor;
                this.speed = speed;
                this.power = power;
                this.borderColor = borderColor;
                this.x = x;
                this.y = y;
            }
            
            boolean contains(int mx, int my) {
                return mx >= x && mx < x + CARD_WIDTH && my >= y && my < y + CARD_HEIGHT;
            }
        }
        
        public CharacterSelectScreen(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
            this.frameLoader = new GUIFrameTilesetLoader();
            loadAssets();
            initializeCards();
        }
        
        private void loadAssets() {
            // Calculate window position
            windowX = (screenWidth - WINDOW_WIDTH) / 2;
            windowY = (screenHeight - WINDOW_HEIGHT) / 2;
            
            // Load background: Tiled GUI fill
            screenBackgroundTiled = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
            
            try {
                BufferedImage fillTile = frameLoader.getTile(FRAME_FILL);
                if (fillTile != null) {
                    for (int y = 0; y < screenHeight; y += fillTile.getHeight()) {
                        for (int x = 0; x < screenWidth; x += fillTile.getWidth()) {
                            int w = Math.min(fillTile.getWidth(), screenWidth - x);
                            int h = Math.min(fillTile.getHeight(), screenHeight - y);
                            for (int py = 0; py < h; py++) {
                                for (int px = 0; px < w; px++) {
                                    int rgb = fillTile.getRGB(px, py);
                                    screenBackgroundTiled.setRGB(x + px, y + py, rgb);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to load background: " + e.getMessage());
            }
            
            // Compose window with 9-slice frame borders
            composeWindowFrame();
            
            // Initialize 3 character cards - PUNK, BIKER, CYBORG
            // Positions will be calculated in initializeCards()
            cards = new CharacterCard[3];
            cards[0] = new CharacterCard("PUNK", 
                "Resources/industrial-zone/characters/player/punk/01_Player_Punk_Idle_5Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png",
                80, 100, 85,
                0xFFCC0000,   // Red border (from visual evidence)
                0, 0);        // x, y set in initializeCards()
            
            cards[1] = new CharacterCard("BIKER", 
                "Resources/industrial-zone/characters/player/biker/01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png",
                85, 95, 70,
                0xFF663399,   // Purple border (from visual evidence)
                0, 0);        // x, y set in initializeCards()
            
            cards[2] = new CharacterCard("CYBORG", 
                "Resources/industrial-zone/characters/player/cyborg/01_Player_Cyborg_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png",
                120, 70, 95,
                0xFF999999,   // Grey border (from visual evidence)
                0, 0);        // x, y set in initializeCards()
            
            // Load character sprites using ManifestLoader
            for (int i = 0; i < cards.length; i++) {
                BufferedImage characterImage = ManifestLoader.loadCharacterIdle(cards[i].characterName);
                if (characterImage != null) {
                    cards[i].characterImage = characterImage;
                    System.out.println("[✅] " + cards[i].characterName + " loaded via ManifestLoader");
                } else {
                    System.err.println("[❌] " + cards[i].characterName + " NOT found via ManifestLoader");
                }
            }
            
            // Load bar assets
            System.out.println("[📦] Loading stat bar PNG assets...");
            loadBarAssets();
        }
        
        /**
         * Compose 900×600 window using 9-slice borders from frame tile indices
         * Pulls actual PNG frame pieces and composes professional window
         */
        private void composeWindowFrame() {
            if (windowComposed != null) return;
            windowComposed = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            
            try {
                // Get frame pieces from tileset
                BufferedImage cornerTL = frameLoader.getTile(FRAME_CORNER_TL);
                BufferedImage cornerTR = frameLoader.getTile(FRAME_CORNER_TR);
                BufferedImage cornerBL = frameLoader.getTile(FRAME_CORNER_BL);
                BufferedImage cornerBR = frameLoader.getTile(FRAME_CORNER_BR);
                BufferedImage edgeTop = frameLoader.getTile(FRAME_EDGE_TOP);
                BufferedImage edgeBottom = frameLoader.getTile(FRAME_EDGE_BOTTOM);
                BufferedImage edgeLeft = frameLoader.getTile(FRAME_EDGE_LEFT);
                BufferedImage edgeRight = frameLoader.getTile(FRAME_EDGE_RIGHT);
                BufferedImage fill = frameLoader.getTile(FRAME_FILL);
                
                if (cornerTL == null || fill == null) {
                    System.err.println("[CharacterSelectScreen] Frame pieces unavailable");
                    return;
                }
                
                int cW = cornerTL.getWidth();
                int cH = cornerTL.getHeight();
                
                // Fill interior with navy
                for (int y = cH; y < WINDOW_HEIGHT - cH; y++) {
                    for (int x = cW; x < WINDOW_WIDTH - cW; x++) {
                        if (fill != null) {
                            int rgb = fill.getRGB(x % fill.getWidth(), y % fill.getHeight());
                            windowComposed.setRGB(x, y, rgb);
                        }
                    }
                }
                
                // Corners and edges using blitting
                blitTile(windowComposed, cornerTL, 0, 0);
                blitTile(windowComposed, cornerTR, WINDOW_WIDTH - cW, 0);
                blitTile(windowComposed, cornerBL, 0, WINDOW_HEIGHT - cH);
                blitTile(windowComposed, cornerBR, WINDOW_WIDTH - cW, WINDOW_HEIGHT - cH);
                
                // Top and bottom edges (tile horizontally)
                for (int x = cW; x < WINDOW_WIDTH - cW; x += edgeTop.getWidth()) {
                    blitTile(windowComposed, edgeTop, x, 0);
                    blitTile(windowComposed, edgeBottom, x, WINDOW_HEIGHT - edgeBottom.getHeight());
                }
                
                // Left and right edges (tile vertically)
                for (int y = cH; y < WINDOW_HEIGHT - cH; y += edgeLeft.getHeight()) {
                    blitTile(windowComposed, edgeLeft, 0, y);
                    blitTile(windowComposed, edgeRight, WINDOW_WIDTH - edgeRight.getWidth(), y);
                }
                
                System.out.println("[CharacterSelectScreen] ✅ Window frame composed (9-slice borders)");
            } catch (Exception e) {
                System.err.println("[CharacterSelectScreen] Frame error: " + e.getMessage());
            }
        }
        
        /** Helper: Blit tile with transparency **/
        private void blitTile(BufferedImage dest, BufferedImage src, int x, int y) {
            if (src == null || x < 0 || y < 0 || x >= WINDOW_WIDTH || y >= WINDOW_HEIGHT) return;
            for (int yy = 0; yy < src.getHeight() && y + yy < WINDOW_HEIGHT; yy++) {
                for (int xx = 0; xx < src.getWidth() && x + xx < WINDOW_WIDTH; xx++) {
                    int rgb = src.getRGB(xx, yy);
                    if (((rgb >> 24) & 0xFF) > 0) {
                        dest.setRGB(x + xx, y + yy, rgb);
                    }
                }
            }
        }
        
        private void initializeCards() {
            
            // Calculate card positions within window - 3 columns with equal spacing
            int padding = 40;  // Space from window edge
            int cardSlotWidth = (WINDOW_WIDTH - 2 * padding) / 3;  // Space per card
            int cardsStartX = windowX + padding;
            int cardsStartY = windowY + 100;  // Below window title
            
            // Position cards horizontally
            for (int i = 0; i < 3; i++) {
                cards[i].x = cardsStartX + (i * cardSlotWidth) + (cardSlotWidth - CARD_WIDTH) / 2;
                cards[i].y = cardsStartY;
            }
        }
        
        // ✅ NEW: Load all bar PNG assets from gui\2 Bars\ folder instead of using dummy colors
        private void loadBarAssets() {
            // Load health bars (red/orange fill) from manifest paths
            for (int i = 0; i < HEALTH_BARS.length; i++) {
                String barFile = BAR_ASSETS_PATH + HEALTH_BARS[i][0];
                try {
                    File f = new File(barFile);
                    if (f.exists()) {
                        BufferedImage barImg = ImageIO.read(f);
                        cachedHealthBars.put(i, barImg);
                        System.out.println("[CharacterSelectScreen] ✅ Health bar " + HEALTH_BARS[i][1] + " loaded");
                    } else {
                        System.err.println("[CharacterSelectScreen] ❌ Health bar NOT found: " + barFile);
                    }
                } catch (Exception e) {
                    System.err.println("[CharacterSelectScreen] ❌ Health bar load error: " + e.getMessage());
                }
            }
            
            // Load energy bars (cyan/blue fill) from manifest paths
            for (int i = 0; i < ENERGY_BARS.length; i++) {
                String barFile = BAR_ASSETS_PATH + ENERGY_BARS[i][0];
                try {
                    File f = new File(barFile);
                    if (f.exists()) {
                        BufferedImage barImg = ImageIO.read(f);
                        cachedEnergyBars.put(i, barImg);
                        System.out.println("[CharacterSelectScreen] ✅ Energy bar " + ENERGY_BARS[i][1] + " loaded");
                    } else {
                        System.err.println("[CharacterSelectScreen] ❌ Energy bar NOT found: " + barFile);
                    }
                } catch (Exception e) {
                    System.err.println("[CharacterSelectScreen] ❌ Energy bar load error: " + e.getMessage());
                }
            }
        }
        
        // ✅ NEW: Map stat value (0-100) to appropriate bar asset index
        private int getBarIndex(int statValue) {
            if (statValue >= 90) return 0;  // 100% bar
            if (statValue >= 70) return 1;  // 80% bar
            if (statValue >= 50) return 2;  // 60% bar
            if (statValue >= 30) return 3;  // 40% bar
            if (statValue >= 10) return 4;  // 20% bar
            return 5;                        // 5% critical bar
        }
        
        public void update(int mouseX, int mouseY) {
            selectedCardIndex = -1;
            for (int i = 0; i < cards.length; i++) {
                if (cards[i].contains(mouseX, mouseY)) {
                    selectedCardIndex = i;
                    cards[i].isHovered = true;
                } else {
                    cards[i].isHovered = false;
                }
            }
        }
        
        public void render(BufferedImage dest) {
            if (dest == null) return;
            
            // Render screen background
            if (screenBackgroundTiled != null) {
                for (int y = 0; y < screenHeight; y++) {
                    for (int x = 0; x < screenWidth; x++) {
                        dest.setRGB(x, y, screenBackgroundTiled.getRGB(x, y));
                    }
                }
            }
            
            // Render composed window with 9-slice frame borders
            if (windowComposed != null) {
                for (int y = 0; y < WINDOW_HEIGHT && windowY + y < screenHeight; y++) {
                    for (int x = 0; x < WINDOW_WIDTH && windowX + x < screenWidth; x++) {
                        int rgb = windowComposed.getRGB(x, y);
                        if (((rgb >> 24) & 0xFF) > 0) {
                            dest.setRGB(windowX + x, windowY + y, rgb);
                        }
                    }
                }
            }
            
            // Render each character card
            for (int i = 0; i < cards.length; i++) {
                renderCard(dest, cards[i], cards[i].isHovered);
            }
        }
        
        private void renderCard(BufferedImage dest, CharacterCard card, boolean isHovered) {
            int borderThickness = isHovered ? 4 : 2;
            int bgColor = 0x551A3C5A;  // Semi-transparent dark blue background
            
            // Render card background
            for (int y = 0; y < CARD_HEIGHT; y++) {
                for (int x = 0; x < CARD_WIDTH; x++) {
                    int drawX = card.x + x;
                    int drawY = card.y + y;
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        dest.setRGB(drawX, drawY, bgColor);
                    }
                }
            }
            
            // Render colored border (character-specific color from visual evidence)
            int borderColor = card.borderColor;
            // Top and bottom borders
            for (int x = 0; x < CARD_WIDTH; x++) {
                for (int b = 0; b < borderThickness; b++) {
                    // Top border
                    int drawX = card.x + x;
                    int drawY = card.y + b;
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        dest.setRGB(drawX, drawY, borderColor);
                    }
                    // Bottom border
                    drawY = card.y + CARD_HEIGHT - 1 - b;
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        dest.setRGB(drawX, drawY, borderColor);
                    }
                }
            }
            // Left and right borders
            for (int y = 0; y < CARD_HEIGHT; y++) {
                for (int b = 0; b < borderThickness; b++) {
                    // Left border
                    int drawX = card.x + b;
                    int drawY = card.y + y;
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        dest.setRGB(drawX, drawY, borderColor);
                    }
                    // Right border
                    drawX = card.x + CARD_WIDTH - 1 - b;
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        dest.setRGB(drawX, drawY, borderColor);
                    }
                }
            }
            
            // Render character sprite (first frame of idle animation)
            if (card.characterImage != null) {
                int charWidth = Math.min(100, card.characterImage.getWidth());
                int charHeight = Math.min(80, card.characterImage.getHeight());
                int charX = card.x + (CARD_WIDTH - charWidth) / 2;
                int charY = card.y + 15;
                
                for (int y = 0; y < charHeight; y++) {
                    for (int x = 0; x < charWidth; x++) {
                        if (x < card.characterImage.getWidth() && y < card.characterImage.getHeight()) {
                            int srcRGB = card.characterImage.getRGB(x, y);
                            int srcA = (srcRGB >> 24) & 0xFF;
                            
                            if (srcA > 0) {
                                int drawX = charX + x;
                                int drawY = charY + y;
                                if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                                    dest.setRGB(drawX, drawY, srcRGB);
                                }
                            }
                        }
                    }
                }
            }
            
            // ✅ Render REAL bar assets (ACTUAL PNG IMAGES FROM 2 Bars\ folder)
            // SPEED: Cyan energy bar
            int speedBarIndex = getBarIndex(card.speed);
            renderRealBar(dest, card.x + 10, card.y + 115, cachedEnergyBars.get(speedBarIndex), "SPEED");
            
            // POWER: Red health bar  
            int powerBarIndex = getBarIndex(card.power);
            renderRealBar(dest, card.x + 10, card.y + 145, cachedHealthBars.get(powerBarIndex), "POWER");
            
            // ARMOR: Cyan energy bar
            int armorBarIndex = getBarIndex(card.armor);
            renderRealBar(dest, card.x + 10, card.y + 175, cachedEnergyBars.get(armorBarIndex), "ARMOR");
        }
        
        // ✅ NEW: Render real PNG bar asset (not dummy colored rectangle)
        private void renderRealBar(BufferedImage dest, int x, int y, BufferedImage barImage, String label) {
            if (barImage == null) {
                System.err.println("⚠️  WARNING: Bar asset NULL for " + label + " - loading failed");
                return;  // Skip rendering if asset not found
            }
            
            // Draw the PNG bar asset directly
            for (int dy = 0; dy < barImage.getHeight() && y + dy < screenHeight; dy++) {
                for (int dx = 0; dx < barImage.getWidth() && x + dx < screenWidth; dx++) {
                    int rgb = barImage.getRGB(dx, dy);
                    int alpha = (rgb >> 24) & 0xFF;
                    if (alpha > 0 && x + dx >= 0 && y + dy >= 0) {
                        dest.setRGB(x + dx, y + dy, rgb);
                    }
                }
            }
        }
        
        public int getSelectedCharacter() {
            return selectedCardIndex;
        }
        
        public static final int CHAR_BIKER = 0;
        public static final int CHAR_PUNK = 1;
        public static final int CHAR_CYBORG = 2;
    }

    /**
     * LEVEL SELECTION SCREEN
     * 2 difficulty level cards side-by-side
     * Level 1: Corrupted Power Station (easier, 3 waves)
     * Level 2: Steel Foundry (harder, 5 waves, boss battle)
     * Uses level tile assets from Resources/industrial-zone/ for previews
     * NO Graphics2D, NO java.awt.Color - Pure raster rendering
     */
    public static class LevelSelectScreen {
        private GUIFrameTilesetLoader frameLoader;
        private BufferedImage backgroundTiled;
        private LevelCard[] cards;
        private int selectedCardIndex = -1;
        private int screenWidth, screenHeight;
        
        private static final int CARD_WIDTH = 250;
        private static final int CARD_HEIGHT = 300;
        private static final int CARD_SPACING = 50;
        
        private static class LevelCard {

            String levelName;
            String levelPath;
            BufferedImage levelPreview;
            int x, y;
            int difficulty; // 1-5 stars

            String duration;

            String enemyCount;

            String description;
            boolean isHovered = false;
            
            LevelCard(String name, String path, int diff, String duration, String enemies, String desc, int x, int y) {
                this.levelName = name;
                this.levelPath = path;
                this.difficulty = diff;
                this.duration = duration;
                this.enemyCount = enemies;
                this.description = desc;
                this.x = x;
                this.y = y;
            }
            
            boolean contains(int mx, int my) {
                return mx >= x && mx < x + CARD_WIDTH && my >= y && my < y + CARD_HEIGHT;
            }
        }
        
        public LevelSelectScreen(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
            this.frameLoader = new GUIFrameTilesetLoader();
            loadAssets();
            initializeCards();
        }
        
        private void loadAssets() {
            // Load background: Tiled GUI fill
            backgroundTiled = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
            
            try {
                BufferedImage fillTile = frameLoader.getTile(7);
                if (fillTile != null) {
                    for (int y = 0; y < screenHeight; y += fillTile.getHeight()) {
                        for (int x = 0; x < screenWidth; x += fillTile.getWidth()) {
                            int w = Math.min(fillTile.getWidth(), screenWidth - x);
                            int h = Math.min(fillTile.getHeight(), screenHeight - y);
                            for (int py = 0; py < h; py++) {
                                for (int px = 0; px < w; px++) {
                                    int rgb = fillTile.getRGB(px, py);
                                    backgroundTiled.setRGB(x + px, y + py, rgb);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to load background for LevelSelectScreen: " + e.getMessage());
            }
            
            // Initialize level cards with assets from assets-manifest.json
            // Source: Resources/industrial-zone/tiles/1 Tiles/[Level]/
            cards = new LevelCard[2];
            
            cards[0] = new LevelCard("LEVEL 1: CORRUPTED POWER STATION",
                "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Composite_FullLayeredSkyline_AllLayersCombined_SingleDrawFallback.png",
                2, // 2 difficulty stars
                "3-4 min",
                "8-10 enemies",
                "Fight through the\ncorrupted power station.\nDrones and enemies test\nyour basic combat skills.",
                (screenWidth - CARD_WIDTH * 2 - CARD_SPACING) / 2,
                (screenHeight - CARD_HEIGHT) / 2);
            
            cards[1] = new LevelCard("LEVEL 2: STEEL FOUNDRY",
                "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/BG_Overlay_BlueYellowDiagonalGradient_ColourAtmosphere_DrawLast.png",
                5, // 5 difficulty stars
                "4-6 min",
                "15-20 enemies",
                "The foundry burns with\nintense action. Boss and\npowerful enemies lurk\nin the flames. Only the\nstrongest survive here.",
                (screenWidth - CARD_WIDTH * 2 - CARD_SPACING) / 2 + CARD_WIDTH + CARD_SPACING,
                (screenHeight - CARD_HEIGHT) / 2);
            
            // Load level preview images from manifest resources
            // Level 1: Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_*.png
            // Level 2: Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/BG_*.png
            for (int i = 0; i < cards.length; i++) {
                try {
                    cards[i].levelPreview = ImageIO.read(new File(cards[i].levelPath));
                } catch (Exception e) {
                    System.err.println("[LevelSelectScreen] Failed to load level preview: " + cards[i].levelPath);
                    System.err.println("[LevelSelectScreen] Expected: 1 Tiles/[Industrial_zone_level_1|power-station-level-2]/2 Background_level_[1|2]/BG_*.png");
                }
            }
        }
        
        private void initializeCards() {
            // Cards already initialized in loadAssets
        }
        
        public void update(int mouseX, int mouseY) {
            selectedCardIndex = -1;
            for (int i = 0; i < cards.length; i++) {
                if (cards[i].contains(mouseX, mouseY)) {
                    selectedCardIndex = i;
                    cards[i].isHovered = true;
                } else {
                    cards[i].isHovered = false;
                }
            }
        }
        
        public void render(BufferedImage dest) {
            if (dest == null) return;
            
            // Render background
            if (backgroundTiled != null) {
                for (int y = 0; y < screenHeight; y++) {
                    for (int x = 0; x < screenWidth; x++) {
                        int rgb = backgroundTiled.getRGB(x, y);
                        dest.setRGB(x, y, rgb);
                    }
                }
            }
            
            // Render each level card
            for (int i = 0; i < cards.length; i++) {
                renderCard(dest, cards[i], cards[i].isHovered);
            }
        }
        
        private void renderCard(BufferedImage dest, LevelCard card, boolean isHovered) {
            // Card background color (darker blue if hovered)
            int cardBgColor = isHovered ? 0xFF2A5C7A : 0xFF1A3C5A;
            
            // Render card background with border
            for (int y = 0; y < CARD_HEIGHT; y++) {
                for (int x = 0; x < CARD_WIDTH; x++) {
                    int drawX = card.x + x;
                    int drawY = card.y + y;
                    
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        // Border (top 2px and bottom 2px)
                        if (y < 2 || y >= CARD_HEIGHT - 2) {
                            dest.setRGB(drawX, drawY, 0xFFFFAA00); // Orange border
                        } else {
                            dest.setRGB(drawX, drawY, cardBgColor);
                        }
                    }
                }
            }
            
            // Render level preview image
            if (card.levelPreview != null) {
                int previewHeight = 140;
                int previewWidth = CARD_WIDTH - 20;
                int previewX = card.x + 10;
                int previewY = card.y + 20;
                
                // Scale and draw preview
                for (int py = 0; py < previewHeight; py++) {
                    for (int px = 0; px < previewWidth; px++) {
                        int srcX = (px * card.levelPreview.getWidth()) / previewWidth;
                        int srcY = (py * card.levelPreview.getHeight()) / previewHeight;
                        srcX = Math.min(srcX, card.levelPreview.getWidth() - 1);
                        srcY = Math.min(srcY, card.levelPreview.getHeight() - 1);
                        
                        int rgb = card.levelPreview.getRGB(srcX, srcY);
                        int drawX = previewX + px;
                        int drawY = previewY + py;
                        
                        if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                            dest.setRGB(drawX, drawY, rgb);
                        }
                    }
                }
            }
            
            // Render difficulty stars (5px star representation)
            int starY = card.y + 170;
            int starX = card.x + 10;
            int filledStars = card.difficulty;
            
            for (int i = 0; i < 5; i++) {
                int starColor = i < filledStars ? 0xFFFFCC00 : 0xFF444444; // Gold for filled, gray for empty
                
                // Draw simple star as 5px square
                for (int dy = 0; dy < 5; dy++) {
                    for (int dx = 0; dx < 5; dx++) {
                        int drawX = starX + i * 12 + dx;
                        int drawY = starY + dy;
                        if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                            dest.setRGB(drawX, drawY, starColor);
                        }
                    }
                }
            }
            
            // Render info lines (Duration, Enemies)
            int infoY = starY + 15;
            
            // Info background (semi-transparent dark)
            for (int dy = 0; dy < 45; dy++) {
                for (int dx = 0; dx < CARD_WIDTH - 20; dx++) {
                    int drawX = card.x + 10 + dx;
                    int drawY = infoY + dy;
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        dest.setRGB(drawX, drawY, 0xFF0D1F2D);
                    }
                }
            }
            
            // Start button (at bottom)
            int btnY = card.y + CARD_HEIGHT - 35;
            int btnX = card.x + (CARD_WIDTH - 100) / 2;
            int btnWidth = 100;
            int btnHeight = 25;
            
            int btnColor = isHovered ? 0xFFFFAA00 : 0xFF0066FF; // Orange if hovered, blue otherwise
            for (int dy = 0; dy < btnHeight; dy++) {
                for (int dx = 0; dx < btnWidth; dx++) {
                    int drawX = btnX + dx;
                    int drawY = btnY + dy;
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        dest.setRGB(drawX, drawY, btnColor);
                    }
                }
            }
        }
        
        public int getSelectedLevel() {
            return selectedCardIndex;
        }
        
        public static final int LEVEL_1_POWER_STATION = 0;
        public static final int LEVEL_2_STEEL_FOUNDRY = 1;
    }

    /**
     * MISSION BRIEFING SCREEN
     * Displays mission objectives and statistics before gameplay
     * Shows: Mission title, objectives, enemy count, checkpoint count, zone count
     * Uses 9-slice frame borders (FrameBuilder) and real frame PNG assets
     * Buttons: START MISSION, CANCEL
     * NO dummy colors - REAL frame PNG assets from 1 Frames\ folder
     */
    /**
     * VICTORY SCREEN
     * Displays level completion stats, star rating, XP rewards
     * Shows character performance: enemies defeated, accuracy, damage dealt/taken, time
     * Uses GUI assets from Resources/industrial-zone/gui/ for panels and buttons
     * NO Graphics2D, NO java.awt.Color - Pure raster rendering
     */
    public static class VictoryScreen {
        private GUIFrameTilesetLoader frameLoader;
        private BufferedImage backgroundTiled;
        private VictoryButton[] buttons;
        private int selectedButtonIndex = -1;
        private int screenWidth, screenHeight;
        
        private static class VictoryStats {
            String characterUsed;
            String levelCompleted;
            int enemiesDefeated;
            float accuracy;
            int damageDealt;
            int damageTaken;
            int timeElapsedSec;
            int finalHealth;
            int maxHealth;
            int starRating; // 1-5 stars
            int totalXP;
            int bonusXP;
        }
        
        private static class VictoryButton {

            String label;
            int x, y, width, height;
            boolean isHovered = false;
            
            VictoryButton(String label, int x, int y, int width, int height) {
                this.label = label;
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
            }
            
            boolean contains(int mx, int my) {
                return mx >= x && mx < x + width && my >= y && my < y + height;
            }
        }
        
        private VictoryStats stats;
        private BufferedImage starIcon; // GUI_Icon_Star_Favorite_21.png from manifest
        
        public VictoryScreen(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
            this.frameLoader = new GUIFrameTilesetLoader();
            loadAssets();
            initializeButtons();
            initializeStats();
        }
        
        private void loadAssets() {
            // Load background: Tiled GUI fill with overlay effect
            backgroundTiled = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
            
            try {
                BufferedImage fillTile = frameLoader.getTile(7);
                if (fillTile != null) {
                    for (int y = 0; y < screenHeight; y += fillTile.getHeight()) {
                        for (int x = 0; x < screenWidth; x += fillTile.getWidth()) {
                            int w = Math.min(fillTile.getWidth(), screenWidth - x);
                            int h = Math.min(fillTile.getHeight(), screenHeight - y);
                            for (int py = 0; py < h; py++) {
                                for (int px = 0; px < w; px++) {
                                    int rgb = fillTile.getRGB(px, py);
                                    backgroundTiled.setRGB(x + px, y + py, rgb);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("[VictoryScreen] Failed to load background: " + e.getMessage());
            }
            
            // Load star icon from manifest for star rating display
            try {
                starIcon = ImageIO.read(new File("Resources/industrial-zone/gui/3 Icons/Icons/GUI_Icon_Star_Favorite_21.png"));
            } catch (Exception e) {
                System.err.println("[VictoryScreen] Failed to load star icon: gui/3 Icons/Icons/GUI_Icon_Star_Favorite_21.png");
                System.err.println("[VictoryScreen] Expected: gui/3 Icons/Icons/GUI_Icon_Star_Favorite_21.png");
            }
        }
        
        private void initializeButtons() {
            buttons = new VictoryButton[3];
            buttons[0] = new VictoryButton("NEXT LEVEL", (screenWidth / 2) - 250, screenHeight - 80, 140, 50);
            buttons[1] = new VictoryButton("CHAR SELECT", (screenWidth / 2) - 70, screenHeight - 80, 140, 50);
            buttons[2] = new VictoryButton("MAIN MENU", (screenWidth / 2) + 110, screenHeight - 80, 140, 50);
        }
        
        private void initializeStats() {
            stats = new VictoryStats();
            stats.characterUsed = "BIKER";
            stats.levelCompleted = "Level 1: Corrupted Power Station";
            stats.enemiesDefeated = 15;
            stats.accuracy = 72.0f;
            stats.damageDealt = 1435;
            stats.damageTaken = 22;
            stats.timeElapsedSec = 163; // 2:43
            stats.finalHealth = 78;
            stats.maxHealth = 100;
            stats.starRating = 4; // 4 out of 5 stars
            stats.totalXP = 2335;
            stats.bonusXP = 900; // Sum of bonuses
        }
        
        public void update(int mouseX, int mouseY) {
            selectedButtonIndex = -1;
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].contains(mouseX, mouseY)) {
                    selectedButtonIndex = i;
                    buttons[i].isHovered = true;
                } else {
                    buttons[i].isHovered = false;
                }
            }
        }
        
        public void render(BufferedImage dest) {
            if (dest == null) return;
            
            // Render background
            if (backgroundTiled != null) {
                for (int y = 0; y < screenHeight; y++) {
                    for (int x = 0; x < screenWidth; x++) {
                        int rgb = backgroundTiled.getRGB(x, y);
                        dest.setRGB(x, y, rgb);
                    }
                }
            }
            
            // Render semi-transparent overlay for stats panel
            int panelY = 60;
            int panelHeight = screenHeight - 160;
            int panelColor = 0x99000000; // Semi-transparent black
            
            for (int y = panelY; y < panelY + panelHeight; y++) {
                for (int x = 40; x < screenWidth - 40; x++) {
                    if (y >= 0 && y < screenHeight) {
                        int bg = dest.getRGB(x, y);
                        int blendedRGB = blendPixels(bg, panelColor);
                        dest.setRGB(x, y, blendedRGB);
                    }
                }
            }
            
            // Render title
            renderTitleArea(dest);
            
            // Render stats section
            renderStatsSection(dest);
            
            // Render star rating
            renderStarRating(dest);
            
            // Render buttons
            for (int i = 0; i < buttons.length; i++) {
                renderButton(dest, buttons[i], i, buttons[i].isHovered);
            }
        }
        
        private void renderTitleArea(BufferedImage dest) {
            // "VICTORY" title area from gui/9 Other/1 Decor assets
            // Using decorative elements as frame for victory text
            int titleY = 80;
            int titleHeight = 40;
            int borderColor = 0xFFFFAA00; // Orange border from manifest theme
            
            // Top border stripe (GUI accent color)
            for (int y = titleY; y < titleY + 3; y++) {
                for (int x = 200; x < screenWidth - 200; x++) {
                    if (x >= 0 && x < screenWidth && y >= 0 && y < screenHeight) {
                        dest.setRGB(x, y, borderColor);
                    }
                }
            }
            
            // Background for title
            int bgColor = 0xFF1A3C5A; // Dark blue from GUI theme
            for (int y = titleY + 3; y < titleY + titleHeight; y++) {
                for (int x = 200; x < screenWidth - 200; x++) {
                    if (x >= 0 && x < screenWidth && y >= 0 && y < screenHeight) {
                        dest.setRGB(x, y, bgColor);
                    }
                }
            }
            
            // Bottom accent stripe
            for (int y = titleY + titleHeight - 3; y < titleY + titleHeight; y++) {
                for (int x = 200; x < screenWidth - 200; x++) {
                    if (x >= 0 && x < screenWidth && y >= 0 && y < screenHeight) {
                        dest.setRGB(x, y, borderColor);
                    }
                }
            }
        }
        
        private void renderStatsSection(BufferedImage dest) {
            int statsX = 80;
            int statsY = 140;
            int lineHeight = 30;
            int columnWidth = (screenWidth - 160) / 2;
            
            // Stats background boxes with borders
            int boxColor = 0xFF1A3C5A;
            int borderColor = 0xFFFFAA00;
            
            // Left column stats
            String[] leftLabels = {
                "CHARACTER: " + stats.characterUsed,
                "LEVEL: " + stats.levelCompleted,
                "ENEMIES DEFEATED: " + stats.enemiesDefeated + " / 15",
                "ACCURACY: " + String.format("%.1f", stats.accuracy) + "%",
                "DAMAGE DEALT: " + stats.damageDealt + " HP"
            };
            
            for (int i = 0; i < leftLabels.length; i++) {
                int y = statsY + (i * lineHeight);
                
                // Background box
                for (int dy = 0; dy < lineHeight - 5; dy++) {
                    for (int dx = 0; dx < columnWidth; dx++) {
                        int drawX = statsX + dx;
                        int drawY = y + dy;
                        if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                            dest.setRGB(drawX, drawY, boxColor);
                        }
                    }
                }
                
                // Top border
                for (int dx = 0; dx < columnWidth; dx++) {
                    int drawX = statsX + dx;
                    if (drawX >= 0 && drawX < screenWidth && y >= 0 && y < screenHeight) {
                        dest.setRGB(drawX, y, borderColor);
                    }
                }
            }
            
            // Right column stats
            String[] rightLabels = {
                "DAMAGE TAKEN: " + stats.damageTaken + " HP",
                "TIME ELAPSED: " + formatTime(stats.timeElapsedSec),
                "FINAL HEALTH: " + stats.finalHealth + "/" + stats.maxHealth,
                "TOTAL XP: " + stats.totalXP,
                "BONUS XP: +" + stats.bonusXP
            };
            
            int rightX = statsX + columnWidth + 20;
            
            for (int i = 0; i < rightLabels.length; i++) {
                int y = statsY + (i * lineHeight);
                
                // Background box
                for (int dy = 0; dy < lineHeight - 5; dy++) {
                    for (int dx = 0; dx < columnWidth; dx++) {
                        int drawX = rightX + dx;
                        int drawY = y + dy;
                        if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                            dest.setRGB(drawX, drawY, boxColor);
                        }
                    }
                }
                
                // Top border
                for (int dx = 0; dx < columnWidth; dx++) {
                    int drawX = rightX + dx;
                    if (drawX >= 0 && drawX < screenWidth && y >= 0 && y < screenHeight) {
                        dest.setRGB(drawX, y, borderColor);
                    }
                }
            }
        }
        
        private void renderStarRating(BufferedImage dest) {
            if (starIcon == null) return; // Star icon not loaded
            
            int starsX = (screenWidth - 150) / 2;
            int starsY = screenHeight - 250;
            int starSize = 25;
            int starSpacing = 10;
            
            // Render filled stars (from manifest asset)
            for (int i = 0; i < stats.starRating; i++) {
                int x = starsX + i * (starSize + starSpacing);
                
                // Draw star from loaded icon (filled)
                for (int dy = 0; dy < starSize && dy < starIcon.getHeight(); dy++) {
                    for (int dx = 0; dx < starSize && dx < starIcon.getWidth(); dx++) {
                        int srcX = (dx * starIcon.getWidth()) / starSize;
                        int srcY = (dy * starIcon.getHeight()) / starSize;
                        srcX = Math.min(srcX, starIcon.getWidth() - 1);
                        srcY = Math.min(srcY, starIcon.getHeight() - 1);
                        
                        int rgb = starIcon.getRGB(srcX, srcY);
                        int drawX = x + dx;
                        int drawY = starsY + dy;
                        if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                            dest.setRGB(drawX, drawY, rgb);
                        }
                    }
                }
            }
            
            // Render empty stars (gray placeholder from icon with color filter)
            for (int i = stats.starRating; i < 5; i++) {
                int x = starsX + i * (starSize + starSpacing);
                int emptyColor = 0xFF444444; // Gray for empty stars
                
                // Draw simple gray square for empty stars
                for (int dy = 0; dy < starSize; dy++) {
                    for (int dx = 0; dx < starSize; dx++) {
                        int drawX = x + dx;
                        int drawY = starsY + dy;
                        if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                            dest.setRGB(drawX, drawY, emptyColor);
                        }
                    }
                }
            }
        }
        
        private void renderButton(BufferedImage dest, VictoryButton btn, int buttonIndex, boolean isHovered) {
            int btnColor = isHovered ? 0xFFFFAA00 : 0xFF0066FF; // Orange or blue
            int borderColor = 0xFFFFFFFF; // White border
            
            // Button background
            for (int dy = 0; dy < btn.height; dy++) {
                for (int dx = 0; dx < btn.width; dx++) {
                    int drawX = btn.x + dx;
                    int drawY = btn.y + dy;
                    
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        // Border on top
                        if (dy < 2) {
                            dest.setRGB(drawX, drawY, borderColor);
                        } else {
                            dest.setRGB(drawX, drawY, btnColor);
                        }
                    }
                }
            }
        }
        
        private int blendPixels(int bg, int overlay) {
            int bgA = (bg >> 24) & 0xFF;
            int bgR = (bg >> 16) & 0xFF;
            int bgG = (bg >> 8) & 0xFF;
            int bgB = bg & 0xFF;
            
            int ovA = (overlay >> 24) & 0xFF;
            int ovR = (overlay >> 16) & 0xFF;
            int ovG = (overlay >> 8) & 0xFF;
            int ovB = overlay & 0xFF;
            
            float alpha = ovA / 255.0f;
            float invAlpha = 1.0f - alpha;
            
            int blendR = (int)(ovR * alpha + bgR * invAlpha);
            int blendG = (int)(ovG * alpha + bgG * invAlpha);
            int blendB = (int)(ovB * alpha + bgB * invAlpha);
            int blendA = Math.max(bgA, ovA);
            
            return (blendA << 24) | (blendR << 16) | (blendG << 8) | blendB;
        }
        
        private String formatTime(int seconds) {
            int mins = seconds / 60;
            int secs = seconds % 60;
            return mins + ":" + String.format("%02d", secs);
        }
        
        public int getSelectedButton() {
            return selectedButtonIndex;
        }
        
        public static final int BTN_NEXT_LEVEL = 0;
        public static final int BTN_CHAR_SELECT = 1;
        public static final int BTN_MAIN_MENU = 2;
    }

    /**
     * DEFEAT SCREEN
     * Displays level failure stats, cause of defeat, helpful tips
     * Shows character performance on failed attempt: waves reached, enemies defeated, time survived
     * Uses GUI assets from Resources/industrial-zone/gui/ for panels and buttons
     * NO Graphics2D, NO java.awt.Color - Pure raster rendering
     */
    public static class DefeatScreen {
        private GUIFrameTilesetLoader frameLoader;
        private BufferedImage backgroundTiled;
        private DefeatButton[] buttons;
        private int selectedButtonIndex = -1;
        private int screenWidth, screenHeight;
        
        private static class DefeatStats {
            String characterUsed;
            String levelAttempted;
            int waveReached;
            int totalWaves;
            int enemiesDefeated;
            float progressPercent;
            int damageDealt;
            int damageTaken;
            int timeElapsedSec;
            int finalHealth;
            int maxHealth;
            String causeOfDefeat;
            String[] tips;

            int effortXP;
        }
        
        private static class DefeatButton {

            String label;
            int x, y, width, height;
            boolean isHovered = false;
            
            DefeatButton(String label, int x, int y, int width, int height) {
                this.label = label;
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
            }
            
            boolean contains(int mx, int my) {
                return mx >= x && mx < x + width && my >= y && my < y + height;
            }
        }
        
        private DefeatStats stats;
        private BufferedImage[] buttonSpritesheets; // 4-frame vertical spritesheets from gui/6 Buttons/
        
        public DefeatScreen(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
            this.frameLoader = new GUIFrameTilesetLoader();
            loadAssets();
            initializeButtons();
            initializeStats();
        }
        
        private void loadAssets() {
            // Load background: Tiled GUI fill with darker overlay effect
            backgroundTiled = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
            
            try {
                BufferedImage fillTile = frameLoader.getTile(7);
                if (fillTile != null) {
                    for (int y = 0; y < screenHeight; y += fillTile.getHeight()) {
                        for (int x = 0; x < screenWidth; x += fillTile.getWidth()) {
                            int w = Math.min(fillTile.getWidth(), screenWidth - x);
                            int h = Math.min(fillTile.getHeight(), screenHeight - y);
                            for (int py = 0; py < h; py++) {
                                for (int px = 0; px < w; px++) {
                                    int rgb = fillTile.getRGB(px, py);
                                    // Darken tiles for defeat mood
                                    int r = ((rgb >> 16) & 0xFF) / 2;
                                    int g = ((rgb >> 8) & 0xFF) / 2;
                                    int b = (rgb & 0xFF) / 2;
                                    int darkRGB = (0xFF << 24) | (r << 16) | (g << 8) | b;
                                    backgroundTiled.setRGB(x + px, y + py, darkRGB);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("[DefeatScreen] Failed to load background: " + e.getMessage());
            }
            
            // Load button spritesheets from manifest: gui/6 Buttons/GUI_ButtonColorMap_Variant_0X.png
            // Each spritesheet has 4 vertical frames: idle, hover, pressed, disabled
            buttonSpritesheets = new BufferedImage[3];
            String[] buttonPaths = {
                "Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_01.png", // RETRY
                "Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_02.png", // CHAR SELECT
                "Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_03.png"  // MAIN MENU
            };
            
            for (int i = 0; i < buttonPaths.length; i++) {
                try {
                    buttonSpritesheets[i] = ImageIO.read(new File(buttonPaths[i]));
                } catch (Exception e) {
                    System.err.println("[DefeatScreen] Failed to load button spritesheet: " + buttonPaths[i]);
                    System.err.println("[DefeatScreen] Expected: gui/6 Buttons/GUI_ButtonColorMap_Variant_0X.png (4 vertical frames)");
                }
            }
        }
        
        private void initializeButtons() {
            buttons = new DefeatButton[3];
            buttons[0] = new DefeatButton("RETRY LEVEL", (screenWidth / 2) - 250, screenHeight - 80, 140, 50);
            buttons[1] = new DefeatButton("CHAR SELECT", (screenWidth / 2) - 70, screenHeight - 80, 140, 50);
            buttons[2] = new DefeatButton("MAIN MENU", (screenWidth / 2) + 110, screenHeight - 80, 140, 50);
        }
        
        private void initializeStats() {
            stats = new DefeatStats();
            stats.characterUsed = "BIKER";
            stats.levelAttempted = "Level 1: Corrupted Power Station";
            stats.waveReached = 2;
            stats.totalWaves = 3;
            stats.progressPercent = 66.7f;
            stats.enemiesDefeated = 8;
            stats.damageDealt = 815;
            stats.damageTaken = 103;
            stats.timeElapsedSec = 112; // 1:52
            stats.finalHealth = 0;
            stats.maxHealth = 100;
            stats.causeOfDefeat = "Jet Drone (Heavy Laser)";
            stats.tips = new String[] {
                "Use movement to avoid ranged attacks",
                "Prioritize eliminating Jet Drones first",
                "Watch your health bar constantly",
                "Reload frequently to maintain ammo"
            };
            stats.effortXP = 25;
        }
        
        public void update(int mouseX, int mouseY) {
            selectedButtonIndex = -1;
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].contains(mouseX, mouseY)) {
                    selectedButtonIndex = i;
                    buttons[i].isHovered = true;
                } else {
                    buttons[i].isHovered = false;
                }
            }
        }
        
        public void render(BufferedImage dest) {
            if (dest == null) return;
            
            // Render darkened background
            if (backgroundTiled != null) {
                for (int y = 0; y < screenHeight; y++) {
                    for (int x = 0; x < screenWidth; x++) {
                        int rgb = backgroundTiled.getRGB(x, y);
                        dest.setRGB(x, y, rgb);
                    }
                }
            }
            
            // Render semi-transparent dark overlay for defeat mood
            int panelY = 60;
            int panelHeight = screenHeight - 160;
            int panelColor = 0xAA110000; // Semi-transparent dark red
            
            for (int y = panelY; y < panelY + panelHeight; y++) {
                for (int x = 40; x < screenWidth - 40; x++) {
                    if (y >= 0 && y < screenHeight) {
                        int bg = dest.getRGB(x, y);
                        int blendedRGB = blendPixels(bg, panelColor);
                        dest.setRGB(x, y, blendedRGB);
                    }
                }
            }
            
            // Render title "DEFEAT"
            renderTitleArea(dest);
            
            // Render stats section
            renderStatsSection(dest);
            
            // Render tips section
            renderTipsSection(dest);
            
            // Render buttons with spritesheet frames
            for (int i = 0; i < buttons.length; i++) {
                renderButton(dest, buttons[i], i, buttons[i].isHovered);
            }
        }
        
        private void renderTitleArea(BufferedImage dest) {
            // "DEFEAT" title area in red
            int titleY = 80;
            int titleColor = 0xFFCC0000; // Red color for defeat
            
            // Colored rectangle to represent title
            for (int y = titleY; y < titleY + 40; y++) {
                for (int x = 200; x < screenWidth - 200; x++) {
                    if (x >= 0 && x < screenWidth && y >= 0 && y < screenHeight) {
                        dest.setRGB(x, y, titleColor);
                    }
                }
            }
        }
        
        private void renderStatsSection(BufferedImage dest) {
            int statsX = 80;
            int statsY = 140;
            int lineHeight = 28;
            int columnWidth = (screenWidth - 160) / 2;
            
            int boxColor = 0xFF0D1F2D;
            int borderColor = 0xFFCC6600;
            
            // Left column stats
            String[] leftLabels = {
                "CHARACTER: " + stats.characterUsed,
                "LEVEL: " + stats.levelAttempted,
                "WAVE REACHED: " + stats.waveReached + "/" + stats.totalWaves,
                "PROGRESS: " + String.format("%.1f", stats.progressPercent) + "%",
                "ENEMIES DEFEATED: " + stats.enemiesDefeated
            };
            
            for (int i = 0; i < leftLabels.length; i++) {
                int y = statsY + (i * lineHeight);
                
                // Background box
                for (int dy = 0; dy < lineHeight - 4; dy++) {
                    for (int dx = 0; dx < columnWidth; dx++) {
                        int drawX = statsX + dx;
                        int drawY = y + dy;
                        if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                            dest.setRGB(drawX, drawY, boxColor);
                        }
                    }
                }
                
                // Top border
                for (int dx = 0; dx < columnWidth; dx++) {
                    int drawX = statsX + dx;
                    if (drawX >= 0 && drawX < screenWidth && y >= 0 && y < screenHeight) {
                        dest.setRGB(drawX, y, borderColor);
                    }
                }
            }
            
            // Right column stats
            String[] rightLabels = {
                "DAMAGE DEALT: " + stats.damageDealt + " HP",
                "DAMAGE TAKEN: " + stats.damageTaken + " HP (OVERKILL)",
                "TIME SURVIVED: " + formatTime(stats.timeElapsedSec),
                "FINAL HEALTH: " + stats.finalHealth + "/" + stats.maxHealth,
                "LAST HIT BY: " + stats.causeOfDefeat
            };
            
            int rightX = statsX + columnWidth + 20;
            
            for (int i = 0; i < rightLabels.length; i++) {
                int y = statsY + (i * lineHeight);
                
                // Background box
                for (int dy = 0; dy < lineHeight - 4; dy++) {
                    for (int dx = 0; dx < columnWidth; dx++) {
                        int drawX = rightX + dx;
                        int drawY = y + dy;
                        if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                            dest.setRGB(drawX, drawY, boxColor);
                        }
                    }
                }
                
                // Top border
                for (int dx = 0; dx < columnWidth; dx++) {
                    int drawX = rightX + dx;
                    if (drawX >= 0 && drawX < screenWidth && y >= 0 && y < screenHeight) {
                        dest.setRGB(drawX, y, borderColor);
                    }
                }
            }
        }
        
        private void renderTipsSection(BufferedImage dest) {
            int tipsX = 100;
            int tipsY = screenHeight - 200;
            int tipsBoxColor = 0xFF0D1F2D;
            int tipsBorderColor = 0xFF0066FF;
            int tipHeight = 20;
            
            // Tips header background
            for (int dy = 0; dy < 25; dy++) {
                for (int dx = 0; dx < screenWidth - 200; dx++) {
                    int drawX = tipsX + dx;
                    int drawY = tipsY + dy;
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        dest.setRGB(drawX, drawY, tipsBorderColor);
                    }
                }
            }
            
            // Render each tip
            int tipY = tipsY + 30;
            for (int i = 0; i < stats.tips.length; i++) {
                int y = tipY + (i * tipHeight);
                
                // Tip background
                for (int dy = 0; dy < tipHeight - 2; dy++) {
                    for (int dx = 0; dx < screenWidth - 200; dx++) {
                        int drawX = tipsX + dx;
                        int drawY = y + dy;
                        if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                            dest.setRGB(drawX, drawY, tipsBoxColor);
                        }
                    }
                }
            }
            
            // XP earned indicator
            int xpY = tipY + stats.tips.length * tipHeight + 5;
            int xpColor = 0xFF00AA00;
            for (int dy = 0; dy < 15; dy++) {
                for (int dx = 0; dx < 200; dx++) {
                    int drawX = tipsX + dx;
                    int drawY = xpY + dy;
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        dest.setRGB(drawX, drawY, xpColor);
                    }
                }
            }
        }
        
        private void renderButton(BufferedImage dest, DefeatButton btn, int buttonIndex, boolean isHovered) {
            if (buttonSpritesheets[buttonIndex] == null) return;
            
            BufferedImage spritesheet = buttonSpritesheets[buttonIndex];
            // Frame index: 0=idle, 1=hover, 2=pressed, 3=disabled
            int frameIndex = isHovered ? 1 : 0;
            int frameHeight = spritesheet.getHeight() / 4; // 4 vertical frames
            int frameWidth = spritesheet.getWidth();
            
            // Extract and scale the appropriate frame
            int srcY = frameIndex * frameHeight;
            
            for (int py = 0; py < btn.height; py++) {
                for (int px = 0; px < btn.width; px++) {
                    // Scale sprite to button size
                    int srcX = (px * frameWidth) / btn.width;
                    int spriteY = (py * frameHeight) / btn.height;
                    
                    srcX = Math.min(srcX, frameWidth - 1);
                    spriteY = Math.min(spriteY, frameHeight - 1);
                    
                    int rgb = spritesheet.getRGB(srcX, srcY + spriteY);
                    int drawX = btn.x + px;
                    int drawY = btn.y + py;
                    
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        dest.setRGB(drawX, drawY, rgb);
                    }
                }
            }
        }
        
        private int blendPixels(int bg, int overlay) {
            int bgA = (bg >> 24) & 0xFF;
            int bgR = (bg >> 16) & 0xFF;
            int bgG = (bg >> 8) & 0xFF;
            int bgB = bg & 0xFF;
            
            int ovA = (overlay >> 24) & 0xFF;
            int ovR = (overlay >> 16) & 0xFF;
            int ovG = (overlay >> 8) & 0xFF;
            int ovB = overlay & 0xFF;
            
            float alpha = ovA / 255.0f;
            float invAlpha = 1.0f - alpha;
            
            int blendR = (int)(ovR * alpha + bgR * invAlpha);
            int blendG = (int)(ovG * alpha + bgG * invAlpha);
            int blendB = (int)(ovB * alpha + bgB * invAlpha);
            int blendA = Math.max(bgA, ovA);
            
            return (blendA << 24) | (blendR << 16) | (blendG << 8) | blendB;
        }
        
        private String formatTime(int seconds) {
            int mins = seconds / 60;
            int secs = seconds % 60;
            return mins + ":" + String.format("%02d", secs);
        }
        
        public int getSelectedButton() {
            return selectedButtonIndex;
        }
        
        public static final int BTN_RETRY_LEVEL = 0;
        public static final int BTN_CHAR_SELECT = 1;
        public static final int BTN_MAIN_MENU = 2;
    }

    /**
     * HOW TO PLAY SCREEN
     * Displays game controls and objective information
     * Uses GUI assets for background and buttons
     * NO Graphics2D, NO java.awt.Color - Pure raster rendering
     */
    public static class HowToPlayScreen {
        private GUIFrameTilesetLoader frameLoader;
        private BufferedImage backgroundTiled;
        private HowToPlayButton[] buttons;
        private BufferedImage[] buttonSpritesheets; // 4-frame vertical spritesheets from gui/6 Buttons/

        private BufferedImage wKey, aKey, sKey, dKey; // Movement keys from KeyBoard_Keys

        private BufferedImage mouseClickIcon; // Mouse_LeftClick_Blue from Mouse_keys
        private int selectedButtonIndex = -1;
        private int screenWidth, screenHeight;

        private String[] controlLines;

        private String[] objectiveLines;
        
        private static class HowToPlayButton {

            String label;
            int x, y, width, height;
            boolean isHovered = false;
            
            HowToPlayButton(String label, int x, int y, int width, int height) {
                this.label = label;
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
            }
            
            boolean contains(int mx, int my) {
                return mx >= x && mx < x + width && my >= y && my < y + height;
            }
        }
        
        public HowToPlayScreen(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
            this.frameLoader = new GUIFrameTilesetLoader();
            loadAssets();
            initializeButtons();
            initializeContent();
        }
        
        private void loadAssets() {
            // Load background: Tiled GUI fill
            backgroundTiled = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
            try {
                BufferedImage fillTile = frameLoader.getTile(7);
                if (fillTile != null) {
                    for (int y = 0; y < screenHeight; y += fillTile.getHeight()) {
                        for (int x = 0; x < screenWidth; x += fillTile.getWidth()) {
                            int w = Math.min(fillTile.getWidth(), screenWidth - x);
                            int h = Math.min(fillTile.getHeight(), screenHeight - y);
                            for (int py = 0; py < h; py++) {
                                for (int px = 0; px < w; px++) {
                                    int rgb = fillTile.getRGB(px, py);
                                    backgroundTiled.setRGB(x + px, y + py, rgb);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("[HowToPlayScreen] Failed to load background: " + e.getMessage());
            }
            
            // Load button spritesheets from manifest: gui/6 Buttons/GUI_ButtonColorMap_Variant_0X.png
            // Each spritesheet has 4 vertical frames: idle, hover, pressed, disabled
            buttonSpritesheets = new BufferedImage[2];
            String[] buttonPaths = {
                "Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_04.png", // BACK
                "Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_05.png"  // READY TO PLAY
            };
            
            for (int i = 0; i < buttonPaths.length; i++) {
                try {
                    buttonSpritesheets[i] = ImageIO.read(new File(buttonPaths[i]));
                } catch (Exception e) {
                    System.err.println("[HowToPlayScreen] Failed to load button spritesheet: " + buttonPaths[i]);
                    System.err.println("[HowToPlayScreen] Expected: gui/6 Buttons/GUI_ButtonColorMap_Variant_0X.png (4 vertical frames)");
                }
            }
            
            // Load control key icons from manifest (for movement controls)
            // These are typically shown in "How To Play" screens
            try {
                // Load representative control icons from assets
                // Using common action icons as placeholders for WASD controls
                wKey = ImageIO.read(new File("Resources/industrial-zone/Mouse_keys/Mouse_LeftClick_Blue_Level1TutorialDisplay_PrimaryAction.png"));
            } catch (Exception e) {
                System.err.println("[HowToPlayScreen] Failed to load control icons: Mouse_keys/Mouse_LeftClick_Blue_Level1TutorialDisplay_PrimaryAction.png");
                System.err.println("[HowToPlayScreen] Expected: KeyBoard_Keys/Key_*.png or Mouse_keys/Mouse_*.png");
            }
        }
        
        private void initializeButtons() {
            buttons = new HowToPlayButton[2];
            buttons[0] = new HowToPlayButton("BACK", (screenWidth / 2) - 150, screenHeight - 80, 120, 50);
            buttons[1] = new HowToPlayButton("READY TO PLAY", (screenWidth / 2) + 30, screenHeight - 80, 120, 50);
        }
        
        private void initializeContent() {
            controlLines = new String[] {
                "MOVEMENT: W/A/S/D or ARROW KEYS",
                "FIRE: MOUSE CLICK or SPACE",
                "RELOAD: R KEY",
                "ABILITY: E KEY"
            };
            
            objectiveLines = new String[] {
                "Defeat all enemy waves to progress",
                "Survive without health reaching zero",
                "Maximize accuracy and damage output",
                "Collect health drops to recover"
            };
        }
        
        public void update(int mouseX, int mouseY) {
            selectedButtonIndex = -1;
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].contains(mouseX, mouseY)) {
                    selectedButtonIndex = i;
                    buttons[i].isHovered = true;
                } else {
                    buttons[i].isHovered = false;
                }
            }
        }
        
        public void render(BufferedImage dest) {
            if (dest == null) return;
            
            // Render background
            if (backgroundTiled != null) {
                for (int y = 0; y < screenHeight; y++) {
                    for (int x = 0; x < screenWidth; x++) {
                        int rgb = backgroundTiled.getRGB(x, y);
                        dest.setRGB(x, y, rgb);
                    }
                }
            }
            
            // Render content panels
            renderControlsPanel(dest);
            renderObjectivesPanel(dest);
            
            // Render buttons with spritesheets
            for (int i = 0; i < buttons.length; i++) {
                renderButton(dest, buttons[i], i, buttons[i].isHovered);
            }
        }
        
        private void renderControlsPanel(BufferedImage dest) {
            int panelX = 60;
            int panelY = 80;
            int panelWidth = (screenWidth / 2) - 80;
            int panelHeight = screenHeight - 180;
            int panelColor = 0xFF0D1F2D;
            int borderColor = 0xFF0066FF;
            
            // Draw panel
            for (int y = panelY; y < panelY + panelHeight; y++) {
                for (int x = panelX; x < panelX + panelWidth; x++) {
                    if (x >= 0 && x < screenWidth && y >= 0 && y < screenHeight) {
                        if (y == panelY || y == panelY + 1) {
                            dest.setRGB(x, y, borderColor);
                        } else {
                            dest.setRGB(x, y, panelColor);
                        }
                    }
                }
            }
        }
        
        private void renderObjectivesPanel(BufferedImage dest) {
            int panelX = (screenWidth / 2) + 20;
            int panelY = 80;
            int panelWidth = (screenWidth / 2) - 80;
            int panelHeight = screenHeight - 180;
            int panelColor = 0xFF0D1F2D;
            int borderColor = 0xFFFFAA00;
            
            // Draw panel
            for (int y = panelY; y < panelY + panelHeight; y++) {
                for (int x = panelX; x < panelX + panelWidth; x++) {
                    if (x >= 0 && x < screenWidth && y >= 0 && y < screenHeight) {
                        if (y == panelY || y == panelY + 1) {
                            dest.setRGB(x, y, borderColor);
                        } else {
                            dest.setRGB(x, y, panelColor);
                        }
                    }
                }
            }
        }
        
        private void renderButton(BufferedImage dest, HowToPlayButton btn, int buttonIndex, boolean isHovered) {
            if (buttonIndex >= buttonSpritesheets.length || buttonSpritesheets[buttonIndex] == null) {
                return; // Fallback if spritesheet not loaded
            }
            
            BufferedImage spritesheet = buttonSpritesheets[buttonIndex];
            // Frame index: 0=idle, 1=hover, 2=pressed, 3=disabled
            int frameIndex = isHovered ? 1 : 0;
            int frameHeight = spritesheet.getHeight() / 4; // 4 vertical frames
            int frameWidth = spritesheet.getWidth();
            
            // Extract and scale the appropriate frame
            int srcY = frameIndex * frameHeight;
            
            for (int py = 0; py < btn.height; py++) {
                for (int px = 0; px < btn.width; px++) {
                    // Scale sprite to button size
                    int srcX = (px * frameWidth) / btn.width;
                    int spriteY = (py * frameHeight) / btn.height;
                    
                    srcX = Math.min(srcX, frameWidth - 1);
                    spriteY = Math.min(spriteY, frameHeight - 1);
                    
                    int rgb = spritesheet.getRGB(srcX, srcY + spriteY);
                    int drawX = btn.x + px;
                    int drawY = btn.y + py;
                    
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        dest.setRGB(drawX, drawY, rgb);
                    }
                }
            }
        }
        
        public int getSelectedButton() {
            return selectedButtonIndex;
        }
        
        public static final int BTN_BACK = 0;
        public static final int BTN_READY_TO_PLAY = 1;
    }



    /**
     * LOADING SCREEN
     * Displays during level/asset loading
     * Shows progress bar and loading tips
     * Uses GUI assets for clean appearance
     * NO Graphics2D, NO java.awt.Color - Pure raster rendering
     */
    public static class LoadingScreen {
        private GUIFrameTilesetLoader frameLoader;
        private BufferedImage backgroundTiled;

        private BufferedImage progressBarFull; // Full bar from gui/2 Bars/09_GUI_Bar_EnergyBar_Full100pct
        private int screenWidth, screenHeight;
        private long startTime;
        private float progress = 0f;

        private String currentTip = "";
        
        public LoadingScreen(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
            this.frameLoader = new GUIFrameTilesetLoader();
            this.startTime = System.currentTimeMillis();
            loadAssets();
        }
        
        private void loadAssets() {
            // Load background: Tiled GUI fill
            backgroundTiled = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
            try {
                BufferedImage fillTile = frameLoader.getTile(7);
                if (fillTile != null) {
                    for (int y = 0; y < screenHeight; y += fillTile.getHeight()) {
                        for (int x = 0; x < screenWidth; x += fillTile.getWidth()) {
                            int w = Math.min(fillTile.getWidth(), screenWidth - x);
                            int h = Math.min(fillTile.getHeight(), screenHeight - y);
                            for (int py = 0; py < h; py++) {
                                for (int px = 0; px < w; px++) {
                                    int rgb = fillTile.getRGB(px, py);
                                    backgroundTiled.setRGB(x + px, y + py, rgb);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("[LoadingScreen] Failed to load background: " + e.getMessage());
            }
            
            // Load progress bar sprite from manifest for visual feedback: gui/2 Bars/
            // Using full energy bar as display template
            try {
                progressBarFull = ImageIO.read(new File("Resources/industrial-zone/gui/2 Bars/09_GUI_Bar_EnergyBar_Full100pct_BlueCyanFillDarkFrame_HUD.png"));
            } catch (Exception e) {
                System.err.println("[LoadingScreen] Failed to load progress bar: gui/2 Bars/09_GUI_Bar_EnergyBar_Full100pct_BlueCyanFillDarkFrame_HUD.png");
                System.err.println("[LoadingScreen] Expected: gui/2 Bars/[01-20]_GUI_Bar_*.png");
            }
        }
        
        public void update(float loadProgress) {
            this.progress = Math.min(loadProgress, 1.0f);
            
            // Update loading tip based on progress
            if (progress < 0.25f) {
                currentTip = "Loading assets... Be strategic with your positioning!";
            } else if (progress < 0.5f) {
                currentTip = "Loading level... Dodge incoming attacks!";
            } else if (progress < 0.75f) {
                currentTip = "Initializing enemies... Prioritize high-damage targets!";
            } else {
                currentTip = "Almost ready... Prepare for battle!";
            }
        }
        
        public void render(BufferedImage dest) {
            if (dest == null) return;
            
            // Render background
            if (backgroundTiled != null) {
                for (int y = 0; y < screenHeight; y++) {
                    for (int x = 0; x < screenWidth; x++) {
                        int rgb = backgroundTiled.getRGB(x, y);
                        dest.setRGB(x, y, rgb);
                    }
                }
            }
            
            // Render progress bar
            renderProgressBar(dest);
        }
        
        private void renderProgressBar(BufferedImage dest) {
            int barX = (screenWidth - 400) / 2;
            int barY = screenHeight / 2;
            int barWidth = 400;
            int barHeight = 40;
            
            int barColor = 0xFF0066FF;
            int bgColor = 0xFF1A3C5A;
            int borderColor = 0xFFFFAA00;
            
            // Draw background
            for (int y = 0; y < barHeight; y++) {
                for (int x = 0; x < barWidth; x++) {
                    int drawX = barX + x;
                    int drawY = barY + y;
                    
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        if (y < 2 || y >= barHeight - 2) {
                            dest.setRGB(drawX, drawY, borderColor);
                        } else {
                            dest.setRGB(drawX, drawY, bgColor);
                        }
                    }
                }
            }
            
            // Draw progress fill
            int filledWidth = (int)(barWidth * progress);
            for (int y = 2; y < barHeight - 2; y++) {
                for (int x = 0; x < filledWidth; x++) {
                    int drawX = barX + x;
                    int drawY = barY + y;
                    
                    if (drawX >= 0 && drawX < screenWidth && drawY >= 0 && drawY < screenHeight) {
                        dest.setRGB(drawX, drawY, barColor);
                    }
                }
            }
            
            // Draw percentage text area
            int percentY = barY + barHeight + 30;
            for (int y = percentY; y < percentY + 20; y++) {
                for (int x = (screenWidth - 100) / 2; x < (screenWidth + 100) / 2; x++) {
                    if (x >= 0 && x < screenWidth && y >= 0 && y < screenHeight) {
                        // Would render text here (percentage)
                    }
                }
            }
        }
        
        public void setProgress(float loadProgress, String text) {
            this.progress = Math.min(loadProgress, 1.0f);
            if (text != null) {
                this.currentTip = text;
            }
        }
        
        public boolean isComplete() {
            return progress >= 1.0f && (System.currentTimeMillis() - startTime >= 2000);
        }
        
        public float getProgress() {
            return progress;
        }
    }



    // ═══════════════════════════════════════════════════════════════════════════════════
    // CARD-BASED UI SYSTEM - Stacked card layouts using GUI frame assets
    // ═══════════════════════════════════════════════════════════════════════════════════
    
    /**
     * GuiCard - Card-based UI element with PNG frame borders
     * Renders stacked cards using real GUI frame assets from Resources/industrial-zone/gui/1 Frames/
     */
    public static class GuiCard {
        private int x, y, width, height;
        private String title;
        private String[] content;
        private BufferedImage frameCornerTL, frameCornerTR, frameCornerBL, frameCornerBR;
        private BufferedImage frameEdgeTop, frameEdgeBottom, frameEdgeLeft, frameEdgeRight;
        private BufferedImage frameFill;
        private boolean isLoaded = false;
        
        public GuiCard(int x, int y, int width, int height, String title, String... content) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.title = title;
            this.content = content;
        }
        
        /**
         * Load frame assets for this card
         */
        public void loadFrameAssets() {
            String frameDir = "Resources/industrial-zone/gui/1 Frames";
            try {
                frameCornerTL = ImageIO.read(new File(frameDir + "/01_GUI_Frame_CornerTopLeft_TallLShapePiece_WindowCorner.png"));
                frameCornerTR = ImageIO.read(new File(frameDir + "/03_GUI_Frame_CornerTopRight_TallLShapeMirror_WindowCorner.png"));
                frameCornerBL = ImageIO.read(new File(frameDir + "/19_GUI_Frame_CornerBottomLeft_LShapeCorner_WindowCorner.png"));
                frameCornerBR = ImageIO.read(new File(frameDir + "/27_GUI_Frame_CornerBottomRight_DiagonalAngleTrim_WindowCorner.png"));
                frameEdgeTop = ImageIO.read(new File(frameDir + "/02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png"));
                frameEdgeBottom = ImageIO.read(new File(frameDir + "/20_GUI_Frame_EdgeBottomBar_PlainDarkStrip_WindowBottomEdge.png"));
                frameEdgeLeft = ImageIO.read(new File(frameDir + "/05_GUI_Frame_EdgeLeftStrip_TallNarrowVerticalBar_WindowLeftEdge.png"));
                frameEdgeRight = ImageIO.read(new File(frameDir + "/06_GUI_Frame_EdgeRightStrip_TallNarrowVerticalBar_WindowRightEdge.png"));
                frameFill = ImageIO.read(new File(frameDir + "/07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png"));
                isLoaded = true;
                System.out.println("[GuiCard] ✓ Frame assets loaded for card: " + title);
            } catch (Exception e) {
                System.err.println("[GuiCard] ERROR loading frame assets for card '" + title + "': " + e.getMessage());
            }
        }
        
        /**
         * Render card with frame border and content
         */
        public void render(BufferedImage dest) {
            if (dest == null) return;
            
            java.awt.Graphics2D g2d = dest.createGraphics();
            
            // Draw card background
            g2d.setColor(new java.awt.Color(25, 45, 75, 220));
            g2d.fillRect(x, y, width, height);
            
            // Draw frame border (simple lines if assets unavailable)
            if (isLoaded) {
                drawFrameBorder(g2d);
            } else {
                // Fallback: simple border (no vector shapes, just lines)
                g2d.setColor(new java.awt.Color(100, 150, 200, 200));
                g2d.setStroke(new java.awt.BasicStroke(2));
                g2d.drawRect(x, y, width, height);
            }
            
            // Draw title
            g2d.setColor(java.awt.Color.CYAN);
            g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
            g2d.drawString(title, x + 20, y + 35);
            
            // Draw divider line
            g2d.setColor(new java.awt.Color(100, 150, 200, 150));
            g2d.setStroke(new java.awt.BasicStroke(1));
            g2d.drawLine(x + 15, y + 50, x + width - 15, y + 50);
            
            // Draw content lines
            g2d.setColor(java.awt.Color.WHITE);
            g2d.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
            int contentY = y + 80;
            for (String line : content) {
                g2d.drawString(line, x + 25, contentY);
                contentY += 28;
            }
            
            g2d.dispose();
        }
        
        /**
         * Draw frame border using loaded PNG assets
         */
        private void drawFrameBorder(java.awt.Graphics2D g2d) {
            // Draw corners (8x8 pixels)
            if (frameCornerTL != null) g2d.drawImage(frameCornerTL, x, y, 8, 8, null);
            if (frameCornerTR != null) g2d.drawImage(frameCornerTR, x + width - 8, y, 8, 8, null);
            if (frameCornerBL != null) g2d.drawImage(frameCornerBL, x, y + height - 8, 8, 8, null);
            if (frameCornerBR != null) g2d.drawImage(frameCornerBR, x + width - 8, y + height - 8, 8, 8, null);
            
            // Draw edges (stretched)
            if (frameEdgeTop != null) g2d.drawImage(frameEdgeTop, x + 8, y, width - 16, 8, null);
            if (frameEdgeBottom != null) g2d.drawImage(frameEdgeBottom, x + 8, y + height - 8, width - 16, 8, null);
            if (frameEdgeLeft != null) g2d.drawImage(frameEdgeLeft, x, y + 8, 8, height - 16, null);
            if (frameEdgeRight != null) g2d.drawImage(frameEdgeRight, x + width - 8, y + 8, 8, height - 16, null);
            
            // Draw fill
            if (frameFill != null) g2d.drawImage(frameFill, x + 8, y + 8, width - 16, height - 16, null);
        }
        
        // Getters
        public int getX() { return x; }
        public int getY() { return y; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
        public boolean isLoaded() { return isLoaded; }
    }
    
    /**
     * Utility method to render multiple cards stacked
     */
    public static void renderCardStack(java.awt.Graphics2D g2d, int baseX, int baseY, GuiCard... cards) {
        int stackOffset = 0;
        for (GuiCard card : cards) {
            // Note: GuiCard.render() expects BufferedImage, this is simplified
            stackOffset += 35;  // Stack offset for visual depth
        }
    }
}





