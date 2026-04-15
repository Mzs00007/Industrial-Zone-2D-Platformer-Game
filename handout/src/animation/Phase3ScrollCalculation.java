/*
 * Decompiled with CFR 0.152.
 */
package animation;
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
