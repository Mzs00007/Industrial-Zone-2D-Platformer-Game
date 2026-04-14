/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static enum AnimationAndSpriteLoader.ImpactEffectSystem.ImpactType {
    SPLAT("Blood splatter effects"),
    EXPLOSION("Area damage explosion"),
    RICOCHET("Bounce off surfaces"),
    SCREEN_SHAKE("Camera shake effect");

    public final String description;

    private AnimationAndSpriteLoader.ImpactEffectSystem.ImpactType(String string2) {
        this.description = string2;
    }
}
