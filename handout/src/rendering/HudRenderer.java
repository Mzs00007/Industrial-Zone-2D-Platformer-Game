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
        // background strip
        g.setColor(CLR_BAR_BG);
        g.fillRect(0, 0, W, 48);

        // teal accent line at very top
        g.setColor(CLR_TEAL_ACCENT);
        g.fillRect(0, 0, W, 2);

        // divider image tiled along position y=44
        if (frmDivider != null) {
            int dw = frmDivider.getWidth();
            int dh = Math.min(frmDivider.getHeight(), 4);
            for (int x = 0; x < W; x += dw)
                g.drawImage(frmDivider, x, 44, Math.min(dw, W - x), dh, null);
        }

        g.setFont(hudFont);

        // Score — left aligned
        g.setColor(CLR_SCORE);
        g.drawString(String.format("SCORE  %07d", score), 14, 30);

        // Level indicator — centred
        g.setColor(Color.WHITE);
        String lvl = "LEVEL " + level + " / 2";
        FontMetrics fm = g.getFontMetrics();
        g.drawString(lvl, (W - fm.stringWidth(lvl)) / 2, 30);

        // Timer — right aligned
        long secs = (System.currentTimeMillis() - startTime) / 1000;
        g.setColor(CLR_TIMER);
        String timeStr = String.format("TIME  %02d:%02d", secs / 60, secs % 60);
        g.drawString(timeStr, W - 140, 30);

        // FPS — tiny text bottom-right, shown if fps > 0
        if (fps > 0) {
            g.setFont(new Font("Courier New", Font.PLAIN, 10));
            g.setColor(CLR_FPS);
            g.drawString(String.format("FPS:%.0f", fps), W - 52, 62);
        }
    }

    // ── bottom bar ────────────────────────────────────────────────────────────

    private void drawBottomBar(Graphics2D g, int W, int H, int hp, int maxHp, int enemyCount) {
        // background strip
        g.setColor(CLR_BAR_BG);
        g.fillRect(0, H - 68, W, 68);

        // teal accent line at top of bottom bar
        g.setColor(CLR_TEAL_ACCENT);
        g.fillRect(0, H - 68, W, 2);

        // ── health bar ──────────────────────────────────────────────────────
        int hpBarW = 220, hpBarH = 22;
        int hpBarX = 14,  hpBarY = H - 52;
        drawBarImage(g, hp, maxHp, hudBars, hpBarX, hpBarY, hpBarW, hpBarH);

        g.setFont(hudFont);
        g.setColor(CLR_HP_LABEL);
        g.drawString("HP", hpBarX, H - 56);

        // ── energy bar ──────────────────────────────────────────────────────
        int enBarX = hpBarX + hpBarW + 12;
        drawBarImage(g, hp, maxHp, enerBars, enBarX, hpBarY, 180, hpBarH);

        g.setColor(CLR_EN_LABEL);
        g.drawString("EN", enBarX, H - 56);

        // ── enemy count ─────────────────────────────────────────────────────
        g.setFont(new Font("Courier New", Font.BOLD, 13));
        g.setColor(CLR_ENEMY);
        g.drawString("ENEMIES: " + enemyCount, enBarX + 200, H - 38);

        // ── controls hint ───────────────────────────────────────────────────
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.setColor(CLR_HINT);
        g.drawString(
            "A/D: Move  |  SPACE: Jump  |  SHIFT: Dash  |  CTRL: Shoot  |  ESC: Pause",
            14, H - 8);
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
