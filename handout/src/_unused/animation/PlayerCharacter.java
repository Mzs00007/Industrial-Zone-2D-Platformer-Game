/*
 * Decompiled with CFR 0.152.
 */
package animation;
public enum PlayerCharacter {
    BIKER("Biker", 30),
    PUNK("Punk", 25),
    CYBORG("Cyborg", 28);

    public final String name;
    public final int baseHealth;

    private PlayerCharacter(String string2, int n2) {
        this.name = string2;
        this.baseHealth = n2;
    }
}
