package levels;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class LevelMapLoader {
    
    private int levelNumber;
    private int mapWidth;
    private int mapHeight;
    private int tileSize = 32;
    private int[][] tileGrid;
    private Map<Integer, String> tileFilePaths = new HashMap<>();
    private Map<Integer, BufferedImage> loadedTiles = new HashMap<>();
    private String baseDir;
    
    public LevelMapLoader(int levelNumber) {
        this.levelNumber = levelNumber;
        this.baseDir = "handout/maps/level_" + levelNumber;
        loadMap();
    }
    
    /**
     * Load level map from file
     */
    private void loadMap() {
        String mapFile = baseDir + "/map.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {
            String line;
            String section = "";
            
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                
                if (line.equals("[HEADER]")) {
                    section = "HEADER";
                    continue;
                } else if (line.equals("[TILE_DEFINITIONS]")) {
                    section = "TILE_DEFINITIONS";
                    continue;
                } else if (line.equals("[MAP]")) {
                    section = "MAP";
                    continue;
                } else if (line.startsWith("[")) {
                    section = line;
                    continue;
                }
                
                if (section.equals("HEADER")) {
                    parseHeader(line);
                } else if (section.equals("TILE_DEFINITIONS")) {
                    parseTileDefinition(line);
                } else if (section.equals("MAP")) {
                    parseMapLine(line);
                }
            }
            
            System.out.println("[LevelMapLoader] ✓ Loaded level " + levelNumber + " (" + mapWidth + "x" + mapHeight + " tiles)");
            loadTileImages();
            
        } catch (IOException e) {
            System.err.println("[LevelMapLoader] ERROR loading map file: " + mapFile);
            System.err.println("  " + e.getMessage());
            tileGrid = new int[1][1];
            mapWidth = 1;
            mapHeight = 1;
        }
    }
    
    /**
     * Parse header: "22 24 32" = width height tileSize
     */
    private void parseHeader(String line) {
        try {
            String[] parts = line.split("\\s+");
            if (parts.length >= 2) {
                mapWidth = Integer.parseInt(parts[0]);
                mapHeight = Integer.parseInt(parts[1]);
                if (parts.length >= 3) {
                    tileSize = Integer.parseInt(parts[2]);
                }
                tileGrid = new int[mapHeight][mapWidth];
                System.out.println("[LevelMapLoader] Header: " + mapWidth + "x" + mapHeight + " tiles, size=" + tileSize);
            }
        } catch (NumberFormatException e) {
            System.err.println("[LevelMapLoader] ERROR parsing header: " + line);
        }
    }
    
    /**
     * Parse tile definition: "0=Resources/path/to/tile.png"
     */
    private void parseTileDefinition(String line) {
        try {
            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                int tileId = Integer.parseInt(parts[0].trim());
                String tilePath = parts[1].trim();
                tileFilePaths.put(tileId, tilePath);
            }
        } catch (NumberFormatException e) {
            System.err.println("[LevelMapLoader] ERROR parsing tile definition: " + line);
        }
    }
    
    /**
     * Parse map line: "0 1 2 3 4 ..."
     */
    private void parseMapLine(String line) {
        try {
            if (tileGrid == null) return;
            
            String[] parts = line.split("\\s+");
            int rowIndex = findNextEmptyMapRow();
            
            if (rowIndex < mapHeight) {
                for (int col = 0; col < Math.min(parts.length, mapWidth); col++) {
                    tileGrid[rowIndex][col] = Integer.parseInt(parts[col].trim());
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("[LevelMapLoader] ERROR parsing map line: " + line);
        }
    }
    
    /**
     * Find next empty row in tile grid
     */
    private int findNextEmptyMapRow() {
        if (tileGrid == null) return 0;
        for (int row = 0; row < mapHeight; row++) {
            if (tileGrid[row][0] == 0 && tileGrid[row][mapWidth-1] == 0) {
                return row;
            }
        }
        return mapHeight;
    }
    
    /**
     * Load all tile PNG images into memory
     */
    private void loadTileImages() {
        int loadedCount = 0;
        for (Integer tileId : tileFilePaths.keySet()) {
            String path = tileFilePaths.get(tileId);
            try {
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    BufferedImage img = ImageIO.read(imgFile);
                    if (img != null) {
                        loadedTiles.put(tileId, img);
                        loadedCount++;
                    } else {
                        System.err.println("[LevelMapLoader] ERROR: Could not read image: " + path);
                    }
                } else {
                    System.err.println("[LevelMapLoader] ERROR: File not found: " + path);
                }
            } catch (IOException e) {
                System.err.println("[LevelMapLoader] ERROR loading tile " + tileId + ": " + path);
            }
        }
        System.out.println("[LevelMapLoader] ✓ Loaded " + loadedCount + "/" + tileFilePaths.size() + " tile images");
    }
    
    /**
     * Render the entire level map to a BufferedImage
     */
    public void render(BufferedImage dest, int offsetX, int offsetY) {
        if (dest == null || tileGrid == null) return;
        
        java.awt.Graphics2D g2d = dest.createGraphics();
        
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                int tileId = tileGrid[row][col];
                BufferedImage tileImg = loadedTiles.get(tileId);
                
                if (tileImg != null) {
                    int x = offsetX + (col * tileSize);
                    int y = offsetY + (row * tileSize);
                    g2d.drawImage(tileImg, x, y, tileSize, tileSize, null);
                }
            }
        }
        
        g2d.dispose();
    }
    
    // Getters
    public int getMapWidth() { return mapWidth; }
    public int getMapHeight() { return mapHeight; }
    public int getTileSize() { return tileSize; }
    public int getLevelNumber() { return levelNumber; }
    public BufferedImage getTile(int tileId) { return loadedTiles.get(tileId); }
}
