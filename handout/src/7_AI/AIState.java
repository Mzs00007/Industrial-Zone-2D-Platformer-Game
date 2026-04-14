/*
 * Decompiled with CFR 0.152.
 */
package ai;

public static enum AI.AIState {
    IDLE("idle"),
    PATROL("patrol"),
    ALERT("alert"),
    CHASE("chase"),
    ATTACK("attack"),
    FLEE("flee"),
    STUNNED("stunned"),
    DEAD("dead"),
    CUSTOM("custom");

    private final String displayName;

    private AI.AIState(String string2) {
        this.displayName = string2;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public boolean isCombat() {
        return this == CHASE || this == ATTACK;
    }

    public boolean isMovement() {
        return this == PATROL || this == CHASE || this == FLEE;
    }

    public boolean isActive() {
        return this != IDLE && this != DEAD && this != STUNNED;
    }
}
