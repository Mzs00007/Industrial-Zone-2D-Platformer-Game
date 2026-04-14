package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import levels.Level1;
import controllers.RenderingSystem;
import ui.components.game2D.GameScreenManager;

/**
 * PHASE 1 REFACTOR: All Rendering Logic
 * - Handles all Graphics2D rendering
 * - Pure rendering functions (no state management)
 * - Receives state from GameScreenManager and renders accordingly
 */
public class GamePanelRenderer {
    
    private BufferedImage splashImage = null;
    private BufferedImage buttonStartImage = null;
    private BufferedImage buttonSettingsImage = null;
    private BufferedImage buttonQuitImage = null;
    
    // Button state tracking
    private int hoveredButtonIndex = -1; // -1 = none, 0 = start, 1 = settings, 2 = quit
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    
    // Button positions for hit detection
    private static final int BUTTON_START_Y = 180;
    private static final int BUTTON_SPACING = 90;
    private static final int BUTTON_X = 280;
    
    private static final int SCREEN_WIDTH = 1024;
    private static final int SCREEN_HEIGHT = 768;
    
    public GamePanelRenderer() {
        loadAssets();
    }
    
    private void loadAssets() {
        System.out.println("[Renderer] Loading visual assets...");
        loadSplashImage();
        loadButtonAssets();
    }
    
    private void loadSplashImage() {
        try {
            File splashFile = new File("Resources/industrial-zone/gui/5 Logo/GUI_Logo_IndustrialZone_Full.png");
            if (splashFile.exists()) {
                splashImage = ImageIO.read(splashFile);
                System.out.println("✅ Splash loaded: " + splashImage.getWidth() + "x" + splashImage.getHeight());
            } else {
                System.err.println("⚠️ Splash not found at: " + splashFile.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("⚠️ Failed to load splash: " + e.getMessage());
        }
    }
    
    private void loadButtonAssets() {
        String buttonDir = "Resources/industrial-zone/gui/6 Buttons/";
        
        try {
            File startBtn = new File(buttonDir + "GUI_ButtonColorMap_Variant_01.png");
            if (startBtn.exists()) {
                buttonStartImage = ImageIO.read(startBtn);
                System.out.println("✅ Start button loaded: " + buttonStartImage.getWidth() + "x" + buttonStartImage.getHeight());
            } else {
                System.err.println("⚠️ Start button not found at: " + startBtn.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("⚠️ Could not load start button: " + e.getMessage());
        }
        
        try {
            File settingsBtn = new File(buttonDir + "GUI_ButtonColorMap_Variant_05.png");
            if (settingsBtn.exists()) {
                buttonSettingsImage = ImageIO.read(settingsBtn);
                System.out.println("✅ Settings button loaded: " + buttonSettingsImage.getWidth() + "x" + buttonSettingsImage.getHeight());
            } else {
                System.err.println("⚠️ Settings button not found at: " + settingsBtn.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("⚠️ Could not load settings button: " + e.getMessage());
        }
        
        try {
            File quitBtn = new File(buttonDir + "GUI_ButtonColorMap_Variant_09.png");
            if (quitBtn.exists()) {
                buttonQuitImage = ImageIO.read(quitBtn);
                System.out.println("✅ Quit button loaded: " + buttonQuitImage.getWidth() + "x" + buttonQuitImage.getHeight());
            } else {
                System.err.println("⚠️ Quit button not found at: " + quitBtn.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("⚠️ Could not load quit button: " + e.getMessage());
        }
    }
    
    /**
     * Update mouse position for button hover detection
     */
    public void updateMousePosition(int mouseX, int mouseY) {
        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;
        
        // Determine which button is hovered
        hoveredButtonIndex = -1;
        if (mouseY >= BUTTON_START_Y && mouseY < BUTTON_START_Y + BUTTON_SPACING) {
            hoveredButtonIndex = 0; // Start button
        } else if (mouseY >= BUTTON_START_Y + BUTTON_SPACING*2 && mouseY < BUTTON_START_Y + BUTTON_SPACING*2 + BUTTON_SPACING) {
            hoveredButtonIndex = 1; // Settings button
        } else if (mouseY >= BUTTON_START_Y + BUTTON_SPACING*3 && mouseY < BUTTON_START_Y + BUTTON_SPACING*3 + BUTTON_SPACING) {
            hoveredButtonIndex = 2; // Quit button
        }
    }
    
    /**
     * Extract a specific button frame from a spritesheet
     * Spritesheets are vertical with 3 different button states
     */
    private BufferedImage getButtonFrame(BufferedImage spritesheet, int buttonIndex, boolean isHovered) {
        if (spritesheet == null) return null;
        
        int sheetWidth = spritesheet.getWidth();
        int sheetHeight = spritesheet.getHeight();
        
        // Each button occupies 1/3 of the height (for 3 buttons vertically)
        int buttonHeight = sheetHeight / 3;
        int frameHeight = buttonHeight / 2; // 2 states: normal and hover
        int frameWidth = sheetWidth;
        
        // Calculate source rectangle
        int srcX = 0;
        int srcY = buttonIndex * buttonHeight;
        
        // Add frame offset if hovered (second half of button area)
        if (isHovered) {
            srcY += frameHeight;
        }
        
        // Extract the frame
        try {
            return spritesheet.getSubimage(srcX, srcY, frameWidth, frameHeight);
        } catch (Exception e) {
            System.err.println("⚠️ Failed to extract button frame: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Main render function - routes to screen-specific renderers
     */
    public void render(BufferedImage dest, GameScreenManager screenManager) {
        if (dest == null || screenManager == null) return;
        
        switch (screenManager.getCurrentState()) {
            case SPLASH:
                renderSplash(dest);
                break;
            case MAIN_MENU:
                renderMainMenu(dest);
                break;
            case LEVEL_SELECT:
                renderLevelSelect(dest, screenManager);
                break;
            case CHARACTER_SELECT:
                renderCharacterSelect(dest);
                break;
            case SETTINGS:
                renderSettings(dest);
                break;
            case GAME_OVER:
                renderGameOver(dest);
                break;
            case PAUSED:
                renderPausedMenu(dest);
                break;
            default:
                renderGameplay(dest, screenManager);
        }
    }
    
    private void renderSplash(BufferedImage dest) {
        Graphics2D g2d = dest.createGraphics();
        g2d.setColor(new Color(30, 30, 50));
        g2d.fillRect(0, 0, dest.getWidth(), dest.getHeight());
        
        if (splashImage != null) {
            int x = (dest.getWidth() - splashImage.getWidth()) / 2;
            int y = (dest.getHeight() - splashImage.getHeight()) / 2;
            g2d.drawImage(splashImage, x, y, null);
        } else {
            g2d.setColor(Color.YELLOW);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.drawString("INDUSTRIAL ZONE", 350, 350);
            g2d.setFont(new Font("Arial", Font.PLAIN, 16));
            g2d.drawString("Click to continue...", 350, 400);
        }
        
        g2d.dispose();
    }
    
    private void renderMainMenu(BufferedImage dest) {
        Graphics2D g2d = dest.createGraphics();
        g2d.setColor(new Color(20, 30, 60));
        g2d.fillRect(0, 0, dest.getWidth(), dest.getHeight());
        
        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        g2d.drawString("MAIN MENU", 300, 100);
        
        // Button 1: START GAME
        BufferedImage startFrame = getButtonFrame(buttonStartImage, 0, hoveredButtonIndex == 0);
        if (startFrame != null) {
            g2d.drawImage(startFrame, BUTTON_X, BUTTON_START_Y, null);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("[1] START GAME", BUTTON_X, BUTTON_START_Y + startFrame.getHeight() + 20);
        } else {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("[1] START GAME", BUTTON_X, BUTTON_START_Y + 35);
        }
        
        // Button 2: LEVEL SELECT (no spritesheet, just text)
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("[2] LEVEL SELECT", BUTTON_X, BUTTON_START_Y + BUTTON_SPACING + 35);
        
        // Button 3: SETTINGS
        BufferedImage settingsFrame = getButtonFrame(buttonSettingsImage, 1, hoveredButtonIndex == 1);
        if (settingsFrame != null) {
            g2d.drawImage(settingsFrame, BUTTON_X, BUTTON_START_Y + BUTTON_SPACING*2, null);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("[3] SETTINGS", BUTTON_X, BUTTON_START_Y + BUTTON_SPACING*2 + settingsFrame.getHeight() + 20);
        } else {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("[3] SETTINGS", BUTTON_X, BUTTON_START_Y + BUTTON_SPACING*2 + 35);
        }
        
        // Button 4: QUIT
        BufferedImage quitFrame = getButtonFrame(buttonQuitImage, 2, hoveredButtonIndex == 2);
        if (quitFrame != null) {
            g2d.drawImage(quitFrame, BUTTON_X, BUTTON_START_Y + BUTTON_SPACING*3, null);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("[4] QUIT", BUTTON_X, BUTTON_START_Y + BUTTON_SPACING*3 + quitFrame.getHeight() + 20);
        } else {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("[4] QUIT", BUTTON_X, BUTTON_START_Y + BUTTON_SPACING*3 + 35);
        }
        
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.ITALIC, 14));
        g2d.drawString("Press 1/2/3/4 or click menu items - Click to START", 120, 700);
        
        g2d.dispose();
    }
    
    private void renderLevelSelect(BufferedImage dest, GameScreenManager screenManager) {
        Graphics2D g2d = dest.createGraphics();
        g2d.setColor(new Color(25, 35, 70));
        g2d.fillRect(0, 0, dest.getWidth(), dest.getHeight());
        
        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString("SELECT LEVEL", 300, 80);
        
        int buttonWidth = 80;
        int buttonHeight = 60;
        int startX = 150;
        int startY = 150;
        int spacingX = 120;
        int spacingY = 80;
        
        for (int i = 1; i <= screenManager.getMaxLevels(); i++) {
            int row = (i - 1) / 5;
            int col = (i - 1) % 5;
            int x = startX + col * spacingX;
            int y = startY + row * spacingY;
            
            boolean levelAvailable = i <= 5;
            if (levelAvailable) {
                g2d.setColor(new Color(80, 120, 200));
            } else {
                g2d.setColor(new Color(60, 60, 100));
            }
            g2d.fillRect(x, y, buttonWidth, buttonHeight);
            
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x, y, buttonWidth, buttonHeight);
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            String levelNum = String.valueOf(i);
            int textWidth = g2d.getFontMetrics().stringWidth(levelNum);
            g2d.drawString(levelNum, x + (buttonWidth - textWidth) / 2, y + 40);
        }
        
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString("Click a level or press number [1-9] to start", 200, 700);
        g2d.drawString("Press [ESC] to return to menu", 300, 730);
        
        g2d.dispose();
    }
    
    private void renderCharacterSelect(BufferedImage dest) {
        Graphics2D g2d = dest.createGraphics();
        g2d.setColor(new Color(30, 40, 80));
        g2d.fillRect(0, 0, dest.getWidth(), dest.getHeight());
        
        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString("SELECT CHARACTER", 250, 80);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString("Characters: Soldier, Hacker, Operative, Cyborg", 150, 200);
        g2d.drawString("Press [ESC] to return", 300, 700);
        
        g2d.dispose();
    }
    
    private void renderGameplay(BufferedImage dest, GameScreenManager screenManager) {
        Graphics2D g2d = dest.createGraphics();
        
        // Clear background
        g2d.setColor(new Color(30, 30, 50));
        g2d.fillRect(0, 0, dest.getWidth(), dest.getHeight());
        
        Level1 currentLevel = screenManager.getCurrentLevelInstance();
        
        if (currentLevel != null) {
            // ═══════════════════════════════════════════════════════════════════════════════════
            // PHASE 2: LEVEL1 RENDERING
            // ═══════════════════════════════════════════════════════════════════════════════════
            
            // 1. Render tilemap
            try {
                currentLevel.renderMap(dest, 0, 0);
                System.out.println("[Phase2] Tilemap rendered");
            } catch (Exception e) {
                System.err.println("[Phase2] Error rendering tilemap: " + e.getMessage());
            }
            
            // 2. Render player sprite with animation
            try {
                Object playerObject = currentLevel.getPlayer();
                if (playerObject != null) {
                    // Get current animation frame from player
                    java.lang.reflect.Method getCurrentFrameMethod = playerObject.getClass()
                            .getMethod("getCurrentFrame");
                    BufferedImage playerFrame = (BufferedImage) getCurrentFrameMethod.invoke(playerObject);
                    
                    if (playerFrame != null) {
                        // Get player position
                        float playerX = currentLevel.getPlayerX();
                        float playerY = currentLevel.getPlayerY();
                        
                        // Draw player sprite on screen
                        g2d.drawImage(playerFrame, (int)playerX, (int)playerY, null);
                        System.out.println("[Phase2] Player rendered at (" + (int)playerX + ", " + (int)playerY + ")");
                    } else {
                        System.out.println("[Phase2] Player frame is null");
                    }
                }
            } catch (Exception e) {
                System.err.println("[Phase2] Error rendering player: " + e.getMessage());
                e.printStackTrace();
            }
            
            // 3. Render enemies (if any)
            try {
                Object enemiesObject = currentLevel.getClass().getMethod("getEnemies").invoke(currentLevel);
                if (enemiesObject instanceof java.util.List) {
                    java.util.List<?> enemies = (java.util.List<?>) enemiesObject;
                    for (Object enemy : enemies) {
                        java.lang.reflect.Method getFrameMethod = enemy.getClass().getMethod("getCurrentFrame");
                        BufferedImage enemyFrame = (BufferedImage) getFrameMethod.invoke(enemy);
                        
                        if (enemyFrame != null) {
                            java.lang.reflect.Method getXMethod = enemy.getClass().getMethod("getX");
                            java.lang.reflect.Method getYMethod = enemy.getClass().getMethod("getY");
                            float ex = ((Number) getXMethod.invoke(enemy)).floatValue();
                            float ey = ((Number) getYMethod.invoke(enemy)).floatValue();
                            
                            g2d.drawImage(enemyFrame, (int)ex, (int)ey, null);
                        }
                    }
                }
            } catch (Exception e) {
                // Enemies rendering not available - continue
            }
        } else if (screenManager.getLevelMapLoader() != null && screenManager.isGameplayInitialized()) {
            screenManager.getLevelMapLoader().render(dest, 0, 0);
        } else {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 18));
            g2d.drawString("Loading level " + screenManager.getCurrentLevel() + "...", 50, 100);
        }
        
        // HUD overlay
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, dest.getHeight() - 80, dest.getWidth(), 80);
        
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("LEVEL " + screenManager.getCurrentLevel(), 20, dest.getHeight() - 50);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        float playerX = currentLevel != null ? currentLevel.getPlayerX() : 0;
        float playerY = currentLevel != null ? currentLevel.getPlayerY() : 0;
        g2d.drawString("Health: 100/100  |  Ammo: 30/150  |  Position: (" + (int)playerX + ", " + (int)playerY + ")", 20, dest.getHeight() - 25);
        
        g2d.setColor(Color.CYAN);
        g2d.drawString("Press [P] to pause   [ESC] to quit   [ARROW KEYS] to move", dest.getWidth() - 500, dest.getHeight() - 25);
        
        g2d.dispose();
    }
    
    private void renderPausedMenu(BufferedImage dest) {
        Graphics2D g2d = dest.createGraphics();
        
        g2d.setColor(new Color(50, 50, 80, 200));
        g2d.fillRect(0, 0, dest.getWidth(), dest.getHeight());
        
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(200, 200, 624, 368);
        
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        g2d.drawString("PAUSED", 350, 280);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("[R] Resume Game", 300, 400);
        g2d.drawString("[S] Settings", 300, 450);
        g2d.drawString("[M] Main Menu", 300, 500);
        
        g2d.setColor(new Color(150, 150, 200));
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString("Press R to resume, S for settings, or M to return to menu", 150, 550);
        
        g2d.dispose();
    }
    
    private void renderSettings(BufferedImage dest) {
        Graphics2D g2d = dest.createGraphics();
        g2d.setColor(new Color(35, 45, 85));
        g2d.fillRect(0, 0, dest.getWidth(), dest.getHeight());
        
        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString("SETTINGS", 350, 100);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString("Volume: 80%", 300, 200);
        g2d.drawString("Graphics: High", 300, 250);
        g2d.drawString("Difficulty: Normal", 300, 300);
        g2d.drawString("Press [ESC] to return", 300, 700);
        
        g2d.dispose();
    }
    
    private void renderGameOver(BufferedImage dest) {
        Graphics2D g2d = dest.createGraphics();
        g2d.setColor(new Color(40, 20, 20));
        g2d.fillRect(0, 0, dest.getWidth(), dest.getHeight());
        
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        g2d.drawString("GAME OVER", 280, 200);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.drawString("Score: 0", 350, 350);
        g2d.drawString("Press [SPACE] to return to menu", 200, 500);
        
        g2d.dispose();
    }
}
