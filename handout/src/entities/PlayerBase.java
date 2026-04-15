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

    public enum AnimState { IDLE, WALK, RUN, JUMP, FALL, ATTACK, HIT, DEATH }

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

    // Frame durations (ms)
    private static final int[] FRAME_DURATIONS = {
        160,  // IDLE
        100,  // WALK
        75,   // RUN
        90,   // JUMP
        100,  // FALL
        65,   // ATTACK
        100,  // HIT
        180   // DEATH
    };

    // =========================================================================
    //  Static: platform data set by Game.java; input key set
    // =========================================================================
    private static float[][]   platforms = new float[0][0];
    private static Set<Integer> keysDown = new HashSet<>();

    /** Called by Game.java before creating the player. */
    public static void setPlatforms(float[][] data) { platforms = data; }

    public static void setKeyPressed(int code, boolean pressed) {
        if (pressed) keysDown.add(code);
        else         keysDown.remove(code);
    }
    private static boolean key(int code) { return keysDown.contains(code); }

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

        // Map animation-name keywords to our AnimState enum
        String[][] prefixes = {
            { "Idle",   "idle"   },
            { "Walk",   "walk"   },
            { "Run",    "run"    },
            { "Jump",   "jump"   },
            { "Fall",   "fall"   },
            { "Attack", "attack" },
            { "Hurt",   "hurt"   },
            { "Death",  "death"  }
        };
        AnimState[] stateMap = {
            AnimState.IDLE, AnimState.WALK, AnimState.RUN,
            AnimState.JUMP, AnimState.FALL, AnimState.ATTACK,
            AnimState.HIT,  AnimState.DEATH
        };

        for (int s = 0; s < prefixes.length; s++) {
            String keyword = prefixes[s][0];
            // Find a file whose name contains this keyword (case-insensitive)
            for (File f : files) {
                String name = f.getName();
                if (name.contains(keyword) || name.contains(keyword.toLowerCase())) {
                    int cols = extractFrameCount(name);
                    if (cols <= 0) cols = 1;
                    BufferedImage[] loaded = loadSheet(f.getPath(), cols);
                    if (loaded.length > 0) {
                        frames.put(stateMap[s], loaded);
                        System.out.printf("[PlayerBase] %s %-8s  (%d frames)%n",
                            charType.folderName, keyword, loaded.length);
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

    private BufferedImage[] loadSheet(String path, int cols) {
        try {
            BufferedImage sheet = ImageIO.read(new File(path));
            if (sheet == null) return new BufferedImage[0];
            int fw = sheet.getWidth() / cols;
            int fh = sheet.getHeight();
            BufferedImage[] out = new BufferedImage[cols];
            for (int c = 0; c < cols; c++) {
                out[c] = sheet.getSubimage(c * fw, 0, fw, fh);
            }
            return out;
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

        // Dash (SHIFT tap)
        if (key(KeyEvent.VK_SHIFT) && dir != 0 && grounded
                && System.currentTimeMillis() - lastDashMs > DASH_COOLDOWN_MS) {
            velX = dir * DASH_SPEED;
            lastDashMs = System.currentTimeMillis();
        }

        // Jump
        if ((key(KeyEvent.VK_SPACE) || key(KeyEvent.VK_UP) || key(KeyEvent.VK_W))
                && grounded) {
            velY = JUMP_POWER;
            grounded = false;
        }

        // Shoot / Attack (CTRL)
        if (key(KeyEvent.VK_CONTROL)
                && System.currentTimeMillis() - lastAtkMs > ATK_COOLDOWN_MS) {
            lastAtkMs = System.currentTimeMillis();
            pendingShot = true;
            setState(AnimState.ATTACK);
        }
    }

    private void applyPhysics(float delta) {
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
    //  Animation state machine
    // -------------------------------------------------------------------------
    private void updateStateMachine() {
        if (state == AnimState.DEATH || state == AnimState.ATTACK || state == AnimState.HIT) {
            // One-shot animations: let them finish
            BufferedImage[] f = currentFrames();
            if (f != null && frameIdx >= f.length - 1) {
                if (state == AnimState.ATTACK || state == AnimState.HIT)
                    setState(AnimState.IDLE);
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
        // Try exact state, then common fallbacks
        if (frames.containsKey(state))     return frames.get(state);
        if (frames.containsKey(AnimState.IDLE)) return frames.get(AnimState.IDLE);
        // Last resort: first available
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
        float projVX = facingRight ? 520f : -520f;
        float projVY = -40f;
        return new Projectile(projX, projY, projVX, projVY, 12f, 2.5f);
    }

    // =========================================================================
    //  Setters / Getters
    // =========================================================================
    /** Move player to an absolute world position. */
    public void setPosition(float nx, float ny) { x = nx; y = ny; }

    public float getX()         { return x; }
    public float getY()         { return y; }
    public float getVelocityX() { return velX; }
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
}
