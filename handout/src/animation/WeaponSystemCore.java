/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class WeaponSystemCore {
public enum PlayerCharacter {
        BIKER("Biker", 30),
        PUNK("Punk", 25),
        CYBORG("Cyborg", 28);

        public final String name;
        public final int baseHealth;

        private PlayerCharacter(String string2, int n2) {
            this.name = string2;
            this.baseHealth = n2;
        }
    }
public enum GripPose {
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

        private GripPose(int n2, String string2, int n3, int n4) {
            this.index = n2;
            this.name = string2;
            this.muzzleOffsetX = n3;
            this.muzzleOffsetY = n4;
        }
    }
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
public enum GunType {
        PISTOL_A(1, "Pistol", 15, 2.0, 8.0),
        PISTOL_B(2, "Pistol", 15, 2.0, 8.0),
        COMPACT_C(3, "Compact", 12, 3.0, 12.0),
        COMPACT_D(4, "Compact", 12, 3.0, 12.0),
        COMPACT_E(5, "Compact", 12, 3.0, 12.0),
        DETAIL_F(6, "Detail", 18, 1.8, 9.0),
        RIFLE_G(7, "Rifle", 25, 1.0, 14.0),
        RIFLE_H(8, "Rifle", 25, 1.0, 14.0),
        SCIFI_I(9, "Sci-Fi", 20, 1.0, 16.0),
        SPECIAL_J(10, "Special", 35, 0.5, 10.0);

        public final int typeIndex;
        public final String className;
        public final int baseDamage;
        public final double fireRatePerSec;
        public final double projectileVelocity;

        private GunType(int n2, String string2, int n3, double d, double d2) {
            this.typeIndex = n2;
            this.className = string2;
            this.baseDamage = n3;
            this.fireRatePerSec = d;
            this.projectileVelocity = d2;
        }

        public String getGunClass() {
            return "TypeGun" + this.className;
        }
    }
}
