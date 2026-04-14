/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.AnimationState;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimatedCharacterProfile {
    public String characterName;
    public String characterPath;
    public String description;
    public int healthMax;
    public int damageBase;
    public int speedBase;
    public int armorBase;
    public String weaponType;
    public List<AnimationState> animationSequence;
    public Map<AnimationState, String> stateToAssetPath;
    public Map<AnimationState, Integer> stateToFrameCount;
    public Map<AnimationState, Integer> stateToDurationMs;
    public Map<AnimationState, BufferedImage[]> cachedFrames;
    private AnimationState currentAnimationState = AnimationState.IDLE;
    private int currentFrameIndex = 0;
    private long frameStartTime = 0L;

    public AnimatedCharacterProfile(String string, String string2) {
        this.characterName = string;
        this.characterPath = string2;
        this.animationSequence = new ArrayList<AnimationState>();
        this.stateToAssetPath = new HashMap<AnimationState, String>();
        this.stateToFrameCount = new HashMap<AnimationState, Integer>();
        this.stateToDurationMs = new HashMap<AnimationState, Integer>();
        this.cachedFrames = new HashMap<AnimationState, BufferedImage[]>();
        this.animationSequence.add(AnimationState.IDLE);
        this.animationSequence.add(AnimationState.WALK);
        this.animationSequence.add(AnimationState.ATTACK);
        this.animationSequence.add(AnimationState.JUMP);
        this.animationSequence.add(AnimationState.DOUBLE_JUMP);
        this.animationSequence.add(AnimationState.FALL);
        this.animationSequence.add(AnimationState.DASH);
        this.animationSequence.add(AnimationState.CLIMB);
        this.animationSequence.add(AnimationState.HANG);
        for (AnimationState animationState : AnimationState.values()) {
            this.stateToFrameCount.put(animationState, animationState.frameCount);
            this.stateToDurationMs.put(animationState, animationState.durationPerFrameMs);
        }
    }

    public void startAnimationCycle() {
        this.currentAnimationState = AnimationState.IDLE;
        this.currentFrameIndex = 0;
        this.frameStartTime = System.currentTimeMillis();
    }

    public void updateAnimationFrame() {
        if (this.frameStartTime == 0L) {
            this.startAnimationCycle();
            return;
        }
        long l = System.currentTimeMillis();
        long l2 = l - this.frameStartTime;
        AnimationState animationState = this.currentAnimationState;
        int n = this.stateToDurationMs.getOrDefault((Object)animationState, 100);
        int n2 = this.stateToFrameCount.getOrDefault((Object)animationState, 1) * n;
        if (l2 >= (long)n2) {
            int n3 = this.animationSequence.indexOf((Object)animationState);
            int n4 = (n3 + 1) % this.animationSequence.size();
            this.currentAnimationState = this.animationSequence.get(n4);
            this.currentFrameIndex = 0;
            this.frameStartTime = l;
        } else {
            int n5 = (int)(l2 / (long)n);
            int n6 = this.stateToFrameCount.getOrDefault((Object)animationState, 1);
            this.currentFrameIndex = Math.min(n5, n6 - 1);
        }
    }

    public AnimationState getCurrentAnimationState() {
        return this.currentAnimationState;
    }

    public int getCurrentFrameIndex() {
        return this.currentFrameIndex;
    }

    public int getCurrentFrameCount() {
        return this.stateToFrameCount.getOrDefault((Object)this.currentAnimationState, 1);
    }

    public void registerAnimation(AnimationState animationState, String string, int n, int n2) {
        this.stateToAssetPath.put(animationState, string);
        this.stateToFrameCount.put(animationState, n);
        this.stateToDurationMs.put(animationState, n2);
    }

    public void setCachedFrames(AnimationState animationState, BufferedImage[] bufferedImageArray) {
        this.cachedFrames.put(animationState, bufferedImageArray);
    }

    public BufferedImage[] getCachedFrames(AnimationState animationState) {
        return this.cachedFrames.get((Object)animationState);
    }

    public void setStats(int n, int n2, int n3, int n4) {
        this.healthMax = n;
        this.damageBase = n2;
        this.speedBase = n3;
        this.armorBase = n4;
    }

    public void setWeapon(String string) {
        this.weaponType = string;
    }

    public void setDescription(String string) {
        this.description = string;
    }
}
