/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody {
    public AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D position;
    public AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D velocity;
    public AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D acceleration;
    public AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D forces;
    public float mass;
    public float radius;
    public boolean isGrounded;
    public boolean isAffectedByGravity;

    public AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody(float f, float f2, float f3, float f4) {
        this.position = new AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D(f, f2);
        this.velocity = new AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D();
        this.acceleration = new AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D();
        this.forces = new AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D();
        this.mass = f3 > 0.0f ? f3 : 1.0f;
        this.radius = f4;
        this.isGrounded = false;
        this.isAffectedByGravity = true;
    }

    public void applyForce(float f, float f2) {
        this.forces.x += f;
        this.forces.y += f2;
    }

    public void applyForce(AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D vector2D) {
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

    public boolean collidesWithAABB(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody) {
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
