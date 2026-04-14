/*
 * Decompiled with CFR 0.152.
 */
package utilities;

import utilities.AudioSystem;

public static class AudioSystem.MusicPlayer {
    private AudioSystem.SoundEffect currentMusik;
    private AudioSystem.SoundEffect nextMusic;
    private boolean isPlaying = false;
    private boolean crossfading = false;
    private float crossfadeDuration = 2.0f;
    private float crossfadeTimer = 0.0f;

    public void playMusic(AudioSystem.SoundEffect soundEffect, boolean bl) {
        if (this.currentMusik != null) {
            this.currentMusik.stop();
        }
        this.currentMusik = soundEffect;
        this.currentMusik.setLooping(bl);
        this.currentMusik.play();
        this.isPlaying = true;
    }

    public void crossfadeToMusic(AudioSystem.SoundEffect soundEffect, float f) {
        this.nextMusic = soundEffect;
        this.crossfadeDuration = Math.max(0.5f, f);
        this.crossfadeTimer = 0.0f;
        this.crossfading = true;
        soundEffect.play();
    }

    public void stopMusic() {
        if (this.currentMusik != null) {
            this.currentMusik.stop();
        }
        this.isPlaying = false;
        this.crossfading = false;
    }

    public void update(float f) {
        if (this.currentMusik != null) {
            this.currentMusik.update(f);
        }
        if (this.crossfading) {
            this.crossfadeTimer += f;
            float f2 = Math.min(1.0f, this.crossfadeTimer / this.crossfadeDuration);
            if (this.currentMusik != null) {
                this.currentMusik.setVolume(1.0f - f2);
            }
            if (this.nextMusic != null) {
                this.nextMusic.setVolume(f2);
            }
            if (f2 >= 1.0f) {
                if (this.currentMusik != null) {
                    this.currentMusik.stop();
                }
                this.currentMusik = this.nextMusic;
                this.nextMusic = null;
                this.crossfading = false;
            }
        }
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public AudioSystem.SoundEffect getCurrentMusic() {
        return this.currentMusik;
    }

    public boolean isCrossfading() {
        return this.crossfading;
    }
}
