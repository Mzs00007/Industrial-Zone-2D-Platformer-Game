/*
 * Decompiled with CFR 0.152.
 */
package core_game_entities.audio;

import entities.audio.AudioEntities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public static class AudioEntities.AudioManager {
    private float masterVolume = 1.0f;
    private float sfxVolume = 0.7f;
    private float musicVolume = 0.6f;
    private boolean isMuted = false;
    private AudioEntities.MusicTrack currentTrack = null;
    private boolean musicPlaying = false;
    private List<String> activeSFXClips = new ArrayList<String>();
    private Map<String, Long> sfxTimestamps = new HashMap<String, Long>();
    private static final int MAX_SIMULTANEOUS_SFX = 8;
    private boolean audioEnabled = true;

    public void initialize() {
        if (!this.audioEnabled) {
            System.out.println("[AudioManager] Audio system disabled - running in silent mode");
            return;
        }
        System.out.println("[AudioManager] Initializing audio system");
        System.out.println("[AudioManager] Master Volume: " + this.masterVolume * 100.0f + "%");
        System.out.println("[AudioManager] SFX Volume: " + this.sfxVolume * 100.0f + "%");
        System.out.println("[AudioManager] Music Volume: " + this.musicVolume * 100.0f + "%");
        System.out.println("[AudioManager] Max simultaneous SFX: 8");
    }

    public void playSFX(AudioEntities.SoundEffect soundEffect, float f) {
        if (!this.audioEnabled || this.isMuted) {
            return;
        }
        if (this.activeSFXClips.size() >= 8) {
            this.activeSFXClips.remove(0);
        }
        float f2 = this.masterVolume * this.sfxVolume * soundEffect.defaultVolume * f;
        String string = String.format("[%.2f]", Float.valueOf(Math.min(1.0f, f2)));
        this.activeSFXClips.add(soundEffect.id);
        this.sfxTimestamps.put(soundEffect.id, System.currentTimeMillis());
    }

    public void playSFX(AudioEntities.SoundEffect soundEffect) {
        this.playSFX(soundEffect, 1.0f);
    }

    public void playMusic(AudioEntities.MusicTrack musicTrack, boolean bl) {
        if (!this.audioEnabled || this.isMuted) {
            return;
        }
        if (this.currentTrack == musicTrack && this.musicPlaying) {
            return;
        }
        if (this.musicPlaying) {
            this.stopMusic();
        }
        this.currentTrack = musicTrack;
        this.musicPlaying = true;
    }

    public void playMusic(AudioEntities.MusicTrack musicTrack) {
        this.playMusic(musicTrack, true);
    }

    public void stopMusic() {
        if (this.musicPlaying && this.currentTrack != null) {
            this.musicPlaying = false;
            this.currentTrack = null;
        }
    }

    public void fadeOutMusic(long l) {
    }

    public void setMasterVolume(float f) {
        this.masterVolume = Math.max(0.0f, Math.min(1.0f, f));
    }

    public void setSFXVolume(float f) {
        this.sfxVolume = Math.max(0.0f, Math.min(1.0f, f));
    }

    public void setMusicVolume(float f) {
        this.musicVolume = Math.max(0.0f, Math.min(1.0f, f));
    }

    public void toggleMute() {
        this.isMuted = !this.isMuted;
    }

    public void setMuted(boolean bl) {
        this.isMuted = bl;
    }

    public float getMasterVolume() {
        return this.masterVolume;
    }

    public float getSFXVolume() {
        return this.sfxVolume;
    }

    public float getMusicVolume() {
        return this.musicVolume;
    }

    public boolean isMuted() {
        return this.isMuted;
    }

    public boolean isAudioEnabled() {
        return this.audioEnabled;
    }

    public AudioEntities.MusicTrack getCurrentTrack() {
        return this.currentTrack;
    }

    public boolean isMusicPlaying() {
        return this.musicPlaying;
    }

    public int getActiveSFXCount() {
        return this.activeSFXClips.size();
    }

    public void update(long l) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string : this.activeSFXClips) {
            long l2 = System.currentTimeMillis() - this.sfxTimestamps.getOrDefault(string, 0L);
            if (l2 <= 5000L) continue;
            arrayList.add(string);
        }
        this.activeSFXClips.removeAll(arrayList);
    }

    public void shutdown() {
        this.stopMusic();
        this.activeSFXClips.clear();
    }
}
