/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimatedObjectManager {
    private static final String TAG = "[AnimatedObjectManager]";
    private static final boolean VERBOSE_LOGGING = true;
    private List<AnimatedObjectInstance> instances = new ArrayList<AnimatedObjectInstance>();
    private Map<String, AnimationAndSpriteLoader.AssetType> assetCache = new HashMap<String, AnimationAndSpriteLoader.AssetType>();
    private Map<String, String> assetMetadata = new HashMap<String, String>();
    private int maxInstances = 1000;

    public AnimatedObjectInstance spawn(String string, float f, float f2, float f3, float f4) {
        if (this.instances.size() >= this.maxInstances) {
            this.logWarning("Maximum instances reached (" + this.maxInstances + "), skipping spawn");
            return null;
        }
        AnimatedObjectInstance animatedObjectInstance = new AnimatedObjectInstance(string, f, f2, f3, f4);
        try {
            AnimationAndSpriteLoader.AssetType assetType = AnimationAndSpriteLoader.load(string, string);
            if (assetType != null) {
                animatedObjectInstance.asset = assetType;
                animatedObjectInstance.frameDuration = 100.0f;
                this.log("Spawned: " + string + " at (" + f + ", " + f2 + ") - " + assetType.getFrameCount() + " frames");
            } else {
                this.logWarning("Animation not found: " + string);
            }
        }
        catch (Exception exception) {
            this.logError("Failed to spawn " + string + ": " + exception.getMessage());
            return null;
        }
        this.instances.add(animatedObjectInstance);
        return animatedObjectInstance;
    }

    public AnimatedObjectInstance spawnFromAsset(String string, float f, float f2, float f3, float f4) {
        String string2 = string.replaceAll("[^a-zA-Z0-9_]", "_");
        try {
            AnimationAndSpriteLoader.AssetType assetType = AnimationAndSpriteLoader.load(string2, string);
            if (assetType != null) {
                this.assetCache.put(string, assetType);
            }
        }
        catch (Exception exception) {
            this.logError("Failed to load asset: " + string);
            return null;
        }
        return this.spawn(string2, f, f2, f3, f4);
    }

    public void update(double d) {
        for (AnimatedObjectInstance animatedObjectInstance : this.instances) {
            if (!animatedObjectInstance.isActive) continue;
            animatedObjectInstance.update(d);
        }
    }

    public void cleanup() {
        this.instances.removeIf(animatedObjectInstance -> !animatedObjectInstance.isActive);
    }

    public List<AnimatedObjectInstance> getActiveInstances() {
        ArrayList<AnimatedObjectInstance> arrayList = new ArrayList<AnimatedObjectInstance>();
        for (AnimatedObjectInstance animatedObjectInstance3 : this.instances) {
            if (!animatedObjectInstance3.isActive) continue;
            arrayList.add(animatedObjectInstance3);
        }
        arrayList.sort((animatedObjectInstance, animatedObjectInstance2) -> Integer.compare(animatedObjectInstance.depth, animatedObjectInstance2.depth));
        return arrayList;
    }

    public int getInstanceCount() {
        return this.instances.size();
    }

    public int getActiveCount() {
        return (int)this.instances.stream().filter(animatedObjectInstance -> animatedObjectInstance.isActive).count();
    }

    public AnimatedObjectInstance getInstance(int n) {
        if (n >= 0 && n < this.instances.size()) {
            return this.instances.get(n);
        }
        return null;
    }

    public List<AnimatedObjectInstance> getByType(String string) {
        ArrayList<AnimatedObjectInstance> arrayList = new ArrayList<AnimatedObjectInstance>();
        for (AnimatedObjectInstance animatedObjectInstance : this.instances) {
            if (!animatedObjectInstance.typeName.equals(string)) continue;
            arrayList.add(animatedObjectInstance);
        }
        return arrayList;
    }

    public List<AnimatedObjectInstance> getAtPosition(float f, float f2) {
        ArrayList<AnimatedObjectInstance> arrayList = new ArrayList<AnimatedObjectInstance>();
        for (AnimatedObjectInstance animatedObjectInstance : this.instances) {
            if (!(animatedObjectInstance.x <= f) || !(f < animatedObjectInstance.x + animatedObjectInstance.width) || !(animatedObjectInstance.y <= f2) || !(f2 < animatedObjectInstance.y + animatedObjectInstance.height)) continue;
            arrayList.add(animatedObjectInstance);
        }
        return arrayList;
    }

    public void stop(AnimatedObjectInstance animatedObjectInstance) {
        if (animatedObjectInstance != null) {
            animatedObjectInstance.isActive = false;
        }
    }

    public void stopAll() {
        for (AnimatedObjectInstance animatedObjectInstance : this.instances) {
            animatedObjectInstance.isActive = false;
        }
    }

    public void resume(AnimatedObjectInstance animatedObjectInstance) {
        if (animatedObjectInstance != null) {
            animatedObjectInstance.isActive = true;
        }
    }

    public void reset(AnimatedObjectInstance animatedObjectInstance) {
        if (animatedObjectInstance != null) {
            animatedObjectInstance.currentFrame = 0;
            animatedObjectInstance.elapsedTime = 0.0f;
            animatedObjectInstance.isActive = true;
        }
    }

    public void clear() {
        this.instances.clear();
        this.assetCache.clear();
    }

    private void log(String string) {
        System.out.println("[AnimatedObjectManager] " + string);
    }

    private void logWarning(String string) {
        System.out.println("[AnimatedObjectManager] WARNING: " + string);
    }

    private void logError(String string) {
        System.err.println("[AnimatedObjectManager] ERROR: " + string);
    }

    public static class AnimatedObjectInstance {
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
}
