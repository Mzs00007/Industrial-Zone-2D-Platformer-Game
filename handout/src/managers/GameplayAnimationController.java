package managers;
import game2D.*;

import managers.AssetManager_001_UnifiedLoader;
import managers.AssetMetadata;
import managers.AssetPathBridge;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PHASE 3: ANIMATION STATE MACHINE
 * 
 * Synchronizes animation frame playback with asset switching for all character states.
 * Serves as foundation for VFX, audio, and physics synchronization.
 * 
 * Integrates Phase 2 assets:
 * - AssetMetadata for frame counts and timing values
 * - AssetManager for sprite frame loading
 * - AssetPathBridge for organized path access
 * 
 * Timeline: 16ms per update cycle at 60fps
 * Precision: ±5ms tolerance on frame timing
 */
public class GameplayAnimationController {
    
    // ===== ANIMATION STATES (Character States) =====
    public enum CharacterState {
        IDLE(100),        // 100ms per frame - breathing/standing
        WALK(120),        // 120ms per frame - smooth movement
        RUN(80),          // 80ms per frame - sprint speed
        JUMP(40),         // 40ms per frame - in-air animation
        ATTACK(40),       // 40ms per frame - combat swing
        HURT(100),        // 100ms per frame - damage reaction
        DEATH(120);       // 120ms per frame - falling/collapse
        
        public final long frameDurationMs;
        CharacterState(long frameDurationMs) {
            this.frameDurationMs = frameDurationMs;
        }
    }
    
    // ===== ANIMATION PLAYBACK MODES =====
    public enum PlayMode {
        LOOP,             // Repeat when finished (WALK, IDLE, RUN)
        PLAY_ONCE,        // Execute once then stop (ATTACK, HURT, DEATH)
        PAUSE             // Freeze current frame (pause menu)
    }
    
    // ===== FRAME CALLBACK INTERFACE =====
    /**
     * Called when specific animation frames are reached.
     * Allows VFX/audio systems to synchronize with animation peaks.
     */
    public interface AnimationFrameCallback {
        void onFrameReached(int frameNumber, long absoluteTimeMs);
    }
    
    // ===== INSTANCE VARIABLES =====
    
    // Current animation state
    private CharacterState currentState = CharacterState.IDLE;
    private PlayMode playMode = PlayMode.LOOP;
    private BufferedImage[] currentFrames = new BufferedImage[0];
    private int currentFrameIndex = 0;
    
    // Timing and synchronization
    private long stateStartTimeMs = 0;
    private long frameStartTimeMs = 0;
    private long frameElapsedTimeMs = 0;
    private long totalElapsedTimeMs = 0;
    
    // Frame callbacks for VFX/audio sync
    private Map<Integer, List<AnimationFrameCallback>> frameCallbacks = new HashMap<>();
    
    // Animation metadata from Phase 2
    private AssetManager_001_UnifiedLoader assetManager;
    private AssetMetadata assetMetadata;
    
    // Direction (for sprite flipping)
    private boolean facingRight = true;
    
    // State tracking
    private boolean isAnimationComplete = false;
    private boolean isInterruptible = true;
    
    // Previous state for debugging
    private CharacterState previousState = null;
    
    // ===== CONSTRUCTOR =====
    public GameplayAnimationController() {
        this.assetManager = AssetManager_001_UnifiedLoader.getInstance();
        this.assetMetadata = AssetMetadata.getInstance();
        
        // Initialize with idle state
        initializeState(CharacterState.IDLE);
        
        Core.logger.info("[GameplayAnimationController] Initialized - Ready for Phase 3 animation playback");
    }
    
    // ===== CORE UPDATE METHOD (Called every 16ms @ 60fps) =====
    /**
     * Updates animation frame timing and checks for frame callbacks.
     * Called from game loop update cycle.
     */
    public void update(long deltaTimeMs) {
        if (currentFrames.length == 0) {
            isAnimationComplete = true;
            return;
        }
        
        // Track elapsed time
        frameElapsedTimeMs += deltaTimeMs;
        totalElapsedTimeMs += deltaTimeMs;
        
        // Get frame duration for current state
        long frameDuration = currentState.frameDurationMs;
        
        // Check if frame duration exceeded (advance to next frame)
        if (frameElapsedTimeMs >= frameDuration) {
            advanceFrame();
            frameElapsedTimeMs = 0;
            frameStartTimeMs = totalElapsedTimeMs;
        }
        
        // Check for frame callbacks at current frame
        checkFrameCallbacks();
        
        // Check if animation complete (for PLAY_ONCE animations)
        if (playMode == PlayMode.PLAY_ONCE && currentFrameIndex >= currentFrames.length - 1) {
            isAnimationComplete = true;
        }
    }
    
    // ===== STATE MANAGEMENT =====
    /**
     * Changes character state and loads associated animation frames.
     * Validates state transitions and loads frames from AssetManager.
     */
    public void setCharacterState(CharacterState newState) {
        // Don't re-enter current state
        if (newState == currentState && !isAnimationComplete) {
            return;
        }
        
        // Check if current animation is interruptible
        if (!isInterruptible && playMode == PlayMode.PLAY_ONCE) {
            return;
        }
        
        previousState = currentState;
        initializeState(newState);
    }
    
    /**
     * Internal method to initialize a new animation state.
     */
    private void initializeState(CharacterState state) {
        currentState = state;
        currentFrameIndex = 0;
        frameElapsedTimeMs = 0;
        stateStartTimeMs = totalElapsedTimeMs;
        isAnimationComplete = false;
        isInterruptible = determineInterruptibility(state);
        
        // Set play mode based on state
        playMode = (state == CharacterState.IDLE || state == CharacterState.WALK || 
                   state == CharacterState.RUN) ? PlayMode.LOOP : PlayMode.PLAY_ONCE;
        
        // Load animation frames from AssetManager (Phase 2 integration)
        loadFramesForState(state);
        
        Core.logger.debug("[AnimationController] State changed to: " + state.name() + 
                         " (Frames: " + currentFrames.length + ", Duration: " + 
                         state.frameDurationMs + "ms)");
    }
    
    /**
     * Loads animation frames from AssetManager based on character state.
     * Uses AssetPathBridge and AssetMetadata for organized access.
     */
    private void loadFramesForState(CharacterState state) {
        try {
            switch (state) {
                case IDLE:
                    loadPlayerAnimation("Idle", AssetMetadata.getInstance().AnimFrames.BIKER_IDLE_FRAMES);
                    break;
                    
                case WALK:
                    loadPlayerAnimation("Walk", AssetMetadata.getInstance().AnimFrames.BIKER_WALK_FRAMES);
                    break;
                    
                case RUN:
                    loadPlayerAnimation("Run", AssetMetadata.getInstance().AnimFrames.BIKER_RUN_FRAMES);
                    break;
                    
                case JUMP:
                    loadPlayerAnimation("Jump", AssetMetadata.getInstance().AnimFrames.BIKER_JUMP_FRAMES);
                    break;
                    
                case ATTACK:
                    loadPlayerAnimation("Attack", AssetMetadata.getInstance().AnimFrames.BIKER_ATTACK_FRAMES);
                    break;
                    
                case HURT:
                    loadPlayerAnimation("Hurt", AssetMetadata.getInstance().AnimFrames.BIKER_HURT_FRAMES);
                    break;
                    
                case DEATH:
                    loadPlayerAnimation("Death", AssetMetadata.getInstance().AnimFrames.BIKER_DEATH_FRAMES);
                    break;
            }
        } catch (Exception e) {
            Core.logger.error("[AnimationController] Failed to load " + state.name() + " animation: " + e.getMessage());
            currentFrames = new BufferedImage[0];
        }
    }
    
    /**
     * Helper method to load player animation frames from AssetManager.
     */
    private void loadPlayerAnimation(String animationName, int frameCount) {
        String path = AssetPathBridge.Characters.PLAYER_BASE + "/biker_" + animationName.toLowerCase() + ".png";
        
        // Load sprite frames using AssetManager (Phase 2)
        BufferedImage[] frames = assetManager.getSpriteFrames(path, frameCount);
        
        if (frames != null && frames.length > 0) {
            currentFrames = frames;
            Core.logger.debug("[AnimationController] Loaded " + animationName + 
                             ": " + frameCount + " frames from " + path);
        } else {
            Core.logger.warn("[AnimationController] Failed to load frames for " + animationName + 
                            " - file may not exist: " + path);
            currentFrames = new BufferedImage[0];
        }
    }
    
    /**
     * Determines if current animation can be interrupted by a new state.
     */
    private boolean determineInterruptibility(CharacterState state) {
        // LOOP animations are always interruptible
        if (state == CharacterState.IDLE || state == CharacterState.WALK || state == CharacterState.RUN) {
            return true;
        }
        
        // ATTACK animations can be interrupted after 200ms (past impact frame)
        if (state == CharacterState.ATTACK) {
            return totalElapsedTimeMs - stateStartTimeMs > 200;
        }
        
        // JUMP, HURT, DEATH animations cannot be interrupted
        return false;
    }
    
    // ===== FRAME ADVANCEMENT =====
    /**
     * Advance to next animation frame with wrapping for loop animations.
     */
    private void advanceFrame() {
        currentFrameIndex++;
        
        // Handle looping
        if (currentFrameIndex >= currentFrames.length) {
            if (playMode == PlayMode.LOOP) {
                currentFrameIndex = 0;  // Wrap to beginning
            } else {
                currentFrameIndex = currentFrames.length - 1;  // Stay on last frame
            }
        }
    }
    
    // ===== FRAME CALLBACKS (VFX/Audio Synchronization) =====
    /**
     * Register a callback to fire when specific frame is reached.
     * Used by VFX and audio systems to synchronize with animation peaks.
     * 
     * Example: Register blood VFX to spawn at frame 4 of attack animation
     */
    public void registerFrameCallback(int frameNumber, AnimationFrameCallback callback) {
        if (!frameCallbacks.containsKey(frameNumber)) {
            frameCallbacks.put(frameNumber, new ArrayList<>());
        }
        frameCallbacks.get(frameNumber).add(callback);
    }
    
    /**
     * Check and execute callbacks for current frame.
     */
    private void checkFrameCallbacks() {
        if (frameCallbacks.containsKey(currentFrameIndex)) {
            List<AnimationFrameCallback> callbacks = frameCallbacks.get(currentFrameIndex);
            for (AnimationFrameCallback callback : callbacks) {
                try {
                    callback.onFrameReached(currentFrameIndex, totalElapsedTimeMs);
                } catch (Exception e) {
                    Core.logger.error("[AnimationController] Error executing frame callback: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Clear all registered frame callbacks (called during state transitions).
     */
    public void clearFrameCallbacks() {
        frameCallbacks.clear();
    }
    
    // ===== RENDERING =====
    /**
     * Render current animation frame at specified position.
     */
    public void render(Graphics2D g, float x, float y) {
        if (currentFrameIndex >= 0 && currentFrameIndex < currentFrames.length) {
            BufferedImage frame = currentFrames[currentFrameIndex];
            
            if (frame != null) {
                // Flip sprite if facing left
                if (!facingRight) {
                    // Draw flipped (horizontally mirrored)
                    g.drawImage(frame, (int)x + frame.getWidth(), (int)y, -frame.getWidth(), frame.getHeight(), null);
                } else {
                    // Draw normal
                    g.drawImage(frame, (int)x, (int)y, null);
                }
            }
        }
    }
    
    // ===== GETTERS =====
    public CharacterState getCurrentState() {
        return currentState;
    }
    
    public int getCurrentFrameIndex() {
        return currentFrameIndex;
    }
    
    public int getTotalFrames() {
        return currentFrames.length;
    }
    
    public BufferedImage getCurrentFrame() {
        if (currentFrameIndex >= 0 && currentFrameIndex < currentFrames.length) {
            return currentFrames[currentFrameIndex];
        }
        return null;
    }
    
    public boolean isAnimationComplete() {
        return isAnimationComplete;
    }
    
    public long getStateElapsedTimeMs() {
        return totalElapsedTimeMs - stateStartTimeMs;
    }
    
    public long getFrameElapsedTimeMs() {
        return frameElapsedTimeMs;
    }
    
    public boolean isFacingRight() {
        return facingRight;
    }
    
    public CharacterState getPreviousState() {
        return previousState;
    }
    
    // ===== SETTERS =====
    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }
    
    public void setFacingDirection(float velocityX) {
        this.facingRight = velocityX >= 0;
    }
    
    // ===== PAUSE/RESUME =====
    /**
     * Pause animation and freeze current frame.
     */
    public void pause() {
        playMode = PlayMode.PAUSE;
        Core.logger.debug("[AnimationController] Animation paused at frame " + currentFrameIndex);
    }
    
    /**
     * Resume animation from paused state.
     */
    public void resume() {
        if (currentState == CharacterState.IDLE || currentState == CharacterState.WALK || currentState == CharacterState.RUN) {
            playMode = PlayMode.LOOP;
        } else {
            playMode = PlayMode.PLAY_ONCE;
        }
        Core.logger.debug("[AnimationController] Animation resumed");
    }
    
    // ===== RESET =====
    /**
     * Reset animation controller to idle state.
     * Called when level restarts or player dies.
     */
    public void reset() {
        setCharacterState(CharacterState.IDLE);
        clearFrameCallbacks();
        totalElapsedTimeMs = 0;
        frameElapsedTimeMs = 0;
        Core.logger.info("[AnimationController] Reset to IDLE state");
    }
    
    // ===== DEBUG INFO =====
    /**
     * Return detailed debug information about current animation.
     */
    public String getDebugInfo() {
        return String.format(
            "State: %s | Frame: %d/%d | Elapsed: %dms | Duration: %dms | Complete: %s",
            currentState.name(),
            currentFrameIndex + 1,
            currentFrames.length,
            frameElapsedTimeMs,
            currentState.frameDurationMs,
            isAnimationComplete
        );
    }
}
