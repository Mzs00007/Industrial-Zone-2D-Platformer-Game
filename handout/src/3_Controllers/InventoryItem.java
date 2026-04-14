/*
 * Decompiled with CFR 0.152.
 */
package gui.screens;

import java.awt.Color;

public static class Phase7ItemInventoryScreen.InventoryItem {
    public String name;
    public String description;
    public int quantity;
    public Color rarity;

    public Phase7ItemInventoryScreen.InventoryItem(String string, String string2, int n, Color color) {
        this.name = string;
        this.description = string2;
        this.quantity = n;
        this.rarity = color;
    }
}
