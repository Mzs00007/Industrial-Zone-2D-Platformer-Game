/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.LevelBackgroundProperties.PowerStationLevel2Background {
    public static final String LEVEL_NAME = "Power Station Level 2";
    public static final String LEVEL_TYPE = "power_station_level_2";
    public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2";
    public static final String THEME = "Power station with day/night cycle";
    public static final int TOTAL_LAYERS = 5;
    public static final boolean HAS_DAY_NIGHT = true;
    public static final boolean HAS_OVERLAY = true;
    public static final String[] DAY_VARIANT = new String[]{"Day/BG_Layer1_SkyBase_LightGreyWhiteGradient_StaticFill_DrawFirst.png", "Day/BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png", "Day/BG_Layer3_FactoryTall_LightBlueChimney_ParallaxFactor025.png", "Day/BG_Layer4_DistantFactory_FaintSilhouette_ParallaxFactor040.png", "Day/BG_Layer5_FactoryRight_LightBlueVariant_ParallaxFactor060.png"};
    public static final String[] NIGHT_VARIANT = new String[]{"Night/BG_Layer1_SkyBase_DarkGreyGradient_StaticFill_DrawFirst.png", "Night/BG_Layer2_FactoryLeft_DarkGreySilhouette_ParallaxFactor015.png", "Night/BG_Layer3_FactoryTall_DarkGreyCentre_ParallaxFactor025.png", "Night/BG_Layer4_DistantFactory_VeryDarkFaint_ParallaxFactor040.png", "Night/BG_Layer5_FactoryRight_DarkGreyVariant_ParallaxFactor060.png"};
    public static final String OVERLAY_FILE = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/BG_Overlay_BlueYellowDiagonalGradient_ColourAtmosphere_DrawLast.png";
    public static final float[] PARALLAX_FACTORS = new float[]{0.0f, 0.15f, 0.3f, 0.45f, 0.6f};
    public static final String[] LAYER_NAMES = new String[]{"Sky", "Far Tower", "Mid Tower", "Near Structure", "Foreground"};
    public static final String[] LAYER_DESCRIPTIONS = new String[]{"Static sky background - no parallax (0.0x)", "Distant tower silhouette - slow parallax (0.15x)", "Mid-ground structure - medium parallax (0.30x)", "Near structure detail - faster parallax (0.45x)", "Foreground element - fastest parallax (0.60x)"};
}
