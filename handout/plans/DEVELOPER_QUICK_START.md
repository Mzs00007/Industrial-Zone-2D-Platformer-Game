# Developer Quick Start Guide
## Using AnimationAndSpriteLoader in Your Game Code
**Updated**: April 3, 2026 - Engine now includes Adjacency System Accessors

---

## TL;DR - Quick Usage

### Import (One Line!)
```java
import animation.AnimationAndSpriteLoader;
```

### That's It! You can now use:
```java
// Tile operations
String tilePath = AnimationAndSpriteLoader.getTile('A');
boolean valid = AnimationAndSpriteLoader.validateLevel1Tilemap(tilemap);

// Asset paths
String playerAssets = AnimationAndSpriteLoader.getPlayerBasePath();
String level1Tiles = AnimationAndSpriteLoader.getLevel1TilesPath();

// GUI building
boolean frameOk = AnimationAndSpriteLoader.validateGUIFrame(frame);

// Physics
float gravity = AnimationAndSpriteLoader.getGravity();
float pixelMeters = AnimationAndSpriteLoader.pixelsToMeters(100);
```

---

## Detailed Reference

### 1. Tile Registry Access

```java
// Get tile asset path by character code
String assetPath = AnimationAndSpriteLoader.getTile('A');  // Level 1 grass tile
if (assetPath != null) {
    BufferedImage tileImage = ImageIO.read(new File(assetPath));
}

// Check if tile code exists
if (AnimationAndSpriteLoader.hasTile('Z')) {
    System.out.println("Tile Z is registered");
}

// Get all registered tile codes
Set<Character> codes = AnimationAndSpriteLoader.getAllTileCodes();
System.out.println("Total tiles: " + AnimationAndSpriteLoader.getTileCount());
```

### 2. Asset Path Access (Unified)

```java
// CHARACTER ASSETS
String playerBase = AnimationAndSpriteLoader.getPlayerBasePath();
String bossBase = AnimationAndSpriteLoader.getBossBasePath();
String enemyBase = AnimationAndSpriteLoader.getEnemyBasePath();

// LEVEL ASSETS
String l1Tiles = AnimationAndSpriteLoader.getLevel1TilesPath();
String l1BG = AnimationAndSpriteLoader.getLevel1BackgroundPath();
String l1Objects = AnimationAndSpriteLoader.getLevel1ObjectsPath();

String l2Tiles = AnimationAndSpriteLoader.getLevel2TilesPath();
String l2BGDay = AnimationAndSpriteLoader.getLevel2BackgroundDayPath();
String l2BGNight = AnimationAndSpriteLoader.getLevel2BackgroundNightPath();

// GUI ASSETS
String guiFrames = AnimationAndSpriteLoader.getGUIFramesPath();
String guiButtons = AnimationAndSpriteLoader.getGUIButtonsPath();
String guiIcons = AnimationAndSpriteLoader.getGUIIconsPath();

// VFX & AUDIO
String vfxSmoke = AnimationAndSpriteLoader.getVFXSmokePath();
String audioSFX = AnimationAndSpriteLoader.getAudioSFXPath();

// INPUT ICONS
String keyboardKeys = AnimationAndSpriteLoader.getKeyboardKeysPath();
```

### 3. Tile Adjacency & Validation (NEW!)

```java
// LEVEL 1 VALIDATION
char[][] tilemap = { ... };
if (AnimationAndSpriteLoader.validateLevel1Tilemap(tilemap)) {
    System.out.println("✅ Level 1 is valid");
} else {
    System.out.println("❌ Invalid tile adjacency");
}

// LEVEL 1 COMPATIBILITY CHECK
Set<?> compatibleTiles = AnimationAndSpriteLoader.getLevel1TileCompatible(
    currentTile, 
    1  // Direction: 0=Top, 1=Right, 2=Bottom, 3=Left
);
System.out.println("Can place these tiles to the right: " + compatibleTiles);

// LEVEL 2 VALIDATION
if (AnimationAndSpriteLoader.validateLevel2Tilemap(level2Map)) {
    loadLevel2(level2Map);
}

// GUI FRAME VALIDATION
if (AnimationAndSpriteLoader.validateGUIFrame(myFrame)) {
    renderGUIFrame(myFrame);
}

// GUI TILE PLACEMENT CHECK (real-time editing)
boolean canPlace = AnimationAndSpriteLoader.canPlaceGUITile(
    tileType,
    tileAbove,    // null if none
    tileRight,
    tileBelow,
    tileLeft
);
```

### 4. Physics Utilities

```java
// Convert between pixel and meter coordinates
float pixelDist = 64;
float meterDist = AnimationAndSpriteLoader.pixelsToMeters(pixelDist);  // 1.0m

float meters = 2.0f;
float pixels = AnimationAndSpriteLoader.metersToPixels(meters);  // 128px

// Get game physics constants
float gravity = AnimationAndSpriteLoader.getGravity();  // 10.0 m/s²
float tileSize = AnimationAndSpriteLoader.getTileSizePixels();  // 64px
float pixelsPerMeter = AnimationAndSpriteLoader.getPixelsPerMeter();  // 64 px/m
```

### 5. Factory Methods (Create Objects)

```java
// Create player with physics
AnimationAndSpriteLoader.PlayerController player = 
    AnimationAndSpriteLoader.createPlayer(100, 200);

// Create enemy with AI radius
AnimationAndSpriteLoader.EnemyController enemy = 
    AnimationAndSpriteLoader.createEnemy(300, 150, 200);  // 200px detection radius

// Create boss
AnimationAndSpriteLoader.BossController boss = 
    AnimationAndSpriteLoader.createBoss(500, 100);
```

### 6. Direct Class Access (Advanced)

```java
// For cases where reflection methods aren't sufficient
Class<?> guiSystem = AnimationAndSpriteLoader.getGUIAdjacencySystemClass();
if (guiSystem != null) {
    // Use reflection or instantiate directly
    // without needing: import animation.GUITileAdjacencySystem;
}

Class<?> level1System = AnimationAndSpriteLoader.getLevel1AdjacencySystemClass();
Class<?> level2System = AnimationAndSpriteLoader.getLevel2AdjacencySystemClass();
```

### 7. Diagnostic & System Info

```java
// Get all asset paths as a map
Map<String, String> assets = AnimationAndSpriteLoader.getAssetPathsMap();
assets.forEach((name, path) -> System.out.println(name + " → " + path));

// Print comprehensive engine diagnostics
System.out.println(AnimationAndSpriteLoader.printDiagnostics());

// Check if system is ready
if (AnimationAndSpriteLoader.isGameIntegrationReady()) {
    System.out.println("All systems operational!");
}

// Validate all asset directories exist
if (AnimationAndSpriteLoader.validateAssetDirectories()) {
    System.out.println("All asset directories found");
}

// Get total asset paths defined
int totalPaths = AnimationAndSpriteLoader.getTotalAssetPaths();
System.out.println("Total asset path constants: " + totalPaths);
```

---

## Common Patterns

### Pattern 1: Load Tile-Based Level

```java
private List<Tile> loadLevel1() {
    List<Tile> tiles = new ArrayList<>();
    char[][] levelGrid = { ... };
    
    // Validate first
    if (!AnimationAndSpriteLoader.validateLevel1Tilemap(levelGrid)) {
        System.err.println("Invalid level layout!");
        return null;
    }
    
    // Load tiles
    for (int y = 0; y < levelGrid.length; y++) {
        for (int x = 0; x < levelGrid[y].length; x++) {
            char code = levelGrid[y][x];
            String path = AnimationAndSpriteLoader.getTile(code);
            if (path != null) {
                tiles.add(new Tile(code, path, x * 64, y * 64));
            }
        }
    }
    
    return tiles;
}
```

### Pattern 2: Build GUI Frame

```java
private boolean buildGUIFrame() {
    FrameTileBuilder frame = PresetFrameSets.createStandardBlueFrame();
    frame.validate(); // Check all pieces present
    frame.loadAll();  // Load images from disk
    
    // Validate final frame
    if (AnimationAndSpriteLoader.validateGUIFrame(frame)) {
        renderFrame(frame);
        return true;
    }
    
    return false;
}
```

### Pattern 3: Real-Time Tile Editor

```java
private void placeGUITile(GUITileType tileType, GUITileType top, 
                          GUITileType right, GUITileType bottom, GUITileType left) {
    
    // Check if placement is valid BEFORE placing
    if (AnimationAndSpriteLoader.canPlaceGUITile(tileType, top, right, bottom, left)) {
        currentFrame.setTile(x, y, tileType);
        System.out.println("✓ Tile placed");
    } else {
        System.out.println("✗ Invalid tile placement");
    }
}
```

### Pattern 4: Procedural Level Generation

```java
private char[][] generateLevel() {
    char[][] level = new char[20][30];
    
    // Generate tiles (not shown)
    fill_level_with_tiles(level);
    
    // Validate generated level
    while (!AnimationAndSpriteLoader.validateLevel1Tilemap(level)) {
        // Fix invalid tiles
        fix_adjacency_issues(level);
    }
    
    return level; // Guaranteed valid
}
```

---

## Method Reference (80+ Methods)

### Asset Paths (70+ methods)
```
getPlayerBasePath()
getBossBasePath()
getEnemyBasePath()
getDroneBasePath()
getSciFiEnemyPath()
getLevel1TilesPath()
getLevel1BackgroundPath()
getLevel1ObjectsPath()
getLevel1AnimatedPath()
getLevel2TilesPath()
getLevel2BackgroundPath()
getLevel2BackgroundDayPath()
getLevel2BackgroundNightPath()
getLevel2ObjectsPath()
getLevel2ObjectsTubePath()
getLevel2ObjectsDecorPath()
getLevel2ObjectsPowerLinesPath()
getLevel2AnimatedPath()
getGUIBasePath()
getGUIFramesPath()
getGUIBarsPath()
getGUIIconsPath()
getGUIButtonsPath()
getGUINumbersPath()
getGUICursorsPath()
getGUIFontPath()
getGUICardAnimationsPath()
getVFXBasePath()
getVFXSmokePath()
getVFXBloodPath()
getVFXSparksPath()
getVFXParticlesPath()
getVFXOtherPath()
getVFXExtraCharacterPath()
getVFXExtraObjectsPath()
getVFXExtraBox1Path()
getVFXExtraBox2Path()
getVFXExtraBushPath()
getVFXExtraCapsulePath()
getWeaponsBasePath()
getWeapon1BikerPath()
getWeapon1PunkPath()
getWeapon1CyborgPath()
getWeapon1GunsPath()
getWeapon1HandsPath()
getWeapon1ShootEffectsPath()
getWeapon1BulletsPath()
getWeapon2BikerPath()
getWeapon2PunkPath()
getWeapon2CyborgPath()
getWeapon2GunsPath()
getWeapon2HandsPath()
getWeapon2ShootEffectsPath()
getWeapon2BulletsPath()
getAudioBasePath()
getAudioMusicMidiPath()
getAudioMusicWavPath()
getAudioSFXPath()
getKeyboardKeysPath()
getMouseKeysPath()
```

### Tile Registry (4 methods)
```
getTile(char code) → String
getAllTileCodes() → Set<Character>
hasTile(char code) → boolean
getTileCount() → int
```

### Adjacency Systems (9 methods) ✨ NEW
```
getGUITileCompatible(Object tile, int direction) → Set<?>
validateGUIFrame(Object frame) → boolean
canPlaceGUITile(Object tile, ...) → boolean
getLevel1TileCompatible(Object tile, int direction) → Set<?>
validateLevel1Tilemap(char[][] tilemap) → boolean
getLevel2TileCompatible(Object tile, int direction) → Set<?>
validateLevel2Tilemap(char[][] tilemap) → boolean
getGUIAdjacencySystemClass() → Class<?>
getLevel1AdjacencySystemClass() → Class<?>
getLevel2AdjacencySystemClass() → Class<?>
```

### Physics (5 methods)
```
pixelsToMeters(float px) → float
metersToPixels(float m) → float
getGravity() → float
getTileSizeMeters() → float
getTileSizePixels() → float
getPixelsPerMeter() → float
```

### Object Creation (3 methods)
```
createPlayer(float x, float y) → PlayerController
createEnemy(float x, float y, float detectionRadius) → EnemyController
createBoss(float x, float y) → BossController
```

### System Info (5 methods)
```
getAssetPathsMap() → Map<String, String>
printDiagnostics() → String
isGameIntegrationReady() → boolean
validateAssetDirectories() → boolean
getTotalAssetPaths() → int
```

---

## Summary

✅ **One Import**: `import animation.AnimationAndSpriteLoader;`  
✅ **80+ Methods**: All asset paths, physics, tile operations, GUI validation  
✅ **9 New Methods**: Adjacency system accessors for validation  
✅ **No Missing Functionality**: Complete game engine API  
✅ **Easy Discovery**: IDE autocomplete shows all available methods  

**🚀 Start coding!** Use these methods in Game.java and other game classes.
