/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class GUINumberElements {
    public static final String UI_TYPE = "number_element";
    public static final String DIRECTORY = "Resources/industrial-zone/gui/7 Numbers";
    public static final int DIGIT_COUNT = 10;
    public static final String[] DIGIT_STYLED_FILES = new String[]{"GUI_Number_Digit0_Zero.png", "01_GUI_Number_Digit1_StyledGlyph_Decorative.png", "02_GUI_Number_Digit2_StyledGlyph_Decorative.png", "03_GUI_Number_Digit3_StyledGlyph_Decorative.png", "04_GUI_Number_Digit4_StyledGlyph_Decorative.png", "05_GUI_Number_Digit5_StyledGlyph_Decorative.png", "06_GUI_Number_Digit6_StyledGlyph_Decorative.png", "07_GUI_Number_Digit7_StyledGlyph_Decorative.png", "08_GUI_Number_Digit8_StyledGlyph_Decorative.png", "09_GUI_Number_Digit9_StyledGlyph_Decorative.png"};
    public static final String PUNCTUATION_DOT = "GUI_Number_Symbol_Dot_Decimal.png";
    public static final String PUNCTUATION_COMMA = "GUI_Number_Symbol_Comma_Separator.png";
    public static final String SYMBOL_K = "GUI_Number_Symbol_K_Suffix.png";
    public static final String SYMBOL_M = "GUI_Number_Symbol_M_Million.png";
    public static final String SYMBOL_PLUS = "GUI_Number_Symbol_Plus_Addition.png";
    public static final String SYMBOL_B = "GUI_Number_Symbol_B_Thousand.png";

    public static final String getDigitFile(int n) {
        if (n < 0 || n > 9) {
            return DIGIT_STYLED_FILES[0];
        }
        return DIGIT_STYLED_FILES[n];
    }
}
