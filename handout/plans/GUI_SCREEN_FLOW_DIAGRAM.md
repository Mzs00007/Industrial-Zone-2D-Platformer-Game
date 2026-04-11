# GUI SCREEN FLOW & STATE DIAGRAM

**Visual Guide**: Complete GUI navigation and state transitions  
**Reference**: Use with `GUI_COMPLETE_STATE_PLAN.md` for implementation details

---

## SCREEN STATE MACHINE

```
┌─────────────────────────────────────────────────────────────────────┐
│                       GAME GUI STATE MACHINE                         │
└─────────────────────────────────────────────────────────────────────┘

                          ┌─────────────────┐
                          │  INITIALIZATION │
                          │  (Load Assets)  │
                          └────────┬────────┘
                                   │
                                   ▼
                            ┌──────────────┐
                            │ MAIN_MENU    │
                  ┌─────────▶│ (Start Game) │◀─┐
                  │          │ (Settings)*  │  │
                  │          │ (Quit)       │  │
                  │          └──────┬───────┘  │
                  │                 │          │
                  │        [Play]   │          │
                  │                 ▼          │
                  │          ┌──────────────┐  │
                  │          │LEVEL_SELECT  ├──┤
                  │          │(Lv1, Lv2)   │  │
                  │          │(Difficulty) │  │
                  │          └──────┬───────┘  │
                  │                 │          │
                  │       [Select]   │          │
                  │                 ▼          │
                  │        ┌──────────────────┐│
                  │        │CHARACTER_SELECT  ││
                  │        │(Biker/Punk       ││
                  │        │ Cyborg Preview)  ││
                  │        │(Idle Animation)  ││
                  │        └──────┬───────────┘│
                  │               │            │
                  │     [Confirm]  │            │
                  │               ▼            │
               ┌──┴─────────────────────────┐  │
               │      GAMEPLAY              │  │
               │                            │  │
               │  ┌─ LEVEL1_SCREEN ────┐   │  │
               │  │ ├─ Map Rendering   │   │  │
               │  │ ├─ HUD (Bars)      │   │  │
               │  │ ├─ Enemy AI        │   │  │
               │  │ ├─ Player Control  │   │  │
               │  │ └─ [P] to Pause    │   │  │
               │  └────────┬───────────┘   │  │
               │           │               │  │
               │  ┌─ LEVEL2_SCREEN ────┐   │  │
               │  │ ├─ Map Rendering   │   │  │
               │  │ ├─ HUD (Bars)      │   │  │
               │  │ ├─ Enemy AI        │   │  │
               │  │ └─ [P] to Pause    │   │  │
               │  └────────┬───────────┘   │  │
               │           │               │  │
               │           │    [Esc/P]    │  │
               │           ▼               │  │
               │    ┌──────────────┐       │  │
               │    │ PAUSE_OVERLAY│       │  │
               │    ├─ Resume Game │       │  │
               │    ├─ Settings *  │    ┌──┤
               │    └─ Main Menu   ├────┘  │
               │                  │        │
               │        [Resume]   │        │
               │                   ▼        │
               │            [Continue]     │
               │                           │
               │    [Win/Lose] ──────┐     │
               │                     ▼     │
               │              ┌──────────┐ │
               │              │GAME_OVER │ │
               │              ├─ Retry   │ │
               │              └─ Main    │ │
               │                Menu     │ │
               │                     │    │
               └─────────────────────┴────┘

               * = Future implementation (Settings Screen)
```

---

## DETAILED SCREEN SPECIFICATIONS

### 1. MAIN_MENU_SCREEN

```
┌──────────────────────────────────────────────┐
│                                              │
│  ╔════════════════════════════════════════╗  │
│  ║                                        ║  │
│  ║      INDUSTRIAL ZONE GAME              ║  │
│  ║      [LOGO IMAGE 300×150]              ║  │
│  ║                                        ║  │
│  ║     (Future: parallax background)      ║  │
│  ║                                        ║  │
│  ╚════════════════════════════════════════╝  │
│                                              │
│                                              │
│          ╔═══════════════════╗               │
│          ║   START GAME      ║               │
│          ╚═══════════════════╝               │
│                                              │
│          ╔═══════════════════╗               │
│          ║   SETTINGS        ║               │
│          ╚═══════════════════╝ (Future)      │
│                                              │
│          ╔═══════════════════╗               │
│          ║   QUIT GAME       ║               │
│          ╚═══════════════════╝               │
│                                              │
└──────────────────────────────────────────────┘

ASSETS:
├─ Frame tiles: #38, #40, #65-68 (fill variants)
├─ Logo: GUI_Logo_IndustrialZone_Full.png
├─ Button frames: FrameTiler.buildPanelFrame(200, 60)
└─ Animations (future):
   └─ Character idle preview (random char)
```

### 2. LEVEL_SELECT_SCREEN

```
┌──────────────────────────────────────────────┐
│                                              │
│  ╔════════════════════════════════════════╗  │
│  ║  SELECT YOUR LEVEL                     ║  │
│  ╚════════════════════════════════════════╝  │
│                                              │
│                                              │
│  ╔══════════╗  ╔══════════╗  ╔══════════╗   │
│  ║ LEVEL 1  ║  ║ LEVEL 2  ║  ║ LEVEL 3  ║   │
│  ║Industrial║  ║ Energy   ║  ║  Sector  ║   │
│  ║Zone      ║  ║ Core     ║  ║    3     ║   │
│  ║⚔ MEDIUM  ║  ║⚔⚔ HARD   ║  ║⚔⚔ HARD   ║   │
│  ╚══════════╝  ╚══════════╝  ╚══════════╝   │
│                                              │
│   ╔══════════════════════════════════════╗   │
│   ║ LEVEL 1 - INDUSTRIAL ZONE            ║   │
│   ║ Difficulty: ⚔ MEDIUM                 ║   │
│   ║ Enemies: 15                           ║   │
│   ║ Reward: 500 XP                        ║   │
│   ║ Description: Industrial robot zone    ║   │
│   ║ with basic security systems.          ║   │
│   ╚══════════════════════════════════════╝   │
│                                              │
└──────────────────────────────────────────────┘

ASSETS:
├─ Frame tiles: Main + buttons
├─ Level info panel: FrameTiler.buildFrame(700, 200)
├─ Difficulty icons:
│  ├─ Easy: GUI_Icon_Shield_Defense_23.png
│  └─ Hard: GUI_Icon_Sword_Attack_24.png
└─ Level button tiles: FrameTiler.buildFrame(180, 100) ×3
```

### 3. CHARACTER_SELECT_SCREEN

```
┌──────────────────────────────────────────────┐
│                                              │
│  ╔════════════════════════════════════════╗  │
│  ║  SELECT YOUR CHARACTER                 ║  │
│  ╚════════════════════════════════════════╝  │
│                                              │
│                                              │
│  ╔═══════════╗   ╔═══════════╗   ╔════════╗  │
│  ║           ║   ║           ║   ║        ║  │
│  ║  [ANIM]   ║   ║  [ANIM]   ║   ║[ANIM]  ║  │
│  ║  5 frames ║   ║  5 frames ║   ║4 frames║  │
│  ║ breathing ║   ║ breathing ║   ║standing║  │
│  ║    150ms  ║   ║    150ms  ║   ║ 150ms  ║  │
│  ║           ║   ║           ║   ║        ║  │
│  ║  BIKER    ║   ║   PUNK    ║   ║CYBORG  ║  │
│  ╚═══════════╝   ╚═══════════╝   ╚════════╝  │
│     ◄━━━━ SELECTED (gold highlight)       │
│                                              │
│  Use Arrow Keys to Select | Press ENTER     │
│  to Confirm                                  │
│                                              │
└──────────────────────────────────────────────┘

ASSETS:
├─ Frame tiles: Main background
├─ Character cards: FrameTiler.buildCardFrame(180, 250, selected)
├─ Idle animations (CRITICAL):
│  ├─ Biker: 5 frames horizontal @ 150ms
│  ├─ Punk: 5 frames horizontal @ 150ms
│  └─ Cyborg: 4 frames horizontal @ 150ms
└─ Selection highlight: Gold border + text label

ANIMATION DETAILS:
├─ Biker breathing loop: Frame0→1→2→3→4→0 (750ms total)
├─ Punk breathing loop: Frame0→1→2→3→4→0 (750ms total)
└─ Cyborg idle loop: Frame0→1→2→3→0 (600ms total)
```

### 4. GAMEPLAY_SCREEN_HUD

```
┌──────────────────────────────────────────────┐
│ Health: ▓▓▓▓▓░░░░░ 50%    Score:  5250      │  ◄─ HUD Bar
│ Energy: ▓▓▓▓░░░░░░ 40%    Level:  1         │
│                                              │
│  ┌────────────────────────────────┐         │
│  │  [Level 1 - Industrial Zone]    │         │
│  │  Enemies Defeated: 7/15         │         │
│  │  Objective: Reach the Core      │         │
│  └────────────────────────────────┘         │
│                                              │
│ ┌─────────────────────────────────────────┐ │
│ │                                         │ │
│ │           [GAMEPLAY MAP HERE]           │ │
│ │                                         │ │
│ │  [Player]  [Enemies]  [Obstacles]      │ │
│ │  (center)                               │ │
│ │                                         │ │
│ │                                         │ │
│ │                                         │ │
│ └─────────────────────────────────────────┘ │
│                                              │
│ ◄ Skill 1 ► ◄ Skill 2 ► (Ability Icons)   │
│                                              │
│            Press [P] to Pause               │
└──────────────────────────────────────────────┘

HUD ASSETS:
├─ Health Bar (70% variant):
│  └─ File: 02_GUI_Bar_HealthBar_80pct_RedOrangeFill_HUD.png
├─ Energy Bar (40% variant):
│  └─ File: 12_GUI_Bar_EnergyBar_40pct_BlueCyanFill_HUD.png
├─ Level Info Panel: FrameTiler.buildPanelFrame(400, 80)
├─ Score Display Numbers (bitmap):
│  └─ Rendered using GUI_Number_Digit{0-9}_*.png
├─ Skill Icons (4):
│  └─ From gui/9 Other/2 Skill icons/
└─ [Future VFX]: Damage numbers, hit flashes, environmental effects
```

### 5. PAUSE_OVERLAY

```
                          ╔╗
                          ║║ (Dim Background 50% alpha)
                          ╚╝

                    ╔═══════════════════╗
                    ║  ▲ PAUSED ▲       ║
                    ╠═══════════════════╣
                    ║                   ║
                    ║  ╔═══════════╗    ║
                    ║  ║  ▶ RESUME ║    ║
                    ║  ║  GAME     ║    ║
                    ║  ╚═══════════╝    ║
                    ║                   ║
                    ║  ╔═══════════╗    ║
                    ║  ║ ⚙ SETTINGS║    ║
                    ║  ║           ║    ║ (Future)
                    ║  ╚═══════════╝    ║
                    ║                   ║
                    ║  ╔═══════════╗    ║
                    ║  ║ ⌂ MAIN     ║    ║
                    ║  ║ MENU       ║    ║
                    ║  ╚═══════════╝    ║
                    ║                   ║
                    ║  [ESC] to Resume  ║
                    ║  [ENTER] Selection║
                    ╚═══════════════════╝

PAUSE MENU ASSETS:
├─ Overlay dim: Black rect with 50% alpha
├─ Panel frame: FrameTiler.buildFrame(400, 300)
├─ Title frame: FrameTiler.buildFrame(380, 70)
├─ Buttons: FrameTiler.buildPanelFrame(150, 50) ×3
├─ Icons:
│  ├─ Resume: GUI_Icon_Play_Start_33.png
│  ├─ Settings: GUI_Icon_Settings_Gear_10.png (future)
│  └─ Menu: GUI_Icon_Home_House_12.png
└─ Text: Rendered labels
```

### 6. GAME_OVER_SCREEN

```
┌──────────────────────────────────────────────┐
│                                              │
│    ╔════════════════════════════════════╗   │
│    ║                                    ║   │
│    ║          ✗ GAME OVER ✗             ║   │
│    ║                                    ║   │
│    ║  You were defeated by enemy AI.    ║   │
│    ║                                    ║   │
│    ║  Final Score: 3,750                ║   │
│    ║  Enemies Defeated: 12/15           ║   │
│    ║  Survival Time: 4:32               ║   │
│    ║                                    ║   │
│    ║  ╔═════════════════╗               ║   │
│    ║  ║  ↻ RETRY LEVEL  ║               ║   │
│    ║  ╚═════════════════╝               ║   │
│    ║                                    ║   │
│    ║  ╔═════════════════╗               ║   │
│    ║  ║  ⌂ MAIN MENU    ║               ║   │
│    ║  ╚═════════════════╝               ║   │
│    ║                                    ║   │
│    ╚════════════════════════════════════╝   │
│                                              │
└──────────────────────────────────────────────┘

ASSETS:
├─ Main frame: FrameTiler.buildFrame(700, 400)
├─ Score display: Number digits (bitmap)
├─ Button frames: FrameTiler.buildPanelFrame(150, 50) ×2
├─ Icons:
│  ├─ Retry: GUI_Icon_Refresh_Reload_32.png
│  └─ Menu: GUI_Icon_Home_House_12.png
└─ Stats text: Rendered dynamic labels
```

---

## ASSET USAGE MATRIX

| Screen | Frames | Bars | Icons | Numbers | Char Anim | Buttons |
|--------|--------|------|-------|---------|-----------|---------|
| MainMenu | ✓ | - | - | - | ◐ (future) | ✓ |
| LevelSelect | ✓ | - | ◐ (difficulty) | - | - | ✓ |
| CharSelect | ✓ | - | - | - | ✓ CRITICAL | ✓ |
| Gameplay HUD | ✓ | ✓ CRITICAL | ✓ | ✓ | - | - |
| Pause | ✓ | - | ✓ | - | - | ✓ |
| GameOver | ✓ | - | ✓ | ✓ | - | ✓ |

**Legend**: ✓ = Required | ◐ = Partial/Optional | - = Not used

---

## IMPLEMENTATION SEQUENCE

### COMPLETED (GREEN ✓):
```
✓ Phase 1: Core Frame System
  ├─ FrameTiler (frame assembly)
  ├─ GUIAssetManager (image caching)
  ├─ MainMenuScreen (PNG rendering)
  └─ LevelSelectScreen (PNG rendering)
```

### IN PROGRESS (YELLOW ⚠):
```
⚠ Phase 2: Character Animations (NEXT)
  ├─ AnimationController base class
  ├─ CharacterIdleAnimationLoader
  ├─ HorizontalSpritesheetLoader integration
  └─ CharacterSelectScreen with idle animations
```

### BLOCKED/WAITING (RED ✗):
```
⚠ Phase 3: HUD Status Bars
  ├─ Bar rendering variants
  ├─ Percentage interpolation
  └─ Level1/Level2 HUD integration

⚠ Phase 4: Numbers & Score Display
⚠ Phase 5: UI Polish & Effects

*** Future Features (Last Priority) ***
- Settings screen
- Parallax background
- Button hover effects
- Decorative animations
- VFX system
```

---

## QUICK REFERENCE: WHICH ASSETS GO WHERE

### FrameTiler Calls:
```
buildFrame(width, height)          → Main backgrounds, panels
buildCardFrame(w, h, selected)     → Character cards with highlight
buildPanelFrame(width, height)     → Buttons, small panels
```

### Loader Classes:
```
HorizontalSpritesheetLoader        → Character animations
SingleSpriteLoader                 → Static images (logo, bars, icons)
VerticalSpritesheetLoader          → Vertical animations (future)
GridSpritesheetLoader              → 2D grid animations (future)
```

### Asset Directories:
```
1 Frames/                          → Window/panel construction
2 Bars/                            → Health/Energy status display
3 Icons/Buttons2                   → Button hover states (future)
3 Icons/Icons                      → UI action icons
5 Logo/                            → Game title logo
6 Buttons/                         → Button base shapes
7 Numbers/                         → HUD score display
8 Cursors/                         → Mouse pointers (future)
9 Other/1 Decor                    → GUI decorations (future)
9 Other/2 Skill icons              → Ability/inventory icons
characters/player/{char}/          → Character animations
```

---

## NEXT STEPS

**Go to**: `PHASE_2_CHARACTER_IDLE_IMPLEMENTATION.md`  
**For**: Detailed code walkthrough and implementation steps  
**Duration**: 1-2 hours to implement and test

