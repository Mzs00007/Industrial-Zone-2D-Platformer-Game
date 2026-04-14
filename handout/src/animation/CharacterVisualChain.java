/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.SpriteChainSystems.CharacterVisualChain {
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
