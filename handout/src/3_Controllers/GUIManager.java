/*
 * Decompiled with CFR 0.152.
 */
package gui;

import core.GameState;
import core.GameStateManager;
import gui.screens.AssetDrivenScreen;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class GUIManager {
    private int screenWidth;
    private int screenHeight;
    private GameStateManager gameStateManager;
    private Map<GameState, AssetDrivenScreen> screenRegistry;
    private GameState currentState;
    private AssetDrivenScreen currentScreen;

    public GUIManager(int n, int n2) {
        this.screenWidth = n;
        this.screenHeight = n2;
        this.screenRegistry = new HashMap<GameState, AssetDrivenScreen>();
        this.gameStateManager = new GameStateManager();
        System.out.println("[GUIManager] \u2713 Initialized with dimensions " + n + "x" + n2);
    }

    public void registerScreen(GameState gameState, AssetDrivenScreen assetDrivenScreen) {
        this.screenRegistry.put(gameState, assetDrivenScreen);
        System.out.println("[GUIManager] \u2713 Registered screen: " + gameState.name());
    }

    public void initialize(GameState gameState) {
        this.currentState = gameState;
        if (!this.screenRegistry.containsKey((Object)gameState)) {
            System.err.println("[GUIManager] \u2717 No screen registered for state: " + gameState.name());
            return;
        }
        this.currentScreen = this.screenRegistry.get((Object)gameState);
        System.out.println("[GUIManager] \u2713 Initialized - Starting with state: " + gameState.name());
    }

    public void render(Graphics2D graphics2D) {
        if (this.currentScreen != null) {
            BufferedImage bufferedImage = this.currentScreen.render(this.screenWidth, this.screenHeight);
            if (bufferedImage != null) {
                graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
            }
        } else {
            graphics2D.setColor(Color.BLACK);
            graphics2D.fillRect(0, 0, this.screenWidth, this.screenHeight);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("No screen initialized", 100, 100);
        }
    }

    public void update(float f) {
        if (this.currentScreen != null) {
            this.currentScreen.update(f);
        }
    }

    public void switchState(GameState gameState) {
        if (gameState == this.currentState) {
            return;
        }
        if (!this.screenRegistry.containsKey((Object)gameState)) {
            System.err.println("[GUIManager] \u2717 No screen registered for state: " + gameState.name());
            return;
        }
        this.currentState = gameState;
        this.currentScreen = this.screenRegistry.get((Object)gameState);
        System.out.println("[GUIManager] \u2192 Switched to state: " + gameState.name());
    }

    public GameState getCurrentState() {
        return this.currentState;
    }

    public GameStateManager getGameStateManager() {
        return this.gameStateManager;
    }
}
