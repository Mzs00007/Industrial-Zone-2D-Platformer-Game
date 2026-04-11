# GUI MASTER SPRITESHEET - 9x9 GRID ADJACENCY SYSTEM
**Date:** April 4, 2026  
**Spritesheet:** 82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png  
**Dimensions:** 288x288px (9x9 grid, each frame = 32px)  
**Total Frames:** 81 cells (standard 9x9 grid indexing)

---

## GRID LAYOUT ANALYSIS

### Visual Organization by Row (top to bottom)

```
GRID COORDINATES FORMAT: [ROW][COL] = INDEX (0-80)
Each cell is 32x32 pixels
Row 0: Indices 0-8
Row 1: Indices 9-17
Row 2: Indices 18-26
Row 3: Indices 27-35
Row 4: Indices 36-44
Row 5: Indices 45-53
Row 6: Indices 54-62
Row 7: Indices 63-71
Row 8: Indices 72-80
```

### Complete 9x9 Grid Mapping

#### **ROW 0 (Top Row) - Indices 0-8: Corners & Top Edges**
| Col | Index | Function | Type | Adjacency Group |
|-----|-------|----------|------|-----------------|
| 0 | 0 | Corner Top-Left | Corner | Corner_TL |
| 1 | 1 | Edge Top Bar (accent) | Edge-Top | EdgeTop_1 |
| 2 | 2 | Corner Top-Right | Corner | Corner_TR |
| 3 | 3 | Inset Square (dark) | Panel-Cell | InsetSquare_1 |
| 4 | 4 | Edge Left (tall) | Edge-Left | EdgeLeft_1 |
| 5 | 5 | Fill Solid Navy | Fill | FillNavy_1 |
| 6 | 6 | Edge Right (tall) | Edge-Right | EdgeRight_1 |
| 7 | 7 | Decorative Cross Pattern | Fill | DecorativeFill_1 |
| 8 | 8 | Edge Top Right Variant | Edge-Top | EdgeTop_2 |

#### **ROW 1 (Indices 9-17) - Mixed Corners & Edges**
| Col | Index | Function | Type | Adjacency Group |
|-----|-------|----------|------|-----------------|
| 0 | 9 | Corner Inset Top-Left | Corner | Corner_Inset_TL |
| 1 | 10 | Edge Left Blue Tint | Edge-Left | EdgeLeft_2 |
| 2 | 11 | Fill Diagonal Texture | Fill | FillTexture_1 |
| 3 | 12 | Edge Left Dark | Edge-Left | EdgeLeft_3 |
| 4 | 13 | Edge Left Thin Light | Edge-Left | EdgeLeft_4 |
| 5 | 14 | Edge Left Wider Trim | Edge-Left | EdgeLeft_5 |
| 6 | 15 | Edge Left Light Blue | Edge-Left | EdgeLeft_6 |
| 7 | 16 | Panel Wide Rect (teal) | Panel-Wide | PanelWide_1 |
| 8 | 17 | Panel Wide Rect Dark | Panel-Wide | PanelWide_2 |

#### **ROW 2 (Indices 18-26) - Corners & Bottom Edges**
| Col | Index | Function | Type | Adjacency Group |
|-----|-------|----------|------|-----------------|
| 0 | 18 | Corner Bottom-Left | Corner | Corner_BL |
| 1 | 19 | [Reserved - Empty or thin piece] | Border | - |
| 2 | 20 | Edge Bottom Bar | Edge-Bottom | EdgeBottom_1 |
| 3 | 21 | Corner Bottom-Left Full | Corner | Corner_BL_Full |
| 4 | 22 | Edge Right Plain | Edge-Right | EdgeRight_2 |
| 5 | 23 | Panel Wide Tech Texture | Panel-Wide | PanelWide_3 |
| 6 | 24 | Edge Bottom Dot Texture | Edge-Bottom | EdgeBottom_2 |
| 7 | 25 | Panel Wide Plain | Panel-Wide | PanelWide_4 |
| 8 | 26 | Panel Wide Dark Variant | Panel-Wide | PanelWide_5 |

#### **ROW 3 (Indices 27-35) - Large Fill Area & Tall Panels**
| Col | Index | Function | Type | Adjacency Group |
|-----|-------|----------|------|-----------------|
| 0 | 27 | Corner Bottom-Right | Corner | Corner_BR |
| 1 | 28 | Edge Right Blue Accent | Edge-Right | EdgeRight_3 |
| 2 | 29 | Corner Bottom-Right Full | Corner | Corner_BR_Full |
| 3 | 30 | Corner Top-Right Bracket | Corner | Corner_TR_Bracket |
| 4 | 31 | Corner Top-Right Light Trim | Corner | Corner_TR_Light |
| 5 | 32 | Fill Solid Navy (large) | Fill | FillNavy_2 |
| 6 | 33 | Fill Solid Navy variant | Fill | FillNavy_3 |
| 7 | 34 | Corner Top-Right Diagonal Cut | Corner | Corner_TR_DiagCut |
| 8 | 35 | Panel Horizontal Divider | Panel-Divider | PanelDivider_1 |

#### **ROW 4 (Indices 36-44) - Wide Horizontal Panels & Colors**
| Col | Index | Function | Type | Adjacency Group |
|-----|-------|----------|------|-----------------|
| 0 | 36 | Edge Top Bar Lighter | Edge-Top | EdgeTop_3 |
| 1 | 37 | Panel Inset Square Dark | Panel-Cell | InsetSquare_2 |
| 2 | 38 | Fill Solid Navy Wide | Fill | FillNavy_4 |
| 3 | 39 | Fill Solid Navy Wider | Fill | FillNavy_5 |
| 4 | 40 | Fill Solid Dark Navy | Fill | FillNavy_Dark_1 |
| 5 | 41 | Panel 2-Cell (two insets) | Panel-2Cell | Panel2Cell_1 |
| 6 | 42 | Panel 2-Cell Variant | Panel-2Cell | Panel2Cell_2 |
| 7 | 43 | Edge Left Narrow Dark | Edge-Left | EdgeLeft_7 |
| 8 | 44 | Edge Horizontal Bar | Edge-Horiz | EdgeHoriz_1 |

#### **ROW 5 (Indices 45-53) - Fill & Small Panels**
| Col | Index | Function | Type | Adjacency Group |
|-----|-------|----------|------|-----------------|
| 0 | 45 | Fill Solid Navy Flat | Fill | FillNavy_6 |
| 1 | 46 | Fill Solid Navy Wide Flat | Fill | FillNavy_7 |
| 2 | 47 | Edge Diagonal Strip (angled) | Edge-Diagonal | EdgeDiag_1 |
| 3 | 48 | Edge Right Narrow | Edge-Right | EdgeRight_4 |
| 4 | 49 | Panel Horizontal Bar | Panel-Bar | PanelBar_1 |
| 5 | 50 | Edge Bottom Bar Accent | Edge-Bottom | EdgeBottom_3 |
| 6 | 51 | Panel Inset Square | Panel-Cell | InsetSquare_3 |
| 7 | 52 | Panel 2-Cell Variant 2 | Panel-2Cell | Panel2Cell_3 |
| 8 | 53 | Panel 2-Cell Variant 3 | Panel-2Cell | Panel2Cell_4 |

#### **ROW 6 (Indices 54-62) - Bottom Edges & Wider Panels**
| Col | Index | Function | Type | Adjacency Group |
|-----|-------|----------|------|-----------------|
| 0 | 54 | Edge Bottom Light Blue | Edge-Bottom | EdgeBottom_4 |
| 1 | 55 | Edge Right Slightly Wider | Edge-Right | EdgeRight_5 |
| 2 | 56 | Edge Diagonal (varied) | Edge-Diagonal | EdgeDiag_2 |
| 3 | 57 | Edge Left Dark Variant | Edge-Left | EdgeLeft_8 |
| 4 | 58 | Panel Wide Inset Border | Panel-Wide | PanelWide_6 |
| 5 | 59 | Fill Solid Navy Wide Flat | Fill | FillNavy_8 |
| 6 | 60 | Panel Medium Tall | Panel-Cell | PanelMedium_1 |
| 7 | 61 | Panel 2-Cell with Icons | Panel-2Cell | Panel2Cell_5 |
| 8 | 62 | Panel 2-Cell Icons Alt | Panel-2Cell | Panel2Cell_6 |

#### **ROW 7 (Indices 63-71) - Corner Variants & Grid Pieces**
| Col | Index | Function | Type | Adjacency Group |
|-----|-------|----------|------|-----------------|
| 0 | 63 | Corner Hexagonal Shape | Corner | Corner_Hex |
| 1 | 64 | Corner Diagonal Top-Left | Corner | Corner_DiagTL |
| 2 | 65 | Fill Dark Navy Plain | Fill | FillNavy_Dark_2 |
| 3 | 66 | Fill Navy Lighter | Fill | FillNavy_Light_1 |
| 4 | 67 | Edge Top Plain Bar | Edge-Top | EdgeTop_4 |
| 5 | 68 | Fill Navy Plain Block | Fill | FillNavy_9 |
| 6 | 69 | Edge Left Bar | Edge-Left | EdgeLeft_9 |
| 7 | 70 | Panel 2-Cell Wide Divider | Panel-2Cell | Panel2Cell_7 |
| 8 | 71 | Panel 2-Cell Grid Block | Panel-Grid | PanelGrid_1 |

#### **ROW 8 (Bottom Row) - Indices 72-80: Small Pieces & Bottom Variants**
| Col | Index | Function | Type | Adjacency Group |
|-----|-------|----------|------|-----------------|
| 0 | 72 | Edge Thin Right | Edge-Right | EdgeRight_6 |
| 1 | 73 | Corner Frame (unused) | Corner | Corner_Special |
| 2 | 74 | Fill Navy Plain (repeat) | Fill | FillNavy_10 |
| 3 | 75 | Edge Bottom Plain Strip | Edge-Bottom | EdgeBottom_5 |
| 4 | 76 | Corner Bottom Small Notch | Corner | Corner_BL_Notch |
| 5 | 77 | Fill Dark Navy | Fill | FillNavy_Dark_3 |
| 6 | 78 | [Small accent piece] | Border | BorderAccent_1 |
| 7 | 79 | Panel Inset Dark Square | Panel-Cell | InsetSquare_4 |
| 8 | 80 | Panel Inset Lighter Square | Panel-Cell | InsetSquare_5 |

---

## ADJACENCY GROUPING RULES

### **PRINCIPLE: Static Frame Windows are Built from Grouped Tiles**

When assembling a complete window/panel frame, we use adjacency rules to automatically select tiles that match and connect properly.

### **Group 1: CORNERS (16 tiles)**
**Primary Indices:** 0, 2, 18, 27, 9, 21, 29, 30, 32, 33, 34, 63, 64, 31, 73, 76

**Adjacency Rules:**
- **Top-Left (TL):** Index 0, 9, 32 (compatible with EdgeTop_1,2,3,4 and EdgeLeft_1,2,3,4,5,6,7,8,9)
- **Top-Right (TR):** Index 2, 8, 30, 31, 33, 34 (compatible with EdgeTop_1,2,3,4 and EdgeRight_1,2,3,4,5,6)
- **Bottom-Left (BL):** Index 18, 21, 76 (compatible with EdgeBottom_1,2,3,4,5 and EdgeLeft_1,2,3,4,5,6,7,8,9)
- **Bottom-Right (BR):** Index 27, 29 (compatible with EdgeBottom_1,2,3,4,5 and EdgeRight_1,2,3,4,5,6)
- **Special Corners:** Index 63 (hex), 64 (diagonal), 73 (unused)

### **Group 2: EDGES (22 tiles)**

**Top Edge Variants:** 1, 8, 36, 44, 50, 54, 67
- **Compatibility Rules:**
  - Can be placed horizontally adjacent to each other
  - Must connect TL (0, 9) and TR (2, 30, 31) corners
  - Width = 32px (1 tile), can be tiled horizontally

**Bottom Edge Variants:** 20, 24, 50, 54, 75
- **Compatibility Rules:**
  - Must connect BL (18, 21, 76) and BR (27, 29) corners
  - Can tile horizontally for longer bottom bars

**Left Edge Variants:** 4, 5, 10, 12, 13, 14, 15, 43, 57, 69
- **Compatibility Rules:**
  - Must connect TL corner (0, 9) and BL corner (18, 21, 76)
  - Height = 32px, can tile vertically for tall windows
  - All variants are interchangeable vertically

**Right Edge Variants:** 6, 18, 22, 28, 48, 55, 72
- **Compatibility Rules:**
  - Must connect TR corner (2, 30, 31) and BR corner (27, 29)
  - Height = 32px, can tile vertically
  - Variants for different visual styles

### **Group 3: FILLS (13 tiles)**
**Indices:** 5, 7, 11, 32, 33, 38, 39, 40, 45, 46, 59, 65, 66, 68, 74, 77

**Adjacency Rules:**
- **Internal Fill:** All fills in this group can be used to fill interior space
- **Tile-ability:** Each fill tile is 32x32
  - For large window interiors (>32px), tile fills horizontally AND vertically
  - Prefer matching shades (Dark Navy, Light Navy, Textured)

**Shade Categories:**
- **Dark Navy:** 40, 65, 77 (darkest interior)
- **Standard Navy:** 5, 32, 33, 38, 39, 45, 46, 59, 68, 74
- **Light Navy:** 66
- **Textured:** 7 (decorative), 11 (diagonal lines)

### **Group 4: PANELS (18 tiles)**
**Indices:** 16, 17, 23, 25, 26, 35, 37, 41, 42, 49, 51, 52, 53, 58, 60, 61, 62, 70, 71

**Adjacency Rules:**

**4A - Wide Rectangular Panels (Dividers):**
- **Indices:** 16, 17, 23, 25, 26, 35, 49, 58, 70
- **Width:** 64px (2 tiles wide) or 96px (3 tiles)
- **Height:** 32px (single row)
- **Function:** Horizontal dividers, section separators
- **Tiling:** Can connect end-to-end horizontally

**4B - Single-Cell Inset Squares:**
- **Indices:** 3, 37, 51, 60, 79, 80
- **Size:** 32x32px
- **Function:** Content boxes, status displays
- **Adjacency:** Can be placed adjacently for layouts (left-right, top-bottom)

**4C - Two-Cell Panel Blocks:**
- **Indices:** 41, 42, 52, 53, 61, 62
- **Size:** 64x32px (two cells wide)
- **Function:** Wider content areas, stat rows
- **Variants:** Different shades (dark, light, icon variants)

**4D - Grid & Layout Pieces:**
- **Indices:** 70, 71
- **Function:** Multi-cell grids, complex layouts
- **Connectivity:** Can stack vertically for grid structures

### **Group 5: SPECIAL PIECES**
- **Decorative fills (Group 3 variants):** 7, 11 for accent areas
- **Diagonal edges (Group 2 variants):** 47, 56 for angled window designs

---

## STATIC FRAME COMPOSITION ALGORITHM

### **Window Assembly Pattern 1: Standard Bordered Window**

```
┌─────────────────────┐
│ TL │ TB │ TB │ TB │ TR │
├─────────────────────┤
│ LT │ Fill │ Fill │ Fill │ RT │
├──────────────────────┤
│ LB │ Fill │ Fill │ Fill │ RB │
├─────────────────────┤
│ BL │ BB │ BB │ BB │ BR │
└─────────────────────┘
```

**Cell Allocation (5x4 example = 160x128px):**
- **Corners (4 cells):** TL[0], TR[2], BL[18], BR[27]
- **Top Edge (3 cells):** [1], [1], [1] or variants [8], [36], [44]
- **Bottom Edge (3 cells):** [20], [20], [20] or variants
- **Left Edge (2 cells):** [4], [4] (tile vertically)
- **Right Edge (2 cells):** [6], [6]
- **Interior Fills (6 cells):** [5], [5], [5], [5], [5], [5]

### **Window Assembly Pattern 2: Panel-Based Layout**

```
┌──────────────────────┐
│ TL │ TE Wide │ TE Wide │ TR │
├─────────────────────┤
│ LE │ Panel-2Cell │ Panel-2Cell │ RE │
├──────────────────────┤
│ LE │ Inset-1Cell │ Inset-1Cell │ RE │
├─────────────────────┤
│ BL │ Bottom Wide │ Bottom Wide │ BR │
└──────────────────────┘
```

**Cell Allocation (4x4 example = 128x128px):**
- **Frame:** Corners + edges (same as Pattern 1)
- **Content:** Mix of inset squares [3, 37, 51] and 2-cell panels [41, 42, 52, 53]
- **Dividers:** Panel wide pieces [16, 17, 23] as horizontal separators

---

## IMPLEMENTATION STRATEGY FOR GridSpritesheet LOADER

### **Phase 1: Detect Master Spritesheet Layout**

```java
// New method in AnimationAndSpriteLoader
public static GuiMasterGridLayout detectMasterGridLayout(BufferedImage image) {
    if (image.getWidth() == 288 && image.getHeight() == 288) {
        return new GuiMasterGridLayout(9, 9, 32); // 9x9 grid, 32px cells
    }
    return null;
}
```

### **Phase 2: Extract Frame by Grid Index**

```java
// Extract individual frame from master spritesheet
public static BufferedImage extractFrameFromMasterGrid(
    BufferedImage masterSheet, int gridRow, int gridCol, int frameSize) {
    
    int x = gridCol * frameSize;
    int y = gridRow * frameSize;
    
    return masterSheet.getSubimage(x, y, frameSize, frameSize);
}
```

### **Phase 3: Apply Adjacency Rules for Window Construction**

```java
// Build complete window using adjacency groups
public static BufferedImage assembleWindowFrame(
    BufferedImage masterSheet,
    int windowWidthInCells,
    int windowHeightInCells,
    String styleVariant) {
    
    // Select corner indices based on style
    int[] corners = selectCornersByStyle(styleVariant); // [TL, TR, BL, BR]
    
    // Select edge indices (will tile as needed)
    int[] topEdges = selectEdgesByStyle(EdgePosition.TOP, styleVariant);
    int[] bottomEdges = selectEdgesByStyle(EdgePosition.BOTTOM, styleVariant);
    int[] leftEdges = selectEdgesByStyle(EdgePosition.LEFT, styleVariant);
    int[] rightEdges = selectEdgesByStyle(EdgePosition.RIGHT, styleVariant);
    
    // Select fill index (single, will tile for interior)
    int fillIndex = selectFillByStyle(styleVariant);
    
    // Assemble tiles into complete frame
    return buildFrameFromComponents(masterSheet, corners, edges, fillIndex);
}
```

### **Phase 4: Caching & Performance**

Pre-generate and cache common window sizes (using adjacency rules):
- Small: 96x96px (3x3 grid cells)
- Medium: 160x128px (5x4 grid cells)
- Large: 224x192px (7x6 grid cells)

---

## DETAILED CODE UPDATES REQUIRED

### **1. New Nested Class: `GuiMasterGridLayout`**
```java
public static class GuiMasterGridLayout {
    public int rows;
    public int cols;
    public int frameSizePixels;
    public int totalFrames;
    
    // 9x9 grid with 82 named frames
    public String[] frameNames = new String[81];
    public GuiFrameAdjacencyGroup[] adjacencyGroups;
}
```

### **2. Enhance `GridFrameAdjacency` System**
- Define 5 primary adjacency groups (Corners, Edges, Fills, Panels, Special)
- Map each group to compatible indices
- Implement validation: "Can this corner connect to this edge?"

### **3. Update `detectSpriteOrientation()` Method**
```
Special case: If image is 288x288 and aspect ratio = 1.0:
  return "MASTER_GRID_9x9"
```

### **4. Update `detectGridDimensions()` Method**
```
If orientation == "MASTER_GRID_9x9":
  return new int[]{9, 9}  // 9 rows, 9 columns
```

### **5. New Method: `analyzeGridFrameAdjacentCompatibility()`**
- Input: Two frame indices
- Output: boolean canConnect (for specified edge positions)
- Uses adjacency groups for validation

### **6. New Method: `generateStaticFrameFromPattern()`**
- Input: Pattern type (BorderedWindow, PanelLayout, etc), dimensions, style
- Output: BufferedImage of assembled complete frame
- Automatically selects tiles from adjacency groups

---

## USAGE EXAMPLES IN CODE

### **Example 1: Load Master Spritesheet & Extract Single Frame**
```java
BufferedImage master = ImageIO.read(new File(masterPath));
GuiMasterGridLayout layout = detectMasterGridLayout(master);
if (layout != null) {
    // Extract frame at grid position [0][0] (corner)
    BufferedImage cornerTL = extractFrameFromMasterGrid(master, 0, 0, 32);
}
```

### **Example 2: Assemble Static Window Frame (Bordered)**
```java
BufferedImage master = ImageIO.read(new File(masterPath));
BufferedImage window = assembleWindowFrame(
    master,
    5,      // 5 cells wide = 160px
    4,      // 4 cells tall = 128px
    "DARK_NAVY"  // style variant
);
```

### **Example 3: Check Adjacency Before Tiling**
```java
int cornerTL_Index = 0;
int edgeTop_Index = 1;
boolean canConnect = canFramesConnect(cornerTL_Index, edgeTop_Index, 
    EdgePosition.TOP_RIGHT);
// Result: true (corner connects to edge on right side)
```

---

## EXPECTED BENEFITS

1. **Modular GUI Assembly:** Build any window size from 81 base tiles
2. **Style Variants:** 5+ complete themes by swapping color groups
3. **No Hardcoding:** Adjacency rules handle connections automatically
4. **Scalability:** Can extend to 10x10, 12x12 grids with same logic
5. **Performance:** Precomputed window caches reduce runtime rendering

---

## FILES TO MODIFY

1. **AnimationAndSpriteLoader.java** (main updates)
   - Add `GuiMasterGridLayout` nested class
   - Add `GuiFrameAdjacencyGroup` enum
   - Update `detectSpriteOrientation()`
   - Update `detectGridDimensions()`
   - Add `analyzeGridFrameAdjacentCompatibility()`
   - Add `assembleWindowFrame()`
   - Add `extractFrameFromMasterGrid()`
   - Add `generateStaticFrameFromPattern()`

2. **GuiFrameAssetProperties.java** (integration)
   - Add reference to master spritesheet grid layout
   - Document how each old file maps to grid indices

3. **ButtonRenderer.java** (usage)
   - Use assembled frames for button backgrounds

4. **GameGUIPanel.java** (usage)
   - Use assembled frames for window frames

---

## TESTING CHECKLIST

☐ Load 288x288 PNG → Correctly detect as 9x9 grid  
☐ Extract frame [0,0] → Returns top-left corner  
☐ Extract frame [8,8] → Returns bottom-right corner  
☐ Extract middle frame [4,4] → Returns fill piece  
☐ Assemble 3x3 window → Returns correct 96x96 frame  
☐ Assemble 5x5 window → Returns correct 160x160 frame  
☐ Adjacency check: corner+edge → Returns true  
☐ Adjacency check: corner+corner → Returns false  
☐ Generate 5 style variants → Each uses different color groups  
☐ Verify tiling works for wide/tall edges  
☐ Verify fill tiling covers interior correctly  

---

## CONCLUSION

This 9x9 master grid system transforms 81 static tiles into an unlimited number of complete window frames through intelligent adjacency rules. Instead of pre-rendering 100 different frame sizes, we:

1. Define 5 adjacency groups (how tiles connect)
2. Select tiles from appropriate groups based on style
3. Assemble them into any size window on-demand
4. Cache results for performance

This is **highly scalable, maintainable, and performant** for a GUI system.
