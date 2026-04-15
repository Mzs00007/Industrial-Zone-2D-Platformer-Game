@echo off
setlocal EnableDelayedExpansion
title Industrial Zone Platformer

REM ============================================================
REM  RUN_GAME.bat  —  Industrial Zone 2D Platformer
REM  -------------------------------------------------------
REM  Double-click this file from the  handout\  folder to:
REM
REM    1.  Strip UTF-8 BOM from every .java source file
REM        (VS Code silently adds a BOM on save; javac rejects it)
REM
REM    2.  Compile all Java sources from  src\
REM        Output .class files land in  bin\  (preserving the
REM        package folder structure — src\ stays clean)
REM
REM    3.  Launch the game
REM
REM  Requirements:  Java JDK 11+  on your PATH
REM  Run from:      handout\  (script self-locates via %~dp0)
REM ============================================================

REM -- Always run relative to the directory containing this file
cd /d "%~dp0"
echo.
echo  =====================================================
echo   INDUSTRIAL ZONE PLATFORMER  ^|  Build ^& Run
echo  =====================================================
echo.

REM -------------------------------------------------------
REM  STEP 1  —  Strip UTF-8 BOM from all Java source files
REM -------------------------------------------------------
echo [1/3] Stripping UTF-8 BOM from Java sources...
powershell -NoProfile -Command ^
  "Get-ChildItem -Path 'src' -Filter '*.java' -Recurse | ForEach-Object {" ^
  "  $b = [System.IO.File]::ReadAllBytes($_.FullName);" ^
  "  if ($b.Length -ge 3 -and $b[0] -eq 0xEF -and $b[1] -eq 0xBB -and $b[2] -eq 0xBF) {" ^
  "    [System.IO.File]::WriteAllBytes($_.FullName, $b[3..($b.Length-1)]);" ^
  "    Write-Host ('  BOM stripped: ' + $_.Name)" ^
  "  }" ^
  "}"
echo     Done.
echo.

REM -------------------------------------------------------
REM  STEP 2  —  Compile  src\  →  bin\
REM
REM   -encoding UTF-8   handle special characters safely
REM   -cp .             allow root-level class resolution
REM   -sourcepath src   compiler finds dependencies in src\
REM   -d bin            all .class files go to bin\
REM -------------------------------------------------------
echo [2/3] Compiling...
if not exist bin mkdir bin

javac -encoding UTF-8 -cp "." -sourcepath "src" -d "bin" "src\Game.java" 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo  [ERROR] Compilation failed — see errors above.
    echo  Fix the errors then run RUN_GAME.bat again.
    echo.
    pause
    exit /b 1
)
echo     Compiled successfully.  .class files are in bin\
echo.

REM -------------------------------------------------------
REM  STEP 3  —  Run the game
REM
REM   -cp "bin;."   bin\ for compiled classes,
REM                 .    for game2D / root-level classes
REM   Game          main class (no package prefix)
REM -------------------------------------------------------
echo [3/3] Launching game...
echo  =====================================================
echo.
java -cp "bin;." Game

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo  [ERROR] Game exited with error code %ERRORLEVEL%.
    pause
)

endlocal
