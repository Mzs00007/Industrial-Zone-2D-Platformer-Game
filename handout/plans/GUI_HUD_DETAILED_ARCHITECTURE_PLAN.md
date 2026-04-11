# COMPREHENSIVE GUI/HUD ARCHITECTURE PLAN
## CSCU9N6 Industrial Zone Platformer - Complete System Design

**Document Version:** 2.0  
**Date:** April 2, 2026  
**Status:** Detailed Architecture & Planning  
**Graphics Format:** RASTER GRAPHICS ONLY (PNG/JPEG Images, NO Vector Graphics)

---

## TABLE OF CONTENTS
1. [System Overview & Philosophy](#system-overview--philosophy)
2. [Architectural Hierarchy](#architectural-hierarchy)
3. [Asset Inventory & Organization](#asset-inventory--organization)
4. [GUI State Machine](#gui-state-machine)
5. [HUD Layout & Components](#hud-layout--components)
6. [Modal & Overlay System](#modal--overlay-system)
7. [Screen Flow Diagram](#screen-flow-diagram)
8. [UI Element Specifications](#ui-element-specifications)
9. [Implementation Phases](#implementation-phases)
10. [Class Architecture Diagram](#class-architecture-diagram)

---

## SYSTEM OVERVIEW & PHILOSOPHY

### Core Principles
1. **Raster-Only Graphics**: ALL visual content rendered via PNG/JPEG images using `g.drawImage()`
2. **Hierarchical Inheritance**: 
   - `GameCore` (JFrame handler)
   - `AnimationAndSpriteLoader` (Asset & animation infrastructure)
   - `GUIComponent` (Abstract GUI base)
   - Concrete panels (TopBar, HUD, Sidebar, etc.)
3. **Singleton Asset Manager**: Centralized asset loading with caching
4. **State-Driven Rendering**: GameState object drives all visual updates
5. **Modal Layering**: Overlays manage pause screens, menus, notifications

### Visual Goals
- Professional sci-fi/industrial aesthetic
- Real-time performance (60 FPS target)
- Responsive to player actions
- Clear information hierarchy
- Immersive UI (doesn't distract from gameplay)

---

## ARCHITECTURAL HIERARCHY

```
═════════════════════════════════════════════════════════════════════
                    INHERITANCE HIERARCHY
═════════════════════════════════════════════════════════════════════

                        GameCore
                    (JFrame, KeyListener)
                            ▲
                            │
                            │ extends
                            │
                          Game
                   (Main game controller)
                            │
                ┌───────────┼───────────┐
                │           │           │
         Level1.java   Level2.java   GUI Components
                │           │           │
                └───────────┼───────────┘
                            │
                   AnimationAndSpriteLoader
                   (Asset loading system)
                            ▲
          ┌─────────────────┼─────────────────┐
          │                 │                 │
          │           GUIComponent            │
          │         (Abstract base)           │
          │                 ▲                 │
          │    ┌────────────┼────────────┐    │
          │    │            │            │    │
      TopBar  HUD      LeftSidebar   ButtonPanel
      Panel   Panel      (Sidebar)     (Buttons)
          │    │            │            │
          └────┴────────────┴────────────┘
             │
         GameState
      (Data holder)


INHERITANCE LEVELS:
═════════════════════════════════════════════════════════════════════
Level 0: GameCore
         - JFrame management
         - Window creation
         - Listener registration

Level 1: Game (extends GameCore)
         - Main game controller
         - Game loop dispatcher
         - Level management

Level 2: AnimationAndSpriteLoader (used by Game)
         - Asset loading infrastructure
         - Sprite sheet parsing
         - Animation frame management
         - Physics system
         - State transitions
         - Input handling

Level 3: GUIComponent ← Level1, Level2, GUI classes
         - Abstract base for all UI elements
         - Common rendering methods
         - Asset loading interface
         - Update/render cycle

Level 4: Concrete GUI Components
         - TopBarPanel (extends GUIComponent → AnimationAndSpriteLoader)
         - HUDPanel (extends GUIComponent → AnimationAndSpriteLoader)
         - LeftSidebar (extends GUIComponent → AnimationAndSpriteLoader)
         - ButtonPanel (extends GUIComponent → AnimationAndSpriteLoader)
         - ModalOverlay (extends GUIComponent → AnimationAndSpriteLoader)

═════════════════════════════════════════════════════════════════════
```

---

## ASSET INVENTORY & ORGANIZATION

### Folder Structure
```
Resources/industrial-zone/gui/
├── 1 Frames/          (82 frame/panel pieces)
│   ├── Corners (8)    - Top-left, top-right, bottom-left, bottom-right variants
│   ├── Edges (24)     - Top, bottom, left, right with various patterns
│   ├── Fills (18)     - Solid colors, patterns, textures
│   ├── Dividers (16)  - Horizontal/vertical separator lines
│   ├── Decorative (8) - Rivets, accents, embellishments
│   └── Variants (8)   - Themed variations
│
├── 2 Bars/            (20 bar assets)
│   ├── Health Bar (6) - 0%, 20%, 40%, 60%, 80%, 100% fill levels
│   ├── Energy Bar (6) - Blue-tinted variants
│   ├── Armor Bar (6)  - Gold/tan-tinted variants
│   └── Scroll (2)     - Trackbar pieces
│
├── 3 Icons/           (40 icon assets)
│   ├── Status (8)     - Poison, bleeding, frozen, burning, etc.
│   ├── Actions (8)    - Attack, defend, heal, interact, etc.
│   ├── Navigation (8) - Up, down, left, right, enter, cancel
│   ├── Items (8)      - Weapon, armor, potion, key, etc.
│   └── System (8)     - Save, load, settings, quit, etc.
│
├── 4 Palette/         (36 color variations)
│   ├── Blue (6)       - Health/Energy palette
│   ├── Red (6)        - Damage/Critical palette
│   ├── Green (6)      - Healing/Safe palette
│   ├── Yellow (6)     - Warning/Caution palette
│   ├── Purple (6)     - Rare/Magic palette
│   └── Gray (4)       - Disabled/Neutral palette
│
├── 5 Logo/            (3 logo variants)
│   ├── Full_Logo.png
│   ├── Icon_Logo.png
│   └── Minimal_Logo.png
│
├── 6 Buttons/         (30 button states)
│   ├── Normal (6)     - 6 color variants
│   ├── Hover (6)      - Highlighted versions
│   ├── Pressed (6)    - Depressed state
│   ├── Disabled (6)   - Grayed out
│   ├── Toggle_On (2)  - Active toggle
│   └── Toggle_Off (2) - Inactive toggle
│
├── 7 Numbers/         (17 digit glyphs)
│   ├── 0-9.png        (10 files)
│   ├── Slash.png
│   ├── Colon.png
│   ├── Dot.png
│   ├── Plus.png
│   └── Minus.png
│
├── 8 Cursors/         (4 cursor variants)
│   ├── Default.png
│   ├── Pointer.png
│   ├── Wait.png
│   └── Invalid.png
│
├── 9 Other/           (20+ misc assets)
│   ├── 1 Skill icons/ (12 skill icons)
│   ├── 2 Decoration/  (8 decorative elements)
│   └── 3 Fonts/       (Custom font assets)
│
└── card-animations/   (12 character animation cards)
    ├── Character_Idle_Breathe_4F.png
    ├── Character_Walk_6F.png
    ├── Character_Run_8F.png
    ├── Character_Jump_4F.png
    ├── Character_Fall_2F.png
    ├── Character_Attack_6F.png
    └── ... (6 more animations)
```

### Asset Usage Matrix

| Component | Frames | Icons | Bars | Buttons | Numbers | Colors |
|-----------|--------|-------|------|---------|---------|--------|
| **TopBar** | 8 (top edges) | 2 (level/time) | — | — | 6 | 1-2 |
| **HUD Panel** | 12 (dividers) | 8 (stat icons) | 3 (H/E/A) | — | 12 | 1-3 |
| **LeftSidebar** | 16 (frame) | 20 (skills) | — | — | — | 2-3 |
| **ButtonPanel** | — | — | — | 20 (states) | — | 5 |
| **Modal Overlay** | 4 (corners) | 4 (close btn) | — | 6 (actions) | 4 | 1 |
| **Notification** | 4 (corners) | 4 (types) | — | 2 (dismiss) | — | 1 |

---

## GUI STATE MACHINE

### High-Level State Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                      GAME INITIALIZATION                        │
│                      (Loading Screen)                           │
└──────────────────────────┬──────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────────┐
│                    GAMEPLAY PRIMARY STATE                       │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  • Top Bar: Level, Progress, Timer                      │  │
│  │  • HUD Panel: Health, Energy, Armor, Ammo               │  │
│  │  • Left Sidebar: Inventory, Skills, Map                 │  │
│  │  • Right Buttons: Weapons, Pause                        │  │
│  │  • Game World: Player, Enemies, Level Tiles             │  │
│  └──────────────────────────────────────────────────────────┘  │
└────┬──────────────────────────────────────────────────────────┬─┘
     │                                                          │
     ▼ (Pause Button Clicked)                   (Player Dies)  ▼
┌──────────────────────────┐                      ┌─────────────────────┐
│   PAUSE OVERLAY STATE    │                      │  DEATH SCREEN STATE │
│                          │◄──────(Resume)───────┤                     │
│ • Semi-transparent black │                      │ • Fade to black     │
│ • "GAME PAUSED" text     │                      │ • Big "DEAD" text   │
│ • Resume button          │                      │ • Respawn options   │
│ • Settings button        │                      │ • Load game button  │
│ • Main Menu button       │                      │ • Quit button       │
│ • Quit button            │                      │                     │
└──────────────────────────┘                      └─────────────────────┘
     ▲   │                                             │
     │   └──────────(Settings/Menu)────────────────────┘
     │                                  │
     └──────────────(Selections)────────┘
     
     
┌─────────────────────────────────┐
│   SETTINGS/MENU STATE           │
│                                 │
│ • Modal overlay               │
│ • Audio controls              │
│ • Graphics settings           │
│ • Keybind mapping             │
│ • Back button                 │
└─────────────────────────────────┘
     │
     └──────────(Back)──────────┘
```

### Detailed State Definitions

#### STATE 1: GAMEPLAY_ACTIVE
**When:** Game is running normally  
**Components Visible:**
- TopBarPanel (Level 1, Progress, Timer)
- HUDPanel (Health, Energy, Armor, Ammo)
- LeftSidebar (Inventory, Skills, Map)
- ButtonPanel (Weapon/Pause buttons)

**Interactions:**
- Mouse over buttons → Hover state (button brightens)
- Click pause button → Transition to PAUSE_OVERLAY
- Click sidebar tabs → Sidebar content changes
- Click skill button → Skill executes (if not on cooldown)
- Press ESC key → Pause game

#### STATE 2: PAUSE_OVERLAY
**When:** Game paused via button or ESC  
**Components Visible:**
- All previous (dimmed, not interactive)
- OVERLAY: Semi-transparent black (128 alpha)
- Text: "GAME PAUSED" (centered, large white)
- Buttons overlay: Resume, Settings, Main Menu, Quit

**Interactions:**
- Click Resume → Return to GAMEPLAY_ACTIVE
- Click Settings → Transition to SETTINGS_MODAL
- Click Main Menu → Go to MENU_STATE
- Click Quit → Exit game

#### STATE 3: SETTINGS_MODAL
**When:** Settings opened from Pause  
**Components Visible:**
- Overlay (semi-transparent)
- Modal window with:
  - Audio volume slider
  - Graphics quality dropdown
  - Keybind display
  - Back button

**Interactions:**
- Adjust sliders → Settings update
- Click Back → Return to previous state

#### STATE 4: DEATH_SCREEN
**When:** Player health reaches 0  
**Transition Trigger:** `gameState.health <= 0`  
**Components Visible:**
- Black fade overlay
- Centered: Large red "YOU DIED" text
- Options: Respawn, Load Last Checkpoint, Load Game, Quit

**Interactions:**
- Respawn → Reset at last checkpoint, return to GAMEPLAY_ACTIVE
- Load Checkpoint → Load save file, return to GAMEPLAY_ACTIVE
- Quit → Exit game

#### STATE 5: NOTIFICATION_STATE
**When:** Damage received, item picked up, achievement unlocked  
**Triggers:**
- Damage taken → Red floating number + health bar flash
- Item collected → Blue notification popup
- Level complete → Gold notification popup
- Achievement → Purple star popup

**Components:**
- Small modal window
- Icon (type-specific)
- Text message
- Auto-dismisses after 3 seconds OR click to dismiss

#### STATE 6: DIALOGUE_STATE
**When:** NPC interaction or storyline sequence  
**Components Visible:**
- Dialogue box (bottom of screen or center)
- NPC portrait (left side)
- Dialogue text (center)
- Continue/Choice buttons (bottom)

---

## HUD LAYOUT & COMPONENTS

### Screen Coordinate System

```
(0,0) ─────────────────────────────────────────────────────── (screenWidth, 0)
  │
  │  TOPBAR PANEL (50px height)
  │  ┌───────────────────────────────────────────────────────┐
  │  │ Level 1 │ Progress ▓▓▓▓▓░░░ │ Timer: 04:32 │ Score: 45780 │
  │  └───────────────────────────────────────────────────────┘
  │
  │                   GAME WORLD AREA
  │  ┌──────────────────────────────────────────────────────┐
  │  │                                                      │
  │  │ LEFT SIDEBAR (200px width)        [GAME SCENE]      │
  │  │ ┌────────────┐                                     │
  │  │ │[INV][CHAR] │                                     │
  │  │ │[SKILL][MAP]│                                     │
  │  │ └────────────┘                    RIGHT BUTTONS    │
  │  │                                   ┌─────────┐      │
  │  │                                   │ PAUSE   │      │
  │  │                                   ├─────────┤      │
  │  │                                   │SETTINGS │      │
  │  │                                   ├─────────┤      │
  │  │                                   │ HELP    │      │
  │  │                                   ├─────────┤      │
  │  │                                   │WPN 1    │      │
  │  │                                   │WPN 2    │      │
  │  │                                   │WPN 3    │      │
  │  │                                   └─────────┘      │
  │  │                                                      │
  │  └──────────────────────────────────────────────────────┘
  │
  │  HUD PANEL (100px height)
  │  ┌───────────────────────────────────────────────────────┐
  │  │ ❤ Health           ⚡ Energy          🛡 Armor        │
  │  │ ████████░░  85/100 █████░░░░ 62/100 ███████░░░ 75/100│
  │  │                                                       │
  │  │ Ammo: 3/36  │ Cooldowns: Primary 0.5s │ Sec: Ready  │
  │  └───────────────────────────────────────────────────────┘
(0, screenHeight) ─── (screenWidth, screenHeight)
```

### TopBar Panel Details

**Height:** 50px  
**Background:** Navy blue gradient frame top edge  
**Layout:**

```
[Logo] [Level Info] [────Progress Bar────] [Timer] [Score]
 10px    30px-100px   100px-600px         600px-700px 750px
```

**Components:**
1. **Logo Icon** (20x20px at 5,5)
   - Asset: `Logo/Icon_Logo.png`
   - Static, never changes

2. **Level Label** (15px Arial font)
   - Text: "LEVEL 1" or "LEVEL 2"
   - Updated by GameState.currentLevel

3. **Progress Bar** (400px wide)
   - Background: dark gray frame divider
   - Foreground: green fill at currentStage/totalStages ratio
   - Shows which zone player is in with numbers

4. **Timer Display** (80px wide)
   - Asset: Digit images
   - Format: "MM:SS" (5:32)
   - Updates each frame from GameState.timeRemaining

5. **Score Display** (100px wide)
   - Text: "Score: 45,780"
   - Digit glyphs for numbers
   - Updates on item pickup/enemy defeat

---

### HUD Panel Details

**Height:** 100px  
**Background:** Dark gradient frame dividers  
**Layout:**

```
[Health Bar] [Energy Bar] [Armor Bar]
   0-200px      220-420px   440-640px

[Ammo Counter] [Cooldown Timers]
   680-800px      850px+
```

**Detailed Component Positions:**

#### Health Bar Section (0-200px)
```
Position: (20, 20) to (180, 60)

  ❤️ HEALTH
  [████████░░░] 85/100
   
  • Bar Asset: 6 PNG variants (0%, 17%, 33%, 50%, 67%, 100%)
  • Background: Dark bar frame
  • Foreground: Red/Orange gradient fill
  • Number display: "85/100" in digit glyphs right of bar
  • Flash on damage: 0.5s white overlay
```

#### Energy Bar Section (220-420px)
```
Position: (240, 20) to (400, 60)

  ⚡ ENERGY
  [█████░░░░░░] 62/100
  
  • Bar Asset: 6 PNG variants (blue tinted)
  • Background: Dark bar frame
  • Foreground: Cyan/Blue gradient
  • Number display: "62/100" to the right
  • Recharges 10 units/sec when not attacking
```

#### Armor Bar Section (440-640px)
```
Position: (460, 20) to (620, 60)

  🛡️ ARMOR
  [███████░░░] 75/100
  
  • Bar Asset: 6 PNG variants (gold/tan tinted)
  • Absorbs 30% of incoming damage
  • Regenerates 5 units per 5 seconds
```

#### Ammo Counter (680-750px)
```
Position: (700, 20) to (800, 80)

  AMMO
  3 / 36
  
  • Large digit glyphs (yellow text)
  • Format: "CURRENT / MAXIMUM"
  • "/" separator glyph
  • Flash white on reload complete
```

#### Cooldown Timers (850px+)
```
Position: (850, 20) to (screenWidth-20, 80)

  PRIMARY: 0.5s
  [■░░░░░░░░░░░░]
  
  SECONDARY: Ready
  [███████████████]
  
  • Primary skill on cooldown shows timer
  • Secondary available shows green "Ready" text
  • Bar fills as cooldown expires
```

#### Status Effects (Bottom Row)
```
Position: (20, 70) to (screenWidth-20, 100)

  [Poison icon] -2 HP/s │ [Frozen icon] Speed: 50% │ [Burning] +3DMG
  
  • Status icons from Icons/Status folder
  • Brief effect description
  • Duration remaining (if applicable)
  • New effects appear on left, oldest squeeze right
```

---

## MODAL & OVERLAY SYSTEM

### Overlay Architecture

```
┌────────────────────────────────────────────────────┐
│                  Game Render Layer 0               │
│              (Gameplay + Game World)               │
│                                                    │
└────────────────────────────────────────────────────┘
                        ▲
                        │ (Always rendered first)
                        │
┌────────────────────────────────────────────────────┐
│              Render Layer 1: GUI Base              │
│    TopBar ◄──────┬──────────┬──────────┬─────► HUD │
│                  │          │          │           │
│       Sidebar ◄──┘    Game  │        Buttons ────►│
│                      World  │                     │
└────────────────────────────────────────────────────┘
                        ▲
                        │ (Rendered if visible)
                        │
┌──────────────────────────────────────────────────┐
│     Render Layer 2: Notifications Layer          │
│                                                  │
│  [Damage Popup: -15]      [Item Acquired]       │
│                                                  │
└──────────────────────────────────────────────────┘
                        ▲
                        │ (Top-most, most interactive)
                        │
┌─────────────────────────────────────────────────┐
│   Render Layer 3: Modal/Overlay Layer           │
│                                                 │
│  ┌───────────────────────────────────────────┐ │
│  │  Modal Window (Pause, Settings, Death)   │ │
│  │  • Semi-transparent background overlay   │ │
│  │  • Centered window with content          │ │
│  │  • Interactive buttons/controls          │ │
│  └───────────────────────────────────────────┘ │
│                                                 │
│  Cursor: Changes based on hover state         │
│  Key Input: Captured by modal                 │
└─────────────────────────────────────────────────┘
```

### Modal Specifications

#### Pause Modal
```
Overlay: Black (255,255,255,128) - Semi-transparent
Window: 400x300px, centered
Corner Assets: 4 frame corner pieces
Edge Assets: Frame edges (scaled to window size)
Fill: Frame fill piece tiled

Content Layout:
┌──────────────────────────┐
│      GAME PAUSED         │  (title, white text, centered)
│                          │
│   ┌──────────────────┐   │
│   │  ► RESUME        │   │
│   ├──────────────────┤   │
│   │    SETTINGS      │   │
│   ├──────────────────┤   │
│   │  MAIN MENU       │   │
│   ├──────────────────┤   │
│   │  QUIT GAME       │   │
│   └──────────────────┘   │
└──────────────────────────┘

Button States:
• Normal: Button asset (frame + fill frame assets)
• Hover: Brightened version (lighter fill)
• Pressed: Darkened version (depressed effect)
```

#### Settings Modal
```
Window: 500x400px, centered
Title: "SETTINGS"

┌─────────────────────────────┐
│        SETTINGS             │
├─────────────────────────────┤
│                             │
│ AUDIO                       │
│ Master: ████░░░░░░░░░░░░░░ │(slider)
│ Music:  ███░░░░░░░░░░░░░░░░│(slider)
│ Effects:████░░░░░░░░░░░░░░ │(slider)
│                             │
│ GRAPHICS                    │
│ Quality: HIGH    ▼          │(dropdown)
│ FOV:     100     ▼          │(dropdown)
│                             │
│ ┌──────────────┐            │
│ │  BACK        │            │
│ └──────────────┘            │
└─────────────────────────────┘
```

#### Death Screen
```
Full screen overlay: Black (0,0,0,200)

Content (centered):

    ╔════════════════════╗
    ║    YOU HAVE DIED   ║  (large red text)
    ╚════════════════════╝
    
    Final Score: 45,780 points
    Enemies Defeated: 23
    Distance Traveled: 2.4 km
    
    ┌──────────────────┐
    │  RESPAWN AT      │
    │  CHECKPOINT      │
    ├──────────────────┤
    │  LOAD LAST SAVE  │
    ├──────────────────┤
    │  QUIT TO MENU    │
    └──────────────────┘
```

#### Notification Popups
```
Type: Damage Taken
┌──────────┐
│  -15 HP  │  (red floating text)
│          │  Floats upward
└──────────┘  Fades in 2s
Y velocity: +20px/frame

Type: Item Collected
┌────────────────────┐
│ [💎] Item Acquired │  (blue text, item icon)
│   Rare Diamond     │
└────────────────────┘
Duration: 3s, then fade

Type: Achievement
┌────────────────────┐
│ ★ ACHIEVEMENT      │  (gold text, star icon)
│   Monster Hunter   │
└────────────────────┘
Duration: 4s with sound effect
```

---

## SCREEN FLOW DIAGRAM

```
╔════════════════════════════════════════════════════════════════════════╗
║                    COMPLETE SCREEN FLOW & STATES                       ║
╚════════════════════════════════════════════════════════════════════════╝


START GAME
   │
   ▼
┌─────────────────────┐
│ LOAD SCREEN        │      Asset: Splashscreen background
│ [Loading...] 45%   │      Progress bar filled from 0-100%
│                     │      Logo centered with fade animation
└─────────────────────┘
   │
   └─► Assets loaded ──┐
                       │
                       ▼
                ┌─────────────────────┐
                │  MAIN MENU SCREEN   │
                │                     │
                │  [NEW GAME]         │
                │  [LOAD GAME]        │
                │  [SETTINGS]         │
                │  [CREDITS]          │
                │  [QUIT]             │
                └─────────────────────┘
                    │ │  │  │  │
          ┌─────────┘ │  │  │  └──────────────┐
          │          │  │  │                  │
          ▼          ▼  ▼  ▼                  ▼
       NEW GAME   LOAD GAME  SETTINGS      CREDITS
          │        │         │              │
          │        │         └──────┐       │
          │        └────────────┐   │       │
          └──────────────┐      ▼   ▼       │
                         ├────────────────┐ │
                         │               │ ▼
                         ▼               │ (Auto-return)
           ┌──────────────────────────┐  │
           │   GAMEPLAY SCREEN        │  │
           │                          │  │
           │  [HUD ACTIVE]            │  │
           │  [Game world running]    │  │
           │  [Sidebar visible]       │  │
           │  [Buttons ready]         │  │
           │                          │  │
           │ ESC or PAUSE button  ──┐ │  │
           │                       │ │  │
           └───────────────────────┼─┘  │
                                   │    │
                   ┌───────────────┴────┴──────┐
                   │                           │
                   ▼                           │
        ┌──────────────────────┐               │
        │  PAUSE OVERLAY       │               │
        │  (Pause state)       │               │
        │                      │   (Resume)---┤
        │  [Resume]            │               │
        │  [Settings] ─────┐   │               │
        │  [Main Menu] ───┐│   │               │
        │  [Quit]──────┐  ││   │               │
        └──────────────┼──┼┼───┘               │
                       │  ││                   │
                  ┌────▼──┼┼──────────────────┘
                  │       ││
                  ▼       ▼▼
            ┌─────────────────────┐
            │  SETTINGS MODAL     │
            │                     │
            │  (Audio controls)   │
            │  (Graphics controls)│
            │  [Back]             │
            └─────────────────────┘
                    │
                    └──────┐
                           │
                           ▼
                  GAMEPLAY SCREEN (Resume)


    ALTERNATE PATHS FROM GAMEPLAY:
    
    Player Health → 0
            │
            ▼
    ┌──────────────────────────┐
    │  DEATH SCREEN            │
    │                          │
    │  YOU HAVE DIED           │
    │  [RESPAWN]               │
    │  [LOAD SAVE]             │
    │  [MAIN MENU]             │
    └──────────────────────────┘
            │  │  │
            │  │  └──────► MAIN MENU
            │  └─────────► LOAD SCREEN → GAMEPLAY
            └────────────► GAMEPLAY (at checkpoint)


    NOTIFICATION SYSTEM (Overlay on existing screen):
    
    ┌──────────────────────────────────────────┐
    │  GAMEPLAY SCREEN                         │
    │  (dimmed behind notifications)           │
    │                                          │
    │  ┌──────────┐  ┌─────────────────────┐  │
    │  │ -15 HP   │  │ Item: Mega-Potion   │  │
    │  │(red)     │  │ Acquired            │  │
    │  │Floats up │  │(blue)3s lifetime    │  │
    │  └──────────┘  └─────────────────────┘  │
    │                                          │
    │                      ┌──────────────────┐│
    │                      │ Achievement:     ││
    │                      │ Monster Hunter   ││
    │                      │ (gold star icon) ││
    │                      └──────────────────┘│
    └──────────────────────────────────────────┘
```

---

## UI ELEMENT SPECIFICATIONS

### Typography (Raster-Based, NO Vector Text)

**All text rendered using PNG digit glyphs**

```
Digit Glyph Assets (7 Numbers folder):
├── 0.png through 9.png (10 files)
├── Colon.png (:)
├── Slash.png (/)
├── Dot.png (.)
├── Plus.png (+)
└── Minus.png (-)

Rendering approach:
1. GameState stores numeric values as integers/floats
2. DigitRenderer converts to string and loads corresponding PNGs
3. drawImage() places each glyph sequentially
4. Spacing: 8px between digits, 4px for punctuation
```

**Text Labels (Limited)**
- Only where NO numbers needed
- Use frame assets + custom shapes
- Labels like "HEALTH", "ENERGY" → Small icon + positioning

---

### Color Palette (Raster Assets, NOT Generated Colors)

All colors come from pre-rendered PNG assets. No `g.setColor()` calls.

```
Primary Colors:
├── Navy Blue (Backgrounds) - Frame/Fill assets
├── Cyan Blue (Energy) - Bar variant assets
├── Deep Red (Health) - Bar variant assets
├── Gold/Tan (Armor) - Bar variant assets
├── Warning Orange (Alerts) - Icon assets
└── Soft Green (Healing) - Icon assets

Implemented via:
├── Frame variants (different base colors)
├── Bar variant PNGs (6 shades each)
├── Palette folder (36 pre-rendered color variations)
└── Icon assets (color-specific)
```

---

### Button Specifications

**Size:** 80x40px standard  
**States:** 4 required PNG variants per button

```
Button Visual States:

Normal State:
┌──────────────┐
│   BUTTON     │  (Frame corner + edges + fill)
└──────────────┘  Asset: Button_Blue_Normal.png

Hover State:
┌──────────────┐
│   BUTTON     │  (Brightened/glowing)
└──────────────┘  Asset: Button_Blue_Hover.png
                  (Lighter fill + bright edge)

Pressed State:
┌──────────────┐
│   BUTTON     │  (Depressed, darker)
└──────────────┘  Asset: Button_Blue_Pressed.png
                  (Darker fill + inset effect)

Disabled State:
┌──────────────┐
│   BUTTON     │  (Grayed out)
└──────────────┘  Asset: Button_Gray_Disabled.png
                  (Desaturated colors)
```

---

### Animation Specifications

**Current AnimationAndSpriteLoader classes available:**
- `AnimationAndSpriteLoader.HorizontalSpritesheetLoader`
- `AnimationAndSpriteLoader.GridSpritesheetLoader`
- `AnimationAndSpriteLoader.StateTransition` (State machine)
- `AnimationAndSpriteLoader.PhysicsBody` (for physics effects)

**GUI Animation Use Cases:**

1. **Bar Fill Animation** (Property animation)
   ```
   Health: 100 → 85 (damage taken)
   Bar visual updates from 100% fill to 85% fill
   Duration: 0.3 seconds (smooth transition)
   Implementation: Lerp between two bar variant assets
   ```

2. **Button Hover Glow** (Sprite swap)
   ```
   Hover trigger: Mouse over button
   Visual: Swap to brighter variant (drawImage)
   Duration: Instant (asset swap, no animation)
   ```

3. **Notification Fade-In/Out**
   ```
   Entry: Alpha 0 → 255 (0.2s fade in)
   Stay: Alpha 255 (3s visible)
   Exit: Alpha 255 → 0 (0.2s fade out)
   Implementation: Create NotificationPanel extends GUIComponent
   Override render() to apply alpha composite
   ```

4. **Damage Numbers Float** (Sprite path)
   ```
   Appears: -15 damage text at entity position
   Movement: +20px Y per frame (rises)
   Effect: Red color from icon palette
   Duration: 2 seconds then disappear
   ```

5. **Bar Recharge Glow** (Color animation)
   ```
   When: Energy bar recharging
   Visual: Pulsing border (swap between dark/bright edge variants)
   Duration: Continuous until full
   ```

---

## IMPLEMENTATION PHASES

### Phase 1: Foundation (COMPLETE)
- [x] GUIComponent abstract base class
- [x] GameState data structure
- [x] DigitRenderer (PNG glyphs)
- [x] TopBarPanel (50px top bar)
- [x] HUDPanel (100px bottom panel)

### Phase 2: Interactive Components (IN PROGRESS)
- [x] LeftSidebar (200px sidebar, 4 tabs)
- [x] ButtonPanel (Pause, Settings, Weapons)
- [x] MouseInputHandler (Click/Hover tracking)
- [ ] **ModalManager** (Pause/Settings/Death modals)
  - Create `ModalManager extends GUIComponent`
  - Implement PauseModal class
  - Implement SettingsModal class
  - Implement DeathScreenModal class

### Phase 3: Notifications & Effects
- [ ] **NotificationSystem** (Popups, alerts)
  - DamagePopup (floating text, red color)
  - ItemAcquiredPopup (item icon + text)
  - AchievementPopup (star icon + text)
  
- [ ] **StatusEffectDisplay** (Poison, Frozen, Burning)
  - Icon rendering
  - Duration counter
  - Sound effects

- [ ] **FloatingTextSystem** (Damage, Healing)
  - Parametric animation (ease-out rise + fade)

### Phase 4: Animations & Polish
- [ ] **ButtonAnimationController**
  - Smooth hover transitions
  - Glow effects
  - Click feedback

- [ ] **BarAnimationController**
  - Smooth health/energy drain
  - Recharge glow pulse
  - Critical health flash

- [ ] **ScreenTransitions**
  - Fade-in/out between screens
  - Slide transitions
  - Cross-dissolve effects

### Phase 5: Advanced Features
- [ ] **KeyboardInputDisplay** (Keyboard/Mouse hint overlays)
  - Show required keys for actions
  - Dynamic based on unresolved actions

- [ ] **VoiceLineSystem** (Character dialogue)
  - Text display synchronized with audio
  - NPC portrait animation

- [ ] **MiniMap** (in LeftSidebar)
  - Level layout overview
  - Player position marker
  - Enemy positions

---

## CLASS ARCHITECTURE DIAGRAM

```
═════════════════════════════════════════════════════════════════════════════
                        COMPLETE CLASS HIERARCHY
═════════════════════════════════════════════════════════════════════════════

Package: gui

┌─────────────────────────────────────────────────────────────────────────┐
│ GUIComponent (abstract)                                                  │
│ └─ extendedBy: AnimationAndSpriteLoader                                 │
│                                                                          │
│ PUBLIC METHODS:                                                          │
│   abstract void loadAssets()                                             │
│   abstract void update(long elapsedTime, GameState state)               │
│   abstract void render(Graphics2D g)                                    │
│                                                                          │
│ PROTECTED METHODS:                                                       │
│   void drawImageAt(Graphics2D, BufferedImage, int, int, int, int)       │
│   void drawImageFit(Graphics2D, BufferedImage, int, int, int, int)      │
│   void drawImageTiled(Graphics2D, BufferedImage, int, int, int, int)    │
│                                                                          │
│ PROTECTED FIELDS:                                                        │
│   float posX, posY                    (Position on screen)              │
│   int width, height                   (Component dimensions)            │
│   boolean isVisible, isEnabled        (State flags)                     │
└─────────────────────────────────────────────────────────────────────────┘
          ▲     ▲     ▲     ▲     ▲
          │     │     │     │     │
          │     │     │     │     └──────┐
          │     │     │     │            │
 ┌────────┴─┐ ┌─┴─────┴─┐ ┌┴─────┐ ┌────┴────┐
 │           │         │   │      │ │         │
 
┌──────────────────────┐  ┌────────────────────┐  ┌──────────────────────┐
│  TopBarPanel         │  │   HUDPanel         │  │   LeftSidebar        │
├──────────────────────┤  ├────────────────────┤  ├──────────────────────┤
│ FIELDS:              │  │ FIELDS:            │  │ FIELDS:              │
│ • frameTopEdge       │  │ • panelBackground  │  │ • tabs[4]            │
│ • fillBackground     │  │ • panelDivider     │  │ • currentTab         │
│ • digitRenderer      │  │ • barRenderer      │  │ • inventorySlots[4]  │
│                      │  │ • digitRenderer    │  │ • skillIcons[5]      │
│ METHODS:             │  │                    │  │                      │
│ • render()           │  │ METHODS:           │  │ METHODS:             │
│ • setTimeRemaining() │  │ • renderBars()     │  │ • switchTab()        │
│ • setLevelInfo()     │  │ • renderAmmo()     │  │ • switchLevel()      │
│                      │  │ • renderStatus()   │  │ • render()           │
│                      │  │ • update...State() │  │ • update()           │
└──────────────────────┘  └────────────────────┘  └──────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│ ButtonPanel                                                              │
├──────────────────────────────────────────────────────────────────────────┤
│ NESTED CLASS:                                                            │
│   GUIButton                                                              │
│   • id, x, y, width, height                                             │
│   • label, normalImage, hoverImage, pressedImage                        │
│   • isHovered, isPressed                                                │
│   • onClickCallback (Runnable)                                          │
│   • containsPoint(int px, int py) → boolean                             │
│                                                                          │
│ FIELDS:                                                                 │
│ • buttons: List<GUIButton>                                             │
│ • mouseX, mouseY, mousePressed: State tracking                         │
│ • isPaused: boolean                                                     │
│ • buttonNormalImage, buttonHoverImage, buttonPressedImage             │
│                                                                          │
│ PUBLIC METHODS:                                                         │
│ • handleMousePress(int x, int y)                                       │
│ • handleMouseRelease(int x, int y)                                     │
│ • updateMousePosition(int x, int y)                                    │
│ • isPaused() → boolean                                                 │
│ • render(Graphics2D) - Renders all buttons & pause overlay            │
│ • update(long, GameState) - Updates button states                     │
│                                                                          │
│ PRIVATE METHODS:                                                        │
│ • initializeButtons() - Creates all button objects                    │
│ • togglePause() - Toggles pause state                                 │
│ • renderPauseOverlay(Graphics2D)                                      │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│ GameState                                                                │
│ (Pure data container - NO extends clause)                               │
├──────────────────────────────────────────────────────────────────────────┤
│ PUBLIC FIELDS:                                                           │
│   // Player Stats                                                        │
│   public int health, maxHealth                                          │
│   public int energy, maxEnergy                                          │
│   public int armor, maxArmor                                            │
│   public int ammo, ammoMax                                              │
│                                                                          │
│   // Level Info                                                          │
│   public String currentLevel, levelName                                 │
│   public int currentStage, totalStages, stageProgress                  │
│                                                                          │
│   // Timers                                                              │
│   public int timeRemainingSeconds, totalElapsedSeconds                 │
│                                                                          │
│   // Scoring                                                             │
│   public int score, enemiesDefeated, itemsCollected                    │
│                                                                          │
│   // Effects                                                             │
│   public Set<String> activeEffects                                      │
│   public String currentStatus                                           │
│                                                                          │
│   // Inventory                                                           │
│   public BufferedImage[] inventorySlots[4]                             │
│   public boolean[] inventoryActive[4]                                   │
│                                                                          │
│ PUBLIC METHODS:                                                         │
│ • int getHealthBarIndex() → 0-5 (for 6-variant bars)                 │
│ • int getEnergyBarIndex() → 0-5                                        │
│ • int getArmorBarIndex() → 0-5                                         │
│ • boolean isCritical() - Health/Energy < 20%                          │
│ • void addEffect(String effect)                                        │
│ • void removeEffect(String effect)                                     │
│ • boolean hasEffect(String effect)                                     │
│ • String getFormattedTime() → "MM:SS"                                 │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│ DigitRenderer                                                            │
│ (Renders PNG digit glyphs - NO extends clause)                          │
├──────────────────────────────────────────────────────────────────────────┤
│ FIELDS:                                                                 │
│ • digits: BufferedImage[] (0-9)                                        │
│ • slash, colon, dot: BufferedImage                                     │
│ • assetManager: GUIAssetManager                                        │
│                                                                          │
│ PUBLIC METHODS:                                                         │
│ • void loadAssets(GUIAssetManager)                                     │
│ • void renderNumber(Graphics2D, int number, int x, int y)            │
│ • void renderString(Graphics2D, String str, int x, int y)            │
│ • void renderAmmo(Graphics2D, int cur, int max, int x, int y)        │
│ • void renderTime(Graphics2D, int seconds, int x, int y)             │
│ • void renderScore(Graphics2D, long score, int x, int y)             │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│ MouseInputHandler                                                        │
│ implements MouseListener, MouseMotionListener                           │
├──────────────────────────────────────────────────────────────────────────┤
│ FIELDS:                                                                 │
│ • buttonPanel: ButtonPanel                                             │
│ • mouseX, mouseY: int                                                  │
│                                                                          │
│ PUBLIC METHODS:                                                         │
│ • void mouseMoved(MouseEvent)                                          │
│ • void mouseDragged(MouseEvent)                                        │
│ • void mousePressed(MouseEvent)                                        │
│ • void mouseReleased(MouseEvent)                                       │
│ • int getMouseX() → int                                                │
│ • int getMouseY() → int                                                │
│                                                                          │
│ LISTENER METHODS (stubs):                                              │
│ • void mouseClicked(MouseEvent)                                        │
│ • void mouseEntered(MouseEvent)                                        │
│ • void mouseExited(MouseEvent)                                         │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│ GUIAssetManager (Singleton)                                             │
│ (Pure utility - NO extends clause)                                      │
├──────────────────────────────────────────────────────────────────────────┤
│ FIELDS:                                                                 │
│ • instance: static GUIAssetManager                                      │
│ • assetCache: Map<String, BufferedImage>                              │
│ • resourcePath: String                                                 │
│                                                                          │
│ PUBLIC STATIC METHODS:                                                 │
│ • static GUIAssetManager getInstance() → GUIAssetManager              │
│                                                                          │
│ PUBLIC METHODS:                                                         │
│ • BufferedImage loadAsset(String category, String filename)           │
│ • BufferedImage getAsset(String category, String assetName)           │
│                                                                          │
│ PRIVATE METHODS:                                                        │
│ • BufferedImage mapCategoryToPath(String category)                    │
│ • BufferedImage searchDirectoriesRecursive(...)                       │
└──────────────────────────────────────────────────────────────────────────┘


═════════════════════════════════════════════════════════════════════════════
                      PHASE 2 & 3 PLANNED CLASSES
═════════════════════════════════════════════════════════════════════════════

┌──────────────────────────────────────────────────────────────────────────┐
│ ModalManager (extends GUIComponent → AnimationAndSpriteLoader)         │
├──────────────────────────────────────────────────────────────────────────┤
│ PURPOSE: Central modal management system                                │
│ PARENT CHAIN: ModalManager → GUIComponent → AnimationAndSpriteLoader   │
│                                                                          │
│ FIELDS:                                                                 │
│ • currentModal: Modal enum (NONE, PAUSE, SETTINGS, DEATH)              │
│ • isModalActive: boolean                                               │
│ • pauseModal: PauseModal                                               │
│ • settingsModal: SettingsModal                                         │
│ • deathModal: DeathScreenModal                                         │
│                                                                          │
│ PUBLIC METHODS:                                                         │
│ • void showModal(Modal modalType)                                      │
│ • void closeModal()                                                    │
│ • void render(Graphics2D)                                              │
│ • void update(long, GameState)                                         │
│ • void handleMouseClick(int x, int y)                                 │
│ • void handleKeyPress(int keyCode)                                     │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│ PauseModal (extends GUIComponent → AnimationAndSpriteLoader)           │
├──────────────────────────────────────────────────────────────────────────┤
│ NESTED CLASS: ModalButton (similar to ButtonPanel.GUIButton)           │
│                                                                          │
│ FIELDS:                                                                 │
│ • buttons: List<ModalButton> {Resume, Settings, MainMenu, Quit}       │
│ • overlayAlpha: int                                                    │
│ • windowX, windowY, windowWidth, windowHeight: int                     │
│ • cornerAssets[4]: BufferedImage                                       │
│ • edgeAssets (top, bottom, left, right): BufferedImage                │
│ • fillAsset: BufferedImage                                             │
│                                                                          │
│ PUBLIC METHODS:                                                         │
│ • void loadAssets()                                                    │
│ • void update(long, GameState)                                         │
│ • void render(Graphics2D)                                              │
│ • void handleClick(int x, int y) → Optional<Runnable>                │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│ NotificationSystem (extends GUIComponent → AnimationAndSpriteLoader)   │
├──────────────────────────────────────────────────────────────────────────┤
│ PURPOSE: Manage damage pops, item notifications, achievements          │
│                                                                          │
│ NESTED CLASS: Notification                                             │
│ • type: NotificationType (DAMAGE, ITEM, ACHIEVEMENT)                   │
│ • message: String                                                       │
│ • icon: BufferedImage                                                  │
│ • age: long (milliseconds)                                             │
│ • duration: long (lifespan)                                            │
│ • x, y: int (position)                                                 │
│ • vx, vy: float (velocity for damage pops)                             │
│ • alpha: float (0-255)                                                 │
│ • update(), render()                                                    │
│                                                                          │
│ FIELDS:                                                                 │
│ • activeNotifications: Queue<Notification>                             │
│ • maxNotifications: int = 5                                            │
│                                                                          │
│ PUBLIC METHODS:                                                         │
│ • void addDamagePopup(int damage, float x, float y)                    │
│ • void addItemNotification(String itemName, BufferedImage icon)        │
│ • void addAchievementNotification(String title)                        │
│ • void update(long, GameState)                                         │
│ • void render(Graphics2D)                                              │
└──────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────┐
│ StatusEffectDisplay (extends GUIComponent → AnimationAndSpriteLoader)  │
├──────────────────────────────────────────────────────────────────────────┤
│ PURPOSE: Render active status effects (Poison, Frozen, Burning, etc)   │
│                                                                          │
│ FIELDS:                                                                 │
│ • effectIcons: Map<String, BufferedImage>                              │
│ • effectDurations: Map<String, Integer> (remaining ms)                 │
│ • displayX, displayY: int (bottom-left position)                       │
│ • spacingX: int = 50 (pixels between icons)                            │
│                                                                          │
│ PUBLIC METHODS:                                                         │
│ • void addEffect(String effect, int durationMs)                        │
│ • void removeEffect(String effect)                                     │
│ • void update(long, GameState)                                         │
│ • void render(Graphics2D)                                              │
└──────────────────────────────────────────────────────────────────────────┘
```

---

## COMPLETE IMPLEMENTATION CHECKLIST

### Completed (Phase 1)
- [x] GUIComponent abstract base
- [x] GameState data holder
- [x] DigitRenderer
- [x] TopBarPanel (50px)
- [x] HUDPanel (100px)
- [x] LeftSidebar (200px, 4 tabs)
- [x] ButtonPanel (interactive buttons)
- [x] MouseInputHandler

### Current Task (Phase 2-3)
- [ ] **ModalManager** - Central modal coordinator
- [ ] **PauseModal** - Pause screen with menu
- [ ] **SettingsModal** - Settings dialog
- [ ] **DeathScreenModal** - Game over screen
- [ ] **NotificationSystem** - Damage/item/achievement popups
- [ ] **StatusEffectDisplay** - Active status icons
- [ ] **BarAnimationController** - Smooth health transitions
- [ ] **ButtonAnimationController** - Hover/press feedback

### Future (Phase 4-5)
- [ ] Screen transitions (fade/slide)
- [ ] Dialogue system
- [ ] MiniMap in sidebar
- [ ] Keyboard input hints
- [ ] Sound effect system
- [ ] Particle effects (raster-based)

---

## RASTER GRAPHICS ENFORCEMENT CHECKLIST

✓ **REQUIRED:**
- All visuals loaded from PNG/JPEG files only
- Use `g.drawImage(BufferedImage, x, y, null)` exclusively
- No `g.fillRect()`, `g.drawString()`, `g.setColor()`, `g.setFont()`
- Create image variants for state changes (normal/hover/pressed)
- Buffer all text as digit glyphs (PNG images)
- All colors come from pre-rendered assets, not generated

✓ **ASSET SOURCES:**
- Frames: `gui/1 Frames/` (82 files)
- Bars: `gui/2 Bars/` (20 files)
- Icons: `gui/3 Icons/` (40 files)
- Buttons: `gui/6 Buttons/` (30 files)
- Numbers: `gui/7 Numbers/` (17 files)
- Cursors: `gui/8 Cursors/` (4 files)
- Decorative: `gui/9 Other/` (20+ files)

✓ **NO EXCEPTIONS:**
- Vector graphics banned everywhere (includes modal backgrounds)
- Modal overlays: Use semi-transparent PNG or alpha composite
- All borders: Frame asset pieces (corners, edges)
- All fills: Tiled fill assets
- Button states: Separate PNG files per state

---

## FINAL NOTES

This comprehensive plan establishes a **professional, scalable GUI/HUD system** for the CSCU9N6 game using **100% raster graphics** (PNG images) with no vector graphics whatsoever. The hierarchical inheritance structure leverages `AnimationAndSpriteLoader` infrastructure for all asset management, sprite handling, and animation support.

**Next Steps:**
1. Implement ModalManager (central modal coordinator)
2. Build PauseModal, SettingsModal, DeathScreenModal
3. Create NotificationSystem for popups
4. Add animation controllers for smooth transitions
5. Polish with sound effects and particle systems

All graphics assets are pre-organized in `Resources/industrial-zone/gui/` folders, providing a complete inventory of 200+ visual elements ready for integration.

---

**Document End**  
*Last Updated: April 2, 2026*  
*Status: Ready for Implementation*
