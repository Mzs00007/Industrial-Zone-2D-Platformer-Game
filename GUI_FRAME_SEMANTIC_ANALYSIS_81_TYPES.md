# GUI FRAME SEMANTIC ANALYSIS - 81 UNIQUE PIECE TYPES
**Date:** April 4, 2026  
**Master Spritesheet:** 288×288px (9×9 = 81 frames, 32px each)  
**Purpose:** Define DIFFERENTIATED adjacency rules for each unique GUI piece

---

## FRAME TYPE CLASSIFICATION SYSTEM

Each of the 81 frames is a **unique GUI component** with specific visual characteristics and connection rules.

### Frame Type Categories

```
TYPE 1:  CORNER PIECES (4 types × multiple variations = ~12 frames)
         - Top-left corner
         - Top-right corner
         - Bottom-left corner
         - Bottom-right corner
         - Can connect to: Adjacent edges ONLY
         
TYPE 2:  EDGE PIECES (4 directions × multiple lengths = ~24 frames)
         - Top edge (horizontal)
         - Bottom edge (horizontal)
         - Left edge (vertical)
         - Right edge (vertical)
         - Can connect to: Corners AND other edges in same direction
         
TYPE 3:  FILL/CENTER PIECES (solid, pattern, gradient = ~30 frames)
         - Solid center fills
         - Patterned fills
         - Gradient fills
         - Can connect to: Any adjacent piece (TOP/BOTTOM/LEFT/RIGHT)
         
TYPE 4:  PANEL PIECES (structured content = ~10 frames)
         - Windows with frames
         - Panels with sections
         - Can connect to: Specific adjacent pieces based on panel layout
         
TYPE 5:  DECORATIVE/SPECIAL (1-2 frames)
         - Transition pieces
         - Special elements
         - Can connect to: Specific frames as designed
```

---

## DETAILED 81-FRAME CLASSIFICATION

### GRID POSITION TO FRAME TYPE MAPPING

**Reading the 9×9 grid from top-left [0,0] to bottom-right [8,8]:**

```
ROW 0: [0,0] [0,1] [0,2] [0,3] [0,4] [0,5] [0,6] [0,7] [0,8]
ROW 1: [1,0] [1,1] [1,2] [1,3] [1,4] [1,5] [1,6] [1,7] [1,8]
ROW 2: [2,0] [2,1] [2,2] [2,3] [2,4] [2,5] [2,6] [2,7] [2,8]
ROW 3: [3,0] [3,1] [3,2] [3,3] [3,4] [3,5] [3,6] [3,7] [3,8]
ROW 4: [4,0] [4,1] [4,2] [4,3] [4,4] [4,5] [4,6] [4,7] [4,8]
ROW 5: [5,0] [5,1] [5,2] [5,3] [5,4] [5,5] [5,6] [5,7] [5,8]
ROW 6: [6,0] [6,1] [6,2] [6,3] [6,4] [6,5] [6,6] [6,7] [6,8]
ROW 7: [7,0] [7,1] [7,2] [7,3] [7,4] [7,5] [7,6] [7,7] [7,8]
ROW 8: [8,0] [8,1] [8,2] [8,3] [8,4] [8,5] [8,6] [8,7] [8,8]
```

**FRAME-BY-FRAME CLASSIFICATION:**

```
FRAME  POS      TYPE                      CATEGORY    ADJACENT_FRAMES
───────────────────────────────────────────────────────────────────────
  1    [0,0]    TOP-LEFT CORNER          CORNER      [0,1] [1,0]
  2    [0,1]    TOP EDGE (LONG)          EDGE        [0,0] [0,2] [1,1]
  3    [0,2]    TOP EDGE (MEDIUM)        EDGE        [0,1] [0,3] [1,2]
  4    [0,3]    TOP EDGE (LONG)          EDGE        [0,2] [0,4] [1,3]
  5    [0,4]    TOP EDGE (CENTER)        EDGE        [0,3] [0,5] [1,4]
  6    [0,5]    TOP EDGE (LONG)          EDGE        [0,4] [0,6] [1,5]
  7    [0,6]    TOP EDGE (MEDIUM)        EDGE        [0,5] [0,7] [1,6]
  8    [0,7]    TOP EDGE (LONG)          EDGE        [0,6] [0,8] [1,7]
  9    [0,8]    TOP-RIGHT CORNER         CORNER      [0,7] [1,8]
  
 10    [1,0]    LEFT EDGE (LONG)         EDGE        [0,0] [2,0] [1,1]
 11    [1,1]    FILL (PATTERN)           FILL        [0,1] [1,0] [1,2] [2,1]
 12    [1,2]    FILL (SOLID)             FILL        [0,2] [1,1] [1,3] [2,2]
 13    [1,3]    PANEL (WINDOW)           PANEL       [0,3] [1,2] [1,4] [2,3]
 14    [1,4]    FILL (GRADIENT)          FILL        [0,4] [1,3] [1,5] [2,4]
 15    [1,5]    PANEL (FRAME)            PANEL       [0,5] [1,4] [1,6] [2,5]
 16    [1,6]    FILL (SOLID)             FILL        [0,6] [1,5] [1,7] [2,6]
 17    [1,7]    FILL (PATTERN)           FILL        [0,7] [1,6] [1,8] [2,7]
 18    [1,8]    RIGHT EDGE (LONG)        EDGE        [0,8] [2,8] [1,7]
 
 19    [2,0]    LEFT EDGE (MEDIUM)       EDGE        [1,0] [3,0] [2,1]
 20    [2,1]    FILL (PATTERN)           FILL        [1,1] [2,0] [2,2] [3,1]
 21    [2,2]    FILL (SOLID)             FILL        [1,2] [2,1] [2,3] [3,2]
 22    [2,3]    FILL (PATTERNED)         FILL        [1,3] [2,2] [2,4] [3,3]
 23    [2,4]    FILL (CENTER)            FILL        [1,4] [2,3] [2,5] [3,4]
 24    [2,5]    FILL (PATTERNED)         FILL        [1,5] [2,4] [2,6] [3,5]
 25    [2,6]    FILL (SOLID)             FILL        [1,6] [2,5] [2,7] [3,6]
 26    [2,7]    FILL (PATTERN)           FILL        [1,7] [2,6] [2,8] [3,7]
 27    [2,8]    RIGHT EDGE (MEDIUM)      EDGE        [1,8] [3,8] [2,7]
 
 28    [3,0]    LEFT EDGE (LONG)         EDGE        [2,0] [4,0] [3,1]
 29    [3,1]    FILL (SOLID)             FILL        [2,1] [3,0] [3,2] [4,1]
 30    [3,2]    FILL (SOLID)             FILL        [2,2] [3,1] [3,3] [4,2]
 31    [3,3]    PANEL (DIVISION)         PANEL       [2,3] [3,2] [3,4] [4,3]
 32    [3,4]    FILL (CENTER)            FILL        [2,4] [3,3] [3,5] [4,4]
 33    [3,5]    PANEL (DIVISION)         PANEL       [2,5] [3,4] [3,6] [4,5]
 34    [3,6]    FILL (SOLID)             FILL        [2,6] [3,5] [3,7] [4,6]
 35    [3,7]    FILL (SOLID)             FILL        [2,7] [3,6] [3,8] [4,7]
 36    [3,8]    RIGHT EDGE (LONG)        EDGE        [2,8] [4,8] [3,7]
 
 37    [4,0]    LEFT EDGE (MEDIUM)       EDGE        [3,0] [5,0] [4,1]
 38    [4,1]    FILL (PATTERN)           FILL        [3,1] [4,0] [4,2] [5,1]
 39    [4,2]    FILL (SOLID)             FILL        [3,2] [4,1] [4,3] [5,2]
 40    [4,3]    PANEL (WINDOW)           PANEL       [3,3] [4,2] [4,4] [5,3]
 41    [4,4]    FILL (CENTER)            FILL        [3,4] [4,3] [4,5] [5,4]
 42    [4,5]    PANEL (WINDOW)           PANEL       [3,5] [4,4] [4,6] [5,5]
 43    [4,6]    FILL (SOLID)             FILL        [3,6] [4,5] [4,7] [5,6]
 44    [4,7]    FILL (PATTERN)           FILL        [3,7] [4,6] [4,8] [5,7]
 45    [4,8]    RIGHT EDGE (MEDIUM)      EDGE        [3,8] [5,8] [4,7]
 
 46    [5,0]    LEFT EDGE (LONG)         EDGE        [4,0] [6,0] [5,1]
 47    [5,1]    FILL (SOLID)             FILL        [4,1] [5,0] [5,2] [6,1]
 48    [5,2]    FILL (SOLID)             FILL        [4,2] [5,1] [5,3] [6,2]
 49    [5,3]    PANEL (DIVISION)         PANEL       [4,3] [5,2] [5,4] [6,3]
 50    [5,4]    FILL (CENTER)            FILL        [4,4] [5,3] [5,5] [6,4]
 51    [5,5]    PANEL (DIVISION)         PANEL       [4,5] [5,4] [5,6] [6,5]
 52    [5,6]    FILL (SOLID)             FILL        [4,6] [5,5] [5,7] [6,6]
 53    [5,7]    FILL (SOLID)             FILL        [4,7] [5,6] [5,8] [6,7]
 54    [5,8]    RIGHT EDGE (LONG)        EDGE        [4,8] [6,8] [5,7]
 
 55    [6,0]    LEFT EDGE (MEDIUM)       EDGE        [5,0] [7,0] [6,1]
 56    [6,1]    FILL (PATTERN)           FILL        [5,1] [6,0] [6,2] [7,1]
 57    [6,2]    FILL (SOLID)             FILL        [5,2] [6,1] [6,3] [7,2]
 58    [6,3]    FILL (PATTERNED)         FILL        [5,3] [6,2] [6,4] [7,3]
 59    [6,4]    FILL (CENTER)            FILL        [5,4] [6,3] [6,5] [7,4]
 60    [6,5]    FILL (PATTERNED)         FILL        [5,5] [6,4] [6,6] [7,5]
 61    [6,6]    FILL (SOLID)             FILL        [5,6] [6,5] [6,7] [7,6]
 62    [6,7]    FILL (PATTERN)           FILL        [5,7] [6,6] [6,8] [7,7]
 63    [6,8]    RIGHT EDGE (MEDIUM)      EDGE        [5,8] [7,8] [6,7]
 
 64    [7,0]    BOTTOM-LEFT CORNER       CORNER      [6,0] [7,1]
 65    [7,1]    BOTTOM EDGE (LONG)       EDGE        [6,1] [7,0] [7,2] [8,1]
 66    [7,2]    BOTTOM EDGE (MEDIUM)     EDGE        [6,2] [7,1] [7,3] [8,2]
 67    [7,3]    BOTTOM EDGE (LONG)       EDGE        [6,3] [7,2] [7,4] [8,3]
 68    [7,4]    BOTTOM EDGE (CENTER)     EDGE        [6,4] [7,3] [7,5] [8,4]
 69    [7,5]    BOTTOM EDGE (LONG)       EDGE        [6,5] [7,4] [7,6] [8,5]
 70    [7,6]    BOTTOM EDGE (MEDIUM)     EDGE        [6,6] [7,5] [7,7] [8,6]
 71    [7,7]    BOTTOM EDGE (LONG)       EDGE        [6,7] [7,6] [7,8] [8,7]
 72    [7,8]    BOTTOM-RIGHT CORNER      CORNER      [6,8] [7,7]
 
 73    [8,0]    ??? (MYSTERY)            SPECIAL     [7,0] (check design)
 74    [8,1]    ??? (MYSTERY)            SPECIAL     [7,1] (check design)
 75    [8,2]    ??? (MYSTERY)            SPECIAL     [7,2] (check design)
 76    [8,3]    ??? (MYSTERY)            SPECIAL     [7,3] (check design)
 77    [8,4]    ??? (MYSTERY)            SPECIAL     [7,4] (check design)
 78    [8,5]    ??? (MYSTERY)            SPECIAL     [7,5] (check design)
 79    [8,6]    ??? (MYSTERY)            SPECIAL     [7,6] (check design)
 80    [8,7]    ??? (MYSTERY)            SPECIAL     [7,7] (check design)
 81    [8,8]    ??? (MYSTERY)            SPECIAL     [7,8] (check design)
```

---

## ADJACENCY RULES - DEFINED BY TYPE

### RULE SET 1: CORNER PIECES
```
CORNER pieces: [0,0], [0,8], [7,0], [7,8]

Connectivity Pattern:
- TOP-LEFT [0,0]     ↔ [0,1] (top edge) AND [1,0] (left edge)
- TOP-RIGHT [0,8]    ↔ [0,7] (top edge) AND [1,8] (right edge)
- BOTTOM-LEFT [7,0]  ↔ [6,0] (left edge) AND [7,1] (bottom edge)
- BOTTOM-RIGHT [7,8] ↔ [6,8] (right edge) AND [7,7] (bottom edge)

Adjacency Rule: ONLY connects to edges in its direction (NO diagonal)
```

### RULE SET 2: EDGE PIECES
```
TOP EDGES: [0,1], [0,2], [0,3], [0,4], [0,5], [0,6], [0,7]
- Connect: Left ↔ Right (horizontally)
- Connect: Top corner ↔ Left / Right corner
- Can connect DOWN to: Fill pieces or Panels directly below

LEFT EDGES: [1,0], [2,0], [3,0], [4,0], [5,0], [6,0]
- Connect: Top ↔ Bottom (vertically)
- Connect: Top corner ↔ Up / Bottom corner
- Can connect RIGHT to: Fill pieces or Panels directly right

BOTTOM EDGES: [7,1], [7,2], [7,3], [7,4], [7,5], [7,6], [7,7]
- Connect: Left ↔ Right (horizontally)
- Connect: Bottom corner ↔ Left / Right corner
- Can connect UP to: Fill pieces or Panels directly above

RIGHT EDGES: [1,8], [2,8], [3,8], [4,8], [5,8], [6,8]
- Connect: Top ↔ Bottom (vertically)
- Connect: Top corner ↔ Up / Bottom corner
- Can connect LEFT to: Fill pieces or Panels directly left

Adjacency Rule: Match edge ORIENTATION and CONNECT horizontally/vertically
```

### RULE SET 3: FILL PIECES
```
FILL pieces: All [i,j] where 1≤i≤6 AND 1≤j≤7

Connectivity Pattern:
- Can connect in ALL FOUR DIRECTIONS (up/down/left/right)
- Can connect to: Other fills, edges, or panels in adjacent cells
- Constraint: Visual continuity (similar color/pattern preferred)

Adjacency Rule: OMNI-DIRECTIONAL connection (most flexible)
```

### RULE SET 4: PANEL PIECES
```
PANEL pieces: [1,3], [1,5], [3,3], [3,5], [4,3], [4,5], [5,3], [5,5]

Connectivity Pattern:
- Can connect to: Fills primarily
- Can connect to: Edges and other panels if design allows
- Constraint: Internal structure must align with adjacent pieces

Adjacency Rule: STRUCTURED connection (requires design match)
```

### RULE SET 5: MYSTERY/SPECIAL (ROW 8)
```
FRAMES 73-81: [8,0] through [8,8]

Status: REQUIRES VISUAL INSPECTION
- Purpose: decorative? structural? spacer?
- Connectivity: Depends on visual design (check spritesheet)

Action Needed: Analyze final row visually
```

---

## INCREMENTATION SEQUENCE - BUILDING VALID WINDOWS

### Building Rule: Construct rectangular windows from TOP-LEFT outward

**Frame Count 1:** Just [0,0] (top-left corner)
```
[C]
```

**Frame Count 3:** Expand width with top edge
```
[C-E]
[L]
```

**Frame Count 5:** Add full top row with corner
```
[C-E--E-C]
[L     L]
```

**Frame Count 9:** Add row 2 (fills)
```
[C-E--E-C]
[L F  F L]
[L F  F L]
```

**Frame Count 13:** Expand to 3×3 with bottom edge
```
[C-E--E-C]
[L F  F L]
[L F  F L]
[C-E--E-C]
```

This builds VALID window structures where:
- ✓ Corners connect to edges
- ✓ Edges connect to fills
- ✓ Fills connect in all directions
- ✓ Becomes complete window at 3×3

Continue expanding: 1×1 → 1×3 → 3×3 → 3×5 → 5×5 → 5×7 → 7×7 → 7×9 → 9×9

---

## ADJACENCY VALIDATION - SEMANTIC CHECK

For each frame transition (frameN → frameN+1):
1. Check if frameN+1 can connect to frameN based on TYPE rules
2. Check if visual style matches (color/pattern continuity)
3. Check if position forms valid rectangular window
4. PASS if all checks succeed, FAIL if any check fails

---

## ACTION ITEMS

**IMMEDIATE:**
- [ ] Analyze last row (frames 73-81) to determine special types
- [ ] Refine visual style matching rules (colors, patterns)
- [ ] Create frame increment sequence that respects TYPE adjacency

**THEN:**
- Update GuiFrameIncrementationTester with semantic rules
- Update FRAME_POSITIONS to reflect valid window building
- Test that each frame transition respects adjacency rules

