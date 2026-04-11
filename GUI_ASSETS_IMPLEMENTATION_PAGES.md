# GUI ASSETS IMPLEMENTATION - PAGE BY PAGE GUIDE

**Date**: April 4, 2026  
**Status**: DETAILED ASSET PLANNING FOR EACH SCREEN  
**Based on**: AnimationAndSpriteLoader.java GUI Asset Constants

---

## PAGE 1: INTRO/SPLASH SCREEN

### Screen Overview
- **Purpose**: Game introduction and studio logo display
- **Duration**: 3-5 seconds auto-transition to main menu
- **Resolution**: Full game viewport (1024x768 recommended)
- **Background**: Solid dark background with gradient overlay

### Asset Paths (from AnimationAndSpriteLoader.java)

```java
// Define these in SplashScreen.java
private static final String GUI_BASE = "Resources/industrial-zone/gui/";
private static final String GUI_LOGO = GUI_BASE + "5 Logo/";
private static final String GUI_FRAMES = GUI_BASE + "1 Frames/";
private static final String GUI_OTHER = GUI_BASE + "9 Other/";
private static final String GUI_OTHER_DECOR = GUI_BASE + "9 Other/1 Decor/";
```

### Asset Components

#### 1. **Background Layer**
```
File: background_dark_gradient.png (or similar)
Location: Resources/industrial-zone/gui/9 Other/1 Decor/
Purpose: Base layer - dark industrial gradient background
Dimensions: Full viewport (1024x768)
Color: Dark purple-grey with slight gradient
Alpha: 100% opacity
```

#### 2. **Studio Logo**
```
File: logo_studio.png (or similar)
Location: Resources/industrial-zone/gui/5 Logo/
Purpose: Developer/studio logo with fade-in animation
Dimensions: ~400x200 pixels
Position: Center of screen, Y=150 from top
Animation: Fade in (0.5s) → Hold (2.5s) → Fade out (1s)
```

#### 3. **Game Title Logo**
```
File: logo_game_title.png
Location: Resources/industrial-zone/gui/5 Logo/
Purpose: Main game title with glow effect
Dimensions: ~500x150 pixels
Position: Center of screen, Y=380 from top
Animation: Fade in 0.5s after studio logo fades
Alpha: Bright (100%)
Effect: Optional glow/shadow
```

#### 4. **Press Any Key Hint** (Optional alternative)
```
File: text_press_any_key.png (or render via font)
Location: Resources/industrial-zone/gui/10 Font/images/
Purpose: "Press any key to continue" indicator
Position: Bottom center, Y=650 from top
Animation: Blinking (1s ON, 0.5s OFF)
Duration: Visible only if no input after 3 seconds
```

#### 5. **Decorative Elements**
```
Files: deco_corner_*.png (top-left, top-right, bottom-left, bottom-right)
Location: Resources/industrial-zone/gui/9 Other/1 Decor/
Purpose: Corner decorations for industrial aesthetic
Position: All four corners
Dimensions: 64x64 or 128x128 per corner
Alpha: 50-70% opacity
```

### Loading Sequence

| Time (s) | Action | Component | Alpha |
|----------|--------|-----------|-------|
| 0.0-0.5 | Fade In | Studio Logo | 0% → 100% |
| 0.5-3.0 | Display | Studio Logo | 100% |
| 3.0-3.5 | Fade Out | Studio Logo | 100% → 0% |
| 3.0-3.5 | Fade In | Title Logo | 0% → 100% |
| 3.5-4.5 | Display | Title Logo | 100% |
| 4.5-5.0 | Fade Out | Title Logo + Background | 100% → 0% |
| 5.0+ | Transition | → Main Menu | - |

### Code Template

```java
public class SplashScreen extends AnimationAndSpriteLoader {
    private BufferedImage backgroundImage;
    private BufferedImage studioLogo;
    private BufferedImage titleLogo;
    private BufferedImage[] cornerDecos;
    
    private long startTime;
    private static final int SPLASH_DURATION = 5000; // 5 seconds
    
    public SplashScreen() {
        super();
        loadAssets();
        startTime = System.currentTimeMillis();
    }
    
    private void loadAssets() {
        String decoPath = AnimationAndSpriteLoader.GUI_OTHER_DECOR;
        String logoPath = AnimationAndSpriteLoader.GUI_LOGO;
        
        // Load backgrounds and logos
        backgroundImage = loadImage(decoPath + "background_dark_gradient.png");
        studioLogo = loadImage(logoPath + "logo_studio.png");
        titleLogo = loadImage(logoPath + "logo_game_title.png");
        
        // Load corner decorations
        cornerDecos = new BufferedImage[4];
        cornerDecos[0] = loadImage(decoPath + "deco_corner_topleft.png");
        cornerDecos[1] = loadImage(decoPath + "deco_corner_topright.png");
        cornerDecos[2] = loadImage(decoPath + "deco_corner_bottomleft.png");
        cornerDecos[3] = loadImage(decoPath + "deco_corner_bottomright.png");
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage canvas = new BufferedImage(width, height, 
                                                  BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = canvas.createGraphics();
        
        long elapsed = System.currentTimeMillis() - startTime;
        float progress = (float) elapsed / SPLASH_DURATION;
        
        // Draw background
        g.drawImage(backgroundImage, 0, 0, width, height, null);
        
        // Draw corner decorations
        drawCornerDecorations(g);
        
        // Studio logo fade (0.5s fade-in, 2.5s display, 1s fade-out)
        if (elapsed < 500) {
            float alpha = elapsed / 500f;
            drawImageWithAlpha(g, studioLogo, width/2 - 200, 150, alpha);
        } else if (elapsed < 3000) {
            drawImageWithAlpha(g, studioLogo, width/2 - 200, 150, 1.0f);
        } else if (elapsed < 3500) {
            float alpha = 1.0f - ((elapsed - 3000) / 500f);
            drawImageWithAlpha(g, studioLogo, width/2 - 200, 150, alpha);
        }
        
        // Title logo fade (starts at 3s)
        if (elapsed >= 3000 && elapsed < 3500) {
            float alpha = (elapsed - 3000) / 500f;
            drawImageWithAlpha(g, titleLogo, width/2 - 250, 350, alpha);
        } else if (elapsed >= 3500 && elapsed < 4500) {
            drawImageWithAlpha(g, titleLogo, width/2 - 250, 350, 1.0f);
        } else if (elapsed >= 4500) {
            float alpha = 1.0f - ((elapsed - 4500) / 500f);
            drawImageWithAlpha(g, titleLogo, width/2 - 250, 350, alpha);
            drawImageWithAlpha(g, backgroundImage, 0, 0, alpha);
        }
        
        g.dispose();
        return canvas;
    }
    
    private void drawCornerDecorations(Graphics2D g) {
        // Top-left
        g.setComposite(AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, 0.6f));
        g.drawImage(cornerDecos[0], 0, 0, null);
        g.drawImage(cornerDecos[1], width - 64, 0, null);
        g.drawImage(cornerDecos[2], 0, height - 64, null);
        g.drawImage(cornerDecos[3], width - 64, height - 64, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    public boolean isComplete() {
        return System.currentTimeMillis() - startTime >= SPLASH_DURATION;
    }
}
```

### Asset File Checklist

- [ ] `Resources/industrial-zone/gui/5 Logo/logo_studio.png` (400x200)
- [ ] `Resources/industrial-zone/gui/5 Logo/logo_game_title.png` (500x150)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/background_dark_gradient.png` (1024x768)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/deco_corner_topleft.png` (64x64)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/deco_corner_topright.png` (64x64)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/deco_corner_bottomleft.png` (64x64)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/deco_corner_bottomright.png` (64x64)

---

## PAGE 2: MAIN MENU SCREEN

### Screen Overview
- **Purpose**: Primary navigation hub for game
- **Background**: Animated tileset pattern (retro-style)
- **Features**: Interactive buttons with hover effects, decorative frame
- **Resolution**: Full game viewport (1024x768)
- **Layout**: Centered menu panel with 5 navigation options

### Asset Paths (from AnimationAndSpriteLoader.java)

```java
// Define these in MainMenuScreen.java
private static final String GUI_FRAMES = "Resources/industrial-zone/gui/1 Frames/";
private static final String GUI_BUTTONS = "Resources/industrial-zone/gui/6 Buttons/";
private static final String GUI_ICONS = "Resources/industrial-zone/gui/3 Icons/";
private static final String GUI_PALETTE = "Resources/industrial-zone/gui/4 Palette/";
private static final String GUI_OTHER_DECOR = "Resources/industrial-zone/gui/9 Other/1 Decor/";
private static final String L1_TILES_BASE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/";
private static final String L1_BG_BASE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/";
```

### Layer Structure

#### LAYER 1: Background (Tiled Pattern)
```
Component: Repeating tileset pattern
Source: Level 1 tile assets (small 64x64 or 32x32 tiles)
Method: Create seamless tiled pattern using Level1TileRegistry
Recommended Tiles: 'A' (platform), 'P' (pattern), 'C' (fill)
Dimensions: Full viewport (1024x768)
Alpha: 60-70% (allow decorative overlay)
Scrolling: Optional subtle horizontal scrolling animation

Code approach:
  for (int y = 0; y < height; y += 64) {
      for (int x = 0; x < width; x += 64) {
          String tilePath = Level1TileRegistry.getTile('A');  // 'A', 'P', or 'C'
          BufferedImage tile = ImageIO.read(new File(tilePath));
          g.drawImage(tile, x, y, null);
      }
  }
```

#### LAYER 2: Decorative Overlay (Semi-transparent)
```
File: menu_background_overlay.png
Location: Resources/industrial-zone/gui/9 Other/1 Decor/
Purpose: Semi-transparent dark overlay to ensure text readability
Dimensions: 1024x768
Color: Dark with 50-60% opacity
Effect: Subtle vignette/gradient toward edges
Alpha: 50-60%
```

#### LAYER 3: Menu Window Frame
```
File: menu_frame_large.png
Location: Resources/industrial-zone/gui/1 Frames/
Purpose: Decorative border/window frame for menu content
Dimensions: 600x500 (centered on screen)
Position: X=212, Y=134 (centered)
Style: Industrial/sci-fi style border with 3D beveled effect
Alpha: 100%
Border Width: 20-30 pixels

Alternative: 9-slice frame for dynamic sizing
```

#### LAYER 4: Menu Title
```
File: text_main_menu.png (or render via font system)
Location: Resources/industrial-zone/gui/10 Font/images/
Purpose: "MAIN MENU" title text
Position: Inside frame, top center, Y=170
Dimensions: ~400x60
Font: Bold, large (36-48pt)
Color: Bright cyan or gold
Alpha: 100%
Effect: Optional glow/shadow
```

#### LAYER 5: Navigation Buttons

**Button Template Structure:**
```
Each button has 3 states: NORMAL, HOVER, PRESSED
Location: Resources/industrial-zone/gui/6 Buttons/
Dimensions: ~160x50 per button
Spacing: 20 pixels between buttons
Column: Centered at X=412 (for 160px width)
```

**BUTTON 1: PLAY/START**
```
Normal:  btn_play_normal.png
Hover:   btn_play_hover.png
Pressed: btn_play_pressed.png
Position: Y=240
Action: Start new game or go to character select
```

**BUTTON 2: CHARACTER SELECT**
```
Normal:  btn_character_normal.png
Hover:   btn_character_hover.png
Pressed: btn_character_pressed.png
Position: Y=310
Action: Open character selection screen
```

**BUTTON 3: HOW TO PLAY (Tutorials)**
```
Normal:  btn_tutorial_normal.png
Hover:   btn_tutorial_hover.png
Pressed: btn_tutorial_pressed.png
Position: Y=380
Action: Open tutorial/help screen
```

**BUTTON 4: SETTINGS/OPTIONS**
```
Normal:  btn_settings_normal.png
Hover:   btn_settings_hover.png
Pressed: btn_settings_pressed.png
Position: Y=450
Action: Open settings menu
```

**BUTTON 5: EXIT**
```
Normal:  btn_exit_normal.png
Hover:   btn_exit_hover.png
Pressed: btn_exit_pressed.png
Position: Y=520
Action: Exit to desktop
```

### Asset Rendering Order

| Layer | Component | Alpha | Notes |
|-------|-----------|-------|-------|
| 1 | Tiled Background (Level 1) | 70% | Animated/static pattern |
| 2 | Overlay Darkening | 50% | Ensures readability |
| 3 | Menu Frame Border | 100% | Decorative border |
| 4 | Title Text | 100% | "MAIN MENU" |
| 5.1 | Button 1 (Play) | 100% | Dynamic state |
| 5.2 | Button 2 (Character) | 100% | Dynamic state |
| 5.3 | Button 3 (Tutorial) | 100% | Dynamic state |
| 5.4 | Button 4 (Settings) | 100% | Dynamic state |
| 5.5 | Button 5 (Exit) | 100% | Dynamic state |
| 6 | Button Hover Effect | Varies | Optional glow/highlight |

### Interactive Elements

#### Button State Machine:
```
NORMAL ──hover──> HOVER ──click──> PRESSED ──release──> NORMAL
  │                  │
  └──click here──────┘
```

#### Hover Effect Options:
1. **Alpha Transition**: Increase opacity from 80% → 100%
2. **Scale Effect**: Slightly enlarge button (1.0 → 1.1)
3. **Glow Effect**: Add glowing border/shadow
4. **Color Tint**: Add bright/bright overlay color
5. **Animation**: Play hover animation sequence

### Code Template

```java
public class MainMenuScreen extends AnimationAndSpriteLoader {
    
    // Background tiling
    private BufferedImage[][] backgroundTiles;
    private BufferedImage backgroundOverlay;
    private int bgScrollX = 0;
    
    // Menu frame and title
    private BufferedImage menuFrame;
    private BufferedImage titleText;
    
    // Buttons
    private MenuButton[] buttons;
    private static final int NUM_BUTTONS = 5;
    
    // Button positions
    private static final int[] BUTTON_Y_POSITIONS = {240, 310, 380, 450, 520};
    private static final String[] BUTTON_NAMES = {
        "play", "character", "tutorial", "settings", "exit"
    };
    
    public MainMenuScreen() {
        super();
        loadAssets();
        initializeButtons();
    }
    
    private void loadAssets() {
        String framePath = AnimationAndSpriteLoader.GUI_FRAMES;
        String buttonPath = AnimationAndSpriteLoader.GUI_BUTTONS;
        String decoPath = AnimationAndSpriteLoader.GUI_OTHER_DECOR;
        
        // Load background tiling
        loadBackgroundTiles();
        backgroundOverlay = loadImage(decoPath + "menu_background_overlay.png");
        
        // Load menu frame and title
        menuFrame = loadImage(framePath + "menu_frame_large.png");
        titleText = loadImage(decoPath + "text_main_menu.png");
    }
    
    private void loadBackgroundTiles() {
        int tileSize = 64;
        int cols = (1024 + tileSize - 1) / tileSize;
        int rows = (768 + tileSize - 1) / tileSize;
        
        backgroundTiles = new BufferedImage[rows][cols];
        char[] tileChars = {'A', 'P', 'C'}; // Variety of tiles
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Randomize tile selection for visual interest
                char tileCode = tileChars[(row + col) % 3];
                String tilePath = Level1TileRegistry.getTile(tileCode);
                backgroundTiles[row][col] = loadImage(tilePath);
            }
        }
    }
    
    private void initializeButtons() {
        buttons = new MenuButton[NUM_BUTTONS];
        int centerX = 412; // Center for 160px width button
        
        for (int i = 0; i < NUM_BUTTONS; i++) {
            String btnName = BUTTON_NAMES[i];
            int btnY = BUTTON_Y_POSITIONS[i];
            
            // Load button states
            BufferedImage normal = loadImage(
                GUI_BUTTONS + "btn_" + btnName + "_normal.png"
            );
            BufferedImage hover = loadImage(
                GUI_BUTTONS + "btn_" + btnName + "_hover.png"
            );
            BufferedImage pressed = loadImage(
                GUI_BUTTONS + "btn_" + btnName + "_pressed.png"
            );
            
            buttons[i] = new MenuButton(centerX, btnY, normal, hover, pressed);
        }
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage canvas = new BufferedImage(width, height, 
                                                  BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = canvas.createGraphics();
        
        // Layer 1: Tiled background
        drawBackgroundTiles(g);
        
        // Layer 2: Overlay darkening
        g.drawImage(backgroundOverlay, 0, 0, null);
        
        // Layer 3: Menu frame
        g.drawImage(menuFrame, 212, 134, null);
        
        // Layer 4: Title text
        g.drawImage(titleText, 312, 170, null);
        
        // Layer 5: Buttons
        for (MenuButton button : buttons) {
            button.render(g);
        }
        
        g.dispose();
        return canvas;
    }
    
    private void drawBackgroundTiles(Graphics2D g) {
        int tileSize = 64;
        for (int row = 0; row < backgroundTiles.length; row++) {
            for (int col = 0; col < backgroundTiles[0].length; col++) {
                int x = col * tileSize + bgScrollX;
                int y = row * tileSize;
                
                // Set transparency
                AlphaComposite ac = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.7f);
                g.setComposite(ac);
                
                g.drawImage(backgroundTiles[row][col], x, y, null);
                
                g.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 1.0f));
            }
        }
        
        // Optional: Animate background scroll
        bgScrollX = (bgScrollX + 1) % 64; // Scroll by 1 pixel per frame
    }
    
    public void updateMouseHover(int mouseX, int mouseY) {
        for (MenuButton button : buttons) {
            button.updateHover(mouseX, mouseY);
        }
    }
    
    public void handleButtonClick(int mouseX, int mouseY, 
                                  MainMenuListener listener) {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].isClicked(mouseX, mouseY)) {
                handleButtonAction(i, listener);
            }
        }
    }
    
    private void handleButtonAction(int buttonIndex, MainMenuListener listener) {
        switch (buttonIndex) {
            case 0: listener.onPlayClicked(); break;
            case 1: listener.onCharacterSelectClicked(); break;
            case 2: listener.onTutorialClicked(); break;
            case 3: listener.onSettingsClicked(); break;
            case 4: listener.onExitClicked(); break;
        }
    }
}

// Inner class for button representation
class MenuButton {
    private int x, y;
    private BufferedImage normalImage, hoverImage, pressedImage;
    private boolean isHovered = false;
    
    public MenuButton(int x, int y, BufferedImage normal, 
                     BufferedImage hover, BufferedImage pressed) {
        this.x = x;
        this.y = y;
        this.normalImage = normal;
        this.hoverImage = hover;
        this.pressedImage = pressed;
    }
    
    public void updateHover(int mouseX, int mouseY) {
        isHovered = mouseX >= x && mouseX <= x + 160 &&
                   mouseY >= y && mouseY <= y + 50;
    }
    
    public boolean isClicked(int mouseX, int mouseY) {
        return isHovered;
    }
    
    public void render(Graphics2D g) {
        BufferedImage image = isHovered ? hoverImage : normalImage;
        if (image != null) {
            g.drawImage(image, x, y, null);
        }
    }
}

@FunctionalInterface
interface MainMenuListener {
    void onPlayClicked();
    void onCharacterSelectClicked();
    void onTutorialClicked();
    void onSettingsClicked();
    void onExitClicked();
}
```

### Asset File Checklist

**Background & Overlay:**
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/menu_background_overlay.png` (1024x768)
- [ ] `Resources/industrial-zone/gui/1 Frames/menu_frame_large.png` (600x500)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/text_main_menu.png` (400x60)

**Button States (5 buttons × 3 states = 15 files):**
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_play_normal.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_play_hover.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_play_pressed.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_character_normal.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_character_hover.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_character_pressed.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_tutorial_normal.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_tutorial_hover.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_tutorial_pressed.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_settings_normal.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_settings_hover.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_settings_pressed.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_exit_normal.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_exit_hover.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_exit_pressed.png` (160x50)

**Note**: Level 1 tiles (A, P, C) are already in Resources/industrial-zone/1 Tiles/

---

## PAGE 3: CHARACTER SELECT SCREEN

### Screen Overview
- **Purpose**: Select playable character before level selection
- **Features**: Character carousel, stats display, description panel
- **Characters**: 3 selectable (Biker, Punk, Cyborg)
- **Layout**: Left carousel, center display, right stats panel
- **Interactive**: Previous/Next buttons, Confirm/Cancel buttons

### Asset Paths (from AnimationAndSpriteLoader.java)

```java
// Define these in CharacterSelectScreen.java
private static final String GUI_FRAMES = "Resources/industrial-zone/gui/1 Frames/";
private static final String GUI_BUTTONS = "Resources/industrial-zone/gui/6 Buttons/";
private static final String GUI_ICONS = "Resources/industrial-zone/gui/3 Icons/";
private static final String GUI_ICONS_ICONS = "Resources/industrial-zone/gui/3 Icons/Icons/";
private static final String GUI_BARS = "Resources/industrial-zone/gui/2 Bars/";
private static final String GUI_OTHER_DECOR = "Resources/industrial-zone/gui/9 Other/1 Decor/";
private static final String WEAPON_1_CHAR = "Resources/industrial-zone/weapons/1/1 Characters/1 Biker/";
private static final String WEAPON_1_CHAR_PUNK = "Resources/industrial-zone/weapons/1/1 Characters/2 Punk/";
private static final String WEAPON_1_CHAR_CYBER = "Resources/industrial-zone/weapons/1/1 Characters/3 Cyborg/";
```

### Layer Structure

#### LAYER 1: Background
```
Component: Tiled background (same as main menu or alternative)
Source: Level 1 or Level 2 tile assets
Alpha: 70%
Scrolling: Optional animated scrolling
```

#### LAYER 2: Overlay
```
File: character_select_overlay.png
Location: Resources/industrial-zone/gui/9 Other/1 Decor/
Purpose: Semi-transparent darker overlay for focus
Dimensions: 1024x768
Alpha: 40-50%
```

#### LAYER 3: Main Frame
```
File: character_frame_main.png
Location: Resources/industrial-zone/gui/1 Frames/
Purpose: Large decorative frame for entire screen
Dimensions: 900x600 (centered)
Position: X=62, Y=84
Alpha: 100%
```

#### LAYER 4: Title
```
File: text_character_select.png
Location: Resources/industrial-zone/gui/9 Other/1 Decor/
Purpose: "SELECT CHARACTER" title
Position: Top center Y=100
Dimensions: 500x50
Alpha: 100%
```

### Content Sections (3 main areas)

#### SECTION A: LEFT CAROUSEL (Character Navigation)

**Frame:**
```
File: character_carousel_frame.png
Location: Resources/industrial-zone/gui/1 Frames/
Position: X=100, Y=200
Dimensions: 220x300
Border: 15px
Alpha: 100%
```

**Character Portraits:**
```
3 Character options:
1. Biker
   File: char_portrait_biker.png
   Source: Resources/industrial-zone/weapons/1/1 Characters/1 Biker/
   Dimensions: 180x250
   Position: Inside frame, centered
   
2. Punk
   File: char_portrait_punk.png
   Source: Resources/industrial-zone/weapons/1/1 Characters/2 Punk/
   Dimensions: 180x250
   Position: Inside frame, centered
   
3. Cyborg
   File: char_portrait_cyber.png
   Source: Resources/industrial-zone/weapons/1/1 Characters/3 Cyborg/
   Dimensions: 180x250
   Position: Inside frame, centered
```

**Navigation Buttons:**
```
Left Arrow Button:
  Normal:  btn_arrow_left_normal.png
  Hover:   btn_arrow_left_hover.png
  Position: X=110, Y=520
  Purpose: Previous character
  
Right Arrow Button:
  Normal:  btn_arrow_right_normal.png
  Hover:   btn_arrow_right_hover.png
  Position: X=280, Y=520
  Purpose: Next character
```

**Character Name Label:**
```
File: Rendered text (or text_char_name_*.png per character)
Position: Center bottom of frame Y=545
Dimensions: 200x40
Content: "BIKER", "PUNK", or "CYBORG"
Font: Bold, 24pt
Alpha: 100%
```

#### SECTION B: CENTER DISPLAY (Character Visual)

**Character Preview Frame:**
```
File: character_preview_frame.png
Location: Resources/industrial-zone/gui/1 Frames/
Position: X=350, Y=200
Dimensions: 300x300
Alpha: 100%
Purpose: Larger character display
```

**Character Performance Bar (Below preview):**
```
Component: Progress bar showing character stats
Position: X=350, Y=520
Dimensions: 300x20
Bars to display (vertically stacked):
  - Speed/Agility bar (40 pixels height)
  - Strength/Power bar (40 pixels height)
  - Defense/Health bar (40 pixels height)
  
Bar Assets (from GUI_BARS):
  File: bar_stat_*.png
  Colors: 
    - Speed: Blue/Cyan gradient
    - Strength: Red/Orange gradient
    - Defense: Green/Yellow gradient
```

#### SECTION C: RIGHT STATS PANEL (Character Information)

**Stats Frame:**
```
File: character_stats_frame.png
Location: Resources/industrial-zone/gui/1 Frames/
Position: X=680, Y=200
Dimensions: 250x300
Alpha: 100%
```

**Stats Display Components:**
```
Stats to show (rendered text or image texture):
1. Health Points (HP)
   Position: Y=220
   Display: "HP: 100"
   
2. Attack Power
   Position: Y=260
   Display: "ATK: 75"
   
3. Defense
   Position: Y=300
   Display: "DEF: 60"
   
4. Speed
   Position: Y=340
   Display: "SPD: 80"
   
5. Special Ability
   Position: Y=380
   Display: "[UNIQUE ABILITY NAME]"
   
Font: Monospace, 14pt
Color: Bright cyan/white
Alpha: 100%
```

**Stat Icons (Optional):**
```
Set of 4 icons (16x16 or 24x24 each):
- icon_health.png (HP icon)
- icon_attack.png (ATK icon)
- icon_defense.png (DEF icon)
- icon_speed.png (SPD icon)

Position: Left of each stat value
Location: Resources/industrial-zone/gui/3 Icons/Icons/
Alpha: 80-100%
```

**Character Description:**
```
File: Character description text (rendered or texture)
Position: Y=450 inside stats frame
Dimensions: 230x100
Content: "Short description of character strengths and playstyle"
Font: Small (10-12pt)
Color: Light grey/white
Alpha: 80-90%
Wrap: Text wrapping enabled
```

### Bottom Control Panel

**Navigation Buttons:**
```
Back/Cancel Button:
  Normal:  btn_cancel_normal.png
  Hover:   btn_cancel_hover.png
  Pressed: btn_cancel_pressed.png
  Position: X=150, Y=650
  Dimensions: 140x50
  
Select/Confirm Button:
  Normal:  btn_select_normal.png
  Hover:   btn_select_hover.png
  Pressed: btn_select_pressed.png
  Position: X=734, Y=650
  Dimensions: 140x50
```

### Asset Rendering Order

| Layer | Component | Alpha | Position |
|-------|-----------|-------|----------|
| 1 | Tiled Background | 70% | Full viewport |
| 2 | Overlay | 50% | Full viewport |
| 3 | Main Frame | 100% | X=62, Y=84 |
| 4 | Title Text | 100% | Top center |
| 5 | Carousel Frame | 100% | Left (X=100) |
| 6 | Character Portrait | 100% | Inside carousel |
| 7 | Arrow Buttons | 100% | Below carousel |
| 8 | Character Name | 100% | Bottom carousel |
| 9 | Preview Frame | 100% | Center (X=350) |
| 10 | Character Preview | 100% | Inside preview |
| 11 | Stat Bars | 100% | Below preview |
| 12 | Stats Frame | 100% | Right (X=680) |
| 13 | Stats Icons | 80% | Inside stats |
| 14 | Stats Text | 100% | Inside stats |
| 15 | Description | 90% | Inside stats bottom |
| 16 | Control Buttons | 100% | Bottom (Y=650) |

### Code Template

```java
public class CharacterSelectScreen extends AnimationAndSpriteLoader {
    
    // Background and overlays
    private BufferedImage[][] backgroundTiles;
    private BufferedImage overlay;
    
    // Main frame and title
    private BufferedImage mainFrame;
    private BufferedImage titleText;
    
    // Character data
    private Character[] characters;
    private int selectedCharacterIndex = 0;
    
    // UI components
    private MenuButton leftArrowBtn, rightArrowBtn;
    private MenuButton cancelBtn, confirmBtn;
    private BufferedImage carouselFrame, previewFrame, statsFrame;
    
    // Character stats display
    private static class Character {
        String name;
        BufferedImage portrait;
        int hp, attack, defense, speed;
        String specialAbility;
        String description;
    }
    
    public CharacterSelectScreen() {
        super();
        loadAssets();
        initializeCharacters();
        initializeButtons();
    }
    
    private void loadAssets() {
        String framePath = AnimationAndSpriteLoader.GUI_FRAMES;
        String btnPath = AnimationAndSpriteLoader.GUI_BUTTONS;
        String decoPath = AnimationAndSpriteLoader.GUI_OTHER_DECOR;
        
        // Load backgrounds
        loadBackgroundTiles();
        overlay = loadImage(decoPath + "character_select_overlay.png");
        
        // Load frames
        mainFrame = loadImage(framePath + "character_frame_main.png");
        carouselFrame = loadImage(framePath + "character_carousel_frame.png");
        previewFrame = loadImage(framePath + "character_preview_frame.png");
        statsFrame = loadImage(framePath + "character_stats_frame.png");
        
        // Load title
        titleText = loadImage(decoPath + "text_character_select.png");
    }
    
    private void initializeCharacters() {
        String bikerPath = "Resources/industrial-zone/weapons/1/1 Characters/1 Biker/";
        String punkPath = "Resources/industrial-zone/weapons/1/1 Characters/2 Punk/";
        String cyberPath = "Resources/industrial-zone/weapons/1/1 Characters/3 Cyborg/";
        
        characters = new Character[3];
        
        // Biker
        characters[0] = new Character();
        characters[0].name = "BIKER";
        characters[0].portrait = loadImage(bikerPath + "char_portrait_biker.png");
        characters[0].hp = 100;
        characters[0].attack = 75;
        characters[0].defense = 60;
        characters[0].speed = 80;
        characters[0].specialAbility = "NITRO BOOST";
        characters[0].description = "Fast and agile fighter. Great for hit-and-run tactics.";
        
        // Punk
        characters[1] = new Character();
        characters[1].name = "PUNK";
        characters[1].portrait = loadImage(punkPath + "char_portrait_punk.png");
        characters[1].hp = 90;
        characters[1].attack = 85;
        characters[1].defense = 50;
        characters[1].speed = 75;
        characters[1].specialAbility = "SONIC SLAM";
        characters[1].description = "High damage output but lower defense. Attack-focused.";
        
        // Cyborg
        characters[2] = new Character();
        characters[2].name = "CYBORG";
        characters[2].portrait = loadImage(cyberPath + "char_portrait_cyber.png");
        characters[2].hp = 110;
        characters[2].attack = 70;
        characters[2].defense = 80;
        characters[2].speed = 60;
        characters[2].specialAbility = "SHIELD MATRIX";
        characters[2].description = "Durable tank character. Perfect for defensive playstyle.";
    }
    
    private void initializeButtons() {
        String btnPath = AnimationAndSpriteLoader.GUI_BUTTONS;
        
        // Arrow buttons for carousel
        BufferedImage leftNormal = loadImage(btnPath + "btn_arrow_left_normal.png");
        BufferedImage leftHover = loadImage(btnPath + "btn_arrow_left_hover.png");
        BufferedImage leftPressed = loadImage(btnPath + "btn_arrow_left_pressed.png");
        leftArrowBtn = new MenuButton(110, 520, leftNormal, leftHover, leftPressed);
        
        BufferedImage rightNormal = loadImage(btnPath + "btn_arrow_right_normal.png");
        BufferedImage rightHover = loadImage(btnPath + "btn_arrow_right_hover.png");
        BufferedImage rightPressed = loadImage(btnPath + "btn_arrow_right_pressed.png");
        rightArrowBtn = new MenuButton(280, 520, rightNormal, rightHover, rightPressed);
        
        // Control buttons
        BufferedImage cancelNormal = loadImage(btnPath + "btn_cancel_normal.png");
        BufferedImage cancelHover = loadImage(btnPath + "btn_cancel_hover.png");
        BufferedImage cancelPressed = loadImage(btnPath + "btn_cancel_pressed.png");
        cancelBtn = new MenuButton(150, 650, cancelNormal, cancelHover, cancelPressed);
        
        BufferedImage selectNormal = loadImage(btnPath + "btn_select_normal.png");
        BufferedImage selectHover = loadImage(btnPath + "btn_select_hover.png");
        BufferedImage selectPressed = loadImage(btnPath + "btn_select_pressed.png");
        confirmBtn = new MenuButton(734, 650, selectNormal, selectHover, selectPressed);
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage canvas = new BufferedImage(width, height, 
                                                  BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = canvas.createGraphics();
        
        // Draw background layers
        drawBackgroundTiles(g);
        g.drawImage(overlay, 0, 0, null);
        g.drawImage(mainFrame, 62, 84, null);
        g.drawImage(titleText, 262, 100, null);
        
        // Draw left section (carousel)
        drawCarouselSection(g);
        
        // Draw center section (preview)
        drawPreviewSection(g);
        
        // Draw right section (stats)
        drawStatsSection(g);
        
        // Draw control buttons
        cancelBtn.render(g);
        confirmBtn.render(g);
        
        g.dispose();
        return canvas;
    }
    
    private void drawCarouselSection(Graphics2D g) {
        g.drawImage(carouselFrame, 100, 200, null);
        
        Character current = characters[selectedCharacterIndex];
        g.drawImage(current.portrait, 130, 230, null);
        
        // Draw character name
        drawText(g, current.name, 200, 545, 24, true);
        
        // Draw arrow buttons
        leftArrowBtn.render(g);
        rightArrowBtn.render(g);
    }
    
    private void drawPreviewSection(Graphics2D g) {
        g.drawImage(previewFrame, 350, 200, null);
        
        Character current = characters[selectedCharacterIndex];
        g.drawImage(current.portrait, 380, 230, 240, 240, null);
        
        // Draw stat bars
        drawStatBars(g, 350, 530);
    }
    
    private void drawStatsSection(Graphics2D g) {
        g.drawImage(statsFrame, 680, 200, null);
        
        Character current = characters[selectedCharacterIndex];
        int startX = 700;
        int startY = 220;
        
        // Draw stats
        drawText(g, "HP: " + current.hp, startX, startY, 14, false);
        drawText(g, "ATK: " + current.attack, startX, startY + 40, 14, false);
        drawText(g, "DEF: " + current.defense, startX, startY + 80, 14, false);
        drawText(g, "SPD: " + current.speed, startX, startY + 120, 14, false);
        drawText(g, current.specialAbility, startX, startY + 160, 14, false);
        
        // Draw description
        drawWrappedText(g, current.description, startX, 420, 220, 80);
    }
    
    private void drawStatBars(Graphics2D g, int x, int y) {
        Character current = characters[selectedCharacterIndex];
        
        // Speed bar (blue)
        drawProgressBar(g, x, y, 300, 30, current.speed / 100f, Color.CYAN);
        
        // Attack bar (red)
        drawProgressBar(g, x, y + 40, 300, 30, current.attack / 100f, Color.RED);
        
        // Defense bar (green)
        drawProgressBar(g, x, y + 80, 300, 30, current.defense / 100f, Color.GREEN);
    }
    
    private void drawProgressBar(Graphics2D g, int x, int y, int width, 
                                 int height, float progress, Color color) {
        // Background
        g.setColor(new Color(50, 50, 50, 200));
        g.fillRect(x, y, width, height);
        
        // Progress fill
        g.setColor(color);
        g.fillRect(x, y, (int)(width * progress), height);
        
        // Border
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(2));
        g.drawRect(x, y, width, height);
    }
    
    public void nextCharacter() {
        selectedCharacterIndex = (selectedCharacterIndex + 1) % characters.length;
    }
    
    public void previousCharacter() {
        selectedCharacterIndex = (selectedCharacterIndex - 1 + characters.length) 
                                 % characters.length;
    }
    
    public Character getSelectedCharacter() {
        return characters[selectedCharacterIndex];
    }
}
```

### Asset File Checklist

**Main Components:**
- [ ] `Resources/industrial-zone/gui/1 Frames/character_frame_main.png` (900x600)
- [ ] `Resources/industrial-zone/gui/1 Frames/character_carousel_frame.png` (220x300)
- [ ] `Resources/industrial-zone/gui/1 Frames/character_preview_frame.png` (300x300)
- [ ] `Resources/industrial-zone/gui/1 Frames/character_stats_frame.png` (250x300)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/character_select_overlay.png` (1024x768)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/text_character_select.png` (500x50)

**Character Portraits:**
- [ ] Character portraits from weapon asset paths (use existing)

**Navigation Buttons (6 buttons × 3 states = 18 files):**
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_arrow_left_normal.png` (60x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_arrow_left_hover.png` (60x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_arrow_left_pressed.png` (60x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_arrow_right_normal.png` (60x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_arrow_right_hover.png` (60x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_arrow_right_pressed.png` (60x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_cancel_normal.png` (140x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_cancel_hover.png` (140x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_cancel_pressed.png` (140x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_select_normal.png` (140x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_select_hover.png` (140x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_select_pressed.png` (140x50)

**Stat Icons (Optional):**
- [ ] `Resources/industrial-zone/gui/3 Icons/Icons/icon_health.png` (24x24)
- [ ] `Resources/industrial-zone/gui/3 Icons/Icons/icon_attack.png` (24x24)
- [ ] `Resources/industrial-zone/gui/3 Icons/Icons/icon_defense.png` (24x24)
- [ ] `Resources/industrial-zone/gui/3 Icons/Icons/icon_speed.png` (24x24)

- [ ] `Resources/industrial-zone/gui/3 Icons/Icons/icon_speed.png` (24x24)

---

## PAGE 4: LEVEL SELECT SCREEN

### Screen Overview
- **Purpose**: Choose level to play (Level 1 & Level 2)
- **Features**: Scrollable level list, difficulty display, level preview
- **Locked/Unlocked**: Visual distinction for completed/available levels
- **Layout**: Grid or column display of selectable levels

### Asset Components

#### LAYER 1: Background
```
Same tiled pattern as main menu (70% alpha)
```

#### LAYER 2: Main Frame & Title
```
Frame: level_select_frame.png (900x600)
Title: text_level_select.png
```

#### LEVEL CARDS (Tiled in grid 2 columns × 2 rows)

**Level Card Template:**
```
Card Frame: level_card_frame.png (350x250)
Locked Overlay: level_locked_overlay.png (semitransparent)
Completed Badge: badge_completed.png (gold star/checkmark)
```

**LEVEL 1: Industrial Zone**
```
Preview Image: level1_preview.png (from L1_BG_BASE)
Position: Grid[0,0]
Difficulty: Easy/Normal
Selectable: Yes (always available)
```

**LEVEL 2: Power Station**
```
Preview Image: level2_preview.png (from L2_BG_BASE)
Position: Grid[0,1]
Difficulty: Hard
Selectable: If Level 1 completed (optional)
Locked Overlay: Shows lock icon until Level 1 beaten
```

**Cards Display Order:**
```
Level 1 Card        [Level 2 Card]
[Level 3 Slot]      [Level 4 Slot]
```

#### Card Elements
```
- Background (preview image) 70% opacity
- Title text (level name) 100%
- Difficulty badge (easy/normal/hard)
- High score display (if completed)
- Play button (for unlocked levels)
- Lock icon (for locked levels)
```

### Code Structure
```java
public class LevelSelectScreen extends AnimationAndSpriteLoader {
    private Level[] levels;
    private int selectedLevelIndex = 0;
    
    static class Level {
        String name;
        BufferedImage previewImage;
        String difficulty;
        boolean unlocked;
        int highScore;
    }
    
    public void selectLevel(int index);
    public Level getSelectedLevel();
}
```

---

## PAGE 5: IN-GAME HUD (Heads-Up Display)

### Screen Overview
- **Purpose**: Real-time game state display during gameplay
- **Position**: Overlaid on game world (corners/edges)
- **Elements**: Health, ammo, score, level indicator, mini-map
- **Always Visible**: Appears on top of game render

### Asset Components

#### TOP-LEFT: HEALTH DISPLAY
```
Frame: hud_health_frame.png (150x100)
Position: X=10, Y=10
Components:
  - Health bar background (120x20)
  - Health bar fill (dynamic width based on HP)
  - Health text: "HP: 100/100"
  - Heart icon (optional)

Bar Assets (from GUI_BARS):
  Empty: bar_health_empty.png
  Full: bar_health_filled.png
  Color: Red → Yellow gradient
```

#### TOP-CENTER: LEVEL INDICATOR
```
Box: hud_level_frame.png (200x60)
Position: X=412, Y=10
Content:
  - Level name: "LEVEL 1"
  - Stage/Area: "AREA 1-1"
  - Objective progress (% complete)

Text Display:
  Font: Monospace 12pt
  Color: Cyan/White
  Background: Semi-transparent black
```

#### TOP-RIGHT: SCORE/TIMER
```
Frame: hud_score_frame.png (150x100)
Position: X=864, Y=10
Components:
  - Current Score: 1250
  - Time Elapsed: 02:35
  - Combo Counter (if applicable)

Font: Large bold numbers (14pt)
Color: Gold/Yellow
```

#### BOTTOM-LEFT: AMMO/WEAPONS
```
Frame: hud_ammo_frame.png (180x120)
Position: X=10, Y=658
Components:
  - Current weapon display
  - Ammo counter: "AMMO: 45/120"
  - Weapon slots (show next 2 weapons available)
  
Icons:
  - Current weapon icon (48x48)
  - Next weapon icons (32x32)
  - Ammo count text

From GUI_ICONS_BUTTONS:
  weapon_icon_gun1.png
  weapon_icon_gun2.png
```

#### BOTTOM-CENTER: MINI-MAP
```
Frame: hud_minimap_frame.png (200x120)
Position: X=412, Y=658
Content:
  - Small map of current level
  - Player position (white dot)
  - Enemy positions (red dots)
  - Collectibles (yellow dots)
  - Exit/goal (blue dot)

Rendered dynamically from level data
- Size: 180x100 (internal map)
- Border: 10px frame
- Update: Every frame
```

#### BOTTOM-RIGHT: POWER-UPS/STATUS
```
Frame: hud_status_frame.png (180x120)
Position: X=834, Y=658
Display:
  - Active power-ups (row of icons)
  - Status effects (poison, shield, etc.)
  - Remaining duration (timer)

Icons (24x24 each from GUI_ICONS):
  - Shield icon
  - Speed boost icon
  - Damage boost icon
  - Invincibility icon
```

### HUD Rendering Code Template
```java
public class HUDOverlay extends AnimationAndSpriteLoader {
    
    private BufferedImage healthFrame, levelFrame, scoreFrame;
    private BufferedImage ammoFrame, minimapFrame, statusFrame;
    
    private GameState gameState;  // Reference to game state
    
    public void render(Graphics2D g, GameState state) {
        this.gameState = state;
        
        // Draw all HUD elements
        drawHealthDisplay(g);
        drawLevelIndicator(g);
        drawScoreDisplay(g);
        drawAmmoDisplay(g);
        drawMiniMap(g);
        drawStatusDisplay(g);
    }
    
    private void drawHealthDisplay(Graphics2D g) {
        g.drawImage(healthFrame, 10, 10, null);
        
        // Draw health bar
        int health = gameState.getPlayerHealth();
        int maxHealth = 100;
        int barWidth = (int)(120 * (health / (float)maxHealth));
        
        // Bar background (red)
        g.setColor(Color.RED);
        g.fillRect(20, 35, 120, 20);
        
        // Bar fill (yellow gradient)
        g.setColor(Color.YELLOW);
        g.fillRect(20, 35, barWidth, 20);
        
        // Health text
        g.setColor(Color.WHITE);
        drawCenteredText(g, "HP: " + health + "/" + maxHealth, 80, 60);
    }
    
    private void drawLevelIndicator(Graphics2D g) {
        g.drawImage(levelFrame, 412, 10, null);
        
        String levelName = gameState.getLevelName();
        String stageName = gameState.getStageName();
        int progress = gameState.getLevelProgress(); // 0-100
        
        g.setColor(new Color(0, 255, 255)); // Cyan
        drawText(g, levelName, 420, 30, 14, true);
        drawText(g, stageName, 420, 50, 12, true);
        drawText(g, progress + "%", 500, 60, 12, true);
    }
    
    // ... similar methods for other HUD elements
}
```

---

## PAGE 6: PAUSE MENU (In-Game Overlay)

### Screen Overview
- **Purpose**: Suspend gameplay, allow menu interaction
- **Position**: Full-screen semi-transparent overlay
- **Input Focus**: Mouse/keyboard focus shifts to menu
- **Return**: Resume game on Escape or Resume button

### Asset Components

#### FULL-SCREEN OVERLAY
```
File: pause_menu_background.png
Location: Resources/industrial-zone/gui/9 Other/1 Decor/
Purpose: Semi-transparent dark background (darkens game render)
Dimensions: 1024x768
Color: Black with 70% opacity (30% game visible)
Alpha: 70%
```

#### PAUSE MENU PANEL
```
Frame: pause_menu_frame.png (600x500)
Position: X=212, Y=134 (centered)
Border: Industrial style decorative border
Alpha: 100%
```

#### TITLE
```
File: text_pause_menu.png
Content: "PAUSED"
Position: X=312, Y=160
Dimensions: 400x60
Font: Bold 48pt
Color: Bright game accent color
```

#### MENU BUTTONS (4 buttons)

**Button 1: RESUME**
```
Normal:  btn_resume_normal.png
Hover:   btn_resume_hover.png
Pressed: btn_resume_pressed.png
Position: Y=240
Action: Close pause, resume game
```

**Button 2: SETTINGS**
```
Normal:  btn_settings_pause_normal.png
Hover:   btn_settings_pause_hover.png
Pressed: btn_settings_pause_pressed.png
Position: Y=310
Action: Open settings while paused
```

**Button 3: LEVEL SELECT**
```
Normal:  btn_level_select_normal.png
Hover:   btn_level_select_hover.png
Pressed: btn_level_select_pressed.png
Position: Y=380
Action: Return to level select
```

**Button 4: MAIN MENU**
```
Normal:  btn_main_menu_normal.png
Hover:   btn_main_menu_hover.png
Pressed: btn_main_menu_pressed.png
Position: Y=450
Action: Return to main menu
```

#### GAME STATE SNAPSHOT (Optional)
```
Small preview of paused game world
Position: Corner overlay (X=700, Y=450)
Dimensions: 300x250
Alpha: 50-60%
Content: Mini screenshot of game being paused
Visual Effect: Slightly blurred or desaturated
```

### Code Template
```java
public class PauseMenuScreen extends AnimationAndSpriteLoader {
    
    private BufferedImage pauseBackground;
    private BufferedImage pauseFrame;
    private BufferedImage pauseTitleText;
    
    private MenuButton[] buttons;
    private BufferedImage gameSnapshot; // Optional: game render snapshot
    
    private GameState suspendedGameState;
    private boolean isActive = false;
    
    public void showPauseMenu(GameState gameState, BufferedImage gameRender) {
        suspendedGameState = gameState;
        gameSnapshot = gameRender;
        isActive = true;
    }
    
    public void hidePauseMenu() {
        isActive = false;
    }
    
    public BufferedImage render(int width, int height) {
        if (!isActive) return null;
        
        BufferedImage canvas = new BufferedImage(width, height, 
                                                  BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = canvas.createGraphics();
        
        // Draw semi-transparent overlay
        g.drawImage(pauseBackground, 0, 0, null);
        
        // Optional: Draw game snapshot
        if (gameSnapshot != null) {
            AlphaComposite ac = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.6f);
            g.setComposite(ac);
            g.drawImage(gameSnapshot, 700, 450, 300, 250, null);
            g.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1.0f));
        }
        
        // Draw pause menu frame
        g.drawImage(pauseFrame, 212, 134, null);
        g.drawImage(pauseTitleText, 312, 160, null);
        
        // Draw buttons
        for (MenuButton button : buttons) {
            button.render(g);
        }
        
        g.dispose();
        return canvas;
    }
}
```

### Asset File Checklist - PAGES 4-6

**Level Select:**
- [ ] `Resources/industrial-zone/gui/1 Frames/level_frame_main.png` (900x600)
- [ ] `Resources/industrial-zone/gui/1 Frames/level_card_frame.png` (350x250)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/level_locked_overlay.png` (350x250)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/badge_completed.png` (60x60)
- [ ] Level preview images

**In-Game HUD:**
- [ ] `Resources/industrial-zone/gui/1 Frames/hud_health_frame.png` (150x100)
- [ ] `Resources/industrial-zone/gui/1 Frames/hud_level_frame.png` (200x60)
- [ ] `Resources/industrial-zone/gui/1 Frames/hud_score_frame.png` (150x100)
- [ ] `Resources/industrial-zone/gui/1 Frames/hud_ammo_frame.png` (180x120)
- [ ] `Resources/industrial-zone/gui/1 Frames/hud_minimap_frame.png` (200x120)
- [ ] `Resources/industrial-zone/gui/1 Frames/hud_status_frame.png` (180x120)
- [ ] `Resources/industrial-zone/gui/2 Bars/bar_health_empty.png`
- [ ] `Resources/industrial-zone/gui/2 Bars/bar_health_filled.png`

**Pause Menu:**
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/pause_menu_background.png` (1024x768)
- [ ] `Resources/industrial-zone/gui/1 Frames/pause_menu_frame.png` (600x500)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/text_pause_menu.png` (400x60)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_resume_normal.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_resume_hover.png` (160x50)
- [ ] `Resources/industrial-zone/gui/6 Buttons/btn_resume_pressed.png` (160x50)
- [ ] Plus versions for: settings_pause, level_select, main_menu buttons

---

---

**Status**: PAGES 1-6 Complete  
**Pages Completed**: 6 / 10 (60%)  
**Comprehensive Coverage**: Splash, Main Menu, Character Select, Level Select, HUD, Pause Menu  
**Remaining Pages**: Settings, Game Over, Level Complete, Tutorial/Help

---

## PAGE 7: SETTINGS/OPTIONS SCREEN

### Screen Overview
- **Purpose**: Adjust game settings (audio, graphics, controls)
- **Layout**: Scrollable settings panel with categories
- **Categories**: Audio, Video, Controls, Gameplay
- **Persistence**: Save settings to config file

### Asset Components

#### MAIN FRAME
```
Frame: settings_frame_main.png (700x550)
Position: Centered X=162, Y=109
```

#### SECTIONS (Tabbed or Scrollable)

**Tab 1: AUDIO**
```
Elements:
  - Master Volume slider
  - Music Volume slider
  - SFX Volume slider
  - Ambient Volume slider
  
Slider Assets:
  - slider_background.png (300x20)
  - slider_handle.png (30x30)
  - Positions: Y=180, 240, 300, 360
```

**Tab 2: VIDEO**
```
Elements:
  - Brightness slider
  - Gamma slider
  - Screen shake toggle
  - Screen flash toggle
  
Toggle Assets:
  - toggle_on.png (60x30)
  - toggle_off.png (60x30)
```

**Tab 3: CONTROLS**
```
Display:
  - Key bindings (read-only or remappable)
  - Movement keys
  - Attack/Action keys
  - Menu navigate keys
  
Format: "ACTION: [KEY]" (e.g., "JUMP: [SPACEBAR]")
```

**Tab 4: GAMEPLAY**
```
Toggle Options:
  - Show FPS counter
  - Show hitboxes (debug)
  - Screenshake On/Off
  - Difficulty level (Easy/Normal/Hard)
```

#### CONTROL BUTTONS
```
Save Button:
  Normal:  btn_save_normal.png
  Position: X=250, Y=500
  
Cancel Button:
  Normal:  btn_cancel_normal.png
  Position: X=500, Y=500
  
Reset Defaults Button:
  Normal:  btn_reset_normal.png
  Position: X=375, Y=550
```

---

## PAGE 8: GAME OVER SCREEN

### Screen Overview
- **Purpose**: Display end-of-game results (loss)
- **Information**: Final score, death cause, stats
- **Duration**: 5-10 seconds before auto-selecting next action
- **Options**: Retry, Level Select, Main Menu

### Asset Components

#### FULL-SCREEN DARKENING
```
File: gameover_overlay.png (1024x768)
Color: Dark red semi-transparent
Alpha: 80%
Purpose: Darken entire screen
```

#### GAME OVER BANNER
```
File: text_game_over.png
Content: "GAME OVER"
Position: Center, Y=200
Dimensions: 600x100
Font: Very large (60pt+)
Color: Bright red
Effect: Optional glowing text effect or animation
```

#### STATS PANEL
```
Frame: gameover_stats_frame.png (500x250)
Position: Center, Y=350
Elements:
  - Final Score: 2500
  - Survived Time: 02:45
  - Enemies Defeated: 15
  - Damage Taken: 45%
  - Final Stats Ranking
  
Format: Left-aligned labels with right-aligned values
Font: 14pt monospace
Color: Light text on dark background
```

#### DEATH CAUSE (Optional)
```
Display: "You were defeated by: [Enemy Name]"
Position: Below stats
Font: 12pt italic
Color: Orange/Yellow
```

#### ACTION BUTTONS
```
Retry Button:
  Normal:  btn_retry_normal.png
  Position: X=250, Y=650
  
Level Select Button:
  Normal:  btn_levelselect_normal.png
  Position: X=412, Y=650
  
Main Menu Button:
  Normal:  btn_mainmenu_normal.png
  Position: X=574, Y=650
```

---

## PAGE 9: LEVEL COMPLETE / VICTORY SCREEN

### Screen Overview
- **Purpose**: Celebrate successful level completion
- **Information**: Score, bonuses, star rating, unlocks
- **Animation**: Dynamic stat reveals with sound effects
- **Unlocks**: Show newly unlocked levels/features

### Asset Components

#### VICTORY BACKGROUND
```
File: victory_background.png (1024x768)
Style: Bright, celebratory (not dark like game over)
Color: Light gradient, gold/cyan accents
Alpha: 100%
Animation: Optional subtle particle effect overlay
```

#### VICTORY TEXT BANNER
```
File: text_victory.png
Content: "LEVEL COMPLETE!" or "CONGRATULATIONS!"
Position: Top center, Y=80
Dimensions: 600x80
Font: 56pt bold
Color: Gold/Bright cyan
Effect: Glowing, bouncy animation
```

#### RESULTS PANEL
```
Frame: results_frame.png (550x300)
Position: Center Y=250
Elements (Left-aligned with value numbers):
  - Base Score: 1000
  - Time Bonus: +500
  - No Damage Bonus: +250
  - Collectibles: 8/8 (+250)
  ─────────────
  - FINAL SCORE: 2000
  
Font: Bold 16pt for labels, 18pt for values
Color: White text, gold numbers for totals
```

#### STAR RATING
```
File: Three star icons (star_full.png, star_empty.png)
Position: Below results, center
Position: Y=600
Display: 1-3 filled stars based on score
Dimensions: 48x48 per star
Animation: Stars pop in sequence with sound
```

#### NEXT LEVEL UNLOCK (Optional)
```
File: unlock_next_level.png (or rendered text)
Content: "NEXT LEVEL UNLOCKED!"
Position: Bottom center
Color: Bright, positive color
Animation: Fade in after star rating
```

#### ACTION BUTTONS
```
Next Level Button:
  Label: "NEXT LEVEL" (if available)
  Position: X=250, Y=680
  State: Enabled/Disabled based on available levels
  
Retry Button:
  Label: "RETRY"
  Position: X=412, Y=680
  
Menu Button:
  Label: "MAIN MENU"
  Position: X=574, Y=680
```

---

## PAGE 10: TUTORIAL / HOW TO PLAY SCREEN

### Screen Overview
- **Purpose**: Teach game mechanics to new players
- **Format**: Page-based tutorial with navigation
- **Pages**: 4-6 screens covering controls, mechanics, combat, tips
- **Skip Option**: Allow players to skip to main game

### Asset Components

#### TUTORIAL PAGE LAYOUT

**Main Frame:**
```
Frame: tutorial_frame.png (800x550)
Position: Centered X=112, Y=109
```

**Navigation Buttons:**
```
Previous Page:
  Normal:  btn_prev_tutorial_normal.png
  Position: X=180, Y=650
  
Next Page:
  Normal:  btn_next_tutorial_normal.png
  Position: X=844, Y=650
  
Skip Tutorial:
  Normal:  btn_skip_tutorial_normal.png
  Position: X=500, Y=650
```

**Page Indicators:**
```
Display: "PAGE 1 OF 5"
Position: Center bottom, Y=680
Font: 12pt
Color: Light grey
```

#### TUTORIAL PAGES

**PAGE 1: MOVEMENT**
```
Title: "HOW TO MOVE"
Content:
  - Arrow Keys / WASD to move left and right
  - SPACE to jump
  - Can double jump while airborne
  
Illustration:
  - Character sprite shown with movement arrows
  - Key labels (←→ SPACE)
  - Flow arrows showing jump arc
  
Assets:
  - char_movement_demo.png (animated GIF or spritesheet)
  - key_arrow_left.png, key_arrow_right.png, key_space.png
```

**PAGE 2: COMBAT**
```
Title: "HOW TO FIGHT"
Content:
  - LEFT CLICK to attack
  - Attack speed increases with weapons
  - Defeat enemies to progress
  
Illustration:
  - Character attacking enemy
  - Attack arc/hitbox visualization
  - Enemy with health bar
  
Assets:
  - combat_demo.png (action sequence)
  - attack_arc.png (visual effect)
```

**PAGE 3: WEAPONS & ITEMS**
```
Title: "WEAPONS & ITEMS"
Content:
  - Find weapons to upgrade firepower
  - Pick up health items to restore HP
  - Number keys 1-3 switch weapons
  
Illustration:
  - Weapon icons with damage values
  - Health item glow effect
  - Weapon switching visual
  
Assets:
  - weapon_icons_{1,2,3}.png
  - item_health.png
  - key_1.png, key_2.png, key_3.png
```

**PAGE 4: HAZARDS**
```
Title: "WATCH OUT!"
Content:
  - Red marked tiles deal damage
  - Electric hazards instant kill
  - Avoid falling off platforms
  
Illustration:
  - Hazard tile examples with warning
  - Danger symbols
  - Character taking damage
  
Assets:
  - hazard_tile_{danger,electric}.png
  - warning_icon.png
  - damage_effect.png
```

**PAGE 5: SPECIAL MOVES** (Character specific)
```
Title: "SPECIAL ABILITIES"
Content:
  - Hold SHIFT for character-specific super move
  - Recharges after use
  - Use strategically for tough enemies
  
Illustration:
  - Character performing special move
  - Cooldown indicator
  - Damage/effect radius
  
Assets:
  - special_ability_demo.png
  - cooldown_bar.png
```

**PAGE 6: TIPS & TRICKS**
```
Title: "PRO TIPS"
Content:
  - Collect all items for score bonuses
  - No-damage clears reward extra stars
  - Practice jumping in level 1-1
  - Experiment with different weapons
  
Illustration:
  - Highlighted paths
  - Bonus item locations
  - Skill challenges
  
Assets:
  - tip_icons.png
  - achievement_badge.png
```

### Code Template
```java
public class TutorialScreen extends AnimationAndSpriteLoader {
    
    private int currentPage = 0;
    private static final int TOTAL_PAGES = 6;
    
    private BufferedImage[] pageBackgrounds;
    private BufferedImage[][] pageIllustrations;
    private String[][] pageText;
    
    private MenuButton prevBtn, nextBtn, skipBtn;
    
    public TutorialScreen() {
        super();
        loadAssets();
        initializeButtons();
    }
    
    private void loadAssets() {
        pageBackgrounds = new BufferedImage[TOTAL_PAGES];
        pageIllustrations = new BufferedImage[TOTAL_PAGES][3];
        pageText = new String[TOTAL_PAGES][5];
        
        for (int i = 0; i < TOTAL_PAGES; i++) {
            pageBackgrounds[i] = loadImage(
                GUI_FRAMES + "tutorial_page_" + (i+1) + ".png"
            );
        }
    }
    
    public BufferedImage render(int width, int height) {
        BufferedImage canvas = new BufferedImage(width, height, 
                                                  BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = canvas.createGraphics();
        
        // Draw current page
        g.drawImage(pageBackgrounds[currentPage], 112, 109, null);
        
        // Draw page illustrations
        drawPageIllustrations(g, currentPage);
        
        // Draw page text
        drawPageText(g, currentPage);
        
        // Draw navigation buttons
        prevBtn.render(g);
        nextBtn.render(g);
        skipBtn.render(g);
        
        // Draw page indicator
        drawText(g, "PAGE " + (currentPage + 1) + " OF " + TOTAL_PAGES, 
                512, 680, 12, true);
        
        g.dispose();
        return canvas;
    }
    
    public void nextPage() {
        if (currentPage < TOTAL_PAGES - 1) currentPage++;
    }
    
    public void prevPage() {
        if (currentPage > 0) currentPage--;
    }
}
```

### Asset File Checklist - PAGES 7-10

**Settings Screen:**
- [ ] `Resources/industrial-zone/gui/1 Frames/settings_frame_main.png` (700x550)
- [ ] Slider assets (background, handle)
- [ ] Toggle on/off images

**Game Over Screen:**
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/gameover_overlay.png` (1024x768)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/text_game_over.png` (600x100)
- [ ] `Resources/industrial-zone/gui/1 Frames/gameover_stats_frame.png` (500x250)
- [ ] Buttons: retry, level_select, main_menu (3 sets)

**Level Complete Screen:**
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/victory_background.png` (1024x768)
- [ ] `Resources/industrial-zone/gui/9 Other/1 Decor/text_victory.png` (600x80)
- [ ] `Resources/industrial-zone/gui/1 Frames/results_frame.png` (550x300)
- [ ] `Resources/industrial-zone/gui/3 Icons/Icons/star_full.png` (48x48)
- [ ] `Resources/industrial-zone/gui/3 Icons/Icons/star_empty.png` (48x48)
- [ ] Buttons: next_level, retry, menu (3 sets)

**Tutorial Screen:**
- [ ] `Resources/industrial-zone/gui/1 Frames/tutorial_frame.png` (800x550)
- [ ] Tutorial page backgrounds (6 per page or single editable)
- [ ] Demonstration images for each page (movement, combat, weapons, hazards, special, tips)
- [ ] Key icon images (arrow keys, space, mouse click, 1-3, shift)
- [ ] Navigation buttons: prev, next, skip (3 sets)
- [ ] Skill demonstration sprites/animations

---

## SUMMARY & IMPLEMENTATION ROADMAP

### Document Components Completed

✅ **PAGE 1: INTRO/SPLASH SCREEN**
  - Studio logo animation
  - Game title reveal
  - Corner decorations
  - 7 asset files

✅ **PAGE 2: MAIN MENU SCREEN**
  - Tiled background system
  - Decorative overlay
  - Menu frame with border
  - 5 navigation buttons (3 states each)
  - 18 asset files

✅ **PAGE 3: CHARACTER SELECT SCREEN**
  - Character carousel navigation
  - Character preview display
  - Stats panel with bars
  - Arrow buttons for navigation
  - Confirm/Cancel buttons
  - 25+ asset files

✅ **PAGE 4: LEVEL SELECT SCREEN**
  - Level card grid (2x2)
  - Level preview images
  - Lock/Unlock visual system
  - Completion badges
  - 8+ asset files

✅ **PAGE 5: IN-GAME HUD (Heads-Up Display)**
  - 6 HUD panel frames
  - Health bar system
  - Ammo counter
  - Score/Timer display
  - Mini-map system
  - Status effect icons
  - Dynamic rendering code

✅ **PAGE 6: PAUSE MENU SCREEN**
  - Full-screen overlay
  - Pause panel with border
  - 4 navigation buttons
  - Game snapshot option
  - 12+ asset files

✅ **PAGE 7: SETTINGS/OPTIONS SCREEN**
  - Tabbed interface (Audio, Video, Controls, Gameplay)
  - Slider controls
  - Toggle switches
  - Save/Cancel/Reset buttons
  - 15+ asset files

✅ **PAGE 8: GAME OVER SCREEN**
  - Game over banner animation
  - Final stats display
  - Death cause display
  - Retry/Level Select/Menu buttons
  - 10+ asset files

✅ **PAGE 9: LEVEL COMPLETE / VICTORY SCREEN**
  - Victory banner with animation
  - Score breakdown panel
  - Star rating system
  - Next level unlock notification
  - Navigation buttons
  - 12+ asset files

✅ **PAGE 10: TUTORIAL / HOW TO PLAY SCREEN**
  - 6-page tutorial system
  - Pages: Movement, Combat, Weapons, Hazards, Special Moves, Tips
  - Page navigation (Previous/Next/Skip)
  - Demonstration images per page
  - Key icon displays
  - 20+ asset files

### Total Asset Count
- **Frames**: 10+ unique frame designs
- **Buttons**: 40+ button graphics (various states)
- **Icons**: 30+ icon assets
- **Text/Titles**: 15+ text/title images
- **Decorations**: 20+ decorative elements
- **Special**: Bars, overlays, backgrounds (10+)

**Total Estimated Assets**: 150+ PNG images

### Implementation Priority

**PHASE 1 (Critical):**
1. Splash Screen (Page 1)
2. Main Menu (Page 2)
3. In-Game HUD (Page 5)

**PHASE 2 (Core Gameplay):**
4. Character Select (Page 3)
5. Level Select (Page 4)
6. Pause Menu (Page 6)

**PHASE 3 (Polish):**
7. Game Over (Page 8)
8. Level Complete (Page 9)
9. Settings (Page 7)
10. Tutorial (Page 10)

### Code Integration Points

Each screen extends `AnimationAndSpriteLoader` and provides:
- `loadAssets()` - Asset initialization
- `render(Graphics2D g, int w, int h)` - Main rendering
- `updateMouseHover(int x, int y)` - Interactive elements
- `handleButtonClick(int x, int y)` - Input handling
- State getter/setter methods

All components use asset paths defined in `AnimationAndSpriteLoader.java` constants.

---

**Status**: COMPLETE - All 10 Pages of GUI Asset Planning Documented  
**Pages Completed**: 10 / 10 (100%)  
**Total Estimated Assets**: 150+ PNG files  
**Estimated Implementation Time**: 2-3 weeks (with asset creation)  
**Date Completed**: April 4, 2026  

**Next Steps:**
1. Create all asset files (or source from existing resources)
2. Implement each screen class following templates provided
3. Integrate with main GameGUIIntegration.java
4. Test each screen with interactive elements
5. Polish animations and transitions
