# COMPREHENSIVE GUI IMPLEMENTATION PLAN - EXPANDED
## CSCU9N6 Industrial Zone Platformer - Complete GUI Subsystem
### Last Updated: April 2, 2026 | Version 2.0 - EXTENDED

---

## EXTENDED TABLE OF CONTENTS
1. [Executive Overview](#executive-overview)
2. [Complete Game State Machine](#complete-game-state-machine)
3. [Screen Flow Diagram](#screen-flow-diagram)
4. [Asset Structure & Organization](#asset-structure--organization)
5. [INTRO SCREEN - Detailed Specification](#intro-screen---detailed-specification)
6. [MAIN MENU - Detailed Specification](#main-menu---detailed-specification)
7. [CHARACTER SELECTION - Detailed 3-Character System](#character-selection---detailed-3-character-system)
8. [HOW TO PLAY - Keyboard & Controls Guide](#how-to-play---keyboard--controls-guide)
9. [LEVEL SELECT - Detailed Specification](#level-select---detailed-specification)
10. [IN-GAME GUI - Complete](#in-game-gui---complete)
11. [PAUSE MENU - Complete](#pause-menu---complete)
12. [GAME OVER & LEVEL COMPLETE](#game-over--level-complete)
13. [SETTINGS SCREEN - Options & Configuration](#settings-screen---options--configuration)
14. [Frame Specifications & Dimensions](#frame-specifications--dimensions)
15. [Implementation Phases](#implementation-phases)

---

## EXECUTIVE OVERVIEW

### Complete GUI System Scope
This document covers the ENTIRE GUI system for the game, including:
- **7 Main Screens**: Intro, Menu, Character Select, How to Play, Level Select, Game, Settings
- **2 Pause Screens**: Pause Menu, Game Over Screen, Level Complete
- **4 In-Game Panels**: TopBar, Sidebar, ButtonPanel, HUDBar
- **3 Character Models**: Biker, Punk, Cyborg (full stat systems)
- **Keyboard & Mouse Control Display**: Using hardware key assets from Resources
- **Professional Frame System**: 82 tile-based frames across 7 themes

### Design Philosophy - The 5 Pillars
1. **Asset-First Architecture**: Every visual comes from pre-made PNG files
2. **State-Machine Driven**: Game states map 1:1 to GUI screens
3. **Keyboard Asset Integration**: Display actual key visuals for controls
4. **Animation & Feedback**: Smooth transitions, hover states, selections
5. **Professional Polish**: Consistent framing, spacing, typography (via assets)

### Asset Foundation
All rendering uses:
```
Resources/industrial-zone/gui/              ← Main GUI folder
  1 Frames/ (82 tiles × 7 themes)          ← Screen borders & frames
  6 Buttons/ (all interactive buttons)      ← Button assets
  7 Numbers/ (digit 0-9 for HUD)           ← Numeric display
  3 Icons/ (status, health, ammo icons)    ← Status indicators
  2 Bars/ (health, ammo, mana bars)        ← Progress bars
  
+ Keyboard Resources:
Resources/industrial-zone/KeyBoard_Keys/   ← Individual key sprites
Resources/industrial-zone/Mouse_keys/      ← Mouse button sprites
```

---

## COMPLETE GAME STATE MACHINE

### Enhanced State Diagram with All Transitions

```
                    ┌─────────────────┐
                    │  INTRO_SCREEN   │
                    │  (Splash/Logo)  │
                    └────────┬────────┘
                             │ (auto-advance 3s)
                             ↓
                    ┌─────────────────┐
                    │   MAIN_MENU     │
                    │  (Start Screen) │
    ┌───────────────┼────────┬────────┼─────────────┐
    │               │        │        │             │
    │ "How to Play" │        │        │ "Quit"      │
    │               │        │        │             │
    ↓               ↓        ↓        ↓             ↓
 HELP_SCREEN   ...  │   "New Game"  [EXIT]
    │               │        │
    │ (Back)        │        ↓
    └──────┐        │   CHARACTER_SELECT
           │        │   (Choose Avatar)
           │        │        │
           │        │        │ (Char selected)
           │        │        ↓
           │        │   LEVEL_SELECT
           │        │   (Choose Level)
           │        │        │
           │        │        │ (Level selected)
           │        │        ↓
           └────────┼────────────────────────┐
                    │                        │
                    │                        ↓
            ┌───────┴────────┐      GAME_ACTIVE
            │                │      (4 Panels)
            │                │        │  │
            └─────────────────┘       │  │ (Pause)
            (Resume/Quit)             │  ↓
                    ↑                 │ GAME_PAUSED
                    │                 │ (Menu)
                    │                 │
           ┌────────┼─────────────────┘
           │        │
    (Resume)│       │ (Death/Time)
           │        ↓
           │    GAME_OVER
           │    (Score Screen)
           │        │
           │        ├─→ RETRY → GAME_ACTIVE
           │        │
           │        └─→ MENU → LEVEL_SELECT
           │
           ↓ (Complete)
        LEVEL_COMPLETE
        (Victory Screen)
           │
           ├─→ NEXT LEVEL → LEVEL_SELECT
           │
           └─→ MENU → MAIN_MENU

Special: SETTINGS (overlay - non-blocking)
- Accessible from: MAIN_MENU, GAME_PAUSED
- Returns to: Previous state
```

---

## SCREEN FLOW DIAGRAM

```
WELCOME EXPERIENCE:
┌──────────────┐      ┌──────────────┐
│ Intro Screen │ ───→ │  Main Menu   │
│ (Logo, fade) │  3s  │ (New/Continue)
└──────────────┘      └──────┬───────┘
                              │
                    ┌─────────┼─────────┐
                    │         │         │
                 [New Game] [How to] [Settings]
                    │        Play      │
                    │         │        │
                    ↓         ↓        ↓
            ┌──────────┐ ┌────────┐ ┌──────────┐
            │ Character│ │How to  │ │Settings  │
            │ Select   │ │Play    │ │ Options  │
            │(3 chars) │ │(Controls)│(Audio,etc)
            └──────┬───┘ │        │ └──────────┘
                   │     └────┬───┘
                   │          │
            ┌──────│──────────┘
            ↓      │
        ┌──────────┐
        │ Levels   │◄─────────────┐
        │ Select   │              │
        └─────┬────┘              │
              │ (Level chosen)     │
              ↓                    │
        ┌──────────────────────────┘
        │
        ↓ (Game Start)
    ┌────────────────┐
    │ GAME_ACTIVE    │
    │ (Gameplay HUD) │
    └────┬───────┬───┘
    Pause│       │Death/Complete
        ↓       ↓
    ┌────────┐┌──────────┐
    │Pause   ││Game Over │
    │Menu    │└──────────┘
    └─┬──────┘     │
      │            └──→ Retry or Menu
      │
      └──→ Resume/Settings/Quit
```

---

## ASSET STRUCTURE & ORGANIZATION - COMPLETE

### Primary GUI Assets (Already in Resources)

```
Resources/industrial-zone/gui/
├─ 1 Frames/                      [82 TILES × 7 THEMES]
│  ├─ frame_theme_1_*.png        (Industrial Blue)
│  ├─ frame_theme_2_*.png        (Tech Purple)
│  ├─ frame_theme_3_*.png        (Dark Red)
│  ├─ frame_theme_4_*.png        (Neon Green)
│  ├─ frame_theme_5_*.png        (Orange Steel)
│  ├─ frame_theme_6_*.png        (Cyan Metal)
│  └─ frame_theme_7_*.png        (Gray Concrete)
│     └─ Tiles: corners, edges, fills (box-drawing)
│
├─ 2 Bars/
│  ├─ health_bar_full.png
│  ├─ health_bar_empty.png
│  ├─ ammo_bar_full.png
│  ├─ ammo_bar_empty.png
│  └─ mana_bar_*.png
│
├─ 3 Icons/
│  ├─ Buttons2/
│  │  ├─ icon_pause.png
│  │  ├─ icon_play.png
│  │  ├─ icon_settings.png
│  │  └─ icon_*.png
│  └─ Icons/
│     ├─ health.png
│     ├─ ammo.png
│     ├─ mana.png
│     └─ status_*.png
│
├─ 6 Buttons/              [⭐ PRIMARY]
│  ├─ btn_play.png, _hover.png, _pressed.png
│  ├─ btn_pause.png, _hover.png, _pressed.png
│  ├─ btn_settings.png, _hover.png, _pressed.png
│  ├─ btn_help.png, _hover.png, _pressed.png
│  ├─ btn_wpn_1.png, _hover.png, _pressed.png
│  ├─ btn_wpn_2.png, _hover.png, _pressed.png
│  ├─ btn_wpn_3.png, _hover.png, _pressed.png
│  └─ btn_*.png (all variations)
│
├─ 7 Numbers/
│  ├─ digit_0.png through digit_9.png
│  └─ And decimal point.png
│
├─ 8 Cursors/
│  └─ cursor_*.png
│
└─ 9 Other/
   ├─ 1 Decor/ (decorative borders)
   └─ 2 Skill icons/ (ability icons)

Resources/industrial-zone/characters/
├─ player/
│  ├─ Biker/
│  │  └─ portrait_biker.png (for menu)
│  ├─ Punk/
│  │  └─ portrait_punk.png
│  └─ Cyborg/
│     └─ portrait_cyborg.png

Resources/industrial-zone/KeyBoard_Keys/  [⭐ NEW]
├─ key_W.png                    (Movement Up)
├─ key_A.png                    (Move Left)
├─ key_S.png                    (Move Down)
├─ key_D.png                    (Move Right)
├─ key_Space.png                (Jump)
├─ key_1.png, key_2.png, key_3.png  (Weapon select)
├─ key_P.png                    (Pause)
├─ key_E.png                    (Interact)
├─ key_Shift.png                (Sprint/Dash)
├─ key_Ctrl.png                 (Crouch)
├─ key_Tab.png                  (Inventory)
└─ key_Esc.png                  (Menu/Back)

Resources/industrial-zone/Mouse_keys/     [⭐ NEW]
├─ mouse_left.png               (Left click)
├─ mouse_right.png              (Right click)
├─ mouse_middle.png             (Middle click)
├─ mouse_wheel.png              (Scroll)
└─ mouse_move.png               (Cursor movement)
```

---

## INTRO SCREEN - DETAILED SPECIFICATION

### Visual Layout

```
┌─────────────────────────────────────────────┐
│                                             │
│                                             │
│                                             │
│           INDUSTRIAL ZONE                  │
│           PLATFORMER GAME                  │
│                                             │
│              [LOGO IMAGE]                  │
│         (512x256px centered)                │
│                                             │
│                                             │
│           Presents...                      │
│                                             │
│           © 2026 Game Studio               │
│           CSCU9N6 Assignment               │
│                                             │
│    [Auto-advancing in 3 seconds...]        │
│    [or press any key to continue]          │
│                                             │
└─────────────────────────────────────────────┘
```

### Technical Specifications

**Screen Duration**: 3 seconds (configurable)
**Dimensions**: Full window (1280x720 default)
**Background**: Solid dark color OR scrolling parallax

**Assets Used**:
- `GUI_LOGO + "logo_main.png"` - Centered game logo
- `GUI_BASE + "bg_intro.png"` - Background image
- None - Static text (if using font assets)

**Animation Sequence**:
```
Timeline:
0ms    → Logo fade-in (0% → 100% alpha, 500ms)
500ms  → Hold at full opacity (1000ms)
1500ms → Additional company text fades in (500ms)
2000ms → Hold (1000ms)
3000ms → Auto-transition to MAIN_MENU
        OR on any key/click → Immediate transition
```

**Interaction**:
- Any key press → Skip to MAIN_MENU
- Any mouse click → Skip to MAIN_MENU
- Auto-advance after 3 seconds

**Code Template**:
```java
public class IntroScreen extends AnimationAndSpriteLoader {
    private BufferedImage logoImage;
    private BufferedImage backgroundImage;
    private long startTime;
    private float fadeAlpha = 0f;
    private boolean skipRequested = false;
    
    public IntroScreen() {
        super();
        logoImage = loadImage(GUI_LOGO + "logo_main.png");
        backgroundImage = loadImage(GUI_BASE + "bg_intro.png");
        startTime = System.currentTimeMillis();
    }
    
    @Override
    public BufferedImage render(int width, int height) {
        BufferedImage screen = new BufferedImage(
            width, height, BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g2d = screen.createGraphics();
        
        // Background
        g2d.drawImage(backgroundImage, 0, 0, width, height, null);
        
        // Logo with fade animation
        long elapsed = System.currentTimeMillis() - startTime;
        float progress = elapsed / 500f;  // 500ms fade-in
        fadeAlpha = Math.min(1f, progress);
        
        g2d.setComposite(AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, fadeAlpha
        ));
        int logoX = (width - logoImage.getWidth()) / 2;
        int logoY = (height - logoImage.getHeight()) / 2;
        g2d.drawImage(logoImage, logoX, logoY, null);
        
        g2d.dispose();
        return screen;
    }
    
    @Override
    public void handleKeyPress(KeyEvent e) {
        skipRequested = true;
    }
    
    public boolean shouldTransition() {
        return skipRequested || System.currentTimeMillis() - startTime > 3000;
    }
}
```

---

## MAIN MENU - DETAILED SPECIFICATION

### Enhanced Visual Layout with Frames

```
┌──────────────────────────────────────────────────────┐
│ ╔════════════════════════════════════════════════╗   │
│ ║                                                ║   │
│ ║     INDUSTRIAL ZONE PLATFORMER GAME            ║   │
│ ║                                                ║   │
│ ║               [LARGE LOGO]                     ║   │
│ ║            (512x256px centered)                ║   │
│ ║                                                ║   │
│ ║   ┌──────────────────────────────────────┐    ║   │
│ ║   │         MAIN MENU OPTIONS            │    ║   │
│ ║   ├──────────────────────────────────────┤    ║   │
│ ║   │                                      │    ║   │
│ ║   │   ▶ [NEW GAME]                      │    ║   │
│ ║   │   ▶ [CONTINUE]   (if save exists)   │    ║   │
│ ║   │   ▶ [HOW TO PLAY]                    │    ║   │
│ ║   │   ▶ [SETTINGS]                       │    ║   │
│ ║   │   ▶ [QUIT TO DESKTOP]                │    ║   │
│ ║   │                                      │    ║   │
│ ║   └──────────────────────────────────────┘    ║   │
│ ║                                                ║   │
│ ║   Press/Click each button above to continue    ║   │
│ ║                                                ║   │
│ ╚════════════════════════════════════════════════╝   │
│                                                      │
└──────────────────────────────────────────────────────┘
```

### Assets & Dimensions

**Outer Frame**: Theme-selected frame tiles (82-piece system)
- Load: `GUI_FRAMES + "frame_theme_1_corner_tl.png"` etc.
- Dimensions: Full screen (1280x720)
- Tiles: 9×6 grid of 128×128px frame pieces

**Logo**: 
- Asset: `GUI_LOGO + "logo_large.png"`
- Dimensions: 512×256px
- Position: Centered horizontally, 80px from top

**Menu Box**:
- Asset: Compose from frame tiles
- Dimensions: 600×320px
- Position: Centered

**Buttons** (5 options):
- NEW GAME: `GUI_BUTTONS + "btn_play.png"`
- CONTINUE: `GUI_BUTTONS + "btn_continue.png"`
- HOW TO PLAY: `GUI_BUTTONS + "btn_help.png"`
- SETTINGS: `GUI_BUTTONS + "btn_settings.png"`
- QUIT: `GUI_BUTTONS + "btn_quit.png"`
- Each with: normal, hover, pressed states
- Size: 320×64px each
- Spacing: 20px between buttons

### Interaction Flow

```
User hovers on button
    → Button state → HOVER
    → Display hover image
    
User clicks button
    → Button state → PRESSED
    → Display pressed image
    → On mouse-up:
         • NEW GAME → CHARACTER_SELECT
         • CONTINUE → LEVEL_SELECT (with save)
         • HOW TO PLAY → HOW_TO_PLAY_SCREEN
         • SETTINGS → SETTINGS_SCREEN
         • QUIT → System.exit(0)
```

### Implementation Template

```java
public class MainMenuScreen extends AnimationAndSpriteLoader {
    private BufferedImage backgroundImage;
    private BufferedImage logoImage;
    private List<MenuButton> buttons;
    private String selectedTheme = "theme_1";  // 7 themes available
    
    public MainMenuScreen() {
        super();
        loadAssets();
        initializeButtons();
    }
    
    private void loadAssets() {
        backgroundImage = loadImage(GUI_BASE + "bg_mainmenu.png");
        logoImage = loadImage(GUI_LOGO + "logo_large.png");
    }
    
    private void initializeButtons() {
        buttons = new ArrayList<>();
        
        buttons.add(new MenuButton(
            "new", "play", 340, 300,
            () -> Game.setState(GameState.CHARACTER_SELECT)
        ));
        
        buttons.add(new MenuButton(
            "continue", "continue", 340, 380,
            () -> Game.setState(GameState.LEVEL_SELECT)
        ));
        
        buttons.add(new MenuButton(
            "help", "help", 340, 460,
            () -> Game.setState(GameState.HOW_TO_PLAY)
        ));
        
        buttons.add(new MenuButton(
            "settings", "settings", 340, 540,
            () -> Game.setState(GameState.SETTINGS)
        ));
        
        buttons.add(new MenuButton(
            "quit", "quit", 340, 620,
            () -> System.exit(0)
        ));
    }
    
    @Override
    public BufferedImage render(int width, int height) {
        BufferedImage screen = new BufferedImage(
            width, height, BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g2d = screen.createGraphics();
        
        // Background
        g2d.drawImage(backgroundImage, 0, 0, width, height, null);
        
        // Frame border (compose from tiles)
        drawFrameBorder(g2d, width, height, selectedTheme);
        
        // Logo
        int logoX = (width - logoImage.getWidth()) / 2;
        g2d.drawImage(logoImage, logoX, 80, null);
        
        // Buttons
        for (MenuButton button : buttons) {
            g2d.drawImage(button.render(), 
                button.getX(), button.getY(), null);
        }
        
        g2d.dispose();
        return screen;
    }
    
    private void drawFrameBorder(Graphics2D g2d, int w, int h, String theme) {
        // Load and composite frame tiles in 9×6 grid
        String frameBasePath = GUI_FRAMES + "frame_" + theme + "_";
        
        // Corners
        BufferedImage tl = loadImage(frameBasePath + "corner_tl.png");
        BufferedImage tr = loadImage(frameBasePath + "corner_tr.png");
        BufferedImage bl = loadImage(frameBasePath + "corner_bl.png");
        BufferedImage br = loadImage(frameBasePath + "corner_br.png");
        
        g2d.drawImage(tl, 0, 0, null);
        g2d.drawImage(tr, w - 128, 0, null);
        g2d.drawImage(bl, 0, h - 128, null);
        g2d.drawImage(br, w - 128, h - 128, null);
        
        // Edges (repeated)
        // Top edge
        for (int x = 128; x < w - 128; x += 128) {
            BufferedImage top = loadImage(frameBasePath + "edge_top.png");
            g2d.drawImage(top, x, 0, null);
        }
        // Bottom, left, right similar...
    }
}
```

---

## CHARACTER SELECTION - DETAILED 3-CHARACTER SYSTEM WITH ANIMATED PORTRAITS

### Screen Layout with Animated Character Cycling

```
┌──────────────────────────────────────────────────────────────────┐
│ ╔════════════════════════════════════════════════════════════╗   │
│ ║     SELECT YOUR CHARACTER - Choose Wisely!               ║   │
│ ╠════════════════════════════════════════════════════════════╣   │
│ ║                                                            ║   │
│ ║   ┌─────────────┐  ┌─────────────┐  ┌─────────────┐      ║   │
│ ║   │   BIKER     │  │    PUNK     │  │  CYBORG     │      ║   │
│ ║   │             │  │             │  │             │      ║   │
│ ║   │  [ANIMATED] │  │  [ANIMATED] │  │  [ANIMATED] │      ║   │
│ ║   │   256×384   │  │   256×384   │  │   256×384   │      ║   │
│ ║   │  PORTRAIT   │  │  PORTRAIT   │  │  PORTRAIT   │      ║   │
│ ║   │             │  │             │  │             │      ║   │
│ ║   │  Cycling:   │  │  Cycling:   │  │  Cycling:   │      ║   │
│ ║   │ Idle→Walk→  │  │ Idle→Walk→  │  │ Idle→Walk→  │      ║   │
│ ║   │ Attack→Jump │  │ Attack→Jump │  │ Attack→Jump │      ║   │
│ ║   │ →Fall→Idle  │  │ →Fall→Idle  │  │ →Fall→Idle  │      ║   │
│ ║   │ (LOOP)      │  │ (LOOP)      │  │ (LOOP)      │      ║   │
│ ║   └─────────────┘  └─────────────┘  └─────────────┘      ║   │
│ ║                                                            ║   │
│ ║   NAME: [BIKER]            NAME: [PUNK]      NAME: [CYBORG]║  │
│ ║                                                            ║   │
│ ║   HP:        100           HP:        85      HP:        110  ║   │
│ ║   DAMAGE:     18           DAMAGE:     22     DAMAGE:     12  ║   │
│ ║   SPEED:      14           SPEED:      16     SPEED:      10  ║   │
│ ║   ARMOR:      10           ARMOR:       5     ARMOR:      20  ║   │
│ ║   WEAPON:  Gun             WEAPON: Sword      WEAPON:  Laser  ║   │
│ ║                                                            ║   │
│ ║   DESCRIPTION:        DESCRIPTION:       DESCRIPTION:        ║   │
│ ║   "Tough fighter     "Swift blade        "Heavy damage       ║   │
│ ║    with balanced      master with high    with armor &       ║   │
│ ║    stats and steady   mobility but        powered weapons.    ║   │
│ ║    aim."              low defense."       Slow but relentless"║   │
│ ║                                                            ║   │
│ ║                                                            ║   │
│ ║   ┌─────────────────────────────────────────────┐        ║   │
│ ║   │  [← BACK]          [SELECT BIKER] ★        │        ║   │
│ ║   └─────────────────────────────────────────────┘        ║   │
│ ║                                                            ║   │
│ ╚════════════════════════════════════════════════════════════╝   │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘
```

### Animation Sequence - 24+ States Cycling

Each character portrait continuously cycles through all animation states:

**ANIMATION CYCLE SEQUENCE:**
```
Timeline (continuous loop):

1. IDLE (4 frames, 150ms each) = 600ms total
   └─ Standing breathing, relaxed pose
   
2. WALK_RIGHT (6 frames, 100ms each) = 600ms total
   └─ Walking cycle moving right
   
3. ATTACK_MELEE (5-7 frames, 70ms each) = 350-490ms total
   └─ Punch/strike animation
   
4. JUMP (4 frames, 80ms each) = 320ms total
   └─ Jump arc rising
   
5. DOUBLE_JUMP (6 frames, 80ms each) = 480ms total
   └─ Second jump in mid-air
   
6. FALL (4 frames, 100ms each) = 400ms total
   └─ Falling descent
   
7. DASH_RIGHT (6 frames, 60ms each) = 360ms total
   └─ Quick dash/slide
   
8. CLIMB (6 frames, 120ms each) = 720ms total
   └─ Ladder climbing motion
   
9. HANG (3 frames, 150ms each) = 450ms total
   └─ Ledge hanging idle
   
[Back to IDLE - loop repeats]

TOTAL CYCLE TIME: ~5.5-6 seconds per full animation sequence
TOTAL FRAMES DISPLAYED: 44+ unique frames across all states
```

### Character Animation Assets (From AnimationAndSpriteLoader)

```java
// BIKER CHARACTER ANIMATIONS (from Resources/industrial-zone/characters/biker/)
BIKER_IDLE = "01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"
BIKER_WALK = "03_Player_Biker_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png"
BIKER_JUMP = "06_Player_Biker_Jump_4Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png"
BIKER_DOUBLE_JUMP = "07_Player_Biker_DoubleJump_6Frames1Row_MidAirFlipBoost_SecondJump_PlayOnce_80ms.png"
BIKER_FALL = "08_Player_Biker_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png"
BIKER_DASH = "05_Player_Biker_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png"
BIKER_CLIMB = "09_Player_Biker_Climb_6Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png"
BIKER_HANG = "10_Player_Biker_Hang_3Frames1Row_LedgeHangHold_HangingIdle_Loop_150ms.png"
BIKER_ATTACK = "12_Player_Biker_Punch_5Frames1Row_StandingPunchCombo_MeleeAttack_PlayOnce_70ms.png"

// PUNK CHARACTER ANIMATIONS (from Resources/industrial-zone/characters/punk/)
PUNK_IDLE = "01_Player_Punk_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"
PUNK_WALK = "03_Player_Punk_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png"
PUNK_JUMP = "06_Player_Punk_Jump_4Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png"
PUNK_ATTACK = "12_Player_Punk_Slash_6Frames1Row_SwordCombo_MeleeAttack_PlayOnce_70ms.png"
PUNK_DASH = "05_Player_Punk_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png"
PUNK_FALL = "08_Player_Punk_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png"

// CYBORG CHARACTER ANIMATIONS (from Resources/industrial-zone/characters/cyborg/)
CYBORG_IDLE = "01_Player_Cyborg_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"
CYBORG_WALK = "03_Player_Cyborg_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png"
CYBORG_JUMP = "06_Player_Cyborg_Jump_4Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png"
CYBORG_ATTACK = "15_Player_Cyborg_LaserCharge_6Frames1Row_ChargedEnergyPulse_LaserAttack_PlayOnce_80ms.png"
CYBORG_FALL = "08_Player_Cyborg_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png"
```

### Character Data Structure with Animation State Management

```java
public class AnimatedCharacterProfile {
    public String characterName;        // "BIKER", "PUNK", "CYBORG"
    public String characterPath;        // "Resources/industrial-zone/characters/biker/"
    
    // Character Stats
    public int healthMax;
    public int damageBase;
    public int speedBase;
    public int armorBase;
    public String weaponType;
    
    // Animation Sequences (sprite sheets with frame counts)
    public List<AnimationState> animationSequence;
    public Map<AnimationState, String> stateToAssetPath;
    public Map<AnimationState, Integer> stateToFrameCount;
    public Map<AnimationState, Integer> stateToDurationMs;
    
    // Active Animation Tracking
    private AnimationState currentAnimationState;
    private BufferedImage[] currentFrames;     // Pre-loaded frames
    private int currentFrameIndex = 0;
    private long frameStartTime = 0;
    
    public String description;
}

public enum AnimationState {
    IDLE(4, 150),           // 4 frames @ 150ms each
    WALK(6, 100),           // 6 frames @ 100ms each
    ATTACK(5, 70),          // 5-7 frames @ 70ms each
    JUMP(4, 80),            // 4 frames @ 80ms each
    DOUBLE_JUMP(6, 80),     // 6 frames @ 80ms each
    FALL(4, 100),           // 4 frames @ 100ms each
    DASH(6, 60),            // 6 frames @ 60ms each
    CLIMB(6, 120),          // 6 frames @ 120ms each
    HANG(3, 150);           // 3 frames @ 150ms each
    
    public final int frameCount;
    public final int durationPerFrameMs;
    
    AnimationState(int frames, int ms) {
        this.frameCount = frames;
        this.durationPerFrameMs = ms;
    }
}
```

### Portrait Animation Rendering - Implementation

```java
public class CharacterSelectScreen extends AnimationAndSpriteLoader {
    
    private AnimatedCharacterProfile[] characters;
    private int selectedIndex = -1;
    private int hoveredIndex = -1;
    
    // Animation cycle: IDLE → WALK → ATTACK → JUMP → DOUBLE_JUMP → FALL → DASH → CLIMB → HANG → IDLE
    private static final AnimationState[] ANIMATION_CYCLE = {
        AnimationState.IDLE,
        AnimationState.WALK,
        AnimationState.ATTACK,
        AnimationState.JUMP,
        AnimationState.DOUBLE_JUMP,
        AnimationState.FALL,
        AnimationState.DASH,
        AnimationState.CLIMB,
        AnimationState.HANG
    };
    
    public CharacterSelectScreen() {
        super();
        initializeCharacters();
        startAnimationCycle();
    }
    
    private void initializeCharacters() {
        characters = new AnimatedCharacterProfile[3];
        
        // ═════════════════════════════════════════════════
        // BIKER - BALANCED FIGHTER
        // ═════════════════════════════════════════════════
        characters[0] = new AnimatedCharacterProfile();
        characters[0].characterName = "BIKER";
        characters[0].characterPath = "Resources/industrial-zone/characters/biker/";
        characters[0].healthMax = 100;
        characters[0].damageBase = 18;
        characters[0].speedBase = 14;
        characters[0].armorBase = 10;
        characters[0].weaponType = "Gun";
        characters[0].description = "Battle-hardened veteran with balanced stats and steady aim.";
        
        // Load animation sprite sheets
        characters[0].stateToAssetPath = new HashMap<>();
        characters[0].stateToAssetPath.put(AnimationState.IDLE,
            characters[0].characterPath + "01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png");
        characters[0].stateToAssetPath.put(AnimationState.WALK,
            characters[0].characterPath + "03_Player_Biker_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png");
        characters[0].stateToAssetPath.put(AnimationState.ATTACK,
            characters[0].characterPath + "12_Player_Biker_Punch_5Frames1Row_StandingPunchCombo_MeleeAttack_PlayOnce_70ms.png");
        characters[0].stateToAssetPath.put(AnimationState.JUMP,
            characters[0].characterPath + "06_Player_Biker_Jump_4Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png");
        characters[0].stateToAssetPath.put(AnimationState.DOUBLE_JUMP,
            characters[0].characterPath + "07_Player_Biker_DoubleJump_6Frames1Row_MidAirFlipBoost_SecondJump_PlayOnce_80ms.png");
        characters[0].stateToAssetPath.put(AnimationState.FALL,
            characters[0].characterPath + "08_Player_Biker_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png");
        characters[0].stateToAssetPath.put(AnimationState.DASH,
            characters[0].characterPath + "05_Player_Biker_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png");
        characters[0].stateToAssetPath.put(AnimationState.CLIMB,
            characters[0].characterPath + "09_Player_Biker_Climb_6Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png");
        characters[0].stateToAssetPath.put(AnimationState.HANG,
            characters[0].characterPath + "10_Player_Biker_Hang_3Frames1Row_LedgeHangHold_HangingIdle_Loop_150ms.png");
        
        // ═════════════════════════════════════════════════
        // PUNK - SPEED/DAMAGE AGENT
        // ═════════════════════════════════════════════════
        characters[1] = new AnimatedCharacterProfile();
        characters[1].characterName = "PUNK";
        characters[1].characterPath = "Resources/industrial-zone/characters/punk/";
        characters[1].healthMax = 85;
        characters[1].damageBase = 22;
        characters[1].speedBase = 16;
        characters[1].armorBase = 5;
        characters[1].weaponType = "Sword";
        characters[1].description = "Agile street fighter excelling at close-quarters combat.";
        
        characters[1].stateToAssetPath = new HashMap<>();
        characters[1].stateToAssetPath.put(AnimationState.IDLE,
            characters[1].characterPath + "01_Player_Punk_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png");
        characters[1].stateToAssetPath.put(AnimationState.WALK,
            characters[1].characterPath + "03_Player_Punk_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png");
        characters[1].stateToAssetPath.put(AnimationState.ATTACK,
            characters[1].characterPath + "12_Player_Punk_Slash_6Frames1Row_SwordCombo_MeleeAttack_PlayOnce_70ms.png");
        characters[1].stateToAssetPath.put(AnimationState.JUMP,
            characters[1].characterPath + "06_Player_Punk_Jump_4Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png");
        characters[1].stateToAssetPath.put(AnimationState.DOUBLE_JUMP,
            characters[1].characterPath + "07_Player_Punk_DoubleJump_6Frames1Row_MidAirFlipBoost_SecondJump_PlayOnce_80ms.png");
        characters[1].stateToAssetPath.put(AnimationState.FALL,
            characters[1].characterPath + "08_Player_Punk_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png");
        characters[1].stateToAssetPath.put(AnimationState.DASH,
            characters[1].characterPath + "05_Player_Punk_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png");
        characters[1].stateToAssetPath.put(AnimationState.CLIMB,
            characters[1].characterPath + "09_Player_Punk_Climb_6Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png");
        characters[1].stateToAssetPath.put(AnimationState.HANG,
            characters[1].characterPath + "10_Player_Punk_Hang_3Frames1Row_LedgeHangHold_HangingIdle_Loop_150ms.png");
        
        // ═════════════════════════════════════════════════
        // CYBORG - TANK/ARMOR BUILD
        // ═════════════════════════════════════════════════
        characters[2] = new AnimatedCharacterProfile();
        characters[2].characterName = "CYBORG";
        characters[2].characterPath = "Resources/industrial-zone/characters/cyborg/";
        characters[2].healthMax = 110;
        characters[2].damageBase = 12;
        characters[2].speedBase = 10;
        characters[2].armorBase = 20;
        characters[2].weaponType = "Laser";
        characters[2].description = "Unstoppable defense with sustained powered firepower.";
        
        characters[2].stateToAssetPath = new HashMap<>();
        characters[2].stateToAssetPath.put(AnimationState.IDLE,
            characters[2].characterPath + "01_Player_Cyborg_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png");
        characters[2].stateToAssetPath.put(AnimationState.WALK,
            characters[2].characterPath + "03_Player_Cyborg_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png");
        characters[2].stateToAssetPath.put(AnimationState.ATTACK,
            characters[2].characterPath + "15_Player_Cyborg_LaserCharge_6Frames1Row_ChargedEnergyPulse_LaserAttack_PlayOnce_80ms.png");
        characters[2].stateToAssetPath.put(AnimationState.JUMP,
            characters[2].characterPath + "06_Player_Cyborg_Jump_4Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png");
        characters[2].stateToAssetPath.put(AnimationState.DOUBLE_JUMP,
            characters[2].characterPath + "07_Player_Cyborg_DoubleJump_6Frames1Row_MidAirFlipBoost_SecondJump_PlayOnce_80ms.png");
        characters[2].stateToAssetPath.put(AnimationState.FALL,
            characters[2].characterPath + "08_Player_Cyborg_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png");
        characters[2].stateToAssetPath.put(AnimationState.DASH,
            characters[2].characterPath + "05_Player_Cyborg_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png");
        characters[2].stateToAssetPath.put(AnimationState.CLIMB,
            characters[2].characterPath + "09_Player_Cyborg_Climb_6Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png");
        characters[2].stateToAssetPath.put(AnimationState.HANG,
            characters[2].characterPath + "10_Player_Cyborg_Hang_3Frames1Row_LedgeHangHold_HangingIdle_Loop_150ms.png");
    }
    
    private void startAnimationCycle() {
        for (AnimatedCharacterProfile char : characters) {
            char.animationSequence = Arrays.asList(ANIMATION_CYCLE);
            char.currentAnimationState = AnimationState.IDLE;
            char.currentFrameIndex = 0;
            char.frameStartTime = System.currentTimeMillis();
        }
    }
    
    @Override
    public BufferedImage render(int width, int height) {
        BufferedImage screen = new BufferedImage(
            width, height, BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g2d = screen.createGraphics();
        
        // Background
        drawFrameBorder(g2d, width, height);
        drawTitle(g2d, width, "SELECT YOUR CHARACTER - WATCH THEM MOVE");
        
        // Character cards (left, center, right)
        int cardWidth = 256;
        int cardHeight = 384;
        int[] cardX = {180, 512, 844};
        int[] cardY = {140, 140, 140};
        
        for (int i = 0; i < 3; i++) {
            drawAnimatedCharacterCard(g2d, i, cardX[i], cardY[i], cardWidth, cardHeight);
        }
        
        // Stats panel (right side) - only if selected
        if (selectedIndex >= 0) {
            drawCharacterStats(g2d, 1100, 140, width - 1150, 400);
        }
        
        // Buttons
        drawButton(g2d, 200, 650, "← BACK", false);
        drawButton(g2d, 600, 650,
            "SELECT " + (selectedIndex >= 0 ? 
                characters[selectedIndex].characterName : "CHARACTER"),
            selectedIndex == -1);
        
        g2d.dispose();
        return screen;
    }
    
    private void drawAnimatedCharacterCard(Graphics2D g2d, int index,
        int x, int y, int w, int h) {
        AnimatedCharacterProfile char = characters[index];
        
        // Update animation frames
        updateAnimationFrame(char);
        
        // Card background
        g2d.setColor(new Color(64, 64, 64));
        g2d.fillRect(x, y, w, h);
        
        // Border (highlight if selected/hovered)
        if (selectedIndex == index) {
            g2d.setColor(new Color(255, 215, 0));  // Gold
            g2d.setStroke(new BasicStroke(4));
        } else if (hoveredIndex == index) {
            g2d.setColor(new Color(200, 200, 200));
            g2d.setStroke(new BasicStroke(2));
        } else {
            g2d.setColor(new Color(100, 100, 100));
            g2d.setStroke(new BasicStroke(1));
        }
        g2d.drawRect(x, y, w, h);
        
        // Draw current animation frame
        BufferedImage currentFrame = getCurrentAnimationFrame(char);
        if (currentFrame != null) {
            g2d.drawImage(currentFrame, x, y, w, h, null);
        }
        
        // Name label
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString(char.characterName, x + 20, y + h + 30);
    }
    
    private void updateAnimationFrame(AnimatedCharacterProfile char) {
        long currentTime = System.currentTimeMillis();
        long elapsedSinceFrame = currentTime - char.frameStartTime;
        
        AnimationState currentState = char.currentAnimationState;
        int totalDurationMs = currentState.frameCount * currentState.durationPerFrameMs;
        
        if (elapsedSinceFrame >= totalDurationMs) {
            // Move to next animation in cycle
            int currentIndex = char.animationSequence.indexOf(currentState);
            int nextIndex = (currentIndex + 1) % char.animationSequence.size();
            char.currentAnimationState = char.animationSequence.get(nextIndex);
            char.currentFrameIndex = 0;
            char.frameStartTime = currentTime;
        } else {
            // Update current frame within this animation state
            int frameIndex = (int) (elapsedSinceFrame / currentState.durationPerFrameMs);
            char.currentFrameIndex = Math.min(frameIndex, currentState.frameCount - 1);
        }
    }
    
    private BufferedImage getCurrentAnimationFrame(AnimatedCharacterProfile char) {
        String assetPath = char.stateToAssetPath.get(char.currentAnimationState);
        if (assetPath == null) return null;
        
        BufferedImage spriteSheet = loadImage(assetPath);
        if (spriteSheet == null) return null;
        
        AnimationState state = char.currentAnimationState;
        int frameWidth = spriteSheet.getWidth() / state.frameCount;
        int frameHeight = spriteSheet.getHeight();
        
        int srcX = char.currentFrameIndex * frameWidth;
        return spriteSheet.getSubimage(srcX, 0, frameWidth, frameHeight);
    }
    
    private void drawCharacterStats(Graphics2D g2d, int x, int y, int w, int h) {
        if (selectedIndex < 0) return;
        
        AnimatedCharacterProfile char = characters[selectedIndex];
        
        g2d.setColor(new Color(64, 64, 100));
        g2d.fillRect(x, y, w, h);
        g2d.setColor(new Color(150, 150, 200));
        g2d.drawRect(x, y, w, h);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospace", Font.BOLD, 12));
        int lineY = y + 30;
        int lineSpacing = 25;
        
        g2d.drawString("HP: " + char.healthMax, x + 20, lineY);
        lineY += lineSpacing;
        g2d.drawString("DMG: " + char.damageBase, x + 20, lineY);
        lineY += lineSpacing;
        g2d.drawString("SPD: " + char.speedBase, x + 20, lineY);
        lineY += lineSpacing;
        g2d.drawString("ARM: " + char.armorBase, x + 20, lineY);
        lineY += lineSpacing * 2;
        g2d.drawString("WPN: " + char.weaponType, x + 20, lineY);
        
        lineY += lineSpacing * 2;
        g2d.setFont(new Font("Arial", Font.PLAIN, 11));
        g2d.drawString(char.description, x + 20, lineY);
    }
    
    @Override
    public void handleMouseMove(int mx, int my) {
        hoveredIndex = getCharacterAtPosition(mx, my);
    }
    
    @Override
    public void handleMousePress(int mx, int my) {
        int charIndex = getCharacterAtPosition(mx, my);
        if (charIndex >= 0) {
            selectedIndex = charIndex;
        }
        
        // Check button clicks
        if (mx >= 200 && mx <= 400 && my >= 650 && my <= 700) {
            Game.setState(GameState.MAIN_MENU);
        }
        if (selectedIndex >= 0 && mx >= 600 && mx <= 900 && my >= 650 && my <= 700) {
            // Pass selected character to Game state
            Game.setSelectedCharacter(characters[selectedIndex].characterName);
            Game.setState(GameState.LEVEL_SELECT);
        }
    }
    
    private int getCharacterAtPosition(int mx, int my) {
        int[] cardX = {180, 512, 844};
        int cardY = 140;
        int cardW = 256;
        int cardH = 384;
        
        for (int i = 0; i < 3; i++) {
            if (mx >= cardX[i] && mx <= cardX[i] + cardW &&
                my >= cardY && my <= cardY + cardH) {
                return i;
            }
        }
        return -1;
    }
}
```

### Integration with Game and Level Classes

```java
// In Game.java - Store selected character
public class Game extends GameCore {
    private static String selectedCharacter = "BIKER";  // Default
    
    public static void setSelectedCharacter(String character) {
        selectedCharacter = character;  // "BIKER", "PUNK", or "CYBORG"
    }
    
    public static String getSelectedCharacter() {
        return selectedCharacter;
    }
    
    // When transitioning to game
    private void startLevel(int levelNumber) {
        if (levelNumber == 1) {
            Level1 level = new Level1(selectedCharacter);
            setGameLevel(level);
        } else if (levelNumber == 2) {
            Level2 level = new Level2(selectedCharacter);
            setGameLevel(level);
        }
    }
}

// In Level1.java - Receive and apply selected character
public class Level1 extends AnimationAndSpriteLoader {
    private String selectedCharacter;
    
    public Level1(String character) {
        super();
        this.selectedCharacter = character != null ? character : "BIKER";
        initializeLevel(this.selectedCharacter);
    }
    
    private void initializeLevel(String character) {
        System.out.println("Level 1 initialized with character: " + character);
        
        switch (character) {
            case "PUNK":
                loadCharacterAssets("Resources/industrial-zone/characters/punk/");
                player.setStats(85, 22, 16, 5);  // HP, DMG, SPD, ARM
                player.setWeapon("Sword");
                break;
            case "CYBORG":
                loadCharacterAssets("Resources/industrial-zone/characters/cyborg/");
                player.setStats(110, 12, 10, 20);
                player.setWeapon("Laser");
                break;
            case "BIKER":
            default:
                loadCharacterAssets("Resources/industrial-zone/characters/biker/");
                player.setStats(100, 18, 14, 10);
                player.setWeapon("Gun");
                break;
        }
    }
}

---

## CHARACTER SELECTION → GAMEPLAY INTEGRATION FLOW

### Complete User Journey with Animation Loop

```
MAIN MENU
    │
    ↓ [NEW GAME]
    
CHARACTER_SELECT SCREEN
    │
    ├─ BIKER Portrait: [ANIMATED LOOP]
    │  Cycling: Idle(4fr)→Walk(6fr)→Attack(5fr)→Jump(4fr)
    │           →DoubleJump(6fr)→Fall(4fr)→Dash(6fr)
    │           →Climb(6fr)→Hang(3fr)→[LOOP RESTART]
    │  HP: 100, DMG: 18, SPD: 14, ARM: 10 | Gun
    │
    ├─ PUNK Portrait: [ANIMATED LOOP]
    │  Same animation cycle as Biker
    │  HP: 85, DMG: 22, SPD: 16, ARM: 5 | Sword
    │
    ├─ CYBORG Portrait: [ANIMATED LOOP]
    │  Same animation cycle as Biker
    │  HP: 110, DMG: 12, SPD: 10, ARM: 20 | Laser
    │
    └─ User clicks portrait to SELECT
            │
            ↓
     [SELECT CHARACTER] button clicked
            │
            ↓
     Game.setSelectedCharacter("BIKER" | "PUNK" | "CYBORG")
            │
            ↓
     
LEVEL SELECT SCREEN
    │
    ├─ LEVEL 1: Industrial Zone
    ├─ LEVEL 2: Power Station
    │
    └─ User selects Level (1 or 2)
            │
            ↓
    Game.startLevel(1 or 2)
            │
            ├─ Calls: Level1(Game.getSelectedCharacter())
            │          OR
            │          Level2(Game.getSelectedCharacter())
            │
            ↓
            
GAMEPLAY STARTS
    │
    ├─ Level1 Constructor receives: "BIKER" (or PUNK/CYBORG)
    │  └─ Loads character-specific animations:
    │     ├─ Resources/industrial-zone/characters/biker/
    │     ├─ Resources/industrial-zone/characters/punk/
    │     └─ Resources/industrial-zone/characters/cyborg/
    │
    ├─ Player stats applied from Character profile:
    │  ├─ HP: 100/85/110
    │  ├─ DMG: 18/22/12
    │  ├─ SPD: 14/16/10
    │  ├─ ARM: 10/5/20
    │  └─ Weapon: Gun/Sword/Laser
    │
    └─ In-Game GUI displays all character stats
       (TopBar shows HP, Sidebar shows inventory,
        ButtonPanel shows weapon selection for that character)
```

### Data Flow Diagram - Character Selection to Level

```
CHARACTER_SELECT_SCREEN
    │
    ├── AnimatedCharacterProfile[3]
    │   ├─ [0] BIKER
    │   │   ├─ characterPath
    │   │   ├─ stateToAssetPath (Map<AnimationState, String>)
    │   │   ├─ healthMax: 100
    │   │   ├─ damageBase: 18
    │   │   ├─ speedBase: 14
    │   │   ├─ armorBase: 10
    │   │   └─ weaponType: "Gun"
    │   │
    │   ├─ [1] PUNK
    │   │   └─ (similar structure)
    │   │
    │   └─ [2] CYBORG
    │       └─ (similar structure)
    │
    └── USER CLICKS CHARACTER [5]
        │
        ├── selectedIndex = [0|1|2]
        ├── [SELECT CHARACTER] button enabled
        │
        └── USER CLICKS [SELECT CHARACTER]
            │
            └── Game.setSelectedCharacter(
                    characters[selectedIndex].characterName
                )
                │
                └── characters[selectedIndex].characterName = "BIKER"
                    │
                    └── PASSED TO LEVEL1/LEVEL2 CONSTRUCTORS
                        │
                        ├── Level1 level = new Level1("BIKER")
                        │   │
                        │   └── initializeLevel("BIKER")
                        │       └── Load all BIKER animations + stats
                        │           ├─ Load: character/biker/*.png
                        │           ├─ setStats(100, 18, 14, 10)
                        │           └─ setWeapon("Gun")
                        │
                        └── Level2 level = new Level2("PUNK")
                            │
                            └── initializeLevel("PUNK")
                                └── Load all PUNK animations + stats
                                    ├─ Load: character/punk/*.png
                                    ├─ setStats(85, 22, 16, 5)
                                    └─ setWeapon("Sword")
```

### Animation Rendering Pipeline

```
CharacterSelectScreen.render(width, height)
    │
    ├── FOR EACH CHARACTER CARD [0, 1, 2]
    │   │
    │   ├── updateAnimationFrame(character)
    │   │   │
    │   │   ├── Check elapsed time since frame start
    │   │   │
    │   │   ├── IF elapsed >= current_state_duration
    │   │   │   └── Advance to NEXT animation in cycle
    │   │   │       (IDLE → WALK → ATTACK → JUMP → ... → IDLE)
    │   │   │
    │   │   └── ELSE
    │   │       └── Calculate current frame index
    │   │           (frameIndex = elapsed / frameDurationMs)
    │   │
    │   └── getCurrentAnimationFrame(character)
    │       │
    │       ├── Load sprite sheet from characterPath + filename
    │       │   e.g., Resources/.../biker/01_Player_Biker_Idle_4Frames...
    │       │
    │       ├── Extract ONE FRAME from sprite sheet
    │       │   frameX = currentFrameIndex * (width / frameCount)
    │       │   subimage(frameX, 0, frameWidth, frameHeight)
    │       │
    │       └── Return extracted frame
    │           (Buffer this in memory for 60 FPS rendering)
    │
    └── DRAW ON SCREEN
        └── g2d.drawImage(currentFrame, cardX, cardY, cardWidth, cardHeight)
            └── Renders single extracted frame to 256×384px card slot
                (Smooth animation at 60 FPS)
```

### Key Implementation Details

**Animation State Enum:**
```java
AnimationState.IDLE      → 4 frames  @ 150ms = 600ms duration
AnimationState.WALK      → 6 frames  @ 100ms = 600ms duration
AnimationState.ATTACK    → 5 frames  @  70ms = 350ms duration
AnimationState.JUMP      → 4 frames  @  80ms = 320ms duration
AnimationState.DOUBLE_JUMP → 6 frames @ 80ms = 480ms duration
AnimationState.FALL      → 4 frames  @ 100ms = 400ms duration
AnimationState.DASH      → 6 frames  @  60ms = 360ms duration
AnimationState.CLIMB     → 6 frames  @ 120ms = 720ms duration
AnimationState.HANG      → 3 frames  @ 150ms = 450ms duration

FULL CYCLE = ~5.5-6 seconds (then loops)
```

**Character Profile Passing:**
```java
public class Game extends GameCore {
    static String selectedCharacter = "BIKER";  // Default
    
    static void setSelectedCharacter(String char) {
        selectedCharacter = char;
    }
    
    static String getSelectedCharacter() {
        return selectedCharacter;
    }
}

// Retrieve in Level class:
String character = Game.getSelectedCharacter();
```

---

## IMPLEMENTATION SUMMARY

### What Happens When User Selects Character

1. **CharacterSelectScreen displays 3 animated portraits**
   - Each portrait cycles through all 9 animation states continuously
   - Biker, Punk, and Cyborg each show their 44+ unique frames
   - Smooth 60 FPS rendering with proper frame timing

2. **User clicks a character card**
   - Card highlights with gold border
   - Stats panel updates showing character's HP, DMG, SPD, ARM
   - [SELECT CHARACTER] button becomes enabled

3. **User clicks [SELECT CHARACTER]**
   - Selected character name is saved in `Game.selectedCharacter`
   - Screen transitions to LEVEL_SELECT

4. **User selects Level 1 or Level 2**
   - Game creates: `new Level1(Game.getSelectedCharacter())`
   - Level1 constructor receives character name ("BIKER", "PUNK", or "CYBORG")

5. **Level1 Initialization**
   - `initializeLevel(character)` is called
   - Correct character folder is loaded: `Resources/industrial-zone/characters/[character]/`
   - All sprite sheets and animations are loaded for that character
   - Player stats are set correctly:
     - BIKER: 100 HP, 18 DMG, 14 SPD, 10 ARM, Gun
     - PUNK: 85 HP, 22 DMG, 16 SPD, 5 ARM, Sword
     - CYBORG: 110 HP, 12 DMG, 10 SPD, 20 ARM, Laser

6. **In-Game GUI applies character stats**
   - TopBar displays correct HP value
   - Sidebar shows character's inventory/collectibles
   - ButtonPanel displays correct weapon options
   - All gameplay reflects selected character's attributes

7. **Player plays Level 1 or 2 with that character**
   - Animations use selected character's sprite sheets
   - Stats determine damage, speed, and survivability
   - Weapon type affects attack mechanics

---

## FILE STRUCTURE AND PATHS

```
Resources/industrial-zone/characters/
├─ biker/
│  ├─ 01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png
│  ├─ 03_Player_Biker_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png
│  ├─ 05_Player_Biker_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png
│  ├─ 06_Player_Biker_Jump_4Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png
│  ├─ 07_Player_Biker_DoubleJump_6Frames1Row_MidAirFlipBoost_SecondJump_PlayOnce_80ms.png
│  ├─ 08_Player_Biker_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png
│  ├─ 09_Player_Biker_Climb_6Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png
│  ├─ 10_Player_Biker_Hang_3Frames1Row_LedgeHangHold_HangingIdle_Loop_150ms.png
│  ├─ 12_Player_Biker_Punch_5Frames1Row_StandingPunchCombo_MeleeAttack_PlayOnce_70ms.png
│  └─ [additional animations]
│
├─ punk/
│  ├─ 01_Player_Punk_Idle_4Frames1Row_...
│  ├─ 03_Player_Punk_Walk_6Frames1Row_...
│  ├─ 05_Player_Punk_Dash_6Frames1Row_...
│  ├─ 06_Player_Punk_Jump_4Frames1Row_...
│  ├─ 07_Player_Punk_DoubleJump_6Frames1Row_...
│  ├─ 08_Player_Punk_Fall_4Frames1Row_...
│  ├─ 09_Player_Punk_Climb_6Frames1Row_...
│  ├─ 10_Player_Punk_Hang_3Frames1Row_...
│  ├─ 12_Player_Punk_Slash_6Frames1Row_...
│  └─ [additional animations]
│
└─ cyborg/
   ├─ 01_Player_Cyborg_Idle_4Frames1Row_...
   ├─ 03_Player_Cyborg_Walk_6Frames1Row_...
   ├─ 05_Player_Cyborg_Dash_6Frames1Row_...
   ├─ 06_Player_Cyborg_Jump_4Frames1Row_...
   ├─ 07_Player_Cyborg_DoubleJump_6Frames1Row_...
   ├─ 08_Player_Cyborg_Fall_4Frames1Row_...
   ├─ 09_Player_Cyborg_Climb_6Frames1Row_...
   ├─ 10_Player_Cyborg_Hang_3Frames1Row_...
   ├─ 15_Player_Cyborg_LaserCharge_6Frames1Row_...
   └─ [additional animations]
```

---

---

## HOW TO PLAY - KEYBOARD & CONTROLS GUIDE

### Screen Layout with Keyboard Assets

```
┌─────────────────────────────────────────────────────────────────┐
│ ╔═════════════════════════════════════════════════════════════╗ │
│ ║     HOW TO PLAY - CONTROLS & KEYBOARD GUIDE               ║ │
│ ╠═════════════════════════════════════════════════════════════╣ │
│ ║                                                             ║ │
│ ║  MOVEMENT:                                                  ║ │
│ ║  ┌──┐ ┌──┐ ┌──┐ ┌──┐                    ┌──┐             ║ │
│ ║  │W │ │A │ │S │ │D │    Use WASD to move   │ ↑│             ║ │
│ ║  └──┘ └──┘ └──┘ └──┘                    └──┘             ║ │
│ ║           ┌─────┐                                         ║ │
│ ║           │SPACE│  Jump (hold longer for higher jump)    ║ │
│ ║           └─────┘                                         ║ │
│ ║                                                             ║ │
│ ║  COMBAT:                                                    ║ │
│ ║  ┌──────────┐                    ┌──────┐                  ║ │
│ ║  │LEFT CLICK│  Attack primary      │ 1 │ Weapon 1 (Gun)   ║ │
│ ║  └──────────┘                    └──────┘                  ║ │
│ ║                                   ┌──────┐                 ║ │
│ ║  ┌──────────────┐                │ 2 │ Weapon 2 (Sword) ║ │
│ ║  │RIGHT CLICK   │ Hold for aim     └──────┘                 ║ │
│ ║  └──────────────┘                  ┌──────┐               ║ │
│ ║                                   │ 3 │ Weapon 3 (Laser) ║ │
│ ║  ┌─────────┐                      └──────┘                │ │
│ ║  │SCROLL UP│  Reload ammo                                │ │
│ ║  └─────────┘                                             │ │
│ ║                                                             ║ │
│ ║  SPECIAL ACTIONS:                                           ║ │
│ ║  ┌──────┐                                                  ║ │
│ ║  │SHIFT │  Sprint - Move faster (short duration)         ║ │
│ ║  └──────┘                                                  ║ │
│ ║  ┌──────┐                                                  ║ │
│ ║  │ CTRL │  Crouch - Reduce visibility, move slower        ║ │
│ ║  └──────┘                                                  ║ │
│ ║  ┌────┐                                                    ║ │
│ ║  │ E  │  Interact - Open doors, collect items             ║ │
│ ║  └────┘                                                    ║ │
│ ║  ┌────┐                                                    ║ │
│ ║  │TAB │  Inventory - View collected items                 ║ │
│ ║  └────┘                                                    ║ │
│ ║                                                             ║ │
│ ║  PAUSE/MENU:                                                ║ │
│ ║  ┌────┐                                                    ║ │
│ ║  │ P  │  Pause game - Access pause menu                   ║ │
│ ║  └────┘                                                    ║ │
│ ║  ┌────┐                                                    ║ │
│ ║  │ESC │  Return to menu                                   ║ │
│ ║  └────┘                                                    ║ │
│ ║                                                             ║ │
│ ║                                                             ║ │
│ ║                      [← BACK TO MENU]                      ║ │
│ ║                                                             ║ │
│ ╚═════════════════════════════════════════════════════════════╝ │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### Asset Structure for Keyboard Display

```java
public class ControlsDisplay {
    
    // Load keyboard key assets
    private Map<String, BufferedImage> keyImages = new HashMap<>();
    
    public void loadKeyAssets() {
        String basePath = "Resources/industrial-zone/KeyBoard_Keys/";
        
        keyImages.put("W", loadImage(basePath + "key_W.png"));
        keyImages.put("A", loadImage(basePath + "key_A.png"));
        keyImages.put("S", loadImage(basePath + "key_S.png"));
        keyImages.put("D", loadImage(basePath + "key_D.png"));
        keyImages.put("Space", loadImage(basePath + "key_Space.png"));
        keyImages.put("LClick", loadImage(
            "Resources/industrial-zone/Mouse_keys/mouse_left.png"
        ));
        keyImages.put("RClick", loadImage(
            "Resources/industrial-zone/Mouse_keys/mouse_right.png"
        ));
        keyImages.put("1", loadImage(basePath + "key_1.png"));
        keyImages.put("2", loadImage(basePath + "key_2.png"));
        keyImages.put("3", loadImage(basePath + "key_3.png"));
        keyImages.put("Shift", loadImage(basePath + "key_Shift.png"));
        keyImages.put("Ctrl", loadImage(basePath + "key_Ctrl.png"));
        keyImages.put("E", loadImage(basePath + "key_E.png"));
        keyImages.put("Tab", loadImage(basePath + "key_Tab.png"));
        keyImages.put("P", loadImage(basePath + "key_P.png"));
        keyImages.put("Esc", loadImage(basePath + "key_Esc.png"));
    }
    
    public void drawControlSection(Graphics2D g2d, int x, int y,
        String title, String[] keys, String[] descriptions) {
        
        // Section title
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString(title + ":", x, y);
        
        // Key-description pairs
        int lineY = y + 30;
        int lineSpacing = 40;
        
        for (int i = 0; i < keys.length; i++) {
            // Draw key image
            BufferedImage keyImg = keyImages.get(keys[i]);
            if (keyImg != null) {
                g2d.drawImage(keyImg, x, lineY - 20, 40, 30, null);
            }
            
            // Draw description
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(descriptions[i], x + 60, lineY);
            
            lineY += lineSpacing;
        }
    }
}
```

### Implementation - HowToPlayScreen

```java
public class HowToPlayScreen extends AnimationAndSpriteLoader {
    
    private ControlsDisplay controlsDisplay;
    
    public HowToPlayScreen() {
        super();
        controlsDisplay = new ControlsDisplay();
        controlsDisplay.loadKeyAssets();
    }
    
    @Override
    public BufferedImage render(int width, int height) {
        BufferedImage screen = new BufferedImage(
            width, height, BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g2d = screen.createGraphics();
        
        // Background
        drawFrameBorder(g2d, width, height);
        
        // Title
        drawTitle(g2d, width, "HOW TO PLAY - CONTROLS");
        
        // Movement controls
        controlsDisplay.drawControlSection(g2d, 100, 150,
            "MOVEMENT",
            new String[]{"W", "A", "S", "D", "Space"},
            new String[]{
                "Move Forward",
                "Move Left",
                "Move Down/Crouch",
                "Move Right",
                "Jump (hold for height)"
            }
        );
        
        // Combat controls
        controlsDisplay.drawControlSection(g2d, 450, 150,
            "COMBAT",
            new String[]{"LClick", "RClick", "1", "2", "3"},
            new String[]{
                "Attack Primary",
                "Hold to Aim",
                "Select Weapon 1",
                "Select Weapon 2",
                "Select Weapon 3"
            }
        );
        
        // Special actions
        controlsDisplay.drawControlSection(g2d, 800, 150,
            "SPECIAL",
            new String[]{"Shift", "Ctrl", "E", "Tab", "P", "Esc"},
            new String[]{
                "Sprint (fast move)",
                "Crouch (stealth)",
                "Interact/Collect",
                "Inventory",
                "Pause",
                "Back to Menu"
            }
        );
        
        // Back button
        drawButton(g2d, 550, 650, "← BACK TO MENU", false);
        
        g2d.dispose();
        return screen;
    }
    
    @Override
    public void handleMousePress(int mx, int my) {
        // Check if back button clicked
        if (mx >= 550 && mx <= 750 && my >= 650 && my <= 700) {
            Game.setState(GameState.MAIN_MENU);
        }
    }
}
```

---

## LEVEL SELECT - DETAILED SPECIFICATION

### Visual Layout

```
┌────────────────────────────────────────────────────────────┐
│ ╔══════════════════════════════════════════════════════╗   │
│ ║     SELECT LEVEL - CHOOSE YOUR CHALLENGE            ║   │
│ ╠══════════════════════════════════════════════════════╣   │
│ ║                                                      ║   │
│ ║   LEVEL 1: INDUSTRIAL ZONE                          ║   │
│ ║   ┌──────────────────────────────────────────────┐  ║   │
│ ║   │                                              │  ║   │
│ ║   │    [LEVEL THUMBNAIL 512x256]                │  ║   │
│ ║   │                                              │  ║   │
│ ║   └──────────────────────────────────────────────┘  ║   │
│ ║                                                      ║   │
│ ║   Difficulty: ★★★                                   ║   │
│ ║   Enemies: 17    Bosses: 1                          ║   │
│ ║   Completion: 0%                                    ║   │
│ ║   Best Time: --:--                                  ║   │
│ ║                                                      ║   │
│ ║   "Infiltrate the industrial complex. Defeat the    ║   │
│ ║    security systems and reach the boss arena."     ║   │
│ ║                                                      ║   │
│ ║                                                      ║   │
│ ║   LEVEL 2: POWER STATION                            ║   │
│ ║   ┌──────────────────────────────────────────────┐  ║   │
│ ║   │                                              │  ║   │
│ ║   │    [LEVEL THUMBNAIL 512x256]                │  ║   │
│ ║   │                                              │  ║   │
│ ║   └──────────────────────────────────────────────┘  ║   │
│ ║                                                      ║   │
│ ║   Difficulty: ★★★★                                  ║   │
│ ║   Enemies: 22    Bosses: 2                          ║   │
│ ║   Completion: 0%                                    ║   │
│ ║   Best Time: --:--                                  ║   │
│ ║                                                      ║   │
│ ║   "Break through the power facility defenses.       ║   │
│ ║    Multiple bosses await in this perilous stage."   ║   │
│ ║                                                      ║   │
│ ║                                                      ║   │
│ ║   ┌───────────────────────────────────────────────┐ ║   │
│ ║   │  [← BACK]  [SELECT LEVEL]  [START]  [  >  ]  │ ║   │
│ ║   └───────────────────────────────────────────────┘ ║   │
│ ║                                                      ║   │
│ ╚══════════════════════════════════════════════════════╝   │
│                                                            │
└────────────────────────────────────────────────────────────┘
```

### Level Data Structure

```java
public class LevelProfile {
    public int levelNumber;           // 1, 2
    public String levelName;          // "Industrial Zone", "Power Station"
    public BufferedImage thumbnail;   // 512×256px preview
    public int difficulty;            // 1-5 stars
    public int enemyCount;
    public int bossCount;
    public int completionPercent;     // From saved progress
    public String bestTime;           // "MM:SS" or "--:--"
    public String description;        // Story/challenge description
    public String unlockHint;         // XP required, etc.
    public boolean isUnlocked;        // Can player select this level?
}
```

---

## IN-GAME GUI - COMPLETE 4-PANEL SYSTEM

### TopBar Panel (Health, Ammo, Level Info)

```
┌────────────────────────────────────────────────────────────┐
│ [♥ 95/100] [◆ 485]  ZONE: Industrial Start  TIME: 2:43  │
└────────────────────────────────────────────────────────────┘
```

**Components**:
- Health Icon + Value: `♥ 95/100`
- Ammo Icon + Value: `◆ 485`
- Current Zone: "Industrial Start", "Pit Gauntlet", etc.
- Elapsed Time: "2:43"

### LeftSidebar (Inventory - 4 Cards)

```
┌─────────┐
│INVENTORY│
├─────────┤
│ ┌─────┐ │
│ │     │ │  Card 1 (Rotating animation)
│ └─────┘ │
│ ┌─────┐ │
│ │  ★  │ │  Card 2 (Bonus collectible)
│ └─────┘ │
│ ┌─────┐ │
│ │     │ │  Card 3 (Empty slot)
│ └─────┘ │
│ ┌─────┐ │
│ │  ♦  │ │  Card 4 (Special item)
│ └─────┘ │
└─────────┘
```

### ButtonPanel (Right Side Actions)

```
┌───────────┐
│  [PAUSE]  │
├───────────┤
│ [WEAPONS] │
├───────────┤
│ [ WPN 1 ] │  Gun
├───────────┤
│ [ WPN 2 ] │  Sword
├───────────┤
│ [ WPN 3 ] │  Laser
├───────────┤
│[SETTINGS] │
├───────────┤
│  [HELP]   │
└───────────┘
```

### HUDBar (Bottom Status)

```
├─────────────────────────────────────────────────────────┤
│ ZONE: Industrial Start  ENEMIES: 3  AMMO: 485  FPS: 60  │
└─────────────────────────────────────────────────────────┘
```

---

## PAUSE MENU - COMPLETE

### Layout While Paused

```
┌────────────────────────────────────────────────────────┐
│                                                        │
│              *** GAME PAUSED ***                       │
│                                                        │
│                   Resume Game                          │
│                   Back to Menu                         │
│                   Settings                             │
│                   Help                                 │
│                   Quit to Desktop                      │
│                                                        │
│    (Background dimmed/blurred with gameplay behind)    │
│                                                        │
└────────────────────────────────────────────────────────┘
```

---

## GAME OVER & LEVEL COMPLETE

### Game Over Screen

```
┌──────────────────────────────────────┐
│       ✗ GAME OVER ✗                  │
│                                      │
│   YOU WERE DEFEATED                  │
│                                      │
│   Final Score: 12,500                │
│   Zone Reached: Pit Gauntlet (Lv 1)  │
│   Time: 3:45                         │
│   Enemies Defeated: 8                │
│                                      │
│   [RETRY LEVEL]                      │
│   [RETURN TO MENU]                   │
│                                      │
└──────────────────────────────────────┘
```

### Level Complete Screen

```
┌──────────────────────────────────────┐
│    ✓ LEVEL COMPLETE ✓                │
│                                      │
│   You conquered Industrial Zone!     │
│                                      │
│   +=== FINAL SCORE ===+              │
│   Base:         10,000               │
│   Speed Bonus:  +2,500               │
│   No Damage:    +5,000               │
│   ─────────────────────              │
│   TOTAL:        17,500               │
│                                      │
│   Time: 8:34                         │
│   Enemies: 17/17 Defeated            │
│   Completion: 100%                   │
│                                      │
│   [NEXT LEVEL]                       │
│   [LEVEL SELECT]                     │
│   [RETURN TO MENU]                   │
│                                      │
└──────────────────────────────────────┘
```

---

## SETTINGS SCREEN - OPTIONS

### Layout

```
┌────────────────────────────────────────┐
│        GAME SETTINGS                   │
├────────────────────────────────────────┤
│                                        │
│  AUDIO:                                │
│  ┌─ Master Volume: [═══════════ ] 75% │
│  ├─ Music Volume:  [═════════   ] 60% │
│  └─ SFX Volume:    [═══════════ ] 80% │
│                                        │
│  GRAPHICS:                             │
│  ├─ Resolution: 1280x720 ▼            │
│  ├─ Fullscreen: [✓] On                │
│  ├─ VSync: [✓] On                     │
│  └─ Frame Limit: 60 FPS ▼             │
│                                        │
│  GAMEPLAY:                             │
│  ├─ Difficulty: Normal ▼              │
│  ├─ Show Hints: [✓] On                │
│  └─ Colorblind Mode: [ ] Off          │
│                                        │
│  ┌────────────────────────────────┐   │
│  │ [APPLY]  [CANCEL]  [DEFAULTS]  │   │
│  └────────────────────────────────┘   │
│                                        │
└────────────────────────────────────────┘
```

---

## FRAME SPECIFICATIONS & DIMENSIONS

### Standard Resolutions

```
┌──────────────────────────────────────────────┐
│ RESOLUTION STANDARDS                         │
├──────────────────────────────────────────────┤
│ 1280 × 720  (Default)                        │
│ 1600 × 900  (Widescreen)                     │
│ 1920 × 1080 (Full HD)                        │
│ 2560 × 1440 (QHD)                            │
│                                              │
│ Scaling: Maintain aspect ratio, scale UI    │
│ Assets loaded at native and scaled sizes    │
└──────────────────────────────────────────────┘
```

### Frame Border Specifications

```
┌─────────────────────────────────────────┐
│ THEME-BASED FRAME SYSTEM (82 tiles)    │
├─────────────────────────────────────────┤
│                                         │
│ Each theme contains 82 tile pieces:    │
│ - 4 Corners (top-left, top-right, etc) │
│ - 4 Edges (top, bottom, left, right)   │
│ - 1 Fill (center/background)           │
│ - 1 Transparent variant                │
│ - Pattern variations × themes           │
│                                         │
│ Tile Size: 128×128 pixels               │
│ Total Frame Area: 9×7 grid = 1152×896px│
│                                         │
│ Themes (7 total):                       │
│ 1. Industrial Blue (default)            │
│ 2. Tech Purple                          │
│ 3. Dark Red                             │
│ 4. Neon Green                           │
│ 5. Orange Steel                         │
│ 6. Cyan Metal                           │
│ 7. Gray Concrete                        │
│                                         │
└─────────────────────────────────────────┘
```

### Button Dimensions

```
Standard Button: 320×64px
Weapon Buttons: 72×72px
Menu Buttons: 400×80px
Card Slots: 128×128px (each)
Character Portrait: 256×384px
```

---

## IMPLEMENTATION PHASES - REVISED

### Phase 1: Infrastructure & Assets
**Duration**: 2-3 hours
- Create GUIAssetManager (centralized asset loading & caching)
- Verify all asset paths from Resources folder
- Create asset cache system with memory cleanup
- Test image loading pipeline
- **Deliverable**: Confirmed asset loading working, verified no vector graphics

### Phase 2: Core Components (In-Game)
**Duration**: 3-4 hours
- Implement TopBarPanel (health, ammo, zone, time)
- Implement LeftSidebar (inventory cards with animation)
- Implement ButtonPanel (weapon selection, pause, etc.)
- Implement HUDBar (zone info, status display)
- **Deliverable**: All 4 panels independently renderable & responsive

### Phase 3: Menu Screens
**Duration**: 4-5 hours
- Implement IntroScreen (splash with auto-advance)
- Implement MainMenuScreen (with frame system)
- Implement CharacterSelectScreen (3 characters with stats)
- Implement HowToPlayScreen (keyboard assets display)
- Implement LevelSelectScreen (2 levels with info)
- **Deliverable**: All menu screens complete & usable

### Phase 4: Overlay Screens
**Duration**: 2-3 hours
- Implement PauseMenuScreen
- Implement GameOverScreen
- Implement LevelCompleteScreen
- Implement SettingsScreen
- **Deliverable**: All overlay screens functional

### Phase 5: Input & Integration
**Duration**: 2-3 hours
- Wire ButtonPanel input to weapon selection
- Wire MenuScreen buttons to state transitions
- Implement character selection persistence
- Test all state transitions
- **Deliverable**: Complete input flow from buttons to game actions

### Phase 6: Animation & Polish
**Duration**: 3-4 hours
- Add button hover animations (state transitions)
- Add card flip animation (inventory)
- Add health bar smooth transitions
- Add score counter animation
- Add screen fade transitions
- **Deliverable**: Professional-looking, smooth animations at 60 FPS

### Phase 7: Testing & Optimization
**Duration**: 2-3 hours
- Performance profiling (target 60 FPS)
- Memory profiling (target <100MB GUI)
- Asset loading optimization
- Memory cleanup on transitions
- Null checks on all asset paths
- **Deliverable**: Optimized, tested, production-ready GUI system

---

## SUMMARY

This comprehensive plan covers:

✅ **9 Major Screens**: Intro, Menu, Character Select, How to Play, Level Select, Game, Pause, Game Over, Level Complete, Settings
✅ **4 In-Game Panels**: TopBar, LeftSidebar, ButtonPanel, HUDBar
✅ **3 Complete Characters**: Biker, Punk, Cyborg (with full stat systems)
✅ **Keyboard Integration**: Display actual key assets for controls
✅ **Professional Framing**: 82-tile frame system with 7 themes
✅ **Pure Asset-Based**: ZERO vector graphics, 100% image rendering
✅ **State Machine**: All transitions mapped and documented
✅ **Implementation Phases**: 7 detailed phases from infrastructure to polish

### Key Files to Reference
- `GUI_ARCHITECTURE_DIAGRAMS.md` - Visual specifications
- `GUI_QUICK_REFERENCE.md` - Code templates & best practices
- Resources folder: `gui/`, `KeyBoard_Keys/`, `Mouse_keys/`, `characters/`

### Never Violate
- ❌ NO Graphics2D drawing primitives (fillRect, drawString, etc.)
- ✅ ONLY BufferedImage composition
- ✅ All assets from Resources folder
- ✅ Extend AnimationAndSpriteLoader for everything
