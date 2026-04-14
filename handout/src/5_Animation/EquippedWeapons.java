/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.HashMap;
import java.util.Map;

public static class AnimationAndSpriteLoader.CharacterWeaponState.EquippedWeapons {
    private static Map<String, String> weaponMap = new HashMap<String, String>();

    public static void equipWeapon(String string, String string2) {
        if (AnimationAndSpriteLoader.CharacterWeaponState.isValidCharacter(string)) {
            weaponMap.put(string, string2);
        }
    }

    public static String getEquippedWeapon(String string) {
        String string2 = weaponMap.getOrDefault(string, null);
        return string2 != null ? string2 : "01_Weapon_Gun_Pistol_TypeA_VariantDark_StaticSprite.png";
    }

    public static boolean isArmed(String string) {
        return weaponMap.containsKey(string) && weaponMap.get(string) != null;
    }

    public static void dropWeapon(String string) {
        weaponMap.remove(string);
    }
}
