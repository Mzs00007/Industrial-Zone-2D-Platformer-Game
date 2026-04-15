/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;
public class ArmoredTruck {
    public static final String DRONE_TYPE = "armored_truck";
    public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/5";
    public static final Map<String, AnimationAndSpriteLoader.AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.AnimationConfig>(){
        {
            this.put("idle", new AnimationAndSpriteLoader.AnimationConfig(4, 150, "Engine idling stationary"));
            this.put("movement", new AnimationAndSpriteLoader.AnimationConfig(4, 100, "Forward driving movement"));
            this.put("death", new AnimationAndSpriteLoader.AnimationConfig(5, 120, "Vehicle destruction explosion"));
        }
    };

    public static AnimationAndSpriteLoader.GridFrameAnimationLoader loadAnimation(String string) {
        AnimationAndSpriteLoader.AnimationConfig animationConfig = ANIMATIONS.get(string);
        if (animationConfig == null) {
            return null;
        }
        AnimationAndSpriteLoader.GridFrameAnimationLoader gridFrameAnimationLoader = new AnimationAndSpriteLoader.GridFrameAnimationLoader("armored_truck_" + string, "Resources/industrial-zone/characters/enemies/drones/5/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_ArmoredTruck*" + animationConfig.frameCount + "*Frames1Row*.png");
        gridFrameAnimationLoader.load();
        return gridFrameAnimationLoader;
    }
}
