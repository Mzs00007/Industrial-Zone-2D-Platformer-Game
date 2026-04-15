/*
 * Decompiled with CFR 0.152.
 */
package managers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;
public class EnhancedInputHandler
extends KeyAdapter
implements MouseListener,
MouseMotionListener {
    private Set<Integer> keysPressed = new HashSet<Integer>();
    private Set<Integer> keysJustPressed = new HashSet<Integer>();
    private Set<Integer> keysJustReleased = new HashSet<Integer>();
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean leftMousePressed = false;
    private boolean rightMousePressed = false;
    private boolean leftMouseJustPressed = false;
    private long lastUpdateTime = System.currentTimeMillis();

    public void update(long l) {
        this.keysJustPressed.clear();
        this.keysJustReleased.clear();
        this.leftMouseJustPressed = false;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int n = keyEvent.getKeyCode();
        if (!this.keysPressed.contains(n)) {
            this.keysJustPressed.add(n);
        }
        this.keysPressed.add(n);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int n = keyEvent.getKeyCode();
        this.keysPressed.remove(n);
        this.keysJustReleased.add(n);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == 1) {
            this.leftMouseJustPressed = true;
            this.leftMousePressed = true;
        } else if (mouseEvent.getButton() == 3) {
            this.rightMousePressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == 1) {
            this.leftMousePressed = false;
        } else if (mouseEvent.getButton() == 3) {
            this.rightMousePressed = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        this.mouseX = mouseEvent.getX();
        this.mouseY = mouseEvent.getY();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        this.mouseX = mouseEvent.getX();
        this.mouseY = mouseEvent.getY();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public boolean isKeyPressed(int n) {
        return this.keysPressed.contains(n);
    }

    public boolean isKeyJustPressed(int n) {
        return this.keysJustPressed.contains(n);
    }

    public boolean isKeyJustReleased(int n) {
        return this.keysJustReleased.contains(n);
    }

    public int getMouseX() {
        return this.mouseX;
    }

    public int getMouseY() {
        return this.mouseY;
    }
}
