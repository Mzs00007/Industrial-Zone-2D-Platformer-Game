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
import java.util.List;

public class Phase9DialogueScreen
extends Screen {
    private static final int MAX_LINES_VISIBLE = 5;
    private static final int CHAR_WIDTH = 8;
    private static final int LINE_HEIGHT = 20;
    private String currentSpeaker = "";
    private String currentDialogue = "";
    private List<String> dialogueHistory = new ArrayList<String>();
    private List<DialogueChoice> choices = new ArrayList<DialogueChoice>();
    private int selectedChoiceIndex = 0;
    private boolean isDisplaying = false;
    private float typewriterProgress = 0.0f;
    private int visibleCharacters = 0;

    public Phase9DialogueScreen() {
        super(null);
    }

    @Override
    public void update(float f) {
        if (this.isDisplaying && this.typewriterProgress < 1.0f) {
            this.typewriterProgress += f * 5.0f;
            if (this.typewriterProgress > 1.0f) {
                this.typewriterProgress = 1.0f;
            }
            this.visibleCharacters = (int)((float)this.currentDialogue.length() * this.typewriterProgress);
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
            if (this.isDisplaying) {
                this.renderDialogueBox(graphics2D, n, n2);
            }
        }
        finally {
            graphics2D.dispose();
        }
        return bufferedImage;
    }

    private void renderDialogueBox(Graphics2D graphics2D, int n, int n2) {
        int n3 = n - 40;
        int n4 = 220;
        int n5 = 20;
        int n6 = n2 - n4 - 20;
        graphics2D.setColor(new Color(10, 10, 20, 220));
        graphics2D.fillRect(n5, n6, n3, n4);
        graphics2D.setColor(new Color(80, 120, 180, 255));
        graphics2D.setStroke(new BasicStroke(3.0f));
        graphics2D.drawRect(n5, n6, n3, n4);
        graphics2D.setColor(new Color(120, 180, 255, 200));
        graphics2D.fillRect(n5, n6, n3, 3);
        graphics2D.setColor(new Color(120, 180, 255, 255));
        graphics2D.setFont(new Font("Arial", 1, 14));
        graphics2D.drawString(this.currentSpeaker, n5 + 20, n6 + 30);
        graphics2D.setColor(new Color(200, 150, 100, 255));
        graphics2D.fillOval(n5 + n3 - 40, n6 + 15, 15, 15);
        graphics2D.setColor(new Color(255, 200, 150, 200));
        graphics2D.setStroke(new BasicStroke(1.0f));
        graphics2D.drawOval(n5 + n3 - 40, n6 + 15, 15, 15);
        this.renderDialogueText(graphics2D, n5 + 20, n6 + 50, n3 - 40, this.visibleCharacters);
        if (this.typewriterProgress >= 1.0f && !this.choices.isEmpty()) {
            this.renderChoices(graphics2D, n5 + 20, n6 + n4 - 80, n3 - 40);
        }
        if (this.typewriterProgress >= 1.0f && this.isDisplaying) {
            this.renderContinueIndicator(graphics2D, n5 + n3 - 30, n6 + n4 - 20);
        }
    }

    private void renderDialogueText(Graphics2D graphics2D, int n, int n2, int n3, int n4) {
        graphics2D.setColor(new Color(200, 200, 220, 255));
        graphics2D.setFont(new Font("Arial", 0, 13));
        String string = this.currentDialogue.substring(0, Math.min(n4, this.currentDialogue.length()));
        List<String> list = this.wrapText(string, n3);
        for (int i = 0; i < Math.min(list.size(), 5); ++i) {
            graphics2D.drawString(list.get(i), n, n2 + i * 20);
        }
    }

    private List<String> wrapText(String string, int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        StringBuilder stringBuilder = new StringBuilder();
        String[] stringArray = string.split(" ");
        Object var6_6 = null;
        for (String string2 : stringArray) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append(string2);
                continue;
            }
            String string3 = stringBuilder.toString() + " " + string2;
            if (string3.length() * 8 > n) {
                arrayList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder(string2);
                continue;
            }
            stringBuilder.append(" ").append(string2);
        }
        if (stringBuilder.length() > 0) {
            arrayList.add(stringBuilder.toString());
        }
        return arrayList;
    }

    private void renderChoices(Graphics2D graphics2D, int n, int n2, int n3) {
        graphics2D.setFont(new Font("Arial", 0, 12));
        for (int i = 0; i < this.choices.size(); ++i) {
            DialogueChoice dialogueChoice = this.choices.get(i);
            int n4 = n2 + i * 25;
            if (i == this.selectedChoiceIndex) {
                graphics2D.setColor(new Color(100, 150, 200, 180));
                graphics2D.fillRect(n, n4 - 15, n3, 20);
                graphics2D.setColor(new Color(150, 200, 255, 255));
            } else {
                graphics2D.setColor(new Color(50, 80, 120, 150));
                graphics2D.fillRect(n, n4 - 15, n3, 20);
                graphics2D.setColor(new Color(120, 150, 200, 200));
            }
            graphics2D.drawString("> " + dialogueChoice.text, n + 10, n4);
        }
    }

    private void renderContinueIndicator(Graphics2D graphics2D, int n, int n2) {
        float f = (float)Math.abs(Math.sin((double)this.typewriterProgress * Math.PI));
        graphics2D.setColor(new Color(200, 150, 100, (int)(155.0f + f * 100.0f)));
        graphics2D.setFont(new Font("Arial", 1, 16));
        graphics2D.drawString("\u25bc", n, n2);
    }

    public void displayDialogue(String string, String string2) {
        this.currentSpeaker = string;
        this.currentDialogue = string2;
        this.isDisplaying = true;
        this.typewriterProgress = 0.0f;
        this.visibleCharacters = 0;
        this.selectedChoiceIndex = 0;
        this.choices.clear();
        this.dialogueHistory.add(string + ": " + string2);
    }

    public void addChoice(String string, int n) {
        this.choices.add(new DialogueChoice(string, n));
    }

    public DialogueChoice getSelectedChoice() {
        if (this.selectedChoiceIndex >= 0 && this.selectedChoiceIndex < this.choices.size()) {
            return this.choices.get(this.selectedChoiceIndex);
        }
        return null;
    }

    public void selectPrevious() {
        if (this.selectedChoiceIndex > 0) {
            --this.selectedChoiceIndex;
        }
    }

    public void selectNext() {
        if (this.selectedChoiceIndex < this.choices.size() - 1) {
            ++this.selectedChoiceIndex;
        }
    }

    public void skipTypewriter() {
        this.typewriterProgress = 1.0f;
        this.visibleCharacters = this.currentDialogue.length();
    }

    public void closeDialogue() {
        this.isDisplaying = false;
        this.choices.clear();
        this.currentDialogue = "";
        this.currentSpeaker = "";
    }

    public boolean isDialogueActive() {
        return this.isDisplaying;
    }

    public List<String> getHistory() {
        return new ArrayList<String>(this.dialogueHistory);
    }

    public void clearHistory() {
        this.dialogueHistory.clear();
    }

    public static class DialogueChoice {
        public String text;
        public int actionId;

        public DialogueChoice(String string, int n) {
            this.text = string;
            this.actionId = n;
        }
    }
}
