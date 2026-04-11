@echo off
REM ════════════════════════════════════════════════════════════════════════════════════════
REM INDUSTRIAL ZONE - BUILD SCRIPT
REM ════════════════════════════════════════════════════════════════════════════════════════

cd /d "%~dp0"
setlocal enabledelayedexpansion

set "SRCDIR=src"
set "BINDIR=bin"
set "LIBDIR=lib"

REM Create bin directory if it doesn't exist
if not exist "%BINDIR%" mkdir "%BINDIR%"

echo ════════════════════════════════════════════════════════════════════════════════════════
echo  INDUSTRIAL ZONE - CYBERPUNK ACTION (Build v1.0)
echo ════════════════════════════════════════════════════════════════════════════════════════
echo.

REM Count total Java files to compile
echo Scanning for Java source files...
setlocal enabledelayedexpansion
set "COUNT=0"
for /r "%SRCDIR%" %%F in (*.java) do (
    set /a COUNT+=1
)
echo Found !COUNT! Java files to compile.
echo.

REM Compile all Java files recursively
echo Compiling all Java source files...
echo Command: javac -cp "%LIBDIR%\*" -d "%BINDIR%" (recursive)
echo.

javac -cp "%LIBDIR%\*" -d "%BINDIR%" "%SRCDIR%\**\*.java" 2>&1

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ════════════════════════════════════════════════════════════════════════════════════════
    echo ✅ COMPILATION SUCCESSFUL!
    echo ════════════════════════════════════════════════════════════════════════════════════════
    echo.
    echo To run the game:
    echo   java -cp "lib/*;bin" Game
    echo.
    echo To run a specific test:
    echo   java -cp "lib/*;bin" test.CyberpunkStoryTester
    echo.
) else (
    echo.
    echo ════════════════════════════════════════════════════════════════════════════════════════
    echo ❌ COMPILATION FAILED!
    echo ════════════════════════════════════════════════════════════════════════════════════════
    echo.
    echo Check the errors above and fix them.
    pause
)

endlocal
