/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;

public static class AnimationAndSpriteLoader.PlayerCharacterAnimations.BikerAnimations {
    public static final String CHARACTER = "biker";
    public static final String BASE_PATH = "Resources/industrial-zone/characters/player/biker";
    public static final Map<String, AnimationAndSpriteLoader.AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.AnimationConfig>(){
        {
            this.put("idle", new AnimationAndSpriteLoader.AnimationConfig(4, 150, "Standing breathing loop, default idle"));
            this.put("idle2", new AnimationAndSpriteLoader.AnimationConfig(5, 150, "Alternate standing posture"));
            this.put("walk", new AnimationAndSpriteLoader.AnimationConfig(5, 100, "Walking cycle, forward movement"));
            this.put("run", new AnimationAndSpriteLoader.AnimationConfig(5, 80, "Fast running, full cycle"));
            this.put("dash", new AnimationAndSpriteLoader.AnimationConfig(4, 60, "Quick dash, sliding forward"));
            this.put("jump", new AnimationAndSpriteLoader.AnimationConfig(3, 80, "Single jump arc start to apex"));
            this.put("doublejump", new AnimationAndSpriteLoader.AnimationConfig(5, 80, "Second jump mid-air"));
            this.put("fall", new AnimationAndSpriteLoader.AnimationConfig(4, 100, "Falling descent animation"));
            this.put("land", new AnimationAndSpriteLoader.AnimationConfig(2, 80, "Landing impact frame"));
            this.put("climb", new AnimationAndSpriteLoader.AnimationConfig(4, 120, "Ladder climbing cycle"));
            this.put("hang", new AnimationAndSpriteLoader.AnimationConfig(3, 150, "Hanging from ledge"));
            this.put("pullup", new AnimationAndSpriteLoader.AnimationConfig(7, 80, "Pulling self up onto platform"));
            this.put("punch", new AnimationAndSpriteLoader.AnimationConfig(6, 70, "Standing punch combo"));
            this.put("attack1", new AnimationAndSpriteLoader.AnimationConfig(5, 70, "First attack variation"));
            this.put("attack2", new AnimationAndSpriteLoader.AnimationConfig(6, 70, "Second attack with combo"));
            this.put("attack3", new AnimationAndSpriteLoader.AnimationConfig(7, 70, "Extended attack sequence"));
            this.put("walkattack", new AnimationAndSpriteLoader.AnimationConfig(5, 80, "Attacking while walking"));
            this.put("runattack", new AnimationAndSpriteLoader.AnimationConfig(6, 70, "Fast running attack"));
            this.put("hurt", new AnimationAndSpriteLoader.AnimationConfig(2, 100, "Recoil from damage"));
            this.put("death", new AnimationAndSpriteLoader.AnimationConfig(5, 120, "Death sequence, fall over"));
            this.put("use", new AnimationAndSpriteLoader.AnimationConfig(5, 100, "Using object interaction"));
            this.put("sitdown", new AnimationAndSpriteLoader.AnimationConfig(3, 120, "Sitting on ground"));
            this.put("angry", new AnimationAndSpriteLoader.AnimationConfig(5, 150, "Angry expression emote"));
            this.put("happy", new AnimationAndSpriteLoader.AnimationConfig(5, 150, "Happy/victorious emote"));
            this.put("talk", new AnimationAndSpriteLoader.AnimationConfig(5, 120, "Talking/dialogue animation"));
        }
    };

    public static AnimationAndSpriteLoader.GridFrameAnimationLoader loadAnimation(String string) {
        AnimationAndSpriteLoader.AnimationConfig animationConfig = ANIMATIONS.get(string);
        if (animationConfig == null) {
            AnimationAndSpriteLoader.logError("Biker animation not found: " + string);
            return null;
        }
        String string2 = String.format("%03d_Player_Biker_%s_%dFrames1Row_*.png", AnimationAndSpriteLoader.getAnimationIndex(string) + 1, AnimationAndSpriteLoader.formatAnimationName(string), animationConfig.frameCount);
        AnimationAndSpriteLoader.GridFrameAnimationLoader gridFrameAnimationLoader = new AnimationAndSpriteLoader.GridFrameAnimationLoader("player_biker_" + string, "Resources/industrial-zone/characters/player/biker/" + string2);
        if (gridFrameAnimationLoader.load()) {
            AnimationAndSpriteLoader.log("\u2713 Loaded Biker animation: " + string + " (" + animationConfig.frameCount + " frames, " + animationConfig.timingMs + "ms)");
        }
        return gridFrameAnimationLoader;
    }
}
