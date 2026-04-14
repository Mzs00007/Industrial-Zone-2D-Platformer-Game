@echo off
REM Industrial Zone 2D Platformer - Game Launcher
REM CSCU9N6 Assignment - 2 Years Development

chcp 65001 >nul
echo.
echo ╔════════════════════════════════════════════════════════════════╗
echo ║         INDUSTRIAL ZONE 2D PLATFORMER - DEMO BUILD            ║
echo ║                    CSCU9N6 Assignment                          ║
echo ║                  © 2024-2026 Development Team                 ║
echo ╚════════════════════════════════════════════════════════════════╝
echo.
echo Compiling game...
javac -d bin -cp bin "src/Game.java" "src/game2D/GameCore.java" 2>&1
if %errorlevel% neq 0 (
    echo Compilation attempted. Running with existing bytecode...
)
echo.
echo Starting game...
echo.
echo Controls:
echo   • ARROWS = Move left/right
echo   • SPACE  = Jump  
echo   • CTRL   = Attack
echo   • ESC    = Pause
echo.
echo Loading assets from Resources/industrial-zone/
echo.

java -cp bin Game

echo Game ended.
pause
