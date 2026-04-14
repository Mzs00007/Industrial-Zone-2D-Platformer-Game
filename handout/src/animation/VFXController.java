/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;

public static class AnimationAndSpriteLoader.VFXController
extends AnimationAndSpriteLoader.EntityAnimationController {
    private long effectStartTime = System.currentTimeMillis();

    public AnimationAndSpriteLoader.VFXController(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody) {
        super(physicsBody);
    }

    @Override
    protected void initializeAssets() {
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.SPARKLE_BURST, "Resources/industrial-zone/vfx/1 Smoke/01_VFX_Smoke_Frame01_DenseThickCloud_SmokeAnim_Loop_80ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.IMPACT_HIT, "Resources/industrial-zone/vfx/2 Blood/01_VFX_Blood_Splatter_4Frames1Row_SmallWideSpread_Impact_PlayOnce_80ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.ENERGY_BEAM, "Resources/industrial-zone/vfx/3 Sparks/01_VFX_Sparks_Burst_4Frames1Row_SmallSparseGold_Impact_PlayOnce_80ms.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.EXPLOSION, "Resources/industrial-zone/vfx/4 Particles/01_VFX_Particles_Green_4Frames1Row_TinyGreenSparse_Ambient_Loop_100ms.png");
    }

    @Override
    protected void initializeTransitions() {
    }

    @Override
    protected void updatePhysicsForState(AnimationAndSpriteLoader.AnimationState animationState, float f) {
    }

    public boolean isComplete() {
        long l = System.currentTimeMillis() - this.effectStartTime;
        return l > (long)(this.currentState.frameCount * this.currentState.frameTimingMs);
    }

    public static String getVFXAssetPath(VFXType vFXType, String string) {
        switch (vFXType.ordinal()) {
            case 0: {
                return AnimationAndSpriteLoader.VFX_SMOKE + AnimationAndSpriteLoader.VFXController.getSmokeEffect(string);
            }
            case 1: {
                return AnimationAndSpriteLoader.VFX_BLOOD + AnimationAndSpriteLoader.VFXController.getBloodEffect(string);
            }
            case 2: {
                return AnimationAndSpriteLoader.VFX_SPARKS + AnimationAndSpriteLoader.VFXController.getSparkEffect(string);
            }
            case 3: {
                return AnimationAndSpriteLoader.VFX_PARTICLES + AnimationAndSpriteLoader.VFXController.getParticleEffect(string);
            }
            case 4: {
                return AnimationAndSpriteLoader.VFX_OTHER + AnimationAndSpriteLoader.VFXController.getStarEffect(string);
            }
            case 5: {
                return AnimationAndSpriteLoader.VFX_EXTRA_CHARACTER + AnimationAndSpriteLoader.VFXController.getCharacterEffect(string);
            }
            case 6: {
                return AnimationAndSpriteLoader.VFX_EXTRA_OBJECTS + AnimationAndSpriteLoader.VFXController.getDestructionEffect(string);
            }
        }
        return "Resources/industrial-zone/vfx/1 Smoke/01_VFX_Smoke_Frame01_DenseThickCloud_SmokeAnim_Loop_80ms.png";
    }

    private static String getSmokeEffect(String string) {
        switch (string.toUpperCase()) {
            case "DENSE": {
                return "01_VFX_Smoke_Frame01_DenseThickCloud_SmokeAnim_Loop_80ms.png";
            }
            case "MEDIUM": {
                return "02_VFX_Smoke_Frame10_MediumDensity_ThinningSmoke_Loop_90ms.png";
            }
            case "LIGHT": {
                return "03_VFX_Smoke_Frame18_LightDissipating_FadingSmoke_Loop_100ms.png";
            }
        }
        return "01_VFX_Smoke_Frame01_DenseThickCloud_SmokeAnim_Loop_80ms.png";
    }

    private static String getBloodEffect(String string) {
        switch (string.toUpperCase()) {
            case "SPLATTER_1": {
                return "01_VFX_Blood_Splatter_4Frames1Row_SmallWideSpread_Impact_PlayOnce_80ms.png";
            }
            case "SPLATTER_2": {
                return "02_VFX_Blood_Splatter_4Frames1Row_MediumBurst_ImpactMid_PlayOnce_80ms.png";
            }
            case "SPLATTER_3": {
                return "03_VFX_Blood_Splatter_4Frames1Row_LargeExplosion_ImpactBig_PlayOnce_80ms.png";
            }
            case "SPLATTER_4": {
                return "04_VFX_Blood_Splatter_4Frames1Row_VerticalSpray_ImpactUp_PlayOnce_80ms.png";
            }
            case "SPLATTER_5": {
                return "05_VFX_Blood_Splatter_4Frames1Row_DiagonalDrip_ImpactDown_PlayOnce_80ms.png";
            }
            case "SPLATTER_6": {
                return "06_VFX_Blood_Splatter_4Frames1Row_WideShatter_ImpactWide_PlayOnce_80ms.png";
            }
            case "SPLATTER_7": {
                return "07_VFX_Blood_Splatter_4Frames1Row_HeavyDrop_ImpactHeavy_PlayOnce_80ms.png";
            }
            case "SPLATTER_8": {
                return "08_VFX_Blood_Splatter_4Frames1Row_TinyScatter_ImpactSmall_PlayOnce_80ms.png";
            }
        }
        return "01_VFX_Blood_Splatter_4Frames1Row_SmallWideSpread_Impact_PlayOnce_80ms.png";
    }

    private static String getSparkEffect(String string) {
        switch (string.toUpperCase()) {
            case "GOLD_BURST": {
                return "01_VFX_Sparks_Burst_4Frames1Row_SmallSparseGold_Impact_PlayOnce_80ms.png";
            }
            case "RED_BURST": {
                return "02_VFX_Sparks_Burst_4Frames1Row_MediumIntenseRed_Impact_PlayOnce_80ms.png";
            }
            case "BLUE_BURST": {
                return "03_VFX_Sparks_Burst_4Frames1Row_LargeElectricBlue_Impact_PlayOnce_80ms.png";
            }
            case "WHITE_BURST": {
                return "04_VFX_Sparks_Burst_4Frames1Row_BrightWhiteFlash_Impact_PlayOnce_80ms.png";
            }
        }
        return "01_VFX_Sparks_Burst_4Frames1Row_SmallSparseGold_Impact_PlayOnce_80ms.png";
    }

    private static String getParticleEffect(String string) {
        switch (string.toUpperCase()) {
            case "GREEN": {
                return "01_VFX_Particles_Green_4Frames1Row_TinyGreenSparse_Ambient_Loop_100ms.png";
            }
            case "BLUE": {
                return "02_VFX_Particles_Blue_4Frames1Row_TinyBlueSparse_Ambient_Loop_100ms.png";
            }
            case "ORANGE": {
                return "03_VFX_Particles_Orange_4Frames1Row_TinyOrangeSparse_Ambient_Loop_100ms.png";
            }
            case "CYAN": {
                return "04_VFX_Particles_Cyan_4Frames1Row_TinyCyanDense_Energy_Loop_100ms.png";
            }
        }
        return "01_VFX_Particles_Green_4Frames1Row_TinyGreenSparse_Ambient_Loop_100ms.png";
    }

    private static String getStarEffect(String string) {
        switch (string.toUpperCase()) {
            case "STAR_BURST": {
                return "01_VFX_Stars_Burst_GoldenSparkles_CelebrationEffect_Loop.png";
            }
            case "CYAN_SHARDS": {
                return "02_VFX_Shards_Cyan_CrystalBreak_GlassEffect_PlayOnce.png";
            }
            case "ENERGY_SHARDS": {
                return "03_VFX_Shards_Electric_EnergyBurst_StaticCrackle_PlayOnce.png";
            }
        }
        return "01_VFX_Stars_Burst_GoldenSparkles_CelebrationEffect_Loop.png";
    }

    private static String getCharacterEffect(String string) {
        switch (string.toUpperCase()) {
            case "DEATH": {
                return "01_VFX_Character_Biker_Death_ExplosionRing_RedBlackSmoke_PlayOnce.png";
            }
            case "DOUBLEJUMP": {
                return "02_VFX_Character_Biker_DoubleJump_BlueEnergyRing_PropulsionAura_PlayOnce.png";
            }
            case "HURT": {
                return "03_VFX_Character_Biker_Hurt_RedFlash_DamageIndicator_PlayOnce.png";
            }
            case "JUMP": {
                return "04_VFX_Character_Biker_Jump_GroundDust_BootThrustSmoke_PlayOnce.png";
            }
            case "RUN": {
                return "05_VFX_Character_Biker_Run_SpeedLines_MomentumTrail_Loop.png";
            }
        }
        return "01_VFX_Character_Biker_Death_ExplosionRing_RedBlackSmoke_PlayOnce.png";
    }

    private static String getDestructionEffect(String string) {
        switch (string.toUpperCase()) {
            case "BOX_DESTROY_1": {
                return "01_VFX_Objects_Box1_Destruction_WoodSplinter_5Frame.png";
            }
            case "BOX_DESTROY_2": {
                return "02_VFX_Objects_Box2_Destruction_Crumble_4Frame.png";
            }
            case "BUSH_DESTROY": {
                return "03_VFX_Objects_Bush_Destruction_Leaves_4Frame.png";
            }
            case "CAPSULE_DESTROY": {
                return "04_VFX_Objects_Capsule_Destruction_Rupture_5Frame.png";
            }
            case "METAL_BREAK": {
                return "05_VFX_Objects_Metal_Destruction_Clang_3Frame.png";
            }
        }
        return "01_VFX_Objects_Box1_Destruction_WoodSplinter_5Frame.png";
    }

    public static enum VFXType {
        SMOKE,
        BLOOD,
        SPARKS,
        PARTICLES,
        STARS,
        CHARACTER_EFFECTS,
        DESTRUCTION;

    }
}
