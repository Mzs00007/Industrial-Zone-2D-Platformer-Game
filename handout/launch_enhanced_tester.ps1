# ═════════════════════════════════════════════════════════════════════════════════════════
#        LIVE CHARACTER PHYSICS TESTER v6.0 - PARALLAX BACKGROUND ENHANCED
#        Interactive Physics & Background Layer Tuning System
# ═════════════════════════════════════════════════════════════════════════════════════════

$projectRoot = "C:\Users\ZAID SIDDIQUI\OneDrive - University of Stirling\stir uni\SEMESTERS\sem6 2026\CSCU9N6\N6AssignmentCode\handout"
$srcPath = "$projectRoot\src"
$binPath = "$projectRoot\bin"

Write-Host "════════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "  LIVE CHARACTER PHYSICS TESTER v6.0" -ForegroundColor Cyan
Write-Host "  Parallax Background System Integration" -ForegroundColor Cyan
Write-Host "════════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""

# Verify directories exist
if (-not (Test-Path $srcPath)) {
    Write-Host "[ERROR] Source directory not found: $srcPath" -ForegroundColor Red
    exit 1
}

if (-not (Test-Path $binPath)) {
    Write-Host "[INFO] Creating bin directory..." -ForegroundColor Yellow
    New-Item -ItemType Directory -Path $binPath -Force | Out-Null
}

Write-Host "[1/3] Compiling LiveCharacterPhysicsTesterEnhanced.java..." -ForegroundColor Green

# Compile with detailed error reporting
$compileResult = & javac -cp "$srcPath;$binPath" "$srcPath\LiveCharacterPhysicsTesterEnhanced.java" -d $binPath 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host "[✓] Compilation successful!" -ForegroundColor Green
    Write-Host ""
    Write-Host "[2/3] Verifying compiled class..." -ForegroundColor Green
    
    # Check if class file exists
    $classFile = "$binPath\LiveCharacterPhysicsTesterEnhanced.class"
    if (Test-Path $classFile) {
        $classSize = (Get-Item $classFile).Length
        Write-Host "[✓] Class file created: $classSize bytes" -ForegroundColor Green
    } else {
        Write-Host "[ERROR] Class file not created!" -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "[ERROR] Compilation failed!" -ForegroundColor Red
    Write-Host $compileResult
    exit 1
}

Write-Host ""
Write-Host "[3/3] Launching application..." -ForegroundColor Green
Write-Host ""

# Launch with full classpath
& java -cp "$srcPath;$binPath" LiveCharacterPhysicsTesterEnhanced

if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Runtime error occurred!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "[✓] Application closed normally" -ForegroundColor Green
