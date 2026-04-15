/*
 * Decompiled with CFR 0.152.
 */
package animation;

import java.awt.image.BufferedImage;
public class CharacterCard {
    public String characterName;
    public String characterType;
    public String characterDescription;
    public BufferedImage cardBackground;
    private int animationFrameIndex = 0;
    private long lastFrameTime = 0L;
    private int currentFrameDuration = 100;

    public CharacterCard(String string, String string2, String string3) {
        this.characterName = string;
        this.characterType = string2;
        this.characterDescription = string3;
    }

    public boolean updateAnimation(long l) {
        if (this.lastFrameTime == 0L) {
            this.lastFrameTime = l;
            return false;
        }
        long l2 = l - this.lastFrameTime;
        if (l2 >= (long)this.currentFrameDuration) {
            ++this.animationFrameIndex;
            if (this.animationFrameIndex >= 12) {
                this.animationFrameIndex = 0;
            }
            this.lastFrameTime = l;
            return true;
        }
        return false;
    }

    public int getCurrentFrameIndex() {
        return this.animationFrameIndex;
    }

    public void resetAnimation() {
        this.animationFrameIndex = 0;
        this.lastFrameTime = System.currentTimeMillis();
    }

    public void setFrameDuration(int n) {
        this.currentFrameDuration = n;
    }
}
