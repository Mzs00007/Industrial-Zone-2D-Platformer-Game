package entities;
import game2D.*;

/*
 * PlayerBase - Animated player character with physics, input, and animation
 *
 * Features
 * --------
 *  - Loads sprite-sheet animations from the assets folder automatically
 *  - State machine: IDLE / WALK / RUN / JUMP / FALL / ATTACK / HIT / DEATH
 *  - Full keyboard input (A/D arrows, SPACE jump, SHIFT dash, CTRL shoot)
 *  - Gravity + velocity physics
 *  - Platform collision via Game.CURRENT_PLATFORMS (set with setPlatforms())
 *  - Health / damage system with invincibility frames
 */

import animation.HorizontalSpritesheetLoader;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;

public class PlayerBase {

    // =========================================================================
    //  Enums
    // =========================================================================
    public enum CharacterType {
        BIKER ("biker"),
        PUNK  ("punk"),
        CYBORG("cyborg");

        public final String folderName;
        CharacterType(String n) { this.folderName = n; }
    }

    public enum AnimState {
        IDLE, IDLE2, WALK, RUN, DASH, JUMP, DOUBLE_JUMP, FALL,
        CLIMB, HANG, PULLUP, PUNCH, ATTACK1, ATTACK2, ATTACK3,
        WALK_ATTACK, RUN_ATTACK, HIT, DEATH,
        USE, SITDOWN, ANGRY, HAPPY, TALK
    }

    // =========================================================================
    //  Constants
    // =========================================================================
    private static final int   SPRITE_W         = 64;
    private static final int   SPRITE_H         = 64;
    private static final float GRAVITY          = 900.0f;  // px/s²
    private static final float TERM_VELOCITY    = 700.0f;  // px/s
    private static final float WALK_SPEED       = 160.0f;
    private static final float RUN_SPEED        = 240.0f;
    private static final float JUMP_POWER       = -420.0f;
    private static final float DASH_SPEED       = 380.0f;
    private static final long  DASH_COOLDOWN_MS = 900L;
    private static final long  ATK_COOLDOWN_MS  = 550L;
    private static final long  DMG_COOLDOWN_MS  = 600L;

    // Frame durations (ms) — one entry per AnimState ordinal
    private static final int[] FRAME_DURATIONS = {
        150,  // IDLE
        150,  // IDLE2
        100,  // WALK
        80,   // RUN
        60,   // DASH
        80,   // JUMP
        80,   // DOUBLE_JUMP
        100,  // FALL
        120,  // CLIMB
        150,  // HANG
        80,   // PULLUP
        70,   // PUNCH
        70,   // ATTACK1
        70,   // ATTACK2
        70,   // ATTACK3
        70,   // WALK_ATTACK
        65,   // RUN_ATTACK
        100,  // HIT (Hurt)
        120,  // DEATH
        100,  // USE
        120,  // SITDOWN
        150,  // ANGRY
        150,  // HAPPY
        120   // TALK
    };

    // =========================================================================
    //  Static: platform data set by Game.java; input key set
    // =========================================================================
    private static float[][]   platforms = new float[0][0];
    private static Set<Integer> keysDown = new HashSet<>();

    /** Called by Game.java before creating the player. */
    public static void setPlatforms(float[][] data) { platforms = data; }

    /** Sets the aim angle (radians) toward the mouse cursor — called each frame from Game.java. */
    public void setAimAngle(double angle) { this.aimAngle = angle; }

    /**
     * Triggers a shot request from an external source (e.g. mouse click).
     * Respects fire-rate cooldown just like the CTRL key trigger.
     */
    public void requestShot() {
        long now = System.currentTimeMillis();
        long fireRate = (activeWeaponSlot >= 0 && weaponSlots[activeWeaponSlot] != null)
                      ? weaponSlots[activeWeaponSlot].fireRateMs : ATK_COOLDOWN_MS;
        if (now - lastAtkMs > fireRate) {
            boolean hasAmmo = true;
            if (activeWeaponSlot >= 0 && weaponSlots[activeWeaponSlot] != null) {
                if (weaponAmmo[activeWeaponSlot] <= 0) hasAmmo = false;
                else weaponAmmo[activeWeaponSlot]--;
            }
            if (hasAmmo) {
                lastAtkMs = now;
                pendingShot = true;
            }
        }
    }

    /** Called by Game.java each frame. Sets the key-pressed state for all input keys. */
    public static void setKeyPressed(int code, boolean pressed) {
        if (pressed) keysDown.add(code);
        else         keysDown.remove(code);
    }
    private static boolean key(int code) { return keysDown.contains(code); }

    /** Public key-state query for Game.java (ladder detection, etc.) */
    public static boolean isKeyDown(int keyCode) { return keysDown.contains(keyCode); }

    // =========================================================================
    //  Weapon System
    // =========================================================================
    public enum WeaponType {
        PISTOL  ("Pistol",  10, 30, 400),
        SMG     ("SMG",      6, 60, 150),
        RIFLE   ("Rifle",   25, 15, 700),
        SHOTGUN ("Shotgun", 35,  8, 900);

        public final String displayName;
        public final int    damage;
        public final int    maxAmmo;
        public final long   fireRateMs;

        WeaponType(String name, int damage, int maxAmmo, long fireRateMs) {
            this.displayName = name;
            this.damage      = damage;
            this.maxAmmo     = maxAmmo;
            this.fireRateMs  = fireRateMs;
        }
    }

    // =========================================================================
    //  Fields
    // =========================================================================
    private final CharacterType charType;

    // Position & physics
    private float x, y;
    private float velX = 0, velY = 0;
    private boolean grounded = false;
    private boolean facingRight = true;

    // Health
    private int health    = 100;
    private int maxHealth = 100;
    private boolean alive = true;

    // Cooldown timers
    private long lastDashMs   = -DASH_COOLDOWN_MS;
    private long lastAtkMs    = -ATK_COOLDOWN_MS;
    private long lastDmgMs    = -DMG_COOLDOWN_MS;

    // Animation
    private Map<AnimState, BufferedImage[]> frames = new HashMap<>();
    private AnimState state     = AnimState.IDLE;
    private AnimState prevState = null;
    private int   frameIdx   = 0;
    private float frameTimer = 0;

    // Pending projectile flag
    private boolean pendingShot = false;
    // Aim angle (radians) — set each frame from Game.java using mouse position
    private double aimAngle = 0.0;

    // ---- Heal system ----
    private long  lastHealTimeMs  = -5000L;
    private static final long HEAL_COOLDOWN_MS = 5000L;
    private static final int  HEAL_AMOUNT      = 20;

    // ---- Double jump ----
    private int jumpsRemaining = 2;

    // ---- Ladder climb ----
    private boolean onLadder = false;
    private static final float CLIMB_SPEED = 80f;

    // ---- Weapon inventory (4 slots) ----
    private WeaponType[] weaponSlots = new WeaponType[4];
    private int[]        weaponAmmo  = new int[4];
    private int          activeWeaponSlot = -1;  // -1 = no weapon

    // ---- Collectibles ----
    private int cashCollected  = 0;
    private int cardsCollected = 0;

    // =========================================================================
    //  Constructor
    // =========================================================================
    public PlayerBase(CharacterType type, float startX, float startY) {
        this.charType = type;
        this.x = startX;
        this.y = startY;
        loadSprites();
    }

    // =========================================================================
    //  Sprite loading
    // =========================================================================
    private void loadSprites() {
        String base = "Resources/industrial-zone/characters/player/"
                    + charType.folderName + "/";
        File dir = new File(base);
        if (!dir.exists()) {
            System.err.println("[PlayerBase] Sprite dir not found: " + base);
            return;
        }
        File[] files = dir.listFiles(f -> f.getName().toLowerCase().endsWith(".png"));
        if (files == null) return;

        // Map all 24 animation states to filename keywords + AnimState enum
        // Each entry: { filenameKeyword, AnimState }
        String[][] stateKeywords = {
            { "Idle_4Frames",   "Idle"        },   // 01 Idle (primary, 4 frames)
            { "Idle2",          "Idle2"       },   // 02 Idle2 (alternate, 6 frames)
            { "Walk_",          "Walk"        },   // 03 Walk
            { "Run_",           "Run"         },   // 04 Run
            { "Dash",           "Dash"        },   // 05 Dash
            { "Jump_",          "Jump"        },   // 06 Jump
            { "DoubleJump",     "DoubleJump"  },   // 07 Double Jump
            { "Fall",           "Fall"        },   // 08 Fall
            { "Climb",          "Climb"       },   // 09 Climb
            { "Hang",           "Hang"        },   // 10 Hang
            { "Pullup",         "Pullup"      },   // 11 Pullup
            { "Punch",          "Punch"       },   // 12 Punch
            { "Attack1",        "Attack1"     },   // 13 Attack1
            { "Attack2",        "Attack2"     },   // 14 Attack2
            { "Attack3",        "Attack3"     },   // 15 Attack3
            { "WalkAttack",     "WalkAttack"  },   // 16 Walk Attack
            { "RunAttack",      "RunAttack"   },   // 17 Run Attack
            { "Hurt",           "Hurt"        },   // 18 Hurt
            { "Death",          "Death"       },   // 19 Death
            { "Use",            "Use"         },   // 20 Use/Interact
            { "Sitdown",        "Sitdown"     },   // 21 Sitdown
            { "Angry",          "Angry"       },   // 22 Angry emote
            { "Happy",          "Happy"       },   // 23 Happy emote
            { "Talk",           "Talk"        },   // 24 Talk
        };
        AnimState[] stateMap = {
            AnimState.IDLE, AnimState.IDLE2, AnimState.WALK, AnimState.RUN,
            AnimState.DASH, AnimState.JUMP, AnimState.DOUBLE_JUMP, AnimState.FALL,
            AnimState.CLIMB, AnimState.HANG, AnimState.PULLUP, AnimState.PUNCH,
            AnimState.ATTACK1, AnimState.ATTACK2, AnimState.ATTACK3,
            AnimState.WALK_ATTACK, AnimState.RUN_ATTACK,
            AnimState.HIT, AnimState.DEATH,
            AnimState.USE, AnimState.SITDOWN, AnimState.ANGRY, AnimState.HAPPY, AnimState.TALK
        };

        for (int s = 0; s < stateKeywords.length; s++) {
            String keyword = stateKeywords[s][0];
            // Find a file whose name contains this keyword
            for (File f : files) {
                String name = f.getName();
                if (name.contains(keyword)) {
                    BufferedImage[] loaded = loadSheet(f.getPath());
                    if (loaded.length > 0) {
                        frames.put(stateMap[s], loaded);
                        System.out.printf("[PlayerBase] %s %-14s (%d frames)%n",
                            charType.folderName, stateKeywords[s][1], loaded.length);
                        break;
                    }
                }
            }
        }

        // Fallback: ensure IDLE exists (create a coloured placeholder if needed)
        if (!frames.containsKey(AnimState.IDLE)) {
            frames.put(AnimState.IDLE, new BufferedImage[]{makePlaceholder()});
        }

        System.out.println("[PlayerBase] Ready: " + charType.folderName);
    }

    /** Parse "6Frames" or "6Frames1Row" from filename → 6 */
    private int extractFrameCount(String name) {
        java.util.regex.Matcher m =
            java.util.regex.Pattern.compile("(\\d+)Frames", java.util.regex.Pattern.CASE_INSENSITIVE)
                                   .matcher(name);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
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
            System.err.println("[PlayerBase] Load error: " + path + " - " + e.getMessage());
            return new BufferedImage[0];
        }
    }

    private BufferedImage makePlaceholder() {
        BufferedImage img = new BufferedImage(SPRITE_W, SPRITE_H, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(0, 180, 255, 200));
        g.fillRect(0, 0, SPRITE_W, SPRITE_H);
        g.setColor(Color.WHITE);
        g.drawRect(1, 1, SPRITE_W - 2, SPRITE_H - 2);
        g.dispose();
        return img;
    }

    // =========================================================================
    //  Update (call with delta in SECONDS)
    // =========================================================================
    public void update(float delta) {
        if (!alive) {
            updateAnimation(delta);
            return;
        }

        handleInput(delta);
        applyPhysics(delta);
        resolveCollisions();
        updateStateMachine();
        updateAnimation(delta);
    }

    // -------------------------------------------------------------------------
    private void handleInput(float delta) {
        // ── Ladder climbing ──
        if (onLadder) {
            velX = 0;
            velY = 0;
            if (key(KeyEvent.VK_W) || key(KeyEvent.VK_UP))   velY = -CLIMB_SPEED;
            if (key(KeyEvent.VK_S) || key(KeyEvent.VK_DOWN))  velY =  CLIMB_SPEED;
            // SPACE detaches from ladder + jumps
            if (key(KeyEvent.VK_SPACE)) {
                onLadder = false;
                velY = JUMP_POWER;
                grounded = false;
                jumpsRemaining = 1;
            }
            setState(AnimState.CLIMB);
            return;  // skip normal movement while on ladder
        }

        int dir = 0;
        if (key(KeyEvent.VK_LEFT)  || key(KeyEvent.VK_A)) dir = -1;
        if (key(KeyEvent.VK_RIGHT) || key(KeyEvent.VK_D)) dir =  1;

        boolean running = key(KeyEvent.VK_SHIFT) && grounded && dir != 0
                         && (System.currentTimeMillis() - lastDashMs > DASH_COOLDOWN_MS);

        if (dir != 0) {
            float targetSpeed = running ? RUN_SPEED : WALK_SPEED;
            velX = dir * targetSpeed;
            facingRight = dir > 0;
        } else {
            velX *= 0.78f;
            if (Math.abs(velX) < 2f) velX = 0;
        }

        // Dash (SHIFT tap while moving)
        if (key(KeyEvent.VK_SHIFT) && dir != 0 && grounded
                && System.currentTimeMillis() - lastDashMs > DASH_COOLDOWN_MS) {
            velX = dir * DASH_SPEED;
            lastDashMs = System.currentTimeMillis();
            setState(AnimState.DASH);
        }

        // Jump / Double Jump
        if (key(KeyEvent.VK_SPACE) || key(KeyEvent.VK_UP) || key(KeyEvent.VK_W)) {
            if (grounded) {
                velY = JUMP_POWER;
                grounded = false;
                jumpsRemaining = 1;  // used first jump
            } else if (jumpsRemaining > 0) {
                velY = JUMP_POWER * 0.85f;  // second jump slightly weaker
                jumpsRemaining = 0;
                setState(AnimState.DOUBLE_JUMP);
            }
        }

        // Interact (E key)
        if (key(KeyEvent.VK_E) && grounded && state != AnimState.USE) {
            setState(AnimState.USE);
        }

        // Shoot / Attack (CTRL or left-mouse via requestShot())
        if (key(KeyEvent.VK_CONTROL)) {
            requestShot();
        }
        // Update attack animation state when pendingShot was just set
        if (pendingShot) {
            if (Math.abs(velX) > RUN_SPEED - 20 && grounded) {
                setState(AnimState.RUN_ATTACK);
            } else if (Math.abs(velX) > 5 && grounded) {
                setState(AnimState.WALK_ATTACK);
            } else {
                if (state == AnimState.ATTACK1) setState(AnimState.ATTACK2);
                else if (state == AnimState.ATTACK2) setState(AnimState.ATTACK3);
                else setState(AnimState.ATTACK1);
            }
        }
    }

    private void applyPhysics(float delta) {
        // Skip gravity while on ladder
        if (onLadder) {
            x += velX * delta;
            y += velY * delta;
            if (x < 0) { x = 0; velX = 0; }
            return;
        }

        // Gravity
        if (!grounded) {
            velY += GRAVITY * delta;
            if (velY > TERM_VELOCITY) velY = TERM_VELOCITY;
        }
        x += velX * delta;
        y += velY * delta;

        // World bounds (don't go left of origin)
        if (x < 0) { x = 0; velX = 0; }
    }

    // -------------------------------------------------------------------------
    //  Platform collision
    // -------------------------------------------------------------------------
    private void resolveCollisions() {
        grounded = false;

        for (float[] plat : platforms) {
            float px = plat[0], py = plat[1], pw = plat[2], ph = plat[3];

            // Horizontal overlap (shrink hitbox slightly)
            boolean hOverlap = (x + SPRITE_W - 6 > px) && (x + 6 < px + pw);
            if (!hOverlap) continue;

            float bottom = y + SPRITE_H;

            // Landing on top surface
            if (velY >= 0 && bottom >= py && bottom <= py + ph + 2) {
                y = py - SPRITE_H;
                velY = 0;
                grounded = true;
                jumpsRemaining = 2;  // reset double jump on landing
                break;
            }
        }

        // Fell out of the world → respawn on first platform at damage cost
        if (y > 1800) {
            takeDamage(20);
            if (platforms.length > 0) {
                y = platforms[0][1] - SPRITE_H;
                x = Math.max(x, 20);
            }
            velY = 0;
            grounded = true;
        }
    }

    // -------------------------------------------------------------------------
    //  Animation state machine (all 24 states)
    // -------------------------------------------------------------------------
    private void updateStateMachine() {
        // One-shot animations: let them finish before transitioning
        if (state == AnimState.DEATH) return;  // death is permanent
        if (state == AnimState.ATTACK1 || state == AnimState.ATTACK2 || state == AnimState.ATTACK3
            || state == AnimState.PUNCH || state == AnimState.WALK_ATTACK || state == AnimState.RUN_ATTACK
            || state == AnimState.HIT || state == AnimState.DASH
            || state == AnimState.USE || state == AnimState.DOUBLE_JUMP
            || state == AnimState.PULLUP) {
            BufferedImage[] f = currentFrames();
            if (f != null && frameIdx >= f.length - 1) {
                // One-shot finished → return to appropriate base state
                if (state == AnimState.HIT || state == AnimState.DASH || state == AnimState.USE
                    || state == AnimState.PULLUP || state == AnimState.DOUBLE_JUMP) {
                    setState(AnimState.IDLE);
                } else {
                    setState(AnimState.IDLE);
                }
            }
            return;
        }

        if (!grounded) {
            setState(velY < 0 ? AnimState.JUMP : AnimState.FALL);
        } else if (Math.abs(velX) > RUN_SPEED - 20) {
            setState(AnimState.RUN);
        } else if (Math.abs(velX) > 5) {
            setState(AnimState.WALK);
        } else {
            setState(AnimState.IDLE);
        }
    }

    private void setState(AnimState next) {
        if (state == next) return;
        state    = next;
        frameIdx = 0;
        frameTimer = 0;
    }

    private void updateAnimation(float delta) {
        if (state != prevState) {
            frameIdx   = 0;
            frameTimer = 0;
            prevState  = state;
        }

        BufferedImage[] f = currentFrames();
        if (f == null || f.length == 0) return;

        frameTimer += delta * 1000f;  // accumulate ms
        int delay = FRAME_DURATIONS[state.ordinal()];

        if (frameTimer >= delay) {
            frameTimer -= delay;
            frameIdx++;
            if (frameIdx >= f.length) {
                frameIdx = 0;  // loop
            }
        }
    }

    private BufferedImage[] currentFrames() {
        // Try exact state first
        if (frames.containsKey(state)) return frames.get(state);
        // Smart fallbacks for missing animations
        switch (state) {
            case IDLE2:        return frames.getOrDefault(AnimState.IDLE, null);
            case RUN:          return frames.getOrDefault(AnimState.WALK, null);
            case DASH:         return frames.getOrDefault(AnimState.RUN, frames.getOrDefault(AnimState.WALK, null));
            case DOUBLE_JUMP:  return frames.getOrDefault(AnimState.JUMP, null);
            case CLIMB:        return frames.getOrDefault(AnimState.WALK, null);
            case HANG:         return frames.getOrDefault(AnimState.IDLE, null);
            case PULLUP:       return frames.getOrDefault(AnimState.JUMP, null);
            case PUNCH:        return frames.getOrDefault(AnimState.ATTACK1, null);
            case ATTACK2:      return frames.getOrDefault(AnimState.ATTACK1, null);
            case ATTACK3:      return frames.getOrDefault(AnimState.ATTACK1, null);
            case WALK_ATTACK:  return frames.getOrDefault(AnimState.ATTACK1, null);
            case RUN_ATTACK:   return frames.getOrDefault(AnimState.ATTACK1, null);
            case USE:          return frames.getOrDefault(AnimState.IDLE, null);
            case SITDOWN:      return frames.getOrDefault(AnimState.IDLE, null);
            case ANGRY:        return frames.getOrDefault(AnimState.IDLE, null);
            case HAPPY:        return frames.getOrDefault(AnimState.IDLE, null);
            case TALK:         return frames.getOrDefault(AnimState.IDLE, null);
            default: break;
        }
        // Last resort: IDLE or first available
        if (frames.containsKey(AnimState.IDLE)) return frames.get(AnimState.IDLE);
        for (BufferedImage[] arr : frames.values()) return arr;
        return null;
    }

    // =========================================================================
    //  Render
    // =========================================================================
    public void render(Graphics2D g, int camX, int camY) {
        BufferedImage[] f = currentFrames();
        if (f == null || f.length == 0) return;

        int idx = Math.min(frameIdx, f.length - 1);
        BufferedImage img = f[idx];
        if (img == null) return;

        int sx = (int)(x - camX);
        int sy = (int)(y - camY);

        // Flip horizontally when facing left
        if (!facingRight) {
            g.drawImage(img, sx + SPRITE_W, sy, -SPRITE_W, SPRITE_H, null);
        } else {
            g.drawImage(img, sx, sy, SPRITE_W, SPRITE_H, null);
        }

        drawHealthBar(g, sx, sy);
    }

    private void drawHealthBar(Graphics2D g, int sx, int sy) {
        int bw = SPRITE_W;
        int bh = 5;
        int by = sy - 10;

        g.setColor(new Color(30, 60, 30));
        g.fillRect(sx, by, bw, bh);

        float ratio = (float) health / maxHealth;
        Color fill = ratio > 0.5f ? new Color(0, 200, 60)
                   : ratio > 0.25f ? new Color(220, 180, 0)
                   : new Color(220, 40, 40);
        g.setColor(fill);
        g.fillRect(sx, by, (int)(bw * ratio), bh);

        g.setColor(new Color(80, 120, 80));
        g.drawRect(sx, by, bw, bh);
    }

    // =========================================================================
    //  Combat
    // =========================================================================
    public void takeDamage(int dmg) {
        if (!alive) return;
        long now = System.currentTimeMillis();
        if (now - lastDmgMs < DMG_COOLDOWN_MS) return;
        lastDmgMs = now;
        health = Math.max(0, health - dmg);
        System.out.println("[Player] Took " + dmg + " damage. HP: " + health);
        if (health <= 0) {
            alive = false;
            setState(AnimState.DEATH);
        } else {
            setState(AnimState.HIT);
            velY = -180f;  // knockback
        }
    }

    /**
     * Returns a Projectile if the player just fired, null otherwise.
     * Called once per frame from Game.update().
     */
    public Projectile getProjectileToFire() {
        if (!pendingShot) return null;
        pendingShot = false;

        float projX = facingRight ? x + SPRITE_W : x - 10f;
        float projY = y + SPRITE_H / 2f - 3f;
        // Use aim angle so bullet travels toward the mouse cursor
        float speed = 550f;
        float projVX = (float)(Math.cos(aimAngle) * speed);
        float projVY = (float)(Math.sin(aimAngle) * speed);
        float dmg = 12f;
        if (activeWeaponSlot >= 0 && weaponSlots[activeWeaponSlot] != null) {
            dmg = weaponSlots[activeWeaponSlot].damage;
        }
        return new Projectile(projX, projY, projVX, projVY, dmg, 2.5f);
    }

    // =========================================================================
    //  Setters / Getters
    // =========================================================================
    /** Move player to an absolute world position. */
    public void setPosition(float nx, float ny) { x = nx; y = ny; }

    public float getX()          { return x; }
    public float getY()          { return y; }
    public boolean isFacingRight(){ return facingRight; }
    public float getVelocityX()  { return velX; }
    public float getVelocityY() { return velY; }
    public int   getHealth()    { return health; }
    public int   getMaxHealth() { return maxHealth; }
    public boolean isAlive()    { return alive; }
    public boolean isGrounded() { return grounded; }

    /** Alias kept for compatibility */
    public float getVelX() { return velX; }
    public float getVelY() { return velY; }
    public CharacterType getCharacterType() { return charType; }
    public AnimState     getAnimationState() { return state; }

    // =========================================================================
    //  Heal System
    // =========================================================================
    public boolean tryHeal() {
        long now = System.currentTimeMillis();
        if (now - lastHealTimeMs < HEAL_COOLDOWN_MS) return false;
        if (health >= maxHealth) return false;
        if (!alive) return false;
        lastHealTimeMs = now;
        health = Math.min(maxHealth, health + HEAL_AMOUNT);
        System.out.println("[Player] Healed +" + HEAL_AMOUNT + " HP → " + health);
        return true;
    }

    public float getHealCooldownPct() {
        long elapsed = System.currentTimeMillis() - lastHealTimeMs;
        if (elapsed >= HEAL_COOLDOWN_MS) return 1f;
        return (float) elapsed / HEAL_COOLDOWN_MS;
    }

    // =========================================================================
    //  Ladder
    // =========================================================================
    public void setOnLadder(boolean val) { onLadder = val; }
    public boolean isOnLadder() { return onLadder; }

    // =========================================================================
    //  Weapon Inventory
    // =========================================================================
    public boolean pickupWeapon(WeaponType weapon) {
        for (int i = 0; i < weaponSlots.length; i++) {
            if (weaponSlots[i] == weapon) {
                weaponAmmo[i] = weapon.maxAmmo;  // refill
                return true;
            }
        }
        for (int i = 0; i < weaponSlots.length; i++) {
            if (weaponSlots[i] == null) {
                weaponSlots[i] = weapon;
                weaponAmmo[i]  = weapon.maxAmmo;
                if (activeWeaponSlot < 0) activeWeaponSlot = i;
                return true;
            }
        }
        return false;  // all 4 slots full
    }

    public void switchWeaponSlot(int slot) {
        if (slot >= 0 && slot < 4 && weaponSlots[slot] != null) {
            activeWeaponSlot = slot;
        }
    }

    public void cycleWeaponNext() {
        if (activeWeaponSlot < 0) return;
        for (int i = 1; i <= 4; i++) {
            int idx = (activeWeaponSlot + i) % 4;
            if (weaponSlots[idx] != null) { activeWeaponSlot = idx; return; }
        }
    }

    public void cycleWeaponPrev() {
        if (activeWeaponSlot < 0) return;
        for (int i = 1; i <= 4; i++) {
            int idx = (activeWeaponSlot - i + 4) % 4;
            if (weaponSlots[idx] != null) { activeWeaponSlot = idx; return; }
        }
    }

    public WeaponType   getActiveWeapon()    { return activeWeaponSlot >= 0 ? weaponSlots[activeWeaponSlot] : null; }
    public int          getActiveAmmo()      { return activeWeaponSlot >= 0 ? weaponAmmo[activeWeaponSlot]  : 0; }
    public WeaponType[] getWeaponSlots()     { return weaponSlots; }
    public int[]        getWeaponAmmo()      { return weaponAmmo; }
    public int          getActiveWeaponSlot(){ return activeWeaponSlot; }

    // =========================================================================
    //  Collectibles
    // =========================================================================
    public void addCash(int amount) { cashCollected += amount; }
    public void addCard()           { cardsCollected++; }
    public void spendCards(int n)   { cardsCollected = Math.max(0, cardsCollected - n); }
    public int  getCash()           { return cashCollected; }
    public int  getCards()          { return cardsCollected; }
}
