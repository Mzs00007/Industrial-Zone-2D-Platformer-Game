# Game Lifecycle Documentation

## Overview

This document describes the lifecycle of the refactored game framework, from startup through shutdown.

---

## Initialization Sequence (Startup)

### Phase 1: JVM Process Start
```
main(String[] args)
├─ Get screen dimensions
├─ Create LevelManager instance
├─ Create GUIManager instance
├─ Create ScreenManager instance
└─ Create GameFramework with all managers (Dependency Injection)
```

### Phase 2: Framework Construction
```
new GameFramework(levelMgr, guiMgr, screenMgr)
├─ Store manager references
├─ Set JFrame properties
│  ├─ Title: "CSCU9N6 Industrial Zone Platformer"
│  ├─ DefaultCloseOperation: EXIT_ON_CLOSE
│  ├─ Resizable: true
│  └─ Visible: false (initially)
└─ Ready for initialization
```

### Phase 3: Subsystem Initialization
```
framework.initialize()
├─ LevelManager.initialize()
│  ├─ initializeLevels()
│  │  ├─ Create Level1 TileMap
│  │  ├─ Run Level1.initialize(tilemap)
│  │  ├─ Create Level2 TileMap
│  │  └─ Run Level2.initialize(tilemap)
│  ├─ initializeParallaxSystems()
│  │  ├─ Create Level1 parallax (single layer)
│  │  ├─ Create Level2 Day parallax (multiple layers)
│  │  └─ Create Level2 Night parallax (optional)
│  └─ initializeRasterAssets()
│     ├─ Create black background tile (32x32)
│     └─ Create HUD panel background (1024x50)
│
├─ GUIManager.initialize(frame)
│  ├─ initializeGameState()
│  │  ├─ Create GameState object
│  │  ├─ Set level name: "INDUSTRIAL ZONE"
│  │  ├─ Set health: 100/100
│  │  ├─ Set energy: 80/100
│  │  ├─ Set armor: 50
│  │  ├─ Set ammo: 3/12
│  │  └─ Set time: 300 seconds (5 minutes)
│  ├─ initializeBasicGUI()
│  │  ├─ Create TopBarPanel
│  │  ├─ Load TopBar assets
│  │  ├─ Create HUDPanel
│  │  └─ Load HUD assets
│  └─ initializePhase2GUI()
│     ├─ Get GUIAssetManager singleton
│     ├─ Create LeftSidebar
│     ├─ Create ButtonPanel
│     ├─ Create MouseInputHandler
│     └─ Register mouse event listeners
│
└─ ScreenManager.initialize()
   ├─ Create Phase2CharacterIdleScreen
   ├─ Create Phase3StatusBarScreen
   ├─ Create Phase4NumericDisplayScreen
   ├─ Create Phase5ButtonScreen
   ├─ Create Phase6DecorationScreen
   ├─ Create Phase7ItemInventoryScreen
   ├─ Create Phase8MinimapScreen
   ├─ Create Phase9DialogueScreen
   ├─ Create Phase10TooltipScreen
   ├─ Create Phase11NotificationScreen
   ├─ Create Phase12QuestTrackerScreen
   ├─ Create Phase13MainMenuScreen
   ├─ Create Phase14PauseMenuScreen
   ├─ Create Phase15SettingsScreen
   └─ Initialize MenuInputHandler with all menus
```

### Phase 4: Game Loop Start
```
framework.run(false, screenWidth, screenHeight)
├─ GameCore.run() (from game2D) starts
├─ JFrame becomes visible
├─ Sets JFrame size: screenWidth x screenHeight
└─ Enters game loop
```

---

## Game Loop (Runtime)

### Every Frame Cycle
```
GameCore.update() [Called by GameCore loop]
│
├─ GameFramework.update(deltaMillis)
│  ├─ LevelManager.update(deltaMillis)
│  │  ├─ updateCamera(deltaMillis)
│  │  │  ├─ Calculate camera speed: 200 pixels/second
│  │  │  ├─ cameraX += speed * deltaSeconds
│  │  │  └─ Clamp camera to level bounds [0, 22400]
│  │  └─ parallaxSystem.updateCamera(cameraX)
│  │
│  ├─ GUIManager.update(deltaMillis)
│  │  ├─ Update gameState timers
│  │  │  ├─ totalElapsedSeconds += deltaSeconds
│  │  │  └─ timeRemainingSeconds = max(0, time - deltaSeconds)
│  │  ├─ topBarPanel.update(deltaMillis, gameState)
│  │  ├─ hudPanel.update(deltaMillis, gameState)
│  │  ├─ leftSidebar.update(deltaMillis, gameState)
│  │  └─ buttonPanel.update(deltaMillis, gameState)
│  │
│  └─ ScreenManager.update(deltaMillis)
│     ├─ characterIdleScreen.update(deltaSeconds)
│     ├─ statusBarScreen.update(deltaSeconds)
│     ├─ numericDisplayScreen.update(deltaSeconds)
│     ├─ [... 12 more screens ...]
│     └─ settingsScreen.update(deltaSeconds)
│

GameCore.draw(Graphics2D) [Called by GameCore after update]
│
└─ GameFramework.draw(Graphics2D)
   ├─ LevelManager.render(g, width, height)
   │  ├─ renderBackgroundRaster() - Black background tiles
   │  ├─ renderParallaxRaster() - Parallax layers with camera offset
   │  └─ renderHUDRaster() - HUD panel background bar
   │
   ├─ GUIManager.render(g, width, height)
   │  ├─ topBarPanel.render(g)
   │  ├─ hudPanel.render(g)
   │  ├─ hudPanel.updateWithGameState(g, gameState)
   │  ├─ leftSidebar.render(g)
   │  └─ buttonPanel.render(g)
   │
   └─ ScreenManager.render(g, width, height)
      ├─ Phase 2 character idle screen
      ├─ Phase 3 status bar screen
      ├─ ... [12 more screens in order] ...
      ├─ Phase 14 pause menu (if paused)
      └─ Phase 15 settings screen
```

---

## Input Handling (Runtime)

### Keyboard Input
```
GameCore (KeyListener inherited)
│
└─ GameFramework.keyReleased(KeyEvent)
   ├─ If ESC (keyCode == 27):
   │  └─ LevelManager.switchLevel()
   │     ├─ isLevel1Active = !isLevel1Active
   │     ├─ cameraX = 0 (reset to start)
   │     └─ Print level switch message
   │
   └─ Else:
      └─ ScreenManager.handleKeyEvent(KeyEvent)
         └─ MenuInputHandler.handleKeyEvent(KeyEvent)
            ├─ Check for main menu actions
            ├─ Check for pause menu actions
            └─ Trigger appropriate callbacks
```

### Mouse Input
```
JFrame (MouseListener registered by GUIManager)
│
└─ MouseInputHandler.mousePressed/Released/Moved/etc
   ├─ Check button hover state
   ├─ Detect button clicks
   └─ Update button panel state
```

### Menu Input
```
MenuInputHandler (KeyListener in ScreenManager)
│
├─ Main Menu Actions (when mainMenuScreen is active):
│  ├─ NEW_GAME → Start new game
│  ├─ CONTINUE → Load saved game
│  ├─ SETTINGS → Open settings screen
│  ├─ CREDITS → Show credits
│  └─ EXIT → System.exit(0)
│
├─ Pause Menu Actions (when game is paused):
│  ├─ RESUME → Unpause game
│  ├─ SETTINGS → Open settings
│  ├─ HELP → Show help dialog
│  └─ SAVE_EXIT → Save & exit to main menu
│
└─ Settings Changes:
   └─ Update configuration values
```

---

## Level Switching (ESC key)

```
User presses ESC
│
└─ GameFramework.keyReleased(KeyEvent)
   └─ LevelManager.switchLevel()
      ├─ Toggle isLevel1Active
      ├─ Reset cameraX to 0
      ├─ Update parallax reference
      ├─ Load new tilemap
      └─ Print: "[LevelManager] Switched to Level _"
```

---

## Shutdown Sequence (Window Close)

### Phase 1: User closes window
```
User clicks X button on JFrame
│
└─ JFrame WindowEvent
   └─ JFrame.setDefaultCloseOperation(EXIT_ON_CLOSE)
      └─ System.exit(0)
```

### Phase 2: JVM cleanup
```
System.exit(0)
├─ Shutdown all threads
├─ Close all resources
├─ Unload all listeners
└─ Terminate JVM
```

### Alternative: Menu Exit (Alt from shutdown)
```
User clicks EXIT in main menu
│
└─ ScreenManager.handleMainMenuAction(MenuAction.EXIT)
   └─ System.exit(0)
```

---

## Error Handling

### During Initialization
```
If error in LevelManager.initialize():
└─ Print: "[LevelManager ERROR] <message>"
   ├─ Stack trace printed
   └─ Continue execution (graceful degradation)

If error in GUIManager.initialize():
└─ Print: "[GUIManager ERROR] <message>"
   ├─ Stack trace printed
   └─ GUI components may be null, handled during render

If error in ScreenManager.initialize():
└─ Print: "[ScreenManager ERROR] <message>"
   ├─ Stack trace printed
   └─ Continue execution
```

### During Runtime
```
If parallax render fails:
└─ Print: "[LevelManager ERROR] Parallax render failed: <message>"
   └─ Continue rendering (shows black background instead)

If GUI render fails:
└─ Print: "[GUIManager ERROR] <message>" (as applicable)
   └─ Component not rendered (others continue)

If screen render returns null:
└─ Skip rendering that layer
   └─ Continue with other layers
```

---

## State Transitions

```
┌─────────────────────────────────────────────────────────────┐
│ INITIALIZATION STATE                                        │
│ - All managers being created                                │
│ - Assets being loaded                                       │
│ - Listeners being registered                                │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ↓
┌─────────────────────────────────────────────────────────────┐
│ MAIN_MENU STATE                                             │
│ - Main menu screen displayed                                │
│ - Game update/render paused (no level updates)              │
│ - Menu input active                                         │
│ - User can: NEW_GAME, CONTINUE, SETTINGS, CREDITS, EXIT    │
└─────────────────┬───────────────────────────────────────────┘
                  │ (NEW_GAME or CONTINUE selected)
                  ↓
┌─────────────────────────────────────────────────────────────┐
│ PLAYING STATE                                               │
│ - Level active and updating                                 │
│ - GUI rendering in real-time                               │
│ - All screens active                                        │
│ - Player can: Move (arrows), ESC to switch level, Pause     │
└─────────────────┬───────────────────────────────────────────┘
                  │
        ┌─────────┴──────────┐
        │                    │
        ↓ (ESC)              ↓ (PAUSE)
┌──────────────┐     ┌──────────────────┐
│ LEVEL_SWITCH │     │ PAUSED STATE     │
│ - Reset cam  │     │ - Pause menu on  │
│ - Load new   │     │ - Game loop runs │
│ - 1 frame    │     │ - No update()    │
│ - Back to    │     │ - Resume option  │
│   PLAYING    │     └────────┬─────────┘
└──────────────┘              │
                   (RESUME or SAVE_EXIT)
                        │
                        ├─→ PLAYING
                        └─→ MAIN_MENU
```

---

## Performance Characteristics

### Per-Frame Budget
- **Target:** 60 FPS = 16.67ms per frame
- **Delta Time:** LevelManager uses ~2-3ms (camera update)
- **GUI Update:** GUIManager uses ~1-2ms (state sync)
- **Screen Update:** ScreenManager uses ~1ms (14 screens)
- **Rendering:** All systems use ~8-10ms total
- **Headroom:** ~2-3ms for other operations

### Memory Usage
- **Managers:** ~500KB for LevelManager
- **GUI:** ~1MB for all GUI components
- **Screens:** ~2MB for all 14 screens
- **Assets:** Loaded by respective systems (parallax, sprites)
- **Total Approx:** ~10-15MB runtime

### Scalability
- **Adding new manager:** Create, add to GameFramework, call initialize() and update()
- **Adding new screen:** Create, add to ScreenManager, call initialize() and update()
- **No recompilation needed for UI changes** (separate from core framework)

---

## Debugging Tips

1. **Check logs** for initialization messages with ✓ and ✗ symbols
2. **Manager separation** makes debugging single system easy (just disable its update/render)
3. **Null checks** in render methods prevent crashes if asset fails to load
4. **MenuInputHandler callbacks** logged for menu action tracking
5. **Frame rate** shown in window title if enabled in GameCore
6. **Use `levelManager.getCurrentTileMap()`** to debug level loading

---

**End of Lifecycle Documentation**
