# GUI MASTER GRID - QUICK REFERENCE CARD
**Grid Size:** 9×9 (288×288px total, 32×32px per tile)  
**File:** `82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png`  
**Date:** April 4, 2026

---

## GRID DIAGRAM WITH TILE TYPES

```
    0           1           2           3           4           5           6           7           8
0 ┌─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┐
  │ Corner  │ Accent  │ Accent  │ Edge    │ Edge    │ Edge    │ Empty   │ Edge    │ Edge    │
  │ TL      │ (Red)   │ (Red)   │ Top[0,3]│ Top[0,4]│ Top[0,5]│ Black   │ Right   │ Right   │
  └─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┘
1 ┌─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┐
  │ Corner  │ Edge    │ Empty   │ Empty   │ Empty   │ Empty   │ Empty   │ Empty   │ Edge    │
  │ TL-I[1,0]│ Left[1,1]        │ Black Area              │ Empty   │ Right   │
  └─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┘
2 ┌─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┐
  │ Corner  │ Corner  │ Edge    │ Empty   │ Fill    │ Fill    │ Empty   │ Empty   │ Edge    │
  │ TR-Pt1  │ TR-Pt2  │ Right   │ Black   │ Navy[2,4]│Navy[2,5]│ Black   │ Empty   │ Right   │
  │[2,0]    │[2,1]    │[2,2]    │         │         │         │         │         │[2,8]    │
  └─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┘
3 ┌─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┐
  │ Edge    │ Empty   │ Fill    │ Fill    │ Inset   │ Inset   │ Empty   │ Edge    │ Edge    │
  │ Left    │ Black   │ Navy    │ Navy    │ Cell[3,4]│Cell[3,5]│ Black   │ Top     │ Top     │
  │[3,0]    │         │[3,2]    │[3,3]    │         │         │         │[3,7]    │[3,8]    │
  └─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┘
4 ┌─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┐
  │ Edge    │ Edge    │ Edge    │ Edge    │ Empty   │ Empty   │ Empty   │ Panel   │ Fill    │
  │ Left    │ Left*   │ Left*   │ Left    │ Black   │ Black   │ Black   │ 2Cell   │ Navy    │
  │[4,0]    │[4,1]    │[4,2]    │[4,3]    │         │         │         │[4,7]    │[4,8]    │
  └─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┘
5 ┌─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┐
  │ Fill    │ Fill    │ Fill    │ Fill    │ Fill    │ Fill    │ Empty   │ Panel   │ Fill    │
  │ Navy    │ Navy    │ Navy    │ Navy    │ Navy    │ Navy    │ Black   │ 2Cell   │ Navy    │
  │[5,0]    │[5,1]    │[5,2]    │[5,3]    │[5,4]    │[5,5]    │         │[5,7]    │[5,8]    │
  └─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┘
6 ┌─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┐
  │ Fill    │ Fill    │ Fill    │ Blue*   │ Blue*   │ Blue*   │ Empty   │ Panel   │ Fill    │
  │ Navy    │ Navy    │ Navy    │ Accent  │ Accent  │ Accent  │ Black   │ 2Cell   │ Navy    │
  │[6,0]    │[6,1]    │[6,2]    │[6,3]    │[6,4]    │[6,5]    │         │[6,7]    │[6,8]    │
  └─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┘
7 ┌─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┐
  │ Fill    │ Fill    │ Fill    │ Blue*   │ Blue*   │ Blue*   │ Edge    │ Panel   │ Edge    │
  │ Navy    │ Navy    │ Navy    │ Accent  │ Accent  │ Accent  │ Bottom  │ 2Cell   │ Top     │
  │[7,0]    │[7,1]    │[7,2]    │[7,3]    │[7,4]    │[7,5]    │[7,6]    │[7,7]    │[7,8]    │
  └─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┘
8 ┌─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┐
  │ Fill    │ Edge    │ Edge    │ Corner  │ Edge    │ Edge    │ Corner  │ Empty   │ Edge    │
  │ Navy    │ Bottom  │ Bottom  │ BL      │ Bottom  │ Bottom  │ BR      │ Black   │ Top     │
  │[8,0]    │[8,1]    │[8,2]    │[8,3]    │[8,4]    │[8,5]    │[8,6]    │         │[8,8]    │
  └─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┘

Legend:
  TL, TR, BL, BR = Top/Bottom Left/Right corners
  TL-I = Top-left Inset variant
  TR-Pt = Top-right split in 2 tiles
  Left, Right = Vertical edge bars
  Edge Top, Edge Bottom = Horizontal bars
  Left*, Edge* = Variant with rivet/accent marks
  Blue* = Colored accent panel (can be tiled)
  Inset Cell = Small 32×32 content boxes
  Panel 2Cell = Two-cell wide panel (64×32)
  Navy = Navy blue solid fill
  Black = Black/empty background
```

---

## TILE POSITION LOOKUP TABLE

### By Category - Copy Grid Coordinates Directly

#### Corners (4 main pieces)
```
[0, 0]  → Top-Left Corner (L-shaped, 32×32)
[2, 0]  → Top-Right Corner Part 1 (2-tiles wide, starts here)
[2, 1]  → Top-Right Corner Part 2 (continuation)
[8, 3]  → Bottom-Left Corner (32×32)
[8, 6]  → Bottom-Right Corner (32×32)
```

#### Top Edge (for horizontal tiling)
```
[0, 3] → Top Edge variant 1
[0, 4] → Top Edge variant 2
[0, 5] → Top Edge variant 3
[3, 7] → Alternative top edge
[3, 8] → Alternative top edge
[7, 8] → Alternative top edge
```

#### Bottom Edge (for horizontal tiling)
```
[8, 1] → Bottom Edge variant 1
[8, 2] → Bottom Edge variant 2
[8, 4] → Bottom Edge variant 3
[8, 5] → Bottom Edge variant 4
[7, 6] → Alternative bottom edge
```

#### Left Edge (for vertical tiling)
```
[1, 0] → Left Edge plain
[3, 0] → Left Edge plain
[4, 0] → Left Edge plain
[4, 1] → Left Edge with rivet accent
[4, 2] → Left Edge with rivet accent
[4, 3] → Left Edge plain variant
```

#### Right Edge (for vertical tiling)
```
[0, 8] → Right Edge variant 1
[1, 8] → Right Edge variant 1 continued
[2, 2] → Alternative right edge
[2, 8] → Right Edge variant 2
[3, 8] → Alternative right edge continued
[7, 8] → Alternative right edge
```

#### Navy Fill (for interior)
```
[2, 4] → Primary fill tile (use this as default)
[2, 5] → Fill continuation
[3, 2] → Fill continuation
[3, 3] → Fill continuation
[5, 0-5] → Large fill area (rows 5-6)
[6, 0-2] → Fill continuation
[7, 0-2] → Fill continuation
[8, 0]  → Fill at bottom
```

#### Content Boxes (Inset Squares - 32×32 each)
```
[3, 4] → Inset box variant 1
[3, 5] → Inset box variant 2
```

#### Two-Cell Panels (64×32 each - right side stack)
```
[4, 7] → Panel 2-cell (top of stack)
[5, 7] → Panel 2-cell (middle)
[6, 7] → Panel 2-cell (lower)
[7, 7] → Panel 2-cell (bottom)
```

#### Accent Panels (Colored - Blue/Cyan)
```
[6, 3] → Blue accent variant 1
[6, 4] → Blue accent variant 2
[6, 5] → Blue accent variant 3
[7, 3] → Blue accent continue 1
[7, 4] → Blue accent continue 2
[7, 5] → Blue accent continue 3
```

---

## COMMON WINDOW PATTERNS

### Pattern 1: Minimal Window (3×4 tiles = 96×128px)
```
[0,0]  [0,3]  [0,8]
[1,0]  FILL   [1,8]
[1,0]  FILL   [1,8]
[8,3]  [8,1]  [8,6]
```

### Pattern 2: Standard Window (5×5 tiles = 160×160px)
```
[0,0]  [0,3]  [0,3]  [0,3]  [2,0-1]
[1,0]  FILL   FILL   FILL   [1,8]
[1,0]  FILL   FILL   FILL   [1,8]
[1,0]  FILL   FILL   FILL   [1,8]
[8,3]  [8,1]  [8,1]  [8,5]  [8,6]
```

### Pattern 3: Large Window with Panels (7×7 tiles = 224×224px)
```
[0,0]  [0,3]  [0,3]  [0,3]  [0,3]  [2,0-1]
[1,0]  FILL   FILL   FILL   FILL   [1,8]
[1,0]  FILL   FILL   PANEL  PANEL  [1,8]
[1,0]  FILL   FILL   PANEL  PANEL  [1,8]
[1,0]  BLUE*  BLUE*  BLUE*  PANEL  [1,8]
[1,0]  BLUE*  BLUE*  BLUE*  PANEL  [1,8]
[8,3]  [8,1]  [8,1]  [8,5]  PANEL  [8,6]
```

### Pattern 4: Full-Featured Window with Accents (9×8 tiles = 288×256px)
```
[0,0]  [0,3]  [0,3]  [0,3]  [0,3]  [0,3]  [0,3]  [2,0-1]
[1,0]  FILL   FILL   FILL   FILL   FILL   FILL   [1,8]
[1,0]  FILL   FILL   FILL   FILL   FILL   PANEL  [1,8]
[1,0]  FILL   FILL   FILL   FILL   FILL   PANEL  [1,8]
[1,0]  BLUE*  BLUE*  BLUE*  FILL   FILL   PANEL  [1,8]
[1,0]  BLUE*  BLUE*  BLUE*  FILL   FILL   PANEL  [1,8]
[1,0]  FILL   FILL   FILL   FILL   FILL   PANEL  [1,8]
[8,3]  [8,1]  [8,1]  [8,1]  [8,5]  PANEL  PANEL  [8,6]
```

---

## CODE QUICK REFERENCE

### Load Master Sheet
```java
GuiMasterGridExtractor.loadMasterSpritesheet(
    "Resources/industrial-zone/gui/1 Frames/" +
    "82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png"
);
```

### Extract Single Tile
```java
BufferedImage tile = GuiMasterGridExtractor.extractTile([row], [col]);
// Example: Extract top-left corner
BufferedImage corner = GuiMasterGridExtractor.extractTile(0, 0);
```

### Extract Region
```java
BufferedImage region = GuiMasterGridExtractor.extractRegion([row], [col], [height], [width]);
// Example: Extract top-right corner (2 tiles wide)
BufferedImage cornerTR = GuiMasterGridExtractor.extractRegion(2, 0, 1, 2);
```

### Compose Window
```java
BufferedImage window = GuiMasterGridExtractor.composeWindowFrame([width], [height], [accents]);
// Example: 6×6 tiles with accent panels
BufferedImage win = GuiMasterGridExtractor.composeWindowFrame(6, 6, true);
```

### Draw to Screen
```java
Graphics g = ...; // from component
g.drawImage(window, x, y, null);
```

---

## PERFORMANCE TIPS

| Operation | First Time | Cached | Recommendation |
|-----------|-----------|---------|----------------|
| Load sheet | 50-100ms | - | Do once at startup |
| Extract tile | 0.1-0.3ms | <0.01ms | Extract all once, cache |
| Extract region | 0.2-0.5ms | <0.01ms | Extract commonly-used |
| Compose window | 5-15ms | <1ms | Compose once, reuse |

### Memory Usage
- Master sheet: ~300KB
- Single tile (32×32 ARGB): ~4KB
- Composed window (6×6): ~150KB
- Full cache (81 tiles): ~330KB

---

## TROUBLESHOOTING GRID POSITIONS

### Finding a Specific Tile
1. Use the grid diagram above (find visual position)
2. Read coordinates as `[row, col]` from labels
3. Pass to `extractTile(row, col)`
4. If position looks wrong, check you're reading correctly

### Common Mistakes
```
❌ Wrong: extractTile(4, 3)     — This skips row 4
✅ Right: extractTile(3, 4)     — [row 3, col 4]

❌ Wrong: extractTile(Column 7) — No! This is row 7, col 0
✅ Right: extractTile(0, 7)     — [row 0, col 7]
```

### Validating Positions
```java
// Check if position valid before extraction
boolean isValid = (row >= 0 && row < 9 && col >= 0 && col < 9);
if (isValid) {
    BufferedImage tile = GuiMasterGridExtractor.extractTile(row, col);
}
```

---

## GRID COORDINATE SYSTEM

```
Coordinates are [ROW][COL] (2D array style)

X-axis (columns): 0→1→2→3→4→5→6→7→8 (left to right)
Y-axis (rows):    0
                  1
                  2
                  3
                  4  ← Row 4 (middle)
                  5
                  6
                  7
                  8 (bottom)

Example:
  [0,0] = Top-left corner (row 0, col 0)
  [8,8] = Bottom-right position (row 8, col 8)
  [4,4] = Center tile (row 4, col 4)
```

---

## PRINT & SAVE FOR REFERENCE

This card can be printed and kept as a desk reference for tile positioning during development.

**Bookmark locations:**
- [Corners] - Use for window borders
- [Tile Position Lookup] - Copy coordinates
- [Code Quick Reference] - For coding
- [Common Window Patterns] - Reuses patterns
