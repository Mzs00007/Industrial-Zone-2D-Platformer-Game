/*
 * Decompiled with CFR 0.152.
 */
package vfx;

import animation.AnimationAndSpriteLoader;

public static class SparkEffectSystem.ActiveSparkEffect {
    private AnimationAndSpriteLoader.HorizontalSpritesheetLoader loader;
    private int screenX;
    private int screenY;
    private long startTime;
    private long frameHoldMs;
    private boolean expired = false;

    public SparkEffectSystem.ActiveSparkEffect(AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader, int n, int n2, long l, long l2) {
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
