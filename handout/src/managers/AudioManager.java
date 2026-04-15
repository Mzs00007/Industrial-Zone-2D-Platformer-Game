package managers;

import utilities.AudioLibrary;
import utilities.SoundEffect;
import utilities.MidiTuner;

/**
 * AudioManager - Consolidated audio system using AudioLibrary + MidiTuner.
 * Manages SFX (via AudioLibrary) and background MIDI music (via MidiTuner).
 */
public class AudioManager {

    private AudioLibrary soundLibrary;
    private MidiTuner    midiTuner;
    private boolean      isInitialized = false;
    private float        masterVolume  = 1.0f;
    private float        sfxVolume     = 1.0f;
    private float        musicVolume   = 0.75f;
    private boolean      audioEnabled  = true;

    public AudioManager() {
        soundLibrary = new AudioLibrary();
        midiTuner    = new MidiTuner();
    }

    /** Load default sounds and mark initialized. */
    public void initialize() {
        if (isInitialized) return;
        try {
            System.out.println("[AudioManager] Initializing...");
            soundLibrary.loadAllSoundsFromDirectory(
                "Resources/industrial-zone/audio/sfx/");
            if (soundLibrary.getTotalSoundsLoaded() == 0) {
                // Fall back to hard-coded paths if directory scan found nothing
                soundLibrary.loadDefaultSounds();
            }
            isInitialized = true;
            System.out.println("[AudioManager] Ready. " +
                soundLibrary.getTotalSoundsLoaded() + " SFX loaded.");
        } catch (Exception e) {
            System.err.println("[AudioManager] Init error: " + e.getMessage());
        }
    }

    /** Play a MIDI file from the audio/music_midi folder (or any path searched by MidiTuner). */
    public void playMidi(String filename, boolean loop) {
        if (!audioEnabled) return;
        try {
            String path = "Resources/industrial-zone/audio/music_midi/" + filename;
            midiTuner.setPath(path);
            midiTuner.setLoop(loop ? MidiTuner.LOOP : MidiTuner.PLAY_ONCE);
            midiTuner.setVolume(musicVolume * masterVolume);
            midiTuner.play();
        } catch (Exception e) {
            System.err.println("[AudioManager] MIDI error: " + e.getMessage());
        }
    }

    /** Stop background MIDI. */
    public void stopMidi() {
        midiTuner.stop();
    }

    /** Play a named SFX registered in the library. */
    public void playSoundEffect(String name) {
        if (!audioEnabled || soundLibrary == null) return;
        SoundEffect se = soundLibrary.getSound(name);
        if (se != null) {
            se.setVolume(sfxVolume * masterVolume);
            se.play();
        }
    }

    /** Play a named SFX (positional – volume attenuated by distance to camera center). */
    public void playSoundEffect(String name, float worldX, float worldY) {
        playSoundEffect(name); // simple version: no positional attenuation
    }

    /** Stop all SFX; leave MIDI running. */
    public void stopAllSounds() {
        if (soundLibrary != null) soundLibrary.stopAllSounds();
    }

    public void setMasterVolume(float v) {
        masterVolume = Math.max(0f, Math.min(1f, v));
    }

    public void setSFXVolume(float v) {
        sfxVolume = Math.max(0f, Math.min(1f, v));
    }

    public void setMusicVolume(float v) {
        musicVolume = Math.max(0f, Math.min(1f, v));
        midiTuner.setVolume(musicVolume * masterVolume);
    }

    public void setAudioEnabled(boolean enabled) {
        this.audioEnabled = enabled;
        if (!enabled) {
            stopAllSounds();
            stopMidi();
        }
    }

    public void update(long deltaMillis) {
        // Reserved for future use (fades, streaming, etc.)
    }

    public void shutdown() {
        stopAllSounds();
        midiTuner.stop();
        midiTuner.close();
        isInitialized = false;
    }
}

