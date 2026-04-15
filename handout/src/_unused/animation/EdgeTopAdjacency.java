/*
 * Decompiled with CFR 0.152.
 */
package animation;
public final class EdgeTopAdjacency {
    public static final String TILE_TYPE = "EDGE-TOP (Primary Walkable)";
    public static final String LEFT_NEIGHBOR_VALID = "EDGE-TOP or CORNER-TL";
    public static final String RIGHT_NEIGHBOR_VALID = "EDGE-TOP or CORNER-TR";
    public static final String BOTTOM_NEIGHBOR_VALID = "INTERIOR or EDGE-BOTTOM";
    public static final String PHYSICS = "Primary walkable surface";
    public static final String COLLISION_FACES = "Top face is solid (player stands here)";
    public static final boolean INTERIOR_VISIBLE = false;
}
