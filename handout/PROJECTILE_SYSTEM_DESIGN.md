# 🚀 Comprehensive Projectile & AI Enemy System Design

## Executive Summary

This document outlines the complete architecture for integrating:
1. **Projectile System** - 21+ projectile types with trajectory support
2. **AI Enemy System** - Enemy AI with auto-aiming and state management
3. **Weapon Integration** - 13 weapon projectile types
4. **Collision & VFX** - Impact effects and collision handling

---

## PART I: PROJECTILE SYSTEM ARCHITECTURE

### 1. Projectile Classification

#### **Type A: Single-Sprite Projectiles** (No Animation)
**Characteristics:**
- 1 Frame only
- Size: 6-15 pixels (width × height)
- Static image = instant render
- No animation loop needed

**Examples:**
- All 13 weapon bullets (TypeA through TypeJ)
- Armoured Knight projectile (13×10 px)
- Winged Warrior red projectile

**Naming Pattern:**
```
[Seq]_Weapon_Bullet_[Type][Variant]_Single_StaticSprite.png
[Seq]_Enemy_[Name]_Projectile_1Frame1Row_[Description]_Projectile_Loop_100ms.png
```

#### **Type B: Sprite-Sheet Projectiles** (With Animation)
**Characteristics:**
- 6-7 frames
- Animation loop during travel
- Examples: Orb projectiles, Drone capsules

**Examples:**
- Winged Warrior orb (6 frames)
- Drone 6 capsule attack (7 frames)

**Naming Pattern:**
```
[Seq]_Enemy_[Name]_[Type]_[Frames]Frames1Row_[Description]_[Category]_[Timing].png
```

#### **Type C: Character Attack Projectiles** (Mixed)
**Characteristics:**
- Character performing attack + projectile release
- 6+ frames showing attack animation
- Projectile spawns at frame N

**Examples:**
- Punk player attack3 (6 frames)
- Rugby guy ball throw (1 frame)

---

### 2. Trajectory Types System

```java
enum ProjectileTrajectory {
    STRAIGHT,      // Direct line from shooter to target
    ARC,           // Gravity-based arc (parabolic)
    HOMING,        // Follows target (auto-aim)
    INSTANT,       // No travel time (laser/hit)
    CURVED,        // Custom curve path
    BOOMERANG      // Goes out and returns (like orb)
}
```

### 3. Projectile Metadata Structure

```java
public class ProjectileDefinition {
    // Identity
    public String projectileName;      // "Rugby Ball", "Bullet TypeA"
    public String projectileId;        // "rugby_ball", "bullet_a"
    
    // Rendering
    public String imagePath;           // Full path to PNG
    public int width;                  // Pixel width
    public int height;                 // Pixel height
    public boolean animated;           // Has animation frames
    public int frameCount;             // Number of frames
    public int frameTimeMs;            // Time per frame
    
    // Physics
    public ProjectileTrajectory trajectory;
    public float speed;                // Pixels per frame
    public float gravity;              // Gravity factor (for arc)
    public float damage;               // Damage dealt
    public int lifetime;               // Milliseconds before despawn
    
    // Source
    public String sourceCharacter;     // Shooter: "RugbyGuy", "Drone6", "PunkPlayer"
    public String sourceAttack;        // Attack name: "ProjectileThrow", "LaserBlast"
    public int spawnOffsetX;           // X offset from shooter
    public int spawnOffsetY;           // Y offset from shooter
    
    // Impact
    public String collisionVfxType;    // "explosion", "spark", "splash"
    public boolean pierce;             // Ignores shields
    public boolean bounce;             // Bounces off terrain
    
    // Direction
    public boolean affectedByAiming;   // Uses shooter's aim direction
    public float minAimAngle;          // Degrees
    public float maxAimAngle;          // Degrees
}
```

---

### 4. Projectile Source Mapping

```
BOSSES → PROJECTILES:
├─ GreenMech (10 animations)
│  ├─ Projectile TBD: CannonBall (specified in analysis)
│  └─ Projectile TBD: Laser bullet (specified in analysis)
│
├─ RugbyGuy (6 animations)
│  └─ ✓ Rugby Ball Throw (03_Boss_RugbyGuy_Projectile_1Frame...)
│
└─ GolfCartSoldier (11 animations)
   └─ Projectile TBD: Vehicle-launched projectile

ENEMIES → PROJECTILES:
├─ Drone 6 (Hover Platform)
│  └─ ✓ Capsule Projectile (04_EnemyDrone_HoverPlatform_CapsuleProjectileAttack_7Frames1Row.png)
│
├─ Sci-Fi 2 (Armoured Knight)
│  └─ ✓ Single Projectile (08_Enemy_ArmouredKnight_Projectile_1Frame1Row...)
│
└─ Sci-Fi 3 (Winged Warrior)
   ├─ ✓ Orb Projectile (09_Enemy_WingedWarrior_Projectile_1Frame1Row...)
   └─ ✓ Orb Attack (returns) (04_Enemy_WingedWarrior_Attack2_6Frames1Row_OrbProjectileShotReturn...)

PLAYERS → PROJECTILES:
└─ Punk (24 animations)
   └─ ✓ Combat Projectile (15_Player_Punk_Attack3_6Frames1Row_ComboHit3Projectile...)

WEAPONS → PROJECTILES (13 types):
└─ Weapon 1 Bullets (13 variants: TypeA-TypeJ)
```

---

## PART II: AI ENEMY SYSTEM ARCHITECTURE

### 1. Enemy State Machine

```java
enum EnemyState {
    IDLE,           // Waiting/patrolling
    TRACKING,       // Detecting player nearby
    ATTACKING,      // Engaged in combat
    CHARGING,       // Building up special attack
    HURT,           // Taking damage
    FLEEING,        // Low health escape
    DEAD            // Defeated
}
```

### 2. Enemy AI Definition

```java
public class AIEnemyDefinition {
    // Identity
    public String enemyName;           // "Armoured Knight"
    public String enemyType;           // "sci_fi_2", "drone_6"
    public String enemyClass;          // "Melee", "Ranged", "Hybrid"
    
    // Behavior
    public EnemyState currentState;
    public float detectionRange;       // Pixels
    public float attackRange;          // Pixels
    public float retreatDistance;      // When to flee
    
    // Combat
    public String[] attackPatterns;    // ["straight", "projectile", "charge"]
    public int[] attackFrames;         // Which animation frame triggers attack
    public float attackCooldown;       // Milliseconds between attacks
    public float healthPoints;         // Max HP
    
    // Aiming
    public boolean autoAim;            // Auto-target player
    public float aimTolerance;         // Degrees of spread
    public float aimUpdateFrequency;   // How often to recalculate aim
    
    // Movement
    public float movementSpeed;        // Pixels per frame
    public String[] movementPatterns;  // ["patrol", "strafe", "rush"]
    
    // AI Difficulty
    public float reactionTimeMs;       // Milliseconds to respond
    public float accuracyPercent;      // 0-100 hit accuracy
    public float aggressionLevel;      // 0-100 how often attacks
}
```

### 3. Auto-Aiming Algorithm

```
PLAYER POSITION → CALCULATE AIM VECTOR:
  1. Get enemy current X,Y
  2. Get player current X,Y
  3. Calculate angle = atan2(player.y - enemy.y, player.x - enemy.x)
  4. Apply difficulty modifiers:
     - Easy: Add ±15° random spread
     - Normal: Add ±8° random spread
     - Hard: Add ±3° random spread
  5. Fire projectile in calculated direction
  6. Projectile spawns at (enemy.x + offsetX, enemy.y + offsetY)
  7. Velocity = direction * projectile.speed
```

---

## PART III: IMPLEMENTATION STRUCTURE

### A. AnimationAndSpriteLoader.java Extensions

**Add Four New Nested Classes:**

```java
// ========== NEW SECTION: PROJECTILE DEFINITIONS ==========

public static class ProjectileRegistry {
    // Single-sprite projectiles (21)
    public static class CannonBallProjectile { ... }
    public static class LaserBulletProjectile { ... }
    
    // Multi-frame projectiles
    public static class RugbyBallProjectile { ... }
    public static class OrbProjectileBoomerang { ... }
    public static class DroneCapuleProjectile { ... }
    
    // Weapon bullets (13 types)
    public static class WeaponBulletRegistry { ... }
}

// ========== NEW SECTION: ENEMY AI DEFINITIONS ==========

public static class AIEnemyRegistry {
    // Bosses with AI
    public static class GreenMechAI { ... }
    public static class RugbyGuyAI { ... }
    public static class GolfCartSoldierAI { ... }
    
    // Enemy drones with AI
    public static class Drone1AI { ... }
    // ... through Drone6AI
    
    // Sci-Fi antagonists with AI
    public static class SciFi1AI { ... }
    public static class SciFi2ArmouredKnightAI { ... }
    public static class SciFi3WingedWarriorAI { ... }
}

// ========== NEW SECTION: COLLISION & VFX ==========

public static class ProjectileCollisionVFX {
    // Impact effects
    public static class ExplosionVFX { ... }
    public static class SparkVFX { ... }
    public static class SplashVFX { ... }
}
```

### B. File Organization

```
AnimationAndSpriteLoader.java
├─ PART 1: Sprite Metadata Analysis (existing)
├─ PART 2: Sprite Loaders (existing)
├─ PART 3: Character Animation Classes (existing)
│  ├─ GreenMechBoss
│  ├─ RugbyGuyBoss
│  └─ GolfCartSoldierBoss
├─ PART 4: Enemy Animation Classes (existing)
│  ├─ Drone 1-6
│  └─ Sci-Fi 1-3
├─ PART 5: [NEW] Projectile Registry ← INSERT HERE
│  ├─ ProjectileDefinition base
│  ├─ Single-sprite projectiles
│  ├─ Animated projectiles
│  └─ Weapon bullets
├─ PART 6: [NEW] AI Enemy Registry ← INSERT HERE
│  ├─ AIEnemyDefinition base
│  ├─ Boss AI classes
│  ├─ Drone AI classes
│  └─ Sci-Fi AI classes
└─ PART 7: Asset Registry (existing)
```

---

## PART IV: PROJECTILE INVENTORY (Complete)

### Bosses (2-3 projectiles)
1. ✓ **RugbyGuy**: Rugby Ball (1 frame, instant)
2. ⏳ **GreenMech**: Cannon Ball (TBD)
3. ⏳ **GreenMech**: Laser Bullet (TBD)

### Enemies (5 projectiles)
1. ✓ **Drone 6**: Capsule Attack (7 frames)
2. ✓ **Sci-Fi 2**: Armoured Knight projectile (1 frame)
3. ✓ **Sci-Fi 3**: Winged Warrior Orb (1 frame)
4. ✓ **Sci-Fi 3**: Winged Warrior Orb Return (6 frames)

### Players (1 projectile)
1. ✓ **Punk**: Combat Projectile (6 frames)

### Weapons (13 projectiles)
1-13: Weapon Bullet Types A-J with variants

---

## PART V: Implementation Roadmap

### Phase 1: ProjectileRegistry Creation
- [ ] Define ProjectileDefinition base class
- [ ] Create individual projectile classes
- [ ] Add projectile linking to source characters
- [ ] Implement projectile metadata extraction

### Phase 2: AIEnemyRegistry Creation
- [ ] Define AIEnemyDefinition base class
- [ ] Create AI classes for each boss
- [ ] Create AI classes for drone enemies
- [ ] Create AI classes for sci-fi antagonists
- [ ] Implement auto-aiming logic

### Phase 3: Collision & VFX
- [ ] Define VFX types
- [ ] Create impact effect classes
- [ ] Link projectiles to VFX on collision

### Phase 4: Integration Testing
- [ ] Build ProjectileTester GUI (like CharacterAnimationTester)
- [ ] Test projectile rendering
- [ ] Test AI targeting
- [ ] Test trajectory calculations

### Phase 5: Extended Features
- [ ] Weapon system integration
- [ ] Projectile pooling for performance
- [ ] Advanced trajectory curves
- [ ] Particle effect integration

---

## PART VI: Code Patterns & Conventions

### Naming Pattern for Single-Sprite Projectiles
```
public static class [Name]Projectile {
    public static final String PROJECTILE_NAME = "[Human Readable]";
    public static final String PROJECTILE_ID = "[identifier]";
    public static final String IMAGE_PATH = "Resources/industrial-zone/[full]/path/to/image.png";
    
    public static final int WIDTH = 13;           // pixels
    public static final int HEIGHT = 10;          // pixels
    public static final boolean ANIMATED = false;
    
    public static final ProjectileTrajectory TRAJECTORY = ProjectileTrajectory.STRAIGHT;
    public static final float SPEED = 5.0f;       // pixels/frame
    public static final float DAMAGE = 15.0f;
    public static final int LIFETIME_MS = 3000;
    
    public static final String SOURCE_CHARACTER = "Sci-Fi 2";
    public static final String SOURCE_ATTACK = "RangedAttack";
}
```

### Naming Pattern for AI Enemies
```
public static class [Name]AI {
    public static final String ENEMY_NAME = "[Human Readable]";
    public static final String ENEMY_TYPE = "[identifier]";
    public static final String CHARACTER_CLASS = "Ranged";
    
    public static final float DETECTION_RANGE = 300f;    // pixels
    public static final float ATTACK_RANGE = 250f;       // pixels
    public static final float MOVEMENT_SPEED = 2.0f;     // pixels/frame
    
    public static final boolean AUTO_AIM = true;
    public static final float AIM_TOLERANCE = 8f;        // degrees
    public static final float REACTION_TIME_MS = 500;
    
    public static final String[] ATTACK_PATTERNS = {"straight", "projectile"};
    public static final float[] AVAILABLE_PROJECTILES = {...};
}
```

---

## PART VII: Future Expansion Points

### Upcoming Projectile Additions
- [ ] Additional GreenMech cannon variants
- [ ] GolfCartSoldier vehicle projectiles
- [ ] Boss-specific weapon variants
- [ ] Special ability projectiles

### Upcoming AI Features
- [ ] Cooperative multi-enemy attacks
- [ ] Formation movement patterns
- [ ] Tactical retreat behavior
- [ ] Dynamic difficulty scaling
- [ ] Learning AI (adapts to player patterns)

### Extended Collision System
- [ ] Terrain interaction
- [ ] Physics-based bouncing
- [ ] Environmental hazard triggers
- [ ] Particle trail effects

---

## Summary of Immediate Next Steps

1. **Read AnimationAndSpriteLoader.java** - Understand full structure
2. **Create ProjectileRegistry section** - Add ~25 projectile classes
3. **Create AIEnemyRegistry section** - Add ~9 AI enemy classes  
4. **Update boss/enemy classes** - Add projectile and AI references
5. **Create ProjectileTester GUI** - Test rendering and trajectories  
6. **Create AITestValidator** - Test AI aiming and behavior
7. **Document in code comments** - Patterns for future additions
