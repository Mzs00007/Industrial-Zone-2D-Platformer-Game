# 📚 COMPLETE JAVA PROJECT INDEX - N6 Assignment Code

**Last Updated:** April 3, 2026

---

## 📋 TABLE OF CONTENTS

1. [Project Overview](#project-overview)
2. [Quick Statistics](#quick-statistics)
3. [Directory Structure](#directory-structure)
4. [Root Directory Files](#root-directory-files)
5. [Core Game Classes](#core-game-classes)
6. [Animation & Sprite System](#animation--sprite-system-major-module)
7. [Physics Engine](#physics-engine)
8. [AI & Behavior System](#ai--behavior-system)
9. [Rendering Pipeline](#rendering-pipeline)
10. [GUI/UI System](#guiui-system)
11. [Audio System](#audio-system)
12. [Weapons & Combat](#weapons--combat-system)
13. [Visual Effects (VFX)](#visual-effects-vfx)
14. [Tile System](#tile-system)
15. [Utilities & Helpers](#utilities--helpers)
16. [Architecture Overview](#architecture-overview)
17. [How to Use This Index](#how-to-use-this-index)

---

## 🎯 PROJECT OVERVIEW

This is a comprehensive 2D game project built in Java with **318 total Java files** covering:

- **Complete 2D Game Framework** (GameCore extending JFrame)
- **Two Full Levels** (Level 1: Industrial Zone, Level 2: Power Station)
- **Advanced Animation System** with character skins and sprite management
- **Physics Engine** with gravity, collision detection, and velocity
- **Enemy AI** with state machines and behavior trees
- **Complex GUI System** with character selection, HUD, and menus
- **Audio System** with music and sound effects
- **Weapons & Projectiles** system
- **Visual Effects** particle system
- **Tile Adjacency** system for realistic terrain

### Core Technologies:
- **Java 8+** with Swing (JFrame, Graphics2D)
- **Physics System** with realistic gravity and collision
- **Animation States** using enum-based state machines
- **AI Behavior Trees** for intelligent enemy behavior
- **Asset Registry System** for efficient resource management

---

## 📊 QUICK STATISTICS

| Metric | Count |
|--------|-------|
| **Total Java Files** | 318 |
| **Main Classes** | 250+ |
| **Nested Classes** | 200+ |
| **Enums** | 40+ |
| **Interfaces** | 15+ |
| **Test Classes** | 30+ |

### Package Breakdown:
| Package | Files | Purpose |
|---------|-------|---------|
| `src/animation/` | 70+ | Sprite loading, animation states, asset management |
| `src/gui/` | 50+ | Complete UI system with screens and components |
| `src/rendering/` | 30+ | Graphics rendering pipeline |
| `src/physics/` | 20+ | Complete physics simulation engine |
| `src/ai/` | 15+ | Enemy AI behavior and decision making |
| `src/core/` | 15+ | Game state, input, score tracking |
| `src/audio/` | 6 | Music and sound effects |
| `src/vfx/` | 10 | Particle effects and visual effects |
| `src/tiles/` | 5 | Tile registry and tile map system |
| `src/utils/` | 8 | Helper utilities and constants |
| `src/camera/` | 2 | Camera and viewport control |
| Other packages | 70+ | Weapons, combat, entities, optimization, etc. |

---

## 📁 DIRECTORY STRUCTURE

```
handout/
├── Root Level Files (6)
│   ├── CharacterAnimationPhysicsTester.java
│   ├── FrameTilerTest.java
│   ├── GUIMouseClickEffects_IntegrationGuide.java
│   ├── TileRegistry.java
│   ├── TileRegistryGenerator.java
│   └── Additional test files
│
├── src/
│   ├── animation/
│   │   ├── AnimationAndSpriteLoader.java (MAJOR - 40+ nested classes)
│   │   ├── metadata/ (4 classes)
│   │   ├── managers/ (5 classes)
│   │   ├── systems/ (7 classes)
│   │   └── ... (other animation classes)
│   │
│   ├── gui/
│   │   ├── GUIManager.java
│   │   ├── screens/ (30+ screen implementations)
│   │   ├── Button*.java, Bar*.java, Digit*.java
│   │   └── ... (50+ GUI component classes)
│   │
│   ├── rendering/
│   │   ├── RenderingSystem.java
│   │   ├── *Renderer.java (30 renderer classes)
│   │   └── Tile/Background loaders
│   │
│   ├── physics/
│   │   ├── Physics.java (main system)
│   │   ├── PhysicsEngine.java
│   │   ├── PhysicsBody.java
│   │   ├── CollisionDetector.java
│   │   └── ... (20+ physics classes)
│   │
│   ├── ai/
│   │   ├── AI.java (main system)
│   │   ├── EnemyAI.java
│   │   ├── BehaviorTree.java
│   │   ├── *State.java (PatrolState, ChaseState, AttackState)
│   │   └── ... (15+ AI classes)
│   │
│   ├── core/
│   │   ├── Core.java
│   │   ├── GameStateManager.java
│   │   ├── LevelManager.java
│   │   ├── InputHandler.java
│   │   └── ... (15+ core classes)
│   │
│   ├── audio/
│   │   ├── Audio.java (main system)
│   │   ├── SoundManager.java
│   │   ├── MusicIntegrator.java
│   │   └── ... (6 audio classes)
│   │
│   ├── vfx/
│   │   ├── VFXManager.java
│   │   ├── ParticleEmitter.java
│   │   ├── *EffectRenderer.java (multiple)
│   │   └── ... (10 VFX classes)
│   │
│   ├── tiles/
│   │   ├── TileMapSystem.java
│   │   ├── Level1TileRegistry.java
│   │   ├── Level2TileRegistry.java
│   │   └── Level1TileAssetCache.java
│   │
│   ├── camera/
│   │   ├── Camera.java
│   │   └── CameraPackageCoordinator.java
│   │
│   ├── utils/
│   │   ├── Constants.java
│   │   ├── MathHelper.java
│   │   ├── AssetManager.java
│   │   └── ... (8 utility classes)
│   │
│   ├── core_game_entities/
│   │   ├── characters/ (player definitions)
│   │   ├── enemies/ (enemy definitions)
│   │   ├── weapons/ (weapon definitions)
│   │   ├── effects/ (VFX definitions)
│   │   └── ... (13 classes)
│   │
│   ├── game2D/
│   │   ├── GameCore.java (base framework)
│   │   ├── Animation.java
│   │   ├── Sprite.java
│   │   └── ... (7 framework classes)
│   │
│   ├── weapons/
│   │   ├── ProjectileManager.java
│   │   └── WeaponRenderer.java
│   │
│   ├── Level1.java
│   ├── Level2.java
│   ├── Game.java
│   ├── GameWindow.java
│   └── ... (20+ other core files)
│
└── bin/ (compiled classes)
```

---

## 🔧 ROOT DIRECTORY FILES

Files in the root directory (C:\Users\...\handout\):

### 1. **TileRegistryGenerator.java**
- **Path:** `handout/TileRegistryGenerator.java`
- **Main Class:** `TileRegistryGenerator`
- **Nested Classes:** `TileEntry`
- **Purpose:** Generator utility that creates character-based tile symbol mapping system
- **Key Methods:** Static tile registry generation
- **Usage:** Run to generate tile registry mappings from asset directories

### 2. **TileRegistry.java**
- **Path:** `handout/TileRegistry.java`
- **Purpose:** Data structure containing pre-generated tile registry with character code to asset path mappings
- **Usage:** Direct reference for tile asset lookup
- **Key Data:** 65+ tile mappings (A-Z, a-z, 0-9, !@)

### 3. **CharacterAnimationPhysicsTester.java**
- **Path:** `handout/CharacterAnimationPhysicsTester.java`
- **Main Class:** `CharacterAnimationPhysicsTester`
- **Purpose:** Comprehensive testing suite for character animation, physics interactions, and collision
- **Usage:** Standalone test application for verifying animation and physics systems work together
- **Features:** Real-time physics visualization, animation frame cycling, collision testing

### 4. **FrameTilerTest.java**
- **Path:** `handout/FrameTilerTest.java`
- **Main Class:** `FrameTilerTest`
- **Purpose:** Test harness for GUI frame tiling functionality
- **Usage:** Tests that frame borders tile correctly for GUI panels

### 5. **GUIMouseClickEffects_IntegrationGuide.java**
- **Path:** `handout/GUIMouseClickEffects_IntegrationGuide.java`
- **Main Class:** `GUISparkEffectsIntegration`
- **Purpose:** Integration guide showing how to add spark effect particles on mouse clicks
- **Usage:** Reference code for implementing click feedback effects

---

## 🎮 CORE GAME CLASSES

These are the main entry points and core game controllers:

### **Game.java**
- **Path:** `src/Game.java`
- **Main Class:** `Game`
- **Purpose:** Main game controller that delegates to Level1/Level2 and GUI systems
- **Key Methods:**
  - `startGame()` - Initializes and starts gameplay
  - `switchLevel(int levelNumber)` - Changes between Level 1 and Level 2
  - `pauseGame()` - Pauses active game
  - `update(double deltaTime)` - Main game loop update
  - `render(Graphics2D g)` - Main game rendering
- **Usage:** Entry point for complete game flow

### **GameWindow.java**
- **Path:** `src/GameWindow.java`
- **Main Class:** `GameWindow`
- **Purpose:** Main game window frame extending JFrame
- **Responsibilities:**
  - Window creation and management
  - Game panel hosting
  - Input event dispatching
- **Usage:** Create and show main game window

### **Level1.java**
- **Path:** `src/Level1.java`
- **Main Class:** `Level1`
- **Purpose:** Complete Level 1 implementation (Industrial Zone)
- **Features:**
  - 25+ tile types with industrial theme
  - Parallel scrolling backgrounds
  - Enemy spawning and patrol routes
  - Boss encounter
  - Checkpoint system
- **Usage:** `Game.switchLevel(1)` or direct instantiation

### **Level2.java**
- **Path:** `src/Level2.java`
- **Main Class:** `Level2`
- **Purpose:** Complete Level 2 implementation (Power Station)
- **Features:**
  - Dynamic asset registry per tileset
  - Advanced environmental hazards
  - Multiple parallax layers
  - Boss phase transitions
- **Usage:** `Game.switchLevel(2)` or direct instantiation

### **PlayerController.java**
- **Path:** `src/PlayerController.java`
- **Main Class:** `PlayerController`
- **Purpose:** Player input handling and animation state management
- **Key Methods:**
  - `handleKeyPress(int keyCode)` - Process keyboard input
  - `update(double deltaTime)` - Update player state based on input
  - `jump()` - Initiate jump animation and physics
  - `attack()` - Start attack animation and projectile spawn
- **Usage:** Called each frame by Game or Level

### **PlayerState.java**
- **Path:** `src/PlayerState.java`
- **Main Class:** `PlayerState`
- **Purpose:** Player state machine and animation state tracking
- **Data:** Current animation state enum, position, health, velocity
- **Usage:** Tracks what the player is currently doing (idle, walk, jump, attack, etc.)

### **GameScreenSystem.java**
- **Path:** `src/GameScreenSystem.java`
- **Main Class:** `GameScreenSystem`
- **Purpose:** Screen-based architecture for menu navigation and game screens
- **Key Methods:**
  - `pushScreen(Screen s)` - Add screen to stack
  - `popScreen()` - Remove top screen
  - `update()` - Update current screen
  - `render(Graphics2D g)` - Render current screen
- **Usage:** Screen management for menus, gameplay, pause, etc.

### **ScreenController.java**
- **Path:** `src/ScreenController.java`
- **Main Class:** `ScreenController`
- **Purpose:** Screen transition and state controller
- **Responsibilities:**
  - Screen switching
  - Transition animations
  - Input routing to appropriate screen
- **Usage:** Central controller for all screen changes

### **CheckpointManager.java**
- **Path:** `src/CheckpointManager.java`
- **Main Class:** `CheckpointManager`
- **Purpose:** Manages checkpoint/save system for player respawn
- **Key Methods:**
  - `setCheckpoint(double x, double y)` - Save checkpoint location
  - `loadCheckpoint()` - Return to last checkpoint
  - `saveGame()` - Save full game state
- **Usage:** Called when player reaches checkpoint flags or dies

---

## 🎨 ANIMATION & SPRITE SYSTEM (MAJOR MODULE)

This is the **largest and most important module**, containing 70+ classes organized for complete animation management.

### **AnimationAndSpriteLoader.java** (MONOLITHIC - 40+ NESTED CLASSES)

- **Path:** `src/animation/AnimationAndSpriteLoader.java`
- **Size:** ~18,000+ lines of code
- **Main Class:** `AnimationAndSpriteLoader extends GameCore`
- **Purpose:** Unified animation and sprite loading system with complete game asset management

#### **Nested Classes & Their Functions:**

##### **Asset Path Constants**
```java
PLAYER_BASE = "Resources/industrial-zone/characters/player/"
BOSS_BASE = "Resources/industrial-zone/characters/bosses/"
ENEMY_BASE = "Resources/industrial-zone/characters/enemies/"
DRONE_BASE = ENEMY_BASE + "drones/"
L1_TILES_BASE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/"
L2_TILES_BASE = "Resources/industrial-zone/1 Tiles/power-station-level-2/1 Tiles/"
GUI_BASE, VFX_BASE, WEAPONS_BASE, AUDIO_BASE
```
- All asset paths unified in one location for easy modification

##### **TileRegistry (Inner Class)**
- **Purpose:** Maps single-character codes (A-Z, a-z, 0-9, !@) to complete tile asset paths
- **Contains:** 65 tile mappings for Level 1 Industrial Zone
- **Key Methods:**
  - `getTile(char code)` - Get asset path for tile character
  - `getAllCodes()` - Get all valid tile codes
  - `hasTile(char code)` - Check if code exists
- **Usage Example:**
  ```java
  String assetPath = TileRegistry.getTile('A');
  BufferedImage tile = ImageIO.read(new File(assetPath));
  ```

##### **SpriteMetadata (Inner Class)**
- **Purpose:** Analyzes sprite properties (image dimensions, frame count, timing)
- **Properties:**
  - `imageWidth`, `imageHeight` - Full sprite sheet dimensions
  - `frameCount` - Number of animation frames
  - `frameWidth`, `frameHeight` - Individual frame dimensions
  - `complexity` - Difficulty rating of sprite
  - `suggestedMs` - Recommended frame timing
- **Usage:** Validate sprite files before loading

##### **PhysicsUnitSystem (Inner Class with 6 nested classes)**
- **Purpose:** Complete physics engine with SI unit conversion (1 Tile = 1 Meter = 32 pixels)
- **Constants:**
  - `PIXELS_PER_METER = 32.0f`
  - `GRAVITY = -9.81f` (m/s²)
  - `LINEAR_DAMPING = 0.85f` (friction)
- **Nested Classes:**
  - **Vector2D:** 2D vector math (position, velocity)
  - **PhysicsBody:** Entity with mass, velocity, forces
- **Key Methods:**
  - `toMeters(pixels)`, `toPixels(meters)` - Unit conversion
  - `jumpVelocity(height)` - Calculate jump velocity needed
  - `fallTime(height)` - Time to fall from height
  - `impactVelocity(height)` - Final velocity after falling
- **Usage:** All physics calculations use this system

##### **AnimationState (Enum - 30+ states)**
- **Purpose:** All possible entity animation states with serial numbers linking to assets
- **Serial Number Bridge:**
  - Serial 1-24: Player animations (IDLE, WALK, RUN, JUMP, PUNCH, ATTACK, etc.)
  - Serial 25: INTERACT (transporter interaction)
  - Serial 50-55: Enemy states
  - Serial 60-65: Boss states
  - Serial 70-72: Environmental states
  - Serial 80-83: VFX states
  - Serial 90-92: GUI states
- **Example States:**
  ```java
  IDLE(1, "idle", 4, 150)           // 4 frames, 150ms each
  WALK(3, "walk", 5, 100)
  JUMP(5, "jump", 3, 80)
  ATTACK(13, "attack", 7, 100)
  ```
- **Usage:** Every animated entity tracks current `AnimationState`

##### **CharacterAssetMapper (Inner Class)**
- **Purpose:** Maps AnimationState serial numbers to character asset files
- **Nested Enum:** `CharacterSkin {BIKER, PUNK, CYBORG}`
- **Key Method:** `getAssetPath(AnimationState state, CharacterSkin skin)`
- **Asset Pattern:**
  ```
  {SerialNumber:02d}_Player_{CharacterName}_{StateName}...png
  01_Player_Biker_Idle_4Frames1Row_StandingBreath...png
  03_Player_Punk_Walk_5FramesCycleForward...png
  12_Player_Cyborg_Punch_6FramesCombo...png
  ```
- **Usage:** Pass AnimationState to get correct asset file

##### **TransporterAssetMapper (Inner Class)**
- **Purpose:** Maps transporter/vehicle types to animations
- **Nested Enum:** `TransporterType {HOVERBOARD, HANGING_CABLE, VERTICAL_LIFT, ZONE_TRANSPORTER, HOVER_PLATFORM}`
- **Serial Numbers:** 107-127 for transporter states
- **Usage:** When player interacts with transporters (cable lifts, platforms)

##### **EnemyAssetMapper (Inner Class with 5 sub-enums)**
- **Purpose:** Maps 5 distinct enemy types to animation states
- **Enemy Types:**
  1. **UFO_SAUCER** (Serial 100-104)
     - States: IDLE, TRAVERSE, SCAN_BEAM_ATTACK, TRAVERSE_WITH_BEAM, DESTRUCTION
  2. **JET_DRONE** (Serial 105-106)
     - States: AERIAL_FLIGHT, BOMB_PAYLOAD
  3. **COMBAT_TANK** (Serial 200-212)
     - States: IDLE, WALK, ATTACK1-4, SPECIAL, HURT, DEATH
  4. **ARMOURED_KNIGHT** (Serial 213-222)
     - States: IDLE, WALK, ATTACK1-4, SPECIAL, PROJECTILE, HURT, DEATH
  5. **WINGED_WARRIOR** (Serial 223-233)
     - States: IDLE, WALK, ATTACK1-4, SPECIAL, PROJECTILE, HURT, DEATH
- **Usage:** Enemy spawners use type to load correct animations

##### **ProjectileAssetMapper (Inner Class)**
- **Purpose:** Maps projectile types to animation assets
- **Projectile Types:**
  - ENERGY_BOLT (from Armoured Knight)
  - RED_ORB (from Winged Warrior)
  - BOMB_PAYLOAD (from Jet Drone)
  - CANNON_BALL, RUGBY_BALL (boss projectiles)
- **Usage:** ProjectileController uses this for rendering

##### **ProjectilePhysics (Inner Class)**
- **Purpose:** Container for projectile physics properties
- **Properties:** velocity, acceleration, friction, lifetime
- **Usage:** Each projectile instance has associated physics

##### **EntityController (Interface)**
- **Purpose:** Standard interface for all animated entities
- **Implementation Pattern:**
  ```java
  public interface EntityController {
      void update(double deltaTime);
      void render(Graphics2D g);
      AnimationState getCurrentState();
      void setPosition(double x, double y);
      void takeDamage(int amount);
      void onAttack();
      boolean isAlive();
  }
  ```
- **Usage:** All entities (player, enemies, projectiles) implement this

##### **InputKeyBindings (Inner Class)**
- **Purpose:** Comprehensive keyboard and input bindings
- **Binding Categories:**
  - Movement Keys: Arrow Keys + Modifiers
  - Combat Keys: K, L, Shift+K, Ctrl+K
  - Special Actions: W, H, T, Q, E, X, V, F, P
  - GUI Keys: R (reload), I (inventory), M (map), Esc (pause)
  - Mouse Actions: Left click, Right click
- **Usage:** Input handler uses these constants

##### **StateTransition (Inner Class)**
- **Purpose:** Defines valid animation state transitions with physics properties
- **Properties:** from state, to state, conditions, physics changes
- **Usage:** Prevents invalid state changes (e.g., can't jump from IDLE then immediately jump again)

##### **InputHandler (Inner Class)**
- **Purpose:** Low-level keyboard input handler managing key press/release states
- **Key Methods:**
  - `keyPressed(KeyEvent e)` - Process key press
  - `keyReleased(KeyEvent e)` - Process key release
  - `isKeyPressed(int keyCode)` - Check if key currently held
  - `getTimeSincePress(int keyCode)` - Measure hold time
- **Usage:** Called by JFrame on every keyboard event

##### **InputController (Inner Class)**
- **Purpose:** High-level input state machine that triggers actions from raw input
- **Responsibilities:**
  - Detect input combos (up+jump = higher jump)
  - Apply input dampening (avoid jittering)
  - Generate input events for game logic
- **Usage:** Between InputHandler and PlayerController

##### **Major Controller Classes (5 nested classes)**
These are instantiated once per game session:
1. **PlayerController** - Handles player input and updates player animation
2. **EnemyController** - Manages all enemy entities and AI
3. **DroneController** - Manages drone-type enemies specifically
4. **BossController** - Manages boss entity and attack phases
5. **ProjectileController** - Manages all active projectiles in scene

##### **AI Behavior Classes (3 nested classes)**
1. **EnemyAIBehavior** - Decision making for enemy actions
2. **DroneAIBehavior** - Specialized drone behavior (hovering, beam attacks)
3. **BossAIBehavior** - Complex boss fight mechanics

##### **Environmental & Special Classes**
1. **ParallaxSystem** - Multi-layer background scrolling controller
2. **EnvironmentController** - Tile and background rendering
3. **VFXController** - Particle effect management
4. **GameStateManager** - Central coordinator for all systems

#### **How to Use AnimationAndSpriteLoader:**

1. **Access Asset Path Constants:**
   ```java
   String path = AnimationAndSpriteLoader.L1_TILES_BASE;
   String playerPath = AnimationAndSpriteLoader.PLAYER_BASE;
   ```

2. **Get Tile Asset:**
   ```java
   String tileAsset = AnimationAndSpriteLoader.TileRegistry.getTile('A');
   ```

3. **Get Enemy Animation:**
   ```java
   String enemyAsset = AnimationAndSpriteLoader.EnemyAssetMapper
       .getAssetPath(AnimationState.ENEMY_ATTACK, EnemyType.COMBAT_TANK);
   ```

4. **Create Player Controller:**
   ```java
   PlayerController player = new AnimationAndSpriteLoader.PlayerController(100, 200);
   player.update(0.016); // 60fps
   player.render(graphics2D);
   ```

5. **Get Physics Velocities:**
   ```java
   float jumpVel = AnimationAndSpriteLoader.PhysicsUnitSystem
       .jumpVelocity(2.0f); // Jump 2 meters high
   ```

---

### **Animation Metadata System** (4 classes in src/animation/metadata/)

| Class | Purpose |
|-------|---------|
| **SpriteMetadata.java** | Analyzes sprite dimensions, frames, and timing |
| **MetadataExtractor.java** | Extracts metadata from sprite files |
| **FilenameMetadata.java** | Parses metadata from sprite filenames |
| **MetadataExtractorTest.java** | Unit tests for extraction |

**Usage:** Validate sprites before adding to game

---

### **Animation Managers** (5 classes in src/animation/managers/)

| Class | Responsible For |
|-------|-----------------|
| **PlayerAssetManager.java** | Player character assets loading |
| **EnemyAssetManager.java** | Enemy animation assets |
| **EnvironmentAssetManager.java** | Tile and background assets |
| **EffectsAssetManager.java** | VFX and particle assets |
| **UIAssetManager.java** | GUI element assets |

**Usage:** Specialized managers for different asset categories

---

### **Animation Systems** (7 classes in src/animation/systems/)

| Class | Purpose |
|-------|---------|
| **PhysicsBase.java** | Base physics for animated entities |
| **InputSystemBase.java** | Base input handling |
| **AnimationSystemBase.java** | Base animation framework |
| **AIBehaviorBase.java** | Base AI behavior |
| **AssetRegistry.java** | Asset registry system |
| **TestPlayerController.java** | Test implementation |
| **InheritanceSystemTest.java** | Tests inheritance hierarchy |

---

### **Other Animation Classes** (remaining in src/animation/)

| Class | Purpose |
|-------|---------|
| **PlayerCharacterAnimations.java** | Player character animation frame definitions |
| **ProjectileAnimationRegistry.java** | Central registry for projectile animations |
| **Level1AssetRegistry.java** | Level 1-specific asset registry |
| **Level2AssetRegistry.java** | Level 2-specific asset registry |
| **Level1TileAdjacencySystem.java** | Tile adjacency rules for Level 1 (65 tiles) |
| **Level2TileAdjacencySystem.java** | Tile adjacency rules for Level 2 |
| **GUITileAdjacencySystem.java** | Tile adjacency for GUI frames (82 tiles, 7 themes) |
| **GUITileAdjacencySystemV2.java** | Enhanced version 2 of GUI adjacency |
| **GUIComponentsSystem.java** | Central GUI components and styling system |
| **CharacterSelectionAnimationSystem.java** | Character selection screen with animated cards |
| **EnemyControllers.java** | Enemy animation and behavior controllers |
| **TileRegistryTest.java** | Unit tests for tile registry |

---

## ⚙️ PHYSICS ENGINE

The physics module contains 20+ classes for complete 2D physics simulation:

### **Physics.java** (Main System)
- **Path:** `src/physics/Physics.java`
- **Main Class:** `Physics`
- **Nested Classes:**
  - `GravitySystem` (with `GravityField`)
  - `VelocitySystem`
  - `CollisionPhysicsSystem`
  - `PhysicsComponent`
  - `PhysicsManager`
  - `PlatformCollider`
- **Purpose:** Unified physics coordinator managing gravity, velocity, and collisions
- **Key Methods:**
  - `update(double deltaTime)` - Simulate one physics step
  - `applyGravity(PhysicsBody body)` - Apply gravity to entity
  - `detectCollisions()` - Check for collisions
  - `resolveCollisions()` - Handle collision responses
- **Usage:** Called once per game frame

### **PhysicsEngine.java**
- **Purpose:** Core physics simulation engine
- **Handles:** F=ma calculations, kinematic integration, constraint solving
- **Usage:** Low-level physics calculations

### **PhysicsBody.java**
- **Purpose:** Physical body representation for any entity with physics
- **Properties:**
  ```java
  double x, y;           // Position
  double vx, vy;         // Velocity
  double ax, ay;         // Acceleration
  double mass;           // Mass in kg
  double width, height;  // Collision bounds
  boolean onGround;      // Contact with ground
  ```
- **Key Methods:**
  - `applyForce(double fx, double fy)` - Add force
  - `update(double dt)` - Update position/velocity
  - `getCollisionBox()` - Get AABB for collision
- **Usage:** Every entity needing physics has a `PhysicsBody`

### **CollisionDetector.java**
- **Purpose:** AABB (Axis-Aligned Bounding Box) collision detection
- **Key Method:** `checkCollision(PhysicsBody a, PhysicsBody b)` → `boolean`
- **Algorithm:** Simple AABB overlap check
- **Usage:** Detect when entities touch

### **CollisionHazardSystem.java**
- **Purpose:** Special system for hazard collision processing
- **Features:** Damage application, knockback, status effects
- **Usage:** When player touches spikes, lava, electric hazards

### **Platform.java**
- **Purpose:** Static platform/collider definition
- **Properties:** position, size, collision type
- **Usage:** Level tilemap defines platforms

### **BoundingBox.java**
- **Purpose:** AABB box representation
- **Properties:** x, y, width, height
- **Methods:** `overlaps()`, `contains()`, `expand()`
- **Usage:** Collision calculations

### **PhysicsConstants.java**
- **Purpose:** Central constants for physics tuning
- **Typical Values:**
  - Gravity: 9.81 m/s²
  - Player mass: 1.0 kg
  - Jump height: 2.0 meters
  - Terminal velocity: 30 m/s
- **Usage:** Modify here to change game feel

### **CharacterPhysicsProfile.java**
- **Purpose:** Character-specific physics parameters
- **Properties:**
  - Max run speed
  - Jump height
  - Acceleration
  - Friction coefficients
- **Usage:** Different characters have different physics profiles

### **CharacterPhysicsSimulator.java**
- **Purpose:** Simulates character physics specifically
- **Features:**
  - Jump mechanics (holding button for higher jumps)
  - Gravity scaling during fall
  - Air control
  - Landing impact
- **Usage:** Updates player PhysicsBody each frame

### **TilePhysics.java** and **TileProperties.java**
- **Purpose:** Physics behavior for tiles
- **Properties per Tile:**
  - Is solid (collision)?
  - Is hazard (damage)?
  - Friction coefficient
  - Bounce factor
- **Usage:** Level tiles use these for physics

### **SpatialGrid.java**
- **Purpose:** Spatial partitioning for collision optimization
- **Algorithm:** Divides space into grid cells
- **Benefits:** O(1) to O(n) collision checks instead of O(n²)
- **Usage:** With 200+ entities, speeds up collision detection 100x

### **Test Classes** (6 physics test files)
- `TestPhysicsBody.java` - Tests physics body mechanics
- `TestPhysicsEngineGravity.java` - Tests gravity simulation
- `TestPhysicsEngineFriction.java` - Tests friction/damping
- `TestPhysicsEngineJumping.java` - Tests jump mechanics
- `TestPhysicsEngineAcceleration.java` - Tests acceleration
- `TestPhysicsEngineCollisions.java` - Tests collision detection

---

## 🤖 AI & BEHAVIOR SYSTEM

The AI module contains 15+ classes for intelligent enemy behavior:

### **AI.java** (Main System - 8 nested classes)
- **Path:** `src/ai/AI.java`
- **Nested Classes:**
  - `AISystem` - Central coordinator
  - `AIState` (enum) - States: IDLE, PATROL, CHASE, ATTACK, DEAD
  - `AIBehavior` (interface with `AIAction`)
  - `AIDecisionMaker` (with `DecisionContext`)
  - `AIPathfinder` (with `Waypoint`, `Path`)
  - `EnemyAI` - Full enemy implementation
  - `AIBehaviorSystem` (with `AIState` enum, `Difficulty` enum, `AIAgent`)
  - `AIManager`
  - `Waypoint` - Navigation waypoint

**Purpose:** Complete AI system with decision making, pathfinding, and behavior management

**Key Methods:**
- `update(double dt)` - Run AI decision cycle
- `makeDecision()` - Decide next action
- `findPath(start, goal)` - Pathfind to target
- `checkPlayerDetected()` - Detect if player is visible

**Usage:** Each enemy instantiated with:
```java
EnemyAI enemy = new EnemyAI(startX, startY, enemyType);
enemy.update(0.016);
```

### **BehaviorTree.java**
- **Purpose:** Base behavior tree implementation
- **Nested Enum:** `State {RUNNING, SUCCESS, FAILURE}`
- **Concept:** Hierarchical decision tree for behavior
- **Usage:** Complex enemy behaviors built as trees

### **EnemyAI.java**
- **Purpose:** Main enemy AI coordinator
- **Responsibilities:**
  - State transitions (idle → patrol → chase → attack)
  - Decision making
  - Animation state updates
  - Projectile spawning
- **Usage:** Every enemy is an instance of or inherits from this

### **State Classes** (4 separate files)

| Class | AI State | Behavior |
|-------|----------|----------|
| **PatrolState.java** | PATROL | Walks set patrol route |
| **ChaseState.java** | CHASE | Pursues player when detected |
| **AttackState.java** | ATTACK | Attacks player in range |
| **DeatState.java** | DEATH | Death animation and cleanup |

### **Enemy Type Implementations**

| Class | Enemy Type | AI Type |
|-------|-----------|---------|
| **PatrollerAI.java** | Wandering enemy | Patrol-focused |
| **GunnerAI.java** | Ranged attacker | Long-range attacks, kiting |
| **MeleeAI.java** | Melee fighter | Close combat, charging |

---

## 🎨 RENDERING PIPELINE

The rendering module contains 30+ classes for complete graphics rendering:

### **RenderingSystem.java** (Chief Coordinator)
- **Path:** `src/rendering/RenderingSystem.java`
- **Purpose:** Chief rendering coordinator for all visual output
- **Responsibilities:**
  - Coordinate all renderer subsystems
  - Manage render order (backgrounds → tiles → entities → effects → UI)
  - Handle camera transformations
  - Apply post-processing
- **Usage:** Called once per frame with `Graphics2D` target

### **Screen Renderers** (Individual subsystems)

| Class | Renders |
|-------|---------|
| **ScreenRenderer.java** | Main screen canvas |
| **BackgroundRenderer.java** | Parallax background layers |
| **TileRenderer.java** | Tile map geometry |
| **EntityRenderer.java** | Player, enemies, bosses |
| **WeaponRenderer.java** | Projectiles and weapons |
| **VFXRenderer.java** | Particle effects |
| **EffectRenderer.java** | Screen effects (shake, fade) |
| **HUDRenderer.java** | Heads-up display (health, ammo) |
| **MenuRenderer.java** | Menu screens |
| **DamageNumberRenderer.java** | Floating damage text |

### **Specialized Renderers**

| Class | Purpose |
|-------|---------|
| **Level1BackgroundRenderer.java** | Level 1-specific background |
| **Level1AnimatedObjectRenderer.java** | Level 1 animated props |
| **AnimatedObjectManager.java** | Manages animated props |
| **InputDisplayRenderer.java** | Shows current key presses |
| **TutorialRenderer.java** | Tutorial messages |

### **Tile Map Loaders** (Enhanced systems)

| Class | Purpose |
|-------|---------|
| **ComprehensiveTileMapLoader.java** | Full-featured tile loader |
| **EnhancedTileMapLoader.java** | Enhanced with validation |

### **Special Effects Renderers**

| Class | Effect |
|-------|--------|
| **PostProcessingRenderer.java** | Bloom, blur, distortion |
| **ScreenShakeManager.java** | Screen vibration on impact |
| **StatusEffectRenderer.java** | Status indicators (poison, freeze) |
| **PropRenderer.java** | Environmental props |
| **DigitRenderer.java** | Numeric digits for score/ammo |

---

## 🖥️ GUI/UI SYSTEM

The GUI module is the second largest with 50+ classes providing complete UI functionality:

### **GUIManager.java** (Main Coordinator)
- **Path:** `src/gui/GUIManager.java`
- **Purpose:** High-level GUI system coordinator
- **Responsibilities:**
  - Manage all GUI screens
  - Handle screen transitions
  - Route input to GUI
  - Coordinate all GUI components
- **Usage:** One instance per game

### **GUI Screens** (30+ implementations in src/gui/screens/)

**Phase-Based Implementation (Incremental development):**

| Phase | Screen | Purpose |
|-------|--------|---------|
| 1 | `StatusBarScreen.java` | Health/energy bar prototype |
| 2 | `Phase2CharacterIdleScreen.java` | Character portraits with animation |
| 3 | `Phase3StatusBarScreen.java` | Complete status bars |
| 4 | `Phase4NumericDisplayScreen.java` | Score, ammo, resources |
| 5 | `Phase5ButtonScreen.java` | Interactive buttons |
| 6 | `Phase6DecorationScreen.java` | Decorative elements |
| 7 | `Phase7ItemInventoryScreen.java` | Item inventory system |
| 8 | `Phase8MinimapScreen.java` | Minimap display |
| 9 | `Phase9DialogueScreen.java` | Story dialogue |
| 10 | `Phase10TooltipScreen.java` | Tooltip hints |
| 11 | `Phase11NotificationScreen.java` | Notifications |
| 12 | `Phase12QuestTrackerScreen.java` | Quest tracking |
| 13 | `Phase13MainMenuScreen.java` | Main menu |
| 14 | `Phase14PauseMenuScreen.java` | Pause overlay |
| 15 | `Phase15SettingsScreen.java` | Settings/options |

**Functional Screens:**

| Class | Purpose |
|-------|---------|
| **MainMenuScreen.java** | Game start, level select |
| **CharacterSelectScreen.java** | Character skin selection |
| **PauseMenuScreen.java** | Pause overlay |
| **SettingsScreen.java** | Game options |
| **HowToPlayScreen.java** | Tutorial/controls |
| **LevelSelectScreen.java** | Level selection |
| **LevelCompleteScreen.java** | Level completion |
| **GameOverScreen.java** | Death screen |
| **DialogueScreen.java** | Story dialogue |
| **SplashScreen.java** | Loading screen |

### **GUI Component Classes** (20+ components)

| Class | Component Type |
|-------|-----------------|
| **GUIComponent.java** | Base GUI component |
| **GUIButton.java** | Clickable button |
| **ButtonPanel.java** | Container with buttons |
| **HealthBar.java** | Health display bar |
| **EnergyBar.java** | Energy/mana bar |
| **BarRenderer.java** | Generic bar rendering |
| **DigitRenderer.java** | Numeric digit rendering |
| **NumberRenderer.java** | Full number rendering |
| **AnimationController.java** | GUI animation controller |
| **AnimatedCharacterProfile.java** | Character card with animations |
| **InteractiveButton.java** | Button with feedback |
| **InteractivePanel.java** | Interactive panel |
| **ControlHintDisplay.java** | Control demonstration |

### **GUI Asset Management**

| Class | Responsibility |
|-------|-----------------|
| **GUIAssetManager.java** | Manages GUI assets |
| **GUIAssetRegistry.java** | Central GUI asset registry |
| **GUIAssetLoader.java** | Loads GUI assets from disk |
| **GUIAssets.java** | GUI asset data structures |

### **Loader Classes** (Specialized asset loading)

| Class | Loads |
|-------|-------|
| **ButtonStateLoader.java** | Button animation states |
| **StatusBarAnimationLoader.java** | Status bar animations |
| **CharacterIdleAnimationLoader.java** | Character portraits |
| **NumericDisplayLoader.java** | Number digit assets |
| **GlowBarAnimationLoader.java** | Glowing bar effects |
| **CableAnimationLoader.java** | Cable/wire animations |

### **Input & State Management**

| Class | Purpose |
|-------|---------|
| **MouseInputHandler.java** | Mouse click handling |
| **MenuInputHandler.java** | Menu navigation input |
| **TransporterInputHandler.java** | Transporter controls |
| **GameState.java** | Game state for GUI |
| **AnimationState.java** | Animation state enum |
| **ScreenStateListener.java** | Listen for screen changes |

### **Screen Management**

| Class | Purpose |
|-------|---------|
| **ScreenManager.java** | Manages screen stack |
| **ScreenAnimationManager.java** | Screen transition animations |
| **AssetDrivenScreen.java** | Base screen driven by assets |
| **Screen.java** | Base screen class |

### **HUD & Overlay Components**

| Class | Purpose |
|-------|---------|
| **HUDPanel.java** | Main HUD panel |
| **TopBarPanel.java** | Top status bar area |
| **LeftSidebar.java** | Left sidebar info |
| **ModuleLogo.java** | Module/game logo |
| **TransporterHUD.java** | Transporter interface |

---

## 🔊 AUDIO SYSTEM

The audio module contains 6 classes for sound and music:

### **Audio.java** (Main System - 3 nested classes)
- **Path:** `src/audio/Audio.java`
- **Nested Classes:**
  - `AudioManager` (with `AudioEvent`)
  - `AudioSystemsStub` (mock implementations)
    - `MusicManager`
    - `SFXManager`
    - `AudioMixer`
    - `AudioEventDispatcher`
- **Purpose:** Main audio system with music and SFX management
- **Key Methods:**
  - `playMusic(String musicFile)` - Start background music
  - `playSound(String sfxFile)` - Play sound effect
  - `setMusicVolume(float vol)` - Adjust music volume
  - `setSFXVolume(float vol)` - Adjust SFX volume
  - `stopMusic()` - Stop background music
  - `stopAllSounds()` - Silence everything
- **Usage:** One instance per game for all audio

### **SoundManager.java**
- **Purpose:** Manages sound effect playback
- **Responsibilities:**
  - Load and cache SFX files
  - Play on demand
  - Multi-channel playback
- **Usage:** Triggered by game events (weapon fire, enemy hit, etc.)

### **MusicIntegrator.java**
- **Purpose:** Integrates background music into gameplay
- **Features:**
  - Smooth transitions between tracks
  - Level-specific music
  - Looping
  - Fade in/out
- **Usage:** Level startup calls to set theme music

### **SoundEffectTrigger.java**
- **Purpose:** Triggers SFX on game events
- **Events Triggering SFX:**
  - Weapon fire
  - Enemy hit/death
  - Player damage
  - Menu hover/click
  - Collectible pickup
- **Usage:** Called by game event system

### **MidiTuner.java**
- **Purpose:** MIDI synthesis and tuning
- **Features:**
  - Pitch adjustment
  - Real-time synthesis
  - Note generation
- **Usage:** For dynamic music generation (optional)

### **AudioAssetRegistry.java**
- **Purpose:** Central registry of all audio assets
- **Contains:**
  - Music track paths
  - SFX names and paths
  - Volume defaults
  - Looping settings
- **Usage:** Audio system references this for assets

---

## ⚔️ WEAPONS & COMBAT SYSTEM

The weapons module contains multiple weapon and combat systems:

### **Weapon.java**
- **Path:** `src/Weapon.java`
- **Main Class:** `Weapon`
- **Purpose:** Weapon data structure
- **Properties:**
  - Weapon type (melee, ranged, energy)
  - Damage
  - Fire rate
  - Ammo capacity
  - Projectile type
- **Usage:** Player carries weapon instances

### **WeaponManager.java**
- **Path:** `src/WeaponManager.java`
- **Main Class:** `WeaponManager`
- **Purpose:** Manages player weapons and equipment
- **Key Methods:**
  - `equipWeapon(Weapon w)` - Switch to weapon
  - `fireWeapon()` - Shoot current weapon
  - `reload()` - Reload ammo
  - `switchWeapon(int slot)` - Switch to weapon in slot
- **Usage:** One per player, controls what weapon is active

### **Weapons Package Classes**

| Class | Purpose |
|-------|---------|
| **ProjectileManager.java** | Manages all active projectiles |
| **WeaponRenderer.java** | Renders weapons and projectiles |

---

## ✨ VISUAL EFFECTS (VFX)

The VFX module contains 10+ classes for particle effects:

### **VFXManager.java** (Main System)
- **Path:** `src/vfx/VFXManager.java`
- **Purpose:** Manages all particle systems and effects
- **Key Methods:**
  - `spawnEffect(type, x, y)` - Create new effect
  - `update(double dt)` - Update all effects
  - `render(Graphics2D g)` - Draw all effects
  - `clearDeadEffects()` - Remove finished effects
- **Usage:** Central effect coordinator

### **VFXAssetRegistry.java**
- **Purpose:** Registry of all VFX sprite assets
- **Contains:** Paths to smoke, fire, spark, blood textures
- **Usage:** VFXManager loads effects from this

### **ParticleEmitter.java**
- **Purpose:** Emits particles over time
- **Properties:**
  - Emission rate
  - Lifetime
  - Spread angle
  - Initial velocity
- **Usage:** Creates explosions, smoke, sparks

### **SpriteParticle.java**
- **Purpose:** Single sprite-based particle
- **Properties:**
  - Position, velocity
  - Lifetime remaining
  - Current sprite frame
  - Scale/rotation
- **Usage:** Individual particles in emitter

### **Specialized Effect Renderers**

| Class | Effect |
|-------|--------|
| **ImpactEffectRenderer.java** | Impact/collision bursts |
| **ImpactVfxRenderer.java** | Impact-specific effects |
| **SmokeEffectRenderer.java** | Smoke particles |
| **SmokeVfxRenderer.java** | Alternative smoke |
| **SparkEffectSystem.java** | Spark/electric effects |
| **AssetBasedVFXRenderer.java** | Base class for asset-based VFX |

---

## 🧩 TILE SYSTEM

The tile module contains 5 classes for tile management:

### **TileMapSystem.java** (Main System)
- **Path:** `src/tiles/TileMapSystem.java`
- **Purpose:** Central tile map management
- **Key Methods:**
  - `loadLevel(String levelData)` - Load level from grid string
  - `getTile(int x, int y)` - Get tile at position
  - `update(double dt)` - Update animated tiles
  - `render(Graphics2D g)` - Render all tiles
- **Usage:** Level instances use this

### **Level1TileRegistry.java**
- **Path:** `src/tiles/Level1TileRegistry.java`
- **Purpose:** Level 1-specific tile registry
- **Contains:** 65 tile mappings for Industrial Zone
- **Usage:** Level1 uses this for terrain construction

### **Level2TileRegistry.java**
- **Path:** `src/tiles/Level2TileRegistry.java`
- **Purpose:** Level 2-specific tile registry
- **Contains:** 64 tile mappings for Power Station
- **Usage:** Level2 uses this for terrain construction

### **Level1TileAssetCache.java**
- **Purpose:** Caches Level 1 tile assets in memory for fast access
- **Key Method:** `loadAllTiles()` - Pre-load all 65 tiles
- **Benefit:** Eliminates frame drops from disk access during gameplay
- **Usage:** Call at level startup

### **Level1TileAssetCacheTest.java**
- **Path:** `src/tiles/Level1TileAssetCacheTest.java`
- **Purpose:** Unit tests for tile cache
- **Verifies:** All tiles loaded, no corruption, timing acceptable
- **Usage:** Run to verify cache works

---

## 🛠️ UTILITIES & HELPERS

The utils module contains 8 universal helper classes:

### **Constants.java**
- **Purpose:** Game-wide constant values
- **Examples:**
  ```java
  SCREEN_WIDTH = 1280
  SCREEN_HEIGHT = 720
  FPS = 60
  TILE_SIZE = 32
  GRAVITY = 9.81
  ```
- **Usage:** Reference instead of magic numbers

### **MathHelper.java**
- **Purpose:** Common math utilities
- **Methods:**
  - `distance(x1, y1, x2, y2)` - Euclidean distance
  - `angleTo(fromX, fromY, toX, toY)` - Angle between points
  - `clamp(value, min, max)` - Constrain value
  - `lerp(a, b, t)` - Linear interpolation
  - `normalize(x, y)` - Unit vector
- **Usage:** Physics, AI, animation calculations

### **AssetManager.java**
- **Purpose:** Manages asset loading and caching
- **Key Methods:**
  - `loadImage(path)` - Load and cache BufferedImage
  - `getImage(path)` - Get cached image
  - `clear()` - Clear all caches
  - `getMemoryUsage()` - Cache size in bytes
- **Usage:** All asset loading goes through this

### **AssetRegistry.java**
- **Purpose:** Central asset registry
- **Contains:** All asset paths and metadata
- **Usage:** Asset paths defined here, not hardcoded

### **AssetInitializer.java**
- **Purpose:** Initializes all game assets on startup
- **Responsibilities:**
  - Loads all images
  - Loads all audio
  - Instantiates asset managers
  - Displays progress
- **Usage:** Called once at game startup

### **SafeAssetLoader.java**
- **Purpose:** Safely loads assets with comprehensive error handling
- **Features:**
  - Logs missing files
  - Returns placeholders on error
  - Validates file existence
  - Provides error reporting
- **Usage:** Prevents crashes from missing assets

### **ResourceManager.java**
- **Purpose:** Manages game resources beyond just assets
- **Manages:** Memory, threads, file I/O
- **Usage:** Game-wide resource coordination

### **ResourceLoader.java**
- **Purpose:** Loads resources from disk
- **Handles:** File I/O, error handling, caching
- **Usage:** Low-level resource loading

---

## 🎲 ADDITIONAL MAJOR SYSTEMS

### **Core Package** (src/core/ - 15+ classes)

| Class | Purpose |
|-------|---------|
| **Core.java** | Core game system coordinator |
| **GameStateManager.java** | Manages game state (MENU, PLAYING, PAUSED, GAME_OVER) |
| **GameState.java** | Game state enum |
| **LevelManager.java** | Manages level loading and transitions |
| **InputHandler.java** | Main keyboard input handler (KeyListener) |
| **MouseHandler.java** | Mouse input handler |
| **ScoreManager.java** | Tracks and manages player score |
| **RespawnController.java** | Manages player respawn mechanics |
| **CheckpointManager.java** | Manages save/checkpoint system |
| **Checkpoint.java** | Single checkpoint data |
| **CardCollectible.java** | Collectible card item |
| **DroneTransport.java** | Transporter pad mechanics |

### **Camera System** (src/camera/ - 2 classes)

| Class | Purpose |
|-------|---------|
| **Camera.java** | Main camera for viewport control |
| **CameraPackageCoordinator.java** | Coordinates all camera systems |

### **2D Game Framework** (src/game2D/ - 7 classes)

| Class | Purpose |
|-------|---------|
| **GameCore.java** | Core JFrame-based 2D game framework |
| **Animation.java** | Animation frame sequence |
| **Sprite.java** | 2D sprite with position and rendering |
| **Tile.java** | Single tile representation |
| **TileMap.java** | 2D grid of tiles |
| **Velocity.java** | Velocity vector (vx, vy) |
| **Sound.java** | Sound playback thread |

### **Entity System** (src/entities/ - 4+ classes)

| Class | Purpose |
|-------|---------|
| **Entity.java** | Base entity class |
| **Enemy.java** | Enemy entity extending Entity |
| **Entities.java** | Factory/registry for all entities |
| **PlayerCharacterAnimationLoader.java** | Player animation loader |

### **Core Game Entities** (src/core_game_entities/ - 13+ classes)

Organized by entity type:
- **Characters** (src/core_game_entities/characters/)
  - `Characters.java` - Player character definitions
  - `PlayerBase.java` - Player interface
  - `PlayerEntities.java` - Playable characters
- **Enemies** (src/core_game_entities/enemies/)
  - `Enemies.java` - Enemy definitions
- **Environment** (src/core_game_entities/environment/)
  - `TilesEntities.java` - Tile entities
- **Weapons** (src/core_game_entities/weapons/)
  - `WeaponsEntities.java` - Weapon entities
- **Effects** (src/core_game_entities/effects/)
  - `VFXEntities.java` - VFX entities
  - `VFXChainReaction.java` - Chain reaction effects
- **Audio** (src/core_game_entities/audio/)
  - `AudioEntities.java` - Audio elements
- **UI** (src/core_game_entities/ui_elements/)
  - `GUIEntities.java` - GUI elements
- **Bosses** (src/core_game_entities/bosses/)
  - `BossEntities.java` - Boss enemies

### **Optimization Package** (src/optimization/ - 8 classes)

| Class | Purpose |
|-------|---------|
| **AssetCache.java** | Caches frequently-used assets |
| **ObjectPool.java** | Object pooling for memory efficiency |
| **ViewportCuller.java** | Culls objects outside viewport |
| **SpatialGrid.java** | Spatial partitioning for O(1) collision |
| **RenderBatcher.java** | Batches render calls |
| **CollisionOptimizer.java** | Optimizes collision detection |
| **PerformanceMonitor.java** | Monitors FPS and performance |
| **PerformanceProfiler.java** | Profiles bottlenecks |

### **Other Specialized Systems**

| Location | System | Purpose |
|----------|--------|---------|
| src/combat/ | Combat System | Combat mechanics |
| src/config/ | Configuration | Game settings |
| src/dialogue/ | Dialogue System | Story/NPC dialogue |
| src/map/ | Map System | Tile data and adjacency |
| src/ui/ | UI System | Unified UI coordinator |
| src/objectives/ | Mission System | Quest/objective tracking |

---

## 🏗️ ARCHITECTURE OVERVIEW

### **Monolithic Main Class: AnimationAndSpriteLoader.java**

This single 18,000+ line file contains 40+ nested classes covering:

```
AnimationAndSpriteLoader (extends GameCore)
├── Asset Path Constants (all Resources/...) 
├── TileRegistry
├── SpriteMetadata
├── PhysicsUnitSystem
│   ├── Vector2D
│   ├── PhysicsBody
│   └── Physics constants & conversions
├── AnimationState (enum 30+ states)
├── CharacterAssetMapper
│   └── CharacterSkin (enum)
├── TransporterAssetMapper
│   └── TransporterType (enum)
├── EnemyAssetMapper
│   ├── UfoSaucerState
│   ├── JetDroneState
│   ├── CombatTankState
│   ├── ArmouredKnightState
│   └── WingedWarriorState
├── ProjectileAssetMapper (5+ projectile types)
├── ProjectilePhysics
├── EntityController (interface)
├── InputKeyBindings (all key codes)
├── StateTransition
├── InputHandler
├── InputController
├── PlayerController
├── EnemyController
├── DroneController
├── BossController
├── ProjectileController
├── EnvironmentController
├── VFXController
├── ParallaxSystem
├── GameStateManager
├── EnemyAIBehavior
├── DroneAIBehavior
└── BossAIBehavior
```

**Why Monolithic?**
This architecture centralizes all asset paths, animation states, and serialization numbers in one place, making:
- Serial number bridge obvious (01-24 = player, 50-55 = enemies, etc.)
- Asset paths consistent
- Animation state enums authoritative
- Easier to synchronize between file naming and code

### **Data Flow in Game Loop**

```
Game Loop (60 FPS, every ~16ms)
│
├─ Input Phase
│  ├─ InputHandler reads keyboard events
│  └─ InputController processes combos
│
├─ Update Phase
│  ├─ PlayerController.update()
│  │  ├─ PhysicsBody.update() → apply gravity
│  │  ├─ CollisionDetector.checkCollision()
│  │  └─ AnimationState.advance() → next frame
│  │
│  ├─ EnemyController.update() (for each enemy)
│  │  ├─ EnemyAI.makeDecision()
│  │  ├─ PhysicsBody.update()
│  │  └─ AnimationState.advance()
│  │
│  ├─ ProjectileController.update()
│  │  ├─ Update positions
│  │  └─ Check hit detection
│  │
│  ├─ VFXController.update()
│  │  └─ Update particle systems
│  │
│  └─ TileMapSystem.update()
│
├─ Render Phase
│  ├─ RenderingSystem.render()
│  │  ├─ BackgroundRenderer (parallax)
│  │  ├─ TileRenderer (tile layer)
│  │  ├─ EntityRenderer (characters)
│  │  │  ├─ Player animation frame
│  │  │  └─ Enemy animation frames
│  │  ├─ WeaponRenderer (projectiles)
│  │  ├─ VFXRenderer (particles)
│  │  └─ HUDRenderer (UI overlay)
│  │
│  └─ GUIManager.render()
│     └─ CurrentScreen.render()
│
└─ Repeat next frame
```

---

## 📖 HOW TO USE THIS INDEX

### **Finding a Class:**

1. **Know the purpose?** → Find in appropriate section (Physics, AI, GUI, etc.)
2. **Know the package?** → Use "Directory Structure" section
3. **Know the file name?** → Search this document for exact match

### **Understanding a System:**

1. **Want to render something?** → Start with `RenderingSystem.java`
2. **Want player animation?** → Use `AnimationAndSpriteLoader` and `PlayerController`
3. **Want enemy behavior?** → Check `src/ai/` package and `EnemyController`
4. **Want GUI screens?** → See `src/gui/screens/` directory
5. **Want physics?** → Start with `Physics.java` and `PhysicsBody.java`

### **Adding New Content:**

1. **New enemy type?**
   - Add to `AnimationAndSpriteLoader.EnemyAssetMapper` with serial numbers
   - Create control class extending `EnemyAI`
   - Implement AI behavior

2. **New GUI element?**
   - Extend `GUIComponent` or `Screen`
   - Register in `GUIAssetRegistry`
   - Add to appropriate screen in `src/gui/screens/`

3. **New tile type?**
   - Add to appropriate `TileRegistry` (Level1 or Level2)
   - Create PNG asset file
   - Add collision properties to `TileProperties`

4. **New animation state?**
   - Add enum value to `AnimationState` in `AnimationAndSpriteLoader`
   - Assign serial number
   - Create sprite asset
   - Add to `CharacterAssetMapper`, `EnemyAssetMapper`, etc.

### **Debugging Tips:**

1. **Animation not showing?** → Check serial number in asset path vs enum
2. **Physics glitchy?** → Verify `TIME_STEP` and damping coefficients
3. **AI not attacking?** → Check detection range in `EnemyAI`
4. **GUI not responsive?** → Verify `MouseInputHandler` in `GUIManager`
5. **Asset not found?** → Check path in `AssetRegistry`, verify file exists

---

## 📊 File Statistics Summary

| Category | Count |
|----------|-------|
| **Source Files (.java)** | 318 |
| **Main Classes** | 250+ |
| **Nested Classes** | 200+ |
| **Public Methods** | 1000+ |
| **Constants** | 500+ |
| **Enums** | 40+ |
| **Interfaces** | 15+ |
| **Test Classes** | 30+ |

---

## 🔗 Quick Reference Links

### **Main Entry Points:**
- Game startup: `src/GameWindow.java` → `src/Game.java`
- Levels: `src/Level1.java`, `src/Level2.java`
- Animation core: `src/animation/AnimationAndSpriteLoader.java`
- Physics core: `src/physics/Physics.java`
- AI core: `src/ai/AI.java`
- GUI core: `src/gui/GUIManager.java`

### **Asset Registries:**
- Tiles: `src/tiles/Level1TileRegistry.java`, `Level2TileRegistry.java`
- Characters: `AnimationAndSpriteLoader.CharacterAssetMapper`
- Enemies: `AnimationAndSpriteLoader.EnemyAssetMapper`
- GUI: `src/gui/GUIAssetRegistry.java`
- Audio: `src/audio/AudioAssetRegistry.java`

### **Largest Packages (Most Code):**
1. `src/animation/` - 70+ classes
2. `src/gui/` - 50+ classes
3. `src/rendering/` - 30+ classes
4. `src/physics/` - 20+ classes
5. `src/ai/` - 15+ classes

---

**Created:** April 3, 2026  
**Total Scope:** 318 Java files, 250+ classes, 40+ enums, complete game architecture documentation
