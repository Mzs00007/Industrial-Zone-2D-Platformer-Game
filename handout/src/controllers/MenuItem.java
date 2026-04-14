/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.screens.Phase13MainMenuScreen;

public static class Phase13MainMenuScreen.MenuItem {
    public String label;
    public String description;
    public Phase13MainMenuScreen.MenuAction action;
    public boolean enabled;

    public Phase13MainMenuScreen.MenuItem(String string, String string2, Phase13MainMenuScreen.MenuAction menuAction) {
        this.label = string;
        this.description = string2;
        this.action = menuAction;
        this.enabled = true;
    }
}
