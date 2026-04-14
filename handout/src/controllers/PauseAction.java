/*
 * Decompiled with CFR 0.152.
 */
package controllers;

public static enum Phase14PauseMenuScreen.PauseAction {
    RESUME("ESC"),
    SETTINGS("S"),
    HELP("H"),
    SAVE_EXIT("Q");

    public String shortcut;

    private Phase14PauseMenuScreen.PauseAction(String string2) {
        this.shortcut = string2;
    }
}
