# GUI QUICK REFERENCE & IMPLEMENTATION GUIDE

## 1. ASSET PATHS - Copy & Paste Ready

```java
// In AnimationAndSpriteLoader.java, these constants are already defined:
public static final String GUI_BASE           = "Resources/industrial-zone/gui/";
public static final String GUI_FRAMES         = GUI_BASE + "1 Frames/";           // 82 tiles
public static final String GUI_BARS           = GUI_BASE + "2 Bars/";            // Health, ammo
public static final String GUI_ICONS          = GUI_BASE + "3 Icons/";           // Status icons
public static final String GUI_ICONS_BUTTONS  = GUI_BASE + "3 Icons/Buttons2/";  // Button icons
public static final String GUI_BUTTONS        = GUI_BASE + "6 Buttons/";         // ⭐ PRIMARY
public static final String GUI_NUMBERS        = GUI_BASE + "7 Numbers/";         // Digits 0-9
public static final String GUI_CARD_ANIM      = GUI_BASE + "card-animations/";   // Cards
```

## 2. CORE COMPONENT TEMPLATE

```java
// Template for all GUI components - COPY THIS

public class MyGUIComponent extends AnimationAndSpriteLoader {
    
    // ==========================================
    // PROPERTIES: Asset caching
    // ==========================================
    private BufferedImage backgroundFrame;
    private BufferedImage[] iconAssets;
    private Map<String, BufferedImage> assetCache;
    
    // ==========================================
    // INITIALIZATION
    // ==========================================
    public MyGUIComponent() {
        super();  // Call AnimationAndSpriteLoader constructor
        loadAssets();
    }
    
    private void loadAssets() {
        // Load from GUI_BASE constants
        backgroundFrame = loadImage(GUI_FRAMES + "frame_component.png");
        iconAssets = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            iconAssets[i] = loadImage(
                GUI_ICONS_ICONS + "icon_" + i + ".png"
            );
        }
    }
    
    // ==========================================
    // CORE RENDERING: Return BufferedImage
    // ==========================================
    public BufferedImage render() {
        // Create canvas
        BufferedImage canvas = new BufferedImage(
            WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = canvas.createGraphics();
        
        // Draw background
        g2d.drawImage(backgroundFrame, 0, 0, null);
        
        // Draw icons/content
        for (int i = 0; i < iconAssets.length; i++) {
            g2d.drawImage(iconAssets[i], 10 + i*50, 10, null);
        }
        
        g2d.dispose();
        return canvas;
    }
}
```

## 3. BUTTON IMPLEMENTATION TEMPLATE

```java
public class InteractiveButton extends AnimationAndSpriteLoader {
    
    private String buttonId;
    private int x, y;
    private BufferedImage normalImage;
    private BufferedImage hoverImage;
    private BufferedImage pressedImage;
    private ButtonState state = ButtonState.NORMAL;
    private Runnable action;
    
    public enum ButtonState { NORMAL, HOVER, PRESSED, DISABLED }
    
    public InteractiveButton(String id, String name, int x, int y, Runnable action) {
        super();
        this.buttonId = id;
        this.x = x;
        this.y = y;
        this.action = action;
        loadAssets(name);
    }
    
    private void loadAssets(String name) {
        String base = AnimationAndSpriteLoader.GUI_BUTTONS;
        normalImage = loadImage(base + "btn_" + name + ".png");
        hoverImage = loadImage(base + "btn_" + name + "_hover.png");
        pressedImage = loadImage(base + "btn_" + name + "_pressed.png");
    }
    
    // Input handling
    public boolean isMouseOver(int mx, int my) {
        return mx >= x && mx <= x + normalImage.getWidth() 
            && my >= y && my <= y + normalImage.getHeight();
    }
    
    public void handleMouseMove(int mx, int my) {
        if (state == ButtonState.PRESSED) return;
        state = isMouseOver(mx, my) ? ButtonState.HOVER : ButtonState.NORMAL;
    }
    
    public void handleMousePress(int mx, int my) {
        if (isMouseOver(mx, my)) state = ButtonState.PRESSED;
    }
    
    public void handleMouseRelease(int mx, int my) {
        if (state == ButtonState.PRESSED) {
            state = ButtonState.NORMAL;
            if (isMouseOver(mx, my)) action.run();  // ← Trigger action
        }
    }
    
    // Rendering
    public BufferedImage render() {
        return switch(state) {
            case NORMAL -> normalImage;
            case HOVER -> hoverImage;
            case PRESSED -> pressedImage;
            default -> normalImage;
        };
    }
}
```

## 4. ASSET CACHE BEST PRACTICES

```java
public class GUIAssetManager extends AnimationAndSpriteLoader {
    
    private static final Map<String, BufferedImage> cache = new HashMap<>();
    
    // Get or load asset (with caching)
    public static BufferedImage getAsset(String assetPath) {
        if (cache.containsKey(assetPath)) {
            return cache.get(assetPath);  // ← Fast path (cache hit)
        }
        
        try {
            BufferedImage asset = ImageIO.read(new File(assetPath));
            cache.put(assetPath, asset);  // ← Cache for future use
            return asset;
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to load: " + assetPath);
            return null;  // Critical error - let it fail
        }
    }
    
    // Clean up all assets (call on level/screen change)
    public static void clearCache() {
        cache.values().forEach(BufferedImage::flush);
        cache.clear();
        System.out.println("[GUIAssetManager] Cache cleared");
    }
    
    // Preload essential assets (startup)
    public static void preloadEssentialAssets() {
        System.out.println("[GUIAssetManager] Preloading...");
        getAsset(AnimationAndSpriteLoader.GUI_BUTTONS + "btn_pause.png");
        getAsset(AnimationAndSpriteLoader.GUI_BUTTONS + "btn_pause_hover.png");
        getAsset(AnimationAndSpriteLoader.GUI_BUTTONS + "btn_pause_pressed.png");
        // ... add all essential assets
        System.out.println("[GUIAssetManager] Preload complete");
    }
}
```

## 5. STATE-SPECIFIC IMPLEMENTATIONS

### MainMenuScreen
```java
public class MainMenuScreen extends AnimationAndSpriteLoader {
    
    private BufferedImage backgroundImage;
    private List<InteractiveButton> menuButtons;
    
    public MainMenuScreen() {
        super();
        backgroundImage = loadImage(GUI_BASE + "bg_mainmenu.png");
        menuButtons = Arrays.asList(
            new InteractiveButton("new", "play", 200, 150, 
                () -> Game.setState(GameState.CHARACTER_SELECT)),
            new InteractiveButton("settings", "settings", 200, 220,
                () -> Game.setState(GameState.SETTINGS)),
            new InteractiveButton("quit", "quit", 200, 290,
                () -> System.exit(0))
        );
    }
    
    @Override
    public void handleMouseMove(int mx, int my) {
        menuButtons.forEach(btn -> btn.handleMouseMove(mx, my));
    }
    
    @Override
    public void handleMousePress(int mx, int my) {
        menuButtons.forEach(btn -> btn.handleMousePress(mx, my));
    }
    
    @Override
    public void handleMouseRelease(int mx, int my) {
        menuButtons.forEach(btn -> btn.handleMouseRelease(mx, my));
    }
    
    public BufferedImage render(int screenWidth, int screenHeight) {
        BufferedImage screen = new BufferedImage(
            screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g2d = screen.createGraphics();
        
        // Background
        g2d.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
        
        // Buttons
        for (InteractiveButton btn : menuButtons) {
            g2d.drawImage(btn.render(), btn.getX(), btn.getY(), null);
        }
        
        g2d.dispose();
        return screen;
    }
}
```

### InGameGUI (TopBar + Sidebar + Buttons + HUD)
```java
public class InGameGUI extends JPanel {
    
    private TopBarPanel topBar;
    private LeftSidebar sidebar;
    private ButtonPanel buttons;
    private HUDBar hudBar;
    
    private PlayerState playerState;
    private GameState gameState;
    
    public InGameGUI() {
        topBar = new TopBarPanel();
        sidebar = new LeftSidebar();
        buttons = new ButtonPanel();
        hudBar = new HUDBar();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        
        // Composite all GUI elements
        BufferedImage topBarImg = topBar.render(playerState, w);
        BufferedImage sidebarImg = sidebar.render(playerState.inventory);
        BufferedImage buttonsImg = buttons.render();
        BufferedImage hudImg = hudBar.render(gameState);
        
        // Draw each at proper position
        g2d.drawImage(topBarImg, 0, 0, null);                  // Top
        g2d.drawImage(sidebarImg, 0, 48, null);                // Left
        g2d.drawImage(buttonsImg, w - 80, 48, null);           // Right
        g2d.drawImage(hudImg, 0, h - 32, null);                // Bottom
    }
    
    public void update(PlayerState player, GameState state) {
        this.playerState = player;
        this.gameState = state;
        repaint();
    }
}
```

## 6. COMPLETE RENDERING FLOW

```java
// In Game.java or GamePanel.java

public void renderFrame() {
    // 1. Get current game state snapshot
    PlayerState playerSnapshot = getCurrentPlayerState();
    GameState gameStateSnapshot = getCurrentGameState();
    
    // 2. Render level background (parallax, entities, VFX)
    BufferedImage levelCanvas = renderLevelGeometry();
    
    // 3. Render GUI overlay (ONLY if in GAME_ACTIVE state)
    if (gameStateSnapshot == GameState.GAME_ACTIVE) {
        
        // Render each panel to BufferedImage
        BufferedImage topBarRender = topBar.render(playerSnapshot, screenWidth);
        BufferedImage sidebarRender = sidebar.render(playerSnapshot.inventory);
        BufferedImage buttonsRender = buttons.render();
        BufferedImage hudRender = hudBar.render(gameStateSnapshot);
        
        // Composite onto level canvas
        Graphics2D g2d = levelCanvas.createGraphics();
        g2d.drawImage(topBarRender, 0, 0, null);
        g2d.drawImage(sidebarRender, 0, 48, null);
        g2d.drawImage(buttonsRender, screenWidth - 80, 48, null);
        g2d.drawImage(hudRender, 0, screenHeight - 32, null);
        g2d.dispose();
    }
    
    // 4. Display final composite
    Graphics2D windowG2d = (Graphics2D) getGraphics();
    windowG2d.drawImage(levelCanvas, 0, 0, null);
    windowG2d.dispose();
}
```

## 7. INPUT HANDLING INTEGRATION

```java
// In MouseInputHandler or GamePanel.addMouseListener()

public void handleMouseEvent(MouseEvent e) {
    int mx = e.getX();
    int my = e.getY();
    
    switch(e.getID()) {
        case MouseEvent.MOUSE_MOVED:
            // Update button states (hover/normal)
            if (gameState == GameState.GAME_ACTIVE) {
                buttonPanel.handleMouseMove(mx, my);
            } else if (currentScreen != null) {
                currentScreen.handleMouseMove(mx, my);
            }
            break;
            
        case MouseEvent.MOUSE_PRESSED:
            // Detect button press
            if (gameState == GameState.GAME_ACTIVE) {
                buttonPanel.handleMousePress(mx, my);
            } else if (currentScreen != null) {
                currentScreen.handleMousePress(mx, my);
            }
            break;
            
        case MouseEvent.MOUSE_RELEASED:
            // Detect button release & trigger actions
            if (gameState == GameState.GAME_ACTIVE) {
                buttonPanel.handleMouseRelease(mx, my);
            } else if (currentScreen != null) {
                currentScreen.handleMouseRelease(mx, my);
            }
            break;
    }
}
```

## 8. STARTUP SEQUENCE

```java
// In Game.constructor()

public Game() {
    super();  // Initialize GameCore
    
    // 1. Preload essential GUI assets
    System.out.println("[Game] Loading GUI assets...");
    GUIAssetManager.preloadEssentialAssets();  // ~200ms, blocking
    
    // 2. Initialize in-game GUI components
    topBar = new TopBarPanel();
    sidebar = new LeftSidebar();
    buttons = new ButtonPanel();
    hudBar = new HUDBar();
    System.out.println("[Game] GUI components initialized");
    
    // 3. Initialize screens
    mainMenuScreen = new MainMenuScreen();
    characterSelectScreen = new CharacterSelectScreen();
    pauseMenuScreen = new PauseMenuScreen();
    System.out.println("[Game] Menu screens initialized");
    
    // 4. Set initial state
    gameState = GameState.MAIN_MENU;
    System.out.println("[Game] Ready to display");
}
```

## 9. CRITICAL DON'Ts - NEVER DO THIS

```java
// ❌ WRONG - Drawing with Graphics2D primitives
g2d.fillRect(x, y, width, height);           // DON'T
g2d.drawString("Health: 100", 50, 50);       // DON'T
g2d.fillRoundRect(x, y, w, h, arc, arc);    // DON'T
g2d.fillOval(cx, cy, radius*2, radius*2);   // DON'T

// ❌ WRONG - Using Color objects as fallback
return new BufferedImage(64, 48, TYPE_INT_RGB);
Graphics2D g2d = image.createGraphics();
g2d.setColor(new Color(64, 128, 200));
g2d.fillRect(0, 0, 64, 48);  // This is vector graphics!

// ❌ WRONG - Hardcoded dimensions
int buttonWidth = 64;   // Use actual image dimensions instead
int buttonHeight = 48;  // Load from asset: image.getWidth()

// ❌ WRONG - Mixing paradigms
if (assetLoadFailed) {
    g2d.setColor(Color.RED);
    g2d.fillRect(0, 0, 100, 100);  // Fallback vector - NO!
}
```

## 10. CRITICAL DOs - ALWAYS DO THIS

```java
// ✅ RIGHT - Load real images
BufferedImage buttonAsset = ImageIO.read(
    new File(AnimationAndSpriteLoader.GUI_BUTTONS + "btn_pause.png")
);
g2d.drawImage(buttonAsset, x, y, null);

// ✅ RIGHT - Cache assets
Map<String, BufferedImage> cache = new HashMap<>();
private BufferedImage getButton(String name) {
    return cache.computeIfAbsent(name, k -> 
        loadImage(GUI_BUTTONS + "btn_" + k + ".png")
    );
}

// ✅ RIGHT - Use actual image dimensions
int width = buttonAsset.getWidth();
int height = buttonAsset.getHeight();
g2d.drawImage(buttonAsset, x, y, width, height, null);

// ✅ RIGHT - Fail loudly on missing assets
catch (IOException e) {
    System.err.println("[ERROR] Missing asset: " + assetPath);
    return null;  // Let it fail, don't create fallback
}

// ✅ RIGHT - State-driven rendering
public BufferedImage render() {
    return switch(state) {
        case NORMAL -> normalImage;
        case HOVER -> hoverImage;
        case PRESSED -> pressedImage;
    };
}
```

## 11. PERFORMANCE TARGETS

| Metric | Target | Max |
|--------|--------|-----|
| Asset load (startup) | 200ms | 500ms |
| Frame render | 16.67ms | 20ms (60 FPS) |
| Memory per GUI state | 20MB | 50MB |
| Frame composition | 5ms | 10ms |
| Input response | <5ms | <10ms |

## 12. VERIFICATION CHECKLIST

Before committing code, verify:

```
✅ All rendering uses BufferedImage.drawImage() ONLY
✅ No Graphics2D.fill*() or draw*() methods
✅ No Color() object creation for rendering
✅ All assets come from Resources/ folder
✅ Asset paths use GUI_BASE constants
✅ All components extend AnimationAndSpriteLoader
✅ Button state machine always has 3 states (NORMAL/HOVER/PRESSED)
✅ Input flows: Mouse → Button → Action
✅ Game state machine has exactly 8 states
✅ Null checks on all image loads
✅ Memory cleanup on state transitions
✅ 60 FPS rendering (verified in profiler)
```

---

## SUMMARY: Implementation Workflow

1. **Read** `GUI_IMPLEMENTATION_DETAILED_PLAN.md` (understand architecture)
2. **Study** `GUI_ARCHITECTURE_DIAGRAMS.md` (visualize flow)
3. **Reference** `GUI_QUICK_REFERENCE.md` (this file - copy/paste code)
4. **Implement** Phase 1-6 in order (don't skip phases)
5. **Verify** using checklist above (no vector graphics!)
6. **Test** each component independently before integration
7. **Profile** to ensure 60 FPS and reasonable memory usage
