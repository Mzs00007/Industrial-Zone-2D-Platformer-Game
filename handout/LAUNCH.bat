@echo off
setlocal EnableDelayedExpansion
title Industrial Zone Platformer - Build ^& Run

REM =============================================================================
REM  INDUSTRIAL ZONE PLATFORMER  -  LAUNCH.bat
REM  -------------------------------------------
REM  Double-click this file to:
REM    1. Strip UTF-8 BOM from all .java sources   (VS Code adds it on save)
REM    2. Compile every .java file under src\       (output goes to bin\)
REM    3. Launch Game.java                          (working dir = handout\)
REM
REM  Requirements: Java JDK 11+ on PATH
REM  Run from:     handout\  folder  (or anywhere - script self-locates)
REM =============================================================================

REM -- Move to the directory that contains this BAT file (handout\) -----------
cd /d "%~dp0"
echo [INFO] Working directory: %cd%
echo.

REM ============================================================
REM  STEP 1 - Strip UTF-8 BOM from all Java source files
REM  (VS Code saves UTF-8 with BOM by default; javac rejects it)
REM ============================================================
echo [STEP 1] Stripping UTF-8 BOM from Java sources...
powershell -NoProfile -Command ^
  "Get-ChildItem -Path 'src' -Filter '*.java' -Recurse | ForEach-Object { " ^
  "  $bytes = [System.IO.File]::ReadAllBytes($_.FullName); " ^
  "  if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) { " ^
  "    [System.IO.File]::WriteAllBytes($_.FullName, $bytes[3..($bytes.Length-1)]); " ^
  "    Write-Host ('  BOM removed: ' + $_.Name) " ^
  "  } " ^
  "}"
echo [INFO] BOM strip complete.
echo.

REM ============================================================
REM  STEP 2 - Compile all Java sources -> bin\
REM  -encoding UTF-8  : handle special chars safely
REM  -cp .            : allow imports relative to handout\ root
REM  -sourcepath src  : compiler finds dependent classes in src\
REM  -d bin           : all .class files land in bin\ (mirroring package tree)
REM ============================================================
echo [STEP 2] Compiling Java sources...
if not exist bin mkdir bin

javac -encoding UTF-8 -cp "." -sourcepath "src" -d "bin" "src\Game.java" 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Compilation FAILED - see errors above.
    echo         Fix the errors then double-click LAUNCH.bat again.
    echo.
    pause
    exit /b 1
)
echo [INFO] Compilation successful - .class files written to bin\
echo.

REM ============================================================
REM  STEP 3 - Run the game
REM  -cp "bin;."  : bin\ for compiled classes, . for game2D JAR-less packages
REM  Game         : main class (no package prefix)
REM ============================================================
echo [STEP 3] Launching game...
echo.
java -cp "bin;." Game
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Game exited with error code %ERRORLEVEL%.
    pause
)

endlocal
