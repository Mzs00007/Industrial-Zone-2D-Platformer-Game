/*
 * Decompiled with CFR 0.152.
 */
package core_game_entities.enemies;

import core_game_entities.enemies.Enemies;

public static enum Enemies.EnemyPhysicsProfile.EnemyType {
    UFO_SAUCER("UfoSaucer", "drones/1/", Enemies.EnemyPhysicsProfile.EnemyCategory.DRONE),
    JET_DRONE("JetDrone", "drones/2/", Enemies.EnemyPhysicsProfile.EnemyCategory.DRONE),
    HOVER_PLATFORM("HoverPlatform", "drones/3/", Enemies.EnemyPhysicsProfile.EnemyCategory.DRONE),
    COMBAT_TANK("CombatTank", "sci-fi-antagonists/1/", Enemies.EnemyPhysicsProfile.EnemyCategory.ANTAGONIST),
    ARMOURED_KNIGHT("ArmouredKnight", "sci-fi-antagonists/2/", Enemies.EnemyPhysicsProfile.EnemyCategory.ANTAGONIST),
    WINGED_WARRIOR("WingedWarrior", "sci-fi-antagonists/3/", Enemies.EnemyPhysicsProfile.EnemyCategory.ANTAGONIST);

    public final String displayName;
    public final String assetPath;
    public final Enemies.EnemyPhysicsProfile.EnemyCategory category;

    private Enemies.EnemyPhysicsProfile.EnemyType(String string2, String string3, Enemies.EnemyPhysicsProfile.EnemyCategory enemyCategory) {
        this.displayName = string2;
        this.assetPath = string3;
        this.category = enemyCategory;
    }
}
