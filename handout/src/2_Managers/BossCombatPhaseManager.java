/*
 * Decompiled with CFR 0.152.
 */
package core;

public class BossCombatPhaseManager {
    private int currentPhase = 1;
    private int maxHP = 600;
    private int currentHP = this.maxHP;
    private float timeSinceLastAttack = 0.0f;
    private int consecutiveAttacks = 0;
    private float attackCooldown = 3.0f;
    private int screenShakeIntensity = 0;
    private boolean isPhaseTransitioning = false;

    public void update(long l) {
        float f = (float)l / 1000.0f;
        this.timeSinceLastAttack += f;
        int n = this.getCurrentPhase();
        if (n != this.currentPhase) {
            this.transitionToPhase(n);
        }
    }

    private int getCurrentPhase() {
        float f = (float)this.currentHP / (float)this.maxHP;
        if (f >= 0.67f) {
            return 1;
        }
        if (f >= 0.34f) {
            return 2;
        }
        return 3;
    }

    private void transitionToPhase(int n) {
        this.isPhaseTransitioning = true;
        this.currentPhase = n;
        this.consecutiveAttacks = 0;
        switch (n) {
            case 1: {
                this.attackCooldown = 3.0f;
                this.screenShakeIntensity = 200;
                break;
            }
            case 2: {
                this.attackCooldown = 2.0f;
                this.screenShakeIntensity = 210;
                break;
            }
            case 3: {
                this.attackCooldown = 1.5f;
                this.screenShakeIntensity = 220;
            }
        }
    }

    public AttackType getNextAttack() {
        if (this.timeSinceLastAttack < this.attackCooldown) {
            return null;
        }
        switch (this.currentPhase) {
            case 1: {
                return this.getPhase1Attack();
            }
            case 2: {
                return this.getPhase2Attack();
            }
            case 3: {
                return this.getPhase3Attack();
            }
        }
        return AttackType.SLOW_PUNCH;
    }

    private AttackType getPhase1Attack() {
        boolean bl = this.consecutiveAttacks % 3 == 2;
        this.resetAttackCooldown();
        return bl ? AttackType.STOMP : AttackType.SLOW_PUNCH;
    }

    private AttackType getPhase2Attack() {
        int n = this.consecutiveAttacks % 3;
        this.resetAttackCooldown();
        switch (n) {
            case 0: {
                return AttackType.DUAL_PUNCH_COMBO;
            }
            case 1: {
                return AttackType.SPINNING_ATTACK;
            }
            case 2: {
                return AttackType.LASER_BEAM;
            }
        }
        return AttackType.DUAL_PUNCH_COMBO;
    }

    private AttackType getPhase3Attack() {
        if (this.consecutiveAttacks == 0) {
            this.resetAttackCooldown();
            return AttackType.SHOCKWAVE;
        }
        if (this.consecutiveAttacks % 2 == 0) {
            this.resetAttackCooldown();
            return AttackType.RAPID_FIRE;
        }
        if (this.consecutiveAttacks % 5 == 4) {
            this.attackCooldown = 0.8f;
            return AttackType.COMBINED_ASSAULT;
        }
        this.resetAttackCooldown();
        return AttackType.RAPID_FIRE;
    }

    public AttackProperties getAttackProperties(AttackType attackType) {
        switch (attackType.ordinal()) {
            case 0: {
                return new AttackProperties(50, 2.0f, 300, "punch");
            }
            case 1: {
                return new AttackProperties(60, 2.5f, 400, "stomp");
            }
            case 2: {
                return new AttackProperties(65, 2.0f, 350, "combo");
            }
            case 3: {
                return new AttackProperties(70, 3.0f, 500, "spin");
            }
            case 4: {
                return new AttackProperties(55, 1.5f, 600, "laser");
            }
            case 5: {
                return new AttackProperties(80, 1.0f, 300, "rapid");
            }
            case 6: {
                return new AttackProperties(75, 2.0f, 700, "shockwave");
            }
            case 7: {
                return new AttackProperties(90, 2.5f, 400, "combined");
            }
        }
        return new AttackProperties(50, 2.0f, 300, "punch");
    }

    public boolean takeDamage(int n) {
        this.currentHP = Math.max(0, this.currentHP - n);
        if (this.currentHP <= 0) {
            this.currentPhase = 4;
            return false;
        }
        return true;
    }

    public int getBossDamage(AttackType attackType, int n) {
        AttackProperties attackProperties = this.getAttackProperties(attackType);
        float f = 1.0f + (float)this.currentPhase * 0.3f;
        return Math.round((float)n * attackProperties.damageMultiplier * f);
    }

    public int getPhase() {
        return this.currentPhase;
    }

    public int getCurrentHP() {
        return this.currentHP;
    }

    public int getMaxHP() {
        return this.maxHP;
    }

    public float getHPPercentage() {
        return (float)this.currentHP / (float)this.maxHP;
    }

    public boolean isDefeated() {
        return this.currentHP <= 0;
    }

    public int getScreenShakeIntensity() {
        return this.screenShakeIntensity;
    }

    public float getTimeUntilNextAttack() {
        return Math.max(0.0f, this.attackCooldown - this.timeSinceLastAttack);
    }

    public float getAttackCooldown() {
        return this.attackCooldown;
    }

    private void resetAttackCooldown() {
        this.timeSinceLastAttack = 0.0f;
        ++this.consecutiveAttacks;
    }

    public void reset() {
        this.currentHP = this.maxHP;
        this.currentPhase = 1;
        this.timeSinceLastAttack = 0.0f;
        this.consecutiveAttacks = 0;
        this.attackCooldown = 3.0f;
        this.screenShakeIntensity = 200;
        this.isPhaseTransitioning = false;
    }

    public boolean isPhaseTransitioning() {
        return this.isPhaseTransitioning;
    }

    public void clearTransitionFlag() {
        this.isPhaseTransitioning = false;
    }

    public static enum AttackType {
        SLOW_PUNCH,
        STOMP,
        DUAL_PUNCH_COMBO,
        SPINNING_ATTACK,
        LASER_BEAM,
        RAPID_FIRE,
        SHOCKWAVE,
        COMBINED_ASSAULT;

    }

    public static class AttackProperties {
        public int baseDamage;
        public float damageMultiplier;
        public int range;
        public String soundEffect;

        public AttackProperties(int n, float f, int n2, String string) {
            this.baseDamage = n;
            this.damageMultiplier = f;
            this.range = n2;
            this.soundEffect = string;
        }
    }
}
