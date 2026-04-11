@echo off
REM ════════════════════════════════════════════════════════════════════════════
REM INDUSTRIAL ZONE - Game Launcher Script
REM Cyberpunk Action Defense Game
REM April 6, 2026
REM ════════════════════════════════════════════════════════════════════════════

setlocal enabledelayedexpansion

REM Navigate to handout directory
cd /d "%~dp0"

echo.
echo ════════════════════════════════════════════════════════════════════════════
echo INDUSTRIAL ZONE - CYBERPUNK ACTION DEFENSE
echo ════════════════════════════════════════════════════════════════════════════
echo.
echo Starting game...
echo.

REM Check if Java is installed
java -version >nul 2>&amp;1
if !errorlevel! neq 0 (
    echo ❌ ERROR: Java is not installed or not in PATH
    echo Please install Java 11+ and add it to your system PATH
    pause
    exit /b 1
)

REM Check if bin directory exists
if not exist "bin" (
    echo ❌ ERROR: bin directory not found
    echo Please compile the project first using: javac -cp "lib/*;" src/**/*.java
    pause
    exit /b 1
)

REM Run the game
echo [Launching GameLauncher...]
java -cp "lib/*;bin" GameLauncher

if !errorlevel! neq 0 (
    echo.
    echo ❌ Game exited with error code: !errorlevel!
    echo Check the console output above for details
    pause
)

endlocal
