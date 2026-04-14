/*
 * Decompiled with CFR 0.152.
 */
package entities;

import entities.enemies.Enemies;
import game2D.Animation;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public static class Enemies.EnemyAnimationManager {
    private Enemies.EnemyPhysicsProfile.EnemyType enemyType;
    private String baseAssetPath;
    private Map<String, Animation> animationCache;
    private static final String RESOURCE_BASE = "Resources/industrial-zone/characters/enemies/";

    public Enemies.EnemyAnimationManager(Enemies.EnemyPhysicsProfile.EnemyType enemyType) {
        this.enemyType = enemyType;
        this.baseAssetPath = RESOURCE_BASE + enemyType.assetPath;
        this.animationCache = new HashMap<String, Animation>();
        System.out.println("[EnemyAnimationManager] Created for " + enemyType.displayName);
    }

    public void loadAllAnimations() {
        System.out.println("  Loading animations for " + this.enemyType.displayName + " from: " + this.baseAssetPath);
        switch (this.enemyType.ordinal()) {
            case 0: {
                this.loadUfoSaucerAnimations();
                break;
            }
            case 1: {
                this.loadJetDroneAnimations();
                break;
            }
            case 2: {
                this.loadHoverPlatformAnimations();
                break;
            }
            case 3: {
                this.loadCombatTankAnimations();
                break;
            }
            case 4: {
                this.loadArmouredKnightAnimations();
                break;
            }
            case 5: {
                this.loadWingedWarriorAnimations();
            }
        }
        System.out.println("  \u2713 Loaded " + this.animationCache.size() + " animations");
    }

    private void loadUfoSaucerAnimations() {
        this.loadAnimation("idle", "01_Enemy_UfoSaucer_Idle", 4, 1, 150L, true);
        this.loadAnimation("traverse", "02_Enemy_UfoSaucer_Traverse", 6, 1, 100L, true);
        this.loadAnimation("scanBeam", "03_Enemy_UfoSaucer_ScanBeam", 5, 1, 120L, false);
        this.loadAnimation("traverseBeam", "04_Enemy_UfoSaucer_TraverseBeam", 6, 2, 100L, true);
        this.loadAnimation("death", "05_Enemy_UfoSaucer_Death", 8, 1, 80L, false);
    }

    private void loadJetDroneAnimations() {
        this.loadAnimation("aerialFlight", "01_Enemy_JetDrone_AerialFlight", 4, 1, 120L, true);
        this.loadAnimation("bombRelease", "02_Enemy_JetDrone_BombRelease", 5, 1, 100L, false);
    }

    private void loadHoverPlatformAnimations() {
        this.loadAnimation("advance", "01_Enemy_HoverPlatform_Advance", 6, 1, 100L, true);
        this.loadAnimation("advanceVariant", "02_Enemy_HoverPlatform_AdvanceVariant", 6, 1, 100L, true);
        this.loadAnimation("platformDrop", "03_Enemy_HoverPlatform_PlatformDrop", 7, 1, 90L, false);
        this.loadAnimation("capsuleAttack", "04_Enemy_HoverPlatform_CapsuleAttack", 5, 1, 110L, false);
    }

    private void loadCombatTankAnimations() {
        this.loadAnimation("idle", "01_Enemy_CombatTank_Idle", 4, 1, 150L, true);
        this.loadAnimation("walk", "02_Enemy_CombatTank_Walk", 6, 1, 100L, true);
        this.loadAnimation("attack1", "03_Enemy_CombatTank_Attack1", 5, 1, 100L, false);
        this.loadAnimation("attack1b", "04_Enemy_CombatTank_Attack1b", 3, 1, 120L, false);
        this.loadAnimation("attack2", "05_Enemy_CombatTank_Attack2", 6, 1, 95L, false);
        this.loadAnimation("attack2b", "06_Enemy_CombatTank_Attack2b", 4, 1, 110L, false);
        this.loadAnimation("attack3", "07_Enemy_CombatTank_Attack3", 5, 1, 100L, false);
        this.loadAnimation("attack4", "08_Enemy_CombatTank_Attack4", 6, 1, 100L, false);
        this.loadAnimation("special", "09_Enemy_CombatTank_Special", 7, 1, 90L, false);
        this.loadAnimation("operator", "10_Enemy_CombatTank_Operator", 3, 1, 150L, false);
        this.loadAnimation("fire", "11_Enemy_CombatTank_Fire", 4, 1, 100L, false);
        this.loadAnimation("hurt", "12_Enemy_CombatTank_Hurt", 3, 1, 80L, false);
        this.loadAnimation("death", "13_Enemy_CombatTank_Death", 8, 1, 80L, false);
    }

    private void loadArmouredKnightAnimations() {
        this.loadAnimation("idle", "01_Enemy_ArmouredKnight_Idle", 4, 1, 150L, true);
        this.loadAnimation("walk", "02_Enemy_ArmouredKnight_Walk", 6, 1, 100L, true);
        this.loadAnimation("attack1", "03_Enemy_ArmouredKnight_Attack1", 5, 1, 100L, false);
        this.loadAnimation("attack2", "04_Enemy_ArmouredKnight_Attack2", 6, 1, 100L, false);
        this.loadAnimation("attack3", "05_Enemy_ArmouredKnight_Attack3", 7, 1, 90L, false);
        this.loadAnimation("attack4", "06_Enemy_ArmouredKnight_Attack4", 8, 1, 85L, false);
        this.loadAnimation("special", "07_Enemy_ArmouredKnight_Special", 6, 1, 100L, false);
        this.loadAnimation("projectile", "08_Enemy_ArmouredKnight_Projectile", 4, 1, 120L, false);
        this.loadAnimation("hurt", "09_Enemy_ArmouredKnight_Hurt", 3, 1, 80L, false);
        this.loadAnimation("death", "10_Enemy_ArmouredKnight_Death", 8, 1, 80L, false);
    }

    private void loadWingedWarriorAnimations() {
        this.loadAnimation("idle", "01_Enemy_WingedWarrior_Idle", 4, 1, 150L, true);
        this.loadAnimation("walk", "02_Enemy_WingedWarrior_Walk", 6, 1, 100L, true);
        this.loadAnimation("attack1", "03_Enemy_WingedWarrior_Attack1", 5, 1, 100L, false);
        this.loadAnimation("attack2", "04_Enemy_WingedWarrior_Attack2", 6, 1, 100L, false);
        this.loadAnimation("attack3", "05_Enemy_WingedWarrior_Attack3", 7, 1, 90L, false);
        this.loadAnimation("attack4a", "06_Enemy_WingedWarrior_Attack4a", 5, 1, 100L, false);
        this.loadAnimation("attack4b", "07_Enemy_WingedWarrior_Attack4b", 6, 1, 95L, false);
        this.loadAnimation("special", "08_Enemy_WingedWarrior_Special", 7, 1, 100L, false);
        this.loadAnimation("projectile", "09_Enemy_WingedWarrior_Projectile", 4, 1, 120L, false);
        this.loadAnimation("hurt", "10_Enemy_WingedWarrior_Hurt", 3, 1, 80L, false);
        this.loadAnimation("death", "11_Enemy_WingedWarrior_Death", 8, 1, 80L, false);
    }

    private void loadAnimation(String string, String string2, int n, int n2, long l, boolean bl) {
        try {
            File file = new File(this.baseAssetPath + string2 + ".png");
            if (!file.exists()) {
                System.out.println("    \u26a0 Missing: " + string2 + ".png");
                this.animationCache.put(string, this.createPlaceholderAnimation());
                return;
            }
            BufferedImage bufferedImage = ImageIO.read(file);
            int n3 = bufferedImage.getWidth() / (n / (n2 > 1 ? n2 : 1));
            int n4 = bufferedImage.getHeight() / n2;
            Animation animation = new Animation();
            int n5 = 0;
            for (int i = 0; i < n2; ++i) {
                for (int j = 0; j < n / (n2 > 1 ? n2 : 1); ++j) {
                    if (n5 >= n) continue;
                    BufferedImage bufferedImage2 = bufferedImage.getSubimage(j * n3, i * n4, n3, n4);
                    animation.addFrame(bufferedImage2, l);
                    ++n5;
                }
            }
            animation.setLoop(bl);
            this.animationCache.put(string, animation);
        }
        catch (Exception exception) {
            System.err.println("    \u2717 Error loading " + string2 + ": " + exception.getMessage());
            this.animationCache.put(string, this.createPlaceholderAnimation());
        }
    }

    private Animation createPlaceholderAnimation() {
        BufferedImage bufferedImage = new BufferedImage(64, 64, 1);
        int n = 0xFF00FF;
        for (int i = 0; i < 64; ++i) {
            for (int j = 0; j < 64; ++j) {
                bufferedImage.setRGB(i, j, n);
            }
        }
        Animation animation = new Animation();
        animation.addFrame(bufferedImage, 100L);
        animation.setLoop(true);
        return animation;
    }

    public Animation getAnimation(String string) {
        return this.animationCache.getOrDefault(string, this.createPlaceholderAnimation());
    }

    public boolean hasAnimation(String string) {
        return this.animationCache.containsKey(string);
    }

    public Map<String, Animation> getAllAnimations() {
        return new HashMap<String, Animation>(this.animationCache);
    }

    public String toString() {
        return "[EnemyAnimationManager] " + this.enemyType.displayName + " (" + this.animationCache.size() + " animations)";
    }
}
