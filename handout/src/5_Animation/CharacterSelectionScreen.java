/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.CharacterSelectionAnimationSystem;

public static class CharacterSelectionAnimationSystem.CharacterSelectionScreen {
    private CharacterSelectionAnimationSystem.CharacterCard[] cards = new CharacterSelectionAnimationSystem.CharacterCard[3];
    private int selectedCardIndex = 0;
    private long screenStartTime = 0L;

    public CharacterSelectionAnimationSystem.CharacterSelectionScreen() {
        this.createBikerCard();
        this.createCyborgCard();
        this.createPunkCard();
        this.screenStartTime = System.currentTimeMillis();
    }

    private void createBikerCard() {
        this.cards[0] = new CharacterSelectionAnimationSystem.CharacterCard("Biker", "Melee Fighter - Speed & Precision", "Fast and agile melee fighter with devastating combo attacks");
        this.cards[0].setFrameDuration(100);
    }

    private void createCyborgCard() {
        this.cards[1] = new CharacterSelectionAnimationSystem.CharacterCard("Cyborg", "Tank/Protector - Defense & Durability", "Heavily armored tank with powerful defensive shields");
        this.cards[1].setFrameDuration(120);
    }

    private void createPunkCard() {
        this.cards[2] = new CharacterSelectionAnimationSystem.CharacterCard("Punk", "Mage/Trickster - Magic & Evasion", "Magical caster with spell-based attacks and time manipulation");
        this.cards[2].setFrameDuration(110);
    }

    public void updateAllAnimations() {
        long l = System.currentTimeMillis();
        for (CharacterSelectionAnimationSystem.CharacterCard characterCard : this.cards) {
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

    public CharacterSelectionAnimationSystem.CharacterCard[] getCards() {
        return this.cards;
    }
}
