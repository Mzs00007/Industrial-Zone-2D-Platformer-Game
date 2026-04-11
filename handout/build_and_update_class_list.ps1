# Build and Update Class Paths List Script
# This script compiles all Java files in batch and automatically updates CLASS_PATHS_LIST.txt

Write-Host "════════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "  BUILDING PROJECT AND UPDATING CLASS PATHS..." -ForegroundColor Yellow
Write-Host "════════════════════════════════════════════════════════════════" -ForegroundColor Cyan

# Get current directory
$scriptDir = Get-Location
Write-Host "Working Directory: $scriptDir`n" -ForegroundColor Green

# Step 1: Compile all Java files
Write-Host "Step 1: Compiling all Java files..." -ForegroundColor Yellow
$javaFiles = Get-ChildItem -Path "src" -Recurse -Include "*.java"
$javaFileCount = @($javaFiles).Count

if ($javaFileCount -eq 0) {
    Write-Host "ERROR: No Java files found in src/ directory!" -ForegroundColor Red
    exit 1
}

Write-Host "Found $javaFileCount Java source files to compile..." -ForegroundColor Green
Write-Host "Compiling..." -ForegroundColor Cyan

# Compile using batch compilation (more reliable on Windows)
$compileOutput = cmd /c "javac -d bin -cp bin @(Get-ChildItem -Path src -Recurse -Include *.java | ForEach-Object {$_.FullName}) 2>&1"
Write-Host $compileOutput | Select-Object -Last 5

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Compilation successful!" -ForegroundColor Green
} else {
    Write-Host "⚠ Compilation completed with warnings/errors (exit code: $LASTEXITCODE)" -ForegroundColor Yellow
}

# Step 2: Generate updated class paths list
Write-Host "`nStep 2: Updating CLASS_PATHS_LIST.txt..." -ForegroundColor Yellow

# Get all .class files, excluding inner classes (those with $)
$classFiles = Get-ChildItem -Path "bin" -Recurse -Include "*.class" | 
    Where-Object { $_.FullName -notmatch '\$' } |
    ForEach-Object { 
        $relativePath = $_.FullName.Substring("$scriptDir\".Length)
        $relativePath
    } | 
    Sort-Object

$classCount = @($classFiles).Count

if ($classCount -eq 0) {
    Write-Host "WARNING: No compiled class files found!" -ForegroundColor Red
} else {
    Write-Host "Found $classCount main compiled classes" -ForegroundColor Green
    
    # Write to file
    $classFiles | Out-File -FilePath "CLASS_PATHS_LIST.txt" -Encoding UTF8 -Force
    Write-Host "✓ CLASS_PATHS_LIST.txt updated with $classCount classes" -ForegroundColor Green
}

# Step 3: Display summary
Write-Host "`n════════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "  BUILD SUMMARY" -ForegroundColor Yellow
Write-Host "════════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "Source Files: $javaFileCount" -ForegroundColor Cyan
Write-Host "Compiled Classes: $classCount" -ForegroundColor Cyan
Write-Host "Output File: CLASS_PATHS_LIST.txt" -ForegroundColor Cyan
Write-Host "`nBuild completed at $(Get-Date)" -ForegroundColor Green
Write-Host "════════════════════════════════════════════════════════════════" -ForegroundColor Cyan
