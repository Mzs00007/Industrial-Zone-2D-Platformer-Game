package tiles;
import animation.InputController;

/**
 * ═══════════════════════════════════════════════════════════════════════════════════════
 * RASTER GRAPHICS ONLY - PNG IMAGE ASSETS ONLY
 * ═══════════════════════════════════════════════════════════════════════════════════════
 * 
 * UPDATE (April 3, 2026 - COMPLETE HANDOUT DIRECTORY CONVERSION):
 * ─────────────────────────────────────────────────────────────────────────────────────
 * • Removed ALL vector graphics imports (no Graphics2D, Shape, Rectangle, Path2D, etc)
 * • Removed ALL vector shape drawing (no fillRect, drawOval, drawLine, etc)
 * • Removed color-based fallback graphics (no Color objects for dummy shapes)
 * • Removed java.awt.Rectangle import (use actual image assets instead!)
 * • Added raster image imports (BufferedImage, ImageIO, File, IOException)
 * • Now loads actual PNG files from Resources directories only
 * 
 * ASSETS USED:
 * ✓ PNG raster image files (from Resources/industrial-zone/...)
 * ✓ MIDI audio files (music)
 * ✓ OTF font files (text rendering)
 * ✓ WAV audio files (sound effects)
 * 
 * ASSETS NOT USED (FORBIDDEN):
 * ✗ Vector graphics (SVG, vector shapes, Batik library)
 * ✗ Dynamically drawn shapes (fillRect, drawOval, drawCircle, etc)
 * ✗ Color-based fallback graphics (Color.RED, Color.BLUE, etc)
 * ✗ Vector geometry paths (Path2D, Ellipse2D, Rectangle2D, Line2D)
 * ✗ java.awt.Rectangle (not for rendering!)
 * ✗ RenderingHints or BasicStroke
 * ✗ Apache Batik or any vector library
 * ✗ java.awt.Graphics or Graphics2D for drawing
 * 
 * ERROR HANDLING REQUIREMENT:
 * When PNG file NOT found:
 * • Log error with complete file path
 * • Skip rendering (do not create fallback color/shape)
 * • User sees missing asset clearly in console output
 * 
 * NEVER FALLBACK TO:
 * ✗ g2d.setColor(Color.RED); g2d.fillRect(...);  // Forbidden!
 * ✗ g2d.drawOval(...);                            // Forbidden!
 * ✗ g2d.drawCircle(...);                          // Forbidden!
 * ✗ new Rectangle(x, y, w, h) for rendering      // Forbidden!
 * 
 * @author 3359098
 * 
 * CORRECT PATTERN:
 * if (image == null) {
 *     System.err.println("ERROR: PNG not found: " + filePath);
 *     return;  // Skip rendering, no fallback graphics
 * }
 * g2d.drawImage(image, x, y, null);  // Draw actual PNG
 * 
 * ═══════════════════════════════════════════════════════════════════════════════════════
 */


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


/**
 * TileMapSystem - Unified tile loading and management for all game levels
 * 
 * Purpose: Load and manage tile definitions from Level1TileRegistry and Level2TileRegistry
 * Provides tile lookup by character ID and physics property access
 * 
 * Usage:
 *   TileMapSystem tileSystem = new TileMapSystem(1);  // Load Level 1
 *   Object[] tile = tileSystem.getTile('A');  // Get platform tile
 *   boolean isSolid = tileSystem.isSolid('A');
 */
public class TileMapSystem extends InputController {
    
    private int currentLevel;
    private Object[][] tileAssets;
    private Map<Character, Object[]> tileMap = new HashMap<>();
    
    /**
     * Initialize tile map for specified level
     * @param levelId 1 for Level 1, 2 for Level 2
     */
    public TileMapSystem(int levelId) {
        this.currentLevel = levelId;
        loadTiles(levelId);
    }
    
    /**
     * Load tile registry based on level ID
     * Constructs tileAssets array from Level1/Level2TileRegistry
     * Format: [0]=ID, [1]=Name, [2]=FilePath, [3]=Width, [4]=Height, [5]=IsSolid, [6]=IsHazard,
     *         [7]=PhysicsType, [8]=Friction, [9]=Damage, [10]=AnimFrames, [11]=FrameTiming, [12]=Tag
     */
    private void loadTiles(int levelId) {
        if (levelId == 1) {
            // Load Level 1 Tile Registry
            loadLevel1Tiles();
            System.out.println("[TileMapSystem] Loaded Level 1 Tile Registry (" + getTotalTileCount() + " tiles)");
        } else if (levelId == 2) {
            // Load Level 2 Tile Registry
            loadLevel2Tiles();
            System.out.println("[TileMapSystem] Loaded Level 2 Tile Registry (" + getTotalTileCount() + " tiles)");
        } else {
            throw new IllegalArgumentException("Invalid level ID: " + levelId + ". Expected 1 or 2.");
        }
        
        // Build character -> tile mapping for O(1) lookup
        for (Object[] tileData : tileAssets) {
            if (tileData != null && tileData.length > 0) {
                Character tileId = (Character) tileData[0];
                tileMap.put(tileId, tileData);
            }
        }
        
        System.out.printf("[TileMapSystem] ✓ Indexed %d tiles for fast lookup%n", tileMap.size());
    }
    
    /**
     * Load all tiles from Level1TileRegistry with correct file paths
     * Maps character codes A-Z, a-z, 0-9 to 81 tile PNG files
     * Uses Level1TileRegistry to get file paths; applies sensible defaults for other properties
     */
    private void loadLevel1Tiles() {
        try {
            // Get registry - provides file paths for all tile codes
            Set<Character> tileCodes = InputController.Level1TileRegistry.getAllCodes();
            
            tileAssets = new Object[tileCodes.size()][];
            int index = 0;
            
            for (Character code : tileCodes) {
                // Get file path from registry
                String filePath = InputController.Level1TileRegistry.getTile(code);
                
                if (filePath != null) {
                    // Create tile data array: [ID, Name, FilePath, Width, Height, IsSolid, IsHazard,
                    //                          PhysicsType, Friction, Damage, AnimFrames, FrameTiming, Tag]
                    tileAssets[index] = new Object[] {
                        code,                           // [0] Tile ID (Character)
                        "L1_Tile_" + code,              // [1] Name
                        filePath,                       // [2] FilePath (from registry)
                        32,                             // [3] Width (standard tile)
                        32,                             // [4] Height (standard tile)
                        InputController.Level1TileRegistry.isSolid(code),         // [5] IsSolid (from registry)
                        isHazardTile(code),             // [6] IsHazard (character-based detection)
                        InputController.Level1TileRegistry.getPhysicsType(code),  // [7] PhysicsType (from registry)
                        InputController.Level1TileRegistry.getFriction(code),     // [8] Friction (from registry)
                        getHazardDamage(code),          // [9] Damage (if hazard)
                        InputController.Level1TileRegistry.getAnimationFrames(code), // [10] AnimFrames (from registry)
                        InputController.Level1TileRegistry.getFrameTiming(code),  // [11] FrameTiming (from registry)
                        determineLevel1TileTag(code)    // [12] Tag (platform, hazard, deco)
                    };
                    index++;
                }
            }
            
            System.out.println("[TileMapSystem] ✓ Level 1 initialized with " + index + " tiles from registry");
        } catch (Exception e) {
            System.err.println("[TileMapSystem] ERROR loading Level 1 tiles: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Create comprehensive tile file mapping for Level 1
     * Maps character codes to absolute file paths
     */

    private Map<Character, String> createLevel1TileFileMap(String baseDir) {
        Map<Character, String> fileMap = new HashMap<>();
        
        String[] tileFiles = {
            "01_Platform_SolidBlock_FlatTopFull_DarkPurple_PrimaryWalkableFloorTile.png",           // A
            "02_Hazard_BreakableBlock_LargeXCrosshatch_PurpleOnDark_WarningSurfaceOrDestructible.png", // B
            "03_Platform_SolidBlock_FlatTopMid_MutedBluePurple_StandardFloorFill.png",            // C
            "04_Corner_InnerTopRight_LShapeCutout_SolidEdge_WallMeetsFloorJoinTopRight.png",    // D
            "05_Structure_SolidBlock_SmallStampTopRight_OffsetAccent_ArchitecturalDetailFill.png", // E
            "06_Corner_InnerTopLeft_NotchedTopLeft_SolidEdge_WallMeetsFloorJoinTopLeft.png",    // F
            "07_Panel_GridSurface_2x2QuadDivided_FlatIndustrialFace_WallOrFloorPanelFill.png",  // G
            "08_Wall_VerticalColumn_NarrowCentreAligned_TallRectangle_ShaftOrPillarMidFill.png", // H
            "09_Hazard_WarningSurface_SingleDiagonalRedStripe_BlueBase_ContactDamageHazard.png", // I
            "10_Corner_ExteriorTopRight_SmallDarkSquare_OffsetTopRight_WallTopRightCapAccent.png", // J
            "11_Panel_GridSurface_2x2LargeGrid_ProminentDividers_LargeIndustrialWallOrFloorPanel.png", // K
            "12_Panel_DetailBlock_RectangularInsetTopRight_IndustrialSurface_WallOrFloorFillDetail.png", // L
            "13_Wall_ThinVerticalColumn_NarrowCentreStrip_TallSlender_PipeCoverOrWallDivider.png", // M
            "14_Panel_InsetDetail_SmallSquareEmbeddedTopRight_RecessedAccent_DecorativeWallFill.png", // N
            "15_Wall_VerticalEdgeStrip_NarrowRightAligned_TallDarkBar_RightWallEdgeCapTile.png", // O
            "16_Platform_SolidBlock_FlatTop_FaintDotRivetPattern_WornIndustrialFloorFill.png",  // P
            "17_Panel_InsetDetail_SmallSquareEmbeddedBottomCentre_RecessedAccent_LowerWallFill.png", // Q
            "18_Edge_HorizontalShelfBar_NarrowCentreAligned_FlatTop_LedgeSurfaceOrPlatformEdge.png", // R
            "19_Corner_DiagonalHalfBlock_TopRightToBottomLeft_TwoToneBlue_SlopedTransitionOrDeco.png", // S
            "20_Corner_ExteriorTopRight_SmallTwoToneSquare_OffsetAccent_WallTopEdgeDecoCap.png", // T
            "21_Structure_SolidBlock_LargeFullFlat_HeavyDarkTone_HeavyStructuralWallOrFloor.png", // U
            "22_Structure_SolidBlock_SmallNotchTopRight_EdgeDetailAccent_StructuralTransitionCap.png", // V
            "23_Edge_BracketShelf_NarrowHorizontalBar_BottomCentreNotch_IndustrialSupportBracket.png", // W
            "24_Corner_DiagonalHalfBlock_BottomRightToTopLeft_LargeSplit_SlopedSurfaceTransition.png", // X
            "25_Edge_MinimalCornerCap_ThinStripTopRight_SmallSquareAccent_CornerEdgeDetailTile.png", // Y
            "26_Edge_BoltedShelfLedge_NarrowHorizontal_BoltRivetSides_SupportedIndustrialLedge.png", // Z
            "27_Edge_WideLedgeBar_HorizontalWideFlat_FlatWalkableTop_LedgePlatformTransition.png", // a
            "28_Corner_DiagonalHalfBlock_BottomLeftToTopRight_PointingRight_RightFacingSlopeBlock.png", // b
            "29_Corner_DiagonalHalfBlock_TopLeftToBottomRight_PointingDown_LeftFacingSlopeBlock.png", // c
            "30_Corner_SmallDiagonal_TopRightCornerOnly_MinorSlopedAccent_TransitionOrDecoDetail.png", // d
            "31_Hazard_FullStripeBlock_DiagonalRedOrangeStripes_FullCoverage_ContactDamageHazardFloor.png", // e
            "32_Hazard_FullStripeBlock_DiagonalAltAngleStripes_FullCoverage_AlternateHazardSurface.png", // f
            "33_Hazard_ZigzagCrisscrossStripe_MultiDiagonalPattern_FullCoverage_IntensiveHazardZone.png", // g
            "34_Hazard_WideSpacedStripeBlock_DiagonalWideGap_ContactDamage_ModerateHazardBarrier.png", // h
            "35_Hazard_DenseStripeBlock_DiagonalDensePacking_ContactDamage_HighDensityHazardSurface.png", // i
            "36_Hazard_CrosshatchXBlock_LargeBoldXPattern_ContactDamageOrBarrier_NoGoZoneWarning.png", // j
            "37_Deco_CircleMarker_LargeSolidCircleCentred_PurpleOnDark_ButtonPortalInteractiveDeco.png", // k
            "38_Corner_DiagonalHalfBlock_BottomLeftToTopRight_DarkPurpleTone_SlopedSurfaceTransition.png", // l
            "39_Deco_CircleMarker_SolidCircleCentredAltShade_SlightlyDifferentPurple_PickupOrDecoSpot.png", // m
            "40_Hazard_PartialStripeBlock_DenseRedStripesRightSide_ContactDamage_HalfHazardTransition.png", // n
            "41_Hazard_FullStripeBlock_DiagonalOrangeRedFull_ContactDamage_HazardFloorSurface.png", // o
            "42_Hazard_CrossedStripeBlock_DiagonalNarrowCrossed_ContactDamage_HazardBarrierVariant.png", // p
            "43_Hazard_WideGapStripeBlock_DiagonalLargeSpacing_ContactDamage_HazardZoneMarkerTile.png", // q
            "44_Hazard_ThinLineStripeBlock_DiagonalFineLines_ContactDamage_LightHazardWarningMarker.png", // r
            "45_Corner_ExteriorTopRight_SmallWarmRedOrange_HazardTone_HazardZoneTopRightCornerCap.png", // s
            "46_Wall_VerticalEdgeStrip_NarrowLeftAligned_PurpleTallBar_LeftWallEdgeCapTile.png", // t
            "47_Panel_HorizontalStripeBlock_EvenSpacedLines_FullSquare_WallFillOrFloorStripePanel.png", // u
            "48_Edge_GapStripeBar_NarrowHorizontalStrip_WideGapLinePattern_PipeEdgeOrLedgeAccent.png", // v
            "49_Hazard_MediumStripeBlock_DiagonalMediumSpacing_ContactDamage_MidIntensityHazardZone.png", // w
            "50_Hazard_BrightStripeBlock_DiagonalVividBright_ContactDamage_HighVisibilityHazardZone.png", // x
            "51_Hazard_DenseOrangeStripe_DiagonalDenseOrangePacked_ContactDamage_HotHazardSurface.png", // y
            "52_Hazard_AltAngleStripeBlock_DiagonalVariantAngle_ContactDamage_AltHazardFloorVariant.png", // z
            "53_Hazard_ElectricEnergyStripe_VerticalLightningLine_InstantDamage_ElectricShockHazard.png", // 0
            "54_Hazard_GlowingEnergyBar_VerticalGradientBrightCentre_InstantDamage_EnergyBeamHazard.png", // 1
            "55_Edge_SmallMutedCornerCap_TopRightAligned_MutedTone_MinimalTransitionEdgeDetail.png", // 2
            "56_Panel_InsetDetail_SmallSquareEmbeddedLeftCentre_RecessedAccent_LeftWallDetailPanel.png", // 3
            "57_Panel_InsetDetail_SmallSquareEmbeddedRightCentre_RecessedAccent_RightWallDetailPanel.png", // 4
            "58_Hazard_FullStripeBlock_DiagonalRedOrangeVariantB_ContactDamage_HazardFloorVariantB.png", // 5
            "59_Hazard_NarrowOrangeStripeBlock_DiagonalTightSpacing_ContactDamage_TightPackedHazard.png", // 6
            "60_Hazard_WideOrangeStripeBlock_DiagonalLooseSpacing_ContactDamage_WideSpacedHazardZone.png", // 7
            "61_Panel_TechControlDetail_SmallInsetDotCentred_BlueGreyTone_MachineryControlPanelWall.png", // 8
            "62_Deco_DiamondXGridPattern_SmallScaleRepeating_PurpleOnDark_DecorativeWallOrFloorFill.png", // 9
            "63_Hazard_EnergyBarrierStrip_TallNarrowBrightGradientGlow_InstantDamage_VerticalEnergyBarrier.png", // !
            "64_Panel_InsetDetail_MediumSquareEmbeddedCentre_RecessedLarger_CentralMachineDetailPanel.png", // @
            "65_Structure_SolidBlock_SmallRightAligned_DarkCompactTone_CompactWallOrEdgeFillTile.png", // #
            "66_Panel_InsetDetail_SmallSquareEmbeddedBottomLeft_RecessedAccent_LowerLeftWallDetail.png", // $
            "67_Hazard_DarkStripeBlock_DiagonalDarkMutedTone_ContactDamage_DarkIndustrialHazardSurface.png", // %
            "68_Hazard_DarkStripeVariant_DiagonalAltDarkTone_ContactDamage_AlternateDarkHazardSurface.png", // ^
            "69_Hazard_WarmDarkStripeBlock_DiagonalWarmBrownishTone_ContactDamage_WarmToneHazardSurface.png", // &
            "70_Deco_GradientColumnStrip_TallNarrowPurpleGradient_BackgroundAccent_VerticalDecoDivider.png", // *
            "71_Hazard_ChunkyDiamondCrossBlock_LargeScaleXPattern_ContactDamage_BarrierOrHazardIndicator.png", // (
            "72_Hazard_BottomEdgeBand_WideShortRectangle_HazardStripesBottomEdge_FloorLevelHazardWarning.png", // )
            "73_Panel_FramedInsetBlock_SmallSquareCentreFrame_RecessedScreen_TechMonitorOrControlPanel.png", // -
            "74_Deco_IndicatorLightStrip_WideShortRectangle_RedLightRowSingle_MachineryStatusIndicator.png", // =
            "75_Deco_IndicatorLightStrip_WideShortLonger_RedLightRowExtended_MachineryStatusBarWide.png", // [
            "76_Deco_IndicatorLightStrip_WideShortVariant_RedLightsSpacedAlt_AlternateStatusIndicator.png", // ]
            "77_Edge_PlainHorizontalDividerBar_NarrowThinStrip_SolidColour_FloorCeilingDividerStrip.png", // {
            "78_Edge_WideHorizontalDividerBar_NarrowMediumStrip_SolidColour_FloorCeilingDividerWide.png", // }
            "79_Edge_AltShadeHorizontalBar_NarrowThinStrip_AlternateColourTone_LedgeOrDividerEdge.png", // ;
            "80_Edge_RedAccentHorizontalBar_NarrowThinStrip_RedHighlightTone_HazardLevelIndicatorLine.png", // :
            "81_Hazard_SmallStripeWideBand_ShortWideRectangle_RedMiniStripes_LowLevelHazardThreshold.png"  // '
        };
        
        // Map letters: A-Z (index 0-25)
        for (int i = 0; i < 26; i++) {
            char code = (char)('A' + i);
            fileMap.put(code, baseDir + tileFiles[i]);
        }
        
        // Map lowercase: a-z (index 26-51)
        for (int i = 0; i < 26; i++) {
            char code = (char)('a' + i);
            fileMap.put(code, baseDir + tileFiles[26 + i]);
        }
        
        // Map digits: 0-9 (index 52-61)
        for (int i = 0; i < 10; i++) {
            char code = (char)('0' + i);
            fileMap.put(code, baseDir + tileFiles[52 + i]);
        }
        
        // Map special characters (index 62-80)
        char[] specialChars = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '[', ']', '{', '}', ';', ':', '\''};
        for (int i = 0; i < specialChars.length; i++) {
            fileMap.put(specialChars[i], baseDir + tileFiles[62 + i]);
        }
        
        return fileMap;
    }
    
    /**
     * Determine if a tile code represents a hazard
     */
    private boolean isHazardTile(char code) {
        // Hazard codes: B, I, e-j, n-r, s, w-z, 0-1, !, %, ^, &, (, ), -, =
        if (code == 'B' || code == 'I') return true;
        if (code >= 'e' && code <= 'j') return true;
        if (code >= 'n' && code <= 'r') return true;
        if (code == 's' || code == 'w' || code == 'x' || code == 'y' || code == 'z') return true;
        if (code == '0' || code == '1') return true;
        if (code == '!' || code == '%' || code == '^' || code == '&' || 
            code == '(' || code == ')' || code == '-' || code == '=') return true;
        return false;
    }
    
    /**
     * Get damage value for hazard tiles
     */
    private int getHazardDamage(char code) {
        if (isHazardTile(code)) {
            // Energy hazards deal more damage
            if (code == '0' || code == '!' || code == '1') return 25;
            // Standard hazards
            return 10;
        }
        return 0;
    }
    
    /**
     * Load all tiles from Level2TileRegistry with correct file paths
     * Maps character codes A-Z, a-z, 0-9 to 64 tile PNG files (Power Station theme)
     * Uses Level2TileRegistry to get file paths; applies sensible defaults for other properties
     */
    private void loadLevel2Tiles() {
        try {
            // Get registry - provides file paths for all tile codes
            Set<Character> tileCodes = InputController.Level2TileRegistry.getAllCodes();
            
            tileAssets = new Object[tileCodes.size()][];
            int index = 0;
            
            for (Character code : tileCodes) {
                // Get file path from registry
                String filePath = InputController.Level2TileRegistry.getTile(code);
                
                if (filePath != null) {
                    // Create tile data array: [ID, Name, FilePath, Width, Height, IsSolid, IsHazard,
                    //                          PhysicsType, Friction, Damage, AnimFrames, FrameTiming, Tag]
                    tileAssets[index] = new Object[] {
                        code,                           // [0] Tile ID (Character)
                        "L2_Tile_" + code,              // [1] Name
                        filePath,                       // [2] FilePath (from registry)
                        32,                             // [3] Width (standard tile)
                        32,                             // [4] Height (standard tile)
                        InputController.Level2TileRegistry.isSolid(code),         // [5] IsSolid (from registry)
                        isLevel2HazardTile(code),       // [6] IsHazard (character-based detection)
                        InputController.Level2TileRegistry.getPhysicsType(code),  // [7] PhysicsType (from registry)
                        InputController.Level2TileRegistry.getFriction(code),     // [8] Friction (from registry)
                        getLevel2HazardDamage(code),    // [9] Damage (if hazard)
                        InputController.Level2TileRegistry.getAnimationFrames(code), // [10] AnimFrames (from registry)
                        InputController.Level2TileRegistry.getFrameTiming(code),  // [11] FrameTiming (from registry)
                        determineLevel2TileTag(code)    // [12] Tag
                    };
                    index++;
                }
            }
            
            System.out.println("[TileMapSystem] ✓ Level 2 initialized with " + index + " tiles from registry");
        } catch (Exception e) {
            System.err.println("[TileMapSystem] ERROR loading Level 2 tiles: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Create comprehensive tile file mapping for Level 2 (Power Station theme)
     * Maps character codes to absolute file paths
     * Level 2 has 64 tiles with blue/navy/purple color scheme
     */

    private Map<Character, String> createLevel2TileFileMap(String baseDir) {
        Map<Character, String> fileMap = new HashMap<>();
        
        // 64 tiles from power-station-level-2 (indices 01-64)
        String[] tileFiles = {
            "01_Panel_HorizStripeBrick_BluePurple_SolidWalkableFloor_PrimaryPlatformTile.png",        // A
            "02_Panel_HorizStripeBrick_BluePurple_SolidFloorVariantB.png",                             // B
            "03_Panel_HorizStripeBrick_MidBlue_SolidFloorVariantC.png",                                // C
            "04_Panel_HorizStripeBrick_DarkBlue_SolidFloorVariantD.png",                               // D
            "05_Panel_HorizStripeBrick_DarkPurple_SolidFloorVariantE.png",                             // E
            "06_Panel_HorizStripeBrick_DeepNavy_SolidFloorVariantF.png",                               // F
            "07_Panel_HorizStripeBrick_LightBlue_SolidFloorVariantG.png",                              // G
            "08_Panel_HorizStripeBrick_MediumBlue_SolidFloorVariantH.png",                             // H
            "09_Panel_HorizStripeBrick_SlatePurple_SolidFloorVariantI.png",                            // I
            "10_Panel_HorizStripeBrick_SteelBlue_SolidFloorVariantJ.png",                              // J
            "11_Panel_HorizStripeBrick_CoolGrey_SolidFloorVariantK.png",                               // K
            "12_Panel_HorizStripeBrick_IndigoTone_SolidFloorVariantL.png",                             // L
            "13_Panel_HorizStripeBrick_SoftLavender_SolidFloorVariantM.png",                           // M
            "14_Panel_HorizStripeBrick_DuskBlue_SolidFloorVariantN.png",                               // N
            "15_Panel_HorizStripeBrick_NightBlue_SolidFloorVariantO.png",                              // O
            "16_Panel_HorizStripeBrick_MutedTeal_SolidFloorVariantP.png",                              // P
            "17_Brick_SmallUnit_BluePurple_WallFillTileA.png",                                        // Q
            "18_Brick_SmallUnit_DarkNavy_WallFillTileB.png",                                           // R
            "19_Brick_SmallUnit_MidBlue_WallFillTileC.png",                                            // S
            "20_Brick_SmallUnit_SlatePurple_WallFillTileD.png",                                        // T
            "21_Brick_SmallUnit_DeepIndigo_WallFillTileE.png",                                         // U
            "22_Brick_SmallUnit_CoolGrey_WallFillTileF.png",                                           // V
            "23_Edge_PanelBorder_BluePurple_WallEdgeTop.png",                                          // W
            "24_Edge_PanelBorder_DarkBlue_WallEdgeBottom.png",                                         // X
            "25_Edge_PanelBorder_MidBlue_WallEdgeLeft.png",                                            // Y
            "26_Edge_PanelBorder_NavyTone_WallEdgeRight.png",                                          // Z
            "27_Edge_PanelBorder_SlatePurple_WallEdgeVariantA.png",                                    // a
            "28_Edge_PanelBorder_IndigoTone_WallEdgeVariantB.png",                                     // b
            "29_Solid_PurpleBrick_FullBlock_HeavyWallA.png",                                           // c
            "30_Solid_PurpleBrick_FullBlock_HeavyWallB.png",                                           // d
            "31_Solid_PurpleBrick_FullBlock_HeavyWallC.png",                                           // e
            "32_Solid_PurpleBrick_FullBlock_HeavyWallD.png",                                           // f
            "33_Slope_DiagonalCut_TopLeftTriangle_RampUpLeft.png",                                    // g
            "34_Slope_DiagonalCut_TopRightTriangle_RampUpRight.png",                                  // h
            "35_Panel_MixedEdge_BlueGrey_StructuralDetailA.png",                                       // i
            "36_Panel_MixedEdge_BlueGrey_StructuralDetailB.png",                                       // j
            "37_Panel_MixedEdge_BlueGrey_StructuralDetailC.png",                                       // k
            "38_Panel_MixedEdge_BlueGrey_StructuralDetailD.png",                                       // l
            "39_Panel_MixedEdge_BlueGrey_StructuralDetailE.png",                                       // m
            "40_Panel_MixedEdge_BlueGrey_StructuralDetailF.png",                                       // n
            "41_Slope_DarkDiagonal_CutA_SteepRampLeft.png",                                            // o
            "42_Slope_DarkDiagonal_CutB_SteepRampRight.png",                                           // p
            "43_Slope_DarkDiagonal_CutC_ShallowRampLeft.png",                                          // q
            "44_Slope_DarkDiagonal_CutD_ShallowRampRight.png",                                         // r
            "45_Solid_FlatBlue_FullBlock_CeilingOrPlatformA.png",                                      // s
            "46_Solid_FlatBlue_FullBlock_CeilingOrPlatformB.png",                                      // t
            "47_Panel_InsetDetail_BlueTech_WallInlayA.png",                                            // u
            "48_Panel_InsetDetail_BlueTech_WallInlayB.png",                                            // v
            "49_Solid_MagentaPurple_FullBlock_AccentStructure.png",                                    // w
            "50_Panel_GreyTech_InsetBevel_DarkPlatformA.png",                                          // x
            "51_Panel_GreyTech_InsetBevel_DarkPlatformB.png",                                          // y
            "52_Panel_GreyTech_InsetBevel_DarkPlatformC.png",                                          // z
            "53_Panel_GreyTech_InsetBevel_DarkPlatformD.png",                                          // 0
            "54_Panel_GreyTech_InsetBevel_DarkPlatformE.png",                                          // 1
            "55_Panel_GreyTech_InsetBevel_DarkPlatformF.png",                                          // 2
            "56_Panel_GreyTech_InsetBevel_DarkPlatformG.png",                                          // 3
            "57_Door_GateFrame_PanelStyle_ClosedDoorA.png",                                            // 4
            "58_Door_GateFrame_PanelStyle_ClosedDoorB.png",                                            // 5
            "59_Door_GateFrame_PanelStyle_ClosedDoorC.png",                                            // 6
            "60_Door_GateFrame_PanelStyle_ClosedDoorD.png",                                            // 7
            "61_Solid_LightGreyBlue_FlatStructural_CeilingTileA.png",                                  // 8
            "62_Solid_LightGreyBlue_FlatStructural_CeilingTileB.png",                                  // 9
            "63_Solid_LightGreyBlue_FlatStructural_CeilingTileC.png",                                  // !
            "64_Solid_LightGreyBlue_FlatStructural_CeilingTileD.png"                                   // @
        };
        
        // Map letters: A-Z (index 0-25)
        for (int i = 0; i < 26; i++) {
            char code = (char)('A' + i);
            fileMap.put(code, baseDir + tileFiles[i]);
        }
        
        // Map lowercase: a-z (index 26-51)
        for (int i = 0; i < 26; i++) {
            char code = (char)('a' + i);
            fileMap.put(code, baseDir + tileFiles[26 + i]);
        }
        
        // Map digits: 0-9 (index 52-61)
        for (int i = 0; i < 10; i++) {
            char code = (char)('0' + i);
            fileMap.put(code, baseDir + tileFiles[52 + i]);
        }
        
        // Map special characters (index 62-63)
        fileMap.put('!', baseDir + tileFiles[62]);
        fileMap.put('@', baseDir + tileFiles[63]);
        
        return fileMap;
    }
    
    /**
     * Determine if a tile code represents a hazard in Level 2
     * Level 2 hazards: doors (4-7), turrets in animated objects
     */
    private boolean isLevel2HazardTile(char code) {
        // Doors can be traversable or blocking, but don't cause damage by default
        // In Level 2, fewer hazards exist - mainly structural
        // Doors (4-7 / '4'-'7') could be blockages
        if (code >= '4' && code <= '7') return false;  // Doors are not hazards, just obstacles
        return false;  // Level 2 has fewer hazard tiles than Level 1
    }
    
    /**
     * Get damage value for hazard tiles in Level 2
     */
    private int getLevel2HazardDamage(char code) {
        // Level 2 primarily focuses on platforming rather than hazards
        // Hazards are mostly from animated turrets, not static tiles
        return 0;
    }
    
    /**
     * Determine tile tag/category for Level 1 based on character code
     * Tags: platform, hazard, decoration
     */
    private String determineLevel1TileTag(char code) {
        // Hazard codes in Level 1 (B, I, e-j, n-r, s, w-z, 0-1, !)
        if (code == 'B' || code == 'I' || (code >= 'e' && code <= 'j') || 
            (code >= 'n' && code <= 'r') || code == 's' || (code >= 'w' && code <= 'z') ||
            code == '0' || code == '1' || code == '!') {
            return "hazard";
        }
        // Decoration codes (k, m, 9)
        if (code == 'k' || code == 'm' || code == '9') {
            return "decoration";
        }
        // Everything else is platform
        return "platform";
    }
    
    /**
     * Determine tile tag/category for Level 2 based on character code
     * Tags: platform, hazard, decoration
     */
    private String determineLevel2TileTag(char code) {
        // Hazard codes in Level 2 (similar pattern to Level 1)
        if ((code >= 'A' && code <= 'Z') || (code >= 'a' && code <= 'z') || 
            (code >= '0' && code <= '9') || code == '@' || code == '!') {
            // This is a simplified check - actual hazard status would come from tile data
            return "platform";
        }
        return "platform";
    }
    
    /**
     * Get complete tile data by character ID
     * Format: [0]=ID, [1]=Name, [2]=FilePath, [3]=Width, [4]=Height, [5]=IsSolid, [6]=IsHazard,
     *         [7]=PhysicsType, [8]=Friction, [9]=Damage, [10]=AnimFrames, [11]=FrameTiming, [12]=Tag
     */
    public Object[] getTileData(char tileId) {
        Object[] tile = tileMap.get(tileId);
        if (tile == null) {
            System.err.printf("[TileMapSystem] ✗ Unknown tile ID: '%c' in Level %d%n", tileId, currentLevel);
            return null;
        }
        return tile;
    }
    
    /**
     * Check if tile is solid (blocks movement)
     */
    public boolean isSolid(char tileId) {
        Object[] tile = getTileData(tileId);
        if (tile == null) return false;
        return (boolean) tile[5];  // Index 5 = IsSolid
    }
    
    /**
     * Check if tile is hazardous (damages on contact)
     */
    public boolean isHazard(char tileId) {
        Object[] tile = getTileData(tileId);
        if (tile == null) return false;
        return (boolean) tile[6];  // Index 6 = IsHazard
    }
    
    /**
     * Get physics type for collision handling
     * Returns: "STATIC", "DYNAMIC", "PLATFORM", or "HAZARD"
     */
    public String getPhysicsType(char tileId) {
        Object[] tile = getTileData(tileId);
        if (tile == null) return "STATIC";
        return (String) tile[7];  // Index 7 = PhysicsType
    }
    
    /**
     * Get friction coefficient for this tile
     */
    public float getFriction(char tileId) {
        Object[] tile = getTileData(tileId);
        if (tile == null) return 0.85f;  // Default friction
        return (float) tile[8];  // Index 8 = Friction
    }
    
    /**
     * Get damage dealt by this tile (if hazard)
     */
    public int getDamage(char tileId) {
        Object[] tile = getTileData(tileId);
        if (tile == null) return 0;
        return (int) tile[9];  // Index 9 = Damage
    }
    
    /**
     * Get animation frame count for this tile
     */
    public int getAnimationFrames(char tileId) {
        Object[] tile = getTileData(tileId);
        if (tile == null) return 0;
        return (int) tile[10];  // Index 10 = AnimFrames
    }
    
    /**
     * Get animation frame timing in milliseconds
     */
    public int getFrameTiming(char tileId) {
        Object[] tile = getTileData(tileId);
        if (tile == null) return 0;
        return (int) tile[11];  // Index 11 = FrameTiming
    }
    
    /**
     * Get semantic tag for this tile (platform, hazard, deco, etc.)
     */
    public String getTag(char tileId) {
        Object[] tile = getTileData(tileId);
        if (tile == null) return "unknown";
        return (String) tile[12];  // Index 12 = Tag
    }
    
    /**
     * Get tile name for debugging
     */
    public String getTileName(char tileId) {
        Object[] tile = getTileData(tileId);
        if (tile == null) return "UNKNOWN";
        return (String) tile[1];  // Index 1 = Name
    }
    
    /**
     * Get file path for this tile
     */
    public String getFilePath(char tileId) {
        Object[] tile = getTileData(tileId);
        if (tile == null) return null;
        return (String) tile[2];  // Index 2 = FilePath
    }
    
    /**
     * Get current level ID
     */
    public int getCurrentLevel() {
        return currentLevel;
    }
    
    /**
     * Get total tile count for current level
     */
    public int getTotalTileCount() {
        return tileMap.size();
    }
    
    /**
     * Print debug info for a specific tile
     */
    public void printTileInfo(char tileId) {
        Object[] tile = getTileData(tileId);
        if (tile == null) {
            System.out.printf("Tile '%c' not found in Level %d%n", tileId, currentLevel);
            return;
        }
        
        System.out.printf("Tile '%c' - %s:%n", tileId, getTileName(tileId));
        System.out.printf("  Physics: %s, Solid: %s, Hazard: %s%n", 
            getPhysicsType(tileId), isSolid(tileId), isHazard(tileId));
        System.out.printf("  Friction: %.2f, Damage: %d%n", getFriction(tileId), getDamage(tileId));
        System.out.printf("  Animation: %d frames, %dms per frame%n", 
            getAnimationFrames(tileId), getFrameTiming(tileId));
        System.out.printf("  Tag: %s%n", getTag(tileId));
    }
    
    /**
     * Print all tiles in current level
     */
    public void printAllTiles() {
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━%n");
        System.out.printf("LEVEL %d TILE REGISTRY - %d tiles%n", currentLevel, getTotalTileCount());
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━%n");
        
        for (Object[] tile : tileAssets) {
            if (tile == null) continue;
            char id = (Character) tile[0];
            String name = (String) tile[1];
            boolean solid = (boolean) tile[5];
            boolean hazard = (boolean) tile[6];
            String tag = (String) tile[12];
            
            System.out.printf("'%c' %-30s [%s] %s%s%n", 
                id, name, tag,
                solid ? "SOLID " : "",
                hazard ? "HAZARD" : "");
        }
        
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━%n");
    }
    
    
    // ════════════════════════════════════════════════════════════════════════════════════════
    // NESTED CLASSES - TILE ADJACENCY SYSTEM (Consolidated from separate files)
    // ════════════════════════════════════════════════════════════════════════════════════════
    
    /**
     * TileTypeEnum - Comprehensive 20+ Tile Type Enumeration System
     * Consolidated from TileType.java
     * 
     * Defines all possible tile types for tile-based level design with specific 
     * adjacency requirements and rules for placement validation.
     * Categories: Corners, Edges, Interior, Junctions, Dead Ends, Internal Corners, etc.
     */
    public enum TileTypeEnum {
        // CORNERS
        CORNER_TOP_LEFT("CORNER_TL", "Corner (Top-Left)", true, true, false, false),
        CORNER_TOP_RIGHT("CORNER_TR", "Corner (Top-Right)", true, false, false, true),
        CORNER_BOTTOM_LEFT("CORNER_BL", "Corner (Bottom-Left)", false, true, true, false),
        CORNER_BOTTOM_RIGHT("CORNER_BR", "Corner (Bottom-Right)", false, false, true, true),
        
        // EDGES
        EDGE_TOP("EDGE_TOP", "Edge (Top Wall)", true, false, false, false),
        EDGE_BOTTOM("EDGE_BOTTOM", "Edge (Bottom Wall)", false, false, true, false),
        EDGE_LEFT("EDGE_LEFT", "Edge (Left Wall)", false, true, false, false),
        EDGE_RIGHT("EDGE_RIGHT", "Edge (Right Wall)", false, false, false, true),
        
        // INTERIOR & SPECIAL
        INTERIOR("INTERIOR", "Interior (Walkable)", false, false, false, false),
        PILLAR("PILLAR", "Pillar (Isolated Wall)", false, false, false, false),
        
        // LINE SEGMENTS
        HORIZONTAL_LINE("HORIZ_LINE", "Horizontal Wall Segment", true, false, true, false),
        VERTICAL_LINE("VERT_LINE", "Vertical Wall Segment", false, true, false, true),
        
        // T-JUNCTIONS
        T_JUNCTION_TOP("T_JUNCTION_TOP", "T-Junction (Top)", true, true, false, true),
        T_JUNCTION_BOTTOM("T_JUNCTION_BOTTOM", "T-Junction (Bottom)", false, true, true, true),
        T_JUNCTION_LEFT("T_JUNCTION_LEFT", "T-Junction (Left)", true, true, true, false),
        T_JUNCTION_RIGHT("T_JUNCTION_RIGHT", "T-Junction (Right)", true, false, true, true),
        
        // DEAD ENDS
        DEAD_END_TOP("DEAD_END_TOP", "Dead End (Top Open)", false, true, true, true),
        DEAD_END_BOTTOM("DEAD_END_BOTTOM", "Dead End (Bottom Open)", true, true, false, true),
        DEAD_END_LEFT("DEAD_END_LEFT", "Dead End (Left Open)", true, false, true, true),
        DEAD_END_RIGHT("DEAD_END_RIGHT", "Dead End (Right Open)", true, true, true, false),
        
        // INTERNAL CORNERS
        INTERNAL_CORNER_TOP_LEFT("INT_CORNER_TL", "Internal Corner (TL)", true, true, false, false),
        INTERNAL_CORNER_TOP_RIGHT("INT_CORNER_TR", "Internal Corner (TR)", true, false, false, true),
        INTERNAL_CORNER_BOTTOM_LEFT("INT_CORNER_BL", "Internal Corner (BL)", false, true, true, false),
        INTERNAL_CORNER_BOTTOM_RIGHT("INT_CORNER_BR", "Internal Corner (BR)", false, false, true, true);
        
        private final String code;
        private final String displayName;
        private final boolean isBlockedTop, isBlockedLeft, isBlockedBottom, isBlockedRight;
        
        TileTypeEnum(String code, String displayName, boolean blockedTop, boolean blockedLeft, 
                     boolean blockedBottom, boolean blockedRight) {
            this.code = code;
            this.displayName = displayName;
            this.isBlockedTop = blockedTop;
            this.isBlockedLeft = blockedLeft;
            this.isBlockedBottom = blockedBottom;
            this.isBlockedRight = blockedRight;
        }
        
        public String getCode() { return code; }
        public String getDisplayName() { return displayName; }
        public boolean isBlockedTop() { return isBlockedTop; }
        public boolean isBlockedLeft() { return isBlockedLeft; }
        public boolean isBlockedBottom() { return isBlockedBottom; }
        public boolean isBlockedRight() { return isBlockedRight; }
        
        public boolean isSideBlocked(int direction) {
            switch (direction) {
                case 0: return isBlockedTop;
                case 1: return isBlockedRight;
                case 2: return isBlockedBottom;
                case 3: return isBlockedLeft;
                default: return false;
            }
        }
    }
    
    
    /**
     * AdjacencyRules - Comprehensive Tile Adjacency Matrix System
     * Consolidated from TileAdjacencyRules.java
     * 
     * Defines which TileTypeEnum types can be adjacent in each direction.
     * Enforces layout rules for valid, consistent map designs.
     */
    public static class AdjacencyRules {
        private static final Map<TileTypeEnum, Set<TileTypeEnum>[]> ADJACENCY_RULES = new HashMap<>();
        
        static {
            initializeAdjacencyRules();
        }
        

        private static void initializeAdjacencyRules() {
            // Define adjacency constraints for each tile type
            // Index: 0=Top, 1=Right, 2=Bottom, 3=Left
            
            Set<TileTypeEnum>[] cornerTL = new Set[4];
            cornerTL[0] = Collections.emptySet();
            cornerTL[1] = setOf(TileTypeEnum.EDGE_TOP);
            cornerTL[2] = setOf(TileTypeEnum.EDGE_LEFT);
            cornerTL[3] = Collections.emptySet();
            ADJACENCY_RULES.put(TileTypeEnum.CORNER_TOP_LEFT, cornerTL);
            
            Set<TileTypeEnum>[] cornerTR = new Set[4];
            cornerTR[0] = Collections.emptySet();
            cornerTR[1] = Collections.emptySet();
            cornerTR[2] = setOf(TileTypeEnum.EDGE_RIGHT);
            cornerTR[3] = setOf(TileTypeEnum.EDGE_TOP);
            ADJACENCY_RULES.put(TileTypeEnum.CORNER_TOP_RIGHT, cornerTR);
            
            Set<TileTypeEnum>[] cornerBL = new Set[4];
            cornerBL[0] = setOf(TileTypeEnum.EDGE_LEFT);
            cornerBL[1] = setOf(TileTypeEnum.EDGE_BOTTOM);
            cornerBL[2] = Collections.emptySet();
            cornerBL[3] = Collections.emptySet();
            ADJACENCY_RULES.put(TileTypeEnum.CORNER_BOTTOM_LEFT, cornerBL);
            
            Set<TileTypeEnum>[] cornerBR = new Set[4];
            cornerBR[0] = setOf(TileTypeEnum.EDGE_RIGHT);
            cornerBR[1] = Collections.emptySet();
            cornerBR[2] = Collections.emptySet();
            cornerBR[3] = setOf(TileTypeEnum.EDGE_BOTTOM);
            ADJACENCY_RULES.put(TileTypeEnum.CORNER_BOTTOM_RIGHT, cornerBR);
            
            // Interior can connect to most tiles
            Set<TileTypeEnum>[] interior = new Set[4];
            Set<TileTypeEnum> interiorAdjacent = new HashSet<>(java.util.Arrays.asList(
                TileTypeEnum.values()
            ));
            for (int i = 0; i < 4; i++) {
                interior[i] = new HashSet<>(interiorAdjacent);
            }
            ADJACENCY_RULES.put(TileTypeEnum.INTERIOR, interior);
            
            // Pillar surrounded by interior only
            Set<TileTypeEnum>[] pillar = new Set[4];
            for (int i = 0; i < 4; i++) {
                pillar[i] = setOf(TileTypeEnum.INTERIOR);
            }
            ADJACENCY_RULES.put(TileTypeEnum.PILLAR, pillar);
        }
        
        public static boolean isValidPlacement(TileTypeEnum tile, TileTypeEnum top, TileTypeEnum right,
                                               TileTypeEnum bottom, TileTypeEnum left) {
            if (!ADJACENCY_RULES.containsKey(tile)) return true;
            
            Set<TileTypeEnum>[] rules = ADJACENCY_RULES.get(tile);
            return (top == null || rules[0].isEmpty() || rules[0].contains(top)) &&
                   (right == null || rules[1].isEmpty() || rules[1].contains(right)) &&
                   (bottom == null || rules[2].isEmpty() || rules[2].contains(bottom)) &&
                   (left == null || rules[3].isEmpty() || rules[3].contains(left));
        }
    }
    
    
    /**
     * AdjacencyValidator - Comprehensive Tile Map Validation System
     * Consolidated from AdjacencyValidator.java
     * 
     * Provides multi-level validation: single tiles, local regions, complete maps,
     * and structural integrity checking.
     */
    public static class AdjacencyValidator {
        /**
         * Validates a single tile placement given its neighbors
         */
        public static boolean validateTilePlacement(TileTypeEnum tile, TileTypeEnum top, 
                                                     TileTypeEnum right, TileTypeEnum bottom, TileTypeEnum left) {
            return AdjacencyRules.isValidPlacement(tile, top, right, bottom, left);
        }
        
        /**
         * Validates a complete tile map for consistency
         */
        public static ValidationResult validateTileMap(TileTypeEnum[][] tileMap) {
            ValidationResult result = new ValidationResult();
            
            if (tileMap == null || tileMap.length == 0) {
                result.addError("Tile map is null or empty");
                return result;
            }
            
            int height = tileMap.length;
            int width = tileMap[0].length;
            
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    TileTypeEnum tile = tileMap[y][x];
                    if (tile == null) continue;
                    
                    TileTypeEnum top = (y > 0) ? tileMap[y-1][x] : null;
                    TileTypeEnum bottom = (y < height-1) ? tileMap[y+1][x] : null;
                    TileTypeEnum left = (x > 0) ? tileMap[y][x-1] : null;
                    TileTypeEnum right = (x < width-1) ? tileMap[y][x+1] : null;
                    
                    if (!validateTilePlacement(tile, top, right, bottom, left)) {
                        result.addError(String.format(
                            "Adjacency violation at (%d,%d): %s", x, y, tile.getCode()
                        ));
                    }
                }
            }
            
            validateStructuralRules(tileMap, result);
            return result;
        }
        
        /**
         * Validates local 3x3 region consistency
         */
        public static boolean validateLocal3x3Region(TileTypeEnum[][] tileMap, int centerX, int centerY) {
            if (tileMap == null || centerX < 0 || centerY < 0 || 
                centerX >= tileMap[0].length || centerY >= tileMap.length) {
                return false;
            }
            
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    int x = centerX + dx;
                    int y = centerY + dy;
                    
                    if (x < 0 || x >= tileMap[0].length || y < 0 || y >= tileMap.length) continue;
                    
                    TileTypeEnum tile = tileMap[y][x];
                    if (tile == null) continue;
                    
                    TileTypeEnum top = (y > 0) ? tileMap[y-1][x] : null;
                    TileTypeEnum bottom = (y < tileMap.length-1) ? tileMap[y+1][x] : null;
                    TileTypeEnum left = (x > 0) ? tileMap[y][x-1] : null;
                    TileTypeEnum right = (x < tileMap[0].length-1) ? tileMap[y][x+1] : null;
                    
                    if (!validateTilePlacement(tile, top, right, bottom, left)) {
                        return false;
                    }
                }
            }
            
            return true;
        }
        
        /**
         * Validates structural rules for the entire map
         */
        private static void validateStructuralRules(TileTypeEnum[][] tileMap, ValidationResult result) {
            validateInteriorEnclosure(tileMap, result);
            validateCornerIntegrity(tileMap, result);
        }
        
        /**
         * Validates that Interior tiles are completely enclosed
         */
        private static void validateInteriorEnclosure(TileTypeEnum[][] tileMap, ValidationResult result) {
            int height = tileMap.length;
            int width = tileMap[0].length;
            
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    TileTypeEnum tile = tileMap[y][x];
                    
                    if (tile == TileTypeEnum.INTERIOR) {
                        if ((y > 0 && tileMap[y-1][x] == null) ||
                            (y < height-1 && tileMap[y+1][x] == null) ||
                            (x > 0 && tileMap[y][x-1] == null) ||
                            (x < width-1 && tileMap[y][x+1] == null)) {
                            result.addError("Interior tile at (" + x + "," + y + ") is not fully enclosed");
                        }
                    }
                }
            }
        }
        
        /**
         * Validates that corners have correct edge attachments
         */
        private static void validateCornerIntegrity(TileTypeEnum[][] tileMap, ValidationResult result) {
            int height = tileMap.length;
            int width = tileMap[0].length;
            
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    TileTypeEnum tile = tileMap[y][x];
                    if (tile == null || !isCornerType(tile)) continue;
                    
                    // Verify corner is part of valid structure
                    if (!isPartOfValidRoom(tileMap, x, y)) {
                        result.addWarning("Possible isolated corner at (" + x + "," + y + ")");
                    }
                }
            }
        }
        
        /**
         * Checks if a tile is a corner type
         */
        private static boolean isCornerType(TileTypeEnum tile) {
            return tile == TileTypeEnum.CORNER_TOP_LEFT || 
                   tile == TileTypeEnum.CORNER_TOP_RIGHT ||
                   tile == TileTypeEnum.CORNER_BOTTOM_LEFT || 
                   tile == TileTypeEnum.CORNER_BOTTOM_RIGHT ||
                   tile == TileTypeEnum.INTERNAL_CORNER_TOP_LEFT ||
                   tile == TileTypeEnum.INTERNAL_CORNER_TOP_RIGHT ||
                   tile == TileTypeEnum.INTERNAL_CORNER_BOTTOM_LEFT ||
                   tile == TileTypeEnum.INTERNAL_CORNER_BOTTOM_RIGHT;
        }
        
        /**
         * Checks if a corner is part of a valid room structure
         */
        private static boolean isPartOfValidRoom(TileTypeEnum[][] tileMap, int x, int y) {
            // A corner must have at least one edge adjacent and interior nearby
            int height = tileMap.length;
            int width = tileMap[0].length;
            
            boolean hasEdgeAdjacent = false;
            boolean hasInteriorNearby = false;
            
            // Check 3x3 region around corner
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    int ny = y + dy;
                    int nx = x + dx;
                    
                    if (nx < 0 || nx >= width || ny < 0 || ny >= height) continue;
                    
                    TileTypeEnum neighbor = tileMap[ny][nx];
                    if (neighbor == null) continue;
                    
                    // Check if it's an edge
                    if (neighbor == TileTypeEnum.EDGE_TOP || neighbor == TileTypeEnum.EDGE_BOTTOM ||
                        neighbor == TileTypeEnum.EDGE_LEFT || neighbor == TileTypeEnum.EDGE_RIGHT) {
                        hasEdgeAdjacent = true;
                    }
                    
                    // Check for interior
                    if (neighbor == TileTypeEnum.INTERIOR) {
                        hasInteriorNearby = true;
                    }
                }
            }
            
            return hasEdgeAdjacent && hasInteriorNearby;
        }
    }
    
    
    /**
     * ValidationResult - Result container for tile validation operations
     */
    public static class ValidationResult {
        private List<String> errors = new ArrayList<>();
        private List<String> warnings = new ArrayList<>();
        
        public void addError(String error) { errors.add(error); }
        public void addWarning(String warning) { warnings.add(warning); }
        public List<String> getErrors() { return errors; }
        public List<String> getWarnings() { return warnings; }
        public boolean isValid() { return errors.isEmpty(); }
        
        public String getReport() {
            StringBuilder report = new StringBuilder();
            if (isValid()) {
                report.append("✓ Tile map validation PASSED\n");
            } else {
                report.append("✗ Tile map validation FAILED:\n");
                for (String error : errors) {
                    report.append("  ERROR: ").append(error).append("\n");
                }
            }
            if (!warnings.isEmpty()) {
                report.append("⚠ Warnings:\n");
                for (String warning : warnings) {
                    report.append("  WARNING: ").append(warning).append("\n");
                }
            }
            return report.toString();
        }
        
        public void printResults() {
            System.out.print(getReport());
        }
    }
    
    
    /**
     * TileAdjacencySystemDemo - Examples and demonstration methods
     * Consolidated from TileAdjacencySystemDemo.java
     * 
     * Demonstrates practical usage of tile adjacency system with example layouts
     */
    public static class TileAdjacencySystemDemo {
        /**
         * Runs comprehensive demonstration
         */
        public static void main(String[] args) {
            System.out.println("╔════════════════════════════════════════════════════════════╗");
            System.out.println("║  COMPREHENSIVE TILE ADJACENCY SYSTEM DEMONSTRATION         ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝\n");
            
            demonstrateBasic3x3Room();
            demonstrateValidationSystem();
            demonstrateComplexLayouts();
        }
        
        /**
         * Demonstrates a basic 3x3 room structure
         */
        private static void demonstrateBasic3x3Room() {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("DEMO 1: BASIC 3x3 ROOM STRUCTURE");
            System.out.println("=".repeat(70));
            
            TileTypeEnum[][] room = new TileTypeEnum[3][3];
            
            room[0][0] = TileTypeEnum.CORNER_TOP_LEFT;
            room[0][1] = TileTypeEnum.EDGE_TOP;
            room[0][2] = TileTypeEnum.CORNER_TOP_RIGHT;
            
            room[1][0] = TileTypeEnum.EDGE_LEFT;
            room[1][1] = TileTypeEnum.INTERIOR;
            room[1][2] = TileTypeEnum.EDGE_RIGHT;
            
            room[2][0] = TileTypeEnum.CORNER_BOTTOM_LEFT;
            room[2][1] = TileTypeEnum.EDGE_BOTTOM;
            room[2][2] = TileTypeEnum.CORNER_BOTTOM_RIGHT;
            
            System.out.println("\nStructure Layout:");
            printTileMap(room);
            
            ValidationResult result = AdjacencyValidator.validateTileMap(room);
            System.out.println("\nValidation Result:");
            System.out.println(result.getReport());
        }
        
        /**
         * Demonstrates validation system with larger map
         */
        private static void demonstrateValidationSystem() {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("DEMO 2: VALIDATION SYSTEM - LARGER MAP");
            System.out.println("=".repeat(70));
            
            TileTypeEnum[][] largerMap = new TileTypeEnum[5][5];
            
            largerMap[0][0] = TileTypeEnum.CORNER_TOP_LEFT;
            largerMap[0][1] = TileTypeEnum.EDGE_TOP;
            largerMap[0][2] = TileTypeEnum.EDGE_TOP;
            largerMap[0][3] = TileTypeEnum.EDGE_TOP;
            largerMap[0][4] = TileTypeEnum.CORNER_TOP_RIGHT;
            
            largerMap[1][0] = TileTypeEnum.EDGE_LEFT;
            largerMap[1][1] = TileTypeEnum.INTERIOR;
            largerMap[1][2] = TileTypeEnum.INTERIOR;
            largerMap[1][3] = TileTypeEnum.INTERIOR;
            largerMap[1][4] = TileTypeEnum.EDGE_RIGHT;
            
            largerMap[2][0] = TileTypeEnum.EDGE_LEFT;
            largerMap[2][1] = TileTypeEnum.INTERIOR;
            largerMap[2][2] = TileTypeEnum.INTERIOR;
            largerMap[2][3] = TileTypeEnum.INTERIOR;
            largerMap[2][4] = TileTypeEnum.EDGE_RIGHT;
            
            largerMap[3][0] = TileTypeEnum.EDGE_LEFT;
            largerMap[3][1] = TileTypeEnum.INTERIOR;
            largerMap[3][2] = TileTypeEnum.INTERIOR;
            largerMap[3][3] = TileTypeEnum.INTERIOR;
            largerMap[3][4] = TileTypeEnum.EDGE_RIGHT;
            
            largerMap[4][0] = TileTypeEnum.CORNER_BOTTOM_LEFT;
            largerMap[4][1] = TileTypeEnum.EDGE_BOTTOM;
            largerMap[4][2] = TileTypeEnum.EDGE_BOTTOM;
            largerMap[4][3] = TileTypeEnum.EDGE_BOTTOM;
            largerMap[4][4] = TileTypeEnum.CORNER_BOTTOM_RIGHT;
            
            System.out.println("\nStructure Layout:");
            printTileMap(largerMap);
            
            ValidationResult result = AdjacencyValidator.validateTileMap(largerMap);
            System.out.println("\nValidation Result:");
            System.out.println(result.getReport());
        }
        
        /**
         * Demonstrates complex layout patterns
         */
        private static void demonstrateComplexLayouts() {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("DEMO 3: COMPLEX LAYOUT PATTERNS");
            System.out.println("=".repeat(70));
            
            System.out.println("\n✓ System supports:");
            System.out.println("  • Multiple connected rooms");
            System.out.println("  • T-junctions for corridor connections");
            System.out.println("  • Dead-ends for specific paths");
            System.out.println("  • Internal corners for complex shapes");
            System.out.println("  • Pillar obstacles in open spaces");
        }
        
        /**
         * Utility method to print a tile map visually
         */
        private static void printTileMap(TileTypeEnum[][] map) {
            if (map == null) return;
            
            for (TileTypeEnum[] row : map) {
                for (TileTypeEnum tile : row) {
                    System.out.print(tile != null ? tile.getCode().substring(0, 2) : "  ");
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
    }
    
    
    /**
     * Helper method for creating tile type sets
     */
    @SafeVarargs
    private static <T> Set<T> setOf(T... elements) {
        return new HashSet<>(java.util.Arrays.asList(elements));
    }
}
