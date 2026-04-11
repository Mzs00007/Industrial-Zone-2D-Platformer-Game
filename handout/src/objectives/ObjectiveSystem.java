package objectives;

import levels.LevelSystem;

import java.util.*;

/**
 * ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *                              UNIFIED OBJECTIVE SYSTEM
 *                   Complete Quest & Mission Architecture - All Objective Classes in One
 * ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *
 * RASTER GRAPHICS ONLY - PNG IMAGE ASSETS ONLY
 * ═══════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *
 * UPDATE (April 4, 2026 - UNIFIED OBJECTIVE ARCHITECTURE):
 * ─────────────────────────────────────────────────────────────────────────────────────────────────────────────
 * • Consolidated all objective/quest-related classes into single unified system
 * • Organized as nested static classes for clean architecture
 * • Zero code duplication through inheritance from AnimationAndSpriteLoader
 *
 * CONSOLIDATED FROM:
 * ├─ objectives/Objective.java → ObjectiveSystem.Objective
 * ├─ objectives/ObjectiveManager.java → ObjectiveSystem.ObjectiveManager
 * ├─ objectives/KillTargetObjective.java → ObjectiveSystem.KillTargetObjective
 * └─ objectives/CollectObjective.java → ObjectiveSystem.CollectObjective
 *
 * NESTED STATIC CLASSES INDEX:
 * ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════
 *
 * [1] Objective (LINE ~50)
 *     - Role: Base objective class
 *     - Fields: id, description, status, progress
 *     - Methods: update(), isComplete(), reset()
 *     - API: ObjectiveSystem.Objective obj = new ObjectiveSystem.Objective("OBJ001", "Collect items");
 *
 * [2] KillTargetObjective (LINE ~110)
 *     - Role: Kill all enemies objective
 *     - Fields: targetCount, killCount, targetName
 *     - Methods: recordKill(), isComplete(), getProgress()
 *     - API: ObjectiveSystem.KillTargetObjective kill = new KillTargetObjective("Drones", 5);
 *
 * [3] CollectObjective (LINE ~170)
 *     - Role: Collect items objective
 *     - Fields: requiredCount, collectedCount, itemType
 *     - Methods: recordCollection(), isComplete(), getProgress()
 *     - API: ObjectiveSystem.CollectObjective collect = new CollectObjective("Keys", 3);
 *
 * [4] ObjectiveTracker (LINE ~230)
 *     - Role: Track active objectives
 *     - Methods: addObjective(), completeObjective(), getObjectives(), reset()
 *     - API: ObjectiveSystem.ObjectiveTracker tracker = new ObjectiveSystem.ObjectiveTracker();
 *
 * [5] ObjectiveMarker (LINE ~290)
 *     - Role: UI marker for objectives on screen
 *     - Fields: x, y, targetX, targetY, isVisible
 *     - Methods: setPosition(), setTargetPosition(), getScreenX(), getScreenY()
 *     - API: ObjectiveSystem.ObjectiveMarker marker = new ObjectiveSystem.ObjectiveMarker();
 *
 * @author 3359098
 * @version 1.0 - Unified objective architecture
 * @since 2026-04-04
 */
public class ObjectiveSystem extends config.Config {
    private static final String TAG = "[ObjectiveSystem]";
    private static final boolean VERBOSE_LOGGING = true;
    
    /**
     * Constructor: Initialize ObjectiveSystem
     * Inherits from LevelSystem + adds mission/objective tracking and management
     */
    public ObjectiveSystem() {
        super();  // Initialize LevelSystem (which initializes UISystem → RenderingSystem → CoreSystem → PhysicsSystem)
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // Objective - Base objective class
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Base class for all objectives
     */
    public static class Objective {
        public String id;
        public String description;
        public boolean isComplete;
        public float progress;
        
        public Objective(String id, String description) {
            this.id = id;
            this.description = description;
            this.isComplete = false;
            this.progress = 0f;
        }
        
        public void update() {
            // Override in subclasses
        }
        
        public boolean isComplete() {
            return isComplete;
        }
        
        public void reset() {
            this.isComplete = false;
            this.progress = 0f;
        }
        
        public void setProgress(float p) {
            this.progress = Math.max(0, Math.min(1, p));
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // KillTargetObjective - Kill enemies objective
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Objective: Kill a certain number of targets
     */
    public static class KillTargetObjective extends Objective {
        public int targetCount;
        public int killCount;
        public String targetName;
        
        public KillTargetObjective(String targetName, int count) {
            super("KILL_" + targetName.toUpperCase(), "Kill " + count + " " + targetName);
            this.targetName = targetName;
            this.targetCount = count;
            this.killCount = 0;
        }
        
        public void recordKill() {
            killCount++;
            this.progress = (float) killCount / targetCount;
            if (killCount >= targetCount) {
                this.isComplete = true;
                log("Completed: Kill " + killCount + "/" + targetCount + " " + targetName);
            } else {
                log("Kill progress: " + killCount + "/" + targetCount);
            }
        }
        
        public int getKillCount() {
            return killCount;
        }
        
        public int getTargetCount() {
            return targetCount;
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // CollectObjective - Collect items objective
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Objective: Collect items
     */
    public static class CollectObjective extends Objective {
        public int requiredCount;
        public int collectedCount;
        public String itemType;
        
        public CollectObjective(String itemType, int count) {
            super("COLLECT_" + itemType.toUpperCase(), "Collect " + count + " " + itemType);
            this.itemType = itemType;
            this.requiredCount = count;
            this.collectedCount = 0;
        }
        
        public void recordCollection() {
            collectedCount++;
            this.progress = (float) collectedCount / requiredCount;
            if (collectedCount >= requiredCount) {
                this.isComplete = true;
                log("Completed: Collect " + collectedCount + "/" + requiredCount + " " + itemType);
            } else {
                log("Collection progress: " + collectedCount + "/" + requiredCount);
            }
        }
        
        public int getCollectedCount() {
            return collectedCount;
        }
        
        public int getRequiredCount() {
            return requiredCount;
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // ObjectiveTracker - Manage active objectives
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Tracks all active objectives
     */
    public static class ObjectiveTracker {
        private List<Objective> objectives = new ArrayList<>();
        private Set<String> completedObjectives = new HashSet<>();
        
        public void addObjective(Objective obj) {
            objectives.add(obj);
            log("Added objective: " + obj.description);
        }
        
        public void completeObjective(String objId) {
            for (Objective obj : objectives) {
                if (obj.id.equals(objId)) {
                    obj.isComplete = true;
                    completedObjectives.add(objId);
                    log("Completed objective: " + objId);
                    return;
                }
            }
        }
        
        public Objective getObjective(String objId) {
            for (Objective obj : objectives) {
                if (obj.id.equals(objId)) return obj;
            }
            return null;
        }
        
        public List<Objective> getActiveObjectives() {
            List<Objective> active = new ArrayList<>();
            for (Objective obj : objectives) {
                if (!obj.isComplete) active.add(obj);
            }
            return active;
        }
        
        public List<Objective> getCompletedObjectives() {
            List<Objective> completed = new ArrayList<>();
            for (Objective obj : objectives) {
                if (obj.isComplete) completed.add(obj);
            }
            return completed;
        }
        
        public void reset() {
            objectives.clear();
            completedObjectives.clear();
        }
        
        public int getObjectiveCount() {
            return objectives.size();
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // ObjectiveMarker - UI indicator
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * On-screen marker for objective location
     */
    public static class ObjectiveMarker {
        public float x, y;
        public float targetX, targetY;
        public boolean isVisible;
        public String objectiveId;
        
        public ObjectiveMarker(String objId) {
            this.objectiveId = objId;
            this.isVisible = true;
        }
        
        public void setPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }
        
        public void setTargetPosition(float tx, float ty) {
            this.targetX = tx;
            this.targetY = ty;
        }
        
        public float getScreenX() {
            return x;
        }
        
        public float getScreenY() {
            return y;
        }
    }
    
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    // UTILITY METHODS
    // ═════════════════════════════════════════════════════════════════════════════════════════════════════════════
    
    protected static void log(String message) {
        if (VERBOSE_LOGGING) {
            System.out.println(TAG + " " + message);
        }
    }
}
