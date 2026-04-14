/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.ImpactEffectSystem {
    public static double[] calculateKnockback(int n, int n2, int n3, int n4, double d, double d2) {
        double d3 = d / d2;
        double d4 = n3 - n;
        double d5 = n4 - n2;
        double d6 = Math.sqrt(d4 * d4 + d5 * d5);
        if (d6 > 0.0) {
            d4 /= d6;
            d5 /= d6;
        }
        return new double[]{d4 * d3, d5 * d3};
    }

    public static int[] getExplosionDamageInRadius(int n, int n2, int n3, int n4) {
        return new int[0];
    }

    public static enum ImpactType {
        SPLAT("Blood splatter effects"),
        EXPLOSION("Area damage explosion"),
        RICOCHET("Bounce off surfaces"),
        SCREEN_SHAKE("Camera shake effect");

        public final String description;

        private ImpactType(String string2) {
            this.description = string2;
        }
    }
}
