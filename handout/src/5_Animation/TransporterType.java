/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static enum AnimationAndSpriteLoader.TransporterDroneLoader.TransporterType {
    HOVER_PLATFORM("Hover Platform - Player stands on top", 350.0f, "ON_TOP"),
    HELICOPTER("Helicopter - Player hangs from cable", 450.0f, "HANGING");

    public final String description;
    public final float defaultSpeed;
    public final String positionMode;

    private AnimationAndSpriteLoader.TransporterDroneLoader.TransporterType(String string2, float f, String string3) {
        this.description = string2;
        this.defaultSpeed = f;
        this.positionMode = string3;
    }
}
