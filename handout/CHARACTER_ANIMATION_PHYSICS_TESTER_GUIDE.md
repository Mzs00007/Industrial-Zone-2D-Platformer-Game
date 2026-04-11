# 🎮 Character Animation + Physics + AI Tester - Complete Guide

## Overview

The **CharacterAnimationPhysicsTester** is a comprehensive interactive testing tool that allows you to:
- Test all 24 character animation states with simple keyboard controls
- Adjust physics constants (gravity, velocity, acceleration, etc.) in real-time
- Tune enemy AI parameters simultaneously
- Tune boss AI parameters simultaneously
- Export all constants as Java code for copy-paste into your game

## Launch the Tester

```bash
cd handout
javac -cp "bin;src" -d bin src/CharacterAnimationPhysicsTester.java
java -cp bin CharacterAnimationPhysicsTester
```

## 24 Animation States - Keyboard Controls

### IDLE STATES (0-3)
| State | Key | Description |
|-------|-----|-------------|
| IDLE_NEUTRAL | **1** | Default idle pose |
| IDLE_BORED | **2** | Fidgeting animation |
| IDLE_ALERT | **3** | Alert pose |
| IDLE_SPECIAL | **4** | Special variant idle |

### MOVEMENT STATES (4-7)
| State | Key | Description |
|-------|-----|-------------|
| WALK_LEFT | **Q** | Walking left |
| WALK_RIGHT | **W** | Walking right |
| RUN_LEFT | **A** | Running left |
| RUN_RIGHT | **S** | Running right |

### JUMP STATES (8-11)
| State | Key | Description |
|-------|-----|-------------|
| JUMP_ASCEND | **E** | Ascending jump |
| JUMP_PEAK | **R** | Jump peak |
| JUMP_DESCEND | **T** | Descending to land |
| JUMP_LAND | **Y** | Landing impact |

### ATTACK STATES (12-15)
| State | Key | Description |
|-------|-----|-------------|
| ATTACK_LIGHT | **Z** | Light attack |
| ATTACK_HEAVY | **X** | Heavy attack |
| ATTACK_SPECIAL | **C** | Special attack |
| ATTACK_COMBO | **V** | Combo attack |

### DAMAGE STATES (16-19)
| State | Key | Description |
|-------|-----|-------------|
| DAMAGE_SMALL | **U** | Small damage |
| DAMAGE_LARGE | **I** | Large damage/knockback |
| KNOCKBACK_HIT | **O** | Mid-air knockback |
| KNOCKBACK_RECOVER | **P** | Recovery from knockback |

### SPECIAL STATES (20-23)
| State | Key | Description |
|-------|-----|-------------|
| POWERUP_ACTIVE | **D** | Power-up/boost active |
| SKILL_CAST | **F** | Skill casting |
| DEATH_FALL | **G** | Death/falling |
| RESPAWN_APPEAR | **H** | Respawn/appear |

## Physics Constants Tuning

Adjust physics in real-time using **SHIFT + keys**:

| Constant | Keys | Default | Effect |
|----------|------|---------|--------|
| Gravity | SHIFT + W/A | -0.6 | Downward acceleration |
| Max Jump Velocity | SHIFT + R/T | -15.0 | Jump power |
| Horizontal Speed | SHIFT + Q/E | 5.0 | Walk/run speed |
| Run Acceleration | SHIFT + Y/U | 0.8 | Speed ramp-up |
| Air Friction | SHIFT + I/O | 0.95 | Air resistance |
| Damage Knockback | *(future)* | 8.0 | Pushback on hit |

**Example:** SHIFT + W increases gravity (faster falling)
**Example:** SHIFT + A decreases gravity (slower falling)

### Real-World Physics Tuning Tips

- **Too floaty?** Increase gravity (SHIFT + W)
- **Jumps too high?** Decrease jump velocity (SHIFT + T)
- **Slow movement?** Increase horizontal speed (SHIFT + Q)
- **Slippery control?** Increase air friction (SHIFT + I)

## Enemy AI Constants Tuning

Adjust enemy behavior in real-time using **CTRL + keys**:

| Constant | Keys | Default | Effect |
|----------|------|---------|--------|
| Detection Range | CTRL + W/A | 200px | How far enemies see |
| Chase Speed | CTRL + S/D | 3.0 | Chase pursuit speed |
| Attack Cooldown | CTRL + Q/E | 60 frames | Time between attacks |
| Attack Range | CTRL + R/T | 50px | Melee reach distance |
| Patrol Distance | CTRL + Y/U | 150px | Patrol walk distance |
| Decision Frequency | CTRL + I/O | 30 frames | AI decision interval |

**Example:** CTRL + A decreases detection range (enemies see less)
**Example:** CTRL + S increases chase speed (enemies faster)

### Enemy AI Tuning Tips

- **Too aggressive?** Increase attack cooldown (CTRL + Q)
- **Too easy to see?** Decrease detection range (CTRL + A)
- **Chase too slow?** Increase chase speed (CTRL + D)
- **Attack too strong?** Decrease attack range (CTRL + T)

## Boss AI Constants Tuning

Adjust boss behavior in real-time using **ALT + keys**:

| Constant | Keys | Default | Effect |
|----------|------|---------|--------|
| Phase Transition | ALT + W/A | 75% | Health threshold for phase |
| Special Attack Freq | ALT + S/D | 0.3 | How often special attacks |
| Aggression Level | ALT + Q/E | 0.7 | Offensive intensity (0.1-1.0) |
| Pattern Complexity | ALT + R/T | 0.8 | Attack pattern difficulty |
| Recovery Speed | ALT + Y/U | 0.5 | How fast boss recovers |
| Difficulty Multiplier | ALT + I/O | 1.0x | Overall difficulty scaling |

**Example:** ALT + W decreases phase health (earlier phase 2)
**Example:** ALT + S increases special attack frequency

### Boss AI Tuning Tips

- **Too easy?** Increase difficulty multiplier (ALT + I)
- **Too many phases?** Decrease phase health threshold (ALT + A)
- **Phase 2 too aggressive?** Decrease aggression (ALT + E)
- **Boss patterns predictable?** Increase pattern complexity (ALT + R)

## Animation Display Controls

| Key | Function |
|-----|----------|
| **Space** | Play/Pause animation |
| **Z / Left Arrow** | Previous frame |
| **X / Right Arrow** | Next frame |
| **+ (Plus)** | Zoom in |
| **- (Minus)** | Zoom out |
| **F** | Flip horizontal (test symmetry) |
| **B** | Toggle checkerboard background |
| **ENTER** | Print all constants to console |
| **ESC** | Reset all values to defaults |

## Export Constants to Code

When you've tuned all values perfectly:

1. Press **ENTER** in the tester window
2. Check the console output - it shows all constants as Java code
3. Copy the constants directly into your game:

```java
// PHYSICS CONSTANTS
private static final double GRAVITY = -0.75;
private static final double MAX_JUMP_VELOCITY = -16.0;
private static final double HORIZONTAL_SPEED = 5.5;
private static final double RUN_ACCELERATION = 0.9;
private static final double AIR_FRICTION = 0.96;
private static final double DAMAGE_KNOCKBACK = 9.0;

// ENEMY AI CONSTANTS
private static final double ENEMY_DETECTION_RANGE = 220.0;
private static final double ENEMY_CHASE_SPEED = 3.5;
private static final int ENEMY_ATTACK_COOLDOWN = 55;
private static final double ENEMY_ATTACK_RANGE = 55.0;
private static final double ENEMY_PATROL_DISTANCE = 160.0;
private static final int ENEMY_DECISION_FREQUENCY = 28;

// BOSS AI CONSTANTS
private static final double BOSS_PHASE_HEALTH_THRESHOLD = 0.7;
private static final double BOSS_SPECIAL_ATTACK_FREQUENCY = 0.35;
private static final double BOSS_AGGRESSION_LEVEL = 0.8;
private static final double BOSS_PATTERN_COMPLEXITY = 0.9;
private static final double BOSS_RECOVERY_SPEED = 0.6;
private static final double BOSS_DIFFICULTY_MULTIPLIER = 1.2;
```

## Workflow Example

### Tuning Character Physics

1. **Press 'E'** - Switch to JUMP_ASCEND animation
2. **Press SHIFT+W** multiple times - Increase gravity until jump looks right
3. **Press SHIFT+R** - Adjust max jump velocity for height
4. **Press SHIFT+Q/E** - Test horizontal movement during jump
5. **Press SHIFT+I** - Fine-tune air friction for control
6. **Press ENTER** - Get the perfect constants

### Tuning Enemy Behavior

1. **Press CTRL+W** multiple times - Increase detection range until it feels fair
2. **Press CTRL+S** - Speed up chase to make it challenging
3. **Press CTRL+Q** - Adjust attack cooldown (more frequent = harder)
4. **Press CTRL+R** - Set attack range for melee reach
5. **Press ENTER** - Export for use in code

### Tuning Boss Fights

1. **Press ALT+I** - Increase difficulty multiplier for overall challenge
2. **Press ALT+S** - Increase special attack frequency for variation
3. **Press ALT+Q** - Adjust aggression for phase intensity
4. **Press ALT+R** - Increase pattern complexity for unpredictability
5. **Play test** - See how it feels in actual game
6. **Adjust** - Use tester to fine-tune further
7. **Press ENTER** - Export final constants

## Output Format

When you press ENTER, the console shows:

```
================================================================================
CURRENT CONSTANTS - Copy these values to your code:
================================================================================

// PHYSICS CONSTANTS
private static final double GRAVITY = -0.6;
private static final double MAX_JUMP_VELOCITY = -15.0;
...

// ENEMY AI CONSTANTS
private static final double ENEMY_DETECTION_RANGE = 200.0;
...

// BOSS AI CONSTANTS
private static final double BOSS_PHASE_HEALTH_THRESHOLD = 0.75;
...
================================================================================
```

Simply copy-paste these directly into your game classes!

## Tips for Best Results

1. **Tweak one constant at a time** - Change one value, test, then move to next
2. **Take notes** - Write down which values feel best
3. **Use extreme values** - Test min/max to find the range that works
4. **Test all states** - Cycle through all 24 states to ensure consistency
5. **Print often** - ENTER often to save your discoveries
6. **Reset often** - ESC to reset and start fresh if needed
7. **Use zoom** - Zoom in (+ key) to see animation details
8. **Check symmetry** - F key to flip and verify animations are balanced

## Structure Overview

The tester contains:
- **24 animation state definitions** with character codes
- **3 physics constant groups** (gravity, movement, damage)
- **2 AI constant groups** (enemy and boss)
- **Real-time adjustment system** with keyboard modifiers
- **Visual display panel** with zoom and flip controls
- **Console exports** of all constants as Java code

## Next Steps

1. Run the tester: `java -cp bin CharacterAnimationPhysicsTester`
2. Try all 24 animation states (keys 1-4, Q-H)
3. Adjust physics with SHIFT + arrow keys
4. Adjust enemy AI with CTRL + arrow keys
5. Adjust boss AI with ALT + arrow keys
6. Press ENTER to get final constants
7. Copy constants into your game code
8. Test in actual game and iterate

Happy tuning! 🎮
