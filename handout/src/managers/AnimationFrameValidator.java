package managers;

import java.util.HashMap;
import java.util.Map;

/**
 * AnimationFrameValidator — Ensures all sprite sheets have EVEN frame counts
 * 
 * Purpose: Proper animation cycling and state management require even frame counts
 * - 2, 4, 6, 8, 10, 12 frames are valid
 * - 3, 5, 7, 9, 11 frames should be adjusted
 * 
 * Provides validation and adjustment helpers for all animation states.
 */
public class AnimationFrameValidator {
    
    private static final Map<String, Integer> frameCountAdjustments = new HashMap<>();
    
    static {
        // Initialize even frame counts for all animation states
        // IDLE animations: 4 frames (150ms/frame)
        frameCountAdjustments.put("IDLE", 4);
        frameCountAdjustments.put("IDLE2", 4);
        
        // WALK/RUN animations: 6-8 frames
        frameCountAdjustments.put("WALK", 6);
        frameCountAdjustments.put("RUN", 8);
        frameCountAdjustments.put("DASH", 6);
        
        // JUMP/FALL animations: 4-6 frames
        frameCountAdjustments.put("JUMP", 6);
        frameCountAdjustments.put("DOUBLE_JUMP", 6);
        frameCountAdjustments.put("FALL", 4);
        frameCountAdjustments.put("CLIMB", 6);
        frameCountAdjustments.put("HANG", 4);
        frameCountAdjustments.put("PULLUP", 6);
        
        // ATTACK animations: 6-8 frames
        frameCountAdjustments.put("PUNCH", 6);
        frameCountAdjustments.put("ATTACK1", 8);
        frameCountAdjustments.put("ATTACK2", 8);
        frameCountAdjustments.put("ATTACK3", 8);
        frameCountAdjustments.put("WALK_ATTACK", 8);
        frameCountAdjustments.put("RUN_ATTACK", 8);
        
        // HIT/DEATH animations: 4-6 frames
        frameCountAdjustments.put("HIT", 4);
        frameCountAdjustments.put("DEATH", 6);
        
        // EXPRESSION animations: 4 frames
        frameCountAdjustments.put("USE", 4);
        frameCountAdjustments.put("SITDOWN", 4);
        frameCountAdjustments.put("ANGRY", 4);
        frameCountAdjustments.put("HAPPY", 4);
        frameCountAdjustments.put("TALK", 4);
        
        // Enemy animations
        frameCountAdjustments.put("ENEMY_IDLE", 4);
        frameCountAdjustments.put("ENEMY_WALK", 6);
        frameCountAdjustments.put("ENEMY_ATTACK", 6);
        frameCountAdjustments.put("ENEMY_HURT", 4);
        frameCountAdjustments.put("ENEMY_DEATH", 6);
        
        // VFX animations
        frameCountAdjustments.put("VFX_BLOOD", 4);
        frameCountAdjustments.put("VFX_SMOKE", 6);
        frameCountAdjustments.put("VFX_SPARK", 8);
    }
    
    /**
     * Validate frame count is even
     */
    public static boolean isValidFrameCount(int frameCount) {
        return frameCount > 0 && frameCount % 2 == 0;
    }
    
    /**
     * Adjust odd frame count to nearest even
     * 3 → 4, 5 → 6, 7 → 8, etc.
     */
    public static int adjustToEvenFrames(int frameCount) {
        if (frameCount <= 0) return 4;  // Default to 4
        if (isValidFrameCount(frameCount)) return frameCount;  // Already even
        return frameCount + 1;  // Make even
    }
    
    /**
     * Get recommended frame count for animation state
     */
    public static int getRecommendedFrameCount(String animationState) {
        return frameCountAdjustments.getOrDefault(animationState, 4);
    }
    
    /**
     * Validate entire animation set
     */
    public static ValidationReport validateAnimationSet(Map<String, Integer> animations) {
        ValidationReport report = new ValidationReport();
        
        for (Map.Entry<String, Integer> entry : animations.entrySet()) {
            String stateName = entry.getKey();
            int frameCount = entry.getValue();
            
            if (!isValidFrameCount(frameCount)) {
                report.addIssue(stateName, frameCount, adjustToEvenFrames(frameCount));
            }
        }
        
        return report;
    }
    
    /**
     * Timing calculation based on frame count and animation speed
     */
    public static int calculateFrameDurationMs(int frameCount, String animSpeed) {
        int baseDuration = switch (animSpeed.toUpperCase()) {
            case "SLOW" -> 150;      // Idle
            case "NORMAL" -> 100;    // Walk
            case "FAST" -> 80;       // Run
            case "INSTANT" -> 60;    // Attack
            default -> 100;
        };
        
        // Adjust for frame count
        // More frames = shorter per-frame duration
        return Math.max(40, baseDuration * 4 / frameCount);
    }
    
    /**
     * Report for animation validation
     */
    public static class ValidationReport {
        public Map<String, FrameIssue> issues = new HashMap<>();
        
        public void addIssue(String state, int current, int recommended) {
            issues.put(state, new FrameIssue(state, current, recommended));
        }
        
        public boolean isValid() {
            return issues.isEmpty();
        }
        
        public void printReport() {
            if (isValid()) {
                System.out.println("[AnimationFrameValidator] ✓ All animations have even frame counts");
                return;
            }
            
            System.out.println("[AnimationFrameValidator] ✗ Animation issues found:");
            for (FrameIssue issue : issues.values()) {
                System.out.println("  - " + issue.stateName + ": " + issue.current + 
                                 " frames (should be " + issue.recommended + ")");
            }
        }
    }
    
    public static class FrameIssue {
        public String stateName;
        public int current;
        public int recommended;
        
        public FrameIssue(String state, int current, int recommended) {
            this.stateName = state;
            this.current = current;
            this.recommended = recommended;
        }
    }
}
