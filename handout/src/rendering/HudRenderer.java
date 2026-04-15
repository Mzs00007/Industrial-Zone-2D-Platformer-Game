/*
 * rendering/HudRenderer.java
 *
 * Draws the complete game HUD (Heads-Up Display) for the Industrial Zone platformer.
 *
 * The HUD has two bars:
 *   TOP BAR  — score, level indicator, elapsed timer, FPS
 *   BOTTOM BAR — HP bar image, EN bar image, enemy count, controls hint
 *
 * All images are passed in via the constructor so this class is fully
 * self-contained and testable without a Game instance.
 *
 * Usage from Game.java:
 *   HudRenderer hud = new HudRenderer(hudBars, enerBars, digitImgs, frmDivider, hudFont);
 *   // every paint call:
 *   hud.draw(g, W, H,
 *             player.getHealth(), player.getMaxHealth(),
 *             score, startTime, currentLevel, enemies.size(), fps);
 */
package rendering;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class HudRenderer {

    // ── assets stored at construction time ────────────────────────────────────
    /** 7 health-bar images indexed by percentage band:
     *  0=100%, 1=80%, 2=60%, 3=40%, 4=20%, 5>0%, 6=empty  */
    private final BufferedImage[] hudBars;

    /** Same structure as hudBars but for the energy bar. */
    private final BufferedImage[] enerBars;

    /** Digit images 0-9 for on-screen number rendering. */
    private final BufferedImage[] digitImgs;

    /** Thin teal divider image tiled along the bottom of the top bar. */
    private final BufferedImage frmDivider;

    /** Game-branded OTF font (falls back to Courier New if null). */
    private final Font hudFont;

    // ── optional icon images (set via setIcons after construction) ───────────
    private BufferedImage iconHp, iconEn, iconStar, iconSkull;

    // ── colour constants ───────────────────────────────────────────────────────
    private static final Color CLR_BAR_BG      = new Color(6,   6,  18, 230);
    private static final Color CLR_TEAL_ACCENT = new Color(0, 200, 240);
    private static final Color CLR_SCORE       = new Color(0, 220, 255);
    private static final Color CLR_TIMER       = new Color(180, 180, 200);
    private static final Color CLR_FPS         = new Color(80, 80, 110);
    private static final Color CLR_HP_LABEL    = new Color(200, 220, 255);
    private static final Color CLR_EN_LABEL    = new Color(100, 220, 255);
    private static final Color CLR_ENEMY       = new Color(255, 80, 80);
    private static final Color CLR_HINT        = new Color(110, 110, 135);

    // =========================================================================
    //  CONSTRUCTOR
    // =========================================================================

    /**
     * @param hudBars    7-slot health-bar image array (null slots tolerated)
     * @param enerBars   7-slot energy-bar image array (null slots tolerated)
     * @param digitImgs  10-slot digit image array 0-9 (null slots tolerated)
     * @param frmDivider teal horizontal divider PNG (may be null)
     * @param hudFont    branded font for all HUD text (may be null → fallback)
     */
    public HudRenderer(BufferedImage[] hudBars, BufferedImage[] enerBars,
                       BufferedImage[] digitImgs, BufferedImage frmDivider,
                       Font hudFont) {
        this.hudBars    = (hudBars    != null) ? hudBars    : new BufferedImage[7];
        this.enerBars   = (enerBars   != null) ? enerBars   : new BufferedImage[7];
        this.digitImgs  = (digitImgs  != null) ? digitImgs  : new BufferedImage[10];
        this.frmDivider = frmDivider;
        this.hudFont    = (hudFont    != null) ? hudFont    : new Font("Courier New", Font.BOLD, 14);
    }

    /** Optionally supply icon images beside HP, EN, score and enemy labels. Any may be null. */
    public void setIcons(BufferedImage hp, BufferedImage en,
                         BufferedImage star, BufferedImage skull) {
        this.iconHp    = hp;
        this.iconEn    = en;
        this.iconStar  = star;
        this.iconSkull = skull;
    }

    // =========================================================================
    //  DRAW  — call once per frame after all gameplay content
    // =========================================================================


    /**
     * Renders the full HUD overlay.
     *
     * @param g           Graphics2D context
     * @param W           panel width
     * @param H           panel height
     * @param hp          player current health
     * @param maxHp       player maximum health
     * @param score       current score value
     * @param startTime   System.currentTimeMillis() when the game/level began
     * @param level       current level number (1 or 2)
     * @param enemyCount  number of active enemies on screen
     * @param fps         current frames per second (pass 0 to hide)
     */
    public void draw(Graphics2D g, int W, int H,
                     int hp, int maxHp, int score,
                     long startTime, int level, int enemyCount, double fps) {

        drawTopBar(g, W, score, level, startTime, fps);
        drawBottomBar(g, W, H, hp, maxHp, enemyCount);
    }

    // ── top bar ───────────────────────────────────────────────────────────────

    private void drawTopBar(Graphics2D g, int W, int score, int level,
                            long startTime, double fps) {
        // Background strip — 52 px tall
        g.setColor(CLR_BAR_BG);
        g.fillRect(0, 0, W, 52);

        // Teal accent at very top (2 px)
        g.setColor(CLR_TEAL_ACCENT);
        g.fillRect(0, 0, W, 2);

        // Divider tiled at bottom of top bar (y≈46)
        if (frmDivider != null) {
            int dw = frmDivider.getWidth(), dh = frmDivider.getHeight();
            for (int x = 0; x < W; x += dw)
                g.drawImage(frmDivider, x, 46, Math.min(dw, W - x), Math.min(dh, 6), null);
        } else {
            g.setColor(CLR_TEAL_ACCENT);
            g.fillRect(0, 48, W, 2);
        }

        FontMetrics fm;

        // ── SCORE (left) — star icon + styled digit PNGs ──────────────────────
        int scoreX = 14;
        if (iconStar != null)
            g.drawImage(iconStar, scoreX, 4, 18, 18, null);
        g.setFont(hudFont.deriveFont(Font.BOLD, 10f));
        g.setColor(new Color(170, 170, 195));
        g.drawString("SCORE", scoreX + (iconStar != null ? 22 : 0), 14);
        drawDigits(g, scoreX + (iconStar != null ? 22 : 0), 16, score, 14, 20);

        // ── LEVEL (centre) ────────────────────────────────────────────────────
        g.setFont(hudFont.deriveFont(Font.BOLD, 16f));
        g.setColor(Color.WHITE);
        String lvlStr = "LEVEL  " + level + "  /  2";
        fm = g.getFontMetrics();
        int lvlTxtX = (W - fm.stringWidth(lvlStr)) / 2;
        g.drawString(lvlStr, lvlTxtX, 34);
        // accent dots either side
        g.setColor(CLR_TEAL_ACCENT);
        g.fillRect(lvlTxtX - 10, 28, 4, 4);
        g.fillRect(lvlTxtX + fm.stringWidth(lvlStr) + 6, 28, 4, 4);

        // ── TIMER (right) ─────────────────────────────────────────────────────
        long secs = (System.currentTimeMillis() - startTime) / 1000;
        int timeRX = W - 118;
        g.setFont(hudFont.deriveFont(Font.PLAIN, 10f));
        g.setColor(new Color(130, 130, 155));
        g.drawString("TIME", timeRX, 14);
        g.setFont(hudFont.deriveFont(Font.BOLD, 15f));
        g.setColor(CLR_TIMER);
        g.drawString(String.format("%02d : %02d", secs / 60, secs % 60), timeRX, 36);

        // ── FPS (tiny, corner) ────────────────────────────────────────────────
        if (fps > 0) {
            g.setFont(new Font("Courier New", Font.PLAIN, 10));
            g.setColor(CLR_FPS);
            g.drawString(String.format("%.0f fps", fps), W - 46, 60);
        }
    }

    // ── bottom bar ────────────────────────────────────────────────────────────

    private void drawBottomBar(Graphics2D g, int W, int H, int hp, int maxHp, int enemyCount) {
        final int PANEL_H = 80;
        final int barY    = H - 52;   // top of bar sprites
        final int barH    = 26;        // height of each bar sprite
        final int labelY  = barY - 6;  // label baseline (just above bars)

        // Background strip
        g.setColor(CLR_BAR_BG);
        g.fillRect(0, H - PANEL_H, W, PANEL_H);

        // Divider / accent image at very top of bottom panel
        if (frmDivider != null) {
            int dw = frmDivider.getWidth(), dh = frmDivider.getHeight();
            for (int x = 0; x < W; x += dw)
                g.drawImage(frmDivider, x, H - PANEL_H, Math.min(dw, W - x), Math.min(dh, 6), null);
        } else {
            g.setColor(CLR_TEAL_ACCENT);
            g.fillRect(0, H - PANEL_H, W, 2);
        }

        // Critical health flash — pulsing red vignette when HP < 20%
        if (maxHp > 0 && hp > 0 && (float) hp / maxHp < 0.20f) {
            float pulse = (float) Math.abs(Math.sin(System.currentTimeMillis() * 0.004)) * 0.28f;
            g.setColor(new Color(200, 0, 0, (int)(pulse * 255)));
            g.fillRect(0, 0, W, H);
        }

        // ── HP bar ────────────────────────────────────────────────────────────
        final int hpBarW = 260, hpBarX = 14;
        if (iconHp != null)
            g.drawImage(iconHp, hpBarX, labelY - 12, 14, 14, null);
        g.setFont(hudFont.deriveFont(Font.BOLD, 11f));
        g.setColor(CLR_HP_LABEL);
        g.drawString("HP", hpBarX + (iconHp != null ? 18 : 0), labelY);
        g.setFont(hudFont.deriveFont(Font.PLAIN, 10f));
        g.setColor(new Color(255, 200, 200));
        g.drawString(hp + " / " + maxHp,
                     hpBarX + (iconHp != null ? 18 : 0) + 22, labelY);
        drawBarImage(g, hp, maxHp, hudBars, hpBarX, barY, hpBarW, barH);
        // Percent text centred on bar
        if (maxHp > 0) {
            int pct = (int)((float) hp / maxHp * 100);
            g.setFont(hudFont.deriveFont(Font.BOLD, 10f));
            g.setColor(new Color(255, 255, 255, 200));
            String ps = pct + "%";
            FontMetrics fm = g.getFontMetrics();
            g.drawString(ps, hpBarX + (hpBarW - fm.stringWidth(ps)) / 2, barY + barH - 5);
        }

        // ── EN bar ────────────────────────────────────────────────────────────
        final int enBarW = 200, enBarX = hpBarX + hpBarW + 16;
        if (iconEn != null)
            g.drawImage(iconEn, enBarX, labelY - 12, 14, 14, null);
        g.setFont(hudFont.deriveFont(Font.BOLD, 11f));
        g.setColor(CLR_EN_LABEL);
        g.drawString("EN", enBarX + (iconEn != null ? 18 : 0), labelY);
        drawBarImage(g, hp, maxHp, enerBars, enBarX, barY, enBarW, barH);

        // ── Enemy count ───────────────────────────────────────────────────────
        final int ecX = enBarX + enBarW + 20;
        if (iconSkull != null)
            g.drawImage(iconSkull, ecX, labelY - 12, 14, 14, null);
        g.setFont(hudFont.deriveFont(Font.BOLD, 14f));
        g.setColor(CLR_ENEMY);
        g.drawString("x" + enemyCount, ecX + (iconSkull != null ? 18 : 0), labelY + 1);
        g.setFont(hudFont.deriveFont(Font.PLAIN, 9f));
        g.setColor(new Color(180, 80, 80));
        g.drawString("enemies", ecX + (iconSkull != null ? 18 : 0), barY + barH - 5);

        // ── Controls hint (bottom edge) ───────────────────────────────────────
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.setColor(CLR_HINT);
        g.drawString(
            "A/D: Move  |  SPACE: Jump  |  SHIFT: Dash  |  CTRL: Shoot  |  H: Heal  |  ESC: Pause",
            hpBarX, H - 5);
    }

    // =========================================================================
    //  HELPERS
    // =========================================================================

    /**
     * Draws the appropriate bar image (hudBars or enerBars) scaled to the given rect.
     * Falls back to a plain coloured rectangle if the image array is empty.
     */
    public void drawBarImage(Graphics2D g, int cur, int max, BufferedImage[] bars,
                              int x, int y, int w, int h) {
        if (bars == null || bars.length == 0) {
            // Plain coloured fallback — no image loaded
            g.setColor(new Color(30, 60, 30));
            g.fillRect(x, y, w, h);
            if (max > 0) {
                int fw = (int)((float) cur / max * w);
                g.setColor(new Color(0, 180, 60));
                g.fillRect(x, y, Math.max(0, fw), h);
            }
            return;
        }
        float pct = (max > 0) ? (float) cur / max : 0f;
        int idx;
        if      (pct >= 0.99f) idx = 0;
        else if (pct >= 0.80f) idx = 1;
        else if (pct >= 0.60f) idx = 2;
        else if (pct >= 0.40f) idx = 3;
        else if (pct >= 0.20f) idx = 4;
        else if (pct >  0.0f)  idx = 5;
        else                    idx = 6;

        idx = Math.min(idx, bars.length - 1);
        if (bars[idx] != null)
            g.drawImage(bars[idx], x, y, w, h, null);
    }

    /**
     * Renders a non-negative integer using the digit PNG images.
     * Each digit image is drawn at (x + i*(digitW+2), y).
     *
     * @param g      Graphics2D context
     * @param x      left edge of the digit string
     * @param y      top edge
     * @param value  value to render (clamped to ≥ 0)
     * @param digitW width to draw each digit
     * @param digitH height to draw each digit
     */
    public void drawDigits(Graphics2D g, int x, int y, int value, int digitW, int digitH) {
        String s = Integer.toString(Math.max(0, value));
        for (int i = 0; i < s.length(); i++) {
            int d = s.charAt(i) - '0';
            if (d >= 0 && d < digitImgs.length && digitImgs[d] != null) {
                g.drawImage(digitImgs[d], x + i * (digitW + 2), y, digitW, digitH, null);
            } else {
                // Fallback: plain text digit when image is missing
                g.setColor(CLR_SCORE);
                g.setFont(new Font("Courier New", Font.BOLD, digitH));
                g.drawString(String.valueOf(d), x + i * (digitW + 2), y + digitH);
            }
        }
    }
}
