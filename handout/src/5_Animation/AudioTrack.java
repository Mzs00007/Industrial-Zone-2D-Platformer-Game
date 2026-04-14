/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.AudioTrack {
    public String trackKey;
    public String filename;
    public float volumeLevel;
    public boolean isLooping;
    public float fadeInMs;
    public float fadeOutMs;
    public int bpm;

    public AnimationAndSpriteLoader.AudioTrack(String string, String string2, float f, boolean bl) {
        this.trackKey = string;
        this.filename = string2;
        this.volumeLevel = f;
        this.isLooping = bl;
        this.fadeInMs = 500.0f;
        this.fadeOutMs = 500.0f;
        this.bpm = 120;
    }

    public void setFades(float f, float f2) {
        this.fadeInMs = f;
        this.fadeOutMs = f2;
    }

    public void setBPM(int n) {
        this.bpm = n;
    }
}
