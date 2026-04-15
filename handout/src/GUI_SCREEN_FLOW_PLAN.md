# =============================================================================
# INDUSTRIAL ZONE PLATFORMER — COMPLETE GUI & SCREEN FLOW PLAN  v2.0
# =============================================================================
# CSCU9N6 Assignment  |  April 2026
# Scope : All 10 screens · HUD · VFX Particles · Animated decors
#         Scrolling parallax in ALL menus · Icon-driven buttons
#         Full keyboard + full mouse · Spark / smoke / glow effects
# Architecture : Game.java → JPanel GamePanel + javax.swing.Timer (60 fps)
# =============================================================================

---

## TABLE OF CONTENTS

```
 1.  Design Philosophy & What Was Missing
 2.  Architecture Diagram — JPanel + Timer rendering pipeline
 3.  Screen State Machine — Full transition diagram
 4.  Navigation Flow Table — Every possible route
 5.  Asset Map — Exact PNG used on each screen
 6.  Screen-by-Screen Breakdown (with code snippets)
      6.1   SPLASH            — logo fade + glow pulse
      6.2   MAIN MENU         — LIVE parallax BG + animated decors + icon buttons
      6.3   CHARACTER SELECT  — animated idle cards + greyscale + stat bars
      6.4   LEVEL SELECT      — slide animation + BG thumbnail + difficulty badge
      6.5   CONTROLS          — key-box grid + icon badges
      6.6   CREDITS           — clipped auto-scroll + gradient fade
      6.7   GAMEPLAY HUD      — bar images + digit PNGs + cursor PNG
      6.8   PAUSE OVERLAY     — dim + nine-patch panel + icon menu items
      6.9   SETTINGS OVERLAY  — sliders + toggles + volume icons
      6.10  GAME OVER         — typewriter + digit score + spark burst VFX
 7.  Input System — Every key and mouse event, all 10 screens
 8.  VFX Particle System — Smoke · Sparks · Glow · Button flash
 9.  Animation Plan — What animates, how, frame timings
10.  Cursor System — 4 PNG cursors, context switching
11.  Parallax Scroll in Menus — cameraX simulation
12.  State Variables Reference — All fields used by GUI
13.  Implementation Checklist — What is done / what is next
```

---

## 1. DESIGN PHILOSOPHY & WHAT WAS MISSING

### Theme
Industrial / Cyberpunk — dark navy backgrounds, teal/cyan neon accents,
glowing edges, pixel art lettering (`CyberpunkCraftpixPixel.otf`).

### What the Previous Version Was Missing

| Missing Feature                     | Why It Matters                                    |
|-------------------------------------|---------------------------------------------------|
| Parallax BG moving in menus         | All menus showed a static frozen background       |
| Button VFX on click (spark burst)   | No feedback when clicking buttons                 |
| Animated decor elements             | Cables/glow bars sat static, never pulsed         |
| Glow pulse on logo                  | Logo looked flat with no life                     |
| Icon PNGs on menu items             | Buttons had no visual symbol, just text           |
| Speaker icon on settings sliders    | No visual tie to audio concept                    |
| Volume icon (mute/unmute)           | Toggle just showed ON/OFF text                    |
| Cursor context switching            | Always used cursor[0], never changed              |
| Smoke VFX on enemy death            | smokeAnim loaded but never drawn                  |
| Spark VFX on projectile hit         | 8 spark sheets loaded, never used                 |
| HUD digit images for score          | Score used g.drawString() not digit PNGs          |
| Nine-patch fill for ALL panels      | Some panels fell back to plain fillRect           |

### Core Non-Negotiables
- Every PNG in `Resources/industrial-zone/gui/` must be used somewhere
- Zero solid-colour fill as a "background" — real parallax images always behind
- Every button has: ColorMap texture bg + state icon (normal/hover/pressed)
- Every clickable area has matching keyboard key equivalent
- VFX spawns on every significant event (hit, death, button press, transition)

---

## 2. ARCHITECTURE DIAGRAM — RENDERING PIPELINE

```
main() → new Game() → game.run(false, 1200, 700)
                              │
             ┌────────────────▼────────────────────┐
             │  run() override in Game.java          │
             │                                       │
             │  setPreferredSize(1200 × 700)         │
             │  GamePanel canvas = new GamePanel()   │
             │  setContentPane(canvas)                │
             │  pack() → JFrame wraps canvas         │
             │  addKeyListener(this)                  │
             │  setVisible(true)                      │
             │                                       │
             │  javax.swing.Timer(16ms) ────────────►│
             │       update(dt) every 16ms           │
             │       canvas.repaint() every 16ms     │
             └────────────────┬────────────────────┘
                              │ Swing EDT calls
                              ▼
             ┌────────────────────────────────────┐
             │  GamePanel.paintComponent(g)        │
             │                                     │
             │  g.setRenderingHint(ANTIALIAS ON)   │
             │  g.setRenderingHint(INTERPOLATION)  │
             │  draw(g)  ← routes to screen method │
             │  debug overlay: [ SCREEN_NAME ]     │
             └────────────────────────────────────┘

draw(g) switch(currentScreen):
  SPLASH           → drawSplash(g,W,H)
  MAIN_MENU        → drawMainMenu(g,W,H)
  CHARACTER_SELECT → drawCharSelect(g,W,H)
  LEVEL_SELECT     → drawLevelSelect(g,W,H)
  CONTROLS         → drawControls(g,W,H)
  CREDITS          → drawCredits(g,W,H)
  GAMEPLAY         → drawGameplayScreen(g,W,H)
  PAUSE            → drawGameplayScreen + drawPauseOverlay
  SETTINGS         → drawGameplayScreen + drawPauseOverlay + drawSettingsOverlay
  GAME_OVER        → drawGameplayScreen + drawGameOverScreen
```

---

## 3. SCREEN STATE MACHINE — FULL TRANSITION DIAGRAM

```
╔═══════════════════════════════════════════════════════════════════════════════╗
║                  INDUSTRIAL ZONE — SCREEN STATE MACHINE  v2.0                ║
╚═══════════════════════════════════════════════════════════════════════════════╝

                         ┌──────────────────────┐
                         │  APPLICATION START    │
                         └──────────┬───────────┘
                                    │ auto
                                    ▼
                    ╔═══════════════════════════════╗
                    ║        SPLASH SCREEN          ║
                    ║   [Logo fade-in + glow pulse] ║
                    ║   [Blinking "PRESS SPACE"]    ║
                    ║   Auto-advance after 3s       ║
                    ╚═════════════╤═════════════════╝
                                  │ SPACE / ENTER / CLICK / auto-advance
                                  ▼
          ╔═══════════════════════════════════════════════════════════╗
          ║                   MAIN  MENU                              ║
          ║  [LIVE parallax BG scrolling left→right at 60fps]        ║
          ║  [Logo top-centre with slow glow pulse]                   ║
          ║  [Decor: glow bars bobbing · cables swaying]             ║
          ║                                                           ║
          ║   ┌──────────────────────────────────────────────────┐   ║
          ║   │  Icon+  ▶ PLAY GAME         → CHARACTER SELECT   │   ║
          ║   │  Icon+  ▶ CONTROLS          → CONTROLS           │   ║
          ║   │  Icon+  ▶ CREDITS           → CREDITS            │   ║
          ║   │  Icon+  ▶ EXIT              → System.exit(0)     │   ║
          ║   └──────────────────────────────────────────────────┘   ║
          ║   [Hover: button glows + cursor changes to blue]         ║
          ║   [Click: spark burst VFX at click point]                ║
          ╚═══════════════════════════════════════════════════════════╝
          │              │              │              │
          ▼              ▼              ▼              ▼
 CHARACTER SELECT    CONTROLS       CREDITS       System.exit
          │
          │ ENTER / SELECT button
          ▼
    LEVEL SELECT ──[ENTER / START GAME]──► GAMEPLAY
    LEVEL SELECT ──[ESC]──────────────────► CHARACTER SELECT

                    ┌──────────────────────┐
                    │     GAMEPLAY         │◄────── from Level Select
                    │  [full game running] │
                    │  [HUD always on]     │
                    └──────┬───────────────┘
                           │ ESC
                           ▼
                    ╔══════════════════╗
                    ║   PAUSE MENU     ║◄── [dim overlay, keeps gameplay behind]
                    ║  ▶ RESUME        ║──────────────────────────────► GAMEPLAY
                    ║  ▶ SETTINGS      ║────────────────────────────► SETTINGS
                    ║  ▶ CONTROLS      ║────────────────────────────► CONTROLS
                    ║  ▶ QUIT TO MENU  ║────────────────────────────► MAIN MENU
                    ╚══════════════════╝
                           │ overlay stack:
                           ▼
                    ╔══════════════════╗
                    ║    SETTINGS      ║◄── [drawn on top of PAUSE]
                    ║ Music Vol slider ║
                    ║ SFX Vol slider   ║
                    ║ Music ON/OFF     ║
                    ║ SFX   ON/OFF     ║
                    ║  [BACK]          ║──────────────────────────────► PAUSE
                    ╚══════════════════╝

    GAMEPLAY → player.hp == 0 → GAME OVER
                    ╔══════════════════╗
                    ║   GAME OVER      ║◄── [drawn on top of gameplay freeze]
                    ║ Typewriter title ║  [spark burst VFX when title finishes]
                    ║ Digit-PNG score  ║
                    ║ Enemies killed   ║
                    ║ Time survived    ║
                    ║  [RETRY]         ║──► startGame() → GAMEPLAY
                    ║  [MAIN MENU]     ║──► MAIN MENU
                    ║  [EXIT]          ║──► System.exit(0)
                    ╚══════════════════╝
```

---

## 4. NAVIGATION FLOW TABLE

| From              | Action                           | To                |
|-------------------|----------------------------------|-------------------|
| SPLASH            | SPACE / ENTER / CLICK / 3s auto  | MAIN_MENU         |
| MAIN_MENU         | UP/DOWN / W/S                    | (move selection)  |
| MAIN_MENU         | ENTER / SPACE / click "PLAY"     | CHARACTER_SELECT  |
| MAIN_MENU         | click "CONTROLS"                 | CONTROLS          |
| MAIN_MENU         | click "CREDITS"                  | CREDITS           |
| MAIN_MENU         | click "EXIT" / ESC               | System.exit(0)    |
| CHARACTER_SELECT  | A / LEFT / click PREV            | (cycle char)      |
| CHARACTER_SELECT  | D / RIGHT / click NEXT           | (cycle char)      |
| CHARACTER_SELECT  | ENTER / SPACE / click SELECT     | LEVEL_SELECT      |
| CHARACTER_SELECT  | ESC / click BACK                 | MAIN_MENU         |
| LEVEL_SELECT      | A / LEFT / click card 0          | (select level 1)  |
| LEVEL_SELECT      | D / RIGHT / click card 1         | (select level 2)  |
| LEVEL_SELECT      | ENTER / SPACE / click START GAME | GAMEPLAY          |
| LEVEL_SELECT      | ESC / click BACK                 | CHARACTER_SELECT  |
| CONTROLS          | ESC / BACKSPACE / click BACK     | prevScreen        |
| CREDITS           | ESC / BACKSPACE / click BACK     | MAIN_MENU         |
| CREDITS           | SPACE (hold)                     | speed up scroll   |
| GAMEPLAY          | ESC                              | PAUSE             |
| GAMEPLAY          | player dies                      | GAME_OVER         |
| PAUSE             | ESC / click RESUME               | GAMEPLAY          |
| PAUSE             | click SETTINGS                   | SETTINGS          |
| PAUSE             | click CONTROLS                   | CONTROLS          |
| PAUSE             | click QUIT TO MENU               | MAIN_MENU         |
| SETTINGS          | ESC / click BACK                 | PAUSE             |
| SETTINGS          | LEFT/RIGHT (on vol row)          | adjust volume     |
| SETTINGS          | ENTER (on toggle row)            | flip toggle       |
| GAME_OVER         | LEFT/RIGHT                       | cycle buttons     |
| GAME_OVER         | ENTER / click RETRY              | GAMEPLAY (reset)  |
| GAME_OVER         | click MAIN MENU                  | MAIN_MENU         |
| GAME_OVER         | click EXIT                       | System.exit(0)    |

---

## 5. ASSET MAP — Exact PNG used on which screen

```
Screen            Asset File                                            Purpose
─────────────────────────────────────────────────────────────────────────────────
ALL MENUS         gui/1 Frames/01..27..40… (nine-patch pieces)         Panels
ALL MENUS         gui/6 Buttons/GUI_ButtonColorMap_Variant_05.png      Button BG
ALL MENUS         gui/3 Icons/Buttons2/GUI_Button_State_Variant02_0*.png  State icon
ALL MENUS         gui/8 Cursors/01..04_GUI_Cursor_*.png               Custom cursor
ALL MENUS         gui/1 Frames/16 (Divider)                           Section bar
BG (ALL menus)    1 Tiles/…/2 Background_level_1/BG_Layer1..5.png     Parallax layers
SPLASH            gui/5 Logo/GUI_Logo_IndustrialZone_Full.png          Big logo
MAIN MENU         gui/5 Logo/GUI_Logo_IndustrialZone_Compact.png       Top logo
MAIN MENU         gui/9 Other/1 Decor/01_GlowBars                     Side decoration
MAIN MENU         gui/9 Other/1 Decor/03_CableTwist                   Bottom-left
MAIN MENU         gui/9 Other/1 Decor/08_CableCoil                    Bottom-right
MAIN MENU         gui/3 Icons/Icons/GUI_Icon_Play_Start_33.png         PLAY icon
MAIN MENU         gui/3 Icons/Icons/GUI_Icon_Settings_Gear_10.png      CONTROLS icon
MAIN MENU         gui/3 Icons/Icons/GUI_Icon_User_Profile_25.png       CHAR icon
MAIN MENU         gui/3 Icons/Icons/GUI_Icon_Star_Favorite_21.png      CREDITS icon
MAIN MENU         gui/3 Icons/Icons/GUI_Icon_Cross_Cancel_04.png       EXIT icon
CHAR SELECT       chars/player/biker/01_…Idle.png    (4 frame strip)   Biker idle
CHAR SELECT       chars/player/cyborg/01_…Idle.png   (4 frame strip)   Cyborg idle
CHAR SELECT       chars/player/punk/01_…Idle.png     (5 frame strip)   Punk idle
CHAR SELECT       gui/2 Bars/09..15 Energy bars                        Stat bars
LEVEL SELECT      gui/5 Logo/GUI_Logo_IndustrialZone_Minimal.png       Mini logo
LEVEL SELECT      BG layer [1] as thumbnail preview                     Card preview
CONTROLS          gui/3 Icons/Icons/GUI_Icon_Arrow_Left_07.png         Left key icon
CONTROLS          gui/3 Icons/Icons/GUI_Icon_Arrow_Right_08.png        Right key icon
CONTROLS          gui/3 Icons/Icons/GUI_Icon_Arrow_Up_05.png           Up key icon
CREDITS           gui/5 Logo/GUI_Logo_IndustrialZone_Minimal.png       Scroll logo
CREDITS           gui/9 Other/1 Decor/02_RibbonZigzag.png             Right decor
CREDITS           gui/9 Other/1 Decor/07_CablePlug.png                Left decor
HUD               gui/2 Bars/01..07 Health bars  (7 images)            HP bar
HUD               gui/2 Bars/09..15 Energy bars  (7 images)            EN bar
HUD               gui/7 Numbers/01..09 Digits  + Digit0                Score digits
HUD               gui/8 Cursors/01_Cursor_White                        Custom cursor
HUD               gui/1 Frames/16 Divider                              Top bar accent
PAUSE             All nine-patch frame pieces                           Panel border
SETTINGS          gui/3 Icons/Icons/GUI_Icon_Volume_Sound_37.png        Music icon
SETTINGS          gui/3 Icons/Icons/GUI_Icon_Mute_Silent_38.png         Mute icon
SETTINGS          gui/3 Icons/Icons/GUI_Icon_Play_Start_33.png          ON icon
SETTINGS          gui/3 Icons/Icons/GUI_Icon_Pause_Stop_34.png          SFX icon
GAME OVER         vfx/3 Sparks/05..08_Sparks_Burst.png  (spark arrays)  VFX burst
GAME OVER         gui/7 Numbers  (digit images)                         Score
GAMEPLAY VFX      vfx/1 Smoke/  (18 frames)                            Enemy death
GAMEPLAY VFX      vfx/3 Sparks/ (8 sheets × 4 frames)                  Projectile hit
```

---

## 6. SCREEN-BY-SCREEN BREAKDOWN

### 6.1  SPLASH SCREEN

**What it shows:**
- Black background
- `logoFull` PNG centred, scales to 580 px wide
- Fade-in over 1 s, hold 1.5 s, fade-out to MAIN_MENU
- "PRESS SPACE TO CONTINUE" blinks at sin-wave rate
- Glow pulse: alpha-composited white rect on logo grows/shrinks with `sin(guiTime*2)`

**Fields used:**
```java
float splashTimer;      // ms since screen start
float splashAlpha;      // 0.0 → 1.0 → 0.0
boolean splashFadingOut;
float splashGlowPulse;  // NEW: sin wave for logo glow overlay
```

**Animation code snippet:**
```java
// In updateSplash(long ms):
splashGlowPulse = (float)(0.15 * Math.sin(guiTime * 2.5));

// In drawSplash() after drawing logo:
Composite old = g.getComposite();
g.setComposite(AlphaComposite.getInstance(SRC_OVER,
               Math.max(0, splashGlowPulse) * splashAlpha));
g.setColor(new Color(0, 200, 255));          // teal glow
g.fillRect(lx-10, ly-10, lw+20, lh+20);     // slightly oversized
g.setComposite(old);
```

---

### 6.2  MAIN MENU  ← MOST IMPORTANT — Live Parallax + Animated Decors

**What it shows (UPGRADED):**
- **Live parallax BG**: all 5 BG layers auto-scroll using `menuCamX` (never zero)
- **Logo** top-centre with slow glow pulse `sin(guiTime*1.5)`
- **GlowBars decor**: slight vertical bob `sin(guiTime*3)*6` px
- **CableTwist decor**: slow rotation `guiTime * 15` degrees
- **Panel** with 4 icon-button rows
- **Button icons**: Play/Settings/Star/Cross icons drawn left of label
- **Button hover**: brightness overlay + cursor changes to blue [1]
- **Button click**: spark burst VFX spawned at click point

**New fields needed:**
```java
float menuCamX = 0f;          // auto-scrolling camera for menu parallax
float menuGlowAnim = 0f;      // used for logo pulse + decor bob
List<VfxParticle> menuVfx = new ArrayList<>();  // spark bursts on menu clicks
```

**Parallax scroll code snippet:**
```java
// In updateMainMenu(long ms) — called from update():
menuCamX += 0.8f * (ms / 16f);          // ~0.8 px/frame at 60fps
if (menuCamX > 4000) menuCamX = 0f;    // seamless loop
menuGlowAnim = guiTime;

// In drawMainMenu() — replaces drawBackground() call:
drawParallaxBG(g, W, H, menuCamX);     // uses menuCamX not cameraX
```

**Parallax draw with menuCamX:**
```java
private void drawParallaxBG(Graphics2D g, int W, int H, float camX) {
    // Sky base (static, index 0)
    g.setColor(new Color(22, 14, 42));
    g.fillRect(0, 0, W, H);

    BufferedImage[] layers = bgLayers1;
    for (int i = 0; i < layers.length; i++) {
        if (layers[i] == null) continue;
        int imgW    = layers[i].getWidth();
        int drawH   = (int)(H * 0.72);
        float factor = SCROLL_FACTORS[i];
        int scrollX  = (int)(camX * factor);
        int startX   = -(scrollX % Math.max(imgW, 1));
        if (startX > 0) startX -= imgW;
        for (int x = startX; x < W; x += imgW)
            g.drawImage(layers[i], x, 0, imgW, drawH, null);
    }
}
```

**Animated GlowBars bob:**
```java
int bob = (int)(Math.sin(menuGlowAnim * 3.0) * 6);
if (decorGlowBars != null) {
    g.drawImage(decorGlowBars, 14, H/2 - 130 + bob, 48, 260, null);
    g.drawImage(decorGlowBars, W-62, H/2 - 130 - bob, 48, 260, null);
}
```

**Icon on button (per row):**
```java
// Icon PNG map for 4 menu items:
private static final String[] MENU_ICON_PATHS = {
    "gui/3 Icons/Icons/GUI_Icon_Play_Start_33.png",       // PLAY
    "gui/3 Icons/Icons/GUI_Icon_Settings_Wrench_13.png",  // CONTROLS
    "gui/3 Icons/Icons/GUI_Icon_Star_Favorite_21.png",    // CREDITS
    "gui/3 Icons/Icons/GUI_Icon_Cross_Cancel_04.png",     // EXIT
};
private BufferedImage[] menuIconImgs = new BufferedImage[4]; // loaded in loadGuiAssets

// In drawButton() — before drawing label:
if (menuIconImgs[i] != null)
    g.drawImage(menuIconImgs[i], bx + 14, by + (btnH - 20)/2, 20, 20, null);
```

---

### 6.3  CHARACTER SELECT

**What it shows:**
- Live parallax BG (uses menuCamX, same auto-scroll)
- 3 cards: selected = full colour + 140px sprite; others = greyscale 100px
- Sprite animates at 150ms/frame using `charAnimFrame[c]`
- Stat bars (SPD/PWR/DEF) only on selected card using `enerBars[]`
- Description text
- PREV / NEXT / SELECT buttons
- Selection caret "v" pulsing above selected card

**Missing from current implementation:**
- Selection caret doesn't pulse — add `sin(guiTime*4)` Y offset (±4px)
- Card shadow below selected card (translucent ellipse)

**Pulse caret code:**
```java
int caretBob = (int)(Math.sin(guiTime * 4) * 4);
g.setColor(new Color(0, 220, 255));
g.setFont(hudFont.deriveFont(Font.BOLD, 20f));
g.drawString("▼", cx + cw/2 - 6, cy - 10 + caretBob);
```

---

### 6.4  LEVEL SELECT

**What it shows:**
- Live parallax BG
- 2 level cards with slide animation (`levelSlideAnim`)
- Each card: coloured header, level number in large font, BG layer[1] thumbnail
- Difficulty badge: green "MEDIUM" / orange "HARD"
- Selected card raised 20px, glowing border
- START GAME / < BACK buttons

**Slide animation (ensure it actually runs):**
```java
// In update() case LEVEL_SELECT:
if (levelSlideAnim != 0f) {
    levelSlideAnim *= 0.82f;                   // exponential ease-out
    if (Math.abs(levelSlideAnim) < 0.003f)
        levelSlideAnim = 0f;
}
// The card draw uses: int slideOff = (int)(levelSlideAnim * 80);
```

---

### 6.5  CONTROLS SCREEN

**What it shows:**
- Live parallax BG
- Nine-patch panel 560×480 centred
- Title "CONTROLS" with divider bar
- 8 rows: key-box + arrow icon + action description
- Arrow icons from `gui/3 Icons/Icons/GUI_Icon_Arrow_*.png`
- BACK button with `GUI_Icon_Home_House_12.png` icon

**Key-box rendering (current is plain RoundRect; upgrade it):**
```java
// Draw a mini nine-patch panel for each key box:
drawPanel(g, panX+36, row-24, 160, 32);    // recycled nine-patch
// Then draw key label on top:
g.setFont(hudFont.deriveFont(Font.BOLD, 13f));
g.setColor(new Color(0, 220, 255));
g.drawString(controls[i][0], panX + 50, row);
```

---

### 6.6  CREDITS SCREEN

**What it shows:**
- Parallax BG (scrolling via menuCamX)
- Dark overlay `new Color(0,0,0,140)`
- RibbonZigzag decor top-right
- CablePlug decor bottom-left
- Clipped scrolling text block (logo → sections → "Thank You")
- Top/bottom gradient fade masks
- BACK button
- SPACE to speed up hint

---

### 6.7  GAMEPLAY HUD

**What it shows:**
- **Top bar** (48px): score (digit PNGs) + "LEVEL X/2" centred + timer + FPS
- **Bottom bar** (68px): HP bar image + EN bar image + enemy count
- **Custom cursor PNG** at mouse position
- **HUD teal accent line** (2px) at top and bottom edges of bars
- **Divider image** tiled along the bar edges

**Score with DIGIT IMAGES (not g.drawString):**
```java
// Draw score using digit PNGs instead of text:
// In drawHUD(), replace g.drawString score with:
String scoreStr = String.format("%07d", score);
int dx = 14, dy = 8;
for (char ch : scoreStr.toCharArray()) {
    int d = ch - '0';
    if (digitImgs[d] != null)
        g.drawImage(digitImgs[d], dx, dy, 22, 30, null);
    dx += 24;
}
```

**Cursor context switching:**
```java
// In drawHUD() — cursor changes based on context:
int cursorIdx = 0;                              // default: white
if (currentScreen == GameScreen.GAMEPLAY) {
    // Red when near enemy
    boolean nearEnemy = enemies.stream().anyMatch(e ->
        Math.abs(e.getX() - mouseX) < 120 && Math.abs(e.getY() - mouseY) < 120);
    cursorIdx = nearEnemy ? 2 : 0;             // 2=red, 0=white
}
if (cursorImgs[cursorIdx] != null)
    g.drawImage(cursorImgs[cursorIdx], mouseX - 2, mouseY - 2, 22, 26, null);
```

---

### 6.8  PAUSE OVERLAY

**What it shows:**
- Dim overlay `new Color(0,0,0,160)`
- Nine-patch panel 380×360
- "PAUSED" title
- 4 icon-button rows: RESUME / SETTINGS / CONTROLS / QUIT

**Icons for pause items:**
```java
private static final String[] PAUSE_ICON_PATHS = {
    "gui/3 Icons/Icons/GUI_Icon_Play_Start_33.png",       // RESUME
    "gui/3 Icons/Icons/GUI_Icon_Settings_Gear_10.png",    // SETTINGS
    "gui/3 Icons/Icons/GUI_Icon_Info_Question_14.png",    // CONTROLS
    "gui/3 Icons/Icons/GUI_Icon_Home_House_12.png",       // QUIT
};
private BufferedImage[] pauseIconImgs = new BufferedImage[4];
```

---

### 6.9  SETTINGS OVERLAY

**What it shows:**
- Additional dim over pause
- Nine-patch panel 440×380
- "SETTINGS" title
- Music Vol slider + Volume icon PNG
- SFX Vol slider + Mute icon PNG (when off)
- Music ON/OFF toggle + Play icon
- SFX ON/OFF toggle + Pause icon
- BACK button

**Volume icon integration:**
```java
// In drawSlider(), draw icon left of label:
BufferedImage icon = on ? iconVolume : iconMute;  // loaded from gui/3 Icons
if (icon != null) g.drawImage(icon, x - 28, y, 22, 22, null);
// iconVolume = tryLoad(GUI_DIR + "3 Icons/Icons/GUI_Icon_Volume_Sound_37.png")
// iconMute   = tryLoad(GUI_DIR + "3 Icons/Icons/GUI_Icon_Mute_Silent_38.png")
```

---

### 6.10  GAME OVER SCREEN

**What it shows (UPGRADED):**
- Animated dark overlay fade-in (alpha composite)
- Nine-patch panel 520×480
- "GAME OVER" typewriter (1 char per 40ms)
- When typewriter finishes → **spark burst VFX** centred above panel
- Score with digit PNGs, counting up from 0
- Enemies killed counter (digit PNGs)
- Time survived (HH:MM format)
- 3 buttons: RETRY / MAIN MENU / EXIT
- Hovered button: blue cursor

**Spark burst VFX on typewriter complete:**
```java
// In updateGameOver(long ms):
if (gameOverCharsShown == 9 && !gameOverBurstFired) {
    gameOverBurstFired = true;
    // Spawn 3 spark particles at top-centre of panel
    int panX = (SCREEN_W - 520)/2 + 260;
    int panY = (SCREEN_H - 480)/2 + 40;
    for (int k = 0; k < 3; k++)
        vfxParticles.add(new VfxParticle(panX + (k-1)*60, panY,
                                          VFX_SPARKS, k % sparkAnims.length));
}
```

---

## 7. INPUT SYSTEM — EVERY KEY AND MOUSE EVENT

### Keyboard Map (all 10 screens)

| Screen            | Key              | Action                                   |
|-------------------|------------------|------------------------------------------|
| SPLASH            | SPACE / ENTER    | Skip to MAIN_MENU immediately            |
| SPLASH            | any key          | Skip to MAIN_MENU                        |
| MAIN_MENU         | UP / W           | Move selection up (wraps)                |
| MAIN_MENU         | DOWN / S         | Move selection down (wraps)              |
| MAIN_MENU         | ENTER / SPACE    | Activate selected item                   |
| MAIN_MENU         | ESC              | System.exit(0)                           |
| CHAR_SELECT       | A / LEFT         | Previous character (wraps)               |
| CHAR_SELECT       | D / RIGHT        | Next character (wraps)                   |
| CHAR_SELECT       | ENTER / SPACE    | Confirm → LEVEL_SELECT                   |
| CHAR_SELECT       | ESC              | Back → MAIN_MENU                         |
| LEVEL_SELECT      | A / LEFT         | Select level 1                           |
| LEVEL_SELECT      | D / RIGHT        | Select level 2                           |
| LEVEL_SELECT      | ENTER / SPACE    | Start game → GAMEPLAY                    |
| LEVEL_SELECT      | ESC              | Back → CHAR_SELECT                       |
| CONTROLS          | ESC / BACKSPACE  | Back → prevScreen                        |
| CREDITS           | ESC / BACKSPACE  | Back → MAIN_MENU                         |
| CREDITS           | SPACE (hold)     | Double scroll speed                      |
| GAMEPLAY          | A / LEFT         | Move player left                         |
| GAMEPLAY          | D / RIGHT        | Move player right                        |
| GAMEPLAY          | SPACE            | Jump                                     |
| GAMEPLAY          | SHIFT            | Dash                                     |
| GAMEPLAY          | CTRL             | Shoot / attack                           |
| GAMEPLAY          | ESC              | Pause → PAUSE                            |
| GAMEPLAY          | 1                | Switch to Level 1                        |
| GAMEPLAY          | 2                | Switch to Level 2                        |
| PAUSE             | ESC              | Resume → GAMEPLAY                        |
| PAUSE             | UP / W           | Move menu selection up                   |
| PAUSE             | DOWN / S         | Move menu selection down                 |
| PAUSE             | ENTER            | Activate selected pause item             |
| SETTINGS          | ESC              | Back → PAUSE                             |
| SETTINGS          | UP / W           | Move to previous setting                 |
| SETTINGS          | DOWN / S         | Move to next setting                     |
| SETTINGS          | LEFT             | Decrease slider value (−5%)              |
| SETTINGS          | RIGHT            | Increase slider value (+5%)              |
| SETTINGS          | ENTER / SPACE    | Toggle on/off (for toggle rows)          |
| GAME_OVER         | LEFT / A         | Move button selection left               |
| GAME_OVER         | RIGHT / D        | Move button selection right              |
| GAME_OVER         | ENTER / SPACE    | Activate selected game-over button       |
| GAME_OVER         | ESC              | Go to MAIN_MENU                          |

### Mouse Map (all 10 screens)

```
MouseMotionListener.mouseMoved(e):
  → mouseX = e.getX(); mouseY = e.getY();
  → recalculate hover state for whichever screen is active
  → update cursor index (0=white,1=blue,2=red,3=green)

MouseAdapter.mouseClicked(e):
  → handleClick(e.getX(), e.getY(), e.getButton())

MouseWheelListener.mouseWheelMoved(e):
  → handleWheel(e.getWheelRotation())
  → CREDITS: scroll up if negative
  → SETTINGS: navigate rows
```

**Hover detection helper:**
```java
private boolean isInside(int mx, int my, int x, int y, int w, int h) {
    return mx >= x && mx <= x+w && my >= y && my <= y+h;
}

// Cursor context in every draw method:
// On MAIN_MENU: if hovering button → cursorImgs[1] (blue), else cursorImgs[0]
// On GAMEPLAY:  if near enemy     → cursorImgs[2] (red),  else cursorImgs[0]
// On GAME_OVER: if over RETRY btn → cursorImgs[3] (green), else cursorImgs[0]
```

---

## 8. VFX PARTICLE SYSTEM

### Particle Types
```java
static final int VFX_SMOKE  = 0;   // enemy death — 18 frame sequence
static final int VFX_SPARKS = 1;   // projectile hit / button click / game over

class VfxParticle {
    int   x, y;           // world position
    int   type;           // VFX_SMOKE or VFX_SPARKS
    int   animIdx;        // which spark sheet (0-7) or 0 for smoke
    int   frame;          // current frame
    long  timer;          // ms since last frame advance
    float alpha;          // fade-out
    boolean screenSpace;  // true = draw at screen coords (gui vfx)
                           // false = draw at world coords - camera (gameplay)
}
List<VfxParticle> vfxParticles = new ArrayList<>();
```

### Spawn points
| Event                        | VFX type    | Count | Where spawned        |
|------------------------------|-------------|-------|----------------------|
| Enemy death                  | SMOKE       | 1     | enemy.getX,Y         |
| Projectile hits enemy        | SPARKS      | 1     | impact point         |
| Button clicked (any screen)  | SPARKS      | 1     | mouseX, mouseY       |
| "GAME OVER" typewriter done  | SPARKS      | 3     | panel top-centre     |
| Level transitions            | SMOKE       | 2     | screen centre        |

### VFX Update (called from update()):
```java
private void updateVfx(long ms) {
    Iterator<VfxParticle> it = vfxParticles.iterator();
    while (it.hasNext()) {
        VfxParticle p = it.next();
        p.timer += ms;
        int msPerFrame = (p.type == VFX_SMOKE) ? 80 : 80;
        if (p.timer >= msPerFrame) {
            p.timer = 0;
            p.frame++;
        }
        int maxFrames = (p.type == VFX_SMOKE)
            ? (smokeAnim != null ? 18 : 0)
            : (sparkAnims != null && p.animIdx < sparkAnims.length ? 4 : 0);
        if (p.frame >= maxFrames) it.remove();
    }
}
```

### VFX Draw (called from drawGameplayScreen and overlay screens):
```java
private void drawVfx(Graphics2D g, int camX, int camY) {
    for (VfxParticle p : vfxParticles) {
        int dx = p.screenSpace ? p.x : (p.x - camX);
        int dy = p.screenSpace ? p.y : (p.y - camY);
        if (p.type == VFX_SMOKE && smokeSheet != null) {
            int fw = smokeSheet.getWidth() / 18;
            int fh = smokeSheet.getHeight();
            int f  = Math.min(p.frame, 17);
            g.drawImage(smokeSheet.getSubimage(f*fw,0,fw,fh),
                        dx-32, dy-32, 64, 64, null);
        } else if (p.type == VFX_SPARKS && sparkAnims != null) {
            // sparkAnims[p.animIdx] is an Anim; use frame p.frame clamped
            BufferedImage[] frames = sparkFrames[p.animIdx];  // pre-cut array
            int f = Math.min(p.frame, frames.length-1);
            if (frames[f] != null)
                g.drawImage(frames[f], dx-24, dy-24, 48, 48, null);
        }
    }
}
```

---

## 9. ANIMATION PLAN

| Element                | What animates           | Rate / Method                          |
|------------------------|-------------------------|----------------------------------------|
| Splash logo glow       | sin pulse brightness    | `sin(guiTime * 2.5)` → alpha composite |
| "PRESS SPACE" blink    | sin fade in/out         | `0.5 + 0.5*sin(guiTime * 4)`           |
| Menu parallax scroll   | menuCamX += 0.8/frame   | update() every 16ms                    |
| Glow bars bob          | ±6px vertical sin       | `sin(guiTime * 3)`                     |
| Cable twist rotation   | AffineTransform rotate  | `guiTime * 15` degrees (mod 360)       |
| Logo top glow pulse    | brightness overlay      | `sin(guiTime * 1.5)` → alpha 0..0.25  |
| Button hover flash     | bright overlay 2px top  | immediate on hover                     |
| Button click spark     | VFX_SPARKS particle     | spawned in handleClick()               |
| Char idle animation    | 4 frames, 150ms each    | charAnimAccum, 150ms tick              |
| Char card caret bob    | ±4px vertical sin       | `sin(guiTime * 4)` in drawCharSelect   |
| Level card slide       | levelSlideAnim * 80px   | exponential ease-out 0.82x/frame       |
| Credits auto-scroll    | creditsScrollY -= speed | 30 px/s normal, 80 px/s with SPACE     |
| HUD score count-up     | gameOverScoreTally++    | +score * (ms/800) per frame             |
| GameOver typewriter    | 1 char per 40ms         | gameOverCharsShown, gameOverTypeTimer  |
| GameOver spark burst   | VFX_SPARKS × 3          | fired once when charsShown == 9        |
| Enemy smoke VFX        | VFX_SMOKE 18 frames     | spawned in updateGameplay on death     |
| Projectile spark VFX   | VFX_SPARKS 4 frames     | spawned in updateGameplay on hit       |
| Cursor                 | follows mouseX,mouseY   | redrawn every frame at top layer       |

---

## 10. CURSOR SYSTEM

```java
// 4 cursor images:
cursorImgs[0] = white  — default, most screens
cursorImgs[1] = blue   — hovering over interactive element
cursorImgs[2] = red    — targeting / near enemy (gameplay)
cursorImgs[3] = green  — confirm action (ENTER state, game over RETRY)

// Context rules:
// SPLASH, CREDITS      → [0] white
// MAIN_MENU (idle)     → [0] white
// MAIN_MENU (hovering) → [1] blue
// CHAR_SELECT (hover)  → [1] blue
// LEVEL_SELECT (hover) → [1] blue
// GAMEPLAY (normal)    → [0] white
// GAMEPLAY (near foe)  → [2] red
// GAME_OVER (RETRY)    → [3] green
// GAME_OVER (EXIT)     → [2] red
// SETTINGS             → [1] blue

// In every draw method, call at very end (top layer):
drawCursor(g);

private void drawCursor(Graphics2D g) {
    if (cursorImgs[activeCursorIdx] != null)
        g.drawImage(cursorImgs[activeCursorIdx], mouseX-2, mouseY-2, 22, 26, null);
}

// activeCursorIdx updated in: mouseMoved event + in each drawXxx method
```

---

## 11. PARALLAX SCROLL IN MENUS

```
Problem (OLD):  drawBackground() used cameraX which is 0 in all menus → frozen BG
Solution (NEW): every menu uses drawParallaxBG(g, W, H, menuCamX)
                menuCamX auto-increments in updateMainMenu() → live scrolling

MenuCamX lifecycle:
  reset to 0 when entering MAIN_MENU (in goTo())
  increments 0.8 px/frame in update() for MAIN_MENU, CHAR_SELECT, LEVEL_SELECT, CREDITS
  wraps at 4000 to prevent float overflow
  CHARACTER_SELECT and LEVEL_SELECT ALSO use menuCamX (same seamless scroll)
  CREDITS uses menuCamX (slow scroll behind dark overlay looks great)

SCROLL_FACTORS used: {0.0, 0.08, 0.18, 0.30, 0.50}
  Layer 0 (sky) = 0.0 → never moves
  Layer 1 (trees) = 0.08 → drifts slowly
  Layer 4 (near factory) = 0.50 → scrolls fastest → depth illusion

Code in update():
  case MAIN_MENU:
  case CHARACTER_SELECT:
  case LEVEL_SELECT:
  case CREDITS:
    menuCamX += 0.8f * (elapsedTime / 16f);
    if (menuCamX > 4000) menuCamX = 0f;
    menuGlowAnim = guiTime;
    break;
```

---

## 12. STATE VARIABLES REFERENCE

```java
// GLOBAL GUI
float   guiTime          = 0f;        // total seconds, used for all sin-wave anims
int     mouseX, mouseY   = 0;         // current mouse position (updated in mouseMoved)
int     menuHoveredIndex = -1;        // which button is hovered (-1=none)
int     activeCursorIdx  = 0;         // 0=white,1=blue,2=red,3=green
float   menuCamX         = 0f;        // NEW: parallax camera for all menu screens

// SPLASH
float   splashTimer      = 0f;
float   splashAlpha      = 0f;
float   splashGlowPulse  = 0f;        // NEW: logo glow overlay
boolean splashFadingOut  = false;

// MAIN MENU
int     menuSelectedIndex = 0;
float   menuGlowAnim     = 0f;        // alias for guiTime, drives logo + decors
List<VfxParticle> menuVfx = new ArrayList<>();  // NEW: click VFX

// CHAR SELECT
int     charSelectIndex  = 1;
long    charAnimAccum    = 0L;
int[]   charAnimFrame    = {0,0,0};
BufferedImage[][] charIdleFrames = new BufferedImage[3][4];

// LEVEL SELECT
int     levelSelectIndex = 0;
float   levelSlideAnim   = 0f;        // exponential ease-out on selection change

// CREDITS
float   creditsScrollY     = 700f;
float   creditsScrollSpeed = 30f;
boolean creditsSpeedUp     = false;
float   creditsAutoReturn  = -1f;

// PAUSE
int     pauseMenuIndex   = 0;

// SETTINGS
int     settingsIndex    = 0;
float   settingsMusicVol = 0.7f;
float   settingsSfxVol   = 0.8f;
boolean settingsMusicOn  = true;
boolean settingsSfxOn    = true;
BufferedImage iconVolume, iconMute;   // NEW: volume icon PNGs
BufferedImage[] pauseIconImgs = new BufferedImage[4];  // NEW: pause menu icons
BufferedImage[] menuIconImgs  = new BufferedImage[4];  // NEW: main menu icons

// GAME OVER
int     gameOverMenuIndex    = 0;
float   gameOverAlpha        = 0f;
float   gameOverTypeTimer    = 0f;
int     gameOverCharsShown   = 0;
float   gameOverScoreTally   = 0f;
int     gameOverEnemiesKilled = 0;
long    gameOverTime          = 0L;
boolean gameOverBurstFired    = false;  // NEW: spark burst fires once

// VFX
List<VfxParticle> vfxParticles = new ArrayList<>();   // NEW: unified VFX list
BufferedImage[][] sparkFrames = new BufferedImage[8][4];  // NEW: pre-cut spark frames
BufferedImage smokeSheet;          // NEW: single sprite sheet for smoke (stitched)
```

---

## 13. IMPLEMENTATION CHECKLIST

```
  FEATURE                              STATUS      FILE / METHOD
  ─────────────────────────────────────────────────────────────────────────────
  JPanel + Swing Timer rendering       ✅ DONE     Game.run() override + GamePanel
  All 10 draw methods present          ✅ DONE     drawSplash..drawGameOverScreen
  Nine-patch panel helper              ✅ DONE     drawPanel(g,x,y,w,h)
  Button with ColorMap texture         ✅ DONE     drawButton(g,x,y,w,h,lbl,st)
  Digit PNG score rendering            ✅ DONE     drawDigits(g,x,y,val,dw,dh)
  Char idle animation (150ms)          ✅ DONE     updateCharSelect + charAnimFrame
  Level slide animation                ✅ DONE     levelSlideAnim exponential ease
  Credits auto-scroll + speed          ✅ DONE     updateCredits
  Game Over typewriter                 ✅ DONE     gameOverCharsShown
  Full keyboard router (10 screens)    ✅ DONE     keyPressed()
  Mouse click router                   ✅ DONE     handleClick()
  Mouse wheel router                   ✅ DONE     handleWheel()
  ─────────────────────────────────────────────────────────────────────────────
  LIVE parallax BG in menus           ❌ MISSING  Need menuCamX + updateMainMenu()
  Logo glow pulse (splash + menu)      ❌ MISSING  Need sin-wave alpha overlay
  Button icon PNGs                     ❌ MISSING  Need menuIconImgs[] loaded+drawn
  Animated glow bars bob               ❌ MISSING  Need sin Y offset in drawMainMenu
  Cable twist rotation                 ❌ MISSING  Need AffineTransform rotate
  Cursor context switching             ❌ MISSING  Need activeCursorIdx logic
  VFX on button click (sparks)         ❌ MISSING  Need VfxParticle system
  VFX on enemy death (smoke)           ❌ MISSING  smokeAnim loaded but not used
  VFX on projectile hit (sparks)       ❌ MISSING  sparkAnims loaded but not used
  Spark burst on Game Over done        ❌ MISSING  Need gameOverBurstFired logic
  Speaker/mute icons in settings       ❌ MISSING  Need iconVolume/iconMute loaded
  Pause menu icon PNGs                 ❌ MISSING  Need pauseIconImgs[] loaded+drawn
  Score via digit PNGs in HUD          ❌ MISSING  Currently uses g.drawString
  Red cursor near enemies              ❌ MISSING  Need cursor context switch
```

---

*End of GUI_SCREEN_FLOW_PLAN.md v2.0*
*Next: see GAME_IMPLEMENTATION_PLAN.md for full implementation priority queue*