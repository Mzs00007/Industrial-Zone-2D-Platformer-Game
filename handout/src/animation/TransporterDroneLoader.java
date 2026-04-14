/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public static class AnimationAndSpriteLoader.TransporterDroneLoader
extends AnimationAndSpriteLoader.AssetType {
    private TransporterType type;
    private float movementSpeed;
    private String currentState;
    private Map<String, AnimationAndSpriteLoader.HorizontalSpritesheetLoader> stateLoaders;
    private Map<String, int[]> playerOffsets;
    private String originTileName;
    private String destinationTileName;
    private String zoneName;

    public TransporterType getTransporterType() {
        return this.type;
    }

    public AnimationAndSpriteLoader.TransporterDroneLoader(String string, String string2, TransporterType transporterType) {
        super(string, string2);
        this.type = transporterType;
        this.movementSpeed = transporterType.defaultSpeed;
        this.currentState = "idle";
        this.stateLoaders = new HashMap<String, AnimationAndSpriteLoader.HorizontalSpritesheetLoader>();
        this.playerOffsets = new HashMap<String, int[]>();
        this.originTileName = "UNSET";
        this.destinationTileName = "UNSET";
        this.zoneName = "Generic Zone";
    }

    public void setPlacementContext(String string, String string2, String string3) {
        this.originTileName = string;
        this.destinationTileName = string2;
        this.zoneName = string3;
    }

    @Override
    public boolean load() {
        try {
            AnimationAndSpriteLoader.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
            AnimationAndSpriteLoader.log("Loading Transporter: " + this.assetName + " (" + this.type.description + ")");
            AnimationAndSpriteLoader.log("Zone: " + this.zoneName);
            AnimationAndSpriteLoader.log("Placement: " + this.originTileName + " \u2192 " + this.destinationTileName);
            AnimationAndSpriteLoader.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
            this.loadState("idle", this.filePath, 4, 100);
            this.loadState("walk", this.filePath, 4, 100);
            this.loadState("drop", this.filePath, 4, 150);
            this.loadState("deploy", this.filePath, 4, 150);
            this.loadState("special", this.filePath, 4, 120);
            if (this.type == TransporterType.HOVER_PLATFORM) {
                this.playerOffsets.put("all", new int[]{0, -50});
            } else {
                this.playerOffsets.put("all", new int[]{0, 60});
            }
            AnimationAndSpriteLoader.log("\u2713 Transporter loaded: " + this.assetName);
            AnimationAndSpriteLoader.log("  Type: " + this.type.description);
            AnimationAndSpriteLoader.log("  Speed: " + this.movementSpeed + " px/sec");
            AnimationAndSpriteLoader.log("  Positioning: " + this.type.positionMode);
            AnimationAndSpriteLoader.log("  States: " + this.stateLoaders.size() + " (idle, walk, drop, deploy, special)");
            AnimationAndSpriteLoader.log("  Origin Tile: " + this.originTileName);
            AnimationAndSpriteLoader.log("  Destination Tile: " + this.destinationTileName);
            AnimationAndSpriteLoader.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
            return true;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to load transporter: " + this.assetName);
            AnimationAndSpriteLoader.logError("Path: " + this.filePath);
            AnimationAndSpriteLoader.logError("Type: " + this.type.description);
            AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
            return false;
        }
    }

    private void loadState(String string, String string2, int n, int n2) {
        String string3 = this.filePath + "/" + string;
        String string4 = string3 + "/" + string + "_4Frames.png";
        AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(this.assetName + "_" + string, string4, 64, 64, n);
        if (horizontalSpritesheetLoader.load()) {
            this.stateLoaders.put(string, horizontalSpritesheetLoader);
            AnimationAndSpriteLoader.log("  \u2713 State loaded: " + string + " (" + n + " frames @ " + n2 + "ms)");
        } else {
            AnimationAndSpriteLoader.logError("  \u2717 Failed to load state: " + string);
        }
    }

    public BufferedImage getFrame(String string, int n) {
        AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = this.stateLoaders.get(string);
        if (horizontalSpritesheetLoader == null) {
            AnimationAndSpriteLoader.logError("State not found: " + string + ". Available: " + String.valueOf(this.stateLoaders.keySet()));
            return null;
        }
        return horizontalSpritesheetLoader.getFrame(n);
    }

    @Override
    public BufferedImage getFrame(int n) {
        return this.getFrame(this.currentState, n);
    }

    public void setState(String string) {
        if (this.stateLoaders.containsKey(string)) {
            this.currentState = string;
            AnimationAndSpriteLoader.log("Transporter state changed: " + string);
        } else {
            AnimationAndSpriteLoader.logError("Invalid transporter state: " + string);
        }
    }

    public int[] getPlayerPositionOffset() {
        return this.playerOffsets.getOrDefault("all", new int[]{0, 0});
    }

    @Override
    public int getFrameCount() {
        AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = this.stateLoaders.get(this.currentState);
        return horizontalSpritesheetLoader != null ? horizontalSpritesheetLoader.getFrameCount() : 4;
    }

    @Override
    public int getFrameWidth() {
        return 64;
    }

    @Override
    public int getFrameHeight() {
        return 64;
    }

    public static enum TransporterType {
        HOVER_PLATFORM("Hover Platform - Player stands on top", 350.0f, "ON_TOP"),
        HELICOPTER("Helicopter - Player hangs from cable", 450.0f, "HANGING");

        public final String description;
        public final float defaultSpeed;
        public final String positionMode;

        private TransporterType(String string2, float f, String string3) {
            this.description = string2;
            this.defaultSpeed = f;
            this.positionMode = string3;
        }
    }

    public static enum TransporterState {
        IDLE("Waiting for player at platform"),
        WALK("Moving horizontally along path"),
        DROP("Descending to pick up player"),
        DEPLOY("Ascending after dropping player"),
        SPECIAL("Special effect/capsule activation");

        public final String description;

        private TransporterState(String string2) {
            this.description = string2;
        }
    }
}
