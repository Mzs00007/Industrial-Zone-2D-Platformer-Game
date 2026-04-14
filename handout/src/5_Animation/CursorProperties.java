/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.CursorProperties {
    public static final String UI_TYPE = "cursor";
    public static final String DIRECTORY = "Resources/industrial-zone/gui/8 Cursors";
    public static final int CURSOR_VARIANTS = 4;
    public static final String CURSOR_DEFAULT = "01_GUI_Cursor_White_DefaultPointer.png";
    public static final String CURSOR_TARGETING = "02_GUI_Cursor_Blue_TargetingPointer.png";
    public static final String CURSOR_ATTACK = "03_GUI_Cursor_Red_AttackPointer.png";
    public static final String CURSOR_CONFIRM = "04_GUI_Cursor_Green_ConfirmPointer.png";
    public static final String[] CURSOR_FILES = new String[]{"01_GUI_Cursor_White_DefaultPointer.png", "02_GUI_Cursor_Blue_TargetingPointer.png", "03_GUI_Cursor_Red_AttackPointer.png", "04_GUI_Cursor_Green_ConfirmPointer.png"};

    public static final String getCursorForState(String string) {
        switch (string.toLowerCase()) {
            case "aiming": 
            case "targeting": {
                return CURSOR_TARGETING;
            }
            case "attack": 
            case "attacking": {
                return CURSOR_ATTACK;
            }
            case "confirm": 
            case "ready": {
                return CURSOR_CONFIRM;
            }
        }
        return CURSOR_DEFAULT;
    }
}
