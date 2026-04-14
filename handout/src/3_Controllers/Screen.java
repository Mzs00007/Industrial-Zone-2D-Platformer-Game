/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import managers.GameState;
import java.awt.image.BufferedImage;

public abstract class Screen {
    protected GameState state;

    public Screen(GameState gameState) {
        this.state = gameState;
    }

    public abstract BufferedImage render(int var1, int var2);

    public abstract void update(float var1);

    public GameState getState() {
        return this.state;
    }
}
