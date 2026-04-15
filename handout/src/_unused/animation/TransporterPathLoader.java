/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
public class TransporterPathLoader
extends AnimationAndSpriteLoader.AssetType {
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private long duration;
    private PathType pathType;
    private List<float[]> waypoints;

    public TransporterPathLoader(String string, float f, float f2, float f3, float f4, long l, PathType pathType) {
        super(string, "");
        this.startX = f;
        this.startY = f2;
        this.endX = f3;
        this.endY = f4;
        this.duration = l;
        this.pathType = pathType;
        this.waypoints = new ArrayList<float[]>();
    }

    @Override
    public boolean load() {
        try {
            this.generateWaypoints();
            AnimationAndSpriteLoader.log("\u2713 Path loaded: " + this.assetName);
            AnimationAndSpriteLoader.log("  From: (" + this.startX + ", " + this.startY + ") \u2192 To: (" + this.endX + ", " + this.endY + ")");
            AnimationAndSpriteLoader.log("  Duration: " + this.duration + "ms");
            AnimationAndSpriteLoader.log("  Type: " + this.pathType.description);
            AnimationAndSpriteLoader.log("  Waypoints: " + this.waypoints.size());
            return true;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to load path: " + this.assetName);
            AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
            return false;
        }
    }

    private void generateWaypoints() {
        this.waypoints.clear();
        int n = Math.max(10, (int)(this.duration / 50L));
        for (int i = 0; i <= n; ++i) {
            float f;
            float f2 = (float)i / (float)n;
            this.waypoints.add(new float[]{f, switch (this.pathType.ordinal()) {
                case 0 -> {
                    f = this.startX + (this.endX - this.startX) * f2;
                    yield this.startY + (this.endY - this.startY) * f2;
                }
                case 1 -> {
                    float var6_6 = (this.startX + this.endX) / 2.0f;
                    float var7_7 = Math.min(this.startY, this.endY) - 100.0f;
                    f = (1.0f - f2) * (1.0f - f2) * this.startX + 2.0f * (1.0f - f2) * f2 * var6_6 + f2 * f2 * this.endX;
                    yield (1.0f - f2) * (1.0f - f2) * this.startY + 2.0f * (1.0f - f2) * f2 * var7_7 + f2 * f2 * this.endY;
                }
                case 2 -> {
                    f = this.startX + (this.endX - this.startX) * f2;
                    float var8_8 = Math.min(this.startY, this.endY) - 150.0f;
                    yield this.startY + (var8_8 - this.startY) * (4.0f * f2 * (1.0f - f2)) + (this.endY - this.startY) * f2;
                }
                default -> {
                    f = this.startX + (this.endX - this.startX) * f2;
                    yield this.startY + (this.endY - this.startY) * f2;
                }
            }});
        }
    }

    public float[] getPositionAtTime(long l) {
        if (l <= 0L) {
            return new float[]{this.startX, this.startY};
        }
        if (l >= this.duration) {
            return new float[]{this.endX, this.endY};
        }
        float f = (float)l / (float)this.duration;
        int n = (int)(f * (float)(this.waypoints.size() - 1));
        float[] fArray = this.waypoints.get(Math.min(n, this.waypoints.size() - 1));
        return new float[]{fArray[0], fArray[1]};
    }

    public float getProgressPercent(long l) {
        return Math.min(1.0f, (float)l / (float)this.duration);
    }

    public boolean isComplete(long l) {
        return l >= this.duration;
    }

    @Override
    public BufferedImage getFrame(int n) {
        return null;
    }

    @Override
    public int getFrameCount() {
        return (int)(this.duration / 50L);
    }

    @Override
    public int getFrameWidth() {
        return 0;
    }

    @Override
    public int getFrameHeight() {
        return 0;
    }
public enum PathType {
        LINEAR("Straight line movement"),
        CURVED("Smooth bezier curve path"),
        PARABOLIC("Arc-shaped movement");

        public final String description;

        private PathType(String string2) {
            this.description = string2;
        }
    }
}
