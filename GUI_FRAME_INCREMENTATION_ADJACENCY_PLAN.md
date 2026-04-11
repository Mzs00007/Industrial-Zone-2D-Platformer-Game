# GUI FRAME INCREMENTATION PLAN - ADJACENCY RULE SEQUENCE
**Date:** April 4, 2026  
**Purpose:** Define tile placement sequence for building frames from minimum to maximum size  
**Pattern:** Serpentine/Snake path - borders first, then inward layers

---

## PART 1: 9-TILE FRAME (3×3 Grid) - FOUNDATIONAL PATTERN

### Visual Layout
```
[1] [2] [3]
[4] [5] [6]
[7] [8] [9]
```

### Incrementation Sequence (Layer-by-Layer)

#### **Level 1: Corners (Frames 1-4)**
Approach: Place all 4 corners first to establish frame boundary

```
[1] · · 
·   ·   ·
·   ·   [3]

[7] ·   ·
·   ·   ·
·   ·   [9]
```

**Order:**
- Frame 1: Top-Left Corner at [0,0]
- Frame 2: (Reserved for next layer - see below)
- Frame 3: Top-Right Corner at [0,2]
- Frame 4+ then continue...

#### **Level 2: Top & Bottom Edges (Frames 2, 8)**
After corners, add connecting edges on same row

```
[1] [2] [3]
·   ·   ·
[7] [8] [9]
```

**Order (continuing from Level 1):**
- Frame 2: Top Edge at [0,1] (connects 1→3)
- Skip middle tiles for now
- Frame 8: Bottom Edge at [2,1] (connects 7→9)

#### **Level 3: Left & Right Edges (Frames 4, 6)**
Fill sides connecting top to bottom

```
[1] [2] [3]
[4] ·   [6]
[7] [8] [9]
```

**Order (continuing):**
- Frame 4: Left Edge at [1,0]
- Frame 6: Right Edge at [1,2]

#### **Level 4: Center Fill (Frame 5)**
Finally, fill interior

```
[1] [2] [3]
[4] [5] [6]
[7] [8] [9]
```

**Order (final):**
- Frame 5: Center at [1,1]

### Complete Placement Sequence for 3×3
```
FRAME  POSITION  TYPE          DESCRIPTION
────────────────────────────────────────────
1      [0,0]     CORNER-TL     Top-Left corner
2      [0,1]     EDGE-TOP      Top center edge
3      [0,2]     CORNER-TR     Top-Right corner
4      [1,0]     EDGE-LEFT     Left center edge
5      [1,1]     FILL          Center interior
6      [1,2]     EDGE-RIGHT    Right center edge
7      [2,0]     CORNER-BL     Bottom-Left corner
8      [2,1]     EDGE-BOT      Bottom center edge
9      [2,2]     CORNER-BR     Bottom-Right corner
```

### Adjacency Connections for 3×3

#### Horizontal Adjacency (Left-Right)
```
1 ↔ 2 ↔ 3
4 ↔ 5 ↔ 6
7 ↔ 8 ↔ 9
```

#### Vertical Adjacency (Top-Bottom)
```
1    2    3
↓    ↓    ↓
4 ↔ 5 ↔ 6
↓    ↓    ↓
7    8    9
```

#### Diagonal Adjacency (for reference, though not typically used)
```
1 \    / 3
  5 (center)
7 /    \ 9
```

---

## PART 2: 25-TILE FRAME (5×5 Grid) - BUILDING UP

### Visual Layout
```
[1]  [2]  [3]  [4]  [5]
[6]  ·    ·    ·   [10]
[11] ·    ·    ·   [15]
[16] ·    ·    ·   [20]
[21] [22] [23] [24] [25]
```

### Incrementation Sequence

#### **Layer 1: Outer Frame Border**
Place corners, edges around the border

```
[1]  [2]  [3]  [4]  [5]
[6]  ·    ·    ·   [10]
[11] ·    ·    ·   [15]
[16] ·    ·    ·   [20]
[21] [22] [23] [24] [25]
```

**Order (12 tiles for perimeter):**
- Frames 1-5: Top row [0,0] → [0,4]
- Frame 10: Right edge continuation [1,4]
- Frame 15: Right edge continuation [2,4]
- Frame 20: Right edge continuation [3,4]
- Frames 21-25: Bottom row [4,0] → [4,4]
- Frames 6, 11, 16: Left edge [1,0], [2,0], [3,0]

#### **Layer 2: Inner Frame Border**
```
[1]  [2]  [3]  [4]  [5]
[6]  [7]  [8]  [9]  [10]
[11] [12] ·    [14] [15]
[16] [17] [18] [19] [20]
[21] [22] [23] [24] [25]
```

**Order (continuing, 8 new tiles):**
- Frames 7-9: Inner top row [1,1] → [1,3]
- Frame 14: Inner right [2,3]
- Frames 17-19: Inner bottom row [3,1] → [3,3]
- Frame 12: Inner left [2,1]

#### **Layer 3: Center Fill**
```
[1]  [2]  [3]  [4]  [5]
[6]  [7]  [8]  [9]  [10]
[11] [12] [13] [14] [15]
[16] [17] [18] [19] [20]
[21] [22] [23] [24] [25]
```

**Order (final, 1 tile):**
- Frame 13: Center [2,2]

---

## PART 3: 81-TILE FRAME (9×9 Grid) - COMPLETE SYSTEM

### Layer-by-Layer Breakdown

#### **Layer 1: Outer Perimeter (32 tiles)**
```
[1]  [2]  [3]  [4]  [5]  [6]  [7]  [8]  [9]
[10] ·    ·    ·    ·    ·    ·    ·   [18]
[19] ·    ·    ·    ·    ·    ·    ·   [27]
[28] ·    ·    ·    ·    ·    ·    ·   [36]
[37] ·    ·    ·    ·    ·    ·    ·   [45]
[46] ·    ·    ·    ·    ·    ·    ·   [54]
[55] ·    ·    ·    ·    ·    ·    ·   [63]
[64] ·    ·    ·    ·    ·    ·    ·   [72]
[73] [74] [75] [76] [77] [78] [79] [80] [81]
```

**Order (32 tiles):**
1. Top row: Frames 1-9 at positions [0, 0-8] (9 tiles)
2. Right column: Frames 10-17 at positions [1-7, 8] (7 tiles, 17 corners only)
3. Bottom row: Frames 18-25 at positions [8, 8-0] reversed (8 tiles, corner already counted)
4. Left column: Frames 26-32 at positions [7-1, 0] reversed (7 tiles, corners already counted)

Wait, let me recalculate:
- Top: [0,0] to [0,8] = 9 tiles (1-9)
- Right: [1,8] to [7,8] = 7 tiles (10-16)
- Bottom: [8,8] to [8,0] = 9 tiles (17-25)
- Left: [7,0] to [1,0] = 7 tiles (26-32)
- Total = 32 tiles ✓

#### **Layer 2: Inner Border (24 tiles)**
```
[1]  [2]  [3]  [4]  [5]  [6]  [7]  [8]  [9]
[10][11] [12] [13] [14] [15] [16] [17][18]
[19][20] ·    ·    ·    ·    ·   [25][27]
[28][29] ·    ·    ·    ·    ·   [34][36]
[37][38] ·    ·    ·    ·    ·   [43][45]
[46][47] ·    ·    ·    ·    ·   [52][54]
[55][56] ·    ·    ·    ·    ·   [61][63]
[64][65] [66] [67] [68] [69] [70] [71][72]
[73][74] [75] [76] [77] [78] [79] [80][81]
```

**Order (continuing, 24 tiles):**
- Top: [1,1-7] = 7 tiles (33-39)
- Right: [2-6, 7] = 5 tiles (40-44)
- Bottom: [7, 7-1] = 7 tiles (45-51)
- Left: [6-2, 1] = 5 tiles (52-56)

#### **Layer 3: Next Inner (16 tiles)**
```
[1]  [2]  [3]  [4]  [5]  [6]  [7]  [8]  [9]
[10][11] [12] [13] [14] [15] [16] [17][18]
[19][20][21] [22] [23] [24] [25] [26][27]
[28][29][30] ·    ·    ·   [34][35][36]
[37][38][39] ·    ·    ·   [43][44][45]
[46][47][48] ·    ·    ·   [52][53][54]
[55][56][57] [58] [59] [60] [61] [62][63]
[64][65][66] [67] [68] [69] [70] [71][72]
[73][74][75] [76] [77] [78] [79] [80][81]
```

**Order (continuing, 16 tiles):**
- Top: [2, 2-6] = 5 tiles (57-61)
- Right: [3-5, 6] = 3 tiles (62-64)
- Bottom: [6, 6-2] = 5 tiles (65-69)
- Left: [5-3, 2] = 3 tiles (70-72)

#### **Layer 4: Next Inner (8 tiles)**
```
[1]  [2]  [3]  [4]  [5]  [6]  [7]  [8]  [9]
[10][11] [12] [13] [14] [15] [16] [17][18]
[19][20][21] [22] [23] [24] [25] [26][27]
[28][29][30][31] [32] [33][34] [35][36]
[37][38][39][40] ·   [42][43] [44][45]
[46][47][48][49] [50][51][52] [53][54]
[55][56][57][58] [59] [60] [61] [62][63]
[64][65][66][67] [68] [69] [70] [71][72]
[73][74][75][76] [77] [78] [79] [80][81]
```

**Order (continuing, 8 tiles):**
- Top: [3, 3-5] = 3 tiles (73-75)
- Right: [4, 6] = 1 tile (76)
- Bottom: [5, 5-3] = 3 tiles (77-79)
- Left: [4, 3] = 1 tile (80)

#### **Layer 5: Center (1 tile)**
```
[1]  [2]  [3]  [4]  [5]  [6]  [7]  [8]  [9]
...
[37][38][39][40][41][42][43][44][45]
[46][47][48][49][50][51][52][53][54]
[55][56][57][58][59][60][61][62][63]
[64][65][66][67][68][69][70][71][72]
[73][74][75][76][77][78][79][80][81]
```

**Order (final):**
- Center: [4, 4] = 1 tile (81)

---

## COMPLETE 81-TILE INCREMENTATION SEQUENCE

```
FRAME  LAYER  POSITION  ROW  COL  TYPE            STATUS
─────────────────────────────────────────────────────────

LAYER 1: OUTER PERIMETER (32 TILES)
─────────────────────────────────────
1      1      [0,0]     0    0    CORNER_TL       Build first
2      1      [0,1]     0    1    EDGE_TOP
3      1      [0,2]     0    2    EDGE_TOP
4      1      [0,3]     0    3    EDGE_TOP
5      1      [0,4]     0    4    EDGE_TOP
6      1      [0,5]     0    5    EDGE_TOP
7      1      [0,6]     0    6    EDGE_TOP
8      1      [0,7]     0    7    EDGE_TOP
9      1      [0,8]     0    8    CORNER_TR
10     1      [1,8]     1    8    EDGE_RIGHT
11     1      [2,8]     2    8    EDGE_RIGHT
12     1      [3,8]     3    8    EDGE_RIGHT
13     1      [4,8]     4    8    EDGE_RIGHT
14     1      [5,8]     5    8    EDGE_RIGHT
15     1      [6,8]     6    8    EDGE_RIGHT
16     1      [7,8]     7    8    EDGE_RIGHT
17     1      [8,8]     8    8    CORNER_BR
18     1      [8,7]     8    7    EDGE_BOT
19     1      [8,6]     8    6    EDGE_BOT
20     1      [8,5]     8    5    EDGE_BOT
21     1      [8,4]     8    4    EDGE_BOT
22     1      [8,3]     8    3    EDGE_BOT
23     1      [8,2]     8    2    EDGE_BOT
24     1      [8,1]     8    1    EDGE_BOT
25     1      [8,0]     8    0    CORNER_BL
26     1      [7,0]     7    0    EDGE_LEFT
27     1      [6,0]     6    0    EDGE_LEFT
28     1      [5,0]     5    0    EDGE_LEFT
29     1      [4,0]     4    0    EDGE_LEFT
30     1      [3,0]     3    0    EDGE_LEFT
31     1      [2,0]     2    0    EDGE_LEFT
32     1      [1,0]     1    0    EDGE_LEFT

LAYER 2: INNER BORDER (24 TILES)
─────────────────────────────────
33     2      [1,1]     1    1    EDGE_TOP
34     2      [1,2]     1    2    EDGE_TOP
35     2      [1,3]     1    3    EDGE_TOP
36     2      [1,4]     1    4    EDGE_TOP
37     2      [1,5]     1    5    EDGE_TOP
38     2      [1,6]     1    6    EDGE_TOP
39     2      [1,7]     1    7    EDGE_TOP
40     2      [2,7]     2    7    EDGE_RIGHT
41     2      [3,7]     3    7    EDGE_RIGHT
42     2      [4,7]     4    7    EDGE_RIGHT
43     2      [5,7]     5    7    EDGE_RIGHT
44     2      [6,7]     6    7    EDGE_RIGHT
45     2      [7,7]     7    7    EDGE_BOT
46     2      [7,6]     7    6    EDGE_BOT
47     2      [7,5]     7    5    EDGE_BOT
48     2      [7,4]     7    4    EDGE_BOT
49     2      [7,3]     7    3    EDGE_BOT
50     2      [7,2]     7    2    EDGE_BOT
51     2      [7,1]     7    1    EDGE_BOT
52     2      [6,1]     6    1    EDGE_LEFT
53     2      [5,1]     5    1    EDGE_LEFT
54     2      [4,1]     4    1    EDGE_LEFT
55     2      [3,1]     3    1    EDGE_LEFT
56     2      [2,1]     2    1    EDGE_LEFT

LAYER 3: NEXT INNER (16 TILES)
──────────────────────────────
57     3      [2,2]     2    2    EDGE_TOP
58     3      [2,3]     2    3    EDGE_TOP
59     3      [2,4]     2    4    EDGE_TOP
60     3      [2,5]     2    5    EDGE_TOP
61     3      [2,6]     2    6    EDGE_TOP
62     3      [3,6]     3    6    EDGE_RIGHT
63     3      [4,6]     4    6    EDGE_RIGHT
64     3      [5,6]     5    6    EDGE_RIGHT
65     3      [6,6]     6    6    EDGE_BOT
66     3      [6,5]     6    5    EDGE_BOT
67     3      [6,4]     6    4    EDGE_BOT
68     3      [6,3]     6    3    EDGE_BOT
69     3      [6,2]     6    2    EDGE_BOT
70     3      [5,2]     5    2    EDGE_LEFT
71     3      [4,2]     4    2    EDGE_LEFT
72     3      [3,2]     3    2    EDGE_LEFT

LAYER 4: NEXT INNER (8 TILES)
─────────────────────────────
73     4      [3,3]     3    3    EDGE_TOP
74     4      [3,4]     3    4    EDGE_TOP
75     4      [3,5]     3    5    EDGE_TOP
76     4      [4,5]     4    5    EDGE_RIGHT
77     4      [5,5]     5    5    EDGE_BOT
78     4      [5,4]     5    4    EDGE_BOT
79     4      [5,3]     5    3    EDGE_BOT
80     4      [4,3]     4    3    EDGE_LEFT

LAYER 5: CENTER FILL (1 TILE)
──────────────────────────────
81     5      [4,4]     4    4    CENTER_FILL    Build last
```

---

## ADJACENCY VALIDATION TABLE

For any frame N, validate neighbors:

### Get Neighbors of Frame N
```
Frame N at position [row, col]

LEFT:   [row, col-1]
RIGHT:  [row, col+1]
UP:     [row-1, col]
DOWN:   [row+1, col]
```

### Layer-Based Adjacency
```
SAME LAYER: Frames within same layer are adjacent
  - Example: Frames 33, 34, 35 (Layer 2 top row) are all adjacent

BETWEEN LAYERS: 
  - Layer 1 corners/edges connect to Layer 2 equivalents
  - Example: Frame 1 corner connects to Frame 33 inner corner
```

---

## IMPLEMENTATION ALGORITHM

```
Algorithm: BuildFrameByIncrementation(maxFrameNum)

FOR frame = 1 TO maxFrameNum:
    GET position[frame] from INCREMENTATION_SEQUENCE_TABLE
    GET tile_type[frame] from POSITION
    EXTRACT tile from GuiMasterGridExtractor at position
    ADD tile to frame_image at correct position
    VALIDATE adjacency with previous frame
    IF adjacency invalid:
        PRINT warning about broken connection
    ENDIF
ENDFOR

RETURN composed_frame_image
```

---

## PSEUDOCODE FOR TEST IMPLEMENTATION

```java
class FrameIncrementationBuilder {
    
    // Tile position sequence (as generated above)
    private static final int[][] FRAME_POSITIONS = {
        // Frame 1:  [0,0]
        // Frame 2:  [0,1]
        // ... etc for all 81 frames
    };
    
    // Tile type for each frame
    private static final TileType[] FRAME_TYPES = {
        CORNER_TL,  // Frame 1
        EDGE_TOP,   // Frame 2
        // ... etc
    };
    
    public BufferedImage buildFrame(int numFramesRequested) {
        // Start with minimum (1 frame = 32x32)
        BufferedImage frame = createEmptyFrame(numFramesRequested);
        
        for (int frameNum = 1; frameNum <= numFramesRequested; frameNum++) {
            int[] pos = FRAME_POSITIONS[frameNum - 1];
            BufferedImage tile = GuiMasterGridExtractor.extractTile(pos[0], pos[1]);
            
            // Draw tile at appropriate position in output frame
            drawTileInFrame(frame, tile, frameNum);
            
            // Validate adjacency
            validateAdjacency(frameNum);
        }
        
        return frame;
    }
    
    private void drawTileInFrame(BufferedImage frame, BufferedImage tile, int frameNum) {
        // Calculate output position based on current frame size
        int[] outputPos = calculateOutputPosition(frameNum);
        Graphics2D g = frame.createGraphics();
        g.drawImage(tile, outputPos[0], outputPixelY, null);
        g.dispose();
    }
    
    private void validateAdjacency(int frameNum) {
        if (frameNum == 1) return;  // First frame has no neighbors
        
        int[] currentPos = FRAME_POSITIONS[frameNum - 1];
        int[] prevPos = FRAME_POSITIONS[frameNum - 2];
        
        // Check if adjacent (Manhattan distance = 1)
        int distance = Math.abs(currentPos[0] - prevPos[0]) + 
                      Math.abs(currentPos[1] - prevPos[1]);
        
        if (distance != 1) {
            System.out.println("WARNING: Frame " + frameNum + 
                             " not adjacent to Frame " + (frameNum-1));
        }
    }
}
```

---

## NOTES FOR TESTING

### Minimum Frame Size
- Start with Frame 1 only: 32×32px (single corner)

### Size Growth Pattern
```
1 frame:    32×32px   (Frame 1 only)
2 frames:   64×32px   (Frame 1-2, top left)
3 frames:   96×32px   (Frame 1-3, full top)
9 frames:   96×96px   (3×3 complete)
16 frames:  160×160px (5×5 complete)
25 frames:  160×160px (5×5 complete)
...
81 frames:  288×288px (9×9 complete)
```

### Validation Checklist
- [ ] Each frame placed at correct [row, col] from master grid
- [ ] Each frame placed adjacent to previous frame (Manhattan distance = 1)
- [ ] Frame grows from corner outward, edge by edge
- [ ] All adjacency connections valid
- [ ] No gaps or overlaps in composition

