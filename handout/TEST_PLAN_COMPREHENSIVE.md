# ═══════════════════════════════════════════════════════════════════════════════
# COMPREHENSIVE TEST PLAN FOR INDUSTRIAL ZONE 2D PLATFORMER GAME
# ═══════════════════════════════════════════════════════════════════════════════
## Complete Testing Strategy for MasterGameTestSuite.java
## Target: 618+ Java Files | 250+ Test Methods | Full Coverage

---

## TABLE OF CONTENTS
1. [Executive Summary](#executive-summary)
2. [Testing Philosophy & Methodology](#testing-philosophy)
3. [Part 1: Core Game Infrastructure](#part-1-core-infrastructure)
4. [Part 2: Asset Loading System](#part-2-asset-loading) ⭐ **CRITICAL**
5. [Part 3: Animation System](#part-3-animation-system) ⭐ **CRITICAL**
6. [Part 4: AI Behavior System](#part-4-ai-system) ⭐ **CRITICAL**
7. [Part 5: Collision Detection System](#part-5-collision-system) ⭐ **CRITICAL**
8. [Part 6: File Loading & Parsing](#part-6-file-loading) ⭐ **CRITICAL**
9. [Part 7: Entity Management](#part-7-entity-management)
10. [Part 8: Physics Engine](#part-8-physics-engine)
11. [Part 9: Game State & Management](#part-9-game-state)
12. [Part 10: Input & UI Controllers](#part-10-input-ui)
13. [Part 11: Level & Tile System](#part-11-level-tiles)
14. [Part 12: Integration Tests](#part-12-integration)
15. [Part 13: Stress & Performance](#part-13-performance)
16. [Part 14: Edge Cases](#part-14-edge-cases)
17. [Test Execution Order](#test-execution-order)

---

## EXECUTIVE SUMMARY

### Purpose of This Document
This comprehensive test plan provides a **complete blueprint** for testing all 618+ Java files in the Industrial Zone 2D Platformer Game. Every test is designed to validate critical game functionality, ensure proper integration between subsystems, and catch edge cases that could cause runtime failures.

### Why Testing Matters for This Project
1. **Game Stability**: Platformers require precise physics, collision detection, and input response. Even small bugs can make the game unplayable.
2. **Asset Loading**: The game loads 300+ sprite images, tile sets, audio files. Any loading failure breaks the game visually or functionally.
3. **State Management**: Transitions between menu → playing → paused → game over must be seamless.
4. **Performance**: With spatial grids, particle effects, parallax scrolling, animations, the game must run at 60 FPS consistently.

### Key Testing Principles

#### 1. **Single File Architecture** 
All tests reside in `MasterGameTestSuite.java`. No external test files will be created.
- **Rationale**: Simplifies test execution, makes it easier to run all tests at once
- **Implementation**: Use nested static classes or method naming conventions to organize tests by category

#### 2. **Systematic Coverage**
Every public method across all 618 files gets at least one test case.
- **Rationale**: Ensures no class is left untested, catches issues in rarely-used utility methods
- **Implementation**: Tests grouped by package (game2D, entities, physics, managers, etc.)

#### 3. **Dependency-Aware Execution**
Tests execute in order of class dependencies (foundation → entities → integration).
- **Rationale**: If GameCore fails, no point testing Game.java which depends on it
- **Implementation**: 10-phase execution plan (see [Test Execution Order](#test-execution-order))

#### 4. **Real-World Scenarios**
Tests simulate actual gameplay: player jumping, enemies attacking, health depleting, level loading.
- **Rationale**: Unit tests alone miss integration bugs; need scenario tests
- **Implementation**: Integration tests in Part 8 that string together multiple subsystems

---

## TESTING PHILOSOPHY & METHODOLOGY

### What Makes a Good Test?
Each test in this plan follows the **AAA Pattern** (Arrange, Act, Assert):
1. **Arrange**: Set up test data (create objects, set initial values)
2. **Act**: Execute the method being tested
3. **Assert**: Verify the result matches expectations

### Test Categories Explained

#### Unit Tests (Parts 1-7)
- **What**: Test individual methods in isolation
- **Why**: Catch bugs in specific functionality (e.g., gravity calculation, collision detection)
- **Example**: Testing `applyGravity(0, 0.016)` returns correct velocity increase

#### Integration Tests (Part 8)
- **What**: Test multiple subsystems working together
- **Why**: Ensure player movement + physics + collision + animation all coordinate properly
- **Example**: Player jumps → physics applies gravity → collision detects ground → animation plays landing state

#### Performance Tests (Part 9)
- **What**: Measure system performance under load (1000 entities, 10000 collision checks)
- **Why**: Game must run at 60 FPS even with many enemies/particles on screen
- **Example**: Render 100 enemies + 50 projectiles in under 16ms per frame

#### Edge Case Tests (Part 10)
- **What**: Test boundary conditions and unusual inputs
- **Why**: Players will try to break the game (walk off map, spam jump, negative health)
- **Example**: What happens when player position is (Float.MAX_VALUE, -1000)?

---

## ╔═══════════════════════════════════════════════════════════════════════════╗
## ║  PART 1: CORE GAME INFRASTRUCTURE TESTS                                   ║
## ╚═══════════════════════════════════════════════════════════════════════════╝

**Package**: `game2D`, `framework`  
**Purpose**: Foundation layer that all other systems depend on  
**Priority**: CRITICAL - If these fail, entire game cannot run  
**Estimated Test Count**: 15 tests

---

### 1.1 GAME CORE & MAIN GAME LOOP
**File**: `game2D/GameCore.java`  
**Total Lines**: ~350 | **Public Methods**: 6  
**Purpose**: Abstract base class providing game loop, frame timing, double-buffered rendering, and keyboard input handling

#### Why This Class Is Critical
GameCore is the **heart of the entire game**. It extends JFrame and implements Runnable, providing:
- The main game loop running at 60 FPS target
- Double-buffered rendering (prevents screen tearing)
- Keyboard event handling (captures player input)
- Image loading utilities (loads all sprites/tiles)

If GameCore fails, **nothing** in the game will work. This is why we test it first.

---

#### Method 1: `run(boolean full, int x, int y)`
**Purpose**: Initialize game window and start the main game loop

**What It Does**:
- Creates JFrame window (fullscreen or windowed based on `full` parameter)
- Sets up double buffering with BufferStrategy
- Registers keyboard listeners
- Starts the game loop thread

**Why We Test This**:
- Ensures window creation doesn't throw exceptions
- Validates buffer is created (null buffer = crash on first render)
- Confirms game loop thread starts successfully

**Test Cases**:
```java
@Test
public void test_GameCore_run_WindowedMode() {
    // ARRANGE: Create game instance
    Game game = new Game();
    
    // ACT: Run game in windowed mode 1200x800
    game.run(false, 1200, 800);
    
    // ASSERT: Buffer created successfully
    assertNotNull("Frame buffer should be created", game.buffer);
    assertTrue("Game should be running", game.isRunning());
    assertEquals("Window width should be 1200", 1200, game.getWidth());
    assertEquals("Window height should be 800", 800, game.getHeight());
}

@Test
public void test_GameCore_run_FullscreenMode() {
    // ARRANGE: Create game instance
    Game game = new Game();
    
    // ACT: Run game in fullscreen mode
    game.run(true, 0, 0); // Fullscreen ignores x,y params
    
    // ASSERT: Fullscreen mode enabled
    assertTrue("Window should be fullscreen", game.isFullscreen());
    assertNotNull("Frame buffer should exist", game.buffer);
}

@Test
public void test_GameCore_run_InvalidDimensions() {
    // ARRANGE & ACT & ASSERT: Negative dimensions should fallback to default
    Game game = new Game();
    game.run(false, -100, -200);
    assertTrue("Width should be positive", game.getWidth() > 0);
    assertTrue("Height should be positive", game.getHeight() > 0);
}
```

**Expected Results**:
- ✅ Window created without exceptions
- ✅ Buffer strategy initialized (prevents crash on render)
- ✅ Game loop starts (thread running)
- ✅ Invalid dimensions handled gracefully

---

#### Method 2: `gameLoop()`
**Purpose**: Main game loop executing update/render cycle at 60 FPS

**What It Does**:
- Runs in infinite loop until game stops
- Tracks delta time between frames
- Calls `update(elapsedTime)` every frame (game logic)
- Calls `draw(Graphics2D)` every frame (rendering)
- Limits frame rate to 60 FPS (16.67ms per frame)

**Why We Test This**:
- Game loop is the backbone of gameplay timing
- Incorrect timing → animations run too fast/slow
- No FPS cap → CPU usage 100%, battery drain on laptops
- Missing update/draw calls → game freezes

**Test Cases**:
```java
@Test
public void test_GameCore_gameLoop_RunsCorrectly() {
    // ARRANGE: Create game, start loop
    Game game = new Game();
    game.run(false, 800, 600);
    AtomicInteger updateCount = new AtomicInteger(0);
    AtomicInteger drawCount = new AtomicInteger(0);
    
    // Override update/draw to track call count
    game.setUpdateCallback(() -> updateCount.incrementAndGet());
    game.setDrawCallback(() -> drawCount.incrementAndGet());
    
    // ACT: Run loop for 500ms (~30 frames at 60 FPS)
    Thread.sleep(500);
    game.stop();
    
    // ASSERT: Update and draw called approximately 30 times
    assertTrue("Update called at least 25 times", updateCount.get() >= 25);
    assertTrue("Update called at most 35 times", updateCount.get() <= 35);
    assertEquals("Draw called same as update", updateCount.get(), drawCount.get());
}

@Test
public void test_GameCore_gameLoop_FPSLimit() {
    // ARRANGE: Game with FPS counter
    Game game = new Game();
    game.run(false, 800, 600);
    
    // ACT: Run for 2 seconds, measure FPS
    Thread.sleep(2000);
    int fps = game.getFPS();
    game.stop();
    
    // ASSERT: FPS should be close to 60 (±10 for system variance)
    assertTrue("FPS should be at least 50", fps >= 50);
    assertTrue("FPS should be at most 70", fps <= 70);
}
```

**Expected Results**:
- ✅ Update called ~60 times per second
- ✅ Draw called same number as update
- ✅ FPS capped near 60 (prevents CPU overload)
- ✅ Loop stops cleanly when game.stop() called

---

#### Method 3: `update(long elapsedTime)`
**Purpose**: Abstract method for game logic (implemented by Game.java)

**What It Does**:
- Abstract method that subclasses override
- Receives elapsed time in milliseconds since last frame
- This is where all game logic happens (player movement, enemy AI, collision detection)

**Why We Test This**:
- Ensures subclass implementations are called
- Validates elapsed time is passed correctly
- Confirms exceptions in update don't crash game loop

**Test Cases**:
```java
@Test
public void test_GameCore_update_CalledWithCorrectTiming() {
    // ARRANGE: Mock game with tracked update calls
    final long[] receivedElapsedTime = {0};
    Game game = new Game() {
        @Override
        public void update(long elapsedTime) {
            receivedElapsedTime[0] = elapsedTime;
        }
    };
    game.run(false, 800, 600);
    
    // ACT: Run one frame
    Thread.sleep(20); // Slightly more than 16ms
    game.stop();
    
    // ASSERT: Update received positive elapsed time
    assertTrue("Elapsed time should be > 0", receivedElapsedTime[0] > 0);
    assertTrue("Elapsed time should be < 100ms", receivedElapsedTime[0] < 100);
}

@Test
public void test_GameCore_update_ZeroElapsedTime() {
    // ARRANGE: Game that receives near-zero elapsed time
    Game game = new Game();
    
    // ACT: Call update manually with 0ms
    try {
        game.update(0);
        // ASSERT: No exceptions thrown, game handles zero gracefully
        assertTrue("Update with 0ms should not crash", true);
    } catch (Exception e) {
        fail("Update should handle 0ms without exception: " + e.getMessage());
    }
}

@Test
public void test_GameCore_update_LargeElapsedTime() {
    // ARRANGE & ACT: Update with very large time (simulating lag spike)
    Game game = new Game();
    
    try {
        game.update(5000); // 5 second lag spike
        // ASSERT: Game should clamp or handle gracefully, not teleport entities
        assertTrue("Update should handle large delta without breaking physics", true);
    } catch (Exception e) {
        fail("Update should handle large delta time: " + e.getMessage());
    }
}
```

**Expected Results**:
- ✅ Update called every frame with elapsed time > 0
- ✅ Delta time in reasonable range (10-30ms for 60 FPS)
- ✅ Zero/huge delta times handled without crashes
- ✅ Exceptions in update logged but don't kill game loop

---

#### Method 4: `draw(Graphics2D g)`
**Purpose**: Abstract method for rendering (implemented by Game.java)

**What It Does**:
- Abstract method for all rendering operations
- Receives Graphics2D object from double buffer
- Subclasses draw background, tiles, entities, UI here

**Why We Test This**:
- Null Graphics2D crashes game instantly
- Drawing errors (missing images) can freeze rendering
- Confirms draw is called after update every frame

**Test Cases**:
```java
@Test
public void test_GameCore_draw_CalledWithValidGraphics() {
    // ARRANGE: Track draw calls
    final boolean[] drawCalled = {false};
    final Graphics2D[] receivedGraphics = {null};
    
    Game game = new Game() {
        @Override
        public void draw(Graphics2D g) {
            drawCalled[0] = true;
            receivedGraphics[0] = g;
        }
    };
    game.run(false, 800, 600);
    
    // ACT: Wait for at least one frame
    Thread.sleep(50);
    game.stop();
    
    // ASSERT: Draw was called with valid Graphics2D
    assertTrue("Draw should have been called", drawCalled[0]);
    assertNotNull("Graphics2D should not be null", receivedGraphics[0]);
}

@Test
public void test_GameCore_draw_NullGraphicsHandling() {
    // ARRANGE: Game with null graphics (edge case)
    Game game = new Game();
    
    // ACT & ASSERT: Should not crash if Graphics2D is null
    try {
        game.draw(null);
        // Implementation should check for null and return early
        assertTrue("Draw should handle null Graphics2D gracefully", true);
    } catch (NullPointerException e) {
        fail("Draw should check for null Graphics2D before use");
    }
}
```

**Expected Results**:
- ✅ Draw called every frame after update
- ✅ Graphics2D object is never null
- ✅ Rendering exceptions logged but don't crash loop
- ✅ Missing images don't prevent other draws

---

#### Method 5: `getFPS()`
**Purpose**: Return current frames per second

**What It Does**:
- Tracks frames rendered in last second
- Returns FPS as integer (e.g., 60, 45, 30)
- Used for performance monitoring and debug display

**Why We Test This**:
- FPS counter shows if game is performing well
- Low FPS (<30) = game lag, player frustration
- FPS fluctuation helps identify performance bottlenecks

**Test Cases**:
```java
@Test
public void test_GameCore_getFPS_ReturnsValidValue() {
    // ARRANGE: Game running for 1+ seconds
    Game game = new Game();
    game.run(false, 800, 600);
    Thread.sleep(1500); // Run for 1.5 seconds
    
    // ACT: Get FPS
    int fps = game.getFPS();
    game.stop();
    
    // ASSERT: FPS in reasonable range
    assertTrue("FPS should be >= 0", fps >= 0);
    assertTrue("FPS should be <= 120", fps <= 120); // Even with fast systems
    assertTrue("FPS should be close to 60", Math.abs(fps - 60) < 15);
}

@Test
public void test_GameCore_getFPS_BeforeGameStarts() {
    // ARRANGE: Game created but not started
    Game game = new Game();
    
    // ACT: Get FPS before run() called
    int fps = game.getFPS();
    
    // ASSERT: Should return 0 or safe default
    assertTrue("FPS before start should be 0 or positive", fps >= 0);
}
```

**Expected Results**:
- ✅ FPS returns 0 before game starts
- ✅ FPS stabilizes around 60 after 1 second
- ✅ FPS never returns negative value
- ✅ FPS updates every second (not stale value)

---

#### Method 6: `loadImage(String fileName)`
**Purpose**: Load PNG image from Resources folder

**What It Does**:
- Loads image file using ClassLoader.getResourceAsStream()
- Returns BufferedImage or null if file not found
- Used to load all sprites, tiles, backgrounds

**Why We Test This**:
- **Critical**: 300+ images must load or game has no graphics
- Wrong paths → null images → NullPointerException → crash
- File not found should return null, not throw exception

**Test Cases**:
```java
@Test
public void test_GameCore_loadImage_ValidPath() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Load known valid image
    BufferedImage img = game.loadImage("Resources/industrial-zone/gui/hud_panel.png");
    
    // ASSERT: Image loaded successfully
    assertNotNull("Valid image path should load successfully", img);
    assertTrue("Image width should be > 0", img.getWidth() > 0);
    assertTrue("Image height should be > 0", img.getHeight() > 0);
}

@Test
public void test_GameCore_loadImage_InvalidPath() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Try to load non-existent image
    BufferedImage img = game.loadImage("Resources/does_not_exist.png");
    
    // ASSERT: Should return null, not throw exception
    assertNull("Invalid path should return null", img);
}

@Test
public void test_GameCore_loadImage_NullPath() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT & ASSERT: Null path should return null gracefully
    BufferedImage img = game.loadImage(null);
    assertNull("Null path should return null", img);
}

@Test
public void test_GameCore_loadImage_EmptyPath() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Load with empty string
    BufferedImage img = game.loadImage("");
    
    // ASSERT: Empty path should return null
    assertNull("Empty path should return null", img);
}
```

**Expected Results**:
- ✅ Valid paths load images successfully
- ✅ Invalid paths return null (no exception)
- ✅ Null/empty paths handled gracefully
- ✅ Image dimensions are positive

---

### 1.2 GAME LOOP MANAGER
**File**: `framework/GameLoopManager.java`  
**Total Lines**: ~120 | **Public Methods**: 4  
**Purpose**: Precise frame timing, delta time calculation, FPS tracking with rolling average

#### Why This Class Is Critical
GameLoopManager separates timing concerns from GameCore. It provides:
- **Delta time**: Time elapsed since last frame (essential for smooth movement)
- **FPS calculation**: Rolling average over last 10 frames
- **Frame synchronization**: Ensures consistent gameplay speed on different CPUs

Without accurate timing, physics breaks (player moves faster on fast PCs, slower on slow PCs).

---

#### Method 1: `calculateDeltaTime()`
**Purpose**: Calculate time elapsed since last call

**What It Does**:
- Uses System.currentTimeMillis() or System.nanoTime()
- Calculates delta: currentTime - lastTime
- Stores delta for getDeltaSeconds()
- Updates lastTime for next frame

**Why We Test This**:
- Delta time is foundation of all movement/physics
- Incorrect delta → objects move at wrong speed
- Zero delta → division by zero errors
- Negative delta → impossible (time goes backwards?)

**Test Cases**:
```java
@Test
public void test_GameLoopManager_calculateDeltaTime_PositiveValue() {
    // ARRANGE: Create manager, call once to initialize lastTime
    GameLoopManager manager = new GameLoopManager();
    manager.calculateDeltaTime(); // First call sets baseline
    
    // ACT: Wait 20ms, calculate delta
    Thread.sleep(20);
    long delta = manager.calculateDeltaTime();
    
    // ASSERT: Delta should be ~20ms (±5ms for system variance)
    assertTrue("Delta should be >= 15ms", delta >= 15);
    assertTrue("Delta should be <= 30ms", delta <= 30);
}

@Test
public void test_GameLoopManager_calculateDeltaTime_Consistency() {
    // ARRANGE: Manager tracking multiple deltas
    GameLoopManager manager = new GameLoopManager();
    List<Long> deltas = new ArrayList<>();
    
    // ACT: Calculate delta 10 times at 16ms intervals (60 FPS)
    for (int i = 0; i < 10; i++) {
        deltas.add(manager.calculateDeltaTime());
        Thread.sleep(16);
    }
    
    // ASSERT: All deltas should be close to 16ms
    for (long delta : deltas) {
        assertTrue("Delta should be between 10-25ms", delta >= 10 && delta <= 25);
    }
}
```

**Expected Results**:
- ✅ Delta time always positive
- ✅ Delta roughly matches sleep time
- ✅ Consecutive calls give consistent deltas
- ✅ No division by zero

---

#### Method 2: `getDeltaSeconds()`
**Purpose**: Get delta time in seconds (for physics calculations)

**What It Does**:
- Returns delta time converted to seconds (delta / 1000.0)
- Used in physics: position += velocity * deltaSeconds
- Ensures movement speed independent of frame rate

**Why We Test This**:
- Physics expects seconds, not milliseconds
- Incorrect conversion → 1000x too fast movement
- Return value must be > 0

**Test Cases**:
```java
@Test
public void test_GameLoopManager_getDeltaSeconds_CorrectConversion() {
    // ARRANGE: Manager with known delta
    GameLoopManager manager = new GameLoopManager();
    manager.calculateDeltaTime();
    Thread.sleep(100); // 100ms
    manager.calculateDeltaTime();
    
    // ACT: Get delta in seconds
    float deltaSeconds = manager.getDeltaSeconds();
    
    // ASSERT: Should be ~0.1 seconds (±0.05 for variance)
    assertTrue("Delta seconds should be >= 0.08", deltaSeconds >= 0.08f);
    assertTrue("Delta seconds should be <= 0.15", deltaSeconds <= 0.15f);
}

@Test
public void test_GameLoopManager_getDeltaSeconds_Never_Negative() {
    // ARRANGE: Manager
    GameLoopManager manager = new GameLoopManager();
    
    // ACT: Get delta seconds multiple times
    for (int i = 0; i < 100; i++) {
        manager.calculateDeltaTime();
        float deltaSeconds = manager.getDeltaSeconds();
        
        // ASSERT: Always non-negative
        assertTrue("Delta seconds must be >= 0", deltaSeconds >= 0);
    }
}
```

**Expected Results**:
- ✅ Conversion from ms to seconds correct
- ✅ Value always >= 0
- ✅ Typical range 0.01-0.03 (60 FPS = 0.0167s)

---

#### Method 3: `updateFPS()`
**Purpose**: Update rolling average FPS counter

**What It Does**:
- Counts frames in current second
- Every 1 second, updates FPS value
- Uses rolling average to smooth fluctuations

**Why We Test This**:
- FPS displayed to player must be accurate
- Fluctuating FPS (60 → 30 → 60) looks bad
- Rolling average provides smooth value

**Test Cases**:
```java
@Test
public void test_GameLoopManager_updateFPS_CalculatesCorrectly() {
    // ARRANGE: Manager simulating 60 FPS
    GameLoopManager manager = new GameLoopManager();
    
    // ACT: Call updateFPS 60 times with 16ms deltas
    for (int i = 0; i < 60; i++) {
        Thread.sleep(16);
        manager.updateFPS();
    }
    
    // ASSERT: FPS should be ~60 (±10 for system variance)
    int fps = manager.getFPS();
    assertTrue("FPS should be >= 50", fps >= 50);
    assertTrue("FPS should be <= 70", fps <= 70);
}

@Test
public void test_GameLoopManager_updateFPS_RollingAverage() {
    //ARRANGE: Manager tracking FPS changes
    GameLoopManager manager = new GameLoopManager();
    
    // ACT: Simulate FPS drop (60 FPS → 30 FPS)
    for (int i = 0; i < 60; i++) manager.updateFPS(); // 60 FPS
    int fps1 = manager.getFPS();
    
    Thread.sleep(500); // Pause (drops FPS)
    for (int i = 0; i < 30; i++) manager.updateFPS(); // 30 FPS
    int fps2 = manager.getFPS();
    
    // ASSERT: FPS should drop from ~60 to ~30
    assertTrue("Initial FPS ~60", Math.abs(fps1 - 60) < 15);
    assertTrue("Dropped FPS ~30", fps2 < fps1);
}
```

**Expected Results**:
- ✅ FPS accurately reflects frame rate
- ✅ Rolling average smooths spikes
- ✅ Updates every second
- ✅ Range 0-120 (sanity check)

---

#### Method 4: `getFPS()`
**Purpose**: Return current FPS value

**Test Cases**:
```java
@Test
public void test_GameLoopManager_getFPS_InitialValue() {
    // ARRANGE: New manager
    GameLoopManager manager = new GameLoopManager();
    
    // ACT & ASSERT: Should start at 0 or positive
    int fps = manager.getFPS();
    assertTrue("Initial FPS should be >= 0", fps >= 0);
}
```

**Expected Results**:
- ✅ Returns integer FPS
- ✅ Never negative
- ✅ Matches updateFPS calculations

---

---

## ╔═══════════════════════════════════════════════════════════════════════════╗
## ║  PART 2: ASSET LOADING SYSTEM TESTS  ⭐ CRITICAL                          ║
## ╚═══════════════════════════════════════════════════════════════════════════╝

**Package**: `game2D`, `utilities`, `assets`  
**Purpose**: Load ALL game assets (sprites, tiles, audio) from Resources folder  
**Priority**: ⭐ **CRITICAL** - Without assets, game has NO GRAPHICS/SOUNDS  
**Estimated Test Count**: 40 tests

---

### Why Asset Loading Is THE MOST CRITICAL System

**If Asset Loading Fails, The Entire Game Is BROKEN**:
- ❌ **No player sprites** → Invisible player character
- ❌ **No enemy sprites** → Invisible enemies
- ❌ **No tile images** → Black screen, no level visuals
- ❌ **No background images** → No parallax scrolling
- ❌ **No audio files** → Silent game (no music, no sound effects)
- ❌ **NULL images** → NullPointerException → **INSTANT CRASH**

**Asset Loading Is The Foundation**:
```
Asset Loading → Animation System → Game Rendering → Playable Game
     ↓               ↓                    ↓
  (SPRITES)      (FRAMES)            (VISIBLE)
```

Without successful asset loading, you can have perfect physics, perfect AI, perfect collision - **but the player sees NOTHING**.

---

### 2.1 SPRITE LOADING - CHARACTER SPRITES
**File**: `game2D/GameCore.java` → `loadImage(String path)`  
**File**: `utilities/SpriteLoader.java`  
**Purpose**: Load player and enemy sprite sheets from `Resources/industrial-zone/characters/`

#### What Assets Must Load
The game has **3 player characters**, each with **6 animation states**, each with **3-8 frames**:
- **BIKER**: `Resources/industrial-zone/characters/Biker/`
  - IDLE: 4 frames
  - WALK: 6 frames
  - JUMP: 3 frames
  - ATTACK: 4 frames
  - HIT: 2 frames
  - DEATH: 5 frames
- **PUNK**: `Resources/industrial-zone/characters/Punk/` (same structure)
- **CYBORG**: `Resources/industrial-zone/characters/Cyborg/` (same structure)

**Total Character Sprites**: 3 characters × 6 states × ~4 frames = **~72 sprite images**

---

#### Method 1: `loadImage(String filePath)`
**Purpose**: Load single PNG image from Resources folder

**What It Does**:
```java
public BufferedImage loadImage(String filePath) {
    try {
        InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
        if (is == null) {
            System.err.println("ERROR: File not found: " + filePath);
            return null;
        }
        return ImageIO.read(is);
    } catch (IOException e) {
        System.err.println("ERROR: Failed to load image: " + filePath);
        e.printStackTrace();
        return null;
    }
}
```

**Why We Test This**:
- **Single point of failure**: ALL images load through this method
- Wrong path (e.g., `/Resources/` instead of `Resources/`) → 300+ images fail
- NULL return → calling code must handle it or crash

**Test Cases**:
```java
@Test
public void test_AssetLoading_loadImage_ValidPlayerSprite() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Load BIKER IDLE frame 1
    BufferedImage img = game.loadImage("Resources/industrial-zone/characters/Biker/Idle/Idle-001.png");
    
    // ASSERT: Image loaded successfully
    assertNotNull("BIKER Idle-001 should load successfully", img);
    assertTrue("Image width should be > 0", img.getWidth() > 0);
    assertTrue("Image height should be > 0", img.getHeight() > 0);
    assertEquals("Image type should be ARGB", BufferedImage.TYPE_INT_ARGB, img.getType());
}

@Test
public void test_AssetLoading_loadImage_AllPlayerCharacters() {
    // ARRANGE: Game and character types
    Game game = new Game();
    String[] characters = {"Biker", "Punk", "Cyborg"};
    
    // ACT & ASSERT: Load first IDLE frame for each character
    for (String character : characters) {
        String path = "Resources/industrial-zone/characters/" + character + "/Idle/Idle-001.png";
        BufferedImage img = game.loadImage(path);
        assertNotNull(character + " Idle-001 should load", img);
        assertTrue(character + " image width > 0", img.getWidth() > 0);
    }
}

@Test
public void test_AssetLoading_loadImage_InvalidPath() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Try to load non-existent image
    BufferedImage img = game.loadImage("Resources/does_not_exist.png");
    
    // ASSERT: Should return NULL (not throw exception)
    assertNull("Non-existent file should return null", img);
}

@Test
public void test_AssetLoading_loadImage_NullPath() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Pass null path
    BufferedImage img = game.loadImage(null);
    
    // ASSERT: Should return NULL gracefully
    assertNull("Null path should return null without crashing", img);
}

@Test
public void test_AssetLoading_loadImage_EmptyPath() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Pass empty string
    BufferedImage img = game.loadImage("");
    
    // ASSERT: Should return NULL
    assertNull("Empty path should return null", img);
}

@Test
public void test_AssetLoading_loadImage_WrongExtension() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Try to load .txt file as image
    BufferedImage img = game.loadImage("Resources/maps/level_1/map.txt");
    
    // ASSERT: Should return NULL (not a valid image format)
    assertNull("Non-image file should return null", img);
}
```

**Expected Results**:
- ✅ Valid PNG files load successfully
- ✅ Loaded images have width/height > 0
- ✅ Invalid paths return NULL (no exception)
- ✅ NULL/empty paths handled gracefully
- ✅ Non-image files return NULL

---

#### Method 2: `loadCharacterSprites(CharacterType character)`
**Purpose**: Load all 6 animation states for a character

**What It Does**:
```java
public Map<AnimationState, BufferedImage[]> loadCharacterSprites(CharacterType character) {
    Map<AnimationState, BufferedImage[]> sprites = new HashMap<>();
    String basePath = "Resources/industrial-zone/characters/" + character.getName() + "/";
    
    // Load IDLE state (4 frames)
    BufferedImage[] idle = new BufferedImage[4];
    for (int i = 0; i < 4; i++) {
        idle[i] = loadImage(basePath + "Idle/Idle-" + String.format("%03d", i+1) + ".png");
    }
    sprites.put(AnimationState.IDLE, idle);
    
    // Load WALK state (6 frames)
    BufferedImage[] walk = new BufferedImage[6];
    for (int i = 0; i < 6; i++) {
        walk[i] = loadImage(basePath + "Walk/Walk-" + String.format("%03d", i+1) + ".png");
    }
    sprites.put(AnimationState.WALK, walk);
    
    // ... (similar for JUMP, ATTACK, HIT, DEATH)
    
    return sprites;
}
```

**Test Cases**:
```java
@Test
public void test_AssetLoading_loadCharacterSprites_BIKER() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Load all BIKER sprites
    Map<AnimationState, BufferedImage[]> sprites = game.loadCharacterSprites(CharacterType.BIKER);
    
    // ASSERT: All 6 states loaded
    assertNotNull("BIKER sprites should load", sprites);
    assertEquals("Should have 6 animation states", 6, sprites.size());
    
    // ASSERT: IDLE state has 4 frames
    BufferedImage[] idle = sprites.get(AnimationState.IDLE);
    assertNotNull("IDLE state should exist", idle);
    assertEquals("IDLE should have 4 frames", 4, idle.length);
    for (int i = 0; i < idle.length; i++) {
        assertNotNull("IDLE frame " + i + " should not be null", idle[i]);
    }
    
    // ASSERT: WALK state has 6 frames
    BufferedImage[] walk = sprites.get(AnimationState.WALK);
    assertEquals("WALK should have 6 frames", 6, walk.length);
    for (int i = 0; i < walk.length; i++) {
        assertNotNull("WALK frame " + i + " should not be null", walk[i]);
    }
}

@Test
public void test_AssetLoading_loadCharacterSprites_AllCharacters() {
    // ARRANGE: Game and all character types
    Game game = new Game();
    CharacterType[] characters = {CharacterType.BIKER, CharacterType.PUNK, CharacterType.CYBORG};
    
    // ACT & ASSERT: Load sprites for each character
    for (CharacterType character : characters) {
        Map<AnimationState, BufferedImage[]> sprites = game.loadCharacterSprites(character);
        assertNotNull(character + " sprites should load", sprites);
        assertEquals(character + " should have 6 states", 6, sprites.size());
        
        // Verify all states present
        assertTrue(character + " should have IDLE", sprites.containsKey(AnimationState.IDLE));
        assertTrue(character + " should have WALK", sprites.containsKey(AnimationState.WALK));
        assertTrue(character + " should have JUMP", sprites.containsKey(AnimationState.JUMP));
        assertTrue(character + " should have ATTACK", sprites.containsKey(AnimationState.ATTACK));
        assertTrue(character + " should have HIT", sprites.containsKey(AnimationState.HIT));
        assertTrue(character + " should have DEATH", sprites.containsKey(AnimationState.DEATH));
    }
}

@Test
public void test_AssetLoading_loadCharacterSprites_NoNullFrames() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Load BIKER sprites
    Map<AnimationState, BufferedImage[]> sprites = game.loadCharacterSprites(CharacterType.BIKER);
    
    // ASSERT: No frames should be NULL
    for (AnimationState state : sprites.keySet()) {
        BufferedImage[] frames = sprites.get(state);
        for (int i = 0; i < frames.length; i++) {
            assertNotNull("Frame " + i + " of " + state + " should not be null", frames[i]);
        }
    }
}
```

**Expected Results**:
- ✅ All 6 animation states load for each character
- ✅ Each state has correct number of frames
- ✅ No NULL frames (all images loaded successfully)
- ✅ Works for BIKER, PUNK, CYBORG

---

### 2.2 TILE SET LOADING
**File**: `tiles/TileManager.java`  
**Purpose**: Load tile set images for level backgrounds (platforms, hazards, decorations)

#### What Assets Must Load
Each level has its own tile set:
- **Level 1**: `Resources/industrial-zone/1 Tiles/Level1/`
  - Background tiles
  - Platform tiles
  - Hazard tiles (spikes, lasers)
  - Decoration tiles
- **Level 2**: `Resources/industrial-zone/1 Tiles/Level2/` (similar structure)

**Total Tile Images**: ~100+ unique tiles

---

#### Method 1: `loadTileSet(int levelNumber)`
**Purpose**: Load all tiles for a specific level

**Test Cases**:
```java
@Test
public void test_AssetLoading_loadTileSet_Level1() {
    // ARRANGE: TileManager
    TileManager tileManager = new TileManager();
    
    // ACT: LoadLevel 1 tiles
    BufferedImage[] tiles = tileManager.loadTileSet(1);
    
    // ASSERT: Tiles loaded
    assertNotNull("Level 1 tile set should load", tiles);
    assertTrue("Should have > 0 tiles", tiles.length > 0);
    for (int i = 0; i < tiles.length; i++) {
        assertNotNull("Tile " + i + " should not be null", tiles[i]);
        assertTrue("Tile " + i + " width > 0", tiles[i].getWidth() > 0);
    }
}

@Test
public void test_AssetLoading_loadTileSet_Level2() {
    // ARRANGE: TileManager
    TileManager tileManager = new TileManager();
    
    // ACT: Load Level 2 tiles
    BufferedImage[] tiles = tileManager.loadTileSet(2);
    
    // ASSERT: Tiles loaded
    assertNotNull("Level 2 tile set should load", tiles);
    assertTrue("Level 2 should have tiles", tiles.length > 0);
}

@Test
public void test_AssetLoading_loadTileSet_InvalidLevel() {
    // ARRANGE: TileManager
    TileManager tileManager = new TileManager();
    
    // ACT: Try to load non-existent level 999
    BufferedImage[] tiles = tileManager.loadTileSet(999);
    
    // ASSERT: Should return null or empty array
    assertTrue("Invalid level should return null or empty",
               tiles == null || tiles.length == 0);
}
```

**Expected Results**:
- ✅ Level 1 tiles load successfully
- ✅ Level 2 tiles load successfully
- ✅ All tiles have width/height > 0
- ✅ Invalid level numbers handled gracefully

---

### 2.3 AUDIO LOADING
**File**: `audio/AudioManager.java`, `audio/SoundEffect.java`  
**Purpose**: Load music and sound effects from `Resources/industrial-zone/audio/`

#### What Audio Must Load
- **Music**:
  - Menu music
  - Level 1 music
  - Level 2 music
  - Boss music
- **Sound Effects**:
  - Jump sound
  - Attack sound
  - Hit/damage sound
  - Enemy death sound
  - Pickup sound
  - UI click sound

**Total Audio Files**: ~20+ WAV/MP3 files

---

#### Method 1: `loadSound(String soundName)`
**Purpose**: Load single sound effect file

**Test Cases**:
```java
@Test
public void test_AssetLoading_loadSound_JumpSound() {
    // ARRANGE: AudioManager
    AudioManager audio = new AudioManager();
    
    // ACT: Load jump sound
    SoundEffect jumpSound = audio.loadSound("jump.wav");
    
    // ASSERT: Sound loaded
    assertNotNull("Jump sound should load", jumpSound);
    assertTrue("Jump sound should be playable", jumpSound.isLoaded());
}

@Test
public void test_AssetLoading_loadSound_AllSoundEffects() {
    // ARRANGE: AudioManager and sound names
    AudioManager audio = new AudioManager();
    String[] sounds = {"jump.wav", "attack.wav", "hit.wav", "death.wav", "pickup.wav"};
    
    // ACT & ASSERT: Load each sound
    for (String soundName : sounds) {
        SoundEffect sound = audio.loadSound(soundName);
        assertNotNull(soundName + " should load", sound);
        assertTrue(soundName + " should be playable", sound.isLoaded());
    }
}

@Test
public void test_AssetLoading_loadSound_InvalidFile() {
    // ARRANGE: AudioManager
    AudioManager audio = new AudioManager();
    
    // ACT: Try to load non-existent sound
    SoundEffect sound = audio.loadSound("does_not_exist.wav");
    
    // ASSERT: Should return null or unloaded sound
    assertTrue("Invalid sound should be null or not loaded",
               sound == null || !sound.isLoaded());
}
```

**Expected Results**:
- ✅ All sound effects load successfully
- ✅ Sounds are marked as loaded/playable
- ✅ Invalid sound files handled gracefully

---

### 2.4 ASSET LOADING ERROR HANDLING
**Purpose**: Test how game handles missing or corrupted assets

#### Critical Edge Cases
**Test Cases**:
```java
@Test
public void test_AssetLoading_MissingFile_ReturnsNull() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Try to load missing sprite
    BufferedImage img = game.loadImage("Resources/industrial-zone/characters/NonExistent/Idle/Idle-001.png");
    
    // ASSERT: Should return NULL, not throw exception
    assertNull("Missing file should return null", img);
}

@Test
public void test_AssetLoading_CorruptedImage_Handled() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Try to load corrupted image file (if we have test file)
    // NOTE: Create a test file that's corrupt for this test
    BufferedImage img = game.loadImage("Resources/test/corrupted.png");
    
    // ASSERT: Should return NULL and log error
    assertNull("Corrupted image should return null", img);
}

@Test
public void test_AssetLoading_WrongFileType_Handled() {
    // ARRANGE: Game instance
    Game game = new Game();
    
    // ACT: Try to load text file as image
    BufferedImage img = game.loadImage("Resources/maps/level_1/map.txt");
    
    // ASSERT: Should return NULL
    assertNull("Text file should not load as image", img);
}

@Test
public void test_AssetLoading_NullImage_DoesNotCrash() {
    // ARRANGE: PlayerBase with potentially missing sprites
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    
    // ACT: Try to draw player even if sprites failed to load
    try {
        BufferedImage offscreen = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = offscreen.createGraphics();
        
        player.draw(g); // Should handle null sprites gracefully
        
        // ASSERT: No exception thrown
        assertTrue("Drawing with potentially missing sprites should not crash", true);
    } catch (NullPointerException e) {
        fail("Drawing should handle missing sprites gracefully: " + e.getMessage());
    }
}
```

**Expected Results**:
- ✅ Missing files return NULL (no exception)
- ✅ Corrupted images handled gracefully
- ✅ Wrong file types return NULL
- ✅ NULL images don't crash rendering

---

---

## ╔═══════════════════════════════════════════════════════════════════════════╗
## ║  PART 3: ANIMATION SYSTEM TESTS  ⭐ CRITICAL                              ║
## ╚═══════════════════════════════════════════════════════════════════════════╝

**Package**: `animation`, `entities`  
**Purpose**: Animate sprites (frame progression, state machines, timing)  
**Priority**: ⭐ **CRITICAL** - Without animation, characters are frozen static images  
**Estimated Test Count**: 30 tests

---

### Why Animation System Is Critical

**If Animation Fails, Characters Look Broken**:
- ❌ **No frame progression** → Character stuck on frame 1 (looks frozen)
- ❌ **Wrong animation speed** → Character moves too fast/slow (looks glitchy)
- ❌ **State machine broken** → Character plays WALK animation while jumping
- ❌ **Loop broken** → IDLE animation plays once then stops
- ❌ **One-shot broken** → DEATH animation loops infinitely (dead character keeps dying)

**Animation Depends on Asset Loading**:
```
Asset Loading → Animation System → Visual Gameplay
    (LOAD)         (ANIMATE)         (LOOKS CORRECT)
```

Without animation, you see a static image that never changes - **looks like the game crashed**.

---

### 3.1 ANIMATION PLAYER - FRAME PROGRESSION
**File**: `animation/AnimationPlayer.java`  
**Purpose**: Advance animation frames over time based on frame duration

#### How Animation Works
Each animation has:
- **Frames**: Array of BufferedImages (e.g., IDLE has 4 frames)
- **Frame Duration**: Time each frame displays (e.g., 100ms per frame)
- **Loop**: Whether animation repeats (IDLE loops, DEATH doesn't)

**Example**:
- IDLE animation: 4 frames, 100ms per frame
- Total animation time: 4 × 100ms = 400ms
- After 400ms, loop back to frame 0

---

#### Method 1: `update(float deltaTime)`
**Purpose**: Advance animation timer and switch frames

**What It Does**:
```java
public void update(float deltaTime) {
    animationTimer += deltaTime;
    
    if (animationTimer >= frameDuration) {
        animationTimer -= frameDuration;
        currentFrameIndex++;
        
        if (currentFrameIndex >= totalFrames) {
            if (loop) {
                currentFrameIndex = 0; // Loop back
            } else {
                currentFrameIndex = totalFrames - 1; // Stay on last frame
                isFinished = true;
            }
        }
    }
}
```

**Why We Test This**:
- Frame progression is **what makes animation animate**
- Wrong timing → animations run at wrong speed
- Broken loop → IDLE animation stops after 1 cycle
- Broken one-shot → DEATH animation loops forever

**Test Cases**:
```java
@Test
public void test_Animation_update_FrameProgression() {
    // ARRANGE: Animation with 4 frames, 100ms per frame
    AnimationPlayer anim = new AnimationPlayer(4, 0.1f, true); // 4 frames, 0.1s, loop
    assertEquals("Initial frame should be 0", 0, anim.getCurrentFrameIndex());
    
    // ACT: Update for 50ms (half frame duration)
    anim.update(0.05f);
    
    // ASSERT: Should still be on frame 0
    assertEquals("Should still be frame 0 after 50ms", 0, anim.getCurrentFrameIndex());
    
    // ACT: Update another 60ms (total 110ms = past frame duration)
    anim.update(0.06f);
    
    // ASSERT: Should advance to frame 1
    assertEquals("Should advance to frame 1 after 110ms", 1, anim.getCurrentFrameIndex());
}

@Test
public void test_Animation_update_LoopAnimation() {
    // ARRANGE: IDLE animation (4 frames, loops)
    AnimationPlayer anim = new AnimationPlayer(4, 0.1f, true);
    
    // ACT: Update for entire animation cycle (4 frames × 100ms = 400ms)
    anim.update(0.1f); // Frame 1
    anim.update(0.1f); // Frame 2
    anim.update(0.1f); // Frame 3
    anim.update(0.1f); // Frame 0 (looped back)
    
    // ASSERT: Should loop back to frame 0
    assertEquals("Should loop back to frame 0", 0, anim.getCurrentFrameIndex());
    assertFalse("Loop animation should never finish", anim.isFinished());
}

@Test
public void test_Animation_update_OneShotAnimation() {
    // ARRANGE: DEATH animation (5 frames, doesn't loop)
    AnimationPlayer anim = new AnimationPlayer(5, 0.1f, false); // No loop
    
    // ACT: Update through all 5 frames (500ms)
    for (int i = 0; i < 5; i++) {
        anim.update(0.1f);
    }
    
    // ASSERT: Should stay on last frame (4) and be finished
    assertEquals("Should stay on last frame", 4, anim.getCurrentFrameIndex());
    assertTrue("One-shot animation should be finished", anim.isFinished());
    
    // ACT: Update more (should NOT advance past last frame)
    anim.update(0.5f);
    
    // ASSERT: Still on last frame
    assertEquals("Should remain on last frame", 4, anim.getCurrentFrameIndex());
}

@Test
public void test_Animation_update_SlowAnimation() {
    // ARRANGE: Slow animation (1 second per frame)
    AnimationPlayer anim = new AnimationPlayer(3, 1.0f, true);
    
    // ACT: Update for 500ms (half frame duration)
    anim.update(0.5f);
    
    // ASSERT: Should still be on frame 0
    assertEquals("Slow animation should still be frame 0", 0, anim.getCurrentFrameIndex());
    
    // ACT: Update another 600ms (total 1100ms)
    anim.update(0.6f);
    
    // ASSERT: Should advance to frame 1
    assertEquals("Should advance to frame 1 after 1100ms", 1, anim.getCurrentFrameIndex());
}

@Test
public void test_Animation_update_FastAnimation() {
    // ARRANGE: Fast animation (50ms per frame)
    AnimationPlayer anim = new AnimationPlayer(4, 0.05f, true); // 50ms per frame
    
    // ACT: Update for one frame at 60 FPS (16ms)
    anim.update(0.016f);
    
    // ASSERT: Should still be frame 0 (16ms < 50ms)
    assertEquals("Should be frame 0", 0, anim.getCurrentFrameIndex());
    
    // ACT: Update 3 more times (16ms × 4 = 64ms total)
    anim.update(0.016f);
    anim.update(0.016f);
    anim.update(0.016f);
    
    // ASSERT: Should advance to frame 1 (64ms > 50ms)
    assertEquals("Should advance to frame 1", 1, anim.getCurrentFrameIndex());
}

@Test
public void test_Animation_update_ZeroDeltaTime() {
    // ARRANGE: Animation
    AnimationPlayer anim = new AnimationPlayer(4, 0.1f, true);
    
    // ACT: Update with 0 delta time (paused game)
    anim.update(0.0f);
    
    // ASSERT: Should not advance
    assertEquals("Should not advance with delta=0", 0, anim.getCurrentFrameIndex());
}
```

**Expected Results**:
- ✅ Frames advance after frame duration elapsed
- ✅ Loop animations restart at frame 0
- ✅ One-shot animations stop on last frame
- ✅ Animation speed affected by frame duration
- ✅ Zero delta time doesn't advance frames

---

### 3.2 ANIMATION STATE MACHINE
**File**: `entities/PlayerBase.java` → `updateAnimationState()`  
**Purpose**: Switch animation states based on player actions

#### How State Machine Works
Player animation state depends on current action:
```
State Transitions:
- IDLE: velocityX = 0, velocityY = 0, onGround = true
- WALK: velocityX != 0, onGround = true
- JUMP: velocityY < 0 (moving up)
- FALL: velocityY > 0 (moving down)
- ATTACK: Attack key pressed, cooldown ready
- HIT: Just took damage
- DEATH: Health = 0
```

---

#### Method 1: `updateAnimationState()`
**Purpose**: Change current animation state based on player state

**Test Cases**:
```java
@Test
public void test_Animation_StateTransition_IdleToWalk() {
    // ARRANGE: Player standing still (IDLE)
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    player.setOnGround(true);
    player.setVelocity(0, 0);
    assertEquals("Should start in IDLE", AnimationState.IDLE, player.getCurrentState());
    
    // ACT: Player starts moving left
    player.setVelocity(-200, 0);
    player.updateAnimationState();
    
    // ASSERT: Should transition to WALK
    assertEquals("Should transition to WALK", AnimationState.WALK, player.getCurrentState());
}

@Test
public void test_Animation_StateTransition_WalkToJump() {
    // ARRANGE: Player walking (WALK)
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    player.setOnGround(true);
    player.setVelocity(200, 0);
    player.updateAnimationState();
    assertEquals("Should be in WALK", AnimationState.WALK, player.getCurrentState());
    
    // ACT: Player jumps (velocityY becomes negative)
    player.jump();
    player.updateAnimationState();
    
    // ASSERT: Should transition to JUMP
    assertEquals("Should transition to JUMP", AnimationState.JUMP, player.getCurrentState());
}

@Test
public void test_Animation_StateTransition_JumpToFall() {
    // ARRANGE: Player jumping (JUMP, velocityY < 0)
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    player.setVelocity(0, -300); // Moving up
    player.updateAnimationState();
    assertEquals("Should be in JUMP", AnimationState.JUMP, player.getCurrentState());
    
    // ACT: Gravity brings player down (velocityY becomes positive)
    for (int i = 0; i < 30; i++) {
        player.update(0.016f); // Apply gravity
    }
    player.updateAnimationState();
    
    // ASSERT: Should transition to FALL
    assertEquals("Should transition to FALL", AnimationState.FALL, player.getCurrentState());
}

@Test
public void test_Animation_StateTransition_FallToIdle() {
    // ARRANGE: Player falling (FALL)
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    player.setVelocity(0, 300); // Falling down
    player.setOnGround(false);
    player.updateAnimationState();
    assertEquals("Should be in FALL", AnimationState.FALL, player.getCurrentState());
    
    // ACT: Player lands on ground
    player.setOnGround(true);
    player.setVelocity(0, 0);
    player.updateAnimationState();
    
    // ASSERT: Should transition to IDLE
    assertEquals("Should transition to IDLE after landing", AnimationState.IDLE, player.getCurrentState());
}

@Test
public void test_Animation_StateTransition_Attack() {
    // ARRANGE: Player in IDLE
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    
    // ACT: Player attacks
    player.attack();
    player.updateAnimationState();
    
    // ASSERT: Should transition to ATTACK
    assertEquals("Should transition to ATTACK", AnimationState.ATTACK, player.getCurrentState());
}

@Test
public void test_Animation_StateTransition_TakeDamage() {
    // ARRANGE: Player in any state
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    
    // ACT: Player takes damage
    player.takeDamage(25);
    
    // ASSERT: Should transition to HIT
    assertEquals("Should transition to HIT when damaged", AnimationState.HIT, player.getCurrentState());
}

@Test
public void test_Animation_StateTransition_Death() {
    // ARRANGE: Player with low health
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    
    // ACT: Player takes lethal damage
    player.takeDamage(100);
    
    // ASSERT: Should transition to DEATH
    assertEquals("Should transition to DEATH when health = 0", AnimationState.DEATH, player.getCurrentState());
    assertFalse("Player should be dead", player.isAlive());
}
```

**Expected Results**:
- ✅ IDLE → WALK when moving horizontally
- ✅ WALK → JUMP when jumping
- ✅ JUMP → FALL when falling
- ✅ FALL → IDLE when landing
- ✅ Any state → ATTACK when attacking
- ✅ Any state → HIT when taking damage
- ✅ Any state → DEATH when health = 0

---

---

## ╔═══════════════════════════════════════════════════════════════════════════╗
## ║  PART 4: AI BEHAVIOR SYSTEM TESTS  ⭐ CRITICAL                            ║
## ╚═══════════════════════════════════════════════════════════════════════════╝

**Package**: `ai`, `entities`  
**Purpose**: Enemy AI (patrol, chase, attack, pathfinding)  
**Priority**: ⭐ **CRITICAL** - Without AI, enemies are static/non-threatening  
**Estimated Test Count**: 25 tests

---

### Why AI System Is Critical

**If AI Fails, Enemies Are Useless**:
- ❌ **No patrol** → Enemies stand still (no challenge)
- ❌ **No chase** → Enemies don't react to player
- ❌ **No attack** → Enemies never damage player
- ❌ **No pathfinding** → Enemies walk into walls, get stuck
- ❌ **No state changes** → Enemies stuck in IDLE forever

**AI Makes The Game Actually A GAME**:
```
Working AI → Enemies React → Player Must Dodge/Fight → Fun Gameplay
Broken AI → Static Enemies → No Challenge → Boring
```

---

### 4.1 ENEMY AI STATE MACHINE
**File**: `ai/EnemyAI.java`  
**Purpose**: Control enemy behavior based on AI state

#### AI States
Each enemy has an AI state:
- **PATROL**: Move left/right in preset pattern
- **CHASE**: Follow player when player is detected
- **ATTACK**: Attack player when in range
- **RETREAT**: Back away when low health
- **IDLE**: Waiting/inactive

---

#### Method 1: `update(float deltaTime, Player player)`
**Purpose**: Update AI state based on player position and enemy state

**What It Does**:
```java
public void update(float deltaTime, Player player) {
    float distanceToPlayer = calculateDistance(enemy.getX(), player.getX());
    
    // State transitions
    if (distanceToPlayer < DETECTION_RANGE) {
        if (distanceToPlayer < ATTACK_RANGE) {
            currentState = AIState.ATTACK;
        } else {
            currentState = AIState.CHASE;
        }
    } else {
        currentState = AIState.PATROL;
    }
    
    // Execute state behavior
    switch (currentState) {
        case PATROL:
            patrol(deltaTime);
            break;
        case CHASE:
            chase(player, deltaTime);
            break;
        case ATTACK:
            attack(player, deltaTime);
            break;
    }
}
```

**Test Cases**:
```java
@Test
public void test_AI_PatrolBehavior() {
    // ARRANGE: Enemy far from player
    Player player = new Player(CharacterType.BIKER, 1000, 100); // Far away
    EnemyInstance enemy = Enemies.createEnemy(EnemyType.UFO_SAUCER, 100, 100);
    EnemyAI ai = new EnemyAI(enemy);
    
    float initialX = enemy.getX();
    
    // ACT: Update AI for 1 second (should patrol)
    for (int i = 0; i < 60; i++) {
        ai.update(0.016f, player);
    }
    
    // ASSERT: Enemy should have moved (patrolling)
    assertNotEquals("Enemy should move during patrol", initialX, enemy.getX(), 1.0f);
    assertEquals("AI state should be PATROL", AIState.PATROL, ai.getCurrentState());
}

@Test
public void test_AI_ChasePlayer() {
    // ARRANGE: Player within detection range
    Player player = new Player(CharacterType.BIKER, 400, 100); // 300 units away
    EnemyInstance enemy = Enemies.createEnemy(EnemyType.UFO_SAUCER, 100, 100);
    EnemyAI ai = new EnemyAI(enemy);
    ai.setDetectionRange(500); // Can detect up to 500 units
    
    float initialDistance = Math.abs(enemy.getX() - player.getX());
    
    // ACT: Update AI for 1 second
    for (int i = 0; i < 60; i++) {
        ai.update(0.016f, player);
    }
    
    // ASSERT: Enemy should move toward player
    float newDistance = Math.abs(enemy.getX() - player.getX());
    assertTrue("Enemy should get closer to player", newDistance < initialDistance);
    assertEquals("AI state should be CHASE", AIState.CHASE, ai.getCurrentState());
}

@Test
public void test_AI_AttackPlayer() {
    // ARRANGE: Player within attack range
    Player player = new Player(CharacterType.BIKER, 110, 100); // 10 units away
    EnemyInstance enemy = Enemies.createEnemy(EnemyType.UFO_SAUCER, 100, 100);
    EnemyAI ai = new EnemyAI(enemy);
    ai.setAttackRange(50); // Attack within 50 units
    
    int initialPlayerHealth = player.getHealth();
    
    // ACT: Update AI for 1 second (should attack)
    for (int i = 0; i < 60; i++) {
        ai.update(0.016f, player);
    }
    
    // ASSERT: Player should have taken damage
    assertTrue("Player health should decrease from attack",
               player.getHealth() < initialPlayerHealth);
    assertEquals("AI state should be ATTACK", AIState.ATTACK, ai.getCurrentState());
}

@Test
public void test_AI_StateTransition_PatrolToChase() {
    // ARRANGE: Enemy patrolling, player far away
    Player player = new Player(CharacterType.BIKER, 1000, 100);
    EnemyInstance enemy = Enemies.createEnemy(EnemyType.UFO_SAUCER, 100, 100);
    EnemyAI ai = new EnemyAI(enemy);
    ai.setDetectionRange(500);
    
    // Update AI (should be PATROL)
    ai.update(0.016f, player);
    assertEquals("Should start in PATROL", AIState.PATROL, ai.getCurrentState());
    
    // ACT: Move player closer (within detection range)
    player.setPosition(300, 100); // Now 200 units away
    ai.update(0.016f, player);
    
    // ASSERT: Should transition to CHASE
    assertEquals("Should transition to CHASE", AIState.CHASE, ai.getCurrentState());
}

@Test
public void test_AI_StateTransition_ChaseToAttack() {
    // ARRANGE: Enemy chasing player
    Player player = new Player(CharacterType.BIKER, 300, 100);
    EnemyInstance enemy = Enemies.createEnemy(EnemyType.UFO_SAUCER, 100, 100);
    EnemyAI ai = new EnemyAI(enemy);
    ai.setDetectionRange(500);
    ai.setAttackRange(50);
    
    ai.update(0.016f, player);
    assertEquals("Should be CHASE", AIState.CHASE, ai.getCurrentState());
    
    // ACT: Move player into attack range
    player.setPosition(120, 100); // Now 20 units away
    ai.update(0.016f, player);
    
    // ASSERT: Should transition to ATTACK
    assertEquals("Should transition to ATTACK", AIState.ATTACK, ai.getCurrentState());
}

@Test
public void test_AI_LosePlayer() {
    // ARRANGE: Enemy chasing player
    Player player = new Player(CharacterType.BIKER, 300, 100);
    EnemyInstance enemy = Enemies.createEnemy(EnemyType.UFO_SAUCER, 100, 100);
    EnemyAI ai = new EnemyAI(enemy);
    ai.setDetectionRange(500);
    
    ai.update(0.016f, player);
    assertEquals("Should be CHASE", AIState.CHASE, ai.getCurrentState());
    
    // ACT: Player moves far away (out of detection rangee)
    player.setPosition(2000, 100);
    ai.update(0.016f, player);
    
    // ASSERT: Should return to PATROL
    assertEquals("Should return to PATROL", AIState.PATROL, ai.getCurrentState());
}
```

**Expected Results**:
- ✅ Enemies patrol when player far away
- ✅ Enemies chase when player detected
- ✅ Enemies attack when player in range
- ✅ State transitions work correctly
- ✅ Enemies lose player when too far

---

---

## ╔═══════════════════════════════════════════════════════════════════════════╗
## ║  PART 5: COLLISION DETECTION SYSTEM TESTS  ⭐ CRITICAL                    ║
## ╚═══════════════════════════════════════════════════════════════════════════╝

**Package**: `physics`, `collision`  
**Purpose**: Detect collisions between entities, tiles, projectiles  
**Priority**: ⭐ **CRITICAL** - Without collision, player falls through floor  
**Estimated Test Count**: 35 tests

---

### Why Collision Detection Is Critical

**If Collision Fails, Physics Is BROKEN**:
- ❌ **No floor collision** → Player falls through ground infinitely
- ❌ **No wall collision** → Player walks through walls, off-screen
- ❌ **No enemy collision** → Player passes through enemies (no hit detection)
- ❌ **No projectile collision** → Bullets don't hit anything
- ❌ **Wrong collision resolution** → Player gets stuck in walls

**Collision Makes The Game World SOLID**:
```
Working Collision → Player Stands On Ground → Can Jump, Walk → Playable Game
Broken Collision → Player Falls Forever → Can't Play
```

---

### 5.1 AABB COLLISION DETECTION
**File**: `physics/CollisionDetector.java`  
**Purpose**: Axis-Aligned Bounding Box collision (rectangle intersection)

#### How AABB Works
Each entity has a bounding box:
```
Player Box:
x = 100, y = 200
width = 32, height = 48

Enemy Box:
x = 150, y = 200
width = 48, height = 48

Overlap Check:
x overlap: 100 < (150 + 48) AND (100 + 32) > 150 → TRUE
y overlap: 200 < (200 + 48) AND (200 + 48) > 200 → TRUE
Result: COLLISION!
```

---

#### Method 1: `checkAABBCollision(BoundingBox a, BoundingBox b)`
**Purpose**: Check if two bounding boxes overlap

**What It Does**:
```java
public static boolean checkAABBCollision(BoundingBox a, BoundingBox b) {
    return a.x < b.x + b.width &&
           a.x + a.width > b.x &&
           a.y < b.y + b.height &&
           a.y + a.height > b.y;
}
```

**Test Cases**:
```java
@Test
public void test_Collision_AABB_OverlappingBoxes() {
    // ARRANGE: Two overlapping boxes
    BoundingBox boxA = new BoundingBox(100, 100, 50, 50); // x,y,w,h
    BoundingBox boxB = new BoundingBox(120, 120, 50, 50); // Overlaps boxA
    
    // ACT: Check collision
    boolean collides = CollisionDetector.checkAABBCollision(boxA, boxB);
    
    // ASSERT: Should detect collision
    assertTrue("Overlapping boxes should collide", collides);
}

@Test
public void test_Collision_AABB_SeparatedBoxes() {
    // ARRANGE: Two boxes far apart
    BoundingBox boxA = new BoundingBox(100, 100, 50, 50);
    BoundingBox boxB = new BoundingBox(300, 300, 50, 50); // Far away
    
    // ACT: Check collision
    boolean collides = CollisionDetector.checkAABBCollision(boxA, boxB);
    
    // ASSERT: Should NOT collide
    assertFalse("Separated boxes should not collide", collides);
}

@Test
public void test_Collision_AABB_TouchingEdges() {
    // ARRANGE: Two boxes touching but not overlapping
    BoundingBox boxA = new BoundingBox(100, 100, 50, 50); // Right edge at x=150
    BoundingBox boxB = new BoundingBox(150, 100, 50, 50); // Left edge at x=150
    
    // ACT: Check collision
    boolean collides = CollisionDetector.checkAABBCollision(boxA, boxB);
    
    // ASSERT: Touching edges should NOT count as collision
    assertFalse("Touching edges should not collide", collides);
}

@Test
public void test_Collision_AABB_NestedBoxes() {
    // ARRANGE: Small box inside large box
    BoundingBox large = new BoundingBox(100, 100, 200, 200);
    Bounding Box small = new BoundingBox(150, 150, 50, 50); // Inside large
    
    // ACT: Check collision
    boolean collides = CollisionDetector.checkAABBCollision(large, small);
    
    // ASSERT: Should collide (nested counts as overlap)
    assertTrue("Nested box should collide with container", collides);
}

@Test
public void test_Collision_AABB_PartialOverlap() {
    // ARRANGE: Boxes with small overlap
    BoundingBox boxA = new BoundingBox(100, 100, 50, 50);
    BoundingBox boxB = new BoundingBox(140, 140, 50, 50); // Small overlap
    
    // ACT: Check collision
    boolean collides = CollisionDetector.checkAABBCollision(boxA, boxB);
    
    // ASSERT: Should collide (any overlap counts)
    assertTrue("Partial overlap should collide", collides);
}

@Test
public void test_Collision_AABB_PlayerVsTile() {
    // ARRANGE: Player landing on tile
    BoundingBox player = new BoundingBox(100, 200, 32, 48); // Player
    BoundingBox tile = new BoundingBox(90, 248, 64, 64); // Ground tile below
    
    // ACT: Check collision
    boolean collides = CollisionDetector.checkAABBCollision(player, tile);
    
    // ASSERT: Should collide (player on ground)
    assertTrue("Player should collide with ground tile", collides);
}

@Test
public void test_Collision_AABB_PlayerVsEnemy() {
    // ARRANGE: Player touching enemy
    BoundingBox player = new BoundingBox(100, 100, 32, 48);
    BoundingBox enemy = new BoundingBox(130, 100, 48, 48); // Slightly overlaps
    
    // ACT: Check collision
    boolean collides = CollisionDetector.checkAABBCollision(player, enemy);
    
    // ASSERT: Should collide
    assertTrue("Player should collide with enemy", collides);
}
```

**Expected Results**:
- ✅ Overlapping boxes detected
- ✅ Separated boxes not detected
- ✅ Touching edges not counted as collision
- ✅ Nested boxes detected
- ✅ Partial overlaps detected
- ✅ Real-world scenarios (player vs tile, player vs enemy) work

---

---

## ╔═══════════════════════════════════════════════════════════════════════════╗
## ║  PART 6: FILE LOADING & PARSING TESTS  ⭐ CRITICAL                        ║
## ╚═══════════════════════════════════════════════════════════════════════════╝

**Package**: `utilities`, `level`  
**Purpose**: Load and parse map files, configuration files  
**Priority**: ⭐ **CRITICAL** - Without file loading, levels don't load  
**Estimated Test Count**: 20 tests

---

### Why File Loading Is Critical

**If File Loading Fails, Levels DON'T EXIST**:
- ❌ **Can't load map.txt** → No level layout (blank screen)
- ❌ **Can't parse tile IDs** → Tiles rendered incorrectly
- ❌ **Can't load config** → Game settings missing
- ❌ **Corrupted files** → Crash on level load

**File Loading Creates The Game World**:
```
Map File → Parse Tiles → Build Level → Render World → Playable Level
(map.txt)   (tile IDs)   (2D array)    (graphics)
```

---

### 6.1 MAP FILE LOADING
**File**: `level/MapLoader.java`  
**Purpose**: Load level layout from `maps/level_X/map.txt`

#### Map File Format
```
# map.txt for Level 1
WIDTH=40
HEIGHT=30
# Tile IDs (0=air, 1=ground, 2=platform, 3=hazard)
0 0 0 0 0 0 0 0 0 0 ...
0 0 0 0 0 0 0 0 0 0 ...
0 0 0 0 0 0 0 0 0 0 ...
1 1 1 1 1 1 1 1 1 1 ...
```

---

#### Method 1: `loadMap(String mapPath)`
**Purpose**: Load map file and parse into 2D tile array

**Test Cases**:
```java
@Test
public void test_FileLoading_loadMap_Level1() {
    // ARRANGE: MapLoader
    MapLoader loader = new MapLoader();
    
    // ACT: Load Level 1 map
    int[][] map = loader.loadMap("maps/level_1/map.txt");
    
    // ASSERT: Map loaded
    assertNotNull("Level 1 map should load", map);
    assertTrue("Map height > 0", map.length > 0);
    assertTrue("Map width > 0", map[0].length > 0);
}

@Test
public void test_FileLoading_loadMap_CorrectDimensions() {
    // ARRANGE: MapLoader
    MapLoader loader = new MapLoader();
    
    // ACT: Load Level 1 map (should be 40×30)
    int[][] map = loader.loadMap("maps/level_1/map.txt");
    
    // ASSERT: Dimensions match file
    assertEquals("Map height should be 30", 30, map.length);
    assertEquals("Map width should be 40", 40, map[0].length);
}

@Test
public void test_FileLoading_loadMap_ValidTileIDs() {
    // ARRANGE: MapLoader
    MapLoader loader = new MapLoader();
    
    // ACT: Load Level 1 map
    int[][] map = loader.loadMap("maps/level_1/map.txt");
    
    // ASSERT: All tile IDs in valid range (0-10)
    for (int y = 0; y < map.length; y++) {
        for (int x = 0; x < map[y].length; x++) {
            int tileID = map[y][x];
            assertTrue("Tile ID should be >= 0", tileID >= 0);
            assertTrue("Tile ID should be < 20", tileID < 20); // Max tile ID
        }
    }
}

@Test
public void test_FileLoading_loadMap_InvalidPath() {
    // ARRANGE: MapLoader
    MapLoader loader = new MapLoader();
    
    // ACT: Try to load non-existent map
    int[][] map = loader.loadMap("maps/level_999/map.txt");
    
    // ASSERT: Should return null
    assertNull("Invalid map path should return null", map);
}

@Test
public void test_FileLoading_loadMap_CorruptedFile() {
    // ARRANGE: MapLoader
    MapLoader loader = new MapLoader();
    
    // ACT: Try to load corrupted map (test file with invalid format)
    int[][] map = loader.loadMap("maps/test/corrupted_map.txt");
    
    // ASSERT: Should return null and log error
    assertNull("Corrupted map should return null", map);
}
```

**Expected Results**:
- ✅ Valid map files load successfully
- ✅ Map dimensions match file header
- ✅ All tile IDs in valid range
- ✅ Invalid paths return null
- ✅ Corrupted files handled gracefully

---

---

## ╔═══════════════════════════════════════════════════════════════════════════╗
## ║  PART 7: ENTITY MANAGEMENT TESTS                                          ║
## ╚═══════════════════════════════════════════════════════════════════════════╝

**Package**: `entities`, `core_game_entities`  
**Purpose**: All game objects (player, enemies, projectiles, pickups)  
**Priority**: HIGH - Entities are what players interact with  
**Estimated Test Count**: 60 tests

### Why Entity Testing Is Critical
Entities are the **visible, interactive game objects**:
- Player character (what you control)
- Enemies (what you fight)
- Projectiles (bullets, attacks)
- Pickups (health, ammo, power-ups)

Entity bugs cause:
- Player can't move/jump
- Enemies don't spawn or attack
- Bullets disappear instantly
- Health pickups don't heal

---

### 2.1 PLAYER ENTITY
**File**: `entities/PlayerBase.java`  
**Total Lines**: ~450 | **Public Methods**: 15  
**Purpose**: Main player character with sprite animation, physics, input handling, combat system

#### Why PlayerBase Is The Most Important Entity
PlayerBase is **what the player controls**. Every input (left, right, jump, attack, dash) goes through this class. If PlayerBase breaks:
- Can't move → game unplayable
- Can't jump → can't reach platforms
- Can't attack → can't defeat enemies
- Incorrect physics → player floats or falls through floor

PlayerBase coordinates **4 major subsystems**:
1. **Animation**: Loads 6 animation states (IDLE, WALK, JUMP, ATTACK, HIT, DEATH) with 3-8 frames each
2. **Physics**: Position, velocity, acceleration, gravity, jumping, dashing
3. **Input**: Responds to keyboard (arrows, WASD, SPACE, CTRL, SHIFT)
4. **Combat**: Health, damage, attack cooldown, death state

---

#### Method 1: `PlayerBase(CharacterType character, float startX, float startY)` - Constructor
**Purpose**: Initialize player with chosen character and starting position

**What It Does**:
```java
// Pseudo-implementation
public PlayerBase(CharacterType character, float startX, float startY) {
    this.character = character; // BIKER, PUNK, or CYBORG
    this.x = startX;
    this.y = startY;
    this.velocityX = 0;
    this.velocityY = 0;
    this.health = 100;
    this.maxHealth = 100;
    this.isAlive = true;
    this.currentState = AnimationState.IDLE;
    
    // Load character-specific sprites
    loadSprites(character);
}
```

**Why We Test This**:
- Constructor is called ONCE when game starts
- Wrong initialization → entire game session bugged
- Missing sprite loading → invisible player
- Wrong position → player spawns off-screen or inside wall

**Test Cases**:
```java
@Test
public void test_PlayerBase_Constructor_BIKER() {
    // ARRANGE & ACT: Create BIKER character at (100, 100)
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    
    // ASSERT: All initial values correct
    assertEquals("Character type should be BIKER", CharacterType.BIKER, player.getCharacter());
    assertEquals("X position should be 100", 100f, player.getX(), 0.01f);
    assertEquals("Y position should be 100", 100f, player.getY(), 0.01f);
    assertEquals("Initial velocity X should be 0", 0f, player.getVelocityX(), 0.01f);
    assertEquals("Initial velocity Y should be 0", 0f, player.getVelocityY(), 0.01f);
    assertEquals("Initial health should be 100", 100, player.getHealth());
    assertEquals("Max health should be 100", 100, player.getMaxHealth());
    assertTrue("Player should be alive", player.isAlive());
    assertEquals("Initial state should be IDLE", AnimationState.IDLE, player.getCurrentState());
}

@Test
public void test_PlayerBase_Constructor_PUNK() {
    // ARRANGE & ACT: Create PUNK at (200, 300)
    PlayerBase player = new PlayerBase(CharacterType.PUNK, 200, 300);
    
    // ASSERT: Position and character correct
    assertEquals("Character should be PUNK", CharacterType.PUNK, player.getCharacter());
    assertEquals("X should be 200", 200f, player.getX(), 0.01f);
    assertEquals("Y should be 300", 300f, player.getY(), 0.01f);
    assertNotNull("Sprites should be loaded", player.getSprites());
}

@Test
public void test_PlayerBase_Constructor_CYBORG() {
    // ARRANGE & ACT: Create CYBORG at (500, 600)
    PlayerBase player = new PlayerBase(CharacterType.CYBORG, 500, 600);
    
    // ASSERT: Cyborg has different properties (e.g., more health)
    assertEquals("Character should be CYBORG", CharacterType.CYBORG, player.getCharacter());
    assertTrue("Cyborg should have health >= 100", player.getMaxHealth() >= 100);
}

@Test
public void test_PlayerBase_Constructor_NegativePosition() {
    // ARRANGE & ACT: Test negative starting position
    PlayerBase player = new PlayerBase(CharacterType.BIKER, -50, -100);
    
    // ASSERT: Position should be accepted (world can have negative coords)
    assertEquals("X can be negative", -50f, player.getX(), 0.01f);
    assertEquals("Y can be negative", -100f, player.getY(), 0.01f);
}

@Test
public void test_PlayerBase_Constructor_SpritesLoaded() {
    // ARRANGE & ACT: Create player
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 0, 0);
    
    // ASSERT: Verify sprites loaded for all 6 animation states
    for (AnimationState state : AnimationState.values()) {
        assertNotNull("Sprites for " + state + " should be loaded",
                      player.getSpritesForState(state));
        assertTrue("Sprites for " + state + " should have > 0 frames",
                   player.getSpritesForState(state).length > 0);
    }
}
```

**Expected Results**:
- ✅ All 3 character types initialize correctly
- ✅ Position set accurately (including negative coords)
- ✅ Velocity starts at zero (not moving)
- ✅ Health initialized to max
- ✅ All 6 animation states have sprites loaded
- ✅ Default state is IDLE

---

#### Method 2: `setKeyPressed(int keyCode, boolean pressed)` - Static Input Tracker
**Purpose**: Track which keys are currently pressed (static method affects all players)

**What It Does**:
```java
// Pseudo-implementation
private static Set<Integer> keysPressed = new HashSet<>();

public static void setKeyPressed(int keyCode, boolean pressed) {
    if (pressed) {
        keysPressed.add(keyCode);
    } else {
        keysPressed.remove(keyCode);
    }
}
```

**Why We Test This**:
- **Static** method affects all PlayerBase instances
- Input lag detection: key must register instantly
- Multiple keys pressed simultaneously (left + jump)
- Key release must clear state (or player keeps moving)

**Test Cases**:
```java
@Test
public void test_PlayerBase_setKeyPressed_SingleKey() {
    // ARRANGE: Clear all keys
    PlayerBase.clearAllKeys();
    
    // ACT: Press LEFT arrow (key code 37)
    PlayerBase.setKeyPressed(KeyEvent.VK_LEFT, true);
    
    // ASSERT: Key is tracked
    assertTrue("LEFT key should be pressed", PlayerBase.isKeyPressed(KeyEvent.VK_LEFT));
}

@Test
public void test_PlayerBase_setKeyPressed_KeyRelease() {
    // ARRANGE: Press LEFT key
    PlayerBase.setKeyPressed(KeyEvent.VK_LEFT, true);
    
    // ACT: Release LEFT key
    PlayerBase.setKeyPressed(KeyEvent.VK_LEFT, false);
    
    // ASSERT: Key no longer pressed
    assertFalse("LEFT key should be released", PlayerBase.isKeyPressed(KeyEvent.VK_LEFT));
}

@Test
public void test_PlayerBase_setKeyPressed_MultipleKeys() {
    // ARRANGE: Clear all keys
    PlayerBase.clearAllKeys();
    
    // ACT: Press LEFT + SPACE (move left while jumping)
    PlayerBase.setKeyPressed(KeyEvent.VK_LEFT, true);
    PlayerBase.setKeyPressed(KeyEvent.VK_SPACE, true);
    
    // ASSERT: Both keys tracked
    assertTrue("LEFT should be pressed", PlayerBase.isKeyPressed(KeyEvent.VK_LEFT));
    assertTrue("SPACE should be pressed", PlayerBase.isKeyPressed(KeyEvent.VK_SPACE));
}

@Test
public void test_PlayerBase_setKeyPressed_PressMultipleTimes() {
    // ARRANGE: Clear keys
    PlayerBase.clearAllKeys();
    
    // ACT: Press same key multiple times (spam jump)
    for (int i = 0; i < 10; i++) {
        PlayerBase.setKeyPressed(KeyEvent.VK_SPACE, true);
    }
    
    // ASSERT: Key still tracked (no duplicates crash Set)
    assertTrue("SPACE should be pressed after spam", PlayerBase.isKeyPressed(KeyEvent.VK_SPACE));
}
```

**Expected Results**:
- ✅ Single key press tracked
- ✅ Key release clears state
- ✅ Multiple simultaneous keys work
- ✅ Press same key repeatedly doesn't break Set
- ✅ Static state accessible from any player instance

---

#### Method 3: `update(float deltaTime)` - Core Game Loop Update
**Purpose**: Update player physics, animation, state every frame

**What It Does** (simplified):
```java
public void update(float deltaTime) {
    // 1. Apply physics
    velocityY += GRAVITY * deltaTime;
    velocityY = Math.min(velocityY, TERMINAL_VELOCITY);
    x += velocityX * deltaTime;
    y += velocityY * deltaTime;
    
    // 2. Apply input
    if (isKeyPressed(VK_LEFT)) velocityX = -MOVE_SPEED;
    else if (isKeyPressed(VK_RIGHT)) velocityX = MOVE_SPEED;
    else velocityX *= FRICTION;
    
    if (isKeyPressed(VK_SPACE) && onGround) jump();
    if (isKeyPressed(VK_SHIFT)) dash();
    if (isKeyPressed(VK_CTRL)) attack();
    
    // 3. Update animation
    animationTimer += deltaTime;
    if (animationTimer >= FRAME_DURATION) {
        currentFrameIndex = (currentFrameIndex + 1) % totalFrames;
        animationTimer = 0;
    }
    
    // 4. Update state
    if (velocityY > 0) currentState = AnimationState.FALL;
    else if (velocityY < 0) currentState = AnimationState.JUMP;
    else if (Math.abs(velocityX) > 0.1) currentState = AnimationState.WALK;
    else currentState = AnimationState.IDLE;
}
```

**Why We Test This**:
- **Most important method**: Called 60 times per second
- Integrates physics, input, animation, state
- Bugs here cause:
  - Player stuck in air (no gravity)
  - Player slides forever (no friction)
  - Animation stuck on one frame
  - State doesn't change (always IDLE even when walking)

**Test Cases**:
```java
@Test
public void test_PlayerBase_update_GravityApplied() {
    // ARRANGE: Player in air at velocityY = 0
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    player.setOnGround(false); // Not on ground
    float initialVY = player.getVelocityY();
    
    // ACT: Update for 1 frame (16ms)
    player.update(0.016f);
    
    // ASSERT: Velocity Y should increase (falling down)
    assertTrue("Velocity Y should increase (gravity)",
               player.getVelocityY() > initialVY);
}

@Test
public void test_PlayerBase_update_TerminalVelocity() {
    // ARRANGE: Player falling for long time
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    player.setOnGround(false);
    
    // ACT: Update for 5 seconds (should reach terminal velocity)
    for (int i = 0; i < 300; i++) {  // 300 frames = 5 seconds at 60 FPS
        player.update(0.016f);
    }
    
    // ASSERT: Velocity Y clamped at terminal velocity (700 px/s)
    assertTrue("Velocity Y should be <= terminal velocity (700)",
               player.getVelocityY() <= 700);
}

@Test
public void test_PlayerBase_update_HorizontalMovement() {
    // ARRANGE: Player with left key pressed
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 200, 300);
    PlayerBase.setKeyPressed(KeyEvent.VK_LEFT, true);
    float initialX = player.getX();
    
    // ACT: Update for 10 frames
    for (int i = 0; i < 10; i++) {
        player.update(0.016f);
    }
    
    // ASSERT: X position should decrease (moved left)
    assertTrue("Player should have moved left (X decreased)",
               player.getX() < initialX);
}

@Test
public void test_PlayerBase_update_AnimationProgression() {
    // ARRANGE: Player in WALK state
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    player.setCurrentState(AnimationState.WALK);
    int initialFrame = player.getCurrentFrameIndex();
    
    // ACT: Update until next frame (animation frame duration ~100ms)
    for (int i = 0; i < 10; i++) {
        player.update(0.016f);
    }
    
    // ASSERT: Frame index should advance
    assertNotEquals("Animation frame should have changed",
                    initialFrame, player.getCurrentFrameIndex());
}

@Test
public void test_PlayerBase_update_ZeroDeltaTime() {
    // ARRANGE: Player at position (100, 100)
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    float initialX = player.getX();
    float initialY = player.getY();
    
    // ACT: Update with 0 delta time
    player.update(0.0f);
    
    // ASSERT: Position and velocity should not change
    assertEquals("X should not change with delta=0", initialX, player.getX(), 0.01f);
    assertEquals("Y should not change with delta=0", initialY, player.getY(), 0.01f);
}

@Test
public void test_PlayerBase_update_LargeDeltaTime() {
    // ARRANGE: Player with velocityY = 100
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    player.setVelocity(0, 100);
    float initialY = player.getY();
    
    // ACT: Update with huge delta time (1 second lag spike)
    player.update(1.0f);
    
    // ASSERT: Position should update but not teleport insanely far
    // (implementation should clamp delta or use smaller steps)
    float deltaY = Math.abs(player.getY() - initialY);
    assertTrue("Y change should be reasonable (<1000px)", deltaY < 1000);
}
```

**Expected Results**:
- ✅ Gravity applied every frame (velocityY increases)
- ✅ Terminal velocity prevents infinite falling speed
- ✅ Horizontal input moves player left/right
- ✅ Animation frames advance over time
- ✅ Zero delta time = no movement
- ✅ Large delta time handled without teleporting

---

#### Method 4: `takeDamage(int damage)` - Combat System
**Purpose**: Reduce player health when hit by enemy or hazard

**What It Does**:
```java
public void takeDamage(int damage) {
    if (!isAlive) return; // Already dead
    
    health -= damage;
    if (health < 0) health = 0;
    
    if (health == 0) {
        isAlive = false;
        currentState = AnimationState.DEATH;
    } else {
        currentState = AnimationState.HIT;
    }
}
```

**Why We Test This**:
- Health = 0 → player dies → game over
- Negative health bugs (health = -50?)
- Taking damage when already dead
- Hit animation must play

**Test Cases**:
```java
@Test
public void test_PlayerBase_takeDamage_NormalDamage() {
    // ARRANGE: Player with 100 health
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    assertEquals("Initial health should be 100", 100, player.getHealth());
    
    // ACT: Take 25 damage
    player.takeDamage(25);
    
    // ASSERT: Health reduced to 75
    assertEquals("Health should be 75 after 25 damage", 75, player.getHealth());
    assertTrue("Player should still be alive", player.isAlive());
    assertEquals("State should be HIT", AnimationState.HIT, player.getCurrentState());
}

@Test
public void test_PlayerBase_takeDamage_LethalDamage() {
    // ARRANGE: Player with 100 health
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    
    // ACT: Take 100 damage (exactly lethal)
    player.takeDamage(100);
    
    // ASSERT: Health = 0, player dead
    assertEquals("Health should be 0", 0, player.getHealth());
    assertFalse("Player should be dead", player.isAlive());
    assertEquals("State should be DEATH", AnimationState.DEATH, player.getCurrentState());
}

@Test
public void test_PlayerBase_takeDamage_Overkill() {
    // ARRANGE: Player with 50 health
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    player.setHealth(50);
    
    // ACT: Take 200 damage (way more than current health)
    player.takeDamage(200);
    
    // ASSERT: Health should be 0, not negative
    assertEquals("Health should be 0, not negative", 0, player.getHealth());
    assertTrue("Health should not go below 0", player.getHealth() >= 0);
}

@Test
public void test_PlayerBase_takeDamage_WhenDead() {
    // ARRANGE: Player already dead
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    player.takeDamage(100); // Kill player
    assertFalse("Player should ARRANGE: Player already dead");
    
    // ACT: Try to damage dead player
    player.takeDamage(50);
    
    // ASSERT: Health stays 0, no change
    assertEquals("Dead player health should stay 0", 0, player.getHealth());
    assertFalse("Player should still be dead", player.isAlive());
}

@Test
public void test_PlayerBase_takeDamage_ZeroDamage() {
    // ARRANGE: Player with 100 health
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    
    // ACT: Take 0 damage
    player.takeDamage(0);
    
    // ASSERT: Health unchanged
    assertEquals("Health should remain 100", 100, player.getHealth());
    assertTrue("Player should be alive", player.isAlive());
}

@Test
public void test_PlayerBase_takeDamage_NegativeDamage() {
    // ARRANGE: Player with 50 health
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    player.setHealth(50);
    
    // ACT: Take -20 damage (healing? or bug?)
    player.takeDamage(-20);
    
    // ASSERT: Should not heal (or implementation may allow it)
    // Typically negative damage is rejected or clamped to 0
    assertTrue("Health should not exceed max from negative damage",
               player.getHealth() <= player.getMaxHealth());
}
```

**Expected Results**:
- ✅ Normal damage reduces health correctly
- ✅ Lethal damage sets health to exactly 0
- ✅ Overkill damage doesn't make health negative
- ✅ Dead players can't take more damage
- ✅ Zero damage doesn't change health
- ✅ Negative damage handled (no healing or clamped)

---

### 2.2 ENEMY ENTITIES
**File**: `entities/Enemies.java`, `entities/EnemyInstance.java`  
**Total Lines**: ~600 | **Public Methods**: 12  
**Purpose**: Enemy creation factory, AI behavior, physics, rendering

#### Why Enemy Testing Matters
Enemies are the **challenge/opposition** in the game. Enemy bugs:
- Enemies don't spawn → game too easy/boring
- Enemies don't move → sitting ducks
- Enemies don't attack → no threat
- Enemies don't die → invincible

Enemy system has 2 files:
1. **Enemies.java**: Factory for creating enemies (UFO_SAUCER, HOVER_PLATFORM, JET_DRONE, etc.)
2. **EnemyInstance.java**: Individual enemy with position, health, AI, state

---

#### Method 1: `createEnemy(EnemyType type, float x, float y)`
**Purpose**: Factory method to spawn enemy at specific position

**What It Does**:
```java
public static EnemyInstance createEnemy(EnemyType type, float x, float y) {
    EnemyInstance enemy = new EnemyInstance();
    enemy.type = type;
    enemy.x = x;
    enemy.y = y;
    enemy.health = type.getMaxHealth();
    enemy.sprites = loadEnemySprites(type);
    enemy.ai = createAI(type);
    return enemy;
}
```

**Test Cases**:
```java
@Test
public void test_Enemies_createEnemy_UFO_SAUCER() {
    // ARRANGE & ACT: Create UFO at (100, 200)
    EnemyInstance enemy = Enemies.createEnemy(EnemyType.UFO_SAUCER, 100, 200);
    
    // ASSERT: Enemy created with correct properties
    assertNotNull("Enemy should not be null", enemy);
    assertEquals("Type should be UFO_SAUCER", EnemyType.UFO_SAUCER, enemy.getType());
    assertEquals("X position should be 100", 100f, enemy.getX(), 0.01f);
    assertEquals("Y position should be 200", 200f, enemy.getY(), 0.01f);
    assertTrue("Health should be > 0", enemy.getHealth() > 0);
    assertNotNull("Sprites should be loaded", enemy.getSprites());
}

@Test
public void test_Enemies_createEnemy_MultipleTypes() {
    // ARRANGE: Enemy types
    EnemyType[] types = {EnemyType.UFO_SAUCER, EnemyType.HOVER_PLATFORM, EnemyType.JET_DRONE};
    
    // ACT & ASSERT: Create each type
    for (EnemyType type : types) {
        EnemyInstance enemy = Enemies.createEnemy(type, 300, 400);
        assertNotNull("Enemy of type " + type + " should be created", enemy);
        assertEquals("Enemy type should match", type, enemy.getType());
    }
}

@Test
public void test_Enemies_createEnemy_NegativePosition() {
    // ARRANGE & ACT: Create enemy at negative coords
    EnemyInstance enemy = Enemies.createEnemy(EnemyType.UFO_SAUCER, -50, -100);
    
    // ASSERT: Position accepted (off-screen spawning valid)
    assertEquals("X can be negative", -50f, enemy.getX(), 0.01f);
    assertEquals("Y can be negative", -100f, enemy.getY(), 0.01f);
}
```

**Expected Results**:
- ✅ Each enemy type creates successfully
- ✅ Position set correctly
- ✅ Health initialized based on type
- ✅ Sprites loaded for type
- ✅ AI system initialized

---

## (CONTINUES WITH MORE DETAILED SECTIONS...)

---

## ═══════════════════════════════════════════════════════════════════════════════
## TEST EXECUTION ORDER & DEPENDENCIES
## ═══════════════════════════════════════════════════════════════════════════════

### Why Test Order Matters
Tests must run in **dependency order**: foundational classes first, integration tests last.

**Example Problem**:
- If `PlayerBase` tests run before `PhysicsUpdateSystem` tests
- And `PlayerBase.update()` depends on `PhysicsUpdateSystem.applyGravity()`
- And `applyGravity()` is broken
- Then `PlayerBase` tests fail even though `PlayerBase` is correct

**Solution**: Run tests in 10 phases, respecting dependencies.

---

### 10-PHASE TEST EXECUTION PLAN (UPDATED FOR CRITICAL SYSTEMS)

#### ✅ PHASE 1: FOUNDATION (RUN FIRST)
**No dependencies** - Pure utility classes

**Tests to Run**:
- GameCore: Game loop, rendering, delta time
- GameLoopManager: FPS calculation, timing
- PhysicsUpdateSystem: Gravity formulas (pure math)

**Why First**: If game loop or timing breaks, nothing else works.

**Estimated Time**: 2-3 minutes | **Test Count**: ~25

---

#### ⭐ PHASE 2: ASSET LOADING (CRITICAL - RUN IMMEDIATELY AFTER FOUNDATION)
**Depends on**: GameCore.loadImage() (Phase 1)

**Tests to Run** (Part 2 of Test Plan):
- Load character sprites (BIKER, PUNK, CYBORG - all 6 animation states)
- Load tile sets (Level 1, Level 2)
- Load audio files (music, sound effects)
- Load backgrounds and GUI elements
- Error handling (missing files, null images, corrupted files)

**Why Second**: **WITHOUT ASSETS, GAME HAS NO GRAPHICS/SOUNDS**. All visual systems depend on this.

**Estimated Time**: 5-7 minutes | **Test Count**: ~40

---

#### ⭐ PHASE 3: FILE LOADING & PARSING (CRITICAL)
**Depends on**: GameCore file I/O (Phase 1)

**Tests to Run** (Part 6 of Test Plan):
- Load map files (map.txt for Level 1, Level 2)
- Parse tile IDs from map data
- Load configuration files
- Error handling (missing map files, corrupted data, invalid tile IDs)

**Why Third**: **WITHOUT MAP FILES, LEVELS DON'T LOAD**. Player sees blank screen.

**Estimated Time**: 3-4 minutes | **Test Count**: ~20

---

#### ⭐ PHASE 4: ANIMATION SYSTEM (CRITICAL)
**Depends on**: Asset Loading (Phase 2 - needs sprites loaded)

**Tests to Run** (Part 3 of Test Plan):
- Frame progression (advancing through animation frames)
- Animation timing (frame duration, loop vs one-shot)
- State machine transitions (IDLE → WALK → JUMP → ATTACK → HIT → DEATH)
- Animation speed correctness

**Why Fourth**: **WITHOUT ANIMATION, CHARACTERS ARE FROZEN**. Player sees static images that never change.

**Estimated Time**: 4-5 minutes | **Test Count**: ~30

---

#### ⭐ PHASE 5: COLLISION DETECTION (CRITICAL)
**Depends on**: Physics basics (Phase 1)

**Tests to Run** (Part 5 of Test Plan):
- AABB collision (overlapping, separated, touching edges)
- Collision scenarios (player vs tile, player vs enemy, projectile vs enemy)
- Spatial grid optimization
- Collision resolution (push-out, bouncing)

**Why Fifth**: **WITHOUT COLLISION, PLAYER FALLS THROUGH FLOOR**. Physics is broken, game unplayable.

**Estimated Time**: 5-6 minutes | **Test Count**: ~35

---

#### ✅ PHASE 6: DATA STRUCTURES & ENTITY BASICS
**Depends on**: Asset Loading (Phase 2), Collision (Phase 5)

**Tests to Run**:
- GameState: Health, score tracking
- BoundingBox: Rectangle data structure
- PlayerBase: Constructor, basic properties
- EnemyInstance: Constructor, basic properties

**Why Sixth**: With assets loaded and collision working, now we can test entity basics.

**Estimated Time**: 4-5 minutes | **Test Count**: ~40

---

#### ⭐ PHASE 7: AI BEHAVIOR SYSTEM (CRITICAL)
**Depends on**: Entities (Phase 6), Collision (Phase 5)

**Tests to Run** (Part 4 of Test Plan):
- AI state machine (PATROL → CHASE → ATTACK)
- Pathfinding and movement
- Enemy detection ranges
- Attack behavior and cooldowns

**Why Seventh**: **WITHOUT AI, ENEMIES DON'T MOVE/ATTACK**. Game has no challenge.

**Estimated Time**: 4-5 minutes | **Test Count**: ~25

---

#### ✅ PHASE 8: ENTITY UPDATES & FULL LOGIC
**Depends on**: Asset Loading (Phase 2), Animation (Phase 4), Collision (Phase 5), AI (Phase 7)

**Tests to Run** (Part 7 of Test Plan):
- PlayerBase.update(): Input + physics + animation integration
- Enemy.update(): AI + physics + animation
- Projectile.update(): Movement + collision
- Combat system: Damage, death, respawn

**Why Eighth**: Now all systems integrated - test full entity lifecycle.

**Estimated Time**: 6-8 minutes | **Test Count**: ~45

---

#### ✅ PHASE 9: LEVEL, UI & RENDERING
**Depends on**: All critical systems (Phases 2-8)

**Tests to Run**:
- Level loading (Level1, Level2)
- Tile rendering
- UI rendering (HUD, menus)
- Camera following player

**Why Ninth**: Rendering and UI need all systems working.

**Estimated Time**: 4-5 minutes | **Test Count**: ~30

---

#### ✅ PHASE 10: INTEGRATION, STRESS & EDGE CASES
**Depends on**: ALL previous phases

**Tests to Run**:
- Full gameplay flow: Menu → Level 1 → Level 2 → Game Over
- Performance: 1000 entities, 10000 collisions
- Edge cases: Extreme positions, null handling, zero delta time
- Stress tests: Long game sessions, memory leaks

**Why Last**: Final validation that everything works together.

**Estimated Time**: 10-15 minutes | **Test Count**: ~30

---

### UPDATED TEST SUITE ESTIMATE
- **Total Phases**: 10
- **Total Tests**: ~300+ (increased to cover critical systems)
- **Total Time**: 50-70 minutes (full suite)
- **Critical Tests (Phases 2-5, 7)**: ~150 tests, 25-30 minutes
- **Tests per Second**: ~5-6

---

### CRITICAL SYSTEMS PRIORITY ORDER (IF TIME LIMITED)

**Run These First** (30 minutes):
1. ⭐ Phase 1: Foundation (game loop must work)
2. ⭐ Phase 2: Asset Loading (**NO GRAPHICS without this**)
3. ⭐ Phase 3: File Loading (**NO LEVELS without this**)
4. ⭐ Phase 4: Animation (**FROZEN SPRITES without this**)
5. ⭐ Phase 5: Collision (**FALL THROUGH FLOOR without this**)
6. ⭐ Phase 7: AI (**STATIC ENEMIES without this**)

**If game passes these 6 critical phases**, it's playable (visible graphics, working collisions, animated characters, working enemies).

**Then Run These** (20 minutes):
- Phase 6: Entity basics
- Phase 8: Full entity updates
- Phase 9: Level & UI

**Finally Run These** (20 minutes):
- Phase 10: Integration, stress, edge cases

---

## ═══════════════════════════════════════════════════════════════════════════════
## SUMMARY & BEST PRACTICES
## ═══════════════════════════════════════════════════════════════════════════════

### What This Test Plan Achieves

✅ **Complete Coverage**: Every public method in 618+ files tested  
✅ **Organized Structure**: 10 phases, 250+ tests, clear categories  
✅ **Dependency-Aware**: Tests run in order of class dependencies  
✅ **Real-World Scenarios**: Integration tests simulate actual gameplay  
✅ **Performance Validated**: Stress tests ensure 60 FPS under load  
✅ **Edge Cases Covered**: Boundary conditions, extreme values, error handling  

---

### Best Practices for Test Implementation

#### 1. Use AAA Pattern (Arrange-Act-Assert)
```java
@Test
public void test_PlayerBase_takeDamage() {
    // ARRANGE: Set up test data
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 100, 100);
    
    // ACT: Execute the method being tested
    player.takeDamage(25);
    
    // ASSERT: Verify the result
    assertEquals("Health should be 75", 75, player.getHealth());
}
```

#### 2. Test One Thing Per Test
❌ BAD:
```java
@Test
public void test_PlayerEverything() {
    // Tests movement AND damage AND animation - too much!
}
```

✅ GOOD:
```java
@Test
public void test_PlayerBase_takeDamage_NormalDamage() { /* ... */ }

@Test
public void test_PlayerBase_takeDamage_LethalDamage() { /* ... */ }

@Test
public void test_PlayerBase_takeDamage_WhenDead() { /* ... */ }
```

#### 3. Use Descriptive Test Names
Format: `test_ClassName_methodName_scenario`

Examples:
- `test_PlayerBase_jump_FromGround`
- `test_PlayerBase_jump_InAir`
- `test_PlayerBase_jump_OnCooldown`

#### 4. Assert with Messages
```java
assertEquals("Health should be 75 after taking 25 damage", 75, player.getHealth());
```
**Why**: When test fails, message shows what was expected, making debugging faster.

#### 5. Use Delta for Float Comparisons
```java
assertEquals("Position X should be 100", 100f, player.getX(), 0.01f);
//                                                              ^^^^^ delta (tolerance)
```
**Why**: Floats have precision errors (100.000001 != 100.0), delta accounts for this.

---

### How to Use This Test Plan

#### Step 1: Read the Full Document
Understand WHY each test matters, not just WHAT it tests.

#### Step 2: Implement Tests Phase by Phase
Don't try to write all 250 tests at once. Implement Phase 1, run it, fix bugs, then Phase 2.

#### Step 3: Run Tests Frequently
After implementing each test method, run it immediately. Don't wait until all tests are written.

#### Step 4: Fix Failures Immediately
If a test fails, fix it before moving on. Broken tests accumulate technical debt.

#### Step 5: Track Coverage
Use a simple spreadsheet:
```
Phase 1: Foundation
  ✅ GameCore: 6/6 tests passing
  ✅ GameLoopManager: 4/4 tests passing
  ✅ PhysicsUpdateSystem: 9/9 tests passing
  PHASE 1 COMPLETE: 19/19 (100%)
```

---

### Common Testing Pitfalls to Avoid

#### ❌ Pitfall 1: Testing Implementation Instead of Behavior
```java
// BAD: Testing internal implementation
@Test
public void test_PlayerBase_internalVariableSet() {
    assertEquals("velocityX field should be 100", 100, player.velocityX);
}

// GOOD: Testing observable behavior
@Test
public void test_PlayerBase_movesRight_WhenRightKeyPressed() {
    player.setKeyPressed(VK_RIGHT, true);
    float initialX = player.getX();
    player.update(0.016f);
    assertTrue("Player should move right", player.getX() > initialX);
}
```

#### ❌ Pitfall 2: Tests That Depend on Other Tests
```java
// BAD: Test depends on previous test's state
static PlayerBase sharedPlayer;

@Test
public void test1_CreatePlayer() {
    sharedPlayer = new PlayerBase(...);
}

@Test
public void test2_MovePlayer() {
    sharedPlayer.update(0.016f); // Breaks if test1 didn't run!
}

// GOOD: Each test is independent
@Test
public void test_PlayerMovement() {
    PlayerBase player = new PlayerBase(...); // Fresh instance
    player.update(0.016f);
    // ...
}
```

#### ❌ Pitfall 3: Not Testing Edge Cases
```java
// BAD: Only testing happy path
@Test
public void test_takeDamage() {
    player.takeDamage(25);
    assertEquals(75, player.getHealth());
}

// GOOD: Testing edge cases too
@Test public void test_takeDamage_Zero() { /* ... */ }
@Test public void test_takeDamage_Negative() { /* ... */ }
@Test public void test_takeDamage_Overkill() { /* ... */ }
@Test public void test_takeDamage_WhenDead() { /* ... */ }
```

---

### Final Checklist Before Submission

✅ All 250+ tests implemented in `MasterGameTestSuite.java`  
✅ Tests organized by phase (comments or nested classes)  
✅ Every test has descriptive name and assert messages  
✅ All tests pass (0 failures, 0 errors)  
✅ Test coverage >= 90% (all public methods tested)  
✅ Edge cases covered (null, zero, negative, extreme values)  
✅ Integration tests validate multi-system scenarios  
✅ Performance tests confirm 60 FPS under load  
✅ Test execution time < 60 minutes  
✅ Documentation updated with test results  

---

## ═══════════════════════════════════════════════════════════════════════════════
## TEST FAILURE RESPONSE GUIDE
## ═══════════════════════════════════════════════════════════════════════════════

### Purpose of This Section
When tests FAIL, you need to know:
1. **Which file(s) to fix**
2. **What code to add/change**
3. **Where to add exception handling**
4. **How to verify the fix**

This guide maps each test failure to the exact remediation steps.

---

### PHASE 1 FAILURES: Foundation

#### If `test_GameCore_loadImage_ValidPath` FAILS

**Symptom**: Image returns NULL even with valid path

**Files to Fix**:
- `game2D/GameCore.java` → `loadImage()` method

**What to Add**:
```java
public BufferedImage loadImage(String fileName) {
    try {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            System.err.println("ERROR: File not found: " + fileName);
            return null; // CRITICAL: Return null, don't throw exception
        }
        BufferedImage img = ImageIO.read(is);
        is.close();
        return img;
    } catch (IOException e) {
        System.err.println("ERROR: Failed to load image: " + fileName);
        e.printStackTrace();
        return null; // CRITICAL: Return null on error
    }
}
```

**Exception Handling Required**:
- ✅ Catch `IOException` (file read errors)
- ✅ Check for NULL `InputStream` (file not found)
- ✅ Log error with exact file path
- ✅ Return NULL (don't throw exception to caller)

**Verification**:
```bash
javac -d bin src/game2D/GameCore.java
# Re-run test:
# test_GameCore_loadImage_ValidPath should now PASS
```

---

#### If `test_GameCore_gameLoop_FPSLimit` FAILS

**Symptom**: FPS too high (100+) or too low (<30)

**Files to Fix**:
- `game2D/GameCore.java` → `gameLoop()` method

**What to Add**:
```java
public void gameLoop() {
    long targetFrameTime = 16_666_667; // 16.67ms = 60 FPS (in nanoseconds)
    
    while (running) {
        long frameStart = System.nanoTime();
        
        update(deltaTime);
        draw(graphics);
        
        long frameTime = System.nanoTime() - frameStart;
        long sleepTime = targetFrameTime - frameTime;
        
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime / 1_000_000); // Convert nanos to millis
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
```

**Exception Handling Required**:
- ✅ Catch `InterruptedException` in sleep
- ✅ Restore interrupt status with `Thread.currentThread().interrupt()`

---

### PHASE 2 FAILURES: Asset Loading

#### If `test_AssetLoading_loadCharacterSprites_BIKER` FAILS

**Symptom**: Sprites map is NULL or missing animation states

**Files to Fix**:
- `utilities/SpriteLoader.java` (if exists) OR
- `game2D/GameCore.java` → add `loadCharacterSprites()` method

**What to Add**:
```java
public Map<AnimationState, BufferedImage[]> loadCharacterSprites(CharacterType character) {
    Map<AnimationState, BufferedImage[]> sprites = new HashMap<>();
    String basePath = "Resources/industrial-zone/characters/" + character.getName() + "/";
    
    try {
        // IDLE: 4 frames
        sprites.put(AnimationState.IDLE, loadAnimationFrames(basePath + "Idle/Idle-", 4));
        
        // WALK: 6 frames
        sprites.put(AnimationState.WALK, loadAnimationFrames(basePath + "Walk/Walk-", 6));
        
        // JUMP: 3 frames
        sprites.put(AnimationState.JUMP, loadAnimationFrames(basePath + "Jump/Jump-", 3));
        
        // ATTACK: 4 frames
        sprites.put(AnimationState.ATTACK, loadAnimationFrames(basePath + "Attack/Attack-", 4));
        
        // HIT: 2 frames
        sprites.put(AnimationState.HIT, loadAnimationFrames(basePath + "Hit/Hit-", 2));
        
        // DEATH: 5 frames
        sprites.put(AnimationState.DEATH, loadAnimationFrames(basePath + "Death/Death-", 5));
        
    } catch (Exception e) {
        System.err.println("ERROR: Failed to load sprites for " + character);
        e.printStackTrace();
        return null;
    }
    
    return sprites;
}

private BufferedImage[] loadAnimationFrames(String basePathWithPrefix, int frameCount) {
    BufferedImage[] frames = new BufferedImage[frameCount];
    for (int i = 0; i < frameCount; i++) {
        String path = basePathWithPrefix + String.format("%03d", i + 1) + ".png";
        frames[i] = loadImage(path);
        if (frames[i] == null) {
            System.err.println("WARNING: Frame " + i + " is NULL for " + basePathWithPrefix);
        }
    }
    return frames;
}
```

**Exception Handling Required**:
- ✅ Catch generic `Exception` (any asset loading error)
- ✅ Log character name in error message
- ✅ Return NULL if sprites fail to load
- ✅ Warn on NULL frames but continue loading others

---

#### If `test_AssetLoading_loadSound_JumpSound` FAILS

**Symptom**: Sound is NULL or not playable

**Files to Fix**:
- `audio/AudioManager.java` → `loadSound()` method
- `audio/SoundEffect.java` → add `isLoaded()` method

**What to Add to AudioManager.java**:
```java
public SoundEffect loadSound(String soundName) {
    try {
        String path = "Resources/industrial-zone/audio/" + soundName;
        InputStream audioStream = getClass().getClassLoader().getResourceAsStream(path);
        
        if (audioStream == null) {
            System.err.println("ERROR: Sound file not found: " + path);
            return null;
        }
        
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        
        return new SoundEffect(clip, soundName);
        
    } catch (UnsupportedAudioFileException e) {
        System.err.println("ERROR: Unsupported audio format: " + soundName);
        return null;
    } catch (IOException e) {
        System.err.println("ERROR: Failed to read audio file: " + soundName);
        return null;
    } catch (LineUnavailableException e) {
        System.err.println("ERROR: Audio line unavailable for: " + soundName);
        return null;
    }
}
```

**Exception Handling Required**:
- ✅ Catch `UnsupportedAudioFileException` (wrong format)
- ✅ Catch `IOException` (file read error)
- ✅ Catch `LineUnavailableException` (audio system error)
- ✅ Return NULL on all errors
- ✅ Log specific error type for debugging

---

### PHASE 3 FAILURES: File Loading

#### If `test_FileLoading_loadMap_Level1` FAILS

**Symptom**: Map is NULL or has wrong dimensions

**Files to Fix**:
- `level/MapLoader.java` (create if doesn't exist)
- `utilities/FileParser.java` (if needed)

**What to Add**:
```java
public int[][] loadMap(String mapPath) {
    try {
        InputStream is = getClass().getClassLoader().getResourceAsStream(mapPath);
        if (is == null) {
            System.err.println("ERROR: Map file not found: " + mapPath);
            return null;
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<String> lines = new ArrayList<>();
        String line;
        
        int width = 0, height = 0;
        
        // Read file
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue; // Skip comments
            
            if (line.startsWith("WIDTH=")) {
                width = Integer.parseInt(line.substring(6));
            } else if (line.startsWith("HEIGHT=")) {
                height = Integer.parseInt(line.substring(7));
            } else {
                lines.add(line);
            }
        }
        reader.close();
        
        if (width == 0 || height == 0) {
            System.err.println("ERROR: Map dimensions not specified in " + mapPath);
            return null;
        }
        
        // Parse tile IDs
        int[][] map = new int[height][width];
        for (int y = 0; y < Math.min(height, lines.size()); y++) {
            String[] tokens = lines.get(y).split("\\s+");
            for (int x = 0; x < Math.min(width, tokens.length); x++) {
                map[y][x] = Integer.parseInt(tokens[x]);
            }
        }
        
        return map;
        
    } catch (IOException e) {
        System.err.println("ERROR: Failed to read map file: " + mapPath);
        e.printStackTrace();
        return null;
    } catch (NumberFormatException e) {
        System.err.println("ERROR: Invalid number format in map file: " + mapPath);
        e.printStackTrace();
        return null;
    }
}
```

**Exception Handling Required**:
- ✅ Catch `IOException` (file read errors)
- ✅ Catch `NumberFormatException` (invalid tile IDs)
- ✅ Handle missing WIDTH/HEIGHT
- ✅ Skip comment lines (start with #)
- ✅ Return NULL on errors

---

### PHASE 4 FAILURES: Animation

#### If `test_Animation_update_FrameProgression` FAILS

**Symptom**: Animation stuck on frame 0, doesn't advance

**Files to Fix**:
- `animation/AnimationPlayer.java` → `update()` method

**What to Add**:
```java
public void update(float deltaTime) {
    if (deltaTime <= 0) return; // CRITICAL: Don't update with zero/negative time
    
    animationTimer += deltaTime;
    
    while (animationTimer >= frameDuration) {
        animationTimer -= frameDuration;
        currentFrameIndex++;
        
        if (currentFrameIndex >= totalFrames) {
            if (loop) {
                currentFrameIndex = 0; // Loop back to first frame
            } else {
                currentFrameIndex = totalFrames - 1; // Stay on last frame
                isFinished = true;
            }
        }
    }
}
```

**Exception Handling Required**:
- ✅ Guard against zero/negative delta time
- ✅ Use WHILE loop (not IF) for timer check - handles large deltas
- ✅ Ensure currentFrameIndex never exceeds totalFrames

---

### PHASE 5 FAILURES: Collision Detection

#### If `test_Collision_AABB_OverlappingBoxes` FAILS

**Symptom**: Collision not detected when boxes clearly overlap

**Files to Fix**:
- `physics/CollisionDetector.java` → `checkAABBCollision()` method

**What to Add**:
```java
public static boolean checkAABBCollision(BoundingBox a, BoundingBox b) {
    if (a == null || b == null) {
        return false; // CRITICAL: Null check
    }
    
    return a.x < b.x + b.width &&
           a.x + a.width > b.x &&
           a.y < b.y + b.height &&
           a.y + a.height > b.y;
}
```

**Exception Handling Required**:
- ✅ NULL checks for both boxes
- ✅ No exceptions thrown (return false if invalid)

---

### PHASE 7 FAILURES: AI Behavior

#### If `test_AI_ChasePlayer` FAILS

**Symptom**: Enemy doesn't move toward player

**Files to Fix**:
- `ai/EnemyAI.java` → `chase()` method
- `entities/EnemyInstance.java` → `setVelocity()` method

**What to Add**:
```java
private void chase(Player player, float deltaTime) {
    if (player == null || !player.isAlive()) {
        currentState = AIState.PATROL;
        return;
    }
    
    float dx = player.getX() - enemy.getX();
    float dy = player.getY() - enemy.getY();
    float distance = (float) Math.sqrt(dx * dx + dy * dy);
    
    if (distance < 0.1f) return; // Already at player position
    
    // Normalize direction
    dx /= distance;
    dy /= distance;
    
    // Move toward player
    float speed = enemy.getChaseSpeed();
    enemy.setVelocity(dx * speed, dy * speed);
}
```

**Exception Handling Required**:
- ✅ NULL check for player
- ✅ Check if player is alive
- ✅ Avoid division by zero (distance < 0.1)

---

## ═══════════════════════════════════════════════════════════════════════════════
## EXCEPTION HANDLING STRATEGY BY SYSTEM
## ═══════════════════════════════════════════════════════════════════════════════

### General Principles

**Rule 1**: **NEVER throw exceptions to the game loop**
- Game loop must NEVER crash
- Catch all exceptions, log error, return safe default

**Rule 2**: **Return NULL for failed resource loading**
- Images, sounds, maps that fail to load → return NULL
- Caller must check for NULL

**Rule 3**: **Log errors verbosely**
- Include exact file path
- Include error type
- Print stack trace for debugging

**Rule 4**: **Degrade gracefully**
- Missing sprite → use placeholder or skip rendering
- Missing sound → silent (no crash)
- Missing map → return to menu

---

### System-by-System Exception Handling

#### 1. Asset Loading (Part 2)

**Files**: `GameCore.java`, `SpriteLoader.java`, `AudioManager.java`

**Exceptions to Catch**:
```java
// Image loading
try {
    BufferedImage img = ImageIO.read(inputStream);
} catch (IOException e) {
    // File corrupted or unreadable
    System.err.println("ERROR: Failed to load image: " + path);
    return null;
}

// Audio loading
try {
    Clip clip = AudioSystem.getClip();
} catch (LineUnavailableException e) {
    // Audio system not available
    System.err.println("ERROR: Audio system unavailable");
    return null;
} catch (UnsupportedAudioFileException e) {
    // Wrong audio format
    System.err.println("ERROR: Unsupported audio format: " + path);
    return null;
}
```

**Strategy**:
- Return NULL on failure
- Log exact file path
- Continue game with missing assets

---

#### 2. File Loading (Part 6)

**Files**: `MapLoader.java`, `ConfigParser.java`

**Exceptions to Catch**:
```java
try {
    BufferedReader reader = new BufferedReader(new FileReader(file));
} catch (FileNotFoundException e) {
    System.err.println("ERROR: Map file not found: " + path);
    return null;
} catch (IOException e) {
    System.err.println("ERROR: Failed to read map: " + path);
    e.printStackTrace();
    return null;
} catch (NumberFormatException e) {
    System.err.println("ERROR: Invalid tile ID in map: " + path);
    return null;
}
```

**Strategy**:
- Return NULL for missing/corrupted files
- Return to main menu if level can't load
- Log line number where parsing failed

---

#### 3. Physics & Collision (Part 5)

**Files**: `CollisionDetector.java`, `PhysicsSystem.java`

**Exceptions to Catch**:
```java
public void resolveCollision(Entity a, Entity b) {
    if (a == null || b == null) {
        System.err.println("WARNING: Null entity in collision resolution");
        return; /// Graceful exit
    }
    
    try {
        // Collision resolution math
        float overlapX = calculateOverlap(a, b);
        // ... division operations
    } catch (ArithmeticException e) {
        System.err.println("ERROR: Math error in collision: " + e.getMessage());
        // Don't resolve collision, let entities pass through
    }
}
```

**Strategy**:
- NULL checks for all entities
- Catch `ArithmeticException` (division by zero)
- Default: let entities pass through if resolution fails

---

#### 4. Animation (Part 3)

**Files**: `AnimationPlayer.java`, `entities/PlayerBase.java`

**Exceptions to Catch**:
```java
public BufferedImage getCurrentFrame() {
    try {
        return frames[currentFrameIndex];
    } catch (ArrayIndexOutOfBoundsException e) {
        System.err.println("ERROR: Animation frame index out of bounds: " + currentFrameIndex);
        currentFrameIndex = 0; // Reset to first frame
        return frames[0];
    } catch (NullPointerException e) {
        System.err.println("ERROR: Animation frames not loaded");
        return null; // Caller must handle null
    }
}
```

**Strategy**:
- Catch `ArrayIndexOutOfBoundsException` (frame index error)
- Reset to frame 0
- Return NULL if frames not loaded

---

#### 5. AI Behavior (Part 4)

**Files**: `ai/EnemyAI.java`, `ai/Pathfinding.java`

**Exceptions to Catch**:
```java
public void update(float deltaTime, Player player) {
    try {
        if (player == null) {
            currentState = AIState.IDLE;
            return;
        }
        
        float distance = calculateDistance(enemy, player);
        // ... AI logic
        
    } catch (Exception e) {
        System.err.println("ERROR: AI update failed for " + enemy.getType());
        e.printStackTrace();
        // Fallback: enemy stands still
        enemy.setVelocity(0, 0);
    }
}
```

**Strategy**:
- NULL check for player
- Catch generic `Exception` (AI should never crash game)
- Fallback: enemy becomes idle/stationary

---

## ═══════════════════════════════════════════════════════════════════════════════
## TEST INTERFACE & STRUCTURE
## ═══════════════════════════════════════════════════════════════════════════════

### Single File Test Suite Structure

All tests in `src/12_Tests/MasterGameTestSuite.java`:

```java
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class MasterGameTestSuite {
    
    // ═══════════════════════════════════════════════════════════════
    // SETUP & TEARDOWN
    // ═══════════════════════════════════════════════════════════════
    
    private Game game;
    private PlayerBase player;
    private EnemyInstance enemy;
    
    @Before
    public void setUp() {
        // Runs before EACH test
        game = new Game();
        player = new PlayerBase(CharacterType.BIKER, 100, 100);
        enemy = Enemies.createEnemy(EnemyType.UFO_SAUCER, 500, 100);
    }
    
    @After
    public void tearDown() {
        // Runs after EACH test (cleanup)
        if (game != null) game.stop();
        game = null;
        player = null;
        enemy = null;
    }
    
    // ═══════════════════════════════════════════════════════════════
    // PHASE 1: FOUNDATION TESTS
    // ═══════════════════════════════════════════════════════════════
    
    @Test
    public void test_GameCore_loadImage_ValidPath() {
        BufferedImage img = game.loadImage("Resources/industrial-zone/gui/hud_panel.png");
        assertNotNull(img);
        assertTrue(img.getWidth() > 0);
    }
    
    @Test
    public void test_GameCore_loadImage_InvalidPath() {
        BufferedImage img = game.loadImage("nonexistent.png");
        assertNull(img);
    }
    
    // ... more Phase 1 tests
    
    // ═══════════════════════════════════════════════════════════════
    // PHASE 2: ASSET LOADING TESTS
    // ═══════════════════════════════════════════════════════════════
    
    @Test
    public void test_AssetLoading_loadCharacterSprites_BIKER() {
        Map<AnimationState, BufferedImage[]> sprites = game.loadCharacterSprites(CharacterType.BIKER);
        assertNotNull(sprites);
        assertEquals(6, sprites.size());
    }
    
    // ... more Phase 2 tests
    
    // ═══════════════════════════════════════════════════════════════
    // HELPER METHODS (used by multiple tests)
    // ═══════════════════════════════════════════════════════════════
    
    private void simulateFrames(int frameCount) {
        for (int i = 0; i < frameCount; i++) {
            game.update(0.016f); // 16ms per frame = 60 FPS
        }
    }
    
    private boolean areFloatsEqual(float a, float b, float delta) {
        return Math.abs(a - b) < delta;
    }
}
```

**Key Structure Points**:
- ✅ Single file, multiple test methods
- ✅ `@Before` setUp() creates fresh objects for each test
- ✅ `@After` tearDown() cleans up resources
- ✅ Tests organized by phase with comment headers
- ✅ Helper methods at bottom (no duplication)

---

### Running the Test Suite

**Option 1: Command Line (using JUnit CLI)**
```bash
# Compile tests
javac -d bin -cp "bin:junit-4.13.2.jar:hamcrest-core-1.3.jar" src/12_Tests/MasterGameTestSuite.java

# Run all tests
java -cp "bin:junit-4.13.2.jar:hamcrest-core-1.3.jar" org.junit.runner.JUnitCore MasterGameTestSuite
```

**Option 2: IDE (VSCode, IntelliJ, Eclipse)**
- Right-click `MasterGameTestSuite.java`
- Select "Run JUnit Tests"

**Option 3: Build Script**
Create `RUN_TESTS.bat`:
```batch
@echo off
cd /d "%~dp0handout"
javac -d bin -cp "bin;lib/*" src/12_Tests/MasterGameTestSuite.java
java -cp "bin;lib/*" org.junit.runner.JUnitCore MasterGameTestSuite
pause
```

---

## ═══════════════════════════════════════════════════════════════════════════════
## COMPLETE FEATURE COVERAGE (INCLUDING PARALLAX, VFX, ETC.)
## ═══════════════════════════════════════════════════════════════════════════════

### Features That Must Be Tested (Previously Skipped)

#### 1. Parallax Scrolling

**Files**: `rendering/ParallaxBackground.java`, `level/Level1.java`

**Tests to Add**:
```java
@Test
public void test_ParallaxScrolling_BackgroundLayers() {
    // ARRANGE: Parallax with 3 layers (far, mid, near)
    ParallaxBackground parallax = new ParallaxBackground();
    parallax.addLayer("bg_far.png", 0.2f);   // Moves at 20% camera speed
    parallax.addLayer("bg_mid.png", 0.5f);   // Moves at 50% camera speed
    parallax.addLayer("bg_near.png", 0.8f);  // Moves at 80% camera speed
    
    // ACT: Move camera 100 pixels right
    parallax.updateCameraX(100);
    
    // ASSERT: Each layer moves at different speed
    assertEquals("Far layer should move 20px", 20f, parallax.getLayerOffset(0), 0.1f);
    assertEquals("Mid layer should move 50px", 50f, parallax.getLayerOffset(1), 0.1f);
    assertEquals("Near layer should move 80px", 80f, parallax.getLayerOffset(2), 0.1f);
}

@Test
public void test_ParallaxScrolling_Wrapping() {
    // Test that background wraps seamlessly when camera passes image width
    ParallaxBackground parallax = new ParallaxBackground();
    parallax.addLayer("bg.png", 0.5f);
    
    float imageWidth = 1920; // Background image width
    parallax.updateCameraX(imageWidth * 2); // Move past image twice
    
    // Background should wrap (offset < imageWidth)
    float offset = parallax.getLayerOffset(0);
    assertTrue("Background should wrap", offset < imageWidth);
}
```

**Exception Handling**:
```java
public void addLayer(String imagePath, float scrollSpeed) {
    try {
        BufferedImage layer = GameCore.loadImage(imagePath);
        if (layer == null) {
            System.err.println("WARNING: Parallax layer not found: " + imagePath);
            return; // Skip this layer
        }
        layers.add(new ParallaxLayer(layer, scrollSpeed));
    } catch (Exception e) {
        System.err.println("ERROR: Failed to add parallax layer: " + imagePath);
        // Continue without this layer
    }
}
```

---

#### 2. Visual Effects (VFX)

**Files**: `vfx/ParticleSystem.java`, `vfx/ExplosionEffect.java`

**Tests to Add**:
```java
@Test
public void test_VFX_ParticleLifetime() {
    // ARRANGE: Create explosion with 50 particles, 2-second lifetime
    ParticleSystem explosion = new ParticleSystem(100, 100, 50);
    explosion.setLifetime(2.0f);
    explosion.start();
    
    // ACT: Update for 1 second (half lifetime)
    for (int i = 0; i < 60; i++) explosion.update(0.016f);
    
    // ASSERT: Particles still active
    assertTrue("Particles should still be active", explosion.isActive());
    
    // ACT: Update for another 1.5 seconds (past lifetime)
    for (int i = 0; i < 90; i++) explosion.update(0.016f);
    
    // ASSERT: Particles finished
    assertFalse("Particles should be finished", explosion.isActive());
}

@Test
public void test_VFX_ParticleVelocity() {
    // Test that particles move correctly based on velocity
    Particle p = new Particle(100, 100);
    p.setVelocity(50, -100); // Move right and up
    
    float initialX = p.getX();
    float initialY = p.getY();
    
    p.update(1.0f); // Update for 1 second
    
    assertEquals("X should increase by 50", initialX + 50, p.getX(), 0.1f);
    assertEquals("Y should decrease by 100", initialY - 100, p.getY(), 0.1f);
}
```

---

#### 3. Camera System

**Files**: `rendering/Camera.java`, `framework/CameraManager.java`

**Tests to Add**:
```java
@Test
public void test_Camera_FollowPlayer_Smooth() {
    // ARRANGE: Camera and player
    Camera camera = new Camera(800, 600);
    camera.setSmoothFollow(true, 0.1f); // 10% lerp
    
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 1000, 500);
    
    // ACT: Update camera to follow player
    for (int i = 0; i < 10; i++) {
        camera.follow(player);
    }
    
    // ASSERT: Camera should be near player (not exactly on player due to smoothing)
    float cameraCenterX = camera.getX() + camera.getWidth() / 2;
    assertTrue("Camera should be close to player", Math.abs(cameraCenterX - player.getX()) < 100);
}

@Test
public void test_Camera_WorldBounds() {
    // Test that camera doesn't go outside world bounds
    Camera camera = new Camera(800, 600);
    camera.setWorldBounds(0, 0, 3000, 1000);
    
    // ACT: Try to move camera outside bounds
    camera.setPosition(-100, -100); // Try to go negative
    
    // ASSERT: Camera clamped to world bounds
    assertEquals("Camera X should be clamped to 0", 0f, camera.getX(), 0.1f);
    assertEquals("Camera Y should be clamped to 0", 0f, camera.getY(), 0.1f);
}
```

---

#### 4. Tile System Details

**Files**: `tiles/TileMap.java`, `tiles/TileManager.java`

**Tests to Add**:
```java
@Test
public void test_TileMap_GetTileAt() {
    // ARRANGE: Load Level 1 map
    int[][] mapData = MapLoader.loadMap("maps/level_1/map.txt");
    TileMap tileMap = new TileMap(mapData);
    
    // ACT: Get tile at specific coordinates
    int tileID = tileMap.getTileAt(100, 200);
    
    // ASSERT: Tile ID is valid (0-19)
    assertTrue("Tile ID should be >= 0", tileID >= 0);
    assertTrue("Tile ID should be < 20", tileID < 20);
}

@Test
public void test_TileMap_CollisionCheck() {
    // Test that solid tiles block movement
    int[][] mapData = {
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {1, 1, 1, 1}  // Row of solid tiles
    };
    TileMap tileMap = new TileMap(mapData);
    
    // Player standing on solid tile (y=128, tile size 64)
    boolean isSolid = tileMap.isSolidTile(64, 128);
    assertTrue("Tile should be solid", isSolid);
    
    // Player in air above solid tile
    boolean isAir = tileMap.isSolidTile(64, 64);
    assertFalse("Tile should be air", isAir);
}
```

---

#### 5. UI Elements

**Files**: `gui/HUDPanel.java`, `gui/TopBarPanel.java`, `gui/screens/PauseMenu.java`

**Tests to Add**:
```java
@Test
public void test_HUD_HealthBar_Rendering() {
    // ARRANGE: HUD with player health
    HUDPanel hud = new HUDPanel();
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 0, 0);
    player.setHealth(75); // 75/100 health
    
    // ACT: Update HUD
    hud.update(player);
    
    // ASSERT: Health bar width reflects 75%
    float healthBarWidth = hud.getHealthBarWidth();
    float expectedWidth = hud.getMaxHealthBarWidth() * 0.75f;
    assertEquals("Health bar should be 75% width", expectedWidth, healthBarWidth, 1.0f);
}

@Test
public void test_PauseMenu_Selection() {
    // ARRANGE: Pause menu with 3 options
    PauseMenu menu = new PauseMenu();
    menu.addOption("Resume");
    menu.addOption("Restart");
    menu.addOption("Quit");
    
    // ACT: Navigate down
    menu.moveSelectionDown();
    
    // ASSERT: Selection moved to option 1
    assertEquals("Selection should be option 1", 1, menu.getSelectedIndex());
    
    // ACT: Navigate down again
    menu.moveSelectionDown();
    assertEquals("Selection should be option 2", 2, menu.getSelectedIndex());
    
    // ACT: Navigate down again (should wrap to 0)
    menu.moveSelectionDown();
    assertEquals("Selection should wrap to 0", 0, menu.getSelectedIndex());
}
```

---

#### 6. Level-Specific Mechanics

**Files**: `level/Level1.java`, `level/Level2.java`

**Tests to Add**:
```java
@Test
public void test_Level1_EnemySpawns() {
    // ARRANGE: Load Level 1
    Level1 level = new Level1();
    
    // ACT: Initialize level (spawns enemies)
    level.initialize();
    
    // ASSERT: Enemies spawned
    List<EnemyInstance> enemies = level.getEnemies();
    assertTrue("Level 1 should have > 0 enemies", enemies.size() > 0);
}

@Test
public void test_Level1_GoalReached() {
    // ARRANGE: Level 1 with player near goal
    Level1 level = new Level1();
    PlayerBase player = new PlayerBase(CharacterType.BIKER, 0, 0);
    
    // ACT: Move player to goal position
    player.setPosition(level.getGoalX(), level.getGoalY());
    level.update(player);
    
    // ASSERT: Level completed
    assertTrue("Level should be completed", level.isCompleted());
}
```

---

## ═══════════════════════════════════════════════════════════════════════════════
## NESTED STATIC CLASS MIGRATION ISSUES
## ═══════════════════════════════════════════════════════════════════════════════

### Background

**Previously**: Many files in same folder were **nested static classes**
```java
// OLD STRUCTURE (before reorganization)
public class Managers {
    public static class AnimationManager { /* ... */ }
    public static class AssetManager { /* ... */ }
    public static class AudioManager { /* ... */ }
}
```

**Now**: Each class is its own file
```
2_Managers/
    AnimationManager.java
    AssetManager.java
    AudioManager.java
```

**Problem**: Code may still reference nested class syntax: `Managers.AnimationManager`

---

### Issues & Fixes

#### Issue 1: Incorrect Class References

**Symptom**: Compile error "cannot find symbol: class Managers"

**Example**:
```java
// OLD CODE (broken)
Managers.AnimationManager anim = new Managers.AnimationManager();
```

**Fix**:
```java
// NEW CODE (correct)
AnimationManager anim = new AnimationManager();
```

**Files to Check**:
- All files in `2_Managers/` folder
- All files in `3_Controllers/` folder
- All files in `5_Animation/` folder

**How to Fix Automatically**:
Run this script:
```bash
cd handout/src
# Find all references to old nested class syntax
grep -r "Managers\." * | grep -v "duplicates"

# Replace with correct imports
# (Manual fix or use find-replace in IDE)
```

---

#### Issue 2: Missing Package Declarations

**Symptom**: "package X does not exist" errors

**All files** must have correct package declaration at top:
```java
// File: 2_Managers/AnimationManager.java
package managers; // REQUIRED

import entities.*;
import animation.*;

public class AnimationManager {
    // ...
}
```

**Package Names by Folder**:
| Folder | Package Name |
|--------|--------------|
| 1_Framework | `framework` |
| 2_Managers | `managers` |
| 3_Controllers | `controllers` |
| 4_Entities | `entities` |
| 5_Animation | `animation` |
| 6_Physics | `physics` |
| 7_AI | `ai` |
| 8_Utilities | `utilities` |
| 9_Enums | `enums` |
| 10_Interfaces | `interfaces` |
| 11_Exceptions | `exceptions` |
| 12_Tests | `tests` |

---

#### Issue 3: Visibility Changes

**Problem**: Nested static classes could access parent class `private` members

**Example**:
```java
// OLD: Nested class could access parent's private fields
public class Managers {
    private static Map<String, Object> cache = new HashMap<>();
    
    public static class AssetManager {
        public void loadAsset(String name) {
            cache.put(name, ...); // This worked when nested
        }
    }
}
```

**Fix**: Make fields `public static` or add getter methods
```java
// File: utilities/ResourceCache.java
public class ResourceCache {
    private static Map<String, Object> cache = new HashMap<>();
    
    public static void put(String name, Object value) {
        cache.put(name, value);
    }
    
    public static Object get(String name) {
        return cache.get(name);
    }
}

// File: 2_Managers/AssetManager.java
package managers;
import utilities.ResourceCache;

public class AssetManager {
    public void loadAsset(String name) {
        ResourceCache.put(name, ...); // Now uses public API
    }
}
```

---

#### Issue 4: Circular Dependencies

**Problem**: Files in same folder referencing each other can create circular imports

**Example**:
```java
// AnimationManager.java
import managers.AssetManager; // Error: circular dependency

// AssetManager.java
import managers.AnimationManager; // Error: circular dependency
```

**Fix**: Extract shared code to separate utility class
```java
// File: utilities/AssetCache.java
public class AssetCache {
    private static Map<String, BufferedImage> images = new HashMap<>();
    
    public static void cacheImage(String name, BufferedImage img) {
        images.put(name, img);
    }
    
    public static BufferedImage getImage(String name) {
        return images.get(name);
    }
}

// Now both AnimationManager and AssetManager can import AssetCache
```

---

### Pre-Testing Checklist for Nested Class Migration

✅ **Step 1**: Search for old nested class references
```bash
grep -r "Managers\." src/
grep -r "Controllers\." src/
grep -r "Entities\." src/
```

✅ **Step 2**: Verify all package declarations
```bash
# Each file should start with: package <folder_name>;
for file in src/**/*.java; do
    head -1 "$file" | grep "package" || echo "MISSING PACKAGE: $file"
done
```

✅ **Step 3**: Fix visibility issues
- Search for `private static` fields referenced across files
- Make them `public static` or add getter/setter methods

✅ **Step 4**: Run compilation test
```bash
cd handout
javac -d bin src/**/*.java 2> errors.txt
# Check errors.txt for remaining issues
```

✅ **Step 5**: Document remaining manual fixes
Create `MIGRATION_NOTES.md` listing files that need manual attention

---

## ═══════════════════════════════════════════════════════════════════════════════
## APPENDIX: QUICK REFERENCE
## ═══════════════════════════════════════════════════════════════════════════════

### Test Count by Category
| Part | System | Test Count | Priority |
|------|--------|-----------|----------|
| 1 | Core Infrastructure | 15 | CRITICAL |
| 2 | Entity Management | 60 | HIGH |
| 3 | Physics Engine | 35 | CRITICAL |
| 4 | State Management | 25 | HIGH |
| 5 | Input & UI | 30 | MEDIUM |
| 6 | Level & Tiles | 20 | MEDIUM |
| 7 | Animation | 25 | MEDIUM |
| 8 | Integration | 20 | HIGH |
| 9 | Performance | 10 | LOW |
| 10 | Edge Cases | 10 | LOW |
| **TOTAL** | - | **250** | - |

---

### Key Constants to Test Against
```java
// Physics
GRAVITY = 980; // pixels per second squared
TERMINAL_VELOCITY = 700; // max fall speed
FRICTION = 0.85; // ground friction multiplier
JUMP_VELOCITY = -440; // upward jump speed

// Player
MAX_HEALTH = 100;
MOVE_SPEED = 200; // pixels per second
DASH_SPEED = 400;
ATTACK_DAMAGE = 25;

// Gameplay
FPS_TARGET = 60;
FRAME_TIME = 16.67; // milliseconds (1000/60)
SCREEN_WIDTH = 1200;
SCREEN_HEIGHT = 800;
```

---

### Sample Test Template
```java
/**
 * Template for writing new tests following AAA pattern
 */
@Test
public void test_ClassName_methodName_scenario() {
    // ========== ARRANGE ==========
    // Set up test objects and initial state
    ClassName obj = new ClassName(param1, param2);
    int expectedValue = 100;
    
    // ========== ACT ==========
    // Execute the method being tested
    int actualValue = obj.methodName();
    
    // ========== ASSERT ==========
    // Verify the result matches expectations
    assertEquals("Method should return 100", expectedValue, actualValue);
    assertTrue("Object should be in valid state", obj.isValid());
    assertNotNull("Result should not be null", obj.getResult());
}
```

---

### Debugging Failed Tests
When a test fails, follow this debugging process:

1. **Read the failure message** - It tells you what was expected vs. actual
2. **Check test preconditions** - Did the ARRANGE phase set up correctly?
3. **Verify method behavior** - Run the method manually with same inputs
4. **Add logging** - Print intermediate values to see where it breaks
5. **Simplify test** - Remove complexity until you isolate the bug
6. **Fix the bug** - Correct the production code or test code
7. **Run again** - Verify the test now passes
8. **Run all tests** - Ensure the fix didn't break other tests

---

## END OF COMPREHENSIVE TEST PLAN
**Document Version**: 2.0  
**Last Updated**: April 14, 2026  
**Total Pages**: 35+  
**Target Coverage**: 618+ Java files, 250+ tests  

---
   - Assert: `enemies.size() == 3`, spacing correct

4. **`createAllEnemyTypes(float x, float y)`** ✓
   - Test Case: Create one of each type
   - Validate: All types instantiated
   - Assert: `enemies.size() >= 3`

5. **`loadEnemySprites(String enemyType, String stateName)`** ✓
   - Test Cases:
     - Load UFO_SAUCER IDLE
     - Load HoverPlatform ATTACK
     - Load JetDrone DEATH
   - Validate: Sprite frames loaded
   - Assert: `sprites.length > 0`

6. **`renderEnemy(...)`** ✓
   - Test Case: Render with valid parameters
   - Validate: Enemy drawn at world position
   - Assert: No exceptions thrown

7. **`update(float deltaTime)` (on EnemyInstance)** ✓
   - Test Case: Update with 16ms
   - Validate: Physics and AI updated
   - Assert: Position changes, no exceptions

8. **`getVelocityX()` / `getVelocityY()`** ✓
   - Test Case: After update
   - Validate: Velocities return values
   - Assert: Returns float values

9. **`setPosition(float x, float y)` / `getX()` / `getY()`** ✓
   - Test Case: Set to (250, 350)
   - Validate: Position updated
   - Assert: `getX() == 250`, `getY() == 350`

10. **`takeDamage(int damage)`** ✓
    - Test Case: Take 25 damage
    - Validate: Health decreased
    - Assert: `health -= damage`

11. **`getHealth()`** ✓
    - Test Case: After damage
    - Validate: Health returned
    - Assert: `health >= 0`

12. **`isAlive()`** ✓
    - Test Cases:
      - Health = 50 (alive)
      - Health = 0 (dead)
    - Validate: Correct alive state
    - Assert: `isAlive() == true/false`

---

### 2.3 PROJECTILE ENTITIES
**File**: `entities/Projectile.java`
**Purpose**: Weapon projectiles/bullets

#### Methods to Test:
1. **`Projectile(float startX, float startY, float velocityX, float velocityY, float damage, float lifetime)` Constructor** ✓
   - Test Case: Create projectile at (100, 200) with velocity (200, -50), damage 25, lifetime 5000ms
   - Validate: Projectile initialized
   - Assert: `getX() == 100`, `getY() == 200`, `getDamage() == 25`

2. **`update(float deltaTime)`** ✓
   - Test Cases:
     - Update with 16ms (should apply gravity)
     - Update multiple times
     - Update beyond lifetime
   - Validate: Position and velocity updated
   - Assert: `velocityY` increases (gravity), position changes, lifetime decreases

3. **`isAlive()`** ✓
   - Test Cases:
     - Recently created (alive)
     - After exceeding lifetime (dead)
     - Out of bounds (dead)
   - Validate: Correct alive state
   - Assert: `isAlive()` returns correct boolean

4. **`getX()` / `getY()`** ✓
   - Test Case: After update
   - Validate: Position returned
   - Assert: Position values >= 0

5. **`getDamage()`** ✓
   - Test Case: After creation
   - Validate: Damage value returned
   - Assert: `getDamage() == 25`

6. **`getWidth()` / `getHeight()`** ✓
   - Test Case: After creation
   - Validate: Dimensions returned
   - Assert: `getWidth() > 0`, `getHeight() > 0`

7. **`kill()` / `destroy()`** ✓
   - Test Case: Call kill()
   - Validate: Projectile marked for removal
   - Assert: `isAlive() == false`

8. **`render(Graphics2D g, int cameraX, int cameraY)`** ✓
   - Test Case: Render with mock Graphics2D
   - Validate: Drawn at screen position
   - Assert: No exceptions thrown

---

## PART 3: PHYSICS SYSTEM TESTS

### 3.1 PHYSICS UPDATE SYSTEM
**File**: `physics/PhysicsUpdateSystem.java`
**Purpose**: Core physics calculations (gravity, friction, velocity, position)

#### Methods to Test:
1. **`applyGravity(float velocityY, float deltaSeconds)`** ✓
   - Test Cases:
     - velocityY = 0, deltaSeconds = 0.016 (1 frame)
     - velocityY = 100 (falling), apply more gravity
     - velocityY = 700 (at terminal velocity)
   - Validate: Gravity applied correctly
   - Assert: `result > velocityY`, `result <= TERMINAL_VELOCITY`

2. **`applyGroundFriction(float velocityX, float deltaSeconds)`** ✓
   - Test Case: velocityX = 200, deltaSeconds = 0.016
   - Validate: Friction reduces X velocity
   - Assert: `result < velocityX`, friction coefficient 0.85 applied

3. **`applyAirResistance(float velocity)`** ✓
   - Test Case: velocity = 100
   - Validate: Air drag applied
   - Assert: `result = velocity * 0.95`

4. **`updatePosition(float position, float velocity, float deltaSeconds)`** ✓
   - Test Cases:
     - position = 100, velocity = 200, deltaSeconds = 0.1
     - position = 0, velocity = 0 (no movement)
     - Large values
   - Validate: Position integration correct
   - Assert: `result = position + velocity * deltaSeconds`

5. **`applyAcceleration(float velocity, float acceleration, float deltaSeconds)`** ✓
   - Test Case: velocity = 100, acceleration = 50, deltaSeconds = 0.1
   - Validate: Acceleration integrated
   - Assert: `result = velocity + acceleration * deltaSeconds`

6. **`clampVelocity(float velocity, float maxSpeed)`** ✓
   - Test Cases:
     - velocity = 50, maxSpeed = 100 (no change)
     - velocity = 150, maxSpeed = 100 (clamped)
     - velocity = -150, maxSpeed = 100 (clamped negative)
   - Validate: Velocity clamped correctly
   - Assert: `Math.abs(result) <= maxSpeed`

7. **`calculateJumpVelocity(float jumpHeight)`** ✓
   - Test Case: jumpHeight = 100
   - Validate: Jump velocity calculated (v = sqrt(2*g*h))
   - Assert: `result > 0`

8. **`isFalling(float velocityY)`** ✓
   - Test Cases:
     - velocityY = 100 (falling, positive)
     - velocityY = -100 (rising, negative)
     - velocityY = 0 (no Y movement)
   - Validate: Correct fall detection
   - Assert: `isFalling(100) == true`, `isFalling(-100) == false`

9. **`stopMovement()`** ✓
   - Test Case: Call for velocity reset
   - Validate: Returns 0
   - Assert: `stopMovement() == 0.0f`

---

### 3.2 COLLISION DETECTOR
**File**: `physics/CollisionDetector.java`
**Purpose**: AABB collision detection with spatial optimization

#### Methods to Test:
1. **`CollisionDetector()` / `CollisionDetector(boolean useSpatialOptimization)`** ✓
   - Test Cases:
     - Default constructor
     - With spatial optimization enabled
     - With spatial optimization disabled
   - Validate: Detector initialized
   - Assert: `detector != null`

2. **`setSpatialOptimization(boolean enabled)`** ✓
   - Test Case: Enable and disable
   - Validate: State toggles
   - Assert: `detector.isSpatialOptimizationActive() == enabled`

3. **`registerBoundingBox(float x, float y, float width, float height)`** ✓
   - Test Cases:
     - Register box 1 at (100, 100) size (50, 50)
     - Register box 2 at (120, 110) size (50, 50)
     - Register box 3 at (300, 300) size (40, 40)
   - Validate: Boxes registered with IDs
   - Assert: `boxId >= 0`, unique IDs returned

4. **`updatePosition(int boxId, float x, float y)`** ✓
   - Test Case: Update box 1 to (200, 200)
   - Validate: Position changed
   - Assert: Returns without error

5. **`moveBox(int boxId, float deltaX, float deltaY)`** ✓
   - Test Case: Move box 1 by (+10, +20)
   - Validate: Box moved relatively
   - Assert: Returns without error

6. **`checkCollision(BoundingBox box1, BoundingBox box2)`** ✓
   - Test Cases:
     - Overlapping boxes (100,100,50,50) vs (120,110,50,50) - COLLISION
     - Non-overlapping boxes (100,100,50,50) vs (300,300,50,50) - NO COLLISION
     - Touching edges (100,100,50,50) vs (150,100,50,50) - COLLISION EDGE
   - Validate: Correct collision detection
   - Assert: `checkCollision().colliding == true/false`

7. **`checkCollision(int boxId1, int boxId2)`** ✓
   - Test Case: Check collision by ID
   - Validate: Returns collision result
   - Assert: `result != null`

8. **`pointInBox(float x, float y, BoundingBox box)`** ✓
   - Test Cases:
     - Point inside box (120, 110) in (100,100,50,50) - TRUE
     - Point outside box (200, 200) in (100,100,50,50) - FALSE
   - Validate: Correct point test
   - Assert: `pointInBox() == true/false`

9. **`getDistance(BoundingBox box1, BoundingBox box2)`** ✓
   - Test Case: Get distance between two boxes
   - Validate: Returns non-negative distance
   - Assert: `distance >= 0`

10. **`circleBoxCollision(float cx, float cy, float radius, BoundingBox box)`** ✓
    - Test Cases:
      - Circle center at (125, 125) radius 30 vs box (100,100,50,50) - COLLISION
      - Circle center at (500, 500) radius 30 vs box (100,100,50,50) - NO COLLISION
    - Validate: Correct circle-box test
    - Assert: `circleBoxCollision() == true/false`

11. **`resolveCollision(int boxId1, int boxId2)`** ✓
    - Test Case: Resolve collision between overlapping boxes
    - Validate: Boxes separated
    - Assert: Boxes no longer colliding after resolution

12. **`getCollidingBoxes(int boxId)`** ✓
    - Test Case: Get all boxes colliding with box 1
    - Validate: Returns list
    - Assert: Returns list with size >= 0

13. **`removeBoundingBox(int boxId)`** ✓
    - Test Case: Remove box 1
    - Validate: Box removed
    - Assert: `getBoxCount()` decreases

14. **`clear()`** ✓
    - Test Case: Clear all boxes
    - Validate: All boxes removed
    - Assert: `getBoxCount() == 0`

15. **`getBoxCount()`** ✓
    - Test Case: After registering 3 boxes
    - Validate: Count correct
    - Assert: `getBoxCount() == 3`

16. **`getSpatialGridStats()`** ✓
    - Test Case: Get stats string
    - Validate: Stats returned
    - Assert: `stats != null && !stats.isEmpty()`

17. **`isSpatialOptimizationActive()`** ✓
    - Test Case: Check optimization status
    - Validate: Returns boolean
    - Assert: `returns true or false`

---

### 3.3 PHYSICS SYSTEM
**File**: `physics/PhysicsSystem.java`
**Purpose**: Unified physics engine with bodies, forces, collisions

#### Methods to Test:
1. **`PhysicsSystem()` Constructor** ✓
   - Test Case: Create physics system
   - Validate: System initialized
   - Assert: `system != null`

2. **`applyForce(float fx, float fy)`** ✓
   - Test Case: Apply force (100, -500)
   - Validate: Force accumulated
   - Assert: Returns without error

3. **`clearForces()`** ✓
   - Test Case: Clear all forces
   - Validate: Forces reset
   - Assert: No active forces remain

4. **`setMaxVelocity(float max)`** ✓
   - Test Case: Set max velocity to 500
   - Validate: Max velocity capped
   - Assert: `getMaxVelocity() == 500`

5. **`applyDamping(float damping, float deltaTime)`** ✓
   - Test Case: Apply damping 0.85, deltaTime 0.016
   - Validate: Damping applied to velocity
   - Assert: Velocity reduced by damping coefficient

6. **`update(float deltaTime)`** ✓
   - Test Case: Call update with 16ms
   - Validate: Physics updated
   - Assert: Position and velocity integrated

7. **`checkCollision(PhysicsBody body1, PhysicsBody body2)`** ✓
   - Test Cases:
     - Two bodies overlapping
     - Two bodies separated
   - Validate: Collision detected or not
   - Assert: `checkCollision() == true/false`

8. **`getContactPoint(PhysicsBody body1, PhysicsBody body2)`** ✓
   - Test Case: Get contact point of colliding bodies
   - Validate: Contact point calculated
   - Assert: `contact != null`

9. **`resolveCollision(PhysicsBody body1, PhysicsBody body2)`** ✓
   - Test Case: Resolve collision between bodies
   - Validate: Impulse applied, bodies separating
   - Assert: No more collision after resolution

10. **`applyGravity(PhysicsBody body, float deltaTime)`** ✓
    - Test Case: Apply gravity to body
    - Validate: Velocity Y increased (downward)
    - Assert: `body.velocityY` increases

---

## PART 4: GAME STATE & MANAGEMENT TESTS

### 4.1 GAME STATE
**File**: `controllers/GameState.java`
**Purpose**: Player and level statistics

#### Methods to Test:
1. **`takeDamage(int damageAmount)`** ✓
   - Test Cases:
     - Take 10 damage from 100 health → 90
     - Take 100 damage from 100 health → 0 (death)
     - Take damage when health = 0 (should not go below 0)
   - Validate: Health decreases correctly
   - Assert: `health == 90`, `health == 0`, `health >= 0`

2. **`heal(int healAmount)`** ✓
   - Test Cases:
     - Heal 20 from health 80 → 100
     - Heal 50 from health 100 (capped) → 100
   - Validate: Health increases to max
   - Assert: `health <= maxHealth`

3. **`addScore(int points)`** ✓
   - Test Case: Add 100 points
   - Validate: Score increased
   - Assert: `score += 100`

4. **`addEnemyKill(int basePoints)`** ✓
   - Test Case: Kill enemy for 50 points
   - Validate: Score and kill count updated
   - Assert: `enemiesDefeated++`, `score += basePoints`

5. **`isPlayerAlive()`** ✓
   - Test Cases:
     - health = 50 → alive
     - health = 0 → not alive
   - Validate: Correct alive state
   - Assert: `isPlayerAlive() == true/false`

6. **`getHealthPercent()` / `healthPercent()`** ✓
   - Test Case: health = 50, maxHealth = 100
   - Validate: Returns 50
   - Assert: `healthPercent() == 50`

7. **`energyPercent()`** ✓
   - Test Case: energy = 75, maxEnergy = 100
   - Validate: Returns 75
   - Assert: `energyPercent() == 75`

8. **`armorPercent()`** ✓
   - Test Case: armor = 60, maxArmor = 100
   - Validate: Returns 60
   - Assert: `armorPercent() == 60`

9. **`getHealthBarIndex()` / `getEnergyBarIndex()` / `getArmorBarIndex()`** ✓
   - Test Cases:
     - health = 100 (100%) → index 5
     - health = 0 (0%) → index 0
     - health = 50 (50%) → index 2 or 3
   - Validate: Sprite index 0-5 returned
   - Assert: `index >= 0 && index <= 5`

10. **`isCritical()`** ✓
    - Test Cases:
      - health = 100, energy = 100 → not critical
      - health = 15, energy = 100 → critical
      - health = 100, energy = 5 → critical
    - Validate: Correct critical state
    - Assert: `isCritical() == true/false`

11. **`addEffect(String effectName)` / `removeEffect(String effectName)` / `hasEffect(String effectName)`** ✓
    - Test Case: Add "poison" effect, check, remove
    - Validate: Effect tracking works
    - Assert: `hasEffect("poison") == true` → add → true → remove → false

12. **`getFormattedTime()`** ✓
    - Test Case: Get time string
    - Validate: Returns formatted time (MM:SS)
    - Assert: Format matches "MM:SS" pattern

13. **`toString()`** ✓
    - Test Case: Call toString
    - Validate: Debug string returned
    - Assert: `!toString().isEmpty()`

---

### 4.2 GAME STATE MANAGER
**File**: `managers/GameStateManager.java`
**Purpose**: Game state machine (menu, playing, paused, game over)

#### Methods to Test:
1. **`GameStateManager()` Constructor** ✓
   - Test Case: Create manager
   - Validate: Initialized to INITIALIZING state
   - Assert: `getCurrentState() == GameState.INITIALIZING`

2. **`transition(GameState newState)`** ✓
   - Test Cases:
     - INITIALIZING → MENU
     - MENU → PLAYING
     - PLAYING → PAUSED
     - PAUSED → PLAYING
     - PLAYING → GAME_OVER
   - Validate: State transitions correctly
   - Assert: `getCurrentState() == newState`

3. **`revert()`** ✓
   - Test Case: Transition twice, then revert
   - Validate: Returns to previous state
   - Assert: `getCurrentState()` returns to previous

4. **`getCurrentState()`** ✓
   - Test Case: After various transitions
   - Validate: Returns current state
   - Assert: `getCurrentState() != null`

5. **`getPreviousState()`** ✓
   - Test Case: After transition
   - Validate: Returns previous state
   - Assert: `getPreviousState() != new state`

6. **`update(long deltaMillis)`** ✓
   - Test Case: Update with 16ms
   - Validate: State elapsed time updated
   - Assert: `getStateElapsedTime() increases`

7. **`getStateElapsed()`** ✓
   - Test Case: After multiple updates
   - Validate: Returns time in seconds
   - Assert: `getStateElapsed() >= 0`

---

### 4.3 CAMERA MANAGER
**File**: `managers/CameraManager.java`
**Purpose**: Camera viewport and following

#### Methods to Test:
1. **`CameraManager()` Constructor** ✓
   - Test Case: Create camera manager
   - Validate: Instantiated
   - Assert: `camera != null`

2. **`initialize()`** ✓
   - Test Case: Initialize camera
   - Validate: Camera system ready
   - Assert: `isInitialized == true`

3. **`setPosition(float x, float y)`** ✓
   - Test Case: Set camera position to (500, 400)
   - Validate: Position set
   - Assert: `getPosition() == (500, 400)`

4. **`getPosition()`** ✓
   - Test Case: After setPosition
   - Validate: Returns current position
   - Assert: `position != null`

5. **`setZoom(float zoom)`** ✓
   - Test Cases:
     - setZoom(1.0) - normal
     - setZoom(2.0) - 2x zoom
     - setZoom(0.5) - 0.5x zoom
   - Validate: Zoom set
   - Assert: `getZoom() == zoom`

6. **`getZoom()`** ✓
   - Test Case: After setZoom
   - Validate: Returns zoom value
   - Assert: `zoom >= 0.1 && zoom <= 5.0`

7. **`pan(float deltaX, float deltaY, float speed)`** ✓
   - Test Case: Pan by (50, 50) with speed 0.1
   - Validate: Camera pans smoothly
   - Assert: Position changes

8. **`follow(float targetX, float targetY)`** ✓
   - Test Case: Follow target at (600, 500)
   - Validate: Camera centers on target
   - Assert: Position approximately equals target

9. **`getViewport()`** ✓
   - Test Case: Get viewport bounds
   - Validate: Returns [x, y, width, height]
   - Assert: `viewport.length == 4`

10. **`shutdown()`** ✓
    - Test Case: Shutdown camera
    - Validate: System properly closed
    - Assert: No null pointer exceptions on next init

---

### 4.4 AUDIO MANAGER
**File**: `managers/AudioManager.java`
**Purpose**: Sound effects and music control

#### Methods to Test:
1. **`AudioManager()` Constructor** ✓
   - Test Case: Create audio manager
   - Validate: Created
   - Assert: `manager != null`

2. **`initialize()`** ✓
   - Test Case: Initialize audio system
   - Validate: Audio system ready
   - Assert: `isInitialized == true`

3. **`playSoundEffect(String soundName)`** ✓
   - Test Cases:
     - Play "jump"
     - Play "attack"
     - Play "hit"
   - Validate: Sound plays or queued
   - Assert: No exceptions thrown

4. **`playSoundEffect(String soundName, float x, float y)`** ✓
   - Test Case: Play "explosion" at (100, 200)
   - Validate: Spatial audio plays
   - Assert: No exceptions thrown

5. **`playMusic(String musicName, boolean loop)`** ✓
   - Test Cases:
     - Play "level1_music" with loop=true
     - Play "boss_music" with loop=false
   - Validate: Music starts
   - Assert: No exceptions thrown

6. **`stopAllSounds()`** ✓
   - Test Case: Stop all sounds
   - Validate: All audio stopped
   - Assert: No exceptions thrown

7. **`setMasterVolume(float volume)`** ✓
   - Test Cases:
     - setMasterVolume(1.0) - full
     - setMasterVolume(0.5) - half
     - setMasterVolume(0.0) - silent
   - Validate: Volume set
   - Assert: `getMasterVolume() == volume`

8. **`setSFXVolume(float volume)` / `setMusicVolume(float volume)`** ✓
   - Test Case: Set SFX and music volumes
   - Validate: Volumes set independently
   - Assert: Volumes in range [0.0, 1.0]

9. **`setAudioEnabled(boolean enabled)`** ✓
   - Test Cases:
     - Enable audio
     - Disable audio
   - Validate: Audio toggle works
   - Assert: No exceptions thrown

10. **`shutdown()`** ✓
    - Test Case: Shutdown audio
    - Validate: System properly closed
    - Assert: No resources leaked

---

## PART 5: INPUT & CONTROLLER TESTS

### 5.1 INPUT HANDLER
**File**: `controllers/InputHandler.java`
**Purpose**: Keyboard input mapping and game action translation

#### Methods to Test:
1. **`isMoveLeftPressed()`** ✓
   - Test Cases:
     - No keys pressed → false
     - LEFT arrow pressed → true
     - A key pressed → true
   - Validate: Correct left movement detection
   - Assert: `isMoveLeftPressed() == true/false`

2. **`isMoveRightPressed()`** ✓
   - Test Case: RIGHT arrow or D pressed
   - Validate: Correct right movement detection
   - Assert: `isMoveRightPressed() == true/false`

3. **`isJumpPressed()`** ✓
   - Test Case: SPACE key pressed
   - Validate: Jump action detected
   - Assert: `isJumpPressed() == true`

4. **`isShootPressed()`** ✓
   - Test Case: CTRL key pressed
   - Validate: Attack action detected
   - Assert: `isShootPressed() == true`

5. **`isPausePressed()`** ✓
   - Test Case: ESC key pressed
   - Validate: Pause action detected
   - Assert: `isPausePressed() == true`

6. **`isInteractPressed()`** ✓
   - Test Case: E key pressed
   - Validate: Interact action detected
   - Assert: `isInteractPressed() == true`

7. **`isDashPressed()`** ✓
   - Test Case: SHIFT key pressed
   - Validate: Dash action detected
   - Assert: `isDashPressed() == true`

8. **`getHorizontalDirection()`** ✓
   - Test Cases:
     - No keys → 0
     - LEFT arrow → -1
     - RIGHT arrow → +1
     - Both → depends on implementation
   - Validate: Direction value correct
   - Assert: `getHorizontalDirection() in [-1, 0, 1]`

9. **`onKeyPressed(KeyEvent e)`** ✓
   - Test Case: Simulate key press event
   - Validate: Key tracked
   - Assert: `isKeyPressed()` returns true

10. **`onKeyReleased(KeyEvent e)`** ✓
    - Test Case: Simulate key release event
    - Validate: Key released from tracking
    - Assert: `isKeyPressed()` returns false

11. **`applyInputToPlayer(PlayerBase player)`** ✓
    - Test Cases:
      - Player with no input pressed
      - Player with left movement pressed
      - Player with jump pressed
    - Validate: Player state updated from input
    - Assert: Player velocity/state changes

12. **`clearAllInput()`** ✓
    - Test Case: Press multiple keys, clear
    - Validate: All keys cleared
    - Assert: `isXPressed()` all return false

13. **`setDebugMode(boolean enabled)`** ✓
    - Test Case: Enable debug mode
    - Validate: Debug output toggled
    - Assert: No exceptions thrown

14. **`playSound(String soundFilename)`** ✓
    - Test Case: Play input feedback sound
    - Validate: Sound plays
    - Assert: No exceptions thrown

---

### 5.2 HUD PANEL
**File**: `controllers/HUDPanel.java`
**Purpose**: Bottom HUD display with stats and bars

#### Methods to Test:
1. **`HUDPanel(int screenWidth, int screenHeight)` Constructor** ✓
   - Test Case: Create HUD (1200x800 screen)
   - Validate: HUD positioned at bottom
   - Assert: `panel != null`

2. **`loadAssets()`** ✓
   - Test Case: Load HUD background and dividers
   - Validate: Assets loaded
   - Assert: `panelBackground != null`, `panelDivider != null`

3. **`update(long deltaMillis, GameState gameState)`** ✓
   - Test Case: Update HUD with game state
   - Validate: HUD state synchronized
   - Assert: No exceptions thrown

4. **`render(Graphics2D g)`** ✓
   - Test Case: Draw HUD with valid Graphics2D
   - Validate: HUD drawn
   - Assert: No exceptions thrown

5. **`renderBars(Graphics2D g, GameState gameState)`** ✓
   - Test Case: Render health/energy bars
   - Validate: Bar graphics drawn
   - Assert: No exceptions thrown

6. **`renderAmmoCounter(Graphics2D g, GameState gameState)`** ✓
   - Test Case: Render ammo display
   - Validate: Ammo count drawn as digits
   - Assert: No exceptions thrown

7. **`renderStatusEffects(Graphics2D g, GameState gameState)`** ✓
   - Test Case: Render status effect icons
   - Validate: Effect icons drawn
   - Assert: No exceptions thrown

8. **`updateWithGameState(Graphics2D g, GameState gameState)`** ✓
   - Test Case: Update and render all at once
   - Validate: Full HUD update
   - Assert: No exceptions thrown

---

### 5.3 TOP BAR PANEL
**File**: `controllers/TopBarPanel.java`
**Purpose**: Top HUD display with level info and time

#### Methods to Test:
1. **`TopBarPanel(int screenWidth)` Constructor** ✓
   - Test Case: Create top bar (1200px wide)
   - Validate: Top bar positioned correctly
   - Assert: `topBar != null`

2. **`loadAssets()`** ✓
   - Test Case: Load top bar frame and background
   - Validate: Assets loaded
   - Assert: `frameTopEdge != null`

3. **`update(long deltaMillis, GameState gameState)`** ✓
   - Test Case: Update top bar with game state
   - Validate: State synchronized
   - Assert: No exceptions thrown

4. **`render(Graphics2D g)`** ✓
   - Test Case: Draw top bar
   - Validate: Bar drawn with frame and background
   - Assert: No exceptions thrown

5. **`setLevelInfo(String levelName, int levelNum, int totalLevels)`** ✓
   - Test Case: Set "Industrial Zone", 1, 2
   - Validate: Level info set
   - Assert: `levelName == "Industrial Zone"`, `levelNum == 1`

6. **`setTimeRemaining(int seconds)`** ✓
   - Test Case: Set time to 300 seconds (5 minutes)
   - Validate: Time set
   - Assert: `timeRemaining == 300`

---

## PART 6: GAME CORE & LEVEL TESTS

### 6.1 TILE MAP
**File**: `game2D/TileMap.java`
**Purpose**: Level tile grid and collision

#### Methods to Test:
1. **`TileMap()` Constructor** ✓
   - Test Case: Create tilemap
   - Validate: Tilemap initialized
   - Assert: `tilemap != null`

2. **`setTile(int x, int y, Tile tile)`** ✓
   - Test Case: Set tile at (5, 5)
   - Validate: Tile placed
   - Assert: `getTile(5, 5) == tile`

3. **`getTile(int x, int y)`** ✓
   - Test Case: Get tile at (5, 5)
   - Validate: Returns tile
   - Assert: `tile != null or null`

4. **`isWalkable(int x, int y)`** ✓
   - Test Cases:
     - Empty tile → walkable
     - Wall tile → not walkable
   - Validate: Walkability correct
   - Assert: `isWalkable() == true/false`

5. **`getWidth()` / `getHeight()`** ✓
   - Test Case: Get tilemap dimensions
   - Validate: Returns dimensions
   - Assert: `width > 0`, `height > 0`

---

### 6.2 LEVEL 1
**File**: `entities/Level1.java`
**Purpose**: Level 1 initialization and setup

#### Methods to Test:
1. **`initialize(TileMap tileMap)`** ✓
   - Test Case: Initialize level 1 with tilemap
   - Validate: Level tiles, enemies, hazards loaded
   - Assert: No exceptions thrown

2. **`getSpawnPoint()`** ✓
   - Test Case: Get player spawn location
   - Validate: Returns spawn position
   - Assert: `spawn != null`

3. **`getEnemies()`** ✓
   - Test Case: Get all enemies in level
   - Validate: Returns enemy list
   - Assert: `enemies.size() > 0`

4. **`getHazards()`** ✓
   - Test Case: Get all hazards
   - Validate: Returns hazard list
   - Assert: `hazards.size() > 0`

---

### 6.3 LEVEL 2
**File**: `entities/Level2.java`
**Purpose**: Level 2 initialization and setup

#### Methods to Test (Same as Level 1):
1. **`initialize(TileMap tileMap)`** ✓
2. **`getSpawnPoint()`** ✓
3. **`getEnemies()`** ✓
4. **`getHazards()`** ✓

---

## PART 7: ANIMATION & RENDERING TESTS

### 7.1 ANIMATION
**File**: `game2D/Animation.java`
**Purpose**: Frame-based animation system

#### Methods to Test:
1. **`Animation(BufferedImage[] frames, int frameDelay)` Constructor** ✓
   - Test Case: Create animation with 6 frames, 100ms delay
   - Validate: Animation initialized
   - Assert: `animation != null`, `getFrameCount() == 6`

2. **`update(long deltaTime)`** ✓
   - Test Case: Update with 16ms frames
   - Validate: Frame index advances
   - Assert: After 100ms+, frame index increments

3. **`getFrame()`** ✓
   - Test Case: Get current frame
   - Validate: Returns current frame image
   - Assert: `frame != null`

4. **`setFrame(int index)`** ✓
   - Test Case: Set frame to 3
   - Validate: Frame set
   - Assert: `getFrame() == frames[3]`

5. **`reset()`** ✓
   - Test Case: Reset animation
   - Validate: Frame index reset to 0
   - Assert: `currentFrame == 0`

6. **`isFinished()`** ✓
   - Test Cases:
     - First frame → false
     - Last frame → true
   - Validate: Correct end detection
   - Assert: `isFinished() == true/false`

---

### 7.2 SPRITE
**File**: `game2D/Sprite.java`
**Purpose**: Renderable sprite with position and animation

#### Methods to Test:
1. **`Sprite(String name, float x, float y)` Constructor** ✓
   - Test Case: Create sprite at (100, 200)
   - Validate: Sprite initialized
   - Assert: `getX() == 100`, `getY() == 200`

2. **`setImage(BufferedImage image)`** ✓
   - Test Case: Set sprite image
   - Validate: Image stored
   - Assert: `getImage() == image`

3. **`getX()` / `getY()`** ✓
   - Test Case: Get position
   - Validate: Position returned
   - Assert: `x >= 0`, `y >= 0`

4. **`setPosition(float x, float y)`** ✓
   - Test Case: Move to (300, 400)
   - Validate: Position changed
   - Assert: `getX() == 300`, `getY() == 400`

5. **`setAnimation(Animation anim)`** ✓
   - Test Case: Set animation
   - Validate: Animation set
   - Assert: `animation == anim`

6. **`update(long deltaTime)`** ✓
   - Test Case: Update sprite
   - Validate: Animation updated
   - Assert: No exceptions thrown

7. **`render(Graphics2D g, int offsetX, int offsetY)`** ✓
   - Test Case: Draw sprite with offsets
   - Validate: Sprite drawn
   - Assert: No exceptions thrown

---

## PART 8: GAME INTEGRATION TESTS

### 8.1 GAME CLASS
**File**: `src/Game.java`
**Purpose**: Main game orchestration

#### Methods to Test:
1. **`Game()` Constructor** ✓
   - Test Case: Create game instance
   - Validate: Game initialized with all systems
   - Assert: `game != null`, player spawned, levels loaded

2. **`update(long deltaTime)`** ✓
   - Test Case: Call update with 16ms
   - Validate: All game phases execute
   - Assert: Player, enemies, projectiles updated

3. **`draw(Graphics2D g)`** ✓
   - Test Case: Render frame
   - Validate: All layers drawn
   - Assert: Tiles, entities, UI rendered

4. **`main(String[] args)`** ✓
   - Test Case: Start game
   - Validate: Game window opens
   - Assert: No startup exceptions

---

## PART 9: SYSTEM INTEGRATION TESTS

### 9.1 PLAYER & PHYSICS INTEGRATION
**Test Scenario**: Player gravity, jumping, collision

#### Test Cases:
1. **Player Falling** ✓
   - Setup: Player at (100, 100) with velocityY = 0
   - Action: Call update 100 times (1.6 seconds)
   - Validate: Player falls due to gravity
   - Assert: `playerY > 100`, `velocityY > 0`

2. **Player Jump** ✓
   - Setup: Player on ground, call jump()
   - Action: Call updates until landing
   - Validate: Player rises then falls
   - Assert: `playerY` peaks then returns

3. **Player Ground Collision** ✓
   - Setup: Player falling from (100, 500)
   - Action: Update until y > 700
   - Validate: Player stops at ground level
   - Assert: `isGrounded == true`, `velocityY == 0`

---

### 9.2 PLAYER & ENEMY COMBAT
**Test Scenario**: Damage, projectiles, collisions

#### Test Cases:
1. **Enemy Takes Damage** ✓
   - Setup: Enemy with 100 health at (200, 300)
   - Action: Call takeDamage(25)
   - Validate: Health decreases
   - Assert: `enemy.getHealth() == 75`

2. **Enemy Dies** ✓
   - Setup: Enemy with 10 health
   - Action: Call takeDamage(25)
   - Validate: Enemy marked for removal
   - Assert: `enemy.isAlive() == false`

3. **Projectile Hits Enemy** ✓
   - Setup: Projectile at (100, 100), enemy at (110, 100)
   - Action: Update, check collision
   - Validate: Collision detected
   - Assert: Enemy takes damage, projectile killed

---

### 9.3 LEVEL PROGRESSION
**Test Scenario**: Level transitions, checkpoints

#### Test Cases:
1. **Level 1 to Level 2** ✓
   - Setup: Player in level 1
   - Action: Reach exit, transition to level 2
   - Validate: Level changes, enemies respawn
   - Assert: `currentLevel == 2`, new enemies loaded

2. **Enemy Spawn** ✓
   - Setup: Level initialized
   - Action: Check enemy list
   - Validate: Enemies spawned at correct positions
   - Assert: `enemies.size() > 0`

---

### 9.4 INPUT & PLAYER CONTROL
**Test Scenario**: Keyboard input affects player

#### Test Cases:
1. **Move Right** ✓
   - Setup: Player at (100, 100)
   - Action: Simulate RIGHT arrow press, update(100ms)
   - Validate: Player moves right
   - Assert: `playerX > 100`

2. **Move Left** ✓
   - Setup: Player at (100, 100)
   - Action: Simulate LEFT arrow press, update(100ms)
   - Validate: Player moves left
   - Assert: `playerX < 100`

3. **Jump** ✓
   - Setup: Player on ground
   - Action: Simulate SPACE press
   - Validate: Player jumps
   - Assert: `velocityY < 0` (upward)

4. **Attack** ✓
   - Setup: Player with ammo
   - Action: Simulate CTRL press
   - Validate: Attack triggered
   - Assert: Projectile created or attack animation plays

---

### 9.5 UI STATE TRACKING
**Test Scenario**: Game state affects UI

#### Test Cases:
1. **Health Bar Updates** ✓
   - Setup: Player health 100
   - Action: Take 50 damage
   - Validate: Health bar changed
   - Assert: `healthBarIndex` updated

2. **Score Display** ✓
   - Setup: Score = 0
   - Action: Kill 3 enemies (50 pts each)
   - Validate: Score increased
   - Assert: `score == 150`

3. **Time Display** ✓
   - Setup: Time remaining = 300 seconds
   - Action: Wait 60 seconds
   - Validate: Timer decremented
   - Assert: `timeRemaining < 300`

---

## PART 10: PERFORMANCE & STRESS TESTS

### 10.1 PHYSICS STRESS TEST
**Test**: Many entities with collisions

#### Test Case:
1. **100 Entities Collision Check** ✓
   - Create: 100 registered bounding boxes
   - Action: Check all collisions (O(n²))
   - Validate: Completes in reasonable time
   - Assert: Execution time < 1 second

---

### 10.2 RENDERING STRESS TEST
**Test**: Many entities rendering

#### Test Case:
1. **Render 50 Enemies** ✓
   - Create: 50 enemies on screen
   - Action: Call render() for each
   - Validate: Frame rate maintained
   - Assert: FPS > 30

---

## PART 11: EDGE CASES & ERROR HANDLING

### 11.1 BOUNDARY CONDITIONS

#### Test Cases:
1. **Player Out of Bounds** ✓
   - Place player at negative X
   - Validate: Clamped or respawned
   - Assert: `playerX >= 0` (or handled properly)

2. **Screen Resize** ✓
   - Resize game window
   - Validate: UI scales and repositions
   - Assert: Panel positions adjusted

3. **Null Input Handling** ✓
   - Pass null GameObject to collision
   - Validate: Graceful handling
   - Assert: No null pointer exception

---

### 11.2 EXTREME VALUES

#### Test Cases:
1. **Negative Velocity** ✓
   - Apply negative velocity to all axes
   - Validate: Handled correctly
   - Assert: Physics still valid

2. **Maximum Velocity Clamping** ✓
   - Apply very large velocity (10000)
   - Validate: Clamped to max
   - Assert: `velocity <= MAX_VELOCITY`

3. **Zero Delta Time** ✓
   - Call update with deltaTime = 0
   - Validate: No division by zero
   - Assert: No exceptions thrown

---

## PART 12: TEST EXECUTION ORDER & DEPENDENCIES

### Test Execution Priority:

**Phase 1 - Foundation (Core Classes)**
- GameCore tests
- GameLoopManager tests
- PhysicsUpdateSystem tests

**Phase 2 - Physics & Collision**
- CollisionDetector tests
- PhysicsSystem tests
- Projectile tests

**Phase 3 - Entities**
- PlayerBase tests
- Enemy tests
- GameState tests

**Phase 4 - Input & Control**
- InputHandler tests
- Camera tests

**Phase 5 - UI & Display**
- HUDPanel tests
- TopBarPanel tests
- Sprite tests
- Animation tests

**Phase 6 - Management Systems**
- GameStateManager tests
- AudioManager tests
- LevelManager tests

**Phase 7 - Levels**
- TileMap tests
- Level1 tests
- Level2 tests

**Phase 8 - Integration Tests**
- Player + Physics integration
- Combat system integration
- Level progression

**Phase 9 - Stress & Performance**
- Physics stress tests
- Rendering stress tests

**Phase 10 - Edge Cases**
- Boundary conditions
- Extreme values

---

## SUMMARY

**Total Test Methods**: ~250+
**Organized By**: 12 test categories + integration tests
**Coverage Areas**:
- ✓ 50+ core classes
- ✓ 150+ public methods
- ✓ Physics calculations
- ✓ Collision detection
- ✓ Entity lifecycle
- ✓ Input handling
- ✓ UI rendering
- ✓ Game state management
- ✓ Audio system
- ✓ Level progression
- ✓ Integration scenarios
- ✓ Edge cases and error handling

**Implementation Strategy**:
1. Create nested test classes for each package
2. Use helper methods to set up common test data
3. Use assertions to validate each test
4. Organize with clear naming: `test_<class>_<method>_<scenario>`
5. Document each test with inline comments
6. Use @Test annotations for clarity

---

**END OF COMPREHENSIVE TEST PLAN**
