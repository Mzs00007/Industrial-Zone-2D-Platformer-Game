/*
 * Decompiled with CFR 0.152.
 */
package core_game_entities.enemies;

import core.Core;
import core_game_entities.characters.PlayerBase;
import core_game_entities.enemies.Enemies;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemyAICombat {
    private static final float PLAYER_DETECTION_RANGE = 300.0f;
    private static final float DETECTION_CONE_ANGLE = 180.0f;
    private static final float VISION_CHECK_INTERVAL = 100.0f;
    private static final float BOMB_DROP_PREDICTION_MS = 50.0f;
    private static final float PROJECTILE_SPEED = 200.0f;
    private static final float ATTACK_COOLDOWN_MS = 1000.0f;
    private static final float ALERT_FADE_MS = 3000.0f;
    private Map<String, CombatInstance> combatRegistry = new HashMap<String, CombatInstance>();

    public void registerEnemy(String string) {
        if (!this.combatRegistry.containsKey(string)) {
            this.combatRegistry.put(string, new CombatInstance(string));
        }
    }

    public void unregisterEnemy(String string) {
        this.combatRegistry.remove(string);
    }

    public void updateAllCombat(List<Enemies.EnemyFactory.EnemyInstance> list, PlayerBase playerBase, long l) {
        for (Enemies.EnemyFactory.EnemyInstance enemyInstance : list) {
            if (enemyInstance == null || !enemyInstance.isAlive()) continue;
            String string = enemyInstance.getEnemyName();
            CombatInstance combatInstance = this.combatRegistry.get(string);
            if (combatInstance == null) {
                this.registerEnemy(string);
                combatInstance = this.combatRegistry.get(string);
            }
            this.updateEnemyCombat(enemyInstance, combatInstance, playerBase, l);
        }
    }

    private void updateEnemyCombat(Enemies.EnemyFactory.EnemyInstance enemyInstance, CombatInstance combatInstance, PlayerBase playerBase, long l) {
        if (combatInstance.lastVisionCheckTime <= 0.0f) {
            boolean bl;
            combatInstance.hasLineOfSightToPlayer = bl = this.canDetectPlayer(enemyInstance, playerBase);
            combatInstance.lastVisionCheckTime = 100.0f;
            if (combatInstance.debugDetection) {
                Core.logger.info(String.format("[EnemyAI] Enemy %s: canSee=%b, dist=%.0f", enemyInstance.getEnemyName(), bl, Float.valueOf(this.distance(enemyInstance.getX(), enemyInstance.getY(), playerBase.getX(), playerBase.getY()))));
            }
        }
        combatInstance.lastVisionCheckTime -= (float)l;
        switch (combatInstance.state.ordinal()) {
            case 0: {
                if (!combatInstance.hasLineOfSightToPlayer) break;
                this.enterAlertState(combatInstance, enemyInstance, playerBase);
                break;
            }
            case 1: {
                if (combatInstance.hasLineOfSightToPlayer) {
                    this.updateAlertState(combatInstance, enemyInstance, playerBase, l);
                    break;
                }
                combatInstance.alertCountdown -= (float)l;
                if (!(combatInstance.alertCountdown <= 0.0f)) break;
                this.enterIdleState(combatInstance);
                break;
            }
            case 2: {
                this.updateAttackState(combatInstance, enemyInstance, playerBase, l);
                break;
            }
        }
        if (combatInstance.lastAttackTime > 0.0f) {
            combatInstance.lastAttackTime -= (float)l;
        }
    }

    private void enterIdleState(CombatInstance combatInstance) {
        combatInstance.state = CombatState.IDLE;
        combatInstance.alertCountdown = 0.0f;
        combatInstance.hasLineOfSightToPlayer = false;
    }

    private void enterAlertState(CombatInstance combatInstance, Enemies.EnemyFactory.EnemyInstance enemyInstance, PlayerBase playerBase) {
        combatInstance.state = CombatState.ALERT;
        combatInstance.alertCountdown = 3000.0f;
        if (combatInstance.debugDetection) {
            Core.logger.info(String.format("[EnemyAI] Enemy %s ALERT! Player detected", enemyInstance.getEnemyName()));
        }
    }

    private void updateAlertState(CombatInstance combatInstance, Enemies.EnemyFactory.EnemyInstance enemyInstance, PlayerBase playerBase, long l) {
        combatInstance.alertCountdown = 3000.0f;
        if (combatInstance.lastAttackTime <= 0.0f) {
            this.enterAttackState(combatInstance, enemyInstance, playerBase);
        }
    }

    private void enterAttackState(CombatInstance combatInstance, Enemies.EnemyFactory.EnemyInstance enemyInstance, PlayerBase playerBase) {
        combatInstance.state = CombatState.ATTACKING;
        combatInstance.lastAttackTime = 1000.0f;
        if ("ScoutDrone".equalsIgnoreCase(enemyInstance.getType().displayName)) {
            this.performDroneAttack(combatInstance, enemyInstance, playerBase);
        } else {
            this.performGenericAttack(combatInstance, enemyInstance, playerBase);
        }
    }

    private void updateAttackState(CombatInstance combatInstance, Enemies.EnemyFactory.EnemyInstance enemyInstance, PlayerBase playerBase, long l) {
        if (combatInstance.lastAttackTime <= 0.0f) {
            combatInstance.state = CombatState.ALERT;
        }
    }

    private void performDroneAttack(CombatInstance combatInstance, Enemies.EnemyFactory.EnemyInstance enemyInstance, PlayerBase playerBase) {
        float f = this.predictPlayerX(playerBase, 50.0f);
        float f2 = this.predictPlayerY(playerBase, 50.0f);
        combatInstance.targetX = f;
        combatInstance.targetY = f2;
        combatInstance.bombsDropped = 3;
        if (combatInstance.debugDetection) {
            Core.logger.info(String.format("[EnemyAI] DRONE ATTACK: Drone at (%.0f,%.0f) \u2192 PredictedPlayer (%.0f,%.0f)", Float.valueOf(enemyInstance.getX()), Float.valueOf(enemyInstance.getY()), Float.valueOf(f), Float.valueOf(f2)));
        }
    }

    private void performGenericAttack(CombatInstance combatInstance, Enemies.EnemyFactory.EnemyInstance enemyInstance, PlayerBase playerBase) {
        float f = this.predictPlayerX(playerBase, 100.0f);
        float f2 = this.predictPlayerY(playerBase, 100.0f);
        combatInstance.targetX = f;
        combatInstance.targetY = f2;
        if (combatInstance.debugDetection) {
            Core.logger.info(String.format("[EnemyAI] WEAPON ATTACK: Enemy at (%.0f,%.0f) firing at predicted (%.0f,%.0f)", Float.valueOf(enemyInstance.getX()), Float.valueOf(enemyInstance.getY()), Float.valueOf(f), Float.valueOf(f2)));
        }
    }

    private boolean canDetectPlayer(Enemies.EnemyFactory.EnemyInstance enemyInstance, PlayerBase playerBase) {
        float f = this.distance(enemyInstance.getX(), enemyInstance.getY(), playerBase.getX(), playerBase.getY());
        return !(f > 300.0f);
    }

    private float predictPlayerX(PlayerBase playerBase, float f) {
        return playerBase.getX();
    }

    private float predictPlayerY(PlayerBase playerBase, float f) {
        return playerBase.getY();
    }

    private float distance(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        return (float)Math.sqrt(f5 * f5 + f6 * f6);
    }

    private float getAngleToTarget(float f, float f2, float f3, float f4) {
        float f5 = f4 - f2;
        float f6 = f3 - f;
        float f7 = (float)Math.atan2(-f5, f6);
        float f8 = (float)Math.toDegrees(f7);
        if (f8 < 0.0f) {
            f8 += 360.0f;
        }
        return f8;
    }

    private float angleDifference(float f, float f2) {
        float f3;
        for (f3 = f2 - f; f3 > 180.0f; f3 -= 360.0f) {
        }
        while (f3 < -180.0f) {
            f3 += 360.0f;
        }
        return f3;
    }

    public CombatInstance getCombatData(String string) {
        return this.combatRegistry.get(string);
    }

    public CombatState getEnemyState(String string) {
        CombatInstance combatInstance = this.combatRegistry.get(string);
        return combatInstance != null ? combatInstance.state : CombatState.IDLE;
    }

    public boolean canEnemySeePlayer(String string) {
        CombatInstance combatInstance = this.combatRegistry.get(string);
        return combatInstance != null && combatInstance.hasLineOfSightToPlayer;
    }

    public void setDebugDetection(String string, boolean bl) {
        CombatInstance combatInstance = this.combatRegistry.get(string);
        if (combatInstance != null) {
            combatInstance.debugDetection = bl;
        }
    }

    public static class CombatInstance {
        public String enemyId;
        public CombatState state = CombatState.IDLE;
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

    public static enum CombatState {
        IDLE,
        ALERT,
        ATTACKING,
        FLEEING;

    }
}
