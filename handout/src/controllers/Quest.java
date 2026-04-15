/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.Phase12QuestTrackerScreen;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
public class Quest {
    public String title;
    public String description;
    public int currentStep;
    public int totalSteps;
    public String reward;
    public Phase12QuestTrackerScreen.QuestStatus status;
    public Color category;
    public List<Phase12QuestTrackerScreen.Objective> objectives;

    public Quest(String string, String string2, String string3, Phase12QuestTrackerScreen.QuestStatus questStatus, Color color) {
        this.title = string;
        this.description = string2;
        this.reward = string3;
        this.status = questStatus;
        this.category = color;
        this.currentStep = 0;
        this.totalSteps = 1;
        this.objectives = new ArrayList<Phase12QuestTrackerScreen.Objective>();
    }
}
