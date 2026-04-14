/*
 * Decompiled with CFR 0.152.
 */
package managers;

import managers.Core;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public static class Core.StateTransitionValidator {
    private static final Map<Core.GameState, Set<Core.GameState>> VALID_TRANSITIONS = new HashMap<Core.GameState, Set<Core.GameState>>();
    private static boolean initialized = false;

    private static void initializeTransitions() {
        if (initialized) {
            return;
        }
        Core.StateTransitionValidator.add(Core.GameState.SPLASH_SCREEN, Core.GameState.STUDIO_IDENT);
        Core.StateTransitionValidator.add(Core.GameState.STUDIO_IDENT, Core.GameState.MAIN_MENU);
        Core.StateTransitionValidator.add(Core.GameState.MAIN_MENU, Core.GameState.LEVEL_SELECT);
        Core.StateTransitionValidator.add(Core.GameState.MAIN_MENU, Core.GameState.HOW_TO_PLAY);
        Core.StateTransitionValidator.add(Core.GameState.MAIN_MENU, Core.GameState.CREDITS);
        Core.StateTransitionValidator.add(Core.GameState.HOW_TO_PLAY, Core.GameState.MAIN_MENU);
        Core.StateTransitionValidator.add(Core.GameState.CREDITS, Core.GameState.MAIN_MENU);
        Core.StateTransitionValidator.add(Core.GameState.LEVEL_SELECT, Core.GameState.CHARACTER_SELECT);
        Core.StateTransitionValidator.add(Core.GameState.CHARACTER_SELECT, Core.GameState.STORY_INTRO);
        Core.StateTransitionValidator.add(Core.GameState.STORY_INTRO, Core.GameState.LEVEL_TRANSITION);
        Core.StateTransitionValidator.add(Core.GameState.LEVEL_TRANSITION, Core.GameState.GET_READY);
        Core.StateTransitionValidator.add(Core.GameState.GET_READY, Core.GameState.PLAYING);
        Core.StateTransitionValidator.add(Core.GameState.GET_READY, Core.GameState.BOSS_INTRO);
        Core.StateTransitionValidator.add(Core.GameState.PLAYING, Core.GameState.PAUSED);
        Core.StateTransitionValidator.add(Core.GameState.PAUSED, Core.GameState.PLAYING);
        Core.StateTransitionValidator.add(Core.GameState.PAUSED, Core.GameState.MAIN_MENU);
        Core.StateTransitionValidator.add(Core.GameState.PLAYING, Core.GameState.GAME_OVER);
        Core.StateTransitionValidator.add(Core.GameState.PLAYING, Core.GameState.LEVEL_COMPLETE);
        Core.StateTransitionValidator.add(Core.GameState.LEVEL_COMPLETE, Core.GameState.LEVEL_TRANSITION);
        Core.StateTransitionValidator.add(Core.GameState.LEVEL_COMPLETE, Core.GameState.VICTORY);
        Core.StateTransitionValidator.add(Core.GameState.BOSS_INTRO, Core.GameState.PLAYING);
        Core.StateTransitionValidator.add(Core.GameState.GAME_OVER, Core.GameState.MAIN_MENU);
        Core.StateTransitionValidator.add(Core.GameState.VICTORY, Core.GameState.CREDITS);
        Core.StateTransitionValidator.add(Core.GameState.VICTORY, Core.GameState.MAIN_MENU);
        initialized = true;
    }

    private static void add(Core.GameState gameState2, Core.GameState gameState3) {
        VALID_TRANSITIONS.computeIfAbsent(gameState2, gameState -> new HashSet()).add(gameState3);
    }

    public static boolean isValidTransition(Core.GameState gameState, Core.GameState gameState2) {
        Set<Core.GameState> set = VALID_TRANSITIONS.get((Object)gameState);
        return set != null && set.contains((Object)gameState2);
    }

    static {
        Core.StateTransitionValidator.initializeTransitions();
    }
}
