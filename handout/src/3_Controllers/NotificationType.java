/*
 * Decompiled with CFR 0.152.
 */
package gui.screens;

import java.awt.Color;

public static enum Phase11NotificationScreen.NotificationType {
    INFO(new Color(100, 150, 255)),
    SUCCESS(new Color(100, 255, 100)),
    WARNING(new Color(255, 200, 50)),
    ERROR(new Color(255, 100, 100));

    public Color color;

    private Phase11NotificationScreen.NotificationType(Color color) {
        this.color = color;
    }
}
