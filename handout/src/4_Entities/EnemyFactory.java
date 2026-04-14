/*
 * Decompiled with CFR 0.152.
 */
package entities;

import entities.enemies.Enemies;
import game2D.Animation;
import java.util.ArrayList;
import java.util.List;

public static class Enemies.EnemyFactory {
    public static EnemyInstance createEnemy(Enemies.EnemyPhysicsProfile.EnemyType enemyType, float f, float f2) {
        return new EnemyInstance(enemyType, f, f2);
    }

    public static EnemyInstance createEnemyByName(String string, float f, float f2) {
        try {
            Enemies.EnemyPhysicsProfile.EnemyType enemyType = Enemies.EnemyPhysicsProfile.EnemyType.valueOf(string.toUpperCase());
            return Enemies.EnemyFactory.createEnemy(enemyType, f, f2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            System.err.println("[EnemyFactory] Unknown enemy: " + string);
            return Enemies.EnemyFactory.createEnemy(Enemies.EnemyPhysicsProfile.EnemyType.UFO_SAUCER, f, f2);
        }
    }

    public static List<EnemyInstance> createEnemyWave(Enemies.EnemyPhysicsProfile.EnemyType[] enemyTypeArray, float f, float f2, float f3) {
        ArrayList<EnemyInstance> arrayList = new ArrayList<EnemyInstance>();
        for (int i = 0; i < enemyTypeArray.length; ++i) {
            arrayList.add(Enemies.EnemyFactory.createEnemy(enemyTypeArray[i], f + (float)i * f3, f2));
        }
        return arrayList;
    }

    public static List<EnemyInstance> createAllEnemyTypes(float f, float f2) {
        ArrayList<EnemyInstance> arrayList = new ArrayList<EnemyInstance>();
        for (Enemies.EnemyPhysicsProfile.EnemyType enemyType : Enemies.EnemyPhysicsProfile.EnemyType.values()) {
            arrayList.add(Enemies.EnemyFactory.createEnemy(enemyType, f + (float)(arrayList.size() * 150), f2));
        }
        return arrayList;
    }

    public static class EnemyInstance {
        private Enemies.EnemyPhysicsProfile.EnemyType type;
        private String enemyName;
        private Enemies.EnemyPhysicsProfile physicsProfile;
        private Enemies.EnemyAnimationManager animationManager;
        private float x = 0.0f;
        private float y = 0.0f;
        private float velocityX = 0.0f;
        private float velocityY = 0.0f;
        private boolean isGrounded = false;
        private boolean isFalling = false;
        private String currentAnimationName = "idle";
        private long lastAnimationChange = 0L;
        private int health;
        private int maxHealth;
        private int armor;
        private long lastAttackTime = 0L;
        private boolean isActive = true;
        private float targetX = 0.0f;
        private float targetY = 0.0f;
        private int attackRotation = 0;

        public EnemyInstance(Enemies.EnemyPhysicsProfile.EnemyType enemyType, float f, float f2) {
            this.x = f;
            this.y = f2;
            this.type = enemyType;
            this.enemyName = enemyType.displayName;
            this.physicsProfile = Enemies.EnemyPhysicsProfile.createProfile(enemyType);
            this.animationManager = new Enemies.EnemyAnimationManager(enemyType);
            this.animationManager.loadAllAnimations();
            this.health = this.maxHealth = this.physicsProfile.getMaxHealth();
            this.armor = this.physicsProfile.getArmor();
            this.targetX = f;
            this.targetY = f2;
            this.setX(f);
            this.setY(f2);
            System.out.println("[EnemyFactory] Created " + this.enemyName + " at (" + f + ", " + f2 + ") - " + String.valueOf(this.physicsProfile));
        }

        public float getX() {
            return this.x;
        }

        public float getY() {
            return this.y;
        }

        public void setX(float f) {
            this.x = f;
        }

        public void setY(float f) {
            this.y = f;
        }

        public void setAnimation(Object object) {
        }

        public void playAnimation(String string) {
            if (!string.equals(this.currentAnimationName) && this.animationManager.hasAnimation(string)) {
                this.currentAnimationName = string;
                this.lastAnimationChange = System.currentTimeMillis();
                Animation animation = this.animationManager.getAnimation(string);
                if (animation != null) {
                    this.setAnimation(animation);
                }
            }
        }

        public void updateAnimationState() {
            String string = this.determineCurrentAnimation();
            if (!string.equals(this.currentAnimationName)) {
                this.playAnimation(string);
            }
        }

        private String determineCurrentAnimation() {
            long l;
            long l2 = System.currentTimeMillis() - this.lastAttackTime;
            if (l2 < (l = this.physicsProfile.getAttackCooldown())) {
                return this.getAttackAnimation();
            }
            if (Math.abs(this.velocityX) > 0.05f) {
                return "walk";
            }
            return "idle";
        }

        private String getAttackAnimation() {
            String[] stringArray = new String[]{"attack1", "attack2", "attack3", "attack4"};
            String string = stringArray[this.attackRotation % 4];
            if (this.animationManager.hasAnimation(string + "b")) {
                return string + "b";
            }
            return this.animationManager.hasAnimation(string) ? string : "idle";
        }

        public void updatePhysics(long l) {
            if (!this.isGrounded) {
                this.velocityY += this.physicsProfile.getGravity() * (float)l;
                if (this.velocityY > this.physicsProfile.getMaxFallSpeed()) {
                    this.velocityY = this.physicsProfile.getMaxFallSpeed();
                }
                this.isFalling = true;
            } else {
                this.isFalling = false;
            }
            this.velocityX = Math.abs(this.velocityX) > 0.01f ? (this.velocityX *= this.isGrounded ? this.physicsProfile.getFriction() : this.physicsProfile.getAirFriction()) : 0.0f;
            this.setX(this.getX() + this.velocityX * (float)l);
            this.setY(this.getY() + this.velocityY * (float)l);
            this.updateAnimationState();
        }

        public void moveToward(float f, float f2, long l) {
            float f3;
            float f4 = f - this.getX();
            float f5 = f4 > 0.0f ? 1.0f : (f3 = (float)(f4 < 0.0f ? -1 : 0));
            if (f3 != 0.0f) {
                float f6 = f3 * this.physicsProfile.getRunSpeed();
                this.velocityX += (f6 - this.velocityX) * 0.1f;
            }
        }

        public void attackPlayer() {
            long l = System.currentTimeMillis();
            if (l - this.lastAttackTime > this.physicsProfile.getAttackCooldown()) {
                this.lastAttackTime = l;
                ++this.attackRotation;
                this.updateAnimationState();
            }
        }

        public void takeDamage(float f) {
            float f2 = f * (1.0f - (float)this.armor / 100.0f);
            this.health -= (int)f2;
            if (this.health < 0) {
                this.health = 0;
            }
            if (this.animationManager.hasAnimation("hurt")) {
                this.playAnimation("hurt");
            }
        }

        public void die() {
            this.isActive = false;
            if (this.animationManager.hasAnimation("death")) {
                this.playAnimation("death");
            }
        }

        public String getEnemyName() {
            return this.enemyName;
        }

        public Enemies.EnemyPhysicsProfile.EnemyType getType() {
            return this.type;
        }

        public int getHealth() {
            return this.health;
        }

        public int getMaxHealth() {
            return this.maxHealth;
        }

        public int getArmor() {
            return this.armor;
        }

        public float getVelocityX() {
            return this.velocityX;
        }

        public float getVelocityY() {
            return this.velocityY;
        }

        public String getCurrentAnimation() {
            return this.currentAnimationName;
        }

        public boolean isAlive() {
            return this.health > 0 && this.isActive;
        }

        public boolean isGrounded() {
            return this.isGrounded;
        }

        public Enemies.EnemyPhysicsProfile getPhysicsProfile() {
            return this.physicsProfile;
        }

        public Enemies.EnemyAnimationManager getAnimationManager() {
            return this.animationManager;
        }

        public float getAttackRange() {
            return this.physicsProfile.getAttackRange();
        }

        public int getAttackDamage() {
            return this.physicsProfile.getAttackDamage();
        }

        public void setVelocityX(float f) {
            this.velocityX = f;
        }

        public void setVelocityY(float f) {
            this.velocityY = f;
        }

        public void setGrounded(boolean bl) {
            this.isGrounded = bl;
        }

        public void setTargetPosition(float f, float f2) {
            this.targetX = f;
            this.targetY = f2;
        }

        public String toString() {
            float f = this.getX();
            float f2 = this.getY();
            return String.format("[%s] HP:%d/%d Armor:%d Vel:(%.2f,%.2f) Pos:(%.0f,%.0f)", this.enemyName, this.health, this.maxHealth, this.armor, Float.valueOf(this.velocityX), Float.valueOf(this.velocityY), Float.valueOf(f), Float.valueOf(f2));
        }
    }
}
