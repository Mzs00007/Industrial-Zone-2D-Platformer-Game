package managers;

import camera.CameraSystem;
import java.awt.Point;

/**
 * CameraManager - Consolidated camera system
 * 
 * Merges:
 * - camera/CameraSystem.java
 * 
 * Manages the game camera, zoom, pan, and viewport for level rendering.
 */
public class CameraManager {
    private CameraSystem cameraSystem;
    private boolean isInitialized = false;
    
    /**
     * Constructor - instantiable manager (no static getInstance)
     */
    public CameraManager() {
        this.cameraSystem = new CameraSystem();
    }
    
    /**
     * Initialize camera system
     */
    public void initialize() {
        if (isInitialized) return;
        try {
            System.out.println("[CameraManager] Initializing camera system...");
            cameraSystem.initialize();
            isInitialized = true;
            System.out.println("[CameraManager] ✓ Camera system initialized");
        } catch (Exception e) {
            System.err.println("[CameraManager ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Update camera state
     */
    public void update(long deltaMillis) {
        if (!isInitialized) return;
        cameraSystem.update(deltaMillis);
    }
    
    /**
     * Set camera position
     */
    public void setPosition(float x, float y) {
        if (cameraSystem != null) {
            cameraSystem.setPosition(x, y);
        }
    }
    
    /**
     * Get camera position
     */
    public Point getPosition() {
        if (cameraSystem != null) {
            return cameraSystem.getPosition();
        }
        return new Point(0, 0);
    }
    
    /**
     * Set camera zoom level
     */
    public void setZoom(float zoom) {
        if (cameraSystem != null) {
            cameraSystem.setZoom(zoom);
        }
    }
    
    /**
     * Get camera zoom level
     */
    public float getZoom() {
        if (cameraSystem != null) {
            return cameraSystem.getZoom();
        }
        return 1.0f;
    }
    
    /**
     * Pan camera smoothly
     */
    public void pan(float deltaX, float deltaY, float speed) {
        if (cameraSystem != null) {
            cameraSystem.pan(deltaX, deltaY, speed);
        }
    }
    
    /**
     * Follow target position
     */
    public void follow(float targetX, float targetY) {
        if (cameraSystem != null) {
            cameraSystem.follow(targetX, targetY);
        }
    }
    
    /**
     * Get camera viewport bounds
     */
    public float[] getViewport() {
        if (cameraSystem != null) {
            return cameraSystem.getViewport();
        }
        return new float[] { 0, 0, 1024, 768 };
    }
    
    /**
     * Shutdown camera system
     */
    public void shutdown() {
        if (cameraSystem != null) {
            cameraSystem.shutdown();
        }
        isInitialized = false;
    }
}
