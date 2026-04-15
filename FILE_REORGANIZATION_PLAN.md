# FILE REORGANIZATION PLAN
## Based on File Names and Functionality

---

## CURRENT ISSUES IDENTIFIED

### 1. FILES IN 2_MANAGERS THAT SHOULD BE ELSEWHERE

#### Animation Files → Should move to 5_Animation:
- `AnimationInitializer.java`
- `AnimationPlayer.java`

**Reason:** These are animation-specific utilities, not system managers.

---

## REORGANIZATION STRATEGY

### Priority 1: Move Animation Files from 2_Managers → 5_Animation
- AnimationInitializer.java
- AnimationPlayer.java

### Priority 2: Verify 3_Controllers Structure
- Currently contains: UI Screens, GUI components, Input handlers
- **Status:** Appears CORRECT ✓

### Priority 3: Verify 4_Entities Structure  
- Currently contains: Level1, Level2, LevelSystem, Enemies, etc.
- **Status:** Appears CORRECT ✓

### Priority 4: Verify Physics in 6_Physics
- Currently contains: BoundingBox, CollisionDetector, PhysicsSystem, etc.
- **Status:** Appears CORRECT ✓

### Priority 5: Verify AI in 7_AI
- Currently contains: AI, AIAgent, AIBehavior, AISystem, etc.
- **Status:** Appears CORRECT ✓

### Priority 6: Verify Utilities in 8_Utilities
- Currently contains: AudioSystem, AudioLibrary, and utilities
- **Status:** Appears CORRECT ✓

### Priority 7: Verify Enums in 9_Enums
- Currently contains: AssetEnumIndex, AudioAssets, CharacterAssets, GUIAssets, etc.
- **Status:** Appears CORRECT ✓

---

## EXECUTION PLAN

### Step 1: Move Animation Files
```
FROM: src/2_Managers/AnimationInitializer.java
TO:   src/5_Animation/AnimationInitializer.java

FROM: src/2_Managers/AnimationPlayer.java
TO:   src/5_Animation/AnimationPlayer.java
```

### Step 2: Verify No Other 2_Managers Files Need Moving
- Review remaining files in 2_Managers to confirm they are system managers
- Most appear correctly placed (Manager, Controller, System, Coordinator classes)

### Step 3: After File Movement
- Update all imports in moved files
- Update all imports in files that reference moved files
- Recompile to verify

---

## FILE COUNTS AFTER REORGANIZATION

| Folder | Before | After | Status |
|--------|--------|-------|--------|
| 1_Framework | 8 | 8 | UNCHANGED |
| 2_Managers | 60+ | ~58 | -2 files moved |
| 3_Controllers | 90+ | 90+ | UNCHANGED |
| 4_Entities | 37 | 37 | UNCHANGED |
| 5_Animation | 500+ | 502+ | +2 files moved |
| 6_Physics | 11 | 11 | UNCHANGED |
| 7_AI | 27 | 27 | UNCHANGED |
| 8_Utilities | 12 | 12 | UNCHANGED |
| 9_Enums | 9 | 9 | UNCHANGED |

---

## NEXT STEPS AFTER REORGANIZATION

1. ✓ Files organized by folder names
2. Update package declarations in moved files
3. Fix all imports across all files
4. Compile and verify
5. Commit to GitHub with message: "Reorganize files by folder names and fix imports"
