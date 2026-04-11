# 🎨 COMPREHENSIVE GUI SYSTEM UNIFIED IMPLEMENTATION PLAN
## Complete Raster Graphics Conversion Using AnimationAndSpriteLoader Nested Classes

**Document Version:** 1.0  
**Date Created:** April 3, 2026  
**Status:** DETAILED PLANNING PHASE  
**Target Completion:** 16 Phase Files + Entity.java  

---

## 📋 EXECUTIVE SUMMARY

This document outlines a **complete architectural redesign** of the GUI system to leverage ALL nested classes from `AnimationAndSpriteLoader`. Rather than creating separate GUI implementations, each screen will extend `AnimationAndSpriteLoader` and utilize its comprehensive nested class ecosystem.

**Key Principle:** One framework, infinite possibilities
- ✅ No vector graphics (Graphics2D forbidden)
- ✅ Raster-only rendering (PNG images exclusively)
- ✅ Unified state management via nested classes
- ✅ Modular component architecture
- ✅ Reusable animation systems

---

## 📊 NESTED CLASS INVENTORY & GUI APPLICATION

### **TIER 0: FOUNDATION CLASSES**

#### 1. **TileRegistry / Level1TileRegistry / Level2TileRegistry**
- **Purpose:** Character-to-asset mapping system
- **GUI Use Cases:**
  - ✓ Build GUI frames from character grids (tile-based UI construction)
  - ✓ Create button grids (button layouts using character codes)
  - ✓ Menu grid systems (navigation grids)
  - ✓ Inventory grid systems (item slots as registry codes)

#### 2. **SpriteMetadata**
- **Purpose:** Analyze spritesheet properties and complexity
- **GUI Use Cases:**
  - ✓ Validate GUI asset dimensions before loading
  - ✓ Determine optimal frame counts for animated buttons
  - ✓ Suggest appropriate animation timings
  - ✓ Report on asset complexity (simple vs complex graphics)

---

### **TIER 1: CORE PHYSICS & STATE SYSTEMS**

#### 3. **PhysicsUnitSystem**
- **Purpose:** Complete physics simulation framework
- **GUI Use Cases:**
  - ✓ Physics-based menu transitions (buttons fall/bounce)
  - ✓ Draggable UI elements with realistic physics
  - ✓ Item inventory with gravity simulation
  - ✓ Animated panels with momentum
  - ✓ Collision-based UI interactions

#### 4. **Vector2D** (Nested in PhysicsUnitSystem)
- **Purpose:** 2D vector mathematics
- **GUI Use Cases:**
  - ✓ Button position interpolation (smooth movement)
  - ✓ Velocity calculations for UI animations
  - ✓ Direction-based menu navigation
  - ✓ Lerp-based smooth transitions

#### 5. **PhysicsBody** (Nested in PhysicsUnitSystem)
- **Purpose:** Kinematic physics entity
- **GUI Use Cases:**
  - ✓ Interactive button physics bodies
  - ✓ Collision detection between UI elements
  - ✓ Draggable window bodies
  - ✓ Character card animation with physics

#### 6. **StateTransition**
- **Purpose:** Entity state machine with transitions
- **GUI Use Cases:**
  - ✓ Button states (normal → hover → pressed → released)
  - ✓ Menu transitions (MENU → CHARACTER_SELECT → GAMEPLAY)
  - ✓ Dialog states (closed → opening → open → closing)
  - ✓ Screen state machine for navigation
  - ✓ Animation state management per screen

---

### **TIER 2: INPUT & INTERACTION SYSTEMS**

#### 7. **InputHandler**
- **Purpose:** Unified keyboard/mouse input processing
- **GUI Use Cases:**
  - ✓ Keyboard navigation (arrow keys for menu selection)
  - ✓ Mouse click detection on buttons
  - ✓ Mouse hover tracking for button highlights
  - ✓ Key press validation (Enter to confirm, Esc to cancel)
  - ✓ Multi-input handling (simultaneous key presses)

#### 8. **PlayerController** (Extends EntityAnimationController)
- **Purpose:** Player character animation and movement control
- **GUI Use Cases:**
  - ✓ Character idle animations in selection screen
  - ✓ Character preview animations in card displays
  - ✓ Animated player portraits in status bar
  - ✓ Character stat displays with animation
  - ✓ Walking/running animations during transitions

#### 9. **EnemyController** (Extends EntityAnimationController)
- **Purpose:** Enemy AI and animation
- **GUI Use Cases:**
  - ✓ Enemy portrait animations in dialogue
  - ✓ Boss character animations in title screen
  - ✓ Enemy status display with animated portraits
  - ✓ Threat level indicator animations
  - ✓ Enemy introduction sequences

#### 10. **BossController** (Extends EntityAnimationController)
- **Purpose:** Boss character advanced AI and animations
- **GUI Use Cases:**
  - ✓ Boss phase transition animations
  - ✓ Boss preview animations in story/intro screens
  - ✓ Boss defeat celebration animations
  - ✓ Boss health bar animations
  - ✓ Boss special attack visual feedback

#### 11. **EnvironmentController**
- **Purpose:** Background, parallax, and environmental animations
- **GUI Use Cases:**
  - ✓ Parallax scrolling menu backgrounds (2-3 layers)
  - ✓ Dynamic weather effects in menus
  - ✓ Day/night cycle backgrounds
  - ✓ Living breathing backgrounds
  - ✓ Screen transition effects (screen shake, fade)

#### 12. **ProjectileController** (Extends EntityAnimationController)
- **Purpose:** Projectile animation and physics
- **GUI Use Cases:**
  - ✓ Animated "spell casting" effects in menus
  - ✓ Item throw animations in inventory
  - ✓ Bullet/projectile animations in weapon select
  - ✓ Visual feedback for projectile weapons
  - ✓ Particle trajectory visualization

#### 13. **VFXController** (Extends EntityAnimationController)
- **Purpose:** Visual effects (particles, explosions, etc.)
- **GUI Use Cases:**
  - ✓ Button click particle effects
  - ✓ Power-up notification sparkles
  - ✓ Achievement unlock explosion effects
  - ✓ Hover highlight particle trails
  - ✓ Transition screen effects (screen wipe, dissolve)

---

### **TIER 3: GAME STATE & PROGRESSION**

#### 14. **GameStateManager**
- **Purpose:** Global game state and progression management
- **GUI Use Cases:**
  - ✓ Screen state management (MENU, PAUSED, GAME_OVER, etc.)
  - ✓ Score and statistics tracking
  - ✓ Achievement unlock management
  - ✓ Persistent state between screens
  - ✓ Game mode selection (single player, multiplayer, etc.)

---

### **TIER 4: ASSET LOADERS (All Extend AssetType)**

#### 15. **SingleSpriteLoader**
- **Purpose:** Load single non-animated sprites
- **GUI Use Cases:**
  - ✓ Button backgrounds (static images)
  - ✓ Menu logos and titles
  - ✓ Window borders and frames
  - ✓ Icon assets (menu icons, status icons)
  - ✓ Static background images

#### 16. **HorizontalSpritesheetLoader**
- **Purpose:** Horizontal animated spritesheets (1 row of frames)
- **GUI Use Cases:**
  - ✓ Button press animations (normal → pressed → released, left to right)
  - ✓ Menu slide-in animations
  - ✓ Health bar depletion animations
  - ✓ Experience bar fill animations
  - ✓ Loading bar progression animations
  - ✓ Horizontal menu transitions

#### 17. **VerticalSpritesheetLoader**
- **Purpose:** Vertical animated spritesheets (1 column of frames)
- **GUI Use Cases:**
  - ✓ Vertical menu selection highlight moving down
  - ✓ Character portrait entry animations (appearing top to bottom)
  - ✓ Floating popup animations (rising vertically)
  - ✓ Typing text animations (letter by letter vertically arranged)
  - ✓ Dropdown menu opening animations

#### 18. **GridSpritesheetLoader**
- **Purpose:** 2D grid spritesheets (rows × columns)
- **GUI Use Cases:**
  - ✓ Character direction variants (8-directional portraits)
  - ✓ Button state grid (normal, hover, pressed, disabled for different themes)
  - ✓ Inventory item grid previews
  - ✓ Game tile grid displays (visual representation)
  - ✓ Multi-state icon sheets (2 states = 2 columns, 4 variations = 2x2 grid)

#### 19. **GridFrameAnimationLoader**
- **Purpose:** Grid animation with per-frame timing control
- **GUI Use Cases:**
  - ✓ Animated button effects (timed frame-by-frame)
  - ✓ Character animation sequences (walk cycle, attack, defeat)
  - ✓ Chest/treasure open animations
  - ✓ Level completion celebration animations
  - ✓ Boss entrance animations

#### 20. **SequenceFrameAnimationLoader**
- **Purpose:** Load sequence of separate image files
- **GUI Use Cases:**
  - ✓ Cinematic intro sequences
  - ✓ Cutscene animations (multiple full-screen images)
  - ✓ Tutorial sequences (step-by-step image progression)
  - ✓ Story presentation (narrative slide shows)
  - ✓ Animated credits sequences

#### 21. **StateVariantLoader**
- **Purpose:** Multiple animation sets for different states
- **GUI Use Cases:**
  - ✓ Character status (healthy, injured, poisoned, frozen)
  - ✓ Button states (enabled, disabled, selected, focused)
  - ✓ Menu sections (normal, expanded, contracted)
  - ✓ Difficulty levels visual representation
  - ✓ Game difficulty indicators

---

## 🎯 GUI SCREEN IMPLEMENTATION PHASES

### **PHASE STRUCTURE**

Each phase builds on previous phases and introduces new nested class integrations:

```
PHASE 1  → Basic screen setup + StateTransition
PHASE 2  → Character idle + PlayerController
PHASE 3  → Status bar + HorizontalSpritesheetLoader
PHASE 4  → Numeric display + GridSpritesheetLoader
PHASE 5  → Buttons + GridFrameAnimationLoader + VFXController
PHASE 6  → Decoration + EnvironmentController + ParallaxScrolling
PHASE 7  → Inventory + GridSpritesheetLoader + Physics interactions
PHASE 8  → Minimap + Level1/2TileRegistry for tile rendering
PHASE 9  → Dialogue + SequenceFrameAnimationLoader + TextAnimations
PHASE 10 → Tooltips + VFXController + Particle effects
PHASE 11 → Notifications + StateVariantLoader + Toast animations
PHASE 12 → Quest tracker + VerticalSpritesheetLoader + Animated lists
PHASE 13 → Main menu + All systems + Full integration
PHASE 14 → Pause menu + GameStateManager integration
PHASE 15 → Settings screen + InputHandler + Configuration UI
```

---

## 📋 DETAILED PHASE BREAKDOWN

### **PHASE 1: Main Pause Menu Base**
**File:** `PauseMenuScreen.java`

**Objectives:**
1. ✅ Extend `AnimationAndSpriteLoader`
2. ✅ Integrate `StateTransition` for menu state management
3. ✅ Integrate `InputHandler` for keyboard navigation
4. ✅ Load GUI frame background (raster PNG only)
5. ✅ Implement basic menu structure

**Nested Classes Used:**
- `StateTransition` (menu states → idle, navigating, selecting)
- `InputHandler` (arrow key navigation, Enter/Esc)
- `SingleSpriteLoader` (menu background frame)

**Code Structure:**
```java
public class PauseMenuScreen extends AnimationAndSpriteLoader {
    // NESTED CLASSES
    private AnimationAndSpriteLoader.StateTransition menuState;
    private AnimationAndSpriteLoader.InputHandler inputHandler;
    private AnimationAndSpriteLoader.SingleSpriteLoader bgLoader;
    
    // MENU PROPERTIES
    private int selectedOption = 0;
    private String[] options = {"RESUME", "SETTINGS", "QUIT"};
    
    // INITIALIZATION
    public void initializeScreen() {
        menuState = new StateTransition();
        menuState.addTransition("idle", "selecting", () -> true);
        menuState.addTransition("selecting", "idle", () -> true);
        
        inputHandler = new InputHandler();
        bgLoader = new SingleSpriteLoader();
        bgLoader.load(GUI_BASE + "pause_menu_bg.png");
    }
    
    // RENDER
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        // Draw background (raster only)
        BufferedImage bg = bgLoader.getFrame(0);
        if (bg != null) g.drawImage(bg, 0, 0, width, height, null);
        
        // Draw menu options (raster text using image fonts)
        drawMenuOptions(g, width, height);
        g.dispose();
        return buffer;
    }
    
    // UPDATE
    public void update(float deltaTime) {
        if (inputHandler.isKeyDown(KeyEvent.VK_UP)) selectedOption--;
        if (inputHandler.isKeyDown(KeyEvent.VK_DOWN)) selectedOption++;
        if (inputHandler.isKeyDown(KeyEvent.VK_ENTER)) selectOption();
    }
}
```

**Key Deliverable:** ✅ Complete menu foundation with state tracking

---

### **PHASE 2: Character Idle Screen**
**File:** `Phase2CharacterIdleScreen.java`

**Objectives:**
1. ✅ Display 3 player characters with idle animations
2. ✅ Integrate `PlayerController` for character animation
3. ✅ Use `HorizontalSpritesheetLoader` for idle animation frames
4. ✅ Implement character selection via keyboard/mouse
5. ✅ Show character stats/attributes

**Nested Classes Used:**
- `PlayerController` (character animation and state)
- `HorizontalSpritesheetLoader` (horizontal idle animation frames)
- `SingleSpriteLoader` (character portraits, stat backgrounds)
- `InputHandler` (character selection input)
- `StateTransition` (character selected/unselected states)

**Code Structure:**
```java
public class Phase2CharacterIdleScreen extends AnimationAndSpriteLoader {
    // CHARACTER CONTROLLERS
    private PlayerController biker;
    private PlayerController punk;
    private PlayerController cyborg;
    
    // ANIMATION LOADERS
    private HorizontalSpritesheetLoader bikerIdleAnim;
    private HorizontalSpritesheetLoader punkIdleAnim;
    private HorizontalSpritesheetLoader cyborgIdleAnim;
    
    // STATE
    private int selectedCharacter = 0;
    private AnimationAndSpriteLoader.StateTransition charState;
    
    public void initializeScreen() {
        // Initialize controllers
        biker = new PlayerController();
        punk = new PlayerController();
        cyborg = new PlayerController();
        
        // Load idle animations (8 frames each)
        bikerIdleAnim = new HorizontalSpritesheetLoader();
        bikerIdleAnim.load(PLAYER_BASE + "biker/idle_8frames.png", 8);
        // Similar for punk and cyborg
        
        charState = new StateTransition();
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        // Draw background
        g.setColor(new java.awt.Color(30, 30, 40));
        g.fillRect(0, 0, width, height);
        
        // Draw characters with idle animations
        BufferedImage bikerFrame = bikerIdleAnim.getNextFrame(deltaTime);
        if (bikerFrame != null) g.drawImage(bikerFrame, 50, 100, null);
        
        // Draw character names and selection highlight
        drawCharacterInfo(g, width, height);
        g.dispose();
        return buffer;
    }
}
```

**Key Deliverable:** ✅ Animated character selection with 3 playable characters

---

### **PHASE 3: Status Bar Screen**
**File:** `Phase3StatusBarScreen.java`

**Objectives:**
1. ✅ Display player health bar with animated depletion
2. ✅ Show mana/energy bar with fill animations
3. ✅ Integrate `HorizontalSpritesheetLoader` for bar animations
4. ✅ Real-time stat updates (HP, Mana, Experience)
5. ✅ Status effects display (poison, freeze, burn)

**Nested Classes Used:**
- `HorizontalSpritesheetLoader` (health bar depletion animation)
- `HorizontalSpritesheetLoader` (mana bar fill animation)
- `StateVariantLoader` (status effect variants: normal, poisoned, frozen, burning)
- `SingleSpriteLoader` (status icons)
- `VFXController` (damage/heal particle effects)

**Code Structure:**
```java
public class Phase3StatusBarScreen extends AnimationAndSpriteLoader {
    // ANIMATION LOADERS
    private HorizontalSpritesheetLoader healthBarAnim;
    private HorizontalSpritesheetLoader manaBarAnim;
    private StateVariantLoader statusEffectVariants;
    private VFXController damageEffect;
    
    // STAT VALUES
    private int currentHP = 100;
    private int maxHP = 100;
    private int currentMana = 50;
    private int maxMana = 100;
    private int experience = 250;
    
    public void initializeScreen() {
        // Load health bar depletion animation (8 frames)
        healthBarAnim = new HorizontalSpritesheetLoader();
        healthBarAnim.load(GUI_BARS + "health_bar_depletion_8frames.png", 8);
        
        // Load mana bar fill animation (6 frames)
        manaBarAnim = new HorizontalSpritesheetLoader();
        manaBarAnim.load(GUI_BARS + "mana_bar_fill_6frames.png", 6);
        
        // Create status effect variants
        statusEffectVariants = new StateVariantLoader();
        statusEffectVariants.addState("normal", new SingleSpriteLoader().load(GUI_BASE + "status_normal.png"));
        statusEffectVariants.addState("poison", new SingleSpriteLoader().load(GUI_BASE + "status_poison.png"));
        
        // Initialize VFX
        damageEffect = new VFXController();
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        // Draw background
        g.fillRect(0, 0, width, height);
        
        // Draw health bar with animation
        BufferedImage healthFrame = healthBarAnim.getNextFrame(deltaTime);
        drawHealthBar(g, healthFrame, currentHP, maxHP);
        
        // Draw mana bar
        BufferedImage manaFrame = manaBarAnim.getNextFrame(deltaTime);
        drawManaBar(g, manaFrame, currentMana, maxMana);
        
        g.dispose();
        return buffer;
    }
}
```

**Key Deliverable:** ✅ Dynamic status bars with smooth animation

---

### **PHASE 4: Numeric Display Screen**
**File:** `Phase4NumericDisplayScreen.java`

**Objectives:**
1. ✅ Display damage numbers, healing numbers, scores
2. ✅ Implement numeric animations (floating up/down)
3. ✅ Use `GridSpritesheetLoader` for digit sprites
4. ✅ Floating damage indicator system
5. ✅ Combo counter with animations

**Nested Classes Used:**
- `GridSpritesheetLoader` (digit grid: 0-9 in 1 row × 10 columns)
- `VerticalSpritesheetLoader` (floating text animation: frame1→frame2→fade)
- `VFXController` (number appearance effects)
- `StateTransition` (combo counter states)

**Code Structure:**
```java
public class Phase4NumericDisplayScreen extends AnimationAndSpriteLoader {
    // DIGIT LOADER (for displaying numbers)
    private GridSpritesheetLoader digitGrid;
    
    // FLOATING TEXT
    private ArrayList<FloatingNumber> floatingNumbers = new ArrayList<>();
    
    // COMBO SYSTEM
    private int comboCounter = 0;
    private AnimationAndSpriteLoader.StateTransition comboState;
    
    private class FloatingNumber {
        int value;
        float x, y;
        float velocityY = -2;  // Floats upward
        int lifetime = 1000;   // 1 second
    }
    
    public void initializeScreen() {
        // Load digit grid (0-9, 10 columns)
        digitGrid = new GridSpritesheetLoader();
        digitGrid.load(GUI_NUMBERS + "digits_grid.png", 1, 10);
        
        comboState = new StateTransition();
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        // Draw floating damage/heal numbers
        for (FloatingNumber fn : floatingNumbers) {
            drawNumber(g, fn.value, (int) fn.x, (int) fn.y);
        }
        
        // Draw combo counter
        drawComboCounter(g, width, height);
        
        g.dispose();
        return buffer;
    }
    
    private void drawNumber(Graphics2D g, int value, int x, int y) {
        String str = String.valueOf(value);
        for (int i = 0; i < str.length(); i++) {
            int digit = Character.getNumericValue(str.charAt(i));
            BufferedImage digitFrame = digitGrid.getFrameAt(0, digit);
            g.drawImage(digitFrame, x + (i * 16), y, 16, 16, null);
        }
    }
}
```

**Key Deliverable:** ✅ Numeric display system with floating animations

---

### **PHASE 5: Button Screen**
**File:** `Phase5ButtonScreen.java`

**Objectives:**
1. ✅ Interactive buttons with hover/click effects
2. ✅ Button animations via `GridFrameAnimationLoader`
3. ✅ Integrate `VFXController` for click particle effects
4. ✅ Keyboard/mouse button selection
5. ✅ Button state management (enabled, disabled, selected, focused)

**Nested Classes Used:**
- `GridFrameAnimationLoader` (button press animation grid)
- `GridSpritesheetLoader` (button state variants: 2 states × 4 options = 2×4 grid)
- `VFXController` (particle effects on click)
- `InputHandler` (mouse clicks, keyboard navigation)
- `StateTransition` (button states: idle, hover, pressed, released)
- `StateVariantLoader` (button state variants)

**Code Structure:**
```java
public class Phase5ButtonScreen extends AnimationAndSpriteLoader {
    // BUTTON DEFINITION
    private class Button {
        String label;
        float x, y;
        float width, height;
        AnimationAndSpriteLoader.StateTransition state;
        GridFrameAnimationLoader pressAnimation;
        boolean enabled;
    }
    
    private ArrayList<Button> buttons = new ArrayList<>();
    private GridSpritesheetLoader buttonStateGrid;
    private VFXController clickEffect;
    
    public void initializeScreen() {
        // Create buttons
        Button btn1 = new Button();
        btn1.label = "START";
        btn1.x = 100; btn1.y = 100;
        btn1.width = 200; btn1.height = 50;
        btn1.state = new StateTransition();
        btn1.state.addTransition("idle", "hover", () -> mouseOver(btn1));
        btn1.state.addTransition("hover", "pressed", () -> mouseClicked(btn1));
        
        // Load button press animation
        btn1.pressAnimation = new GridFrameAnimationLoader();
        btn1.pressAnimation.load(
            GUI_BUTTONS + "button_press_4x8.png", 
            4, 8, 
            new int[]{50, 50, 50, 50, 50, 50, 50, 50}
        );
        
        buttons.add(btn1);
        
        clickEffect = new VFXController();
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        for (Button btn : buttons) {
            // Draw button with current state
            drawButton(g, btn);
            
            // Update animation if pressed
            if (btn.state.getCurrentState().equals("pressed")) {
                BufferedImage frame = btn.pressAnimation.getNextFrame(16.6f);
                if (frame != null) {
                    g.drawImage(frame, (int) btn.x, (int) btn.y, (int) btn.width, (int) btn.height, null);
                }
            }
        }
        
        g.dispose();
        return buffer;
    }
}
```

**Key Deliverable:** ✅ Fully interactive button system with animations

---

### **PHASE 6: Decoration Screen**
**File:** `Phase6DecorationScreen.java`

**Objectives:**
1. ✅ Parallax scrolling backgrounds using `EnvironmentController`
2. ✅ Animated decorative elements
3. ✅ Living breathing background effects
4. ✅ Environmental particle effects
5. ✅ Weather effects (rain, snow, wind)

**Nested Classes Used:**
- `EnvironmentController` (parallax layers, weather effects)
- `HorizontalSpritesheetLoader` (animated decoration frames)
- `VFXController` (particle effects: rain, snow, sparkles)
- `StateTransition` (weather state management)

**Code Structure:**
```java
public class Phase6DecorationScreen extends AnimationAndSpriteLoader {
    // ENVIRONMENT
    private EnvironmentController environment;
    
    // DECORATIONS
    private ArrayList<AnimatedDeco> decorations = new ArrayList<>();
    
    private class AnimatedDeco {
        HorizontalSpritesheetLoader animation;
        float x, y;
        boolean looping;
    }
    
    // WEATHER
    private String currentWeather = "CLEAR";
    private VFXController weatherEffect;
    
    public void initializeScreen() {
        // Setup parallax
        environment = new EnvironmentController();
        environment.setParallaxLayers(3);
        // Far background scrolls slowest, near background fastest
        
        // Add weather effects
        weatherEffect = new VFXController();
        setWeather("RAIN");
        
        // Add decorative elements
        AnimatedDeco torch = new AnimatedDeco();
        torch.animation = new HorizontalSpritesheetLoader();
        torch.animation.load(GUI_OTHER_DECOR + "torch_flame_6frames.png", 6);
        decorations.add(torch);
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        // Draw parallax backgrounds
        environment.updateParallax(cameraX, cameraY);
        // Background rendering happens here
        
        // Draw decorative elements
        for (AnimatedDeco deco : decorations) {
            BufferedImage frame = deco.animation.getNextFrame(16.6f);
            g.drawImage(frame, (int) deco.x, (int) deco.y, null);
        }
        
        // Render weather particles
        weatherEffect.updateVFX(16.6f);
        
        g.dispose();
        return buffer;
    }
    
    private void setWeather(String type) {
        currentWeather = type;
        switch (type) {
            case "RAIN":
                weatherEffect.playEffect("rain");
                weatherEffect.particleEmit(50, 90);  // Straight down
                break;
            case "SNOW":
                weatherEffect.playEffect("snow");
                weatherEffect.particleEmit(30, 85);  // Slightly angled
                break;
        }
    }
}
```

**Key Deliverable:** ✅ Dynamic environmental system with parallax and weather

---

### **PHASE 7: Item Inventory Screen**
**File:** `Phase7ItemInventoryScreen.java`

**Objectives:**
1. ✅ Grid-based inventory display using `GridSpritesheetLoader`
2. ✅ Draggable items with `PhysicsBody` physics
3. ✅ Item preview with physics interactions
4. ✅ Inventory management (add, remove, sort)
5. ✅ Item descriptions and tooltips

**Nested Classes Used:**
- `GridSpritesheetLoader` (inventory grid layout)
- `PhysicsUnitSystem` + `PhysicsBody` (draggable item physics)
- `TileRegistry` / `Level1TileRegistry` (tile-based inventory cell rendering)
- `VFXController` (item pickup effect)
- `InputHandler` (drag detection)
- `StateTransition` (inventory states: normal, dragging, invalid_drop)

**Code Structure:**
```java
public class Phase7ItemInventoryScreen extends AnimationAndSpriteLoader {
    // INVENTORY GRID
    private static final int GRID_WIDTH = 5;
    private static final int GRID_HEIGHT = 4;
    private ItemStack[][] inventory = new ItemStack[GRID_WIDTH][GRID_HEIGHT];
    
    // ITEM MODEL
    private class ItemStack {
        String itemId;
        BufferedImage icon;
        int quantity;
        float x, y;
        AnimationAndSpriteLoader.PhysicsBody physicsBody;
    }
    
    // PHYSICS
    private PhysicsUnitSystem physics;
    
    // INTERACTIONS
    private VFXController pickupEffect;
    private InputHandler input;
    
    public void initializeScreen() {
        physics = new PhysicsUnitSystem();
        pickupEffect = new VFXController();
        input = new InputHandler();
        
        // Load sample items
        ItemStack sword = new ItemStack();
        sword.itemId = "iron_sword";
        sword.icon = loadItemIcon("sword.png");
        sword.quantity = 1;
        sword.physicsBody = physics.createBody(100, 100, 32, 32);
        inventory[0][0] = sword;
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        // Draw inventory grid
        drawInventoryGrid(g);
        
        // Draw items
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (inventory[x][y] != null) {
                    ItemStack item = inventory[x][y];
                    g.drawImage(item.icon, (int) item.x, (int) item.y, 32, 32, null);
                }
            }
        }
        
        g.dispose();
        return buffer;
    }
    
    public void update(float deltaTime) {
        physics.update(deltaTime);
        
        // Check for item drag
        if (input.isMouseButtonDown(1)) {
            handleItemDrag();
        }
    }
}
```

**Key Deliverable:** ✅ Physics-based interactive inventory system

---

### **PHASE 8: Minimap Screen**
**File:** `Phase8MinimapScreen.java`

**Objectives:**
1. ✅ Display level minimap using `TileRegistry`
2. ✅ Character position indicator
3. ✅ Enemy positions on minimap
4. ✅ Fog of war effects
5. ✅ Interactive minimap (click to go to location)

**Nested Classes Used:**
- `Level1TileRegistry` / `Level2TileRegistry` (tile rendering)
- `TileRegistry` (minimap tile representation)
- `SingleSpriteLoader` (player icon, enemy icons)
- `GridSpritesheetLoader` (fog of war animation)
- `PhysicsBody` (player position tracking)
- `InputHandler` (click detection on minimap)

**Code Structure:**
```java
public class Phase8MinimapScreen extends AnimationAndSpriteLoader {
    // LEVEL DATA
    private String[] levelGrid;
    private int levelWidth, levelHeight;
    
    // MINIMAP RENDERING
    private static final int TILE_SIZE_MINIMAP = 8;  // 8x8 pixel minimap tiles
    
    // ENTITIES
    private ArrayList<MinimapEntity> entities = new ArrayList<>();
    
    private class MinimapEntity {
        float x, y;
        String type;  // player, enemy, boss
        BufferedImage icon;
    }
    
    public void initializeScreen() {
        // Load level grid (from Level1TileRegistry)
        levelGrid = loadLevelData();  // Character grid: "AAAAAABBBB..." etc
        levelWidth = levelGrid[0].length();
        levelHeight = levelGrid.length;
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        // Draw minimap background
        g.fillRect(0, 0, width, height);
        
        // Draw level tiles using registry
        for (int y = 0; y < levelHeight; y++) {
            String row = levelGrid[y];
            for (int x = 0; x < levelWidth; x++) {
                char code = row.charAt(x);
                String assetPath = Level1TileRegistry.getTile(code);
                
                if (assetPath != null) {
                    BufferedImage tile = ImageIO.read(new File(assetPath));
                    // Draw tiny 8x8 representation
                    g.drawImage(tile, x * TILE_SIZE_MINIMAP, y * TILE_SIZE_MINIMAP, 8, 8, null);
                }
            }
        }
        
        // Draw entities
        for (MinimapEntity ent : entities) {
            g.drawImage(ent.icon, (int) ent.x, (int) ent.y, 6, 6, null);
        }
        
        g.dispose();
        return buffer;
    }
}
```

**Key Deliverable:** ✅ Interactive minimap with tile registry integration

---

### **PHASE 9: Dialogue Screen**
**File:** `Phase9DialogueScreen.java`

**Objectives:**
1. ✅ Character dialogue display with animated text
2. ✅ Multiple dialogue options (branching dialogue)
3. ✅ Character portrait animations
4. ✅ Sound effect integration (dialogue sounds)
5. ✅ Dialogue progression with animations

**Nested Classes Used:**
- `SequenceFrameAnimationLoader` (cinematic dialogue backgrounds)
- `PlayerController` / `EnemyController` (character animation during dialogue)
- `VerticalSpritesheetLoader` (text appearance animation)
- `VFXController` (text sparkle effects as text appears)
- `StateTransition` (dialogue flow states)
- `InputHandler` (advance dialogue with space/enter)

**Code Structure:**
```java
public class Phase9DialogueScreen extends AnimationAndSpriteLoader {
    // DIALOGUE DATA
    private String[] dialogueLines = {};
    private int currentLineIndex = 0;
    private ArrayList<String> currentChoices = new ArrayList<>();
    
    // ANIMATIONS
    private PlayerController speaker;
    private SequenceFrameAnimationLoader bgAnimation;
    private VerticalSpritesheetLoader textAppearanceAnim;
    
    // TEXT ANIMATION
    private String displayedText = "";
    private float textDisplayProgress = 0;
    private VFXController textEffects;
    
    public void initializeScreen(String dialogueKey) {
        // Load dialogue
        dialogueLines = loadDialogueLines(dialogueKey);
        currentLineIndex = 0;
        
        // Load background cinematic
        bgAnimation = new SequenceFrameAnimationLoader();
        String[] bgSequence = {
            GUI_BASE + "dialogue_bg_01.png",
            GUI_BASE + "dialogue_bg_02.png",
            GUI_BASE + "dialogue_bg_03.png"
        };
        bgAnimation.loadSequence(bgSequence, new int[]{500, 500, 500});
        
        // Initialize speaker
        speaker = new PlayerController();
        
        textEffects = new VFXController();
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        // Draw dialogue background
        BufferedImage bgFrame = bgAnimation.getNextFrame(16.6f);
        g.drawImage(bgFrame, 0, 0, width, height, null);
        
        // Draw speaker character
        BufferedImage charFrame = speaker.getAnimationFrame();
        g.drawImage(charFrame, 50, 100, 200, 200, null);
        
        // Draw dialogue text box
        drawDialogueBox(g, width, height);
        
        // Draw response choices
        drawChoices(g, width, height);
        
        g.dispose();
        return buffer;
    }
    
    public void update(float deltaTime) {
        textDisplayProgress += deltaTime;
        
        String currentLine = dialogueLines[currentLineIndex];
        float charSpeed = 50;  // Characters per second
        int charsToShow = Math.min(
            (int) (textDisplayProgress * charSpeed / 1000),
            currentLine.length()
        );
        
        displayedText = currentLine.substring(0, charsToShow);
        
        if (input.isKeyDown(KeyEvent.VK_SPACE) && textDisplayProgress > 500) {
            advanceDialogue();
        }
    }
}
```

**Key Deliverable:** ✅ Full dialogue system with animations and branching

---

### **PHASE 10: Tooltip Screen**
**File:** `Phase10TooltipScreen.java`

**Objectives:**
1. ✅ Dynamic tooltips on hover
2. ✅ Animated tooltip appearance
3. ✅ Tooltip positioning and sizing
4. ✅ Rich text support (item descriptions)
5. ✅ Particle effects on tooltip appearance

**Nested Classes Used:**
- `VFXController` (tooltip sparkle/shimmer effects)
- `SingleSpriteLoader` (tooltip background frame)
- `VerticalSpritesheetLoader` (text appearance animation in tooltip)
- `InputHandler` (mouse position tracking for tooltip placement)
- `StateTransition` (tooltip visibility states)

**Code Structure:**
```java
public class Phase10TooltipScreen extends AnimationAndSpriteLoader {
    // TOOLTIP DATA
    private class Tooltip {
        String title;
        String[] description;
        float x, y;
        boolean visible;
        AnimationAndSpriteLoader.StateTransition state;
    }
    
    private ArrayList<Tooltip> tooltips = new ArrayList<>();
    private Tooltip activeTooltip;
    private VFXController shimmerEffect;
    private SingleSpriteLoader tooltipBg;
    
    public void initializeScreen() {
        shimmerEffect = new VFXController();
        tooltipBg = new SingleSpriteLoader();
        tooltipBg.load(GUI_BASE + "tooltip_bg.png");
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        if (activeTooltip != null && activeTooltip.visible) {
            drawTooltip(g, activeTooltip);
        }
        
        g.dispose();
        return buffer;
    }
    
    private void drawTooltip(Graphics2D g, Tooltip tooltip) {
        // Background
        BufferedImage bg = tooltipBg.getFrame(0);
        g.drawImage(bg, (int) tooltip.x, (int) tooltip.y, null);
        
        // Title and description text
        drawText(g, tooltip.title, (int) tooltip.x + 10, (int) tooltip.y + 10);
        for (int i = 0; i < tooltip.description.length; i++) {
            drawText(g, tooltip.description[i], (int) tooltip.x + 10, (int) tooltip.y + 30 + (i * 15));
        }
        
        // Shimmer effect
        shimmerEffect.updateVFX(16.6f);
    }
}
```

**Key Deliverable:** ✅ Rich tooltip system with particle effects

---

### **PHASE 11: Notification Screen**
**File:** `Phase11NotificationScreen.java`

**Objectives:**
1. ✅ Toast notification popups
2. ✅ Animated notification appearance/disappearance
3. ✅ Multiple simultaneous notifications
4. ✅ Notification queuing system
5. ✅ Notification types (info, success, warning, error)

**Nested Classes Used:**
- `StateVariantLoader` (notification type variants: info, success, warning, error)
- `VerticalSpritesheetLoader` (float-up animation)
- `VFXController` (notification effects)
- `StateTransition` (notification lifecycle states)

**Code Structure:**
```java
public class Phase11NotificationScreen extends AnimationAndSpriteLoader {
    // NOTIFICATION
    private class Notification {
        String type;  // info, success, warning, error
        String message;
        float x, y;
        float lifetime = 0;
        float maxLifetime = 3000;  // 3 seconds
        AnimationAndSpriteLoader.StateTransition state;
    }
    
    private ArrayList<Notification> notifications = new ArrayList<>();
    private StateVariantLoader notificationVariants;
    private VFXController notificationEffects;
    
    public void initializeScreen() {
        notificationVariants = new StateVariantLoader();
        
        // Load notification variants
        notificationVariants.addState("info", new SingleSpriteLoader().load(GUI_BASE + "notification_info.png"));
        notificationVariants.addState("success", new SingleSpriteLoader().load(GUI_BASE + "notification_success.png"));
        notificationVariants.addState("warning", new SingleSpriteLoader().load(GUI_BASE + "notification_warning.png"));
        notificationVariants.addState("error", new SingleSpriteLoader().load(GUI_BASE + "notification_error.png"));
        
        notificationEffects = new VFXController();
    }
    
    public void addNotification(String type, String message) {
        Notification notif = new Notification();
        notif.type = type;
        notif.message = message;
        notif.x = 10;
        notif.y = 10 + (notifications.size() * 80);
        
        notifications.add(notif);
        notificationEffects.playEffect("sparkle");
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        // Draw notifications
        for (Notification notif : notifications) {
            drawNotification(g, notif);
        }
        
        g.dispose();
        return buffer;
    }
    
    public void update(float deltaTime) {
        for (Notification notif : notifications) {
            notif.lifetime += deltaTime;
            notif.y -= 0.5f;  // Float upward
        }
        
        // Remove expired notifications
        notifications.removeIf(n -> n.lifetime > n.maxLifetime);
    }
}
```

**Key Deliverable:** ✅ Toast notification system with auto-cleanup

---

### **PHASE 12: Quest Tracker Screen**
**File:** `Phase12QuestTrackerScreen.java`

**Objectives:**
1. ✅ Display active quests with progress bars
2. ✅ Animated quest objective list
3. ✅ Quest completion effects
4. ✅ Expandable/collapsible quest details
5. ✅ Quest reward previews

**Nested Classes Used:**
- `VerticalSpritesheetLoader` (quest list scroll animation)
- `HorizontalSpritesheetLoader` (progress bar animations)
- `StateVariantLoader` (quest status: active, complete, failed)
- `VFXController` (quest completion effects)
- `StateTransition` (expanded/collapsed quest states)

**Code Structure:**
```java
public class Phase12QuestTrackerScreen extends AnimationAndSpriteLoader {
    // QUEST DATA
    private class Quest {
        String id;
        String name;
        int progress;
        int maxProgress;
        String[] objectives;
        boolean active;
        boolean completed;
        AnimationAndSpriteLoader.StateTransition state;
    }
    
    private ArrayList<Quest> quests = new ArrayList<>();
    private int expandedQuestIndex = -1;
    private VerticalSpritesheetLoader questListAnim;
    private HorizontalSpritesheetLoader progressBarAnim;
    private VFXController completionEffect;
    
    public void initializeScreen() {
        questListAnim = new VerticalSpritesheetLoader();
        questListAnim.load(GUI_BASE + "quest_list_scroll_4frames.png", 4);
        
        progressBarAnim = new HorizontalSpritesheetLoader();
        progressBarAnim.load(GUI_BARS + "quest_progress_8frames.png", 8);
        
        completionEffect = new VFXController();
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        // Draw quest list
        int yOffset = 0;
        for (int i = 0; i < quests.size(); i++) {
            Quest quest = quests.get(i);
            drawQuestEntry(g, quest, yOffset);
            
            if (expandedQuestIndex == i) {
                yOffset += 100 + (quest.objectives.length * 20);
            } else {
                yOffset += 50;
            }
        }
        
        g.dispose();
        return buffer;
    }
}
```

**Key Deliverable:** ✅ Dynamic quest tracking with expandable details

---

### **PHASE 13: Main Menu Screen**
**File:** `Phase13MainMenuScreen.java`

**Objectives:**
1. ✅ Complete main menu with all systems integrated
2. ✅ Game title with animation
3. ✅ Start game, settings, quit buttons
4. ✅ Parallax background with particles
5. ✅ Music integration

**Nested Classes Used:**
- ALL PREVIOUS SYSTEMS
- `EnvironmentController` (parallax background)
- `ButtonAnimation` system from Phase 5
- `StateTransition` (menu navigation)
- `GameStateManager` (game mode selection)

**Code Structure:**
```java
public class Phase13MainMenuScreen extends AnimationAndSpriteLoader {
    // Complete integration of all previous systems
    private EnvironmentController background;
    private ArrayList<Button> buttons = new ArrayList<>();
    private GameStateManager gameState;
    private VFXController menuEffects;
    
    public void initializeScreen() {
        // Initialize all systems
        background = new EnvironmentController();
        gameState = new GameStateManager();
        menuEffects = new VFXController();
        
        // Create buttons: Start Game, Settings, Quit
        createMainMenuButtons();
    }
    
    public BufferedImage render(int width, int height) {
        // Render complete menu with all components
        return completeMenuRender(width, height);
    }
}
```

**Key Deliverable:** ✅ Fully functional main menu

---

### **PHASE 14: Pause Menu Screen**
**File:** `Phase14PauseMenuScreen.java`

**Objectives:**
1. ✅ In-game pause overlay
2. ✅ Semi-transparent background
3. ✅ Resume, settings, quit buttons
4. ✅ Game state preservation
5. ✅ Animation on pause/unpause

**Nested Classes Used:**
- `StateTransition` (pause state management)
- Button system from Phase 5
- `GameStateManager` (game state tracking)

**Key Deliverable:** ✅ Pauseable game with overlay menu

---

### **PHASE 15: Settings Screen**
**File:** `Phase15SettingsScreen.java`

**Objectives:**
1. ✅ Audio settings (volume sliders)
2. ✅ Graphics settings (quality, resolution)
3. ✅ Control remapping
4. ✅ Difficulty selection
5. ✅ Settings persistence

**Nested Classes Used:**
- `HorizontalSpritesheetLoader` (volume slider fill animation)
- `InputHandler` (control detection, remapping)
- `StateVariantLoader` (difficulty level variants)
- Slider components

**Key Deliverable:** ✅ Complete settings configuration screen

---

## 🏗️ ARCHITECTURE IMPLEMENTATION GUIDE

### **File Structure Template**

```java
public class [PhaseScreenName]Screen extends AnimationAndSpriteLoader {
    
    // ═══════════════════════════════════════════════════════════════
    // 1. NESTED CLASS DECLARATIONS (from AnimationAndSpriteLoader)
    // ═══════════════════════════════════════════════════════════════
    private AnimationAndSpriteLoader.StateTransition screenState;
    private AnimationAndSpriteLoader.InputHandler inputHandler;
    private AnimationAndSpriteLoader.VFXController vfxController;
    // ... other nested class instances
    
    // ═══════════════════════════════════════════════════════════════
    // 2. ASSET LOADERS
    // ═══════════════════════════════════════════════════════════════
    private AnimationAndSpriteLoader.SingleSpriteLoader bgLoader;
    private AnimationAndSpriteLoader.HorizontalSpritesheetLoader animLoader;
    // ... other loaders
    
    // ═══════════════════════════════════════════════════════════════
    // 3. SCREEN-SPECIFIC DATA
    // ═══════════════════════════════════════════════════════════════
    private float screenWidth;
    private float screenHeight;
    // ... other fields
    
    // ═══════════════════════════════════════════════════════════════
    // 4. CONSTRUCTOR & INITIALIZATION
    // ═══════════════════════════════════════════════════════════════
    public [PhaseScreenName]Screen() {
        super();
        initializeScreen();
    }
    
    private void initializeScreen() {
        screenState = new StateTransition();
        inputHandler = new InputHandler();
        // ... load all assets
    }
    
    // ═══════════════════════════════════════════════════════════════
    // 5. RENDER METHOD (returns BufferedImage ONLY - no Graphics2D!)
    // ═══════════════════════════════════════════════════════════════
    public BufferedImage render(int width, int height) {
        BufferedImage buffer = new BufferedImage(
            width, height, BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        
        // Draw background
        // Draw components
        // Draw effects
        
        g.dispose();
        return buffer;
    }
    
    // ═══════════════════════════════════════════════════════════════
    // 6. UPDATE METHOD
    // ═══════════════════════════════════════════════════════════════
    public void update(float deltaTime) {
        // Update animations
        // Update physics
        // Update state transitions
        // Handle input
    }
}
```

---

## 🎯 INTEGRATION CHECKLIST

### **For Each Phase File:**

✅ **Imports**
- [ ] Extends `AnimationAndSpriteLoader`
- [ ] No Graphics2D imports
- [ ] No Rectangle imports
- [ ] No Color imports for fallbacks

✅ **Nested Class Usage**
- [ ] Uses MINIMUM 2, MAXIMUM 5 nested classes per phase
- [ ] All nested class instances properly initialized
- [ ] No duplicate nested class instantiation

✅ **Asset Loading**
- [ ] All PNG resources exist at specified paths
- [ ] Asset paths use constants from parent class
- [ ] No fake/placeholder asset names
- [ ] Proper error handling for missing assets

✅ **Rendering**
- [ ] render() method returns BufferedImage
- [ ] Only drawImage() used (no fillRect, drawString with vector, etc.)
- [ ] All sprite frames extracted via source/dest coordinates
- [ ] Proper clipping and bounds checking

✅ **State Management**
- [ ] StateTransition used for screen state
- [ ] InputHandler used for all input
- [ ] Proper event propagation

✅ **No Forbidden Patterns**
- [ ] ❌ No `g.fillRect()`
- [ ] ❌ No `g.drawOval()`
- [ ] ❌ No `new Color(r, g, b)` for rendering
- [ ] ❌ No vector shapes from java.awt.Shape
- [ ] ❌ No java.awt.Rectangle for rendering

---

## 📊 DEPENDENCY MATRIX

```
Entity.java
├─ Phase 1 (PauseMenuScreen) → StateTransition, InputHandler
├─ Phase 2 (Character) → PlayerController, HorizontalSpritesheetLoader
├─ Phase 3 (Status) → HorizontalSpritesheetLoader, VFXController
├─ Phase 4 (Numeric) → GridSpritesheetLoader, VFXController
├─ Phase 5 (Buttons) → GridFrameAnimationLoader, VFXController, StateVariantLoader
├─ Phase 6 (Decoration) → EnvironmentController, VFXController, HorizontalSpritesheetLoader
├─ Phase 7 (Inventory) → GridSpritesheetLoader, PhysicsBody, TileRegistry
├─ Phase 8 (Minimap) → Level1/2TileRegistry, SingleSpriteLoader
├─ Phase 9 (Dialogue) → SequenceFrameAnimationLoader, PlayerController/EnemyController
├─ Phase 10 (Tooltip) → VFXController, SingleSpriteLoader
├─ Phase 11 (Notification) → StateVariantLoader, VFXController
├─ Phase 12 (Quest) → VerticalSpritesheetLoader, HorizontalSpritesheetLoader
├─ Phase 13 (Main Menu) → ALL ABOVE SYSTEMS
├─ Phase 14 (Pause Menu) → StateTransition, GameStateManager
└─ Phase 15 (Settings) → InputHandler, HorizontalSpritesheetLoader
```

---

## 🚀 EXECUTION STRATEGY

### **Week 1: Phases 1-5 (Foundation)**
- Create phase files with base structure
- Integrate core nested classes
- Verify compilation

### **Week 2: Phases 6-10 (Advanced Systems)**
- Environmental and interactive systems
- Particle effects integration
- Complex animations

### **Week 3: Phases 11-15 (Polish & Integration)**
- Complete remaining screen types
- Full system integration
- Testing and verification

---

## ✅ SUCCESS CRITERIA

1. ✅ All 16 phase files created and extended from AnimationAndSpriteLoader
2. ✅ Each file uses 2-5 nested classes appropriately
3. ✅ Zero forbidden imports (Graphics2D, Rectangle, Color vector)
4. ✅ Zero vector graphics code (fillRect, drawOval, drawString with vector)
5. ✅ All PNG assets load from proper paths
6. ✅ All files compile with 0 errors
7. ✅ Unified state management across all screens
8. ✅ Raster-only rendering throughout

---

## 📝 NEXT STEPS

1. Create Entity.java base structure
2. Begin Phase 1 (PauseMenuScreen)  implementation
3. Incrementally implement phases 1-15
4. Integration testing after each phase set
5. Final system verification

---

**This plan represents a complete architectural redesign leveraging 21+ nested classes from AnimationAndSpriteLoader to create a cohesive, maintainable, raster-graphics-only GUI system.**

