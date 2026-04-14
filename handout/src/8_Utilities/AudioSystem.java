/*
 * Decompiled with CFR 0.152.
 */
package utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioSystem {

    public static class Manager {
        private VolumeController volumeController = new VolumeController();
        private AudioLibrary audioLibrary = new AudioLibrary();
        private MusicPlayer musicPlayer = new MusicPlayer();
        private AudioListener audioListener = new AudioListener();
        private List<SoundEffect> activeSounds = new ArrayList<SoundEffect>();
        private boolean audioEnabled = true;

        public Manager() {
            this.audioLibrary.loadDefaultSounds();
        }

        public void playSoundEffect(String string) {
            if (!this.audioEnabled) {
                return;
            }
            SoundEffect soundEffect = this.audioLibrary.getSound(string);
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
            SoundEffect soundEffect = this.audioLibrary.getSound(string);
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
            SoundEffect soundEffect = new SoundEffect("music", string);
            soundEffect.setVolume(this.volumeController.getMusicVolume());
            this.musicPlayer.playMusic(soundEffect, bl);
        }

        public void stopAllSounds() {
            for (SoundEffect soundEffect : this.activeSounds) {
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

        public VolumeController getVolumeController() {
            return this.volumeController;
        }

        public AudioLibrary getAudioLibrary() {
            return this.audioLibrary;
        }

        public MusicPlayer getMusicPlayer() {
            return this.musicPlayer;
        }

        public AudioListener getAudioListener() {
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

    public static class AudioListener {
        private float listenerX = 0.0f;
        private float listenerY = 0.0f;
        private float listenerZ = 0.0f;
        private float maxAudioDistance = 500.0f;
        private boolean spatialAudioEnabled = false;

        public void setPosition(float f, float f2, float f3) {
            this.listenerX = f;
            this.listenerY = f2;
            this.listenerZ = f3;
        }

        public float calculateVolumeFromDistance(float f, float f2) {
            if (!this.spatialAudioEnabled) {
                return 1.0f;
            }
            float f3 = f - this.listenerX;
            float f4 = f2 - this.listenerY;
            float f5 = (float)Math.sqrt(f3 * f3 + f4 * f4);
            if (f5 > this.maxAudioDistance) {
                return 0.0f;
            }
            return 1.0f - f5 / this.maxAudioDistance;
        }

        public float calculatePanFromPosition(float f) {
            if (!this.spatialAudioEnabled) {
                return 0.0f;
            }
            float f2 = f - this.listenerX;
            return Math.max(-1.0f, Math.min(1.0f, f2 / 200.0f));
        }

        public void enableSpatialAudio(boolean bl) {
            this.spatialAudioEnabled = bl;
        }

        public void setMaxAudioDistance(float f) {
            this.maxAudioDistance = Math.max(100.0f, f);
        }

        public float getListenerX() {
            return this.listenerX;
        }

        public float getListenerY() {
            return this.listenerY;
        }

        public float getListenerZ() {
            return this.listenerZ;
        }

        public boolean isSpatialAudioEnabled() {
            return this.spatialAudioEnabled;
        }
    }

    public static class MusicPlayer {
        private SoundEffect currentMusik;
        private SoundEffect nextMusic;
        private boolean isPlaying = false;
        private boolean crossfading = false;
        private float crossfadeDuration = 2.0f;
        private float crossfadeTimer = 0.0f;

        public void playMusic(SoundEffect soundEffect, boolean bl) {
            if (this.currentMusik != null) {
                this.currentMusik.stop();
            }
            this.currentMusik = soundEffect;
            this.currentMusik.setLooping(bl);
            this.currentMusik.play();
            this.isPlaying = true;
        }

        public void crossfadeToMusic(SoundEffect soundEffect, float f) {
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

        public SoundEffect getCurrentMusic() {
            return this.currentMusik;
        }

        public boolean isCrossfading() {
            return this.crossfading;
        }
    }

    public static class AudioLibrary {
        private Map<String, SoundEffect> soundEffects = new HashMap<String, SoundEffect>();
        private int totalSoundsLoaded = 0;
        private float totalMemoryUsed = 0.0f;

        public void registerSound(String string, SoundEffect soundEffect) {
            this.soundEffects.put(string, soundEffect);
            ++this.totalSoundsLoaded;
        }

        public SoundEffect getSound(String string) {
            return this.soundEffects.getOrDefault(string, null);
        }

        public boolean hasSound(String string) {
            return this.soundEffects.containsKey(string);
        }

        public void loadDefaultSounds() {
            this.registerSound("jump", new SoundEffect("jump", "Resources/industrial-zone/audio/sfx/jump.wav"));
            this.registerSound("land", new SoundEffect("land", "Resources/industrial-zone/audio/sfx/land.wav"));
            this.registerSound("fall", new SoundEffect("fall", "Resources/industrial-zone/audio/sfx/fall.wav"));
            this.registerSound("dash", new SoundEffect("dash", "Resources/industrial-zone/audio/sfx/dash.wav"));
            this.registerSound("hit", new SoundEffect("hit", "Resources/industrial-zone/audio/sfx/hit.wav"));
            this.registerSound("punch", new SoundEffect("punch", "Resources/industrial-zone/audio/sfx/punch.wav"));
            this.registerSound("kick", new SoundEffect("kick", "Resources/industrial-zone/audio/sfx/kick.wav"));
            this.registerSound("death", new SoundEffect("death", "Resources/industrial-zone/audio/sfx/death.wav"));
            this.registerSound("hurt", new SoundEffect("hurt", "Resources/industrial-zone/audio/sfx/hurt.wav"));
            this.registerSound("item_collect", new SoundEffect("item_collect", "Resources/industrial-zone/audio/sfx/item_collect.wav"));
            this.registerSound("powerup", new SoundEffect("powerup", "Resources/industrial-zone/audio/sfx/powerup.wav"));
            this.registerSound("coin", new SoundEffect("coin", "Resources/industrial-zone/audio/sfx/coin.wav"));
            this.registerSound("explosion", new SoundEffect("explosion", "Resources/industrial-zone/audio/sfx/explosion.wav"));
            this.registerSound("ui_click", new SoundEffect("ui_click", "Resources/industrial-zone/audio/sfx/ui_click.wav"));
            this.registerSound("ui_hover", new SoundEffect("ui_hover", "Resources/industrial-zone/audio/sfx/ui_hover.wav"));
            this.registerSound("ui_select", new SoundEffect("ui_select", "Resources/industrial-zone/audio/sfx/ui_select.wav"));
            this.registerSound("ui_back", new SoundEffect("ui_back", "Resources/industrial-zone/audio/sfx/ui_back.wav"));
            this.registerSound("door_open", new SoundEffect("door_open", "Resources/industrial-zone/audio/sfx/door_open.wav"));
            this.registerSound("water_splash", new SoundEffect("water_splash", "Resources/industrial-zone/audio/sfx/water_splash.wav"));
            this.registerSound("fire_burn", new SoundEffect("fire_burn", "Resources/industrial-zone/audio/sfx/fire_burn.wav"));
            this.registerSound("ambient_loop", new SoundEffect("ambient_loop", "Resources/industrial-zone/audio/sfx/ambient.wav"));
            this.registerSound("wind", new SoundEffect("wind", "Resources/industrial-zone/audio/sfx/wind.wav"));
            this.registerSound("rain", new SoundEffect("rain", "Resources/industrial-zone/audio/sfx/rain.wav"));
        }

        public int getTotalSoundsLoaded() {
            return this.totalSoundsLoaded;
        }

        public float getTotalMemoryUsed() {
            return this.totalMemoryUsed;
        }

        public void addMemoryUsage(float f) {
            this.totalMemoryUsed += f;
        }
    }

    public static class SoundEffectPresets {
        public static final String JUMP = "jump";
        public static final String LAND = "land";
        public static final String FALL = "fall";
        public static final String DASH = "dash";
        public static final String HIT = "hit";
        public static final String PUNCH = "punch";
        public static final String KICK = "kick";
        public static final String DEATH = "death";
        public static final String HURT = "hurt";
        public static final String ITEM_COLLECT = "item_collect";
        public static final String POWERUP = "powerup";
        public static final String COIN = "coin";
        public static final String EXPLOSION = "explosion";
        public static final String UI_CLICK = "ui_click";
        public static final String UI_HOVER = "ui_hover";
        public static final String UI_SELECT = "ui_select";
        public static final String UI_BACK = "ui_back";
        public static final String DOOR_OPEN = "door_open";
        public static final String WATER_SPLASH = "water_splash";
        public static final String FIRE_BURN = "fire_burn";
        public static final String AMBIENT_LOOP = "ambient_loop";
        public static final String WIND = "wind";
        public static final String RAIN = "rain";
    }

    public static class SoundEffect {
        private String name;
        private String filePath;
        private float volume = 1.0f;
        private float pitch = 1.0f;
        private float pan = 0.0f;
        private boolean isPlaying = false;
        private boolean looping = false;
        private float playbackPosition = 0.0f;
        private float duration = 0.0f;

        public SoundEffect(String string, String string2) {
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

    public static class VolumeController {
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
}
