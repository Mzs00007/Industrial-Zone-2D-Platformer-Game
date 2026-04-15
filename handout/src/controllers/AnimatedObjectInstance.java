/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
public class AnimatedObjectInstance {
    public String typeName;
    public float x;
    public float y;
    public float width;
    public float height;
    public int currentFrame;
    public float frameDuration;
    public float elapsedTime;
    public boolean isLooping;
    public boolean isActive;
    public int depth;
    public AnimationAndSpriteLoader.AssetType asset;
    public String assetPath;

    public AnimatedObjectInstance(String string, float f, float f2, float f3, float f4) {
        this.typeName = string;
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.currentFrame = 0;
        this.frameDuration = 100.0f;
        this.elapsedTime = 0.0f;
        this.isLooping = true;
        this.isActive = true;
        this.depth = 5;
        this.asset = null;
    }

    public void update(double d) {
        if (!this.isActive || this.asset == null) {
            return;
        }
        this.elapsedTime = (float)((double)this.elapsedTime + d * 1000.0);
        int n = this.asset.getFrameCount();
        while (this.elapsedTime >= this.frameDuration && n > 0) {
            this.elapsedTime -= this.frameDuration;
            ++this.currentFrame;
            if (this.currentFrame < n) continue;
            if (this.isLooping) {
                this.currentFrame = 0;
                continue;
            }
            this.currentFrame = n - 1;
            this.isActive = false;
            break;
        }
    }

    public BufferedImage getCurrentFrame() {
        if (this.asset == null || this.currentFrame < 0 || this.currentFrame >= this.asset.getFrameCount()) {
            return null;
        }
        return this.asset.getFrame(this.currentFrame);
    }

    public void setAnimation(float f, boolean bl) {
        this.frameDuration = f;
        this.isLooping = bl;
        this.currentFrame = 0;
        this.elapsedTime = 0.0f;
    }
}
