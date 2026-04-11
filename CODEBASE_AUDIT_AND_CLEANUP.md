# CODEBASE AUDIT & CLEANUP PLAN - Phase 5E
**Date:** April 2, 2026  
**Goal:** Identify essential files vs. removable demos/tests

---

## CRITICAL ISSUE: Build Errors Pre-exist

**Status:** Build fails with compilation errors in Game.java, ScreenManager.java, GUIEntities.java
**Not caused by:** PhysicsConstants.java or PlayerController.java updates (these are correct)
**Root cause:** Missing dependencies or broken imports (pre-existing from earlier phases)

### Current Error Summary:
```
Game.java:60, 119, 284, 381, 522 - "cannot find symbol"
ScreenManager.java:266, 273 - "cannot find symbol"
GUIEntities.java:2058, 2059, 2099, 2113, 2187, 1979, 2078, 2083, 2087, 2089 - "cannot find symbol"
```

**Action Needed:** Fix these errors OR identify which files are actually needed for the game

---

## CODEBASE INVENTORY

### TIER 1: CORE GAME ENGINE (ESSENTIAL)

#### Physics System ✓ WORKING
- **File:** `src/physics/PhysicsConstants.java` ✨ NEW (Phase 5E)
- **File:** `src/physics/Physics.java` (core physics engine)
- **File:** `src/physics/PhysicsBody.java`
- **File:** `src/physics/CollisionDetector.java`
- **File:** `src/physics/CharacterPhysicsSimulator.java`
- **Status:** PhysicsConstants is complete and tested
- **Action:** KEEP - Required for game mechanics

#### Player Control System ✓ UPDATED
- **File:** `src/PlayerController.java` ✨ UPDATED (Phase 5E)
- **File:** `src/PlayerState.java`
- **Status:** Updated to use PhysicsConstants
- **Action:** KEEP - Core player mechanics

#### Game Loop
- **File:** `src/Game.java` ❌ HAS ERRORS
- **Problem:** Lines 60, 119, 284, 381, 522 have undefined symbols
- **Status:** BROKEN - blocks all testing
- **Action:** FIX or REPLACE

#### GUI/Rendering
- **File:** `src/GameScreenSystem.java` ✓ (mentioned as working)
- **File:** `src/GameWindow.java`
- **File:** `src/ScreenController.java`
- **Status:** Should be working
- **Action:** KEEP

#### Level Systems
- **File:** `src/Level1.java` (empty - needs TILES_ASSETS)
- **File:** `src/Level2.java` (empty - needs TILES_ASSETS)
- **Status:** Stubbed out, need implementation
- **Action:** IMPLEMENT with TILES_ASSETS (Phase 2)

### TIER 2: OPTIONAL SYSTEMS (NICE-TO-HAVE)

#### AI/Enemy System
- **Files:** `src/ai/AI.java`, `src/ai/EnemyAICombat.java`, `src/ai/BehaviorTree.java`
- **Status:** Probably doesn't compile
- **Action:** REMOVE if causes errors, IMPLEMENT later if not

#### Weapons/Combat
- **Files:** `src/weapons/`, `src/Weapon.java`, `src/WeaponManager.java`
- **Status:** Import exists in Game.java → probably broken
- **Action:** REMOVE if causes errors, IMPLEMENT later

#### Animation System
- **File:** `src/animation/AnimationAndSpriteLoader.java` ✓ (mentioned as existing)
- **Status:** Should be working (used by PlayerController)
- **Action:** KEEP

### TIER 3: TEST/DEMO FILES (REMOVABLE)

These provide NO game value and clutter the codebase:

#### Testing Utilities
- `src/AssetsAnimationAndLoadingTester.java` - Test harness, not gameplay
- `src/CharacterAnimationTester.java` - GUI testing tool (has wrong constants)
- `src/CompleteGameplaySimulation.java` - Old simulation, not used
- `src/GUISystemExamples.java` - Example code only
- `src/ModularTileSystemGameIntegration.java` - Old integration test
- `src/AssetsAnimationAndLoadingTester.java` - Duplicate testing
- `src/GameProduction.java` - Unclear purpose

#### Demo Classes
- `src/BasicGameLevel.java` - Demo level
- `src/CharacterFactory.java` - Factory pattern demo (might be needed)

#### Test Files in Physics
- `src/physics/TestPhysicsBody.java`
- `src/physics/TestPhysicsEngineAcceleration.java`
- `src/physics/TestPhysicsEngineCollisions.java`
- `src/physics/TestPhysicsEngineFriction.java`
- `src/physics/TestPhysicsEngineGravity.java`
- `src/physics/TestPhysicsEngineJumping.java`

#### Directory Clutter (handout/)
- `handout/batch_planner/` - Planning documents
- `handout/main_planning_designing/` - Planning documents
- `handout/logs/` - Old logs
- `handout/*.txt` - Old data files
- `handout/*.ps1` - Extra build scripts

**Action:** REMOVE all of these to clean up workspace

---

## CLEANUP PRIORITY RANKING

### 🔴 BLOCKING (Fix Now)
1. **Game.java errors** - Prevents ANY testing
   - Action: Diagnosis needed
   - Queries: What symbols are undefined? `WeaponRenderer`? `ProjectileManager`?

2. **Determine if weapons/AI are IMPORTED but not IMPLEMENTED**
   - File imports exist but implementation might be missing
   - Action: Check if weapons/, ai/ directories have .java files

### 🟡 HIGH (Next)
1. Remove all test files in physics/ (~6 files)
2. Remove all demo/example files (AssetsAnimationAndLoadingTester, etc.)
3. Clean up handout directory (remove batch_planner, logs, etc.)

### 🟢 MEDIUM (After Phase 1)
1. Implement TILES_ASSETS for Level1 & Level2
2. Implement collision system
3. Stub out minimal AI if needed

### 🔵 LOW (Optional)
1. Weapons system (if game design needs it)
2. Advanced AI (low priority)

---

## FILE ORGANIZATION RECOMMENDATION

### Current Structure (MESSY)
```
handout/src/
  ├── Game.java (❌ broken)
  ├── PlayerController.java (✓ updated)
  ├── Level1.java (empty)
  ├── Level2.java (empty)
  ├── CharacterAnimationTester.java (❌ demo, wrong constants)
  ├── AssetsAnimationAndLoadingTester.java (❌ demo)
  ├── CompleteGameplaySimulation.java (❌ demo)
  ├── GUISystemExamples.java (❌ example)
  ├── ModularTileSystemGameIntegration.java (❌ test)
  ├── physics/
  │   ├── PhysicsConstants.java (✓ NEW)
  │   ├── Physics.java (✓)
  │   ├── TestPhysicsEngineJumping.java (❌ test)
  │   ├── TestPhysicsBodyAcceleration.java (❌ test)
  │   └── ... (5+ more test files)
  ├── animation/ (should exist, ✓)
  ├── ai/ (error sources?)
  ├── weapons/ (error sources?)
  └── ... (25+ other subdirs)
```

### Recommended Structure (CLEAN)
```
handout/src/
  ├── Game.java (CORE)
  ├── GameScreenSystem.java (CORE)
  ├── Level1.java (CORE - to implement)
  ├── Level2.java (CORE - to implement)
  ├── PlayerController.java (CORE)
  ├── physics/
  │   ├── PhysicsConstants.java (✓ NEW)
  │   ├── Physics.java
  │   ├── PhysicsBody.java
  │   ├── CollisionDetector.java
  │   └── (no test files!)
  ├── animation/ (✓)
  ├── rendering/ (working files only)
  ├── entities/ (cleanup needed)
  └── gui/ (working files only)
  
  [REMOVE]:
  - All Test*.java files
  - All example files
  - All demo games
```

---

## ACTION ITEMS FOR PHASE 5E

### 1. DIAGNOSE Game.java (IMMEDIATE)
```bash
# Get specific error messages
cd handout
javac -d bin -sourcepath src src/Game.java 2>&1 | head -50
```

**Expected:** Will show which symbols are undefined
**Typical causes:**
- Missing `WeaponRenderer` class (weapons system incomplete)
- Missing `ProjectileManager` class (weapons system incomplete)
- Missing `Enemy` class (AI system incomplete)
- Circular dependencies between classes

### 2. IS WEAPONS/AI ACTUALLY NEEDED?
- Check: Do Level1 and Level2 designs require weapons/enemies?
- If NO: Comment out weapon/AI imports in Game.java, remove those classes
- If YES: Implement the missing classes

### 3. REMOVE DEMO/TEST FILES (30 minutes)
Delete these 10+ files:
```
AssetsAnimationAndLoadingTester.java
CharacterAnimationTester.java  
CompleteGameplaySimulation.java
GUISystemExamples.java
ModularTileSystemGameIntegration.java
src/physics/TestPhysicsBody.java
src/physics/TestPhysicsEngineAcceleration.java
src/physics/TestPhysicsEngineCollisions.java
src/physics/TestPhysicsEngineFriction.java
src/physics/TestPhysicsEngineGravity.java
src/physics/TestPhysicsEngineJumping.java
```

### 4. CLEAN handout DIRECTORY (15 minutes)
Delete:
```
handout/batch_planner/
handout/main_planning_designing/
handout/logs/
handout/*.txt (old data files)
handout/check_*.py (old scripts)
handout/*.bat (redundant with .ps1)
```

### 5. VERIFY CORE COMPILATION
After cleanup:
```
cd handout
./build.ps1
java -cp bin GameScreenSystem
```

---

## EXPECTED STATE AFTER CLEANUP

✓ **Working:**
- PhysicsConstants.java (NEW)
- PlayerController.java (UPDATED)
- Physics system (EXISTING)
- Animation system (EXISTING)
- GUI/rendering (EXISTING)
- GameScreenSystem (VERIFIED WORKING)

❌ **Broken (to fix):**
- Game.java (fix imports/symbols)

📋 **Stubbed (to implement):**
- Level1.java (TILES_ASSETS)
- Level2.java (TILES_ASSETS)
- CollisionSystem.java (NEW - Phase 3)

🗑️ **Removed:**
- All test files (20+ files)
- All demo/example files (10+ files)
- Clutter in handout/ directory

**Result:** Clean, compilable codebase focused on actual game needs

---

## SUMMARY TABLE

| Category | Count | Status | Action |
|----------|-------|--------|--------|
| Core Game Files | 8 | 50% broken | Fix Game.java |
| Physics Files | 15 | ✓ Working | KEEP |
| Test Files | 12 | ❌ Demo | REMOVE |
| Example Files | 8 | ❌ Demo | REMOVE |
| AI/Weapons | 20+ | ❌ Broken | INVESTIGATE |
| Animation | 5+ | ✓ Assume working | KEEP |
| Rendering | 10+ | ✓ Assume working | KEEP |

**Cleanup Impact:** -40 files (demos/tests), +1 file (PhysicsConstants) = -39 net files

---

