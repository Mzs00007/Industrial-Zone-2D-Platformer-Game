/*
 * Decompiled with CFR 0.152.
 */
package managers;

public static class BossCombatPhaseManager.AttackProperties {
    public int baseDamage;
    public float damageMultiplier;
    public int range;
    public String soundEffect;

    public BossCombatPhaseManager.AttackProperties(int n, float f, int n2, String string) {
        this.baseDamage = n;
        this.damageMultiplier = f;
        this.range = n2;
        this.soundEffect = string;
    }
}
