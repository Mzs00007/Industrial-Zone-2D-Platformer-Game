/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.BarRenderer;
import controllers.DigitRenderer;
import controllers.GUIAssetManager;
import controllers.GUIComponent;
import controllers.GameState;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class HUDPanel
extends GUIComponent {
    private BufferedImage panelBackground;
    private BufferedImage panelDivider;
    private BarRenderer barRenderer;
    private DigitRenderer digitRenderer;
    private GUIAssetManager assetManager;
    private static final int PANEL_HEIGHT = 100;
    private static final int MARGIN = 20;
    private static final int SECTION_SPACING = 230;
    private static final int HEALTH_X = 20;
    private static final int ENERGY_X = 250;
    private static final int ARMOR_X = 480;
    private static final int AMMO_X = 730;
    private static final int BAR_Y_OFFSET = 20;
    private static final int STATUS_Y_OFFSET = 60;

    public HUDPanel(int n, int n2) {
        this.posX = 0;
        this.posY = n2 - 100;
        this.width = n;
        this.height = 100;
        this.barRenderer = new BarRenderer();
        this.digitRenderer = new DigitRenderer();
        this.assetManager = GUIAssetManager.getInstance();
    }

    @Override
    public void loadAssets() {
        try {
            this.panelBackground = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png");
            this.panelDivider = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/17_GUI_Frame_PanelWideRect_TealCyanAccentStripe_DividerBar.png");
            this.barRenderer = new BarRenderer();
            this.digitRenderer.loadAssets(this.assetManager);
            System.out.println("[HUDPanel] \u2705 Assets loaded successfully");
        }
        catch (Exception exception) {
            System.out.println("[HUDPanel] \u26a0\ufe0f Failed to load assets: " + exception.getMessage());
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
        if (this.panelBackground != null) {
            this.drawImageFit(graphics2D, this.panelBackground, this.posX, this.posY, this.width, this.height);
        }
        if (this.panelDivider != null) {
            this.drawImageAt(graphics2D, this.panelDivider, 200, this.posY + 15, 2, 70);
            this.drawImageAt(graphics2D, this.panelDivider, 430, this.posY + 15, 2, 70);
            this.drawImageAt(graphics2D, this.panelDivider, 660, this.posY + 15, 2, 70);
        }
    }

    private void renderBars(Graphics2D graphics2D, GameState gameState) {
        if (this.barRenderer != null) {
            this.barRenderer.renderHealthBar(graphics2D, 20, this.posY + 20, gameState.health);
        }
        if (this.barRenderer != null) {
            this.barRenderer.renderEnergyBar(graphics2D, 250, this.posY + 20, gameState.energy);
        }
    }

    private void renderAmmoCounter(Graphics2D graphics2D, GameState gameState) {
        this.digitRenderer.renderAmmo(graphics2D, gameState.ammo, gameState.ammoMax, 730, this.posY + 20);
    }

    private void renderStatusEffects(Graphics2D graphics2D, GameState gameState) {
    }

    public void updateWithGameState(Graphics2D graphics2D, GameState gameState) {
        this.renderBars(graphics2D, gameState);
        this.renderAmmoCounter(graphics2D, gameState);
        this.renderStatusEffects(graphics2D, gameState);
    }
}
