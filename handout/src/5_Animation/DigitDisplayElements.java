/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class GUIComponentsSystem.UIElementProperties.DigitDisplayElements {
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
