/*
 * Decompiled with CFR 0.152.
 */
package animation;
public enum TrajectoryType {
    STRAIGHT("Straight", 0.0, 0),
    ARC("Arc", 0.3, 100),
    HOMING("Homing", 0.0, 0),
    CURVED("Curved", 0.0, 50);

    public final String name;
    public final double gravity;
    public final int turningSpeed;

    private TrajectoryType(String string2, double d, int n2) {
        this.name = string2;
        this.gravity = d;
        this.turningSpeed = n2;
    }
}
