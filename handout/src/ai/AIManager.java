/*
 * Decompiled with CFR 0.152.
 */
package ai;

import java.util.List;
public class AIManager {
    private static final float MAX_FORCE = 1.5f;
    private static final float MAX_VELOCITY = 3.0f;
    private static final float MAX_WANDER_DISTANCE = 100.0f;
    private static float wanderAngle = 0.0f;

    public static float[] seek(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        float f7 = (float)Math.sqrt(f5 * f5 + f6 * f6);
        if (f7 < 0.1f) {
            return new float[]{0.0f, 0.0f};
        }
        float f8 = f5 / f7 * 3.0f;
        float f9 = f6 / f7 * 3.0f;
        return new float[]{Math.min(1.5f, f8), Math.min(1.5f, f9)};
    }

    public static float[] flee(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        float f7 = (float)Math.sqrt(f5 * f5 + f6 * f6);
        if (f7 > 300.0f) {
            return new float[]{0.0f, 0.0f};
        }
        if (f7 < 0.1f) {
            return new float[]{1.5f, 1.5f};
        }
        float f8 = f5 / f7 * 3.0f;
        float f9 = f6 / f7 * 3.0f;
        return new float[]{f8, f9};
    }

    public static float[] pursuit(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        float f9 = 0.5f;
        float f10 = f5 + f7 * f9;
        float f11 = f6 + f8 * f9;
        return AIManager.seek(f, f2, f10, f11);
    }

    public static float[] evade(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        float f9 = 0.5f;
        float f10 = f5 + f7 * f9;
        float f11 = f6 + f8 * f9;
        return AIManager.flee(f, f2, f10, f11);
    }

    public static float[] wander(float f, float f2, float f3, float f4) {
        wanderAngle = (float)((double)wanderAngle + (Math.random() - 0.5) * 2.0);
        float f5 = (float)(Math.cos(wanderAngle) * 100.0);
        float f6 = (float)(Math.sin(wanderAngle) * 100.0);
        return AIManager.seek(f, f2, f + f5, f2 + f6);
    }

    public static float[] avoidObstacle(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8;
        float f9 = f - f5;
        float f10 = f2 - f6;
        float f11 = (float)Math.sqrt(f9 * f9 + f10 * f10);
        if (f11 > (f8 = f7 + 100.0f)) {
            return new float[]{0.0f, 0.0f};
        }
        float f12 = f9 / f11 * 3.0f;
        float f13 = f10 / f11 * 3.0f;
        return new float[]{f12, f13};
    }

    public static float[] separation(float f, float f2, List<float[]> list) {
        float f3;
        float f4 = 0.0f;
        float f5 = 0.0f;
        int n = 0;
        float f6 = 50.0f;
        for (float[] fArray : list) {
            float f7;
            float f8 = fArray[0] - f;
            float f9 = (float)Math.sqrt(f8 * f8 + (f7 = fArray[1] - f2) * f7);
            if (!(f9 < f6) || !(f9 > 0.1f)) continue;
            f4 -= f8 / f9;
            f5 -= f7 / f9;
            ++n;
        }
        if (n > 0 && (f3 = (float)Math.sqrt((f4 /= (float)n) * f4 + (f5 /= (float)n) * f5)) > 0.1f) {
            f4 = f4 / f3 * 3.0f;
            f5 = f5 / f3 * 3.0f;
        }
        return new float[]{f4, f5};
    }

    public static float[] alignment(float f, float f2, List<float[]> list) {
        float f3;
        float f4 = f;
        float f5 = f2;
        for (float[] fArray : list) {
            if (fArray.length < 4) continue;
            f4 += fArray[2];
            f5 += fArray[3];
        }
        if ((f3 = (float)Math.sqrt((f4 /= (float)(list.size() + 1)) * f4 + (f5 /= (float)(list.size() + 1)) * f5)) > 0.1f) {
            f4 = f4 / f3 * 3.0f;
            f5 = f5 / f3 * 3.0f;
        }
        return new float[]{f4, f5};
    }

    public static float[] cohesion(float f, float f2, List<float[]> list) {
        if (list.isEmpty()) {
            return new float[]{0.0f, 0.0f};
        }
        float f3 = f;
        float f4 = f2;
        for (float[] fArray : list) {
            f3 += fArray[0];
            f4 += fArray[1];
        }
        return AIManager.seek(f, f2, f3 /= (float)(list.size() + 1), f4 /= (float)(list.size() + 1));
    }
}
