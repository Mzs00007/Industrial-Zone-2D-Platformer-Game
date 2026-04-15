/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class SplashLogoProperties {
    public static final String UI_TYPE = "splash_logo";
    public static final String DIRECTORY = "Resources/industrial-zone/gui/5 Logo";
    public static final int LOGO_VARIANTS = 3;
    public static final String LOGO_COMPACT = "GUI_Logo_IndustrialZone_Compact.png";
    public static final String LOGO_FULL = "GUI_Logo_IndustrialZone_Full.png";
    public static final String LOGO_MINIMAL = "GUI_Logo_IndustrialZone_Minimal.png";
    public static final String[] LOGO_FILES = new String[]{"GUI_Logo_IndustrialZone_Compact.png", "GUI_Logo_IndustrialZone_Full.png", "GUI_Logo_IndustrialZone_Minimal.png"};
    public static final String LOGO_COLOR = "#4A4A9E";
    public static final String LOGO_ACCENT = "#FFD700";
    public static final String VISUAL_STYLE = "Digital/Industrial";

    public static final String getLogoFile(int n) {
        if (n < 0 || n >= LOGO_FILES.length) {
            return LOGO_COMPACT;
        }
        return LOGO_FILES[n];
    }
public class LogoTextOverlays {
public class MinimalOverlay {
            public static final String TEXT_CONTENT = "NEXUS";
            public static final int OVERLAY_X = 10;
            public static final int OVERLAY_Y = 10;
            public static final String FONT_COLOR = "#00FF00";
            public static final int FONT_SIZE = 16;
            public static final String FONT_WEIGHT = "bold";
            public static final String TEXT_ALIGNMENT = "center";
            public static final String TEXT_EFFECT = "glow_effect";
        }
public class FullOverlay {
            public static final String TEXT_CONTENT = "3359098";
            public static final int OVERLAY_X = 10;
            public static final int OVERLAY_Y = 10;
            public static final String FONT_COLOR = "#4A4A9E";
            public static final int FONT_SIZE = 14;
            public static final String FONT_WEIGHT = "bold";
            public static final String TEXT_ALIGNMENT = "center";
        }
public class CompactOverlay {
            public static final String TEXT_CONTENT = "CSCU9N6";
            public static final int OVERLAY_X = 10;
            public static final int OVERLAY_Y = 10;
            public static final String FONT_COLOR = "#FFD700";
            public static final int FONT_SIZE = 12;
            public static final String FONT_WEIGHT = "bold";
            public static final String TEXT_ALIGNMENT = "center";
        }
    }
}
