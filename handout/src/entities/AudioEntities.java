/*
 * Decompiled with CFR 0.152.
 */
package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioEntities {
    private static final boolean VERBOSE_LOGGING = true;

    private static void log(String string) {
        System.out.println("[AudioEntities] " + string);
    }

    private static void logError(String string) {
        System.err.println("[AudioEntities] ERROR: " + string);
    }

    public static class AudioManager {
        private float masterVolume = 1.0f;
        private float sfxVolume = 0.7f;
        private float musicVolume = 0.6f;
        private boolean isMuted = false;
        private MusicTrack currentTrack = null;
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

        public void playSFX(SoundEffect soundEffect, float f) {
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

        public void playSFX(SoundEffect soundEffect) {
            this.playSFX(soundEffect, 1.0f);
        }

        public void playMusic(MusicTrack musicTrack, boolean bl) {
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

        public void playMusic(MusicTrack musicTrack) {
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

        public MusicTrack getCurrentTrack() {
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

    public static enum MusicTrack {
        MENU_THEME("menu", "Resources/industrial-zone/audio/music/menu_theme.wav", true),
        LEVEL_1_THEME("level_1", "Resources/industrial-zone/audio/music/level_1_theme.wav", true),
        LEVEL_2_THEME("level_2", "Resources/industrial-zone/audio/music/level_2_theme.wav", true),
        BOSS_BATTLE_THEME("boss", "Resources/industrial-zone/audio/music/boss_battle.wav", true),
        GAME_OVER_THEME("game_over", "Resources/industrial-zone/audio/music/game_over.wav", false);

        public final String id;
        public final String filePath;
        public final boolean isLooping;

        private MusicTrack(String string2, String string3, boolean bl) {
            this.id = string2;
            this.filePath = string3;
            this.isLooping = bl;
        }
    }

    public static enum SoundEffect {
        WEAPON_FIRE("weapon_fire", "Resources/industrial-zone/audio/sfx/weapon_fire.wav", 0.7f),
        WEAPON_RELOAD("weapon_reload", "Resources/industrial-zone/audio/sfx/weapon_reload.wav", 0.6f),
        WEAPON_EMPTY("weapon_empty", "Resources/industrial-zone/audio/sfx/weapon_empty.wav", 0.5f),
        ENEMY_SPAWN("enemy_spawn", "Resources/industrial-zone/audio/sfx/enemy_spawn.wav", 0.6f),
        ENEMY_DEATH("enemy_death", "Resources/industrial-zone/audio/sfx/enemy_death.wav", 0.7f),
        BOSS_DEFEAT("boss_defeat", "Resources/industrial-zone/audio/sfx/boss_defeat.wav", 0.9f),
        PLAYER_JUMP("player_jump", "Resources/industrial-zone/audio/sfx/player_jump.wav", 0.5f),
        PLAYER_LAND("player_land", "Resources/industrial-zone/audio/sfx/player_land.wav", 0.4f),
        PLAYER_HIT("player_hit", "Resources/industrial-zone/audio/sfx/player_hit.wav", 0.8f),
        PLAYER_DEATH("player_death", "Resources/industrial-zone/audio/sfx/player_death.wav", 1.0f),
        UI_CLICK("ui_click", "Resources/industrial-zone/audio/sfx/ui_click.wav", 0.6f),
        UI_TOGGLE("ui_toggle", "Resources/industrial-zone/audio/sfx/ui_toggle.wav", 0.5f),
        UI_ACCEPT("ui_accept", "Resources/industrial-zone/audio/sfx/ui_accept.wav", 0.7f),
        UI_CANCEL("ui_cancel", "Resources/industrial-zone/audio/sfx/ui_cancel.wav", 0.6f),
        COLLECTIBLE_PICKUP("collectible", "Resources/industrial-zone/audio/sfx/collectible_pickup.wav", 0.7f),
        DOOR_OPEN("door_open", "Resources/industrial-zone/audio/sfx/door_open.wav", 0.6f),
        EXPLOSION("explosion", "Resources/industrial-zone/audio/sfx/explosion.wav", 0.9f);

        public final String id;
        public final String filePath;
        public final float defaultVolume;

        private SoundEffect(String string2, String string3, float f) {
            this.id = string2;
            this.filePath = string3;
            this.defaultVolume = f;
        }
    }
}
