/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.ProjectilePhysicsSystem.StraightTrajectory {
    public double velocityX;
    public double velocityY;

    public AnimationAndSpriteLoader.ProjectilePhysicsSystem.StraightTrajectory(double d, double d2) {
        this.velocityX = d;
        this.velocityY = d2;
    }

    public void update(int n, int n2) {
        n += (int)this.velocityX;
        n2 += (int)this.velocityY;
    }

    public double getSpeed() {
        return Math.sqrt(this.velocityX * this.velocityX + this.velocityY * this.velocityY);
    }
}
