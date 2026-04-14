package ui;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * UNIFIED GAME SCREEN - ALL SCREENS MERGED INTO ONE SYNCHRONIZED SYSTEM
 * ════════════════════════════════════════════════════════════════════════════
 * Handles: Splash → Menu → Level Select → Character Select → Gameplay → GameOver
 */
public class UnifiedGameScreen {
    
    public enum ScreenState {
        SPLASH, MENU, LEVEL_SELECT, CHARACTER_SELECT, GAMEPLAY, GAME_OVER
    }
    
    private ScreenState currentState = ScreenState.SPLASH;
    private int screenWidth, screenHeight;
    
    // Timing
    private long splashStartTime = System.currentTimeMillis();
    private static final long SPLASH_DURATION = 3000; // 3 seconds
    
    // UI Assets - Character Select
    private UISystem.CharacterSelectScreen characterSelectScreen;
    
    // Gameplay
    private GameplayScreenV2 gameplayScreen;
    
    // Selection State
    private String selectedCharacter = "PUNK";
    private String selectedLevel = "Industrial_zone_level_1";
    private int characterIndex = 0; // 0=PUNK, 1=BIKER, 2=CYBORG
    private String[] characters = {"PUNK", "BIKER", "CYBORG"};
    
    public UnifiedGameScreen(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
        
        // Initialize character select screen
        this.characterSelectScreen = new UISystem.CharacterSelectScreen(width, height);
        
        // Initialize gameplay
        this.gameplayScreen = new GameplayScreenV2(width, height);
        
        System.out.println("[UnifiedGameScreen] ✅ System initialized - starting SPLASH screen");
    }
    
    /**
     * MAIN RENDER LOOP - ALL SCREENS RENDER HERE
     */
    public void render(BufferedImage dest) {
        if (dest == null) return;
        
        switch(currentState) {
            case SPLASH:
                renderSplash(dest);
                break;
            case MENU:
                renderMenu(dest);
                break;
            case LEVEL_SELECT:
                renderLevelSelect(dest);
                break;
            case CHARACTER_SELECT:
                renderCharacterSelect(dest);
                break;
            case GAMEPLAY:
                renderGameplay(dest);
                break;
            case GAME_OVER:
                renderGameOver(dest);
                break;
        }
    }
    
    /**
     * SPLASH SCREEN - Black screen with text for 3 seconds
     */
    private void renderSplash(BufferedImage dest) {
        int[] pixels = ((java.awt.image.DataBufferInt) dest.getRaster().getDataBuffer()).getData();
        java.util.Arrays.fill(pixels, 0xFF000000); // Black
        
        long elapsed = System.currentTimeMillis() - splashStartTime;
        if (elapsed > SPLASH_DURATION) {
            currentState = ScreenState.MENU;
            System.out.println("[Screen] → MENU");
        }
        
        // Draw text
        drawText(dest, "INDUSTRIAL ZONE", screenWidth/2 - 150, screenHeight/2 - 50, 0xFFFFFF00);
        drawText(dest, "Cyberpunk Action Defense", screenWidth/2 - 150, screenHeight/2 + 20, 0xFF00FFFF);
    }
    
    /**
     * MENU SCREEN - Press SPACE to start
     */
    private void renderMenu(BufferedImage dest) {
        // Dark background
        int[] pixels = ((java.awt.image.DataBufferInt) dest.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0xFF1a1a2e; // Dark blue
        }
        
        drawText(dest, "INDUSTRIAL ZONE", screenWidth/2 - 150, 100, 0xFF00FFFF);
        drawText(dest, "Main Menu", screenWidth/2 - 80, 200, 0xFFFFFF00);
        drawText(dest, "Press SPACE to start", screenWidth/2 - 130, 350, 0xFF00FF00);
        drawText(dest, "ESC to exit", screenWidth/2 - 80, 400, 0xFFFF0000);
    }
    
    /**
     * LEVEL SELECT SCREEN
     */
    private void renderLevelSelect(BufferedImage dest) {
        int[] pixels = ((java.awt.image.DataBufferInt) dest.getRaster().getDataBuffer()).getData();
        java.util.Arrays.fill(pixels, 0xFF0f3460); // Dark purple
        
        drawText(dest, "SELECT LEVEL", screenWidth/2 - 110, 100, 0xFF00FFFF);
        drawText(dest, "Press 1: Industrial Zone Level 1", screenWidth/2 - 180, 250, 0xFFFFFF00);
        drawText(dest, "Press 2: Power Station Level 2", screenWidth/2 - 180, 350, 0xFFFFFF00);
        drawText(dest, "ESC to go back", screenWidth/2 - 110, 500, 0xFFFF0000);
    }
    
    /**
     * CHARACTER SELECT SCREEN - Using UISystem.CharacterSelectScreen
     */
    private void renderCharacterSelect(BufferedImage dest) {
        try {
            // Use CharacterSelectScreen to render character cards
            characterSelectScreen.render(dest);
        } catch (Exception e) {
            System.err.println("[CharacterSelect] Error: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback: Simple colored screen with text
            int[] pixels = ((java.awt.image.DataBufferInt) dest.getRaster().getDataBuffer()).getData();
            java.util.Arrays.fill(pixels, 0xFF2a2a4e);
            
            drawText(dest, "SELECT CHARACTER", screenWidth/2 - 140, 100, 0xFF00FFFF);
            drawText(dest, "Punk", 100, 300, selectedCharacter.equals("PUNK") ? 0xFF00FF00 : 0xFFFFFFFF);
            drawText(dest, "Biker", screenWidth/2 - 30, 300, selectedCharacter.equals("BIKER") ? 0xFF00FF00 : 0xFFFFFFFF);
            drawText(dest, "Cyborg", screenWidth - 200, 300, selectedCharacter.equals("CYBORG") ? 0xFF00FF00 : 0xFFFFFFFF);
            drawText(dest, "Arrow Left/Right: select | ENTER: play | ESC: back", screenWidth/2 - 250, 600, 0xFFFFFF00);
        }
    }
    
    /**
     * GAMEPLAY SCREEN - With physics and animation
     */
    private void renderGameplay(BufferedImage dest) {
        try {
            gameplayScreen.update(16);
            gameplayScreen.render(dest);
        } catch (Exception e) {
            System.err.println("[Gameplay] Render error: " + e.getMessage());
            int[] pixels = ((java.awt.image.DataBufferInt) dest.getRaster().getDataBuffer()).getData();
            java.util.Arrays.fill(pixels, 0xFF1a1a1a);
            drawText(dest, "GAMEPLAY ERROR", screenWidth/2 - 120, 300, 0xFFFF0000);
        }
    }
    
    /**
     * GAME OVER SCREEN
     */
    private void renderGameOver(BufferedImage dest) {
        int[] pixels = ((java.awt.image.DataBufferInt) dest.getRaster().getDataBuffer()).getData();
        java.util.Arrays.fill(pixels, 0xFF0a0a0a); // Black
        
        drawText(dest, "GAME OVER", screenWidth/2 - 120, 200, 0xFFFF0000);
        drawText(dest, "Press SPACE to continue", screenWidth/2 - 150, 400, 0xFF00FF00);
    }
    
    /**
     * HANDLE KEYBOARD INPUT
     */
    public void handleKeyPress(int keyCode) {
        switch(currentState) {
            case MENU:
                if (keyCode == KeyEvent.VK_SPACE) {
                    currentState = ScreenState.LEVEL_SELECT;
                    System.out.println("[Screen] → LEVEL_SELECT");
                } else if (keyCode == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                break;
                
            case LEVEL_SELECT:
                if (keyCode == KeyEvent.VK_1) {
                    selectedLevel = "Industrial_zone_level_1";
                    currentState = ScreenState.CHARACTER_SELECT;
                    System.out.println("[Screen] → CHARACTER_SELECT (Level 1)");
                } else if (keyCode == KeyEvent.VK_2) {
                    selectedLevel = "power-station-level-2";
                    currentState = ScreenState.CHARACTER_SELECT;
                    System.out.println("[Screen] → CHARACTER_SELECT (Level 2)");
                } else if (keyCode == KeyEvent.VK_ESCAPE) {
                    currentState = ScreenState.MENU;
                    System.out.println("[Screen] → MENU");
                }
                break;
                
            case CHARACTER_SELECT:
                if (keyCode == KeyEvent.VK_LEFT) {
                    characterIndex = (characterIndex - 1 + characters.length) % characters.length;
                    selectedCharacter = characters[characterIndex];
                    System.out.println("[Screen] Character: " + selectedCharacter);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    characterIndex = (characterIndex + 1) % characters.length;
                    selectedCharacter = characters[characterIndex];
                    System.out.println("[Screen] Character: " + selectedCharacter);
                } else if (keyCode == KeyEvent.VK_ENTER) {
                    gameplayScreen.loadCharacter(selectedCharacter);
                    gameplayScreen.loadLevel(selectedLevel);
                    currentState = ScreenState.GAMEPLAY;
                    System.out.println("[Screen] → GAMEPLAY (" + selectedCharacter + " in " + selectedLevel + ")");
                } else if (keyCode == KeyEvent.VK_ESCAPE) {
                    currentState = ScreenState.LEVEL_SELECT;
                    System.out.println("[Screen] → LEVEL_SELECT");
                }
                break;
                
            case GAMEPLAY:
                if (keyCode == KeyEvent.VK_ESCAPE) {
                    currentState = ScreenState.CHARACTER_SELECT;
                    System.out.println("[Screen] → CHARACTER_SELECT");
                } else {
                    // Pass input to gameplay
                    gameplayScreen.setKeyPressed(keyCode, true);
                }
                break;
                
            case GAME_OVER:
                if (keyCode == KeyEvent.VK_SPACE) {
                    currentState = ScreenState.MENU;
                    System.out.println("[Screen] → MENU");
                }
                break;
        }
    }
    
    public void handleKeyRelease(int keyCode) {
        if (currentState == ScreenState.GAMEPLAY) {
            gameplayScreen.setKeyPressed(keyCode, false);
        }
    }
    
    /**
     * Simple text rendering - WHITE text
     */
    private void drawText(BufferedImage dest, String text, int x, int y, int color) {
        if (dest == null || text == null) return;
        int[] pixels = ((java.awt.image.DataBufferInt) dest.getRaster().getDataBuffer()).getData();
        int width = dest.getWidth();
        int height = dest.getHeight();
        
        // Simple text rendering with 8-pixel characters
        int charWidth = 8;
        int charHeight = 16;
        
        int px = x;
        for (char c : text.toCharArray()) {
            if (px + charWidth > width) break;
            if (y + charHeight > height) break;
            
            // Draw character
            for (int dy = 0; dy < charHeight; dy++) {
                for (int dx = 0; dx < charWidth; dx++) {
                    int idx = (y + dy) * width + (px + dx);
                    if (idx >= 0 && idx < pixels.length) {
                        pixels[idx] = color;
                    }
                }
            }
            px += charWidth + 2;
        }
    }
}
