# ════════════════════════════════════════════════════════════════════════════════
# QUICK REFERENCE - AnimationAndSpriteLoader ASSETS & CLASSES
# Use This While Implementing Game.java
# ════════════════════════════════════════════════════════════════════════════════

---

## 🎨 STATIC STRING CONSTANTS (Use in Game.java)

### Character Assets
```java
AnimationAndSpriteLoader.PLAYER_BASE
AnimationAndSpriteLoader.BOSS_BASE
AnimationAndSpriteLoader.ENEMY_BASE
AnimationAndSpriteLoader.DRONE_BASE
AnimationAndSpriteLoader.SCIFI_BASE
```

### Level 1 Assets (Industrial Zone)
```java
AnimationAndSpriteLoader.L1_TILES_BASE           // Character codes: 65 types
AnimationAndSpriteLoader.L1_BG_BASE              // Background layers
AnimationAndSpriteLoader.L1_OBJECTS_BASE         // Static objects
AnimationAndSpriteLoader.L1_ANIMATED_BASE        // Animated objects
```

### Level 2 Assets (Power Station)
```java
AnimationAndSpriteLoader.L2_TILES_BASE           // Character codes: 64 types
AnimationAndSpriteLoader.L2_BG_BASE              // Background layers
AnimationAndSpriteLoader.L2_BG_DAY               // Day variant
AnimationAndSpriteLoader.L2_BG_NIGHT             // Night variant
AnimationAndSpriteLoader.L2_OBJECTS_BASE         // Static objects
AnimationAndSpriteLoader.L2_OBJECTS_TUBE         // Tubes
AnimationAndSpriteLoader.L2_OBJECTS_DECOR        // Decorations
AnimationAndSpriteLoader.L2_OBJECTS_LINES        // Power lines
AnimationAndSpriteLoader.L2_ANIMATED_BASE        // Animated objects
```

### GUI Assets
```java
AnimationAndSpriteLoader.GUI_FRAMES              // Button/window frames
AnimationAndSpriteLoader.GUI_BUTTONS             // 10 button variants
AnimationAndSpriteLoader.GUI_BARS                // Health/Energy bars
AnimationAndSpriteLoader.GUI_ICONS               // HUD icons
AnimationAndSpriteLoader.GUI_ICONS_BUTTONS       // Button icons
AnimationAndSpriteLoader.GUI_ICONS_ICONS         // Generic icons
AnimationAndSpriteLoader.GUI_PALETTE             // Color palette images
AnimationAndSpriteLoader.GUI_LOGO                // Title/Logo
AnimationAndSpriteLoader.GUI_NUMBERS             // Digit images 0-9
AnimationAndSpriteLoader.GUI_CURSORS             // Mouse cursors
AnimationAndSpriteLoader.GUI_OTHER               // Other GUI
AnimationAndSpriteLoader.GUI_FONT                // Font folder
AnimationAndSpriteLoader.GUI_FONT_IMAGES         // ⭐ CHARACTER IMAGES (text rendering)
AnimationAndSpriteLoader.GUI_CARD_ANIM           // Character cards
```

### VFX Assets
```java
AnimationAndSpriteLoader.VFX_SMOKE               // Smoke effects
AnimationAndSpriteLoader.VFX_BLOOD               // Blood effects
AnimationAndSpriteLoader.VFX_SPARKS              // Spark effects
AnimationAndSpriteLoader.VFX_PARTICLES           // Particle effects
AnimationAndSpriteLoader.VFX_OTHER               // Other VFX
AnimationAndSpriteLoader.VFX_EXTRA               // Extra effects
AnimationAndSpriteLoader.VFX_EXTRA_CHARACTER     // Character effects
AnimationAndSpriteLoader.VFX_EXTRA_OBJECTS       // Object destruction
AnimationAndSpriteLoader.VFX_EXTRA_BOX1          // Box 1 destruction
AnimationAndSpriteLoader.VFX_EXTRA_BOX2          // Box 2 destruction
AnimationAndSpriteLoader.VFX_EXTRA_BUSH          // Bush destruction
AnimationAndSpriteLoader.VFX_EXTRA_CAPSULE       // Capsule destruction
```

### Weapon Assets
```java
AnimationAndSpriteLoader.WEAPON_1                // Weapon set 1 base
AnimationAndSpriteLoader.WEAPON_1_CHAR           // Characters with weapons
AnimationAndSpriteLoader.WEAPON_1_CHAR_BIKER     // Biker character
AnimationAndSpriteLoader.WEAPON_1_CHAR_PUNK      // Punk character
AnimationAndSpriteLoader.WEAPON_1_CHAR_CYBER     // Cyborg character
AnimationAndSpriteLoader.WEAPON_1_GUNS           // Gun sprites
AnimationAndSpriteLoader.WEAPON_1_HANDS          // Hand sprites
AnimationAndSpriteLoader.WEAPON_1_EFFECTS        // Shoot effects
AnimationAndSpriteLoader.WEAPON_1_BULLETS        // Bullet sprites

// Weapon 2 same structure as Weapon 1
AnimationAndSpriteLoader.WEAPON_2
AnimationAndSpriteLoader.WEAPON_2_CHAR
// ... etc
```

### Audio Assets
```java
AnimationAndSpriteLoader.AUDIO_BASE              // Audio folder
AnimationAndSpriteLoader.AUDIO_MUSIC_MIDI        // MIDI music
AnimationAndSpriteLoader.AUDIO_MUSIC_WAV         // WAV music
AnimationAndSpriteLoader.AUDIO_SFX               // Sound effects
```

---

## 🧩 NESTED CLASSES (Use in Game.java)

### Tile Registry - CHARACTER CODE TO PNG LOOKUP
```java
// Get tile PNG path by character code
AnimationAndSpriteLoader.Level1TileRegistry.getTile(char c)
AnimationAndSpriteLoader.Level1TileRegistry.getTileCount()  // Returns 64 or 65

AnimationAndSpriteLoader.Level2TileRegistry.getTile(char c)
AnimationAndSpriteLoader.Level2TileRegistry.getTileCount()  // Returns 64

// Example Usage:
String tilePath = AnimationAndSpriteLoader.Level1TileRegistry.getTile('A');
BufferedImage tileImage = cache.get(tilePath);
```

### Parallax System - BACKGROUND LAYERS
```java
// Create parallax system
ParallexSystem level1Parallax = new AnimationAndSpriteLoader.ParallaxSystem();

// Render parallax
level1Parallax.render(Graphics2D g, int screenWidth, int screenHeight);

// Update camera position
level1Parallax.updateCamera(float cameraX);
```

### GUI Button System
```java
// Access button properties
AnimationAndSpriteLoader.GUIButtonSystemProperties
  .ButtonVariants                          // 10 button types

// Button variants available:
- StandardButtonVariant
- CyanAccentButtonVariant
- HoloButtonVariant
- GlassButtonVariant
- GreenConfirmButtonVariant
- MetalButtonVariant
- OrangeWarningButtonVariant
- RedCancelButtonVariant
- CyanLargeButtonVariant
- PressurePlateButtonVariant
```

### HUD Bar System
```java
// Access HUD bar properties
AnimationAndSpriteLoader.GUIButtonSystemProperties.HUDBarSystem
  .HealthBarStates                         // Health bar images
  .EnergyBarStates                         // Energy bar images
```

### GUI Tileset System - FRAME ASSEMBLY
```java
// Access frame pieces for UI construction
AnimationAndSpriteLoader.GUITilesetSystem
  .CornerPieces                            // TL, TR, BL, BR corners
  .EdgePieces                              // Top, Bottom, Left, Right edges
  .DividerPieces                           // Dividers
  .FillPieces                              // Interior fills
  .PanelPieces                             // Panel backgrounds
```

### GUI Frame Asset Properties
```java
// Master reference for assembling UI panels
AnimationAndSpriteLoader.GUIFrameAssetProperties
  .CornerPieces                            // Corner assets
  .EdgePieces                              // Edge assets
  .FillPieces                              // Fill assets
  .PanelPieces                             // Panel assets
  .MasterReference                         // Complete reference
```

### Character Animation State Machine
```java
// Access character animation states
AnimationAndSpriteLoader.CharacterAnimationStateMachine
  .CharacterAnimationState                 // IDLE, WALK, RUN, JUMP, ATTACK, HIT, DEATH
```

### Bullet Spawner - WEAPON SYSTEM
```java
// Use parent's bullet system
AnimationAndSpriteLoader.BulletSpawner
  .fire(Vector2f position, Vector2f direction)
  .update(float deltaTime)
  .render(Graphics2D g)
```

### Enemy Controller - ENEMY MANAGEMENT
```java
// Use parent's enemy system
AnimationAndSpriteLoader.EnemyController
  .spawnEnemy(EnemyType type, float x, float y)
  .update(float deltaTime)
  .render(Graphics2D g)
```

### Boss Controller - BOSS MANAGEMENT
```java
// Use parent's boss system
AnimationAndSpriteLoader.BossController
  .spawnBoss(BossType type, float x, float y)
  .update(float deltaTime)
  .render(Graphics2D g)
```

### Enemy AI Behavior - AI LOGIC
```java
// Use parent's AI system
AnimationAndSpriteLoader.EnemyAIBehavior
  .setPattern(EnemyPattern pattern)       // PATROL, CHASE, ATTACK
  .update(Entity enemy, Player player)    // Update AI state
  .decideAction()                         // Get next AI action
```

### Game State Manager
```java
// Use parent's state management
AnimationAndSpriteLoader.GameStateManager
  .setState(String state)                 // MENU, PLAYING, PAUSED, GAME_OVER
  .getState()                             // Get current state
```

### Entity Controller
```java
// Use parent's entity system
AnimationAndSpriteLoader.EntityController
  .createEntity(EntityType type)
  .removeEntity(Entity e)
  .updateAll(float deltaTime)
  .renderAll(Graphics2D g)
```

---

## 📝 FONT IMAGE RENDERING

### Font Images Location
```
Resources/industrial-zone/gui/10 Font/images/
```

### Font Files Available
- **1_01.png through 1_63.png** (63 character images)
- ASCII codes: 33-95 (!, ", #, ..., ~)

### In Game.java
```java
// Load font images
private Map<Character, BufferedImage> fontImageCache = new HashMap<>();

// Load them in constructor or loadFontImages()
for (int i = 0; i < 63; i++) {
    char character = (char)(33 + i);  // ASCII 33-95
    BufferedImage img = ImageIO.read(new File(GUI_FONT_IMAGES + "1_" + String.format("%02d", i+1) + ".png"));
    fontImageCache.put(character, img);
}

// Render text
void renderText(Graphics2D g, String text, int x, int y, int charW, int charH) {
    int currentX = x;
    for (char c : text.toCharArray()) {
        BufferedImage charImg = fontImageCache.get(c);
        if (charImg != null) {
            g.drawImage(charImg, currentX, y, charW, charH, null);
            currentX += charW;
        }
    }
}
```

---

## 🔌 PARENT CLASS METHODS TO USE

### From game2D.GameCore (inherited)
```java
update(long elapsedTime)      // Called each frame
draw(Graphics2D g)            // Called each frame
keyPressed(KeyEvent e)        // Called on key press
run(boolean fullscreen, int width, int height)  // Start game loop
```

### From AnimationAndSpriteLoader (parent)
```java
// Already initialized on startup
Level1TileRegistry          // Static tile lookup
Level2TileRegistry          // Static tile lookup
ParallaxSystem              // Parallax rendering
EnemyAIBehavior             // Enemy AI
BulletSpawner               // Weapon system
CharacterAnimationStateMachine  // Character animations
GameStateManager            // Game state
```

---

## ✅ USAGE EXAMPLES IN GAME.JAVA

### Example 1: Load Assets
```java
private void loadRasterAssets() {
    try {
        // Use constants from parent
        loadAssetsFromFolder(GUI_FRAMES);
        loadAssetsFromFolder(L1_TILES_BASE);
        loadAssetsFromFolder(PLAYER_BASE);
        System.out.println("Cached " + imageCache.size() + " images");
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
```

### Example 2: Render Tilemap
```java
private void renderTilemap(Graphics2D g) {
    char[] row = {'A', 'P', 'C', 'U', 'V', 'E'};
    
    for (int i = 0; i < row.length; i++) {
        // Get PNG path from parent's registry
        String tilePath = AnimationAndSpriteLoader.Level1TileRegistry.getTile(row[i]);
        
        if (tilePath != null) {
            BufferedImage tile = imageCache.get(tilePath);
            if (tile != null) {
                int x = i * 64 - (int)cameraX;
                int y = getHeight() - 64;
                g.drawImage(tile, x, y, 64, 64, null);
            }
        }
    }
}
```

### Example 3: Render Text
```java
private void renderText(Graphics2D g, String text, int x, int y) {
    int charWidth = 16;
    int charHeight = 16;
    int currentX = x;
    
    for (char c : text.toCharArray()) {
        BufferedImage charImg = fontImageCache.get(c);
        if (charImg != null) {
            g.drawImage(charImg, currentX, y, charWidth, charHeight, null);
            currentX += charWidth;
        }
    }
}
```

### Example 4: Use Parallax
```java
private void initializeParallaxSystems() {
    try {
        level1Parallax = new AnimationAndSpriteLoader.ParallaxSystem();
        System.out.println("Parallax initialized");
    } catch (Exception e) {
        System.out.println("Parallax error: " + e.getMessage());
    }
}

@Override
public void draw(Graphics2D g) {
    if (level1Parallax != null && currentLevel == 1) {
        level1Parallax.render(g, getWidth(), getHeight());
    }
}
```

---

## 🚀 QUICK TIPS

1. **Always use parent constants** instead of hardcoding paths
   ```java
   ✅ loadAssetsFromFolder(GUI_FRAMES);
   ❌ loadAssetsFromFolder("Resources/industrial-zone/gui/1 Frames/");
   ```

2. **Check if asset exists before using**
   ```java
   if (imageCache.get(path) != null) {
       g.drawImage(imageCache.get(path), x, y, w, h, null);
   }
   ```

3. **Use fully qualified names for Graphics2D and Color**
   ```java
   ✅ public void draw(java.awt.Graphics2D g)
   ✅ g.drawImage(img, x, y, w, h, null);
   ❌ g.setColor(Color.BLACK);  // Use images instead
   ```

4. **Cache everything at startup** - don't load in draw()
   ```java
   // Constructor: Load everything
   loadRasterAssets();
   loadFontImages();
   
   // draw(): Just render from cache
   BufferedImage img = imageCache.get(path);
   g.drawImage(img, x, y, w, h, null);
   ```

5. **Delegate to parent when possible**
   ```java
   // Parent has all the complex systems
   // Just use them in your draw() method
   level1Parallax.render(g, width, height);
   super.draw(g);  // Fallback
   ```

---

**Reference Complete** ✅
**Ready to Implement** 🚀
