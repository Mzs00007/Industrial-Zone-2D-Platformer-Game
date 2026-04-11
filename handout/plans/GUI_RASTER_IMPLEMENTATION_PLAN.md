# Complete GUI Implementation Plan - Raster Graphics Only

**Document:** GUI_RASTER_IMPLEMENTATION_PLAN.md  
**Date:** April 2, 2026  
**Framework:** Game.java (raster graphics enforced)  
**Constraint:** ONLY `drawImage()` - NO vector graphics anywhere

---

## 1. OVERVIEW & ARCHITECTURE

### GUI System Philosophy
- **Pure Raster:** Every visual element is a PNG/JPEG image
- **Layered Rendering:** Background → Game Objects → HUD Panels → UI Overlays
- **Modular Components:** Reusable GUI element classes
- **Data-Driven Display:** Numbers/stats via digit sprites, not text rendering

### Available Asset Inventory (190+ assets)
- **82 Frame/Panel assets** - Windows, dividers, decorative fills
- **20 Bar assets** - Health/energy bars at 0%, 20%, 40%, 60%, 80%, 100%
- **40 Icon assets** - Actions, status, system indicators
- **10 Button variants** - Color-coded button styles
- **17 Number glyph assets** - Individual digits 0-9 + symbols
- **4 Cursor variants** - Pointer states
- **20 Skill icons** - Special abilities & status effects
- **63 Font character sheets** - Full alphabet support (CyberpunkCraftpixPixel)
- **12 Card animation spritesheets** - Character UI animations
- **8 Decorative elements** - Cables, glow bars, ribbons
- **61 Keyboard indicators** - Key input display
- **21 Mouse indicators** - Mouse action display

---

## 2. GUI LAYOUT STRUCTURE

### Screen Division (1080×720 example)
```
┌─────────────────────────────────────────────────────────────────┐
│                                                                   │ 50px
│ TOP BAR: Level, Stage, Objective Display                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│                                                                   │
│                        GAME VIEW                                  │ 570px
│                    (Parallax Rendering)                          │
│                                                                   │
│                                                                   │
├─────────────────────────────────────────────────────────────────┤
│  [Inv] [Map] │ Health: ████████░░ │ Energy: ████████░░ │ Score │ 100px
│  [Stats]     │ Ammo: [3/12]        │ Armor:  ████░░░░░░ │ Timer │
│  [Skills]    │ Status: NORMAL      │ Effects: SHIELD    │ Goals │
└─────────────────────────────────────────────────────────────────┘
```

### Rendering Order (Back to Front)
1. **Background Layer** - Black tiled background
2. **Game Layer** - Parallax system renders here
3. **HUD Layer 1** - Progress bars, status indicators
4. **HUD Layer 2** - Inventory/stats/map panels
5. **HUD Layer 3** - Interactive buttons, menu overlays
6. **Overlay Layer** - Tooltips, notifications, popups

---

## 3. CORE GUI COMPONENTS

### 3.1 TOP BAR (50px height)
**Purpose:** Level info, objectives, timers  
**Elements:**
- Level indicator: "LEVEL 1 - INDUSTRIAL ZONE"
- Stage progress: "Stage 1/3"
- Time/Timer display: Using digit glyphs → Position [50, 5]
- Objective text: Via character sheet glyphs

**Asset Usage:**
- Frame: `01_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png` (scaled)
- Fill: `38_GUI_Frame_FillSolidNavy_WideRectNoBorder_WindowFill.png`
- Digits: `01_GUI_Number_Digit[0-9]_StyledGlyph_Decorative.png`

**Implementation Class:**
```java
class TopBarPanel {
    BufferedImage frameTopEdge;
    BufferedImage fillBackground;
    int timeLeft = 300; // seconds
    
    void render(Graphics2D g, int screenWidth) {
        // Draw background
        g.drawImage(fillBackground, 0, 0, screenWidth, 50, null);
        // Draw frame edge
        g.drawImage(frameTopEdge, 0, 0, screenWidth, 3, null);
        // Draw digit glyphs for time
        renderNumberAtPosition(g, timeLeft, 900, 15);
    }
}
```

---

### 3.2 BOTTOM HUD PANEL (100px height)
**Purpose:** Player stats, health, ammo, effects display  
**Elements:**
- Health bar (with frame): @ [20, height-90]
- Energy bar: @ [250, height-90]
- Armor bar: @ [480, height-90]
- Ammo counter: "3/12" → digit glyphs @ [720, height-90]
- Status text: Via character glyphs @ [20, height-40]
- Active effects: Icons @ [150, height-40]

**Asset Usage:**
- Panel frame: `07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png`
- Health bars: `01_GUI_Bar_HealthBar_[0-100pct]_RedOrangeFillDarkFrame_HUD.png`
- Energy bars: `09_GUI_Bar_EnergyBar_[0-100pct]_BlueCyanFillDarkFrame_HUD.png`
- Armor bars: (Custom or use as placeholder)
- Status icons: `16_GUI_SkillIcon_Heart_HealthOrLife_SkillIcon.png`

**Implementation Class:**
```java
class HUDPanel {
    BufferedImage healthBars[] = new BufferedImage[6]; // 0%, 20%, 40%, 60%, 80%, 100%
    BufferedImage energyBars[] = new BufferedImage[6];
    BufferedImage armorBars[] = new BufferedImage[6];
    
    int playerHealth = 85;
    int playerEnergy = 60;
    int playerArmor = 40;
    int ammoCount = 3;
    int ammoMax = 12;
    
    void render(Graphics2D g, int screenWidth, int screenHeight) {
        // Draw background panel
        // Draw health bar based on playerHealth percentage
        int healthIndex = (playerHealth / 20); // 0-5 range
        g.drawImage(healthBars[healthIndex], 20, screenHeight-90, null);
        
        // Draw energy bar
        int energyIndex = (playerEnergy / 20);
        g.drawImage(energyBars[energyIndex], 250, screenHeight-90, null);
        
        // Draw ammo counter: 3/12
        renderNumberAtPosition(g, ammoCount, 720, screenHeight-80);
        renderDigitsAtPosition(g, "/", 745, screenHeight-80);
        renderNumberAtPosition(g, ammoMax, 760, screenHeight-80);
    }
}
```

---

### 3.3 LEFT SIDEBAR (200px width) - Collapsible Panels
**Purpose:** Inventory, stats, skills, mission log  
**Panels:**
- **Inventory Panel** - 4 slots showing equipped items w/ icons
- **Character Stats** - Health, Armor, Damage using bar graphics
- **Skills** - 5 skill icons with cooldown indicators
- **Map Preview** - Mini version of level layout

**Asset Usage:**
- Panels: `37_GUI_Frame_PanelInsetSquare_SingleCellDarkBorder_PanelCell.png` (repeated)
- Item icons: Various from icon library
- Skill icons: `[01-20]_GUI_SkillIcon_*.png`
- Dividers: `17_GUI_Frame_PanelWideRect_TealCyanAccentStripe_DividerBar.png`

**Implementation Class:**
```java
class LeftSidebar {
    BufferedImage panelCell;
    BufferedImage dividerBar;
    BufferedImage skillIcons[] = new BufferedImage[5];
    boolean isExpanded = true;
    
    void render(Graphics2D g) {
        if (!isExpanded) return; // Hidden state
        
        // Draw background panel containers
        for (int i = 0; i < 4; i++) {
            g.drawImage(panelCell, 10, 60 + i*80, 180, 75, null);
        }
        
        // Draw skill icons
        for (int i = 0; i < 5; i++) {
            if (skillIcons[i] != null) {
                g.drawImage(skillIcons[i], 30 + i*25, 420, null);
            }
        }
    }
}
```

---

### 3.4 BUTTONS & INTERACTIVE ELEMENTS (Collapsible)
**Purpose:** Pause, Settings, Help, Weapon Select  
**Button Locations:**
- Pause button: Top right @ [1000, 5]
- Settings: @ [950, 5]
- Help/Tutorial: @ [900, 5]
- Weapon slots: Bottom right @ [style]

**Asset Usage:**
- Button base: `GUI_ButtonColorMap_Variant_[01-10].png` (10 color variants)
- Icons on buttons: Heart, Shield, Sword, Settings, Pause

**Implementation Class:**
```java
class ButtonPanel {
    BufferedImage buttonVariants[] = new BufferedImage[10];
    boolean pauseButtonHovered = false;
    
    void render(Graphics2D g, int screenWidth) {
        // Pause button
        int buttonX = screenWidth - 80;
        int buttonY = 5;
        BufferedImage button = pauseButtonHovered 
            ? buttonVariants[5]  // Highlight variant
            : buttonVariants[0];  // Normal variant
        g.drawImage(button, buttonX, buttonY, 60, 30, null);
        
        // Draw pause icon centered on button
        // Icon would be loaded separately
    }
}
```

---

### 3.5 NOTIFICATION & POPUP OVERLAYS
**Purpose:** Level complete, damage taken, mission updates, dialogs  
**Element Types:**
- **Damage Popup** - Red flashing alert with damage amount
- **Level Complete** - Centered modal with stage progress
- **Mission Update** - Slide-in notification from top
- **Error/Alert** - Warning icon + text via character glyphs

**Asset Usage:**
- Alert icon: `10_GUI_SkillIcon_Exclaim_AlertOrWarning_SkillIcon.png`
- Background panel: Frame assets + fill
- Text: Character sheet glyphs
- Animation: Sequential frame rendering for flashing effect

**Implementation Class:**
```java
class NotificationSystem {
    Queue<Notification> activeNotifications = new Queue<>();
    
    class Notification {
        String type; // "damage", "level_complete", "mission"
        long startTime;
        int duration;
        String message;
        BufferedImage icon;
    }
    
    void renderNotifications(Graphics2D g, int screenWidth, int screenHeight) {
        for (Notification n : activeNotifications) {
            long elapsed = System.currentTimeMillis() - n.startTime;
            if (elapsed > n.duration) {
                activeNotifications.remove(n);
                continue;
            }
            
            // Render based on type
            if (n.type.equals("damage")) {
                renderDamageNotification(g, n, elapsed);
            }
        }
    }
}
```

---

## 4. IMPLEMENTATION PHASES

### **PHASE 1: Foundation (Week 1)**
**Goals:** Core infrastructure, basic panel rendering

- [x] Create GUIComponent base class
- [x] Create BarRenderer for health/energy/armor bars
- [x] Create DigitRenderer for number display (0-9 mapping)
- [x] Create PanelRenderer for frame/fill assembly
- [ ] Implement TopBarPanel rendering
- [ ] Implement HUDPanel rendering
- [ ] Load all frame, bar, and digit assets into memory

**Expected Output:** Two functional HUD panels on-screen

---

### **PHASE 2: Interaction (Week 2)**
**Goals:** Interactive elements, input handling

- [ ] Implement LeftSidebar with panel stacking
- [ ] Implement ButtonPanel with hover detection
- [ ] Add mouse position tracking to Game.java
- [ ] Add hover-based button highlighting
- [ ] Implement pause button functionality
- [ ] Add inventory item display

**Expected Output:** Clickable buttons, responsive UI

---

### **PHASE 3: Notifications (Week 3)**
**Goals:** Dynamic popups, status effects

- [ ] Implement NotificationSystem
- [ ] Add damage popup rendering
- [ ] Add level complete modal
- [ ] Add mission update notifications
- [ ] Implement flashing/animation effects
- [ ] Add status effect icons

**Expected Output:** Dynamic notifications during gameplay

---

### **PHASE 4: Polish (Week 4)**
**Goals:** Animations, refinement

- [ ] Add frame-based animations for panels
- [ ] Implement smooth transitions
- [ ] Add decorative elements (cables, glow bars)
- [ ] Implement cursor variants
- [ ] Add keyboard input display
- [ ] Performance optimization

**Expected Output:** Polished, professional GUI

---

## 5. CODE ARCHITECTURE

### New GUI Classes (in src/ or separate gui/ folder)

```
src/
├── Game.java                    (EXISTING - modified to add GUI calls)
├── gui/
│   ├── GUIComponent.java        (Base abstract class)
│   ├── TopBarPanel.java         (Level info, timers)
│   ├── HUDPanel.java            (Stats, bars, ammo)
│   ├── LeftSidebar.java         (Inventory, skills, map)
│   ├── ButtonPanel.java         (Interactive buttons)
│   ├── NotificationSystem.java  (Popups, alerts)
│   ├── BarRenderer.java         (Health/Energy/Armor bar helper)
│   ├── DigitRenderer.java       (Number glyph rendering)
│   └── PanelRenderer.java       (Frame assembly helper)
└── ...
```

### GUIComponent Base Class
```java
abstract class GUIComponent {
    protected BufferedImage[] assets;
    protected int posX, posY, width, height;
    protected boolean isVisible = true;
    
    abstract void loadAssets();
    abstract void update(long elapsedTime, GameState state);
    abstract void render(Graphics2D g);
    
    protected void loadImage(String resourcePath) {
        // Load PNG from Resources/industrial-zone/...
    }
}
```

### Integration into Game.java
```java
public class Game extends AnimationAndSpriteLoader {
    // Existing code...
    
    // GUI Components
    private TopBarPanel topBar;
    private HUDPanel hudPanel;
    private LeftSidebar sidebar;
    private ButtonPanel buttons;
    private NotificationSystem notifications;
    
    public Game() {
        super();
        // ... existing init code ...
        
        // Initialize GUI
        topBar = new TopBarPanel();
        hudPanel = new HUDPanel();
        sidebar = new LeftSidebar();
        buttons = new ButtonPanel();
        notifications = new NotificationSystem();
    }
    
    @Override
    public void draw(Graphics2D g) {
        // Existing rendering...
        renderBackgroundRaster(g);
        renderParallaxRaster(g);
        renderHUDRaster(g);
        
        // NEW: GUI Rendering (RASTER ONLY)
        topBar.render(g, getWidth());
        hudPanel.render(g, getWidth(), getHeight());
        sidebar.render(g);
        buttons.render(g, getWidth(), getHeight());
        notifications.renderNotifications(g, getWidth(), getHeight());
    }
}
```

---

## 6. DATA STRUCTURES FOR STATE

### GameState Tracking
```java
class GameState {
    // Player stats
    int health = 100;
    int maxHealth = 100;
    int energy = 80;
    int maxEnergy = 100;
    int armor = 50;
    int maxArmor = 100;
    
    // Inventory
    int ammo = 3;
    int ammoMax = 12;
    int[] inventorySlots = new int[4]; // Item IDs
    
    // Effects
    Set<String> activeEffects = new HashSet<>(); // "shield", "poisoned", etc.
    
    // Mission/Level
    String currentLevel = "LEVEL_1";
    int currentStage = 1;
    int totalStages = 3;
    int timeElapsed = 0;
    
    // Display
    List<String> notifications = new ArrayList<>();
}
```

---

## 7. ASSET LOADING STRATEGY

### Preload at Startup
```java
class GUIAssetManager {
    private Map<String, BufferedImage> images = new HashMap<>();
    
    public void loadAllGUIAssets() {
        // Frames
        loadAsset("frame_top_edge", "Resources/industrial-zone/gui/1 Frames/02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png");
        loadAsset("frame_fill_navy", "Resources/industrial-zone/gui/1 Frames/07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png");
        
        // Bars (6 variants each)
        for (int pct = 0; pct <= 100; pct += 20) {
            loadAsset("health_bar_" + pct, "Resources/industrial-zone/gui/2 Bars/0X_GUI_Bar_HealthBar_" + pct + "pct_*.png");
        }
        
        // Digits
        for (int i = 0; i <= 9; i++) {
            loadAsset("digit_" + i, "Resources/industrial-zone/gui/7 Numbers/GUI_Number_Digit" + i + "*.png");
        }
        
        // Icons
        loadAsset("heart_icon", "Resources/industrial-zone/gui/3 Icons/Icons/GUI_Icon_Heart_Love_22.png");
        // ... more icons
    }
    
    public BufferedImage get(String key) {
        return images.getOrDefault(key, null);
    }
}
```

---

## 8. NUMBER RENDERING SYSTEM

**Challenge:** Display numbers without text rendering  
**Solution:** Use digit glyphs (PNG images for each digit 0-9)

### DigitRenderer Class
```java
class DigitRenderer {
    private BufferedImage[] digits = new BufferedImage[10]; // 0-9
    private BufferedImage colon;
    private BufferedImage slash;
    private int digitWidth = 24;   // pixels per digit
    private int digitHeight = 32;
    
    /**
     * Render a number at position using digit sprites
     * Example: renderNumber(g, 123, 100, 100);
     *   Draws digits 1, 2, 3 horizontally starting at (100, 100)
     */
    public void renderNumber(Graphics2D g, int number, int x, int y) {
        String numStr = String.valueOf(number);
        for (int i = 0; i < numStr.length(); i++) {
            int digit = numStr.charAt(i) - '0';
            g.drawImage(digits[digit], x + i * digitWidth, y, digitWidth, digitHeight, null);
        }
    }
    
    /**
     * Render complex formats like "3/12" or "1:45"
     */
    public void renderFormat(Graphics2D g, String format) {
        // Parse format, render each character
        // E.g., "3/12" → digit[3] + slash + digit[1] + digit[2]
    }
}
```

**Usage:**
```java
hudPanel.digitRenderer.renderNumber(g, playerHealth, 120, screenHeight-80);  // "85"
hudPanel.digitRenderer.renderFormat(g, ammo + "/" + ammoMax);  // "3/12"
```

---

## 9. TESTING CHECKLIST

- [ ] All GUI components render without errors
- [ ] No vector graphics (fillRect, drawString, etc.) used
- [ ] All images load from PNG files successfully
- [ ] Layout is responsive to different screen sizes
- [ ] Button hover states work correctly
- [ ] Bars update based on player state
- [ ] Notifications appear and disappear properly
- [ ] Performance: 60 FPS with GUI rendered
- [ ] No memory leaks from asset loading

---

## 10. REFERENCE FILES

**Existing Frame Assets:**
- `01_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png`
- `07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png`
- `37_GUI_Frame_PanelInsetSquare_SingleCellDarkBorder_PanelCell.png`

**Bar Assets (Health Example):**
- `01_GUI_Bar_HealthBar_Full100pct_RedOrangeFillDarkFrame_HUD.png`
- `02_GUI_Bar_HealthBar_80pct_RedOrangeFill_HUD.png`
- `03_GUI_Bar_HealthBar_60pct_RedOrangeFill_HUD.png`
- `04_GUI_Bar_HealthBar_40pct_RedOrangeFill_HUD.png`
- `05_GUI_Bar_HealthBar_20pct_RedOrangeFill_HUD.png`
- `06_GUI_Bar_HealthBar_5pctCritical_RedOrangeFill_HUD.png`

**Digit Assets (Complete Set):**
- `GUI_Number_Digit0_Zero.png` through `09_GUI_Number_Digit9_StyledGlyph_Decorative.png`
- `GUI_Number_Symbol_Slash_Separator.png`
- `GUI_Number_Symbol_Colon_Separator.png`

---

## SUMMARY

| Phase | Duration | Components | Status |
|-------|----------|-----------|--------|
| **1: Foundation** | Week 1 | TopBar, HUD Bars, Digits | Not Started |
| **2: Interaction** | Week 2 | Buttons, Sidebar, Input | Not Started |
| **3: Notifications** | Week 3 | Popups, Alerts, Effects | Not Started |
| **4: Polish** | Week 4 | Animations, Transitions | Not Started |

**Total Assets Used:** 150+  
**Total Methods to Implement:** 25+  
**Estimated Lines of Code:** 3000-4000  
**Constraint:** ABSOLUTELY NO VECTOR GRAPHICS - RASTER ONLY

---
