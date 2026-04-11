# Quick Start - Character Physics Tester

## One Command To Start

Copy and paste this into PowerShell in the `handout` directory:

```powershell
java -cp bin CharacterAnimationPhysicsTester
```

That's it! The interactive GUI will launch.

---

## One Command To Test Physics

```powershell
java -cp bin CharacterPhysicsTestCases
```

You'll see all 16 test results printed to console.

---

## Verify Files Are Ready

Check that these files exist and are compiled:

```powershell
Test-Path bin/CharacterAnimationPhysicsTester.class  # Should be True
Test-Path bin/CharacterPhysicsTestCases.class        # Should be True
Test-Path src/CharacterAnimationPhysicsTester.java   # Should be True
Test-Path src/CharacterPhysicsTestCases.java         # Should be True
```

All should return `True`.

---

## Step-by-Step Verification

### 1. Compile Both Files (if needed)
```powershell
javac -cp "bin;src" -d bin src/CharacterAnimationPhysicsTester.java
javac -cp "bin;src" -d bin src/CharacterPhysicsTestCases.java
```

### 2. Run The Tester GUI
```powershell
java -cp bin CharacterAnimationPhysicsTester
```

**You should see:**
- A window with animation display on the left
- Three info panels on the right (Physics, Enemy AI, Boss AI)
- Instructions panel
- All values displayed in numeric fields

**What to do in the window:**
- Press `1` → See IDLE_NEUTRAL animation
- Press `E` → See JUMP animation  
- Press `SHIFT+W` → Increase Gravity value (watch it change in panel)
- Press `ENTER` → Print all constants to console
- Press `ESC` → Reset all to defaults

### 3. Run The Test Suite
```powershell
java -cp bin CharacterPhysicsTestCases
```

**You should see:**
```
========================================
CHARACTER PHYSICS TEST SUITE
========================================

--- PHYSICS CONSTANT TESTS ---

--- ANIMATION STATE TESTS ---

...

========================================
TEST RESULTS
========================================

✓ PASS | Physics_Running_Speed
       Running position after 60 frames: 300.0 (expected: 300.0)

✓ PASS | Physics_Air_Friction
       Air friction reduces velocity. Final X position: 149.22 (would be 300.0 without friction)

...

========================================
SUMMARY: 12/16 tests passed
========================================
```

---

## If Something Goes Wrong

**Problem: "java: command not found"**
- Java isn't installed or not in PATH
- Install Java 8+

**Problem: "ClassNotFoundException"**
- Files weren't compiled
- Run the compile command above first
- Make sure you're in the `handout` directory

**Problem: "Cannot find symbol"**
- Source file has errors
- Recompile: `javac -cp "bin;src" -d bin src/CharacterAnimationPhysicsTester.java`

**Problem: GUI won't appear**
- GUI apps need a display
- If on headless/remote machine, use test suite instead: `java -cp bin CharacterPhysicsTestCases`

---

## File Structure For Reference

```
handout/
├── src/
│   ├── CharacterAnimationPhysicsTester.java      (700+ lines)
│   ├── CharacterPhysicsTestCases.java            (300+ lines)
│   └── [other files...]
│
├── bin/
│   ├── CharacterAnimationPhysicsTester.class     (compiled)
│   ├── CharacterPhysicsTestCases.class           (compiled)
│   └── [other compiled files...]
│
└── [Documentation files...]
```

---

## What You Can Do Right Now

1. **In the Tester GUI:**
   - View all 24 animation states (press 1-4, Q-H)
   - Adjust gravity, jump velocity, speed, acceleration, friction (SHIFT+keys)
   - Adjust enemy AI parameters (CTRL+keys)
   - Adjust boss AI parameters (ALT+keys)
   - Export all values as Java code (ENTER)
   - Reset to defaults (ESC)

2. **With the Test Suite:**
   - Run automated tests for physics
   - Verify all 24 states are accessible
   - Check enemy AI logic
   - Check boss difficulty scaling
   - Get pass/fail results

3. **Using the Documentation:**
   - Read CHARACTER_TUNING_WORKFLOW.md for complete guide
   - Read HOW_TO_USE_CHARACTER_TESTER.md for quick reference
   - Read CHARACTER_PHYSICS_AI_TESTING_INDEX.md for overview

---

## Next Steps After Verifying It Works

1. **Tune your physics** - Adjust constants until game feels good
2. **Export values** - Press ENTER, copy the constants
3. **Integrate into game** - Paste constants into your actual game code
4. **Test in-game** - Verify it matches the tester
5. **Document your settings** - Write down what worked and why

---

## Support Files

- **CHARACTER_PHYSICS_AI_TESTING_INDEX.md** - Master index and overview
- **CHARACTER_TUNING_WORKFLOW.md** - Detailed 7-phase workflow guide
- **HOW_TO_USE_CHARACTER_TESTER.md** - Quick user guide
- **CHARACTER_ANIMATION_PHYSICS_TESTER_GUIDE.md** - Technical documentation

Open any of these in a text editor or Markdown viewer for detailed help.

---

## Success Checklist

After running the commands above, you should have:

- ✓ Tester GUI window opens and displays
- ✓ Can press keys and see animation states change
- ✓ Can press SHIFT+keys and watch values update in real-time
- ✓ Can press ENTER and see Java constants printed
- ✓ Can press ESC and watch all values reset
- ✓ Test suite runs and shows 12+ tests passing
- ✓ All documentation files are readable

When all of these are true, **you're done!** The system is ready to use.

---

## TL;DR

```powershell
# Run the tester:
java -cp bin CharacterAnimationPhysicsTester

# Run tests:
java -cp bin CharacterPhysicsTestCases

# Read the guide:
notepad CHARACTER_TUNING_WORKFLOW.md
```

Enjoy tuning your game! 🎮
