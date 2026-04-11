# Game State System - Integration Template for Game.java

---

## How to Integrate Into Your Game Class

The **GameStateManager** handles all animation, input, and entity coordination. Your main `Game.java` should delegate to it.

### Step 1: Add Game State Manager Field

```java
public class Game extends JFrame {
    private AnimationAndSpriteLoader.GameStateManager gameState;
    private AnimationAndSpriteLoader.InputHandler inputHandler;
    
    public Game() {
        super("Game");
        // ... existing code ...
        
        // Initialize game state system
        gameState = new AnimationAndSpriteLoader.GameStateManager();
        
        // Create player physics body (starting position)
        AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody playerBody = 
            new AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody(
                new AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D(5, 15),  // x=5m, y=15m
                1.8f  // mass = 1.8 kg (player)
            );
        
        gameState.initialize(playerBody);
    }
}
```

### Step 2: Add Keyboard Input Handling

Wire up your keyboard listeners to `InputHandler`:

```java
public class Game extends JFrame {
    // ... existing code ...
    
    private void setupKeyboard() {
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                inputHandler.onKeyDown(e.getKeyCode());
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                inputHandler.onKeyUp(e.getKeyCode());
            }
            
            @Override
            public void keyTyped(KeyEvent e) {}
        };
        
        // Add to your game panel
        gamePanel.addKeyListener(keyListener);
        gamePanel.setFocusable(true);
    }
}
```

Key codes for common keys:
- `KeyEvent.VK_UP` (38) / `KeyEvent.VK_DOWN` (40)
- `KeyEvent.VK_LEFT` (37) / `KeyEvent.VK_RIGHT` (39)
- `KeyEvent.VK_SPACE` (32)
- `KeyEvent.VK_SHIFT` (16)

### Step 3: Main Game Loop

Replace your existing update loop:

```java
private void gameLoop() {
    float deltaTime = 1.0f / 60.0f;  // 60 FPS
    long lastFrameTime = System.nanoTime();
    
    while (isRunning) {
        long currentTime = System.nanoTime();
        float actualDeltaTime = (currentTime - lastFrameTime) / 1_000_000_000.0f;
        
        // UPDATE
        gameState.update(actualDeltaTime);
        
        // RENDER
        renderFrame();
        
        lastFrameTime = currentTime;
    }
}

private void renderFrame() {
    AnimationAndSpriteLoader.PlayerController player = gameState.getPlayer();
    
    if (player != null) {
        // Get current animation state
        AnimationAndSpriteLoader.AnimationState state = player.getCurrentState();
        int frameIndex = player.getCurrentFrame();
        
        // Get asset path (e.g., "Resources/industrial-zone/characters/player/idle.png")
        String assetPath = player.getAssetPath();
        
        // Get physics (position & velocity)
        float screenX = player.physics.getScreenX();  // In pixels
        float screenY = player.physics.getScreenY();  // In pixels
        
        // Load sprite sheet and draw current frame
        BufferedImage spriteSheet = loadImage(assetPath);
        drawAnimation(spriteSheet, frameIndex, screenX, screenY);
    }
    
    // Render all enemies
    for (AnimationAndSpriteLoader.EnemyController enemy : gameState.getEnemies()) {
        AnimationAndSpriteLoader.AnimationState state = enemy.getCurrentState();
        int frameIndex = enemy.getCurrentFrame();
        String assetPath = enemy.getAssetPath();
        float screenX = enemy.physics.getScreenX();
        float screenY = enemy.physics.getScreenY();
        drawAnimation(loadImage(assetPath), frameIndex, screenX, screenY);
    }
    
    // Render boss
    AnimationAndSpriteLoader.BossController boss = gameState.getBoss();
    if (boss != null) {
        // Same as enemy
    }
    
    // Render projectiles
    for (AnimationAndSpriteLoader.ProjectileController proj : gameState.getProjectiles()) {
        // Same pattern
    }
    
    // Render VFX (on top layer)
    for (AnimationAndSpriteLoader.VFXController vfx : gameState.getVFXEffects()) {
        // Same pattern
    }
}
```

### Step 4: Add Enemies Dynamically

```java
private void spawnEnemies() {
    // Drone enemy at position (50, 10)
    AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody droneBody =
        new AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody(
            new AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D(50, 10),
            1.2f  // mass
        );
    
    AnimationAndSpriteLoader.EnemyController drone =
        new AnimationAndSpriteLoader.EnemyController(droneBody, 12.0f);  // 12m detection radius
    
    gameState.addEnemy(drone);
}
```

### Step 5: Boss Battle

```java
private void startBossFight() {
    AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody bossBody =
        new AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody(
            new AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D(100, 8),
            5.0f  // Heavy (5 kg)
        );
    
    AnimationAndSpriteLoader.BossController boss =
        new AnimationAndSpriteLoader.BossController(bossBody);
    
    gameState.setBoss(boss);
}

// When player hits boss with weapon
private void damageBosse(float damage) {
    AnimationAndSpriteLoader.BossController boss = gameState.getBoss();
    if (boss != null) {
        boss.takeDamage(damage);
        
        // Boss phases automatically change:
        // > 50% health: BOSS_ATTACK_PHASE1
        // > 25% health: BOSS_ATTACK_PHASE2
        // ≤ 25% health: BOSS_SPECIAL (enhanced attacks)
    }
}
```

### Step 6: Projectile Firing

```java
private void fireProjectile(float velocityX, float velocityY, float damage) {
    AnimationAndSpriteLoader.PlayerController player = gameState.getPlayer();
    
    // Create projectile at player position
    AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody projBody =
        new AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody(
            player.physics.position.clone(),
            0.5f  // Light projectile
        );
    
    projBody.velocity.x = velocityX;  // m/s
    projBody.velocity.y = velocityY;  // m/s
    projBody.isAffectedByGravity = true;
    
    AnimationAndSpriteLoader.ProjectileController proj =
        new AnimationAndSpriteLoader.ProjectileController(projBody, damage);
    
    gameState.addProjectile(proj);
}
```

### Step 7: Visual Effects

```java
private void playExplosion(double x, double y) {
    AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody vfxBody =
        new AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody(
            new AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D((float)x, (float)y),
            0  // VFX don't have physics typically
        );
    
    AnimationAndSpriteLoader.VFXController explosion =
        new AnimationAndSpriteLoader.VFXController(vfxBody);
    
    explosion.transitionTo(AnimationAndSpriteLoader.AnimationState.EXPLOSION);
    
    gameState.addVFX(explosion);
    // Auto-removes when animation completes (10 frames × 70ms = 700ms)
}
```

---

## Input Flow: Space Key → Jump Animation

### User presses Space:
1. KeyListener intercepts `KeyEvent.VK_SPACE`
2. Calls `inputHandler.onKeyDown(KeyEvent.VK_SPACE)`
3. `InputHandler.keyPressed[32] = true`

### In game loop update:
```java
gameState.update(deltaTime);
  ↓
PlayerController.update(deltaTime)
  ↓
PlayerController.handleInput()
  ↓
if (input.isKeyPressed(InputHandler.KEY_SPACE) && isGround) {
    transitionTo(AnimationState.JUMP)
    physics.velocity.y = PhysicsUnitSystem.STANDARD_JUMP_VELOCITY  // -6.26 m/s
    isGrounded = false
}
```

### Next frames:
```
Frame 0: JUMP animation starts (frame 0/6)
Frame 1: JUMP frame 1/6
...
Frame 6: FALL state triggered (gravity accelerates downward)
...
Frame N: Player lands, LAND state, isGrounded = true
Frame N+1: IDLE state
```

---

## Physics Constants Quick Reference

```java
// From PhysicsUnitSystem (inside AnimationAndSpriteLoader)
PhysicsUnitSystem.GRAVITY          // -9.81 m/s² (negative = downward)
PhysicsUnitSystem.TIME_STEP        // 1/60 second (0.01667)
PhysicsUnitSystem.LINEAR_DAMPING   // 0.85 (ground friction)
PhysicsUnitSystem.AIR_DAMPING      // 0.15 (air resistance)

// Jump velocities (negative = upward, Y-down convention)
PhysicsUnitSystem.SMALL_JUMP_VELOCITY      // -3.84 m/s (0.75m high)
PhysicsUnitSystem.STANDARD_JUMP_VELOCITY   // -6.26 m/s (2.0m high)
PhysicsUnitSystem.HIGH_JUMP_VELOCITY       // -8.86 m/s (4.0m high)

// Unit conversion
1 Tile = 32 pixels = 1 Meter (configured in PhysicsBody)
```

---

## Complete Minimum Game Loop

```java
public class Game extends JFrame {
    private AnimationAndSpriteLoader.GameStateManager gameState;
    private AnimationAndSpriteLoader.InputHandler inputHandler;
    private boolean isRunning = true;
    
    public Game() {
        super("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        
        // Initialize game state
        gameState = new AnimationAndSpriteLoader.GameStateManager();
        inputHandler = gameState.getInput();
        
        // Create player
        AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody playerBody =
            new AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody(
                new AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D(5, 15),
                1.8f
            );
        gameState.initialize(playerBody);
        
        // Setup input
        setupKeyboard();
        
        // Start game loop
        startGameLoop();
    }
    
    private void setupKeyboard() {
        KeyListener keyListener = new KeyListener() {
            public void keyPressed(KeyEvent e) { inputHandler.onKeyDown(e.getKeyCode()); }
            public void keyReleased(KeyEvent e) { inputHandler.onKeyUp(e.getKeyCode()); }
            public void keyTyped(KeyEvent e) {}
        };
        getContentPane().addKeyListener(keyListener);
        getContentPane().setFocusable(true);
    }
    
    private void startGameLoop() {
        Thread gameThread = new Thread(() -> {
            long lastTime = System.nanoTime();
            float deltaTime = 1.0f / 60.0f;
            
            while (isRunning) {
                long currentTime = System.nanoTime();
                float actualDelta = (currentTime - lastTime) / 1_000_000_000.0f;
                
                gameState.update(actualDelta);
                repaint();
                
                lastTime = currentTime;
                
                // 60 FPS cap
                try { Thread.sleep(16); } catch (InterruptedException e) {}
            }
        });
        gameThread.start();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        renderFrame(g);
    }
    
    private void renderFrame(Graphics g) {
        AnimationAndSpriteLoader.PlayerController player = gameState.getPlayer();
        if (player != null) {
            String asset = player.getAssetPath();
            int frame = player.getCurrentFrame();
            float x = player.physics.getScreenX();
            float y = player.physics.getScreenY();
            
            // Draw sprite from animation system
            // g.drawImage(...); // Load asset and draw frame
        }
    }
    
    public static void main(String[] args) {
        new Game().setVisible(true);
    }
}
```

---

## Accessing System Components

```java
// Get player
AnimationAndSpriteLoader.PlayerController player = gameState.getPlayer();

// Get all enemies
java.util.List<AnimationAndSpriteLoader.EnemyController> enemies = gameState.getEnemies();

// Get boss
AnimationAndSpriteLoader.BossController boss = gameState.getBoss();

// Get input handler
AnimationAndSpriteLoader.InputHandler input = gameState.getInput();

// Get environment (parallax, tiles)
AnimationAndSpriteLoader.EnvironmentController env = gameState.getEnvironment();

// From player controller
AnimationAndSpriteLoader.AnimationState currentState = player.getCurrentState();
int currentFrame = player.getCurrentFrame();
String assetPath = player.getAssetPath();
float screenX = player.physics.getScreenX();
float screenY = player.physics.getScreenY();
boolean isGrounded = player.isGrounded();
```

---

## Collision Detection Integration

```java
// When player lands (gravity brought them down to platform)
// Set isGrounded = true
player.setGrounded(true);

// Check if projectile hits enemy
AnimationAndSpriteLoader.ProjectileController proj = ...;
AnimationAndSpriteLoader.EnemyController enemy = ...;

if (proj.physics.collidesWithAABB(enemy.physics)) {
    // Apply damage
    float damage = proj.getDamage();
    // Handle enemy knockout, loot, etc.
    gameState.getProjectiles().remove(proj);
}

// Check if player touches hazard
AnimationAndSpriteLoader.AnimationState tileState = environment.getTileAnimation(tileIndex);
if (tileState == AnimationAndSpriteLoader.AnimationState.HAZARD_ACTIVE) {
    // Player takes damage
    player.transitionTo(AnimationAndSpriteLoader.AnimationState.HURT);
}
```

---

## Summary

**Integration Points:**
1. Create `GameStateManager` once in Game constructor
2. Wire keyboard to `InputHandler`
3. Call `gameState.update(deltaTime)` in game loop
4. Query state + physics for rendering
5. Add entities with `addEnemy()`, `setBoss()`, `addProjectile()`, `addVFX()`
6. Handle collisions with `PhysicsBody.collidesWithAABB()`

**Status**: Ready for Game.java integration ✅

