/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;
public class WeaponCyborgAnimations {
    public static final String CHARACTER = "Cyborg";
    public static final String ANIMATION_TYPE = "weapon_held";
    public static final String DIRECTORY = "Resources/industrial-zone/weapons/1/1 Characters/3 Cyborg";
    public static final int TOTAL_ANIMATIONS = 5;
    public static final Map<String, AnimationAndSpriteLoader.AnimationConfig> WEAPON_ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.AnimationConfig>(){
        {
            this.put("idle_a", new AnimationAndSpriteLoader.AnimationConfig(3, 50, "Cyborg standing still, armed with weapon"));
            this.put("idle_b", new AnimationAndSpriteLoader.AnimationConfig(3, 50, "Cyborg standing still variant 2, armed"));
            this.put("jump_a", new AnimationAndSpriteLoader.AnimationConfig(3, 80, "Cyborg jumping arc with weapon"));
            this.put("jump_b", new AnimationAndSpriteLoader.AnimationConfig(3, 80, "Cyborg jumping arc variant 2"));
            this.put("run_a", new AnimationAndSpriteLoader.AnimationConfig(5, 80, "Cyborg running with weapon"));
            this.put("run_b", new AnimationAndSpriteLoader.AnimationConfig(5, 80, "Cyborg running variant 2"));
            this.put("sitdown_a", new AnimationAndSpriteLoader.AnimationConfig(3, 120, "Cyborg sitting down, defensive position"));
            this.put("walk_a", new AnimationAndSpriteLoader.AnimationConfig(4, 100, "Cyborg walking with weapon"));
            this.put("walk_b", new AnimationAndSpriteLoader.AnimationConfig(4, 100, "Cyborg walking variant 2"));
        }
    };
}
