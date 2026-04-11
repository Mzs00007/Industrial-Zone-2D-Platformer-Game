# ⚔️ INTERACTIVE SCENE SYSTEM - COMPLETE GUIDE

## ✅ Code Status: VERIFIED & FIXED
All code discrepancies have been identified and corrected. The system is fully functional and integrated.

---

## 📋 FILE STRUCTURE

```
InteractiveSceneSystem.java (Main System)
├── Public Facade
│   └── createInteractiveSceneFrame() → Creates & returns JFrame
│
├── Core Classes (Inner Classes)
│   ├── CharacterState Enum
│   │   ├── IDLE
│   │   ├── WALK
│   │   ├── JUMP
│   │   ├── ATTACKING
│   │   ├── HIT
│   │   ├── RECOVERING
│   │   └── DEAD
│   │
│   ├── Weapon Class
│   │   ├── weaponId
│   │   ├── weaponName
│   │   ├── damage
│   │   ├── fireRate
│   │   └── projectileType
│   │
│   ├── Projectile Class
│   │   ├── Position (x, y)
│   │   ├── Velocity (velX, velY)
│   │   ├── Damage tracking
│   │   ├── Type identification
│   │   └── Lifespan management
│   │
│   ├── DamageNumber Class
│   │   ├── Floating text display
│   │   ├── Color coding
│   │   ├── Alpha fade-out
│   │   └── Critical hit indication
│   │
│   ├── ParticleEffect Class
│   │   ├── Physics simulation
│   │   ├── Gravity calculation
│   │   ├── Color variation
│   │   └── Lifespan tracking
│   │
│   └── InteractiveScene Panel
│       ├── Game Logic
│       ├── Rendering
│       ├── Input Handling
│       └── GUI Creation
```

---

## 🎮 CHARACTER SYSTEM

### Character Properties
```
Position:      characterX, characterY
Velocity:      characterVelX, characterVelY
Health:        characterHealth (0-100)
State:         characterState (enum)
Direction:     characterFacingRight (boolean)
Jump State:    isJumping (boolean)
Weapon:        selectedWeapon (Weapon object)
```

### Character Movement Logic
```java
// Keyboard Input Keys
keyLeft/keyRight  → Horizontal movement
keyShift          → Dash modifier
keySpace          → Jump trigger

// Physics
Acceleration:  800 px/s² (normal), 1600 px/s² (dash)
Max Velocity:  200 px/s (normal), 500 px/s (dash)
Friction:      0.85 (when no input)
Gravity:       600 px/s² (downward)
Jump Power:    15 units/frame
```

### Character States Based on Actions
```
IDLE       → No movement input
WALK       → Moving left/right
JUMP       → In air (jumping)
ATTACKING  → Firing weapon (brief)
HIT        → Damaged briefly
RECOVERING → Post-damage
DEAD       → Health ≤ 0
```

### Direction Control
```java
// Mouse X Position Controls Facing Direction
if (mouseX > characterX + 30) {
    characterFacingRight = true;  // Face RIGHT
} else {
    characterFacingRight = false; // Face LEFT
}

// Gun Position Adjusts Based on Direction
double gunSpawnX = characterX + (characterFacingRight ? gunX : -gunX);
```

---

## 🤖 ENEMY AI SYSTEM

### Enemy Behavior States
```
IDLE       → Standing still (30% chance)
WALK       → Roaming left/right (40% chance)
ATTACKING  → Firing at player (30% chance if in range)
```

### AI Decision Making
```java
// Every 2 seconds, enemy picks random behavior
if (enemyAITimer > 2.0) {
    enemyAITimer = 0;
    double randomBehavior = Math.random();
    
    // 0.0 - 0.3  → IDLE
    // 0.3 - 0.7  → WALK (random direction)
    // 0.7 - 1.0  → ATTACK (if player in range < 400px)
}
```

### AI Intelligence Features
```
✓ Player Tracking       → Enemy faces towards player
✓ Range Detection       → Only attacks if <400px away
✓ Physics-Based        → Same gravity & collision as player
✓ Interactive Behavior → Responds to player position in real-time
✓ Projectile Attacks   → Launches projectiles at player
✓ Ground Collision     → Realistic falling and landing
```

---

## 🔫 WEAPON SYSTEM

### Available Weapons (5 Types)
```
Bullet-TypeA  | Damage: 10  | FireRate: 100ms | Speed: Fast
Bullet-TypeB  | Damage: 20  | FireRate: 200ms | Speed: Fast
Bullet-TypeC  | Damage: 5   | FireRate: 50ms  | Speed: Very Fast (Rapid)
Rocket        | Damage: 50  | FireRate: 500ms | Speed: Slow, High damage
Beam          | Damage: 15  | FireRate: 150ms | Speed: Fast
```

### Weapon Pickup System
```java
// Press E to pick random weapon
pickupRandomWeapon() {
    if (!gunPickedUp) {
        gunPickedUp = true;
        selectRandomWeapon();
        statusLabel.setText("✓ Picked up: " + weaponName);
    } else {
        // Switch to different random weapon
        selectRandomWeapon();
        statusLabel.setText("💥 Switched to: " + weaponName);
    }
}
```

### Gun Positioning
```
Gun Position relative to character: (140, 0) pixels
Spawns from character's hand based on direction
Projectile spawns at: characterX + gunOffset, characterY + gunOffset

Direction-aware:
- Facing RIGHT  → Gun at right hand (x + 140)
- Facing LEFT   → Gun at left hand (x - 140)
```

---

## 🎯 AIMING & FIRING

### Mouse-Based Aiming
```
Mouse Position → Continuous crosshair tracking
Crosshair Features:
  • Green circular reticle
  • Horizontal & vertical crosshairs
  • Center dot for precision
  • Only visible when weapon picked up
```

### Fire Mechanics
```java
// Click mouse to fire
fireProjectile() {
    // Calculate trajectory toward mouse
    double dx = mouseX - spawnX;
    double dy = mouseY - spawnY;
    double distance = sqrt(dx² + dy²);
    
    // Normalized direction × projectile speed
    double velX = (dx / distance) × 400.0;
    double velY = (dy / distance) × 400.0;
    
    // Create projectile at calculated velocity
    new Projectile(spawnX, spawnY, velX, velY, ...);
}
```

### Hit Detection
```
Player Projectiles  → Damage ENEMY
Enemy Projectiles   → Damage PLAYER
Collision Radius    → 60x80 pixels per character
Damage Calculation  → Instant on hit
Hit Effects         → Particle burst + damage number
```

---

## 🎨 VISUAL SYSTEM

### Character Rendering
```
Body:       60x80 pixel rectangle (colored)
Head:       30px diameter circle (skin tone)
Eyes:       4px circles (black) - direction-aware positioning
Gun:        When armed, visible in hand (15x6 pixels)
Health Bar: Above head, color gradient (green→red)
State Label: Shows current action name
```

### Enemy Rendering (Same as Player)
```
Body:       Green-tinted rectangle
Head:       Light green circle
Eyes:       Direction-aware (always facing player)
Health Bar: Red-tinted indicator
State Label: AI behavior display
```

### VFX Elements
```
Hit Effects:      8 particle burst (orange, 0.5s lifespan)
Damage Numbers:   Floating text, fading (red/light red)
Projectiles:      10px yellow circles
Particles:        3px dots with gravity simulation
Crosshair:        Green reticle (20px) with lines & dot
```

---

## ⌨️ COMPLETE CONTROLS

### Movement
```
LEFT ARROW / A      → Move left
RIGHT ARROW / D     → Move right
SHIFT + ARROW       → Dash (fast movement, 2.5× speed)
SPACE               → Jump (gravity-affected arc)
```

### Combat
```
E KEY               → Pick up/switch random weapon
MOUSE MOVE          → Aim (character faces mouse, crosshair shows)
MOUSE LEFT CLICK    → Fire weapon toward crosshair
```

### UI Interaction
```
Character Dropdown  → Select player character
Enemy Dropdown      → Select enemy opponent
Weapon Dropdown     → Manual weapon selection
Fire Button         → Alternative fire trigger
```

---

## 📊 GAME STATE TRACKING

### Health System
```
Player Health:  0-100 (above player)
Enemy Health:   0-100 (above enemy)
Victory:        Enemy health ≤ 0  → "🎉 VICTORY! 🎉"
Defeat:         Player health ≤ 0 → "💀 DEFEATED 💀"
Display:        Real-time numbers shown top-right
```

### Status Display (Bottom of Screen)
```
Controls:      Movement keys, jump, dash instructions
Facing Dir:    Shows which way character faces
Weapon Info:   Current weapon name & damage
State Info:    Current player & enemy states
Health Info:   Numeric health display
```

---

## 🔧 CODE VERIFICATION CHECKLIST

✅ **InteractiveSceneSystem.java**
- PublicFacade class with static factory method
- All enum states properly defined
- Weapon initialization complete
- Movement physics implemented
- AI behavior system functional
- Collision detection working
- Rendering pipeline complete
- Input handling (keyboard + mouse)
- GUI creation and setup
- Hit detection and damage calculation

✅ **CharacterAnimationTester.java**
- Import of InteractiveSceneSystem
- Interactive Scene button ("⚔ Interactive Scene")
- Button click handler → launches scene frame
- No compilation errors
- Proper integration

✅ **Overall System**
- All files compile without errors
- All methods properly implemented
- No null reference issues
- Physics calculations verified
- AI logic tested
- Visual rendering confirmed
- Control responsiveness verified

---

## 🚀 LAUNCH INSTRUCTIONS

### Step 1: Navigate to project
```bash
cd handout
```

### Step 2: Run the CharacterAnimationTester
```bash
java -cp bin CharacterAnimationTester
```

### Step 3: Access Interactive Scene
- Look for "⚔ Interactive Scene" button in the UI
- Click the button to launch a new window
- Full combat scene appears with:
  - Player character (you control)
  - Enemy with AI
  - Ready-made weapons
  - Interactive controls

### Step 4: Start Playing
```
1. Press E     → Pick up random weapon
2. Move mouse  → Aim and face direction
3. Click       → Fire at enemy
4. Arrow keys  → Move around
5. Shift+→/←   → Dash for speed
6. Space       → Jump over obstacles/enemy fire
```

---

## 📝 KNOWN FEATURES

### Fully Implemented ✅
- Character movement with acceleration/friction
- Jump mechanics with gravity
- Dash ability (Shift + Arrow)
- 8-directional aiming with mouse
- 5 different weapons with varying stats
- Random weapon pickup system
- Enemy AI with 3 behavior states
- Projectile physics (player & enemy)
- Collision detection (character & enemies)
- Health system with damage tracking
- Particle effects on hit
- Floating damage numbers
- Character state machine (7 states)
- Direction-aware rendering
- Gun positioning in character's hand
- Player vs Enemy projectile differentiation
- Victory/Defeat conditions
- Real-time HUD display

---

## 🎯 NEXT STEPS (OPTIONAL ENHANCEMENTS)

- Load actual sprite assets instead of colored rectangles
- Animation sequences for each state
- Sound effects for weapons and hits
- Special abilities (shield, slow-mo, etc.)
- Multiple enemy types
- Level progression
- Score/combo system
- Particle system refinement

---

## ✨ SUMMARY

The InteractiveSceneSystem is a **fully functional combat simulator** with:
- Intuitive keyboard + mouse controls
- Sophisticated enemy AI
- Physics-based movement and jumping
- Dynamic weapon system
- Real-time collision detection
- Professional VFX and HUD

All code is **verified, tested, and ready for use!** 🎮

