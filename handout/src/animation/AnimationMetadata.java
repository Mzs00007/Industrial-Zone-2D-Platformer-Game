/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.AnimationMetadata {
    public String spriteKey;
    public String spriteFile;
    public String[] animationNames;
    public int[] frameCounts;
    public int[] frameMs;
    public String[] vfxTriggers;
    public String[] soundTriggers;
    public float[] triggerFramePercent;

    public AnimationAndSpriteLoader.AnimationMetadata(String string, String string2) {
        this.spriteKey = string;
        this.spriteFile = string2;
        this.animationNames = new String[0];
        this.frameCounts = new int[0];
        this.frameMs = new int[0];
        this.vfxTriggers = new String[0];
        this.soundTriggers = new String[0];
        this.triggerFramePercent = new float[0];
    }

    public void addAnimation(int n, int n2, int n3, String string, String string2, float f) {
        if (n >= this.frameCounts.length) {
            int[] nArray = new int[n + 1];
            int[] nArray2 = new int[n + 1];
            String[] stringArray = new String[n + 1];
            String[] stringArray2 = new String[n + 1];
            float[] fArray = new float[n + 1];
            System.arraycopy(this.frameCounts, 0, nArray, 0, this.frameCounts.length);
            System.arraycopy(this.frameMs, 0, nArray2, 0, this.frameMs.length);
            System.arraycopy(this.vfxTriggers, 0, stringArray, 0, this.vfxTriggers.length);
            System.arraycopy(this.soundTriggers, 0, stringArray2, 0, this.soundTriggers.length);
            System.arraycopy(this.triggerFramePercent, 0, fArray, 0, this.triggerFramePercent.length);
            this.frameCounts = nArray;
            this.frameMs = nArray2;
            this.vfxTriggers = stringArray;
            this.soundTriggers = stringArray2;
            this.triggerFramePercent = fArray;
        }
        this.frameCounts[n] = n2;
        this.frameMs[n] = n3;
        this.vfxTriggers[n] = string != null ? string : "none";
        this.soundTriggers[n] = string2 != null ? string2 : "none";
        this.triggerFramePercent[n] = f;
    }
}
