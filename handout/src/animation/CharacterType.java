/*
 * Decompiled with CFR 0.152.
 */
package animation;
public enum CharacterType {
    CYBORG("Cyborg - Heavy built, stable"),
    PUNK("Punk - Slim, agile"),
    BIKER("Biker - Medium, balanced");

    public final String description;

    private CharacterType(String string2) {
        this.description = string2;
    }
}
