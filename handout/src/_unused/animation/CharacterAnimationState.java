/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class CharacterAnimationState {
    public int currentState = 0;
    public String characterId;
    public boolean isArmed;
    public String equippedGun;
    public int aimAngle = 0;
    public boolean isFiring = false;
    public long lastFireTime = 0L;
    public int fireRateMs = 200;

    public CharacterAnimationState(String string) {
        this.characterId = string;
    }

    public void transitionTo(int n) {
        if (n >= 0 && n < STATE_NAMES.length) {
            this.currentState = n;
        }
    }

    public String getCurrentStateName() {
        return STATE_NAMES[this.currentState];
    }

    public boolean canFire() {
        long l = System.currentTimeMillis();
        return l - this.lastFireTime >= (long)this.fireRateMs;
    }

    public void fire() {
        if (this.canFire() && this.isArmed) {
            this.lastFireTime = System.currentTimeMillis();
            this.isFiring = true;
        }
    }
}
