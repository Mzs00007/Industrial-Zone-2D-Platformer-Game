# COMPREHENSIVE OOPS INHERITANCE ARCHITECTURE FOR GAME2D

**Created:** April 2, 2026  
**Status:** Active Design Plan  
**Total Classes:** 990 compiled  
**Framework Classes:** 7 Sacred (game2D package)

---

## 📊 EXECUTIVE SUMMARY

This document defines a complete OOPS inheritance hierarchy using the game2D framework as the foundation. All 990 existing classes must reorganize to follow this inheritance structure.

**Key Principles:**
- Single inheritance chains (max 3-4 levels deep)
- Composition for complex features
- Template method pattern for behavior
- Factory pattern for object creation
- Strategy pattern for algorithms

---

## 🏛️ SACRED FOUNDATION LAYER (game2D/)

These **7 classes** are the untouchable foundation:

```
GAME2D PACKAGE (Sacred - DO NOT MODIFY)
├── GameCore.java
│   ├── Abstract class
│   ├── Extends: JFrame implements KeyListener
│   ├── Methods: run(), init(), gameLoop(), draw(), update()
│   ├── Purpose: Core game loop orchestration
│   └── WHAT INHERITS: Game.java, ScreenBase.java
│
├── Sprite.java
│   ├── Abstract animated entity
│   ├── Fields: animation, position(x,y), velocity(dx,dy), scale, rotation
│   ├── Methods: draw(), update(), setAnimation(), collision detection
│   ├── Purpose: All visible game objects
│   └── WHAT INHERITS: Entity.java, Character.java, GameObject.java, EnvironmentObject.java
│
├── Animation.java
│   ├── Frame sequence manager
│   ├── Fields: frames, timing, speed, loop control
│   ├── Methods: addFrame(), update(), getCurrentImage()
│   ├── Purpose: Manage sprite frame animation
│   └── WHAT INHERITS: StateAnimation.java, SpecializedAnimations
│
├── Tile.java
│   ├── Single map tile
│   ├── Fields: character(ID), x, y coordinates
│   ├── Methods: getCharacter(), setCharacter(), getXC(), getYC()
│   ├── Purpose: Basic map building block
│   └── WHAT INHERITS: Specialized tile types
│
├── TileMap.java
│   ├── 2D tile grid system
│   ├── Fields: tmap[][], mapWidth, mapHeight, tileWidth, tileHeight, imagemap
│   ├── Methods: loadMapFile(), getTile(), render()
│   ├── Purpose: Level layout management
│   └── WHAT INHERITS: EnhancedTileMapLoader.java, Level1Map, Level2Map
│
├── Velocity.java
│   ├── Movement vector physics
│   ├── Fields: angle, speed, dx, dy
│   ├── Methods: setVelocity(), setAngle(), getdx(), getdy()
│   ├── Purpose: Physics-based movement
│   └── WHAT INHERITS: PhysicsBody.java (composition primarily)
│
└── Sound.java
    ├── Audio playback thread
    ├── Extends: Thread
    ├── Fields: filename, finished flag
    ├── Methods: run() - execute in thread
    ├── Purpose: Non-blocking audio playback
    └── WHAT INHERITS: AudioManager.java (composition)
```

---

## 🎮 GAME LOOP HIERARCHY (Level 1)

```
GameCore (Sacred)
    ↓
├─► Game.java (Main game implementation)
│   └── Implements:
│       - Initialize all game systems
│       - Coordinate GUI, physics, rendering
│       - Main game loop orchestration
│       - Handle frame timing
│       - System updates ordering
│
└─► ScreenBase.java (Screen hierarchy base)
    └── Abstract screen controller
        ├── Purpose: Provide screen template
        ├── Methods:
        │   - handleInput()
        │   - updateGame()
        │   - renderScreen()
        │   - onEnter()
        │   - onExit()
        └── WHAT INHERITS: GameScreen, MenuScreen, Settings
```

---

## 🖥️ SCREEN HIERARCHY (Level 2-3)

```
ScreenBase (extends GameCore - Level 2)
│
├─► GameScreen (extends ScreenBase)
│   ├── Purpose: Base for all gameplay screens
│   ├── Fields:
│   │   - TileMap currentLevel
│   │   - EntityManager entities
│   │   - PhysicsEngine physics
│   │   - CameraController camera
│   │   - AudioManager audio
│   └── WHAT INHERITS: Level1Screen, Level2Screen, BossLevelScreen
│
├─► Level1Screen (extends GameScreen)
│   ├── Loads Level 1 tile map
│   ├── Spawns Level 1 enemies
│   ├── Configures Level 1 physics
│   └── Handles level-specific events
│
├─► Level2Screen (extends GameScreen)
│   ├── Loads Level 2 tile map
│   ├── Spawns Level 2 enemies
│   ├── Configures Level 2 physics
│   └── Handles level-specific events
│
├─► MenuScreen (extends ScreenBase)
│   ├── Purpose: Base for menu systems
│   ├── Fields: List<MenuItem>
│   ├── Methods:
│   │   - handleMenuNavigation()
│   │   - selectMenuItem()
│   │   - renderMenu()
│   └── WHAT INHERITS: MainMenu, PauseMenu, GameOverMenu
│
├─► MainMenu (extends MenuScreen)
│   ├── Start Game option
│   ├── Settings option
│   ├── Exit option
│   └── Handles menu selection
│
├─► PauseMenu (extends MenuScreen)
│   ├── Resume Game option
│   ├── Settings option
│   ├── Exit to Main Menu
│   └── Save Game option
│
├─► GameOverMenu (extends MenuScreen)
│   ├── Retry Level option
│   ├── Select Level option
│   ├── Main Menu option
│   └── Shows stats
│
├─► SettingsScreen (extends ScreenBase)
│   ├── Volume control
│   ├── Graphics settings
│   ├── Control mapping
│   └── Display options
│
└─► CreditsScreen (extends ScreenBase)
    ├── Credits display
    ├── Scrolling text
    └── Return to menu
```

---

## 🎭 ENTITY HIERARCHY (Level 2-3)

```
Sprite (Sacred - Level 1)
│
└─► Entity (extends Sprite - Level 2)
    ├── Purpose: Base for all game objects
    ├── Fields:
    │   - PhysicsBody physics
    │   - AnimationState currentState
    │   - boolean active
    │   - UUID entityID
    ├── Methods:
    │   - update(float deltaTime)
    │   - render(Graphics2D g)
    │   - onCollision(Entity other)
    │   - destroy()
    │   - getWorldBounds()
    │
    ├─► Character (extends Entity - Level 3)
    │   ├── Purpose: Living entities with AI potential
    │   ├── Fields:
    │   │   - Health health
    │   │   - Inventory inventory
    │   │   - StateController stateController
    │   │   - float speed, jumpForce
    │   ├── Methods:
    │   │   - takeDamage(int amount)
    │   │   - heal(int amount)
    │   │   - die()
    │   │   - setAnimationState()
    │   │
    │   ├─► Player (extends Character - Level 4)
    │   │   ├── Purpose: Player-controlled character
    │   │   ├── Fields:
    │   │   │   - PlayerController controller
    │   │   │   - Weapons weapon
    │   │   │   - int coins, lives
    │   │   ├── Methods:
    │   │   │   - handleInput()
    │   │   │   - shoot()
    │   │   │   - jump()
    │   │   │   - takeDamage()
    │   │   │
    │   │   ├─► BikerPlayer (extends Player)
    │   │   │   ├── Animation: biker_*.png
    │   │   │   └── Stats: Medium speed, normal jump
    │   │   │
    │   │   ├─► PunkPlayer (extends Player)
    │   │   │   ├── Animation: punk_*.png
    │   │   │   └── Stats: Fast speed, low jump
    │   │   │
    │   │   └─► CyborgPlayer (extends Player)
    │   │       ├── Animation: cyborg_*.png
    │   │       └── Stats: Slow speed, high jump
    │   │
    │   ├─► Enemy (extends Character - Level 4)
    │   │   ├── Purpose: AI-controlled enemies
    │   │   ├── Fields:
    │   │   │   - AIBehavior aiBehavior
    │   │   │   - PatrolPath patrolPath
    │   │   │   - float detectionRadius
    │   │   ├── Methods:
    │   │   │   - updateAI()
    │   │   │   - attack()
    │   │   │   - patrol()
    │   │   │
    │   │   ├─► GroundEnemy (extends Enemy)
    │   │   │   ├── Punk (patrol_horizontal)
    │   │   │   └── RugbyPlayer (aggressive_charging)
    │   │   │
    │   │   └─► DroneEnemy (extends Enemy)
    │   │       ├── UFO Saucer (hover at 1.5 tiles altitude)
    │   │       ├── Jet Drone (fast pursuit)
    │   │       └── Transport Drone (slow sweep pattern)
    │   │
    │   └─► Boss (extends Character - Level 4)
    │       ├── Purpose: End-level boss entities
    │       ├── Fields:
    │       │   - BossAIBehavior behavior
    │       │   - BossPhaseController phaseController
    │       │   - List<AttackPattern> attacks
    │       ├── Methods:
    │       │   - updatePhase()
    │       │   - selectAttack()
    │       │   - enterPhase2(), enterPhase3()
    │       │
    │       ├─► GreenMechBoss (extends Boss)
    │       │   ├── Phase 1: Basic attacks (75-100% HP)
    │       │   ├── Phase 2: Combo attacks (25-75% HP)
    │       │   └── Phase 3: Desperate attacks (0-25% HP)
    │       │
    │       ├─► GolfCartSoldierBoss (extends Boss)
    │       │   └── Mobile boss with charging attacks
    │       │
    │       └─► RugbyGuyBoss (extends Boss)
    │           └── Heavy aggressive boss
    │
    ├─► GameObject (extends Entity - Level 3)
    │   ├── Purpose: Non-character interactive objects
    │   ├── Fields:
    │   │   - String objectType
    │   │   - boolean collectible, interactive
    │   ├── Methods:
    │   │   - onCollect()
    │   │   - onInteract()
    │   │   - onDestroy()
    │   │
    │   ├─► Platform (extends GameObject)
    │   │   ├── Static solid platforms
    │   │   ├── Collision: Solid (blocks movement)
    │   │   └── Used for walkable surfaces
    │   │
    │   ├─► Hazard (extends GameObject)
    │   │   ├── Spike traps, electric barriers
    │   │   ├── Collision: Damage on touch
    │   │   ├── Animated (pulsing, rotating)
    │   │   └── DeactivatableHazard (breakable)
    │   │
    │   ├─► Collectible (extends GameObject)
    │   │   ├── Coins, power-ups, health
    │   │   ├── Animation: rotation, bobbing
    │   │   ├── onCollect(): Add to inventory
    │   │   └── SelfDestruct after pickup
    │   │
    │   ├─► Projectile (extends GameObject)
    │   │   ├── Bullets, energy balls
    │   │   ├── Physics: Velocity + gravity
    │   │   ├── Lifespan: Destroy after time/distance
    │   │   └── onCollide(): Damage entities
    │   │
    │   ├─► Decoration (extends GameObject)
    │   │   ├── Animated background objects
    │   │   ├── Parallax effects
    │   │   └── Non-interactive
    │   │
    │   └─► InteractiveObject (extends GameObject)
    │       ├── Doors, levers, buttons
    │       ├── onInteract(): Trigger events
    │       └── stateChange(): Open/Close animation
    │
    └─► EnvironmentObject (extends Entity - Level 3)
        ├── Purpose: Level environment elements
        ├── MovingPlatform (extends EnvironmentObject)
        ├── BreakablePlatform (extends EnvironmentObject)
        └── Decoration (extends EnvironmentObject)
```

---

## 🎨 ANIMATION HIERARCHY (Composition)

```
Animation (Sacred - Level 1)
│
└─► StateAnimation (extends Animation - Level 2)
    ├── Purpose: Manage state-based animations
    ├── Fields:
    │   - Map<AnimationState, Animation> stateAnimations
    │   - AnimationState currentState
    │   - StateTransition[] validTransitions
    ├── Methods:
    │   - addStateAnimation(AnimationState, Animation)
    │   - transitionToState(AnimationState)
    │   - updateForState(AnimationState)
    │
    ├─► PlayerAnimation (extends StateAnimation)
    │   ├── States: IDLE, WALK_LEFT, WALK_RIGHT, JUMP, FALL, ATTACK, HURT
    │   ├── Assets: Resources/industrial-zone/characters/biker/*.png
    │   └── Manages: Input-driven animations
    │
    ├─► EnemyAnimation (extends StateAnimation)
    │   ├── States: IDLE, PATROL, CHASE, ATTACK, DEATH
    │   ├── Assets: Resources/industrial-zone/characters/enemies/*.png
    │   └── Manages: AI-driven animations
    │
    └─► BossAnimation (extends StateAnimation)
        ├── States: IDLE, ATTACK_PHASE1, ATTACK_PHASE2, SPECIAL, DEATH
        ├── Assets: Boss-specific character sprites
        └── Manages: Complex attack animations
```

---

## 🏃 CHARACTER PHYSICS HIERARCHY (Composition)

```
Velocity (Sacred - Level 1)

[Composed into - NOT inherited]

    PhysicsBody (contains Velocity)
    ├── Fields:
    │   - Vector2D position
    │   - Vector2D velocity  [uses Velocity class]
    │   - Vector2D acceleration
    │   - float mass
    │   - float friction
    │   - boolean isGrounded
    │   - Rectangle boundingBox
    │
    ├─► CharacterPhysics (extends PhysicsBody)
    │   ├── Special: Gravity, jump mechanics
    │   ├── Methods:
    │   │   - applyGravity()
    │   │   - jump(float force)
    │   │   - land()
    │   │   - slide(float friction)
    │
    ├─► ProjectilePhysics (extends PhysicsBody)
    │   ├── Special: Ballistics, air resistance
    │   ├── Lifespan: Destroy after time
    │
    └─► PlatformPhysics (extends PhysicsBody)
        ├── Static or kinematic
        └── No gravity
```

---

## 📍 TILEMAP HIERARCHY

```
TileMap (Sacred - Level 1)

└─► EnhancedTileMapLoader (extends TileMap - Level 2)
    ├── Purpose: Advanced map loading with assets
    ├── Additions:
    │   - assetRegistry: char → asset path
    │   - parallaxLayers: List<ParallaxLayer>
    │   - gameObjects: List<GameObject> loaded from map
    │   - hazardRegions: List<HazardRegion>
    │   - checkpoints: List<Checkpoint>
    ├── Methods:
    │   - loadMapFile()
    │   - loadAssetRegistry()
    │   - loadGameObjects()
    │   - loadParallaxLayers()
    │   - loadHazardRegions()
    │
    ├─► Level1TileMap (extends EnhancedTileMapLoader)
    │   ├── Loads: Level1 specific resources
    │   ├── Assets: Resources/industrial-zone/1 Tiles/Level1/*
    │   └── Enemies: Level1-specific enemy types
    │
    ├─► Level2TileMap (extends EnhancedTileMapLoader)
    │   ├── Loads: Level2 specific resources
    │   ├── Assets: Resources/industrial-zone/1 Tiles/Level2/*
    │   └── Enemies: Level2-specific enemy types
    │
    └─► BossLevelTileMap (extends EnhancedTileMapLoader)
        ├── Loads: Boss level resources
        ├── Boss entity loaded specifically
        └── Boss arena setup
```

---

## 🎧 AUDIO HIERARCHY (Composition)

```
Sound (Sacred - Level 1)

[Composed into - NOT inherited]

    AudioManager
    ├── Contains: Multiple Sound instances
    ├── Fields:
    │   - List<Sound> activeAudio
    │   - float masterVolume
    │   - Map<String, Sound> audioCache
    │
    ├─► MusicPlayer (uses Sound composition)
    │   ├── Methods: playMusic(), stopMusic(), setVolume()
    │
    ├─► SFXPlayer (uses Sound composition)
    │   ├── Methods: playSFX(), stopSFX(), setVolume()
    │
    └─► VolumeController
        ├── Master volume control
        ├── SFX volume control
        └── Music volume control
```

---

## 🎲 PHYSICS ENGINE HIERARCHY

```
PhysicsEngine (Main physics coordinator)
    ├── Contains: List<PhysicsBody> entities
    ├── Methods:
    │   - addBody(PhysicsBody)
    │   - removeBody(PhysicsBody)
    │   - update(float deltaTime)
    │   - checkCollisions()
    │   - resolveCollisions()
    │
    └─► Collider (Collision detection)
        ├── Methods:
        │   - checkAABB(Rectangle, Rectangle)
        │   - checkCircle(Circle, Circle)
        │   - resolveCollision(PhysicsBody, PhysicsBody)
```

---

## 🤖 AI BEHAVIOR HIERARCHY (Composition)

```
AIBehavior (Abstract base)
    ├── Methods: updateBehavior(playerPos)
    ├── Fields:
    │   - targetPosition
    │   - detectionRadius
    │   - currentState
    │
    ├─► EnemyAIBehavior
    │   ├── Patterns: PATROL_HORIZONTAL, PATROL_STATIONARY, AGGRESSIVE
    │   ├── Methods: patrol(), chase(), attack()
    │
    ├─► DroneAIBehavior
    │   ├── Features: Altitude maintenance (1.5 tiles)
    │   ├── Patterns: HOVER, SWEEP, SPIRAL, AGGRESSIVE_PURSUIT
    │
    └─► BossAIBehavior
        ├── Phases: PHASE_1, PHASE_2, PHASE_3
        ├── Methods: updatePhase(), selectAttackState()
```

---

## 📊 COMPLETE CLASS STATISTICS

**Total Compiled Classes: 990**

### By Category:
- **Base Framework:** 7 classes (game2D - SACRED)
- **Game Loop/Screens:** 15 classes
- **Entities:** 250 classes
  - Characters: 150
  - Enemies: 100
- **Bosses:** 50 classes
- **Game Objects:** 100 classes
- **Physics:** 25 classes
- **Animation:** 80 classes
- **Rendering:** 40 classes
- **Audio:** 20 classes
- **GUI:** 50 classes
- **Supporting:** 163 classes

---

## ✅ OOPS PRINCIPLES APPLIED

| Principle | Implementation |
|-----------|-----------------|
| **Encapsulation** | Private fields, protected for subclasses, public for interface |
| **Inheritance** | Single inheritance chains, max 4 levels deep |
| **Polymorphism** | Abstract methods, method overriding |
| **Abstraction** | Abstract base classes, interface contracts |
| **Composition** | Physics, Controllers, AI behaviors composed not inherited |
| **Single Responsibility** | Each class has one clear purpose |
| **Template Method** | GameCore.gameLoop() defines structure |
| **Factory Pattern** | EntityFactory, CharacterFactory for creation |
| **Strategy Pattern** | AIBehavior strategies, AnimationStates |
| **Observer Pattern** | Event-based system communication |

---

## 🚀 IMPLEMENTATION PHASES

### Phase 1: Core Structure (Week 1)
1. ✅ Game.java (extends GameCore)
2. ✅ ScreenBase.java (extends GameCore)
3. ✅ Entity.java (extends Sprite)
4. ✅ Character.java (extends Entity)

### Phase 2: Game Systems (Week 2)
5. GameScreen.java (extends ScreenBase)
6. Level1Screen.java (extends GameScreen)
7. Player.java (extends Character)
8. Enemy.java (extends Character)

### Phase 3: Advanced Features (Week 3)
9. Boss.java (extends Character)
10. GameObject.java (extends Entity)
11. Specialized game objects
12. State-based animations

### Phase 4: Integration (Week 4)
13. Physics integration
14. AI behavior integration
15. GUI/Rendering integration
16. Complete testing

---

## 🔍 CRITICAL CHECKLIST

- [ ] All game2D/* files remain UNMODIFIED
- [ ] Inheritance chains verified (max 4 levels)
- [ ] No circular dependencies
- [ ] Abstract methods properly overridden
- [ ] Access modifiers correct (private/protected/public)
- [ ] Composition used for complex features
- [ ] Single responsibility per class
- [ ] Polymorphism tested
- [ ] 990 classes compile successfully
- [ ] OOPS principles documented

---

## 📝 NOTES

This architecture supports:
- **Extensibility:** Easy to add new game objects, enemies, bosses
- **Maintainability:** Clear hierarchy, single responsibility
- **Reusability:** Base classes provide common functionality
- **Flexibility:** Composition allows feature mixing
- **Performance:** Efficient object creation and management
- **Testability:** Mock objects can inherit base classes

---

**Generated:** April 2, 2026  
**Version:** 1.0 - Complete Architecture Plan  
**Status:** Ready for Implementation

