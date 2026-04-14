/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.ShootEffectsProperties {
    public static final String TYPE_SHOOT_EFFECTS = "shoot_effects_system";
    public static final String DIRECTORY = "Resources/industrial-zone/weapons/2/4 Shoot_effects";
    public static final int TOTAL_EFFECT_TYPES = 10;
    public static final int FRAMES_PER_EFFECT = 6;
    public static final int TIMING_MS = 60;
    public static final String EFFECT_TYPE_01 = "Basic_Flash";
    public static final String EFFECT_TYPE_02 = "Spark_Burst";
    public static final String EFFECT_TYPE_03 = "Cyan_Tracer";
    public static final String EFFECT_TYPE_04 = "Red_Tracer";
    public static final String EFFECT_TYPE_05 = "Yellow_Burst";
    public static final String EFFECT_TYPE_06 = "Blue_Spark";
    public static final String EFFECT_TYPE_07 = "White_Flash";
    public static final String EFFECT_TYPE_08 = "Orange_Burst";
    public static final String EFFECT_TYPE_09 = "Electric_Effect";
    public static final String EFFECT_TYPE_10 = "Heavy_Recoil_Flash";
    public static final String[] EFFECT_FILES = new String[]{"6_1.png", "6_2.png", "7_1.png", "7_2.png", "8_1.png", "8_2.png", "9_1.png", "9_2.png", "10_1.png", "10_2.png"};
    public static final int EFFECT_WIDTH = 40;
    public static final int EFFECT_HEIGHT = 40;
    public static final String COLOR_MUZZLE_YELLOW = "#FFDD00";
    public static final String COLOR_MUZZLE_ORANGE = "#FF9900";
    public static final String COLOR_MUZZLE_CYAN = "#00FFFF";
    public static final String COLOR_MUZZLE_RED = "#FF3333";

    public static String getEffectFile(int n) {
        if (n < 6 || n > 10) {
            AnimationAndSpriteLoader.logError("Invalid shoot effect type: " + n);
            return null;
        }
        int n2 = (n - 6) * 2;
        if (n2 >= EFFECT_FILES.length) {
            AnimationAndSpriteLoader.logError("Shoot effect file index out of bounds: " + n2);
            return null;
        }
        return AnimationAndSpriteLoader.WEAPON_2_EFFECTS + EFFECT_FILES[n2];
    }

    public static AnimationAndSpriteLoader.AnimationConfig getEffectAnimation(int n) {
        return new AnimationAndSpriteLoader.AnimationConfig(6, 60, "Muzzle flash effect " + n + " - plays on weapon fire");
    }
}
