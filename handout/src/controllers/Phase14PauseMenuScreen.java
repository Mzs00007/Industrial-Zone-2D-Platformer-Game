/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.screens.Screen;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Phase14PauseMenuScreen
extends Screen {
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 45;
    private static final int BUTTON_SPACING = 15;
    private List<PauseMenuItem> menuItems = new ArrayList<PauseMenuItem>();
    private int selectedMenuIndex = 0;
    private boolean isPaused = false;
    private float blurAlpha = 0.0f;
    private float slideProgress = 0.0f;
    private long pauseStartTime = 0L;
    private int elapsedGameTime = 0;

    public Phase14PauseMenuScreen() {
        super(null);
        this.initializePauseMenu();
    }

    private void initializePauseMenu() {
        this.menuItems.add(new PauseMenuItem("RESUME GAME", "ESC", PauseAction.RESUME));
        this.menuItems.add(new PauseMenuItem("SETTINGS", "S", PauseAction.SETTINGS));
        this.menuItems.add(new PauseMenuItem("HELP", "H", PauseAction.HELP));
        this.menuItems.add(new PauseMenuItem("SAVE & EXIT", "Q", PauseAction.SAVE_EXIT));
    }

    @Override
    public void update(float f) {
        if (this.isPaused) {
            if (this.blurAlpha < 0.7f) {
                this.blurAlpha += f * 3.0f;
                if (this.blurAlpha > 0.7f) {
                    this.blurAlpha = 0.7f;
                }
            }
            if (this.slideProgress < 1.0f) {
                this.slideProgress += f * 4.0f;
                if (this.slideProgress > 1.0f) {
                    this.slideProgress = 1.0f;
                }
            }
            long l = System.currentTimeMillis();
            this.elapsedGameTime = (int)((l - this.pauseStartTime) / 1000L);
        } else {
            if (this.blurAlpha > 0.0f) {
                this.blurAlpha -= f * 3.0f;
                if (this.blurAlpha < 0.0f) {
                    this.blurAlpha = 0.0f;
                }
            }
            if (this.slideProgress > 0.0f) {
                this.slideProgress -= f * 4.0f;
                if (this.slideProgress < 0.0f) {
                    this.slideProgress = 0.0f;
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public BufferedImage render(int n, int n2) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 2);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            if (this.isPaused) {
                graphics2D.setColor(new Color(0, 0, 0, (int)(this.blurAlpha * 255.0f)));
                graphics2D.fillRect(0, 0, n, n2);
                int n3 = (n - 200) / 2;
                int n4 = (int)((1.0f - this.slideProgress) * 100.0f);
                this.renderPausePanel(graphics2D, n3 + n4, n2 / 3, n, n2);
            }
        }
        finally {
            graphics2D.dispose();
        }
        return bufferedImage;
    }

    private void renderPausePanel(Graphics2D graphics2D, int n, int n2, int n3, int n4) {
        int n5 = 250;
        graphics2D.setColor(new Color(20, 30, 50, 240));
        graphics2D.fillRoundRect(n, n2, 200, n5, 15, 15);
        graphics2D.setColor(new Color(100, 150, 200, 150));
        graphics2D.setStroke(new BasicStroke(3.0f));
        graphics2D.drawRoundRect(n, n2, 200, n5, 15, 15);
        graphics2D.setColor(new Color(60, 100, 140, 200));
        graphics2D.fillRect(n, n2, 200, 50);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 24));
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        String string = "PAUSED";
        int n6 = n + (200 - fontMetrics.stringWidth(string)) / 2;
        graphics2D.drawString(string, n6, n2 + 35);
        int n7 = n2 + 60;
        for (int i = 0; i < this.menuItems.size(); ++i) {
            PauseMenuItem pauseMenuItem = this.menuItems.get(i);
            boolean bl = i == this.selectedMenuIndex;
            this.renderPauseButton(graphics2D, pauseMenuItem, n + 10, n7, 180, bl);
            n7 += 60;
        }
        this.renderPauseFooter(graphics2D, n, n2 + n5 - 35, 200);
    }

    private void renderPauseButton(Graphics2D graphics2D, PauseMenuItem pauseMenuItem, int n, int n2, int n3, boolean bl) {
        graphics2D.setColor(bl ? new Color(80, 130, 180, 200) : new Color(50, 80, 120, 180));
        graphics2D.fillRoundRect(n, n2, n3, 45, 6, 6);
        Color color = bl ? new Color(150, 200, 255, 255) : new Color(80, 130, 180, 200);
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRoundRect(n, n2, n3, 45, 6, 6);
        graphics2D.setColor(bl ? Color.WHITE : new Color(150, 180, 220));
        graphics2D.setFont(new Font("Arial", bl ? 1 : 0, 13));
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n4 = n + 10;
        int n5 = n2 + (45 - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
        graphics2D.drawString(pauseMenuItem.label, n4, n5);
        graphics2D.setColor(new Color(100, 150, 200, 180));
        graphics2D.setFont(new Font("Arial", 0, 10));
        int n6 = n + n3 - 20;
        graphics2D.drawString("[" + pauseMenuItem.shortcut + "]", n6, n5);
    }

    private void renderPauseFooter(Graphics2D graphics2D, int n, int n2, int n3) {
        graphics2D.setColor(new Color(80, 130, 180, 200));
        graphics2D.drawLine(n + 5, n2, n + n3 - 5, n2);
        graphics2D.setColor(new Color(100, 150, 200, 180));
        graphics2D.setFont(new Font("Arial", 0, 10));
        String string = "Press ESC to resume";
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n4 = n + (n3 - fontMetrics.stringWidth(string)) / 2;
        graphics2D.drawString(string, n4, n2 + 25);
    }

    public void pauseGame() {
        if (!this.isPaused) {
            this.isPaused = true;
            this.pauseStartTime = System.currentTimeMillis();
            this.slideProgress = 0.0f;
        }
    }

    public void resumeGame() {
        if (this.isPaused) {
            this.isPaused = false;
        }
    }

    public void togglePause() {
        if (this.isPaused) {
            this.resumeGame();
        } else {
            this.pauseGame();
        }
    }

    public void selectPrevious() {
        if (this.selectedMenuIndex > 0) {
            --this.selectedMenuIndex;
        }
    }

    public void selectNext() {
        if (this.selectedMenuIndex < this.menuItems.size() - 1) {
            ++this.selectedMenuIndex;
        }
    }

    public PauseAction getSelectedAction() {
        if (this.selectedMenuIndex >= 0 && this.selectedMenuIndex < this.menuItems.size()) {
            return this.menuItems.get((int)this.selectedMenuIndex).action;
        }
        return null;
    }

    public boolean isPaused() {
        return this.isPaused;
    }

    public static class PauseMenuItem {
        public String label;
        public String shortcut;
        public PauseAction action;

        public PauseMenuItem(String string, String string2, PauseAction pauseAction) {
            this.label = string;
            this.shortcut = string2;
            this.action = pauseAction;
        }
    }

    public static enum PauseAction {
        RESUME("ESC"),
        SETTINGS("S"),
        HELP("H"),
        SAVE_EXIT("Q");

        public String shortcut;

        private PauseAction(String string2) {
            this.shortcut = string2;
        }
    }
}
