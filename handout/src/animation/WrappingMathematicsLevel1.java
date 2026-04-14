/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static final class AnimationAndSpriteLoader.ParallaxRenderingPipeline.Phase7LayerWrapping.WrappingMathematicsLevel1 {
    public static final String LEVEL = "Industrial Zone Level 1";
    public static final int ESTIMATED_LAYER_WIDTH = 1920;
    public static final float[] PARALLAX_FACTORS = new float[]{0.0f, 0.1f, 0.25f, 0.35f, 0.55f};
    public static final String[] WRAP_EXAMPLES_AT_PLAYER_X_3000 = new String[]{"Sky (0.0x): offset = 3000 * 0.0 = 0px (no wrap, static)", "Tree (0.1x): offset = 3000 * 0.1 = 300px, wrapped = 300 % 1920 = 300px", "Far (0.25x): offset = 3000 * 0.25 = 750px, wrapped = 750 % 1920 = 750px", "Mid (0.35x): offset = 3000 * 0.35 = 1050px, wrapped = 1050 % 1920 = 1050px", "Near (0.55x): offset = 3000 * 0.55 = 1650px, wrapped = 1650 % 1920 = 1650px"};
}
