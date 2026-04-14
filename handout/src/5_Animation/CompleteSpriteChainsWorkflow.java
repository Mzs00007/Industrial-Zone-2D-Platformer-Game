/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.CompleteSpriteChainsWorkflow {
    public static final String DOCUMENTATION = "Complete 10-phase workflow with visual diagram above";
    public static final String[] PHASE_SEQUENCE = new String[]{"PHASE 1: Load character base animation (24 types available)", "PHASE 2: Check if armed (load weapon overlay or melee)", "PHASE 3: Load weapon animation (10 types per character)", "PHASE 4: Get gun sprite (20 weapons, 5 tiers)", "PHASE 5: Get hand grip pose (10 angles \u00d7 3 characters)", "PHASE 6: Render armed character (4-layer composite)", "PHASE 7: On fire: spawn bullet sprite (13 types)", "PHASE 8: Display tracer effect entire flight (10 types)", "PHASE 9: On impact: spawn spark VFX (8 types, 4 frames each)", "PHASE 10: Clean up temporary sprites, return to Phase 1"};
}
