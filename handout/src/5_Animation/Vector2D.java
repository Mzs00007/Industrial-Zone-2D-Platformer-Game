/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class Vector2D.PhysicsUnitSystem.Vector2D {
    public float x;
    public float y;

    public AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D(Vector2D.PhysicsUnitSystem.Vector2D vector2D) {
        this.x = vector2D.x;
        this.y = vector2D.y;
    }

    public void add(Vector2D.PhysicsUnitSystem.Vector2D vector2D) {
        this.x += vector2D.x;
        this.y += vector2D.y;
    }

    public void add(float f, float f2) {
        this.x += f;
        this.y += f2;
    }

    public void subtract(Vector2D.PhysicsUnitSystem.Vector2D vector2D) {
        this.x -= vector2D.x;
        this.y -= vector2D.y;
    }

    public void multiply(float f) {
        this.x *= f;
        this.y *= f;
    }

    public float dot(Vector2D.PhysicsUnitSystem.Vector2D vector2D) {
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

    public Vector2D.PhysicsUnitSystem.Vector2D toPixels() {
        return new Vector2D.PhysicsUnitSystem.Vector2D(this.x * 32.0f, this.y * 32.0f);
    }

    public Vector2D.PhysicsUnitSystem.Vector2D toMeters() {
        return new Vector2D.PhysicsUnitSystem.Vector2D(this.x * 0.03125f, this.y * 0.03125f);
    }

    public String toString() {
        return String.format("[%.2f, %.2f]", Float.valueOf(this.x), Float.valueOf(this.y));
    }
}
