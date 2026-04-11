# 🚀 PROJECTILE ANIMATION SYSTEM
## Complete API Reference & Implementation Guide

**Version:** 1.0  
**Status:** PRODUCTION READY  
**Created:** 2026-03-30

---

## TABLE OF CONTENTS

1. [Quick Start](#quick-start)
2. [Architecture Overview](#architecture-overview)
3. [Core Concepts](#core-concepts)
4. [API Reference](#api-reference)
5. [Code Examples](#code-examples)
6. [Integration Guide](#integration-guide)
7. [Troubleshooting](#troubleshooting)
8. [File Structure](#file-structure)

---

## QUICK START

### Initialize the Registry (Once at Startup)
```java
// In your Game.java or main class initialization:
ProjectileAnimationRegistry.initializeRegistry();

// Verify registry loaded
System.out.println(ProjectileAnimationRegistry.getStatistics());
```

### Get All Projectiles for a Character
```java
List<ProjectileDefinition> projectiles = 
    ProjectileAnimationRegistry.getProjectilesFor("RugbyGuy");

for (ProjectileDefinition def : projectiles) {
    System.out.println("✓ " + def.projectileType + ": " + def.description);
    // Output: ✓ ball: Rugby ball throw - single frame projectile
}
```

### Load a Projectile Animation
```java
// Method 1: By character and attack type
HorizontalSpritesheetLoader loader = 
    ProjectileAnimationRegistry.loadProjectile("RugbyGuy", "projectile");

if (loader != null) {
    BufferedImage frame = loader.getFrame(0);
    // Use frame for rendering
}

// Method 2: By projectile ID
HorizontalSpritesheetLoader loader = 
    ProjectileAnimationRegistry.loadProjectile("RugbyGuy_RugbyBall");
```

### Check if Character Has Projectiles
```java
if (ProjectileAnimationRegistry.hasProjectiles("GreenMech")) {
    // Custom rendering logic for projectile attacks
} else {
    // Use default melee attack
}
```

---

## ARCHITECTURE OVERVIEW

### System Components

```
┌─────────────────────────────────────────────┐
│  ProjectileAnimationRegistry                │  ← Main API
│  (ProjectileAnimationRegistry.java)         │
└──────────────┬──────────────────────────────┘
               │
        ┌──────┴───────────────────────┐
        │                              │
        ▼                              ▼
┌──────────────────────┐   ┌──────────────────────────────┐
│ ProjectilePattern    │   │ ProjectileDefinition         │
│ enum (8 types)       │   │ (Immutable configuration)    │
└──────────────────────┘   └──────────────────────────────┘
                                       │
                            ┌──────────┴──────────┐
                            │                     │
                            ▼                     ▼
                  ┌─────────────────┐   ┌────────────────────┐
                  │ AnimationLoader │   │ HorizontalSpritesheet
                  │ Integration     │   │ Loader             │
                  └─────────────────┘   └────────────────────┘
```

### Data Flow

```
1. System Startup
   └─> ProjectileAnimationRegistry.initializeRegistry()
       └─> Hardcoded projectile definitions registered
           └─> Internal HashMap indexed by character & type

2. Query at Runtime
   └─> getProjectilesFor("RugbyGuy")
       └─> Lookup characterProjectiles["RugbyGuy"]
           └─> Return List<ProjectileDefinition>

3. Load Animation
   └─> loadProjectile("RugbyGuy_RugbyBall")
       └─> Get ProjectileDefinition from registry
           └─> Call AnimationAndSpriteLoader.load(filePath)
               └─> Return HorizontalSpritesheetLoader
```

---

## CORE CONCEPTS

### 1. ProjectilePattern Enum

Defines the animation and physics pattern for a projectile:

```java
public enum ProjectilePattern {
    SINGLE_SPRITE,          // 1 frame, static
    SIMPLE_ANIMATION,       // 2-4 frames linear
    LOOPING_ANIMATION,      // Infinite loop (particles, glow)
    BURST_ATTACK,          // Multiple projectiles pattern
    HOMING_PROJECTILE,     // Auto-targeting trajectory
    AREA_EFFECT,           // Blast/explosion radius
    BEAM_RAY,              // Continuous energy beam
    PARTICLE_EFFECT        // Explosion/dispersal effect
}
```

**Usage:**
```java
ProjectileDefinition def = ProjectileAnimationRegistry.getProjectile("Drone1", "bomb");
if (def.pattern == ProjectilePattern.SIMPLE_ANIMATION) {
    // Use sprite sheet animation renderer
}
```

### 2. ProjectileDefinition Class

Immutable data class containing all projectile metadata:

```java
public static class ProjectileDefinition {
    public String projectileId;           // "RugbyGuy_RugbyBall"
    public String sourceName;             // "RugbyGuy"
    public String projectileType;         // "ball"
    public ProjectilePattern pattern;     // SINGLE_SPRITE
    public String filePath;               // Full file path
    public int frameCount;                // 1
    public int frameTimingMs;             // 0 (or 50-100 for animated)
    public int spriteWidth;               // 32
    public int spriteHeight;              // 32
    public boolean looping;               // false
    public String description;            // "Rugby ball throw..."
}
```

### 3. Registry Indexing

Projectiles are indexed TWO ways for fast lookup:

**By Projectile ID:**
```java
// Direct lookup: O(1)
projectileRegistry.get("RugbyGuy_RugbyBall")
```

**By Character:**
```java
// All projectiles for character: O(1)
characterProjectiles.get("RugbyGuy")
// Returns: List<ProjectileDefinition>
```

---

## API REFERENCE

### Registry Initialization

#### `initializeRegistry()`
```java
public static void initializeRegistry()
```
Initialize the registry with all hardcoded projectiles. **Call once at startup.**

**Usage:**
```java
ProjectileAnimationRegistry.initializeRegistry();
```

### Query Methods

#### `getProjectile(String projectileId)`
```java
public static ProjectileDefinition getProjectile(String projectileId)
```
Get a projectile by its unique ID.

**Parameters:**
- `projectileId`: Unique identifier (e.g., "RugbyGuy_RugbyBall")

**Returns:** ProjectileDefinition or null

**Example:**
```java
ProjectileDefinition def = ProjectileAnimationRegistry.getProjectile("RugbyGuy_RugbyBall");
System.out.println(def.description);  // "Ruby ball throw - single frame projectile"
```

#### `getProjectile(String characterName, String attackType)`
```java
public static ProjectileDefinition getProjectile(String characterName, String attackType)
```
Get a projectile by character and attack type.

**Parameters:**
- `characterName`: Name of character (e.g., "RugbyGuy", "Drone1")
- `attackType`: Type of attack (e.g., "projectile", "bomb", "ball")

**Returns:** ProjectileDefinition or null

**Example:**
```java
ProjectileDefinition def = 
    ProjectileAnimationRegistry.getProjectile("Drone1", "bomb");
if (def != null) {
    int animTime = def.getTotalDurationMs();
    System.out.println("Animation duration: " + animTime + "ms");
}
```

#### `getProjectilesFor(String characterName)`
```java
public static List<ProjectileDefinition> getProjectilesFor(String characterName)
```
Get all projectiles for a specific character.

**Parameters:**
- `characterName`: Character to query

**Returns:** List<ProjectileDefinition> (empty if none)

**Example:**
```java
List<ProjectileDefinition> projectiles = 
    ProjectileAnimationRegistry.getProjectilesFor("SciFi3");

System.out.println("Sci-Fi 3 has " + projectiles.size() + " projectile types:");
for (ProjectileDefinition def : projectiles) {
    System.out.println("  • " + def.projectileType + " (" + def.pattern + ")");
}
// Output:
//   • orb (HOMING_PROJECTILE)
//   • energy (LOOPING_ANIMATION)
```

#### `getProjectilesByType(String projectileType)`
```java
public static List<ProjectileDefinition> getProjectilesByType(String projectileType)
```
Get all projectiles of a specific type across all characters.

**Parameters:**
- `projectileType`: Type to search for (e.g., "bomb", "orb", "bullet")

**Returns:** List<ProjectileDefinition>

**Example:**
```java
List<ProjectileDefinition> bombs = 
    ProjectileAnimationRegistry.getProjectilesByType("bomb");

if (!bombs.isEmpty()) {
    System.out.println("Found " + bombs.size() + " bomb-type projectiles");
}
```

#### `getProjectilesByPattern(ProjectilePattern pattern)`
```java
public static List<ProjectileDefinition> getProjectilesByPattern(ProjectilePattern pattern)
```
Get all projectiles using a specific animation pattern.

**Parameters:**
- `pattern`: ProjectilePattern to match

**Returns:** List<ProjectileDefinition>

**Example:**
```java
List<ProjectileDefinition> singleFrames = 
    ProjectileAnimationRegistry.getProjectilesByPattern(
        ProjectilePattern.SINGLE_SPRITE);

System.out.println("Static projectiles (no animation): " + singleFrames.size());
```

#### `getAllProjectiles()`
```java
public static Collection<ProjectileDefinition> getAllProjectiles()
```
Get all registered projectiles.

**Returns:** Collection of all ProjectileDefinition

**Example:**
```java
for (ProjectileDefinition def : ProjectileAnimationRegistry.getAllProjectiles()) {
    System.out.println(def.projectileId + ": " + def.description);
}
```

#### `getCharactersWithProjectiles()`
```java
public static Set<String> getCharactersWithProjectiles()
```
Get names of all characters that have projectiles.

**Returns:** Set<String> of character names

**Example:**
```java
Set<String> characters = ProjectileAnimationRegistry.getCharactersWithProjectiles();
System.out.println("Characters with projectiles: " + characters);
// Output: Characters with projectiles: [RugbyGuy, Drone1, SciFi3, Weapon, ...]
```

#### `hasProjectiles(String characterName)`
```java
public static boolean hasProjectiles(String characterName)
```
Check if a character has any projectiles.

**Parameters:**
- `characterName`: Character to check

**Returns:** true if character has projectiles, false otherwise

**Example:**
```java
if (ProjectileAnimationRegistry.hasProjectiles("GreenMech")) {
    // Render projectile attack
} else {
    // Use melee-only attack pattern
}
```

### Loader Methods

#### `loadProjectile(String projectileId)`
```java
public static HorizontalSpritesheetLoader loadProjectile(String projectileId)
```
Load a projectile animation by ID.

**Parameters:**
- `projectileId`: Projectile ID to load

**Returns:** HorizontalSpritesheetLoader or null if not found

**Example:**
```java
HorizontalSpritesheetLoader loader = 
    ProjectileAnimationRegistry.loadProjectile("RugbyGuy_RugbyBall");

if (loader != null) {
    BufferedImage frame = loader.getFrame(0);
    // Render frame at projectile position
}
```

#### `loadProjectile(String characterName, String attackType)`
```java
public static HorizontalSpritesheetLoader loadProjectile(String characterName, String attackType)
```
Load projectile animation by character and attack type.

**Parameters:**
- `characterName`: Character name
- `attackType`: Attack type

**Returns:** HorizontalSpritesheetLoader or null if not found

**Example:**
```java
HorizontalSpritesheetLoader loader = 
    ProjectileAnimationRegistry.loadProjectile("Drone4", "capsule");

// Use loader to animate projectile during flight
```

### Statistics & Debugging

#### `getStatistics()`
```java
public static String getStatistics()
```
Get comprehensive statistics about the registry.

**Returns:** Formatted statistics string

**Example:**
```java
System.out.println(ProjectileAnimationRegistry.getStatistics());
```

**Output:**
```
═══════════════════════════════════════════
  PROJECTILE REGISTRY STATISTICS
═══════════════════════════════════════════
  Total Projectiles: 24
  Characters with Projectiles: 8

  By Pattern:
    • SINGLE_SPRITE: 15
    • SIMPLE_ANIMATION: 5
    • LOOPING_ANIMATION: 3
    • HOMING_PROJECTILE: 1

  By Character:
    • Drone1: 1
    • Drone4: 1
    • Drone6: 1
    • Punk: 1
    • RugbyGuy: 1
    • SciFi2: 1
    • SciFi3: 2
    • Weapon: 11
═══════════════════════════════════════════
```

#### `getProjectileCount()`
```java
public static int getProjectileCount()
```
Get total number of registered projectiles.

**Returns:** Integer count

**Example:**
```java
int total = ProjectileAnimationRegistry.getProjectileCount();
System.out.println("Total projectiles: " + total);  // Total projectiles: 24
```

#### `clear()`
```java
public static void clear()
```
Clear all registered projectiles (for reloading or testing).

**Usage:**
```java
ProjectileAnimationRegistry.clear();
ProjectileAnimationRegistry.initializeRegistry();  // Reload
```

---

## CODE EXAMPLES

### Example 1: Display All Projectiles in Console

```java
public static void listAllProjectiles() {
    ProjectileAnimationRegistry.initializeRegistry();
    
    System.out.println(ProjectileAnimationRegistry.getStatistics());
    
    for (ProjectileDefinition def : ProjectileAnimationRegistry.getAllProjectiles()) {
        System.out.println(String.format(
            "%-30s | %-15s | %s",
            def.projectileId,
            def.pattern.name(),
            def.description
        ));
    }
}
```

### Example 2: Preview Projectile Animation

```java
public class ProjectilePreview {
    
    public static void previewProjectile(String characterName, String attackType) {
        ProjectileDefinition def = 
            ProjectileAnimationRegistry.getProjectile(characterName, attackType);
        
        if (def == null) {
            System.out.println("❌ No projectile found for " + characterName + "/" + attackType);
            return;
        }
        
        System.out.println("✓ Found projectile: " + def.projectileId);
        System.out.println("  Source: " + def.sourceName);
        System.out.println("  Type: " + def.projectileType);
        System.out.println("  Pattern: " + def.pattern.name());
        System.out.println("  Frames: " + def.frameCount);
        System.out.println("  Timing: " + def.frameTimingMs + "ms per frame");
        System.out.println("  Duration: " + def.getTotalDurationMs() + "ms");
        System.out.println("  Sprite: " + def.spriteWidth + "x" + def.spriteHeight + "px");
        System.out.println("  Description: " + def.description);
        System.out.println("  File: " + def.fileName);
        
        // Load animation
        HorizontalSpritesheetLoader loader = 
            ProjectileAnimationRegistry.loadProjectile(characterName, attackType);
        
        if (loader != null) {
            System.out.println("✓ Animation loaded successfully");
            BufferedImage frame = loader.getFrame(0);
            if (frame != null) {
                System.out.println("  Frame size: " + frame.getWidth() + "x" + frame.getHeight());
            }
        } else {
            System.out.println("❌ Failed to load animation");
        }
    }
}

// Usage:
ProjectilePreview.previewProjectile("RugbyGuy", "projectile");
ProjectilePreview.previewProjectile("Drone1", "bomb");
```

### Example 3: Render Projectile in Game

```java
public class ProjectileRenderer {
    
    private HorizontalSpritesheetLoader loader;
    private int frameIndex = 0;
    private int frameTimer = 0;
    private ProjectileDefinition definition;
    
    public ProjectileRenderer(String characterName, String attackType) {
        this.definition = 
            ProjectileAnimationRegistry.getProjectile(characterName, attackType);
        
        if (definition != null) {
            this.loader = 
                ProjectileAnimationRegistry.loadProjectile(characterName, attackType);
        }
    }
    
    public void update(int deltaMs) {
        if (definition == null || loader == null) return;
        
        // For single-sprite projectiles, no animation needed
        if (definition.frameCount == 1) return;
        
        frameTimer += deltaMs;
        if (frameTimer >= definition.frameTimingMs) {
            frameTimer = 0;
            frameIndex++;
            
            // Handle looping vs one-shot
            if (definition.looping) {
                frameIndex %= definition.frameCount;
            } else if (frameIndex >= definition.frameCount) {
                // Animation finished
                frameIndex = definition.frameCount - 1;
            }
        }
    }
    
    public void render(Graphics2D g, int x, int y) {
        if (loader == null) return;
        
        BufferedImage frame = loader.getFrame(frameIndex);
        if (frame != null) {
            g.drawImage(frame, x, y, null);
        }
    }
}

// Usage in game:
ProjectileRenderer renderer = new ProjectileRenderer("Drone1", "bomb");
// In update loop:
renderer.update(deltaTime);
// In render loop:
renderer.render(g, projectileX, projectileY);
```

### Example 4: Batch Load All Character Projectiles

```java
public class CharacterProjectileLoader {
    
    public static Map<String, HorizontalSpritesheetLoader> 
            loadAllProjectilesFor(String characterName) {
        
        Map<String, HorizontalSpritesheetLoader> loaded = new HashMap<>();
        
        List<ProjectileDefinition> projectiles = 
            ProjectileAnimationRegistry.getProjectilesFor(characterName);
        
        for (ProjectileDefinition def : projectiles) {
            HorizontalSpritesheetLoader loader = 
                ProjectileAnimationRegistry.loadProjectile(def.projectileId);
            
            if (loader != null) {
                loaded.put(def.projectileType, loader);
                System.out.println("✓ Loaded: " + def.projectileType);
            }
        }
        
        return loaded;
    }
}

// Usage:
Map<String, HorizontalSpritesheetLoader> droneProjectiles = 
    CharacterProjectileLoader.loadAllProjectilesFor("Drone1");

HorizontalSpritesheetLoader bombAnimation = droneProjectiles.get("bomb");
```

---

## INTEGRATION GUIDE

### Step 1: Initialize at Game Startup

```java
public class Game extends JPanel {
    
    public Game() {
        // Initialize subsystems
        ProjectileAnimationRegistry.initializeRegistry();
        
        // Verify loading
        System.out.println(ProjectileAnimationRegistry.getStatistics());
    }
}
```

### Step 2: Add Projectile Checks to AI/Combat System

```java
public class EnemyAI {
    
    private String enemyName;
    
    public void selectAttack() {
        if (ProjectileAnimationRegistry.hasProjectiles(enemyName)) {
            // Use projectile attack
            List<ProjectileDefinition> projectiles = 
                ProjectileAnimationRegistry.getProjectilesFor(enemyName);
            
            ProjectileDefinition proj = projectiles.get(0);
            fireProjectile(proj);
        } else {
            // Fall back to melee
            performMeleeAttack();
        }
    }
    
    private void fireProjectile(ProjectileDefinition def) {
        HorizontalSpritesheetLoader loader = 
            ProjectileAnimationRegistry.loadProjectile(def.projectileId);
        
        // Create projectile entity with animation
    }
}
```

### Step 3: Use in UI/Debugger

```java
public class DebugProjectilePanel extends JPanel {
    
    public DebugProjectilePanel() {
        ProjectileAnimationRegistry.initializeRegistry();
        
        // List all characters
        for (String character : ProjectileAnimationRegistry.getCharactersWithProjectiles()) {
            // Add buttons to preview projectiles
        }
    }
}
```

---

## TROUBLESHOOTING

### Problem: Registry shows 0 projectiles

**Solution:** Make sure to call `initializeRegistry()` before querying:
```java
ProjectileAnimationRegistry.initializeRegistry();  // Run FIRST
List<ProjectileDefinition> list = ProjectileAnimationRegistry.getAllProjectiles();
```

### Problem: Projectile definition not found

**Solution:** Check the exact character name:
```java
// Check available characters
Set<String> chars = ProjectileAnimationRegistry.getCharactersWithProjectiles();
System.out.println("Available: " + chars);

// Try exact match
ProjectileDefinition def = 
    ProjectileAnimationRegistry.getProjectile("RugbyGuy", "projectile");
```

### Problem: Animation doesn't play smoothly

**Solution:** Check frame count and timing:
```java
ProjectileDefinition def = ProjectileAnimationRegistry.getProjectile(...);

System.out.println("Frames: " + def.frameCount);      // Should be > 1
System.out.println("Timing: " + def.frameTimingMs);   // Should be 50-120ms
System.out.println("Looping: " + def.looping);        // Check if should loop
```

### Problem: File not found error

**Solution:** Verify file paths:
```java
for (ProjectileDefinition def : ProjectileAnimationRegistry.getAllProjectiles()) {
    File f = new File(def.filePath);
    if (!f.exists()) {
        System.out.println("❌ Missing: " + def.filePath);
    }
}
```

---

## FILE STRUCTURE

### Code Files
```
handout/src/
├── animation/
│   ├── AnimationAndSpriteLoader.java
│   ├── ProjectileAnimationRegistry.java      ← NEW
│   └── HorizontalSpritesheetLoader extends AssetType
└── Test_ProjectileAnimationRegistry.java     ← NEW (test suite)
```

### Asset Files
```
Resources/industrial-zone/
├── characters/
│   ├── bosses/
│   │   └── RugbyGuy/
│   │       └── 03_Boss_RugbyGuy_Projectile_*.png
│   ├── player/
│   │   └── punk/
│   │       └── 15_Player_Punk_Attack3_*.png
│   └── enemies/
│       ├── drones/
│       │   ├── 1/ → 02_Drone_JetDrone_Bomb_*.png
│       │   ├── 4/ → 04_Drone_HoverPlatform_Capsule_*.png
│       │   └── 6/ → 04_EnemyDrone_HoverPlatform_Capsule_*.png
│       └── sci-fi-antagonists/
│           ├── 2/ → 08_Enemy_ArmouredKnight_Projectile_*.png
│           └── 3/ → 04_Enemy_WingedWarrior_Attack2_*.png
│               → 09_Enemy_WingedWarrior_Projectile_*.png
└── weapons/
    └── 1/5 Bullets/
        └── 01-13_Bullet_Type*.png
```

### Documentation Files
```
handout/
├── PROJECTILE_SYSTEM_UPGRADE_ANALYSIS.md     ← Comprehensive analysis
├── PROJECTILE_ANIMATION_API.md               ← This document
└── PROJECTILE_IMPLEMENTATION_GUIDE.md        ← Step-by-step guide
```

---

## SUMMARY

The **ProjectileAnimationRegistry** provides:

✅ **Zero per-character hardcoding** - All projectiles in one registry  
✅ **Unified API** - Get any projectile with consistent interface  
✅ **Type-safe definitions** - ProjectileDefinition immutable class  
✅ **8 animation patterns** - Supports all projectile types  
✅ **Fast lookups** - O(1) by character or projectile ID  
✅ **Statistics & debugging** - Full introspection API  
✅ **Extensible architecture** - Easy to add new projectiles

Use this system for:
- Character projectile attacks
- Enemy AI projectile selection
- Weapon impact effects
- UI projectile preview
- Testing and debugging
- Balancing projectile behavior

