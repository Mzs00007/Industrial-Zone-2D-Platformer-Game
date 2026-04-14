/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static final class AnimationAndSpriteLoader.ParallaxBackgroundSystem.Phase7LayerWrapping.WrappingExample {
    public static final String SCENARIO = "Player at X=5000, Parallax 0.4x, Layer width 1920px";
    public static final int RAW_OFFSET = 2000;
    public static final int WRAPPED = 80;
    public static final String DRAW_LOGIC = "Tile 1: Draw at position -80px\nTile 2: Draw at position -80 + 1920 = 1840px\nResult: Seamless visual transition, player never sees edge";
}
