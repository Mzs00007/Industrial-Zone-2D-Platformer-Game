/*
 * Decompiled with CFR 0.152.
 */
package animation;
import game2D.*;
public class AnimatedObjectPlacementRules {
    public static final String REGISTRY_TYPE = "object_placement";
public class DecoScreenRedPlacement {
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
public class CollectibleCardPlacement {
        public static final String OBJECT_TYPE = "collectible_card";
        public static final String OBJECT_FILE = "Anim_Collectible_Card_6Frames1Row_WhiteBlueSpinningFloat_PickupItem_Loop80ms.png";
        public static final String ANIMATION_DESCRIPTION = "Rotating holographic card - plays 6 frames at 80ms with active glow";
        public static final String[] TIER_1_ALCOVE_TILES = new String[]{"PanelStructures.PANEL_FILE_9", "PanelStructures.PANEL_FILE_18", "PanelStructures.PANEL_FILE_21", "PanelStructures.PANEL_FILE_27"};
        public static final String[] TIER_2_FLOATING_ANCHORS = new String[]{"HorizontalStripeBrickPanels_Elevated", "PanelStructures.PANEL_FILE_15", "PanelStructures.PANEL_FILE_24"};
        public static final String SHIELD_CARD_CONTEXT = "Shield Card placement: TIER_1_ALCOVE. Reason: Protection is vital, hide to reward exploration.";
        public static final String DAMAGE_CARD_CONTEXT = "DoubleDamage Card placement: TIER_1_ALCOVE. Reason: Power spike should feel earned.";
        public static final String SPEED_CARD_CONTEXT = "SpeedBoost Card placement: TIER_2_FLOATING. Reason: Speed cards feel 'light', place in air.";
        public static final String HEALTH_CARD_CONTEXT = "HealthRestore Card placement: TIER_2_FLOATING. Reason: Recovery items less urgent, can be floating.";
        public static final String COMBO_CARD_CONTEXT = "ComboMultiplier Card placement: TIER_1_ALCOVE. Reason: Skill-based card, hide for skilled players.";
        public static final String[] FORBIDDEN_TILES = new String[]{"PanelStructures.PANEL_FILE_52", "PanelStructures.PANEL_FILE_58", "EdgeBorders.ANY", "HorizontalStripeBrickPanels_BaseLevel", "BrickSmallUnits.ANY"};
        public static final String PROXIMITY_CONTEXT = "Card must be 5+ tiles away from: Doors, Money spawns, Transporter zones. Card should be 2-3 tiles away from: Hazard areas (to entice risk). Card visible if player is within 10 tiles (sightline matters).";
        public static final String LEVEL_1_CONTEXT = "Level 1: 1-2 cards only. Placement: Safe hidden alcoves. Purpose: Teach discovery mechanic.";
        public static final String LEVEL_2_CONTEXT = "Level 2: 3-4 cards. Placement: Mix of alcoves and floating. Purpose: Reward skill exploration.";
        public static final String CHALLENGE_CONTEXT = "Challenge Rooms: 1 card per room. Placement: Highest difficulty platform. Purpose: Gate progression on mastery.";
        public static final String PLACEMENT_STRATEGY = "Hide in difficult-to-reach areas. Use for rewards and progression gates.";
        public static final float SPAWN_FREQUENCY = 0.1f;
        public static final String DIFFICULTY_CONTEXT = "Typically only on Level 2 (harder) or challenge areas";
        public static final String TECHNICAL_NOTES = "Interaction radius: 100px (larger than money - forces player to commit). Animation: 6 frames at 80ms = 480ms loop with active glow effect. Audio: Ascending chime melody (Do-Re-Mi-Fa-Sol 5 notes) with 3x reverb. Visual: Glow expands/contracts with frame - signals importance. Behavior: Slight vertical bobbing (\u00b115px, 2 second cycle).";
    }
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
}
