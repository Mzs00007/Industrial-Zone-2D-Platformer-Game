/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import managers.GameState;
import controllers.Screen;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public abstract class AssetDrivenScreen
extends Screen {
    protected static final int SCREEN_WIDTH = 1280;
    protected static final int SCREEN_HEIGHT = 720;

    public AssetDrivenScreen(GameState gameState) {
        super(gameState);
    }

    @Override
    public final BufferedImage render(int n, int n2) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 1);
        Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        this.renderBitmap(graphics2D, n, n2);
        graphics2D.dispose();
        return bufferedImage;
    }

    public abstract void renderBitmap(Graphics2D var1, int var2, int var3);

    @Override
    public void update(float f) {
    }

    public void handleMouseDragged(MouseEvent mouseEvent) {
    }

    protected BufferedImage loadImage(String string) {
        try {
            File file = new File(string);
            if (!file.exists()) {
                System.err.println("[AssetDrivenScreen] \u2717 Asset not found: " + string);
                return null;
            }
            return ImageIO.read(file);
        }
        catch (Exception exception) {
            System.err.println("[AssetDrivenScreen] \u2717 Failed to load: " + string);
            exception.printStackTrace();
            return null;
        }
    }
}
