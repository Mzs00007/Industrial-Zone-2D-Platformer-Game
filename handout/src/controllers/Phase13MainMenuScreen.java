/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.Screen;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Phase13MainMenuScreen
extends Screen {
    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTON_SPACING = 20;
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();
    private int selectedMenuIndex = 0;
    private float fadeProgress = 0.0f;
    private boolean isDisplayed = true;
    private float animationTime = 0.0f;

    public Phase13MainMenuScreen() {
        super(null);
        this.initializeMenu();
    }

    private void initializeMenu() {
        this.menuItems.add(new MenuItem("NEW GAME", "Start a new adventure", MenuAction.NEW_GAME));
        this.menuItems.add(new MenuItem("CONTINUE", "Load last saved game", MenuAction.CONTINUE));
        this.menuItems.add(new MenuItem("SETTINGS", "Adjust game options", MenuAction.SETTINGS));
        this.menuItems.add(new MenuItem("CREDITS", "View game credits", MenuAction.CREDITS));
        this.menuItems.add(new MenuItem("EXIT", "Quit to desktop", MenuAction.EXIT));
    }

    @Override
    public void update(float f) {
        if (this.fadeProgress < 1.0f) {
            this.fadeProgress += f * 2.0f;
            if (this.fadeProgress > 1.0f) {
                this.fadeProgress = 1.0f;
            }
        }
        this.animationTime += f;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public BufferedImage render(int n, int n2) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 2);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            this.renderMenuBackground(graphics2D, n, n2);
            int n3 = 80;
            this.renderTitle(graphics2D, n, n3);
            int n4 = 250;
            int n5 = (n - 250) / 2;
            for (int i = 0; i < this.menuItems.size(); ++i) {
                MenuItem menuItem = this.menuItems.get(i);
                int n6 = n4 + i * 70;
                boolean bl = i == this.selectedMenuIndex;
                this.renderMenuItem(graphics2D, menuItem, n5, n6, bl);
            }
            if (this.selectedMenuIndex >= 0 && this.selectedMenuIndex < this.menuItems.size()) {
                MenuItem menuItem = this.menuItems.get(this.selectedMenuIndex);
                this.renderDescription(graphics2D, n, n2 - 100, menuItem);
            }
        }
        finally {
            graphics2D.dispose();
        }
        return bufferedImage;
    }

    private void renderMenuBackground(Graphics2D graphics2D, int n, int n2) {
        GradientPaint gradientPaint = new GradientPaint(0.0f, 0.0f, new Color(20, 20, 40, 220), n, n2, new Color(40, 30, 60, 220));
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRect(0, 0, n, n2);
        graphics2D.setColor(new Color(100, 150, 200, (int)(50.0 + Math.sin(this.animationTime * 3.0f) * 30.0)));
        graphics2D.setStroke(new BasicStroke(2.0f));
        int n3 = (int)((double)n2 * 0.6 + Math.sin(this.animationTime * 2.0f) * 30.0);
        graphics2D.drawLine(0, n3, n, n3);
        for (int i = 0; i < 10; ++i) {
            float f = (float)Math.abs(Math.sin(this.animationTime + (float)i));
            int n4 = 2 + i % 3;
            int n5 = (int)((double)(n * (i + 1) / 11) + Math.sin((double)this.animationTime * 1.5 + (double)i) * 20.0);
            int n6 = (int)((double)n2 * 0.3 + Math.cos(this.animationTime * 2.0f + (float)i) * 40.0);
            graphics2D.setColor(new Color(100, 150, 200, (int)(100.0f * f)));
            graphics2D.fillOval(n5, n6, n4, n4);
        }
    }

    private void renderTitle(Graphics2D graphics2D, int n, int n2) {
        graphics2D.setColor(new Color(200, 220, 255, (int)(this.fadeProgress * 255.0f)));
        graphics2D.setFont(new Font("Arial", 1, 48));
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        String string = "GAME TITLE";
        int n3 = (n - fontMetrics.stringWidth(string)) / 2;
        graphics2D.drawString(string, n3, n2);
        graphics2D.setFont(new Font("Arial", 2, 18));
        graphics2D.setColor(new Color(150, 180, 220, (int)(this.fadeProgress * 200.0f)));
        String string2 = "An Epic Adventure Awaits";
        int n4 = (n - fontMetrics.stringWidth(string2)) / 2;
        graphics2D.drawString(string2, n4, n2 + 50);
    }

    private void renderMenuItem(Graphics2D graphics2D, MenuItem menuItem, int n, int n2, boolean bl) {
        if (bl) {
            graphics2D.setColor(new Color(100, 150, 200, 220));
            graphics2D.setStroke(new BasicStroke(2.0f));
            graphics2D.setColor(new Color(150, 200, 255, 150));
            graphics2D.drawRoundRect(n - 5, n2 - 5, 260, 60, 10, 10);
        }
        graphics2D.setColor(bl ? new Color(100, 150, 200, 200) : new Color(60, 90, 130, 180));
        graphics2D.fillRoundRect(n, n2, 250, 50, 8, 8);
        Color color = bl ? new Color(200, 220, 255, 255) : new Color(100, 150, 200, 200);
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRoundRect(n, n2, 250, 50, 8, 8);
        graphics2D.setColor(bl ? Color.WHITE : new Color(180, 180, 200));
        graphics2D.setFont(new Font("Arial", bl ? 1 : 0, 16));
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n3 = n + (250 - fontMetrics.stringWidth(menuItem.label)) / 2;
        int n4 = n2 + (50 - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
        graphics2D.drawString(menuItem.label, n3, n4);
    }

    private void renderDescription(Graphics2D graphics2D, int n, int n2, MenuItem menuItem) {
        graphics2D.setColor(new Color(150, 180, 220, 200));
        graphics2D.setFont(new Font("Arial", 2, 14));
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n3 = (n - fontMetrics.stringWidth(menuItem.description)) / 2;
        graphics2D.drawString(menuItem.description, n3, n2);
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

    public MenuAction getSelectedAction() {
        if (this.selectedMenuIndex >= 0 && this.selectedMenuIndex < this.menuItems.size()) {
            return this.menuItems.get((int)this.selectedMenuIndex).action;
        }
        return null;
    }

    public void setDisplayed(boolean bl) {
        this.isDisplayed = bl;
    }

    public boolean isDisplayed() {
        return this.isDisplayed;
    }

    public static class MenuItem {
        public String label;
        public String description;
        public MenuAction action;
        public boolean enabled;

        public MenuItem(String string, String string2, MenuAction menuAction) {
            this.label = string;
            this.description = string2;
            this.action = menuAction;
            this.enabled = true;
        }
    }

    public static enum MenuAction {
        NEW_GAME("Start new game"),
        CONTINUE("Continue saved game"),
        SETTINGS("Adjust game settings"),
        CREDITS("View game credits"),
        EXIT("Exit to desktop");

        public String tooltip;

        private MenuAction(String string2) {
            this.tooltip = string2;
        }
    }
}
