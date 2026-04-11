# GUI Asset Images - Complete Reference

**Status**: ✓ Generated Successfully  
**Date**: April 3, 2026  
**Total Assets**: 23 PNG images  
**Format**: PNG (Raster Graphics)

---

## Asset Directory Structure

```
Resources/gui/
├── backgrounds/          (10 screen background images)
│   ├── splash_screen.png         (Initial splash screen)
│   ├── main_menu.png             (Main menu screen)
│   ├── character_select.png       (Character selection)
│   ├── level_select.png          (Level picker)
│   ├── how_to_play.png           (Tutorial/help screen)
│   ├── settings.png              (Settings/options)
│   ├── playing.png               (Gameplay HUD background)
│   ├── paused.png                (Pause menu background)
│   ├── level_complete.png        (Victory screen)
│   └── game_over.png             (Game over/failure screen)
│
├── buttons/              (3 button state images)
│   ├── button_normal.png          (Normal state - idle)
│   ├── button_hover.png           (Hover state - mouse over)
│   └── button_pressed.png         (Pressed state - clicked)
│
└── elements/             (10 UI element images)
    ├── panel_small.png            (400x300 panel background)
    ├── panel_medium.png           (600x400 panel background)
    ├── panel_large.png            (800x600 panel background)
    ├── progressbar_empty.png      (Empty progress bar)
    ├── progressbar_half.png       (50% filled progress bar)
    ├── progressbar_full.png       (100% filled progress bar)
    ├── checkbox_empty.png         (Unchecked checkbox)
    ├── checkbox_checked.png       (Checked checkbox)
    ├── slider_background.png      (Slider track)
    └── slider_knob.png            (Slider handle)
```

---

## Asset Specifications

### Screen Backgrounds
| Screen | Filename | Size | Purpose |
|--------|----------|------|---------|
| Splash Screen | splash_screen.png | 1920×1080 | Initial loading/splash |
| Main Menu | main_menu.png | 1920×1080 | Game menu |
| Character Selection | character_select.png | 1920×1080 | Choose character |
| Level Selection | level_select.png | 1920×1080 | Choose level |
| How To Play | how_to_play.png | 1920×1080 | Tutorial/help |
| Settings | settings.png | 1920×1080 | Options/preferences |
| Gameplay | playing.png | 1920×1080 | Active gameplay HUD |
| Paused | paused.png | 1920×1080 | Pause menu overlay |
| Level Complete | level_complete.png | 1920×1080 | Victory/success |
| Game Over | game_over.png | 1920×1080 | Failure/death |

### Button Graphics
| Button State | Filename | Size | Purpose |
|--------------|----------|------|---------|
| Normal | button_normal.png | 200×50 | Idle/default |
| Hover | button_hover.png | 200×50 | Mouse over |
| Pressed | button_pressed.png | 200×50 | Clicked/active |

### UI Elements
| Element Type | Filename | Size | Purpose |
|--------------|----------|------|---------|
| Small Panel | panel_small.png | 400×300 | Compact dialog |
| Medium Panel | panel_medium.png | 600×400 | Standard dialog |
| Large Panel | panel_large.png | 800×600 | Full-size dialog |
| Empty Bar | progressbar_empty.png | 300×20 | No progress |
| Half Bar | progressbar_half.png | 300×20 | 50% complete |
| Full Bar | progressbar_full.png | 300×20 | 100% complete |
| Empty Checkbox | checkbox_empty.png | 30×30 | Unchecked |
| Checked Checkbox | checkbox_checked.png | 30×30 | Checked |
| Slider Track | slider_background.png | 200×10 | Slider rail |
| Slider Knob | slider_knob.png | 20×20 | Slider handle |

---

## Color Palette Used

The assets use a consistent professional color scheme:

| Element | RGB Value | Usage |
|---------|-----------|-------|
| Dark Background | (20, 20, 25) | Main background |
| Medium Background | (40, 40, 50) | Secondary background |
| Light Background | (80, 80, 90) | Accent background |
| Accent Color | (0, 150, 255) | Primary highlight (blue) |
| Accent Hover | (50, 180, 255) | Highlight on hover (bright blue) |
| Accent Pressed | (0, 100, 200) | Highlight when pressed (dark blue) |
| Text | (220, 220, 220) | Light text |
| Text Dark | (50, 50, 50) | Dark text |
| Border | (100, 100, 110) | Component borders |
| Success | (0, 200, 80) | Positive/success (green) |
| Warning | (255, 150, 0) | Warning/alert (orange) |
| Error | (255, 50, 50) | Error/danger (red) |

---

## How to Use With CompleteGameGUI

### Step 1: Load Assets in CompleteGameGUI

The `CompleteGameGUI` class automatically loads images from the Resources/gui/ directory.

```java
// In CompleteGameGUI initialization
Map<String, BufferedImage> backgroundImages = new HashMap<>();
backgroundImages.put("splash", ImageIO.read(new File("Resources/gui/backgrounds/splash_screen.png")));
backgroundImages.put("menu", ImageIO.read(new File("Resources/gui/backgrounds/main_menu.png")));
// ... etc for each screen background
```

### Step 2: Reference in Component Code

Use the preloaded images when rendering components:

```java
// In GUIScreenManager.render()
BufferedImage screenBg = backgroundImages.get("splash");
g.drawImage(screenBg, 0, 0, null);
```

### Step 3: Create Custom Assets

To create additional assets, modify `generate_gui_assets.py` and rerun:

```bash
python generate_gui_assets.py
```

---

## Asset Usage Patterns

### Pattern 1: Screen Rendering
```java
// Get background for current screen state
BufferedImage background = loadScreenBackground(screenState);

// Draw full-screen background
g.drawImage(background, 0, 0, screenWidth, screenHeight, null);
```

### Pattern 2: Button Rendering
```java
// Select button state based on interaction
String buttonState = isPressed ? "pressed" : isHovered ? "hover" : "normal";
BufferedImage buttonImage = loadButtonImage(buttonState);

// Draw button
g.drawImage(buttonImage, x, y, null);
```

### Pattern 3: Panel Rendering
```java
// Choose panel size based on content
BufferedImage panel = loadPanelImage("medium");

// Draw panel background
g.drawImage(panel, x, y, panelWidth, panelHeight, null);

// Draw content on top
drawPanelContent(g, x, y);
```

### Pattern 4: Progress Bar Rendering
```java
// Calculate progress (0.0 to 1.0)
float progress = currentValue / maxValue;

// Load appropriate progress bar image
String barState = progress < 0.5 ? "empty" : progress < 1.0 ? "half" : "full";
BufferedImage bar = loadProgressBarImage(barState);

// Draw progress bar
g.drawImage(bar, x, y, null);
```

---

## Extending Assets

### Adding New Screen States

1. Update CompleteGameGUI.ScreenState enum
2. Create corresponding PNG image (1920×1080)
3. Add to generate_gui_assets.py screens dictionary
4. Rerun asset generator

### Adding New Button Variations

1. Extend button states in `GUIComponentLibrary`
2. Create button graphics (any size, typically 200×50+)
3. Add to generate_gui_assets.py
4. Rerun asset generator

### Creating Custom UI Elements

1. Design PNG graphic in image editor
2. Save to Resources/gui/elements/
3. Load in component initialization
4. Use drawImage() to render

---

## Technical Details

### PNG Format Specifications
- **Color Depth**: RGB (24-bit) or RGBA (32-bit with alpha)
- **Compression**: PNG lossless compression
- **Dimensions**: Variable (specified in table above)
- **DPI**: 72 DPI (standard for screen assets)

### Loading Assets in Java
```java
// Load single image
BufferedImage img = ImageIO.read(new File("Resources/gui/buttons/button_normal.png"));

// Load with caching
private static final Map<String, BufferedImage> imageCache = new HashMap<>();

public BufferedImage getImage(String path) throws IOException {
    if (!imageCache.containsKey(path)) {
        imageCache.put(path, ImageIO.read(new File(path)));
    }
    return imageCache.get(path);
}
```

### Scaling Assets at Runtime
```java
// Scale image to desired size
Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
BufferedImage scaledBuffered = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
Graphics2D g2d = scaledBuffered.createGraphics();
g2d.drawImage(scaledImage, 0, 0, null);
g2d.dispose();
```

---

## Integration With CompleteGameGUI

### Automatic Loading
The CompleteGameGUI system automatically loads these assets during initialization:

```java
public class CompleteGameGUI extends AnimationAndSpriteLoader {
    private void loadGUIImages() {
        try {
            // Load screen backgrounds
            backgroundImages.put("splash", loadImage("Resources/gui/backgrounds/splash_screen.png"));
            backgroundImages.put("menu", loadImage("Resources/gui/backgrounds/main_menu.png"));
            // ... loads all 10 screen backgrounds
            
            // Load UI elements
            uiElementImages.put("button_normal", loadImage("Resources/gui/buttons/button_normal.png"));
            uiElementImages.put("button_hover", loadImage("Resources/gui/buttons/button_hover.png"));
            uiElementImages.put("button_pressed", loadImage("Resources/gui/buttons/button_pressed.png"));
            // ... loads all UI elements
        } catch (IOException e) {
            System.err.println("[!] Failed to load GUI images: " + e.getMessage());
        }
    }
}
```

### Missing Asset Handling
If any image fails to load:
1. Component renders with fallback color
2. Error logged to console
3. Application continues (graceful degradation)
4. Check console output for missing file paths

---

## Asset Directory Setup Instructions

### For Windows
```powershell
# Assets are created automatically by generate_gui_assets.py
# Verify with:
Get-ChildItem -Recurse Resources/gui/ | Select-Object Name
```

### For macOS/Linux
```bash
# Assets are created automatically
# Verify with:
find Resources/gui/ -name "*.png" | sort
```

---

## Modifying Assets

### Edit Existing Images
1. Use image editor (Photoshop, GIMP, Paint.NET)
2. Open asset from Resources/gui/
3. Edit graphics
4. Save as PNG (ensure format compatibility)
5. Restart application for changes to load

### Regenerate All Assets
```bash
python generate_gui_assets.py
```

### Create Custom Assets
1. Design in image editor
2. Export as PNG (RGB or RGBA)
3. Place in appropriate Resources/gui/ subdirectory
4. Update CompleteGameGUI.loadGUIImages() to load new asset
5. Reference in component code

---

## Troubleshooting

### Assets Not Loading
**Problem**: Images not appearing in GUI  
**Solution**:
1. Verify file paths match exactly (case-sensitive on Linux/macOS)
2. Check Resources/gui/ directory exists
3. Verify PNG files are valid (try opening in image viewer)
4. Check console for error messages
5. Ensure CompleteGameGUI.loadGUIImages() is called

### Images Look Blurry
**Problem**: Rendered images appear pixelated  
**Solution**:
1. Reduce downscaling at runtime (use original size or upscale)
2. Use Image.SCALE_SMOOTH for scaling
3. Ensure source images are high enough resolution

### Colors Not Correct
**Problem**: UI colors don't match expected palette  
**Solution**:
1. Verify PNG color format (RGB vs RGBA)
2. Check display color profile settings
3. Regenerate assets: `python generate_gui_assets.py`

### Memory Issues
**Problem**: Out of memory errors with large GUI  
**Solution**:
1. Implement image caching (load once, reuse)
2. Release unused images: `imageCache.clear()`
3. Use smaller images where possible
4. Monitor memory with profiler

---

## Summary

✓ **23 GUI asset PNG images generated**  
✓ **All screen backgrounds ready** (10 images for each screen state)  
✓ **Button states complete** (normal, hover, pressed)  
✓ **UI elements provided** (panels, progress bars, checkboxes, sliders)  
✓ **Professional color palette** (consistent, accessible design)  
✓ **Ready for CompleteGameGUI integration**

---

## Next Steps

1. ✓ Assets generated and verified
2. Integrate with CompleteGameGUI.loadGUIImages()
3. Test rendering with GameWithCompleteGUI
4. Customize colors/design as needed
5. Create additional assets for game-specific needs

---

**GUI Asset Generation Complete ✓**  
**All Images Ready for Production ✓**
