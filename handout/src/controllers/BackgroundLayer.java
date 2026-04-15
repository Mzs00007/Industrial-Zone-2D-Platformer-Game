/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import java.awt.image.BufferedImage;
public class BackgroundLayer {
    public int priority;
    public float parallaxX;
    public float parallaxY;
    public String imagePath;
    public BufferedImage image;

    public BackgroundLayer(int n, float f, float f2, String string) {
        this.priority = n;
        this.parallaxX = f;
        this.parallaxY = f2;
        this.imagePath = string;
    }
}
