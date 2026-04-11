═══════════════════════════════════════════════════════════════════════════════
 AVAILABLE LAUNCHER SCRIPTS & FILES
═══════════════════════════════════════════════════════════════════════════════

Your Game Directory Structure:
├── RUN_GAME_DEBUG.bat          ← FULL FEATURED LAUNCHER (Recommended!)
├── run_game.bat                ← QUICK LAUNCHER (Also good)
├── DEBUG_OUTPUT_GUIDE.md       ← THIS GUIDE - Read this first!
├── src/
│   ├── Game.java               ← Main game with integrated debug output
│   ├── GUIAssetAccessor.java   ← GUI helper (no changes needed)
│   └── [other files]
├── bin/                         ← Compiled .class files (auto-generated)
├── lib/                         ← Game libraries (game2D, animation, etc)
└── Resources/                   ← Game assets (PNG images, etc)

═══════════════════════════════════════════════════════════════════════════════
 SINGLE-CLICK LAUNCHERS (RECOMMENDED)
═══════════════════════════════════════════════════════════════════════════════

1. RUN_GAME_DEBUG.bat
   ✓ EASIEST TO USE - Just double-click!
   ✓ Most detailed debug output
   ✓ Saves debug logs to file
   ✓ Interactive menu after game closes
   ✓ Shows asset verification
   
   WHAT IT DOES:
   1. Checks system and directories
   2. Compiles Game.java automatically
   3. Shows compilation status
   4. Starts game with full debug output
   5. Game runs with every event logged
   6. When game closes: Shows menu with options
      - Restart game
      - View debug log in Notepad
      - Clear debug log
      - Exit
   
   PERFECT FOR:
   • First time running the game
   • Debugging issues
   • Watching detailed game events
   • Understanding game flow

   TO RUN:
   → Double-click RUN_GAME_DEBUG.bat

2. run_game.bat
   ✓ Quick launch (slightly faster)
   ✓ Still has debug output
   ✓ Supports command-line options
   ✓ Simpler interface
   
   USAGE:
   → Double-click run_game.bat (default: compile & run)
   
   ADVANCED USAGE (Command Prompt):
   → run_game.bat clean      = Delete .class files only
   → run_game.bat rebuild    = Full clean rebuild
   → run_game.bat compile    = Just compile (no run)
   → run_game.bat debug      = Extra verbose debugging

═══════════════════════════════════════════════════════════════════════════════
 WHAT EACH LAUNCHER DOES
═══════════════════════════════════════════════════════════════════════════════

RUN_GAME_DEBUG.bat:
┌─────────────────────────────────────────────────────────────┐
│ STARTUP & VALIDATION                                        │
├─────────────────────────────────────────────────────────────┤
│ • Sets console title to "Game Debug Console - N6 Assignment"│
│ • Displays colorful header banner                           │
│ • Verifies all directories exist (src/, bin/, lib/)        │
│ • Checks for essential source files (Game.java)            │
│ • Logs all configuration paths                             │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│ COMPILATION PHASE                                           │
├─────────────────────────────────────────────────────────────┤
│ • Calls: javac -cp ".;../lib/*" Game.java                 │
│ • Shows: [COMPILING] Game.java and dependencies...         │
│ • Result: ✓ Compilation successful!  OR  ✗ FAILED         │
│ • If failed: Lists syntax errors and exits                 │
│ • If success: .class files generated in bin/               │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│ ASSET VERIFICATION                                          │
├─────────────────────────────────────────────────────────────┤
│ • Checks for AnimationAndSpriteLoader                      │
│ • Lists all .jar files in lib/ directory                   │
│ • Confirms asset system is ready                           │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│ GAME EXECUTION                                              │
├─────────────────────────────────────────────────────────────┤
│ • Calls: java -cp "bin;lib\*" Game                        │
│ • Shows: [DEBUG] explanations of what to watch             │
│ • Streams: All console output in real-time                 │
│ • Logs: Everything to game_debug.log file                  │
│ • Duration: Game runs until you close the window           │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│ POST-GAME MENU                                              │
├─────────────────────────────────────────────────────────────┤
│ Choice 1 = Restart game immediately                        │
│ Choice 2 = Open debug log in Notepad                       │
│ Choice 3 = Clear debug log (deletes game_debug.log)       │
│ Choice 4 = Exit script                                     │
└─────────────────────────────────────────────────────────────┘

run_game.bat:
Similar to above but faster, with integrated build management.
Supports legacy command-line arguments for advanced users.

═══════════════════════════════════════════════════════════════════════════════
 STEP-BY-STEP: FIRST TIME SETUP
═══════════════════════════════════════════════════════════════════════════════

STEP 1: Locate the game folder
   • C:\Users\ZAID SIDDIQUI\OneDrive - University.../N6AssignmentCode/handout/

STEP 2: Find RUN_GAME_DEBUG.bat
   • Look for a file called "RUN_GAME_DEBUG.bat"
   • It should show file icon and have .bat extension

STEP 3: Double-click RUN_GAME_DEBUG.bat
   • A black console window appears
   • Header shows: "Game Debug Console - N6 Assignment"
   • It will say "[CHECKING]" and "[COMPILING]"

STEP 4: Wait for "[SUCCESS]" message
   • When you see "✓ Compilation successful!"
   • Game window should appear after a moment

STEP 5: Play the game!
   • Press SPACE to start
   • SPACE = fire bullet (in-game)
   • ESC = return to menu / advance level
   • Watch console for live debug output

STEP 6: After game closes
   • Menu appears in console with options:
   • Answer: 1 = Play again
   •        2 = View debug log
   •        4 = Exit

═══════════════════════════════════════════════════════════════════════════════
 INTERPRETING LAUNCHER OUTPUT
═══════════════════════════════════════════════════════════════════════════════

GOOD STATUS MESSAGES:
─────────────────────────────────────────────────────────────
✓ [SUCCESS]                 = Action completed successfully
✓ Compilation successful!   = Game compiled without errors
✓ All directories accessible = All needed folders found
[INFO]                       = Information message (progress)
[COMMAND]                    = Shows what command is running

BAD STATUS MESSAGES:
─────────────────────────────────────────────────────────────
✗ [ERROR]                   = Something went wrong, see details
✗ COMPILATION FAILED        = Syntax error in source code
✗ not found                 = Missing file or directory
✗ Exit Code: 1              = Program returned error

═══════════════════════════════════════════════════════════════════════════════
 DEBUG LOG FILES
═══════════════════════════════════════════════════════════════════════════════

Location: handout\game_debug.log

Contents:
• Everything printed to console during game
• Timestamped events from game start to exit
• All state changes, collisions, spawns, kills
• Player actions and keyboard input
• VFX effects and damage

VIEWING:
→ Open: Double-click "game_debug.log" (opens in Notepad)
→ Or: Text editor → File → Open → game_debug.log
→ Or: Launcher menu → Choice 2

CLEARING:
→ Launcher menu → Choice 3
→ Or: Delete the file manually

═══════════════════════════════════════════════════════════════════════════════
 TROUBLESHOOTING LAUNCHER ISSUES
═══════════════════════════════════════════════════════════════════════════════

PROBLEM: Batch file doesn't open
SOLUTION:
  • Right-click → "Run as administrator"
  • Or: Drag it to PowerShell window and press Enter
  • Or: Open Command Prompt → type full path to .bat file

PROBLEM: "Compilation FAILED" error
SOLUTION:
  • Check Game.java for syntax errors
  • Look for red underlines in VS Code
  • Common: Missing quotes, bracket mismatches
  • Save Game.java and try again

PROBLEM: Game window doesn't appear after compilation
SOLUTION:
  • Check if game is already running (stop it)
  • Verify game.java isn't locked by another process
  • Try launcher again with "rebuild" option
  • run_game.bat rebuild

PROBLEM: "ClassNotFoundException" error
SOLUTION:
  • Recompile: run_game.bat rebuild
  • Check lib/ folder has all .jar files
  • Verify GUIAssetAccessor.java compiled

PROBLEM: Console freezes or seems stuck
SOLUTION:
  • Game window might be behind other windows
  • Alt+Tab to find the game window
  • Game runs but displays may be hidden
  • Check console still prints debug messages

═══════════════════════════════════════════════════════════════════════════════
 PLATFORM-SPECIFIC NOTES
═══════════════════════════════════════════════════════════════════════════════

WINDOWS (10/11):
  ✓ Both batch files work perfectly
  ✓ Double-click to run
  ✓ Command Prompt integration included

MAC/LINUX:
  ✗ .bat files don't work natively
  ? Use existing run_game.bat converted to .sh script
  ? Or run from command line: javac -cp ".;lib/*" src/Game.java

═══════════════════════════════════════════════════════════════════════════════
 NEXT STEPS
═══════════════════════════════════════════════════════════════════════════════

IMMEDIATE:
→ Run RUN_GAME_DEBUG.bat by double-clicking it
→ Watch the debug output in the console
→ Read DEBUG_OUTPUT_GUIDE.md to understand the messages
→ Play the game and observe event logging

ADVANCED:
→ To disable debug logging:
  • Edit src/Game.java
  • Find: private static final boolean DEBUG_MODE = true;
  • Change to: private static final boolean DEBUG_MODE = false;
  • Recompile and run

→ To add more debug points:
  • Use: debugLog("LABEL", "message");
  • Examples in Game.java show all the patterns

═══════════════════════════════════════════════════════════════════════════════
End of Launcher Guide
═══════════════════════════════════════════════════════════════════════════════
