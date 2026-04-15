/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import java.awt.image.BufferedImage;
class GUIButton {
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
