/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.MenuInputHandler;
import controllers.Phase13MainMenuScreen;
import controllers.Phase14PauseMenuScreen;
public interface MenuActionCallback {
    public void onMainMenuAction(Phase13MainMenuScreen.MenuAction var1);

    public void onPauseMenuAction(Phase14PauseMenuScreen.PauseAction var1);

    public void onSettingsChanged(String var1, Object var2);

    public void onMenuStateChanged(MenuInputHandler.MenuState var1, MenuInputHandler.MenuState var2);
}
