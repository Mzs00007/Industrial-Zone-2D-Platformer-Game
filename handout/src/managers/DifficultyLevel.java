/*
 * Decompiled with CFR 0.152.
 */
package managers;
enum DifficultyLevel {
    EASY(0),
    NORMAL(1),
    HARD(2),
    EXPERT(3);

    public final int index;

    private DifficultyLevel(int n2) {
        this.index = n2;
    }
}
