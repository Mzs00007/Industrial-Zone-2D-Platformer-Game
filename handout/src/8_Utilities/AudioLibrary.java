/*
 * Decompiled with CFR 0.152.
 */
package audio;

import audio.AudioSystem;
import java.util.HashMap;
import java.util.Map;

public static class AudioSystem.AudioLibrary {
    private Map<String, AudioSystem.SoundEffect> soundEffects = new HashMap<String, AudioSystem.SoundEffect>();
    private int totalSoundsLoaded = 0;
    private float totalMemoryUsed = 0.0f;

    public void registerSound(String string, AudioSystem.SoundEffect soundEffect) {
        this.soundEffects.put(string, soundEffect);
        ++this.totalSoundsLoaded;
    }

    public AudioSystem.SoundEffect getSound(String string) {
        return this.soundEffects.getOrDefault(string, null);
    }

    public boolean hasSound(String string) {
        return this.soundEffects.containsKey(string);
    }

    public void loadDefaultSounds() {
        this.registerSound("jump", new AudioSystem.SoundEffect("jump", "Resources/industrial-zone/audio/sfx/jump.wav"));
        this.registerSound("land", new AudioSystem.SoundEffect("land", "Resources/industrial-zone/audio/sfx/land.wav"));
        this.registerSound("fall", new AudioSystem.SoundEffect("fall", "Resources/industrial-zone/audio/sfx/fall.wav"));
        this.registerSound("dash", new AudioSystem.SoundEffect("dash", "Resources/industrial-zone/audio/sfx/dash.wav"));
        this.registerSound("hit", new AudioSystem.SoundEffect("hit", "Resources/industrial-zone/audio/sfx/hit.wav"));
        this.registerSound("punch", new AudioSystem.SoundEffect("punch", "Resources/industrial-zone/audio/sfx/punch.wav"));
        this.registerSound("kick", new AudioSystem.SoundEffect("kick", "Resources/industrial-zone/audio/sfx/kick.wav"));
        this.registerSound("death", new AudioSystem.SoundEffect("death", "Resources/industrial-zone/audio/sfx/death.wav"));
        this.registerSound("hurt", new AudioSystem.SoundEffect("hurt", "Resources/industrial-zone/audio/sfx/hurt.wav"));
        this.registerSound("item_collect", new AudioSystem.SoundEffect("item_collect", "Resources/industrial-zone/audio/sfx/item_collect.wav"));
        this.registerSound("powerup", new AudioSystem.SoundEffect("powerup", "Resources/industrial-zone/audio/sfx/powerup.wav"));
        this.registerSound("coin", new AudioSystem.SoundEffect("coin", "Resources/industrial-zone/audio/sfx/coin.wav"));
        this.registerSound("explosion", new AudioSystem.SoundEffect("explosion", "Resources/industrial-zone/audio/sfx/explosion.wav"));
        this.registerSound("ui_click", new AudioSystem.SoundEffect("ui_click", "Resources/industrial-zone/audio/sfx/ui_click.wav"));
        this.registerSound("ui_hover", new AudioSystem.SoundEffect("ui_hover", "Resources/industrial-zone/audio/sfx/ui_hover.wav"));
        this.registerSound("ui_select", new AudioSystem.SoundEffect("ui_select", "Resources/industrial-zone/audio/sfx/ui_select.wav"));
        this.registerSound("ui_back", new AudioSystem.SoundEffect("ui_back", "Resources/industrial-zone/audio/sfx/ui_back.wav"));
        this.registerSound("door_open", new AudioSystem.SoundEffect("door_open", "Resources/industrial-zone/audio/sfx/door_open.wav"));
        this.registerSound("water_splash", new AudioSystem.SoundEffect("water_splash", "Resources/industrial-zone/audio/sfx/water_splash.wav"));
        this.registerSound("fire_burn", new AudioSystem.SoundEffect("fire_burn", "Resources/industrial-zone/audio/sfx/fire_burn.wav"));
        this.registerSound("ambient_loop", new AudioSystem.SoundEffect("ambient_loop", "Resources/industrial-zone/audio/sfx/ambient.wav"));
        this.registerSound("wind", new AudioSystem.SoundEffect("wind", "Resources/industrial-zone/audio/sfx/wind.wav"));
        this.registerSound("rain", new AudioSystem.SoundEffect("rain", "Resources/industrial-zone/audio/sfx/rain.wav"));
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
