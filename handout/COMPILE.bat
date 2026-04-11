@echo off
REM ════════════════════════════════════════════════════════════════
REM INDUSTRIAL ZONE - BUILD SCRIPT
REM Compiles Game.java to bytecode
REM ════════════════════════════════════════════════════════════════

echo [BUILD] Compiling Industrial Zone...
echo.

REM Clean old build
if exist bin\Game.class (
    echo [CLEAN] Removing old Game.class...
    del bin\Game.class
)

REM Compile
echo [JAVAC] Compiling src\Game.java...
javac -d bin src/Game.java

REM Check result
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ════════════════════════════════════════════════════════════
    echo [SUCCESS] Build complete!
    echo ════════════════════════════════════════════════════════════
    echo.
    echo Compiled bytecode: bin\Game.class
    echo Ready to run: RUN.bat
    echo.
) else (
    echo.
    echo ════════════════════════════════════════════════════════════
    echo [ERROR] Compilation failed!
    echo ════════════════════════════════════════════════════════════
    echo.
    pause
    exit /b 1
)

pause
