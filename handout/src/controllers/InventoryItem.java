/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import java.awt.Color;
public class InventoryItem {
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
