/*
 * Decompiled with CFR 0.152.
 */
package animation;
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
