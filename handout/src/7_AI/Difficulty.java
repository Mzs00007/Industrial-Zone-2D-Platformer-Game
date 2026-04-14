/*
 * Decompiled with CFR 0.152.
 */
package ai;

public static enum AI.AIBehaviorSystem.Difficulty {
    EASY(0.5f, 200.0f, 5000L),
    NORMAL(0.75f, 300.0f, 3000L),
    HARD(0.95f, 500.0f, 1000L);

    public final float accuracy;
    public final float detectionRange;
    public final long reactionTimeMs;

    private AI.AIBehaviorSystem.Difficulty(float f, float f2, long l) {
        this.accuracy = f;
        this.detectionRange = f2;
        this.reactionTimeMs = l;
    }
}
