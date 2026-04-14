/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import java.awt.Color;

public static enum Phase12QuestTrackerScreen.QuestStatus {
    ACTIVE(new Color(100, 200, 255)),
    IN_PROGRESS(new Color(255, 200, 100)),
    COMPLETE(new Color(100, 255, 100)),
    FAILED(new Color(255, 100, 100));

    public Color color;

    private Phase12QuestTrackerScreen.QuestStatus(Color color) {
        this.color = color;
    }
}
