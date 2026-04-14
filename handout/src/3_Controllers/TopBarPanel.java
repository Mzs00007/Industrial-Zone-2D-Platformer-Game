/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.DigitRenderer;
import controllers.GUIAssetManager;
import controllers.GUIComponent;
import controllers.GameState;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TopBarPanel
extends GUIComponent {
    private BufferedImage frameTopEdge;
    private BufferedImage fillBackground;
    private DigitRenderer digitRenderer;
    private GUIAssetManager assetManager;
    private static final int PANEL_HEIGHT = 50;
    private static final int PANEL_PADDING = 8;

    public TopBarPanel(int n) {
        this.posX = 0;
        this.posY = 0;
        this.width = n;
        this.height = 50;
        this.digitRenderer = new DigitRenderer();
        this.assetManager = GUIAssetManager.getInstance();
    }

    @Override
    public void loadAssets() {
        try {
            this.frameTopEdge = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png");
            this.fillBackground = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/38_GUI_Frame_FillSolidNavy_WideRectNoBorder_WindowFill.png");
            this.digitRenderer.loadAssets(this.assetManager);
            System.out.println("[TopBarPanel] \u2705 Assets loaded successfully");
        }
        catch (Exception exception) {
            System.out.println("[TopBarPanel] \u26a0\ufe0f Failed to load assets: " + exception.getMessage());
        }
    }

    @Override
    public void update(long l, GameState gameState) {
    }

    @Override
    public void render(Graphics2D graphics2D) {
        if (!this.isVisible) {
            return;
        }
        if (this.fillBackground != null) {
            this.drawImageFit(graphics2D, this.fillBackground, this.posX, this.posY, this.width, this.height);
        }
        if (this.frameTopEdge != null) {
            this.drawImageFit(graphics2D, this.frameTopEdge, this.posX, this.posY, this.width, 3);
        }
        int n = this.width - 100;
        int n2 = this.posY + 10;
    }

    public void setLevelInfo(String string, int n, int n2) {
    }

    public void setTimeRemaining(int n) {
    }
}
