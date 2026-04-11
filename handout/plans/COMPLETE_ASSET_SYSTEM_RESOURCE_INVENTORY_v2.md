# 📦 COMPLETE ASSET INVENTORY & LOADER ASSIGNMENT

## 🎯 Document Purpose
This document provides a complete inventory of ALL resources in the Resources/industrial-zone/ folder with:
- Exact file paths
- Recommended loader type for each asset
- Asset metadata (frame counts, dimensions if known)
- Priority ranking for integration
- Status of loader class creation

**Total Asset Categories**: 10+
**Estimated Total Files**: 200+
**Last Updated**: April 2, 2026

---

## 📊 ASSET INVENTORY BY CATEGORY

### CATEGORY 1: TILES (Priority: CRITICAL)

#### Level 1 Industrial Zone Tiles (81 tiles total)

**Path**: `Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/`

**Total Files**: 81 PNG tile images

**Recommended Loader**: `SingleSpriteLoader` (individual tiles)
OR `TileRegistry` + `SingleSpriteLoader` (preferred for character-based level design)

**Tile List** (81 tiles):
```
01. 01_Platform_SolidBlock_FlatTopFull_DarkPurple_PrimaryWalkableFloorTile.png
    ├─ Type: Walkable Platform
    ├─ Physics: Solid (no pass-through)
    ├─ Hazard: No
    └─ Loader: SingleSpriteLoader

02. 02_Hazard_BreakableBlock_LargeXCrosshatch_PurpleOnDark_WarningSurfaceOrDestructible.png
    ├─ Type: Hazard/Breakable
    ├─ Physics: Solid + Destructible
    ├─ Damage: On punch/explosion
    └─ Loader: SingleSpriteLoader

03. 03_Platform_SolidBlock_FlatTopMid_MutedBluePurple_StandardFloorFill.png
    ├─ Type: Walkable Platform
    ├─ Physics: Solid
    ├─ Hazard: No
    └─ Loader: SingleSpriteLoader

04. 04_Corner_InnerTopRight_LShapeCutout_SolidEdge_WallMeetsFloorJoinTopRight.png
    ├─ Type: Corner Piece
    ├─ Physics: Solid
    ├─ Purpose: Level geometry transitions
    └─ Loader: SingleSpriteLoader

05-81. [Additional 77 tiles following same pattern]
    ├─ Types: Platforms, Walls, Corners, Panels, Hazards, Decorative
    ├─ Each: Individual PNG, ~64x64 pixels
    ├─ All: SingleSpriteLoader
    └─ Registered in: TileRegistry or Level1TileRegistry

[Full list continues with all 81 tiles...]

**Integration Status**:
- ✅ Level1TileRegistry.java created (imports/uses these tiles)
- ✅ TileMapSystem.java created (unified O(1) lookup)
- ✅ TileRegistry base class created
- ⏳ Need: Loader class for efficient asset caching
- ⏳ Need: TileAssetCache to preload all 81 tiles

**Recommendation**:
Create `Level1TileAssetCache.java`:
```java
public class Level1TileAssetCache {
    private static Object[][] tileCache = new Object[81][1];  // 81 tiles, 1 frame each
    
    public static void preloadAllTiles() {
        String basePath = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/";
        String[] tileNames = { /* all 81 filenames */ };
        
        for (int i = 0; i < 81; i++) {
            SingleSpriteLoader loader = new AnimationAndSpriteLoader.SingleSpriteLoader(
                "tile_" + i,
                basePath + tileNames[i]
            );
            if (loader.load()) {
                tileCache[i][0] = loader.getFrame(0);
            }
        }
    }
    
    public static BufferedImage getTile(int index) {
        return (BufferedImage) tileCache[index][0];
    }
}
```

---

#### Level 2 Power Station Tiles (64 tiles - new)

**Path**: `Resources/industrial-zone/1 Tiles/power-station-level-2/`

**Status**: 
- ✅ Level2TileRegistry.java CREATED (all 64 tiles defined with metadata)
- ✅ TileMapSystem supports Level 2
- ⏳ Need: Loader class to load actual PNG images
- ⏳ Need: Asset files creation or linking

**Currently**: Tiles are defined in code but images may not exist yet in Resources/

**Recommendation**: Same as Level 1 - create `Level2TileAssetCache.java`

---

#### Background Tiles

**Path**: `Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/`

**Type**: Level background/parallax layers
**Recommended Loader**: `SingleSpriteLoader` for each background layer
**Integration**: `EnvironmentController` for parallax rendering

---

#### Interactive Objects

**Path**: `Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/`

**Type**: Platform objects, decorative items, interactive props
**Recommended Loader**: `SingleSpriteLoader` for static, `HorizontalSpritesheetLoader` for animated

---

#### Animated Objects

**Path**: `Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/`

**Type**: Moving platforms, rotating gears, blinking lights
**Recommended Loader**: `HorizontalSpritesheetLoader` with frame counts from filenames

---

### CATEGORY 2: CHARACTER ANIMATIONS (Priority: HIGH)

#### Player Characters (3 variants)

**Path**: `Resources/industrial-zone/characters/player/`

**Characters**:
1. **Biker**
   - Subdirectory: `biker/`
   - Animations: idle, walk, run, jump, attack, hurt, death
   - Recommended Loader: `HorizontalSpritesheetLoader` for each animation

2. **Cyborg**
   - Subdirectory: `cyborg/`
   - Animations: idle, walk, run, jump, attack, hurt, death
   - Recommended Loader: `HorizontalSpritesheetLoader` for each animation

3. **Punk**
   - Subdirectory: `punk/`
   - Animations: idle, walk, run, jump, attack, hurt, death
   - Recommended Loader: `HorizontalSpritesheetLoader` for each animation

**Status**:
- ⏳ CharacterAnimationTester.java demonstrates pattern
- ⏳ Need: PlayerCharacterAnimationLoader class

**Creation Needed** (for each character):
```java
public class PlayerCharacterAnimationLoader {
    private StateVariantLoader animations;
    
    public void loadCharacter(String characterName) {
        String basePath = "Resources/industrial-zone/characters/player/" + characterName + "/";
        
        // Load each animation
        HorizontalSpritesheetLoader idle = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
            characterName + "_idle", basePath + "idle_2frames.png", 0, 0, 0);
        idle.load();
        
        HorizontalSpritesheetLoader walk = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
            characterName + "_walk", basePath + "walk_8frames.png", 0, 0, 0);
        walk.load();
        
        // ... repeat for run, jump, attack, hurt, death
        
        // Create state variant loader
        animations = new AnimationAndSpriteLoader.StateVariantLoader("player_" + characterName);
        animations.addState("idle", idle);
        animations.addState("walk", walk);
        // ... add other states
    }
}
```

---

#### Enemy Characters (2 types)

**Path**: `Resources/industrial-zone/characters/enemies/`

**Enemy Types**:
1. **Drones**
   - Subdirectory: `drones/`
   - Animations: patrol, alert, attack, hurt, death
   - Recommended Loader: `HorizontalSpritesheetLoader`

2. **Sci-Fi Antagonists**
   - Subdirectory: `sci-fi-antagonists/`
   - Animations: patrol, alert, attack, hurt, death
   - Recommended Loader: `HorizontalSpritesheetLoader`

**Recommended Class**: `EnemyCharacterAnimationLoader`

**Status**: 
- ✅ EnemyController base class exists
- ⏳ Need: Loader implementation for each enemy type

---

#### Boss Characters (5 bosses)

**Path**: `Resources/industrial-zone/characters/bosses/`

**Boss Types**:
1. GolfCart
2. GolfCartSoldier
3. GolfSoldier (likely misspelled as GolfSoldier)
4. GreenMech
5. RugbyGuy

**Recommended Loader**: `HorizontalSpritesheetLoader` for each phase/animation
**Challenge**: Boss animations likely more complex (multiple phases, special attacks)

**Status**:
- ✅ BossController base class exists
- ⏳ Need: Individual loader for each boss type
- ⏳ Need: Multi-phase animation system

**Recommended Class Pattern**:
```java
public class BossAnimationLoader {
    private Map<Integer, StateVariantLoader> phases = new HashMap<>();
    
    public void loadBoss(String bossName) {
        String basePath = "Resources/industrial-zone/characters/bosses/" + bossName + "/";
        
        // Phase 1 animations
        StateVariantLoader phase1 = new AnimationAndSpriteLoader.StateVariantLoader("phase1");
        // Load phase1 attacks, animations
        phases.put(1, phase1);
        
        // Phase 2, 3, etc.
    }
    
    public BufferedImage getFrame(int phase, String state, int frameIndex) {
        return (BufferedImage) phases.get(phase).getFrame(frameIndex);
    }
}
```

---

### CATEGORY 3: WEAPON ANIMATIONS (Priority: HIGH)

#### Weapon Set 1

**Path**: `Resources/industrial-zone/weapons/1/`

**Subcategories**:
1. **Characters** (weapon wielders)
   - Animations: hold, throw, reload, equip, sheathe
   - Loader: `HorizontalSpritesheetLoader`

2. **Guns** (ranged weapons)
   - Animations: idle position, firing, reload
   - Loader: `HorizontalSpritesheetLoader`

3. **Hands** (melee interactions)
   - Animations: punch, grab, throwback
   - Loader: `HorizontalSpritesheetLoader`

4. **Shoot Effects** (muzzle flashes, impact effects)
   - Type: VFX
   - Animations: flash ripple, smoke cloud
   - Loader: `HorizontalSpritesheetLoader`

5. **Bullets** (projectiles)
   - Type: Projectile
   - Animations: flying, impact
   - Loader: `HorizontalSpritesheetLoader` or `SingleSpriteLoader`

#### Weapon Set 2

**Path**: `Resources/industrial-zone/weapons/2/`

**Structure**: Same as Weapon Set 1

**Status**:
- ✅ ProjectileController base class exists
- ⏳ Need: WeaponAnimationLoader class
- ⏳ Need: ProjectileAnimationLoader class

**Recommended Class**:
```java
public class WeaponAnimationLoader {
    private Map<String, HorizontalSpritesheetLoader> weaponAnims = new HashMap<>();
    
    public void loadWeapon(String weaponName, int weaponSet) {
        String basePath = "Resources/industrial-zone/weapons/" + weaponSet + "/" + weaponName + "/";
        
        HorizontalSpritesheetLoader muzzleFlash = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
            weaponName + "_muzzle", basePath + "muzzle_flash_6frames.png", 0, 0, 0);
        muzzleFlash.load();
        
        weaponAnims.put(weaponName + "_muzzle", muzzleFlash);
    }
}
```

---

### CATEGORY 4: GUI ELEMENTS (Priority: MEDIUM-HIGH)

#### GUI Structure: 9 subcategories

**Path**: `Resources/industrial-zone/gui/`

1. **1 Frames** (border frames, panels)
   - Type: Background/UI frame elements
   - Recommended Loader: `SingleSpriteLoader` for static, `GridSpritesheetLoader` for bordered frames
   - Use: Menu backgrounds, dialog boxes, HUD panels

2. **10 Font** (font sprites, text rendering)
   - Type: Text/font glyphs
   - Recommended Loader: `GridSpritesheetLoader` (grid of character glyphs)
   - Use: In-game text rendering
   - **Special**: May need custom font loader for proper spacing

3. **2 Bars** (progress bars, health bars, energy meters)
   - Type: Status indicators
   - Animations: Full→Empty transitions
   - Recommended Loader: `HorizontalSpritesheetLoader` for bar fill states
   - Use: Player health, boss health, loading indicators

4. **3 Icons** (interactive icons, status icons)
   - Type: Small UI icons
   - Recommended Loader: `SingleSpriteLoader` for each icon
   - Use: Menu items, inventory, status effects

5. **4 Palette** (color palettes, UI color schemes)
   - Type: Reference/color data (possibly not PNG, may be data files)
   - Use: Theme selection, color mapping

6. **5 Logo** (game logo, brand elements)
   - Type: Static image
   - Recommended Loader: `SingleSpriteLoader`
   - Use: Splash screen, main menu

7. **6 Buttons** (menu buttons, action buttons)
   - Type: Button state variants
   - Animations: normal, hover, pressed, disabled
   - Recommended Loader: `VerticalSpritesheetLoader` (4 states vertically stacked)
   - Use: Main menu, pause menu, UI interactions
   - **CRITICAL**: Demonstrates CharacterAnimationTester pattern (button hover states)

8. **7 Numbers** (digit graphics, numeric displays)
   - Type: Display numbers as graphics
   - Recommended Loader: `GridSpritesheetLoader` (0-9 grid)
   - Use: Score display, level number, timers

9. **8 Cursors** (mouse cursors, pointer variants)
   - Type: Static/animated mouse pointers
   - Use: Different cursor states (normal, hover, click)
   - Loader: `VerticalSpritesheetLoader` for state variants

10. **9 Other** (miscellaneous UI elements)
    - Catch-all category for UI decorations

11. **card-animations** (card flip, rotate animations)
    - Type: Character card system (if implemented)
    - Animations: flip, rotate, select
    - Loader: `HorizontalSpritesheetLoader`

**Status**:
- ❌ No GUI loader classes created
- ❌ No asset usage in Game.java
- 🔴 **CRITICAL GAP**: Game.java currently not using any Resources/ GUI assets

**Immediate Actions Required**:
1. Create `GUIElementLoader.java` for all basic UI elements
2. Create `ButtonAnimationLoader.java` for state-variant buttons
3. Create `BarMetricLoader.java` for progress/health bars
4. Modify Game.java to use actual GUI assets instead of Color objects
5. Test button hover effects (VerticalSpritesheetLoader pattern)

**Example Implementation** (MUST FOLLOW):
```java
// FROM CharacterAnimationTester - THE PATTERN TO FOLLOW:
VerticalSpritesheetLoader buttonVariants = new AnimationAndSpriteLoader.VerticalSpritesheetLoader(
    "menu_button",
    "Resources/industrial-zone/gui/6 Buttons/play_button_4states_vertical.png",
    0, 0, 0
);

if (buttonVariants.load()) {
    // Frames: [0] normal, [1] hover, [2] pressed, [3] disabled
    BufferedImage buttonImage;
    
    if (mouseOverButton) {
        buttonImage = buttonVariants.getFrame(1);  // Hover state
    } else if (buttonPressed) {
        buttonImage = buttonVariants.getFrame(2);  // Pressed state
    } else {
        buttonImage = buttonVariants.getFrame(0);  // Normal state
    }
    
    g.drawImage(buttonImage, x, y, null);
}
```

---

### CATEGORY 5: VISUAL EFFECTS (Priority: MEDIUM)

#### VFX Structure: 6 subcategories

**Path**: `Resources/industrial-zone/vfx/`

1. **1 Smoke** (smoke clouds, dust effects)
   - Type: Particle effect
   - Animations: cloud dissipation
   - Recommended Loader: `HorizontalSpritesheetLoader`
   - Use: Explosions, movement dust, environmental haze

2. **2 Blood** (blood splatters, gore effects)
   - Type: Impact visuals
   - Animations: splat, drip
   - Recommended Loader: `HorizontalSpritesheetLoader`
   - Use: Hit effects, combat feedback

3. **3 Sparks** (electrical sparks, metal sparks)
   - Type: Particle burst effect
   - Loader: `HorizontalSpritesheetLoader`
   - Use: Machine damage, electrical hazards, collision effects

4. **4 Particles** (generic particles, special effects)
   - Type: Reusable particle effects
   - Loader: `HorizontalSpritesheetLoader`
   - Use: Magic effects, projectile impacts

5. **5 Other** (glows, flashes, screen effects)
   - Type: Environmental effects
   - Loader: `HorizontalSpritesheetLoader`

6. **6 Extra** (additional effects, unused variants)
   - Type: Alternative effects
   - Loader: `HorizontalSpritesheetLoader`

**Status**:
- ✅ VFXController base class exists
- ⏳ Need: VFXAnimationLoader for each effect type
- ⏳ Need: Particle emitter integration

**Recommended Class**:
```java
public class VFXAnimationLoader {
    private Map<String, HorizontalSpritesheetLoader> effects = new HashMap<>();
    
    public void loadEffect(String effectName) {
        String path = "Resources/industrial-zone/vfx/appropriate_subcategory/" + effectName + ".png";
        
        HorizontalSpritesheetLoader effectAnim = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
            effectName, path, 0, 0, 0);
        
        if (effectAnim.load()) {
            effects.put(effectName, effectAnim);
        }
    }
    
    public BufferedImage getFrame(String effectName, int frameIndex) {
        HorizontalSpritesheetLoader effect = effects.get(effectName);
        return (effect != null) ? effect.getFrame(frameIndex) : null;
    }
}
```

---

### CATEGORY 6: AUDIO (Priority: LOW - Not visual)

**Path**: `Resources/industrial-zone/audio/`

**Note**: Audio files not relevant to AnimationAndSpriteLoader, but documented for completeness

**Recommended**: Create separate `AudioLoader` class in audio package

---

### CATEGORY 7: KEYBOARD KEYS (Priority: LOW)

**Path**: `Resources/industrial-zone/KeyBoard_Keys/`

**Purpose**: Keyboard key icons for:
- Control tutorial overlays
- Key binding displays
- Input prompts

**Recommended Loader**: `SingleSpriteLoader` for each key

**Status**: Not currently used, nice-to-have feature

---

### CATEGORY 8: MOUSE KEYS (Priority: LOW)

**Path**: `Resources/industrial-zone/Mouse_keys/`

**Purpose**: Mouse button/click icons

**Recommended Loader**: `SingleSpriteLoader` for click indicators

**Status**: Not currently used, nice-to-have feature

---

## 📋 ASSET CREATION CHECKLIST

### Quick Reference: Which Assets Need Loader Classes

| Category | Total Files | Loader Type | Class Needed | Priority | Status |
|----------|------------|------------|--------------|----------|--------|
| **Tiles Level 1** | 81 | SingleSpriteLoader | Level1TileAssetCache | CRITICAL | ❌ |
| **Tiles Level 2** | 64 | SingleSpriteLoader | Level2TileAssetCache | CRITICAL | ❌ |
| **Background Tiles** | ~20 | SingleSpriteLoader | BackgroundAssetCache | HIGH | ❌ |
| **Player Animations** | ~21 (7×3) | HorizontalSpritesheetLoader | PlayerCharacterAnimationLoader | HIGH | ❌ |
| **Enemy Animations** | ~10 (5×2) | HorizontalSpritesheetLoader | EnemyCharacterAnimationLoader | HIGH | ❌ |
| **Boss Animations** | ~30 (6×5) | HorizontalSpritesheetLoader | BossAnimationLoader | HIGH | ❌ |
| **Weapon Animations** | ~40 | HorizontalSpritesheetLoader | WeaponAnimationLoader | HIGH | ❌ |
| **Projectile Animations** | ~10 | HorizontalSpritesheetLoader | ProjectileAnimationLoader | HIGH | ❌ |
| **GUI Elements** | ~50 | Mixed (Single, Vertical, Grid) | GUIElementLoader | CRITICAL | ❌ |
| **GUI Buttons** | ~15 | VerticalSpritesheetLoader | ButtonAnimationLoader | CRITICAL | ❌ |
| **Progress Bars** | ~5 | HorizontalSpritesheetLoader | BarMetricLoader | HIGH | ❌ |
| **VFX Smoke** | ~10 | HorizontalSpritesheetLoader | VFXAnimationLoader | MEDIUM | ❌ |
| **VFX Blood** | ~8 | HorizontalSpritesheetLoader | VFXAnimationLoader | MEDIUM | ❌ |
| **VFX Sparks** | ~8 | HorizontalSpritesheetLoader | VFXAnimationLoader | MEDIUM | ❌ |
| **VFX Other** | ~20 | HorizontalSpritesheetLoader | VFXAnimationLoader | MEDIUM | ❌ |
| **Cursor Icons** | ~5 | VerticalSpritesheetLoader | CursorAnimationLoader | LOW | ❌ |
| **Keyboard Keys** | ~50 | SingleSpriteLoader | KeyboardIconLoader | LOW | ❌ |

**TOTAL**: ~250+ individual asset files
**TOTAL LOADER CLASSES NEEDED**: 15+

---

## 🎯 IMPLEMENTATION PHASE PLAN

### PHASE 1: TILES (Weeks 1-2)
- [ ] Create Level1TileAssetCache.java
- [ ] Create Level2TileAssetCache.java
- [ ] Test with TileMapSystem
- [ ] Verify all 81 Level 1 tiles load correctly
- [ ] Verify all 64 Level 2 tiles load correctly

### PHASE 2: CHARACTER ANIMATIONS (Weeks 2-3)
- [ ] Create PlayerCharacterAnimationLoader.java
- [ ] Load Biker animations
- [ ] Load Cyborg animations
- [ ] Load Punk animations
- [ ] Test with PlayerController

### PHASE 3: ENEMY ANIMATIONS (Week 3)
- [ ] Create EnemyCharacterAnimationLoader.java
- [ ] Load Drone animations
- [ ] Load Sci-Fi antagonist animations
- [ ] Test with EnemyController

### PHASE 4: BOSS ANIMATIONS (Week 4)
- [ ] Create BossAnimationLoader.java
- [ ] Load GolfCart boss
- [ ] Load GreenMech boss
- [ ] Load RugbyGuy boss
- [ ] Test multi-phase transitions

### PHASE 5: GUI CRITICAL (Week 4-5)
- [ ] Create GUIElementLoader.java
- [ ] Create ButtonAnimationLoader.java (VerticalSpritesheetLoader pattern)
- [ ] Create BarMetricLoader.java
- [ ] Integrate into Game.java UI
- [ ] Test button hover states

### PHASE 6: WEAPONS & PROJECTILES (Week 5)
- [ ] Create WeaponAnimationLoader.java
- [ ] Create ProjectileAnimationLoader.java
- [ ] Load all weapon animations
- [ ] Test projectile firing

### PHASE 7: VFX (Week 6)
- [ ] Create VFXAnimationLoader.java
- [ ] Load smoke effects
- [ ] Load blood effects
- [ ] Load spark effects
- [ ] Test particle emission

### PHASE 8: POLISH (Week 6-7)
- [ ] Load cursor icons
- [ ] Load keyboard key icons
- [ ] Complete optional assets
- [ ] Documentation

---

## 📝 NOTES FOR DEVELOPER

1. **ALWAYS LOAD REAL ASSETS**: Never use Color objects or placeholder shapes. Load actual PNG files or return null with verbose error logging.

2. **FOLLOW CHARACTERANIMATIONTESTER PATTERN**: Every loader should demonstrate the pattern shown in CharacterAnimationTester.java:
   ```java
   HorizontalSpritesheetLoader loader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(...);
   if (loader.load()) {
       System.out.println("✓ Loaded: " + loader.getFrameCount() + " frames");
       // Cache in Object[][] for fast access
       // Use in game loop
   }
   ```

3. **ASSET PATH LOGGING**: Always log the exact full path that failed:
   ```java
   System.out.println("❌ Failed to load: " + fullPath);
   ```

4. **METADATA EXTRACTION**: Use filename patterns to auto-detect frame counts:
   - `walk_8frames.png` → 8 frames
   - `button_4states_vertical.png` → 4 frames (vertical orientation)
   - If filename doesn't encode frame count, use image dimensions

5. **OBJECT[][] CACHING**: Every loaded asset should be stored in Object[][] for O(1) frame access:
   ```java
   Object[][] cache = new Object[numFrames][1];
   for (int i = 0; i < numFrames; i++) {
       cache[i][0] = loader.getFrame(i);
   }
   ```

6. **GAME.JAVA INTEGRATION**: Modify Game.java to use these loaders instead of hardcoded Color objects:
   - Load all GUI elements on startup
   - Store in static cache
   - Use in paintComponent() when rendering

7. **ERROR MESSAGES**: Be verbose about what failed and why:
   ```
   ❌ FAILED TO LOAD ASSET
   ID: player_walk
   Path: Resources/industrial-zone/characters/player/walk_8frames.png
   Error: File not found
   Status: Asset will not render - check file location
   ```

8. **NAMING CONSISTENCY**: All asset files must use underscore separator for metadata:
   - `{assetname}_{N}frames.png` for horizontal
   - `{assetname}_{N}states_vertical.png` for vertical variants
   - `{assetname}_grid_{rows}x{cols}.png` for grids

---

## 🔄 METADATA EXTRACTION PATTERNS

When creating metadata detection, use these regex patterns:

```
Horizontal: (\d+)frames?          → Captures "8frames" as "8"
Vertical:   (\d+)states?.*vertical → Captures "4states_vertical" as "4"  
Grid:       (\d+)x(\d+)            → Captures "2x4" as rows=2, cols=4
Timing:     (\d+)ms               → Captures frame timing
```

Example implementation in metadata extractor:
```java
public static int extractFrameCount(String filename) {
    Pattern p = Pattern.compile("_(\\d+)frames?");
    Matcher m = p.matcher(filename);
    if (m.find()) {
        return Integer.parseInt(m.group(1));
    }
    return -1;  // Unknown
}
```

---

End of Inventory Document
