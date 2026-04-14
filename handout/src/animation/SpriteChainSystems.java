/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.SpriteChainSystems {
    public static final String REGISTRY_TYPE = "sprite_chain";

    public static class InteractiveObjectChain {
        public static final String CHAIN_NAME = "interactive_object";
        public static final String STEP_1_COLLISION = "Character collides with collectible";
        public static final String STEP_1_EXAMPLE = "   Example: CollectibleMoney at position (100, 50)";
        public static final String STEP_2_DETECT = "Identify object type from metadata";
        public static final String STEP_2_EXAMPLE = "   Type = 'collectible_money' from InteractiveObjectProperties.CollectibleMoney.OBJECT_TYPE";
        public static final String STEP_3_ANIMATION = "Load animation sequence for effect";
        public static final String STEP_3_EXAMPLE = "   File: InteractiveObjectProperties.CollectibleMoney.FILE";
        public static final String STEP_3_FRAMES = "   6 frames, 80ms each = fast spinning pickup effect";
        public static final String STEP_4_EFFECT = "Play effect immediately or on contact";
        public static final String STEP_4_EXAMPLE = "   Money gives immediate bonus score";
        public static final String STEP_4_EXAMPLE_2 = "   Card might trigger special power-up sequence";
        public static final String STEP_5_VFX = "Play associated VFX if impact-based";
        public static final String STEP_5_EXAMPLE = "   CollectibleCard might trigger special glow effect";
        public static final String STEP_6_AUDIO = "Play pickup sound effect";
        public static final String STEP_6_EXAMPLE = "   Different sounds for money vs cards (coming in audio batch)";
    }

    public static class CharacterVisualChain {
        public static final String CHAIN_NAME = "character_visual";
        public static final String START_POINT = "PlayerCharacterProperties.BIKER/CYBORG/PUNK";
        public static final String STEP_1_BASE = "PlayerCharacterAnimations - Load character movement animations";
        public static final String STEP_1_DETAIL = "   Example: BikerAnimations.IDLE";
        public static final String STEP_2_CURRENT_STATE = "Select animation based on current action";
        public static final String STEP_2_DETAIL = "   If idle: use IDLE animation (4 frames)";
        public static final String STEP_2_DETAIL_2 = "   If running: use RUN animation (5 frames, 80ms)";
        public static final String STEP_3_CHECK_ARMED = "Check if character is holding weapon";
        public static final String STEP_3A_UNARMED = "FALSE: Render base character animation only";
        public static final String STEP_3B_ARMED = "TRUE: Proceed to weapon assembly chain";
        public static final String STEP_4_WEAPON_TYPE = "Get equipped weapon (GunProperties)";
        public static final String STEP_4_DETAIL = "   Example: GUN_PISTOL_TYPE_A_DARK";
        public static final String STEP_5_WEAPON_ANIMATION = "Get weapon animation for current state";
        public static final String STEP_5_DETAIL = "   Example: WeaponBikerAnimations.IDLE_VARIANT_A if idle with gun";
        public static final String STEP_6_HAND_POSE = "Get hand grip pose for current aiming angle";
        public static final String STEP_6_DETAIL = "   Example: WeaponHandPoses.BikerHands.GRIP_HORIZONTAL (0\u00b0)";
        public static final String STEP_7_RENDER = "Render: Base Animation + Weapon Animation + Hand Pose + Gun Sprite";
        public static final String STEP_7_DETAIL = "   Composite renders all 4 layers";
    }

    public static class WeaponFireChain {
        public static final String CHAIN_NAME = "weapon_fire";
        public static final String START_POINT = "GunProperties.GUN_[TYPE]_[VARIANT]";
        public static final String STEP_1_WEAPON_SELECT = "GunProperties - Select specific gun file";
        public static final String STEP_2_CHARACTER_EQUIP = "WeaponCharacterAnimations - Load character weapon idle animation";
        public static final String STEP_2_DETAIL = "   Example: WeaponBikerAnimations.IDLE_VARIANT_A";
        public static final String STEP_3_HAND_GRIP = "WeaponHandPoses - Apply grip pose overlay";
        public static final String STEP_3_DETAIL = "   Example: WeaponHandPoses.BikerHands.GRIP_HORIZONTAL";
        public static final String STEP_4_AIMING = "WeaponHandPoses - Cycle through 10 grip angles as player aims";
        public static final String STEP_4_DETAIL = "   Grips 0-9 cover full 360\u00b0 aiming circle";
        public static final String STEP_5_FIRE = "WeaponCharacterAnimations - Play fire/attack animation";
        public static final String STEP_5_DETAIL = "   Example: WeaponBikerAnimations.FIRE_VARIANT_A";
        public static final String STEP_6_PROJECTILE = "BulletProperties - Spawn ammo sprite";
        public static final String STEP_6_DETAIL = "   Example: BulletProperties.BULLET_TYPE_A (brass round)";
        public static final String STEP_7_TRACER = "ProjectileTracerProperties - Show bullet trajectory";
        public static final String STEP_7_DETAIL = "   Example: ProjectileTracerProperties.TRACER_TYPE_A_VARIANT_NARROW";
        public static final String STEP_8_IMPACT = "VfxAssetProperties - Play collision effect";
        public static final String STEP_8_DETAIL = "   Example: VfxAssetProperties.IMPACT_SPARK_BURST_GOLD_VAR1";
        public static final String STEP_9_DAMAGE = "Character properties - Apply damage value";
        public static final String STEP_9_DETAIL = "   Determines gameplay outcome";
    }
}
