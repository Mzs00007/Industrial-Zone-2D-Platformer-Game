/*
 * Decompiled with CFR 0.152.
 */
package physics;

public static enum CharacterPhysicsProfile.CharacterType {
    BIKER("Biker"),
    CYBORG("Cyborg"),
    PUNK("Punk");

    public final String displayName;

    private CharacterPhysicsProfile.CharacterType(String string2) {
        this.displayName = string2;
    }
}
