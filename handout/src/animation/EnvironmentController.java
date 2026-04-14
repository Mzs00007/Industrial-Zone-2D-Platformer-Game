/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.HashMap;
import java.util.Map;

public static class AnimationAndSpriteLoader.EnvironmentController {
    private Map<Integer, AnimationAndSpriteLoader.AnimationState> tileStates = new HashMap<Integer, AnimationAndSpriteLoader.AnimationState>();
    private Map<String, String> tileAssets = new HashMap<String, String>();
    private Map<String, String> backgroundAssets = new HashMap<String, String>();
    private Map<String, String> objectAssets = new HashMap<String, String>();
    private Map<String, String> animatedObjectAssets = new HashMap<String, String>();
    private float parallaxOffset = 0.0f;
    private int currentLevel;

    public AnimationAndSpriteLoader.EnvironmentController(int n) {
        this.currentLevel = n;
        this.initializeAssets();
    }

    private void initializeAssets() {
        this.tileAssets.put("FLOOR_SOLID_PRIMARY", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/01_Platform_SolidBlock_FlatTopFull_DarkPurple_PrimaryWalkableFloorTile.png");
        this.tileAssets.put("FLOOR_STANDARD", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/03_Platform_SolidBlock_FlatTopMid_MutedBluePurple_StandardFloorFill.png");
        this.tileAssets.put("CORNER_INNER_RIGHT", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/04_Corner_InnerTopRight_LShapeCutout_SolidEdge_WallMeetsFloorJoinTopRight.png");
        this.tileAssets.put("CORNER_INNER_LEFT", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/06_Corner_InnerTopLeft_NotchedTopLeft_SolidEdge_WallMeetsFloorJoinTopLeft.png");
        this.tileAssets.put("WALL_VERTICAL", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/08_Wall_VerticalColumn_NarrowCentreAligned_TallRectangle_ShaftOrPillarMidFil.png");
        this.tileAssets.put("PANEL_GRID", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/07_Panel_GridSurface_2x2QuadDivided_FlatIndustrialFace_WallOrFloorPanelFill.png");
        this.tileAssets.put("HAZARD_BREAKABLE", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/02_Hazard_BreakableBlock_LargeXCrosshatch_PurpleOnDark_WarningSurfaceOrDestr.png");
        this.tileAssets.put("HAZARD_WARNING", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/09_Hazard_WarningSurface_SingleDiagonalRedStripe_BlueBase_ContactDamageHazar.png");
        this.backgroundAssets.put("SKY_BASE", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer1_SkyBase_SolidLavenderGrey_StaticFill_DrawFirstNoScroll.png");
        this.backgroundAssets.put("PARALLAX_LAYER_1", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer2_FractalTreeSilhouette_MintSkyBlackCracks_ParallaxFactor015.png");
        this.backgroundAssets.put("PARALLAX_LAYER_2", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer3_FarFactorySilhouette_LightBlueIndustrial_ParallaxFactor025.png");
        this.backgroundAssets.put("PARALLAX_LAYER_3", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer4_MidFactorySilhouette_MediumBluePipeDetail_ParallexFactor040.png");
        this.backgroundAssets.put("PARALLAX_LAYER_4", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer5_NearFactorySilhouette_DarkNavyLargeTank_ParallaxFactor060.png");
        this.objectAssets.put("PROP_BARREL_UPRIGHT", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Barrel_UprightRedMetal_StandardLabel_FloorDecorOrPushable_VariantA.png");
        this.objectAssets.put("PROP_BARREL_TALL", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Barrel_TallDarkRed_WornIndustrial_FloorDecorOrPushable_TallVariant.png");
        this.objectAssets.put("PROP_BENCH", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Bench_BlueMetal_FlatTopWorkbench_WallPlacedDeco_IndustrialFurniture.png");
        this.objectAssets.put("PROP_BOARD_SINGLE", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Board_SingleWoodenNoticeBoard_PapersOnWall_WallMountedDeco_VariantA.png");
        this.animatedObjectAssets.put("COLLECTIBLE_COIN", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Collectible_Money_6Frames1Row_GreenBanknotesSpinFlip_CurrencyPickup_Loop80ms.png");
        this.animatedObjectAssets.put("COLLECTIBLE_CARD", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Collectible_Card_6Frames1Row_WhiteBlueSpinningFloat_PickupItem_Loop80ms.png");
        this.animatedObjectAssets.put("CONVEYOR_BELT", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_ConveyorFull_4Frames1Row_FullWidthBeltRunning_MovesPlayerRight_Loop80ms.png");
        this.animatedObjectAssets.put("MOVING_PLATFORM", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_MovingRed_6Frames1Row_SlidingLeftRight_PlayerRideable_Loop100ms.png");
        this.animatedObjectAssets.put("SCREEN_DECORATION", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Deco_Screen1_4Frames1Row_BlueMonitorFlicker_WallPanelTechDeco_Loop150ms.png");
        this.animatedObjectAssets.put("CHEST_INTERACTIVE", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Interactive_Chest_8Frames1Row_OrangeRedLidOpenSequence_PlayOnce100ms.png");
        this.animatedObjectAssets.put("HAZARD_HAMMER", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Hazard_Hammer_6Frames1Row_RedOrangeSwingArc_DamageFrames3to5_Loop90ms.png");
        this.animatedObjectAssets.put("PORTAL_LEVEL_ENTRY", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Portal_LevelEntry_4Frames1Row_RedChevronGateOpening_LevelTransition_PlayOnce100ms.png");
    }

    public void updateParallax(float f) {
        this.parallaxOffset = f * 0.3f;
    }

    public void setTileAnimation(int n, AnimationAndSpriteLoader.AnimationState animationState) {
        this.tileStates.put(n, animationState);
    }

    public AnimationAndSpriteLoader.AnimationState getTileAnimation(int n) {
        return this.tileStates.getOrDefault(n, AnimationAndSpriteLoader.AnimationState.TILE_DEFAULT);
    }

    public String getTileAsset(String string) {
        return this.tileAssets.getOrDefault(string, null);
    }

    public String getBackgroundAsset(String string) {
        return this.backgroundAssets.getOrDefault(string, null);
    }

    public String getObjectAsset(String string) {
        return this.objectAssets.getOrDefault(string, null);
    }

    public String getAnimatedObjectAsset(String string) {
        return this.animatedObjectAssets.getOrDefault(string, null);
    }

    public float getParallaxOffset() {
        return this.parallaxOffset;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }
}
