/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.screens.Screen;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Phase7ItemInventoryScreen
extends Screen {
    private static final int SLOT_SIZE = 48;
    private static final int GRID_COLS = 5;
    private static final int GRID_ROWS = 3;
    private static final int PADDING = 10;
    private List<InventoryItem> items = new ArrayList<InventoryItem>();
    private int selectedSlot = -1;
    private boolean showTooltip = false;
    private int tooltipX = 0;
    private int tooltipY = 0;

    public Phase7ItemInventoryScreen() {
        super(null);
        this.initializeInventory();
    }

    private void initializeInventory() {
        this.items.add(new InventoryItem("Health Potion", "Restores 50 HP", 5, new Color(150, 150, 150)));
        this.items.add(new InventoryItem("Mana Potion", "Restores 30 Mana", 3, new Color(100, 200, 100)));
        this.items.add(new InventoryItem("Antidote", "Cures poison", 2, new Color(100, 150, 255)));
        this.items.add(new InventoryItem("Iron Sword", "Sharp blade", 1, new Color(180, 100, 255)));
        this.items.add(new InventoryItem("Ancient Amulet", "Legendary artifact", 1, new Color(255, 165, 0)));
    }

    @Override
    public void update(float f) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public BufferedImage render(int n, int n2) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 2);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            int n3 = 20;
            int n4 = n2 - 250;
            int n5 = 280;
            int n6 = 214;
            graphics2D.setColor(new Color(30, 30, 30, 200));
            graphics2D.fillRect(n3, n4, n5, n6);
            graphics2D.setColor(new Color(100, 150, 200, 255));
            graphics2D.setStroke(new BasicStroke(2.0f));
            graphics2D.drawRect(n3, n4, n5, n6);
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(new Font("Arial", 1, 14));
            graphics2D.drawString("INVENTORY", n3 + 10, n4 + 25);
            int n7 = n3 + 10;
            int n8 = n4 + 40;
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 5; ++j) {
                    int n9 = i * 5 + j;
                    int n10 = n7 + j * 53;
                    int n11 = n8 + i * 53;
                    this.renderSlot(graphics2D, n9, n10, n11);
                }
            }
            if (this.showTooltip && this.selectedSlot >= 0 && this.selectedSlot < this.items.size()) {
                this.renderTooltip(graphics2D, this.tooltipX, this.tooltipY);
            }
        }
        finally {
            graphics2D.dispose();
        }
        return bufferedImage;
    }

    private void renderSlot(Graphics2D graphics2D, int n, int n2, int n3) {
        boolean bl;
        boolean bl2 = bl = n == this.selectedSlot;
        if (bl) {
            graphics2D.setColor(new Color(100, 150, 200, 200));
        } else {
            graphics2D.setColor(new Color(50, 50, 50, 200));
        }
        graphics2D.fillRect(n2, n3, 48, 48);
        if (bl) {
            graphics2D.setColor(new Color(100, 200, 255, 255));
            graphics2D.setStroke(new BasicStroke(3.0f));
        } else {
            graphics2D.setColor(new Color(80, 80, 80, 200));
            graphics2D.setStroke(new BasicStroke(1.0f));
        }
        graphics2D.drawRect(n2, n3, 48, 48);
        if (n < this.items.size()) {
            InventoryItem inventoryItem = this.items.get(n);
            graphics2D.setColor(inventoryItem.rarity);
            graphics2D.fillRect(n2 + 4, n3 + 4, 40, 40);
            if (inventoryItem.quantity > 1) {
                graphics2D.setColor(Color.WHITE);
                graphics2D.setFont(new Font("Arial", 1, 10));
                graphics2D.drawString(String.valueOf(inventoryItem.quantity), n2 + 48 - 20, n3 + 48 - 5);
            }
        }
    }

    private void renderTooltip(Graphics2D graphics2D, int n, int n2) {
        InventoryItem inventoryItem = this.items.get(this.selectedSlot);
        graphics2D.setColor(new Color(20, 20, 20, 220));
        graphics2D.fillRect(n, n2, 200, 80);
        graphics2D.setColor(inventoryItem.rarity);
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRect(n, n2, 200, 80);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 11));
        graphics2D.drawString(inventoryItem.name, n + 10, n2 + 20);
        graphics2D.setFont(new Font("Arial", 0, 9));
        graphics2D.drawString(inventoryItem.description, n + 10, n2 + 40);
        graphics2D.setColor(new Color(200, 200, 100));
        graphics2D.drawString("Qty: " + inventoryItem.quantity, n + 10, n2 + 60);
    }

    public void selectSlot(int n) {
        this.selectedSlot = n;
        this.showTooltip = true;
    }

    public void addItem(String string, String string2, int n, Color color) {
        this.items.add(new InventoryItem(string, string2, n, color));
    }

    public void removeItem(int n) {
        if (n >= 0 && n < this.items.size()) {
            this.items.remove(n);
        }
    }

    public InventoryItem getItem(int n) {
        if (n >= 0 && n < this.items.size()) {
            return this.items.get(n);
        }
        return null;
    }

    public void setTooltipPosition(int n, int n2) {
        this.tooltipX = n;
        this.tooltipY = n2;
    }

    public static class InventoryItem {
        public String name;
        public String description;
        public int quantity;
        public Color rarity;

        public InventoryItem(String string, String string2, int n, Color color) {
            this.name = string;
            this.description = string2;
            this.quantity = n;
            this.rarity = color;
        }
    }
}
