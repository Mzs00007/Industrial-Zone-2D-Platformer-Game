/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.MusicAudioRegistry {
    public static final String TYPE_MUSIC = "music_audio_system";
    public static final String DIRECTORY_MIDI = "Resources/industrial-zone/audio/music_midi";
    public static final String DIRECTORY_WAV = "Resources/industrial-zone/audio/music_wav";
    public static final int TOTAL_MIDI_TRACKS = 5;
    public static final String[] MIDI_TRACKS = new String[]{"Track 1.mid", "Track 2.mid", "Track 3.mid", "Track 4.mid", "Track 5.mid"};
    public static final String MUSIC_MAIN_THEME_CHINESE = "Main_theme_Chinese_Street.wav";
    public static final String MUSIC_BATTLE_THEME = "Battle_theme_Chinese_Street.wav";
    public static final String MUSIC_CALM_THEME = "Calm_theme_Chinese_Street.wav";
    public static final String MUSIC_ALTERNATIVE_THEME = "Alternative_theme_Chinese_Street_treat.wav";
    public static final String MUSIC_STEALTHY_THEME = "Stealthy_theme_loopable.wav";
    public static final String[] MUSIC_WAV_TRACKS = new String[]{"Alternative_theme_Chinese_Street_treat.wav", "Battle_theme_Chinese_Street.wav", "Bell_on_the_door.wav", "Bomb_drop.wav", "Calm_theme_Chinese_Street.wav", "Click_digital_1.wav", "Click_digital_2.wav", "Creating_wooden_door.wav", "Door_with_password.wav", "Elevator_motor.wav", "Explosion.wav", "Flying_platform_attack_1.wav", "Flying_platform_attack_2.wav", "Hovering_robot_sting.wav", "Hovering_robot_walk_loopable.wav", "Karateka_attack.wav", "Laser_sword_1.wav", "Laser_sword_2.wav", "Lift_mechanism.wav", "Main_theme_Chinese_Street.wav", "Melody_of_attraction_loopable.wav", "Melody_of_the_win.wav", "Portal_1.wav", "Portal_2.wav", "Portal_closing.wav", "Portal_moving.wav", "Push_slide_door.wav", "Roller_doors.wav", "Samurai_death.wav", "Samurai_footstep_1.wav", "Samurai_footstep_2.wav", "Sliding_doors.wav", "Stealthy_theme_loopable.wav", "Unlocked_chest.wav", "Zipline_loopable.wav"};
    public static final String CATEGORY_MAIN_THEME = "main_theme";
    public static final String CATEGORY_BATTLE = "battle";
    public static final String CATEGORY_EXPLORATION = "exploration";
    public static final String CATEGORY_BOSS = "boss";
    public static final String CATEGORY_AMBIENT = "ambient";
    public static final String CATEGORY_MENU = "menu";
    public static final float DEFAULT_VOLUME = 1.0f;
    public static final float LOOP_CROSSFADE_MS = 50.0f;

    public static String getMidiTrack(int n) {
        if (n >= 0 && n < 5) {
            return AnimationAndSpriteLoader.AUDIO_MUSIC_MIDI + MIDI_TRACKS[n];
        }
        AnimationAndSpriteLoader.logError("Invalid MIDI track index: " + n);
        return null;
    }

    public static String getMusicWavTrack(int n) {
        if (n >= 0 && n < MUSIC_WAV_TRACKS.length) {
            return AnimationAndSpriteLoader.AUDIO_MUSIC_WAV + MUSIC_WAV_TRACKS[n];
        }
        AnimationAndSpriteLoader.logError("Invalid music WAV track index: " + n);
        return null;
    }

    public static String getMusicByName(String string) {
        for (String string2 : MUSIC_WAV_TRACKS) {
            if (!string2.equalsIgnoreCase(string)) continue;
            return AnimationAndSpriteLoader.AUDIO_MUSIC_WAV + string2;
        }
        AnimationAndSpriteLoader.logError("Music track not found: " + string);
        return null;
    }
}
