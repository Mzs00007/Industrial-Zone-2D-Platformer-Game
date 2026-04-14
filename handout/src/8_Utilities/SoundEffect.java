/*
 * Decompiled with CFR 0.152.
 */
package audio;

public static class AudioSystem.SoundEffect {
    private String name;
    private String filePath;
    private float volume = 1.0f;
    private float pitch = 1.0f;
    private float pan = 0.0f;
    private boolean isPlaying = false;
    private boolean looping = false;
    private float playbackPosition = 0.0f;
    private float duration = 0.0f;

    public AudioSystem.SoundEffect(String string, String string2) {
        this.name = string;
        this.filePath = string2;
    }

    public void play() {
        this.isPlaying = true;
        this.playbackPosition = 0.0f;
    }

    public void stop() {
        this.isPlaying = false;
        this.playbackPosition = 0.0f;
    }

    public void pause() {
        this.isPlaying = false;
    }

    public void resume() {
        this.isPlaying = true;
    }

    public void update(float f) {
        if (this.isPlaying) {
            this.playbackPosition += f;
            if (this.playbackPosition >= this.duration) {
                if (this.looping) {
                    this.playbackPosition = 0.0f;
                } else {
                    this.isPlaying = false;
                }
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public boolean isLooping() {
        return this.looping;
    }

    public float getPlaybackPosition() {
        return this.playbackPosition;
    }

    public float getDuration() {
        return this.duration;
    }

    public void setVolume(float f) {
        this.volume = Math.max(0.0f, Math.min(1.0f, f));
    }

    public float getVolume() {
        return this.volume;
    }

    public void setPitch(float f) {
        this.pitch = Math.max(0.5f, Math.min(2.0f, f));
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPan(float f) {
        this.pan = Math.max(-1.0f, Math.min(1.0f, f));
    }

    public float getPan() {
        return this.pan;
    }

    public void setLooping(boolean bl) {
        this.looping = bl;
    }

    public void setDuration(float f) {
        this.duration = Math.max(0.1f, f);
    }
}
