/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;
public class JetDrone {
    public static final String DRONE_TYPE = "jet_drone";
    public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/2";
    public static final Map<String, AnimationAndSpriteLoader.AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.AnimationConfig>(){
        {
            this.put("fly", new AnimationAndSpriteLoader.AnimationConfig(6, 80, "Aerial flight forward movement"));
            this.put("bomb", new AnimationAndSpriteLoader.AnimationConfig(8, 80, "Bomb projectile payload drop"));
        }
    };

    public static AnimationAndSpriteLoader.GridFrameAnimationLoader loadAnimation(String string) {
        AnimationAndSpriteLoader.AnimationConfig animationConfig = ANIMATIONS.get(string);
        if (animationConfig == null) {
            return null;
        }
        AnimationAndSpriteLoader.GridFrameAnimationLoader gridFrameAnimationLoader = new AnimationAndSpriteLoader.GridFrameAnimationLoader("jet_drone_" + string, "Resources/industrial-zone/characters/enemies/drones/2/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_JetDrone*" + animationConfig.frameCount + "*Frames1Row*.png");
        gridFrameAnimationLoader.load();
        return gridFrameAnimationLoader;
    }
}
