═══════════════════════════════════════════════════════════════════════════════
 GAME DEBUG & LAUNCHER GUIDE - How to Run and Monitor the Game
═══════════════════════════════════════════════════════════════════════════════

## QUICK START

You have TWO batch files available to run the game with debug output:

### Option 1: FULL DEBUG LAUNCHER (Recommended for Detailed Monitoring)
   File: RUN_GAME_DEBUG.bat
   
   Features:
   • Comprehensive state monitoring every second
   • Detailed event logging with timestamps
   • Asset verification
   • Debug log file output (game_debug.log)
   • Post-game statistics and options menu
   • Best for: In-depth debugging and understanding game flow

   How to use:
   1. Double-click RUN_GAME_DEBUG.bat
   2. Game will compile automatically
   3. Watch the console for live debug output
   4. Game window opens with full debugging enabled
   5. After exiting, choose options (restart, view logs, etc.)

### Option 2: QUICK RUN (Fastest Launch)
   File: run_game.bat
   
   Usage:
   • run_game.bat          - Compile and run (default)
   • run_game.bat clean    - Delete all .class files
   • run_game.bat rebuild  - Full clean rebuild
   • run_game.bat compile  - Compile only, don't run
   • run_game.bat debug    - Extra verbose debugging mode

   How to use:
   1. Double-click run_game.bat
   2. Game compiles and runs automatically
   3. Faster startup than full debug launcher

═══════════════════════════════════════════════════════════════════════════════
 UNDERSTANDING THE DEBUG OUTPUT
═══════════════════════════════════════════════════════════════════════════════

The game prints timestamped events showing everything happening in real-time:

TIMESTAMP AND EVENT TYPES:

[000.000] - Milliseconds elapsed since game start
[001.250] - After 1.250 seconds

EVENT CATEGORIES (shown after timestamp):

◆STATE     - Game state snapshot (every 1 second)
            Format: LEVEL=1 STATE=PLAYING HP=100/100 AMMO=30/100 ENERGY=80/100
            Shows: Current level, game state, player health/ammo/energy

◆ENTITIES  - Entity count summary (every 1 second)
            Format: Enemies=3 Bullets=5 VFX=2 EnemiesDefeated=2/5
            Shows: How many active enemies, bullets, effects, progress

•IDLE      - Waiting state (main menu or level select)

•WAIT      - Waiting for input (victory/game over screen)

★VICTORY   - Win condition achieved
            Format: Level 1 completed! Total Score: 1250
            Shows: Successful level completion and score

★DEFEAT    - Loss condition (player health depleted)
            Format: Health depleted at Level 1. Final Score: 850
            Shows: How player died and final score

★SPAWN    - Enemy spawned
            Format: Enemy #3 spawned at (1280, 450) HP=65 [Total: 3]
            Shows: Enemy number, position, health, total active

◆REMOVE   - Enemy left screen
            Format: Enemy left screen [Remaining: 2]
            Shows: How many enemies still active

⟳FIRE     - Bullet fired
            Format: Bullet fired! [Ammo: 29/100] [Score +5]
            Shows: Ammo remaining, score bonus for firing

⚡HIT     - Bullet hit enemy
            Format: Bullet struck Enemy! DMG=25 HP=15/40
            Shows: Damage dealt, enemy's remaining health

★KILL     - Enemy defeated
            Format: Enemy defeated! Score +100 [Total: 3/5 completed]
            Shows: Level progress (3 out of 5 enemies killed)

◆REMOVE   - Bullet removed (off-screen)
            Format: Bullet off-screen [Remaining: 4]
            Shows: How many bullets still active

💥DMG!    - Player took damage from enemy collision
            Format: Enemy collision! Damage=-10 HP=90/100
            Shows: Damage received and current health

✨VFX     - Visual effect spawned
            Format: Spawned EXPLOSION at (640, 360) [Total: 1 active]
            Shows: Effect type, location, number of active effects

⟹STATE   - Game state transition
            Format: GAME STARTED - Level 1 | HP=100 AMMO=30
            Shows: State change and initial player stats

⟹STATE   - Advancing to next level
            Format: ADVANCING TO LEVEL 2
            Shows: Level transition in progress

⟹STATE   - Returning to menu
            Format: RETURNING TO MAIN MENU | Score=1500
            Shows: Menu return and final score

⌨KEY     - Keyboard input
            Format: SPACE pressed - Fire command
            Shows: What key was pressed and action triggered

═══════════════════════════════════════════════════════════════════════════════
 INTERPRETING GAME STATE SYMBOLS
═══════════════════════════════════════════════════════════════════════════════

GAME STATE VALUES:
  MAIN_MENU      = On main menu, waiting for SPACE to start
  PLAYING        = In-game, actively fighting enemies
  LEVEL_COMPLETE = Won the level, press ESC for next level
  GAME_OVER      = Lost, press SPACE to return to menu

HP TRACKING:
  HP=100/100     = Full health
  HP=50/100      = Half health (damaged but alive)
  HP=1/100       = Critical condition
  HP=0/100       = Dead (loss condition triggered)

AMMO TRACKING:
  AMMO=30/100    = Current ammo / Max ammo
  AMMO=0/100     = Out of ammo (can't fire)

ENERGY TRACKING:
  ENERGY=80/100  = Energy system (regenerates over time)
  ENERGY=100/100 = Full energy

WIN CONDITION:
  EnemiesDefeated=5/5 = Level won! Press ESC for next level

═══════════════════════════════════════════════════════════════════════════════
 EXAMPLE SESSION OUTPUT
═══════════════════════════════════════════════════════════════════════════════

[000.001] [STARTUP] Starting Level 1
[000.001] [LOAD] Game initialized successfully!
[000.050] [⟹STATE] GAME STARTED - Level 1 | HP=100 AMMO=30
[000.100] [◆STATE] LEVEL=1 STATE=PLAYING HP=100/100 AMMO=30/100 ENERGY=80/100
[000.100] [◆ENTITIES] Enemies=0 Bullets=0 VFX=0 EnemiesDefeated=0/5
[000.150] [⌨KEY] SPACE pressed - Fire command
[000.150] [⟳FIRE] Bullet fired! [Ammo: 29/100] [Score +5]
[001.100] [★SPAWN] Enemy #1 spawned at (1280, 450) HP=50 [Total: 1]
[001.200] [◆STATE] LEVEL=1 STATE=PLAYING HP=100/100 AMMO=29/100 ENERGY=85/100
[001.200] [◆ENTITIES] Enemies=1 Bullets=1 VFX=0 EnemiesDefeated=0/5
[002.300] [⚡HIT] Bullet struck Enemy! DMG=25 HP=25/50
[002.301] [✨VFX] Spawned COLLISION_SPARK at (640, 360) [Total: 1 active]
[002.310] [⌨KEY] SPACE pressed - Fire command
[002.310] [⟳FIRE] Bullet fired! [Ammo: 28/100] [Score +5]
[002.500] [⚡HIT] Bullet struck Enemy! DMG=25 HP=0/50
[002.501] [★KILL] Enemy defeated! Score +100 [Total: 1/5 completed]
[002.502] [✨VFX] Spawned EXPLOSION at (640, 360) [Total: 1 active]
[003.200] [◆STATE] LEVEL=1 STATE=PLAYING HP=100/100 AMMO=28/100 ENERGY=90/100
[003.200] [◆ENTITIES] Enemies=0 Bullets=0 VFX=0 EnemiesDefeated=1/5

... more gameplay ...

[025.450] [★VICTORY] Level 1 completed! Total Score: 1250
[025.451] [⟹STATE] LEVEL_COMPLETE screen shown - press ESC for next level
[025.500] [⌨KEY] ESC pressed - Advancing to next level
[025.501] [⟹STATE] ADVANCING TO LEVEL 2
[025.502] [⟹STATE] GAME STARTED - Level 2 | HP=100 AMMO=30
[025.550] [◆STATE] LEVEL=2 STATE=PLAYING HP=100/100 AMMO=30/100 ENERGY=80/100

═══════════════════════════════════════════════════════════════════════════════
 TROUBLESHOOTING DEBUG OUTPUT
═══════════════════════════════════════════════════════════════════════════════

PROBLEM: No bullets appearing after pressing SPACE

  SOLUTION: Check the console for:
  □ "AMMO=0/100" - Player is out of ammo
  □ "⟳FIRE" messages missing - Bullets not spawning
  □ Bullet fire rate limit - You can only fire once per 200ms

PROBLEM: Enemies not spawning

  SOLUTION: Check for:
  □ "★SPAWN" messages in log
  □ Wait first 1-2 seconds (enemies spawn periodically)
  □ "Enemies=0" means no active enemies
  □ "Enemies=5" means all spawned (max 5 per level)

PROBLEM: Player takes damage unexpectedly

  SOLUTION: Look for:
  □ "💥DMG!" messages showing enemy collisions
  □ Close proximity to enemy = collision occurs
  □ Each collision = -10 HP
  □ Multiple collisions = rapid health loss

PROBLEM: Game seems frozen or not responding

  SOLUTION: Check console for:
  □ "STATE=PLAYING" still showing = game is active
  □ Frame updates continuing = game loop running
  □ Lag may cause visual stutter but game continues

PROBLEM: Enemies die without bullets being fired

  SOLUTION: This shouldn't happen - check for:
  □ Accidental double-fire (rapid SPACE presses)
  □ Multiple enemies spawned at same location
  □ Bullets are invisible (rendering issue, not gameplay)

═══════════════════════════════════════════════════════════════════════════════
 PERFORMANCE TIPS
═══════════════════════════════════════════════════════════════════════════════

• Debug output is printed to STDOUT (console)
  - This slows down the game slightly
  - To disable: Set DEBUG_MODE = false in Game.java

• Log files are saved in: logs/game_debug.log
  - Can be reviewed after game closes

• Game runs at whatever frame rate the JVM permits
  - Usually 60+ FPS on modern systems
  - Debug logging doesn't significantly impact gameplay

═══════════════════════════════════════════════════════════════════════════════
 KEY BINDINGS REFERENCE
═══════════════════════════════════════════════════════════════════════════════

SPACE  = Fire bullet (in-game) or Start game (on menu)
ESC    = Return to menu (in-game) or Next level (victory screen)
LEFT   = Move left (future implementation)
RIGHT  = Move right (future implementation)

═══════════════════════════════════════════════════════════════════════════════
 QUICK STATS REFERENCE
═══════════════════════════════════════════════════════════════════════════════

SCORING:
  • Fire bullet      = +5 points
  • Defeat enemy     = +100 points
  • Time bonus       = +(500 - enemies_defeated*20) points

ENEMY SPAWNING:
  • Spawn interval   = Every 2 seconds
  • Per level        = Max 5 enemies to defeat to win
  • Damage to player = -10 HP per collision

PLAYER STATS:
  • Max health       = 100 HP
  • Max ammo         = 100 bullets
  • Max energy       = 100 (regenerates 5 points/sec)
  • Starting ammo    = 30 bullets
  • Bullet damage    = 25 HP per hit
  • Enemy health     = 50 HP (varies)

═══════════════════════════════════════════════════════════════════════════════
 TECHNICAL NOTES
═══════════════════════════════════════════════════════════════════════════════

The debug logging system uses:

Method: debugLog(String level, String message)
  • Timestamps each message with elapsed ms
  • Formats: [NNN.NNN] [LEVEL] Message
  • Automatically tracks game start time

Message Levels:
  • ◆ = State update (system info)
  • • = Idle/wait state
  • ★ = Major event (victory/defeat/spawn/kill)
  • ⟳ = Action/input related
  • ⚡ = Collision/damage event
  • 💥 = Damage taken
  • ✨ = Visual effect
  • ⟹ = State transition
  • ⌨ = Keyboard input

Debug Mode:
  • Enabled by default (DEBUG_MODE = true)
  • Enable/disable in Game.java constructor
  • When enabled: ~5-10% performance overhead
  • When disabled: No performance impact

═══════════════════════════════════════════════════════════════════════════════
End of Guide
═══════════════════════════════════════════════════════════════════════════════
