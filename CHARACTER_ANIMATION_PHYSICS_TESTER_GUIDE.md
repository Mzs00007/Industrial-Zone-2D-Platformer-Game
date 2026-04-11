# Character Animation Physics & Enemy AI Tester Guide

## Overview
`CharacterAnimationPhysicsTester` is an interactive physics parameter tuner and enemy AI configuration tool. It provides real-time adjustment of 32 game parameters across 4 systems with a live visual interface.

## File Location
- **Source:** `src/CharacterAnimationPhysicsTester.java` (18 KB)
- **Compiled:** `bin/CharacterAnimationPhysicsTester.class` (1.5 KB)

## How to Run
```bash
java -cp bin CharacterAnimationPhysicsTester
```

## Parameter Systems (32 Total)

### 1. Physics Constants (16 parameters)
Control fundamental character movement and jumping behavior.

**Movement Control (4 params):**
- Walk Speed: Default 4.0 (pixels/frame)
- Run Speed: Default 8.0 (pixels/frame)  
- Walk Acceleration: Default 0.3
- Run Acceleration: Default 0.25

**Gravity System (2 params):**
- Gravity: Default 0.5 (acceleration per frame)
- Max Fall Speed: Default 12.0 (pixels/frame terminal velocity)

**Jump Mechanics (4 params):**
- Max Jump Velocity: Default 12.0 (pixels/frame upward)
- Double Jump Velocity: Default 10.0 (second jump strength)
- Coyote Time: Default 6.0 (frames of forgiveness after leaving ground)
- Jump Grace Period: Default 4.0 (frames to buffer jump input)

**Friction & Control (4 params):**
- Ground Friction: Default 0.15 (deceleration multiplier)
- Air Friction: Default 0.05 (deceleration multiplier)
- Wall Slide Friction: Default 0.3 (wall slide slowness)
- Air Control: Default 0.6 (control while airborne, 0.0-1.0)

### 2. Ground Enemy AI (6 parameters)
Tune behavior for land-based enemies.

- **Detection Range:** Default 300.0 px (how far they see the player)
- **Chase Speed:** Default 2.5 px/frame (pursuit velocity)
- **Attack Cooldown:** Default 60 frames (between attacks)
- **Attack Range:** Default 60.0 px (melee reach)
- **Patrol Distance:** Default 200.0 px (patrol extent)
- **Acceleration:** Default 0.4 (how quickly they speed up)

### 3. Air Enemy AI (6 parameters)
Tune behavior for flying enemies.

- **Detection Range:** Default 400.0 px (wider vision than ground)
- **Chase Speed:** Default 3.5 px/frame (faster than ground)
- **Attack Cooldown:** Default 45 frames (more aggressive)
- **Attack Range:** Default 80.0 px (longer reach)
- **Patrol Altitude:** Default 150.0 px (hover height)
- **Vertical Speed:** Default 2.0 px/frame (climb/descent rate)

### 4. Boss AI (6 parameters)
Tune behavior for boss-level enemies.

- **Detection Range:** Default 500.0 px
- **Chase Speed:** Default 3.0 px/frame
- **Attack Cooldown:** Default 40 frames
- **Attack Range:** Default 100.0 px
- **Patrol Distance:** Default 250.0 px
- **Acceleration:** Default 0.5

## Keyboard Controls

### Physics Parameters (16)
```
SHIFT + UP/DOWN Arrow      → Navigate parameters
SHIFT + LEFT/RIGHT Arrow   → Adjust selected value
```
Adjustment granularity: ±0.1 per keystroke

### Ground Enemy AI (6)
```
CTRL + UP/DOWN Arrow       → Navigate parameters
CTRL + LEFT/RIGHT Arrow    → Adjust selected value
```
Adjustment granularity: ±5.0 per keystroke (speed/acceleration: ±0.1)

### Air Enemy AI (6)
```
CTRL + ALT + UP/DOWN Arrow → Navigate parameters
CTRL + ALT + LEFT/RIGHT    → Adjust selected value
```
Adjustment granularity: ±5.0 per keystroke (speed/acceleration: ±0.1)

### Boss AI (6)
```
ALT + UP/DOWN Arrow        → Navigate parameters
ALT + LEFT/RIGHT Arrow     → Adjust selected value
```
Adjustment granularity: ±5.0 per keystroke (speed/acceleration: ±0.1)

## Visual Interface Features

- **Left Panel:** 16 Physics Constants organized by category
  - Movement Control (blue)
  - Gravity System (orange)
  - Jump Mechanics (green)
  - Friction & Control (pink)

- **Right Panel:** Three enemy AI systems
  - Ground Enemy (brown)
  - Air Enemy (cyan)
  - Boss AI (purple)

- **Highlighting:** Selected parameter is highlighted in yellow for easy identification

- **Bottom Panel:** Quick keyboard reference for all control schemes

## Design Philosophy

Each system is independently adjustable:
- Physics constants control character capabilities (movement, jumping)
- Ground enemies can be tuned for land-based challenges
- Air enemies have separate parameters for flight behavior
- Boss AI allows for boss-specific challenge tuning

Parameters include sensible bounds to prevent invalid values:
- Movement speeds: minimum 0.1 px/frame
- Friction values: clamped 0.0-1.0
- Times: minimum 0 frames
- Ranges/distances: minimum 10 px

## Complete Architecture Summary

| System | Parameters | Min Value | Max Value | Notes |
|--------|-----------|-----------|-----------|-------|
| Physics Movement | 4 | 0.1 | Unlimited | Walk/run speed & acceleration |
| Physics Gravity | 2 | 0.01 | Unlimited | Gravity strength & fall speed |
| Physics Jump | 4 | 0.0 | Unlimited | Jump power & timing forgiveness |
| Physics Friction | 4 | 0.0 | 1.0 | Deceleration multipliers |
| Ground Enemy | 6 | 0.1-10 | Unlimited | Detection, chase, attack tuning |
| Air Enemy | 6 | 0.1-10 | Unlimited | Flight-specific parameters |
| Boss AI | 6 | 0.1-10 | Unlimited | Boss-level challenge tuning |
| **TOTAL** | **32** | - | - | **Complete game tuning suite** |

## Integration with Game

These parameters are designed to be read from this tester and copied into:
- `src/core/GameEngine.java` (physics constants)
- Enemy AI classes for their respective behaviors
- Configuration files or game state system

## Compilation & Deployment

**Compile:**
```bash
javac -d bin src/CharacterAnimationPhysicsTester.java
```

**Run:**
```bash
java -cp bin CharacterAnimationPhysicsTester
```

**File Statistics:**
- Source lines: ~500
- Compilation: < 1 second
- Memory usage: ~50 MB when running
- No external dependencies required
