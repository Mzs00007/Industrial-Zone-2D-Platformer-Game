# CSCU9N6 — Industrial Zone Platformer: Complete Implementation Plan
*Full codebase analysis — 1,174 real assets, ~500 Java files, 9 packages*

---

## HOW TO USE THIS DOCUMENT

This plan is ordered so you can follow it **top-to-bottom** and always have a working state at each step:

1. **Read Section 1** to understand how every file fits together before touching anything.
2. **Read Section 3** to know which files compile first — compiling in the wrong order gives confusing errors.
3. **Execute Section 4 fixes first** — all 10 bugs listed there are *compilation blockers*. Nothing runs until they are fixed.
4. **Follow Section 5 upgrades in order** — each upgrade is written as numbered steps with before/after code. Test after each step.
5. **Use Section 6** to check which assignment requirements are satisfied and how a marker will see them.
6. **Use Section 9** as your daily task queue — tasks are ordered by dependency, with time estimates and a test-to-verify for each.

> **Working directory for ALL commands:** `handout/`
> **DO NOT** compile from the top-level workspace folder or from within a package subfolder.

---

## TABLE OF CONTENTS
1. [Architecture Overview](#1-architecture-overview)
   - 1.1 Package dependency tree
   - 1.2 Data flow: keypress → player movement
   - 1.3 Data flow: enemy AI tick
   - 1.4 Threading model
   - 1.5 Class hierarchy for each entity family
2. [What Already Works](#2-what-already-works)
   - 2.1 game2D engine — full API
   - 2.2 Animation package — 4 rewritten files
   - 2.3 InteractiveGameTester — 7-tab visual tester
   - 2.4 What is NOT yet done
3. [Compilation Dependency Order](#3-compilation-dependency-order)
   - Layer 0 through Layer 8, with WHY and example errors
4. [Exact Bugs to Fix — Package by Package](#4-exact-bugs-to-fix)
   - Bugs 4.1–4.10: Root cause, exact location, before/after diff, what it unblocks
5. [Upgrade Plan — File by File](#5-upgrade-plan)
   - Upgrades 5.1–5.16: Numbered steps, complete code, how to test
6. [Assignment Requirements Coverage](#6-assignment-requirements-coverage)
   - Requirement → file+method+line, what marker sees on screen
7. [Full Asset Inventory Used](#7-full-asset-inventory-used)
8. [Build & Run Commands](#8-build--run-commands)
   - Compilation, run, troubleshooting guide
9. [Implementation Priority Queue](#9-implementation-priority-queue)
   - Phases 1–4 with time estimates and verification tests
- [Appendix A: Code Bridges](#appendix-a-code-bridges)
- [Appendix B: EchoFilter — Full Implementation](#appendix-b-echofilter-full-implementation)
- [Appendix C: InteractiveGameTester Overview](#appendix-c-interactivegametester-overview)

---

## 1. ARCHITECTURE OVERVIEW

The game is a 2D side-scrolling platformer built in Java Swing. It inherits from the
**game2D engine** (7 classes that must NOT be modified). Everything else inherits from it.

```
┌─────────────────────────────────────────────────────────┐
│                  game2D ENGINE (LOCKED)                  │
│  GameCore ← JFrame   Animation   TileMap   Tile         │
│  Sprite              Sound       Velocity               │
└───────────────────────────┬─────────────────────────────┘
                            │ extends / uses
            ┌───────────────┼───────────────┐
            ▼               ▼               ▼
     important/        animation/       utilities/
   Config (paths)    4 fixed loaders   SoundEffect
   CharacterAssets   ParallaxSystem    MidiTuner
   AudioAssets       AnimStateMachine  AudioLibrary
   TileAssets                          MusicPlayer
            │               │               │
            └───────────────┼───────────────┘
                            │ used by
            ┌───────────────┼───────────────┐
            ▼               ▼               ▼
        physics/         entities/        managers/
  CollisionDetector  PlayerBase←Sprite  AudioManager
  PhysicsSystem      Enemies←Sprite     GameLoop
  TileMapSystem      Level1+Level2←TileMap  CameraManager
                     Projectile         LevelManager
            │               │               │
            └───────────────┼───────────────┘
                            │
                    ┌───────┴───────┐
                    ▼               ▼
               ai/             controllers/
            EnemyAI          GameplayScreen
            AIPathfinder     InputHandler
            AIBehavior       GUIManager
                    │               │
                    └───────┬───────┘
                            ▼
                        Game.java
                  (extends GameCore — entry point)
```

### 1.1 Package Roles

| Package | Role | Key Files | Depends On |
|---------|------|-----------|------------|
| `game2D/` | Engine — do NOT touch | `GameCore`, `Sprite`, `Animation`, `TileMap`, `Sound` | Nothing (base layer) |
| `important/` | Asset path constants & enums | `Config`, `CharacterAssets`, `AudioAssets` | Nothing (just strings) |
| `animation/` | Loaders + visual systems | `HorizontalSpritesheetLoader`, `ParallaxSystem`, `CharacterAnimationStateMachine` | `game2D`, `important` |
| `utilities/` | Audio helper classes | `SoundEffect` (real WAV), `MidiTuner`, `AudioLibrary` | `game2D.Sound` |
| `physics/` | AABB collision, spatial grid | `CollisionDetector`, `PhysicsSystem` | Nothing (pure math) |
| `entities/` | Player, enemies, levels | `PlayerBase`, `Enemies`, `Level1`, `Level2`, `Projectile` | `animation`, `physics`, `important`, `game2D` |
| `managers/` | Game systems managers | `AudioManager`, `GameLoop`, `CameraManager`, `LevelManager`, `ScoreManager` | `utilities`, `entities` |
| `ai/` | Enemy decision making | `EnemyAI`, `AI` (inner classes), `AIPathfinder` | `physics` |
| `controllers/` | UI screens + input | `InputHandler`, `GameplayScreen`, `GUIManager` | `entities`, `managers`, `ai` |
| `Game.java` | Entry point | Extends `GameCore`, orchestrates all systems | Everything |

---

### 1.2 Data Flow: Keypress → Player Movement

This is one of the most important flows to understand. A bug **anywhere** in this chain means the player cannot move at all.

```
 ① OS sends keydown event
        │
        ▼
 ② Java AWT dispatches to JFrame
    (game2D.GameCore extends JFrame, implements KeyListener)
        │
        ▼
 ③ GameCore.keyPressed(KeyEvent e) — abstract method
    → Your Game.java MUST override this
        │  Game.java override:
        │  public void keyPressed(KeyEvent e) {
        │      PlayerBase.setKeyPressed(e.getKeyCode(), true);
        │  }
        ▼
 ④ PlayerBase.setKeyPressed(keyCode, true)
    → adds keyCode to static Set<Integer> keysDown
        │
        ▼
 ⑤ Game.update(long elapsed) calls player.update(elapsed)
        │
        ▼
 ⑥ PlayerBase.update(elapsed):
    isLeft  = keysDown.contains(VK_A) || keysDown.contains(VK_LEFT)
    isRight = keysDown.contains(VK_D) || keysDown.contains(VK_RIGHT)
    isJump  = keysDown.contains(VK_SPACE) && isGrounded
    isSprint = keysDown.contains(VK_SHIFT)
        │
        ▼
 ⑦ PlayerBase applies velocity:
    if (isRight) vx = isSprint ? RUN : WALK;   // 240 or 160 px/s
    if (isLeft)  vx = -(isSprint ? RUN : WALK);
    if (isJump)  { vy = JUMP_VELOCITY; isGrounded = false; }  // -420 px/s
        │
        ▼
 ⑧ PlayerBase.updateAnimState() selects AnimState enum value
    → AnimationPlayer.play(newState) if state changed
        │
        ▼
 ⑨ Game.draw() calls player.draw(g, cameraX, cameraY)
    → renders the current animation frame at (x - cameraX, y - cameraY)
```

> **Current bug:** Step ③ is broken — `Game.java` does NOT override `keyPressed()`. Fix: see Bug 4.7.

---

### 1.3 Data Flow: Enemy AI Tick

```
 ① Game.update(elapsed) iterates enemy list
        │
        ▼
 ② EnemyAI.executeBehavior(dt) — switch on currentState
        │
        │ ┌── IDLE: timer++, transition to PATROL after N ms
        │ ├── PATROL: move along AI.AIPathfinder waypoints
        │ │     AI.AIManager.seek(target) → returns direction vector
        │ │     move at patrolSpeed, flip direction at waypoint
        │ ├── ALERT: detected player — play alert anim, enter CHASE
        │ ├── CHASE: AI.AIManager.seek(playerPos) → chase at chaseSpeed
        │ │     if within attackRange → ATTACK
        │ │     if health low → FLEE
        │ ├── ATTACK: trigger attack animation frame, deal damage, start cooldown
        │ ├── FLEE: AI.AIManager.flee(playerPos) → run away
        │ └── DEAD: play death animation, spawn smoke VFX, remove from list
        │
        ▼
 ③ EnemyFactory.EnemyInstance.x, y updated by AI
        │
        ▼
 ④ Game.draw() calls Enemies.renderEnemy(g, inst, cameraX)
    → draws current animation frame with camera offset and culling
```

---

### 1.4 Threading Model

Java game loops and Swing's Event Dispatch Thread (EDT) are **separate threads**. Understanding this prevents race conditions.

```
┌──────────────────────────────────────────────────────────────┐
│  Thread Name             │ What it does                      │
├──────────────────────────┼───────────────────────────────────┤
│  Thread-0 (game loop)    │ game2D.GameCore.gameLoop()        │
│                          │ Calls update(elapsed) + draw(g)   │
│                          │ ~60 times per second               │
├──────────────────────────┼───────────────────────────────────┤
│  AWT-EventQueue-0 (EDT)  │ Swing repaints, mouse events,     │
│                          │ key events from OS                 │
├──────────────────────────┼───────────────────────────────────┤
│  Thread-N (Sound)        │ game2D.Sound extends Thread       │
│                          │ One thread per WAV file played    │
│                          │ Fire-and-forget, auto-terminates   │
├──────────────────────────┼───────────────────────────────────┤
│  Thread-M (MidiTuner)    │ javax.sound.midi.Sequencer        │
│                          │ Runs internally via MIDI API       │
│                          │ Controlled by play()/stop()/pause()│
├──────────────────────────┼───────────────────────────────────┤
│  asset-loader Thread     │ InteractiveGameTester.loadAll()   │
│                          │ Loads images on background thread  │
│                          │ Calls SwingUtilities.invokeLater   │
└──────────────────────────┴───────────────────────────────────┘
```

**Key rule:** All Swing UI mutations (setting label text, changing button color) must happen on the EDT via `SwingUtilities.invokeLater(() -> { ... })`. The game loop thread should **never** call Swing UI methods directly.

---

### 1.5 Class Hierarchy for Each Entity Family

**Player hierarchy:**
```
java.lang.Object
  └── game2D.Sprite           (position, velocity, scale, rotation, Animation reference)
        └── entities.PlayerBase   (AnimState enum, physics constants, keysDown Set,
                                   loadSprites(), update(), draw())
              └── (no further subclasses — PlayerBase IS the player)
```

**Enemy hierarchy:**
```
java.lang.Object
  └── ai.AI.AIAgent            (abstract: x, y, vx, vy, currentState, executeBehavior())
        └── ai.EnemyAI          (concrete FSM: IDLE/PATROL/ALERT/CHASE/ATTACK/FLEE/DEAD)
```
*Note: `entities.Enemies` is a static utility class (not in the inheritance chain). It provides `loadEnemySprites()` and the `EnemyFactory` inner class that creates `EnemyInstance` objects.*

**Level hierarchy:**
```
java.lang.Object
  └── game2D.TileMap            (loadMap, draw, getTileAt)
        └── (used BY Level1/Level2, not extended — composition pattern)

entities.Level1  ← standalone class, HAS-A TileMap
entities.Level2  ← standalone class, HAS-A TileMap
```

**Audio hierarchy:**
```
java.lang.Object
  └── javax.sound.sampled.Clip   (used inside SoundEffect)
        (SoundEffect wraps Clip for volume/pan/loop control)

java.lang.Thread
  └── game2D.Sound               (fire-and-forget WAV thread)

utilities.MidiTuner              (wraps javax.sound.midi.Sequencer)
utilities.AudioLibrary           (Map<String, SoundEffect> registry)
managers.AudioManager            (facade: wires SFX + MIDI + EchoFilter)
```

---

---

## 2. WHAT ALREADY WORKS

### 2.1 game2D Engine — Full Public API (do NOT modify these files)

**`game2D.GameCore`** — abstract JFrame, drives the entire game loop
| Method | Signature | What it does |
|--------|-----------|--------------|
| `run` | `run(boolean full, int w, int h)` | Sets full-screen or windowed size, calls `init()` then `gameLoop()` |
| `gameLoop` | `protected void gameLoop()` | Creates double-buffer, calls `update(elapsed)` + `draw(g)` ~60fps |
| `stop` | `public void stop()` | Sets internal stop flag, loop exits gracefully |
| `loadImage` | `public BufferedImage loadImage(String path)` | Reads PNG/JPG, returns null if path bad |
| `update` | `abstract void update(long elapsed)` | **Override in Game.java** — game logic tick |
| `draw` | `abstract void draw(Graphics2D g)` | **Override in Game.java** — rendering |
| `keyPressed` | `abstract void keyPressed(KeyEvent e)` | **Override in Game.java** — forward to PlayerBase |
| `keyReleased` | `abstract void keyReleased(KeyEvent e)` | **Override in Game.java** — forward to PlayerBase |
| `keyTyped` | `abstract void keyTyped(KeyEvent e)` | Stub — override with empty body |

**`game2D.Sprite`** — base class for any positioned, animated game object
| Field/Method | Type | Meaning |
|---|---|---|
| `x, y` | `float` | World position in pixels |
| `dx, dy` | `float` | Velocity in pixels per second |
| `scale` | `float` | Render scale (1.0 = natural size) |
| `rotation` | `float` | Rotation in radians |
| `animation` | `Animation` | Current animation (set via `setAnimation`) |
| `update(elapsed)` | `void` | `x += dx * (elapsed/1000f)`, `y += dy * (elapsed/1000f)` |
| `draw(g, xoff, yoff)` | `void` | Draws current animation frame at `(x - xoff, y - yoff)` with scale + rotation transforms |
| `getX(), getY()` | `float` | Getters for position |
| `setX(x), setY(y)` | `void` | Position setters |
| `setVelocity(dx, dy)` | `void` | Set velocity |
| `setAnimation(anim)` | `void` | Swap active animation |

**`game2D.Animation`** — frame-based animation with timing control
| Method | Signature | What it does |
|--------|-----------|--------------|
| `addFrame` | `addFrame(Image img, long duration)` | Appends a frame (duration in milliseconds) |
| `update` | `update(long elapsed)` | Advances elapsed time counter, flips frame |
| `getImage` | `getImage()` | Returns the current frame's `Image` |
| `setLoop` | `setLoop(boolean)` | Whether to restart after last frame |
| `setAnimating` | `setAnimating(boolean)` | Pause/resume the animation |
| `setSpeed` | `setSpeed(float)` | Playback speed multiplier (0.5 = half speed) |
| `isFinished` | `isFinished()` | True if non-looping animation has reached last frame |
| `reset` | `reset()` | Jump back to frame 0 |

**`game2D.TileMap`** — loads and renders a tile-based level map
| Method | Signature | What it does |
|--------|-----------|--------------|
| `loadMap` | `loadMap(String folder, String mapfile)` | Reads `map.txt` — first line is `cols rows tileW tileH` then tile definitions then `#map` then ASCII grid |
| `draw` | `draw(Graphics2D g, int xoff, int yoff)` | Draws all visible tiles offset by camera position |
| `getTileAt` | `getTileAt(int col, int row)` | Returns `Tile` at grid position, or null |
| `getWidth` | `getWidth()` | Map width in pixels (`cols * tileW`) |
| `getHeight` | `getHeight()` | Map height in pixels (`rows * tileH`) |

**`game2D.Sound`** — fire-and-forget WAV playback
| Method | What it does |
|--------|--------------|
| `Sound(String path)` | Constructor — stores path |
| `start()` | Inherited from Thread — begins playback on a new thread |
| `run()` | Opens AudioInputStream, plays via Clip, auto-closes |

> **Important:** `Sound` has no pause/stop/loop controls. For those, use `utilities.SoundEffect` with a `javax.sound.sampled.Clip` directly.

---

### 2.2 Animation Package — 4 Fully Rewritten Files

These 4 files were rewritten from scratch as proper standalone Java classes. They compile and work correctly.

**`animation.HorizontalSpritesheetLoader`** — loads a single horizontal PNG spritesheet
```
Constructor: HorizontalSpritesheetLoader(String path, int frameCount, int msPerFrame, boolean loop)
             HorizontalSpritesheetLoader(String path)   ← parses _NFrames_, _Xms_ from filename

getFrame(int i)    → BufferedImage   — frame i (0-indexed)
getFrameCount()    → int
getMsPerFrame()    → int
isLoop()           → boolean
toGameAnimation()  → game2D.Animation  — converts all frames to Animation object
```

**`animation.AnimationPlayer`** — drives playback with accumulator-based timing
```
Constructor: AnimationPlayer()

play(String stateName)     — start playing named animation (no interrupt if already playing)
forcePlay(String stateName) — interrupt current and start new one immediately
update(long elapsed)        — advance accumulator, flip frame when time hits msPerFrame
getCurrentFrame()           → BufferedImage — the currently visible frame
isFinished()                → boolean — true if non-loop anim hit last frame
registerAnimation(String name, HorizontalSpritesheetLoader hsl)
```

**`animation.AnimationConfig`** — plain data class for animation settings
```
Fields: String name, int frameCount, int msPerFrame, boolean loop, boolean pingPong
Constructor: AnimationConfig(String name, int frameCount, int msPerFrame, boolean loop)

getName()          → String
getFrameCount()    → int
getMsPerFrame()    → int
isLoop()           → boolean
isPingPong()       → boolean
```

**`animation.SequenceFrameAnimationLoader`** — assembles PNG sequences into one sheet
```
Constructor: SequenceFrameAnimationLoader(String directory)

load()             — scans directory, sorts PNGs, arranges into horizontal sheet in memory
getSheet()         → BufferedImage — the stitched sheet
getFrameCount()    → int — number of PNGs found
getFrameWidth()    → int — width of each individual frame
getFrameHeight()   → int — height of each frame
```

---

### 2.3 InteractiveGameTester — 7-Tab Visual Tester

`tests/InteractiveGameTester.java` is the fully working visual test harness. Run it from `handout/`:

```batch
javac -encoding UTF-8 -cp . -d bin -sourcepath src src/tests/InteractiveGameTester.java
java -cp bin tests.InteractiveGameTester
```

| Tab | Label | What you can test | Controls |
|-----|-------|-------------------|----------|
| 0 | 🌅 Parallax | 5 background layers scrolling at different speeds | Drag mouse left/right, or enable auto-scroll |
| 1 | 🎮 Player | All 3 characters (Biker, Punk, Cyborg), all 8 animation states | WASD / Space / Z X C for states, 1-2-3 to switch character |
| 2 | 🔫 Weapons | Gun equip, aim with mouse, muzzle flash VFX, bullet animation | Left-click to fire, mouse moves aim angle |
| 3 | 👾 Enemies | All drone types animated (UFO Saucer, Jet Drone...), A/D to cycle | A/D keys cycle enemies, spacebar to trigger death |
| 4 | 🧱 Tiles | Full 81-tile palette from Level 1 tileset, hover for tile name | Mouse over tiles to see names |
| 5 | 💥 VFX | Smoke, sparks, particle systems | Left-click to spawn smoke at cursor, right-click for sparks |
| 6 | 🔊 Sound | MIDI playback, WAV SFX trigger, EchoFilter demo | Buttons to play each sound |

**Architecture of the tester:**
- `InteractiveGameTester extends JFrame implements KeyListener`
- Uses a custom `GameCanvas extends JPanel` for all rendering
- `GameCanvas.tick()` called by `javax.swing.Timer` every 16ms (≈ 60fps)
- Loads all assets on a background thread to keep UI responsive

---

### 2.4 What Is NOT Yet Done (these are the remaining tasks)

| System | Current State | What's Missing |
|--------|--------------|----------------|
| Player movement | `PlayerBase` exists with full physics constants | `Game.java` does NOT call `setKeyPressed()` — player is frozen |
| Enemy AI | `EnemyAI` FSM written | Not wired into `Enemies.EnemyFactory` — enemies stand still |
| Audio | `AudioManager` written | Won't compile — 3 bugs in audio utilities (4.1–4.3) |
| Tile collision | `CollisionDetector` works | Not called in `Game.update()` — player falls through tiles |
| Parallax | `ParallaxSystem` written | Unclosed brace — won't compile (bug 4.6) |
| Level 1 | `Level1.java` written | Import chain broken by bug 4.5 (AnimationAndSpriteLoader) |
| Level 2 | `Level2.java` written | Same as Level1 |
| HUD | Not started | No score/health/timer drawn yet |
| Pause menu | Not started | ESC key not wired to pause state |
| Game Over | Not started | Player death does not trigger end screen |
| EchoFilter | Not started | Required for "novel audio effect" criterion |
| maps/level_1/map.txt | Exists but empty/minimal | Needs real tile character grid |
| maps/level_2/map.txt | Exists but empty/minimal | Needs real tile character grid |

---

### ✅ Key entity structures (partially working)
- `Game.java` — good base, extends GameCore, has level 1+2 platforms, camera, draw
- `entities/PlayerBase.java` — full state machine, keyboard input, sprite loading
- `entities/Enemies.java` — enemy factory with sprite loading and AABB

---

## 3. COMPILATION DEPENDENCY ORDER

**Build these layers in order. Each layer depends on the one above. Compile out of order and you get `cannot find symbol` errors everywhere.**

The single compile command at the end resolves all dependencies automatically, but understanding the order helps when debugging a specific package.

---

### Layer 0 — game2D Engine (pre-compiled, do NOT recompile unless told to)

**Files:**
```
game2D/Animation.java     game2D/Sprite.java    game2D/Sound.java
game2D/GameCore.java      game2D/Tile.java      game2D/Velocity.java
game2D/TileMap.java
```
**WHY first:** Every other file in the project depends on at least one game2D class.
**If missing from classpath:** `error: package game2D does not exist` on the FIRST import line of almost every file.

---

### Layer 1 — Constants & Enums (no internal dependencies)

**Files:**
```
important/Config.java           → asset base path strings (static final String)
important/CharacterAssets.java  → 100+ constants for sprite paths (enum)
important/AudioAssets.java      → audio path constants
important/TileAssets.java       → tile path constants
important/VFXAssets.java        → VFX path constants
```
**WHY second:** These are pure constant classes with no imports from our packages. Everything else reads these to get file paths.
**If missing:** `error: cannot find symbol: variable Config.ASSET_BASE` (in entities, utilities, animation layers).
**Example error if compiled after utilities:**
```
utilities/AudioLibrary.java:12: error: cannot find symbol
import important.Config;
               ^
```

---

### Layer 2 — Utilities & Animation Loaders (depend on Layer 0+1)

**Files:**
```
utilities/SoundEffect.java        ← MUST be rewritten (Bug 4.1)
utilities/AudioLibrary.java       ← MUST fix SoundEffect reference (Bug 4.2)
utilities/MidiTuner.java          ← MUST add no-arg constructor (Bug 4.3)
utilities/MusicPlayer.java        ← verify compiles
utilities/VolumeController.java   ← verify compiles
utilities/AudioListener.java      ← verify compiles
utilities/AudioSystem.java        ← fix inner class reference
animation/HorizontalSpritesheetLoader.java  ✅ already correct
animation/AnimationPlayer.java              ✅ already correct
animation/AnimationConfig.java              ✅ already correct
animation/SequenceFrameAnimationLoader.java ✅ already correct
animation/ParallaxSystem.java               ← MUST fix inner class brace (Bug 4.6)
animation/ParallexLayer.java                ← extract or verify
```
**WHY third:** AudioManager (Layer 6) imports these. Entities (Layer 4) import the animation classes.
**If SoundEffect broken:**
```
utilities/AudioLibrary.java:5: error: cannot find symbol
    Map<String, SoundEffect> sounds;
                ^
```

---

### Layer 3 — Physics & Animation Utilities (depend on Layers 0-2)

**Files:**
```
physics/CollisionDetector.java        ← inner class BoundingBox, CollisionResult, SpatialGrid
physics/PhysicsSystem.java
physics/TileMapSystem.java
physics/CharacterPhysicsProfile.java
physics/PhysicsUpdateSystem.java
animation/AnimationAndSpriteLoader.java  ← MUST remove extends GameCore (Bug 4.5)
animation/CharacterAnimationStateMachine.java
managers/MathUtils.java                  ← package is managers, NOT managers.utils
```
**WHY here:** `entities/PlayerBase` imports both `physics.CollisionDetector` and `animation.AnimationAndSpriteLoader`. Both must exist first.
**If AnimationAndSpriteLoader extends GameCore:**
```
entities/Level1.java:3: error: constructor GameCore in class GameCore cannot
be applied to given types; required: no arguments; found no arguments
```
*(This is confusing — the real error is the bad extends, which breaks all constructors)*

---

### Layer 4 — Entities (depend on Layers 0-3)

**Files:**
```
entities/Projectile.java
entities/PlayerBase.java        ← ideally extend game2D.Sprite
entities/Enemies.java
entities/EnemyFactory.java
entities/Level1.java            ← fix import chain (Bug 4.9)
entities/Level2.java
entities/LevelMapLoader.java
```
**WHY here:** `ai/EnemyAI` and `managers/GameLoop` reference entity types. They must be compiled first.
**If Level1.java import chain is broken:**
```
entities/Level1.java:4: error: cannot find symbol
import animation.AnimationAndSpriteLoader;
                ^
```

---

### Layer 5 — AI (depends on Layers 0-4)

**Files:**
```
ai/AI.java          (contains inner static classes: AIAgent, AIPathfinder, AIState, AIManager, Waypoint)
ai/EnemyAI.java     (extends AI.AIAgent — verify compiles once AI.java is clean)
ai/EnemyBehavior.java
ai/AIManager.java
ai/AIPathfinder.java
```
**WHY here:** `managers/EnemyController` extends `GameEntity` and processes `EnemyAI` instances. Managers come next.
**EnemyAI dependency:**
```
ai/EnemyAI.java imports: ai.AI   → needs Layer 5 AI.java
                         physics.CollisionDetector → needs Layer 3
```

---

### Layer 6 — Managers (depend on Layers 0-5)

**Files:**
```
managers/GameEntity.java       ← base: position, velocity, health, isAlive
managers/AudioManager.java     ← MUST fix MidiTuner constructor call (Bug 4.3)
managers/GameLoop.java         ← MUST fix import path (Bug 4.4)
managers/CameraManager.java
managers/LevelManager.java
managers/ScoreManager.java
managers/StateMachine.java
managers/PlayerController.java
managers/EnemyController.java
managers/GameState.java
```
**WHY here:** Controllers (Layer 7) reference managers. Game.java (Layer 8) wires managers together.
**GameLoop bug — if wrong import:**
```
managers/GameLoop.java:7: error: package managers.utils does not exist
import managers.utils.MathUtils;
```

---

### Layer 7 — Controllers (depend on Layers 0-6)

**Files:**
```
controllers/InputHandler.java
controllers/MouseInputHandler.java
controllers/ScreenManager.java
controllers/GUIManager.java
controllers/GameplayScreen.java
controllers/MainMenuScreen.java
controllers/GameOverScreen.java
controllers/PauseScreen.java
controllers/ComprehensiveTileMapLoader.java  ← used by Level1/Level2
controllers/AnimatedObjectManager.java       ← used by Level1/Level2
```
**WHY here:** Controllers orchestrate entities + managers. They are the last step before Game.java.
**Duplicate class issue here:**
- `controllers/GameState.java` AND `managers/GameState.java` both exist.
- If any controller imports `managers.GameState` with wrong package, you get `error: class GameState is already defined in package controllers`.

---

### Layer 8 — Entry Point

**Files:**
```
Game.java
tests/InteractiveGameTester.java  (optional — standalone tester)
```
**Single compile command (from `handout/`):**
```batch
javac -encoding UTF-8 -cp . -d bin -sourcepath src src/Game.java
```
`-sourcepath src` lets javac follow imports automatically — you only specify `Game.java` and it compiles every dependency.

**Compile just the tester:**
```batch
javac -encoding UTF-8 -cp . -d bin -sourcepath src src/tests/InteractiveGameTester.java
java -cp bin tests.InteractiveGameTester
```

---

## 4. EXACT BUGS TO FIX

Fix all bugs in this section before attempting upgrades in Section 5. The table below shows which files each bug blocks.

### Quick-Reference Bug Table

| Bug # | File | Bug Type | Files Blocked Until Fixed |
|-------|------|----------|--------------------------|
| 4.1 | `utilities/SoundEffect.java` | Stub class — no audio plays | `AudioLibrary`, `AudioManager`, all audio |
| 4.2 | `utilities/AudioLibrary.java` | Wrong type `AudioSystem.SoundEffect` | `AudioManager` |
| 4.3 | `utilities/MidiTuner.java` | Missing no-arg constructor | `AudioManager` |
| 4.4 | `managers/GameLoop.java` | Wrong import path for MathUtils | All `managers/` files |
| 4.5 | `animation/AnimationAndSpriteLoader.java` | Wrongly extends GameCore | `Level1`, `Level2`, all entities |
| 4.6 | `animation/ParallaxSystem.java` | Unclosed inner class brace | `Game.java` parallax rendering |
| 4.7 | `Game.java` | Missing `keyPressed` override | Player frozen — can't move |
| 4.8 | `utilities/AudioLibrary.java` | Wrong WAV paths (missing `._` prefix) | All SFX plays silently |
| 4.9 | `entities/Level1.java` | Cascading import failure | Level 1 entire system |
| 4.10 | `controllers/` | Duplicate class names | Controllers layer compilation |

---

### 4.1 utilities/SoundEffect.java — CRITICAL: Stub Class, No Audio

**Root cause:** The original developer wrote a placeholder with fields but never implemented the `javax.sound.sampled` API. The `play()` method only flips a boolean — it never opens a Clip or audio stream.

**Exact location of problem:**
```java
// SoundEffect.java — CURRENT (broken):
public void play() {
    this.isPlaying = true;   // ← THAT IS ALL IT DOES. Nothing plays.
}
```

**Impact if left unfixed:** Every call to `sfxLibrary.getSound("explosion").play()` does nothing. All game audio events (jump, shoot, hit, death) are completely silent. The entire `AudioManager` is useless because it delegates all SFX to `SoundEffect.play()`.

**Complete rewrite:**

```java
// utilities/SoundEffect.java — COMPLETE REWRITE
package utilities;
import javax.sound.sampled.*;
import java.io.File;

public class SoundEffect {
    private String name;
    private String filePath;
    private float volume = 1.0f;   // 0.0 silent → 1.0 full
    private float pan = 0.0f;      // -1.0 left → 0.0 centre → 1.0 right
    private boolean isPlaying = false;
    private boolean looping = false;
    private Clip clip = null;

    public SoundEffect(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    public void play() {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                System.err.println("[SoundEffect] File not found: " + filePath);
                return;
            }
            AudioInputStream stream = AudioSystem.getAudioInputStream(f);
            DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            applyVolume();   // set volume + pan before start
            if (looping) clip.loop(Clip.LOOP_CONTINUOUSLY);
            else clip.start();
            isPlaying = true;
        } catch (Exception e) {
            System.err.println("[SoundEffect] Play error " + filePath + ": " + e.getMessage());
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) clip.stop();
        if (clip != null) clip.close();
        clip = null;
        isPlaying = false;
    }

    public void pause()  { if (clip != null && clip.isRunning()) clip.stop(); }
    public void resume() { if (clip != null && !clip.isRunning()) clip.start(); }

    private void applyVolume() {
        if (clip == null) return;
        // Volume control — converts linear 0–1 to decibels
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float)(Math.log10(Math.max(volume, 0.0001f)) * 20.0);
            gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), dB)));
        }
        // Stereo pan control
        if (clip.isControlSupported(FloatControl.Type.PAN)) {
            FloatControl panCtrl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
            panCtrl.setValue(Math.max(-1.0f, Math.min(1.0f, pan)));
        }
    }

    public boolean isPlaying() { return clip != null && clip.isRunning(); }
    public void setVolume(float v)    { this.volume = Math.max(0f, Math.min(1f, v)); }
    public void setPan(float p)       { this.pan = Math.max(-1f, Math.min(1f, p)); }
    public void setLooping(boolean l) { this.looping = l; }
    public String getName()     { return name; }
    public String getFilePath() { return filePath; }
    public void update(float dt) { /* clip manages itself */ }
}
```

**How to verify fix:** After rewriting, compile just this file:
```batch
javac -encoding UTF-8 -cp . -d bin src/utilities/SoundEffect.java
```
Then in `InteractiveGameTester`, the Sound tab should play real audio when buttons are clicked.

**What this unblocks:** Bug 4.2 (`AudioLibrary` referencing `SoundEffect`), Bug 4.3 (`AudioManager` using `AudioLibrary`), and ultimately all in-game audio.

---

### 4.2 utilities/AudioLibrary.java — Wrong Inner Class Reference

**Root cause:** Someone wrote `AudioSystem.SoundEffect` as the type, importing from `javax.sound.sampled.AudioSystem`. But `AudioSystem` is a factory class — it has no inner class called `SoundEffect`. The correct type is simply `utilities.SoundEffect`.

**Exact location:**
```java
// AudioLibrary.java — CURRENT (broken):
import javax.sound.sampled.AudioSystem;
...
private Map<String, AudioSystem.SoundEffect> soundEffects = new HashMap<>();
//                  ^^^^^^^^^^^^^^^^^^^^^^^^^^^ does not exist
public AudioSystem.SoundEffect getSound(String key) { ... }
```

**Fix — every occurrence:**
```java
// AudioLibrary.java — FIXED:
import utilities.SoundEffect;   // ← correct import
...
private Map<String, SoundEffect> soundEffects = new HashMap<>();
public SoundEffect getSound(String key) { return soundEffects.get(key); }
```

Also add the directory scanner method (needed to handle `._` filenames — see Bug 4.8):
```java
public void loadAllSoundsFromDirectory(String dirPath) {
    File folder = new File(dirPath);
    if (!folder.isDirectory()) {
        System.err.println("[AudioLibrary] Not a directory: " + dirPath);
        return;
    }
    File[] wavFiles = folder.listFiles(f -> f.getName().endsWith(".wav"));
    if (wavFiles == null || wavFiles.length == 0) {
        System.err.println("[AudioLibrary] No WAV files in: " + dirPath);
        return;
    }
    int count = 0;
    for (File f : wavFiles) {
        // Strip macOS ._prefix and .wav extension to create a clean key
        String key = f.getName()
            .replace("._", "")
            .replace(".wav", "")
            .toLowerCase()
            .replace(" ", "_");
        soundEffects.put(key, new SoundEffect(key, f.getAbsolutePath()));
        count++;
    }
    System.out.println("[AudioLibrary] Registered " + count + " sounds from " + dirPath);
}

public int getTotalSoundsLoaded() { return soundEffects.size(); }

public void stopAllSounds() {
    for (SoundEffect se : soundEffects.values()) se.stop();
}
```

**What this unblocks:** `AudioManager` can now construct `AudioLibrary` and call `getSound()` without a compile error.

---

### 4.3 utilities/MidiTuner.java — Missing No-Arg Constructor

**Root cause:** `MidiTuner` was written with only one constructor that requires a file path and loop count. Later, `AudioManager` was written to call `new MidiTuner()` with no arguments (perhaps assuming a default would exist). Java does not generate a no-arg constructor when you define any explicit constructor.

**Exact location of call:**
```java
// AudioManager.java line ~18:
midiPlayer = new MidiTuner();   // ← compile error: no suitable constructor
```

**Exact location of the constructor block in MidiTuner.java:**
```java
// MidiTuner.java — CURRENT (only constructor):
public MidiTuner(String audioPath, int loopCount) {
    this.audioPath = audioPath;
    this.loopCount = loopCount;
    initializeMidiSequencer();
}
// ← no no-arg constructor exists
```

**Fix — add immediately after the existing constructor:**
```java
/** No-arg constructor for AudioManager default init. Path set later via setPath(). */
public MidiTuner() {
    this.audioPath = "";
    this.loopCount = LOOP;   // LOOP = -1 constant already in file
    try {
        initializeMidiSequencer();
    } catch (Exception e) {
        System.err.println("[MidiTuner] No-arg init failed: " + e.getMessage());
        // Non-fatal — setPath() + play() will retry
    }
}

/** Set the MIDI file path before calling play(). */
public void setPath(String path) { this.audioPath = path; }
```

**What this unblocks:** `AudioManager` constructor can run. MIDI music can play in-game.

---

### 4.4 managers/GameLoop.java — Wrong Import Package for MathUtils

**Root cause:** `MathUtils.java` lives in the `managers/` folder and declares `package managers;`. But GameLoop imports from `managers.utils.MathUtils` — a sub-package that does not exist.

**Exact location:**
```java
// managers/GameLoop.java — CURRENT (broken):
import managers.utils.MathUtils;   // ← package managers.utils does not exist
```

**What the compiler says:**
```
managers/GameLoop.java:7: error: package managers.utils does not exist
import managers.utils.MathUtils;
                    ^
```

**Fix — one line change:**
```java
// AFTER:
import managers.MathUtils;
```

**What this unblocks:** The entire `managers/` package. `GameLoop` is the central coordinator (`PlayerController`, `EnemyController`, `BossController` all depend on it).

---

### 4.5 animation/AnimationAndSpriteLoader.java — Wrongly Extends GameCore

**Root cause:** `AnimationAndSpriteLoader` should be a static utility for loading sprite images. At some point, someone added `extends GameCore` — this is almost certainly a copy-paste accident from another file. `GameCore` extends `JFrame`, so every time `AnimationAndSpriteLoader` is instantiated it tries to create a window and calls the `JFrame` constructor chain. Worse, it does not implement the abstract methods `update()` and `draw()`, causing a compile error at instantiation sites.

**Exact location:**
```java
// animation/AnimationAndSpriteLoader.java — CURRENT (broken):
public class AnimationAndSpriteLoader extends GameCore {
//                                     ^^^^^^^^^^^^^^^^ WRONG
```

**What the compiler says (at Level1.java, not at the source):**
```
entities/Level1.java:12: error: AnimationAndSpriteLoader is not abstract
and does not override abstract method draw(Graphics2D) in GameCore
```
*(The error appears in the file that tries to USE AnimationAndSpriteLoader, not in AnimationAndSpriteLoader itself — this makes it confusing to diagnose.)*

**Fix:**
```java
// animation/AnimationAndSpriteLoader.java — FIXED:
public class AnimationAndSpriteLoader {
    // All methods stay identical — just remove extends GameCore
    // Remove any @Override annotations on update()/draw() if present
    // Add private constructor to prevent instantiation:
    private AnimationAndSpriteLoader() {}
```

**After the fix:** Update any internal reference to `this.loadImage(path)` (a `GameCore` method) to use `ImageIO.read(new File(path))` instead:
```java
// Replace inherited loadImage() calls:
BufferedImage img = ImageIO.read(new File(path));
if (img == null) System.err.println("[Loader] Failed: " + path);
```

**What this unblocks:** `Level1.java`, `Level2.java`, `Game.java` all import from this class. Once fixed, the entire entities layer can compile.

---

### 4.6 animation/ParallaxSystem.java — Unclosed Inner Class Brace

**Root cause:** The `ParallexLayer` inner class (note: `Parallexs` not `Parallaxi` — unusual spelling, it's correct as-is since it's used everywhere by that name) is opened but never closed. The file ends before the matching `}` for the inner class.

**Exact problem:**
```java
// animation/ParallaxSystem.java — end of file (CURRENT):
    public class ParallexLayer {
        BufferedImage img;
        float parallaxDepth;
        int layerIndex;
        float currentOffsetX = 0f;

        public ParallexLayer(BufferedImage img, float parallaxDepth, int layerIndex) {
            this.img = img;
            this.parallaxDepth = parallaxDepth;
            this.layerIndex = layerIndex;
        }
        // ← FILE ENDS HERE. Missing: }  (ParallexLayer close)  }  (ParallaxSystem close)
```

**What the compiler says:**
```
animation/ParallaxSystem.java:XX: error: reached end of file while parsing
}
^
```

**Fix option 1 — add braces at end of file:**
```java
    }  // closes ParallexLayer
}      // closes ParallaxSystem
```

**Fix option 2 (recommended) — extract ParallexLayer to its own file:**

Create `animation/ParallexLayer.java`:
```java
package animation;
import java.awt.image.BufferedImage;

public class ParallexLayer {
    public BufferedImage img;
    public float parallaxDepth;
    public int layerIndex;
    public float currentOffsetX = 0f;

    public ParallexLayer(BufferedImage img, float parallaxDepth, int layerIndex) {
        this.img = img;
        this.parallaxDepth = parallaxDepth;
        this.layerIndex = layerIndex;
    }
}
```

Then in `ParallaxSystem.java`, remove the inner class declaration and add a plain `import animation.ParallexLayer;` at the top (or use fully qualified name). This is preferred because several other files already `import animation.ParallexLayer` as if it were a top-level class.

**What this unblocks:** `Game.java` parallax rendering, `Level1.java` and `Level2.java` background layers.

---

### 4.7 Game.java — Missing keyPressed/keyReleased Overrides (Player Can't Move)

**Root cause:** `GameCore` declares `keyPressed(KeyEvent e)` and `keyReleased(KeyEvent e)` as **abstract** methods. `Game.java` must implement them. Currently, `Game.java` provides empty stub bodies (or no bodies at all), meaning key events never reach `PlayerBase.keysDown`. The player appears on screen but is completely frozen.

**Symptoms at runtime:** Game runs, player sprite visible, but pressing WASD/arrow keys does nothing.

**Fix — add to Game.java:**
```java
@Override
public void keyPressed(KeyEvent e) {
    PlayerBase.setKeyPressed(e.getKeyCode(), true);
    // ESC = toggle pause
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        paused = !paused;
    }
    // 1 / 2 = switch level
    if (e.getKeyCode() == KeyEvent.VK_1) switchLevel(1);
    if (e.getKeyCode() == KeyEvent.VK_2) switchLevel(2);
}

@Override
public void keyReleased(KeyEvent e) {
    PlayerBase.setKeyPressed(e.getKeyCode(), false);
}

@Override
public void keyTyped(KeyEvent e) {
    // Do nothing — keyPressed handles everything
}
```

**How to verify:** After this fix, run `java -cp bin Game`. Press A/D — you should see the player sprite move left/right. Press Space — player jumps.

**What this unblocks:** Player movement, jump, sprint, attack — everything keyboard-driven.

---

### 4.8 AudioLibrary — Wrong WAV File Paths (macOS ._prefix)

**Root cause:** Asset files were created on macOS. macOS automatically creates hidden resource fork files with a `._` prefix for every file. The actual playable WAV files are `._Explosion.wav`, `._Jump.wav`, etc. The `loadDefaultSounds()` method in `AudioLibrary` hard-codes paths without the `._` prefix (e.g. `"audio/sfx/Explosion.wav"`) — these paths do not match real files.

**Affected files:**
```
Resources/industrial-zone/audio/sfx/._Explosion.wav        → key: "explosion"
Resources/industrial-zone/audio/sfx/._Jump.wav             → key: "jump"
Resources/industrial-zone/audio/sfx/._Karateka_attack.wav  → key: "karateka_attack"
Resources/industrial-zone/audio/music_wav/._Battle_theme_Chinese_Street.wav
(etc.)
```

**Fix:** Replace all hard-coded path strings in `loadDefaultSounds()` with the directory scanner from Bug 4.2:
```java
// OLD — manual paths (wrong):
registerSound("explosion", new SoundEffect("explosion",
    Config.ASSET_BASE + "audio/sfx/Explosion.wav"));  // file doesn't exist

// NEW — directory scan handles ._prefix automatically:
loadAllSoundsFromDirectory(Config.ASSET_BASE + "audio/sfx/");
loadAllSoundsFromDirectory(Config.ASSET_BASE + "audio/music_wav/");
```

**How to verify:** After fix, add a temporary debug line to print all loaded keys:
```java
soundEffects.keySet().forEach(k -> System.out.println("[AudioLibrary] key: " + k));
```
You should see `explosion`, `jump`, `karateka_attack`, etc. in the output.

---

### 4.9 entities/Level1.java — Cascading Import Chain Failure

**Root cause:** `Level1.java` imports `animation.AnimationAndSpriteLoader`. That class wrongly extends `GameCore` (Bug 4.5). When javac tries to resolve `Level1`, it pulls in `AnimationAndSpriteLoader`, then `GameCore`, and fails because `AnimationAndSpriteLoader` doesn't implement the abstract methods. The error appears in Level1 even though the actual cause is in AnimationAndSpriteLoader.

**Resolution:** This bug resolves automatically once Bug 4.5 is fixed. After removing `extends GameCore` from `AnimationAndSpriteLoader`, the `Level1` import chain compiles cleanly.

**Secondary issue in Level1.java:** The reference to `AnimationAndSpriteLoader.ParallaxSystem` becomes `animation.ParallaxSystem` (separate class after Bug 4.6 fix). Update all usages:
```java
// BEFORE:
AnimationAndSpriteLoader.ParallaxSystem ps = new AnimationAndSpriteLoader.ParallaxSystem();

// AFTER (once both bugs 4.5 and 4.6 are fixed):
import animation.ParallaxSystem;
...
ParallaxSystem ps = new ParallaxSystem();
```

---

### 4.10 controllers/ — Duplicate Class Names in Multiple Packages

**Root cause:** The codebase has parallel development — some concepts were implemented in both `managers/` and `controllers/` without consolidation. Java allows two classes with the same name in different packages, but if any file imports both (or imports the wrong one), you get a conflict.

**Duplicate pairs:**
| Class | Package 1 | Package 2 | Risk |
|-------|-----------|-----------|------|
| `GameState` | `managers` | `controllers` | High — many files import one or the other |
| `AnimationType` | `animation` | `controllers` | Medium — depends on usage |
| `AnimationState` | `animation` | `controllers` | Medium |
| `InputHandler` | `managers` | `controllers` | High — two implementations |

**Fix strategy:**
1. Decide which package "owns" each concept:
   - `GameState` → keep in `managers/` (it's a game-system concern, not a UI concern)
   - `AnimationType` and `AnimationState` → keep in `animation/`
   - `InputHandler` → keep in `controllers/` (the one that implements `MouseListener` too)
2. Delete the duplicates in the other package.
3. Update any imports that pointed to the deleted location.

**Safe approach:** Use `grep` to find all import lines before deleting anything:
```batch
findstr /r /s "import controllers.GameState" src\*.java
findstr /r /s "import managers.GameState" src\*.java
```
Then consolidate to one and fix the others.

---

## 5. UPGRADE PLAN

### 5.1 Game.java — Already solid, minor upgrades

**Current state:** Good. Extends `GameCore`, has parallax, player, enemies, camera, collision.

**Upgrades needed:**

1. **Add keyboard forwarding to PlayerBase:**
```java
@Override
public void keyPressed(KeyEvent e) {
    PlayerBase.setKeyPressed(e.getKeyCode(), true);
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) paused = !paused;
    if (e.getKeyCode() == KeyEvent.VK_1) switchLevel(1);
    if (e.getKeyCode() == KeyEvent.VK_2) switchLevel(2);
}

@Override
public void keyReleased(KeyEvent e) {
    PlayerBase.setKeyPressed(e.getKeyCode(), false);
}
```

2. **Wire in AudioManager:**
```java
// In constructor:
AudioManager audio = new AudioManager();
audio.initialize();
// Start MIDI background music:
audio.playMidi("Resources/industrial-zone/audio/music_midi/Track 1.mid", true);
```

3. **Wire in mouse input via addMouseListener:**
```java
addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent e) {
        // handle mouse click — pass to screen manager
    }
});
addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
    public void mouseMoved(java.awt.event.MouseEvent e) {
        // hover effects
    }
});
```

4. **Draw HUD** (score, health, timer):
```java
private void drawHUD(Graphics2D g) {
    g.setFont(hudFont);  // OTF font loaded via Font.createFont()
    g.setColor(Color.WHITE);
    g.drawString("SCORE: " + score, 20, 40);
    // Health bar using GUI bar asset
    drawHealthBar(g, player.getHealth(), player.getMaxHealth(), 20, 50);
    // Timer
    long elapsed = (System.currentTimeMillis() - startTime) / 1000;
    g.drawString("TIME: " + elapsed + "s", SCREEN_W - 150, 40);
    // Enemy count
    g.drawString("ENEMIES: " + enemies.size(), 20, 70);
}
```

5. **Load OTF fonts:**
```java
private Font loadFont(String path, float size) {
    try {
        return Font.createFont(Font.TRUETYPE_FONT,
            new File(path)).deriveFont(size);
    } catch (Exception e) {
        System.err.println("[Font] Failed: " + path);
        return new Font("Arial", Font.BOLD, (int)size);
    }
}
// Usage:
hudFont = loadFont("Resources/industrial-zone/fonts/some_font.otf", 18f);
```

---

### 5.2 entities/PlayerBase.java — Upgrade to use game2D.Animation

**Current:** Uses raw `BufferedImage[]` arrays and manual frame timer.

**Upgrade:** Wire it through `game2D.Animation` so it can be used as a proper `game2D.Sprite`:

```java
// Option A: Extend Sprite (ideal)
public class PlayerBase extends game2D.Sprite {
    // Constructor builds a game2D.Animation for the current state
    // then passes it to super(animation)
    // This gives us Sprite.draw(), Sprite.update(), etc. for free
}

// Option B: Keep standalone but use game2D.Animation internally
private game2D.Animation buildAnimation(AnimState state) {
    game2D.Animation anim = new game2D.Animation();
    BufferedImage[] fr = frames.get(state);
    if (fr == null) return anim;
    for (BufferedImage img : fr) {
        anim.addFrame(img, FRAME_DURATIONS[state.ordinal()]);
    }
    return anim;
}
```

**Animation state machine (already in PlayerBase):**
```
IDLE  → if key pressed: → WALK
WALK  → if SHIFT: → RUN
WALK  → SPACE pressed: → JUMP
JUMP  → vy > 0: → FALL
FALL  → grounded: → IDLE
IDLE  → CTRL: → ATTACK
ATTACK → timer ends: → IDLE
any  → take damage: → HIT → IDLE
any  → health = 0: → DEATH
```

---

### 5.3 entities/Enemies.java — Enemy system already solid

**Current:** Has `loadEnemySprites()`, `EnemyFactory`, animation by keyword scan.

**Upgrades:**

1. Make `EnemyFactory.EnemyInstance` implement patrol AI:
```java
// Patrol between leftBound and rightBound
private void updatePatrol(float dt) {
    x += speed * dir * dt;
    if (x < leftBound || x > rightBound) dir *= -1;
}
// Chase player when in detection range
private void updateChase(float px, float py, float dt) {
    float dx = px - x;
    if (Math.abs(dx) > 5) x += Math.signum(dx) * chaseSpeed * dt;
}
```

2. Trigger attack animation when player is within `attackRange`:
```java
if (dist < attackRange && attackCooldown <= 0) {
    currentState = EnemyState.ATTACK;
    attackCooldown = 1.2f;
    player.takeDamage(contactDamage);
}
```

3. Spawn smoke VFX on death:
```java
// In the draw / on-death callback:
VFXSystem.spawnSmoke(x, y);  // smoke frames from vfx/1 Smoke/
```

---

### 5.4 managers/AudioManager.java — Full audio system

```java
package managers;

import game2D.Sound;
import utilities.MidiTuner;
import utilities.SoundEffect;
import utilities.AudioLibrary;
import java.io.File;

public class AudioManager {
    private AudioLibrary sfxLibrary;
    private MidiTuner midiPlayer;
    private Sound currentMusic;

    public AudioManager() {
        sfxLibrary = new AudioLibrary();
        midiPlayer = new MidiTuner();  // now has no-arg constructor
    }

    public void initialize() {
        // Load all SFX from directory (handles ._ prefix automatically)
        sfxLibrary.loadAllSoundsFromDirectory(
            "Resources/industrial-zone/audio/sfx/");
        sfxLibrary.loadAllSoundsFromDirectory(
            "Resources/industrial-zone/audio/music_wav/");
        System.out.println("[AudioManager] Loaded " +
            sfxLibrary.getTotalSoundsLoaded() + " sounds");
    }

    /** Play WAV by key name e.g. "explosion", "jump" */
    public void playSFX(String key) {
        SoundEffect se = sfxLibrary.getSound(key);
        if (se != null) se.play();
        else System.err.println("[Audio] SFX not found: " + key);
    }

    /** Play via game2D.Sound directly (fire + forget) */
    public void playWAV(String filePath) {
        Sound s = new Sound(filePath);
        s.start();  // non-blocking thread
    }

    /** Play MIDI track 1–5 */
    public void playMidi(String path, boolean loop) {
        if (midiPlayer != null) {
            midiPlayer.setPath(path);
            midiPlayer.setLoop(loop ? MidiTuner.LOOP : MidiTuner.PLAY_ONCE);
            midiPlayer.play();
        }
    }

    /** Novel audio filter — play WAV with echo effect */
    public void playSFXWithEcho(String key) {
        SoundEffect se = sfxLibrary.getSound(key);
        if (se != null) utilities.EchoFilter.playWithEcho(se.getFilePath());
    }

    public void stopAll() {
        sfxLibrary.stopAllSounds();
        if (midiPlayer != null) midiPlayer.stop();
    }
}
```

---

### 5.5 physics/CollisionDetector.java — Already works, just needs wiring

The `CollisionDetector` class is already correct. It uses:
- `BoundingBox` inner class (x, y, width, height + overlap math)
- `CollisionResult` inner class (colliding, side, penetrationX/Y)
- `SpatialGrid` for broad-phase optimisation

**Wire it into Game.java:**
```java
// In Game constructor:
CollisionDetector physics = new CollisionDetector(true);
int playerID = physics.registerBoundingBox(player.getX(), player.getY(), 64, 64);

// In update():
physics.updatePosition(playerID, player.getX(), player.getY());
// Check player vs all platforms:
for (float[] plat : CURRENT_PLATFORMS) {
    physics.checkCollision(
        physics.getBoundingBox(playerID),
        new CollisionDetector.BoundingBox(plat[0], plat[1], plat[2], plat[3])
    );
}
```

---

### 5.6 animation/ParallaxSystem.java — Fix + wire to Game.java

After fixing the brace issue (see 4.6), wire to Game.java:
```java
// In Game.loadAssets():
parallax1 = new ParallaxSystem();
String bgDir = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/";
File[] bgFiles = new File(bgDir).listFiles(f -> f.getName().endsWith(".png") && !f.getName().startsWith("._"));
if (bgFiles != null) {
    java.util.Arrays.sort(bgFiles);
    float[] factors = {0.0f, 0.08f, 0.18f, 0.30f, 0.50f};
    for (int i = 0; i < Math.min(bgFiles.length, factors.length); i++) {
        BufferedImage img = ImageIO.read(bgFiles[i]);
        parallax1.addLayer(new ParallexLayer(img, factors[i], i));
    }
}

// In update():
parallax1.updateCamera(cameraX);

// In draw():
parallax1.render(g, getWidth(), getHeight());
```

---

### 5.7 entities/Level1.java & Level2.java — Fix imports, use TileMap

**Fix:** Remove dependency on `controllers.AnimatedObjectManager` for basic functionality.
Wire `game2D.TileMap` directly:

```java
// Simplified Level1 init using game2D.TileMap:
public static TileMap buildTileMap() {
    TileMap tm = new TileMap();
    // The map.txt for level 1 lives at: handout/maps/level_1/map.txt
    tm.loadMap("maps/level_1", "map.txt");
    return tm;
}

// And in draw() in Game.java:
tileMap.draw(g, (int)-cameraX, (int)-cameraY);
```

The `maps/level_1/map.txt` file already exists in the repo. Populate it with real tile characters:
```
20 10 32 32
#g=Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/01_Platform...png
#map
gggggggggggggggggggg
g..................g
...
```

---

### 5.8 ai/EnemyAI.java — Already structurally sound

`EnemyAI extends AI.AIAgent` is architecturally correct. `AI.java` has the inner class `AIAgent`. The AI state machine is: IDLE → PATROL → ALERT → CHASE → ATTACK → FLEE.

**Wire it to enemies:**
```java
// In EnemyFactory, for each enemy instance:
AI.AIPathfinder pathfinder = new AI.AIPathfinder();
pathfinder.addWaypoint(new AI.Waypoint(leftBound, y));
pathfinder.addWaypoint(new AI.Waypoint(rightBound, y));
EnemyAI brain = new EnemyAI(enemyType, pathfinder);
brain.initialize();
// In update(): brain.executeBehavior(dt);
```

---

### 5.9 controllers/InputHandler.java — Keyboard + mouse

```java
package controllers;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {
    private Set<Integer> keysDown = new HashSet<>();
    private int mouseX, mouseY;
    private boolean leftClick, rightClick;

    // Keyboard
    public void keyPressed(KeyEvent e)  { keysDown.add(e.getKeyCode()); }
    public void keyReleased(KeyEvent e) { keysDown.remove(e.getKeyCode()); }
    public void keyTyped(KeyEvent e)    {}

    // Mouse
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) leftClick = true;
        if (e.getButton() == MouseEvent.BUTTON3) rightClick = true;
    }
    public void mouseMoved(MouseEvent e)   { mouseX = e.getX(); mouseY = e.getY(); }
    public void mouseDragged(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
    // ... (other required interface methods stub out)

    public boolean isDown(int keyCode) { return keysDown.contains(keyCode); }
    public int getMouseX() { return mouseX; }
    public int getMouseY() { return mouseY; }
    public boolean consumeLeftClick() { boolean v = leftClick; leftClick = false; return v; }
}
```

---

### 5.10 ScoreManager.java — Wire into Game, Display in HUD

**Current state:** `managers/ScoreManager.java` exists but is never instantiated or called anywhere. The HUD shows a hard-coded `"SCORE: 0"` string (or nothing at all).

**Why it matters:** The assignment requires a working game loop with player progression. A live score counter shows the marker that enemies are actually being defeated and the game state is tracked.

**Step-by-step upgrade:**

**Step 1 — Declare field in Game.java:**
```java
private ScoreManager scoreManager;
```

**Step 2 — Instantiate in Game constructor (before the game loop starts):**
```java
scoreManager = new ScoreManager();
scoreManager.reset();   // ensure starting at zero
```

**Step 3 — Award points on enemy death. In the enemy update loop inside `update(float dt)` in Game.java:**
```java
Iterator<EnemyFactory.EnemyInstance> it = enemies.iterator();
while (it.hasNext()) {
    EnemyFactory.EnemyInstance e = it.next();
    e.update(dt);
    if (e.isDead()) {
        // Award points — different enemies worth different amounts
        switch (e.getType()) {
            case DRONE_UFO:    scoreManager.addPoints(100); break;
            case DRONE_JET:    scoreManager.addPoints(150); break;
            case KNIGHT:       scoreManager.addPoints(200); break;
            case WINGED:       scoreManager.addPoints(250); break;
            default:           scoreManager.addPoints(50);
        }
        scoreManager.incrementKillCount();
        it.remove();
    }
}
```

**Step 4 — Display in the HUD `drawHUD(Graphics2D g)` method:**
```java
private void drawHUD(Graphics2D g) {
    g.setFont(hudFont);
    g.setColor(Color.WHITE);

    // Score — top left
    g.drawString("SCORE: " + scoreManager.getScore(), 20, 35);

    // Kill count — below score
    g.drawString("KILLS: " + scoreManager.getKillCount(), 20, 60);

    // Level label — top right
    String lvlLabel = "LEVEL " + currentLevel;
    int lw = g.getFontMetrics().stringWidth(lvlLabel);
    g.drawString(lvlLabel, getWidth() - lw - 20, 35);

    // Time — top right, below level label
    long elapsed = (System.currentTimeMillis() - startTime) / 1000L;
    String timeStr = String.format("TIME: %d:%02d", elapsed / 60, elapsed % 60);
    int tw = g.getFontMetrics().stringWidth(timeStr);
    g.drawString(timeStr, getWidth() - tw - 20, 60);
}
```

**How to test:** Run game, kill any enemy — score counter in the top-left corner increments. Check console for `[ScoreManager]` log output if the class has debug logging.

---

### 5.11 OTF Font Loading — HUD and Menus in Custom Typeface

**Current state:** All text (score, health, time display) uses `new Font("Arial", Font.BOLD, 18)` — a system font with no visual connection to the industrial-zone art style.

**Why it matters:** The game uses a detailed pixel-art / cyberpunk aesthetic. Custom fonts make the HUD match the art style and demonstrate resource-loading expertise (assessed under "technical quality").

**Resource location:** `Resources/industrial-zone/fonts/` — contains `.otf` files. List them:
```
cd handout
dir Resources\industrial-zone\fonts\
```

**Step-by-step upgrade:**

**Step 1 — Scan font directory and load at startup:**
```java
private Font loadOTF(String path, float size) {
    try {
        Font base = Font.createFont(Font.TRUETYPE_FONT, new File(path));
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(base);
        return base.deriveFont(Font.PLAIN, size);
    } catch (FontFormatException | IOException ex) {
        System.err.println("[Font] Cannot load " + path + " — " + ex.getMessage());
        return new Font("Arial", Font.BOLD, (int) size);
    }
}
```

**Step 2 — Load fonts in the `loadAssets()` or `init()` method before first draw:**
```java
// Scan and load all OTF files from the fonts directory
File fontDir = new File("Resources/industrial-zone/fonts/");
File[] otfFiles = fontDir.listFiles(f -> f.getName().toLowerCase().endsWith(".otf"));
if (otfFiles != null && otfFiles.length > 0) {
    hudFont    = loadOTF(otfFiles[0].getPath(), 18f);      // smallest — HUD
    menuFont   = loadOTF(otfFiles[0].getPath(), 36f);      // larger — menus
    titleFont  = loadOTF(otfFiles[0].getPath(), 72f);      // largest — game-over / title
    System.out.println("[Font] Loaded: " + otfFiles[0].getName());
} else {
    // Fallback — no crash, just Arial
    System.err.println("[Font] No OTF files found in " + fontDir.getAbsolutePath());
    hudFont  = new Font("Arial", Font.BOLD, 18);
    menuFont = new Font("Arial", Font.BOLD, 36);
    titleFont = new Font("Arial", Font.BOLD, 72);
}
```

**Step 3 — Apply `hudFont` everywhere text is drawn.** Replace any `g.setFont(...)` calls with:
```java
g.setFont(hudFont);   // or menuFont / titleFont depending on context
```

**How to test:** Run game — HUD text changes to the custom pixel/cyberpunk typeface. If Arial still shows, add `System.out.println("[Font] " + hudFont.getFontName())` and check.

---

### 5.12 Pause Menu Overlay

**Current state:** Pressing ESC does nothing (or toggles `paused` but nothing is drawn). The game loop keeps running regardless.

**Why it matters:** Pause functionality is a core quality-of-life feature. Required for the menu / screen-management requirement.

**Step-by-step upgrade:**

**Step 1 — Add field to Game.java:**
```java
private boolean paused = false;
private long pauseStartTime;   // to track time accurately (don't count paused time)
```

**Step 2 — Toggle in `keyPressed()`:**
```java
if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
    paused = !paused;
    if (paused) pauseStartTime = System.currentTimeMillis();
    else        startTime += (System.currentTimeMillis() - pauseStartTime); // shift start
}
```

**Step 3 — Skip game logic when paused in `update(float dt)`:**
```java
@Override
public void update(float dt) {
    if (paused) return;   // ← add this at the very top of update()
    // ... all existing update code below ...
}
```

**Step 4 — Draw the overlay at the bottom of `draw(Graphics2D g)`, after everything else:**
```java
private void drawPauseOverlay(Graphics2D g) {
    // Semi-transparent dark rectangle covering full screen
    g.setColor(new Color(0, 0, 0, 150));
    g.fillRect(0, 0, getWidth(), getHeight());

    // "PAUSED" centred on screen
    g.setFont(titleFont);   // large custom font
    g.setColor(Color.WHITE);
    String text = "PAUSED";
    FontMetrics fm = g.getFontMetrics();
    int tx = (getWidth()  - fm.stringWidth(text)) / 2;
    int ty = (getHeight() - fm.getHeight())        / 2 + fm.getAscent();
    g.drawString(text, tx, ty);

    // Sub-text instructions
    g.setFont(hudFont);
    g.setColor(new Color(200, 200, 200));
    String sub = "Press ESC to resume  |  Press Q to quit";
    int sx = (getWidth() - g.getFontMetrics().stringWidth(sub)) / 2;
    g.drawString(sub, sx, ty + 60);
}

// In draw():
if (paused) drawPauseOverlay(g);
```

**How to test:** Run game, press ESC — player movement freezes, a dark transparent overlay appears with "PAUSED" in the custom font. Press ESC again — overlay disappears and game resumes.

---

### 5.13 Game Over Screen

**Current state:** If the player's `health` reaches 0, nothing visible happens — the game loop continues as if the player is still alive (frozen at 0 HP).

**Why it matters:** The assignment requires a complete game experience. The Game Over screen also displays the final score, demonstrating that `ScoreManager` (upgrade 5.10) is fully integrated.

**Step-by-step upgrade:**

**Step 1 — Add fields to Game.java:**
```java
private boolean gameOver    = false;
private int     deathFrames = 0;       // frames elapsed since death
private static final int DEATH_ANIM_FRAMES = 90;  // ~1.5 sec at 60fps
```

**Step 2 — Detect death in `update(float dt)`. After the player takes damage:**
```java
if (player.getHealth() <= 0 && !gameOver) {
    gameOver = true;
    deathFrames = 0;
    player.setState(AnimState.DEATH);  // trigger death animation
    audioManager.playSFX("samurai_death");
    audioManager.stopMidi();
    audioManager.playMidi("Resources/industrial-zone/audio/music_midi/Track 5.mid", false); // game-over track
}
if (gameOver) {
    deathFrames++;
    player.update(dt); // let death animation play out
    return;            // skip all other game logic
}
```

**Step 3 — Draw the Game Over screen in `draw(Graphics2D g)`. After all normal game drawing:**
```java
private void drawGameOver(Graphics2D g) {
    // Dark overlay
    g.setColor(new Color(0, 0, 0, 180));
    g.fillRect(0, 0, getWidth(), getHeight());

    // "GAME OVER" in red, centred
    g.setFont(titleFont);
    g.setColor(new Color(220, 30, 30));
    String go = "GAME OVER";
    FontMetrics fm = g.getFontMetrics();
    int gx = (getWidth() - fm.stringWidth(go)) / 2;
    int gy = getHeight() / 3 + fm.getAscent();
    g.drawString(go, gx, gy);

    // Final score below
    g.setFont(menuFont);
    g.setColor(Color.WHITE);
    String sc = "FINAL SCORE: " + scoreManager.getScore();
    int sx = (getWidth() - g.getFontMetrics().stringWidth(sc)) / 2;
    g.drawString(sc, sx, gy + 80);

    // Kill count
    String kc = "ENEMIES DEFEATED: " + scoreManager.getKillCount();
    int kx = (getWidth() - g.getFontMetrics().stringWidth(kc)) / 2;
    g.drawString(kc, kx, gy + 120);

    // Prompt — only show after death anim finishes
    if (deathFrames > DEATH_ANIM_FRAMES) {
        g.setFont(hudFont);
        g.setColor(new Color(180, 180, 180));
        String prompt = "Press ENTER to restart   |   Press ESC to quit";
        int px = (getWidth() - g.getFontMetrics().stringWidth(prompt)) / 2;
        g.drawString(prompt, px, gy + 180);
    }
}

// In draw():
if (gameOver) { drawGameOver(g); return; }
```

**How to test:** Let an enemy kill the player (reduce health to 0). After the death animation (~1.5 s), a Game Over screen appears in the custom font with the final score, kill count, and "Press ENTER to restart" prompt.

---

### 5.14 Level Transition Trigger

**Current state:** `currentLevel` variable exists and `switchLevel(int n)` exists in Game.java, but nothing ever calls `switchLevel()` — the player can run infinitely to the right and never progresses to Level 2.

**Why it matters:** Two distinct levels are required by the assignment. A seamless trigger makes the game feel complete.

**Step-by-step upgrade:**

**Step 1 — Define exit trigger in each Level class:**
```java
// In entities/Level1.java:
public static final float EXIT_X = 8192f;   // far right of the level
public static final float EXIT_Y = 400f;    // ground height at exit
public static final int   NEXT_LEVEL = 2;

// In entities/Level2.java:
public static final float EXIT_X = 10240f;
public static final float EXIT_Y = 380f;
public static final int   NEXT_LEVEL = -1;  // -1 = no next level (boss / end)
```

**Step 2 — Check in `update()` after player position update:**
```java
// Level exit check  (put this near bottom of update(), after player moves)
float exitX = (currentLevel == 1) ? Level1.EXIT_X : Level2.EXIT_X;
if (!gameOver && player.getX() >= exitX) {
    int next = (currentLevel == 1) ? Level1.NEXT_LEVEL : Level2.NEXT_LEVEL;
    if (next > 0) {
        switchLevel(next);
    } else {
        // All levels done — show credits / victory
        showCredits = true;
    }
}
```

**Step 3 — Flesh out `switchLevel(int n)`:**
```java
private void switchLevel(int n) {
    currentLevel = n;
    // Swap tile map
    tileMap = (n == 1) ? Level1.buildTileMap() : Level2.buildTileMap();
    // Swap parallax layers
    parallax = (n == 1) ? Level1.buildParallax() : Level2.buildParallax();
    // Reset player to start position of new level
    player.setX(200f);
    player.setY(Level1.EXIT_Y);   // or Level2 equivalent
    player.setHealth(player.getMaxHealth());  // optional — restore HP
    cameraX = 0f;
    // Swap MIDI
    String midiPath = "Resources/industrial-zone/audio/music_midi/Track " + n + ".mid";
    audioManager.stopMidi();
    audioManager.playMidi(midiPath, true);
    System.out.println("[Game] Switched to Level " + n);
}
```

**How to test:** Stand next to the invisible EXIT_X boundary (or temporarily reduce `EXIT_X` to `500f`), walk right — screen loads Level 2 tile set, parallax swaps to Level 2 backgrounds, MIDI Track 2 starts playing.

---

### 5.15 Character Select Screen

**Current state:** The game always starts with the Biker character. `entities/PlayerBase.java` already supports `CharacterType` enum (`BIKER`, `PUNK`, `CYBORG`), but no UI lets the player choose.

**Why it matters:** Character selection demonstrates multiple animated sprites, mouse-click event handling, and screen management — all assessed criteria.

**Step-by-step upgrade:**

**Step 1 — Add screen state enum to Game.java:**
```java
private enum ScreenState { CHARACTER_SELECT, PLAYING, PAUSED, GAME_OVER }
private ScreenState screenState = ScreenState.CHARACTER_SELECT;
```

**Step 2 — Load character portrait images during `loadAssets()`:**
```java
private BufferedImage portraitBiker, portraitPunk, portraitCyborg;

// In loadAssets():
portraitBiker  = ImageIO.read(new File("Resources/industrial-zone/characters/playable/Biker/Idle.png"));
portraitPunk   = ImageIO.read(new File("Resources/industrial-zone/characters/playable/Punk/Idle.png"));
portraitCyborg = ImageIO.read(new File("Resources/industrial-zone/characters/playable/Cyborg/Idle.png"));
```

**Step 3 — Draw select screen when `screenState == CHARACTER_SELECT`:**
```java
private void drawCharacterSelect(Graphics2D g) {
    // Dark background
    g.setColor(new Color(15, 15, 30));
    g.fillRect(0, 0, getWidth(), getHeight());

    // Title
    g.setFont(menuFont);
    g.setColor(Color.WHITE);
    String t = "SELECT YOUR CHARACTER";
    g.drawString(t, (getWidth() - g.getFontMetrics().stringWidth(t)) / 2, 80);

    // Three portrait boxes
    int[] xs = { 120, 380, 640 };
    BufferedImage[] portraits = { portraitBiker, portraitPunk, portraitCyborg };
    String[] names = { "BIKER", "PUNK", "CYBORG" };
    String[] descs = { "Balanced", "Fast/Agile", "Heavy/Tech" };

    for (int i = 0; i < 3; i++) {
        // Highlight selected
        if (hoveredChar == i) { g.setColor(new Color(80, 160, 255, 80)); g.fillRect(xs[i]-10, 110, 220, 320); }
        // Portrait
        if (portraits[i] != null) g.drawImage(portraits[i], xs[i], 130, 200, 200, null);
        // Name
        g.setFont(hudFont);
        g.setColor(Color.WHITE);
        g.drawString(names[i], xs[i] + 50, 350);
        // Desc
        g.setColor(new Color(160, 160, 160));
        g.drawString(descs[i], xs[i] + 30, 375);
    }

    // Instruction
    g.setFont(hudFont);
    g.setColor(new Color(200, 200, 200));
    String inst = "Click a character to start";
    g.drawString(inst, (getWidth() - g.getFontMetrics().stringWidth(inst)) / 2, 460);
}
```

**Step 4 — Handle mouse click on the select screen:**
```java
// In Game.java — add MouseListener in constructor:
addMouseListener(new MouseAdapter() {
    @Override public void mouseClicked(MouseEvent e) {
        if (screenState == ScreenState.CHARACTER_SELECT) {
            int mx = e.getX();
            // Detect which portrait was clicked based on x ranges
            if (mx >= 120 && mx <= 320) { player = new PlayerBase(CharacterType.BIKER);  startGame(); }
            else if (mx >= 380 && mx <= 580) { player = new PlayerBase(CharacterType.PUNK);    startGame(); }
            else if (mx >= 640 && mx <= 840) { player = new PlayerBase(CharacterType.CYBORG);  startGame(); }
        }
    }
});
```

**Step 5 — Track mouse hover:**
```java
private int hoveredChar = -1;  // index 0/1/2 or -1 for none

addMouseMotionListener(new MouseMotionAdapter() {
    @Override public void mouseMoved(MouseEvent e) {
        int mx = e.getX();
        if (screenState == ScreenState.CHARACTER_SELECT) {
            if      (mx >= 120 && mx <= 320) hoveredChar = 0;
            else if (mx >= 380 && mx <= 580) hoveredChar = 1;
            else if (mx >= 640 && mx <= 840) hoveredChar = 2;
            else                             hoveredChar = -1;
        }
    }
});
```

**Step 6 — `startGame()` method:**
```java
private void startGame() {
    screenState = ScreenState.PLAYING;
    scoreManager.reset();
    startTime = System.currentTimeMillis();
    switchLevel(1);  // load Level 1 and start MIDI Track 1
}
```

**How to test:** On game launch, the character select screen appears with three portrait options instead of the game. Hovering over a portrait highlights it in blue. Clicking starts the game with that character's sprite.

---

### 5.16 EchoFilter — Novel Audio Effect (Assignment Requirement)

**Current state:** `utilities/EchoFilter.java` is referenced in `AudioManager.playSFXWithEcho()` but the file does not exist — calling that method throws a compile error.

**Why it matters:** The assignment specification requires a "novel digital audio effect" beyond just playing sounds. An echo / delay filter implemented manually (not via a third-party library) fully satisfies this and demonstrates DSP understanding.

**Mathematical model:**

$$y[n] = x[n] + \alpha \cdot x[n - D]$$

Where:
- $x[n]$ = input sample at index $n$
- $D$ = delay in samples = `delayMs * sampleRate / 1000`
- $\alpha$ = echo volume factor (typically 0.4–0.6)
- $y[n]$ = output sample with echo mixed in

**Step-by-step implementation:**

**Step 1 — Create `src/utilities/EchoFilter.java`:**
```java
package utilities;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Novel audio effect: delay-line echo filter.
 * Mixes the input PCM signal with a time-delayed copy of itself.
 * Mathematical model: y[n] = x[n] + alpha * x[n - D]
 */
public class EchoFilter {

    private final int    delayMs;    // echo delay in milliseconds
    private final float  alpha;      // echo volume multiplier (0.0 – 1.0)

    public EchoFilter(int delayMs, float alpha) {
        this.delayMs = delayMs;
        this.alpha   = Math.max(0f, Math.min(1f, alpha));
    }

    /**
     * Play a WAV file with echo applied in real time via a ring-buffer delay line.
     */
    public void play(String filePath) {
        new Thread(() -> {
            try {
                AudioInputStream raw = AudioSystem.getAudioInputStream(new File(filePath));
                AudioFormat fmt = raw.getFormat();

                // Only 16-bit PCM is processed; fall back to plain play otherwise
                if (fmt.getSampleSizeInBits() != 16) {
                    System.err.println("[EchoFilter] Only 16-bit PCM supported: " + filePath);
                    new game2D.Sound(filePath).start();
                    return;
                }

                // Delay line length in samples
                int delaySamples = (int)(fmt.getSampleRate() * delayMs / 1000.0)
                                   * fmt.getChannels();
                short[] delayBuf = new short[delaySamples];
                int     delayPos = 0;

                // Open output line
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, fmt);
                SourceDataLine out = (SourceDataLine) AudioSystem.getLine(info);
                out.open(fmt);
                out.start();

                // Process in chunks of 4 KB
                byte[] inBuf  = new byte[4096];
                byte[] outBuf = new byte[4096];
                int  bytesRead;

                while ((bytesRead = raw.read(inBuf, 0, inBuf.length)) != -1) {
                    for (int i = 0; i < bytesRead - 1; i += 2) {
                        // Read 16-bit little-endian sample
                        short sample = (short)((inBuf[i+1] << 8) | (inBuf[i] & 0xFF));

                        // y[n] = x[n] + alpha * x[n - D]
                        float out_f = sample + alpha * delayBuf[delayPos];
                        // Clamp to 16-bit range
                        short out_s = (short) Math.max(Short.MIN_VALUE,
                                               Math.min(Short.MAX_VALUE, (int) out_f));

                        // Write back to delay buffer
                        delayBuf[delayPos] = sample;
                        delayPos = (delayPos + 1) % delaySamples;

                        // Write processed sample to output buffer
                        outBuf[i]   = (byte)( out_s        & 0xFF);
                        outBuf[i+1] = (byte)((out_s >>> 8) & 0xFF);
                    }
                    out.write(outBuf, 0, bytesRead);
                }

                out.drain();
                out.close();
                raw.close();
                System.out.println("[EchoFilter] Finished: " + filePath);

            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                System.err.println("[EchoFilter] Error: " + ex.getMessage());
            }
        }, "EchoFilter-Thread").start();
    }

    /** Convenience static factory — 250 ms echo, 50% volume */
    public static EchoFilter defaultEcho() {
        return new EchoFilter(250, 0.5f);
    }

    /** Static convenience: fire-and-forget with default echo settings */
    public static void playWithEcho(String filePath) {
        defaultEcho().play(filePath);
    }
}
```

**How the ring buffer works (plain English for comments / report):**
1. Maintain a circular `short[]` array of length `delaySamples`
2. For every input sample, read the value that was stored `D` samples ago from the ring
3. Add `alpha` times that old value to the current sample → output
4. Store the current input sample into the ring, advance the write pointer
5. The result is the original sound with a softer copy of itself echoing behind

**Wire into AudioManager:**
```java
// In AudioManager:
private EchoFilter echoFilter = new EchoFilter(250, 0.5f);

public void playSFXWithEcho(String key) {
    SoundEffect se = sfxLibrary.getSound(key);
    if (se != null) echoFilter.play(se.getFilePath());
    else System.err.println("[Audio] SFX key not found: " + key);
}
```

**How to test:** Trigger `audioManager.playSFXWithEcho("explosion")` on an enemy death — you hear the explosion sound followed immediately by a softer, decaying echo ~250 ms later. Check the console for `[EchoFilter] Finished:` to confirm the method ran.

---

## 6. ASSIGNMENT REQUIREMENTS COVERAGE

This section maps every assessed criterion to the exact class, method, and observable behaviour a marker would see on screen. Use this as a cross-reference checklist when writing your report.

> **Coverage status legend:**  
> ✅ Fully implemented  |  🔧 Implemented but needs bug-fix from Section 4 first  |  ⏳ Planned (Upgrade 5.x)

---

### 6.1 Animations with State-Change

**Criterion:** Each sprite must visually change animation when the game state changes (idle → walk → jump, etc.).

| Detail | Value |
|--------|-------|
| **Status** | 🔧 (needs Bug 4.5 + 4.6 fix first, then works) |
| **Exact class** | `entities/PlayerBase.java` |
| **Key method** | `PlayerBase.setState(AnimState s)` |
| **State machine** | IDLE → WALK → RUN → JUMP → FALL → ATTACK → HIT → DEATH |
| **Transition trigger** | `keysDown.contains(VK_A)` → WALK; `keysDown.contains(VK_SPACE)` → JUMP |
| **Animation class** | `animation/AnimationPlayer.java` — `playAnimation(AnimState, frames, duration)` |
| **Spritesheet source** | `Resources/industrial-zone/characters/playable/Biker/Idle.png` (etc.) |
| **What marker sees** | Player sprite changes from a standing loop → running frames → mid-air frame → landing |

**Enemy animation state-change:**

| Detail | Value |
|--------|-------|
| **Exact class** | `entities/Enemies.java` → `EnemyFactory.EnemyInstance` |
| **States** | IDLE → PATROL → ALERT → CHASE → ATTACK → DEATH |
| **AI driver** | `ai/EnemyAI.java` `executeBehavior(dt)` |
| **What marker sees** | Drone hovers (IDLE), moves left/right (PATROL), lunges (ATTACK), explodes (DEATH smoke) |

---

### 6.2 Player-Controlled Sprite

**Criterion:** A sprite that users directly control with keyboard input.

| Detail | Value |
|--------|-------|
| **Status** | 🔧 (needs Bug 4.7 fix — `keyPressed` forwarding) |
| **Exact class** | `entities/PlayerBase.java` |
| **Key handler** | `Game.keyPressed(KeyEvent)` → `PlayerBase.setKeyPressed(code, true)` |
| **Bound keys** | A/← = left, D/→ = right, SPACE = jump, SHIFT = sprint, CTRL = attack, ESC = pause |
| **Physics** | `PlayerBase.update(dt)` applies gravity (`vy += GRAVITY * dt`), friction, jump impulse |
| **Rendering** | `player.draw(g, cameraX)` — draws the current animation frame offset by scroll |
| **What marker sees** | Biker sprite walks left/right, jumps, plays punch animation on CTRL |

---

### 6.3 Multiple Animated Enemy Sprites

**Criterion:** At least two distinct enemy types, both animated.

| Enemy | Sprite folder | States animated | Upgrade |
|-------|--------------|-----------------|---------|
| UFO Saucer Drone | `characters/enemies/drones/1/` | Idle, Traverse, Attack, Destroy | 5.3 |
| Jet Drone | `characters/enemies/drones/2/` | Flight, Bomb-drop | 5.3 |
| Armoured Knight | `characters/enemies/sci-fi-antagonists/2/` | Idle, Walk, Attack×4, Hurt, Death | 5.3 |
| Winged Warrior | `characters/enemies/sci-fi-antagonists/3/` | Idle, Walk, Attack×4, Death | 5.3 |

| Detail | Value |
|--------|-------|
| **Status** | 🔧 (blocked by Bug 4.5 / 4.6 — AnimationAndSpriteLoader + ParallaxSystem) |
| **Factory** | `entities/Enemies.java` `EnemyFactory.createEnemy(EnemyType)` |
| **Animation loader** | `animation/AnimationAndSpriteLoader.loadAnimationFromFolder(path)` |
| **What marker sees** | Multiple distinct sprite types on screen, each independently animated, each with different movement patterns |

---

### 6.4 Collision Detection

**Criterion:** Player and enemies must respond to collisions (with each other, with tiles).

| Collision pair | Detector | Method |
|----------------|----------|--------|
| Player ↔ enemy | `physics/CollisionDetector.java` AABB | `checkCollision(bb1, bb2)` returns `CollisionResult` |
| Player ↔ tile | `game2D.TileMap` | `getTileAt(col, row)` — character `'.'` is passable, others solid |
| Player ↔ projectile | `physics/CollisionDetector.java` | Same AABB, projectile bounding box |
| Enemy ↔ tile | Same TileMap check | Applied per enemy in `update()` |

| Detail | Value |
|--------|-------|
| **Status** | ✅ Class exists and is correct; needs wiring — Upgrade 5.5 |
| **Exact class** | `physics/CollisionDetector.java` |
| **Algorithm** | AABB (Axis-Aligned Bounding Box) — computed via `BoundingBox.overlaps()` inner class |
| **Broad phase** | `SpatialGrid` inner class — divides world into cells, only checks nearby objects |
| **Resolution** | `CollisionResult.penetrationX/Y` — pushes player out of solid tile by minimum overlap |
| **What marker sees** | Player cannot walk through walls or floors; player takes damage on touch with enemy |

---

### 6.5 Multiple Sounds + Novel Audio Filter

**Criterion:** Game must play multiple different sound effects. Must implement at least one "novel" audio effect beyond simple WAV playback.

| Sound trigger | Key used | SFX file |
|--------------|----------|---------|
| Player jump | SPACE | `audio/sfx/._Jump.wav` |
| Player melee attack | CTRL | `audio/sfx/._Karateka_attack.wav` |
| Player death | health → 0 | `audio/sfx/._Samurai_death.wav` |
| Enemy death | `isDead()` | `audio/sfx/._Explosion.wav` + echo filter |
| Drone proximity alert | within range | `audio/sfx/._Hovering_robot_sting.wav` |
| Level complete | exit trigger | `audio/sfx/._Melody_of_the_win.wav` |
| Checkpoint | tile trigger | `audio/sfx/._Bell_on_the_door.wav` |

**Novel filter:** `utilities/EchoFilter.java` (Upgrade 5.16)  
- Algorithm: delay-line echo — $y[n] = x[n] + \alpha \cdot x[n-D]$  
- No third-party library used — raw PCM manipulation via `javax.sound.sampled.SourceDataLine`  
- Demonstrates DSP understanding and `javax.sound.sampled` API depth

| Detail | Value |
|--------|-------|
| **Status** | ⏳ EchoFilter needs creating (Upgrade 5.16); SFX wiring is Upgrade 5.4 |
| **SFX class** | `utilities/SoundEffect.java` |
| **Library class** | `utilities/AudioLibrary.java` |
| **Manager** | `managers/AudioManager.java` |
| **What marker sees** | Different sounds play on different actions; explosion sound has an audible echo effect |

---

### 6.6 MIDI Background Music

**Criterion:** Background music using MIDI (not WAV).

| Track | File | When played |
|-------|------|-------------|
| Track 1 | `audio/music_midi/Track 1.mid` | Level 1 gameplay |
| Track 2 | `audio/music_midi/Track 2.mid` | Level 2 gameplay |
| Track 3 | `audio/music_midi/Track 3.mid` | Boss fight |
| Track 4 | `audio/music_midi/Track 4.mid` | Main menu |
| Track 5 | `audio/music_midi/Track 5.mid` | Game Over |

| Detail | Value |
|--------|-------|
| **Status** | 🔧 (needs Bug 4.3 fix — MidiTuner no-arg constructor) |
| **MIDI class** | `utilities/MidiTuner.java` |
| **API used** | `javax.sound.midi.MidiSystem`, `Sequencer`, `Sequence` |
| **Loop support** | `MidiTuner.setLoop(MidiTuner.LOOP)` → `Sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY)` |
| **What marker sees** | Background music starts on game launch (looping), changes when switching levels |

---

### 6.7 Keyboard Event Handling

**Criterion:** Game must respond to keyboard input using Java event model (`KeyListener`).

| Key | Action | Handler location |
|-----|--------|-----------------|
| A / ← | Move left | `Game.keyPressed()` → `PlayerBase.setKeyPressed(VK_A, true)` |
| D / → | Move right | `Game.keyPressed()` → `PlayerBase.setKeyPressed(VK_D, true)` |
| SPACE | Jump | `Game.keyPressed()` → `PlayerBase.setKeyPressed(VK_SPACE, true)` |
| SHIFT | Sprint | `Game.keyPressed()` → speed multiplier ×1.8 |
| CTRL | Attack (melee) | `Game.keyPressed()` → ATTACK animation + hitbox check |
| ESC | Toggle pause | `Game.keyPressed()` → `paused = !paused` |
| 1 | Switch to Level 1 | `Game.keyPressed()` → `switchLevel(1)` |
| 2 | Switch to Level 2 | `Game.keyPressed()` → `switchLevel(2)` |
| Q | Quit (on Game Over) | `Game.keyPressed()` → `System.exit(0)` |

| Detail | Value |
|--------|-------|
| **Status** | 🔧 (needs Bug 4.7 fix — stubs must be replaced with real bodies) |
| **Interface implemented** | `KeyListener` — inherited via `game2D.GameCore extends JFrame implements KeyListener` |
| **Methods** | `keyPressed(KeyEvent)`, `keyReleased(KeyEvent)`, `keyTyped(KeyEvent)` |
| **What marker sees** | Player responds immediately to key presses with no polling lag |

---

### 6.8 Mouse Event Handling

**Criterion:** Game must respond to mouse events using Java event model.

| Mouse action | Effect | Where handled |
|-------------|--------|---------------|
| Click character portrait | Selects character, starts game | `MouseAdapter.mouseClicked()` in `Game` constructor |
| Click "RESUME" button (pause menu) | Unpauses | Same `MouseAdapter` |
| Click "QUIT" button (pause menu) | `System.exit(0)` | Same |
| Mouse move over portrait | Highlights portrait (hover effect) | `MouseMotionAdapter.mouseMoved()` |

| Detail | Value |
|--------|-------|
| **Status** | ⏳ (Upgrade 5.15 — character select) |
| **Classes** | `java.awt.event.MouseAdapter`, `java.awt.event.MouseMotionAdapter` |
| **Registration** | `addMouseListener(...)` + `addMouseMotionListener(...)` on `Game` (`JFrame`) |
| **What marker sees** | Character portraits highlight on hover; clicking starts the game with that character |

---

### 6.9 Two-Level 2D Tilemap

**Criterion:** Two distinct levels, each with a scrolling tile-based map.

| Detail | Level 1 | Level 2 |
|--------|---------|---------|
| **Status** | 🔧 (Upgrade 5.7) | 🔧 (Upgrade 5.7) |
| **Map file** | `maps/level_1/map.txt` | `maps/level_2/map.txt` |
| **Tile folder** | `1 Tiles/Industrial_zone_level_1/1 Tiles/` | `1 Tiles/power-station-level-2/1 Tiles/` |
| **TileMap class** | `game2D.TileMap.loadMap("maps/level_1", "map.txt")` | `game2D.TileMap.loadMap("maps/level_2", "map.txt")` |
| **Scroll** | Camera follows player horizontally (`cameraX += speed * dt`) | Same |
| **Transition trigger** | Player x > `Level1.EXIT_X` → `switchLevel(2)` | n/a (boss or end) |
| **What marker sees** | Industrial-zone metal tiles, distinct from power-station tiles in Level 2 |

---

### 6.10 Parallax Scrolling Background

**Criterion:** Background must scroll at different speeds from the foreground (parallax effect).

| Layer | Image source | Parallax depth | Speed factor |
|-------|-------------|----------------|-------------|
| Layer 0 (sky) | `2 Background_level_1/bg_layer_0.png` | 0.00 | Sky does not move |
| Layer 1 | `2 Background_level_1/bg_layer_1.png` | 0.08 | Very slow drift |
| Layer 2 | `2 Background_level_1/bg_layer_2.png` | 0.18 | Slow buildings |
| Layer 3 | `2 Background_level_1/bg_layer_3.png` | 0.30 | Mid structures |
| Layer 4 (near) | `2 Background_level_1/bg_layer_4.png` | 0.50 | Faster, near-field elements |

| Detail | Value |
|--------|-------|
| **Status** | 🔧 (needs Bug 4.6 fix — brace issue, then Upgrade 5.6 wiring) |
| **System class** | `animation/ParallaxSystem.java` |
| **Layer class** | `animation/ParallexLayer.java` |
| **Update method** | `ParallaxSystem.updateCamera(cameraX)` → offset per layer = `cameraX * depth` |
| **Draw method** | `ParallaxSystem.render(g, screenW, screenH)` → tiles image horizontally |
| **What marker sees** | Background layers slide at visibly different speeds as player moves — classic parallax |

---

## 7. FULL ASSET INVENTORY USED

### Characters (32 animation sprite sheets actively used)
```
characters/playable/Biker/     → PlayerBase BIKER mode  (8 states)
characters/playable/Punk/      → PlayerBase PUNK mode   (8 states)
characters/playable/Cyborg/    → PlayerBase CYBORG mode (8 states)

characters/enemies/drones/1/   → UFO Saucer             (idle, traverse, attack, destroy)
characters/enemies/drones/2/   → Jet Drone              (flight, bomb-drop)
characters/enemies/drones/6/   → Hover Platform         (advance, attack)
characters/enemies/sci-fi-antagonists/2/  → Armoured Knight (idle, walk, attack×4, hurt, death)
characters/enemies/sci-fi-antagonists/3/  → Winged Warrior  (idle, walk, attack×4, death)
```

### Tiles (level-specific tile collections)
```
1 Tiles/Industrial_zone_level_1/1 Tiles/    → Level 1 floor, platforms, walls
1 Tiles/Industrial_zone_level_1/2 Background_level_1/  → 5 parallax layers
1 Tiles/Industrial_zone_level_1/3 Objects/  → crates, barrels, signs
1 Tiles/Industrial_zone_level_1/4 Animated objects/  → animated platforms

1 Tiles/power-station-level-2/1 Tiles/      → Level 2 tiles
1 Tiles/power-station-level-2/2 Background_level_2/Day/  → Day parallax layers
1 Tiles/power-station-level-2/2 Background_level_2/Night/ → Night variant
```

### VFX
```
vfx/1 Smoke/   → 14-frame smoke animation (Death + hit effects)
vfx/2 Blood/   → Blood splat on melee hit
vfx/3 Sparks/  → Spark burst on bullet impact
vfx/4 Particles/ → Particle system
vfx/5 Other/   → Impact bursts
vfx/6 Extra/Character/ → Character-specific VFX
```

### Audio
```
audio/music_midi/Track 1.mid → Gameplay level 1 music
audio/music_midi/Track 2.mid → Gameplay level 2 music
audio/music_midi/Track 3.mid → Boss fight music
audio/music_midi/Track 4.mid → Main menu music
audio/music_midi/Track 5.mid → Game over music

audio/sfx/._Explosion.wav         → Enemy death, projectile hit
audio/sfx/._Karateka_attack.wav   → Player melee attack
audio/sfx/._Laser_sword_1.wav     → Weapon SFX
audio/sfx/._Hovering_robot_sting.wav → Drone enemy proximity alert
audio/sfx/._Hovering_robot_walk_loopable.wav → Drone patrol ambient
audio/sfx/._Jump.wav              → Player jump (if exists, else Portal_1)
audio/sfx/._Bell_on_the_door.wav  → Checkpoint trigger
audio/sfx/._Melody_of_the_win.wav → Level complete
audio/sfx/._Samurai_death.wav     → Player death
audio/music_wav/._Battle_theme_Chinese_Street.wav → WAV music alternative
```

### GUI
```
gui/1 Frames/   → HUD frame borders
gui/2 Bars/     → Health bar, energy bar sprites
gui/3 Icons/    → Ability icons, keyboard key indicators
gui/4 Palette/  → Colour palette swatches
gui/5 Logo/     → Game logo
gui/6 Buttons/  → UI button sprites (glass, metal, neon variants)
gui/7 Numbers/  → Sprite-based score digits
gui/8 Cursors/  → Custom cursor sprite
```

### Fonts
```
Resources/industrial-zone/fonts/*.otf  → HUD font, menu font
(loaded via Font.createFont(Font.TRUETYPE_FONT, new File(path)))
```

---

## 8. BUILD & RUN COMMANDS

All commands run from the `handout/` directory as the working directory. Open a terminal or command prompt, `cd` into `handout/`, then use the commands below.

---

### 8.1 Primary Commands

```batch
REM ─────────────────────────────────────────────
REM  COMPILE — full project from Game.java entry
REM  javac follows -sourcepath to resolve imports
REM ─────────────────────────────────────────────
javac -encoding UTF-8 -cp . -d bin -sourcepath src src/Game.java

REM ─────────────────────────────────────────────
REM  RUN GAME
REM ─────────────────────────────────────────────
java -cp bin Game

REM ─────────────────────────────────────────────
REM  COMPILE + RUN in one step (uses RUN_GAME.bat)
REM ─────────────────────────────────────────────
RUN_GAME.bat

REM ─────────────────────────────────────────────
REM  COMPILE visual test harness only
REM ─────────────────────────────────────────────
javac -encoding UTF-8 -cp . -d bin -sourcepath src src/tests/InteractiveGameTester.java

REM ─────────────────────────────────────────────
REM  RUN visual test harness (7-tab tester)
REM ─────────────────────────────────────────────
java -cp bin tests.InteractiveGameTester
```

**Flag explanations:**

| Flag | Meaning |
|------|---------|
| `-encoding UTF-8` | Prevents garbled output on Windows codepage 1252 |
| `-cp .` | Classpath = current directory (finds `game2D/` source directly) |
| `-d bin` | Place `.class` files into `bin/` folder |
| `-sourcepath src` | javac can find source files anywhere under `src/` when resolving imports |
| `src/Game.java` | Entry point — javac pulls in everything reachable from here |

---

### 8.2 Incremental Recompile (faster — only recompile what changed)

```batch
REM Recompile a single package (e.g. after editing animation files):
javac -encoding UTF-8 -cp . -d bin -sourcepath src src/animation/*.java

REM Recompile a single file:
javac -encoding UTF-8 -cp bin;. -d bin src/entities/PlayerBase.java
REM Note: -cp bin;. puts existing .class files on the classpath so javac can
REM       resolve already-compiled dependencies
```

---

### 8.3 Clean Build (delete all .class files and recompile from scratch)

```batch
REM Windows:
rmdir /s /q bin
mkdir bin
javac -encoding UTF-8 -cp . -d bin -sourcepath src src/Game.java

REM Or use PowerShell:
Remove-Item -Recurse -Force bin ; New-Item -ItemType Directory bin
javac -encoding UTF-8 -cp . -d bin -sourcepath src src/Game.java
```

---

### 8.4 Troubleshooting Common Compile Errors

| Error message | Root cause | Fix |
|---------------|-----------|-----|
| `error: package game2D does not exist` | Wrong working directory — javac cannot see `src/game2D/` | `cd handout` first, then run javac |
| `error: cannot find symbol: class SoundEffect` | `AudioLibrary.java` uses `AudioSystem.SoundEffect` instead of `utilities.SoundEffect` | Apply Bug 4.2 fix |
| `error: AnimationAndSpriteLoader is not abstract…` | `AnimationAndSpriteLoader extends GameCore` but doesn't implement abstract methods | Apply Bug 4.5 fix |
| `error: reached end of file while parsing` | Missing closing `}` in `ParallaxSystem.java` | Apply Bug 4.6 fix |
| `error: MidiTuner() has private access` | `MidiTuner` only has a parameterised constructor | Add no-arg constructor — Bug 4.3 fix |
| `error: package managers.utils does not exist` | `GameLoop.java` imports a package that was renamed or never existed | Apply Bug 4.4 fix |
| `unmappable character for encoding UTF-8` | Source file saved in non-UTF-8 encoding on Windows | Re-save file as UTF-8 or add `-encoding windows-1252` flag |
| `OutOfMemoryError: Java heap space` (compile) | Too many large assets referenced at compile time | Add `-J-Xmx512m` flag: `javac -J-Xmx512m ...` |
| `error: class Game is public, should be in file Game.java` | File renamed or `public class` name does not match filename | Ensure `Game.java` contains `public class Game` |
| `error: duplicate class: entities.Level1` | Two files both declare `class Level1` in the same package | Check for copies in `13_Duplicates/` — delete the duplicate |

---

### 8.5 Runtime Troubleshooting

| Symptom | Likely cause | Fix |
|---------|-------------|-----|
| Window opens but is blank / black | `draw()` is not called, or `GameCore.startGame()` was not called in constructor | Ensure constructor ends with `startGame()` |
| Player visible but frozen (no movement) | `keyPressed()` body is empty — Bug 4.7 | Implement `keyPressed()` forwarding |
| `NullPointerException` at `draw(Graphics2D)` | An asset (`tileMap`, `player`, `parallax`) was not loaded before first draw | Move asset loading before `startGame()` call |
| No sound at all | `SoundEffect` uses wrong API or WAV paths have `._` prefix mismatch | Apply Bugs 4.1 + 4.2 + 4.8 fixes |
| MIDI does not start | `MidiTuner` constructor throws exception | Apply Bug 4.3 fix; check `javax.sound.midi` availability |
| Game runs at ~5 FPS | Drawing code is doing file I/O every frame | Move all `ImageIO.read()` calls to `loadAssets()`, store results in fields |
| Assets not found (FileNotFoundException) | Relative path wrong — game launched from wrong directory | Must run `java -cp bin Game` from `handout/` directory |

---

---

## 9. IMPLEMENTATION PRIORITY QUEUE

Ordered by broken-dependency impact. Fix Phase 1 first — everything else is blocked until those 6 compile errors are gone. **Total estimated effort: ~10 hours focused work.**

> **Dependency rule:** An arrow `→` means "must finish before starting." A task without an arrow can be started any time after its phase begins.

---

### PHASE 1 — Fix Compilation Blockers (~2 hours total)

Fix these in order — each fix unblocks the next file in the chain.

| # | Priority | File | Fix required | Estimated time | Verify by | Unblocks |
|---|----------|------|-------------|---------------|-----------|---------|
| 1 | 🔴 P1 | `utilities/SoundEffect.java` | Rewrite using `javax.sound.sampled` API (see Bug 4.1) | 20 min | `javac … src/utilities/SoundEffect.java` exits with 0 errors | `AudioLibrary`, `AudioManager`, all audio |
| 2 | 🔴 P1 | `utilities/AudioLibrary.java` | Change `AudioSystem.SoundEffect(…)` → `new SoundEffect(…)` (see Bug 4.2) | 10 min | `javac … src/utilities/AudioLibrary.java` exits with 0 errors | `AudioManager.initialize()` |
| 3 | 🔴 P1 | `utilities/MidiTuner.java` | Add `public MidiTuner() { this("", PLAY_ONCE); }` (see Bug 4.3) | 5 min | `new MidiTuner()` compiles; run: `audioManager.playMidi(track1, true)` — MIDI starts | `AudioManager` constructor |
| 4 | 🔴 P1 | `managers/GameLoop.java` | Change import from `managers.utils.MathUtils` to `managers.MathUtils` (see Bug 4.4) | 5 min | `javac … src/managers/GameLoop.java` exits with 0 errors | All `managers/` package |
| 5 | 🔴 P1 | `animation/AnimationAndSpriteLoader.java` | Remove `extends GameCore` from class declaration (see Bug 4.5) | 10 min | `javac … src/animation/AnimationAndSpriteLoader.java` exits with 0 errors | `Level1`, `Level2`, `Game` |
| 6 | 🔴 P1 | `animation/ParallaxSystem.java` | Add the two missing closing braces at end-of-file (see Bug 4.6) | 5 min | `javac … src/animation/ParallaxSystem.java` exits with 0 errors | Parallax rendering |

**Phase 1 completion check:**
```batch
javac -encoding UTF-8 -cp . -d bin -sourcepath src src/Game.java
```
This should produce **0 errors**. If errors remain, consult Section 4 for the specific bug matching the error message. Phase 2 must not start until this command is clean.

---

### PHASE 2 — Wire Core Game Systems (~3 hours total)

These tasks can be done in parallel once Phase 1 is complete. Do `Game.java` upgrades first since they tie everything together.

| # | Priority | File | Action | Estimated time | Verify by | Dependency |
|---|----------|------|--------|---------------|-----------|------------|
| 7 | 🟠 P2 | `Game.java` | Add real `keyPressed()` / `keyReleased()` bodies forwarding to `PlayerBase` (Upgrade 5.1 step 1) | 15 min | Run game, press A/D — player sprite moves left/right | Needs Phase 1 done |
| 8 | 🟠 P2 | `Game.java` | Add `MouseAdapter` + `MouseMotionAdapter` in constructor (character select + pause) (Upgrade 5.15 step 4) | 20 min | Character select screen shows on launch; clicking portrait starts game | Requires task 7 |
| 9 | 🟠 P2 | `Game.java` | Instantiate `AudioManager`, call `initialize()`, call `playMidi(Track 1.mid, true)` in constructor (Upgrade 5.1 step 2) | 15 min | MIDI music starts within 1 second of launch | Requires tasks 1-3 |
| 10 | 🟠 P2 | `Game.java` | Load OTF fonts, draw HUD (score/health/timer) in `draw()` (Upgrades 5.1 step 4,5 + 5.11) | 30 min | HUD appears in custom font in top-left + top-right | Requires tasks 1, 7 |
| 11 | 🟠 P2 | `managers/AudioManager.java` | Replace hard-coded paths with directory scanner (Upgrade 5.4) | 20 min | Console shows `[AudioManager] Loaded N sounds` for N > 0 | Requires tasks 1-3 |
| 12 | 🟠 P2 | `entities/Level1.java` | Replace `AnimationAndSpriteLoader` usage with direct `game2D.TileMap.loadMap()` calls (Upgrade 5.7) | 30 min | Level 1 tile map draws (even if tiles look wrong — just no crash) | Requires task 5 |
| 13 | 🟠 P2 | `entities/Level2.java` | Same as above for Level 2 (Upgrade 5.7) | 20 min | Level 2 tile map draws without crash | Requires task 12 |
| 14 | 🟠 P2 | `maps/level_1/map.txt` | Populate with initial layout: 20×10 grid, `g` = ground tile, `.` = air (Upgrade 5.7) | 20 min | Level 1 shows visible tile floor that the player can stand on | Requires task 12 |
| 15 | 🟠 P2 | `maps/level_2/map.txt` | Populate with level 2 layout (different shape from level 1) | 20 min | Level 2 looks different from Level 1 when switching | Requires task 13 |

**Phase 2 completion check:**
```batch
java -cp bin Game
```
- MIDI music starts
- Character select screen shows with 3 portraits
- Clicking a portrait loads the game
- Player sprite moves on A/D/SPACE
- HUD displays score, health, timer in custom font

---

### PHASE 3 — Enhance Game Mechanics (~3 hours total)

| # | Priority | Component | Action | Estimated time | Verify by | Dependency |
|---|----------|-----------|--------|---------------|-----------|------------|
| 16 | 🟡 P3 | `animation/ParallaxSystem.java` | Wire 5 background layers to Level 1 and Level 2 inside `Game.loadAssets()` (Upgrade 5.6) | 30 min | Run game — background layers scroll at different speeds when player moves | Requires task 6 |
| 17 | 🟡 P3 | `physics/CollisionDetector.java` | Register player bounding box; check player vs tile + player vs enemy in `update()` (Upgrade 5.5) | 45 min | Player cannot fall through floor tiles; enemy contact deals damage | Requires tasks 12-14 |
| 18 | 🟡 P3 | `entities/Enemies.java` | Add `updatePatrol()` + `updateChase()` to `EnemyInstance`; trigger attack when within range (Upgrade 5.3) | 45 min | Enemies walk left/right patrol path, speed up and follow player when close | Requires task 17 |
| 19 | 🟡 P3 | `ai/EnemyAI.java` | Create `AI.AIPathfinder`, add 2 waypoints per enemy, call `brain.executeBehavior(dt)` in `update()` (Upgrade 5.8) | 30 min | Enemies reverse direction at waypoints; drone approaches player when ALERT | Requires task 18 |
| 20 | 🟡 P3 | `utilities/EchoFilter.java` | Create full file with ring-buffer echo algorithm (Upgrade 5.16) | 30 min | `audioManager.playSFXWithEcho("explosion")` plays sound + echo; console shows `[EchoFilter] Finished:` | Requires tasks 1-3 |
| 21 | 🟡 P3 | `entities/PlayerBase.java` | Wire game2D.Animation state machine; add `setState(AnimState)` (Upgrade 5.2) | 45 min | Player sprite changes animation frames visibly on walk/jump/attack | Requires tasks 12, 16 |

**Phase 3 completion check:**
- Parallax background scrolls (5 layers at different speeds)
- Player collides with floor tiles (does not fall through)
- Enemies patrol and chase player
- Explosion sound has audible echo effect

---

### PHASE 4 — Polish + Assignment Completeness (~2 hours total)

| # | Priority | Feature | Action | Estimated time | Verify by | Dependency |
|---|----------|---------|--------|---------------|-----------|------------|
| 22 | 🟢 P4 | Score system | Instantiate `ScoreManager`, award points on enemy death, display in HUD (Upgrade 5.10) | 20 min | Kill an enemy — score number in top-left corner increases | Requires tasks 10, 18 |
| 23 | 🟢 P4 | Pause menu | `paused = !paused` on ESC; skip `update()` + draw semi-transparent overlay (Upgrade 5.12) | 25 min | Press ESC — game freezes, "PAUSED" overlay appears; press again — resumes | Requires task 7 |
| 24 | 🟢 P4 | Game Over screen | On `player.health <= 0`: stop loop, draw Game Over with score (Upgrade 5.13) | 25 min | Let player die — Game Over screen shows score and kill count | Requires tasks 17, 22 |
| 25 | 🟢 P4 | Level transition | Add exit X trigger in `update()`; call `switchLevel(2)` when reached (Upgrade 5.14) | 20 min | Walk player to far-right of Level 1 — Level 2 loads with new tiles + new MIDI | Requires tasks 12-15 |
| 26 | 🟢 P4 | Character select | Draw portrait screen before game starts; mouse click sets `selectedCharacter` (Upgrade 5.15) | 30 min | Game opens to select screen; clicking Cyborg portrait starts game with Cyborg sprite | Requires tasks 8, 21 |
| 27 | 🟢 P4 | Smoke VFX on death | On `enemy.isDead()`: spawn 14-frame smoke animation at enemy position from `vfx/1 Smoke/` | 20 min | Enemy dies → puff of smoke appears at death location, then disappears after ~0.5s | Requires task 18 |

**Phase 4 completion check (full assignment checklist):**

```
[ ] Player-controlled sprite moves with keyboard input
[ ] Player sprite changes animation on state change (walk/jump/attack/death)
[ ] Multiple distinct enemy types on screen, each animated
[ ] Enemies chase + attack player
[ ] Collision detection prevents falling through tiles
[ ] SFX plays on player jump, attack, death
[ ] Echo filter audible on explosion/attack sounds
[ ] MIDI music plays continuously in background
[ ] MIDI track changes when switching level
[ ] Two distinct tiled levels with different art
[ ] Parallax background scrolling in both levels
[ ] HUD shows: score, kills, level, timer
[ ] Pause menu on ESC
[ ] Game Over screen on player death
[ ] Level transition from Level 1 to Level 2
[ ] Character select screen at launch
[ ] Mouse hover + click registered on menus
```

---

### 9.1 Cumulative Time Budget

| Phase | Work hours | What you get |
|-------|-----------|-------------|
| Phase 1 | ~2 h | Game compiles cleanly — can run `java -cp bin Game` without errors |
| Phase 2 | ~3 h | Playable game: player moves, MIDI plays, tiles draw, HUD visible |
| Phase 3 | ~3 h | Complete mechanics: parallax, collision, enemy AI, echo filter |
| Phase 4 | ~2 h | Polished & submission-ready: all 17 checklist items ticked |
| **Total** | **~10 h** | Full assignment-ready game |

---

### 9.2 Dependency Graph (Phase 1 → 2 → 3 → 4)

```
Bug 4.1 (SoundEffect) ──┬──► Bug 4.2 (AudioLibrary) ──► AudioManager (task 11)
                         └──► EchoFilter (task 20)

Bug 4.3 (MidiTuner)  ──► AudioManager (task 9) ──► MIDI music

Bug 4.4 (GameLoop)   ──► managers/ package ──► ScoreManager (task 22)

Bug 4.5 (AnimLoader) ──► Level1/Level2 (tasks 12/13) ──► TileMaps (tasks 14/15)
                                                       └──► Level transition (task 25)

Bug 4.6 (Parallax)   ──► ParallaxSystem wiring (task 16)

Task 7  (keyPressed) ──► Player moves ──► Collision (task 17) ──► Enemy AI (tasks 18/19)
                                       └──► AnimState (task 21) ──► VFX (task 27)
                      └──► Pause menu (task 23) ──► Game Over (task 24)

Task 10 (HUD)        ──► Score display (task 22)
Task 8  (MouseAdapt) ──► Character select (task 26)
```

---

## APPENDIX: Key Code Bridges

### Bridge 1: game2D.Animation ← HorizontalSpritesheetLoader

```java
// Used in PlayerBase + Enemies to build a game2D.Animation from a spritesheet
public static game2D.Animation toGameAnimation(HorizontalSpritesheetLoader hsl) {
    game2D.Animation anim = new game2D.Animation();
    for (int i = 0; i < hsl.getFrameCount(); i++) {
        anim.addFrame(hsl.getFrame(i), hsl.getMsPerFrame());
    }
    if (!hsl.isLoop()) anim.setLoop(false);
    return anim;
}
```

### Bridge 2: game2D.Sound → SFX playback

```java
// Quick fire-and-forget WAV via game2D.Sound (no controls):
Sound s = new Sound("Resources/industrial-zone/audio/sfx/._Explosion.wav");
s.start();  // plays in background thread, auto-closes

// Controlled WAV via SoundEffect (volume, pan):
SoundEffect se = new SoundEffect("explosion",
    "Resources/industrial-zone/audio/sfx/._Explosion.wav");
se.setVolume(0.8f);
se.play();
```

### Bridge 3: game2D.TileMap for levels

```java
TileMap map = new TileMap();
boolean ok = map.loadMap("maps/level_1", "map.txt");
if (ok) {
    // In draw():
    map.draw(g, -(int)cameraX, -(int)cameraY);
}
// Tile collision: check tile character at player position
Tile t = map.getTileAt(col, row);
if (t != null && t.getCharacter() != '.') {
    // solid tile — resolve overlap
}
```

### Bridge 4: OTF font loading

```java
// ONE-TIME in init:
Font customFont = Font.createFont(Font.TRUETYPE_FONT,
    new File("Resources/industrial-zone/fonts/SomeFont.otf"))
    .deriveFont(Font.BOLD, 18f);
// Register with graphics environment so Swing picks it up:
GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);

// In draw():
g.setFont(customFont);
g.setColor(Color.WHITE);
g.drawString("SCORE: " + score, 20, 40);
```

---

---

*End of Appendix A.*

---

## APPENDIX B: EchoFilter — Full Reference Implementation

This appendix provides the complete stand-alone `utilities/EchoFilter.java` class exactly as it should appear in the source tree, with comprehensive inline comments explaining every line for the report/viva.

```java
package utilities;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * EchoFilter — Novel digital audio effect (delay-line echo).
 *
 * MATHEMATICAL MODEL
 * ──────────────────
 * The echo is produced by a single-tap delay line:
 *
 *   y[n] = x[n] + alpha * x[n - D]
 *
 *   where:
 *     x[n]     = input PCM sample at index n
 *     y[n]     = output (echoed) PCM sample at index n
 *     D        = delay in samples = (delayMs / 1000) * sampleRate * channels
 *     alpha    = echo attenuation factor, [0.0, 1.0]
 *
 * IMPLEMENTATION
 * ──────────────
 * A circular (ring) buffer of length D holds the most recent D input samples.
 * At each step:
 *   1. Read the value stored D positions ago (= x[n - D])
 *   2. Compute y[n] = x[n] + alpha * x[n - D]
 *   3. Clamp y[n] to [-32768, 32767] (16-bit signed PCM range)
 *   4. Store x[n] at the current ring-buffer position
 *   5. Advance the ring-buffer write pointer (mod D)
 *
 * This runs entirely in a background thread and uses no third-party library —
 * only the standard javax.sound.sampled API.
 *
 * ASSIGNMENT NOTE
 * ───────────────
 * This satisfies the "novel digital audio effect" requirement because:
 *   - It implements a DSP algorithm (delay-line echo) from scratch
 *   - It operates directly on raw PCM byte data (not a pre-built effect)
 *   - It uses SourceDataLine for real-time streaming playback
 *   - The echo parameters (delay, volume) are configurable at construction time
 */
public class EchoFilter {

    /** Echo delay in milliseconds. Typical range: 100–500 ms. */
    private final int   delayMs;

    /**
     * Echo volume multiplier, clamped to [0.0, 1.0].
     * 0.0 = no echo, 1.0 = full echo (same volume as original — can sound harsh).
     * Recommended: 0.4–0.6 for a natural reverb-like effect.
     */
    private final float alpha;

    /**
     * Construct an EchoFilter with custom delay and attenuation.
     *
     * @param delayMs  echo delay in milliseconds (e.g. 250)
     * @param alpha    echo volume multiplier in [0.0, 1.0] (e.g. 0.5)
     */
    public EchoFilter(int delayMs, float alpha) {
        this.delayMs = delayMs;
        this.alpha   = Math.max(0f, Math.min(1f, alpha));   // clamp alpha
    }

    /**
     * Play a WAV file through this echo filter.
     * Processing happens on a NEW background thread — this method returns immediately.
     * The caller does NOT need to wait; the thread handles playback start-to-finish.
     *
     * @param filePath  absolute or relative path to a 16-bit PCM WAV file
     */
    public void play(final String filePath) {
        Thread t = new Thread(() -> {
            try {
                // ── Step 1: Open the source WAV file ──────────────────────────────
                AudioInputStream rawStream =
                        AudioSystem.getAudioInputStream(new File(filePath));
                AudioFormat fmt = rawStream.getFormat();

                // ── Step 2: Validate — only 16-bit signed PCM is supported ────────
                if (fmt.getSampleSizeInBits() != 16 ||
                    fmt.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                    System.err.printf(
                        "[EchoFilter] %s: must be 16-bit signed PCM, got %s%n",
                        filePath, fmt.getEncoding());
                    // Graceful fallback — play without effect
                    new game2D.Sound(filePath).start();
                    return;
                }

                // ── Step 3: Build the delay ring buffer ───────────────────────────
                // D = samples needed to represent delayMs milliseconds.
                // Multiply by channels so stereo audio delays both channels correctly.
                int delaySamples = (int)(fmt.getSampleRate() * delayMs / 1000.0)
                                   * fmt.getChannels();
                short[] ringBuf  = new short[delaySamples];   // initialised to 0
                int     ringPos  = 0;                          // write head, wraps mod D

                // ── Step 4: Open output (SourceDataLine) ──────────────────────────
                DataLine.Info lineInfo =
                        new DataLine.Info(SourceDataLine.class, fmt);
                if (!AudioSystem.isLineSupported(lineInfo)) {
                    System.err.println("[EchoFilter] SourceDataLine not supported");
                    return;
                }
                SourceDataLine outLine = (SourceDataLine) AudioSystem.getLine(lineInfo);
                outLine.open(fmt);       // allocate native audio buffer
                outLine.start();         // signal: ready to receive data

                // ── Step 5: Process audio in 4 KB chunks ─────────────────────────
                final int CHUNK = 4096;
                byte[] inBuf  = new byte[CHUNK];
                byte[] outBuf = new byte[CHUNK];
                int    nRead;

                while ((nRead = rawStream.read(inBuf, 0, inBuf.length)) != -1) {
                    // Iterate over each 16-bit sample (2 bytes each, little-endian)
                    for (int i = 0; i + 1 < nRead; i += 2) {
                        // Reconstruct signed 16-bit integer from two bytes
                        //   low byte:  inBuf[i]   (mask with 0xFF to prevent sign extension)
                        //   high byte: inBuf[i+1]  (shift left 8 — already signed)
                        short x = (short)((inBuf[i+1] << 8) | (inBuf[i] & 0xFF));

                        // Apply delay-line echo formula:
                        //   y[n] = x[n] + alpha * ring[ringPos]
                        // ring[ringPos] contains the sample that was written D steps ago
                        float y_f = x + alpha * ringBuf[ringPos];

                        // Clamp to valid 16-bit PCM range (prevent clipping distortion)
                        short y = (short) Math.max(Short.MIN_VALUE,
                                           Math.min(Short.MAX_VALUE, (long) y_f));

                        // Store current INPUT sample into ring (not the output!)
                        // This preserves the dry signal in the delay buffer.
                        ringBuf[ringPos] = x;

                        // Advance ring-buffer write head (wraps circularly)
                        ringPos = (ringPos + 1) % delaySamples;

                        // Pack processed sample back into output byte array (little-endian)
                        outBuf[i]   = (byte)( y        & 0xFF);   // low byte
                        outBuf[i+1] = (byte)((y >>> 8) & 0xFF);   // high byte
                    }
                    // Send processed chunk to the audio hardware
                    outLine.write(outBuf, 0, nRead);
                }

                // ── Step 6: Flush + close ─────────────────────────────────────────
                outLine.drain();    // wait until all buffered data is played
                outLine.close();    // release native audio resources
                rawStream.close();  // close file stream
                System.out.printf("[EchoFilter] Done: %s (delay=%dms alpha=%.2f)%n",
                                  filePath, delayMs, alpha);

            } catch (UnsupportedAudioFileException e) {
                System.err.println("[EchoFilter] Unsupported format: " + e.getMessage());
            } catch (LineUnavailableException e) {
                System.err.println("[EchoFilter] Audio line unavailable: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("[EchoFilter] I/O error: " + e.getMessage());
            }
        }, "EchoFilter-Thread");

        t.setDaemon(true);   // don't prevent JVM exit if main window is closed
        t.start();
    }

    // ── Convenience API ──────────────────────────────────────────────────────

    /** Returns an EchoFilter with 250 ms delay and 50% echo volume. */
    public static EchoFilter defaultEcho() {
        return new EchoFilter(250, 0.5f);
    }

    /** Returns an EchoFilter with 80 ms delay and 35% echo (short reverb-like effect). */
    public static EchoFilter shortReverb() {
        return new EchoFilter(80, 0.35f);
    }

    /**
     * Static one-liner: load default echo settings and play the file.
     * Equivalent to {@code new EchoFilter(250, 0.5f).play(filePath)}.
     */
    public static void playWithEcho(String filePath) {
        defaultEcho().play(filePath);
    }
}
```

### B.1 How to integrate EchoFilter with AudioManager

```java
// In managers/AudioManager.java — add the field:
private final EchoFilter echoFilter = EchoFilter.defaultEcho();

// Play a loaded SFX with echo:
public void playSFXWithEcho(String key) {
    SoundEffect se = sfxLibrary.getSound(key);
    if (se == null) {
        System.err.println("[Audio] SFX not found: " + key);
        return;
    }
    echoFilter.play(se.getFilePath());   // background thread — non-blocking
}

// Alternative: short reverb variant for hit sounds:
public void playSFXWithReverb(String key) {
    SoundEffect se = sfxLibrary.getSound(key);
    if (se != null) EchoFilter.shortReverb().play(se.getFilePath());
}
```

### B.2 Call sites in Game.java

```java
// Enemy death — explosion with echo
for (EnemyFactory.EnemyInstance e : deadEnemies) {
    audioManager.playSFXWithEcho("explosion");   // echo effect
}

// Player melee hit — short reverb
audioManager.playSFXWithReverb("karateka_attack");
```

### B.3 What to say in the report / viva

> "The `EchoFilter` class implements a single-tap delay-line echo effect using the `javax.sound.sampled` API. It reads WAV PCM data in 4 KB chunks and, for each 16-bit sample, mixes the current sample with an attenuated version of the sample that was recorded $D$ samples earlier, where $D = (\text{delayMs}/1000) \times \text{sampleRate}$. A circular ring buffer of length $D$ stores the most recent input samples. The algorithm is: $y[n] = x[n] + \alpha \cdot x[n-D]$. No third-party DSP library was used — the effect is implemented entirely in Java standard library code operating on raw PCM bytes."

---

## APPENDIX C: InteractiveGameTester — Architecture and Usage

`tests/InteractiveGameTester.java` is a standalone visual test harness that lets you browse assets, test rendering, and verify audio **without launching the full game**. It supports 7 tabs, each testing a different subsystem.

### C.1 How to Compile and Run

```batch
REM From handout/ directory:
javac -encoding UTF-8 -cp . -d bin -sourcepath src src/tests/InteractiveGameTester.java
java -cp bin tests.InteractiveGameTester
```

### C.2 Architecture Overview

```
InteractiveGameTester extends JFrame implements KeyListener
│
├── JButton[] tabBtns = new JButton[7]   ← tab bar at the top
├── GameCanvas canvas (extends JPanel)   ← main render area
│     └── paintComponent(g) calls tab-specific draw method
├── JLabel statusBar                     ← shows current state info at bottom
├── HashSet<Integer> keysDown           ← tracks held keys
├── int activeTab                        ← which tab is selected (0–6)
│
└── javax.swing.Timer (16 ms)           ← drives canvas.tick() + repaint()
      ├── canvas.tick()   → updates state (animation frame, camera, etc.)
      └── canvas.repaint() → triggers paintComponent()
```

**Asset loading:** The constructor starts a **background daemon thread** that calls `loadAssets()`. The EDT continues immediately (window opens). The status bar shows "Loading assets…" until the thread completes and sets `assetsLoaded = true`.

### C.3 The Seven Tabs

| Tab | Label | What it tests | Key bindings | How to read the result |
|-----|-------|--------------|--------------|----------------------|
| 0 | 🌅 Parallax | 5-layer parallax background from Level 1 and Level 2 | ← → to scroll (or hold A/D) | All 5 layers visible; layers closer to back move slower |
| 1 | 🎮 Player | All 3 characters (Biker, Punk, Cyborg) with 8 animation states | 1/2/3 to switch character; W/A/S/D to change state; SPACE resets | No missing frames; state name printed in status bar |
| 2 | 🔫 Weapons | Weapon sprite sheets (guns, bullet trails) | Mouse hover shows weapon name; click cycles through variants | Every weapon frame loads without placeholder colour |
| 3 | 👾 Enemies | All 4 enemy types with their full animation sets | ← → to cycle enemy; UP/DOWN to cycle animation state | Each enemy animates continuously; death has smoke VFX |
| 4 | 🧱 Tiles | Both level tile sets rendered as a grid | PAGE_UP/DOWN to switch level; WASD camera pan | Every tile in the tile sheet renders (no black squares) |
| 5 | 💥 VFX | Smoke, blood splat, spark burst, particle emitter | Click anywhere to spawn VFX at that position | Each VFX plays its full frame sequence then disappears |
| 6 | 🔊 Sound | List of all loaded SFX keys; MIDI playback | Click key name to play SFX; buttons for MIDI Track 1–5; ECHO toggle | Console shows `[Audio]` log; echo adds audible delay to SFX |

### C.4 Path Constants in InteractiveGameTester

```java
private static final String RES      = "Resources/industrial-zone/";
private static final String BG_L1    = RES + "1 Tiles/Industrial_zone_level_1/2 Background_level_1/";
private static final String BG_L2    = RES + "1 Tiles/power-station-level-2/2 Background_level_2/Day/";
private static final String BIKER    = RES + "characters/playable/Biker/";
private static final String PUNK     = RES + "characters/playable/Punk/";
private static final String CYBORG   = RES + "characters/playable/Cyborg/";
private static final String DRONE    = RES + "characters/enemies/drones/1/";
private static final String TILES1   = RES + "1 Tiles/Industrial_zone_level_1/1 Tiles/";
private static final String SMOKE    = RES + "vfx/1 Smoke/";
private static final String SPARKS   = RES + "vfx/3 Sparks/";
private static final String GUNS     = RES + "weapons/";
private static final String BULLETS  = RES + "weapons/";
private static final String FX       = RES + "vfx/";
private static final String MIDI_DIR = RES + "audio/music_midi/";
```

All paths are relative to `handout/`.

### C.5 Colour System

```java
private static final Color C_BG     = new Color(18,  18,  28);   // very dark navy
private static final Color C_PANEL  = new Color(30,  30,  46);   // sidebar background
private static final Color C_ACCENT = new Color(80, 160, 255);   // bright blue highlight
private static final Color C_WARN   = new Color(255, 80,  80);   // red warning
private static final Color C_TEXT   = new Color(210, 210, 220);  // near-white text
private static final Color C_TABSEL = new Color(60, 120, 200);   // selected tab background
private static final Color C_TABIDLE= new Color(40,  40,  60);   // unselected tab background
```

### C.6 Known Limitations of the Test Harness

| Limitation | Detail |
|-----------|--------|
| Assets must be in `handout/Resources/` | The tester uses hard-coded paths relative to the `handout/` directory. Run from there. |
| Only tests rendering + audio, not physics | Collision detection is not tested here — use the full game for that. |
| 16ms timer ≠ fixed DT | Tab 0 (parallax) and Tab 1 (player) use frame-count-based timing, not delta-time, so animations may run slightly differently from the real game loop. |
| Sound requires `._`-prefixed WAV files | On Windows, the assets were originally created on macOS. The `._` prefix is on the actual playable file. Tab 6 (Sound) will show 0 sounds loaded if the WAV scanner doesn't handle this. |

---

*End of implementation plan. 27 numbered tasks across 4 phases. Total estimated effort: 10 hours. All 17 assignment checklist items accounted for.*
