/*
 * Decompiled with CFR 0.152.
 * UPDATED: 2026-04-14 - Production sprite asset integration
 */
import game2D.Animation;
import utils.Config;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public class Enemies {
    private static final boolean VERBOSE_LOGGING = true;

    private static void log(String string) {
        System.out.println("[Enemies] " + string);
    }

    private static void logError(String string) {
        System.err.println("[Enemies] ERROR: " + string);
    }
    
    /**
     * Load enemy sprite frames from Config asset paths
     */
    public static BufferedImage[] loadEnemySprites(String enemyType, String stateName) {
        try {
            String spritePath = Config.getEnemyAssetPath(enemyType) + stateName + "/";
            File spriteDir = new File(spritePath);
            
            if (!spriteDir.exists()) {
                logError("Enemy sprite directory not found: " + spritePath);
                return new BufferedImage[0];
            }
            
            File[] frames = spriteDir.listFiles((dir, name) -> 
                name.endsWith(".png") || name.endsWith(".jpg"));
            
            if (frames == null || frames.length == 0) {
                logError("No sprite frames found for " + enemyType + " " + stateName);
                return new BufferedImage[0];
            }
            
            BufferedImage[] sprites = new BufferedImage[frames.length];
            for (int i = 0; i < frames.length; i++) {
                sprites[i] = ImageIO.read(frames[i]);
                if (sprites[i] == null) {
                    throw new Exception("Failed to load sprite: " + frames[i].getAbsolutePath());
                }
            }
            
            Enemies.log("✓ Loaded " + enemyType + " " + stateName + " (" + frames.length + " frames)");
            return sprites;
        } catch (Exception e) {
            logError("Failed to load sprites for " + enemyType + ": " + e.getMessage());
            return new BufferedImage[0];
        }
    }
    
    /**
     * Render enemy sprite at screen position
     */
    public static void renderEnemy(Graphics2D g, BufferedImage sprite, float x, float y, 
                                   int screenWidth, int screenHeight, int cameraX, int cameraY) {
        if (sprite == null) return;
        int screenX = (int)(x - cameraX);
        int screenY = (int)(y - cameraY);
        
        if (screenX >= -64 && screenX < screenWidth && screenY >= -64 && screenY < screenHeight) {
            g.drawImage(sprite, screenX, screenY, 64, 64, null);
        }
    }

    public static class EnemyEntities {

        public static class EnemyDrone_HoverPlatformVariant {
            private EnemyFactory.EnemyInstance instance;
            private float x;
            private float y;

            public EnemyDrone_HoverPlatformVariant(float f, float f2) {
                this.x = f;
                this.y = f2;
                this.instance = EnemyFactory.createEnemy(EnemyPhysicsProfile.EnemyType.HOVER_PLATFORM, f, f2);
            }

            public void update(long l) {
                if (this.instance != null) {
                    this.instance.updatePhysics(l);
                    this.x = this.instance.getX();
                    this.y = this.instance.getY();
                }
            }

            public EnemyFactory.EnemyInstance getInstance() {
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

        public static class EnemyDrone_JetDroneVariant {
            private EnemyFactory.EnemyInstance instance;
            private float x;
            private float y;

            public EnemyDrone_JetDroneVariant(float f, float f2) {
                this.x = f;
                this.y = f2;
                this.instance = EnemyFactory.createEnemy(EnemyPhysicsProfile.EnemyType.JET_DRONE, f, f2);
            }

            public void update(long l) {
                if (this.instance != null) {
                    this.instance.updatePhysics(l);
                    this.x = this.instance.getX();
                    this.y = this.instance.getY();
                }
            }

            public EnemyFactory.EnemyInstance getInstance() {
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

        public static class EnemyDrone_UfoSaucerHovering {
            private EnemyFactory.EnemyInstance instance;
            private float x;
            private float y;

            public EnemyDrone_UfoSaucerHovering(float f, float f2) {
                this.x = f;
                this.y = f2;
                this.instance = EnemyFactory.createEnemy(EnemyPhysicsProfile.EnemyType.UFO_SAUCER, f, f2);
            }

            public void update(long l) {
                if (this.instance != null) {
                    this.instance.updatePhysics(l);
                    this.x = this.instance.getX();
                    this.y = this.instance.getY();
                }
            }

            public EnemyFactory.EnemyInstance getInstance() {
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
    }

    public static class EnemyFactory {
        public static EnemyInstance createEnemy(EnemyPhysicsProfile.EnemyType enemyType, float f, float f2) {
            return new EnemyInstance(enemyType, f, f2);
        }

        public static EnemyInstance createEnemyByName(String string, float f, float f2) {
            try {
                EnemyPhysicsProfile.EnemyType enemyType = EnemyPhysicsProfile.EnemyType.valueOf(string.toUpperCase());
                return EnemyFactory.createEnemy(enemyType, f, f2);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                System.err.println("[EnemyFactory] Unknown enemy: " + string);
                return EnemyFactory.createEnemy(EnemyPhysicsProfile.EnemyType.UFO_SAUCER, f, f2);
            }
        }

        public static List<EnemyInstance> createEnemyWave(EnemyPhysicsProfile.EnemyType[] enemyTypeArray, float f, float f2, float f3) {
            ArrayList<EnemyInstance> arrayList = new ArrayList<EnemyInstance>();
            for (int i = 0; i < enemyTypeArray.length; ++i) {
                arrayList.add(EnemyFactory.createEnemy(enemyTypeArray[i], f + (float)i * f3, f2));
            }
            return arrayList;
        }

        public static List<EnemyInstance> createAllEnemyTypes(float f, float f2) {
            ArrayList<EnemyInstance> arrayList = new ArrayList<EnemyInstance>();
            for (EnemyPhysicsProfile.EnemyType enemyType : EnemyPhysicsProfile.EnemyType.values()) {
                arrayList.add(EnemyFactory.createEnemy(enemyType, f + (float)(arrayList.size() * 150), f2));
            }
            return arrayList;
        }

        public static class EnemyInstance {
            private EnemyPhysicsProfile.EnemyType type;
            private String enemyName;
            private EnemyPhysicsProfile physicsProfile;
            private EnemyAnimationManager animationManager;
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

            public EnemyInstance(EnemyPhysicsProfile.EnemyType enemyType, float f, float f2) {
                this.x = f;
                this.y = f2;
                this.type = enemyType;
                this.enemyName = enemyType.displayName;
                this.physicsProfile = EnemyPhysicsProfile.createProfile(enemyType);
                this.animationManager = new EnemyAnimationManager(enemyType);
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

            public EnemyPhysicsProfile.EnemyType getType() {
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

            public EnemyPhysicsProfile getPhysicsProfile() {
                return this.physicsProfile;
            }

            public EnemyAnimationManager getAnimationManager() {
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

    public static class EnemyAnimationManager {
        private EnemyPhysicsProfile.EnemyType enemyType;
        private String baseAssetPath;
        private Map<String, Animation> animationCache;
        private static final String RESOURCE_BASE = "Resources/industrial-zone/characters/enemies/";

        public EnemyAnimationManager(EnemyPhysicsProfile.EnemyType enemyType) {
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

    public static class EnemyPhysicsProfile {
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

        public static EnemyPhysicsProfile createProfile(EnemyType enemyType) {
            EnemyPhysicsProfile enemyPhysicsProfile = new EnemyPhysicsProfile();
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
}
