/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class DamageCalculationSystem {
    public static int calculateDamage(int n, double d, double d2, HitLocation hitLocation, DifficultyLevel difficultyLevel) {
        double d3 = (double)n * d * Math.max(0.25, d2) * hitLocation.damageMultiplier * difficultyLevel.damageMultiplier;
        return (int)d3;
    }

    public static double getDistanceFalloff(double d, double d2) {
        if (d >= d2) {
            return 0.0;
        }
        return 1.0 - Math.pow(d / d2, 2.0);
    }
public enum HitLocation {
        HEAD(1.5, "Critical"),
        TORSO(1.0, "Standard"),
        LIMB(0.75, "Reduced");

        public final double damageMultiplier;
        public final String description;

        private HitLocation(double d, String string2) {
            this.damageMultiplier = d;
            this.description = string2;
        }
    }
public enum DifficultyLevel {
        EASY(0.7),
        NORMAL(1.0),
        HARD(1.3),
        NIGHTMARE(1.7);

        public final double damageMultiplier;

        private DifficultyLevel(double d) {
            this.damageMultiplier = d;
        }
    }
}
