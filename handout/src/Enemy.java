import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import animation.HorizontalSpritesheetLoader;
import entities.PlayerBase;
import entities.Projectile;

/**
 * Enemy -- Multi-type enemy with sprite animation, AI patrol/chase/attack behaviour.
 *
 * Drones:  DRONE_UFO, DRONE_JET, DRONE_HOVER (flying enemies)
 * Land:    LAND_TANK, LAND_KNIGHT, LAND_WARRIOR (ground-walking sci-fi-antagonists)
 * Bosses:  BOSS_GOLF_CART, BOSS_GREEN_MECH, BOSS_RUGBY_GUY (real bosses from bosses/ folder)
 *
 * AI States: IDLE -> PATROL -> CHASE -> ATTACK -> HURT -> DEATH
 */
public class Enemy {

    // =========================================================================
    //  Enemy type enum
    // =========================================================================
    public enum EnemyType {
        // Aerial drones
        DRONE_UFO      ("enemies/drones/1/",              "UfoSaucer",       3,  10,  320,  60, 1.2f, false),
        DRONE_JET      ("enemies/drones/2/",              "JetDrone",        2,  12,  400,  70, 0.9f, false),
        DRONE_HOVER    ("enemies/drones/6/",              "HoverPlatform",   3,  8,   280,  50, 1.5f, false),
        // Land enemies (sci-fi-antagonists — NOT bosses!)
        LAND_TANK      ("enemies/sci-fi-antagonists/1/",  "CombatTank",      8,  15,  300, 100, 1.8f, false),
        LAND_KNIGHT    ("enemies/sci-fi-antagonists/2/",  "ArmouredKnight",  6,  12,  280, 120, 1.5f, false),
        LAND_WARRIOR   ("enemies/sci-fi-antagonists/3/",  "WingedWarrior",   5,  10,  320, 100, 1.4f, false),
        // Real bosses (from bosses/ folder)
        BOSS_GOLF_CART ("bosses/GolfCartSoldier/",        "GolfCart",       40,  20,  350, 300, 2.0f, true),
        BOSS_GREEN_MECH("bosses/GreenMech/",              "GreenMech",      60,  30,  400, 500, 2.2f, true),
        BOSS_RUGBY_GUY ("bosses/RugbyGuy/",               "RugbyGuy",       80,  35,  380, 700, 2.5f, true);

        public final String spritePath;
        public final String prefix;
        public final int maxHealth;
        public final int attackDamage;
        public final float detectionRange;
        public final int scoreValue;
        public final float attackCooldownSec;
        private final boolean boss;

        EnemyType(String path, String prefix, int hp, int dmg, float range, int score, float atkCd, boolean boss) {
            this.spritePath = "Resources/industrial-zone/characters/" + path;
            this.prefix = prefix;
            this.maxHealth = hp;
            this.attackDamage = dmg;
            this.detectionRange = range;
            this.scoreValue = score;
            this.attackCooldownSec = atkCd;
            this.boss = boss;
        }

        public boolean isBoss() { return boss; }
        public boolean isLandEnemy() {
            return this == LAND_TANK || this == LAND_KNIGHT || this == LAND_WARRIOR;
        }
        public boolean isDrone() {
            return this == DRONE_UFO || this == DRONE_JET || this == DRONE_HOVER;
        }
    }

    // =========================================================================
    //  AI State
    // =========================================================================
    private enum AIState { IDLE, PATROL, CHASE, ATTACK, HURT, DEATH }

    // =========================================================================
    //  Constants
    // =========================================================================
    private static final float GRAVITY      = 600f;
    private static final float PATROL_SPEED = 60f;
    private static final float CHASE_SPEED  = 120f;
    private static final float BOSS_CHASE   = 90f;
    private static final float PATROL_RANGE = 200f;
    private static final float ATTACK_RANGE = 48f;
    private static final float PATROL_Y_BAND = 40f; // max vertical drift during chase

    // =========================================================================
    //  Fields
    // =========================================================================
    private final EnemyType type;
    private float x, y;
    private float velX, velY;
    private float spawnX, spawnY;
    private int width, height;
    private int health;
    private boolean alive = true;
    private boolean facingRight = true;
    private boolean grounded = false;

    // AI
    private AIState aiState = AIState.IDLE;
    private float aiTimer = 0;
    private float attackTimer = 0;
    private float hurtTimer = 0;
    private float idleTimer = 0;
    private float hoverOffset = 0;
    private float hoverTime = 0;
    private int patrolDir = 1;

    // Pending enemy projectile (collected by Game each frame)
    private Projectile pendingProjectile = null;

    // Patrol zone boundaries
    private float patrolMinX, patrolMaxX;

    // Animation
    private Map<String, BufferedImage[]> anims = new HashMap<>();
    private String currentAnim = "idle";
    private int frameIdx = 0;
    private float frameTimer = 0;
    private float frameDurationMs = 150;

    // =========================================================================
    //  Constructors
    // =========================================================================
    /** Default drone constructor (backward-compatible with Game.java spawner). */
    public Enemy(float x, float y) {
        this(x, y, EnemyType.DRONE_UFO);
    }

    public Enemy(float x, float y, EnemyType type) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.spawnX = x;
        this.spawnY = y;
        this.health = type.maxHealth;
        this.width  = type.isBoss() ? 96 : (type.isLandEnemy() ? 64 : 48);
        this.height = type.isBoss() ? 96 : (type.isLandEnemy() ? 64 : 48);
        this.patrolMinX = x - PATROL_RANGE;
        this.patrolMaxX = x + PATROL_RANGE;
        loadSprites();
    }

    // =========================================================================
    //  Sprite Loading â€” scans folder for matching PNGs, splits spritesheets
    // =========================================================================
    private void loadSprites() {
        File dir = new File(type.spritePath);
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("[Enemy] Sprite dir not found: " + type.spritePath);
            return;
        }
        File[] files = dir.listFiles(f -> f.getName().toLowerCase().endsWith(".png"));
        if (files == null) return;

        // Map animation keywords to keys
        String[][] animMap;
        if (type.isBoss() || type.isLandEnemy()) {
            animMap = new String[][]{
                {"Idle",    "idle"},
                {"Walk",    "walk"},
                {"Attack1", "attack1"},
                {"Attack2", "attack2"},
                {"Attack3", "attack3"},
                {"Attack4", "attack4"},
                {"Special", "special"},
                {"Hurt",    "hurt"},
                {"Hit",     "hurt"},
                {"Hurt1",   "hurt"},
                {"Hurt2",   "hurt2"},
                {"Death",   "death"},
                {"Charge",  "charge"},
                {"Cart_Idle",  "cart_idle"},
                {"Cart_Walk",  "cart_walk"},
                {"Cart_FastOut","cart_fastout"},
                {"Cart_Death", "cart_death"},
                {"Cart_IdleEmpty","cart_idleempty"},
                {"Projectile","projectile"},
            };
        } else {
            animMap = new String[][]{
                {"Idle",       "idle"},
                {"Traverse",   "walk"},
                {"Flight",     "walk"},
                {"Advance",    "walk"},
                {"Attack",     "attack1"},
                {"ScanBeam",   "attack1"},
                {"Bomb",       "attack1"},
                {"Drop",       "attack1"},
                {"Capsule",    "attack2"},
                {"Destruction","death"},
            };
        }

        for (String[] entry : animMap) {
            String keyword = entry[0];
            String key = entry[1];
            if (anims.containsKey(key)) continue; // don't overwrite
            for (File f : files) {
                if (f.getName().contains(keyword)) {
                    BufferedImage[] frames = loadSheet(f.getPath());
                    if (frames.length > 0) {
                        anims.put(key, frames);
                        System.out.printf("[Enemy] %s %-10s (%d frames) %s%n",
                            type.prefix, key, frames.length, f.getName());
                        break;
                    }
                }
            }
        }

        if (!anims.containsKey("idle")) {
            System.err.println("[Enemy] WARNING: No idle animation for " + type);
        }
    }

    private int extractFrameCount(String name) {
        java.util.regex.Matcher m =
            java.util.regex.Pattern.compile("(\\d+)Frames",
                java.util.regex.Pattern.CASE_INSENSITIVE).matcher(name);
        return m.find() ? Integer.parseInt(m.group(1)) : -1;
    }

    /**
     * Load a spritesheet and split into frames.
     * Uses HorizontalSpritesheetLoader for sheets wider than 32px (manifest metadata aware).
     * Falls back to single-frame for narrow images.
     */
    private BufferedImage[] loadSheet(String path) {
        try {
            BufferedImage sheet = ImageIO.read(new File(path));
            if (sheet == null) return new BufferedImage[0];
            if (sheet.getWidth() > 32) {
                // Use the loader — reads manifest metadata for correct frame count
                HorizontalSpritesheetLoader loader = HorizontalSpritesheetLoader.fromFilename(path);
                if (loader.isLoaded()) return loader.getAllFrames();
            }
            // Single frame (icon or small image)
            return new BufferedImage[]{ sheet };
        } catch (Exception e) {
            System.err.println("[Enemy] Load error: " + path + " - " + e.getMessage());
            return new BufferedImage[0];
        }
    }

    // =========================================================================
    //  UPDATE â€” AI + physics + animation  (called with delta in SECONDS)
    // =========================================================================
    public void update(float delta, float playerX, float playerY) {
        if (!alive) return;

        float dx = playerX - x;
        float dy = playerY - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        hoverTime += delta;

        // Timers
        attackTimer -= delta;
        if (hurtTimer > 0) { hurtTimer -= delta; if (hurtTimer <= 0) aiState = AIState.PATROL; }

        // AI state machine
        switch (aiState) {
            case IDLE:
                idleTimer += delta;
                velX = 0;
                if (idleTimer > 1.0f) { aiState = AIState.PATROL; idleTimer = 0; }
                if (dist < type.detectionRange) aiState = AIState.CHASE;
                setAnim("idle");
                break;

            case PATROL:
                velX = PATROL_SPEED * patrolDir;
                facingRight = patrolDir > 0;
                if (Math.abs(x - spawnX) > PATROL_RANGE) {
                    patrolDir *= -1;
                    velX = PATROL_SPEED * patrolDir;
                }
                if (dist < type.detectionRange) aiState = AIState.CHASE;
                setAnim("walk");
                break;

            case CHASE:
                float chaseSpd = type.isBoss() ? BOSS_CHASE : CHASE_SPEED;
                if (dx > 8)       { velX =  chaseSpd; facingRight = true; }
                else if (dx < -8) { velX = -chaseSpd; facingRight = false; }
                else              { velX = 0; }

                if (dist < ATTACK_RANGE && attackTimer <= 0) {
                    aiState = AIState.ATTACK;
                    attackTimer = type.attackCooldownSec;
                    frameIdx = 0; frameTimer = 0;
                }
                if (dist > type.detectionRange * 1.5f) aiState = AIState.PATROL;
                setAnim("walk");
                break;

            case ATTACK:
                velX = 0;
                setAnim("attack1");
                BufferedImage[] atkFrames = anims.get("attack1");
                if (atkFrames != null && frameIdx >= atkFrames.length - 1) {
                    // Attack animation finished, fire projectile for ranged enemies
                    if (type.isDrone() || type == EnemyType.LAND_TANK) {
                        float projDir = facingRight ? 1 : -1;
                        pendingProjectile = new Projectile(
                            x + (facingRight ? width : -10), y + height / 2f,
                            projDir * 300f, 0f, type.attackDamage, 3.0f)
                                .setEnemyProjectile(true);
                    }
                    aiState = AIState.CHASE;
                }
                break;

            case HURT:
                velX = 0;
                setAnim("hurt");
                break;

            case DEATH:
                velX = 0;
                setAnim("death");
                BufferedImage[] deathFrames = anims.get("death");
                if (deathFrames != null && frameIdx >= deathFrames.length - 1) {
                    alive = false;
                }
                return;
        }

        // Hover oscillation for flying drones only (not land enemies/bosses/DRONE_HOVER)
        if (type.isDrone() && type != EnemyType.DRONE_HOVER) {
            hoverOffset = (float) Math.sin(hoverTime * 1.8 * Math.PI) * 6;
        }

        // Physics: gravity for bosses, land enemies, and ground drones (DRONE_HOVER)
        if (type.isBoss() || type.isLandEnemy() || type == EnemyType.DRONE_HOVER) {
            velY += GRAVITY * delta;
            if (velY > 600) velY = 600;
            y += velY * delta;
            if (y + height > 520) {
                y = 520 - height;
                velY = 0;
                grounded = true;
            }
        } else {
            // Flying drones: clamp Y near spawn to prevent vertical stacking
            float yDrift = y - spawnY;
            if (Math.abs(yDrift) > PATROL_Y_BAND) {
                y = spawnY + (yDrift > 0 ? PATROL_Y_BAND : -PATROL_Y_BAND);
            }
        }

        x += velX * delta;

        // Enforce patrol zone
        if (x < patrolMinX) { x = patrolMinX; patrolDir = 1; velX = Math.abs(velX); }
        if (x > patrolMaxX) { x = patrolMaxX; patrolDir = -1; velX = -Math.abs(velX); }

        // Animation
        updateAnimation(delta);
    }

    /** Backward-compatible: called by old code. Adapts longâ†’float. */
    public void update(long deltaTime) {
        update(deltaTime / 1000f, x, y); // no player tracking in legacy mode
    }

    // =========================================================================
    //  ATTACK PLAYER â€” contact damage with cooldown
    // =========================================================================
    public void attackPlayer(PlayerBase player) {
        if (!alive || attackTimer > 0) return;
        player.takeDamage(type.attackDamage);
        attackTimer = type.attackCooldownSec;
        aiState = AIState.ATTACK;
        frameIdx = 0;
        frameTimer = 0;
    }

    // =========================================================================
    //  DAMAGE
    // =========================================================================
    public void takeDamage(int damage) {
        if (!alive) return;
        health -= damage;
        if (health <= 0) {
            health = 0;
            aiState = AIState.DEATH;
            frameIdx = 0;
            frameTimer = 0;
        } else {
            aiState = AIState.HURT;
            hurtTimer = 0.3f;
            frameIdx = 0;
            frameTimer = 0;
        }
    }

    // =========================================================================
    //  ANIMATION
    // =========================================================================
    private void setAnim(String name) {
        if (!name.equals(currentAnim) && anims.containsKey(name)) {
            currentAnim = name;
            frameIdx = 0;
            frameTimer = 0;
        }
        // Fallback to idle if requested anim doesn't exist
        if (!anims.containsKey(currentAnim) && anims.containsKey("idle")) {
            currentAnim = "idle";
        }
    }

    private void updateAnimation(float delta) {
        BufferedImage[] frames = anims.get(currentAnim);
        if (frames == null || frames.length == 0) return;

        frameTimer += delta * 1000f;
        if (frameTimer >= frameDurationMs) {
            frameTimer -= frameDurationMs;
            frameIdx++;
            if (frameIdx >= frames.length) {
                // Loop for idle/walk, don't loop for attack/hurt/death
                if (currentAnim.equals("idle") || currentAnim.equals("walk")) {
                    frameIdx = 0;
                } else {
                    frameIdx = frames.length - 1;
                }
            }
        }
    }

    // =========================================================================
    //  RENDER
    // =========================================================================
    public void render(Graphics2D g, int camX, int camY) {
        if (!alive) return;

        BufferedImage[] frames = anims.get(currentAnim);
        if (frames == null || frames.length == 0) {
            // Emergency fallback: red rectangle
            int sx = (int)(x - camX), sy = (int)(y - camY);
            g.setColor(new Color(200, 40, 40, 160));
            g.fillRect(sx, sy, width, height);
            return;
        }

        int idx = Math.min(frameIdx, frames.length - 1);
        BufferedImage img = frames[idx];
        if (img == null) return;

        int sx = (int)(x - camX);
        int sy = (int)(y - camY + ((type.isBoss() || type.isLandEnemy() || type == EnemyType.DRONE_HOVER) ? 0 : hoverOffset));

        // Flip horizontally when facing left
        if (!facingRight) {
            g.drawImage(img, sx + width, sy, -width, height, null);
        } else {
            g.drawImage(img, sx, sy, width, height, null);
        }

        // Health bar above enemy
        if (health < type.maxHealth) {
            int barW = width;
            int barH = type.isBoss() ? 8 : 4;
            int by = sy - (type.isBoss() ? 14 : 8);
            float ratio = (float) health / type.maxHealth;

            // Background
            g.setColor(new Color(40, 10, 10, 180));
            g.fillRect(sx, by, barW, barH);

            // Health fill
            Color fill = type.isBoss()
                ? new Color(220, 60, 20) // boss = red
                : ratio > 0.5f ? new Color(0, 200, 60) : new Color(220, 180, 0);
            g.setColor(fill);
            g.fillRect(sx, by, (int)(barW * ratio), barH);

            // Border
            g.setColor(new Color(80, 80, 80, 120));
            g.drawRect(sx, by, barW, barH);

            // Boss name label
            if (type.isBoss()) {
                g.setFont(new Font("Consolas", Font.BOLD, 10));
                g.setColor(new Color(255, 100, 60));
                String name = type.prefix.replaceAll("([A-Z])", " $1").trim().toUpperCase();
                g.drawString(name, sx, by - 4);
            }
        }
    }

    // =========================================================================
    //  GETTERS
    // =========================================================================
    public float getX()         { return x; }
    public float getY()         { return y; }
    public float getWidth()     { return width; }
    public float getHeight()    { return height; }
    public int   getHealth()    { return health; }
    public boolean isAlive()    { return alive; }
    public EnemyType getType()  { return type; }
    public int getScoreValue()  { return type.scoreValue; }

    /** Returns and clears pending enemy projectile (null if none). */
    public Projectile getPendingProjectile() {
        Projectile p = pendingProjectile;
        pendingProjectile = null;
        return p;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.spawnX = x;
        this.spawnY = y;
    }
}
