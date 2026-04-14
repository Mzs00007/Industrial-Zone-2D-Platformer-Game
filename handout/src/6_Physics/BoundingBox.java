/*
 * Decompiled with CFR 0.152.
 */
package physics;

public static class CollisionDetector.BoundingBox {
    public float x;
    public float y;
    public float width;
    public float height;

    public CollisionDetector.BoundingBox(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    public float getLeft() {
        return this.x;
    }

    public float getRight() {
        return this.x + this.width;
    }

    public float getTop() {
        return this.y;
    }

    public float getBottom() {
        return this.y + this.height;
    }

    public float getCenterX() {
        return this.x + this.width / 2.0f;
    }

    public float getCenterY() {
        return this.y + this.height / 2.0f;
    }

    public void move(float f, float f2) {
        this.x += f;
        this.y += f2;
    }

    public void setPosition(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public CollisionDetector.BoundingBox copy() {
        return new CollisionDetector.BoundingBox(this.x, this.y, this.width, this.height);
    }
}
