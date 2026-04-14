/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.lang.reflect.Method;

public class TransporterHUD {
    private static final int HUD_MARGIN_X = 20;
    private static final int HUD_MARGIN_Y = 20;
    private static final int PROMPT_X = 512;
    private static final int PROMPT_Y = 660;
    private static final int PROGRESS_BAR_WIDTH = 400;
    private static final int PROGRESS_BAR_HEIGHT = 30;
    private static final int PROGRESS_BAR_Y = 100;
    private static final Color COLOR_PROMPT = new Color(0, 255, 100, 200);
    private static final Color COLOR_PROMPT_TEXT = Color.WHITE;
    private static final Color COLOR_PROGRESS_BG = new Color(50, 50, 50, 150);
    private static final Color COLOR_PROGRESS_FILL = new Color(100, 200, 255, 200);
    private static final Color COLOR_WARNING = new Color(255, 100, 100, 200);
    private static final Color COLOR_TIMER = new Color(255, 200, 0, 200);
    private long promptVisibleUntil = 0L;
    private static final long PROMPT_VISIBILITY_MS = 3000L;

    public void render(Graphics2D graphics2D, Object object, Object object2) {
        if (object == null || object2 == null) {
            return;
        }
        try {
            boolean bl;
            Method method;
            String string;
            Class<?> clazz = Class.forName("core_game_entities.TransporterManager");
            Class<?> clazz2 = Class.forName("gui.TransporterInputHandler");
            if (TransporterHUD.hasMethod(clazz2, object2, "getInteractableTransporterId") && (string = (String)(method = clazz2.getMethod("getInteractableTransporterId", new Class[0])).invoke(object2, new Object[0])) != null) {
                this.renderBoardingPrompt(graphics2D);
            }
            if (TransporterHUD.hasMethod(clazz2, object2, "isPlayerInTransport") && (bl = ((Boolean)(method = clazz2.getMethod("isPlayerInTransport", new Class[0])).invoke(object2, new Object[0])).booleanValue())) {
                this.renderTransitStatus(graphics2D);
                this.renderEjectReminder(graphics2D);
            }
            if (TransporterHUD.hasMethod(clazz, object, "getStatusString")) {
                method = clazz.getMethod("getStatusString", new Class[0]);
                string = (String)method.invoke(object, new Object[0]);
                this.renderDebugStatus(graphics2D, string);
            }
        }
        catch (Exception exception) {
            System.err.println("[TransporterHUD] Rendering error: " + exception.getMessage());
        }
    }

    private void renderBoardingPrompt(Graphics2D graphics2D) {
        if (this.promptVisibleUntil <= System.currentTimeMillis()) {
            this.promptVisibleUntil = System.currentTimeMillis() + 3000L;
        }
        String string = "Press [E] to Board Transport";
        Font font = new Font("Arial", 1, 20);
        graphics2D.setFont(font);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n = fontMetrics.stringWidth(string) + 40;
        int n2 = fontMetrics.getHeight() + 20;
        int n3 = 512 - n / 2;
        int n4 = 660 - n2 / 2;
        long l = System.currentTimeMillis() % 500L;
        float f = 0.5f + 0.3f * (float)l / 500.0f;
        Color color = new Color(0, 255, 100, (int)(f * 150.0f));
        graphics2D.setColor(color);
        graphics2D.fillRoundRect(n3 - 5, n4 - 5, n + 10, n2 + 10, 10, 10);
        graphics2D.setColor(new Color(20, 30, 20, 200));
        graphics2D.fillRoundRect(n3, n4, n, n2, 10, 10);
        graphics2D.setColor(COLOR_PROMPT_TEXT);
        graphics2D.drawString(string, n3 + 20, n4 + fontMetrics.getAscent() + 10);
    }

    private void renderTransitStatus(Graphics2D graphics2D) {
        graphics2D.setColor(COLOR_PROGRESS_BG);
        graphics2D.fillRoundRect(312, 100, 400, 30, 8, 8);
        int n = 260;
        graphics2D.setColor(COLOR_PROGRESS_FILL);
        graphics2D.fillRoundRect(312, 100, n, 30, 8, 8);
        Font font = new Font("Arial", 1, 16);
        graphics2D.setFont(font);
        graphics2D.setColor(COLOR_PROMPT_TEXT);
        graphics2D.drawString("EN ROUTE TO DESTINATION", 402, 90);
    }

    private void renderEjectReminder(Graphics2D graphics2D) {
        String string = "Press [SPACE] to Emergency Eject";
        Font font = new Font("Arial", 2, 14);
        graphics2D.setFont(font);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n = 1024 - fontMetrics.stringWidth(string) - 20;
        int n2 = 740;
        long l = System.currentTimeMillis() % 1000L;
        if (l < 500L) {
            graphics2D.setColor(COLOR_WARNING);
            graphics2D.drawString(string, n, n2);
        }
    }

    private void renderDebugStatus(Graphics2D graphics2D, String string) {
        Font font = new Font("Courier New", 0, 12);
        graphics2D.setFont(font);
        graphics2D.setColor(new Color(100, 200, 100, 180));
        String[] stringArray = string.split("\n");
        for (int i = 0; i < stringArray.length; ++i) {
            graphics2D.drawString(stringArray[i], 20, 20 + i * 20);
        }
    }

    private static boolean hasMethod(Class<?> clazz, Object object, String string) {
        try {
            clazz.getMethod(string, new Class[0]);
            return true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return false;
        }
    }
}
