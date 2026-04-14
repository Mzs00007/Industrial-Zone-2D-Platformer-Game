/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static final class AnimationAndSpriteLoader.ParallaxRenderingPipeline.Phase2VariantSelection.Level1StaticVariant {
    public static final String LEVEL = "Industrial Zone Level 1";
    public static final String VARIANT_TYPE = "SINGLE STATIC VARIANT (No day/night)";
    public static final String APPLIES_TO_LEVEL_1 = "Level 1 skips this phase entirely";
    public static final String REASON = "Level 1 has no day/night cycle - uses fixed industrial daylight appearance";
    public static final String LEVEL_1_PROCESS = "PHASE 2 BEHAVIOR FOR LEVEL 1:\n\nInstead of selecting variants:\n- Load all 5 layers immediately (all use \"industrial daylight\" appearance)\n- No day/night state to check\n- No overlay blending needed\n- Skip directly to Phase 3\n\nAvailable single variant:\n- Layer 1 (Sky): BG_Layer1_SkyBase_SolidLavenderGrey_StaticFill_DrawFirstNoScroll.png\n- Layer 2 (Tree): BG_Layer2_FractalTreeSilhouette_MintSkyBlackCracks_ParallaxFactor015.png\n- Layer 3 (Far): BG_Layer3_FarFactorySilhouette_LightBlueIndustrial_ParallaxFactor025.png\n- Layer 4 (Mid): BG_Layer4_MidFactorySilhouette_MediumBluePipeDetail_ParallaxFactor040.png\n- Layer 5 (Near): BG_Layer5_NearFactorySilhouette_DarkNavyLargeTank_ParallaxFactor060.png\n\nAll layers use consistent \"industrial daylight\" color palette:\n- Sky: Lavender grey\n- Parallax layers: Various shades of blue (light \u2192 dark as they approach)\n- Overall atmosphere: Clear daytime industrial zone\n";
}
