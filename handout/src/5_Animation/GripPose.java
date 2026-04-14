/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static enum AnimationAndSpriteLoader.WeaponSystemCore.GripPose {
    HORIZONTAL(0, "Horizontal", 35, 15),
    DIAGONAL_DOWN(1, "DiagonalDown", 30, 25),
    VERTICAL_UP(2, "VerticalUp", 28, 5),
    VERTICAL_ALT(3, "VerticalAlt", 28, 8),
    ANGLE_DOWN(4, "AngleDown", 32, 20),
    LOW_GRIP(5, "LowGrip", 25, 35),
    ANGLE_UP_LEFT(6, "AngleUpLeft", 20, 5),
    VERTICAL_B(7, "VerticalB", 28, -5),
    HORIZONTAL_B(8, "HorizontalB", 38, 12),
    DIAGONAL_LONG(9, "DiagonalLong", 40, 15);

    public final int index;
    public final String name;
    public final int muzzleOffsetX;
    public final int muzzleOffsetY;

    private AnimationAndSpriteLoader.WeaponSystemCore.GripPose(int n2, String string2, int n3, int n4) {
        this.index = n2;
        this.name = string2;
        this.muzzleOffsetX = n3;
        this.muzzleOffsetY = n4;
    }
}
