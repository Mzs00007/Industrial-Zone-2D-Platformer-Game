/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;
public class UfoSaucerDrone {
    public static final String DRONE_TYPE = "ufo_saucer";
    public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/1";
    public static final Map<String, AnimationAndSpriteLoader.AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.AnimationConfig>(){
        {
            this.put("idle", new AnimationAndSpriteLoader.AnimationConfig(4, 150, "UFO hovering in place with beam"));
            this.put("walk", new AnimationAndSpriteLoader.AnimationConfig(4, 100, "Horizontal hovering movement"));
            this.put("scan", new AnimationAndSpriteLoader.AnimationConfig(8, 100, "Beam scanning full rotation"));
            this.put("walkscan", new AnimationAndSpriteLoader.AnimationConfig(6, 100, "Moving while scanning area"));
            this.put("death", new AnimationAndSpriteLoader.AnimationConfig(5, 120, "Destruction sequence explosion"));
        }
    };

    public static AnimationAndSpriteLoader.GridFrameAnimationLoader loadAnimation(String string) {
        AnimationAndSpriteLoader.AnimationConfig animationConfig = ANIMATIONS.get(string);
        if (animationConfig == null) {
            return null;
        }
        AnimationAndSpriteLoader.GridFrameAnimationLoader gridFrameAnimationLoader = new AnimationAndSpriteLoader.GridFrameAnimationLoader("ufo_saucer_" + string, "Resources/industrial-zone/characters/enemies/drones/1/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_UfoSaucer*" + animationConfig.frameCount + "*Frames1Row*.png");
        gridFrameAnimationLoader.load();
        return gridFrameAnimationLoader;
    }
}
