/*
 * Decompiled with CFR 0.152.
 */
package controllers;

public static class Phase12QuestTrackerScreen.Objective {
    public String description;
    public int progress;
    public int goal;
    public boolean completed;

    public Phase12QuestTrackerScreen.Objective(String string, int n) {
        this.description = string;
        this.goal = n;
        this.progress = 0;
        this.completed = false;
    }

    public float getProgress() {
        return this.goal > 0 ? (float)this.progress / (float)this.goal : 0.0f;
    }
}
