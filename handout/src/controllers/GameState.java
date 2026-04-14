/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import animation.AnimationAndSpriteLoader;
import java.util.HashSet;
import java.util.Set;

public class GameState
extends AnimationAndSpriteLoader {
    public int health = 100;
    public int maxHealth = 100;
    public int energy = 80;
    public int maxEnergy = 100;
    public int armor = 50;
    public int maxArmor = 100;
    public int ammo = 3;
    public int ammoMax = 12;
    public int[] inventorySlots = new int[4];
    public boolean[] inventoryActive = new boolean[4];
    public Set<String> activeEffects = new HashSet<String>();
    public String currentStatus = "NORMAL";
    public String currentLevel = "LEVEL_1";
    public String levelName = "INDUSTRIAL ZONE";
    public int currentStage = 1;
    public int totalStages = 3;
    public float stageProgress = 0.33f;
    public int timeRemainingSeconds = 300;
    public int totalElapsedSeconds = 0;
    public int score = 0;
    public int enemiesDefeated = 0;
    public int itemsCollected = 0;
    public boolean level1Completed = false;
    public boolean level2Completed = false;
    public boolean allLevelsCompleted = false;
    public int levelsCompletedCount = 0;
    public long levelStartTime = 0L;
    public long levelElapsedTime = 0L;

    public void takeDamage(int damageAmount) {
        this.health = Math.max(0, this.health - damageAmount);
        if (this.health == 0) {
            System.out.println("[\u2717] PLAYER DEFEATED - Game Over");
        }
    }

    public void heal(int healAmount) {
        this.health = Math.min(this.maxHealth, this.health + healAmount);
    }

    public void addScore(int points) {
        this.score += points;
        System.out.println("[\u2713] Score: +" + points + " (Total: " + this.score + ")");
    }

    public void addEnemyKill(int basePoints) {
        int points = basePoints;
        this.enemiesDefeated++;
        this.addScore(points);
    }

    public boolean isPlayerAlive() {
        return this.health > 0;
    }

    public int getHealthPercent() {
        return this.health * 100 / this.maxHealth;
    }

    public int healthPercent() {
        return this.health * 100 / this.maxHealth;
    }

    public int energyPercent() {
        return this.energy * 100 / this.maxEnergy;
    }

    public int armorPercent() {
        return this.armor * 100 / this.maxArmor;
    }

    public int getHealthBarIndex() {
        int n = this.healthPercent();
        if (n >= 80) {
            return 0;
        }
        if (n >= 60) {
            return 1;
        }
        if (n >= 40) {
            return 2;
        }
        if (n >= 20) {
            return 3;
        }
        if (n >= 5) {
            return 4;
        }
        return 5;
    }

    public int getEnergyBarIndex() {
        int n = this.energyPercent();
        if (n >= 80) {
            return 0;
        }
        if (n >= 60) {
            return 1;
        }
        if (n >= 40) {
            return 2;
        }
        if (n >= 20) {
            return 3;
        }
        if (n >= 5) {
            return 4;
        }
        return 5;
    }

    public int getArmorBarIndex() {
        int n = this.armorPercent();
        if (n >= 80) {
            return 0;
        }
        if (n >= 60) {
            return 1;
        }
        if (n >= 40) {
            return 2;
        }
        if (n >= 20) {
            return 3;
        }
        if (n >= 5) {
            return 4;
        }
        return 5;
    }

    public boolean isCritical() {
        return this.health < 20 || this.energy < 10;
    }

    public void addEffect(String string) {
        this.activeEffects.add(string);
    }

    public void removeEffect(String string) {
        this.activeEffects.remove(string);
    }

    public boolean hasEffect(String string) {
        return this.activeEffects.contains(string);
    }

    public String getFormattedTime() {
        int n = this.timeRemainingSeconds / 60;
        int n2 = this.timeRemainingSeconds % 60;
        return String.format("%d:%02d", n, n2);
    }

    @Override
    public String toString() {
        return String.format("GameState{ Health=%d/%d, Energy=%d/%d, Ammo=%d/%d, Level=%s, Status=%s }", this.health, this.maxHealth, this.energy, this.maxEnergy, this.ammo, this.ammoMax, this.currentLevel, this.currentStatus);
    }
}
