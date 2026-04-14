/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.GUIAnimationPattern {
    public final String filename;
    public final int frameCount;
    public final int timingMs;
    public final boolean looping;
    public final String description;

    public AnimationAndSpriteLoader.GUIAnimationPattern(String string, int n, int n2, boolean bl, String string2) {
        this.filename = string;
        this.frameCount = n;
        this.timingMs = n2;
        this.looping = bl;
        this.description = string2;
    }
}
