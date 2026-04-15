/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
public class GUIAnimationRegistry {
    private static final Map<String, AnimationAndSpriteLoader.GUIAnimationPattern> GUI_ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.GUIAnimationPattern>();

    public static AnimationAndSpriteLoader.GUIAnimationPattern getPattern(String string) {
        return GUI_ANIMATIONS.get(string);
    }

    public static boolean hasPattern(String string) {
        return GUI_ANIMATIONS.containsKey(string);
    }

    public static Collection<String> listPatterns() {
        return GUI_ANIMATIONS.keySet();
    }

    public static Map<String, AnimationAndSpriteLoader.GUIAnimationPattern> getAllPatterns() {
        return new LinkedHashMap<String, AnimationAndSpriteLoader.GUIAnimationPattern>(GUI_ANIMATIONS);
    }

    public static void printRegistry() {
        System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        System.out.println("GUI ANIMATION REGISTRY - " + GUI_ANIMATIONS.size() + " patterns");
        System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        for (String string : GUI_ANIMATIONS.keySet()) {
            AnimationAndSpriteLoader.GUIAnimationPattern gUIAnimationPattern = GUI_ANIMATIONS.get(string);
            System.out.println("  \u2713 " + String.format("%-20s", string) + " | " + gUIAnimationPattern.frameCount + " frames @ " + gUIAnimationPattern.timingMs + "ms | " + (gUIAnimationPattern.looping ? "LOOP" : "ONCE"));
        }
        System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n");
    }

    static {
        GUI_ANIMATIONS.put("button_hover", new AnimationAndSpriteLoader.GUIAnimationPattern("Button_Hover", 4, 80, true, "Button hover state animation"));
        GUI_ANIMATIONS.put("button_press", new AnimationAndSpriteLoader.GUIAnimationPattern("Button_Press", 3, 60, false, "Button press feedback animation"));
        GUI_ANIMATIONS.put("button_active", new AnimationAndSpriteLoader.GUIAnimationPattern("Button_Active", 4, 100, true, "Button active state pulsing"));
        GUI_ANIMATIONS.put("fade_in", new AnimationAndSpriteLoader.GUIAnimationPattern("FadeIn", 20, 25, false, "Screen fade-in transition"));
        GUI_ANIMATIONS.put("fade_out", new AnimationAndSpriteLoader.GUIAnimationPattern("FadeOut", 20, 25, false, "Screen fade-out transition"));
        GUI_ANIMATIONS.put("slide_right", new AnimationAndSpriteLoader.GUIAnimationPattern("Slide_Right", 10, 40, false, "Slide in from left transition"));
        GUI_ANIMATIONS.put("slide_left", new AnimationAndSpriteLoader.GUIAnimationPattern("Slide_Left", 10, 40, false, "Slide out to left transition"));
        GUI_ANIMATIONS.put("menu_idle", new AnimationAndSpriteLoader.GUIAnimationPattern("Menu_Idle", 5, 150, true, "Menu idle breathing animation"));
        GUI_ANIMATIONS.put("menu_background", new AnimationAndSpriteLoader.GUIAnimationPattern("MenuBackground_Idle", 5, 150, true, "Menu background animation"));
        GUI_ANIMATIONS.put("card_hover", new AnimationAndSpriteLoader.GUIAnimationPattern("Card_Hover", 4, 100, true, "Card hover effect"));
        GUI_ANIMATIONS.put("card_select", new AnimationAndSpriteLoader.GUIAnimationPattern("Card_Select", 6, 80, false, "Card selection animation"));
        GUI_ANIMATIONS.put("logo_entrance", new AnimationAndSpriteLoader.GUIAnimationPattern("Logo_Entrance", 8, 80, false, "Logo entrance animation"));
        GUI_ANIMATIONS.put("logo_idle", new AnimationAndSpriteLoader.GUIAnimationPattern("Logo_Idle", 4, 150, true, "Logo idle pulsing"));
        GUI_ANIMATIONS.put("slider_active", new AnimationAndSpriteLoader.GUIAnimationPattern("Slider_Active", 3, 100, true, "Slider thumb active state"));
        GUI_ANIMATIONS.put("toggle_on", new AnimationAndSpriteLoader.GUIAnimationPattern("Toggle_On", 4, 80, false, "Toggle switch on animation"));
        GUI_ANIMATIONS.put("toggle_off", new AnimationAndSpriteLoader.GUIAnimationPattern("Toggle_Off", 4, 80, false, "Toggle switch off animation"));
        GUI_ANIMATIONS.put("icon_spin", new AnimationAndSpriteLoader.GUIAnimationPattern("Icon_Spin", 8, 60, true, "Spinning icon animation"));
        GUI_ANIMATIONS.put("icon_pulse", new AnimationAndSpriteLoader.GUIAnimationPattern("Icon_Pulse", 4, 150, true, "Icon pulsing animation"));
    }
}
