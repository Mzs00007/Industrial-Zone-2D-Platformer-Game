╔═══════════════════════════════════════════════════════════════════════════════╗
║                                                                               ║
║          N6 ASSIGNMENT GAME - LIVE DEBUG LAUNCHER QUICK START                ║
║                                                                               ║
║  Everything you need to run and monitor your game with detailed debugging!    ║
║                                                                               ║
╚═══════════════════════════════════════════════════════════════════════════════╝

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ FASTEST WAY TO START: JUST DOUBLE-CLICK                                      ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

File: RUN_GAME_DEBUG.bat
Location: c:\...\N6AssignmentCode\handout\

⚡ INSTANTLY:
1. Double-click RUN_GAME_DEBUG.bat
2. Console appears with colorful header
3. Watch it compile and verify
4. Game launches in full debug mode
5. Debug output shows EVERYTHING happening:
   • Every game state change
   • Every bullet fired
   • Every enemy spawned
   • Every collision detected
   • Every point scored
   • Every keystroke logged
   
THAT'S IT! 🎮

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ WHERE TO FIND THE DEBUG OUTPUT                                               ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

TWO PLACES:

1. CONSOLE (Real-time while game runs)
   ► Black console window behind/next to game window
   ► Shows events as they happen
   ► Format: [000.350] [EVENT-TYPE] Description

2. DEBUG LOG FILE (Saved for later review)
   ► File: game_debug.log
   ► Location: handout/ folder
   ► Contains complete session history
   ► Access from launcher menu after game closes

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ EXAMPLE: WHAT YOU'LL SEE                                                     ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

During gameplay, you'll see messages like:

[000.050] [⟹STATE] GAME STARTED - Level 1 | HP=100 AMMO=30
[001.100] [◆STATE] LEVEL=1 STATE=PLAYING HP=100/100 AMMO=30/100 ENERGY=80/100
[001.100] [◆ENTITIES] Enemies=0 Bullets=0 VFX=0 EnemiesDefeated=0/5
[001.150] [⌨KEY] SPACE pressed - Fire command
[001.150] [⟳FIRE] Bullet fired! [Ammo: 29/100] [Score +5]
[002.100] [★SPAWN] Enemy #1 spawned at (1280, 450) HP=50 [Total: 1]
[002.300] [⚡HIT] Bullet struck Enemy! DMG=25 HP=25/50
[002.310] [⟳FIRE] Bullet fired! [Ammo: 28/100] [Score +5]
[002.500] [⚡HIT] Bullet struck Enemy! DMG=25 HP=0/50
[002.501] [★KILL] Enemy defeated! Score +100 [Total: 1/5 completed]

Each message shows EXACTLY what's happening in the game!

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ DEBUG MESSAGE LEGEND (What Each Symbol Means)                                ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

◆STATE    = Current game state snapshot (HP, AMMO, ENERGY, etc)
◆ENTITIES = How many enemies, bullets, effects are active
•IDLE     = Game is idle, waiting for input
•WAIT     = Game is waiting (victory/gameover screen)
★SPAWN    = Enemy has been created
★KILL     = Enemy defeated! Score awarded!
★VICTORY  = Level won! Condition met!
★DEFEAT   = Player died! Game over!
⟳FIRE     = Bullet shot by player
⚡HIT      = Bullet struck enemy, damage dealt
💥DMG!     = Player took damage from enemy
✨VFX      = Visual effect created (explosion, sparks, etc)
⟹STATE    = Game state transition (like: START → Play, or Play → Menu)
⌨KEY      = Keyboard input detected

Example:
[001.200] [◆STATE] LEVEL=1 STATE=PLAYING HP=85/100 AMMO=28/100 ENERGY=90/100
          └──────┘ └──────┘ └─────────────────────────────────────────────┘
          Timestamp  Type    Detailed game info

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ STEP-BY-STEP GAMEPLAY VISUALIZATION                                         ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

1. GAME STARTS (MAIN MENU)
   ◆STATE: LEVEL=1 STATE=MAIN_MENU HP=100/100
   •IDLE: Main menu - waiting for player
   ⌨KEY: SPACE pressed - Starting game!

2. GAMEPLAY BEGINS
   ◆STATE: LEVEL=1 STATE=PLAYING HP=100/100
   ★SPAWN: Enemy #1 spawned at (1280, 450) HP=50
   
3. PLAYER FIRES
   ⌨KEY: SPACE pressed - Fire command
   ⟳FIRE: Bullet fired! [Ammo: 29/100]
   
4. COLLISION HAPPENS
   ⚡HIT: Bullet struck Enemy! DMG=25 HP=25/50
   ✨VFX: Spawned COLLISION_SPARK at (640, 360)
   
5. ENEMY DEFEATED
   ⚡HIT: Bullet struck Enemy! DMG=25 HP=0/50
   ★KILL: Enemy defeated! Score +100 [Total: 1/5 completed]
   ✨VFX: Spawned EXPLOSION at (640, 360)
   
6. REPEAT... defeat 5 enemies
   [100+ more messages as gameplay continues]
   
7. LEVEL WON
   ★VICTORY: Level 1 completed! Total Score: 1250
   ⟹STATE: ADVANCING TO LEVEL 2
   
8. NEXT LEVEL OR GAME OVER
   [Game repeats or ends]

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ GAME STATS YOU CAN TRACK                                                     ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

Watch for these values in console:

PLAYER STATUS:
  HP=100/100     = Health (max 100)
  AMMO=30/100    = Ammunition (max 100)
  ENERGY=80/100  = Energy (regenerates)
  SCORE=1250     = Points earned

ENEMIES:
  Enemies=3      = Number active on screen
  EnemiesDefeated=2/5 = Progress (2 out of 5 killed)

INTERACTIONS:
  Bullets=5      = Bullets in flight
  VFX=2          = Visual effects (explosions, sparks)

DAMAGE:
  DMG=25         = Damage dealt per bullet
  DMG=-10        = Damage taken per enemy collision

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ AFTER GAME CLOSES - LAUNCHER MENU                                            ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

When you close the game window, console shows:

[OPTIONS]
  1 = Run game again
  2 = Open debug log in Notepad
  3 = Clear debug log
  4 = Exit

OPTION 1: Play again immediately (great for testing!)
OPTION 2: View all game events from this session (analyze gameplay)
OPTION 3: Delete the log file to start fresh
OPTION 4: Close everything and exit

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ KEY BINDINGS                                                                 ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

SPACE  = Fire bullet (in-game) / Start game (menu)
ESC    = Next level (victory) / Return to menu (gameplay)

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ FILES CREATED FOR YOU                                                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

📄 RUN_GAME_DEBUG.bat
   → Full-featured launcher with validation, logging, and menus
   → Just double-click!

📄 DEBUG_OUTPUT_GUIDE.md
   → Explains EVERY debug message type
   → What each symbol means
   → Example output walkthrough
   → Troubleshooting guide

📄 LAUNCHER_GUIDE.md
   → Complete guide to using the launchers
   → Step-by-step setup instructions
   → How to interpret console output
   → Advanced options and tips

📝 game_debug.log
   → Auto-created each time you run the game
   → Contains complete session history
   → Can be opened in any text editor
   → Great for reviewing gameplay later

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ WHAT'S BEEN ADDED TO GAME.java                                               ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

✅ debugLog() method - Timestamps and formats all messages
✅ Game state tracking - States logged every 1 second
✅ Entity tracking - Enemy/bullet counts every second
✅ Event logging:
   • Enemy spawning with position/health
   • Bullet firing with ammo updates
   • Collision detection (hits and damage)
   • Enemy defeats with score
   • VFX particle spawns
   • Player damage taken
   • Level transitions
   • Keyboard input
   
✅ All integrated seamlessly - No impact on game performance
✅ Easy to disable - Set DEBUG_MODE = false in Game.java

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ QUICK TROUBLESHOOTING                                                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

Q: Where's the console output?
A: Look for black window behind the game window. Alt+Tab to find it.

Q: Console says "Compilation FAILED"
A: Save Game.java in VS Code and try launcher again.

Q: What if I want to disable debug output?
A: Edit Game.java → Find "DEBUG_MODE = true;" → Change to "false;"

Q: Where do I find game_debug.log?
A: In the handout\ folder, same level as RUN_GAME_DEBUG.bat

Q: Can I run this from command prompt instead?
A: Yes! cd to handout\ folder, then: run_game.bat

Q: The messages are too fast to read!
A: Open game_debug.log file to review at your own pace.

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ YOU'RE ALL SET! 🎮✨                                                         ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

NEXT STEP:
→ Double-click RUN_GAME_DEBUG.bat

Then watch the magic happen as your game shows every event in real-time!

All the state changes, entity spawning, collisions, damage, and scoring
will be displayed in the console with beautiful formatting and timestamps.

═══════════════════════════════════════════════════════════════════════════════
