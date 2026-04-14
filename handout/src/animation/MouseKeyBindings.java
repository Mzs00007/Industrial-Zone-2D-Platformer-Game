/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.MouseKeyBindings {
    public static final String INPUT_TYPE = "mouse_key";
    public static final String DIRECTORY = "Resources/industrial-zone/Mouse_keys";
    public static final int TOTAL_MOUSE_BINDINGS = 18;
    public static final String[] MOUSE_ACTION_BINDINGS = new String[]{"Mouse_LeftClick_Blue_Level1Tutorial_Display_PrimaryAction.png", "Mouse_LeftClick_Red_Level2Combat_Display_PrimaryAttack.png", "Mouse_MiddleClick_Blue_Level1Tutorial_Display_SecondaryAction.png", "Mouse_MiddleClick_Red_Level2Combat_Display_SecondaryAttack.png", "Mouse_Move_FourDirections_Camer_OrCrosshairMove_Tutorial.png", "Mouse_MoveDiagonal_FourDiagonals_AimOrCrosshairMove_Tutorial.png", "Mouse_MoveDown_ArrowDown_ScrollDownOrAimDown_Tutorial.png", "Mouse_MoveLeft_ArrowLeft_ScrollLeftOrAimLeft_Tutorial.png", "Mouse_MoveLeftRight_BothArrows_HorizontalScrollOrAim_Tutorial.png", "Mouse_MoveRight_ArrowRight_ScrollRightOrAimRight_Tutorial.png", "Mouse_MoveUp_ArrowUp_ScrollUpOrAimUp_Tutorial.png", "Mouse_MoveUpDown_BothArrows_VerticalScrollOrAim_Tutorial.png", "Mouse_Neutral_NoHighlight_DefaultState_Display.png", "Mouse_RightClick_Blue_Level1Tutorial_Display_AimOrContext.png", "Mouse_RightClick_Red_Level2Combat_Display_AimOrContext.png", "Mouse_ScrollDown_Blue_Level1Tutorial_Display_ZoomOutOrPrevWeapon.png", "Mouse_ScrollUp_Blue_Level1Tutorial_Display_ZoomInOrNextWeapon.png", "Mouse_ScrollWheel_Red_Level2Combat_Display_ZoomOrWeaponSwap.png"};

    public static final String getMouseBinding(String string, boolean bl) {
        String string2 = string.toUpperCase();
        for (String string3 : MOUSE_ACTION_BINDINGS) {
            if (!string3.contains(string2)) continue;
            if (bl && string3.contains("Red_Level2")) {
                return string3;
            }
            if (bl || !string3.contains("Blue_Level1")) continue;
            return string3;
        }
        return MOUSE_ACTION_BINDINGS[0];
    }
}
