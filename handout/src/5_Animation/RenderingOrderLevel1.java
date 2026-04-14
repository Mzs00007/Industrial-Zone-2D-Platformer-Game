/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static final class AnimationAndSpriteLoader.ParallaxRenderingPipeline.Phase4LayerRendering.RenderingOrderLevel1 {
    public static final String LEVEL = "Industrial Zone Level 1";
    public static final String RENDER_ORDER = "STEP 1: Layer 1 (Sky Base) - Parallax 0.0x\n        \u2022 Draw first (background)\n        \u2022 No parallax scrolling (stays static)\n        \u2022 Fills entire background area\n        \u2022 Scroll offset: 0 pixels (always)\n\nSTEP 2: Layer 2 (Fractal Tree) - Parallax 0.1x\n        \u2022 Draw second\n        \u2022 Very slow parallax movement\n        \u2022 Calculated offset: playerX * 0.1\n        \u2022 Updates rarely (slow scrolling effect)\n\nSTEP 3: Layer 3 (Far Factory) - Parallax 0.25x\n        \u2022 Draw third\n        \u2022 Slow parallax movement\n        \u2022 Calculated offset: playerX * 0.25\n        \u2022 Moves slower than camera\n\nSTEP 4: Layer 4 (Mid Factory) - Parallax 0.35x\n        \u2022 Draw fourth\n        \u2022 Medium parallax movement\n        \u2022 Calculated offset: playerX * 0.35\n        \u2022 More responsive than far layer\n\nSTEP 5: Layer 5 (Near Factory) - Parallax 0.55x\n        \u2022 Draw last (foreground)\n        \u2022 Fastest parallax movement\n        \u2022 Calculated offset: playerX * 0.55\n        \u2022 Most responsive to camera, creates depth\n";
}
