/*
 * Decompiled with CFR 0.152.
 */
package gui;

import gui.MenuInputHandler;
import gui.screens.Phase13MainMenuScreen;
import gui.screens.Phase14PauseMenuScreen;

public static interface MenuInputHandler.MenuActionCallback {
    public void onMainMenuAction(Phase13MainMenuScreen.MenuAction var1);

    public void onPauseMenuAction(Phase14PauseMenuScreen.PauseAction var1);

    public void onSettingsChanged(String var1, Object var2);

    public void onMenuStateChanged(MenuInputHandler.MenuState var1, MenuInputHandler.MenuState var2);
}
