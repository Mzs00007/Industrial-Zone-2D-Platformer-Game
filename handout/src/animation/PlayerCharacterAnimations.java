/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;

public static class AnimationAndSpriteLoader.PlayerCharacterAnimations {

    public static class PunkAnimations {
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

    public static class CyborgAnimations {
        public static final String CHARACTER = "cyborg";
        public static final String BASE_PATH = "Resources/industrial-zone/characters/player/cyborg";
        public static final Map<String, AnimationAndSpriteLoader.AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.AnimationConfig>(){
            {
                this.put("idle", new AnimationAndSpriteLoader.AnimationConfig(4, 150, "Standing tech breathing"));
                this.put("idle2", new AnimationAndSpriteLoader.AnimationConfig(5, 150, "Secondary idle with arm movement"));
                this.put("walk", new AnimationAndSpriteLoader.AnimationConfig(5, 100, "Tech-enhanced walking"));
                this.put("run", new AnimationAndSpriteLoader.AnimationConfig(5, 80, "Fast synchronized running"));
                this.put("dash", new AnimationAndSpriteLoader.AnimationConfig(4, 60, "Boost dash with tech effect"));
                this.put("jump", new AnimationAndSpriteLoader.AnimationConfig(3, 80, "Powered jump arc"));
                this.put("doublejump", new AnimationAndSpriteLoader.AnimationConfig(5, 80, "Second mid-air jump"));
                this.put("fall", new AnimationAndSpriteLoader.AnimationConfig(3, 100, "Falling with stabilization"));
                this.put("land", new AnimationAndSpriteLoader.AnimationConfig(2, 80, "Powered landing impact"));
                this.put("climb", new AnimationAndSpriteLoader.AnimationConfig(4, 120, "Climbing with arm assists"));
                this.put("hang", new AnimationAndSpriteLoader.AnimationConfig(3, 150, "Hanging with grip strength"));
                this.put("pullup", new AnimationAndSpriteLoader.AnimationConfig(7, 80, "Powered pull-up animation"));
                this.put("punch", new AnimationAndSpriteLoader.AnimationConfig(5, 70, "Enhanced punch combo"));
                this.put("attack1", new AnimationAndSpriteLoader.AnimationConfig(5, 70, "First cyber attack"));
                this.put("attack2", new AnimationAndSpriteLoader.AnimationConfig(6, 70, "Extended combo attack"));
                this.put("attack3", new AnimationAndSpriteLoader.AnimationConfig(6, 70, "Triple attack variation"));
                this.put("walkattack", new AnimationAndSpriteLoader.AnimationConfig(5, 80, "Walking attack movement"));
                this.put("runattack", new AnimationAndSpriteLoader.AnimationConfig(5, 70, "Running strike"));
                this.put("hurt", new AnimationAndSpriteLoader.AnimationConfig(2, 100, "Damage reaction"));
                this.put("death", new AnimationAndSpriteLoader.AnimationConfig(5, 120, "Shutdown sequence"));
                this.put("use", new AnimationAndSpriteLoader.AnimationConfig(5, 100, "Tech interface interaction"));
                this.put("sitdown", new AnimationAndSpriteLoader.AnimationConfig(3, 120, "Crouching posture"));
                this.put("angry", new AnimationAndSpriteLoader.AnimationConfig(5, 150, "Aggression mode emote"));
                this.put("happy", new AnimationAndSpriteLoader.AnimationConfig(5, 150, "System success emote"));
                this.put("talk", new AnimationAndSpriteLoader.AnimationConfig(5, 120, "Voice synthesis talk"));
            }
        };

        public static AnimationAndSpriteLoader.GridFrameAnimationLoader loadAnimation(String string) {
            AnimationAndSpriteLoader.AnimationConfig animationConfig = ANIMATIONS.get(string);
            if (animationConfig == null) {
                AnimationAndSpriteLoader.logError("Cyborg animation not found: " + string);
                return null;
            }
            AnimationAndSpriteLoader.GridFrameAnimationLoader gridFrameAnimationLoader = new AnimationAndSpriteLoader.GridFrameAnimationLoader("player_cyborg_" + string, "Resources/industrial-zone/characters/player/cyborg/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_Player_Cyborg_" + AnimationAndSpriteLoader.formatAnimationName(string) + "_" + animationConfig.frameCount + "Frames1Row_*.png");
            if (gridFrameAnimationLoader.load()) {
                AnimationAndSpriteLoader.log("\u2713 Loaded Cyborg animation: " + string);
            }
            return gridFrameAnimationLoader;
        }
    }

    public static class BikerAnimations {
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
}
