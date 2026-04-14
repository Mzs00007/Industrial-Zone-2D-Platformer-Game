/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.Level2EnvironmentSystem {
    public static final String TYPE_LEVEL2_ENV = "level2_environment_system";
    public static final String DIRECTORY_LEVEL2_OBJECTS = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects";
    public static final String ANIM_CARD_L2 = "Resources/industrial-zone/1 Tiles/power-station-level-2/4 Animated objects/Anim_Collectible_Card_6Frames1Row_BlueSpinningFloat_PickupItem_Loop80ms.png";
    public static final String ANIM_MONEY_L2 = "Resources/industrial-zone/1 Tiles/power-station-level-2/4 Animated objects/Anim_Collectible_Money_6Frames1Row_GreenBanknotesSpinFlip_CurrencyPickup_Loop80ms.png";
    public static final String ANIM_CHEST_L2 = "Resources/industrial-zone/1 Tiles/power-station-level-2/4 Animated objects/Anim_Interactive_Chest_8Frames1Row_BlueTealLidOpenSequence_PlayOnce100ms.png";
    public static final String ANIM_TRAP_L2 = "Resources/industrial-zone/1 Tiles/power-station-level-2/4 Animated objects/Anim_Hazard_Turret_MultiFrame1Row_TurretFiringProjectile_DamageOnFire_Loop120ms.png";

    public static class PowerLineComponents {
        public static final String DIR_POWER = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines";
        public static final String PYLON_BASE_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Pylon_BaseOnly_VariantB_BlueMetal_PylonFeetAlt.png";
        public static final String PYLON_FULL_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Pylon_FullTall_WithAntennaTop_BlueMetal_PylonFullA.png";
        public static final String PYLON_FULL_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Pylon_FullTall_AltTopVariant_BlueMetal_PylonFullB.png";
        public static final String PYLON_LOWER_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Pylon_LowerHalf_MediumHeight_BlueMetal_PylonBodyA.png";
        public static final String PYLON_LOWER_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Pylon_LowerHalf_VariantB_BlueMetal_PylonBodyB.png";
        public static final String PYLON_MID = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Pylon_MidSection_VariantB_BlueMetal_PylonMidBody.png";
        public static final String WIRE_DIAGONAL_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Wire_DiagonalCable_ThinSingle_PowerLineSegmentA.png";
        public static final String WIRE_DIAGONAL_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Wire_DiagonalCable_ThinAltAngle_PowerLineSegmentB.png";
    }

    public static class DecorationComponents {
        public static final String DIR_DECO = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration";
        public static final String ANTENNA_BRACKET = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Antenna_SmallBracket_HorizontalMount_RooftopAerialB.png";
        public static final String ANTENNA_TALL = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Antenna_TallPole_ThinVertical_RooftopAerialA.png";
        public static final String BUILDING_FACTORY_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Building_FactoryStructureA_LargeBackdrop_SceneryA.png";
        public static final String BUILDING_FACTORY_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Building_FactoryStructureB_LargeBackdrop_SceneryB.png";
        public static final String RIVET_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_Rivet_TinyDot_BoltDetail_WallRivetA.png";
        public static final String RIVET_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_Rivet_TinyDotVariant_BoltDetail_WallRivetB.png";
        public static final String SHELF_COLORED = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_Shelf_ColouredRack_SmallShelfUnit_WallMountA.png";
        public static final String DEVICE_SMALL = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_SmallDevice_Coloured_MiscEquipment_FloorDeco.png";
        public static final String ITEM_SMALL = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_SmallItem_Accessory_MiscDetail_FloorOrWall.png";
        public static final String TABLET_BLUE = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_Tablet_SmallBlue_TechDevice_WallOrFloor.png";
        public static final String WAVE_ARCH = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_WaveArch_DarkBlue_ArchShape_CeilingDeco.png";
        public static final String WIRE_SEGMENT_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_WireSegment_TinyHorizontal_CableDetail_WallA.png";
        public static final String WIRE_SEGMENT_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_WireSegment_TinyVariant_CableDetailB_WallB.png";
        public static final String CHAIR_OFFICE = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Furniture_OfficeChair_BlueSeat_BreakRoomProp.png";
        public static final String SHELF_NARROW = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Furniture_Shelf_MetalNarrow_IndustrialRackA.png";
        public static final String SHELF_WIDE = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Furniture_Shelf_MetalWide_IndustrialRackB.png";
        public static final String SCREEN_CONTROL = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Screen_ControlPanel_LargePinkHighlight_AltConsole.png";
        public static final String SCREEN_MONITOR_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Screen_MonitorSingle_TechPanel_WallScreenA.png";
        public static final String SCREEN_MONITOR_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Screen_MonitorDouble_TechPanel_WallScreenB.png";
        public static final String SCREEN_MONITOR_C = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Screen_MonitorDetail_TechPanel_WallScreenC.png";
        public static final String SIGN_WARNING_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Sign_Warning_YellowTypeA_HazardNoticeA.png";
        public static final String SIGN_WARNING_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Sign_Warning_YellowTypeB_HazardNoticeB.png";
        public static final String SIGN_WARNING_C = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Sign_Warning_YellowSmall_HazardNoticeC.png";
        public static final String TANK_FACTORY_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Tank_Industrial_LabelledAC3_LargeTankA.png";
        public static final String TANK_FACTORY_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Tank_Industrial_LabelledEFG2_LargeTankB.png";
    }

    public static class PipeComponents {
        public static final String DIR_PIPES = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube";
        public static final String PIPE_ARCH_LOOP = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_ArchLoop_RedMetal_CurvedSection_WallDeco.png";
        public static final String PIPE_CONNECTOR_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_Connector_DarkBlue_SmallJoint_PipeJoinA.png";
        public static final String PIPE_CONNECTOR_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_Connector_DarkBlue_SmallJointVariant_PipeJoinB.png";
        public static final String PIPE_ELBOW = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_ElbowBend_DarkBlueGrey_LBend_CornerJoin.png";
        public static final String PIPE_END_CAP_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_EndCap_DarkNavy_SmallCap_PipeTerminatorA.png";
        public static final String PIPE_END_CAP_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_EndCap_DarkBlue_SmallCap_PipeTerminatorB.png";
        public static final String PIPE_HORIZONTAL = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_Horizontal_BlueWithClamp_BracketedRun_WallMount.png";
        public static final String PIPE_TJUNCTION = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_TJunction_DarkNavy_ThreeWayConnector_PipeHub.png";
        public static final String PIPE_VERTICAL_RED = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_Vertical_RedMetal_StraightSegment_WallRun.png";
        public static final String PIPE_VERTICAL_TALL = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_Vertical_TallBlue_FullHeightRun_BackgroundPipe.png";
        public static final String PIPE_VERTICAL_THIN = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_Vertical_ThinDarkBlue_StripRun_WallDetail.png";
    }
}
