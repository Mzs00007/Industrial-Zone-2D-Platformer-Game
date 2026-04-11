# Phase 3: Tile Collision Detection & Validation
## Building on Updated AnimationAndSpriteLoader Engine

---

## What Just Changed in the Engine

The AnimationAndSpriteLoader.java now provides **9 new public accessor methods** that make it trivial to integrate tile adjacency and collision detection:

### Easy Tile Validation Methods (Available Now!):
```java
// Validate Level 1 tilemaps with adjacency rules
boolean isValid = AnimationAndSpriteLoader.validateLevel1Tilemap(char[][] tilemap);

// Get compatible tiles for any position
Set<Object> compatible = AnimationAndSpriteLoader.getLevel1TileCompatible(tile, direction);

// Similarly for Level 2 and GUI systems
boolean isValidL2 = AnimationAndSpriteLoader.validateLevel2Tilemap(tilemap);
boolean isValidGUI = AnimationAndSpriteLoader.validateGUIFrame(frame);
```

---

## Phase 3 Implementation Plan

### STEP 1: Define Tile Collision Types (Code)
Create collision detection system in Game.java:

```java
// In Game.java, add this to your Tile class:
private static class Tile {
    char code;
    String assetPath;
    float x, y;
    boolean isSolid;  // NEW: Can player pass through this tile?
    
    Tile(char code, String assetPath, float x, float y) {
        this.code = code;
        this.assetPath = assetPath;
        this.x = x;
        this.y = y;
        
        // Determine if tile is solid by code
        // 'A' = air (no collision), others = solid platforms
        this.isSolid = (code != 'A');  
    }
}
```

### STEP 2: Add Collision Detection Method
```java
// In Game.java update() method, add this check:
private boolean checkTileCollision(float playerX, float playerY, float width, float height) {
    // Get player bounds
    float playerLeft = playerX;
    float playerRight = playerX + width;
    float playerTop = playerY;
    float playerBottom = playerY + height;
    
    // Check against each tile
    for (Tile tile : levelTiles) {
        if (!tile.isSolid) continue;  // Skip air tiles
        
        float tileLeft = tile.x;
        float tileRight = tile.x + TILE_WIDTH;
        float tileTop = tile.y;
        float tileBottom = tile.y + TILE_HEIGHT;
        
        // AABB collision check
        if (playerRight > tileLeft && 
            playerLeft < tileRight && 
            playerBottom > tileTop && 
            playerTop < tileBottom) {
            return true;  // Collision detected
        }
    }
    
    return false;  // No collision
}
```

### STEP 3: Integrate with Physics
```java
// In Game.java update() method, after gravity and before position update:

@Override
public void update(long elapsedTime) {
    // ... existing input handling ...
    
    // Calculate new position
    float newPlayerX = playerX + playerVelocityX;
    float newPlayerY = playerY + playerVelocityY;
    
    // Check horizontal collision
    if (!checkTileCollision(newPlayerX, playerY, playerWidth, playerHeight)) {
        playerX = newPlayerX;  // Apply horizontal movement
    }
    
    // Check vertical collision
    if (!checkTileCollision(playerX, newPlayerY, playerWidth, playerHeight)) {
        playerY = newPlayerY;  // Apply vertical movement
    } else {
        // Hit a solid tile
        playerVelocityY = 0;  // Stop falling
        playerY = Math.floor(playerY / TILE_HEIGHT) * TILE_HEIGHT - playerHeight;  // Snap to platform
    }
    
    // ... rest of update ...
}
```

### STEP 4: Use Engine Validation (Optional Advanced Feature)
```java
// After loading tilemap, validate it with engine:
if (AnimationAndSpriteLoader.validateLevel1Tilemap(tileGrid)) {
    System.out.println("✅ Level 1 layout is valid (adjacency rules satisfied)");
} else {
    System.err.println("❌ Invalid tile layout - check adjacency");
}
```

---

## Integration Checklist

- [ ] Add `isSolid` field to Tile class
- [ ] Implement `checkTileCollision()` method
- [ ] Update `update()` to call collision detection
- [ ] Test movement - player should stop at platforms
- [ ] (Optional) Call `AnimationAndSpriteLoader.validateLevel1Tilemap()` on startup
- [ ] Recompile and test

---

## Key Points

✅ **The engine now provides the tools** - Use `AnimationAndSpriteLoader.validateLevel1Tilemap()`  
✅ **No direct imports needed** - Everything goes through AnimationAndSpriteLoader  
✅ **Seamless integration** - Call methods directly from Game.java  
✅ **AABB collision** - Simple rectangle-based collision detection  
✅ **Tile validation** - Adjacency rules enforced by engine  

---

## Next: Phase 3B (Parallax & Camera)

Once collision detection is working:
1. Implement parallax scrolling background
2. Add camera that follows player
3. Test level scrolling with player movement
