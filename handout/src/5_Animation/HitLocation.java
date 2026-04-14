/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static enum AnimationAndSpriteLoader.DamageCalculationSystem.HitLocation {
    HEAD(1.5, "Critical"),
    TORSO(1.0, "Standard"),
    LIMB(0.75, "Reduced");

    public final double damageMultiplier;
    public final String description;

    private AnimationAndSpriteLoader.DamageCalculationSystem.HitLocation(double d, String string2) {
        this.damageMultiplier = d;
        this.description = string2;
    }
}
