/*
 * Decompiled with CFR 0.152.
 */
package animation;
import game2D.*;
public class CollectibleMoneyPlacement {
    public static final String OBJECT_TYPE = "collectible_money";
    public static final String OBJECT_FILE = "Anim_Collectible_Money_6Frames1Row_GreenBanknotesSpinFlip_CurrencyPickup_Loop80ms.png";
    public static final String ANIMATION_DESCRIPTION = "Spinning banknote with flip rotation - plays 6 frames at 80ms intervals";
    public static final String[] TIER_1_PRIMARY_TILES = new String[]{"HorizontalStripeBrickPanels_Any_Color", "PanelStructures.PANEL_FILE_3", "PanelStructures.PANEL_FILE_6", "PanelStructures.PANEL_FILE_12", "PanelStructures.PANEL_FILE_15"};
    public static final String[] TIER_2_SECONDARY_TILES = new String[]{"PanelStructures.PANEL_FILE_9", "PanelStructures.PANEL_FILE_21", "PanelStructures.PANEL_FILE_27", "DoorGateElements.DOOR_FILE_0", "DoorGateElements.DOOR_FILE_2"};
    public static final String[] TIER_3_INDUSTRIAL_TILES = new String[]{"PanelStructures.PANEL_FILE_56", "PanelStructures.PANEL_FILE_61", "PanelStructures.PANEL_FILE_64", "PanelStructures.PANEL_FILE_70"};
    public static final String[] FORBIDDEN_TILES = new String[]{"PanelStructures.PANEL_FILE_52", "PanelStructures.PANEL_FILE_58", "EdgeBorders.EDGE_FILE_55", "EdgeBorders.EDGE_FILE_77", "EdgeBorders.EDGE_FILE_78", "EdgeBorders.EDGE_FILE_79", "EdgeBorders.EDGE_FILE_80", "BrickSmallUnits.ANY"};
    public static final String PLACEMENT_CONTEXT = "Money appears as reward for navigation. Spacing of 3-5 tiles prevents clustering. Vary height: 40% same level (easy), 35% 1 level up (moderate), 25% 2+ levels (challenge). Always place within player's line of sight when approaching tile. Clustering 2-3 money on elevated platforms creates 'treasure spots'.";
    public static final String LEVEL_DISTRIBUTION = "Level 1: 35% placement - Easy discovery. Level 2: 40% placement - More challenging. Challenge Areas: 50% placement - Reward for skill.";
    public static final String PLACEMENT_STRATEGY = "Place on prominent SAFE platforms. Space 3-5 tiles apart to encourage exploration.";
    public static final float SPAWN_FREQUENCY = 0.35f;
    public static final String TECHNICAL_NOTES = "Collision detection: 32\u00d732 origin point at tile center. Animation: 6 frames at 80ms = 480ms total loop. Interaction: Auto-pickup within 50px radius. Audio: Coin-chime sound at +2 octaves pitched randomly \u00b150Hz per pickup.";
}
