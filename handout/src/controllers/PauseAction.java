/*
 * Decompiled with CFR 0.152.
 */
package controllers;
public enum PauseAction {
    RESUME("ESC"),
    SETTINGS("S"),
    HELP("H"),
    SAVE_EXIT("Q");

    public String shortcut;

    private PauseAction(String string2) {
        this.shortcut = string2;
    }
}
