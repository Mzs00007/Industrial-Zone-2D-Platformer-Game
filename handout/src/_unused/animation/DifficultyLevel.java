/*
 * Decompiled with CFR 0.152.
 */
package animation;
public enum DifficultyLevel {
    EASY(0.7),
    NORMAL(1.0),
    HARD(1.3),
    NIGHTMARE(1.7);

    public final double damageMultiplier;

    private DifficultyLevel(double d) {
        this.damageMultiplier = d;
    }
}
