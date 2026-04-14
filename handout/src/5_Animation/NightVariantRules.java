/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static final class AnimationAndSpriteLoader.ParallaxRenderingPipeline.Phase2VariantSelection.NightVariantRules {
    public static final String VARIANT_NAME = "NIGHT";
    public static final String LEVEL = "Power Station Level 2 ONLY";
    public static final String TIME_RANGE = "18:00 to 6:00 (12 hours)";
    public static final String SKY_COLOR = "Dark gray/black - Minimal light";
    public static final String SILHOUETTES = "Dimmed visibility - Subtle gray shapes";
    public static final String VISIBILITY = "50-70% - Limited detail, atmosphere heavy";
    public static final String LAYER_FILES = "Night/* files";
    public static final String[] LAYERS = new String[]{"Night/1.png - Sky: Dark gray night atmosphere, possibly stars/moon", "Night/2.png - Far tower: Dimmed silhouette (0.15x parallax)", "Night/3.png - Mid structure: Gray-blue limited detail (0.30x)", "Night/4.png - Near structure: Very dark, minimal piping visible (0.45x)", "Night/5.png - Foreground: Darkest, only outline visible (0.60x)"};
}
