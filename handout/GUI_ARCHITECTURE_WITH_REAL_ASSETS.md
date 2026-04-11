# 🎨 COMPREHENSIVE GUI ARCHITECTURE WITH REAL ASSETS

## 🎯 Executive Summary

**Current State**: Game.java has skeleton GUI implementation with Color-based components
**Problem**: No actual asset integration - using placeholder colors instead of Resources/
**Goal**: Complete system redesign using ONLY real PNG assets from Resources/industrial-zone/gui/

**Key Principle**: EVERY visual element must load from actual asset files
- NO Color objects for buttons, panels, bars
- NO placeholder graphics
- LOAD REAL RESOURCES OR RETURN NULL

---

## 📊 GUI COMPONENT INVENTORY

### CATEGORY 1: MAIN MENU SCREEN

#### 1.1 Menu Background
**Asset Path**: `Resources/industrial-zone/gui/1 Frames/menu_background.png`
**Loader**: `SingleSpriteLoader`
**Dimensions**: 1024×768 (full screen)
**Purpose**: Main menu backdrop

**Implementation**:
```java
SingleSpriteLoader menuBg = new AnimationAndSpriteLoader.SingleSpriteLoader(
    "menu_background",
    "Resources/industrial-zone/gui/1 Frames/menu_background.png"
);

if (menuBg.load()) {
    BufferedImage bgImage = menuBg.getFrame(0);
    g.drawImage(bgImage, 0, 0, 1024, 768, null);
} else {
    System.err.println("❌ FAILED TO LOAD MENU BACKGROUND");
}
```

#### 1.2 Play Button
**Asset Path**: `Resources/industrial-zone/gui/6 Buttons/play_button_4states_vertical.png`
**Loader**: `VerticalSpritesheetLoader` (4 states: normal, hover, pressed, disabled)
**Frames**:
- Frame 0: Normal state (idle)
- Frame 1: Hover state (mouse over)
- Frame 2: Pressed state (clicked)
- Frame 3: Disabled state (inactive)

**Implementation** (FOLLOWING CharacterAnimationTester PATTERN):
```java
public class PlayButtonComponent {
    
    private VerticalSpritesheetLoader buttonLoader;
    private Rectangle bounds;
    private boolean mouseOver = false;
    private boolean pressed = false;
    
    public void initialize() {
        buttonLoader = new AnimationAndSpriteLoader.VerticalSpritesheetLoader(
            "play_button",
            "Resources/industrial-zone/gui/6 Buttons/play_button_4states_vertical.png",
            0, 0, 0
        );
        
        if (!buttonLoader.load()) {
            System.err.println("❌ FAILED TO LOAD PLAY BUTTON");
            return;
        }
        
        System.out.println("✓ Play button loaded with " + buttonLoader.getFrameCount() + " states");
    }
    
    public void updateMouseState(int mouseX, int mouseY) {
        mouseOver = bounds.contains(mouseX, mouseY);
    }
    
    public void render(Graphics2D g) {
        if (buttonLoader == null) return;
        
        // Determine which state to show
        int stateIndex;
        if (pressed) {
            stateIndex = 2;  // Pressed state
        } else if (mouseOver) {
            stateIndex = 1;  // Hover state
        } else {
            stateIndex = 0;  // Normal state
        }
        
        BufferedImage buttonImage = buttonLoader.getFrame(stateIndex);
        if (buttonImage != null) {
            g.drawImage(buttonImage, bounds.x, bounds.y, bounds.width, bounds.height, null);
        }
    }
    
    public boolean isClicked() {
        return mouseOver && pressed;
    }
}
```

#### 1.3 Settings Button
**Asset Path**: `Resources/industrial-zone/gui/6 Buttons/settings_button_4states_vertical.png`
**Loader**: `VerticalSpritesheetLoader` (4 states)
**Same pattern as PlayButton**

#### 1.4 Exit Button
**Asset Path**: `Resources/industrial-zone/gui/6 Buttons/exit_button_4states_vertical.png`
**Loader**: `VerticalSpritesheetLoader` (4 states)
**Same pattern as PlayButton**

#### 1.5 Logo/Title
**Asset Path**: `Resources/industrial-zone/gui/5 Logo/game_title_logo.png`
**Loader**: `SingleSpriteLoader`
**Dimensions**: Varies (typically ~512×128 for title)
**Purpose**: Game title display

#### 1.6 Menu Panel Frame
**Asset Path**: `Resources/industrial-zone/gui/1 Frames/menu_panel_frame.png`
**Loader**: `SingleSpriteLoader` or 9-patch system (if available)
**Purpose**: Decorative frame around menu elements

---

### CATEGORY 2: HUD (Heads-Up Display) - In-Game

#### 2.1 Health Bar Container
**Asset Path**: `Resources/industrial-zone/gui/2 Bars/health_bar_background.png`
**Loader**: `SingleSpriteLoader`
**Purpose**: Static background for health display

#### 2.2 Health Bar Fill (Animated)
**Asset Path**: `Resources/industrial-zone/gui/2 Bars/health_bar_fill_100states.png`
**Loader**: `HorizontalSpritesheetLoader` (100 frames, 1-100%)
**Purpose**: Shows health percentage as animation
**Implementation**:
```java
public class HealthBarComponent {
    
    private SingleSpriteLoader barBg;
    private HorizontalSpritesheetLoader barFill;
    private int currentHealth = 100;
    private int maxHealth = 100;
    
    public void initialize() {
        // Load background
        barBg = new AnimationAndSpriteLoader.SingleSpriteLoader(
            "health_bar_bg",
            "Resources/industrial-zone/gui/2 Bars/health_bar_background.png"
        );
        barBg.load();
        
        // Load fill animation (100 states = 1% per frame)
        barFill = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
            "health_bar_fill",
            "Resources/industrial-zone/gui/2 Bars/health_bar_fill_100states.png",
            0, 0, 0
        );
        barFill.load();
    }
    
    public void setHealth(int health) {
        currentHealth = Math.max(0, Math.min(health, maxHealth));
    }
    
    public void render(Graphics2D g, int x, int y) {
        // Draw background
        BufferedImage bg = barBg.getFrame(0);
        if (bg != null) g.drawImage(bg, x, y, null);
        
        // Draw fill based on health percentage
        int healthPercent = (currentHealth * 99) / maxHealth;  // 0-99 frame index
        BufferedImage fill = barFill.getFrame(healthPercent);
        if (fill != null) g.drawImage(fill, x, y, null);
    }
}
```

#### 2.3 Score Display
**Asset Path**: `Resources/industrial-zone/gui/7 Numbers/numbers_grid_1x10.png` or separate digit files
**Loader**: `GridSpritesheetLoader` (1 row × 10 cols = digits 0-9)
**Purpose**: Display player score as graphics
**Implementation**:
```java
public class ScoreDisplayComponent {
    
    private GridSpritesheetLoader digitLoader;
    private int score = 0;
    
    public void initialize() {
        digitLoader = new AnimationAndSpriteLoader.GridSpritesheetLoader(
            "score_digits",
            "Resources/industrial-zone/gui/7 Numbers/digits_grid_1x10.png",
            1, 10  // 1 row, 10 columns (0-9)
        );
        digitLoader.load();
    }
    
    public void setScore(int newScore) {
        this.score = newScore;
    }
    
    public void render(Graphics2D g, int x, int y) {
        String scoreStr = String.valueOf(score);
        int offsetX = x;
        
        for (char digit : scoreStr.toCharArray()) {
            int digitIndex = Character.getNumericValue(digit);
            BufferedImage digitImage = digitLoader.getFrameAt(0, digitIndex);
            
            if (digitImage != null) {
                g.drawImage(digitImage, offsetX, y, null);
                offsetX += digitImage.getWidth();
            }
        }
    }
}
```

#### 2.4 Level Indicator
**Asset Path**: `Resources/industrial-zone/gui/3 Icons/level_icon.png`
**Loader**: `SingleSpriteLoader`
**Purpose**: Show current level number

#### 2.5 Mini-map/Compass
**Asset Path**: `Resources/industrial-zone/gui/3 Icons/compass_north.png` or animated variant
**Loader**: `SingleSpriteLoader` or `HorizontalSpritesheetLoader` for rotation
**Purpose**: Show player orientation

---

### CATEGORY 3: PAUSE MENU

#### 3.1 Pause Overlay
**Asset Path**: `Resources/industrial-zone/gui/1 Frames/pause_overlay_semi_transparent.png`
**Loader**: `SingleSpriteLoader`
**Purpose**: Semi-transparent overlay dimming the game

#### 3.2 Pause Menu Panel
**Asset Path**: `Resources/industrial-zone/gui/1 Frames/pause_menu_panel.png`
**Loader**: `SingleSpriteLoader`
**Purpose**: Background panel for pause menu buttons

#### 3.3 Resume Button
**Asset Path**: `Resources/industrial-zone/gui/6 Buttons/resume_button_4states_vertical.png`
**Loader**: `VerticalSpritesheetLoader` (4 states)

#### 3.4 Settings Button (Pause Menu variant)
**Asset Path**: `Resources/industrial-zone/gui/6 Buttons/settings_button_pause_4states_vertical.png`
**Loader**: `VerticalSpritesheetLoader`

#### 3.5 Exit to Menu Button
**Asset Path**: `Resources/industrial-zone/gui/6 Buttons/exit_to_menu_button_4states_vertical.png`
**Loader**: `VerticalSpritesheetLoader`

---

### CATEGORY 4: INVENTORY/EQUIPMENT SCREEN

#### 4.1 Inventory Panel Background
**Asset Path**: `Resources/industrial-zone/gui/1 Frames/inventory_panel_background.png`
**Loader**: `SingleSpriteLoader`

#### 4.2 Inventory Item Slots
**Asset Path**: `Resources/industrial-zone/gui/1 Frames/inventory_slot_empty.png`
**Loader**: `SingleSpriteLoader` (for repetition)
**Alternate**: Create dynamic slot renderer

#### 4.3 Weapon Icons
**Asset Path**: `Resources/industrial-zone/gui/3 Icons/weapon_icons_grid.png`
**Loader**: `GridSpritesheetLoader` (rows × columns for each weapon type)

#### 4.4 Armor Icons
**Asset Path**: `Resources/industrial-zone/gui/3 Icons/armor_icons_grid.png`
**Loader**: `GridSpritesheetLoader`

---

### CATEGORY 5: CURSOR SYSTEM

#### 5.1 Cursor Normal
**Asset Path**: `Resources/industrial-zone/gui/8 Cursors/cursor_normal.png`
**Loader**: `SingleSpriteLoader`

#### 5.2 Cursor Hover (on clickable)
**Asset Path**: `Resources/industrial-zone/gui/8 Cursors/cursor_hover_4states_vertical.png`
**Loader**: `VerticalSpritesheetLoader` (animated hover effect)

#### 5.3 Cursor Attack/Interact
**Asset Path**: `Resources/industrial-zone/gui/8 Cursors/cursor_attack.png`
**Loader**: `SingleSpriteLoader`

---

### CATEGORY 6: NOTIFICATIONS & POPUPS

#### 6.1 Notification Background
**Asset Path**: `Resources/industrial-zone/gui/1 Frames/notification_banner.png`
**Loader**: `SingleSpriteLoader`
**Purpose**: Achievement unlocked, level complete messages

#### 6.2 Dialog Box Background
**Asset Path**: `Resources/industrial-zone/gui/1 Frames/dialog_box_background.png`
**Loader**: `SingleSpriteLoader`
**Purpose**: NPC dialogues, story messages

#### 6.3 Yes/No Buttons
**Asset Paths**: 
- Yes: `Resources/industrial-zone/gui/6 Buttons/yes_button_4states_vertical.png`
- No: `Resources/industrial-zone/gui/6 Buttons/no_button_4states_vertical.png`
**Loader**: `VerticalSpritesheetLoader` (each)

---

## 🏗️ GUI ARCHITECTURE STRUCTURE

```java
// ════════════════════════════════════════════════════════════════════════════════
// TOP LEVEL: GUIManager (singleton, coordinates all UI)
// ════════════════════════════════════════════════════════════════════════════════

public class GUIManager {
    
    private static GUIManager instance = new GUIManager();
    
    // Asset loaders for all GUI elements
    private GUIElementLoaders loaders;
    
    // Screen states
    private enum ScreenState {
        MAIN_MENU,
        IN_GAME,
        PAUSED,
        SETTINGS,
        GAME_OVER
    }
    
    private ScreenState currentScreen = ScreenState.MAIN_MENU;
    
    // Component containers
    private MainMenuScreen mainMenu;
    private GameHUDScreen gameHUD;
    private PauseMenuScreen pauseMenu;
    private GameOverScreen gameOverScreen;
    
    // ════════════════════════════════════════════════════════════════════════════
    // INITIALIZATION
    // ════════════════════════════════════════════════════════════════════════════
    
    public void initialize() {
        // Load all GUI assets
        loaders = new GUIElementLoaders();
        loaders.loadAllAssets();
        
        // Initialize screens
        mainMenu = new MainMenuScreen(loaders);
        gameHUD = new GameHUDScreen(loaders);
        pauseMenu = new PauseMenuScreen(loaders);
        gameOverScreen = new GameOverScreen(loaders);
        
        System.out.println("✓ GUI Manager initialized with all asset loaders");
    }
    
    // ════════════════════════════════════════════════════════════════════════════
    // RENDERING
    // ════════════════════════════════════════════════════════════════════════════
    
    public void render(Graphics2D g, int width, int height) {
        switch (currentScreen) {
            case MAIN_MENU:
                mainMenu.render(g, width, height);
                break;
            case IN_GAME:
                gameHUD.render(g, width, height);
                break;
            case PAUSED:
                gameHUD.renderWithPauseOverlay(g, width, height);
                pauseMenu.render(g, width, height);
                break;
            case GAME_OVER:
                gameOverScreen.render(g, width, height);
                break;
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════
    // INPUT HANDLING
    // ════════════════════════════════════════════════════════════════════════════
    
    public void handleMouseClick(int mouseX, int mouseY) {
        switch (currentScreen) {
            case MAIN_MENU:
                mainMenu.handleClick(mouseX, mouseY);
                break;
            case PAUSED:
                pauseMenu.handleClick(mouseX, mouseY);
                break;
        }
    }
    
    public void handleMouseMove(int mouseX, int mouseY) {
        switch (currentScreen) {
            case MAIN_MENU:
                mainMenu.handleMouseMove(mouseX, mouseY);
                break;
            case PAUSED:
                pauseMenu.handleMouseMove(mouseX, mouseY);
                break;
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════
    // STATE TRANSITIONS
    // ════════════════════════════════════════════════════════════════════════════
    
    public void goToMainMenu() {
        currentScreen = ScreenState.MAIN_MENU;
    }
    
    public void startGame() {
        currentScreen = ScreenState.IN_GAME;
        gameHUD.reset();
    }
    
    public void pauseGame() {
        currentScreen = ScreenState.PAUSED;
        pauseMenu.reset();
    }
    
    public void resumeGame() {
        currentScreen = ScreenState.IN_GAME;
    }
    
    public void gameOver() {
        currentScreen = ScreenState.GAME_OVER;
        gameOverScreen.setScore(getCurrentScore());
    }
    
    public static GUIManager getInstance() {
        return instance;
    }
}

// ════════════════════════════════════════════════════════════════════════════════
// ELEMENT LOADERS: Central repository of all GUI asset loaders
// ════════════════════════════════════════════════════════════════════════════════

public class GUIElementLoaders {
    
    // Menu elements
    public SingleSpriteLoader menuBackground;
    public SingleSpriteLoader menuLogo;
    public VerticalSpritesheetLoader playButton;
    public VerticalSpritesheetLoader settingsButton;
    public VerticalSpritesheetLoader exitButton;
    
    // HUD elements
    public SingleSpriteLoader healthBarBackground;
    public HorizontalSpritesheetLoader healthBarFill;
    public GridSpritesheetLoader scoreDigits;
    
    // Pause menu
    public SingleSpriteLoader pauseOverlay;
    public SingleSpriteLoader pausePanel;
    public VerticalSpritesheetLoader resumeButton;
    public VerticalSpritesheetLoader pauseSettingsButton;
    public VerticalSpritesheetLoader exitToMenuButton;
    
    // Cursors
    public SingleSpriteLoader cursorNormal;
    public VerticalSpritesheetLoader cursorHover;
    
    // ════════════════════════════════════════════════════════════════════════════
    // BATCH LOADING
    // ════════════════════════════════════════════════════════════════════════════
    
    public void loadAllAssets() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║     LOADING ALL GUI ASSETS FROM RESOURCES/              ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
        
        // Menu
        loadMenuAssets();
        
        // HUD
        loadHUDAssets();
        
        // Pause menu
        loadPauseAssets();
        
        // Cursors
        loadCursorAssets();
        
        System.out.println("\n✓ ALL GUI ASSETS LOADED SUCCESSFULLY\n");
    }
    
    private void loadMenuAssets() {
        System.out.println("Loading Menu Assets...");
        
        menuBackground = new AnimationAndSpriteLoader.SingleSpriteLoader(
            "menu_bg",
            "Resources/industrial-zone/gui/1 Frames/menu_background.png"
        );
        verifyLoad(menuBackground, "Menu Background");
        
        menuLogo = new AnimationAndSpriteLoader.SingleSpriteLoader(
            "menu_logo",
            "Resources/industrial-zone/gui/5 Logo/game_title_logo.png"
        );
        verifyLoad(menuLogo, "Menu Logo");
        
        playButton = new AnimationAndSpriteLoader.VerticalSpritesheetLoader(
            "play_btn",
            "Resources/industrial-zone/gui/6 Buttons/play_button_4states_vertical.png",
            0, 0, 0
        );
        verifyLoad(playButton, "Play Button");
        
        settingsButton = new AnimationAndSpriteLoader.VerticalSpritesheetLoader(
            "settings_btn",
            "Resources/industrial-zone/gui/6 Buttons/settings_button_4states_vertical.png",
            0, 0, 0
        );
        verifyLoad(settingsButton, "Settings Button");
        
        exitButton = new AnimationAndSpriteLoader.VerticalSpritesheetLoader(
            "exit_btn",
            "Resources/industrial-zone/gui/6 Buttons/exit_button_4states_vertical.png",
            0, 0, 0
        );
        verifyLoad(exitButton, "Exit Button");
    }
    
    private void loadHUDAssets() {
        System.out.println("Loading HUD Assets...");
        
        healthBarBackground = new AnimationAndSpriteLoader.SingleSpriteLoader(
            "health_bar_bg",
            "Resources/industrial-zone/gui/2 Bars/health_bar_background.png"
        );
        verifyLoad(healthBarBackground, "Health Bar Background");
        
        healthBarFill = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
            "health_bar_fill",
            "Resources/industrial-zone/gui/2 Bars/health_bar_fill_100states.png",
            0, 0, 0
        );
        verifyLoad(healthBarFill, "Health Bar Fill");
        
        scoreDigits = new AnimationAndSpriteLoader.GridSpritesheetLoader(
            "score_digits",
            "Resources/industrial-zone/gui/7 Numbers/digits_grid_1x10.png",
            1, 10
        );
        verifyLoad(scoreDigits, "Score Digits");
    }
    
    private void loadPauseAssets() {
        System.out.println("Loading Pause Menu Assets...");
        
        pauseOverlay = new AnimationAndSpriteLoader.SingleSpriteLoader(
            "pause_overlay",
            "Resources/industrial-zone/gui/1 Frames/pause_overlay.png"
        );
        verifyLoad(pauseOverlay, "Pause Overlay");
        
        pausePanel = new AnimationAndSpriteLoader.SingleSpriteLoader(
            "pause_panel",
            "Resources/industrial-zone/gui/1 Frames/pause_panel.png"
        );
        verifyLoad(pausePanel, "Pause Panel");
        
        resumeButton = new AnimationAndSpriteLoader.VerticalSpritesheetLoader(
            "resume_btn",
            "Resources/industrial-zone/gui/6 Buttons/resume_button_4states_vertical.png",
            0, 0, 0
        );
        verifyLoad(resumeButton, "Resume Button");
    }
    
    private void loadCursorAssets() {
        System.out.println("Loading Cursor Assets...");
        
        cursorNormal = new AnimationAndSpriteLoader.SingleSpriteLoader(
            "cursor_normal",
            "Resources/industrial-zone/gui/8 Cursors/cursor_normal.png"
        );
        verifyLoad(cursorNormal, "Cursor Normal");
    }
    
    /**
     * Verify loader success with verbose error reporting
     */
    private void verifyLoad(AssetType loader, String assetName) {
        if (!loader.load()) {
            System.err.println("❌ FAILED TO LOAD: " + assetName);
        } else {
            System.out.println("  ✓ " + assetName);
        }
    }
}

// ════════════════════════════════════════════════════════════════════════════════
// SCREEN IMPLEMENTATIONS
// ════════════════════════════════════════════════════════════════════════════════

/**
 * Main menu screen with all button interactions
 */
public class MainMenuScreen {
    
    private GUIElementLoaders loaders;
    private List<ButtonComponent> buttons = new ArrayList<>();
    
    public MainMenuScreen(GUIElementLoaders loaders) {
        this.loaders = loaders;
        
        // Create button components
        buttons.add(new ButtonComponent(
            "Play",
            loaders.playButton,
            new Rectangle(400, 300, 224, 64)
        ));
        
        buttons.add(new ButtonComponent(
            "Settings",
            loaders.settingsButton,
            new Rectangle(400, 400, 224, 64)
        ));
        
        buttons.add(new ButtonComponent(
            "Exit",
            loaders.exitButton,
            new Rectangle(400, 500, 224, 64)
        ));
    }
    
    public void render(Graphics2D g, int width, int height) {
        // Draw background
        BufferedImage bg = loaders.menuBackground.getFrame(0);
        if (bg != null) {
            g.drawImage(bg, 0, 0, width, height, null);
        }
        
        // Draw logo
        BufferedImage logo = loaders.menuLogo.getFrame(0);
        if (logo != null) {
            g.drawImage(logo, 250, 50, 524, 100, null);
        }
        
        // Draw buttons
        for (ButtonComponent btn : buttons) {
            btn.render(g);
        }
    }
    
    public void handleClick(int x, int y) {
        for (ButtonComponent btn : buttons) {
            if (btn.isClicked(x, y)) {
                btn.onClick();
            }
        }
    }
    
    public void handleMouseMove(int x, int y) {
        for (ButtonComponent btn : buttons) {
            btn.updateMouseState(x, y);
        }
    }
}

/**
 * In-game HUD showing health, score, level
 */
public class GameHUDScreen {
    
    private GUIElementLoaders loaders;
    private HealthBarComponent healthBar;
    private ScoreDisplayComponent scoreDisplay;
    
    public GameHUDScreen(GUIElementLoaders loaders) {
        this.loaders = loaders;
        
        healthBar = new HealthBarComponent(loaders.healthBarBackground, loaders.healthBarFill);
        scoreDisplay = new ScoreDisplayComponent(loaders.scoreDigits);
    }
    
    public void render(Graphics2D g, int width, int height) {
        // Render HUD elements (top-left corner)
        healthBar.render(g, 10, 10);
        scoreDisplay.render(g, 10, 100);
    }
    
    public void renderWithPauseOverlay(Graphics2D g, int width, int height) {
        render(g, width, height);
        
        // Draw semi-transparent pause overlay
        BufferedImage overlay = loaders.pauseOverlay.getFrame(0);
        if (overlay != null) {
            g.drawImage(overlay, 0, 0, width, height, null);
        }
    }
    
    public void updateHealth(int health) {
        healthBar.setHealth(health);
    }
    
    public void updateScore(int score) {
        scoreDisplay.setScore(score);
    }
}
```

---

## ✅ CRITICAL IMPLEMENTATION CHECKLIST

- [ ] **ALL buttons load from VerticalSpritesheetLoader**
  - Play, Settings, Exit, Resume, etc.
  - Each with 4 states: normal, hover, pressed, disabled
  - ZERO Color-based fallbacks

- [ ] **ALL backgrounds are SingleSpriteLoader**
  - Menu background
  - Panel backgrounds
  - Pause overlay
  - NO Color rectangles

- [ ] **HEALTH BAR uses real graphics**
  - Background: SingleSpriteLoader
  - Fill: HorizontalSpritesheetLoader (100 frames = 1% each)
  - NO gradients, NO java.awt.Color

- [ ] **SCORE DISPLAY uses digit graphics**
  - Digits: GridSpritesheetLoader (0-9 grid)
  - Dynamically renders numbers as graphics
  - NO AWT font rendering

- [ ] **CURSORS are custom graphics**
  - Normal cursor: SingleSpriteLoader
  - Hover cursor: VerticalSpritesheetLoader (animated)
  - NO default OS cursor

- [ ] **ERROR HANDLING is verbose**
  - Every loader logs exact path
  - Failed assets report full path, not just filename
  - System exits gracefully if critical asset missing

- [ ] **ASSET PATHS are complete**
  - FULL: `Resources/industrial-zone/gui/6 Buttons/play_button_4states_vertical.png`
  - NOT: `gui/buttons/play.png`
  - NOT: `res/button.png`

---

## 🎯 INTEGRATION INTO Game.java

Replace current Color-based GUI with GUIManager:

```java
// OLD (WRONG):
g.setColor(Color.BLUE);
g.fillRect(100, 100, 200, 50);  // Color button

// NEW (CORRECT):
GUIManager.getInstance().render(g, screenWidth, screenHeight);
GUIManager.getInstance().handleMouseClick(mouseX, mouseY);
```

---

End of GUI Architecture Document
