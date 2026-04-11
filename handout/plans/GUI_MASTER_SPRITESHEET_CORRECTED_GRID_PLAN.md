# GUI MASTER SPRITESHEET - CORRECTED 9x9 GRID ADJACENCY SYSTEM
**Date:** April 4, 2026  
**Source Image:** 82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png (with grid overlay)  
**Dimensions:** 288x288px (9x9 grid, each frame = 32px)  
**Total Frames:** 81 cells (9 rows × 9 columns)

---

## ACCURATE GRID POSITION MAP

Based on visual analysis of the gridded reference image, this is the ACTUAL layout of frame positions:

```
GRID COORDINATES: [ROW][COL] = Visual Description
Each cell is 32x32 pixels
Rows: 0-8 (top to bottom)
Cols: 0-8 (left to right)
```

### **ROW 0 (Top Row) - Y: 0-32px**

| [0,0] | [0,1] | [0,2] | [0,3] | [0,4] | [0,5] | [0,6] | [0,7] | [0,8] |
|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| **Large Corner TL** | **Small Corner Accent** | **Small Corner Accent** | **Horiz Bar** | **Horiz Bar** | **Horiz Bar** | **Empty/Black** | **Vert Bar** | **Vert Bar** |
| Wide L-shape | Rivet red dot | Rivet red dot | Top edge accent | Top edge accent | Top edge accent | Background black | Right edge | Right edge |

**Analysis Row 0:**
- [0,0]: Large corner piece Top-Left (L-shaped, white border visible)
- [0,1-0,2]: Small corner accent variants with rivet details
- [0,3-0,5]: Horizontal bar edges (top bar variants)
- [0,6]: Empty space or black background tile
- [0,7-0,8]: Vertical bar edges (right edge strips)

---

### **ROW 1 (Y: 32-64px)**

| [1,0] | [1,1] | [1,2] | [1,3] | [1,4] | [1,5] | [1,6] | [1,7] | [1,8] |
|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| **Corner TL Inset** | **Vert Bar** | **Empty** | **Empty** | **Empty** | **Empty** | **Empty** | **Empty** | **Vert Bar** |
| Corner piece | Left edge | Black | Black | Black | Black | Black | Black | Right edge |

**Analysis Row 1:**
- [1,0]: Corner piece continuation (inset variant)
- [1,1]: Vertical left edge bar
- [1,2-1,7]: Empty/black background tiles
- [1,8]: Vertical right edge bar

---

### **ROW 2 (Y: 64-96px)**

| [2,0] | [2,1] | [2,2] | [2,3] | [2,4] | [2,5] | [2,6] | [2,7] | [2,8] |
|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| **Corner TR Area** | **Corner TR Part** | **Vert Bar** | **Empty** | **Fill Navy** | **Fill Navy** | **Empty** | **Empty** | **Vert Bar** |
| Top-right section | Top-right part | Right edge | Black |Dark blue solid | Dark blue solid | Black | Black | Right edge |

**Analysis Row 2:**
- [2,0-2,1]: Top-right corner area pieces
- [2,2]: Vertical right edge continuation
- [2,3]: Empty/black
- [2,4-2,5]: Fill tiles (solid navy interior)
- [2,6-2,7]: Empty/black
- [2,8]: Vertical right edge

---

### **ROW 3 (Y: 96-128px)**

| [3,0] | [3,1] | [3,2] | [3,3] | [3,4] | [3,5] | [3,6] | [3,7] | [3,8] |
|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| **Vert Bar** | **Empty** | **Large Fill** | **Large Fill** | **Panel Inset** | **Panel Inset** | **Empty** | **Horiz Bar** | **Horiz Bar** |
| Left edge |Black | Dark navy fill | Dark navy fill | Small square | Small square | Black | Top edge | Top edge |

**Analysis Row 3:**
- [3,0]: Vertical left edge bar
- [3,1]: Empty/black
- [3,2-3,3]: Large fill area (navy blue solid)
- [3,4-3,5]: Panel inset squares (small dark bordered squares)
- [3,6]: Empty/black
- [3,7-3,8]: Horizontal top edges

---

### **ROW 4 (Y: 128-160px)**

| [4,0] | [4,1] | [4,2] | [4,3] | [4,4] | [4,5] | [4,6] | [4,7] | [4,8] |
|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| **Vert Bar** | **Vert Bar (Rivet)** | **Vert Bar (Rivet)** | **Vert Bar** | **Empty** | **Empty** | **Empty** | **2-Cell Panel** | **Fill** |
| Left edge | Left edge rivet | Left edge rivet | Left edge | Black | Black | Black | Two inset squares | Navy fill |

**Analysis Row 4:**
- [4,0]: Left vertical edge bar
- [4,1-4,2]: Left edge bars with rivet/accent pattern
- [4,3]: Left edge continuation
- [4,4-4,6]: Empty/black background
- [4,7]: 2-cell panel (two horizontal inset squares)
- [4,8]: Fill navy solid

---

### **ROW 5 (Y: 160-192px)**

| [5,0] | [5,1] | [5,2] | [5,3] | [5,4] | [5,5] | [5,6] | [5,7] | [5,8] |
|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| **Fill** | **Fill** | **Fill** | **Fill Area** | **Fill Area** | **Fill Area** | **Empty** | **2-Cell Panel** | **Fill** |
| Navy | Navy | Navy | Navy | Navy | Navy | Black | Two inset squares | Navy |

**Analysis Row 5:**
- [5,0-5,5]: Large fill area (solid navy interior)
- [5,6]: Empty/black
- [5,7]: 2-cell panel
- [5,8]: Fill navy

---

### **ROW 6 (Y: 192-224px)**

| [6,0] | [6,1] | [6,2] | [6,3] | [6,4] | [6,5] | [6,6] | [6,7] | [6,8] |
|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| **Fill** | **Fill** | **Fill** | **Blue Panel** | **Blue Panel** | **Blue Panel** | **Empty** | **2-Cell Panel** | **Fill** |
| Navy | Navy | Navy | Colored inset | Colored inset | Colored inset | Black | Two inset squares | Navy |

**Analysis Row 6:**
- [6,0-6,2]: Continue fill area
- [6,3-6,5]: Blue/cyan colored panel pieces (accent panels)
- [6,6]: Empty/black
- [6,7]: 2-cell panel
- [6,8]: Fill navy

---

### **ROW 7 (Y: 224-256px)**

| [7,0] | [7,1] | [7,2] | [7,3] | [7,4] | [7,5] | [7,6] | [7,7] | [7,8] |
|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| **Fill** | **Fill** | **Fill** | **Blue Panel** | **Blue Panel** | **Blue Panel** | **Bottom Edge** | **2-Cell Panel** | **Horiz Bar** |
| Navy | Navy | Navy | Colored inset | Colored inset | Colored inset | Bottom edge | Two inset squares | Bottom bar |

**Analysis Row 7:**
- [7,0-7,2]: Continue fill area
- [7,3-7,5]: Continue blue/cyan panel pieces
- [7,6]: Bottom edge bar
- [7,7]: 2-cell panel
- [7,8]: Horizontal bottom edge bar

---

### **ROW 8 (Bottom Row) (Y: 256-288px)**

| [8,0] | [8,1] | [8,2] | [8,3] | [8,4] | [8,5] | [8,6] | [8,7] | [8,8] |
|-------|-------|-------|-------|-------|-------|-------|-------|-------|
| **Fill** | **Bottom Edge** | **Bottom Edge** | **Corner BL** | **Bottom Edge** | **Bottom Edge** | **Corner BR** | **Empty** | **Horiz Bar** |
| Navy | Bottom bar | Bottom bar | Bottom-left corner | Bottom bar | Bottom bar | Bottom-right corner | Black | Right edge |

**Analysis Row 8:**
- [8,0]: Fill navy (continue from above)
- [8,1-8,2]: Bottom edge bars
- [8,3]: Corner Bottom-Left
- [8,4-8,5]: Bottom edge bars
- [8,6]: Corner Bottom-Right
- [8,7]: Empty/black
- [8,8]: Horizontal bottom edge bar (right side)

---

## TILE GROUPING BY FUNCTION

### **GROUP A: CORNER PIECES (8 major pieces)**

| Position | Type | Description | Dimensions | Adjacency |
|----------|------|-------------|------------|-----------|
| [0,0] | Corner TL | Large L-shaped top-left corner | 32x32 | Must connect to: EdgeTop or EdgeLeft |
| [2,0-2,1] | Corner TR | Top-right corner (2-cell wide) | 64x32 | Must connect to: EdgeTop or EdgeRight |
| [8,3] | Corner BL | Bottom-left corner | 32x32 | Must connect to: EdgeBottom or EdgeLeft |
| [8,6] | Corner BR | Bottom-right corner | 32x32 | Must connect to: EdgeBottom or EdgeRight |
| [1,0] | Corner TL-Inset | Inset variant of TL | 32x32 | Alternate for TL position |
| (additional corner variants in edges) | - | - | - | - |

**Adjacency Rules for Corners:**
- Top-Left corners must connect: EdgeTop above/right + EdgeLeft below
- Top-Right corners must connect: EdgeTop above/left + EdgeRight below
- Bottom-Left corners must connect: EdgeBottom below/right + EdgeLeft above
- Bottom-Right corners must connect: EdgeBottom below/left + EdgeRight above

---

### **GROUP B: EDGE PIECES (20+ pieces distributed)**

#### **Top Edges (Horizontal bars)**
- **Positions:** [0,3], [0,4], [0,5], [3,7], [3,8], [7,8]
- **Size:** 32x32 each (can tile horizontally)
- **Function:** Top window border
- **Adjacency Rules:**
  - Can connect end-to-end horizontally
  - Must connect TL corner [0,0] on left and TR corner [2,0-2,1] on right
  - Sits directly above interior fill area

#### **Bottom Edges (Horizontal bars)**
- **Positions:** [8,1], [8,2], [8,4], [8,5], [7,6]
- **Size:** 32x32 each (can tile horizontally)
- **Function:** Bottom window border
- **Adjacency Rules:**
  - Can connect end-to-end horizontally
  - Must connect BL corner [8,3] on left and BR corner [8,6] on right
  - Sits directly below interior fill area

#### **Left Edges (Vertical bars)**
- **Positions:** [0,0], [1,0], [3,0], [4,0], [4,1], [4,2], [4,3], [7,0], [8,0]
- **Size:** 32x32 each (can tile vertically)
- **Function:** Left window border
- **Adjacency Rules:**
  - Can connect end-to-end vertically
  - Must connect TL corner [0,0] on top and BL corner [8,3] on bottom
  - Can have rivet/accent variants (rows 4,1-4,2)

#### **Right Edges (Vertical bars)**
- **Positions:** [0,7], [0,8], [1,8], [2,2], [2,8], [3,8], [4,8], [7,8], [8,8]
- **Size:** 32x32 each (can tile vertically)
- **Function:** Right window border
- **Adjacency Rules:**
  - Can connect end-to-end vertically
  - Must connect TR corner [2,0-2,1] on top and BR corner [8,6] on bottom
  - Mirrors left edge variants

---

### **GROUP C: FILL PIECES (Interior Navy Blue)**

#### **Solid Navy Fills**
- **Positions:** [2,4], [2,5], [3,2], [3,3], [5,0-5,5], [6,0-6,2], [7,0-7,2], [8,0]
- **Size:** 32x32 each
- **Density:** ~22-25 tiles of solid fill
- **Function:** Interior background for windows and panels
- **Adjacency Rules:**
  - **Tiling:** Can tile both horizontally AND vertically freely
  - **Placement:** Fills entire interior space between edges and corners
  - **Shade Variants:** All appear to be standard navy blue (consistent shade)
  - **Flexibility:** Any fill can be adjacent to any other fill in any direction

**Fill Algorithm:**
- Calculate interior width and height in grid cells
- Fill all positions between [top_edge_row] and [bottom_edge_row], [left_edge_col] and [right_edge_col]
- Use consistent navy fill tiles for uniformity

---

### **GROUP D: PANEL PIECES (Content Boxes & Dividers)**

#### **Single-Cell Inset Squares (32x32)**
- **Positions:** [3,4], [3,5]
- **Type:** Dark-bordered square with inset/3D effect
- **Function:** Single content box, status indicator
- **Adjacency Rules:**
  - Can be placed horizontally adjacent (left-right)
  - Can be placed vertically adjacent (top-bottom)
  - Maintains consistent border style

#### **Two-Cell Panels (64x32)**
- **Positions:** [4,7], [5,7], [6,7], [7,7]
- **Type:** Two adjacent inset squares in one 64x32 tile
- **Function:** Wider content area, dual stats row
- **Quantity:** 4 vertical stack
- **Adjacency Rules:**
  - Can stack vertically (as shown in current layout)
  - Interior fill goes to left of these panels
  - Right edge connects to right edge tiles

#### **Colored Panel Sections (Accent/Blue panels)**
- **Positions:** [6,3-6,5], [7,3-7,5] (2 rows × 3 columns)
- **Type:** Blue/cyan colored inset panels
- **Function:** Accent or special content areas
- **Adjacency Rules:**
  - Block of 3 columns × 2 rows
  - Can replace solid fills in accent areas
  - Maintains visual break in interface

**Tile Layout Pattern:** The panel stacking on the right side ([4,7], [5,7], [6,7], [7,7]) shows vertical composition - 4 tiles high = 128px tall panel area.

---

## WINDOW COMPOSITION ALGORITHM

### **Standard Frame Assembly (2-Column Layout Example)**

Using the grid positions from the spritesheet:

```
┌──────────────────────┐
│ [0,0] │ [0,3-0,5]    │              Row 0: Corners + Top Edges
├───────┬──────────────┤
│[1-4,0]│    FILL      │              Rows 1-4: Left Edge + Interior Fill
│       │              │
├───────┼──────────────┤
│[8,3]  │ [8,1-2,4-5]  │ [8,6]        Row 8: BL Corner + Bottom Edges + BR Corner
└───────┴──────────────┘

Right Edge at column 8 (vertical stack)
Interior Fill Area: [2,4-5] through [7,0-7,5] approximately
```

### **Algorithm Steps:**

1. **Place Corners**: Position [0,0], [2,0-2,1], [8,3], [8,6]
   
2. **Span Edges**:
   - Top: [0,3-0,5] tiles (tile horizontally between corners)
   - Bottom: [8,1-2], [8,4-5] tiles
   - Left: [1-4,0], [4,1-2,3] (tile vertically)
   - Right: [0-8, 8] (tile vertically)

3. **Fill Interior**: Use navy fill tiles to fill all space between edges
   
4. **Add Content Panels**: Place [4,7], [5,7], [6,7], [7,7] panels or use [6,3-5] accent area

5. **Extract as Complete Frame**: Composite all tiles into single window sprite

---

## STATIC FRAME EXTRACTION CODE STRUCTURE

```java
// ============================================================
// MASTER GRID SPRITESHEET EXTRACTOR
// Maps 9x9 grid positions to tile extraction
// ============================================================

public class MasterGridSpriteExtractor {
    
    // Grid dimensions (all in grid units where 1 unit = 32px)
    public static final int GRID_SIZE = 9; // 9x9 grid
    public static final int TILE_SIZE = 32; // pixels per tile
    public static final int SPRITESHEET_SIZE = 288; // 9 * 32
    
    // Grid position constants for quick reference
    public static class GridPositions {
        // Corners
        public static final int[] CORNER_TL = {0, 0};
        public static final int[] CORNER_TR = {2, 0}; // 2-cell wide, col 0-1
        public static final int[] CORNER_BL = {8, 3};
        public static final int[] CORNER_BR = {8, 6};
        
        // Top edges: row 0, cols 3-5 and row 3 cols 7-8
        public static final int[] TOP_EDGE_MAIN = {0, 3}; // Can span to [0,5]
        public static final int[] TOP_EDGE_RIGHT = {3, 7}; // Can span to {3,8]
        
        // Bottom edges: row 8, cols 1-2 and 4-5
        public static final int[] BOT_EDGE_LEFT = {8, 1}; // Can span to [8,2]
        public static final int[] BOT_EDGE_RIGHT = {8, 4}; // Can span to [8,5]
        
        // Left edge: col 0, rows 0-8; additional rivet variant at row 4 cols 1-2
        public static final int[] LEFT_EDGE_MAIN = {0, 0}; // Can span down
        public static final int[] LEFT_EDGE_RIVET = {4, 1}; // Variant with rivet [4,1-2]
        
        // Right edge: col 8, rows 0-8
        public static final int[] RIGHT_EDGE = {0, 8}; // Can span down
        
        // Interior fills (navy blue)
        public static final int[] FILL_TOP_LEFT = {2, 4}; // Zone: rows 2-7, cols 0-5
        public static final int[] FILL_BLUE_ACCENT = {6, 3}; // Blue panels: rows 6-7, cols 3-5
        
        // Right side panels: 4-cell tall stack
        public static final int[] PANEL_STACK = {4, 7}; // Stack goes down 4 rows
    }
    
    /**
     * Extract a tile from the master spritesheet at grid position
     * @param masterSheet Full 288x288 spritesheet
     * @param gridRow Row in 9x9 grid (0-8)
     * @param gridCol Column in 9x9 grid (0-8)
     * @return BufferedImage of the 32x32 tile
     */
    public static BufferedImage extractTileAtGridPosition(
            BufferedImage masterSheet, int gridRow, int gridCol) {
        
        if (masterSheet == null || gridRow < 0 || gridRow > 8 || gridCol < 0 || gridCol > 8) {
            return null;
        }
        
        int pixelX = gridCol * TILE_SIZE;
        int pixelY = gridRow * TILE_SIZE;
        
        // Extract 32x32 region
        return masterSheet.getSubimage(pixelX, pixelY, TILE_SIZE, TILE_SIZE);
    }
    
    /**
     * Extract multiple tiles as a single composite image
     * Used for wide tiles or panel stacks
     * @param masterSheet Full 288x288 spritesheet
     * @param startRow Starting grid row
     * @param startCol Starting grid col
     * @param heightInTiles Height in grid units
     * @param widthInTiles Width in grid units
     * @return Composite BufferedImage
     */
    public static BufferedImage extractTileRegion(
            BufferedImage masterSheet,
            int startRow, int startCol,
            int heightInTiles, int widthInTiles) {
        
        int pixelWidth = widthInTiles * TILE_SIZE;
        int pixelHeight = heightInTiles * TILE_SIZE;
        int pixelX = startCol * TILE_SIZE;
        int pixelY = startRow * TILE_SIZE;
        
        // Extract rectangular region
        return masterSheet.getSubimage(pixelX, pixelY, pixelWidth, pixelHeight);
    }
    
    /**
     * Compose a complete window frame from grid positions
     * @param masterSheet Full spritesheet
     * @param windowWidthInTiles Desired window width in tiles
     * @param windowHeightInTiles Desired window height in tiles
     * @return Complete window sprite
     */
    public static BufferedImage composeWindowFrame(
            BufferedImage masterSheet,
            int windowWidthInTiles,
            int windowHeightInTiles) {
        
        // Create output canvas
        BufferedImage frame = new BufferedImage(
            windowWidthInTiles * TILE_SIZE,
            windowHeightInTiles * TILE_SIZE,
            BufferedImage.TYPE_INT_ARGB
        );
        
        Graphics2D g2d = (Graphics2D) frame.getGraphics();
        
        // 1. Place top-left corner [0,0]
        BufferedImage tlCorner = extractTileAtGridPosition(masterSheet, 0, 0);
        g2d.drawImage(tlCorner, 0, 0, null);
        
        // 2. Place top edges [0,3] through [0,5] for width-2 tiles
        if (windowWidthInTiles >= 2) {
            for (int i = 1; i < windowWidthInTiles - 1; i++) {
                BufferedImage topEdge = extractTileAtGridPosition(masterSheet, 0, 3);
                g2d.drawImage(topEdge, i * TILE_SIZE, 0, null);
            }
        }
        
        // 3. Place top-right corner [2,0] for rightmost position
        BufferedImage trCorner = extractTileAtGridPosition(masterSheet, 2, 0);
        g2d.drawImage(trCorner, (windowWidthInTiles - 1) * TILE_SIZE, 0, null);
        
        // 4. Place left edges [0,0] down column 0
        BufferedImage leftEdge = extractTileAtGridPosition(masterSheet, 1, 0);
        for (int i = 1; i < windowHeightInTiles - 1; i++) {
            g2d.drawImage(leftEdge, 0, i * TILE_SIZE, null);
        }
        
        // 5. Fill interior with navy fills [2,4] or variants
        BufferedImage fill = extractTileAtGridPosition(masterSheet, 2, 4);
        for (int row = 1; row < windowHeightInTiles - 1; row++) {
            for (int col = 1; col < windowWidthInTiles - 1; col++) {
                g2d.drawImage(fill, col * TILE_SIZE, row * TILE_SIZE, null);
            }
        }
        
        // 6. Place right edges [0,8] down last column
        BufferedImage rightEdge = extractTileAtGridPosition(masterSheet, 0, 8);
        for (int i = 1; i < windowHeightInTiles - 1; i++) {
            g2d.drawImage(rightEdge, (windowWidthInTiles - 1) * TILE_SIZE, 
                          i * TILE_SIZE, null);
        }
        
        // 7. Place bottom-left corner [8,3]
        BufferedImage blCorner = extractTileAtGridPosition(masterSheet, 8, 3);
        g2d.drawImage(blCorner, 0, (windowHeightInTiles - 1) * TILE_SIZE, null);
        
        // 8. Place bottom edges [8,1-2] for middle section
        for (int i = 1; i < windowWidthInTiles - 1; i++) {
            BufferedImage botEdge = extractTileAtGridPosition(masterSheet, 8, 1);
            g2d.drawImage(botEdge, i * TILE_SIZE, (windowHeightInTiles - 1) * TILE_SIZE, null);
        }
        
        // 9. Place bottom-right corner [8,6]
        BufferedImage brCorner = extractTileAtGridPosition(masterSheet, 8, 6);
        g2d.drawImage(brCorner, (windowWidthInTiles - 1) * TILE_SIZE,
                      (windowHeightInTiles - 1) * TILE_SIZE, null);
        
        g2d.dispose();
        return frame;
    }
}
```

---

## KEY DIFFERENCES FROM PREVIOUS PLAN

1. **Accurate Grid Mapping**: Positions based on visual analysis of the gridded screenshot
2. **Correct Tile Locations**: Corners, edges, fills placed at actual grid coordinates
3. **Clear Adjacency Rules**: Which tiles connect to which based on position in grid
4. **Practical Code Example**: Shows actual tile extraction from grid positions
5. **Static Frame Composition**: Algorithm for building windows from these grid tiles

---

## NEXT INTEGRATION STEPS

### Step 1: Update AnimationAndSpriteLoader.java
- Add GridPositions inner class with all coordinate constants
- Implement extractTileAtGridPosition() method
- Implement extractTileRegion() for multi-tile pieces

### Step 2: Update Frame Detection Logic
- Add detectMasterGridSpritesheet() method
- Recognize 288x288 size as master grid
- Return grid position constants instead of estimating

### Step 3: Implement Window Composition
- Add composeWindowFrame() for static window generation
- Cache extracted tiles to avoid repeated extractions
- Generate standard window sizes (6x4, 8x6, 10x8 tiles, etc.)

### Step 4: Load Tiles from Master Layout
- When drawing GUI, use extractTileAtGridPosition instead of loading individual files
- Composite dynamically or cache pre-composed frames
- Performance improvement: single 288px texture vs 82 separate files
