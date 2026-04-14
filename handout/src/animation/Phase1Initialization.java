/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.ParallaxBackgroundSystem.Phase1Initialization {
    public static final String PHASE_NAME = "PHASE 1: LAYER ASSET INITIALIZATION";

    public static final class Level2LoadSequence {
        public static final String LEVEL = "Power Station Level 2";
        public static final int LAYER_COUNT_PER_VARIANT = 5;
        public static final int TOTAL_VARIANT_SETS = 2;
        public static final String LOAD_TYPE = "Dual Variant with Overlay Blending";
        public static final String[] DAY_LAYERS = new String[]{"Sky (0.0x - static day sky)", "Far Tower (0.15x - daylight)", "Mid Tower (0.30x - daylight)", "Near Structure (0.45x - daylight)", "Foreground (0.60x - daylight)"};
        public static final String[] NIGHT_LAYERS = new String[]{"Sky (0.0x - static night sky)", "Far Tower (0.15x - nighttime)", "Mid Tower (0.30x - nighttime)", "Near Structure (0.45x - nighttime)", "Foreground (0.60x - nighttime)"};
        public static final String OVERLAY_ASSET = "Overlay.png (blending mask for smooth transitions)";
    }

    public static final class Level1LoadSequence {
        public static final String LEVEL = "Industrial Zone Level 1";
        public static final int LAYER_COUNT = 5;
        public static final String LOAD_TYPE = "Single Static Variant";
        public static final String[] LAYERS = new String[]{"Sky Base (0.0x parallax - static)", "Fractal Tree (0.1x parallax - very slow)", "Far Factory (0.25x parallax - slow)", "Mid Factory (0.35x parallax - medium)", "Near Factory (0.55x parallax - fast)"};
    }
}
