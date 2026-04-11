@echo off
REM ═══════════════════════════════════════════════════════════════════════════════
REM   GAME LAUNCHER WITH LIVE DEBUGGING & STATE MONITORING
REM   Compile and run with detailed console output for all game states
REM ═══════════════════════════════════════════════════════════════════════════════

setlocal enabledelayedexpansion

title Game Debug Console - N6 Assignment

color 0A
cls

REM ═══════════════════════════════════════════════════════════════════════════════
REM   HEADER AND STARTUP INFO
REM ═══════════════════════════════════════════════════════════════════════════════

echo.
echo ███████████████████████████████████████████████████████████████████████████████
echo █                                                                             █
echo █   N6 ASSIGNMENT GAME - DEBUG LAUNCHER v1.0                                 █
echo █   Live State Monitoring + Detailed Event Logging                           █
echo █   [Time: %date% %time%]                                                   █
echo █                                                                             █
echo ███████████████████████████████████████████████████████████████████████████████
echo.

REM Store paths
set SCRIPT_DIR=%~dp0
set SRC_DIR=%SCRIPT_DIR%src
set BIN_DIR=%SCRIPT_DIR%bin
set LIB_DIR=%SCRIPT_DIR%lib
set LOG_FILE=%SCRIPT_DIR%game_debug.log

echo [INFO] Script Directory: %SCRIPT_DIR%
echo [INFO] Source Directory: %SRC_DIR%
echo [INFO] Binary Directory: %BIN_DIR%
echo [INFO] Library Directory: %LIB_DIR%
echo [INFO] Log File: %LOG_FILE%
echo.

REM ═══════════════════════════════════════════════════════════════════════════════
REM   VERIFY DIRECTORIES
REM ═══════════════════════════════════════════════════════════════════════════════

echo [CHECKING] Directory structure...
if not exist "%SRC_DIR%" (
    echo [ERROR] Source directory not found: %SRC_DIR%
    pause
    exit /b 1
)

if not exist "%LIB_DIR%" (
    echo [WARNING] Library directory not found: %LIB_DIR%
    echo [INFO] Creating minimal setup...
)

if not exist "%BIN_DIR%" (
    echo [INFO] Creating binary directory: %BIN_DIR%
    mkdir "%BIN_DIR%" 2>nul
)

echo [SUCCESS] All directories accessible
echo.

REM ═══════════════════════════════════════════════════════════════════════════════
REM   VERIFY SOURCE FILES
REM ═══════════════════════════════════════════════════════════════════════════════

echo [CHECKING] Essential source files...
if not exist "%SRC_DIR%\Game.java" (
    echo [ERROR] Game.java not found in %SRC_DIR%
    pause
    exit /b 1
)

if not exist "%SRC_DIR%\GUIAssetAccessor.java" (
    echo [WARNING] GUIAssetAccessor.java not found
    echo [INFO] Game may run with limited GUI features
)

echo [SUCCESS] Essential files found
echo.

REM ═══════════════════════════════════════════════════════════════════════════════
REM   COMPILATION PHASE
REM ═══════════════════════════════════════════════════════════════════════════════

echo ███████████████████████████████████████████████████████████████████████████████
echo ░                         COMPILATION PHASE                                  ░
echo ███████████████████████████████████████████████████████████████████████████████
echo.

cd /d "%SRC_DIR%"

echo [COMPILING] Game.java and dependencies...
echo [COMMAND] javac -cp ".;%LIB_DIR%\*" -d "%BIN_DIR%" Game.java GUIAssetAccessor.java
echo.

javac -cp ".;%LIB_DIR%\*" -d "%BIN_DIR%" Game.java GUIAssetAccessor.java 2>&1

if errorlevel 1 (
    echo.
    echo [ERROR] ╔════════════════════════════════════════════════════════╗
    echo [ERROR] ║  COMPILATION FAILED                                    ║
    echo [ERROR] ║  Check syntax errors in source files                  ║
    echo [ERROR] ╚════════════════════════════════════════════════════════╝
    echo.
    pause
    exit /b 1
)

cls
echo [SUCCESS] ✓ Compilation successful!
echo [INFO] Binary files generated in: %BIN_DIR%
echo.

REM ═══════════════════════════════════════════════════════════════════════════════
REM   ASSET VERIFICATION
REM ═══════════════════════════════════════════════════════════════════════════════

echo ███████████████████████████████████████████████████████████████████████████████
echo ░                      ASSET AND RESOURCE VERIFICATION                       ░
echo ███████████████████████████████████████████████████████████████████████████████
echo.

if exist "%SRC_DIR%\AnimationAndSpriteLoader.java" (
    echo [INFO] AnimationAndSpriteLoader found - Asset system available
) else (
    echo [WARNING] AnimationAndSpriteLoader not found - Game will load base assets
)

echo [INFO] Verifying library files...
for %%F in ("%LIB_DIR%\*") do (
    echo   ✓ %%~nF
)
echo.

REM ═══════════════════════════════════════════════════════════════════════════════
REM   RUNTIME SETUP
REM ═══════════════════════════════════════════════════════════════════════════════

echo ███████████████████████████████████████████████████████████████████████████████
echo ░                      GAME LAUNCH - DEBUG MODE ENABLED                      ░
echo ███████████████████████████████████████████████████████████████████████████████
echo.
echo [STATUS] Starting game with debug output...
echo [DEBUG] All game states, events, and entities will be logged below
echo [DEBUG] Watch for:
echo   • Game state transitions (MAIN_MENU, PLAYING, LEVEL_COMPLETE, GAME_OVER)
echo   • Entity spawning and destruction
echo   • Collision detection events
echo   • Asset loading confirmation
echo   • HUD state updates
echo   • VFX particle system events
echo [DEBUG] Press ESC or close window to exit
echo.
echo ═══════════════════════════════════════════════════════════════════════════════
echo.

REM ═══════════════════════════════════════════════════════════════════════════════
REM   GAME EXECUTION WITH LOGGING
REM ═══════════════════════════════════════════════════════════════════════════════

cd /d "%SCRIPT_DIR%"

REM Create timestamp in log file
(
    echo.
    echo ════════════════════════════════════════════════════════════════
    echo  GAME SESSION LOG - %date% %time%
    echo ════════════════════════════════════════════════════════════════
    echo.
) >> "%LOG_FILE%"

REM Run game with full debug output to both console and log file
java -cp "%BIN_DIR%;%LIB_DIR%\*" Game 2>&1 | tee -a "%LOG_FILE%"

REM Capture exit code
set GAME_EXIT=%errorlevel%

echo.
echo ═══════════════════════════════════════════════════════════════════════════════
echo.

REM ═══════════════════════════════════════════════════════════════════════════════
REM   POST-EXECUTION SUMMARY
REM ═══════════════════════════════════════════════════════════════════════════════

echo ███████████████████████████████████████████████████████████████████████████████
echo ░                           GAME SESSION ENDED                                ░
echo ███████████████████████████████████████████████████████████████████████████████
echo.

if %GAME_EXIT% equ 0 (
    echo [SUCCESS] ✓ Game exited cleanly (Exit Code: %GAME_EXIT%)
) else (
    echo [WARNING] Game exited with code: %GAME_EXIT%
)

echo [INFO] Debug log saved to: %LOG_FILE%
echo.

REM ═══════════════════════════════════════════════════════════════════════════════
REM   DEBUG LOG SUMMARY
REM ═══════════════════════════════════════════════════════════════════════════════

echo ███████████████████████████████████████████████████████████████████████████████
echo ░                        DEBUG SESSION STATISTICS                             ░
echo ███████████████████████████████████████████████████████████████████████████████
echo.

if exist "%LOG_FILE%" (
    echo [INFO] Recent log entries:
    echo.
    REM Show last 20 lines of log file
    powershell -Command "Get-Content '%LOG_FILE%' -Tail 20"
) else (
    echo [INFO] No log file generated yet
)

echo.
echo.
echo [OPTIONS]
echo   1 = Run game again
echo   2 = Open debug log in Notepad
echo   3 = Clear debug log
echo   4 = Exit
echo.

:menu
set /p choice="Enter choice [1-4]: "

if "%choice%"=="1" (
    cls
    goto :eof
    REM Script will restart
)

if "%choice%"=="2" (
    if exist "%LOG_FILE%" (
        start notepad "%LOG_FILE%"
    ) else (
        echo Debug log not found
    )
    goto menu
)

if "%choice%"=="3" (
    if exist "%LOG_FILE%" (
        del "%LOG_FILE%"
        echo [INFO] Debug log cleared
    )
    goto menu
)

if "%choice%"=="4" (
    echo [INFO] Exiting...
    exit /b 0
)

echo [ERROR] Invalid choice
goto menu

REM ═══════════════════════════════════════════════════════════════════════════════
REM End of script
REM ═══════════════════════════════════════════════════════════════════════════════
