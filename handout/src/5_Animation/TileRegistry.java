/*
 * Decompiled with CFR 0.152.
 */
package animation;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public static class AnimationAndSpriteLoader.TileRegistry {
    private static final Map<Character, String> REGISTRY = new TreeMap<Character, String>();

    public static String getTile(char c) {
        return REGISTRY.getOrDefault(Character.valueOf(c), null);
    }

    public static Set<Character> getAllCodes() {
        return REGISTRY.keySet();
    }

    public static boolean hasTile(char c) {
        return REGISTRY.containsKey(Character.valueOf(c));
    }

    public static int getTileCount() {
        return REGISTRY.size();
    }

    static {
        REGISTRY.put(Character.valueOf('A'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/01_Platform_SolidBlock_FlatTopFull_DarkPurple_PrimaryWalkableFloorTile.png");
        REGISTRY.put(Character.valueOf('P'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/16_Platform_SolidBlock_FlatTop_FaintDotRivetPattern_WornIndustrialFloorFill.png");
        REGISTRY.put(Character.valueOf('C'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/03_Platform_SolidBlock_FlatTopMid_MutedBluePurple_StandardFloorFill.png");
        REGISTRY.put(Character.valueOf('U'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/21_Structure_SolidBlock_LargeFullFlat_HeavyDarkTone_HeavyStructuralWallOrFloor.png");
        REGISTRY.put(Character.valueOf('V'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/22_Structure_SolidBlock_SmallNotchTopRight_EdgeDetailAccent_StructuralTransitionCap.png");
        REGISTRY.put(Character.valueOf('E'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/05_Structure_SolidBlock_SmallStampTopRight_OffsetAccent_ArchitecturalDetailFill.png");
        REGISTRY.put(Character.valueOf('D'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/04_Corner_InnerTopRight_LShapeCutout_SolidEdge_WallMeetsFloorJoinTopRight.png");
        REGISTRY.put(Character.valueOf('F'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/06_Corner_InnerTopLeft_NotchedTopLeft_SolidEdge_WallMeetsFloorJoinTopLeft.png");
        REGISTRY.put(Character.valueOf('J'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/10_Corner_ExteriorTopRight_SmallDarkSquare_OffsetTopRight_WallTopRightCapAccent.png");
        REGISTRY.put(Character.valueOf('T'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/20_Corner_ExteriorTopRight_SmallTwoToneSquare_OffsetAccent_WallTopEdgeDecoCap.png");
        REGISTRY.put(Character.valueOf('S'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/19_Corner_DiagonalHalfBlock_TopRightToBottomLeft_TwoToneBlue_SlopedTransitionOrDeco.png");
        REGISTRY.put(Character.valueOf('X'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/24_Corner_DiagonalHalfBlock_BottomRightToTopLeft_LargeSplit_SlopedSurfaceTransition.png");
        REGISTRY.put(Character.valueOf('b'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/28_Corner_DiagonalHalfBlock_BottomLeftToTopRight_PointingRight_RightFacingSlopeBlock.png");
        REGISTRY.put(Character.valueOf('c'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/29_Corner_DiagonalHalfBlock_TopLeftToBottomRight_PointingDown_LeftFacingSlopeBlock.png");
        REGISTRY.put(Character.valueOf('l'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/38_Corner_DiagonalHalfBlock_BottomLeftToTopRight_DarkPurpleTone_SlopedSurfaceTransition.png");
        REGISTRY.put(Character.valueOf('d'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/30_Corner_SmallDiagonal_TopRightCornerOnly_MinorSlopedAccent_TransitionOrDecoDetail.png");
        REGISTRY.put(Character.valueOf('2'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/55_Edge_SmallMutedCornerCap_TopRightAligned_MutedTone_MinimalTransitionEdgeDetail.png");
        REGISTRY.put(Character.valueOf('Y'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/25_Edge_MinimalCornerCap_ThinStripTopRight_SmallSquareAccent_CornerEdgeDetailTile.png");
        REGISTRY.put(Character.valueOf('H'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/08_Wall_VerticalColumn_NarrowCentreAligned_TallRectangle_ShaftOrPillarMidFill.png");
        REGISTRY.put(Character.valueOf('M'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/13_Wall_ThinVerticalColumn_NarrowCentreStrip_TallSlender_PipeCoverOrWallDivider.png");
        REGISTRY.put(Character.valueOf('O'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/15_Wall_VerticalEdgeStrip_NarrowRightAligned_TallDarkBar_RightWallEdgeCapTile.png");
        REGISTRY.put(Character.valueOf('t'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/46_Wall_VerticalEdgeStrip_NarrowLeftAligned_PurpleTallBar_LeftWallEdgeCapTile.png");
        REGISTRY.put(Character.valueOf('G'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/07_Panel_GridSurface_2x2QuadDivided_FlatIndustrialFace_WallOrFloorPanelFill.png");
        REGISTRY.put(Character.valueOf('K'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/11_Panel_GridSurface_2x2LargeGrid_ProminentDividers_LargeIndustrialWallOrFloorPanel.png");
        REGISTRY.put(Character.valueOf('L'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/12_Panel_DetailBlock_RectangularInsetTopRight_IndustrialSurface_WallOrFloorFillDetail.png");
        REGISTRY.put(Character.valueOf('N'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/14_Panel_InsetDetail_SmallSquareEmbeddedTopRight_RecessedAccent_DecorativeWallFill.png");
        REGISTRY.put(Character.valueOf('Q'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/17_Panel_InsetDetail_SmallSquareEmbeddedBottomCentre_RecessedAccent_LowerWallFill.png");
        REGISTRY.put(Character.valueOf('u'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/47_Panel_HorizontalStripeBlock_EvenSpacedLines_FullSquare_WallFillOrFloorStripePanel.png");
        REGISTRY.put(Character.valueOf('3'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/56_Panel_InsetDetail_SmallSquareEmbeddedLeftCentre_RecessedAccent_LeftWallDetailPanel.png");
        REGISTRY.put(Character.valueOf('4'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/57_Panel_InsetDetail_SmallSquareEmbeddedRightCentre_RecessedAccent_RightWallDetailPanel.png");
        REGISTRY.put(Character.valueOf('8'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/61_Panel_TechControlDetail_SmallInsetDotCentred_BlueGreyTone_MachineryControlPanelWall.png");
        REGISTRY.put(Character.valueOf('@'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/64_Panel_InsetDetail_MediumSquareEmbeddedCentre_RecessedLarger_CentralMachineDetailPanel.png");
        REGISTRY.put(Character.valueOf('R'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/18_Edge_HorizontalShelfBar_NarrowCentreAligned_FlatTop_LedgeSurfaceOrPlatformEdge.png");
        REGISTRY.put(Character.valueOf('Z'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/26_Edge_BoltedShelfLedge_NarrowHorizontal_BoltRivetSides_SupportedIndustrialLedge.png");
        REGISTRY.put(Character.valueOf('a'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/27_Edge_WideLedgeBar_HorizontalWideFlat_FlatWalkableTop_LedgePlatformTransition.png");
        REGISTRY.put(Character.valueOf('W'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/23_Edge_BracketShelf_NarrowHorizontalBar_BottomCentreNotch_IndustrialSupportBracket.png");
        REGISTRY.put(Character.valueOf('v'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/48_Edge_GapStripeBar_NarrowHorizontalStrip_WideGapLinePattern_PipeEdgeOrLedgeAccent.png");
        REGISTRY.put(Character.valueOf('B'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/02_Hazard_BreakableBlock_LargeXCrosshatch_PurpleOnDark_WarningSurfaceOrDestr.png");
        REGISTRY.put(Character.valueOf('I'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/09_Hazard_WarningSurface_SingleDiagonalRedStripe_BlueBase_ContactDamageHazar.png");
        REGISTRY.put(Character.valueOf('e'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/31_Hazard_FullStripeBlock_DiagonalRedOrangeStripes_FullCoverage_ContactDamageHazardFloor.png");
        REGISTRY.put(Character.valueOf('f'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/32_Hazard_FullStripeBlock_DiagonalAltAngleStripes_FullCoverage_AlternateHazardSurface.png");
        REGISTRY.put(Character.valueOf('g'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/33_Hazard_ZigzagCrisscrossStripe_MultiDiagonalPattern_FullCoverage_IntensiveHazardZone.png");
        REGISTRY.put(Character.valueOf('h'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/34_Hazard_WideSpacedStripeBlock_DiagonalWideGap_ContactDamage_ModerateHazardBarrier.png");
        REGISTRY.put(Character.valueOf('i'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/35_Hazard_DenseStripeBlock_DiagonalDensePacking_ContactDamage_HighDensityHazardSurface.png");
        REGISTRY.put(Character.valueOf('j'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/36_Hazard_CrosshatchXBlock_LargeBoldXPattern_ContactDamageOrBarrier_NoGoZoneWarning.png");
        REGISTRY.put(Character.valueOf('n'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/40_Hazard_PartialStripeBlock_DenseRedStripesRightSide_ContactDamage_HalfHazardTransition.png");
        REGISTRY.put(Character.valueOf('o'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/41_Hazard_FullStripeBlock_DiagonalOrangeRedFull_ContactDamage_HazardFloorSurface.png");
        REGISTRY.put(Character.valueOf('p'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/42_Hazard_CrossedStripeBlock_DiagonalNarrowCrossed_ContactDamage_HazardBarrierVariant.png");
        REGISTRY.put(Character.valueOf('q'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/43_Hazard_WideGapStripeBlock_DiagonalLargeSpacing_ContactDamage_HazardZoneMarkerTile.png");
        REGISTRY.put(Character.valueOf('r'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/44_Hazard_ThinLineStripeBlock_DiagonalFineLines_ContactDamage_LightHazardWarningMarker.png");
        REGISTRY.put(Character.valueOf('s'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/45_Corner_ExteriorTopRight_SmallWarmRedOrange_HazardTone_HazardZoneTopRightCornerCap.png");
        REGISTRY.put(Character.valueOf('w'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/49_Hazard_MediumStripeBlock_DiagonalMediumSpacing_ContactDamage_MidIntensityHazardZone.png");
        REGISTRY.put(Character.valueOf('x'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/50_Hazard_BrightStripeBlock_DiagonalVividBright_ContactDamage_HighVisibilityHazardZone.png");
        REGISTRY.put(Character.valueOf('y'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/51_Hazard_DenseOrangeStripe_DiagonalDenseOrangePacked_ContactDamage_HotHazardSurface.png");
        REGISTRY.put(Character.valueOf('z'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/52_Hazard_AltAngleStripeBlock_DiagonalVariantAngle_ContactDamage_AltHazardFloorVariant.png");
        REGISTRY.put(Character.valueOf('5'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/58_Hazard_FullStripeBlock_DiagonalRedOrangeVariantB_ContactDamage_HazardFloorVariantB.png");
        REGISTRY.put(Character.valueOf('6'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/59_Hazard_NarrowOrangeStripeBlock_DiagonalTightSpacing_ContactDamage_TightPackedHazard.png");
        REGISTRY.put(Character.valueOf('7'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/60_Hazard_WideOrangeStripeBlock_DiagonalLooseSpacing_ContactDamage_WideSpacedHazardZone.png");
        REGISTRY.put(Character.valueOf('0'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/53_Hazard_ElectricEnergyStripe_VerticalLightningLine_InstantDamage_ElectricShockHazard.png");
        REGISTRY.put(Character.valueOf('1'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/54_Hazard_GlowingEnergyBar_VerticalGradientBrightCentre_InstantDamage_EnergyBeamHazard.png");
        REGISTRY.put(Character.valueOf('!'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/63_Hazard_EnergyBarrierStrip_TallNarrowBrightGradientGlow_InstantDamage_VerticalEnergyBarrier.png");
        REGISTRY.put(Character.valueOf('k'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/37_Deco_CircleMarker_LargeSolidCircleCentred_PurpleOnDark_ButtonPortalInteractiveDeco.png");
        REGISTRY.put(Character.valueOf('m'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/39_Deco_CircleMarker_SolidCircleCentredAltShade_SlightlyDifferentPurple_PickupOrDecoSpot.png");
        REGISTRY.put(Character.valueOf('9'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/62_Deco_DiamondXGridPattern_SmallScaleRepeating_PurpleOnDark_DecorativeWallOrFloorFill.png");
    }
}
