/*
 * Decompiled with CFR 0.152.
 */
package gui.screens;

public static enum Phase13MainMenuScreen.MenuAction {
    NEW_GAME("Start new game"),
    CONTINUE("Continue saved game"),
    SETTINGS("Adjust game settings"),
    CREDITS("View game credits"),
    EXIT("Exit to desktop");

    public String tooltip;

    private Phase13MainMenuScreen.MenuAction(String string2) {
        this.tooltip = string2;
    }
}
