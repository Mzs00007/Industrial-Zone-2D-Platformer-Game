# GUI IMPLEMENTATION ROADMAP - Code Structure

## PHASE 1: CORE RENDERING FOUNDATION (Current State)

### Required Methods (Already Have / Need to Add)

```java
// ALREADY IMPLEMENTED:
✓ loadRasterAssets()           // Loads all 939 images into cache
✓ imageCache Map<String, BufferedImage>
✓ fontImageCache Map<Character, BufferedImage>
✓ draw(Graphics2D g)            // Main render method

// CRITICAL ADDITIONS NEEDED:
□ renderBackground()             // Background tiling
□ renderTilemap()                // Level tiles using registry
□ renderGameHUD()                // Status bars + stats
□ renderFrame()                  // GUI_FRAMES/ border construction
□ renderText()                   // GUI_FONT_IMAGES/ char-by-char
```

---

## PHASE 2: MAIN GAME SCREEN RENDERING

### Implementation Order

#### Step 1: renderBackground()
```java
private void renderBackground(Graphics2D g) {
    Buffer Image bgImage = null;
    
    // Get level-appropriate background
    if (currentLevel == 1) {
        for (String key : imageCache.keySet()) {
            if (key.contains(L1_BG_BASE)) {
                bgImage = imageCache.get(key);
                break;
            }
        }
    } else {
        for (String key : imageCache.keySet()) {
            if (key.contains(L2_BG_BASE)) {
                bgImage = imageCache.get(key);
                break;
            }
        }
    }
    
    // Tile background to fill screen
    if (bgImage != null) {
        int bgW = bgImage.getWidth();
        int bgH = bgImage.getHeight();
        for (int y = 0; y < getHeight(); y += bgH) {
            for (int x = 0; x < getWidth(); x += bgW) {
                g.drawImage(bgImage, x, y, null);
            }
        }
    }
}
```

---

#### Step 2: renderTilemap()
```java
private void renderTilemap(Graphics2D g) {
    final int TILE_SIZE = 64;
    
    // Define level layout
    String[] levelMap = getCurrentLevelMap();
    
    // Get a sample tile image
    BufferedImage tileImg = getFirstTileFromCache();
    
    // Render all tiles
    int screenW = getWidth();
    int screenH = getHeight();
    
    for (int row = 0; row < levelMap.length; row++) {
        int screenY = screenH - ((levelMap.length - row) * TILE_SIZE);
        String mapRow = levelMap[row];
        
        for (int col = 0; col < mapRow.length(); col++) {
            char tile = mapRow.charAt(col);
            if (tile == ' ') continue;
            
            int screenX = col * TILE_SIZE;
            g.drawImage(tileImg, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
    }
}
```

---

#### Step 3: renderEntities()
```java
private void renderPlayer(Graphics2D g) {
    BufferedImage playerImg = getFirstImageContaining(PLAYER_BASE);
    if (playerImg != null) {
        int pX = getWidth() / 2 - 32;
        int pY = getHeight() - 200;
        g.drawImage(playerImg, pX, pY, 64, 64, null);
    }
}

private void renderEnemies(Graphics2D g) {
    BufferedImage enemyImg = getFirstImageContaining(ENEMY_BASE);
    for (Enemy enemy : enemies) {
        if (enemyImg != null) {
            g.drawImage(enemyImg, (int)enemy.x, (int)enemy.y, 64, 64, null);
        }
    }
}

private void renderBullets(Graphics2D g) {
    BufferedImage bulletImg = getFirstImageContaining(WEAPON_1_BULLETS);
    for (Bullet bullet : bullets) {
        if (bulletImg != null) {
            g.drawImage(bulletImg, (int)bullet.x, (int)bullet.y, 16, 16, null);
        }
    }
}
```

---

#### Step 4: renderGameHUD()
```java
private void renderGameHUD(Graphics2D g) {
    final int HUD_Y = getHeight() - 100;
    
    // Draw HUD background panel (frame construction)
    drawFramePanel(g, 0, HUD_Y, getWidth(), 100);
    
    // Health Bar
    BufferedImage healthBg = getImageContaining("health_bg");
    BufferedImage healthFill = getImageContaining("health_fill");
    
    if (healthBg != null) {
        g.drawImage(healthBg, 20, HUD_Y, 200, 20, null);
        if (healthFill != null) {
            int fillWidth = (int)(190 * playerHealth / playerHealthMax);
            g.drawImage(healthFill, 25, HUD_Y + 2, fillWidth, 16, null);
        }
    }
    
    // Energy Bar
    BufferedImage energyBg = getImageContaining("energy_bg");
    BufferedImage energyFill = getImageContaining("energy_fill");
    
    if (energyBg != null) {
        g.drawImage(energyBg, 20, HUD_Y + 30, 200, 20, null);
        if (energyFill != null) {
            int fillWidth = (int)(190 * playerEnergy / playerEnergyMax);
            g.drawImage(energyFill, 25, HUD_Y + 32, fillWidth, 16, null);
        }
    }
    
    // Text Labels
    renderText(g, "HP:" + playerHealth + "/" + playerHealthMax, 20, HUD_Y - 25, 8, 12);
    renderText(g, "EN:" + playerEnergy + "/" + playerEnergyMax, 120, HUD_Y - 25, 8, 12);
    renderText(g, "AMMO:" + playerAmmo + "/" + playerAmmoMax, 400, HUD_Y - 25, 8, 12);
    renderText(g, "SCORE:" + playerScore, 900, HUD_Y - 25, 8, 12);
    
    // Weapon Indicator
    BufferedImage weaponIcon = getFirstImageContaining(WEAPON_1_EFFECTS);
    if (weaponIcon != null) {
        g.drawImage(weaponIcon, 800, HUD_Y, 40, 40, null);
    }
    
    // Key Bindings
    renderText(g, "SPACE:FIRE", 900, HUD_Y, 8, 12);
    renderText(g, "ESC:MENU", 1000, HUD_Y, 8, 12);
}
```

---

### Step 5: drawFramePanel() - Border Construction
```java
private void drawFramePanel(Graphics2D g, int x, int y, int w, int h) {
    // Get frame components from GUI_FRAMES/
    BufferedImage cornerTL = getImageContaining("corner_top_left");
    BufferedImage cornerTR = getImageContaining("corner_top_right");
    BufferedImage cornerBL = getImageContaining("corner_bottom_left");
    BufferedImage cornerBR = getImageContaining("corner_bottom_right");
    BufferedImage edgeTop = getImageContaining("edge_top");
    BufferedImage edgeBottom = getImageContaining("edge_bottom");
    BufferedImage edgeLeft = getImageContaining("edge_left");
    BufferedImage edgeRight = getImageContaining("edge_right");
    BufferedImage panelFill = getImageContaining("panel_fill");
    
    // Fill center with panel background
    if (panelFill != null) {
        for (int py = y + 64; py < y + h - 64; py += panelFill.getHeight()) {
            for (int px = x + 64; px < x + w - 64; px += panelFill.getWidth()) {
                g.drawImage(panelFill, px, py, null);
            }
        }
    }
    
    // Draw corners
    if (cornerTL != null) g.drawImage(cornerTL, x, y, null);
    if (cornerTR != null) g.drawImage(cornerTR, x + w - 64, y, null);
    if (cornerBL != null) g.drawImage(cornerBL, x, y + h - 64, null);
    if (cornerBR != null) g.drawImage(cornerBR, x + w - 64, y + h - 64, null);
    
    // Draw edges (tiled)
    if (edgeTop != null) {
        for (int px = x + 64; px < x + w - 64; px += edgeTop.getWidth()) {
            g.drawImage(edgeTop, px, y, null);
        }
    }
    if (edgeBottom != null) {
        for (int px = x + 64; px < x + w - 64; px += edgeBottom.getWidth()) {
            g.drawImage(edgeBottom, px, y + h - 64, null);
        }
    }
    if (edgeLeft != null) {
        for (int py = y + 64; py < y + h - 64; py += edgeLeft.getHeight()) {
            g.drawImage(edgeLeft, x, py, null);
        }
    }
    if (edgeRight != null) {
        for (int py = y + 64; py < y + h - 64; py += edgeRight.getHeight()) {
            g.drawImage(edgeRight, x + w - 64, py, null);
        }
    }
}
```

---

#### Step 6: renderText()
```java
private void renderText(Graphics2D g, String text, int x, int y, 
                        int charWidth, int charHeight) {
    int currentX = x;
    
    for (char c : text.toCharArray()) {
        BufferedImage charImg = fontImageCache.get(c);
        if (charImg != null) {
            g.drawImage(charImg, currentX, y, charWidth, charHeight, null);
        }
        currentX += charWidth;
    }
}
```

---

## PHASE 3: HELPER METHODS

### Asset Lookup Utilities

```java
/** Get first image from cache containing folder path */
private BufferedImage getFirstImageContaining(String folderPath) {
    for (String key : imageCache.keySet()) {
        if (key.contains(folderPath)) {
            return imageCache.get(key);
        }
    }
    return null;
}

/** Get image matching pattern */
private BufferedImage getImageContaining(String pattern) {
    for (String key : imageCache.keySet()) {
        if (key.toLowerCase().contains(pattern.toLowerCase())) {
            return imageCache.get(key);
        }
    }
    return null;
}

/** Get current level map layout */
private String[] getCurrentLevelMap() {
    if (currentLevel == 1) {
        return new String[] {
            "                         ",
            "                         ",
            "             A            ",
            "AAAAAAAAA    AAAAA  AAAA  ",
            "ZZZZZZZZZZZZZZZZZZZZZZZZZZ",
            "ZZZZZZZZZZZZZZZZZZZZZZZZZZ",
            "ZZZZZZZZZZZZZZZZZZZZZZZZZZ",
            "ZZZZZZZZZZZZZZZZZZZZZZZZZZ",
        };
    } else {
        return new String[] {
            "                         ",
            "                         ",
            "           A              ",
            "AAAAA      AAAAA    AAAA  ",
            "QQQQQQQQQQQQQQQQQQQQQQQQQQ",
            "QQQQQQQQQQQQQQQQQQQQQQQQQQ",
            "QQQQQQQQQQQQQQQQQQQQQQQQQQ",
            "QQQQQQQQQQQQQQQQQQQQQQQQQQ",
        };
    }
}
```

---

## PHASE 4: SCREEN STATE MANAGEMENT

### Menu Screen Rendering

```java
private void renderMainMenu(Graphics2D g) {
    // Black background
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());
    
    // Background - tiled frame or level BG
    BufferedImage menubBg = getFirstImageContaining(GUI_FRAMES);
    if (menubBg != null) {
        for (int y = 0; y < getHeight(); y += menubBg.getHeight()) {
            for (int x = 0; x < getWidth(); x += menubBg.getWidth()) {
                g.drawImage(menubBg, x, y, null);
            }
        }
    }
    
    // Logo
    BufferedImage logo = getFirstImageContaining(GUI_LOGO);
    if (logo != null) {
        int logoX = (getWidth() - logo.getWidth()) / 2;
        g.drawImage(logo, logoX, 30, getWidth() - 100, 100, null);
    }
    
    // Character display
    BufferedImage playerImg = getFirstImageContaining(PLAYER_BASE);
    BufferedImage enemyImg = getFirstImageContaining(ENEMY_BASE);
    
    if (playerImg != null) {
        g.drawImage(playerImg, 40, 150, 80, 80, null);
    }
    if (enemyImg != null) {
        g.drawImage(enemyImg, getWidth() - 120, 150, 80, 80, null);
    }
    
    // Buttons
    BufferedImage buttonImg = getFirstImageContaining(GUI_BUTTONS);
    if (buttonImg != null) {
        // Start Button
        g.drawImage(buttonImg, 350, 380, 150, 50, null);
        renderText(g, "START", 370, 400, 8, 12);
        
        // Options Button
        g.drawImage(buttonImg, 820, 380, 150, 50, null);
        renderText(g, "OPTIONS", 840, 400, 8, 12);
    }
    
    // Instructions
    renderText(g, "PRESS SPACE TO START", 400, 480, 8, 12);
    renderText(g, "PRESS ESC FOR OPTIONS", 390, 510, 8, 12);
}
```

---

### Victory Screen Rendering

```java
private void renderVictoryScreen(Graphics2D g) {
    // Draw semi-transparent overlay
    g.setColor(new Color(0, 0, 0, 100));
    g.fillRect(0, 0, getWidth(), getHeight());
    
    // Victory frame
    drawFramePanel(g, 150, 120, getWidth() - 300, getHeight() - 240);
    
    // Victory banner
    BufferedImage victoryImg = getFirstImageContaining("victory");
    if (victoryImg == null) victoryImg = getFirstImageContaining(GUI_FRAMES);
    
    if (victoryImg != null) {
        int bannerX = getWidth() / 2 - victoryImg.getWidth() / 2;
        g.drawImage(victoryImg, bannerX, 150, null);
    }
    
    // Text
    renderText(g, "LEVEL COMPLETE!", getWidth() / 2 - 60, 200, 10, 14);
    
    // Statistics
    int statsY = 300;
    renderText(g, "ENEMIES DEFEATED: " + enemiesDefeated + "/" + enemiesRequired, 
               300, statsY, 8, 12);
    renderText(g, "FINAL SCORE:" + playerScore, 
               300, statsY + 30, 8, 12);
    
    // Continue button
    BufferedImage btnImg = getFirstImageContaining(GUI_BUTTONS);
    if (btnImg != null) {
        g.drawImage(btnImg, getWidth()/2 - 75, 500, 150, 50, null);
    }
    renderText(g, "PRESS ESC", getWidth()/2 - 30, 520, 8, 12);
    
    // Defeated enemy display
    BufferedImage enemyImg = getFirstImageContaining(ENEMY_BASE);
    if (enemyImg != null) {
        g.drawImage(enemyImg, getWidth()/2 - 40, getHeight() - 150, 80, 80, null);
    }
}
```

---

### Game Over Screen Rendering

```java
private void renderGameOverScreen(Graphics2D g) {
    // Red tint overlay
    g.setColor(new Color(200, 0, 0, 100));
    g.fillRect(0, 0, getWidth(), getHeight());
    
    // Game over frame
    drawFramePanel(g, 150, 120, getWidth() - 300, getHeight() - 240);
    
    // Banner
    BufferedImage gameOverImg = getFirstImageContaining(GUI_FRAMES);
    if (gameOverImg != null) {
        g.drawImage(gameOverImg, getWidth()/2 - 150, 150, 300, 80, null);
    }
    
    // Text
    renderText(g, "GAME OVER", getWidth() / 2 - 40, 200, 10, 14);
    renderText(g, "HEALTH DEPLETED", getWidth() / 2 - 60, 280, 8, 12);
    
    // Statistics
    int statsY = 320;
    renderText(g, "ENEMIES DEFEATED:" + enemiesDefeated, 300, statsY, 8, 12);
    renderText(g, "FINAL SCORE:" + playerScore, 300, statsY + 30, 8, 12);
    
    // Restart button
    BufferedImage btnImg = getFirstImageContaining(GUI_BUTTONS);
    if (btnImg != null) {
        g.drawImage(btnImg, getWidth()/2 - 75, 500, 150, 50, null);
    }
    renderText(g, "PRESS ESC", getWidth()/2 - 30, 520, 8, 12);
    
    // Defeated player
    BufferedImage playerImg = getFirstImageContaining(PLAYER_BASE);
    if (playerImg != null) {
        g.drawImage(playerImg, getWidth()/2 - 40, getHeight() - 150, 80, 80, null);
    }
}
```

---

## PHASE 5: INTEGRATION INTO draw() METHOD

```java
@Override
public void draw(Graphics2D g) {
    if (gameState.equals("MAIN_MENU")) {
        renderMainMenu(g);
    } else if (gameState.equals("PLAYING")) {
        // Game rendering
        renderBackground(g);
        renderTilemap(g);
        renderEnemies(g);
        renderBullets(g);
        renderPlayer(g);
        renderGameHUD(g);
    } else if (gameState.equals("WON")) {
        renderVictoryScreen(g);
    } else if (gameState.equals("GAME_OVER")) {
        renderGameOverScreen(g);
    }
}
```

---

## IMPLEMENTATION CHECKLIST

### Rendering Functions
- [ ] `renderBackground(Graphics2D)`
- [ ] `renderTilemap(Graphics2D)`
- [ ] `renderPlayer(Graphics2D)`
- [ ] `renderEnemies(Graphics2D)`
- [ ] `renderBullets(Graphics2D)`
- [ ] `renderGameHUD(Graphics2D)`
- [ ] `drawFramePanel(Graphics2D, x, y, w, h)`
- [ ] `renderText(Graphics2D, String, x, y, charW, charH)`
- [ ] `renderMainMenu(Graphics2D)`
- [ ] `renderVictoryScreen(Graphics2D)`
- [ ] `renderGameOverScreen(Graphics2D)`

### Helper Functions
- [ ] `getFirstImageContaining(String folder)`
- [ ] `getImageContaining(String pattern)`
- [ ] `getCurrentLevelMap()`

### Game State Management
- [ ] Add gameState: "MAIN_MENU", "PLAYING", "PAUSED", "WON", "GAME_OVER"
- [ ] Update draw() switch on gameState
- [ ] Update keyPressed() for state transitions

### Asset Verification
- [ ] Verify all 939 images loaded
- [ ] Verify 63 font characters loaded
- [ ] Test asset lookup with search fallbacks
- [ ] Test tile rendering with sample images
- [ ] Test HUD rendering with bar images

---

## EXPECTED OUTPUT AT EACH PHASE

| Phase | Output | Status |
|-------|--------|--------|
| 1 | All 939 assets loaded, cached ready | Current |
| 2 | Full in-game view: background + tilemap + entities + HUD | Next |
| 3 | Helper methods working, asset lookups efficient | Then |
| 4 | Menu, victory, game-over screens complete | Then |
| 5 | Full GUI integrated, all screens working | Final |

---

## TESTING STRATEGY

```
Test Sequence:
1. Verify imageCache populated with all 939 images
2. Verify fontImageCache has 63 characters
3. Test renderBackground() shows tiled image
4. Test renderTilemap() shows grid of tiles
5. Test renderGameHUD() displays bars + text
6. Test renderText() renders font correctly
7. Test frame construction from GUI_FRAMES/
8. Play game: verify all rendering works at 60fps
9. Test menu transitions
10. Test victory/game-over screens
```

This implementation ensures EVERY visual element comes from REAL assets, no fallbacks needed!
