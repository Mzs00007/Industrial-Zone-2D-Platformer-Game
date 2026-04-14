/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static enum AnimationAndSpriteLoader.WeaponSystemCore.GunType {
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

    private AnimationAndSpriteLoader.WeaponSystemCore.GunType(int n2, String string2, int n3, double d, double d2) {
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
