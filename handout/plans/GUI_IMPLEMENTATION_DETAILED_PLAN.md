# COMPREHENSIVE GUI IMPLEMENTATION PLAN
## CSCU9N6 Industrial Zone Platformer - GUI Subsystem
### Last Updated: April 2, 2026

---

## TABLE OF CONTENTS
1. [Executive Overview](#executive-overview)
2. [Architecture & Design Principles](#architecture--design-principles)
3. [Game State Machine](#game-state-machine)
4. [Asset Structure & Organization](#asset-structure--organization)
5. [GUI Component Architecture](#gui-component-architecture)
6. [Rendering Pipeline](#rendering-pipeline)
7. [State-Specific GUI Layouts](#state-specific-gui-layouts)
8. [Button System Architecture](#button-system-architecture)
9. [Input Handling System](#input-handling-system)
10. [Animation & Visual Effects](#animation--visual-effects)
11. [Implementation Phases](#implementation-phases)

---

## EXECUTIVE OVERVIEW

### Project Scope
The GUI system is the primary interface for player interaction with the game. It displays:
- **Game State Information**: Health, ammo, score, level progress
- **Interactive Controls**: Buttons for weapons, pause, settings, help
- **Visual Feedback**: Animations, transitions, status indicators
- **Asset-Based Rendering**: Pure image/sprite-based rendering (NO vector graphics)

### Design Philosophy
1. **Asset-First**: All visuals come from pre-made PNG/image files
2. **State-Driven**: GUI changes based on game state transitions
3. **Extensible**: Easy to add new panels, buttons, and screens
4. **Performance**: Efficient caching and batch rendering
5. **Real-Time**: Smooth animations at 60 FPS minimum

### Key Constraints
- ❌ NO vector graphics (no Graphics2D drawing primitives)
- ✅ ONLY PNG/Image-based assets from Resources folders
- ✅ Extend AnimationAndSpriteLoader for asset loading
- ✅ Leverage existing GUI asset structure (6 button themes, 82 frame tiles, etc.)

---

## ARCHITECTURE & DESIGN PRINCIPLES

### Fundamental Architecture Pattern
```
┌─────────────────────────────────────────────────────────┐
│                    GAME MAIN WINDOW                      │
│  (GameCore extends JFrame - maintains context)           │
│                                                           │
│  ┌───────────────────────────────────────────────────┐  │
│  │              GamePanel (JPanel)                    │  │
│  │  ┌─────────────────────────────────────────────┐  │  │
│  │  │          Background Canvas                  │  │  │
│  │  │  (Parallax, level geometry renders here)    │  │  │
│  │  └─────────────────────────────────────────────┘  │  │
│  │                                                    │  │
│  │  ┌─────────────────────────────────────────────┐  │  │
│  │  │     GUI Overlay Compositing Layer           │  │  │
│  │  │  ┌─────────────────┐  ┌──────────────────┐ │  │  │
│  │  │  │  TopBar Panel   │  │   Right Sidebar  │ │  │  │
│  │  │  │  (HUD info)     │  │  (Button Panel)  │ │  │  │
│  │  │  └─────────────────┘  └──────────────────┘ │  │  │
│  │  │                                            │  │  │
│  │  │  ┌─────────────────────────────────────┐  │  │  │
│  │  │  │  Left Sidebar (Inventory)           │  │  │  │
│  │  │  └─────────────────────────────────────┘  │  │  │
│  │  │                                            │  │  │
│  │  │  ┌─────────────────────────────────────┐  │  │  │
│  │  │  │  Bottom HUD Bar (Health/Status)     │  │  │  │
│  │  │  └─────────────────────────────────────┘  │  │  │
│  │  └─────────────────────────────────────────────┘  │  │
│  │                                                    │  │
│  │  Note: All GUI elements are image-based,         │  │
│  │        rendered to BufferedImage, then composited │  │
│  └───────────────────────────────────────────────────┘  │
│                                                           │
│  ┌───────────────────────────────────────────────────┐  │
│  │         Screen Management System                  │  │
│  │  (Main Menu, Level Select, Game Over, Pause)      │  │
│  │  [Replaces entire render pipeline when active]    │  │
│  └───────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
```

### Key Design Decisions

#### 1. **Component Separation**
Each GUI panel is a separate, self-contained component:
- Manages its own asset loading
- Extends AnimationAndSpriteLoader for shared utilities
- Renders to BufferedImage independently
- Receives compose commands from parent

#### 2. **Asset-Based vs Vector Graphics**
**WRONG (Current)**: Using Graphics2D primitives to draw shapes
```java
//  ❌ WRONG - This is what we're ELIMINATING
g2d.fillRect(x, y, width, height);         // Vector rectangle
g2d.drawString(...);                        // Rendering text as vector
g2d.fillRoundRect(...);                     // Rounded corners as vector
```

**RIGHT (New)**: Loading pre-made PNG images
```java
//  ✅ CORRECT - This is what we're implementing
BufferedImage buttonAsset = loadFromResources("gui/buttons/btn_attack.png");
g2d.drawImage(buttonAsset, x, y, null);     // Direct image composition
```

#### 3. **State-Driven Rendering**
The GUI appearance changes based on game state:

```
┌──────────────────────────────────────────────────────┐
│           GAME STATE ENUM                            │
├──────────────────────────────────────────────────────┤
│  MAIN_MENU          → MainMenuScreen panel            │
│  LEVEL_SELECT       → LevelSelectScreen panel         │
│  GAME_ACTIVE        → InGameGUI (all 4 panels)        │
│  GAME_PAUSED        → PauseMenuScreen panel           │
│  GAME_OVER          → GameOverScreen panel            │
│  LEVEL_COMPLETE     → LevelCompleteScreen panel       │
│  SETTINGS           → SettingsScreen panel            │
│  CHARACTER_SELECT   → CharacterSelectScreen panel     │
└──────────────────────────────────────────────────────┘
   ↓
   Each state maps to a specific GUI configuration
   and rendering pipeline
```

---

## GAME STATE MACHINE

### State Transition Diagram

```
                          ┌─────────────────┐
                          │   MAIN_MENU     │
                          │  (Start Screen) │
                          └────────┬────────┘
                                   │ "New Game" clicked
                                   ↓
                          ┌─────────────────┐
                          │CHARACTER_SELECT │
                          │ (Avatar Choice) │
                          └────────┬────────┘
                                   │ Character selected
                                   ↓
                          ┌─────────────────┐
                          │ LEVEL_SELECT    │
                          │(Level Choice)   │
                          └────────┬────────┘
                                   │ Level selected
                                   ↓
                    ┌──────────────────────────────┐
                    │      GAME_ACTIVE            │
                    │  (Gameplay - 4 panels)      │
                    └──┬──────────────────────┬───┘
                       │                      │
         (P key/pause) │                      │ (death/timer)
                       ↓                      ↓
            ┌──────────────────┐  ┌──────────────────┐
            │  GAME_PAUSED     │  │   GAME_OVER      │
            │ (Resume/Quit)    │  │ (Restart/Quit)   │
            └────────┬─────────┘  └────────┬─────────┘
                     │                      │
        (Resume/Quit)│                      │
                     └──────░───────────────┘
                            │
                            ↓ (Quit)
                     ┌─────────────────┐
                     │   MAIN_MENU     │
                     │  (Back to Start) │
                     └─────────────────┘

Special Transitions:
- Level Complete → LEVEL_SELECT (after delay)
- Settings → Previous state (non-blocking overlay)
- Help Screen → Previous state (non-blocking overlay)
```

### State-to-GUI-Panel Mapping

Each game state requires specific GUI configuration:

| Game State | Active Panels | Input Mode | Asset Sets |
|-----------|--------------|-----------|-----------|
| MAIN_MENU | MenuScreen only | UI Navigation | Logo, buttons (large) |
| LEVEL_SELECT | LevelButtons + Desc | UI Navigation | Level thumbnails, buttons |
| CHARACTER_SELECT | CharacterCards | UI Navigation + Preview | Character portraits, stats |
| GAME_ACTIVE | TopBar + HUD + Sidebar + ButtonPanel | Game Controls | All in-game GUI assets |
| GAME_PAUSED | PauseMenu overlay | UI Navigation | Buttons (large), background tint |
| GAME_OVER | GameOverScreen | UI Navigation | End screen graphics, buttons |
| LEVEL_COMPLETE | CompleteScreen | UI Navigation | Completion graphics, buttons |
| SETTINGS | SettingsScreen overlay | UI Navigation | Sliders, toggles, buttons |
| HELP | HelpScreen overlay | UI Navigation | Help text, diagrams |

---

## ASSET STRUCTURE & ORGANIZATION

### Complete Asset Hierarchy from AnimationAndSpriteLoader.java

```
Resources/
├── industrial-zone/
│   ├── gui/                              ← PRIMARY GUI ASSETS
│   │   ├── 1 Frames/                     ✓ 82 tiles × 7 themes
│   │   │   ├── frame_theme_1_*.png
│   │   │   ├── frame_theme_2_*.png
│   │   │   └── ... (7 complete themes)
│   │   │
│   │   ├── 2 Bars/                       ✓ Health, ammo, mana bars
│   │   │   ├── health_bar_full.png
│   │   │   ├── health_bar_empty.png
│   │   │   ├── ammo_bar_*.png
│   │   │   └── ...
│   │   │
│   │   ├── 3 Icons/                      ✓ Status icons
│   │   │   ├── Buttons2/                 ✓ Button-specific icons
│   │   │   │   ├── button_pause.png
│   │   │   │   ├── button_play.png
│   │   │   │   ├── button_settings.png
│   │   │   │   └── ...
│   │   │   └── Icons/                    ✓ General icons
│   │   │       ├── health_icon.png
│   │   │       ├── ammo_icon.png
│   │   │       └── ...
│   │   │
│   │   ├── 4 Palette/                    ✓ Color reference
│   │   │
│   │   ├── 5 Logo/                       ✓ Game logo
│   │   │
│   │   ├── 6 Buttons/                    ✓ CRITICAL: Interactive buttons
│   │   │   ├── btn_pause.png             ← State: NORMAL
│   │   │   ├── btn_pause_hover.png       ← State: HOVER
│   │   │   ├── btn_pause_pressed.png     ← State: PRESSED
│   │   │   ├── btn_play.png
│   │   │   ├── btn_play_hover.png
│   │   │   ├── btn_play_pressed.png
│   │   │   ├── btn_settings.png
│   │   │   ├── btn_settings_hover.png
│   │   │   ├── btn_settings_pressed.png
│   │   │   ├── btn_help.png
│   │   │   ├── btn_wpn_1.png
│   │   │   ├── btn_wpn_1_hover.png
│   │   │   ├── btn_wpn_1_pressed.png
│   │   │   ├── btn_wpn_2.png
│   │   │   ├── btn_wpn_3.png
│   │   │   └── ... (all button variations)
│   │   │
│   │   ├── 7 Numbers/                    ✓ Digit rendering
│   │   │   ├── digit_0.png
│   │   │   ├── digit_1.png
│   │   │   └── digit_9.png
│   │   │
│   │   ├── 8 Cursors/                    ✓ Mouse cursor styles
│   │   │
│   │   ├── 9 Other/                      ✓ Special elements
│   │   │   ├── 1 Decor/                  ✓ Decorative frames
│   │   │   └── 2 Skill icons/            ✓ Ability icons
│   │   │
│   │   ├── 10 Font/                      ✓ Font assets (if vector-free)
│   │   │   └── images/                   ✓ Pre-rendered text
│   │   │
│   │   └── card-animations/              ✓ Card flip/slide anims
│   │
│   └── characters/, weapons/, vfx/       ← In-game assets
│       └── (managed separately)
```

### Asset Constants in AnimationAndSpriteLoader

We leverage these pre-defined paths:
```java
public static final String GUI_BASE           = "Resources/industrial-zone/gui/";
public static final String GUI_FRAMES         = GUI_BASE + "1 Frames/";
public static final String GUI_BARS           = GUI_BASE + "2 Bars/";
public static final String GUI_ICONS          = GUI_BASE + "3 Icons/";
public static final String GUI_ICONS_BUTTONS  = GUI_BASE + "3 Icons/Buttons2/";
public static final String GUI_BUTTONS        = GUI_BASE + "6 Buttons/";
public static final String GUI_NUMBERS        = GUI_BASE + "7 Numbers/";
public static final String GUI_CURSORS        = GUI_BASE + "8 Cursors/";
public static final String GUI_OTHER          = GUI_BASE + "9 Other/";
public static final String GUI_OTHER_DECOR    = GUI_BASE + "9 Other/1 Decor/";
public static final String GUI_OTHER_SKILLS   = GUI_BASE + "9 Other/2 Skill icons/";
public static final String GUI_CARD_ANIM      = GUI_BASE + "card-animations/";
```

### Button States & Asset Organization

Each interactive button has 3 states with corresponding assets:

```
┌─────────────────────────────┐
│     BUTTON STATE SYSTEM      │
├─────────────────────────────┤
│                             │
│  NORMAL (default)           │
│  └─ btn_pause.png          │
│      (width=64px, height=48)│
│      (shown when not hovered)
│                             │
│  HOVER (mouse over)         │
│  └─ btn_pause_hover.png    │
│      (typically brighter)   │
│      (shown on mouseover)   │
│                             │
│  PRESSED (clicked)          │
│  └─ btn_pause_pressed.png  │
│      (typically darker)     │
│      (shown on mousedown)   │
│                             │
│  DISABLED (optional)        │
│  └─ btn_pause_disabled.png │
│      (grayed out)           │
│      (if not available)     │
│                             │
└─────────────────────────────┘
```

---

## GUI COMPONENT ARCHITECTURE

### Component Inheritance Hierarchy

```
                    AnimationAndSpriteLoader
                    (Base: asset loading)
                            △
                            │ (extends)
                            │
        ┌───────────────────┼───────────────────┐
        │                   │                   │
        ▼                   ▼                   ▼
    TopBarPanel        LeftSidebar        ButtonPanel
    (Health/Ammo)    (Inventory)        (Actions)
        │                   │                   │
        │                   │                   │
        └───────────────────┼───────────────────┘
                            │
                            ▼
                      HUDPanel
                  (Composite Manager)
                      Renders all 3
```

### Core GUI Components

#### 1. TopBarPanel
**Responsibility**: Display player status information

```
┌────────────────────────────────────────────────┐
│  [♥ Health: 100/100] [◆ Ammo: 500] Level: 1   │
│                                                 │
│ Assets:                                         │
│ - Background: GUI_FRAMES + frame_top_bar.png  │
│ - Health Icon: GUI_ICONS_ICONS + health.png   │
│ - Ammo Icon: GUI_ICONS_ICONS + ammo.png       │
│ - Numbers: GUI_NUMBERS + digit_*.png          │
│ - Health Bar: GUI_BARS + health_bar.png       │
│                                                 │
│ Rendering:                                      │
│ 1. Draw background frame                       │
│ 2. Draw icon assets                            │
│ 3. Draw bar (scaled to current health)         │
│ 4. Draw text numbers (using digit images)      │
│ 5. Composite to main canvas                    │
└────────────────────────────────────────────────┘
```

**Implementation Pattern**:
```java
public class TopBarPanel extends AnimationAndSpriteLoader {
    
    // Asset caching
    private BufferedImage bgFrame;
    private BufferedImage healthIcon;
    private BufferedImage ammoIcon;
    private BufferedImage[] digits; // 0-9
    private BufferedImage healthBar;
    
    public TopBarPanel() {
        super();
        loadAssets();
    }
    
    private void loadAssets() {
        // Load from GUI_BASE constants
        bgFrame = loadImage(GUI_BASE + "frame_topbar.png");
        healthIcon = loadImage(GUI_ICONS_ICONS + "health.png");
        ammoIcon = loadImage(GUI_ICONS_ICONS + "ammo.png");
        loadDigits();  // Load 0-9
        healthBar = loadImage(GUI_BARS + "health_bar.png");
    }
    
    public BufferedImage render(PlayerState player, int screenWidth) {
        BufferedImage canvas = new BufferedImage(
            screenWidth, 48, BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = canvas.createGraphics();
        
        // Draw background
        g2d.drawImage(bgFrame, 0, 0, screenWidth, 48, null);
        
        // Draw icons
        g2d.drawImage(healthIcon, 10, 8, null);
        g2d.drawImage(ammoIcon, 150, 8, null);
        
        // Draw health bar (scaled)
        int barWidth = (int)(100 * (player.health / 100.0f));
        g2d.drawImage(healthBar, 50, 15, barWidth, 18, null);
        
        // Draw numeric values (using digit images)
        drawNumber(g2d, player.health, 50, 8);
        
        g2d.dispose();
        return canvas;
    }
}
```

#### 2. LeftSidebar
**Responsibility**: Display inventory and collected items

```
┌─────────────────┐
│  [INVENTORY]    │
├─────────────────┤
│  ┌───┐ ┌───┐   │
│  │ │ │ │ │ │   │  Card 1
│  └───┘ └───┘   │
│  ┌───┐ ┌───┐   │
│  │ │ │ │ │ │   │  Card 2
│  └───┘ └───┘   │
│  ┌───┐ ┌───┐   │
│  │ │ │ │ │ │   │  Card 3 (animated)
│  └───┘ └───┘   │
│  ┌───┐ ┌───┐   │
│  │ │ │ │ │ │   │  Card 4
│  └───┘ └───┘   │
└─────────────────┘

Assets:
- Background: GUI_FRAMES + frame_sidebar_left.png
- Card slots: GUI_CARD_ANIM + card_empty.png
- Collected cards: char_prefab_*.png
```

#### 3. ButtonPanel
**Responsibility**: Display action buttons for weapons and controls

```
┌──────────┐
│ [PAUSE]  │  btn_pause + states
├──────────┤
│ [WPNS]   │  button_weapons
├──────────┤
│ [WPN 1]  │  btn_wpn_1 + hover/press
├──────────┤
│ [WPN 2]  │  btn_wpn_2 + hover/press
├──────────┤
│ [WPN 3]  │  btn_wpn_3 + hover/press
├──────────┤
│[SETTINGS]│  btn_settings + hover/press
├──────────┤
│[HELP]    │  btn_help + hover/press
└──────────┘

Each button:
- Has 3 states (NORMAL, HOVER, PRESSED)
- Responds to mouse input
- Triggers game actions
```

#### 4. HUDPanel (Composite)
**Responsibility**: Manage and composite all GUI elements

```java
public class HUDPanel extends JPanel {
    private TopBarPanel topBar;
    private LeftSidebar sidebar;
    private ButtonPanel buttons;
    private HUDPanel hudBar;
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        // Render all components
        BufferedImage topBarRender = topBar.render();
        BufferedImage sidebarRender = sidebar.render();
        BufferedImage buttonsRender = buttons.render();
        BufferedImage hudRender = hudBar.render();
        
        // Composite onto main canvas
        g2d.drawImage(topBarRender, 0, 0, null);              // Top
        g2d.drawImage(sidebarRender, 0, 48, null);            // Left
        g2d.drawImage(buttonsRender, 
            getWidth() - 80, 48, null);                       // Right
        g2d.drawImage(hudRender, 0, 
            getHeight() - 32, null);                          // Bottom
    }
}
```

---

## RENDERING PIPELINE

### Frame-by-Frame Rendering Process

```
┌────────────────────────────────────────────────────────┐
│           GAME LOOP (60 FPS = ~16.67ms)                │
└────────────────────────────────────────────────────────┘
                            │
                            ↓
        ┌──────────────────────────────────────┐
        │  1. INPUT POLLING (16ms point)        │
        │  - Check mouse position               │
        │  - Check mouse clicks                 │
        │  - Check keyboard input               │
        │  - Update button states (HOVER/PRESS) │
        └──────────────────────────────────────┘
                            │
                            ↓
        ┌──────────────────────────────────────┐
        │  2. GAME STATE UPDATE                 │
        │  - Update physics                     │
        │  - Update player position             │
        │  - Update animation frames            │
        │  - Check collisions                   │
        │  - Update health/ammo                 │
        └──────────────────────────────────────┘
                            │
                            ↓
        ┌──────────────────────────────────────┐
        │  3. GUI STATE SNAPSHOT                │
        │  - Capture current player health      │
        │  - Capture current ammo               │
        │  - Capture button states              │
        │  - Capture item inventory             │
        └──────────────────────────────────────┘
                            │
                            ↓
        ┌──────────────────────────────────────┐
        │  4. RENDER BACKGROUND LAYER           │
        │  - Render level tilemap               │
        │  - Render parallax layers             │
        │  - Render entities (player, enemies)  │
        │  - Render VFX                         │
        │  Result: mainCanvas (BufferedImage)   │
        └──────────────────────────────────────┘
                            │
                            ↓
        ┌──────────────────────────────────────┐
        │  5. RENDER GUI OVERLAY                │
        │  a) TopBarPanel.render()              │
        │     → BufferedImage topBarImage       │
        │  b) LeftSidebar.render()              │
        │     → BufferedImage sidebarImage      │
        │  c) ButtonPanel.render()              │
        │     → BufferedImage buttonImage       │
        │  d) HUDBar.render()                   │
        │     → BufferedImage hudImage          │
        └──────────────────────────────────────┘
                            │
                            ↓
        ┌──────────────────────────────────────┐
        │  6. COMPOSITE GUI ONTO MAIN CANVAS    │
        │  mainCanvas.getGraphics().drawImage() │
        │  - Composite each GUI element         │
        │  - Respecting layering & positioning  │
        │  - Result: finalScreen                │
        └──────────────────────────────────────┘
                            │
                            ↓
        ┌──────────────────────────────────────┐
        │  7. DISPLAY TO SCREEN                 │
        │  - Paint finalScreen to JFrame        │
        │  - Platform native rendering          │
        └──────────────────────────────────────┘
                            │
                            ↓
                    (16.67ms elapsed)
                    Loop repeats
```

### Asset Loading Strategy

**Goal**: Minimize load times, maximize rendering speed

#### Phase 1: Startup Asset Loading
```java
// Called once at game initialization
public void loadAllAssets() {
    // 1. Priority: Essential UI assets (load first)
    loadTopBarAssets();      // ~50ms
    loadButtonAssets();      // ~100ms
    loadSidebarAssets();     // ~50ms
    
    // 2. Secondary: Level-specific assets (async)
    asyncLoadLevelAssets();  // Background in thread
    
    // 3. Deferred: Animations & VFX (on-demand)
    createAssetLoadingQueue();
}
```

#### Phase 2: Runtime Caching
```java
// Keep loaded assets in memory during gameplay
private Map<String, BufferedImage> assetCache = 
    new HashMap<>();

private BufferedImage getOrLoadAsset(String assetPath) {
    if (assetCache.containsKey(assetPath)) {
        return assetCache.get(assetPath);  // Cache hit
    }
    
    BufferedImage asset = loadImageFromFile(assetPath);
    assetCache.put(assetPath, asset);      // Cache miss
    return asset;
}
```

#### Phase 3: Memory Management
```java
// Clean up assets when switching levels
public void unloadAssets(String levelName) {
    assetCache.values().stream()
        .forEach(BufferedImage::flush);
    assetCache.clear();
}
```

---

## STATE-SPECIFIC GUI LAYOUTS

### State 1: MAIN_MENU

```
┌────────────────────────────────────────┐
│                                        │
│          INDUSTRIAL ZONE               │
│         PLATFORMER GAME                │
│                                        │
│  [NEW GAME]                            │
│  [CONTINUE]                            │
│  [SETTINGS]                            │
│  [QUIT]                                │
│                                        │
│        © 2026 Game Studio              │
│                                        │
└────────────────────────────────────────┘

Assets:
- Background: GUI_BASE + bg_mainmenu.png
- Logo: GUI_LOGO + logo_large.png
- Buttons: GUI_BUTTONS + btn_*.png
- Button States: hover, pressed

Transitions:
- NEW GAME → CHARACTER_SELECT
- CONTINUE → LEVEL_SELECT (with save data)
- SETTINGS → SETTINGS screen
- QUIT → Close application
```

### State 2: CHARACTER_SELECT

```
┌────────────────────────────────────────┐
│       SELECT YOUR CHARACTER            │
├────────────────────────────────────────┤
│                                        │
│  [CHAR 1]    [CHAR 2]    [CHAR 3]     │
│   Portrait    Portrait    Portrait     │
│   Stats       Stats       Stats        │
│                                        │
│  Name: BIKER              HP: 120      │
│  Damage: 18  Speed: 16  Armor: 8       │
│                                        │
│  [← BACK]  [SELECT →]                 │
│                                        │
└────────────────────────────────────────┘

Assets:
- Character cards: portraits from Resources
- Background: GUI_FRAMES + frame_select.png
- Button backgrounds: GUI_BUTTONS + btn_*.png
- Stat icons: GUI_ICONS_ICONS + icon_*.png

Logic:
- Load character data
- Display stats dynamically
- Animate selection highlighting
```

### State 3: GAME_ACTIVE (Primary)

```
┌────────────────────────────────────────────┐
│  [♥ 100/100] [◆ 500] LEVEL: 1              │  TopBar
├────────────────────────────────────────────┤
│                                        [P]  │
│     LEVEL GAMEPLAY CANVAS              [W] │
│     (Parallax, entities, VFX)         [1]  │
│                                        [2]  │  Button
│                                        [3]  │  Panel
│                                        [S]  │  (Right)
│                                        [?]  │
│                                            │
│ [Inventory cards]                          │  Sidebar
│ ┌──┐ ┌──┐                                  │  (Left)
│ │  │ │  │                                  │
│ └──┘ └──┘                                  │
│ ┌──┐ ┌──┐                                  │
│ │  │ │  │                                  │
│ └──┘ └──┘                                  │
│                                            │
├────────────────────────────────────────────┤
│  ZONE: Industrial Start   TIME: 2:45       │  HUD Bar
└────────────────────────────────────────────┘

Components & Assets:
- TopBar: Health bar, ammo counter
- Sidebar: Inventory (4 card slots)
- ButtonPanel: Pause, Weapons, Settings, Help
- HUD Bar: Zone name, time/score
- Main: Full level rendering

Interactions:
- Click buttons → Button actions
- Hover buttons → Visual feedback
- Press P → Pause menu
- Collect items → Sidebar updates
```

### State 4: GAME_PAUSED

```
┌────────────────────────────────────┐
│            PAUSED                  │
│                                    │
│  [RESUME]                          │
│  [SETTINGS]                        │
│  [QUIT TO MENU]                    │
│                                    │
│  (Background dimmed/blurred)       │
│                                    │
└────────────────────────────────────┘

Assets:
- Background overlay: Semi-transparent
- Buttons: Large, centered
- Font: GUI_FONT + text assets

Logic:
- Dim background
- Show pause menu
- Stop game time
- Handle input (Resume/Quit)
```

### State 5: GAME_OVER

```
┌────────────────────────────────────┐
│         GAME OVER                  │
│                                    │
│  Final Score: 12,500 points        │
│  Zone Reached: Underground (2)     │
│  Time Survived: 4:32               │
│  Enemies Defeated: 23              │
│                                    │
│  [RETRY LEVEL]                     │
│  [RETURN TO MENU]                  │
│                                    │
└────────────────────────────────────┘

Assets:
- Background: GUI_BASE + bg_gameover.png
- Stats display: Text rendered from digit images
- Buttons: Large, centered

Data Display:
- Score calculation (UI-only, no logic)
- Zone reached (from game state)
- Time survived (from game state)
- Enemies defeated (from game state)
```

### State 6: LEVEL_COMPLETE

```
┌────────────────────────────────────┐
│       LEVEL COMPLETE!              │
│                                    │
│  Level: Industrial Zone            │
│  Difficulty: Normal                │
│  Time: 8:34                        │
│  Score: 25,000                     │
│                                    │
│  ◆ BONUS: Speed Clear +2000        │
│  ◆ BONUS: No Damage +5000          │
│                                    │
│  Total: 32,000 points              │
│                                    │
│  [NEXT LEVEL]                      │
│  [LEVEL SELECT]                    │
│                                    │
└────────────────────────────────────┘

Assets:
- Background: GUI_BASE + bg_complete.png
- Bonus icons: GUI_ICONS_ICONS + bonus_*.png
- Buttons: Large, centered

Logic:
- Calculate bonuses
- Update progression data
- Animate score counter
```

---

## BUTTON SYSTEM ARCHITECTURE

### Button Component Detailed Specification

```
┌─────────────────────────────────────────────────────┐
│          INTERACTIVE BUTTON COMPONENT               │
├─────────────────────────────────────────────────────┤
│                                                     │
│  Properties:                                        │
│  - Position (x, y)                                 │
│  - Dimensions (width, height)                      │
│  - Button ID (unique identifier)                   │
│  - Label (action name)                             │
│  - Current state (NORMAL/HOVER/PRESSED/DISABLED)   │
│  - Associated action (callback)                    │
│                                                     │
│  Assets:                                            │
│  - normalImage: btn_*.png                          │
│  - hoverImage: btn_*_hover.png                     │
│  - pressedImage: btn_*_pressed.png                 │
│  - disabledImage: btn_*_disabled.png (optional)    │
│                                                     │
│  Methods:                                           │
│  - isMouseOver(mouseX, mouseY): boolean            │
│  - handleMouseEnter(): void (trigger hover)        │
│  - handleMouseLeave(): void (back to normal)       │
│  - handleMousePress(): void (trigger pressed)      │
│  - handleMouseRelease(): void (trigger action)     │
│  - render(): BufferedImage                         │
│                                                     │
└─────────────────────────────────────────────────────┘
```

### Button State Machine

```
             START
              │
              ↓
        ┌──────────┐
        │  NORMAL  │◄──┐
        │(default) │   │
        └────┬─────┘   │
             │         │
   (mouse in)│         │(mouse out)
             ↓         │
        ┌──────────┐   │
        │  HOVER   ├───┘
        │(brighter)│
        └────┬─────┘
             │
   (clicked) │
             ↓
        ┌──────────┐
        │ PRESSED  │
        │(darker)  │
        └────┬─────┘
             │
  (released) │
             ↓
         Action triggered
             │
             └──→ Return to NORMAL
```

### Button Rendering Logic

```java
public class InteractiveButton extends AnimationAndSpriteLoader {
    
    private String buttonId;
    private int x, y;
    private BufferedImage normalImage;
    private BufferedImage hoverImage;
    private BufferedImage pressedImage;
    private ButtonState currentState;
    private Runnable action;
    
    public enum ButtonState {
        NORMAL, HOVER, PRESSED, DISABLED
    }
    
    public InteractiveButton(String id, String buttonName, 
        int x, int y, Runnable action) {
        this.buttonId = id;
        this.x = x;
        this.y = y;
        this.action = action;
        this.currentState = ButtonState.NORMAL;
        
        // Load all state images
        loadButtonAssets(buttonName);
    }
    
    private void loadButtonAssets(String buttonName) {
        String basePath = AnimationAndSpriteLoader.GUI_BUTTONS;
        
        normalImage = loadImage(basePath + "btn_" + buttonName + ".png");
        hoverImage = loadImage(basePath + "btn_" + buttonName + "_hover.png");
        pressedImage = loadImage(basePath + "btn_" + buttonName + "_pressed.png");
    }
    
    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + normalImage.getWidth() 
            && mouseY >= y && mouseY <= y + normalImage.getHeight();
    }
    
    public void handleMouseMove(int mouseX, int mouseY) {
        if (isMouseOver(mouseX, mouseY) && currentState != ButtonState.PRESSED) {
            currentState = ButtonState.HOVER;
        } else if (!isMouseOver(mouseX, mouseY) && currentState == ButtonState.HOVER) {
            currentState = ButtonState.NORMAL;
        }
    }
    
    public void handleMousePress(int mouseX, int mouseY) {
        if (isMouseOver(mouseX, mouseY)) {
            currentState = ButtonState.PRESSED;
        }
    }
    
    public void handleMouseRelease(int mouseX, int mouseY) {
        if (currentState == ButtonState.PRESSED) {
            currentState = ButtonState.NORMAL;
            if (isMouseOver(mouseX, mouseY)) {
                action.run();  // Execute button action
            }
        }
    }
    
    public BufferedImage render() {
        return switch(currentState) {
            case NORMAL -> normalImage;
            case HOVER -> hoverImage;
            case PRESSED -> pressedImage;
            case DISABLED -> disabledImage != null ? disabledImage : normalImage;
        };
    }
}
```

### Button Panel Implementation

```java
public class ButtonPanel extends AnimationAndSpriteLoader {
    
    private List<InteractiveButton> buttons;
    private int panelX, panelY;
    
    public ButtonPanel() {
        super();
        buttons = new ArrayList<>();
        initializeButtons();
    }
    
    private void initializeButtons() {
        // Pause Button
        buttons.add(new InteractiveButton(
            "pause", "pause", 
            panelX, panelY, 
            () -> Game.setPaused(true)
        ));
        
        // Weapon 1 Button
        buttons.add(new InteractiveButton(
            "wpn1", "wpn_1", 
            panelX, panelY + 50, 
            () -> Player.selectWeapon(1)
        ));
        
        // Weapon 2 Button
        buttons.add(new InteractiveButton(
            "wpn2", "wpn_2", 
            panelX, panelY + 100, 
            () -> Player.selectWeapon(2)
        ));
        
        // Weapon 3 Button
        // ... etc
        
        // Settings Button
        buttons.add(new InteractiveButton(
            "settings", "settings", 
            panelX, panelY + 200, 
            () -> Game.showSettings()
        ));
        
        // Help Button
        buttons.add(new InteractiveButton(
            "help", "help", 
            panelX, panelY + 250, 
            () -> Game.showHelp()
        ));
    }
    
    public void handleMouseMove(int mouseX, int mouseY) {
        buttons.forEach(btn -> btn.handleMouseMove(mouseX, mouseY));
    }
    
    public void handleMousePress(int mouseX, int mouseY) {
        buttons.forEach(btn -> btn.handleMousePress(mouseX, mouseY));
    }
    
    public void handleMouseRelease(int mouseX, int mouseY) {
        buttons.forEach(btn -> btn.handleMouseRelease(mouseX, mouseY));
    }
    
    public BufferedImage render() {
        BufferedImage panelImage = new BufferedImage(
            80, getHeight(), BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = panelImage.createGraphics();
        
        // Draw background frame
        g2d.drawImage(bgFrame, 0, 0, null);
        
        // Render each button
        for (InteractiveButton button : buttons) {
            BufferedImage buttonImg = button.render();
            // Position button on panel
            g2d.drawImage(buttonImg, button.x, button.y, null);
        }
        
        g2d.dispose();
        return panelImage;
    }
}
```

---

## INPUT HANDLING SYSTEM

### Input Event Flow

```
┌──────────────────────────────┐
│   JFrame receives mouse event │
└──────┬───────────────────────┘
       │
       ↓ (if GAME_ACTIVE state)
┌──────────────────────────────┐
│  MouseInputHandler            │
│  - Updates mouse position     │
│  - Polls mouse buttons        │
│  - Queries keyboard           │
└──────┬───────────────────────┘
       │
       ├─→ PlayerController
       │   (movement input)
       │
       ├─→ ButtonPanel
       │   (button clicks)
       │
       └─→ Game
           (action dispatch)
```

### Mouse Input Handler Implementation

```java
public class MouseInputHandler extends MouseAdapter 
    implements MouseMotionListener, MouseWheelListener {
    
    private ButtonPanel buttonPanel;
    private int lastMouseX, lastMouseY;
    private int mouseButtonState = 0;  // Bit flags
    
    public static final int LEFT_BUTTON = 1;
    public static final int MIDDLE_BUTTON = 2;
    public static final int RIGHT_BUTTON = 4;
    
    @Override
    public void mouseMoved(MouseEvent e) {
        lastMouseX = e.getX();
        lastMouseY = e.getY();
        
        // Update button states
        buttonPanel.handleMouseMove(lastMouseX, lastMouseY);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        mouseButtonState |= (1 << (button - 1));
        
        if (button == MouseEvent.BUTTON1) {
            buttonPanel.handleMousePress(lastMouseX, lastMouseY);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        mouseButtonState &= ~(1 << (button - 1));
        
        if (button == MouseEvent.BUTTON1) {
            buttonPanel.handleMouseRelease(lastMouseX, lastMouseY);
        }
    }
    
    public boolean isLeftButtonPressed() {
        return (mouseButtonState & LEFT_BUTTON) != 0;
    }
    
    public int getMouseX() { return lastMouseX; }
    public int getMouseY() { return lastMouseY; }
}
```

---

## ANIMATION & VISUAL EFFECTS

### Animation System for GUI Elements

#### 1. Button Hover Animation
```
State: NORMAL          → State: HOVER
Image: btn_pause.png   → Image: btn_pause_hover.png
(Transition: instant or 100ms linear)
```

#### 2. Card Flip Animation (Inventory)
```
Frame 0: Card front (512x256px)
Frame 1: Rotating 45°  (450x256px, rotated)
Frame 2: Side view     (100x256px)
Frame 3: Back view     (100x256px)
Frame 4: Rotating back (450x256px)
Frame 5: Card front    (512x256px)

Timing: 30ms per frame = 180ms total flip
Asset: card-animations/*.png
```

#### 3. Health Bar Depletion Animation
```
Current: 75 HP
Target: 50 HP

Animation:
- Frame 0-30: Smoothly scale bar from 75% to 50%
- Color shift: Green → Yellow (HP < 50%)
- Duration: 300ms

Implementation:
```java
private float animationProgress = 0;
private float targetHealth;

public void takeDamage(int damage) {
    targetHealth = Math.max(0, currentHealth - damage);
    animationProgress = 0;  // Start animation
}

public void updateAnimation(float deltaTime) {
    if (animationProgress < 1.0f) {
        animationProgress += deltaTime / 300f;  // 300ms duration
        currentHealth = Mathf.lerp(currentHealth, targetHealth, animationProgress);
    }
}
```

#### 4. Ammo Counter Update
```
Previous: 450 ammo
Current: 435 ammo

Animation (optional):
- Flash white for 200ms
- Fade back to normal color

Implementation:
```java
private float ammoFlashTimer = 0;

public void consumeAmmo(int count) {
    ammoCount -= count;
    ammoFlashTimer = 200;  // Flash for 200ms
}

public void render() {
    // Color based on flash timer
    Color ammoColor = ammoFlashTimer > 0 ? Color.WHITE : Color.YELLOW;
    // ... render with ammoColor
    
    ammoFlashTimer -= deltaTime;
}
```

---

## IMPLEMENTATION PHASES

### Phase 1: Foundation & Infrastructure
**Objective**: Set up base classes and asset loading system
**Duration**: ~2-3 hours
**Tasks**:
1. ✅ Create AnimationAndSpriteLoader base extension
2. ✅ Verify asset paths from Resources
3. ✅ Create asset cache/loader utility
4. ✅ Test image loading pipeline

**Deliverables**:
- `GUIAssetManager.java` - Centralized asset loading
- Confirmed asset paths working
- Performance metrics (load times)

### Phase 2: Core GUI Components
**Objective**: Implement individual GUI panels
**Duration**: ~3-4 hours
**Tasks**:
1. Implement TopBarPanel
   - Load health/ammo icons
   - Render bar scaling
   - Draw numeric display (digit images)

2. Implement LeftSidebar
   - Card slot rendering
   - Inventory update logic
   - Animation preparation

3. Implement ButtonPanel
   - Button grid layout
   - State tracking (NORMAL/HOVER/PRESSED)
   - Action callbacks

4. Implement HUDBar
   - Zone name display
   - Time/score display
   - Status indicators

**Deliverables**:
- All 4 panels independently renderable
- Asset-based rendering (no vectors)
- Responsive to game state changes

### Phase 3: Input System
**Objective**: Wire up mouse/keyboard input
**Duration**: ~2 hours
**Tasks**:
1. Extend MouseInputHandler
2. Implement button click detection
3. Wire button actions to Game
4. Test input responsiveness

**Deliverables**:
- Buttons respond to clicks
- Visual feedback (hover/press states)
- Clean input event flow

### Phase 4: Screen System
**Objective**: Implement menu screens and overlays
**Duration**: ~4 hours
**Tasks**:
1. MainMenuScreen (title, buttons)
2. LevelSelectScreen (level thumbnails)
3. CharacterSelectScreen (character cards)
4. PauseMenuScreen (pause overlay)
5. GameOverScreen (end screen)
6. LevelCompleteScreen (completion screen)

**Deliverables**:
- All screens independent and testable
- Proper state transitions
- Button navigation working

### Phase 5: Animations & Polish
**Objective**: Add animations and visual effects
**Duration**: ~3 hours
**Tasks**:
1. Implement card flip animation
2. Button hover animations
3. Health bar smooth transitions
4. Score counter animation
5. Screen transition effects

**Deliverables**:
- Smooth animations at 60 FPS
- Visual feedback for all interactions
- Professional appearance

### Phase 6: Integration & Testing
**Objective**: Full system integration and testing
**Duration**: ~2-3 hours
**Tasks**:
1. Integrate all screens with Game class
2. Test state machine transitions
3. Test all button actions
4. Performance profiling
5. Asset loading optimization
6. Memory cleanup

**Deliverables**:
- Complete working GUI system
- All states reachable and functional
- Performance acceptable (60 FPS)
- Clean shutdown with resource cleanup

---

## SUMMARY

This plan establishes a **pure asset-based GUI system** that:

✅ **Uses ONLY image assets** from Resources folder  
✅ **Eliminates vector graphics** completely  
✅ **Leverages AnimationAndSpriteLoader** for shared utilities  
✅ **Handles all game states** with proper transitions  
✅ **Supports interactive buttons** with state machines  
✅ **Performs smoothly** at 60 FPS with caching  
✅ **Maintains clean architecture** for extensibility  

### Next Steps
1. Start Phase 1: Foundation setup
2. Create GUIAssetManager with verified asset loading
3. Implement individual GUI components
4. Test each component independently
5. Integrate into Game class
