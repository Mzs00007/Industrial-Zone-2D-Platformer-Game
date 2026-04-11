# Phase 2-3 Implementation Guide  - Remaining Work

## 1. QUICK FIX: Update EnemyController (5 min)

**Location**: AnimationAndSpriteLoader.java, around line 2300

**Current Code Problem**:
- Old `updateAI()` uses manual distance checking
- No integration with new AIBehavior system

**Solution**: Replace the `updateAI()` method:

```java
// OLD CODE (DELETE this section):
public void updateAI(PhysicsUnitSystem.PhysicsBody playerPhysics) {
    float distanceToPlayer = Math.abs(physics.position.x - playerPhysics.position.x);
    
    if (distanceToPlayer < detectionRadius) {
        if (!isAlerted) {
            isAlerted = true;
            physics.velocity.x *= 1.25f;
        }
        transitionTo(AnimationState.ENEMY_CHASE);
    } else {
        if (isAlerted) {
            isAlerted = false;
            physics.velocity.x *= 0.8f;
        }
        transitionTo(AnimationState.ENEMY_IDLE);
    }
}

// NEW CODE (REPLACE with this):
private EnemyAIBehavior aiBehavior;  // ADD this field

public EnemyController(PhysicsUnitSystem.PhysicsBody physicsBody, float detectionRadius) {
    super(physicsBody);
    this.detectionRadius = detectionRadius;
    this.aiBehavior = new EnemyAIBehavior(physicsBody, detectionRadius, 3.0f, 
                                         EnemyAIBehavior.EnemyPattern.PATROL_HORIZONTAL);
    this.isAlerted = false;
}

public void updateAI(PhysicsUnitSystem.Vector2D playerPos) {
    // Use AIBehavior system instead of manual logic
    AnimationState nextState = aiBehavior.updateBehavior(playerPos);
    if (nextState != currentState) {
        transitionTo(nextState);
    }
    
    // Sync alert state
    isAlerted = aiBehavior.isAlerted();
}

// SIGNATURE CHANGE - callers must pass Vector2D:
// OLD: updateAI(playerPhysicsBody)
// NEW: updateAI(playerPhysicsBody.position)
```

---

## 2. CREATE DroneController Class (10 min)

**Location**: Insert AFTER EnemyController class (around line 2600)

**Complete Code**:

```java
// ════════════════════════════════════════════════════════════════
// DRONE CONTROLLER - Air-Based Enemy Units (1 Tile Above Ground)
// ════════════════════════════════════════════════════════════════
public static class DroneController extends EntityAnimationController {
    public static final float DRONE_HEIGHT_OFFSET = 1.5f;  // 1 tile = 1 meter above ground
    
    private DroneAIBehavior aiBehavior;
    private float baseAltitude;
    private boolean isAlerted;
    
    public enum DroneType {
        UFO_SAUCER,      // Type 1: Hovering drone
        JET_DRONE,       // Type 2: Fast flying
        TRANSPORT_DRONE  // Type 3: Heavy cargo carrier
    }
    
    private DroneType type;
    
    public DroneController(PhysicsUnitSystem.PhysicsBody physicsBody, 
                           float detectionRadius, DroneType droneType) {
        super(physicsBody);
        this.type = droneType;
        this.baseAltitude = physicsBody.position.y;
        
        // Position drone 1 tile above ground
        physicsBody.position.y = baseAltitude + DRONE_HEIGHT_OFFSET;
        
        // Initialize AI behavior with HOVER pattern
        this.aiBehavior = new DroneAIBehavior(physicsBody, detectionRadius, 4.0f,
                                              DroneAIBehavior.DronePattern.HOVER);
        this.isAlerted = false;
    }
    
    @Override
    protected void initializeAssets() {
        // Drone animation asset paths based on type
        switch (type) {
            case UFO_SAUCER:
                // UFO Saucer sprites (Type 1)
                stateToAssetPath.put(AnimationState.ENEMY_IDLE, 
                    "Resources/industrial-zone/characters/enemies/drones/1/01_EnemyDrone_UfoSaucer_Idle_3Frames1Row_HoveringStationary_DefaultIdle_Loop_150ms.png");
                stateToAssetPath.put(AnimationState.ENEMY_WALK, 
                    "Resources/industrial-zone/characters/enemies/drones/1/02_EnemyDrone_UfoSaucer_Movement_4Frames1Row_SmoothHoveringMove_Movement_Loop_100ms.png");
                stateToAssetPath.put(AnimationState.ENEMY_CHASE, 
                    "Resources/industrial-zone/characters/enemies/drones/1/03_EnemyDrone_UfoSaucer_Chase_4Frames1Row_FastHoveringPursuit_Chase_Loop_80ms.png");
                stateToAssetPath.put(AnimationState.ENEMY_ATTACK, 
                    "Resources/industrial-zone/characters/enemies/drones/1/04_EnemyDrone_UfoSaucer_Attack_3Frames1Row_EnergyBeamCharge_Attack_PlayOnce_100ms.png");
                stateToAssetPath.put(AnimationState.ENEMY_DEATH, 
                    "Resources/industrial-zone/characters/enemies/drones/1/05_EnemyDrone_UfoSaucer_Death_4Frames1Row_ExplosionDisappear_Death_PlayOnce_120ms.png");
                break;
            case JET_DRONE:
                // Jet Drone sprites (Type 2)
                stateToAssetPath.put(AnimationState.ENEMY_IDLE, 
                    "Resources/industrial-zone/characters/enemies/drones/2/01_EnemyDrone_JetDrone_Idle_Hovering_FastMovingUnit_Loop_150ms.png");
                // ... add other states
                break;
            case TRANSPORT_DRONE:
                // Transport Drone sprites (Type 3)
                stateToAssetPath.put(AnimationState.ENEMY_IDLE, 
                    "Resources/industrial-zone/characters/enemies/drones/3/01_EnemyDrone_TransportDrone_Idle_Stationary_CargoHold_Loop_200ms.png");
                // ... add other states
                break;
        }
    }
    
    @Override
    protected void initializeTransitions() {
        // Define drone state transitions
    }
    
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // Maintain altitude - drones never fall
        physics.position.y = baseAltitude + DRONE_HEIGHT_OFFSET;
        physics.velocity.y = 0;
        physics.isAffectedByGravity = false;
    }
    
    @Override
    protected void updatePhysicsForState(AnimationState state, float deltaTime) {
        // Drones maintain constant altitude
        physics.position.y = baseAltitude + DRONE_HEIGHT_OFFSET;
        physics.velocity.y = 0;
        physics.isAffectedByGravity = false;
        
        // Apply movement based on state
        switch (state) {
            case ENEMY_IDLE:
                physics.velocity.x = 0;
                break;
            case ENEMY_CHASE:
                // AI behavior handles velocity
                break;
            case ENEMY_ATTACK:
                physics.velocity.x = 0;
                break;
            default:
                break;
        }
    }
    
    public void updateAI(PhysicsUnitSystem.Vector2D playerPos) {
        AnimationState nextState = aiBehavior.updateBehavior(playerPos);
        if (nextState != currentState) {
            transitionTo(nextState);
        }
        isAlerted = aiBehavior.isAlerted();
    }
    
    public DroneType getDroneType() { return type; }
    public boolean isAlerted() { return isAlerted; }
    public float getAltitude() { return baseAltitude + DRONE_HEIGHT_OFFSET; }
}
```

---

## TESTING CHECKLIST

- [ ] EnemyController compiles and runs
- [ ] DroneController positioned 48px above ground
- [ ] Drones don't fall (velocity.y = 0 maintained)
- [ ] Player input mapping works (all 24 states)
- [ ] ParallaxSystem scrolls Level 2 correctly
- [ ] All animations render without errors

---

**Implementation Time**: ~45 minutes for all remaining work
