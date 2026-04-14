/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.screens.Phase14PauseMenuScreen;

public static class Phase14PauseMenuScreen.PauseMenuItem {
    public String label;
    public String shortcut;
    public Phase14PauseMenuScreen.PauseAction action;

    public Phase14PauseMenuScreen.PauseMenuItem(String string, String string2, Phase14PauseMenuScreen.PauseAction pauseAction) {
        this.label = string;
        this.shortcut = string2;
        this.action = pauseAction;
    }
}
