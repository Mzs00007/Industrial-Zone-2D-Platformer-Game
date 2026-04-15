/*
 * Decompiled with CFR 0.152.
 */
package ai;
public class Waypoint {
    public float x;
    public float y;
    public String type;
    public float radius;
    public long stayTime;

    public Waypoint(float f, float f2) {
        this.x = f;
        this.y = f2;
        this.type = "patrol";
        this.radius = 10.0f;
        this.stayTime = 0L;
    }

    public Waypoint(float f, float f2, String string, float f3, long l) {
        this.x = f;
        this.y = f2;
        this.type = string;
        this.radius = f3;
        this.stayTime = l;
    }

    public boolean isAtWaypoint(float f, float f2) {
        float f3 = f - this.x;
        float f4 = f2 - this.y;
        return Math.sqrt(f3 * f3 + f4 * f4) <= (double)this.radius;
    }

    public float getDistance(float f, float f2) {
        float f3 = f - this.x;
        float f4 = f2 - this.y;
        return (float)Math.sqrt(f3 * f3 + f4 * f4);
    }
}
