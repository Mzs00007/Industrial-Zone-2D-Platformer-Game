/*
 * Decompiled with CFR 0.152.
 */
package animation;

import java.util.LinkedHashMap;
import java.util.Map;
public class ObjectPlacementRulesEngine {
    public static final String TYPE_PLACEMENT_RULES = "object_placement_rules_engine";
    public static final Map<String, PlacementRule> PLACEMENT_MATRIX = new LinkedHashMap<String, PlacementRule>(){
        {
            this.put("RED", new PlacementRule("RED", new String[]{"TrapHammer_SwingingBlade", "TrapSpike_UpDown", "TrapElectric_Arc", "Barrel_Damaged", "Barrel_Standard", "FireExtinguisher_Standard", "WarningSign_Triangle", "Platform_Moving_Red", "Portal_LevelEntry"}, new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Hazard_Hammer_6Frames1Row_RedOrangeSwingArc_DamageFrames3to5_Loop90ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Barrel_SmallDamagedTipped_RedWithSpill_FloorHazardDeco_DamagedVariant.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Barrel_UprightRedMetal_StandardLabel_FloorDecorOrPushable_VariantA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_FireExtinguisher_StandardUprightRed_WallMountedDeco_SafetyPropA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Sign_RedWarningTriangle_HazardAheadWarning_FloorPoleSign_DirectionalA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_MovingRed_6Frames1Row_SlidingLeftRight_PlayerRideable_Loop100ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Portal_LevelEntry_4Frames1Row_RedChevronGateOpening_LevelTransition_PlayOnce120ms.png"}));
            this.put("BLUE", new PlacementRule("BLUE", new String[]{"Money_Collectible", "Bench_Blue", "Screen_Blue_Flicker", "Locker_Blue", "Bucket_Blue", "Crate_Dark", "Monitor_Deco"}, new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Collectible_Money_6Frames1Row_GreenBanknotesSpinFlip_CurrencyPickup_Loop80ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Bench_BlueMetal_FlatTopWorkbench_WallPlacedDeco_IndustrialFurniture.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Deco_Screen1_4Frames1Row_BlueMonitorFlicker_WallPanelTechDeco_Loop150ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Locker_BlueDoubleDoor_TallMetal_WallPlacedDeco_IndustrialLockerBlue.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Bucket_SmallBlueWithHandle_FloorClutter_AmbientDeco_JanitorialProp.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Crate_DarkWoodSealed_SmallSquare_FloorStackable_StandardCrateA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Deco_Screen1_4Frames1Row_BlueMonitorFlicker_WallPanelTechDeco_Loop150ms.png"}));
            this.put("GREEN", new PlacementRule("GREEN", new String[]{"Card_Collectible", "Box_Cardboard", "Desk_Wooden", "Board_Wooden", "Crate_Light", "Crate_Locked", "Locker_Red"}, new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Collectible_Card_6Frames1Row_WhiteBlueSpinningFloat_PickupItem_Loop80ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_CardboardLight_MediumLighter_FloorStackable_CardboardVariantD.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Desk_WoodenWorkstation_DrawersAndSurface_FloorPlacedDeco_WorkstationDesk.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Board_SingleWoodenNoticeBoard_PapersOnWall_WallMountedDeco_VariantA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Crate_LightWoodPanelled_MediumSquare_FloorStackable_LightWoodVariantB.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Crate_RedLockedClasp_SecuredContainer_InteractiveOrDeco_LockedCrate.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Locker_RedDoubleDoor_TallMetal_WallPlacedDeco_IndustrialLockerA.png"}));
            this.put("YELLOW", new PlacementRule("YELLOW", new String[]{"Warning_Sign", "Box_Light", "Crate_Light", "WarningSign_Question"}, new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Sign_RedWarningTriangle_HazardAheadWarning_FloorPoleSign_DirectionalA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_CardboardLight_MediumLighter_FloorStackable_CardboardVariantD.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Crate_LightWoodPanelled_MediumSquare_FloorStackable_LightWoodVariantB.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Sign_RedLollipopQuestionMark_HintOrMystery_FloorPoleSign_DirectionalB.png"}));
            this.put("ANY", new PlacementRule("ANY", new String[]{"Ladder_Climbable", "Fence_Generic", "Conveyor_Belt", "Chest_Interactive", "Platform_Moving", "Flag_Landmark", "Mop_Deco", "Box_Generic", "Plank_Generic"}, new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Ladder_TallFullHeight_BlueGreyRungs_ShaftWallClimb_ClimbableA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Fence_SingleLargeFrame_GateShape_ZoneEntranceDeco_FenceGateFrame.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_ConveyorFull_4Frames1Row_FullWidthBeltRunning_MovesPlayerRight_Loop80ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Interactive_Chest_8Frames1Row_OrangeRedLidOpenSequence_PlayOnce100ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Flag_TallRedBannerPole_GoldEmblem_CheckpointOrBossGateLandmark.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Mop_WoodenHandleBrownHead_FloorLeaningDeco_JanitorialAmbientProp.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_OpenTopColoured_ItemsVisible_FloorDeco_OpenBoxWithContents.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_ThinFlatPlank_NarrowLow_FloorDeco_FlatPlankOrLidPiece.png"}));
        }
    };

    public static String[] getObjectsForTileset(String string) {
        PlacementRule placementRule = PLACEMENT_MATRIX.get(string.toUpperCase());
        if (placementRule != null) {
            return placementRule.filePaths;
        }
        return ObjectPlacementRulesEngine.PLACEMENT_MATRIX.get((Object)"ANY").filePaths;
    }

    public static String getRandomObjectForTileset(String string) {
        String[] stringArray = ObjectPlacementRulesEngine.getObjectsForTileset(string);
        if (stringArray.length > 0) {
            return stringArray[(int)(Math.random() * (double)stringArray.length)];
        }
        return null;
    }
public class PlacementRule {
        public String tileColor;
        public String[] objectTypes;
        public String[] filePaths;

        public PlacementRule(String string, String[] stringArray, String[] stringArray2) {
            this.tileColor = string;
            this.objectTypes = stringArray;
            this.filePaths = stringArray2;
        }
    }
}
