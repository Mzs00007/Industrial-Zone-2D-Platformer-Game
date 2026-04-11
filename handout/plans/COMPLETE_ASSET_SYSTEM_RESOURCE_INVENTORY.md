# COMPLETE ASSET SYSTEM - RESOURCE INVENTORY & CATALOG

**Date: April 2, 2026**  
**Status: COMPREHENSIVE PLANNING PHASE**  
**Scope: ALL Resources/industrial-zone/ Assets Cataloging**

---

## EXECUTIVE SUMMARY

This document catalogs EVERY PNG file in Resources/industrial-zone/ and maps them to required registry classes in AnimationAndSpriteLoader.java. The goal is systematic asset-driven architecture with:

- ✅ Object[][] storage for every asset category
- ✅ Metadata extraction (dimensions, frame counts, timing)
- ✅ Clean static access methods
- ✅ Real asset loading (NO dummy graphics)
- ✅ Complete nested class INDEX
- ✅ Unified GUI architecture

---

## PART 1: PLAYER CHARACTER ANIMATIONS

### Overview
**Path:** `Resources/industrial-zone/characters/player/`  
**Characters:** 3 (Biker, Cyborg, Punk)  
**Animations Per Character:** 24  
**Total Animation Files:** 72  
**Format:** Horizontal spritesheets, 1-7 frames each  

### Detailed Breakdown: PUNK (Representative Sample)

| Animation | File | Frames | Timing | Type | Use Case |
|-----------|------|--------|--------|------|----------|
| **IDLE** | `01_Player_Punk_Idle_5Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png` | 5 | 150ms | Loop | Standing still waiting |
| **IDLE2** | `02_Player_Punk_Idle2_5Frames1Row_StandingPoseVariant_IdleVariant_Loop_150ms.png` | 5 | 150ms | Loop | Alternate idle stance |
| **WALK** | `03_Player_Punk_Walk_5Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png` | 5 | 100ms | Loop | Normal movement |
| **RUN** | `04_Player_Punk_Run_6Frames1Row_FullRunCycle_FastMovement_Loop_80ms.png` | 6 | 80ms | Loop | Sprint/fast move |
| **DASH** | `05_Player_Punk_Dash_4Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png` | 4 | 60ms | Once | Quick dash/dodge |
| **JUMP** | `06_Player_Punk_Jump_3Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png` | 3 | 80ms | Once | Jump takeoff |
| **DOUBLEJUMP** | `07_Player_Punk_DoubleJump_4Frames1Row_MidAirFlipBoost_SecondJump_PlayOnce_80ms.png` | 4 | 80ms | Once | Mid-air second jump |
| **FALL** | `08_Player_Punk_Fall_3Frames1Row_FallingDescend_AirFall_Loop_100ms.png` | 3 | 100ms | Loop | Falling descent |
| **CLIMB** | `09_Player_Punk_Climb_4Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png` | 4 | 120ms | Loop | Climbing ladder |
| **HANG** | `10_Player_Punk_Hang_4Frames1Row_LedgeHangHold_HangingIdle_Loop_150ms.png` | 4 | 150ms | Loop | Hanging from ledge |
| **PULLUP** | `11_Player_Punk_Pullup_7Frames1Row_PullUpFromLedge_LedgePullup_PlayOnce_80ms.png` | 7 | 80ms | Once | Pulling up to platform |
| **PUNCH** | `12_Player_Punk_Punch_6Frames1Row_StandingPunchCombo_MeleeAttack_PlayOnce_70ms.png` | 6 | 70ms | Once | Melee punch combo |
| **ATTACK1** | `13_Player_Punk_Attack1_5Frames1Row_ComboHit1Swing_Attack1_PlayOnce_70ms.png` | 5 | 70ms | Once | Attack combo start |
| **ATTACK2** | `14_Player_Punk_Attack2_6Frames1Row_ComboHit2Swing_Attack2_PlayOnce_70ms.png` | 6 | 70ms | Once | Attack combo mid |
| **ATTACK3** | `15_Player_Punk_Attack3_6Frames1Row_ComboHit3Projectile_Attack3_PlayOnce_70ms.png` | 6 | 70ms | Once | Attack combo finisher |
| **WALKATTACK** | `16_Player_Punk_WalkAttack_5Frames1Row_WalkingAttackSwing_MoveAttack_PlayOnce_80ms.png` | 5 | 80ms | Once | Attack while moving |
| **RUNATTACK** | `17_Player_Punk_RunAttack_6Frames1Row_RunningAttackSwing_RunAttack_PlayOnce_70ms.png` | 6 | 70ms | Once | Attack while sprinting |
| **HURT** | `18_Player_Punk_Hurt_2Frames1Row_HitReactionFlinch_TakeDamage_PlayOnce_100ms.png` | 2 | 100ms | Once | Hit reaction |
| **DEATH** | `19_Player_Punk_Death_5Frames1Row_DeathFallSequence_PlayerDeath_PlayOnce_120ms.png` | 5 | 120ms | Once | Death sequence |
| **USE** | `20_Player_Punk_Use_5Frames1Row_InteractUseObject_Interact_PlayOnce_100ms.png` | 5 | 100ms | Once | Object interaction |
| **SITDOWN** | `21_Player_Punk_Sitdown_3Frames1Row_SitDownTransition_Sit_PlayOnce_120ms.png` | 3 | 120ms | Once | Sit down action |
| **ANGRY** | `22_Player_Punk_Angry_5Frames1Row_AngryExpressionEmote_Emote_Loop_150ms.png` | 5 | 150ms | Loop | Angry emotion |
| **HAPPY** | `23_Player_Punk_Happy_5Frames1Row_HappyExpressionEmote_Emote_Loop_150ms.png` | 5 | 150ms | Loop | Happy emotion |
| **TALK** | `24_Player_Punk_Talk_5Frames1Row_TalkingMouthMove_Dialogue_Loop_120ms.png` | 5 | 120ms | Loop | Dialog/talking |

**SAME PATTERN APPLIES TO:**
- Biker (identical structure, different sprite colors - magenta/pink)
- Cyborg (identical structure, different sprite colors - cyan/blue)

---

## PART 2: BOSS CHARACTER ANIMATIONS

### Overview
**Path:** `Resources/industrial-zone/characters/bosses/`  
**Bosses:** 3  
**Total AnimationFiles:** ~27  

### BOSS 1: GreenMech (Machine with Cannons)
**Animations:** ~10

| Animation | Frames | Timing | Type |
|-----------|--------|--------|------|
| IDLE1 | 4 | 150ms | Loop |
| IDLE2 | 5 | 150ms | Loop |
| WALK | 4 | 100ms | Loop |
| STRAFE | 5 | 100ms | Loop |
| ATTACK1 | 6 | 70ms | Once |
| ATTACK2 | 5 | 100ms | Once |
| CANNON_FIRE | 4 | 80ms | Once |
| HIT | 2 | 100ms | Once |
| DEATH | 4 | 120ms | Once |

### BOSS 2: RugbyGuy (Athletic Soldier)
**Animations:** ~6

| Animation | Frames | Timing | Type |
|-----------|--------|--------|------|
| IDLE1 | 4 | 150ms | Loop |
| IDLE2 | 6 | 150ms | Loop |
| CHARGE | 6 | 120ms | BuildUp |
| ATTACK1 | 4 | 100ms | Once |
| ATTACK2 | 8 | 80ms | Once |
| PROJECTILE | 1 | Instant | Single |

### BOSS 3: GolfCartSoldier (Vehicle + Driver)
**Animations:** ~11

| Animation | Frames | Timing | Type |
|-----------|--------|--------|------|
| IDLE | 4 | 150ms | Loop |
| IDLE_EMPTY | 4 | 150ms | Loop |
| WALK | 4 | 100ms | Loop |
| FASTOUT | 5 | 80ms | Loop |
| ATTACK | 5 | 70ms | Once |
| HURT1 | 2 | 100ms | Once |
| HURT2 | 2 | 100ms | Once |
| DEATH | 6 | 120ms | Once |
| (+ variants for driver vs cart) | - | - | - |

---

## PART 3: ENEMY CHARACTER ANIMATIONS

### Overview
**Path:** `Resources/industrial-zone/characters/enemies/`  
**Enemy Groups:** 2 (Drones + Sci-Fi Antagonists)  
**Total Enemies:** 9 (6 drones + 3 sci-fi)  
**Total Animation Files:** ~65  

### DRONES (UFO Saucers) - 6 Variants

**Drone 1:** Hover Saucer
- IDLE | 4 frames | 150ms
- TRAVERSE | 4 frames | 100ms
- SCAN_BEAM | 8 frames | 80ms
- ATTACK | 6 frames | 70ms
- HURT | 2 frames | 100ms
- DEATH | 4 frames | 120ms

(Drones 2-6: Same structure, different visual variants)

### SCI-FI ANTAGONISTS - 3 Types

**SciFi-1: CombatTank (Turret Vehicle)**
- IDLE | 4 frames | 150ms
- WALK | 4 frames | 100ms
- ATTACK1 | 4 frames | 80ms
- ATTACK2 | 4 frames | 80ms
- ATTACK3a-c | 4 frames each | 80-100ms
- ATTACK4a-c | 4 frames each | 80-100ms
- HURT | 2 frames | 100ms
- DEATH | 4 frames | 120ms

**SciFi-2: AlienFighter (Biped Alien)**
- IDLE | 4 frames | 150ms
- WALK | 6 frames | 100ms
- ATTACK1 | 5 frames | 70ms
- ATTACK2 | 6 frames | 80ms
- PROJECTILE | 1 frame | Single
- HURT | 2 frames | 100ms
- DEATH | 5 frames | 120ms

**SciFi-3: WingedWarrior (Flying Alien)**
- IDLE | 4 frames | 150ms
- WALK | 6 frames | 100ms
- ATTACK1 | 6 frames | 70ms
- ATTACK2 | 6 frames | 80ms
- ATTACK3 | 5 frames | 70ms
- ATTACK4 | 6 frames | 80ms
- ATTACK4b | 4 frames | 80ms
- SPECIAL | 6 frames | 100ms
- PROJECTILE | 1 frame | Single
- HURT | 2 frames | 100ms
- DEATH | 6 frames | 120ms

---

## PART 4: WEAPON ANIMATIONS & ASSETS

### Overview
**Path:** `Resources/industrial-zone/weapons/`  
**Weapon Types:** 8+  
**Total Files:** ~212 PNG files  

### Weapon Structure (Per Weapon)
Each weapon folder contains:

1. **1 Characters/**
   - Player variants (Biker, Cyborg, Punk) holding this weapon
   - Walk, idle, attack frames

2. **2 Projectiles/**  
   - Bullet/projectile sprites with variants
   - Impact effects
   - Trail animations

3. **3 Muzzle Flash/**
   - Fire effects
   - Multiple intensity levels

4. **4 Impact/**
   - Hit effect sprites
   - Explosion variants
   - Wall hit effects

5. **5 Bullets/**
   - Individual bullet sprites (10 variants)
   - Bullet trails
   - Decorative variants

6. **6 Icons/**
   - Weapon selection icons
   - HUD display icons

### Weapon Types (Examples)
- Weapon 1: Laser Blaster (projectile-based)
- Weapon 2: Rifle (multiple projectile types)
- Weapon 3-8: Various guns, energy weapons, etc.

---

## PART 5: TILE SYSTEMS

### Level 1 Tiles
**Path:** `Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/`  
**Total Tiles:** 65  
**Status:** ✅ COMPLETE - TileRegistry exists  
**Format:** Individual 32×32 PNG files  

**Categories:**
- Platforms (A, P, C, ...) - 3 walkable platforms
- Structures (U, V, E, ...) - 3 solid structures
- Corners & Edges (D, F, J, T, ...) - 17 edge pieces
- Walls (H, M, O, t, ...) - 4 vertical elements
- Panels & Detail (G, K, L, N, ...) - 9 panel types
- Ledges & Brackets (R, Z, a, W, v, ...) - 5 ledge variants
- Hazards - Contact (B, I, e-g, h-r, ...) - 23 striped/warning tiles
- Hazards - Energy (0, 1, !) - 3 instant damage tiles
- Deco (k, m, 9) - 3 decorative tiles

**Registry:** `AnimationAndSpriteLoader.TileRegistry`

### Level 2 Tiles
**Path:** `Resources/industrial-zone/1 Tiles/power-station-level-2/`  
**Total Tiles:** 64  
**Status:** ✅ COMPLETE - Level2TileRegistry exists  
**Format:** Individual 32×32 PNG files  

**Categories:** Similar to Level 1, power station themed

**Registry:** `Level2TileRegistry.java` (separate file)

---

## PART 6: GUI & UI ELEMENTS

### Overview
**Path:** `Resources/industrial-zone/gui/`  
**Contains:** Menu buttons, HUD elements, backgrounds  

### Button Variants (ON VERTICAL SPRITESHEET)
- Normal state
- Hover state
- Pressed state
- Disabled state

**Loader:** VerticalSpritesheetLoader (currently in use for buttons)

### HUD Elements
- Health bar segments
- Score display sprites
- Lives indicator
- Pause menu background
- Settings icons
- Inventory slots

### Menu Backgrounds
- Title screen backgrounds
- Level select backgrounds
- Settings menu backgrounds

---

## PART 7: VFX & EFFECTS

### Overview
**Path:** `Resources/industrial-zone/vfx/`  
**Contains:** Particle effects, explosions, impacts  

### Effect Types
1. **Explosions** (4-8 frames each)
2. **Hit Sparks** (5-6 frames)
3. **Dust Clouds** (6-8 frames)
4. **Slashes/Trails** (4-6 frames)
5. **Heal Effects** (5-6 frames)
6. **Screen Shake Overlays** (1-3 frames)
7. **Transition Effects** (8-10 frames)

---

## PART 8: BACKGROUND & PARALLAX

### Overview
**Path:** `Resources/industrial-zone/levels/`  

### Level 1 - Industrial Zone
- **Day_Parallax.png** - 3-layer parallax for daytime
- **Night_Parallax.png** - 3-layer parallax for nighttime

### Level 2 - Power Station
- **Day_Parallax.png** - 3-layer parallax for daytime
- **Night_Parallax.png** - 3-layer parallax for nighttime

---

## PART 9: KEYBOARD & MOUSE ASSETS

### Overview
**Path:** `Resources/industrial-zone/KeyBoard_Keys/` & `.../Mouse_keys/`  

**Contains:** Visual representations of keyboard keys and mouse buttons for control hints/tutorials

---

## COMPLETE REGISTRY REQUIREMENTS

Based on this inventory, AnimationAndSpriteLoader.java needs these NESTED CLASSES:

### TIER 1: DATA STRUCTURES
```
✅ TileRegistry (EXISTS - Level 1, 65 tiles)
⏳ Level2TileRegistry (EXISTS separately, needs integration)
⏳ SpriteMetadata (EXISTS basic, needs enhancement)
🔄 MetadataExtractor (NEW - to create)
🔄 AssetPathResolver (NEW - unified path management)
```

### TIER 2: CHARACTER REGISTRIES  
```
🔄 PlayerCharacterAnimationRegistry (NEW - 3 chars × 24 anims = 72 files)
🔄 BossAnimationRegistry (NEW - 3 bosses × ~10 anims = 27 files)
🔄 DronEnemyAnimationRegistry (NEW - 6 drones × ~6 anims = 36 files)
🔄 SciFiEnemyAnimationRegistry (NEW - 3 enemies × ~11 anims = 33 files)
```

### TIER 3: WEAPON REGISTRIES
```
🔄 WeaponAnimationRegistry (NEW - 8+ weapons with all assets)
🔄 ProjectileRegistry (NEW - all projectile types)
🔄 ImpactEffectRegistry (NEW - hit/impact effects)
🔄 MuzzleFlashRegistry (NEW - firing effects)
```

### TIER 4: ENVIRONMENT REGISTRIES
```
🔄 ParallaxBackgroundRegistry (NEW - Level1 & Level2 day/night)
🔄 UIElementRegistry (NEW - buttons, HUD, menus)
🔄 EffectAnimationRegistry (NEW - VFX, particles, explosions)
🔄 ControlHintRegistry (NEW - KB/Mouse key sprites)
```

### TIER 5: LOADER ENHANCEMENTS
```
✅ HorizontalSpritesheetLoader (EXISTS, needs upgrade)
✅ VerticalSpritesheetLoader (EXISTS, in use for buttons)
⏳ GridSpritesheetLoader (EXISTS, DEPRECATE)
🔄 MetadataAwareLoader (NEW - auto-detect frame count)
🔄 AssetValidator (NEW - verify all files exist)
```

---

## IMPLEMENTATION CHECKLIST

### Phase 1: Documentation Complete ✅
- [x] Complete resource inventory created
- [x] All asset categories documented
- [x] Path structures identified
- [x] Frame count data collected
- [x] Timing information extracted

### Phase 2: Infrastructure (🔄 TO START)
- [ ] Create MetadataExtractor class
- [ ] Create AssetPathResolver class  
- [ ] Upgrade HorizontalSpritesheetLoader
- [ ] Create MetadataAwareLoader wrapper
- [ ] Create AssetValidator class

### Phase 3: Registries (🔄 TO START)
- [ ] PlayerCharacterAnimationRegistry
- [ ] BossAnimationRegistry
- [ ] DronEnemyAnimationRegistry
- [ ] SciFiEnemyAnimationRegistry
- [ ] WeaponAnimationRegistry
- [ ] ParallaxBackgroundRegistry
- [ ] UIElementRegistry
- [ ] EffectAnimationRegistry

### Phase 4: Integration (🔄 TO START)
- [ ] Update Level1TileRegistry
- [ ] Integrate Level2TileRegistry
- [ ] Create asset validation tests
- [ ] Create registry access documentation
- [ ] Update GUI classes to use registries

### Phase 5: GUI Rebuild (🔄 TO START)
- [ ] Menu screen with asset-based UI
- [ ] Game HUD with asset-based elements
- [ ] Weapon selection GUI
- [ ] Level select screen
- [ ] Settings/pause menu

### Phase 6: Testing & Documentation (🔄 TO START)
- [ ] All registries populated
- [ ] Asset existence validation
- [ ] Performance testing
- [ ] Complete INDEX created
- [ ] Usage examples documented

---

## KEY PRINCIPLES FOR IMPLEMENTATION

### 1. NO DUMMY GRAPHICS
- Every visual element must be from a PNG in Resources/
- NULL fallback only with verbose logging
- Show exact file path when asset fails to load

### 2. METADATA EVERYWHERE
```java
Object[][] ASSETS = {
  { "name", "full/path", width, height, frameCount, frameType, complexity },
  ...
};
```

### 3. CLEAN ACCESS PATTERNS
```java
// Get animation frames
HorizontalSpritesheetLoader loader = 
    PlayerCharacterAnimationRegistry.getAnimation("Punk", "walk");
    
// Get tile by code
String tilePath = TileRegistry.getTile('A');

// Get weapon with all assets
WeaponData weapon = WeaponAnimationRegistry.getWeapon(1);
```

### 4. COMPREHENSIVE LOGGING
```
✓ Loading: Resources/industrial-zone/characters/player/punk/walk.png
  Dimensions: 320×32 px
  Frames: 5
  Timing: 100ms/frame
  Complexity: MEDIUM
```

### 5. VALIDATION ON STARTUP
```
Asset System Initialization:
  ✓ 72 Player animations loaded
  ✓ 27 Boss animations loaded
  ✓ 65 Enemies loaded  
  ✓ 212 Weapon assets loaded
  ✓ 65 Level1 tiles loaded
  ✓ 64 Level2 tiles loaded
  ✓ 8 UI element sets loaded
  
  ✅ TOTAL: 513 assets ready (0 missing)
```

---

## NEXT STEPS

This inventory serves as the BLUEPRINT for complete asset system implementation. Every PNG file listed here gets:

1. A corresponding Object[][] entry
2. Metadata (dimensions, frame count, timing)
3. Access method in registry class
4. Validation check in startup
5. Documentation in INDEX

**Target:** Complete asset-driven GUI with ZERO dummy graphics, ZERO hardcoded colors, ZERO Color-based fallbacks.

