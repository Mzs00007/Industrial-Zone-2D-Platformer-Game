@echo off
REM Asset Animation Tester - Run this to validate all game assets

cd /d "C:\Users\ZAID SIDDIQUI\OneDrive - University of Stirling\stir uni\SEMESTERS\sem6 2026\CSCU9N6\N6AssignmentCode\handout"

echo ============================================================
echo Asset Animation Tester - CSCU9N6 Industrial Zone Platformer
echo ============================================================
echo.
echo Starting asset test harness...
echo White background with 60 FPS animation loop
echo Auto-cycles through asset categories every 5 seconds
echo.

java -cp bin AssetTestFrame

pause
