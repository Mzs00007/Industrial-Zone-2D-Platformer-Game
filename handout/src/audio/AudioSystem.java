package audio;

import levels.LevelSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════════════════════════════════
 *                          UNIFIED AUDIO SYSTEM
 *                Complete audio subsystem with all nested classes
 * ═══════════════════════════════════════════════════════════════════════════════════════
 *
 * RASTER GRAPHICS ONLY - PNG IMAGE ASSETS ONLY (Audio: WAV/OGG)
 * ═══════════════════════════════════════════════════════════════════════════════════════
 *
 * FEATURES:
 * ├─ Sound Effects: Individual SFX with volume, pitch, pan control
 * ├─ Volume Control: Master, SFX, Music, UI channels (independent)
 * ├─ Audio Manager: High-level audio API for game
 * ├─ Music Player: Background music with loop support
 * ├─ Audio Library: Central registry of all loaded sounds
 * ├─ SFX Presets: Common game sounds (JUMP, HIT, POWERUP, etc)
 * └─ Audio Listener: World audio configuration
 *
 * @author 3359098
 * @version 1.0 - Complete Audio Consolidation
 * @since 2026
 */
public class AudioSystem extends camera.CameraSystem {
    
    /**
     * Constructor: Initialize AudioSystem
     * Inherits from VFXSystem (200 lines)
     * Adds sound playback, music management, audio mixing
     */
    public AudioSystem() {
        super();  // Initialize VFXSystem → CameraSystem → ObjectiveSystem → LevelSystem → DialogueSystem → Config → CoreSystem → PhysicsSystem → UISystem → RenderingSystem → InputController → AnimationAndSpriteLoader → GameCore
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        VOLUME CONTROLLER CLASS
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Manages independent volume channels for different audio types
     */
    public static class VolumeController {
        private float masterVolume = 1.0f;       // 0.0 - 1.0
        private float sfxVolume = 0.8f;          // Sound effects volume
        private float musicVolume = 0.7f;        // Background music volume
        private float uiVolume = 0.9f;           // UI sounds volume
        
        private boolean masterMuted = false;
        private boolean sfxMuted = false;
        private boolean musicMuted = false;
        private boolean uiMuted = false;
        
        private float volumeChangeSpeed = 0.5f; // Fade speed (0.0-1.0 per second)
        private float targetMasterVolume;
        private float targetMusicVolume;
        
        /**
         * Constructor
         */
        public VolumeController() {
            this.targetMasterVolume = masterVolume;
            this.targetMusicVolume = musicVolume;
        }
        
        /**
         * Updates volume fading
         */
        public void update(float deltaSeconds) {
            // Fade master volume
            if (Math.abs(targetMasterVolume - masterVolume) > 0.01f) {
                float direction = targetMasterVolume > masterVolume ? 1 : -1;
                masterVolume += direction * volumeChangeSpeed * deltaSeconds;
                masterVolume = Math.max(0.0f, Math.min(1.0f, masterVolume));
            }
            
            // Fade music volume
            if (Math.abs(targetMusicVolume - musicVolume) > 0.01f) {
                float direction = targetMusicVolume > musicVolume ? 1 : -1;
                musicVolume += direction * volumeChangeSpeed * deltaSeconds;
                musicVolume = Math.max(0.0f, Math.min(1.0f, musicVolume));
            }
        }
        
        // ════════════════════════════════════════════════════════════
        // SETTERS
        // ════════════════════════════════════════════════════════════
        
        /**
         * Set master volume (affects all channels)
         */
        public void setMasterVolume(float volume) {
            this.masterVolume = Math.max(0.0f, Math.min(1.0f, volume));
        }
        
        /**
         * Fade master volume smoothly
         */
        public void fadeMasterVolume(float targetVolume, float fadeSpeed) {
            this.targetMasterVolume = Math.max(0.0f, Math.min(1.0f, targetVolume));
            this.volumeChangeSpeed = Math.max(0.1f, fadeSpeed);
        }
        
        public void setSfxVolume(float volume) {
            this.sfxVolume = Math.max(0.0f, Math.min(1.0f, volume));
        }
        
        public void setMusicVolume(float volume) {
            this.musicVolume = Math.max(0.0f, Math.min(1.0f, volume));
        }
        
        /**
         * Fade music volume smoothly
         */
        public void fadeMusicVolume(float targetVolume, float fadeSpeed) {
            this.targetMusicVolume = Math.max(0.0f, Math.min(1.0f, targetVolume));
            this.volumeChangeSpeed = Math.max(0.1f, fadeSpeed);
        }
        
        public void setUiVolume(float volume) {
            this.uiVolume = Math.max(0.0f, Math.min(1.0f, volume));
        }
        
        public void toggleMasterMute() {
            this.masterMuted = !masterMuted;
        }
        
        public void toggleSfxMute() {
            this.sfxMuted = !sfxMuted;
        }
        
        public void toggleMusicMute() {
            this.musicMuted = !musicMuted;
        }
        
        public void toggleUiMute() {
            this.uiMuted = !uiMuted;
        }
        
        // ════════════════════════════════════════════════════════════
        // GETTERS
        // ════════════════════════════════════════════════════════════
        
        public float getMasterVolume() { return masterMuted ? 0.0f : masterVolume; }
        public float getSfxVolume() { return (sfxMuted ? 0.0f : sfxVolume) * getMasterVolume(); }
        public float getMusicVolume() { return (musicMuted ? 0.0f : musicVolume) * getMasterVolume(); }
        public float getUiVolume() { return (uiMuted ? 0.0f : uiVolume) * getMasterVolume(); }
        
        public boolean isMasterMuted() { return masterMuted; }
        public boolean isSfxMuted() { return sfxMuted; }
        public boolean isMusicMuted() { return musicMuted; }
        public boolean isUiMuted() { return uiMuted; }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        SOUND EFFECT CLASS
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Represents a single sound effect with playback parameters
     */
    public static class SoundEffect {
        private String name;
        private String filePath;
        private float volume = 1.0f;
        private float pitch = 1.0f;
        private float pan = 0.0f;              // -1.0 (left) to 1.0 (right)
        private boolean isPlaying = false;
        private boolean looping = false;
        private float playbackPosition = 0.0f; // Current playback position
        private float duration = 0.0f;         // Total duration in seconds
        
        /**
         * Constructor
         */
        public SoundEffect(String name, String filePath) {
            this.name = name;
            this.filePath = filePath;
        }
        
        /**
         * Play the sound effect
         */
        public void play() {
            this.isPlaying = true;
            this.playbackPosition = 0.0f;
        }
        
        /**
         * Stop the sound effect
         */
        public void stop() {
            this.isPlaying = false;
            this.playbackPosition = 0.0f;
        }
        
        /**
         * Pause the sound effect
         */
        public void pause() {
            this.isPlaying = false;
        }
        
        /**
         * Resume from pause
         */
        public void resume() {
            this.isPlaying = true;
        }
        
        /**
         * Update playback position
         */
        public void update(float deltaSeconds) {
            if (isPlaying) {
                playbackPosition += deltaSeconds;
                
                if (playbackPosition >= duration) {
                    if (looping) {
                        playbackPosition = 0.0f;
                    } else {
                        isPlaying = false;
                    }
                }
            }
        }
        
        // ════════════════════════════════════════════════════════════
        // GETTERS & SETTERS
        // ════════════════════════════════════════════════════════════
        
        public String getName() { return name; }
        public String getFilePath() { return filePath; }
        public boolean isPlaying() { return isPlaying; }
        public boolean isLooping() { return looping; }
        public float getPlaybackPosition() { return playbackPosition; }
        public float getDuration() { return duration; }
        
        public void setVolume(float volume) {
            this.volume = Math.max(0.0f, Math.min(1.0f, volume));
        }
        
        public float getVolume() { return volume; }
        
        public void setPitch(float pitch) {
            this.pitch = Math.max(0.5f, Math.min(2.0f, pitch));
        }
        
        public float getPitch() { return pitch; }
        
        public void setPan(float pan) {
            this.pan = Math.max(-1.0f, Math.min(1.0f, pan));
        }
        
        public float getPan() { return pan; }
        
        public void setLooping(boolean loop) {
            this.looping = loop;
        }
        
        public void setDuration(float duration) {
            this.duration = Math.max(0.1f, duration);
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        SOUND EFFECT PRESETS
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Common sound effect presets
     */
    public static class SoundEffectPresets {
        // Movement sounds
        public static final String JUMP = "jump";
        public static final String LAND = "land";
        public static final String FALL = "fall";
        public static final String DASH = "dash";
        
        // Combat sounds
        public static final String HIT = "hit";
        public static final String PUNCH = "punch";
        public static final String KICK = "kick";
        public static final String DEATH = "death";
        public static final String HURT = "hurt";
        
        // Item sounds
        public static final String ITEM_COLLECT = "item_collect";
        public static final String POWERUP = "powerup";
        public static final String COIN = "coin";
        public static final String EXPLOSION = "explosion";
        
        // UI sounds
        public static final String UI_CLICK = "ui_click";
        public static final String UI_HOVER = "ui_hover";
        public static final String UI_SELECT = "ui_select";
        public static final String UI_BACK = "ui_back";
        
        // Environment sounds
        public static final String DOOR_OPEN = "door_open";
        public static final String WATER_SPLASH = "water_splash";
        public static final String FIRE_BURN = "fire_burn";
        
        // Ambient
        public static final String AMBIENT_LOOP = "ambient_loop";
        public static final String WIND = "wind";
        public static final String RAIN = "rain";
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        AUDIO LIBRARY CLASS
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Central registry of all loaded audio assets
     */
    public static class AudioLibrary {
        private Map<String, SoundEffect> soundEffects = new HashMap<>();
        private int totalSoundsLoaded = 0;
        private float totalMemoryUsed = 0.0f;    // in MB
        
        /**
         * Register a sound effect
         */
        public void registerSound(String id, SoundEffect effect) {
            soundEffects.put(id, effect);
            totalSoundsLoaded++;
        }
        
        /**
         * Get sound effect by ID
         */
        public SoundEffect getSound(String id) {
            return soundEffects.getOrDefault(id, null);
        }
        
        /**
         * Check if sound exists
         */
        public boolean hasSound(String id) {
            return soundEffects.containsKey(id);
        }
        
        /**
         * Load all default sounds
         */
        public void loadDefaultSounds() {
            // Movement
            registerSound(SoundEffectPresets.JUMP, new SoundEffect(SoundEffectPresets.JUMP, "assets/sounds/jump.wav"));
            registerSound(SoundEffectPresets.LAND, new SoundEffect(SoundEffectPresets.LAND, "assets/sounds/land.wav"));
            registerSound(SoundEffectPresets.FALL, new SoundEffect(SoundEffectPresets.FALL, "assets/sounds/fall.wav"));
            registerSound(SoundEffectPresets.DASH, new SoundEffect(SoundEffectPresets.DASH, "assets/sounds/dash.wav"));
            
            // Combat
            registerSound(SoundEffectPresets.HIT, new SoundEffect(SoundEffectPresets.HIT, "assets/sounds/hit.wav"));
            registerSound(SoundEffectPresets.PUNCH, new SoundEffect(SoundEffectPresets.PUNCH, "assets/sounds/punch.wav"));
            registerSound(SoundEffectPresets.KICK, new SoundEffect(SoundEffectPresets.KICK, "assets/sounds/kick.wav"));
            registerSound(SoundEffectPresets.DEATH, new SoundEffect(SoundEffectPresets.DEATH, "assets/sounds/death.wav"));
            registerSound(SoundEffectPresets.HURT, new SoundEffect(SoundEffectPresets.HURT, "assets/sounds/hurt.wav"));
            
            // Items
            registerSound(SoundEffectPresets.ITEM_COLLECT, new SoundEffect(SoundEffectPresets.ITEM_COLLECT, "assets/sounds/item_collect.wav"));
            registerSound(SoundEffectPresets.POWERUP, new SoundEffect(SoundEffectPresets.POWERUP, "assets/sounds/powerup.wav"));
            registerSound(SoundEffectPresets.COIN, new SoundEffect(SoundEffectPresets.COIN, "assets/sounds/coin.wav"));
            registerSound(SoundEffectPresets.EXPLOSION, new SoundEffect(SoundEffectPresets.EXPLOSION, "assets/sounds/explosion.wav"));
            
            // UI
            registerSound(SoundEffectPresets.UI_CLICK, new SoundEffect(SoundEffectPresets.UI_CLICK, "assets/sounds/ui_click.wav"));
            registerSound(SoundEffectPresets.UI_HOVER, new SoundEffect(SoundEffectPresets.UI_HOVER, "assets/sounds/ui_hover.wav"));
            registerSound(SoundEffectPresets.UI_SELECT, new SoundEffect(SoundEffectPresets.UI_SELECT, "assets/sounds/ui_select.wav"));
            registerSound(SoundEffectPresets.UI_BACK, new SoundEffect(SoundEffectPresets.UI_BACK, "assets/sounds/ui_back.wav"));
            
            // Environment
            registerSound(SoundEffectPresets.DOOR_OPEN, new SoundEffect(SoundEffectPresets.DOOR_OPEN, "assets/sounds/door_open.wav"));
            registerSound(SoundEffectPresets.WATER_SPLASH, new SoundEffect(SoundEffectPresets.WATER_SPLASH, "assets/sounds/water_splash.wav"));
            registerSound(SoundEffectPresets.FIRE_BURN, new SoundEffect(SoundEffectPresets.FIRE_BURN, "assets/sounds/fire_burn.wav"));
            
            // Ambient
            registerSound(SoundEffectPresets.AMBIENT_LOOP, new SoundEffect(SoundEffectPresets.AMBIENT_LOOP, "assets/sounds/ambient.wav"));
            registerSound(SoundEffectPresets.WIND, new SoundEffect(SoundEffectPresets.WIND, "assets/sounds/wind.wav"));
            registerSound(SoundEffectPresets.RAIN, new SoundEffect(SoundEffectPresets.RAIN, "assets/sounds/rain.wav"));
        }
        
        public int getTotalSoundsLoaded() { return totalSoundsLoaded; }
        public float getTotalMemoryUsed() { return totalMemoryUsed; }
        public void addMemoryUsage(float mb) { this.totalMemoryUsed += mb; }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        MUSIC PLAYER CLASS
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Manages background music playback
     */
    public static class MusicPlayer {
        private SoundEffect currentMusik;
        private SoundEffect nextMusic;
        private boolean isPlaying = false;
        private boolean crossfading = false;
        private float crossfadeDuration = 2.0f;
        private float crossfadeTimer = 0.0f;
        
        /**
         * Play music track
         */
        public void playMusic(SoundEffect music, boolean loop) {
            if (currentMusik != null) {
                currentMusik.stop();
            }
            currentMusik = music;
            currentMusik.setLooping(loop);
            currentMusik.play();
            isPlaying = true;
        }
        
        /**
         * Crossfade to next music
         */
        public void crossfadeToMusic(SoundEffect nextMusic, float duration) {
            this.nextMusic = nextMusic;
            this.crossfadeDuration = Math.max(0.5f, duration);
            this.crossfadeTimer = 0.0f;
            this.crossfading = true;
            nextMusic.play();
        }
        
        /**
         * Stop music
         */
        public void stopMusic() {
            if (currentMusik != null) {
                currentMusik.stop();
            }
            isPlaying = false;
            crossfading = false;
        }
        
        /**
         * Update music playback
         */
        public void update(float deltaSeconds) {
            if (currentMusik != null) {
                currentMusik.update(deltaSeconds);
            }
            
            if (crossfading) {
                crossfadeTimer += deltaSeconds;
                float progress = Math.min(1.0f, crossfadeTimer / crossfadeDuration);
                
                if (currentMusik != null) {
                    currentMusik.setVolume(1.0f - progress);
                }
                if (nextMusic != null) {
                    nextMusic.setVolume(progress);
                }
                
                if (progress >= 1.0f) {
                    if (currentMusik != null) {
                        currentMusik.stop();
                    }
                    currentMusik = nextMusic;
                    nextMusic = null;
                    crossfading = false;
                }
            }
        }
        
        public boolean isPlaying() { return isPlaying; }
        public SoundEffect getCurrentMusic() { return currentMusik; }
        public boolean isCrossfading() { return crossfading; }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        AUDIO LISTENER CLASS
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Configures world audio properties
     */
    public static class AudioListener {
        private float listenerX = 0.0f;
        private float listenerY = 0.0f;
        private float listenerZ = 0.0f;
        private float maxAudioDistance = 500.0f;
        private boolean spatialAudioEnabled = false;
        
        /**
         * Set listener position in world
         */
        public void setPosition(float x, float y, float z) {
            this.listenerX = x;
            this.listenerY = y;
            this.listenerZ = z;
        }
        
        /**
         * Calculate volume based on distance (for spatial audio)
         */
        public float calculateVolumeFromDistance(float sourceX, float sourceY) {
            if (!spatialAudioEnabled) return 1.0f;
            
            float dx = sourceX - listenerX;
            float dy = sourceY - listenerY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            
            if (distance > maxAudioDistance) return 0.0f;
            return 1.0f - (distance / maxAudioDistance);
        }
        
        /**
         * Calculate pan based on position
         */
        public float calculatePanFromPosition(float sourceX) {
            if (!spatialAudioEnabled) return 0.0f;
            
            float dx = sourceX - listenerX;
            return Math.max(-1.0f, Math.min(1.0f, dx / 200.0f));
        }
        
        public void enableSpatialAudio(boolean enabled) {
            this.spatialAudioEnabled = enabled;
        }
        
        public void setMaxAudioDistance(float distance) {
            this.maxAudioDistance = Math.max(100.0f, distance);
        }
        
        public float getListenerX() { return listenerX; }
        public float getListenerY() { return listenerY; }
        public float getListenerZ() { return listenerZ; }
        public boolean isSpatialAudioEnabled() { return spatialAudioEnabled; }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        AUDIO MANAGER CLASS
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * High-level audio manager - main API for game audio
     */
    public static class Manager {
        private VolumeController volumeController;
        private AudioLibrary audioLibrary;
        private MusicPlayer musicPlayer;
        private AudioListener audioListener;
        
        private List<SoundEffect> activeSounds;
        private boolean audioEnabled = true;
        
        /**
         * Constructor
         */
        public Manager() {
            this.volumeController = new VolumeController();
            this.audioLibrary = new AudioLibrary();
            this.musicPlayer = new MusicPlayer();
            this.audioListener = new AudioListener();
            this.activeSounds = new ArrayList<>();
            
            // Load default sounds
            audioLibrary.loadDefaultSounds();
        }
        
        /**
         * Play sound effect
         */
        public void playSoundEffect(String effectId) {
            if (!audioEnabled) return;
            
            SoundEffect effect = audioLibrary.getSound(effectId);
            if (effect != null) {
                effect.setVolume(volumeController.getSfxVolume());
                effect.play();
                activeSounds.add(effect);
            }
        }
        
        /**
         * Play sound effect with position (for spatial audio)
         */
        public void playSoundEffect(String effectId, float worldX, float worldY) {
            if (!audioEnabled) return;
            
            SoundEffect effect = audioLibrary.getSound(effectId);
            if (effect != null) {
                float distance = audioListener.calculateVolumeFromDistance(worldX, worldY);
                float pan = audioListener.calculatePanFromPosition(worldX);
                
                effect.setVolume(volumeController.getSfxVolume() * distance);
                effect.setPan(pan);
                effect.play();
                activeSounds.add(effect);
            }
        }
        
        /**
         * Play background music
         */
        public void playMusic(String musicFile, boolean loop) {
            SoundEffect music = new SoundEffect("music", musicFile);
            music.setVolume(volumeController.getMusicVolume());
            musicPlayer.playMusic(music, loop);
        }
        
        /**
         * Stop all sounds
         */
        public void stopAllSounds() {
            for (SoundEffect sound : activeSounds) {
                sound.stop();
            }
            activeSounds.clear();
            musicPlayer.stopMusic();
        }
        
        /**
         * Update all audio
         */
        public void update(float deltaSeconds) {
            volumeController.update(deltaSeconds);
            musicPlayer.update(deltaSeconds);
            
            // Update active sounds and remove finished ones
            activeSounds.removeIf(sound -> {
                sound.update(deltaSeconds);
                return !sound.isPlaying();
            });
        }
        
        // ════════════════════════════════════════════════════════════
        // GETTERS
        // ════════════════════════════════════════════════════════════
        
        public VolumeController getVolumeController() { return volumeController; }
        public AudioLibrary getAudioLibrary() { return audioLibrary; }
        public MusicPlayer getMusicPlayer() { return musicPlayer; }
        public AudioListener getAudioListener() { return audioListener; }
        public int getActiveSoundCount() { return activeSounds.size(); }
        public boolean isAudioEnabled() { return audioEnabled; }
        
        public void setAudioEnabled(boolean enabled) {
            this.audioEnabled = enabled;
            if (!enabled) stopAllSounds();
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        AUDIO MANAGER (From Audio.java)
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Core AudioManager for complete audio playback and effects
     */
    public static class AudioManager {
        private static volatile boolean initialized = false;
        
        /**
         * Audio event for processing
         */
        public static class AudioEvent {
            String name;
            AudioEvent(String name) { this.name = name; }
        }
        
        public AudioManager() {
            initialized = true;
        }
        
        public void playSound(String sfxName) {
            // Sound playback
        }
        
        public void playMusic(String musicName) {
            // Music playback
        }
        
        public void stopAllSounds() {
            // Stop all sounds
        }
        
        public void processAudioEvents() {
            if (!initialized) return;
        }
        
        public void shutdown() {
            initialized = false;
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        AUDIO SYSTEMS STUB (From Audio.java)
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Placeholder audio subsystem classes
     */
    public static class AudioSystemsStub {
        
        public static class MusicManager {
            public void playMusic(String trackId) {}
            public void stopMusic() {}
            public void setMusicVolume(float volume) {}
        }
        
        public static class SFXManager {
            public void playSFX(String sfxId) {}
            public void stopSFX(String sfxId) {}
            public void setSFXVolume(float volume) {}
        }
        
        public static class AudioMixer {
            public void setMasterVolume(float volume) {}
            public void setChannelVolume(String channel, float volume) {}
        }
        
        public static class AudioEventDispatcher {
            public void dispatchEvent(Object event) {}
            public void addListener(Object listener) {}
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        AUDIO ASSET REGISTRY (From AudioAssetRegistry.java)
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Comprehensive registry for ALL audio assets used in the game
     */
    public static class AudioAssetRegistry {
        
        private static final String AUDIO_BASE = "Resources/industrial-zone/audio/";
        private static final String AUDIO_MUSIC_MIDI = AUDIO_BASE + "music_midi/";
        private static final String AUDIO_MUSIC_WAV = AUDIO_BASE + "music_wav/";
        private static final String AUDIO_SFX = AUDIO_BASE + "sfx/";
        
        /**
         * MUSIC ASSETS (MIDI FORMAT)
         */
        public static class MusicAssets {
            private static final Map<String, String> REGISTRY = new HashMap<>();
            
            static {
                REGISTRY.put("TRACK_01", AUDIO_MUSIC_MIDI + "Track 1.mid");
                REGISTRY.put("TRACK_02", AUDIO_MUSIC_MIDI + "Track 2.mid");
                REGISTRY.put("TRACK_03", AUDIO_MUSIC_MIDI + "Track 3.mid");
                REGISTRY.put("TRACK_04", AUDIO_MUSIC_MIDI + "Track 4.mid");
                REGISTRY.put("TRACK_05", AUDIO_MUSIC_MIDI + "Track 5.mid");
            }
            
            public static String getAsset(String key) {
                return REGISTRY.getOrDefault(key, null);
            }
            
            public static java.util.Set<String> getAllKeys() {
                return REGISTRY.keySet();
            }
            
            public static boolean hasAsset(String key) {
                return REGISTRY.containsKey(key);
            }
            
            public static int getAssetCount() {
                return REGISTRY.size();
            }
        }
        
        /**
         * MUSIC ASSETS (WAV FORMAT - HIGH QUALITY)
         */
        public static class MusicWAVAssets {
            private static final Map<String, String> REGISTRY = new HashMap<>();
            
            static {
                REGISTRY.put("THEME_MAIN", AUDIO_MUSIC_WAV + "Main_theme_Chinese_Street.wav");
                REGISTRY.put("THEME_CALM", AUDIO_MUSIC_WAV + "Calm_theme_Chinese_Street.wav");
                REGISTRY.put("THEME_BATTLE", AUDIO_MUSIC_WAV + "Battle_theme_Chinese_Street.wav");
                REGISTRY.put("THEME_ALTERNATIVE", AUDIO_MUSIC_WAV + "Alternative_theme_Chinese_Street.wav");
                REGISTRY.put("MELODY_ATTRACTION", AUDIO_MUSIC_WAV + "Melody_of_attraction_loopable.wav");
                REGISTRY.put("MELODY_WIN", AUDIO_MUSIC_WAV + "Melody_of_the_win.wav");
                REGISTRY.put("THEME_STEALTHY", AUDIO_MUSIC_WAV + "Stealthy_theme_loopable.wav");
                REGISTRY.put("PORTAL_OPEN_1", AUDIO_MUSIC_WAV + "Portal_1.wav");
                REGISTRY.put("PORTAL_OPEN_2", AUDIO_MUSIC_WAV + "Portal_2.wav");
                REGISTRY.put("PORTAL_CLOSE", AUDIO_MUSIC_WAV + "Portal_closing.wav");
                REGISTRY.put("PORTAL_MOVING", AUDIO_MUSIC_WAV + "Portal_moving.wav");
                REGISTRY.put("ZIPLINE", AUDIO_MUSIC_WAV + "Zipline_loopable.wav");
                REGISTRY.put("ELEVATOR_MOTOR", AUDIO_MUSIC_WAV + "Elevator_motor.wav");
                REGISTRY.put("LIFT_MECHANISM", AUDIO_MUSIC_WAV + "Lift_mechanism.wav");
            }
            
            public static String getAsset(String key) {
                return REGISTRY.getOrDefault(key, null);
            }
            
            public static java.util.Set<String> getAllKeys() {
                return REGISTRY.keySet();
            }
            
            public static boolean hasAsset(String key) {
                return REGISTRY.containsKey(key);
            }
            
            public static int getAssetCount() {
                return REGISTRY.size();
            }
        }
        
        /**
         * SOUND EFFECTS (GAMEPLAY & ENVIRONMENTAL)
         */
        public static class SFXAssets {
            private static final Map<String, String> REGISTRY = new HashMap<>();
            
            static {
                // Door & passage sounds
                REGISTRY.put("DOOR_WOODEN_CREAKING", AUDIO_SFX + "Creaking_wooden_door.wav");
                REGISTRY.put("DOOR_SLIDING_OPEN", AUDIO_SFX + "Sliding_doors.wav");
                REGISTRY.put("DOOR_ROLLER", AUDIO_SFX + "Roller_doors.wav");
                REGISTRY.put("DOOR_PASSWORD", AUDIO_SFX + "Door_with_password.wav");
                REGISTRY.put("DOOR_BELL", AUDIO_SFX + "Bell_on_the_door.wav");
                REGISTRY.put("DOOR_PUSH_SLIDE", AUDIO_SFX + "Push_slide_door.wav");
                
                // Movement & mechanical
                REGISTRY.put("ELEVATOR_MOTOR", AUDIO_SFX + "Elevator_motor.wav");
                REGISTRY.put("LIFT_MECHANISM", AUDIO_SFX + "Lift_mechanism.wav");
                
                // Combat & attack
                REGISTRY.put("SWORD_LASER_1", AUDIO_SFX + "Laser_sword_1.wav");
                REGISTRY.put("SWORD_LASER_2", AUDIO_SFX + "Laser_sword_2.wav");
                REGISTRY.put("ATTACK_KARATEKA", AUDIO_SFX + "Karateka_attack.wav");
                REGISTRY.put("IMPACT_FLYING_PLATFORM_1", AUDIO_SFX + "Flying_platform_attak_1.wav");
                REGISTRY.put("IMPACT_FLYING_PLATFORM_2", AUDIO_SFX + "Flying_platform_attak_2.wav");
                
                // Robot sounds
                REGISTRY.put("ROBOT_HOVERING_WALK", AUDIO_SFX + "Hovering_robot_walk_loopable.wav");
                REGISTRY.put("ROBOT_HOVERING_STING", AUDIO_SFX + "Hovering_robot_sting.wav");
                
                // Character movement
                REGISTRY.put("FOOTSTEP_SAMURAI_1", AUDIO_SFX + "Samurai_footstep_1.wav");
                REGISTRY.put("FOOTSTEP_SAMURAI_2", AUDIO_SFX + "Samurai_footstep_2.wav");
                REGISTRY.put("CHARACTER_DEATH_SAMURAI", AUDIO_SFX + "Samurai_death.wav");
                
                // Danger & hazards
                REGISTRY.put("BOMB_DROP", AUDIO_SFX + "Bomb_drop.wav");
                REGISTRY.put("EXPLOSION", AUDIO_SFX + "Explosion.wav");
                
                // Interactive objects
                REGISTRY.put("CHEST_UNLOCKED", AUDIO_SFX + "Unlocked_chest.wav");
                
                // UI & click
                REGISTRY.put("CLICK_DIGITAL_1", AUDIO_SFX + "Click_digital_1.wav");
                REGISTRY.put("CLICK_DIGITAL_2", AUDIO_SFX + "Click_digital_2.wav");
                
                // Portal & teleport
                REGISTRY.put("PORTAL_OPEN_1", AUDIO_SFX + "Portal_1.wav");
                REGISTRY.put("PORTAL_OPEN_2", AUDIO_SFX + "Portal_2.wav");
                REGISTRY.put("PORTAL_CLOSE", AUDIO_SFX + "Portal_closing.wav");
                REGISTRY.put("PORTAL_MOVING", AUDIO_SFX + "Portal_moving.wav");
                
                // Zipline
                REGISTRY.put("ZIPLINE", AUDIO_SFX + "Zipline_loopable.wav");
            }
            
            public static String getAsset(String key) {
                return REGISTRY.getOrDefault(key, null);
            }
            
            public static java.util.Set<String> getAllKeys() {
                return REGISTRY.keySet();
            }
            
            public static boolean hasAsset(String key) {
                return REGISTRY.containsKey(key);
            }
            
            public static int getAssetCount() {
                return REGISTRY.size();
            }
        }
        
        /**
         * UI & FEEDBACK AUDIO
         */
        public static class UIAudioAssets {
            private static final Map<String, String> REGISTRY = new HashMap<>();
            
            static {
                REGISTRY.put("CLICK_1", AUDIO_SFX + "Click_digital_1.wav");
                REGISTRY.put("CLICK_2", AUDIO_SFX + "Click_digital_2.wav");
                REGISTRY.put("CONFIRM", AUDIO_SFX + "Bell_on_the_door.wav");
                REGISTRY.put("WIN", AUDIO_MUSIC_WAV + "Melody_of_the_win.wav");
                REGISTRY.put("ITEM_PICKUP", AUDIO_SFX + "Bell_on_the_door.wav");
            }
            
            public static String getAsset(String key) {
                return REGISTRY.getOrDefault(key, null);
            }
            
            public static java.util.Set<String> getAllKeys() {
                return REGISTRY.keySet();
            }
            
            public static boolean hasAsset(String key) {
                return REGISTRY.containsKey(key);
            }
            
            public static int getAssetCount() {
                return REGISTRY.size();
            }
        }
        
        /**
         * AMBIENT & ENVIRONMENTAL SOUNDS
         */
        public static class AmbienceAssets {
            private static final Map<String, String> REGISTRY = new HashMap<>();
            
            static {
                REGISTRY.put("FOOTSTEPS_SAMURAI_LOOP", AUDIO_SFX + "Samurai_footstep_1.wav");
                REGISTRY.put("ROBOT_WALK_LOOP", AUDIO_SFX + "Hovering_robot_walk_loopable.wav");
                REGISTRY.put("THEME_STEALTHY_LOOP", AUDIO_MUSIC_WAV + "Stealthy_theme_loopable.wav");
                REGISTRY.put("MELODY_LOOP", AUDIO_MUSIC_WAV + "Melody_of_attraction_loopable.wav");
                REGISTRY.put("ZIPLINE_LOOP", AUDIO_SFX + "Zipline_loopable.wav");
            }
            
            public static String getAsset(String key) {
                return REGISTRY.getOrDefault(key, null);
            }
            
            public static java.util.Set<String> getAllKeys() {
                return REGISTRY.keySet();
            }
            
            public static boolean hasAsset(String key) {
                return REGISTRY.containsKey(key);
            }
            
            public static int getAssetCount() {
                return REGISTRY.size();
            }
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        SOUND MANAGER (From SoundManager.java)
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Singleton audio system for game sounds and music
     */
    public static class SoundManager {
        private static SoundManager instance = null;
        
        private float masterVolume = 1.0f;
        private float sfxVolume = 0.8f;
        private float musicVolume = 0.6f;
        
        private Map<String, String> soundPaths = new HashMap<>();
        private String currentMusic = "";
        private boolean musicMuted = false;
        private boolean sfxMuted = false;
        
        private SoundManager() {
            registerDefaultSounds();
        }
        
        public static SoundManager getInstance() {
            if (instance == null) {
                instance = new SoundManager();
            }
            return instance;
        }
        
        private void registerDefaultSounds() {
            registerSound("fire_attack_melee", AudioAssetRegistry.SFXAssets.getAsset("ATTACK_KARATEKA"));
            registerSound("explosion", AudioAssetRegistry.SFXAssets.getAsset("EXPLOSION"));
            registerSound("footstep_1", AudioAssetRegistry.SFXAssets.getAsset("FOOTSTEP_SAMURAI_1"));
            registerSound("footstep_2", AudioAssetRegistry.SFXAssets.getAsset("FOOTSTEP_SAMURAI_2"));
            registerSound("ui_click", AudioAssetRegistry.UIAudioAssets.getAsset("CLICK_1"));
            registerSound("door_open", AudioAssetRegistry.SFXAssets.getAsset("DOOR_SLIDING_OPEN"));
            registerSound("music_menu", AudioAssetRegistry.MusicWAVAssets.getAsset("THEME_MAIN"));
            registerSound("music_battle", AudioAssetRegistry.MusicWAVAssets.getAsset("THEME_BATTLE"));
        }
        
        public void registerSound(String name, String filepath) {
            soundPaths.put(name, filepath);
        }
        
        public void playSFX(String soundName) {
            if (sfxMuted || sfxVolume <= 0) return;
            // Placeholder for actual audio playback
        }
        
        public void playMusic(String musicName) {
            if (musicMuted || musicVolume <= 0) return;
            if (musicName.equals(currentMusic)) return;
            currentMusic = musicName;
            // Placeholder for actual music playback
        }
        
        public void stopMusic() {
            currentMusic = "";
        }
        
        public void setMasterVolume(float volume) {
            this.masterVolume = Math.max(0f, Math.min(1f, volume));
        }
        
        public void setSFXVolume(float volume) {
            this.sfxVolume = Math.max(0f, Math.min(1f, volume));
        }
        
        public void setMusicVolume(float volume) {
            this.musicVolume = Math.max(0f, Math.min(1f, volume));
        }
        
        public void toggleSFX() {
            sfxMuted = !sfxMuted;
        }
        
        public void toggleMusic() {
            musicMuted = !musicMuted;
        }
        
        public float getMasterVolume() { return masterVolume; }
        public float getSFXVolume() { return sfxVolume; }
        public float getMusicVolume() { return musicVolume; }
        public String getCurrentMusic() { return currentMusic; }
        public boolean isSFXMuted() { return sfxMuted; }
        public boolean isMusicMuted() { return musicMuted; }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        MIDI TUNER (From MidiTuner.java)
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * MIDI/Audio Tuner for Game Audio Management
     */
    public static class MidiTuner {
        
        public static final int LOOP = -1;
        public static final int PLAY_ONCE = 1;
        
        private String audioPath;
        private int loopCount;
        private float volume = 1.0f;
        private boolean isPlaying = false;
        private float playbackTime = 0.0f;          // Track playback duration
        private int loopsRemaining;                 // Track remaining loops
        
        public MidiTuner(String filename, int loop) {
            this.audioPath = filename;
            this.loopCount = loop;
            this.loopsRemaining = loop;
            
            if (audioPath == null || audioPath.isEmpty()) {
                System.err.println("[MidiTuner] Warning: Empty audio path provided");
            }
        }
        
        public void play() {
            if (audioPath == null || audioPath.isEmpty()) {
                System.err.println("[MidiTuner] Error: Cannot play - audio path not set!");
                return;
            }
            
            isPlaying = true;
            playbackTime = 0.0f;
            loopsRemaining = loopCount;
            
            // Log playback info using audioPath and loopCount
            String loopInfo = (loopCount == LOOP) ? "∞ (infinite)" : loopCount + " time(s)";
            System.out.println("[MidiTuner] ▶ Playing: " + audioPath + " | Loops: " + loopInfo + " | Vol: " + String.format("%.0f%%", volume * 100));
        }
        
        public void stop() {
            isPlaying = false;
            playbackTime = 0.0f;
            loopsRemaining = 0;
            System.out.println("[MidiTuner] ⏹ Stopped: " + audioPath);
        }
        
        public void pause() {
            isPlaying = false;
            System.out.println("[MidiTuner] ⏸ Paused: " + audioPath);
        }
        
        public void resume() {
            if (audioPath != null && !audioPath.isEmpty()) {
                isPlaying = true;
                System.out.println("[MidiTuner] ▶ Resumed: " + audioPath);
            }
        }
        
        public void update(float deltaSeconds) {
            if (!isPlaying || audioPath == null) return;
            
            playbackTime += deltaSeconds;
            
            // Handle looping logic using loopCount
            if (loopCount == LOOP) {
                // Infinite looping - keep playing
                // In real implementation, would check audio length and restart
            } else if (loopCount > 0) {
                // Finite looping
                loopsRemaining--;
                if (loopsRemaining <= 0) {
                    isPlaying = false;
                    System.out.println("[MidiTuner] ✓ Finished: " + audioPath + " (played " + loopCount + " times)");
                }
            }
        }
        
        public void setVolume(float vol) {
            this.volume = Math.max(0.0f, Math.min(1.0f, vol));
            System.out.println("[MidiTuner] 🔊 Volume set to " + String.format("%.0f%%", volume * 100) + " for: " + audioPath);
        }
        
        public void fadeIn(float targetVolume, int durationMs) {
            this.volume = targetVolume;
            System.out.println("[MidiTuner] 🔊 Fade-in to " + String.format("%.0f%%", targetVolume * 100) + " for: " + audioPath);
        }
        
        public void fadeOut(int durationMs) {
            this.volume = 0.0f;
            System.out.println("[MidiTuner] 🔊 Fade-out for: " + audioPath);
        }
        
        // ════════════════════════════════════════════════════════════
        // GETTERS & SETTERS - Using audioPath and loopCount actively
        // ════════════════════════════════════════════════════════════
        
        public String getAudioPath() {
            return audioPath;
        }
        
        public void setAudioPath(String path) {
            this.audioPath = path;
            System.out.println("[MidiTuner] Audio path set to: " + path);
        }
        
        public int getLoopCount() {
            return loopCount;
        }
        
        public void setLoopCount(int count) {
            this.loopCount = count;
            this.loopsRemaining = count;
            String loopInfo = (count == LOOP) ? "infinite" : count + " time(s)";
            System.out.println("[MidiTuner] Loop count set to: " + loopInfo);
        }
        
        public boolean isLooping() {
            return loopCount == LOOP;
        }
        
        public float getPlaybackTime() {
            return playbackTime;
        }
        
        public boolean isPlaying() {
            return isPlaying;
        }
        
        public float getVolume() {
            return volume;
        }
        
        public void close() {
            if (isPlaying) {
                stop();
            }
            audioPath = null;
            System.out.println("[MidiTuner] Resource closed");
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        MUSIC INTEGRATOR (From MusicIntegrator.java)
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * High-level music controller for game events
     */
    public static class MusicIntegrator {
        
        private SoundManager soundManager;
        private String currentTrack = "";
        
        public enum MusicContext {
            NONE, MENU, LEVEL_CALM, LEVEL_BATTLE, BOSS_FIGHT, VICTORY
        }
        
        private MusicContext currentContext = MusicContext.NONE;
        
        public MusicIntegrator(SoundManager soundManager) {
            this.soundManager = soundManager;
        }
        
        public void playMenuMusic() {
            switchMusicIfNeeded(MusicContext.MENU, AudioAssetRegistry.MusicWAVAssets.getAsset("THEME_MAIN"), "menu");
        }
        
        public void playLevelCalm() {
            switchMusicIfNeeded(MusicContext.LEVEL_CALM, AudioAssetRegistry.MusicWAVAssets.getAsset("THEME_CALM"), "level_calm");
        }
        
        public void playLevelBattle() {
            switchMusicIfNeeded(MusicContext.LEVEL_BATTLE, AudioAssetRegistry.MusicWAVAssets.getAsset("THEME_BATTLE"), "level_battle");
        }
        
        public void playBossFight() {
            switchMusicIfNeeded(MusicContext.BOSS_FIGHT, AudioAssetRegistry.MusicWAVAssets.getAsset("THEME_BATTLE"), "boss_fight");
        }
        
        public void playVictory() {
            switchMusicIfNeeded(MusicContext.VICTORY, AudioAssetRegistry.MusicWAVAssets.getAsset("MELODY_WIN"), "victory");
        }
        
        public void stopMusic() {
            soundManager.stopMusic();
            currentTrack = "";
            currentContext = MusicContext.NONE;
        }
        
        public void resumeMusic() {
            if (!currentTrack.isEmpty() && currentContext != MusicContext.NONE) {
                soundManager.playMusic(currentTrack);
            }
        }
        
        private void switchMusicIfNeeded(MusicContext context, String trackPath, String displayName) {
            if (currentContext != context) {
                currentContext = context;
                currentTrack = trackPath;
                if (trackPath != null) {
                    soundManager.playMusic(trackPath);
                }
            }
        }
        
        public MusicContext getCurrentContext() {
            return currentContext;
        }
        
        public String getCurrentTrack() {
            return currentTrack;
        }
        
        public boolean isMusicPlaying() {
            return !currentMusic.isEmpty();
        }
        
        private String currentMusic = "";
    }
    
    // ════════════════════════════════════════════════════════════════════════════════════
    //                        SOUND EFFECT TRIGGER (From SoundEffectTrigger.java)
    // ════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Utility for gameplay sound triggers
     */
    public static class SoundEffectTrigger {
        
        private static final SoundManager soundManager = SoundManager.getInstance();
        
        public static void playWeaponFire(String weaponType) {
            String soundName = "fire_" + weaponType.toLowerCase();
            soundManager.playSFX(soundName);
        }
        
        public static void playShotgunFire() {
            soundManager.playSFX("fire_shotgun");
        }
        
        public static void playRifleFire() {
            soundManager.playSFX("fire_rifle");
        }
        
        public static void playPistolFire() {
            soundManager.playSFX("fire_pistol");
        }
        
        public static void playImpactHit() {
            soundManager.playSFX("impact_hit");
        }
        
        public static void playEnemyDeath() {
            soundManager.playSFX("impact_enemy_death");
        }
        
        public static void playItemCollect() {
            soundManager.playSFX("impact_collect");
        }
        
        public static void playPlayerJump() {
            soundManager.playSFX("player_jump");
        }
        
        public static void playPlayerHurt() {
            soundManager.playSFX("player_hurt");
        }
        
        public static void playPlayerHeal() {
            soundManager.playSFX("player_heal");
        }
        
        public static void playUISelect() {
            soundManager.playSFX("ui_select");
        }
        
        public static void playUIConfirm() {
            soundManager.playSFX("ui_confirm");
        }
        
        public static void playUIBack() {
            soundManager.playSFX("ui_back");
        }
        
        public static void playLevel1Music() {
            soundManager.playMusic("music_level1");
        }
        
        public static void playLevel2Music() {
            soundManager.playMusic("music_level2");
        }
        
        public static void playBossMusic() {
            soundManager.playMusic("music_boss");
        }
    }
}
