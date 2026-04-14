/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import animation.AnimationAndSpriteLoader;
import utilities.MidiTuner;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import vfx.SparkEffectSystem;

public class InteractiveButton {
    private AnimationAndSpriteLoader.HorizontalSpritesheetLoader spritesheet;
    private String buttonName;
    private int x;
    private int y;
    private int width;
    private int height;
    private ButtonState currentState = ButtonState.IDLE;
    private ButtonState previousState = ButtonState.IDLE;
    private AnimationController animController;
    private long stateChangeTime = 0L;
    private static final long STATE_TRANSITION_DURATION = 150L;
    private Runnable onClickAction = null;
    private Runnable onHoverEnter = null;
    private Runnable onHoverExit = null;
    private int sparkType = 6;
    private String clickSoundFile = null;
    private SparkEffectSystem sparkSystem = null;
    private MidiTuner clickSound = null;
    private boolean enabled = true;
    private boolean isMouseOver = false;

    public InteractiveButton(String string, AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader, int n, int n2, int n3, int n4) {
        this.buttonName = string;
        this.spritesheet = horizontalSpritesheetLoader;
        this.x = n;
        this.y = n2;
        this.width = n3;
        this.height = n4;
        this.animController = new AnimationController(horizontalSpritesheetLoader.getFrameCount(), 100L);
        System.out.println("[InteractiveButton] Created: " + string + " at (" + n + "," + n2 + ")");
    }

    public void update() {
        this.animController.update();
        if (this.currentState != this.previousState) {
            this.stateChangeTime = System.currentTimeMillis();
            switch (this.currentState.ordinal()) {
                case 0: {
                    this.animController.setCurrentFrame(0);
                    break;
                }
                case 1: {
                    this.animController.setCurrentFrame(1);
                    break;
                }
                case 2: {
                    this.animController.setCurrentFrame(2);
                    break;
                }
                case 3: {
                    this.animController.setCurrentFrame(3);
                }
            }
            this.previousState = this.currentState;
        }
    }

    public void render(Graphics2D graphics2D) {
        if (this.spritesheet == null) {
            this.renderFallback(graphics2D);
            return;
        }
        try {
            BufferedImage bufferedImage = this.spritesheet.getFrame(this.animController.getCurrentFrame());
            if (bufferedImage != null) {
                graphics2D.drawImage(bufferedImage, this.x, this.y, this.width, this.height, null);
            } else {
                this.renderFallback(graphics2D);
            }
        }
        catch (Exception exception) {
            System.out.println("[InteractiveButton] Render error for " + this.buttonName + ": " + exception.getMessage());
            this.renderFallback(graphics2D);
        }
    }

    private void renderFallback(Graphics2D graphics2D) {
        switch (this.currentState.ordinal()) {
            case 0: {
                graphics2D.setColor(new Color(100, 100, 100));
                break;
            }
            case 1: {
                graphics2D.setColor(new Color(150, 150, 150));
                break;
            }
            case 2: {
                graphics2D.setColor(new Color(200, 200, 200));
                break;
            }
            case 3: {
                graphics2D.setColor(new Color(50, 50, 50));
            }
        }
        graphics2D.fillRect(this.x, this.y, this.width, this.height);
        graphics2D.setColor(Color.WHITE);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n = this.x + (this.width - fontMetrics.stringWidth(this.buttonName)) / 2;
        int n2 = this.y + (this.height - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
        graphics2D.drawString(this.buttonName, n, n2);
    }

    public void handleMouseMove(int n, int n2) {
        boolean bl = this.isMouseOver;
        this.isMouseOver = this.isPointInBounds(n, n2);
        if (this.isMouseOver && !bl && this.enabled && this.currentState == ButtonState.IDLE) {
            this.setButtonState(ButtonState.HOVER);
            if (this.onHoverEnter != null) {
                this.onHoverEnter.run();
            }
        }
        if (!this.isMouseOver && bl && this.currentState == ButtonState.HOVER) {
            this.setButtonState(ButtonState.IDLE);
            if (this.onHoverExit != null) {
                this.onHoverExit.run();
            }
        }
    }

    public void handleMouseClick(int n, int n2) {
        if (!this.isPointInBounds(n, n2) || !this.enabled) {
            return;
        }
        this.setButtonState(ButtonState.PRESSED);
        if (this.clickSound != null) {
            this.clickSound.play();
        }
        if (this.sparkSystem != null) {
            int n3 = this.x + this.width / 2;
            int n4 = this.y + this.height / 2;
            this.sparkSystem.triggerSpark(this.sparkType, n3, n4);
        }
        if (this.onClickAction != null) {
            Timer timer = new Timer(100, actionEvent -> {
                this.onClickAction.run();
                this.setButtonState(ButtonState.IDLE);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public void setButtonState(ButtonState buttonState) {
        if (!this.enabled && buttonState != ButtonState.DISABLED) {
            return;
        }
        this.currentState = buttonState;
    }

    public ButtonState getButtonState() {
        return this.currentState;
    }

    private boolean isPointInBounds(int n, int n2) {
        return n >= this.x && n <= this.x + this.width && n2 >= this.y && n2 <= this.y + this.height;
    }

    public InteractiveButton setOnClick(Runnable runnable) {
        this.onClickAction = runnable;
        return this;
    }

    public InteractiveButton setOnHoverEnter(Runnable runnable) {
        this.onHoverEnter = runnable;
        return this;
    }

    public InteractiveButton setOnHoverExit(Runnable runnable) {
        this.onHoverExit = runnable;
        return this;
    }

    public InteractiveButton setAudio(MidiTuner midiTuner) {
        this.clickSound = midiTuner;
        return this;
    }

    public InteractiveButton setSparkEffect(SparkEffectSystem sparkEffectSystem, int n) {
        this.sparkSystem = sparkEffectSystem;
        this.sparkType = n;
        return this;
    }

    public void setEnabled(boolean bl) {
        this.enabled = bl;
        if (!bl) {
            this.setButtonState(ButtonState.DISABLED);
        } else {
            this.setButtonState(ButtonState.IDLE);
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getButtonName() {
        return this.buttonName;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Rectangle2D.Float getBounds() {
        return new Rectangle2D.Float(this.x, this.y, this.width, this.height);
    }

    public static enum ButtonState {
        IDLE,
        HOVER,
        PRESSED,
        DISABLED;

    }

    public static class AnimationController {
        private int frameCount;
        private int currentFrame = 0;
        private long frameDuration;
        private long lastFrameTime = 0L;

        public AnimationController(int n, long l) {
            this.frameCount = n;
            this.frameDuration = l;
            this.lastFrameTime = System.currentTimeMillis();
        }

        public void update() {
            this.lastFrameTime = System.currentTimeMillis();
        }

        public void setCurrentFrame(int n) {
            this.currentFrame = Math.max(0, Math.min(n, this.frameCount - 1));
        }

        public int getCurrentFrame() {
            return this.currentFrame;
        }

        public int getFrameCount() {
            return this.frameCount;
        }
    }
}
