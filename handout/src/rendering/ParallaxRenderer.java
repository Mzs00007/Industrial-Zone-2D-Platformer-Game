/*
 * rendering/ParallaxRenderer.java
 *
 * Draws a multi-layer parallax background for both the gameplay world and
 * menu/GUI screens, using pre-loaded BufferedImage layers.
 *
 * Each layer is scrolled by a different fraction of the camera X position:
 *   scrollX[i] = camX * scrollFactors[i]
 * Layers are tiled horizontally so the seam is never visible.
 *
 * Usage:
 *   // Construct once with the layer images + scroll speed per layer
 *   BufferedImage[] layers = { far, mid, near, ground };
 *   float[]         factors = { 0.05f, 0.12f, 0.25f, 0.45f };
 *   ParallaxRenderer bg = new ParallaxRenderer(layers, factors, new Color(15,18,30));
 *
 *   // Draw every frame (camX advances via Timer / GameLoop)
 *   bg.draw(g2d, panelWidth, panelHeight, cameraX, 0.65f);
 *
 *   // For menu screens, drive camX yourself (menuCamX += speed * dt)
 *   bg.draw(g2d, W, H, menuCamX, 0.55f);
 */
package rendering;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public final class ParallaxRenderer {

    // ── state ─────────────────────────────────────────────────────────────────
    /** Parallax layer images in back-to-front order. Null slots are skipped. */
    private final BufferedImage[] layers;

    /**
     * Horizontal scroll factor per layer (0 = no scroll, 1 = locked to camera).
     * Length must match layers[].
     */
    private final float[] scrollFactors;

    /** Base sky / clear colour painted before any layer. */
    private final Color skyColor;

    // ── gradient overlay constants ────────────────────────────────────────────
    /** How tall the bottom gradient fade is, as a fraction of the panel height. */
    private static final float FADE_FRACTION = 0.18f;

    // =========================================================================
    //  CONSTRUCTOR
    // =========================================================================

    /**
     * @param layers        array of pre-loaded layer images (null entries OK)
     * @param scrollFactors parallax scroll fraction per layer (same length as layers)
     * @param skyColor      base colour drawn under all layers
     */
    public ParallaxRenderer(BufferedImage[] layers, float[] scrollFactors, Color skyColor) {
        if (layers == null || scrollFactors == null)
            throw new IllegalArgumentException("[ParallaxRenderer] layers and scrollFactors must not be null");
        this.layers        = layers;
        this.scrollFactors = scrollFactors;
        this.skyColor      = (skyColor != null) ? skyColor : new Color(15, 18, 30);
    }

    // =========================================================================
    //  DRAW
    // =========================================================================

    /**
     * Renders the parallax background onto the given Graphics2D context.
     *
     * @param g          target Graphics2D (state is not modified — all transforms saved/restored)
     * @param W          panel width in pixels
     * @param H          panel height in pixels
     * @param camX       horizontal camera scroll position (pixels)
     * @param heightFrac fraction of H used by the skybox/layers (e.g. 0.65 for gameplay,
     *                   0.55 for menu screens where sky fills less area)
     */
    public void draw(Graphics2D g, int W, int H, float camX, float heightFrac) {
        // 1. Sky fill
        g.setColor(skyColor);
        int skyH = (int)(H * heightFrac);
        g.fillRect(0, 0, W, skyH);

        // 2. Each parallax layer, tiled horizontally
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                           RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        for (int i = 0; i < layers.length; i++) {
            BufferedImage img = layers[i];
            if (img == null) continue;

            float factor  = (i < scrollFactors.length) ? scrollFactors[i] : 0f;
            int   scrollX = (int)(camX * factor);
            int   imgW    = img.getWidth();
            if (imgW <= 0) continue;

            // tile so the layer covers [0, W] regardless of scroll offset
            int startX = -(scrollX % imgW);
            if (startX > 0) startX -= imgW;

            // draw layer at the bottom of the sky region
            int layerH = Math.min(img.getHeight(), skyH);
            int drawY  = skyH - layerH;

            for (int tx = startX; tx < W; tx += imgW) {
                g.drawImage(img, tx, drawY, imgW, layerH, null);
            }
        }

        // 3. Subtle bottom gradient fade (sky → transparent) to blend into the ground
        GradientPaint fade = new GradientPaint(
                0, skyH - (int)(H * FADE_FRACTION), new Color(0, 0, 0, 0),
                0, skyH,                             new Color(0, 0, 0, 100));
        g.setPaint(fade);
        g.fillRect(0, skyH - (int)(H * FADE_FRACTION), W, (int)(H * FADE_FRACTION) + 1);

        // restore default paint so caller is not affected
        g.setPaint(Color.WHITE);
    }
}
