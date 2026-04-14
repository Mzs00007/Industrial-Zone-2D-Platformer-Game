/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static final class AnimationAndSpriteLoader.GUITilesetSystem.TileAdjacencyRules.InteriorAdjacency {
    public static final String TILE_TYPE = "INTERIOR (Full Solid)";
    public static final String[] ALL_NEIGHBORS_VALID = new String[]{"Any INTERIOR", "Any EDGE", "Any CORNER"};
    public static final String PHYSICS = "Full solid block (no visibility)";
    public static final String COLLISION_OPTIMIZATION = "Often NOT checked by physics (exterior edges handle it)";
    public static final boolean INTERIOR_VISIBLE = false;
}
