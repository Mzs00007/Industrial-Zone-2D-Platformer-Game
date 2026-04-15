/*
 * Decompiled with CFR 0.152.
 */
package animation;
public final class RenderingOrderLevel2 {
    public static final String LEVEL = "Power Station Level 2 (Day/Night)";
    public static final String RENDER_ORDER = "STEP 1: Layer 1 (Sky) - Parallax 0.0x\n        \u2022 Draw first (static background)\n        \u2022 Uses Day/1.png OR Night/1.png\n        \u2022 No scrolling\n\nSTEP 2: Layer 2 (Far Tower) - Parallax 0.15x\n        \u2022 Draw second\n        \u2022 Uses Day/BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png OR Night/2.png\n        \u2022 Slow subtle movement\n        \u2022 Calculated offset: playerX * 0.15\n\nSTEP 3: Layer 3 (Mid Tower) - Parallax 0.30x\n        \u2022 Draw third\n        \u2022 Uses Day/3.png OR Night/3.png\n        \u2022 Medium movement\n        \u2022 Calculated offset: playerX * 0.30\n\nSTEP 4: Layer 4 (Near Structure) - Parallax 0.45x\n        \u2022 Draw fourth\n        \u2022 Uses Day/4.png OR Night/4.png\n        \u2022 Faster movement\n        \u2022 Calculated offset: playerX * 0.45\n\nSTEP 5: Layer 5 (Foreground) - Parallax 0.60x\n        \u2022 Draw last (closest element)\n        \u2022 Uses Day/5.png OR Night/5.png\n        \u2022 Fastest parallax movement\n        \u2022 Calculated offset: playerX * 0.60\n        \u2022 Creates strongest depth effect\n";
}
