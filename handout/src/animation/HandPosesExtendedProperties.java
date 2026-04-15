/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
public class HandPosesExtendedProperties {
    public static final String TYPE_HAND_POSES = "hand_poses_extended";
    public static final String DIRECTORY_BASE = "Resources/industrial-zone/weapons/2/3 Hands";
    public static final int HAND_POSES_PER_CHARACTER = 10;
    public static final int TOTAL_CHARACTERS = 3;
    public static final int TOTAL_HAND_POSES = 30;
    public static final String[] BIKER_HANDS = new String[]{"1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png", "10.png"};
    public static final String[] PUNK_HANDS = new String[]{"1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png", "10.png"};
    public static final String[] CYBORG_HANDS = new String[]{"1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png", "10.png"};
    public static final int HAND_WIDTH = 24;
    public static final int HAND_HEIGHT = 24;
    public static final String COLOR_SKIN_BIKER = "#FFB366";
    public static final String COLOR_SKIN_PUNK = "#FF9999";
    public static final String COLOR_SKIN_CYBORG = "#99CCFF";
    public static final int[] POSE_ANGLES = new int[]{0, 36, 72, 108, 144, 180, 216, 252, 288, 324};

    public static String getHandPose(String string, int n) {
        if (n < 0 || n >= 10) {
            AnimationAndSpriteLoader.logError("Invalid hand pose index: " + n);
            return null;
        }
        String string2 = "";
        String[] stringArray = null;
        if ("biker".equalsIgnoreCase(string)) {
            string2 = "1 Biker";
            stringArray = BIKER_HANDS;
        } else if ("punk".equalsIgnoreCase(string)) {
            string2 = "2 Punk";
            stringArray = PUNK_HANDS;
        } else if ("cyborg".equalsIgnoreCase(string)) {
            string2 = "3 Cyborg";
            stringArray = CYBORG_HANDS;
        } else {
            AnimationAndSpriteLoader.logError("Unknown character for hand poses: " + string);
            return null;
        }
        return AnimationAndSpriteLoader.WEAPON_2_HANDS + string2 + "/" + stringArray[n];
    }

    public static int getAngleForPose(int n) {
        if (n >= 0 && n < POSE_ANGLES.length) {
            return POSE_ANGLES[n];
        }
        return 0;
    }
}
