package framework;

/**
 * GAME LOOP MANAGER
 * Handles timing, FPS control, delta time calculations
 * Ensures consistent frame updates regardless of system speed
 */
public class GameLoopManager {
    private static final int TARGET_FPS = 60;
    private static final long FRAME_TIME_MS = 1000 / TARGET_FPS;  // ~16.67ms per frame
    
    private long lastUpdateTime;
    private long lastFrameRenderTime;
    private long deltaTime;
    private int framecounter;
    private long lastFPSUpdateTime;
    private int currentFPS;
    
    public GameLoopManager() {
        this.lastUpdateTime = System.currentTimeMillis();
        this.lastFrameRenderTime = System.currentTimeMillis();
        this.deltaTime = 0;
        this.framecounter = 0;
        this.lastFPSUpdateTime = System.currentTimeMillis();
        this.currentFPS = 0;
    }
    
    /**
     * Calculate delta time since last frame
     */
    public long calculateDeltaTime() {
        long currentTime = System.currentTimeMillis();
        this.deltaTime = currentTime - this.lastUpdateTime;
        this.lastUpdateTime = currentTime;
        return this.deltaTime;
    }
    
    /**
     * Get delta time in seconds for physics calculations
     */
    public float getDeltaSeconds() {
        return this.deltaTime / 1000.0f;
    }
    
    /**
     * Sleep to maintain target FPS
     */
    public void syncFrameRate() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - this.lastFrameRenderTime;
        
        if (elapsedTime < FRAME_TIME_MS) {
            try {
                Thread.sleep(FRAME_TIME_MS - elapsedTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        this.lastFrameRenderTime = System.currentTimeMillis();
    }
    
    /**
     * Update FPS counter
     */
    public void updateFPS() {
        this.framecounter++;
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - this.lastFPSUpdateTime >= 1000) {
            this.currentFPS = this.framecounter;
            this.framecounter = 0;
            this.lastFPSUpdateTime = currentTime;
        }
    }
    
    /**
     * Get current FPS
     */
    public int getCurrentFPS() {
        return this.currentFPS;
    }
    
    /**
     * Get target FPS constant
     */
    public static int getTargetFPS() {
        return TARGET_FPS;
    }
}
