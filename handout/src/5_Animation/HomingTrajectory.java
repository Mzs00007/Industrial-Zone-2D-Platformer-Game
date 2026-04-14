/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.ProjectilePhysicsSystem.HomingTrajectory {
    public double velocityX;
    public double velocityY;
    public double speed = 8.0;
    public double maxTurnSpeed = 0.1;

    public AnimationAndSpriteLoader.ProjectilePhysicsSystem.HomingTrajectory(double d, double d2) {
        this.velocityX = d;
        this.velocityY = d2;
    }

    public void trackTarget(int n, int n2, int n3, int n4) {
        double d = n3 - n;
        double d2 = n4 - n2;
        double d3 = Math.sqrt(d * d + d2 * d2);
        if (d3 > 0.0) {
            double d4;
            double d5 = Math.atan2(this.velocityY, this.velocityX);
            double d6 = Math.atan2(d2 /= d3, d /= d3);
            for (d4 = d6 - d5; d4 > Math.PI; d4 -= Math.PI * 2) {
            }
            while (d4 < -Math.PI) {
                d4 += Math.PI * 2;
            }
            double d7 = Math.max(-this.maxTurnSpeed, Math.min(this.maxTurnSpeed, d4));
            double d8 = d5 + d7;
            this.velocityX = this.speed * Math.cos(d8);
            this.velocityY = this.speed * Math.sin(d8);
        }
    }
}
