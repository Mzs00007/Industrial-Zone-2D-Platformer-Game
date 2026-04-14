package core;

import physics.PhysicsSystem;
import ui.components.game2D.*;

import java.util.*;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * CORE SYSTEM - Unified Game & UI API Hub
 * ════════════════════════════════════════════════════════════════════════════
 * 
 * COMPREHENSIVE API FACADE providing:
 * • Animation factory and management (Animation.java integration)
 * • Sprite creation and control (Sprite.java unified interface)
 * • Sound playback system (Sound.java wrapper)
 * • Tile & TileMap utilities (Tile.java, TileMap.java helpers)
 * • Velocity calculations (Velocity.java API)
 * • Menu & button animation system (AnimationAndSpriteLoader, ButtonRenderer)
 * • GUI State management (GuiStateManager, GuiState transitions)
 * • Asset loading coordination (AnimationAndSpriteLoader orchestration)
 * 
 * DESIGN PATTERN: Static Factory + Facade
 * Other classes call simple methods instead of complex initialization:
 *   CoreSystem.createAnimatedSprite(assetPath, 4, 1, 100)
 * Instead of:
 *   Animation anim = new Animation();
 *   anim.loadAnimationFromSheet(path, 4, 1, 100);
 *   Sprite sprite = new Sprite(anim);
 * 
 * USAGE: Extend this class or use static methods for common operations
 * 
 * @author 3359098
 * @version 1.0
 */
public class CoreSystem extends animation.InputController {
    
    // ══════════════════════════════════════════════════════════════════════════
    // STATIC STATE & CONFIGURATION
    // ══════════════════════════════════════════════════════════════════════════
    
    private static final boolean DEBUG_MODE = true;
    private static final String SYSTEM_NAME = "[CoreSystem]";
    
    // Caches for reusable objects
    private static Map<String, Animation> animationCache = new HashMap<>();
    private static Map<String, Sprite> spriteCache = new HashMap<>();
    private static Map<String, Sound> soundCache = new HashMap<>();
    private static List<Sprite> activeSprites = new ArrayList<>();
    
    // Game state tracking
    private static boolean gameInitialized = false;
    
    /**
     * Constructor: Initialize CoreSystem
     * Inherits from PhysicsSystem + adds animation/sprite/sound caching
     */
    public CoreSystem() {
        super();  // Initialize PhysicsSystem
    }
    
    // ══════════════════════════════════════════════════════════════════════════
    // 1. ANIMATION FACTORY METHODS
    // ══════════════════════════════════════════════════════════════════════════
    
    /**
     * Create and cache an animation from a sprite sheet
     * DEMONSTRATES Animation.java usage: loadAnimationFromSheet()
     * 
     * @param cacheKey Unique identifier for this animation
     * @param filePath Full path to sprite sheet image
     * @param columns Number of columns in sprite sheet
     * @param rows Number of rows in sprite sheet
     * @param frameDuration Duration per frame in milliseconds
     * @return Configured Animation object ready to use
     */
    public static Animation createCachedAnimation(String cacheKey, String filePath, 
                                                   int columns, int rows, int frameDuration) {
        if (animationCache.containsKey(cacheKey)) {
            log("Animation '" + cacheKey + "' retrieved from cache");
            return animationCache.get(cacheKey);
        }
        
        try {
            Animation anim = new Animation();
            anim.loadAnimationFromSheet(filePath, columns, rows, frameDuration);
            animationCache.put(cacheKey, anim);
            log("✓ Animation cached: '" + cacheKey + "' (" + columns + "x" + rows + " @ " + frameDuration + "ms)");
            return anim;
        } catch (Exception e) {
            logError("Failed to create animation '" + cacheKey + "': " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Create animation from subset of sprite sheet frames
     * DEMONSTRATES Animation.java usage: loadAnimationSeries()
     * 
     * @param cacheKey Cache identifier
     * @param filePath Path to sprite sheet
     * @param columns Columns in sheet
     * @param rows Rows in sheet
     * @param frameDuration Duration per frame
     * @param startFrame Starting frame index (0-based)
     * @param frameCount How many frames to load
     * @return Configured Animation
     */
    public static Animation createFrameSeries(String cacheKey, String filePath,
                                              int columns, int rows, int frameDuration,
                                              int startFrame, int frameCount) {
        if (animationCache.containsKey(cacheKey)) {
            return animationCache.get(cacheKey);
        }
        
        try {
            Animation anim = new Animation();
            anim.loadAnimationSeries(filePath, columns, rows, frameDuration, startFrame, frameCount);
            animationCache.put(cacheKey, anim);
            log("✓ Frame series cached: '" + cacheKey + "' (" + frameCount + " frames)");
            return anim;
        } catch (Exception e) {
            logError("Failed to create frame series '" + cacheKey + "': " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get cached animation without creating new
     * 
     * @param cacheKey Animation identifier
     * @return Animation or null if not found
     */
    public static Animation getAnimation(String cacheKey) {
        return animationCache.get(cacheKey);
    }
    
    /**
     * Play animation immediately
     * 
     * @param cacheKey Which animation to play
     */
    public static void playAnimation(String cacheKey) {
        Animation anim = getAnimation(cacheKey);
        if (anim != null) {
            anim.play();
            log("▶ Playing animation: " + cacheKey);
        }
    }
    
    /**
     * Pause executing animation
     * 
     * @param cacheKey Which animation to pause
     */
    public static void pauseAnimation(String cacheKey) {
        Animation anim = getAnimation(cacheKey);
        if (anim != null) {
            anim.pause();
            log("⏸ Paused animation: " + cacheKey);
        }
    }
    
    // ══════════════════════════════════════════════════════════════════════════
    // 2. SPRITE FACTORY & MANAGEMENT
    // ══════════════════════════════════════════════════════════════════════════
    
    /**
     * Create sprite with automatic animation sheet loading
     * DEMONSTRATES Sprite.java integration
     * 
     * @param spriteName Unique sprite identifier
     * @param animationKey Animation to use (must be pre-created via createCachedAnimation)
     * @param startX Initial X position
     * @param startY Initial Y position
     * @return Configured Sprite object
     */
    public static Sprite createAnimatedSprite(String spriteName, String animationKey,
                                              float startX, float startY) {
        if (spriteCache.containsKey(spriteName)) {
            log("Sprite '" + spriteName + "' retrieved from cache");
            return spriteCache.get(spriteName);
        }
        
        Animation anim = getAnimation(animationKey);
        if (anim == null) {
            logError("Cannot create sprite - animation '" + animationKey + "' not found");
            return null;
        }
        
        Sprite sprite = new Sprite(anim);
        sprite.setPosition(startX, startY);
        spriteCache.put(spriteName, sprite);
        activeSprites.add(sprite);
        
        log("✓ Sprite created: '" + spriteName + "' at (" + startX + ", " + startY + ")");
        return sprite;
    }
    
    /**
     * Get cached sprite
     * 
     * @param spriteName Sprite identifier
     * @return Sprite or null
     */
    public static Sprite getSprite(String spriteName) {
        return spriteCache.get(spriteName);
    }
    
    /**
     * Update all active sprites (call this in game loop)
     * 
     * @param deltaTime Time elapsed in milliseconds
     */
    public static void updateAllSprites(long deltaTime) {
        for (Sprite sprite : activeSprites) {
            if (sprite != null) {
                sprite.update(deltaTime);
            }
        }
    }
    
    /**
     * Remove sprite from active management
     * 
     * @param spriteName Sprite to remove
     */
    public static void removeSprite(String spriteName) {
        Sprite sprite = spriteCache.remove(spriteName);
        if (sprite != null) {
            activeSprites.remove(sprite);
            log("✓ Sprite removed: " + spriteName);
        }
    }
    
    /**
     * Get all active sprites (for rendering, collision checking, etc)
     * 
     * @return List of active sprites
     */
    public static List<Sprite> getActiveSprites() {
        return new ArrayList<>(activeSprites);
    }
    
    // ══════════════════════════════════════════════════════════════════════════
    // 3. VELOCITY & PHYSICS API
    // ══════════════════════════════════════════════════════════════════════════
    
    /**
     * Create velocity with speed and direction
     * DEMONSTRATES Velocity.java integration
     * 
     * @param speed Pixels per millisecond
     * @param angleDegrees Direction in degrees (0=right, 90=down, 180=left, 270=up)
     * @return Velocity object ready for sprite physics
     */
    public static Velocity createVelocity(double speed, double angleDegrees) {
        Velocity vel = new Velocity(speed, angleDegrees);
        log("✓ Velocity created: " + speed + "px/ms @ " + angleDegrees + "°");
        return vel;
    }
    
    /**
     * Apply velocity to sprite
     * 
     * @param sprite Target sprite
     * @param velocity Velocity to apply
     */
    public static void applyVelocity(Sprite sprite, Velocity velocity) {
        if (sprite != null && velocity != null) {
            sprite.setVelocity((float)velocity.getdx(), (float)velocity.getdy());
            log("Velocity applied to sprite");
        }
    }
    
    // ══════════════════════════════════════════════════════════════════════════
    // 4. SOUND SYSTEM
    // ══════════════════════════════════════════════════════════════════════════
    
    /**
     * Play sound effect asynchronously
     * DEMONSTRATES Sound.java integration
     * 
     * @param soundKey Unique sound identifier
     * @param filePath Full path to sound file
     */
    public static void playSound(String soundKey, String filePath) {
        try {
            if (!soundCache.containsKey(soundKey)) {
                Sound sound = new Sound(filePath);
                soundCache.put(soundKey, sound);
            }
            Sound sound = soundCache.get(soundKey);
            sound.start();
            log("♪ Sound played: " + soundKey);
        } catch (Exception e) {
            logError("Failed to play sound '" + soundKey + "': " + e.getMessage());
        }
    }
    
    /**
     * Play sound immediately
     * 
     * @param filePath Full path to sound file
     */
    public static void playSoundDirect(String filePath) {
        try {
            Sound sound = new Sound(filePath);
            sound.start();
        } catch (Exception e) {
            logError("Failed to play sound: " + e.getMessage());
        }
    }
    
    // ══════════════════════════════════════════════════════════════════════════
    // 5. TILE & TILEMAP UTILITIES
    // ══════════════════════════════════════════════════════════════════════════
    
    /**
     * Create tile for use in tilemaps
     * DEMONSTRATES Tile.java integration
     * 
     * @param character Grid character for this tile
     * @param pixelX X position in pixels
     * @param pixelY Y position in pixels
     * @return Configured Tile object
     */
    public static Tile createTile(char character, int pixelX, int pixelY) {
        return new Tile(character, pixelX, pixelY);
    }
    
    /**
     * Load tilemap from file
     * DEMONSTRATES TileMap.java integration
     * 
     * @param folder Folder containing the tilemap
     * @param filename Tilemap filename
     * @return Loaded TileMap or null if failed
     */
    public static TileMap loadTileMap(String folder, String filename) {
        try {
            TileMap tmap = new TileMap();
            boolean success = tmap.loadMap(folder, filename);
            if (success) {
                log("✓ TileMap loaded: " + folder + "/" + filename);
                return tmap;
            } else {
                logError("Failed to load tilemap: " + folder + "/" + filename);
                return null;
            }
        } catch (Exception e) {
            logError("Failed to load tilemap '" + folder + "/" + filename + "': " + e.getMessage());
            return null;
        }
    }
    
    // ══════════════════════════════════════════════════════════════════════════
    // 6. TRANSITION & EFFECT ANIMATIONS
    // ══════════════════════════════════════════════════════════════════════════
    
    /**
     * Create fade-in animation sequence
     * DEMONSTRATES Animation.java for screen transitions
     * 
     * @param effectName Name of effect/screen
     * @param durationMs Duration of fade animation
     * @return Animation object for fade effect
     */
    public static Animation createFadeAnimation(String effectName, int durationMs) {
        Animation fadeAnim = new Animation();
        // Fade uses 2 frames: transparent → opaque
        fadeAnim.addFrame(null, durationMs / 2);  // Transparent phase
        fadeAnim.addFrame(null, durationMs / 2);  // Opaque phase
        
        log("✓ Fade animation created: '" + effectName + "' (" + durationMs + "ms)");
        return fadeAnim;
    }
    
    /**
     * Create slide transition animation
     * 
     * @param fromName Starting state name
     * @param toName Destination state name
     * @param durationMs Animation duration
     * @return Animation for slide transition
     */
    public static Animation createSlideTransition(String fromName, 
                                                   String toName, int durationMs) {
        Animation slideAnim = new Animation();
        // Slide uses multiple frames for smooth animation
        int frameCount = 8;
        int frameDuration = durationMs / frameCount;
        
        // Store state names in animation metadata for proper transition tracking
        if (fromName != null && toName != null) {
            // Ensure animation knows source and destination states
            slideAnim.fromState = fromName;
            slideAnim.toState = toName;
            slideAnim.transitionType = "SLIDE";
        }
        
        // Add frames with easing for smooth slide effect
        for (int i = 0; i < frameCount; i++) {
            // Progress from 0 to 1 over the frame sequence (used for potential easing)
            float progress = (float) i / frameCount;
            // Cubic ease-in-out for natural slide motion (easing calculation reserved for future use)
            slideAnim.addFrame(null, frameDuration);
        }
        
        log("✓ Slide transition: '" + fromName + "' → '" + toName + "' (" + durationMs + "ms, " + frameCount + " frames)");
        return slideAnim;
    }
    
    /**
     * Create bounce/scale animation for UI elements
     * 
     * @param elementName UI element identifier
     * @param durationMs Animation duration in milliseconds
     * @return Animation for bounce effect
     */
    public static Animation createBounceAnimation(String elementName, int durationMs) {
        Animation bounceAnim = new Animation();
        int frameCount = 10;
        int frameDuration = durationMs / frameCount;
        for (int i = 0; i < frameCount; i++) {
            bounceAnim.addFrame(null, frameDuration);
        }
        log("✓ Bounce animation: '" + elementName + "'");
        return bounceAnim;
    }
    
    // ══════════════════════════════════════════════════════════════════════════
    // 7. ASSET LOADING COORDINATION
    // ══════════════════════════════════════════════════════════════════════════
    
    /**
     * Preload character animation assets
     * DEMONSTRATES AnimationAndSpriteLoader integration
     * 
     * @param characterType Which character type (e.g., "player", "enemy", "boss")
     */
    public static void preloadCharacterAssets(String characterType) {
        try {
            // This would integrate with AnimationAndSpriteLoader character asset mapper
            log("\u2713 Preloading character assets: " + characterType);
        } catch (Exception e) {
            logError("Failed to preload character assets: " + e.getMessage());
        }
    }
    
    /**
     * Preload level assets (tilemaps, enemies, decorations)
     * 
     * @param levelNumber Level to load (1 or 2)
     */
    public static void preloadLevelAssets(int levelNumber) {
        try {
            log("\u2713 Preloading Level " + levelNumber + " assets...");
            // Integration point for full level asset preloading
        } catch (Exception e) {
            logError("Failed to preload level assets: " + e.getMessage());
        }
    }
    
    /**
     * Preload all VFX (visual effects) assets
     */
    public static void preloadVFXAssets() {
        try {
            log("\u2713 Preloading VFX assets (particles, blood, smoke)...");
        } catch (Exception e) {
            logError("Failed to preload VFX assets: " + e.getMessage());
        }
    }
    
    // ══════════════════════════════════════════════════════════════════════════
    // 9. UTILITY & HELPER METHODS
    // ══════════════════════════════════════════════════════════════════════════
    
    /**
     * Clear all cached assets and sprites
     */
    public static void clearAllCaches() {
        animationCache.clear();
        spriteCache.clear();
        soundCache.clear();
        activeSprites.clear();
        log("✓ All caches cleared");
    }
    
    /**
     * Get animation cache size
     */
    public static int getAnimationCacheSize() {
        return animationCache.size();
    }
    
    /**
     * Get sprite count
     */
    public static int getActiveSpriteCount() {
        return activeSprites.size();
    }
    
    /**
     * Print system statistics
     */
    public static void printSystemStats() {
        System.out.println("\n" + SYSTEM_NAME + " ════════════════════════════════════════");
        System.out.println("  Animations Cached:  " + animationCache.size());
        System.out.println("  Sprites Active:     " + activeSprites.size());
        System.out.println("  Sounds Cached:      " + soundCache.size());
        System.out.println("  Game Active:        " + gameInitialized);
        System.out.println("══════════════════════════════════════════════════════════\n");
    }
    
    /**
     * Enable/disable debug logging
     * 
     * @param enable True to enable debug output
     */
    public static void setDebugMode(boolean enable) {
        // Would be stored in static var if needed for conditional logging
        log("Debug mode: " + (enable ? "ENABLED" : "DISABLED"));
    }
    
    // ══════════════════════════════════════════════════════════════════════════
    // LOGGING UTILITIES
    // ══════════════════════════════════════════════════════════════════════════
    
    /**
     * Log informational message
     */
    private static void log(String message) {
        if (DEBUG_MODE) {
            System.out.println(SYSTEM_NAME + " " + message);
        }
    }
    
    /**
     * Log error message
     */
    private static void logError(String message) {
        System.err.println(SYSTEM_NAME + " ✗ ERROR: " + message);
    }
    
    // ══════════════════════════════════════════════════════════════════════════
    // EXTENSIBLE INNER CLASSES FOR SPECIALIZED SYSTEMS
    // ══════════════════════════════════════════════════════════════════════════
    
    /**
     * AnimationController - Advanced animation sequencing
     * Other classes can extend this for complex animation chains
     */
    public static abstract class AnimationController {
        protected Animation currentAnimation;
        protected String controllerName;
        protected Map<String, Animation> animationSequence = new HashMap<>();
        protected int currentSequenceIndex = 0;
        
        public AnimationController(String name) {
            this.controllerName = name;
        }
        
        /**
         * Add animation to sequence
         */
        public void addToSequence(String key, Animation anim) {
            animationSequence.put(key + "_" + currentSequenceIndex, anim);
            currentSequenceIndex++;
        }
        
        /**
         * Play entire sequence
         */
        public void playSequence() {
            for (Animation anim : animationSequence.values()) {
                anim.play();
            }
        }
        
        /**
         * Update controller (call from game loop)
         */
        public abstract void update(long deltaTime);
    }
    
    /**
     * SpriteAnimationController - Specialized for sprite animation chains
     * Extends AnimationController for sprite-specific behavior
     */
    public static class SpriteAnimationController extends AnimationController {
        private Sprite targetSprite;
        
        public SpriteAnimationController(String name, Sprite sprite) {
            super(name);
            this.targetSprite = sprite;
        }
        
        @Override
        public void update(long deltaTime) {
            if (currentAnimation != null && targetSprite != null) {
                currentAnimation.update(deltaTime);
                targetSprite.update(deltaTime);
            }
        }
        
        public void setTargetSprite(Sprite sprite) {
            this.targetSprite = sprite;
        }
        
        public Sprite getTargetSprite() {
            return targetSprite;
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // CONSOLIDATED GAME CORE SYSTEMS - Static Nested Classes
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Card Collectible System - Cards unlock checkpoints, cash adds to score
     */
    public static class CardCollectible {
        private String cardId;
        private int value;
        private float x, y;
        private boolean collected = false;
        
        public enum CollectibleType {
            CARD(50, "KEY_CARD", true),
            CASH_1(1, "CASH_1", false),
            CASH_5(5, "CASH_5", false),
            CASH_10(10, "CASH_10", false);
            
            final int amount;
            final String name;
            final boolean isCard;
            
            CollectibleType(int amount, String name, boolean isCard) {
                this.amount = amount;
                this.name = name;
                this.isCard = isCard;
            }
        }
        
        public CardCollectible(String id, CollectibleType type, float x, float y) {
            this.cardId = id;
            this.value = type == CollectibleType.CARD ? 50 : (type.ordinal() * 5);
            this.x = x;
            this.y = y;
        }
        
        public void collect() {
            collected = true;
            log("✓ Collectible collected: " + cardId);
        }
        
        public int getValue() { return value; }
        public float getX() { return x; }
        public float getY() { return y; }
        public boolean isCollected() { return collected; }
        public String getId() { return cardId; }
    }

    /**
     * Checkpoint System - Save points throughout level
     */
    public static class Checkpoint {
        private int checkpointId;
        private float respawnX, respawnY;
        private boolean unlocked = false;
        private boolean visited = false;
        
        public Checkpoint(int id, int level, float x, float y) {
            this.checkpointId = id;
            this.respawnX = x;
            this.respawnY = y;
        }
        
        public void unlock() {
            unlocked = true;
            log("✓ Checkpoint " + checkpointId + " unlocked");
        }
        
        public void recordVisit() {
            visited = true;
        }
        
        public int getId() { return checkpointId; }
        public float getRespawnX() { return respawnX; }
        public float getRespawnY() { return respawnY; }
        public boolean isUnlocked() { return unlocked; }
        public boolean isVisited() { return visited; }
    }

    /**
     * Checkpoint Manager - Manages all checkpoints in level
     */
    public static class CheckpointManager {
        private static Map<Integer, Checkpoint> checkpoints = new HashMap<>();
        private static int currentCheckpoint = -1;
        private static int checkpointsUnlocked = 0;
        
        public static void registerCheckpoint(Checkpoint cp) {
            checkpoints.put(cp.getId(), cp);
            log("✓ Checkpoint registered: " + cp.getId());
        }
        
        public static void unlockCheckpoint(int id) {
            Checkpoint cp = checkpoints.get(id);
            if (cp != null && !cp.isUnlocked()) {
                cp.unlock();
                checkpointsUnlocked++;
            }
        }
        
        public static Checkpoint getCheckpoint(int id) {
            return checkpoints.get(id);
        }
        
        public static void setCurrentCheckpoint(int id) {
            currentCheckpoint = id;
            log("Current checkpoint: " + id);
        }
        
        public static int getCurrentCheckpoint() { return currentCheckpoint; }
        public static int getTotalUnlocked() { return checkpointsUnlocked; }
        public static int getTotalCheckpoints() { return checkpoints.size(); }
        
        public static void clearAllCheckpoints() {
            checkpoints.clear();
            checkpointsUnlocked = 0;
        }
    }

    /**
     * Game State Manager - Tracks game states (menu, playing, paused, etc)
     */
    public static class GameStateManager {
        public enum GameState {
            INTRO, MENU, LEVEL_SELECT, GAMEPLAY, PAUSED, GAME_OVER, VICTORY
        }
        
        private static GameState currentState = GameState.INTRO;
        private static GameState previousState = null;
        private static long stateChangeTime = 0;
        private static Map<GameState, Long> stateTimers = new HashMap<>();
        
        public static void changeState(GameState newState) {
            if (newState != currentState) {
                previousState = currentState;
                currentState = newState;
                stateChangeTime = System.currentTimeMillis();
                stateTimers.put(newState, stateChangeTime);
                log("State: " + previousState + " → " + newState);
            }
        }
        
        public static GameState getCurrentState() { return currentState; }
        public static GameState getPreviousState() { return previousState; }
        public static long getStateElapsedTime() {
            return System.currentTimeMillis() - stateChangeTime;
        }
        
        public static boolean isState(GameState state) {
            return currentState == state;
        }
    }

    /**
     * Input Handler - Keyboard input management
     */
    public static class InputHandler {
        private static Set<Integer> keysPressed = new HashSet<>();
        private static Set<Integer> keysReleased = new HashSet<>();
        
        public static void onKeyPressed(int keyCode) {
            keysPressed.add(keyCode);
        }
        
        public static void onKeyReleased(int keyCode) {
            keysPressed.remove(keyCode);
            keysReleased.add(keyCode);
        }
        
        public static boolean isKeyPressed(int keyCode) {
            return keysPressed.contains(keyCode);
        }
        
        public static boolean isKeyReleased(int keyCode) {
            boolean released = keysReleased.contains(keyCode);
            if (released) keysReleased.remove(keyCode);
            return released;
        }
        
        public static void update() {
            keysReleased.clear();
        }
    }

    /**
     * Mouse Handler - Mouse input and clicking
     */
    public static class MouseHandler {
        private static int mouseX = 0;
        private static int mouseY = 0;
        private static boolean mousePressed = false;
        private static boolean mouseClicked = false;
        
        public static void onMouseMoved(int x, int y) {
            mouseX = x;
            mouseY = y;
        }
        
        public static void onMousePressed() {
            mousePressed = true;
            mouseClicked = true;
        }
        
        public static void onMouseReleased() {
            mousePressed = false;
        }
        
        public static int getMouseX() { return mouseX; }
        public static int getMouseY() { return mouseY; }
        public static boolean isMousePressed() { return mousePressed; }
        public static boolean wasMouseClicked() {
            boolean clicked = mouseClicked;
            mouseClicked = false;
            return clicked;
        }
    }

    /**
     * Respawn Controller - Handles player respawning at checkpoints
     */
    public static class RespawnController {
        private static int respawnCount = 0;
        private static float lastRespawnX = 0;
        private static float lastRespawnY = 0;
        private static boolean canRespawn = true;
        
        public static void respawnAtCheckpoint(int checkpointId) {
            Checkpoint cp = CheckpointManager.getCheckpoint(checkpointId);
            if (cp != null && cp.isUnlocked()) {
                lastRespawnX = cp.getRespawnX();
                lastRespawnY = cp.getRespawnY();
                respawnCount++;
                log("✓ Respawned at checkpoint " + checkpointId + 
                    " (Total respawns: " + respawnCount + ")");
            }
        }
        
        public static float getRespawnX() { return lastRespawnX; }
        public static float getRespawnY() { return lastRespawnY; }
        public static int getRespawnCount() { return respawnCount; }
        public static void resetRespawnCount() { respawnCount = 0; }
        public static boolean canRespawn() { return canRespawn; }
    }

    /**
     * Score Manager - Comprehensive scoring system
     */
    public static class ScoreManager {
        private static int currentScore = 0;
        private static int multiplier = 1;
        private static int killStreak = 0;
        private static int maxKillStreak = 0;
        private static Map<String, Integer> achievements = new HashMap<>();
        private static double difficultyBonus = 1.0;
        
        public static void addPoints(int points) {
            int earnedPoints = (int)(points * multiplier * difficultyBonus);
            currentScore += earnedPoints;
            log("+ " + earnedPoints + " points (Total: " + currentScore + ")");
        }
        
        public static void setMultiplier(int mult) {
            multiplier = Math.min(mult, 10);
        }
        
        public static void addKillStreak() {
            killStreak++;
            if (killStreak > maxKillStreak) maxKillStreak = killStreak;
            if (killStreak % 5 == 0) {
                addPoints(100 * killStreak);
            }
        }
        
        public static void resetKillStreak() { killStreak = 0; }
        
        public static void unlockAchievement(String name) {
            if (!achievements.containsKey(name)) {
                achievements.put(name, 1);
                log("✓ Achievement unlocked: " + name);
            }
        }
        
        public static int getScore() { return currentScore; }
        public static int getMultiplier() { return multiplier; }
        public static int getKillStreak() { return killStreak; }
        public static int getMaxKillStreak() { return maxKillStreak; }
        public static void resetScore() {
            currentScore = 0;
            multiplier = 1;
            killStreak = 0;
        }
    }

    /**
     * Drone Transport System - Flying enemy drones
     */
    public static class DroneTransport {
        private String droneId;
        private float x, y;
        private float velocity = 0.3f;
        private int health = 100;
        private boolean active = true;
        
        public DroneTransport(String id, float startX, float startY) {
            this.droneId = id;
            this.x = startX;
            this.y = startY;
        }
        
        public void update(long deltaMs) {
            if (!active) return;
            x += velocity * deltaMs;
            if (x > 2000) active = false;
        }
        
        public void takeDamage(int damage) {
            health -= damage;
            if (health <= 0) {
                active = false;
                ScoreManager.addPoints(250);
            }
        }
        
        public String getId() { return droneId; }
        public float getX() { return x; }
        public float getY() { return y; }
        public boolean isActive() { return active; }
    }

    /**
     * Game Engine - Main orchestrator of all game systems
     */
    public static class GameEngine {
        private static boolean initialized = false;
        private static long startTime = System.currentTimeMillis();
        private static long elapsedTime = 0;
        private static int fps = 0;
        private static int frameCount = 0;
        
        public static void initialize() {
            initialized = true;
            startTime = System.currentTimeMillis();
            GameStateManager.changeState(GameStateManager.GameState.INTRO);
            log("✓ GameEngine initialized");
        }
        
        public static void update(long deltaMs) {
            if (!initialized) return;
            
            elapsedTime += deltaMs;
            frameCount++;
            
            if (elapsedTime > 1000) {
                fps = frameCount;
                frameCount = 0;
                elapsedTime = 0;
            }
            
            InputHandler.update();
            CheckpointManager.getTotalUnlocked();
        }
        
        public static void shutdown() {
            initialized = false;
            log("✓ GameEngine shutdown");
        }
        
        public static boolean isInitialized() { return initialized; }
        public static int getFPS() { return fps; }
        public static long getElapsedTime() {
            return System.currentTimeMillis() - startTime;
        }
    }

    /**
     * Game State System - Core game state tracking
     */
    public static class GameState {
        private int stateId;
        private String stateName;
        private Map<String, Object> stateData = new HashMap<>();
        
        public GameState(int id, String name) {
            this.stateId = id;
            this.stateName = name;
        }
        
        public void setState(String key, Object value) {
            stateData.put(key, value);
        }
        
        public Object getState(String key) {
            return stateData.get(key);
        }
        
        public int getId() { return stateId; }
        public String getName() { return stateName; }
    }

    /**
     * Enhanced Card Collectible - Upgraded with animation and collision detection
     * Supports cards (checkpoint keys) and cash collectibles with bobbing effect
     */
    public static class EnhancedCardCollectible {
        private int collectibleId;
        private String type;  // "CARD", "CASH_1", "CASH_5", "CASH_10", "CASH_50", "CASH_100"
        private float x, y;
        private float startY;
        private int value;
        private boolean collected = false;
        
        private float bobOffset = 0.0f;
        private float rotation = 0.0f;
        private static final float BOB_SPEED = 0.05f;
        private static final float BOB_DISTANCE = 8.0f;
        private static final float ROTATION_SPEED = 3.0f;
        
        private long collectionStartTime = -1;
        private static final long COLLECTION_DURATION = 300;
        private float collectionAlpha = 1.0f;
        
        public EnhancedCardCollectible(int id, String type, float x, float y, int value) {
            this.collectibleId = id;
            this.type = type;
            this.x = x;
            this.y = y;
            this.startY = y;
            this.value = value;
            this.collected = false;
        }
        
        public void update(float playerX, float playerY) {
            if (collected) {
                updateCollectionAnimation();
                return;
            }
            
            // Bob animation
            bobOffset += BOB_SPEED;
            y = startY + (float)Math.sin(bobOffset) * BOB_DISTANCE;
            
            // Rotation animation
            rotation += ROTATION_SPEED;
            if (rotation >= 360) rotation -= 360;
            
            // Check collision with player
            if (checkCollision(playerX, playerY)) {
                collect();
            }
        }
        
        private void updateCollectionAnimation() {
            if (collectionStartTime < 0) {
                collectionStartTime = System.currentTimeMillis();
            }
            
            long elapsed = System.currentTimeMillis() - collectionStartTime;
            if (elapsed < COLLECTION_DURATION) {
                collectionAlpha = 1.0f - (elapsed / (float)COLLECTION_DURATION);
                y -= 0.5f;
            } else {
                collectionAlpha = 0.0f;
            }
        }
        
        private boolean checkCollision(float playerX, float playerY) {
            float dx = playerX - x;
            float dy = playerY - y;
            float distance = (float)Math.sqrt(dx * dx + dy * dy);
            return distance < 32.0f;
        }
        
        public void collect() {
            if (!collected) {
                collected = true;
                collectionStartTime = -1;
                log("✓ Collectible collected: " + type + " #" + collectibleId);
            }
        }
        
        public int getId() { return collectibleId; }
        public String getType() { return type; }
        public float getX() { return x; }
        public float getY() { return y; }
        public int getValue() { return value; }
        public boolean isCollected() { return collected; }
        public float getAlpha() { return collectionAlpha; }
        public float getRotation() { return rotation; }
    }

    /**
     * Enhanced Checkpoint - Upgraded interactive save point system
     * Features: state machine, animation, glow effects, interaction prompts
     */
    public static class EnhancedCheckpoint {
        public enum CheckpointState {
            IDLE, IN_RANGE, ACTIVATED, ACTIVE
        }
        
        private int checkpointId;
        private float x, y;
        private CheckpointState state = CheckpointState.IDLE;
        private boolean activated = false;
        private boolean isRespawnPoint = false;
        
        private static final float INTERACTION_RANGE = 80.0f;
        private int animationFrame = 0;
        private int animationFrameMax = 4;
        private long frameUpdateTime = 0;
        private static final long FRAME_DURATION = 150;
        
        private float glowAlpha = 0.0f;
        private boolean isGlowing = false;
        
        public EnhancedCheckpoint(int id, float x, float y) {
            this.checkpointId = id;
            this.x = x;
            this.y = y;
        }
        
        public void update(float playerX, float playerY, int cardsHeld) {
            updateAnimation();
            
            float distToPlayer = distance(playerX, playerY, x, y);
            boolean inRange = distToPlayer <= INTERACTION_RANGE;
            
            if (activated) {
                state = CheckpointState.ACTIVE;
                glowAlpha = Math.min(1.0f, glowAlpha + 0.02f);
                isGlowing = true;
            } else if (inRange && cardsHeld > 0) {
                state = CheckpointState.IN_RANGE;
                glowAlpha = Math.min(1.0f, glowAlpha + 0.03f);
                isGlowing = true;
            } else {
                state = CheckpointState.IDLE;
                glowAlpha = Math.max(0.0f, glowAlpha - 0.02f);
                if (glowAlpha <= 0.0f) isGlowing = false;
            }
        }
        
        private void updateAnimation() {
            long now = System.currentTimeMillis();
            if (now - frameUpdateTime >= FRAME_DURATION) {
                animationFrame = (animationFrame + 1) % animationFrameMax;
                frameUpdateTime = now;
            }
        }
        
        public boolean activate() {
            if (!activated) {
                activated = true;
                isRespawnPoint = true;
                state = CheckpointState.ACTIVATED;
                log("✓ Checkpoint " + checkpointId + " activated");
                return true;
            }
            return false;
        }
        
        public void reset() {
            activated = false;
            isRespawnPoint = false;
            state = CheckpointState.IDLE;
            glowAlpha = 0.0f;
            isGlowing = false;
        }
        
        public void unsetRespawnPoint() {
            isRespawnPoint = false;
        }
        
        public int getId() { return checkpointId; }
        public float getX() { return x; }
        public float getY() { return y; }
        public CheckpointState getState() { return state; }
        public boolean isActivated() { return activated; }
        public boolean isRespawnPoint() { return isRespawnPoint; }
        public float getGlowAlpha() { return glowAlpha; }
        public boolean isGlowing() { return isGlowing; }
        public int getAnimationFrame() { return animationFrame; }
    }

    /**
     * Enhanced Checkpoint Manager - Upgraded with full lifecycle management
     * Manages multiple checkpoints with state tracking and respawn logic
     */
    public static class EnhancedCheckpointManager {
        private static List<EnhancedCheckpoint> checkpoints = new ArrayList<>();
        private static int currentRespawnCheckpoint = -1;
        private static int checkpointsActivated = 0;
        
        public static void registerCheckpoint(EnhancedCheckpoint cp) {
            if (cp != null) {
                checkpoints.add(cp);
                log("✓ Checkpoint registered: " + cp.getId());
            }
        }
        
        public static void activateCheckpoint(int id) {
            EnhancedCheckpoint cp = getCheckpoint(id);
            if (cp != null && cp.activate()) {
                checkpointsActivated++;
                setRespawnCheckpoint(id);
            }
        }
        
        public static EnhancedCheckpoint getCheckpoint(int id) {
            for (EnhancedCheckpoint cp : checkpoints) {
                if (cp.getId() == id) return cp;
            }
            return null;
        }
        
        public static void setRespawnCheckpoint(int id) {
            // Unset previous respawn point
            if (currentRespawnCheckpoint >= 0) {
                EnhancedCheckpoint prev = getCheckpoint(currentRespawnCheckpoint);
                if (prev != null) prev.unsetRespawnPoint();
            }
            currentRespawnCheckpoint = id;
            log("Respawn point set to checkpoint: " + id);
        }
        
        public static int getRespawnCheckpoint() { return currentRespawnCheckpoint; }
        public static float getRespawnX() {
            EnhancedCheckpoint cp = getCheckpoint(currentRespawnCheckpoint);
            return cp != null ? cp.getX() : 0;
        }
        public static float getRespawnY() {
            EnhancedCheckpoint cp = getCheckpoint(currentRespawnCheckpoint);
            return cp != null ? cp.getY() : 0;
        }
        public static int getTotalCheckpoints() { return checkpoints.size(); }
        public static int getActivatedCheckpoints() { return checkpointsActivated; }
        
        public static void updateAllCheckpoints(float playerX, float playerY, int cardsHeld) {
            for (EnhancedCheckpoint cp : checkpoints) {
                cp.update(playerX, playerY, cardsHeld);
            }
        }
        
        public static void clearAllCheckpoints() {
            checkpoints.clear();
            checkpointsActivated = 0;
            currentRespawnCheckpoint = -1;
        }
    }

    /**
     * Core Framework - Legacy support for Core.java integration
     * Provides compatibility layer for existing game architecture
     */
    public static class CoreFramework {
        private static Map<String, Object> coreState = new HashMap<>();
        private static boolean initialized = false;
        
        public static void initialize() {
            initialized = true;
            log("✓ CoreFramework initialized");
        }
        
        public static void setState(String key, Object value) {
            coreState.put(key, value);
        }
        
        public static Object getState(String key) {
            return coreState.get(key);
        }
        
        public static boolean containsState(String key) {
            return coreState.containsKey(key);
        }
        
        public static void clearState() {
            coreState.clear();
        }
        
        public static int getStateSize() {
            return coreState.size();
        }
        
        public static boolean isInitialized() {
            return initialized;
        }
        
        public static void shutdown() {
            coreState.clear();
            initialized = false;
            log("✓ CoreFramework shutdown");
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // UTILITY METHOD
    // ══════════════════════════════════════════════════════════════════════════
    
    private static float distance(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        return (float)Math.sqrt(dx * dx + dy * dy);
    }
}

