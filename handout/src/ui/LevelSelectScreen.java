package ui;

import java.awt.image.BufferedImage;

/**
 * Level Select Screen
 */
public class LevelSelectScreen {
    int selectedLevel = 1;
    
    public LevelSelectScreen(int w, int h) {
    }
    
    public void render(BufferedImage dest) {
        int[] pixels = new int[dest.getWidth() * dest.getHeight()];
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0xFF1a1a2e;
        }
        dest.setRGB(0, 0, dest.getWidth(), dest.getHeight(), pixels, 0, dest.getWidth());
    }
    
    public int getSelectedLevel() { return selectedLevel; }
    public void selectLevel(int level) { this.selectedLevel = level; }
}
