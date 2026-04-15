/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
public class HandGripSelector {
    public static final String SYSTEM_TYPE = "hand_grip_selector";

    public static String selectGripPose(String string, int n) {
        int n2 = Math.max(0, Math.min(9, n));
        switch (string.toLowerCase()) {
            case "biker": {
                return AnimationAndSpriteLoader.WeaponHandPoses.BikerHands.ALL_GRIP_POSES[n2];
            }
            case "punk": {
                return AnimationAndSpriteLoader.WeaponHandPoses.PunkHands.ALL_GRIP_POSES[n2];
            }
            case "cyborg": {
                return AnimationAndSpriteLoader.WeaponHandPoses.CyborgHands.ALL_GRIP_POSES[n2];
            }
        }
        return AnimationAndSpriteLoader.WeaponHandPoses.BikerHands.ALL_GRIP_POSES[0];
    }

    public static String getHandPoseDirectory(String string) {
        switch (string.toLowerCase()) {
            case "biker": {
                return "Resources/industrial-zone/weapons/1/3 Hands/1 Biker";
            }
            case "punk": {
                return "Resources/industrial-zone/weapons/1/3 Hands/2 Punk";
            }
            case "cyborg": {
                return "Resources/industrial-zone/weapons/1/3 Hands/3 Cyborg";
            }
        }
        return "Resources/industrial-zone/weapons/1/3 Hands/1 Biker";
    }

    public static String[] getAllGripAngles(String string) {
        switch (string.toLowerCase()) {
            case "biker": {
                return AnimationAndSpriteLoader.WeaponHandPoses.BikerHands.ALL_GRIP_POSES;
            }
            case "punk": {
                return AnimationAndSpriteLoader.WeaponHandPoses.PunkHands.ALL_GRIP_POSES;
            }
            case "cyborg": {
                return AnimationAndSpriteLoader.WeaponHandPoses.CyborgHands.ALL_GRIP_POSES;
            }
        }
        return AnimationAndSpriteLoader.WeaponHandPoses.BikerHands.ALL_GRIP_POSES;
    }
}
