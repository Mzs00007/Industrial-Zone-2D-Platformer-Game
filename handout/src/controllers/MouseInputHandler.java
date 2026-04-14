/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import animation.AnimationAndSpriteLoader;
import controllers.ButtonPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputHandler
extends AnimationAndSpriteLoader
implements MouseListener,
MouseMotionListener {
    private ButtonPanel buttonPanel;
    private int mouseX = 0;
    private int mouseY = 0;

    public MouseInputHandler(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        this.mouseX = mouseEvent.getX();
        this.mouseY = mouseEvent.getY();
        if (this.buttonPanel != null) {
            this.buttonPanel.updateMousePosition(this.mouseX, this.mouseY);
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        this.mouseX = mouseEvent.getX();
        this.mouseY = mouseEvent.getY();
        if (this.buttonPanel != null) {
            this.buttonPanel.updateMousePosition(this.mouseX, this.mouseY);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (this.buttonPanel != null) {
            this.buttonPanel.handleMousePress(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (this.buttonPanel != null) {
            this.buttonPanel.handleMouseRelease(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    public int getMouseX() {
        return this.mouseX;
    }

    public int getMouseY() {
        return this.mouseY;
    }
}
