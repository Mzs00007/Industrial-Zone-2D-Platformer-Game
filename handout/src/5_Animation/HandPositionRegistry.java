/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.CharacterHandPositionSystem.HandPositionRegistry {
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
