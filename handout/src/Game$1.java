/*
 * Decompiled with CFR 0.152.
 */
import gui.MenuInputHandler;
import gui.screens.Phase13MainMenuScreen;
import gui.screens.Phase14PauseMenuScreen;

class Game.1
implements MenuInputHandler.MenuActionCallback {
    Game.1() {
    }

    @Override
    public void onMainMenuAction(Phase13MainMenuScreen.MenuAction menuAction) {
        Game.this.handleMainMenuAction(menuAction);
    }

    @Override
    public void onPauseMenuAction(Phase14PauseMenuScreen.PauseAction pauseAction) {
        Game.this.handlePauseMenuAction(pauseAction);
    }

    @Override
    public void onSettingsChanged(String string, Object object) {
        System.out.println("[Settings] " + string + " = " + String.valueOf(object));
    }

    @Override
    public void onMenuStateChanged(MenuInputHandler.MenuState menuState, MenuInputHandler.MenuState menuState2) {
        System.out.println("[MenuState] " + String.valueOf((Object)menuState) + " \u2192 " + String.valueOf((Object)menuState2));
    }
}
