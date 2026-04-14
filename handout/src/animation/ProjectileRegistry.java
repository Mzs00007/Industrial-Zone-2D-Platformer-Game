/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.ProjectileRegistry {
    static BulletProperties[] BULLET_TYPES = new BulletProperties[]{new BulletProperties('A', "Single", 1.0, 0, false), new BulletProperties('B', "Single", 1.0, 0, false), new BulletProperties('C', "Single", 1.0, 0, false), new BulletProperties('D', "VariantA", 1.1, 0, false), new BulletProperties('E', "VariantA", 1.1, 0, false), new BulletProperties('F', "Single", 1.0, 0, false), new BulletProperties('G', "VariantA", 1.3, 1, true), new BulletProperties('H', "Single", 1.2, 0, false), new BulletProperties('I', "Single", 1.2, 2, true), new BulletProperties('J', "Single", 1.5, 0, false)};

    public static BulletProperties getBulletProperties(char c) {
        int n = c - 65;
        if (n >= 0 && n < BULLET_TYPES.length) {
            return BULLET_TYPES[n];
        }
        return BULLET_TYPES[0];
    }

    public static class BulletProperties {
        public char bulletType;
        public String variant;
        public double baseDamageMultiplier;
        public int maxPenetrations;
        public boolean isPenetrating;

        public BulletProperties(char c, String string, double d, int n, boolean bl) {
            this.bulletType = c;
            this.variant = string;
            this.baseDamageMultiplier = d;
            this.maxPenetrations = n;
            this.isPenetrating = bl;
        }
    }
}
