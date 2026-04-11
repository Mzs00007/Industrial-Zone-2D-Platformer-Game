# ════════════════════════════════════════════════════════════════════════════════
# CSCU9N6 CODE REFACTORING & CLEANUP SCRIPT (Windows PowerShell)
# Removes duplicate/unused classes to create LEAN game codebase
# EXECUTION: Run this script with: powershell -ExecutionPolicy Bypass -File cleanup.ps1
# ════════════════════════════════════════════════════════════════════════════════

# Set location to src folder
Push-Location "handout\src"
Write-Host "════════════════════════════════════════════════════════════════════════════════"
Write-Host "CSCU9N6 CODE CLEANUP - Removing Duplicate Classes"
Write-Host "════════════════════════════════════════════════════════════════════════════════"
Write-Host ""

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 1: DELETE AI SYSTEM DUPLICATES (Parent has EnemyAIBehavior)
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 1: Deleting AI system duplicates..."
if (Test-Path "ai") {
    Remove-Item -Path "ai" -Recurse -Force
    Write-Host "✓ Deleted ai/ folder - Use AnimationAndSpriteLoader.EnemyAIBehavior"
} else {
    Write-Host "✓ ai/ folder already removed"
}

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 2: DELETE CAMERA SYSTEM DUPLICATES (Parent has ParallaxSystem)
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 2: Deleting camera system duplicates..."
if (Test-Path "camera") {
    Remove-Item -Path "camera" -Recurse -Force
    Write-Host "✓ Deleted camera/ folder - Use AnimationAndSpriteLoader.ParallaxSystem"
} else {
    Write-Host "✓ camera/ folder already removed"
}

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 3: DELETE ENTITY SYSTEM DUPLICATES (Parent has EntityController)
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 3: Deleting entity system duplicates..."
if (Test-Path "core_game_entities") {
    Remove-Item -Path "core_game_entities" -Recurse -Force
    Write-Host "✓ Deleted core_game_entities/ folder"
}
if (Test-Path "entities") {
    Remove-Item -Path "entities" -Recurse -Force
    Write-Host "✓ Deleted entities/ folder"
}
Write-Host "Use parent EntityController instead"

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 4: DELETE GAMEPLAY DUPLICATES
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 4: Deleting gameplay system duplicates..."
if (Test-Path "gameplay") {
    Remove-Item -Path "gameplay" -Recurse -Force
    Write-Host "✓ Deleted gameplay/ folder - Use AnimationAndSpriteLoader.GameStateManager"
} else {
    Write-Host "✓ gameplay/ folder already removed"
}

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 5: DELETE GUI DUPLICATES (Keep Game.java only)
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 5: Cleaning GUI folder (keeping Game.java)..."
if (Test-Path "gui") {
    # Move Game.java to safe location
    Copy-Item -Path "gui\Game.java" -Destination "Game.java.temp" -ErrorAction SilentlyContinue
    
    # Remove entire gui folder
    Remove-Item -Path "gui" -Recurse -Force
    
    # Recreate gui folder with Game.java
    New-Item -ItemType Directory -Path "gui" -Force | Out-Null
    Copy-Item -Path "Game.java.temp" -Destination "gui\Game.java" -Force -ErrorAction SilentlyContinue
    Remove-Item -Path "Game.java.temp" -Force -ErrorAction SilentlyContinue
    
    Write-Host "✓ Cleaned gui/ folder - Kept only Game.java"
} else {
    Write-Host "✓ gui/ folder already cleaned"
}

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 6: DELETE RENDERING DUPLICATES
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 6: Deleting rendering system duplicates..."
if (Test-Path "rendering") {
    Remove-Item -Path "rendering" -Recurse -Force
    Write-Host "✓ Deleted rendering/ folder - Use parent rendering pipeline"
} else {
    Write-Host "✓ rendering/ folder already removed"
}

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 7: DELETE UI DUPLICATES
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 7: Deleting UI system duplicates..."
if (Test-Path "ui") {
    Remove-Item -Path "ui" -Recurse -Force
    Write-Host "✓ Deleted ui/ folder - Use AnimationAndSpriteLoader GUI system"
} else {
    Write-Host "✓ ui/ folder already removed"
}

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 8: DELETE WEAPON DUPLICATES
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 8: Deleting weapon system duplicates..."
if (Test-Path "weapons") {
    Remove-Item -Path "weapons" -Recurse -Force
    Write-Host "✓ Deleted weapons/ folder"
}
Remove-Item -Path "Weapon.java" -Force -ErrorAction SilentlyContinue
Write-Host "✓ Use AnimationAndSpriteLoader.BulletSpawner instead"

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 9: DELETE PHYSICS DUPLICATES
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 9: Deleting physics system duplicates..."
if (Test-Path "physics") {
    Remove-Item -Path "physics" -Recurse -Force
    Write-Host "✓ Deleted physics/ folder - Use parent physics system"
} else {
    Write-Host "✓ physics/ folder already removed"
}

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 10: DELETE COMBAT DUPLICATES
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 10: Deleting combat system duplicates..."
if (Test-Path "combat") {
    Remove-Item -Path "combat" -Recurse -Force
    Write-Host "✓ Deleted combat/ folder - Use parent DamageCalculationSystem"
} else {
    Write-Host "✓ combat/ folder already removed"
}

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 11: DELETE TEST FILES
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 11: Deleting test files..."
$testFiles = @(
    "*Test.java",
    "*Tester.java",
    "CharacterPhysicsTestCases.java",
    "CharacterProfile.java"
)

foreach ($pattern in $testFiles) {
    Get-ChildItem -Path "." -Filter $pattern -Recurse -ErrorAction SilentlyContinue | 
        Remove-Item -Force -ErrorAction SilentlyContinue
}
Write-Host "✓ Deleted all test files"

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 12: DELETE MISC FILES
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 12: Deleting misc files..."
$miscFiles = @(
    "*_Misc.java",
    "StudentUsageExample.java",
    "GameWindow.java"
)

foreach ($pattern in $miscFiles) {
    Get-ChildItem -Path "." -MaxDepth 1 -Filter $pattern -ErrorAction SilentlyContinue | 
        Remove-Item -Force -ErrorAction SilentlyContinue
}
Write-Host "✓ Deleted misc files"

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 13: CLEAN UP TILES AND MAP
# ────────────────────────────────────────────────────────────────────────────────

Write-Host "Phase 13: Cleaning tiles and map folders..."
Get-ChildItem -Path "tiles", "map" -Filter "*Test.java" -Recurse -ErrorAction SilentlyContinue | 
    Remove-Item -Force -ErrorAction SilentlyContinue
Remove-Item -Path "map\IntegratedLevelComparison.java" -Force -ErrorAction SilentlyContinue
Write-Host "✓ Cleaned up tiles/ and map/ folders"

# ────────────────────────────────────────────────────────────────────────────────
# PHASE 14: FINAL VERIFICATION
# ────────────────────────────────────────────────────────────────────────────────

Write-Host ""
Write-Host "════════════════════════════════════════════════════════════════════════════════"
Write-Host "CLEANUP COMPLETE - Remaining Directories:"
Write-Host "════════════════════════════════════════════════════════════════════════════════"

Get-ChildItem -Directory | ForEach-Object { Write-Host "  - $($_.Name)/" }

Write-Host ""
Write-Host "════════════════════════════════════════════════════════════════════════════════"
Write-Host "REMAINING KEY FILES (.java):"
Write-Host "════════════════════════════════════════════════════════════════════════════════"

Get-ChildItem -Filter "*.java" -Depth 0 | ForEach-Object { Write-Host "  - $($_.Name)" }

Write-Host ""
Write-Host "════════════════════════════════════════════════════════════════════════════════"
Write-Host "STATUS: CODE CLEANUP COMPLETE ✓"
Write-Host "════════════════════════════════════════════════════════════════════════════════"
Write-Host ""
Write-Host "NEXT STEPS:"
Write-Host "1. Compile Game.java: javac -sourcepath src -d bin -cp bin src/Game.java"
Write-Host "2. Run game: java -cp bin Game"
Write-Host ""

Pop-Location
