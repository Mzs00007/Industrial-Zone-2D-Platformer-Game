/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static enum AnimationAndSpriteLoader.InteractionZoneLoader.ZoneShape {
    CIRCLE("Circular interaction zone"),
    RECTANGLE("Rectangular interaction zone"),
    POLYGON("Complex polygon zone");

    public final String description;

    private AnimationAndSpriteLoader.InteractionZoneLoader.ZoneShape(String string2) {
        this.description = string2;
    }
}
