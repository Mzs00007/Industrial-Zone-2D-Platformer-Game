/*
 * Decompiled with CFR 0.152.
 */
package managers;
public enum GameState {
    INITIALIZING("System startup"),
    SPLASH_SCREEN("Studio splash screen"),
    STUDIO_IDENT("University identification"),
    LOADING("Loading resources"),
    MAIN_MENU("Main menu"),
    CHARACTER_SELECT("Character selection"),
    LEVEL_SELECT("Level selection"),
    SETTINGS("Settings/Options"),
    HOW_TO_PLAY("Tutorial/Controls"),
    CREDITS("Credits screen"),
    STORY_INTRO("Pre-level story briefing"),
    LEVEL_TRANSITION("Level title transition"),
    GET_READY("Countdown before gameplay"),
    PLAYING("Active gameplay"),
    BOSS_INTRO("Boss encounter intro"),
    BOSS_ENCOUNTER("Boss battle special state"),
    DIALOGUE("Dialogue/narrative overlay"),
    PAUSED("Game paused"),
    LEVEL_COMPLETE("Level victory screen"),
    GAME_OVER("Defeat/Game Over"),
    VICTORY("Mission/Game victory"),
    CUTSCENE("Cinematic sequence"),
    TRANSITION("State transition effect"),
    ERROR("Error state"),
    DEBUG_MENU("Developer debug menu"),
    EXITING("Shutdown procedure");

    private final String description;

    private GameState(String string2) {
        this.description = string2;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isGameplay() {
        return this == PLAYING || this == BOSS_ENCOUNTER || this == DIALOGUE;
    }

    public boolean isMenu() {
        return this == MAIN_MENU || this == CHARACTER_SELECT || this == LEVEL_SELECT || this == SETTINGS || this == HOW_TO_PLAY;
    }

    public boolean canPause() {
        return this == PLAYING || this == BOSS_ENCOUNTER;
    }

    public boolean isInteractive() {
        return this != LOADING && this != CUTSCENE && this != TRANSITION;
    }

    public boolean allowsInput() {
        return this != LOADING && this != SPLASH_SCREEN && this != STUDIO_IDENT && this != CUTSCENE;
    }
}
