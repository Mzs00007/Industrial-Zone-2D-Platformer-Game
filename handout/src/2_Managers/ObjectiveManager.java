package managers;

import objectives.ObjectiveSystem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ObjectiveManager - Consolidated objective and quest system
 * 
 * Merges:
 * - objectives/ObjectiveSystem.java
 * 
 * Manages player objectives, quests, and mission tracking.
 */
public class ObjectiveManager {
    private ObjectiveSystem objectiveSystem;
    private boolean isInitialized = false;
    
    /**
     * Constructor - instantiable manager (no static getInstance)
     */
    public ObjectiveManager() {
        this.objectiveSystem = new ObjectiveSystem();
    }
    
    /**
     * Initialize objective system
     */
    public void initialize() {
        if (isInitialized) return;
        try {
            System.out.println("[ObjectiveManager] Initializing objective system...");
            objectiveSystem.initialize();
            isInitialized = true;
            System.out.println("[ObjectiveManager] ✓ Objective system initialized");
        } catch (Exception e) {
            System.err.println("[ObjectiveManager ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Update objective state
     */
    public void update(long deltaMillis) {
        if (!isInitialized) return;
        objectiveSystem.update(deltaMillis);
    }
    
    /**
     * Add new objective
     */
    public void addObjective(Object objective) {
        if (objectiveSystem != null) {
            objectiveSystem.addObjective(objective);
        }
    }
    
    /**
     * Complete objective
     */
    public void completeObjective(String objectiveId) {
        if (objectiveSystem != null) {
            objectiveSystem.completeObjective(objectiveId);
        }
    }
    
    /**
     * Get active objectives
     */
    public List<Object> getActiveObjectives() {
        if (objectiveSystem != null) {
            return objectiveSystem.getActiveObjectives();
        }
        return new java.util.ArrayList<>();
    }
    
    /**
     * Get completed objectives
     */
    public List<Object> getCompletedObjectives() {
        if (objectiveSystem != null) {
            return objectiveSystem.getCompletedObjectives();
        }
        return new java.util.ArrayList<>();
    }
    
    /**
     * Check if objective is completed
     */
    public boolean isObjectiveCompleted(String objectiveId) {
        if (objectiveSystem != null) {
            return objectiveSystem.isObjectiveCompleted(objectiveId);
        }
        return false;
    }
    
    /**
     * Track quest progress
     */
    public void updateQuestProgress(String questId, float progress) {
        if (objectiveSystem != null) {
            objectiveSystem.updateQuestProgress(questId, progress);
        }
    }
    
    /**
     * Get quest progress
     */
    public float getQuestProgress(String questId) {
        if (objectiveSystem != null) {
            return objectiveSystem.getQuestProgress(questId);
        }
        return 0f;
    }
    
    /**
     * Shutdown objective system
     */
    public void shutdown() {
        if (objectiveSystem != null) {
            objectiveSystem.shutdown();
        }
        isInitialized = false;
    }
}
