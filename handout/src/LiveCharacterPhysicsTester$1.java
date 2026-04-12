/*
 * Decompiled with CFR 0.152.
 */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class LiveCharacterPhysicsTester.1
extends KeyAdapter {
    LiveCharacterPhysicsTester.1() {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        LiveCharacterPhysicsTester.this.testPanel.handleKeyPress(keyEvent);
    }
}
