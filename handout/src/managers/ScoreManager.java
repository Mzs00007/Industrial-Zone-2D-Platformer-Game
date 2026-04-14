/*
 * Decompiled with CFR 0.152.
 */
package managers;

public static class Core.ScoreManager {
    private int score = 0;
    private int lives = 3;
    private int collectibles = 0;
    private int kills = 0;
    private int highScore = 0;

    public void collectibleGathered() {
        this.score += 10;
        ++this.collectibles;
    }

    public void enemyKilled() {
        this.score += 50;
        ++this.kills;
    }

    public void bossDefeated() {
        this.score += 500;
        ++this.kills;
    }

    public boolean loseLife() {
        --this.lives;
        if (this.lives < 0) {
            this.lives = 0;
        }
        return this.lives <= 0;
    }

    public void addLife() {
        ++this.lives;
    }

    public void updateHighScore() {
        if (this.score > this.highScore) {
            this.highScore = this.score;
            System.out.println("[ScoreManager] New high score: " + this.highScore);
        }
    }

    public void reset() {
        this.score = 0;
        this.lives = 3;
        this.collectibles = 0;
        this.kills = 0;
    }

    public int getScore() {
        return this.score;
    }

    public int getLives() {
        return this.lives;
    }

    public int getCollectibles() {
        return this.collectibles;
    }

    public int getKills() {
        return this.kills;
    }

    public int getHighScore() {
        return this.highScore;
    }
}
