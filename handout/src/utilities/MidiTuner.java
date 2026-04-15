/*
 * Decompiled with CFR 0.152.
 */
package utilities;

import java.io.File;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MidiTuner {
    public static final int LOOP = -1;
    public static final int PLAY_ONCE = 1;
    private Sequencer sequencer;
    private String audioPath;
    private int loopCount;
    private float volume = 1.0f;
    private boolean isPlaying = false;
    static final String AUDIO_BASE = "Resources/industrial-zone/audio/";

    public MidiTuner(String string, int n) {
        try {
            this.audioPath = string;
            this.loopCount = n;
            if (string.endsWith(".mid") || string.endsWith(".midi")) {
                this.initializeMidiSequencer();
            }
            System.out.println("[MidiTuner] \u2713 Initialized: " + string);
        }
        catch (Exception exception) {
            System.out.println("[MidiTuner] \u2717 Failed to initialize: " + exception.getMessage());
        }
    }

    private void initializeMidiSequencer() throws MidiUnavailableException {
        if (this.sequencer == null) {
            this.sequencer = MidiSystem.getSequencer();
            this.sequencer.open();
        }
    }

    public void play() {
        try {
            String[] stringArray = new String[]{this.audioPath, AUDIO_BASE + this.audioPath, "Resources/industrial-zone/" + this.audioPath, "audio/" + this.audioPath};
            File file = null;
            for (String string : stringArray) {
                File file2 = new File(string);
                if (!file2.exists()) continue;
                file = file2;
                System.out.println("[MidiTuner] Found audio at: " + string);
                break;
            }
            if (file == null) {
                System.out.println("[MidiTuner] \u26a0 Audio file not found: " + this.audioPath);
                return;
            }
            if (this.audioPath.endsWith(".mid") || this.audioPath.endsWith(".midi")) {
                if (this.sequencer == null) {
                    this.initializeMidiSequencer();
                }
                Sequence sequence = MidiSystem.getSequence(file);
                this.sequencer.setSequence(sequence);
                if (this.loopCount == -1) {
                    this.sequencer.setLoopCount(-1);
                } else {
                    this.sequencer.setLoopCount(this.loopCount - 1);
                }
                this.setVolume(this.volume);
                this.sequencer.start();
                this.isPlaying = true;
                System.out.println("[MidiTuner] \u25b6 Playing: " + this.audioPath);
            }
        }
        catch (Exception exception) {
            System.out.println("[MidiTuner] \u2717 Playback error: " + exception.getMessage());
        }
    }

    public void stop() {
        try {
            if (this.sequencer != null && this.sequencer.isRunning()) {
                this.sequencer.stop();
                this.isPlaying = false;
                System.out.println("[MidiTuner] \u23f9 Stopped: " + this.audioPath);
            }
        }
        catch (Exception exception) {
            System.out.println("[MidiTuner] \u2717 Stop error: " + exception.getMessage());
        }
    }

    public void pause() {
        try {
            if (this.sequencer != null && this.sequencer.isRunning()) {
                this.sequencer.stop();
                System.out.println("[MidiTuner] \u23f8 Paused: " + this.audioPath);
            }
        }
        catch (Exception exception) {
            System.out.println("[MidiTuner] \u2717 Pause error: " + exception.getMessage());
        }
    }

    public void resume() {
        try {
            if (this.sequencer != null && !this.sequencer.isRunning()) {
                this.sequencer.start();
                this.isPlaying = true;
                System.out.println("[MidiTuner] \u25b6 Resumed: " + this.audioPath);
            }
        }
        catch (Exception exception) {
            System.out.println("[MidiTuner] \u2717 Resume error: " + exception.getMessage());
        }
    }

    public void setVolume(float f) {
        try {
            this.volume = Math.max(0.0f, Math.min(1.0f, f));
            if (this.sequencer != null) {
                int n = (int)(this.volume * 127.0f);
                for (Track track : this.sequencer.getSequence().getTracks()) {
                    ShortMessage shortMessage = new ShortMessage();
                    shortMessage.setMessage(176, 0, 7, n);
                    track.add(new MidiEvent(shortMessage, 0L));
                }
            }
            System.out.println("[MidiTuner] \ud83d\udd0a Volume: " + String.format("%.1f", Float.valueOf(this.volume * 100.0f)) + "%");
        }
        catch (Exception exception) {
            System.out.println("[MidiTuner] \u2717 Volume error: " + exception.getMessage());
        }
    }

    public void fadeIn(float f, int n) {
        new Thread(() -> {
            int n2 = 20;
            int n3 = n / n2;
            float f2 = 0.0f;
            float f3 = f / (float)n2;
            for (int i = 0; i < n2; ++i) {
                float f4 = f2 + f3 * (float)i;
                this.setVolume(f4);
                try {
                    Thread.sleep(n3);
                    continue;
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
            this.setVolume(f);
        }).start();
    }

    public void fadeOut(int n) {
        new Thread(() -> {
            int n2 = 20;
            int n3 = n / n2;
            float f = this.volume;
            float f2 = f / (float)n2;
            for (int i = 0; i < n2; ++i) {
                float f3 = f - f2 * (float)i;
                this.setVolume(f3);
                try {
                    Thread.sleep(n3);
                    continue;
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
            this.setVolume(0.0f);
            this.stop();
        }).start();
    }

    public boolean isPlaying() {
        if (this.sequencer != null) {
            return this.sequencer.isRunning();
        }
        return this.isPlaying;
    }

    public float getVolume() {
        return this.volume;
    }

    public void close() {
        try {
            if (this.sequencer != null) {
                this.sequencer.stop();
                this.sequencer.close();
            }
            System.out.println("[MidiTuner] Closed: " + this.audioPath);
        }
        catch (Exception exception) {
            System.out.println("[MidiTuner] Error closing: " + exception.getMessage());
        }
    }

    public MidiTuner() {
        this("", PLAY_ONCE);
    }

    public void setPath(String path) {
        this.audioPath = path;
    }

    public void setLoop(int n) {
        this.loopCount = n;
    }

    public static void printAvailableAudio() {
        System.out.println("\n\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        System.out.println("MidiTuner - Available Audio Files");
        System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        File file2 = new File(AUDIO_BASE);
        if (!file2.exists()) {
            System.out.println("Audio directory not found: Resources/industrial-zone/audio/");
            System.out.println("Creating placeholder audio system...");
            return;
        }
        File[] fileArray = file2.listFiles((file, string) -> string.endsWith(".mid") || string.endsWith(".midi") || string.endsWith(".wav") || string.endsWith(".mp3"));
        if (fileArray != null && fileArray.length > 0) {
            for (File file3 : fileArray) {
                System.out.println("\u2713 " + file3.getName());
            }
        } else {
            System.out.println("No audio files found in: Resources/industrial-zone/audio/");
        }
        System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n");
    }
}
