/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.TracerEffectSystem {
    public static TracerType getTracerForGunType(AnimationAndSpriteLoader.WeaponSystemCore.GunType gunType) {
        switch (gunType.ordinal()) {
            case 0: 
            case 1: {
                return TracerType.STANDARD_A;
            }
            case 2: 
            case 3: 
            case 4: {
                return TracerType.DOTTED;
            }
            case 5: {
                return TracerType.WAVE;
            }
            case 6: 
            case 7: {
                return TracerType.HEAVY;
            }
            case 8: {
                return TracerType.LASER;
            }
            case 9: {
                return TracerType.OUTLINE;
            }
        }
        return TracerType.STANDARD_A;
    }

    public static enum TracerType {
        STANDARD_A("Narrow"),
        STANDARD_SCATTER("Scatter"),
        DOTTED("Dotted"),
        SLASH("Slash"),
        HEAVY("Heavy"),
        BOLD("Bold"),
        WAVE("Wave"),
        JAGGED("Jagged"),
        LASER("Laser"),
        OUTLINE("Outline");

        public final String style;

        private TracerType(String string2) {
            this.style = string2;
        }
    }
}
