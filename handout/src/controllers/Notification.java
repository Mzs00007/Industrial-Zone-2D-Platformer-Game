/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.Phase11NotificationScreen;
public class Notification {
    public String title;
    public String message;
    public Phase11NotificationScreen.NotificationType type;
    public long createdAt;
    public float slideProgress;

    public Notification(String string, String string2, Phase11NotificationScreen.NotificationType notificationType) {
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
