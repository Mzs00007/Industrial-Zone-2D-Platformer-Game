# VISUAL GUI MOCKUP DIAGRAMS

## SCREEN 1: MAIN MENU (1280x720)

```
╔════════════════════════════════════════════════════════════════════════════╗
║                           MAIN MENU SCREEN                                ║
╠════════════════════════════════════════════════════════════════════════════╣
║                                                                            ║
║  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓   ║
║  ┃                                                                  ┃   ║
║  ┃  ┌────────────────────────────────────────────────────────────┐ ┃   ║
║  ┃  │                 [MAIN LOGO IMAGE]                         │ ┃   ║
║  ┃  │         From: GUI_LOGO/ (centered, 80% width)            │ ┃   ║
║  ┃  │         Y: 30px | Size: ~1000x80px                       │ ┃   ║
║  ┃  └────────────────────────────────────────────────────────────┘ ┃   ║
║  ┃                                                                  ┃   ║
║  ┃       ┌────────────────────┐         ┌────────────────────┐   ┃   ║
║  ┃       │  [PLAYER SPRITE]   │         │  [ENEMY SPRITE]    │   ┃   ║
║  ┃       │   64x64 or larger  │         │   64x64 or larger  │   ┃   ║
║  ┃       │   PLAYER_BASE/     │         │   ENEMY_BASE/      │   ┃   ║
║  ┃       │                    │         │                    │   ┃   ║
║  ┃       │  X: 40             │         │  X: 1180           │   ┃   ║
║  ┃       │  Y: 150            │         │  Y: 150            │   ┃   ║
║  ┃       └────────────────────┘         └────────────────────┘   ┃   ║
║  ┃                                                                  ┃   ║
║  ┃                                                                  ┃   ║
║  ┃         ┌──────────────────┐    ┌──────────────────┐           ┃   ║
║  ┃         │   [START GAME]   │    │   [OPTIONS]      │           ┃   ║
║  ┃         │  GUI_BUTTONS/ +  │    │  GUI_BUTTONS/ +  │           ┃   ║
║  ┃         │ GUI_FONT_IMAGES/ │    │ GUI_FONT_IMAGES/ │           ┃   ║
║  ┃         │ X: 400, Y: 380   │    │ X: 820, Y: 380   │           ┃   ║
║  ┃         │ W: 150, H: 50    │    │ W: 150, H: 50    │           ┃   ║
║  ┃         └──────────────────┘    └──────────────────┘           ┃   ║
║  ┃                                                                  ┃   ║
║  ┃         P  R  E  S  S    S  P  A  C  E    T  O    S  T  A  R  T ┃   ║
║  ┃         (GUI_FONT_IMAGES/ - character by character rendering) ┃   ║
║  ┃         Y: 480                                                  ┃   ║
║  ┃                                                                  ┃   ║
║  ┃              P  R  E  S  S    E  S  C    F  O  R    O  P  T  S  ┃   ║
║  ┃              Y: 510                                              ┃   ║
║  ┃                                                                  ┃   ║
║  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛   ║
║                                                                            ║
║  BACKGROUND: Either                                                       ║
║  1) GUI_FRAMES/ tiled to create frame border + panel fill center        ║
║  2) L1_BG_BASE/ background image tiled across full screen              ║
║                                                                            ║
╚════════════════════════════════════════════════════════════════════════════╝
```

---

## SCREEN 2: IN-GAME HUD (1280x720) - FULL LAYOUT

```
╔════════════════════════════════════════════════════════════════════════════╗
║ L V L 1        S  C  O  R  E: 0850  pts                                   ║
║ (GUI_FONT_IMAGES + GUI_NUMBERS)                                           ║
╠════════════════════════════════════════════════════════════════════════════╣
║                                                                            ║
║  ┌───────────────────────────────────────────────────────────────────┐   ║
║  │                      GAME PLAY AREA                              │   ║
║  │                                                                   │   ║
║  │  ┌─────┐  ┌─────┐  ┌─────┐                        ┌─────────┐  │   ║
║  │  │Tile │  │Tile │  │Tile │   ...         ....     │[ENEMY]  │  │   ║
║  │  └─────┘  └─────┘  └─────┘                        │  64x64  │  │   ║
║  │    (Tiles from L1_TILES_BASE or L2_TILES_BASE)    └─────────┘  │   ║
║  │                                                                   │   ║
║  │                  ┌─────────────┐                                 │   ║
║  │                  │  [PLAYER]   │         ●●●●●●●●●               │   ║
║  │                  │ 64x64 center│      (WEAPON_1_BULLETS)        │   ║
║  │                  │PLAYER_BASE/ │                                 │   ║
║  │                  └─────────────┘                                 │   ║
║  │                                                                   │   ║
║  │ ═════════════════════════════════════════════════════════════  │   ║
║  │ [GROUND - Tile row, 64px height]                              │   ║
║  │ ═════════════════════════════════════════════════════════════  │   ║
║  │                                                                   │   ║
║  └───────────────────────────────────────────────────────────────────┘   ║
║                                                                            ║
║  ┌──────────────────────────────────────────────────────────────────┐   ║
║  │ [HUD PANEL] - GUI_FRAMES/ frame border assembly               │   ║
║  │                                                                │   ║
║  │ HP: XX/100  │ EN: XX/100  │ AMMO: XX/30  │  WPN 1          │   ║
║  │ ┌─────────┐ │ ┌─────────┐ │              │  [Icon]         │   ║
║  │ │███░░░░░│ │ │███░░░░░│ │              │  SPACE: FIRE    │   ║
║  │ └─────────┘ │ └─────────┘ │              │  ESC: MENU      │   ║
║  │ (GUI_BARS/) │ (GUI_BARS/) │              │  (KEYBOARD_     │   ║
║  │             │             │              │   KEYS/)        │   ║
║  │ GUI_FONT_IMAGES text + GUI_NUMBERS digits                  │   ║
║  │                                                                │   ║
║  └──────────────────────────────────────────────────────────────────┘   ║
║                                                                            ║
║  UI Element Positions:                                                   ║
║  ├─ Health Bar: X=20, Y=630, W=200, H=20 (GUI_BARS/)                  ║
║  ├─ Energy Bar: X=20, Y=660, W=200, H=20 (GUI_BARS/)                  ║
║  ├─ Ammo Text: X=400, Y=650 (GUI_FONT_IMAGES + GUI_NUMBERS/)          ║
║  ├─ Weapon Icon: X=800, Y=640, 40x40 (WEAPON_1_EFFECTS/)              ║
║  ├─ Key Display: X=900, Y=630 (KEYBOARD_KEYS/)                        ║
║  └─ Score: X=1000, Y=20 (GUI_NUMBERS/)                                ║
║                                                                            ║
╚════════════════════════════════════════════════════════════════════════════╝
```

---

## SCREEN 3: VICTORY SCREEN (1280x720)

```
╔════════════════════════════════════════════════════════════════════════════╗
║                                                                            ║
║                  [IN-GAME SCENE UNDERNEATH - SEMI-TRANSPARENT]            ║
║                                                                            ║
║      ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓      ║
║      ┃  ┌──────────────────────────────────────────────────────┐  ┃      ║
║      ┃  │                                                      │  ┃      ║
║      ┃  │     L E V E L    C O M P L E T E !                 │  ┃      ║
║      ┃  │  (GUI_FONT_IMAGES/) --- [DECORATIVE FRAME]         │  ┃      ║
║      ┃  │                    (GUI_FRAMES/)                    │  ┃      ║
║      ┃  │                                                      │  ┃      ║
║      ┃  └──────────────────────────────────────────────────────┘  ┃      ║
║      ┃                                                             ┃      ║
║      ┃  ┌──────────────────────────────────────────────────────┐  ┃      ║
║      ┃  │                                                      │  ┃      ║
║      ┃  │           STATISTICS                               │  ┃      ║
║      ┃  │           ─────────────────────────────           │  ┃      ║
║      ┃  │                                                      │  ┃      ║
║      ┃  │  Enemies Defeated:  5 / 5                          │  ┃      ║
║      ┃  │  Final Score:       2450 points                    │  ┃      ║
║      ┃  │  Accuracy:          78 %                           │  ┃      ║
║      ┃  │                                                      │  ┃      ║
║      ┃  │  (All text from GUI_FONT_IMAGES/)                  │  ┃      ║
║      ┃  │  (All numbers from GUI_NUMBERS/ or font)           │  ┃      ║
║      ┃  │                                                      │  ┃      ║
║      ┃  └──────────────────────────────────────────────────────┘  ┃      ║
║      ┃                                                             ┃      ║
║      ┃              ┌──────────────────────────┐                  ┃      ║
║      ┃              │  PRESS ESC FOR LEVEL 2  │                  ┃      ║
║      ┃              │  (GUI_BUTTONS/)         │                  ┃      ║
║      ┃              └──────────────────────────┘                  ┃      ║
║      ┃                                                             ┃      ║
║      ┃                    ┌──────────────┐                         ┃      ║
║      ┃                    │ [ENEMY IMG]  │                         ┃      ║
║      ┃                    │   defeated   │                         ┃      ║
║      ┃                    │  80x80 px    │                         ┃      ║
║      ┃                    │ ENEMY_BASE/  │                         ┃      ║
║      ┃                    └──────────────┘                         ┃      ║
║      ┃                                                             ┃      ║
║      ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛      ║
║                                                                            ║
║  OVERLAY BACKGROUND: Semi-transparent dark panel from GUI_PALETTE/       ║
║  BORDER: Frame construction from GUI_FRAMES/ (corners + edges)           ║
║                                                                            ║
╚════════════════════════════════════════════════════════════════════════════╝
```

---

## SCREEN 4: GAME OVER SCREEN (1280x720)

```
╔════════════════════════════════════════════════════════════════════════════╗
║                          [RED TINT OVERLAY]                              ║
║                      From GUI_PALETTE/ colors                            ║
║                                                                            ║
║      ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓      ║
║      ┃  ┌──────────────────────────────────────────────────────┐  ┃      ║
║      ┃  │                                                      │  ┃      ║
║      ┃  │        G A M E   O V E R                           │  ┃      ║
║      ┃  │     (GUI_FONT_IMAGES/) - Large Red Banner          │  ┃      ║
║      ┃  │                    (GUI_FRAMES/)                    │  ┃      ║
║      ┃  │                                                      │  ┃      ║
║      ┃  └──────────────────────────────────────────────────────┘  ┃      ║
║      ┃                                                             ┃      ║
║      ┃           H E A L T H    D E P L E T E D                   ┃      ║
║      ┃           (GUI_FONT_IMAGES/) - Failure reason              ┃      ┃
║      ┃                                                             ┃      ║
║      ┃  ┌──────────────────────────────────────────────────────┐  ┃      ║
║      ┃  │                                                      │  ┃      ║
║      ┃  │  Final Statistics:                                 │  ┃      ║
║      ┃  │  ──────────────────────────────                    │  ┃      ║
║      ┃  │                                                      │  ┃      ║
║      ┃  │  Enemies Defeated:  3 / 5                          │  ┃      ║
║      ┃  │  Final Score:       1200 points                    │  ┃      ║
║      ┃  │  Wave Level:        2 / 10                         │  ┃      ║
║      ┃  │                                                      │  ┃      ║
║      ┃  └──────────────────────────────────────────────────────┘  ┃      ║
║      ┃                                                             ┃      ║
║      ┃              ┌──────────────────────────┐                  ┃      ║
║      ┃              │  PRESS ESC TO RESTART   │                  ┃      ║
║      ┃              │  (GUI_BUTTONS/)         │                  ┃      ║
║      ┃              └──────────────────────────┘                  ┃      ║
║      ┃                                                             ┃      ║
║      ┃                  ┌──────────────┐                           ┃      ║
║      ┃                  │ [PLAYER IMG] │                           ┃      ║
║      ┃                  │   knocked    │                           ┃      ║
║      ┃                  │  down pose   │                           ┃      ║
║      ┃                  │  80x80 px    │                           ┃      ║
║      ┃                  │ PLAYER_BASE/ │                           ┃      ║
║      ┃                  └──────────────┘                           ┃      ║
║      ┃                                                             ┃      ║
║      ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛      ║
║                                                                            ║
║  BACKGROUND: Dark red overlay from GUI_PALETTE/                          ║
║  BORDER: Frame from GUI_FRAMES/ in error/red variant                     ║
║                                                                            ║
╚════════════════════════════════════════════════════════════════════════════╝
```

---

## DETAILED HUD COMPONENT BREAKDOWN

### Health Bar Component (Zoomed In)

```
┌─────────────────────────────────────────────────┐
│  H P: 7 5 / 100                                │  ← Text (GUI_FONT_IMAGES/)
│  ┌─────────────────────────────────────────────┐│  ← Background from GUI_BARS/
│  │███████████████░░░░░░░░░░░░░░░░░░░░░░░░░░░│  ← Fill scaled to 75%
│  └─────────────────────────────────────────────┘│  ← from GUI_BARS/
│                                                  │
│  X: 20px                                         │
│  Y: screenHeight - 100px                        │
│  Width: 200px                                    │
│  Height: 20px                                    │
│                                                  │
│  Assets Used:                                    │
│  ├─ GUI_BARS/health_bg.png (background)         │
│  ├─ GUI_BARS/health_fill.png (fill, scaled)    │
│  └─ GUI_FONT_IMAGES/ (text "HP:")              │
└─────────────────────────────────────────────────┘
```

### Energy Bar Component (Zoomed In)

```
┌──────────────────────────────────────────────────┐
│  E N: 9 0 / 100                                 │  ← Text (GUI_FONT_IMAGES/)
│  ┌──────────────────────────────────────────────┐│  ← Background
│  │██████████████████████████░░░░░░░░░░░░░░░░░│  ← Fill 90%
│  └──────────────────────────────────────────────┘│
│                                                   │
│  X: 20px                                          │
│  Y: screenHeight - 70px (below health bar)       │
│  Width: 200px                                     │
│  Height: 20px                                     │
│                                                   │
│  Assets Used:                                     │
│  ├─ GUI_BARS/energy_bg.png (background)         │
│  ├─ GUI_BARS/energy_fill.png (fill, scaled)    │
│  └─ GUI_FONT_IMAGES/ (text "EN:")              │
└──────────────────────────────────────────────────┘
```

### Ammo Counter Component (Zoomed In)

```
┌────────────────────────┐
│  A M M O: 1 8 / 3 0   │  ← All from GUI_FONT_IMAGES/
│                        │     Numbers from GUI_NUMBERS/
│  X: 400px              │
│  Y: screenHeight - 60px│
└────────────────────────┘
```

### Weapon Indicator Component (Zoomed In)

```
┌────────────────────┐
│  ┌──────────────┐  │  ← Icon from WEAPON_1_EFFECTS/
│  │      🔫       │  │     (or first image in folder)
│  └──────────────┘  │     Size: 40x40
│  W  E  A  P  O  N  1 │  ← Text from GUI_FONT_IMAGES/
│                     │
│  X: 800px           │
│  Y: screenHeight-80px
└────────────────────┘
```

### Keybind Display Component (Zoomed In)

```
┌──────────────┐   ┌──────────────┐
│  ┌──────┐    │   │  ┌──────┐    │
│  │SPACE │ ← KEYBOARD_KEYS/   │  ESC  │ ← KEYBOARD_KEYS/
│  └──────┘    │   │  └──────┘    │
│  F  I  R  E  │   │  M  E  N  U  │
│              │   │              │
└──────────────┘   └──────────────┘
   X: 900          X: 1000
   Y: screenHeight - 80
```

---

## TILEMAP VISUAL EXAMPLE (Level 1)

```
Rendered Output (Grid - not to scale):

Row 0-1 (Sky):  [EMPTY]  [EMPTY]  [EMPTY]  ...  [EMPTY]
                (Transparent or background shows through)

Row 2 (Platform):
                [EMPTY]  [EMPTY]  [EMPTY]  [TILE_A] [EMPTY]  ...

Row 3 (Main Platform):
                [TILE_A][TILE_A][TILE_A][EMPTY][TILE_A][TILE_A]...

Row 4-7 (Floor/Fill):
                [TILE_C][TILE_C][TILE_C][TILE_C][TILE_C][TILE_C]...

Each [TILE_X] = 64x64 pixels
Each tile is an image from L1_TILES_BASE/
Character codes defined by Level1TileRegistry
```

### Visual Asset Substitution Example

```
Character Code 'A' (Platform) → Level1TileRegistry.getTile('A')
                              → Path: "Resources/.../1 Tiles/tile_platform.png"
                              → Cache lookup → BufferedImage
                              → g.drawImage(img, screenX, screenY, 64, 64, null)

Real visible result: A single 64x64 pixel platform sprite appears on screen
```

---

## ANIMATION & TRANSITION FLOWS

### Enemy Spawn Sequence

```
1. Spawn Position: X = screenWidth + 100, Y = screenHeight - 150
2. Asset: ENEMY_BASE/ img1.png (64x64)
3. Animation: Move left at 100 px/sec
   ├─ Update X every frame: x -= 100ms * delta
   ├─ Render: g.drawImage(enemyImg, (int)x, y, 64, 64, null)
   └─ Loop until x < -100 (off-screen left)
4. On collision with bullet:
   ├─ Remove from enemies list
   ├─ Spawn VFX: VFX_SPARKS/ at (x, y) for 0.2s
   └─ Add score
```

### Bullet Fire Sequence

```
1. Spawn Position: X = screenWidth/2 + 32, Y = screenHeight - 200
2. Asset: WEAPON_1_BULLETS/ img1.png (16x16)
3. Fire Effect: WEAPON_1_EFFECTS/ img1.png at (X+10, Y-20) for 0.1s
4. Animation: Move right at 400 px/sec
   ├─ Update X every frame: x += 400ms * delta
   ├─ Render: g.drawImage(bulletImg, (int)x, y, 16, 16, null)
   └─ Loop until x > screenWidth + 100 (off-screen right)
5. On collision with enemy:
   ├─ Remove from bullets list
   ├─ Remove from enemies list
   ├─ Spawn VFX: VFX_SPARKS/ at impact
   └─ Add score + 100
```

---

## COLOR & VISUAL HIERARCHY

```
PRIORITY 1 (MOST VISIBLE):
├─ Player character (center, 64x64, always visible)
├─ HUD bars (bright colors from GUI_BARS/)
└─ Large text (title, score)

PRIORITY 2 (GAMEPLAY):
├─ Enemy sprites (moving, 64x64)
├─ Bullets (small, 16x16, fast moving)
├─ Tilemap (static background)
└─ Collision VFX

PRIORITY 3 (BACKGROUND/POLISH):
├─ Level background
├─ Frame borders
└─ Decorative icons

VISUAL ASSETS:
- Bright colors: Health bars (green), score text
- Dark colors: HUD panel background
- Red accents: Game over, enemy presence
- Blue accents: Energy bar, water/tech elements
- Yellow/gold: Score, reward indicators
```

---

## RENDERING LAYER ORDER (Depth/Z-Order)

```
Layer 0: Background image (tiled)
         ↓
Layer 1: Tilemap (static platforms/floor)
         ↓
Layer 2: Enemies (moving, behind player)
         ↓
Layer 3: Player (center, fixed position)
         ↓
Layer 4: Bullets (projectiles)
         ↓
Layer 5: Collision VFX (sparks, impacts)
         ↓
Layer 6: HUD Panel (semi-transparent background)
         ↓
Layer 7: HUD Text & Icons (fully opaque)
         ↓
Layer 8: Game State Messages (large, centered)
```

This layering ensures correct visibility without objects appearing "behind" UI.

---

## DESIGN SUMMARY

| Screen | Background | Main Content | HUD | Buttons |
|--------|---|---|---|---|
| Main Menu | GUI_FRAMES/ tiled | Logo + Characters | Score (optional) | GUI_BUTTONS/ |
| Gameplay | L1/L2_BG_BASE/ | Tilemap + Entities | Health/Energy/Ammo | N/A |
| Victory | In-game (faded) | Stats + Banner | Score Breakdown | GUI_BUTTONS/ |
| Game Over | Red tint overlay | Failure message | Final stats | GUI_BUTTONS/ |

All visual elements come from REAL PNG/JPEG assets loaded from:
- 14 GUI folders
- 4 Level 1 folders  
- 9 Level 2 folders
- 5 Character folders
- 12 VFX folders
- 24 Weapon folders

**Total: 68 asset folders, 939 images, ZERO dummy graphics**
