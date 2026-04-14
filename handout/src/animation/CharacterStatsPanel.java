/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class CharacterSelectionAnimationSystem.CharacterStatsPanel {
    public String characterName;
    public String characterDescription;
    public int health;
    public int speed;
    public int attack;
    public int defense;
    public String specialAbility;

    public CharacterSelectionAnimationSystem.CharacterStatsPanel(String string, String string2) {
        this.characterName = string;
        this.characterDescription = string2;
    }

    public static CharacterSelectionAnimationSystem.CharacterStatsPanel getBikerStats() {
        CharacterSelectionAnimationSystem.CharacterStatsPanel characterStatsPanel = new CharacterSelectionAnimationSystem.CharacterStatsPanel("Biker", "Speed-focused melee fighter");
        characterStatsPanel.health = 80;
        characterStatsPanel.speed = 95;
        characterStatsPanel.attack = 85;
        characterStatsPanel.defense = 60;
        characterStatsPanel.specialAbility = "Dash + Blade Mastery";
        return characterStatsPanel;
    }

    public static CharacterSelectionAnimationSystem.CharacterStatsPanel getCyborgStats() {
        CharacterSelectionAnimationSystem.CharacterStatsPanel characterStatsPanel = new CharacterSelectionAnimationSystem.CharacterStatsPanel("Cyborg", "Tank-style defensive character");
        characterStatsPanel.health = 120;
        characterStatsPanel.speed = 50;
        characterStatsPanel.attack = 70;
        characterStatsPanel.defense = 95;
        characterStatsPanel.specialAbility = "Shield Fortify + Repair";
        return characterStatsPanel;
    }

    public static CharacterSelectionAnimationSystem.CharacterStatsPanel getPunkStats() {
        CharacterSelectionAnimationSystem.CharacterStatsPanel characterStatsPanel = new CharacterSelectionAnimationSystem.CharacterStatsPanel("Punk", "Magic-focused caster character");
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
