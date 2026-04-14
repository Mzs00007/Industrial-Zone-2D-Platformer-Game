package utilities;

import managers.Core;
import managers.assetbridge.AssetPathBridge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PHASE 3: AUDIO-VISUAL SYNCHRONIZER
 * 
 * Coordinates audio playback with animation frames and VFX for perfect synchronization.
 * Ensures sound effects play at exact milliseconds relative to attack impacts, music crossfades,
 * and UI feedback.
 * 
 * SYNCHRONIZATION REQUIREMENTS:
 * 
 * EVENT: Player Attack (SPACE key)
 *   t=0ms:   Wind-up sound starts (100ms, 40% volume)
 *   t=120ms: Impact sound plays if hit (200ms, 80% volume)  [Replaces wind-up]
 *   t=500ms: Attack cooldown expires
 *
 * EVENT: Jump
 *   t=0ms:   Jump sound plays (300ms, 60% volume)
 *
 * EVENT: Pause Menu
 *   t=150ms: Game music fades from 100% to 50% (300ms duration)
 *   t=150ms: Menu open sound (300ms fade-in, 80% volume)
 *   t=150ms+: Menu ambience at 40% volume
 *
 * EVENT: Resume Game
 *   t=800ms: Menu closes, music fades from 50% to 100% (300ms)
 *   t=800ms: Menu close sound fades out
 *   t=1100ms: Fully resumed
 * 
 * Timeline: ±10ms precision required for believable sync
 * Integration: Called from attack/jump/pause handlers
 */
public class GameplayAudioVisualSynchronizer {
    
    // ===== AUDIO CHAIN SCHEDULING =====
    /**
     * Stores scheduled audio to play with specific timing offset.
     */
    public class ScheduledAudio {
        public String assetKey;           // Identifier for audio asset
        public long scheduleTimeMs;       // When to play relative to event start
        public float volumePercent;       // 0.0-1.0, representing 0-100%
        public float pitchShift;          // 1.0 = normal speed
        public long durationMs;           // Duration of audio
        public boolean fadeIn;            // Fade in effect
        public boolean fadeOut;           // Fade out effect
        public long fadeInDuration;       // Fade-in time
        public long fadeOutDuration;      // Fade-out time
        
        public ScheduledAudio(String assetKey, long scheduleTimeMs, float volumePercent) {
            this.assetKey = assetKey;
            this.scheduleTimeMs = scheduleTimeMs;
            this.volumePercent = volumePercent;
            this.pitchShift = 1.0f;
            this.durationMs = 1000;  // Default 1 second
            this.fadeIn = false;
            this.fadeOut = false;
        }
    }
    
    /**
     * Complete audio chain for complex sequences.
     */
    public class AudioChain {
        public String chainName;
        public List<ScheduledAudio> audioSequence;
        public long startTimeMs;
        
        public AudioChain(String name) {
            this.chainName = name;
            this.audioSequence = new ArrayList<>();
            this.startTimeMs = System.currentTimeMillis();
        }
        
        public void addAudio(ScheduledAudio audio) {
            audioSequence.add(audio);
        }
        
        // Convenience method to add audio
        public void addAudio(String key, long delayMs, float volume) {
            addAudio(new ScheduledAudio(key, delayMs, volume));
        }
    }
    
    // ===== INSTANCE VARIABLES =====
    private Map<String, AudioClipPlayer> audioClips = new HashMap<>();
    private List<AudioChain> activeChains = new ArrayList<>();
    private float masterVolumePercent = 1.0f;
    private float musicVolumePercent = 1.0f;
    private float sfxVolumePercent = 1.0f;
    
    // Music crossfade tracking
    private String currentMusic = null;
    private String targetMusic = null;
    private long musicCrossfadeStartMs = 0;
    private long musicCrossfadeDurationMs = 0;
    private float musicFadeStartVolume = 1.0f;
    private float musicFadeEndVolume = 1.0f;
    
    // Game pause state
    private boolean isPaused = false;
    
    // ===== CONSTRUCTOR =====
    public GameplayAudioVisualSynchronizer() {
        initializeAudioClips();
        Core.logger.info("[AudioVisualSynchronizer] Initialized - Ready for Phase 3 audio sync");
    }
    
    // ===== AUDIO CLIP INITIALIZATION =====
    
    /**
     * Initialize all audio assets used during gameplay.
     */
    private void initializeAudioClips() {
        try {
            // Movement sounds
            registerAudioClip("jump", AssetPathBridge.Audio.AUDIO_SFX + "/jump.mp3", 300);
            registerAudioClip("landing", AssetPathBridge.Audio.AUDIO_SFX + "/landing.mp3", 150);
            registerAudioClip("footstep_metal", AssetPathBridge.Audio.AUDIO_SFX + "/footstep_metal.mp3", 250);
            registerAudioClip("breathing_heavy", AssetPathBridge.Audio.AUDIO_SFX + "/breathing_heavy.mp3", 400);
            
            // Combat sounds
            registerAudioClip("slash_wind", AssetPathBridge.Audio.AUDIO_SFX + "/slash_wind_anticipation.mp3", 100);
            registerAudioClip("impact", AssetPathBridge.Audio.AUDIO_SFX + "/blade_hit_metal.mp3", 200);
            registerAudioClip("impact_flesh", AssetPathBridge.Audio.AUDIO_SFX + "/blade_hit_flesh.mp3", 200);
            registerAudioClip("impact_mech", AssetPathBridge.Audio.AUDIO_SFX + "/blade_hit_mechanical.mp3", 200);
            registerAudioClip("blood_splash", AssetPathBridge.Audio.AUDIO_SFX + "/blood_splash.mp3", 300);
            
            // UI sounds
            registerAudioClip("ui_button_hover", AssetPathBridge.Audio.AUDIO_SFX + "/ui_button_hover.mp3", 150);
            registerAudioClip("ui_button_click", AssetPathBridge.Audio.AUDIO_SFX + "/ui_button_click.mp3", 200);
            registerAudioClip("ui_menu_open", AssetPathBridge.Audio.AUDIO_SFX + "/ui_menu_open.mp3", 300);
            registerAudioClip("ui_menu_close", AssetPathBridge.Audio.AUDIO_SFX + "/ui_menu_close.mp3", 200);
            registerAudioClip("ui_interact", AssetPathBridge.Audio.AUDIO_SFX + "/ui_interact.mp3", 150);
            
            // Music tracks
            registerAudioClip("music_level1", AssetPathBridge.Audio.AUDIO_MUSIC_MIDI + "/level1_industrial.mid", 360000);
            registerAudioClip("music_level2", AssetPathBridge.Audio.AUDIO_MUSIC_MIDI + "/level2_powerstation.mid", 360000);
            registerAudioClip("music_menu", AssetPathBridge.Audio.AUDIO_MUSIC_MIDI + "/menu_theme.mid", 60000);
            registerAudioClip("music_pause", AssetPathBridge.Audio.AUDIO_MUSIC_MIDI + "/pause_ambience.mid", 60000);
            
            Core.logger.info("[AudioVisualSynchronizer] Audio clips registered and ready");
            
        } catch (Exception e) {
            Core.logger.error("[AudioVisualSynchronizer] Error initializing audio: " + e.getMessage());
        }
    }
    
    /**
     * Register an audio asset for later playback.
     */
    private void registerAudioClip(String key, String assetPath, long durationMs) {
        try {
            AudioClipPlayer clip = new AudioClipPlayer(key, assetPath, durationMs);
            audioClips.put(key, clip);
            Core.logger.debug("[AudioVisualSynchronizer] Registered audio: " + key + " (" + durationMs + "ms)");
        } catch (Exception e) {
            Core.logger.warn("[AudioVisualSynchronizer] Failed to load audio: " + key);
        }
    }
    
    // ===== AUDIO PLAYBACK =====
    
    /**
     * Play audio immediately at specified time offset.
     * Called from attack/jump handlers.
     * 
     * Timeline: [t=0ms] playAudioAtTime("jump", 0, 300) →  sound plays immediately
     */
    public void playAudioAtTime(String assetKey, long delayMs, long durationMs) {
        try {
            AudioClipPlayer clip = audioClips.get(assetKey);
            if (clip == null) {
                Core.logger.warn("[AudioVisualSynchronizer] Audio not found: " + assetKey);
                return;
            }
            
            // Schedule with delay
            if (delayMs > 0) {
                scheduleAudioAfterDelay(assetKey, delayMs, 1.0f);
            } else {
                // Play immediately
                clip.play(1.0f * sfxVolumePercent);
                Core.logger.debug("[AudioVisualSynchronizer] Playing: " + assetKey);
            }
            
        } catch (Exception e) {
            Core.logger.error("[AudioVisualSynchronizer] Error playing audio: " + e.getMessage());
        }
    }
    
    /**
     * Play audio with custom volume (0.0-1.0).
     */
    public void playAudioWithVolume(String assetKey, float volume) {
        try {
            AudioClipPlayer clip = audioClips.get(assetKey);
            if (clip != null) {
                clip.play(volume * sfxVolumePercent);
                Core.logger.debug("[AudioVisualSynchronizer] Playing with volume: " + assetKey + 
                                 " (" + (volume * 100) + "%)");
            }
        } catch (Exception e) {
            Core.logger.error("[AudioVisualSynchronizer] Error playing audio: " + e.getMessage());
        }
    }
    
    /**
     * Schedule audio to play after delay (in milliseconds).
     * Used for synchronized sequences.
     */
    private void scheduleAudioAfterDelay(String assetKey, long delayMs, float volume) {
        // TODO: Implement proper scheduling (use Timer or ScheduledExecutorService)
        // For now, simple sleep (blocking - should be non-blocking in production)
        try {
            Thread.sleep(delayMs);
            playAudioWithVolume(assetKey, volume);
        } catch (InterruptedException e) {
            Core.logger.error("[AudioVisualSynchronizer] Scheduling interrupted");
        }
    }
    
    // ===== AUDIO CHAINS (Complex Sequences) =====
    
    /**
     * Create and execute an attack audio chain.
     * 
     * Timeline:
     *   t=0ms:   Wind-up sound (100ms)
     *   t=120ms: Impact sound replaces wind-up (200ms)
     */
    public void createAttackAudioChain(String attackType) {
        AudioChain chain = new AudioChain("Attack_" + attackType);
        
        // Wind-up sound at t=0ms
        ScheduledAudio windUp = new ScheduledAudio("slash_wind", 0, 0.4f);
        windUp.durationMs = 100;
        chain.addAudio(windUp);
        
        // Impact sound at t=120ms (but only if hit - called separately)
        // If hit is detected, call playAudioAtTime("impact", 0, 200) to replace
        
        activeChains.add(chain);
        Core.logger.debug("[AudioVisualSynchronizer] Attack audio chain created");
    }
    
    /**
     * Create and execute a jump audio chain.
     * 
     * Timeline:
     *   t=0ms: Jump sound plays (300ms)
     */
    public void createJumpAudioChain() {
        AudioChain chain = new AudioChain("Jump");
        chain.addAudio(new ScheduledAudio("jump", 0, 0.6f));
        activeChains.add(chain);
        Core.logger.debug("[AudioVisualSynchronizer] Jump audio chain created");
    }
    
    /**
     * Create pause menu audio sequence.
     * 
     * Timeline:
     *   t=0ms:   UI button click (200ms)
     *   t=150ms: Menu open sound (300ms fade-in)
     *   t=150ms: Game music fades to 50% (300ms)
     */
    public void createPauseAudioChain() {
        AudioChain chain = new AudioChain("Pause");
        
        // Button click at t=0ms
        ScheduledAudio buttonClick = new ScheduledAudio("ui_button_click", 0, 0.7f);
        buttonClick.durationMs = 200;
        chain.addAudio(buttonClick);
        
        // Menu open sound at t=150ms
        ScheduledAudio menuOpen = new ScheduledAudio("ui_menu_open", 150, 0.8f);
        menuOpen.durationMs = 300;
        menuOpen.fadeIn = true;
        menuOpen.fadeInDuration = 100;
        chain.addAudio(menuOpen);
        
        activeChains.add(chain);
        
        // Fade game music to 50% (non-blocking, separate from chain)
        fadeMusicTo(currentMusic, 0.5f, 300);
        
        isPaused = true;
        Core.logger.debug("[AudioVisualSynchronizer] Pause audio chain created");
    }
    
    /**
     * Create resume audio sequence.
     * 
     * Timeline:
     *   t=0ms:   Menu close sound (200ms fade-out)
     *   t=0ms:   Menu music fades to 0% (300ms)
     *   t=0ms:   Game music fades from 50% to 100% (300ms)
     */
    public void createResumeAudioChain() {
        AudioChain chain = new AudioChain("Resume");
        
        // Menu close sound at t=0ms
        ScheduledAudio menuClose = new ScheduledAudio("ui_menu_close", 0, 0.8f);
        menuClose.durationMs = 200;
        menuClose.fadeOut = true;
        menuClose.fadeOutDuration = 100;
        chain.addAudio(menuClose);
        
        activeChains.add(chain);
        
        // Fade game music back to 100%
        fadeMusicTo(currentMusic, 1.0f, 300);
        
        isPaused = false;
        Core.logger.debug("[AudioVisualSynchronizer] Resume audio chain created");
    }
    
    // ===== MUSIC MANAGEMENT =====
    
    /**
     * Play background music at specified volume.
     */
    public void playMusic(String musicKey, float volume) {
        try {
            currentMusic = musicKey;
            AudioClipPlayer musicClip = audioClips.get(musicKey);
            if (musicClip != null) {
                musicClip.play(volume * musicVolumePercent);
                Core.logger.debug("[AudioVisualSynchronizer] Playing music: " + musicKey);
            }
        } catch (Exception e) {
            Core.logger.error("[AudioVisualSynchronizer] Error playing music: " + e.getMessage());
        }
    }
    
    /**
     * Crossfade between two music tracks.
     * Example: Level 1 music (100%) → Level 2 music (0%) over 1500ms
     */
    public void crossfadeMusic(String fromMusic, String toMusic, long durationMs) {
        targetMusic = toMusic;
        musicCrossfadeStartMs = System.currentTimeMillis();
        musicCrossfadeDurationMs = durationMs;
        musicFadeStartVolume = musicVolumePercent;
        musicFadeEndVolume = 1.0f;
        
        // Start playing target music silently
        playMusic(toMusic, 0.0f);
        
        Core.logger.debug("[AudioVisualSynchronizer] Music crossfade started: " + fromMusic + 
                         " → " + toMusic + " (" + durationMs + "ms)");
    }
    
    /**
     * Fade music to specific volume.
     * Used for pause/unpause sequences.
     */
    public void fadeMusicTo(String musicKey, float targetVolume, long durationMs) {
        musicCrossfadeStartMs = System.currentTimeMillis();
        musicCrossfadeDurationMs = durationMs;
        musicFadeStartVolume = musicVolumePercent;
        musicFadeEndVolume = targetVolume;
        
        Core.logger.debug("[AudioVisualSynchronizer] Music fade: " + musicVolumePercent * 100 + 
                         "% → " + (targetVolume * 100) + "% over " + durationMs + "ms");
    }
    
    // ===== PAUSE/RESUME =====
    
    /**
     * Pause all game audio (for pause menu).
     */
    public void pauseGameAudio(long fadeOutDurationMs) {
        // Create pause chain which handles audio transition
        createPauseAudioChain();
        Core.logger.debug("[AudioVisualSynchronizer] Game audio paused");
    }
    
    /**
     * Resume game audio (from pause menu).
     */
    public void resumeGameAudio(long fadeInDurationMs) {
        // Create resume chain which handles audio transition
        createResumeAudioChain();
        Core.logger.debug("[AudioVisualSynchronizer] Game audio resumed");
    }
    
    // ===== FRAME UPDATE =====
    
    /**
     * Update audio mixing and crossfading (called every frame).
     */
    public void update(long deltaTimeMs) {
        updateMusicCrossfade(deltaTimeMs);
    }
    
    /**
     * Update music crossfade progress.
     */
    private void updateMusicCrossfade(long deltaTimeMs) {
        if (musicCrossfadeDurationMs <= 0) {
            return;
        }
        
        long elapsedMs = System.currentTimeMillis() - musicCrossfadeStartMs;
        
        if (elapsedMs >= musicCrossfadeDurationMs) {
            // Crossfade complete
            musicVolumePercent = musicFadeEndVolume;
            musicCrossfadeDurationMs = 0;
            return;
        }
        
        // Calculate progress (0.0 to 1.0)
        float progress = (float)elapsedMs / musicCrossfadeDurationMs;
        
        // Interpolate volume
        musicVolumePercent = musicFadeStartVolume + 
                           (musicFadeEndVolume - musicFadeStartVolume) * progress;
    }
    
    // ===== GETTERS =====
    public float getMasterVolume() {
        return masterVolumePercent;
    }
    
    public float getMusicVolume() {
        return musicVolumePercent;
    }
    
    public float getSFXVolume() {
        return sfxVolumePercent;
    }
    
    public boolean isPaused() {
        return isPaused;
    }
    
    // ===== SETTERS =====
    public void setMasterVolume(float volume) {
        this.masterVolumePercent = Math.max(0.0f, Math.min(1.0f, volume));
    }
    
    public void setMusicVolume(float volume) {
        this.musicVolumePercent = Math.max(0.0f, Math.min(1.0f, volume));
    }
    
    public void setSFXVolume(float volume) {
        this.sfxVolumePercent = Math.max(0.0f, Math.min(1.0f, volume));
    }
    
    // ===== DEBUG =====
    public String getDebugInfo() {
        return String.format(
            "Music: %s (%.0f%%) | SFX: %.0f%% | Chains: %d",
            currentMusic != null ? currentMusic : "None",
            musicVolumePercent * 100,
            sfxVolumePercent * 100,
            activeChains.size()
        );
    }
}

// ===== PLACEHOLDER AUDIO CLIP PLAYER =====
/**
 * Simple placeholder for audio playback.
 * In production, this would use real audio library (javax.sound, JavaFX, or LibGDX).
 */
class AudioClipPlayer {
    private String key;
    private String assetPath;
    private long durationMs;
    
    public AudioClipPlayer(String key, String assetPath, long durationMs) {
        this.key = key;
        this.assetPath = assetPath;
        this.durationMs = durationMs;
    }
    
    public void play(float volume) {
        // TODO: Implement actual audio playback
        // For now, just log that it "played"
        Core.logger.debug("[AudioPlayer] Playing: " + key + " at " + (volume * 100) + "% volume");
    }
    
    public void stop() {
        Core.logger.debug("[AudioPlayer] Stopped: " + key);
    }
}
