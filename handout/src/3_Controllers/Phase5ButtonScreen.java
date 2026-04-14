/*
 * Decompiled with CFR 0.152.
 */
package gui.screens;

import core.GameState;
import gui.screens.AssetDrivenScreen;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Phase5ButtonScreen
extends AssetDrivenScreen {
    private BufferedImage startButtonNormal;
    private BufferedImage startButtonHover;
    private BufferedImage startButtonPressed;
    private BufferedImage pauseButtonNormal;
    private BufferedImage pauseButtonHover;
    private BufferedImage pauseButtonPressed;
    private BufferedImage settingsButtonNormal;
    private BufferedImage settingsButtonHover;
    private BufferedImage settingsButtonPressed;
    private List<Button> buttons = new ArrayList<Button>();
    private Button hoveredButton = null;
    private Button pressedButton = null;
    private static final String BUTTONS_DIRECTORY = "Resources/industrial-zone/gui/6 Buttons";
    private Runnable onStartClick = null;
    private Runnable onPauseClick = null;
    private Runnable onSettingsClick = null;

    public Phase5ButtonScreen() {
        super(GameState.PLAYING);
        this.loadAssetsOnce();
        this.initializeButtons();
    }

    private void loadAssetsOnce() {
        this.startButtonNormal = this.loadImage("Resources/industrial-zone/gui/6 Buttons/01_GUI_Button_Start_Normal_Enabled.png");
        this.startButtonHover = this.loadImage("Resources/industrial-zone/gui/6 Buttons/02_GUI_Button_Start_Hover_Highlight.png");
        this.startButtonPressed = this.loadImage("Resources/industrial-zone/gui/6 Buttons/03_GUI_Button_Start_Pressed_Active.png");
        this.pauseButtonNormal = this.loadImage("Resources/industrial-zone/gui/6 Buttons/04_GUI_Button_Pause_Normal_Enabled.png");
        this.pauseButtonHover = this.loadImage("Resources/industrial-zone/gui/6 Buttons/05_GUI_Button_Pause_Hover_Highlight.png");
        this.pauseButtonPressed = this.loadImage("Resources/industrial-zone/gui/6 Buttons/06_GUI_Button_Pause_Pressed_Active.png");
        this.settingsButtonNormal = this.loadImage("Resources/industrial-zone/gui/6 Buttons/07_GUI_Button_Settings_Normal_Enabled.png");
        this.settingsButtonHover = this.loadImage("Resources/industrial-zone/gui/6 Buttons/08_GUI_Button_Settings_Hover_Highlight.png");
        this.settingsButtonPressed = this.loadImage("Resources/industrial-zone/gui/6 Buttons/09_GUI_Button_Settings_Pressed_Active.png");
        System.out.println("[Phase5ButtonScreen] \u2713 Button sprites loaded");
    }

    private void initializeButtons() {
        Button button;
        if (this.startButtonNormal != null) {
            button = new Button("start", 100, 300, this.startButtonNormal.getWidth(), this.startButtonNormal.getHeight());
            this.buttons.add(button);
        }
        if (this.pauseButtonNormal != null) {
            button = new Button("pause", 300, 300, this.pauseButtonNormal.getWidth(), this.pauseButtonNormal.getHeight());
            this.buttons.add(button);
        }
        if (this.settingsButtonNormal != null) {
            button = new Button("settings", 500, 300, this.settingsButtonNormal.getWidth(), this.settingsButtonNormal.getHeight());
            this.buttons.add(button);
        }
    }

    public void setStartClickCallback(Runnable runnable) {
        this.onStartClick = runnable;
    }

    public void setPauseClickCallback(Runnable runnable) {
        this.onPauseClick = runnable;
    }

    public void setSettingsClickCallback(Runnable runnable) {
        this.onSettingsClick = runnable;
    }

    public void handleMouseMoved(MouseEvent mouseEvent) {
        this.hoveredButton = null;
        for (Button button : this.buttons) {
            if (!button.bounds.contains(mouseEvent.getPoint())) continue;
            this.hoveredButton = button;
            break;
        }
    }

    public void handleMousePressed(MouseEvent mouseEvent) {
        for (Button button : this.buttons) {
            if (!button.bounds.contains(mouseEvent.getPoint())) continue;
            this.pressedButton = button;
            break;
        }
    }

    public void handleMouseReleased(MouseEvent mouseEvent) {
        if (this.pressedButton != null && this.pressedButton.bounds.contains(mouseEvent.getPoint())) {
            this.triggerButtonAction(this.pressedButton);
        }
        this.pressedButton = null;
    }

    private void triggerButtonAction(Button button) {
        switch (button.id) {
            case "start": {
                if (this.onStartClick != null) {
                    this.onStartClick.run();
                }
                System.out.println("[Phase5ButtonScreen] Start button clicked");
                break;
            }
            case "pause": {
                if (this.onPauseClick != null) {
                    this.onPauseClick.run();
                }
                System.out.println("[Phase5ButtonScreen] Pause button clicked");
                break;
            }
            case "settings": {
                if (this.onSettingsClick != null) {
                    this.onSettingsClick.run();
                }
                System.out.println("[Phase5ButtonScreen] Settings button clicked");
            }
        }
    }

    @Override
    public void update(float f) {
    }

    @Override
    public void renderBitmap(Graphics2D graphics2D, int n, int n2) {
        BufferedImage bufferedImage;
        Button button;
        if (this.startButtonNormal != null) {
            button = this.findButtonById("start");
            bufferedImage = this.startButtonNormal;
            if (this.pressedButton == button && this.startButtonPressed != null) {
                bufferedImage = this.startButtonPressed;
            } else if (this.hoveredButton == button && this.startButtonHover != null) {
                bufferedImage = this.startButtonHover;
            }
            graphics2D.drawImage(bufferedImage, (int)button.bounds.getX(), (int)button.bounds.getY(), (int)(button.bounds.getX() + button.bounds.getWidth()), (int)(button.bounds.getY() + button.bounds.getHeight()), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        }
        if (this.pauseButtonNormal != null) {
            button = this.findButtonById("pause");
            bufferedImage = this.pauseButtonNormal;
            if (this.pressedButton == button && this.pauseButtonPressed != null) {
                bufferedImage = this.pauseButtonPressed;
            } else if (this.hoveredButton == button && this.pauseButtonHover != null) {
                bufferedImage = this.pauseButtonHover;
            }
            graphics2D.drawImage(bufferedImage, (int)button.bounds.getX(), (int)button.bounds.getY(), (int)(button.bounds.getX() + button.bounds.getWidth()), (int)(button.bounds.getY() + button.bounds.getHeight()), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        }
        if (this.settingsButtonNormal != null) {
            button = this.findButtonById("settings");
            bufferedImage = this.settingsButtonNormal;
            if (this.pressedButton == button && this.settingsButtonPressed != null) {
                bufferedImage = this.settingsButtonPressed;
            } else if (this.hoveredButton == button && this.settingsButtonHover != null) {
                bufferedImage = this.settingsButtonHover;
            }
            graphics2D.drawImage(bufferedImage, (int)button.bounds.getX(), (int)button.bounds.getY(), (int)(button.bounds.getX() + button.bounds.getWidth()), (int)(button.bounds.getY() + button.bounds.getHeight()), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        }
    }

    private Button findButtonById(String string) {
        for (Button button : this.buttons) {
            if (!button.id.equals(string)) continue;
            return button;
        }
        return null;
    }

    private static class Button {
        String id;
        Rectangle bounds;

        Button(String string, int n, int n2, int n3, int n4) {
            this.id = string;
            this.bounds = new Rectangle(n, n2, n3, n4);
        }
    }
}
