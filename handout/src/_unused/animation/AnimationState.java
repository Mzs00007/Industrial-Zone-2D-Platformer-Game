/*
 * Decompiled with CFR 0.152.
 */
package animation;
public enum AnimationState {
    IDLE("idle", 1, 0),
    WALK_LEFT("walk_left", 8, 100),
    WALK_RIGHT("walk_right", 8, 100),
    JUMP("jump", 6, 80),
    DOUBLE_JUMP("double_jump", 8, 70),
    FALL("fall", 4, 100),
    LAND("land", 4, 80),
    DASH_LEFT("dash_left", 6, 60),
    DASH_RIGHT("dash_right", 6, 60),
    CLIMB("climb", 6, 120),
    HANG("hang", 2, 150),
    WALL_SLIDE("wall_slide", 4, 100),
    ATTACK_MELEE("attack_melee", 6, 70),
    ATTACK_RANGE("attack_range", 4, 80),
    HURT("hurt", 3, 100),
    DEATH("death", 8, 100),
    ENEMY_IDLE("enemy_idle", 2, 200),
    ENEMY_WALK("enemy_walk", 6, 120),
    ENEMY_CHASE("enemy_chase", 8, 80),
    ENEMY_ATTACK("enemy_attack", 5, 100),
    ENEMY_HURT("enemy_hurt", 3, 80),
    ENEMY_DEATH("enemy_death", 6, 120),
    BOSS_IDLE("boss_idle", 1, 200),
    BOSS_ATTACK_PHASE1("boss_phase1", 8, 90),
    BOSS_ATTACK_PHASE2("boss_phase2", 10, 80),
    BOSS_SPECIAL("boss_special", 12, 70),
    BOSS_WEAK("boss_weak", 4, 100),
    BOSS_DEATH("boss_death", 10, 120),
    TILE_DEFAULT("tile_default", 1, 0),
    TILE_ANIMATED("tile_animated", 4, 150),
    HAZARD_ACTIVE("hazard", 4, 100),
    SPARKLE_BURST("sparkle", 8, 60),
    IMPACT_HIT("impact", 6, 80),
    ENERGY_BEAM("energy", 5, 100),
    EXPLOSION("explosion", 10, 70),
    GUI_IDLE("gui_idle", 1, 0),
    GUI_BUTTON_HOVER("button_hover", 1, 0),
    GUI_BUTTON_PRESS("button_press", 3, 100);

    public final String filename;
    public final int frameCount;
    public final int frameTimingMs;

    private AnimationState(String string2, int n2, int n3) {
        this.filename = string2;
        this.frameCount = n2;
        this.frameTimingMs = n3;
    }
}
