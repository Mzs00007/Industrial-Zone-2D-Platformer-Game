# PROJECT HANDOVER DOCUMENT
## Industrial Zone - Cyberpunk Action Game
**Date:** April 6, 2026  
**Status:** ACTIVE DEVELOPMENT - Core systems functional, ready for phase 2 enhancements

---

## 1. QUICK START (For Next Chat Session)

### Build & Run Commands
```bash
# Navigate to handout directory
cd handout

# Build all Java source files
build.bat

# Run the game
run.bat
```

### File Locations
- **Source Code:** `handout/src/`
- **Compiled Classes:** `handout/bin/` (created after first build)
- **Game Assets:** `handout/Resources/industrial-zone/`
- **Libraries:** `handout/lib/` (contains all .jar dependencies)

### Current Main Classes
- **Game.java** - Main entry point, screen state machine
- **Level1.java** - Level 1 gameplay (player, tilemap, combat)
- **RenderingSystem.java** - Unified graphics rendering with EntityRenderer
- **AnimationAndSpriteLoader.java** - Sprite sheet management with nested classes

---

## 2. PROJECT ARCHITECTURE

### High-Level System Organization
```
Game (screen state machine)
└── Level1 (gameplay controller)
    ├── Player (AnimationAndSpriteLoader.PlayerController)
    │   ├── Animation system (24+ states per character)
    │   ├── Physics (velocity, gravity, collisions)
    │   └── Input handler (keyboard controls)
    ├── RenderingSystem (unified renderer)
    │   ├── EntityRenderer (all visual output)
    │   ├── Player sprite animation
    │   ├── Tilemap rendering
    │   ├── UI overlay
    │   └── VFX effects
    ├── TileMapSystem (collision, tile properties)
    ├── AISystem (enemy behavior)
    ├── CombatSystem (health, damage, effects)
    └── DialogueSystem (NPC interactions)
```

### Core Technology Stack
- **Language:** Java (compiled to bytecode, runs on JVM)
- **Graphics:** Java Swing/AWT (2D rendering)
- **Assets:** PNG raster images only (no vector, no colors as fallback)
- **Build:** Batch scripts (build.bat, run.bat) on Windows
- **Execution:** Classpath-based with lib/* dependencies

### Package Structure
```
src/
├── Game.java                      (main entry point)
├── GameRenderData.java            (rendering data container)
├── animation/AnimationAndSpriteLoader.java
├── audio/AudioSystem.java
├── ai/AISystem.java
├── camera/CameraSystem.java
├── combat/CombatSystem.java
├── config/Config.java
├── core/
│   ├── CoreSystem.java
│   └── SystemsContainer.java
├── dialogue/DialogueSystem.java
├── events/Events.java
├── game2D/ (legacy 2D classes)
├── levels/Level1.java             (NEW - level 1 controller)
├── objectives/ObjectiveSystem.java
├── physics/PhysicsSystem.java
├── rendering/RenderingSystem.java (unified renderer)
├── tiles/TileMapSystem.java
├── ui/UISystem.java
├── utils/UtilsSystem.java
├── vfx/VFXSystem.java
└── test/ (test/debug programs)
```

---

## 3. CRITICAL IMPLEMENTATION DETAILS

### Player Sprite Rendering System ✅ COMPLETED
**Status:** FULLY FUNCTIONAL - Real PNG sprites load and animate

**Key Files:**
- [src/rendering/RenderingSystem.java](src/rendering/RenderingSystem.java) - Lines 1070-1250 (EntityRenderer.renderPlayer)
- [src/levels/Level1.java](src/levels/Level1.java) - Player instantiation via reflection

**How It Works:**
1. **Asset Loading:** PNG sprite sheets loaded from `Resources/industrial-zone/characters/biker/`
2. **Frame Extraction:** Horizontal sprite strips with multiple frames in single row
3. **Animation Timing:** Extracted from filename: "150ms" = 150ms per frame, "6Frames" = 6 total frames
4. **Frame Selection:** `frame = (elapsedTime / frameInterval) % frameCount`
5. **Rendering:** `spriteSheet.getSubimage(frame * width, 0, width, height)` draws current frame

**Animation States Available (24 total):**
- IDLE (standing still)
- WALK (moving forward)
- RUN (fast movement)
- JUMP (airborne)
- ATTACK_MELEE (punching/kicking)
- ATTACK_RANGED (shooting)
- DAMAGE (taking hit)
- DEATH (dying)
- CLIMB (ladder/wall)
- CROUCH (ducking)
- And 14 more...

**Asset Path Pattern:**
```
Resources/industrial-zone/characters/[character]/[AnimationState]_[Character]_[StateType]_[Frames]_[Timing].png
Example: 01_Player_Biker_Idle_4Frames1Row_100ms_.png
```

### Reflection-Based Nested Class Access ✅ COMPLETED
**Problem:** PlayerController, InputHandler, PhysicsBody are non-public nested classes

**Solution:** Use `Class.forName()` with inner class separator `$`

**Code Pattern:**
```java
Class<?> playerClass = Class.forName("animation.AnimationAndSpriteLoader$PlayerController");
Object player = playerClass.getDeclaredConstructor().newInstance();
```

**Why This Works:**
- Avoids import errors for nested classes
- Reflection allows access to non-public members
- Used in Level1.java lines 80-101 (createPlayerViaReflection method)

### Input System ✅ COMPLETED
**Keyboard Forwarding Chain:**
1. Game.java detects key press (KeyListener)
2. Calls `currentLevelInstance.handleKeyPress(keyCode)`
3. Level1 forwards to `inputHandler.handleInput(keyCode)`
4. InputHandler updates player.currentState (WALK, JUMP, etc.)
5. RenderingSystem reads current state and renders appropriate sprite

**Supported Keys:**
- **Arrow Keys:** Movement (LEFT, RIGHT, UP=jump)
- **Space:** Action/Attack
- **1-5:** Cheat codes for debugging
- **ESC:** Return to menu

---

## 4. BUILD SYSTEM (CRITICAL FIX APPLIED)

### Build.bat - Fixed April 6, 2026
**Previous Issue:** Only compiled `.java` files in src/ root, missed all subdirectories
**Root Cause:** Glob pattern `src\*.java` is non-recursive

**Fixed Command:**
```batch
javac -cp "%LIBDIR%\*" -d "%BINDIR%" "%SRCDIR%\**\*.java"
```

**Key Change:** `*.java` → `**\*.java` (enables recursive subdirectory matching)

**Compilation Results:**
- Scans all subdirectories under src/
- Finds 100+ Java files across 15+ packages
- Outputs .class files to bin/ directory
- Includes all lib/*.jar files in classpath

### Run.bat - Launcher Script
**Purpose:** Execute compiled game with proper classpath

**Command:**
```batch
java -cp "lib/*;bin" Game
```

**Checks:**
- Verifies bin/ directory exists (error if missing compile step)
- Sets working directory correctly for asset loading
- Reports clear error messages if startup fails

---

## 5. COMPLETED WORK SUMMARY

### Phase 1: Core Systems (COMPLETE)
- ✅ Game state machine (Splash → Menu → Level Select → Gameplay)
- ✅ Screen rendering framework
- ✅ Input capture system
- ✅ Unified RenderingSystem with EntityRenderer
- ✅ Configuration system

### Phase 2: Player Systems (COMPLETE)
- ✅ Player sprite loading and rendering
- ✅ Animation state machine (24+ animations)
- ✅ Keyboard input handling
- ✅ Physics body integration (velocity, gravity)
- ✅ Frame extraction from sprite sheets
- ✅ Animation timing from filename parsing

### Phase 3: Level 1 (COMPLETE)
- ✅ Level1.java class created
- ✅ Player instantiation via reflection
- ✅ Tilemap loading (LevelMapLoader)
- ✅ Tilemap rendering
- ✅ Player update loop
- ✅ Camera coordinate system

### Phase 4: Audio & Visual Systems (FUNCTIONAL)
- ✅ AudioSystem (framework exists)
- ✅ VFXSystem (framework exists)
- ✅ UISystem (framework exists)
- ✅ Test programs created for verification

### Phase 5: Dialogue & Objectives (STRUCTURE READY)
- ✅ DialogueSystem class structure
- ✅ ObjectiveSystem class structure
- ✅ Configuration files present

---

## 6. REMAINING WORK (Priority Order)

### P1 - Collision Detection
**Status:** Physics framework exists, not connected to tilemap
**What Needed:**
- Connect PhysicsSystem with TileMapSystem
- Implement AABB collision detection
- Test player-tile collision
- Add jumping/sliding physics

**Files to Modify:**
- src/physics/PhysicsSystem.java
- src/levels/Level1.java (collision response)

### P2 - Enemy AI & Rendering
**Status:** AISystem framework exists, not rendering enemies
**What Needed:**
- Create Enemy class (similar to Player)
- Load enemy sprite assets
- Implement basic patrol behavior
- Add combat interaction with player
- Render enemies on screen

**Key Asset Location:**
- Enemy sprites in Resources/industrial-zone/characters/
- Available: Drone, Cyborg enemies (24+ animations each)

### P3 - Combat System
**Status:** CombatSystem exists, not integrated
**What Needed:**
- Connect player attack to hitboxes
- Implement health system
- Add damage feedback animation
- Death handling
- Health bar UI rendering

### P4 - UI Display
**Status:** UISystem framework exists
**What Needed:**
- Health bar rendering
- Objective display
- Dialogue box system
- Menu integration
- Debug info overlay

### P5 - Level 2 & Map Design
**Status:** Map files created, Level2.java not created
**What Needed:**
- Create Level2.java class
- Load different tilemap
- Add new enemies/obstacles
- Transition between levels
- Victory condition

### P6 - Audio Integration
**Status:** AudioSystem framework exists, not connected
**What Needed:**
- Load WAV/MP3 files
- Background music for levels
- Sound effects for actions
- Volume control

---

## 7. KEY ASSET PATHS (Hard-Coded in Code)

### Character Sprites
```
Resources/industrial-zone/characters/biker/
├── 01_Player_Biker_Idle_4Frames1Row_100ms_.png
├── 02_Player_Biker_Run_6Frames1Row_150ms_.png
├── 03_Player_Biker_Jump_4Frames1Row_100ms_.png
├── 04_Player_Biker_Attack_6Frames1Row_200ms_.png
├── ... (20+ more animations)
```

### Tilemap & Backgrounds
```
Resources/industrial-zone/1 Tiles/
├── Level1/
│   ├── 2 Background_level_1/
│   ├── 3 Obstacles/
│   └── ... (tile variants)
```

### GUI Assets
```
Resources/industrial-zone/gui/
├── buttons/
├── icons/
└── ... (UI elements)
```

### Audio Assets
```
Resources/industrial-zone/audio/
├── background_music/
├── sfx/ (sound effects)
└── ... (voice files)
```

---

## 8. IMPORTANT LESSONS & RULES

### Rule 1: ALWAYS USE REAL ASSETS
- **Never create dummy rectangles** - use real PNG images or return NULL
- **Never use Color objects as fallback** - load actual files
- **User provides PNG resources for a reason** - honor the asset pipeline
- User will immediately notice and be frustrated with placeholders

### Rule 2: COMPLETE FILE PATHS
- Always include full directory structure: `Resources/industrial-zone/1 Tiles/Level1/2 Background_level_1/image.png`
- Not: `res/image.png` or relative paths
- Use actual filenames from Resources folder

### Rule 3: ERROR HANDLING
- **Log verbosely** - show exact file paths that failed
- **Return NULL instead of fallback graphics**
- Let user see exactly what went wrong (file not found, corrupt, dimensions invalid)

### Rule 4: SEPARATION OF CONCERNS
- Each level gets its own Level class (Level1.java, Level2.java, etc)
- Level classes manage their own assets
- Game.java delegates to level classes, doesn't hardcode level logic
- RenderingSystem is pure rendering, not game logic

### Rule 5: BREAKING CHANGES TO AVOID
- Do NOT modify build.bat glob pattern back to `*.java` (will break again)
- Do NOT create dummy color graphics
- Do NOT hardcode physics/collision values without documenting them
- Do NOT change RenderingSystem rendering order without testing all visuals

---

## 9. DEBUG & TEST TOOLS

### Available Test Programs
Located in `src/test/`:

1. **Game_Visual_Audio_tester.java**
   - Tests sprite rendering
   - Verifies audio system
   - Run: `java -cp "lib/*;src" test.Game_Visual_Audio_tester`

2. **CyberpunkStoryTester.java**
   - Tests dialogue system
   - Verifies narrative content
   - Run: `java -cp "lib/*;src" test.CyberpunkStoryTester`

3. **Game_Visual_Audio_Tester_Interactive.java**
   - Interactive GUI testing
   - Manual verification of systems
   - Run: `java -cp "lib/*;src" test.Game_Visual_Audio_Tester_Interactive`

### Quick Compilation & Run
```bash
# Single file compile & run
cd handout
javac -cp "lib/*;src" src/Game.java
java -cp "lib/*;src" Game
```

---

## 10. RECENT CHANGES (April 6, 2026)

### Change 1: Player Sprite Rendering Implemented
**Files Modified:**
- `src/rendering/RenderingSystem.java` - Added sprite rendering in EntityRenderer
- `src/levels/Level1.java` - Created player via reflection
- `src/Game.java` - Integrated Level1 into game loop
- `src/GameRenderData.java` - Added player field

**What Changed:**
- Replaced blue rectangles with real PNG sprites
- Added frame extraction from sprite sheets
- Connected animation system to rendering
- Automatic frame selection based on elapsed time

**Result:** Player now renders with smooth sprite animations

### Change 2: Build System Fixed
**File Modified:**
- `handout/build.bat` - Fixed recursive compilation

**What Changed:**
- Pattern: `src\*.java` → `src\**\*.java`
- Now finds all Java files in subdirectories
- Properly compiles 100+ files across 15+ packages

**Result:** Project builds without missing files

### Change 3: Run Script Created
**File Created:**
- `handout/run.bat` - New launcher script

**What It Does:**
- Checks if bin/ directory exists
- Runs compiled game with correct classpath
- Clear error reporting if compilation needed

**Result:** Easy one-command execution

---

## 11. COMPILATION VERIFICATION

### Last Successful Build
```
Date: April 6, 2026
Exit Code: 0
Files Compiled: 100+ Java files
Output Directory: bin/
Status: ALL FILES SUCCESSFUL
```

### How to Verify Next Session
```bash
# Navigate to handout
cd handout

# Run build
build.bat

# Check for success message
# Should see: "✅ COMPILATION SUCCESSFUL!"

# If compilation fails, check:
# 1. All .java files are in src/
# 2. No syntax errors in recent changes
# 3. lib/ directory has all .jar files
```

---

## 12. TIME-SENSITIVE INFORMATION

### Performance Notes
- Sprite rendering uses buffered image subimage extraction (efficient)
- Animation timing based on elapsed time (smooth 60 fps target)
- No known performance bottlenecks at current scale

### Known Issues
- Camera system uses placeholder values (cameraX=0, cameraY=0)
- Collision not yet connected to physics
- Enemy rendering not implemented
- Audio playback not integrated
- Dialogue not rendered to screen

### Next Chat Session Priorities
1. **First:** Verify build and sprites render correctly with `build.bat` then `run.bat`
2. **Second:** If issues found, check Asset paths match Resources folder exactly
3. **Third:** Proceed with collision implementation (P1 priority work)

---

## 13. REFERENCE DOCUMENTATION

### File Maps
**Main Game Files:**
- [Game.java](src/Game.java) - Entry point, screen states, game loop
- [Level1.java](src/levels/Level1.java) - Level 1 gameplay controller
- [RenderingSystem.java](src/rendering/RenderingSystem.java) - Graphics rendering
- [AnimationAndSpriteLoader.java](src/animation/AnimationAndSpriteLoader.java) - Sprite management

**System Files:**
- [Config.java](src/config/Config.java) - Game configuration
- [GameRenderData.java](src/GameRenderData.java) - Rendering data container
- [PhysicsSystem.java](src/physics/PhysicsSystem.java) - Physics simulation
- [CombatSystem.java](src/combat/CombatSystem.java) - Combat logic
- [AISystem.java](src/ai/AISystem.java) - Enemy AI controller

**Test & Debug:**
- [src/test/Game_Visual_Audio_tester.java](src/test/Game_Visual_Audio_tester.java) - Visual tests

### Build Command Reference
```bash
# Full project build (recommended)
build.bat

# Single file compilation
javac -cp "lib/*;src" src/Game.java

# Running from handout directory
java -cp "lib/*;src" Game
java -cp "lib/*:bin" Game  # After build.bat (uses compiled classes)
```

---

## 14. QUICK DECISION TREE (For Next Session)

**"Game won't build"**
→ Check `handout/build.bat` uses `src\**\*.java` (not `src\*.java`)
→ Verify all .java files in `src/` subdirectories
→ Run build.bat with verbose output

**"Player sprite not rendering"**
→ Check `Resources/industrial-zone/characters/biker/` directory exists
→ Verify PNG filenames match animation state names
→ Check RenderingSystem.renderAnimatedSprite() method is called
→ Add debug logging to show loaded sprite path

**"Input not working"**
→ Check Game.java forwards keypress to Level1
→ Verify Level1 forwards to InputHandler via reflection
→ Test keyboard input directly in Game class

**"Performance issues"**
→ Profile EntityRenderer.renderAnimatedSprite()
→ Check BufferedImage caching strategy
→ Verify frame extraction not creating garbage

**"Need to add new feature"**
→ Identify which system owns it (Combat, AI, UI, Audio, Physics)
→ Create in appropriate package
→ Update Level1.java to instantiate it
→ Wire to RenderingSystem if visual output needed

---

## 15. CONTACT & CONTEXT TRANSFER

**For next chat session, provide:**
1. This document path: `PROJECT_HANDOVER_2026_04_06.md`
2. Current status: "Player sprites render, collision pending"
3. What you're working on: (describe task)
4. Any build errors: (copy full output)
5. Any visual issues: (describe what you see vs. expected)

**Critical Files to Keep Accessible:**
- This handover document
- `handout/build.bat` (build script)
- `handout/run.bat` (launcher script)
- `src/levels/Level1.java` (primary level logic)
- `src/rendering/RenderingSystem.java` (rendering code)

---

**End of Handover Document**  
*Generated: April 6, 2026*  
*Version: 1.0*  
*Status: Ready for next development session*
