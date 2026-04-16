package controllers;

import managers.HUDManager;
import managers.EmoteManager;
import managers.HealSystem;
import rendering.HudRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * EnhancedHUDRenderer — Renders comprehensive HUD using HUDManager data
 * 
 * Displays all 8 HUD elements with modern layout:
 * 1. Character name + level (top-left)
 * 2. Health bar with background panel
 * 3. Weapon/ammo display (top-right)
 * 4. Collectible counters (cash/cards)
 * 5. Enemy health bar (when in combat)
 * 6. Combo counter (center area)
 * 7. Area name/checkpoint indicator (bottom-left)
 * 8. FPS counter (debug toggle)
 */
public class EnhancedHUDRenderer {

    private HUDManager hudManager;
    private EmoteManager emoteManager;
    private HealSystem healSystem;
    private HudRenderer baseRenderer;  // existing HUD bars, fonts, icons
    private BufferedImage healthbarFillImage;
    private BufferedImage healthbarBackImage;
    
    // ===== COSMETICS =====
    private final Color COLOR_ACCENT       = new Color(0, 200, 255);     // cyan
    private final Color COLOR_DANGER       = new Color(255, 80, 80);     // red
    private final Color COLOR_SUCCESS      = new Color(80, 255, 100);    // green
    private final Color COLOR_TEXT         = new Color(220, 220, 240);   // light blue-white
    private final Color COLOR_TEXT_DARK    = new Color(100, 100, 120);   // muted
    private final Color COLOR_PANEL_BG     = new Color(10, 10, 30, 220); // dark navy
    private final Color COLOR_PANEL_BORDER = new Color(0, 180, 255, 180); // cyan border
    
    // ===== ANIMATION STATE =====
    private float comboPulseTimer = 0f;
    private static final float COMBO_PULSE_SPEED = 0.1f;
    private float healthDamageFlash = 0f;
    private static final float HEALTH_FLASH_DURATION = 0.2f;
    
    public EnhancedHUDRenderer(HUDManager hudManager, HudRenderer baseRenderer) {
        this(hudManager, null, null, baseRenderer);
    }
    
    public EnhancedHUDRenderer(HUDManager hudManager, EmoteManager emoteManager, HudRenderer baseRenderer) {
        this(hudManager, emoteManager, null, baseRenderer);
    }
    
    public EnhancedHUDRenderer(HUDManager hudManager, EmoteManager emoteManager, HealSystem healSystem, HudRenderer baseRenderer) {
        this.hudManager = hudManager;
        this.emoteManager = emoteManager;
        this.healSystem = healSystem;
        this.baseRenderer = baseRenderer;
    }
    
    public void update(float delta) {
        // Combo pulse animation
        if (hudManager.hasActiveCombo()) {
            comboPulseTimer += delta;
            if (comboPulseTimer >= COMBO_PULSE_SPEED * 2) {
                comboPulseTimer -= COMBO_PULSE_SPEED * 2;
            }
        } else {
            comboPulseTimer = 0f;
        }
        
        // Health damage flash (fades out)
        if (healthDamageFlash > 0) {
            healthDamageFlash -= delta;
        }
    }
    
    public void render(Graphics2D g, int W, int H) {
        if (hudManager == null) return;
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Render all HUD elements
        drawCharacterInfo(g, W, H);
        drawHealthBar(g, W, H);
        drawWeaponDisplay(g, W, H);
        drawCollectibles(g, W, H);
        drawEnemyHealthBar(g, W, H);
        drawComboCounter(g, W, H);
        drawAreaInfo(g, W, H);
        drawDebugInfo(g, W, H);
        drawEmoteIndicator(g, W, H);
        drawHealCooldown(g, W, H);
    }
    
    // ===== HUD ELEMENT RENDERERS =====
    
    private void drawCharacterInfo(Graphics2D g, int W, int H) {
        // Top-left: Character name + level
        int x = 16, y = 16;
        
        // Background panel
        g.setColor(COLOR_PANEL_BG);
        g.fillRoundRect(x, y, 180, 60, 8, 8);
        g.setColor(COLOR_PANEL_BORDER);
        g.setStroke(new BasicStroke(1.5f));
        g.drawRoundRect(x, y, 180, 60, 8, 8);
        
        // Character name (bold)
        g.setFont(new Font("Consolas", Font.BOLD, 14));
        g.setColor(COLOR_ACCENT);
        g.drawString(hudManager.getPlayerName(), x + 12, y + 24);
        
        // Level indicator
        g.setFont(new Font("Consolas", Font.PLAIN, 11));
        g.setColor(COLOR_TEXT_DARK);
        g.drawString("LEVEL " + hudManager.getCurrentLevel(), x + 12, y + 42);
    }
    
    private void drawHealthBar(Graphics2D g, int W, int H) {
        // Left side, below character info
        int x = 16, y = 84;
        int barW = 180, barH = 28;
        
        // Background panel
        g.setColor(COLOR_PANEL_BG);
        g.fillRoundRect(x, y, barW, barH, 6, 6);
        g.setColor(COLOR_PANEL_BORDER);
        g.setStroke(new BasicStroke(1.5f));
        g.drawRoundRect(x, y, barW, barH, 6, 6);
        
        // Health icon (fake heart icon)
        g.setColor(COLOR_DANGER);
        g.fillOval(x + 8, y + 8, 12, 12);
        
        // Health bar fill
        float healthPercent = hudManager.getHealthPercent();
        int fillW = (int)(barW - 32) * (int)(healthPercent * 100) / 100;
        
        // Flash on damage
        Color barColor = COLOR_SUCCESS;
        if (healthPercent < 0.3f) {
            barColor = COLOR_DANGER;
        } else if (healthPercent < 0.6f) {
            barColor = new Color(255, 200, 80); // orange
        }
        
        if (healthDamageFlash > 0) {
            float alpha = healthDamageFlash / HEALTH_FLASH_DURATION;
            barColor = new Color(
                Math.min(255, (int)(barColor.getRed() + 100 * alpha)),
                Math.min(255, (int)(barColor.getGreen() + 100 * alpha)),
                Math.min(255, (int)(barColor.getBlue() + 100 * alpha))
            );
        }
        
        g.setColor(barColor);
        g.fillRect(x + 26, y + 8, fillW, 12);
        
        // Bar border
        g.setColor(COLOR_TEXT_DARK);
        g.setStroke(new BasicStroke(1.0f));
        g.drawRect(x + 26, y + 8, barW - 32, 12);
        
        // Health text (HP / MaxHP)
        g.setFont(new Font("Consolas", Font.BOLD, 9));
        g.setColor(COLOR_TEXT);
        g.drawString(hudManager.getPlayerHealth() + "/" + hudManager.getPlayerMaxHealth(),
                     x + 28, y + 24);
    }
    
    private void drawWeaponDisplay(Graphics2D g, int W, int H) {
        // Top-right: Current weapon + ammo
        int x = W - 196, y = 16;
        int panelW = 180, panelH = 60;
        
        // Background panel
        g.setColor(COLOR_PANEL_BG);
        g.fillRoundRect(x, y, panelW, panelH, 8, 8);
        g.setColor(COLOR_PANEL_BORDER);
        g.setStroke(new BasicStroke(1.5f));
        g.drawRoundRect(x, y, panelW, panelH, 8, 8);
        
        // Weapon name (bold)
        g.setFont(new Font("Consolas", Font.BOLD, 12));
        g.setColor(COLOR_ACCENT);
        g.drawString(hudManager.getEquippedWeapon(), x + 12, y + 22);
        
        // Ammo display
        g.setFont(new Font("Consolas", Font.PLAIN, 11));
        g.setColor(COLOR_TEXT);
        String ammoDisplay = hudManager.getAmmoDisplay();
        g.drawString("AMMO: " + ammoDisplay, x + 12, y + 42);
    }
    
    private void drawCollectibles(Graphics2D g, int W, int H) {
        // Bottom-right: Cash + Cards collected
        int x = W - 196, y = H - 84;
        int panelW = 180, panelH = 68;
        
        // Background panel
        g.setColor(COLOR_PANEL_BG);
        g.fillRoundRect(x, y, panelW, panelH, 8, 8);
        g.setColor(COLOR_PANEL_BORDER);
        g.setStroke(new BasicStroke(1.5f));
        g.drawRoundRect(x, y, panelW, panelH, 8, 8);
        
        // Cash counter
        g.setFont(new Font("Consolas", Font.BOLD, 11));
        g.setColor(new Color(200, 180, 80));  // gold
        g.drawString("CASH", x + 12, y + 22);
        
        g.setFont(new Font("Consolas", Font.PLAIN, 10));
        g.setColor(COLOR_TEXT);
        g.drawString("$ " + hudManager.getCashCollected(), x + 12, y + 36);
        
        // Cards counter
        g.setFont(new Font("Consolas", Font.BOLD, 11));
        g.setColor(new Color(100, 150, 255));  // blue
        g.drawString("CARDS", x + 12, y + 52);
        
        g.setFont(new Font("Consolas", Font.PLAIN, 10));
        g.setColor(COLOR_TEXT);
        g.drawString("" + hudManager.getCardsCollected(), x + 12, y + 66);
    }
    
    private void drawEnemyHealthBar(Graphics2D g, int W, int H) {
        // Only show if targeting an enemy
        if (hudManager.getTargetEnemy() == null) return;
        
        // Center-bottom area
        int x = (W - 200) / 2, y = H - 120;
        int barW = 200, barH = 36;
        
        // Background panel
        g.setColor(COLOR_PANEL_BG);
        g.fillRoundRect(x, y, barW, barH, 8, 8);
        g.setColor(COLOR_DANGER);
        g.setStroke(new BasicStroke(2.0f));
        g.drawRoundRect(x, y, barW, barH, 8, 8);
        
        // Enemy type label
        g.setFont(new Font("Consolas", Font.BOLD, 11));
        g.setColor(COLOR_DANGER);
        g.drawString(hudManager.getTargetEnemyType(), x + 12, y + 16);
        
        // Enemy health bar
        float targetHealthPercent = hudManager.getTargetHealthPercent();
        int fillW = (int)((barW - 24) * targetHealthPercent);
        
        g.setColor(COLOR_DANGER);
        g.fillRect(x + 12, y + 20, fillW, 10);
        
        g.setColor(COLOR_TEXT_DARK);
        g.setStroke(new BasicStroke(1.0f));
        g.drawRect(x + 12, y + 20, barW - 24, 10);
        
        // Health text
        g.setFont(new Font("Consolas", Font.PLAIN, 9));
        g.setColor(COLOR_TEXT);
        g.drawString(hudManager.getTargetEnemyHealth() + "/" + hudManager.getTargetEnemyMaxHealth(),
                     x + barW - 60, y + 32);
    }
    
    private void drawComboCounter(Graphics2D g, int W, int H) {
        // Only show if active combo
        if (!hudManager.hasActiveCombo()) return;
        
        int comboCount = hudManager.getComboCounter();
        
        // Center area, above enemy health bar
        int x = (W - 140) / 2, y = H - 160;
        
        // Pulsing background
        float pulse = (float) Math.abs(Math.sin(comboPulseTimer / COMBO_PULSE_SPEED * Math.PI));
        int pulseAlpha = (int)(100 + pulse * 120);
        
        g.setColor(new Color(255, 200, 0, pulseAlpha));
        g.fillOval(x - 40, y - 40, 80, 80);
        
        // Border glow
        g.setColor(new Color(255, 255, 100, (int)(200 + pulse * 55)));
        g.setStroke(new BasicStroke(2.0f));
        g.drawOval(x - 40, y - 40, 80, 80);
        
        // Combo text
        g.setFont(new Font("Consolas", Font.BOLD, 20));
        g.setColor(COLOR_TEXT);
        FontMetrics fm = g.getFontMetrics();
        String comboText = "COMBO " + comboCount + "x";
        int tw = fm.stringWidth(comboText);
        g.drawString(comboText, x - tw / 2, y + 8);
        
        // Combo timer indicator (small bar below)
        float comboPercent = hudManager.getComboTimer() / 2.5f;  // COMBO_DECAY_TIME = 2.5s
        int timerBarW = 100;
        int timerFillW = (int)(timerBarW * comboPercent);
        
        g.setColor(new Color(255, 200, 0, 100));
        g.fillRect(x - timerBarW / 2, y + 24, timerBarW, 4);
        g.setColor(new Color(255, 220, 0, 200));
        g.fillRect(x - timerBarW / 2, y + 24, timerFillW, 4);
    }
    
    private void drawAreaInfo(Graphics2D g, int W, int H) {
        // Bottom-left: Area name + checkpoint
        int x = 16, y = H - 84;
        int panelW = 180, panelH = 68;
        
        // Background panel
        g.setColor(COLOR_PANEL_BG);
        g.fillRoundRect(x, y, panelW, panelH, 8, 8);
        g.setColor(COLOR_PANEL_BORDER);
        g.setStroke(new BasicStroke(1.5f));
        g.drawRoundRect(x, y, panelW, panelH, 8, 8);
        
        // Area label
        g.setFont(new Font("Consolas", Font.BOLD, 11));
        g.setColor(COLOR_ACCENT);
        g.drawString(hudManager.getAreaName(), x + 12, y + 22);
        
        // Level name
        g.setFont(new Font("Consolas", Font.PLAIN, 9));
        g.setColor(COLOR_TEXT_DARK);
        g.drawString(hudManager.getLevelName(), x + 12, y + 36);
        
        // Checkpoint number
        g.setFont(new Font("Consolas", Font.BOLD, 10));
        g.setColor(COLOR_SUCCESS);
        g.drawString("✓ CP #" + (hudManager.getCurrentCheckpoint() + 1), x + 12, y + 52);
        
        // Score display
        g.setFont(new Font("Consolas", Font.PLAIN, 9));
        g.setColor(COLOR_TEXT);
        g.drawString("Score: " + hudManager.getTotalScore(), x + 12, y + 66);
    }
    
    private void drawDebugInfo(Graphics2D g, int W, int H) {
        if (!hudManager.isShowingDebugInfo()) return;
        
        // Top-right corner (small text)
        int x = W - 140, y = 84;
        g.setFont(new Font("Monospaced", Font.PLAIN, 9));
        g.setColor(COLOR_TEXT_DARK);
        
        g.drawString("FPS: " + hudManager.getFramesPerSecond(), x, y);
        g.drawString("Combo Timer: " + String.format("%.1f", hudManager.getComboTimer()), x, y + 12);
        g.drawString("HUD Alpha: " + String.format("%.1f", hudManager.getHudAlpha()), x, y + 24);
    }
    
    private void drawEmoteIndicator(Graphics2D g, int W, int H) {
        if (emoteManager == null || !emoteManager.hasActiveEmote()) return;
        
        // Center-top area: emote name with progress bar
        String emoteName = emoteManager.getCurrentEmoteName();
        float progress = emoteManager.getEmoteProgress();
        
        int x = (W - 200) / 2, y = 36;
        int panelW = 200, panelH = 32;
        
        // Background panel
        g.setColor(COLOR_PANEL_BG);
        g.fillRoundRect(x, y, panelW, panelH, 8, 8);
        g.setColor(new Color(100, 200, 255, 180));  // bright cyan for emote active
        g.setStroke(new BasicStroke(2.0f));
        g.drawRoundRect(x, y, panelW, panelH, 8, 8);
        
        // Emote name
        g.setFont(new Font("Consolas", Font.BOLD, 12));
        g.setColor(new Color(100, 255, 200));  // bright green-cyan
        FontMetrics fm = g.getFontMetrics();
        int tw = fm.stringWidth(emoteName);
        g.drawString(emoteName, x + (panelW - tw) / 2, y + 18);
        
        // Progress bar below text
        int barW = panelW - 16, barH = 4;
        int barX = x + 8, barY = y + 24;
        
        // Background
        g.setColor(new Color(50, 50, 80, 160));
        g.fillRect(barX, barY, barW, barH);
        
        // Fill
        int fillW = (int)(barW * progress);
        g.setColor(new Color(100, 255, 200));
        g.fillRect(barX, barY, fillW, barH);
        
        // Border
        g.setColor(new Color(100, 200, 255));
        g.setStroke(new BasicStroke(1.0f));
        g.drawRect(barX, barY, barW, barH);
    }
    
    private void drawHealCooldown(Graphics2D g, int W, int H) {
        if (healSystem == null) return;
        
        // Bottom-right area: heal cooldown indicator
        int x = W - 160, y = H - 50;
        int panelW = 150, panelH = 40;
        
        // Background panel
        g.setColor(COLOR_PANEL_BG);
        g.fillRoundRect(x, y, panelW, panelH, 8, 8);
        g.setColor(healSystem.canHeal() ? new Color(100, 255, 150) : COLOR_ACCENT);
        g.setStroke(new BasicStroke(2.0f));
        g.drawRoundRect(x, y, panelW, panelH, 8, 8);
        
        // Heal icon (+ symbol)
        g.setColor(healSystem.canHeal() ? new Color(100, 255, 150) : COLOR_TEXT_DARK);
        g.setFont(new Font("Consolas", Font.BOLD, 16));
        g.drawString("+", x + 12, y + 22);
        
        // Heal status text
        String status = healSystem.getCooldownStatus();
        g.setFont(new Font("Consolas", Font.BOLD, 11));
        g.setColor(healSystem.canHeal() ? new Color(100, 255, 150) : COLOR_TEXT_DARK);
        g.drawString(status, x + 32, y + 18);
        
        // Cooldown progress bar
        float progress = healSystem.getCooldownProgress();
        int barW = panelW - 16, barH = 4;
        int barX = x + 8, barY = y + 28;
        
        g.setColor(new Color(50, 50, 80, 160));
        g.fillRect(barX, barY, barW, barH);
        
        int fillW = (int)(barW * progress);
        g.setColor(new Color(100, 255, 150));
        g.fillRect(barX, barY, fillW, barH);
        
        g.setColor(new Color(100, 200, 150));
        g.setStroke(new BasicStroke(1.0f));
        g.drawRect(barX, barY, barW, barH);
    }
    
    // ===== EVENT TRIGGERS =====
    
    public void onPlayerDamaged(int damage) {
        healthDamageFlash = HEALTH_FLASH_DURATION;
    }
    
    public void onComboIncrease() {
        comboPulseTimer = 0f;  // reset pulse animation
    }
    
    public void setShowDebugInfo(boolean show) {
        hudManager.setShowDebugInfo(show);
    }
}
