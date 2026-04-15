/*
 * Decompiled with CFR 0.152.
 */
package animation;
public enum TransporterState {
    IDLE("Waiting for player at platform"),
    WALK("Moving horizontally along path"),
    DROP("Descending to pick up player"),
    DEPLOY("Ascending after dropping player"),
    SPECIAL("Special effect/capsule activation");

    public final String description;

    private TransporterState(String string2) {
        this.description = string2;
    }
}
