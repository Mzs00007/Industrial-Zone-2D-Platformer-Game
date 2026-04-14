/*
 * Decompiled with CFR 0.152.
 */
package gui.screens;

import core.GameState;
import gui.screens.AssetDrivenScreen;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CharacterCardScreen
extends AssetDrivenScreen {
    private BufferedImage bikerIdleSprite;
    private BufferedImage cyborgIdleSprite;
    private BufferedImage punkIdleSprite;
    private long lastFrameTime = 0L;
    private int currentBikerFrame = 0;
    private int currentCyborgFrame = 0;
    private int currentPunkFrame = 0;
    private static final int IDLE_FRAME_COUNT = 4;
    private static final int IDLE_FRAME_TIMING_MS = 150;
    private static final int FRAME_WIDTH = 64;
    private static final int FRAME_HEIGHT = 64;

    public CharacterCardScreen() {
        super(GameState.CHARACTER_SELECT);
        this.loadAssets();
    }

    private void loadAssets() {
        String string = "Resources/industrial-zone/characters/player/biker/01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png";
        String string2 = "Resources/industrial-zone/characters/player/cyborg/01_Player_Cyborg_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png";
        String string3 = "Resources/industrial-zone/characters/player/punk/01_Player_Punk_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png";
        this.bikerIdleSprite = this.loadImage(string);
        this.cyborgIdleSprite = this.loadImage(string2);
        this.punkIdleSprite = this.loadImage(string3);
        if (this.bikerIdleSprite == null) {
            System.err.println("[CharacterCardScreen] \u2717 Biker sprite failed to load");
        } else {
            System.out.println("[CharacterCardScreen] \u2713 Biker sprite loaded");
        }
        if (this.cyborgIdleSprite == null) {
            System.err.println("[CharacterCardScreen] \u2717 Cyborg sprite failed to load");
        } else {
            System.out.println("[CharacterCardScreen] \u2713 Cyborg sprite loaded");
        }
        if (this.punkIdleSprite == null) {
            System.err.println("[CharacterCardScreen] \u2717 Punk sprite failed to load");
        } else {
            System.out.println("[CharacterCardScreen] \u2713 Punk sprite loaded");
        }
    }

    @Override
    public void update(float f) {
        this.lastFrameTime += (long)(f * 1000.0f);
        if (this.lastFrameTime > 150L) {
            this.currentBikerFrame = (this.currentBikerFrame + 1) % 4;
            this.currentCyborgFrame = (this.currentCyborgFrame + 1) % 4;
            this.currentPunkFrame = (this.currentPunkFrame + 1) % 4;
            this.lastFrameTime = 0L;
        }
    }

    @Override
    public void renderBitmap(Graphics2D graphics2D, int n, int n2) {
        int n3;
        if (this.bikerIdleSprite != null) {
            n3 = this.currentBikerFrame * 64;
            graphics2D.drawImage(this.bikerIdleSprite, 200, 300, 328, 428, n3, 0, n3 + 64, 64, null);
        }
        if (this.cyborgIdleSprite != null) {
            n3 = this.currentCyborgFrame * 64;
            graphics2D.drawImage(this.cyborgIdleSprite, 540, 300, 668, 428, n3, 0, n3 + 64, 64, null);
        }
        if (this.punkIdleSprite != null) {
            n3 = this.currentPunkFrame * 64;
            graphics2D.drawImage(this.punkIdleSprite, 880, 300, 1008, 428, n3, 0, n3 + 64, 64, null);
        }
    }
}
