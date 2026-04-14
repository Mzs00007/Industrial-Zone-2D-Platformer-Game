/*
 * Decompiled with CFR 0.152.
 */
package entities;

import entities.enemies.Enemies;

public static class Enemies.EnemyEntities.EnemyDrone_JetDroneVariant {
    private Enemies.EnemyFactory.EnemyInstance instance;
    private float x;
    private float y;

    public Enemies.EnemyEntities.EnemyDrone_JetDroneVariant(float f, float f2) {
        this.x = f;
        this.y = f2;
        this.instance = Enemies.EnemyFactory.createEnemy(Enemies.EnemyPhysicsProfile.EnemyType.JET_DRONE, f, f2);
    }

    public void update(long l) {
        if (this.instance != null) {
            this.instance.updatePhysics(l);
            this.x = this.instance.getX();
            this.y = this.instance.getY();
        }
    }

    public Enemies.EnemyFactory.EnemyInstance getInstance() {
        return this.instance;
    }

    public int getHealth() {
        return this.instance != null ? this.instance.getHealth() : 0;
    }

    public void takeDamage(float f) {
        if (this.instance != null) {
            this.instance.takeDamage(f);
        }
    }

    public boolean isAlive() {
        return this.instance != null && this.instance.isAlive();
    }

    public float getAttackRange() {
        return this.instance != null ? this.instance.getAttackRange() : 0.0f;
    }
}
