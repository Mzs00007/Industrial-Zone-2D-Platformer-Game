/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class ParallaxRenderingPipeline {
public class CompleteParallaxWorkflow {
        public static final String DOCUMENTATION = "Complete parallax rendering pipeline for both levels";
        public static final String[] FULL_SEQUENCE = new String[]{"PHASE 1: Initialize - Load all 5 layer images into memory", "PHASE 2: Select - Choose Day/Night variant (Level 2 only) or skip (Level 1)", "PHASE 3: Calculate - Compute scroll offset for each layer (parallaxFactor \u00d7 playerX)", "PHASE 4: Render - Draw layers back-to-front with calculated offsets", "PHASE 5: Blend - Apply overlay blending if transitioning day/night (Level 2 only)", "PHASE 6: Update - Recalculate offsets every frame (60 FPS)", "PHASE 7: Wrap - Handle seamless layer repetition at edges", "PHASE 8: Composite - Combine all layers into final background"};
        public static final String CONTINUOUS_GAMEPLAY = "During actual gameplay:\n- Phases 3-8 repeat EVERY FRAME\n- Phase 1-2 happen ONCE at level load\n- Player position updates continuously\n- Parallax offsets smooth and responsive\n- Creates depth illusion through layer speed variance\n- Seamless infinite scrolling in both directions\n";
public final class ComparisonSummary {
            public static final String COMPARISON_TITLE = "LEVEL 1 vs LEVEL 2 PARALLAX SYSTEMS";
            public static final String COMPARISON_TABLE = "\u250c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u252c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u252c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510\n\u2502 Feature             \u2502 Level 1 (Industrial) \u2502 Level 2 (Power St.)  \u2502\n\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u253c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u253c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2524\n\u2502 Layers              \u2502 5                    \u2502 5                    \u2502\n\u2502 Parallax Factors    \u2502 0.0, 0.1, 0.25,     \u2502 0.0, 0.15, 0.30,    \u2502\n\u2502                     \u2502 0.35, 0.55           \u2502 0.45, 0.60           \u2502\n\u2502 Day/Night Variants  \u2502 Single static        \u2502 Day + Night          \u2502\n\u2502 Transitions         \u2502 None                 \u2502 Yes (6am & 6pm)      \u2502\n\u2502 Overlay Blending    \u2502 Not used             \u2502 Overlay.png (45sec)  \u2502\n\u2502 Sky Appearance      \u2502 Lavender grey        \u2502 Light blue (day) or  \u2502\n\u2502                     \u2502 (always)             \u2502 Dark grey (night)    \u2502\n\u2502 Pipeline Phases     \u2502 1,3-8 (skip 2,5)     \u2502 1-8 (all)            \u2502\n\u2502 Frame Time          \u2502 ~13-14ms             \u2502 ~15ms                \u2502\n\u2502 Complexity          \u2502 Simplified           \u2502 Full featured        \u2502\n\u2502 File Count          \u2502 6 (1 composite+5)    \u2502 11 (5 day+5 night+1  \u2502\n\u2502                     \u2502 or 5 layers          \u2502 overlay)             \u2502\n\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2534\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2534\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518\n";
        }
public final class Level2SpecificWorkflow {
            public static final String LEVEL = "Power Station Level 2";
            public static final String VARIANT_TYPE = "DUAL VARIANT WITH DAY/NIGHT TRANSITIONS";
            public static final String FULL_PIPELINE = "Level 2 Parallax Pipeline (Full Featured):\n\nINITIALIZATION (Once at load):\n1. Load both Day variant (5 files) and Night variant (5 files)\n2. Load Overlay.png for blending mask\n3. Determine current game time\n4. Phase 2: Select appropriate variant (Day 6:00-18:00 or Night 18:00-6:00)\n5. Proceed to Phase 3 with selected variant\n\nGAMEPLAY LOOP (Every frame, 60 FPS):\nPhase 3: Calculate offsets using factors [0.0, 0.15, 0.30, 0.45, 0.60]\nPhase 4: Render 5 layers back-to-front with calculated offsets\nPhase 5: IF transitioning day/night: Apply overlay blending with smooth alpha\nPhase 6: Update offsets based on new player position\nPhase 7: Wrap layers at edges (modulo width)\nPhase 8: Composite final background and display\n\nTRANSITIONS (At 6:00 AM and 6:00 PM):\n- Trigger: Game time reaches transition hour\n- Duration: 45 seconds smooth blend\n- Method: Alpha blend overlay mask between Day and Night variants\n- Formula: finalColor = dayColor * (1 - blendFactor) + nightColor * blendFactor\n- blendFactor goes from 0.0 \u2192 1.0 over 45 seconds\n\nPERFORMANCE: ~15ms per frame (includes blending math during transitions)\n";
        }
public final class Level1SpecificWorkflow {
            public static final String LEVEL = "Industrial Zone Level 1";
            public static final String VARIANT_TYPE = "SINGLE STATIC VARIANT";
            public static final String SIMPLIFIED_PIPELINE = "Level 1 Parallax Pipeline (Simplified):\n\nINITIALIZATION (Once at load):\n1. Load 5 layer files from Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/\n2. Skip day/night selection (Phase 2 SKIPPED)\n3. All 5 layers use consistent \"industrial daylight\" appearance\n4. Proceed to Phase 3 immediately\n\nGAMEPLAY LOOP (Every frame, 60 FPS):\nPhase 3: Calculate offsets using factors [0.0, 0.1, 0.25, 0.35, 0.55]\nPhase 4: Render 5 layers back-to-front with calculated offsets\nPhase 5: Skip overlay blending (no day/night)\nPhase 6: Update offsets based on new player position\nPhase 7: Wrap layers at edges (modulo width)\nPhase 8: Composite final background and display\n\nNO TRANSITIONS:\n- No day/night blending\n- No overlay alpha changes\n- Consistent appearance throughout gameplay\n- Simplified per-frame computation (no blending math)\n\nPERFORMANCE: ~13-14ms per frame (slightly faster than Level 2 due to no blending)\n";
        }
    }
public class Phase8FinalComposite {
        public static final String PHASE_NAME = "PHASE 8: FINAL COMPOSITE & RENDER";
        public static final String DESCRIPTION = "Blend all layers and draw final background";
public final class RenderPerformance {
            public static final String TARGET_FPS = "60 frames per second";
            public static final String FRAME_TIME = "16.67 milliseconds per frame";
            public static final int LAYERS_TO_RENDER = 5;
            public static final int AVG_TIME_PER_LAYER = 3;
            public static final String TOTAL_TIME = "~15ms (leaves 1.67ms headroom)";
            public static final String OPTIMIZATION = "Parallax uses pre-loaded PNG - minimal CPU overhead";
        }
public final class CompositeProcess {
            public static final String STEP1 = "CREATE: Empty canvas (screen resolution, e.g., 1920x1080)";
            public static final String STEP2 = "LOOP: For each layer (back to front):";
            public static final String STEP2A = "  - Get calculated scroll offset for layer";
            public static final String STEP2B = "  - Draw Layer 1 at position (offset, 0)";
            public static final String STEP2C = "  - If wrapping: Draw Layer 1 at (offset + layerWidth, 0)";
            public static final String STEP3 = "IF day/night transition active:";
            public static final String STEP3A = "  - Apply overlay blending with current transition alpha";
            public static final String STEP4 = "DRAW: Final composite to screen";
            public static final String VISUAL_EXAMPLE = "Final Background Rendering (60 FPS):\n\nLAYER STACK (back to front):\n\u250c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510  \u2190 Layer 5 (Near) - fastest parallax\n\u2502 Layer 5: Near Factory Element     \u2502     offset = 1650px (continuous update)\n\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2524\n\u2502 Layer 4: Mid Factory Element      \u2502  \u2190 Layer 4 (Mid) - medium parallax\n\u2502                                   \u2502     offset = 1050px\n\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2524\n\u2502 Layer 3: Far Factory Background   \u2502  \u2190 Layer 3 (Far) - slow parallax\n\u2502                                   \u2502     offset = 750px\n\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2524\n\u2502 Layer 2: Fractal Tree Overlay     \u2502  \u2190 Layer 2 (Tree) - very slow\n\u2502                                   \u2502     offset = 300px\n\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2524\n\u2502 Layer 1: Sky Base (Static)        \u2502  \u2190 Layer 1 (Sky) - no parallax\n\u2502                                   \u2502     offset = 0px (always)\n\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518\n\nResult displayed to player:\n- Smooth scrolling parallax depth effect\n- Layers move at different speeds\n- Seamless wrapping at edges\n- Seamless day/night transitions (Layer 2 only)\n";
        }
    }
public class Phase7LayerWrapping {
        public static final String PHASE_NAME = "PHASE 7: LAYER WRAPPING";
        public static final String DESCRIPTION = "Repeat layers seamlessly when they reach edges";
public final class WrappingMathematicsLevel1 {
            public static final String LEVEL = "Industrial Zone Level 1";
            public static final int ESTIMATED_LAYER_WIDTH = 1920;
            public static final float[] PARALLAX_FACTORS = new float[]{0.0f, 0.1f, 0.25f, 0.35f, 0.55f};
            public static final String[] WRAP_EXAMPLES_AT_PLAYER_X_3000 = new String[]{"Sky (0.0x): offset = 3000 * 0.0 = 0px (no wrap, static)", "Tree (0.1x): offset = 3000 * 0.1 = 300px, wrapped = 300 % 1920 = 300px", "Far (0.25x): offset = 3000 * 0.25 = 750px, wrapped = 750 % 1920 = 750px", "Mid (0.35x): offset = 3000 * 0.35 = 1050px, wrapped = 1050 % 1920 = 1050px", "Near (0.55x): offset = 3000 * 0.55 = 1650px, wrapped = 1650 % 1920 = 1650px"};
        }
public final class WrappingMechanic {
            public static final String TRIGGER = "When scroll offset > layer width";
            public static final String ACTION = "Draw layer twice: original position + offset position";
            public static final String WRAPPING_ALGORITHM = "Seamless Wrap Example:\n\nLayer width = 1920 pixels\nPlayer at X = 1500\nParallax factor = 0.4x\n\nCalculation:\n1. Raw offset = 1500 * 0.4 = 600px\n2. Wrapped = 600 % 1920 = 600px (no wrap needed yet)\n3. Draw layer at position: -600, 0, 1920 screen\n\nWhen Player reaches X = 5000:\n1. Raw offset = 5000 * 0.4 = 2000px\n2. Wrapped = 2000 % 1920 = 80px (WRAP OCCURRED)\n3. First tile:  Draw at -80px\n4. Second tile: Draw at -80 + 1920 = 1840px (seamless transition)\n5. Result: Screen shows end of first tile + beginning of first tile\n\nVisual effect: Infinite seamless scrolling\n";
        }
    }
public class Phase6FactorUpdate {
        public static final String PHASE_NAME = "PHASE 6: PARALLAX FACTOR UPDATE";
        public static final String DESCRIPTION = "Recalculate layer offsets based on new camera position";
        public static final String FREQUENCY = "Every frame (60 FPS = every 16.67ms)";
public final class UpdateProcess {
            public static final String STEP1 = "GET: Current camera X position (player position)";
            public static final String STEP2 = "FOR EACH layer: newOffset = cameraX * parallaxFactor";
            public static final String STEP3 = "WRAP: newOffset = newOffset % layerWidth (for seamless wrapping)";
            public static final String STEP4 = "STORE: Update layer render position";
            public static final String STEP5 = "NEXT FRAME: Repeat with new camera position";
            public static final String SMOOTH_SCROLLING_EXPLANATION = "Continuous Update Example (60 FPS):\n\nFrame 1: playerX = 100, far_layer_offset = 100 * 0.25 = 25px\nFrame 2: playerX = 105, far_layer_offset = 105 * 0.25 = 26.25px\nFrame 3: playerX = 110, far_layer_offset = 110 * 0.25 = 27.5px\nFrame 4: playerX = 115, far_layer_offset = 115 * 0.25 = 28.75px\nFrame 5: playerX = 120, far_layer_offset = 120 * 0.25 = 30px\n\nNear layer (0.55x) updates faster:\nFrame 1: playerX = 100, near_layer_offset = 100 * 0.55 = 55px\nFrame 2: playerX = 105, near_layer_offset = 105 * 0.55 = 57.75px\nFrame 3: playerX = 110, near_layer_offset = 110 * 0.55 = 60.5px\n\nResult: Near layers scroll faster  = depth effect!\n";
        }
    }
public class Phase5OverlayBlending {
        public static final String PHASE_NAME = "PHASE 5: OVERLAY BLENDING";
        public static final String DESCRIPTION = "Blend day/night variants during transitions";
        public static final String APPLIES_TO = "Level 2 (Power Station) only";
public final class TransitionTiming {
            public static final String SUNRISE_START = "5:45 AM";
            public static final String SUNRISE_END = "6:30 AM";
            public static final String SUNRISE_DIRECTION = "Night \u2192 Day";
            public static final String SUNSET_START = "5:30 PM";
            public static final String SUNSET_END = "6:15 PM";
            public static final String SUNSET_DIRECTION = "Day \u2192 Night";
        }
public final class BlendingMechanic {
            public static final String OVERLAY_FILE = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Overlay.png";
            public static final String BLEND_PURPOSE = "Smooth transition between day and night backgrounds";
            public static final float TRANSITION_DURATION = 45.0f;
            public static final float BLEND_SPEED = 0.75f;
            public static final String BLEND_ALGORITHM = "For each pixel at time T during 45-second transition:\n1. Load both Day and Night layer images\n2. Load Overlay.png (blending mask)\n3. For each pixel:\n   - If overlay alpha = 0: Use Day image (100%)\n   - If overlay alpha = 1: Use Night image (100%)\n   - If overlay alpha = 0.5: Blend 50% Day + 50% Night\n\nBlend formula per pixel:\nfinalColor = dayColor * (1 - blendFactor) + nightColor * blendFactor\n\nWhere blendFactor goes from 0.0 \u2192 1.0 over 45 seconds\n\nResult: Smooth gradient transition from day to night\n(or night to day at morning transition)\n";
        }
    }
public class Phase4LayerRendering {
        public static final String PHASE_NAME = "PHASE 4: LAYER RENDERING ORDER";
        public static final String DESCRIPTION = "Draw layers back-to-front with calculated offsets";
        public static final String ALGORITHM = "Painters Algorithm (back to front)";
public final class RenderingOrderLevel2 {
            public static final String LEVEL = "Power Station Level 2 (Day/Night)";
            public static final String RENDER_ORDER = "STEP 1: Layer 1 (Sky) - Parallax 0.0x\n        \u2022 Draw first (static background)\n        \u2022 Uses Day/1.png OR Night/1.png\n        \u2022 No scrolling\n\nSTEP 2: Layer 2 (Far Tower) - Parallax 0.15x\n        \u2022 Draw second\n        \u2022 Uses Day/BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png OR Night/2.png\n        \u2022 Slow subtle movement\n        \u2022 Calculated offset: playerX * 0.15\n\nSTEP 3: Layer 3 (Mid Tower) - Parallax 0.30x\n        \u2022 Draw third\n        \u2022 Uses Day/3.png OR Night/3.png\n        \u2022 Medium movement\n        \u2022 Calculated offset: playerX * 0.30\n\nSTEP 4: Layer 4 (Near Structure) - Parallax 0.45x\n        \u2022 Draw fourth\n        \u2022 Uses Day/4.png OR Night/4.png\n        \u2022 Faster movement\n        \u2022 Calculated offset: playerX * 0.45\n\nSTEP 5: Layer 5 (Foreground) - Parallax 0.60x\n        \u2022 Draw last (closest element)\n        \u2022 Uses Day/5.png OR Night/5.png\n        \u2022 Fastest parallax movement\n        \u2022 Calculated offset: playerX * 0.60\n        \u2022 Creates strongest depth effect\n";
        }
public final class RenderingOrderLevel1 {
            public static final String LEVEL = "Industrial Zone Level 1";
            public static final String RENDER_ORDER = "STEP 1: Layer 1 (Sky Base) - Parallax 0.0x\n        \u2022 Draw first (background)\n        \u2022 No parallax scrolling (stays static)\n        \u2022 Fills entire background area\n        \u2022 Scroll offset: 0 pixels (always)\n\nSTEP 2: Layer 2 (Fractal Tree) - Parallax 0.1x\n        \u2022 Draw second\n        \u2022 Very slow parallax movement\n        \u2022 Calculated offset: playerX * 0.1\n        \u2022 Updates rarely (slow scrolling effect)\n\nSTEP 3: Layer 3 (Far Factory) - Parallax 0.25x\n        \u2022 Draw third\n        \u2022 Slow parallax movement\n        \u2022 Calculated offset: playerX * 0.25\n        \u2022 Moves slower than camera\n\nSTEP 4: Layer 4 (Mid Factory) - Parallax 0.35x\n        \u2022 Draw fourth\n        \u2022 Medium parallax movement\n        \u2022 Calculated offset: playerX * 0.35\n        \u2022 More responsive than far layer\n\nSTEP 5: Layer 5 (Near Factory) - Parallax 0.55x\n        \u2022 Draw last (foreground)\n        \u2022 Fastest parallax movement\n        \u2022 Calculated offset: playerX * 0.55\n        \u2022 Most responsive to camera, creates depth\n";
        }
    }
public class Phase3ScrollCalculation {
        public static final String PHASE_NAME = "PHASE 3: SCROLL OFFSET CALCULATION";
        public static final String DESCRIPTION = "Compute scroll offset for each layer";
public final class Level2ScrollFactors {
            public static final String LEVEL = "Power Station Level 2";
            public static final float[] FACTORS = new float[]{0.0f, 0.15f, 0.3f, 0.45f, 0.6f};
            public static final String[] LAYER_NAMES = new String[]{"Sky", "Far Tower", "Mid Tower", "Near Structure", "Foreground"};
public final class ScrollBehavior {
                public static final String SKY_BEHAVIOR = "0.0x - NO SCROLL (Static sky)";
                public static final String FAR_BEHAVIOR = "0.15x - SLOW (Distant tower, subtle movement)";
                public static final String MID_BEHAVIOR = "0.30x - MEDIUM-SLOW (Mid-ground structure)";
                public static final String NEAR_BEHAVIOR = "0.45x - MEDIUM-FAST (Near structure, noticeable movement)";
                public static final String FORE_BEHAVIOR = "0.60x - FAST (Foreground, most responsive)";
            }
        }
public final class Level1ScrollFactors {
            public static final String LEVEL = "Industrial Zone Level 1";
            public static final float[] FACTORS = new float[]{0.0f, 0.1f, 0.25f, 0.35f, 0.55f};
            public static final String[] LAYER_NAMES = new String[]{"Sky", "Fractal Tree", "Far Factory", "Mid Factory", "Near Factory"};
public final class ScrollBehavior {
                public static final String SKY_BEHAVIOR = "0.0x - NO SCROLL (Static sky, stays in place)";
                public static final String TREE_BEHAVIOR = "0.1x - VERY SLOW (Barely moves, very distant)";
                public static final String FAR_BEHAVIOR = "0.25x - SLOW (Quarter speed of camera, far background)";
                public static final String MID_BEHAVIOR = "0.35x - MEDIUM (Third speed of camera, mid-ground)";
                public static final String NEAR_BEHAVIOR = "0.55x - FAST (Half speed of camera, closest)";
            }
        }
public final class ScrollFormulaExplanation {
            public static final String FORMULA = "layerScrollOffset = playerX * parallaxFactor";
            public static final String EXAMPLE_CALCULATION = "Assume:\n- Camera X position (player): 500 pixels\n- Layer parallax factor: 0.3x\n- Layer image width: 1920 pixels\n\nCalculate:\n1. Raw scroll = 500 * 0.3 = 150 pixels\n2. Wrapped scroll = 150 % 1920 = 150 pixels (layer scrolls 150px left)\n\nAnother example:\n- Camera X: 2000 pixels\n- Layer parallax: 0.6x\n- Layer width: 1920 pixels\n\nCalculate:\n1. Raw scroll = 2000 * 0.6 = 1200 pixels\n2. Wrapped scroll = 1200 % 1920 = 1200 pixels (within one tile)\n\nExample with wrapping:\n- Camera X: 4000 pixels\n- Layer parallax: 0.8x\n- Layer width: 1920 pixels\n\nCalculate:\n1. Raw scroll = 4000 * 0.8 = 3200 pixels\n2. Wrapped scroll = 3200 % 1920 = 1280 pixels\n3. Draw first tile at -1280, then second tile at 640 (seamless)\n";
        }
    }
public class Phase2VariantSelection {
        public static final String PHASE_NAME = "PHASE 2: DAY/NIGHT VARIANT SELECTION";
        public static final String DESCRIPTION = "Choose day or night variant based on game time";
public final class TransitionMechanic {
            public static final String TRIGGER = "When time crosses 6:00 or 18:00";
            public static final String APPLIES_TO = "Level 2 (Power Station) ONLY";
            public static final String DURATION = "45 seconds smooth blend";
            public static final String BLENDING_FILE = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/BG_Overlay_BlueYellowDiagonalGradient_ColourAtmosphere_DrawLast.png";
            public static final String METHOD = "Alpha blend overlay mask between variants";
            public static final float BLEND_SPEED = 0.02f;
            public static final String LEVEL_1_NOTE = "Level 1 has NO transitions - static appearance throughout gameplay";
        }
public final class NightVariantRules {
            public static final String VARIANT_NAME = "NIGHT";
            public static final String LEVEL = "Power Station Level 2 ONLY";
            public static final String TIME_RANGE = "18:00 to 6:00 (12 hours)";
            public static final String SKY_COLOR = "Dark gray/black - Minimal light";
            public static final String SILHOUETTES = "Dimmed visibility - Subtle gray shapes";
            public static final String VISIBILITY = "50-70% - Limited detail, atmosphere heavy";
            public static final String LAYER_FILES = "Night/* files";
            public static final String[] LAYERS = new String[]{"Night/1.png - Sky: Dark gray night atmosphere, possibly stars/moon", "Night/2.png - Far tower: Dimmed silhouette (0.15x parallax)", "Night/3.png - Mid structure: Gray-blue limited detail (0.30x)", "Night/4.png - Near structure: Very dark, minimal piping visible (0.45x)", "Night/5.png - Foreground: Darkest, only outline visible (0.60x)"};
        }
public final class DayVariantRules {
            public static final String VARIANT_NAME = "DAY";
            public static final String LEVEL = "Power Station Level 2 ONLY";
            public static final String TIME_RANGE = "6:00 to 18:00 (12 hours)";
            public static final String SKY_COLOR = "Light blue - Full brightness";
            public static final String SILHOUETTES = "Clear visibility - Distinct black shapes";
            public static final String VISIBILITY = "100% - Maximum detail visible";
            public static final String LAYER_FILES = "Day/* files";
            public static final String[] LAYERS = new String[]{"Day/1.png - Sky: Bright blue daylight atmosphere", "Day/BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png - Far tower: Clear light blue silhouette", "Day/3.png - Mid structure: Medium blue detail", "Day/4.png - Near structure: Darker blue with visible piping", "Day/5.png - Foreground: Closest structure, darkest blue"};
        }
public final class Level1StaticVariant {
            public static final String LEVEL = "Industrial Zone Level 1";
            public static final String VARIANT_TYPE = "SINGLE STATIC VARIANT (No day/night)";
            public static final String APPLIES_TO_LEVEL_1 = "Level 1 skips this phase entirely";
            public static final String REASON = "Level 1 has no day/night cycle - uses fixed industrial daylight appearance";
            public static final String LEVEL_1_PROCESS = "PHASE 2 BEHAVIOR FOR LEVEL 1:\n\nInstead of selecting variants:\n- Load all 5 layers immediately (all use \"industrial daylight\" appearance)\n- No day/night state to check\n- No overlay blending needed\n- Skip directly to Phase 3\n\nAvailable single variant:\n- Layer 1 (Sky): BG_Layer1_SkyBase_SolidLavenderGrey_StaticFill_DrawFirstNoScroll.png\n- Layer 2 (Tree): BG_Layer2_FractalTreeSilhouette_MintSkyBlackCracks_ParallaxFactor015.png\n- Layer 3 (Far): BG_Layer3_FarFactorySilhouette_LightBlueIndustrial_ParallaxFactor025.png\n- Layer 4 (Mid): BG_Layer4_MidFactorySilhouette_MediumBluePipeDetail_ParallaxFactor040.png\n- Layer 5 (Near): BG_Layer5_NearFactorySilhouette_DarkNavyLargeTank_ParallaxFactor060.png\n\nAll layers use consistent \"industrial daylight\" color palette:\n- Sky: Lavender grey\n- Parallax layers: Various shades of blue (light \u2192 dark as they approach)\n- Overall atmosphere: Clear daytime industrial zone\n";
        }
    }
public class Phase1Initialization {
        public static final String PHASE_NAME = "PHASE 1: BACKGROUND INITIALIZATION";
        public static final String DESCRIPTION = "Load all parallax layers into memory";
public final class Level2Init {
            public static final String LEVEL_TYPE = "Power Station Level 2 (Day/Night)";
            public static final int TOTAL_LAYERS = 5;
            public static final String DAY_DIRECTORY = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Day";
            public static final String NIGHT_DIRECTORY = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Night";
            public static final String OVERLAY_FILE = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/BG_Overlay_BlueYellowDiagonalGradient_ColourAtmosphere_DrawLast.png";
            public static final String[] DAY_FILES = new String[]{"Day/BG_Layer1_SkyBase_LightGreyWhiteGradient_StaticFill_DrawFirst.png", "Day/BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png", "Day/BG_Layer3_FactoryTall_LightBlueChimney_ParallaxFactor025.png", "Day/BG_Layer4_DistantFactory_FaintSilhouette_ParallaxFactor040.png", "Day/BG_Layer5_FactoryRight_LightBlueVariant_ParallaxFactor060.png"};
            public static final String[] NIGHT_FILES = new String[]{"Night/BG_Layer1_SkyBase_DarkGreyGradient_StaticFill_DrawFirst.png", "Night/BG_Layer2_FactoryLeft_DarkGreySilhouette_ParallaxFactor015.png", "Night/BG_Layer3_FactoryTall_DarkGreyCentre_ParallaxFactor025.png", "Night/BG_Layer4_DistantFactory_VeryDarkFaint_ParallaxFactor040.png", "Night/BG_Layer5_FactoryRight_DarkGreyVariant_ParallaxFactor060.png"};
        }
public final class Level1Init {
            public static final String LEVEL_TYPE = "Industrial Zone Level 1";
            public static final int TOTAL_LAYERS = 5;
            public static final String[] FILES_TO_LOAD = new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Composite_FullLayeredSkyline_AllLayersCombined_SingleDrawFallback.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer1_SkyBase_SolidLavenderGrey_StaticFill_DrawFirstNoScroll.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer2_FractalTreeSilhouette_MintSkyBlackCracks_ParallaxFactor015.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer3_FarFactorySilhouette_LightBlueIndustrial_ParallexFactor025.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer4_MidFactorySilhouette_MediumBluePipeDetail_ParallaxFactor040.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer5_NearFactorySilhouette_DarkNavyLargeTank_ParallaxFactor060.png"};
            public static final int ESTIMATED_LAYER_WIDTH = 1920;
            public static final int ESTIMATED_LAYER_HEIGHT = 1080;
        }
    }
}
