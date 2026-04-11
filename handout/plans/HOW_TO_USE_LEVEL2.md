# How to Use Level2TileRegistry in Your Code

## Quick Start

Level 2 has been created with 64 tiles and is ready to use immediately.

### Option 1: Direct Access to Tiles

```java
import tiles.Level2TileRegistry;

// Access the 64-tile array directly
Object[][] tiles = Level2TileRegistry.TILES_ASSETS;

// Get a specific tile
Object[] tileA = tiles[0];  // Platform Primary (indexed by position)
char tileId = (char) tileA[0];  // 'A'
String tileName = (String) tileA[1];  // "Platform Primary"
String path = (String) tileA[2];  // Full Resources path
```

### Option 2: Use TileMapSystem (Recommended)

```java
import tiles.TileMapSystem;

// Load Level 2
TileMapSystem tileSystem = new TileMapSystem(2);

// Access tiles by ID (O(1) lookup)
char platformTile = 'A';
boolean isSolid = tileSystem.isSolid(platformTile);
boolean isHazard = tileSystem.isHazard(platformTile);
String physicsType = tileSystem.getPhysicsType(platformTile);
float friction = tileSystem.getFriction(platformTile);
int damage = tileSystem.getDamage(platformTile);
int animFrames = tileSystem.getAnimFrames(platformTile);
```

### Option 3: Switch Between Levels

```java
TileMapSystem tileSystem = new TileMapSystem(1);  // Start with Level 1
// ... use level 1 ...

tileSystem = new TileMapSystem(2);  // Switch to Level 2
// ... use level 2 ...
```

## Level 2 Tile IDs

### Platforms (A-P) - 16 variants
- `A` to `P`: Different colored platform variants

### Walls and Structures (Q-Z, !, ") - 14 tiles
- `Q` to `V`: Brick wall units (6 tiles)
- `W` to `Z`: Edge/border tiles (4 tiles)
- `!`, `"`: Additional edge variants (2 tiles)

### Solid Walls (#-&) - 4 tiles
- Heavy wall blocks

### Slopes and Ramps (*, (, ), -, =, +) - 6 tiles
- Ramp variants for navigation

### Structural Details ([, ], {, }, |, ;) - 6 tiles
- Detail and decoration tiles

### Ceiling and Platforms (:, ,) - 2 tiles
- Overhead structure

### Tech Inlay (., /) - 2 tiles
- Decorative tech elements

### Dark Platforms (>, ?, @, ^, ~, `, space) - 7 tiles
- Alternative platform colors

### Doors (tab, \n, \r, vt) - 4 tiles
- Interactive door elements

### Ceiling Tiles (form feed through DC3) - 4 tiles
- Ceiling structure

## Properties Available for Each Tile

- **Solid**: Can collide with (boolean)
- **Hazard**: Deals damage (boolean)
- **Damage**: Damage amount if hazard (int)
- **Physics Type**: STATIC, DYNAMIC, or other (String)
- **Friction**: Surface friction (float, 0.0-1.0)
- **Animation Frames**: Number of animation frames if animated (int)
- **Frame Timing**: MS per frame for animation (int)
- **Asset Path**: Full path to Resources PNG file (String)

## Integration Example

```java
// In your game loop
TileMapSystem level2 = new TileMapSystem(2);

// Check tile properties
if (level2.isSolid('A')) {
    // Platform A is solid, can stand on it
}

if (level2.isHazard('w')) {
    // Tile 'w' is a hazard, apply damage
    int damageAmount = level2.getDamage('w');
}

// Load animation frames if available
int frames = level2.getAnimFrames('w');
if (frames > 0) {
    // Tile is animated, use frames
}
```

## Files Provided

- `src/tiles/Level2TileRegistry.java` - The 64-tile registry
- `src/tiles/TileMapSystem.java` - O(1) lookup system
- `src/Level2GameIntegration.java` - Usage demonstration
- `bin/tiles/Level2TileRegistry.class` - Compiled class
- `bin/tiles/TileMapSystem.class` - Compiled class

## Verification

Run StudentUsageExample to verify everything works:
```bash
javac -d bin -cp bin src/StudentUsageExample.java
java -cp bin StudentUsageExample
```

Expected output:
```
✅ VERIFIED: Level 2 has exactly 64 tiles
✅ LEVEL 2 TILE REGISTRY IS READY FOR USE
```

## Testing

Run the test suite:
```bash
java -cp bin TileMapSystemTest
```

All 7 tests should pass, including:
- ✅ Level 2 loads 64 tiles
- ✅ Tile properties accessible
- ✅ Level switching works

---

**Status**: Production Ready
**Verified**: 64 tiles, all physics properties, zero compilation errors
**Ready for**: Game integration, collision detection, hazard systems
