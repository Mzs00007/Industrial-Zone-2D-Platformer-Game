/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
public class AdvancedBulletProperties {
    public static final String TYPE_BULLETS = "advanced_bullets";
    public static final String DIRECTORY = "Resources/industrial-zone/weapons/2/5 Bullets";
    public static final int TOTAL_BULLET_TYPES = 10;
    public static final int VARIANTS_PER_BULLET = 2;
    public static final int TOTAL_BULLET_SPRITES = 20;
    public static final String BULLET_BASIC = "Basic_Bullet";
    public static final String BULLET_TRACER_YELLOW = "Tracer_Yellow";
    public static final String BULLET_TRACER_CYAN = "Tracer_Cyan";
    public static final String BULLET_TRACER_ORANGE = "Tracer_Orange";
    public static final String BULLET_HEAVY = "Heavy_Round";
    public static final String BULLET_EXPLOSIVE = "Explosive";
    public static final String BULLET_ARMOR_PIERCING = "Armor_Piercing";
    public static final String BULLET_ELECTRIC = "Electric_Round";
    public static final String BULLET_SCATTER = "Scatter_Shot";
    public static final String BULLET_EXOTIC = "Exotic_Round";
    public static final String[] BULLET_FILES = new String[]{"1.png", "1_2.png", "2.png", "2_2.png", "3.png", "3_2.png", "4.png", "4_2.png", "5.png", "5_2.png", "6.png", "6_2.png", "7.png", "7_2.png", "8.png", "8_2.png", "9.png", "9_2.png", "10.png", "10_2.png"};
    public static final int BULLET_WIDTH = 8;
    public static final int BULLET_HEIGHT = 6;
    public static final String COLOR_BULLET_YELLOW = "#FFFF00";
    public static final String COLOR_BULLET_ORANGE = "#FF9900";
    public static final String COLOR_BULLET_CYAN = "#00FFFF";
    public static final String COLOR_BULLET_WHITE = "#FFFFFF";
    public static final String COLOR_BULLET_RED = "#FF0000";
    public static final float VELOCITY_BASIC = 8.0f;
    public static final float VELOCITY_HEAVY = 6.5f;
    public static final float VELOCITY_TRACER = 9.0f;
    public static final float VELOCITY_SCATTER = 7.0f;

    public static String getBulletFile(int n, int n2) {
        if (n < 1 || n > 10 || n2 < 1 || n2 > 2) {
            AnimationAndSpriteLoader.logError("Invalid bullet type: " + n + " or variant: " + n2);
            return null;
        }
        int n3 = (n - 1) * 2 + (n2 - 1);
        return AnimationAndSpriteLoader.WEAPON_2_BULLETS + BULLET_FILES[n3];
    }

    public static float getVelocity(String string) {
        if (string.contains("HEAVY")) {
            return 6.5f;
        }
        if (string.contains("TRACER")) {
            return 9.0f;
        }
        if (string.contains("SCATTER")) {
            return 7.0f;
        }
        return 8.0f;
    }
}
