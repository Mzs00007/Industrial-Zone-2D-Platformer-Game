# Phase 2-3 Implementation - Correct Asset Paths from AnimationAndSpriteLoader.java

## CRITICAL PRINCIPLE: SPRITE-BLITTING ARCHITECTURE
- **Load ALL assets ONCE at startup** (not in render loop)
- **Use ONLY g.drawImage()** in render methods
- **Never use vector graphics** (fillRect, setColor, drawString, etc.)
- **Return NULL** if asset fails to load - no fallback graphics

---

## PHASE 2: CHARACTER IDLE ANIMATIONS

### File: Phase2CharacterIdleScreen.java

#### Asset Paths (from AnimationAndSpriteLoader.java)

**Biker Character:**
```
BASE_PATH = "Resources/industrial-zone/characters/player/biker"
IDLE SPRITE = "/01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"
FULL PATH = "Resources/industrial-zone/characters/player/biker/01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"

Animation Config:
- Frames: 4 (horizontal in single row)
- Timing: 150ms per frame
- Description: "Standing breathing loop, default idle"
```

**Cyborg Character:**
```
BASE_PATH = "Resources/industrial-zone/characters/player/cyborg"
IDLE SPRITE = "/01_Player_Cyborg_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"
FULL PATH = "Resources/industrial-zone/characters/player/cyborg/01_Player_Cyborg_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"

Animation Config:
- Frames: 4 (horizontal in single row)
- Timing: 150ms per frame
- Description: "Standing tech breathing"
```

**Punk Character:**
```
BASE_PATH = "Resources/industrial-zone/characters/player/punk"
IDLE SPRITE = "/01_Player_Punk_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"
FULL PATH = "Resources/industrial-zone/characters/player/punk/01_Player_Punk_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"

Animation Config:
- Frames: 5 (horizontal in single row)
- Timing: 150ms per frame
- Description: "Casual standing posture"
```

#### Rendering Code (RASTER-ONLY)

```java
// PRELOAD ONCE AT STARTUP
private BufferedImage bikerIdleSprite = null;
private BufferedImage cyborgIdleSprite = null;
private BufferedImage punkIdleSprite = null;

// In loadAssetsOnce():
bikerIdleSprite = loadImage(
    AnimationAndSpriteLoader.PlayerCharacterAnimations.BikerAnimations.BASE_PATH 
    + "/01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"
);

// IN RENDER (RASTER-ONLY):
// Frame extraction from horizontal sprite sheet:
int frameWidth = bikerIdleSprite.getWidth() / 4;  // 4 frames total
int srcX = currentFrame * frameWidth;  // Which frame

g.drawImage(bikerIdleSprite,
    destX, destY, destX+destWidth, destY+destHeight,  // destination
    srcX, 0, srcX+frameWidth, frameHeight,             // source (frame)
    null);
```

---

## PHASE 3: STATUS BAR HUD (Health + Energy)

### File: Phase3StatusBarScreen.java

#### Asset Directory (from AnimationAndSpriteLoader.java)

```
BAR_DIRECTORY = "Resources/industrial-zone/gui/2 Bars"
```

#### Health Bar States

**Path Format:**
```
"Resources/industrial-zone/gui/2 Bars/{FILENAME}"
```

**Asset Files (7 states):**
```
Index 0: "01_GUI_Bar_HealthBar_Full100pct_RedOrangeFillDarkFrame_HUD.png"        → 100%
Index 1: "02_GUI_Bar_HealthBar_80pct_RedOrangeFill_HUD.png"                      → 80%
Index 2: "03_GUI_Bar_HealthBar_60pct_OrangeFill_HUD.png"                         → 60%
Index 3: "04_GUI_Bar_HealthBar_40pct_OrangeFill_HUD.png"                         → 40%
Index 4: "05_GUI_Bar_HealthBar_20pct_DarkRedFill_HUD.png"                        → 20%
Index 5: "06_GUI_Bar_HealthBar_Critical5pct_RedFlashing_HUD.png"                 → 5%
Index 6: "07_GUI_Bar_HealthBar_Empty0pct_GreyEmpty_HUD.png"                      → 0%
```

#### Energy Bar States

**Path Format:**
```
"Resources/industrial-zone/gui/2 Bars/{FILENAME}"
```

**Asset Files (6 states):**
```
Index 0: "09_GUI_Bar_EnergyBar_Full100pct_BlueCyanFillDarkFrame_HUD.png"         → 100%
Index 1: "10_GUI_Bar_EnergyBar_80pct_BlueFill_HUD.png"                           → 80%
Index 2: "11_GUI_Bar_EnergyBar_60pct_CyanFill_HUD.png"                           → 60%
Index 3: "12_GUI_Bar_EnergyBar_40pct_LightBlueFill_HUD.png"                      → 40%
Index 4: "13_GUI_Bar_EnergyBar_20pct_OrangeFlashing_HUD.png"                     → 20%
Index 5: "14_GUI_Bar_EnergyBar_Empty0pct_GreyEmpty_HUD.png"                      → 0%
```

#### Rendering Code (RASTER-ONLY)

```java
// PRELOAD ALL 13 BARS ONCE AT STARTUP
private BufferedImage[] healthBars = new BufferedImage[7];
private BufferedImage[] energyBars = new BufferedImage[6];

// In loadAssetsOnce():
for (int i = 0; i < HEALTH_BAR_FILES.length; i++) {
    healthBars[i] = loadImage("Resources/industrial-zone/gui/2 Bars/" + HEALTH_BAR_FILES[i]);
}
for (int i = 0; i < ENERGY_BAR_FILES.length; i++) {
    energyBars[i] = loadImage("Resources/industrial-zone/gui/2 Bars/" + ENERGY_BAR_FILES[i]);
}

// IN RENDER (RASTER-ONLY):
int healthIndex = getHealthBarIndex(currentHealthPercent);  // 0-6
BufferedImage healthBar = healthBars[healthIndex];  // Pre-rendered PNG

g.drawImage(healthBar,
    healthX, healthY,                                           // destination
    healthX + healthBar.getWidth(), healthY + healthBar.getHeight(),
    0, 0, healthBar.getWidth(), healthBar.getHeight(),          // source (full image)
    null);  // RASTER: g.drawImage() ONLY
```

---

## KEY DIFFERENCES FROM VECTOR APPROACH

| Aspect | Vector Graphics ✗ | Raster Graphics ✓ |
|--------|-------------------|------------------|
| **Health Bar** | `g.fillRect(x, y, width\*pct, height); g.setColor(red)` | Load "03_HealthBar_60pct.png", use `g.drawImage()` |
| **Text Display** | `g.setFont(new Font(...)); g.drawString("100/100", x, y)` | Load font PNG, render each letter with `g.drawImage()` |
| **Button Rendering** | `g.fillRect(), g.drawRect(), g.setColor()` | Load button PNG, use `g.drawImage()` |
| **Load Timing** | Not applicable | **CRITICAL: Load ONCE at startup, NEVER in render loop** |
| **Performance** | Can be slow with complex shapes | Fast - just bitmap blitting |
| **Appearance** | Consistent across Java versions | Pixel-perfect on all machines |

---

## COMPILATION STATUS

✓ **Phase2CharacterIdleScreen.java** - Compiles successfully
✓ **Phase3StatusBarScreen.java** - Compiles successfully
✓ **AssetDrivenScreen.java** - Already exists with loadImage() helper
✓ **Screen.java** - Base class with RASTER-ONLY rule

---

## NEXT STEPS

1. **Register screens in Game.java** - Map GameState.CHARACTER_SELECT → Phase2CharacterIdleScreen
2. **Test visual output** - Verify sprites and bars render correctly
3. **Phase 4: Numeric Displays** - Create screen for health/ammo numbers using PNG font
4. **Phase 5: Interactive Buttons** - Create screen for buttons using pre-rendered PNG assets
5. **Phase 6: Decorations** - Add decorative UI elements from PNG assets

---

## CRITICAL REMINDERS

⚠️ **ALWAYS follow these rules:**
- ❌ NO `g.fillRect()`, `g.setColor()`, `g.drawString()`, `g.setFont()`
- ❌ NO Shape objects (Rectangle2D, Ellipse2D, Path2D)
- ✅ ONLY `g.drawImage()` with BufferedImage
- ✅ ONLY `ImageIO.read()` to load files
- ✅ Load assets ONCE at startup
- ✅ Return NULL if load fails - no fallback graphics

**User provided this as a strict, non-negotiable rule.**
The Gemini conversation confirmed this is a valid, traditional, industry-standard approach (sprite-based rendering / blitting).
