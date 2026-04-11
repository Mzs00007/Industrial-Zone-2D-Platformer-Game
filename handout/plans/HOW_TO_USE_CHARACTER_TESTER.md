# How to Use Character Animation Physics AI Tester

## Quick Start

```bash
cd handout
javac -cp "bin;src" -d bin src/CharacterAnimationPhysicsTester.java
java -cp bin CharacterAnimationPhysicsTester
```

## Example Testing Session

### Step 1: Test Animation States
Open the tester and press these keys in sequence:

```
Press 1 → See IDLE_NEUTRAL animation
Press 2 → See IDLE_BORED animation
Press Q → See WALK_LEFT animation
Press E → See JUMP_ASCEND animation
Press Z → See ATTACK_LIGHT animation
```

### Step 2: Test Physics - Make Jump Feel Better

The default gravity may feel too floaty. Let's tune it:

```
Press E (to show jump animation)
Press SHIFT+W multiple times (increase gravity)
Now the character falls faster - more responsive!

If it's too heavy:
Press SHIFT+A a few times (decrease gravity)

Fine-tune until it feels right
```

### Step 3: Test Jump Height

Jump feels good, but too high? Adjust jump velocity:

```
Press SHIFT+T a few times (decrease jump velocity)
Jump is now lower - more snappy

If you want higher jumps:
Press SHIFT+R (increase jump velocity)
```

### Step 4: Test Movement Speed

Character moving too slow?

```
Press W (walk right animation)
Press SHIFT+Q (increase horizontal speed)
Now character walks faster!
```

### Step 5: Test Enemy AI

You want enemies to see the player from farther away?

```
Press CTRL+W (increase detection range to 210)
Press CTRL+W again (increase to 220)

Enemies now notice player from further away

Too far? Tune it back:
Press CTRL+A (decrease detection range)
```

### Step 6: Test Enemy Chase Speed

Enemies not catching up fast enough?

```
Press CTRL+S (increase enemy chase speed to 3.1)
Keep pressing CTRL+S until enemies feel threatening
Maybe 3.5 or 4.0 is good for your game
```

### Step 7: Test Boss AI

Set up a boss fight difficulty:

```
Press ALT+I (increase difficulty multiplier to 1.1)
Press ALT+I again (now 1.2x)
Press ALT+Q (increase aggression to 0.8)

Boss is now more aggressive and harder
```

### Step 8: Export Your Tuned Values

Happy with all your tweaks? Export them:

```
Press ENTER
→ Console shows all current values as Java code:

    private static final double GRAVITY = -0.75;
    private static final double MAX_JUMP_VELOCITY = -16.0;
    private static final double HORIZONTAL_SPEED = 5.3;
    ... (all values)
```

Copy these values directly into your game code!

### Step 9: Test Another Scenario

Want to start fresh and try different values?

```
Press ESC
→ All values reset to defaults
Try different combinations
```

## Common Tuning Scenarios

### Scenario 1: "Game Feels Too Floaty"

```
SHIFT+W → Increase gravity to -0.8
SHIFT+R → Decrease jump velocity to -13.0
Result: Snappier, more responsive feel
```

### Scenario 2: "Enemy Too Easy"

```
CTRL+W → Increase detection range to 250
CTRL+S → Increase chase speed to 4.0
CTRL+Q → Decrease attack cooldown to 50
Result: Enemies are much more threatening
```

### Scenario 3: "Boss Fight Needs Challenge"

```
ALT+I → Increase difficulty to 1.5x
ALT+Q → Increase aggression to 0.9
ALT+S → Increase special attack frequency to 0.5
Result: Boss is significantly harder
```

### Scenario 4: "Platforming is Frustrating"

```
SHIFT+Q → Increase horizontal speed to 5.5
SHIFT+E → Increase horizontal speed to 6.0 (try it)
SHIFT+Y → Increase run acceleration to 1.0
Result: Better control in jumps and platforming
```

## Keyboard Reference Card

Print this out and keep it handy:

```
ANIMATION STATES:
  Idle:     1  2  3  4
  Move:     Q  W  A  S
  Jump:     E  R  T  Y
  Attack:   Z  X  C  V
  Damage:   U  I  O  P
  Special:  D  F  G  H

CONTROLS:
  Space  = Play/Pause
  Z/X    = Previous/Next Frame
  +/-    = Zoom In/Out
  F      = Flip Horizontal
  B      = Toggle Checkerboard
  ENTER  = Export Constants
  ESC    = Reset All

PHYSICS (SHIFT + Keys):
  W/A = Gravity
  R/T = Jump Height
  Q/E = Speed
  Y/U = Acceleration
  I/O = Air Friction

ENEMY AI (CTRL + Keys):
  W/A = Detection Range
  S/D = Chase Speed
  Q/E = Attack Cooldown
  R/T = Attack Range
  Y/U = Patrol Distance
  I/O = Decision Frequency

BOSS AI (ALT + Keys):
  W/A = Phase Health
  S/D = Special Frequency
  Q/E = Aggression
  R/T = Pattern Complexity
  Y/U = Recovery Speed
  I/O = Difficulty
```

## Tips for Effective Tuning

1. **Change one value at a time** - Only modify one constant, test it, then move to the next
2. **Use extreme values first** - Go very high/low to understand the range
3. **Find the sweet spot** - Once you know the range, find the middle ground
4. **Test all animation states** - Ensure your physics feel good for all character poses
5. **Save your notes** - Write down which values felt best
6. **Press ENTER often** - Copy your best constants frequently
7. **Print the keyboard reference** - Keep it visible while tuning

## Integration into Your Game

Once you've tuned all values:

1. Press ENTER in the tester
2. Copy the Java code from console
3. Paste into your game's character class:

```java
public class Character {
    // Paste the constants here:
    private static final double GRAVITY = -0.75;
    private static final double MAX_JUMP_VELOCITY = -16.0;
    // ... rest of constants
    
    // Use them in your physics update:
    public void updatePhysics() {
        velocity.y += GRAVITY;
        if (isJumping) {
            velocity.y = MAX_JUMP_VELOCITY;
        }
        // etc...
    }
}
```

## Troubleshooting

**"The tester won't launch"**
- Make sure you compiled it: `javac -cp "bin;src" -d bin src/CharacterAnimationPhysicsTester.java`
- Check that Java is installed: `java -version`

**"Constants don't seem to change"**
- Make sure you're holding the modifier key (SHIFT/CTRL/ALT)
- Check the info panels on the right - they should update live

**"I want to see the actual animation"**
- The tester is designed for constants - add sprite rendering separately
- For now, use the state label to verify state switching works

**"How do I know what values are good?"**
- Test with different values and see what feels right
- Start with defaults, then small adjustments
- Compare with similar games you like playing

Happy tuning! 🎮
