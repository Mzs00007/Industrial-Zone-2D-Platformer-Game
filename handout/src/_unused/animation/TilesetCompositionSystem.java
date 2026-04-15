/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
public class TilesetCompositionSystem {
    public static final String TYPE_TILESET_COMPOSITION = "tileset_composition_system";
    public static final String COLOR_RED = "Red";
    public static final String COLOR_BLUE = "Blue";
    public static final String COLOR_GREEN = "Green";
    public static final String COLOR_YELLOW = "Yellow";
    public static final String COLOR_PURPLE = "Purple";
    public static final String COLOR_ORANGE = "Orange";
    public static final String POS_LEFT_CORNER = "LeftCorner";
    public static final String POS_CENTER = "Center";
    public static final String POS_RIGHT_CORNER = "RightCorner";
    public static final String POS_TOP_LEFT = "TopLeft";
    public static final String POS_TOP_CENTER = "TopCenter";
    public static final String POS_TOP_RIGHT = "TopRight";
    public static final String POS_MID_LEFT = "MidLeft";
    public static final String POS_MID_CENTER = "MidCenter";
    public static final String POS_MID_RIGHT = "MidRight";
    public static final String POS_BOT_LEFT = "BotLeft";
    public static final String POS_BOT_CENTER = "BotCenter";
    public static final String POS_BOT_RIGHT = "BotRight";
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    public static final String[][] PATTERN_HORIZONTAL = new String[][]{{"LeftCorner", "Center", "RightCorner"}};
    public static final String[][] PATTERN_VERTICAL = new String[][]{{"TopLeft"}, {"MidCenter"}, {"BotRight"}};
    public static final String[][] PATTERN_BOX_3x3 = new String[][]{{"TopLeft", "TopCenter", "TopRight"}, {"MidLeft", "MidCenter", "MidRight"}, {"BotLeft", "BotCenter", "BotRight"}};

    public static String getTileFile(String string, String string2) {
        String string3 = "Resources/industrial-zone/1 Tiles/Level1/2 Background_level_1";
        return string3 + "/" + string + "_" + string2 + ".png";
    }

    public static String[] getHorizontalPlatform(String string, int n) {
        if (n < 3) {
            AnimationAndSpriteLoader.logError("Platform width must be at least 3 tiles");
            return null;
        }
        String[] stringArray = new String[n];
        stringArray[0] = TilesetCompositionSystem.getTileFile(string, POS_LEFT_CORNER);
        for (int i = 1; i < n - 1; ++i) {
            stringArray[i] = TilesetCompositionSystem.getTileFile(string, POS_CENTER);
        }
        stringArray[n - 1] = TilesetCompositionSystem.getTileFile(string, POS_RIGHT_CORNER);
        return stringArray;
    }
}
