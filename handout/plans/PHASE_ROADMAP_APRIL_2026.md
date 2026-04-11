# COMPREHENSIVE PHASE ROADMAP - APRIL 2026
**Status Date**: April 6, 2026  
**Project Status**: 85% Complete - Entry into Final Polish & Integration Phase

---

## EXECUTIVE SUMMARY

The game framework is **fully functional** with 66 asset folders loaded, complete rendering system, and core gameplay. The **critical blocker** is handling button sprite sheets with **mixed frame sizes** (2 small frames + 2 large frames). Next phases focus on:

1. **PHASE 8** (CURRENT BLOCKER): Button mixed-frame parsing & render system
2. **PHASE 9**: Menu system & UI navigation
3. **PHASE 10**: Advanced gameplay features
4. **PHASE 11**: Polish, optimization & final integration
5. **PHASE 12**: Testing, debugging & submission prep

---

## PHASE 8: BUTTON MIXED-FRAME SYSTEM (CRITICAL - BLOCKER)

### Problem Statement
Button sprite sheets contain **4 frames in 2 different sizes**:
- Frames 0-1: Small size (e.g., 64×64)
- Frames 2-3: Large size (e.g., 96×96 or different aspect ratio)

Current code assumes **uniform frame sizes**, causing:
- ❌ Incorrect frame extraction
- ❌ Rendering artifacts
- ❌ Animation glitches

### Phase 8A: Asset Analysis & Mapping

**Objective**: Understand exact dimensions for each button variant

**Tasks**:
1. **View all button sprite sheets** using image viewing tool
   - `01_GUI_Button_Hollow_Rounded_Variant_01_4States2Frames_Size_Variant.png`
   - `02-10` variants
   - Hollow variants (11-20) if different

2. **Document frame layout**:
   ```
   For each button:
   - Total image dimensions: W × H
   - Frame 0 dimensions: w0 × h0
   - Frame 1 dimensions: w1 × h1
   - Frame 2 dimensions: w2 × h2
   - Frame 3 dimensions: w3 × h3
   - Layout pattern: (horizontal|vertical|mixed)
   ```

3. **Create metadata file**: `BUTTON_FRAMESIZE_MANIFEST.json`
   ```json
   {
     "buttons": {
       "variant_01": {
         "file": "01_GUI_Button_...",
         "total": "288x256",
         "frames": [
           {"state": "idle", "frame": 0, "dims": "64x64", "pos": "0,0"},
           {"state": "idle", "frame": 1, "dims": "64x64", "pos": "64,0"},
           {"state": "hover", "frame": 0, "dims": "96x96", "pos": "128,0"},
           ...
         ]
       }
     }
   }
   ```

**Deliverables**:
- ✅ Visual analysis of all 10 button variants
- ✅ Framesize manifest JSON
- ✅ Frame coordinate mapping document

**Estimated Time**: 2 hours (image analysis)

---

### Phase 8B: Enhanced Sprite Parser

**Objective**: Create flexible frame extraction supporting mixed sizes

**New Class**: `MixedFrameSpriteLoader`

```java
public class MixedFrameSpriteLoader {
    private BufferedImage sheet;
    private List<FrameDefinition> frames;
    
    // Frame definition with explicit size
    public static class FrameDefinition {
        int x, y, width, height;
        String state;
        int frameIndex;
    }
    
    // Load from metadata
    public static MixedFrameSpriteLoader loadFromManifest(String manifestPath)
    
    // Extract single frame with correct dimensions
    public BufferedImage extractFrame(int frameIndex)
    
    // Get all frames for a state
    public List<BufferedImage> getStateFrames(String state)
    
    // Get frame dimensions
    public Dimension getFrameDimensions(int frameIndex)
}
```

**Integration Points**:
- Updated `AnimationAndSpriteLoader.loadGUIButtonStates()`
- Use `MixedFrameSpriteLoader` instead of `HorizontalSpritesheet` for buttons
- Return frame dimensions alongside images

**Modifications to AnimationAndSpriteLoader.java**:

```java
// BEFORE (assumes uniform frames)
public static Map<String, HorizontalSpritesheetLoader> loadGUIButtonStates(...) {
    HorizontalSpritesheet loader = new HorizontalSpritesheet(image, 2); // assumes 2 frames
    // WRONG if button has mixed sizes!
}

// AFTER (supports mixed sizes)
public static Map<String, MixedFrameSpriteLoader> loadGUIButtonStates(...) {
    MixedFrameSpriteLoader loader = MixedFrameSpriteLoader.loadFromManifest(
        buttonVariantPath + "/manifest.json"
    );
    return Map.of(
        "idle", new StatefulFrameLoader(loader, "idle"),
        "hover", new StatefulFrameLoader(loader, "hover"),
        "press", new StatefulFrameLoader(loader, "press"),
        "disabled", new StatefulFrameLoader(loader, "disabled")
    );
}
```

**Deliverables**:
- ✅ MixedFrameSpriteLoader class (200 lines)
- ✅ StatefulFrameLoader wrapper (100 lines)
- ✅ Updated AnimationAndSpriteLoader methods
- ✅ Compilation successful (0 errors)

**Estimated Time**: 3 hours (coding + testing)

---

### Phase 8C: Button Renderer Enhancement

**Objective**: Render buttons with correct frame sizing

**New Class**: `MixedSizeButtonRenderer`

```java
public class MixedSizeButtonRenderer {
    private MixedFrameSpriteLoader frameLoader;
    private String currentState = "idle";
    private int frameIndex = 0;
    private long frameTime = 0;
    
    public void render(Graphics2D g2d, int x, int y, long deltaTime) {
        // Get current frame
        BufferedImage frame = frameLoader.extractFrame(frameIndex);
        Dimension dims = frameLoader.getFrameDimensions(frameIndex);
        
        // Render at position with correct dimensions
        g2d.drawImage(frame, x, y, dims.width, dims.height, null);
        
        // Update frame animation
        frameTime += deltaTime;
        if (frameTime > FRAME_INTERVAL) {
            frameIndex = (frameIndex + 1) % frameLoader.getFrameCount();
            frameTime = 0;
        }
    }
    
    public void setState(String state) {
        this.currentState = state;
        this.frameIndex = 0;
    }
}
```

**Integration with UI System**:
- Update `GameGUIPanel.renderButtons()` to use `MixedSizeButtonRenderer`
- Adjust button hitboxes based on actual frame dimensions
- Support smooth transitions between states

**Deliverables**:
- ✅ MixedSizeButtonRenderer class (250 lines)
- ✅ Integration into GameGUIPanel
- ✅ Button state transitions working
- ✅ Rendering tests pass

**Estimated Time**: 2 hours

---

### Phase 8D: Testing & Validation

**Objective**: Verify button rendering works correctly

**Test Cases**:
1. ✅ Load all 10 button variants from manifest
2. ✅ Render each button variant in all 4 states
3. ✅ Verify frame dimensions match manifest
4. ✅ Test state transitions (idle → hover → press → disabled)
5. ✅ Verify hitbox accuracy matches frame size
6. ✅ Performance check (no FPS drops)

**Test Class**: `MixedFrameButtonTester.java`

```java
public class MixedFrameButtonTester {
    public static void main(String[] args) {
        // Load manifest
        BUTTON_FRAMESIZE_MANIFEST manifest = loadManifest();
        
        // Test each variant
        for (String variant : manifest.buttons.keySet()) {
            System.out.println("Testing " + variant);
            MixedFrameSpriteLoader loader = 
                MixedFrameSpriteLoader.loadFromManifest(variant);
            
            // Verify frame count
            assert loader.getFrameCount() == 4;
            
            // Verify each frame can be extracted
            for (int i = 0; i < 4; i++) {
                BufferedImage frame = loader.extractFrame(i);
                assert frame != null;
                assert frame.getWidth() > 0 && frame.getHeight() > 0;
            }
            
            System.out.println("✓ " + variant + " passed");
        }
    }
}
```

**Deliverables**:
- ✅ Test class created
- ✅ All tests pass (green)
- ✅ Performance metrics documented
- ✅ Edge cases handled

**Estimated Time**: 1.5 hours

---

**PHASE 8 TOTAL**: ~8.5 hours | **Status**: CRITICAL PATH

---

## PHASE 9: MENU SYSTEM & UI NAVIGATION

### Problem Statement
Game currently boots directly into gameplay. Need:
- Start menu
- Pause menu
- Settings screen
- Level selection
- Game over screen

### Phase 9A: MenuScreen Implementation

**New Class**: `MenuScreen extends GameScreen`

```java
public class MenuScreen extends GameScreen {
    private List<MixedSizeButtonRenderer> buttons;
    private int selectedButton = 0;
    private MenuState state = MenuState.MAIN;
    
    enum MenuState {
        MAIN, SETTINGS, LEVEL_SELECT, PAUSED, GAME_OVER
    }
    
    @Override
    public void render(Graphics2D g2d) {
        // Draw background
        g2d.drawImage(menuBackground, 0, 0, null);
        
        // Draw buttons
        for (int i = 0; i < buttons.size(); i++) {
            MixedSizeButtonRenderer btn = buttons.get(i);
            String state = (i == selectedButton) ? "hover" : "idle";
            btn.setState(state);
            btn.render(g2d, buttonX[i], buttonY[i], deltaTime);
        }
    }
    
    @Override
    public void handleInput(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                selectedButton = (selectedButton - 1 + buttons.size()) % buttons.size();
                break;
            case KeyEvent.VK_DOWN:
                selectedButton = (selectedButton + 1) % buttons.size();
                break;
            case KeyEvent.VK_ENTER:
                handleButtonClick(selectedButton);
                break;
            case KeyEvent.VK_ESCAPE:
                handleBackButton();
                break;
        }
    }
    
    private void handleButtonClick(int index) {
        switch (state) {
            case MAIN:
                if (index == 0) startGame();
                else if (index == 1) showSettings();
                else if (index == 2) showLevelSelect();
                else if (index == 3) System.exit(0);
                break;
            // ... other states
        }
    }
}
```

**Deliverables**:
- ✅ MenuScreen class (400 lines)
- ✅ Button layout & positioning
- ✅ State transitions
- ✅ Input handling

**Estimated Time**: 3 hours

---

### Phase 9B: PauseScreen & GameOverScreen

**New Classes**:
- `PauseScreen extends GameScreen`
- `GameOverScreen extends GameScreen`

**Features**:
- Resume game
- Return to menu
- Restart level
- View score
- Show final stats

**Deliverables**:
- ✅ PauseScreen (200 lines)
- ✅ GameOverScreen (250 lines)
- ✅ Integration with Game.java
- ✅ Input handling

**Estimated Time**: 2 hours

---

### Phase 9C: Settings & Options

**New Class**: `SettingsScreen extends GameScreen`

**Options**:
- Volume slider (music/sfx)
- Difficulty level selector
- Resolution/fullscreen
- Keybinding display
- Graphics quality

**Deliverables**:
- ✅ SettingsScreen (300 lines)
- ✅ Settings persistence (JSON file)
- ✅ Apply/Cancel buttons
- ✅ Default settings loaded

**Estimated Time**: 2.5 hours

---

**PHASE 9 TOTAL**: ~7.5 hours | **Status**: HIGH PRIORITY

---

## PHASE 10: ADVANCED GAMEPLAY FEATURES

### Phase 10A: Enemy AI Enhancement

**Current State**: Enemies spawn every 2s, move left linearly

**Enhancements**:
1. **Enemy Types**:
   - Basic Drone (current)
   - Tank (slow, high health)
   - Scout (fast, low health)
   - Boss (very high health, attack pattern)

2. **Behavior Patterns**:
   - Random movement with pauses
   - Predictive dodging
   - Formation attacks
   - Charging attacks

**New Class**: `AdvancedEnemyAI`

```java
public class AdvancedEnemyAI {
    public enum EnemyType { BASIC, TANK, SCOUT, BOSS }
    
    private EnemyType type;
    private int health;
    private int attackPattern;
    
    public void updateBehavior(GameState state) {
        switch (type) {
            case BASIC:
                moveLinear();
                break;
            case TANK:
                moveSlowChargeAttack();
                break;
            case SCOUT:
                moveRandomFast();
                break;
            case BOSS:
                executeBossPattern();
                break;
        }
    }
    
    private void executeBossPattern() {
        // Complex attack pattern
        // Dodge bullets, charge at player, summon minions
    }
}
```

**Deliverables**:
- ✅ Enemy type system (300 lines)
- ✅ 4 distinct behaviors
- ✅ AI state machine
- ✅ Difficulty scaling

**Estimated Time**: 4 hours

---

### Phase 10B: Power-up System

**Objective**: Add collectible power-ups

**Power-up Types**:
1. Health Pack (+25 HP)
2. Shield (+50 HP + 10s invulnerability)
3. Rapid Fire (2× bullet speed for 10s)
4. Spread Shot (fires 3 bullets instead of 1)
5. Score Multiplier (2× points for 20s)

**New Class**: `PowerUp`

```java
public class PowerUp {
    enum Type { HEALTH, SHIELD, RAPID_FIRE, SPREAD_SHOT, MULTIPLIER }
    
    private Type type;
    private int x, y;
    private boolean active = true;
    
    public void render(Graphics2D g2d) {
        g2d.drawImage(getTypeImage(), x, y, 32, 32, null);
    }
    
    public void collect() {
        active = false;
        applyEffect();
    }
    
    private void applyEffect() {
        // Apply to game state
    }
}
```

**Integration**:
- Add to enemy drop system (30% chance on death)
- Collision detection with player
- Effect duration tracking
- HUD indicator display

**Deliverables**:
- ✅ PowerUp class (250 lines)
- ✅ 5 power-up types
- ✅ Effect system
- ✅ Drop probability tuning

**Estimated Time**: 3 hours

---

### Phase 10C: Weapon Progression

**Objective**: Unlock better weapons through progression

**Progression**:
- Level 1: Basic Pistol (1 bullet/shot)
- Level 2: Dual Pistol (2 bullets/shot)
- Level 3: Rifle (3 bullets/shot, faster)
- Level 4: Plasma Cannon (large projectile, slow)

**New Class**: `WeaponUpgrade`

```java
public class WeaponUpgrade {
    enum Weapon { PISTOL, DUAL_PISTOL, RIFLE, PLASMA }
    
    private Weapon current = Weapon.PISTOL;
    
    public void fire() {
        switch (current) {
            case PISTOL: fireBasic(); break;
            case DUAL_PISTOL: fireDual(); break;
            case RIFLE: fireTriple(); break;
            case PLASMA: fireCharged(); break;
        }
    }
}
```

**Deliverables**:
- ✅ Weapon progression system
- ✅ 4 weapon variants
- ✅ Upgrade animations
- ✅ Balance tuning

**Estimated Time**: 2.5 hours

---

### Phase 10D: Level Progression & Difficulty

**Objective**: Implement 3-level campaign with difficulty scaling

**Level Changes**:
- Level 1 → Level 2: 2x enemy spawn rate
- Level 2 → Level 3: 3x spawn rate + boss enemy
- Difficulty modifier based on score

**New System**: `DifficultyScaler`

```java
public class DifficultyScaler {
    private int level;
    private int score;
    
    public float getSpawnRateMultiplier() {
        return 1.0f + (level * 0.5f) + (score / 5000);
    }
    
    public float getEnemyHealthMultiplier() {
        return 1.0f + (level * 0.3f);
    }
    
    public float getEnemyDamageMultiplier() {
        return 1.0f + (level * 0.2f);
    }
}
```

**Deliverables**:
- ✅ Difficulty scaling system
- ✅ 3-level progression
- ✅ Boss enemy implementation
- ✅ Balance metrics

**Estimated Time**: 2 hours

---

**PHASE 10 TOTAL**: ~11.5 hours | **Status**: MEDIUM PRIORITY

---

## PHASE 11: POLISH & OPTIMIZATION

### Phase 11A: Visual Polish

**Tasks**:
1. Add screen shake on bullet hit
2. Add death splash animation
3. Particle effects for explosions
4. Screen fade transitions
5. Button hover/press animations
6. HUD animation polish

**Deliverables**:
- ✅ Screen effects system
- ✅ Particle engine
- ✅ Animation polish
- ✅ Visual feedback complete

**Estimated Time**: 3 hours

---

### Phase 11B: Audio Integration

**Tasks**:
1. Load sound effects loader
2. Add sound files for:
   - Background music
   - Bullet fire
   - Enemy death
   - Player damage
   - Game over
   - Menu select
3. Volume control integration

**New Class**: `AudioManager`

**Deliverables**:
- ✅ AudioManager class
- ✅ 6+ sound effects
- ✅ Volume control
- ✅ Looping music

**Estimated Time**: 2.5 hours

---

### Phase 11C: Performance Optimization

**Optimization Passes**:
1. **Memory**: Object pooling for bullets/enemies
2. **Rendering**: Batch rendering of sprites
3. **CPU**: Spatial partitioning for collision checks
4. **Caching**: Pre-load all images at startup

**Metrics**:
- Target: 60 FPS stable
- Max memory: 256 MB
- Load time: < 2 seconds

**Deliverables**:
- ✅ Object pooling (400 lines)
- ✅ Spatial hash grid
- ✅ Batch rendering
- ✅ Benchmark results

**Estimated Time**: 4 hours

---

### Phase 11D: UI Polish & Accessibility

**Tasks**:
1. High contrast mode
2. Larger text options
3. Keyboard-only mode (no mouse required)
4. Colorblind mode (adjust palette)
5. Pause prompt reminders
6. Clearer instructions

**Deliverables**:
- ✅ Accessibility options menu
- ✅ 3 contrast levels
- ✅ Font size options
- ✅ Color remapping

**Estimated Time**: 2.5 hours

---

**PHASE 11 TOTAL**: ~12 hours | **Status**: FINAL POLISH

---

## PHASE 12: TESTING, DEBUGGING & SUBMISSION

### Phase 12A: Comprehensive Testing

**Test Categories**:
1. **Unit Tests** (Game mechanics)
   - Enemy spawning
   - Bullet collision
   - Scoring system
   - State transitions

2. **Integration Tests** (System interactions)
   - Menu → Game → Pause → Resume
   - Level transitions
   - Button rendering
   - Audio playback

3. **Performance Tests**
   - Frame rate stability
   - Memory leak detection
   - Load time measurement

4. **Gameplay Tests**
   - Win condition
   - Lose condition
   - Difficulty scaling
   - Power-up effects

**Deliverables**:
- ✅ Test suite (500+ lines)
- ✅ All tests passing
- ✅ Code coverage report
- ✅ Performance baseline

**Estimated Time**: 4 hours

---

### Phase 12B: Bug Fixing & Polish

**Priority Bugs**:
1. Crash on level transition
2. Button clipping issues
3. Audio sync problems
4. Collision edge cases
5. Memory leaks

**Process**:
- Reproduce each bug
- Fix in minimal code
- Regression testing
- Performance check

**Deliverables**:
- ✅ All critical bugs fixed
- ✅ 0 compilation errors/warnings
- ✅ Stable gameplay
- ✅ Smooth transitions

**Estimated Time**: 3 hours

---

### Phase 12C: Documentation & Code Comments

**Documentation**:
1. Code comments for complex logic
2. Class-level JavaDoc
3. Method signatures documented
4. Game design document final version
5. Technical architecture guide
6. Installation instructions

**Deliverables**:
- ✅ Complete code documentation
- ✅ Game design document (final)
- ✅ README.md for running game
- ✅ Architecture diagrams

**Estimated Time**: 2.5 hours

---

### Phase 12D: Submission Preparation

**Final Checklist**:
- ✅ Code compiles without errors/warnings
- ✅ All assets loaded and displayed
- ✅ Game runs without crashes
- ✅ All features documented
- ✅ README included
- ✅ Source code properly formatted
- ✅ No hardcoded test code
- ✅ Performance verified
- ✅ Testing report generated
- ✅ Legal/asset attribution

**Deliverables**:
- ✅ Final source ZIP
- ✅ Build instructions
- ✅ Test results report
- ✅ Submission metadata

**Estimated Time**: 2 hours

---

**PHASE 12 TOTAL**: ~11.5 hours | **STATUS**: FINAL DELIVERY

---

## COMPLETE PROJECT TIMELINE

| Phase | Title | Hours | Status | Blocker |
|-------|-------|-------|--------|---------|
| 8 | Button Mixed-Frame System | 8.5 | 🔴 CRITICAL | Need asset analysis |
| 9 | Menu & UI Navigation | 7.5 | ⏳ WAITING | Phase 8 completion |
| 10 | Advanced Gameplay | 11.5 | ⏳ WAITING | Phase 9 completion |
| 11 | Polish & Optimization | 12 | ⏳ WAITING | Phase 10 completion |
| 12 | Testing & Submission | 11.5 | ⏳ WAITING | Phase 11 completion |
| **TOTAL** | **All Phases** | **50.5 hours** | - | - |

---

## IMMEDIATE ACTION ITEMS (NEXT 24 HOURS)

### ✅ Priority 1: Complete Phase 8A (Asset Analysis)
1. **View all button sprite sheets** in assets-manifest.json
2. **Document frame dimensions** for each button variant
3. **Create BUTTON_FRAMESIZE_MANIFEST.json**

### ✅ Priority 2: Code Phase 8B (Parser)
1. Implement `MixedFrameSpriteLoader` class
2. Create frame definition system
3. Update `AnimationAndSpriteLoader`

### ✅ Priority 3: Test Phase 8C-8D
1. Render buttons with correct dimensions
2. Verify all states work
3. Pass validation tests

### ✅ Priority 4: Branch Finalization
1. Compile & verify 0 errors
2. Document all changes
3. Update repo memory with Phase 8 completion

---

## SUCCESS CRITERIA

### Phase 8 (Current): ✅ Button System
- [ ] All button variants parse correctly
- [ ] Mixed-size frames render properly
- [ ] State transitions smooth
- [ ] 0 compilation errors
- [ ] All asset paths logged

### Phase 9: ✅ Menus
- [ ] Menu navigation works
- [ ] Pause system functional
- [ ] Settings apply correctly
- [ ] All screens render

### Phase 10: ✅ Gameplay
- [ ] 4 enemy types spawning
- [ ] Power-ups collectable
- [ ] Weapons upgrade properly
- [ ] Difficulty scales correctly

### Phase 11: ✅ Polish
- [ ] 60 FPS stable
- [ ] All effects working
- [ ] Audio plays
- [ ] Accessible to all users

### Phase 12: ✅ Submission
- [ ] All tests pass
- [ ] 0 critical bugs
- [ ] Complete documentation
- [ ] Ready to submit

---

## RISK ASSESSMENT

| Risk | Impact | Likelihood | Mitigation |
|------|--------|------------|-----------|
| Button frame parsing fails | HIGH | HIGH | Phase 8A analysis first |
| Performance drops with effects | MEDIUM | MEDIUM | Early optimization (Phase 11C) |
| Audio system crashes | MEDIUM | LOW | Fallback silent mode |
| Menu styling issues | LOW | MEDIUM | Test on target system |
| Compilation errors | HIGH | LOW | Daily compilation checks |

---

## NOTES & CONSIDERATIONS

1. **Critical Path**: Phase 8 is the project's critical path blocker. Must complete before proceeding.

2. **Time Estimates**: Based on current development pace. May vary ±20% based on complexity.

3. **Asset Quality**: All 1174 assets are loaded and verified. No additional asset creation needed.

4. **Code Quality**: Maintain 0 compilation errors throughout. Run `javac -cp "lib/*;src" ...` after each major change.

5. **Testing Philosophy**: Test each phase completion before moving to next.

6. **Documentation**: Keep repo memory updated with completion status of each phase.

---

## FINAL DEADLINE

**Target Submission**: April 20, 2026 (14 days remaining)

**Buffer**: 4 days for unexpected issues/testing revisions

**Recommended Completion**: April 16, 2026

---

**Generated**: 2026-04-06  
**Next Review**: After Phase 8 completion  
**Contact**: Game Development Team CSCU9N6
