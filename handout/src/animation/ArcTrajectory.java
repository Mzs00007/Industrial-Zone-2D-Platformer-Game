/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.ProjectilePhysicsSystem.ArcTrajectory {
    public double velocityX;
    public double velocityY;
    public double gravity = 0.3;

    public AnimationAndSpriteLoader.ProjectilePhysicsSystem.ArcTrajectory(double d, double d2) {
        this.velocityX = d;
        this.velocityY = d2;
    }

    public void update(int n, int n2) {
        this.velocityY += this.gravity;
        n += (int)this.velocityX;
        n2 += (int)this.velocityY;
    }
}
