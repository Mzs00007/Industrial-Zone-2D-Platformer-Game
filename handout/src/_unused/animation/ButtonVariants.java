/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
public class ButtonVariants {
    public static final String BASE_DIRECTORY = "Resources/industrial-zone/gui/6 Buttons";
    public static final int TOTAL_BUTTON_VARIANTS = 10;
    public static final int TYPICAL_FRAMES_PER_BUTTON = 4;

    public static AnimationAndSpriteLoader.VerticalSpritesheetLoader loadButtonVariant(String string, String string2, int n, int n2, int n3) {
        return new AnimationAndSpriteLoader.VerticalSpritesheetLoader(string, AnimationAndSpriteLoader.GUI_BUTTONS + string2, n, n2, n3);
    }
public final class RedCancelButtonVariant {
        public static final String VARIANT_NAME = "Cancel (Red)";
        public static final String SPRITESHEET = "10_GUI_Button_RedCancel_4StatesVertical_CancelColor.png";
        public static final int NORMAL_STATE = 0;
        public static final int HOVER_STATE = 1;
        public static final int PRESSED_STATE = 2;
        public static final int DISABLED_STATE = 3;
        public static final int FRAME_WIDTH = 64;
        public static final int FRAME_HEIGHT = 24;
        public static final int TRANSITION_TIMING_MS = 80;
    }
public final class GreenConfirmButtonVariant {
        public static final String VARIANT_NAME = "Confirm (Green)";
        public static final String SPRITESHEET = "09_GUI_Button_GreenConfirm_4StatesVertical_SuccessColor.png";
        public static final int NORMAL_STATE = 0;
        public static final int HOVER_STATE = 1;
        public static final int PRESSED_STATE = 2;
        public static final int DISABLED_STATE = 3;
        public static final int FRAME_WIDTH = 64;
        public static final int FRAME_HEIGHT = 24;
        public static final int TRANSITION_TIMING_MS = 80;
    }
public final class CyanLargeButtonVariant {
        public static final String VARIANT_NAME = "Large Cyan";
        public static final String SPRITESHEET = "08_GUI_Button_CyanLarge_4StatesVertical_FullWidthButton.png";
        public static final int NORMAL_STATE = 0;
        public static final int HOVER_STATE = 1;
        public static final int PRESSED_STATE = 2;
        public static final int DISABLED_STATE = 3;
        public static final int FRAME_WIDTH = 128;
        public static final int FRAME_HEIGHT = 32;
        public static final int TRANSITION_TIMING_MS = 90;
    }
public final class OrangeWarningButtonVariant {
        public static final String VARIANT_NAME = "Warning/Danger";
        public static final String SPRITESHEET = "07_GUI_Button_OrangeWarning_4StatesVertical_AlertColor.png";
        public static final int NORMAL_STATE = 0;
        public static final int HOVER_STATE = 1;
        public static final int PRESSED_STATE = 2;
        public static final int DISABLED_STATE = 3;
        public static final int FRAME_WIDTH = 64;
        public static final int FRAME_HEIGHT = 24;
        public static final int TRANSITION_TIMING_MS = 80;
    }
public final class GlassButtonVariant {
        public static final String VARIANT_NAME = "Glass Panel";
        public static final String SPRITESHEET = "06_GUI_Button_Glass_4StatesVertical_TranslucentReflective.png";
        public static final int NORMAL_STATE = 0;
        public static final int HOVER_STATE = 1;
        public static final int PRESSED_STATE = 2;
        public static final int DISABLED_STATE = 3;
        public static final int FRAME_WIDTH = 64;
        public static final int FRAME_HEIGHT = 24;
        public static final int TRANSITION_TIMING_MS = 120;
    }
public final class PressurePlateButtonVariant {
        public static final String VARIANT_NAME = "Pressure Plate";
        public static final String SPRITESHEET = "05_GUI_Button_PressurePlate_4StatesVertical_PhysicalDepression.png";
        public static final int NORMAL_STATE = 0;
        public static final int HOVER_STATE = 1;
        public static final int PRESSED_STATE = 2;
        public static final int DISABLED_STATE = 3;
        public static final int FRAME_WIDTH = 64;
        public static final int FRAME_HEIGHT = 24;
        public static final int TRANSITION_TIMING_MS = 100;
    }
public final class CyanAccentButtonVariant {
        public static final String VARIANT_NAME = "Cyan Accent";
        public static final String SPRITESHEET = "04_GUI_Button_CyanAccent_4StatesVertical_BlueHighlight.png";
        public static final int NORMAL_STATE = 0;
        public static final int HOVER_STATE = 1;
        public static final int PRESSED_STATE = 2;
        public static final int DISABLED_STATE = 3;
        public static final int FRAME_WIDTH = 64;
        public static final int FRAME_HEIGHT = 24;
        public static final int TRANSITION_TIMING_MS = 80;
    }
public final class MetalButtonVariant {
        public static final String VARIANT_NAME = "Industrial Metal";
        public static final String SPRITESHEET = "03_GUI_Button_Metal_4StatesVertical_RivetedSteelLook.png";
        public static final int NORMAL_STATE = 0;
        public static final int HOVER_STATE = 1;
        public static final int PRESSED_STATE = 2;
        public static final int DISABLED_STATE = 3;
        public static final int FRAME_WIDTH = 64;
        public static final int FRAME_HEIGHT = 24;
        public static final int TRANSITION_TIMING_MS = 90;
    }
public final class HoloButtonVariant {
        public static final String VARIANT_NAME = "Holographic";
        public static final String SPRITESHEET = "02_GUI_Button_Holographic_4StatesVertical_TechGlowEffect.png";
        public static final int NORMAL_STATE = 0;
        public static final int HOVER_STATE = 1;
        public static final int PRESSED_STATE = 2;
        public static final int DISABLED_STATE = 3;
        public static final int FRAME_WIDTH = 64;
        public static final int FRAME_HEIGHT = 24;
        public static final int TRANSITION_TIMING_MS = 100;
    }
public final class StandardButtonVariant {
        public static final String VARIANT_NAME = "Standard Blue";
        public static final String SPRITESHEET = "01_GUI_Button_Standard_4StatesVertical_NormalHoverPressDisabled.png";
        public static final int NORMAL_STATE = 0;
        public static final int HOVER_STATE = 1;
        public static final int PRESSED_STATE = 2;
        public static final int DISABLED_STATE = 3;
        public static final int FRAME_WIDTH = 64;
        public static final int FRAME_HEIGHT = 24;
        public static final int TRANSITION_TIMING_MS = 80;
    }
}
