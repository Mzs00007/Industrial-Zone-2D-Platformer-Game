/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.LinkedHashMap;
import java.util.Map;

public static class AnimationAndSpriteLoader.WeaponBikerAnimations {
    public static final String CHARACTER = "Biker";
    public static final String ANIMATION_TYPE = "weapon_held";
    public static final String DIRECTORY = "Resources/industrial-zone/weapons/1/1 Characters/1 Biker";
    public static final int TOTAL_ANIMATIONS = 5;
    public static final int IDLE_VARIANT_A_FRAMES = 4;
    public static final int IDLE_VARIANT_A_TIMING_MS = 50;
    public static final String IDLE_VARIANT_A_FILE = "01_Weapon_Biker_Idle_VariantA_4Frames1Row_WeaponIdleStand_Loop_1_50ms.png";
    public static final int IDLE_VARIANT_B_FRAMES = 4;
    public static final int IDLE_VARIANT_B_TIMING_MS = 50;
    public static final String IDLE_VARIANT_B_FILE = "02_Weapon_Biker_Idle_VariantB_4Frames1Row_WeaponIdleStand_Loop_1_50ms.png";
    public static final int JUMP_VARIANT_A_FRAMES = 3;
    public static final int JUMP_VARIANT_A_TIMING_MS = 80;
    public static final String JUMP_VARIANT_A_FILE = "03_Weapon_Biker_Jump_VariantA_3Frames1Row_WeaponJumpArcPlayOnce_80ms.png";
    public static final int JUMP_VARIANT_B_FRAMES = 3;
    public static final int JUMP_VARIANT_B_TIMING_MS = 80;
    public static final String JUMP_VARIANT_B_FILE = "04_Weapon_Biker_Jump_VariantB_3Frames1Row_WeaponJumpArcPlayOnce_80ms.png";
    public static final int RUN_VARIANT_A_FRAMES = 5;
    public static final int RUN_VARIANT_A_TIMING_MS = 80;
    public static final String RUN_VARIANT_A_FILE = "05_Weapon_Biker_Run_VariantA_5Frames1Row_WeaponRunCycle_Loop_1_80ms.png";
    public static final int RUN_VARIANT_B_FRAMES = 5;
    public static final int RUN_VARIANT_B_TIMING_MS = 80;
    public static final String RUN_VARIANT_B_FILE = "06_Weapon_Biker_Run_VariantB_5Frames1Row_WeaponRunCycle_Loop_1_80ms.png";
    public static final int SITDOWN_VARIANT_A_FRAMES = 4;
    public static final int SITDOWN_VARIANT_A_TIMING_MS = 120;
    public static final String SITDOWN_VARIANT_A_FILE = "07_Weapon_Biker_Sitdown_VariantA_4Frames1Row_WeaponSitDown_PlayOnce_120ms.png";
    public static final int WALK_VARIANT_A_FRAMES = 4;
    public static final int WALK_VARIANT_A_TIMING_MS = 100;
    public static final String WALK_VARIANT_A_FILE = "09_Weapon_Biker_Walk_VariantA_4Frames1Row_WeaponWalkCycle_Loop_100ms.png";
    public static final int WALK_VARIANT_B_FRAMES = 4;
    public static final int WALK_VARIANT_B_TIMING_MS = 100;
    public static final String WALK_VARIANT_B_FILE = "10_Weapon_Biker_Walk_VariantB_4Frames1Row_WeaponWalkCycle_Loop_100ms.png";
    public static final Map<String, AnimationAndSpriteLoader.AnimationConfig> WEAPON_ANIMATIONS = new LinkedHashMap<String, AnimationAndSpriteLoader.AnimationConfig>(){
        {
            this.put("idle_a", new AnimationAndSpriteLoader.AnimationConfig(4, 50, "Biker standing still, armed with weapon"));
            this.put("idle_b", new AnimationAndSpriteLoader.AnimationConfig(4, 50, "Biker standing still variant 2, armed"));
            this.put("jump_a", new AnimationAndSpriteLoader.AnimationConfig(3, 80, "Biker jumping arc with weapon"));
            this.put("jump_b", new AnimationAndSpriteLoader.AnimationConfig(3, 80, "Biker jumping arc variant 2"));
            this.put("run_a", new AnimationAndSpriteLoader.AnimationConfig(5, 80, "Biker running with weapon"));
            this.put("run_b", new AnimationAndSpriteLoader.AnimationConfig(5, 80, "Biker running variant 2"));
            this.put("sitdown_a", new AnimationAndSpriteLoader.AnimationConfig(4, 120, "Biker sitting down, defensive position"));
            this.put("walk_a", new AnimationAndSpriteLoader.AnimationConfig(4, 100, "Biker walking with weapon"));
            this.put("walk_b", new AnimationAndSpriteLoader.AnimationConfig(4, 100, "Biker walking variant 2"));
        }
    };
}
