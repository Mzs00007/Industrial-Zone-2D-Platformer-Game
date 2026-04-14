package managers;

import optimization.OptimizationSystem;

/**
 * OptimizationManager - Consolidated performance optimization system
 * 
 * Merges:
 * - optimization/OptimizationSystem.java
 * 
 * Manages performance monitoring, optimization, and memory management.
 */
public class OptimizationManager {
    private OptimizationSystem optimizationSystem;
    private boolean isInitialized = false;
    
    /**
     * Constructor - instantiable manager (no static getInstance)
     */
    public OptimizationManager() {
        this.optimizationSystem = new OptimizationSystem();
    }
    
    /**
     * Initialize optimization system
     */
    public void initialize() {
        if (isInitialized) return;
        try {
            System.out.println("[OptimizationManager] Initializing optimization system...");
            optimizationSystem.initialize();
            isInitialized = true;
            System.out.println("[OptimizationManager] ✓ Optimization system initialized");
        } catch (Exception e) {
            System.err.println("[OptimizationManager ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Update optimization state
     */
    public void update(long deltaMillis) {
        if (!isInitialized) return;
        optimizationSystem.update(deltaMillis);
    }
    
    /**
     * Get current FPS
     */
    public int getCurrentFPS() {
        if (optimizationSystem != null) {
            return optimizationSystem.getCurrentFPS();
        }
        return 0;
    }
    
    /**
     * Get average FPS
     */
    public float getAverageFPS() {
        if (optimizationSystem != null) {
            return optimizationSystem.getAverageFPS();
        }
        return 0f;
    }
    
    /**
     * Get memory usage
     */
    public long getMemoryUsage() {
        if (optimizationSystem != null) {
            return optimizationSystem.getMemoryUsage();
        }
        return 0L;
    }
    
    /**
     * Get memory limit
     */
    public long getMemoryLimit() {
        if (optimizationSystem != null) {
            return optimizationSystem.getMemoryLimit();
        }
        return 0L;
    }
    
    /**
     * Force garbage collection
     */
    public void forceGarbageCollection() {
        System.gc();
    }
    
    /**
     * Enable performance monitoring
     */
    public void enablePerfMonitoring(boolean enabled) {
        if (optimizationSystem != null) {
            optimizationSystem.enablePerfMonitoring(enabled);
        }
    }
    
    /**
     * Get performance report
     */
    public String getPerformanceReport() {
        if (optimizationSystem != null) {
            return optimizationSystem.getPerformanceReport();
        }
        return "No data available";
    }
    
    /**
     * Shutdown optimization system
     */
    public void shutdown() {
        if (optimizationSystem != null) {
            optimizationSystem.shutdown();
        }
        isInitialized = false;
    }
}
