/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.ParallaxBackgroundSystem.Phase5OverlayBlending {
    public static final String PHASE_NAME = "PHASE 5: OVERLAY BLENDING";
    public static final String APPLIES_TO = "Level 2 (Power Station) ONLY";

    public static final class BlendingMechanic {
        public static final String OVERLAY_FILE = "Overlay.png (blending mask)";
        public static final float TRANSITION_DURATION_SECONDS = 45.0f;
        public static final String BLEND_FORMULA = "finalColor = dayColor \u00d7 (1 - alpha) + nightColor \u00d7 alpha\nwhere alpha goes from 0.0 \u2192 1.0 over 45 seconds\nResult: Smooth gradient transition across entire screen";

        public static final class SunsetTransition {
            public static final String TIME_WINDOW = "5:30 PM to 6:15 PM";
            public static final String DIRECTION = "Day \u2192 Night (alpha: 1.0 \u2192 0.0)";
        }

        public static final class SunriseTransition {
            public static final String TIME_WINDOW = "5:45 AM to 6:30 AM";
            public static final String DIRECTION = "Night \u2192 Day (alpha: 0.0 \u2192 1.0)";
        }
    }
}
