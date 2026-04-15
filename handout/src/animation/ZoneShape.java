/*
 * Decompiled with CFR 0.152.
 */
package animation;
public enum ZoneShape {
    CIRCLE("Circular interaction zone"),
    RECTANGLE("Rectangular interaction zone"),
    POLYGON("Complex polygon zone");

    public final String description;

    private ZoneShape(String string2) {
        this.description = string2;
    }
}
