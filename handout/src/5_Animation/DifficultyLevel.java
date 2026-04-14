/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static enum AnimationAndSpriteLoader.DamageCalculationSystem.DifficultyLevel {
    EASY(0.7),
    NORMAL(1.0),
    HARD(1.3),
    NIGHTMARE(1.7);

    public final double damageMultiplier;

    private AnimationAndSpriteLoader.DamageCalculationSystem.DifficultyLevel(double d) {
        this.damageMultiplier = d;
    }
}
