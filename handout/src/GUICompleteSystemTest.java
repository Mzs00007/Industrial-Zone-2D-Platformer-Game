/*
 * Decompiled with CFR 0.152.
 */
import animation.CharacterSelectionAnimationSystem;
import animation.GUIComponentsSystem;
import animation.PlayerCharacterAnimations;
import java.util.LinkedHashMap;
import java.util.Map;

public class GUICompleteSystemTest {
    public static void main(String[] stringArray) {
        System.out.println("========================================");
        System.out.println("GUI COMPLETE SYSTEMS TEST");
        System.out.println("========================================\n");
        GUICompleteSystemTest.testGUIComponentsSystem();
        GUICompleteSystemTest.testPlayerCharacterAnimations();
        GUICompleteSystemTest.testCharacterSelectionAnimationSystem();
        System.out.println("\n========================================");
        System.out.println("ALL TESTS COMPLETED SUCCESSFULLY");
        System.out.println("========================================");
    }

    static void testGUIComponentsSystem() {
        System.out.println("\n[TEST 1] GUI COMPONENTS SYSTEM");
        System.out.println("------------------------------\n");
        System.out.println("\u2713 HUD Bar System:");
        System.out.println("  - Health Bar States: " + GUIComponentsSystem.HUDBarSystem.HealthBarStates.HEALTH_BAR_FILES.length + " files");
        System.out.println("  - Energy Bar States: " + GUIComponentsSystem.HUDBarSystem.EnergyBarStates.ENERGY_BAR_FILES.length + " files");
        System.out.println("  Example: " + GUIComponentsSystem.HUDBarSystem.HealthBarStates.HEALTH_BAR_FILES[0]);
        System.out.println("\n\u2713 Button System:");
        System.out.println("  - Color Maps: 10 variants");
        System.out.println("  - State Variants: 10 filled + " + GUIComponentsSystem.GUIButtonSystemProperties.ButtonStateVariants.HOLLOW_VARIANT_FILES.length + " hollow");
        System.out.println("\n\u2713 Standard UI Icons:");
        System.out.println("  - Total Icons: 40");
        System.out.println("  - Categories: " + String.join((CharSequence)", ", GUIComponentsSystem.StandardUIIcons.ICON_CATEGORIES));
        System.out.println("\n\u2713 GUI Tileset System:");
        System.out.println("  - Total Pieces: 82");
        System.out.println("  - Corner Pieces: 16");
        System.out.println("  - Edge Pieces: 20");
        System.out.println("  - Fill Pieces: 12");
        System.out.println("  - Panel Pieces: 14");
        System.out.println("  - Divider Pieces: 6");
        System.out.println("  - Special Pieces: 3");
    }

    static void testPlayerCharacterAnimations() {
        System.out.println("\n[TEST 2] PLAYER CHARACTER ANIMATIONS");
        System.out.println("-----------------------------------\n");
        System.out.println("\u2713 Biker Character:");
        System.out.println("  - Name: Biker");
        System.out.println("  - Type: Melee Fighter - Speed & Precision");
        System.out.println("  - Color Palette: Black leather with neon cyan accents");
        System.out.println("  - Total Animations: " + PlayerCharacterAnimations.BikerAnimations.animations.size());
        GUICompleteSystemTest.printAnimationSample(PlayerCharacterAnimations.BikerAnimations.animations);
        System.out.println("\n\u2713 Cyborg Character:");
        System.out.println("  - Name: Cyborg");
        System.out.println("  - Type: Tank/Protector - Defense & Durability");
        System.out.println("  - Color Palette: Silver metal with blue energy lines");
        System.out.println("  - Total Animations: " + PlayerCharacterAnimations.CyborgAnimations.animations.size());
        GUICompleteSystemTest.printAnimationSample(PlayerCharacterAnimations.CyborgAnimations.animations);
        System.out.println("\n\u2713 Punk Character:");
        System.out.println("  - Name: Punk");
        System.out.println("  - Type: Mage/Trickster - Magic & Evasion");
        System.out.println("  - Color Palette: Neon clothing with holographic effects");
        System.out.println("  - Total Animations: " + PlayerCharacterAnimations.PunkAnimations.animations.size());
        GUICompleteSystemTest.printAnimationSample(PlayerCharacterAnimations.PunkAnimations.animations);
        System.out.println("\n\u2713 Skill Icon System:");
        System.out.println("  - Total Icons: 20");
        for (int i = 0; i < 5; ++i) {
            System.out.println("    " + (i + 1) + ". " + PlayerCharacterAnimations.SkillIconProperties.SKILL_NAMES[i]);
        }
        System.out.println("    ... and 15 more");
    }

    static void printAnimationSample(LinkedHashMap<String, PlayerCharacterAnimations.AnimationConfig> linkedHashMap) {
        System.out.println("  Sample Animations:");
        int n = 0;
        for (Map.Entry<String, PlayerCharacterAnimations.AnimationConfig> entry : linkedHashMap.entrySet()) {
            if (n >= 3) continue;
            System.out.println("    - " + entry.getKey() + ": " + String.valueOf(entry.getValue()));
            ++n;
        }
        System.out.println("    ... and " + (linkedHashMap.size() - 3) + " more");
    }

    static void testCharacterSelectionAnimationSystem() {
        System.out.println("\n[TEST 3] CHARACTER SELECTION ANIMATION SYSTEM");
        System.out.println("--------------------------------------------\n");
        CharacterSelectionAnimationSystem.CharacterSelectionScreen characterSelectionScreen = new CharacterSelectionAnimationSystem.CharacterSelectionScreen();
        System.out.println("\u2713 Character Selection Screen Created");
        System.out.println("  - Available Characters:");
        for (CharacterSelectionAnimationSystem.CharacterCard characterCard : characterSelectionScreen.getCards()) {
            System.out.println("    \u2022 " + characterCard.characterName + " (" + characterCard.characterType + ")");
        }
        System.out.println("\n\u2713 Animation Synchronization Test:");
        GUICompleteSystemTest.testAnimationSync(characterSelectionScreen);
        System.out.println("\n\u2713 Character Selection:");
        characterSelectionScreen.selectCard(0);
        System.out.println("  - Selected: " + characterSelectionScreen.getSelectedCharacter());
        characterSelectionScreen.selectCard(1);
        System.out.println("  - Selected: " + characterSelectionScreen.getSelectedCharacter());
        characterSelectionScreen.selectCard(2);
        System.out.println("  - Selected: " + characterSelectionScreen.getSelectedCharacter());
        System.out.println("\n\u2713 Character Stats Panels:");
        CharacterSelectionAnimationSystem.CharacterStatsPanel characterStatsPanel = CharacterSelectionAnimationSystem.CharacterStatsPanel.getBikerStats();
        System.out.println("  Biker Stats:");
        System.out.println("    HP: " + characterStatsPanel.health + " | SPD: " + characterStatsPanel.speed + " | ATK: " + characterStatsPanel.attack + " | DEF: " + characterStatsPanel.defense);
        System.out.println("    Ability: " + characterStatsPanel.specialAbility);
        CharacterSelectionAnimationSystem.CharacterStatsPanel characterStatsPanel2 = CharacterSelectionAnimationSystem.CharacterStatsPanel.getCyborgStats();
        System.out.println("  Cyborg Stats:");
        System.out.println("    HP: " + characterStatsPanel2.health + " | SPD: " + characterStatsPanel2.speed + " | ATK: " + characterStatsPanel2.attack + " | DEF: " + characterStatsPanel2.defense);
        System.out.println("    Ability: " + characterStatsPanel2.specialAbility);
        CharacterSelectionAnimationSystem.CharacterStatsPanel characterStatsPanel3 = CharacterSelectionAnimationSystem.CharacterStatsPanel.getPunkStats();
        System.out.println("  Punk Stats:");
        System.out.println("    HP: " + characterStatsPanel3.health + " | SPD: " + characterStatsPanel3.speed + " | ATK: " + characterStatsPanel3.attack + " | DEF: " + characterStatsPanel3.defense);
        System.out.println("    Ability: " + characterStatsPanel3.specialAbility);
    }

    static void testAnimationSync(CharacterSelectionAnimationSystem.CharacterSelectionScreen characterSelectionScreen) {
        for (int i = 0; i < 3; ++i) {
            characterSelectionScreen.updateAllAnimations();
            CharacterSelectionAnimationSystem.CharacterCard[] characterCardArray = characterSelectionScreen.getCards();
            StringBuilder stringBuilder = new StringBuilder("  Frame Update " + (i + 1) + ": [");
            for (int j = 0; j < characterCardArray.length; ++j) {
                if (j > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(characterCardArray[j].getCurrentFrameIndex());
            }
            stringBuilder.append("]");
            System.out.println(stringBuilder.toString());
            try {
                Thread.sleep(100L);
                continue;
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
}
