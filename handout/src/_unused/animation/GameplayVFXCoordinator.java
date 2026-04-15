package vfx;

import managers.Core;
import managers.AssetManager_001_UnifiedLoader;
import managers.AssetPathBridge;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PHASE 3: VFX COORDINATION SYSTEM
 * 
 * Manages all active visual effects (particles, sprite animations, decals).
 * Synchronized with animation frames at millisecond precision.
 * Handles memory cleanup and lifetime expiration.
 * 
 * VFX TYPES & LIFETIMES:
 * - Footstep particles: 500ms (300ms visible + 200ms fade)
 * - Jump aura: 600ms (particles disperse)
 * - Blood splatter: 320ms (80ms/frame × 4 frames)
 * - Spark burst: 320ms (80ms/frame × 4 frames, +20ms delay after blood)
 * - Slash trail: 120ms (visual arc during swing)
 * - Portal animation: 3000ms+ (persistent, loops)
 * - Level transition fade: Variable
 * 
 * Timeline: Spawned by animation frame callbacks, self-destroy at lifetime expiration
 * Integration Points: Called from attack/jump handlers, collision system
 */
public class GameplayVFXCoordinator {
    
    // ===== VFX TYPES ENUM =====
    public enum VFXType {
        FOOTSTEP_PARTICLES(500),    // Particles that fade out
        JUMP_AURA(600),             // Jump/takeoff effect
        BLOOD_SPLATTER(320),        // Impact blood effect
        SPARK_BURST(320),           // Impact sparks
        SLASH_TRAIL(120),           // Weapon swing arc
        PORTAL_ANIMATION(3000),     // Level portal loop
        LEVEL_TRANSITION_FADE(1000),// Fade to black
        PARTICLE_TRAIL(500),        // General movement particles
        UI_INTERACTION_GLOW(200);   // UI feedback
        
        public final long defaultLifetimeMs;
        VFXType(long defaultLifetimeMs) {
            this.defaultLifetimeMs = defaultLifetimeMs;
        }
    }
    
    // ===== VFX INSTANCE CLASS =====
    /**
     * Instance of a single VFX effect in the world.
     */
    public class VFXInstance {
        public VFXType type;
        public BufferedImage[] frames;
        public long creationTimeMs;
        public long lifetimeMs;
        public float x, y;
        public float velocityX = 0.0f, velocityY = 0.0f;  // For moving effects
        public float opacity = 1.0f;
        public int currentFrame = 0;
        public long frameElapsedMs = 0;
        public long frameDurationMs;
        
        // Playback control
        public boolean looping = false;
        public boolean isFading = false;
        
        public VFXInstance(VFXType type, float x, float y, BufferedImage[] frames, 
                          long lifetimeMs, long frameDurationMs) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.frames = frames;
            this.creationTimeMs = System.currentTimeMillis();
            this.lifetimeMs = lifetimeMs;
            this.frameDurationMs = frameDurationMs;
        }
        
        /**
         * Check if VFX has expired.
         */
        public boolean isExpired(long currentTimeMs) {
            return (currentTimeMs - creationTimeMs) >= lifetimeMs;
        }
        
        /**
         * Update VFX animation frame and position.
         */
        public void update(long deltaMs, long currentTimeMs) {
            // Update frame timer
            frameElapsedMs += deltaMs;
            
            if (frameElapsedMs >= frameDurationMs && frames.length > 0) {
                currentFrame++;
                frameElapsedMs = 0;
                
                // Handle looping
                if (currentFrame >= frames.length) {
                    if (looping) {
                        currentFrame = 0;
                    } else {
                        currentFrame = frames.length - 1;
                    }
                }
            }
            
            // Update position (gravity/velocity)
            x += velocityX * deltaMs / 1000.0f;
            y += velocityY * deltaMs / 1000.0f;
            
            // Apply gravity to particles
            if (type == VFXType.FOOTSTEP_PARTICLES || type == VFXType.PARTICLE_TRAIL) {
                velocityY += 0.1f;  // Gravity effect
            }
            
            // Fade out in final 100ms
            long elapsedMs = currentTimeMs - creationTimeMs;
            if (elapsedMs > (lifetimeMs - 100)) {
                // Fade from 1.0 to 0.0 over final 100ms
                opacity = Math.max(0.0f, 1.0f - (float)(elapsedMs - (lifetimeMs - 100)) / 100.0f);
            }
        }
        
        /**
         * Render VFX at current position and frame.
         */
        public void render(Graphics2D g, float screenX, float screenY) {
            if (currentFrame >= 0 && currentFrame < frames.length) {
                BufferedImage frame = frames[currentFrame];
                
                if (frame != null) {
                    // Set opacity
                    float oldOpacity = 1.0f;
                    if (opacity < 1.0f) {
                        // TODO: Apply opacity (needs AlphaComposite)
                        g.setComposite(java.awt.AlphaComposite.getInstance(
                            java.awt.AlphaComposite.SRC_OVER, opacity));
                    }
                    
                    // Draw frame at world position + screen offset
                    g.drawImage(frame, 
                        (int)(screenX + x), 
                        (int)(screenY + y), 
                        null);
                    
                    // Restore opacity
                    if (opacity < 1.0f) {
                        g.setComposite(java.awt.AlphaComposite.getInstance(
                            java.awt.AlphaComposite.SRC_OVER, oldOpacity));
                    }
                }
            }
        }
    }
    
    // ===== INSTANCE VARIABLES =====
    private List<VFXInstance> activeVFX = new ArrayList<>();
    private Map<VFXType, BufferedImage[]> vfxFrameCache = new HashMap<>();
    private AssetManager_001_UnifiedLoader assetManager;
    private long lastUpdateTimeMs = 0;
    
    // ===== CONSTRUCTOR =====
    public GameplayVFXCoordinator() {
        this.assetManager = AssetManager_001_UnifiedLoader.getInstance();
        Core.logger.info("[GameplayVFXCoordinator] Initialized - Ready for Phase 3 VFX spawning");
    }
    
    // ===== VFX SPAWNING =====
    
    /**
     * Spawn VFX at specified world position.
     * Loads frames from AssetManager and creates VFX instance.
     * Called from attack, jump, and collision handlers.
     */
    public void spawnVFX(VFXType type, float x, float y) {
        spawnVFXWithLifetime(type, x, y, type.defaultLifetimeMs);
    }
    
    /**
     * Spawn VFX with custom lifetime.
     */
    public void spawnVFXWithLifetime(VFXType type, float x, float y, long lifetimeMs) {
        try {
            // Load frames if not cached
            if (!vfxFrameCache.containsKey(type)) {
                loadVFXFrames(type);
            }
            
            BufferedImage[] frames = vfxFrameCache.get(type);
            if (frames == null || frames.length == 0) {
                Core.logger.warn("[VFXCoordinator] Failed to load frames for " + type.name());
                return;
            }
            
            // Create VFX instance
            VFXInstance vfx = new VFXInstance(type, x, y, frames, lifetimeMs, getFrameDuration(type));
            vfx.looping = (type == VFXType.PORTAL_ANIMATION);
            
            activeVFX.add(vfx);
            
            Core.logger.debug("[VFXCoordinator] Spawned " + type.name() + 
                             " at (" + x + ", " + y + ") - Lifetime: " + lifetimeMs + "ms");
            
        } catch (Exception e) {
            Core.logger.error("[VFXCoordinator] Error spawning VFX: " + e.getMessage());
        }
    }
    
    /**
     * Spawn VFX with delayed start (used for secondary effects like sparks after blood).
     * Creates dummy VFX that waits before becoming visible.
     */
    public void spawnVFXDelayed(VFXType type, long delayMs, float x, float y) {
        // Schedule delayed spawn (simplified - in production, use proper scheduler)
        try {
            Thread.sleep(delayMs);
            spawnVFX(type, x, y);
        } catch (InterruptedException e) {
            Core.logger.error("[VFXCoordinator] Delay interrupted: " + e.getMessage());
        }
    }
    
    // ===== FRAME LOADING =====
    
    /**
     * Load VFX animation frames from AssetManager.
     * Uses AssetPathBridge for organized path access.
     */
    private void loadVFXFrames(VFXType type) {
        try {
            String path;
            int frameCount;
            
            switch (type) {
                case FOOTSTEP_PARTICLES:
                    path = AssetPathBridge.VFX.VFX_PARTICLES + "/footstep_metal.png";
                    frameCount = 2;  // Meta: 2 frames for footstep
                    break;
                    
                case JUMP_AURA:
                    path = AssetPathBridge.VFX.VFX_PARTICLES + "/jump_aura.png";
                    frameCount = 6;
                    break;
                    
                case BLOOD_SPLATTER:
                    path = AssetPathBridge.VFX.VFX_BLOOD + "/splatter_medium.png";
                    frameCount = 4;  // 4 frames, 80ms each = 320ms
                    break;
                    
                case SPARK_BURST:
                    path = AssetPathBridge.VFX.VFX_SPARKS + "/burst_medium.png";
                    frameCount = 4;  // 4 frames, 80ms each = 320ms
                    break;
                    
                case SLASH_TRAIL:
                    path = AssetPathBridge.VFX.VFX_OTHER + "/slash_trail.png";
                    frameCount = 8;
                    break;
                    
                case PORTAL_ANIMATION:
                    path = AssetPathBridge.VFX.VFX_OTHER + "/portal_animation.png";
                    frameCount = 6;  // Loops every 500ms
                    break;
                    
                case LEVEL_TRANSITION_FADE:
                    path = AssetPathBridge.GUI.GUI_FRAMES + "/black_fade.png";
                    frameCount = 1;  // Single frame fade
                    break;
                    
                case PARTICLE_TRAIL:
                    path = AssetPathBridge.VFX.VFX_PARTICLES + "/trail_generic.png";
                    frameCount = 4;
                    break;
                    
                case UI_INTERACTION_GLOW:
                    path = AssetPathBridge.GUI.GUI_ICONS + "/glow_effect.png";
                    frameCount = 3;
                    break;
                    
                default:
                    Core.logger.warn("[VFXCoordinator] Unknown VFX type: " + type.name());
                    return;
            }
            
            // Load frames from AssetManager (Phase 2 integration)
            BufferedImage[] frames = assetManager.getSpriteFrames(path, frameCount);
            
            if (frames != null && frames.length > 0) {
                vfxFrameCache.put(type, frames);
                Core.logger.debug("[VFXCoordinator] Loaded " + type.name() + 
                                 ": " + frameCount + " frames from " + path);
            } else {
                Core.logger.warn("[VFXCoordinator] Failed to load VFX frames: " + path);
                vfxFrameCache.put(type, new BufferedImage[0]);
            }
            
        } catch (Exception e) {
            Core.logger.error("[VFXCoordinator] Error loading VFX frames: " + e.getMessage());
            vfxFrameCache.put(type, new BufferedImage[0]);
        }
    }
    
    /**
     * Get frame duration in milliseconds for each VFX type.
     * From Phase 2 AssetMetadata.AnimTiming values.
     */
    private long getFrameDuration(VFXType type) {
        switch (type) {
            case BLOODPLAT_SPLATTER:
            case SPARK_BURST:
                return 80;  // 80ms per frame
                
            case JUMP_AURA:
                return 100;  // 100ms per frame
                
            case SLASH_TRAIL:
                return 15;   // 15ms per frame for smooth visual
                
            case FOOTSTEP_PARTICLES:
            case PARTICLE_TRAIL:
                return 80;
                
            case PORTAL_ANIMATION:
                return 500 / 6;  // 3000ms total ÷ 6 frames = 500ms per frame
                
            case LEVEL_TRANSITION_FADE:
                return 1000;  // Single fade frame
                
            case UI_INTERACTION_GLOW:
                return 66;   // ~60fps animation
                
            default:
                return 100;
        }
    }
    
    // ===== FRAME UPDATE =====
    
    /**
     * Update all active VFX (called every 16ms from game loop).
     * Handles frame advancement, position updates, fade-out, and cleanup.
     */
    public void update(long deltaTimeMs) {
        long currentTimeMs = System.currentTimeMillis();
        
        // Update and cleanup
        List<VFXInstance> expiredVFX = new ArrayList<>();
        
        for (VFXInstance vfx : activeVFX) {
            // Check if expired
            if (vfx.isExpired(currentTimeMs)) {
                expiredVFX.add(vfx);
                continue;
            }
            
            // Update VFX
            vfx.update(deltaTimeMs, currentTimeMs);
        }
        
        // Remove expired VFX and free memory
        for (VFXInstance vfx : expiredVFX) {
            activeVFX.remove(vfx);
            Core.logger.debug("[VFXCoordinator] Cleaned up expired VFX: " + vfx.type.name());
        }
    }
    
    // ===== RENDERING =====
    
    /**
     * Render all active VFX to screen.
     */
    public void render(Graphics2D g, float screenOffsetX, float screenOffsetY) {
        for (VFXInstance vfx : activeVFX) {
            try {
                vfx.render(g, screenOffsetX, screenOffsetY);
            } catch (Exception e) {
                Core.logger.error("[VFXCoordinator] Error rendering VFX: " + e.getMessage());
            }
        }
    }
    
    // ===== QUERIES =====
    
    /**
     * Get all active VFX of specific type.
     */
    public List<VFXInstance> getVFXByType(VFXType type) {
        List<VFXInstance> results = new ArrayList<>();
        for (VFXInstance vfx : activeVFX) {
            if (vfx.type == type) {
                results.add(vfx);
            }
        }
        return results;
    }
    
    /**
     * Get total active VFX count.
     */
    public int getActiveVFXCount() {
        return activeVFX.size();
    }
    
    /**
     * Get memory usage estimate.
     */
    public long getMemoryUsageBytes() {
        long total = 0;
        for (VFXInstance vfx : activeVFX) {
            if (vfx.frames != null) {
                // Rough estimate: 4 bytes per pixel
                for (BufferedImage frame : vfx.frames) {
                    if (frame != null) {
                        total += frame.getWidth() * frame.getHeight() * 4;
                    }
                }
            }
        }
        return total;
    }
    
    // ===== CACHE MANAGEMENT =====
    
    /**
     * Clear all VFX cache (for level transitions).
     */
    public void clearCache() {
        activeVFX.clear();
        vfxFrameCache.clear();
        Core.logger.info("[VFXCoordinator] Cleared all VFX and cache");
    }
    
    /**
     * Clear only active VFX (keep cached frames).
     */
    public void clearActiveVFX() {
        activeVFX.clear();
        Core.logger.debug("[VFXCoordinator] Cleared active VFX instances");
    }
    
    // ===== DEBUG =====
    
    public String getDebugInfo() {
        return String.format(
            "Active VFX: %d | Memory: %d KB | Cached Types: %d",
            activeVFX.size(),
            getMemoryUsageBytes() / 1024,
            vfxFrameCache.size()
        );
    }
}
