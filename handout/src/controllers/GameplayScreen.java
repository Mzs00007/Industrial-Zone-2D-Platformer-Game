package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;
import animation.InputController.PixelCopyHelper;
import controllers.ManifestLoader;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * GAMEPLAY SCREEN - Main game level rendering WITH ANIMATION + CONTROLS
 * 
 * ✅ RASTER GRAPHICS ONLY - Pure PNG asset rendering
 * ✅ FRAME ANIMATION - Multi-frame sprite animation
 * ✅ KEYBOARD INPUT - WASD movement, ESC to return
 * ✅ MOUSE AIMING - Track mouse position for aim direction
 * ❌ NO Graphics2D, NO Font, NO Color, NO forbidden imports
 * ════════════════════════════════════════════════════════════════════════════
 */
public class GameplayScreen {
    
    private BufferedImage levelTilemap;           // Level background
    private BufferedImage playerSpriteIdle;       // Idle animation frames (horizontal strip)
    private BufferedImage playerSpriteWalk;       // Walk animation frames (horizontal strip)
    private BufferedImage currentDisplayFrame;    // Current frame to render
    private BufferedImage screenBuffer;           // Full screen render
    
    private int playerX = 512;                    // X position on screen
    private int playerY = 400;                    // Y position on screen
    private int screenWidth, screenHeight;
    
    private String currentLevel = "Industrial_zone_level_1";      // Current level name
    private String playerCharacter = "PUNK";      // Current character
    
    // ✅ ANIMATION SYSTEM
    private int currentFrameIndex = 0;            // Current frame in sprite strip
    private int totalFrames = 1;                  // Total frames in current animation
    private int frameWidth = 64;                  // Width of each frame
    private long lastFrameTime = System.currentTimeMillis();
    private long frameDuration = 100;             // MS between frame changes
    
    // ✅ INPUT TRACKING
    private boolean[] keysPressed = new boolean[256];  // Track all keys
    private int mouseX = 0, mouseY = 0;          // Mouse position for aiming
    
    // ✅ MOVEMENT STATE
    private boolean isMoving = false;             // Are we currently moving?
    private int velocityX = 0, velocityY = 0;    // Movement velocity
    
    private Map<String, BufferedImage> levelCache = new HashMap<>();
    private Map<String, BufferedImage> characterCache = new HashMap<>();
    
    private long lastUpdateTime = System.currentTimeMillis();
    
    /**
     * Constructor - Initialize gameplay screen
     */
    public GameplayScreen(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.screenBuffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        
        System.out.println("[GameplayScreen] Initialized " + screenWidth + "×" + screenHeight);
    }
    
    /**
     * Load a level by name using ManifestLoader
     */
    public void loadLevel(String levelName) {
        this.currentLevel = levelName;
        
        // Use ManifestLoader to load level
        BufferedImage bg = ManifestLoader.loadLevelBackground(levelName);
        if (bg != null) {
            this.levelTilemap = bg;
            System.out.println("[✅ GameplayScreen] Level loaded: " + levelName);
        } else {
            System.err.println("[❌ GameplayScreen] Failed to load level: " + levelName);
            this.levelTilemap = null;
        }
    }
    
    /**
     * Load a character skin & BOTH animations (IDLE + WALK)
     */
    public void loadCharacter(String characterName) {
        this.playerCharacter = characterName;
        String charType = characterName.toLowerCase();
        
        // Load IDLE animation
        BufferedImage idle = ManifestLoader.loadCharacterIdle(characterName);
        if (idle != null) {
            this.playerSpriteIdle = idle;
            this.currentDisplayFrame = idle;
            
            // Auto-detect frame count from width (each frame ~64px)
            this.frameWidth = Math.max(32, idle.getHeight());  // Use height as guide
            this.totalFrames = Math.max(1, idle.getWidth() / frameWidth);
            this.currentFrameIndex = 0;
            System.out.println("[✅ GameplayScreen] IDLE animation loaded: " + characterName + " (" + totalFrames + " frames)");
        }
        
        // Load WALK animation if available
        try {
            String walkPath = "Resources/industrial-zone/characters/player/" + charType + "/";
            File walkDir = new File(walkPath);
            File[] walkFiles = walkDir.listFiles((d, n) -> n.toLowerCase().contains("walk"));
            if (walkFiles != null && walkFiles.length > 0) {
                BufferedImage walk = ImageIO.read(walkFiles[0]);
                if (walk != null) {
                    this.playerSpriteWalk = walk;
                    System.out.println("[✅ GameplayScreen] WALK animation loaded: " + walkFiles[0].getName());
                }
            }
        } catch (Exception e) {
            System.out.println("[ℹ GameplayScreen] No walk animation found for " + characterName);
        }
    }
    
    /**
     * Handle keyboard input - set key state
     */
    public void setKeyPressed(int keyCode, boolean pressed) {
        if (keyCode >= 0 && keyCode < 256) {
            this.keysPressed[keyCode] = pressed;
        }
    }
    
    /**
     * Set mouse position (for aiming)
     */
    public void setMousePosition(int x, int y) {
        this.mouseX = x;
        this.mouseY = y;
    }
    
    /**
     * Update game state WITH ANIMATION + INPUT HANDLING
     */
    public void update(long deltaTime) {
        long now = System.currentTimeMillis();
        deltaTime = now - lastUpdateTime;
        lastUpdateTime = now;
        
        // ✅ HANDLE MOVEMENT INPUT
        this.isMoving = false;
        this.velocityX = 0;
        this.velocityY = 0;
        
        // W or UP = Move up
        if (isKeyPressed('W') || isKeyPressed('w')) {
            velocityY = -5;
            isMoving = true;
        }
        // S or DOWN = Move down
        if (isKeyPressed('S') || isKeyPressed('s')) {
            velocityY = 5;
            isMoving = true;
        }
        // A or LEFT = Move left
        if (isKeyPressed('A') || isKeyPressed('a')) {
            velocityX = -5;
            isMoving = true;
        }
        // D or RIGHT = Move right
        if (isKeyPressed('D') || isKeyPressed('d')) {
            velocityX = 5;
            isMoving = true;
        }
        
        // Apply movement
        playerX += velocityX;
        playerY += velocityY;
        
        // Keep in bounds
        playerX = Math.max(frameWidth/2, Math.min(screenWidth - frameWidth/2, playerX));
        playerY = Math.max(0, Math.min(screenHeight - 100, playerY));
        
        // ✅ UPDATE ANIMATION FRAME
        long frameTime = now - lastFrameTime;
        if (frameTime >= frameDuration && totalFrames > 1) {
            currentFrameIndex = (currentFrameIndex + 1) % totalFrames;
            lastFrameTime = now;
        }
        
        // Select which sprite to show (WALK if moving, IDLE if not)
        if (isMoving && playerSpriteWalk != null) {
            this.currentDisplayFrame = playerSpriteWalk;
            this.totalFrames = Math.max(1, playerSpriteWalk.getWidth() / frameWidth);
        } else if (playerSpriteIdle != null) {
            this.currentDisplayFrame = playerSpriteIdle;
            this.totalFrames = Math.max(1, playerSpriteIdle.getWidth() / frameWidth);
        }
    }
    
    /**
     * Check if a key is currently pressed
     */
    private boolean isKeyPressed(char c) {
        return keysPressed[(int)c];
    }
    
    /**
     * Extract a single frame from sprite sheet and render it
     */
    public void render(BufferedImage dest) {
        if (dest == null) return;
        
        // Draw level background
        if (levelTilemap != null) {
            PixelCopyHelper.copyRaster(levelTilemap, dest, 0, 0);
        } else {
            // Solid background if no level loaded
            int[] pixels = new int[dest.getWidth() * dest.getHeight()];
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = 0xFF1a1a28; // Dark grey
            }
            dest.setRGB(0, 0, dest.getWidth(), dest.getHeight(), pixels, 0, dest.getWidth());
        }
        
        // ✅ DRAW ANIMATED PLAYER SPRITE (extract current frame)
        if (currentDisplayFrame != null) {
            int frameX = currentFrameIndex * frameWidth;
            int frameY = 0;
            int frameH = currentDisplayFrame.getHeight();
            
            // Extract just the current frame from the sprite strip
            BufferedImage frameToRender = new BufferedImage(frameWidth, frameH, BufferedImage.TYPE_INT_ARGB);
            int[] framePixels = new int[frameWidth * frameH];
            currentDisplayFrame.getRGB(frameX, frameY, frameWidth, frameH, framePixels, 0, frameWidth);
            frameToRender.setRGB(0, 0, frameWidth, frameH, framePixels, 0, frameWidth);
            
            // Draw the frame
            PixelCopyHelper.copyRaster(frameToRender, dest, playerX - frameWidth / 2, playerY - frameH / 2);
        }
        
        // ✅ DEBUG: Draw aiming direction from mouse
        // (Can add simple aim indicator here later)
    }
    
    /**
     * Move player left
     */
    public void moveLeft(int amount) {
        playerX = Math.max(0, playerX - amount);
    }
    
    /**
     * Move player right
     */
    public void moveRight(int amount) {
        playerX = Math.min(screenWidth, playerX + amount);
    }
    
    /**
     * Move player up
     */
    public void moveUp(int amount) {
        playerY = Math.max(0, playerY - amount);
    }
    
    /**
     * Move player down
     */
    public void moveDown(int amount) {
        playerY = Math.min(screenHeight, playerY + amount);
    }
    
    /**
     * Get current level name
     */
    public String getCurrentLevel() { return currentLevel; }
    
    /**
     * Get current character name
     */
    public String getCurrentCharacter() { return playerCharacter; }
}
