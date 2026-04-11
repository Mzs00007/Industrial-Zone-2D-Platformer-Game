# Character Physics Tuning Workflow - Step by Step

This document walks you through an actual tuning session for character physics and AI constants.

## Phase 1: Launch and Baseline

### Step 1.1: Start the Tester
```bash
cd handout
java -cp bin CharacterAnimationPhysicsTester
```

You should see:
- A window with the character animations on the left
- 3 info panels on the right: Physics, Enemy AI, Boss AI
- Instructions panel on top right

**Default values visible:**
- GRAVITY: -0.6
- MAX_JUMP_VELOCITY: -15.0
- HORIZONTAL_SPEED: 5.0
- ENEMY_DETECTION_RANGE: 200
- BOSS_DIFFICULTY_MULTIPLIER: 1.0
- And 12 more constants

### Step 1.2: Test Each Animation State
- Press 1 → See IDLE_NEUTRAL
- Press 2 → See IDLE_BORED  
- Press Q → See WALK_LEFT
- Press W → See WALK_RIGHT
- Press E → See JUMP_ASCEND
- Press Z → See ATTACK_LIGHT

Confirm all 24 states are accessible with E, R, T, Y, Z, X, C, V, U, I, O, P keys.

---

## Phase 2: Tune Physics for Core Feel

### Step 2.1: Assess Current Jump Feel

Press E (jump animation) repeatedly, looking at:
- How high does it go?
- How long is it in the air?
- Does it feel archery or floaty?

**If jump feels TOO FLOATY:**
```
Press SHIFT+W 3 times → Increase gravity to -0.9
Press SHIFT+R 2 times → Decrease jump velocity to -13.0
Jump now → Much snappier!
```

**If jump feels TOO HEAVY:**
```
Press SHIFT+A 2 times → Decrease gravity back toward -0.4
Press SHIFT+T 1 time  → Increase jump velocity to -16.0
Jump now → More floaty, more "air time"
```

### Step 2.2: Running Speed Feels Right?

Press W (walk right animation):

**If character moves too slow:**
```
Press SHIFT+Q 2 times → Increase horizontal speed to 5.5
Run now → Noticeably faster!
```

**If character moves too fast:**
```
Press SHIFT+A 1 time → Decrease horizontal speed to 4.5
Run now → More controlled
```

### Step 2.3: Acceleration Ramp

Press and hold right arrow (conceptually - this is animation test):

**If character feels sluggish to start moving:**
```
Press SHIFT+Y 2 times → Increase run acceleration to 1.0
Character now reaches full speed faster
```

**If character accelerates too quickly:**
```
Press SHIFT+U 1 time → Decrease acceleration to 0.5
More gradual speed increase
```

### Step 2.4: Air Friction (Mid-Air Control)

Imagine pressing W, jumping (E), still pressing W in mid-air:

**If character slides too far in air:**
```
Press SHIFT+I 1 time → Increase air friction to 0.98
Less slide, more control
```

**If character loses horizontal momentum too fast:**
```
Press SHIFT+O 1 time → Decrease air friction to 0.92
More momentum carries through air
```

---

## Phase 3: Tune Enemy AI

### Step 3.1: Enemy Detection Distance

Where should enemies "wake up"?

**If you want enemies to see player from far away:**
```
Press CTRL+W 5 times → Increase detection range to 250
Enemies now react from off-screen
```

**If you want closer encounters:**
```
Press CTRL+A 3 times → Decrease detection range to 140
Enemies only react when player is nearby
```

**Suggested value:** 200 feels balanced. Not too surprising, not too forgiving.

### Step 3.2: Chase Speed (How Fast Enemies Catch You)

**If enemies are too slow to catch:**
```
Press CTRL+S 3 times → Increase chase speed to 3.9
Enemies now very threatening, hard to escape
```

**If enemies are overpowered:**
```
Press CTRL+D 2 times → Decrease chase speed to 2.5
More kiting opportunity, player can run away
```

**Suggested value:** 3.0-3.5 is a good sweet spot for medium difficulty.

### Step 3.3: Attack Cooldown (How Often They Attack)

Shorter cooldown = more attacks:

**If enemies don't punish mistakes enough:**
```
Press CTRL+Q 2 times → Decrease cooldown to 40
Enemies attack every 40 frames (gets scary fast)
```

**If you're taking too much damage:**
```
Press CTRL+E 3 times → Increase cooldown to 90
Enemies attack less frequently, more forgiving
```

**Suggested value:** 60 frames = 1 attack per second at 60 FPS. Balanced.

### Step 3.4: Attack Range (How Far They Can Hit)

**If player feels safe at distance:**
```
Press CTRL+R 3 times → Increase attack range to 80
Now they hit from further away
```

**If you want melee-only AI:**
```
Press CTRL+T 2 times → Decrease attack range to 30
Enemies must get very close to land hits
```

---

## Phase 4: Tune Boss AI

### Step 4.1: Difficulty Multiplier (Master Scale)

This is the main knob for boss challenge:

**For easy mode:**
```
Press ALT+A 3 times → Decrease to 0.7x
Boss is 30% weaker, good for learning attack patterns
```

**For normal mode:**
```
ALT+I and ALT+A to get to → 1.0x
Boss at intended difficulty
```

**For hard mode:**
```
Press ALT+I 3 times → Increase to 1.5x
Boss is 50% harder, needs mastery
```

### Step 4.2: Boss Aggression (Attack Frequency)

How often does boss attack?

**For aggressive boss:**
```
Press ALT+Q 2 times → Increase aggression to 0.9
Boss attacks nearly every turn
```

**For predictable boss:**
```
Press ALT+E 1 time → Decrease aggression to 0.4
Boss has long pauses between attacks, easier to respond
```

### Step 4.3: Pattern Complexity (Behavior Variety)

How unpredictable is the boss?

**For chaotic boss:**
```
Press ALT+R 3 times → Increase complexity to 1.0
Boss uses all attack types randomly
```

**For pattern-learnable boss:**
```
Press ALT+T 2 times → Decrease complexity to 0.5
Boss has predictable pattern, player can learn it
```

---

## Phase 5: Integration Test

### Step 5.1: Test Jump + Enemy Chase Together

In your head, imagine:
1. Player jump height looks good (from Step 2.1)
2. Enemy can detect player (from Step 3.1) 
3. Enemy chases at good speed (from Step 3.2)

**Perfect scenario:** Player can jump over enemy, land, escape because they can outrun OR enemy catches up for exciting chase.

**If something's wrong:**
- Jump too short? → Go back to Step 2.1
- Enemy too slow? → Go back to Step 3.2
- Enemy sees through walls? → Decrease detection range at Step 3.1

### Step 5.2: Test Physics + Boss Together

The boss difficulty multiplier scales all boss stats. If you:
1. Tuned gravity/jump for great platforming feel (Step 2)
2. Set boss difficulty to 1.5x (Step 4.1)

Boss attacks should be 50% harder - your platforming feels good but boss is truly dangerous. Perfect balance!

---

## Phase 6: Save Your Settings

### Step 6.1: Export Your Tuned Constants

When you like how everything feels:

**Press ENTER**

You'll see in the console:
```
=== CURRENT CONSTANTS ===
private static final double GRAVITY = -0.72;
private static final double MAX_JUMP_VELOCITY = -14.5;
private static final double HORIZONTAL_SPEED = 5.3;
... (all 18 constants)
```

### Step 6.2: Copy to Your Game Code

Open your character physics class:
```java
public class CharacterPhysics {
    // PASTE YOUR VALUES HERE:
    private static final double GRAVITY = -0.72;
    private static final double MAX_JUMP_VELOCITY = -14.5;
    private static final double HORIZONTAL_SPEED = 5.3;
    // ... rest
}
```

### Step 6.3: Verify In-Game

Run your actual game and confirm:
- Jump height matches what you tuned
- Run speed feels familiar
- Enemies behave as expected
- Boss is appropriately challenging

---

## Phase 7: Iteration (Make It Perfect)

### Step 7.1: Identify What's Wrong

If in-game doesn't feel like the tester:

**"Jump height is different"**
- Might have gravity applied elsewhere in your code
- Check `updatePhysics()` method - is GRAVITY applied twice?

**"Character accelerates differently"**
- Your code might have different acceleration implementation
- Use ENTER to verify your constants exactly match game code

**"Enemy movement seems off"**
- Your AI code might have different chase logic
- Adjust ENEMY_CHASE_SPEED until it feels like the tester

### Step 7.2: Fine-Tune Details

Once game roughly matches tester, make small tweaks:

```
Press ESC → Reset all to defaults
Then slowly adjust just the values that need tweaking
```

For example, if physics are perfect but enemies still suck:

```
CTRL+S → Adjust chase speed only
ALT+I → Adjust boss difficulty only
Press ENTER → Copy just those constants to game
Run game → Test
```

### Step 7.3: Document Your Final Values

Create a note:

```
FINAL TUNED VALUES - Version 1.0

Physics:
- Gravity: -0.72 (felt snappy, good platforming)
- Jump Height: -14.5 (good air time, lands on standard platforms)
- Run Speed: 5.3 (fast enough, not overpowered)

Enemy AI:
- Detection: 200 (fair surprise, not unfair)
- Chase Speed: 3.2 (matches player skill level)
- Attack Frequency: 60 frames (one per second, manageable)

Boss:
- Difficulty: 1.2x (challenging but fair)
- Aggression: 0.7 (not spammy)
- Complexity: 0.8 (interesting patterns)
```

Keep this for future reference!

---

## Quick Reference: Keyboard Layout

```
ANIMATION:        PHYSICS (SHIFT+):     ENEMY AI (CTRL+):   BOSS AI (ALT+):
1,2,3,4           W/A Gravity           W/A Range           W/A Health Phase
Q,W,A,S           R/T Jump Height       S/D Speed           S/D Special Freq
E,R,T,Y           Q/E Horiz Speed       Q/E Cooldown        Q/E Aggression
Z,X,C,V           Y/U Acceleration      R/T Range           R/T Complexity
U,I,O,P           I/O Air Friction      Y/U Patrol          Y/U Recovery
D,F,G,H           

CONTROL KEYS:
Space = Play/Pause animation
Z/X = Previous/Next Frame
+/- = Zoom In/Out
F = Flip Character
B = Toggle Checkerboard
ENTER = Export All Constants
ESC = Reset All to Defaults
```

---

## Troubleshooting

### "I exported constants but game doesn't match tester"

1. Verify you copied ALL constants, not just some
2. Check your game applies constants the same way tester does
3. Make sure no hardcoded values override your constants

### "Jumping in tester feels good but different in game"

Your game likely has collision handling that affects jump. Adjust gravity until game matches, not tester.

### "I messed up constants, how do I get defaults back?"

Press ESC in tester - all reset to original values. Then press ENTER to see what defaults are.

### "How do I know if my tuning is good?"

When the game feels like:
- ✓ Platforming is possible but challenging
- ✓ Enemies feel fair, not cheap
- ✓ Boss fights are exciting, not frustrating
- ✓ Player skill matters (better play = better results)

Then you're done!

---

## Final Tips

1. **Tune in isolation** - Fix one thing at a time
2. **Play frequently** - Switch between tester and game often
3. **Small adjustments** - Don't jump by huge numbers
4. **Trust your gut** - If it feels good, it's good
5. **Document changes** - Write down what you changed and why
6. **Test edge cases** - Try all character states with all AI levels
7. **Get feedback** - Have someone else play and report how it feels

Happy tuning! 🎮
