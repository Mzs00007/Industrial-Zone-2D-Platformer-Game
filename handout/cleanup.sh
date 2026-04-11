#!/bin/bash
# ════════════════════════════════════════════════════════════════════════════════
# CSCU9N6 CODE REFACTORING & CLEANUP SCRIPT
# Removes duplicate/unused classes to create LEAN game codebase
# EXECUTION: Run these commands to clean up directory
# ════════════════════════════════════════════════════════════════════════════════

# PHASE 1: DELETE AI SYSTEM DUPLICATES (Parent has EnemyAIBehavior)
# ────────────────────────────────────────────────────────────────────────────────
cd handout/src

# Remove all AI duplicate classes
rm -rf ai/

echo "✓ Deleted ai/ folder - Use AnimationAndSpriteLoader.EnemyAIBehavior instead"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 2: DELETE CAMERA SYSTEM DUPLICATES (Parent has ParallaxSystem)
# ────────────────────────────────────────────────────────────────────────────────

rm -rf camera/

echo "✓ Deleted camera/ folder - Use AnimationAndSpriteLoader.ParallaxSystem instead"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 3: DELETE ENTITY SYSTEM DUPLICATES (Parent has EntityController)
# ────────────────────────────────────────────────────────────────────────────────

rm -rf core_game_entities/
rm -rf entities/

echo "✓ Deleted core_game_entities/ and entities/ folders - Use parent classes"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 4: DELETE GAMEPLAY DUPLICATES (Parent has GameStateManager)
# ────────────────────────────────────────────────────────────────────────────────

rm -rf gameplay/

echo "✓ Deleted gameplay/ folder - Use AnimationAndSpriteLoader.GameStateManager"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 5: DELETE GUI DUPLICATES (Keep Game.java only)
# ────────────────────────────────────────────────────────────────────────────────

# Move Game.java to temp location
mv gui/Game.java Game.java.temp || true

# Delete entire gui folder
rm -rf gui/

# Restore Game.java
mkdir -p gui/
mv Game.java.temp gui/Game.java || true

echo "✓ Cleaned gui/ folder - Kept only Game.java"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 6: DELETE RENDERING DUPLICATES (Parent has rendering pipeline)
# ────────────────────────────────────────────────────────────────────────────────

rm -rf rendering/

echo "✓ Deleted rendering/ folder - Use parent rendering pipeline"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 7: DELETE UI DUPLICATES (Parent has complete GUI)
# ────────────────────────────────────────────────────────────────────────────────

rm -rf ui/

echo "✓ Deleted ui/ folder - Use AnimationAndSpriteLoader GUI system"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 8: DELETE WEAPON DUPLICATES (Parent has BulletSpawner)
# ────────────────────────────────────────────────────────────────────────────────

rm -f Weapon.java
rm -f weapons/

echo "✓ Deleted weapons/ folder and Weapon.java - Use parent BulletSpawner"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 9: DELETE PHYSICS DUPLICATES (Parent has physics system)
# ────────────────────────────────────────────────────────────────────────────────

rm -rf physics/

echo "✓ Deleted physics/ folder - Use parent physics system"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 10: DELETE COMBAT DUPLICATES (Parent has DamageCalculationSystem)
# ────────────────────────────────────────────────────────────────────────────────

rm -rf combat/

echo "✓ Deleted combat/ folder - Use parent DamageCalculationSystem"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 11: DELETE TEST FILES (Not needed for final build)
# ────────────────────────────────────────────────────────────────────────────────

rm -f *Test.java
rm -f *Tester.java
rm -f CharacterPhysicsTestCases.java
rm -f CharacterProfile.java

echo "✓ Deleted all test files (*Test.java, *Tester.java)"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 12: DELETE MISC FILES (Not needed)
# ────────────────────────────────────────────────────────────────────────────────

rm -f *_Misc.java
rm -f StudentUsageExample.java
rm -f GameWindow.java

echo "✓ Deleted misc files (StudentUsageExample.java, GameWindow.java, etc.)"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 13: CLEAN UP TILE/MAP SYSTEM (Keep Level1, Level2 only if needed)
# ────────────────────────────────────────────────────────────────────────────────

# Remove test files from tiles folder
rm -f tiles/*Test.java

# Remove complexity from map folder
rm -f map/*Test.java
rm -f map/IntegratedLevelComparison.java

echo "✓ Cleaned up tiles/ and map/ folders"

# ────────────────────────────────────────────────────────────────────────────────

# PHASE 14: FINAL VERIFICATION
# ────────────────────────────────────────────────────────────────────────────────

echo ""
echo "════════════════════════════════════════════════════════════════════════════"
echo "CLEANUP COMPLETE - Remaining Directories:"
echo "════════════════════════════════════════════════════════════════════════════"
ls -d */

echo ""
echo "════════════════════════════════════════════════════════════════════════════"
echo "REMAINING KEY FILES:"
echo "════════════════════════════════════════════════════════════════════════════"
find . -maxdepth 1 -name "*.java" | sort

echo ""
echo "════════════════════════════════════════════════════════════════════════════"
echo "STATUS: CODE CLEANUP COMPLETE ✓"
echo "════════════════════════════════════════════════════════════════════════════"
echo ""
echo "NEXT STEPS:"
echo "1. Compile Game.java: javac -sourcepath src -d bin -cp bin src/Game.java"
echo "2. Run game: java -cp bin Game"
echo ""
