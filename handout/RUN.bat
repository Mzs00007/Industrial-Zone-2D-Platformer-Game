@echo off
REM =====================================================================
REM  Industrial Zone Platformer  --  Compile + Run (single entry point)
REM
REM    1) Compiles src/Game.java (and everything it transitively needs)
REM       into bin/ using javac -sourcepath so the src/ folder keeps its
REM       package structure intact and no .class files pollute src/.
REM    2) Launches the Game with bin/ on the classpath.
REM =====================================================================
setlocal
cd /d "%~dp0"

echo ============================================================
echo  [1/2] Compiling  (UTF-8)   src/Game.java  -^>  bin/
echo ============================================================
if not exist "bin" mkdir bin

javac -encoding UTF-8 -sourcepath src -d bin -cp bin src\Game.java
set COMPILE_STATUS=%ERRORLEVEL%

if not "%COMPILE_STATUS%"=="0" (
    echo.
    echo *** Compilation FAILED  ^(exit code %COMPILE_STATUS%^) ***
    echo.
    pause
    exit /b %COMPILE_STATUS%
)

echo.
echo    Compilation OK.
echo.
echo ============================================================
echo  [2/2] Launching game      java -cp "bin;." Game
echo ============================================================
java -cp "bin;." Game
endlocal
