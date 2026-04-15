/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;
public class CyborgAnimations {
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
