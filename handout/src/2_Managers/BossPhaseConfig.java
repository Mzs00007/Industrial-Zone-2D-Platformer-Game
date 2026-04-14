/*
 * Decompiled with CFR 0.152.
 */
package core;

public static class GameplayEnhancementSystem.BossPhaseConfig {
    public int phase;
    public int hpStart;
    public int hpEnd;
    public int damagePerHit;
    public float attackCooldown;
    public int screenShakeIntensity;
    public boolean slowAttacks;

    public GameplayEnhancementSystem.BossPhaseConfig(int n, int n2, int n3, int n4, float f, int n5, boolean bl) {
        this.phase = n;
        this.hpStart = n2;
        this.hpEnd = n3;
        this.damagePerHit = n4;
        this.attackCooldown = f;
        this.screenShakeIntensity = n5;
        this.slowAttacks = bl;
    }
}
