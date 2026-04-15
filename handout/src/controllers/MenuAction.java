/*
 * Decompiled with CFR 0.152.
 */
package controllers;
public enum MenuAction {
    NEW_GAME("Start new game"),
    CONTINUE("Continue saved game"),
    SETTINGS("Adjust game settings"),
    CREDITS("View game credits"),
    EXIT("Exit to desktop");

    public String tooltip;

    private MenuAction(String string2) {
        this.tooltip = string2;
    }
}
