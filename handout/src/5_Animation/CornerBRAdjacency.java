/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static final class AnimationAndSpriteLoader.GUITilesetSystem.TileAdjacencyRules.CornerBRAdjacency {
    public static final String TILE_TYPE = "CORNER-BOTTOM-RIGHT";
    public static final String TOP_NEIGHBOR_VALID = "EDGE-RIGHT or CORNER-TR";
    public static final String LEFT_NEIGHBOR_VALID = "EDGE-BOTTOM or CORNER-BL";
    public static final String PHYSICS = "Blocks movement from bottom and right sides";
    public static final String COLLISION_FACES = "Bottom face and Right face are solid";
}
