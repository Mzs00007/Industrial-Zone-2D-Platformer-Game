/*
 * Decompiled with CFR 0.152.
 */
package gui;

import gui.BarRenderer;
import gui.GUIAssetManager;
import gui.GUIComponent;
import gui.GameState;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class LeftSidebar
extends GUIComponent {
    private static final int SIDEBAR_WIDTH = 200;
    private static final int TAB_HEIGHT = 40;
    private static final int PADDING = 8;
    private Tab activeTab = Tab.INVENTORY;
    private int contentStartY;
    private BufferedImage sidebarBackground;
    private BufferedImage tabButtonActive;
    private BufferedImage tabButtonInactive;
    private BufferedImage inventorySlotEmpty;
    private BufferedImage inventorySlotFilled;
    private BufferedImage skillIconPlaceholder;
    private BufferedImage characterStatBackground;
    private BufferedImage mapBackground;
    private BarRenderer barRenderer;
    private GUIAssetManager assetManager;
    private BufferedImage[] inventoryIcons = new BufferedImage[4];

    public LeftSidebar(int n, int n2, GUIAssetManager gUIAssetManager) {
        this.posX = 0;
        this.posY = 40;
        this.width = 200;
        this.height = n2 - 40;
        this.isVisible = true;
        this.isEnabled = true;
        this.assetManager = gUIAssetManager;
        this.barRenderer = new BarRenderer(true);
        this.contentStartY = 40;
    }

    @Override
    public void loadAssets() {
        try {
            this.sidebarBackground = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/corner_TL.png");
            if (this.sidebarBackground == null) {
                this.sidebarBackground = this.createSolidImage(200, this.height, new Color(30, 30, 40));
            }
            this.tabButtonActive = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/divider_vertical.png");
            if (this.tabButtonActive == null) {
                this.tabButtonActive = this.createSolidImage(50, 40, new Color(60, 120, 200));
            }
            this.tabButtonInactive = this.createSolidImage(50, 40, new Color(40, 40, 50));
            this.inventorySlotEmpty = this.assetManager.getImage("Resources/industrial-zone/gui/1 Frames/corner_TL.png");
            if (this.inventorySlotEmpty == null) {
                this.inventorySlotEmpty = this.createSolidImage(40, 40, new Color(50, 50, 60));
            }
            this.inventorySlotFilled = this.createSolidImage(40, 40, new Color(80, 120, 180));
            this.skillIconPlaceholder = this.assetManager.getImage("Resources/industrial-zone/gui/icons/action_examine.png");
            if (this.skillIconPlaceholder == null) {
                this.skillIconPlaceholder = this.createSolidImage(32, 32, new Color(100, 100, 120));
            }
            this.characterStatBackground = this.createSolidImage(184, 80, new Color(40, 50, 60));
            this.mapBackground = this.createSolidImage(184, 100, new Color(20, 40, 50));
            for (int i = 0; i < 4; ++i) {
                this.inventoryIcons[i] = this.skillIconPlaceholder;
            }
        }
        catch (Exception exception) {
            System.err.println("[LeftSidebar] Asset loading error: " + exception.getMessage());
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
        this.drawImageAt(graphics2D, this.sidebarBackground, this.posX, this.posY, 200, this.height);
        this.renderTabs(graphics2D);
        switch (this.activeTab.ordinal()) {
            case 0: {
                this.renderInventoryTab(graphics2D);
                break;
            }
            case 1: {
                this.renderCharacterTab(graphics2D);
                break;
            }
            case 2: {
                this.renderSkillsTab(graphics2D);
                break;
            }
            case 3: {
                this.renderMapTab(graphics2D);
            }
        }
    }

    private void renderTabs(Graphics2D graphics2D) {
        int n = 50;
        String[] stringArray = new String[]{"INV", "CHAR", "SKILL", "MAP"};
        Tab[] tabArray = new Tab[]{Tab.INVENTORY, Tab.CHARACTER, Tab.SKILLS, Tab.MAP};
        for (int i = 0; i < 4; ++i) {
            boolean bl = tabArray[i] == this.activeTab;
            BufferedImage bufferedImage = bl ? this.tabButtonActive : this.tabButtonInactive;
            int n2 = this.posX + i * n;
            this.drawImageAt(graphics2D, bufferedImage, n2, 0, n, 40);
        }
    }

    private void renderInventoryTab(Graphics2D graphics2D) {
        int n = this.posX + 8;
        int n2 = this.contentStartY + 8;
        int n3 = 40;
        int n4 = 8;
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 12));
        graphics2D.drawString("INVENTORY", n, n2 - 5);
        n2 += 20;
        for (int i = 0; i < 4; ++i) {
            int n5 = n + i % 2 * (n3 + n4);
            int n6 = n2 + i / 2 * (n3 + n4);
            this.drawImageAt(graphics2D, this.inventorySlotEmpty, n5, n6, n3, n3);
            if (this.inventoryIcons[i] == null) continue;
            this.drawImageAt(graphics2D, this.inventoryIcons[i], n5 + 4, n6 + 4, n3 - 8, n3 - 8);
        }
    }

    private void renderCharacterTab(Graphics2D graphics2D) {
        int n = this.posX + 8;
        int n2 = this.contentStartY + 8;
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 12));
        graphics2D.drawString("CHARACTER", n, n2);
        this.drawImageAt(graphics2D, this.characterStatBackground, n, n2 += 25, 184, 80);
        graphics2D.setColor(new Color(200, 200, 200));
        graphics2D.setFont(new Font("Arial", 0, 11));
        graphics2D.drawString("HP: 85/100", n + 10, n2 + 20);
        graphics2D.drawString("ARM: 50/100", n + 10, n2 + 35);
        graphics2D.drawString("DMG: 15", n + 10, n2 + 50);
        graphics2D.drawString("SPD: 12", n + 10, n2 + 65);
    }

    private void renderSkillsTab(Graphics2D graphics2D) {
        int n = this.posX + 8;
        int n2 = this.contentStartY + 8;
        int n3 = 32;
        int n4 = 8;
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 12));
        graphics2D.drawString("SKILLS", n, n2);
        n2 += 25;
        for (int i = 0; i < 5; ++i) {
            int n5 = n;
            int n6 = n2 + i * (n3 + n4);
            this.drawImageAt(graphics2D, this.skillIconPlaceholder, n5, n6, n3, n3);
            graphics2D.setColor(new Color(150, 150, 150));
            graphics2D.setFont(new Font("Arial", 0, 10));
            graphics2D.drawString("Skill " + (i + 1), n5 + n3 + 5, n6 + 15);
        }
    }

    private void renderMapTab(Graphics2D graphics2D) {
        int n = this.posX + 8;
        int n2 = this.contentStartY + 8 + 20;
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 12));
        graphics2D.drawString("MAP", n, n2 - 5);
        this.drawImageAt(graphics2D, this.mapBackground, n, n2, 184, 100);
        graphics2D.setColor(new Color(100, 150, 100));
        graphics2D.drawString("Level: 1", n + 10, n2 + 20);
        graphics2D.drawString("Progress: 45%", n + 10, n2 + 40);
    }

    public void switchTab(int n) {
        Tab[] tabArray = new Tab[]{Tab.INVENTORY, Tab.CHARACTER, Tab.SKILLS, Tab.MAP};
        if (n >= 0 && n < tabArray.length) {
            this.activeTab = tabArray[n];
        }
    }

    public void setInventoryIcon(int n, BufferedImage bufferedImage) {
        if (n >= 0 && n < 4 && bufferedImage != null) {
            this.inventoryIcons[n] = bufferedImage;
        }
    }

    private BufferedImage createSolidImage(int n, int n2, Color color) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 1);
        int n3 = color.getRGB();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                bufferedImage.setRGB(i, j, n3);
            }
        }
        return bufferedImage;
    }

    private static enum Tab {
        INVENTORY,
        CHARACTER,
        SKILLS,
        MAP;

    }
}
