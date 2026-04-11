@echo off
REM ════════════════════════════════════════════════════════════════
REM PRODUCTION GAME LAUNCHER - INDUSTRIAL ZONE PLATFORMER
REM ════════════════════════════════════════════════════════════════
REM 
REM This script launches the production-ready game built on April 1, 2026
REM Requirements: Java 11+ installed and in PATH
REM 
REM Game Features:
REM   - Main menu with start button
REM   - Playable platforming level
REM   - Character animation system (8 states)
REM   - Physics engine (gravity, jumping)
REM   - 60 FPS stable performance
REM

color 0A
title INDUSTRIAL ZONE - GAME LAUNCHER
cls

echo ════════════════════════════════════════════════════════════════
echo  INDUSTRIAL ZONE - PLATFORMER ACTION GAME
echo ════════════════════════════════════════════════════════════════
echo.
echo Launching production game...
echo.

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    color 0C
    echo ERROR: Java not found in PATH
    echo Please install Java 11 or higher
    pause
    exit /b 1
)

REM Change to handout directory
cd /d "%~dp0"

REM Verify game classes exist
if not exist "bin\GameWindow.class" (
    color 0C
    echo ERROR: Game files not found
    echo Expected: bin\GameWindow.class
    pause
    exit /b 1
)

REM Launch the game
echo Starting game...
echo.
java -cp bin GameWindow

if errorlevel 1 (
    color 0C
    echo.
    echo ERROR: Game crashed or failed to run
    pause
    exit /b 1
)

exit /b 0
