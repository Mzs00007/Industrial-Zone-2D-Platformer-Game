# Build script for N6AssignmentCode - Java project
# Always compiles to bin/ directory, keeping src/ clean
# Run from handout/ directory

param(
    [switch]$Clean = $false,
    [switch]$Run = $false,
    [string]$MainClass = "Game"
)

$ErrorActionPreference = "Continue"

Write-Host "`n========== Java Build System ==========" -ForegroundColor Cyan

# Clean bin/ if requested
if ($Clean) {
    Write-Host "`n[1/3] Cleaning bin directory..." -ForegroundColor Yellow
    if (Test-Path "bin") {
        Remove-Item -Path "bin" -Recurse -Force -ErrorAction SilentlyContinue
        Write-Host "[OK] bin/ cleaned" -ForegroundColor Green
    }
    New-Item -ItemType Directory -Path "bin" -Force | Out-Null
}

# Compile with -d bin (all .class files go to bin/, src/ stays clean)
Write-Host "`n[2/3] Compiling Java files..." -ForegroundColor Yellow
Write-Host "  Source: src/" -ForegroundColor Gray
Write-Host "  Output: bin/" -ForegroundColor Gray
Write-Host "  Command: javac with -cp bin -sourcepath src -d bin" -ForegroundColor Gray

# Use cmd.exe to compile all files at once (handles spaces better)
# PowerShell's glob expansion + cmd.exe external tool works well
$cmd = 'for /R src %f in (*.java) do javac -cp bin -sourcepath src -d bin "%f" 2>&1'
$output = cmd /c $cmd | Tee-Object -Variable compileOutput

# Check if compilation succeeded
$hasErrors = $output | Select-String "error:" -ErrorAction SilentlyContinue

if ($null -eq $hasErrors) {
    Write-Host "`n[OK] Compilation successful" -ForegroundColor Green
    
    # Count .class files
    $classCount = @(Get-ChildItem -Path "bin" -Filter "*.class" -Recurse -ErrorAction SilentlyContinue).Count
    $srcClassCount = @(Get-ChildItem -Path "src" -Filter "*.class" -Recurse -ErrorAction SilentlyContinue).Count
    
    Write-Host "`n[3/3] Build Results:" -ForegroundColor Yellow
    Write-Host "  bin/ directory: $classCount .class files [OK]" -ForegroundColor Green
    if ($srcClassCount -eq 0) {
        Write-Host "  src/ directory: $srcClassCount .class files [OK]" -ForegroundColor Green
    } else {
        Write-Host "  src/ directory: $srcClassCount .class files [WARNING]" -ForegroundColor Yellow
    }
    Write-Host "`n[OK] Build complete" -ForegroundColor Green
    
    # Run if requested
    if ($Run) {
        Write-Host "`n[4/4] Running $MainClass..." -ForegroundColor Yellow
        Write-Host "==========================================" -ForegroundColor Gray
        java -cp bin $MainClass
    }
} else {
    Write-Host "[FAILED] Compilation failed" -ForegroundColor Red
    Write-Host "`nErrors found:" -ForegroundColor Red
    $output | Select-String "error:" | Select-Object -First 20
    exit 1
}

Write-Host "`n==========================================" -ForegroundColor Cyan
Write-Host "  USAGE FROM handout/ DIRECTORY:" -ForegroundColor Cyan
Write-Host "  .\build.ps1                   - Compile" -ForegroundColor White
Write-Host "  .\build.ps1 -Clean            - Clean and compile" -ForegroundColor White
Write-Host "  .\build.ps1 -Run              - Compile and run Game" -ForegroundColor White
Write-Host "  .\build.ps1 -Run -MainClass X - Compile and run class X" -ForegroundColor White
Write-Host "==========================================" -ForegroundColor Cyan
