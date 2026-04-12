/*
 * Decompiled with CFR 0.152.
 */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class LiveCharacterPhysicsTester.TestCharacterPanel.1
extends KeyAdapter {
    LiveCharacterPhysicsTester.TestCharacterPanel.1() {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        TestCharacterPanel.this.pressedKeys.add(keyEvent.getKeyCode());
        TestCharacterPanel.this.handleKeyPress(keyEvent);
        TestCharacterPanel.this.requestFocusInWindow();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        TestCharacterPanel.this.pressedKeys.remove(keyEvent.getKeyCode());
    }
}
