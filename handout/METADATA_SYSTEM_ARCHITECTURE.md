# ⚙️ METADATA SYSTEM ARCHITECTURE

## 🎯 Executive Summary

The metadata system provides intelligent, automatic analysis of game assets to extract:
- Frame counts from filenames and image dimensions
- Animation timing information
- Asset categories and types
- Spritesheet grid dimensions
- Resource dependencies and properties

**Goal**: Minimize manual configuration, maximize auto-detection

**Result**: Asset loaders can load with minimal parameters, letting metadata handle defaults

---

## 📊 METADATA CLASSES HIERARCHY

```
MetadataExtractor (Static utility - image analysis)
├── SpriteMetadata (Immutable data class - analysis results)
│   ├── width, height (pixels)
│   ├── estimatedFrameCount (auto-detected)
│   ├── complexity (LOW/MEDIUM/HIGH)
│   ├── suggestedFrameDelay (milliseconds)
│   └── analysisReport (human readable)
│
├── SpritesheetMetadata (Grid-specific metadata)
│   ├── rows, cols (grid dimensions)
│   ├── frameWidth, frameHeight (per frame)
│   ├── totalFrames (rows × cols)
│   └── frameTiming (array of delays)
│
├── TileMetadata (Tile-specific metadata)
│   ├── solid (collision)
│   ├── hazard (damage)
│   ├── friction (physics)
│   ├── physicsType (static/dynamic/trigger)
│   └── tileID (registry mapping)
│
├── CharacterMetadata (Character asset metadata)
│   ├── characterType (player/enemy/boss)
│   ├── availableAnimations (walk, run, jump, etc.)
│   ├── animationFrameCounts (map of state → frame count)
│   └── animationTiming (map of state → timing in ms)
│
└── WeaponMetadata (Weapon asset metadata)
    ├── weaponType (melee/ranged/projectile)
    ├── damage (base damage value)
    ├── projectileSpeed (pixels/second)
    ├── effectFrameCounts (impact, muzzle flash)
    └── soundEffectPath (audio asset reference)
```

---

## 🔍 CLASS 1: MetadataExtractor (Static Utility)

**Purpose**: Static utility methods for analyzing image files and filenames

**Location**: `handout/src/animation/metadata/MetadataExtractor.java`

```java
/**
 * Static utility for extracting metadata from asset files
 * NO INSTANTIATION - Use static methods directly
 */
public class MetadataExtractor {
    
    // ════════════════════════════════════════════════════════════════════════════════
    // IMAGE ANALYSIS METHODS
    // ════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Analyze image file and return comprehensive metadata
     * 
     * @param filePath Full path to image file
     * @return SpriteMetadata with dimensions, frame count, complexity
     */
    public static SpriteMetadata analyzeImage(String filePath) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            if (image == null) {
                return SpriteMetadata.createNull(filePath);
            }
            
            int width = image.getWidth();
            int height = image.getHeight();
            
            // Auto-detect if horizontal or vertical strip
            int estimatedFrames;
            boolean isVerticalStrip;
            
            if (width > height) {
                // Horizontal strip: square frame width = height
                estimatedFrames = width / height;
                isVerticalStrip = false;
            } else {
                // Vertical strip: square frame height = width
                estimatedFrames = height / width;
                isVerticalStrip = true;
            }
            
            // Analyze pixel complexity
            String complexity = analyzeComplexity(image);
            
            // Suggest timing
            int suggestedDelay = suggestFrameDelay(complexity);
            
            return new SpriteMetadata(
                filePath,
                width,
                height,
                estimatedFrames,
                isVerticalStrip,
                complexity,
                suggestedDelay
            );
            
        } catch (IOException e) {
            System.err.println("❌ Error analyzing image: " + filePath);
            return SpriteMetadata.createNull(filePath);
        }
    }
    
    /**
     * Analyze filename for metadata extraction
     * Detects patterns like: "walk_8frames.png", "buttons_4states_vertical.png"
     * 
     * @param filename Just filename (no path)
     * @return FilenameMetadata with detected frame count, orientation, etc.
     */
    public static FilenameMetadata analyzeFilename(String filename) {
        FilenameMetadata meta = new FilenameMetadata();
        meta.originalFilename = filename;
        
        // Extract frame count from patterns like "_8frames", "_8f"
        Pattern framePattern = Pattern.compile("_(\\d+)frames?", Pattern.CASE_INSENSITIVE);
        Matcher frameMatcher = framePattern.matcher(filename);
        if (frameMatcher.find()) {
            meta.frameCount = Integer.parseInt(frameMatcher.group(1));
            meta.frameCountDetected = true;
        }
        
        // Detect orientation: horizontal (default) or vertical
        boolean hasVertical = filename.toLowerCase().contains("vertical");
        boolean hasHorizontal = filename.toLowerCase().contains("horizontal");
        meta.isVertical = hasVertical && !hasHorizontal;
        
        // Detect grid layout: pattern like "2x4" or "grid_2_4"
        Pattern gridPattern = Pattern.compile("(\\d+)[x_](\\d+)");
        Matcher gridMatcher = gridPattern.matcher(filename);
        if (gridMatcher.find()) {
            meta.gridRows = Integer.parseInt(gridMatcher.group(1));
            meta.gridCols = Integer.parseInt(gridMatcher.group(2));
            meta.isGrid = true;
        }
        
        // Detect timing info: pattern like "100ms" or "timing_100"
        Pattern timingPattern = Pattern.compile("(\\d+)ms");
        Matcher timingMatcher = timingPattern.matcher(filename);
        if (timingMatcher.find()) {
            meta.frameDelay = Integer.parseInt(timingMatcher.group(1));
            meta.timingDetected = true;
        }
        
        // Category detection (for asset organization)
        if (filename.contains("walk") || filename.contains("run") || filename.contains("idle")) {
            meta.category = AssetCategory.CHARACTER_ANIMATION;
        } else if (filename.contains("button")) {
            meta.category = AssetCategory.UI_BUTTON;
        } else if (filename.contains("bar")) {
            meta.category = AssetCategory.UI_BAR;
        } else if (filename.contains("tile")) {
            meta.category = AssetCategory.TILE;
        } else {
            meta.category = AssetCategory.UNKNOWN;
        }
        
        return meta;
    }
    
    // ════════════════════════════════════════════════════════════════════════════════
    // COMPLEXITY ANALYSIS
    // ════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Analyze pixel color variation to estimate animation complexity
     * 
     * @param image BufferedImage to analyze
     * @return "LOW", "MEDIUM", or "HIGH" based on color variance
     */
    private static String analyzeComplexity(BufferedImage image) {
        Set<Integer> uniqueColors = new HashSet<>();
        int width = image.getWidth();
        int height = image.getHeight();
        
        // Sample every 4th pixel for performance
        for (int y = 0; y < height; y += 4) {
            for (int x = 0; x < width; x += 4) {
                int rgb = image.getRGB(x, y);
                uniqueColors.add(rgb);
            }
        }
        
        int colorCount = uniqueColors.size();
        int pixelSampleSize = (width / 4) * (height / 4);
        double colorDensity = (double) colorCount / pixelSampleSize;
        
        if (colorDensity < 0.3) {
            return "LOW";      // Simple, flat colors
        } else if (colorDensity < 0.7) {
            return "MEDIUM";    // Moderate detail
        } else {
            return "HIGH";      // Complex shading/gradients
        }
    }
    
    /**
     * Suggest reasonable frame delay based on animation complexity
     * @param complexity "LOW", "MEDIUM", or "HIGH"
     * @return Suggested milliseconds per frame
     */
    private static int suggestFrameDelay(String complexity) {
        switch (complexity) {
            case "LOW":
                return 150;     // Simple animations can be slower (6.7 fps)
            case "MEDIUM":
                return 100;     // Standard animation speed (10 fps)
            case "HIGH":
                return 80;      // Complex animations may need faster playback (12.5 fps)
            default:
                return 100;
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════
    // SPRITESHEET ANALYSIS
    // ════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Analyze grid-based spritesheet dimensions
     * @param filePath Path to spritesheet
     * @param rows Expected number of rows
     * @param cols Expected number of columns
     * @return SpritesheetMetadata with frame dimensions
     */
    public static SpritesheetMetadata analyzeGrid(String filePath, int rows, int cols) {
        try {
            BufferedImage sheet = ImageIO.read(new File(filePath));
            if (sheet == null) return null;
            
            int frameWidth = sheet.getWidth() / cols;
            int frameHeight = sheet.getHeight() / rows;
            int totalFrames = rows * cols;
            
            return new SpritesheetMetadata(
                filePath,
                rows,
                cols,
                frameWidth,
                frameHeight,
                totalFrames
            );
            
        } catch (IOException e) {
            System.err.println("❌ Error analyzing grid spritesheet: " + filePath);
            return null;
        }
    }
    
    /**
     * Validate that spritesheet dimensions are valid for given grid
     * @param width Image width
     * @param height Image height
     * @param rows Expected rows
     * @param cols Expected columns
     * @return true if dimensions divide evenly
     */
    public static boolean validateGridDimensions(int width, int height, int rows, int cols) {
        return (width % cols == 0) && (height % rows == 0);
    }
}
```

---

## 📦 CLASS 2: SpriteMetadata (Immutable Data Class)

**Purpose**: Store results of image analysis

```java
/**
 * Immutable metadata from image file analysis
 * USAGE: Auto-generated by MetadataExtractor.analyzeImage()
 */
public class SpriteMetadata {
    
    public final String filePath;
    public final int width;
    public final int height;
    public final int estimatedFrameCount;
    public final boolean isVerticalStrip;
    public final String complexity;  // "LOW", "MEDIUM", "HIGH"
    public final int suggestedFrameDelay;  // milliseconds
    public final boolean analysisSuccessful;
    
    // Public constructor for validation results
    public SpriteMetadata(
        String filePath,
        int width,
        int height,
        int estimatedFrameCount,
        boolean isVerticalStrip,
        String complexity,
        int suggestedFrameDelay
    ) {
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.estimatedFrameCount = estimatedFrameCount;
        this.isVerticalStrip = isVerticalStrip;
        this.complexity = complexity;
        this.suggestedFrameDelay = suggestedFrameDelay;
        this.analysisSuccessful = true;
    }
    
    /**
     * Create failure metadata (couldn't load file)
     */
    public static SpriteMetadata createNull(String filePath) {
        SpriteMetadata meta = new SpriteMetadata(filePath, 0, 0, 0, false, "UNKNOWN", 100);
        return meta;
    }
    
    /**
     * Comprehensive analysis report
     */
    @Override
    public String toString() {
        if (!analysisSuccessful) {
            return String.format(
                "════════════════════════════════════════════\n" +
                "SPRITE METADATA ANALYSIS (FAILED)\n" +
                "File: %s\n" +
                "Status: File not found or unreadable\n" +
                "════════════════════════════════════════════",
                filePath
            );
        }
        
        String orientation = isVerticalStrip ? "VERTICAL" : "HORIZONTAL";
        return String.format(
            "════════════════════════════════════════════\n" +
            "SPRITE METADATA ANALYSIS\n" +
            "File: %s\n" +
            "Dimensions: %d × %d pixels\n" +
            "Orientation: %s (%d frames)\n" +
            "Complexity: %s\n" +
            "Suggested Frame Delay: %d ms per frame\n" +
            "════════════════════════════════════════════",
            new File(filePath).getName(),
            width, height,
            orientation, estimatedFrameCount,
            complexity,
            suggestedFrameDelay
        );
    }
}
```

---

## 📋 CLASS 3: TileMetadata (Tile-Specific Metadata)

**Purpose**: Store tile properties for physics and gameplay

```java
/**
 * Metadata for tile assets - physics, collision, hazard properties
 */
public class TileMetadata {
    
    public enum PhysicsType {
        STATIC,      // Doesn't move (platforms, walls)
        DYNAMIC,     // Can be moved by forces (pushable blocks)
        TRIGGER,     // Collision triggers but no blocking (destructibles)
        SENSOR       // Detects collision but doesn't block (checkpoints)
    }
    
    public enum TileCategory {
        PLATFORM,    // Solid walkable surface
        WALL,        // Vertical barrier
        HAZARD,      // Contact damage or destruction
        CORNER,      // Transition piece
        DECORATION,  // Visual only, no collision
        INTERACTIVE  // Door, switch, chest
    }
    
    // Asset identification
    public final int tileID;
    public final String tileName;
    public final String assetPath;
    
    // Visual properties
    public final int width;   // pixels
    public final int height;  // pixels
    
    // Physics properties
    public final boolean solid;          // Blocks movement?
    public final boolean hazard;         // Deals damage?
    public final int hazardDamage;       // Damage per frame contact
    public final float friction;         // Physics friction (0.0 - 1.0)
    public final float bounciness;       // Physics bounce (0.0 - 1.0)
    public final PhysicsType physicsType;
    
    // Tile properties
    public final TileCategory category;
    public final boolean breakable;      // Can be destroyed?
    public final int breakHealth;        // Hit points before destruction
    
    // Animation (if tile is animated)
    public final boolean animated;
    public final int frameCount;         // 0 if not animated
    public final int animationDelay;     // milliseconds per frame
    
    // Constructor for creating tile metadata
    public TileMetadata(
        int tileID,
        String tileName,
        String assetPath,
        int width,
        int height,
        boolean solid,
        boolean hazard,
        int hazardDamage,
        float friction,
        float bounciness,
        PhysicsType physicsType,
        TileCategory category,
        boolean breakable,
        int breakHealth,
        boolean animated,
        int frameCount,
        int animationDelay
    ) {
        this.tileID = tileID;
        this.tileName = tileName;
        this.assetPath = assetPath;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.hazard = hazard;
        this.hazardDamage = hazardDamage;
        this.friction = friction;
        this.bounciness = bounciness;
        this.physicsType = physicsType;
        this.category = category;
        this.breakable = breakable;
        this.breakHealth = breakHealth;
        this.animated = animated;
        this.frameCount = frameCount;
        this.animationDelay = animationDelay;
    }
    
    @Override
    public String toString() {
        return String.format(
            "Tile[ID=%d, name=%s, solid=%b, hazard=%b, category=%s]",
            tileID, tileName, solid, hazard, category
        );
    }
}
```

---

## 🎮 CLASS 4: CharacterMetadata (Character-Specific Metadata)

**Purpose**: Store character animation and property data

```java
/**
 * Metadata for character assets - animations, health, movement, abilities
 */
public class CharacterMetadata {
    
    public enum CharacterType {
        PLAYER,
        ENEMY,
        BOSS,
        NPC
    }
    
    public enum CharacterSize {
        SMALL,    // ~32×32 pixels
        MEDIUM,   // ~64×64 pixels
        LARGE,    // ~128×128 pixels
        BOSS      // 256×256+ pixels
    }
    
    // Identity
    public final String characterName;
    public final CharacterType characterType;
    public final CharacterSize size;
    
    // Physical properties
    public final int maxHealth;
    public final float moveSpeed;           // pixels/second
    public final float jumpForce;           // physics units
    public final float collisionWidth;
    public final float collisionHeight;
    
    // Animation mapping: state → metadata
    public final Map<String, AnimationMetadata> animations;
    
    // Available abilities
    public final List<String> availableAbilities;  // ["attack", "jump", "dash", etc.]
    
    // Combat properties (if applicable)
    public final int attackDamage;
    public final float attackSpeed;         // attacks per second
    public final int attackRange;          // pixels
    
    // AI properties (for enemies)
    public final float visionRange;        // pixels
    public final float visionAngle;        // degrees
    public final float patrolSpeed;
    public final float chaseSpeed;
    
    /**
     * Nested class for single animation metadata
     */
    public static class AnimationMetadata {
        public final String stateName;     // "walk", "run", "attack", etc.
        public final String assetPath;
        public final int frameCount;
        public final int frameDelay;       // milliseconds per frame
        public final boolean looping;
        public final float speed;          // multiplier (1.0 = normal)
        
        public AnimationMetadata(
            String stateName,
            String assetPath,
            int frameCount,
            int frameDelay,
            boolean looping,
            float speed
        ) {
            this.stateName = stateName;
            this.assetPath = assetPath;
            this.frameCount = frameCount;
            this.frameDelay = frameDelay;
            this.looping = looping;
            this.speed = speed;
        }
    }
    
    // Constructor
    public CharacterMetadata(
        String characterName,
        CharacterType characterType,
        CharacterSize size,
        int maxHealth,
        float moveSpeed,
        float jumpForce,
        float collisionWidth,
        float collisionHeight
    ) {
        this.characterName = characterName;
        this.characterType = characterType;
        this.size = size;
        this.maxHealth = maxHealth;
        this.moveSpeed = moveSpeed;
        this.jumpForce = jumpForce;
        this.collisionWidth = collisionWidth;
        this.collisionHeight = collisionHeight;
        this.animations = new HashMap<>();
        this.availableAbilities = new ArrayList<>();
        this.attackDamage = 10;
        this.attackSpeed = 1.0f;
        this.attackRange = 32;
        this.visionRange = 200;
        this.visionAngle = 90;
        this.patrolSpeed = 50;
        this.chaseSpeed = 150;
    }
    
    /**
     * Register animation for this character
     */
    public void addAnimation(AnimationMetadata anim) {
        animations.put(anim.stateName, anim);
    }
    
    /**
     * Get animation metadata by state
     */
    public AnimationMetadata getAnimation(String state) {
        return animations.getOrDefault(state, null);
    }
}
```

---

## 🎯 INTEGRATION PATTERN: Using Metadata in Loaders

**This shows how metadata flows from extraction → storage → usage:**

```java
/**
 * PATTERN: How to use metadata when loading assets
 * FOLLOW THIS PATTERN FOR ALL LOADERS
 */

// STEP 1: Analyze metadata
String assetPath = "Resources/industrial-zone/characters/player/walk_8frames.png";
SpriteMetadata imageMeta = MetadataExtractor.analyzeImage(assetPath);
FilenameMetadata filenameMeta = MetadataExtractor.analyzeFilename(new File(assetPath).getName());

// STEP 2: Prefer filename detection if available, fall back to image analysis
int frameCount = filenameMeta.frameCountDetected ? filenameMeta.frameCount : imageMeta.estimatedFrameCount;
int frameDelay = filenameMeta.timingDetected ? filenameMeta.frameDelay : imageMeta.suggestedFrameDelay;

// STEP 3: Load with automatically detected parameters
HorizontalSpritesheetLoader loader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
    "player_walk",
    assetPath,
    0, 0, 0  // offsets are minimized with good metadata
);

// STEP 4: Verify with actual load
if (loader.load()) {
    // VALIDATE: Metadata matches actual load
    if (loader.getFrameCount() != frameCount) {
        System.out.println("⚠️  WARNING: Frame count mismatch");
        System.out.println("   Metadata: " + frameCount);
        System.out.println("   Actual: " + loader.getFrameCount());
    }
    
    // REPORT: Show what was loaded
    System.out.println("✓ Loaded: " + assetPath);
    System.out.println("  Frames: " + loader.getFrameCount());
    System.out.println("  Timing: " + frameDelay + "ms per frame");
    System.out.println("  Complexity: " + imageMeta.complexity);
    
} else {
    System.out.println("❌ FAILED TO LOAD: " + assetPath);
    System.out.println("   Full Path: " + new File(assetPath).getAbsolutePath());
}

// STEP 5: Store using metadata for easy access
Object[][] animationCache = new Object[1][loader.getFrameCount()];
for (int i = 0; i < loader.getFrameCount(); i++) {
    animationCache[0][i] = loader.getFrame(i);
}
```

---

## 📋 IMPLEMENTATION CHECKLIST

- [ ] Create `metadata/` package under src/animation/
- [ ] Create `MetadataExtractor.java` with all static utility methods
- [ ] Create `SpriteMetadata.java` with analysis data
- [ ] Create `FilenameMetadata.java` for filename parsing results
- [ ] Create `SpritesheetMetadata.java` for grid analysis
- [ ] Create `TileMetadata.java` for tile-specific data
- [ ] Create `CharacterMetadata.java` for character data
- [ ] Create `WeaponMetadata.java` for weapon data
- [ ] Integrate metadata into all loader classes
- [ ] Test filename detection patterns
- [ ] Test image analysis (complexity, frame count)
- [ ] Document metadata format standards
- [ ] Create metadata validation utilities

---

## 🎓 EXAMPLE: Complete Loader with Metadata

```java
/**
 * Example loader that demonstrates proper metadata usage
 */
public class PlayerWalkAnimationLoader {
    
    private HorizontalSpritesheetLoader loader;
    private SpriteMetadata imageMeta;
    private CharacterMetadata.AnimationMetadata animMeta;
    
    public boolean loadPlayerWalk(String characterName) {
        String assetPath = String.format(
            "Resources/industrial-zone/characters/player/%s/walk_8frames.png",
            characterName
        );
        
        // Analyze
        imageMeta = MetadataExtractor.analyzeImage(assetPath);
        if (!imageMeta.analysisSuccessful) {
            System.err.println("❌ Analysis failed: " + assetPath);
            return false;
        }
        
        // Create and load
        loader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(
            characterName + "_walk",
            assetPath,
            0, 0, 0
        );
        
        if (!loader.load()) {
            System.err.println("❌ Load failed: " + assetPath);
            return false;
        }
        
        // Store metadata
        animMeta = new CharacterMetadata.AnimationMetadata(
            "walk",
            assetPath,
            loader.getFrameCount(),
            imageMeta.suggestedFrameDelay,
            true,  // looping
            1.0f   // speed
        );
        
        // Report
        System.out.println("✓ Loaded: " + characterName + " walk animation");
        System.out.println(imageMeta);
        
        return true;
    }
    
    public SpriteMetadata getImageMetadata() {
        return imageMeta;
    }
    
    public BufferedImage getFrame(int index) {
        return loader.getFrame(index);
    }
    
    public int getFrameCount() {
        return loader.getFrameCount();
    }
}
```

---

End of Metadata System Architecture Document
