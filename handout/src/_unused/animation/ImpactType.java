/*
 * Decompiled with CFR 0.152.
 */
package animation;
public enum ImpactType {
    SPLAT("Blood splatter effects"),
    EXPLOSION("Area damage explosion"),
    RICOCHET("Bounce off surfaces"),
    SCREEN_SHAKE("Camera shake effect");

    public final String description;

    private ImpactType(String string2) {
        this.description = string2;
    }
}
