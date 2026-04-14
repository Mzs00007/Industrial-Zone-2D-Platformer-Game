/*
 * Decompiled with CFR 0.152.
 */
package gui.screens;

import core.GameState;
import gui.screens.AssetDrivenScreen;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Phase2CharacterIdleScreen
extends AssetDrivenScreen {
    private BufferedImage bikerIdleSprite = null;
    private BufferedImage cyborgIdleSprite = null;
    private BufferedImage punkIdleSprite = null;
    private long lastFrameTime = 0L;
    private int currentBikerFrame = 0;
    private int currentCyborgFrame = 0;
    private int currentPunkFrame = 0;
    private static final int IDLE_FRAME_TIMING_MS = 150;
    private static final int BIKER_IDLE_FRAMES = 4;
    private static final int CYBORG_IDLE_FRAMES = 4;
    private static final int PUNK_IDLE_FRAMES = 5;

    public Phase2CharacterIdleScreen() {
        super(GameState.CHARACTER_SELECT);
        this.loadAssetsOnce();
    }

    private void loadAssetsOnce() {
        String string = "Resources/industrial-zone/characters/player/biker/01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png";
        String string2 = "Resources/industrial-zone/characters/player/cyborg/01_Player_Cyborg_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png";
        String string3 = "Resources/industrial-zone/characters/player/punk/01_Player_Punk_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png";
        this.bikerIdleSprite = this.loadImage(string);
        this.cyborgIdleSprite = this.loadImage(string2);
        this.punkIdleSprite = this.loadImage(string3);
        if (this.bikerIdleSprite == null) {
            System.err.println("[Phase2CharacterIdleScreen] \u2717 FAILED to load: " + string);
        } else {
            System.out.println("[Phase2CharacterIdleScreen] \u2713 Loaded Biker idle sprite: " + this.bikerIdleSprite.getWidth() + "x" + this.bikerIdleSprite.getHeight());
        }
        if (this.cyborgIdleSprite == null) {
            System.err.println("[Phase2CharacterIdleScreen] \u2717 FAILED to load: " + string2);
        } else {
            System.out.println("[Phase2CharacterIdleScreen] \u2713 Loaded Cyborg idle sprite: " + this.cyborgIdleSprite.getWidth() + "x" + this.cyborgIdleSprite.getHeight());
        }
        if (this.punkIdleSprite == null) {
            System.err.println("[Phase2CharacterIdleScreen] \u2717 FAILED to load: " + string3);
        } else {
            System.out.println("[Phase2CharacterIdleScreen] \u2713 Loaded Punk idle sprite: " + this.punkIdleSprite.getWidth() + "x" + this.punkIdleSprite.getHeight());
        }
    }

    @Override
    public void update(float f) {
        long l = System.currentTimeMillis();
        if (this.lastFrameTime == 0L) {
            this.lastFrameTime = l;
            return;
        }
        long l2 = l - this.lastFrameTime;
        if (l2 >= 150L) {
            this.lastFrameTime = l;
            this.currentBikerFrame = (this.currentBikerFrame + 1) % 4;
            this.currentCyborgFrame = (this.currentCyborgFrame + 1) % 4;
            this.currentPunkFrame = (this.currentPunkFrame + 1) % 5;
        }
    }

    @Override
    public void renderBitmap(Graphics2D graphics2D, int n, int n2) {
        if (this.bikerIdleSprite == null || this.cyborgIdleSprite == null || this.punkIdleSprite == null) {
            return;
        }
        int n3 = this.bikerIdleSprite.getWidth() / 4;
        int n4 = this.bikerIdleSprite.getHeight();
        int n5 = this.cyborgIdleSprite.getWidth() / 4;
        int n6 = this.cyborgIdleSprite.getHeight();
        int n7 = this.punkIdleSprite.getWidth() / 5;
        int n8 = this.punkIdleSprite.getHeight();
        int n9 = this.currentBikerFrame * n3;
        int n10 = 0;
        int n11 = 50;
        int n12 = 100;
        int n13 = 150;
        int n14 = 200;
        graphics2D.drawImage(this.bikerIdleSprite, n11, n12, n11 + n13, n12 + n14, n9, n10, n9 + n3, n10 + n4, null);
        int n15 = this.currentCyborgFrame * n5;
        int n16 = 0;
        int n17 = n / 2 - 75;
        int n18 = 100;
        int n19 = 150;
        int n20 = 200;
        graphics2D.drawImage(this.cyborgIdleSprite, n17, n18, n17 + n19, n18 + n20, n15, n16, n15 + n5, n16 + n6, null);
        int n21 = this.currentPunkFrame * n7;
        int n22 = 0;
        int n23 = n - 200;
        int n24 = 100;
        int n25 = 150;
        int n26 = 200;
        graphics2D.drawImage(this.punkIdleSprite, n23, n24, n23 + n25, n24 + n26, n21, n22, n21 + n7, n22 + n8, null);
    }
}
