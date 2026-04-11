# Getting Started with Industrial Zone 2D Platformer Game

This guide will help you set up, build, and run the Industrial Zone game on your machine.

## Prerequisites

Before you begin, ensure you have the following installed:

### Required
- **Java Development Kit (JDK) 8 or higher**
  - Download: https://www.oracle.com/java/technologies/downloads/
  - Verify: `java -version` and `javac -version` in terminal

### For Windows Users
- **PowerShell 5.1 or higher** (usually pre-installed)
- **Git** (for cloning the repository)
  - Download: https://git-scm.com/download/win

### Optional but Recommended
- **Visual Studio Code** (for development)
- **JetBrains IntelliJ IDEA Community** (excellent Java IDE)

## Installation & Setup

### Step 1: Clone the Repository

```bash
git clone https://github.com/Mzs00007/Industrial-Zone-2D-Platformer-Game.git
cd Industrial-Zone-2D-Platformer-Game
```

### Step 2: Verify Java Installation

```bash
java -version
javac -version
```

Both should show version 8 or higher. If not, install/update Java.

### Step 3: Review Repository Structure

```
├── handout/src/              → Java source code (16 packages)
├── Resources/                → Game assets (sprites, audio)
├── docs/                     → Documentation guides
├── scripts/                  → Build & automation
└── [Build scripts in root]
```

## Building the Game

### Option 1: Quick Build (Recommended)

**Windows PowerShell**:
```powershell
.\build.ps1
```

This will:
1. ✅ Compile all Java source files
2. ✅ Link all subsystems
3. ✅ Create executable game
4. ✅ Verify build success

### Option 2: Batch Compilation (Dependency-Aware)

For detailed control with proper dependency handling:

```powershell
.\compile_batched.ps1
```

This compilation method:
- **Phase 1**: Compiles core engine first
- **Phase 2**: Compiles all subsystems
- **Phase 3**: Links integration layer
- Handles dependencies correctly

### Option 3: Standard Compilation

Simple direct compilation:

```powershell
.\compile.ps1
```

## Running the Game

### Launch the Game

```powershell
.\run_game.ps1
```

### Expected Output

You should see:
```
✅ Build verified
✅ Classpath configured
✅ Launching game...
✅ Game initialized successfully
```

Then the game window will open (1920×1080 resolution) running at **60 FPS**.

### Troubleshooting Launch Issues

**Problem**: "Java command not found"
- **Solution**: Java is not in PATH. Install JDK and add to system PATH.

**Problem**: "Class files not found"
- **Solution**: Run `.\build.ps1` first to compile sources.

**Problem**: "Asset files not found"
- **Solution**: Ensure `Resources/` folder is in project root.

**Problem**: Game closes immediately
- **Solution**: Check console output for errors. Run compile again.

## Project Structure Overview

### Source Code (handout/src/)

16 Java packages containing 500+ compiled classes:

```
ai/              → Enemy AI system (20 classes)
audio/           → Audio management (26 classes)
camera/          → Camera system (14 classes)
combat/          → Weapons & combat (21 classes)
config/          → Configuration (6 classes)
core/            → Game engine core (21+ classes)
events/          → Event system (21+ classes)
levels/          → Level management (45+ classes)
objectives/      → Quest system (7 classes)
optimization/    → Performance (6 classes)
physics/         → Physics engine (24 classes)
rendering/       → Rendering (30+ classes)
tiles/           → Tile system (5 classes)
ui/              → UI system (80+ classes)
utils/           → Utilities (8 classes)
vfx/             → Visual effects (18 classes)
```

### Game Assets (Resources/)

```
player/          → Player sprites (40+ frames)
enemies/         → Enemy sprites (drone, robot, etc.)
levels/          → Tile sets & backgrounds
weapons/         → Weapon & projectile graphics
ui/              → Menu & HUD graphics (80+ items)
vfx/             → Particle effects (30+ sprites)
audio/           → Music & sound effects (100+ files)
```

### Documentation (docs/)

```
architecture/    → System design & patterns
systems/         → Individual system docs
implementation/  → Implementation guides
guides/          → User & developer guides
reference/       → Quick reference materials
status/          → Project completion reports
```

## Game Controls

### Keyboard

| Key | Action |
|-----|--------|
| **Arrow Left** | Move left |
| **Arrow Right** | Move right |
| **Space** | Jump |
| **Z** | Fire weapon |
| **X** | Switch weapon |
| **R** | Reload ammunition |
| **P** | Pause game |
| **ESC** | Menu |

### Mouse

| Action | Effect |
|--------|--------|
| **Mouse Click** | Menu interactions |
| **Mouse Hover** | Button highlights |

## Game Features Overview

### 2 Playable Levels

**Level 1** (Tutorial/Easy)
- 40×25 tile grid
- 89 unique tile types
- 3 checkpoints
- 12 enemies
- Beginner-friendly difficulty

**Level 2** (Challenge)
- 25×19 tile grid
- 64 unique tile types
- 2 checkpoints
- 18 enemies
- Boss encounter
- 40% harder than Level 1

### Enemy Types (5 total)

| Enemy | Health | Speed | Range | Behavior |
|-------|--------|-------|-------|----------|
| **Drone** | 30 HP | 150 | 300px | Flying ranged |
| **Robot** | 50 HP | 100 | 150px | Ground charging |
| **Turret** | 80 HP | 0 | 500px | Stationary gunner |
| **Boss** | 200 HP | 80 | 400px | Multi-phase |
| **Minion** | 15 HP | 120 | 50px | Weak melee |

### Weapon Types (4 total)

| Weapon | Damage | Fire Rate | Magazine | Range |
|--------|--------|-----------|----------|-------|
| **Pistol** | 10 | 6/sec | 30 | 800px |
| **Rifle** | 20 | 4/sec | 60 | 1200px |
| **Shotgun** | 30 | 2/sec | 24 | 400px |
| **Special** | 40 | 1/sec | 8 | 1600px |

## Performance

### Expected Performance

- **Frame Rate**: Consistent 60 FPS
- **Frame Time**: 16.4ms average
- **Memory**: 120-180MB usage
- **Render Optimizations**: 30-50% fewer draw calls via culling

### Performance Profiling

The game includes built-in performance profiling. Check console for:
```
Frame Time: 16.4ms (60.0 FPS)
  Render: 10.8ms (65%)
  Update: 3.2ms (20%)
  GC: 2.4ms (15%)

Memory: 145MB / 256MB
Active Objects: 142
Culled: 78 (35%)
```

## Development & Modding

### Modifying Game Values

Edit configuration files in `Resources/config/`:

```json
{
  "display": {
    "width": 1920,
    "height": 1080,
    "fps": 60,
    "vsync": true
  },
  "physics": {
    "gravity": -9.81,
    "friction": 0.85
  },
  "player": {
    "health": 100,
    "speed": 200,
    "jumpForce": 400
  }
}
```

### Compiling After Changes

After modifying Java files:

```powershell
# Quick rebuild
.\build.ps1

# Or batch compile
.\compile_batched.ps1

# Then run
.\run_game.ps1
```

## Understanding the Architecture

### 8-Layer Game Engine

```
Layer 1: Game Loop & Events
Layer 2: Physics & Collision
Layer 3: AI & Decision Making
Layer 4: Audio Management
Layer 5: Animation & Sprites
Layer 6: Rendering Pipeline
Layer 7: UI/HUD Management
Layer 8: Asset Loading & Caching
```

### Main Classes to Know

| Class | Purpose | Location |
|-------|---------|----------|
| `Game.java` | Entry point | `handout/src/` |
| `GameEngine` | Core orchestrator | `core/CoreSystem.java` |
| `PhysicsSystem` | Physics engine | `physics/PhysicsSystem.java` |
| `RenderingSystem` | Rendering pipeline | `rendering/RenderingSystem.java` |
| `AISystem` | Enemy AI | `ai/AISystem.java` |
| `UISystem` | UI/HUD | `ui/UISystem.java` |

## Documentation Guide

### For Quick Start
→ Read: [`../README.md`](../README.md)

### For Architecture Understanding
→ Read: [`architecture/GAME_ENGINE_ARCHITECTURE.md`](architecture/GAME_ENGINE_ARCHITECTURE.md)

### For System Implementation
→ Read: [`implementation/GUI_IMPLEMENTATION_DETAILED_PLAN.md`](implementation/GUI_IMPLEMENTATION_DETAILED_PLAN.md)

### For API Reference
→ Read: [`reference/INTEGRATION_QUICK_REFERENCE.md`](reference/INTEGRATION_QUICK_REFERENCE.md)

### For Game Design
→ Read: [`guides/HOW_TO_USE_LEVEL2.md`](guides/HOW_TO_USE_LEVEL2.md)

## Common Tasks

### Add a New Enemy Type

1. Edit: `handout/src/ai/AISystem.java`
2. Add `EnemyType` enum value
3. Create behavior class extending `BehaviorTree`
4. Run: `.\build.ps1`

### Change Game Difficulty

1. Edit: `Resources/config/game_config.json`
2. Modify:
   - `ENEMY_DAMAGE_MULTIPLIER`
   - `AMMO_SCARCITY`
   - `PLAYER_HEALTH`
3. Run: `.\run_game.ps1` (no recompile needed)

### Add New Weapon

1. Edit: `handout/src/combat/CombatSystem.java`
2. Add `WeaponType` enum entry
3. Define weapon stats
4. Add weapon sprites to `Resources/weapons/`
5. Run: `.\build.ps1`

### Modify Level Design

1. Edit: `Resources/levels/level1.map` or `level2.map`
2. Change tilemap layout or object placements
3. Run: `.\run_game.ps1`

## Troubleshooting

### Build Issues

**Compilation Errors**
```powershell
# Run with verbose output
javac -d bin handout/src/**/*.java 2>&1
```

**Missing Dependencies**
```powershell
# Verify all imports
python scripts/Python/fix_all_imports.py
```

### Runtime Issues

**Window doesn't open**
- Check Java heap size: Errors in console
- Increase memory if needed

**Assets not loading**
- Verify `Resources/` folder exists
- Check file paths in code

**Game runs slowly**
- Check FPS counter in console
- Review performance profiling output

## Next Steps

1. ✅ Review the [`../README.md`](../README.md) for overview
2. ✅ Explore [`architecture/`](architecture/) for system understanding
3. ✅ Read [`guides/`](guides/) for feature guides
4. ✅ Check [`reference/`](reference/) for API details
5. ✅ Dive into the code: Start with `Game.java`

## Support & Resources

### Documentation
- See `docs/` folder for comprehensive guides
- Read inline code comments for implementation details
- Check console output for debug information

### Key Files
- **Project Summary**: [`COMPLETE_PROJECT_DETAILED_SUMMARY.md`](../COMPLETE_PROJECT_DETAILED_SUMMARY.md)
- **Main README**: [`README.md`](../README.md)
- **Architecture**: [`architecture/GAME_ENGINE_ARCHITECTURE.md`](architecture/GAME_ENGINE_ARCHITECTURE.md)

---

**Happy Coding! 🎮**

For issues or questions, refer to the documentation in the `docs/` folder.
