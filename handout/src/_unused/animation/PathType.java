/*
 * Decompiled with CFR 0.152.
 */
package animation;
public enum PathType {
    LINEAR("Straight line movement"),
    CURVED("Smooth bezier curve path"),
    PARABOLIC("Arc-shaped movement");

    public final String description;

    private PathType(String string2) {
        this.description = string2;
    }
}
