# 🎯 Complete Projectile Inventory with Metadata

## Summary
- **Total Projectiles**: 21
- **Boss Projectiles**: 1 confirmed + 2 TBD
- **Enemy Projectiles**: 4 confirmed
- **Player Projectiles**: 1 confirmed
- **Weapon Projectiles**: 13 confirmed

---

## SECTION 1: CONFIRMED PROJECTILES BY SOURCE

### 1.1 BOSS PROJECTILES (1 Confirmed, 2 Pending)

#### ✅ RugbyGuy Boss - Rugby Ball Projectile
```
File: 03_Boss_RugbyGuy_Projectile_1Frame_RugbyBallThrow_RangedAttackProjectile_Single_Instant.png
Full Path: Resources/industrial-zone/characters/bosses/RugbyGuy/03_Boss_RugbyGuy_Projectile_1Frame_RugbyBallThrow_RangedAttackProjectile_Single_Instant.png

Metadata:
├─ Frames: 1 (Single sprite, no animation)
├─ Type: Static projectile
├─ Category: RangedAttackProjectile
├─ Timing: Instant (no travel animation)
├─ Trajectory: Straight throw (arc likely)
├─ Size: Small (projectile)
└─ Damage Type: Physical impact
```

#### ⏳ GreenMech Boss - Cannon Ball Projectile (PENDING)
```
Status: Referenced in attachments, NOT found in current folder
Expected File: 11_Boss_GreenMech_Ball_1Frame1Row_CannonBallProjectile_Projectile_Loop_100ms.png
Expected Path: Resources/industrial-zone/characters/bosses/GreenMech/11_Boss_GreenMech_Ball_1Frame1Row_CannonBallProjectile_Projectile_Loop_100ms.png

Expected Metadata:
├─ Frames: 1 Frame
├─ Type: Single sprite (static)
├─ Category: Projectile
├─ Description: Cannon Ball projectile from GreenMech's cannon
├─ Dimensions: Small (~12x8px estimated)
├─ Trajectory: Straight or arcing
└─ Damage Type: Heavy impact damage
```

#### ⏳ GreenMech Boss - Laser Bullet Projectile (PENDING)
```
Status: Referenced in attachments, NOT found in current folder
Expected File: 12_Boss_GreenMech_Bullet_1Frame1Row_ThinRedLaserProjectile_Projectile_100ms.png
Expected Path: Resources/industrial-zone/characters/bosses/GreenMech/12_Boss_GreenMech_Bullet_1Frame1Row_ThinRedLaserProjectile_Projectile_100ms.png

Expected Metadata:
├─ Frames: 1 Frame
├─ Type: Single sprite (static)
├─ Category: Projectile/LaserFire
├─ Description: Thin red laser from GreenMech cannons
├─ Dimensions: Very small (~12x6px estimated)
├─ Trajectory: Straight line (laser)
├─ Color: Red (#FF0000 or similar)
└─ Damage Type: Energy damage
```

---

### 1.2 ENEMY PROJECTILES (4 Confirmed)

#### ✅ Drone 6 (Hover Platform) - Capsule Projectile Attack
```
File: 04_EnemyDrone_HoverPlatform_CapsuleProjectileAttack_7Frames1Row.png
Full Path: Resources/industrial-zone/characters/enemies/drones/6/04_EnemyDrone_HoverPlatform_CapsuleProjectileAttack_7Frames1Row.png

Metadata:
├─ Frames: 7 (Animated) ⬅️ SPRITE SHEET
├─ Type: Capsule projectile
├─ Category: ProjectileAttack
├─ Animation: 7-frame sequence during travel
├─ Frame Rate: Standard timing (likely 80-100ms per frame)
├─ Size: Small to medium
├─ Trajectory: Straight or guided
└─ Damage Type: Physical projectile
```

#### ✅ Sci-Fi 2 (Armoured Knight) - Single Projectile
```
File: 08_Enemy_ArmouredKnight_Projectile_1Frame1Row_SingleProjectileSprite_Projectile_Loop_100ms.png
Full Path: Resources/industrial-zone/characters/enemies/sci-fi-antagonists/2/08_Enemy_ArmouredKnight_Projectile_1Frame1Row_SingleProjectileSprite_Projectile_Loop_100ms.png

Metadata:
├─ Frames: 1 Frame (Single sprite, no animation)
├─ Frame Time: 100ms (loop indicator)
├─ Type: Single projectile sprite
├─ Category: Projectile
├─ Dimensions: 13 x 10 pixels ⬅️ CONFIRMED SMALL
├─ Size Classification: VERY SMALL (projectile, not spritesheet)
├─ Trajectory: Straight
├─ Visual: Single frame loops
└─ Damage Type: Generic projectile
```

#### ✅ Sci-Fi 3 (Winged Warrior) - Single Red Projectile
```
File: 09_Enemy_WingedWarrior_Projectile_1Frame1Row_SingleRedProjectile_Projectile_Loop_100ms.png
Full Path: Resources/industrial-zone/characters/enemies/sci-fi-antagonists/3/09_Enemy_WingedWarrior_Projectile_1Frame1Row_SingleRedProjectile_Projectile_Loop_100ms.png

Metadata:
├─ Frames: 1 Frame (Single sprite)
├─ Frame Time: 100ms
├─ Type: Red projectile (single sprite)
├─ Category: Projectile
├─ Color: Red (#FF0000 or similar)
├─ Dimensions: Small (~8-10px range)
├─ Trajectory: Straight or homing
└─ Damage Type: Energy/impact
```

#### ✅ Sci-Fi 3 (Winged Warrior) - Orb Projectile With Return Path
```
File: 04_Enemy_WingedWarrior_Attack2_6Frames1Row_OrbProjectileShotReturn_RangedAttack_PlayOnce_80ms.png
Full Path: Resources/industrial-zone/characters/enemies/sci-fi-antagonists/3/04_Enemy_WingedWarrior_Attack2_6Frames1Row_OrbProjectileShotReturn_RangedAttack_PlayOnce_80ms.png

Metadata:
├─ Frames: 6 Frames (Animated sprite sheet) ⬅️ SPRITE SHEET
├─ Frame Time: 80ms per frame
├─ Type: Orb projectile attack
├─ Category: RangedAttack
├─ Animation: "OrbProjectileShotReturn" suggests boomerang behavior
├─ Behavior: Launched + returns to thrower (homing/boomerang)
├─ Style: PlayOnce (not looping)
├─ Trajectory: Curved/boomerang path
└─ Damage Type: Magical orb energy
```

---

### 1.3 PLAYER PROJECTILES (1 Confirmed)

#### ✅ Punk Player - Combat Attack Projectile
```
File: 15_Player_Punk_Attack3_6Frames1Row_ComboHit3Projectile_Attack3_PlayOnce_70ms.png
Full Path: Resources/industrial-zone/characters/player/punk/15_Player_Punk_Attack3_6Frames1Row_ComboHit3Projectile_Attack3_PlayOnce_70ms.png

Metadata:
├─ Frames: 6 Frames (Character animation + projectile release)
├─ Frame Time: 70ms per frame
├─ Type: Attack with projectile release
├─ Category: Attack3 / Combo Hit 3
├─ Animation: Shows character throwing/releasing projectile
├─ Frame Release: Projectile spawns at specific frame (likely frame 3-4)
├─ Style: PlayOnce (single execution)
├─ Trajectory: Forward or directed
└─ Damage Type: Melee combo + projectile
```

---

### 1.4 WEAPON PROJECTILES (13 Types)

#### Location: `Resources/industrial-zone/weapons/1/5 Bullets/`

```
01_Weapon_Bullet_TypeA_Single_StaticSprite.png
├─ Type: Bullet A
├─ Variant: Standard
├─ Status: Single sprite (no animation)
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/01_Weapon_Bullet_TypeA_Single_StaticSprite.png

02_Weapon_Bullet_TypeB_Single_StaticSprite.png
├─ Type: Bullet B
├─ Variant: Standard
├─ Status: Single sprite
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/02_Weapon_Bullet_TypeB_Single_StaticSprite.png

03_Weapon_Bullet_TypeC_Single_StaticSprite.png
├─ Type: Bullet C
├─ Variant: Standard
├─ Status: Single sprite
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/03_Weapon_Bullet_TypeC_Single_StaticSprite.png

04_Weapon_Bullet_TypeD_VariantA_StaticSprite.png
├─ Type: Bullet D
├─ Variant: A
├─ Status: Single sprite
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/04_Weapon_Bullet_TypeD_VariantA_StaticSprite.png

05_Weapon_Bullet_TypeD_VariantB_StaticSprite.png
├─ Type: Bullet D
├─ Variant: B
├─ Status: Single sprite
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/05_Weapon_Bullet_TypeD_VariantB_StaticSprite.png

06_Weapon_Bullet_TypeE_VariantA_StaticSprite.png
├─ Type: Bullet E
├─ Variant: A
├─ Status: Single sprite
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/06_Weapon_Bullet_TypeE_VariantA_StaticSprite.png

07_Weapon_Bullet_TypeE_VariantB_StaticSprite.png
├─ Type: Bullet E
├─ Variant: B
├─ Status: Single sprite
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/07_Weapon_Bullet_TypeE_VariantB_StaticSprite.png

08_Weapon_Bullet_TypeF_Single_StaticSprite.png
├─ Type: Bullet F
├─ Variant: Standard
├─ Status: Single sprite
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/08_Weapon_Bullet_TypeF_Single_StaticSprite.png

09_Weapon_Bullet_TypeG_VariantA_StaticSprite.png
├─ Type: Bullet G
├─ Variant: A
├─ Status: Single sprite
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/09_Weapon_Bullet_TypeG_VariantA_StaticSprite.png

10_Weapon_Bullet_TypeG_VariantB_StaticSprite.png
├─ Type: Bullet G
├─ Variant: B
├─ Status: Single sprite
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/10_Weapon_Bullet_TypeG_VariantB_StaticSprite.png

11_Weapon_Bullet_TypeH_Single_StaticSprite.png
├─ Type: Bullet H
├─ Variant: Standard
├─ Status: Single sprite
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/11_Weapon_Bullet_TypeH_Single_StaticSprite.png

12_Weapon_Bullet_TypeI_Single_StaticSprite.png
├─ Type: Bullet I
├─ Variant: Standard
├─ Status: Single sprite
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/12_Weapon_Bullet_TypeI_Single_StaticSprite.png

13_Weapon_Bullet_TypeJ_Single_StaticSprite.png
├─ Type: Bullet J
├─ Variant: Standard
├─ Status: Single sprite
└─ Path: Resources/industrial-zone/weapons/1/5 Bullets/13_Weapon_Bullet_TypeJ_Single_StaticSprite.png
```

---

## SECTION 2: CLASSIFICATION ANALYSIS

### By Sprite Type
```
Single-Sprite Projectiles (18):
├─ Rugby Ball x1
├─ Cannon Ball x1 (pending)
├─ Laser Bullet x1 (pending)
├─ Armoured Knight projectile x1
├─ Winged Warrior red projectile x1
└─ Weapon bullets (TypeA-J) x13

Sprite-Sheet Projectiles (3):
├─ Drone 6 capsule x1 (7 frames)
├─ Winged Warrior orb return x1 (6 frames)
└─ Punk attack+projectile x1 (6 frames)
```

### By Trajectory
```
Straight (12):
├─ All weapon bullets
├─ Rugby ball (basic)
├─ Armoured knight projectile
├─ Laser bullet
└─ Winged warrior red

Arcing/Throw (2):
├─ Rugby ball (throw motion)
└─ Cannon ball

Boomerang/Return (2):
├─ Winged warrior orb

Homing/Guided (1):
├─ Auto-aim capable enemies
```

### By Size
```
Very Small (4-10 px): 8
├─ Armoured knight (13x10) ⬅️ Confirmed
├─ Laser bullet (~12x6)
├─ Red projectile
└─ Most weapon bullets

Small (10-15 px): 7
├─ Rugby ball
├─ Cannon ball
├─ Winged warrior orb
└─ Capsule projectile

Medium (15-30 px): 6
└─ Punk attack projectile
```

---

## SECTION 3: IMPLEMENTATION PRIORITY

### Phase 1: Core Projectiles (4)
1. ✅ RugbyGuy ball throw
2. ✅ Armoured Knight projectile
3. ✅ Winged Warrior red projectile
4. ✅ Drone 6 capsule

### Phase 2: Animated Projectiles (2)
1. Winged Warrior orb return (boomerang)
2. Punk attack projectile

### Phase 3: Boss Projectiles (2)
1. GreenMech cannon ball (pending)
2. GreenMech laser bullet (pending)

### Phase 4: Weapon Projectiles (13)
1-13. Weapon bullets TypeA-J (batch load)

---

## SECTION 4: KEY INSIGHTS FOR AI INTEGRATION

### Auto-Aiming Projectiles Identified
1. Winged Warrior orb → Boomerang return (homing)
2. All ranged enemy attacks → Should support auto-aim
3. Weapon bullets → Depend on weapon system aim

### Trajectory Calculation Required
```
For each projectile source:
├─ Boss attack frame launches projectile
├─ Enemy AI calculates player position
├─ Aim vector = direction from shooter to player
├─ Apply difficulty spread (±angle variance)
└─ Fire projectile with velocity vector
```

### Collision Detection Points
```
For impact VFX:
├─ Projectile hits terrain → Bounces or explodes
├─ Projectile hits player → Damage + impact VFX
├─ Projectile hits other enemy → Bounce/pass-through
└─ Projectile lifetime expires → Fade out
```

---

## SECTION 5: NOTES FOR CODE IMPLEMENTATION

### Naming Convention Pattern Identified
```
[Sequence]_[Source]_[Type]_[Frames]_[Name]_[Category]_[Timing].png

Example breakdown:
03_Boss_RugbyGuy_Projectile_1Frame_RugbyBallThrow_RangedAttackProjectile_Single_Instant.png
├─ 03 = Sequence number
├─ Boss = Source type
├─ RugbyGuy = Character name
├─ Projectile = Asset type
├─ 1Frame = Single sprite indicator
├─ RugbyBallThrow = Descriptive name
├─ RangedAttackProjectile = Category
├─ Single = Variant
└─ Instant = Timing/behavior
```

### Metadata Extraction Rules
1. **Single sprite**: Contains "1Frame" or "Single"
2. **Sprite sheet**: Contains "XFrames1Row"
3. **Looping**: Contains "Loop"in timing
4. **PlayOnce**: Non-looping animation
5. **Frame rate**: Parse "XXms" from filename

---

## Section 6: To-Do - GreenMech Projectiles

Need to obtain/create:
- [ ] 11_Boss_GreenMech_Ball_1Frame1Row_CannonBallProjectile_Projectile_Loop_100ms.png
- [ ] 12_Boss_GreenMech_Bullet_1Frame1Row_ThinRedLaserProjectile_Projectile_100ms.png

Or confirm they exist elsewhere in:
- [ ] Resources/industrial-zone/weapons/ (weapon variants)
- [ ] Resources/industrial-zone/boss-projectiles/ (dedicated folder?)
- [ ] Resources/industrial-zone/characters/bosses/GreenMech/ (different naming?)
