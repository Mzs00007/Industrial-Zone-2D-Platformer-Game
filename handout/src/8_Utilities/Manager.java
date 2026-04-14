/*
 * Decompiled with CFR 0.152.
 */
package audio;

import audio.AudioSystem;
import java.util.ArrayList;
import java.util.List;

public static class AudioSystem.Manager {
    private AudioSystem.VolumeController volumeController = new AudioSystem.VolumeController();
    private AudioSystem.AudioLibrary audioLibrary = new AudioSystem.AudioLibrary();
    private AudioSystem.MusicPlayer musicPlayer = new AudioSystem.MusicPlayer();
    private AudioSystem.AudioListener audioListener = new AudioSystem.AudioListener();
    private List<AudioSystem.SoundEffect> activeSounds = new ArrayList<AudioSystem.SoundEffect>();
    private boolean audioEnabled = true;

    public AudioSystem.Manager() {
        this.audioLibrary.loadDefaultSounds();
    }

    public void playSoundEffect(String string) {
        if (!this.audioEnabled) {
            return;
        }
        AudioSystem.SoundEffect soundEffect = this.audioLibrary.getSound(string);
        if (soundEffect != null) {
            soundEffect.setVolume(this.volumeController.getSfxVolume());
            soundEffect.play();
            this.activeSounds.add(soundEffect);
        }
    }

    public void playSoundEffect(String string, float f, float f2) {
        if (!this.audioEnabled) {
            return;
        }
        AudioSystem.SoundEffect soundEffect = this.audioLibrary.getSound(string);
        if (soundEffect != null) {
            float f3 = this.audioListener.calculateVolumeFromDistance(f, f2);
            float f4 = this.audioListener.calculatePanFromPosition(f);
            soundEffect.setVolume(this.volumeController.getSfxVolume() * f3);
            soundEffect.setPan(f4);
            soundEffect.play();
            this.activeSounds.add(soundEffect);
        }
    }

    public void playMusic(String string, boolean bl) {
        AudioSystem.SoundEffect soundEffect = new AudioSystem.SoundEffect("music", string);
        soundEffect.setVolume(this.volumeController.getMusicVolume());
        this.musicPlayer.playMusic(soundEffect, bl);
    }

    public void stopAllSounds() {
        for (AudioSystem.SoundEffect soundEffect : this.activeSounds) {
            soundEffect.stop();
        }
        this.activeSounds.clear();
        this.musicPlayer.stopMusic();
    }

    public void update(float f) {
        this.volumeController.update(f);
        this.musicPlayer.update(f);
        this.activeSounds.removeIf(soundEffect -> {
            soundEffect.update(f);
            return !soundEffect.isPlaying();
        });
    }

    public AudioSystem.VolumeController getVolumeController() {
        return this.volumeController;
    }

    public AudioSystem.AudioLibrary getAudioLibrary() {
        return this.audioLibrary;
    }

    public AudioSystem.MusicPlayer getMusicPlayer() {
        return this.musicPlayer;
    }

    public AudioSystem.AudioListener getAudioListener() {
        return this.audioListener;
    }

    public int getActiveSoundCount() {
        return this.activeSounds.size();
    }

    public boolean isAudioEnabled() {
        return this.audioEnabled;
    }

    public void setAudioEnabled(boolean bl) {
        this.audioEnabled = bl;
        if (!bl) {
            this.stopAllSounds();
        }
    }
}
