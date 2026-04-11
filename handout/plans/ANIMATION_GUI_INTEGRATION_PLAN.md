# Animation & Sprite Loader Integration Plan for GUI
**Date:** April 3, 2026  
**Objective:** Enhance GUI menus with animated elements from AnimationAndSpriteLoader

---

## 📋 PHASE 1: Key Nested Classes to Use

### TIER 1: Image/Asset Loading (Foundation)
| Class | Purpose | In GUI |
|-------|---------|--------|
| `SpriteMetadata` | Analyzes sprite dimensions and props | Auto-detect button/logo sizes |
| `SingleSpriteLoader` | Load static PNG files | Logo, background tiles |
| `HorizontalSpritesheetLoader` | Slice horizontal sprite rows | Animated buttons (8-frame hover) |
| `StateVariantLoader` | Multiple animation sets | Menu transitions (fade, slide) |

### TIER 2: Animation Control (Playback)
| Class | Purpose | In GUI |
|-------|---------|--------|
| `TileRegistry` | Character→Asset path mapping | Level tile buttons |
| `GameStateManager` | Game progression tracking | Menu state (MENU→PLAYING→PAUSED) |
| `InputHandler` | Keyboard/mouse input unified | Detect menu navigation |

---

## 🎨 PHASE 2: GUI Screen Enhancements

### MainMenuScreen.java
**Current State:** Loads static PNG button frame + logo
**Enhancement:**
1. Add `HorizontalSpritesheetLoader` for animated start button (8-frame hover animation)
2. Load button sprites: `Resources/industrial-zone/gui/buttons/start_animated.png` (8 frames)
3. On mouse hover → cycle through frames using `getFrame(hoverFrame % 8)`
4. Animation timing: 50ms per frame = smooth glow effect

**Nested Classes Used:**
```java
HorizontalSpritesheetLoader btnLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader();
btnLoader.load("Resources/industrial-zone/gui/buttons/start_animated.png", 8);
BufferedImage frame = btnLoader.getFrame(currentFrame % 8);
```

### LevelSelectScreen.java
**Current State:** Static tile-frame buttons with PNG level info
**Enhancement:**
1. Add `GridSpritesheetLoader` for level difficulty icons (4×2 grid = 8 difficulty variants)
2. Load icons: `Resources/industrial-zone/gui/difficulty/icons.png` (4 rows, 2 cols)
3. Display correct difficulty icon based on level tier (EASY, MEDIUM, HARD)
4. Add `StateVariantLoader` for level button animations (locked state vs unlocked state)

**Nested Classes Used:**
```java
GridSpritesheetLoader diffIcons = new AnimationAndSpriteLoader.GridSpritesheetLoader();
diffIcons.load("Resources/industrial-zone/gui/difficulty/icons.png", 4, 2);
BufferedImage icon = diffIcons.getFrameAt(row, col);  // Get difficulty icon
```

### CharacterSelectScreen.java
**Current State:** Static character portrait PNGs
**Enhancement:**
1. Add `SequenceFrameAnimationLoader` for character idle animations (breathing, blinking)
2. Load character sequences: `Resources/industrial-zone/gui/characters/{biker|punk|cyborg}_idle/`
3. Play idle animation on portrait during selection
4. Add `StateVariantLoader` for selected vs unselected character states

**Nested Classes Used:**
```java
String[] idleFrames = {
    "Resources/.../biker_idle_frame_01.png",
    "Resources/.../biker_idle_frame_02.png",
    "Resources/.../biker_idle_frame_03.png"
};
SequenceFrameAnimationLoader idleAnim = new AnimationAndSpriteLoader.SequenceFrameAnimationLoader();
idleAnim.loadSequence(idleFrames, new int[]{200, 200, 200});  // 200ms per frame
```

---

## 🔧 PHASE 3: Implementation Strategy

### Step 1: Create ScreenAnimationManager Utility
**New File:** `src/gui/ScreenAnimationManager.java`
- Centralizes animation playback for all menu screens
- Manages frame timing across multiple animations
- Integrates AnimationAndSpriteLoader nested classes

```java
public class ScreenAnimationManager {
    private HorizontalSpritesheetLoader buttonHoverAnim;
    private GridSpritesheetLoader difficultyIcons;
    private StateVariantLoader characterStates;
    private float elapsedTime = 0;
    
    public void update(float deltaTime) {
        elapsedTime += deltaTime;
    }
    
    public BufferedImage getButtonFrame(int frameIndex) {
        return buttonHoverAnim.getFrame(frameIndex % 8);
    }
    
    public BufferedImage getDifficultyIcon(int row, int col) {
        return difficultyIcons.getFrameAt(row, col);
    }
}
```

### Step 2: Update MainMenuScreen
1. Add `ScreenAnimationManager` field
2. Load button hover animation in `loadAssets()`
3. In `render()`, update frame based on button hover state
4. Change: Static frame → `getButtonFrame(frameCount++)`

### Step 3: Update LevelSelectScreen  
1. Add difficulty icon loader
2. In `renderLevelButton()`, swap PNG info overlay with animated difficulty icon
3. Use `getDifficultyIcon()` for visual feedback

### Step 4: Update CharacterSelectScreen
1. Load character idle animations
2. Display animated portrait instead of static PNG
3. Play continuous idle loop during character selection

---

## 🎯 PHASE 4: Asset Requirements

### Directory Structure Needed
```
Resources/industrial-zone/gui/
├── buttons/
│   └── start_animated.png          (4×2 grid: 8 hover animation frames)
├── difficulty/
│   └── icons.png                   (4×2 grid: 8 difficulty variants)
└── characters/
    ├── biker_idle/
    │   ├── frame_01.png
    │   ├── frame_02.png
    │   └── frame_03.png
    ├── punk_idle/
    │   └── (same)
    └── cyborg_idle/
        └── (same)
```

**If assets don't exist:** Load will fail silently with error logging → Fallback to static PNGs (already working)

---

## ✅ PHASE 5: Testing Plan

1. **Compile Check:** `javac -cp "src" src/gui/*.java`
2. **Import Check:** Verify AnimationAndSpriteLoader nested classes accessible
3. **Runtime Test:** `java -cp bin Game`
4. **Visual Check:**
   - Main menu: Start button glows on hover (8-frame animation)
   - Level select: Difficulty icons animate when selected
   - Character select: Character portrait animates with idle loop
5. **Error Fallback:** If animation assets missing → still renders static PNGs

---

## 📊 Expected Outcome

| Screen | Current | Enhanced | Animation |
|--------|---------|----------|-----------|
| MainMenu | Static start button | Glowing hover | HorizontalSpritesheetLoader (8 frames) |
| LevelSelect | Info text overlay | Animated difficulty icon | GridSpritesheetLoader (4×2) |
| CharSelect | Static portrait | Breathing animation | SequenceFrameAnimationLoader (3+ frames) |

**Result:** Polished, animated UI with professional feel while maintaining **PNG-only rendering** (no vector graphics)

---

## 🚨 Risk Mitigation

- ✓ All animations are **optional** (static PNG fallback works)
- ✓ Animation timing **non-blocking** (doesn't freeze menu)
- ✓ Asset loading errors **logged verbosely** (debug-friendly)
- ✓ No new dependencies (uses existing AnimationAndSpriteLoader)

