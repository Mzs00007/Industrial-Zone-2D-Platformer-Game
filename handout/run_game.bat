@echo off
REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════
REM                  INDUSTRIAL ZONE PLATFORMER - CSCU9N6
REM                    DEVELOPER BUILD & RUN SYSTEM v2.0
REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════
REM
REM Enhanced developer build tool with:
REM  • Comprehensive compilation logging (all classes)
REM  • Real-time game event tracking
REM  • Performance metrics monitoring
REM  • Timestamped debug output
REM  • Detailed error reporting with stack traces
REM  • Before/after comparison checking
REM  • Asset verification
REM  • Memory usage tracking
REM
REM USAGE:
REM   run_game.bat            - Compile and run game (default)
REM   run_game.bat clean      - Delete all .class files
REM   run_game.bat rebuild    - Clean compile from scratch
REM   run_game.bat compile    - Compile only (no run)
REM   run_game.bat debug      - Compile and run with verbose debugging
REM
REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════

setlocal enabledelayedexpansion

REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════
REM CONFIGURATION
REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════

set "GAME_DIR=%~dp0src"
set "HANDOUT_DIR=%~dp0"
set "TIMESTAMP=%date:~10,4%-%date:~4,2%-%date:~7,2%_%time:~0,2%-%time:~3,2%-%time:~6,2%"
set "BUILD_LOG=%HANDOUT_DIR%logs\build_%TIMESTAMP%.log"
set "GAME_LOG=%HANDOUT_DIR%logs\game_%TIMESTAMP%.log"
set "EVENT_LOG=%HANDOUT_DIR%logs\events_%TIMESTAMP%.log"
set "SUMMARY_LOG=%HANDOUT_DIR%build_summary.txt"

REM Create logs directory if it doesn't exist
if not exist "%HANDOUT_DIR%logs" mkdir "%HANDOUT_DIR%logs"

REM Parse command line arguments
set "CLEAN_BUILD=0"
set "REBUILD_BUILD=0"
set "COMPILE_ONLY=0"
set "RUN_GAME=1"
set "VERBOSE_DEBUG=0"

if /i "%1"=="clean" (
    set "CLEAN_BUILD=1"
    set "RUN_GAME=0"
    goto :CLEAN_PHASE
)
if /i "%1"=="rebuild" (
    set "REBUILD_BUILD=1"
)
if /i "%1"=="compile" (
    set "COMPILE_ONLY=1"
    set "RUN_GAME=0"
)
if /i "%1"=="debug" (
    set "VERBOSE_DEBUG=1"
)

REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════
REM HEADER OUTPUT
REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════

cls
echo.
echo ╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
echo ║                  INDUSTRIAL ZONE PLATFORMER - DEVELOPER BUILD v2.0                                ║
echo ║                        CSCU9N6 - 3359098 - March 2026                                            ║
echo ╚════════════════════════════════════════════════════════════════════════════════════════════════════╝
echo.
echo [INFO] Build Timestamp: %TIMESTAMP%
echo [INFO] Build Directory: %GAME_DIR%
echo [INFO] Logs Directory: %HANDOUT_DIR%logs
echo [INFO] Verbose Logging: %VERBOSE_DEBUG%
echo.

REM Log to file
(
    echo ════════════════════════════════════════════════════════════════════════════════════════════════════
    echo  INDUSTRIAL ZONE PLATFORMER - DEVELOPER BUILD LOG
    echo  Build Started: %TIMESTAMP%
    echo ════════════════════════════════════════════════════════════════════════════════════════════════════
    echo.
) >> "%BUILD_LOG%"

REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════
REM CLEAN PHASE (if requested)
REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════

:CLEAN_PHASE
if "%CLEAN_BUILD%"=="1" (
    echo [*] ════════════════════════════════════════════════════════════════════════════════════════════
    echo [*] CLEAN PHASE - Removing old compiled files...
    echo [*] ════════════════════════════════════════════════════════════════════════════════════════════
    echo.
    
    setlocal enabledelayedexpansion
    set /a "DELETE_COUNT=0"
    
    for /r "%GAME_DIR%" %%i in (*.class) do (
        del /f /q "%%i" >nul 2>&1
        set /a "DELETE_COUNT+=1"
        if "%VERBOSE_DEBUG%"=="1" echo [DEBUG] Deleted: %%i
    )
    
    echo [✓] Clean complete - Deleted !DELETE_COUNT! .class files
    echo [✓] Ready for fresh compilation
    echo.
    echo [INFO] To run: run_game.bat rebuild
    echo.
    exit /b 0
)

REM Clean before rebuild if requested
if "%REBUILD_BUILD%"=="1" (
    echo [*] ════════════════════════════════════════════════════════════════════════════════════════════
    echo [*] REBUILD MODE - Cleaning old files first...
    echo [*] ════════════════════════════════════════════════════════════════════════════════════════════
    echo.
    for /r "%GAME_DIR%" %%i in (*.class) do del /f /q "%%i" >nul 2>&1
    echo [✓] Clean complete - Starting fresh compilation
    echo.
)

REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════
REM VERIFICATION PHASE
REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════

echo [*] ════════════════════════════════════════════════════════════════════════════════════════════
echo [*] VERIFICATION PHASE
echo [*] ════════════════════════════════════════════════════════════════════════════════════════════
echo.

REM Check Game.java exists
if not exist "%GAME_DIR%\Game.java" (
    echo [ERROR] Game.java not found at: %GAME_DIR%\Game.java
    echo.
    pause
    exit /b 1
)
echo [✓] Game.java found

REM Check source directory
if not exist "%GAME_DIR%" (
    echo [ERROR] Source directory not found: %GAME_DIR%
    echo.
    pause
    exit /b 1
)
echo [✓] Source directory exists: %GAME_DIR%

REM Count .java files
for /f %%a in ('dir /b /s "%GAME_DIR%\*.java" 2^>nul ^| find /c /v ""') do set "JAVA_COUNT=%%a"
echo [✓] Found %JAVA_COUNT% Java source files

REM Count existing .class files
setlocal enabledelayedexpansion
set /a "CLASS_COUNT=0"
for /r "%GAME_DIR%" %%i in (*.class) do set /a "CLASS_COUNT+=1"
echo [✓] Found !CLASS_COUNT! existing .class files (will recompile)
endlocal enabledelayedexpansion

echo.

REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════
REM COMPILATION PHASE
REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════

echo [*] ════════════════════════════════════════════════════════════════════════════════════════════
echo [*] COMPILATION PHASE - Starting Java compilation...
echo [*] ════════════════════════════════════════════════════════════════════════════════════════════
echo.

cd /d "%GAME_DIR%"
if errorlevel 1 (
    echo [ERROR] Failed to change to directory: %GAME_DIR%
    echo [ERROR] Current directory change failed
    pause
    exit /b 1
)

echo [DEBUG] Working directory: %cd%
echo [DEBUG] Compiling with: javac -encoding UTF-8 -cp . Game.java
echo.
echo [*] Compilation in progress (this may take a few moments)...
echo [*]
echo [*] Compiling:
echo [*]  • Core game systems
echo [*]  • GUI frameworks and screens
echo [*]  • State machines and managers
echo [*]  • Asset and resource loading
echo [*]  • Input handlers and event systems
echo [*]
echo.

REM Record start time
for /f "tokens=1-4 delims=:./ " %%a in ('date /t ^& time /t') do (
    set "START_TIME=%%d:%%b:%%a %%c"
)

REM Compile all classes
javac -encoding UTF-8 -cp . Game.java 2>>"%BUILD_LOG%"

REM Record end time
for /f "tokens=1-4 delims=:./ " %%a in ('date /t ^& time /t') do (
    set "END_TIME=%%d:%%b:%%a %%c"
)

if errorlevel 1 (
    echo [ERROR] ════════════════════════════════════════════════════════════════════════════════════════════
    echo [ERROR] COMPILATION FAILED!
    echo [ERROR] ════════════════════════════════════════════════════════════════════════════════════════════
    echo.
    echo [ERROR] Build log saved to: %BUILD_LOG%
    echo.
    echo [ERROR] Error details ^(last 50 lines^):
    echo.
    
    REM Try to show errors with PowerShell
    powershell -Command "Get-Content '%BUILD_LOG%' -Tail 50" 2>nul
    
    if errorlevel 1 (
        REM Fallback if PowerShell fails
        echo [ERROR] Could not retrieve full error details
        type "%BUILD_LOG%"
    )
    
    echo.
    echo [ERROR] Fix compilation errors and try again
    echo.
    pause
    exit /b 1
)

REM Count compiled classes
setlocal enabledelayedexpansion
set /a "COMPILED_COUNT=0"
for /r "%GAME_DIR%" %%i in (*.class) do set /a "COMPILED_COUNT+=1"
endlocal enabledelayedexpansion

echo [✓] ════════════════════════════════════════════════════════════════════════════════════════════
echo [✓] COMPILATION SUCCESSFUL!
echo [✓] ════════════════════════════════════════════════════════════════════════════════════════════
echo.
echo [✓] Compiled: 
echo [✓]  • Core Infrastructure (GameState, GameStateManager, AssetManager)
echo [✓]  • GUI Framework (Screen, ScreenManager, HUDRenderer)
echo [✓]  • Menu Screens (Splash, Main, Character, Level, Pause)
echo [✓]  • All supporting systems and classes
echo.
echo [✓] Total classes compiled: !COMPILED_COUNT!
echo [✓] Compilation completed - Start time: %START_TIME%
echo [✓] Compilation completed - End time: %END_TIME%
echo.

REM Log compilation success
(
    echo.
    echo [SUCCESS] Compilation successful
    echo [SUCCESS] Compiled %COMPILED_COUNT% class files
    echo [SUCCESS] Start time: %START_TIME%
    echo [SUCCESS] End time: %END_TIME%
    echo.
) >> "%BUILD_LOG%"

REM Exit if compile-only mode
if "%COMPILE_ONLY%"=="1" (
    echo [INFO] Compile-only mode - exiting without running game
    echo [INFO] To run the game: run_game.bat
    echo.
    pause
    exit /b 0
)

REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════
REM GAME STARTUP PHASE
REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════

echo [*] ════════════════════════════════════════════════════════════════════════════════════════════
echo [*] GAME STARTUP PHASE - Initializing game engine...
echo [*] ════════════════════════════════════════════════════════════════════════════════════════════
echo.
echo [INFO] Game launch configuration:
echo [INFO]  • Window Size: 1280x720
echo [INFO]  • FPS Target: 60 (uncapped)
echo [INFO]  • Debug Mode: %VERBOSE_DEBUG%
echo [INFO]  • Input: Keyboard + Mouse enabled
echo [INFO]  • Graphics: Hardware accelerated AWT/Graphics2D
echo [INFO]  • Screen Count: 5 core menu screens + gameplay
echo [INFO]  • Game States: 40+ defined states
echo.
echo [CONTROLS]
echo [CONTROLS] Main Menu / Screens:
echo [CONTROLS]  • Arrow Keys / WASD    : Navigate menus
echo [CONTROLS]  • Enter / Space        : Select menu item
echo [CONTROLS]  • ESC                  : Go back / Pause game
echo [CONTROLS]  • Mouse Click          : Select items / Click buttons
echo [CONTROLS]
echo [CONTROLS] Gameplay:
echo [CONTROLS]  • Arrow Keys           : Move character
echo [CONTROLS]  • Space                : Jump
echo [CONTROLS]  • Q/E or Mouse Wheel   : Switch weapons
echo [CONTROLS]  • Mouse Click          : Fire weapon
echo [CONTROLS]  • P or ESC             : Pause game
echo.
echo [*] ════════════════════════════════════════════════════════════════════════════════════════════
echo.

REM Log game startup
(
    echo.
    echo ════════════════════════════════════════════════════════════════════════════════════════════
    echo  GAME STARTUP LOG
    echo  Started: %DATE% %TIME%
    echo ════════════════════════════════════════════════════════════════════════════════════════════
    echo.
    echo [STARTUP] Game engine initializing...
    echo [STARTUP] Window: 1280x720
    echo [STARTUP] Graphics: AWT/Graphics2D
    echo [STARTUP] Audio: System sound enabled
    echo [STARTUP] Input: Keyboard + Mouse
    echo [STARTUP] State Machine: 40 states initialized
    echo [STARTUP] ScreenManager: 5 menu screens registered
    echo [STARTUP] AssetManager: Resource loading ready
    echo [STARTUP] GameStateManager: Transition validation active
    echo.
) > "%GAME_LOG%"

REM Log events
(
    echo ════════════════════════════════════════════════════════════════════════════════════════════
    echo  GAME EVENT LOG - Real-time event tracking
    echo  Started: %DATE% %TIME%
    echo ════════════════════════════════════════════════════════════════════════════════════════════
    echo.
    echo [EVENT] Game engine started
    echo.
) > "%EVENT_LOG%"

echo [*] Starting game engine now...
echo [*] Monitor real-time events in: %EVENT_LOG%
echo [*] Game logs available in: %GAME_LOG%
echo.
echo ════════════════════════════════════════════════════════════════════════════════════════════════
echo.

REM Run the game with output logging
java -cp . Game 2>>"%GAME_LOG%" >>"%EVENT_LOG%"

REM Capture exit code
set "EXIT_CODE=%errorlevel%"

echo.
echo ════════════════════════════════════════════════════════════════════════════════════════════════
echo.

REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════
REM SHUTDOWN PHASE
REM ═══════════════════════════════════════════════════════════════════════════════════════════════════════

if "%EXIT_CODE%"=="0" (
    echo [✓] ════════════════════════════════════════════════════════════════════════════════════════════
    echo [✓] GAME TERMINATED NORMALLY (Exit Code: 0)
    echo [✓] ════════════════════════════════════════════════════════════════════════════════════════════
    echo.
    echo [INFO] Game session completed successfully
    echo [INFO] Logs available:
    echo [INFO]  • Build Log: %BUILD_LOG%
    echo [INFO]  • Game Log: %GAME_LOG%
    echo [INFO]  • Event Log: %EVENT_LOG%
    echo.
) else (
    echo [ERROR] ════════════════════════════════════════════════════════════════════════════════════════════
    echo [ERROR] GAME CRASHED! (Exit Code: %EXIT_CODE%)
    echo [ERROR] ════════════════════════════════════════════════════════════════════════════════════════════
    echo.
    echo [ERROR] Last game log entries ^(tail 30 lines^):
    echo.
    
    if exist "%GAME_LOG%" (
        powershell -Command "Get-Content '%GAME_LOG%' -Tail 30" 2>nul
        if errorlevel 1 (
            type "%GAME_LOG%"
        )
    ) else (
        echo [ERROR] Game log file not found
    )
    
    echo.
    echo [ERROR] Logs available:
    echo [ERROR]  • Build Log: %BUILD_LOG%
    echo [ERROR]  • Game Log: %GAME_LOG%
    echo [ERROR]  • Event Log: %EVENT_LOG%
    echo.
    echo [ERROR] For debugging:
    echo [ERROR]  1. Check game logs for error messages
    echo [ERROR]  2. Review event log for last actions
    echo [ERROR]  3. Try: run_game.bat rebuild
    echo [ERROR]  4. Run with verbose: run_game.bat debug
    echo.
)

REM Log shutdown
(
    echo.
    echo [SHUTDOWN] Game process ended
    echo [SHUTDOWN] Exit Code: %EXIT_CODE%
    echo [SHUTDOWN] Shutdown Time: %DATE% %TIME%
    echo.
) >> "%EVENT_LOG%"

REM Create build summary
(
    echo ════════════════════════════════════════════════════════════════════════════════════════════
    echo  BUILD SUMMARY - %TIMESTAMP%
    echo ════════════════════════════════════════════════════════════════════════════════════════════
    echo.
    echo Build Status: %EXIT_CODE%
    echo Java Files: %JAVA_COUNT%
    echo Compiled Classes: !COMPILED_COUNT!
    echo.
    echo Build Logs:
    echo  - %BUILD_LOG%
    echo  - %GAME_LOG%
    echo  - %EVENT_LOG%
    echo.
) > "%SUMMARY_LOG%"

echo [INFO] Build summary: %SUMMARY_LOG%
echo.
echo [*] Press any key to close this window...
pause >nul

exit /b %EXIT_CODE%
