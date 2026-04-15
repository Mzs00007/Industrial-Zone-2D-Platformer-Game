/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;
public class HoverShooterDrone {
    public static final String DRONE_TYPE = "hovershooter";
    public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/3";
    public static final Map<String, AnimationAndSpriteLoader.AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.AnimationConfig>(){
        {
            this.put("idle", new AnimationAndSpriteLoader.AnimationConfig(4, 150, "Stationary floating idle"));
            this.put("forward", new AnimationAndSpriteLoader.AnimationConfig(4, 100, "Moving forward while tracking"));
            this.put("back", new AnimationAndSpriteLoader.AnimationConfig(4, 100, "Reversing retreat movement"));
            this.put("fire1", new AnimationAndSpriteLoader.AnimationConfig(16, 50, "Rapid burst fire mode 1"));
            this.put("fire2", new AnimationAndSpriteLoader.AnimationConfig(16, 50, "Rapid burst fire mode 2"));
            this.put("fire3", new AnimationAndSpriteLoader.AnimationConfig(16, 50, "Rapid burst fire mode 3"));
            this.put("death", new AnimationAndSpriteLoader.AnimationConfig(8, 80, "Explosion death sequence"));
        }
    };

    public static AnimationAndSpriteLoader.GridFrameAnimationLoader loadAnimation(String string) {
        AnimationAndSpriteLoader.AnimationConfig animationConfig = ANIMATIONS.get(string);
        if (animationConfig == null) {
            return null;
        }
        AnimationAndSpriteLoader.GridFrameAnimationLoader gridFrameAnimationLoader = new AnimationAndSpriteLoader.GridFrameAnimationLoader("hovershooter_" + string, "Resources/industrial-zone/characters/enemies/drones/3/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_HoverShooter*" + animationConfig.frameCount + "*Frames1Row*.png");
        gridFrameAnimationLoader.load();
        return gridFrameAnimationLoader;
    }
}
