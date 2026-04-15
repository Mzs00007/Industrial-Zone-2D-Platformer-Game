/*
 * Decompiled with CFR 0.152.
 */
package animation;
public final class ScrollFormulaExplanation {
    public static final String FORMULA = "layerScrollOffset = playerX * parallaxFactor";
    public static final String EXAMPLE_CALCULATION = "Assume:\n- Camera X position (player): 500 pixels\n- Layer parallax factor: 0.3x\n- Layer image width: 1920 pixels\n\nCalculate:\n1. Raw scroll = 500 * 0.3 = 150 pixels\n2. Wrapped scroll = 150 % 1920 = 150 pixels (layer scrolls 150px left)\n\nAnother example:\n- Camera X: 2000 pixels\n- Layer parallax: 0.6x\n- Layer width: 1920 pixels\n\nCalculate:\n1. Raw scroll = 2000 * 0.6 = 1200 pixels\n2. Wrapped scroll = 1200 % 1920 = 1200 pixels (within one tile)\n\nExample with wrapping:\n- Camera X: 4000 pixels\n- Layer parallax: 0.8x\n- Layer width: 1920 pixels\n\nCalculate:\n1. Raw scroll = 4000 * 0.8 = 3200 pixels\n2. Wrapped scroll = 3200 % 1920 = 1280 pixels\n3. Draw first tile at -1280, then second tile at 640 (seamless)\n";
}
