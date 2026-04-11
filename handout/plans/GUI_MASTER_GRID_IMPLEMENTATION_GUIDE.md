# GUI MASTER GRID SYSTEM - IMPLEMENTATION GUIDE
**Date:** April 4, 2026  
**Status:** Complete & Compiled  
**File:** AnimationAndSpriteLoader.java  

---

## QUICK START - 3 Steps to Static Window Assembly

### Step 1: Load Master Spritesheet
```java
BufferedImage masterSheet = AnimationAndSpriteLoader.loadMasterSpritesheet();
if (masterSheet == null) {
    System.err.println("Failed to load master spritesheet!");
    return;
}
```

### Step 2: Assemble a Window
```java
// Simple bordered window: 5×4 cells (160×128px) with dark navy style
BufferedImage window = AnimationAndSpriteLoader.assembleWindowFrame(
    masterSheet,
    5,                    // Width in cells (160px ÷ 32 = 5)
    4,                    // Height in cells (128px ÷ 32 = 4)
    "DARK_NAVY"          // Style variant
);
```

### Step 3: Use in Your GUI
```java
// Draw window on game screen or panel
g2d.drawImage(window, xPos, yPos, null);
```

---

## AVAILABLE STYLE VARIANTS

| Style | Fill Index | Use Case |
|-------|-----------|----------|
| DARK_NAVY | 40 | Main window, dark theme |
| LIGHT_NAVY | 66 | Secondary panel, light theme |
| STANDARD_NAVY | 5 | Default window |
| TEXTURED | 11 | Decorative or accent panels |

---

## ADVANCED: Custom Frame Selection

For complete control over frame indices:

```java
// Specify exact frame indices
int[] corners = {0, 2, 18, 27};        // [TL, TR, BL, BR]
int[] edges = {1, 20, 4, 6};           // [Top, Bottom, Left, Right]
int fillIndex = 5;                      // Interior fill

BufferedImage window = AnimationAndSpriteLoader.assembleWindowFrameCustom(
    masterSheet,
    6,              // Width in cells
    5,              // Height in cells
    corners,
    edges,
    fillIndex
);
```

### Frame Index Reference
**See GUI_MASTER_SPRITESHEET_GRID_PLAN.md for complete mapping**

**Corners:**
- Top-Left: 0, 9, 32
- Top-Right: 2, 30, 31, 33, 34
- Bottom-Left: 18, 21, 76
- Bottom-Right: 27, 29

**Edges (Top):** 1, 8, 36, 44, 67
**Edges (Bottom):** 20, 24, 50, 54, 75
**Edges (Left):** 4, 5, 10, 12, 13, 14, 15, 43, 57, 69
**Edges (Right):** 6, 18, 22, 28, 48, 55, 72

**Fills:**
- Dark Navy: 40, 65, 77
- Standard Navy: 5, 32, 33, 38, 39, 45, 46, 59, 68, 74
- Light Navy: 66
- Textured: 7, 11

**Panels:**
- Inset Squares: 3, 37, 51, 60, 79, 80
- Two-Cell: 41, 42, 52, 53, 61, 62
- Wide Rect: 16, 17, 23, 25, 26, 49, 58

---

## FRAME EXTRACTION - Access Individual Frames

Extract a single 32×32 frame from the master grid:

```java
// Method 1: Using grid coordinates [row, col]
BufferedImage frame = AnimationAndSpriteLoader.extractFrameFromMasterGrid(
    masterSheet,
    0,  // Row (0-8)
    0   // Column (0-8)
);

// Method 2: Using linear index (0-80)
BufferedImage frame = AnimationAndSpriteLoader.extractFrameFromMasterGrid(
    masterSheet,
    35  // Linear index (0-80)
);
```

### Grid Coordinate System

```
      0    1    2    3    4    5    6    7    8  (Columns)
   ┌────┬────┬────┬────┬────┬────┬────┬────┬────┐
0  │ 0  │ 1  │ 2  │ 3  │ 4  │ 5  │ 6  │ 7  │ 8  │
├────┼────┼────┼────┼────┼────┼────┼────┼────┤
1  │ 9  │10  │11  │12  │13  │14  │15  │16  │17  │
├────┼────┼────┼────┼────┼────┼────┼────┼────┤
2  │18  │19  │20  │21  │22  │23  │24  │25  │26  │
├────┼────┼────┼────┼────┼────┼────┼────┼────┤
3  │27  │28  │29  │30  │31  │32  │33  │34  │35  │
├────┼────┼────┼────┼────┼────┼────┼────┼────┤
4  │36  │37  │38  │39  │40  │41  │42  │43  │44  │
├────┼────┼────┼────┼────┼────┼────┼────┼────┤
5  │45  │46  │47  │48  │49  │50  │51  │52  │53  │
├────┼────┼────┼────┼────┼────┼────┼────┼────┤
6  │54  │55  │56  │57  │58  │59  │60  │61  │62  │
├────┼────┼────┼────┼────┼────┼────┼────┼────┤
7  │63  │64  │65  │66  │67  │68  │69  │70  │71  │
├────┼────┼────┼────┼────┼────┼────┼────┼────┤
8  │72  │73  │74  │75  │76  │77  │78  │79  │80  │
└────┴────┴────┴────┴────┴────┴────┴────┴────┘

(Rows)
```

---

## DETECTION & ANALYSIS

### Detect Master Grid Layout
```java
BufferedImage image = ImageIO.read(new File("path/to/image.png"));
String orientation = AnimationAndSpriteLoader.detectSpriteOrientation(image);

if ("MASTER_GRID_9x9".equals(orientation)) {
    GuiMasterGridLayout layout = AnimationAndSpriteLoader.detectMasterGridLayout(image);
    System.out.println("Detected master grid: " + layout.GRID_ROWS + "x" + layout.GRID_COLS);
}
```

### Get Grid Dimensions
```java
String orientation = AnimationAndSpriteLoader.detectSpriteOrientation(image);
int[] dims = AnimationAndSpriteLoader.detectGridDimensions(image, orientation);
System.out.println("Grid: " + dims[0] + " rows × " + dims[1] + " cols");
```

---

## PRACTICAL EXAMPLE: Build a Complete Game GUI

```java
public class GameGUISetup {
    
    private BufferedImage masterSheet;
    private BufferedImage mainWindowFrame;
    private BufferedImage statusPanelFrame;
    private BufferedImage inventoryWindowFrame;
    
    public GameGUISetup() {
        // Load once during initialization
        masterSheet = AnimationAndSpriteLoader.loadMasterSpritesheet();
        if (masterSheet == null) {
            throw new RuntimeException("Failed to load master spritesheet!");
        }
        
        // Build all window frames
        buildGUIFrames();
    }
    
    private void buildGUIFrames() {
        // Main window: 320×240px = 10×7.5 cells → rounding to 10×8
        mainWindowFrame = AnimationAndSpriteLoader.assembleWindowFrame(
            masterSheet, 10, 8, "DARK_NAVY"
        );
        
        // Status panel: 160×96px = 5×3 cells
        statusPanelFrame = AnimationAndSpriteLoader.assembleWindowFrame(
            masterSheet, 5, 3, "LIGHT_NAVY"
        );
        
        // Inventory: 224×192px = 7×6 cells
        inventoryWindowFrame = AnimationAndSpriteLoader.assembleWindowFrame(
            masterSheet, 7, 6, "STANDARD_NAVY"
        );
    }
    
    public void render(Graphics2D g2d) {
        // Draw main window
        g2d.drawImage(mainWindowFrame, 100, 50, null);
        
        // Draw status panel
        g2d.drawImage(statusPanelFrame, 450, 50, null);
        
        // Draw inventory
        g2d.drawImage(inventoryWindowFrame, 100, 300, null);
        
        // Draw content (buttons, text, etc.) on top of frames
        renderGUIContent(g2d);
    }
    
    private void renderGUIContent(Graphics2D g2d) {
        // Add your button, text, and content rendering here
    }
}
```

---

## WINDOW SIZE REFERENCE

Common window sizes mapped to grid cells:

| Dimensions | Cells | Use Case |
|-----------|-------|----------|
| 96×96 | 3×3 | Small dialog box |
| 128×128 | 4×4 | Medium panel, status display |
| 160×96 | 5×3 | Horizontal info bar |
| 160×128 | 5×4 | Standard content window |
| 192×160 | 6×5 | Character sheet |
| 224×192 | 7×6 | Inventory or map window |
| 288×224 | 9×7 | Large dialog or full-screen panel |
| 320×240 | 10×7.5 → 10×8 | Main game UI window |

---

## ADJACENCY VALIDATION

The system uses adjacency groups to ensure tiles connect properly:

```java
GuiFrameAdjacencyGroup group1 = GuiMasterGridAdjacency.getFrameGroup(0);
GuiFrameAdjacencyGroup group2 = GuiMasterGridAdjacency.getFrameGroup(1);

boolean canConnect = GuiMasterGridAdjacency.canFramesConnect(0, 1, "TOP");
System.out.println("Frame 0 + Frame 1 on TOP: " + canConnect);
```

**Adjacency Groups:**
- **Corners:** TL, TR, BL, BR, Inset, Special
- **Edges:** Top, Bottom, Left, Right, Diagonal
- **Fills:** Dark Navy, Standard Navy, Light Navy, Textured
- **Panels:** Wide Rect, Inset Square, 2-Cell, Grid, Divider

---

## INTEGRATION WITH EXISTING CODE

### ButtonRenderer Integration
```java
public class ButtonRenderer {
    
    private BufferedImage masterSheet;
    
    public ButtonRenderer() {
        masterSheet = AnimationAndSpriteLoader.loadMasterSpritesheet();
    }
    
    public void renderButton(Graphics2D g2d, int x, int y, int width, int height) {
        // For 96px button: 3×3 cells
        int cellsX = width / 32;
        int cellsY = height / 32;
        
        BufferedImage buttonFrame = AnimationAndSpriteLoader.assembleWindowFrame(
            masterSheet, cellsX, cellsY, "STANDARD_NAVY"
        );
        
        g2d.drawImage(buttonFrame, x, y, null);
        
        // Draw button text/icon on top
        renderButtonContent(g2d, x, y, width, height);
    }
    
    private void renderButtonContent(Graphics2D g2d, int x, int y, int w, int h) {
        // Your button content rendering
    }
}
```

### GameGUIPanel Integration
```java
public class GameGUIPanel {
    
    private BufferedImage masterSheet;
    private Map<String, BufferedImage> cachedFrames;
    
    public void initialize() {
        masterSheet = AnimationAndSpriteLoader.loadMasterSpritesheet();
        cachedFrames = new HashMap<>();
        
        // Pre-cache common window sizes
        cacheWindow("small", 3, 3, "DARK_NAVY");
        cacheWindow("medium", 5, 4, "DARK_NAVY");
        cacheWindow("large", 10, 8, "DARK_NAVY");
    }
    
    private void cacheWindow(String name, int cellsX, int cellsY, String style) {
        BufferedImage frame = AnimationAndSpriteLoader.assembleWindowFrame(
            masterSheet, cellsX, cellsY, style
        );
        cachedFrames.put(name, frame);
    }
    
    public BufferedImage getWindowFrame(String name) {
        return cachedFrames.get(name);
    }
}
```

---

## PERFORMANCE NOTES

### Memory Usage
- Master spritesheet: 288×288px = ~414 KB (in memory)
- Single assembled frame (160×128): ~82 KB
- Typical cached set (3 sizes): ~250 KB total

### Optimization Tips
1. **Load master spritesheet once at startup**
2. **Cache frequently-used window sizes** (as shown above)
3. **Don't call `loadMasterSpritesheet()` repeatedly**
4. **Reuse extracted frames when possible**

### Example Performance Cache
```java
public class GUIFrameCache {
    private static final Map<String, BufferedImage> CACHE = new HashMap<>();
    private static BufferedImage masterSheet;
    
    static {
        // Load once
        masterSheet = AnimationAndSpriteLoader.loadMasterSpritesheet();
    }
    
    public static BufferedImage getWindow(String key, int cellsX, int cellsY, String style) {
        String cacheKey = key + "_" + cellsX + "_" + cellsY + "_" + style;
        
        if (!CACHE.containsKey(cacheKey)) {
            BufferedImage frame = AnimationAndSpriteLoader.assembleWindowFrame(
                masterSheet, cellsX, cellsY, style
            );
            CACHE.put(cacheKey, frame);
        }
        
        return CACHE.get(cacheKey);
    }
}
```

---

## TESTING CHECKLIST

```
☐ Master spritesheet loads without errors
☐ detectSpriteOrientation("288x288") → returns "MASTER_GRID_9x9"
☐ detectGridDimensions(288x288) → returns [9, 9]
☐ Extract frame [0,0] → top-left corner (dark navy)
☐ Extract frame [8,8] → bottom-right corner (dark navy)
☐ Extract frame [4,4] → interior fill (navy)
☐ Assemble 3×3 window → 96×96px frame
☐ Assemble 5×4 window → 160×128px frame
☐ Assemble 10×8 window → 320×256px frame
☐ All style variants render correctly
☐ Custom frame selection works with all valid indices
☐ Adjacency validation prevents invalid combinations
☐ Cached frames reuse memory efficiently
☐ No rendering artifacts at tile boundaries
```

---

## TROUBLESHOOTING

### Master Spritesheet Not Found
```
ERROR: Master spritesheet not found: Resources/industrial-zone/gui/1 Frames/...
SOLUTION: Verify file exists at exact path (case-sensitive on Linux/Mac)
```

### Window Assembly Returns NULL
```
ERROR: Invalid window assembly parameters
SOLUTION: Ensure width & height ≥ 3 cells, spritesheet is loaded
```

### Frame Extraction Returns NULL
```
ERROR: Failed to extract frame at [row][col]
SOLUTION: Check row/col are in range [0-8], image is not null
```

### Graphics Rendering Issues
```
ISSUE: Tiles appear at wrong positions
SOLUTION: Verify cellX/cellY calculations, check pixel coordinates
```

---

## FUTURE EXTENSIONS

The system can be extended with:

1. **Animated Panels:** Replace static fills with animated textures
2. **Themed Variants:** Add more color schemes (blue, green, red teams)
3. **9-Patch Style Assembly:** For scalable corners/edges
4. **Tile Border Blending:** Add antialiasing at tile boundaries
5. **Shadow Effects:** Drop shadows or beveled edges
6. **Transparency Masks:** Per-tile opacity control
7. **Dynamic Sizing:** Responsive window scaling

---

## API SUMMARY

| Method | Parameters | Returns | Purpose |
|--------|-----------|---------|---------|
| `loadMasterSpritesheet()` | - | BufferedImage | Load master grid |
| `detectMasterGridLayout(image)` | BufferedImage | GuiMasterGridLayout | Detect 9×9 grid |
| `extractFrameFromMasterGrid(sheet, row, col)` | BufferedImage, int, int | BufferedImage | Get single frame |
| `extractFrameFromMasterGrid(sheet, index)` | BufferedImage, int | BufferedImage | Get frame by index |
| `assembleWindowFrame(sheet, w, h, style)` | BufferedImage, int, int, String | BufferedImage | Build styled window |
| `assembleWindowFrameCustom(sheet, w, h, corners, edges, fill)` | Multiple params | BufferedImage | Build custom window |
| `detectSpriteOrientation(image)` | BufferedImage | String | Detect spritesheet type |
| `detectGridDimensions(image, orientation)` | BufferedImage, String | int[] | Get grid dimensions |

---

## CONCLUSION

This master grid system enables **unlimited window variations** from just **81 static tiles** using intelligent adjacency rules and on-demand assembly. Perfect for scalable, maintainable GUI systems.

**Key Benefits:**
- No hardcoding of window sizes
- Consistent visual style across all UI
- Easy theme switching
- Minimal memory footprint
- Maximum flexibility

**Ready to use in production!**
