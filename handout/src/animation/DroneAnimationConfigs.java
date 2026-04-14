/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;

public static class AnimationAndSpriteLoader.DroneAnimationConfigs {

    public static class HoverPlatform {
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

    public static class ArmoredTruckVariant {
        public static final String DRONE_TYPE = "armored_truck_variant";
        public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/5_2";
        public static final Map<String, AnimationAndSpriteLoader.AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.AnimationConfig>(){
            {
                this.put("idle", new AnimationAndSpriteLoader.AnimationConfig(4, 150, "Variant idle engined"));
                this.put("movement", new AnimationAndSpriteLoader.AnimationConfig(4, 100, "Variant driving movement"));
                this.put("death", new AnimationAndSpriteLoader.AnimationConfig(5, 120, "Variant destruction sequence"));
            }
        };

        public static AnimationAndSpriteLoader.GridFrameAnimationLoader loadAnimation(String string) {
            AnimationAndSpriteLoader.AnimationConfig animationConfig = ANIMATIONS.get(string);
            if (animationConfig == null) {
                return null;
            }
            AnimationAndSpriteLoader.GridFrameAnimationLoader gridFrameAnimationLoader = new AnimationAndSpriteLoader.GridFrameAnimationLoader("armored_truck_variant_" + string, "Resources/industrial-zone/characters/enemies/drones/5_2/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_ArmoredTruckVariant*" + animationConfig.frameCount + "*Frames1Row*.png");
            gridFrameAnimationLoader.load();
            return gridFrameAnimationLoader;
        }
    }

    public static class ArmoredTruck {
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

    public static class HelicopterDrone {
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

    public static class HoverShooterDrone {
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

    public static class JetDrone {
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

    public static class UfoSaucerDrone {
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
}
