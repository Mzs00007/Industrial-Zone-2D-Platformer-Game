# AnimationAndSpriteLoader.java - Method Signature Analysis

## Overview
This document provides the exact method signatures and locations found in the AnimationAndSpriteLoader.java file, including what Game.java must override from GameCore.

---

## 1. KEY LISTENER / INPUT METHODS

### Location: Lines 1634-1665 (InputHandler Inner Class)

#### Method: `isKeyPressed(int keyCode)`
**Line Number:** 1662-1663
```java
public boolean isKeyPressed(int keyCode) {
    return (keyCode >= 0 && keyCode < 256) ? keyPressed[keyCode] : false;
}
```

**Purpose:**
- Checks if a key is currently being held down
- Part of the `InputHandler` inner static class
- Used for polling keyboard input in the game loop

**Usage in Game.java (Lines 81, 83, 89):**
```java
if (isKeyPressed(KeyEvent.VK_LEFT)) { ... }
if (isKeyPressed(KeyEvent.VK_RIGHT)) { ... }
if (isKeyPressed(KeyEvent.VK_SPACE) && onGround) { ... }
```

**Note:** Game.java calls `isKeyPressed()` directly (inherited from GameCore or AnimationAndSpriteLoader)

---

### Related Input Handler Methods:

#### Method: `onKeyDown(int keyCode)` 
**Line Number:** 1640-1644
```java
public void onKeyDown(int keyCode) {
    if (keyCode >= 0 && keyCode < 256) {
        keyPressed[keyCode] = true;
        keyReleased[keyCode] = false;
    }
}
```

#### Method: `onKeyUp(int keyCode)`
**Line Number:** 1649-1654
```java
public void onKeyUp(int keyCode) {
    if (keyCode >= 0 && keyCode < 256) {
        keyPressed[keyCode] = false;
        keyReleased[keyCode] = true;
    }
}
```

#### Method: `isKeyReleased(int keyCode)`
**Line Number:** 1658-1660
```java
public boolean isKeyReleased(int keyCode) {
    return (keyCode >= 0 && keyCode < 256) ? keyReleased[keyCode] : false;
}
```

**Summary of InputHandler Class:**
- Field: `private boolean[] keyPressed = new boolean[256];` (Line 1634)
- Field: `private boolean[] keyReleased = new boolean[256];` (Line 1635)
- Field: `private long[] lastKeyTime = new long[256];` (Line 1636)
- Constant: `private static final long DOUBLE_TAP_WINDOW = 250;` (Line 1637)

---

## 2. TILEMAP / LEVEL DATA METHODS

### ⚠️ CRITICAL ISSUE FOUND:

**Problem Location:** Game.java, Line 222
```java
private Object[][] getLevelTilemap() {
    try {
        return AnimationAndSpriteLoader.Level1TileRegistry.getTileMap();
    } catch (Exception e) {
        System.err.println("ERROR: Could not get tilemap: " + e.getMessage());
        return null;
    }
}
```

### ❌ Method Does NOT Exist:
**`getTileMap()` is NOT defined** in `Level1TileRegistry` (Lines 227-250)

### ✅ Methods THAT DO Exist in Level1TileRegistry:

#### Line 231-233: `getTile(char code)`
```java
public static String getTile(char code) {
    return REGISTRY.getOrDefault(code, null);
}
```
**Returns:** Full path to tile asset PNG for a character code, or null

#### Line 238-240: `getAllCodes()`
```java
public static Set<Character> getAllCodes() {
    return REGISTRY.keySet();
}
```
**Returns:** Sorted set of all valid tile codes (A-Z, a-z, 0-9, !@)

#### Line 244-246: `hasTile(char code)`
```java
public static boolean hasTile(char code) {
    return REGISTRY.containsKey(code);
}
```
**Returns:** true if tile code is registered, false otherwise

#### Line 250-252: `getTileCount()`
```java
public static int getTileCount() {
    return REGISTRY.size();
}
```
**Returns:** Total number of registered tiles (65 tiles for Level 1)

### Registry Data Structure:
```java
private static final Map<Character, String> REGISTRY = new TreeMap<>();
```
- Type: TreeMap<Character, String>
- Maps single characters (A-Z, a-z, 0-9, !@) to full asset file paths
- Static initialization with 65 tile codes (Lines 165-224)

---

## 3. UPDATE METHOD (GAME LOOP OVERRIDE)

### Location: Game.java, Line 71

```java
@Override
public void update() {
    handleInput();
    updatePhysics();
    updateAnimation();
}
```

**Purpose:**
- Overrides the abstract or virtual `update()` method from GameCore
- Called every game loop frame
- Must implement game logic here

**Related Abstract/Virtual Methods That Game Must Override:**
- `public void update()` - Main game loop update (Game.java:71)
- `public void paint(Graphics2D g)` - Rendering (Game.java should override)
- `public void init()` - Initialization (if needed)

**Physics Update Call in AnimationAndSpriteLoader.java (Line 648):**
```java
public void update(float deltaTime) {
    if (deltaTime <= 0) return;
    // ... physics calculations
}
```
This is for `PhysicsBody` class inside `PhysicsUnitSystem`, NOT the game loop update.

---

## 4. CHARACTER ANIMATION STATES

### Location: Lines 1067-1196 (AnimationState Enum)

The `AnimationState` enum defines all possible animation states with serial numbers:

```java
public enum AnimationState {
    // PLAYER STATES (01-24)
    IDLE                (1,  "idle", 4, 150),
    WALK                (3,  "walk", 5, 100),
    RUN                 (4,  "run", 4, 60),
    JUMP                (5,  "jump", 3, 80),
    // ... 20 more player states
    
    // ENEMY STATES (50-55)
    ENEMY_IDLE          (50, "enemy_idle", 2, 200),
    // ... more enemy states
    
    // ... BOSS, VFX, GUI states
}
```

**Fields:**
- `serialNumber` - Unique identifier (01-92)
- `filename` - State name for file building
- `frameCount` - Default number of frames
- `frameTimingMs` - Milliseconds per frame

**Key Method:**
```java
public static AnimationState bySerialNumber(int serialNumber) {
    for (AnimationState state : AnimationState.values()) {
        if (state.serialNumber == serialNumber) {
            return state;
        }
    }
    return null;
}
```

---

## 5. GAME MUST OVERRIDE FROM GameCore

Based on typical Swing game framework patterns, Game.java extending AnimationAndSpriteLoader must override:

1. **`public void update()`** ✅ (Line 71 - Already overridden)
   - Called once per frame
   - Handle input, physics, animation

2. **`public void paint(Graphics2D g)`** (Should be present)
   - Called after update() to render
   - Draw tiles, player, etc.

3. **`public void init()`** (Optional)
   - Called once at game start
   - Load resources, initialize state

4. **`public void keyTyped(KeyEvent e)`** (Optional)
   - Handle single character input
   - Normally empty unless text input needed

5. **`public void keyPressed(KeyEvent e)`** → Calls `InputHandler.onKeyDown(keyCode)`
6. **`public void keyReleased(KeyEvent e)`** → Calls `InputHandler.onKeyUp(keyCode)`

---

## 6. SUMMARY TABLE

| Signature | Location | Status | Purpose |
|-----------|----------|--------|---------|
| `isKeyPressed(int)` | Line 1662 | ✅ Exists | Check if key held |
| `getTileMap()` | Not Found | ❌ MISSING | Should get level tilemap |
| `update()` | Game:71 | ✅ Overridden | Game loop update |
| `getTile(char)` | Line 231 | ✅ Exists | Get tile asset path |
| `getAllCodes()` | Line 238 | ✅ Exists | Get all tile codes |
| `hasTile(char)` | Line 244 | ✅ Exists | Check if tile exists |
| `getTileCount()` | Line 250 | ✅ Exists | Get total tiles |

---

## 7. ACTION ITEMS

### Critical Fix Needed:
**Game.java Line 222** - Remove call to non-existent `getTileMap()` method:
```java
// WRONG - Method doesn't exist
return AnimationAndSpriteLoader.Level1TileRegistry.getTileMap();

// CORRECT - Define the tilemap elsewhere or create the method
// Option 1: Create the method to return a level grid
// Option 2: Hardcode the tilemap in Game.java
// Option 3: Create a separate Level1 class
```

### Questions to Answer:
1. **Where should the level tilemap (grid of tiles) be defined?**
   - In AnimationAndSpriteLoader?
   - In Game.java?
   - In a separate Level1.java class?

2. **What is the expected structure of getTileMap()?**
   - Should return `Object[][]` (2D array of tile codes)
   - Each element is a Character like 'A', 'B', 'C', etc.

3. **Should Level1TileRegistry have a getTileMap() static method?**
   - Would need to store and return the actual level grid
   - Currently only maps character codes to asset paths

