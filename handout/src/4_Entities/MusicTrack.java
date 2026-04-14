/*
 * Decompiled with CFR 0.152.
 */
package core_game_entities.audio;

public static enum AudioEntities.MusicTrack {
    MENU_THEME("menu", "Resources/industrial-zone/audio/music/menu_theme.wav", true),
    LEVEL_1_THEME("level_1", "Resources/industrial-zone/audio/music/level_1_theme.wav", true),
    LEVEL_2_THEME("level_2", "Resources/industrial-zone/audio/music/level_2_theme.wav", true),
    BOSS_BATTLE_THEME("boss", "Resources/industrial-zone/audio/music/boss_battle.wav", true),
    GAME_OVER_THEME("game_over", "Resources/industrial-zone/audio/music/game_over.wav", false);

    public final String id;
    public final String filePath;
    public final boolean isLooping;

    private AudioEntities.MusicTrack(String string2, String string3, boolean bl) {
        this.id = string2;
        this.filePath = string3;
        this.isLooping = bl;
    }
}
