package controllers;

import java.awt.image.BufferedImage;
import animation.InputController.PixelCopyHelper;

/**
 * Main Menu - Start game, controls, exit
 */
public class MainMenuScreen {
    private boolean showControls = false;
    
    public MainMenuScreen(int w, int h) {
    }
    
    public void render(BufferedImage dest) {
        // Dark menu background
        int[] pixels = new int[dest.getWidth() * dest.getHeight()];
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0xFF0a0a1a;
        }
        dest.setRGB(0, 0, dest.getWidth(), dest.getHeight(), pixels, 0, dest.getWidth());
    }
    
    public void setShowControls(boolean show) {
        this.showControls = show;
    }
}
