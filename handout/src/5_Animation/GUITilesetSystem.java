/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class GUIComponentsSystem.GUITilesetSystem {
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
