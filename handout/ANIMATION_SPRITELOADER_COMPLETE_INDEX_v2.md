# 🎯 COMPREHENSIVE INDEX: AnimationAndSpriteLoader Nested Classes

## 📋 Document Overview
**Purpose**: Complete directory of all 22+ nested classes in AnimationAndSpriteLoader.java with:
- Exact line numbers and file locations
- Full method signatures
- Constructor details
- Return types and parameters
- Usage examples and integration points
- Asset path patterns for each loader type
- When to use each class

**File Location**: `handout/src/animation/AnimationAndSpriteLoader.java`

**Last Updated**: April 2, 2026

---

## ⚙️ TIER 1: CORE INFRASTRUCTURE & REGISTRIES

### 1. TileRegistry (Line 506)
**Type**: `public static class TileRegistry`

**Purpose**: Maps single-character codes to complete tile asset paths, enabling intuitive level design using character grids

**Key Methods**:
```java
public static String getTile(char code)
  // Input: Character code (e.g., 'A', 'B', '#')
  // Output: String path to tile asset file
  // Example: TileRegistry.getTile('A') → "Resources/industrial-zone/1 Tiles/Level1/tilename.png"

public static boolean registerTile(char code, String assetPath)
  // Input: Character code, full asset path
  // Output: boolean success

public static Map<Character, String> getAllTiles()
  // Output: Complete map of all registered code→path pairs
```

**Properties**:
- Supports arbitrary character mapping
- Case-sensitive codes
- Full path storage (no relative paths)

**Usage Pattern** (CharacterAnimationTester example):
```java
// Level design using tile codes
String levelRow = "AAAAAABBBBBBAAAA";
for (char code : levelRow.toCharArray()) {
    String assetPath = AnimationAndSpriteLoader.TileRegistry.getTile(code);
    BufferedImage tile = ImageIO.read(new File(assetPath));
    // Render tile at position
}
```

**Related Classes**: Integrates with Level1.java, Level2.java, TileMapSystem.java

---

### 2. SpriteMetadata (Line 631)
**Type**: `public static class SpriteMetadata`

**Purpose**: Analyzes spritesheet properties and suggests optimal animation timing and frame counts

**Key Methods**:
```java
public static SpriteMetadata analyze(String filePath)
  // Input: Full path to image file
  // Output: SpriteMetadata object with analysis results
  // Side Effect: Logs verbose analysis to console

public int getWidth()
  // Output: Image width in pixels

public int getHeight()
  // Output: Image height in pixels

public int getEstimatedFrameCount()
  // Output: Heuristic frame count based on image dimensions
  // Logic: (width > height) ? width/height : height/width

public String getComplexityRating()
  // Output: "LOW", "MEDIUM", or "HIGH" based on pixel variation

public int getSuggestedFrameDelay()
  // Output: Recommended milliseconds between frames

public String toString()
  // Output: Full analysis report with all metrics
```

**Properties Extracted**:
- Image dimensions (width × height)
- Estimated frame count (auto-detected from aspect ratio)
- Color complexity analysis
- Suggested animation timing

**Usage Pattern**:
```java
SpriteMetadata meta = AnimationAndSpriteLoader.SpriteMetadata.analyze(
    "Resources/industrial-zone/characters/player_walk_5frames.png"
);
System.out.println(meta);  // Prints complete analysis
// Output example:
// ═══════════════════════════════════════════
// SPRITE METADATA ANALYSIS: player_walk_5frames.png
// Dimensions: 320 × 64 pixels (5 frames horizontally)
// Complexity: MEDIUM
// Suggested Frame Count: 5
// Suggested Delay: 80ms per frame
// ═══════════════════════════════════════════
```

---

## ⚙️ TIER 2: PHYSICS & STATE MANAGEMENT

### 3. PhysicsUnitSystem (Line 712)
**Type**: `public static class PhysicsUnitSystem`

**Purpose**: Complete physics simulation framework for game entities including gravity, forces, and collisions

**Key Methods**:
```java
public PhysicsUnitSystem()
  // Constructor: Initialize physics world

public PhysicsBody createBody(float x, float y, float width, float height)
  // Input: Position (x, y), dimensions (width, height)
  // Output: PhysicsBody configured for the world
  
public void applyGravity(float gravityAmount)
  // Input: Gravity acceleration (e.g., -9.8f for Earth-like)
  // Default: -9.8f (downward)

public void setGlobalFriction(float friction)
  // Input: Friction coefficient (0.0 - 1.0)

public void update(float deltaTime)
  // Input: Time since last update in seconds
  // Side Effect: Updates all bodies, applies forces, detects collisions

public List<PhysicsBody> getBodies()
  // Output: All bodies currently in physics world

public void removeBody(PhysicsBody body)
  // Input: Body to remove from simulation
```

**Nested Classes**: Vector2D, PhysicsBody

**Integration Pattern** (Game.java):
```java
PhysicsUnitSystem physics = new AnimationAndSpriteLoader.PhysicsUnitSystem();
PhysicsBody player = physics.createBody(100, 100, 32, 32);
player.applyForce(0, -9.8f);  // Gravity

// Each frame:
player.applyForce(0, -9.8f);  // Re-apply gravity
physics.update(deltaTime);
```

---

### 4. Vector2D (Line 740) [NESTED IN PhysicsUnitSystem]
**Type**: `public static class Vector2D`

**Purpose**: 2D vector mathematics for positions, velocities, and directions

**Constructor & Methods**:
```java
public Vector2D(float x, float y)
  // Input: x and y components

public Vector2D add(Vector2D v)
  // Input: Another Vector2D
  // Output: New Vector2D with summed components
  // Example: pos.add(vel) for movement

public Vector2D subtract(Vector2D v)
  // Input: Another Vector2D
  // Output: New Vector2D with difference

public Vector2D scale(float scalar)
  // Input: Multiplication factor
  // Output: New Vector2D with scaled components
  // Example: vel.scale(0.8f) for friction

public float magnitude()
  // Output: Length of vector (distance from origin)
  // Formula: sqrt(x² + y²)

public Vector2D normalize()
  // Output: New Vector2D with magnitude = 1.0
  // Use: Direction vectors for physics

public float getX()
  // Output: x component

public float getY()
  // Output: y component
```

**Common Physics Patterns**:
```java
// Movement
Vector2D position = new Vector2D(100, 100);
Vector2D velocity = new Vector2D(5, 0);
position = position.add(velocity);  // Move right by 5 pixels

// Acceleration
Vector2D acceleration = new Vector2D(0, -9.8f);
velocity = velocity.add(acceleration.scale(deltaTime));

// Direction
Vector2D toPlayer = playerPos.subtract(enemyPos);
Vector2D direction = toPlayer.normalize();
```

---

### 5. PhysicsBody (Line 771) [NESTED IN PhysicsUnitSystem]
**Type**: `public static class PhysicsBody`

**Purpose**: Kinematic physics body for game entities (position, velocity, collision detection)

**Constructor & Methods**:
```java
public PhysicsBody(float x, float y, float width, float height)
  // Input: Initial position (x, y), dimensions (width, height)
  // Creates axis-aligned bounding box

public void update(float deltaTime)
  // Input: Time since last update (seconds)
  // Side Effect: Applies velocity to position, applies forces

public void applyForce(float forceX, float forceY)
  // Input: Force components (e.g., gravity: 0, -9.8f)
  // Side Effect: Accumulates in acceleration

public void setVelocity(float vx, float vy)
  // Input: New velocity components
  // Side Effect: Replaces current velocity

public boolean collidesWith(PhysicsBody other)
  // Input: Another PhysicsBody
  // Output: true if bounding boxes overlap

public Rectangle getBounds()
  // Output: Rectangle representing current position/size

public static final float DEFAULT_MASS = 1.0f;
public static final float DEFAULT_FRICTION = 0.95f;

// Getters
public float getX()
public float getY()
public float getVelocityX()
public float getVelocityY()
public float getWidth()
public float getHeight()
```

**Collision Detection Pattern**:
```java
// Check if player touches ground
if (player.collidesWith(groundBody) && player.getVelocityY() < 0) {
    player.setVelocity(player.getVelocityX(), 0);  // Stop falling
}

// Damage on collision
if (projectile.collidesWith(enemy)) {
    enemy.takeDamage(projectile.getDamage());
    projectile.destroy();
}
```

---

### 6. StateTransition (Line 1046)
**Type**: `public static class StateTransition`

**Purpose**: Entity state machine for managing animation state transitions with conditions and callbacks

**Key Methods**:
```java
public StateTransition()
  // Constructor: Initialize empty state machine

public void addState(String stateName)
  // Input: New state identifier (e.g., "idle", "run", "jump")
  
public void addTransition(String from, String to, Condition condition)
  // Input: Source state, destination state, condition lambda
  // Example: state.addTransition("idle", "run", () -> isMoving);

public boolean transitionTo(String newState)
  // Input: Target state name
  // Output: true if transition was valid
  // Side Effect: Calls state exit/enter callbacks

public String getCurrentState()
  // Output: Current active state name

public void onStateEnter(String state, Runnable callback)
  // Input: State name, callback function
  // Side Effect: Callback runs when entering state

public void onStateExit(String state, Runnable callback)
  // Input: State name, callback function
  // Side Effect: Callback runs when leaving state

public void update()
  // Side Effect: Evaluates all active transitions, performs any valid ones

@FunctionalInterface
public interface Condition {
    boolean isMet();
}
```

**State Machine Pattern** (CharacterAnimationTester pattern):
```java
StateTransition playerState = new AnimationAndSpriteLoader.StateTransition();

// Define states
playerState.addState("idle");
playerState.addState("run");
playerState.addState("jump");

// Define transitions
playerState.addTransition("idle", "run", () -> Math.abs(inputVelocityX) > 0);
playerState.addTransition("run", "idle", () -> Math.abs(inputVelocityX) == 0);
playerState.addTransition("idle", "jump", () -> jumpPressed && onGround);
playerState.addTransition("jump", "idle", () -> onGround && !jumpPressed);

// Setup callbacks
playerState.onStateEnter("run", () -> {
    System.out.println("Started running");
    currentAnimation = runLoader;
});

playerState.onStateEnter("jump", () -> {
    playerPhysics.applyForce(0, 20.0f);  // Jump impulse
});

// Each frame
playerState.update();
String state = playerState.getCurrentState();
BufferedImage frame = animations.get(state).getFrame(frameIndex);
```

---

### 7. InputHandler (Line 1075)
**Type**: `public static class InputHandler`

**Purpose**: Unified keyboard and mouse input processing with key state tracking

**Key Methods**:
```java
public InputHandler()
  // Constructor: Initialize input system (must be attached to JFrame)

public boolean isKeyPressed(int keyCode)
  // Input: KeyEvent.VK_* constant (e.g., KeyEvent.VK_W)
  // Output: true if key was pressed THIS FRAME (edge trigger)

public boolean isKeyDown(int keyCode)
  // Input: KeyEvent.VK_* constant
  // Output: true if key is currently held down (level trigger)

public boolean isKeyReleased(int keyCode)
  // Input: KeyEvent.VK_* constant
  // Output: true if key was released THIS FRAME

public int getMouseX()
  // Output: Current mouse X position relative to frame

public int getMouseY()
  // Output: Current mouse Y position relative to frame

public boolean isMouseButtonDown(int button)
  // Input: 1 (left), 2 (middle), 3 (right)
  // Output: true if button is currently held

public boolean isMouseButtonClicked(int button)
  // Input: Button number
  // Output: true if clicked THIS FRAME

public void attachToFrame(JFrame frame)
  // Input: JFrame to listen for input on
  // Side Effect: Registers input listeners
```

**Input Pattern** (CharacterAnimationTester):
```java
InputHandler input = new AnimationAndSpriteLoader.InputHandler();
input.attachToFrame(gameFrame);

// Each frame
float moveVelocity = 0;
if (input.isKeyDown(KeyEvent.VK_D)) moveVelocity += 5;
if (input.isKeyDown(KeyEvent.VK_A)) moveVelocity -= 5;

if (input.isKeyPressed(KeyEvent.VK_SPACE) && onGround) {
    playerPhysics.applyForce(0, 20);  // Jump
}

if (input.isMouseButtonClicked(1)) {
    // Left click attack
    spawnProjectile(player.getX(), player.getY());
}
```

---

## ⚙️ TIER 3: ANIMATION CONTROLLERS (Abstract Base)

### 8. EntityAnimationController (Line 1133)
**Type**: `public static abstract class EntityAnimationController`

**Purpose**: Base class for all animated entities (player, enemies, projectiles, effects)

**Key Methods**:
```java
public abstract void updateAnimation(float deltaTime)
  // Input: Time since last frame (seconds)
  // Side Effect: Advances animation frame, handles state transitions

public abstract BufferedImage getAnimationFrame()
  // Output: Current animation frame for rendering

public abstract void playAnimation(String state)
  // Input: State identifier (e.g., "walk", "attack")
  // Side Effect: Starts specified animation, resets frame index

public abstract boolean isAnimationFinished()
  // Output: true if current animation has completed

public void setAnimationSpeed(float speed)
  // Input: Speed multiplier (1.0 = normal, 2.0 = double speed)

public void resetAnimation()
  // Side Effect: Resets animation to first frame

public void pauseAnimation()
public void resumeAnimation()
```

**Concrete Subclasses**: 
- PlayerController (Line 1205)
- EnemyController (Line 1382)
- BossController (Line 1557)
- ProjectileController (Line 1898)
- VFXController (Line 1960)

---

## ⚙️ TIER 4: CONCRETE CONTROLLERS

### 9. PlayerController extends EntityAnimationController (Line 1205)
**Type**: `public static class PlayerController extends EntityAnimationController`

**Purpose**: Manages player character animation, movement, and gameplay state

**Key Methods**:
```java
public PlayerController()
  // Constructor: Initialize player with default animations

public void loadAnimations(String basePath)
  // Input: Base path to character assets (e.g., "Resources/industrial-zone/characters/")
  // Side Effect: Loads walk, run, jump, attack, hurt, death animations

public void handlePlayerInput(InputHandler input)
  // Input: InputHandler instance
  // Side Effect: Updates velocity based on keypresses
  
public void setAnimationState(String state)
  // Input: Animation state (e.g., "idle", "walk", "run", "jump", "attack")
  
public void updateAnimation(float deltaTime)
  // Input: Delta time in seconds
  // Side Effect: Updates frame index, may transition states

public BufferedImage getAnimationFrame()
  // Output: Current player sprite frame

public int getHealth()
public void takeDamage(int amount)
public boolean isAlive()
```

**Animation States**:
- `idle`: Standing still
- `walk`: Walking
- `run`: Running
- `jump`: Jumping
- `attack`: Attacking
- `hurt`: Damage reaction
- `death`: Game over animation

**CharacterAnimationTester Pattern** (CORRECT APPROACH):
```java
// Load animations
PlayerController player = new AnimationAndSpriteLoader.PlayerController();
player.loadAnimations("Resources/industrial-zone/characters/");

// Each game frame
input.update();
player.handlePlayerInput(input);
player.updateAnimation(deltaTime);

// Render
BufferedImage currentFrame = player.getAnimationFrame();
g.drawImage(currentFrame, player.getX(), player.getY(), null);

// Collision/Combat
for (EnemyController enemy : enemies) {
    if (player.collidesWith(enemy)) {
        player.takeDamage(enemy.getDamage());
        player.setAnimationState("hurt");
    }
}
```

---

### 10. EnemyController extends EntityAnimationController (Line 1382)
**Type**: `public static class EnemyController extends EntityAnimationController`

**Purpose**: Manages enemy AI, pathfinding, and combat behavior

**Key Methods**:
```java
public EnemyController(String type, float x, float y)
  // Input: Enemy type ("soldier", "mech", "drone"), position
  // Side Effect: Loads appropriate animations for enemy type

public void updateAI(float deltaTime, PlayerController player)
  // Input: Delta time, player reference for pathfinding
  // Side Effect: Updates AI state, possibly transitions to attack

public void decideNextAction()
  // Side Effect: Determines patrol, chase, or attack based on position

public void attackPlayer(PlayerController player)
  // Input: Player to attack
  // Side Effect: Initiates attack animation, deals damage

public void patrolPath(List<Vector2D> waypoints)
  // Input: List of patrol positions
  // Side Effect: Sets patrol route

public boolean canSeePlayer(PlayerController player)
  // Input: Player reference
  // Output: true if player within vision range

public void takeDamage(int amount)
public int getHealth()
public boolean isAlive()
```

**AI States**:
- `patrol`: Following patrol route
- `alert`: Detected player, moving to investigate
- `chase`: Following player
- `attack`: In combat
- `hurt`: Knocked back/damaged
- `death`: Defeated

---

### 11. BossController extends EntityAnimationController (Line 1557)
**Type**: `public static class BossController extends EntityAnimationController`

**Purpose**: Advanced boss enemy with multi-phase battles and special attacks

**Key Methods**:
```java
public BossController(String bossName, float x, float y)
  // Input: Boss identifier, starting position

public void updateBossAI(float deltaTime, PlayerController player)
  // Input: Delta time, player reference
  // Side Effect: Executes boss behavior and attacks

public void executeAttackPattern(int phase)
  // Input: Current phase (1, 2, 3, etc.)
  // Side Effect: Spawns attacks appropriate to phase

public void transitionPhase(int newPhase)
  // Input: Target phase number
  // Side Effect: Changes attack patterns, plays transition animation

public void spawnMinions(int count)
  // Input: Number of minions to create
  // Side Effect: Adds EnemyController minions to battle

public int getPhase()
public int getMaxHealth()
public int getHealth()
public void takeDamage(int amount)
```

**Phase System**:
- `Phase 1`: Simple attacks (0-33% health)
- `Phase 2`: Multiple attacks (33-66% health)
- `Phase 3`: Ultimate attacks + minions (66-100% health)

---

### 12. EnvironmentController (Line 1731)
**Type**: `public static class EnvironmentController`

**Purpose**: Manages parallax scrolling, weather, and environmental animations

**Key Methods**:
```java
public EnvironmentController()
  // Constructor: Initialize environment system

public void setParallaxLayers(int layerCount)
  // Input: Number of parallax background layers
  // Example: 3 = far background, mid background, near background

public void loadParallaxLayer(int layer, String imagePath, float speed)
  // Input: Layer index (0=farthest), asset path, scroll speed (0.5 = half game camera speed)

public void updateParallax(float cameraX, float cameraY, float deltaTime)
  // Input: Camera position
  // Side Effect: Updates parallax offset for each layer

public void renderEnvironment(Graphics2D g, int screenWidth, int screenHeight)
  // Input: Graphics2D context, screen dimensions
  // Side Effect: Draws all parallax layers

public void setWeather(String weatherType, float intensity)
  // Input: "rain", "snow", "fog", "sand" + intensity (0.0-1.0)  
  // Side Effect: Starts weather effect

public void stopWeather()
```

**Parallax Pattern** (Game.java):
```java
EnvironmentController env = new AnimationAndSpriteLoader.EnvironmentController();
env.setParallaxLayers(3);
env.loadParallaxLayer(0, "Resources/industrial-zone/bg/far.png", 0.3f);
env.loadParallaxLayer(1, "Resources/industrial-zone/bg/mid.png", 0.6f);
env.loadParallaxLayer(2, "Resources/industrial-zone/bg/near.png", 0.9f);

// Each frame
env.updateParallax(cameraX, cameraY, deltaTime);
env.renderEnvironment(g, 1024, 768);
```

---

### 13. ProjectileController extends EntityAnimationController (Line 1898)
**Type**: `public static class ProjectileController extends EntityAnimationController`

**Purpose**: Manages projectiles (arrows, bullets, spells) with physics and impact detection

**Key Methods**:
```java
public ProjectileController(String type, float x, float y)
  // Input: Projectile type ("arrow", "fireball", "bullet"), position

public void setVelocity(float vx, float vy)
  // Input: Velocity components (pixels/second)
  
public void updateProjectile(float deltaTime)
  // Input: Delta time in seconds
  // Side Effect: Updates position, checks collisions, may trigger impact

public void onImpact(GameObject target)
  // Input: Object hit (enemy, wall, tile)
  // Side Effect: Plays impact animation, deals damage, removes projectile

public boolean isAlive()
  // Output: true if projectile hasn't been destroyed

public float getX()
public float getY()
public Rectangle getBounds()

// For camera/rendering system
public int getDamage()
public void resetLifetime()
```

**Projectile Pattern** (CharacterAnimationTester):
```java
// Create and fire projectile
ProjectileController arrow = new AnimationAndSpriteLoader.ProjectileController("arrow", 100, 100);
arrow.setVelocity(15, 0);  // Flying right
projectiles.add(arrow);

// Each frame
for (ProjectileController p : projectiles) {
    p.updateProjectile(deltaTime);
    
    // Collision
    for (EnemyController enemy : enemies) {
        if (p.getBounds().intersects(enemy.getBounds())) {
            p.onImpact(enemy);
            enemy.takeDamage(p.getDamage());
        }
    }
    
    if (!p.isAlive()) {
        projectiles.remove(p);
    }
}
```

---

### 14. VFXController extends EntityAnimationController (Line 1960)
**Type**: `public static class VFXController extends EntityAnimationController`

**Purpose**: Visual effects (explosions, particles, impact feedback, screen effects)

**Key Methods**:
```java
public VFXController(String effectType, float x, float y)
  // Input: Effect type ("explosion", "spark", "dust", "heal"), position

public void playEffect(String effectType)
  // Input: Effect identifier
  // Side Effect: Starts effect animation

public void updateVFX(float deltaTime)
  // Input: Delta time
  // Side Effect: Updates effect animation

public void particleEmit(int count, float angle, float spread)
  // Input: Particle count, direction angle, angle variation
  // Side Effect: Creates particle spray

public void screenShake(float intensity, float duration)
  // Input: Intensity (0.0-1.0), duration in seconds
  // Side Effect: Queues screen shake effect

public boolean isActive()
  // Output: true if effect still animating

public void destroy()
```

**VFX Pattern**:
```java
// Hit effect
VFXController hitFX = new AnimationAndSpriteLoader.VFXController("hit_spark", enemy.getX(), enemy.getY());
hitFX.particleEmit(12, 180, 45);  // Explosion outward
vfxList.add(hitFX);

// Explosion
VFXController explosion = new AnimationAndSpriteLoader.VFXController("explosion", detonateX, detonateY);
explosion.screenShake(0.8f, 0.3f);  // Shake camera
vfxList.add(explosion);

// Render VFX
for (VFXController vfx : vfxList) {
    vfx.updateVFX(deltaTime);
    BufferedImage frame = vfx.getAnimationFrame();
    g.drawImage(frame, vfx.getX(), vfx.getY(), null);
    
    if (!vfx.isActive()) vfxList.remove(vfx);
}
```

---

### 15. GameStateManager (Line 2127)
**Type**: `public static class GameStateManager`

**Purpose**: Global game state, progression, scoring, and achievement tracking

**Key Methods**:
```java
public GameStateManager()
  // Constructor: Initialize game state

public void setCurrentLevel(int levelNumber)
  // Input: Level number (1, 2, 3, etc.)

public int getCurrentLevel()
  // Output: Current active level

public void setGameState(GameState state)
  // Input: MENU, PLAYING, PAUSED, GAME_OVER, WIN, LEVEL_COMPLETE

public GameState getGameState()
  // Output: Current game state

public void addScore(int points)
  // Input: Points to add (positive or negative)
  // Side Effect: Updates total score

public int getScore()
  // Output: Total accumulated score

public void resetScore()

public void addKill()
public int getKillCount()

public void unlockAchievement(String achievementId)
  // Input: Achievement key (e.g., "first_boss_defeat")
  
public boolean hasAchievement(String achievementId)
  // Output: true if achievement unlocked

public void saveGameState(String filename)
public void loadGameState(String filename)

public enum GameState {
    MENU, PLAYING, PAUSED, GAME_OVER, WIN, LEVEL_COMPLETE
}
```

**Game Loop Integration** (Game.java Pattern):
```java
GameStateManager gameState = new AnimationAndSpriteLoader.GameStateManager();
gameState.setCurrentLevel(1);

while (gameRunning) {
    switch (gameState.getGameState()) {
        case MENU:
            // Render menu
            break;
        case PLAYING:
            // Update game
            player.updateAnimation(deltaTime);
            for (EnemyController e : enemies) {
                e.updateAI(deltaTime, player);
                if (opponent defeated) {
                    gameState.addScore(100);
                    gameState.addKill();
                    gameState.unlockAchievement("enemy_defeated");
                }
            }
            break;
        case PAUSED:
            // Show pause menu
            break;
        case GAME_OVER:
            gameState.saveGameState("player_progress.dat");
            break;
    }
}
```

---

## ⚙️ TIER 5: ASSET LOADERS (Extend AssetType Base)

### 16. AssetType (Line 2427) [BASE CLASS FOR ALL LOADERS]
**Type**: `public abstract static class AssetType`

**Purpose**: Abstract base class defining interface for all sprite/animation loaders

**Abstract Methods**:
```java
public abstract boolean load()
  // Side Effect: Load asset from file/path
  // Output: true if successful

public abstract BufferedImage getFrame(int index)
  // Input: Frame index (0-based)
  // Output: Requested frame image
  
public abstract int getFrameCount()
  // Output: Total number of frames available
```

**Helper Methods** (all AssetType subclasses):
```java
protected boolean fileExists(String path)
  // Input: Full file path
  // Output: true if file exists and readable

protected BufferedImage loadImage(String path)
  // Input: Full file path
  // Output: BufferedImage from file (or null if fails)
  
protected void logError(String message)
  // Input: Error message
  // Side Effect: Prints ERROR to console with timestamp
```

---

### 17. SingleSpriteLoader extends AssetType (Line 2501)
**Type**: `public static class SingleSpriteLoader extends AssetType`

**Purpose**: Load single non-animated sprite images

**Constructor & Methods**:
```java
public SingleSpriteLoader(String imageId, String filePath)
  // Input: Identifier for logging, full file path

public boolean load()
  // Side Effect: Loads image from file into memory
  // Output: true if successful

public BufferedImage getFrame(int index)
  // Input: Frame index (IGNORED - always returns single frame)
  // Output: The loaded image (same for any index)

public int getFrameCount()
  // Output: Always 1 (single sprite)
```

**Usage Pattern** (UI/Static Images):
```java
SingleSpriteLoader logo = new AnimationAndSpriteLoader.SingleSpriteLoader(
    "logo",
    "Resources/industrial-zone/gui/logo.png"
);

if (logo.load()) {
    BufferedImage logoImage = logo.getFrame(0);  // Always returns the one loaded image
    g.drawImage(logoImage, centerX - logoImage.getWidth()/2, 50, null);
}
```

**Use Cases**:
- UI logos and backgrounds
- Static level decorations
- Map icons
- Character portraits

---

### 18. HorizontalSpritesheetLoader extends AssetType (Line 2565)
**Type**: `public static class HorizontalSpritesheetLoader extends AssetType`

**Purpose**: Load spritesheets with frames arranged horizontally in a single row

**Constructor & Methods**:
```java
public HorizontalSpritesheetLoader(String loaderId, String filePath, int extraOffsetX, int extraOffsetY, int borderPixels)
  // Input: ID for logging, full path, offset adjustments, border pixels to skip
  // Purpose of extras: Handle spritesheets with padding/borders

public boolean load()
  // Side Effect: Smart detection - analyzes filename for frame count
  // Example: "walk_8frames.png" → auto-detects 8 frames
  // Fallback: Uses image aspect ratio (width/height)
  // Output: true if successful

public BufferedImage getFrame(int frameIndex)
  // Input: Frame index (0 to frameCount-1)
  // Output: Cropped portion of spritesheet for that frame

public int getFrameCount()
  // Output: Number of frames

public BufferedImage getFullSheet()
  // Output: Original uncropped spritesheet
```

**Frame Detection Algorithm**:
1. Examine filename for pattern like "_8frames" or "_5f"
2. If found, use that number
3. Otherwise, use aspect ratio: `frameCount = width / height`
4. Load frames left-to-right, top-to-bottom

**CharacterAnimationTester Pattern** (CORRECT - MUST FOLLOW THIS):  
```java
// Load player walk animation
HorizontalSpritesheetLoader walkLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
    "player_walk",
    "Resources/industrial-zone/characters/player/walk_8frames.png",
    0, 0, 0
);

if (walkLoader.load()) {
    System.out.println("✓ Loaded: " + walkLoader.getFrameCount() + " frames");
    
    // Store for animation caching
    Object[][] characterAnimations = new Object[1][walkLoader.getFrameCount()];
    for (int i = 0; i < walkLoader.getFrameCount(); i++) {
        characterAnimations[0][i] = walkLoader.getFrame(i);
    }
    
    // In render loop
    frameIndex = (frameIndex + 1) % walkLoader.getFrameCount();
    BufferedImage frame = (BufferedImage) characterAnimations[0][frameIndex];
    g.drawImage(frame, playerX, playerY, null);
}
```

**Critical**:
- ALWAYS load real PNG files from Resources/
- NEVER create dummy colored rectangles
- Return NULL if file not found
- Log exact file path that failed

**Asset Path Pattern**:
```
Resources/industrial-zone/
├── characters/
│   ├── player/
│   │   ├── walk_8frames.png         ← HorizontalSpritesheetLoader
│   │   ├── run_6frames.png          ← HorizontalSpritesheetLoader
│   │   ├── jump_4frames.png         ← HorizontalSpritesheetLoader
│   │   └── attack_5frames.png       ← HorizontalSpritesheetLoader
│   └── enemies/
│       ├── soldier_walk_4frames.png ← HorizontalSpritesheetLoader
│       └── mech_attack_6frames.png  ← HorizontalSpritesheetLoader
└── weapons/
    └── Arrow/
        └── arrow_spin_8frames.png   ← HorizontalSpritesheetLoader
```

---

### 19. VerticalSpritesheetLoader extends AssetType (Line 2724)
**Type**: `public static class VerticalSpritesheetLoader extends AssetType`

**Purpose**: Load spritesheets with frames arranged vertically in a single column

**Constructor & Methods**:
```java
public VerticalSpritesheetLoader(String loaderId, String filePath, int extraOffsetX, int extraOffsetY, int borderPixels)
  // Same as HorizontalSpritesheetLoader

public boolean load()
  // Frame detection: Uses filename pattern or height/width ratio
  // Example: "variants_3frames_vertical.png" → 3 frames stacked vertically

public BufferedImage getFrame(int frameIndex)
  // Input: Frame index (top to bottom)
  // Output: Vertical slice of spritesheet

public int getFrameCount()
  // Output: Number of vertical frames
```

**Frame Layout**:
```
[Frame 0]
[Frame 1]
[Frame 2]
[Frame 3]
```

**Usage Pattern** (Menu Variant Buttons):
```java
// Load vertical button variants (normal, hover, pressed, disabled)
VerticalSpritesheetLoader buttonVariants = new AnimationAndSpriteLoader.VerticalSpritesheetLoader(
    "menu_button_variants",
    "Resources/industrial-zone/gui/button_states_4frames_vertical.png",
    0, 0, 0
);

if (buttonVariants.load()) {
    // Frames: [0] normal, [1] hover, [2] pressed, [3] disabled
    BufferedImage normalState = buttonVariants.getFrame(0);
    BufferedImage hoverState = buttonVariants.getFrame(1);
    
    // Render appropriate state
    if (mouseOverButton) {
        g.drawImage(hoverState, buttonX, buttonY, null);
    } else {
        g.drawImage(normalState, buttonX, buttonY, null);
    }
}
```

**Asset Path Examples**:
```
Resources/industrial-zone/gui/
├── menu_button_4states_vertical.png      ← VerticalSpritesheetLoader(4 frames)
├── pause_menu_variants_3states.png       ← VerticalSpritesheetLoader(3 frames)
└── indicator_light_colors_vertical.png   ← VerticalSpritesheetLoader(N colors)
```

---

### 20. GridSpritesheetLoader extends AssetType (Line 2817)
**Type**: `public static class GridSpritesheetLoader extends AssetType`

**Purpose**: Load spritesheets with frames arranged in a 2D grid (rows × columns)

**Constructor & Methods**:
```java
public GridSpritesheetLoader(String loaderId, String filePath, int rows, int cols)
  // Input: Loader ID, path, grid dimensions

public boolean load()
  // Side Effect: Loads spritesheet
  // Output: true if successful

public BufferedImage getFrame(int index)
  // Input: Frame number (0 to rows*cols-1)
  // Output: Frame from grid (row-major order: left-to-right, top-to-bottom)

public BufferedImage getFrameAt(int row, int col)
  // Input: Row and column indices (0-based)
  // Output: Frame at that grid position

public int getFrameCount()
  // Output: rows × cols
```

**Frame Layout** (4 rows × 3 cols = 12 frames):
```
[F0]  [F1]  [F2]
[F3]  [F4]  [F5]
[F6]  [F7]  [F8]
[F9]  [F10] [F11]
```

**Usage Pattern** (Directional Sprites):
```java
// 8-directional character sprite (2 rows × 4 cols)
GridSpritesheetLoader directions = new AnimationAndSpriteLoader.GridSpritesheetLoader(
    "player_directions",
    "Resources/industrial-zone/characters/player_8directions.png",
    2, 4  // 2 rows, 4 cols
);

if (directions.load()) {
    // Direction mapping:
    // Row 0: Right, Down-Right, Down, Down-Left
    // Row 1: Left,  Up-Left,    Up,   Up-Right
    
    BufferedImage playerSprite;
    float angle = calculateFacingAngle();
    
    // Simplified: 4 directions
    if (angle < 45) {
        playerSprite = directions.getFrameAt(0, 0);  // Right
    } else if (angle < 135) {
        playerSprite = directions.getFrameAt(0, 2);  // Down
    } else if (angle < 225) {
        playerSprite = directions.getFrameAt(1, 0);  // Left
    } else {
        playerSprite = directions.getFrameAt(1, 2);  // Up
    }
    
    g.drawImage(playerSprite, playerX, playerY, null);
}
```

---

### 21. GridFrameAnimationLoader extends AssetType (Line 2921)
**Type**: `public static class GridFrameAnimationLoader extends AssetType`

**Purpose**: Grid-based animation with per-frame timing control for variable frame delays

**Constructor & Methods**:
```java
public GridFrameAnimationLoader(String loaderId, String filePath, int rows, int cols, int[] frameTiming)
  // Input: ID, path, grid dimensions, array of milliseconds per frame
  // Example timing: {100, 100, 150, 100} = frames at 10fps, 10fps, ~6.7fps, 10fps

public boolean load()
  // Side Effect: Loads grid spritesheet and timing config

public BufferedImage getNextFrame(float deltaTime)
  // Input: Time since last frame (seconds)
  // Output: Next frame based on elapsed time
  // Side Effect: Updates internal animation timer

public void setPlaybackSpeed(float speedMultiplier)
  // Input: Speed multiplier (1.0 = normal, 2.0 = double speed)

public float getProgress()
  // Output: Animation progress (0.0 = start, 1.0 = end)

public void resetAnimation()
  // Side Effect: Restart from first frame

public int getFrameCount()
  // Output: Total frames (rows × cols)
```

**Usage Pattern** (Attack with Variable Timing):
```java
// Attack animation: 3 frames with varying speeds
// Frame 0: Windup (slow) 150ms
// Frame 1: Strike (fast) 50ms
// Frame 2: Recovery (medium) 100ms
int[] attackTiming = {150, 50, 100};

GridFrameAnimationLoader attackAnim = new AnimationAndSpriteLoader.GridFrameAnimationLoader(
    "player_attack",
    "Resources/industrial-zone/characters/attacks_3frames_1x3.png",
    1, 3,  // 1 row, 3 columns
    attackTiming
);

if (attackAnim.load()) {
    attackAnim.resetAnimation();
    
    // In game loop
    BufferedImage attackFrame = attackAnim.getNextFrame((float)deltaTime);
    g.drawImage(attackFrame, playerX, playerY, null);
    
    System.out.println("Attack " + String.format("%.1f", attackAnim.getProgress() * 100) + "% complete");
    
    // Detect hit frame (exact timing)
    if (attackAnim.getProgress() > 0.33f && attackAnim.getProgress() < 0.67f) {
        // This is the strike frame - apply damage now
        for (EnemyController e : enemies) {
            if (e.getBounds().intersects(player.getAttackRange())) {
                e.takeDamage(player.getAttackDamage());
            }
        }
    }
}
```

---

### 22. SequenceFrameAnimationLoader extends AssetType (Line 3106)
**Type**: `public static class SequenceFrameAnimationLoader extends AssetType`

**Purpose**: Load animation as sequence of separate image files (not spritesheet)

**Constructor & Methods**:
```java
public SequenceFrameAnimationLoader(String loaderId, int[] frameTiming)
  // Input: Loader ID, timing array (milliseconds per frame)

public boolean loadSequence(String[] filePaths)
  // Input: Array of full file paths for each frame
  // Example: {"scene_01.png", "scene_02.png", "scene_03.png"}
  // Side Effect: Loads all frames from disk
  // Output: true if all loaded successfully

public BufferedImage getNextFrame(float deltaTime)
  // Input: Delta time in seconds
  // Output: Next frame based on timing
  // Side Effect: Updates internal timer

public float getTotalDuration()
  // Output: Total animation duration in seconds

public void resetAnimation()

public int getFrameCount()
```

**Usage Pattern** (Cinematic Sequence):
```java
// Load cutscene animation from separate files
SequenceFrameAnimationLoader cutscene = new AnimationAndSpriteLoader.SequenceFrameAnimationLoader(
    "intro_cutscene",
    new int[]{200, 200, 200, 300, 200, 200}  // 6 frames, 1.3 second total
);

String[] cutsceneFrames = {
    "Resources/industrial-zone/cutscenes/intro_frame_01.png",
    "Resources/industrial-zone/cutscenes/intro_frame_02.png",
    "Resources/industrial-zone/cutscenes/intro_frame_03.png",
    "Resources/industrial-zone/cutscenes/intro_frame_04.png",
    "Resources/industrial-zone/cutscenes/intro_frame_05.png",
    "Resources/industrial-zone/cutscenes/intro_frame_06.png"
};

if (cutscene.loadSequence(cutsceneFrames)) {
    System.out.println("Cutscene duration: " + cutscene.getTotalDuration() + "s");
    
    // Play cutscene
    while (playing) {
        BufferedImage frame = cutscene.getNextFrame(deltaTime);
        if (frame != null) {
            g.drawImage(frame, 0, 0, null);
        } else {
            playing = false;  // Animation finished
        }
    }
}
```

**Use Cases**:
- Game intro/outro sequences
- Boss death cinematics
- Level transition animations
- Story cutscenes
- Tutorial sequences

---

### 23. StateVariantLoader extends AssetType (Line 3244)
**Type**: `public static class StateVariantLoader extends AssetType`

**Purpose**: Manage multiple animation variants for different entity states

**Constructor & Methods**:
```java
public StateVariantLoader(String loaderId)
  // Input: Identifier for logging

public void addState(String stateName, AssetType animationLoader)
  // Input: State identifier (e.g., "walk", "run", "jump"), loaded animation
  // Side Effect: Registers animation for this state

public void removeState(String stateName)

public AssetType getState(String stateName)
  // Input: State name
  // Output: Registered animation for that state (or null if not found)

public void switchState(String newState)
  // Input: New state identifier
  // Side Effect: Changes active state

public String getCurrentState()
  // Output: Current active state name

public AssetType getAnimationForCurrentState()
  // Output: Animation loader for current state

public BufferedImage getFrame(int frameIndex)
  // Input: Frame index
  // Output: Frame from current state's animation
```

**Usage Pattern** (Multi-State Character):
```java
// Create container for all player animations
StateVariantLoader playerAnimations = new AnimationAndSpriteLoader.StateVariantLoader("player");

// Load each animation
HorizontalSpritesheetLoader idleAnim = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
    "player_idle", "Resources/industrial-zone/characters/player/idle_2frames.png", 0, 0, 0);
idleAnim.load();
playerAnimations.addState("idle", idleAnim);

HorizontalSpritesheetLoader walkAnim = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
    "player_walk", "Resources/industrial-zone/characters/player/walk_8frames.png", 0, 0, 0);
walkAnim.load();
playerAnimations.addState("walk", walkAnim);

HorizontalSpritesheetLoader jumpAnim = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
    "player_jump", "Resources/industrial-zone/characters/player/jump_4frames.png", 0, 0, 0);
jumpAnim.load();
playerAnimations.addState("jump", jumpAnim);

// Switch states based on game logic
if (isMoving) {
    playerAnimations.switchState("walk");
} else if (isJumping) {
    playerAnimations.switchState("jump");
} else {
    playerAnimations.switchState("idle");
}

// Render current frame
BufferedImage frame = playerAnimations.getFrame(frameIndex);
g.drawImage(frame, playerX, playerY, null);
```

---

## 📊 QUICK REFERENCE MATRIX

| Class | Purpose | Input | Output | Use Case |
|-------|---------|-------|--------|----------|
| **TileRegistry** | Map codes to tile paths | char | String | Level design |
| **SpriteMetadata** | Analyze sprite properties | path | metadata | Auto-detect settings |
| **SingleSpriteLoader** | Load static image | path | image | UI, decorations |
| **HorizontalSpritesheetLoader** | Load 1D horizontal strip | path, frameCount | frames | Walk, run, attack |
| **VerticalSpritesheetLoader** | Load 1D vertical stack | path, frameCount | frames | Menu states, variants |
| **GridSpritesheetLoader** | Load 2D grid | path, rows, cols | frames | 8-directional, variants |
| **GridFrameAnimationLoader** | Grid with timing | path, rows, cols, timing | frame | Attack with hit frame |
| **SequenceFrameAnimationLoader** | Separate files | paths[], timing[] | frames | Cinematics, cutscenes |
| **StateVariantLoader** | Container for states | AssetType objects | active frame | Multi-state character |
| **PhysicsUnitSystem** | Physics simulation | forces | body | Gravity, collisions |
| **PlayerController** | Player character | input | frame | Player rendering |
| **EnemyController** | Enemy AI | player ref | frame | Enemy behavior |
| **BossController** | Boss enemy | player ref | frame | Boss battles |
| **ProjectileController** | Projectiles | velocity | frame | Arrow, bullet physics |
| **VFXController** | Visual effects | effect type | frame | Explosions, particles |
| **GameStateManager** | Game progress | events | state | Score, level, progression |
| **EnvironmentController** | Parallax, weather | camera pos | rendered | Scrolling backgrounds |
| **StateTransition** | State machine | conditions | current | Animation states |
| **InputHandler** | Keyboard/mouse | frame | key state | Player input |

---

## 🎓 INTEGRATION EXAMPLE: COMPLETE CHARACTER SYSTEM

This example shows how ALL components work together:

```java
// ════════════════════════════════════════════════════════════════════════════════
// TIER 1: Load all assets
// ════════════════════════════════════════════════════════════════════════════════

// Character animations
HorizontalSpritesheetLoader walkAnim = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
    "walk", "Resources/industrial-zone/characters/walk_8frames.png", 0, 0, 0);
walkAnim.load();

HorizontalSpritesheetLoader attackAnim = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
    "attack", "Resources/industrial-zone/characters/attack_6frames.png", 0, 0, 0);
attackAnim.load();

// Container
StateVariantLoader playerAnims = new AnimationAndSpriteLoader.StateVariantLoader("player");
playerAnims.addState("walk", walkAnim);
playerAnims.addState("attack", attackAnim);

// ════════════════════════════════════════════════════════════════════════════════
// TIER 2: Setup game systems
// ════════════════════════════════════════════════════════════════════════════════

// Physics
PhysicsUnitSystem physics = new AnimationAndSpriteLoader.PhysicsUnitSystem();
PhysicsBody playerBody = physics.createBody(100, 100, 32, 32);

// Input
InputHandler input = new AnimationAndSpriteLoader.InputHandler();
input.attachToFrame(gameFrame);

// State machine
StateTransition playerState = new AnimationAndSpriteLoader.StateTransition();
playerState.addState("idle");
playerState.addState("walk");
playerState.addState("attack");
playerState.addTransition("idle", "walk", () -> Math.abs(moveVelocity) > 0);
playerState.addTransition("walk", "attack", () -> input.isKeyPressed(KeyEvent.VK_SPACE));
playerState.addTransition("attack", "idle", () -> !attacking);

// ════════════════════════════════════════════════════════════════════════════════
// TIER 3: Game loop
// ════════════════════════════════════════════════════════════════════════════════

float frameIndex = 0;
float animTimer = 0;
final float FRAME_DELAY = 100; // 100ms per frame

while (running) {
    // Input
    float moveVelocity = 0;
    if (input.isKeyDown(KeyEvent.VK_D)) moveVelocity += 5;
    if (input.isKeyDown(KeyEvent.VK_A)) moveVelocity -= 5;
    
    // Detection
    boolean wantToAttack = input.isKeyDown(KeyEvent.VK_SPACE);
    
    // State machine
    playerState.update();
    String state = playerState.getCurrentState();
    
    // Physics
    playerBody.setVelocity(moveVelocity, playerBody.getVelocityY());
    playerBody.applyForce(0, -9.8f);  // Gravity
    physics.update(deltaTime);
    
    // Animation
    playerAnims.switchState(state.equals("attack") ? "attack" : "walk");
    animTimer += deltaTime;
    if (animTimer >= FRAME_DELAY) {
        animTimer -= FRAME_DELAY;
        frameIndex = (frameIndex + 1) % playerAnims.getAnimationForCurrentState().getFrameCount();
    }
    
    // Rendering
    BufferedImage playerFrame = playerAnims.getFrame((int)frameIndex);
    g.drawImage(playerFrame, playerBody.getX(), playerBody.getY(), null);
}
```

---

## 📚 ASSET PATH NAMING CONVENTIONS

All resources should follow this pattern:

```
Resources/industrial-zone/
├── characters/
│   └── {CHARACTER_TYPE}/
│       ├── idle_{N}frames.png
│       ├── walk_{N}frames.png
│       ├── run_{N}frames.png
│       ├── jump_{N}frames.png
│       ├── attack_{N}frames.png
│       └── hurt_{N}frames.png
│
├── weapons/
│   └── {WEAPON_TYPE}/
│       ├── projectile_{N}frames.png
│       └── impact_{N}frames.png
│
├── vfx/
│   └── {EFFECT_TYPE}/
│       ├── particle_{N}frames.png
│       └── {effect}_animation.png
│
├── gui/
│   ├── button_{N}states_vertical.png
│   ├── panel_background.png
│   └── icons/
│       └── {icon}_single.png
│
├── 1 Tiles/
│   └── Level{N}/
│       ├── tileset_{N}tiles.png
│       └── hazards_{N}variants.png
└── audio/
    └── {sound_type}/
        └── {sound_name}.wav
```

---

## ✅ CHECKLIST FOR ADDING NEW ASSETS

When adding a new animation/sprite to the game:

- [ ] Create PNG file with naming convention: `{name}_{N}frames.png` or `{name}_vertical.png`
- [ ] Place in appropriate Resources/industrial-zone/ subdirectory
- [ ] Choose appropriate loader (Horizontal, Vertical, Grid, Single)
- [ ] Create loader instance: `new AnimationAndSpriteLoader.{LoaderType}(...)`
- [ ] Call `.load()` and verify returned true
- [ ] Store in appropriate container (StateVariantLoader or Object[][] array)
- [ ] Integrate into entity controller or game loop
- [ ] Test frame timing and visual appearance
- [ ] Log success with frame count: `System.out.println("✓ Loaded: " + loader.getFrameCount() + " frames")`
- [ ] Document in code with comment showing asset path

---

## 🔍 DEBUG & TROUBLESHOOTING

**Error: "Asset not found"**
- Verify exact file path (case-sensitive on Linux)
- Check Resources/industrial-zone/ folder structure
- Paste exact error message path into file explorer

**Error: "Incorrect frame count"**
- Check filename encoding (underscore placement)
- Manually specify frame count in loader constructor if auto-detection fails
- Use SpriteMetadata.analyze() to verify

**Error: "Animation plays too fast"**
- Each loader type has different frame timing
- HorizontalSpritesheetLoader default: 100ms per frame
- Adjust with `.setAnimationSpeed()` or timing array

**Error: "Frames are corrupted/stretched"**
- Verify spritesheet dimensions are divisible by frame count
- Example: 320px wide ÷ 8 frames = 40px per frame (must be exact)
- Check image format (must be PNG)

---

End of Index Document
