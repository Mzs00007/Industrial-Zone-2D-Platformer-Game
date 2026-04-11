# INDUSTRIAL ZONE - GAMEPLAY LAUNCHER

**Cyberpunk Action Defense Game - Phase 2**  
**Created: April 6, 2026**

---

## Quick Start

### Option 1: Run Using Batch Script (Windows)
```batch
LAUNCH_GAME.bat
```

This will automatically:
1. Check for Java installation
2. Compile any updated code
3. Launch the game window

### Option 2: Manual Compilation & Run

#### Compile:
```bash
javac -cp "lib/*;bin" src/ui/*.java src/GameLauncher.java
```

#### Run:
```bash
java -cp "lib/*;bin" GameLauncher
```

---

## Game Controls

### Character Selection Screen
- **Arrow Keys (←/→)**: Select character
  - PUNK (Red, balanced stats)
  - BIKER (Purple, speed-focused)
  - CYBORG (Grey, armor-heavy)
- **ENTER**: Confirm selection → Start gameplay
- **ESC**: Back to character select (from gameplay)

### Gameplay Screen
- **A / ←**: Move left
- **D / →**: Move right
- **W / ↑**: Move up
- **S / ↓**: Move down
- **ESC**: Return to character select

---

## Features Implemented

✅ **Character Selection**
- Visual character cards with stat display
- Professional GUI frame borders
- Real PNG asset loading

✅ **Gameplay Screen**
- Level background rendering
- Player character sprite display
- Smooth movement controls
- Asset caching for performance

✅ **Asset System**
- Automatic PNG loading from Resources directory
- Character: PUNK, BIKER, CYBORG (industrial-zone/characters/player/)
- Levels: Industrial_zone_level_1, power-station-level-2

✅ **No Graphics2D**
- Pure raster graphics (BufferedImage)
- No forbidden imports (Graphics2D, Font, Color, etc.)
- All rendering to bitmap targets

---

## Asset Directory Structure

```
Resources/industrial-zone/
├── characters/player/
│   ├── punk/        → Character sprites
│   ├── biker/       → Character sprites
│   └── cyborg/      → Character sprites
├── 1 Tiles/
│   ├── Industrial_zone_level_1/
│   │   └── 2 Background_level_1/
│   │       └── BG_Composite_*.png
│   └── power-station-level-2/
│       └── 2 Background_level_2/
│           └── BG_Composite_*.png
└── gui/
    └── 2 Bars/
        └── Health/Energy bar PNG assets
```

---

## System Requirements

- **Java**: Version 11 or higher
- **RAM**: 512 MB minimum
- **Display**: 1024×768 or higher

---

## Technical Details

### Classes Implemented

| Class | Purpose |
|-------|---------|
| GameLauncher | Main entry point, 60 FPS game loop |
| ScreenManager | Screen state machine (Select → Gameplay) |
| GameplayScreen | Level rendering + player movement |
| CharacterSelectScreen | Character selection with stat display |
| AssetLoader | PNG loading with caching |
| GUIFrameTilesetLoader | GUI frame border rendering |

### Performance

- **FPS Target**: 60 FPS (16ms per frame)
- **Asset Caching**: LRU-style caching to reduce disk I/O
- **Memory Safety**: Pure Java, no native code

---

## Known Limitations

- Single level implemented (Industrial_zone_level_1)
- Character movement is keyboard-only
- No collision detection yet
- No enemies or AI
- No projectile system

---

## Troubleshooting

### Game won't start
```
Error: Java is not installed or not in PATH
```
→ Install Java 11+ from [java.com](https://java.com)

### Game crashes immediately
```
Message: [❌ GameplayScreen] Level not found...
```
→ Verify Resources/industrial-zone directory exists

### Characters not showing
```
Message: [❌] PUNK NOT found: ...
```
→ Check Resources/industrial-zone/characters/player/ exists with PNG files

---

## For Developers

### Running in IDE
Add to Run Configuration:
- **Class**: GameLauncher
- **Working Directory**: handout/
- **Classpath**: Include lib/* and bin directories

### Debugging Assets
```java
// Enable verbose asset loading:
AssetLoader.printStats();  // Shows cache hits/misses
```

---

## Next Phase Goals

- [ ] Combat system integration
- [ ] Enemy spawning with AI
- [ ] Projectile weapon system
- [ ] Level progression (Level 2, 3, etc.)
- [ ] Pause/resume functionality
- [ ] Sound effects and background music
- [ ] Collision detection and physics

---

**Created By**: Assignment AI Agent  
**Last Updated**: April 6, 2026  
**Status**: ✅ Phase 2 Complete - Gameplay Ready for Testing
