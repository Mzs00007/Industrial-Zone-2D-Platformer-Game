# COMPLETE GAME GUI SYSTEM DOCUMENTATION

## Overview

The **Complete Game GUI System** extends `AnimationAndSpriteLoader.java` to provide a unified, comprehensive GUI framework for the CSCU9N6 Industrial Zone Platformer game.

## Architecture

### Core Classes

#### 1. **CompleteGameGUI** (Main GUI System)
- **Location**: `handout/src/gui/CompleteGameGUI.java`
- **Extends**: `AnimationAndSpriteLoader`
- **Purpose**: Master class that integrates all GUI components

**Key Features**:
- Screen state management (splash, menu, gameplay, pause, settings)
- Resource loading from AnimationAndSpriteLoader parent
- Subsystem coordination (screens, rendering, input, state)
- Image asset management for all GUI elements

**Inner Classes**:
- `GUIScreenManager`: Manages screen displays and transitions
- `GUIRenderEngine`: Central rendering system
- `GUIInputManager`: Unified input handling
- `GUIStateManager`: Game state management
- `HUDOverlaySystem`: In-game HUD rendering
- `NotificationSystem`: In-game notifications
- `TooltipSystem`: Interactive tooltips

#### 2. **GameGUIIntegration**
- **Location**: `handout/src/gui/GameGUIIntegration.java`
- **Purpose**: Integration layer between Game.java and CompleteGameGUI

**Responsibilities**:
- Synchronizes game state to GUI
- Coordinates input handling
- Manages screen transitions
- Updates GUI during game loop

#### 3. **GUIComponentLibrary**
- **Location**: `handout/src/gui/GUIComponentLibrary.java`
- **Purpose**: Reusable GUI component classes

**Components**:
- `AnimatedGUIComponent`: Base class for all animated components
- `AnimatedButton`: Interactive button with states (normal, hover, pressed)
- `AnimatedPanel`: Container component with dragging support
- `AnimatedProgressBar`: Health/energy bar display
- `AnimatedLabel`: Text display with alignment and shadow
- `AnimatedScrollPane`: Scrollable content area

## Usage Guide

### 1. Initializing the GUI System

```java
// In Game.java constructor or main method
GameGUIIntegration guiIntegration = new GameGUIIntegration(this);

// During game loop
guiIntegration.updateGUI(System.currentTimeMillis());
guiIntegration.renderGUI(g2d, screenWidth, screenHeight);
```

### 2. Managing Screen States

```java
// Transition to different screens
guiIntegration.startGameplay();           // Start game
guiIntegration.togglePauseMenu();         // Pause game
guiIntegration.levelComplete();           // Show level complete
guiIntegration.gameOver();                // Show game over
guiIntegration.goToMainMenu();            // Return to menu
```

### 3. Creating Custom GUI Components

```java
// Create a custom button
AnimatedButton btnStart = new AnimatedButton(100, 200, 200, 50, "START");
btnStart.setOnClickListener(() -> {
    System.out.println("Game started!");
    guiIntegration.startGameplay();
});

// Create a progress bar for health
AnimatedProgressBar healthBar = new AnimatedProgressBar(10, 10, 300, 20);
healthBar.setProgress(0.75f);  // 75% health
healthBar.setBarColor(new Color(100, 200, 100));

// Create a label
AnimatedLabel scoreLabel = new AnimatedLabel(50, 50, "Score: 1000");
scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
scoreLabel.setAlignment(AnimatedLabel.HorizontalAlignment.CENTER);
```

### 4. Handling Input

```java
// Keyboard input
guiIntegration.handleKeyInput(keyCode, pressed);

// Mouse input
guiIntegration.handleMouseInput(mouseX, mouseY, mouseButton, pressed);
```

### 5. Accessing GUI Resources

```java
CompleteGameGUI gui = guiIntegration.getCompleteGUI();

// Get loaded images
Map<String, BufferedImage> backgrounds = gui.getBackgroundImages();
Map<String, BufferedImage> uiElements = gui.getUIElementImages();
Map<String, BufferedImage> screens = gui.getScreenImages();

// Get subsystems
HUDOverlaySystem hud = gui.getHUDOverlay();
NotificationSystem notifications = gui.getNotificationSystem();
TooltipSystem tooltips = gui.getTooltipSystem();
```

## Class Inheritance Hierarchy

```
AnimationAndSpriteLoader (parent with all asset management)
    ↓
CompleteGameGUI (main GUI system)
    ├─ GUIScreenManager (screen management)
    ├─ GUIRenderEngine (rendering)
    ├─ GUIInputManager (input handling)
    ├─ GUIStateManager (state management)
    ├─ HUDOverlaySystem (HUD display)
    ├─ NotificationSystem (notifications)
    └─ TooltipSystem (tooltips)

AnimatedGUIComponent (base gui component)
    ├─ AnimatedButton (clickable button)
    ├─ AnimatedPanel (container)
    ├─ AnimatedProgressBar (progress display)
    ├─ AnimatedLabel (text display)
    └─ AnimatedScrollPane (scrollable area)
```

## Screen State Flow

```
SPLASH_SCREEN
    ↓
MAIN_MENU → CHARACTER_SELECT → LEVEL_SELECT → PLAYING ← PAUSED
    ↓                                             ↓
HOW_TO_PLAY                              LEVEL_COMPLETE
    ↓                                         ↓
SETTINGS                                GAME_OVER
    ↓
(Back to MAIN_MENU)
```

## Resource Loading

### Image Asset Organization

```
Resources/
  ├─ gui/
  │  ├─ backgrounds/
  │  │  ├─ splash_screen.png
  │  │  ├─ main_menu_bg.png
  │  │  ├─ character_select_bg.png
  │  │  └─ ...
  │  ├─ elements/
  │  │  ├─ button_normal.png
  │  │  ├─ button_hover.png
  │  │  ├─ button_pressed.png
  │  │  └─ ...
  │  └─ screens/
  │     ├─ minimap_frame.png
  │     ├─ dialogue_box.png
  │     └─ ...
  └─ ...
```

### Loading Resources from AnimationAndSpriteLoader

```java
// The CompleteGameGUI class automatically loads:
// 1. Sprite Registry - All sprite assets
// 2. Animation System - Animation sequences
// 3. Parallax System - Parallax layer images
// 4. VFX System - Visual effects
```

## Rendering Pipeline

```
1. Game World Rendering (handled by Game.java)
   ↓
2. Parallax Layers (from AnimationAndSpriteLoader)
   ↓
3. Game Entities (sprites, tiles, effects)
   ↓
4. HUD Overlay (via CompleteGameGUI)
   ├─ Health/Energy bars
   ├─ Score display
   ├─ Status indicators
   └─ Game state info
   ↓
5. Menu Overlays (screen state dependent)
   ├─ Main Menu
   ├─ Pause Menu
   ├─ Settings Panel
   └─ Screen Transitions
   ↓
6. Notifications & Tooltips (floating elements)
```

## State Synchronization

Game state flows from Game.java to GUI:

```
Game.java (game logic)
    ↓
GameGUIIntegration (sync layer)
    ↓
CompleteGameGUI (display)
    ↓
HUDOverlaySystem (render)
```

**Synchronized Values**:
- Player health/energy/shield
- Current score/coins
- Current level/stage
- Equipped weapon
- Active status effects
- Game time/timer

## Input Handling Flow

```
User Input (keyboard/mouse)
    ↓
Game.java KeyListener/MouseListener
    ↓
GameGUIIntegration.handleKeyInput()
    ↓
CompleteGameGUI.handleGUIInput()
    ↓
GUIInputManager (processes input)
    ↓
Affected Components (buttons, panels, etc.)
    ↓
Action Callbacks/Event Handlers
```

## Extension Points

### Creating Custom Screens

```java
// Add to CompleteGameGUI
private CustomScreen customScreen;

// In ScreenState enum
HOW_TO_PLAY,
CUSTOM_SCREEN,  // Add your screen
SETTINGS,

// In transitionToScreen()
case CUSTOM_SCREEN:
    screenManager.showCustomScreen();
    break;
```

### Creating Custom Components

```java
public class CustomComponent extends AnimatedGUIComponent {
    @Override
    public void render(Graphics2D g) {
        // Your rendering code
    }
    
    @Override
    public void update(float deltaTime) {
        // Your update logic
    }
}
```

### Adding GUI Event Listeners

```java
// In GameGUIIntegration or GameStateSync
completeGUI.getNotificationSystem().addListener(() -> {
    System.out.println("Notification triggered");
});
```

## Performance Considerations

1. **Image Caching**: All background and UI images are cached in maps
2. **Clipping**: Scroll panes use graphics clipping for efficiency
3. **Delta Time**: All animations use delta time for frame-rate independence
4. **Lazy Loading**: Resources loaded on-demand, not all at startup

## Troubleshooting

### GUI Not Appearing
- Check: Is `guiIntegration.renderGUI()` being called?
- Verify: Screen state is correctly set
- Ensure: Graphics2D is properly passed to render methods

### Input Not Responding
- Check: Key listeners registered correctly
- Verify: Input coordinates in screen space
- Ensure: Components are enabled and visible

### Images Not Loading
- Verify: Resource paths are correct
- Check: Image files exist in Resources directory
- Ensure: File formats are PNG or JPEG

### Performance Issues
- Reduce: Number of animated components on screen
- Optimize: Image dimensions
- Check: Update methods for expensive operations

## Best Practices

1. **State Management**: Always update game state before rendering
2. **Resource Cleanup**: Dispose of Graphics2D objects properly
3. **Error Handling**: Wrap file I/O in try-catch blocks
4. **Component Reuse**: Use the same component instances across frames
5. **Animation Timing**: Use provided deltaTime parameter for smooth animation

## Integration Checklist

- [ ] Create GameGUIIntegration instance in Game.java
- [ ] Call updateGUI() in game loop update
- [ ] Call renderGUI() in game loop draw
- [ ] Register input handlers (keyboard, mouse)
- [ ] Verify image resources exist
- [ ] Test screen transitions
- [ ] Verify state synchronization
- [ ] Profile performance

## Future Extensions

- [ ] Dialog system with branching conversations
- [ ] Inventory management interface
- [ ] Quest log and tracking
- [ ] Achievements/unlockables display
- [ ] Video/cutscene player
- [ ] Configuration/rebinding UI
- [ ] Accessibility options
- [ ] Localization system

---

**Created**: April 3, 2026
**Last Updated**: April 3, 2026
**System Version**: 1.0
