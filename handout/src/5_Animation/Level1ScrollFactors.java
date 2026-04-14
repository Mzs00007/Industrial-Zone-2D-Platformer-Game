/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static final class AnimationAndSpriteLoader.ParallaxRenderingPipeline.Phase3ScrollCalculation.Level1ScrollFactors {
    public static final String LEVEL = "Industrial Zone Level 1";
    public static final float[] FACTORS = new float[]{0.0f, 0.1f, 0.25f, 0.35f, 0.55f};
    public static final String[] LAYER_NAMES = new String[]{"Sky", "Fractal Tree", "Far Factory", "Mid Factory", "Near Factory"};

    public static final class ScrollBehavior {
        public static final String SKY_BEHAVIOR = "0.0x - NO SCROLL (Static sky, stays in place)";
        public static final String TREE_BEHAVIOR = "0.1x - VERY SLOW (Barely moves, very distant)";
        public static final String FAR_BEHAVIOR = "0.25x - SLOW (Quarter speed of camera, far background)";
        public static final String MID_BEHAVIOR = "0.35x - MEDIUM (Third speed of camera, mid-ground)";
        public static final String NEAR_BEHAVIOR = "0.55x - FAST (Half speed of camera, closest)";
    }
}
