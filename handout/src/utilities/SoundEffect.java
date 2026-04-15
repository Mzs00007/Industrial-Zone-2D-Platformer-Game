package utilities;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundEffect {
    private String name;
    private String filePath;
    private float volume = 1.0f;
    private float pitch = 1.0f;
    private float pan = 0.0f;
    private boolean isPlaying = false;
    private boolean looping = false;
    private float playbackPosition = 0.0f;
    private float duration = 0.0f;
    private Clip clip;
    private long pausePosition = 0L;

    public SoundEffect(String string, String string2) {
        this.name = string;
        this.filePath = string2;
    }

    public void play() {
        try {
            if (clip != null && clip.isOpen()) { clip.close(); }
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(ais);
            applyVolume();
            pausePosition = 0L;
            if (looping) { clip.loop(Clip.LOOP_CONTINUOUSLY); }
            else          { clip.start(); }
            isPlaying = true;
            playbackPosition = 0.0f;
        } catch (Exception e) {
            System.err.println("[SoundEffect] Cannot play " + filePath + ": " + e.getMessage());
        }
    }

    public void stop() {
        if (clip != null) { clip.stop(); clip.close(); }
        isPlaying = false;
        playbackPosition = 0.0f;
        pausePosition = 0L;
    }

    public void pause() {
        if (clip != null && clip.isRunning()) {
            pausePosition = clip.getMicrosecondPosition();
            clip.stop();
        }
        isPlaying = false;
    }

    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.setMicrosecondPosition(pausePosition);
            clip.start();
            isPlaying = true;
        }
    }

    public void update(float f) {
        if (clip != null && isPlaying) {
            isPlaying = clip.isRunning();
            playbackPosition = clip.getMicrosecondPosition() / 1_000_000.0f;
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
        volume = Math.max(0.0f, Math.min(1.0f, f));
        applyVolume();
    }

    private void applyVolume() {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (volume > 0.0001f) ? (float)(20.0 * Math.log10(volume)) : gain.getMinimum();
            gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), dB)));
        }
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
        pan = Math.max(-1.0f, Math.min(1.0f, f));
        if (clip != null && clip.isControlSupported(FloatControl.Type.PAN)) {
            FloatControl panCtrl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
            panCtrl.setValue(Math.max(panCtrl.getMinimum(), Math.min(panCtrl.getMaximum(), pan)));
        }
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
