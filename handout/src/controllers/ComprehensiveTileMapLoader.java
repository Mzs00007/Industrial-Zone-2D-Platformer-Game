/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public class ComprehensiveTileMapLoader {
    private static final String TAG = "[ComprehensiveTileMapLoader]";
    private static final boolean VERBOSE = true;
    private Map<Character, String> tileDefinitions = new HashMap<Character, String>();
    private Map<Character, BufferedImage> tileCache = new HashMap<Character, BufferedImage>();
    private List<AnimatedObject> animatedObjects = new ArrayList<AnimatedObject>();
    private List<BackgroundLayer> backgroundLayers = new ArrayList<BackgroundLayer>();
    private Map<String, String> objectMetadata = new HashMap<String, String>();
    private int mapWidth;
    private int mapHeight;
    private int tileSize;
    private String levelName;
    private String levelPath;

    public ComprehensiveTileMapLoader(String string, String string2) {
        this.levelName = string2;
        this.levelPath = "Resources/industrial-zone/";
        this.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        this.log("LOADING COMPLETE ASSET SYSTEM: " + string2);
        this.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        try {
            this.loadMapFile(string);
            this.loadTileAssets();
            this.loadAnimatedObjects();
            this.loadBackgroundLayers();
            this.log("\u2713 ASSET LOAD COMPLETE");
            this.log("  - Tile definitions: " + this.tileDefinitions.size());
            this.log("  - Animated objects:  " + this.animatedObjects.size());
            this.log("  - Background layers: " + this.backgroundLayers.size());
            this.log("  - Total cached:      " + this.tileCache.size() + " tiles");
        }
        catch (Exception exception) {
            this.logError("Failed to load assets: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void loadMapFile(String string) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(string));
        int n = 0;
        String string2 = bufferedReader.readLine();
        ++n;
        String[] stringArray = string2.trim().split("\\s+");
        if (stringArray.length >= 3) {
            this.mapWidth = Integer.parseInt(stringArray[0]);
            this.mapHeight = Integer.parseInt(stringArray[1]);
            this.tileSize = Integer.parseInt(stringArray[2]);
            this.log("Map dimensions: " + this.mapWidth + "x" + this.mapHeight + " tiles (" + this.tileSize + "px)");
        }
        while ((string2 = bufferedReader.readLine()) != null) {
            String[] stringArray2;
            ++n;
            if ((string2 = string2.trim()).isEmpty() || string2.startsWith("//")) continue;
            if (string2.equals("#map")) {
                this.log("Loaded " + this.tileDefinitions.size() + " tile definitions");
                break;
            }
            if (!string2.contains("=") || (stringArray2 = string2.split("=")).length != 2) continue;
            String string3 = stringArray2[0].trim();
            String string4 = stringArray2[1].trim();
            if (string3.length() != 1 || string4.equals("empty air")) continue;
            char c = string3.charAt(0);
            this.tileDefinitions.put(Character.valueOf(c), string4);
        }
        bufferedReader.close();
    }

    private void loadTileAssets() {
        this.log("Loading tile graphics...");
        int n = 0;
        int n2 = 0;
        for (Map.Entry<Character, String> entry : this.tileDefinitions.entrySet()) {
            char c = entry.getKey().charValue();
            String string = entry.getValue();
            try {
                String string2 = this.levelPath + "1 Tiles/Industrial_zone_level_1/1 Tiles/";
                String string3 = string2 + string;
                BufferedImage bufferedImage = ImageIO.read(new File(string3));
                if (bufferedImage != null) {
                    this.tileCache.put(Character.valueOf(c), bufferedImage);
                    ++n;
                    continue;
                }
                this.logError("Failed to load tile image: " + string3);
                ++n2;
            }
            catch (Exception exception) {
                this.logError("Error loading tile " + c + ": " + string);
                ++n2;
            }
        }
        this.log("Loaded " + n + " tiles (" + n2 + " failed)");
    }

    private void loadAnimatedObjects() {
        this.log("Scanning animated objects...");
        try {
            File file2 = new File(this.levelPath + "1 Tiles/Industrial_zone_level_1/4 Animated objects/");
            if (!file2.exists()) {
                this.logError("Animated objects directory not found: " + file2.getAbsolutePath());
                return;
            }
            File[] fileArray = file2.listFiles((file, string) -> string.toLowerCase().endsWith(".png"));
            if (fileArray == null) {
                this.logError("No PNG files found in animated objects directory");
                return;
            }
            for (File file3 : fileArray) {
                String string2 = file3.getName().replace(".png", "");
                AnimatedObject animatedObject = new AnimatedObject(string2, file3.getAbsolutePath());
                this.animatedObjects.add(animatedObject);
            }
            this.log("Found " + this.animatedObjects.size() + " animated objects");
            for (AnimatedObject animatedObject : this.animatedObjects) {
                try {
                    AnimationAndSpriteLoader.AssetType assetType = AnimationAndSpriteLoader.load(animatedObject.name, animatedObject.filePath);
                    if (assetType == null) continue;
                    animatedObject.frameCount = assetType.getFrameCount();
                    animatedObject.frameWidth = assetType.getFrameWidth();
                    animatedObject.frameHeight = assetType.getFrameHeight();
                }
                catch (Exception exception) {
                    this.logError("Could not load animation frames for " + animatedObject.name + ": " + exception.getMessage());
                }
            }
            this.log("\u2713 Registered " + this.animatedObjects.size() + " animations with system");
        }
        catch (Exception exception) {
            this.logError("Error scanning animated objects: " + exception.getMessage());
        }
    }

    private void loadBackgroundLayers() {
        this.log("Loading background layers...");
        try {
            File file3 = new File(this.levelPath + "1 Tiles/Industrial_zone_level_1/2 Background_level_1/");
            if (!file3.exists()) {
                this.logError("Background directory not found: " + file3.getAbsolutePath());
                return;
            }
            File[] fileArray = file3.listFiles((file, string) -> string.toLowerCase().endsWith(".png"));
            if (fileArray == null) {
                this.logError("No background images found");
                return;
            }
            Arrays.sort(fileArray, (file, file2) -> {
                Integer n = this.extractPriority(file.getName());
                Integer n2 = this.extractPriority(file2.getName());
                return n.compareTo(n2);
            });
            for (int i = 0; i < fileArray.length; ++i) {
                File file4 = fileArray[i];
                int n = i;
                float f = 0.5f - (float)i * 0.1f;
                BackgroundLayer backgroundLayer = new BackgroundLayer(n, f, f, file4.getAbsolutePath());
                try {
                    backgroundLayer.image = ImageIO.read(file4);
                    this.backgroundLayers.add(backgroundLayer);
                    this.log("  Loaded background #" + n + ": " + file4.getName() + " (parallax: " + String.format("%.2f", Float.valueOf(f)) + ")");
                    continue;
                }
                catch (Exception exception) {
                    this.logError("Failed to load background: " + file4.getName());
                }
            }
            this.log("Loaded " + this.backgroundLayers.size() + " background layers");
        }
        catch (Exception exception) {
            this.logError("Error loading backgrounds: " + exception.getMessage());
        }
    }

    private int extractPriority(String string) {
        try {
            String[] stringArray = string.split("[_.]");
            if (stringArray.length > 0) {
                return Integer.parseInt(stringArray[0]);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return 0;
    }

    public BufferedImage getTile(char c) {
        return this.tileCache.get(Character.valueOf(c));
    }

    public String getTileFilename(char c) {
        return this.tileDefinitions.get(Character.valueOf(c));
    }

    public List<AnimatedObject> getAnimatedObjects() {
        return new ArrayList<AnimatedObject>(this.animatedObjects);
    }

    public List<BackgroundLayer> getBackgroundLayers() {
        return new ArrayList<BackgroundLayer>(this.backgroundLayers);
    }

    public int getMapWidth() {
        return this.mapWidth;
    }

    public int getMapHeight() {
        return this.mapHeight;
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public int getPixelWidth() {
        return this.mapWidth * this.tileSize;
    }

    public int getPixelHeight() {
        return this.mapHeight * this.tileSize;
    }

    private void log(String string) {
        System.out.println("[ComprehensiveTileMapLoader] " + string);
    }

    private void logError(String string) {
        System.err.println("[ComprehensiveTileMapLoader] ERROR: " + string);
    }

    public static class AnimatedObject {
        public String name;
        public String filePath;
        public int frameCount;
        public int frameWidth;
        public int frameHeight;
        public int animationSpeed;

        public AnimatedObject(String string, String string2) {
            this.name = string;
            this.filePath = string2;
            this.frameCount = 1;
            this.frameWidth = 32;
            this.frameHeight = 32;
            this.animationSpeed = 100;
        }

        public String toString() {
            return this.name + " [" + this.frameWidth + "x" + this.frameHeight + "px, " + this.frameCount + " frames]";
        }
    }

    public static class BackgroundLayer {
        public int priority;
        public float parallaxX;
        public float parallaxY;
        public String imagePath;
        public BufferedImage image;

        public BackgroundLayer(int n, float f, float f2, String string) {
            this.priority = n;
            this.parallaxX = f;
            this.parallaxY = f2;
            this.imagePath = string;
        }
    }
}
