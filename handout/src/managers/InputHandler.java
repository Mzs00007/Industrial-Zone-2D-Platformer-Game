/*
 * Decompiled with CFR 0.152.
 */
package managers;
public class InputHandler {
    public boolean left;
    public boolean right;
    public boolean up;
    public boolean down;
    public boolean jump;
    public boolean dash;
    public boolean attack1;
    public boolean attack2;
    public boolean attack3;
    public boolean pause;
    public boolean restart;
    public boolean interact;
    public boolean special1;
    public boolean special2;
    public boolean special3;
    public boolean crouch;
    public boolean leftJustPressed;
    public boolean rightJustPressed;
    public boolean upJustPressed;
    public boolean downJustPressed;
    public boolean jumpJustPressed;
    public boolean jumpJustReleased;
    public boolean dashJustPressed;
    public boolean attack1JustPressed;
    public boolean attack2JustPressed;
    public boolean attack3JustPressed;
    public boolean pauseJustPressed;
    public boolean restartJustPressed;
    public boolean interactJustPressed;
    public boolean special1JustPressed;
    public boolean special2JustPressed;
    public boolean special3JustPressed;
    public boolean crouchJustPressed;
    public boolean crouchJustReleased;
    public boolean anyKeyJustPressed;
    public boolean debugJustPressed;

    public void keyPressed(int n) {
        this.anyKeyJustPressed = true;
        switch (n) {
            case 37: 
            case 65: {
                if (!this.left) {
                    this.leftJustPressed = true;
                }
                this.left = true;
                break;
            }
            case 39: 
            case 68: {
                if (!this.right) {
                    this.rightJustPressed = true;
                }
                this.right = true;
                break;
            }
            case 38: 
            case 87: {
                if (!this.up) {
                    this.upJustPressed = true;
                }
                this.up = true;
                break;
            }
            case 40: 
            case 83: {
                if (!this.down) {
                    this.downJustPressed = true;
                }
                this.down = true;
                break;
            }
            case 32: {
                if (!this.jump) {
                    this.jumpJustPressed = true;
                }
                this.jump = true;
                break;
            }
            case 16: {
                if (!this.dash) {
                    this.dashJustPressed = true;
                }
                this.dash = true;
                break;
            }
            case 90: {
                if (!this.attack1) {
                    this.attack1JustPressed = true;
                }
                this.attack1 = true;
                break;
            }
            case 88: {
                if (!this.attack2) {
                    this.attack2JustPressed = true;
                }
                this.attack2 = true;
                break;
            }
            case 67: {
                if (!this.attack3) {
                    this.attack3JustPressed = true;
                }
                this.attack3 = true;
                break;
            }
            case 27: {
                if (!this.pause) {
                    this.pauseJustPressed = true;
                }
                this.pause = true;
                break;
            }
            case 82: {
                if (!this.restart) {
                    this.restartJustPressed = true;
                }
                this.restart = true;
                break;
            }
            case 69: {
                if (!this.interact) {
                    this.interactJustPressed = true;
                }
                this.interact = true;
                break;
            }
            case 49: {
                if (!this.special1) {
                    this.special1JustPressed = true;
                }
                this.special1 = true;
                break;
            }
            case 50: {
                if (!this.special2) {
                    this.special2JustPressed = true;
                }
                this.special2 = true;
                break;
            }
            case 51: {
                if (!this.special3) {
                    this.special3JustPressed = true;
                }
                this.special3 = true;
                break;
            }
            case 17: {
                if (!this.crouch) {
                    this.crouchJustPressed = true;
                }
                this.crouch = true;
                break;
            }
            case 114: {
                this.debugJustPressed = true;
            }
        }
    }

    public void keyReleased(int n) {
        switch (n) {
            case 37: 
            case 65: {
                this.left = false;
                break;
            }
            case 39: 
            case 68: {
                this.right = false;
                break;
            }
            case 38: 
            case 87: {
                this.up = false;
                break;
            }
            case 40: 
            case 83: {
                this.down = false;
                break;
            }
            case 32: {
                this.jumpJustReleased = true;
                this.jump = false;
                break;
            }
            case 16: {
                this.dash = false;
                break;
            }
            case 90: {
                this.attack1 = false;
                break;
            }
            case 88: {
                this.attack2 = false;
                break;
            }
            case 67: {
                this.attack3 = false;
                break;
            }
            case 27: {
                this.pause = false;
                break;
            }
            case 82: {
                this.restart = false;
                break;
            }
            case 69: {
                this.interact = false;
                break;
            }
            case 49: {
                this.special1 = false;
                break;
            }
            case 50: {
                this.special2 = false;
                break;
            }
            case 51: {
                this.special3 = false;
                break;
            }
            case 17: {
                this.crouchJustReleased = true;
                this.crouch = false;
            }
        }
    }

    public void clearOneShots() {
        this.downJustPressed = false;
        this.upJustPressed = false;
        this.rightJustPressed = false;
        this.leftJustPressed = false;
        this.dashJustPressed = false;
        this.jumpJustReleased = false;
        this.jumpJustPressed = false;
        this.attack3JustPressed = false;
        this.attack2JustPressed = false;
        this.attack1JustPressed = false;
        this.interactJustPressed = false;
        this.restartJustPressed = false;
        this.pauseJustPressed = false;
        this.special3JustPressed = false;
        this.special2JustPressed = false;
        this.special1JustPressed = false;
        this.crouchJustReleased = false;
        this.crouchJustPressed = false;
        this.debugJustPressed = false;
        this.anyKeyJustPressed = false;
    }

    public void reset() {
        this.down = false;
        this.up = false;
        this.right = false;
        this.left = false;
        this.dash = false;
        this.jump = false;
        this.attack3 = false;
        this.attack2 = false;
        this.attack1 = false;
        this.special3 = false;
        this.special2 = false;
        this.special1 = false;
        this.crouch = false;
        this.interact = false;
        this.restart = false;
        this.pause = false;
        this.clearOneShots();
    }
}
