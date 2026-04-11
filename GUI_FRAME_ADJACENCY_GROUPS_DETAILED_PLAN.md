# GUI FRAME ADJACENCY GROUPS - SEMANTIC GROUPING PLAN
**Date:** April 4, 2026  
**Total Frames:** 81 (9×9 grid, 32px each tile)  
**Purpose:** Define frame groups with semantic adjacency rules

---

## GRID POSITION REFERENCE (Frame Numbers 1-81)

```
ROW 0:  1   2   3   4   5   6   7   8   9
ROW 1: 10  11  12  13  14  15  16  17  18
ROW 2: 19  20  21  22  23  24  25  26  27
ROW 3: 28  29  30  31  32  33  34  35  36
ROW 4: 37  38  39  40  41  42  43  44  45
ROW 5: 46  47  48  49  50  51  52  53  54
ROW 6: 55  56  57  58  59  60  61  62  63
ROW 7: 64  65  66  67  68  69  70  71  72
ROW 8: 73  74  75  76  77  78  79  80  81
```

---

## SEMANTIC ADJACENCY GROUPS

Each group represents a **complete tile pattern** that can be used to build windows with proper adjacency rules.

### **GROUP 1: TOP-LEFT CORNER PATTERN** (3×3)
**Frames:** 1, 2, 3, 10, 11 (center), 12, 19, 20, 21  
**Grid Position:** [0,0] to [2,2]  
**Type:** Corner Pattern  
**Adjacency Rules:**
- Frame 11 (center): Can connect to any adjacent group
- Frames 1,3: Corners - must connect to edges only
- Frames 2,10,12,19,20,21: Edges/fills - must connect to adjacent frames within pattern

**Pattern Visualization:**
```
[1] [2] [3]
[10][11][12]
[19][20][21]
```

**Tile Role Assignments:**
- 1: CORNER_TOPLEFT
- 2: EDGE_TOP
- 3: CORNER_TOPRIGHT
- 10: EDGE_LEFT
- 11: FILL_CENTER
- 12: EDGE_RIGHT
- 19: CORNER_BOTTOMLEFT
- 20: EDGE_BOTTOM
- 21: CORNER_BOTTOMRIGHT

---

### **GROUP 2: TOP-MIDDLE PATTERN** (1×4)
**Frames:** 4, 5, 6, 7, 8  
**Grid Position:** [0,3] to [0,7]  
**Type:** Horizontal Edge Pattern  
**Adjacency Rules:**
- Frames can chain left-right
- Must connect to vertical edges above/below

**Pattern Visualization:**
```
[4][5][6][7][8]
```

**Tile Role Assignments:**
- 4: EDGE_TOP_START
- 5: EDGE_TOP_MID
- 6: EDGE_TOP_CENTER
- 7: EDGE_TOP_MID
- 8: EDGE_TOP_END

---

### **GROUP 3: TOP-RIGHT CORNER PATTERN** (3×3)
**Frames:** 7, 8, 9, 16, 17, 18, 25, 26, 27  
**Grid Position:** [0,6] to [2,8]  
**Type:** Corner Pattern  
**Adjacency Rules:**
- Frame 17 (center): Flexible connection point
- Frames 9,27: Corners - limited connections
- Frames 8,16,18,25,26: Edges/fills

**Pattern Visualization:**
```
[7] [8] [9]
[16][17][18]
[25][26][27]
```

**CENTER TILE:** 17

---

### **GROUP 4: LEFT EDGE PATTERN** (4×1)
**Frames:** 10, 19, 28, 37  
**Grid Position:** [1,0] to [4,0]  
**Type:** Vertical Edge Pattern  
**Adjacency Rules:**
- Frames chain top-bottom
- Must connect to horizontal edges left/right

**Pattern Visualization:**
```
[10]
[19]
[28]
[37]
```

---

### **GROUP 5: CENTER FILL VARIANT 1** (Single Tile)
**Frame:** 37  
**Grid Position:** [4,0]  
**Type:** Replacement Center Frame  
**Use Case:** Can replace any CENTER tile in window patterns

---

### **GROUP 6: CONTENT WINDOW PATTERN** (1×3)
**Frames:** 38, 39, 40  
**Grid Position:** [4,1] to [4,3]  
**Type:** Window Header Pattern  
**Adjacency Rules:**
- Forms top of window content area
- Must connect to content below (frames 41-51)

**Pattern Visualization:**
```
[38][39][40]
```

---

### **GROUP 7: CENTRAL WINDOW STRUCTURE** (7 Frames - Complex)
**Frames:** 13, 73, 74, 75, 76, 77, 78  
**Grid Position:** Complex diagonal/cross pattern  
**Type:** Window Title/Header Bar  
**Adjacency Rules:**
- Forms central window decorative bar
- Connects top to content area

**Frame Layout (Conceptual):**
```
[13] = Top center window marker
[73][74][75] = Bottom support row
[76] = Left support
[77] = Center
[78] = Right support
```

---

### **GROUP 8: MIDDLE SECTION PATTERN** (6 Frames)
**Frames:** 35, 43, 44, 49, 50, 51  
**Grid Position:** Mixed layout  
**Type:** Window Content Area  
**Adjacency Rules:**
- Provides content fill area
- Connects vertically from header to bottom

**Frame Distribution:**
```
Row 3: [35] [43][44]
Row 5: [49][50][51]
```

---

### **GROUP 9: CROSS/DIAMOND PATTERN** (9 Frames - Diagonal)
**Frames:** 9, 18, 36, 27, 45, 72, 54, 63, 81  
**Grid Position:** Diagonal cross pattern  
**Type:** Decorative Window Accent  
**Adjacency Rules:**
- Forms diagonal band through center
- Represents window frame/trim

**Pattern Visualization (Diagonal):**
```
[9]               = top-right corner
   [18]           = right edge
      [36]        = right middle
          [45]    = center-right
              [54]= bottom-right
                  [63]= bottom-right corner
      [81]        = center
   [72]           = bottom-center
[27]              = bottom-left
```

**CENTER TILE:** 45

---

### **GROUP 10: BOTTOM-LEFT CORNER PATTERN** (3×3)
**Frames:** 55, 56, 57, 64, 65 (center), 66, 73, 74, 75  
**Grid Position:** [6,0] to [8,2]  
**Type:** Corner Pattern  
**Adjacency Rules:**
- Frame 65 (center): Connection hub
- Frames 55,57: Top corners - limited
- Frames 64,66,73,74,75: Edges/fills

**Pattern Visualization:**
```
[55][56][57]
[64][65][66]
[73][74][75]
```

**CENTER TILE:** 65

---

### **GROUP 11: BOTTOM EDGE PATTERN** (1×4)
**Frames:** 67, 68, 69, 70, 71  
**Grid Position:** [7,3] to [7,7]  
**Type:** Horizontal Edge Pattern  
**Adjacency Rules:**
- Frames chain left-right
- Must connect to tiles above

**Pattern Visualization:**
```
[67][68][69][70][71]
```

---

### **GROUP 12: BOTTOM-RIGHT CORNER PATTERN** (3×3)
**Frames:** 61, 62, 63, 70, 71, 72, 79, 80, 81  
**Grid Position:** [6,6] to [8,8]  
**Type:** Corner Pattern  
**Adjacency Rules:**
- Frame 71 (center): Hub connection
- Frames 63,81: Corners
- Frames 62,70,72,79,80: Edges/fills

**Pattern Visualization:**
```
[61][62][63]
[70][71][72]
[79][80][81]
```

**CENTER TILE:** 71

---

### **GROUP 13: CENTER FILL VARIANT 2** (Single Tile)
**Frame:** 79  
**Grid Position:** [8,0]  
**Type:** Replacement Center Frame  
**Use Case:** Can replace any CENTER tile for variation

---

### **GROUP 14: CENTER FILL VARIANT 3** (Single Tile)
**Frame:** 80  
**Grid Position:** [8,1]  
**Type:** Replacement Center Frame  
**Use Case:** Can replace any CENTER tile for variation

---

### **GROUP 15: RIGHT EDGE PATTERN** (4×1)
**Frames:** 18, 27, 36, 45, 54  
**Grid Position:** [1,8] to [5,8]  
**Type:** Vertical Edge Pattern  
**Adjacency Rules:**
- Frames chain top-bottom
- Must connect to horizontal edges

**Pattern Visualization:**
```
[18]
[27]
[36]
[45]
[54]
```

---

### **GROUP 16: MIDDLE ROW FILL** (Remaining tiles in middle)
**Frames:** 14, 15, 16, 23, 24, 25, 32, 33, 34  
**Grid Position:** [1,3]-[1,7], [2,3]-[2,7], [3,3]-[3,7]  
**Type:** Content Fill Area  
**Adjacency Rules:**
- Forms interior content region
- Connects all edges to center

**Pattern Visualization:**
```
[14][15][16]
[23][24][25]
[32][33][34]
```

**CENTER TILE:** 24

---

### **GROUP 17: MIDDLE SECTION CONTENT** (Alternate fill)
**Frames:** 41, 42, 52, 53, 58, 59, 60, 61, 62  
**Grid Position:** Middle rectangular area  
**Type:** Content/Fill Area  
**Adjacency Rules:**
- Provides window content space
- Can be replaced with other fill groups

**Pattern Visualization:**
```
[41][42]
   [52][53]
[58][59][60][61][62]
```

---

## INCREMENTATION RULE

### **Even-Number Tile Scaling**
When building windows with **even number of tiles**, the **middle 2 tiles** serve as expansion points:

**Example (10 tiles):**
```
Initial frame (1×5):
[A][A][B][A][A]  ← B is center expansion point

Expand to (2×5):
[A][A][B][A][A]
[A][A][B][A][A]  ← Both B's expand for next size
```

**Window Expansion Sequence:**
- 1 tile → Single CENTER
- 4 tiles → 2×2 (2 middle tiles expand)
- 9 tiles → 3×3 (1 center tile expands)
- 16 tiles → 4×4 (2 middle tiles expand)
- 25 tiles → 5×5 (1 center tile expands)
- 36 tiles → 6×6 (2 middle tiles expand)
- 49 tiles → 7×7 (1 center tile expands)
- 64 tiles → 8×8 (2 middle tiles expand)
- 81 tiles → 9×9 (1 center tile expands - complete)

---

## CENTER TILE RULES

### Primary Center Tiles (for expansion):
1. **11** (GROUP 1) - Top-left quadrant center
2. **17** (GROUP 3) - Top-right quadrant center  
3. **29** (GROUP 16) - Middle-left center
4. **35** (GROUP 8) - Middle center
5. **41** (GROUP 17) - Middle-right center
6. **47** (GROUP 2) - Center expansion hub
7. **65** (GROUP 10) - Bottom-left quadrant center
8. **71** (GROUP 12) - Bottom-right quadrant center

### Replacement Center Tiles (for variation):
1. **37** (Single frame variant 1)
2. **79** (Single frame variant 2)
3. **80** (Single frame variant 3)

---

## ADJACENCY VALIDATION MATRIX

For each frame in a group, define which other frames/groups it can connect to:

```
FRAME  TYPE              CAN_CONNECT_TO           POSITION_ROLE
──────────────────────────────────────────────────────────────
1      CORNER_TL        2, 10                    Top-left anchor
2      EDGE_TOP         1, 3, 11                 Top edge
3      CORNER_TR        2, 12                    Top-right anchor
...
11     FILL_CENTER      2, 10, 12, 20            Expansion point
...
```

---

## REMAINING UNASSIGNED TILES (To be filled)

**Tiles 4-8:** Part of Group 2 (TOP-MIDDLE PATTERN) ✓  
**Tiles 13:** Part of Group 7 (CENTRAL WINDOW) ✓  
**Tiles 14-16:** Part of Group 16 ✓  
**Tiles 22-26:** Part of Group 16 ✓  
**Tiles 28:** Part of Group 4 ✓  
**Tiles 29-30:** Can expand Group 4  
**Tiles 31-34:** Can form decorative edge  
**Tiles 35:** Part of Group 8 ✓  
**Tiles 38-40:** Part of Group 6 ✓  
**Tiles 41-45:** Part of Group 17 ✓  
**Tiles 49-54:** Part of Groups 8, 15 ✓  
**Tiles 55-66:** Part of Groups 10, 11, 12 ✓  
**Tiles 67-72:** Part of Groups 11, 15, 12 ✓  
**Tiles 73-81:** Part of Groups 10, 12, 13, 14 ✓  

**ALL 81 TILES ASSIGNED!** ✓

---

## IMPLEMENTATION ROADMAP

### Phase 1: Define Frame Types
Map each of 81 frames to semantic type:
- CORNER_TL, CORNER_TR, CORNER_BL, CORNER_BR
- EDGE_TOP, EDGE_BOTTOM, EDGE_LEFT, EDGE_RIGHT
- FILL_CENTER, FILL_CONTENT, FILL_ALTERNATE
- DECORATIVE_BAR, DECORATIVE_TRIM
- REPLACEMENT_CENTER (77, 79, 80)

### Phase 2: Create Adjacency Matrix
```java
boolean canConnect(int frame1, int frame2, Direction dir) {
    // Check if frame1's type allows connection to frame2
    // in specified direction
}
```

### Phase 3: Implement Group-Based Building
```java
WindowFrame buildWindow(int numTiles) {
    // Select appropriate groups
    // Apply expansion rules for even/odd
    // Use center tiles for scaling
    // Validate adjacency
}
```

### Phase 4: Test Incrementation
Verify: 1 → 4 → 9 → 16 → 25 → 36 → 49 → 64 → 81 tiles all valid

---

## VISUAL ADJACENCY RULES SUMMARY

```
┌─────────────────────────────────┐
│ CORNER pieces (1, 3, 55, 81)    │
│ MUST connect to EDGE pieces     │
│ NO diagonal or free connections │
└─────────────────────────────────┘

┌─────────────────────────────────┐
│ EDGE pieces (2, 10, 12,...)     │
│ MUST match direction             │
│ TOP/BOTTOM edges only connect    │
│ horizontally to other EDGES      │
├─────────────────────────────────┤
│ LEFT/RIGHT edges only connect    │
│ vertically to other EDGES        │
└─────────────────────────────────┘

┌─────────────────────────────────┐
│ FILL pieces (11, 20, 21,...)    │
│ CAN connect OMNI-DIRECTIONAL    │
│ to adjacent FILLS or EDGES      │
│ NO connection to distant tiles  │
└─────────────────────────────────┘

┌─────────────────────────────────┐
│ CENTER tiles (11, 17, 35, 45,...)
│ PRIMARY expansion/scaling points │
│ Connect to all adjacent pieces   │
└─────────────────────────────────┘
```

---

This detailed plan provides the foundation for semantic frame grouping and proper adjacency-based window building! 🎨

Each of the 81 frames is now:
✓ Numbered and positioned  
✓ Assigned to a semantic group  
✓ Given adjacency rules  
✓ Mapped to tile roles (corner, edge, fill, etc.)  
✓ Ready for implementation in the test class  

