/*
 * Decompiled with CFR 0.152.
 */
package core_game_entities.audio;

public static enum AudioEntities.SoundEffect {
    WEAPON_FIRE("weapon_fire", "Resources/industrial-zone/audio/sfx/weapon_fire.wav", 0.7f),
    WEAPON_RELOAD("weapon_reload", "Resources/industrial-zone/audio/sfx/weapon_reload.wav", 0.6f),
    WEAPON_EMPTY("weapon_empty", "Resources/industrial-zone/audio/sfx/weapon_empty.wav", 0.5f),
    ENEMY_SPAWN("enemy_spawn", "Resources/industrial-zone/audio/sfx/enemy_spawn.wav", 0.6f),
    ENEMY_DEATH("enemy_death", "Resources/industrial-zone/audio/sfx/enemy_death.wav", 0.7f),
    BOSS_DEFEAT("boss_defeat", "Resources/industrial-zone/audio/sfx/boss_defeat.wav", 0.9f),
    PLAYER_JUMP("player_jump", "Resources/industrial-zone/audio/sfx/player_jump.wav", 0.5f),
    PLAYER_LAND("player_land", "Resources/industrial-zone/audio/sfx/player_land.wav", 0.4f),
    PLAYER_HIT("player_hit", "Resources/industrial-zone/audio/sfx/player_hit.wav", 0.8f),
    PLAYER_DEATH("player_death", "Resources/industrial-zone/audio/sfx/player_death.wav", 1.0f),
    UI_CLICK("ui_click", "Resources/industrial-zone/audio/sfx/ui_click.wav", 0.6f),
    UI_TOGGLE("ui_toggle", "Resources/industrial-zone/audio/sfx/ui_toggle.wav", 0.5f),
    UI_ACCEPT("ui_accept", "Resources/industrial-zone/audio/sfx/ui_accept.wav", 0.7f),
    UI_CANCEL("ui_cancel", "Resources/industrial-zone/audio/sfx/ui_cancel.wav", 0.6f),
    COLLECTIBLE_PICKUP("collectible", "Resources/industrial-zone/audio/sfx/collectible_pickup.wav", 0.7f),
    DOOR_OPEN("door_open", "Resources/industrial-zone/audio/sfx/door_open.wav", 0.6f),
    EXPLOSION("explosion", "Resources/industrial-zone/audio/sfx/explosion.wav", 0.9f);

    public final String id;
    public final String filePath;
    public final float defaultVolume;

    private AudioEntities.SoundEffect(String string2, String string3, float f) {
        this.id = string2;
        this.filePath = string3;
        this.defaultVolume = f;
    }
}
