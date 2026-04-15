/*
 * Decompiled with CFR 0.152.
 */
package animation;
public final class Level1SpecificWorkflow {
    public static final String LEVEL = "Industrial Zone Level 1";
    public static final String VARIANT_TYPE = "SINGLE STATIC VARIANT";
    public static final String SIMPLIFIED_PIPELINE = "Level 1 Parallax Pipeline (Simplified):\n\nINITIALIZATION (Once at load):\n1. Load 5 layer files from Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/\n2. Skip day/night selection (Phase 2 SKIPPED)\n3. All 5 layers use consistent \"industrial daylight\" appearance\n4. Proceed to Phase 3 immediately\n\nGAMEPLAY LOOP (Every frame, 60 FPS):\nPhase 3: Calculate offsets using factors [0.0, 0.1, 0.25, 0.35, 0.55]\nPhase 4: Render 5 layers back-to-front with calculated offsets\nPhase 5: Skip overlay blending (no day/night)\nPhase 6: Update offsets based on new player position\nPhase 7: Wrap layers at edges (modulo width)\nPhase 8: Composite final background and display\n\nNO TRANSITIONS:\n- No day/night blending\n- No overlay alpha changes\n- Consistent appearance throughout gameplay\n- Simplified per-frame computation (no blending math)\n\nPERFORMANCE: ~13-14ms per frame (slightly faster than Level 2 due to no blending)\n";
}
