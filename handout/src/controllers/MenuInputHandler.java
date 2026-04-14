/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.screens.Phase13MainMenuScreen;
import controllers.screens.Phase14PauseMenuScreen;
import controllers.screens.Phase15SettingsScreen;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuInputHandler
implements KeyListener {
    private Phase13MainMenuScreen mainMenuScreen;
    private Phase14PauseMenuScreen pauseMenuScreen;
    private Phase15SettingsScreen settingsScreen;
    private MenuState currentMenuState = MenuState.MAIN_MENU;
    private MenuActionCallback actionCallback;
    private boolean[] keyPressed = new boolean[256];

    public MenuInputHandler(Phase13MainMenuScreen phase13MainMenuScreen, Phase14PauseMenuScreen phase14PauseMenuScreen, Phase15SettingsScreen phase15SettingsScreen) {
        this.mainMenuScreen = phase13MainMenuScreen;
        this.pauseMenuScreen = phase14PauseMenuScreen;
        this.settingsScreen = phase15SettingsScreen;
    }

    public void setActionCallback(MenuActionCallback menuActionCallback) {
        this.actionCallback = menuActionCallback;
    }

    public MenuState getCurrentMenuState() {
        return this.currentMenuState;
    }

    public void setMenuState(MenuState menuState) {
        MenuState menuState2 = this.currentMenuState;
        this.currentMenuState = menuState;
        this.updateScreenVisibility();
        if (this.actionCallback != null) {
            this.actionCallback.onMenuStateChanged(menuState2, menuState);
        }
    }

    public boolean isKeyPressed(int n) {
        return n < 256 && this.keyPressed[n];
    }

    public void openMainMenu() {
        this.setMenuState(MenuState.MAIN_MENU);
        if (this.mainMenuScreen != null) {
            this.mainMenuScreen.setDisplayed(true);
        }
    }

    public void closeAllMenus() {
        this.setMenuState(MenuState.CLOSED);
        if (this.mainMenuScreen != null) {
            this.mainMenuScreen.setDisplayed(false);
        }
        if (this.pauseMenuScreen != null) {
            this.pauseMenuScreen.resumeGame();
        }
        if (this.settingsScreen != null) {
            this.settingsScreen.setDisplayed(false);
        }
    }

    public void togglePause() {
        if (this.pauseMenuScreen == null) {
            return;
        }
        if (this.pauseMenuScreen.isPaused()) {
            this.pauseMenuScreen.resumeGame();
            this.setMenuState(MenuState.CLOSED);
        } else {
            this.pauseMenuScreen.pauseGame();
            this.setMenuState(MenuState.PAUSE_MENU);
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int n = keyEvent.getKeyCode();
        if (n < 256) {
            this.keyPressed[n] = true;
        }
        switch (this.currentMenuState.ordinal()) {
            case 0: {
                this.handleMainMenuInput(keyEvent);
                break;
            }
            case 1: {
                this.handlePauseMenuInput(keyEvent);
                break;
            }
            case 2: {
                this.handleSettingsMenuInput(keyEvent);
                break;
            }
            case 3: {
                this.handleClosedMenuInput(keyEvent);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int n = keyEvent.getKeyCode();
        if (n < 256) {
            this.keyPressed[n] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    private void handleClosedMenuInput(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case 27: {
                this.togglePause();
                break;
            }
            case 77: {
                this.openMainMenu();
            }
        }
    }

    private void handleMainMenuInput(KeyEvent keyEvent) {
        if (this.mainMenuScreen == null) {
            return;
        }
        switch (keyEvent.getKeyCode()) {
            case 38: 
            case 87: {
                this.mainMenuScreen.selectPrevious();
                break;
            }
            case 40: 
            case 83: {
                this.mainMenuScreen.selectNext();
                break;
            }
            case 10: 
            case 32: {
                this.executeMainMenuAction(this.mainMenuScreen.getSelectedAction());
                break;
            }
            case 27: {
                this.closeAllMenus();
            }
        }
    }

    private void handlePauseMenuInput(KeyEvent keyEvent) {
        if (this.pauseMenuScreen == null) {
            return;
        }
        switch (keyEvent.getKeyCode()) {
            case 38: 
            case 87: {
                this.pauseMenuScreen.selectPrevious();
                break;
            }
            case 40: 
            case 83: {
                this.pauseMenuScreen.selectNext();
                break;
            }
            case 10: 
            case 32: {
                this.executePauseMenuAction(this.pauseMenuScreen.getSelectedAction());
                break;
            }
            case 27: {
                this.pauseMenuScreen.resumeGame();
                this.setMenuState(MenuState.CLOSED);
                break;
            }
            case 9: {
                if (this.settingsScreen == null) break;
                this.settingsScreen.setDisplayed(true);
                this.setMenuState(MenuState.SETTINGS_MENU);
            }
        }
    }

    private void handleSettingsMenuInput(KeyEvent keyEvent) {
        if (this.settingsScreen == null) {
            return;
        }
        switch (keyEvent.getKeyCode()) {
            case 37: 
            case 65: {
                this.settingsScreen.adjustCurrentSetting(-1);
                break;
            }
            case 39: 
            case 68: {
                this.settingsScreen.adjustCurrentSetting(1);
                break;
            }
            case 9: {
                if (keyEvent.isShiftDown()) {
                    this.settingsScreen.previousTab();
                    break;
                }
                this.settingsScreen.nextTab();
                break;
            }
            case 38: 
            case 87: {
                this.settingsScreen.selectPreviousSetting();
                break;
            }
            case 40: 
            case 83: {
                this.settingsScreen.selectNextSetting();
                break;
            }
            case 10: 
            case 32: {
                this.settingsScreen.confirmCurrentSetting();
                break;
            }
            case 27: {
                this.settingsScreen.saveSettings();
                this.settingsScreen.setDisplayed(false);
                this.setMenuState(MenuState.PAUSE_MENU);
            }
        }
    }

    private void executeMainMenuAction(Phase13MainMenuScreen.MenuAction menuAction) {
        if (menuAction == null || this.actionCallback == null) {
            return;
        }
        switch (menuAction) {
            case NEW_GAME: {
                System.out.println("[MenuInput] NEW_GAME selected - starting new game");
                this.actionCallback.onMainMenuAction(menuAction);
                break;
            }
            case CONTINUE: {
                System.out.println("[MenuInput] CONTINUE selected - loading saved game");
                this.actionCallback.onMainMenuAction(menuAction);
                break;
            }
            case SETTINGS: {
                System.out.println("[MenuInput] SETTINGS selected - opening settings");
                if (this.settingsScreen != null) {
                    this.settingsScreen.setDisplayed(true);
                    this.setMenuState(MenuState.SETTINGS_MENU);
                }
                this.actionCallback.onMainMenuAction(menuAction);
                break;
            }
            case CREDITS: {
                System.out.println("[MenuInput] CREDITS selected - showing credits");
                this.actionCallback.onMainMenuAction(menuAction);
                break;
            }
            case EXIT: {
                System.out.println("[MenuInput] EXIT selected - exiting game");
                this.actionCallback.onMainMenuAction(menuAction);
            }
        }
    }

    private void executePauseMenuAction(Phase14PauseMenuScreen.PauseAction pauseAction) {
        if (pauseAction == null || this.actionCallback == null) {
            return;
        }
        switch (pauseAction) {
            case RESUME: {
                System.out.println("[MenuInput] RESUME selected - resuming");
                this.pauseMenuScreen.resumeGame();
                this.setMenuState(MenuState.CLOSED);
                this.actionCallback.onPauseMenuAction(pauseAction);
                break;
            }
            case SETTINGS: {
                System.out.println("[MenuInput] SETTINGS selected from pause - opening settings");
                if (this.settingsScreen != null) {
                    this.settingsScreen.setDisplayed(true);
                    this.setMenuState(MenuState.SETTINGS_MENU);
                }
                this.actionCallback.onPauseMenuAction(pauseAction);
                break;
            }
            case HELP: {
                System.out.println("[MenuInput] HELP selected - showing help");
                this.actionCallback.onPauseMenuAction(pauseAction);
                break;
            }
            case SAVE_EXIT: {
                System.out.println("[MenuInput] SAVE_EXIT selected - saving and exiting");
                this.actionCallback.onPauseMenuAction(pauseAction);
            }
        }
    }

    private void updateScreenVisibility() {
        if (this.mainMenuScreen != null) {
            this.mainMenuScreen.setDisplayed(this.currentMenuState == MenuState.MAIN_MENU);
        }
        if (this.pauseMenuScreen != null && this.currentMenuState == MenuState.CLOSED) {
            this.pauseMenuScreen.resumeGame();
        }
        if (this.settingsScreen != null) {
            this.settingsScreen.setDisplayed(this.currentMenuState == MenuState.SETTINGS_MENU);
        }
    }

    public void resetKeyStates() {
        for (int i = 0; i < this.keyPressed.length; ++i) {
            this.keyPressed[i] = false;
        }
    }

    public String getDebugInfo() {
        return String.format("[MenuInput] State: %s | Keys Pressed: %d", new Object[]{this.currentMenuState, this.countPressedKeys()});
    }

    private int countPressedKeys() {
        int n = 0;
        for (boolean bl : this.keyPressed) {
            if (!bl) continue;
            ++n;
        }
        return n;
    }

    public static enum MenuState {
        MAIN_MENU,
        PAUSE_MENU,
        SETTINGS_MENU,
        CLOSED;

    }

    public static interface MenuActionCallback {
        public void onMainMenuAction(Phase13MainMenuScreen.MenuAction var1);

        public void onPauseMenuAction(Phase14PauseMenuScreen.PauseAction var1);

        public void onSettingsChanged(String var1, Object var2);

        public void onMenuStateChanged(MenuState var1, MenuState var2);
    }
}
