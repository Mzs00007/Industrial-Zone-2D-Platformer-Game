/*
 * Decompiled with CFR 0.152.
 */
package gui;

public class GUIAnimationManager {
    private float elapsedTime = 0.0f;
    private float duration = 0.0f;
    private AnimationType type = AnimationType.FADE_IN;
    private float startValue = 0.0f;
    private float endValue = 1.0f;
    private boolean isRunning = false;
    private boolean isLooping = false;
    private AnimationCallback callback = null;

    public void startAnimation(AnimationType animationType, float f, float f2, float f3, boolean bl, AnimationCallback animationCallback) {
        this.type = animationType;
        this.duration = f;
        this.startValue = f2;
        this.endValue = f3;
        this.elapsedTime = 0.0f;
        this.isRunning = true;
        this.isLooping = bl;
        this.callback = animationCallback;
    }

    public void startFade(boolean bl, float f) {
        this.startAnimation(bl ? AnimationType.FADE_IN : AnimationType.FADE_OUT, f, bl ? 0.0f : 1.0f, bl ? 1.0f : 0.0f, false, null);
    }

    public void startSlide(AnimationType animationType, float f) {
        if (animationType != AnimationType.SLIDE_LEFT && animationType != AnimationType.SLIDE_RIGHT && animationType != AnimationType.SLIDE_UP && animationType != AnimationType.SLIDE_DOWN) {
            throw new IllegalArgumentException("Invalid slide type");
        }
        this.startAnimation(animationType, f, 0.0f, 1.0f, false, null);
    }

    public void update(float f) {
        if (!this.isRunning) {
            return;
        }
        this.elapsedTime += f;
        if (this.elapsedTime >= this.duration) {
            if (this.isLooping) {
                this.elapsedTime = 0.0f;
            } else {
                this.isRunning = false;
                if (this.callback != null) {
                    this.callback.onAnimationComplete();
                }
            }
        }
    }

    public float getProgress() {
        if (this.duration <= 0.0f) {
            return 1.0f;
        }
        return Math.min(1.0f, this.elapsedTime / this.duration);
    }

    public float getEasedProgress() {
        float f = this.getProgress();
        return this.easeInOutCubic(f);
    }

    private float easeInOutCubic(float f) {
        if (f < 0.5f) {
            return 4.0f * f * f * f;
        }
        float f2 = 2.0f * f - 2.0f;
        return 0.5f * f2 * f2 * f2 + 1.0f;
    }

    public float getAlpha() {
        float f = this.getEasedProgress();
        return this.startValue + (this.endValue - this.startValue) * f;
    }

    public float getScale() {
        return this.getAlpha();
    }

    public float getXOffset(int n) {
        float f = this.getEasedProgress();
        switch (this.type.ordinal()) {
            case 2: {
                return (float)(-n) * (1.0f - f);
            }
            case 3: {
                return (float)n * f;
            }
        }
        return 0.0f;
    }

    public float getYOffset(int n) {
        float f = this.getEasedProgress();
        switch (this.type.ordinal()) {
            case 4: {
                return (float)(-n) * (1.0f - f);
            }
            case 5: {
                return (float)n * f;
            }
        }
        return 0.0f;
    }

    public boolean isAnimating() {
        return this.isRunning;
    }

    public void stop() {
        this.isRunning = false;
        this.elapsedTime = 0.0f;
    }

    public void reset() {
        this.elapsedTime = 0.0f;
        this.isRunning = false;
    }

    public static enum AnimationType {
        FADE_IN,
        FADE_OUT,
        SLIDE_LEFT,
        SLIDE_RIGHT,
        SLIDE_DOWN,
        SLIDE_UP,
        SCALE_UP,
        SCALE_DOWN,
        PULSE,
        FLICKER,
        BOUNCE,
        ROTATE,
        GLOW;

    }

    public static interface AnimationCallback {
        public void onAnimationComplete();
    }
}
