/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.ParallaxBackgroundSystem.ContinuousGameplayLoop {
    public static final String DESCRIPTION = "Complete parallax rendering during gameplay";
    public static final String WORKFLOW = "At Level Start (Once):\n  \u2192 Phase 1: Initialize - Load all layer images\n  \u2192 Phase 2: Select variant (Day vs Night for Level 2 only)\n\nEvery Frame (60 FPS, repeated infinitely):\n  \u2192 Phase 3: Calculate scroll offsets based on new player position\n  \u2192 Phase 4: Render all 5 layers back-to-front\n  \u2192 Phase 5: Apply overlay blend if transitioning (Level 2 only)\n  \u2192 Phase 6: Update offsets for next frame\n  \u2192 Phase 7: Wrap layers at edges for seamless scrolling\n  \u2192 Phase 8: Composite final background to display\n\nResult: Smooth scrolling parallax depth effect with seamless wrapping";

    public static final class Level2Optimizations {
        public static final String LEVEL = "Power Station";
        public static final String OPTIMIZATION = "Partial overhead only during transitions (45sec windows)";
        public static final String FRAME_TIME = "~15ms (includes blending math when active)";
    }

    public static final class Level1Optimizations {
        public static final String LEVEL = "Industrial Zone";
        public static final String OPTIMIZATION = "No day/night blending overhead";
        public static final String FRAME_TIME = "~13-14ms (faster than Level 2)";
    }
}
