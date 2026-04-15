/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.Screen;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Phase12QuestTrackerScreen
extends Screen {
    private static final int PANEL_WIDTH = 280;
    private static final int PANEL_HEIGHT = 400;
    private static final int PADDING = 10;
    private static final int QUEST_ITEM_HEIGHT = 60;
    private List<Quest> activeQuests = new ArrayList<Quest>();
    private int selectedQuestIndex = 0;
    private boolean isExpanded = true;
    private float expandProgress = 1.0f;

    public Phase12QuestTrackerScreen() {
        super(null);
        this.initializeQuestsList();
    }

    private void initializeQuestsList() {
        Quest quest = new Quest("Ancient Artifact", "Find the lost artifact in the ruins", "5000 XP", QuestStatus.ACTIVE, new Color(150, 150, 255));
        quest.currentStep = 1;
        quest.totalSteps = 3;
        quest.objectives.add(new Objective("Explore the ruins", 1));
        quest.objectives.add(new Objective("Defeat guardians", 3));
        quest.objectives.add(new Objective("Find the artifact", 1));
        this.activeQuests.add(quest);
        Quest quest2 = new Quest("Merchant's Request", "Deliver items to the merchant", "200 Gold", QuestStatus.IN_PROGRESS, new Color(200, 150, 100));
        quest2.currentStep = 2;
        quest2.totalSteps = 2;
        quest2.objectives.add(new Objective("Collect items", 2));
        Objective objective = new Objective("Deliver to merchant", 1);
        objective.progress = 0;
        quest2.objectives.add(objective);
        this.activeQuests.add(quest2);
        Quest quest3 = new Quest("Learn Combat Basics", "Complete basic combat tutorial", "Tutorial Complete", QuestStatus.IN_PROGRESS, new Color(150, 200, 150));
        quest3.currentStep = 1;
        quest3.totalSteps = 1;
        quest3.objectives.add(new Objective("Practice attacks", 5));
        this.activeQuests.add(quest3);
    }

    @Override
    public void update(float f) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public BufferedImage render(int n, int n2) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 2);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            int n3 = 20;
            int n4 = 20;
            graphics2D.setColor(new Color(15, 15, 30, 220));
            graphics2D.fillRoundRect(n3, n4, 280, 400, 10, 10);
            graphics2D.setColor(new Color(100, 150, 200, 255));
            graphics2D.setStroke(new BasicStroke(2.0f));
            graphics2D.drawRoundRect(n3, n4, 280, 400, 10, 10);
            graphics2D.setColor(new Color(100, 150, 200, 255));
            graphics2D.fillRect(n3, n4, 280, 30);
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(new Font("Arial", 1, 13));
            graphics2D.drawString("QUESTS", n3 + 10, n4 + 22);
            graphics2D.setFont(new Font("Arial", 0, 10));
            graphics2D.setColor(new Color(150, 200, 255));
            graphics2D.drawString("(" + this.activeQuests.size() + ")", n3 + 280 - 35, n4 + 22);
            int n5 = n4 + 40;
            for (int i = 0; i < this.activeQuests.size() && n5 < n4 + 400 - 20; n5 += 65, ++i) {
                Quest quest = this.activeQuests.get(i);
                this.renderQuestItem(graphics2D, quest, n3 + 5, n5, 270, i == this.selectedQuestIndex);
            }
            if (this.selectedQuestIndex >= 0 && this.selectedQuestIndex < this.activeQuests.size()) {
                this.renderQuestDetails(graphics2D, n, n2);
            }
        }
        finally {
            graphics2D.dispose();
        }
        return bufferedImage;
    }

    private void renderQuestItem(Graphics2D graphics2D, Quest quest, int n, int n2, int n3, boolean bl) {
        if (bl) {
            graphics2D.setColor(new Color(70, 100, 140, 180));
        } else {
            graphics2D.setColor(new Color(40, 60, 90, 150));
        }
        graphics2D.fillRoundRect(n, n2, n3, 60, 5, 5);
        Color color = quest.status.color;
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(bl ? 2.0f : 1.0f));
        graphics2D.drawRoundRect(n, n2, n3, 60, 5, 5);
        graphics2D.fillRect(n, n2, 3, 60);
        this.renderStatusIcon(graphics2D, quest.status, n + 5, n2 + 5);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 11));
        graphics2D.drawString(quest.title, n + 25, n2 + 17);
        graphics2D.setColor(new Color(150, 180, 220));
        graphics2D.setFont(new Font("Arial", 0, 9));
        graphics2D.drawString("Step " + quest.currentStep + "/" + quest.totalSteps, n + n3 - 50, n2 + 17);
        int n4 = (int)((float)quest.currentStep / (float)quest.totalSteps * (float)(n3 - 30));
        graphics2D.setColor(new Color(60, 100, 150, 150));
        graphics2D.fillRect(n + 25, n2 + 28, n3 - 30, 8);
        graphics2D.setColor(quest.status.color);
        graphics2D.fillRect(n + 25, n2 + 28, n4, 8);
        graphics2D.setColor(new Color(200, 180, 100));
        graphics2D.setFont(new Font("Arial", 0, 9));
        graphics2D.drawString(quest.reward, n + 25, n2 + 48);
    }

    private void renderQuestDetails(Graphics2D graphics2D, int n, int n2) {
        Quest quest = this.activeQuests.get(this.selectedQuestIndex);
        int n3 = 320;
        int n4 = 20;
        int n5 = n - n3 - 20;
        int n6 = 400;
        graphics2D.setColor(new Color(15, 15, 30, 220));
        graphics2D.fillRoundRect(n3, n4, n5, n6, 10, 10);
        graphics2D.setColor(quest.status.color);
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRoundRect(n3, n4, n5, n6, 10, 10);
        graphics2D.setColor(quest.status.color);
        graphics2D.fillRect(n3, n4, n5, 30);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 14));
        graphics2D.drawString(quest.title, n3 + 10, n4 + 22);
        graphics2D.setColor(new Color(180, 180, 200));
        graphics2D.setFont(new Font("Arial", 0, 11));
        int n7 = n4 + 50;
        graphics2D.drawString(quest.description, n3 + 10, n7);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 11));
        graphics2D.drawString("Objectives:", n3 + 10, n7 += 25);
        n7 += 20;
        for (Objective objective : quest.objectives) {
            graphics2D.setColor(new Color(200, 200, 220));
            graphics2D.setFont(new Font("Arial", 0, 10));
            graphics2D.drawString(objective.description, n3 + 20, n7);
            int n8 = n5 - 40;
            graphics2D.setColor(new Color(60, 100, 150, 150));
            graphics2D.fillRect(n3 + 20, n7 + 8, n8, 8);
            Color color = objective.completed ? new Color(100, 255, 100) : quest.status.color;
            graphics2D.setColor(color);
            int n9 = (int)(objective.getProgress() * (float)n8);
            graphics2D.fillRect(n3 + 20, n7 + 8, n9, 8);
            graphics2D.setColor(new Color(150, 180, 220));
            graphics2D.drawString(objective.progress + "/" + objective.goal, n3 + n5 - 50, n7 + 16);
            n7 += 25;
        }
    }

    private void renderStatusIcon(Graphics2D graphics2D, QuestStatus questStatus, int n, int n2) {
        graphics2D.setColor(questStatus.color);
        switch (questStatus.ordinal()) {
            case 2: {
                graphics2D.fillOval(n, n2, 8, 8);
                graphics2D.drawLine(n + 2, n2 + 4, n + 4, n2 + 6);
                graphics2D.drawLine(n + 4, n2 + 6, n + 7, n2 + 2);
                break;
            }
            case 3: {
                graphics2D.drawLine(n, n2, n + 8, n2 + 8);
                graphics2D.drawLine(n + 8, n2, n, n2 + 8);
                break;
            }
            case 1: {
                graphics2D.drawOval(n, n2, 8, 8);
                break;
            }
            default: {
                graphics2D.fillRect(n, n2, 8, 8);
            }
        }
    }

    public void addQuest(Quest quest) {
        this.activeQuests.add(quest);
    }

    public void updateObjective(int n, int n2, int n3) {
        if (n >= 0 && n < this.activeQuests.size()) {
            Quest quest = this.activeQuests.get(n);
            if (n2 >= 0 && n2 < quest.objectives.size()) {
                Objective objective = quest.objectives.get(n2);
                objective.progress = Math.min(n3, objective.goal);
                if (objective.progress >= objective.goal) {
                    objective.completed = true;
                }
            }
        }
    }

    public void completeQuest(int n) {
        if (n >= 0 && n < this.activeQuests.size()) {
            this.activeQuests.get((int)n).status = QuestStatus.COMPLETE;
        }
    }

    public void selectQuest(int n) {
        if (n >= 0 && n < this.activeQuests.size()) {
            this.selectedQuestIndex = n;
        }
    }

    public List<Quest> getQuests() {
        return new ArrayList<Quest>(this.activeQuests);
    }

    public static class Quest {
        public String title;
        public String description;
        public int currentStep;
        public int totalSteps;
        public String reward;
        public QuestStatus status;
        public Color category;
        public List<Objective> objectives;

        public Quest(String string, String string2, String string3, QuestStatus questStatus, Color color) {
            this.title = string;
            this.description = string2;
            this.reward = string3;
            this.status = questStatus;
            this.category = color;
            this.currentStep = 0;
            this.totalSteps = 1;
            this.objectives = new ArrayList<Objective>();
        }
    }

    public static enum QuestStatus {
        ACTIVE(new Color(100, 200, 255)),
        IN_PROGRESS(new Color(255, 200, 100)),
        COMPLETE(new Color(100, 255, 100)),
        FAILED(new Color(255, 100, 100));

        public Color color;

        private QuestStatus(Color color) {
            this.color = color;
        }
    }

    public static class Objective {
        public String description;
        public int progress;
        public int goal;
        public boolean completed;

        public Objective(String string, int n) {
            this.description = string;
            this.goal = n;
            this.progress = 0;
            this.completed = false;
        }

        public float getProgress() {
            return this.goal > 0 ? (float)this.progress / (float)this.goal : 0.0f;
        }
    }
}
