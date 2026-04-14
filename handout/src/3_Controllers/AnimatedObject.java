/*
 * Decompiled with CFR 0.152.
 */
package rendering;

public static class ComprehensiveTileMapLoader.AnimatedObject {
    public String name;
    public String filePath;
    public int frameCount;
    public int frameWidth;
    public int frameHeight;
    public int animationSpeed;

    public ComprehensiveTileMapLoader.AnimatedObject(String string, String string2) {
        this.name = string;
        this.filePath = string2;
        this.frameCount = 1;
        this.frameWidth = 32;
        this.frameHeight = 32;
        this.animationSpeed = 100;
    }

    public String toString() {
        return this.name + " [" + this.frameWidth + "x" + this.frameHeight + "px, " + this.frameCount + " frames]";
    }
}
