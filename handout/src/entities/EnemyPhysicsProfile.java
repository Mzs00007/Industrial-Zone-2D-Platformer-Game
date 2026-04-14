/*
 * Decompiled with CFR 0.152.
 */
package entities;

public static class Enemies.EnemyPhysicsProfile {
    private EnemyType type;
    private float walkSpeed = 0.1f;
    private float runSpeed = 0.15f;
    private float acceleration = 0.008f;
    private float friction = 0.85f;
    private float airFriction = 0.98f;
    private float jumpPower = 0.3f;
    private float gravity = 5.0E-4f;
    private float maxFallSpeed = 0.4f;
    private float width = 64.0f;
    private float height = 64.0f;
    private int maxHealth = 50;
    private int armor = 0;
    private int attackDamage = 10;
    private long attackCooldown = 2000L;
    private float attackRange = 120.0f;

    public static Enemies.EnemyPhysicsProfile createProfile(EnemyType enemyType) {
        Enemies.EnemyPhysicsProfile enemyPhysicsProfile = new Enemies.EnemyPhysicsProfile();
        enemyPhysicsProfile.type = enemyType;
        switch (enemyType.ordinal()) {
            case 0: {
                enemyPhysicsProfile.configureUfoSaucer();
                break;
            }
            case 1: {
                enemyPhysicsProfile.configureJetDrone();
                break;
            }
            case 2: {
                enemyPhysicsProfile.configureHoverPlatform();
                break;
            }
            case 3: {
                enemyPhysicsProfile.configureCombatTank();
                break;
            }
            case 4: {
                enemyPhysicsProfile.configureArmouredKnight();
                break;
            }
            case 5: {
                enemyPhysicsProfile.configureWingedWarrior();
            }
        }
        return enemyPhysicsProfile;
    }

    private void configureUfoSaucer() {
        this.walkSpeed = 0.08f;
        this.runSpeed = 0.1f;
        this.gravity = 3.0E-4f;
        this.maxFallSpeed = 0.3f;
        this.maxHealth = 50;
        this.attackDamage = 10;
        this.attackCooldown = 2000L;
    }

    private void configureJetDrone() {
        this.walkSpeed = 0.12f;
        this.runSpeed = 0.14f;
        this.gravity = 2.0E-4f;
        this.maxFallSpeed = 0.25f;
        this.maxHealth = 45;
        this.attackDamage = 15;
        this.attackCooldown = 1500L;
    }

    private void configureHoverPlatform() {
        this.walkSpeed = 0.09f;
        this.runSpeed = 0.11f;
        this.gravity = 2.0E-4f;
        this.maxFallSpeed = 0.2f;
        this.maxHealth = 60;
        this.attackDamage = 13;
        this.attackCooldown = 2500L;
    }

    private void configureCombatTank() {
        this.walkSpeed = 0.07f;
        this.runSpeed = 0.09f;
        this.gravity = 4.0E-4f;
        this.maxFallSpeed = 0.35f;
        this.maxHealth = 85;
        this.armor = 30;
        this.attackDamage = 18;
        this.attackCooldown = 1600L;
        this.attackRange = 150.0f;
    }

    private void configureArmouredKnight() {
        this.walkSpeed = 0.08f;
        this.runSpeed = 0.1f;
        this.gravity = 5.0E-4f;
        this.maxFallSpeed = 0.4f;
        this.maxHealth = 95;
        this.armor = 35;
        this.attackDamage = 20;
        this.attackCooldown = 1400L;
        this.attackRange = 140.0f;
    }

    private void configureWingedWarrior() {
        this.walkSpeed = 0.09f;
        this.runSpeed = 0.12f;
        this.gravity = 2.0E-4f;
        this.maxFallSpeed = 0.25f;
        this.maxHealth = 90;
        this.armor = 25;
        this.attackDamage = 19;
        this.attackCooldown = 1300L;
        this.attackRange = 130.0f;
    }

    public EnemyType getType() {
        return this.type;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    public float getRunSpeed() {
        return this.runSpeed;
    }

    public float getAcceleration() {
        return this.acceleration;
    }

    public float getFriction() {
        return this.friction;
    }

    public float getAirFriction() {
        return this.airFriction;
    }

    public float getJumpPower() {
        return this.jumpPower;
    }

    public float getGravity() {
        return this.gravity;
    }

    public float getMaxFallSpeed() {
        return this.maxFallSpeed;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getArmor() {
        return this.armor;
    }

    public int getAttackDamage() {
        return this.attackDamage;
    }

    public long getAttackCooldown() {
        return this.attackCooldown;
    }

    public float getAttackRange() {
        return this.attackRange;
    }

    public String toString() {
        return String.format("[%s] HP:%d Armor:%d Dmg:%d Speed:%.2f Cooldown:%dms", this.type.displayName, this.maxHealth, this.armor, this.attackDamage, Float.valueOf(this.runSpeed), this.attackCooldown);
    }

    public static enum EnemyType {
        UFO_SAUCER("UfoSaucer", "drones/1/", EnemyCategory.DRONE),
        JET_DRONE("JetDrone", "drones/2/", EnemyCategory.DRONE),
        HOVER_PLATFORM("HoverPlatform", "drones/3/", EnemyCategory.DRONE),
        COMBAT_TANK("CombatTank", "sci-fi-antagonists/1/", EnemyCategory.ANTAGONIST),
        ARMOURED_KNIGHT("ArmouredKnight", "sci-fi-antagonists/2/", EnemyCategory.ANTAGONIST),
        WINGED_WARRIOR("WingedWarrior", "sci-fi-antagonists/3/", EnemyCategory.ANTAGONIST);

        public final String displayName;
        public final String assetPath;
        public final EnemyCategory category;

        private EnemyType(String string2, String string3, EnemyCategory enemyCategory) {
            this.displayName = string2;
            this.assetPath = string3;
            this.category = enemyCategory;
        }
    }

    public static enum EnemyCategory {
        DRONE,
        ANTAGONIST;

    }
}
