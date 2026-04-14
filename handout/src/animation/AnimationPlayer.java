/*
 * Decompiled with CFR 0.152.
 */
package animation;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public static class Core.AnimationPlayer {
    private Map<Integer, BufferedImage> entityFrames = new HashMap<Integer, BufferedImage>();
    private Map<Integer, Long> entityStartTimes = new HashMap<Integer, Long>();

    public void playAnimation(int n, String string, String string2, long l) {
        this.entityStartTimes.put(n, l);
    }

    public void switchAnimation(int n, String string, long l) {
        this.entityStartTimes.put(n, l);
    }

    public void stopAnimation(int n) {
        this.entityFrames.remove(n);
        this.entityStartTimes.remove(n);
    }

    public void update(long l) {
    }

    public BufferedImage getCurrentFrameImage(int n) {
        return this.entityFrames.get(n);
    }

    public String getMemoryStats() {
        return "Animations loaded: " + this.entityFrames.size();
    }
}
