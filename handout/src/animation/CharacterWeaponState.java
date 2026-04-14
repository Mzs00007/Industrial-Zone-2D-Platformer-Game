/*
 * Decompiled with CFR 0.152.
 */
package animation;

import java.util.HashMap;
import java.util.Map;

public static class AnimationAndSpriteLoader.CharacterWeaponState {
    public static final String SYSTEM_TYPE = "character_weapon_state";
    public static final String CHARACTER_BIKER = "biker";
    public static final String CHARACTER_PUNK = "punk";
    public static final String CHARACTER_CYBORG = "cyborg";
    public static final String[] AVAILABLE_CHARACTERS = new String[]{"biker", "punk", "cyborg"};

    public static boolean isValidCharacter(String string) {
        for (String string2 : AVAILABLE_CHARACTERS) {
            if (!string2.equals(string)) continue;
            return true;
        }
        return false;
    }

    public static class EquippedWeapons {
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
}
