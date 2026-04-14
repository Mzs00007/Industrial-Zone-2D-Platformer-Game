/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class PlayerCharacterAnimations.AnimationConfig {
    public final int frameCount;
    public final int timingMs;
    public final String description;

    public PlayerCharacterAnimations.AnimationConfig(int n, int n2, String string) {
        this.frameCount = n;
        this.timingMs = n2;
        this.description = string;
    }

    public String toString() {
        return this.frameCount + " frames @ " + this.timingMs + "ms - " + this.description;
    }
}
