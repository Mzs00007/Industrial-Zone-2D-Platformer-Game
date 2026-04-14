/*
 * Decompiled with CFR 0.152.
 */
package gui;

import animation.AnimationAndSpriteLoader;
import gui.GameState;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public abstract class GUIComponent
extends AnimationAndSpriteLoader {
    protected int posX = 0;
    protected int posY = 0;
    protected int width = 100;
    protected int height = 50;
    protected boolean isVisible = true;
    protected boolean isEnabled = true;

    public abstract void loadAssets();

    public abstract void update(long var1, GameState var3);

    public abstract void render(Graphics2D var1);

    protected void drawImageAt(Graphics2D graphics2D, BufferedImage bufferedImage, int n, int n2, int n3, int n4) {
        if (bufferedImage == null) {
            return;
        }
        if (n3 == -1 && n4 == -1) {
            graphics2D.drawImage((Image)bufferedImage, n, n2, null);
        } else if (n3 == -1) {
            graphics2D.drawImage(bufferedImage, n, n2, bufferedImage.getWidth(), n4, null);
        } else if (n4 == -1) {
            graphics2D.drawImage(bufferedImage, n, n2, n3, bufferedImage.getHeight(), null);
        } else {
            graphics2D.drawImage(bufferedImage, n, n2, n3, n4, null);
        }
    }

    protected void drawImageFit(Graphics2D graphics2D, BufferedImage bufferedImage, int n, int n2, int n3, int n4) {
        if (bufferedImage == null) {
            return;
        }
        graphics2D.drawImage(bufferedImage, n, n2, n3, n4, null);
    }

    protected void drawImageTiled(Graphics2D graphics2D, BufferedImage bufferedImage, int n, int n2, int n3, int n4) {
        if (bufferedImage == null) {
            return;
        }
        int n5 = bufferedImage.getWidth();
        int n6 = bufferedImage.getHeight();
        for (int i = n2; i < n2 + n4; i += n6) {
            for (int j = n; j < n + n3; j += n5) {
                graphics2D.drawImage((Image)bufferedImage, j, i, null);
            }
        }
    }

    public void setPosition(int n, int n2) {
        this.posX = n;
        this.posY = n2;
    }

    @Override
    public void setSize(int n, int n2) {
        this.width = n;
        this.height = n2;
    }

    @Override
    public void setVisible(boolean bl) {
        this.isVisible = bl;
    }

    @Override
    public boolean isVisible() {
        return this.isVisible;
    }

    @Override
    public int getX() {
        return this.posX;
    }

    @Override
    public int getY() {
        return this.posY;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
