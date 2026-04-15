/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class Phase4LayerRendering {
    public static final String PHASE_NAME = "PHASE 4: LAYER RENDERING (BACK TO FRONT)";
    public static final String PRINCIPLE = "Painters Algorithm - render distant objects first";
public final class RenderOrderLevel2 {
        public static final String ORDER = "STEP 1: Sky (0.0x) - Static day/night background\nSTEP 2: Far Tower (0.15x) - Slow scroll\nSTEP 3: Mid Tower (0.30x) - Medium scroll\nSTEP 4: Near Structure (0.45x) - Faster scroll\nSTEP 5: Foreground (0.60x) - Fastest scroll (closest)";
    }
public final class RenderOrderLevel1 {
        public static final String ORDER = "STEP 1: Sky Base (0.0x) - Draw first, stays static, fills background\nSTEP 2: Fractal Tree (0.1x) - Draw second, very slow scroll\nSTEP 3: Far Factory (0.25x) - Draw third, slow scroll\nSTEP 4: Mid Factory (0.35x) - Draw fourth, medium scroll\nSTEP 5: Near Factory (0.55x) - Draw last, fastest scroll (foreground)";
    }
}
