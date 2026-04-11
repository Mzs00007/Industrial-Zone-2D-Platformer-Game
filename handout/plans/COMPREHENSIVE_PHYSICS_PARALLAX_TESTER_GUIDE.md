# CharacterAnimationPhysicsTester v4.0
## Comprehensive Physics & Parallax Background Tuner

### New v4.0 Features

**Two Tabbed Interfaces:**

#### Tab 1: ⚡ Physics & AI Tuning
- **16 Physics Constants** for character movement and jumping
- **18 AI Parameters** across 3 enemy types (Ground, Air, Boss)
- Real-time adjustment with keyboard controls
- Yellow highlighting shows selected parameter

#### Tab 2: 🌄 Parallax Background Tester
- **Level 1** - Industrial Zone Entry (5 parallax layers)
- **Level 2 Day** - Power Station Daytime (5 layers)
- **Level 2 Night** - Power Station Nighttime (5 layers)
- **Real-time camera scrolling** to preview parallax effects
- **Per-layer depth adjustment** (0.0f - 1.0f scale)
- **Individual layer visibility toggles**

---

## Physics Tab - Complete Parameter Set

### Tier 1: Movement Control (4 Parameters)
```
Walk Speed              : 4.0 pixels/frame
Run Speed              : 8.0 pixels/frame
Walk Acceleration      : 0.3 (how quickly walk speed ramps up)
Run Acceleration       : 0.25 (how quickly run speed ramps up)
```
**How to tune:**
- Increase Walk Speed for faster-moving character
- Increase Run Speed for more aggressive dash/chase
- Increase Acceleration for snappier response

### Tier 2: Gravity System (2 Parameters)
```
Gravity                : 0.5 pixels/frame² (downward acceleration)
Max Fall Speed         : 12.0 pixels/frame (terminal velocity)
```
**How to tune:**
- Increase Gravity for heavier character, snappier falls
- Decrease Gravity for floaty, slowed descent
- Increase Max Fall Speed for faster drops

### Tier 3: Jump Mechanics (4 Parameters)
```
Max Jump Velocity      : 12.0 pixels/frame (upward initial velocity)
Double Jump Velocity   : 10.0 pixels/frame (secondary jump power)
Coyote Time            : 6.0 frames (forgiveness after leaving platform)
Jump Grace Period      : 4.0 frames (buffer input before jumping)
```
**How to tune:**
- Increase Max Jump Velocity for higher/longer jumps
- Adjust Coyote Time: 0=no forgiveness, 6-10=comfortable, 15+=generous
- Increase Jump Grace Period for forgiving jump timing

### Tier 4: Friction & Control (4 Parameters)
```
Ground Friction        : 0.15 (deceleration on ground, 0-1 scale)
Air Friction           : 0.05 (deceleration while airborne)
Wall Slide Friction    : 0.30 (slowdown when sliding walls)
Air Control            : 0.60 (steering ability while in air, 0-1)
```
**How to tune:**
- Increase Ground Friction for slippery terrain feel
- Decrease Air Friction for floaty aerial control
- Increase Air Control (0.8-1.0) for tight aerial movement

---

## AI Tab - Enemy Behavior Parameters

### Ground Enemy AI (6 Parameters)

```
Detection Range        : 300.0 pixels (how far they see player)
Chase Speed           : 2.5 pixels/frame (pursuit velocity)
Attack Cooldown       : 60 frames (time between attacks)
Attack Range          : 60.0 pixels (melee reach)
Patrol Distance       : 200.0 pixels (walking extent)
Acceleration          : 0.4 (speedup rate from patrol to chase)
```

**Tuning Tips:**
- Increase Detection Range for more aggressive enemies (300→500)
- Decrease Chase Speed (1.5) for weaker enemies, increase (4.0) for tougher
- Reduce Attack Cooldown (30) for rapid-fire attacks
- Decrease Attack Range (40) for melee-only, increase (100) for ranged

### Air Enemy AI (6 Parameters)

```
Detection Range        : 400.0 pixels (wider vision than ground)
Chase Speed           : 3.5 pixels/frame (faster than ground enemies)
Attack Cooldown       : 45 frames (more aggressive timing)
Attack Range          : 80.0 pixels (longer reach for airborne)
Patrol Altitude       : 150.0 pixels (hover height)
Vertical Speed        : 2.0 pixels/frame (climb/descent rate)
```

**Tuning Tips:**
- Air enemies naturally see further (400px) due to altitude
- Chase Speed is higher (3.5) - they're more dangerous
- Reduce Patrol Altitude (80) for lower-flying enemies
- Increase Vertical Speed (3.0-4.0) for aggressive diving

### Boss AI (6 Parameters)

```
Detection Range        : 500.0 pixels (always aware of player)
Chase Speed           : 3.0 pixels/frame (measured, powerful)
Attack Cooldown       : 40 frames (deadly, deliberate attacks)
Attack Range          : 100.0 pixels (boss reach)
Patrol Distance       : 250.0 pixels (large patrol area)
Acceleration          : 0.5 (heavy but responsive)
```

**Tuning Tips:**
- Boss Detection Range (500) - keeps them in pursuit
- Chase Speed (2.5-3.5) - powerful but not faster than player
- Attack Cooldown (50-70) - fewer but more dangerous attacks
- Attack Range (80-120) - boss commands respect

---

## Parallax Background Tab - Real-Time Tuning

### Background Modes
```
↑/↓ Arrow Keys  : Cycle between 3 backgrounds
  • Level 1 - Industrial Zone Entry
  • Level 2 - Power Station (Day)
  • Level 2 - Power Station (Night)
```

### Camera Scrolling
```
← Arrow Key     : Scroll left (camera moves backward)
→ Arrow Key     : Scroll right (camera moves forward)
```

### Layer Controls
```
Number Keys 1-5 : Toggle visibility of each layer
  • Layer 1 = Sky (stationary, factor 0.0)
  • Layer 2 = Near trees/factories (factor 0.15)
  • Layer 3 = Mid factories (factor 0.25)
  • Layer 4 = Far factories (factor 0.40)
  • Layer 5 = Foreground (factor 0.60)
```

### Depth Adjustment
```
+ Key           : Increase selected layer's depth factor (+0.05)
- Key           : Decrease selected layer's depth factor (-0.05)
```

### How Parallax Works
- **Depth Factor 0.0** = Layer doesn't move (static sky)
- **Depth Factor 0.5** = Moves at half camera speed
- **Depth Factor 1.0** = Moves with camera (foreground)

**Example Tweaking:**
```
Original: [0.0, 0.15, 0.25, 0.40, 0.60]
Make more dramatic: [0.0, 0.10, 0.20, 0.35, 0.70]
Make subtle: [0.0, 0.20, 0.30, 0.45, 0.55]
```

---

## Integration with AnimationAndSpriteLoader

### After Tuning Physics Parameters

Find these methods in `AnimationAndSpriteLoader.java` and update:

```java
// Update these constants in createLevel1ParallaxSystem()
float[] depths = {0.0f, 0.15f, 0.25f, 0.40f, 0.60f};

// Update these constants in createLevel2ParallaxSystemDay()
float[] depths = {0.0f, 0.15f, 0.25f, 0.40f, 0.60f};

// Update these constants in createLevel2ParallaxSystemNight()
float[] depths = {0.0f, 0.15f, 0.25f, 0.40f, 0.60f};
```

### After Tuning Enemy AI Parameters

Update the spawning code in `Level1.java` and `Level2.java`:

```java
// Ground Enemy AI constants
groundEnemy.detectionRange = 300.0f;
groundEnemy.chaseSpeed = 2.5f;
groundEnemy.attackCooldown = 60;

// Air Enemy AI constants
airEnemy.detectionRange = 400.0f;
airEnemy.chaseSpeed = 3.5f;
airEnemy.attackCooldown = 45;

// Boss AI constants
boss.detectionRange = 500.0f;
boss.chaseSpeed = 3.0f;
boss.attackCooldown = 40;
```

---

## How to Run

### From Handout Directory
```bash
cd handout
javac -cp src src/CharacterAnimationPhysicsTester.java -d bin
java -cp src:bin CharacterAnimationPhysicsTester
```

### From Project Root
```bash
cd handout
java -cp src:bin CharacterAnimationPhysicsTester
```

---

## Keyboard Shortcuts Reference

### Physics Tab Controls
```
SHIFT + ↑/↓        Navigate Physics parameters (16 total)
SHIFT + ←/→        Adjust selected Physics value

CTRL + ↑/↓         Navigate Ground Enemy parameters (6 total)
CTRL + ←/→         Adjust selected Ground Enemy value

CTRL+ALT + ↑/↓     Navigate Air Enemy parameters (6 total)
CTRL+ALT + ←/→     Adjust selected Air Enemy value

ALT + ↑/↓          Navigate Boss AI parameters (6 total)
ALT + ←/→          Adjust selected Boss value
```

### Parallax Tab Controls
```
↑/↓                Switch background mode
←/→                Scroll camera left/right

1-5                Toggle layer visibility
+/-                Adjust selected layer depth factor
```

---

## Best Practices

### Physics Tuning Order
1. **Adjust Movement** - Get walk/run speeds feeling right
2. **Adjust Jump** - Set jump height and air control
3. **Fine-tune Friction** - Smooth out movement response

### AI Tuning Order
1. **Ground Enemies** - Basic land-based threat
2. **Air Enemies** - Aerial harassment
3. **Boss** - Final challenge encounter

### Parallax Tuning Order
1. **Switch between backgrounds** - Notice the layer arrangement
2. **Scroll the camera** - Preview the parallax effect
3. **Adjust depths** - Create desired visual depth sensation
4. **Document values** - Note what looks best

---

## Common Tuning Scenarios

### "Game Feels Too Slow"
```
→ Increase: Walk Speed (5.0), Run Speed (10.0)
→ Increase: Acceleration values (0.4, 0.3)
→ Decrease: Friction values (0.08, 0.02)
```

### "Jumping Feels Floaty"
```
→ Decrease: Max Jump Velocity (10.0)
→ Increase: Gravity (0.7)
→ Decrease: Max Fall Speed (10.0)
```

### "Enemies Too Aggressive"
```
→ Decrease: Detection Range (200.0)
→ Decrease: Chase Speed (1.5)
→ Increase: Attack Cooldown (90)
```

### "Parallax Looks Flat"
```
→ Increase spread: [0.0, 0.05, 0.15, 0.45, 0.80]
→ Or adjust: Change factor 0.60 → 0.75
```

---

## File Statistics

- **Source:** 310 lines
- **Compiled Size:** 22.5 KB
- **Memory Usage:** ~80 MB at runtime
- **Dependencies:** animation.AnimationAndSpriteLoader only

## Version History
- **v4.0** (Apr 3, 2026) - Added Parallax Background tab, integrated ParallaxSystem from AnimationAndSpriteLoader
- **v3.0** - Original Physics + 3 Enemy AI types
