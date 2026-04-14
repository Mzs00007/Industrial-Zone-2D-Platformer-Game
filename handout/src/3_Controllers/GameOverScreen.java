package ui;

import java.awt.image.BufferedImage;

/**
 * Game Over / Victory Screen
 */
public class GameOverScreen {
    private boolean isVictory = false;
    private String message = "GAME OVER";
    
    public GameOverScreen(int w, int h) {
    }
    
    public void render(BufferedImage dest) {
        int[] pixels = new int[dest.getWidth() * dest.getHeight()];
        int bgColor = isVictory ? 0xFF1a3a1a : 0xFF3a1a1a; // Green for win, red for loss
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = bgColor;
        }
        dest.setRGB(0, 0, dest.getWidth(), dest.getHeight(), pixels, 0, dest.getWidth());
    }
    
    public void setVictory(boolean win) {
        this.isVictory = win;
        this.message = win ? "LEVEL COMPLETE!" : "GAME OVER";
    }
    
    public String getMessage() { return message; }
}
