/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class BulletInstance {
    public String bulletType;
    public float x;
    public float y;
    public float velocityX;
    public float velocityY;
    public float directionAngle;
    public long spawnTime;

    public BulletInstance(String string, float f, float f2, float f3) {
        this.bulletType = string;
        this.x = f;
        this.y = f2;
        this.directionAngle = f3;
        this.spawnTime = System.currentTimeMillis();
        float f4 = 5.0f;
        this.velocityX = f4 * (float)Math.cos(Math.toRadians(f3));
        this.velocityY = f4 * (float)Math.sin(Math.toRadians(f3));
    }
}
