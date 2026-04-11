# GUI MASTER GRID EXTRACTOR - PRACTICAL USAGE GUIDE
**Date:** April 4, 2026  
**Framework:** AnimationAndSpriteLoader.java (GuiMasterGridExtractor class)  
**Spritesheet:** 82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png (288x288px, 9x9 grid)

---

## QUICK START - Using Master Grid Tiles

### 1. Initialize Master Spritesheet

```java
// At application startup
String masterSpritesheetPath = "Resources/industrial-zone/gui/1 Frames/" +
    "82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png";

boolean loaded = GuiMasterGridExtractor.loadMasterSpritesheet(masterSpritesheetPath);

if (loaded) {
    System.out.println("Master spritesheet loaded! Ready to extract tiles.");
}
```

### 2. Extract Individual Tiles

```java
// Extract a single 32x32 tile from grid position
BufferedImage cornerTL = GuiMasterGridExtractor.extractTile(0, 0);  // Top-left corner
BufferedImage fill = GuiMasterGridExtractor.extractTile(2, 4);      // Navy fill
BufferedImage topEdge = GuiMasterGridExtractor.extractTile(0, 3);   // Top edge

// Draw to screen
Graphics g = myPanel.getGraphics();
g.drawImage(cornerTL, 50, 50, null);
g.drawImage(fill, 82, 50, null);  // 32px to the right
```

### 3. Extract Multi-Tile Regions

```java
// Extract a 64x32 region (2 tiles wide, 1 tile high)
// Grid position [2,0] to [2,1] = Top-right corner (2-wide)
BufferedImage cornerTR = GuiMasterGridExtractor.extractRegion(2, 0, 1, 2);

// Extract a 96x96 region (3x3 tiles)
BufferedImage fill3x3 = GuiMasterGridExtractor.extractRegion(4, 4, 3, 3);
```

### 4. Compose Complete Window Frames

```java
// Build a window frame automatically
// Parameters: width in tiles, height in tiles, include accent panels

// Standard 5x5 tile window (160x160 pixels)
BufferedImage window5x5 = GuiMasterGridExtractor.composeWindowFrame(5, 5, false);

// Larger window with accent panels (7x7 tiles = 224x224 pixels)
BufferedImage window7x7 = GuiMasterGridExtractor.composeWindowFrame(7, 7, true);

// Wide window (10x6 tiles = 320x192 pixels)
BufferedImage windowWide = GuiMasterGridExtractor.composeWindowFrame(10, 6, false);
```

---

## GRID POSITION REFERENCE

### Standard Tile Positions

```
Key Positions in 9x9 Grid (coordinates are [row, col]):

CORNERS:
  [0,0]   = Top-Left Corner (L-shaped)
  [2,0-1] = Top-Right Corner (2-tiles wide)
  [8,3]   = Bottom-Left Corner
  [8,6]   = Bottom-Right Corner

EDGES:
  [0,3-5] = Top Edge bars (tiling)
  [8,1-2] = Bottom Edge left section
  [8,4-5] = Bottom Edge right section
  [1,0]   = Left Edge (continue vertically)
  [0,8]   = Right Edge (continue vertically)

FILLS:
  [2,4-5] = Navy blue fill (standard)
  [3,2-3] = Navy blue fill (continue)
  [5,0-5] = Large fill area
  [6,0-2] = Continue fill
  [7,0-2] = Continue fill

PANELS:
  [3,4-5] = Inset squares (small content boxes)
  [4,7]   = 2-cell panel (right side)
  [5,7]   = 2-cell panel (continue down)
  [6,7]   = 2-cell panel (continue down)
  [7,7]   = 2-cell panel (bottom of stack)

ACCENT AREAS:
  [6,3-5] = Blue/cyan accent panels (2 rows × 3 columns)
  [7,3-5] = Accent panels continue
```

---

## USE CASE EXAMPLES

### Example 1: Draw a Simple 4x4 Window

```java
// Create and display a 4x4 tile window (128x128px)
BufferedImage window = GuiMasterGridExtractor.composeWindowFrame(4, 4, false);

JLabel windowLabel = new JLabel(new ImageIcon(window));
add(windowLabel);
```

### Example 2: Multiple Windows with Different Sizes

```java
// Create window panel with 3 different sized windows
JPanel windowPanel = new JPanel(new GridLayout(1, 3));

BufferedImage smallWindow = GuiMasterGridExtractor.composeWindowFrame(4, 4, false);
BufferedImage mediumWindow = GuiMasterGridExtractor.composeWindowFrame(6, 5, true);
BufferedImage largeWindow = GuiMasterGridExtractor.composeWindowFrame(10, 8, true);

windowPanel.add(new JLabel(new ImageIcon(smallWindow)));
windowPanel.add(new JLabel(new ImageIcon(mediumWindow)));
windowPanel.add(new JLabel(new ImageIcon(largeWindow)));

add(windowPanel);
```

### Example 3: Custom Frame Assembly (Manual)

```java
// Manually assemble specific tiles for custom layout
BufferedImage customFrame = new BufferedImage(
    160, 160,  // 5x5 tiles = 160x160 pixels
    BufferedImage.TYPE_INT_ARGB
);

Graphics2D g2d = (Graphics2D) customFrame.getGraphics();

// Place corners
g2d.drawImage(GuiMasterGridExtractor.extractTile(0, 0), 0, 0, null);
g2d.drawImage(GuiMasterGridExtractor.extractRegion(2, 0, 1, 2), 128, 0, null);
g2d.drawImage(GuiMasterGridExtractor.extractTile(8, 3), 0, 128, null);
g2d.drawImage(GuiMasterGridExtractor.extractTile(8, 6), 128, 128, null);

// Place edges and fill (as needed)
// ...

g2d.dispose();

// Display custom frame
JLabel customLabel = new JLabel(new ImageIcon(customFrame));
add(customLabel);
```

### Example 4: Using with GUI Components

```java
public class GameWindow extends JFrame {
    private JPanel guiPanel;
    
    public GameWindow() {
        setTitle("Game GUI with Master Tiles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Load master spritesheet
        GuiMasterGridExtractor.loadMasterSpritesheet(
            "Resources/industrial-zone/gui/1 Frames/" +
            "82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png"
        );
        
        // Create GUI panel
        guiPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGameUI(g);
            }
        };
        
        add(guiPanel);
        setSize(800, 600);
        setVisible(true);
    }
    
    private void drawGameUI(Graphics g) {
        // Draw main window
        BufferedImage mainWindow = GuiMasterGridExtractor.composeWindowFrame(10, 8, true);
        g.drawImage(mainWindow, 50, 50, null);
        
        // Draw stat panels
        BufferedImage panelWindow = GuiMasterGridExtractor.composeWindowFrame(4, 4, false);
        g.drawImage(panelWindow, 500, 100, null);
        g.drawImage(panelWindow, 500, 250, null);
    }
}
```

### Example 5: Cache Management

```java
// Useful for repeated window generation with same tiles

// Generate windows
for (int i = 0; i < 10; i++) {
    BufferedImage window = GuiMasterGridExtractor.composeWindowFrame(5, 5, false);
    // Use window...
}

// Check how many tiles are cached
int cacheSize = GuiMasterGridExtractor.getCacheSize();
System.out.println("Cached tiles: " + cacheSize);  // Should be 81 or less

// Clear cache if needed (e.g., when switching screens)
GuiMasterGridExtractor.clearCache();
```

---

## ADJACENCY GROUP REFERENCE

The master grid is organized by **adjacency groups**. Understanding these helps with custom tile placement:

### Adjacency Groups

| Group | Indices | Tiles | Purpose |
|-------|---------|-------|---------|
| **CORNER_TL** | 0 | Top-left corner | Must connect top+left edges |
| **CORNER_TR** | 2, 30, 31, 33, 34 | Top-right corners | Must connect top+right edges |
| **CORNER_BL** | 18, 21, 76 | Bottom-left corners | Must connect bottom+left edges |
| **CORNER_BR** | 27, 29 | Bottom-right corners | Must connect bottom+right edges |
| **CORNER_INSET** | 9 | Inset variant | Alternative corner style |
| **EDGE_TOP** | 1, 8, 36, 44, 67 | Top edges | Tile horizontally |
| **EDGE_BOTTOM** | 20, 24, 50, 54, 75 | Bottom edges | Tile horizontally |
| **EDGE_LEFT** | 4, 5, 10, 12, 13, 14, 15, 43, 57, 69 | Left edges | Tile vertically |
| **EDGE_RIGHT** | 6, 18, 22, 28, 48, 55, 72 | Right edges | Tile vertically |
| **FILL_NAVY** | 5, 32, 33, 38, 39, 45, 46, 59, 68, 74 | Standard fills | Interior background |
| **FILL_NAVY_DARK** | 40, 65, 77 | Dark fills | Darker interior variant |
| **PANEL_INSET** | 3, 37, 51, 60, 79, 80 | Single cells | Content boxes (32x32) |
| **PANEL_2CELL** | 41, 42, 52, 53, 61, 62, 70 | Two-cell panels | Wider content (64x32) |
| **PANEL_WIDE_RECT** | 16, 17, 23, 25, 26, 35, 49, 58 | Wide bars | Dividers/spacers (64x32+) |

---

## PERFORMANCE NOTES

### Tile Caching
- Extracted tiles are automatically cached in memory
- First extraction of a tile takes longer (subimage operation)
- Subsequent accesses to same tile are instant (from cache)
- Cache size grows with usage (max 81 tiles = ~256KB)

### Memory Usage
- Full 288x288 master spritesheet: ~0.3MB
- Cached 32x32 tile: ~4KB
- Composed window frame: ~0.1-0.5MB (depending on size)

### Optimization Tips
1. Load master spritesheet once at startup
2. Compose frequently-used windows once and reuse
3. Use `extractRegion()` for multi-tile pieces (more efficient)
4. Call `clearCache()` when switching major screens
5. For animations, extract tiles once and cache

---

## TROUBLESHOOTING

### Master Spritesheet Not Loading
```java
// Check file path exists
File masterFile = new File("Resources/industrial-zone/gui/1 Frames/" +
    "82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png");

if (!masterFile.exists()) {
    System.err.println("Master spritesheet not found!");
} else {
    GuiMasterGridExtractor.loadMasterSpritesheet(masterFile.getAbsolutePath());
}
```

### Tile at Position Returns Null
```java
// Validate grid coordinates before extraction
int row = 0, col = 0;

if (row >= 0 && row < 9 && col >= 0 && col < 9) {
    BufferedImage tile = GuiMasterGridExtractor.extractTile(row, col);
    if (tile != null) {
        // Use tile
    } else {
        System.err.println("Tile extraction failed!");
    }
} else {
    System.err.println("Invalid grid position: [" + row + "," + col + "]");
}
```

### Window Frame Not Composing
```java
// Check minimum size requirements
if (windowWidthTiles >= 3 && windowHeightTiles >= 4) {
    BufferedImage frame = GuiMasterGridExtractor.composeWindowFrame(
        windowWidthTiles, windowHeightTiles, false
    );
} else {
    System.err.println("Window too small (minimum 3x4 tiles)");
}
```

---

## NEXT STEPS

1. **Import the grid extractor** into your GUI rendering code
2. **Initialize the master spritesheet** at application start
3. **Use `composeWindowFrame()`** for static window generation
4. **Cache composed frames** for frequently-used window sizes
5. **Deploy with master PNG** included in Resources folder
