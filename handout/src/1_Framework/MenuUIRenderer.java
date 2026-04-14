package framework;

import controllers.GUIAssetManager;
import controllers.screens.FrameTiler;
import java.awt.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * MenuUIRenderer - Main menu rendering logic (rendering ONLY, no input)
 * 
 * RESPONSIBILITY: Render menu UI components
 * 
 * This class handles:
 * - Menu background tiling/rendering
 * - Button frame rendering
 * - Menu text and logo rendering
 * - Visual effects on menu elements
 * 
 * DOES NOT handle:
 * - Input handling (delegated to MenuInputHandler)
 * - State management (delegated to ScreenManager)
 * - Asset loading (delegated to GUIAssetManager)
 */
public class MenuUIRenderer {
    
    private final GUIAssetManager assetManager;
    private final FrameTiler frameTiler;
    private BufferedImage cachedMenuBuffer;
    
    // Menu rendering configuration
    private static final int MENU_FRAME_WIDTH = 800;
    private static final int MENU_FRAME_HEIGHT = 600;
    
    public MenuUIRenderer() {
        this.assetManager = GUIAssetManager.getInstance();
        this.frameTiler = new FrameTiler();
    }
    
    /**
     * Render menu to buffer (pure rendering, no state changes)
     */
    public BufferedImage render(int screenWidth, int screenHeight) {
        if (cachedMenuBuffer == null || cachedMenuBuffer.getWidth() != screenWidth || cachedMenuBuffer.getHeight() != screenHeight) {
            cachedMenuBuffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        }
        
        Graphics2D g = cachedMenuBuffer.createGraphics();
        
        try {
            // Render background
            renderMenuBackground(g, screenWidth, screenHeight);
            
            // Render menu frame
            renderMenuFrame(g, screenWidth, screenHeight);
            
            // Render menu buttons
            renderMenuButtons(g, screenWidth, screenHeight);
            
        } finally {
            g.dispose();
        }
        
        return cachedMenuBuffer;
    }
    
    private void renderMenuBackground(Graphics2D g, int width, int height) {
        // Render dark background
        g.setColor(new java.awt.Color(30, 30, 30));
        g.fillRect(0, 0, width, height);
    }
    
    private void renderMenuFrame(Graphics2D g, int width, int height) {
        // Render menu frame border
        int frameX = (width - MENU_FRAME_WIDTH) / 2;
        int frameY = (height - MENU_FRAME_HEIGHT) / 2;
        
        g.setColor(new java.awt.Color(100, 100, 100));
        g.drawRect(frameX, frameY, MENU_FRAME_WIDTH, MENU_FRAME_HEIGHT);
        g.drawRect(frameX + 2, frameY + 2, MENU_FRAME_WIDTH - 4, MENU_FRAME_HEIGHT - 4);
    }
    
    private void renderMenuButtons(Graphics2D g, int width, int height) {
        // Button rendering delegated to actual button renderers
        // This is just a placeholder for menu-level button rendering
    }
}
