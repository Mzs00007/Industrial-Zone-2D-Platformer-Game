/*
 * Decompiled with CFR 0.152.
 */
package entities;

import entities.EnemyAICombat;
public class CombatInstance {
    public String enemyId;
    public EnemyAICombat.CombatState state = EnemyAICombat.CombatState.IDLE;
    public float lastVisionCheckTime = 0.0f;
    public float lastAttackTime = 0.0f;
    public float alertCountdown = 0.0f;
    public boolean hasLineOfSightToPlayer = false;
    public int bombsDropped = 0;
    public float targetX;
    public float targetY = 0.0f;
    public boolean debugDetection = false;

    public CombatInstance(String string) {
        this.enemyId = string;
    }
}
