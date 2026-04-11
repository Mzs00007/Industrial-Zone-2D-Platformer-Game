# Collision and Interaction System - Comprehensive Design Plan
**Date**: April 3, 2026  
**Status**: Brainstorm & Design Phase  
**Version**: 1.0

---

## 1. OVERVIEW: COLLISION vs INTERACTION

### 1.1 Collision System (Physical Boundaries)
**Purpose**: Detect when entities touch/overlap and PREVENT movement through solid objects

**What Collides**:
- ✅ Player character
- ✅ Enemy characters
- ✅ Bosses
- ✅ Projectiles
- ✅ Level 1 solid tiles (platforms, walls)
- ✅ Level 2 solid tiles (platforms, walls, machinery)
- ✅ Level 1 solid objects (boxes, barrels, doors, crates, machinery)
- ✅ Level 2 solid objects (tubes, containers, heavy equipment)
- ✅ Animated solid objects (moving platforms, gates)
- ✅ Hazard objects (spikes, lasers, crushing plates)

**What DOES NOT Collide**:
- ❌ Decorative objects (bushes, trees, signage)
- ❌ Background layers (parallax backgrounds)
- ❌ GUI/HUD elements
- ❌ Particle effects (explosions, sparks)
- ❌ Some VFX (blood splatter, dust clouds)

---

## 2. COLLISION SYSTEM ARCHITECTURE

### 2.1 Core Components

#### A. CollisionBox (AABB - Axis-Aligned Bounding Box)
```
┌─────────────────────────────────┐
│      Collision detection        │
│  using rectangular boundaries   │
├─────────────────────────────────┤
│ x, y: top-left position         │
│ width, height: dimensions       │
│ velocity: for sweep testing     │
│ type: STATIC/DYNAMIC/SENSOR     │
└─────────────────────────────────┘
```

**Types**:
1. **STATIC**: Never moves (platforms, walls, decorative)
   - No velocity
   - No gravity
   - Example: Platform tile

2. **DYNAMIC**: Moves with velocity + gravity (entities)
   - Has velocity vector
   - Affected by gravity
   - Example: Player, Enemy

3. **SENSOR**: Detects triggers without blocking (item pickups, hazards)
   - No physics response
   - Callback on entry/exit
   - Example: Damage zone, collectible pickup area

#### B. CollisionLayer System
```
Layer 0 (Tiles):        STATIC    - Solid tiles (platforms, walls)
Layer 1 (Objects):      STATIC    - Boxes, barrels, decorative
Layer 2 (Hazards):      SENSOR    - Spikes, lasers, pits
Layer 3 (Entities):     DYNAMIC   - Player, enemies, bosses
Layer 4 (Projectiles):  DYNAMIC   - Arrows, bullets, magical attacks
Layer 5 (Animated):     DYNAMIC   - Moving platforms, doors
Layer 6 (VFX):          IGNORE    - Particles, effects
```

**Collision Matrix**:
```
             Tiles  Objects  Hazards  Entities  Projectiles  Animated  VFX
Tiles        -      YES      NO       YES       YES          YES       NO
Objects      YES    NO       NO       YES       YES          YES       NO
Hazards      NO     NO       -        YES       YES          YES       NO
Entities     YES    YES      YES      -         YES          YES       NO
Projectiles  YES    YES      YES      YES       -            YES       NO
Animated     YES    YES      YES      YES       YES          -         NO
VFX          NO     NO       NO       NO        NO           NO        -
```

#### C. Collision Response Types
```
1. BLOCK:     Stop movement, slide along surface (Player on platform)
2. BOUNCE:    Reverse velocity (Ball bouncing)
3. DAMAGE:    Apply damage, push back (Hazard collision)
4. TRIGGER:   Callback, no physics (Pickup or zone entry)
5. DESTROY:   Remove entity (Projectile on impact)
6. STOP:      Instant halt (Barrier)
```

---

### 2.2 Collision Pipeline

```
┌──────────────────────────────────────────────────────────────┐
│                   EACH FRAME (60 FPS)                        │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│  1. BROAD PHASE (Fast)                                       │
│     └─ Spatial partitioning (Grid/Quadtree)                  │
│     └─ Quick overlap tests between layers                    │
│     └─ Output: Potential collision pairs                     │
│                                                              │
│  2. NARROW PHASE (Accurate)                                  │
│     └─ AABB vs AABB collision tests                          │
│     └─ Circle vs Circle collision tests                      │
│     └─ Polygon collision tests (if needed)                   │
│     └─ Output: Collision info (point, normal, depth)       │
│                                                              │
│  3. SWEEP TEST (Tunneling Prevention)                        │
│     └─ For fast-moving objects (projectiles, falling)        │
│     └─ Interpolate between last and current position        │
│     └─ Prevent objects passing through thin walls           │
│                                                              │
│  4. RESPONSE (Physics)                                       │
│     └─ Apply impulse-based resolution                        │
│     └─ Handle friction, bouncing                             │
│     └─ Trigger callbacks                                     │
│     └─ Separate overlapping bodies                           │
│                                                              │
│  5. CALLBACK PHASE                                           │
│     └─ onCollisionEnter()  - When collision starts          │
│     └─ onCollisionStay()   - While in collision             │
│     └─ onCollisionExit()   - When collision ends            │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

---

## 3. INTERACTION SYSTEM ARCHITECTURE

### 3.1 Interaction Types

#### A. DIALOGUE INTERACTIONS
- NPC dialogue sequences
- Multi-option responses
- Quest-related conversations
- Cutscene dialogues

#### B. PICKUP INTERACTIONS
- Health pickups
- Ammo pickups
- Power-up pickups
- Equipment/weapons
- Key items (keys, puzzle items)

#### C. ENVIRONMENT INTERACTIONS
- Doors (locked/unlocked, sliding/swing)
- Levers/switches (activate machinery)
- Platforms (moving, breakable)
- Hazards (spikes trigger damage, lasers)
- Portals (level transitions)

#### D. COMBAT INTERACTIONS
- Hit enemy (deal damage)
- Get hit by enemy (take damage)
- Pick up dropped loot
- Trigger traps

#### E. PUZZLE INTERACTIONS
- Move objects to activate switches
- Pattern-matching challenges
- Timed sequences
- Multi-step puzzles

---

### 3.2 Interaction Trigger System

```
┌─────────────────────────────────────────────────────────┐
│            INTERACTION DETECTION FLOW                   │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  1. RANGE CHECK                                         │
│     └─ Is player within interaction range?              │
│     └─ Default: 50 pixels from entity                   │
│     └─ Can be per-entity configurable                   │
│                                                         │
│  2. FACING CHECK                                        │
│     └─ Is player facing the entity?                     │
│     └─ Optional: Some interactions require facing        │
│     └─ Some work from any direction (pickups)           │
│                                                         │
│  3. INPUT CHECK                                         │
│     └─ Did player press interaction key? (E key)        │
│     └─ Or automatic on range (pickups)?                 │
│     └─ Or triggered by collision (hazards)?             │
│                                                         │
│  4. STATE CHECK                                         │
│     └─ Can this entity be interacted with now?          │
│     └─ Is door locked? Is switch already triggered?     │
│     └─ Has NPC already said goodbye?                    │
│                                                         │
│  5. EXECUTE INTERACTION                                 │
│     └─ Play animation/sound                             │
│     └─ Display dialogue/UI                              │
│     └─ Apply game state changes                         │
│     └─ Trigger callbacks                                │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## 4. DETAILED REQUIREMENTS BY ASSET CATEGORY

### 4.1 LEVEL 1 - Industrial Zone

#### Tiles (Collide: YES)
- Solid platform tiles → Full collision
- Wall tiles → Full collision
- Spike tiles → Hazard collision + damage
- Breakable tiles → Destructible collision
- Missing tiles (holes) → No collision (fall-through)

#### Objects (Collide: YES for solids)
- Boxes → STATIC collision, pushable? No (unless gameplay feature)
- Barrels → STATIC collision, can break for loot?
- Machinery → STATIC collision, can damage player
- Doors → STATIC collision, can open (interact)
- Crates → STATIC collision, breakable for pickups
- Decorative (vines, signage) → NO collision

#### Animated Objects (Collide: YES)
- Moving platforms → DYNAMIC collision with player
- Elevators → DYNAMIC collision, player rides with it
- Grinding hazards → SENSOR collision + damage

---

### 4.2 LEVEL 2 - Power Station

#### Objects (Collide: YES for solids)
- Tubes/Pipes → STATIC collision
- Containers → STATIC collision
- Heavy machinery → STATIC collision
- Power lines (visual only) → NO collision
- Control panels → STATIC collision, interactive (switches)
- Decorative (scaffolding non-solid parts) → NO collision

#### Animated Objects (Collide: YES)
- Moving machinery → DYNAMIC collision
- Breakable equipment → Destroyable collision
- Hazardous machinery (saws, crushers) → SENSOR + damage

---

### 4.3 CHARACTERS

#### Player (Collide: YES, DYNAMIC)
- **Collision Box**: ~20-32 pixels wide, full height minus 4 pixels feet offset
- **Affected by**:
  - Gravity (falls down)
  - Friction (slides on slopes)
  - Knockback (from hazards/enemies)
- **Can move**: Left, right, jump
- **Cannot move**: Through solid objects

#### Enemies (Collide: YES, DYNAMIC)
- **Collision Box**: Variable per enemy type
- **Affected by**:
  - Gravity (falls if not flying)
  - Friction
  - Knockback
- **Special**: Can push through some decorative objects
- **Special**: AI patrolling and chasing player

#### Bosses (Collide: YES, DYNAMIC)
- **Collision Box**: Larger (50-80+ pixels)
- **Affected by**: Same as enemies
- **Special**: Multiple collision zones (body, attack range)

---

### 4.4 PROJECTILES

#### Arrows, Bullets, Magic Bolts (Collide: YES, DYNAMIC)
- **Collision Box**: Small (2-8 pixels)
- **Response**: DESTROY on impact (bullet) or BOUNCE (arrow)
- **Affected by**: Gravity (arrows fall), no friction
- **Special**: Sweep testing to prevent tunneling

---

### 4.5 HAZARDS

#### Spikes, Lasers, Pits (Collide: SENSOR)
- **Type**: SENSOR (trigger-only)
- **Response on Contact**: 
  - Deal damage to player/enemies
  - Push back (knockback)
  - Possible stun effect
- **Special**: Some have animations (pulsing lasers)

---

### 4.6 PICKUPS & ITEMS

#### Health, Ammo, Power-ups (Collide: SENSOR)
- **Type**: SENSOR (trigger pickup)
- **Response**: 
  - Auto-pickup on player touch OR
  - Player presses E to pick up
  - Play pickup sound
  - Show UI feedback
  - Remove entity from world
- **Special**: Can expire if not picked up (time-limited)

---

## 5. CLASS STRUCTURE IN ANIMATIONANDSPRITELOADER

### 5.1 CollisionSystem Class

```java
public static class CollisionSystem {
    
    // ════════════════════════════════════════════════════════════════
    // COLLISION SYSTEM - Core collision detection and response
    // ════════════════════════════════════════════════════════════════
    // 
    // PURPOSE:
    //   Comprehensive collision detection and resolution system for
    //   all game entities. Handles AABB collisions, broad/narrow phase
    //   detection, sweep testing for fast-moving objects, and physics
    //   response (impulse, friction, bounce).
    //
    // RESPONSIBILITY:
    //   1. Manage collision layers (7 layer system)
    //   2. Broad-phase detection using spatial partitioning
    //   3. Narrow-phase AABB collision tests
    //   4. Sweep testing to prevent tunneling
    //   5. Physics resolution (impulse, friction, bounce)
    //   6. Trigger callbacks for collision events
    //
    // KEY CLASSES:
    //   - CollisionBox: Rectangle with velocity, type, filter
    //   - CollisionLayer: Manages entities in a specific layer
    //   - CollisionResult: Returned from collision tests
    //
    // USAGE PATTERNS:
    //   CollisionSystem collision = new CollisionSystem();
    //   
    //   // Register entity collision box
    //   CollisionBox playerBox = new CollisionBox(x, y, w, h, DYNAMIC);
    //   collision.registerCollider(playerBox, LAYER_ENTITIES);
    //   
    //   // Update each frame
    //   collision.update(deltaTime);
    //   
    //   // Check specific collision
    //   if (collision.collidesWith(playerBox, platformBox)) {
    //       // Handle collision
    //   }
    //   
    //   // Get all colliding objects
    //   List<CollisionBox> colliding = collision.getCollisionsFor(playerBox);
    //
    // PERFORMANCE NOTES:
    //   - Uses spatial hashing for broad phase (O(1) lookup)
    //   - Maximum 1000 objects per layer
    //   - Broad phase reduces narrow phase tests by ~90%
    //   - Sweep testing only for fast objects (v > 100 pixels/frame)
    //
    // ════════════════════════════════════════════════════════════════
    
    // Layer definitions
    public static final int LAYER_TILES       = 0;  // Platforms, walls
    public static final int LAYER_OBJECTS     = 1;  // Static game objects
    public static final int LAYER_HAZARDS     = 2;  // Damage zones, triggers
    public static final int LAYER_ENTITIES    = 3;  // Player, enemies, bosses
    public static final int LAYER_PROJECTILES = 4;  // Arrows, bullets
    public static final int LAYER_ANIMATED    = 5;  // Moving objects
    public static final int LAYER_VFX         = 6;  // Effects (ignored)
    
    // Type definitions
    public static final int TYPE_STATIC     = 0;
    public static final int TYPE_DYNAMIC    = 1;
    public static final int TYPE_SENSOR     = 2;
    
    // Methods will include:
    // - registerCollider(CollisionBox box, int layer)
    // - unregisterCollider(CollisionBox box)
    // - update(float deltaTime)
    // - collidesWith(CollisionBox a, CollisionBox b) -> boolean
    // - testCollision(CollisionBox a, CollisionBox b) -> CollisionResult
    // - sweepTest(CollisionBox from, CollisionBox to) -> CollisionResult
    // - getCollisionsFor(CollisionBox box) -> List<CollisionBox>
    // - resolveCollision(CollisionBox a, CollisionBox b)
}
```

### 5.2 InteractionSystem Class

```java
public static class InteractionSystem {
    
    // ════════════════════════════════════════════════════════════════
    // INTERACTION SYSTEM - Handle all game interactions
    // ════════════════════════════════════════════════════════════════
    //
    // PURPOSE:
    //   Manage all interactive world objects and player interactions.
    //   Handles dialogue, pickups, environment triggers, combat
    //   interactions, and puzzle mechanics.
    //
    // RESPONSIBILITY:
    //   1. Register and manage interactive objects
    //   2. Detect when player is in interaction range
    //   3. Queue and process interaction input
    //   4. Execute interaction callbacks and side effects
    //   5. Manage interaction state (cooldowns, locked states)
    //   6. Handle multi-step interactions (puzzles, quests)
    //
    // KEY CLASSES:
    //   - InteractiveObject: Base for all interactive entities
    //   - DialogueInteraction: NPC conversations
    //   - PickupInteraction: Item pickups
    //   - EnvironmentInteraction: Doors, levers, hazards
    //   - CombatInteraction: Combat feedback
    //
    // INTERACTION FLOW:
    //   1. Check distance from player to object (range check)
    //   2. Check if facing object (optional)
    //   3. Wait for player input (E key or proximity)
    //   4. Verify object state can be interacted with
    //   5. Execute interaction effect
    //   6. Trigger callbacks and side effects
    //   7. Play animations/sounds
    //
    // USAGE PATTERNS:
    //   InteractionSystem interaction = new InteractionSystem();
    //   
    //   // Register interactive object
    //   InteractiveObject door = new InteractiveObject(x, y, 50);
    //   door.setType(INTERACTION_DOOR);
    //   door.setCallback(() -> openDoor());
    //   interaction.register(door);
    //   
    //   // Update each frame (checks for interactions)
    //   interaction.update(playerX, playerY, inputHandler);
    //   
    //   // Check what's nearby and ready to interact
    //   InteractiveObject nearby = interaction.getNearestInteractable();
    //   if (nearby != null) {
    //       // Show UI prompt
    //   }
    //
    // STATE MANAGEMENT:
    //   - Locked/unlocked state (doors, chests)
    //   - Used/unused state (one-time pickups)
    //   - Cooldown timers (spam prevention)
    //   - Multi-state objects (door: locked -> open -> closed)
    //
    // ════════════════════════════════════════════════════════════════
    
    // Interaction types
    public static final int INTERACTION_NPC       = 0;  // Dialogue
    public static final int INTERACTION_PICKUP    = 1;  // Item pickup
    public static final int INTERACTION_DOOR      = 2;  // Door/portal
    public static final int INTERACTION_LEVER     = 3;  // Switch/mechanism
    public static final int INTERACTION_HAZARD    = 4;  // Damage trigger
    public static final int INTERACTION_PUZZLE    = 5;  // Puzzle element
    public static final int INTERACTION_COMBAT    = 6;  // Combat feedback
    
    // Methods will include:
    // - register(InteractiveObject obj)
    // - unregister(InteractiveObject obj)
    // - update(float playerX, float playerY, InputHandler input)
    // - getNearestInteractable() -> InteractiveObject
    // - executeInteraction(InteractiveObject obj)
    // - setObjectState(InteractiveObject obj, String state)
    // - getObjectState(InteractiveObject obj) -> String
    // - getInteractablesInRange(float x, float y, float range) -> List
}
```

---

## 6. IMPLEMENTATION CHECKLIST

### Phase 1: Core Collision System
- [ ] CollisionBox class (AABB structure)
- [ ] CollisionLayer management
- [ ] Broad-phase spatial hash grid
- [ ] Narrow-phase AABB tests
- [ ] Sweep testing for fast objects
- [ ] Collision response (block, bounce, damage)
- [ ] Layer filtering and collision matrix

### Phase 2: Core Interaction System
- [ ] InteractiveObject base class
- [ ] Registration and management
- [ ] Range detection system
- [ ] Input processing
- [ ] State management
- [ ] Callback system
- [ ] Cooldown tracking

### Phase 3: Integration
- [ ] Hook CollisionSystem into physics loop
- [ ] Hook InteractionSystem into input loop
- [ ] Test with player movement
- [ ] Test with enemy AI
- [ ] Test hazard damage
- [ ] Test pickup collection

### Phase 4: Asset Mapping
- [ ] Map Level 1 assets to collision types
- [ ] Map Level 2 assets to collision types
- [ ] Configure pickup interactions
- [ ] Configure hazard damage values
- [ ] Test full collision chains

---

## 7. PERFORMANCE CONSIDERATIONS

### Collision System
- **Spatial Hashing**: Divide world into grid cells
- **Broad Phase**: Only check cells with both objects
- **Object Pooling**: Reuse CollisionBox objects
- **Layer Filtering**: Don't test cross-layer collisions

### Interaction System
- **Spatial Indexing**: Only check nearby objects
- **Input Caching**: Don't process every frame
- **Callback Queuing**: Batch state changes
- **Cooldown Tracking**: Prevent spam

---

## 8. DEBUG/VISUALIZATION

### Collision Debug Mode
```
- Draw AABB boxes in red (colliders)
- Draw collision points in yellow
- Draw collision normals in green
- Show grid cells in grid pattern
```

### Interaction Debug Mode
```
- Draw interaction range circles in blue
- Show facing direction in cyan
- Highlight available interactions in green
- List interaction queue
```

---

**Document Version History**:
- **v1.0** (2026-04-03): Initial comprehensive design and planning
