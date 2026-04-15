/*
 * Decompiled with CFR 0.152.
 */
package animation;
import game2D.*;

import java.awt.image.BufferedImage;

public class GUIComponentsSystem {

    public static class GUITilesetSystem {
        public static final String SYSTEM_TYPE = "window_frame_tileset";
        public static final String DIRECTORY = "Resources/industrial-zone/gui/1 Frames";
        public static final int TOTAL_PIECES = 82;
        public static final String ASSEMBLY_PATTERN = "Modular corner + edge + fill system";

        public static class SpecialPieces {
            public static final int COUNT = 3;
            public static final String[] SPECIAL_FILES = new String[]{"47_GUI_Frame_EdgeDiagonalStrip_AngledTopLeftSlant_WindowEdge.png", "56_GUI_Frame_EdgeDiagonalStrip_AngledLineTexture_WindowEdge.png", "76_GUI_Frame_CornerBottomSmallNotch_BottomNotchCorner_WindowCorner.png"};
        }

        public static class DividerPieces {
            public static final int COUNT = 6;
            public static final String[] DIVIDER_FILES = new String[]{"16_GUI_Frame_PanelWideRect_TealCyanAccentStripe_DividerBar.png", "17_GUI_Frame_PanelWideRect_DarkerNoAccent_DividerBar.png", "23_GUI_Frame_PanelWideRect_TechDotTexture_DividerBar.png", "25_GUI_Frame_PanelWideRect_WiderPlainDark_DividerBar.png", "26_GUI_Frame_PanelWideRect_DarkerVariant_DividerBar.png", "35_GUI_Frame_PanelHorizDivider_TwoRowDarkNavy_DividerBar.png", "49_GUI_Frame_PanelHorizBar_MediumWidth_DividerBar.png", "70_GUI_Frame_Panel2CellWideDivider_TwoCellBlock_DividerBar.png"};
        }

        public static class PanelPieces {
            public static final int COUNT = 14;
            public static final String[] PANEL_FILES = new String[]{"37_GUI_Frame_PanelInsetSquare_SingleCellDarkBorder_PanelCell.png", "41_GUI_Frame_Panel2Cell_TwoInsetSquares_PanelCell.png", "42_GUI_Frame_Panel2CellVariant_TwoInsetSquaresDiffShade_PanelCell.png", "51_GUI_Frame_PanelInsetSquare_SingleCell_PanelCell.png", "52_GUI_Frame_Panel2CellVariant2_TwoInsetSquares_PanelCell.png", "53_GUI_Frame_Panel2CellVariant3_TwoInsetSlightBorderDiff_PanelCell.png", "58_GUI_Frame_PanelWideRect_InsetBorderWider_PanelCell.png", "60_GUI_Frame_PanelMedium_InsetSquareTaller_PanelCell.png", "61_GUI_Frame_Panel2Cell_TwoInsetSquaresWithIcons_PanelCell.png", "62_GUI_Frame_Panel2CellVariant_TwoInsetSquaresIconsAlt_PanelCell.png", "71_GUI_Frame_Panel2CellGrid_TwoCellGridBlock_PanelCell.png", "79_GUI_Frame_PanelInsetSquare_DarkBorderSquare_PanelCell.png", "80_GUI_Frame_PanelInsetSquare_LighterVariant_PanelCell.png", "82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png"};
        }

        public static class FillPieces {
            public static final int COUNT = 12;
            public static final String[] FILL_FILES = new String[]{"07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png", "11_GUI_Frame_FillDiagonalTexture_FaintDiagLines_WindowFill.png", "38_GUI_Frame_FillSolidNavy_WideRectNoBorder_WindowFill.png", "39_GUI_Frame_FillSolidNavy_WiderVariant_WindowFill.png", "40_GUI_Frame_FillSolidDarkNavy_FullBlock_WindowFill.png", "45_GUI_Frame_FillSolidNavy_PlainFlatBlock_WindowFill.png", "46_GUI_Frame_FillSolidNavy_WideFlatRect_WindowFill.png", "59_GUI_Frame_FillSolidNavy_WideFlatRect_WindowFill.png", "65_GUI_Frame_FillSolidDarkNavy_PlainBlock_WindowFill.png", "66_GUI_Frame_FillSolidNavyLighter_SlightlyLighter_WindowFill.png", "68_GUI_Frame_FillSolidNavy_PlainBlock_WindowFill.png", "74_GUI_Frame_FillSolidNavy_PlainBlock_WindowFill.png"};
        }

        public static class EdgePieces {
            public static final int COUNT = 20;
            public static final String RENDERING = "Stretch horizontally (top/bottom) or vertically (left/right)";
            public static final String[] TOP_EDGE_FILES = new String[]{"02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png", "36_GUI_Frame_EdgeTopBarVariant_LighterStripe_WindowTopEdge.png", "67_GUI_Frame_EdgeTopBar_PlainBar_WindowTopEdge.png"};
            public static final String[] BOTTOM_EDGE_FILES = new String[]{"20_GUI_Frame_EdgeBottomBar_PlainDarkStrip_WindowBottomEdge.png", "24_GUI_Frame_EdgeBottomBar_DotLineTexture_WindowBottomEdge.png", "50_GUI_Frame_EdgeBottomBar_HorizStripTexture_WindowBottomEdge.png", "54_GUI_Frame_EdgeBottomBar_LightBlueAccentLine_WindowBottomEdge.png", "75_GUI_Frame_EdgeBottomBar_PlainHorizStrip_WindowBottomEdge.png"};
            public static final String[] LEFT_EDGE_FILES = new String[]{"05_GUI_Frame_EdgeLeftStrip_TallNarrowVerticalBar_WindowLeftEdge.png", "10_GUI_Frame_EdgeLeftStrip_TallBlueTintBar_WindowLeftEdge.png", "12_GUI_Frame_EdgeLeftStrip_TallDarkerBar_WindowLeftEdge.png", "13_GUI_Frame_EdgeLeftStrip_TallThinLighter_WindowLeftEdge.png", "14_GUI_Frame_EdgeLeftStrip_TallWiderWithTrim_WindowLeftEdge.png", "15_GUI_Frame_EdgeLeftStrip_TallLighterBlueTrim_WindowLeftEdge.png", "43_GUI_Frame_EdgeLeftStrip_NarrowDarkVertical_WindowLeftEdge.png", "57_GUI_Frame_EdgeLeftStrip_NarrowDarkSlightVariant_WindowLeftEdge.png", "69_GUI_Frame_EdgeLeftStrip_LeftEdgeBar_WindowLeftEdge.png"};
            public static final String[] RIGHT_EDGE_FILES = new String[]{"06_GUI_Frame_EdgeRightStrip_TallNarrowVerticalBar_WindowRightEdge.png", "18_GUI_Frame_EdgeRightStrip_TallBlueAccentLine_WindowRightEdge.png", "22_GUI_Frame_EdgeRightStrip_TallNarrowPlain_WindowRightEdge.png", "28_GUI_Frame_EdgeRightStrip_TallBlueAccent_WindowRightEdge.png", "48_GUI_Frame_EdgeRightStrip_NarrowVerticalBar_WindowRightEdge.png", "55_GUI_Frame_EdgeRightStrip_NarrowSlightlyWider_WindowRightEdge.png", "72_GUI_Frame_EdgeThinRightStrip_ThinVerticalRight_WindowRightEdge.png"};
        }

        public static class CornerPieces {
            public static final int COUNT = 16;
            public static final String[] CORNER_FILES = new String[]{"01_GUI_Frame_CornerTopLeft_TallLShapePiece_WindowCorner.png", "03_GUI_Frame_CornerTopRight_TallLShapeMirror_WindowCorner.png", "19_GUI_Frame_CornerBottomLeft_LShapeCorner_WindowCorner.png", "27_GUI_Frame_CornerBottomRight_DiagonalAngleTrim_WindowCorner.png", "04_GUI_Frame_CornerTopLeftRivet_RedDotAccents_WindowCorner.png", "09_GUI_Frame_CornerInsetTopLeft_InsetBorderSquare_WindowCorner.png", "21_GUI_Frame_CornerBottomLeftFull_InsetSquareCorner_WindowCorner.png", "29_GUI_Frame_CornerBottomRightFull_BracketCorner_WindowCorner.png", "30_GUI_Frame_CornerTopRightFull_BracketTShape_WindowCorner.png", "31_GUI_Frame_CornerTopRight_SmallInsetLightTrim_WindowCorner.png", "32_GUI_Frame_CornerTopLeft_TriangleCut_WindowCorner.png", "33_GUI_Frame_CornerTopRight_TriangleCutMirror_WindowCorner.png", "34_GUI_Frame_CornerTopRight_DiagonalCut_WindowCorner.png", "63_GUI_Frame_CornerHexagonal_LightGreyOctagonShape_WindowCorner.png", "64_GUI_Frame_CornerDiagonalCutTopLeft_WindowCorner.png", "73_GUI_Frame_CornerBottomLeft_PlainCorner_WindowCorner.png"};
        }

        public static class TileAdjacencyRules {
            public static final String CONCEPT = "Tile Connection Mathematics";
            public static final String PURPOSE = "Ensure tiles only connect to valid neighbors";
        }
    }

    public static class StandardUIIcons {
        public static final String UI_TYPE = "standard_icon";
        public static final String DIRECTORY = "Resources/industrial-zone/gui/3 Icons/Icons";
        public static final int TOTAL_ICONS = 40;
        public static final String ICON_PURPOSE = "Symbolic UI elements for menus and HUD";
        public static final String IMAGE_SIZE = "16\u00d716 to 32\u00d732 pixels (scalable)";
        public static final String[] ICON_CATEGORIES = new String[]{"Navigation", "Actions", "Media", "System", "Communication", "Alerts", "Misc"};
        public static final String[] ICON_FILES = new String[]{"Icon_Arrow_Up_Navigation_MenuScroll.png", "Icon_Arrow_Down_Navigation_MenuScroll.png", "Icon_Arrow_Left_Navigation_Movement.png", "Icon_Arrow_Right_Navigation_Movement.png", "Icon_Arrow_Back_Navigation_MenuReturn.png", "Icon_Check_Confirm_Success_MenuAccept.png", "Icon_Cross_Cancel_Close_MenuDeny.png", "Icon_Plus_Add_Increase_Inventory.png", "Icon_Minus_Remove_Decrease_Inventory.png", "Icon_Settings_Options_Config_Menu.png", "Icon_Menu_Hamburger_Options_Button.png", "Icon_Play_Start_Begin_Media.png", "Icon_Pause_Hold_Stop_Media.png", "Icon_Stop_End_Exit_Media.png", "Icon_FastForward_Skip_Next_Media.png", "Icon_Rewind_Previous_Back_Media.png", "Icon_Home_Main_Hub_Menu.png", "Icon_Save_Store_Write_File.png", "Icon_Load_Open_Read_File.png", "Icon_Exit_Quit_Close_App.png", "Icon_Help_Question_Support_Info.png", "Icon_Info_Details_Information_HUD.png", "Icon_Lock_Secure_Password_Security.png", "Icon_Unlock_UnSecure_Open_Security.png", "Icon_User_Profile_Person_Account.png", "Icon_Users_Group_Team_Multiplayer.png", "Icon_Chat_Talk_Speech_Message.png", "Icon_Email_Mail_Message_Inbox.png", "Icon_Phone_Call_Contact_Communication.png", "Icon_Download_Receive_Import_File.png", "Icon_Alert_Attention_Warning_Danger.png", "Icon_Warning_Caution_Risk_Hazard.png", "Icon_Info_Notification_Message_Log.png", "Icon_Help_Support_Question_FAQ.png", "Icon_Star_Favorite_Rating_Quality.png", "Icon_Heart_Love_Health_Status.png", "Icon_Sword_Attack_Combat_Battle.png", "Icon_Shield_Defense_Protection_Guard.png", "Icon_Battery_Power_Energy_Status.png", "Icon_Volume_Audio_Sound_Speaker.png"};
    }

    public static class GUIButtonSystemProperties {

        public static class ButtonStateVariants {
            public static final String UI_TYPE = "toggle_switch";
            public static final String DIRECTORY = "Resources/industrial-zone/gui/3 Icons/Buttons2";
            public static final int TOTAL_VARIANTS = 10;
            public static final String STATE_PURPOSE = "Colored toggle switches for binary on/off controls";
            public static final String[] STATE_VARIANT_FILES = new String[]{"GUI_Button_State_Variant02_01.png", "GUI_Button_State_Variant02_02.png", "GUI_Button_State_Variant02_03.png", "GUI_Button_State_Variant02_04.png", "GUI_Button_State_Variant02_05.png", "GUI_Button_State_Variant02_06.png", "GUI_Button_State_Variant02_07.png", "GUI_Button_State_Variant02_08.png", "GUI_Button_State_Variant02_09.png", "GUI_Button_State_Variant02_10.png"};
            public static final String[] HOLLOW_VARIANT_FILES = new String[]{"GUI_Button_State_Variant02_11.png", "GUI_Button_State_Variant02_12.png", "GUI_Button_State_Variant02_13.png", "GUI_Button_State_Variant02_14.png", "GUI_Button_State_Variant02_15.png", "GUI_Button_State_Variant02_16.png", "GUI_Button_State_Variant02_17.png", "GUI_Button_State_Variant02_18.png", "GUI_Button_State_Variant02_19.png", "GUI_Button_State_Variant02_20.png"};
            public static final String[] VARIANT_DESCRIPTIONS = new String[]{"Blue Toggle - Audio/Sound settings", "Green Toggle - Graphics quality", "Orange Toggle - Performance mode", "Red Toggle - Danger/Adult content", "Purple Toggle - Special features", "Yellow Toggle - Warning/caution state", "Cyan Toggle - Accessibility options", "Pink Toggle - Color/theme customization", "Lime Toggle - New/unlocked features", "White Toggle - Display/screen options"};
        }

        public static class ButtonColorMaps {
            public static final String UI_TYPE = "button_animation_spritesheet";
            public static final String DIRECTORY = "Resources/industrial-zone/gui/6 Buttons";
            public static final int TOTAL_VARIANTS = 10;
            public static final String ANIMATION_TYPE = "Press animation sequence";
            public static final String[] COLOR_MAP_FILES = new String[]{"GUI_ButtonColorMap_Variant_01.png", "GUI_ButtonColorMap_Variant_02.png", "GUI_ButtonColorMap_Variant_03.png", "GUI_ButtonColorMap_Variant_04.png", "GUI_ButtonColorMap_Variant_05.png", "GUI_ButtonColorMap_Variant_06.png", "GUI_ButtonColorMap_Variant_07.png", "GUI_ButtonColorMap_Variant_08.png", "GUI_ButtonColorMap_Variant_09.png", "GUI_ButtonColorMap_Variant_10.png"};
            public static final String[] VARIANT_DESCRIPTIONS = new String[]{"Blue Button - Smooth press animation", "Green Button - Confirm action animation", "Orange Button - Alert state animation", "Red Button - Destructive action animation", "Purple Button - Menu navigation animation", "Navy Button - Background action animation", "Cyan Button - Focus state animation", "Gold Button - Special reward animation", "Grey Button - Inactive state animation", "Pink Button - Attention-grabbing animation"};
        }
    }

    public static class HUDBarSystem {
        public static final String UI_TYPE = "hud_bar";
        public static final String DIRECTORY = "Resources/industrial-zone/gui/2 Bars";
        public static final int TOTAL_STATES = 13;
        public static final String RENDERING_FORMAT = "Static PNG, no animation";

        public static class EnergyBarStates {
            public static final String BAR_TYPE = "energy_bar";
            public static final String COLOR = "Blue/Cyan #00DDFF";
            public static final String PURPOSE = "Display player energy/mana percentage";
            public static final String[] ENERGY_BAR_FILES = new String[]{"09_GUI_Bar_EnergyBar_Full100pct_BlueCyanFillDarkFrame_HUD.png", "10_GUI_Bar_EnergyBar_80pct_BlueFill_HUD.png", "11_GUI_Bar_EnergyBar_60pct_CyanFill_HUD.png", "12_GUI_Bar_EnergyBar_40pct_LightBlueFill_HUD.png", "13_GUI_Bar_EnergyBar_20pct_OrangeFlashing_HUD.png", "14_GUI_Bar_EnergyBar_Empty0pct_GreyEmpty_HUD.png"};
            public static final int[] PERCENTAGE_VALUES = new int[]{100, 80, 60, 40, 20, 0};
            public static final String[] STATE_NAMES = new String[]{"Full", "Good", "Okay", "Low", "Critical", "Empty"};
        }

        public static class HealthBarStates {
            public static final String BAR_TYPE = "health_bar";
            public static final String COLOR = "Red/Orange #FF6B35";
            public static final String PURPOSE = "Display player health percentage";
            public static final String[] HEALTH_BAR_FILES = new String[]{"01_GUI_Bar_HealthBar_Full100pct_RedOrangeFillDarkFrame_HUD.png", "02_GUI_Bar_HealthBar_80pct_RedOrangeFill_HUD.png", "03_GUI_Bar_HealthBar_60pct_OrangeFill_HUD.png", "04_GUI_Bar_HealthBar_40pct_OrangeFill_HUD.png", "05_GUI_Bar_HealthBar_20pct_DarkRedFill_HUD.png", "06_GUI_Bar_HealthBar_Critical5pct_RedFlashing_HUD.png", "07_GUI_Bar_HealthBar_Empty0pct_GreyEmpty_HUD.png"};
            public static final int[] PERCENTAGE_VALUES = new int[]{100, 80, 60, 40, 20, 5, 0};
            public static final String[] STATE_NAMES = new String[]{"Full", "Good", "Okay", "Low", "Critical", "UltraCritical", "Dead"};

            public static BufferedImage getHealthBar(int n) {
                if (n >= 100) {
                    return null;
                }
                if (n >= 80) {
                    return null;
                }
                if (n >= 60) {
                    return null;
                }
                if (n >= 40) {
                    return null;
                }
                if (n >= 20) {
                    return null;
                }
                if (n > 0) {
                    return null;
                }
                return null;
            }
        }
    }

    public static class UIElementProperties {

        public static class DigitDisplayElements {
            public static final String UI_TYPE = "digit_display";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final String FONT_NAME = "Pixel Font Grey";
            public static final String COLOR = "Grey #808080";
            public static final int DIGIT_COUNT = 10;
            public static final int GLYPH_WIDTH = 8;
            public static final int GLYPH_HEIGHT = 8;
            public static final String[] DIGIT_FILES = new String[]{"UI_Digit_Zero_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "UI_Digit_One_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "UI_Digit_Two_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "UI_Digit_Three_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "UI_Digit_Four_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "UI_Digit_Five_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "UI_Digit_Six_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "UI_Digit_Seven_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "UI_Digit_Eight_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "UI_Digit_Nine_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png"};

            public static String getDigitFile(int n) {
                if (n < 0 || n > 9) {
                    return DIGIT_FILES[0];
                }
                return DIGIT_FILES[n];
            }
        }
    }
}
