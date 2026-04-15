# Framework Dependencies & Architecture

## Component Overview

```
                         ┌──────────────────────┐
                         │  GameFramework       │
                         │  (Pure game loop)    │
                         └──────┬───────────────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
                ↓               ↓               ↓
        ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
        │ LevelManager │  │ GUIManager   │  │ScreenManager │
        │(Levels +     │  │(Basic GUI +  │  │(Phase 2-15   │
        │ Parallax +   │  │GameState)    │  │+ Menus)      │
        │ Camera)      │  └──────────────┘  └──────────────┘
        └──────────────┘
```

---

## Dependency Graph

### GameFramework (Main)
**Purpose:** Pure game loop foundation
**Extends:** GameCore (game2D base)
**Depends On:**
- `LevelManager` - For level/parallax/camera updates & rendering
- `GUIManager` - For GUI component updates & rendering  
- `ScreenManager` - For UI screen updates & rendering

**Key Methods:**
```java
initialize()              // Initialize all subsystems
update(deltaMillis)       // Called each frame - delegates to managers
draw(Graphics2D)          // Called each frame - delegates to managers
keyReleased(KeyEvent)     // Handle input - ESC for level switch
```

**Responsibilities:**
- Create JFrame window
- Start game loop (inherited from GameCore)
- Coordinate manager updates/renders
- Handle top-level input (ESC key)
- **Does NOT:** Initialize GUI, manage levels, render anything directly

---

### LevelManager
**Purpose:** Level lifecycle, parallax, camera management
**Depends On:**
- `Level1` - Static class for Level 1 setup
- `Level2` - Static class for Level 2 setup
- `AnimationAndSpriteLoader` - Create parallax systems
- `GameCore.TileMap` - Container for level data
- `GameCore.Animation.ParallaxSystem` - Parallax rendering

**Level1 Parallax:** Background images with camera offset calculation
**Level2 Parallax (Day/Night):** Alternative set of background layers

**Key Methods:**
```java
initialize()              // Load levels & parallax
update(deltaMillis)       // Update camera position
render(g, w, h)          // Render background + parallax
switchLevel()            // Toggle Level 1 ↔ Level 2
```

**State Maintained:**
```
currentLevel: 1 or 2
isLevel1Active: boolean
level1TileMap: TileMap object
level2TileMap: TileMap object
level1Parallax: ParallaxSystem
level2ParallaxDay: ParallaxSystem
level2ParallaxNight: ParallaxSystem (optional)
cameraX, cameraY: float (camera position)
```

**Responsibilities:**
- Load both levels on initialization
- Create parallax systems for visual depth
- Update camera smoothly (200 px/sec)
- Clamp camera to level bounds [0, 22400]
- Render background + parallax with camera offset
- Switch between Level 1 and Level 2 on ESC key
- **Does NOT:** Handle GUI, input (except ESC delegation), main menus

---

### GUIManager
**Purpose:** Basic GUI component lifecycle
**Depends On:**
- `GameState` - Current player stats/timer
- `TopBarPanel` - Top UI bar
- `HUDPanel` - Main HUD display
- `LeftSidebar` - Left side panel
- `ButtonPanel` - Main buttons
- `MouseInputHandler` - Mouse event handling
- `GUIAssetManager` - Singleton for sprite assets

**Key Methods:**
```java
initialize(JFrame)        // Create all GUI components & listeners
update(deltaMillis)       // Update GUI state (timers, status)
render(g, w, h)          // Render all GUI components
updateGameState(g, state) // Sync with game state
```

**State Maintained:**
```
gameState: GameState
├─ currentLevel: "LEVEL_1" or "LEVEL_2"
├─ levelName: String description
├─ health, maxHealth: int
├─ energy, maxEnergy: int
├─ armor: int
├─ ammo, ammoMax: int
├─ totalElapsedSeconds: int (cumulative)
└─ timeRemainingSeconds: int (countdown)

topBarPanel: TopBarPanel
hudPanel: HUDPanel
leftSidebar: LeftSidebar
buttonPanel: ButtonPanel
mouseInputHandler: MouseInputHandler
```

**Responsibilities:**
- Initialize GameState with default values
- Create & configure all basic GUI components
- Register mouse event listeners
- Update game state timers each frame
- Pass GameState to GUI components during update
- Render all GUI layers in proper order
- **Does NOT:** Render Phase 2-15 screens (ScreenManager), handle menus, load levels

---

### ScreenManager
**Purpose:** Phase 2-15 screen lifecycle & menu coordination
**Depends On:**
- `Phase2CharacterIdleScreen` through `Phase15SettingsScreen` (14 screens)
- `MenuInputHandler` - Menu input/callbacks
- `Phase13MainMenuScreen.MenuAction` - Menu action enum
- `Phase14PauseMenuScreen.PauseAction` - Pause action enum

**All 14 Screens Managed:**
```
Phase 2:  CharacterIdleScreen
Phase 3:  StatusBarScreen
Phase 4:  NumericDisplayScreen
Phase 5:  ButtonScreen
Phase 6:  DecorationScreen
Phase 7:  ItemInventoryScreen
Phase 8:  MinimapScreen
Phase 9:  DialogueScreen
Phase 10: TooltipScreen
Phase 11: NotificationScreen
Phase 12: QuestTrackerScreen
Phase 13: MainMenuScreen (main menu)
Phase 14: PauseMenuScreen (pause overlay)
Phase 15: SettingsScreen (settings UI)
```

**Key Methods:**
```java
initialize()              // Create all 14 screens + menu handler
update(deltaMillis)       // Update all screens
render(g, w, h)          // Render all screens in order
handleKeyEvent(KeyEvent) // Delegate key events to menu handler
handleMainMenuAction()    // Callback for main menu
handlePauseMenuAction()   // Callback for pause menu
```

**Menu Action Callbacks:**
```java
MenuInputHandler.MenuActionCallback {
  onMainMenuAction(MenuAction)    // NEW_GAME, CONTINUE, SETTINGS, CREDITS, EXIT
  onPauseMenuAction(PauseAction)  // RESUME, SETTINGS, HELP, SAVE_EXIT
  onSettingsChanged(key, value)   // Setting name → new value
  onMenuStateChanged(old, new)    // State transition logging
}
```

**Rendering Order (Bottom-to-Top Layer):**
1. Phase 2 character idle
2. Phase 3 status bar
3. ... [Phases 4-12]
4. Phase 13 main menu (if displayed)
5. Phase 14 pause menu (if paused)
6. Phase 15 settings screen (top layer)

**Responsibilities:**
- Initialize all 14 UI screens
- Set up MenuInputHandler with callbacks
- Update all screens each frame
- Render screens in proper layering order
- Handle main menu actions (NEW_GAME, CONTINUE, EXIT, etc.)
- Handle pause menu actions (RESUME, SETTINGS, etc.)
- Log menu state transitions
- **Does NOT:** Render game world (LevelManager), render basic GUI (GUIManager), manage levels

---

## Interaction Patterns

### Manager Initialization Order
```
GameFramework constructor
  ↓ (stores references)
GameFramework.initialize()
  ├─ LevelManager.initialize()
  │  └─ Load tilemaps, parallax, assets
  ├─ GUIManager.initialize(frame)
  │  ├─ Create GameState
  │  ├─ Create GUI components
  │  └─ Register mouse listeners (on frame)
  └─ ScreenManager.initialize()
     ├─ Create 14 screens
     └─ Create MenuInputHandler
```

### Frame Update Sequence
```
GameCore game loop
  └─ GameFramework.update(deltaMillis)
      ├─ LevelManager.update(deltaMillis) [Camera + Parallax]
      ├─ GUIManager.update(deltaMillis) [Timers + Component state]
      └─ ScreenManager.update(deltaMillis) [14 screens]
```

### Frame Render Sequence
```
GameCore game loop
  └─ GameFramework.draw(Graphics2D)
      ├─ LevelManager.render(g) [Background + Parallax]
      ├─ GUIManager.render(g) [Basic GUI components]
      └─ ScreenManager.render(g) [Phase 2-15 screens in order]
      
Result: Layered composition
  Bottom: World (parallax)
  Middle: Basic GUI (HUD, bars, panels)
  Top: UI Screens (character, status, inventory, etc.)
```

### Input Event Routing
```
User presses key
  └─ JFrame KeyListener (inherited from GameCore)
      └─ GameFramework.keyReleased(KeyEvent)
          ├─ If ESC:
          │  └─ LevelManager.switchLevel()
          └─ Else:
             └─ ScreenManager.handleKeyEvent(KeyEvent)
                 └─ MenuInputHandler.handleKeyEvent(KeyEvent)
                     └─ Trigger appropriate callback

User moves/clicks mouse
  └─ MouseListener (registered by GUIManager)
      └─ MouseInputHandler.mousePressed/Released/Moved
          └─ Update button state
```

### Menu Action Callback Chain
```
User clicks "Start Game" button
  └─ MenuInputHandler detects click
      └─ Calls callback: MenuActionCallback.onMainMenuAction(NEW_GAME)
          └─ ScreenManager.handleMainMenuAction(MenuAction.NEW_GAME)
              └─ Print log: "[ScreenManager] Main Menu: Starting new game..."
                  └─ [Could trigger actual game start logic here]
```

---

## Dependency Violations to Avoid

**❌ These would break architecture:**

1. GUIManager accessing LevelManager directly
   - ❌ `guiManager.getLevelState()`
   - ✅ Pass LevelManager reference explicitly if needed

2. ScreenManager creating its own GameState
   - ❌ `new GameState()` in ScreenManager
   - ✅ Receive GameState from GUIManager

3. LevelManager managing UI screens
   - ❌ Level rendering Phase13MainMenuScreen
   - ✅ Keep Level separate from UI

4. Direct GUIAssetManager usage from GameFramework
   - ❌ `gameMgr = GUIAssetManager.getInstance()`
   - ✅ Access through GUIManager

5. Circular dependencies (Manager A → B → A)
   - **Current:** DAG (Directed Acyclic Graph) ✓
   - **Risk:** Adding back-references breaks this

---

## Extending the Framework

### Adding a New Manager
1. Create `NewManager` class in `1_Framework/`
2. Add field to `GameFramework`
3. Instantiate in `main()` or dependency injector
4. Pass to constructor: `new GameFramework(levelMgr, guiMgr, screenMgr, newMgr)`
5. Call `newMgr.initialize()` in `GameFramework.initialize()`
6. Call `newMgr.update()` in `GameFramework.update()`
7. Call `newMgr.render()` in `GameFramework.draw()` (if applicable)

### Adding a New Screen
1. Create `PhaseXYZScreen` class
2. Add field to `ScreenManager`
3. Initialize in `ScreenManager.initialize()`
4. Add update call in `ScreenManager.update()`
5. Add render call in `ScreenManager.render()` (in proper layer order)

### Swapping Implementations
**Example: Replace MouseInputHandler**
1. Create `NewMouseHandler` with same interface
2. Instantiate in `GUIManager.initializePhase2GUI()`
3. Register as listener instead: `frame.addMouseListener(newHandler)`
4. **No changes needed** in GameFramework or other managers ✓

---

## Error Propagation

**Graceful degradation strategy:**

```
If LevelManager initialization fails:
  └─ Prints error with context
      └─ Black background renders instead
           └─ Game continues (can switch levels, but visual glitch)

If GUIManager initialization fails:
  └─ Prints error with context
      └─ GameState stays null
           └─ GUI components null-checked before render
                └─ No GUI displayed, but game still works

If ScreenManager initialization fails:
  └─ Prints error with context
      └─ Individual screens may be null
           └─ Null checks skip missing screens
                └─ Menu still functional if key screens initialized
```

**Best Practice:** Check logs for ✓ symbols during startup to verify all systems initialized.

---

## Unit Testing Implications

**Each manager can be tested independently:**

```java
// Test LevelManager
LevelManager lm = new LevelManager();
lm.initialize();
assert lm.getCurrentTileMap() != null;
lm.switchLevel();
assert lm.isLevel1Active() == false;

// Test GUIManager (with mock JFrame)
JFrame mockFrame = new JFrame();
GUIManager gm = new GUIManager();
gm.initialize(mockFrame);
assert gm.getGameState() != null;

// Test ScreenManager
ScreenManager sm = new ScreenManager();
sm.initialize();
assert sm.getMainMenuScreen() != null;
```

**No interdependencies = Easy testing** ✓

---

## Performance Monitoring

**Monitor these if performance degrades:**

| Component | Typical Cost | Rising Indicator |
|-----------|-------------|-----------------|
| LevelManager.update() | 2-3ms | Camera movement choppy |
| GUIManager.update() | 1-2ms | Timers lag |
| ScreenManager.update() | 1ms | Screen animations stutter |
| LevelManager.render() | 3-4ms | Parallax flickers |
| GUIManager.render() | 1-2ms | GUI flickers |
| ScreenManager.render() | 2-3ms | Screen overlap visible |

**Total budget:** ~16ms @ 60 FPS

---

**End of Dependencies Documentation**
