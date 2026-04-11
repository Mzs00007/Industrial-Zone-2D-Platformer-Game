# CharacterAnimationPhysicsTester v4.0 Launcher (PowerShell)
# Comprehensive Physics & Parallax Background Tuner

Write-Host "╔════════════════════════════════════════════════════════════════╗"
Write-Host "║  🎮 Character Animation Physics & Parallax Tester v4.0        ║"
Write-Host "║     Comprehensive Game Tuning Suite                           ║"
Write-Host "╚════════════════════════════════════════════════════════════════╝"
Write-Host ""

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Push-Location $scriptDir

Write-Host "Compiling..."
javac -cp src src/CharacterAnimationPhysicsTester.java -d bin
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Compilation failed!"
    Pop-Location
    exit 1
}

Write-Host "✓ Compilation successful!"
Write-Host ""
Write-Host "Launching tester..."
Write-Host ""

java -cp "src;bin" CharacterAnimationPhysicsTester

Pop-Location
