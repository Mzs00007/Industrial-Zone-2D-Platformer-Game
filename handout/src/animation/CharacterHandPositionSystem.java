/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class CharacterHandPositionSystem {
    public static final String SYSTEM_TYPE = "hand_positioning";
    public static final int CHARACTER_TYPES = 3;
public class HandAnimationTiming {
        public static final int TRANSITION_EASE_MS = 50;
        public static final int IDLE_FRAME_RATE = 100;
        public static final int ACTION_FRAME_RATE = 60;
        public static final float ROTATION_SPEED = 8.0f;
        public static final float INTERPOLATION_SMOOTHNESS = 0.85f;
    }
public class HandPositionRegistry {
        public static final String getCharacterType(String string) {
            if (string.contains("female")) {
                return "female_soldier";
            }
            if (string.contains("male")) {
                return "male_soldier";
            }
            if (string.contains("brawler")) {
                return "brawler";
            }
            return "male_soldier";
        }

        public static final float getHandSpeedMultiplier(String string) {
            switch (string) {
                case "female_soldier": {
                    return 1.15f;
                }
                case "male_soldier": {
                    return 1.0f;
                }
                case "brawler": {
                    return 0.8f;
                }
            }
            return 1.0f;
        }

        public static final float getReachDistance(String string) {
            switch (string) {
                case "female_soldier": {
                    return 85.0f;
                }
                case "male_soldier": {
                    return 90.0f;
                }
                case "brawler": {
                    return 100.0f;
                }
            }
            return 90.0f;
        }

        public static final float getGripStrength(String string) {
            switch (string) {
                case "female_soldier": {
                    return 0.85f;
                }
                case "male_soldier": {
                    return 0.95f;
                }
                case "brawler": {
                    return 1.2f;
                }
            }
            return 1.0f;
        }
    }
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
public class MaleSoldierHandProfile {
        public static final String CHARACTER_TYPE = "male_soldier";
        public static final String CHARACTER_DESC = "Standard soldier - balanced strength and control";
        public static final float HAND_SPEED_MULTIPLIER = 1.0f;
        public static final float REACH_DISTANCE = 90.0f;
        public static final float GRIP_STRENGTH = 0.95f;
        public static final int LEFT_HAND_X = -20;
        public static final int LEFT_HAND_Y = -10;
        public static final int RIGHT_HAND_X = 20;
        public static final int RIGHT_HAND_Y = -10;
public class HandJoints {
            public static final int WRIST_OFFSET_X = 6;
            public static final int WRIST_OFFSET_Y = 4;
            public static final int PALM_OFFSET_X = 10;
            public static final int PALM_OFFSET_Y = 6;
            public static final int FINGER_SPREAD = 5;
        }
public class AnimationOffsets {
            public static final int REACH_X_OFFSET = 28;
            public static final int REACH_Y_OFFSET = -3;
            public static final int REACH_DURATION_MS = 350;
            public static final int MELEE_X_OFFSET = 40;
            public static final int MELEE_Y_OFFSET = 8;
            public static final int MELEE_DURATION_MS = 450;
            public static final int AIM_X_OFFSET = 32;
            public static final int AIM_Y_OFFSET = -12;
            public static final int AIM_DURATION_MS = 220;
            public static final int PICKUP_X_OFFSET = 22;
            public static final int PICKUP_Y_OFFSET = 12;
            public static final int PICKUP_DURATION_MS = 500;
        }
    }
public class FemaleSoldierHandProfile {
        public static final String CHARACTER_TYPE = "female_soldier";
        public static final String CHARACTER_DESC = "Elite soldier - agile with precise hand control";
        public static final float HAND_SPEED_MULTIPLIER = 1.15f;
        public static final float REACH_DISTANCE = 85.0f;
        public static final float GRIP_STRENGTH = 0.85f;
        public static final int LEFT_HAND_X = -18;
        public static final int LEFT_HAND_Y = -8;
        public static final int RIGHT_HAND_X = 18;
        public static final int RIGHT_HAND_Y = -8;
public class HandJoints {
            public static final int WRIST_OFFSET_X = 5;
            public static final int WRIST_OFFSET_Y = 3;
            public static final int PALM_OFFSET_X = 8;
            public static final int PALM_OFFSET_Y = 5;
            public static final int FINGER_SPREAD = 4;
        }
public class AnimationOffsets {
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
}
