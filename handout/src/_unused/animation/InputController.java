/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
public class InputController {
    private AnimationAndSpriteLoader.InputHandler baseInput;
    private AnimationAndSpriteLoader.AnimationState currentState = AnimationAndSpriteLoader.AnimationState.IDLE;
    private AnimationAndSpriteLoader.AnimationState previousState = AnimationAndSpriteLoader.AnimationState.IDLE;
    private boolean isMoving = false;
    private boolean facingRight = true;
    public static final int KEY_W = 87;
    public static final int KEY_K = 75;
    public static final int KEY_L = 76;
    public static final int KEY_H = 72;
    public static final int KEY_T = 84;
    public static final int KEY_Q = 81;
    public static final int KEY_R = 82;
    public static final int KEY_E = 69;
    public static final int KEY_X = 88;
    public static final int KEY_V = 86;
    public static final int KEY_F = 70;
    public static final int KEY_P = 80;
    public static final int KEY_CTRL = 17;
    public static final int MOUSE_LEFT = -1;

    public InputController(AnimationAndSpriteLoader.InputHandler inputHandler) {
        this.baseInput = inputHandler;
    }

    public AnimationAndSpriteLoader.AnimationState updateAndGetState() {
        this.previousState = this.currentState;
        if (this.baseInput.isKeyPressed(16)) {
            if (this.baseInput.isKeyPressed(37)) {
                this.currentState = AnimationAndSpriteLoader.AnimationState.DASH_LEFT;
                this.facingRight = false;
                return this.currentState;
            }
            if (this.baseInput.isKeyPressed(39)) {
                this.currentState = AnimationAndSpriteLoader.AnimationState.DASH_RIGHT;
                this.facingRight = true;
                return this.currentState;
            }
        }
        if (this.baseInput.isKeyPressed(75)) {
            this.currentState = this.baseInput.isKeyPressed(16) ? AnimationAndSpriteLoader.AnimationState.ATTACK_MELEE : (this.baseInput.isKeyPressed(17) ? AnimationAndSpriteLoader.AnimationState.ATTACK_MELEE : AnimationAndSpriteLoader.AnimationState.ATTACK_MELEE);
            return this.currentState;
        }
        if (this.baseInput.isKeyPressed(76)) {
            this.currentState = AnimationAndSpriteLoader.AnimationState.ATTACK_RANGE;
            return this.currentState;
        }
        if (this.baseInput.isKeyPressed(72)) {
            this.currentState = AnimationAndSpriteLoader.AnimationState.HANG;
            return this.currentState;
        }
        if (this.baseInput.isKeyPressed(84)) {
            this.currentState = AnimationAndSpriteLoader.AnimationState.IDLE;
            return this.currentState;
        }
        if (this.baseInput.isKeyPressed(81)) {
            this.currentState = AnimationAndSpriteLoader.AnimationState.IDLE;
            return this.currentState;
        }
        if (this.baseInput.isKeyPressed(87)) {
            this.currentState = AnimationAndSpriteLoader.AnimationState.CLIMB;
            return this.currentState;
        }
        if (this.baseInput.isKeyPressed(88)) {
            this.currentState = AnimationAndSpriteLoader.AnimationState.WALL_SLIDE;
            return this.currentState;
        }
        if (this.baseInput.isKeyPressed(32)) {
            this.currentState = AnimationAndSpriteLoader.AnimationState.JUMP;
            return this.currentState;
        }
        if (this.baseInput.isKeyPressed(37)) {
            this.currentState = AnimationAndSpriteLoader.AnimationState.WALK_LEFT;
            this.facingRight = false;
            this.isMoving = true;
            return this.currentState;
        }
        if (this.baseInput.isKeyPressed(39)) {
            this.currentState = AnimationAndSpriteLoader.AnimationState.WALK_RIGHT;
            this.facingRight = true;
            this.isMoving = true;
            return this.currentState;
        }
        this.currentState = AnimationAndSpriteLoader.AnimationState.IDLE;
        this.isMoving = false;
        return this.currentState;
    }

    public AnimationAndSpriteLoader.AnimationState getCurrentState() {
        return this.currentState;
    }

    public AnimationAndSpriteLoader.AnimationState getPreviousState() {
        return this.previousState;
    }

    public boolean isFacingRight() {
        return this.facingRight;
    }

    public boolean isMoving() {
        return this.isMoving;
    }

    public boolean isInputPressed(AnimationAndSpriteLoader.AnimationState animationState) {
        switch (animationState.ordinal()) {
            case 1: {
                return this.baseInput.isKeyPressed(37);
            }
            case 2: {
                return this.baseInput.isKeyPressed(39);
            }
            case 3: {
                return this.baseInput.isKeyPressed(32);
            }
            case 7: {
                return this.baseInput.isKeyPressed(16) && this.baseInput.isKeyPressed(37);
            }
            case 8: {
                return this.baseInput.isKeyPressed(16) && this.baseInput.isKeyPressed(39);
            }
            case 9: {
                return this.baseInput.isKeyPressed(87);
            }
            case 12: {
                return this.baseInput.isKeyPressed(75);
            }
            case 13: {
                return this.baseInput.isKeyPressed(76);
            }
            case 10: {
                return this.baseInput.isKeyPressed(72);
            }
            case 11: {
                return this.baseInput.isKeyPressed(88);
            }
        }
        return false;
    }

    public AnimationAndSpriteLoader.InputHandler getBaseInput() {
        return this.baseInput;
    }
}
