# GUI INCREMENTATION - COMPLETE 81-FRAME POSITION REFERENCE
**Date:** April 4, 2026  
**Format:** [Frame Number] → [Grid Position] = [Grid Location]  
**Total Frames:** 81 (9×9 grid)

---

## QUICK VISUAL - LAYER BY LAYER

### Layer 1: Outer Perimeter (Frames 1-32)

```
[1] [2] [3] [4] [5] [6] [7] [8] [9]
[32]                         [10]
[31]                         [11]
[30]                         [12]
[29]                         [13]
[28]                         [14]
[27]                         [15]
[26]                         [16]
[25][24][23][22][21][20][19][18][17]
```

**Frames:** 1-9 (top), 10-17 (right), 18-25 (bottom), 26-32 (left)  
**Count:** 32 tiles total

### Layer 2: Inner Border (Frames 33-56)

```
[1] [2] [3] [4] [5] [6] [7] [8] [9]
[32][33][34][35][36][37][38][39][10]
[31][56]                    [40][11]
[30][55]                    [41][12]
[29][54]                    [42][13]
[28][53]                    [43][14]
[27][52]                    [44][15]
[26][51][50][49][48][47][46][45][16]
[25][24][23][22][21][20][19][18][17]
```

**Frames:** 33-39 (top), 40-44 (right), 45-51 (bottom), 52-56 (left)  
**Count:** 24 new tiles (57 total so far)

### Layer 3: Next Inner (Frames 57-72)

```
[1] [2] [3] [4] [5] [6] [7] [8] [9]
[32][33][34][35][36][37][38][39][10]
[31][56][57][58][59][60][61][62][40][11]
[30][55][72]               [63][41][12]
[29][54][71]               [64][42][13]
[28][53][70]               [65][43][14]
[27][52][69][68][67][66]   [44][15]
[26][51][50][49][48][47][46][45][16]
[25][24][23][22][21][20][19][18][17]
```

**Frames:** 57-61 (top), 62-65 (right), 66-69 (bottom), 70-72 (left)  
**Count:** 16 new tiles (73 total so far)

### Layer 4: Inner Most (Frames 73-80)

```
[1] [2] [3] [4] [5] [6] [7] [8] [9]
[32][33][34][35][36][37][38][39][10]
[31][56][57][58][59][60][61][62][40][11]
[30][55][72][73][74][75]   [63][41][12]
[29][54][71][80]      [76] [64][42][13]
[28][53][70][79]      [77] [65][43][14]
[27][52][69][68][67][66]   [44][15]
[26][51][50][49][48][47][46][45][16]
[25][24][23][22][21][20][19][18][17]
```

**Frames:** 73-75 (top), 76 (right), 77-79 (bottom), 80 (left)  
**Count:** 8 new tiles (81 total so far)

### Layer 5: Center (Frame 81)

```
Final frame placed at center [4,4]
[81]
```

**Frame:** 81 (center)  
**Count:** 1 final tile (81 TOTAL)

---

## COMPLETE 81-FRAME POSITION TABLE

### LAYER 1: OUTER PERIMETER (32 FRAMES)

```
FRAME  ROW  COL   FRAME  ROW  COL   FRAME  ROW  COL   FRAME  ROW  COL
──────────────────────────────────────────────────────────────────────
  1    0    0      9    0    8      17   8    8      25   8    0
  2    0    1      10   1    8      18   8    7      26   7    0
  3    0    2      11   2    8      19   8    6      27   6    0
  4    0    3      12   3    8      20   8    5      28   5    0
  5    0    4      13   4    8      21   8    4      29   4    0
  6    0    5      14   5    8      22   8    3      30   3    0
  7    0    6      15   6    8      23   8    2      31   2    0
  8    0    7      16   7    8      24   8    1      32   1    0
```

### LAYER 2: INNER BORDER (24 FRAMES)

```
FRAME  ROW  COL   FRAME  ROW  COL   FRAME  ROW  COL   FRAME  ROW  COL
──────────────────────────────────────────────────────────────────────
  33   1    1      39   1    7      45   7    7      51   7    1
  34   1    2      40   2    7      46   7    6      52   6    1
  35   1    3      41   3    7      47   7    5      53   5    1
  36   1    4      42   4    7      48   7    4      54   4    1
  37   1    5      43   5    7      49   7    3      55   3    1
  38   1    6      44   6    7      50   7    2      56   2    1
```

### LAYER 3: NEXT INNER (16 FRAMES)

```
FRAME  ROW  COL   FRAME  ROW  COL   FRAME  ROW  COL   FRAME  ROW  COL
──────────────────────────────────────────────────────────────────────
  57   2    2      61   2    6      65   6    6      69   6    2
  58   2    3      62   3    6      66   6    5      70   5    2
  59   2    4      63   4    6      67   6    4      71   4    2
  60   2    5      64   5    6      68   6    3      72   3    2
```

### LAYER 4: INNER MOST (8 FRAMES)

```
FRAME  ROW  COL   FRAME  ROW  COL   FRAME  ROW  COL   FRAME  ROW  COL
──────────────────────────────────────────────────────────────────────
  73   3    3      75   3    5      77   5    5      79   5    3
  74   3    4      76   4    5      78   5    4      80   4    3
```

### LAYER 5: CENTER (1 FRAME)

```
FRAME  ROW  COL
──────────────
  81   4    4
```

---

## FRAME POSITION ARRAY (Java Code Format)

```java
private static final int[][] FRAME_POSITIONS = {
    // Layer 1: Outer Perimeter (32 frames)
    // Top row
    {0,0}, {0,1}, {0,2}, {0,3}, {0,4}, {0,5}, {0,6}, {0,7}, {0,8},  // 1-9
    // Right column
    {1,8}, {2,8}, {3,8}, {4,8}, {5,8}, {6,8}, {7,8}, {8,8},         // 10-17
    // Bottom row (reversed)
    {8,7}, {8,6}, {8,5}, {8,4}, {8,3}, {8,2}, {8,1}, {8,0},         // 18-25
    // Left column (reversed)
    {7,0}, {6,0}, {5,0}, {4,0}, {3,0}, {2,0}, {1,0},                // 26-32
    
    // Layer 2: Inner Border (24 frames)
    // Top
    {1,1}, {1,2}, {1,3}, {1,4}, {1,5}, {1,6}, {1,7},                // 33-39
    // Right
    {2,7}, {3,7}, {4,7}, {5,7}, {6,7},                              // 40-44
    // Bottom (reversed)
    {7,7}, {7,6}, {7,5}, {7,4}, {7,3}, {7,2}, {7,1},                // 45-51
    // Left (reversed)
    {6,1}, {5,1}, {4,1}, {3,1}, {2,1},                              // 52-56
    
    // Layer 3: Next Inner (16 frames)
    // Top
    {2,2}, {2,3}, {2,4}, {2,5}, {2,6},                              // 57-61
    // Right
    {3,6}, {4,6}, {5,6},                                            // 62-64
    // Bottom (reversed)
    {6,6}, {6,5}, {6,4}, {6,3}, {6,2},                              // 65-69
    // Left (reversed)
    {5,2}, {4,2}, {3,2},                                            // 70-72
    
    // Layer 4: Inner Most (8 frames)
    // Top
    {3,3}, {3,4}, {3,5},                                            // 73-75
    // Right
    {4,5},                                                          // 76
    // Bottom (reversed)
    {5,5}, {5,4}, {5,3},                                            // 77-79
    // Left
    {4,3},                                                          // 80
    
    // Layer 5: Center (1 frame)
    {4,4}                                                           // 81
};
```

---

## SEQUENCE VALIDATION - ADJACENCY CHECK

### Manhattan Distance Formula
```
distance = |row2 - row1| + |col2 - col1|

For adjacent frames: distance must equal 1
```

### Complete Adjacency Validation Table

```
FRAME  POSITION   ← PREVIOUS    DISTANCE   STATUS
────────────────────────────────────────────────
  2    [0,1]     from [0,0]        1      ✓ VALID
  3    [0,2]     from [0,1]        1      ✓ VALID
  4    [0,3]     from [0,2]        1      ✓ VALID
  5    [0,4]     from [0,3]        1      ✓ VALID
  6    [0,5]     from [0,4]        1      ✓ VALID
  7    [0,6]     from [0,5]        1      ✓ VALID
  8    [0,7]     from [0,6]        1      ✓ VALID
  9    [0,8]     from [0,7]        1      ✓ VALID
 10    [1,8]     from [0,8]        1      ✓ VALID
 11    [2,8]     from [1,8]        1      ✓ VALID
 12    [3,8]     from [2,8]        1      ✓ VALID
 13    [4,8]     from [3,8]        1      ✓ VALID
 14    [5,8]     from [4,8]        1      ✓ VALID
 15    [6,8]     from [5,8]        1      ✓ VALID
 16    [7,8]     from [6,8]        1      ✓ VALID
 17    [8,8]     from [7,8]        1      ✓ VALID
 18    [8,7]     from [8,8]        1      ✓ VALID
 19    [8,6]     from [8,7]        1      ✓ VALID
 20    [8,5]     from [8,6]        1      ✓ VALID
 21    [8,4]     from [8,5]        1      ✓ VALID
 22    [8,3]     from [8,4]        1      ✓ VALID
 23    [8,2]     from [8,3]        1      ✓ VALID
 24    [8,1]     from [8,2]        1      ✓ VALID
 25    [8,0]     from [8,1]        1      ✓ VALID
 26    [7,0]     from [8,0]        1      ✓ VALID
 27    [6,0]     from [7,0]        1      ✓ VALID
 28    [5,0]     from [6,0]        1      ✓ VALID
 29    [4,0]     from [5,0]        1      ✓ VALID
 30    [3,0]     from [4,0]        1      ✓ VALID
 31    [2,0]     from [3,0]        1      ✓ VALID
 32    [1,0]     from [2,0]        1      ✓ VALID
 33    [1,1]     from [1,0]        1      ✓ VALID
 34    [1,2]     from [1,1]        1      ✓ VALID
 35    [1,3]     from [1,2]        1      ✓ VALID
 36    [1,4]     from [1,3]        1      ✓ VALID
 37    [1,5]     from [1,4]        1      ✓ VALID
 38    [1,6]     from [1,5]        1      ✓ VALID
 39    [1,7]     from [1,6]        1      ✓ VALID
 40    [2,7]     from [1,7]        1      ✓ VALID
 41    [3,7]     from [2,7]        1      ✓ VALID
 42    [4,7]     from [3,7]        1      ✓ VALID
 43    [5,7]     from [4,7]        1      ✓ VALID
 44    [6,7]     from [5,7]        1      ✓ VALID
 45    [7,7]     from [6,7]        1      ✓ VALID
 46    [7,6]     from [7,7]        1      ✓ VALID
 47    [7,5]     from [7,6]        1      ✓ VALID
 48    [7,4]     from [7,5]        1      ✓ VALID
 49    [7,3]     from [7,4]        1      ✓ VALID
 50    [7,2]     from [7,3]        1      ✓ VALID
 51    [7,1]     from [7,2]        1      ✓ VALID
 52    [6,1]     from [7,1]        1      ✓ VALID
 53    [5,1]     from [6,1]        1      ✓ VALID
 54    [4,1]     from [5,1]        1      ✓ VALID
 55    [3,1]     from [4,1]        1      ✓ VALID
 56    [2,1]     from [3,1]        1      ✓ VALID
 57    [2,2]     from [2,1]        1      ✓ VALID
 58    [2,3]     from [2,2]        1      ✓ VALID
 59    [2,4]     from [2,3]        1      ✓ VALID
 60    [2,5]     from [2,4]        1      ✓ VALID
 61    [2,6]     from [2,5]        1      ✓ VALID
 62    [3,6]     from [2,6]        1      ✓ VALID
 63    [4,6]     from [3,6]        1      ✓ VALID
 64    [5,6]     from [4,6]        1      ✓ VALID
 65    [6,6]     from [5,6]        1      ✓ VALID
 66    [6,5]     from [6,6]        1      ✓ VALID
 67    [6,4]     from [6,5]        1      ✓ VALID
 68    [6,3]     from [6,4]        1      ✓ VALID
 69    [6,2]     from [6,3]        1      ✓ VALID
 70    [5,2]     from [6,2]        1      ✓ VALID
 71    [4,2]     from [5,2]        1      ✓ VALID
 72    [3,2]     from [4,2]        1      ✓ VALID
 73    [3,3]     from [3,2]        1      ✓ VALID
 74    [3,4]     from [3,3]        1      ✓ VALID
 75    [3,5]     from [3,4]        1      ✓ VALID
 76    [4,5]     from [3,5]        1      ✓ VALID
 77    [5,5]     from [4,5]        1      ✓ VALID
 78    [5,4]     from [5,5]        1      ✓ VALID
 79    [5,3]     from [5,4]        1      ✓ VALID
 80    [4,3]     from [5,3]        1      ✓ VALID
 81    [4,4]     from [4,3]        1      ✓ VALID
```

**Total Frames:** 81  
**All Distances:** 1 (all valid!) ✓  
**Completion:** Frame 81 at center [4,4]

---

## QUICK LOOKUP BY FRAME RANGE

### Frames 1-9 (Top Row → Right Column Start)
```
1  @[0,0]  2  @[0,1]  3  @[0,2]  4  @[0,3]  5  @[0,4]
6  @[0,5]  7  @[0,6]  8  @[0,7]  9  @[0,8]
```

### Frames 10-20 (Right Side → Bottom Start)
```
10 @[1,8]  11 @[2,8]  12 @[3,8]  13 @[4,8]  14 @[5,8]
15 @[6,8]  16 @[7,8]  17 @[8,8]  18 @[8,7]  19 @[8,6]
20 @[8,5]
```

### Frames 21-32 (Bottom Row → Left Column)
```
21 @[8,4]  22 @[8,3]  23 @[8,2]  24 @[8,1]  25 @[8,0]
26 @[7,0]  27 @[6,0]  28 @[5,0]  29 @[4,0]  30 @[3,0]
31 @[2,0]  32 @[1,0]
```

### Frames 33-50 (Layer 2: Inner Top → Bottom)
```
33 @[1,1]  34 @[1,2]  35 @[1,3]  36 @[1,4]  37 @[1,5]
38 @[1,6]  39 @[1,7]  40 @[2,7]  41 @[3,7]  42 @[4,7]
43 @[5,7]  44 @[6,7]  45 @[7,7]  46 @[7,6]  47 @[7,5]
48 @[7,4]  49 @[7,3]  50 @[7,2]
```

### Frames 51-65 (Layer 2 End → Layer 3 Mid)
```
51 @[7,1]  52 @[6,1]  53 @[5,1]  54 @[4,1]  55 @[3,1]
56 @[2,1]  57 @[2,2]  58 @[2,3]  59 @[2,4]  60 @[2,5]
61 @[2,6]  62 @[3,6]  63 @[4,6]  64 @[5,6]  65 @[6,6]
```

### Frames 66-81 (Layer 3 End → Center)
```
66 @[6,5]  67 @[6,4]  68 @[6,3]  69 @[6,2]  70 @[5,2]
71 @[4,2]  72 @[3,2]  73 @[3,3]  74 @[3,4]  75 @[3,5]
76 @[4,5]  77 @[5,5]  78 @[5,4]  79 @[5,3]  80 @[4,3]
81 @[4,4]
```

---

## USING THIS REFERENCE

### For Code Implementation
Copy the **Frame Position Array** section into your Java code

### For Debugging
Use the **Complete Adjacency Validation Table** to verify positions

### For Understanding
View the **QUICK VISUAL - LAYER BY LAYER** section

### For Testing
Use the **Complete 81-Frame Position Table** to verify your implementation

---

**This reference is embedded in the test class as:** `FRAME_POSITIONS[][]`

Use it directly in your frame building logic!
