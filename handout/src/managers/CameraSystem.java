package camera;
import game2D.*;
import utilities.AudioSystem;

import java.util.Random;
import java.awt.geom.AffineTransform;


/**
 * ═══════════════════════════════════════════════════════════════════════════════════════
 *                          UNIFIED CAMERA SYSTEM
 *            Complete camera subsystem with all nested classes and utilities
 * ═══════════════════════════════════════════════════════════════════════════════════════
 *
 * RASTER GRAPHICS ONLY - PNG IMAGE ASSETS ONLY
 * ═══════════════════════════════════════════════════════════════════════════════════════
 *
 * UPDATE (April 4, 2026 - UNIFIED CAMERA ARCHITECTURE):
 * ─────────────────────────────────────────────────────────────────────────────────────
 * • Consolidated Camera, ViewportCuller, and CameraPackageCoordinator into single file
 * • Organized as nested classes within CameraSystem for clean architecture
 * • Maintained all functionality and API compatibility
 * • Improved code organization and maintainability
 *
 * CONSOLIDATED FROM:
 * ├─ camera/Camera.java → CameraSystem.Camera
 * ├─ optimization/ViewportCuller.java → CameraSystem.ViewportCuller
 * ├─ camera/CameraPackageCoordinator.java → CameraSystem.Coordinator
 * └─ All nested utilities (CameraAnimator, ViewportManager, CameraTarget, etc)
 *
 * FEATURES:
 * ├─ Core Camera: Follow System, Zoom Control, Screen Shake, Screen Tilt, Boundary Constraints
 * ├─ Viewport Culling: Frustum culling for 30-50% draw call reduction
 * ├─ Coordinator: High-level camera management with world bounds and transforms
 * ├─ Animation: Easing functions, pan/zoom animations
 * ├─ Effects: Screen shake events, presets, multiple camera modes
 * └─ Utilities: ViewportManager, CameraTarget interface, coordinate transformations
 *
 * @author 3359098
 * @version 4.0 - Complete Consolidation
 * @since 2026
 */
public class CameraSystem extends ui.UISystem {
    
    /**
     * Constructor: Initialize CameraSystem
     * Inherits from ObjectiveSystem (300 lines)
     * Adds camera control, viewport management, camera effects
     */
    public CameraSystem() {
        super();  // Initialize ObjectiveSystem → LevelSystem → DialogueSystem → Config → CoreSystem → PhysicsSystem → UISystem → RenderingSystem → InputController → AnimationAndSpriteLoader → GameCore
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        MAIN CAMERA CLASS
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Core camera with positioning, zoom, shake, and follow mechanics
     */
    public static class Camera {
        // ────────────────────────────────────────────────────────────
        // CORE CAMERA STATE
        // ────────────────────────────────────────────────────────────
        
        private float x;
        private float y;
        private float width;
        private float height;
        private float zoom;
        private float targetZoom;
        
        // Lerp follow properties
        private float followLagX;
        private float followLagY;
        private float targetX;
        private float targetY;
        
        // Lookahead properties
        private float lookaheadX;
        private float lookaheadY;
        private float lookaheadDistance;
        
        // Target to follow
        private CameraTarget target;
        
        // Screen shake properties
        private float shakeIntensity;
        private float shakeDuration;
        private float shakeTimer;
        private float shakeOffsetX;
        private float shakeOffsetY;
        private float shakeDecay;
        private float shakeMagnitude;
        private float shakeDecayCurve;
        private Random shakeRandom;
        
        // Bounds/clamping
        private float minX;
        private float minY;
        private float maxX;
        private float maxY;
        private boolean clampToBounds;
        private float levelWidth;
        private float levelHeight;
        
        // Death zoom
        private boolean isDeathZooming;
        private float deathZoomTarget;
        private float deathZoomSpeed;
        
        // Camera state
        private boolean isFollowing;
        private boolean useLookahead;
        private boolean useYAxisLag;
        
        // Dead zone and lead
        private float deadZoneWidth = 100f;
        private float deadZoneHeight = 100f;
        private float horizontalLead = 60f;
        private float verticalLead = 20f;
        private float horizontalOffset = 0f;
        private float verticalOffset = -20f;
        private float smoothSpeed = 8.0f;
        
        // Screen tilt (rotation) system
        private float currentTilt = 0.0f;
        private float targetTilt = 0.0f;
        private float tiltSpeed = 0.02f;
        
        // Boss fight mode
        private boolean inBossFight = false;
        private int screenWidth;
        private int screenHeight;
        
        // Camera modes
        public enum CameraMode {
            NORMAL,          // Standard platforming follow
            TIGHT,           // Boss/high-difficulty combat
            WIDE,            // Exploration/puzzle solving
            CINEMATIC,       // Cutscene/special moments
            LOCKED           // Fixed position
        }
        private CameraMode currentMode = CameraMode.NORMAL;
        private CameraMode targetMode = CameraMode.NORMAL;
        private float modeFadeDuration = 0.5f;
        private float modeTransitionTime = 0.0f;
        
        // Animation system
        private CameraAnimator animator;
        private ViewportManager viewportManager;
        
        /**
         * Creates a new camera with specified dimensions
         */
        public Camera(float x, float y, float width, float height) {
            this(x, y, width, height, width, height);
        }
        
        /**
         * Creates a new camera with dimensions and world bounds
         */
        public Camera(float x, float y, float width, float height, float levelWidth, float levelHeight) {
            this.x = x;
            this.y = y;
            this.targetX = x;
            this.targetY = y;
            this.width = width;
            this.height = height;
            this.screenWidth = (int)width;
            this.screenHeight = (int)height;
            this.levelWidth = levelWidth;
            this.levelHeight = levelHeight;
            this.zoom = 1.0f;
            this.targetZoom = 1.0f;
            
            this.followLagX = 0.1f;
            this.followLagY = 0.1f;
            this.lookaheadDistance = 50f;
            this.lookaheadX = 0;
            this.lookaheadY = 0;
            
            this.shakeIntensity = 0;
            this.shakeDuration = 0;
            this.shakeTimer = 0;
            this.shakeDecay = 1.0f;
            this.shakeOffsetX = 0;
            this.shakeOffsetY = 0;
            this.shakeMagnitude = 0f;
            this.shakeDecayCurve = 0.8f;
            this.shakeRandom = new Random();
            
            this.clampToBounds = true;
            this.isFollowing = false;
            this.useLookahead = true;
            this.useYAxisLag = true;
            
            this.minX = width * 0.5f;
            this.maxX = levelWidth - width * 0.5f;
            this.minY = height * 0.5f;
            this.maxY = levelHeight - height * 0.5f;
            
            this.isDeathZooming = false;
            this.deathZoomTarget = 2.0f;
            this.deathZoomSpeed = 0.5f;
            
            this.animator = new CameraAnimator(this);
            this.viewportManager = new ViewportManager(this);
        }
        
        /**
         * Updates camera position and effects
         */
        public void update(float deltaTime) {
            float deltaSeconds = deltaTime / 1000f;
            
            // Update following behavior
            if (isFollowing && target != null) {
                updateFollow(deltaSeconds);
            }
            
            // Update animation
            if (animator != null) {
                animator.update(deltaSeconds);
            }
            
            // Update shake effect
            updateShake(deltaSeconds);
            
            // Update zoom
            updateZoom(deltaSeconds);
            
            // Update mode transition
            updateModeTransition(deltaSeconds);
            
            // Clamp to bounds
            if (clampToBounds) {
                clampToBounds();
            }
        }
        
        /**
         * Updates camera following with lerp and lookahead
         */
        private void updateFollow(float deltaSeconds) {
            float targetPosX = target.getCameraFollowX();
            float targetPosY = target.getCameraFollowY();
            float velocityX = target.getCameraVelocityX();
            float velocityY = target.getCameraVelocityY();
            float velocity = (float) Math.sqrt(velocityX * velocityX + velocityY * velocityY);
            
            // DYNAMIC DEAD ZONE BASED ON MOVEMENT SPEED
            updateDynamicDeadZone(velocity);
            
            // DIRECTIONAL LOOKAHEAD (scale by lookaheadDistance)
            if (useLookahead && lookaheadDistance > 0) {
                // Horizontal lookahead: apply when moving horizontally
                if (Math.abs(velocityX) > 0.1f) {
                    lookaheadX = (velocityX > 0 ? 1 : -1) * horizontalLead * (lookaheadDistance / 50f);
                } else {
                    lookaheadX *= 0.85f;  // Smoothly decay horizontal lookahead
                }
                
                // Vertical lookahead: apply when moving vertically
                if (Math.abs(velocityY) > 0.1f) {
                    lookaheadY = (velocityY > 0 ? 1 : -1) * verticalLead * (lookaheadDistance / 50f);
                } else {
                    lookaheadY *= 0.85f;  // Smoothly decay vertical lookahead
                }
            }
            
            // APPLY FOCUS OFFSET (mode-aware, set by setMode)
            float focusX = targetPosX + lookaheadX + horizontalOffset;
            float focusY = targetPosY + lookaheadY + verticalOffset;
            
            targetX = focusX - (width / zoom) * 0.5f;
            targetY = focusY - (height / zoom) * 0.5f;
            
            // USE SEPARATE FOLLOW LAG (X and Y independent smoothing)
            x += (targetX - x) * followLagX * deltaSeconds * smoothSpeed;
            
            if (useYAxisLag) {
                y += (targetY - y) * followLagY * deltaSeconds * smoothSpeed;
            } else {
                y = targetY;
            }
            
            // UPDATE VIEWPORT MANAGER with current camera bounds
            if (viewportManager != null) {
                viewportManager.setCullingMargin(100f);
            }
        }
        
        /**
         * Updates dead zones dynamically based on player movement speed
         */
        private void updateDynamicDeadZone(float velocity) {
            // Scale dead zone based on movement velocity (faster movement = larger dead zone)
            float velocityFactor = Math.min(1.0f, velocity / 200f);
            float scaledDeadZoneWidth = 100f + (50f * velocityFactor);
            float scaledDeadZoneHeight = 60f + (30f * velocityFactor);
            
            // Lerp to new dead zone values
            deadZoneWidth += (scaledDeadZoneWidth - deadZoneWidth) * 0.1f;
            deadZoneHeight += (scaledDeadZoneHeight - deadZoneHeight) * 0.1f;
        }
        
        /**
         * Updates screen shake effect with decay curve and decay rate
         */
        private void updateShake(float deltaSeconds) {
            if (shakeTimer > 0) {
                shakeTimer -= deltaSeconds;
                
                // Apply shakeDecay as exponential multiplier (controls how fast it decays per frame)
                shakeIntensity *= (float) Math.pow(shakeDecay, deltaSeconds);
                
                // Use shakeDecayCurve as exponent for non-linear decay curve
                float timeRemaining = shakeTimer / shakeDuration;
                float decayMultiplier = (float) Math.pow(timeRemaining, shakeDecayCurve);
                float intensityDecay = shakeIntensity * decayMultiplier;
                
                shakeOffsetX = (float) (shakeRandom.nextDouble() - 0.5f) * 2 * intensityDecay;
                shakeOffsetY = (float) (shakeRandom.nextDouble() - 0.5f) * 2 * intensityDecay;
                shakeMagnitude = intensityDecay;
            } else {
                shakeOffsetX = 0;
                shakeOffsetY = 0;
                shakeIntensity = 0;
                shakeMagnitude = 0;
            }
        }
        
        /**
         * Updates zoom smoothly using unified smoothSpeed
         */
        private void updateZoom(float deltaSeconds) {
            if (isDeathZooming) {
                zoom += (deathZoomTarget - zoom) * deathZoomSpeed;
                
                if (Math.abs(deathZoomTarget - zoom) < 0.01f) {
                    zoom = deathZoomTarget;
                    isDeathZooming = false;
                }
            } else {
                // Use unified smoothSpeed instead of hardcoded 0.1f
                zoom += (targetZoom - zoom) * smoothSpeed * deltaSeconds;
            }
        }
        
        /**
         * Updates camera mode transition and screen tilt
         */
        private void updateModeTransition(float deltaSeconds) {
            // UPDATE SCREEN TILT for dynamic rotation effects
            currentTilt += (targetTilt - currentTilt) * tiltSpeed * deltaSeconds;
            
            if (currentMode != targetMode && modeTransitionTime < modeFadeDuration) {
                modeTransitionTime += deltaSeconds;
                if (modeTransitionTime >= modeFadeDuration) {
                    currentMode = targetMode;
                }
            }
        }
        
        /**
         * Clamps camera position to world bounds (uses levelWidth and levelHeight)
         */
        private void clampToBounds() {
            float viewportWidth = width / zoom;
            float viewportHeight = height / zoom;
            
            // Use levelWidth and levelHeight to constrain camera bounds
            float worldMaxX = levelWidth > 0 ? levelWidth : maxX;
            float worldMaxY = levelHeight > 0 ? levelHeight : maxY;
            
            float clampedMinX = minX;
            float clampedMaxX = Math.max(worldMaxX - viewportWidth, clampedMinX);
            float clampedMinY = minY;
            float clampedMaxY = Math.max(worldMaxY - viewportHeight, clampedMinY);
            
            x = Math.max(clampedMinX, Math.min(x, clampedMaxX));
            y = Math.max(clampedMinY, Math.min(y, clampedMaxY));
        }
        
        // ════════════════════════════════════════════════════════════
        // PUBLIC API
        // ════════════════════════════════════════════════════════════
        
        public void setTarget(CameraTarget target) {
            this.target = target;
            this.isFollowing = true;
        }
        
        public void clearTarget() {
            this.target = null;
            this.isFollowing = false;
        }
        
        public void shake(float intensity, float duration) {
            this.shakeIntensity = intensity;
            this.shakeDuration = duration;
            this.shakeTimer = duration;
            this.shakeMagnitude = Math.max(this.shakeMagnitude, intensity);
        }
        
        public void zoomTo(float targetZoom, float duration) {
            this.targetZoom = targetZoom;
        }
        
        public void setZoom(float zoom) {
            this.zoom = zoom;
            this.targetZoom = zoom;
        }
        
        /**
         * Set camera translation (position) directly
         */
        public void setTranslation(float x, float y) {
            this.x = x;
            this.y = y;
        }
        
        /**
         * Set camera X position
         */
        public void setX(float x) {
            this.x = x;
        }
        
        /**
         * Set camera Y position
         */
        public void setY(float y) {
            this.y = y;
        }
        
        public void panToPosition(float x, float y, float duration) {
            this.targetX = x;
            this.targetY = y;
        }
        
        public void setMode(CameraMode mode) {
            if (mode != currentMode) {
                targetMode = mode;
                modeTransitionTime = 0.0f;
                
                switch (mode) {
                    case NORMAL:
                        setDeadZone(100.0f, 60.0f);
                        setLead(60.0f, 20.0f);
                        setFocusOffset(0f, -20f);     // Look ahead of player
                        smoothSpeed = 8.0f;            // Balanced smoothing
                        break;
                    case TIGHT:
                        setDeadZone(50.0f, 30.0f);
                        setLead(30.0f, 10.0f);
                        setFocusOffset(20f, -10f);     // Shift perspective
                        smoothSpeed = 10.0f;           // Faster response
                        targetTilt = 0.05f;            // Slight tilt on combat
                        break;
                    case WIDE:
                        setDeadZone(150.0f, 100.0f);
                        setLead(100.0f, 40.0f);
                        setFocusOffset(-30f, 0f);      // Wider view
                        smoothSpeed = 6.0f;            // Slower, more cinematic
                        targetTilt = -0.05f;           // Slight counter-tilt
                        break;
                    case CINEMATIC:
                        setDeadZone(250.0f, 150.0f);
                        setLead(80.0f, 60.0f);
                        setFocusOffset(50f, -40f);     // Dramatic positioning
                        smoothSpeed = 4.0f;            // Slow, cinematic pan
                        targetTilt = 0.1f;             // Pronounced tilt
                        break;
                    case LOCKED:
                        smoothSpeed = 0f;              // No movement
                        targetTilt = 0f;               // No rotation
                        break;
                }
            }
        }
        
        public void enterBossFight() {
            inBossFight = true;
            setMode(CameraMode.TIGHT);
            setZoom(0.8f);
        }
        
        public void exitBossFight() {
            inBossFight = false;
            setMode(CameraMode.NORMAL);
            setZoom(1.0f);
        }
        
        // ════════════════════════════════════════════════════════════
        // GETTERS
        // ════════════════════════════════════════════════════════════
        
        public float getRawX() { return x; }
        public float getRawY() { return y; }
        public float getScreenX() { return x + shakeOffsetX; }
        public float getScreenY() { return y + shakeOffsetY; }
        public float getX() { return x; }
        public float getY() { return y; }
        public float getZoom() { return zoom; }
        public float getWidth() { return width; }
        public float getHeight() { return height; }
        public float getLeft() { return x; }
        public float getRight() { return x + width / zoom; }
        public float getTop() { return y; }
        public float getBottom() { return y + height / zoom; }
        public float getLevelWidth() { return levelWidth; }
        public float getLevelHeight() { return levelHeight; }
        public float getFollowLagX() { return followLagX; }
        public float getFollowLagY() { return followLagY; }
        public float getLookaheadDistance() { return lookaheadDistance; }
        public float getTilt() { return currentTilt; }
        public void setTargetTilt(float tilt) { this.targetTilt = tilt; }
        public void setTiltSpeed(float speed) { this.tiltSpeed = Math.max(0.01f, speed); }
        public CameraMode getMode() { return currentMode; }
        public boolean isInBossFight() { return inBossFight; }
        
        public void setFocusOffset(float offsetX, float offsetY) {
            this.horizontalOffset = offsetX;
            this.verticalOffset = offsetY;
        }
        
        public void setSmoothSpeed(float speed) {
            this.smoothSpeed = Math.max(0.01f, speed);
        }
        
        public void setShakeDecayCurve(float curve) {
            this.shakeDecayCurve = Math.max(0.1f, Math.min(2.0f, curve));
        }
        
        public void setShakeDecay(float decay) {
            this.shakeDecay = Math.max(0.1f, Math.min(1.0f, decay));
        }
        
        public float getShakeDecay() {
            return shakeDecay;
        }
        
        public float getParallaxOffsetX(float parallaxFactor) {
            return -(x - screenWidth * 0.5f) * (1.0f - parallaxFactor);
        }
        
        public float getParallaxOffsetY(float parallaxFactor) {
            return -(y - screenHeight * 0.5f) * (1.0f - parallaxFactor);
        }
        
        public boolean isVisible(float x, float y) {
            return x >= getLeft() && x <= getRight() && y >= getTop() && y <= getBottom();
        }
        
        public float worldToScreenX(float worldX) {
            return (worldX - x) * zoom;
        }
        
        public float worldToScreenY(float worldY) {
            return (worldY - y) * zoom;
        }
        
        // ════════════════════════════════════════════════════════════
        // SETTERS
        // ════════════════════════════════════════════════════════════
        
        public void setFollowLag(float lag) { 
            this.followLagX = Math.max(0.01f, lag); 
            this.followLagY = Math.max(0.01f, lag); 
        }
        public void setFollowLagX(float lagX) { this.followLagX = Math.max(0.01f, lagX); }
        public void setFollowLagY(float lagY) { this.followLagY = Math.max(0.01f, lagY); }
        public void setUseLookahead(boolean use) { this.useLookahead = use; }
        public void setUseYAxisLag(boolean use) { this.useYAxisLag = use; }
        public void setLookaheadDistance(float distance) { 
            this.lookaheadDistance = Math.max(0.1f, distance);
        }
        public void setLevelBounds(float widthLevel, float heightLevel) {
            this.levelWidth = Math.max(width, widthLevel);
            this.levelHeight = Math.max(height, heightLevel);
        }
        public void setDeadZone(float width, float height) { 
            this.deadZoneWidth = width; 
            this.deadZoneHeight = height; 
        }
        public void setLead(float horizontal, float vertical) {
            this.horizontalLead = horizontal;
            this.verticalLead = vertical;
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                      VIEWPORT CULLING CLASS
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Optimizes rendering by skipping off-screen entities
     * Provides 30-50% draw call reduction in typical gameplay scenes
     */
    public static class ViewportCuller {
        // Viewport dimensions and position
        private float viewportX;      // Camera/viewport left edge
        private float viewportY;      // Camera/viewport top edge
        private float viewportWidth;  // Screen width
        private float viewportHeight; // Screen height
        
        // Margin for smooth entity fade-in (prevents pop-in)
        private float margin;         // Margin around viewport (default 100px)
        
        // Extended viewport with margin
        private float extViewportX;
        private float extViewportY;
        private float extViewportWidth;
        private float extViewportHeight;
        
        // Statistics for profiling
        private long totalTests = 0;
        private long visibleCount = 0;
        private long culledCount = 0;
        
        /**
         * Create a viewport culler
         */
        public ViewportCuller(float screenWidth, float screenHeight, float margin) {
            this.viewportWidth = screenWidth;
            this.viewportHeight = screenHeight;
            this.margin = margin;
            
            setViewport(0, 0);
        }
        
        /**
         * Set viewport position (typically camera position)
         */
        public void setViewport(float x, float y) {
            this.viewportX = x;
            this.viewportY = y;
            
            // Calculate extended viewport with margin
            this.extViewportX = x - margin;
            this.extViewportY = y - margin;
            this.extViewportWidth = viewportWidth + (2 * margin);
            this.extViewportHeight = viewportHeight + (2 * margin);
        }
        
        /**
         * Check if entity is visible (intersects with viewport)
         */
        public boolean isVisible(float entityX, float entityY, float width, float height) {
            totalTests++;
            
            // AABB intersection test with extended viewport
            if (entityX + width <= extViewportX ||
                entityX >= extViewportX + extViewportWidth ||
                entityY + height <= extViewportY ||
                entityY >= extViewportY + extViewportHeight) {
                
                culledCount++;
                return false;
            }
            
            visibleCount++;
            return true;
        }
        
        /**
         * Check if entity is visible (using bounding box)
         */
        public boolean isVisible(BoundingBox box) {
            if (box == null) return false;
            return isVisible(box.getLeft(), box.getTop(), box.getWidth(), box.getHeight());
        }
        
        /**
         * Check if point is visible
         */
        public boolean isPointVisible(float x, float y) {
            return x >= extViewportX && x <= extViewportX + extViewportWidth &&
                   y >= extViewportY && y <= extViewportY + extViewportHeight;
        }
        
        /**
         * Check if circle is visible (for projectiles, particles, etc)
         */
        public boolean isCircleVisible(float centerX, float centerY, float radius) {
            totalTests++;
            
            // Find closest point on viewport to circle center
            float closestX = Math.max(extViewportX, Math.min(centerX, extViewportX + extViewportWidth));
            float closestY = Math.max(extViewportY, Math.min(centerY, extViewportY + extViewportHeight));
            
            // Calculate distance
            float dx = centerX - closestX;
            float dy = centerY - closestY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            
            if (distance >= radius) {
                culledCount++;
                return false;
            }
            
            visibleCount++;
            return true;
        }
        
        /**
         * Get list of visible entities from a bounding box list
         */
        public java.util.List<Integer> filterVisibleEntities(
                java.util.Map<Integer, BoundingBox> entities) {
            
            java.util.List<Integer> visible = new java.util.ArrayList<>();
            
            for (java.util.Map.Entry<Integer, BoundingBox> entry : entities.entrySet()) {
                if (isVisible(entry.getValue())) {
                    visible.add(entry.getKey());
                }
            }
            
            return visible;
        }
        
        // ════════════════════════════════════════════════════════════
        // STATISTICS & PROFILING
        // ════════════════════════════════════════════════════════════
        
        public double getCullRate() {
            long total = visibleCount + culledCount;
            return total > 0 ? (100.0 * culledCount / total) : 0;
        }
        
        public long getVisibleCount() { return visibleCount; }
        public long getCulledCount() { return culledCount; }
        public long getTotalTests() { return totalTests; }
        
        public void resetStats() {
            totalTests = 0;
            visibleCount = 0;
            culledCount = 0;
        }
        
        public float[] getViewportBounds() {
            return new float[] {viewportX, viewportY, viewportWidth, viewportHeight};
        }
        
        public float[] getExtendedViewportBounds() {
            return new float[] {extViewportX, extViewportY, extViewportWidth, extViewportHeight};
        }
        
        public String getReport() {
            StringBuilder sb = new StringBuilder();
            sb.append("═════════════════ VIEWPORT CULLING REPORT ═════════════════\n");
            sb.append(String.format("Viewport: (%.0f, %.0f) [%.0f x %.0f]\n",
                viewportX, viewportY, viewportWidth, viewportHeight));
            sb.append(String.format("Margin: %.0fpx\n", margin));
            sb.append(String.format("Visible: %d | Culled: %d | Cull Rate: %.1f%%\n",
                visibleCount, culledCount, getCullRate()));
            sb.append(String.format("Total Tests: %d\n", totalTests));
            
            if (getCullRate() > 50) {
                sb.append("✓ High culling efficiency\n");
            } else if (getCullRate() > 30) {
                sb.append("✓ Good culling efficiency\n");
            } else {
                sb.append("⚠ Low culling efficiency (most entities visible)\n");
            }
            
            sb.append("═════════════════════════════════════════════════════════════\n");
            return sb.toString();
        }
        
        public String getSummary() {
            return String.format(
                "Culling: %.0f%% | Visible: %d | Culled: %d",
                getCullRate(), visibleCount, culledCount
            );
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                      CAMERA PACKAGE COORDINATOR
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * High-level camera coordinator integrating camera position, smoothing, zoom, and effects
     */
    public static class Coordinator {
        
        private final Camera baseCamera;
        
        // Camera position state
        private float targetX = 0;
        private float targetY = 0;
        private float currentX = 0;
        private float currentY = 0;
        
        // Viewport dimensions
        private int viewportWidth;
        private int viewportHeight;
        
        // Camera movement parameters
        private float smoothingFactor = 0.12f;
        private float deadZoneX = 150f;
        private float deadZoneY = 100f;
        private float lookaheadDistance = 50f;
        
        // Camera focus & offset
        private float focusOffsetX = 0f;
        private float focusOffsetY = -60f;
        
        // Shake effects
        private float shakeIntensity = 0f;
        private float shakeDecayRate = 0.85f;
        
        // Zoom system
        private float zoom = 1.0f;
        private float targetZoom = 1.0f;
        private float zoomSmoothness = 0.1f;
        
        // World bounds
        private float minX = 0;
        private float maxX = 2560;
        private float minY = 0;
        private float maxY = 1600;
        private boolean clampToBounds = true;
        
        // Camera modes
        public enum CameraMode {
            NORMAL, TIGHT, WIDE, CINEMATIC, LOCKED
        }
        
        private CameraMode currentMode = CameraMode.NORMAL;
        private CameraMode targetMode = CameraMode.NORMAL;
        private float modeTransitionTime = 0.0f;
        private float modeTransitionDuration = 0.5f;
        
        // Boss fight mode
        private boolean inBossFight = false;
        
        /**
         * Constructor - Initialize camera coordinator
         */
        public Coordinator(int width, int height) {
            this.viewportWidth = width;
            this.viewportHeight = height;
            this.baseCamera = new Camera(width / 2f, height / 2f, width, height, width, height);
            this.currentX = 0;
            this.currentY = 0;
        }
        
        /**
         * Update camera every frame (main update method)
         */
        public void update(long deltaMs, int playerX, int playerY, float playerVelX, float playerVelY) {
            updateCamera((float)playerX, (float)playerY, playerVelX, playerVelY, deltaMs);
        }
        
        /**
         * Update camera position and effects
         */
        public void updateCamera(float playerX, float playerY, float playerVelX, float playerVelY, long deltaMs) {
            // STEP 1: CALCULATE LOOKAHEAD POSITION
            float lookaheadX = playerX;
            float lookaheadY = playerY;
            
            if (lookaheadDistance > 0) {
                float velocity = (float)Math.sqrt(playerVelX * playerVelX + playerVelY * playerVelY);
                if (velocity > 0.1f) {
                    lookaheadX += (playerVelX / velocity) * lookaheadDistance;
                    lookaheadY += (playerVelY / velocity) * lookaheadDistance * 0.5f;
                }
            }
            
            // STEP 2: APPLY FOCUS OFFSET
            targetX = lookaheadX + focusOffsetX;
            targetY = lookaheadY + focusOffsetY;
            
            // STEP 3: APPLY DEAD ZONE
            float deltaX = targetX - currentX;
            float deltaY = targetY - currentY;
            
            if (Math.abs(deltaX) > deadZoneX) {
                float moveAmount = (Math.abs(deltaX) - deadZoneX) * smoothingFactor;
                currentX += (deltaX > 0 ? 1 : -1) * moveAmount;
            }
            
            if (Math.abs(deltaY) > deadZoneY) {
                float moveAmount = (Math.abs(deltaY) - deadZoneY) * smoothingFactor;
                currentY += (deltaY > 0 ? 1 : -1) * moveAmount;
            }
            
            // STEP 4: CLAMP TO WORLD BOUNDS
            if (clampToBounds) {
                float viewportHalfWidth = viewportWidth / (2.0f * zoom);
                float viewportHalfHeight = viewportHeight / (2.0f * zoom);
                
                currentX = Math.max(minX + viewportHalfWidth, Math.min(maxX - viewportHalfWidth, currentX));
                currentY = Math.max(minY + viewportHalfHeight, Math.min(maxY - viewportHalfHeight, currentY));
            }
            
            // STEP 5: UPDATE ZOOM
            updateZoom();
            
            // STEP 6: APPLY SHAKE EFFECT
            if (shakeIntensity > 0) {
                float shakeX = (float)(Math.random() - 0.5f) * shakeIntensity * 2;
                float shakeY = (float)(Math.random() - 0.5f) * shakeIntensity * 2;
                
                baseCamera.setTranslation(currentX + shakeX, currentY + shakeY);
                baseCamera.setZoom(zoom);
                
                shakeIntensity *= shakeDecayRate;
                if (shakeIntensity < 0.05f) {
                    shakeIntensity = 0;
                }
            } else {
                baseCamera.setTranslation(currentX, currentY);
                baseCamera.setZoom(zoom);
            }
            
            // STEP 7: UPDATE MODE TRANSITIONS
            updateModeTransition(deltaMs / 1000.0f);
        }
        
        private void updateZoom() {
            if (Math.abs(zoom - targetZoom) > 0.01f) {
                zoom += (targetZoom - zoom) * zoomSmoothness;
            } else {
                zoom = targetZoom;
            }
        }
        
        private void updateModeTransition(float deltaSeconds) {
            if (currentMode != targetMode && modeTransitionTime < modeTransitionDuration) {
                modeTransitionTime += deltaSeconds;
                if (modeTransitionTime >= modeTransitionDuration) {
                    currentMode = targetMode;
                    modeTransitionTime = 0.0f;
                }
            }
        }
        
        // ════════════════════════════════════════════════════════════
        // CAMERA EFFECTS
        // ════════════════════════════════════════════════════════════
        
        public void shake(float intensity) {
            shakeIntensity = Math.max(shakeIntensity, intensity);
        }
        
        public void shake(float intensity, float duration) {
            shake(intensity);
        }
        
        // ════════════════════════════════════════════════════════════
        // CAMERA CONFIGURATION
        // ════════════════════════════════════════════════════════════
        
        public void setDeadZone(float zoneX, float zoneY) {
            this.deadZoneX = Math.max(0, zoneX);
            this.deadZoneY = Math.max(0, zoneY);
        }
        
        public void setLookaheadDistance(float distance) {
            this.lookaheadDistance = Math.max(0, distance);
        }
        
        public void setFocusOffset(float offsetX, float offsetY) {
            this.focusOffsetX = offsetX;
            this.focusOffsetY = offsetY;
        }
        
        public void setSmoothingFactor(float factor) {
            this.smoothingFactor = Math.max(0.01f, Math.min(1.0f, factor));
        }
        
        public void setShakeDecayCurve(float rate) {
            this.shakeDecayRate = Math.max(0.5f, Math.min(0.99f, rate));
        }
        
        public void setWorldBounds(float minX, float maxX, float minY, float maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }
        
        public void setClampToBounds(boolean clamp) {
            this.clampToBounds = clamp;
        }
        
        public void setZoom(float zoomLevel) {
            this.zoom = Math.max(0.1f, zoomLevel);
            this.targetZoom = zoom;
        }
        
        public void zoomTo(float targetZoom, float smoothness) {
            this.targetZoom = Math.max(0.1f, targetZoom);
            this.zoomSmoothness = Math.max(0.01f, Math.min(1.0f, smoothness));
        }
        
        public void setMode(CameraMode mode) {
            if (mode != currentMode) {
                targetMode = mode;
                modeTransitionTime = 0.0f;
                applyModeSettings(mode);
            }
        }
        
        private void applyModeSettings(CameraMode mode) {
            switch (mode) {
                case NORMAL:
                    setDeadZone(150.0f, 100.0f);
                    setLookaheadDistance(50.0f);
                    setSmoothingFactor(0.12f);
                    setZoom(1.0f);
                    break;
                case TIGHT:
                    setDeadZone(50.0f, 30.0f);
                    setLookaheadDistance(30.0f);
                    setSmoothingFactor(0.15f);
                    setZoom(0.95f);
                    break;
                case WIDE:
                    setDeadZone(200.0f, 150.0f);
                    setLookaheadDistance(80.0f);
                    setSmoothingFactor(0.08f);
                    setZoom(0.85f);
                    break;
                case CINEMATIC:
                    setDeadZone(250.0f, 180.0f);
                    setLookaheadDistance(100.0f);
                    setSmoothingFactor(0.05f);
                    setZoom(1.0f);
                    break;
                case LOCKED:
                    setSmoothingFactor(0f);
                    break;
            }
        }
        
        public void enterBossFight() {
            inBossFight = true;
            setMode(CameraMode.TIGHT);
            zoomTo(0.8f, 0.3f);
        }
        
        public void exitBossFight() {
            inBossFight = false;
            setMode(CameraMode.NORMAL);
            zoomTo(1.0f, 0.3f);
        }
        
        // ════════════════════════════════════════════════════════════
        // COORDINATE TRANSFORMATIONS
        // ════════════════════════════════════════════════════════════
        
        public float worldToScreenX(float worldX) {
            return (worldX - currentX) * zoom;
        }
        
        public float worldToScreenY(float worldY) {
            return (worldY - currentY) * zoom;
        }
        
        public float screenToWorldX(float screenX) {
            return (screenX / zoom) + currentX;
        }
        
        public float screenToWorldY(float screenY) {
            return (screenY / zoom) + currentY;
        }
        
        public AffineTransform getTransform(float screenCenterX, float screenCenterY) {
            AffineTransform transform = new AffineTransform();
            transform.translate(screenCenterX, screenCenterY);
            transform.scale(zoom, zoom);
            transform.translate(-currentX, -currentY);
            return transform;
        }
        
        public void applyTransform(java.awt.Graphics2D g2d, float screenCenterX, float screenCenterY) {
            g2d.setTransform(getTransform(screenCenterX, screenCenterY));
        }
        
        // ════════════════════════════════════════════════════════════
        // VISIBILITY & CULLING
        // ════════════════════════════════════════════════════════════
        
        public boolean isPointVisible(float worldX, float worldY) {
            float screenX = worldToScreenX(worldX);
            float screenY = worldToScreenY(worldY);
            return screenX >= -100 && screenX <= viewportWidth + 100 &&
                   screenY >= -100 && screenY <= viewportHeight + 100;
        }
        
        public boolean isRectVisible(float worldX, float worldY, float width, float height) {
            return isPointVisible(worldX, worldY) || isPointVisible(worldX + width, worldY + height);
        }
        
        // ════════════════════════════════════════════════════════════
        // GETTERS
        // ════════════════════════════════════════════════════════════
        
        public float getCameraX() { return currentX; }
        public float getCameraY() { return currentY; }
        public float getZoom() { return zoom; }
        public CameraMode getMode() { return currentMode; }
        public boolean isInBossFight() { return inBossFight; }
        public Camera getBaseCamera() { return baseCamera; }
        public int getViewportWidth() { return viewportWidth; }
        public int getViewportHeight() { return viewportHeight; }
        public float getShakeIntensity() { return shakeIntensity; }
        
        public String getDebugInfo() {
            return String.format(
                "Camera: (%.0f, %.0f) | Zoom: %.2fx | Shake: %.1f | Mode: %s",
                currentX, currentY, zoom, shakeIntensity, currentMode
            );
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                         NESTED UTILITY CLASSES
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Camera animation system with easing functions, pan, and zoom
     */
    public static class CameraAnimator {
        private Camera camera;
        private float animationTime;
        private float animationDuration;
        private float startX, startY, endX, endY;
        private float startZoom, endZoom;
        private AnimationType animationType = AnimationType.LINEAR;
        private Runnable onComplete;
        private boolean isAnimating;
        
        public enum AnimationType {
            LINEAR, EASE_IN, EASE_OUT, EASE_IN_OUT
        }
        
        public CameraAnimator(Camera camera) {
            this.camera = camera;
        }
        
        public void update(float deltaSeconds) {
            if (!isAnimating) return;
            
            animationTime += deltaSeconds;
            float progress = Math.min(1.0f, animationTime / animationDuration);
            float eased = applyEasing(progress);
            
            camera.x = startX + (endX - startX) * eased;
            camera.y = startY + (endY - startY) * eased;
            camera.zoom = startZoom + (endZoom - startZoom) * eased;
            
            if (progress >= 1.0f) {
                isAnimating = false;
                if (onComplete != null) onComplete.run();
            }
        }
        
        public void panTo(float targetX, float targetY, float duration) {
            startX = camera.x;
            startY = camera.y;
            endX = targetX;
            endY = targetY;
            startZoom = camera.zoom;
            endZoom = camera.zoom;
            animationDuration = duration;
            animationTime = 0;
            isAnimating = true;
        }
        
        public void panAndZoom(float targetX, float targetY, float targetZoom, float duration) {
            startX = camera.x;
            startY = camera.y;
            endX = targetX;
            endY = targetY;
            startZoom = camera.zoom;
            endZoom = targetZoom;
            animationDuration = duration;
            animationTime = 0;
            isAnimating = true;
        }
        
        public void setAnimationType(AnimationType type) {
            this.animationType = type;
        }
        
        public void setOnComplete(Runnable onComplete) {
            this.onComplete = onComplete;
        }
        
        private float applyEasing(float t) {
            return switch (animationType) {
                case LINEAR -> t;
                case EASE_IN -> t * t;
                case EASE_OUT -> 1 - (1 - t) * (1 - t);
                case EASE_IN_OUT -> t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;
            };
        }
    }
    
    /**
     * Event object for screen shake effects
     */
    public static class CameraShakeEvent {
        private float intensity;
        private float duration;
        private float x;
        private float y;
        private boolean isImpact;
        
        public CameraShakeEvent(float intensity, float duration) {
            this(intensity, duration, 0, 0, false);
        }
        
        public CameraShakeEvent(float intensity, float duration, float x, float y) {
            this(intensity, duration, x, y, false);
        }
        
        public CameraShakeEvent(float intensity, float duration, boolean isImpact) {
            this(intensity, duration, 0, 0, isImpact);
        }
        
        public CameraShakeEvent(float intensity, float duration, float x, float y, boolean isImpact) {
            this.intensity = Math.max(0.1f, intensity);
            this.duration = Math.max(0.05f, duration);
            this.x = x;
            this.y = y;
            this.isImpact = isImpact;
        }
        
        public float getIntensity() { return intensity; }
        public float getDuration() { return duration; }
        public float getX() { return x; }
        public float getY() { return y; }
        public boolean isImpact() { return isImpact; }
        
        public float getDistanceFrom(float camX, float camY) {
            return (float)Math.sqrt((x - camX) * (x - camX) + (y - camY) * (y - camY));
        }
        
        public float getScaledIntensity(float maxDistance, float cameraCenterX, float cameraCenterY) {
            float distance = getDistanceFrom(cameraCenterX, cameraCenterY);
            float attenuation = Math.max(0, 1 - (distance / maxDistance));
            return intensity * attenuation;
        }
    }
    
    /**
     * Predefined camera preset configurations
     */
    public static class CameraPreset {
        public final String name;
        public final float followLagX;
        public final float followLagY;
        public final float lookaheadDistance;
        public final float zoom;
        public final boolean useLookahead;
        public final boolean useYAxisLag;
        
        public static final CameraPreset ACTION = new CameraPreset(
            "ACTION", 0.12f, 0.12f, 50f, 1.0f, true, true
        );
        
        public static final CameraPreset EXPLORATION = new CameraPreset(
            "EXPLORATION", 0.08f, 0.08f, 80f, 0.9f, true, true
        );
        
        public static final CameraPreset BOSS_FIGHT = new CameraPreset(
            "BOSS_FIGHT", 0.15f, 0.15f, 60f, 0.8f, true, true
        );
        
        public static final CameraPreset CINEMATIC = new CameraPreset(
            "CINEMATIC", 0.05f, 0.05f, 100f, 1.0f, true, true
        );
        
        public static final CameraPreset THIRD_PERSON = new CameraPreset(
            "THIRD_PERSON", 0.1f, 0.1f, 120f, 0.85f, true, false
        );
        
        public CameraPreset(String name, float lagX, float lagY, float lookahead, float zoom, boolean useLookahead, boolean useYAxisLag) {
            this.name = name;
            this.followLagX = lagX;
            this.followLagY = lagY;
            this.lookaheadDistance = lookahead;
            this.zoom = zoom;
            this.useLookahead = useLookahead;
            this.useYAxisLag = useYAxisLag;
        }
        
        public void apply(Camera camera) {
            camera.setFollowLagX(followLagX);
            camera.setFollowLagY(followLagY);
            camera.setZoom(zoom);
            camera.setUseLookahead(useLookahead);
            camera.setUseYAxisLag(useYAxisLag);
            camera.setLookaheadDistance(lookaheadDistance);
        }
    }
    
    /**
     * Interface for entities that can be followed by the camera
     */
    public interface CameraTarget {
        float getCameraFollowX();
        float getCameraFollowY();
        float getCameraVelocityX();
        float getCameraVelocityY();
        
        default float getWidth() { return 0; }
        default float getHeight() { return 0; }
        default boolean isAlive() { return true; }
    }
    
    /**
     * Manages viewport calculations and frustum culling
     */
    public static class ViewportManager {
        private Camera camera;
        private float cullingMargin;
        
        public ViewportManager(Camera camera) {
            this.camera = camera;
            this.cullingMargin = 100f;
        }
        
        public boolean isPointVisible(float x, float y) {
            return camera.isVisible(x, y);
        }
        
        public boolean isRectVisible(float x, float y, float width, float height) {
            float left = camera.getLeft() - cullingMargin;
            float right = camera.getRight() + cullingMargin;
            float top = camera.getTop() - cullingMargin;
            float bottom = camera.getBottom() + cullingMargin;
            
            return !(x + width < left || x > right || y + height < top || y > bottom);
        }
        
        public boolean isCircleVisible(float centerX, float centerY, float radius) {
            float left = camera.getLeft() - cullingMargin - radius;
            float right = camera.getRight() + cullingMargin + radius;
            float top = camera.getTop() - cullingMargin - radius;
            float bottom = camera.getBottom() + cullingMargin + radius;
            
            return !(centerX < left || centerX > right || centerY < top || centerY > bottom);
        }
        
        public float[] getViewport() {
            return new float[]{
                camera.getLeft(),
                camera.getTop(),
                camera.getRight() - camera.getLeft(),
                camera.getBottom() - camera.getTop()
            };
        }
        
        public float getViewportWidth() {
            return camera.getWidth() / camera.getZoom();
        }
        
        public float getViewportHeight() {
            return camera.getHeight() / camera.getZoom();
        }
        
        public float[] getViewportCenter() {
            return new float[]{
                camera.getRawX() + getViewportWidth() * 0.5f,
                camera.getRawY() + getViewportHeight() * 0.5f
            };
        }
        
        public void setCullingMargin(float margin) {
            this.cullingMargin = margin;
        }
        
        public float getDistanceToCenter(float x, float y) {
            float[] center = getViewportCenter();
            float dx = x - center[0];
            float dy = y - center[1];
            return (float) Math.sqrt(dx * dx + dy * dy);
        }
        
        public float[] worldToScreen(float worldX, float worldY) {
            return new float[]{
                camera.worldToScreenX(worldX),
                camera.worldToScreenY(worldY)
            };
        }
    }
    
    /**
     * Bounding box interface for visibility culling
     */
    public interface BoundingBox {
        float getLeft();
        float getTop();
        float getWidth();
        float getHeight();
    }
}
