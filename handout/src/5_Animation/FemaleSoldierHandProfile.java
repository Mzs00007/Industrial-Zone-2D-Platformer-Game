/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.CharacterHandPositionSystem.FemaleSoldierHandProfile {
    public static final String CHARACTER_TYPE = "female_soldier";
    public static final String CHARACTER_DESC = "Elite soldier - agile with precise hand control";
    public static final float HAND_SPEED_MULTIPLIER = 1.15f;
    public static final float REACH_DISTANCE = 85.0f;
    public static final float GRIP_STRENGTH = 0.85f;
    public static final int LEFT_HAND_X = -18;
    public static final int LEFT_HAND_Y = -8;
    public static final int RIGHT_HAND_X = 18;
    public static final int RIGHT_HAND_Y = -8;

    public static class HandJoints {
        public static final int WRIST_OFFSET_X = 5;
        public static final int WRIST_OFFSET_Y = 3;
        public static final int PALM_OFFSET_X = 8;
        public static final int PALM_OFFSET_Y = 5;
        public static final int FINGER_SPREAD = 4;
    }

    public static class AnimationOffsets {
        public static final int REACH_X_OFFSET = 25;
        public static final int REACH_Y_OFFSET = -5;
        public static final int REACH_DURATION_MS = 300;
        public static final int MELEE_X_OFFSET = 35;
        public static final int MELEE_Y_OFFSET = 5;
        public static final int MELEE_DURATION_MS = 400;
        public static final int AIM_X_OFFSET = 30;
        public static final int AIM_Y_OFFSET = -15;
        public static final int AIM_DURATION_MS = 200;
        public static final int PICKUP_X_OFFSET = 20;
        public static final int PICKUP_Y_OFFSET = 10;
        public static final int PICKUP_DURATION_MS = 450;
    }
}
