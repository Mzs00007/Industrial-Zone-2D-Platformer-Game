/*
 * Decompiled with CFR 0.152.
 */
package utilities;
public class VolumeController {
    private float masterVolume = 1.0f;
    private float sfxVolume = 0.8f;
    private float musicVolume = 0.7f;
    private float uiVolume = 0.9f;
    private boolean masterMuted = false;
    private boolean sfxMuted = false;
    private boolean musicMuted = false;
    private boolean uiMuted = false;
    private float volumeChangeSpeed = 0.5f;
    private float targetMasterVolume = this.masterVolume;
    private float targetMusicVolume = this.musicVolume;

    public void update(float f) {
        float f2;
        if (Math.abs(this.targetMasterVolume - this.masterVolume) > 0.01f) {
            f2 = this.targetMasterVolume > this.masterVolume ? 1.0f : -1.0f;
            this.masterVolume += f2 * this.volumeChangeSpeed * f;
            this.masterVolume = Math.max(0.0f, Math.min(1.0f, this.masterVolume));
        }
        if (Math.abs(this.targetMusicVolume - this.musicVolume) > 0.01f) {
            f2 = this.targetMusicVolume > this.musicVolume ? 1.0f : -1.0f;
            this.musicVolume += f2 * this.volumeChangeSpeed * f;
            this.musicVolume = Math.max(0.0f, Math.min(1.0f, this.musicVolume));
        }
    }

    public void setMasterVolume(float f) {
        this.masterVolume = Math.max(0.0f, Math.min(1.0f, f));
    }

    public void fadeMasterVolume(float f, float f2) {
        this.targetMasterVolume = Math.max(0.0f, Math.min(1.0f, f));
        this.volumeChangeSpeed = Math.max(0.1f, f2);
    }

    public void setSfxVolume(float f) {
        this.sfxVolume = Math.max(0.0f, Math.min(1.0f, f));
    }

    public void setMusicVolume(float f) {
        this.musicVolume = Math.max(0.0f, Math.min(1.0f, f));
    }

    public void fadeMusicVolume(float f, float f2) {
        this.targetMusicVolume = Math.max(0.0f, Math.min(1.0f, f));
        this.volumeChangeSpeed = Math.max(0.1f, f2);
    }

    public void setUiVolume(float f) {
        this.uiVolume = Math.max(0.0f, Math.min(1.0f, f));
    }

    public void toggleMasterMute() {
        this.masterMuted = !this.masterMuted;
    }

    public void toggleSfxMute() {
        this.sfxMuted = !this.sfxMuted;
    }

    public void toggleMusicMute() {
        this.musicMuted = !this.musicMuted;
    }

    public void toggleUiMute() {
        this.uiMuted = !this.uiMuted;
    }

    public float getMasterVolume() {
        return this.masterMuted ? 0.0f : this.masterVolume;
    }

    public float getSfxVolume() {
        return (this.sfxMuted ? 0.0f : this.sfxVolume) * this.getMasterVolume();
    }

    public float getMusicVolume() {
        return (this.musicMuted ? 0.0f : this.musicVolume) * this.getMasterVolume();
    }

    public float getUiVolume() {
        return (this.uiMuted ? 0.0f : this.uiVolume) * this.getMasterVolume();
    }

    public boolean isMasterMuted() {
        return this.masterMuted;
    }

    public boolean isSfxMuted() {
        return this.sfxMuted;
    }

    public boolean isMusicMuted() {
        return this.musicMuted;
    }

    public boolean isUiMuted() {
        return this.uiMuted;
    }
}
