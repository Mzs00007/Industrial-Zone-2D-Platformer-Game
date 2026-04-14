/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.InteractiveObjectAssignmentMatrix {
    public static final String REGISTRY_TYPE = "object_assignment_matrix";
    public static final String ASSIGNMENT_RULE_1 = "MONEY: Only on SAFE tilesets (any horizontal brick, panel middles). 35% spawn rate.";
    public static final String ASSIGNMENT_RULE_2 = "CARD: Rare (10%) on elevated SAFE tilesets only. Acts as power-up reward.";
    public static final String ASSIGNMENT_RULE_3 = "DECO_BLUE: On INDUSTRIAL tilesets (panels, doors). 25% spawn rate for tech aesthetic.";
    public static final String ASSIGNMENT_RULE_4 = "DECO_RED: On HAZARD/PANELS near hazards. 40% spawn rate above slopes/ramps.";
    public static final String ASSIGNMENT_RULE_5 = "Never place collectibles on slopes, small units, or edge borders.";
    public static final String ASSIGNMENT_RULE_6 = "Never mix safe and hazard aesthetics - red screens don't go in peaceful zones.";
    public static final String VALIDATION_METHOD = "Run level validator: Check each placed object against its VALID_PARENT_TILESETS list.";
}
