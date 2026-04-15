/*
 * Decompiled with CFR 0.152.
 */
package animation;
import game2D.*;
public class ParallaxBackgroundSystem {
    public static final String SYSTEM_NAME = "PARALLAX BACKGROUND RENDERING";
    public static final String DESCRIPTION = "8-phase pipeline for layered background scrolling";
public class ContinuousGameplayLoop {
        public static final String DESCRIPTION = "Complete parallax rendering during gameplay";
        public static final String WORKFLOW = "At Level Start (Once):\n  \u2192 Phase 1: Initialize - Load all layer images\n  \u2192 Phase 2: Select variant (Day vs Night for Level 2 only)\n\nEvery Frame (60 FPS, repeated infinitely):\n  \u2192 Phase 3: Calculate scroll offsets based on new player position\n  \u2192 Phase 4: Render all 5 layers back-to-front\n  \u2192 Phase 5: Apply overlay blend if transitioning (Level 2 only)\n  \u2192 Phase 6: Update offsets for next frame\n  \u2192 Phase 7: Wrap layers at edges for seamless scrolling\n  \u2192 Phase 8: Composite final background to display\n\nResult: Smooth scrolling parallax depth effect with seamless wrapping";
public final class Level2Optimizations {
            public static final String LEVEL = "Power Station";
            public static final String OPTIMIZATION = "Partial overhead only during transitions (45sec windows)";
            public static final String FRAME_TIME = "~15ms (includes blending math when active)";
        }
public final class Level1Optimizations {
            public static final String LEVEL = "Industrial Zone";
            public static final String OPTIMIZATION = "No day/night blending overhead";
            public static final String FRAME_TIME = "~13-14ms (faster than Level 2)";
        }
    }
public class Phase8FinalComposite {
        public static final String PHASE_NAME = "PHASE 8: FINAL COMPOSITE & RENDER";
        public static final String DESCRIPTION = "Blend all layers into final displayable background";
public final class CompositeProcess {
            public static final String RENDER_PIPELINE = "1. CREATE empty canvas (screen resolution)\n2. FOR EACH layer (back to front):\n   - Get calculated offset\n   - Draw layer at offset position\n   - If wrapping: draw layer again at (offset + width)\n3. IF day/night transition active:\n   - Apply overlay blending with current alpha\n4. DISPLAY final composite to screen";
            public static final String PERFORMANCE = "Target FPS: 60 frames per second\nTime per frame: 16.67ms\n5 layers \u00d7 3ms = 15ms (leaves 1.67ms headroom)\nOptimization: Pre-loaded PNG files minimize CPU overhead";
        }
    }
public class Phase7LayerWrapping {
        public static final String PHASE_NAME = "PHASE 7: LAYER WRAPPING";
        public static final String TRIGGER = "When scroll offset > layer width";
        public static final String ACTION = "Draw layer twice: original + offset repeat";
public final class WrappingExample {
            public static final String SCENARIO = "Player at X=5000, Parallax 0.4x, Layer width 1920px";
            public static final int RAW_OFFSET = 2000;
            public static final int WRAPPED = 80;
            public static final String DRAW_LOGIC = "Tile 1: Draw at position -80px\nTile 2: Draw at position -80 + 1920 = 1840px\nResult: Seamless visual transition, player never sees edge";
        }
    }
public class Phase6FactorUpdate {
        public static final String PHASE_NAME = "PHASE 6: PARALLAX FACTOR UPDATE";
        public static final String FREQUENCY = "Every frame (60 FPS = every 16.67ms)";
public final class UpdateProcess {
            public static final String STEP1 = "GET current camera X (player position)";
            public static final String STEP2 = "FOR each layer: newOffset = cameraX \u00d7 parallaxFactor";
            public static final String STEP3 = "WRAP: newOffset = newOffset % layerWidth (seamless)";
            public static final String STEP4 = "UPDATE layer render position";
            public static final String STEP5 = "NEXT FRAME: Repeat with new position";
            public static final String SMOOTH_EXAMPLE = "Frame 1: playerX=100, far_layer(0.25x) offset=25px\nFrame 2: playerX=105, far_layer offset=26.25px\nFrame 3: playerX=110, far_layer offset=27.5px\nResult: Smooth continuous scrolling by small increments";
        }
    }
public class Phase5OverlayBlending {
        public static final String PHASE_NAME = "PHASE 5: OVERLAY BLENDING";
        public static final String APPLIES_TO = "Level 2 (Power Station) ONLY";
public final class BlendingMechanic {
            public static final String OVERLAY_FILE = "Overlay.png (blending mask)";
            public static final float TRANSITION_DURATION_SECONDS = 45.0f;
            public static final String BLEND_FORMULA = "finalColor = dayColor \u00d7 (1 - alpha) + nightColor \u00d7 alpha\nwhere alpha goes from 0.0 \u2192 1.0 over 45 seconds\nResult: Smooth gradient transition across entire screen";
public final class SunsetTransition {
                public static final String TIME_WINDOW = "5:30 PM to 6:15 PM";
                public static final String DIRECTION = "Day \u2192 Night (alpha: 1.0 \u2192 0.0)";
            }
public final class SunriseTransition {
                public static final String TIME_WINDOW = "5:45 AM to 6:30 AM";
                public static final String DIRECTION = "Night \u2192 Day (alpha: 0.0 \u2192 1.0)";
            }
        }
    }
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
public class Phase3ScrollCalculation {
        public static final String PHASE_NAME = "PHASE 3: SCROLL OFFSET CALCULATION";
        public static final String FORMULA = "layerScrollOffset = playerX * parallaxFactor";
public final class ExampleLevel2 {
            public static final String SCENARIO = "Player at X=4000, Near Structure (0.45x)";
            public static final int PLAYER_X = 4000;
            public static final float PARALLAX_FACTOR = 0.45f;
            public static final int RAW_SCROLL = 1800;
            public static final int LAYER_WIDTH = 1920;
            public static final int WRAPPED_SCROLL = 1800;
            public static final String RESULT = "Near layer scrolls 1800px (within bounds)";
        }
public final class ExampleLevel1 {
            public static final String SCENARIO = "Player at X=2000, Mid Factory layer (0.35x)";
            public static final int PLAYER_X = 2000;
            public static final float PARALLAX_FACTOR = 0.35f;
            public static final int RAW_SCROLL = 700;
            public static final int LAYER_WIDTH = 1920;
            public static final int WRAPPED_SCROLL = 700;
            public static final String RESULT = "Mid layer scrolls 700px to the left";
        }
    }
public class Phase2VariantSelection {
        public static final String PHASE_NAME = "PHASE 2: VARIANT SELECTION";
public final class Level2VariantLogic {
            public static final String LEVEL = "Power Station Level 2";
            public static final String LOGIC = "SELECT based on game time";
            public static final String DAY_PERIOD = "6:00 AM to 6:00 PM \u2192 Use Day variant";
            public static final String NIGHT_PERIOD = "6:00 PM to 6:00 AM \u2192 Use Night variant";
            public static final String TRANSITION_AT_6AM = "Sunrise: Fade from Night \u2192 Day over 45 seconds";
            public static final String TRANSITION_AT_6PM = "Sunset: Fade from Day \u2192 Night over 45 seconds";
        }
public final class Level1VariantLogic {
            public static final String LEVEL = "Industrial Zone Level 1";
            public static final String LOGIC = "SKIP PHASE 2: Use single consistent daylight variant always";
            public static final String REASON = "Level 1 has no time-of-day system; always displays industrial daylight";
        }
    }
public class Phase1Initialization {
        public static final String PHASE_NAME = "PHASE 1: LAYER ASSET INITIALIZATION";
public final class Level2LoadSequence {
            public static final String LEVEL = "Power Station Level 2";
            public static final int LAYER_COUNT_PER_VARIANT = 5;
            public static final int TOTAL_VARIANT_SETS = 2;
            public static final String LOAD_TYPE = "Dual Variant with Overlay Blending";
            public static final String[] DAY_LAYERS = new String[]{"Sky (0.0x - static day sky)", "Far Tower (0.15x - daylight)", "Mid Tower (0.30x - daylight)", "Near Structure (0.45x - daylight)", "Foreground (0.60x - daylight)"};
            public static final String[] NIGHT_LAYERS = new String[]{"Sky (0.0x - static night sky)", "Far Tower (0.15x - nighttime)", "Mid Tower (0.30x - nighttime)", "Near Structure (0.45x - nighttime)", "Foreground (0.60x - nighttime)"};
            public static final String OVERLAY_ASSET = "Overlay.png (blending mask for smooth transitions)";
        }
public final class Level1LoadSequence {
            public static final String LEVEL = "Industrial Zone Level 1";
            public static final int LAYER_COUNT = 5;
            public static final String LOAD_TYPE = "Single Static Variant";
            public static final String[] LAYERS = new String[]{"Sky Base (0.0x parallax - static)", "Fractal Tree (0.1x parallax - very slow)", "Far Factory (0.25x parallax - slow)", "Mid Factory (0.35x parallax - medium)", "Near Factory (0.55x parallax - fast)"};
        }
    }
}
