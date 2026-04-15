/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class CharacterAnimationStateMachine {
    public static final String SYSTEM_TYPE = "character_animation_state";
    public static final int STATE_IDLE = 0;
    public static final int STATE_RUN = 1;
    public static final int STATE_JUMP = 2;
    public static final int STATE_FIRE = 3;
    public static final int STATE_MELEE = 4;
    public static final int STATE_RELOAD = 5;
    public static final int STATE_HIT = 6;
    public static final int STATE_DIE = 7;
    public static final String[] STATE_NAMES = new String[]{"IDLE", "RUN", "JUMP", "FIRE", "MELEE", "RELOAD", "HIT", "DIE"};

    public static CharacterAnimationState createStateFor(String string) {
        return new CharacterAnimationState(string);
    }
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
}
