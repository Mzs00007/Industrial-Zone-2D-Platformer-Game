/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.AnimatedObjectPlacementRules.DecoScreenRedPlacement {
    public static final String OBJECT_TYPE = "deco_screen_red";
    public static final String OBJECT_FILE = "Anim_Deco_Screen2_4Frames1Row_RedBlueMonitorFlicker_WallPanelAltDeco_Loop150ms.png";
    public static final String ANIMATION_DESCRIPTION = "Red alarm monitor flicker - plays 4 frames at 150ms with intense red glow";
    public static final String[] HAZARD_ZONE_TILES = new String[]{"PanelStructures.PANEL_FILE_52", "PanelStructures.PANEL_FILE_58", "Above_PANEL_FILE_52_or_58"};
    public static final String[] HAZARD_ADJACENT_TILES = new String[]{"PanelStructures.PANEL_FILE_51", "PanelStructures.PANEL_FILE_59", "PanelStructures.PANEL_FILE_62", "EdgeBorders.EDGE_FILE_79", "EdgeBorders.EDGE_FILE_80"};
    public static final String CLUSTER_2_SCREENS = "Two red screens: Both above slope entry. Spacing: 1 tile apart horizontal. Creates twin-alarm effect. Placement: Left screen (PANEL_FILE_52 area), right screen (PANEL_FILE_58 area). Purpose: Double alert = serious danger.";
    public static final String CLUSTER_4_SCREENS = "Four red screens: 2\u00d72 grid above hazard zone. Spacing: Checker pattern, 1-2 tiles. Creates intense alarm field. Purpose: EXTREME DANGER - progression gate. Player sees this and knows: 'I will die if unprepared.'";
    public static final String CLUSTER_RULE = "Minimum 2 screens per hazard zone. Never use isolated red screens. Exceptions: Very small hazard areas (1\u00d71 single slope tile) = 1 red screen acceptable.";
    public static final String[] FORBIDDEN_TILES = new String[]{"HorizontalStripeBrickPanels.ANY", "DoorGateElements.DOOR_FILE_0", "DoorGateElements.DOOR_FILE_2", "Player_Start_Zone", "Collectible_Money_Zones", "Safe_Platform_Clusters"};
    public static final String VISUAL_HIERARCHY = "Red screens are HIGHER PRIORITY than blue. If red is visible, blue is ignored. Red flicker is FASTER and BRIGHTER than blue. Red glow expands more aggressively. Audio: Red screens emit HIGH-PITCH warning (1000Hz + 1500Hz harmonics) \u00d7 urgent tempo.";
    public static final String SPATIAL_RULES = "Rule 1: Red screen NEVER below player. Always at player eye-level or above. Rule 2: Red screens visible from safe zone (5+ tile sightline). Rule 3: Red zones create visual 'no-go' atmosphere. Rule 4: Path design: Blue areas \u2192 Yellow transition \u2192 Red danger zones (aesthetic gradient).";
    public static final String PROGRESSION_CONTEXT = "Level 1: No red screens (safe intro). Level 2: Introduce 1-2 red zones (2 clusters of 2 screens each). Challenge: Heavy red screen usage (3+ clusters of 4 screens = maximum alert state).";
    public static final String PLACEMENT_STRATEGY = "Cluster 2-4 red screens in dangerous zones to warn player.";
    public static final float SPAWN_FREQUENCY = 0.4f;
    public static final String VISUAL_NOTE = "Red flicker creates alarm/alert atmosphere";
    public static final String TECHNICAL_NOTES = "Animation: 4 frames at 150ms = 600ms cycle, BUT frame 1-2-1-2 pattern (emphasis on red moments). Glow: Red glow pulsates more aggressively than blue - expands \u00b15px per frame. Audio: 1000Hz + 1500Hz sine wave \u00d7 3 harmonics, 200ms ON/100ms OFF pulse. Offset: Mount at +15px (slightly lower than blue to feel 'threatening'). Screen Flicker: Brightness variance 80%-100% (more extreme variation = more alarming). Rotation: Slight 10-degree tilt variance (\u00d7-5\u00b0, \u00d70\u00b0, \u00d7+5\u00b0) = unstable feeling. No interaction: Purely visual deco - cannot be disabled by player.";
}
