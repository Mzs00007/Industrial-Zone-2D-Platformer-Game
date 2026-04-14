/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.TileCompositionPatterns.HorizontalBrickPlatformPattern {
    public static final String PATTERN_NAME = "HorizontalBrickPlatform";
    public static final String PATTERN_TYPE = "horizontal_strip";
    public static final String LEFT_EDGE_FILE = "02_Panel_HorizStripeBrick_BluePurple_SolidFloorVariantB.png";
    public static final String CENTER_TILE_FILE = "03_Panel_HorizStripeBrick_MidBlue_SolidFloorVariantC.png";
    public static final String RIGHT_EDGE_FILE = "04_Panel_HorizStripeBrick_DarkBlue_SolidFloorVariantD.png";
    public static final String NOTE = "Combine LEFT + (CENTER \u00d7 N) + RIGHT to create platform of any length";
    public static final int MIN_WIDTH_TILES = 1;
    public static final int MAX_WIDTH_TILES = 20;
}
