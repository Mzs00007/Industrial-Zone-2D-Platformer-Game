/*
 * Decompiled with CFR 0.152.
 */
package core;

import core.Core;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public static class Core.GameStateManager {
    private Core.GameState currentState;
    private Core.GameState previousState = null;
    private Stack<Core.GameState> stateHistory;
    private long stateChangeTime;
    private float stateElapsed;
    private List<StateListener> listeners = new ArrayList<StateListener>();

    public Core.GameStateManager() {
        this.currentState = Core.GameState.INITIALIZING;
        this.stateHistory = new Stack();
        this.stateChangeTime = System.currentTimeMillis();
    }

    public boolean transition(Core.GameState gameState) {
        if (gameState == this.currentState) {
            return false;
        }
        this.previousState = this.currentState;
        this.stateHistory.push(this.currentState);
        this.currentState = gameState;
        this.stateChangeTime = System.currentTimeMillis();
        this.stateElapsed = 0.0f;
        return true;
    }

    public boolean revert() {
        if (this.stateHistory.isEmpty()) {
            return false;
        }
        Core.GameState gameState = this.stateHistory.pop();
        return this.transition(gameState);
    }

    public void update(long l) {
        this.stateElapsed += (float)l / 1000.0f;
    }

    public Core.GameState getCurrentState() {
        return this.currentState;
    }

    public Core.GameState getPreviousState() {
        return this.previousState;
    }

    public float getStateElapsed() {
        return this.stateElapsed;
    }

    public static interface StateListener {
        public void onStateChanged(Core.GameState var1, Core.GameState var2);
    }
}
