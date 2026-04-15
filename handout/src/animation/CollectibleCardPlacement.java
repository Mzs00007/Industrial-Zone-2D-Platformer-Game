/*
 * Decompiled with CFR 0.152.
 */
package animation;
import game2D.*;
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
