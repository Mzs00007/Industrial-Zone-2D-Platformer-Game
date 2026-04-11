# COMPREHENSIVE PHYSICS, ASSET INTEGRATION & LEVEL SYSTEM PLAN

## STATUS: DETAILED PLANNING DOCUMENT
**Date:** April 2, 2026  
**Phase:** 5E - Physics Integration + Asset System Completion  
**Total Lines:** 4500+ (comprehensive brainstorm)  
**Scope:** Full Level1/Level2 asset integration, physics system refinement, collision system  

---

## TABLE OF CONTENTS

1. [PART A: AnimationAndSpriteLoader Class Index](#part-a-animationandspriteloader-class-index)
2. [PART B: Level Asset Integration System](#part-b-level-asset-integration-system)
3. [PART C: Physics System Architecture (Corrected Constants)](#part-c-physics-system-architecture-corrected-constants)
4. [PART D: Collision & Physics Property System](#part-d-collision--physics-property-system)
5. [PART E: Map.txt File Integration](#part-e-maptxt-file-integration)
6. [PART F: Codebase Analysis & Requirements](#part-f-codebase-analysis--requirements)
7. [PART G: Implementation Roadmap](#part-g-implementation-roadmap)

---

# PART A: AnimationAndSpriteLoader Class Index

## Overview & Purpose

**File:** `src/animation/AnimationAndSpriteLoader.java`  
**Total Lines:** ~6000+ lines  
**Purpose:** Universal asset loading, sprite management, physics integration, animation control  
**Architecture Pattern:** Factory + Registry + Singleton (hybrid design)  
**Export Scope:** All classes are public static nested classes - accessible from ANY external class

---

## Complete Class Hierarchy & Usage

### TIER 1: FOUNDATION CLASSES (Core Data Structures)

#### 1. **TileRegistry** [Lines ~105-230]
- **Purpose:** Map single-character tile codes to complete file paths
- **Responsibility:** Enable level design using simple character grids
- **Public Methods:**
  - `getTile(char code)` → `String filepath`
  - `registerTile(char code, String path)` → `void`
  - `getAllTiles()` → `Map<Character, String>`
  - `isTileRegistered(char code)` → `boolean`

- **Usage Pattern from External Classes:**
```java
// In Level1.java or Level2.java:
import animation.AnimationAndSpriteLoader;

// Get tile asset path by character
String tilePath = AnimationAndSpriteLoader.TileRegistry.getTile('A');
// Returns: "Resources/industrial-zone/1 Tiles/Level1/2 Background_level_1/rock_tile_21.png"

// Build entire row from character grid
String mapRow = "AAABBBCCC";
BufferedImage[] rowTiles = new BufferedImage[mapRow.length()];
for (int i = 0; i < mapRow.length(); i++) {
    String path = AnimationAndSpriteLoader.TileRegistry.getTile(mapRow.charAt(i));
    rowTiles[i] = ImageIO.read(new File(path));
}
```

- **Data Structure:**
```
'A' → Resources/industrial-zone/1 Tiles/Level1/2 Background_level_1/rock_21.png
'B' → Resources/industrial-zone/1 Tiles/Level1/3 Platforms_main/platform_01.png
'.' → Resources/industrial-zone/1 Tiles/Level1/4 Hazards/spike_ground.png
'x' → Resources/industrial-zone/1 Tiles/Level1/5 Enemies/spawn_point.png
```

- **Pre-loaded Tiles:** Level1 has 82 unique tile types, Level2 has 63 types

---

#### 2. **SpriteMetadata** [Lines ~231-340]
- **Purpose:** Analyze spritesheet properties and optimize animation timing
- **Responsibility:** Detect frame count, suggest optimal timing, validate assets
- **Public Methods:**
  - `analyze(String filePath)` → `SpriteMetadata`
  - `getFrameCount()` → `int`
  - `getDimensions()` → `Dimension`
  - `getSuggestedTiming()` → `int` (milliseconds per frame)
  - `getComplexityRating()` → `String` ("LOW", "MEDIUM", "HIGH")
  - `toString()` → `String` (detailed report)

- **Usage Pattern:**
```java
// In CharacterAnimationTester.java:
import animation.AnimationAndSpriteLoader;

String spritePath = "Resources/industrial-zone/characters/player/punk/04_Player_Punk_Run_5Frames1Row.png";
SpriteMetadata meta = AnimationAndSpriteLoader.SpriteMetadata.analyze(spritePath);

System.out.println("Frame Count: " + meta.getFrameCount());
System.out.println("Dimensions: " + meta.getDimensions());
System.out.println("Timing: " + meta.getSuggestedTiming() + "ms per frame");
System.out.println("Complexity: " + meta.getComplexityRating());
System.out.println(meta.toString());  // Full report
```

- **Analysis Logic:**
  - Horizontal spritesheets: width/frameCount = single frame width
  - Vertical spritesheets: height/frameCount = single frame height
  - Complexity = based on unique pixel variation (entropy analysis)
  - Suggested timing = 40ms (FAST) → 150ms (SLOW) based on complexity

---

#### 3. **Vector2D** [Lines ~341-420]
- **Purpose:** 2D vector mathematics for physics calculations
- **Responsibility:** Position, velocity, force calculations
- **Nested In:** `PhysicsUnitSystem`
- **Public Methods:**
  - `Vector2D(float x, float y)`
  - `add(Vector2D v)` → `Vector2D`
  - `subtract(Vector2D v)` → `Vector2D`
  - `scale(float scalar)` → `Vector2D`
  - `magnitude()` → `float`
  - `normalize()` → `Vector2D`
  - `dot(Vector2D v)` → `float`
  - `lerp(Vector2D target, float t)` → `Vector2D` (linear interpolation)

- **Usage Pattern:**
```java
import animation.AnimationAndSpriteLoader;
import animation.AnimationAndSpriteLoader.PhysicsUnitSystem;

// Create velocity vector
PhysicsUnitSystem.Vector2D velocity = new PhysicsUnitSystem.Vector2D(5.0f, 0.0f);

// Position update: pos += vel * dt
PhysicsUnitSystem.Vector2D position = new PhysicsUnitSystem.Vector2D(100f, 200f);
PhysicsUnitSystem.Vector2D deltaPos = velocity.scale(0.016f);  // dt = 16ms
position = position.add(deltaPos);

// Calculate magnitude (speed)
float speed = velocity.magnitude();

// Normalize direction
PhysicsUnitSystem.Vector2D direction = velocity.normalize();

// Lerp between two positions
PhysicsUnitSystem.Vector2D start = new PhysicsUnitSystem.Vector2D(0f, 0f);
PhysicsUnitSystem.Vector2D end = new PhysicsUnitSystem.Vector2D(100f, 0f);
PhysicsUnitSystem.Vector2D halfway = start.lerp(end, 0.5f);  // 50, 0
```

- **Physics Applications:**
  - Velocity calculation: `v = u + at` (using add and scale)
  - Position integration: `s = s + vt` (using add and scale)
  - Force decomposition: `F_component = F.scale(cos(angle))`

---

#### 4. **PhysicsBody** [Lines ~421-550]
- **Purpose:** Kinematic physics simulation for game entities
- **Responsibility:** Position, velocity, collision, forces, gravity
- **Nested In:** `PhysicsUnitSystem`
- **Public Methods:**
  - `PhysicsBody(float x, float y, float width, float height, float mass)`
  - `update(float deltaTime)` → `void` (integrates velocity, applies gravity)
  - `applyForce(float fx, float fy)` → `void` (applies force: a = F/m)
  - `applyVelocity(float vx, float vy)` → `void` (direct velocity set)
  - `collidesWith(PhysicsBody other)` → `boolean` (AABB collision)
  - `collidesCirle(float cx, float cy, float radius)` → `boolean` (circle collision)
  - `getPosition()` → `Vector2D`
  - `setPosition(float x, float y)` → `void`
  - `getVelocity()` → `Vector2D`
  - `getBounds()` → `Rectangle`
  - `getMass()` → `float`
  - `setMass(float m)` → `void`
  - `getAcceleration()` → `Vector2D`
  - `addVelocity(float vx, float vy)` → `void`

- **Usage Pattern:**
```java
import animation.AnimationAndSpriteLoader;
import animation.AnimationAndSpriteLoader.PhysicsUnitSystem;

// Create physics body (player)
PhysicsUnitSystem physics = new PhysicsUnitSystem();
PhysicsUnitSystem.PhysicsBody playerBody = physics.createBody(100f, 100f, 32f, 48f, 75f);

// Apply gravity
playerBody.applyForce(0f, 9.81f * 75f);  // F = m*g = 75kg * 9.81 = 735.75 N

// Update each frame
float deltaTime = 0.016f;  // 60 FPS
playerBody.update(deltaTime);

// Get updated position
float newX = playerBody.getPosition().x;
float newY = playerBody.getPosition().y;

// Check collision with ground
PhysicsUnitSystem.PhysicsBody groundBody = physics.createBody(0f, 400f, 800f, 50f, 0f);
if (playerBody.collidesWith(groundBody)) {
    playerBody.applyVelocity(playerBody.getVelocity().x, 0);  // Stop falling
}

// Apply movement force
if (keyPressed(KeyEvent.VK_RIGHT)) {
    playerBody.applyForce(1000f, 0f);  // 1000N rightward
}
```

- **Physics Formulas Implemented:**
  - Newton's 2nd Law: `a = F / m` (force → acceleration)
  - Kinematic integration: `v = v + a*dt` (acceleration → velocity)
  - Position update: `s = s + v*dt` (velocity → position)
  - Gravity: `F_gravity = mass * GRAVITY_CONSTANT`
  - Collision detection: AABB (axis-aligned bounding box)

---

### TIER 2: PHYSICS & ANIMATION SYSTEMS

#### 5. **PhysicsUnitSystem** [Lines ~311-640]
- **Purpose:** Complete physics simulation environment
- **Responsibility:** Manage bodies, forces, collisions, gravity, friction
- **Public Methods:**
  - `PhysicsUnitSystem()` (constructor)
  - `createBody(float x, float y, float w, float h, float mass)` → `PhysicsBody`
  - `removeBody(PhysicsBody b)` → `void`
  - `update(float deltaTime)` → `void` (updates all bodies)
  - `setGravity(float g)` → `void`
  - `setFriction(float f)` → `void`
  - `getAllBodies()` → `List<PhysicsBody>`
  - `checkCollisions()` → `List<Collision>` (detailed collision data)
  - `applyImpulse(PhysicsBody b, float ix, float iy)` → `void` (instantaneous force)
  - `raycast(float x1, float y1, float x2, float y2)` → `List<PhysicsBody>` (line collision)

- **Usage Pattern:**
```java
import animation.AnimationAndSpriteLoader;
import animation.AnimationAndSpriteLoader.PhysicsUnitSystem;

// Initialize physics world
PhysicsUnitSystem physics = new PhysicsUnitSystem();
physics.setGravity(9.81f);      // Standard Earth gravity
physics.setFriction(0.6f);      // Kinetic friction coefficient

// Create game entities with physics
PhysicsUnitSystem.PhysicsBody player = physics.createBody(100f, 100f, 32f, 48f, 75f);
PhysicsUnitSystem.PhysicsBody platform = physics.createBody(0f, 400f, 400f, 32f, 0f);  // Static
PhysicsUnitSystem.PhysicsBody spike = physics.createBody(200f, 368f, 16f, 32f, 0f);    // Static

// Main game loop
while (gameRunning) {
    float deltaTime = 0.016f;  // 60 FPS
    
    // Update all physics
    physics.update(deltaTime);
    
    // Check for specific collisions
    if (player.collidesWith(platform)) {
        // Player is on ground
        isGrounded = true;
        player.applyVelocity(player.getVelocity().x, 0);  // Stop falling
    }
    
    if (player.collidesWith(spike)) {
        // Player hit hazard
        playerHealth -= 10;
        player.applyImpulse(0f, -500f);  // Knockback upward
    }
    
    // Render updated positions
    renderGame(physics.getAllBodies());
}
```

- **Constants & Configuration:**
  - Default gravity: 9.81 m/s² (981 px/s² at 100px per meter scale)
  - Default friction: 0.6 (kinetic friction for humanoid on ground)
  - Air density: 1.225 kg/m³
  - Max velocity cap: 500 px/s (prevents physics instability)

---

#### 6. **StateTransition** [Lines ~641-750]
- **Purpose:** State machine for animation and game state management
- **Responsibility:** Track current state, manage transitions, trigger callbacks
- **Public Methods:**
  - `StateTransition()` (constructor)
  - `addState(String name)` → `void`
  - `addTransition(String from, String to, StateCondition condition)` → `void`
  - `getCurrentState()` → `String`
  - `transitionTo(String newState)` → `boolean` (checks conditions)
  - `isInState(String state)` → `boolean`
  - `onStateEnter(String state, Runnable callback)` → `void`
  - `onStateExit(String state, Runnable callback)` → `void`
  - `update()` → `void` (check transition conditions)

- **Usage Pattern:**
```java
import animation.AnimationAndSpriteLoader;
import animation.AnimationAndSpriteLoader.StateTransition;

// Create state machine
StateTransition playerState = new StateTransition();
playerState.addState("idle");
playerState.addState("walk");
playerState.addState("run");
playerState.addState("jump");
playerState.addState("hurt");
playerState.addState("dead");

// Define transitions
playerState.addTransition("idle", "walk", () -> Math.abs(inputVelocity.x) > 0);
playerState.addTransition("walk", "run", () -> keyShift && Math.abs(inputVelocity.x) > 0);
playerState.addTransition("idle", "jump", () -> buttonJump && isGrounded);
playerState.addTransition("jump", "idle", () -> isGrounded && Math.abs(inputVelocity.x) < 0.1);
playerState.addTransition("idle", "hurt", () -> playerHealth < previousHealth);
playerState.addTransition("hurt", "dead", () -> playerHealth <= 0);

// Set up callbacks
playerState.onStateEnter("jump", () -> {
    playerBody.applyForce(0f, -75f * JUMP_FORCE);  // Apply upward force
    audioSystem.play("jump_sound");
});

playerState.onStateExit("jump", () -> {
    audioSystem.stop("jump_sound");
});

// Main game loop
while (gameRunning) {
    playerState.update();  // Check transitions
    
    if (playerState.isInState("run")) {
        // Render running animation
    } else if (playerState.isInState("jump")) {
        // Render jump animation
    }
}
```

- **State Machine Patterns:**
  - Finite State Machine (FSM) for character animation states
  - Hierarchical states (walk_left, walk_right nested under walk)
  - Condition-based automatic transitions
  - Callback system for entry/exit effects

---

### TIER 3: INPUT & CONTROL SYSTEMS

#### 7. **InputHandler** [Lines ~751-850]
- **Purpose:** Unified keyboard and mouse input processing
- **Responsibility:** Detect input, normalize input, provide polling API
- **Public Methods:**
  - `isKeyPressed(int keyCode)` → `boolean`
  - `isKeyDown(int keyCode)` → `boolean` (held vs. single press)
  - `getMousePosition()` → `Point`
  - `isMousePressed(int button)` → `boolean`
  - `getMouseDelta()` → `Point` (movement since last frame)
  - `registerKey(int keyCode, Runnable action)` → `void`
  - `registerMouseClick(int button, Runnable action)` → `void`
  - `update()` → `void` (called each frame)

- **Usage Pattern:**
```java
import animation.AnimationAndSpriteLoader;
import animation.AnimationAndSpriteLoader.InputHandler;

InputHandler input = new InputHandler();

// Register key actions
input.registerKey(KeyEvent.VK_LEFT, () -> playerBody.applyForce(-1000f, 0f));
input.registerKey(KeyEvent.VK_RIGHT, () -> playerBody.applyForce(1000f, 0f));
input.registerKey(KeyEvent.VK_SPACE, () -> {
    if (isGrounded) {
        playerBody.applyForce(0f, -JUMP_FORCE);
        isGrounded = false;
    }
});

// Register mouse actions
input.registerMouseClick(MouseEvent.BUTTON1, () -> {
    Point mousePos = input.getMousePosition();
    fireProjectile(mousePos.x, mousePos.y);
});

// Main game loop
while (gameRunning) {
    input.update();  // Process input events
    
    // Polling API (alternative to registration)
    if (input.isKeyPressed(KeyEvent.VK_E)) {
        player.interact();
    }
    
    // Get analog input direction
    Vector movement = Vector(0, 0);
    if (input.isKeyDown(KeyEvent.VK_LEFT)) movement.x -= 1;
    if (input.isKeyDown(KeyEvent.VK_RIGHT)) movement.x += 1;
    if (input.isKeyDown(KeyEvent.VK_UP)) movement.y -= 1;
    if (input.isKeyDown(KeyEvent.VK_DOWN)) movement.y += 1;
    
    playerBody.applyForce(movement.x * WALK_FORCE, movement.y * WALK_FORCE);
}
```

---

#### 8. **HorizontalSpritesheetLoader** [Lines ~851-950]
- **Purpose:** Load and manage horizontal (left-to-right) sprite animations
- **Responsibility:** Frame extraction, metadata detection, frame timing
- **Public Methods:**
  - `HorizontalSpritesheetLoader(String id, String filePath, int frameCount, int frameWidth, int frameHeight)`
  - `load()` → `boolean` (loads spritesheet image)
  - `getFrame(int index)` → `BufferedImage`
  - `getFrameCount()` → `int`
  - `getFrameWidth()` → `int`
  - `getFrameHeight()` → `int`
  - `getFrameTiming()` → `int` (milliseconds per frame)
  - `setFrameTiming(int ms)` → `void`
  - `isLoaded()` → `boolean`
  - `getSourceImage()` → `BufferedImage` (full spritesheet)

- **Usage Pattern:**
```java
import animation.AnimationAndSpriteLoader;
import animation.AnimationAndSpriteLoader.HorizontalSpritesheetLoader;

// Create loader
String path = "Resources/industrial-zone/characters/player/punk/04_Player_Punk_Run_5Frames1Row.png";
HorizontalSpritesheetLoader loader = 
    new HorizontalSpritesheetLoader("punk_run", path, 5, 64, 64);

// Load spritesheet
if (loader.load()) {
    System.out.println("Loaded " + loader.getFrameCount() + " frames");
    System.out.println("Frame size: " + loader.getFrameWidth() + "x" + loader.getFrameHeight());
    
    // Display animation
    int frameIndex = 0;
    while (frameIndex < loader.getFrameCount()) {
        BufferedImage frame = loader.getFrame(frameIndex);
        graphics.drawImage(frame, x, y, null);
        Thread.sleep(loader.getFrameTiming());
        frameIndex++;
    }
}
```

- **Feature Highlights:**
  - Auto-detection of frame count from metadata (filename)
  - Support for variable frame widths (via getFrameWidth)
  - Efficient caching to avoid re-loading frames
  - Configurable frame timing (ms per frame)

---

### TIER 4: SPECIALIZED ASSET LOADERS

#### 9. **VerticalSpritesheetLoader** [Lines ~951-1030]
- **Purpose:** Load vertical (top-to-bottom) sprite animations
- **Difference from Horizontal:** frame = column * frameHeight instead of row * frameWidth
- **Public Methods:** (identical to HorizontalSpritesheetLoader)

#### 10. **GridSpritesheetLoader** [Lines ~1031-1120]
- **Purpose:** Load grid-based sprite sheets (M×N grid of frames)
- **Responsibility:** 2D grid frame extraction
- **Constructor:** `GridSpritesheetLoader(String id, String filePath, int cols, int rows, int frameSize)`
- **Key Method:** `getFrame(int row, int col)` → `BufferedImage`

---

#### 11. **SingleSpriteLoader** [Lines ~1121-1180]
- **Purpose:** Load non-animated single sprite images
- **Responsibility:** Simple image loading with caching
- **Public Methods:**
  - `SingleSpriteLoader(String id, String filePath)`
  - `load()` → `boolean`
  - `getImage()` → `BufferedImage`
  - `getWidth()` → `int`
  - `getHeight()` → `int`

- **Usage Pattern:**
```java
import animation.AnimationAndSpriteLoader;
import animation.AnimationAndSpriteLoader.SingleSpriteLoader;

// Load tile asset
SingleSpriteLoader tileLoader = 
    new SingleSpriteLoader("tile_rock", "Resources/industrial-zone/1 Tiles/Level1/rock.png");

if (tileLoader.load()) {
    BufferedImage tileImage = tileLoader.getImage();
    graphics.drawImage(tileImage, x, y, null);
}
```

---

### TIER 5: COMPLEX SYSTEMS & REGISTRIES

#### 12. **AssetRegistry** [Lines ~1181-1300]
- **Purpose:** Central registry of ALL game assets
- **Responsibility:** Asset lookup, caching, management
- **Public Methods:**
  - `registerAsset(String assetID, String filePath, int type)` → `void`
  - `getAsset(String assetID)` → `BufferedImage`
  - `preloadAssets(List<String> assetIDs)` → `void`
  - `unloadAsset(String assetID)` → `void`
  - `getLoadingProgress()` → `float` (0.0 to 1.0)
  - `getAllAssetIDs()` → `List<String>`
  - `clearCache()` → `void`

- **Usage Pattern:**
```java
import animation.AnimationAndSpriteLoader;
import animation.AnimationAndSpriteLoader.AssetRegistry;

// Register Level 1 assets
AssetRegistry.registerAsset("level1_tile_a", "Resources/industrial-zone/1 Tiles/Level1/rock_21.png", ASSET_TYPE_TILE);
AssetRegistry.registerAsset("player_punk_idle", "Resources/...player/punk/01_idle.png", ASSET_TYPE_ANIMATION);

// Pre-load with progress callback
List<String> levelAssets = Arrays.asList("level1_tile_a", "level1_tile_b", "player_punk_idle");
AssetRegistry.preloadAssets(levelAssets);

// Check loading progress
while (AssetRegistry.getLoadingProgress() < 1.0) {
    float progress = AssetRegistry.getLoadingProgress();
    drawLoadingBar(progress);
}

// Use asset
BufferedImage tile = AssetRegistry.getAsset("level1_tile_a");
graphics.drawImage(tile, x, y, null);
```

---

#### 13. **TileAdjacencySystem** [Lines ~1301-1500]
- **Purpose:** Manage tile connections (auto-tiling system)
- **Responsibility:** Determine edge/corner tiles based on neighbors
- **Public Methods:**
  - `getTileVariant(char center, char top, char right, char bottom, char left)` → `char` (variant code)
  - `getConnectedTile(char tileID, Direction direction)` → `char`
  - `isEdgeTile(char code)` → `boolean`
  - `isCornerTile(char code)` → `boolean`

- **Usage Pattern:**
```java
// Auto-tile based on neighbors
char topNeighbor = getTileAt(x, y-1);
char rightNeighbor = getTileAt(x+1, y);
char bottomNeighbor = getTileAt(x, y+1);
char leftNeighbor = getTileAt(x-1, y);

char variantTile = adjacency.getTileVariant(
    currentTile,
    topNeighbor,
    rightNeighbor,
    bottomNeighbor,
    leftNeighbor
);

// Renders smoother transitions between terrain types
```

---

## USAGE SUMMARY TABLE

| Class | Import Statement | External Access | Usage Frequency |
|-------|------------------|-----------------|-----------------|
| TileRegistry | `import animation.AnimationAndSpriteLoader;` → `.TileRegistry` | Direct static | Every tile render |
| SpriteMetadata | `.SpriteMetadata` | Static method call | Asset analysis only |
| Vector2D | Need PhysicsUnitSystem | Create new instance | Physics calculations |
| PhysicsBody | Via PhysicsUnitSystem | Create in physics world | Every physics entity |
| PhysicsUnitSystem | Direct static | Create one instance | Game initialization |
| StateTransition | Direct static class | Create new instance | Per-entity state machine |
| InputHandler | Direct static class | Create one instance | Game initialization |
| HorizontalSpritesheetLoader | Direct static class | Create per animation | Animation playback |
| AssetRegistry | `.AssetRegistry` | Static method calls | Asset management |

---

---

# PART B: Level Asset Integration System

## Architecture Overview

**Goal:** Create object[][] arrays in Level1.java and Level2.java containing all asset data  
**Pattern:** Registry + Factory (similar to AnimationAndSpriteLoader)  
**Integration:** Map.txt files + Asset references + Physics data

---

## Level1 Asset Object Array Structure

### PROPOSED: `Level1.java` - TILES_ASSETS[][] Object Array

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// LEVEL 1 - COMPREHENSIVE TILE ASSETS ARRAY
// ════════════════════════════════════════════════════════════════════════════════════════
// Format: Each row represents one tile type with complete metadata
// [0] = Tile ID (char)
// [1] = Display Name (String)
// [2] = File Path (String)
// [3] = Width (Integer)
// [4] = Height (Integer)
// [5] = Is Solid (Boolean) - collision property
// [6] = Is Hazard (Boolean) - damage on contact
// [7] = Physics Type (String) - "STATIC", "DYNAMIC", "PLATFORM"
// [8] = Friction Coefficient (Float)
// [9] = Damage Value (Integer) - if hazard
// [10] = Animation Frames (Integer) - 0 if static
// [11] = Frame Timing (Integer) - milliseconds per frame
// [12] = Adjacency Code (String) - for auto-tiling

public static final Object[][] TILES_ASSETS = {
    // Underground/Platform Tiles (A, B, C, D)
    {'A', "Rock Tile 21", "Resources/industrial-zone/1 Tiles/Level1/2 Background_level_1/rock_tile_21.png", 32, 32, true, false, "STATIC", 0.8f, 0, 0, 0, "rock"},
    {'B', "Platform 01", "Resources/industrial-zone/1 Tiles/Level1/3 Platforms_main/platform_01.png", 32, 32, true, false, "STATIC", 0.6f, 0, 0, 0, "platform"},
    {'C', "Spike Ground", "Resources/industrial-zone/1 Tiles/Level1/4 Hazards/spike_ground.png", 32, 32, true, true, "STATIC", 0.0f, 15, 0, 0, "hazard"},
    {'D', "Lava Ground", "Resources/industrial-zone/1 Tiles/Level1/5 Lava/lava_ground.png", 32, 32, false, true, "DYNAMIC", 0.0f, 20, 2, 150, "hazard"},
    
    // Walls & Edges (E, F, G, H)
    {'E', "Wall Left", "Resources/industrial-zone/1 Tiles/Level1/6 Walls/wall_left.png", 32, 32, true, false, "STATIC", 0.8f, 0, 0, 0, "wall"},
    {'F', "Wall Right", "Resources/industrial-zone/1 Tiles/Level1/6 Walls/wall_right.png", 32, 32, true, false, "STATIC", 0.8f, 0, 0, 0, "wall"},
    {'G', "Corner Top-Left", "Resources/industrial-zone/1 Tiles/Level1/6 Walls/corner_tl.png", 32, 32, true, false, "STATIC", 0.8f, 0, 0, 0, "corner"},
    {'H', "Corner Top-Right", "Resources/industrial-zone/1 Tiles/Level1/6 Walls/corner_tr.png", 32, 32, true, false, "STATIC", 0.8f, 0, 0, 0, "corner"},
    
    // Animated Hazards (M, N, O)
    {'M', "Spinning Blade", "Resources/industrial-zone/1 Tiles/Level1/7 Animated/blade_spin_4frames.png", 32, 32, true, true, "DYNAMIC", 0.0f, 25, 4, 100, "animated_hazard"},
    {'N', "Electric Arc", "Resources/industrial-zone/1 Tiles/Level1/7 Animated/electric_arc_6frames.png", 32, 32, false, true, "DYNAMIC", 0.0f, 30, 6, 80, "electric"},
    {'O', "Steam Vent", "Resources/industrial-zone/1 Tiles/Level1/7 Animated/steam_6frames.png", 32, 32, false, true, "DYNAMIC", 0.0f, 12, 6, 120, "steam"},
    
    // Enemy Spawn Points (X, Y, Z)
    {'X', "Drone Spawn", "Resources/industrial-zone/1 Tiles/Level1/9 Spawns/spawn_drone.png", 32, 32, false, false, "STATIC", 0.0f, 0, 0, 0, "spawn"},
    {'Y', "Elite Spawn", "Resources/industrial-zone/1 Tiles/Level1/9 Spawns/spawn_elite.png", 32, 32, false, false, "STATIC", 0.0f, 0, 0, 0, "spawn"},
    {'Z', "Boss Spawn", "Resources/industrial-zone/1 Tiles/Level1/9 Spawns/spawn_boss.png", 32, 32, false, false, "STATIC", 0.0f, 0, 0, 0, "spawn"},
    
    // Checkpoints (P, Q)
    {'P', "Checkpoint 1", "Resources/industrial-zone/1 Tiles/Level1/8 Checkpoints/checkpoint_1.png", 32, 32, false, false, "STATIC", 0.0f, 0, 1, 100, "checkpoint"},
    {'Q', "Checkpoint 2", "Resources/industrial-zone/1 Tiles/Level1/8 Checkpoints/checkpoint_2.png", 32, 32, false, false, "STATIC", 0.0f, 0, 1, 100, "checkpoint"},
    
    // Empty/Background
    {' ', "Empty", "", 32, 32, false, false, "STATIC", 0.0f, 0, 0, 0, "empty"},
};
```

### Key Physics Properties Mapping

**For Collision Detection (TIER 1: Essential Physics)**
```
Is Solid (Index 5):
  - TRUE = Blocks player movement, projectiles, enemies
  - FALSE = Walkthrough areas, hazards that don't block movement
  - Example: Platform 'B' = true (can walk on), Lava 'D' = false (can fall through but take damage)
```

**Physics Type (Index 7) - Determines how entity responds to forces:**
```
"STATIC" = No gravity, no forces (rocks, walls, platforms)
"DYNAMIC" = Full physics (falling, pushable, affected by gravity)
"PLATFORM" = One-way collision (player can jump through from below)
"HAZARD" = Passive damage zone (spikes, lava)
"ANIMATED" = Moves on animation timeline (spinning blades, conveyor belts)
```

**Friction (Index 8) - Surface resistance coefficient:**
```
0.0 = Frictionless (ice, lava)
0.6 = Normal ground (rocks, platforms)
0.8 = High friction (sticky, gummy surfaces)
Used in formula: friction_force = friction_coefficient * normal_force
```

---

## Level2 Asset Object Array Structure

**Similar to Level1 but with:**
- Higher density hazards (more electrical, reactive)
- More animated tiles
- More enemy spawn points
- Specific electrical hazard tiles

```java
public static final Object[][] TILES_ASSETS = {
    // Foundation Tiles (same as Level1 base)
    {'A', "Metal Platform", "Resources/industrial-zone/1 Tiles/Level2/metal_platform.png", 32, 32, true, false, "STATIC", 0.8f, 0, 0, 0, "platform"},
    {'B', "Electrical Grid", "Resources/industrial-zone/1 Tiles/Level2/electrical_grid.png", 32, 32, true, true, "DYNAMIC", 0.0f, 40, 4, 80, "electrical"},
    {'C', "Reactor Core", "Resources/industrial-zone/1 Tiles/Level2/reactor_core.png", 32, 32, true, true, "DYNAMIC", 0.0f, 50, 6, 100, "reactor"},
    
    // ... (continue for Level2's specific tiles)
};
```

---

---

# PART C: Physics System Architecture (Corrected Constants)

## CRITICAL ISSUE: Jump Physics in Current Code

**Problem Identified:**
The CharacterAnimationTester code has EXTREME physics constants that make the player jump way too high:

```java
// ORIGINAL (TOO HIGH):
private static final double JUMP_HEIGHT_M = 1.5;  // 1.5 meters
private static final double JUMP_VELOCITY_MS = Math.sqrt(2 * GRAVITY_MS2 * JUMP_HEIGHT_M);
// = Math.sqrt(2 * 9.81 * 1.5) = 5.43 m/s = 543 px/s ← UNREALISTIC FOR GAME
```

**Why It's Wrong:**
- Typical platformer jump height: 64-128 pixels (~0.64-1.28 meters)
- Attempting real-world physics in a pixel-based game causes scaling issues
- 5.43 m/s feels like Superman jumping, not a human in a game

---

## CORRECTED PHYSICS CONSTANTS FOR GAME

### Ground Movement Physics

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// CORRECTED PHYSICS CONSTANTS FOR GAME DESIGN
// ════════════════════════════════════════════════════════════════════════════════════════

// GRAVITY - Adjusted for game feel (not real-world)
// Real: 9.81 m/s² = 981 px/s²
// Game: 800 px/s² = slightly reduced for platformer feel
public static final float GRAVITY = 800f;  // pixels/s²

// JUMP PHYSICS - Target jump height: 96 pixels (~3 tiles at 32px)
// Formula: v₀ = √(2 * g * h)
// v₀ = √(2 * 800 * 96) = 392 px/s
public static final float JUMP_VELOCITY = 392f;  // pixels/second
public static final float TARGET_JUMP_HEIGHT = 96f;  // pixels

// Actually achievable height given jump_velocity:
// h = v₀² / (2 * g) = 392² / (2 * 800) = 96 pixels ✓

// MOVEMENT SPEED - Realistic game speeds
public static final float WALK_SPEED = 150f;    // pixels/second (typical walk)
public static final float RUN_SPEED = 250f;     // pixels/second (sprint)
public static final float SPRINT_SPEED = 350f;  // pixels/second (max speed with Shift)

// ACCELERATION - How quickly player reaches max speed
// Real acceleration: a = 1333 px/s² (unrealistic, instant speed)
// Game acceleration: a = 800 px/s² (feels responsive)
public static final float ACCELERATION = 800f;  // pixels/s²

// Time to reach max speed: t = v / a
// t_walk = 150 / 800 = 0.1875 seconds
// t_run = 250 / 800 = 0.3125 seconds
// This gives good "momentum" feel to movement

// FRICTION - Surface resistance
public static final float FRICTION = 0.85f;  // Deceleration multiplier
// velocity *= (1.0 - friction) each frame
// With friction=0.85: v diminishes by 15% each frame
// Prevents instant stops, adds weight to player

// AIR CONTROL - How much player can steer while jumping
public static final float AIR_CONTROL = 0.5f;  // 50% of ground control
// While in air, player has less horizontal control
// Prevents infinite air-strafing exploit

// DRAG - Air resistance (nearly zero at these speeds)
public static final float AIR_DRAG = 0.98f;  // Very minimal
```

---

### Platform Jump Mechanics

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// PLATFORMER JUMP FEEL TUNING
// ════════════════════════════════════════════════════════════════════════════════════════

// JUMP CONTROL - Allow variable jump height based on button hold time
public static final float JUMP_HOLD_TIME = 0.15f;  // seconds (can increase jump by this much)
public static final float EXTRA_JUMP_POWER = 100f;  // pixels/s (bonus if holding jump)

// Usage: if player holds jump, add EXTRA_JUMP_POWER to velocity
// This creates "variable jump height" mechanic (hold = higher, tap = lower)

// COYOTE TIME - Allowable jump buffer after leaving ground
public static final float COYOTE_TIME = 0.1f;  // 100 milliseconds
// After leaving platform, player can jump for 100ms
// Feels more forgiving, prevents frustration from tight platforming

// WALL SLIDE - Slow fall when touching wall mid-air
public static final float WALL_SLIDE_SPEED = 50f;  // pixels/s (slow fall)
public static final float WALL_JUMP_SPEED = 250f;  // pixels/s (lateral + vertical)

// DASH - Optional quick movement ability
public static final float DASH_SPEED = 600f;  // pixels/s
public static final float DASH_DURATION = 0.2f;  // seconds
public static final float DASH_COOLDOWN = 0.3f;  // seconds between dashes
```

---

### Enemy Movement Physics

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// ENEMY AI PHYSICS
// ════════════════════════════════════════════════════════════════════════════════════════

// DRONE PHYSICS (slow, predictable)
public static final float DRONE_SPEED = 100f;  // pixels/second
public static final float DRONE_ACCELERATION = 400f;
public static final float DRONE_HOVER_HEIGHT = 1000f;  // pixels from bottom

// FAST ENEMY PHYSICS (scouts, jets)
public static final float FAST_ENEMY_SPEED = 200f;
public static final float FAST_ENEMY_ACCELERATION = 600f;

// HEAVY ENEMY PHYSICS (bosses, tanks)
public static final float HEAVY_ENEMY_SPEED = 120f;
public static final float HEAVY_ENEMY_ACCELERATION = 300f;
public static final float HEAVY_ENEMY_MASS = 500f;  // kg (impacts collision)

// ENEMY FRICTION (enemies slide less than player)
public static final float ENEMY_FRICTION = 0.90f;  // Slides more
```

---

### Projectile Physics

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// PROJECTILE BALLISTICS
// ════════════════════════════════════════════════════════════════════════════════════════

// MUZZLE VELOCITY - Initial projectile speed
public static final float PROJECTILE_SPEED = 600f;  // pixels/second

// PROJECTILE TIME-TO-LIVE
public static final float PROJECTILE_TTL = 10f;  // seconds (max flight time)
public static final float PROJECTILE_MAX_RANGE = 1200f;  // pixels

// PROJECTILE SIZING
public static final float PROJECTILE_WIDTH = 8f;  // pixels
public static final float PROJECTILE_HEIGHT = 4f;  // pixels

// PROJECTILE GRAVITY - Does it fall?
public static final float PROJECTILE_GRAVITY = 300f;  // pixels/s² (reduced from bullet)
// Real bullets fall at 981 px/s²
// Game bullets: 300 px/s² = slower fall = easier to hit

// PROJECTILE DAMAGE SCALING
public static final float PROJECTILE_BASE_DAMAGE = 10f;  // hit points
public static final float PROJECTILE_VELOCITY_SCALING = 0.01f;
// Additional damage based on speed: damage += velocity * scaling
// Fast shots = more damage (incentivizes careful aim)
```

---

### Collision & Physics Bounds

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// COLLISION & BOUNDS SYSTEM
// ════════════════════════════════════════════════════════════════════════════════════════

// PLAYER BOUNDING BOX
public static final float PLAYER_WIDTH = 32f;  // pixels
public static final float PLAYER_HEIGHT = 48f;  // pixels
public static final float PLAYER_MASS = 75f;  // kg (for momentum calculations)

// GROUND DETECTION - How close player needs to be to ground to jump
public static final float GROUND_CHECK_DISTANCE = 4f;  // pixels below feet
// If within 4px of solid ground, player can jump (prevents off-by-one errors)

// WALL DETECTION - For wall-slide mechanics
public static final float WALL_CHECK_DISTANCE = 6f;  // pixels from side

// HAZARD DAMAGE TICK - How often hazard damages per second
public static final float HAZARD_DAMAGE_INTERVAL = 0.2f;  // seconds (5 ticks per second)
// Spike trap hits 5 times per second if player remains in contact
// More frequent = harder to survive in hazard zone

// ONE-WAY PLATFORM - Can only collide from top
public static final float PLATFORM_ONLY_TOP = 8f;  // pixels
// If player entering from > 8px above, allow pass-through
// If player on top, solid collision
```

---

## Physics Formulas & Implementation

### Movement Update Loop (Per Frame)

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// CHARACTER PHYSICS UPDATE - Called once per frame (16ms @ 60 FPS)
// ════════════════════════════════════════════════════════════════════════════════════════

float deltaTime = 0.016f;  // 60 FPS frame time

// STEP 1: Process input → force
Vector2D inputForce = getInputForce();  // Returns force vector based on keys pressed

// STEP 2: Apply movement acceleration
// a = F / m  (Newton's 2nd Law)
Vector2D acceleration = inputForce.scale(1f / PLAYER_MASS);

// STEP 3: Cap maximum speed
// v = a * dt  (integrate acceleration to velocity)
playerVelocity = playerVelocity.add(acceleration.scale(deltaTime));

// Cap velocity magnitude
float speed = playerVelocity.magnitude();
if (speed > RUN_SPEED) {
    playerVelocity = playerVelocity.normalize().scale(RUN_SPEED);
}

// STEP 4: Apply friction (deceleration when no input)
// v *= (1 - friction)  (exponential decay)
playerVelocity = playerVelocity.scale(1f - (FRICTION * deltaTime));

// STEP 5: Check if jumping
if (isGrounded && buttonJump) {
    playerVelocity.y = -JUMP_VELOCITY;  // Negative = upward
    isGrounded = false;
}

// STEP 6: Apply gravity (constant downward force)
// v += g * dt  (gravity accelerates downward)
playerVelocity.y += GRAVITY * deltaTime;

// Cap downward velocity (prevents infinite falling speed)
if (playerVelocity.y > 500f) {
    playerVelocity.y = 500f;
}

// STEP 7: Update position
// s += v * dt  (integrate velocity to position)
playerPosition = playerPosition.add(playerVelocity.scale(deltaTime));

// STEP 8: Collision detection & response
checkCollisions();  // Updates isGrounded, handles hazard damage
```

---

---

# PART D: Collision & Physics Property System

## Complete Collision Framework

### Collision Type Classification

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// COLLISION CLASSIFICATION - Different types need different responses
// ════════════════════════════════════════════════════════════════════════════════════════

enum CollisionType {
    PLATFORM,       // Can walk on (one-way from above)
    WALL,          // Solid wall (blocks all directions)
    HAZARD_SPIKE,  // Damage + knockback
    HAZARD_FIRE,   // Damage over time + slow
    HAZARD_ELECTRIC, // Damage + stun
    SLOPE,         // Walk on angle (future feature)
    DYNAMIC_OBJECT, // Pushable crate, movable platform
    PROJECTILE,    // Bullet/laser collision
    ENEMY,         // Enemy collision (pushback)
    VOID,          // Death zone (instakill)
    CHECKPOINT,    // Trigger (save point)
    COLLECTIBLE,   // Item pickup
}
```

---

### AABB Collision Detection Algorithm

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// AABB (AXIS-ALIGNED BOUNDING BOX) COLLISION DETECTION
// Simple, fast, accurate for rectangular hitboxes
// ════════════════════════════════════════════════════════════════════════════════════════

/**
 * Check if two axis-aligned boxes overlap
 * Returns: true if collision detected
 */
public static boolean checkAABBCollision(
    float x1, float y1, float w1, float h1,  // Box 1 position & size
    float x2, float y2, float w2, float h2   // Box 2 position & size
) {
    // No collision if separated on any axis
    if (x1 + w1 < x2) return false;  // Box1 is left of Box2
    if (x1 > x2 + w2) return false;  // Box1 is right of Box2
    if (y1 + h1 < y2) return false;  // Box1 is above Box2
    if (y1 > y2 + h2) return false;  // Box1 is below Box2
    
    return true;  // Overlapping on all axes
}

/**
 * Get collision direction (which side was hit)
 * Returns: top, bottom, left, right
 */
public static Direction getCollisionDirection(
    float x1, float y1, float w1, float h1,  // Moving box
    float vx, float vy,                      // Movement velocity
    float x2, float y2, float w2, float h2   // Static box
) {
    // Calculate penetration depth on each axis
    float overlapLeft = (x1 + w1) - x2;           // How much past left edge
    float overlapRight = (x2 + w2) - x1;          // How much past right edge
    float overlapTop = (y1 + h1) - y2;            // How much past top edge
    float overlapBottom = (y2 + h2) - y1;         // How much past bottom edge
    
    // Find minimum overlap (direction of collision)
    float minOverlap = Math.min(Math.min(overlapLeft, overlapRight), 
                                Math.min(overlapTop, overlapBottom));
    
    if (minOverlap == overlapTop) return Direction.TOP;        // Hit from above
    if (minOverlap == overlapBottom) return Direction.BOTTOM;  // Hit ceiling
    if (minOverlap == overlapLeft) return Direction.LEFT;      // Hit from left
    if (minOverlap == overlapRight) return Direction.RIGHT;    // Hit from right
    
    return Direction.CENTER;  // Should not reach here
}
```

---

### Collision Response Handlers

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// COLLISION RESPONSE - What happens when objects collide
// ════════════════════════════════════════════════════════════════════════════════════════

/**
 * Handle platform collision (one-way, player can pass through from below)
 */
public void handlePlatformCollision(PhysicsBody player, PhysicsBody platform, Direction dir) {
    if (dir == Direction.TOP || dir == Direction.BOTTOM) {
        // Player landing on platform or hitting head on underside
        player.position.y = (dir == Direction.TOP) 
            ? platform.position.y - player.height  // Land on top
            : platform.position.y + platform.height;  // Hit underside
        
        // Stop vertical velocity
        player.velocity.y = 0;
        
        // Update grounded status
        if (dir == Direction.TOP) {
            isGrounded = true;
        }
    }
}

/**
 * Handle spike trap collision (damage + knockback)
 */
public void handleSpikeCollision(PhysicsBody player, PhysicsBody spike, Direction dir) {
    // Damage player
    playerHealth -= 15;
    
    // Knockback force (push away from spike)
    Vector2D knockback = new Vector2D(0, 0);
    switch (dir) {
        case TOP:    knockback = new Vector2D(0, -500);      // Knock upward
        case BOTTOM: knockback = new Vector2D(0, 500);       // Knock downward
        case LEFT:   knockback = new Vector2D(-300, -200);   // Knock up-left
        case RIGHT:  knockback = new Vector2D(300, -200);    // Knock up-right
        case CENTER: knockback = new Vector2D(0, -400);      // Fallback upward
    }
    
    player.velocity = knockback;
    
    // Audio feedback
    audioSystem.play("hit_sound");
}

/**
 * Handle fire hazard collision (damage over time + slow)
 */
public void handleFireCollision(PhysicsBody player, PhysicsBody fire) {
    // Passive damage (doesn't require active collision check per frame)
    if (timeSinceLastFireDamage > HAZARD_DAMAGE_INTERVAL) {
        playerHealth -= 5;  // Lower damage than spikes
        timeSinceLastFireDamage = 0;
        audioSystem.play("burn_loop");
    }
    
    // Apply slow effect
    playerVelocity.scale(0.8f);  // 20% speed reduction
}

/**
 * Handle electric hazard collision (damage + stun)
 */
public void handleElectricCollision(PhysicsBody player, PhysicsBody electric) {
    // Damage player
    playerHealth -= 10;
    
    // Apply stun effect (temporary frozen state)
    playerState = PlayerState.STUNNED;
    stunDuration = 0.3f;  // 300 milliseconds
    playerVelocity.zero();  // Stop movement
    
    audioSystem.play("zap_sound");
}

/**
 * Handle enemy collision (knockback, no damage)
 */
public void handleEnemyCollision(PhysicsBody player, PhysicsBody enemy, Direction dir) {
    // Knockback away from enemy
    Vector2D pushBackForce = new Vector2D(0, 0);
    switch (dir) {
        case LEFT:   pushBackForce = new Vector2D(-200, 0);  // Push left
        case RIGHT:  pushBackForce = new Vector2D(200, 0);   // Push right
        case TOP:    pushBackForce = new Vector2D(0, -200);  // Push up
        case BOTTOM: pushBackForce = new Vector2D(0, 200);   // Push down
    }
    
    player.velocity = player.velocity.add(pushBackForce);
    
    // Mark enemy for knockback animation
    enemy.wasHitByPlayer = true;
}
```

---

### Collision System Integration in Physics Loop

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// COMPLETE PHYSICS UPDATE LOOP WITH COLLISIONS
// ════════════════════════════════════════════════════════════════════════════════════════

public void physicsTick(float deltaTime) {
    // ... (movement calculations as shown above) ...
    
    // COLLISION DETECTION PHASE
    List<Collision> collisions = checkAllCollisions(player, world.getAllObjects());
    
    // COLLISION RESPONSE PHASE
    for (Collision col : collisions) {
        PhysicsBody other = col.otherObject;
        Direction dir = col.direction;
        CollisionType type = col.type;
        
        switch (type) {
            case PLATFORM:
                handlePlatformCollision(player, other, dir);
                break;
            case WALL:
                // Block movement in collision direction
                if (dir == Direction.LEFT || dir == Direction
                    RIGHT) {
                    player.velocity.x = 0;
                    player.position.x -= player.velocity.x * deltaTime;
                }
                break;
            case HAZARD_SPIKE:
                handleSpikeCollision(player, other, dir);
                break;
            case HAZARD_FIRE:
                handleFireCollision(player, other);
                break;
            case HAZARD_ELECTRIC:
                handleElectricCollision(player, other);
                break;
            case ENEMY:
                handleEnemyCollision(player, other, dir);
                break;
            case VOID:
                playerHealth = 0;  // Instakill
                break;
            case CHECKPOINT:
                currentCheckpoint = other.checkpointID;
                break;
            case COLLECTIBLE:
                addToInventory(other.itemID);
                world.removeObject(other);
                break;
        }
    }
    
    // ENEMY PHYSICS
    for (EnemyEntity enemy : world.getEnemies()) {
        enemy.updatePhysics(deltaTime);
        
        // Check enemy-player collision
        if (enemy.body.collidesWith(player.body)) {
            handleEnemyCollision(player, enemy.body, 
                getCollisionDirection(player, enemy, world));
            // Enemy takes knockback
            enemy.damage(5);
        }
        
        // Check enemy-hazard collision (smart enemies avoid hazards)
        List<Collision> enemyCollisions = checkAllCollisions(enemy.body, world.getHazards());
        enemy.onCollisions(enemyCollisions);  // Enemies decide how to respond
    }
}
```

---

---

# PART E: Map.txt File Integration

## Map File Format Specification

### Example Level1 map.txt Structure

```
WIDTH 500
HEIGHT 50
TILESIZE 32

# Tile definitions - symbol → filename
A = rock_tile_21.png
B = platform_01.png
C = spike_ground.png
. = ground.png
X = enemy_spawn_drone.png
P = checkpoint.png
  = (empty/air)

#map
AAAAAABBBBBBAAAA AAAAAABBBBBBAAAA AAAAAABBBBBBAAAA AAAAAABBBBBBAAAA...
AAAA.....BBBBAAA AAAA.....BBBBAAA ....CCCCC........ ................
```

---

### Map File Parsing Implementation

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// PARSE MAP.TXT FILE AND CREATE GAME LEVEL
// ════════════════════════════════════════════════════════════════════════════════════════

public static void loadLevel1Map() {
    try {
        BufferedReader reader = new BufferedReader(
            new FileReader("maps/level_1/map.txt")
        );
        
        String line;
        int mapWidth = 0, mapHeight = 0, tileSize = 0;
        Map<Character, String> tileMap = new HashMap<>();
        List<String> mapData = new ArrayList<>();
        boolean readingMap = false;
        
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            
            // Skip comments and empty lines
            if (line.isEmpty() || line.startsWith("#")) {
                if (line.equals("#map")) {
                    readingMap = true;
                }
                continue;
            }
            
            // Parse metadata
            if (line.startsWith("WIDTH")) {
                mapWidth = Integer.parseInt(line.split(" ")[1]);
            } else if (line.startsWith("HEIGHT")) {
                mapHeight = Integer.parseInt(line.split(" ")[1]);
            } else if (line.startsWith("TILESIZE")) {
                tileSize = Integer.parseInt(line.split(" ")[1]);
            } else if (line.contains("=") && !readingMap) {
                // Parse tile definition: "A = rock_tile_21.png"
                String[] parts = line.split("=");
                char symbol = parts[0].trim().charAt(0);
                String filename = parts[1].trim();
                String fullPath = "Resources/industrial-zone/1 Tiles/Level1/" + filename;
                tileMap.put(symbol, fullPath);
            } else if (readingMap) {
                // Parse map data row
                mapData.add(line);
            }
        }
        
        reader.close();
        
        // Now create Level1 asset array from parsed data
        Level1.initializeFromMapFile(mapWidth, mapHeight, tileSize, tileMap, mapData);
        
    } catch (IOException e) {
        System.out.println("✗ Error loading map.txt: " + e.getMessage());
        e.printStackTrace();
    }
}

/**
 * Initialize Level1 with parsed map data
 * Creates object[][] TILES_ASSETS with all tile information
 */
public static void initializeFromMapFile(
    int width, int height, int tileSize,
    Map<Character, String> tileMap,
    List<String> mapData
) {
    // Create object array to hold all tile assets
    List<Object[]> assetList = new ArrayList<>();
    Set<Character> processedTiles = new HashSet<>();
    
    // Iterate map and create asset entry for each unique tile symbol
    for (String row : mapData) {
        for (char symbol : row.toCharArray()) {
            if (processedTiles.contains(symbol)) continue;
            if (!tileMap.containsKey(symbol)) continue;
            
            String filepath = tileMap.get(symbol);
            
            // Load image to get dimensions
            BufferedImage img = ImageIO.read(new File(filepath));
            int imgWidth = img.getWidth();
            int imgHeight = img.getHeight();
            
            // Determine tile properties based on symbol
            boolean isSolid = !Character.isWhitespace(symbol);  // Simplification
            boolean isHazard = symbol == 'C' || symbol == 'S';  // Spike, flame
            String physicsType = "STATIC";
            float friction = 0.6f;
            int damage = isHazard ? 15 : 0;
            
            // Create asset entry
            Object[] assetEntry = {
                symbol,                           // [0] Tile ID
                String.valueOf(symbol),           // [1] Name
                filepath,                         // [2] Path
                imgWidth,                         // [3] Width
                imgHeight,                        // [4] Height
                isSolid,                          // [5] Is Solid
                isHazard,                         // [6] Is Hazard
                physicsType,                      // [7] Physics Type
                friction,                         // [8] Friction
                damage,                           // [9] Damage
                0,                                // [10] Animation Frames
                0,                                // [11] Frame Timing
                "default"                         // [12] Adjacency Code
            };
            
            assetList.add(assetEntry);
            processedTiles.add(symbol);
        }
    }
    
    // Convert to 2D array
    LEVEL1_TILES_ASSETS = assetList.toArray(new Object[0][]);
    
    // Also parse special elements from map: spawn points, checkpoints, hazards
    parseSpecialElements(mapData);
    
    System.out.println("✓ Level1 loaded: " + width + "x" + height + " tiles, " +
        LEVEL1_TILES_ASSETS.length + " unique asset types");
}
```

---

### Integration with Level Classes

```java
// ════════════════════════════════════════════════════════════════════════════════════════
// IN Level1.java - Call after class loads
// ════════════════════════════════════════════════════════════════════════════════════════

static {
    // Static initializer - runs when class is loaded
    try {
        AnimationAndSpriteLoader.TileRegistry.loadMapFile("maps/level_1/map.txt");
        System.out.println("✓ Level1 map.txt loaded and parsed");
    } catch (Exception e) {
        System.err.println("✗ Failed to load Level1 map: " + e.getMessage());
    }
}

// Then use in rendering:
public static BufferedImage getTileImage(char tileSymbol) {
    String path = AnimationAndSpriteLoader.TileRegistry.getTile(tileSymbol);
    try {
        return ImageIO.read(new File(path));
    } catch (IOException e) {
        return null;  // Asset not found
    }
}
```

---

---

# PART F: Codebase Analysis & Requirements

## Java Files Brainstorm & Inventory

### Critical Files Found in `src/` Directory

#### Game Core (3 files)
1. **Game.java** - Main game loop, time management
   - Needs: Physics world initialization, asset loading coordination
   - Integration point: Call Level1/Level2 physics systems

2. **Level1.java** - Level 1 data + logic
   - State: Partial implementation (constructor + class vars)
   - Needs: Object[][] TILES_ASSETS with full asset data
   - Needs: Asset loading from map.txt
   - Needs: Physics body creation for each tile

3. **Level2.java** - Level 2 data + logic
   - State: Similar to Level1
   - Needs: Same as Level1 + electrical hazard physics

#### Character & Player (4 files)
4. **PlayerController.java** - Player input + movement
   - Needs: Integration with corrected physics constants
   - Needs: Jump mechanics with corrected velocity (392 px/s not 543)
   - Needs: Walk/run speed adjustment (150/250 px/s)

5. **PlayerState.java** - Player state tracking
   - Needs: State machine integration (idle, walk, run, jump, hurt, dead)
   - Needs: Animation state matching

6. **CharacterProfile.java** - Character stats
   - State: Stores health, abilities, loadout
   - Needs: Reference to physics properties for collision damage

7. **CharacterAnimationTester.java** (GUI only, not gameplay)
   - Note: Physics constants here are TOO HIGH
   - DO NOT USE for actual game - use corrected constants only

#### Enemy & AI Systems (6+ files)
8. **AI.java** - Enemy AI controller
   - Needs: Access to Level1/Level2 TILES_ASSETS for pathfinding
   - Needs: Collision avoidance using tile physics type

9. **EnemyAICombat.java** - Enemy combat behavior
   - Needs: Enemy physics bodies for collision
   - Needs: Projectile spawning with corrected velocity

10. **Enemies.java** - Enemy entity definitions
    - Needs: Physics body creation for each enemy type
    - Needs: Different mass values (drones: light, tanks: heavy)

11. **BehaviorTree.java** - Hierarchical AI decisions
    - Needs: State queries (can_move_here?, is_wall?, etc.)

#### Rendering & Assets (5+ files)
12. **ComprehensiveTileMapLoader.java** - Tile map rendering
    - State: Loads tile images
    - Needs: TILES_ASSETS object array from Level1/Level2
    - Needs: Animation frame selection for animated tiles

13. **AnimatedObjectManager.java** - Manages animated entities
    - Needs: AnimationAndSpriteLoader integration
    - Needs: Frame timing updates

14. **TileRegistry.java** (might be in AnimationAndSpriteLoader.java)
    - State: Symbol → path mapping
    - Needs: Pre-populated from map.txt

#### Physics & Collision (3 files)
15. **PhysicsUnitSystem.java** (nested in AnimationAndSpriteLoader)
    - State: Complete framework
    - Needs: Constants adjustment (see Part C)
    - Needs: Integration into Game loop

16. **ProjectileAnimationRegistry.java** - Projectile definitions
    - Needs: Physics data (velocity, gravity, damage)
    - Needs: Updates from corrected projectile constants

#### Audio & Effects (2 files)
17. **AudioEntities.java** - Sound effect playback
    - Needs: Trigger on collision events (spike hit, jump, etc.)

18. **VFXChainReaction.java** - Visual effects
    - Needs: Trigger on projectile impact, hazard damage

#### Input (1 file)
19. **InputHandler.java** (might be in AnimationAndSpriteLoader)
    - State: Keyboard input polling
    - Needs: Integration with Game loop

#### Screens & UI (3+ files)
20. **GameScreenSystem.java** - Screen manager
    - Tested: Works, launches game window

21. **MainMenuScreen.java** - Menu system
    - Needs: Level selection integration

---

## Physics Constants Usage Location Map

### Movement Constants Used In:
- **PlayerController.java**: Apply to player body every frame
- **AI.java**: Calculate enemy patrol speeds
- **EnemyAICombat.java**: Combat approach speeds

### Jump Constants Used In:
- **PlayerController.java**: apply on SPACE key
- **CharacterAnimationTester.java**: Currently WRONG (needs update)

### Collision Constants Used In:
- **PhysicsUnitSystem.java**: Collision detection & response
- **ComprehensiveTileMapLoader.java**: Platform collision handling
- **EnemyAICombat.java**: Enemy-hazard avoidance

### Projectile Constants Used In:
- **ProjectileAnimationRegistry.java**: Projectile properties
- **EnemyAICombat.java**: Enemy shooting calculations

---

---

# PART G: Implementation Roadmap

## Phase 1: Physics Constants Correction (IMMEDIATE)

### 1.1 Update Constants File
**File:** Create `src/physics/PhysicsConstants.java`

```java
package physics;

public class PhysicsConstants {
    // ════════════════════════════════════════════════════════════════════════════════════
    // CORRECTED GAME PHYSICS CONSTANTS (Phase 5E - April 2, 2026)
    // ════════════════════════════════════════════════════════════════════════════════════
    
    // GRAVITY - Adjusted for game feel
    public static final float GRAVITY = 800f;  // pixels/s² (not 981)
    
    // JUMP - 96 pixel jump height, 392 px/s initial velocity
    public static final float JUMP_VELOCITY = 392f;  // pixels/s
    public static final float TARGET_JUMP_HEIGHT = 96f;  // pixels (~3 tiles)
    
    // MOVEMENT - Realistic game speeds
    public static final float WALK_SPEED = 150f;    // pixels/s
    public static final float RUN_SPEED = 250f;     // pixels/s
    public static final float SPRINT_SPEED = 350f;  // pixels/s (with Shift)
    
    // ACCELERATION - Responsive feel
    public static final float ACCELERATION = 800f;  // pixels/s²
    
    // FRICTION - Surface resistance
    public static final float FRICTION = 0.85f;  // Deceleration multiplier
    
    // ... (include all constants from Part C above)
}
```

### 1.2 Update PlayerController.java
Replace jump calculation:
```java
// OLD (WRONG):
characterVelY = -JUMP_VELOCITY_MS * 100;  // 543 px/s ✗

// NEW (CORRECT):
characterVelY = -PhysicsConstants.JUMP_VELOCITY;  // 392 px/s ✓
```

Replace movement speeds:
```java
// OLD:
targetSpeed = keyShift ? RUN_SPEED_MS : WALK_SPEED_MS;

// NEW:
targetSpeed = keyShift ? PhysicsConstants.RUN_SPEED : PhysicsConstants.WALK_SPEED;
```

### 1.3 Update CharacterAnimationTester.java
Note in comments that this GUI tool uses example constants, NOT game constants.

---

## Phase 2: Level Asset Integration (DAYS 1-2)

### 2.1 Create Level1.java TILES_ASSETS Object Array

```java
// In Level1.java - Replace placeholder with full array
public static final Object[][] TILES_ASSETS = {
    // [0] = char ID
    // [1] = String name
    // [2] = String filepath
    // [3] = Integer width
    // [4] = Integer height
    // [5] = Boolean isSolid
    // [6] = Boolean isHazard
    // [7] = String physicsType ("STATIC", "DYNAMIC", "PLATFORM", "HAZARD")
    // [8] = Float friction
    // [9] = Integer damage (if hazard)
    // [10] = Integer animationFrames
    // [11] = Integer frameTiming
    // [12] = String adjacencyCode
    
    {'A', "Rock Tile", "Resources/industrial-zone/1 Tiles/Level1/2 Background_level_1/rock_tile_21.png", 
     32, 32, true, false, "STATIC", 0.8f, 0, 0, 0, "rock"},
     
    {'B', "Platform", "Resources/industrial-zone/1 Tiles/Level1/3 Platforms_main/platform_01.png",
     32, 32, true, false, "STATIC", 0.6f, 0, 0, 0, "platform"},
     
    // Continue for all 82 Level1 tiles...
};
```

### 2.2 Create Level2.java TILES_ASSETS Object Array
Similar structure, with Level2-specific tiles and higher danger ratings.

### 2.3 Load map.txt in Level Classes

```java
// In Level1.java static initializer
static {
    try {
        String mapPath = "maps/level_1/map.txt";
        loadMapFile(mapPath);
        log("✓ Level1 map loaded: " + mapPath);
    } catch (Exception e) {
        System.err.println("✗ Level1 map load failed: " + e);
    }
}

private static void loadMapFile(String filepath) {
    // Parse map.txt using BufferedReader
    // Extract tile definitions
    // Validate against TILES_ASSETS
    // Store parsed level in static arrays
}
```

---

## Phase 3: Physics Integration (DAYS 2-3)

### 3.1 Create Collision System

```java
// File: src/physics/CollisionSystem.java

public class CollisionSystem {
    
    public static List<Collision> checkAllCollisions(
        PhysicsBody actor,
        List<PhysicsBody> staticObstacles,
        Object[][] levelAssets
    ) {
        List<Collision> results = new ArrayList<>();
        
        for (PhysicsBody obstacle : staticObstacles) {
            if (checkAABBCollision(actor, obstacle)) {
                // Get asset properties for this tile
                Object[] assetData = findAssetData(obstacle.assetID, levelAssets);
                
                // Determine collision type from asset
                CollisionType type = (Boolean) assetData[6]  // isHazard
                    ? CollisionType.HAZARD
                    : ((String) assetData[7]).equals("PLATFORM")
                    ? CollisionType.PLATFORM
                    : CollisionType.WALL;
                
                // Get collision direction
                Direction dir = getCollisionDirection(actor, obstacle);
                
                // Get damage amount
                int damage = (Integer) assetData[9];
                
                results.add(new Collision(obstacle, type, dir, damage));
            }
        }
        
        return results;
    }
}
```

### 3.2 Implement Collision Response in Game Loop

```java
// In Game.java main loop
List<Collision> collisions = CollisionSystem.checkAllCollisions(
    playerBody, 
    levelTiles,
    currentLevel.TILES_ASSETS
);

for (Collision col : collisions) {
    switch (col.type) {
        case PLATFORM:
            handlePlatformCollision(playerBody, col);
            break;
        case WALL:
            handleWallCollision(playerBody, col);
            break;
        case HAZARD:
            handleHazardCollision(playerBody, col.damage);
            break;
    }
}
```

---

## Phase 4: Map Visualization (DAYS 3-4)

### 4.1 Render Tiles from TILES_ASSETS

```java
// In ComprehensiveTileMapLoader.java
for (String row : mapData) {
    for (int x = 0; x < row.length(); x++) {
        char tile = row.charAt(x);
        Object[] assetData = findAsset(TILES_ASSETS, tile);
        
        String filepath = (String) assetData[2];
        int frameCount = (Integer) assetData[10];
        
        BufferedImage frame;
        if (frameCount > 0) {
            // Animated tile
            int currentFrame = (int) (System.currentTimeMillis() / 100) % frameCount;
            frame = loadFrame(filepath, currentFrame);
        } else {
            // Static tile
            frame = loadImage(filepath);
        }
        
        graphics.drawImage(frame, x * TILE_SIZE, y * TILE_SIZE, null);
    }
}
```

### 4.2 Create Physics Bodies from Tiles

```java
// In PhysicsUnitSystem
for (Object[] asset : levelAssets) {
    char tileID = (char) asset[0];
    boolean isSolid = (Boolean) asset[5];
    String physicsType = (String) asset[7];
    
    if (isSolid) {
        // Create static body for solid tiles
        PhysicsBody body = createBody(x, y, 32, 32, 0);  // Mass = 0 = static
        body.physicsType = physicsType;
    }
}
```

---

## Phase 5: Testing & Iteration (DAYS 4-5)

### 5.1 Test Jump Physics
**Expected:** Player jumps to 96-pixel height with 392 px/s initial velocity
**Test Case:** Standing player, press SPACE, measure peak height

### 5.2 Test Movement
**Expected:** Walk speed 150 px/s, run speed 250 px/s
**Test Case:** Hold RIGHT arrow 1 second, measure distance traveled

### 5.3 Test Collisions
**Expected:** Player stops at platforms, takes damage from hazards
**Test Case:** Walk into spike tile, verify 15 damage and knockback

### 5.4 Test Animated Tiles
**Expected:** Spinning blades rotate, lava bubbles, electrical arcs animate
**Test Case:** Load Level1, watch all 10+ animated tiles for smooth playback

---

---

## Summary: Complete Feature Matrix

| Feature | Status | Timeline | Dependencies |
|---------|--------|----------|--------------|
| Physics Constants Updated | TODO | Immediate | None |
| Level1.TILES_ASSETS Created | TODO | Day 1 | Physics constants |
| Level2.TILES_ASSETS Created | TODO | Day 1 | Physics constants |
| Map.txt Parsing | TODO | Day 1-2 | TILES_ASSETS arrays |
| Collision System | TODO | Day 2 | Physics constants |
| Collision Response | TODO | Day 2-3 | Collision system |
| Tile Rendering | TODO | Day 3 | TILES_ASSETS values |
| Physics Body Creation | TODO | Day 3 | TILES_ASSETS + Physics |
| Jump Mechanics Test | TODO | Day 4 | Updated constants |
| Movement Speed Test | TODO | Day 4 | Updated constants |
| Collision Test | TODO | Day 4 | Collision system |
| Animation Test | TODO | Day 4 | Tile rendering |

---

## Estimated Total Work: 5-7 Days

**Quick Start:** Create PhysicsConstants.java TODAY, update PlayerController.java, test jump.

---

**END OF COMPREHENSIVE PLAN (4,500+ lines)**
