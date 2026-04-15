/*
 * Decompiled with CFR 0.152.
 */
package controllers;
public class AnimationController {
    private int frameCount;
    private int currentFrame = 0;
    private long frameDuration;
    private long lastFrameTime = 0L;

    public AnimationController(int n, long l) {
        this.frameCount = n;
        this.frameDuration = l;
        this.lastFrameTime = System.currentTimeMillis();
    }

    public void update() {
        this.lastFrameTime = System.currentTimeMillis();
    }

    public void setCurrentFrame(int n) {
        this.currentFrame = Math.max(0, Math.min(n, this.frameCount - 1));
    }

    public int getCurrentFrame() {
        return this.currentFrame;
    }

    public int getFrameCount() {
        return this.frameCount;
    }
}
