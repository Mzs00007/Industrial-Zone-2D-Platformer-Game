/*
 * Decompiled with CFR 0.152.
 */
package managers;

public static class Core.MouseHandler {
    public int x;
    public int y;
    public boolean leftHeld;
    public boolean rightHeld;
    public boolean leftJustClicked;
    public boolean rightJustClicked;
    public boolean leftJustPressed;
    public boolean rightJustPressed;
    public boolean leftJustReleased;
    public boolean rightJustReleased;
    public int clickX;
    public int clickY;

    public void moved(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    public void pressed(int n, int n2, int n3) {
        this.x = n;
        this.y = n2;
        if (n3 == 1) {
            this.leftHeld = true;
            this.leftJustPressed = true;
        }
        if (n3 == 3) {
            this.rightHeld = true;
            this.rightJustPressed = true;
        }
    }

    public void released(int n, int n2, int n3) {
        this.x = n;
        this.y = n2;
        if (n3 == 1) {
            this.leftHeld = false;
            this.leftJustReleased = true;
        }
        if (n3 == 3) {
            this.rightHeld = false;
            this.rightJustReleased = true;
        }
    }

    public void clicked(int n, int n2, int n3) {
        this.x = n;
        this.y = n2;
        this.clickX = n;
        this.clickY = n2;
        if (n3 == 1) {
            this.leftJustClicked = true;
        }
        if (n3 == 3) {
            this.rightJustClicked = true;
        }
    }

    public boolean isOver(int n, int n2, int n3, int n4) {
        return this.x >= n && this.x <= n + n3 && this.y >= n2 && this.y <= n2 + n4;
    }

    public boolean clickedIn(int n, int n2, int n3, int n4) {
        return this.leftJustClicked && this.clickX >= n && this.clickX <= n + n3 && this.clickY >= n2 && this.clickY <= n2 + n4;
    }

    public void clearOneShots() {
        this.rightJustClicked = false;
        this.leftJustClicked = false;
        this.rightJustPressed = false;
        this.leftJustPressed = false;
        this.rightJustReleased = false;
        this.leftJustReleased = false;
    }

    public void reset() {
        this.rightHeld = false;
        this.leftHeld = false;
        this.rightJustPressed = false;
        this.leftJustPressed = false;
        this.rightJustReleased = false;
        this.leftJustReleased = false;
        this.clearOneShots();
    }
}
