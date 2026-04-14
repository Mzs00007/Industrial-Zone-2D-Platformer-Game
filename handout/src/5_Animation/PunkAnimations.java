/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;

public static class AnimationAndSpriteLoader.PlayerCharacterAnimations.PunkAnimations {
    public static final String CHARACTER = "punk";
    public static final String BASE_PATH = "Resources/industrial-zone/characters/player/punk";
    public static final Map<String, AnimationAndSpriteLoader.AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.AnimationConfig>(){
        {
            this.put("idle", new AnimationAndSpriteLoader.AnimationConfig(5, 150, "Casual standing posture"));
            this.put("idle2", new AnimationAndSpriteLoader.AnimationConfig(5, 150, "Hip strut idle"));
            this.put("walk", new AnimationAndSpriteLoader.AnimationConfig(5, 100, "Confident walking style"));
            this.put("run", new AnimationAndSpriteLoader.AnimationConfig(6, 80, "Fast sprint running"));
            this.put("dash", new AnimationAndSpriteLoader.AnimationConfig(4, 60, "Quick slide forward"));
            this.put("jump", new AnimationAndSpriteLoader.AnimationConfig(3, 80, "High vertical jump"));
            this.put("doublejump", new AnimationAndSpriteLoader.AnimationConfig(4, 80, "Aerial flip jump"));
            this.put("fall", new AnimationAndSpriteLoader.AnimationConfig(3, 100, "Falling descent"));
            this.put("land", new AnimationAndSpriteLoader.AnimationConfig(2, 80, "Impact landing"));
            this.put("climb", new AnimationAndSpriteLoader.AnimationConfig(4, 120, "Parkour climbing"));
            this.put("hang", new AnimationAndSpriteLoader.AnimationConfig(4, 150, "Extended hanging"));
            this.put("pullup", new AnimationAndSpriteLoader.AnimationConfig(7, 80, "Acrobatic pull-up"));
            this.put("punch", new AnimationAndSpriteLoader.AnimationConfig(6, 70, "Quick combo punches"));
            this.put("attack1", new AnimationAndSpriteLoader.AnimationConfig(5, 70, "First combo attack"));
            this.put("attack2", new AnimationAndSpriteLoader.AnimationConfig(6, 70, "Second attack variant"));
            this.put("attack3", new AnimationAndSpriteLoader.AnimationConfig(6, 70, "Triple strike combo"));
            this.put("walkattack", new AnimationAndSpriteLoader.AnimationConfig(5, 80, "Walking attack flow"));
            this.put("runattack", new AnimationAndSpriteLoader.AnimationConfig(6, 70, "Running strike combo"));
            this.put("hurt", new AnimationAndSpriteLoader.AnimationConfig(2, 100, "Flinch reaction"));
            this.put("death", new AnimationAndSpriteLoader.AnimationConfig(5, 120, "Dramatic fall death"));
            this.put("use", new AnimationAndSpriteLoader.AnimationConfig(5, 100, "Casual object use"));
            this.put("sitdown", new AnimationAndSpriteLoader.AnimationConfig(3, 120, "Relaxed sitting"));
            this.put("angry", new AnimationAndSpriteLoader.AnimationConfig(5, 150, "Rage expression"));
            this.put("happy", new AnimationAndSpriteLoader.AnimationConfig(5, 150, "Victory celebration"));
            this.put("talk", new AnimationAndSpriteLoader.AnimationConfig(5, 120, "Casual talking"));
        }
    };

    public static AnimationAndSpriteLoader.GridFrameAnimationLoader loadAnimation(String string) {
        AnimationAndSpriteLoader.AnimationConfig animationConfig = ANIMATIONS.get(string);
        if (animationConfig == null) {
            AnimationAndSpriteLoader.logError("Punk animation not found: " + string);
            return null;
        }
        AnimationAndSpriteLoader.GridFrameAnimationLoader gridFrameAnimationLoader = new AnimationAndSpriteLoader.GridFrameAnimationLoader("player_punk_" + string, "Resources/industrial-zone/characters/player/punk/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_Player_Punk_" + AnimationAndSpriteLoader.formatAnimationName(string) + "_" + animationConfig.frameCount + "Frames1Row_*.png");
        if (gridFrameAnimationLoader.load()) {
            AnimationAndSpriteLoader.log("\u2713 Loaded Punk animation: " + string);
        }
        return gridFrameAnimationLoader;
    }
}
