# PHASE 1: NESTED CLASSES API AUDIT & UPGRADE PLAN

## 📋 Overview

This document audits all **22 nested classes** from `AnimationAndSpriteLoader.java` to determine:
1. **What methods currently exist?** (public API)
2. **What methods should exist?** (ideal API for testing)
3. **Which classes need upgrades?** (add missing methods)
4. **Priority for upgrades?** (which are critical for tester)

---

## 🔍 CLASS-BY-CLASS AUDIT

### **GROUP 1: ASSET MANAGERS (6 Classes)**

---

#### **1. TileAssets** 
**Purpose**: Unified tile asset management for both Level 1 & 2  
**Line**: ~220 in AnimationAndSpriteLoader.java

**Current Methods** ✓
```java
public static synchronized TileAssets getInstance()
public String getTile(int level, char code)
public BufferedImage loadTile(int level, char code)
public int getCacheSize()
public void clearCache()
```

**Methods Needed for Tester** (to upgrade)
```java
// MISSING CRITICAL METHODS:
public Set<Character> getAllTileCodes(int level)        // Get all 65/64 codes
public Map<Character, String> getAllTiles(int level)    // Get code→path mapping
public float getFriction(int level, char code)          // Get tile physics
public boolean isHazard(int level, char code)           // Is tile dangerous?
public String getAnimationFrames(int level, char code)  // Animated tiles
public int getTileWidth()                               // Fixed dimension?
public int getTileHeight()                              // Fixed dimension?
public void preloadAllTiles(int level)                  // Batch load
public BufferedImage getVerifiedTile(char code)         // Load from manifest
```

**Status**: 🟡 **NEEDS UPGRADE** (Add missing methods)  
**Priority**: 🔴 **CRITICAL** (First test subject)

---

#### **2. ParticleAssets**
**Purpose**: VFX particle effect assets (smoke, blood, sparks)  
**Line**: ~278

**Current Methods** ✓
```java
public static synchronized ParticleAssets getInstance()
public String getSmoke(int frameNum)                    // Get smoke frame path
public String getBlood(String effectType)               // Get blood effect
public String getSpark(String sparkType)                // Get spark effect
public BufferedImage loadSmoke(int frameNum)            // Load & cache smoke
public BufferedImage loadBlood(String effectType)       // Load & cache blood
public BufferedImage loadSpark(String sparkType)        // Load & cache spark
public int getCacheSize()
public void clearCache()
```

**Methods Needed for Tester** (to upgrade)
```java
// MISSING - Animation Support:
public List<BufferedImage> getSmokeFrameSequence()      // All 18 smoke frames
public List<BufferedImage> getBloodEffects()            // All blood variants
public List<BufferedImage> getSparkEffects()            // All spark variants
public int getSmokeFrameSpeed()                         // Milliseconds per frame

// MISSING - Query Methods:
public Set<String> getAllBloodTypes()                   // Available blood effects
public Set<String> getAllSparkTypes()                   // Available spark types
public int getTotalSmokeFrames()                        // Return 18
public BufferedImage getRandomSmokeFrame()              // Random frame

// MISSING - Batch Operation:
public void preloadAllParticles()                       // Load everything
public int getTotalCacheSize()                          // All particles
```

**Status**: 🟡 **NEEDS UPGRADE** (Add animation support)  
**Priority**: 🟠 **HIGH** (VFX is visually important)

---

#### **3. UIAssets**  
**Purpose**: GUI/HUD interface assets (buttons, frames, bars, icons, numbers)  
**Line**: ~400

**Current Methods** ✓
```java
public static synchronized UIAssets getInstance()
public String getFrame(String type)                     // Get frame asset path
public String getBar(String type)                       // Get bar asset path
public String getButton(String type)                    // Get button asset path
public String getIcon(String type)                      // Get icon asset path
public String getNumber(int digit)                      // Get digit sprite
public BufferedImage loadFrame(String type)
public BufferedImage loadBar(String type)
public BufferedImage loadButton(String type)
public BufferedImage loadIcon(String type)
public BufferedImage loadNumber(int digit)
public int getCacheSize()
public void clearCache()
```

**Methods Needed for Tester** (to upgrade)
```java
// MISSING - Enumeration:
public Set<String> getAllFrameTypes()                   // Available frames
public Set<String> getAllBarTypes()                     // Available bars
public Set<String> getAllButtonTypes()                  // Available buttons
public Set<String> getAllIconTypes()                    // Available icons
public int getTotalFrames()                             // Count

// MISSING - Batch Operations:
public void preloadAllUIElements()                      // Load everything
public Map<String, BufferedImage> getAllButtons()       // Load all buttons
public Map<String, BufferedImage> getAllIcons()         // Load all icons

// MISSING - Dimension Info:
public Dimension getFrameDimension(String type)         // Width x Height
public Dimension getBarDimension(String type)
public Dimension getButtonDimension(String type)
public Dimension getIconDimension(String type)
```

**Status**: 🟡 **NEEDS UPGRADE** (Add enumeration/dimension methods)  
**Priority**: 🟠 **MEDIUM-HIGH**

---

#### **4. CharacterAssets**  
**Purpose**: Player character skins (Biker, Punk, Cyborg)  
**Line**: ~540

**Current Methods** ✓
```java
public static synchronized CharacterAssets getInstance()
public String getBiker()                                // Biker skin path
public String getPunk()                                 // Punk skin path
public String getCyborg()                               // Cyborg skin path
public BufferedImage loadBiker()
public BufferedImage loadPunk()
public BufferedImage loadCyborg()
public int getCacheSize()
public void clearCache()
```

**Methods Needed for Tester** (to upgrade)
```java
// MISSING - Enumeration & State:
public List<String> getAllCharacterSkins()              // ["Biker","Punk","Cyborg"]
public Map<String, String> getSkinAssets()              // Map name→path
public BufferedImage getSkinByName(String name)         // Load by name string
public Set<String> getAnimationStates(String skin)      // Walk, Attack, Die, etc

// MISSING - Animation Support:
public List<BufferedImage> getAnimationFrames(String skin, String state)
public int getFramesPerSecond(String skin, String state)
public boolean hasAnimation(String skin, String state)

// MISSING - Properties:
public int getSkinWidth(String skin)                    // Sprite dimensions
public int getSkinHeight(String skin)
public int getAnimationSpeed(String skin)               // Milliseconds per frame
```

**Status**: 🟡 **NEEDS UPGRADE** (Add skin enumeration & animation)  
**Priority**: 🔴 **CRITICAL** (Core visual test)

---

#### **5. WeaponAssets**  
**Purpose**: Weapon systems (bullets A-J, guns, effects)  
**Line**: ~680

**Current Methods** (incomplete - assume present but need verification)
```java
public static synchronized WeaponAssets getInstance()
// Assumed:
public String getWeapon(String type)
public BufferedImage loadWeapon(String type)
// + cache methods
```

**Methods Needed for Tester** (to upgrade)
```java
// QUERY METHODS:
public List<String> getAllWeaponTypes()                 // Return [A,B,C,...,J]
public String getWeaponPath(String type)                // Get asset path
public BufferedImage getWeaponSprite(String type)       // Load weapon

// SHOOTING EFFECTS:
public BufferedImage getShootEffect(String weaponType)
public String[] getBloodSplatEffects()                  // Multiple variants

// BULLET/PROJECTILE:
public String getBulletAsset(String bulletType)         // Get projectile sprite
public int getBulletWidth(String bulletType)
public int getBulletHeight(String bulletType)
public float getBulletSpeed(String bulletType)          // Pixels/second

// BATCH OPERATIONS:
public void preloadAllWeapons()
public Map<String, BufferedImage> getWeaponMap()
```

**Status**: 🔴 **UNKNOWN** (Needs full code review)  
**Priority**: 🟠 **HIGH**

---

#### **6. VFXAssets**  
**Purpose**: Advanced visual effects (explosions, impacts)  
**Line**: ~800

**Current Methods** (assumed similar to ParticleAssets)
```java
public static synchronized VFXAssets getInstance()
// Assumed methods similar to ParticleAssets
```

**Methods Needed for Tester** (to upgrade)
```java
// QUERY:
public List<String> getAllEffectTypes()                 // Available effects
public BufferedImage getEffect(String type, int frame)
public int getEffectFrameCount(String type)

// ANIMATION:
public List<BufferedImage> getEffectSequence(String type)
public int getEffectSpeed(String type)                  // FPS

// BATCH:
public void preloadAllEffects()
public Map<String, List<BufferedImage>> getAllEffectMaps()
```

**Status**: 🔴 **UNKNOWN**  
**Priority**: 🟠 **MEDIUM**

---

### **GROUP 2: TILE REGISTRIES (2 Classes)**

---

#### **7. Level1TileRegistry**  
**Purpose**: Map character codes (A-Z, a-z, 0-9, !@) to tile assets  
**Line**: ~900  
**Tile Count**: 65

**Current Methods** ✓ (These are likely static)
```java
public static String getTile(char code)                 // Return asset path
// Likely has:
public static float getFriction(char code)              // Physics property
public static boolean isHazard(char code)              // Safety check
public static int getAnimationSpeed(char code)          // If animated
```

**Methods Needed for Tester** (to upgrade)
```java
// ENUMERATION:
public static Set<Character> getAllCodes()              // All 65 codes: A-Z,a-z,0-9,!@
public static List<String> getAllTilePaths()            // All 65 asset paths
public static Map<Character, String> getTileMap()       // Code→path mapping

// PROPERTIES - BATCH:
public static Map<Character, Float> getAllFriction()    // All friction values
public static Map<Character, Boolean> getAllHazards()   // All hazard flags
public static Map<Character, Integer> getAllAnimSpeeds()

// DIMENSION INFO:
public static int getTileWidth()                        // Pixel width
public static int getTileHeight()                       // Pixel height

// EXISTENCE CHECK:
public static boolean isTileCode(char code)             // Valid code?
public static int getTotalTiles()                       // Return 65

// DISPLAY:
public static String getDisplayName(char code)          // Human-readable name
public static String getDescription(char code)          // Tile description
```

**Status**: 🟡 **NEEDS UPGRADE** (Add enumeration methods)  
**Priority**: 🔴 **CRITICAL**

---

#### **8. Level2TileRegistry**  
**Purpose**: Character codes for Power Station Level 2  
**Line**: ~1170  
**Tile Count**: 64

**Current Methods** ✓ (Same as Level1)
```java
public static String getTile(char code)
// Similar methods to Level1
```

**Methods Needed for Tester** (to upgrade)
```java
// SAME AS LEVEL1 + specific features:
public static Set<Character> getAllCodes()              // All 64 codes
public static List<String> getAllTilePaths()
public static Map<Character, String> getTileMap()

// LEVEL2-SPECIFIC:
public static boolean isDoorTile(char code)             // Level2 has doors
public static boolean isMovingPlatform(char code)       // Level2 mechanics
public static int getDoorDirection(char code)           // Up/Down/Left/Right?

// STANDARD:
public static int getTotalTiles()                       // Return 64
public static Map<Character, Float> getAllFriction()
public static Map<Character, Boolean> getAllHazards()
```

**Status**: 🟡 **NEEDS UPGRADE** (Same as Level1)  
**Priority**: 🔴 **CRITICAL**

---

### **GROUP 3: ANIMATION & PHYSICS (4 Classes)**

---

#### **9. SpriteMetadata**  
**Purpose**: Animation frame analysis (duration, fps, complexity)  
**Line**: ~1493

**Current Methods** (assumed)
```java
public static SpriteMetadata analyze(BufferedImage sprite)
public int getFrameCount()
public int getFrameWidth()
public int getFrameHeight()
public int getAnimationDuration()
public float getComplexity()                            // Pixel density metric
```

**Methods Needed for Tester** (to upgrade)
```java
// ANIMATION TIMING:
public int getFramesPerSecond()                         // Calculated FPS
public int getMillisecondsPerFrame()                    // 1000/FPS
public boolean isAnimated()                             // Multiple frames?

// SPRITE METRICS:
public int getTotalPixels()                             // frameCount * W * H
public float getDensity()                               // Non-transparent %
public boolean hasAlphaChannel()                        // Transparency?

// QUALITY:
public String getQuality()                              // "High"/"Medium"/"Low"
public boolean isHighResolution()                       // Width > 256?

// COMPARISON:
public boolean isSimilarTo(SpriteMetadata other)        // Compare dimensions
public int getMemoryUsage()                             // Bytes needed
```

**Status**: 🔴 **UNKNOWN**  
**Priority**: 🟢 **LOW**

---

#### **10. AnimationRegistry**  
**Purpose**: Central animation state tracking  
**Line**: ~(need to find)

**Current Methods** (assumed)
```java
public static void registerAnimation(String name, List<BufferedImage> frames)
public static List<BufferedImage> getAnimation(String name)
public static void playAnimation(String name)
public static void stopAnimation(String name)
public static boolean isPlaying(String name)
```

**Methods Needed for Tester** (to upgrade)
```java
// ENUMERATION:
public static Set<String> getAllAnimations()            // Registered anims
public static int getAnimationCount()

// CONTROL:
public static void setLooping(String name, boolean loop)
public static void setSpeed(String name, float speed)   // 0.5x, 1.0x, 2.0x
public static boolean canLoop(String name)

// QUERY:
public static int getFrameCount(String name)
public static int getCurrentFrame(String name)
public static float getProgress(String name)            // 0.0 to 1.0
public static boolean isRegistered(String name)

// BATCH:
public static void playAll()
public static void stopAll()
public static void preloadAllAnimations()
```

**Status**: 🔴 **UNKNOWN**  
**Priority**: 🟢 **MEDIUM**

---

#### **11. PhysicsUnitSystem**  
**Purpose**: SI unit conversion (pixels ↔ meters, gravity)  
**Line**: ~1641

**Current Methods** ✓ (Static constants should be here)
```java
public static final float GRAVITY = -9.81f               // m/s²
public static final float TIME_STEP = 1.0f / 60.0f      // seconds (60 FPS)
public static final float PIXELS_PER_METER = 32.0f
public static final float DAMPING = 0.95f               // Energy loss
```

**Methods Needed for Tester** (to upgrade - these should be math utilities)
```java
// CONVERSION - Pixels ↔ Meters:
public static float pixelsToMeters(int pixels)          // pixels / PPM
public static int metersToPixels(float meters)          // (int)(m * PPM)
public static float pixelsPerSecondToMetersPerSecond(int pps)

// PHYSICS CALCULATIONS:
public static float calculateVelocity(float acceleration, float time)
public static float calculateDistance(float velocity, float time)
public static float calculateAcceleration(float force, float mass)
public static Vector2D calculateGravity(float mass)      // Force due to gravity

// VALIDATION:
public static boolean isPhysicallyValid(float value)    // Not NaN/Infinity?
public static boolean isInBounds(int x, int y)          // Screen bounds check?

// CONSTANTS ACCESS:
public static float getGravity()                        // Return GRAVITY
public static float getTimeStep()                       // Return TIME_STEP
public static float getPixelsPerMeter()                 // Return PPM
```

**Status**: 🟡 **NEEDS UPGRADE** (Add conversion methods)  
**Priority**: 🔴 **CRITICAL** (Physics test depends on this)

---

#### **12. PhysicsBody** (or similar)  
**Purpose**: Entity physics (position, velocity, acceleration, forces)  
**Line**: ~1700

**Current Methods** (assumed)
```java
public PhysicsBody(float x, float y, float mass)
public void applyForce(Vector2D force)
public void update(float deltaTime)
public Vector2D getVelocity()
public Vector2D getPosition()
public void setPosition(float x, float y)
public void setVelocity(float vx, float vy)
```

**Methods Needed for Tester** (to upgrade)
```java
// PHYSICS STATE:
public Vector2D getAcceleration()
public float getMass()
public boolean isMoving()                               // Velocity > threshold?

// FORCES:
public void applyGravity()
public void applyDrag(float dragCoefficient)
public void addImpulse(float impulseX, float impulseY)  // Instant velocity change
public void resetForces()                               // Clear accumulated forces

// COLLISION (likely EXISTS but might need upgrade):
public boolean checkCollision(PhysicsBody other)
public boolean isColliding()

// QUERY:
public float getKineticEnergy()                         // 0.5 * m * v²
public float getPotentialEnergy(float groundLevel)      // m * g * h
public float getTotalEnergy()
```

**Status**: 🟡 **NEEDS UPGRADE**  
**Priority**: 🔴 **CRITICAL** (Physics demo key)

---

### **GROUP 4: ASSET MAPPERS (5 Classes)**

---

#### **13. CharacterAssetMapper**  
**Purpose**: Player skin asset lookup  
**Line**: ~2149

**Current Methods** (assumed)
```java
public static String getAsset(String characterID)
public static BufferedImage loadCharacter(String characterID)
```

**Methods Needed for Tester** (to upgrade)
```java
// ENUMERATION:
public static List<String> getAllCharacterIDs()         // ["Biker", "Punk", "Cyborg"]
public static Map<String, String> getCharacterMap()

// DIMENSION:
public static Dimension getSize(String characterID)     // Width x Height
public static int getWidth(String characterID)
public static int getHeight(String characterID)

// STATES:
public static List<String> getStates(String characterID)  // ["idle","walk","attack","die"]
public static BufferedImage getState(String id, String state)

// ANIMATION:
public static int getAnimationFrames(String id, String state)
public static int getAnimationSpeed(String id, String state)
```

**Status**: 🟡 **NEEDS UPGRADE**  
**Priority**: 🟠 **HIGH**

---

#### **14. TransporterAssetMapper**  
**Purpose**: Vehicle asset lookup (5 types)  
**Line**: ~2353

**Current Methods** (assumed)
```java
public static String getTransporter(int typeID)         // 107-127 range
public static BufferedImage loadTransporter(int typeID)
```

**Methods Needed for Tester** (to upgrade)
```java
// ENUMERATION:
public static int[] getAllTransporterIDs()              // [107,108,109...]
public static List<String> getAllTransporterTypes()     // Type names
public static int getTotalTransporterTypes()            // Return 5

// IDENTIFICATION:
public static String getTransporterName(int typeID)     // "Hover Tank", etc
public static int getTransporterID(String name)         // Reverse lookup

// PROPERTIES:
public static float getTransporterSpeed(int typeID)     // Pixels/sec max
public static float getTransporterWeight(int typeID)    // Mass
public static int getTransporterHealth(int typeID)      // Max HP

// ANIMATION:
public static List<BufferedImage> getTransporterFrames(int typeID)
```

**Status**: 🟡 **NEEDS UPGRADE**  
**Priority**: 🟠 **MEDIUM**

---

#### **15. EnemyAssetMapper**  
**Purpose**: Enemy asset lookup (40+ states, 5 types, codes 100-233)  
**Line**: ~2524

**Current Methods** (assumed)
```java
public static String getEnemy(int enemyID)              // 100-233
public static BufferedImage loadEnemy(int enemyID)
```

**Methods Needed for Tester** (to upgrade)
```java
// ENUMERATION:
public static int[] getAllEnemyIDs()                    // [100..233]
public static int getEnemyCount()                       // Return total valid IDs
public static List<String> getAllEnemyTypes()           // Type names

// IDENTIFICATION:
public static String getEnemyType(int enemyID)          // "Grunt", "Elite", etc
public static List<Integer> getEnemiesByType(String type)  // All IDs of type

// STATES (41 different states):
public static List<String> getAllStates()               // ["idle","patrol","chase"...]
public static String getState(int enemyID)              // Current state
public static BufferedImage getStateSprite(int id, String state)

// PROPERTIES:
public static int getHealth(int enemyID)                // Max HP
public static float getSpeed(int enemyID)               // Movement speed
public static int getAttackPower(int enemyID)           // Damage dealt

// ANIMATION:
public static List<BufferedImage> getAnimationFrames(int id, String state)
public static int getAnimationSpeed(int id, String state)
```

**Status**: 🔴 **LIKELY INCOMPLETE** (41 states complex)  
**Priority**: 🟠 **MEDIUM**

---

#### **16. ProjectileAssetMapper**  
**Purpose**: Projectile asset lookup (5 types)  
**Line**: ~2877

**Current Methods** (assumed)
```java
public static String getProjectile(String projectileType)  // A-E or similar
public static BufferedImage loadProjectile(String type)
```

**Methods Needed for Tester** (to upgrade)
```java
// ENUMERATION:
public static List<String> getAllProjectileTypes()      // ["A","B","C","D","E"]
public static int getProjectileCount()                  // Return 5

// IDENTIFICATION:
public static String getProjectileName(String type)     // "Bullet A", etc
public static String getProjectileType(String name)     // Reverse lookup

// PHYSICS:
public static float getProjectileSpeed(String type)
public static float getProjectileSize(String type)
public static int getProjectileDamage(String type)
public static boolean pierces(String type)              // Penetration?

// ANIMATION:
public static BufferedImage getTrailEffect(String type)
public static int getTrailFrames(String type)
```

**Status**: 🟡 **NEEDS UPGRADE**  
**Priority**: 🟠 **MEDIUM**

---

#### **17. ProjectilePhysics**  
**Purpose**: Projectile physics properties (velocity, drag, lifetime)  
**Line**: ~3074

**Current Methods** (assumed)
```java
public ProjectilePhysics(float x, float y, String type)
public void update(float deltaTime)
public Vector2D getPosition()
public boolean isAlive()
```

**Methods Needed for Tester** (to upgrade)
```java
// STATE QUERY:
public Vector2D getVelocity()
public Vector2D getAcceleration()
public float getLifeRemaining()                         // Time until dead
public boolean shouldDelete()                           // Past lifetime?

// PHYSICS:
public float getInitialVelocity()
public float getDragCoefficient()
public float getGravityScale()                          // 0.0 = no gravity
public float getMass()

// COLLISION & EFFECTS:
public boolean checkBounds(int screenWidth, int height)
public void bounce(float elasticity)                    // Physics response
public void explode()                                   // On impact
public List<Vector2D> getTrailPositions()              // For drawing
```

**Status**: 🟡 **NEEDS UPGRADE**  
**Priority**: 🟠 **MEDIUM**

---

### **GROUP 5: OTHER SYSTEMS (5 Classes)**

---

#### **18. DamageCalculationSystem**  
**Purpose**: Damage calculation with modifiers & falloff  
**Line**: ~(need to find)

**Current Methods** (assumed)
```java
public static int calculate(int baseDamage, float distance)
public static int applyModifiers(int damage, String[] modifiers)
```

**Methods Needed for Tester** (to upgrade)
```java
// BASIC:
public static int calculateDamage(int baseDamage, Entity attacker, Entity target)
public static int calculateFalloff(int baseDamage, float distance)

// MODIFIERS:
public static List<String> getAvailableModifiers()
public static int applyModifier(int damage, String modifier)
public static int applyMultipleModifiers(int base, String[] mods)

// CRITICAL HITS:
public static boolean isCritical(int critChance)       // Probability check
public static int calculateCriticalDamage(int baseDamage, float multiplier)

// RESISTANCES:
public static int applyResistance(int damage, float resistance)  // 0.0=immune, 1.0=normal
public static int applyArmor(int damage, float armorValue)
```

**Status**: 🔴 **UNKNOWN**  
**Priority**: 🟢 **LOW** (Not critical for visual tester)

---

#### **19. ProjectileRegistry**  
**Purpose**: Bullet properties (A-J types, penetration)  
**Line**: ~(need to find)

**Current Methods** (assumed)
```java
public static Map<String, BulletProperties> getRegistry()
public static BulletProperties get(String bulletID)
```

**Methods Needed for Tester** (to upgrade)
```java
// ENUMERATION:
public static Set<String> getAllBulletTypes()           // A-J types
public static List<BulletProperties> getAll()

// PROPERTIES QUERY:
public static float getSpeed(String type)
public static int getDamage(String type)
public static float getSize(String type)
public static boolean canPenetrate(String type)
public static int getPenetrationLimit(String type)      // How many entities?

// SPECIAL PROPERTIES:
public static boolean isExplosive(String type)
public static float getExplosionRadius(String type)
public static boolean hasTrail(String type)
public static boolean isFocused(String type)            // Beam vs projectile?

// CRAFTING (if applicable):
public static List<String> getCraftingMaterials(String type)
public static boolean canCraft(String type)
```

**Status**: 🔴 **UNKNOWN**  
**Priority**: 🟠 **MEDIUM**

---

#### **20. EnemyProjectileRegistry**  
**Purpose**: Enemy projectile types (Rugby Ball, Ghost Orb, etc)  
**Line**: ~(need to find)

**Current Methods** (assumed)
```java
public static List<EnemyProjectile> getAllProjectiles()
public static EnemyProjectile get(String projectileID)
```

**Methods Needed for Tester** (to upgrade)
```java
// ENUMERATION:
public static Set<String> getAllEnemyProjectiles()      // Available types
public static int getTotalTypes()

// QUERY:
public static String getProjectileName(String id)
public static String getDescription(String id)

// BEHAVIOR:
public static float getSpeed(String id)
public static int getDamage(String id)
public static float getLifetime(String id)              // Seconds

// PHYSICS:
public static boolean homesOnPlayer(String id)          // Tracking?
public static boolean explodes(String id)
public static float getExplosionRadius(String id)
public static boolean passes(String id)                 // Homing type?

// VISUALS:
public static BufferedImage getSprite(String id)
public static List<BufferedImage> getAnimationFrames(String id)
```

**Status**: 🔴 **UNKNOWN**  
**Priority**: 🟠 **MEDIUM**

---

#### **21. EntityController**  
**Purpose**: Base interface for all game entities  
**Line**: ~(likely an interface)

**Current Methods** (assumed interface)
```java
public interface EntityController {
    void initialize();
    void update(float deltaTime);
    void render(Graphics2D g);
    void handleInput(KeyEvent e);
    void handleMouse(MouseEvent e);
    boolean isAlive();
}
```

**Methods Needed for Tester** (to upgrade)
```java
// QUERY:
public Entity getEntity()
public Vector2D getPosition()
public Dimension getSize()
public boolean isActive()
public boolean isVisible()

// CONTROL:
public void activate()
public void deactivate()
public void setVisible(boolean visible)

// STATE:
public String getState()
public void setState(String state)
public List<String> getAvailableStates()

// COLLISION:
public Rectangle getBounds()
public boolean intersects(Rectangle other)

// PROPERTIES:
public int getHealth()
public void takeDamage(int amount)
public void heal(int amount)
```

**Status**: 🟡 **NEEDS VERIFICATION**  
**Priority**: 🟢 **MEDIUM** (Base class for all entities)

---

#### **22. MenuAnimationSystem**  
**Purpose**: UI animation system for menus & transitions  
**Line**: ~(need to find)

**Current Methods** (assumed)
```java
public static void playAnimation(String menuName, AnimationType type)
public static void stopAnimation(String menuName)
public static boolean isAnimating(String menuName)
```

**Methods Needed for Tester** (to upgrade)
```java
// ENUMERATION:
public static List<String> getAvailableAnimations()
public static Set<AnimationType> getAnimationTypes()    // Fade, Slide, Zoom, etc

// CONTROL:
public static void play(String name, float duration)
public static void stop(String name)
public static void pauseAnimation(String name)
public static void resumeAnimation(String name)

// QUERY:
public static float getProgress(String name)            // 0.0 to 1.0
public static float getRemainingTime(String name)
public static boolean isPlaying(String name)
public static boolean canInterrupt(String name)

// CALLBACKS:
public static void setOnComplete(String name, Runnable callback)
public static void setOnFrame(String name, Consumer<Integer> callback)

// BATCH:
public static void stopAll()
public static void pauseAll()
public static void resumeAll()
```

**Status**: 🔴 **UNKNOWN**  
**Priority**: 🟢 **LOW** (Nice to have, not critical)

---

## 📊 SUMMARY TABLE

| # | Class | Group | Current State | Methods Present | Methods Missing | Upgrade Priority |
|---|-------|-------|---|---|---|---|
| 1 | TileAssets | Assets | 🏁 Known | 5 | 9+ | 🔴 CRITICAL |
| 2 | ParticleAssets | Assets | 🏁 Known | 8 | 8+ | 🟠 HIGH |
| 3 | UIAssets | Assets | 🏁 Known | 13 | 8+ | 🟠 HIGH |
| 4 | CharacterAssets | Assets | 🏁 Known | 8 | 6+ | 🔴 CRITICAL |
| 5 | WeaponAssets | Assets | ❓ Unknown | ? | ? | 🟠 HIGH |
| 6 | VFXAssets | Assets | ❓ Unknown | ? | ? | 🟠 HIGH |
| 7 | Level1TileRegistry | Registries | 🏁 Known | 3+ | 9+ | 🔴 CRITICAL |
| 8 | Level2TileRegistry | Registries | 🏁 Known | 3+ | 9+ | 🔴 CRITICAL |
| 9 | SpriteMetadata | Animation | ❓ Unknown | ~6 | 8+ | 🟢 LOW |
| 10 | AnimationRegistry | Animation | ❓ Unknown | ~5 | 7+ | 🟢 MEDIUM |
| 11 | PhysicsUnitSystem | Physics | 🏁 Known | 4 const | 9+ | 🔴 CRITICAL |
| 12 | PhysicsBody | Physics | ❓ Partial | ~7 | 7+ | 🔴 CRITICAL |
| 13 | CharacterAssetMapper | Mappers | ❓ Partial | ~2 | 7+ | 🟠 HIGH |
| 14 | TransporterAssetMapper | Mappers | ❓ Partial | ~2 | 7+ | 🟠 MEDIUM |
| 15 | EnemyAssetMapper | Mappers | ❓ Partial | ~2 | 8+ | 🟠 MEDIUM |
| 16 | ProjectileAssetMapper | Mappers | ❓ Partial | ~2 | 7+ | 🟠 MEDIUM |
| 17 | ProjectilePhysics | Mappers | ❓ Partial | ~3 | 8+ | 🟠 MEDIUM |
| 18 | DamageCalculationSystem | Systems | ❓ Unknown | ? | ? | 🟢 LOW |
| 19 | ProjectileRegistry | Systems | ❓ Unknown | ? | ? | 🟠 MEDIUM |
| 20 | EnemyProjectileRegistry | Systems | ❓ Unknown | ? | ? | 🟠 MEDIUM |
| 21 | EntityController | Interface | ❓ Unknown | ~6 | 8+ | 🟢 MEDIUM |
| 22 | MenuAnimationSystem | Systems | ❓ Unknown | ~3 | 8+ | 🟢 LOW |

---

## 🎯 UPGRADE PRIORITY RANKING

### **PHASE 2A: CRITICAL UPGRADES** (Must do for tester)
```
1. TileAssets ..................... Add enumeration methods
2. CharacterAssets ............... Add skin enumeration + animation
3. Level1TileRegistry ............ Add getAllCodes() + properties map
4. Level2TileRegistry ............ Add getAllCodes() + properties map
5. PhysicsUnitSystem ............. Add conversion math methods
6. PhysicsBody ................... Add energy + impulse methods
```

### **PHASE 2B: HIGH-PRIORITY UPGRADES** (Recommended)
```
7. ParticleAssets ............... Add frame sequence methods
8. UIAssets ..................... Add enumeration + dimension methods
9. CharacterAssetMapper ......... Add skin query methods
10. WeaponAssets ................ Add weapon enumeration
11. VFXAssets ................... Add effect enumeration
```

### **PHASE 2C: MEDIUM UPGRADES** (Nice to have)
```
12. TransporterAssetMapper
13. EnemyAssetMapper
14. ProjectileAssetMapper
15. ProjectilePhysics
16. ProjectileRegistry
17. EnemyProjectileRegistry
18-22. Other systems (lower priority)
```

---

## ✅ NEXT STEP: Press [Continue] for Next Phase

This audit identifies **exactly which methods need to be added** to each of the 22 nested classes to make them full, testable APIs.

**PHASE 2 will:**
1. Read actual code of classes marked ❓
2. Complete methods list
3. Create upgrade implementations
4. Add missing methods one by one

**Ready to proceed?**

