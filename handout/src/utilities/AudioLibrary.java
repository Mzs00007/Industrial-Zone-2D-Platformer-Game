package utilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AudioLibrary {
    private Map<String, SoundEffect> soundEffects = new HashMap<String, SoundEffect>();
    private int totalSoundsLoaded = 0;
    private float totalMemoryUsed = 0.0f;

    public void registerSound(String key, SoundEffect soundEffect) {
        soundEffects.put(key, soundEffect);
        ++totalSoundsLoaded;
    }

    public SoundEffect getSound(String key) {
        return soundEffects.getOrDefault(key, null);
    }

    public boolean hasSound(String key) {
        return soundEffects.containsKey(key);
    }

    public void loadAllSoundsFromDirectory(String directory) {
        File dir = new File(directory);
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("[AudioLibrary] Directory not found: " + directory);
            return;
        }
        File[] files = dir.listFiles((d, name) -> name.endsWith(".wav") || name.endsWith(".WAV"));
        if (files == null) return;
        for (File f : files) {
            String filename = f.getName();
            String key = filename.startsWith("._") ? filename.substring(2) : filename;
            int dot = key.lastIndexOf('.');
            if (dot > 0) key = key.substring(0, dot);
            key = key.toLowerCase();
            registerSound(key, new SoundEffect(key, f.getPath()));
        }
    }

    public void loadDefaultSounds() {
        String base = "Resources/industrial-zone/audio/sfx/";
        String[][] sounds = {
            {"jump", "jump.wav"}, {"land", "land.wav"}, {"fall", "fall.wav"},
            {"dash", "dash.wav"}, {"hit", "hit.wav"}, {"punch", "punch.wav"},
            {"kick", "kick.wav"}, {"death", "death.wav"}, {"hurt", "hurt.wav"},
            {"item_collect", "item_collect.wav"}, {"powerup", "powerup.wav"},
            {"coin", "coin.wav"}, {"explosion", "explosion.wav"},
            {"ui_click", "ui_click.wav"}, {"ui_hover", "ui_hover.wav"},
            {"ui_select", "ui_select.wav"}, {"ui_back", "ui_back.wav"},
            {"door_open", "door_open.wav"}, {"water_splash", "water_splash.wav"},
            {"fire_burn", "fire_burn.wav"}, {"ambient_loop", "ambient.wav"},
            {"wind", "wind.wav"}, {"rain", "rain.wav"}
        };
        for (String[] s : sounds) {
            registerSound(s[0], new SoundEffect(s[0], base + s[1]));
        }
    }

    public void stopAllSounds() {
        for (SoundEffect se : soundEffects.values()) {
            if (se.isPlaying()) se.stop();
        }
    }

    public int getTotalSoundsLoaded() {
        return totalSoundsLoaded;
    }

    public float getTotalMemoryUsed() {
        return totalMemoryUsed;
    }

    public void addMemoryUsage(float f) {
        totalMemoryUsed += f;
    }
}
