package tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * TileMap — Loads and renders tile-based level maps.
 *
 * Map format (maps/level_N/map.txt):
 *   Line 1:  cols rows tileW tileH
 *   Lines:   #alias=filename.png   (tile alias definitions)
 *   Lines:   // comment             (ignored)
 *   #map     (marks start of grid data)
 *   row0 data (cols characters)
 *   row1 data
 *   ...
 *   rowN data
 *
 * '.' = empty/air, any other char = tile alias
 */
public class TileMap {

    private int cols, rows, tileW, tileH;
    private char[][] grid;                          // grid[row][col]
    private Map<Character, BufferedImage> tileImages = new HashMap<>();
    private Map<Character, String> aliasToFile       = new HashMap<>();
    private Map<Character, TileType> tileTypes       = new HashMap<>();
    private int worldOffsetY;  // pixel Y where row 0 starts in world space

    public enum TileType {
        SOLID,       // walkable floor, structural fill
        PLATFORM,    // one-way platform (jump through from below)
        HAZARD,      // contact damage
        HAZARD_KILL, // instant kill
        DECO,        // decorative, no collision
        EMPTY        // air
    }

    // =========================================================================
    //  Constructor — parse map file & load tile images
    // =========================================================================
    public TileMap(String mapFilePath, String[] searchDirs, int worldOffsetY) {
        this.worldOffsetY = worldOffsetY;
        parseMapFile(mapFilePath);
        loadTileImages(searchDirs);
        classifyTiles();
        System.out.printf("[TileMap] Loaded %dx%d map (%d aliases, %d images, offsetY=%d)%n",
            cols, rows, aliasToFile.size(), tileImages.size(), worldOffsetY);
    }

    // =========================================================================
    //  Parsing
    // =========================================================================
    private void parseMapFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean headerRead = false;
            boolean inGrid = false;
            int gridRow = 0;

            while ((line = br.readLine()) != null) {
                String trimmed = line.trim();

                // Skip empty and comment lines (before grid)
                if (!inGrid && (trimmed.isEmpty() || trimmed.startsWith("//"))) continue;

                // Header: cols rows tileW tileH
                if (!headerRead && !trimmed.startsWith("#")) {
                    String[] parts = trimmed.split("\\s+");
                    if (parts.length >= 4) {
                        cols  = Integer.parseInt(parts[0]);
                        rows  = Integer.parseInt(parts[1]);
                        tileW = Integer.parseInt(parts[2]);
                        tileH = Integer.parseInt(parts[3]);
                        grid  = new char[rows][cols];
                        headerRead = true;
                    }
                    continue;
                }

                // #map marker
                if (trimmed.equals("#map")) {
                    inGrid = true;
                    continue;
                }

                // Alias definition: #c=filename.png
                if (!inGrid && trimmed.startsWith("#") && trimmed.length() >= 4
                        && trimmed.charAt(2) == '=') {
                    char alias = trimmed.charAt(1);
                    String filename = trimmed.substring(3).trim();
                    aliasToFile.put(alias, filename);
                    continue;
                }

                // Grid row data
                if (inGrid && gridRow < rows) {
                    for (int c = 0; c < Math.min(line.length(), cols); c++) {
                        grid[gridRow][c] = line.charAt(c);
                    }
                    gridRow++;
                }
            }

            if (grid == null) {
                System.err.println("[TileMap] Failed to parse map: " + path);
                cols = rows = 0;
                grid = new char[0][0];
            }
        } catch (Exception e) {
            System.err.println("[TileMap] Error reading map: " + path + " — " + e.getMessage());
            cols = rows = 0;
            grid = new char[0][0];
        }
    }

    // =========================================================================
    //  Image loading — search multiple directories for each alias filename
    // =========================================================================
    private void loadTileImages(String[] searchDirs) {
        int loaded = 0, missed = 0;

        // Build a lookup of all available files across search dirs (recursive)
        Map<String, File> fileIndex = new HashMap<>();
        for (String dir : searchDirs) {
            File d = new File(dir);
            if (!d.exists() || !d.isDirectory()) continue;
            indexPngFiles(d, fileIndex);
        }

        for (Map.Entry<Character, String> entry : aliasToFile.entrySet()) {
            char alias = entry.getKey();
            String filename = entry.getValue();
            File file = fileIndex.get(filename);
            // Try with .png extension if missing
            if (file == null && !filename.toLowerCase().endsWith(".png")) {
                file = fileIndex.get(filename + ".png");
            }
            if (file == null) {
                // Try partial match strategies
                String fnLower = filename.toLowerCase().replace(".png", "");
                // Extract numeric prefix (e.g. "31" from "31_Hazard_ContactDamage_Floor.png")
                String numPrefix = "";
                int us = filename.indexOf('_');
                if (us > 0) {
                    String candidate = filename.substring(0, us);
                    if (candidate.chars().allMatch(Character::isDigit)) numPrefix = candidate;
                }

                for (Map.Entry<String, File> fe : fileIndex.entrySet()) {
                    String key = fe.getKey();
                    String keyLower = key.toLowerCase();
                    // Exact suffix/prefix match
                    if (key.endsWith(filename) || filename.endsWith(key)) {
                        file = fe.getValue();
                        break;
                    }
                    // Substring containment
                    if (keyLower.contains(fnLower) || fnLower.contains(keyLower.replace(".png", ""))) {
                        file = fe.getValue();
                        break;
                    }
                    // Numeric prefix match (e.g. "31_" matches "31_Hazard_FullStripe...png")
                    if (!numPrefix.isEmpty() && keyLower.startsWith(numPrefix + "_")) {
                        file = fe.getValue();
                        break;
                    }
                    // Strip numeric prefix from key and check if it starts with alias name
                    String keyStripped = keyLower.replaceFirst("^\\d+_", "").replace(".png", "");
                    if (keyStripped.startsWith(fnLower) || fnLower.startsWith(keyStripped)) {
                        file = fe.getValue();
                        break;
                    }
                    // All words check: every word in the alias appears in the key
                    String[] words = fnLower.split("_");
                    if (words.length >= 2) {
                        boolean allMatch = true;
                        for (String w : words) {
                            if (!w.isEmpty() && !keyLower.contains(w)) {
                                allMatch = false;
                                break;
                            }
                        }
                        if (allMatch) {
                            file = fe.getValue();
                            break;
                        }
                    }
                }
            }
            if (file != null) {
                try {
                    BufferedImage img = ImageIO.read(file);
                    if (img != null) {
                        tileImages.put(alias, img);
                        loaded++;
                    }
                } catch (Exception e) {
                    System.err.println("[TileMap] Load fail: " + file.getPath());
                }
            } else {
                missed++;
                if (missed <= 10) // limit spam
                    System.err.println("[TileMap] Not found: '" + alias + "' = " + filename);
            }
        }
        System.out.printf("[TileMap] Tile images: %d loaded, %d missing%n", loaded, missed);
    }

    /** Recursively index all PNG files under a directory. */
    private void indexPngFiles(File dir, Map<String, File> index) {
        File[] children = dir.listFiles();
        if (children == null) return;
        for (File f : children) {
            if (f.isDirectory()) {
                indexPngFiles(f, index);
            } else if (f.getName().toLowerCase().endsWith(".png")) {
                index.put(f.getName(), f);
            }
        }
    }

    // =========================================================================
    //  Tile classification — determines collision behavior
    // =========================================================================
    private void classifyTiles() {
        for (Map.Entry<Character, String> entry : aliasToFile.entrySet()) {
            char alias = entry.getKey();
            String fn = entry.getValue().toLowerCase();

            if (fn.contains("hazard") && (fn.contains("instant") || fn.contains("energy")
                    || fn.contains("electric") || fn.contains("barrier"))) {
                tileTypes.put(alias, TileType.HAZARD_KILL);
            } else if (fn.contains("hazard")) {
                tileTypes.put(alias, TileType.HAZARD);
            } else if (fn.contains("deco") || fn.contains("indicator") || fn.contains("gradient")
                    || fn.contains("prop_") || fn.contains("marker") || fn.contains("portal")) {
                tileTypes.put(alias, TileType.DECO);
            } else if (fn.contains("edge") || fn.contains("shelf") || fn.contains("ledge")
                    || fn.contains("bracket")) {
                tileTypes.put(alias, TileType.PLATFORM);
            } else if (fn.contains("platform") || fn.contains("solid") || fn.contains("structure")
                    || fn.contains("panel") || fn.contains("wall") || fn.contains("corner")
                    || fn.contains("block") || fn.contains("brick") || fn.contains("floor")
                    || fn.contains("fill")) {
                tileTypes.put(alias, TileType.SOLID);
            } else if (fn.contains("slope") || fn.contains("diagonal") || fn.contains("ramp")) {
                tileTypes.put(alias, TileType.SOLID);
            } else if (fn.contains("door") || fn.contains("gate")) {
                tileTypes.put(alias, TileType.SOLID);
            } else {
                tileTypes.put(alias, TileType.DECO);
            }
        }
    }

    // =========================================================================
    //  Rendering — draws visible tiles with camera offset
    // =========================================================================
    public void render(Graphics2D g, float cameraX, float cameraY, int screenW, int screenH) {
        if (grid == null || cols == 0) return;

        // Calculate visible tile range (with 1 tile margin)
        int startCol = Math.max(0, (int)(cameraX / tileW) - 1);
        int endCol   = Math.min(cols, (int)((cameraX + screenW) / tileW) + 2);
        int startRow = Math.max(0, (int)((cameraY - worldOffsetY) / tileH) - 1);
        int endRow   = Math.min(rows, (int)((cameraY - worldOffsetY + screenH) / tileH) + 2);

        for (int r = startRow; r < endRow; r++) {
            for (int c = startCol; c < endCol; c++) {
                char ch = grid[r][c];
                if (ch == '.') continue;  // empty

                BufferedImage img = tileImages.get(ch);
                if (img == null) continue;

                int worldX = c * tileW;
                int worldY = worldOffsetY + r * tileH;
                int screenX = (int)(worldX - cameraX);
                int screenY = (int)(worldY - cameraY);

                // Skip if off screen
                if (screenX + tileW < 0 || screenX > screenW) continue;
                if (screenY + tileH < 0 || screenY > screenH) continue;

                g.drawImage(img, screenX, screenY, tileW, tileH, null);
            }
        }
    }

    // =========================================================================
    //  Extended rendering — tile to fill ground below tile map
    // =========================================================================
    public void renderGroundFill(Graphics2D g, float cameraX, float cameraY,
                                  int screenW, int screenH, int fillToY) {
        if (grid == null || rows == 0 || cols == 0) return;

        // Fill area below the tile map with bottom-row tiles
        int mapBottomY = worldOffsetY + rows * tileH;
        if (mapBottomY >= fillToY) return;

        int startCol = Math.max(0, (int)(cameraX / tileW) - 1);
        int endCol   = Math.min(cols, (int)((cameraX + screenW) / tileW) + 2);

        // Use bottom row tile as fill pattern
        for (int c = startCol; c < endCol; c++) {
            char ch = grid[rows - 1][c];
            if (ch == '.') ch = grid[Math.max(0, rows - 2)][c]; // try second-to-last
            if (ch == '.') continue;
            BufferedImage img = tileImages.get(ch);
            if (img == null) continue;

            int worldX = c * tileW;
            int screenX = (int)(worldX - cameraX);
            if (screenX + tileW < 0 || screenX > screenW) continue;

            for (int wy = mapBottomY; wy < fillToY; wy += tileH) {
                int screenY = (int)(wy - cameraY);
                if (screenY > screenH) break;
                if (screenY + tileH < 0) continue;
                g.drawImage(img, screenX, screenY, tileW, tileH, null);
            }
        }
    }

    // =========================================================================
    //  Collision queries
    // =========================================================================

    /** Check if a world-space pixel position hits a solid tile. */
    public boolean isSolid(float worldX, float worldY) {
        int col = (int)(worldX / tileW);
        int row = (int)((worldY - worldOffsetY) / tileH);
        if (col < 0 || col >= cols || row < 0 || row >= rows) return false;
        char ch = grid[row][col];
        if (ch == '.') return false;
        TileType t = tileTypes.getOrDefault(ch, TileType.EMPTY);
        return t == TileType.SOLID || t == TileType.PLATFORM;
    }

    /** Check if a world-space pixel position hits a hazard tile. */
    public boolean isHazard(float worldX, float worldY) {
        int col = (int)(worldX / tileW);
        int row = (int)((worldY - worldOffsetY) / tileH);
        if (col < 0 || col >= cols || row < 0 || row >= rows) return false;
        char ch = grid[row][col];
        if (ch == '.') return false;
        TileType t = tileTypes.getOrDefault(ch, TileType.EMPTY);
        return t == TileType.HAZARD || t == TileType.HAZARD_KILL;
    }

    /** Check if a hazard is instant-kill. */
    public boolean isInstantKill(float worldX, float worldY) {
        int col = (int)(worldX / tileW);
        int row = (int)((worldY - worldOffsetY) / tileH);
        if (col < 0 || col >= cols || row < 0 || row >= rows) return false;
        char ch = grid[row][col];
        if (ch == '.') return false;
        return tileTypes.getOrDefault(ch, TileType.EMPTY) == TileType.HAZARD_KILL;
    }

    /**
     * Get the Y position of the ground surface at a given X.
     * Scans from top to bottom to find the first solid tile.
     */
    public float getGroundY(float worldX) {
        int col = (int)(worldX / tileW);
        if (col < 0 || col >= cols) return Float.MAX_VALUE;
        for (int r = 0; r < rows; r++) {
            char ch = grid[r][col];
            if (ch == '.') continue;
            TileType t = tileTypes.getOrDefault(ch, TileType.EMPTY);
            if (t == TileType.SOLID || t == TileType.PLATFORM) {
                return worldOffsetY + r * tileH;
            }
        }
        return Float.MAX_VALUE;
    }

    // =========================================================================
    //  Getters
    // =========================================================================
    public int getCols()        { return cols; }
    public int getRows()        { return rows; }
    public int getTileW()       { return tileW; }
    public int getTileH()       { return tileH; }
    public int getWorldWidth()  { return cols * tileW; }
    public int getWorldHeight() { return rows * tileH; }
    public int getWorldOffsetY() { return worldOffsetY; }
}
