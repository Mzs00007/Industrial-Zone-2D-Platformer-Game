/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
public class UniversalWeaponPickup {
    public static final String SYSTEM_TYPE = "universal_weapon_pickup";

    public static void pickupWeapon(String string, String string2) {
        if (!AnimationAndSpriteLoader.CharacterWeaponState.isValidCharacter(string)) {
            System.out.println("ERROR: Invalid character ID: " + string);
            return;
        }
        AnimationAndSpriteLoader.CharacterWeaponState.EquippedWeapons.equipWeapon(string, string2);
        System.out.println(string + " equipped: " + string2);
    }

    public static void dropWeapon(String string) {
        AnimationAndSpriteLoader.CharacterWeaponState.EquippedWeapons.dropWeapon(string);
        System.out.println(string + " dropped weapon");
    }

    public static String[] getAvailableGunsOnLevel() {
        return AnimationAndSpriteLoader.GunProperties.ALL_GUN_SPRITES;
    }
}
