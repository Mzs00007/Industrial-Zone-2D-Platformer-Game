# Game State System - Complete Usage Examples

---

## Example 1: Player Jump Mechanics

### Scenario: User presses SPACE to make player jump

**Preconditions:**
- Player is grounded
- Player is not already jumping
- Jump button (SPACE) is pressed

**Code Flow:**

```java
// User presses Space
keyListener.keyPressed(KeyEvent e) {
    inputHandler.onKeyDown(e.getKeyCode());  // keyCode = 32 (SPACE)
}

// In game loop
gameState.update(deltaTime);
  ↓ calls:
playerController.update(deltaTime);
  ↓ calls:
playerController.handleInput();
  ↓
if (input.isKeyPressed(InputHandler.KEY_SPACE) && isGrounded) {
    // JUMP SEQUENCE STARTS
    transitionTo(AnimationState.JUMP);
    physics.velocity.y = PhysicsUnitSystem.STANDARD_JUMP_VELOCITY;
    // = -6.26 m/s (negative = upward in Y-down convention)
    
    isGrounded = false;
    isInAir = true;
    jumpCount = 1;
}
```

### Animation & Physics Over Time:

```
Frame 0: State=JUMP, Frame=0/6
  - velocity.y = -6.26 m/s
  - position.y += velocity.y * TIME_STEP = 10.00 - 0.1043 = 9.8957m
  
Frame 1: State=JUMP, Frame=1/6
  - Gravity applied: velocity.y -= 9.81 * TIME_STEP = -6.26 - 0.1637 = -6.4237 m/s
  - Damping applied: velocity.y *= (1 - 0.15 * 0.01667) = -6.4237 * 0.9975 = -6.4119 m/s
  - position.y += velocity.y * TIME_STEP = 9.8957 - 0.1069 = 9.7888m
  
Frames 2-3: Jump animation continues, Y position increases

Frame 4: Jump apex reached
  - velocity.y is near 0
  - Player reaches maximum height (~2.0m based on jump velocity)
  
Frame 5: State transitions to FALL
  - velocity.y starts becoming positive (downward)
  - Gravity pulls player down
  - FALL animation starts
  
Frames 6-N: Falling
  - velocity.y increases (down) every frame
  - Terminal velocity limited
  - Air damping = 0.15 reduces velocity slightly each frame
  
Frame N: Player collides with platform (collision detection)
  - position.y == platform.y
  - physics.isGrounded = true
  - state transitions to LAND
  
Frame N+1: LAND animation (4 frames)
  - velocity.y = 0
  - position.y locked to platform
  
Frame N+5: State transitions to IDLE
  - Default state, ready for next input
```

### Physics Calculation Details:

```
Standard Jump Velocity Calculation:
- Height desired: 2.0 meters
- From physics: v² = 2 * g * h
- v = √(2 * 9.81 * 2.0) = √39.24 = 6.254 m/s
- In Y-down convention (negative = up): v = -6.254 m/s
- Stored as: PhysicsUnitSystem.STANDARD_JUMP_VELOCITY = -6.26 m/s

Jump Apex Time:
- v = v₀ + a*t
- 0 = -6.26 + 9.81*t
- t = 6.26 / 9.81 = 0.638 seconds ≈ 38 frames
- So jump takes ~0.64 seconds to apex

Time-Independent Damping:
- velocity *= (1.0f - damping * TIME_STEP)
- In air: velocity_new = velocity_old * (1.0 - 0.15 * 0.01667)
- velocity_new = velocity_old * 0.9975
- After 60 frames (1 second): velocity * 0.9975^60 ≈ velocity * 0.918 (8% loss)
```

---

## Example 2: Double Jump Mechanics

### Scenario: User rapidly presses UP arrow twice to double jump

**Code Flow:**

```
Frame 0: User presses UP
  - InputHandler records time: lastKeyTime[38] = System.currentTimeMillis()
  - isDoubleTap(KEY_UP) returns false (first tap)

Frame N: User releases UP and presses UP again within 250ms
  - currentTime = System.currentTimeMillis()
  - timeSinceLastTap = currentTime - lastKeyTime[38] = 120ms
  - if (120ms < 250ms && 120ms > 0) {  // TRUE
      return true;  // DOUBLE TAP DETECTED
    }

PlayerController.handleInput():
  - currentState == FALL (from previous jump)
  - isDoubleTap(KEY_UP) returns TRUE
  - jumpCount == 1 (from first jump)
  - Condition met: !isGrounded && jumpCount == 1
  
  transitionTo(AnimationState.DOUBLE_JUMP);
  physics.velocity.y = PhysicsUnitSystem.HIGH_JUMP_VELOCITY;  // -8.86 m/s
  jumpCount = 2;
```

### Animation Timeline:

```
Frame 0: First Jump (JUMP state, 6 frames)
Frame 6: Transition to FALL (4 frames)
Frame 8: Double-tap detected
  - State changes to DOUBLE_JUMP (8 frames)
  - velocity.y = -8.86 m/s (higher jump)
  - height = 4.0 meters
Frame 9: DOUBLE_JUMP animation frame 1
...
Frame 14: DOUBLE_JUMP animation frame 6
  - Starting to fall again, velocity.y becoming positive
Frame 15: Fall back to ground
Frame N+5: LAND animation
```

### Jump Heights Comparison:

```
STANDARD JUMP: 2.0 meters (velocity = -6.26 m/s)
DOUBLE_JUMP:   4.0 meters (velocity = -8.86 m/s)

Time to apex:
- STANDARD: 6.26 / 9.81 = 0.638 seconds
- DOUBLE:   8.86 / 9.81 = 0.903 seconds

Distance formula: d = v²/(2*g)
- STANDARD: d = 6.26²/(2*9.81) = 39.19/19.62 = 2.0m ✓
- DOUBLE:   d = 8.86²/(2*9.81) = 78.50/19.62 = 4.0m ✓
```

---

## Example 3: Dash Movement Physics

### Scenario: Player holds SHIFT + RIGHT arrow to dash

**Code Flow:**

```java
playerController.handleInput():

if (input.isKeyPressed(InputHandler.KEY_SHIFT)) {
    if (input.isKeyPressed(InputHandler.KEY_RIGHT)) {
        transitionTo(AnimationState.DASH_RIGHT);
        physics.velocity.x = 15.0f;  // 15 m/s horizontal (extremely fast)
    }
}
```

### Physics Behavior:

```
DASH_RIGHT State Physics:
- Velocity: 15.0 m/s (fast travel)
- Gravity: Still applied (1.0 multiplier)
- Linear damping: IGNORE (0, no friction)
- Color: Affects animation (fast dash frames)

Movement Per Frame:
- Position change: 15.0 m/s * 0.01667 s/frame = 0.25 meters/frame
- In pixels: 0.25m * 32 px/m = 8 pixels per frame ← very fast!
- Over 1 second (60 frames): 15.0 m = 480 pixels

Vertical Motion During Dash:
- Gravity still active: velocity.y can become positive (falling)
- Dash can be done mid-air while falling
- Player can land during dash → Auto-transition to IDLE
```

### Example Dash Trajectory:

```
Frame 0: Pressed SHIFT+RIGHT
  State: WALK_RIGHT → DASH_RIGHT
  velocity.x = 15.0 m/s
  velocity.y = current (falling from jump)

Frame 1: 
  velocity.x = 15.0 m/s (no damping)
  velocity.y += gravity = velocity.y + 9.81*0.01667 = velocity.y + 0.1637
  position.x += 0.25m (8 pixels right)

Frame 2-5: Continues dashing
  Horizontal velocity unchanged
  Vertical velocity increases (gravity)

Frame 6: Lands on platform
  velocity.y set to 0
  isGrounded = true
  State → IDLE (no more left/right input)
```

---

## Example 4: Enemy Detection & Chase

### Scenario: Player approaches enemy within detection radius

**Code Setup:**

```java
// Create enemy with 12-meter detection radius
AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody droneBody =
    new AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody(
        new AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D(50, 10),
        1.2f  // mass
    );

AnimationAndSpriteLoader.EnemyController drone =
    new AnimationAndSpriteLoader.EnemyController(droneBody, 12.0f);  // Detection: 12m

gameState.addEnemy(drone);
```

**Game Loop Execution:**

```
Frame 0: 
  Player position: x=30m
  Enemy position: x=50m
  Distance: 20m (outside 12m detection radius)
  Enemy state: ENEMY_IDLE
  velocity.x = 0

Frame N: Player walks toward enemy
  Player position: x=42m
  Enemy position: x=50m
  Distance: |42 - 50| = 8m (INSIDE 12m radius!)
  
  In update():
  enemy.updateAI(playerPhysics);
  {
    float distance = |42 - 50| = 8m
    if (8m < 12m) {
        isAlerted = true;
        transitionTo(AnimationState.ENEMY_CHASE);
    }
  }

Frame N+1:
  Enemy state: ENEMY_CHASE (6 frames, 80ms each frame)
  velocity.x = 4.0 m/s (moving left toward player)
  position.x -= 0.067m/frame (≈2 pixels/frame)
  
  ENEMY_CHASE animation showing running/chasing sprite

Frame N+20: Enemy reaches player
  Collision detected via collidesWithAABB()
  trigger damage
  transition to ENEMY_ATTACK
```

### Detection Radius Visualization:

```
          Detection Radius (12m)
    ← 12m ↔ ENEMY (x=50) ↔ 12m →
                    
Enemy range: x ∈ [38, 62]

Player positions:
- x=35m: Outside, ENEMY_IDLE
- x=42m: Inside, ENEMY_CHASE
- x=50m: Directly on enemy, ENEMY_ATTACK distance
- x=65m: Outside, ENEMY_IDLE
```

---

## Example 5: Boss Combat Phases

### Scenario: Boss health decreases, phases change automatically

**Code Setup:**

```java
AnimationAndSpriteLoader.BossController boss =
    new AnimationAndSpriteLoader.BossController(bossBody);

gameState.setBoss(boss);

// Boss starts with 100% health
// 3 phases based on health%
```

**Combat Timeline:**

```
PHASE 1: Health 100% → 50%
  State: BOSS_ATTACK_PHASE1
  Animation: 8 frames, 90ms per frame (720ms per attack pattern)
  Behavior:
    - Boss stands still
    - Attacks from current position
    - Player must dodge
    - Slower attack pattern

PHASE 2: Health 50% → 25%
  State: BOSS_ATTACK_PHASE2
  Animation: 10 frames, 80ms per frame (800ms per attack)
  Behavior:
    - Faster attack pattern
    - More frequent attacks
    - More damage per attack

PHASE 3: Health < 25%
  State: BOSS_SPECIAL
  Animation: 12 frames, 70ms per frame (840ms per attack)
  Behavior:
    - Desperate mode
    - Maximum damage attacks
    - All-out assault
    - Possible screen-shake effects
```

**Phase Transition Code (Automatic):**

```java
public void updateBehavior() {
    if (healthPercent > 0.5f) {
        transitionTo(AnimationState.BOSS_ATTACK_PHASE1);
    } else if (healthPercent > 0.25f) {
        transitionTo(AnimationState.BOSS_ATTACK_PHASE2);
    } else {
        transitionTo(AnimationState.BOSS_SPECIAL);
    }
}
```

**Taking Damage Example:**

```
Initial: healthPercent = 1.0f (100%)

Player hits boss with 15 damage:
boss.takeDamage(15.5f);
{
    healthPercent -= 15.5f;
    healthPercent = 1.0 - 15.5/100 = 0.845
}
→ Still in PHASE 1 (> 0.5f)

Player continues hitting:
after 3 more hits (total 47 damage):
healthPercent = 0.53
→ Still in PHASE 1 (> 0.5f)

After next hit (62 total damage):
healthPercent = 0.38
→ Transitions to PHASE 2 (> 0.25f)
→ Animation changes
→ Attack pattern intensifies

After continuing (75 damage):
healthPercent = 0.25
→ Transitions to PHASE 3 (≤ 0.25f)
→ BOSS_SPECIAL state
→ Desperate combat mode
```

---

## Example 6: Projectile Physics

### Scenario: Player fires weapon at 45° angle

**Projectile Creation:**

```java
// Player at (20, 8), firing northeast at 45°
float velocity = 12.0f;  // m/s
float angle = 45.0f;     // degrees

float vx = velocity * Math.cos(Math.toRadians(angle));  // 8.49 m/s
float vy = -velocity * Math.sin(Math.toRadians(angle)); // -8.49 m/s (negative = up)

AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody projBody =
    new AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody(
        new AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D(20, 8),
        0.5f  // light projectile
    );

projBody.velocity.x = vx;
projBody.velocity.y = vy;
projBody.isAffectedByGravity = true;

AnimationAndSpriteLoader.ProjectileController proj =
    new AnimationAndSpriteLoader.ProjectileController(projBody, 25.0f);  // 25 damage

gameState.addProjectile(proj);
```

**Projectile Trajectory Over Time:**

```
Frame 0: x=20m, y=8m
  velocity.x = 8.49 m/s
  velocity.y = -8.49 m/s (upward)
  Position: (20, 8)

Frame 1: 
  velocity.y += gravity = -8.49 + 9.81*0.01667 = -8.33 m/s
  velocity.y *= damping = -8.33 * 0.9975 = -8.30 m/s
  position.x = 20 + 8.49*0.01667 = 20.141m
  position.y = 8 - 8.30*0.01667 = 7.862m

Frame 10:
  velocity.x = 8.49 m/s (unchanged, no air damping on X)
  velocity.y = approaching 0 (gravity pulling down, heading to apex)
  position.x ≈ 21.4m
  position.y ≈ 8.3m (apex reached around frame 8-9)

Frame 20:
  velocity.y = positive (now falling)
  position.x ≈ 22.8m
  position.y ≈ 6.5m (below starting height)

Frame 40:
  Terminal velocity reached
  Parabolic arc visible
  position.x ≈ 25.6m
  position.y ≈ 2.0m

Frame 63: Still alive (60 seconds * 1 frame/second < 5 second limit)
  position.x ≈ 29.6m
  position.y ≈ -10m (far below, likely offscreen)

Frame 301+ (5 seconds):
  isAlive() returns false
  ProjectileController removed from gameState
  Memory freed
```

**Realistic Parabolic Path:**

```
        ╱╲
       ╱  ╲
      ╱    ╲
Firing ────► ←── Landing

Initial velocity: 12 m/s at 45°
Range (horizontal) = v²*sin(2θ)/g = 144*sin(90°)/9.81 = 14.7m
Max height = v²*sin²(θ)/(2g) = 144*0.5/19.62 = 3.67m above fire point
```

---

## Example 7: Complete Round Sequence

### Scenario: Full gameplay loop - 30 seconds of gameplay

```
t=0.0s: Game starts
  Player: position=(5, 15), state=IDLE
  Enemy: position=(50, 10), state=ENEMY_IDLE, distance=45m
  Boss: Not spawned yet
  Input: None

t=0.5s: Player presses RIGHT, runs toward enemy
  Player: Move velocity.x=5 m/s, state=WALK_RIGHT
  position.x = 5 + 5*0.5 = 7.5m
  Enemy: Still IDLE (45m away)

t=3.0s: Player continues running
  Player: position.x ≈ 20m
  Distance to enemy: 50-20=30m

t=5.0s: Player reaches close range
  Player: position.x ≈ 32.5m
  Enemy: DETECTS! Distance = 50-32.5=17.5m (over radius)
  ...wait, 17.5 > 12, not detected yet

t=6.0s: Player gets closer
  Player: position.x ≈ 37.5m
  Enemy: Distance = 50-37.5=12.5m NO, wait...
  Player: position.x = 37.5m
  Enemy: position.x = 50m
  Distance = |50-37.5| = 12.5m (just barely outside!)

t=6.1s: 
  Player: position.x ≈ 37.55m
  Enemy: Distance ≈ 12.45m (still outside)

t=6.2s:
  Player: position.x ≈ 37.6m
  Enemy: Distance = 12.4m (INSIDE 12m detection!)
  Transition: ENEMY_IDLE → ENEMY_CHASE
  velocity.x = -4.0 m/s (move left toward player)

t=6.5s:
  Enemy: Moving toward player at 4 m/s
  Enemy position decreases
  position.x = 50 - 4*0.3 = 48.8m
  Player position: 37.6m
  Distance: 11.2m (still closing)

t=7.0s:
  Enemy: Reached player position (collision)
  Transition: ENEMY_CHASE → ENEMY_ATTACK
  velocity.x = 0
  Damage: 10 points to player

t=7.5s: Player hit by enemy
  Player: Transition → HURT state (3 frames, 100ms = 0.3s)
  Takes damage, health reduced

t=7.8s: Player recovers from hurt state
  Player: Transition → IDLE
  Can move again

t=8.0s: Player presses SPACE while falling from hurt knockback
  Player: State = FALL (standing on ground)
  Transition → JUMP
  velocity.y = -6.26 m/s
  
t=8.3s: Player at jump apex
  Player: Still in JUMP animation frame 3/6
  Decides to press SHIFT+RIGHT to dash away

t=8.4s: Jump → FALL → DASH_RIGHT
  Player: velocity.x = 15 m/s (fast retreat)
  Moving right at high speed

t=9.0s: Player lands after jump+dash
  velocity.y = 0
  position.y = platform height
  Transition → IDLE

t=10.0s: Player is safe distance away
  Enemy: Lost player (distance > 12m)
  Transition: ENEMY_ATTACK → ENEMY_IDLE
  Back to idle behavior

t=15.0s: Player approaches again, this time readies weapon
  Player: Approaching enemy position

t=16.0s: Player fires projectile
  Projectile created at (40, 8)
  velocity: (8.49, -8.49) m/s at 45°
  Damage: 25 points

t=16.5s: Projectile hits enemy
  Collision detected: proj.collidesWithAABB(enemy)
  Enemy health -= 25
  Projectile removed from game
  Enemy: Transition → ENEMY_HURT (3 frames)

t=16.8s: Enemy recovers
  Enemy: Back to ENEMY_ATTACK or ENEMY_CHASE
  
t=20.0s: Boss door exits
  Boss spawned at (100, 8)
  gameState.setBoss(boss)

t=25.0s: Player approaches boss
  Boss: Detects? (no detection radius on boss, always active)
  Transition: BOSS_IDLE → BOSS_ATTACK_PHASE1
  
t=30.0s: Battle continues...
  Game runs smoothly at 60 FPS
  All systems updated: physics, animation, AI, rendering
```

---

## Summary of Mechanics

| Mechanic | Trigger | Animation | Physics | Duration |
|----------|---------|-----------|---------|----------|
| **Jump** | Space (grounded) | JUMP (6 frames) | vy=-6.26 m/s | ~1.3s total (jump+fall) |
| **Double Jump** | Up×2 (mid-air) | DOUBLE_JUMP (8 frames) | vy=-8.86 m/s | ~1.8s total |
| **Dash** | Shift+Arrow | DASH_LEFT/RIGHT (6 frames) | vx=±15 m/s | Player controlled |
| **Walk** | Arrow keys | WALK (8 frames) | vx=±5 m/s | Player controlled |
| **Enemy Detect** | Distance < radius | ENEMY_CHASE (8 frames) | vx varies | Continuous until far |
| **Enemy Attack** | Collision | ENEMY_ATTACK (5 frames) | vx=0 | Repeats while colliding |
| **Projectile** | Fire weapon | Spinning (N frames) | Physics based | 5s lifetime max |
| **VFX Effect** | Damage impact | EXPLOSION (10 frames) | None | Auto-cleanup |
| **Boss Phase** | Health % | ATTACK_PHASE1/2/SPECIAL | State dependent | Auto-transition |

All mechanics work together through **GameStateManager** - a production-ready gaming system!

