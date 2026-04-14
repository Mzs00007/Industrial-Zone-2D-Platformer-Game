/*
 * Decompiled with CFR 0.152.
 */
package managers;

public static class Core.GameplayEnhancementSystem {
    public static final int DIFFICULTY_EASY = 1;
    public static final int DIFFICULTY_NORMAL = 2;
    public static final int DIFFICULTY_HARD = 3;
    private int currentDifficulty = 2;

    public void setDifficulty(int n) {
        this.currentDifficulty = n;
    }

    public int getDifficulty() {
        return this.currentDifficulty;
    }

    public float getDamageMultiplier() {
        return 1.0f;
    }

    public float getHealthMultiplier() {
        return 1.0f;
    }
}
