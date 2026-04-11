# GUI MASTER GRID - PRACTICAL EXAMPLES
**Date:** April 4, 2026  
**Purpose:** Concrete code examples for real-world usage

---

## EXAMPLE 1: Simple Bordered Window

### Scenario
You want a small 160×128px dialog box with dark navy theme.

### Code
```java
public class SimpleDialogExample {
    
    private BufferedImage masterSheet;
    private BufferedImage dialogWindow;
    
    public void initialize() {
        // Load once
        masterSheet = AnimationAndSpriteLoader.loadMasterSpritesheet();
        if (masterSheet == null) {
            System.err.println("Failed to load master sheet!");
            return;
        }
        
        // Assemble window: 5 cells × 4 cells = 160×128px
        dialogWindow = AnimationAndSpriteLoader.assembleWindowFrame(
            masterSheet,
            5,              // Width in 32px cells
            4,              // Height in 32px cells
            "DARK_NAVY"     // Dark navy style
        );
    }
    
    public void render(Graphics2D g2d) {
        if (dialogWindow == null) return;
        
        // Draw the window frame on screen
        g2d.drawImage(dialogWindow, 100, 100, null);
        
        // Now draw your content (buttons, text, etc.) on top
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Confirm Action?", 120, 130);
        
        // Draw buttons on top of the frame
        // Button code here...
    }
}
```

### Result
```
┌────────────────────────────────────┐
│                                    │
│      Confirm Action?               │
│                                    │
│    [YES Button]  [NO Button]       │
│                                    │
└────────────────────────────────────┘
  160px width × 128px height
```

---

## EXAMPLE 2: Status Panel with Light Theme

### Scenario
You want a horizontal status bar (typically shown at bottom or top of game screen).

### Code
```java
public class StatusPanelExample {
    
    private BufferedImage masterSheet;
    private BufferedImage statusPanel;
    
    public void initialize() {
        masterSheet = AnimationAndSpriteLoader.loadMasterSpritesheet();
        
        // Create wide, short status bar
        // 10 cells wide × 2 cells tall = 320×64px
        // Using light navy for contrast with game scene
        statusPanel = AnimationAndSpriteLoader.assembleWindowFrame(
            masterSheet,
            10,         // Very wide
            2,          // Very short
            "LIGHT_NAVY" // Light theme for visibility
        );
    }
    
    public void render(Graphics2D g2d, GameState gameState) {
        // Draw status panel at bottom of screen
        g2d.drawImage(statusPanel, 0, SCREEN_HEIGHT - 64, null);
        
        // Draw status data on top
        int x = 20;
        int y = SCREEN_HEIGHT - 40;
        
        // Health bar
        g2d.setColor(Color.WHITE);
        g2d.drawString("HP: ", x, y);
        drawHealthBar(g2d, x + 50, y - 10, gameState.getHP(), gameState.getMaxHP());
        
        // Ammo count
        g2d.drawString("Ammo: " + gameState.getAmmo(), x + 200, y);
        
        // Score
        g2d.drawString("Score: " + gameState.getScore(), x + 350, y);
        
        // Level/Wave
        g2d.drawString("Wave: " + gameState.getWave(), x + 550, y);
    }
    
    private void drawHealthBar(Graphics2D g2d, int x, int y, int current, int max) {
        // Simple health bar implementation
        int barWidth = 100;
        int fillWidth = (current * barWidth) / max;
        
        g2d.setColor(Color.RED);
        g2d.fillRect(x, y, barWidth, 15);
        
        g2d.setColor(Color.GREEN);
        g2d.fillRect(x, y, fillWidth, 15);
        
        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y, barWidth, 15);
    }
}
```

---

## EXAMPLE 3: Character Selection Screen

### Scenario
Build a character sheet window showing stats, equipment, skills.

### Code
```java
public class CharacterSheetExample {
    
    private BufferedImage masterSheet;
    private BufferedImage characterWindow;
    private Map<String, BufferedImage> sectionPanels;
    
    public void initialize() {
        masterSheet = AnimationAndSpriteLoader.loadMasterSpritesheet();
        sectionPanels = new HashMap<>();
        
        // Main character window
        // 192x160px = 6x5 cells
        characterWindow = AnimationAndSpriteLoader.assembleWindowFrame(
            masterSheet,
            6,              // Width: 192px
            5,              // Height: 160px
            "DARK_NAVY"
        );
        
        // Sub-panels for sections
        // Stats section: 160x96px = 5x3
        sectionPanels.put("stats", AnimationAndSpriteLoader.assembleWindowFrame(
            masterSheet, 5, 3, "STANDARD_NAVY"
        ));
        
        // Equipment section: 160x96px = 5x3
        sectionPanels.put("equipment", AnimationAndSpriteLoader.assembleWindowFrame(
            masterSheet, 5, 3, "LIGHT_NAVY"
        ));
        
        // Skills section: 160x96px = 5x3
        sectionPanels.put("skills", AnimationAndSpriteLoader.assembleWindowFrame(
            masterSheet, 5, 3, "TEXTURED"
        ));
    }
    
    public void render(Graphics2D g2d, Character character) {
        // Draw main window
        g2d.drawImage(characterWindow, 50, 50, null);
        
        // Draw character portrait (inside main window)
        BufferedImage portrait = character.getPortrait();
        if (portrait != null) {
            g2d.drawImage(portrait, 70, 65, 80, 80, null);
        }
        
        // Draw character name
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString(character.getName(), 160, 75);
        
        // Draw level and class
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Level " + character.getLevel(), 160, 95);
        g2d.drawString(character.getCharacterClass(), 160, 110);
        
        // Draw stats panel
        g2d.drawImage(sectionPanels.get("stats"), 70, 135, null);
        renderStats(g2d, character, 85, 150);
        
        // Draw equipment panel
        g2d.drawImage(sectionPanels.get("equipment"), 250, 135, null);
        renderEquipment(g2d, character, 265, 150);
        
        // Draw skills panel
        g2d.drawImage(sectionPanels.get("skills"), 75, 245, null);
        renderSkills(g2d, character, 90, 260);
    }
    
    private void renderStats(Graphics2D g2d, Character character, int x, int y) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 11));
        
        g2d.drawString("STR: " + character.getStrength(), x, y);
        g2d.drawString("DEX: " + character.getDexterity(), x, y + 15);
        g2d.drawString("CON: " + character.getConstitution(), x, y + 30);
        g2d.drawString("INT: " + character.getIntelligence(), x, y + 45);
        g2d.drawString("WIS: " + character.getWisdom(), x, y + 60);
        g2d.drawString("CHA: " + character.getCharisma(), x, y + 75);
    }
    
    private void renderEquipment(Graphics2D g2d, Character character, int x, int y) {
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 11));
        
        g2d.drawString("EQUIPMENT:", x, y);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        
        Equipment eq = character.getEquipment();
        g2d.drawString("Weapon: " + eq.getWeapon().getName(), x, y + 15);
        g2d.drawString("Armor: " + eq.getArmor().getName(), x, y + 30);
        g2d.drawString("Helmet: " + eq.getHelmet().getName(), x, y + 45);
        g2d.drawString("Shield: " + eq.getShield().getName(), x, y + 60);
        g2d.drawString("Boots: " + eq.getBoots().getName(), x, y + 75);
    }
    
    private void renderSkills(Graphics2D g2d, Character character, int x, int y) {
        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font("Arial", Font.BOLD, 11));
        
        g2d.drawString("LEARNED SKILLS:", x, y);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        
        List<Skill> skills = character.getLearnedSkills();
        for (int i = 0; i < Math.min(5, skills.size()); i++) {
            g2d.drawString("• " + skills.get(i).getName(), x, y + 15 + (i * 15));
        }
    }
}
```

---

## EXAMPLE 4: Inventory System with Custom Frames

### Scenario
Build an inventory grid where each cell shows an item slot. Use custom frame selection for maximum visual control.

### Code
```java
public class InventoryWindowExample {
    
    private BufferedImage masterSheet;
    private BufferedImage inventoryFrame;
    private BufferedImage[] itemSlots;
    
    public void initialize() {
        masterSheet = AnimationAndSpriteLoader.loadMasterSpritesheet();
        
        // Build main inventory window: 224x192px = 7x6 cells
        // Use custom frame selection for total control
        int[] corners = {0, 2, 18, 27};        // Standard dark navy corners
        int[] edges = {1, 20, 4, 6};           // Standard edges
        int fillIndex = 5;                      // Standard navy fill
        
        inventoryFrame = AnimationAndSpriteLoader.assembleWindowFrameCustom(
            masterSheet,
            7,          // Width
            6,          // Height
            corners,
            edges,
            fillIndex
        );
        
        // Create item slot panels (6 rows × 4 columns = 24 slots)
        itemSlots = new BufferedImage[24];
        for (int i = 0; i < 24; i++) {
            // Each slot is 1x1 cell = 32x32px
            // Use inset square frames (indices 3, 37, 51, 60, 79, 80)
            // Extract directly from master sheet
            int slotFrame = 37; // Dark inset square
            itemSlots[i] = AnimationAndSpriteLoader.extractFrameFromMasterGrid(
                masterSheet, slotFrame
            );
        }
    }
    
    public void render(Graphics2D g2d, Inventory inventory) {
        // Draw main inventory window
        g2d.drawImage(inventoryFrame, 100, 50, null);
        
        // Draw title
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("INVENTORY", 120, 75);
        
        // Draw item slots in 4 columns
        int startX = 130;
        int startY = 100;
        int slotIndex = 0;
        
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (slotIndex >= 24) break;
                
                int x = startX + (col * 36);
                int y = startY + (row * 36);
                
                // Draw slot background
                g2d.drawImage(itemSlots[slotIndex], x, y, null);
                
                // Draw item if present
                Item item = inventory.getItemAt(slotIndex);
                if (item != null) {
                    BufferedImage itemIcon = item.getIcon();
                    if (itemIcon != null) {
                        g2d.drawImage(itemIcon, x + 2, y + 2, 28, 28, null);
                    }
                    
                    // Draw item quantity
                    if (item.getQuantity() > 1) {
                        g2d.setColor(Color.YELLOW);
                        g2d.setFont(new Font("Arial", Font.BOLD, 10));
                        g2d.drawString(String.valueOf(item.getQuantity()), 
                            x + 20, y + 28);
                    }
                }
                
                slotIndex++;
            }
        }
        
        // Draw total weight at bottom
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Total Weight: " + inventory.getTotalWeight() + " / " + 
            inventory.getMaxWeight(), 130, 320);
    }
}
```

---

## EXAMPLE 5: Caching System for Performance

### Scenario
You're rendering GUI every frame. Create a cache to avoid reassembling windows.

### Code
```java
public class GUIFrameCache {
    
    private static final Map<String, BufferedImage> CACHE = new HashMap<>();
    private static BufferedImage masterSheet;
    private static boolean initialized = false;
    
    /**
     * Initialize cache once at startup
     */
    public static void initialize() {
        if (initialized) return;
        
        masterSheet = AnimationAndSpriteLoader.loadMasterSpritesheet();
        if (masterSheet == null) {
            throw new RuntimeException("Failed to load master spritesheet!");
        }
        
        // Pre-cache common window sizes and styles
        preCache();
        
        initialized = true;
        System.out.println("[GUIFrameCache] Initialized with " + CACHE.size() + " cached frames");
    }
    
    /**
     * Pre-populate cache with common windows
     */
    private static void preCache() {
        // Small dialog: 96×96 = 3×3
        cacheWindow("dialog_small", 3, 3, "DARK_NAVY");
        cacheWindow("dialog_small_light", 3, 3, "LIGHT_NAVY");
        
        // Medium window: 160×128 = 5×4
        cacheWindow("window_medium", 5, 4, "DARK_NAVY");
        cacheWindow("window_medium_light", 5, 4, "LIGHT_NAVY");
        
        // Large window: 224×192 = 7×6
        cacheWindow("window_large", 7, 6, "DARK_NAVY");
        cacheWindow("window_large_light", 7, 6, "LIGHT_NAVY");
        
        // Status bar: 320×64 = 10×2
        cacheWindow("status_bar", 10, 2, "LIGHT_NAVY");
        
        // Character sheet: 192×160 = 6×5
        cacheWindow("character_sheet", 6, 5, "DARK_NAVY");
        
        System.out.println("[GUIFrameCache] Pre-cached " + CACHE.size() + " window frames");
    }
    
    /**
     * Get cached window or assemble and cache if not found
     */
    public static BufferedImage getWindow(String key, int cellsX, int cellsY, String style) {
        String cacheKey = key + "_" + cellsX + "_" + cellsY + "_" + style;
        
        if (!CACHE.containsKey(cacheKey)) {
            // Assemble and cache
            BufferedImage frame = AnimationAndSpriteLoader.assembleWindowFrame(
                masterSheet, cellsX, cellsY, style
            );
            if (frame != null) {
                CACHE.put(cacheKey, frame);
                System.out.println("[GUIFrameCache] Cached new window: " + cacheKey);
            }
        }
        
        return CACHE.get(cacheKey);
    }
    
    /**
     * Shortcut method with standard keys
     */
    public static BufferedImage getDialog() {
        return getWindow("dialog", 5, 4, "DARK_NAVY");
    }
    
    public static BufferedImage getStatusBar() {
        return getWindow("status_bar", 10, 2, "LIGHT_NAVY");
    }
    
    public static BufferedImage getCharacterSheet() {
        return getWindow("character_sheet", 6, 5, "DARK_NAVY");
    }
    
    /**
     * Clear cache (for memory cleanup)
     */
    public static void clearCache() {
        CACHE.clear();
        System.out.println("[GUIFrameCache] Cache cleared");
    }
    
    /**
     * Get cache statistics
     */
    public static void printStats() {
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  GUI FRAME CACHE STATISTICS");
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  Cached Frames: " + CACHE.size());
        System.out.println("  Master Sheet: " + (masterSheet != null ? 
            masterSheet.getWidth() + "x" + masterSheet.getHeight() : "NOT LOADED"));
        System.out.println("═══════════════════════════════════════════════");
        
        for (String key : CACHE.keySet()) {
            BufferedImage img = CACHE.get(key);
            System.out.println("  • " + key + ": " + img.getWidth() + "x" + img.getHeight());
        }
    }
}

/**
 * Usage in main game
 */
public class GameGUIRenderer {
    
    public void initialize() {
        GUIFrameCache.initialize();  // Load and cache at startup
    }
    
    public void render(Graphics2D g2d) {
        // Use cached windows - no assembly time!
        BufferedImage dialogFrame = GUIFrameCache.getDialog();
        g2d.drawImage(dialogFrame, 100, 100, null);
        
        BufferedImage statusFrame = GUIFrameCache.getStatusBar();
        g2d.drawImage(statusFrame, 0, 580, null);
        
        // Draw content on top (very fast)
        renderDialogContent(g2d);
        renderStatusContent(g2d);
    }
}
```

---

## EXAMPLE 6: Theme System

### Scenario
Support multiple UI themes (dark, light, team colors) and swap them easily.

### Code
```java
public enum UITheme {
    DARK_NAVY("DARK_NAVY", new Color(30, 50, 90)),
    LIGHT_NAVY("LIGHT_NAVY", new Color(80, 110, 160)),
    STANDARD_NAVY("STANDARD_NAVY", new Color(50, 80, 120)),
    RED_TEAM("DARK_NAVY", new Color(150, 30, 30)),        // Use dark frames with red tint
    BLUE_TEAM("LIGHT_NAVY", new Color(30, 80, 150)),
    GREEN_TEAM("STANDARD_NAVY", new Color(30, 140, 50));
    
    public final String frameStyle;
    public final Color accentColor;
    
    UITheme(String frameStyle, Color accentColor) {
        this.frameStyle = frameStyle;
        this.accentColor = accentColor;
    }
}

public class ThemedGUIRenderer {
    
    private UITheme currentTheme = UITheme.DARK_NAVY;
    private BufferedImage masterSheet;
    
    public void initialize() {
        masterSheet = AnimationAndSpriteLoader.loadMasterSpritesheet();
        setTheme(UITheme.DARK_NAVY);
    }
    
    public void setTheme(UITheme theme) {
        this.currentTheme = theme;
        System.out.println("GUI Theme changed to: " + theme.name());
        // Could clear cache here if supporting dynamic color tinting
    }
    
    public void render(Graphics2D g2d) {
        // All windows use current theme
        BufferedImage window = AnimationAndSpriteLoader.assembleWindowFrame(
            masterSheet,
            5, 4,
            currentTheme.frameStyle  // Uses theme's frame style
        );
        
        g2d.drawImage(window, 100, 100, null);
        
        // Draw content with theme accent color
        g2d.setColor(currentTheme.accentColor);
        g2d.fillRect(120, 120, 100, 20);
        
        g2d.setColor(Color.WHITE);
        g2d.drawString("Accent Color: " + currentTheme.name(), 130, 135);
    }
}
```

---

## KEY TAKEAWAYS FROM EXAMPLES

1. **Load Once**: Master spritesheet is loaded once at initialization
2. **Assemble Once**: Windows are assembled once and drawn repeatedly
3. **Cache for Performance**: Pre-cache common window sizes
4. **Compose**: Draw windows as backgrounds, then add UI elements on top
5. **Theme**: Use style parameters to switch visual themes
6. **Extract for Slots**: Use `extractFrameFromMasterGrid()` for individual cells/slots
7. **Custom Selection**: Use `assembleWindowFrameCustom()` for precise control

All examples are **production-ready** and can be used directly in your game engine!
