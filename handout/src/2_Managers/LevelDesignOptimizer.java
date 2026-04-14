/*
 * Decompiled with CFR 0.152.
 */
package managers;

public class LevelDesignOptimizer {
    private static final float EXCELLENT_THRESHOLD = 0.8f;
    private static final float GOOD_THRESHOLD = 0.6f;
    private static final float POOR_THRESHOLD = 0.3f;
    private int startingPlayerHP;
    private int currentPlayerHP;
    private float targetDifficulty = 1.0f;
    private float performanceRating = 0.5f;
    private int totalDamageTaken = 0;
    private int totalCombosCompleted = 0;
    private float averageComboDuration = 0.0f;
    private int checkpointUsageCount = 0;
    private int zoneCompletionCount = 0;
    private float currentLevelDifficulty = 1.0f;
    private float nextLevelDifficulty = 1.0f;
    private boolean shouldIncreaseDifficulty = false;
    private boolean shouldDecreaseDifficulty = false;

    public LevelDesignOptimizer(int n) {
        this.startingPlayerHP = n;
        this.currentPlayerHP = n;
    }

    public void updatePlayerHP(int n) {
        if (n < this.currentPlayerHP) {
            this.totalDamageTaken += this.currentPlayerHP - n;
        }
        this.currentPlayerHP = n;
    }

    public void recordCombo(float f, int n) {
        ++this.totalCombosCompleted;
        this.averageComboDuration = (this.averageComboDuration + f) / 2.0f;
    }

    public void recordCheckpointUsage() {
        ++this.checkpointUsageCount;
    }

    public void recordZoneCompletion(int n) {
        ++this.zoneCompletionCount;
    }

    public float calculatePerformanceRating() {
        int n = Math.round((float)this.startingPlayerHP * 0.3f);
        this.performanceRating = (float)this.totalDamageTaken <= (float)n * 0.5f ? 0.9f : (this.totalDamageTaken <= n ? 0.8f : ((float)this.totalDamageTaken <= (float)n * 1.5f ? 0.6f : ((float)this.totalDamageTaken <= (float)n * 2.0f ? 0.4f : 0.2f)));
        if (this.totalCombosCompleted > 5) {
            this.performanceRating += 0.1f;
        }
        if (this.checkpointUsageCount > 8) {
            this.performanceRating -= 0.15f;
        }
        return Math.max(0.1f, Math.min(1.0f, this.performanceRating));
    }

    public void analyzePerformance() {
        float f = this.calculatePerformanceRating();
        if (f >= 0.8f) {
            this.shouldIncreaseDifficulty = true;
            this.shouldDecreaseDifficulty = false;
            this.nextLevelDifficulty = this.currentLevelDifficulty * 1.15f;
        } else if (f >= 0.6f) {
            this.shouldIncreaseDifficulty = false;
            this.shouldDecreaseDifficulty = false;
            this.nextLevelDifficulty = this.currentLevelDifficulty;
        } else if (f >= 0.3f) {
            this.shouldIncreaseDifficulty = false;
            this.shouldDecreaseDifficulty = true;
            this.nextLevelDifficulty = this.currentLevelDifficulty * 0.85f;
        } else {
            this.shouldIncreaseDifficulty = false;
            this.shouldDecreaseDifficulty = true;
            this.nextLevelDifficulty = this.currentLevelDifficulty * 0.7f;
        }
    }

    public float getDifficultyAdjustment() {
        this.analyzePerformance();
        return this.nextLevelDifficulty / this.currentLevelDifficulty;
    }

    public float getEnemyHPModifier() {
        float f = this.calculatePerformanceRating();
        if (f >= 0.8f) {
            return 1.2f;
        }
        if (f >= 0.6f) {
            return 1.0f;
        }
        if (f >= 0.3f) {
            return 0.8f;
        }
        return 0.6f;
    }

    public float getEnemyDamageModifier() {
        float f = this.calculatePerformanceRating();
        if (f >= 0.8f) {
            return 1.1f;
        }
        if (f >= 0.6f) {
            return 1.0f;
        }
        if (f >= 0.3f) {
            return 0.85f;
        }
        return 0.7f;
    }

    public float getHazardIntensityModifier() {
        float f = this.calculatePerformanceRating();
        if (f >= 0.8f) {
            return 1.15f;
        }
        if (f >= 0.6f) {
            return 1.0f;
        }
        if (f >= 0.3f) {
            return 0.8f;
        }
        return 0.6f;
    }

    public boolean shouldAddExtraCheckpoint() {
        float f = this.calculatePerformanceRating();
        return f < 0.3f && this.checkpointUsageCount < 3;
    }

    public boolean shouldRemoveCheckpoint() {
        float f = this.calculatePerformanceRating();
        return f >= 0.8f && this.checkpointUsageCount == 0;
    }

    public int getRecommendedEnemyCount(int n) {
        float f = this.calculatePerformanceRating();
        if (f >= 0.8f) {
            return Math.round((float)n * 1.3f);
        }
        if (f >= 0.6f) {
            return n;
        }
        if (f >= 0.3f) {
            return Math.round((float)n * 0.7f);
        }
        return Math.round((float)n * 0.5f);
    }

    public int getRecommendedHazardCount(int n) {
        float f = this.calculatePerformanceRating();
        if (f >= 0.8f) {
            return Math.round((float)n * 1.2f);
        }
        if (f >= 0.6f) {
            return n;
        }
        if (f >= 0.3f) {
            return Math.round((float)n * 0.75f);
        }
        return Math.round((float)n * 0.5f);
    }

    public float getScoreMultiplier(float f) {
        float f2 = this.calculatePerformanceRating();
        if (this.currentLevelDifficulty > 1.2f) {
            return f * 1.25f;
        }
        if (this.currentLevelDifficulty > 1.0f) {
            return f * 1.1f;
        }
        if (this.currentLevelDifficulty < 0.8f) {
            return f * 0.9f;
        }
        return f;
    }

    public int getPerformanceBonus() {
        float f = this.calculatePerformanceRating();
        if (f >= 0.8f) {
            return 500;
        }
        if (f >= 0.6f) {
            return 200;
        }
        if (f >= 0.3f) {
            return 0;
        }
        return -100;
    }

    public String getPerformanceSummary() {
        float f = this.calculatePerformanceRating();
        if (f >= 0.8f) {
            return "Excellent - You dominated this level!";
        }
        if (f >= 0.6f) {
            return "Good - Great job!";
        }
        if (f >= 0.3f) {
            return "Fair - You're learning!";
        }
        return "Poor - Keep trying!";
    }

    public void reset() {
        this.totalDamageTaken = 0;
        this.totalCombosCompleted = 0;
        this.averageComboDuration = 0.0f;
        this.checkpointUsageCount = 0;
        this.zoneCompletionCount = 0;
        this.performanceRating = 0.5f;
        this.currentLevelDifficulty = this.nextLevelDifficulty;
    }

    public float getPerformanceRating() {
        return this.performanceRating;
    }

    public int getTotalDamageTaken() {
        return this.totalDamageTaken;
    }

    public int getTotalCombosCompleted() {
        return this.totalCombosCompleted;
    }

    public int getCheckpointUsageCount() {
        return this.checkpointUsageCount;
    }

    public float getCurrentDifficulty() {
        return this.currentLevelDifficulty;
    }

    public float getNextDifficulty() {
        return this.nextLevelDifficulty;
    }

    public boolean shouldIncreaseDifficulty() {
        return this.shouldIncreaseDifficulty;
    }

    public boolean shouldDecreaseDifficulty() {
        return this.shouldDecreaseDifficulty;
    }
}
