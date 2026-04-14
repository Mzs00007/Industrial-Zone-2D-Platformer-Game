/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.ArrayList;
import java.util.List;

public static class AnimationAndSpriteLoader.GameStateManager {
    private AnimationAndSpriteLoader.PlayerController player;
    private List<AnimationAndSpriteLoader.EnemyController> enemies = new ArrayList<AnimationAndSpriteLoader.EnemyController>();
    private AnimationAndSpriteLoader.BossController boss;
    private AnimationAndSpriteLoader.EnvironmentController environment;
    private List<AnimationAndSpriteLoader.ProjectileController> projectiles = new ArrayList<AnimationAndSpriteLoader.ProjectileController>();
    private List<AnimationAndSpriteLoader.VFXController> vfxEffects = new ArrayList<AnimationAndSpriteLoader.VFXController>();
    private AnimationAndSpriteLoader.InputHandler input = new AnimationAndSpriteLoader.InputHandler();

    public AnimationAndSpriteLoader.GameStateManager() {
        this.environment = new AnimationAndSpriteLoader.EnvironmentController(1);
    }

    public void initialize(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody) {
        this.player = new AnimationAndSpriteLoader.PlayerController(physicsBody, this.input);
    }

    public void update(float f) {
        int n;
        this.input.clearFrame();
        if (this.player != null) {
            this.player.update(f);
            this.player.physics.update(f);
        }
        for (AnimationAndSpriteLoader.EnemyController entityAnimationController2 : this.enemies) {
            entityAnimationController2.update(f);
            if (this.player != null) {
                entityAnimationController2.updateAI(this.player.physics);
            }
            entityAnimationController2.physics.update(f);
        }
        if (this.boss != null) {
            this.boss.update(f);
            if (this.player != null) {
                this.boss.updateBehavior(this.player.physics.position);
            } else {
                this.boss.updateBehavior(new AnimationAndSpriteLoader.PhysicsUnitSystem.Vector2D(0.0f, 0.0f));
            }
            this.boss.physics.update(f);
        }
        for (n = this.projectiles.size() - 1; n >= 0; --n) {
            AnimationAndSpriteLoader.ProjectileController projectileController = this.projectiles.get(n);
            projectileController.update(f);
            if (projectileController.isAlive()) continue;
            this.projectiles.remove(n);
        }
        for (n = this.vfxEffects.size() - 1; n >= 0; --n) {
            AnimationAndSpriteLoader.VFXController vFXController = this.vfxEffects.get(n);
            vFXController.update(f);
            if (!vFXController.isComplete()) continue;
            this.vfxEffects.remove(n);
        }
    }

    public void addEnemy(AnimationAndSpriteLoader.EnemyController enemyController) {
        this.enemies.add(enemyController);
    }

    public void setBoss(AnimationAndSpriteLoader.BossController bossController) {
        this.boss = bossController;
    }

    public void addProjectile(AnimationAndSpriteLoader.ProjectileController projectileController) {
        this.projectiles.add(projectileController);
    }

    public void addVFX(AnimationAndSpriteLoader.VFXController vFXController) {
        this.vfxEffects.add(vFXController);
    }

    public AnimationAndSpriteLoader.PlayerController getPlayer() {
        return this.player;
    }

    public List<AnimationAndSpriteLoader.EnemyController> getEnemies() {
        return this.enemies;
    }

    public AnimationAndSpriteLoader.BossController getBoss() {
        return this.boss;
    }

    public AnimationAndSpriteLoader.InputHandler getInput() {
        return this.input;
    }

    public AnimationAndSpriteLoader.EnvironmentController getEnvironment() {
        return this.environment;
    }
}
