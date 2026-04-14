/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static enum AnimationAndSpriteLoader.TransporterPathLoader.PathType {
    LINEAR("Straight line movement"),
    CURVED("Smooth bezier curve path"),
    PARABOLIC("Arc-shaped movement");

    public final String description;

    private AnimationAndSpriteLoader.TransporterPathLoader.PathType(String string2) {
        this.description = string2;
    }
}
