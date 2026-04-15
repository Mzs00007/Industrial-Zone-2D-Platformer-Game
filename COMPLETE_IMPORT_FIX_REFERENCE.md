# COMPLETE IMPORT FIX REFERENCE GUIDE
**Generated after file reorganization by folder names**

---

## STATUS: PHASE 1 COMPLETE ✓

### Phase 1: File Reorganization (DONE)
- ✓ AnimationInitializer.java moved from 2_Managers → 5_Animation
- ✓ AnimationPlayer.java moved from 2_Managers → 5_Animation  
- ✓ Package declarations updated: `core` → `animation`
- ✓ Imports updated: `core.Core` → `managers.Core`

---

## FOLDER STRUCTURE & CURRENT STATUS

```
src/
├── 1_Framework/           (8 files)   - package: framework ✓
├── 2_Managers/            (58 files)  - package: managers
|   ├── "Old package: core" - need to change to "managers"
|   ├── "Old package: config" - need to change to "managers"
|   └── "Old package: core.assets" - need to change to "managers"
├── 3_Controllers/         (90+ files) - package: controllers
|   ├── "Old package: gui" - need to change to "controllers"
|   ├── "Old package: gui.screens" - need to change to "controllers"
|   └── "Old package: rendering" - need to change to "controllers"
├── 4_Entities/            (37 files)  - package: entities
|   └── "Old package: core_game_entities" - need to change to "entities"
├── 5_Animation/           (502+ files) - package: animation ✓ (2 files just moved)
├── 6_Physics/             (11 files)  - package: physics
├── 7_AI/                  (27 files)  - package: ai
├── 8_Utilities/           (12 files)  - package: utilities
|   ├── "Old package: audio" - need to change to "utilities"
|   └── "Old package: core_game_entities.audio" - need to change to "utilities"
├── 9_Enums/               (9 files)   - package: enums
|   └── "Old package: assets.enums" - need to change to "enums"
├── 10_Interfaces/         (0 files)
├── 11_Exceptions/         (0 files)
├── 12_Tests/              (1 file)
├── 13_Duplicates/         (4 backup files)
├── game2D/                (7 files)   - DO NOT TOUCH
└── Root level:            (4 files)   - Game.java, Enemy.java, GameTest.java, Game_minimal.java
```

---

## IMPORT REPLACEMENT PATTERNS

### Pattern 1: Core Managers (2_Managers)
**Old:**
```java
package core;
import core.*;
import core.assets.*;
import config.*;
```

**New:**
```java
package managers;
import managers.*;
```

**Files to Fix:** ~58 files in 2_Managers/

---

### Pattern 2: GUI Controllers (3_Controllers)  
**Old:**
```java
package gui;
package gui.screens;
package rendering;
import gui.*;
import gui.screens.*;
import rendering.*;
```

**New:**  
```java
package controllers;
import controllers.*;
```

**Files to Fix:** ~90 files in 3_Controllers/

---

### Pattern 3: Game Entities (4_Entities)
**Old:**
```java
package core_game_entities;
package core_game_entities.audio;
import core_game_entities.*;
```

**New:**
```java
package entities;
import entities.*;
```

**Files to Fix:** ~37 files in 4_Entities/

---

### Pattern 4: Audio Utilities (8_Utilities)
**Old:**
```java
package audio;
package core_game_entities.audio;
import audio.*;
import core_game_entities.audio.*;
```

**New:**
```java
package utilities;
import utilities.*;
```

**Files to Fix:** ~12 files in 8_Utilities/

---

### Pattern 5: Enumerations (9_Enums)
**Old:**
```java
package assets.enums;
import assets.enums.*;
```

**New:**
```java
package enums;
import enums.*;
```

**Files to Fix:** 9 files in 9_Enums/ (AssetEnumIndex.java, AudioAssets.java, CharacterAssets.java, etc.)

---

## DEPENDENCY MAP

### Cross-Folder Dependencies to Watch

**Framework (1_Framework) imports from:**
- managers → 2_Managers
- controllers → 3_Controllers
- enums → 9_Enums

**Managers (2_Managers) imports from:**
- animation → 5_Animation (AnimationPlayer, AnimationInitializer NOW HERE)
- physics → 6_Physics
- ai → 7_AI
- utilities → 8_Utilities
- enums → 9_Enums

**Controllers (3_Controllers) imports from:**
- managers → 2_Managers
- entities → 4_Entities
- animation → 5_Animation
- utilities → 8_Utilities
- enums → 9_Enums

**Entities (4_Entities) imports from:**
- managers → 2_Managers
- animation → 5_Animation
- physics → 6_Physics
- ai → 7_AI
- utilities → 8_Utilities
- enums → 9_Enums

---

## NEXT STEPS (PHASE 2)

### Priority Order for Import Fixes:

**Step 1:** Fix 9_Enums package declarations (8 files)
- Change `package assets.enums;` → `package enums;`
- Update imports: `import assets.enums.*;` → `import enums.*;`

**Step 2:** Fix 8_Utilities package declarations (~12 files)
- Change `package audio;` → `package utilities;`
- Change `package core_game_entities.audio;` → `package utilities;`
- Update imports accordingly

**Step 3:** Fix 2_Managers package declarations (~58 files)
- Change `package core;` → `package managers;`
- Change `package config;` → `package managers;`
- Change `package core.assets;` → `package managers;`
- Update all imports

**Step 4:** Fix 4_Entities package declarations (~37 files)
- Change `package core_game_entities.*;` → `package entities;`
- Update imports  

**Step 5:** Fix 3_Controllers package declarations (~90 files)
- Change `package gui;` → `package controllers;`
- Change `package gui.screens;` → `package controllers;`
- Change `package rendering;` → `package controllers;`
- Update imports

**Step 6:** Fix 6_Physics, 7_AI, and others as needed

**Step 7:** Compile and test: `javac -d bin -cp bin src/**/*.java`

**Step 8:** Push to GitHub with message: "Fix all package declarations and imports after file reorganization"

---

## FILES ALREADY MOVED & FIXED ✓

| File | From | To | Status |
|------|------|-----|--------|
| AnimationInitializer.java | 2_Managers | 5_Animation | package updated: `core` → `animation` |
| AnimationPlayer.java | 2_Managers | 5_Animation | package updated: `core` → `animation` |

---

## VERIFICATION CHECKLIST

- [ ] All 9_Enums files have `package enums;`
- [ ] All 8_Utilities files have `package utilities;`
- [ ] All 2_Managers files have `package managers;`
- [ ] All 4_Entities files have `package entities;`
- [ ] All 3_Controllers files have `package controllers;`
- [ ] All 6_Physics files have `package physics;`
- [ ] All 7_AI files have `package ai;`
- [ ] All 5_Animation files have correct packages
- [ ] All 1_Framework files have `package framework;`
- [ ] Code compiles without import errors
- [ ] All tests pass
- [ ] Changes committed to GitHub

---

## TOTAL FILES: 620+ ✓
**Files organized into folders by name**  
**Files remaining to fix: ~570+**

