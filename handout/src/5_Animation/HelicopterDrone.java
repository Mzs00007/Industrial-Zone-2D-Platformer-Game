/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;

public static class AnimationAndSpriteLoader.DroneAnimationConfigs.HelicopterDrone {
    public static final String DRONE_TYPE = "helicopter";
    public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/4";
    public static final Map<String, AnimationAndSpriteLoader.AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.AnimationConfig>(){
        {
            this.put("idle", new AnimationAndSpriteLoader.AnimationConfig(4, 150, "Rotor spinning hovering"));
            this.put("patrol", new AnimationAndSpriteLoader.AnimationConfig(4, 100, "Patrol flight movement"));
            this.put("landing", new AnimationAndSpriteLoader.AnimationConfig(16, 80, "Extended landing sequence descent"));
            this.put("death", new AnimationAndSpriteLoader.AnimationConfig(1, 120, "Ghost silhouette death"));
        }
    };

    public static AnimationAndSpriteLoader.GridFrameAnimationLoader loadAnimation(String string) {
        AnimationAndSpriteLoader.AnimationConfig animationConfig = ANIMATIONS.get(string);
        if (animationConfig == null) {
            return null;
        }
        AnimationAndSpriteLoader.GridFrameAnimationLoader gridFrameAnimationLoader = new AnimationAndSpriteLoader.GridFrameAnimationLoader("helicopter_" + string, "Resources/industrial-zone/characters/enemies/drones/4/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_HelicopterDrone*" + animationConfig.frameCount + "*Frames1Row*.png");
        gridFrameAnimationLoader.load();
        return gridFrameAnimationLoader;
    }
}
