package levels;

import animation.AnimationAndSpriteLoader;

/**
 * ════════════════════════════════════════════════════════════════════════════════════════
 * LEVEL 1 - PLAYER AND LEVEL MANAGEMENT
 * ════════════════════════════════════════════════════════════════════════════════════════
 * 
 * Manages:
 * - Player character (PlayerController) - renders actual sprite assets
 * - Physics body for player  
 * - Input handling
 * - Level map rendering
 * 
 * PLAYER SPRITE SYSTEM:
 * - Loads real PNG assets from: Resources/industrial-zone/characters/biker/
 * - Supports 20+ animations (IDLE, WALK, RUN, JUMP, ATTACK, etc)
 * - Automatic frame extraction from horizontal sprite sheets
 * - Frame timing from sprite filename (e.g., "150ms" per frame)
 * 
 * @author 3359098
 * @version 1.0 - Player sprite rendering integration
 * @since April 6, 2026
 */
public class Level1 {
    
    private static final String TAG = "[Level1]";
    
    // Player and physics - stored as Objects to avoid nested class resolution issues
    private Object player;  // AnimationAndSpriteLoader.PlayerController
    private Object playerPhysics;  // PhysicsBody
    private Object inputHandler;  // InputHandler
    
    // Level map
    private LevelMapLoader mapLoader;
    
    // Player spawn position
    private static final float PLAYER_SPAWN_X = 100.0f;
    private static final float PLAYER_SPAWN_Y = 300.0f;
    
    /**
     * Constructor - Initialize Level 1
     */
    public Level1() {
        System.out.println(TAG + " Initializing Level 1...");
        
        try {
            // Create player controller using reflection to access nested classes
            createPlayerViaReflection();
            
            if (player != null) {
                System.out.println(TAG + " ✓ Created PlayerController with sprite assets");
            } else {
                System.out.println(TAG + " ⚠ PlayerController creation returned null!");
            }
            
            // Load level map
            mapLoader = new LevelMapLoader(1);
            System.out.println(TAG + " ✓ Loaded level map");
            
            System.out.println(TAG + " ✓ Level 1 initialized successfully");
        } catch (Exception e) {
            System.err.println(TAG + " ERROR: Failed to initialize level: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Create player controller via reflection to access AnimationAndSpriteLoader's nested classes
     */
    private void createPlayerViaReflection() throws Exception {
        try {
            // Get nested classes as inner classes using $ notation
            Class<?> inputHandlerClass = Class.forName("animation.AnimationAndSpriteLoader$InputHandler");
            Class<?> physicsBodyClass = Class.forName("animation.AnimationAndSpriteLoader$PhysicsBody");
            Class<?> playerControllerClass = Class.forName("animation.AnimationAndSpriteLoader$PlayerController");
            
            // Create InputHandler
            inputHandler = inputHandlerClass.getDeclaredConstructor().newInstance();
            
            // Create PhysicsBody with position parameters
            java.lang.reflect.Constructor<?> physicsConstructor = physicsBodyClass.getDeclaredConstructor(float.class, float.class);
            physicsConstructor.setAccessible(true);
            playerPhysics = physicsConstructor.newInstance(PLAYER_SPAWN_X, PLAYER_SPAWN_Y);
            
            // Create PlayerController
            java.lang.reflect.Constructor<?> playerConstructor = playerControllerClass.getDeclaredConstructor(physicsBodyClass, inputHandlerClass);
            playerConstructor.setAccessible(true);
            player = playerConstructor.newInstance(playerPhysics, inputHandler);
            
            System.out.println(TAG + " ✓ Created player via reflection");
        } catch (ClassNotFoundException e) {
            throw new Exception("Failed to find required classes: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception("Failed to instantiate player via reflection: " + e.getMessage(), e);
        }
    }
    
    /**
     * Update level logic each frame
     */
    public void update(float deltaTime) {
        if (player != null) {
            try {
                java.lang.reflect.Method updateMethod = player.getClass().getMethod("update", float.class);
                updateMethod.invoke(player, deltaTime);
            } catch (Exception e) {
                System.err.println(TAG + " Error updating player: " + e.getMessage());
            }
        }
    }
    
    /**
     * Render level (tilemap) to screen
     * NOTE: Player is rendered by RenderingSystem via GameRenderData.player
     */
    public void renderMap(java.awt.image.BufferedImage dest, int offsetX, int offsetY) {
        if (mapLoader != null) {
            mapLoader.render(dest, offsetX, offsetY);
        }
    }
    
    /**
     * Get the player controller for rendering
     * This is passed to GameRenderData.player for sprite rendering
     */
    public Object getPlayer() {
        return player;
    }
    
    /**
     * Get player position X
     */
    public float getPlayerX() {
        try {
            java.lang.reflect.Method getXMethod = player.getClass().getMethod("getX");
            return ((Number) getXMethod.invoke(player)).floatValue();
        } catch (Exception e) {
            return PLAYER_SPAWN_X;
        }
    }
    
    /**
     * Get player position Y
     */
    public float getPlayerY() {
        try {
            java.lang.reflect.Method getYMethod = player.getClass().getMethod("getY");
            return ((Number) getYMethod.invoke(player)).floatValue();
        } catch (Exception e) {
            return PLAYER_SPAWN_Y;
        }
    }
    
    /**
     * Handle key press from Game
     */
    public void handleKeyPress(int keyCode) {
        if (player != null) {
            try {
                // Try to get input handler from player and call onKeyPressed
                java.lang.reflect.Method getInputMethod = player.getClass().getMethod("getInputHandler");
                Object inputHandler = getInputMethod.invoke(player);
                if (inputHandler != null) {
                    java.lang.reflect.Method keyPressedMethod = inputHandler.getClass().getMethod("onKeyPressed", int.class);
                    keyPressedMethod.invoke(inputHandler, keyCode);
                }
            } catch (Exception e) {
                // Silently ignore - not all player types may have this method
            }
        }
    }
    
    /**
     * Handle key release from Game
     */
    public void handleKeyRelease(int keyCode) {
        if (player != null) {
            try {
                // Try to get input handler from player and call onKeyReleased
                java.lang.reflect.Method getInputMethod = player.getClass().getMethod("getInputHandler");
                Object inputHandler = getInputMethod.invoke(player);
                if (inputHandler != null) {
                    java.lang.reflect.Method keyReleasedMethod = inputHandler.getClass().getMethod("onKeyReleased", int.class);
                    keyReleasedMethod.invoke(inputHandler, keyCode);
                }
            } catch (Exception e) {
                // Silently ignore - not all player types may have this method
            }
        }
    }
}


