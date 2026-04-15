/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
public class InteractionZoneLoader
extends AnimationAndSpriteLoader.AssetType {
    private float centerX;
    private float centerY;
    private float radius;
    private float width;
    private float height;
    private ZoneShape shape;
    private char interactionKey;
    private boolean isActive;

    public InteractionZoneLoader(String string, float f, float f2, float f3, ZoneShape zoneShape, char c) {
        super(string, "");
        this.centerX = f;
        this.centerY = f2;
        this.radius = f3;
        this.shape = zoneShape;
        this.interactionKey = c;
        this.isActive = true;
    }

    @Override
    public boolean load() {
        AnimationAndSpriteLoader.log("\u2713 Interaction zone loaded: " + this.assetName);
        AnimationAndSpriteLoader.log("  Shape: " + this.shape.description);
        AnimationAndSpriteLoader.log("  Center: (" + this.centerX + ", " + this.centerY + ")");
        AnimationAndSpriteLoader.log("  Radius: " + this.radius + "px");
        AnimationAndSpriteLoader.log("  Key: '" + this.interactionKey + "'");
        return true;
    }

    public boolean isPlayerInZone(float f, float f2) {
        if (!this.isActive) {
            return false;
        }
        switch (this.shape.ordinal()) {
            case 0: {
                float f3 = (f - this.centerX) * (f - this.centerX) + (f2 - this.centerY) * (f2 - this.centerY);
                return f3 <= this.radius * this.radius;
            }
            case 1: {
                return f >= this.centerX - this.width / 2.0f && f <= this.centerX + this.width / 2.0f && f2 >= this.centerY - this.height / 2.0f && f2 <= this.centerY + this.height / 2.0f;
            }
        }
        return false;
    }

    public float[] getClosestPoint(float f, float f2) {
        if (this.shape == ZoneShape.CIRCLE) {
            float f3 = f - this.centerX;
            float f4 = f2 - this.centerY;
            float f5 = (float)Math.sqrt(f3 * f3 + f4 * f4);
            if (f5 == 0.0f) {
                return new float[]{this.centerX, this.centerY};
            }
            return new float[]{this.centerX + f3 / f5 * this.radius, this.centerY + f4 / f5 * this.radius};
        }
        return new float[]{this.centerX, this.centerY};
    }

    public void setActive(boolean bl) {
        this.isActive = bl;
    }

    public char getInteractionKey() {
        return this.interactionKey;
    }

    public ZoneShape getShape() {
        return this.shape;
    }

    @Override
    public BufferedImage getFrame(int n) {
        return null;
    }

    @Override
    public int getFrameCount() {
        return 1;
    }

    @Override
    public int getFrameWidth() {
        return (int)this.radius;
    }

    @Override
    public int getFrameHeight() {
        return (int)this.radius;
    }
public enum ZoneShape {
        CIRCLE("Circular interaction zone"),
        RECTANGLE("Rectangular interaction zone"),
        POLYGON("Complex polygon zone");

        public final String description;

        private ZoneShape(String string2) {
            this.description = string2;
        }
    }
}
