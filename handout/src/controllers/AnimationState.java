/*
 * Decompiled with CFR 0.152.
 */
package controllers;

public enum AnimationState {
    IDLE(4, 150),
    WALK(6, 100),
    ATTACK(5, 70),
    JUMP(4, 80),
    DOUBLE_JUMP(6, 80),
    FALL(4, 100),
    DASH(6, 60),
    CLIMB(6, 120),
    HANG(3, 150);

    public final int frameCount;
    public final int durationPerFrameMs;

    private AnimationState(int n2, int n3) {
        this.frameCount = n2;
        this.durationPerFrameMs = n3;
    }
}
