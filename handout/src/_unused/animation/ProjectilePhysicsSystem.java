/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class ProjectilePhysicsSystem {
public class HomingTrajectory {
        public double velocityX;
        public double velocityY;
        public double speed = 8.0;
        public double maxTurnSpeed = 0.1;

        public HomingTrajectory(double d, double d2) {
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
public class ArcTrajectory {
        public double velocityX;
        public double velocityY;
        public double gravity = 0.3;

        public ArcTrajectory(double d, double d2) {
            this.velocityX = d;
            this.velocityY = d2;
        }

        public void update(int n, int n2) {
            this.velocityY += this.gravity;
            n += (int)this.velocityX;
            n2 += (int)this.velocityY;
        }
    }
public class StraightTrajectory {
        public double velocityX;
        public double velocityY;

        public StraightTrajectory(double d, double d2) {
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
}
