/*
 * Decompiled with CFR 0.152.
 */
package vfx;

import animation.AnimationAndSpriteLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SparkEffectSystem {
    public static final int SPARK_TYPE_01 = 1;
    public static final int SPARK_TYPE_02 = 2;
    public static final int SPARK_TYPE_03 = 3;
    public static final int SPARK_TYPE_04 = 4;
    public static final int SPARK_TYPE_05 = 5;
    public static final int SPARK_TYPE_06 = 6;
    public static final int SPARK_TYPE_07 = 7;
    public static final int SPARK_TYPE_08 = 8;
    private static final String SPARK_BASE = "Resources/industrial-zone/vfx/3 Sparks/";
    private static final int FRAME_COUNT = 4;
    private static final long FRAME_DURATION_MS = 80L;
    private Map<Integer, AnimationAndSpriteLoader.HorizontalSpritesheetLoader> sparkLoaders;
    private List<ActiveSparkEffect> activeEffects;
    private AnimationAndSpriteLoader animLoader;

    public SparkEffectSystem(AnimationAndSpriteLoader animationAndSpriteLoader) {
        this.animLoader = animationAndSpriteLoader;
        this.sparkLoaders = new HashMap<Integer, AnimationAndSpriteLoader.HorizontalSpritesheetLoader>();
        this.activeEffects = Collections.synchronizedList(new ArrayList());
        this.loadAllSparkEffects();
    }

    private void loadAllSparkEffects() {
        System.out.println("\n[SparkEffectSystem] Loading spark effects...");
        int[] nArray = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int n = 0;
        for (int n2 : nArray) {
            try {
                String string = String.format("%02d Spark.png", n2);
                String string2 = SPARK_BASE + string;
                File file = new File(string2);
                if (!file.exists()) {
                    System.out.println("[SparkEffectSystem] \u2717 Spark " + n2 + " not found: " + string2);
                    continue;
                }
                AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader("Spark_" + n2, string2, 64, 64, 4);
                this.sparkLoaders.put(n2, horizontalSpritesheetLoader);
                ++n;
                System.out.println("[SparkEffectSystem] \u2713 Loaded Spark Type " + n2);
            }
            catch (Exception exception) {
                System.out.println("[SparkEffectSystem] \u2717 Failed to load Spark " + n2 + ": " + exception.getMessage());
            }
        }
        System.out.println("[SparkEffectSystem] Loaded: " + n + " / 8 spark types\n");
    }

    public void triggerSpark(int n, int n2, int n3) {
        if (!this.sparkLoaders.containsKey(n)) {
            System.out.println("[SparkEffectSystem] \u2717 Unknown spark type: " + n);
            return;
        }
        try {
            AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = this.sparkLoaders.get(n);
            ActiveSparkEffect activeSparkEffect = new ActiveSparkEffect(horizontalSpritesheetLoader, n2, n3, 80L, System.currentTimeMillis());
            this.activeEffects.add(activeSparkEffect);
            System.out.println("[SparkEffectSystem] \u2713 Triggered Spark " + n + " at (" + n2 + ", " + n3 + ")");
        }
        catch (Exception exception) {
            System.out.println("[SparkEffectSystem] \u2717 Error triggering spark: " + exception.getMessage());
        }
    }

    public List<ActiveSparkEffect> getActiveEffects() {
        this.activeEffects.removeIf(activeSparkEffect -> activeSparkEffect.isExpired());
        return new ArrayList<ActiveSparkEffect>(this.activeEffects);
    }

    public void clearEffects() {
        this.activeEffects.clear();
    }

    public boolean hasActiveEffects() {
        return !this.getActiveEffects().isEmpty();
    }

    public static int getSparkTypeByColor(String string) {
        switch (string.toLowerCase()) {
            case "gold": 
            case "yellow": {
                return 1;
            }
            case "blue": 
            case "cyan": {
                return 2;
            }
            case "red": 
            case "orange": {
                return 3;
            }
            case "green": {
                return 4;
            }
            case "purple": 
            case "violet": {
                return 5;
            }
            case "white": 
            case "bright": {
                return 6;
            }
            case "pink": 
            case "magenta": {
                return 7;
            }
            case "neon": {
                return 8;
            }
        }
        return 6;
    }

    public static void printAvailableSparks() {
        System.out.println("\n\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        System.out.println("SparkEffectSystem - Available Spark Types");
        System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        System.out.println("SPARK_TYPE_01: Yellow/Gold sparks");
        System.out.println("SPARK_TYPE_02: Blue/Cyan sparks");
        System.out.println("SPARK_TYPE_03: Red/Orange sparks");
        System.out.println("SPARK_TYPE_04: Green sparks");
        System.out.println("SPARK_TYPE_05: Purple sparks");
        System.out.println("SPARK_TYPE_06: White/Bright sparks");
        System.out.println("SPARK_TYPE_07: Pink/Magenta sparks");
        System.out.println("SPARK_TYPE_08: Cyan/Neon sparks");
        System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        System.out.println("Frame timing: 80ms per frame");
        System.out.println("Total duration: 320ms");
        System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n");
    }

    public void close() {
        this.activeEffects.clear();
        this.sparkLoaders.clear();
        System.out.println("[SparkEffectSystem] Closed");
    }

    public static class ActiveSparkEffect {
        private AnimationAndSpriteLoader.HorizontalSpritesheetLoader loader;
        private int screenX;
        private int screenY;
        private long startTime;
        private long frameHoldMs;
        private boolean expired = false;

        public ActiveSparkEffect(AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader, int n, int n2, long l, long l2) {
            this.loader = horizontalSpritesheetLoader;
            this.screenX = n;
            this.screenY = n2;
            this.frameHoldMs = l;
            this.startTime = l2;
        }

        public int getCurrentFrame() {
            long l = System.currentTimeMillis() - this.startTime;
            int n = (int)(l / this.frameHoldMs);
            if (n >= 4) {
                this.expired = true;
                return 3;
            }
            return Math.min(n, 3);
        }

        public boolean isExpired() {
            return this.expired || System.currentTimeMillis() - this.startTime > 4L * this.frameHoldMs;
        }

        public int getScreenX() {
            return this.screenX;
        }

        public int getScreenY() {
            return this.screenY;
        }

        public AnimationAndSpriteLoader.HorizontalSpritesheetLoader getLoader() {
            return this.loader;
        }
    }
}
