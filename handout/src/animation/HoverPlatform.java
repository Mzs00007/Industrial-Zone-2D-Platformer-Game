/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;
public class HoverPlatform {
    public static final String DRONE_TYPE = "hover_platform";
    public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/6";
    public static final Map<String, AnimationAndSpriteLoader.AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.AnimationConfig>(){
        {
            this.put("walk", new AnimationAndSpriteLoader.AnimationConfig(4, 150, "Primary hovering movement"));
            this.put("walk2", new AnimationAndSpriteLoader.AnimationConfig(4, 100, "Alternative movement pattern"));
            this.put("drop", new AnimationAndSpriteLoader.AnimationConfig(5, 100, "Platform deployment sequence"));
            this.put("capsule", new AnimationAndSpriteLoader.AnimationConfig(7, 100, "Capsule projectile launch"));
        }
    };

    public static AnimationAndSpriteLoader.GridFrameAnimationLoader loadAnimation(String string) {
        AnimationAndSpriteLoader.AnimationConfig animationConfig = ANIMATIONS.get(string);
        if (animationConfig == null) {
            return null;
        }
        AnimationAndSpriteLoader.GridFrameAnimationLoader gridFrameAnimationLoader = new AnimationAndSpriteLoader.GridFrameAnimationLoader("hover_platform_" + string, "Resources/industrial-zone/characters/enemies/drones/6/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_HoverPlatform*" + animationConfig.frameCount + "*Frames1Row*.png");
        gridFrameAnimationLoader.load();
        return gridFrameAnimationLoader;
    }
}
