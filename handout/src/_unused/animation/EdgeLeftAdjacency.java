/*
 * Decompiled with CFR 0.152.
 */
package animation;
public final class EdgeLeftAdjacency {
    public static final String TILE_TYPE = "EDGE-LEFT (Wall)";
    public static final String TOP_NEIGHBOR_VALID = "EDGE-LEFT or CORNER-TL";
    public static final String BOTTOM_NEIGHBOR_VALID = "EDGE-LEFT or CORNER-BL";
    public static final String RIGHT_NEIGHBOR_VALID = "INTERIOR";
    public static final String PHYSICS = "Vertical wall blocking left side";
    public static final String COLLISION_FACES = "Left face is solid (player blocks here)";
    public static final boolean INTERIOR_VISIBLE = false;
}
