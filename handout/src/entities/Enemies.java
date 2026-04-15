package entities;

/*
 * Enemies - Complete enemy system: factory, physics, animation, AI, rendering
 *
 * Inner classes
 * -------------
 *  EnemyFactory.EnemyInstance   – a live enemy (position, AI, animation, health)
 *  EnemyAnimationManager        – loads sprite-sheet animations by keyword scan
 *  EnemyPhysicsProfile          – per-type movement & combat stats
 *  EnemyEntities.*              – convenience wrapper variants
 *
 * All animation loading is keyword-based (scans the asset directory) so the
 * exact filenames in Resources/ never need to match hard-coded strings.
 */

import game2D.Animation;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public class Enemies {

    // =========================================================================
    //  Logging helpers
    // =========================================================================
    private static void log(String msg)      { System.out.println("[Enemies] " + msg); }
    private static void logError(String msg) { System.err.println("[Enemies] ERROR: " + msg); }

    // =========================================================================
    //  Static helper: load individual sprite frames from a directory
    // =========================================================================
    /**
     * Scans the enemy's asset directory for a .png whose name contains
     * {@code stateName} and slices it into frames.
     */
    public static BufferedImage[] loadEnemySprites(String enemyType, String stateName) {
        try {
            String base = "Resources/industrial-zone/characters/enemies/";
            String sub;
            switch (enemyType.toLowerCase()) {
                case "ufo_saucer":      sub = "drones/1/";             break;
                case "jet_drone":       sub = "drones/2/";             break;
                case "hover_platform":  sub = "drones/3/";             break;
                case "combat_tank":     sub = "sci-fi-antagonists/1/"; break;
                case "armoured_knight": sub = "sci-fi-antagonists/2/"; break;
                case "winged_warrior":  sub = "sci-fi-antagonists/3/"; break;
                default:                sub = "drones/1/";             break;
            }
            File dir = new File(base + sub);
            if (!dir.exists()) { logError("Dir not found: " + dir.getPath()); return new BufferedImage[0]; }

            File[] matches = dir.listFiles(f -> {
                String n = f.getName().toLowerCase();
                return n.contains(stateName.toLowerCase()) && n.endsWith(".png");
            });
            if (matches == null || matches.length == 0) return new BufferedImage[0];

            Arrays.sort(matches);
            BufferedImage sheet = ImageIO.read(matches[0]);
            if (sheet == null) return new BufferedImage[0];

            int cols = extractFrameCountStatic(matches[0].getName());
            if (cols <= 0) cols = 4;
            int fw = sheet.getWidth() / cols, fh = sheet.getHeight();
            BufferedImage[] out = new BufferedImage[cols];
            for (int c = 0; c < cols; c++) out[c] = sheet.getSubimage(c * fw, 0, fw, fh);
            log("loadEnemySprites: " + matches[0].getName() + " (" + cols + " frames)");
            return out;
        } catch (Exception e) {
            logError("loadEnemySprites failed: " + e.getMessage());
            return new BufferedImage[0];
        }
    }

    private static int extractFrameCountStatic(String name) {
        java.util.regex.Matcher m =
            java.util.regex.Pattern.compile("(\\d+)Frames",
                java.util.regex.Pattern.CASE_INSENSITIVE).matcher(name);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }

    // =========================================================================
    //  Static helper: render a single sprite at screen coordinates
    // =========================================================================
    public static void renderEnemy(Graphics2D g, BufferedImage sprite,
                                   float x, float y,
                                   int screenWidth, int screenHeight,
                                   int cameraX, int cameraY) {
        if (sprite == null) return;
        int sx = (int)(x - cameraX), sy = (int)(y - cameraY);
        if (sx + 64 < 0 || sy + 64 < 0 || sx > screenWidth || sy > screenHeight) return;
        g.drawImage(sprite, sx, sy, 64, 64, null);
    }

    // =========================================================================
    //  EnemyEntities – convenience wrapper classes per variant
    // =========================================================================
    public static class EnemyEntities {

        public static class EnemyDrone_HoverPlatformVariant {
            private final EnemyFactory.EnemyInstance instance;
            public EnemyDrone_HoverPlatformVariant(float x, float y) {
                this.instance = EnemyFactory.createEnemy(EnemyPhysicsProfile.EnemyType.HOVER_PLATFORM, x, y);
            }
            public void update(float delta, float px, float py) { if (instance != null) instance.update(delta, px, py); }
            public void render(Graphics2D g, int cx, int cy)    { if (instance != null) instance.render(g, cx, cy); }
            public EnemyFactory.EnemyInstance getInstance()     { return instance; }
            public int     getHealth()          { return instance != null ? instance.getHealth() : 0; }
            public void    takeDamage(float dmg){ if (instance != null) instance.takeDamage(dmg); }
            public boolean isAlive()            { return instance != null && instance.isAlive(); }
            public float   getAttackRange()     { return instance != null ? instance.getAttackRange() : 0f; }
        }

        public static class EnemyDrone_JetDroneVariant {
            private final EnemyFactory.EnemyInstance instance;
            public EnemyDrone_JetDroneVariant(float x, float y) {
                this.instance = EnemyFactory.createEnemy(EnemyPhysicsProfile.EnemyType.JET_DRONE, x, y);
            }
            public void update(float delta, float px, float py) { if (instance != null) instance.update(delta, px, py); }
            public void render(Graphics2D g, int cx, int cy)    { if (instance != null) instance.render(g, cx, cy); }
            public EnemyFactory.EnemyInstance getInstance()     { return instance; }
            public int     getHealth()          { return instance != null ? instance.getHealth() : 0; }
            public void    takeDamage(float dmg){ if (instance != null) instance.takeDamage(dmg); }
            public boolean isAlive()            { return instance != null && instance.isAlive(); }
            public float   getAttackRange()     { return instance != null ? instance.getAttackRange() : 0f; }
        }

        public static class EnemyDrone_UfoSaucerHovering {
            private final EnemyFactory.EnemyInstance instance;
            public EnemyDrone_UfoSaucerHovering(float x, float y) {
                this.instance = EnemyFactory.createEnemy(EnemyPhysicsProfile.EnemyType.UFO_SAUCER, x, y);
            }
            public void update(float delta, float px, float py) { if (instance != null) instance.update(delta, px, py); }
            public void render(Graphics2D g, int cx, int cy)    { if (instance != null) instance.render(g, cx, cy); }
            public EnemyFactory.EnemyInstance getInstance()     { return instance; }
            public int     getHealth()          { return instance != null ? instance.getHealth() : 0; }
            public void    takeDamage(float dmg){ if (instance != null) instance.takeDamage(dmg); }
            public boolean isAlive()            { return instance != null && instance.isAlive(); }
            public float   getAttackRange()     { return instance != null ? instance.getAttackRange() : 0f; }
        }
    }

    // =========================================================================
    //  EnemyFactory + EnemyInstance
    // =========================================================================
    public static class EnemyFactory {

        public static EnemyInstance createEnemy(EnemyPhysicsProfile.EnemyType type, float x, float y) {
            return new EnemyInstance(type, x, y);
        }

        public static EnemyInstance createEnemyByName(String name, float x, float y) {
            try {
                return createEnemy(EnemyPhysicsProfile.EnemyType.valueOf(name.toUpperCase()), x, y);
            } catch (IllegalArgumentException e) {
                System.err.println("[EnemyFactory] Unknown type: " + name);
                return createEnemy(EnemyPhysicsProfile.EnemyType.UFO_SAUCER, x, y);
            }
        }

        public static List<EnemyInstance> createEnemyWave(EnemyPhysicsProfile.EnemyType[] types,
                                                           float startX, float y, float spacing) {
            List<EnemyInstance> list = new ArrayList<>();
            for (int i = 0; i < types.length; i++) list.add(createEnemy(types[i], startX + i * spacing, y));
            return list;
        }

        public static List<EnemyInstance> createAllEnemyTypes(float startX, float y) {
            List<EnemyInstance> list = new ArrayList<>();
            for (EnemyPhysicsProfile.EnemyType t : EnemyPhysicsProfile.EnemyType.values())
                list.add(createEnemy(t, startX + list.size() * 150f, y));
            return list;
        }

        // =====================================================================
        //  EnemyInstance
        // =====================================================================
        public static class EnemyInstance {

            private final EnemyPhysicsProfile.EnemyType type;
            private final String enemyName;
            private final EnemyPhysicsProfile    physicsProfile;
            private final EnemyAnimationManager  animationManager;

            // Position & movement
            private float   x, y;
            private float   velocityX   = 0f;
            private float   velocityY   = 0f;
            private boolean facingRight = false;
            private final float patrolCenterX;

            // Hover oscillation (drones only)
            private float hoverTime   = 0f;
            private float hoverOffset = 0f;

            // Animation
            private String currentAnimationName = "idle";
            private long   lastAnimationChange  = 0L;

            // Health / combat
            private int     health;
            private final int     maxHealth;
            private final int     armor;
            private long    lastAttackTime = 0L;
            private int     attackRotation = 0;
            private boolean isActive = true;

            public EnemyInstance(EnemyPhysicsProfile.EnemyType type, float x, float y) {
                this.type            = type;
                this.enemyName       = type.displayName;
                this.physicsProfile  = EnemyPhysicsProfile.createProfile(type);
                this.animationManager = new EnemyAnimationManager(type);
                this.animationManager.loadAllAnimations();
                this.health  = this.maxHealth = physicsProfile.getMaxHealth();
                this.armor   = physicsProfile.getArmor();
                this.x       = this.patrolCenterX = x;
                this.y       = y;
                System.out.println("[EnemyFactory] Created " + enemyName + " at (" + x + ", " + y + ")");
            }

            // ------------------------------------------------------------------
            //  AI Update — delta in SECONDS, called by Game.java each frame
            // ------------------------------------------------------------------
            public void update(float delta, float playerX, float playerY) {
                if (!isAlive()) return;

                // Drone hover oscillation
                if (physicsProfile.getType().category == EnemyPhysicsProfile.EnemyCategory.DRONE) {
                    hoverTime   += delta;
                    hoverOffset  = (float) Math.sin(hoverTime * 1.8f * Math.PI * 2.0) * 8f;
                }

                // px/ms speeds × 1000 → px/s
                float chaseSpeed  = physicsProfile.getRunSpeed()  * 1000f;
                float patrolSpeed = physicsProfile.getWalkSpeed() * 1000f;
                float detectRange = 320f;
                float halfPatrol  = 110f;

                float dx   = playerX - x;
                float dist = Math.abs(dx);

                if (dist < detectRange) {
                    // Chase player
                    velocityX   = (dx > 0 ? chaseSpeed : -chaseSpeed);
                    facingRight = dx > 0;
                } else {
                    // Patrol around spawn point
                    if      (x > patrolCenterX + halfPatrol) velocityX = -patrolSpeed;
                    else if (x < patrolCenterX - halfPatrol) velocityX =  patrolSpeed;
                    facingRight = velocityX > 0;
                }
                x += velocityX * delta;

                // Advance animation
                updateAnimationState();
                Animation anim = animationManager.getAnimation(currentAnimationName);
                if (anim != null) anim.update((long)(delta * 1000f));
            }

            // ------------------------------------------------------------------
            //  Contact-attack the player (with cooldown)
            // ------------------------------------------------------------------
            public void attackPlayer(PlayerBase player) {
                long now = System.currentTimeMillis();
                if (now - lastAttackTime < physicsProfile.getAttackCooldown()) return;
                lastAttackTime = now;
                ++attackRotation;
                player.takeDamage(physicsProfile.getAttackDamage());
                System.out.println("[Enemy:" + enemyName + "] Hit player for " + physicsProfile.getAttackDamage());
            }

            /** Legacy no-arg version — triggers attack animation only. */
            public void attackPlayer() {
                long now = System.currentTimeMillis();
                if (now - lastAttackTime > physicsProfile.getAttackCooldown()) {
                    lastAttackTime = now;
                    ++attackRotation;
                    updateAnimationState();
                }
            }

            // ------------------------------------------------------------------
            //  Render
            // ------------------------------------------------------------------
            public void render(Graphics2D g, int camX, int camY) {
                if (!isAlive()) return;

                int sx = (int)(x - camX);
                int sy = (int)(y - camY + hoverOffset);

                // Cull off-screen
                if (sx + 64 < 0 || sy + 64 < 0) return;

                // Sprite
                Animation anim = animationManager.getAnimation(currentAnimationName);
                if (anim != null) {
                    Image img = anim.getImage();
                    if (img != null) {
                        if (!facingRight) {
                            g.drawImage(img, sx + 64, sy, -64, 64, null);
                        } else {
                            g.drawImage(img, sx, sy, 64, 64, null);
                        }
                    }
                }

                // Health bar (shown only when damaged)
                if (health < maxHealth) {
                    int bw = 64, bh = 4, by = sy - 8;
                    g.setColor(new Color(60, 20, 20));
                    g.fillRect(sx, by, bw, bh);
                    float ratio = (float) health / maxHealth;
                    g.setColor(ratio > 0.5f ? new Color(200, 50, 50) : new Color(255, 80, 20));
                    g.fillRect(sx, by, (int)(bw * ratio), bh);
                    g.setColor(new Color(120, 40, 40));
                    g.drawRect(sx, by, bw, bh);
                }
            }

            // ------------------------------------------------------------------
            //  Animation state machine
            // ------------------------------------------------------------------
            public void updateAnimationState() {
                String next = determineCurrentAnimation();
                if (!next.equals(currentAnimationName)) playAnimation(next);
            }

            private String determineCurrentAnimation() {
                long msSinceAttack = System.currentTimeMillis() - lastAttackTime;
                if (msSinceAttack < physicsProfile.getAttackCooldown()) return getAttackAnimation();
                if (Math.abs(velocityX) > 5f) return "walk";
                return "idle";
            }

            private String getAttackAnimation() {
                String[] v = {"attack1", "attack2", "attack3", "attack4"};
                String base = v[attackRotation % 4];
                if (animationManager.hasAnimation(base + "b")) return base + "b";
                if (animationManager.hasAnimation(base))       return base;
                return "idle";
            }

            public void playAnimation(String name) {
                if (!name.equals(currentAnimationName) && animationManager.hasAnimation(name)) {
                    currentAnimationName = name;
                    lastAnimationChange  = System.currentTimeMillis();
                }
            }

            // ------------------------------------------------------------------
            //  Legacy physics update (milliseconds) — kept for back-compat
            // ------------------------------------------------------------------
            public void updatePhysics(long deltaMs) {
                // No player position available; just patrol
                update(deltaMs / 1000f, x + 9999f, y);
            }

            public void moveToward(float targetX, float targetY, long deltaMs) {
                float dx = targetX - x;
                velocityX = (dx > 0 ? 1 : -1) * physicsProfile.getRunSpeed() * 1000f;
                x += velocityX * (deltaMs / 1000f);
            }

            // ------------------------------------------------------------------
            //  Damage / death
            // ------------------------------------------------------------------
            public void takeDamage(float dmg) {
                float reduced = dmg * (1f - armor / 100f);
                health = Math.max(0, health - (int) reduced);
                if (health <= 0) {
                    isActive = false;
                    playAnimation("death");
                } else {
                    playAnimation("hurt");
                }
            }

            public void die() {
                isActive = false;
                playAnimation("death");
            }

            // ------------------------------------------------------------------
            //  Getters / setters
            // ------------------------------------------------------------------
            public float   getX()               { return x; }
            public float   getY()               { return y + hoverOffset; }
            public void    setX(float v)        { x = v; }
            public void    setY(float v)        { y = v; }
            public int     getHealth()          { return health; }
            public int     getMaxHealth()       { return maxHealth; }
            public int     getArmor()           { return armor; }
            public float   getVelocityX()       { return velocityX; }
            public float   getVelocityY()       { return velocityY; }
            public void    setVelocityX(float v){ velocityX = v; }
            public void    setVelocityY(float v){ velocityY = v; }
            public boolean isAlive()            { return health > 0 && isActive; }
            public boolean isGrounded()         { return false; }
            public void    setGrounded(boolean b){ /* drones ignore ground */ }
            public String  getEnemyName()       { return enemyName; }
            public EnemyPhysicsProfile.EnemyType getType() { return type; }
            public String  getCurrentAnimation(){ return currentAnimationName; }
            public EnemyPhysicsProfile   getPhysicsProfile()   { return physicsProfile; }
            public EnemyAnimationManager getAnimationManager() { return animationManager; }
            public float   getAttackRange()     { return physicsProfile.getAttackRange(); }
            public int     getAttackDamage()    { return physicsProfile.getAttackDamage(); }
            public void    setAnimation(Object o) { /* no-op */ }
            public void    setTargetPosition(float tx, float ty) { /* no-op */ }

            @Override
            public String toString() {
                return String.format("[%s] HP:%d/%d Armor:%d Vel:(%.2f,%.2f) Pos:(%.0f,%.0f)",
                    enemyName, health, maxHealth, armor, velocityX, velocityY, x, y);
            }
        }
    }

    // =========================================================================
    //  EnemyAnimationManager — keyword-based sprite-sheet loader
    // =========================================================================
    public static class EnemyAnimationManager {

        private final EnemyPhysicsProfile.EnemyType enemyType;
        private final String baseAssetPath;
        private final Map<String, Animation> animationCache = new HashMap<>();
        private static final String RESOURCE_BASE =
            "Resources/industrial-zone/characters/enemies/";

        public EnemyAnimationManager(EnemyPhysicsProfile.EnemyType type) {
            this.enemyType     = type;
            this.baseAssetPath = RESOURCE_BASE + type.assetPath;
            System.out.println("[EnemyAnimMgr] Created for " + type.displayName
                + " @ " + baseAssetPath);
        }

        public void loadAllAnimations() {
            switch (enemyType) {
                case UFO_SAUCER:      loadUfoSaucerAnimations();     break;
                case JET_DRONE:       loadJetDroneAnimations();      break;
                case HOVER_PLATFORM:  loadHoverPlatformAnimations(); break;
                case COMBAT_TANK:     loadCombatTankAnimations();    break;
                case ARMOURED_KNIGHT: loadArmouredKnightAnimations();break;
                case WINGED_WARRIOR:  loadWingedWarriorAnimations(); break;
            }
            System.out.println("  Loaded " + animationCache.size()
                + " animations for " + enemyType.displayName);
        }

        // Each method passes a keyword that appears in the real filename.
        // The loader scans the directory for a match so exact names don't matter.
        private void loadUfoSaucerAnimations() {
            loadByKeyword("idle",     "Idle",     4, 150L, true);
            loadByKeyword("walk",     "Traverse", 4, 100L, true);
            loadByKeyword("scanBeam", "Scan",     5, 120L, false);
            loadByKeyword("death",    "Death",    8,  80L, false);
        }

        private void loadJetDroneAnimations() {
            loadByKeyword("idle",  "Flight", 4, 120L, true);
            loadByKeyword("walk",  "Flight", 4, 120L, true);
            loadByKeyword("death", "Death",  6,  80L, false);
        }

        private void loadHoverPlatformAnimations() {
            loadByKeyword("idle",  "Advance", 6, 100L, true);
            loadByKeyword("walk",  "Advance", 6, 100L, true);
            loadByKeyword("death", "Drop",    7,  90L, false);
        }

        private void loadCombatTankAnimations() {
            loadByKeyword("idle",    "Idle",   4, 150L, true);
            loadByKeyword("walk",    "Walk",   6, 100L, true);
            loadByKeyword("attack1", "Attack", 5, 100L, false);
            loadByKeyword("hurt",    "Hurt",   3,  80L, false);
            loadByKeyword("death",   "Death",  8,  80L, false);
        }

        private void loadArmouredKnightAnimations() {
            loadByKeyword("idle",    "Idle",   4, 150L, true);
            loadByKeyword("walk",    "Walk",   6, 100L, true);
            loadByKeyword("attack1", "Attack", 5, 100L, false);
            loadByKeyword("hurt",    "Hurt",   3,  80L, false);
            loadByKeyword("death",   "Death",  8,  80L, false);
        }

        private void loadWingedWarriorAnimations() {
            loadByKeyword("idle",    "Idle",   4, 150L, true);
            loadByKeyword("walk",    "Walk",   6, 100L, true);
            loadByKeyword("attack1", "Attack", 5, 100L, false);
            loadByKeyword("hurt",    "Hurt",   3,  80L, false);
            loadByKeyword("death",   "Death",  8,  80L, false);
        }

        /**
         * Scan {@code baseAssetPath} for a .png whose name contains
         * {@code keyword} (case-insensitive), slice it into frames.
         * Falls back to a placeholder if no match found.
         */
        private void loadByKeyword(String animName, String keyword,
                                   int defaultFrames, long frameDurationMs, boolean loop) {
            File dir = new File(baseAssetPath);
            if (dir.exists()) {
                File[] matches = dir.listFiles(f -> {
                    String n = f.getName().toLowerCase();
                    return n.contains(keyword.toLowerCase()) && n.endsWith(".png");
                });
                if (matches != null && matches.length > 0) {
                    Arrays.sort(matches);
                    try {
                        BufferedImage sheet = ImageIO.read(matches[0]);
                        if (sheet != null) {
                            int cols = extractFrameCount(matches[0].getName());
                            if (cols <= 0) cols = defaultFrames;
                            int fw = sheet.getWidth() / cols;
                            int fh = sheet.getHeight();
                            Animation anim = new Animation();
                            for (int c = 0; c < cols; c++) {
                                anim.addFrame(sheet.getSubimage(c * fw, 0, fw, fh),
                                              frameDurationMs);
                            }
                            anim.setLoop(loop);
                            animationCache.put(animName, anim);
                            System.out.println("    [OK] " + animName + " <- " + matches[0].getName());
                            return;
                        }
                    } catch (Exception e) {
                        System.err.println("    [ERR] " + animName + ": " + e.getMessage());
                    }
                }
            }
            // Placeholder when file not found
            animationCache.put(animName, createPlaceholderAnimation());
            System.out.println("    [PH] " + animName + " (placeholder)");
        }

        private int extractFrameCount(String name) {
            java.util.regex.Matcher m =
                java.util.regex.Pattern.compile("(\\d+)Frames",
                    java.util.regex.Pattern.CASE_INSENSITIVE).matcher(name);
            return m.find() ? Integer.parseInt(m.group(1)) : 0;
        }

        private Animation createPlaceholderAnimation() {
            BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = img.createGraphics();
            g.setColor(new Color(180, 50, 220, 200));
            g.fillOval(4, 16, 56, 32);
            g.setColor(Color.WHITE);
            g.drawOval(4, 16, 56, 32);
            g.dispose();
            Animation anim = new Animation();
            anim.addFrame(img, 200L);
            anim.setLoop(true);
            return anim;
        }

        public Animation getAnimation(String name) {
            return animationCache.getOrDefault(name, createPlaceholderAnimation());
        }

        public boolean hasAnimation(String name) {
            return animationCache.containsKey(name);
        }

        public Map<String, Animation> getAllAnimations() {
            return new HashMap<>(animationCache);
        }

        @Override
        public String toString() {
            return "[EnemyAnimMgr] " + enemyType.displayName
                + " (" + animationCache.size() + " animations)";
        }
    }

    // =========================================================================
    //  EnemyPhysicsProfile — per-type stats
    //
    //  Speeds are stored in px/ms (legacy).  Multiply by 1000 for px/s.
    // =========================================================================
    public static class EnemyPhysicsProfile {

        private EnemyType type;
        private float walkSpeed    = 0.10f;
        private float runSpeed     = 0.15f;
        private float acceleration = 0.008f;
        private float friction     = 0.85f;
        private float airFriction  = 0.98f;
        private float jumpPower    = 0.30f;
        private float gravity      = 5.0E-4f;
        private float maxFallSpeed = 0.40f;
        private float width  = 64f;
        private float height = 64f;
        private int   maxHealth    = 50;
        private int   armor        = 0;
        private int   attackDamage = 10;
        private long  attackCooldown = 2000L;
        private float attackRange    = 120f;

        public static EnemyPhysicsProfile createProfile(EnemyType type) {
            EnemyPhysicsProfile p = new EnemyPhysicsProfile();
            p.type = type;
            switch (type) {
                case UFO_SAUCER:      p.configureUfoSaucer();      break;
                case JET_DRONE:       p.configureJetDrone();       break;
                case HOVER_PLATFORM:  p.configureHoverPlatform();  break;
                case COMBAT_TANK:     p.configureCombatTank();     break;
                case ARMOURED_KNIGHT: p.configureArmouredKnight(); break;
                case WINGED_WARRIOR:  p.configureWingedWarrior();  break;
            }
            return p;
        }

        private void configureUfoSaucer()     { walkSpeed=0.08f; runSpeed=0.10f; gravity=3e-4f; maxFallSpeed=0.30f; maxHealth=50;  attackDamage=10; attackCooldown=2000L; }
        private void configureJetDrone()      { walkSpeed=0.12f; runSpeed=0.14f; gravity=2e-4f; maxFallSpeed=0.25f; maxHealth=45;  attackDamage=15; attackCooldown=1500L; }
        private void configureHoverPlatform() { walkSpeed=0.09f; runSpeed=0.11f; gravity=2e-4f; maxFallSpeed=0.20f; maxHealth=60;  attackDamage=13; attackCooldown=2500L; }
        private void configureCombatTank()    { walkSpeed=0.07f; runSpeed=0.09f; gravity=4e-4f; maxFallSpeed=0.35f; maxHealth=85;  armor=30; attackDamage=18; attackCooldown=1600L; attackRange=150f; }
        private void configureArmouredKnight(){ walkSpeed=0.08f; runSpeed=0.10f; gravity=5e-4f; maxFallSpeed=0.40f; maxHealth=95;  armor=35; attackDamage=20; attackCooldown=1400L; attackRange=140f; }
        private void configureWingedWarrior() { walkSpeed=0.09f; runSpeed=0.12f; gravity=2e-4f; maxFallSpeed=0.25f; maxHealth=90;  armor=25; attackDamage=19; attackCooldown=1300L; attackRange=130f; }

        public EnemyType getType()          { return type; }
        public float  getWalkSpeed()        { return walkSpeed; }
        public float  getRunSpeed()         { return runSpeed; }
        public float  getAcceleration()     { return acceleration; }
        public float  getFriction()         { return friction; }
        public float  getAirFriction()      { return airFriction; }
        public float  getJumpPower()        { return jumpPower; }
        public float  getGravity()          { return gravity; }
        public float  getMaxFallSpeed()     { return maxFallSpeed; }
        public float  getWidth()            { return width; }
        public float  getHeight()           { return height; }
        public int    getMaxHealth()        { return maxHealth; }
        public int    getArmor()            { return armor; }
        public int    getAttackDamage()     { return attackDamage; }
        public long   getAttackCooldown()   { return attackCooldown; }
        public float  getAttackRange()      { return attackRange; }

        @Override
        public String toString() {
            return String.format("[%s] HP:%d Armor:%d Dmg:%d Speed:%.2f Cooldown:%dms",
                type.displayName, maxHealth, armor, attackDamage, runSpeed, attackCooldown);
        }

        public enum EnemyType {
            UFO_SAUCER     ("UfoSaucer",       "drones/1/",             EnemyCategory.DRONE),
            JET_DRONE      ("JetDrone",        "drones/2/",             EnemyCategory.DRONE),
            HOVER_PLATFORM ("HoverPlatform",   "drones/3/",             EnemyCategory.DRONE),
            COMBAT_TANK    ("CombatTank",      "sci-fi-antagonists/1/", EnemyCategory.ANTAGONIST),
            ARMOURED_KNIGHT("ArmouredKnight",  "sci-fi-antagonists/2/", EnemyCategory.ANTAGONIST),
            WINGED_WARRIOR ("WingedWarrior",   "sci-fi-antagonists/3/", EnemyCategory.ANTAGONIST);

            public final String displayName;
            public final String assetPath;
            public final EnemyCategory category;

            EnemyType(String displayName, String assetPath, EnemyCategory category) {
                this.displayName = displayName;
                this.assetPath   = assetPath;
                this.category    = category;
            }
        }

        public enum EnemyCategory { DRONE, ANTAGONIST }
    }
}
