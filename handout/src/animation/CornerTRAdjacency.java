/*
 * Decompiled with CFR 0.152.
 */
package animation;
public final class CornerTRAdjacency {
    public static final String TILE_TYPE = "CORNER-TOP-RIGHT";
    public static final String LEFT_NEIGHBOR_VALID = "EDGE-TOP or CORNER-TL";
    public static final String BOTTOM_NEIGHBOR_VALID = "EDGE-RIGHT or CORNER-BR";
    public static final String PHYSICS = "Blocks movement from top and right sides";
    public static final String COLLISION_FACES = "Top face and Right face are solid";
    public static final boolean INTERIOR_VISIBLE = false;
}
