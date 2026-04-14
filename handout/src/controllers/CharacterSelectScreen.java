/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import animation.AnimationAndSpriteLoader;
import controllers.AnimatedCharacterProfile;
import controllers.AnimationState;
import controllers.FrameTiler;
import controllers.GUIAssetManager;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class CharacterSelectScreen
extends AnimationAndSpriteLoader {
    private AnimatedCharacterProfile[] characters = new AnimatedCharacterProfile[3];
    private int selectedIndex = -1;
    private int hoveredIndex = -1;
    private GUIAssetManager assetManager = GUIAssetManager.getInstance();
    private FrameTiler frameTiler = new FrameTiler();

    public CharacterSelectScreen() {
        System.out.println("\n[CharacterSelectScreen] Initializing...\n");
        this.initializeCharacters();
        System.out.println("\n[CharacterSelectScreen] Ready to display\n");
    }

    private void initializeCharacters() {
        this.characters[0] = new AnimatedCharacterProfile("BIKER", "Resources/industrial-zone/characters/biker/");
        this.characters[0].setStats(100, 18, 14, 10);
        this.characters[0].setWeapon("Gun");
        this.characters[0].setDescription("Battle-hardened veteran with balanced stats and steady aim.");
        this.characters[0].registerAnimation(AnimationState.IDLE, "Resources/industrial-zone/characters/biker/01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png", 4, 150);
        this.characters[0].registerAnimation(AnimationState.WALK, "Resources/industrial-zone/characters/biker/03_Player_Biker_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png", 6, 100);
        this.characters[0].registerAnimation(AnimationState.ATTACK, "Resources/industrial-zone/characters/biker/12_Player_Biker_Punch_5Frames1Row_StandingPunchCombo_MeleeAttack_PlayOnce_70ms.png", 5, 70);
        this.characters[0].registerAnimation(AnimationState.JUMP, "Resources/industrial-zone/characters/biker/06_Player_Biker_Jump_4Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png", 4, 80);
        this.characters[0].registerAnimation(AnimationState.DOUBLE_JUMP, "Resources/industrial-zone/characters/biker/07_Player_Biker_DoubleJump_6Frames1Row_MidAirFlipBoost_SecondJump_PlayOnce_80ms.png", 6, 80);
        this.characters[0].registerAnimation(AnimationState.FALL, "Resources/industrial-zone/characters/biker/08_Player_Biker_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png", 4, 100);
        this.characters[0].registerAnimation(AnimationState.DASH, "Resources/industrial-zone/characters/biker/05_Player_Biker_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png", 6, 60);
        this.characters[0].registerAnimation(AnimationState.CLIMB, "Resources/industrial-zone/characters/biker/09_Player_Biker_Climb_6Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png", 6, 120);
        this.characters[0].registerAnimation(AnimationState.HANG, "Resources/industrial-zone/characters/biker/10_Player_Biker_Hang_3Frames1Row_LedgeHangHold_HangingIdle_Loop_150ms.png", 3, 150);
        this.characters[0].startAnimationCycle();
        System.out.println("[CharacterSelectScreen] \u2713 Initialized BIKER");
        this.characters[1] = new AnimatedCharacterProfile("PUNK", "Resources/industrial-zone/characters/punk/");
        this.characters[1].setStats(85, 22, 16, 5);
        this.characters[1].setWeapon("Sword");
        this.characters[1].setDescription("Agile street fighter excelling at close-quarters combat with devastating blade technique.");
        this.characters[1].registerAnimation(AnimationState.IDLE, "Resources/industrial-zone/characters/punk/01_Player_Punk_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png", 4, 150);
        this.characters[1].registerAnimation(AnimationState.WALK, "Resources/industrial-zone/characters/punk/03_Player_Punk_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png", 6, 100);
        this.characters[1].registerAnimation(AnimationState.ATTACK, "Resources/industrial-zone/characters/punk/12_Player_Punk_Slash_6Frames1Row_SwordCombo_MeleeAttack_PlayOnce_70ms.png", 6, 70);
        this.characters[1].registerAnimation(AnimationState.JUMP, "Resources/industrial-zone/characters/punk/06_Player_Punk_Jump_4Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png", 4, 80);
        this.characters[1].registerAnimation(AnimationState.DOUBLE_JUMP, "Resources/industrial-zone/characters/punk/07_Player_Punk_DoubleJump_6Frames1Row_MidAirFlipBoost_SecondJump_PlayOnce_80ms.png", 6, 80);
        this.characters[1].registerAnimation(AnimationState.FALL, "Resources/industrial-zone/characters/punk/08_Player_Punk_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png", 4, 100);
        this.characters[1].registerAnimation(AnimationState.DASH, "Resources/industrial-zone/characters/punk/05_Player_Punk_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png", 6, 60);
        this.characters[1].registerAnimation(AnimationState.CLIMB, "Resources/industrial-zone/characters/punk/09_Player_Punk_Climb_6Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png", 6, 120);
        this.characters[1].registerAnimation(AnimationState.HANG, "Resources/industrial-zone/characters/punk/10_Player_Punk_Hang_3Frames1Row_LedgeHangHold_HangingIdle_Loop_150ms.png", 3, 150);
        this.characters[1].startAnimationCycle();
        System.out.println("[CharacterSelectScreen] \u2713 Initialized PUNK");
        this.characters[2] = new AnimatedCharacterProfile("CYBORG", "Resources/industrial-zone/characters/cyborg/");
        this.characters[2].setStats(120, 20, 10, 15);
        this.characters[2].setWeapon("Cannon");
        this.characters[2].setDescription("Armored unit with superior defense and devastating ranged firepower.");
        this.characters[2].registerAnimation(AnimationState.IDLE, "Resources/industrial-zone/characters/cyborg/01_Player_Cyborg_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png", 4, 150);
        this.characters[2].registerAnimation(AnimationState.WALK, "Resources/industrial-zone/characters/cyborg/03_Player_Cyborg_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png", 6, 100);
        this.characters[2].registerAnimation(AnimationState.ATTACK, "Resources/industrial-zone/characters/cyborg/12_Player_Cyborg_Shoot_5Frames1Row_StandingRangedAttack_MeleeAttack_PlayOnce_70ms.png", 5, 70);
        this.characters[2].registerAnimation(AnimationState.JUMP, "Resources/industrial-zone/characters/cyborg/06_Player_Cyborg_Jump_4Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png", 4, 80);
        this.characters[2].registerAnimation(AnimationState.DOUBLE_JUMP, "Resources/industrial-zone/characters/cyborg/07_Player_Cyborg_DoubleJump_6Frames1Row_MidAirFlipBoost_SecondJump_PlayOnce_80ms.png", 6, 80);
        this.characters[2].registerAnimation(AnimationState.FALL, "Resources/industrial-zone/characters/cyborg/08_Player_Cyborg_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png", 4, 100);
        this.characters[2].registerAnimation(AnimationState.DASH, "Resources/industrial-zone/characters/cyborg/05_Player_Cyborg_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png", 6, 60);
        this.characters[2].registerAnimation(AnimationState.CLIMB, "Resources/industrial-zone/characters/cyborg/09_Player_Cyborg_Climb_6Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png", 6, 120);
        this.characters[2].registerAnimation(AnimationState.HANG, "Resources/industrial-zone/characters/cyborg/10_Player_Cyborg_Hang_3Frames1Row_LedgeHangHold_HangingIdle_Loop_150ms.png", 3, 150);
        this.characters[2].startAnimationCycle();
        System.out.println("[CharacterSelectScreen] \u2713 Initialized CYBORG");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public BufferedImage render(int n, int n2) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 1);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            int n3;
            BufferedImage bufferedImage2 = this.assetManager.getImage("Resources/industrial-zone/gui/backgrounds/character_select_bg.png");
            if (bufferedImage2 == null) {
                System.err.println("[CharacterSelectScreen] FATAL: Background image missing");
                System.err.println("Expected: Resources/industrial-zone/gui/backgrounds/character_select_bg.png");
                BufferedImage bufferedImage3 = null;
                return bufferedImage3;
            }
            graphics2D.drawImage(bufferedImage2, 0, 0, n, n2, null);
            BufferedImage bufferedImage4 = this.assetManager.getImage("Resources/industrial-zone/gui/text/title_select_character.png");
            if (bufferedImage4 != null) {
                n3 = (n - bufferedImage4.getWidth()) / 2;
                graphics2D.drawImage((Image)bufferedImage4, n3, 50, null);
            } else {
                System.err.println("[CharacterSelectScreen] WARNING:  Title image missing");
            }
            n3 = 256;
            int n4 = 384;
            int[] nArray = new int[]{180, 512, 844};
            int n5 = 140;
            for (int i = 0; i < 3; ++i) {
                this.drawAnimatedCharacterCard(graphics2D, i, nArray[i], n5, n3, n4);
            }
            if (this.selectedIndex >= 0) {
                this.drawCharacterStats(graphics2D, 1080, 140, 180, 380);
            }
            this.drawBackButton(graphics2D, 200, 650);
            this.drawSelectButton(graphics2D, 600, 650);
        }
        finally {
            graphics2D.dispose();
        }
        return bufferedImage;
    }

    private void drawAnimatedCharacterCard(Graphics2D graphics2D, int n, int n2, int n3, int n4, int n5) {
        BufferedImage bufferedImage;
        AnimatedCharacterProfile animatedCharacterProfile = this.characters[n];
        animatedCharacterProfile.updateAnimationFrame();
        BufferedImage bufferedImage2 = null;
        bufferedImage2 = this.selectedIndex == n ? this.frameTiler.buildCardFrame(n4, n5, true) : this.frameTiler.buildCardFrame(n4, n5, false);
        if (bufferedImage2 != null) {
            graphics2D.drawImage((Image)bufferedImage2, n2, n3, null);
        } else {
            System.err.println("[CharacterSelectScreen] WARNING: Failed to assemble card frame at index " + n);
        }
        BufferedImage bufferedImage3 = this.getCurrentAnimationFrame(animatedCharacterProfile);
        if (bufferedImage3 != null) {
            bufferedImage = this.assetManager.scaleImage(bufferedImage3, n4, n5);
            graphics2D.drawImage((Image)bufferedImage, n2, n3, null);
        }
        if ((bufferedImage = this.assetManager.getImage("Resources/industrial-zone/gui/text/label_" + animatedCharacterProfile.characterName.toLowerCase() + ".png")) != null) {
            graphics2D.drawImage((Image)bufferedImage, n2 + 20, n3 + n5 + 10, null);
        }
    }

    private BufferedImage getCurrentAnimationFrame(AnimatedCharacterProfile animatedCharacterProfile) {
        AnimationState animationState = animatedCharacterProfile.getCurrentAnimationState();
        String string = animatedCharacterProfile.stateToAssetPath.get((Object)animationState);
        if (string == null) {
            return null;
        }
        BufferedImage bufferedImage = this.assetManager.getImage(string);
        if (bufferedImage == null) {
            return null;
        }
        int n = animatedCharacterProfile.getCurrentFrameCount();
        int n2 = animatedCharacterProfile.getCurrentFrameIndex();
        int n3 = bufferedImage.getWidth() / n;
        int n4 = bufferedImage.getHeight();
        try {
            int n5 = n2 * n3;
            return bufferedImage.getSubimage(n5, 0, n3, n4);
        }
        catch (Exception exception) {
            System.err.println("[CharacterSelectScreen] Error extracting frame: " + exception.getMessage());
            return null;
        }
    }

    private void drawCharacterStats(Graphics2D graphics2D, int n, int n2, int n3, int n4) {
        BufferedImage bufferedImage;
        BufferedImage bufferedImage2;
        BufferedImage bufferedImage3;
        BufferedImage bufferedImage4;
        if (this.selectedIndex < 0) {
            return;
        }
        AnimatedCharacterProfile animatedCharacterProfile = this.characters[this.selectedIndex];
        BufferedImage bufferedImage5 = this.frameTiler.buildPanelFrame(n3, n4);
        if (bufferedImage5 == null) {
            System.err.println("[CharacterSelectScreen] WARNING: Failed to assemble stats panel frame");
            return;
        }
        graphics2D.drawImage((Image)bufferedImage5, n, n2, null);
        BufferedImage bufferedImage6 = this.assetManager.getImage("Resources/industrial-zone/gui/text/stat_hp_" + animatedCharacterProfile.healthMax + ".png");
        if (bufferedImage6 != null) {
            graphics2D.drawImage((Image)bufferedImage6, n + 15, n2 + 30, null);
        }
        if ((bufferedImage4 = this.assetManager.getImage("Resources/industrial-zone/gui/text/stat_dmg_" + animatedCharacterProfile.damageBase + ".png")) != null) {
            graphics2D.drawImage((Image)bufferedImage4, n + 15, n2 + 50, null);
        }
        if ((bufferedImage3 = this.assetManager.getImage("Resources/industrial-zone/gui/text/stat_spd_" + animatedCharacterProfile.speedBase + ".png")) != null) {
            graphics2D.drawImage((Image)bufferedImage3, n + 15, n2 + 70, null);
        }
        if ((bufferedImage2 = this.assetManager.getImage("Resources/industrial-zone/gui/text/stat_arm_" + animatedCharacterProfile.armorBase + ".png")) != null) {
            graphics2D.drawImage((Image)bufferedImage2, n + 15, n2 + 90, null);
        }
        if ((bufferedImage = this.assetManager.getImage("Resources/industrial-zone/gui/text/stat_wpn_" + animatedCharacterProfile.weaponType.toLowerCase() + ".png")) != null) {
            graphics2D.drawImage((Image)bufferedImage, n + 15, n2 + 120, null);
        }
    }

    private void drawBackButton(Graphics2D graphics2D, int n, int n2) {
        BufferedImage bufferedImage = null;
        bufferedImage = this.isMouseOverButton(n, n2, 160, 50) ? this.assetManager.getImage("Resources/industrial-zone/gui/buttons/btn_back_hover.png") : this.assetManager.getImage("Resources/industrial-zone/gui/buttons/btn_back_normal.png");
        if (bufferedImage != null) {
            graphics2D.drawImage(bufferedImage, n, n2, 160, 50, null);
        } else {
            System.err.println("[CharacterSelectScreen] WARNING: Back button image missing");
        }
    }

    private void drawSelectButton(Graphics2D graphics2D, int n, int n2) {
        BufferedImage bufferedImage = null;
        bufferedImage = this.isMouseOverButton(n, n2, 250, 50) ? this.assetManager.getImage("Resources/industrial-zone/gui/buttons/btn_select_hover.png") : (this.selectedIndex >= 0 ? this.assetManager.getImage("Resources/industrial-zone/gui/buttons/btn_select_active.png") : this.assetManager.getImage("Resources/industrial-zone/gui/buttons/btn_select_disabled.png"));
        if (bufferedImage != null) {
            graphics2D.drawImage(bufferedImage, n, n2, 250, 50, null);
        } else {
            System.err.println("[CharacterSelectScreen] WARNING: Select button image missing");
        }
    }

    private boolean isMouseOverButton(int n, int n2, int n3, int n4) {
        return false;
    }

    public void handleMouseMove(int n, int n2) {
        this.hoveredIndex = this.getCharacterAtPosition(n, n2);
    }

    public void handleMousePress(int n, int n2) {
        int n3 = this.getCharacterAtPosition(n, n2);
        if (n3 >= 0) {
            this.selectedIndex = n3;
            System.out.println("[CharacterSelectScreen] Selected: " + this.characters[this.selectedIndex].characterName);
        }
        if (n >= 200 && n <= 360 && n2 >= 650 && n2 <= 700) {
            System.out.println("[CharacterSelectScreen] BACK clicked");
        }
        if (this.selectedIndex >= 0 && n >= 600 && n <= 850 && n2 >= 650 && n2 <= 700) {
            System.out.println("[CharacterSelectScreen] SELECT clicked - " + this.characters[this.selectedIndex].characterName);
            System.out.println("[CharacterSelectScreen] Character " + this.characters[this.selectedIndex].characterName + " selected for gameplay");
        }
    }

    private int getCharacterAtPosition(int n, int n2) {
        int[] nArray = new int[]{180, 512, 844};
        int n3 = 140;
        int n4 = 256;
        int n5 = 384;
        for (int i = 0; i < 3; ++i) {
            if (n < nArray[i] || n > nArray[i] + n4 || n2 < n3 || n2 > n3 + n5) continue;
            return i;
        }
        return -1;
    }
}
