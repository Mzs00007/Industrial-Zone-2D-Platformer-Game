/*
 * Decompiled with CFR 0.152.
 */
package animation;

import java.awt.image.BufferedImage;

public class CharacterSelectionAnimationSystem {

    public static class CharacterStatsPanel {
        public String characterName;
        public String characterDescription;
        public int health;
        public int speed;
        public int attack;
        public int defense;
        public String specialAbility;

        public CharacterStatsPanel(String string, String string2) {
            this.characterName = string;
            this.characterDescription = string2;
        }

        public static CharacterStatsPanel getBikerStats() {
            CharacterStatsPanel characterStatsPanel = new CharacterStatsPanel("Biker", "Speed-focused melee fighter");
            characterStatsPanel.health = 80;
            characterStatsPanel.speed = 95;
            characterStatsPanel.attack = 85;
            characterStatsPanel.defense = 60;
            characterStatsPanel.specialAbility = "Dash + Blade Mastery";
            return characterStatsPanel;
        }

        public static CharacterStatsPanel getCyborgStats() {
            CharacterStatsPanel characterStatsPanel = new CharacterStatsPanel("Cyborg", "Tank-style defensive character");
            characterStatsPanel.health = 120;
            characterStatsPanel.speed = 50;
            characterStatsPanel.attack = 70;
            characterStatsPanel.defense = 95;
            characterStatsPanel.specialAbility = "Shield Fortify + Repair";
            return characterStatsPanel;
        }

        public static CharacterStatsPanel getPunkStats() {
            CharacterStatsPanel characterStatsPanel = new CharacterStatsPanel("Punk", "Magic-focused caster character");
            characterStatsPanel.health = 70;
            characterStatsPanel.speed = 80;
            characterStatsPanel.attack = 90;
            characterStatsPanel.defense = 65;
            characterStatsPanel.specialAbility = "Time Warp + Spell Amplify";
            return characterStatsPanel;
        }

        public String getStatsString() {
            return String.format("%s - %s\nHP: %d | SPD: %d | ATK: %d | DEF: %d\nAbility: %s", this.characterName, this.characterDescription, this.health, this.speed, this.attack, this.defense, this.specialAbility);
        }
    }

    public static class CharacterSelectionScreen {
        private CharacterCard[] cards = new CharacterCard[3];
        private int selectedCardIndex = 0;
        private long screenStartTime = 0L;

        public CharacterSelectionScreen() {
            this.createBikerCard();
            this.createCyborgCard();
            this.createPunkCard();
            this.screenStartTime = System.currentTimeMillis();
        }

        private void createBikerCard() {
            this.cards[0] = new CharacterCard("Biker", "Melee Fighter - Speed & Precision", "Fast and agile melee fighter with devastating combo attacks");
            this.cards[0].setFrameDuration(100);
        }

        private void createCyborgCard() {
            this.cards[1] = new CharacterCard("Cyborg", "Tank/Protector - Defense & Durability", "Heavily armored tank with powerful defensive shields");
            this.cards[1].setFrameDuration(120);
        }

        private void createPunkCard() {
            this.cards[2] = new CharacterCard("Punk", "Mage/Trickster - Magic & Evasion", "Magical caster with spell-based attacks and time manipulation");
            this.cards[2].setFrameDuration(110);
        }

        public void updateAllAnimations() {
            long l = System.currentTimeMillis();
            for (CharacterCard characterCard : this.cards) {
                characterCard.updateAnimation(l);
            }
        }

        public void selectCard(int n) {
            if (n >= 0 && n < 3) {
                this.selectedCardIndex = n;
            }
        }

        public String getSelectedCharacter() {
            return this.cards[this.selectedCardIndex].characterName;
        }

        public int getSelectedCardIndex() {
            return this.selectedCardIndex;
        }

        public CharacterCard[] getCards() {
            return this.cards;
        }
    }

    public static class CharacterCard {
        public String characterName;
        public String characterType;
        public String characterDescription;
        public BufferedImage cardBackground;
        private int animationFrameIndex = 0;
        private long lastFrameTime = 0L;
        private int currentFrameDuration = 100;

        public CharacterCard(String string, String string2, String string3) {
            this.characterName = string;
            this.characterType = string2;
            this.characterDescription = string3;
        }

        public boolean updateAnimation(long l) {
            if (this.lastFrameTime == 0L) {
                this.lastFrameTime = l;
                return false;
            }
            long l2 = l - this.lastFrameTime;
            if (l2 >= (long)this.currentFrameDuration) {
                ++this.animationFrameIndex;
                if (this.animationFrameIndex >= 12) {
                    this.animationFrameIndex = 0;
                }
                this.lastFrameTime = l;
                return true;
            }
            return false;
        }

        public int getCurrentFrameIndex() {
            return this.animationFrameIndex;
        }

        public void resetAnimation() {
            this.animationFrameIndex = 0;
            this.lastFrameTime = System.currentTimeMillis();
        }

        public void setFrameDuration(int n) {
            this.currentFrameDuration = n;
        }
    }
}
