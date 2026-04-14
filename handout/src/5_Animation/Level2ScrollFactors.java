/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static final class AnimationAndSpriteLoader.ParallaxRenderingPipeline.Phase3ScrollCalculation.Level2ScrollFactors {
    public static final String LEVEL = "Power Station Level 2";
    public static final float[] FACTORS = new float[]{0.0f, 0.15f, 0.3f, 0.45f, 0.6f};
    public static final String[] LAYER_NAMES = new String[]{"Sky", "Far Tower", "Mid Tower", "Near Structure", "Foreground"};

    public static final class ScrollBehavior {
        public static final String SKY_BEHAVIOR = "0.0x - NO SCROLL (Static sky)";
        public static final String FAR_BEHAVIOR = "0.15x - SLOW (Distant tower, subtle movement)";
        public static final String MID_BEHAVIOR = "0.30x - MEDIUM-SLOW (Mid-ground structure)";
        public static final String NEAR_BEHAVIOR = "0.45x - MEDIUM-FAST (Near structure, noticeable movement)";
        public static final String FORE_BEHAVIOR = "0.60x - FAST (Foreground, most responsive)";
    }
}
