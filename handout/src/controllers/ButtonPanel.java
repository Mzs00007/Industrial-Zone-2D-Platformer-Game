/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.GUIAssetManager;
import controllers.GUIComponent;
import controllers.GameState;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ButtonPanel
extends GUIComponent {
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 40;
    private static final int PADDING = 10;
    private List<GUIButton> buttons = new ArrayList<GUIButton>();
    private GUIAssetManager assetManager;
    private int screenWidth;
    private int screenHeight;
    private BufferedImage buttonNormalImage;
    private BufferedImage buttonHoverImage;
    private BufferedImage buttonPressedImage;
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean mousePressed = false;
    private boolean isPaused = false;

    public ButtonPanel(int n, int n2, GUIAssetManager gUIAssetManager) {
        this.screenWidth = n;
        this.screenHeight = n2;
        this.assetManager = gUIAssetManager;
        this.posX = 0;
        this.posY = 0;
        this.width = n;
        this.height = n2;
        this.isVisible = true;
        this.isEnabled = true;
    }

    @Override
    public void loadAssets() {
        try {
            this.buttonNormalImage = this.createButtonImage(80, 40, new Color(60, 120, 200));
            this.buttonHoverImage = this.createButtonImage(80, 40, new Color(80, 150, 220));
            this.buttonPressedImage = this.createButtonImage(80, 40, new Color(40, 90, 180));
            this.initializeButtons();
        }
        catch (Exception exception) {
            System.err.println("[ButtonPanel] Asset loading error: " + exception.getMessage());
        }
    }

    private void initializeButtons() {
        this.buttons.clear();
        GUIButton gUIButton = new GUIButton("pause", this.screenWidth - 80 - 10, 10, 80, 40, "PAUSE");
        gUIButton.normalImage = this.buttonNormalImage;
        gUIButton.hoverImage = this.buttonHoverImage;
        gUIButton.pressedImage = this.buttonPressedImage;
        gUIButton.onClickCallback = () -> this.togglePause();
        this.buttons.add(gUIButton);
        GUIButton gUIButton2 = new GUIButton("settings", this.screenWidth - 80 - 10, 60, 80, 40, "SETTINGS");
        gUIButton2.normalImage = this.buttonNormalImage;
        gUIButton2.hoverImage = this.buttonHoverImage;
        gUIButton2.pressedImage = this.buttonPressedImage;
        gUIButton2.onClickCallback = () -> System.out.println("[ButtonPanel] Settings clicked");
        this.buttons.add(gUIButton2);
        GUIButton gUIButton3 = new GUIButton("help", this.screenWidth - 80 - 10, 110, 80, 40, "HELP");
        gUIButton3.normalImage = this.buttonNormalImage;
        gUIButton3.hoverImage = this.buttonHoverImage;
        gUIButton3.pressedImage = this.buttonPressedImage;
        gUIButton3.onClickCallback = () -> System.out.println("[ButtonPanel] Help clicked");
        this.buttons.add(gUIButton3);
        GUIButton gUIButton4 = new GUIButton("weapon1", this.screenWidth - 80 - 10, this.screenHeight - 200, 80, 40, "WPN 1");
        gUIButton4.normalImage = this.buttonNormalImage;
        gUIButton4.hoverImage = this.buttonHoverImage;
        gUIButton4.pressedImage = this.buttonPressedImage;
        gUIButton4.onClickCallback = () -> System.out.println("[ButtonPanel] Weapon 1 selected");
        this.buttons.add(gUIButton4);
        GUIButton gUIButton5 = new GUIButton("weapon2", this.screenWidth - 80 - 10, this.screenHeight - 150, 80, 40, "WPN 2");
        gUIButton5.normalImage = this.buttonNormalImage;
        gUIButton5.hoverImage = this.buttonHoverImage;
        gUIButton5.pressedImage = this.buttonPressedImage;
        gUIButton5.onClickCallback = () -> System.out.println("[ButtonPanel] Weapon 2 selected");
        this.buttons.add(gUIButton5);
        GUIButton gUIButton6 = new GUIButton("weapon3", this.screenWidth - 80 - 10, this.screenHeight - 100, 80, 40, "WPN 3");
        gUIButton6.normalImage = this.buttonNormalImage;
        gUIButton6.hoverImage = this.buttonHoverImage;
        gUIButton6.pressedImage = this.buttonPressedImage;
        gUIButton6.onClickCallback = () -> System.out.println("[ButtonPanel] Weapon 3 selected");
        this.buttons.add(gUIButton6);
    }

    @Override
    public void update(long l, GameState gameState) {
        for (GUIButton gUIButton : this.buttons) {
            gUIButton.isHovered = gUIButton.containsPoint(this.mouseX, this.mouseY);
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        if (!this.isVisible) {
            return;
        }
        for (GUIButton gUIButton : this.buttons) {
            BufferedImage bufferedImage = gUIButton.normalImage;
            if (gUIButton.isPressed) {
                bufferedImage = gUIButton.pressedImage;
            } else if (gUIButton.isHovered) {
                bufferedImage = gUIButton.hoverImage;
            }
            this.drawImageAt(graphics2D, bufferedImage, gUIButton.x, gUIButton.y, gUIButton.width, gUIButton.height);
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(new Font("Arial", 1, 11));
            int n = gUIButton.x + (gUIButton.width - graphics2D.getFontMetrics().stringWidth(gUIButton.label)) / 2;
            int n2 = gUIButton.y + gUIButton.height / 2 + 5;
            graphics2D.drawString(gUIButton.label, n, n2);
        }
        if (this.isPaused) {
            this.renderPauseOverlay(graphics2D);
        }
    }

    private void renderPauseOverlay(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(0, 0, 0, 128));
        graphics2D.fillRect(0, 0, this.screenWidth, this.screenHeight);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 48));
        String string = "GAME PAUSED";
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n = (this.screenWidth - fontMetrics.stringWidth(string)) / 2;
        int n2 = (this.screenHeight - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
        graphics2D.drawString(string, n, n2);
    }

    public void updateMousePosition(int n, int n2) {
        this.mouseX = n;
        this.mouseY = n2;
    }

    public void handleMousePress(int n, int n2) {
        this.mousePressed = true;
        for (GUIButton gUIButton : this.buttons) {
            if (!gUIButton.containsPoint(n, n2)) continue;
            gUIButton.isPressed = true;
        }
    }

    public void handleMouseRelease(int n, int n2) {
        this.mousePressed = false;
        for (GUIButton gUIButton : this.buttons) {
            gUIButton.isPressed = false;
            if (!gUIButton.containsPoint(n, n2) || gUIButton.onClickCallback == null) continue;
            gUIButton.onClickCallback.run();
        }
    }

    private void togglePause() {
        this.isPaused = !this.isPaused;
        System.out.println("[ButtonPanel] Game " + (this.isPaused ? "PAUSED" : "RESUMED"));
    }

    public boolean isPaused() {
        return this.isPaused;
    }

    private BufferedImage createButtonImage(int n, int n2, Color color) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 1);
        int n3 = color.getRGB();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                bufferedImage.setRGB(i, j, n3);
            }
        }
        return bufferedImage;
    }

    private static class GUIButton {
        String id;
        int x;
        int y;
        int width;
        int height;
        String label;
        BufferedImage normalImage;
        BufferedImage hoverImage;
        BufferedImage pressedImage;
        boolean isHovered = false;
        boolean isPressed = false;
        Runnable onClickCallback;

        GUIButton(String string, int n, int n2, int n3, int n4, String string2) {
            this.id = string;
            this.x = n;
            this.y = n2;
            this.width = n3;
            this.height = n4;
            this.label = string2;
        }

        boolean containsPoint(int n, int n2) {
            return n >= this.x && n <= this.x + this.width && n2 >= this.y && n2 <= this.y + this.height;
        }
    }
}
