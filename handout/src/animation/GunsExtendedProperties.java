/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.GunsExtendedProperties {
    public static final String TYPE_GUNS_EXTENDED = "guns_extended_system";
    public static final String DIRECTORY = "Resources/industrial-zone/weapons/2/2 Guns";
    public static final int TOTAL_GUN_TYPES = 10;
    public static final int VARIANTS_PER_GUN = 2;
    public static final int TOTAL_GUN_SPRITES = 20;
    public static final String GUN_TYPE_01 = "Gun_01";
    public static final String GUN_TYPE_02 = "Gun_02";
    public static final String GUN_TYPE_03 = "Gun_03";
    public static final String GUN_TYPE_04 = "Gun_04";
    public static final String GUN_TYPE_05 = "Gun_05";
    public static final String GUN_TYPE_06 = "Gun_06";
    public static final String GUN_TYPE_07 = "Gun_07";
    public static final String GUN_TYPE_08 = "Gun_08";
    public static final String GUN_TYPE_09 = "Gun_09";
    public static final String GUN_TYPE_10 = "Gun_10";
    public static final String[] GUN_FILES = new String[]{"1_1.png", "1_2.png", "2_1.png", "2_2.png", "3_1.png", "3_2.png", "4_1.png", "4_2.png", "5_1.png", "5_2.png", "6_1.png", "6_2.png", "7_1.png", "7_2.png", "8_1.png", "8_2.png", "9_1.png", "9_2.png", "10_1.png", "10_2.png"};
    public static final int GUN_WIDTH_DEFAULT = 32;
    public static final int GUN_HEIGHT_DEFAULT = 16;
    public static final String COLOR_PRIMARY = "#00CCFF";
    public static final String COLOR_SECONDARY = "#333333";

    public static String getGunFile(int n, int n2) {
        if (n < 1 || n > 10 || n2 < 1 || n2 > 2) {
            AnimationAndSpriteLoader.logError("Invalid gun type: " + n + " or variant: " + n2);
            return null;
        }
        int n3 = (n - 1) * 2 + (n2 - 1);
        return AnimationAndSpriteLoader.WEAPON_2_GUNS + GUN_FILES[n3];
    }

    public static String getGunType(int n) {
        switch (n) {
            case 1: {
                return GUN_TYPE_01;
            }
            case 2: {
                return GUN_TYPE_02;
            }
            case 3: {
                return GUN_TYPE_03;
            }
            case 4: {
                return GUN_TYPE_04;
            }
            case 5: {
                return GUN_TYPE_05;
            }
            case 6: {
                return GUN_TYPE_06;
            }
            case 7: {
                return GUN_TYPE_07;
            }
            case 8: {
                return GUN_TYPE_08;
            }
            case 9: {
                return GUN_TYPE_09;
            }
            case 10: {
                return GUN_TYPE_10;
            }
        }
        return null;
    }
}
