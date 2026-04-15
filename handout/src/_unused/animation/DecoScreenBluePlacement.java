/*
 * Decompiled with CFR 0.152.
 */
package animation;
import game2D.*;
public class DecoScreenBluePlacement {
    public static final String OBJECT_TYPE = "deco_screen_blue";
    public static final String OBJECT_FILE = "Anim_Deco_Screen1_4Frames1Row_BlueMonitorFlicker_WallPanelTechDeco_Loop150ms.png";
    public static final String ANIMATION_DESCRIPTION = "Blue monitor flicker effect - plays 4 frames at 150ms with active glow";
    public static final String[] PRIMARY_MOUNT_TILES = new String[]{"PanelStructures.PANEL_FILE_61", "PanelStructures.PANEL_FILE_64", "PanelStructures.PANEL_FILE_73"};
    public static final String[] SECONDARY_MOUNT_TILES = new String[]{"PanelStructures.PANEL_FILE_56", "PanelStructures.PANEL_FILE_57", "DoorGateElements.DOOR_FILE_0", "DoorGateElements.DOOR_FILE_2"};
    public static final String PAIR_PATTERN = "Two screens mounted vertically on same panel (PANEL_FILE_64 top + PANEL_FILE_64 below). Spacing: Centered, 2 tiles apart. Purpose: Control room duality. Visual impact: Symmetrical.";
    public static final String TRIPLE_PATTERN = "Three screens in triangle: Primary at center (PANEL_FILE_61 or 64), secondaries on left/right (PANEL_FILE_56/57). Spacing: 2-3 tiles. Purpose: Surveillance hub. Visual impact: High density, command center feel.";
    public static final String ISOLATED_RULE = "Single screens should not be used. Minimum 2 screens per installation.";
    public static final String[] FORBIDDEN_TILES = new String[]{"HorizontalStripeBrickPanels.ANY", "EdgeBorders.ANY", "PanelStructures.PANEL_FILE_52", "PanelStructures.PANEL_FILE_58", "BrickSmallUnits.ANY", "Player_Spawn_Zone_5tile_radius"};
    public static final String ZONE_CONTEXT = "Blue screens ONLY in control rooms, tech facilities, and secured zones. They indicate active monitoring and organizational control. Contrast with red screens by placing blue in 'safe' tech areas.";
    public static final String NARRATIVE_CONTEXT = "Blue screens represent: Corporate surveillance, automated systems, secure access. Player finding blue screens = finding control center = strategic location. Often placed above collectibles = guarded rewards.";
    public static final String GAME_HINT_CONTEXT = "Blue screens are visual feedback: 'You are in a controlled/secured area now.'";
    public static final String PLACEMENT_STRATEGY = "Mount on walls for tech atmosphere. Use in pairs or triplet clusters.";
    public static final float SPAWN_FREQUENCY = 0.25f;
    public static final String VISUAL_NOTE = "Flickers creating sense of active surveillance/control";
    public static final String TECHNICAL_NOTES = "Animation: 4 frames at 150ms = 600ms total cycle (slower than collectibles - emphasizes stasis). Glow: Blue glow expands/contracts with frame - draws attention subtly. Audio: Low soft hum (60Hz sine wave \u00d7 2 harmonics) - background ambient. Offset: Mount at +20px vertical to simulate mounted-on-panel placement. Rotation: Slight 5-degree tilt variation between screens in cluster (\u00d7-2\u00b0, \u00d70\u00b0, \u00d7+2\u00b0) = more organic feel. No interaction: Purely visual deco - player cannot activate/disable.";
}
