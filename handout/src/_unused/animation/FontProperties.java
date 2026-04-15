/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class FontProperties {
    public static final String UI_TYPE = "font";
    public static final String DIRECTORY = "Resources/industrial-zone/gui/10 Font/images";
    public static final int CHARACTER_COUNT = 60;
    public static final String FONT_STYLE = "Monospace Pixel Font";
    public static final String[] FONT_CHARACTER_FILES = new String[]{"1.01.png", "1.02.png", "1.03.png", "1.04.png", "1.05.png", "1.06.png", "1.07.png", "1.08.png", "1.09.png", "1.10.png", "1.11.png", "1.12.png", "1.13.png", "1.14.png", "1.15.png", "1.16.png", "1.17.png", "1.18.png", "1.19.png", "1.20.png", "1.21.png", "1.22.png", "1.23.png", "1.24.png", "1.25.png", "1.26.png", "1.27.png", "1.28.png", "1.29.png", "1.30.png", "1.31.png", "1.32.png", "1.33.png", "1.34.png", "1.35.png", "1.36.png", "1.37.png", "1.38.png", "1.39.png", "1.40.png", "1.41.png", "1.42.png", "1.43.png", "1.44.png", "1.45.png", "1.46.png", "1.47.png", "1.48.png", "1.49.png", "1.50.png", "1.51.png", "1.52.png", "1.53.png", "1.54.png", "1.55.png", "1.56.png", "1.57.png", "1.58.png", "1.59.png", "1.60.png", "1.61.png", "1.62.png", "1.63.png"};

    public static final String getCharacterFile(int n) {
        if (n < 0 || n >= FONT_CHARACTER_FILES.length) {
            return FONT_CHARACTER_FILES[0];
        }
        return FONT_CHARACTER_FILES[n];
    }
}
