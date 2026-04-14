/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static enum AnimationAndSpriteLoader.TracerEffectSystem.TracerType {
    STANDARD_A("Narrow"),
    STANDARD_SCATTER("Scatter"),
    DOTTED("Dotted"),
    SLASH("Slash"),
    HEAVY("Heavy"),
    BOLD("Bold"),
    WAVE("Wave"),
    JAGGED("Jagged"),
    LASER("Laser"),
    OUTLINE("Outline");

    public final String style;

    private AnimationAndSpriteLoader.TracerEffectSystem.TracerType(String string2) {
        this.style = string2;
    }
}
