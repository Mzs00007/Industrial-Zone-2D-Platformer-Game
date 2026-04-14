/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public static class AnimationAndSpriteLoader.CharacterRemoteAnimationLoader
extends AnimationAndSpriteLoader.AssetType {
    private CharacterType character;
    private Map<String, AnimationAndSpriteLoader.SingleSpriteLoader> poses;
    private int syncFrameRateMs;

    public AnimationAndSpriteLoader.CharacterRemoteAnimationLoader(String string, String string2, CharacterType characterType) {
        super(string, string2);
        this.character = characterType;
        this.poses = new HashMap<String, AnimationAndSpriteLoader.SingleSpriteLoader>();
        this.syncFrameRateMs = 100;
    }

    @Override
    public boolean load() {
        try {
            AnimationAndSpriteLoader.log("Loading Character Remote Animation: " + this.assetName + " (" + this.character.description + ")");
            this.loadPose("standing_on_platform", this.filePath + this.character.name().toLowerCase() + "_standing_on_platform.png");
            this.loadPose("hanging_from_rope", this.filePath + this.character.name().toLowerCase() + "_hanging_from_rope.png");
            this.loadSwingPoses();
            this.loadPose("falling", this.filePath + this.character.name().toLowerCase() + "_falling.png");
            AnimationAndSpriteLoader.log("\u2713 Character remote animation loaded: " + this.assetName);
            AnimationAndSpriteLoader.log("  Character: " + this.character.description);
            AnimationAndSpriteLoader.log("  Synchronized frame rate: " + this.syncFrameRateMs + "ms");
            return true;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to load character remote animation: " + this.assetName);
            AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
            return false;
        }
    }

    private void loadPose(String string, String string2) {
        AnimationAndSpriteLoader.SingleSpriteLoader singleSpriteLoader = new AnimationAndSpriteLoader.SingleSpriteLoader(this.assetName + "_" + string, string2);
        if (singleSpriteLoader.load()) {
            this.poses.put(string, singleSpriteLoader);
            AnimationAndSpriteLoader.log("  \u2713 Pose loaded: " + string);
        }
    }

    private void loadSwingPoses() {
        String string = this.filePath + this.character.name().toLowerCase() + "_swing_4Frames.png";
        AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader(this.assetName + "_swinging", string, 64, 64, 4);
        if (horizontalSpritesheetLoader.load()) {
            for (int i = 0; i < horizontalSpritesheetLoader.getFrameCount(); ++i) {
                final int n = i;
                final AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader2 = horizontalSpritesheetLoader;
                AnimationAndSpriteLoader.SingleSpriteLoader singleSpriteLoader = new AnimationAndSpriteLoader.SingleSpriteLoader(this, this.assetName + "_swinging_" + i, string){

                    @Override
                    public BufferedImage getFrame(int n2) {
                        return horizontalSpritesheetLoader2.getFrame(n);
                    }
                };
                this.poses.put("swinging_" + i, singleSpriteLoader);
            }
            AnimationAndSpriteLoader.log("  \u2713 Swing animation loaded (4 frames)");
        }
    }

    public BufferedImage getFrame(String string) {
        AnimationAndSpriteLoader.SingleSpriteLoader singleSpriteLoader = this.poses.get(string);
        if (singleSpriteLoader == null) {
            AnimationAndSpriteLoader.logError("Pose not found: " + string);
            return null;
        }
        return singleSpriteLoader.getFrame(0);
    }

    public BufferedImage getSwingFrame(float f) {
        int n = Math.round(f * 3.0f) % 4;
        return this.getFrame("swinging_" + n);
    }

    @Override
    public BufferedImage getFrame(int n) {
        return this.getFrame("standing_on_platform");
    }

    @Override
    public int getFrameCount() {
        return 1;
    }

    @Override
    public int getFrameWidth() {
        return 64;
    }

    @Override
    public int getFrameHeight() {
        return 64;
    }

    public static enum CharacterType {
        CYBORG("Cyborg - Heavy built, stable"),
        PUNK("Punk - Slim, agile"),
        BIKER("Biker - Medium, balanced");

        public final String description;

        private CharacterType(String string2) {
            this.description = string2;
        }
    }
}
