/*
 * Decompiled with CFR 0.152.
 */
package animation;
public final class WrappingMechanic {
    public static final String TRIGGER = "When scroll offset > layer width";
    public static final String ACTION = "Draw layer twice: original position + offset position";
    public static final String WRAPPING_ALGORITHM = "Seamless Wrap Example:\n\nLayer width = 1920 pixels\nPlayer at X = 1500\nParallax factor = 0.4x\n\nCalculation:\n1. Raw offset = 1500 * 0.4 = 600px\n2. Wrapped = 600 % 1920 = 600px (no wrap needed yet)\n3. Draw layer at position: -600, 0, 1920 screen\n\nWhen Player reaches X = 5000:\n1. Raw offset = 5000 * 0.4 = 2000px\n2. Wrapped = 2000 % 1920 = 80px (WRAP OCCURRED)\n3. First tile:  Draw at -80px\n4. Second tile: Draw at -80 + 1920 = 1840px (seamless transition)\n5. Result: Screen shows end of first tile + beginning of first tile\n\nVisual effect: Infinite seamless scrolling\n";
}
