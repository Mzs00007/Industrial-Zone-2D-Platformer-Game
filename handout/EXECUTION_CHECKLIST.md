# ════════════════════════════════════════════════════════════════════════════════
# CSCU9N6 GAME DEVELOPMENT - EXECUTION CHECKLIST
# 24-Hour Sprint to Complete Game Using AnimationAndSpriteLoader Assets Only
# ════════════════════════════════════════════════════════════════════════════════

Start Time: ___________
Deadline: ___________

---

## 🧹 PHASE 1: CODE CLEANUP (30 minutes)

Cleanup Execution:
---
Two Options:

**Option A - PowerShell Script (Recommended):**
```
cd handout
powershell -ExecutionPolicy Bypass -File cleanup.ps1
```

**Option B - Manual Deletion:**
Follow steps below to delete each folder

---

### Delete Folders (Execute cleanup.ps1 or delete manually):

- [ ] DELETE: ai/
  ```powershell
  Remove-Item -Path "src/ai" -Recurse -Force
  ```
  
- [ ] DELETE: camera/
  ```powershell
  Remove-Item -Path "src/camera" -Recurse -Force
  ```
  
- [ ] DELETE: core_game_entities/
  ```powershell
  Remove-Item -Path "src/core_game_entities" -Recurse -Force
  ```
  
- [ ] DELETE: entities/
  ```powershell
  Remove-Item -Path "src/entities" -Recurse -Force
  ```
  
- [ ] DELETE: gameplay/
  ```powershell
  Remove-Item -Path "src/gameplay" -Recurse -Force
  ```
  
- [ ] DELETE: gui/ (except Game.java)
  ```powershell
  Copy-Item "src/gui/Game.java" "src/Game.java.bak" -Force
  Remove-Item "src/gui" -Recurse -Force
  New-Item -ItemType Directory "src/gui" | Out-Null
  Copy-Item "src/Game.java.bak" "src/gui/Game.java" -Force
  Remove-Item "src/Game.java.bak" -Force
  ```
  
- [ ] DELETE: rendering/
  ```powershell
  Remove-Item -Path "src/rendering" -Recurse -Force
  ```
  
- [ ] DELETE: ui/
  ```powershell
  Remove-Item -Path "src/ui" -Recurse -Force
  ```
  
- [ ] DELETE: weapons/
  ```powershell
  Remove-Item -Path "src/weapons" -Recurse -Force
  Remove-Item -Path "src/Weapon.java" -Force -ErrorAction SilentlyContinue
  ```
  
- [ ] DELETE: physics/
  ```powershell
  Remove-Item -Path "src/physics" -Recurse -Force
  ```
  
- [ ] DELETE: combat/
  ```powershell
  Remove-Item -Path "src/combat" -Recurse -Force
  ```

### Delete Test Files:
- [ ] Remove all *Test.java files
- [ ] Remove all *Tester.java files
- [ ] Remove CharacterProfile.java
- [ ] Remove CharacterPhysicsTestCases.java

### Delete Misc Files:
- [ ] Remove StudentUsageExample.java
- [ ] Remove GameWindow.java
- [ ] Remove all *_Misc.java files
- [ ] Remove tiles/*Test.java
- [ ] Remove map/*Test.java
- [ ] Remove map/IntegratedLevelComparison.java

### Verify Remaining Structure:
```
✓ animation/AnimationAndSpriteLoader.java
✓ game2D/GameCore.java
✓ gui/Game.java (MAIN CLASS)
✓ tiles/
✓ map/
✓ config/
✓ dialogue/
✓ events/
✓ objectives/
✓ optimization/
✓ utils/
✓ vfx/
✓ audio/
✓ characters/

✗ DELETED: ai/, camera/, core_game_entities/, entities/
✗ DELETED: gameplay/, rendering/, ui/, weapons/, physics/, combat/
✗ DELETED: All test files, all misc files
```

Time Spent: _____ minutes | Status: [ ] Complete

---

## 📊 PHASE 2: ASSET ANALYSIS (30 minutes)

### Verify AnimationAndSpriteLoader Constants:

- [ ] PLAYER_BASE
- [ ] BOSS_BASE
- [ ] ENEMY_BASE
- [ ] DRONE_BASE
- [ ] L1_TILES_BASE
- [ ] L1_BG_BASE
- [ ] L2_TILES_BASE
- [ ] L2_BG_BASE
- [ ] GUI_FRAMES
- [ ] GUI_BUTTONS
- [ ] GUI_BARS
- [ ] GUI_ICONS
- [ ] GUI_NUMBERS
- [ ] GUI_FONT_IMAGES ← FOR TEXT RENDERING (63 character PNGs)
- [ ] VFX_SMOKE, VFX_BLOOD, VFX_SPARKS
- [ ] WEAPON_1, WEAPON_2

### Verify Nested Classes Available:
- [ ] AnimationAndSpriteLoader.Level1TileRegistry (getTile(char c))
- [ ] AnimationAndSpriteLoader.Level2TileRegistry (getTile(char c))
- [ ] AnimationAndSpriteLoader.ParallaxSystem (render method)
- [ ] AnimationAndSpriteLoader.EnemyAIBehavior
- [ ] AnimationAndSpriteLoader.BulletSpawner
- [ ] AnimationAndSpriteLoader.CharacterAnimationStateMachine
- [ ] AnimationAndSpriteLoader.GUIButtonSystemProperties
- [ ] AnimationAndSpriteLoader.GUITilesetSystem

Time Spent: _____ minutes | Status: [ ] Complete

---

## 🎮 PHASE 3: GAME.JAVA ARCHITECTURE (2 hours)

### 3.1 Imports (STRICT - NO WILDCARDS)
- [ ] import game2D.GameCore;
- [ ] import java.awt.Dimension;
- [ ] import java.awt.Toolkit;
- [ ] import java.awt.event.KeyEvent;
- [ ] import java.awt.image.BufferedImage;
- [ ] import java.io.File;
- [ ] import java.io.IOException;
- [ ] import java.util.HashMap;
- [ ] import java.util.Map;
- [ ] import javax.imageio.ImageIO;
- [ ] import javax.swing.JFrame;
- [ ] import animation.AnimationAndSpriteLoader;

**FORBIDDEN IMPORTS:**
- [ ] ❌ NO java.awt.* (wildcard)
- [ ] ❌ NO java.awt.Graphics2D (use fully qualified)
- [ ] ❌ NO java.awt.Color (use fully qualified)
- [ ] ❌ NO java.awt.Font (use fully qualified)

### 3.2 Class Declaration
- [ ] public class Game extends AnimationAndSpriteLoader

### 3.3 Core Fields
- [ ] private String gameState = "PLAYING";
- [ ] private int currentLevel = 1;
- [ ] private float cameraX = 0;
- [ ] private int playerHealth = 100;
- [ ] private int playerEnergy = 100;
- [ ] private int playerAmmo = 30;
- [ ] private int playerScore = 0;
- [ ] private Map<String, BufferedImage> imageCache = new HashMap<>();
- [ ] private Map<Character, BufferedImage> fontImageCache = new HashMap<>();
- [ ] private AnimationAndSpriteLoader.ParallaxSystem level1Parallax;
- [ ] private AnimationAndSpriteLoader.ParallaxSystem level2ParallaxDay;

### 3.4 Main Method
- [ ] public static void main(String[] args) implemented
- [ ] Creates Game instance
- [ ] Calls game.run(false, screenWidth, screenHeight)
- [ ] Welcome/splash messages printed

### 3.5 Constructor
- [ ] super() called
- [ ] setTitle(), setDefaultCloseOperation(), setPreferredSize()
- [ ] loadRasterAssets() called
- [ ] loadFontImages() called
- [ ] initializeParallaxSystems() called
- [ ] super.initialize() or parent init called

### 3.6 Asset Loading Methods
- [ ] loadRasterAssets() - loads ALL PNG/JPEG from folders
- [ ] loadAssetsFromFolder(String path) - batch loader
- [ ] loadFontImages() - loads 63 font character images
- [ ] Verifies paths exist before loading

### 3.7 Rendering Methods
- [ ] draw(Graphics2D g) - main rendering
- [ ] renderParallax(Graphics2D g) - delegates to parent
- [ ] renderTilemap(Graphics2D g) - uses Level1/2TileRegistry
- [ ] renderHUD(Graphics2D g) - health/energy bars as PNG
- [ ] renderText(Graphics2D, String, x, y, charW, charH) - FONT IMAGES ONLY
- [ ] NO direct setColor(), fillRect(), drawString() calls

### 3.8 Game Loop Methods
- [ ] update(long ms) - inherited behavior
- [ ] keyPressed(KeyEvent) - input handling

### 3.9 Helper Methods
- [ ] findCharImage(char c) → BufferedImage
- [ ] Any utility methods needed

Time Spent: _____ minutes | Status: [ ] Complete

---

## 💻 PHASE 4: GAME.JAVA IMPLEMENTATION (5 hours)

### 4.1 Start Implementation
- [ ] Create gui/Game.java with basic skeleton
- [ ] Add all imports (verified as allowed)
- [ ] Add all fields
- [ ] Compile and verify no errors

Time: _____ min | Errors: ___

### 4.2 Main & Constructor
- [ ] Implement main() method
- [ ] Implement constructor with super()
- [ ] Test compilation

Time: _____ min | Errors: ___

### 4.3 Asset Loading
- [ ] Implement loadRasterAssets()
- [ ] Implement loadAssetsFromFolder()
- [ ] Implement loadFontImages()
- [ ] Verify fonts load from GUI_FONT_IMAGES

Time: _____ min | Errors: ___

### 4.4 Initialization
- [ ] Implement initializeParallaxSystems()
- [ ] Implement initializeGUI()
- [ ] Test compilation with all methods

Time: _____ min | Errors: ___

### 4.5 Game Loop
- [ ] Implement update(long ms)
  - [ ] Update energy regeneration
  - [ ] Update camera position
  - [ ] Update parallax system
  - [ ] Call super.update()
- [ ] Implement draw(Graphics2D g)
  - [ ] Render parallax (parent)
  - [ ] Call renderGameHUD()
  - [ ] Call renderText() for HUD text
- [ ] Test compilation

Time: _____ min | Errors: ___

### 4.6 Rendering Methods
- [ ] Implement renderTilemap(Graphics2D g)
  - [ ] Use Level1TileRegistry.getTile(char)
  - [ ] Load tile PNG from cache
  - [ ] Draw with g.drawImage()
- [ ] Implement renderHUD(Graphics2D g)
  - [ ] Load health/energy bar images from GUI_BARS
  - [ ] Draw bars as PNG images
- [ ] Implement renderText(Graphics2D, String, int x, int y, int w, int h)
  - [ ] Iterate through string characters
  - [ ] Lookup character in fontImageCache
  - [ ] Draw each character image
  - [ ] Move cursor X position
- [ ] Test compilation

Time: _____ min | Errors: ___

### 4.7 Input Handling
- [ ] Implement keyPressed(KeyEvent)
  - [ ] ESC → switch level
  - [ ] SPACE → fire weapon (use parent)
  - [ ] Other keys as needed
- [ ] Test compilation

Time: _____ min | Errors: ___

### 4.8 Compile & Test Asset Loading
```bash
cd handout
javac -sourcepath src -d bin -cp bin src/Game.java 2>&1
```

- [ ] No compilation errors
- [ ] No warnings about imports
- [ ] Game.class created in bin/

Time: _____ min | Status: [ ] Complete

---

## 🎨 PHASE 5: LEVEL DESIGN (1.5 hours)

### 5.1 Level 1 Tilemap
- [ ] Create character-code based tilemap
- [ ] Use characters A-Z for tile types
- [ ] Define level width (base unit)
- [ ] Define level height (base unit)
- [ ] Place tile character codes in grid

Example Map Format:
```
"AAAAAAAAAA"
"A........A"
"A.PPPPPP.A"
"A.CCC..P.A"
"A.U.E.PP.A"
"AAAAAAAAAA"
```

- [ ] Use Level1TileRegistry.getTile(char) for lookup

### 5.2 Level 2 Tilemap
- [ ] Create character-code based tilemap
- [ ] Similar to Level 1
- [ ] Different tile set (64 types available)
- [ ] Different layout

### 5.3 Player Spawn Points
- [ ] Define Level 1 start position (x, y)
- [ ] Define Level 2 start position (x, y)

### 5.4 Enemy Placement
- [ ] Mark enemy spawn zones in tilemap
- [ ] Use parent EnemyAIBehavior
- [ ] Use parent EnemyController

### 5.5 Collectibles & Hazards
- [ ] Mark in tilemap
- [ ] Implement collision detection (use parent)

Time: _____ min | Status: [ ] Complete

---

## ⚙️ PHASE 6: GAME LOGIC (1 hour)

### 6.1 Player Health/Energy
- [ ] Health decreases on collision
- [ ] Energy regenerates over time
- [ ] Death condition when health = 0

### 6.2 Enemy AI
- [ ] Delegate to parent EnemyAIBehavior
- [ ] Use parent EnemyController for spawning

### 6.3 Weapons & Bullets
- [ ] Use parent BulletSpawner
- [ ] Ammo system (deplete on fire)
- [ ] Score +10 per bullet fired

### 6.4 Level Progression
- [ ] ESC key switches levels
- [ ] Reset camera and entities on level change

### 6.5 Win/Lose Conditions
- [ ] Lose: health = 0
- [ ] Win: eliminate all enemies (or reach end of level)

Time: _____ min | Status: [ ] Complete

---

## 🧪 PHASE 7: TESTING & POLISH (1 hour)

### Compilation Tests
```bash
cd handout
javac -sourcepath src -d bin -cp bin src/Game.java 2>&1
```
- [ ] No compilation errors
- [ ] No "package not found" errors
- [ ] No "cannot find symbol" errors
- [ ] No import warnings

### Execution Tests
```bash
cd handout
java -cp bin Game
```
- [ ] Game window opens
- [ ] No startup errors in console
- [ ] No NULL pointer exceptions
- [ ] Asset cache loads successfully

### Visual Tests
- [ ] Background parallax displays
- [ ] Tiles render from registry
- [ ] HUD bars display (as PNG images)
- [ ] Text displays using font images only
- [ ] NO colored rectangles or fallback graphics

### Functional Tests
- [ ] ESC key switches level
- [ ] SPACE fires weapon (ammo decreases)
- [ ] Health bar responds to damage
- [ ] Energy bar regenerates
- [ ] Score increases

### Performance Tests
- [ ] 60 FPS maintained
- [ ] No lag during action
- [ ] Smooth camera movement
- [ ] Smooth animation

Time: _____ min | Status: [ ] Complete

---

## ✅ FINAL QUALITY CHECKLIST

### Code Quality
- [ ] NO unused variables
- [ ] NO empty methods  
- [ ] NO commented-out code
- [ ] NO placeholder text
- [ ] Clean, readable code

### Asset Compliance
- [ ] ALL text rendered from GUI_FONT_IMAGES (PNG)
- [ ] ALL graphics from Resources/ folders
- [ ] NO dummy Color objects (java.awt.Color)
- [ ] NO fallback graphics
- [ ] NO hardcoded pixel drawing

### Import Compliance
- [ ] NO wildcard imports (java.awt.*)
- [ ] NO java.awt.Graphics2D import
- [ ] NO java.awt.Color import
- [ ] NO java.awt.Font import
- [ ] Only necessary imports present

### Codebase Cleanliness
- [ ] All duplicate classes deleted
- [ ] All test files removed
- [ ] Only Game.java in gui/ folder
- [ ] animation/ folder intact
- [ ] game2D/ folder intact

---

## 📋 SUBMISSION CHECKLIST

Before final submission:

### Files to Submit:
- [ ] src/gui/Game.java (main game class)
- [ ] src/animation/AnimationAndSpriteLoader.java (engine - unchanged)
- [ ] src/game2D/GameCore.java (foundation - unchanged)
- [ ] Compiled: bin/Game.class
- [ ] Compiled: bin/animation/*.class
- [ ] Working executable

### Documentation:
- [ ] GAME_COMPLETION_PLAN_FINAL_24HOURS.md (this file)
- [ ] cleanup.ps1 (cleanup script used)
- [ ] Code comments explaining major sections

### Testing Evidence:
- [ ] Game runs without crashing
- [ ] Console output shows proper initialization
- [ ] All visuals from assets only
- [ ] No compiler errors or warnings

---

## 🕐 TIME TRACKING

| Phase | Task | Estimated | Actual | Status |
|-------|------|-----------|--------|--------|
| 1 | Cleanup | 30 min | _____ | [ ] |
| 2 | Asset Analysis | 30 min | _____ | [ ] |
| 3 | Architecture | 2 hours | _____ | [ ] |
| 4 | Implementation | 5 hours | _____ | [ ] |
| 5 | Level Design | 1.5 hours | _____ | [ ] |
| 6 | Game Logic | 1 hour | _____ | [ ] |
| 7 | Testing | 1 hour | _____ | [ ] |
| - | **TOTAL** | **11 hours** | **_____** | - |
| - | BUFFER | 13+ hours | | |

---

## 📞 QUICK REFERENCE

### Useful Commands

**Compile:**
```bash
javac -sourcepath src -d bin -cp bin src/Game.java 2>&1
```

**Run:**
```bash
java -cp bin Game
```

**Run Cleanup:**
```bash
powershell -ExecutionPolicy Bypass -File cleanup.ps1
```

**View Assets:**
```bash
dir "Resources/industrial-zone/gui/10 Font/images"
```

---

## 🎯 SUCCESS CRITERIA

✅ Game runs under 1 second startup time
✅ All visuals from PNG/JPEG assets only
✅ Text rendered as font image PNGs
✅ No compiler warnings or errors
✅ No runtime errors or exceptions
✅ Clean, lean codebase (only Game.java + parent)
✅ Proper MVC architecture (Game = controller)
✅ Full asset utilization from AnimationandSpriteLoader

---

**Generated:** April 3, 2026
**Status:** READY TO EXECUTE
**Deadline:** <24 hours from start
---
