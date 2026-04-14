package managers;

import utilities.AudioSystem;
import utilities.MidiTuner;
import java.util.ArrayList;
import java.util.List;

/**
 * AudioManager - Consolidated audio system
 * 
 * Merges:
 * - audio/AudioSystem.java
 * - audio/MidiTuner.java
 * 
 * Manages all audio playback, mixing, and MIDI tuning for the game.
 */
public class AudioManager {
    private AudioSystem.Manager audioSystemManager;
    private MidiTuner midiTuner;
    private boolean isInitialized = false;
    
    /**
     * Constructor - instantiable manager (no static getInstance)
     */
    public AudioManager() {
        this.audioSystemManager = new AudioSystem.Manager();
        this.midiTuner = new MidiTuner();
    }
    
    /**
     * Initialize audio system
     */
    public void initialize() {
        if (isInitialized) return;
        try {
            System.out.println("[AudioManager] Initializing audio system...");
            midiTuner.initialize();
            isInitialized = true;
            System.out.println("[AudioManager] ✓ Audio system initialized");
        } catch (Exception e) {
            System.err.println("[AudioManager ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Update audio state
     */
    public void update(long deltaMillis) {
        if (!isInitialized) return;
        // Audio updates (volume changes, sound cleanup, etc.)
    }
    
    /**
     * Play a sound effect by name
     */
    public void playSoundEffect(String soundName) {
        if (audioSystemManager != null) {
            audioSystemManager.playSoundEffect(soundName);
        }
    }
    
    /**
     * Play a positional sound effect
     */
    public void playSoundEffect(String soundName, float x, float y) {
        if (audioSystemManager != null) {
            audioSystemManager.playSoundEffect(soundName, x, y);
        }
    }
    
    /**
     * Play background music
     */
    public void playMusic(String musicName, boolean loop) {
        if (audioSystemManager != null) {
            audioSystemManager.playMusic(musicName, loop);
        }
    }
    
    /**
     * Stop all playing sounds
     */
    public void stopAllSounds() {
        if (audioSystemManager != null) {
            audioSystemManager.stopAllSounds();
        }
    }
    
    /**
     * Set master volume
     */
    public void setMasterVolume(float volume) {
        if (audioSystemManager != null) {
            audioSystemManager.setMasterVolume(volume);
        }
    }
    
    /**
     * Set SFX volume
     */
    public void setSFXVolume(float volume) {
        if (audioSystemManager != null) {
            audioSystemManager.setSFXVolume(volume);
        }
    }
    
    /**
     * Set music volume
     */
    public void setMusicVolume(float volume) {
        if (audioSystemManager != null) {
            audioSystemManager.setMusicVolume(volume);
        }
    }
    
    /**
     * Enable/Disable audio
     */
    public void setAudioEnabled(boolean enabled) {
        if (audioSystemManager != null) {
            audioSystemManager.setAudioEnabled(enabled);
        }
    }
    
    /**
     * Shutdown audio system
     */
    public void shutdown() {
        if (audioSystemManager != null) {
            audioSystemManager.stopAllSounds();
        }
        isInitialized = false;
    }
}
