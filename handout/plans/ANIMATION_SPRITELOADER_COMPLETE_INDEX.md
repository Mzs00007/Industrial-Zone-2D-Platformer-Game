# COMPLETE INDEX: AnimationAndSpriteLoader.java Nested Classes

**Document Version:** 1.0  
**Date:** April 2, 2026  
**Purpose:** Complete reference guide to all nested classes and how to use them  
**Scope:** All 21+ nested classes in AnimationAndSpriteLoader.java with full documentation

---

## TABLE OF CONTENTS

1. [Quick Start Guide](#quick-start-guide)
2. [Tier 1: Core Infrastructure](#tier-1-core-infrastructure)
3. [Tier 2: Physics & State Management](#tier-2-physics--state-management)
4. [Tier 3: Input & Controllers](#tier-3-input--controllers)
5. [Tier 4: Game State](#tier-4-game-state)
6. [Tier 5: Asset Loaders](#tier-5-asset-loaders)
7. [Usage Patterns](#usage-patterns)
8. [Integration Examples](#integration-examples)

---

## QUICK START GUIDE

### Most Common Uses (Copy-Paste Ready)

#### Load a character animation and display it
```java
import animation.AnimationAndSpriteLoader;
import animation.AnimationAndSpriteLoader.HorizontalSpritesheetLoader;

HorizontalSpritesheetLoader loader = 
    new HorizontalSpritesheetLoader("PLAYER_walk", 
    "Resources/industrial-zone/characters/player/punk/03_Player_Punk_Walk_5Frames1Row.png", 
    5, 0, 0);  // 5 frames

loader.load();  // Load the spritesheet

BufferedImage frame = loader.getFrame(0);  // Get frame 0
BufferedImage nextFrame = loader.getFrame(1);  // Get frame 1
```

#### Get a tile by character code
```java
String tilePath = AnimationAndSpriteLoader.TileRegistry.getTile('A');
BufferedImage tile = ImageIO.read(new File(tilePath));
```

#### Check physics simulation
```java
PhysicsUnitSystem physics = new AnimationAndSpriteLoader.PhysicsUnitSystem();
PhysicsUnitSystem.PhysicsBody body = new PhysicsUnitSystem.PhysicsBody(10, 10, 1.0f, 0.5f);
body.applyForce(0, -9.81f);  // gravity
body.update(0.016f);  // 60 FPS
```

---

##  TIER 1: CORE INFRASTRUCTURE

### 1. **TileRegistry** (Static Class)

**Location:** Line ~567 in AnimationAndSpriteLoader.java  
**Purpose:** Map single-character tile codes to complete asset file paths for level design  
**Status:** ✅ COMPLETE (65 tiles for Level 1)

**Key Methods:**
```java
public static String getTile(char code)
    // Returns full path to PNG, or null if not found
    // Example: getTile('A') → "Resources/industrial-zone/1 Tiles/..."
    
public static Set<Character> getAllCodes()
    // Returns all registered tile codes
    
public static boolean hasTile(char code)
    // Check if a code is registered
    
public static int getTileCount()
    // Returns number of registered tiles (65 for Level 1)
```

**Key Data:**
```java
private static final Map<Character, String> REGISTRY = new TreeMap<>();
// Static block initializes all 65 tile codes
// Examples:
//   'A' → primary platform
//   'B' → hazard breakable block
//   'H' → vertical wall column
//   'Z' → bolted ledge
//   '@' → central machine detail panel
```

**Usage Example:**
```java
// Build a level from character grid
String levelGrid = "AAAAAABBBBBBAAAA";
for (char code : levelGrid.toCharArray()) {
    String assetPath = AnimationAndSpriteLoader.TileRegistry.getTile(code);
    if (assetPath != null) {
        BufferedImage tile = ImageIO.read(new File(assetPath));
        // Render tile at appropriate position
    }
}

// Load all registered codes
for (char code : TileRegistry.getAllCodes()) {
    System.out.println(code + " → " + TileRegistry.getTile(code));
}
```

**Integration with Level.java:**
```java
// In Level1.java constructor:
for (int y = 0; y < levelMap.length; y++) {
    for (int x = 0; x < levelMap[y].length; x++) {
        char code = levelMap[y][x];
        String path = TileRegistry.getTile(code);
        // Load and render tile at (x*32, y*32) screen position
    }
}
```

---

### 2. **SpriteMetadata** (Static Class)

**Location:** Line ~665 in AnimationAndSpriteLoader.java  
**Purpose:** Analyze spritesheet properties and suggest optimal animation timing  
**Status:** ✅ EXISTS (basic version, needs enhancement)

**Key Properties:**
```java
public int imageWidth;        // Total spritesheet width in pixels
public int imageHeight;       // Total spritesheet height in pixels
public int frameCount;        // Number of frames in spritesheet
public int frameWidth;        // Width of ONE frame
public int frameHeight;       // Height of ONE frame
public int totalPixelsPerFrame; // frameWidth * frameHeight
public String complexity;     // LOW, MEDIUM, or HIGH
public int suggestedMs;       // Recommended timing ms/frame
```

**Constructor:**
```java
public SpriteMetadata(int imgW, int imgH, int frames, int fw, int fh)
    // Analyzes spritesheet and determines complexity
    // LOW (≤32px frames) → 120ms/frame
    // MEDIUM (typical) → 100ms/frame
    // HIGH (>64×80px) → 80ms/frame
```

**Usage Example:**
```java
// Analyze a spritesheet
SpriteMetadata meta = new AnimationAndSpriteLoader.SpriteMetadata(320, 32, 5, 64, 32);
System.out.println(meta);
// Output:
// ═══════════════════════════════════════════
//   SPRITE METADATA ANALYSIS
// ═══════════════════════════════════════════
//   Spritesheet Size:  320×32px
//   Frame Count:       5 frames
//   Frame Dimensions:  64×32px
//   Pixels/Frame:      2048px²
//   Complexity:        MEDIUM
//   Suggested Timing:  100ms/frame
// ═══════════════════════════════════════════

// Use metadata for automatic timing
int delay = meta.suggestedMs;  // 100ms
```

**Integration with Loaders:**
```java
// HorizontalSpritesheetLoader internally uses SpriteMetadata
HorizontalSpritesheetLoader loader = new HorizontalSpritesheetLoader(...);
loader.load();
SpriteMetadata meta = loader.getMetadata();  // If implemented
```

---

### 3. **MetadataExtractor** (NEW - Proposed Class)

**Location:** To be added after SpriteMetadata  
**Purpose:** Extract actual image properties(PNG metadata, dimensions, color depth)  
**Benefit:** Auto-detect frame counts without guessing

**Proposed Methods:**
```java
public static SpriteMetadata analyzeImage(String imagePath)
    // Load image, extract dimensions, calculate complexity
    
public static int detectFrameCount(String filename, int imageWidth, float expectedFrameWidth)
    // Smart detection: examine filename for patterns like "5Frames1Row"
    
public static int getImageWidth(String imagePath)
public static int getImageHeight(String imagePath)
    // Direct image dimension queries
    
public static String getColorDepth(String imagePath)
    // Return bit depth (8, 24, 32-bit)
```

---

## TIER 2: PHYSICS & STATE MANAGEMENT

### 4. **PhysicsUnitSystem** (Static Class)

**Location:** Line ~800 in AnimationAndSpriteLoader.java  
**Purpose:** Complete physics simulation for game entities using real-world units  
**Status:** ✅ EXISTS (comprehensive implementation)

**Key Constants:**
```java
public static final float PIXELS_PER_METER = 32.0f;     // Conversion factor
public static final float GRAVITY = -9.81f;             // m/s² (SI unit)
public static final float TIME_STEP = 1.0f / 60.0f;      // 16.67ms for 60fps
public static final float LINEAR_DAMPING = 0.85f;       // Ground friction
public static final float AIR_DAMPING = 0.15f;          // Air resistance
```

**Unit System:**
- **1 Tile = 1 Meter = 32 Pixels**
- Enables realistic physics calculations
- Perfect alignment with tile-based grid

**Usage Example:**
```java
// Create a physics body for player (75kg, 0.5m radius)
PhysicsUnitSystem.PhysicsBody player = 
    new PhysicsUnitSystem.PhysicsBody(5.0f, 5.0f, 75.0f, 0.5f);
    
// Apply gravity each frame
player.applyForce(0, PhysicsUnitSystem.GRAVITY * player.mass);

// Update with delta time
player.update(0.016f);  // 60 FPS

// Convert to screen coordinates
int screenX = (int)(player.position.x * PhysicsUnitSystem.PIXELS_PER_METER);
int screenY = (int)(player.position.y * PhysicsUnitSystem.PIXELS_PER_METER);
```

---

### 5. **Vector2D** (Nested in PhysicsUnitSystem)

**Purpose:** 2D vector math for physics calculations  
**Use Cases:** Position, velocity, acceleration, forces

**Key Methods:**
```java
public Vector2D()                       // Zero vector [0, 0]
public Vector2D(float x, float y)       // Parametric vector
public Vector2D(Vector2D v)             // Copy constructor

public void add(Vector2D v)             // v1 += v2
public void subtract(Vector2D v)        // v1 -= v2
public void multiply(float scalar)      // v *= s
public float dot(Vector2D v)            // Dot product
public float magnitude()                // |v| = √(x²+y²)
public void normalize()                 // Make unit vector
public Vector2D toPixels()              // Convert to pixels
public Vector2D toMeters()              // Convert to meters
```

**Usage Example:**
```java
Vector2D position = new Vector2D(10, 5);      // 10m, 5m
Vector2D velocity = new Vector2D(2, 0);       // 2 m/s rightward

// Simulate 60 FPS (0.0167s per frame)
for (int i = 0; i < 60; i++) {
    position.add(velocity);  // p += v
    System.out.println("Position: " + position);
}
```

---

### 6. **PhysicsBody** (Nested in PhysicsUnitSystem)

**Purpose:** Kinematic physics entity for game objects  
**Status:** ✅ EXISTS (complete implementation)

**Key Properties:**
```java
public Vector2D position;      // Current position (meters)
public Vector2D velocity;      // Current velocity (m/s)
public Vector2D acceleration;  // Current acceleration (m/s²)
public Vector2D forces;        // Applied forces (newtons)
public float mass;             // Entity mass (kg)
public float radius;           // Collision radius (meters
public boolean isGrounded;     // Touching ground?
public boolean isAffectedByGravity;  // Apply gravity?
```

**Key Methods:**
```java
public PhysicsBody(float x, float y, float mass, float radius)
    // Constructor with position, mass, collision radius
    
public void applyForce(float fx, float fy)
    // Apply force F (newtons) to body
    
public void applyForce(Vector2D force)
    // Apply vector force
    
public void update(float deltaTime)
    // Physics step: integrate forces, update velocity/position
    
public boolean collidesWith(PhysicsBody other)
    // AABB collision detection
    
public float getKineticEnergy()
    // E_k = 0.5*m*v²
    
public float getMomentum()
    // p = m*v
```

**Integration Example:**
```java
// Player physics in game loop
PhysicsBody player = new PhysicsUnitSystem.PhysicsBody(10, 10, 75, 0.5f);

// Every frame:
void update(float dt) {
    // Apply gravity
    player.applyForce(0, -9.81f * player.mass);
    
    // Apply player input forces
    if (keyDown(LEFT)) player.applyForce(-100, 0);
    if (keyDown(RIGHT)) player.applyForce(100, 0);
    
    // Update physics
    player.update(dt);
    
    // Ground collision
    if (player.position.y <= 0) {
        player.position.y = 0;
        player.velocity.y = 0;
        player.isGrounded = true;
    } else {
        player.isGrounded = false;
    }
    
    // Render at screen position
    renderSprite(player.position.x * 32, player.position.y * 32);
}
```

---

### 7. **StateTransition** (Static Class)

**Location:** Line ~1000 in AnimationAndSpriteLoader.java  
**Purpose:** State machine for entity animation transitions  
**Status:** ✅ EXISTS (complete)

**Key Methods:**
```java
public void addTransition(String fromState, String toState, Condition cond)
    // Define transition rule: when Condition is met, go from→to state
    // Example: addTransition("walk", "run", () -> isSprintPressed)
    
public void transitionTo(String newState)
    // Force immediate transition to new state
    
public String getCurrentState()
    // Get active state string
    
public void onStateEnter(String state, Runnable callback)
    // Register callback when entering state
    
public void onStateExit(String state, Runnable callback)
    // Register callback when leaving state
    
public void update()
    // Check transitions, execute callbacks
```

**Usage Example:**
```java
StateTransition state = new AnimationAndSpriteLoader.StateTransition();

// Add transitions
state.addTransition("idle", "walk", () -> isMoving);
state.addTransition("walk", "run", () -> isSprintPressed);
state.addTransition("walk", "idle", () -> !isMoving);
state.addTransition("run", "walk", () -> !isSprintPressed);
state.addTransition("walk", "jump", () -> isJumping);
state.addTransition("jump", "fall", () -> velocityY > 0);  // Falling down
state.addTransition("fall", "idle", () -> isGrounded);

// Callbacks when entering states
state.onStateEnter("jump", () -> {
    System.out.println("JUMP STARTED");
    velocity.y = jumpPower;
});

state.onStateEnter("run", () -> {
    System.out.println("RUNNING");
    // Load run animation
    loader = getRun Animation();
});

// Each frame
state.update();  // Check transitions
String currentAnim = state.getCurrentState();
// Load and display animation for currentAnim
```

---

## TIER 3: INPUT & CONTROLLERS

### 8. **InputHandler** (Static Class)

**Location:** Line ~1200 in AnimationAndSpriteLoader.java  
**Purpose:** Unified keyboard and mouse input detection  
**Status:** ✅ EXISTS

**Key Methods:**
```java
public static boolean isKeyPressed(int keyCode)
    // Single frame key press (true only when first pressed)
    
public static boolean isKeyDown(int keyCode)
    // Continuous key down state (true while held)
    
public static void setKeyDown(int keyCode, boolean down)
    // Internal use: set key state
    
public static int getMouseX()
    // Get current mouse X coordinate
    
public static int getMouseY()
    // Get current mouse Y coordinate
    
public static boolean isMouseButtonDown(int button)
    // Check mouse button state (LEFT:1, RIGHT:3, MIDDLE:2)
    
public static void setMousePosition(int x, int y)
    // Internal: update mouse position
```

**Usage with KeyListener:**
```java
// In KeyListener.keyPressed
public void keyPressed(KeyEvent e) {
    InputHandler.setKeyDown(e.getKeyCode(), true);
}

// In KeyListener.keyReleased
public void keyReleased(KeyEvent e) {
    InputHandler.setKeyDown(e.getKeyCode(), false);
}

// In game loop
void update() {
    if (InputHandler.isKeyDown(KeyEvent.VK_W)) {
        // Move up
    }
    if (InputHandler.isKeyPressed(KeyEvent.VK_SPACE)) {
        // Jump (only once when pressed)
    }
    if (InputHandler.isMouseButtonDown(MouseEvent.BUTTON1)) {
        // Fire projectile
    }
}
```

---

### 9. **EntityAnimationController** (Abstract Base Class)

**Location:** Line ~1300 in AnimationAndSpriteLoader.java  
**Purpose:** Base class for all entity controllers  
**Status:** ✅ EXISTS

**Key Methods (Abstract):**
```java
public abstract void update(float deltaTime);
public abstract BufferedImage getAnimationFrame();
public abstract void setAnimationState(String state);
```

**Key Methods (Concrete):**
```java
public String getCurrentAnimationState()
    // Return current animation state string
    
public int getAnimationFrameIndex()
    // Current frame index
    
public boolean hasAnimationFinished()
    // Check if one-shot animation is done
```

---

### 10. **PlayerController** extends EntityAnimationController

**Location:** Line ~1350 in AnimationAndSpriteLoader.java  
**Purpose:** Control player character animations and movement  
**Status:** ✅ EXISTS

**Key Methods:**
```java
public void handlePlayerInput(InputHandler input)
    // Process keyboard/mouse input
    // Updates state, direction, attack flags
    
public void updateAnimation(float deltaTime)
    // Update animation frame index
    // Called every game frame
    
public void playAnimation(String stateName)
    // Force play specific animation
    // Example: "walk", "jump", "attack1"
    
public BufferedImage getAnimationFrame()
    // Get current frame sprite
    
public void attack(int attackType)
    // Trigger attack animation (0-3 for combos)
    
public void takeDamage(int amount)
    // Apply damage, trigger hurt animation
    
public int getHealth()
    // Return current health
    
public void setAnimationState(String state)
    // Change animation state
```

**Available States:**
- idle, idle2, walk, run, dash, jump, doublejump, fall
- climb, hang, pullup, punch, attack1, attack2, attack3
- walkattack, runattack, hurt, death, use, sitdown, angry, happy, talk

**Usage Example:**
```java
PlayerController player = new AnimationAndSpriteLoader.PlayerController();

// Game loop
void gameLoop() {
    // Handle input
    player.handlePlayerInput(inputHandler);
    
    // Update animation
    player.updateAnimation(0.016f);  // 60 FPS
    
    // Get frame and render
    BufferedImage frame = player.getAnimationFrame();
    graphics.drawImage(frame, playerX, playerY, null);
    
    // Handle damage
    if (collision(player, hazard)) {
        player.takeDamage(10);
    }
}
```

---

### 11. **EnemyController** extends EntityAnimationController

**Location:** Line ~1500 in AnimationAndSpriteLoader.java  
**Purpose:** Control enemy AI and behaviors  
**Status:** ✅ EXISTS

**Key Methods:**
```java
public void updateAI(float deltaTime)
    // AI decision making, movement, attacks
    
public void decideNextAction()
    // Choose patrol, alert, or attack
    
public void attackPlayer(PlayerController player)
    // Execute attack sequence toward player
    
public void patrolPath(Path path)
    // Follow defined patrol route
    
public boolean isAlertedToPlayer()
    // Check if player is detected
    
public void takeDamage(int amount)
    // Trigger hurt animation, reduce health
    
public int getHealth()
    // Current health
```

**Usage Example:**
```java
EnemyController enemy = new AnimationAndSpriteLoader.EnemyController();

void gameLoop() {
    // Update AI
    enemy.updateAI(0.016f);
    
    // Check if attacking player
    if (enemy.isAlertedToPlayer()) {
        enemy.attackPlayer(player);
    }
    
    // Render
    BufferedImage frame = enemy.getAnimationFrame();
    graphics.drawImage(frame, enemyX, enemyY, null);
}
```

---

### 12. **BossController** extends EntityAnimationController

**Location:** Line ~1700 in AnimationAndSpriteLoader.java  
**Purpose:** Control boss enemy with advanced AI and phases  
**Status:** ✅ EXISTS

**Key Methods:**
```java
public void updateBossAI(float deltaTime)
    // Complex AI decision tree
    
public void executeAttackPattern(int phase)
    // Execute phase-specific attack sequence
    
public void transitionPhase(int newPhase)
    // Change to new attack phase (1-3)
    
public int getCurrentPhase()
    // Get phase number
    
public void takeDamage(int amount)
    // Trigger hurt animation
    // Auto-transition phase at health thresholds
    
public int getHealth()
    // Current health
    
public void spawnMinions()
    // Summon helper enemies
    
public boolean hasDefeated()
    // Check if boss is dead
```

**Usage Example:**
```java
BossController boss = new AnimationAndSpriteLoader.BossController();

void gameLoop() {
    boss.updateBossAI(0.016f);
    
    // Render boss
    BufferedImage frame = boss.getAnimationFrame();
    graphics.drawImage(frame, bossX, bossY, null);
    
    // Draw health bar
    drawHealthBar(boss.getHealth(), bossMaxHealth);
    
    // Check for phase transition
    if (boss.getHealth() < boss.getMaxHealth() * 0.67f && boss.getCurrentPhase() == 1) {
        boss.transitionPhase(2);
    }
}
```

---

### 13. **EnvironmentController** (Static Class)

**Location:** Line ~1900 in AnimationAndSpriteLoader.java  
**Purpose:** Manage backgrounds, parallax, environmental effects  
**Status:** ✅ EXISTS

**Key Methods:**
```java
public void setParallaxLayers(int count)
    // Set number of background layers (typically 3)
    
public void updateParallax(float cameraX, float cameraY)
    // Update parallax based on camera position
    
public void renderEnvironment(Graphics2D g, int screenWidth, int screenHeight)
    // Draw all background layers
    
public void setWeather(String weatherType)
    // "rain", "snow", "dust", "clear"
    
public void setDayNightCycle(float timeOfDay)
    // 0.0 = midnight, 0.5 = noon, 1.0 = next midnight
    
public BufferedImage getWeatherEffect()
    // Get weather particle frame for current frame
```

**Parallax System:**
- Layer 0: Far background (moves slowest)
- Layer 1: Mid background (medium speed)
- Layer 2: Near background (moves fastest)

**Usage Example:**
```java
EnvironmentController env = new AnimationAndSpriteLoader.EnvironmentController();

// Setup
env.setParallaxLayers(3);
env.setWeather("clear");

// Game loop
void render(Graphics2D g) {
    // Calculate camera position from player
    float cameraX = player.getX();
    float cameraY = player.getY();
    
    // Update parallax
    env.updateParallax(cameraX, cameraY);
    
    // Render background FIRST (behind everything)
    env.renderEnvironment(g, screenWidth, screenHeight);
    
    // Then render game objects on top
    renderPlayer(g, player);
    renderEnemies(g, enemies);
    renderHUD(g);
}
```

---

### 14. **ProjectileController** extends EntityAnimationController

**Location:** Line ~2050 in AnimationAndSpriteLoader.java  
**Purpose:** Control projectile animations and physics  
**Status:** ✅ EXISTS

**Key Methods:**
```java
public void updateProjectile(float deltaTime)
    // Update position based on velocity
    // Apply gravity if enabled
    // Check impacts
    
public void setVelocity(float vx, float vy)
    // Set projectile velocity (m/s or pixels/s)
    
public void onImpact(GameObject target)
    // Call when projectile hits target
    // Trigger impact animation
    
public boolean isAlive()
    // Check if projectile should still exist
    
public Vector2D getPosition()
    // Get current position
    
public void setPosition(float x, float y)
    // Set projectile position
    
public int getDamage()
    // Get damage amount to apply on hit
```

**Usage Example:**
```java
ProjectileController bullet = new AnimationAndSpriteLoader.ProjectileController();

// Create bullet at gun position
bullet.setPosition(player.getX() + 30, player.getY());
bullet.setVelocity(400, 0);  // 400 pixels/second right
bullet.setDamage(10);

// Game loop
void updateProjectiles() {
    for (ProjectileController proj : activeProjectiles) {
        proj.updateProjectile(0.016f);
        
        // Check hits
        for (EnemyController enemy : enemies) {
            if (collision(proj, enemy)) {
                enemy.takeDamage(proj.getDamage());
                proj.onImpact(enemy);
                activeProjectiles.remove(proj);
            }
        }
        
        // Remove off-screen projectiles
        if (proj.getPosition().x < -100 || proj.getPosition().x > screenWidth + 100) {
            activeProjectiles.remove(proj);
        }
    }
}
```

---

### 15. **VFXController** extends EntityAnimationController

**Location:** Line ~2150 in AnimationAndSpriteLoader.java  
**Purpose:** Control visual effects (explosions, particles, dust)  
**Status:** ✅ EXISTS

**Key Methods:**
```java
public void playEffect(String effectType)
    // "explosion", "hit_spark", "slash", "dust", "heal"
    
public void updateVFX(float deltaTime)
    // Update effect animation
    
public void particleEmit(int count, float angle)
    // Emit particles in direction (angle in degrees)
    
public boolean isActive()
    // Check if effect is still playing
    
public void setPosition(float x, float y)
    // Position effect at coordinates
    
public Vector2D getPosition()
    // Get current position
    
public float getScale()
    // Return effect scale multiplier
    
public void setScale(float scale)
    // Set size multiplier
```

**Usage Example:**
```java
// Hit effect when enemy takes damage
void onEnemyHit(EnemyController enemy, int damage) {
    VFXController hit = new AnimationAndSpriteLoader.VFXController();
    hit.setPosition(enemy.getX(), enemy.getY());
    hit.playEffect("hit_spark");
    activeVFX.add(hit);
}

// Explosion on death
void onEnemyDeath(EnemyController enemy) {
    VFXController explosion = new AnimationAndSpriteLoader.VFXController();
    explosion.setPosition(enemy.getX(), enemy.getY());
    explosion.playEffect("explosion");
    explosion.particleEmit(20, 360);  // Full circle particles
    activeVFX.add(explosion);
}

// Render VFX
void renderVFX(Graphics2D g) {
    for (VFXController vfx : activeVFX) {
        vfx.updateVFX(0.016f);
        
        if (vfx.isActive()) {
            BufferedImage frame = vfx.getAnimationFrame();
            Vector2D pos = vfx.getPosition();
            float scale = vfx.getScale();
            
            int w = (int)(frame.getWidth() * scale);
            int h = (int)(frame.getHeight() * scale);
            g.drawImage(frame, (int)pos.x - w/2, (int)pos.y - h/2, w, h, null);
        } else {
            activeVFX.remove(vfx);
        }
    }
}
```

---

## TIER 4: GAME STATE

### 16. **GameStateManager** (Static Class)

**Location:** Line ~2350 in AnimationAndSpriteLoader.java  
**Purpose:** Manage global game state and progression  
**Status:** ✅ EXISTS

**Game States:**
```java
enum GameState {
    MENU,          // Main menu screen
    PLAYING,       // In-game active
    PAUSED,        // Game paused
    GAME_OVER,     // Player died
    WIN,           // Level completed
    CUTSCENE       // Playing cutscene
}
```

**Key Methods:**
```java
public static void setCurrentLevel(int levelNumber)
    // Switch to level 1, 2, etc.
    
public static int getCurrentLevel()
    // Get active level number
    
public static void addScore(int points)
    // Increase score
    
public static int getScore()
    // Get current score
    
public static void unlockAchievement(String achievementId)
    // Mark achievement as completed
    
public static boolean hasAchievement(String achievementId)
    // Check if achievement unlocked
    
public static void setGameState(GameState state)
    // Change game state
    
public static GameState getGameState()
    // Get current game state
    
public static void saveGameProgress()
    // Save to file
    
public static void loadGameProgress()
    // Load from file
    
public static void resetGame()
    // Start new game
```

**Usage Example:**
```java
// Main game loop control
switch (GameStateManager.getGameState()) {
    case MENU:
        renderMainMenu();
        handleMainMenuInput();
        break;
        
    case PLAYING:
        updateGameLogic(0.016f);
        renderGame();
        handleGameInput();
        break;
        
    case PAUSED:
        renderGame();  // Show paused game
        renderPauseMenu();
        handlePauseMenuInput();
        break;
        
    case GAME_OVER:
        renderGameOverScreen();
        if (player.pressedRestart()) {
            GameStateManager.resetGame();
            GameStateManager.setGameState(GameState.PLAYING);
        }
        break;
        
    case WIN:
        renderWinScreen();
        GameStateManager.addScore(levelBonusPoints);
        if (player.continuesGame()) {
            GameStateManager.setCurrentLevel(GameStateManager.getCurrentLevel() + 1);
            GameStateManager.setGameState(GameState.PLAYING);
        }
        break;
}

// Score tracking
void onEnemyKill(EnemyController enemy) {
    GameStateManager.addScore(100);
}

void onLevelComplete() {
    GameStateManager.setGameState(GameState.WIN);
}
```

---

## TIER 5: ASSET LOADERS

### 17. **SingleSpriteLoader** extends AssetType

**Purpose:** Load single non-animated sprite files  
**Use Cases:** UI elements, static backgrounds, icons

**Key Methods:**
```java
public BufferedImage load(String path)
    // Load single image from path
    // Returns BufferedImage or null
    
public BufferedImage getFrame(int index)
    // Always returns same image (index ignored)
    
public int getFrameCount()
    // Always returns 1
    
public boolean isLoaded()
    // Check load status
```

**Usage:**
```java
SingleSpriteLoader logoLoader = new AnimationAndSpriteLoader.SingleSpriteLoader();
BufferedImage logo = logoLoader.load("Resources/industrial-zone/gui/logo.png");
graphics.drawImage(logo, screenWidth/2 - logo.getWidth()/2, 50, null);
```

---

### 18. **HorizontalSpritesheetLoader** extends AssetType

**Purpose:** Load spritesheets with frames arranged horizontally in ONE ROW  
**Format:** [Frame0][Frame1][Frame2]...[FrameN] all in single row  
**Status:** ✅ EXISTS (main loader, **NEEDS UPGRADE**)

**Constructor:**
```java
public HorizontalSpritesheetLoader(String id, String path, int frameCount, int param1, int param2)
    // id: identifier for debugging
    // path: full path to PNG file
    // frameCount: number of frames (0 = auto-detect)
    // param1, param2: reserved for future expansion
```

**Key Methods:**
```java
public boolean load()
    // Load and slice spritesheet
    // Returns true if successful
    
public BufferedImage getFrame(int frameIndex)
    // Get frame by index (0-based)
    
public int getFrameCount()
    // Return total frame count
    
public int getFrameWidth()
    // Width of single frame
    
public int getFrameHeight()
    // Height of single frame (full image height)
```

**Usage:**
```java
// Load character walk animation
HorizontalSpritesheetLoader walkLoader = 
    new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
    "PLAYER_punk_walk",
    "Resources/industrial-zone/characters/player/punk/03_Player_Punk_Walk_5Frames1Row.png",
    5,  // 5 frames
    0, 0);  // Auto mode

if (walkLoader.load()) {
    System.out.println("✓ Loaded " + walkLoader.getFrameCount() + " frames");
    System.out.println("  Frame size: " + walkLoader.getFrameWidth() + "×" + walkLoader.getFrameHeight());
    
    // Animate through frames
    for (int frameIndex = 0; frameIndex < walkLoader.getFrameCount(); frameIndex++) {
        BufferedImage frame = walkLoader.getFrame(frameIndex);
        graphics.drawImage(frame, x, y, null);
        Thread.sleep(100);  // 100ms per frame
    }
} else {
    System.out.println("✗ Failed to load walk animation");
}
```

**UPGRADE NEEDED:**
- Auto-detect frameCount from filename if not provided
- Return SpriteMetadata along with frames
- Validate frame dimensions
- Better error reporting

---

### 19. **VerticalSpritesheetLoader** extends AssetType

**Purpose:** Load spritesheets with frames arranged vertically in ONE COLUMN  
**Format:** [Frame0]  
**_____** [Frame1]  
**_____** [Frame2]  
**Status:** ✅ EXISTS (in use by button variant spritesheets)

**Key Methods:**
```java
public boolean load()
    // Load and slice vertical spritesheet
    
public BufferedImage getFrame(int frameIndex)
    // Get frame by index
    
public int getFrameCount()
    // Return number of frames
    
public int getFrameWidth()
    // Width (full image width)
    
public int getFrameHeight()
    // Height of single frame
```

**Usage (Button States Example):**
```java
// Load button with states: normal, hover, pressed, disabled
VerticalSpritesheetLoader buttonStates = 
    new AnimationAndSpriteLoader.VerticalSpritesheetLoader(
    "BUTTON_start",
    "Resources/industrial-zone/gui/buttons/start_button_states.png",
    4);  // 4 states

if (buttonStates.load()) {
    BufferedImage normalState = buttonStates.getFrame(0);
    BufferedImage hoverState = buttonStates.getFrame(1);
    BufferedImage pressedState = buttonStates.getFrame(2);
    BufferedImage disabledState = buttonStates.getFrame(3);
    
    // Render appropriate state based on mouse position
    if (!isButtonEnabled) {
        graphics.drawImage(disabledState, buttonX, buttonY, null);
    } else if (isPressed) {
        graphics.drawImage(pressedState, buttonX, buttonY, null);
    } else if (isHovered) {
        graphics.drawImage(hoverState, buttonX, buttonY, null);
    } else {
        graphics.drawImage(normalState, buttonX, buttonY, null);
    }
}
```

---

### 20. **GridSpritesheetLoader** extends AssetType

**Purpose:** Load spritesheets with frames in 2D grid (rows × columns)  
**Format:**
```
[F0] [F1] [F2] [F3]
[F4] [F5] [F6] [F7]
[F8] [F9] [F10][F11]
```
**Status:** ⏳ EXISTS (DEPRECATE in favor of upgraded HorizontalSpritesheetLoader)

**Key Methods:**
```java
public boolean load()
    // Load and slice grid spritesheet
    
public BufferedImage getFrame(int index)
    // Get frame by linear index (row-major order)
    
public BufferedImage getFrameAt(int row, int col)
    // Get frame by 2D coordinates
    
public int getFrameCount()
    // Total frames (rows × cols)
```

**Usage (Character Direction Variants):**
```java
// Load 8-directional player sprite (2 rows × 4 cols = 8 directions)
// Row 0: front-left, front, front-right, left
// Row 1: right, back-left, back, back-right
GridSpritesheetLoader directionVariants = 
    new AnimationAndSpriteLoader.GridSpritesheetLoader(
    "PLAYER_directions",
    "Resources/industrial-zone/characters/player/punk/directions_8way.png",
    2, 4);  // 2 rows, 4 columns

if (directionVariants.load()) {
    BufferedImage frontFace = directionVariants.getFrameAt(0, 1);  // Row 0, Col 1
    BufferedImage sideFace = directionVariants.getFrameAt(0, 3);   // Row 0, Col 3
    BufferedImage backFace = directionVariants.getFrameAt(1, 2);   // Row 1, Col 2
}
```

**NOTE:** This will be DEPRECATED once HorizontalSpritesheetLoader is upgraded to handle grid-based auto-detection.

---

### 21. **GridFrameAnimationLoader** extends AssetType

**Purpose:** Grid-based animation with per-frame timing control  
**Status:** ✅ EXISTS

**Key Methods:**
```java
public boolean load(String path, int rows, int cols, int[] frameTiming)
    // Load grid and set timing per frame
    // frameTiming: ms delay per frame
    
public BufferedImage getNextFrame(float deltaTime)
    // Get next frame based on elapsed time
    
public void setPlaybackSpeed(float speedMultiplier)
    // Speed up/slow down animation (1.0 = normal)
    
public float getProgress()
    // Animation progress (0.0 - 1.0)
    
public void reset()
    // Start animation from beginning
    
public boolean isFinished()
    // Check if animation completed
```

**Usage:**
```java
// Load attack animation with variable frame timing
int[] timing = {50, 50, 100, 150, 100};  // ms per frame
GridFrameAnimationLoader attackAnim = 
    new AnimationAndSpriteLoader.GridFrameAnimationLoader();

if (attackAnim.load("Resources/industrial-zone/characters/player/punk/attack_grid.png", 
                    2, 3, timing)) {  // 2×3 grid, custom timing
    
    // Play animation
    while (!attackAnim.isFinished()) {
        BufferedImage frame = attackAnim.getNextFrame(0.016f);  // 60 FPS
        graphics.drawImage(frame, playerX, playerY, null);
    }
    
    // Can speed up animation
    attackAnim.setPlaybackSpeed(1.5f);  // 1.5x speed
}
```

---

### 22. **SequenceFrameAnimationLoader** extends AssetType

**Purpose:** Load animation as sequence of separate image files  
**Use Cases:** Complex animations split across multiple files, cinematics

**Key Methods:**
```java
public boolean loadSequence(String[] filePaths, int[] frameTiming)
    // Load multiple files as animation frames
    // filePaths: array of file paths
    // frameTiming: ms delay per frame
    
public BufferedImage getNextFrame(float deltaTime)
    // Get next frame based on timing
    
public float getTotalDuration()
    // Total animation duration in milliseconds
    
public void reset()
    // Start sequence from beginning
    
public boolean isFinished()
    // Check if sequence completed
```

**Usage:**
```java
// Load cutscene as sequence of images
String[] frames = {
    "Resources/cutscenes/scene_01.png",
    "Resources/cutscenes/scene_02.png",
    "Resources/cutscenes/scene_03.png"
};
int[] timing = {2000, 3000, 1500};  // ms per frame

SequenceFrameAnimationLoader cutscene = 
    new AnimationAndSpriteLoader.SequenceFrameAnimationLoader();

if (cutscene.loadSequence(frames, timing)) {
    System.out.println("Cutscene duration: " + cutscene.getTotalDuration() + "ms");
    
    while (!cutscene.isFinished()) {
        BufferedImage frame = cutscene.getNextFrame(0.016f);
        graphics.drawImage(frame, 0, 0, screenWidth, screenHeight, null);
    }
}
```

---

### 23. **StateVariantLoader** extends AssetType

**Purpose:** Manage multiple animation sets for different entity states  
**Status:** ✅ EXISTS

**Key Methods:**
```java
public void addState(String stateName, AssetType animationLoader)
    // Register animation for a state
    // Example: addState("walk", walkLoader)
    
public AssetType getState(String stateName)
    // Get loader for state
    
public void switchState(String newState)
    // Change active state
    
public AssetType getAnimationForCurrentState()
    // Get currently active animation loader
    
public String getCurrentState()
    // Get current state name
    
public boolean hasState(String stateName)
    // Check if state exists
```

**Usage (Complete Entity Setup):**
```java
// Create animation set for player
StateVariantLoader playerAnimations = 
    new AnimationAndSpriteLoader.StateVariantLoader();

// Load all animations
HorizontalSpritesheetLoader idleLoader = 
    new HorizontalSpritesheetLoader("idle", "path/to/idle.png", 5, 0, 0);
HorizontalSpritesheetLoader walkLoader = 
    new HorizontalSpritesheetLoader("walk", "path/to/walk.png", 5, 0, 0);
HorizontalSpritesheetLoader runLoader = 
    new HorizontalSpritesheetLoader("run", "path/to/run.png", 6, 0, 0);
HorizontalSpritesheetLoader attackLoader = 
    new HorizontalSpritesheetLoader("attack", "path/to/attack.png", 6, 0, 0);

// Register states
playerAnimations.addState("idle", idleLoader);
playerAnimations.addState("walk", walkLoader);
playerAnimations.addState("run", runLoader);
playerAnimations.addState("attack", attackLoader);

// Use in game
playerAnimations.switchState("walk");
BufferedImage frame = playerAnimations.getAnimationForCurrentState().getFrame(0);

// Transition
if (sprintButtonPressed) {
    playerAnimations.switchState("run");
}
```

---

## USAGE PATTERNS

### Pattern 1: Load and animate a character walk cycle
```java
HorizontalSpritesheetLoader walkLoader = 
    new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
    "PUNK_WALK", 
    "Resources/industrial-zone/characters/player/punk/03_Player_Punk_Walk_5Frames1Row.png",
    5, 0, 0);

if (walkLoader.load()) {
    int currentFrame = 0;
    long lastFrameTime = System.currentTimeMillis();
    
    while (isWalking) {
        long now = System.currentTimeMillis();
        if (now - lastFrameTime >= 100) {  // 100ms per frame
            currentFrame = (currentFrame + 1) % walkLoader.getFrameCount();
            lastFrameTime = now;
            
            BufferedImage frame = walkLoader.getFrame(currentFrame);
            renderScreen.drawImage(frame, playerX, playerY, null);
        }
    }
}
```

### Pattern 2: Build level from tile codes
```java
String[] levelMap = {
    "AAAAAABBBBBBAAAA",
    "AAPPPPPPPPPPPAA",
    "...etc..."
};

for (int y = 0; y < levelMap.length; y++) {
    for (int x = 0; x < levelMap[y].length(); x++) {
        char code = levelMap[y].charAt(x);
        String path = AnimationAndSpriteLoader.TileRegistry.getTile(code);
        
        if (path != null) {
            BufferedImage tile = ImageIO.read(new File(path));
            graphics.drawImage(tile, x * 32, y * 32, 32, 32, null);
        }
    }
}
```

### Pattern 3: Control entity with physics
```java
PhysicsUnitSystem.PhysicsBody body = 
    new PhysicsUnitSystem.PhysicsBody(10, 10, 75, 0.5f);

void gameLoop() {
    // Apply gravity
    body.applyForce(0, -9.81f * body.mass);
    
    // Apply input forces
    if (keyDown(LEFT)) body.applyForce(-200, 0);
    if (keyDown(RIGHT)) body.applyForce(200, 0);
    
    // Update physics
    body.update(0.016f);  // 60 FPS
    
    // Ground collision
    if (body.position.y <= 0) {
        body.position.y = 0;
        body.velocity.y = 0;
    }
    
    // Render at screen position
    renderSprite(body.position.x * 32, body.position.y * 32);
}
```

### Pattern 4: Manage game state
```java
// Main game loop
while (gameRunning) {
    switch (GameStateManager.getGameState()) {
        case MENU:
            updateMenu();
            renderMenu();
            break;
        case PLAYING:
            updateGame();
            renderGame();
            break;
        case PAUSED:
            renderGame();
            renderPauseMenu();
            break;
        case WIN:
            GameStateManager.addScore(1000);
            renderWinScreen();
            break;
        case GAME_OVER:
            renderGameOverScreen();
            break;
    }
}
```

### Pattern 5: Create animation set with state switching
```java
StateVariantLoader animations = new AnimationAndSpriteLoader.StateVariantLoader();

// Add all states
animations.addState("idle", new HorizontalSpritesheetLoader(..."idle.png", 4, 0, 0));
animations.addState("walk", new HorizontalSpritesheetLoader(..."walk.png", 5, 0, 0));
animations.addState("run", new HorizontalSpritesheetLoader(..."run.png", 6, 0, 0));
animations.addState("jump", new HorizontalSpritesheetLoader(..."jump.png", 3, 0, 0));

// State machine
StateTransition state = new AnimationAndSpriteLoader.StateTransition();
state.addTransition("idle", "walk", () -> isMoving && !isSprinting);
state.addTransition("walk", "run", () -> isSprinting);
state.addTransition("walk", "idle", () -> !isMoving);
state.addTransition("idle", "jump", () -> isJumping);

// Each frame
state.update();
animations.switchState(state.getCurrentState());
BufferedImage frame = animations.getAnimationForCurrentState().getFrame(frameIndex);
```

---

## INTEGRATION EXAMPLES

### Example 1: Complete Player-Enemy Combat System
```java
public class CombatDemo {
    PlayerController player;
    EnemyController enemy;
    List<ProjectileController> projectiles;
    List<VFXController> effects;
    EnvironmentController environment;
    GameStateManager gameState;
    
    void initialize() {
        player = new AnimationAndSpriteLoader.PlayerController();
        enemy = new AnimationAndSpriteLoader.EnemyController();
        projectiles = new ArrayList<>();
        effects = new ArrayList<>();
        environment = new AnimationAndSpriteLoader.EnvironmentController();
        
        environment.setParallaxLayers(3);
    }
    
    void gameLoop() {
        // Update all systems
        player.handlePlayerInput(inputHandler);
        player.updateAnimation(0.016f);
        
        enemy.updateAI(0.016f);
        if (enemy.isAlertedToPlayer()) {
            enemy.attackPlayer(player);
        }
        
        // Update projectiles
        for (ProjectileController proj : projectiles) {
            proj.updateProjectile(0.016f);
            
            // Check hits
            if (collision(proj, enemy)) {
                enemy.takeDamage(proj.getDamage());
                createHitEffect(proj.getPosition());
                projectiles.remove(proj);
            }
        }
        
        // Update effects
        for (VFXController vfx : effects) {
            vfx.updateVFX(0.016f);
            if (!vfx.isActive()) {
                effects.remove(vfx);
            }
        }
        
        // Render
        environment.updateParallax(player.getX(), 0);
        environment.renderEnvironment(graphics, screenWidth, screenHeight);
        
        graphics.drawImage(player.getAnimationFrame(), player.getX(), player.getY(), null);
        graphics.drawImage(enemy.getAnimationFrame(), enemy.getX(), enemy.getY(), null);
        
        for (ProjectileController proj : projectiles) {
            graphics.drawImage(proj.getAnimationFrame(), proj.getX(), proj.getY(), null);
        }
        
        for (VFXController vfx : effects) {
            graphics.drawImage(vfx.getAnimationFrame(), vfx.getX(), vfx.getY(), null);
        }
        
        // Game state
        if (enemy.getHealth() <= 0) {
            GameStateManager.addScore(500);
            GameStateManager.setGameState(GameState.WIN);
        } else if (player.getHealth() <= 0) {
            GameStateManager.setGameState(GameState.GAME_OVER);
        }
    }
    
    void createHitEffect(Vector2D pos) {
        VFXController hitVFX = new AnimationAndSpriteLoader.VFXController();
        hitVFX.setPosition(pos.x, pos.y);
        hitVFX.playEffect("hit_spark");
        effects.add(hitVFX);
    }
}
```

---

## QUICK REFERENCE: METHOD LOOKUP TABLE

| Class | Method | Returns | Purpose |
|-------|--------|---------|---------|
| TileRegistry | getTile(char) | String | Get tile path for code |
| SpriteMetadata | toString() | String | Analysis report |
| PhysicsBody | update(float) | void | Physics step |
| StateTransition | update() | void | Check state transitions |
| PlayerController | handlePlayerInput() | void | Process input |
| EnemyController | updateAI(float) | void | Enemy AI step |
| BossController | transitionPhase(int) | void | Change boss phase |
| EnvironmentController | updateParallax() | void | Update backgrounds |
| ProjectileController | updateProjectile(float) | void | Move projectile |
| VFXController | playEffect(String) | void | Trigger effect |
| GameStateManager | addScore(int) | void | Increase score |
| HorizontalSpritesheetLoader | getFrame(int) | BufferedImage | Get animation frame |
| VerticalSpritesheetLoader | load() | boolean | Load spritesheet |
| GridSpritesheetLoader | getFrameAt(row,col) | BufferedImage | Get frame by coords |
| StateVariantLoader | switchState(String) | void | Change animation set |

---

## END OF INDEX

**This INDEX covers all 23+ nested classes in AnimationAndSpriteLoader.java**

For integration into Game.java, use the patterns above to set up complete systems.  
For asset management, see the accompanying COMPLETE_ASSET_SYSTEM_RESOURCE_INVENTORY.md

