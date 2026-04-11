# INDUSTRIAL ZONE COMBAT GAME - COMPREHENSIVE DESIGN DOCUMENT
**Date**: April 5, 2026  
**Version**: 1.0 - Complete Game Flow & UI Design  
**Asset Base**: 1,174 assets from Resources/industrial-zone/  

---

## TABLE OF CONTENTS
1. [Game Flow Overview](#game-flow-overview)
2. [Master State Diagram](#master-state-diagram)
3. [Screen Layouts & UI Design](#screen-layouts--ui-design)
4. [Character Selection Design](#character-selection-design)
5. [Level Selection Design](#level-selection-design)
6. [How to Play Screen](#how-to-play-screen)
7. [Gameplay HUD Design](#gameplay-hud-design)
8. [Victory/Loss Screen](#victoryloss-screen)
9. [Asset Placement & Timing](#asset-placement--timing)
10. [Audio & Music Flow](#audio--music-flow)
11. [Technical Specifications](#technical-specifications)

---

## GAME FLOW OVERVIEW

### **Master Game Loop Flow**
```
┌─────────────────────────────────────────────────────────────────┐
│                         GAME START                               │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         ▼
        ┌────────────────────────────────┐
        │   INTRO SPLASH SCREEN (3sec)   │
        │  - Logo animation              │
        │  - Game title fade-in          │
        │  - Ambient background music    │
        └────────────┬───────────────────┘
                     │
                     ▼
        ┌────────────────────────────────┐
        │   MAIN MENU SCREEN             │
        │  - Play Button                 │
        │  - How to Play Button          │
        │  - Settings Button             │
        │  - Exit Button                 │
        └──────────┬─────────┬───────────┘
                   │         │
        ┌──────────▼┐    ┌───▼──────────┐
        │ PLAY FLOW │    │ HOW TO PLAY  │
        └──────────┬┘    └───┬──────────┘
                   │         │
        ┌──────────▼─────────▼──────────┐
        │  CHARACTER SELECTION SCREEN   │
        │  - 3 Character Cards          │
        │  - Description & Stats        │
        │  - Select Button Per Card     │
        └──────────┬────────────────────┘
                   │
                   ▼
        ┌────────────────────────────────┐
        │  LEVEL SELECTION SCREEN        │
        │  - 2 Level Cards               │
        │  - Difficulty Indicator        │
        │  - Select Button Per Level     │
        └────────────┬───────────────────┘
                     │
                     ▼
        ┌────────────────────────────────┐
        │    *** GAMEPLAY BEGINS ***     │
        │                                │
        │  - Level loads with parallax   │
        │  - Player spawns at start      │
        │  - Enemies spawn in waves      │
        │  - HUD visible (health, etc)   │
        │  - Music transitions           │
        └────────────┬───────────────────┘
                     │
                     ▼
        ┌────────────────────────────────┐
        │   COMBAT LOOP (30-60 sec)      │
        │                                │
        │  - Player movement (W/A/S/D)   │
        │  - Enemy AI pathfinding        │
        │  - Weapon firing               │
        │  - Collision detection         │
        │  - Damage calculations         │
        │  - VFX spawning (blood, smoke) │
        │  - Health regeneration         │
        │  - Wave progression            │
        └────────────┬───────────────────┘
                     │
           ┌─────────┴─────────┐
           │                   │
        ┌──▼─────┐         ┌───▼──────┐
        │  LOSE  │         │   WIN    │
        └──┬─────┘         └───┬──────┘
           │                   │
    ┌──────▼──────┐     ┌──────▼──────┐
    │ Loss Screen │     │ Win Screen  │
    │ - Retry btn │     │ - Stats     │
    │ - Main Menu │     │ - Next Lvl  │
    │ - Quit      │     │ - Main Menu │
    └──────┬──────┘     └──────┬──────┘
           │                   │
           └────────┬──────────┘
                    │
        ┌───────────▼──────────┐
        │  LOOP: New Selection │
        │  OR Exit to Menu     │
        └────────────┬─────────┘
                     │
        ┌────────────▼─────────────┐
        │  CHARACTER/LEVEL SELECT  │
        │  (or return to MAIN MENU)│
        └────────────┬─────────────┘
                     │
                     ▼
              (Loop repeats)
```

---

## MASTER STATE DIAGRAM

```
                          ┌──────────────┐
                          │   SPLASH     │
                          │   SCREEN     │
                          │  (3 seconds) │
                          └──────┬───────┘
                                 │ auto-advance
                                 ▼
                    ┌────────────────────────┐
                    │   MAIN MENU SCREEN     │
                    │                        │
                    │  ┌──────────────────┐  │
                    │  │ ► PLAY           │  │
                    │  ├──────────────────┤  │
                    │  │ ◆ HOW TO PLAY    │  │
                    │  ├──────────────────┤  │
                    │  │ ⚙ SETTINGS       │  │
                    │  ├──────────────────┤  │
                    │  │ ✕ EXIT           │  │
                    │  └──────────────────┘  │
                    └──┬─────────────────┬───┘
                       │                 │
         How to Play   │                 │  Play
              ↓        │                 │   ↓
          ┌───────┐    │             ┌───────────────────────┐
          │ HELP  │    │             │  CHARACTER SELECT     │
          │SCREEN │    │             │                       │
          └───┬───┘    │             │  ┌─────────────────┐  │
              │        │             │  │  CHAR 1  CHAR 2 │  │
              └─────┬──┘             │  │  CHAR 3         │  │
                    │                │  │                 │  │
             Resume │                │  │  [Descriptions] │  │
                    │                │  │  [Stats shown]  │  │
                    ▼                │  └─────────────────┘  │
          ┌──────────────────┐       └─────┬─────┬─────┬─────┘
          │  CHARACTER       │             │     │     │
          │  SELECTION       │             │ sel1│sel2 │sel3
          │  (if from help)  │             │     │     ▼
          │                  │             │     │  ┌────────────────┐
          └────────┬─────────┘             │     │  │  LEVEL SELECT  │
                   │                       │     │  │                │
                   └──┬──────┬─────────────┘     │  │  LEVEL 1  LVL2 │
                      │      │                   │  │                │
                      │      └───┬────────────────┤  │  [Difficulty]  │
                      │          │                │  │  [Description] │
                      ▼          ▼                │  └────┬──────┬────┘
            ┌──────────────────────────┐         │       │      │
            │ LEVEL SELECTION (branched)        │       │      │
            │                          │        │ sel1  │sel2  │
            │  LEVEL 1  |  LEVEL 2     │        │       │      ▼
            │  Click to select         │        │       │  ┌──────────┐
            └──┬────────┬──────────────┘        │       │  │Validate  │
               │        │                  ┌────┘       │  │Selection │
          Lvl1 │        │ Lvl2         OR ─┤           │  └──┬───────┘
               │        │                  │           │      │
               │        │                  │           │      │
               ▼        ▼                  └───────┬───┘      │
          ┌─────────────────────┐              ┌──▼──────────▼─────┐
          │   GAMEPLAY - LEVEL 1│              │  GAMEPLAY - LEVEL │
          │                     │              │  (1 or 2)         │
          │  HUD Visible        │              │                   │
          │  - Health Bar       │              │  HUD Visible      │
          │  - Ammo Counter     │              │  - Health Bar     │
          │  - Wave Counter     │              │  - Ammo Counter   │
          │  - Minimap          │              │  - Wave Counter   │
          │  - Mini Timer       │              │  - Minimap        │
          │                     │              │  - Mini Timer     │
          │  Parallax BG scroll │              │                   │
          │  Enemy waves spawn  │              │  Parallax BG      │
          │  Combat occurs      │              │  Enemy waves      │
          └────┬────────────────┘              │  Combat occurs    │
               │                              └────┬───────┬──────┘
               │ Player Dies                       │       │ Player Dies
               │ OR Waves Complete                 │       │ OR Waves Complete
               │                                   │       │
               ├───────────────┬───────────────────┘       │
               │               │                          │
               ▼               ▼                          ▼
        ┌────────────┐  ┌──────────────┐          ┌──────────────┐
        │  LOSS      │  │   VICTORY    │          │    LOSS      │
        │  SCREEN    │  │   SCREEN     │          │    SCREEN    │
        │            │  │              │          │              │
        │ ✕ DIED     │  │ ⭐ VICTORY   │          │ ✕ DIED       │
        │ Score: XXX │  │ Stats shown  │          │ Score: XXX   │
        │            │  │              │          │              │
        │ [Retry]    │  │ [Next Level] │          │ [Retry]      │
        │ [Menu]     │  │ [Main Menu]  │          │ [Menu]       │
        │ [Quit]     │  │ [Quit]       │          │ [Quit]       │
        └────┬────┬─┘  └──┬─────────┬──┘          └────┬────┬────┘
             │    │        │         │                  │    │
          R  │    │ Menu   │ Next Lvl│ Menu          R │    │ Menu
          e  │    │        │         │                 e │    │
          t  │    ▼        ▼         │                 t │    ▼
          r  │   MAIN    (goto char  │                 r │
          y  │   MENU    select if 2)│                 y │
             ▼             │         │                  ▼
          (Retry          │         │              (Retry
           Level 1)       │         │               Level)
                          │         │
                          ▼         ▼
                      ┌──────────────────┐
                      │ CHARACTER        │
                      │ SELECTION SCREEN │
                      │ (for Level 2)    │
                      └────┬─────────────┘
                           │
                           ▼
                      ┌──────────────────┐
                      │ GAMEPLAY - LVL 2 │
                      │ (harder waves)   │
                      └────┬─────────────┘
                           │
                        (continues...)
```

---

## SCREEN LAYOUTS & UI DESIGN

### **1. SPLASH SCREEN (Intro - 3 seconds)**

```
╔═══════════════════════════════════════════════════════════════╗
║                                                               ║
║                                                               ║
║                      [GAME LOGO ANIMATION]                   ║
║                      Fades in over 1 second                  ║
║                                                               ║
║                      INDUSTRIAL ZONE COMBAT                  ║
║                      (Title fades in - 2nd second)           ║
║                                                               ║
║                     (Loading... auto-advances)               ║
║                                                               ║
║  Background: Dark industrial tileset with parallax movement  ║
║  Music: Ambient intro theme (smooth, ~120 BPM)             ║
║                                                               ║
╚═══════════════════════════════════════════════════════════════╝

TIMING:
0-1s  : Logo fades in + scale animation
1-2s  : Title text fades in
2-3s  : Hold and auto-advance to Main Menu
```

---

### **2. MAIN MENU SCREEN**

```
╔═══════════════════════════════════════════════════════════════╗
║                                                               ║
║                   📜 INDUSTRIAL ZONE 📜                      ║
║                      COMBAT ARENA                            ║
║                                                               ║
║  ┌─────────────────────────────────────────────────────────┐ ║
║  │                                                         │ ║
║  │    ┌──────────────────────────────────┐                │ ║
║  │    │    ▶︎ PLAY GAME                 │ ◄─ Hover/Click │ ║
║  │    └──────────────────────────────────┘                │ ║
║  │                                                         │ ║
║  │    ┌──────────────────────────────────┐                │ ║
║  │    │    ◆ HOW TO PLAY                 │                │ ║
║  │    └──────────────────────────────────┘                │ ║
║  │                                                         │ ║
║  │    ┌──────────────────────────────────┐                │ ║
║  │    │    ⚙ SETTINGS                    │                │ ║
║  │    └──────────────────────────────────┘                │ ║
║  │                                                         │ ║
║  │    ┌──────────────────────────────────┐                │ ║
║  │    │    ✕ EXIT GAME                   │                │ ║
║  │    └──────────────────────────────────┘                │ ║
║  │                                                         │ ║
║  └─────────────────────────────────────────────────────────┘ ║
║                                                               ║
║  Background: Animated industrial zone level 1 (looping)     ║
║  Particles: Smoke VFX floating in background                ║
║  Music: Menu theme (~110 BPM, calm/strategic)              ║
║                                                               ║
╚═══════════════════════════════════════════════════════════════╝

UI ELEMENTS:
- Button width: 60% of screen
- Button height: 60px each
- Font: Bold, size 24-28
- Colors: Primary accent for selected button
- Spacing: 30px between buttons
- Sound: Click sound on selection

ANIMATIONS:
- Buttons scale up on hover (+5%)
- Slight glow effect on selected button
- Smoke particles drift slowly (background VFX)
```

---

### **3. HOW TO PLAY SCREEN**

```
╔════════════════════════════════════════════════════════════════╗
║               HOW TO PLAY - GAME CONTROLS                      ║
╠════════════════════════════════════════════════════════════════╣
║                                                                ║
║  ┌────────────────────────────────────────────────────────┐  ║
║  │  MOVEMENT                                              │  ║
║  │  ═════════════════════════════════════════════════     │  ║
║  │                                                        │  ║
║  │  [W] ▲                                                 │  ║
║  │      │                Move Up                          │  ║
║  │  [A] ◄─── ────► [D]  Move Left/Right                 │  ║
║  │      │                                                 │  ║
║  │  [S] ▼                Move Down                        │  ║
║  │                                                        │  ║
║  │  Or use ARROW KEYS for movement                       │  ║
║  └────────────────────────────────────────────────────────┘  ║
║                                                                ║
║  ┌────────────────────────────────────────────────────────┐  ║
║  │  COMBAT                                                │  ║
║  │  ═════════════════════════════════════════════════     │  ║
║  │                                                        │  ║
║  │  [MOUSE] Point & Click to Fire Weapon                │  ║
║  │  [SPACE] Reload Ammo                                  │  ║
║  │  [E]     Use Ability/Item (if available)              │  ║
║  │                                                        │  ║
║  │  Aim toward enemies and click to attack               │  ║
║  │  Watch ammo counter in top-right HUD                  │  ║
║  └────────────────────────────────────────────────────────┘  ║
║                                                                ║
║  ┌────────────────────────────────────────────────────────┐  ║
║  │  OBJECTIVES                                            │  ║
║  │  ═════════════════════════════════════════════════     │  ║
║  │                                                        │  ║
║  │  ① Defeat all enemy waves                             │  ║
║  │  ② Survive without your health reaching zero          │  ║
║  │  ③ Reduce enemy health to zero before they hit you    │  ║
║  │                                                        │  ║
║  │  Enemies: Watch for different enemy types             │  ║
║  │  - UFO Saucers (fast, ranged attacks)                │  ║
║  │  - Jet Drones (aggressive, moderate damage)           │  ║
║  │  - Transport Drones (slow, heavy damage)              │  ║
║  │  - Punks (fast-moving, melee attacks)                │  ║
║  │  - Rugby Players (tank, slow, high damage)            │  ║
║  │                                                        │  ║
║  └────────────────────────────────────────────────────────┘  ║
║                                                                ║
║  ┌────────────────────────────────────────────────────────┐  ║
║  │  AUDIO & VISUAL FEEDBACK                              │  ║
║  │  ═════════════════════════════════════════════════     │  ║
║  │                                                        │  ║
║  │  🔊 Sound Effects:                                    │  ║
║  │     - Weapon fire (distinct per weapon type)          │  ║
║  │     - Hit/damage sounds when enemies take damage      │  ║
║  │     - Enemy death sound                               │  ║
║  │     - Health warning beep (when < 30%)                │  ║
║  │                                                        │  ║
║  │  🎵 Music:                                            │  ║
║  │     - Menu theme (calm)                               │  ║
║  │     - Gameplay theme (intense, ~140 BPM)             │  ║
║  │     - Victory fanfare (triumphant)                    │  ║
║  │     - Defeat theme (ominous)                          │  ║
║  │                                                        │  ║
║  │  ✨ Visual Effects:                                   │  ║
║  │     - Bullet trails (weapon effect)                   │  ║
║  │     - Impact VFX (blood, sparks, smoke)              │  ║
║  │     - Health bar animations (smooth decrease)         │  ║
║  │     - Screenshake on major hits                       │  ║
║  │                                                        │  ║
║  └────────────────────────────────────────────────────────┘  ║
║                                                                ║
║               [BACK TO MENU]     [READY TO PLAY]             ║
║                                                                ║
╚════════════════════════════════════════════════════════════════╝
```

---

## CHARACTER SELECTION DESIGN

### **Character Selection Screen Layout**

```
╔═══════════════════════════════════════════════════════════════╗
║                   SELECT YOUR CHARACTER                       ║
║                                                               ║
║  ╔─────────────────────────────────────────────────────────╗ ║
║  ║                                                         ║ ║
║  ║  ┌──────────────────┐  ┌──────────────────┐           ║ ║
║  ║  │   CHARACTER 1    │  │   CHARACTER 2    │           ║ ║
║  ║  │                  │  │                  │           ║ ║
║  ║  │   [PLAYER IMG]   │  │   [PLAYER IMG]   │           ║ ║
║  ║  │    Animation     │  │    Animation     │           ║ ║
║  ║  │   (rotating)     │  │   (rotating)     │           ║ ║
║  ║  │                  │  │                  │           ║ ║
║  ║  │ Name: Biker      │  │ Name: Soldier    │           ║ ║
║  ║  │ Health: ████████ │  │ Health: ████████ │           ║ ║
║  ║  │ Speed:  ███████░ │  │ Speed:  ███████░ │           ║ ║
║  ║  │ Damage: ░░░░░███ │  │ Damage: ████████ │           ║ ║
║  ║  │                  │  │                  │           ║ ║
║  ║  │    [SELECT]      │  │    [SELECT]      │           ║ ║
║  ║  │                  │  │                  │           ║ ║
║  ║  └──────────────────┘  └──────────────────┘           ║ ║
║  ║                                                         ║ ║
║  ║                      ┌──────────────────┐              ║ ║
║  ║                      │   CHARACTER 3    │              ║ ║
║  ║                      │                  │              ║ ║
║  ║                      │   [PLAYER IMG]   │              ║ ║
║  ║                      │    Animation     │              ║ ║
║  ║                      │   (rotating)     │              ║ ║
║  ║                      │                  │              ║ ║
║  ║                      │ Name: Security   │              ║ ║
║  ║                      │ Health: ████████ │              ║ ║
║  ║                      │ Speed:  ███████░ │              ║ ║
║  ║                      │ Damage: ██░░░░░░ │              ║ ║
║  ║                      │                  │              ║ ║
║  ║                      │    [SELECT]      │              ║ ║
║  ║                      │                  │              ║ ║
║  ║                      └──────────────────┘              ║ ║
║  ║                                                         ║ ║
║  ╚─────────────────────────────────────────────────────────╝ ║
║                                                               ║
║              [BACK]                  [IF SELECTED: NEXT]     ║
║                                                               ║
║  Background: Industrial level 1 parallax (static)           ║
║  Music: Character selection music (~100 BPM, heroic)       ║
║                                                               ║
╚═══════════════════════════════════════════════════════════════╝

CHARACTER DESCRIPTIONS:

┌─────────────────────────────────────────────────────────────┐
│ CHARACTER 1: THE BIKER                                      │
├─────────────────────────────────────────────────────────────┤
│ Asset Path: Resources/industrial-zone/characters/            │
│            player/01_Player_Biker_[POSE]_*.png             │
│                                                               │
│ Description:                                                 │
│ A tough street veteran with years of combat experience.      │
│ Fast, agile, and deadly with quick reflexes.                │
│                                                               │
│ Stats:                                                       │
│ ▶ Health:   85/100  (Average durability)                    │
│ ▶ Speed:    95/100  (Very fast movement)                    │
│ ▶ Damage:   70/100  (Moderate damage output)                │
│                                                               │
│ Playstyle: Hit & Run tactics                                │
│ Weapon: Standard Pistol (rapid fire)                        │
│ Special: +15% movement speed in gameplay                    │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│ CHARACTER 2: THE SOLDIER                                    │
├─────────────────────────────────────────────────────────────┤
│ Asset Path: Resources/industrial-zone/characters/            │
│            player/02_Player_Soldier_[POSE]_*.png           │
│                                                               │
│ Description:                                                 │
│ A trained military operative with tactical training.        │
│ Balanced fighter with solid fundamentals and accuracy.      │
│                                                               │
│ Stats:                                                       │
│ ▶ Health:   95/100  (High durability)                      │
│ ▶ Speed:    75/100  (Good movement)                        │
│ ▶ Damage:   90/100  (High damage)                          │
│                                                               │
│ Playstyle: Aggressive direct engagement                     │
│ Weapon: Assault Rifle (burst fire)                         │
│ Special: +20% damage output in gameplay                     │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│ CHARACTER 3: SECURITY CHIEF                                 │
├─────────────────────────────────────────────────────────────┤
│ Asset Path: Resources/industrial-zone/characters/            │
│            player/03_Player_SecurityChief_[POSE]_*.png     │
│                                                               │
│ Description:                                                 │
│ A seasoned security veteran with defensive tactics.         │
│ Slower but incredibly durable and powerful.                 │
│                                                               │
│ Stats:                                                       │
│ ▶ Health:   120/100 (Extremely high durability) ⭐         │
│ ▶ Speed:    60/100  (Slower movement)                      │
│ ▶ Damage:   75/100  (Good damage)                          │
│                                                               │
│ Playstyle: Tank & hold position                             │
│ Weapon: Heavy Shotgun (slow but powerful)                  │
│ Special: +30% health, -20% movement speed                  │
└─────────────────────────────────────────────────────────────┘

ANIMATION DETAILS:
- Each character model rotates 360° on character card (5-second loop)
- Animation sourced from: Resources/industrial-zone/characters/player/
- Idle pose animation plays smoothly
- Card has subtle shadow underneath for depth

INTERACTION TIMING:
- Character hover: Card scales up 5%, slight glow appears
- Character selection: Button becomes highlighted
- Loading delay: 1.5 seconds transition to Level Select
```

---

## LEVEL SELECTION DESIGN

### **Level Selection Screen**

```
╔═══════════════════════════════════════════════════════════════╗
║                    SELECT YOUR DIFFICULTY LEVEL               ║
║                                                               ║
║  ┌────────────────────────────────────────────────────────┐  ║
║  │                                                        │  ║
║  │  ┌──────────────────────┐  ┌──────────────────────┐  │  ║
║  │  │                      │  │                      │  │  ║
║  │  │   LEVEL 1: BEGINNER  │  │   LEVEL 2: ADVANCED  │  │  ║
║  │  │                      │  │                      │  │  ║
║  │  │  [LEVEL BG IMAGE]    │  │  [LEVEL BG IMAGE]    │  │  ║
║  │  │   Industrial Zone 1  │  │   Industrial Zone 2  │  │  ║
║  │  │   (Corrupted Power)  │  │   (Steel Foundry)    │  │  ║
║  │  │                      │  │                      │  │  ║
║  │  │  ★★░░░  DIFFICULTY  │  │  ★★★★★  DIFFICULTY  │  │  ║
║  │  │                      │  │                      │  │  ║
║  │  │  Duration: 3 waves   │  │  Duration: 5 waves   │  │  ║
║  │  │  Enemies: 8-10       │  │  Enemies: 15-20      │  │  ║
║  │  │                      │  │                      │  │  ║
║  │  │  Description:        │  │  Description:        │  │  ║
║  │  │  "Fight through the  │  │  "The foundry burns  │  │  ║
║  │  │  corrupted power     │  │  with intense action │  │  ║
║  │  │  station. Drones     │  │  and powerful enemies │  │  ║
║  │  │  and basic enemies   │  │  lurk in the flames. │  │  ║
║  │  │  test your skills."  │  │  Only the strongest  │  │  ║
║  │  │                      │  │  survive here."      │  │  ║
║  │  │    [START LEVEL]     │  │    [START LEVEL]     │  │  ║
║  │  │                      │  │                      │  │  ║
║  │  └──────────────────────┘  └──────────────────────┘  │  ║
║  │                                                        │  ║
║  └────────────────────────────────────────────────────────┘  ║
║                                                               ║
║              [BACK TO CHARACTER SELECT]                      ║
║                                                               ║
║  Background: Animated industrial level (looping)           ║
║  Music: Level selection theme (~105 BPM)                   ║
║                                                               ║
╚═══════════════════════════════════════════════════════════════╝

LEVEL LAYOUT SPECIFICATIONS:

┌─────────────────────────────────────────────────────────────┐
│ LEVEL 1: CORRUPTED POWER STATION                            │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│ Asset Path: Resources/industrial-zone/                       │
│                                                               │
│ BACKGROUND LAYERS (Parallax):                               │
│  • Layer 0 (Sky):                                           │
│    - Tile: power_station_background_sky.png                │
│    - Scroll Speed: 0.2x (slowest)                          │
│    - Position: Top of screen                               │
│                                                               │
│  • Layer 1 (Distant Structures):                            │
│    - Tile: power_station_distantbuildings.png              │
│    - Scroll Speed: 0.4x                                     │
│    - Position: Upper-middle area                            │
│                                                               │
│  • Layer 2 (Platforms & Middle Ground):                     │
│    - Tile: power_station_level_1_platforms_*.png           │
│    - Scroll Speed: 0.6x                                     │
│    - Position: Middle area                                  │
│    - Includes platform collision surfaces                  │
│                                                               │
│  • Layer 3 (Foreground Props):                              │
│    - Tile: power_station_foreground_*.png                  │
│    - Scroll Speed: 0.8x                                     │
│    - Position: Foreground                                   │
│                                                               │
│ GAMEPLAY SPECS:                                             │
│  • Duration: ~2-4 minutes (depending on skill)             │
│  • Waves: 3 waves of enemies                               │
│  • Wave 1: 3-4 UFO Saucers (ranged attackers)            │
│  • Wave 2: 2-3 Jet Drones + 2 Transport Drones            │
│  • Wave 3: All previous types mixed (final)                │
│  • Playable Width: 2200px                                   │
│  • Playable Height: 600px (gravity active)                 │
│                                                               │
│ SPAWN POINTS:                                               │
│  • Player Spawn: Center-left (250, 300)                    │
│  • Enemy Spawn Left: (50, 250)                             │
│  • Enemy Spawn Right: (2150, 250)                          │
│                                                               │
│ HAZARDS: None (clean level)                                 │
│ ITEMS: Health boxes (appear after wave completion)         │
│                                                               │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│ LEVEL 2: STEEL FOUNDRY                                      │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│ Asset Path: Resources/industrial-zone/                       │
│                                                               │
│ BACKGROUND LAYERS (Parallax):                               │
│  • Layer 0 (Sky/Smoke):                                    │
│    - Tile: foundry_sky_smoky.png + VFX overlay            │
│    - Scroll Speed: 0.15x                                    │
│    - Position: Top of screen                               │
│    - Smoke VFX: 10-15 animated smoke clouds looping       │
│                                                               │
│  • Layer 1 (Distant Foundry):                               │
│    - Tile: foundry_distantstructure.png                   │
│    - Scroll Speed: 0.35x                                    │
│    - Position: Upper area                                   │
│                                                               │
│  • Layer 2 (Main Platforms):                                │
│    - Tile: foundry_level_2_platforms_*.png                │
│    - Scroll Speed: 0.6x                                     │
│    - Position: Middle playable area                         │
│    - Platform surfaces + lava/hazards                      │
│                                                               │
│  • Layer 3 (Foreground Fire/Props):                         │
│    - Tile: foundry_foreground_fire.png                     │
│    - Scroll Speed: 0.85x                                    │
│    - VFX: Spark particles floating upward                  │
│    - Position: Foreground                                   │
│                                                               │
│ GAMEPLAY SPECS:                                             │
│  • Duration: ~4-6 minutes (depending on skill)             │
│  • Waves: 5 waves of enemies (MORE INTENSE)               │
│  • Wave 1: 2 UFO Saucers + 2 Punks                        │
│  • Wave 2: 3 Jet Drones                                    │
│  • Wave 3: 2 Transport Drones + Rugby Player              │
│  • Wave 4: Mixed (4-5 enemies various types)              │
│  • Wave 5: BOSS WAVE (1 Heavy Boss + 2 escorts)           │
│  • Playable Width: 2400px                                   │
│  • Playable Height: 600px (gravity active)                 │
│                                                               │
│ SPAWN POINTS:                                               │
│  • Player Spawn: Center-left (250, 300)                    │
│  • Enemy Spawn Left: (50, 250)                             │
│  • Enemy Spawn Right: (2350, 250)                          │
│  • Hazard spawns: Lava projectiles from center             │
│                                                               │
│ HAZARDS:                                                     │
│  • Lava pits (kill zone - instant death)                   │
│  • Moving fire platforms (damage over time)                │
│  • Falling debris (knockback damage)                       │
│                                                               │
│ ITEMS:                                                       │
│  • Health boxes (more frequent than Level 1)              │
│  • Ammo refills (1-2 per wave)                            │
│  • Speed boosts (temporary +20% movement)                  │
│                                                               │
└─────────────────────────────────────────────────────────────┘
```

---

## GAMEPLAY HUD DESIGN

### **In-Game HUD Layout**

```
╔═══════════════════════════════════════════════════════════════╗
║                      GAMEPLAY SCREEN                          ║
║                                                               ║
║  ┌─────────────┐                             ┌─────────────┐ ║
║  │ HEALTH  ██ │                             │ AMMO  50/120│ ║
║  │ ████████░░ │  (85/100)                   │ ████░░░░░░ │ ║
║  │ SHIELD   ██│                             │ [R-Reload] │ ║
║  │ ░░░░░░░░░░ │                             │             │ ║
║  └─────────────┘                             └─────────────┘ ║
║                                                               ║
║                                                               ║
║  ┌──────────────────────────────────────┐                   ║
║  │ WAVE: 2/3  |  ENEMIES: 5  |  TIME: 45s │                   ║
║  └──────────────────────────────────────┘                   ║
║                                                               ║
║                 [GAME VIEWPORT - 2200x600]                   ║
║                                                               ║
║                [PLAYER CHARACTER CENTER]                      ║
║                   [ENEMIES SPAWN]                            ║
║                   [VFX/BULLETS]                              ║
║                [PARALLAX BACKGROUNDS]                        ║
║                                                               ║
║  ┌─────────────────────────────────────────────────────────┐ ║
║  │                    MINIMAP (top-right corner)            │ ║
║  │  ┌────────────────────────────────────────────────────┐ │ ║
║  │  │░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░        │ │ ║
║  │  │░░ [P] ░░░░░ [E] [E] ░░░░░░░░░░░░░░░░░░░░░░░░      │ ║
║  │  │░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░        │ │ ║
║  │  │ 1 0 0 px  (Scale: 1:20)  Level 1/2                │ │ ║
║  │  └────────────────────────────────────────────────────┘ │ ║
║  │  [P] = Player, [E] = Enemy, ░░ = Walls                  │ ║
║  └─────────────────────────────────────────────────────────┘ ║
║                                                               ║
║  ┌─────────────┐                             ┌─────────────┐ ║
║  │ [ESC] Pause │                             │ [MOUSE AIM] │ ║
║  └─────────────┘                             └─────────────┘ ║
║                                                               ║
╚═══════════════════════════════════════════════════════════════╝

HUD ELEMENT SPECIFICATIONS:

┌──────────────────────────────────────────────────────────────┐
│ TOP-LEFT: PLAYER STATUS PANEL                               │
├──────────────────────────────────────────────────────────────┤
│ • Position: (20px, 20px)                                    │
│ • Width: 150px, Height: 80px                                │
│                                                              │
│ • Health Bar:                                               │
│   - Name: "HEALTH"                                          │
│   - Display: XXX/100 (current/max)                         │
│   - Visual: Green bar (████████░░) with frame              │
│   - Damage alert: Flashes red if damage taken             │
│   - Sound: Damage sound plays on hit                      │
│                                                              │
│ • Shield Bar (if applicable):                               │
│   - Name: "SHIELD"                                          │
│   - Display: Subtle blue bar segment                       │
│   - Regenerates passively when not taking damage          │
│                                                              │
│ • Update Frequency: Every frame (smooth updates)            │
│ • Color: Green (healthy), Yellow (medium), Red (low)       │
│ • Font Size: 12px for values, 14px bold for "HEALTH"      │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│ TOP-RIGHT: AMMUNITION & WEAPON PANEL                        │
├──────────────────────────────────────────────────────────────┤
│ • Position: (right-170px, 20px)                            │
│ • Width: 150px, Height: 100px                              │
│                                                              │
│ • Ammo Counter:                                             │
│   - Display: "50/120" (current magazine / total reserves)  │
│   - Visual: Bullet icons + number bars                     │
│   - Low ammo alert: Flashes when < 15 bullets            │
│   - Reload indicator: Shows when reloading                │
│                                                              │
│ • Reload Instruction:                                       │
│   - Text: "[R] RELOAD AMMO"                               │
│   - Appears when ammo < 50%                               │
│   - Audio cue: Different sound when reload available      │
│                                                              │
│ • Weapon Display:                                           │
│   - Current weapon icon shown                              │
│   - Weapon name (e.g., "Pistol", "Rifle")                │
│   - Fire rate indicator (bullets/second)                   │
│                                                              │
│ • Update Frequency: Every frame                             │
│ • Color: Gold textyellow when reload available             │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│ TOP-CENTER: WAVE & ENCOUNTER PANEL                          │
├──────────────────────────────────────────────────────────────┤
│ • Position: Center horizontally, y: 40px                   │
│ • Width: 350px, Height: 50px                               │
│                                                              │
│ • Format: "WAVE: 2/3  |  ENEMIES: 5  |  TIME: 45s"        │
│                                                              │
│ • Wave Counter:                                             │
│   - Shows current wave / total waves (e.g., 2/3)          │
│   - Updates when new wave spawns                           │
│   - Special indicator for final wave                       │
│                                                              │
│ • Enemy Counter:                                            │
│   - Number of living enemies on screen                     │
│   - Updates in real-time as enemies die                   │
│   - Shows "0" = all defeated                              │
│                                                              │
│ • Wave Timer:                                               │
│   - Countdown timer (starts at wave spawn time)            │
│   - Shows elapsed time for current wave                    │
│   - Audio cue when 10 seconds remaining                   │
│                                                              │
│ • Update Frequency: Every 0.1 seconds                      │
│ • Font: 16px bold, subtle background glow                 │
│ • Color: White text with gold accents                     │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│ BOTTOM-CENTER: CONTROLS HINT                                │
├──────────────────────────────────────────────────────────────┤
│ • Position: Center horizontally, near bottom               │
│ • Width: 300px, Height: 40px                               │
│                                                              │
│ • Display: "[W/A/S/D] Move  [MOUSE] Aim & Fire  [ESC] Pause" │
│                                                              │
│ • Updates dynamically based on context:                    │
│   - During gameplay: Shows action buttons                  │
│   - During reload: Shows reload progress                  │
│   - During dialogue: Shows dialogue options               │
│                                                              │
│ • Visibility: Fade in/out based on player actions         │
│ • Font: Small, 10px, low opacity (subtle)                │
│ • Color: Light gray with key highlights in yellow        │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│ MINIMAP (Top-right corner)                                  │
├──────────────────────────────────────────────────────────────┤
│ • Position: (right-260px, 20px)                            │
│ • Size: 240px × 100px                                      │
│ • Scale: 1:20 ratio (real level is 20x larger)            │
│                                                              │
│ • Visual Elements:                                          │
│   - Background: Dark semi-transparent background          │
│   - Walls: Gray rectangles (collision boundaries)         │
│   - Player: Blue square/circle ([P])                     │
│   - Enemies: Red squares ([E]) for each enemy            │
│   - Spawn points: Orange markers                          │
│   - Hazards: Purple markers (Level 2 only)               │
│                                                              │
│ • Updates: Every 50ms (20 FPS minimap refresh)            │
│ • Interaction: Click on minimap to center camera?         │
│ • Shows level bounds clearly                              │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

---

## VICTORY/LOSS SCREEN

### **Victory Screen**

```
╔═══════════════════════════════════════════════════════════════╗
║                                                               ║
║                      ⭐ LEVEL COMPLETE! ⭐                    ║
║                                                               ║
║                       *** VICTORY ***                         ║
║                                                               ║
║  ┌─────────────────────────────────────────────────────────┐ ║
║  │  MATCH STATISTICS                                       │ ║
║  │  ═══════════════════════════════════════════════════    │ ║
║  │                                                         │ ║
║  │  Character Used: BIKER                                 │ ║
║  │  Level Completed: 1 of 2 (Corrupted Power Station)   │ ║
║  │                                                         │ ║
║  │  ┌─ COMBAT STATS ──────────────────────────────────┐  │ ║
║  │  │  Enemies Defeated:  15 / 15  (100%)            │  │ ║
║  │  │  Accuracy:          72%  (87/120 shots)        │  │ ║
║  │  │  Damage Dealt:      1,435 HP                   │  │ ║
║  │  │  Damage Taken:      22 HP                      │  │ ║
║  │  │  Time Elapsed:      2:43 minutes               │  │ ║
║  │  │                                                 │  │ ║
║  │  │  Final Health:      78/100 ✓                   │  │ ║
║  │  │                                                 │  │ ║
║  │  └─────────────────────────────────────────────────┘  │ ║
║  │                                                         │ ║
║  │  ┌─ PERFORMANCE RATING ────────────────────────────┐  │ ║
║  │  │  ★★★★☆   EXCELLENT  (4/5 stars)               │  │ ║
║  │  │  Bonus: +500 XP for Perfect Health Retention   │  │ ║
║  │  │  Bonus: +250 XP for High Accuracy              │  │ ║
║  │  │  Bonus: +150 XP for Beat Time Limit            │  │ ║
║  │  │                                                 │  │ ║
║  │  │  Total Score: 2,335 XP                         │  │ ║
║  │  └─────────────────────────────────────────────────┘  │ ║
║  │                                                         │ ║
║  └─────────────────────────────────────────────────────────┘ ║
║                                                               ║
║           [NEXT LEVEL]  [CHAR SELECT]  [MAIN MENU]           ║
║                                                               ║
║  Background: Victory effects (particle celebrations)        ║
║  Music: Victory fanfare theme (triumphant, ~100 BPM)       ║
║  Sound: Celebratory sound effects + ding sounds            ║
║                                                               ║
╚═══════════════════════════════════════════════════════════════╝
```

### **Defeat Screen**

```
╔═══════════════════════════════════════════════════════════════╗
║                                                               ║
║                        ✗ DEFEAT ✗                            ║
║                                                               ║
║                    MISSION FAILED                            ║
║                                                               ║
║  ┌─────────────────────────────────────────────────────────┐ ║
║  │  FINAL STATISTICS                                       │ ║
║  │  ═══════════════════════════════════════════════════    │ ║
║  │                                                         │ ║
║  │  Character Used: BIKER                                 │ ║
║  │  Level Attempted: 1 of 2 (Corrupted Power Station)   │ ║
║  │                                                         │ ║
║  │  ┌─ DEFEAT SUMMARY ────────────────────────────────┐  │ ║
║  │  │  Wave Reached: 2/3  (66% complete)             │  │ ║
║  │  │  Enemies Defeated: 8 / 15  (53%)               │  │ ║
║  │  │  Damage Dealt: 815 HP                          │  │ ║
║  │  │  Damage Taken: 103 HP  (OVERKILL)              │  │ ║
║  │  │  Time Survived: 1:52 minutes                   │  ║
║  │  │                                                 │  │ ║
║  │  │  Final Health: 0/100 ✗ (ELIMINATED)            │  │ ║
║  │  │  Last Enemy Hit: Jet Drone (Heavy Laser)       │  │ ║
║  │  │                                                 │  │ ║
║  │  │  Cause of Defeat: Overwhelmed by Wave 2       │  │ ║
║  │  └─────────────────────────────────────────────────┘  │ ║
║  │                                                         │ ║
║  │  ┌─ LESSON LEARNED ────────────────────────────────┐  │ ║
║  │  │  💡 Tip: Use movement to avoid ranged attacks  │  │ ║
║  │  │  💡 Tip: Prioritize eliminating Jets first     │  │ ║
║  │  │  💡 Tip: Watch your health bar constantly     │  │ ║
║  │  │                                                 │  │ ║
║  │  │  Progress: 25 XP earned for effort             │  │ ║
║  │  └─────────────────────────────────────────────────┘  │ ║
║  │                                                         │ ║
║  └─────────────────────────────────────────────────────────┘ ║
║                                                               ║
║            [RETRY LEVEL]  [CHAR SELECT]  [MAIN MENU]         ║
║                                                               ║
║  Background: Dark filtered gameplay screen (fallen effect)  ║
║  Music: Defeat theme (ominous, ~80 BPM, minor key)         ║
║  Sound: Sad/defeated sound effect                          ║
║                                                               ║
╚═══════════════════════════════════════════════════════════════╝
```

---

## ASSET PLACEMENT & TIMING

### **Asset Detailed Timeline & Positioning**

```
INTRO SEQUENCE TIMELINE (0-5 seconds):

Time  | Asset                  | Position          | Action
------|------------------------|-------------------|---------------------------
0:00  | Splash Screen BG       | Full screen       | Fade in (1s), hold (2s)
      | (industrial-zone/tiles)| 0,0 - 1024x768   |

1:00  | Game Logo              | Center screen     | Scale up + glow effect
      | (industrial-zone/gui)  | 400,250           | Fade in (1s)
      
2:00  | Title Text             | Center-lower      | Fade in + drop shadow
      | "INDUSTRIAL ZONE..."   | 512,400           | Stay for 3 seconds
      
3:00  | Transition Effects     | Screen overlay    | Cross-fade to Main Menu
      | Smoke VFX (fade out)   | Full screen       |

3:00+ | Main Menu Screen Begin | (See separate)    | Music cross-fade
────────────────────────────────────────────────────────────────────────

CHARACTER SELECT POSITIONING:

Screen: 1024x768 pixels
Background: Level 1 (scrolling at 0.2x speed)

┌──────────────────────────────────────────────────┐
│           CHARACTER 1        CHARACTER 2         │ Y: 150-250px
│           (350, 200)         (680, 200)          │
│          [150x180 card]      [150x180 card]      │
│           ┌─────────┐         ┌─────────┐        │
│           │ [CHAR] │         │ [CHAR] │        │
│           │  Idle  │         │  Idle  │        │
│           │Rotating│         │Rotating│        │
│           └─────────┘         └─────────┘        │
│           Stats Below          Stats Below       │
│                                                  │
│                    CHARACTER 3                   │ Y: 350-450px
│                    (512, 400)                    │
│                  [150x180 card]                  │
│                   ┌─────────┐                    │
│                   │ [CHAR] │                    │
│                   │  Idle  │                    │
│                   │Rotating│                    │
│                   └─────────┘                    │
│                   Stats Below                    │
│                                                  │
└──────────────────────────────────────────────────┘

Character Animation Details:
- Each character model: 150x180 pixels
- Animation: Idle pose, 360° rotation
- Rotation speed: 1 full rotation per 5 seconds
- Source assets:
  Character 1: Resources/industrial-zone/characters/player/01_*.png
  Character 2: Resources/industrial-zone/characters/player/02_*.png
  Character 3: Resources/industrial-zone/characters/player/03_*.png

SELECT buttons:
- Position: Below each card (Y+180px, centered)
- Size: 100px wide × 40px tall
- Hover effect: Scale +5%, bright glow
- Click effect: Instant selection, 1.5s fade-out
─────────────────────────────────────────────────────

LEVEL SELECT POSITIONING:

Screen: 1024x768 pixels
Background: Level preview images (semi-transparent gameplay preview)

┌──────────────────────────────────────────────────┐
│           LEVEL 1              LEVEL 2           │ Y: 150-450px
│           (200, 200)           (650, 200)        │
│         [250x300 card]       [250x300 card]      │
│         ┌──────────────┐     ┌──────────────┐   │
│         │              │     │              │   │
│         │  LEVEL IMAGE │     │  LEVEL IMAGE │   │
│         │  (Gameplay   │     │  (Gameplay   │   │
│         │   Preview)   │     │   Preview)   │   │
│         │              │     │              │   │
│         │ Level Stats: │     │ Level Stats: │   │
│         │ ★★░░░        │     │ ★★★★★        │   │
│         │ Duration: 3  │     │ Duration: 5  │   │
│         │ Difficulty   │     │ Difficulty   │   │
│         │ 08-10 enemys │     │ 15-20 enemys │   │
│         └──────────────┘     └──────────────┘   │
│             (Dropdown)          (Dropdown)       │
│          [Start Level]       [Start Level]       │
│                                                  │
└──────────────────────────────────────────────────┘

Level Preview:
- Level 1 Preview: power_station_background.png (semi-transparent)
- Level 2 Preview: foundry_background.png (semi-transparent)
- Preview Size: 250x300 pixels
- Opacity: 70% (allow stats text behind to show)
─────────────────────────────────────────────────────

GAMEPLAY SCREEN LAYOUT:

Screen: 1024x768 pixels
Gameplay Viewport: 1024x600 pixels (bottom 600 pixels)
HUD Space: 1024x168 pixels (top area)

┌────────────────────────────────────────────────────┐
│ HUD AREA (168px tall)                              │
│ ┌──────────────┐      ┌────────────┐     ┌──────┐ │
│ │ Health/Shield│      │ Wave/Enemy │     │ Ammo │ │
│ │ (20,20)      │      │ (center)   │     │(R-W-20)
│ └──────────────┘      └────────────┘     └──────┘ │
│                                                    │
│ ┌────────────────────────────────────────────────┐ │
│ │           MINIMAP (Top-right)                  │ │
│ │           (240x100, Top-right)                 │ │
│ └────────────────────────────────────────────────┘ │
├────────────────────────────────────────────────────┤
│                                                    │
│  GAMEPLAY VIEWPORT (1024x600 continuous scroll)  │
│  ┌──────────────────────────────────────────────┐ │
│  │ [BG LAYER 0] Sky/Distant (slowest scroll)   │ │
│  │ [BG LAYER 1] Structures                     │ │
│  │ [BG LAYER 2] Platforms (collision active)  │ │
│  │                                              │ │
│  │              [PLAYER @ CENTER]               │ │
│  │              (512, 400 screen center)       │ │
│  │              [Width: 64px, Height: 96px]   │ │
│  │                                              │ │
│  │  [ENEMIES] ←──→ [PLAYER] ←──→ [ENEMIES]    │ │
│  │                                              │ │
│  │ [BG LAYER 3] Foreground fire/props (fast)  │ │
│  │ [VFX] Smoke, blood, sparks, bullets        │ │
│  │ [PARTICLES] Floating debris                │ │
│  │                                              │ │
│  └──────────────────────────────────────────────┘ │
│                                                    │
└────────────────────────────────────────────────────┘

PARALLAX SCROLLING SPEEDS (as gameplay continues):

Actually moving horizontally (camera follows player):
- Camera position = Player X - 512 (centered)
- But clamped to level bounds (0 - 1176, to keep edges onscreen)

BG Scroll Calculation:
- Actual Camera X = Player.X - 512 (clamped)
- Layer 0 offset = CameraX * 0.15  (Sky - very slow)
- Layer 1 offset = CameraX * 0.35  (Structures)
- Layer 2 offset = CameraX * 0.65  (Platforms - most detail)
- Layer 3 offset = CameraX * 0.85  (Foreground fire - fastest)

Result: Background layers shift at different speeds,
creating visual depth illusion.
```

---

## AUDIO & MUSIC FLOW

```
COMPLETE AUDIO SEQUENCE:

SPLASH SCREEN (0-3 seconds):
├─ Ambient Intro Theme
│  ├─ Duration: 4 seconds
│  ├─ BPM: 100, Key: Major
│  ├─ Instruments: Strings + Synth pads
│  ├─ Volume: 70%
│  └─ Fade out into Main Menu
│
MAIN MENU:
├─ Main Menu Theme
│  ├─ Duration: Loop (continuous)
│  ├─ BPM: 110, Key: Major
│  ├─ Instruments: Electronic + orchestral blend
│  ├─ Volume: 60%
│  ├─ Features: Heroic, strategic mood
│  └─ Fade when transitioning
│
├─ Sound Effects:
│  ├─ Menu select: "Ding" sound (120ms)
│  ├─ Button hover: Subtle "whoosh" (80ms)
│  └─ Transitions: Fade in/out (500ms)
│
CHARACTER SELECT:
├─ Character Select Theme (NEW)
│  ├─ Duration: Loop
│  ├─ BPM: 105, Key: Major
│  ├─ Instruments: Drums + strings
│  ├─ Volume: 65%
│  └─ Features: Heroic character intro feel
│
├─ Sound Effects:
│  ├─ Character highlight: "Whoosh" + chime
│  ├─ Character select: Ascending notes
│  └─ Each character has unique intro sound
│
LEVEL SELECT:
├─ Level Select Theme
│  ├─ Duration: Loop
│  ├─ BPM: 100, Key: Minor
│  ├─ Instruments: Synthesizer + bass
│  ├─ Volume: 60%
│  └─ Features: Strategic, slightly ominous
│
├─ Sound Effects:
│  ├─ Level preview hover: Ascending tone
│  ├─ Level select: "Ready" sound cue
│  └─ Loading sound: Short electronic chirp (400ms)
│
GAMEPLAY - LEVEL 1 (2-4 minutes):
├─ Combat Theme - Level 1
│  ├─ Duration: Loop (adaptive length)
│  ├─ BPM: 135-140, Key: Minor
│  ├─ Instruments: Electric drums + strings
│  ├─ Volume: 75% (louder, intense)
│  ├─ Features: High energy, slightly less intense than L2
│  └─ Variation: Changes slightly each wave
│
├─ IN-GAME SOUND EFFECTS:
│  ├─ Weapon Fire:
│  │  ├─ Pistol: "PEW-PEW" (80ms each, 0.3s between)
│  │  ├─ Rifle: "TZZZZZZ" (rapid fire, 40ms bursts)
│  │  ├─ Shotgun: "BOOM" (deep, 120ms)
│  │  └─ Each has different tone/pitch
│  │
│  ├─ Hit/Impact Sounds:
│  │  ├─ Enemy hit: "Thunk" sound (100ms)
│  │  ├─ Critical hit: Ascending tone (80ms)  
│  │  ├─ Enemy death: "Explode/scream" (150-200ms)
│  │  └─ Volume ramps with damage
│  │
│  ├─ Player Damage:
│  │  ├─ Normal hit: "Ouch" sound (100ms)
│  │  ├─ Critical hit: Louder "CRACK" (120ms)
│  │  ├─ Health warning: Beeping at < 30% (repeating 300ms)
│  │  └─ Death: "Dramatic fall" sound (500ms)
│  │
│  ├─ Reload Sound:
│  │  ├─ Trigger: When [R] pressed
│  │  ├─ Sound: "CLICK-CLACK" mechanical (200ms)
│  │  ├─ Completion: "Ready" chime (100ms)
│  │  └─ Volume: 70%
│  │
│  └─ Wave Progression:
│     ├─ New wave incoming: Alarm sound (300ms)
│     ├─ 10 seconds left: Timer beep (repeating)
│     ├─ Wave clear: Victory fanfare (300ms)
│     └─ All enemies dead: Success jingle
│
GAMEPLAY - LEVEL 2 (4-6 minutes):
├─ Combat Theme - Level 2
│  ├─ Duration: Loop (adaptive)
│  ├─ BPM: 145-150, Key: Minor
│  ├─ Instruments: Aggressive electric + heavy bass
│  ├─ Volume: 80% (even more intense)
│  ├─ Features: Harder, more threatening
│  └─ Adds: Danger motifs (ominous)
│
├─ ADDITIONAL SOUNDS:
│  ├─ Lava sounds: Crackling/sizzling (looping ambient)
│  ├─ Platform dangers: Rumble/creak sounds on collision
│  ├─ Fire effects: Whoosh when fire VFX spawns
│  └─ Boss preparation: Ominous buildup (2 second warning)
│
VICTORY SCREEN:
├─ Victory Theme
│  ├─ Duration: 3-4 seconds then loop
│  ├─ BPM: 120, Key: Major
│  ├─ Instruments: Triumphant strings + brass
│  ├─ Volume: 70%
│  └─ Features: Happy, celebratory, epic
│
├─ Sound Effects:
│  ├─ Victory announced: "VICTORY!" voice or fanfare
│  ├─ Stats appear: Ascending ding sounds (per stat)
│  ├─ XP reward: "Cha-ching!" sound
│  └─ Button hover: Normal menu sound
│
DEFEAT SCREEN:
├─ Defeat Theme
│  ├─ Duration: 2-3 seconds then loop
│  ├─ BPM: 80-85, Key: Minor
│  ├─ Instruments: Sad strings, slower tempo
│  ├─ Volume: 60%
│  └─ Features: Sad, ominous, slightly dramatic
│
├─ Sound Effects:
│  ├─ Defeat announced: Mournful tone
│  ├─ Stats appear: Descending tone sounds
│  ├─ Retry prompt: Motivational "you can do better" chime
│  └─ Button hover: Normal menu sound

MUSIC CROSS-FADE TIMING:

Menu → Character Select: 500ms fade out/in
Character Select → Level Select: 500ms fade out/in  
Level Select → Gameplay: 1000ms fade out/in (more dramatic)
Gameplay Victory: 500ms fade to victory theme
Gameplay Defeat: 500ms fade to defeat theme
Victory/Defeat → Menu: 1500ms fade (let results sink in)
```

---

## TECHNICAL SPECIFICATIONS

### **Screen Dimensions & Rendering**

```
PRIMARY VIEWPORT: 1024x768 pixels (standard)

HUD LAYOUT:
├─ Top panel: 1024x168px (20% of screen)
├─ Game viewport: 1024x600px (80% of screen, scrollable)
└─ Total render target: 1024x768px

LEVEL DIMENSIONS:
├─ Level 1 Width: 2200px total (2.1x screen width)
├─ Level 2 Width: 2400px total (2.3x screen width)
├─ Height: 600px (playable area within viewport)
└─ Vertical margin: 168px above (HUD) + 0px below

CAMERA SYSTEM:
├─ Follow Player X position
├─ Center on player when possible
├─ Clamp to level bounds
└─ Parallax scrolling on background layers

VFX RENDER LAYER ORDER (front to back):
1. Floating text/damage numbers
2. Foreground props & fire
3. Bullets & projectiles
4. Blood/spark effects
5. Smoke effects
6. Characters (player & enemies)
7. Platforms & collision geometry
8. Middle ground
9. Distant structures
10. Sky (background)

ANIMATION FRAME RATES:
├─ Character idle: 6-8 FPS
├─ Character run: 8-10 FPS
├─ Enemy animation: 8-12 FPS
├─ VFX smoke: 5-6 FPS (looping)
├─ VFX blood: 8-10 FPS
├─ Weapon fire: Instant (no anim)
├─ HUD updates: 30 FPS min
└─ Game physics: 60 FPS

ASSET MEMORY BUDGET:
├─ Level 1 assets: ~15-20 MB
├─ Level 2 assets: ~18-25 MB
├─ Character animations: ~/5-8 MB
├─ VFX sprites: ~8-10 MB
├─ GUI elements: ~2-3 MB
├─ Audio files: ~10-15 MB
└─ Total: ~60-90 MB estimated

COLLISION DETECTION:
├─ Platform collision: Rectangle-based
├─ Bullet hit detection: Circle vs rectangle
├─ Enemy pathfinding: Grid-based A* algorithm
└─ Damage zones: Circular splash radius

SPAWN SYSTEM:
Level 1:
├─ Wave 1: 3-4 enemies, spawn at t=1.0s, t=1.5s, t=2.0s
├─ Wave 2: 4-5 enemies, 1.5s intervals
├─ Wave 3: 5-6 enemies, 1.0s intervals (harder!)

Level 2:
├─ Wave 1: 4 enemies, 1.0s intervals
├─ Wave 2: 4 enemies, 0.8s intervals
├─ Wave 3: 5 enemies, 1.2s intervals
├─ Wave 4: 5 enemies, 1.0s intervals
├─ Wave 5: Boss + 2 escorts, special timing

NEW GAME+ / DIFFICULTY MODIFIERS:
Level 1 → Level 2 progression:
├─ Enemy HP +30%
├─ Enemy damage +20%
├─ Enemy speed +10%
├─ Enemy spawn frequency x1.3
└─ VFX intensity increases
```

---

---

## ASSET DIRECTORY REFERENCE (UPDATED FROM assets-manifest.json)

### **Complete Asset Structure**

All assets sourced from: `Resources/industrial-zone/`
Total Available Assets: **1,174 files**

```
Resources/industrial-zone/
│
├── vfx/ (250+ visual effects)
│   ├── 1 Smoke/ (18 frames looping animation)
│   │   ├── 01-18_VFX_Smoke_Frame*_*_SmokeAnim_Loop_80ms.png
│   │   └── Used for: Background ambience, impact effects, explosion clouds
│   │
│   ├── 2 Blood/ (8 splatter variants)
│   │   ├── 01-08_VFX_Blood_Splatter_4Frames1Row_*_Impact_PlayOnce_80ms.png
│   │   └── Used for: Enemy damage feedback, melee hit effects
│   │
│   ├── 3 Sparks/ (8 burst variants)
│   │   ├── 01-08_VFX_Sparks_Burst_4Frames1Row_*_Impact_PlayOnce_80ms.png
│   │   └── Used for: Metal impacts, weapon fire, mechanical hits
│   │
│   ├── 4 Particles/ (12 ambient particle effects)
│   │   ├── 01-12_VFX_Particles_[Color]_4Frames1Row_*_Ambient_Loop_100ms.png
│   │   ├── Colors: Green (3), Blue (3), Orange (3), Yellow (3)
│   │   └── Used for: Ambient environment effects, energy clouds
│   │
│   ├── 5 Other/ (12 special effects)
│   │   ├── 01-04_VFX_Stars_Burst_*Frames_*_[Ambient/Impact]_*.png
│   │   ├── 05-08_VFX_CyanShards_Scatter_*Frames_*_Impact_PlayOnce_80ms.png
│   │   ├── 09-10_VFX_Portal_Frame*_LargePortalOpening_Portal_PlayOnce_100ms.png
│   │   ├── 11-12_VFX_Smoke_Wisps_6Frames_TallTealWispVariant_Ambient_Loop_120ms.png
│   │   └── Used for: Special events, portals, energy effects
│   │
│   └── 6 Extra/ (Variable character-specific VFX)
│       └── Character/ (10+ character death/action VFX)
│           ├── 01_VFX_Char_Biker_Death_6Frames_DeathTumbleColourVFX_PlayOnce_120ms.png
│           ├── 02_VFX_Char_Biker_DoubleJump_6Frames_MidAirFlipColourVFX_PlayOnce_80ms.png
│           ├── 03_VFX_Char_Biker_Hurt_2Frames_HurtFlinchRedGhostVFX_PlayOnce_100ms.png
│           ├── 04_VFX_Char_Biker_Jump_4Frames_JumpArcColourVFX_PlayOnce_80ms.png
│           └── (Similar patterns for Soldier and Security Chief)
│
├── characters/ (150+ character sprites)
│   ├── player/ (3 playable characters × 2 animation sets)
│   │   ├── 01_Player_Biker_Idle_*.png (6 idle frames)
│   │   ├── 01_Player_Biker_Run_*.png (8 run frames)
│   │   ├── 02_Player_Soldier_Idle_*.png (6 idle frames)
│   │   ├── 02_Player_Soldier_Run_*.png (8 run frames)
│   │   ├── 03_Player_SecurityChief_Idle_*.png (6 idle frames)
│   │   └── 03_Player_SecurityChief_Run_*.png (8 run frames)
│   │
│   ├── enemies/ (5 enemy types × multiple frames)
│   │   ├── drones/
│   │   │   ├── 01_UFO_Saucer_Idle_*.png
│   │   │   ├── 01_UFO_Saucer_Attack_*.png
│   │   │   ├── 02_Jet_Drone_Idle_*.png
│   │   │   ├── 02_Jet_Drone_Attack_*.png
│   │   │   ├── 03_Transport_Drone_Idle_*.png
│   │   │   └── 03_Transport_Drone_Attack_*.png
│   │   │
│   │   ├── punks/
│   │   │   ├── 01_Punk_Idle_*.png
│   │   │   ├── 01_Punk_Run_*.png
│   │   │   └── 01_Punk_Attack_*.png
│   │   │
│   │   └── rugby/
│   │       ├── 01_Rugby_Player_Idle_*.png
│   │       ├── 01_Rugby_Player_Run_*.png
│   │       └── 01_Rugby_Player_Attack_*.png
│   │
│   └── bosses/ (2 boss types × multiple frames)
│       ├── 01_Boss_Heavy_Idle_*.png
│       ├── 01_Boss_Heavy_Attack_*.png
│       ├── 02_Boss_Armored_Idle_*.png
│       └── 02_Boss_Armored_Attack_*.png
│
├── tiles/ (300+ level tileset assets)
│   └── 1 Tiles/
│       ├── Industrial_zone_level_1/
│       │   ├── power_station_background_sky.png (Layer 0: Sky)
│       │   ├── power_station_distantbuildings.png (Layer 1: Structures)
│       │   ├── power_station_level_1_platforms_*.png (Layer 2: Platforms, collision)
│       │   └── power_station_foreground_*.png (Layer 3: Foreground props)
│       │
│       └── power-station-level-2/ (Steel Foundry)
│           ├── foundry_sky_smoky.png (Layer 0: Smoky sky)
│           ├── foundry_distantstructure.png (Layer 1: Distant foundry)
│           ├── foundry_level_2_platforms_*.png (Layer 2: Main platforms with hazards)
│           └── foundry_foreground_fire.png (Layer 3: Foreground fire effects)
│
├── gui/ (200+ UI elements)
│   ├── 1 Frames/ (81-tile 9×9 comprehensive UI frame tileset)
│   │   ├── gui_frame_tileset_9x9.png
│   │   └── Includes: Corners, edges, fills for all UI panels
│   │
│   ├── 2 Bars/ (Health, ammo, shield bars)
│   │   ├── health_bar_green_*.png
│   │   ├── ammo_bar_yellow_*.png
│   │   ├── shield_bar_blue_*.png
│   │   └── bar_background_dark.png
│   │
│   ├── 3 Icons/ (Various UI icons)
│   │   ├── health_icon.png
│   │   ├── ammo_icon.png
│   │   ├── wave_icon.png
│   │   ├── timer_icon.png
│   │   └── minimap_icon.png
│   │
│   ├── 4 Palette/ (Color reference swatches)
│   │   ├── palette_primary.png
│   │   ├── palette_secondary.png
│   │   └── palette_accent.png
│   │
│   ├── 5 Logo/ (Game branding)
│   │   ├── game_logo.png (Main logo for splash screen)
│   │   ├── game_title.png
│   │   └── game_icon.png
│   │
│   ├── 6 Buttons/ (Interactive buttons)
│   │   ├── button_play_idle.png & button_play_hover.png
│   │   ├── button_help_idle.png & button_help_hover.png
│   │   ├── button_settings_idle.png & button_settings_hover.png
│   │   ├── button_exit_idle.png & button_exit_hover.png
│   │   ├── button_start_level_idle.png & button_start_level_hover.png
│   │   ├── button_next_level_idle.png & button_next_level_hover.png
│   │   ├── button_retry_idle.png & button_retry_hover.png
│   │   ├── button_char_select_idle.png & button_char_select_hover.png
│   │   ├── button_main_menu_idle.png & button_main_menu_hover.png
│   │   └── button_quit_idle.png & button_quit_hover.png
│   │
│   ├── 7 Numbers/ (Font digits for HUD display)
│   │   ├── digit_0-9_orange.png (Individual digit files)
│   │   ├── digit_0-9_white.png
│   │   └── digit_0-9_green.png
│   │
│   ├── 8 Cursors/ (Mouse cursor variants)
│   │   ├── cursor_default.png
│   │   ├── cursor_aim.png (Weapon aiming crosshair)
│   │   └── cursor_hover.png
│   │
│   └── 9 Other/ (Miscellaneous UI)
│       ├── star_filled.png (For difficulty ratings)
│       ├── star_empty.png
│       ├── panel_background.png
│       └── border_frame.png
│
├── audio/
│   ├── music_midi/
│   │   ├── menu_theme.mid
│   │   ├── level1_combat.mid
│   │   ├── level2_combat.mid
│   │   ├── victory_theme.mid
│   │   └── defeat_theme.mid
│   │
│   ├── music_wav/ (WAV versions of MIDI)
│   │   └── (Same structure as MIDI)
│   │
│   └── sfx/ (Sound effects)
│       ├── weapons/
│       │   ├── pistol_fire.wav
│       │   ├── rifle_fire.wav
│       │   └── shotgun_fire.wav
│       │
│       ├── impacts/
│       │   ├── hit_enemy.wav
│       │   ├── critical_hit.wav
│       │   └── enemy_death.wav
│       │
│       ├── player/
│       │   ├── player_hurt.wav
│       │   ├── player_reload.wav
│       │   └── player_death.wav
│       │
│       └── ui/
│           ├── button_click.wav
│           ├── wave_start.wav
│           └── victory_fanfare.wav
│
├── weapons/ (50+ weapon animation sprites)
│   ├── 1 Pistol/
│   ├── 2 Rifle/
│   └── 3 Shotgun/
│
└── KeyBoard_Keys/ & Mouse_keys/
    └── Key sprite files for "How to Play" display
```

---

## SCREEN CLASS ASSET IMPLEMENTATION

### **SplashScreen Assets**
- **Background**: `gui/1 Frames/gui_frame_tileset_9x9.png` (tile 7 - fill)
- **Logo**: `gui/5 Logo/game_logo.png`
- **Timing**: 3 second intro loop

### **MainMenuScreen Assets**  
- **Background**: `gui/1 Frames/gui_frame_tileset_9x9.png` (tile 7 - tiled fill)
- **Buttons**: 
  - `gui/6 Buttons/button_play_idle.png` & `button_play_hover.png`
  - `gui/6 Buttons/button_help_idle.png` & `button_help_hover.png`
  - `gui/6 Buttons/button_settings_idle.png` & `button_settings_hover.png`
  - `gui/6 Buttons/button_exit_idle.png` & `button_exit_hover.png`

### **CharacterSelectScreen Assets**
- **Background**: `gui/1 Frames/gui_frame_tileset_9x9.png` (tile 7 - tiled fill)
- **Character 1 (Biker)**: `characters/player/01_Player_Biker_Idle_1.png`
- **Character 2 (Soldier)**: `characters/player/02_Player_Soldier_Idle_1.png`
- **Character 3 (Security Chief)**: `characters/player/03_Player_SecurityChief_Idle_1.png`
- **Select Buttons**: `gui/6 Buttons/button_char_select_*.png`

### **LevelSelectScreen Assets**
- **Background**: `gui/1 Frames/gui_frame_tileset_9x9.png` (tile 7 - tiled fill)
- **Level 1 Preview**: `tiles/1 Tiles/Industrial_zone_level_1/power_station_background_sky.png`
- **Level 2 Preview**: `tiles/1 Tiles/power-station-level-2/foundry_sky_smoky.png`
- **Difficulty Stars**: `gui/9 Other/star_filled.png` & `star_empty.png`
- **Start Buttons**: `gui/6 Buttons/button_start_level_*.png`

### **VictoryScreen Assets**
- **Background**: `gui/1 Frames/gui_frame_tileset_9x9.png` (tile 7 - tiled fill with overlay)
- **Panel Frames**: `gui/9 Other/panel_background.png` & `border_frame.png`
- **Star Rating**: `gui/9 Other/star_filled.png` & `star_empty.png`
- **Action Buttons**:
  - `gui/6 Buttons/button_next_level_*.png`
  - `gui/6 Buttons/button_char_select_*.png`
  - `gui/6 Buttons/button_main_menu_*.png`

### **DefeatScreen Assets**
- **Background**: `gui/1 Frames/gui_frame_tileset_9x9.png` (tile 7 - darkened tiled fill)
- **Panel Frames**: `gui/9 Other/panel_background.png` & `border_frame.png`
- **Tips Section**: `gui/9 Other/panel_background.png` (blue variant)
- **Action Buttons**:
  - `gui/6 Buttons/button_retry_*.png`
  - `gui/6 Buttons/button_char_select_*.png`
  - `gui/6 Buttons/button_main_menu_*.png`

---

## CONCLUSION

This comprehensive design document provides:

✅ **Complete Game Flow** - From intro through victory/defeat
✅ **Detailed UI Layouts** - Every screen with positioning specs
✅ **Character Design** - 3 unique characters with stats & abilities
✅ **Level Design** - 2 distinct levels with difficulty scaling
✅ **Asset Management** - Exact paths & animations for all 1,174 assets
✅ **Audio/Music Timeline** - Full soundtrack flow with timing
✅ **Technical Specifications** - Rendering, fps, memory budgets
✅ **VFX & Particle Systems** - Smoke, blood, sparks with spawning
✅ **HUD Design** - Health, ammo, wave counters, minimap

**Next Phase**: Implement these designs into Game.java, using:
- AnimationAndSpriteLoader.java (loader classes for all assets)
- assets-manifest.json (1,174 assets as reference)
- This design document (exact positioning & timing)

---

**Document Version**: 1.0
**Last Updated**: April 5, 2026
**Status**: ✅ READY FOR IMPLEMENTATION
