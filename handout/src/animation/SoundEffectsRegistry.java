/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.SoundEffectsRegistry {
    public static final String TYPE_SFX = "sound_effects_system";
    public static final String DIRECTORY = "Resources/industrial-zone/audio/sfx";
    public static final String CATEGORY_IMPACT = "impact";
    public static final String CATEGORY_MOVEMENT = "movement";
    public static final String CATEGORY_INTERACTION = "interaction";
    public static final String CATEGORY_FEEDBACK = "feedback";
    public static final String CATEGORY_ENEMY = "enemy";
    public static final String CATEGORY_ENVIRONMENT = "environment";
    public static final String SFX_BULLET_HIT = "Laser_sword_1.wav";
    public static final String SFX_EXPLOSION = "Explosion.wav";
    public static final String SFX_BOMB_DROP = "Bomb_drop.wav";
    public static final String SFX_FOOTSTEP_1 = "Samurai_footstep_1.wav";
    public static final String SFX_FOOTSTEP_2 = "Samurai_footstep_2.wav";
    public static final String SFX_WHOOSH = "Flying_platform_attack_1.wav";
    public static final String SFX_DOOR_OPEN = "Door_with_password.wav";
    public static final String SFX_DOOR_CLOSE = "Sliding_doors.wav";
    public static final String SFX_DOOR_LOCKED = "Unlocked_chest.wav";
    public static final String SFX_CHEST_OPEN = "Unlocked_chest.wav";
    public static final String SFX_COLLECTIBLE = "Click_digital_1.wav";
    public static final String SFX_BELL = "Bell_on_the_door.wav";
    public static final String SFX_ZIPLINE = "Zipline_loopable.wav";
    public static final String SFX_PORTAL = "Portal_1.wav";
    public static final String SFX_ELEVATOR = "Elevator_motor.wav";
    public static final String SFX_ROLLER_DOOR = "Roller_doors.wav";
    public static final String SFX_PLATFORM = "Lift_mechanism.wav";
    public static final String SFX_ROBOT_ATTACK_1 = "Flying_platform_attack_1.wav";
    public static final String SFX_ROBOT_ATTACK_2 = "Flying_platform_attack_2.wav";
    public static final String SFX_ROBOT_WALK = "Hovering_robot_walk_loopable.wav";
    public static final String SFX_ROBOT_STING = "Hovering_robot_sting.wav";
    public static final String SFX_KARATEKA_ATTACK = "Karateka_attack.wav";
    public static final String SFX_SAMURAI_DEATH = "Samurai_death.wav";
    public static final String SFX_CLICK_1 = "Click_digital_1.wav";
    public static final String SFX_CLICK_2 = "Click_digital_2.wav";
    public static final String SFX_LASER_SWORD = "Laser_sword_2.wav";
    public static final String[] ALL_SFX_FILES = new String[]{"Alternative_theme_Chinese_Street.wav", "Battle_theme_Chinese_Street.wav", "Bell_on_the_door.wav", "Bomb_drop.wav", "Calm_theme_Chinese_Street.wav", "Click_digital_1.wav", "Click_digital_2.wav", "Creating_wooden_door.wav", "Door_with_password.wav", "Elevator_motor.wav", "Explosion.wav", "Flying_platform_attack_1.wav", "Flying_platform_attack_2.wav", "Hovering_robot_sting.wav", "Hovering_robot_walk_loopable.wav", "Karateka_attack.wav", "Laser_sword_1.wav", "Laser_sword_2.wav", "Lift_mechanism.wav", "Main_theme_Chinese_Street.wav", "Melody_of_attraction_loopable.wav", "Melody_of_the_win.wav", "Portal_1.wav", "Portal_2.wav", "Portal_closing.wav", "Portal_moving.wav", "Push_slide_door.wav", "Roller_doors.wav", "Samurai_death.wav", "Samurai_footstep_1.wav", "Samurai_footstep_2.wav", "Sliding_doors.wav", "Stealthy_theme_loopable.wav", "Unlocked_chest.wav", "Zipline_loopable.wav"};
    public static final float VOLUME_IMPACT = 1.0f;
    public static final float VOLUME_MOVEMENT = 0.7f;
    public static final float VOLUME_INTERACTION = 0.8f;
    public static final float VOLUME_FEEDBACK = 0.6f;
    public static final float VOLUME_ENEMY = 0.9f;
    public static final float VOLUME_ENVIRONMENT = 0.75f;

    public static String getSoundFile(String string) {
        for (String string2 : ALL_SFX_FILES) {
            if (!string2.equalsIgnoreCase(string) && !string2.replace(".wav", "").equalsIgnoreCase(string)) continue;
            return AnimationAndSpriteLoader.AUDIO_SFX + string2;
        }
        AnimationAndSpriteLoader.logError("Sound effect not found: " + string);
        return null;
    }

    public static String getSoundFileByIndex(int n) {
        if (n >= 0 && n < ALL_SFX_FILES.length) {
            return AnimationAndSpriteLoader.AUDIO_SFX + ALL_SFX_FILES[n];
        }
        AnimationAndSpriteLoader.logError("Invalid SFX index: " + n);
        return null;
    }

    public static float getVolumeForCategory(String string) {
        switch (string.toLowerCase()) {
            case "impact": {
                return 1.0f;
            }
            case "movement": {
                return 0.7f;
            }
            case "interaction": {
                return 0.8f;
            }
            case "feedback": {
                return 0.6f;
            }
            case "enemy": {
                return 0.9f;
            }
            case "environment": {
                return 0.75f;
            }
        }
        return 1.0f;
    }
}
