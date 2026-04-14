/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.SpriteChainSystems.InteractiveObjectChain {
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
