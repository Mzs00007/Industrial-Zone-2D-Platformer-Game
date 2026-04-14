/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static final class AnimationAndSpriteLoader.GUITilesetSystem.TileAdjacencyRules.EdgeRightAdjacency {
    public static final String TILE_TYPE = "EDGE-RIGHT (Wall)";
    public static final String TOP_NEIGHBOR_VALID = "EDGE-RIGHT or CORNER-TR";
    public static final String BOTTOM_NEIGHBOR_VALID = "EDGE-RIGHT or CORNER-BR";
    public static final String LEFT_NEIGHBOR_VALID = "INTERIOR";
    public static final String PHYSICS = "Vertical wall blocking right side";
    public static final String COLLISION_FACES = "Right face is solid";
}
