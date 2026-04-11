# GUI System Implementation - COMPLETE ARCHITECTURE GUIDE

## Phase 1: Framework Foundation - ✓ COMPLETE

All core GUI framework classes have been created and successfully compiled:

### 1. **GUIFrameRegistry** (22.9 KB)
- **Purpose**: Central asset catalog for all GUI resources
- **Components**:
  - `CornerType`: 16 corner piece variants (TL, TR, BL, BR with different styles)
  - `EdgeType`: 27 edge piece variants (Top, Bottom, Left, Right with different widths/textures)
  - `FillType`: 12 interior fill patterns (solid navy, diagonal texture, various rectangles)
  - `PanelType`: 14 panel/divider pieces for cell divisions
  - `BarType`: 20+ bar pieces (Health, Energy, Scroll bars at different states)
  - `IconType`: 35+ standard UI icons (arrows, controls, status indicators)
  - `SkillIconType`: 20 ability/skill icons
  - `LogoType`: 3 game logo variants
  - `NumberType`: 16 digit and symbol glyphs for score display
  - `CursorType`: 4 mouse cursor styles

- **Key Methods**:
  - `getCornerPiece(CornerType)` - Load corner image
  - `getEdgePiece(EdgeType)` - Load edge image  
  - `getFillPiece(FillType)` - Load fill pattern
  - `getBar(BarType)` - Load bar image
  - Image caching system for performance
  - Verbose error logging for missing assets

### 2. **TileFrameBuilder** (21.4 KB)
- **Purpose**: Enforces strict adjacency rules for window frame assembly
- **Key Features**:
  - `FrameTile` class tracks grid position and tile type
  - `FrameStructure` class represents complete frame with validation
  - **Adjacency Validation Rules**:
    - Corner-TL: right must be EDGE-TOP/CORNER, bottom must be EDGE-LEFT/CORNER
    - Corner-TR: left must be EDGE-TOP/CORNER, bottom must be EDGE-RIGHT/CORNER
    - Corner-BL: top must be EDGE-LEFT/CORNER, right must be EDGE-BOTTOM/CORNER
    - Corner-BR: top must be EDGE-RIGHT/CORNER, left must be EDGE-BOTTOM/CORNER
    - EDGE-TOP: connects to EDGE-TOP or CORNERs horizontally, INTERIOR below
    - EDGE-BOTTOM: connects to EDGE-BOTTOM or CORNERs horizontally, INTERIOR above
    - EDGE-LEFT: connects to EDGE-LEFT or CORNERs vertically, INTERIOR right
    - EDGE-RIGHT: connects to EDGE-RIGHT or CORNERs vertically, INTERIOR left
    - INTERIOR: can connect to any tile type

- **Key Methods**:
  - `buildStandardWindow()` - Automatically construct frame from corner→edge→fill
  - `setTile()` - Place tile with adjacency validation
  - `validate()` - Verify complete frame structure
  - Factory methods: `createSmallWindow()`, `createMediumWindow()`, `createLargeWindow()`

### 3. **UIWindow** (19.6 KB)
- **Purpose**: Renders complete UI windows with frame tiles and content
- **Components**:
  - Title bar with text rendering
  - Shadow/depth effects
  - Background fill
  - Frame tile rendering from TileFrameBuilder
  - Content area with padding management
  - Fallback colored rectangles for missing tile images
  
- **Nested Classes**:
  - `UIElement` (interface) - All GUI elements implement render()
  - `IMouseInteractive` (interface) - For interactive elements
  - `UIButton` - Clickable buttons with hover states
  - `UILabel` - Text labels

- **Key Methods**:
  - `buildFrame()` - Auto-create frame structure  
  - `addButton()`, `addLabel()` - Add content elements
  - `render(Graphics2D)` - Draw entire window to screen
  - `onMouseMoved()`, `onMouseClicked()` - Handle events

### 4. **GUIManager** (10.7 KB)
- **Purpose**: Central controller for all GUI pages and state management
- **Page Types**:
  - `MAIN_MENU` - Game start screen
  - `IN_GAME_HUD` - Active level HUD (health, energy, score)
  - `PAUSE_MENU` - Game paused overlay
  - `SETTINGS` - Settings and options
  - `GAME_OVER` - Failed level screen
  - `LEVEL_COMPLETE` - Success screen
  - `CHARACTER_SELECT` - Character selection (placeholder)

- **Base Class**: `GUIPage` (abstract)
  - All pages extend this base class
  - Lifecycle methods: `onShow()`, `onHide()`, `update()`, `render()`
  - Event handlers: `onMouseMoved()`, `onMouseClicked()`, `onKeyPressed()`
  - Window list management

- **Key Methods**:
  - `showPage(PageType)` - Switch to specific page
  - `goBackPage()` - Return to previous page
  - `update(deltaTimeMs)` - Update animations/state
  - `render(Graphics2D)` - Render current page
  - Event callback registration system

---

## Phase 2: Implementation Guide

### Step 1: Create a `GameGUIManager` Extension
Create new class `GameGUIManager extends GUIManager` in src/gui/ that implements all concrete page classes with real layouts.

### Step 2: Implement Main Menu Page
```
MAIN_MENU {
  - Title: "INDUSTRIAL ZONE"
  - Large centered window (500×400)
  - Buttons:
    * PLAY (green) → starts game
    * SETTINGS (blue) → opens settings
    * CREDITS (yellow) → shows credits
    * QUIT (red) → exits game
  - Background: industrial theme or logo
}
```

### Step 3: Implement In-Game HUD
```
IN_GAME_HUD {
  - Top-left corner:
    * Health Bar (green→yellow→red gradient)
    * Energy Bar (cyan glow effect)
  - Top-right corner:
    * Score display (numeric)
    * Level indicator
    * Time remaining
  - Bottom-left:
    * Mini inventory/ability quick select
  - Floating tooltips on hover
}
```

### Step 4: Implement Pause Menu  
```
PAUSE_MENU {
  - Semi-transparent overlay (40% opacity)
  - Centered window (600×500)
  - Buttons:
    * RESUME (highlighted first)
    * SETTINGS
    * SAVE GAME
    * LOAD GAME
    * QUIT TO MENU
  - Shows current score/time while paused
}
```

### Step 5: Implement Settings Page
```
SETTINGS {
  - Form-style layout
  - Sliders:
    * Master Volume (0-100%)
    * Music Volume (0-100%)
    * Effects Volume (0-100%)
    * Brightness (0-100%)
  - Toggles:
    * Fullscreen on/off
    * V-Sync on/off
    * Show FPS counter
  - Buttons: APPLY & SAVE, BACK
}
```

### Step 6: Implement Game Over Page
```
GAME_OVER {
  - Large centered message: "GAME OVER - YOU FAILED"
  - Stats panel:
    * Final Score: XXXXX
    * Time Alive: MM:SS
    * Enemies Defeated: XX
    * Levels Completed: X/Y
  - Buttons:
    * RETRY LEVEL
    * BACK TO MENU
}
```

### Step 7: Implement Level Complete Page
```
LEVEL_COMPLETE {
  - Large message: "LEVEL X - COMPLETE!"
  - Stars/rating display (1-3 stars)
  - Bonus breakdown:
    * Level Score: XXXXX
    * Time Bonus: XXXXX
    * Completion Bonus: XXXXX
    * Enemy Bonus: XXXXX
    * ─────────────
    * Total: XXXXX
  - Buttons:
    * NEXT LEVEL
    * RETRY THIS LEVEL
    * BACK TO MENU
}
```

---

## Integration with Game.java

### 1. In GameWindow/Game class:
```java
// At class level
private GUIManager guiManager = GUIManager.getInstance();

// In initialization
guiManager.setScreenSize(getWidth(), getHeight());
guiManager.showPage(GUIManager.PageType.MAIN_MENU);

// In paint/render loop
@Override
public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    // ... render game first
    guiManager.render(g2d);  // Then render GUI on top
}

// In mouse listeners
@Override
public void mouseMoved(MouseEvent e) {
    guiManager.onMouseMoved(e.getX(), e.getY());
}

@Override
public void mouseClicked(MouseEvent e) {
    guiManager.onMouseClicked(e.getX(), e.getY(), e.getButton());
}

// In key listeners
@Override
public void keyPressed(KeyEvent e) {
    guiManager.onKeyPressed(e.getKeyCode());
}
```

### 2. Game State Integration:
```java
// When starting game
guiManager.showPage(GUIManager.PageType.IN_GAME_HUD);

// When pausing
guiManager.showPage(GUIManager.PageType.PAUSE_MENU);
game.pause();

// When resuming
game.resume();
guiManager.showPage(GUIManager.PageType.IN_GAME_HUD);

// When level failed
guiManager.getPage(GUIManager.PageType.GAME_OVER)
    .setFinalScore(player.getScore())
    .setTimeAlive(currentTime);
guiManager.showPage(GUIManager.PageType.GAME_OVER);

// When level complete
guiManager.getPage(GUIManager.PageType.LEVEL_COMPLETE)
    .setLevelStats(levelNum, score, bonuses);
guiManager.showPage(GUIManager.PageType.LEVEL_COMPLETE);
```

---

## Asset Files Used

### Frame Tiles: 82 PNG files
- Location: `Resources/industrial-zone/gui/1 Frames/`
- 01_GUI_Frame_* through 82_GUI_Frame_*
- Total: ~5-10MB

### Bars: 20+ PNG files
- Location: `Resources/industrial-zone/gui/2 Bars/`
- Health bars (full, 80%, 60%, 40%, 20%, 5%, empty)
- Energy bars (same states)
- Scroll bars (neon variants)

### Icons: 50+ PNG files
- Location: `Resources/industrial-zone/gui/3 Icons/Buttons2/`
- Standard UI icons, buttons, skill icons
- Cursors in: `8 Cursors/`

### Numbers: 13 PNG files
- Location: `Resources/industrial-zone/gui/7 Numbers/`
- Digits 0-9, K, M, B symbols

### Logos: 3 PNG files
- Location: `Resources/industrial-zone/gui/5 Logos/`
- Compact, Full, Minimal variants

### Fonts: TTF/OTF files
- Location: `Resources/industrial-zone/gui/10 Fonts/`

---

## Tile Layout Rules Summary

**These rules are STRICTLY ENFORCED by TileFrameBuilder:**

```
┌──────────────────────────────────────┐
│ Corner-TL ─ Edge-Top ─ Corner-TR    │
│     │                      │         │
│ Edge-Left  Interior Fill Edge-Right  │
│     │      (all same)      │         │
│ Corner-BL ─ Edge-Bot ─  Corner-BR   │
└──────────────────────────────────────┘

Rules:
• Each Corner connects to 2 perpendicular Edges
• Edges cannot connect to opposite Edges (top≠bottom)
• Interior (Fill) can connect to anything
• No diagonal connections allowed
```

---

## Performance Considerations

1. **Image Caching**: GUIFrameRegistry caches loaded images
   - First load: Disk I/O
   - Subsequent loads: Memory cache hit

2. **Tile Rendering**: 
   - Only renders visible window tiles
   - Fallback colored rectangles for missing images

3. **Event Handling**:
   - Mouse events only processed if window is hovered
   - Keyboard events processed at page level

4. **Memory Management**:
   - Pages persist in memory
   - UI elements are lightweight references
   - Clear cache when switching to different theme

---

## Testing Checklist

- [ ] Compile all GUI classes without errors
- [ ] Load GUIFrameRegistry - verify asset paths correct
- [ ] Create small window with TileFrameBuilder - check adjacency
- [ ] Render window to test graphics - verify tile placement
- [ ] Create MainMenuPage with buttons
- [ ] Test button hover/click events
- [ ] Implement all 7 page types
- [ ] Integrate with Game.java main loop
- [ ] Test page transitions
- [ ] Verify HUD updates during gameplay
- [ ] Test pause/resume cycle
- [ ] Test game over and level complete screens
- [ ] Profile image loading performance
- [ ] Test on different screen resolutions

---

## Next Immediate Steps

1. ✓ Complete GUI framework (DONE)
2. → Implement concrete page classes (IN PROGRESS)
3. → Integrate GUIManager into Game.java
4. → Test all pages and transitions
5. → Polish graphics and animations
6. → Final playtesting and refinement
