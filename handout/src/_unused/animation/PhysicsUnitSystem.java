/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class PhysicsUnitSystem {
    public static final float PIXELS_PER_METER = 32.0f;
    public static final float METERS_PER_PIXEL = 0.03125f;
    public static final float TILE_SIZE_METERS = 1.0f;
    public static final float TILE_SIZE_PIXELS = 32.0f;
    public static final float GRAVITY = -9.81f;
    public static final float TIME_STEP = 0.016666668f;
    public static final float LINEAR_DAMPING = 0.85f;
    public static final float ANGULAR_DAMPING = 0.1f;
    public static final float AIR_DAMPING = 0.15f;
    public static final float STANDARD_JUMP_HEIGHT = 2.0f;
    public static final float STANDARD_JUMP_VELOCITY = PhysicsUnitSystem.jumpVelocity(2.0f);
    public static final float SMALL_JUMP_HEIGHT = 0.75f;
    public static final float SMALL_JUMP_VELOCITY = PhysicsUnitSystem.jumpVelocity(0.75f);
    public static final float HIGH_JUMP_HEIGHT = 4.0f;
    public static final float HIGH_JUMP_VELOCITY = PhysicsUnitSystem.jumpVelocity(4.0f);

    public static float toMeters(float f) {
        return f * 0.03125f;
    }

    public static float toPixels(float f) {
        return f * 32.0f;
    }

    public static float jumpVelocity(float f) {
        float f2 = Math.abs(-9.81f);
        return -((float)Math.sqrt(2.0f * f2 * f));
    }

    public static float distance(float f, float f2, float f3) {
        return f * f3 + 0.5f * f2 * f3 * f3;
    }

    public static float fallTime(float f) {
        float f2 = Math.abs(-9.81f);
        return (float)Math.sqrt(2.0f * f / f2);
    }

    public static float impactVelocity(float f) {
        float f2 = Math.abs(-9.81f);
        return -((float)Math.sqrt(2.0f * f2 * f));
    }
public class PhysicsBody {
        public Vector2D position;
        public Vector2D velocity;
        public Vector2D acceleration;
        public Vector2D forces;
        public float mass;
        public float radius;
        public boolean isGrounded;
        public boolean isAffectedByGravity;

        public PhysicsBody(float f, float f2, float f3, float f4) {
            this.position = new Vector2D(f, f2);
            this.velocity = new Vector2D();
            this.acceleration = new Vector2D();
            this.forces = new Vector2D();
            this.mass = f3 > 0.0f ? f3 : 1.0f;
            this.radius = f4;
            this.isGrounded = false;
            this.isAffectedByGravity = true;
        }

        public void applyForce(float f, float f2) {
            this.forces.x += f;
            this.forces.y += f2;
        }

        public void applyForce(Vector2D vector2D) {
            this.forces.add(vector2D);
        }

        public void clearForces() {
            this.forces.x = 0.0f;
            this.forces.y = 0.0f;
        }

        public void setMaxVelocity(float f) {
            float f2 = this.velocity.magnitude();
            if (f2 > f) {
                this.velocity.multiply(f / f2);
            }
        }

        public void applyDamping(float f, float f2) {
            float f3 = 1.0f - f * f2;
            f3 = Math.max(0.0f, f3);
            this.velocity.multiply(f3);
        }

        public void update(float f) {
            if (f <= 0.0f) {
                return;
            }
            if (this.isAffectedByGravity && !this.isGrounded) {
                this.forces.y += this.mass * -9.81f;
            }
            this.acceleration.x = this.forces.x / this.mass;
            this.acceleration.y = this.forces.y / this.mass;
            this.velocity.x += this.acceleration.x * f;
            this.velocity.y += this.acceleration.y * f;
            if (!this.isGrounded) {
                this.applyDamping(0.15f, f);
            } else {
                this.applyDamping(0.85f, f);
            }
            this.position.x += this.velocity.x * f;
            this.position.y += this.velocity.y * f;
            this.clearForces();
        }

        public float getLeft() {
            return this.position.x - this.radius;
        }

        public float getRight() {
            return this.position.x + this.radius;
        }

        public float getTop() {
            return this.position.y - this.radius;
        }

        public float getBottom() {
            return this.position.y + this.radius;
        }

        public boolean collidesWithAABB(PhysicsBody physicsBody) {
            return !(this.getRight() < physicsBody.getLeft() || this.getLeft() > physicsBody.getRight() || this.getBottom() < physicsBody.getTop() || this.getTop() > physicsBody.getBottom());
        }

        public float getScreenX() {
            return this.position.x * 32.0f;
        }

        public float getScreenY() {
            return this.position.y * 32.0f;
        }

        public String toString() {
            return String.format("PhysicsBody{pos:%s, vel:%s, mass:%.1fkg, radius:%.2fm}", this.position, this.velocity, Float.valueOf(this.mass), Float.valueOf(this.radius));
        }
    }
public class Vector2D {
        public float x;
        public float y;

        public Vector2D() {
            this.x = 0.0f;
            this.y = 0.0f;
        }

        public Vector2D(float f, float f2) {
            this.x = f;
            this.y = f2;
        }

        public Vector2D(Vector2D vector2D) {
            this.x = vector2D.x;
            this.y = vector2D.y;
        }

        public void add(Vector2D vector2D) {
            this.x += vector2D.x;
            this.y += vector2D.y;
        }

        public void add(float f, float f2) {
            this.x += f;
            this.y += f2;
        }

        public void subtract(Vector2D vector2D) {
            this.x -= vector2D.x;
            this.y -= vector2D.y;
        }

        public void multiply(float f) {
            this.x *= f;
            this.y *= f;
        }

        public float dot(Vector2D vector2D) {
            return this.x * vector2D.x + this.y * vector2D.y;
        }

        public float magnitude() {
            return (float)Math.sqrt(this.x * this.x + this.y * this.y);
        }

        public float magnitudeSquared() {
            return this.x * this.x + this.y * this.y;
        }

        public void normalize() {
            float f = this.magnitude();
            if (f > 0.0f) {
                this.x /= f;
                this.y /= f;
            }
        }

        public Vector2D toPixels() {
            return new Vector2D(this.x * 32.0f, this.y * 32.0f);
        }

        public Vector2D toMeters() {
            return new Vector2D(this.x * 0.03125f, this.y * 0.03125f);
        }

        public String toString() {
            return String.format("[%.2f, %.2f]", Float.valueOf(this.x), Float.valueOf(this.y));
        }
    }
}
