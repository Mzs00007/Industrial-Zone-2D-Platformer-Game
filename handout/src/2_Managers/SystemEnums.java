/**
 * COMPREHENSIVE SYSTEM ENUMS - All Missing String Constants
 * These enums convert hundreds of hardcoded strings to type-safe constants
 * Generated: 2026-04-13
 */

public class SystemEnums {

    // ==================== AUDIO SYSTEM ====================
    /**
     * Sound Effect Types - 20 different sound effect categories
     * Replaces: AudioSystem.SoundEffectPresets constants
     */
    public enum SoundEffectType {
        JUMP("jump"),
        LAND("land"),
        FALL("fall"),
        DASH("dash"),
        HIT("hit"),
        PUNCH("punch"),
        KICK("kick"),
        DEATH("death"),
        HURT("hurt"),
        ITEM_COLLECT("item_collect"),
        POWERUP("powerup"),
        COIN("coin"),
        EXPLOSION("explosion"),
        UI_CLICK("ui_click"),
        UI_HOVER("ui_hover"),
        UI_SELECT("ui_select"),
        UI_BACK("ui_back"),
        DOOR_OPEN("door_open"),
        WATER_SPLASH("water_splash"),
        FIRE_BURN("fire_burn"),
        AMBIENT_LOOP("ambient_loop"),
        WIND("wind"),
        RAIN("rain");

        private final String id;

        SoundEffectType(String id) {
            this.id = id;
        }

        public String getId() { return id; }

        @Override
        public String toString() { return id; }
    }

    // ==================== VFX SYSTEM ====================
    /**
     * VFX Format Types - Asset format specifications
     * Replaces: VFXSystem format constants
     */
    public enum VFXFormatType {
        SMOKE_FRAME("Resources/industrial-zone/vfx/1 Smoke/"),
        BLOOD_SPLAT("Resources/industrial-zone/vfx/2 Blood/"),
        IMPACT_SPARK("Resources/industrial-zone/vfx/3 Sparks/"),
        SPARK_BURST("Resources/industrial-zone/vfx/3 Sparks/"),
        PARTICLE("Resources/industrial-zone/vfx/4 Particles/"),
        SHARD("Resources/industrial-zone/vfx/5 Other/"),
        ENERGY("Resources/industrial-zone/vfx/5 Other/"),
        PORTAL("Resources/industrial-zone/vfx/5 Other/");

        private final String basePath;

        VFXFormatType(String basePath) {
            this.basePath = basePath;
        }

        public String getBasePath() { return basePath; }
    }

    /**
     * VFX Effect Types - Categories of visual effects
     */
    public enum VFXEffectType {
        EXPLOSION("explosion"),
        IMPACT("impact"),
        SMOKE("smoke"),
        BLOOD_SPLAT("blood_splat"),
        SPARK_BURST("spark_burst"),
        PARTICLE_SYSTEM("particle_system"),
        ENERGY_EFFECT("energy_effect"),
        PORTAL_EFFECT("portal_effect");

        private final String name;

        VFXEffectType(String name) {
            this.name = name;
        }

        public String getName() { return name; }

        @Override
        public String toString() { return name; }
    }

    // ==================== GAME STATE ====================
    /**
     * Level Difficulty Settings
     */
    public enum LevelDifficulty {
        EASY("Easy"),
        NORMAL("Normal"),
        MEDIUM("Medium"),
        HARD("Hard"),
        NIGHTMARE("Nightmare"),
        CUSTOM("Custom");

        private final String displayName;

        LevelDifficulty(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() { return displayName; }
    }

    // ==================== GUI SYSTEM ====================
    /**
     * GUI Asset Frame Types - 70+ frame constants from GUIAssets.java
     * Organized by category
     */
    public enum GUIFrameType {
        // Corners
        CORNER_TL_01("Resources/industrial-zone/gui/1 Frames/01_GUI_Frame_CornerTopLeft_TallLShapePiece_WindowCorner.png"),
        CORNER_TL_TRIANGLE("Resources/industrial-zone/gui/1 Frames/32_GUI_Frame_CornerTopLeft_TriangleCut_WindowCorner.png"),
        CORNER_TL_INSET("Resources/industrial-zone/gui/1 Frames/09_GUI_Frame_CornerInsetTopLeft_InsetBorderSquare_WindowCorner.png"),
        CORNER_TR_01("Resources/industrial-zone/gui/1 Frames/03_GUI_Frame_CornerTopRight_TallLShapeMirror_WindowCorner.png"),
        CORNER_TR_TRIANGLE("Resources/industrial-zone/gui/1 Frames/33_GUI_Frame_CornerTopRight_TriangleCutMirror_WindowCorner.png"),
        CORNER_TR_LIGHT("Resources/industrial-zone/gui/1 Frames/31_GUI_Frame_CornerTopRight_SmallInsetLightTrim_WindowCorner.png"),
        CORNER_TR_FULL("Resources/industrial-zone/gui/1 Frames/30_GUI_Frame_CornerTopRightFull_BracketTShape_WindowCorner.png"),
        CORNER_BL_LSHAPE("Resources/industrial-zone/gui/1 Frames/19_GUI_Frame_CornerBottomLeft_LShapeCorner_WindowCorner.png"),
        CORNER_BL_FULL("Resources/industrial-zone/gui/1 Frames/21_GUI_Frame_CornerBottomLeftFull_InsetSquareCorner_WindowCorner.png"),
        CORNER_BL_PLAIN("Resources/industrial-zone/gui/1 Frames/73_GUI_Frame_CornerBottomLeft_PlainCorner_WindowCorner.png"),
        CORNER_BR_DIAGONAL("Resources/industrial-zone/gui/1 Frames/27_GUI_Frame_CornerBottomRight_DiagonalAngleTrim_WindowCorner.png"),
        CORNER_BR_FULL("Resources/industrial-zone/gui/1 Frames/29_GUI_Frame_CornerBottomRightFull_BracketCorner_WindowCorner.png"),
        CORNER_BR_PLAIN("Resources/industrial-zone/gui/1 Frames/78_GUI_Frame_CornerBottomRight_PlainCorner_WindowCorner.png"),
        CORNER_BR_NOTCH("Resources/industrial-zone/gui/1 Frames/76_GUI_Frame_CornerBottomSmallNotch_BottomNotchCorner_WindowCorner.png"),

        // Edges - Top
        EDGE_TOP_01("Resources/industrial-zone/gui/1 Frames/02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png"),
        EDGE_TOP_VARIANT("Resources/industrial-zone/gui/1 Frames/36_GUI_Frame_EdgeTopBarVariant_LighterStripe_WindowTopEdge.png"),
        EDGE_TOP_PLAIN("Resources/industrial-zone/gui/1 Frames/67_GUI_Frame_EdgeTopBar_PlainBar_WindowTopEdge.png"),

        // Edges - Bottom
        EDGE_BOTTOM_PLAIN("Resources/industrial-zone/gui/1 Frames/20_GUI_Frame_EdgeBottomBar_PlainDarkStrip_WindowBottomEdge.png"),
        EDGE_BOTTOM_TEXTURE("Resources/industrial-zone/gui/1 Frames/24_GUI_Frame_EdgeBottomBar_DotLineTexture_WindowBottomEdge.png"),
        EDGE_BOTTOM_STRIPED("Resources/industrial-zone/gui/1 Frames/50_GUI_Frame_EdgeBottomBar_HorizStripTexture_WindowBottomEdge.png"),
        EDGE_BOTTOM_LIGHT("Resources/industrial-zone/gui/1 Frames/54_GUI_Frame_EdgeBottomBar_LightBlueAccentLine_WindowBottomEdge.png"),

        // Edges - Left
        EDGE_LEFT_01("Resources/industrial-zone/gui/1 Frames/05_GUI_Frame_EdgeLeftStrip_TallNarrowVerticalBar_WindowLeftEdge.png"),
        EDGE_LEFT_BLUETINT("Resources/industrial-zone/gui/1 Frames/10_GUI_Frame_EdgeLeftStrip_TallBlueTintBar_WindowLeftEdge.png"),
        EDGE_LEFT_DARK("Resources/industrial-zone/gui/1 Frames/12_GUI_Frame_EdgeLeftStrip_TallDarkerBar_WindowLeftEdge.png"),
        EDGE_LEFT_LIGHT("Resources/industrial-zone/gui/1 Frames/13_GUI_Frame_EdgeLeftStrip_TallThinLighter_WindowLeftEdge.png"),
        EDGE_LEFT_TRIM("Resources/industrial-zone/gui/1 Frames/15_GUI_Frame_EdgeLeftStrip_TallLighterBlueTrim_WindowLeftEdge.png"),
        EDGE_LEFT_BAR("Resources/industrial-zone/gui/1 Frames/69_GUI_Frame_EdgeLeftStrip_LeftEdgeBar_WindowLeftEdge.png"),

        // Edges - Right
        EDGE_RIGHT_01("Resources/industrial-zone/gui/1 Frames/06_GUI_Frame_EdgeRightStrip_TallNarrowVerticalBar_WindowRightEdge.png"),
        EDGE_RIGHT_BLUE("Resources/industrial-zone/gui/1 Frames/18_GUI_Frame_EdgeRightStrip_TallBlueAccentLine_WindowRightEdge.png"),
        EDGE_RIGHT_ACCENT("Resources/industrial-zone/gui/1 Frames/28_GUI_Frame_EdgeRightStrip_TallBlueAccent_WindowRightEdge.png"),
        EDGE_RIGHT_PLAIN("Resources/industrial-zone/gui/1 Frames/22_GUI_Frame_EdgeRightStrip_TallNarrowPlain_WindowRightEdge.png"),
        EDGE_RIGHT_THIN("Resources/industrial-zone/gui/1 Frames/72_GUI_Frame_EdgeThinRightStrip_ThinVerticalRight_WindowRightEdge.png"),

        // Fills
        FILL_SOLID_NAVY_01("Resources/industrial-zone/gui/1 Frames/07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png"),
        FILL_SOLID_NAVY_FLAT("Resources/industrial-zone/gui/1 Frames/45_GUI_Frame_FillSolidNavy_PlainFlatBlock_WindowFill.png"),
        FILL_SOLID_NAVY_BLOCK("Resources/industrial-zone/gui/1 Frames/68_GUI_Frame_FillSolidNavy_PlainBlock_WindowFill.png"),
        FILL_DARK_NAVY_01("Resources/industrial-zone/gui/1 Frames/40_GUI_Frame_FillSolidDarkNavy_FullBlock_WindowFill.png"),
        FILL_DARK_NAVY_PLAIN("Resources/industrial-zone/gui/1 Frames/65_GUI_Frame_FillSolidDarkNavy_PlainBlock_WindowFill.png"),
        FILL_DARK_NAVY_DARKER("Resources/industrial-zone/gui/1 Frames/77_GUI_Frame_FillSolidNavyDark_DarkBlock_WindowFill.png"),
        FILL_NAVY_LIGHTER("Resources/industrial-zone/gui/1 Frames/66_GUI_Frame_FillSolidNavyLighter_SlightlyLighter_WindowFill.png"),
        FILL_DIAGONAL_TEXTURE("Resources/industrial-zone/gui/1 Frames/11_GUI_Frame_FillDiagonalTexture_FaintDiagLines_WindowFill.png"),

        // Panels & Dividers
        PANEL_SINGLE_CELL("Resources/industrial-zone/gui/1 Frames/37_GUI_Frame_PanelInsetSquare_SingleCellDarkBorder_PanelCell.png"),
        PANEL_SINGLE_LIGHT("Resources/industrial-zone/gui/1 Frames/80_GUI_Frame_PanelInsetSquare_LighterVariant_PanelCell.png"),
        PANEL_SINGLE_DARK("Resources/industrial-zone/gui/1 Frames/79_GUI_Frame_PanelInsetSquare_DarkBorderSquare_PanelCell.png"),
        PANEL_MEDIUM("Resources/industrial-zone/gui/1 Frames/60_GUI_Frame_PanelMedium_InsetSquareTaller_PanelCell.png"),
        PANEL_2CELL("Resources/industrial-zone/gui/1 Frames/41_GUI_Frame_Panel2Cell_TwoInsetSquares_PanelCell.png"),
        PANEL_DIVIDER_TEAL("Resources/industrial-zone/gui/1 Frames/16_GUI_Frame_PanelWideRect_TealCyanAccentStripe_DividerBar.png"),
        PANEL_DIVIDER_DARK("Resources/industrial-zone/gui/1 Frames/17_GUI_Frame_PanelWideRect_DarkerNoAccent_DividerBar.png"),
        PANEL_DIVIDER_TECH("Resources/industrial-zone/gui/1 Frames/23_GUI_Frame_PanelWideRect_TechDotTexture_DividerBar.png"),
        PANEL_DIVIDER_PLAIN("Resources/industrial-zone/gui/1 Frames/25_GUI_Frame_PanelWideRect_WiderPlainDark_DividerBar.png"),
        PANEL_DIVIDER_DARKER("Resources/industrial-zone/gui/1 Frames/26_GUI_Frame_PanelWideRect_DarkerVariant_DividerBar.png"),

        // Decorative
        PANEL_CROSS_DIAMOND("Resources/industrial-zone/gui/1 Frames/08_GUI_Frame_PanelCrossDiamond_BlueGeometricPattern_DecorativeFill.png"),
        PANEL_HEXAGON("Resources/industrial-zone/gui/1 Frames/63_GUI_Frame_CornerHexagonal_LightGreyOctagonShape_WindowCorner.png");

        private final String path;

        GUIFrameType(String path) {
            this.path = path;
        }

        public String getPath() { return path; }
    }

    /**
     * GUI Button Types - Button variants
     */
    public enum GUIButtonType {
        VARIANT_01("Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_01.png"),
        VARIANT_02("Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_02.png"),
        VARIANT_03("Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_03.png"),
        VARIANT_04("Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_04.png"),
        VARIANT_05("Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_05.png"),
        VARIANT_06("Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_06.png"),
        VARIANT_07("Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_07.png"),
        VARIANT_08("Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_08.png"),
        VARIANT_09("Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_09.png"),
        VARIANT_10("Resources/industrial-zone/gui/6 Buttons/GUI_ButtonColorMap_Variant_10.png");

        private final String path;

        GUIButtonType(String path) {
            this.path = path;
        }

        public String getPath() { return path; }
    }

    /**
     * GUI Health Bar Types - Different health percentage displays
     */
    public enum GUIHealthBarType {
        HEALTH_100("Resources/industrial-zone/gui/2 Bars/01_GUI_Bar_HealthBar_Full100pct_RedOrangeFillDarkFrame_HUD.png"),
        HEALTH_80("Resources/industrial-zone/gui/2 Bars/02_GUI_Bar_HealthBar_80pct_RedOrangeFill_HUD.png"),
        HEALTH_60("Resources/industrial-zone/gui/2 Bars/03_GUI_Bar_HealthBar_60pct_RedOrangeFill_HUD.png"),
        HEALTH_40("Resources/industrial-zone/gui/2 Bars/04_GUI_Bar_HealthBar_40pct_RedOrangeFill_HUD.png"),
        HEALTH_20("Resources/industrial-zone/gui/2 Bars/05_GUI_Bar_HealthBar_20pct_RedOrangeFill_HUD.png"),
        HEALTH_CRITICAL("Resources/industrial-zone/gui/2 Bars/06_GUI_Bar_HealthBar_5pctCritical_RedOrangeFill_HUD.png");

        private final String path;

        GUIHealthBarType(String path) {
            this.path = path;
        }

        public String getPath() { return path; }
    }

    // ==================== RESOURCE PATHS ====================
    /**
     * Common Resource Base Paths
     */
    public enum ResourcePaths {
        ASSETS("Resources/"),
        INDUSTRIAL_ZONE("Resources/industrial-zone/"),
        CHARACTERS("Resources/industrial-zone/characters/"),
        ENEMIES("Resources/industrial-zone/characters/enemies/"),
        VFX("Resources/industrial-zone/vfx/"),
        GUI("Resources/industrial-zone/gui/"),
        AUDIO("Resources/industrial-zone/audio/"),
        WEAPONS("Resources/industrial-zone/weapons/"),
        TILES("Resources/industrial-zone/1 Tiles/"),
        KEYBOARD_KEYS("Resources/industrial-zone/KeyBoard_Keys/"),
        MOUSE_KEYS("Resources/industrial-zone/Mouse_keys/"),
        SPARK_BASE("Resources/industrial-zone/vfx/3 Sparks/"),
        BUTTON_DIR("Resources/industrial-zone/gui/6 Buttons/"),
        BAR_DIR("Resources/industrial-zone/gui/2 Bars/");

        private final String path;

        ResourcePaths(String path) {
            this.path = path;
        }

        public String getPath() { return path; }

        @Override
        public String toString() { return path; }
    }

    // ==================== SETTINGS ====================
    /**
     * Toggle Setting Names - Settings that can be turned on/off
     */
    public enum ToggleSetting {
        BACKGROUND_MUSIC("Background Music"),
        SOUND_EFFECTS("Sound Effects"),
        SCREEN_SHAKE("Screen Shake"),
        FULLSCREEN("Fullscreen"),
        VSYNC("VSync"),
        PARTICLES("Particles"),
        BLOOM("Bloom"),
        MOTION_BLUR("Motion Blur");

        private final String displayName;

        ToggleSetting(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() { return displayName; }

        @Override
        public String toString() { return displayName; }
    }

    // ==================== LEVEL METADATA ====================
    /**
     * Level Metadata Tags
     */
    public enum LevelMetadata {
        LEVEL_NAME("levelName"),
        DIFFICULTY("difficulty"),
        STORY("story"),
        ENVIRONMENT("environment"),
        THEME("theme"),
        BOSS_COUNT("bossCount"),
        ENEMY_COUNT("enemyCount"),
        TIME_LIMIT("timeLimit"),
        COLLECTIBLES("collectibles");

        private final String key;

        LevelMetadata(String key) {
            this.key = key;
        }

        public String getKey() { return key; }
    }

    // ==================== DEBUG TAGS ====================
    /**
     * System Debug Tags
     */
    public enum SystemTag {
        ANIMATION("[Animation]"),
        PHYSICS("[Physics]"),
        AI("[AI]"),
        AUDIO("[Audio]"),
        RENDERING("[Rendering]"),
        GUI("[GUI]"),
        UTILS("[UtilsSystem]"),
        LEVEL("[Level]"),
        COMBAT("[Combat]"),
        VFX("[VFX]"),
        CAMERA("[Camera]"),
        EVENTS("[Events]"),
        CORE("[Core]");

        private final String tag;

        SystemTag(String tag) {
            this.tag = tag;
        }

        public String getTag() { return tag; }

        @Override
        public String toString() { return tag; }
    }
}
