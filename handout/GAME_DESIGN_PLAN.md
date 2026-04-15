# CYBER RUNNER 2067 — Master Game Design Plan  *(v3.0 — Full Upgrade)*

> **Project:** CSCU9N6 Assignment — 2D Side-Scrolling Platformer  
> **Official Title:** CYBER RUNNER 2067  
> **Engine:** Java Swing / `game2D.GameCore`  
> **Window:** 1500 × 860 px @ ~60 FPS (16 ms Timer)  
> **Author:** ZAID SIDDIQUI  
> **Asset Pack:** Industrial Zone (1174+ catalogued assets across 13 categories)

---

> **HOW TO READ THIS PLAN:** Every section marked ✅ is already implemented.  
> Every section marked 🔲 is planned/needed next.  
> Sections marked 🔁 are partially done but need upgrades.  
> Follow sections in order — they build on each other.

---

## Table of Contents

1. [Game Story & Narrative](#1-game-story--narrative)
2. [Screen Flow & GUI Architecture (UPGRADE)](#2-screen-flow--gui-architecture-upgrade)
3. [Player Characters](#3-player-characters)
4. [Controls & Input Mapping (UPGRADE)](#4-controls--input-mapping-upgrade)
5. [Weapon System — Starting Gun & Inventory](#5-weapon-system--starting-gun--inventory)
6. [Animated Objects — Physics, Collision & Sprites](#6-animated-objects--physics-collision--sprites)
7. [Interactive Transporters & Hang Mechanic](#7-interactive-transporters--hang-mechanic)
8. [Level Design (UPGRADE)](#8-level-design-upgrade)
9. [Tile & Background System (UPGRADE)](#9-tile--background-system-upgrade)
10. [Enemy & Drone AI (UPGRADE)](#10-enemy--drone-ai-upgrade)
11. [Boss Encounters](#11-boss-encounters)
12. [Checkpoint & Save System](#12-checkpoint--save-system)
13. [Reward & Collectible System (UPGRADE)](#13-reward--collectible-system-upgrade)
14. [HUD Layout (UPGRADE)](#14-hud-layout-upgrade)
15. [Audio System (UPGRADE)](#15-audio-system-upgrade)
16. [VFX & Particle System](#16-vfx--particle-system)
17. [Physics & Collision (UPGRADE)](#17-physics--collision-upgrade)
18. [Camera System](#18-camera-system)
19. [Asset Architecture (Java Classes)](#19-asset-architecture-java-classes)
20. [Full Asset Inventory Summary](#20-full-asset-inventory-summary)
21. [Implementation Status — Upgraded Priority Table](#21-implementation-status--upgraded-priority-table)

---

## 1. Game Story & Narrative

### 1.1 Official Title & Setting

**CYBER RUNNER 2067**

> *"The cyborg factory has crashed and hordes of hostile machines have filled the streets of your city. The government sent you to fight against huge enemy forces — do not lose their confidence! Explore the entire territory of the plant and deal with the enemies in any way convenient for you. Destroy opponents with an energy salvo and don't fall into deadly traps on your way. Can you overcome all the hardcore levels and get to the very end?"*
>
> *Cyber Runner 2067 is a 2D platformer. You play as a positive character — a cyborg operative — trying to fix the situation in the factory before the enemies hit the city.*

**Year:** 2067  
**Location:** An abandoned cyborg manufacturing complex on the edge of the city  
**Threat:** NEXUS — a rogue AI hive that reprogrammed every machine in the facility  
**Mission:** Infiltrate both zones (Industrial + Power Station), destroy all hostile machines, shut down NEXUS

---

### 1.2 Story Introduction Screen

Shown **after level selection** and **before gameplay begins**, with a dark cinematic panel:

```
  ┌──────────────────────────────────────────────────────────────────────┐
  │                    ≡  CYBER RUNNER 2067  ≡                          │
  │                                                                      │
  │  [Character portrait — selected character idle animation, 96×96]    │
  │                                                                      │
  │  "The factory is lost. NEXUS controls everything. You are the        │
  │   last operative standing. The city depends on what you do           │
  │   in the next few minutes. Do not fail."                             │
  │                                                                      │
  │  ── MISSION BRIEFING ─────────────────────────────────────────────  │
  │  Level 1: Industrial Zone — Clear the outer perimeter.              │
  │           Objective: Defeat all drone patrols. Reach the gate.      │
  │                                                                      │
  │  Level 2: Power Station — Shut down the NEXUS reactor core.         │
  │           Objective: Destroy all enemies. Defeat the final boss.    │
  │                                                                      │
  │             [ PRESS ENTER TO BEGIN ]           [ ESC = BACK ]       │
  └──────────────────────────────────────────────────────────────────────┘
```

**Assets used:**  
- `GUI_Logo_IndustrialZone_Full.png` — top logo banner  
- `frmCornerTL/TR/BL/BR` + edge tiles — panel frame (nine-patch)  
- `01_GUI_Decor_GlowBars_*` — vertical neon rods on panel sides  
- `03_GUI_Decor_CableTwist_*` — corner cable decoration  
- Background: auto-scroll parallax (Level 1 or 2 BG depending on selected level)

---

### 1.3 Level 1 Story & Objectives

**ACT I — INDUSTRIAL ZONE**

```
  ╔══════════════════════════════════════════════════════════════════╗
  ║  LEVEL 1: INDUSTRIAL ZONE ENTRY                                 ║
  ╠══════════════════════════════════════════════════════════════════╣
  ║  Setting: The outer factory zone. Conveyor belts still run.     ║
  ║  Hazard hammers swing. The drones took the perimeter first.     ║
  ║                                                                 ║
  ║  Story Beat:                                                    ║
  ║  "The outer gate fell 12 hours ago. Surveillance shows drone    ║
  ║   patrols in every sector. You're going in armed — use the     ║
  ║   pistol we gave you and find what else they left behind."     ║
  ║                                                                 ║
  ║  Objectives (displayed in HUD objective box):                   ║
  ║  [ ] 1. Survive the industrial zone (reach the exit portal)    ║
  ║  [ ] 2. Defeat all 13 drone sentinels                          ║
  ║  [ ] 3. Destroy the Combat Tank                                ║
  ║  [ ] 4. Collect 3+ cards (unlocks dash power-up)              ║
  ║  [ ] 5. Find and open all 4 chests (bonus score)              ║
  ║                                                                 ║
  ║  Starting Equipment: Pistol A (Set 1, Gun 01) — 12 rounds     ║
  ║  Completion Reward: +500 score, unlock Level 2                  ║
  ╚══════════════════════════════════════════════════════════════════╝
```

---

### 1.4 Level 2 Story & Objectives

**ACT II — POWER STATION**

```
  ╔══════════════════════════════════════════════════════════════════╗
  ║  LEVEL 2: POWER STATION (NEXUS CORE)                           ║
  ╠══════════════════════════════════════════════════════════════════╣
  ║  Setting: The main power plant. Day fades to night as you      ║
  ║  push deeper. Turrets activate. Bosses guard the reactor.      ║
  ║                                                                 ║
  ║  Story Beat:                                                    ║
  ║  "You made it through the outer zone. The power station is     ║
  ║   alive — NEXUS is running the reactor at full capacity.       ║
  ║   The Knight and the Warrior are the last line of defence.    ║
  ║   Destroy them. Pull the plug. End this."                      ║
  ║                                                                 ║
  ║  Objectives (displayed in HUD):                                 ║
  ║  [ ] 1. Reach the reactor chamber (exit portal)                ║
  ║  [ ] 2. Defeat all 15 drone patrols                            ║
  ║  [ ] 3. Defeat the Armoured Knight (mini-boss)                 ║
  ║  [ ] 4. Defeat the Winged Warrior (final boss)                 ║
  ║  [ ] 5. Collect 5+ cards (unlocks ultimate energy blast)       ║
  ║  [ ] 6. Activate all 3 portals (bonus checkpoints)            ║
  ║                                                                 ║
  ║  PHASE SHIFT: Background transitions from Day → Night at       ║
  ║  x=14400 (halfway through the level). Visual overlay fades in. ║
  ║                                                                 ║
  ║  Starting Equipment: Carry-over from Level 1 (or Pistol A)    ║
  ║  Completion Reward: +1500 score, "YOU WIN!" screen            ║
  ╚══════════════════════════════════════════════════════════════════╝
```

---

### 1.5 Narrative Beat Table (All Events)

| Trigger | Screen Text / Event | Audio Cue |
|---------|---------------------|-----------|
| Game start (first launch) | Splash → fade in → Main Menu | `Main_theme_Chinese_Street.wav` |
| Character selected | Character portrait flash + name reveal | `Click_digital_2.wav` |
| Level selected | Story intro panel shown | `Stealthy_theme_loopable.wav` (L1) |
| Level 1 gameplay begin | "OBJECTIVE: SURVIVE THE INDUSTRIAL ZONE" in HUD | `Stealthy_theme_loopable.wav` |
| Player picks up card | "CARD COLLECTED! [N/3]" flash on screen | `Unlocked_chest.wav` |
| 3 cards collected | "DASH UNLOCKED! [SHIFT]" banner appears | `Hovering_robot_sting.wav` |
| Chest opened | Loot burst + "CHEST OPENED! +100" | `Unlocked_chest.wav` |
| Checkpoint reached | Player sit-down anim + "CHECKPOINT SAVED" | `Bell_on_the_door.wav` |
| Boss detected | Camera shake + "WARNING: BOSS AHEAD!" red flash | `Battle_theme_Chinese_Street.wav` |
| Boss defeated (L1 Tank) | "COMBAT TANK DESTROYED — GATEWAY OPEN!" | `Melody_of_the_win.wav` |
| Level 1 complete | Score tally overlay + "LEVEL 1 COMPLETE!" | `Melody_of_the_win.wav` |
| Level 2 start | Day background + "ENTER: POWER STATION" | `Alternative_theme_Chinese_Street.wav` |
| L2 phase shift (x=14400) | Day→Night overlay fade + "THE DARKNESS FALLS" | — (visual only) |
| Mini-boss (Knight) detected | Same boss warning flash | `Battle_theme_Chinese_Street.wav` |
| Final boss (Warrior) detected | Reactor warning sirens | `Battle_theme_Chinese_Street.wav` (louder) |
| Final boss defeated | Slow-mo + "NEXUS SHUTDOWN COMPLETE" | `Calm_theme_Chinese_Street.wav` |
| Player death | Death animation → fade → Game Over screen | `Samurai_death.wav` |
| Portal entered | Warp VFX + level transition | `Portal_1.wav` → `Portal_moving.wav` |
| H key (heal) | Idle2 animation play + "HEALED!" text + HP fill | `Bell_on_the_door.wav` |

---

## 2. Screen Flow & GUI Architecture

### 2.1 Screen State Machine

The game uses an enum-based state machine with 11 screens:

```
                          ┌─────────────┐
                          │   SPLASH    │
                          │ (3s auto)   │
                          └──────┬──────┘
                                 │ fade-out
                                 ▼
                          ┌─────────────┐
                   ┌──────│  MAIN_MENU  │──────┐
                   │      └──┬───┬───┬──┘      │
                   │         │   │   │          │
              ┌────▼───┐  ┌──▼───▼──┐  ┌───────▼──────┐
              │CONTROLS│  │ CREDITS │  │     EXIT     │
              │(view)  │  │(scroll) │  │ System.exit()│
              └────┬───┘  └────┬────┘  └──────────────┘
                   │           │
                   └─────┬─────┘
                         │ BACK
                         ▼
              ┌─────────────────────┐
              │   PLAY GAME         │
              │  ┌───────────────┐  │
              │  │ CHARACTER     │  │
              │  │ SELECT        │  │
              │  │ Biker|Punk|   │  │
              │  │ Cyborg        │  │
              │  └───────┬───────┘  │
              │          │          │
              │  ┌───────▼───────┐  │
              │  │ LEVEL SELECT  │  │
              │  │ Level 1 or 2  │  │
              │  └───────┬───────┘  │
              └──────────┼──────────┘
                         │ Enter
                         ▼
              ┌─────────────────────┐
              │     GAMEPLAY        │◄──── Resume
              │  (main game loop)   │         │
              └───┬───┬────┬────────┘         │
                  │   │    │                  │
           ESC───►│   │    │◄── hp≤0          │
                  ▼   │    ▼                  │
           ┌──────┐   │  ┌───────────┐        │
           │PAUSE │   │  │ GAME_OVER │        │
           │      │   │  │           │        │
           │Resume├───┘  │ Retry ────►GAMEPLAY│
           │Settings     │ Menu ─────►MAIN    │
           │Controls     │ Exit ─────►EXIT    │
           │Quit─►MENU   └───────────┘        │
           └──┬───┘                           │
              │                               │
              ▼                               │
         ┌──────────┐                         │
         │ SETTINGS │                         │
         │ Music Vol│                         │
         │ SFX Vol  │                         │
         │ Toggle   │                         │
         └──────┬───┘                         │
                │ BACK                        │
                └─────────────────────────────┘

              ┌──────────────────┐
              │  LEVEL_COMPLETE  │
              │  Score tally     │
              │  → Next Level    │
              │  → Main Menu     │
              └──────────────────┘
```

### 2.2 Screen Transition Matrix

| From | To | Trigger | Transition Effect |
|------|----|---------|-------------------|
| SPLASH | MAIN_MENU | 3s timer expires | Alpha fade-out (1.0→0.0 over 0.8s) |
| MAIN_MENU | CHARACTER_SELECT | "PLAY GAME" selected | Slide left |
| MAIN_MENU | CONTROLS | "CONTROLS" selected | Slide right |
| MAIN_MENU | CREDITS | "CREDITS" selected | Slide up |
| MAIN_MENU | (exit) | "EXIT" selected | System.exit(0) |
| CHARACTER_SELECT | LEVEL_SELECT | Character confirmed | Slide left |
| LEVEL_SELECT | GAMEPLAY | Level confirmed | Fade to black |
| GAMEPLAY | PAUSE | ESC pressed | Overlay darken |
| GAMEPLAY | GAME_OVER | Player HP ≤ 0 | Fade to red |
| GAMEPLAY | LEVEL_COMPLETE | All enemies defeated | Score tally overlay |
| PAUSE | GAMEPLAY | "Resume" selected | Remove overlay |
| PAUSE | SETTINGS | "Settings" selected | Slide right |
| PAUSE | CONTROLS | "Controls" selected | Slide right |
| PAUSE | MAIN_MENU | "Quit" selected | Fade to black |
| GAME_OVER | GAMEPLAY | "Retry" selected | Restart level |
| GAME_OVER | MAIN_MENU | "Menu" selected | Fade to black |
| LEVEL_COMPLETE | GAMEPLAY | "Next Level" | Load level 2 |
| LEVEL_COMPLETE | MAIN_MENU | "Main Menu" | Fade to black |

### 2.3 GUI Frame Assembly (Nine-Patch System)

All panels and menus are built from a nine-patch frame system using images from `gui/1 Frames/`:

```
  ┌── frmCornerTL ──┬── frmEdgeTop (repeat) ──┬── frmCornerTR ──┐
  │                 │                          │                 │
  ├── frmEdgeLeft ──┼── frmFillNavy (tile) ────┼── frmEdgeRight ─┤
  │   (repeat ↕)    │                          │   (repeat ↕)    │
  │                 │     CONTENT AREA         │                 │
  │                 │     (text, buttons,      │                 │
  │                 │      bars, icons)        │                 │
  ├── frmEdgeLeft ──┼── frmDivider ────────────┼── frmEdgeRight ─┤
  │                 │                          │                 │
  ├── frmEdgeLeft ──┼── frmFillDark (tile) ────┼── frmEdgeRight ─┤
  │                 │                          │                 │
  └── frmCornerBL ──┴── frmEdgeBot (repeat) ──┴── frmCornerBR ──┘
```

**Decorative elements** layered on frames:
- `decorGlowBars` — animated glow strip along panel edges
- `decorRibbon` — highlight ribbon for selected menu items
- `decorCableTwist` / `decorCableCoil` / `decorCablePlug` — industrial cable decorations at corners

### 2.4 Button States

```
                         ┌──────────────────┐
         Mouse outside → │   btnNormal      │ (default blue/teal)
                         └────────┬─────────┘
                                  │ mouse enters
                                  ▼
                         ┌──────────────────┐
         Mouse hovering →│   btnHover       │ (brighter glow)
                         └────────┬─────────┘
                                  │ mouse pressed
                                  ▼
                         ┌──────────────────┐
         Mouse clicked → │   btnPressed     │ (darker pressed)
                         └──────────────────┘
```

10 button color variants available: `btnColors[0..9]` — used for different contexts (confirm=green, cancel=red, neutral=blue, etc.)

---

## 3. Player Characters

### 3.1 Character Selection

Three playable characters, each sharing the same 24-animation moveset:

| # | Character | Description | Visual Style |
|---|-----------|-------------|--------------|
| 0 | **Biker** | Street rebel, pink-haired, leather jacket | Punk aesthetic, compact build |
| 1 | **Cyborg** | Enhanced operative, metallic prosthetics | Sleek chrome/blue, tech visor |
| 2 | **Punk** | Underground fighter, spiky hair, chains | Grunge colors, aggressive pose |

### 3.2 Full Animation Set — Per-Character Frame Reference

All 24 animations use **`HorizontalSpritesheetLoader`** (1-row spritesheets, auto frame count = image width ÷ frame height). Frame rate is the per-frame delay in milliseconds.

> **B** = Biker · **C** = Cyborg · **P** = Punk

| # | Animation Name | Trigger Key / Condition | Mode | Rate | B | C | P | Notes |
|---|---------------|------------------------|------|------|---|---|---|-------|
| 01 | **Idle** | _auto_ — no input | Loop | 150ms | 4 | 4 | 5 | Default breathing stance |
| 02 | **Idle2** | _auto_ (5 s idle) / `[H]` heal trigger | Loop/Once | 150ms | 6 | 5 | 5 | Alt-stand; also plays on Heal |
| 03 | **Walk** | `[A]` / `[D]` — low speed | Loop | 100ms | 6 | 5 | 5 | Ground step cycle |
| 04 | **Run** | `[A]` / `[D]` — sustained 0.5 s+ | Loop | 80ms | 6 | 5 | 6 | Full sprint cycle |
| 05 | **Dash** | `[SHIFT]` | Once | 60ms | 6 | 4 | 4 | Rapid sliding lunge |
| 06 | **Jump** | `[SPACE]` on ground | Once | 80ms | 4 | 3 | 3 | Jump rise arc |
| 07 | **Double Jump** | `[SPACE]` in air | Once | 80ms | 6 | 5 | 4 | Mid-air flip boost |
| 08 | **Fall** | _auto_ (vy > 0) | Loop | 100ms | 4 | 3 | 3 | Free-fall descend |
| 09 | **Climb** | `[W]` up / `[S]` down on ladder | Loop | 120ms | 6 | 4 | 4 | Ladder rung cycle — see §3.5 |
| 10 | **Hang** | _auto_ (near ledge edge) | Loop | 150ms | 3 | 3 | 4 | Ledge hang hold |
| 11 | **Pull-up** | `[W]` while hanging | Once | 80ms | 6 | 7 | 7 | Hoist onto ledge surface |
| 12 | **Punch** | `[K]` / `[LMB]` unarmed, 1st press | Once | 70ms | 5 | 5 | 6 | Punch combo start |
| 13 | **Attack1** | `[K]` / `[LMB]` combo hit 1 | Once | 70ms | 5 | 5 | 5 | Light swing |
| 14 | **Attack2** | `[K]` / `[LMB]` combo hit 2 | Once | 70ms | 5 | 5 | 6 | Heavy swing / guitar |
| 15 | **Attack3** | `[K]` / `[LMB]` combo hit 3 | Once | 70ms | 7 | 5 | 6 | Energy wave / ultimate |
| 16 | **Walk Attack** | `[A]`/`[D]` + `[K]` while walking | Once | 80ms | 5 | 5 | 5 | Attack without stopping |
| 17 | **Run Attack** | `[A]`/`[D]` + `[K]` while running | Once | 70ms | 5 | 5 | 6 | Running slash |
| 18 | **Hurt** | _auto_ (on damage received) | Once | 100ms | 2 | 2 | 2 | Hit-flinch reaction |
| 19 | **Death** | _auto_ (HP ≤ 0) | Once | 120ms | 6 | 5 | 5 | Death fall sequence |
| 20 | **Use** | `[E]` — interact with object | Once | 100ms | 5 | 5 | 5 | Interaction pose |
| 21 | **Sit Down** | _auto_ (reaches checkpoint) | Once | 120ms | 3 | 3 | 3 | Rest at checkpoint |
| 22 | **Angry** | _auto_ (story beat) | Loop | 150ms | 5 | 5 | 5 | Emote — frustration |
| 23 | **Happy** | _auto_ (level complete / victory) | Loop | 150ms | 5 | 5 | 5 | Emote — celebration |
| 24 | **Talk** | _auto_ (dialogue) / `[T]` advance | Loop | 120ms | 5 | 5 | 5 | Mouth-movement cycle |

#### File Naming Convention

```
{N:02}_Player_{Character}_{AnimName}_{F}Frames1Row_{Description}_{LoopOrPlayOnce}_{ms}ms.png
```

Example: `09_Player_Biker_Climb_6Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png`

#### Key → Animation Mapping Summary

| Key / Input | Animations Triggered |
|-------------|----------------------|
| `[A]` / `[D]` | Walk (03), Run (04), Walk Attack (16), Run Attack (17) |
| `[W]` | Climb up (09), Pull-up from ledge (11) |
| `[S]` | Climb down (09) |
| `[SPACE]` | Jump (06), Double Jump in air (07) |
| `[SHIFT]` | Dash (05) |
| `[K]` or `[LMB]` | Punch (12), Attack1 (13), Attack2 (14), Attack3 (15), Walk Attack (16), Run Attack (17) |
| `[E]` | Use / Interact (20) |
| `[H]` | Idle2 (02) — Heal variant, plays once then returns to Idle |
| `[T]` | Talk (24) — advance / skip current dialogue line |
| `[1]` `[2]` `[3]` `[4]` | _(no anim)_ — switch active weapon slot in inventory |
| _Auto / Game State_ | Idle (01), Hang (10), Fall (08), Hurt (18), Death (19), Sitdown (21), Angry (22), Happy (23) |

### 3.3 Animation State Machine

```
                                 ┌─────────┐
                         ┌───────│  IDLE   │◄──────────────┐
                         │       └────┬────┘               │
                         │            │ A/D key             │ no input
                         │            ▼                     │ for 200ms
                    E key│       ┌─────────┐               │
                         │  ┌────│  WALK   │────┐          │
                         │  │    └────┬────┘    │          │
                         │  │         │ held    │ attack   │
                         │  │         ▼         ▼          │
                         │  │    ┌─────────┐ ┌─────────┐   │
                         │  │    │  RUN    │ │WALK_ATK │───┘
                         │  │    └──┬──┬───┘ └─────────┘
                         │  │       │  │
                         │  │  SHIFT│  │attack
                   ┌─────▼──▼──┐    │  ▼
                   │   USE     │    │ ┌─────────┐
                   │(interact) │    │ │ RUN_ATK │───┐
                   └───────────┘    │ └─────────┘   │
                                    ▼               │
                               ┌─────────┐         │
                               │  DASH   │         │
                               └─────────┘         │
                                                   │
     ┌──────────────────────────────────────────────┘
     │
     │  SPACE          SPACE (in air)
     ▼                 ▼
┌─────────┐     ┌──────────────┐
│  JUMP   │────►│ DOUBLE_JUMP  │
└────┬────┘     └──────┬───────┘
     │                 │
     │ vy > 0          │ vy > 0
     ▼                 ▼
┌─────────┐     ┌──────────────┐
│  FALL   │◄────│    FALL      │
└────┬────┘     └──────────────┘
     │
     │ land on ground
     ▼
     └──────────────────────────────► IDLE


                  COMBAT BRANCH
     ┌─────────┐    ┌─────────┐    ┌─────────┐
     │PUNCH    │───►│ATTACK1  │───►│ATTACK2  │───► ATTACK3
     │(K/click)│    │(combo)  │    │(combo)  │     (combo)
     └─────────┘    └─────────┘    └─────────┘

                  DAMAGE BRANCH
     ┌─────────┐
     │  HURT   │───► if HP > 0 → IDLE
     │(flinch) │───► if HP ≤ 0 → DEATH
     └─────────┘

                  INTERACTION BRANCH
     ┌─────────┐    ┌─────────┐    ┌─────────┐
     │  CLIMB  │───►│  HANG   │───►│ PULLUP  │───► IDLE (on ledge)
     └─────────┘    └─────────┘    └─────────┘

     ┌─────────┐
     │ SITDOWN │───► IDLE (at checkpoint)
     └─────────┘

                  HEAL BRANCH (H key)
     ┌─────────────────────────────────────────────────────────────────┐
     │  [H] key pressed                                                │
     │  Condition: hp < maxHp AND healCooldown <= 0                    │
     │                                                                 │
     │  IDLE / WALK → [IDLE2] (Alternate Stand, anim #02 plays once)  │
     │                                 │                               │
     │                                 │  anim frame 3 reached         │
     │                                 ▼                               │
     │                          hp += 20 (capped at maxHp)            │
     │                          spawn healing particle VFX (green glow)│
     │                          play Bell_on_the_door.wav              │
     │                          healCooldown = 5.0 s                   │
     │                                 │                               │
     │                                 │  anim complete                │
     │                                 ▼                               │
     │                               IDLE                              │
     └─────────────────────────────────────────────────────────────────┘

     Note: H key is ignored while airborne, dashing, attacking, or on cooldown.
     H key during hang (drone): also ignored.
```

### 3.4 Player Stats

| Stat | Value | Notes |
|------|-------|-------|
| Max HP | 100 | Displayed as health bar |
| Walk Speed | 120 px/s | A/D keys |
| Run Speed | 200 px/s | Sustained A/D |
| Dash Speed | 400 px/s | SHIFT, 0.36s duration (6f × 60ms) |
| Jump Velocity | -350 px/s | Initial upward push |
| Double Jump | -300 px/s | Mid-air second push |
| Gravity | 600 px/s² | Applied every frame |
| Melee Damage | 15 | Punch / Attack1 |
| Heavy Damage | 25 | Attack2 / Attack3 |
| Invincibility | 0.5s | After taking hit (Hurt animation) |
| Hitbox | 48 × 48 px | Sprite frame size |

### 3.5 Ladder Climb System

Ladders are placed as world objects in the tile map. Three distinct ladder sprites exist, supporting both short platform-connector links and full vertical shaft climbs.

#### Ladder Prop Sprites

All three ladder sprites exist under:
`Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/`

```
  ┌─────────────────────────────────────────────────────────────────────────┐
  │  LADDER A — Full Height                                                 │
  │  Prop_Ladder_TallFullHeight_BlueGreyRungs_ShaftWallClimb_ClimbableA.png│
  │                                                                         │
  │  ║ ══ ║   32px wide, 192px tall                                        │
  │  ║ ══ ║   Full shaft climber — connects floors 192px apart             │
  │  ║ ══ ║   Deep blue vertical rails, grey horizontal rungs              │
  │  ║ ══ ║   Used in: vertical shafts, underground passages               │
  │  ║ ══ ║                                                                 │
  └─────────────────────────────────────────────────────────────────────────┘

  ┌─────────────────────────────────────────────────────────────────────────┐
  │  LADDER B — Alt Rung Spacing                                            │
  │  Prop_Ladder_TallAltSpacing_BlueGreyRungs_ShaftWallClimb_ClimbableB   │
  │                                                                         │
  │  ║  ═  ║   32px wide, 192px tall                                       │
  │  ║     ║   Same height as A, wider rung gaps — visual variety          │
  │  ║  ═  ║   Used in: wall climbs, alternate shaft sections              │
  │  ║     ║                                                                │
  └─────────────────────────────────────────────────────────────────────────┘

  ┌─────────────────────────────────────────────────────────────────────────┐
  │  LADDER SHORT — Platform Connector                                       │
  │  Prop_Ladder_ShortHorizontalRung_BlueCrossbar_PlatformConnector_Short  │
  │                                                                         │
  │  ╠══╣   32px wide, 64px tall                                           │
  │  ╠══╣   Short bridge between nearby platforms within 64px vertical     │
  │  Used in: platform steps, quick height changes                         │
  └─────────────────────────────────────────────────────────────────────────┘
```

#### Climb Animation Spritesheets (Animation #09)

| Character | Spritesheet Filename | Frames | Rate |
|-----------|----------------------|--------|------|
| **Biker** | `09_Player_Biker_Climb_6Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png` | **6** | 120ms |
| **Cyborg** | `09_Player_Cyborg_Climb_4Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png` | **4** | 120ms |
| **Punk** | `09_Player_Punk_Climb_4Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png` | **4** | 120ms |

Biker has the richest climb cycle (6 frames) — smoother arm-over-arm motion. Cyborg and Punk both use a 4-frame cycle.

#### Climb Interaction State Machine

```
  Player approaches ladder (within 24px of ladder tile centre X)
                      │
                      │ [W] or [S] key pressed
                      ▼
            ┌─────────────────────┐
            │   LADDER ATTACH     │
            │ • Player X snaps to │
            │   ladder centre     │
            │ • Gravity = 0       │
            │ • vx = 0 (locked)   │
            │ • Anim #09 paused   │
            └──────────┬──────────┘
                       │
         ┌─────────────┼──────────────┐
         │ [W] held    │              │ [S] held
         ▼             │              ▼
  ┌────────────┐       │       ┌────────────┐
  │  CLIMBING  │       │       │ DESCENDING │
  │  (upward)  │       │       │ (downward) │
  │ anim fwd   │       │       │ anim fwd   │
  │ y -= 80/s  │       │       │ y += 80/s  │
  └─────┬──────┘       │       └─────┬──────┘
        │              │             │
  reach top     release key    reach bottom
        │              │             │
        ▼              ▼             ▼
  [W] → PULLUP  CLIMB_IDLE      FALL / IDLE
  (anim #11)    (anim paused)
  then IDLE
  on ledge
```

#### Physics Changes On Ladder

| Parameter | Normal State | On Ladder |
|-----------|-------------|-----------|
| Gravity | 600 px/s² | **0 — suspended** |
| Horizontal velocity | ±120–400 px/s | **0 — locked** |
| Vertical velocity | −350 to +600 px/s | **±80 px/s climb speed** |
| Can shoot | Yes | **No** |
| Can dash | Yes | **No** |
| Can take damage | Yes | **Yes — brief 0.3s invincibility on attach** |
| [SPACE] | Jump | **Detach + apply jump velocity** |

#### Ladder Placement Rules

```
  ✓ DO:
  • Place ladders adjacent to vertical walls or inside shaft columns
  • Use Tall-A and Tall-B alternately for visual variety
  • Short connector for gaps ≤ 64px between platforms
  • Space ladders ≥ 400px apart horizontally (don't trivialise vertical traversal)
  • Cap with a platform at the top so Pull-up (anim #11) has a surface to land on

  ✗ DO NOT:
  • Float ladders detached from any wall or column
  • Place ladders on slopes or diagonal tiles
  • Stack two ladders vertically without an intermediate rest platform
  • Use Short connector for gaps > 64px (use Tall A/B instead)
```

---

## 4. Controls & Input Mapping

### 4.1 Keyboard Layout

Every bound key has a corresponding key-cap image in `Resources/industrial-zone/KeyBoard_Keys/` used to visually display it in the Controls screen.

```
┌──────────────────────────────────────────────────────────────────────────────┐
│                         FULL KEYBOARD LAYOUT                                 │
│                                                                              │
│  ┌─────┐  ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐                                 │
│  │ ESC │  │  P  │ │  T  │ │ TAB │ │ENTR │                                  │
│  │Pause│  │Pause│ │Talk/│ │Inv  │ │Confm│                                  │
│  │/Rsm │  │ alt │ │Skip │ │HUD  │ │     │                                  │
│  └─────┘  └─────┘ └─────┘ └─────┘ └─────┘                                 │
│                                                                              │
│  ───────────── MOVEMENT ─────────────────────────────────────────           │
│                                                                              │
│         ┌─────┐                                                              │
│         │  W  │  Move up / Climb up ladder / Pull-up from ledge             │
│         └──┬──┘                                                              │
│  ┌─────┐  ─┤  ┌─────┐                                                       │
│  │  A  ├──┼──┤  D  │  Move left / Move right                               │
│  └─────┘  ─┤  └─────┘                                                       │
│         ┌──┴──┐                                                              │
│         │  S  │  Move down / Climb down ladder / Crouch                     │
│         └─────┘                                                              │
│  (Also: ↑ ↓ ← → arrow keys mirror W/A/S/D)                                 │
│                                                                              │
│  ───────────── JUMP & MOVEMENT ABILITIES ───────────────────────────        │
│                                                                              │
│  ┌───────────────────────────────────┐                                       │
│  │            S P A C E             │  Jump / Double Jump (in air)          │
│  └───────────────────────────────────┘                                       │
│  ┌────────┐                                                                   │
│  │ SHIFT  │  Dash forward (plays Dash anim, 0.36 s burst)                  │
│  └────────┘                                                                   │
│  ┌────────┐                                                                   │
│  │  CTRL  │  Shoot equipped weapon (same as Left Click)                     │
│  └────────┘                                                                   │
│                                                                              │
│  ───────────── COMBAT ───────────────────────────────────────────           │
│                                                                              │
│  ┌─────┐   Melee attack / Fire weapon (combo chains on repeat press)        │
│  │  K  │   Unarmed: Punch → Attack1 → Attack2 → Attack3                    │
│  └─────┘   Armed: fires equipped weapon                                      │
│                                                                              │
│  ───────────── INTERACTION & ITEMS ──────────────────────────────           │
│                                                                              │
│  ┌─────┐   Interact / Pick up weapon / Board transporter / Grab drone      │
│  │  E  │   Plays Use anim (#20) — context-sensitive to nearest object       │
│  └─────┘                                                                     │
│  ┌─────┐   Throw equipped weapon (arc throw, weapon becomes pickup again)   │
│  │  F  │                                                                     │
│  └─────┘                                                                     │
│  ┌─────┐   Reload equipped weapon (1.5 s lock, plays reload VFX)            │
│  │  R  │                                                                     │
│  └─────┘                                                                     │
│  ┌─────┐   Drop current weapon onto ground (no throw, stays in slot area)   │
│  │  G  │                                                                     │
│  └─────┘                                                                     │
│  ┌─────┐   Heal — plays Idle2 anim, restores +20 HP at frame 3             │
│  │  H  │   (5 s cooldown; ignored while airborne / dashing / attacking)     │
│  └─────┘                                                                     │
│                                                                              │
│  ───────────── WEAPON INVENTORY SLOTS ───────────────────────────────       │
│                                                                              │
│  ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐                                           │
│  │  1  │ │  2  │ │  3  │ │  4  │  Select weapon slot 1 / 2 / 3 / 4        │
│  └─────┘ └─────┘ └─────┘ └─────┘  (instant switch, no animation delay)     │
│  ┌─────┐                                                                     │
│  │  Q  │  Cycle weapon — previous slot (wrap-around from 1 ← 4)            │
│  └─────┘                                                                     │
│  [Mouse Scroll ↑↓] — cycle next / previous (same as Q / forward)           │
│                                                                              │
│  ───────────── UI / MENUS ────────────────────────────────────────          │
│                                                                              │
│  ┌─────────┐   Open / close weapon inventory HUD overlay                    │
│  │   TAB   │   (shows all 4 slots, ammo counts, held items)                │
│  └─────────┘                                                                 │
│  ┌──────────┐  Advance / skip current dialogue line (plays Talk anim #24)  │
│  │    T     │                                                                │
│  └──────────┘                                                                │
│  ┌───────────┐  Back / Return to previous menu screen                       │
│  │BACKSPACE  │                                                               │
│  └───────────┘                                                               │
│                                                                              │
│  ───────────── LEVEL NAVIGATION ─────────────────────────────────           │
│                                                                              │
│  ┌──────────────────────────┐                                                │
│  │  [1] [2]  Level select   │  (in Level Select screen only)               │
│  └──────────────────────────┘                                                │
└──────────────────────────────────────────────────────────────────────────────┘
```

#### Context-Sensitive Key Behaviour

| Key | Context | Action |
|-----|---------|--------|
| `[W]` | On ground / in air | Jump / navigate up |
| `[W]` | On ladder | Climb up |
| `[W]` | While hanging | Pull-up (anim #11) |
| `[S]` | On ground | Crouch (sit-down pose, hitbox -50%) |
| `[S]` | On ladder | Climb down |
| `[E]` | Near weapon on ground | Pick up weapon |
| `[E]` | Near transporter (H/V-Mover) | Board transporter |
| `[E]` | Near drone HOVER | Grab drone hang |
| `[E]` | Near chest / terminal | Open / interact |
| `[SPACE]` | On ladder | Detach + jump off |
| `[K]` | Unarmed | Melee combo |
| `[K]` | Armed | Fire weapon |

### 4.2 Mouse Controls

```
┌──────────────────────────────────────────┐
│           MOUSE CONTROLS                 │
│                                          │
│  [Left Click]   Fire weapon / Confirm    │
│  [Right Click]  Throw weapon / Cancel    │
│  [Scroll Up]    Cycle weapon next        │
│  [Scroll Down]  Cycle weapon previous    │
│  [Mouse Move]   Aim direction / Menu     │
│                                          │
│  Cursor States:                          │
│  ┌────┐  Default (navigation)            │
│  │ ○  │  cursorImgs[0] — white           │
│  └────┘                                  │
│  ┌────┐  Aim crosshair (gameplay)        │
│  │ ◎  │  cursorImgs[1] — blue            │
│  └────┘                                  │
│  ┌────┐  Adjusting (settings sliders)    │
│  │ ↔  │  cursorImgs[2] — red             │
│  └────┘                                  │
│  ┌────┐  Hover (over clickable element)  │
│  │ ☝  │  cursorImgs[3] — green           │
│  └────┘                                  │
└──────────────────────────────────────────┘
```

### 4.3 Input Reference Images

The game includes pre-made keyboard and mouse key reference images:

- **66 keyboard key PNGs** in `Resources/industrial-zone/KeyBoard_Keys/` — individual key cap sprites for each bindable key
- **21 mouse key PNGs** in `Resources/industrial-zone/Mouse_keys/` — left click, right click, scroll, etc.

These are used in the **CONTROLS** screen to display visual key bindings alongside text descriptions.

---

## 5. Weapon System & Interaction Chains

### 5.1 Weapon Sets Overview

Two complete weapon sets are provided, each containing 5 subcategories:

| Category | Set 1 Contents | Set 2 Contents |
|----------|----------------|----------------|
| **Characters** | Biker/Punk/Cyborg holding poses (30 sprites) | Same characters, different weapon grips (30 sprites) |
| **Guns** | 20 gun sprites: Pistol×4, Compact×6, Detail×2, Rifle×6, Special×2 | 20 gun sprites: same types, alternate designs |
| **Hands** | Biker/Punk/Cyborg hand-only grips (30 sprites) | Same characters, alternate hand poses (30 sprites) |
| **Shoot Effects** | 10 muzzle flash / recoil effects | 10 muzzle flash / recoil effects |
| **Bullets** | 13 bullet sprites (Types A–J, with variants) | 19 bullet sprites (expanded set) |

### 5.2 Gun Types

| Gun Type | ID | Variants | Damage | Fire Rate | Ammo | Range |
|----------|----|----------|--------|-----------|------|-------|
| **Pistol A** | 01-02 | Dark, Light | 10 | 3/sec | 12 | 400px |
| **Pistol B** | 03-04 | Dark, Light | 12 | 2.5/sec | 10 | 400px |
| **Compact C** | 05-06 | Dark, Light | 8 | 5/sec | 20 | 300px |
| **Compact D** | 07-08 | Dark, Light | 8 | 5/sec | 20 | 300px |
| **Compact E** | 09-10 | Dark, Light | 9 | 4.5/sec | 18 | 320px |
| **Detail F** | 11-12 | Dark, Light | 15 | 2/sec | 8 | 500px |
| **Rifle G** | 13-14 | Dark, Light | 20 | 1.5/sec | 6 | 600px |
| **Rifle H** | 15-16 | Dark, Light | 22 | 1.2/sec | 5 | 650px |
| **Rifle I** | 17-18 | Blue, BlueAlt | 25 | 1/sec | 4 | 700px |
| **Special J** | 19-20 | Teal, Red | 35 | 0.8/sec | 3 | 800px |

### 5.3 Weapon Pickup & Use Chain

```
     WEAPON ON GROUND                    PLAYER INTERACTION
     ════════════════                    ═══════════════════

     ┌──────────────┐        E key       ┌──────────────────┐
     │  Gun sprite  │ ◄──── within ─────►│  Player walks    │
     │  (dropped /  │       32px range    │  near weapon     │
     │   spawned)   │                     └────────┬─────────┘
     └──────┬───────┘                              │
            │                                      │ Press [E]
            │ picked up                            ▼
            │                              ┌──────────────────┐
            └─────────────────────────────►│ WEAPON EQUIPPED  │
                                           │                  │
                                           │ Sprite overlay:  │
                                           │ Character + Gun  │
                                           │ + Hand grip      │
                                           └──┬───┬───┬───┬───┘
                                              │   │   │   │
                           ┌──────────────────┘   │   │   └──────────────────┐
                           │                      │   │                      │
                           ▼                      ▼   ▼                      ▼
                    ┌─────────────┐    ┌──────────┐   ┌──────────┐   ┌─────────────┐
                    │ [K] / Click │    │ [R]      │   │ [F]      │   │ [Scroll]    │
                    │ FIRE WEAPON │    │ RELOAD   │   │ THROW    │   │ CYCLE       │
                    └──────┬──────┘    └────┬─────┘   └────┬─────┘   └──────┬──────┘
                           │                │              │                │
                           ▼                ▼              ▼                ▼
                    ┌─────────────┐  ┌───────────┐  ┌───────────┐  ┌──────────────┐
                    │ Shoot VFX:  │  │ ammo =    │  │ Gun arcs  │  │ Switch to    │
                    │ • Muzzle    │  │ maxAmmo   │  │ forward & │  │ next/prev    │
                    │   flash     │  │           │  │ lands on  │  │ weapon in    │
                    │ • Bullet    │  │ Play      │  │ ground    │  │ inventory    │
                    │   projectile│  │ reload    │  │           │  │              │
                    │ • Recoil    │  │ animation │  │ Becomes   │  └──────────────┘
                    │             │  │           │  │ pickup-   │
                    │ ammo--      │  │ 1.5s lock │  │ able again│
                    └──────┬──────┘  └───────────┘  └───────────┘
                           │
                           ▼
                    ┌─────────────┐
                    │ Bullet      │
                    │ travels →   │
                    │ at speed    │
                    │             │
                    │ Hit enemy?  │
                    │ ┌─Yes───┐   │
                    │ │Damage │   │
                    │ │+ VFX  │   │
                    │ │(blood/│   │
                    │ │spark) │   │
                    │ └───────┘   │
                    │ Miss?       │
                    │ ┌─────────┐ │
                    │ │Despawn  │ │
                    │ │at range │ │
                    │ └─────────┘ │
                    └─────────────┘
```

### 5.4 Composite Sprite Layering

When a weapon is equipped, the player sprite is composed of three layers:

```
  LAYER 3 (top):     Gun sprite          ← from weapons/N/2 Guns/
  LAYER 2 (middle):  Hand grip overlay   ← from weapons/N/3 Hands/{character}/
  LAYER 1 (bottom):  Character pose      ← from weapons/N/1 Characters/{character}/
                     (weapon-holding variant)

  Combined render order (back to front):
  ┌────────────────────┐
  │ Character body     │ ← base layer, facing direction
  │ ┌────────────────┐ │
  │ │ Hand grip      │ │ ← positioned at hand joint
  │ │ ┌──────────┐   │ │
  │ │ │ Gun      │   │ │ ← positioned at grip point
  │ │ └──────────┘   │ │
  │ └────────────────┘ │
  └────────────────────┘

  On fire:
  ┌────────────────────────────────┐
  │ [Character] + [Hand] + [Gun]  │
  │                    ┌────────┐ │
  │                    │Muzzle  │ │ ← from weapons/N/4 Shoot_effects/
  │                    │Flash   │ │
  │                    └────────┘ │
  │                        ● ──────► Bullet projectile
  └────────────────────────────────┘    ← from weapons/N/5 Bullets/
```

### 5.5 Bullet–Gun Mapping

Each gun type has a corresponding bullet type:

| Gun | Bullet | Description |
|-----|--------|-------------|
| Pistol A (01-02) | Bullet A (01) | Standard round |
| Pistol B (03-04) | Bullet B (02) | Hollow point |
| Compact C (05-06) | Bullet C (03) | Small rapid |
| Compact D (07-08) | Bullet D (04-05) | Dual shell variants |
| Compact E (09-10) | Bullet E (06-07) | Energy cell variants |
| Detail F (11-12) | Bullet F (08) | Precision slug |
| Rifle G (13-14) | Bullet G (09-10) | Heavy round variants |
| Rifle H (15-16) | Bullet H (11) | Armour piercing |
| Rifle I (17-18) | Bullet I (12) | Plasma bolt |
| Special J (19-20) | Bullet J (13) | Explosive charge |

### 5.6 Weapon Spawn System

A maximum of **4 weapons** can exist on the ground in a level at any time. This prevents the level from becoming cluttered and keeps each weapon meaningful.

#### Spawn Rules

```
  ┌──────────────────────────────────────────────────────────────────────┐
  │               WEAPON SPAWN SYSTEM — MAX 4 PER LEVEL                 │
  │                                                                      │
  │  Spawn slots are defined per level in LevelData:                    │
  │  int[][] weaponSpawns = {                                            │
  │    { worldX, worldY, gunTypeId },   // Slot A                       │
  │    { worldX, worldY, gunTypeId },   // Slot B                       │
  │    { worldX, worldY, gunTypeId },   // Slot C                       │
  │    { worldX, worldY, gunTypeId },   // Slot D                       │
  │  };                                                                  │
  │                                                                      │
  │  Spawn Lifecycle:                                                    │
  │                                                                      │
  │  ┌──────────┐   level start    ┌──────────────┐                    │
  │  │  SLOT    │ ──────────────►  │   WEAPON     │                    │
  │  │ EMPTY    │                  │  ON GROUND   │ ──► player [E]     │
  │  └──────────┘                  └──────────────┘    picks up        │
  │       ▲                               │                            │
  │       │ 30s respawn timer             │ picked up                  │
  │       │ (if slot is empty)            ▼                            │
  │       │                       ┌──────────────┐                    │
  │       └──── respawn ──────────│   IN PLAYER  │                    │
  │                                │  INVENTORY   │                    │
  │                                └──────┬───────┘                    │
  │                                       │ [F] throw / [G] drop       │
  │                                       ▼                            │
  │                               ┌──────────────┐                    │
  │                               │  DROPPED ON  │ ── stays until      │
  │                               │   GROUND     │    picked up or     │
  │                               └──────────────┘    collects        │
  │                                                                    │
  │  Enemy-drop weapons also count toward the 4-weapon cap.            │
  │  If cap is reached, new enemy drops despawn immediately.            │
  └──────────────────────────────────────────────────────────────────────┘
```

#### Weapon Placement — Level 1

| Slot | World X | World Y | Gun Type | Notes |
|------|---------|---------|----------|-------|
| A | 480 | 510 | Pistol A (dark) | Near spawn — tutorial gun |
| B | 1800 | 250 | Compact C | Mid-section elevated platform |
| C | 3200 | 290 | Rifle G (dark) | Zigzag ascent section |
| D | 4200 | 440 | Special J (teal) | Pre-boss arena |

#### Weapon Placement — Level 2

| Slot | World X | World Y | Gun Type | Notes |
|------|---------|---------|----------|-------|
| A | 600 | 510 | Pistol B (light) | Entrance section |
| B | 2500 | 300 | Compact E | Reactor corridor |
| C | 4500 | 280 | Rifle H (dark) | Catwalk gauntlet |
| D | 5800 | 440 | Special J (red) | Final gauntlet |

#### Weapon Sprite on Ground

Dropped/spawned weapons display their static gun sprite, gently bobbing using a sine-wave offset:

```java
// Ground weapon idle bob (in update loop):
float bob = (float)(Math.sin(System.currentTimeMillis() / 300.0) * 3.0);
g.drawImage(gunSprite, (int)worldX - cameraX, (int)worldY + (int)bob, null);
```

A glowing outline (drawn 1px outset in weapon's tint colour) appears when the player is within pickup range (32px).

---

### 5.7 HUD Weapon Inventory Display

The weapon inventory HUD shows 4 slots permanently visible at the **bottom-right** of the screen. Slots allow instant switching with keys `[1]` `[2]` `[3]` `[4]`.

#### Inventory Bar Layout

```
  ┌─────────────────────────────────────────────────────────────────────┐
  │          WEAPON INVENTORY BAR (bottom-right HUD)                    │
  │                                                                      │
  │   ┌───────────────┬───────────────┬───────────────┬───────────────┐ │
  │   │  SLOT [1]     │  SLOT [2]     │  SLOT [3]     │  SLOT [4]     │ │
  │   │ ┌───────────┐ │ ┌───────────┐ │ ┌───────────┐ │ ┌───────────┐ │ │
  │   │ │  🔫 gun   │ │ │  ░░░░░░░  │ │ │  ░░░░░░░  │ │ │  ░░░░░░░  │ │ │
  │   │ │  sprite   │ │ │  [empty]  │ │ │  [empty]  │ │ │  [empty]  │ │ │
  │   │ └───────────┘ │ └───────────┘ │ └───────────┘ │ └───────────┘ │ │
  │   │  8 / 12 ammo  │     ---       │     ---       │     ---       │ │
  │   └───────────────┴───────────────┴───────────────┴───────────────┘ │
  │     ▲ ACTIVE (glowing border)                                        │
  │                                                                      │
  │   Active slot: golden glow border (3px), slightly larger (scale 1.1)│
  │   Empty slot:  dark panel, silhouette lock icon                      │
  │   Ammo text:   digit sprites from gui/7 Numbers/ + "/" separator     │
  └─────────────────────────────────────────────────────────────────────┘
```

#### Slot State Rules

| State | Visual | Trigger |
|-------|--------|---------|
| **Empty** | Dark panel, greyed lock icon, `---` ammo | No weapon assigned |
| **Armed** | Gun thumbnail sprite, `N / max` ammo digits | Weapon picked up |
| **Active** | Golden border glow, 1.1× scale | Currently selected slot |
| **Reload** | Pulsing border + spinning indicator | [R] pressed, reloading |
| **Out of ammo** | Red tint border, `0 / max` ammo text flashing | ammo == 0 |

#### Switching Logic

```
  Key [1] → activeSlot = 0
  Key [2] → activeSlot = 1
  Key [3] → activeSlot = 2
  Key [4] → activeSlot = 3
  Key [Q] or Scroll ↑ → activeSlot = (activeSlot - 1 + 4) % 4  // wrap prev
  Scroll ↓            → activeSlot = (activeSlot + 1) % 4      // wrap next

  If target slot is EMPTY → skip to next occupied slot
  Switching animation: slot slides up 4px, flashes once (60ms)
```

#### TAB — Full Inventory Overlay

Pressing `[TAB]` opens a centred inventory overlay showing all 4 slots with:
- Full gun name text (not just thumbnail)
- Full ammo bar (bar fill from `gui/2 Bars/`)
- Brief description (`"Rapid fire, 20 rounds"` etc.)
- Dimmed gameplay behind the overlay (alpha 0.6 black curtain)
- Press `[TAB]` again or `[ESC]` to dismiss

## 6. Animated Objects — Physics, Collision & Sprites

### 6.1 Animated Object Type Directory

All animated objects are `ObjType` enum instances returned by `activeLevel.getAnimatedObjects()` as `int[][] { type, worldX, worldY }` arrays. Two categories exist: **Stationary** (fixed position) and **Moving** (translate through world space).

| ObjType | Category | Asset Dir | Frames | Rate | Collision | Interaction |
|---------|----------|-----------|--------|------|-----------|-------------|
| `CARD` | Collectible | `.../Anim_Collectible_Card/` | 6 | 80ms | Collect trigger | Auto-collect on overlap |
| `MONEY` | Collectible | `.../Anim_Collectible_Money/` | 6 | 80ms | Collect trigger | Auto-collect on overlap |
| `CHEST` | Interactive | `.../Anim_Interactive_Chest_OpenAndClose_*/` | 8 | 100ms | Solid top edge | E key opens |
| `SCREEN` | Interactive | `.../Anim_Interactive_Screen/` | 4 | 80ms | None | E key = lore display |
| `PORTAL` | Transition | `.../Anim_Transition_PortalActive/` | 4 | 100ms | Enter trigger | Walk in = level transition |
| `HAMMER` | Hazard | `.../Anim_Hazard_Hammer/` | 8 | 60ms | Swing hitbox | Frames 3–6 deal −30 HP on contact |
| `CONVEYOR` | Environment | `.../Anim_Environment_Conveyor/` | 4 | 80ms | Solid top | Pushes standing player ±40 px/s |
| `MOVING_PLATFORM` | Platform | `.../Anim_MovingPlatform/` | 4 | 100ms | Solid top | Player rides when standing on it |
| `TURRET` | Hazard (L2) | Level 2 objects dir | 8 | 50ms | None (rotates) | Auto-fires projectile every 3 s |

**Level Asset Paths:**
- **Level 1:** `Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/`
- **Level 2:** `Resources/industrial-zone/1 Tiles/power-station-level-2/7 Objects_level_2/` (Card uses Blue Card, Chest uses BlueTeal variant, Turret path differs)

---

### 6.2 HorizontalSpritesheetLoader — Loading Pipeline

All animated objects load frames using `animation.HorizontalSpritesheetLoader`:

```java
// Inside spawnAnimatedObjects() — for each entry in activeLevel.getAnimatedObjects():
int type   = objData[0];    // ObjType ordinal
int worldX = objData[1];
int worldY = objData[2];

String assetPath = activeLevel.getAnimAssetPath(type); // delegates to Level1 / Level2

HorizontalSpritesheetLoader loader = new HorizontalSpritesheetLoader();
BufferedImage sheet = ImageIO.read(new File(assetPath));

// Auto frame count: total width ÷ frame height (square frames assumed)
int frameH     = sheet.getHeight();
int frameW     = frameH;
int frameCount = sheet.getWidth() / frameW;

List<BufferedImage> frames = new ArrayList<>();
for (int i = 0; i < frameCount; i++) {
    frames.add(sheet.getSubimage(i * frameW, 0, frameW, frameH));
}

// Frame durations per type:
int frameDurationMs = switch (type) {
    case CARD, MONEY, SCREEN, CONVEYOR -> 80;
    case CHEST, PORTAL, MOVING_PLATFORM -> 100;
    case HAMMER  -> 60;   // faster swing
    case TURRET  -> 50;   // fast rotation
    default -> 100;
};

animatedObjects.add(new AnimatedObject(type, worldX, worldY, frames, frameDurationMs));
```

---

### 6.3 Physics & Collision Model

#### Collectibles (CARD, MONEY)
```
World:   Fixed position — no movement, no gravity
Hitbox:  Full frame bounds (e.g. 32×32)

Each frame:
  if intersects(playerBounds, objBounds):
      collect(obj)   // +score, play SFX, spawn particle VFX
      obj.active = false  // disappears from world
```

#### CHEST
```
World:   Fixed position — sits on platform surface
Hitbox:  Solid on TOP edge only (4 px thick) — player can stand on chest lid

States:
  CLOSED → (player within 48 px + E key) → OPENING (plays frames 0→7 once)
  OPENING → (anim complete) → OPEN (stays on last frame)

  On OPENING:
    • Play Unlocked_chest.wav
    • Spawn loot items per loot table (Section 13.2)
    • Spawn golden particle burst VFX
    • +100 to score
```

#### PORTAL
```
World:   Fixed position — looping animation
Hitbox:  Trigger zone 8 px inside portal bounds

Each frame:
  if intersects(playerBounds, portalTrigger):
      triggerLevelTransition()   // next level or main menu
      // Play Portal_moving.wav
```

#### HAMMER
```
World:   Fixed position — swings on pivot (rotational spritesheet)
Damage hitbox: hammer HEAD only, ACTIVE during frames 3–6 of the 8-frame cycle

Each frame:
  if isActiveSwingFrame && intersects(playerBounds, hammerHeadBounds):
      player.takeDamage(30)
      player.applyKnockback(direction=AWAY, force=200 px/s)
```

#### CONVEYOR
```
World:   Fixed position
Hitbox:  Solid top edge — player lands and stands normally

Each frame:
  if playerStandingOnConveyor:
      player.vx += CONVEYOR_DIRECTION * 40  // left: -40, right: +40
      // normal horizontal cap still applies
```

#### MOVING_PLATFORM
```
World:   Translates between two world-space waypoints (start ↔ end)
Hitbox:  Solid top edge — carries player

Route types:
  Horizontal: moves left/right within ±300 px of spawn X
  Vertical:   moves up/down within ±200 px of spawn Y
  Speed:      60 px/s horizontal, 40 px/s vertical

Each frame:
  move platform toward current waypoint
  if player standing on top:
      player.x += platform.dx   // carry horizontally
      player.y += platform.dy   // carry vertically
  if reached waypoint:
      reverse direction (bounce)
```

#### TURRET (Level 2)
```
World:   Fixed position — rotates sprite to face player
Collision: None — player cannot land on turret

Behaviour (state machine):
  [IDLE] → every 3s: face player → [FIRING] → spawn bullet (speed=400, dmg=15) → 2s cooldown → [IDLE]
  Bullet: no gravity, despawns at 800px or on hit
```

---

### 6.4 AnimatedObject Class Design

```java
class AnimatedObject {
    int            type;           // ObjType ordinal
    int            worldX, worldY; // position in world (top-left)
    List<BufferedImage> frames;
    int            frameDurationMs;
    int            currentFrame;
    long           lastFrameTime;
    boolean        active;         // false = collected / opened / gone

    // Moving platform fields:
    int   moveStartX, moveStartY;
    int   moveEndX,   moveEndY;
    float speed;
    int   directionX, directionY;  // ±1 each axis

    // Chest:
    boolean opened;

    // Conveyor:
    int conveyorDirection; // -1 = left, +1 = right

    void update(long now, Player player) {
        // 1. Advance animation frame
        if (now - lastFrameTime > frameDurationMs) {
            currentFrame = (currentFrame + 1) % frames.size();
            lastFrameTime = now;
        }
        // 2. Type-specific update (movement, collision, interaction)
    }

    void render(Graphics2D g, int cameraX, int cameraY) {
        if (!active) return;
        g.drawImage(frames.get(currentFrame),
                    worldX - cameraX, worldY - cameraY, null);
    }
}
```

---

## 7. Interactive Transporters & Hang Mechanic

### 7.1 Overview

Three interaction-based transporters let the player traverse the level by pressing the E key:

| Transporter | Trigger Condition | E-Key Action | Movement |
|-------------|-------------------|--------------|----------|
| **H-Mover** (Horizontal Traveller) | Player standing on top | Start / stop riding | Horizontal left or right |
| **V-Mover** (Vertical Lift) | Player standing on top | Call lift / depart | Vertical up or down |
| **Drone Hang** | Player standing *below* a HOVER drone | Grab / release | Follows drone's patrol route |

All transporters display a key-cap prompt above the player when interaction is available:

```
  ╔═══════════╗
  ║  [E] RIDE ║   ← rendered using KeyBoard_E.png from KeyboardKeyAssets
  ╚═══════════╝
         ☻   ← player
```

**Prompt asset:** `Resources/industrial-zone/KeyBoard_Keys/KeyBoard_E.png`  
**Interaction radius:** 64 px from player centre

---

### 7.2 H-Mover (Horizontal Traveller)

**Sprite:** `Anim_MovingPlatform` (4 frames @ 100 ms, looping)  
**Placement:** `{ ObjType.H_MOVER.id, startX, platformY, endX }` — 4 values

```
  Route:  ◄──────────────────────────────────────────────►
          startX                                      endX

  IDLE AT START:
  ╔══════════════╗   ← platform stationary
  ║              ║
  ╚══════════════╝

  PLAYER BOARDS (player on top for 0.2 s):
  ╔══════════════╗
  ║      ☻       ║   [E] RIDE prompt shown above player
  ╚══════════════╝

  MOVING (E pressed):
        ╔══════════════╗
  ─────►║      ☻       ║────────────────────────────────►
        ╚══════════════╝   platform + player travel together
  
  ARRIVED AT ENDPOINT:
                                              ╔══════════════╗
                                              ║      ☻       ║  [E] RETURN
                                              ╚══════════════╝
```

**State Machine:**
```
[IDLE_START] → (player on top + E) → [MOVING_FORWARD]
[MOVING_FORWARD] → (reaches endX OR player jumps) → [IDLE_END]
[IDLE_END]   → (player on top + E) → [MOVING_BACK]
[MOVING_BACK] → (reaches startX) → [IDLE_START]
```

**Properties:**
| Property | Value |
|----------|-------|
| Speed | 100 px/s |
| Board detection | Player on top for ≥ 200 ms |
| Cancel | Player jumps (SPACE) → platform continues to endpoint |
| Player carry | `player.x += direction * speed * dt` each frame while riding |
| SFX start | `Elevator_motor.wav` |
| SFX stop | Stop clip on arrival |

---

### 7.3 V-Mover (Vertical Lift)

**Sprite:** `Anim_MovingPlatform` (vertical interpretation)  
**Placement:** `{ ObjType.V_MOVER.id, platformX, bottomY, topY }` — 4 values

```
  ▲  topY
  │
  │   ╔═══════╗  ← IDLE_TOP (arrived at top floor)
  │   ║   ☻   ║
  │   ╚═══════╝
  │       │
  │       │  60 px/s rising  /  80 px/s descending
  │       │
  │   ╔═══════╗  ← IDLE_BOTTOM (ground level, start position)
  │   ║       ║
  │   ╚═══════╝
  │
  ▼  bottomY (ground)
```

**State Machine:**
```
[IDLE_BOTTOM] → (player on top + E) → [RISING]
[RISING]      → (reaches topY)       → [IDLE_TOP]
[IDLE_TOP]    → (player on top + E, or 8 s auto-return) → [DESCENDING]
[DESCENDING]  → (reaches bottomY)    → [IDLE_BOTTOM]
```

**Player Carry (while lift is moving):**
```java
player.y  += lift.vy * dt;
player.vy  = 0;         // override gravity while on lift
player.onGround = true; // keeps player in ground movement state
```

**Properties:**
| Property | Value |
|----------|-------|
| Rise speed | 60 px/s |
| Descend speed | 80 px/s |
| Auto-return delay | 8 s (lift returns to bottom if player leaves) |
| Interaction label (bottom) | `[E] ASCEND` |
| Interaction label (top) | `[E] DESCEND` |
| SFX | `Lift_mechanism.wav` |

---

### 7.4 Drone Hang Mechanic

The player can grab and hang from a hovering **HOVER drone** (EnemyType 6) by positioning directly below it and pressing E.

```
  ════════════════════════════════════════════════
  STEP 1 — DETECT (show prompt)
  ════════════════════════════════════════════════

        ≈≈≈≈≈≈≈≈≈≈≈≈   ← HOVER drone (IDLE or PATROL state)

              ☻          [E] GRAB prompt shown above player

  Condition:
    abs(player.centerX - drone.centerX) < 64
    drone.y > player.y
    drone.y - player.baseY < 120
    drone.state == IDLE || PATROL

  ════════════════════════════════════════════════
  STEP 2 — GRAB (E pressed)
  ════════════════════════════════════════════════

        ≈≈≈≈≈≈≈≈≈≈≈≈   ← drone continues patrol
              │   12 px
              ☻           ← HANG animation (#10), legs dangling

    player.gravity   = false
    player.vy        = 0
    player.x         = drone.centerX - player.width/2
    player.y         = drone.y + drone.height + 12
    playerState      = HANG

  ════════════════════════════════════════════════
  STEP 3 — RIDE
  ════════════════════════════════════════════════

  ──────────────────────────────────────────────►
        ≈≈≈≈≈≈≈≈≈≈≈≈   drone moves along patrol route
              │
              ☻           player moves with drone (x,y anchored)

    • A/D keys: no effect while hanging
    • CTRL/K:   can still fire weapon (useful for clearing path)

  ════════════════════════════════════════════════
  STEP 4 — RELEASE
  ════════════════════════════════════════════════

  E pressed → release → FALL state (normal gravity resumes)
  SPACE pressed → release + upward boost (vy = −150 px/s) → JUMP arc
  Drone killed → immediate release → FALL state

              ☻                 (falling after release)
             /|\                gravity restored
              │
```

**Hang Properties:**
| Property | Value |
|----------|-------|
| Horizontal grab range | 64 px (player.cx to drone.cx) |
| Vertical grab range | 120 px (drone must be above player by ≤ 120 px) |
| Hang offset below drone | 12 px |
| Release — E key | Drop (normal fall) |
| Release — SPACE | Boost release (vy = −150 px/s) |
| Fire weapon while hanging | Yes (CTRL or K) |
| Move while hanging | No (A/D ignored) |
| Gravity during hang | Disabled |
| Player animation | `HANG` (anim #10) |
| Eligible drone types | HOVER (type 6) only |
| Eligible drone states | IDLE or PATROL (not CHASE/ATTACK/HURT/DEATH) |

---

### 7.5 E-Key Interaction Resolution

When multiple interactable objects are within 64 px, the nearest or highest-priority one wins:

```java
// Priority order (highest to lowest):
// 1. Drone Hang
// 2. H-Mover or V-Mover (transporter)
// 3. Chest (open/close)
// 4. Screen (lore/hint)
// 5. Card / Money (auto-collect — no E needed)

InteractableObject nearest = findBestInteractable(player, 64);
if (nearest != null) {
    // Draw [E] prompt above player using KeyBoard_E.png asset
    drawKeyCapPrompt(g, keyboardAssets[KEY_E], player.screenX, player.screenY - 40);

    if (keyPressed[KeyEvent.VK_E]) {
        nearest.interact(player);
        keyPressed[KeyEvent.VK_E] = false; // consume input
    }
}
```

**Key-cap prompt rendering:**  
Use `KeyboardKeyAssets.KEYBOARD_E` path → load PNG 44×44 px → draw at `(player.screenX - 10, player.screenY - 56)` with a small translucent dark background behind it.

---

## 8. Level Design

### 8.1 Level 1 — Industrial Zone

```
World: 16,000 × 192 px (500 cols × 6 rows @ 32×32 tiles)
Tile dir: Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/
Background: 5-layer parallax (factory silhouettes, lavender sky)
Enemies: 13 drones + 1 boss (Combat Tank)
```

#### Platform Layout (23 platforms)

```
                    LEVEL 1 — PLATFORM MAP (not to scale)
  Y=0 ─────────────────────────────────────────────────────────────►
      │  SKY / PARALLAX BACKGROUND LAYERS
      │
 Y=180├─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
      │                              [3680,180]
 Y=200├─ ─ ─ ─ ─ ─ ─ ─[1520,200]      ▓▓▓▓     [4100,200]
      │                  ▓▓▓▓                      ▓▓▓
 Y=220├─ ─ ─ ─ ─ ─ ─ ─ ─ ─[2600,220]─ ─ ─ ─[3420,240]─ ─ ─ ─ ─
      │                       ▓▓▓▓▓       ▓▓▓▓▓
 Y=250├─ ─ ─ ─ ─[1280,250]─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─[4500,280]─
      │            ▓▓▓▓▓       [2350,280]   [3200,310]    ▓▓▓▓▓
 Y=300├─ ─[1060,300]─ ─ ─ ─ ─ ─  ▓▓▓     ─ ─ ▓▓▓▓ ─ ─ ─ ─ ─ ─
      │     ▓▓▓▓   [1780,280]
 Y=350├[780,350]──── ─ ▓▓▓▓▓ ─[2100,350]─ ─[2950,380]─[4000,350]
      │  ▓▓▓▓▓                   ▓▓▓         ▓▓▓▓      ▓▓▓▓▓▓▓▓▓
 Y=400├─ ─[520,400]─ ─[1520,440]─ ─ ─[2600,420]─[3680,400]─ ─ ─ ─
      │     ▓▓▓▓       ▓▓▓▓▓▓       ▓▓▓▓▓▓▓       ▓▓▓▓▓▓
 Y=440├[250,440]─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
      │  ▓▓▓▓▓                                          [4950,320]
 Y=480├─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─[4800,480]▓
      │                                                ▓▓▓▓▓▓▓▓▓▓▓
 Y=520╠════════════════════════════════════════════════════════════►
      │  GROUND (0, 520, 16000, 800) — solid fill below
  Y=  ▼
```

#### Level 1 Sections

| Section | X Range | Platforms | Enemies | Theme |
|---------|---------|-----------|---------|-------|
| 1. Gentle Start | 0–1000 | 3 stepping stones | 3 (UFO, JET, UFO) | Tutorial area |
| 2. Step-Up | 1000–2000 | 5 ascending + 1 low alt | 3 (HOVER, UFO, JET) | Vertical challenge |
| 3. Gap Challenge | 2000–2900 | 3 high + 1 low bridge | 3 (HOVER, UFO, JET) | Precision jumps |
| 4. Zigzag Ascent | 2900–4000 | 4 ascending + 1 shortcut | 3 (UFO, JET, HOVER) | Complex routing |
| 5. Boss Arena | 4000–5400 | 5 (wide fight floor, ledges) | 1 BOSS_TANK | Final confrontation |

#### Enemy Placement — Level 1

```
  Section 1          Section 2          Section 3          Section 4         Section 5
  ┌─────────┐        ┌─────────┐        ┌─────────┐        ┌─────────┐       ┌─────────────┐
  │ ☁ ☁ ☁  │        │ ☁ ☁ ☁  │        │ ☁ ☁ ☁  │        │ ☁ ☁ ☁  │       │             │
  │  UFO    │        │ HOVER   │        │ HOVER   │        │  UFO    │       │             │
  │    JET  │        │    UFO  │        │    UFO  │        │    JET  │       │   ╔═══╗     │
  │ UFO     │        │  JET    │        │  JET    │        │ HOVER   │       │   ║BOSS║    │
  │─────────│        │─────────│        │─────────│        │─────────│       │   ║TANK║    │
  │▓▓▓▓▓▓▓▓│        │▓▓▓▓▓▓▓▓│        │▓▓▓▓▓▓▓▓│        │▓▓▓▓▓▓▓▓│       │▓▓▓╚═══╝▓▓▓▓│
  └─────────┘        └─────────┘        └─────────┘        └─────────┘       └─────────────┘
  x: 0–1000          x: 1000–2000       x: 2000–2900       x: 2900–4000     x: 4000–5400
```

### 8.2 Level 2 — Power Station

```
World: 28,800 × 192 px (900 cols × 6 rows @ 32×32 tiles)
Tile dir: Resources/industrial-zone/1 Tiles/power-station-level-2/
Background: 5-layer parallax with Day/Night variants
Enemies: 15 drones + 2 bosses (Armoured Knight + Winged Warrior)
```

#### Platform Layout (30 platforms)

| Section | X Range | Platforms | Enemies | Theme |
|---------|---------|-----------|---------|-------|
| 1. Entrance | 0–1200 | 4 ascending | 3 (JET, UFO, HOVER) | Breach the walls |
| 2. Reactor Corridor | 1200–2400 | 4 ascending + 1 low pipeline | 3 (JET, UFO, JET) | Tight passageways |
| 3. Vertical Shaft | 2400–3000 | 5 staircase | 3 (HOVER, UFO, JET) | Pure verticality |
| 4. Catwalk Gauntlet | 3000–4200 | 5 platforms + 1 secret | 3 (HOVER, UFO, JET) | Gauntlet run |
| 5. Mini-Boss Arena | 4200–4800 | 2 (wide floor + ledge) | BOSS_KNIGHT + 2 drones | Knight encounter |
| 6. Final Gauntlet | 4800–5700 | 4 platforms + 1 low route | 3 (UFO, JET, HOVER) | Last challenge |
| 7. Final Boss Arena | 5700–6300 | 3 (wide floor, cover, perch) | BOSS_WARRIOR + 1 drone | Climactic battle |

### 8.3 Tile Alias System

Maps use a single-character alias system defined in the map header:

```
#a=01_Platform_SolidBlock_FlatTopFull_DarkPurple_PrimaryWalkableFloorTile.png
#b=03_Platform_SolidBlock_FlatTopMid_MutedBluePurple_StandardFloorFill.png
...
```

**Tile Categories Used:**

| Category | Count | Purpose |
|----------|-------|---------|
| Walkable floors | 3+ | Top surface of platforms (#a, #b, #c) |
| Structural fill | 3+ | Underground solid blocks (#d, #e, #f) |
| Corner tiles | 4+ | Inner/outer corners at platform edges (#g–#k) |
| Diagonal slopes | 6+ | Ramps and transitions (#l–#q) |
| Edge tiles | 4+ | Platform edges and shelf bars |
| Decorative | 10+ | Pipes, panels, vents, industrial detail |
| Hazard markers | 4+ | Warning stripes, caution zones |

### 8.4 Tile Map Adventure Design Principles

The tile maps should feel **hand-crafted and adventurous**, not repetitive flat corridors. This section defines the rules for building rich, complex maps using the available tile set.

#### 8.4.1 Ground Continuity — Non-Continuous Design

```
  ✗ BAD — Flat continuous ground (boring):
  ══════════════════════════════════════════════  <- single flat ground line
  No variation, no challenge, no interest.

  ✓ GOOD — Non-continuous ground (adventurous):

  ════════        ══════════         ═══              ════════════
         ╲          ╱                   ╲           ╱
          ▓▓▓▓▓▓▓▓▓▓           PIT     ▓▓▓▓▓▓▓▓▓▓▓
  Gaps + pits + level changes + ramps + underground routes
```

**Ground gap rules:**
- Minimum gap: 64px (2 tiles) — forces the player to time a jump
- Maximum gap: 256px (8 tiles) — larger gaps require a run-up
- Gaps over 192px should have a visible bottom pit or hazard tiles
- At least **3 major gaps** per level section (400px span)

#### 8.4.2 Dot (`.`) Tile = NO Collision

The map text file uses `.` as the background filler character. These tiles are **decoration only** — they render a background texture but have **zero collision**. The player falls through them.

```
  Map legend:
  .  = background filler  → NO COLLISION (visual decoration only)
  a  = platform tile      → SOLID COLLISION (player stands on it)
  b  = fill tile          → SOLID COLLISION (underground structural)
  H  = hazard tile        → DAMAGE + no solid collision (walk-through damage)

  Example map row:
  .....a.a...a......b.b...a.a...
  └────┘└─┘  └──┘  └─┘ └─┘└─┘
   empty  plat  plat   fill plat  plat
   (no collision)   ↑ all solid
```

#### 8.4.3 Underground Passages

Maps must include underground sections — passages dug below the main ground level:

```
  ┌─────────────────────────────────────────────────────────────────────┐
  │  SURFACE LEVEL:  ══════════╗           ╔════════════════════════   │
  │                            ║           ║                           │
  │  DROP HOLE:                ▓           ▓  (vertical shaft 64px)    │
  │  (no tile here)            ▓           ▓                           │
  │                            ▓           ▓                           │
  │  UNDERGROUND:   ╔══════════╝           ╚════════════════╗          │
  │                 ║...treasure chest...ladder...secret...  ║          │
  │                 ╚══════════════════════════════════════════        │
  │                                                                     │
  │  Underground rules:                                                 │
  │  • Ceiling height: 96px min (3 tiles) — player + enemies fit       │
  │  • Must have at least 1 way up (ladder) and 1 way to enter (pit)   │
  │  • Should contain reward: card, chest, or better weapon spawn       │
  │  • Hazard tiles (#31–#44) line the underground floor edges          │
  └─────────────────────────────────────────────────────────────────────┘
```

#### 8.4.4 Top-Climbing Elements — Overhead Routes

Players can reach elevated paths via ladders, ledge-hangs, and pull-ups:

```
  OVERHEAD ROUTE (top-climbing sequence):
  ─────────────────────────────────────────────────────────────────────

  Level: ═══╗                ╔═══╗           ╔════════════════════
            ║  [LADDER A]    ║   ║           ║  overhead platform
            ║████████████████║   ║           ║  (accessible via
            ║                ║   ║ [HANG] ───┛  ledge hang + pullup)
                         ════╝   ╚════
                        mid-platform  ledge edge ← player hangs here

  Three types of upward traversal available:
  ┌─────────────────┬──────────────────────────────┬──────────────────┐
  │ Method          │ Required Assets               │ Keys Used        │
  ├─────────────────┼──────────────────────────────┼──────────────────┤
  │ Ladder A/B      │ Tall ladder sprites (#09 anim)│ [W] / [S]       │
  │ Ledge Hang      │ Platform with exposed edge    │ [W] to pull up  │
  │ Moving Platform │ H-Mover / V-Mover transporter │ Stand on it     │
  └─────────────────┴──────────────────────────────┴──────────────────┘
```

#### 8.4.5 Tile Adjacency Synchronisation Rules

Adjacent tiles in the map must use **tiles from compatible categories** — visually matching edges prevent jarring breaks. The industrial zone tile set categorises tiles into visual families:

```
  TILE FAMILY GROUPS (must use same group for adjacent placements):

  ┌──────────────────────────────────────────────────────────────────────┐
  │  FAMILY A — Dark Purple Solid                                        │
  │  Tiles: 01 (top), 03 (mid fill), 21 (heavy fill)                   │
  │  Use together: top surface + structural fill below                  │
  │                                                                      │
  │  FAMILY B — Grid Panel / Industrial Wall                            │
  │  Tiles: 07, 11 (grid panels), 08 (vertical column), 13 (thin strip)│
  │  Use together: wall sections and vertical supports                  │
  │                                                                      │
  │  FAMILY C — Edge & Ledge Bars                                       │
  │  Tiles: 18 (shelf bar), 23 (bracket shelf), 26 (bolted ledge),     │
  │         27 (wide ledge)                                              │
  │  Use together: platform edges and overhanging ledge platforms        │
  │                                                                      │
  │  FAMILY D — Diagonal / Slope Transitions                            │
  │  Tiles: 19, 24, 28, 29, 30, 38 (various diagonal half-blocks)      │
  │  Use together: ramps and slope transitions between height levels     │
  │                                                                      │
  │  FAMILY E — Corner Caps                                             │
  │  Tiles: 04, 06, 10, 20, 22, 25 (inner/outer corners)               │
  │  Use at: all platform corner joints — NEVER isolated               │
  │                                                                      │
  │  FAMILY F — Hazard Surfaces                                         │
  │  Tiles: 09, 31–44 (stripe, crosshatch, zigzag hazards)             │
  │  Use together: always surrounded by solid platform tiles for context │
  └──────────────────────────────────────────────────────────────────────┘

  ADJACENCY RULE:
  ┌───┬───┐  TOP tile must be from Family A (solid top surface)
  │ A │ A │  ← flat top: tiles 01 or 16 (dot-rivet variant)
  ├───┼───┤
  │ B │ B │  ← structural fill: tiles 03 or 21
  ├───┼───┤
  │ B │ B │  ← continued fill (go as deep as needed)
  └───┴───┘

  CORNER RULE:
  ┌──┬──┐
  │E │A │  ← top-right corner: tile 04 (InnerTopRight corner cap)
  ├──┼──┤
  │B │B │
  └──┴──┘
```

#### 8.4.6 Map Complexity Tier Guidelines

| Tier | Description | Gap Count | Ladders | Underground | Hazard Zones |
|------|-------------|-----------|---------|-------------|--------------|
| **1 — Tutorial** | Gentle intro, flat with 1–2 gaps | 2 | 0 | 0 | 0 |
| **2 — Standard** | Mixed elevations, some gaps | 4–6 | 1 | 1 | 2 |
| **3 — Challenge** | Non-continuous, multi-path | 7–10 | 2–3 | 2 | 4+ |
| **4 — Labyrinth** | Underground + overhead + ladder routes | 10+ | 3+ | 2+ | 6+ |

**Level 1** uses Tiers 1→3 progressively (section 1=T1, section 4=T3).
**Level 2** uses Tiers 2→4 — starts harder, ends at labyrinth complexity.

#### 8.4.7 Hazard Tile Usage

Hazard tiles (tiles 09, 31–44) deal damage per frame (10 HP/s default). Rules:

- Hazard tiles have **no collision** — player walks through them (they act as damage zones)
- Always border hazard tiles with **solid wall tiles** on non-walkable sides
- Diagonal hazard stripes (tiles 09, 40) warn before an obstacle
- Dense crosshatch (tile 36) = instant-kill zone (100 damage/frame) — use sparingly in boss arenas only
- Hazard zones must be **visually distinct** — player should see them before touching

#### 8.4.8 Prop Object Placement Rules

| Prop Category | Collision | Interaction | Placement |
|---------------|-----------|-------------|-----------|
| Crates, barrels | Solid (push-able future) | None | Foreground decoration items on platforms |
| Ladders (A, B, Short) | Trigger zone | W/S to climb | Adjacent to vertical walls |
| Chests | Solid (stand-on-able) | [E] to open | End of underground passages |
| Flags / signs | No collision | Visual only | Section entry/exit markers |
| Benches, desks | No collision | [E] checkpoint sit | Checkpoint areas only |
| Screens / monitors | No collision | [E] terminal | Story beat zones |
| Fire extinguisher | No collision | Decoration | Alongside walls |
| Barricades/fences | Solid | None | Chokepoints, hazard borders |

---

## 9. Tile & Background System

### 9.1 Parallax Background Layers

#### Level 1 — Industrial Zone (5 layers)

```
  RENDER ORDER (back → front)           SCROLL FACTOR
  ═══════════════════════════            ═════════════

  Layer 1: Sky Base                      0.00 (fixed)
  ┌─────────────────────────────────────────┐
  │ Solid lavender-grey fill               │
  │ (drawn first, no scroll)               │
  └─────────────────────────────────────────┘

  Layer 2: Fractal Tree Silhouette       0.08
  ┌─────────────────────────────────────────┐
  │ █▓░   Mint sky + black tree cracks     │
  │  ░▓█  Very slow drift                  │
  └─────────────────────────────────────────┘

  Layer 3: Far Factory                   0.18
  ┌─────────────────────────────────────────┐
  │  ▓█▓  Light blue industrial shapes     │
  │ █▓░█  Distant smokestacks + tanks      │
  └─────────────────────────────────────────┘

  Layer 4: Mid Factory                   0.30
  ┌─────────────────────────────────────────┐
  │ ██▓██ Medium blue with pipe detail     │
  │ ▓██▓█ Buildings, gantries              │
  └─────────────────────────────────────────┘

  Layer 5: Near Factory                  0.50
  ┌─────────────────────────────────────────┐
  │ ████ Dark navy silhouette, large tanks  │
  │ ████ Closest layer, fastest scroll      │
  └─────────────────────────────────────────┘

  ════ GAMEPLAY LAYER ════                1.00
  Tiles, player, enemies, VFX
```

#### Level 2 — Power Station (Day/Night variants)

Level 2 backgrounds come in **Day** and **Night** variants (5 layers each), allowing time-of-day visual changes:

| Layer | Day Variant | Night Variant | Scroll |
|-------|-------------|---------------|--------|
| 1 | Sky gradient (warm yellow) | Dark starfield | 0.00 |
| 2 | Distant power lines | Dim power lines (glowing) | 0.08 |
| 3 | Mid factory pipes | Factory with lit windows | 0.18 |
| 4 | Near cooling towers | Cooling towers + steam glow | 0.30 |
| 5 | Closest reactor details | Reactor + warning lights | 0.50 |

### 9.2 Parallax Rendering Formula

```java
// For each background layer i:
float layerX = cameraX * SCROLL_FACTORS[i];

// Tile the image horizontally to fill the view
int imgW = bgLayers[i].getWidth();
int startTile = (int)(layerX / imgW);
int startX    = (int)(layerX % imgW);

for (int t = -1; t <= (SCREEN_W / imgW) + 1; t++) {
    g.drawImage(bgLayers[i],
        t * imgW - startX, 0,     // destination
        null);
}
```

### 9.3 Level 1 Object Sprites (44 animated + static objects)

From `Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/`:

| Object Type | Examples | Usage |
|-------------|----------|-------|
| Animated Collectibles | Card (6f), Money (6f), Chest (8f) | Rewards / loot |
| Portal | Portal (4f, red chevron gate) | Level transition / checkpoint |
| Industrial Objects | Pipes, valves, monitors, crates | Environmental decoration |
| Screens | Monitor displays (animated, 128×32) | Interactable terminals |
| Signs | Warning signs, direction arrows | Navigation aids |

### 9.4 Level 2 Object Sprites

| Category | Count | Description |
|----------|-------|-------------|
| Decoration | 27 | Environmental props, panels, lights |
| Power Lines | 9 | Overhead cable systems |
| Tubes | 11 | Pipe networks, vents, exhausts |

---

## 10. Enemy & Drone AI

### 10.1 Drone Types

| # | Type | Folder | HP | DMG | Detection | Speed | Score | Cooldown |
|---|------|--------|----|-----|-----------|-------|-------|----------|
| 1 | **UFO Saucer** | drones/1/ | 3 | 10 | 320px | Medium | 60 | 1.2s |
| 2 | **Jet Drone** | drones/2/ | 2 | 12 | 400px | Fast | 70 | 0.9s |
| 3 | **Drone 3** | drones/3/ | 4 | 8 | 280px | Slow | 50 | 1.5s |
| 4 | **Drone 4** | drones/4/ | 3 | 11 | 350px | Medium | 65 | 1.1s |
| 5 | **Drone 5** | drones/5/ | 2 | 14 | 450px | Fast | 75 | 0.8s |
| 5b | **Drone 5 Alt** | drones/5_2/ | 2 | 14 | 450px | Fast | 75 | 0.8s |
| 6 | **Hover Platform** | drones/6/ | 3 | 8 | 280px | Slow | 50 | 1.5s |

> **Currently Active:** Drones 1, 2, 6 (defined in `EnemyType` enum). Drones 3, 4, 5, 5b are available assets for future expansion.

### 10.2 Drone Animation Sets

Each drone folder contains spritesheets for different states:

**UFO Saucer (drones/1/) — 5 animations:**
| Animation | Frames | Description |
|-----------|--------|-------------|
| Idle (Hovering) | 4 | Metallic saucer floating with red/blue accent glow |
| Move | 4 | Tilted flight with thruster particles |
| Attack (Scan Beam) | 8 | Extending luminous scan beam downward |
| Hurt | 2 | Flash/recoil on impact |
| Death | 6 | Sparks + plummet + explosion |

**Jet Drone (drones/2/) — 2 animations:**
| Animation | Frames | Description |
|-----------|--------|-------------|
| Idle | 4 | Hovering with jet exhaust |
| Attack | 6 | Bomb drop sequence |

**Hover Platform (drones/6/) — 4 animations:**
| Animation | Frames | Description |
|-----------|--------|-------------|
| Idle | 4 | Floating platform, gentle oscillation |
| Move | 4 | Directional thrust |
| Attack | 6 | Capsule projectile launch |
| Hurt | 2 | Damage spark flash |

### 10.3 AI State Machine

```
                          ┌─────────────────┐
                          │   SPAWN / INIT  │
                          └────────┬────────┘
                                   │
                                   ▼
                ┌──────────────────────────────────┐
                │              IDLE                │
                │ • Face random direction           │
                │ • Wait 1–3 seconds                │
                │ • Hover vertically (sine wave)    │
                └─────────────┬────────────────────┘
                              │ timeout
                              ▼
         ┌──────────────────────────────────────┐
         │             PATROL                   │
         │ • Move left/right within home zone   │
         │ • Patrol radius: 200px from spawn    │
         │ • Speed: type-dependent              │
         │ • Reverse at patrol boundary         │
         └──────┬────────────────┬──────────────┘
                │                │
     player in  │                │ patrol timer
     detection  │                │ expires
     range      │                │
                ▼                ▼
    ┌───────────────────┐   (back to IDLE)
    │      CHASE        │
    │ • Face player     │
    │ • Move toward     │
    │   player at       │
    │   1.5× speed      │
    │ • Track Y within  │
    │   detection cone  │
    └─────────┬─────────┘
              │ within attack range
              ▼
    ┌───────────────────┐
    │      ATTACK       │
    │ • Play attack     │
    │   animation       │
    │ • Spawn           │
    │   projectile /    │
    │   melee hitbox    │
    │ • Apply damage    │
    │   to player       │
    │ • Enter cooldown  │
    └───┬─────┬─────────┘
        │     │
        │     │ player out of range
        │     └──────────────────►  CHASE / PATROL
        │
        │ took damage (hit by player)
        ▼
    ┌───────────────────┐
    │      HURT         │
    │ • Flash white     │
    │ • Knockback 30px  │
    │ • Invincible      │
    │   for 0.3s        │
    └───┬─────┬─────────┘
        │     │
        │     │ HP > 0
        │     └──────────────────►  CHASE (angry)
        │
        │ HP ≤ 0
        ▼
    ┌───────────────────┐
    │      DEATH        │
    │ • Play death anim │
    │ • Spawn smoke VFX │
    │ • Spawn blood VFX │
    │ • Add score       │
    │ • Drop loot?      │
    │ • Remove entity   │
    └───────────────────┘
```

### 10.4 Detection Cone

```
                     Detection Range (varies by type)
                     ◄──────────────────────────────►
                     
         ┌──────────────────────────────────────────────────┐
         │                                                  │
         │     DRONE                 detection zone         │
         │    ┌────┐    ╔═══════════════════════════════╗   │
         │    │ ☼  │────║  PLAYER in this zone?         ║   │
         │    └────┘    ║  → Switch to CHASE state      ║   │
         │              ╚═══════════════════════════════╝   │
         │                                                  │
         │              The detection zone is circular:     │
         │              dist = √((dx²) + (dy²))            │
         │              if dist < detectionRange → CHASE    │
         │                                                  │
         └──────────────────────────────────────────────────┘
```

---

## 11. Boss Encounters

### 11.1 Active Bosses (In-Game)

#### Boss 1: Combat Tank (`BOSS_TANK`)
- **Level:** 1 (section 5, [4800, 456])
- **HP:** 25 | **Damage:** 30 | **Score:** 100
- **Detection:** 200px | **Cooldown:** 2.0s
- **Sprites:** `Resources/industrial-zone/characters/enemies/sci-fi-antagonists/1/`
- **Behaviour:**
  - Slow, heavy movement along boss arena floor
  - Turret rotates to track player
  - Heavy projectile attack (slow, high damage)
  - When HP < 50%: enters enraged mode (faster fire rate)

```
  COMBAT TANK — Attack Pattern
  ═══════════════════════════════

  Phase 1 (HP 100%–50%):
  ┌──────────────────────────────────────┐
  │  [PATROL] → [AIM] → [FIRE] → 2.0s  │
  │      │                   cooldown    │
  │      └───────────────────────────────┤
  │                                      │
  │  Phase 2 (HP < 50%): ENRAGED        │
  │  [PATROL] → [AIM] → [FIRE] → 1.0s  │
  │      │         → [CHARGE] → [FIRE]  │
  │      └───────────────────────────────┤
  └──────────────────────────────────────┘
```

#### Boss 2: Armoured Knight (`BOSS_KNIGHT`)
- **Level:** 2 (section 5, [4000, 456])
- **HP:** 35 | **Damage:** 25 | **Score:** 120
- **Detection:** 240px | **Cooldown:** 1.8s
- **Sprites:** `Resources/industrial-zone/characters/enemies/sci-fi-antagonists/2/`
- **Behaviour:**
  - Agile melee boss with blade attacks
  - Dashes toward player for close-range combos
  - Shield block when player fires projectiles
  - When HP < 30%: enters fury mode (triple-slash combo)

```
  ARMOURED KNIGHT — Attack Pattern
  ═════════════════════════════════

  Phase 1 (HP 100%–30%):
  [PATROL] → player near → [DASH] → [SLASH] → [BLOCK] → 1.8s

  Phase 2 (HP < 30%): FURY
  [DASH] → [SLASH] → [SLASH] → [SLASH] → [BLOCK] → 1.0s
                        triple combo
```

#### Boss 3: Winged Warrior (`BOSS_WARRIOR`)
- **Level:** 2 (section 7, [5800, 456])
- **HP:** 50 | **Damage:** 35 | **Score:** 150
- **Detection:** 280px | **Cooldown:** 2.2s
- **Sprites:** `Resources/industrial-zone/characters/enemies/sci-fi-antagonists/3/`
- **Behaviour:**
  - Final boss — aerial + ground hybrid
  - Wing slam: jumps and crashes down, shockwave AoE
  - Projectile barrage: fires 3 spread projectiles
  - Phase 2 (HP < 40%): flight mode — hovers above, rains projectiles
  - Phase 3 (HP < 15%): desperation — continuous slam + fire

```
  WINGED WARRIOR — Attack Pattern
  ════════════════════════════════

  Phase 1 (HP 100%–40%):
  [GROUND] → [WING SLAM] → [PROJECTILE ×3] → 2.2s

  Phase 2 (HP 40%–15%): FLIGHT MODE
  [FLY UP] → [RAIN PROJECTILE ×5] → [DIVE SLAM] → 1.5s

  Phase 3 (HP < 15%): DESPERATION
  [SLAM] → [FIRE ×3] → [SLAM] → [FIRE ×3] → 0.8s (no rest)
```

### 11.2 Available Boss Assets (Unused — Future Expansion)

Three additional boss character sets exist in `Resources/industrial-zone/characters/bosses/`:

| Boss | Folder | Sprites | Potential Role |
|------|--------|---------|----------------|
| **Golf Cart Soldier** | bosses/GolfCartSoldier/ | 11 animations | Level 3 — Vehicle-based boss |
| **Green Mech** | bosses/GreenMech/ | 10 animations | Level 3 — Heavy mech with dual cannons |
| **Rugby Guy** | bosses/RugbyGuy/ | 6 animations | Level 3 — Charge + tackle melee boss |

---

## 12. Checkpoint & Save System

### 12.1 Checkpoint Placement

Each level has **4 equally-spaced checkpoints** plus the start and end points:

```
  LEVEL 1 (16,000px wide)
  ╠════════╬════════╬════════╬════════╬════════╣
  START    CP-1     CP-2     CP-3     CP-4     END/BOSS
  x=0     x=3200   x=6400   x=9600   x=12800  x=15000

  LEVEL 2 (28,800px wide)
  ╠════════╬════════╬════════╬════════╬════════╣
  START    CP-1     CP-2     CP-3     CP-4     END/BOSS
  x=0     x=5760   x=11520  x=17280  x=23040  x=27500
```

### 12.2 Checkpoint Machine

Each checkpoint is represented by the **Portal animated object** (4 frames, red chevron gate):

```
  Checkpoint States:
  
  ┌─────────────┐          ┌─────────────┐          ┌─────────────┐
  │  INACTIVE   │  player  │  ACTIVATING │ save     │   ACTIVE    │
  │             │  enters  │             │ complete │             │
  │  ░░░░░░░░  │ ────────►│  ▓░▓░▓░▓░  │─────────►│  ▓▓▓▓▓▓▓▓  │
  │  (dim red)  │  E key   │  (flashing) │          │  (solid red)│
  │             │          │  1.5s anim  │          │             │
  └─────────────┘          └─────────────┘          └─────────────┘
                                                     │
                                                     │ Player death
                                                     ▼
                                               ┌─────────────┐
                                               │  RESPAWN    │
                                               │  here       │
                                               │  HP = 100%  │
                                               │  Enemies:   │
                                               │  section    │
                                               │  reset only │
                                               └─────────────┘
```

### 12.3 Checkpoint Data Saved

| Field | Description |
|-------|-------------|
| `checkpointX`, `checkpointY` | World position |
| `currentLevel` | Level 1 or 2 |
| `score` | Current score at checkpoint |
| `elapsedTime` | Timer value |
| `weaponEquipped` | Currently held weapon type, or null |
| `ammoCount` | Remaining ammo |
| `enemiesKilled` | # of enemies defeated so far |

---

## 13. Reward & Collectible System

### 13.1 Collectible Types

```
  COLLECTIBLE HIERARCHY
  ═════════════════════
                        ┌───────────────┐
                        │   REWARDS     │
                        └───────┬───────┘
               ┌────────────┬───┴───┬────────────┐
               ▼            ▼       ▼            ▼
         ┌──────────┐ ┌─────────┐ ┌─────────┐ ┌──────────┐
         │  CASH    │ │  CHEST  │ │  CARD   │ │ PORTAL   │
         │ (money)  │ │ (loot)  │ │(collect)│ │(teleport)│
         └──────────┘ └─────────┘ └─────────┘ └──────────┘
```

| Collectible | Sprite | Frames | Animation | Points | Effect |
|-------------|--------|--------|-----------|--------|--------|
| **Money (Cash)** | Anim_Collectible_Money | 6 | Loop 80ms | +25 | Score boost |
| **Card** | Anim_Collectible_Card | 6 | Loop 80ms | +50 | Rare score item |
| **Chest** | Anim_Interactive_Chest_OpenAndClose | 8 | Once 100ms | +100 | Opens to reveal items + VFX |
| **Screen (Terminal)** | Anim_Interactive_Screen | 4 | Once 80ms | — | Displays hint/lore |
| **Portal** | Anim_Transition_PortalActive | 4 | Loop 100ms | — | Level exit / teleporter |

### 13.2 Loot Drop Table

When an enemy is defeated:

```
  ENEMY DEATH
      │
      ├── 60% chance: Drop MONEY (1–3 stacks)
      │     └── Each stack: +25 points
      │
      ├── 25% chance: Drop CARD
      │     └── +50 points
      │
      ├── 10% chance: Drop WEAPON
      │     └── Random gun from available set
      │
      └──  5% chance: Drop HEALTH PACK
            └── Restore 30 HP
```

When a CHEST is opened:

```
  CHEST OPENED
      │
      ├── Always: 3× MONEY stacks (+75 points total)
      │
      ├── 50% chance: 1× CARD (+50 points)
      │
      ├── 30% chance: 1× WEAPON (upgraded type)
      │
      └── 20% chance: 1× HEALTH PACK (+30 HP)
```

### 13.3 Score System

| Action | Points |
|--------|--------|
| Kill drone (UFO) | +60 |
| Kill drone (JET) | +70 |
| Kill drone (HOVER) | +50 |
| Kill BOSS_TANK | +100 |
| Kill BOSS_KNIGHT | +120 |
| Kill BOSS_WARRIOR | +150 |
| Collect MONEY | +25 |
| Collect CARD | +50 |
| Open CHEST | +100 |
| Checkpoint reached | +200 |
| Level complete bonus | +1000 |
| Time bonus (<3 min) | +500 |

---

## 14. HUD & UI Rendering

### 14.1 In-Game HUD Layout

```
┌──────────────────────────────────────────────────────────────────────────┐
│ ┌──────────────────┐   $: 1,250  🃏: 2/3    SCORE: 12450   ┌─────────┐  │
│ │ ❤ HP BAR         │   [cash counter] [card counter]        │ ⏱ 02:34 │  │
│ │ ██████████░░░░░░ │                                        │         │  │
│ │ 78 / 100         │            LEVEL 1                     │ ☠ 8/13  │  │
│ └──────────────────┘                                        └─────────┘  │
│ ┌──────────────────┐                                                     │
│ │ ⚡ ENERGY BAR    │                                                     │
│ │ ████████████░░░░ │                                                     │
│ └──────────────────┘                                                     │
│                                                                          │
│                                                                          │
│                          GAMEPLAY AREA                                   │
│                                                                          │
│                                                                          │
│                                                                          │
│                                                                          │
│                                                                          │
│ ┌──────────────────┐    ┌──────────────────────────────────────────────┐ │
│ │ [E] INTERACT     │    │  WEAPON INVENTORY BAR             (slot key) │ │
│ │ (when near item) │    │ ┌──────────┬──────────┬──────────┬──────────┐│ │
│ └──────────────────┘    │ │  [1] 🔫  │  [2] ---  │  [3] --- │  [4] ---││ │
│ ┌──────────────────┐    │ │ Pistol A │  empty   │  empty  │  empty  ││ │
│ │ Objective:       │    │ │  8 / 12  │   ---    │   ---   │   ---   ││ │
│ │ DEFEAT ALL       │    │ └──────────┴──────────┴──────────┴──────────┘│ │
│ │ ENEMIES          │    └──────────────────────────────────────────────┘ │
│ └──────────────────┘                                                     │
└──────────────────────────────────────────────────────────────────────────┘
```

**HUD Element Map:**

| Element | Position | Asset | Value Source |
|---------|----------|-------|--------------|
| Health bar | Top-left | `gui/2 Bars/` (7 fill levels) | `player.hp / player.maxHp` |
| Energy bar | Below health | `gui/2 Bars/` | `player.energy / player.maxEnergy` |
| Cash counter (`$`) | Top-centre-left | `GUI_Icon_*` + digit sprites | `player.cashCollected` |
| Card counter (`🃏`) | Next to cash | Card icon + `N/maxCards` text | `player.cardsCollected` |
| Score | Top-centre | Digit sprites from `gui/7 Numbers/` | `score` |
| Level name | Centre-top | Font images `gui/10 Font/` | `activeLevel.getLevelName()` |
| Timer | Top-right | Digit sprites | `elapsedTimeSeconds` formatted MM:SS |
| Enemy counter | Below timer | Skull icon + `killed/total` | `enemiesKilled / totalEnemies` |
| **Weapon inventory bar** | **Bottom-right** | **4 slot panels + gun thumbnails** | **inventory[0..3]** |
| **Slot ammo** | **Below each slot** | Digit sprites | `slot.ammo / slot.maxAmmo` |
| **Active slot border** | **Slot highlight** | Golden glow border 3px | `activeSlot == slotIndex` |
| E-key prompt | Bottom-left | `KeyBoard_E.png` | Shown when `nearestInteractable != null` |
| Objective box | Bottom-left (below E-prompt) | Panel frame + font | `activeLevel.getCurrentObjective()` |

### 14.1a Cash Counter

The **cash counter** (`$: N`) shows the total cash (Money collectibles) picked up in the current session:

```
  Icon:   coin/money icon from gui/3 Icons/Icons/
  Value:  player.cashCollected (incremented by +25 per MONEY pickup)
  Format: "$: 1,250" — comma-formatted with digit sprites

  Flash effect: when cash increases, counter flashes white for 0.3 s
```

### 14.1b Card Counter

The **card counter** (`🃏: N/3`) tracks cards collected and unlocks a reward at the threshold:

```
  Icon:   card sprite (small, 16×16, from Card animation frame 0)
  Format: "🃏: 2 / 3"  (collected / threshold)

  Card Reward Logic:
    L1 threshold = 3 cards → "DASH UNLOCKED!" banner + SHIFT dash enabled
    L2 threshold = 5 cards → "ENERGY BLAST UNLOCKED!" banner

  Visual:
    Each card slot shown as a small card icon:
    ■ ■ □   (2 collected, 1 remaining — hollow for uncollected)

  Flash effect: card icon glows cyan for 0.5 s when a card is collected
  Banner:       full-width animated banner appears for 2 s on threshold reached
```

### 14.1c Weapon Inventory Bar

The 4-slot weapon bar is drawn at the **bottom-right** of the screen. See §5.7 for full slot state logic.

```
  Weapon slot rendering order:
  1. Draw dark panel background (from gui/3 Icons/ panel sprite)
  2. Draw gun thumbnail sprite (scale to fit 48×48 within slot)
  3. If activeSlot == this slot → draw golden glow border (3px, alpha pulse)
  4. Draw ammo text below thumbnail using digit sprites from gui/7 Numbers/
  5. Draw key label ([1]/[2]/[3]/[4]) top-left corner of each slot

  Slot positions (screen-relative, SCREEN_W = 1500, SCREEN_H = 860):
  Slot 1: x = SCREEN_W - 328, y = SCREEN_H - 82
  Slot 2: x = SCREEN_W - 244, y = SCREEN_H - 82
  Slot 3: x = SCREEN_W - 160, y = SCREEN_H - 82
  Slot 4: x = SCREEN_W -  76, y = SCREEN_H - 82

  Each slot: 68px wide × 72px tall
  Inner gun thumbnail: 48×48px centred in slot
  Ammo text: 6px below inner area, centred, white digit sprites
```

### 14.2 Health/Energy Bar System

From `gui/2 Bars/` — pre-rendered gradient bars in multiple fill states:

```
  BAR RENDERING:
  
  Frame (dark outline):  ┌─────────────────────────┐
                         │ ▓▓▓▓▓▓▓▓▓▓▓▓▓░░░░░░░░ │ ← gradient fill
                         └─────────────────────────┘
  
  hudBars[0] = 100% full (bright red-orange)
  hudBars[1] = ~83%
  hudBars[2] = ~67%
  hudBars[3] = ~50%
  hudBars[4] = ~33%
  hudBars[5] = ~17%
  hudBars[6] = 0% empty (dark frame only)
  
  Selected by: int barIdx = 6 - (int)(hp / 100f * 6);
```

### 14.3 Number Rendering

Score, timer, and other numeric displays use the pre-rendered digit sprites from `gui/7 Numbers/`:

```
  digitImgs[0..9] = individual digit PNGs
  
  To render "12450":
  for each char c in "12450":
      draw digitImgs[c - '0'] at (x, y)
      x += digitWidth + spacing
```

### 14.4 Icon Usage

40 icons from `gui/3 Icons/Icons/` for various HUD elements:

| Icon | File | Usage |
|------|------|-------|
| Heart | `GUI_Icon_Heart_Love_22.png` | HP indicator label |
| Shield | `GUI_Icon_Shield_Defense_23.png` | Armour/energy indicator |
| Sword | `GUI_Icon_Sword_Attack_24.png` | Melee damage stat |
| Star | `GUI_Icon_Star_Favorite_21.png` | Score/achievement |
| Settings Gear | `GUI_Icon_Settings_Gear_10.png` | Settings screen header |
| Play | `GUI_Icon_Play_Start_33.png` | Resume from pause |
| Pause | `GUI_Icon_Pause_Stop_34.png` | Pause indicator |
| Home | `GUI_Icon_Home_House_12.png` | Main menu button |
| Volume | `GUI_Icon_Volume_Sound_37.png` | Audio settings |
| Mute | `GUI_Icon_Mute_Silent_38.png` | Audio muted state |
| Lock/Unlock | `GUI_Icon_Lock_16/17.png` | Locked levels |
| Save | `GUI_Icon_Save_Floppy_19.png` | Checkpoint saved |
| Refresh | `GUI_Icon_Refresh_Reload_32.png` | Retry button |
| Search | `GUI_Icon_Search_Magnifier_11.png` | Level select |
| Arrow keys | `GUI_Icon_Arrow_*_05-08.png` | Navigation hints |
| Plus/Minus | `GUI_Icon_Plus_01/Minus_02.png` | Volume adjust |
| Check/Cross | `GUI_Icon_Check_03/Cross_04.png` | Confirm/cancel |
| Info | `GUI_Icon_Info_Question_14.png` | Tooltip/help |
| Alert | `GUI_Icon_Alert_Exclamation_15.png` | Warning indicator |

---

## 15. Audio System

### 15.1 Audio File Inventory

**Music/WAV Tracks (40 files):**

| # | File | Type | Usage |
|---|------|------|-------|
| 1 | `Main_theme_Chinese_Street.wav` | Theme | Main menu background |
| 2 | `Alternative_theme_Chinese_Street.wav` | Theme | Level 2 background |
| 3 | `Battle_theme_Chinese_Street.wav` | Theme | Boss encounter |
| 4 | `Calm_theme_Chinese_Street.wav` | Theme | Credits / victory |
| 5 | `Stealthy_theme_loopable.wav` | Theme | Level 1 background |
| 6 | `Melody_of_attraction_loopable.wav` | Theme | Character select |
| 7 | `Melody_of_the_win.wav` | Jingle | Level complete |
| 8 | `Explosion.wav` | SFX | Enemy death, boss hit |
| 9 | `Bomb_drop.wav` | SFX | Jet drone attack |
| 10 | `Flying_platform_attak_1.wav` | SFX | Hover drone attack |
| 11 | `Flying_platform_attak_2.wav` | SFX | Hover drone attack alt |
| 12 | `Hovering_robot_sting.wav` | SFX | Drone alert/detect |
| 13 | `Hovering_robot_walk_loopable.wav` | SFX | Drone patrol loop |
| 14 | `Karateka_attack.wav` | SFX | Player melee attack |
| 15 | `Laser_sword_1.wav` | SFX | Boss Knight slash |
| 16 | `Laser_sword_2.wav` | SFX | Boss Knight heavy slash |
| 17 | `Samurai_death.wav` | SFX | Player / enemy death |
| 18 | `Samurai_footstep_1.wav` | SFX | Player walk/run |
| 19 | `Samurai_footstep_2.wav` | SFX | Player walk/run alt |
| 20 | `Click_digital_1.wav` | SFX | Menu click |
| 21 | `Click_digital_2.wav` | SFX | Menu confirm |
| 22 | `Unlocked_chest.wav` | SFX | Chest opened |
| 23 | `Portal_1.wav` | SFX | Portal activate |
| 24 | `Portal_2.wav` | SFX | Portal alternate |
| 25 | `Portal_closing.wav` | SFX | Portal deactivate |
| 26 | `Portal_moving.wav` | SFX | Portal transition |
| 27 | `Bell_on_the_door.wav` | SFX | Shop/checkpoint bell |
| 28 | `Creaking_wooden_door.wav` | SFX | Door environment |
| 29 | `Door_with_password.wav` | SFX | Locked door |
| 30 | `Elevator_motor.wav` | SFX | Moving platform |
| 31 | `Lift_mechanism.wav` | SFX | Elevator/lift |
| 32 | `Push_slide_door.wav` | SFX | Sliding panel |
| 33 | `Roller_doors.wav` | SFX | Industrial gate |
| 34 | `Sliding_doors.wav` | SFX | Automatic door |
| 35 | `Zipline_loopable.wav` | SFX | Zipline ride |

**MIDI Tracks (5 files):**

| # | File | Usage |
|---|------|-------|
| 1 | `Track 1.mid` | Alternative background music |
| 2 | `Track 2.mid` | Alternative background music |
| 3 | `Track 3.mid` | Alternative background music |
| 4 | `Track 4.mid` | Alternative background music |
| 5 | `Track 5.mid` | Alternative background music |

### 15.2 Audio Trigger Map

```
  GAME EVENT                  AUDIO CUE
  ══════════                  ═════════
  
  ┌─ MENU SCREENS ───────────────────────────────────────┐
  │ Main Menu loaded     → Main_theme (loop)             │
  │ Menu button hover    → Click_digital_1               │
  │ Menu button click    → Click_digital_2               │
  │ Character select     → Melody_of_attraction (loop)   │
  └──────────────────────────────────────────────────────┘

  ┌─ GAMEPLAY ───────────────────────────────────────────┐
  │ Level 1 start       → Stealthy_theme (loop)          │
  │ Level 2 start       → Alternative_theme (loop)       │
  │ Boss encounter      → Battle_theme (loop)            │
  │ Level complete      → Melody_of_the_win (once)       │
  │ Credits / victory   → Calm_theme (loop)              │
  └──────────────────────────────────────────────────────┘

  ┌─ PLAYER ─────────────────────────────────────────────┐
  │ Walk/Run            → Samurai_footstep_1/2 (alt)     │
  │ Melee attack        → Karateka_attack                │
  │ Gun fire            → (procedural synth or SFX)      │
  │ Take damage         → Samurai_death (short clip)     │
  │ Death               → Samurai_death (full)           │
  └──────────────────────────────────────────────────────┘

  ┌─ ENEMY ──────────────────────────────────────────────┐
  │ Drone detects player → Hovering_robot_sting          │
  │ Drone patrol         → Hovering_robot_walk (loop)    │
  │ Drone attack         → Flying_platform_attak_1/2     │
  │ Drone bomb           → Bomb_drop                     │
  │ Enemy death          → Explosion                     │
  │ Boss Knight slash    → Laser_sword_1/2 (alt)         │
  └──────────────────────────────────────────────────────┘

  ┌─ ENVIRONMENT ────────────────────────────────────────┐
  │ Chest opened         → Unlocked_chest                │
  │ Portal activate      → Portal_1 or Portal_2          │
  │ Portal transition    → Portal_moving                 │
  │ Portal close         → Portal_closing                │
  │ Door interaction     → Door_with_password /           │
  │                        Push_slide_door / Sliding_doors│
  │ Elevator             → Elevator_motor / Lift_mechanism│
  │ Checkpoint bell      → Bell_on_the_door              │
  │ Zipline              → Zipline_loopable              │
  └──────────────────────────────────────────────────────┘
```

### 15.3 Audio Manager Architecture

```
  ┌─────────────────────────────┐
  │       AudioManager          │
  │                             │
  │  ┌─────────────────────┐    │
  │  │ MUSIC CHANNEL       │    │   Volume: settingsMusicVol (0.0–1.0)
  │  │ • 1 track at a time │    │   Mutable: settingsMusicOn
  │  │ • Crossfade 0.5s    │    │
  │  │ • Loop or one-shot  │    │
  │  └─────────────────────┘    │
  │                             │
  │  ┌─────────────────────┐    │
  │  │ SFX CHANNELS (×8)   │    │   Volume: settingsSfxVol (0.0–1.0)
  │  │ • Polyphonic mixing │    │   Mutable: settingsSfxOn
  │  │ • Priority queue    │    │
  │  │ • Distance falloff  │    │
  │  └─────────────────────┘    │
  │                             │
  │  Methods:                   │
  │  • playMusic(track, loop)   │
  │  • playSfx(clip)            │
  │  • stopMusic()              │
  │  • setMusicVolume(v)        │
  │  • setSfxVolume(v)          │
  │  • initialize()             │
  └─────────────────────────────┘
```

---

## 16. VFX & Particle System

### 16.1 VFX Categories

| # | Category | Folder | Count | Description |
|---|----------|--------|-------|-------------|
| 1 | **Smoke** | vfx/1 Smoke/ | 18 sprites | Grey-white cloud puffs, used for enemy death, explosions, dash trail |
| 2 | **Blood** | vfx/2 Blood/ | 8 sprites | Red splatter particles, used for damage hits on organic enemies |
| 3 | **Sparks** | vfx/3 Sparks/ | 8 sprites | Orange-yellow electrical sparks, used for metal-on-metal, drone hits |
| 4 | **Particles** | vfx/4 Particles/ | 12 sprites | Generic particle sprites, used for ambient effects, collectible pickup |
| 5 | **Other** | vfx/5 Other/ | 12 sprites | Misc effects: shockwave, energy pulse, glow orbs |
| 6 | **Extra** | vfx/6 Extra/ | 24 sprites | Additional effects: fire, electricity, ice, poison |

**Total: 82 VFX sprites**

### 16.2 VFX Trigger Map

```
  EVENT                         VFX EFFECT
  ═════                         ══════════
  
  Player dash               →  Smoke trail (3–5 puffs behind player)
  Player melee hit (enemy)  →  Sparks (2–4 at contact point)
  Player gun fire           →  Muzzle flash (from Shoot_effects) + Smoke (1 puff)
  Player hurt               →  Blood (2–3 splatter from player)
  Player death              →  Blood (5–7 splatter) + Smoke (cloud)
  
  Drone hit (by bullet)     →  Sparks (3–5) + Smoke (1 puff)
  Drone death               →  Smoke (large cloud, 14 frames) + Sparks (burst)
  Boss damaged              →  Sparks (5–8) + Smoke + screen shake
  Boss death                →  Explosion: Smoke (full 18 frames) + Sparks + Particles
  
  Chest open                →  Particles (golden sparkle burst)
  Collectible pickup        →  Particles (float upward, 3–5)
  Portal active             →  Particles (swirling + glow orbs)
  Checkpoint save           →  Other (energy pulse ring)
  
  Bullet impact (wall)      →  Sparks (2–3) + Smoke (tiny puff)
  Bullet impact (enemy)     →  Blood (1–2) + Sparks (1–2)
```

### 16.3 VFX Particle System Architecture

```
  ┌─────────────────────────────────────────────────┐
  │                VfxSystem                        │
  │                                                 │
  │  particles: List<VfxParticle>                   │
  │  ┌────────────────────────────────────────────┐ │
  │  │ VfxParticle                                │ │
  │  │  • x, y        (world position)            │ │
  │  │  • vx, vy      (velocity)                  │ │
  │  │  • lifetime     (seconds remaining)         │ │
  │  │  • alpha        (0.0–1.0, fades over life) │ │
  │  │  • scale        (size multiplier)           │ │
  │  │  • rotation     (degrees)                   │ │
  │  │  • spriteFrame  (current animation frame)   │ │
  │  │  • type         (SMOKE/BLOOD/SPARK/...)     │ │
  │  └────────────────────────────────────────────┘ │
  │                                                 │
  │  Methods:                                       │
  │  • spawn(type, x, y, count)                     │
  │  • update(deltaTime)          // physics + fade │
  │  • render(g, cameraX, cameraY) // draw all     │
  │  • clear()                                      │
  │                                                 │
  │  Per-type defaults:                             │
  │  SMOKE: vx=±20, vy=-40, life=0.8s, scale=1.5  │
  │  BLOOD: vx=±60, vy=-80, life=0.5s, scale=1.0  │
  │  SPARK: vx=±100, vy=±100, life=0.3s, scale=0.5│
  │  PARTICLE: vx=±30, vy=-50, life=1.0s, scale=0.8│
  └─────────────────────────────────────────────────┘
```

---

## 17. Physics & Collision

### 17.1 Physics Constants

| Constant | Value | Description |
|----------|-------|-------------|
| GRAVITY | 600 px/s² | Downward acceleration |
| MAX_FALL_SPEED | 500 px/s | Terminal velocity |
| JUMP_VELOCITY | -350 px/s | Initial upward push |
| DOUBLE_JUMP_VEL | -300 px/s | Air jump push |
| GROUND_FRICTION | 0.85 | Horizontal deceleration |
| AIR_FRICTION | 0.95 | Less friction in air |

### 17.2 Collision Detection

```
  AABB (Axis-Aligned Bounding Box) Collision
  ═══════════════════════════════════════════
  
  Player hitbox:  48 × 48 px (centered on sprite frame)
  Drone hitbox:   varies by type (32×32 to 64×64)
  Boss hitbox:    64×64 to 96×96
  Bullet hitbox:  8×4 px
  
  ┌─────────────────────────────────────────┐
  │  Platform Collision (per frame):        │
  │                                         │
  │  for each platform in CURRENT_PLATFORMS:│
  │    if player.bounds overlaps platform:  │
  │      resolve by minimum overlap axis    │
  │                                         │
  │      ┌──────────┐                       │
  │      │  PLAYER  │                       │
  │      │          │ ← overlap top of      │
  │      └────┬─────┘   platform = landing  │
  │     ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓                  │
  │     PLATFORM                            │
  │                                         │
  │  Resolve:                               │
  │  • From above → land on top, vy = 0     │
  │  • From below → bonk head, vy = 0       │
  │  • From side  → stop horizontal, vx = 0 │
  └─────────────────────────────────────────┘
  
  ┌─────────────────────────────────────────┐
  │  Entity-Entity Collision:               │
  │                                         │
  │  Bullet → Enemy:                        │
  │    if bullet.bounds overlaps enemy:     │
  │      enemy.takeDamage(gun.damage)       │
  │      spawn VFX (sparks + blood)         │
  │      destroy bullet                     │
  │                                         │
  │  Enemy → Player:                        │
  │    if enemy.attackHitbox overlaps player:│
  │      player.takeDamage(enemy.dmg)       │
  │      spawn VFX (blood)                  │
  │      player enters HURT state           │
  │                                         │
  │  Player → Collectible:                  │
  │    if player.bounds overlaps pickup:    │
  │      collect item, add score            │
  │      spawn VFX (particles)             │
  │      play SFX                           │
  └─────────────────────────────────────────┘
```

### 17.3 Projectile Physics

```
  Bullet trajectory:
  
  ┌──── Player ────┐
  │                │     ● ─── ● ─── ● ─── ● ─── ● ─── ● ──► (despawn at range)
  │     ☻ ──►      │     │                                │
  │   muzzle       │   bullet                          range limit
  └────────────────┘   spawn
  
  • Bullets travel in a straight line (no gravity)
  • Speed: 600 px/s (all gun types)
  • Direction: player facing direction (left/right)
  • Despawn: on hit OR at max range for gun type
  • No friendly fire (player bullets only hurt enemies, enemy projectiles only hurt player)
```

---

## 18. Camera System

### 18.1 Camera Follow

```
  ┌──────────────────────────────────────────────────┐
  │                                                  │
  │  Camera target: player position                  │
  │  Smoothing: lerp(current, target, 0.1)           │
  │                                                  │
  │  cameraX = lerp(cameraX, player.x - SCREEN_W/2, │
  │                 0.1f)                             │
  │  cameraY = 0 (fixed vertical — side-scroller)    │
  │                                                  │
  │  Clamping:                                       │
  │  cameraX = max(0, min(cameraX,                   │
  │                worldWidth - SCREEN_W))            │
  │                                                  │
  │  ┌──── SCREEN (1500×860) ────┐                   │
  │  │                           │                   │
  │  │         ☻ player          │                   │
  │  │      (center-ish)         │                   │
  │  │                           │                   │
  │  │◄── cameraX ──►           │                   │
  │  └───────────────────────────┘                   │
  │  ◄═══════════ worldWidth (16000 or 28800) ═══════►│
  └──────────────────────────────────────────────────┘
```

### 18.2 Camera Shake

Triggered by: boss hits, explosions, player heavy damage

```
  During shake:
    shakeIntensity = initial (e.g. 8px)
    shakeDecay     = 0.9 per frame
    
    offsetX = random(-intensity, +intensity)
    offsetY = random(-intensity, +intensity)
    
    render at (cameraX + offsetX, cameraY + offsetY)
    
    intensity *= decay  // fades to 0 over ~20 frames
```

---

## 19. Asset Architecture (Java Classes)

### 19.1 Package Structure

All 1174 assets are catalogued in a hierarchical Java package system:

```
src/assets/
├── SpriteAsset.java          ← Base descriptor: auto frame calc, loop/once/single
├── AudioAsset.java           ← Audio descriptor: music/sfx factory methods
│
├── animated/                 ← Level animated objects
│   ├── Level1AnimatedAssets.java     (12 constants)
│   └── Level2AnimatedAssets.java     (4 constants)
│
├── audio/                    ← Music & sound effects
│   ├── MusicAssets.java              (35 constants)
│   └── SfxAssets.java                (35 constants)
│
├── backgrounds/              ← Parallax background layers
│   ├── Level1BackgroundAssets.java   (6 constants)
│   ├── Level2BackgroundAssets.java   (1 constant)
│   ├── Level2BackgroundDayAssets.java   (5 constants)
│   └── Level2BackgroundNightAssets.java (5 constants)
│
├── bosses/                   ← Boss character spritesheets
│   ├── GolfCartSoldierAssets.java    (11 constants)
│   ├── GreenMechAssets.java          (10 constants)
│   └── RugbyGuyAssets.java           (6 constants)
│
├── characters/               ← Playable character spritesheets
│   ├── BikerAssets.java              (24 constants)
│   ├── CyborgAssets.java             (24 constants)
│   └── PunkAssets.java               (24 constants)
│
├── enemies/                  ← Drone & antagonist spritesheets
│   ├── Drone1Assets.java            (5 constants)
│   ├── Drone2Assets.java            (2 constants)
│   ├── Drone3Assets.java            (7 constants)
│   ├── Drone4Assets.java            (4 constants)
│   ├── Drone5Assets.java            (3 constants)
│   ├── Drone5bAssets.java           (3 constants)
│   ├── Drone6Assets.java            (4 constants)
│   ├── DroneCharAnimAssets.java     (3 constants)
│   ├── Antagonist1Assets.java       (13 constants)
│   ├── Antagonist2Assets.java       (10 constants)
│   └── Antagonist3Assets.java       (11 constants)
│
├── gui/                      ← GUI frames, bars, icons, cursors, fonts
│   ├── FrameAssets.java              (82 constants)
│   ├── BarAssets.java                (21 constants)
│   ├── IconAssets.java               (60 constants)
│   ├── PaletteAssets.java            (1 constant)
│   ├── LogoAssets.java               (3 constants)
│   ├── ButtonAssets.java             (10 constants)
│   ├── NumberAssets.java             (17 constants)
│   ├── CursorAssets.java            (4 constants)
│   ├── OtherGuiAssets.java          (28 constants)
│   └── FontAssets.java              (64 constants)
│
├── input/                    ← Reference key images
│   ├── KeyboardKeyAssets.java        (66 constants)
│   └── MouseKeyAssets.java           (21 constants)
│
├── objects/                  ← Level objects & decorations
│   ├── Level1ObjectAssets.java       (44 constants)
│   ├── Level2DecorationAssets.java   (27 constants)
│   ├── Level2PowerLineAssets.java    (9 constants)
│   └── Level2TubeAssets.java         (11 constants)
│
├── tiles/                    ← Structural tile sprites
│   ├── Level1TileAssets.java         (81 constants)
│   └── Level2TileAssets.java         (64 constants)
│
├── vfx/                      ← Visual effects sprites
│   ├── SmokeVfxAssets.java           (18 constants)
│   ├── BloodVfxAssets.java           (8 constants)
│   ├── SparkVfxAssets.java           (8 constants)
│   ├── ParticleVfxAssets.java        (12 constants)
│   ├── OtherVfxAssets.java           (12 constants)
│   └── ExtraVfxAssets.java           (24 constants)
│
└── weapons/                  ← Weapon system sprites
    ├── WeaponSet1CharAssets.java     (30 constants)
    ├── WeaponSet1GunAssets.java      (20 constants)
    ├── WeaponSet1HandAssets.java     (30 constants)
    ├── WeaponSet1ShootAssets.java    (10 constants)
    ├── WeaponSet1BulletAssets.java   (13 constants)
    ├── WeaponSet2CharAssets.java     (30 constants)
    ├── WeaponSet2GunAssets.java      (20 constants)
    ├── WeaponSet2HandAssets.java     (30 constants)
    ├── WeaponSet2ShootAssets.java    (10 constants)
    └── WeaponSet2BulletAssets.java   (19 constants)
```

### 19.2 SpriteAsset Usage Example

```java
import assets.characters.BikerAssets;
import assets.SpriteAsset;

// Get a sprite asset descriptor
SpriteAsset idle = BikerAssets.PLAYER_BIKER_IDLE_STANDINGBREATHLOOP_DEFAULTIDLE;

// Load the spritesheet
String path = idle.getFullPath();              // "Resources/.../01_Player_Biker_Idle_..."
BufferedImage sheet = ImageIO.read(new File(path));

// Extract frames automatically
int frameCount = idle.getFrameCount();          // 4 (auto-calculated: 192/48)
int frameW     = idle.getFrameWidth();          // 48
int frameH     = idle.getHeight();              // 48
boolean loops  = idle.isLooping();              // true

// Build animation
Animation anim = new Animation();
for (int i = 0; i < frameCount; i++) {
    BufferedImage frame = sheet.getSubimage(i * frameW, 0, frameW, frameH);
    anim.addFrame(frame, idle.getSuggestedDurationMs());
}
```

### 19.3 Audio Asset Usage Example

```java
import assets.audio.MusicAssets;
import assets.audio.SfxAssets;
import assets.AudioAsset;

// Play background music
AudioAsset bgMusic = MusicAssets.MAIN_THEME_CHINESE_STREET;
audioManager.playMusic(bgMusic.getFullPath(), true); // loop

// Play sound effect
AudioAsset explosionSfx = SfxAssets.EXPLOSION;
audioManager.playSfx(explosionSfx.getFullPath());
```

---

## 20. Full Asset Inventory Summary

### 20.1 Category Totals

| Category | Subcategories | Asset Count | Format |
|----------|---------------|-------------|--------|
| **Tiles** | Level 1, Level 2 | 145 | PNG (32×32 each) |
| **Backgrounds** | L1 (5), L2 Day (5), L2 Night (5), L2 generic (1) | 16 | PNG (wide) |
| **Characters** | Biker (24), Cyborg (24), Punk (24) | 72 | PNG spritesheets |
| **Enemies** | Drones ×7 folders, Antagonists ×3 | 65 | PNG spritesheets |
| **Bosses** | GolfCartSoldier, GreenMech, RugbyGuy | 27 | PNG spritesheets |
| **Weapons** | 2 sets × 5 subcategories | 212 | PNG |
| **GUI** | Frames, Bars, Icons, etc. | 290 | PNG |
| **VFX** | Smoke, Blood, Sparks, Particles, Other, Extra | 82 | PNG |
| **Audio** | Music WAV (35), SFX (35), MIDI (5) | 75 | WAV, MIDI |
| **Animated** | Level 1 objects, Level 2 objects | 16 | PNG spritesheets |
| **Objects** | Level 1 (44), Level 2 (47) | 91 | PNG |
| **Input** | Keyboard (66), Mouse (21) | 87 | PNG |
| **TOTAL** | | **~1178** | |

### 20.2 Directory Structure Reference

```
Resources/industrial-zone/
├── 1 Tiles/
│   ├── Industrial_zone_level_1/
│   │   ├── 1 Tiles/           (81 tiles)
│   │   ├── 2 Background_level_1/ (5+1 backgrounds)
│   │   └── 3 Objects/         (44 objects + 12 animated)
│   └── power-station-level-2/
│       ├── 2 Background_level_2/
│       │   ├── Day/           (5 backgrounds)
│       │   └── Night/         (5 backgrounds)
│       ├── 3 Tiles_level_2/   (64 tiles)
│       ├── 4 Decoration_level_2/ (27 objects)
│       ├── 5 Tubes_level_2/   (11 tubes)
│       ├── 6 PowerLines_level_2/ (9 power lines)
│       └── 7 Objects_level_2/ (4 animated)
├── audio/
│   ├── music_midi/            (5 MIDI tracks)
│   ├── music_wav/             (35 WAV tracks)
│   └── sfx/                   (35 SFX clips)
├── characters/
│   ├── bosses/
│   │   ├── GolfCartSoldier/   (11 spritesheets)
│   │   ├── GreenMech/         (10 spritesheets)
│   │   └── RugbyGuy/          (6 spritesheets)
│   ├── enemies/
│   │   ├── drones/
│   │   │   ├── 1/ (UFO Saucer, 5 anims)
│   │   │   ├── 2/ (Jet Drone, 2 anims)
│   │   │   ├── 3/ (7 anims)
│   │   │   ├── 4/ (4 anims)
│   │   │   ├── 5/ (3 anims)
│   │   │   ├── 5_2/ (3 anims)
│   │   │   ├── 6/ (Hover Platform, 4 anims)
│   │   │   └── Character animations/ (3 shared anims)
│   │   └── sci-fi-antagonists/
│   │       ├── 1/ (Combat Tank, 13 anims)
│   │       ├── 2/ (Armoured Knight, 10 anims)
│   │       └── 3/ (Winged Warrior, 11 anims)
│   └── player/
│       ├── biker/             (24 spritesheets)
│       ├── cyborg/            (24 spritesheets)
│       └── punk/              (24 spritesheets)
├── gui/
│   ├── 1 Frames/             (82 frame pieces)
│   ├── 2 Bars/               (21 bar sprites)
│   ├── 3 Icons/
│   │   ├── Buttons2/         (~20 button icons)
│   │   └── Icons/            (40 utility icons)
│   ├── 4 Palette/            (1 color palette)
│   ├── 5 Logo/               (3 logo variants)
│   ├── 6 Buttons/            (10 button sprites)
│   ├── 7 Numbers/            (17 digit sprites)
│   ├── 8 Cursors/            (4 cursor sprites)
│   ├── 9 Other/
│   │   ├── 1 Decor/          (decorative elements)
│   │   └── 2 Skill icons/    (skill/ability icons)
│   └── 10 Font/
│       └── images/            (64 font character images)
├── KeyBoard_Keys/             (66 key cap sprites)
├── Mouse_keys/                (21 mouse sprites)
├── vfx/
│   ├── 1 Smoke/              (18 smoke frames)
│   ├── 2 Blood/              (8 blood frames)
│   ├── 3 Sparks/             (8 spark frames)
│   ├── 4 Particles/          (12 particle frames)
│   ├── 5 Other/              (12 misc effects)
│   └── 6 Extra/              (24 extra effects)
└── weapons/
    ├── 1/ (Weapon Set 1)
    │   ├── 1 Characters/     (Biker/Punk/Cyborg × 10 each)
    │   ├── 2 Guns/           (20 gun sprites)
    │   ├── 3 Hands/          (Biker/Punk/Cyborg × 10 each)
    │   ├── 4 Shoot_effects/  (10 muzzle flash sprites)
    │   └── 5 Bullets/        (13 bullet sprites)
    └── 2/ (Weapon Set 2)
        ├── 1 Characters/     (30 character poses)
        ├── 2 Guns/           (20 gun sprites)
        ├── 3 Hands/          (30 hand grips)
        ├── 4 Shoot_effects/  (10 muzzle flash sprites)
        └── 5 Bullets/        (19 bullet sprites)
```

---

## Appendix A: Rendering Pipeline (Per Frame)

```
  FRAME RENDER ORDER (back → front)
  ═════════════════════════════════

  1. Clear screen (black)
  
  2. Parallax backgrounds (5 layers, each at own scroll rate)
     └── parallaxGame1.render(g, cameraX) or parallaxGame2.render()
  
  3. Tile map (ground + platforms)
     └── Draw visible tiles in camera viewport only
  
  4. Collectibles & objects (behind player)
     └── Animated chests, money, cards, screens
  
  5. Enemies (drones + bosses)
     └── Current animation frame, health bars, AI debug overlay
  
  6. Player character
     └── Current animation + weapon overlay (if equipped)
  
  7. Projectiles (bullets, enemy projectiles)
     └── Small sprites moving at bullet speed
  
  8. VFX particles (smoke, blood, sparks — on top of everything)
     └── vfx.render(g, cameraX, cameraY)
  
  9. HUD overlay (fixed screen-space, not affected by camera)
     └── Health bar, energy bar, score, timer, weapon indicator
     └── Objective text, interaction prompts
  
  10. Pause / Game-Over / Menu overlay (when applicable)
      └── Darkened backdrop + panel + buttons
```

---

## Appendix B: Game Loop Timing

```
  ┌─────────────────────────────────────────────┐
  │           GAME LOOP (16ms Swing Timer)       │
  │                                              │
  │  1. deltaTime = currentTime - lastTime       │
  │                                              │
  │  2. INPUT PHASE:                             │
  │     • Poll keyboard state                    │
  │     • Poll mouse position                    │
  │                                              │
  │  3. UPDATE PHASE:                            │
  │     • Player physics (gravity, velocity)     │
  │     • Player-platform collision              │
  │     • Player animation state machine         │
  │     • Enemy AI (per enemy):                  │
  │       - State transition check               │
  │       - Movement / attack                    │
  │       - Animation advance                    │
  │     • Projectile movement + hit detection    │
  │     • VFX particle simulation                │
  │     • Camera follow + shake decay            │
  │     • Score / timer / objective check         │
  │                                              │
  │  4. RENDER PHASE:                            │
  │     • (See Appendix A render pipeline)        │
  │                                              │
  │  5. BUFFER SWAP:                             │
  │     • Double-buffered via GameCore            │
  │                                              │
  │  lastTime = currentTime                      │
  └─────────────────────────────────────────────┘
```

---

## Appendix C: Implementation Priority

| Priority | Feature | Status | Effort |
|----------|---------|--------|--------|
| P0 | Parallax backgrounds (Level 1) | ✅ Done | — |
| P0 | Tile rendering | ✅ Done | — |
| P0 | Player movement + animation | ✅ Done | — |
| P0 | Platform collision | ✅ Done | — |
| P0 | Basic enemy AI (patrol + chase) | ✅ Done | — |
| P0 | Shooting mechanic | ✅ Done | — |
| P0 | HUD (health, score, timer) | ✅ Done | — |
| P0 | Screen state machine (11 screens) | ✅ Done | — |
| P0 | Asset descriptor classes (1174) | ✅ Done | — |
| P0 | Level1 / Level2 / LevelData separation | ✅ Done | — |
| P1 | HUD — cash counter (live $ display) | 🔲 Planned | Low |
| P1 | HUD — card counter (🃏 N/threshold display) | 🔲 Planned | Low |
| P1 | H key heal — Idle2 anim + HP restore + cooldown | 🔲 Planned | Low |
| P1 | Animated objects — HorizontalSpritesheetLoader for all types | 🔲 Planned | Medium |
| P1 | Animated object physics (Hammer hitbox, Conveyor push, Platform carry) | 🔲 Planned | Medium |
| P1 | E-key interaction system (chest open, screen lore, transporter) | 🔲 Planned | Medium |
| P1 | H-Mover (horizontal transporter) — ride + E key | 🔲 Planned | Medium |
| P1 | V-Mover (vertical lift) — E key ascend/descend | 🔲 Planned | Medium |
| P1 | Drone Hang mechanic — stand below HOVER, E to grab/release | 🔲 Planned | High |
| P1 | Weapon pickup/throw/reload system | 🔲 Planned | High |
| P1 | Composite weapon sprite layering (char + hand + gun) | 🔲 Planned | Medium |
| P1 | Full audio integration (WAV SFX map) | 🔲 Planned | Medium |
| P1 | VFX system (all 82 effect sprites) | 🔲 Planned | Medium |
| P1 | Checkpoint system (4 per level, Portal interaction) | 🔲 Planned | Medium |
| P1 | Collectible/reward system (loot drops, chest open) | 🔲 Planned | Medium |
| P1 | Level 2 backgrounds — Day/Night variants + colour overlay | 🔲 Planned | Low |
| P1 | Controls screen — KeyBoard_Keys + Mouse_keys images, scrollbar | 🔲 Planned | Medium |
| P2 | Card threshold rewards (Dash unlock at 3, Energy blast at 5) | 🔲 Planned | Medium |
| P2 | Boss fight phases (multi-phase AI state machine) | 🔲 Planned | High |
| P2 | Enemy enhanced AI (alert state, shooting, cone detection) | 🔲 Planned | High |
| P2 | Expansion drones (types 3, 4, 5, 5b) | 🔲 Planned | Medium |
| P2 | Expansion bosses (GolfCart, Mech, Rugby) — Level 3 potential | 🔲 Planned | High |
| P2 | Camera shake system | 🔲 Planned | Low |
| P2 | Melee combo system (3-hit chain) | 🔲 Planned | Medium |
| P2 | Turret AI (Level 2 — rotate + fire projectile every 3 s) | 🔲 Planned | Medium |
| P3 | Double jump mechanic | 🔲 Planned | Low |
| P3 | Climb/hang/pull-up system (ledge grab) | 🔲 Planned | Medium |
| P3 | Emote animations (angry/happy/talk) | 🔲 Planned | Low |
| P3 | Font rendering (64 character images from gui/10 Font/) | 🔲 Planned | Medium |
| P3 | MIDI music playback | 🔲 Planned | Low |
| P3 | Inventory UI (weapon slots, item cycling) | 🔲 Planned | High |
| P3 | Narrative beat system (HUD objective text, story panels) | 🔲 Planned | Medium |

---

*Document generated from analysis of 1174 catalogued assets, Game.java (2087 lines), Enemy.java (~490 lines), and the full `Resources/industrial-zone/` directory tree.*

---

# ═══════════════════════════════════════════════════════════════════
# PHASE 2 — COMPREHENSIVE GAME OVERHAUL PLAN
# ═══════════════════════════════════════════════════════════════════
# Added: Phase 2 overhaul covering all critical fixes and new systems.
# This section supersedes any conflicting guidance in earlier sections.
# ═══════════════════════════════════════════════════════════════════

---

## 21 · TILE ADJACENCY RULES — IMAGE-BASED (CRITICAL)

> **RULE:** NEVER rely solely on tile filenames — always confirm adjacency
> by inspecting the **actual pixel edges** of each tile image.

### 21.1 Level 1 — Industrial Zone (81 tiles)

Tile directory: `Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/`

Tiles share a **dark purple / navy / slate-blue palette**. All edges must
colour-match their neighbours. The following adjacency families are derived
from inspecting the actual tile images:

```
┌──────────────────────────────────────────────────────────────────────────┐
│  FAMILY A — Flat-Top Floor Blocks (walkable surface row 3, platform    │
│             bodies rows 1-2)                                            │
│  Tiles: 01, 03, 16                                                      │
│  Visual: Flat top edge (lighter accent stripe), solid purple body       │
│  Adjacent to: each other horizontally, Corner Family E at ends,         │
│               Structure Family B below                                   │
│                                                                          │
│  FAMILY B — Heavy Structural Fill (underground rows 4-5, wall faces)   │
│  Tiles: 05, 21, 65                                                      │
│  Visual: Uniform dark navy solid — NO visible edge detail               │
│  Adjacent to: each other in all directions, Family A above,             │
│               Hazards below/beside                                       │
│                                                                          │
│  FAMILY C — Grid / Panel Walls (vertical wall surfaces rows 1-3)       │
│  Tiles: 07, 11, 12, 14, 17, 47, 56, 57, 61, 62, 64, 66, 73           │
│  Visual: Inset rectangles, grid dividers, or recessed details on        │
│          dark purple base — edges match solid purple seamlessly          │
│  Adjacent to: each other, Family B below, Column Family D beside        │
│  NEVER place: next to Ledge/Edge Family F (visual clash)                │
│                                                                          │
│  FAMILY D — Vertical Columns & Pillars (tall narrow tiles)             │
│  Tiles: 08, 13, 15, 46                                                  │
│  Visual: Narrow centre strip or left/right-aligned bar, purple/navy     │
│  Adjacent to: Panel Family C on sides, Family A/B above/below           │
│  Always frame: between wall panel sections, never isolated              │
│                                                                          │
│  FAMILY E — Corner Caps (inner/outer, orient tile to match geometry)   │
│  Tiles: 04 (inner-TR), 06 (inner-TL), 10 (exterior-TR),               │
│         20 (exterior-TR alt), 22 (notch-TR), 25 (thin-TR),             │
│         55 (muted-TR), 45 (hazard-TR corner)                            │
│  Visual: L-shaped cutout or small square accent in one corner           │
│  Adjacent to: Family A floor tiles on the flat side,                     │
│               empty/sky (.) on the cutout side                           │
│  CRITICAL: Orient so cutout faces OUTWARD from platform body            │
│                                                                          │
│  FAMILY F — Ledge / Edge Bars (horizontal floating platforms)          │
│  Tiles: 18, 23, 26, 27, 48                                              │
│  Visual: Thin horizontal bar with flat walking surface                   │
│  Adjacent to: empty (.) above and below (floating ledge)                │
│  NEVER place: inside a solid block region (defeats visual purpose)      │
│                                                                          │
│  FAMILY G — Horizontal Divider Strips (1px-tall accent lines)          │
│  Tiles: 77, 78, 79, 80                                                  │
│  Visual: Narrow full-width coloured bar (thin strip)                     │
│  Adjacent to: Family A/B surfaces — placed inline as a border accent    │
│                                                                          │
│  FAMILY H — Diagonal Slopes (ramp between height levels)               │
│  Tiles: 19 (TR→BL), 24 (BR→TL), 28 (BL→TR), 29 (TL→BR),             │
│         30 (small-TR), 38 (BL→TR dark)                                  │
│  Visual: Half-block diagonal; one triangle is solid, the other sky-bg   │
│  Adjacent to: Family A on the solid side, empty (.) on the cut side     │
│  RULE: Slopes must connect a HIGHER platform to a LOWER platform.       │
│        The solid triangle side must touch the solid platform tiles.      │
│        Example:  tile-19 sits left of a step-up, tile-28 sits right.   │
│                                                                          │
│  FAMILY I — Decorative / Interactive                                    │
│  Tiles: 37 (circle marker/portal), 39 (alt circle), 70 (gradient       │
│         column), 74-76 (indicator light strips)                          │
│  Adjacent to: placed ON Family A/B surfaces or walls as detail overlay  │
│                                                                          │
│  FAMILY J — Hazard Surfaces (contact damage, red/orange stripes)       │
│  Tiles: 02, 09, 31-36, 40-44, 49-52, 58-60, 63, 67-69,               │
│         71, 72, 81                                                       │
│  Visual: Diagonal red/orange warning stripes on blue/purple base        │
│  Adjacent to: MUST be bounded by Family A solid tiles on at least       │
│               one edge — never floating alone in sky                     │
│                                                                          │
│  FAMILY K — Energy Hazards (vertical instant-kill beams)               │
│  Tiles: 53, 54, 63                                                      │
│  Visual: Vertical glowing neon stripe (full-height column)              │
│  Adjacent to: Placed spanning full column (rows 0-5) or                 │
│               as single-tile obstacles — empty (.) on both sides         │
└──────────────────────────────────────────────────────────────────────────┘
```

#### 21.1a Correct Platform Construction Pattern (Level 1)

```
  A correct floating platform (5 tiles wide):

  Row above platform:   .  .  .  .  .  .  .     (sky — no collision)
  Platform top:         .  g  a  a  a  h  .     (06-corner-TL, 01 floor×3, 04-corner-TR)
  Platform fill:        .  d  d  d  d  d  .     (21-fill underneath)
  Row below:            .  .  .  .  .  .  .     (sky — no collision)

  A correct ground with hazard pit:

  Row 2 (mid):          .  .  .  .  .  .  .  .  .  .  .
  Row 3 (ground):       a  a  a  .  .  .  .  .  a  a  a   ← gap = pit
  Row 4 (subfill):      d  d  d  1  1  1  1  1  d  d  d   ← hazard at bottom of pit
  Row 5 (bedrock):      d  d  d  d  d  d  d  d  d  d  d
```

### 21.2 Level 2 — Power Station (64 tiles)

Tile directory: `Resources/industrial-zone/1 Tiles/power-station-level-2/1 Tiles/`

Tiles share a **blue-purple and teal horizontal-stripe brick** visual.
Key difference from Level 1: tiles have visible **horizontal mortar lines**
(brick pattern) instead of smooth flat surfaces.

```
┌──────────────────────────────────────────────────────────────────────────┐
│  FAMILY A — Horizontal-Stripe Brick Floor (16 colour variants)         │
│  Tiles: 01-16                                                           │
│  Visual: Horizontal brick stripes with 3-4 visible mortar lines,        │
│          each tile a slightly different blue/purple/teal hue             │
│  Adjacent to: each other freely — stripe pattern seamlessly tiles       │
│  Use: Row 3 ground surface, platform bodies on rows 1-2                 │
│  TIP: Mix 2-3 hue variants per platform segment for visual richness     │
│                                                                          │
│  FAMILY B — Small Brick Wall Units (6 variants)                        │
│  Tiles: 17-22                                                           │
│  Visual: LARGER brick pattern (3-4 bricks per tile), blue/purple        │
│  Adjacent to: Family A above (floor→wall transition),                   │
│               each other for wall body fill (rows 4-5)                  │
│  Use: Underground fill, wall faces below platforms                       │
│                                                                          │
│  FAMILY C — Edge / Border Panels (6 variants)                          │
│  Tiles: 23-28                                                           │
│  Visual: Panel border with inner frame detail, blue/navy tones          │
│  Adjacent to: Family A on inner side, sky (.) on outer exposed side    │
│  Use: Platform edges, wall top borders                                   │
│                                                                          │
│  FAMILY D — Heavy Purple Brick (4 variants — bedrock)                  │
│  Tiles: 29-32                                                           │
│  Visual: Full solid purple bricks, dense mortar pattern                  │
│  Adjacent to: Family B above, each other (rows 4-5 bedrock)             │
│                                                                          │
│  FAMILY E — Slopes / Ramps (6 diagonal tiles)                          │
│  Tiles: 33 (TR-cut), 34 (TL-cut), 41-44 (steep/shallow dark slopes)   │
│  Visual: Diagonal cut through blue-teal block                            │
│  Adjacent to: Family A on solid side, sky (.) on cut side               │
│  RULE: pair 33+34 for symmetric ramp; 41-44 for steep dark ramps       │
│                                                                          │
│  FAMILY F — Structural Detail Panels (6 mixed-edge panels)             │
│  Tiles: 35-40                                                           │
│  Visual: Blue-grey panels with mixed detail — inset frames, rivets      │
│  Adjacent to: Family A/B surfaces, Family C edges, Family D below       │
│  Use: Visual variety on long wall stretches and platform undersides      │
│                                                                          │
│  FAMILY G — Flat Blue Platform Blocks (2 ceiling/platform tiles)       │
│  Tiles: 45-46                                                           │
│  Visual: Solid flat blue with no brick pattern — smooth tech surface    │
│  Adjacent to: each other, Family C edges, sky (.) above/below           │
│  Use: Ceiling tiles, floating tech platforms (distinct from brick)       │
│                                                                          │
│  FAMILY H — Grey Tech Bevelled Platforms (7 variants)                  │
│  Tiles: 50-56                                                           │
│  Visual: Dark grey with inset bevel lines — DIFFERENT palette from      │
│          brick families — appears metallic / industrial                   │
│  Adjacent to: each other (form separate grey-tech platform group),      │
│               sky (.) around them, Family C edges optionally             │
│  NEVER mix: directly next to Family A brick tiles (jarring edge clash)  │
│  Use: Elevated tech platforms, machinery surfaces                        │
│                                                                          │
│  FAMILY I — Door / Gate Frames (4 variants)                            │
│  Tiles: 57-60                                                           │
│  Visual: Panel-style gate frame with recessed door detail                │
│  Adjacent to: Family A or Family B on both sides (embedded in wall)     │
│  Use: Boss gates, locked passages, decoration                            │
│                                                                          │
│  FAMILY J — Light Grey Ceiling Tiles (4 variants)                      │
│  Tiles: 61-64                                                           │
│  Visual: Light grey-blue flat structural panels                          │
│  Adjacent to: each other (ceiling runs), Family G flush join             │
│  Use: Row 0 ceiling surfaces in enclosed sections                        │
│                                                                          │
│  FAMILY K — Blue Tech Wall Inlays (2 variants)                        │
│  Tiles: 47-48                                                           │
│  Visual: Blue recessed panel with tech detail on dark background         │
│  Adjacent to: Family B or Family D wall surfaces (decorative panel)     │
│                                                                          │
│  FAMILY L — Magenta Accent (1 tile)                                    │
│  Tile: 49                                                                │
│  Visual: Bright magenta-purple solid block                               │
│  Adjacent to: Family A or Family D (highlight boss-area accent)         │
│  Use: Boss arena boundary marker, alarm indicator                        │
└──────────────────────────────────────────────────────────────────────────┘
```

#### 21.2a Correct Platform Construction Pattern (Level 2)

```
  Floating brick platform (6 tiles wide, mixed hues):

  Row above:            .  .  .  .  .  .  .  .      (sky)
  Platform top (row 1): .  w  a  c  b  d  z  .      (edge-L, mixed brick×4, edge-R)
  Platform fill (row 2):.  q  r  s  q  t  u  .      (wall bricks below)
  Row below:            .  .  .  .  .  .  .  .      (sky)

  Grey-tech elevated platform (separate visual group):

  Row above:            .  .  .  .  .  .           (sky)
  Platform (row 1):     .  X  T  U  Z  .          (grey tech bevel×4)
  Row below:            .  .  .  .  .  .           (sky)

  Ground with ramp transition:

  Row 3:  a  a  a  a  R  .  .  .  .  S  a  a  a  a  a
                      ↑ ramp-up-left      ↑ ramp-up-right
  Row 4:  q  q  q  q  q  .  .  .  .  q  q  q  q  q  q
  Row 5:  D  D  D  D  D  D  D  D  D  D  D  D  D  D  D   (purple bedrock)
```

---

## 22 · LEVEL 1 — FULL MAP REDESIGN

### 22.1 Design Requirements

- **World size:** 500 columns × 6 rows (16,000 × 192 px)
- **ALL 500 columns** must contain meaningful content — no dead stretches
- Ground is **NON-CONTINUOUS**: gaps, pits, ramps, underground passages
- Platforms at multiple heights with ladders connecting them
- **10 sections** (≈50 cols each), each with a distinct challenge theme
- Checkpoints at the end of sections 2, 4, 6, 8
- Boss arena in section 10
- Use actual Prop_Ladder asset images (not code-drawn rectangles)

### 22.2 Section Layout

```
  SECTION MAP (500 columns, each section ≈50 columns)
  ═══════════════════════════════════════════════════════════════════════
  COL:  0        50       100      150      200      250      300      350      400      450  500
        │        │        │        │        │        │        │        │        │        │    │
  SEC:  ├─SEC 1──┼─SEC 2──┤─SEC 3──┤─SEC 4──┤─SEC 5──┤─SEC 6──┤─SEC 7──┤─SEC 8──┤─SEC 9──┤S10┤
        │Tutorial│Vertical│Jump    │Under-  │Factory │Hazard  │Conveyor│Gauntlet│Ascent  │BOSS│
        │ Walk   │ Climb  │ Gaps   │ ground │ Floor  │ Maze   │ Run    │ Sprint │ Tower  │TANK│
        │        │  CP1   │        │  CP2   │        │  CP3   │        │  CP4   │        │    │
  ═══════════════════════════════════════════════════════════════════════

  ┌─ CINEMATIC dialogue at columns: 0, 100, 200, 300, 400 (every ~100 cols)
  └─ Weapon spawns: ~col 30 (PISTOL), ~col 130 (SMG), ~col 250 (RIFLE), ~col 380 (SHOTGUN)
```

### 22.3 Section Descriptions

| # | Name | Cols | Ground Style | Platforms | Enemies | Special |
|---|------|------|-------------|-----------|---------|---------|
| 1 | Tutorial Walk | 0–49 | Continuous with 2 small gaps | 3 stepping stones | 2 drones (UFO) | Cinematic intro scene, PISTOL spawn |
| 2 | Vertical Climb | 50–99 | Broken into 3 islands | 4 stacked + 2 ladders | 3 drones + 1 CombatTank | **Checkpoint 1**, ladder teaching |
| 3 | Jump Gaps | 100–149 | Large gaps (6-8 col wide) | 5 floating ledges (Family F) | 3 drones (Jet) | Cinematic scene, precision platforming |
| 4 | Underground | 150–199 | Surface + underground tunnel | 2 surface + 3 underground | 2 ArmouredKnight + 2 drones | **Checkpoint 2**, chest reward, ladder down+up |
| 5 | Factory Floor | 200–249 | Wide floor with hazard pits | 4 mixed-height platforms | 3 CombatTank + 2 drones | Cinematic scene, conveyor intro |
| 6 | Hazard Maze | 250–299 | Narrow paths between hazards | 6 small islands | 2 WingedWarrior + 3 drones | **Checkpoint 3**, energy barriers |
| 7 | Conveyor Run | 300–349 | Moving conveyor belts | 3 conveyors + 2 hammers | 4 drones | Moving platform challenges |
| 8 | Gauntlet Sprint | 350–399 | Alternating floor/hazard | 4 ledges, 2 ladders | 3 ArmouredKnight + 3 drones | **Checkpoint 4**, cinematic scene, SHOTGUN spawn |
| 9 | Ascent Tower | 400–449 | Vertical tower with no ground | 8 ascending platforms | 2 WingedWarrior + 2 drones | Climactic vertical section |
| 10 | Boss Arena | 450–499 | Wide flat arena floor | 2 elevated sniping ledges | **BOSS: GolfCartSoldier** | "BOSS APPEARED" notification, screen shake |

### 22.4 Enemy Placement Rules

- **Land enemies** (sci-fi-antagonists) patrol on solid ground surfaces:
  - **CombatTank** (heavy): Slow patrol, turret tracks player, ranged shots
  - **ArmouredKnight** (melee): Fast patrol, charges and slashes at close range
  - **WingedWarrior** (hybrid): Alternates ground walk and aerial swoops
- **Drones** (aerial): Hover above platforms in patrol patterns
- **Minimum spacing:** 80px between enemy spawn points
- **Maximum per screen:** 3 enemies visible simultaneously

### 22.5 Prop Placement

Objects directory: `Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/5 Objects/`

| Prop | Placement | Frequency |
|------|-----------|-----------|
| Barrels (A-D) | On ground/platforms, pushable | Every 15-20 cols |
| Boxes/Crates | Stackable, breakable | Every 10-15 cols |
| Fences | Zone dividers between sections | At section boundaries |
| Signs (warning/quest) | Before hazard zones, near objectives | At key junctions |
| Notice Boards | On walls in enclosed sections | Sections 4, 7 |
| Lockers | Wall-mounted in underground areas | Sections 4, underground |
| Fire Extinguishers | Near hazard zones (decorative) | Sections 5, 6 |
| **Ladders** | **Prop_Ladder_TallFullHeight.png** for 4-tile vertical | Sections 2, 4, 8, 9 |
|              | **Prop_Ladder_TallAltSpacing.png** for 3-tile vertical | Sections 4, 9 |
|              | **Prop_Ladder_ShortHorizontalRung.png** for 1-tile | Section 2 (tutorial) |
| Flags (checkpoint) | At checkpoint locations | Sections 2, 4, 6, 8 |

---

## 23 · LEVEL 2 — FULL MAP REDESIGN WITH DAY/NIGHT

### 23.1 Design Requirements

- **World size:** 900 columns × 6 rows (28,800 × 192 px)
- **Day/Night transition:** Columns 0–449 = DAY backgrounds, columns 450–899 = NIGHT backgrounds
- Cross-fade zone: columns 430–469 blend Day→Night over 40 columns
- 16 sections (≈56 cols each), each distinct
- Ground is NON-CONTINUOUS with pipes, power lines, elevated tech platforms
- More vertical gameplay with many multi-level platforms
- Boss encounters at section 8 (mid-boss) and section 16 (final boss)
- Checkpoints at sections 3, 6, 9, 12, 15

### 23.2 Section Layout

```
  SECTION MAP (900 columns)
  DAY HALF (columns 0-449)                    NIGHT HALF (columns 450-899)
  ═══════════════════════════════════════      ═══════════════════════════════════════
  SEC 1: Arrival Platform (0-55)               SEC 9:  Twilight Crossing (450-505)
  SEC 2: Pipe Works (56-111)                   SEC 10: Neon Factory (506-561)
  SEC 3: Pylon Field (112-167) — CP1           SEC 11: Dark Tunnels (562-617)
  SEC 4: Brick Canyon (168-223)                SEC 12: Power Core (618-673) — CP4
  SEC 5: Tech Platforms (224-279)              SEC 13: Night Sky Sprint (674-729)
  SEC 6: Conveyor Heights (280-335) — CP2      SEC 14: Turret Gallery (730-785) — CP5
  SEC 7: Hazard Gallery (336-391)              SEC 15: Reactor Climb (786-841)
  SEC 8: MID-BOSS ARENA (392-449) — CP3        SEC 16: FINAL BOSS (842-899)
        (GreenMech boss)                              (RugbyGuy/variant boss)
  ═══════════════════════════════════════      ═══════════════════════════════════════

  Cinematic scenes at: col 0, 112, 224, 336, 450, 562, 674, 842
  Weapon spawns: ~col 40, ~col 170, ~col 310, ~col 500, ~col 700
```

### 23.3 Day Half Section Descriptions (Cols 0–449)

| # | Name | Cols | Features | Enemies | Special |
|---|------|------|----------|---------|---------|
| 1 | Arrival Platform | 0–55 | Wide brick platforms, intro pipes | 2 drones | Cinematic intro |
| 2 | Pipe Works | 56–111 | Pipe props on walls, narrow paths | 3 drones + 1 CombatTank | Pipe maze |
| 3 | Pylon Field | 112–167 | Power line pylons spanning gaps | 3 drones + 1 ArmouredKnight | **CP1**, cinematic, pylon obstacles |
| 4 | Brick Canyon | 168–223 | Deep canyon with brick walls both sides | 2 CombatTank + 2 drones | Underground passage |
| 5 | Tech Platforms | 224–279 | Grey-tech elevated platforms (Family H) | 2 WingedWarrior + 2 drones | Cinematic, tech platform jumping |
| 6 | Conveyor Heights | 280–335 | Conveyor belts + elevated routes | 4 drones | **CP2**, conveyor riding |
| 7 | Hazard Gallery | 336–391 | Turret hazards + energy barriers | 3 drones + 2 ArmouredKnight | Turret avoidance |
| 8 | MID-BOSS | 392–449 | Wide flat arena, magenta accent tiles | **BOSS: GreenMech** | **CP3**, "BOSS APPEARED", screen shake |

### 23.4 Night Half Section Descriptions (Cols 450–899)

| # | Name | Cols | Features | Enemies | Special |
|---|------|------|----------|---------|---------|
| 9 | Twilight Crossing | 450–505 | Day→Night transition zone, atmospheric | 2 drones | Cinematic (night begins), mood shift |
| 10 | Neon Factory | 506–561 | Factory with glowing screen props | 3 drones + 2 CombatTank | Screens illuminate dark areas |
| 11 | Dark Tunnels | 562–617 | Underground brick tunnels, enclosed | 3 ArmouredKnight + 1 drone | Cinematic, claustrophobic feel |
| 12 | Power Core | 618–673 | Central reactor area, magenta accents | 2 WingedWarrior + 3 drones | **CP4**, power core aesthetic |
| 13 | Night Sky Sprint | 674–729 | Open night sky, floating platforms | 4 drones | Cinematic, fast platforming |
| 14 | Turret Gallery | 730–785 | Turret-heavy section, cover required | 3 turrets + 3 drones | **CP5**, strategic combat |
| 15 | Reactor Climb | 786–841 | Vertical ascending tower, night sky BG | 2 WingedWarrior + 2 ArmouredKnight | Vertical gauntlet |
| 16 | FINAL BOSS | 842–899 | Grand arena, ALL door/gate tiles | **BOSS: RugbyGuy variant** | "FINAL BOSS" notification, screen shake, ending cinematic |

---

## 24 · DAY/NIGHT PARALLAX SYSTEM (Level 2)

### 24.1 Background Asset Mapping

```
  DAY BACKGROUNDS (columns 0--449):
  ──────────────────────────────────
  Layer 1 (fixed):  BG_Layer1_SkyBase_LightGreyWhiteGradient_StaticFill_DrawFirst.png
  Layer 2 (0.08):   BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png
  Layer 3 (0.18):   BG_Layer3_FactoryTall_LightBlueChimney_ParallaxFactor025.png
  Layer 4 (0.30):   BG_Layer4_DistantFactory_FaintSilhouette_ParallaxFactor040.png
  Layer 5 (0.50):   BG_Layer5_FactoryRight_LightBlueVariant_ParallaxFactor060.png

  NIGHT BACKGROUNDS (columns 450--899):
  ──────────────────────────────────────
  Layer 1 (fixed):  BG_Layer1_SkyBase_DarkGreyGradient_StaticFill_DrawFirst.png
  Layer 2 (0.08):   BG_Layer2_FactoryLeft_DarkGreySilhouette_ParallaxFactor015.png
  Layer 3 (0.18):   BG_Layer3_FactoryTall_DarkGreyCentre_ParallaxFactor025.png
  Layer 4 (0.30):   BG_Layer4_DistantFactory_VeryDarkFaint_ParallaxFactor040.png
  Layer 5 (0.50):   BG_Layer5_FactoryRight_DarkGreyVariant_ParallaxFactor060.png

  OVERLAY (drawn LAST over both):
  ────────────────────────────────
  BG_Overlay_BlueYellowDiagonalGradient_ColourAtmosphere_DrawLast.png
```

### 24.2 Rendering Algorithm

```java
// In drawGameplayScreen() for Level 2:
float cameraX = ...; // player-centred camera X
float worldMidpoint = 450 * 32; // = 14400 px (column 450)
float fadeStart = 430 * 32;     // = 13760 px
float fadeEnd   = 469 * 32;     // = 15008 px

for (int layer = 0; layer < 5; layer++) {
    float scrollX = cameraX * SCROLL_FACTORS[layer];

    if (cameraX < fadeStart) {
        // PURE DAY — draw only day layer
        drawParallaxLayer(g, dayBG[layer], scrollX);
    } else if (cameraX > fadeEnd) {
        // PURE NIGHT — draw only night layer
        drawParallaxLayer(g, nightBG[layer], scrollX);
    } else {
        // CROSS-FADE — blend day and night
        float t = (cameraX - fadeStart) / (fadeEnd - fadeStart); // 0.0 → 1.0
        // Draw day with decreasing alpha
        Composite old = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - t));
        drawParallaxLayer(g, dayBG[layer], scrollX);
        // Draw night with increasing alpha
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, t));
        drawParallaxLayer(g, nightBG[layer], scrollX);
        g.setComposite(old);
    }
}
// Always draw overlay last
drawParallaxLayer(g, overlayBG, cameraX * 0.05f);
```

### 24.3 Visual Timeline

```
  COL:  0 ───────────── 430 ─── 450 ─── 469 ────────────── 900
        │  FULL DAY BG  │ CROSS │FADE│ ZONE │  FULL NIGHT BG  │
        │  (light sky,  │  ─────│────│─────  │  (dark sky,      │
        │   blue factry) │ alpha │ blend     │   grey silhouettes)│
        └───────────────┴───────┴────┴───────┴──────────────────┘
```

---

## 25 · PLAYER SPRITE LOADING FIX

### 25.1 Problem

Player character sprite sheets are NOT loading for some animation states.
Example: "Punk idle spritesheet" fails to load, producing a null/missing frame.

### 25.2 Root Cause Analysis

The sprite filename parsing must extract:
1. **Character name** (Biker / Cyborg / Punk)
2. **Animation state** (Idle, Walk, Run, etc.)
3. **Frame count** (from filename: `NFrames1Row`)
4. **Frame delay** (from filename: `Nms`)

### 25.3 Sprite Loading Solution

Each character has **24 animation states** in a standard naming pattern:

```
  NN_Player_{Char}_{State}_{Frames}Frames1Row_{Description}_{Category}_{Playback}_{Delay}ms.png

  Example: 01_Player_Punk_Idle_5Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png
           → Character: Punk, State: Idle, Frames: 5, Delay: 150ms, Loop: true
```

**Full state table (per character):**

| # | State | Biker Frames | Cyborg Frames | Punk Frames | Delay(ms) | Loop? |
|---|-------|-------------|--------------|------------|-----------|-------|
| 01 | Idle | 4 | 4 | 5 | 150 | Yes |
| 02 | Idle2 | 6 | 5 | 5 | 150 | Yes |
| 03 | Walk | 6 | 5 | 5 | 100 | Yes |
| 04 | Run | 6 | 5 | 6 | 80 | Yes |
| 05 | Dash | 6 | 4 | 4 | 60 | Once |
| 06 | Jump | 4 | 3 | 3 | 80 | Once |
| 07 | DoubleJump | 6 | 5 | 4 | 80 | Once |
| 08 | Fall | 4 | 3 | 3 | 100 | Yes |
| 09 | Climb | 6 | 4 | 4 | 120 | Yes |
| 10 | Hang | 3 | 3 | 4 | 150 | Yes |
| 11 | Pullup | 6 | 7 | 7 | 80 | Once |
| 12 | Punch | 5 | 5 | 6 | 70 | Once |
| 13 | Attack1 | 5 | 5 | 5 | 70 | Once |
| 14 | Attack2 | 5 | 5 | 6 | 70 | Once |
| 15 | Attack3 | 7 | 5 | 6 | 70 | Once |
| 16 | WalkAttack | 5 | 5 | 5 | 80 | Once |
| 17 | RunAttack | 5 | 5 | 6 | 70 | Once |
| 18 | Hurt | 2 | 2 | 2 | 100 | Once |
| 19 | Death | 6 | 5 | 5 | 120 | Once |
| 20 | Use | 5 | 5 | 5 | 100 | Once |
| 21 | Sitdown | 3 | 3 | 3 | 120 | Once |
| 22 | Angry | 5 | 5 | 5 | 150 | Yes |
| 23 | Happy | 5 | 5 | 5 | 150 | Yes |
| 24 | Talk | 5 | 5 | 5 | 120 | Yes |

### 25.4 Loader Code Pattern

```java
// In AnimationInitializer or AssetManager — load ALL 24 states per character:
String charDir = "Resources/industrial-zone/characters/player/";
String[] states = {"Idle","Idle2","Walk","Run","Dash","Jump","DoubleJump",
    "Fall","Climb","Hang","Pullup","Punch","Attack1","Attack2","Attack3",
    "WalkAttack","RunAttack","Hurt","Death","Use","Sitdown","Angry","Happy","Talk"};

for (String state : states) {
    // Find file matching pattern: *_Player_{charName}_{state}_*
    File spriteFile = findFileMatching(charDir, "*_Player_" + charName + "_" + state + "_*");
    if (spriteFile == null) {
        System.err.println("MISSING SPRITE: " + charName + " / " + state
            + " — searched: " + charDir);
        continue; // DO NOT create fallback — log and skip
    }
    int frames = parseFrameCount(spriteFile.getName()); // extract NFrames1Row
    int delay  = parseDelay(spriteFile.getName());       // extract Nms
    Animation anim = loadHorizontalSpritesheet(spriteFile, frames, delay);
    player.setAnimation(state, anim);
}
```

---

## 26 · LAND ENEMY SYSTEM (sci-fi-antagonists 1/2/3)

### 26.1 Classification Correction

> **IMPORTANT:** The `sci-fi-antagonists/` folders contain **regular land
> enemies** that spawn throughout BOTH levels — they are NOT bosses.
> The actual bosses are in `characters/bosses/` (GolfCartSoldier, GreenMech, RugbyGuy).

**Enemy directory:** `Resources/industrial-zone/characters/enemies/sci-fi-antagonists/`

### 26.2 Three Land Enemy Types

#### Type 1: Combat Tank (`sci-fi-antagonists/1/`)
```
  ┌─────────────────────────────────────────────────────────────────┐
  │  COMBAT TANK — Heavy Ranged Enemy                              │
  │  HP: 8  |  Damage: 12  |  Speed: 40 px/s  |  Score: 80        │
  │  Detection: 300px  |  Fire cooldown: 2.0s                      │
  │                                                                 │
  │  Behaviour:                                                     │
  │  • Slow patrol on ground surface (left-right within 200px zone)│
  │  • Turret rotates to track player when in detection range      │
  │  • Fires heavy projectile (slow, high damage)                  │
  │  • Has multi-attack combos (Attack1-4) with laser beam option  │
  │  • Special ability: calls for fire support animation           │
  │                                                                 │
  │  Sprites (13 files):                                           │
  │  Idle(4f) Walk(4f) Attack1-4(4f ea) Special(6f)               │
  │  Hurt(2f) Death(5f)                                            │
  └─────────────────────────────────────────────────────────────────┘
```

#### Type 2: Armoured Knight (`sci-fi-antagonists/2/`)
```
  ┌─────────────────────────────────────────────────────────────────┐
  │  ARMOURED KNIGHT — Fast Melee Enemy                            │
  │  HP: 6  |  Damage: 15  |  Speed: 80 px/s  |  Score: 90        │
  │  Detection: 240px  |  Attack cooldown: 1.2s                    │
  │                                                                 │
  │  Behaviour:                                                     │
  │  • Fast patrol on ground surface (200px zone)                  │
  │  • Charges toward player at double speed when detected         │
  │  • Melee blade attacks (Attack1: slash, Attack2: reach slash,  │
  │    Attack3: heavy energy swing, Attack4: gun ranged shot)      │
  │  • Special: energy charge glow transformation                  │
  │  • Has own projectile sprite for Attack4 ranged shot           │
  │                                                                 │
  │  Sprites (10 files):                                           │
  │  Idle(4f) Walk(5f) Attack1-4(5f ea) Special(6f)               │
  │  Projectile(1f) Hurt(2f) Death(6f)                             │
  └─────────────────────────────────────────────────────────────────┘
```

#### Type 3: Winged Warrior (`sci-fi-antagonists/3/`)
```
  ┌─────────────────────────────────────────────────────────────────┐
  │  WINGED WARRIOR — Aerial/Ground Hybrid Enemy                   │
  │  HP: 5  |  Damage: 18  |  Speed: 60 px/s  |  Score: 100       │
  │  Detection: 280px  |  Attack cooldown: 1.5s                    │
  │                                                                 │
  │  Behaviour:                                                     │
  │  • Ground patrol + aerial swoop capability                     │
  │  • Attack1: kick strike combo (melee)                          │
  │  • Attack2: orb projectile shot + return (ranged)              │
  │  • Attack3: wing cape slash sweep (melee AoE)                  │
  │  • Attack4: jump aerial slam + ground burst (heavy)            │
  │  • Attack4b: ground slam burst follow-through                  │
  │  • Special: wing flare charge ability                          │
  │  • Own projectile sprite (single red projectile)               │
  │                                                                 │
  │  Sprites (11 files):                                           │
  │  Idle(4f) Walk(6f) Attack1-4(5-6f ea) Attack4b(4f)            │
  │  Special(6f) Projectile(1f) Hurt(2f) Death(6f)                │
  └─────────────────────────────────────────────────────────────────┘
```

### 26.3 Land Enemy AI State Machine

```
  ┌───────┐     spawn timer      ┌──────┐    no player     ┌────────┐
  │ SPAWN ├─────────────────────►│ IDLE ├──────────────────►│ PATROL │
  └───────┘                      └──┬───┘                   └──┬─────┘
                                    │ player in range           │ player in range
                                    ▼                           ▼
                               ┌────────┐    within attack  ┌────────┐
                               │ CHASE  ├──────────────────►│ ATTACK │
                               └──┬─────┘   range           └──┬─────┘
                                  │                             │
                                  │ player out of range         │ attack done
                                  ▼                             ▼
                               ┌────────┐                   ┌────────┐
                               │ PATROL │◄──────────────────┤ IDLE   │
                               └────────┘   cooldown done    └────────┘
                                    │
                                    │ takes damage
                                    ▼
                               ┌────────┐    HP > 0         ┌────────┐
                               │  HURT  ├──────────────────►│ CHASE  │
                               └──┬─────┘                   └────────┘
                                  │ HP <= 0
                                  ▼
                               ┌────────┐
                               │ DEATH  │ → drop loot → remove entity
                               └────────┘
```

### 26.4 Spawn Placement Rules

| Level | Enemy Type | Sections | Count per Section | Total |
|-------|-----------|----------|-------------------|-------|
| L1 | CombatTank | 2, 5, 8 | 1-3 | ~6 |
| L1 | ArmouredKnight | 4, 8 | 2 | ~4 |
| L1 | WingedWarrior | 6, 9 | 2 | ~4 |
| L2 | CombatTank | 2, 4, 10 | 1-2 | ~5 |
| L2 | ArmouredKnight | 3, 7, 11, 15 | 1-2 | ~6 |
| L2 | WingedWarrior | 5, 12, 15 | 1-2 | ~5 |

---

## 27 · BOSS ENCOUNTER SYSTEM

### 27.1 Boss Registry

Bosses are in `Resources/industrial-zone/characters/bosses/`:

| Boss | Folder | Level | Section | Where |
|------|--------|-------|---------|-------|
| GolfCartSoldier | `bosses/GolfCartSoldier/` | Level 1 | Section 10 | Col ~470 |
| GreenMech | `bosses/GreenMech/` | Level 2 | Section 8 (mid-boss) | Col ~420 |
| RugbyGuy (variant) | `bosses/RugbyGuy/` | Level 2 | Section 16 (final) | Col ~870 |

### 27.2 GolfCartSoldier Boss (Level 1 Final)

```
  TWO PHASES:

  Phase A — GolfCart (vehicle):
  ┌────────────────────────────────────────────────────────────┐
  │ Idle(4f) IdleEmpty(4f) Walk/Drive(4f) FastOut(5f) Death(6f)│
  │ HP: 25  |  Damage: 20  |  Speed: 80 px/s                 │
  │ Drives back and forth across arena, ramming player        │
  │ On death → soldier dismounts → Phase B                    │
  └────────────────────────────────────────────────────────────┘

  Phase B — GolfSoldier (on foot):
  ┌────────────────────────────────────────────────────────────┐
  │ Idle(4f) Walk(5f) Attack/MeleeWeaponSweep(5f)            │
  │ Hurt1(2f) Hurt2(2f) Death(4f) Sneer/Taunt(5f)            │
  │ HP: 15  |  Damage: 15  |  Speed: 60 px/s                 │
  │ Patrols, taunts player, melee weapon sweep attacks        │
  │ When HP < 30%: taunts then fights aggressively            │
  └────────────────────────────────────────────────────────────┘
```

### 27.3 GreenMech Boss (Level 2 Mid-Boss)

```
  ┌────────────────────────────────────────────────────────────┐
  │ Idle1-4(4f×4) Walk(5f) Walk2(5f) Charge(6f)              │
  │ Attack1/CannonBlast(4f) Attack2/StompFireCombo(5f)         │
  │ Hit(2f) Death(4f) Ball/Projectile(1f)                     │
  │                                                            │
  │ HP: 40  |  Damage: 25  |  Speed: 50 px/s                 │
  │                                                            │
  │ Phase 1 (HP > 50%): Slow walk + cannon blast              │
  │ Phase 2 (HP 20-50%): Charge + stomp fire combo            │
  │ Phase 3 (HP < 20%): Rapid cannon + charge chain           │
  └────────────────────────────────────────────────────────────┘
```

### 27.4 Boss Entry Sequence

When player enters boss arena column range:

```
  1. Gameplay PAUSES (freeze player + all entities)
  2. Camera smoothly pans to boss spawn position (0.5s)
  3. Boss idle animation plays
  4. Screen shake: intensity=6px, duration=800ms
  5. BIG NOTIFICATION: "BOSS APPEARED!" — white text, 72pt, centre screen
     → Stays for 2.0 seconds with red pulsing glow behind text
  6. Notification fades (0.5s)
  7. Camera returns to player, gameplay RESUMES
  8. Boss music track starts (replace level BGM)
```

---

## 28 · CINEMATIC SCENE SYSTEM

### 28.1 Design Philosophy

> The game should feel like a **movie** — cinematic scenes with character
> dialog trigger at regular intervals to drive the narrative forward.

### 28.2 Trigger Mechanism

Cinematic scenes trigger when the player crosses specific X-coordinate thresholds:

| Level | Col | X-Position | Scene ID | Content |
|-------|-----|-----------|----------|---------|
| L1 | 0 | 0 | L1_INTRO | Mission briefing, character intro |
| L1 | 100 | 3200 | L1_MIDWAY_1 | "Factory getting dangerous..." |
| L1 | 200 | 6400 | L1_MIDWAY_2 | "Nearly at the core!" |
| L1 | 300 | 9600 | L1_MIDWAY_3 | "Security systems activated!" |
| L1 | 400 | 12800 | L1_PRE_BOSS | "Tank approaching — get ready!" |
| L2 | 0 | 0 | L2_INTRO | Power station entry briefing |
| L2 | 112 | 3584 | L2_PYLON_FIELD | "Watch the power lines!" |
| L2 | 224 | 7168 | L2_TECH_ZONE | "Advanced security ahead..." |
| L2 | 336 | 10752 | L2_PRE_MIDBOSS | "Detecting large mech signature!" |
| L2 | 450 | 14400 | L2_NIGHT_BEGINS | "Night falls. Stay alert." |
| L2 | 562 | 17984 | L2_DARK_TUNNELS | "Going underground — watch out." |
| L2 | 674 | 21568 | L2_NIGHT_SKY | "Almost at the final reactor..." |
| L2 | 842 | 26944 | L2_FINAL_BOSS | "Final guardian detected. This ends now!" |

### 28.3 Scene Rendering

```
  ┌════════════════════════════════════════════════════════════════════┐
  │                     (game world darkened to 40% opacity)          │
  │                                                                    │
  │    ┌──────────────────────────────────────────────────────────┐    │
  │    │                                                          │    │
  │    │   [CHARACTER PORTRAIT]    "Dialog text here that         │    │
  │    │    ┌────────┐             scrolls letter by letter       │    │
  │    │    │        │             at 30ms per character,         │    │
  │    │    │  64×64 │             creating a typewriter           │    │
  │    │    │  face  │             effect."                        │    │
  │    │    │        │                                             │    │
  │    │    └────────┘                            [SPACE to skip] │    │
  │    │                                                          │    │
  │    └──────────────────────────────────────────────────────────┘    │
  │                                                                    │
  │                     (gameplay frozen during scene)                  │
  └════════════════════════════════════════════════════════════════════┘

  Implementation:
  • GameState adds CINEMATIC state (gameplay paused, scene drawing active)
  • Scene advances on SPACE press or auto-advances after 3 seconds
  • Character portrait uses Talk animation frame (state 24) at 64×64 crop
  • Semi-transparent black overlay (alpha 0.6) covers gameplay
  • Dialog box: 600×120px, centred horizontally, bottom third of screen
  • Text typewriter speed: 30ms/character
  • Skip: SPACE key immediately shows full text, next SPACE closes scene
```

### 28.4 Scene Data Format

```java
// In LevelData interface — each level defines its cinematic scenes:
default CinematicScene[] getCinematicScenes() { return new CinematicScene[0]; }

class CinematicScene {
    int triggerColumn;         // map column that triggers this scene
    String speakerName;        // "AGENT", "COMMAND", "UNKNOWN"
    String dialogText;         // dialog text to display
    boolean triggered;         // prevent re-triggering
}
```

---

## 29 · CHECKPOINT & CARD SYSTEM

### 29.1 Checkpoint Activation

Checkpoints are **Portal** animated objects placed at specific map positions.
They require the player to have collected a minimum number of **Card**
collectibles to activate.

```
  Checkpoint activation rules:
  ┌──────────────────────────────────────────────────────┐
  │  Approach Portal → E-key interaction prompt appears   │
  │                                                        │
  │  IF cards >= threshold:                                │
  │    → Portal opens (play Anim_Portal opening animation)│
  │    → Screen flash (white, 200ms)                       │
  │    → "CHECKPOINT SAVED!" notification (2s)             │
  │    → Respawn point updated to this portal position     │
  │    → Cards spent (subtract threshold from count)       │
  │                                                        │
  │  IF cards < threshold:                                 │
  │    → "Need N more cards!" warning text (red, 1.5s)     │
  │    → Portal remains inactive                           │
  └──────────────────────────────────────────────────────┘
```

### 29.2 Checkpoint Locations & Card Thresholds

| Level | Checkpoint | Section | Approx Col | Cards Required |
|-------|-----------|---------|-----------|---------------|
| L1 | CP1 | Section 2 end | ~99 | 1 card |
| L1 | CP2 | Section 4 end | ~199 | 2 cards |
| L1 | CP3 | Section 6 end | ~299 | 2 cards |
| L1 | CP4 | Section 8 end | ~399 | 3 cards |
| L2 | CP1 | Section 3 end | ~167 | 1 card |
| L2 | CP2 | Section 6 end | ~335 | 2 cards |
| L2 | CP3 | Section 8 end (pre-boss) | ~449 | 3 cards |
| L2 | CP4 | Section 12 end | ~673 | 2 cards |
| L2 | CP5 | Section 14 end | ~785 | 3 cards |

### 29.3 Card Spawn Frequency

- **Level 1:** 15 cards total scattered across 500 columns (1 per ~33 cols)
- **Level 2:** 25 cards total scattered across 900 columns (1 per ~36 cols)
- Cards placed on platforms, in underground passages, and near hazard rewards
- Use `Anim_Collectible_Card_6Frames1Row` animated sprite

---

## 30 · HUD OVERHAUL

### 30.1 Layout (1500×860 screen)

```
  ┌══════════════════════════════════════════════════════════════════════════┐
  │                                                                          │
  │  ┌───HP BAR──────────┐     $ 1,250    🃏 2/3     SCORE: 12450           │
  │  │ ██████████░░░░░░░ │     (cash)    (cards)    (running score)         │
  │  │ 78 / 100          │                                           ⏱ 02:34│
  │  └───────────────────┘                                                  │
  │  ┌───ENERGY BAR──────┐                                                  │
  │  │ █████████████░░░░ │         LEVEL 1 — SECTION 3                     │
  │  └───────────────────┘         (zone name, fades after 3s)              │
  │                                                                          │
  │  ┌───HEAL CD─────────┐                                                  │
  │  │ ░░░░██████████████ │  ← heal cooldown progress bar                   │
  │  └───────────────────┘                                                  │
  │                                                                          │
  │                                                                          │
  │  ┌════════════════════════════════════════════════════════════════════┐  │
  │  │                                                                    │  │
  │  │            BIG NOTIFICATION ZONE (centre screen)                   │  │
  │  │   "BOSS APPEARED!"  ← 72pt white, red glow pulse, 2s duration    │  │
  │  │   "CHECKPOINT SAVED!"  ← 48pt green, 2s duration                  │  │
  │  │   "WEAPON ACQUIRED: RIFLE"  ← 36pt cyan, 1.5s duration           │  │
  │  │                                                                    │  │
  │  └════════════════════════════════════════════════════════════════════┘  │
  │                                                                          │
  │                                                                          │
  │                                                                          │
  │  [E] INTERACT          WEAPON INVENTORY BAR                              │
  │  (contextual)    ┌──────┬──────┬──────┬──────┐                          │
  │                  │ [1]🔫│ [2]  │ [3]  │ [4]  │                          │
  │  Objective:      │PISTOL│ SMG  │  --- │  --- │                          │
  │  DEFEAT ALL      │ 8/12 │30/60 │  --- │  --- │                          │
  │  ENEMIES         └──────┴──────┴──────┴──────┘                          │
  │                                                                          │
  └══════════════════════════════════════════════════════════════════════════┘
```

### 30.2 Big Notification System

```java
// Notification queue — shown one at a time, centre screen
class HUDNotification {
    String text;
    Color color;
    int fontSize;
    long displayMs;
    long startTime;
    boolean pulseGlow; // for boss notifications
}

// Trigger examples:
showNotification("BOSS APPEARED!", Color.WHITE, 72, 2000, true);
showNotification("CHECKPOINT SAVED!", Color.GREEN, 48, 2000, false);
showNotification("WEAPON: RIFLE", Color.CYAN, 36, 1500, false);
showNotification("SECTION 3: JUMP GAPS", Color.YELLOW, 32, 2000, false);
```

### 30.3 Zone Labels

When the player enters a new section, a zone label appears top-centre:

```
  "LEVEL 1 — SECTION 3: JUMP GAPS"
  ├─ Font: 24pt bold
  ├─ Color: white with semi-transparent background bar
  ├─ Duration: 3 seconds, fade out over 0.5s
  └─ Position: centre-top, Y=60px
```

### 30.4 Screen Shake

```java
// Screen shake: offset all rendering by random displacement
int shakeOffsetX = 0;
int shakeOffsetY = 0;
long shakeEndTime = 0;
int shakeIntensity = 0;

void triggerScreenShake(int intensity, long durationMs) {
    shakeIntensity = intensity; // pixels max displacement
    shakeEndTime = System.currentTimeMillis() + durationMs;
}

// In draw(): before rendering, apply offset
if (System.currentTimeMillis() < shakeEndTime) {
    shakeOffsetX = (int)(Math.random() * shakeIntensity * 2 - shakeIntensity);
    shakeOffsetY = (int)(Math.random() * shakeIntensity * 2 - shakeIntensity);
} else {
    shakeOffsetX = 0; shakeOffsetY = 0;
}
g.translate(shakeOffsetX, shakeOffsetY);
// ... render everything ...
g.translate(-shakeOffsetX, -shakeOffsetY);

// Triggers:
// Boss entry:       triggerScreenShake(6, 800);
// Boss heavy attack: triggerScreenShake(4, 400);
// Player death:     triggerScreenShake(8, 500);
```

---

## 31 · CAMERA SYSTEM FIX

### 31.1 Current Problem

Camera jumps or doesn't follow the player smoothly, especially at world edges.

### 31.2 Camera Follow Algorithm

```java
// Smooth camera follow with deadzone
float cameraX, cameraY;
static final float CAMERA_SPEED = 0.08f; // lerp factor (0.0 = static, 1.0 = instant)
static final int DEAD_ZONE_X = 80;       // pixels — no camera move if player within
static final int DEAD_ZONE_Y = 40;

void updateCamera(float playerX, float playerY, float worldWidth, float worldHeight) {
    float targetX = playerX - SCREEN_W / 2f;
    float targetY = playerY - SCREEN_H / 2f;

    // Deadzone — only move if player is outside deadzone rectangle
    float diffX = targetX - cameraX;
    float diffY = targetY - cameraY;
    if (Math.abs(diffX) > DEAD_ZONE_X) {
        cameraX += (diffX - Math.signum(diffX) * DEAD_ZONE_X) * CAMERA_SPEED;
    }
    if (Math.abs(diffY) > DEAD_ZONE_Y) {
        cameraY += (diffY - Math.signum(diffY) * DEAD_ZONE_Y) * CAMERA_SPEED;
    }

    // Clamp to world boundaries
    cameraX = Math.max(0, Math.min(cameraX, worldWidth - SCREEN_W));
    cameraY = Math.max(0, Math.min(cameraY, worldHeight - SCREEN_H));
}
```

### 31.3 Integration Points

- Call `updateCamera()` every frame in `update()`
- Pass `cameraX`, `cameraY` to all rendering: tiles, parallax, entities, HUD
- HUD renders at **screen coordinates** (NOT offset by camera)
- Everything else renders at **world coordinates − camera offset**

---

## 32 · ACTUAL LADDER ASSETS (REPLACE CODE-DRAWN)

### 32.1 Problem

Ladders are currently drawn as code-generated brown rectangles.
Must use the actual PNG assets from the game's object directory.

### 32.2 Ladder Asset Files

Directory: `Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/5 Objects/`

| Asset File | Use Case | Height | Placement |
|-----------|---------|--------|-----------|
| `Prop_Ladder_TallFullHeight_BlueGreyRungs_ShaftWallClimb_ClimbableA.png` | 4-tile vertical climb | ~128px | Main ladders between levels |
| `Prop_Ladder_TallAltSpacing_BlueGreyRungs_ShaftWallClimb_ClimbableB.png` | 3-tile vertical climb | ~96px | Shorter connections |
| `Prop_Ladder_ShortHorizontalRung_BlueCrossbar_PlatformConnector_Short.png` | 1-tile short step | ~32px | Platform connectors |

### 32.3 Rendering Change

```java
// BEFORE (wrong — code-drawn):
g.setColor(new Color(139, 90, 43));
g.fillRect(ladderX, ladderY, 16, ladderHeight);

// AFTER (correct — load actual PNG):
Image ladderImg = loadImage("Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/5 Objects/"
    + "Prop_Ladder_TallFullHeight_BlueGreyRungs_ShaftWallClimb_ClimbableA.png");
if (ladderImg != null) {
    g.drawImage(ladderImg, (int)(ladderX - cameraX), (int)(ladderY - cameraY), null);
}
// NEVER fall back to colored rectangle — log error if image is null
```

### 32.4 Ladder Zone Data

Ladders defined in LevelData with position + which asset variant:

```java
// In Level1.java:
public LadderDef[] getLadderDefs() {
    return new LadderDef[] {
        new LadderDef(1580, 320, "TallFullHeight"),  // Section 2
        new LadderDef(2400, 288, "TallAltSpacing"),   // Section 3
        new LadderDef(5100, 352, "TallFullHeight"),   // Section 4 underground
        new LadderDef(5300, 256, "TallFullHeight"),   // Section 4 exit
        new LadderDef(11800, 320, "TallAltSpacing"),  // Section 8
        new LadderDef(13200, 288, "TallFullHeight"),  // Section 9
    };
}
```

---

## 33 · ANIMATED OBJECT AI

### 33.1 Object Behaviours

| Object | Animation | Behaviour | Interaction |
|--------|-----------|-----------|-------------|
| **Card** | 6f spinning loop | Floats + bobs ±4px sine wave | Auto-pickup on contact, +1 card |
| **Money** | 6f spinning loop | Floats + bobs ±4px sine wave | Auto-pickup on contact, +25 cash |
| **Chest** | 8f lid-open sequence | Static until interacted | E-key opens → drops 3 money + random weapon |
| **Conveyor** | 4f belt loop | Moves player horizontally 60px/s when standing on it | Automatic, directional |
| **Hammer** | 6f swing arc | Swings in arc, damage on frames 3-5 | Contact damage (15 HP), avoid timing |
| **Portal** | 4f chevron gate | Static until activated (checkpoint) | E-key + cards = checkpoint save |
| **Screen1/2** | 4f flicker | Static wall decoration | No interaction (ambient) |
| **MovingRed** | 6f sliding | Slides left↔right 200px range, 40px/s | Rideable platform, player moves with it |
| **Turret** (L2) | Multi-frame | Rotates to face player | Fires projectile every 3s at player in 400px range |

### 33.2 Turret AI (Level 2 Specific)

```
  ┌───────────────────────────────────────────────────┐
  │  TURRET — Stationary ranged hazard                │
  │                                                    │
  │  Placed on platforms facing player approach side   │
  │  Detection range: 400px                            │
  │  Fire rate: 1 shot every 3 seconds                 │
  │  Projectile damage: 10 HP                          │
  │  Projectile speed: 200 px/s                        │
  │                                                    │
  │  States: IDLE → TRACKING → FIRING → COOLDOWN       │
  │  Rotate turret to face player when in range        │
  │  Fire animation + spawn projectile entity          │
  └───────────────────────────────────────────────────┘
```

---

## 34 · PHYSICS FOR ALL SPRITE STATES

### 34.1 State-Physics Mapping

| AnimState | Gravity | Horiz Movement | Vert Movement | Collision |
|-----------|---------|---------------|--------------|-----------|
| IDLE | Normal | None | None | Full |
| IDLE2 | Normal | None | None | Full |
| WALK | Normal | Walk speed (120px/s) | None | Full |
| RUN | Normal | Run speed (200px/s) | None | Full |
| DASH | Normal | Dash speed (300px/s for 0.3s) | None | Full |
| JUMP | Normal | Preserve horizontal | Jump impulse (-280px/s) | Full |
| DOUBLE_JUMP | Normal | Preserve horizontal | Second impulse (-240px/s) | Full |
| FALL | Normal | Preserve horizontal | Gravity accelerates | Full |
| CLIMB | **NONE** | None | Climb speed (±80px/s) | Vertical only |
| HANG | **NONE** | None | None (fixed Y) | None (attached to ledge) |
| PULLUP | **NONE** | None | Rising (+32px over anim) | None (scripted) |
| PUNCH | Normal | Half walk speed | None | Full |
| ATTACK1-3 | Normal | None (rooted) | None | Full |
| WALK_ATTACK | Normal | Walk speed | None | Full |
| RUN_ATTACK | Normal | Run speed | None | Full |
| HURT | Normal | Knockback (-100px/s, 0.2s) | Small bounce (-80px/s) | Full |
| DEATH | **NONE** | None | Collapse animation | Disabled (ghost) |
| USE | Normal | None (rooted) | None | Full |
| SITDOWN | Normal | None | None | Full |
| ANGRY | Normal | None | None | Full |
| HAPPY | Normal | None | None | Full |
| TALK | Normal | None | None | Full |

### 34.2 State Transition Priority

```
  DEATH (highest — overrides everything)
    ↓
  HURT (interrupts any action)
    ↓
  CLIMB / HANG / PULLUP (ladder states)
    ↓
  DASH (time-limited, cancels movement)
    ↓
  ATTACK states (rooted or moving)
    ↓
  JUMP / DOUBLE_JUMP / FALL (aerial)
    ↓
  RUN / WALK / IDLE (ground movement — lowest)
```

---

## 35 · MENU / GUI IMPROVEMENTS

### 35.1 Background Size Fix

Menu background image should match the viewport (1500×860).
Load and scale: `g.drawImage(menuBG, 0, 0, SCREEN_W, SCREEN_H, null);`

### 35.2 Player Selection Cards — Enhanced

```
  ┌──────────────────────────────────────────────────────┐
  │                                                        │
  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐    │
  │  │    BIKER     │  │   CYBORG     │  │    PUNK      │    │
  │  │             │  │             │  │             │    │
  │  │  [Animated  │  │  [Animated  │  │  [Animated  │    │
  │  │   Idle      │  │   Idle      │  │   Idle      │    │
  │  │   Preview]  │  │   Preview]  │  │   Preview]  │    │
  │  │             │  │             │  │             │    │
  │  │  HP:  100   │  │  HP:  90    │  │  HP:  80    │    │
  │  │  SPD: ███░░ │  │  SPD: ████░ │  │  SPD: █████ │    │
  │  │  ATK: ████░ │  │  ATK: ███░░ │  │  ATK: ██░░░ │    │
  │  │  DEF: ███░░ │  │  DEF: ████░ │  │  DEF: ██░░░ │    │
  │  │             │  │             │  │             │    │
  │  │  "Balanced  │  │  "Durable   │  │  "Fast and  │    │
  │  │   fighter"  │  │   tank"     │  │   agile"    │    │
  │  └─────────────┘  └─────────────┘  └─────────────┘    │
  │                                                        │
  │           [←]  SELECT: ENTER  [→]                      │
  │                                                        │
  └──────────────────────────────────────────────────────┘

  Features:
  • Animated idle preview (play Idle animation in card)
  • Stat bars (HP, Speed, Attack, Defence) with filled rectangles
  • Character description text
  • Arrow key navigation + ENTER to confirm
  • Selected card highlighted with cyan border glow
```

### 35.3 Level Selection — Enhanced

```
  ┌──────────────────────────────────────────────────────┐
  │                                                        │
  │  ┌───────────────────┐    ┌───────────────────┐        │
  │  │  LEVEL 1           │    │  LEVEL 2           │        │
  │  │  Industrial Zone   │    │  Power Station     │        │
  │  │                   │    │                   │        │
  │  │  [BG Preview:     │    │  [BG Preview:     │        │
  │  │   BG_Composite]   │    │   Day Composite]  │        │
  │  │                   │    │                   │        │
  │  │  Sections: 10     │    │  Sections: 16     │        │
  │  │  Enemies: ~14     │    │  Enemies: ~20     │        │
  │  │  Boss: GolfCart    │    │  Boss: GreenMech   │        │
  │  │  Best Time: --:-- │    │  Best Time: --:-- │        │
  │  │                   │    │                   │        │
  │  └───────────────────┘    └───────────────────┘        │
  │                                                        │
  │           [←]  SELECT: ENTER  [→]                      │
  │                                                        │
  └──────────────────────────────────────────────────────┘

  Features:
  • Level thumbnail using BG_Composite image
  • Level stats (sections, enemies, boss name)
  • Best time (saved between sessions, or "--:--" if not played)
  • Arrow navigation + ENTER
```

---

## 36 · IMPLEMENTATION PRIORITY — PHASE 2

| Priority | Task | Section Ref | Dependencies | Estimated Complexity |
|----------|------|------------|-------------|---------------------|
| **P0** | Fix player sprite loading (all 24 states) | §25 | None | Medium |
| **P0** | Fix camera follow + clamp | §31 | None | Low |
| **P0** | Replace code-drawn ladders with PNG assets | §32 | Ladder images | Low |
| **P0** | Fix tile adjacency in map files | §21 | Image review | High |
| **P1** | Redesign Level 1 map (full 500 cols) | §22 | §21 adjacency done | High |
| **P1** | Redesign Level 2 map (full 900 cols) | §23 | §21 adjacency done | High |
| **P1** | Day/Night parallax for Level 2 | §24 | BG images loaded | Medium |
| **P1** | Land enemy system (3 types + AI) | §26 | Sprite loading | High |
| **P1** | HUD overhaul (cash/cards/score/notifications) | §30 | None | Medium |
| **P1** | Screen shake system | §30.4 | None | Low |
| **P2** | Boss encounter system (3 bosses) | §27 | Enemy AI done | High |
| **P2** | Cinematic scene system | §28 | None | Medium |
| **P2** | Checkpoint & card activation | §29 | Portal objects | Medium |
| **P2** | Animated object AI (turrets, conveyors, chests) | §33 | Object loader | Medium |
| **P2** | Physics for all 24 animation states | §34 | Sprite loading | Medium |
| **P3** | Player selection card enhancement | §35.2 | Character sprites | Low |
| **P3** | Level selection enhancement | §35.3 | BG images | Low |
| **P3** | Menu background size fix | §35.1 | None | Low |

---

*Phase 2 plan compiled from inspection of all 1174+ game assets, actual tile image
analysis, and the full `Resources/industrial-zone/` directory structure.*

