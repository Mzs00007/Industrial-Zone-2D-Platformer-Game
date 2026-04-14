/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import managers.GameState;

public interface ScreenStateListener {
    public void onStateTransition(GameState var1);

    public void onScreenClosed();
}
