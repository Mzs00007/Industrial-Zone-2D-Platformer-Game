/*
 * rendering/PanelRenderer.java
 *
 * Draws nine-patch framed panels and icon-decorated buttons for the
 * Industrial Zone game's GUI screens.
 *
 * Panel anatomy (nine-patch layout):
 *   ┌─────────────────────────┐
 *   │ TL │  top edge  │  TR   │
 *   ├────┼────────────┼───────┤
 *   │    │            │       │
 *   │ L  │  fill navy │  R    │  ← tiled fill texture
 *   │    │            │       │
 *   ├────┼────────────┼───────┤
 *   │ BL │  bot edge  │  BR   │
 *   └─────────────────────────┘
 *
 * Button anatomy:
 *   ┌──────────────────────────────────────┐
 *   │ [icon] LABEL TEXT              [bdr] │
 *   └──────────────────────────────────────┘
 *   State 0=normal (red icon), 1=hover (green icon + teal border),
 *   2=pressed (blue icon + bright border)
 *
 * Usage:
 *   PanelRenderer panel = new PanelRenderer(
 *       frmCornerTL, frmCornerTR, frmCornerBL, frmCornerBR,
 *       frmEdgeTop, frmEdgeBot, frmEdgeLeft, frmEdgeRight,
 *       frmFillNavy, btnColors, btnNormal, btnHover, btnPressed, hudFont);
 *
 *   panel.drawPanel(g, x, y, w, h);
 *   panel.drawButton(g, x, y, w, h, "START", 0);
 *   panel.drawButton(g, x, y, w, h, "EXIT",  1);   // hover state
 */
package rendering;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class PanelRenderer {

    // ── nine-patch panel images ───────────────────────────────────────────────
    private final BufferedImage frmCornerTL, frmCornerTR, frmCornerBL, frmCornerBR;
    private final BufferedImage frmEdgeTop, frmEdgeBot, frmEdgeLeft, frmEdgeRight;
    private final BufferedImage frmFillNavy;

    // ── button images ─────────────────────────────────────────────────────────
    /** 10-slot array of button background colour patches; slot [5] is used as default. */
    private final BufferedImage[] btnColors;
    /** Small icon drawn at left of button to indicate interaction state. */
    private final BufferedImage btnNormal, btnHover, btnPressed;

    /** Font used for button labels. */
    private final Font hudFont;

    // ── layout constants ──────────────────────────────────────────────────────
    /** Size of the corner images in pixels. */
    private static final int CORNER_SIZE = 40;
    /** Thickness of edge strips in pixels. */
    private static final int EDGE_SIZE   = 8;

    // ── colour constants ──────────────────────────────────────────────────────
    private static final Color CLR_FILL_FALLBACK   = new Color(12,  14,  30, 230);
    private static final Color CLR_BTN_NORMAL      = new Color(15,  20,  50);
    private static final Color CLR_BTN_HOVER       = new Color(30,  50, 100);
    private static final Color CLR_ICON_NORMAL     = new Color(200, 50,  50);
    private static final Color CLR_ICON_HOVER      = new Color(60, 220,  60);
    private static final Color CLR_ICON_PRESSED    = new Color(60,  60, 220);
    private static final Color CLR_HIGHLIGHT_TOP   = new Color(0,  200, 255, 120);
    private static final Color CLR_BORDER_ACTIVE   = new Color(0,  180, 240, 200);
    private static final Color CLR_BORDER_NORMAL   = new Color(40,  50, 100, 180);
    private static final Color CLR_LABEL_HOVERED   = Color.WHITE;
    private static final Color CLR_LABEL_NORMAL    = new Color(200, 215, 240);

    // =========================================================================
    //  CONSTRUCTOR
    // =========================================================================

    /**
     * All parameters may be null — the renderer has coloured fallbacks for
     * every missing image so the game still functions without a full asset load.
     */
    public PanelRenderer(BufferedImage frmCornerTL, BufferedImage frmCornerTR,
                         BufferedImage frmCornerBL, BufferedImage frmCornerBR,
                         BufferedImage frmEdgeTop,  BufferedImage frmEdgeBot,
                         BufferedImage frmEdgeLeft, BufferedImage frmEdgeRight,
                         BufferedImage frmFillNavy,
                         BufferedImage[] btnColors,
                         BufferedImage btnNormal, BufferedImage btnHover, BufferedImage btnPressed,
                         Font hudFont) {
        this.frmCornerTL = frmCornerTL;
        this.frmCornerTR = frmCornerTR;
        this.frmCornerBL = frmCornerBL;
        this.frmCornerBR = frmCornerBR;
        this.frmEdgeTop  = frmEdgeTop;
        this.frmEdgeBot  = frmEdgeBot;
        this.frmEdgeLeft = frmEdgeLeft;
        this.frmEdgeRight= frmEdgeRight;
        this.frmFillNavy = frmFillNavy;
        this.btnColors   = (btnColors != null) ? btnColors : new BufferedImage[10];
        this.btnNormal   = btnNormal;
        this.btnHover    = btnHover;
        this.btnPressed  = btnPressed;
        this.hudFont     = (hudFont != null) ? hudFont : new Font("Courier New", Font.BOLD, 14);
    }

    // =========================================================================
    //  DRAW PANEL  — nine-patch framed rectangle
    // =========================================================================

    /**
     * Draws a nine-patch framed panel at the given screen rect.
     *
     * @param g Graphics2D context
     * @param x left edge
     * @param y top edge
     * @param w total width
     * @param h total height
     */
    public void drawPanel(Graphics2D g, int x, int y, int w, int h) {
        int cs = CORNER_SIZE;
        int es = EDGE_SIZE;

        // ── interior fill ────────────────────────────────────────────────────
        if (frmFillNavy != null) {
            int fw = frmFillNavy.getWidth(), fh = frmFillNavy.getHeight();
            if (fw > 0 && fh > 0) {
                for (int tx = x + cs; tx < x + w - cs; tx += fw) {
                    for (int ty = y + cs; ty < y + h - cs; ty += fh) {
                        int dw = Math.min(fw, (x + w - cs) - tx);
                        int dh = Math.min(fh, (y + h - cs) - ty);
                        g.drawImage(frmFillNavy, tx, ty, tx + dw, ty + dh,
                                    0, 0, dw, dh, null);
                    }
                }
            }
        } else {
            g.setColor(CLR_FILL_FALLBACK);
            g.fillRect(x + cs, y + cs, w - cs * 2, h - cs * 2);
        }

        // ── top edge (tiled horizontally) ────────────────────────────────────
        if (frmEdgeTop != null) {
            int ew = frmEdgeTop.getWidth();
            for (int tx = x + cs; tx < x + w - cs; tx += ew) {
                int dw = Math.min(ew, (x + w - cs) - tx);
                g.drawImage(frmEdgeTop, tx, y, tx + dw, y + es,
                            0, 0, dw, frmEdgeTop.getHeight(), null);
            }
        }

        // ── bottom edge (tiled horizontally) ─────────────────────────────────
        if (frmEdgeBot != null) {
            int ew = frmEdgeBot.getWidth();
            for (int tx = x + cs; tx < x + w - cs; tx += ew) {
                int dw = Math.min(ew, (x + w - cs) - tx);
                g.drawImage(frmEdgeBot, tx, y + h - es, tx + dw, y + h,
                            0, 0, dw, frmEdgeBot.getHeight(), null);
            }
        }

        // ── left edge (tiled vertically) ─────────────────────────────────────
        if (frmEdgeLeft != null) {
            int eh = frmEdgeLeft.getHeight();
            for (int ty = y + cs; ty < y + h - cs; ty += eh) {
                int dh = Math.min(eh, (y + h - cs) - ty);
                g.drawImage(frmEdgeLeft, x, ty, x + es, ty + dh,
                            0, 0, frmEdgeLeft.getWidth(), dh, null);
            }
        }

        // ── right edge (tiled vertically) ────────────────────────────────────
        if (frmEdgeRight != null) {
            int eh = frmEdgeRight.getHeight();
            for (int ty = y + cs; ty < y + h - cs; ty += eh) {
                int dh = Math.min(eh, (y + h - cs) - ty);
                g.drawImage(frmEdgeRight, x + w - es, ty, x + w, ty + dh,
                            0, 0, frmEdgeRight.getWidth(), dh, null);
            }
        }

        // ── corners ───────────────────────────────────────────────────────────
        if (frmCornerTL != null) g.drawImage(frmCornerTL, x,         y,          x + cs, y + cs, null);
        if (frmCornerTR != null) g.drawImage(frmCornerTR, x + w - cs, y,         x + w,  y + cs, null);
        if (frmCornerBL != null) g.drawImage(frmCornerBL, x,          y + h - cs, x + cs, y + h, null);
        if (frmCornerBR != null) g.drawImage(frmCornerBR, x + w - cs, y + h - cs, x + w,  y + h, null);
    }

    // =========================================================================
    //  DRAW BUTTON  — state-aware button (normal / hover / pressed)
    // =========================================================================

    /**
     * Draws a GUI button.
     *
     * @param g     Graphics2D context
     * @param x     left edge
     * @param y     top edge
     * @param w     width
     * @param h     height
     * @param label button text (centred)
     * @param state 0=normal, 1=hover, 2=pressed
     */
    public void drawButton(Graphics2D g, int x, int y, int w, int h, String label, int state) {
        // ── background ───────────────────────────────────────────────────────
        BufferedImage bg = safeGet(btnColors, 5);
        if (bg == null) bg = safeGet(btnColors, 0);
        if (bg != null) {
            g.drawImage(bg, x, y, w, h, null);
        } else {
            g.setColor(state == 1 ? CLR_BTN_HOVER : CLR_BTN_NORMAL);
            g.fillRect(x, y, w, h);
        }

        // ── state icon (small PNG on left side) ──────────────────────────────
        BufferedImage icon = (state == 2) ? btnPressed : (state == 1) ? btnHover : btnNormal;
        if (icon != null) {
            g.drawImage(icon, x + 6, y + (h - 16) / 2, 16, 16, null);
        } else {
            // Fallback: coloured accent bar
            g.setColor(state == 1 ? CLR_ICON_HOVER
                     : state == 2 ? CLR_ICON_PRESSED
                                  : CLR_ICON_NORMAL);
            g.fillRect(x + 4, y + 4, 4, h - 8);
        }

        // ── top highlight on hover / press ───────────────────────────────────
        if (state >= 1) {
            g.setColor(CLR_HIGHLIGHT_TOP);
            g.fillRect(x, y, w, 2);
        }

        // ── label — centred ──────────────────────────────────────────────────
        Font lf = (state >= 1) ? hudFont.deriveFont(Font.BOLD,  16f)
                                : hudFont.deriveFont(Font.PLAIN, 15f);
        g.setFont(lf);
        g.setColor(state == 1 ? CLR_LABEL_HOVERED : CLR_LABEL_NORMAL);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(label,
                     x + (w - fm.stringWidth(label)) / 2,
                     y + (h + fm.getAscent() - fm.getDescent()) / 2);

        // ── border ───────────────────────────────────────────────────────────
        g.setColor(state >= 1 ? CLR_BORDER_ACTIVE : CLR_BORDER_NORMAL);
        g.drawRect(x, y, w - 1, h - 1);
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    /** Null-safe array access. Returns null if array is null or index out of bounds. */
    private static BufferedImage safeGet(BufferedImage[] arr, int idx) {
        if (arr == null || idx < 0 || idx >= arr.length) return null;
        return arr[idx];
    }
}
