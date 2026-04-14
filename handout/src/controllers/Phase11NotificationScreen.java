/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.screens.Screen;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Phase11NotificationScreen
extends Screen {
    private static final int MAX_VISIBLE = 4;
    private static final int NOTIFICATION_LIFETIME = 4000;
    private static final int SLIDE_ANIMATION_TIME = 300;
    private static final int NOTIFICATION_WIDTH = 350;
    private static final int NOTIFICATION_HEIGHT = 60;
    private static final int NOTIFICATION_SPACING = 10;
    private Deque<Notification> notificationQueue = new LinkedList<Notification>();
    private int screenWidth = 800;
    private int screenHeight = 600;

    public Phase11NotificationScreen() {
        super(null);
    }

    @Override
    public void update(float f) {
        long l = System.currentTimeMillis();
        ArrayList<Notification> arrayList = new ArrayList<Notification>();
        for (Notification notification : this.notificationQueue) {
            if (notification.slideProgress < 1.0f) {
                notification.slideProgress += f * 1000.0f / 300.0f;
                if (notification.slideProgress > 1.0f) {
                    notification.slideProgress = 1.0f;
                }
            }
            if (!notification.isExpired(l)) continue;
            arrayList.add(notification);
        }
        this.notificationQueue.removeAll(arrayList);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public BufferedImage render(int n, int n2) {
        this.screenWidth = n;
        this.screenHeight = n2;
        BufferedImage bufferedImage = new BufferedImage(n, n2, 2);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            int n3 = 20;
            int n4 = 0;
            for (Notification notification : this.notificationQueue) {
                if (n4 >= 4) {
                    break;
                }
                int n5 = (int)((float)n - 350.0f * notification.slideProgress - 20.0f);
                this.renderNotification(graphics2D, notification, n5, n3);
                n3 += 70;
                ++n4;
            }
        }
        finally {
            graphics2D.dispose();
        }
        return bufferedImage;
    }

    private void renderNotification(Graphics2D graphics2D, Notification notification, int n, int n2) {
        long l = System.currentTimeMillis();
        float f = notification.getFadeAlpha(l);
        int n3 = (int)(f * 220.0f);
        Color color = new Color(15, 15, 25, n3);
        Color color2 = new Color(notification.type.color.getRed(), notification.type.color.getGreen(), notification.type.color.getBlue(), n3);
        graphics2D.setColor(color);
        graphics2D.fillRoundRect(n, n2, 350, 60, 8, 8);
        graphics2D.setColor(color2);
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRoundRect(n, n2, 350, 60, 8, 8);
        graphics2D.setColor(notification.type.color);
        graphics2D.fillRect(n, n2, 4, 60);
        this.renderTypeIcon(graphics2D, notification.type, n + 15, n2 + 15, n3);
        graphics2D.setColor(new Color(220, 220, 255, n3));
        graphics2D.setFont(new Font("Arial", 1, 12));
        graphics2D.drawString(notification.title, n + 45, n2 + 25);
        graphics2D.setColor(new Color(180, 180, 200, n3));
        graphics2D.setFont(new Font("Arial", 0, 10));
        Object object = notification.message;
        if (((String)object).length() > 35) {
            object = ((String)object).substring(0, 35) + "...";
        }
        graphics2D.drawString((String)object, n + 45, n2 + 42);
        float f2 = (float)(System.currentTimeMillis() - notification.createdAt) / 4000.0f;
        int n4 = (int)(350.0f * f2);
        graphics2D.setColor(new Color(100, 100, 120, n3 / 2));
        graphics2D.fillRect(n, n2 + 60 - 3, 350, 3);
        graphics2D.setColor(notification.type.color);
        graphics2D.fillRect(n, n2 + 60 - 3, n4, 3);
    }

    private void renderTypeIcon(Graphics2D graphics2D, NotificationType notificationType, int n, int n2, int n3) {
        Color color = new Color(notificationType.color.getRed(), notificationType.color.getGreen(), notificationType.color.getBlue(), n3);
        graphics2D.setColor(color);
        switch (notificationType.ordinal()) {
            case 1: {
                int[] nArray = new int[]{n, n + 3, n + 8, n + 3, n};
                int[] nArray2 = new int[]{n2 + 4, n2 + 7, n2 - 2, n2 + 2, n2 + 4};
                graphics2D.setStroke(new BasicStroke(2.0f));
                graphics2D.drawPolyline(nArray, nArray2, 5);
                break;
            }
            case 3: {
                graphics2D.setStroke(new BasicStroke(2.0f));
                graphics2D.drawLine(n, n2, n + 8, n2 + 8);
                graphics2D.drawLine(n + 8, n2, n, n2 + 8);
                break;
            }
            case 2: {
                graphics2D.fillOval(n + 2, n2 + 6, 4, 4);
                graphics2D.fillRect(n + 3, n2, 2, 5);
                break;
            }
            default: {
                graphics2D.fillOval(n + 3, n2 + 1, 2, 2);
                graphics2D.fillRect(n + 3, n2 + 4, 2, 4);
            }
        }
    }

    public void addNotification(String string, String string2, NotificationType notificationType) {
        Notification notification = new Notification(string, string2, notificationType);
        this.notificationQueue.addFirst(notification);
    }

    public void info(String string, String string2) {
        this.addNotification(string, string2, NotificationType.INFO);
    }

    public void success(String string, String string2) {
        this.addNotification(string, string2, NotificationType.SUCCESS);
    }

    public void warning(String string, String string2) {
        this.addNotification(string, string2, NotificationType.WARNING);
    }

    public void error(String string, String string2) {
        this.addNotification(string, string2, NotificationType.ERROR);
    }

    public void clearAll() {
        this.notificationQueue.clear();
    }

    public int getNotificationCount() {
        return this.notificationQueue.size();
    }

    public static class Notification {
        public String title;
        public String message;
        public NotificationType type;
        public long createdAt;
        public float slideProgress;

        public Notification(String string, String string2, NotificationType notificationType) {
            this.title = string;
            this.message = string2;
            this.type = notificationType;
            this.createdAt = System.currentTimeMillis();
            this.slideProgress = 0.0f;
        }

        public boolean isExpired(long l) {
            return l - this.createdAt > 4000L;
        }

        public float getFadeAlpha(long l) {
            long l2 = l - this.createdAt;
            if (l2 > 3500L) {
                return (float)(4000L - l2) / 500.0f;
            }
            return 1.0f;
        }
    }

    public static enum NotificationType {
        INFO(new Color(100, 150, 255)),
        SUCCESS(new Color(100, 255, 100)),
        WARNING(new Color(255, 200, 50)),
        ERROR(new Color(255, 100, 100));

        public Color color;

        private NotificationType(Color color) {
            this.color = color;
        }
    }
}
