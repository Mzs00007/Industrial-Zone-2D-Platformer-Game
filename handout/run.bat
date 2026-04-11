@echo off
REM ════════════════════════════════════════════════════════════════════════════════════════
REM RUN GAME - INDUSTRIAL ZONE
REM ════════════════════════════════════════════════════════════════════════════════════════

cd /d "%~dp0"

REM Check if bin directory exists
if not exist "bin" (
    echo.
    echo ❌ ERROR: bin directory not found!
    echo Please run build.bat first to compile the game.
    echo.
    pause
    exit /b 1
)

echo ════════════════════════════════════════════════════════════════════════════════════════
echo  INDUSTRIAL ZONE - CYBERPUNK ACTION PLATFORMER
echo ════════════════════════════════════════════════════════════════════════════════════════
echo.
echo Starting game...
echo Command: java -cp bin Game
echo.

java -cp bin Game

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ ERROR: Game failed to start!
    echo ERRORLEVEL: %ERRORLEVEL%
    echo.
    pause
)
