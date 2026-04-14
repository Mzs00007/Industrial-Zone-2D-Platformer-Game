/*
 * Decompiled with CFR 0.152.
 */
package gui;

import animation.AnimationAndSpriteLoader;
import audio.MidiTuner;
import core.GameState;
import gui.InteractiveButton;
import gui.Screen;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import vfx.SparkEffectSystem;

public class GameControlsScreen
extends Screen {
    private MidiTuner backgroundMusic;
    private SparkEffectSystem sparkSystem;
    private AnimationAndSpriteLoader animLoader;
    private BufferedImage keyboardImage;
    private BufferedImage mouseImage;
    private InteractiveButton backButton;
    private int leftPanelX = 20;
    private int rightPanelX;
    private int panelY = 120;
    private int panelWidth = this.screenWidth / 2 - 40;
    private int panelHeight = this.screenHeight - 200;
    private int imageDisplayWidth = 300;
    private int imageDisplayHeight = 300;
    private static final String KEYBOARD_IMAGE_PATH = "Resources/industrial-zone/KeyBoard_Keys/Key_Spacebar_Special_JumpOrConfirm_MovementTutorial.png";
    private static final String MOUSE_IMAGE_PATH = "Resources/industrial-zone/Mouse_keys/Mouse_Neutral_NoHighlight_DefaultIdleState_Display.png";

    public GameControlsScreen() {
        super(GameState.HOW_TO_PLAY);
        this.rightPanelX = this.screenWidth / 2 + 20;
        this.initializeAssets();
        this.createBackButton();
        this.startBackgroundMusic();
        System.out.println("[GameControlsScreen] \u2713 Initialized");
    }

    private void initializeAssets() {
        try {
            this.animLoader = new AnimationAndSpriteLoader();
            this.sparkSystem = new SparkEffectSystem(this.animLoader);
            this.backgroundMusic = new MidiTuner("ControlsTheme.mid", -1);
            this.loadReferenceImages();
            System.out.println("[GameControlsScreen] \u2713 Assets initialized");
        }
        catch (Exception exception) {
            System.out.println("[GameControlsScreen] \u2717 Asset initialization error: " + exception.getMessage());
        }
    }

    private void loadReferenceImages() {
        File file;
        try {
            file = new File(KEYBOARD_IMAGE_PATH);
            if (file.exists()) {
                this.keyboardImage = ImageIO.read(file);
                System.out.println("[GameControlsScreen] \u2713 Loaded keyboard image");
            } else {
                System.out.println("[GameControlsScreen] \u26a0 Keyboard image not found: Resources/KeyBoard_Keys/185311.png");
            }
        }
        catch (Exception exception) {
            System.out.println("[GameControlsScreen] \u2717 Failed to load keyboard image: " + exception.getMessage());
        }
        try {
            file = new File(MOUSE_IMAGE_PATH);
            if (file.exists()) {
                this.mouseImage = ImageIO.read(file);
                System.out.println("[GameControlsScreen] \u2713 Loaded mouse image");
            } else {
                System.out.println("[GameControlsScreen] \u26a0 Mouse image not found: Resources/Mouse_keys/185346.png");
            }
        }
        catch (Exception exception) {
            System.out.println("[GameControlsScreen] \u2717 Failed to load mouse image: " + exception.getMessage());
        }
    }

    private void createBackButton() {
        try {
            int n = this.screenWidth / 2 - 100;
            int n2 = this.screenHeight - 70;
            String string = "Resources/industrial-zone/gui/6 Buttons/Variant_07 Button.png";
            AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader("Button_Back", string, 200, 60, 4);
            this.backButton = new InteractiveButton("Back", horizontalSpritesheetLoader, n, n2, 200, 60);
            MidiTuner midiTuner = new MidiTuner("click_back.wav", 1);
            this.backButton.setAudio(midiTuner);
            this.backButton.setSparkEffect(this.sparkSystem, 6);
            this.backButton.setOnClick(() -> this.onBackButtonClicked());
            System.out.println("[GameControlsScreen] \u2713 Created back button");
        }
        catch (Exception exception) {
            System.out.println("[GameControlsScreen] \u2717 Failed to create back button: " + exception.getMessage());
        }
    }

    private void startBackgroundMusic() {
        if (this.backgroundMusic != null) {
            this.backgroundMusic.setVolume(0.5f);
            this.backgroundMusic.play();
            System.out.println("[GameControlsScreen] \u25b6 Background music started");
        }
    }

    @Override
    public void update(float f) {
        if (this.backButton != null) {
            this.backButton.update();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public BufferedImage render(int n, int n2) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 1);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            graphics2D.setColor(new Color(30, 30, 40));
            graphics2D.fillRect(0, 0, n, n2);
            this.drawTitle(graphics2D, n);
            this.drawPanel(graphics2D, "KEYBOARD CONTROLS", this.leftPanelX, this.panelY, this.panelWidth, this.panelHeight, this.keyboardImage);
            this.drawPanel(graphics2D, "MOUSE CONTROLS", this.rightPanelX, this.panelY, this.panelWidth, this.panelHeight, this.mouseImage);
            if (this.backButton != null) {
                this.backButton.render(graphics2D);
            }
            if (this.sparkSystem != null) {
                for (SparkEffectSystem.ActiveSparkEffect activeSparkEffect : this.sparkSystem.getActiveEffects()) {
                    try {
                        BufferedImage bufferedImage2 = activeSparkEffect.getLoader().getFrame(activeSparkEffect.getCurrentFrame());
                        if (bufferedImage2 == null) continue;
                        graphics2D.drawImage(bufferedImage2, activeSparkEffect.getScreenX() - 32, activeSparkEffect.getScreenY() - 32, 64, 64, null);
                    }
                    catch (Exception exception) {}
                }
            }
        }
        finally {
            graphics2D.dispose();
        }
        return bufferedImage;
    }

    private void drawTitle(Graphics2D graphics2D, int n) {
        graphics2D.setFont(new Font("Arial", 1, 48));
        String string = "GAME CONTROLS";
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n2 = (n - fontMetrics.stringWidth(string)) / 2;
        int n3 = 50;
        graphics2D.setColor(new Color(0, 0, 0, 180));
        graphics2D.drawString(string, n2 + 2, n3 + 2);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(string, n2, n3);
    }

    private void drawPanel(Graphics2D graphics2D, String string, int n, int n2, int n3, int n4, BufferedImage bufferedImage) {
        graphics2D.setColor(new Color(100, 100, 120));
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRect(n, n2, n3, n4);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 18));
        graphics2D.drawString(string, n + 15, n2 + 30);
        if (bufferedImage != null) {
            int n5 = Math.min(this.imageDisplayWidth, n3 - 30);
            int n6 = Math.min(this.imageDisplayHeight, n4 - 80);
            int n7 = n5;
            int n8 = bufferedImage.getHeight() * n7 / bufferedImage.getWidth();
            if (n8 > n6) {
                n8 = n6;
                n7 = bufferedImage.getWidth() * n8 / bufferedImage.getHeight();
            }
            int n9 = n + (n3 - n7) / 2;
            int n10 = n2 + 50 + (n4 - 80 - n8) / 2;
            graphics2D.drawImage(bufferedImage, n9, n10, n7, n8, null);
        } else {
            graphics2D.setColor(Color.GRAY);
            graphics2D.setFont(new Font("Arial", 0, 14));
            String string2 = "Image not found";
            FontMetrics fontMetrics = graphics2D.getFontMetrics();
            int n11 = n + (n3 - fontMetrics.stringWidth(string2)) / 2;
            int n12 = n2 + n4 / 2;
            graphics2D.drawString(string2, n11, n12);
        }
    }

    @Override
    public void handleMouseMoved(MouseEvent mouseEvent) {
        super.handleMouseMoved(mouseEvent);
        if (this.backButton != null) {
            this.backButton.handleMouseMove(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    @Override
    public void handleMouseClicked(MouseEvent mouseEvent) {
        super.handleMouseClicked(mouseEvent);
        if (this.backButton != null) {
            this.backButton.handleMouseClick(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    @Override
    public void handleKeyPressed(KeyEvent keyEvent) {
        super.handleKeyPressed(keyEvent);
        if (keyEvent.getKeyCode() == 27) {
            this.onBackButtonClicked();
        }
    }

    private void onBackButtonClicked() {
        System.out.println("[GameControlsScreen] Back button clicked");
        if (this.backgroundMusic != null) {
            this.backgroundMusic.fadeOut(300);
        }
        if (this.stateListener != null) {
            this.stateListener.onStateTransition(GameState.MAIN_MENU);
        }
    }

    public void close() {
        if (this.backgroundMusic != null) {
            this.backgroundMusic.close();
        }
        if (this.sparkSystem != null) {
            this.sparkSystem.close();
        }
        System.out.println("[GameControlsScreen] Closed");
    }
}
