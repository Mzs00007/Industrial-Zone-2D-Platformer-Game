/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.InputHandler {
    private boolean[] keyPressed = new boolean[256];
    private boolean[] keyReleased = new boolean[256];
    private long[] lastKeyTime = new long[256];
    private static final long DOUBLE_TAP_WINDOW = 250L;
    public static final int KEY_UP = 38;
    public static final int KEY_DOWN = 40;
    public static final int KEY_LEFT = 37;
    public static final int KEY_RIGHT = 39;
    public static final int KEY_SPACE = 32;
    public static final int KEY_SHIFT = 16;

    public void onKeyDown(int n) {
        this.keyPressed[n] = true;
        this.keyReleased[n] = false;
    }

    public void onKeyUp(int n) {
        this.keyPressed[n] = false;
        this.keyReleased[n] = true;
    }

    public boolean isKeyPressed(int n) {
        return this.keyPressed[n];
    }

    public boolean isKeyReleased(int n) {
        return this.keyReleased[n];
    }

    public boolean isDoubleTap(int n) {
        long l = System.currentTimeMillis();
        long l2 = l - this.lastKeyTime[n];
        if (l2 < 250L && l2 > 0L) {
            this.lastKeyTime[n] = 0L;
            return true;
        }
        if (this.isKeyReleased(n)) {
            this.lastKeyTime[n] = l;
        }
        return false;
    }

    public void clearFrame() {
        for (int i = 0; i < this.keyReleased.length; ++i) {
            this.keyReleased[i] = false;
        }
    }
}
