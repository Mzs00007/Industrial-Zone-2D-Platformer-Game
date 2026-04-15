/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class TileCompositionPatterns {
    public static final String REGISTRY_TYPE = "tile_composition";
public class DecorationTileSystem {
        public static final String PATTERN_NAME = "DecoTileAccents";
        public static final String PATTERN_TYPE = "decoration_visual";
        public static final String DECO_DIAMOND_GRID = "62_Deco_DiamondXGridPattern_SmallScaleRepeating_PurpleOnDark_DecorativeWallO.png";
        public static final String DECO_GRADIENT_COLUMN = "70_Deco_GradientColumnStrip_TallNarrowPurpleGradient_BackgroundAccent_Vertic.png";
        public static final String DECO_LIGHT_STRIP_SINGLE = "74_Deco_IndicatorLightStrip_WideShortRectangle_RedLightRowSingle_MachinerySt.png";
        public static final String DECO_LIGHT_STRIP_EXTENDED = "75_Deco_IndicatorLightStrip_WideShortLonger_RedLightRowExtended_MachinerySta.png";
        public static final String DECO_LIGHT_STRIP_ALT = "76_Deco_IndicatorLightStrip_WideShortVariant_RedLightsSpacedAlt_AlternateSta.png";
        public static final String[] DECO_FILES = new String[]{"62_Deco_DiamondXGridPattern_SmallScaleRepeating_PurpleOnDark_DecorativeWallO.png", "70_Deco_GradientColumnStrip_TallNarrowPurpleGradient_BackgroundAccent_Vertic.png", "74_Deco_IndicatorLightStrip_WideShortRectangle_RedLightRowSingle_MachinerySt.png", "75_Deco_IndicatorLightStrip_WideShortLonger_RedLightRowExtended_MachinerySta.png", "76_Deco_IndicatorLightStrip_WideShortVariant_RedLightsSpacedAlt_AlternateSta.png"};
        public static final String NOTE = "Use decoration pieces to add visual detail without affecting walkability. Creates industrial atmosphere.";
    }
public class EdgeBorderAssemblyPattern {
        public static final String PATTERN_NAME = "EdgeBorderFrame";
        public static final String PATTERN_TYPE = "border_frame";
        public static final String EDGE_TOP_VARIANT_1 = "55_Edge_SmallMutedCornerCap_TopRightAligned_MutedTone_MinimalTransitionEdgeD.png";
        public static final String EDGE_DIVIDER_PLAIN = "77_Edge_PlainHorizontalDividerBar_NarrowThinStrip_SolidColour_FloorCeilingDi.png";
        public static final String EDGE_DIVIDER_WIDE = "78_Edge_WideHorizontalDividerBar_NarrowMediumStrip_SolidColour_FloorCeilingD.png";
        public static final String EDGE_DIVIDER_ALT = "79_Edge_AltShadeHorizontalBar_NarrowThinStrip_AlternateColourTone_LedgeOrDiv.png";
        public static final String EDGE_ACCENT_RED = "80_Edge_RedAccentHorizontalBar_NarrowThinStrip_RedHighlightTone_HazardLevelI.png";
        public static final String[] EDGE_FILES = new String[]{"55_Edge_SmallMutedCornerCap_TopRightAligned_MutedTone_MinimalTransitionEdgeD.png", "77_Edge_PlainHorizontalDividerBar_NarrowThinStrip_SolidColour_FloorCeilingDi.png", "78_Edge_WideHorizontalDividerBar_NarrowMediumStrip_SolidColour_FloorCeilingD.png", "79_Edge_AltShadeHorizontalBar_NarrowThinStrip_AlternateColourTone_LedgeOrDiv.png", "80_Edge_RedAccentHorizontalBar_NarrowThinStrip_RedHighlightTone_HazardLevelI.png"};
        public static final String NOTE = "Frame edge pieces around tile sections to prevent raw boundaries. Use dividers for floor/ceiling separation.";
    }
public class PanelStructureWallPattern {
        public static final String PATTERN_NAME = "PanelStructureWall";
        public static final String PATTERN_TYPE = "panel_assembly";
        public static final String INSET_DETAIL_TOP = "56_Panel_InsetDetail_SmallSquareEmbeddedLeftCentre_RecessedAccent_LeftWallDe.png";
        public static final String INSET_DETAIL_CENTER = "57_Panel_InsetDetail_SmallSquareEmbeddedRightCentre_RecessedAccent_RightWall.png";
        public static final String PANEL_DETAIL_FRAMED = "73_Panel_FramedInsetBlock_SmallSquareCentreFrame_RecessedScreen_TechMonitorO.png";
        public static final String PANEL_TECH_CONTROL = "61_Panel_TechControlDetail_SmallInsetDotCentred_BlueGreyTone_MachineryContro.png";
        public static final String PANEL_LARGE_INSET = "64_Panel_InsetDetail_MediumSquareEmbeddedCentre_RecessedLarger_CentralMachin.png";
        public static final String HAZARD_SLOPE_1 = "52_Hazard_AltAngleStripeBlock_DiagonalVariantAngle_ContactDamage_AltHazardFl.png";
        public static final String HAZARD_SLOPE_2 = "58_Hazard_FullStripeBlock_DiagonalRedOrangeVariantB_ContactDamage_HazardFloo.png";
        public static final String[] PANEL_FILES = new String[]{"56_Panel_InsetDetail_SmallSquareEmbeddedLeftCentre_RecessedAccent_LeftWallDe.png", "57_Panel_InsetDetail_SmallSquareEmbeddedRightCentre_RecessedAccent_RightWall.png", "73_Panel_FramedInsetBlock_SmallSquareCentreFrame_RecessedScreen_TechMonitorO.png", "61_Panel_TechControlDetail_SmallInsetDotCentred_BlueGreyTone_MachineryContro.png", "64_Panel_InsetDetail_MediumSquareEmbeddedCentre_RecessedLarger_CentralMachin.png", "52_Hazard_AltAngleStripeBlock_DiagonalVariantAngle_ContactDamage_AltHazardFl.png", "58_Hazard_FullStripeBlock_DiagonalRedOrangeVariantB_ContactDamage_HazardFloo.png"};
        public static final String NOTE = "Use panel pieces for structure, slopes for navigation surfaces";
    }
public class BrickSmallUnitWallPattern {
        public static final String PATTERN_NAME = "BrickSmallUnitWall";
        public static final String PATTERN_TYPE = "modular_grid";
        public static final String UNIT_VARIANT_A = "17_Brick_SmallUnit_BluePurple_WallFillTileA.png";
        public static final String UNIT_VARIANT_B = "18_Brick_SmallUnit_DarkNavy_WallFillTitleB.png";
        public static final String UNIT_VARIANT_C = "19_Brick_SmallUnit_MidBlue_WallFillTileC.png";
        public static final String NOTE = "Mix 3-6 unit variants in grid for visual complexity. No pattern needed - random placement OK.";
        public static final int RECOMMENDED_WALL_HEIGHT_TILES = 4;
    }
public class HorizontalBrickPlatformPattern {
        public static final String PATTERN_NAME = "HorizontalBrickPlatform";
        public static final String PATTERN_TYPE = "horizontal_strip";
        public static final String LEFT_EDGE_FILE = "02_Panel_HorizStripeBrick_BluePurple_SolidFloorVariantB.png";
        public static final String CENTER_TILE_FILE = "03_Panel_HorizStripeBrick_MidBlue_SolidFloorVariantC.png";
        public static final String RIGHT_EDGE_FILE = "04_Panel_HorizStripeBrick_DarkBlue_SolidFloorVariantD.png";
        public static final String NOTE = "Combine LEFT + (CENTER \u00d7 N) + RIGHT to create platform of any length";
        public static final int MIN_WIDTH_TILES = 1;
        public static final int MAX_WIDTH_TILES = 20;
    }
}
