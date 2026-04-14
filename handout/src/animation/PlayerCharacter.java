/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static enum AnimationAndSpriteLoader.WeaponSystemCore.PlayerCharacter {
    BIKER("Biker", 30),
    PUNK("Punk", 25),
    CYBORG("Cyborg", 28);

    public final String name;
    public final int baseHealth;

    private AnimationAndSpriteLoader.WeaponSystemCore.PlayerCharacter(String string2, int n2) {
        this.name = string2;
        this.baseHealth = n2;
    }
}
