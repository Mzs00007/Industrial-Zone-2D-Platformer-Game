/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class AdaptiveTileSelection {
    public static final String ALGORITHM = "Neighbor-based tile variant selection";
    public static final String BENEFIT = "Automatically place correct tile piece without manual specification";
public final class ExampleLevel1Tilemap {
        public static final String LEVEL = "Industrial Zone Level 1";
        public static final String MAP_COORDINATE = "Position (10, 8) in level tilemap";
        public static final String NEIGHBORS = "Top: FLOOR_SOLID (Edge-Top)\nBottom: EMPTY (Interior)\nLeft: CORNER (Edge-Left)\nRight: FLOOR_SOLID (Edge-Top)";
        public static final String AUTO_SELECTION = "= CORNER-TL (matches top+left adjacency)";
    }
}
