# GUI FRAME INCREMENTATION TESTER - USER GUIDE
**Date:** April 4, 2026  
**Class:** `test.GuiFrameIncrementationTester`  
**Status:** ✅ Compiled and ready to use

---

## OVERVIEW

The **GUI Frame Incrementation Tester** is an interactive testing tool that allows you to:

1. **View frames incrementally** - Start with 1 tile and grow to 81
2. **Adjust frame count** - Use sliders, spinners, or direct input
3. **Validate adjacency rules** - Check that tiles connect properly
4. **Visualize the grid** - See how tiles are placed from the master grid
5. **Performance testing** - Measure frame composition speed

---

## FEATURES

### 🎮 Interactive Controls
- **Frame Count Slider** - Quickly change frame count (1-81)
- **Frame Count Spinner** - Precise numeric input
- **Auto-build** - Frame updates automatically as you change count
- **Build Button** - Manual frame composition with timing
- **Validate Button** - Check all adjacency relationships

### 🎨 Visual Display
- **Frame Preview** - Shows composed frame canvas
- **Grid Overlay** - Optional 9×9 grid visualization  
- **Real-time Updates** - Immediate visual feedback

### 📊 Information Panels
- **Frame Count Display** - Current frame number
- **Grid Dimensions** - Calculated grid size (3×3, 5×5, 7×7, 9×9)
- **Pixel Size** - Output frame size in pixels
- **Status Bar** - Current operation status
- **Log Panel** - Detailed messages and validation results

---

## HOW TO USE

### Step 1: Launch the Test Application

```bash
cd handout
java -cp "bin;lib\*" test.GuiFrameIncrementationTester
```

Or from within your IDE, run the main method in `GuiFrameIncrementationTester.java`

### Step 2: Observe Initial Load

The application will:
- Load the master spritesheet (288×288px)
- Initialize with 9 frames (3×3 grid)
- Display the composed window frame
- Show status "Ready" in blue

### Step 3: Adjust Frame Count

**Option A: Use Slider**
- Click and drag the horizontal slider from 1 to 81
- Frame automatically builds as you drag

**Option B: Type in Spinner**
- Click the spinner field
- Type number (1-81)
- Press Enter
- Frame automatically builds

**Option C: Manual Build**
- Change slider or spinner
- Click "Build Frame" button
- Timing information appears in log

### Step 4: Visualize the Grid

Check **"Show Grid"** checkbox to see:
- 9×9 grid lines overlay (semi-transparent)
- Each grid cell = 32×32 pixels
- Helps you understand tile placement

### Step 5: Validate Adjacency

Click **"Validate Adjacency"** button to:
- Check that each frame connects to previous frame
- Report any gaps or violations
- Show validation results in log

---

## UNDERSTANDING THE DISPLAY

### Frame Count & Dimensions

```
Frames: 9           ← Number of tiles currently displayed
Grid: 3×3          ← Minimum grid needed (3 cols × 3 rows)
Size: 96×96px      ← Output frame size in pixels
```

As you increase frame count:
```
Frames: 1    →  Grid: 2×2, Size: 32×32px
Frames: 4    →  Grid: 2×2, Size: 32×32px
Frames: 9    →  Grid: 3×3, Size: 96×96px
Frames: 25   →  Grid: 5×5, Size: 160×160px
Frames: 49   →  Grid: 7×7, Size: 224×224px
Frames: 81   →  Grid: 9×9, Size: 288×288px
```

### Frame Composition Speed

Log shows timing information:
```
Building frame with 9 tiles...
✓ Frame built in 45ms
  Dimensions: 96×96
```

- First build: ~10-50ms (depends on system)
- Subsequent builds: <10ms (due to tile caching)

---

## INCREMENTATION PATTERNS

### 9-Frame (3×3) Pattern

```
Frame Sequence:
[1] [2] [3]
[4] [5] [6]
[7] [8] [9]

Build Order:
1. Frame 1: Top-left corner
2. Frames 2-3: Top edges + top-right corner
3. Frames 4,6: Left & right edges  
4. Frame 5: Center fill
5. Frames 7-9: Bottom layer
```

### 25-Frame (5×5) Pattern

```
Layer 1: Outer border (16 tiles)
  [1][2][3][4][5]
  [10]·······[15]
  [19]·······[24]
  [28]·······[33]
  [37][38][39][40][41]

Layer 2: Inner border (8 tiles)
  Next ring inside

Layer 3: Center fills (1 tile)
```

### 81-Frame (9×9) Complete Pattern

```
Layer 1: Outer perimeter (32 tiles) - frames 1-32
Layer 2: Inner border (24 tiles) - frames 33-56
Layer 3: Next inner (16 tiles) - frames 57-72
Layer 4: Next inner (8 tiles) - frames 73-80
Layer 5: Center (1 tile) - frame 81
```

Each layer follows the **serpentine/snake path** pattern:
- Start at top-left
- Move right across top
- Move down right side
- Move left across bottom
- Move up left side
- Repeat inward

---

## ADJACENCY RULES

### What Adjacency Means

Two frames are **adjacent** if they are **exactly 1 tile apart** (Manhattan distance = 1):

```
ADJACENT PAIRS:
[1][2]  ← Frames 1 and 2 are adjacent (horizontal)
[1]     ← Frames 1 and 4 are adjacent (vertical)
[4]

NOT ADJACENT:
[1] · · ← Frames 1 and 5 are NOT adjacent (distance = 2)
· · [5]
```

### Validation Process

Click "Validate Adjacency" to check:

```
For each frame (2 to N):
  1. Get current frame position [row, col]
  2. Get previous frame position [row, col]
  3. Calculate distance = |row_diff| + |col_diff|
  4. If distance ≠ 1: Report violation
```

### Viewing Violations

The log will show:
```
VIOLATION: Frame 15 at [2,7] not adjacent to Frame 14 at [2,4]

(if there's a gap, fix the FRAME_POSITIONS array)
```

---

## PRACTICAL EXAMPLES

### Example 1: View Growing Window Frame

```
1. Launch application
2. Leave slider at 9 frames (3×3 grid)
3. Observe the completed window frame
4. Click "Show Grid" to see tile boundaries
5. Drag slider left to frame 1 to see just corner
6. Drag right to frame 25 to see 5×5 window
```

### Example 2: Check Adjacency Validity

```
1. Launch application
2. Keep default 9 frames
3. Click "Validate Adjacency" button
4. Check log for "all adjacencies valid" message
5. Try with 25, 49, and 81 frames
```

### Example 3: Performance Benchmark

```
1. Click "Build Frame" button with 81 frames
2. Watch log for timing: should be 10-20ms
3. Click again - should be <5ms (cached)
4. Check cache size in log
```

### Example 4: Understand the Pattern

```
1. Start with 1 frame - see topleft corner
2. Increase to 3 - see three tiles in a row
3. Increase to 9 - complete 3×3 frame
4. Jump to 33 - see outer border complete
5. Change to 81 - full 9×9 master grid view
```

---

## LOG PANEL MESSAGES

### Startup Messages
```
✓ Master spritesheet loaded
```
- Spritesheet successfully loaded from disk

### Build Messages
```
>>> Building frame with 9 tiles...
✓ Frame built in 45ms
  Dimensions: 96×96
```
- Shows frame count and composition time
- Shows resulting frame dimensions

### Validation Messages
```
>>> Validating adjacency for 25 frames...
✓ All adjacencies valid!
```
or
```
VIOLATION: Frame 15 at [2,7] not adjacent...
✗ Found 2 adjacency violations!
```

### Error Messages
```
✗ ERROR: Invalid frame count
✗ Frame build failed
✗ Validation error: Null pointer
```

---

## KEYBOARD SHORTCUTS

| Action | Keys |
|--------|------|
| Increase frames | ↑ (slider) or Ctrl+↑ (spinner) |
| Decrease frames | ↓ (slider) or Ctrl+↓ (spinner) |
| Build frame | Click "Build Frame" button |
| Validate | Click "Validate Adjacency" button |
| Clear log | Click "Clear Log" button |
| Toggle grid | Click "Show Grid" checkbox |

---

## TROUBLESHOOTING

### Issue: "Master spritesheet not loaded"
- **Cause:** File path incorrect or file missing
- **Solution:** Verify file exists at `Resources/industrial-zone/gui/1 Frames/82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png`

### Issue: Frame preview is blank
- **Cause:** Spritesheet not loaded OR slider at 0
- **Solution:** 
  1. Check log for load error
  2. Ensure slider/spinner ≥ 1
  3. Click "Build Frame" button

### Issue: Slow frame building
- **Cause:** First build extracts tiles from disk
- **Solution:** Normal - caching makes subsequent builds faster

### Issue: Adjacency violations reported
- **Cause:** FRAME_POSITIONS array has wrong sequence
- **Solution:** Verify positions match planning document

---

## CODE REFERENCE

### Main Class
```java
test.GuiFrameIncrementationTester extends JFrame
```

### Inner Builder Class
```java
GuiFrameIncrementationTester.FrameIncrementationBuilder
```

### Key Methods
```java
public BufferedImage buildFrame(int numFrames)
public int validateAllAdjacency(int numFrames)
private int[] calculateGridDimsForFrames(int frameCount)
```

### Frame Positions Array
```java
private static final int[][] FRAME_POSITIONS = {
    // 81 entries: [row, col] for each frame
    {0, 0},  // Frame 1 at [0,0]
    {0, 1},  // Frame 2 at [0,1]
    // ... etc
};
```

---

## TESTING CHECKLIST

When using the tester, verify:

- [ ] Application launches without errors
- [ ] Master spritesheet loads (log shows ✓)
- [ ] Frame displays correctly at startup (9 frames, 3×3 grid)
- [ ] Slider adjusts frame count (1-81)
- [ ] Spinner accepts numeric input
- [ ] Grid visualization appears when checkbox enabled
- [ ] "Build Frame" button shows timing
- [ ] "Validate Adjacency" reports all valid
- [ ] Log scrolls and updates correctly
- [ ] Frame growsvisually as count increases
- [ ] 81 frames shows full 9×9 grid
- [ ] No errors in console

---

## NEXT STEPS

### For Development
1. Use tester to understand incrementation pattern
2. Verify adjacency rules visually
3. Test with different window sizes
4. Add custom frame building logic

### For Integration
1. Copy the incrementation sequence to your code
2. Use `buildFrame()` logic in game rendering
3. Adapt frame building to your GUI framework
4. Cache frequently-used frame sizes

### For Optimization
1. Pre-build common window sizes
2. Cache tiles to improveperformance
3. Consider async loading for large frames
4. Profile with frame count = 81

---

## REFERENCE

See these documents for more details:

1. **[GUI_FRAME_INCREMENTATION_ADJACENCY_PLAN.md](../GUI_FRAME_INCREMENTATION_ADJACENCY_PLAN.md)**
   - Complete 81-frame sequence table
   - Adjacency validation rules
   - Implementation algorithm

2. **[GUI_MASTER_GRID_QUICK_REFERENCE.md](../GUI_MASTER_GRID_QUICK_REFERENCE.md)**
   - Grid position lookup
   - Tile coordinates
   - Common patterns

3. **[GUI_MASTER_SPRITESHEET_CORRECTED_GRID_PLAN.md](../GUI_MASTER_SPRITESHEET_CORRECTED_GRID_PLAN.md)**
   - Master grid layout
   - Adjacency groups
   - Detailed planning

---

## SUMMARY

The **GUI Frame Incrementation Tester** provides an interactive way to:
- ✓ View frame building in action
- ✓ Adjust frame size with sliders
- ✓ Validate adjacency rules
- ✓ Understand the serpentine pattern
- ✓ Test performance
- ✓ Debug frame composition

Use it to explore the 9×9 grid system and understand how frames are built incrementally!

**Ready to launch? Run:**
```
java -cp "bin;lib\*" test.GuiFrameIncrementationTester
```
