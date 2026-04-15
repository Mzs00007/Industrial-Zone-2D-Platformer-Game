/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class BrawlerHandProfile {
    public static final String CHARACTER_TYPE = "brawler";
    public static final String CHARACTER_DESC = "Melee bruiser - powerful grip, slow but devastating";
    public static final float HAND_SPEED_MULTIPLIER = 0.8f;
    public static final float REACH_DISTANCE = 100.0f;
    public static final float GRIP_STRENGTH = 1.2f;
    public static final int LEFT_HAND_X = -22;
    public static final int LEFT_HAND_Y = -12;
    public static final int RIGHT_HAND_X = 22;
    public static final int RIGHT_HAND_Y = -12;
public class HandJoints {
        public static final int WRIST_OFFSET_X = 8;
        public static final int WRIST_OFFSET_Y = 5;
        public static final int PALM_OFFSET_X = 12;
        public static final int PALM_OFFSET_Y = 8;
        public static final int FINGER_SPREAD = 6;
    }
public class AnimationOffsets {
        public static final int REACH_X_OFFSET = 32;
        public static final int REACH_Y_OFFSET = -8;
        public static final int REACH_DURATION_MS = 400;
        public static final int MELEE_X_OFFSET = 45;
        public static final int MELEE_Y_OFFSET = 12;
        public static final int MELEE_DURATION_MS = 550;
        public static final int AIM_X_OFFSET = 38;
        public static final int AIM_Y_OFFSET = -10;
        public static final int AIM_DURATION_MS = 250;
        public static final int PICKUP_X_OFFSET = 28;
        public static final int PICKUP_Y_OFFSET = 15;
        public static final int PICKUP_DURATION_MS = 550;
    }
}
