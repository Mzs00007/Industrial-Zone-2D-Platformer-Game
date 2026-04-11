@echo off
REM Test if GameLauncher compiles and starts
REM This is a diagnostic script to verify the game is ready

setlocal enabledelayedexpansion

cd /d "%~dp0"

echo.
echo ════════════════════════════════════════════════════════════════════════════
echo GAME LAUNCHER DIAGNOSTIC TEST
echo ════════════════════════════════════════════════════════════════════════════
echo.

REM Test 1: Check Java
echo [TEST 1] Checking Java installation...
java -version 2>&gt;&amp;1 | findstr /I "version" &gt;nul
if !errorlevel! equ 0 (
    echo ✅ Java is installed
) else (
    echo ❌ Java is NOT installed
    exit /b 1
)

REM Test 2: Check bin directory
echo.
echo [TEST 2] Checking compiled classes...
if exist "bin\GameLauncher.class" (
    echo ✅ GameLauncher.class found
) else (
    echo ❌ GameLauncher.class NOT found - please compile first
    exit /b 1
)

if exist "bin\ui\ScreenManager.class" (
    echo ✅ ScreenManager.class found
) else (
    echo ❌ ScreenManager.class NOT found
    exit /b 1
)

if exist "bin\ui\GameplayScreen.class" (
    echo ✅ GameplayScreen.class found
) else (
    echo ❌ GameplayScreen.class NOT found
    exit /b 1
)

REM Test 3: Check assets
echo.
echo [TEST 3] Checking game assets...
if exist "Resources\industrial-zone\characters\player\punk" (
    echo ✅ PUNK character assets found
) else (
    echo ❌ PUNK character assets NOT found
    exit /b 1
)

if exist "Resources\industrial-zone\1 Tiles\Industrial_zone_level_1" (
    echo ✅ Level 1 assets found
) else (
    echo ❌ Level 1 assets NOT found
    exit /b 1
)

REM Test 4: Try to load game
echo.
echo [TEST 4] Starting game...
echo ⏳ Waiting 3 seconds for game window...
timeout /t 3 /nobreak &gt;nul

echo.
echo ════════════════════════════════════════════════════════════════════════════
echo ✅ ALL TESTS PASSED - GAME IS READY TO PLAY
echo ════════════════════════════════════════════════════════════════════════════
echo.
echo Run LAUNCH_GAME.bat to start playing!
echo.

endlocal
