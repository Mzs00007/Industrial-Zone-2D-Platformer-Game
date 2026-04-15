/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class SpriteMetadata {
    public int imageWidth;
    public int imageHeight;
    public int frameCount;
    public int frameWidth;
    public int frameHeight;
    public int totalPixelsPerFrame;
    public String complexity;
    public int suggestedMs;

    public SpriteMetadata(int n, int n2, int n3, int n4, int n5) {
        this.imageWidth = n;
        this.imageHeight = n2;
        this.frameCount = n3;
        this.frameWidth = n4;
        this.frameHeight = n5;
        this.totalPixelsPerFrame = n4 * n5;
        if (n4 <= 32 || n5 <= 32) {
            this.complexity = "LOW";
            this.suggestedMs = 120;
        } else if (n4 > 64 && n5 > 80) {
            this.complexity = "HIGH";
            this.suggestedMs = 80;
        } else {
            this.complexity = "MEDIUM";
            this.suggestedMs = 100;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n");
        stringBuilder.append("  SPRITE METADATA ANALYSIS\n");
        stringBuilder.append("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n");
        stringBuilder.append("  Spritesheet Size:  ").append(this.imageWidth).append("\u00d7").append(this.imageHeight).append("px\n");
        stringBuilder.append("  Frame Count:       ").append(this.frameCount).append(" frames\n");
        stringBuilder.append("  Frame Dimensions:  ").append(this.frameWidth).append("\u00d7").append(this.frameHeight).append("px\n");
        stringBuilder.append("  Pixels/Frame:      ").append(this.totalPixelsPerFrame).append("px\u00b2\n");
        stringBuilder.append("  Complexity:        ").append(this.complexity).append("\n");
        stringBuilder.append("  Suggested Timing:  ").append(this.suggestedMs).append("ms/frame\n");
        stringBuilder.append("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n");
        return stringBuilder.toString();
    }
}
