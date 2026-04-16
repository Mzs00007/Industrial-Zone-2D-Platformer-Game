package entities;

import animation.HorizontalSpritesheetLoader;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * AnimatedObject - Animated world objects (collectibles, chests, conveyors, portals,
 * screens, hazards, moving platforms).
 *
 * Loads a spritesheet from file, auto-detects frame count/duration from filename,
 * and provides update/render/interact methods.
 */
public class AnimatedObject {

    // =========================================================================
    //  Object types  (rules: score, loop, auto-collect)
    //
    //  OBJECT-INTERACTION CONTRACT (authoritative rules — keep in sync):
    //
    //   TYPE                | COLLISION | REACT TO PLAYER        | SFX
    //   --------------------+-----------+------------------------+--------------
    //   COLLECTIBLE_CARD    | AABB      | auto-pickup, +1 card   | unlocked_chest
    //   COLLECTIBLE_MONEY   | AABB      | auto-pickup, +cash     | click_digital_1
    //   CHEST               | AABB      | E-key opens → spawns 1 | unlocked_chest
    //                       |           | COLLECTIBLE_CARD above |
    //   CONVEYOR            | AABB      | pushes player +100 px/s| (ambient)
    //   CONVEYOR_REVERSE    | AABB      | pushes player −100 px/s| (ambient)
    //   PORTAL              | AABB      | checkpoint save + heal | portal_1
    //   SCREEN_DECO         | AABB-vis  | none (decor)           | (none)
    //   HAZARD_HAMMER       | AABB      | damage 15 when frameIdx| impact_hit
    //                       |           | in [2..4] (mid-swing)  |
    //   HAZARD_TURRET       | AABB      | damage 20 when frameIdx| turret_fire
    //                       |           | ≥ frames/2 (fire half) |
    //   MOVING_PLATFORM     | visual    | rideable (future work) | (ambient)
    //
    //  INVARIANTS enforced by Game.startGame():
    //    • # CHEST    == # PORTAL   (boxes == checkpoints)
    //    • # CHEST    == # cards the player CAN collect in this level
    //      (no pre-placed COLLECTIBLE_CARD entries; every card comes from
    //      opening a chest, so cardsExpected climbs from 0 → chestCount.)
    // =========================================================================
    public enum ObjType {
        COLLECTIBLE_CARD  (50,   true,  true),
        COLLECTIBLE_MONEY (25,   true,  true),
        CHEST             (100,  false, false),
        CONVEYOR          (0,    true,  false),
        CONVEYOR_REVERSE  (0,    true,  false),
        PORTAL            (0,    true,  false),
        SCREEN_DECO       (0,    true,  false),
        HAZARD_HAMMER     (15,   true,  false),
        HAZARD_TURRET     (20,   true,  false),
        MOVING_PLATFORM   (0,    true,  false);

        public final int scoreValue;
        public final boolean loops;
        public final boolean autoCollect;
        ObjType(int score, boolean loops, boolean autoCollect) {
            this.scoreValue = score; this.loops = loops; this.autoCollect = autoCollect;
        }
    }

    // =========================================================================
    //  Fields
    // =========================================================================
    private ObjType type;
    private float x, y;
    private int width, height;
    private boolean active = true;
    private boolean opened = false;

    // Animation
    private BufferedImage[] frames;
    private int frameIdx = 0;
    private float frameTimer = 0;
    private float frameDurationMs;
    private boolean animFinished = false;

    // Conveyor push speed (pixels per second)
    private float conveyorSpeed = 80f;

    // Moving platform range and direction
    private float moveRange = 200f;
    private float moveSpeed = 60f;
    private float originX;
    private int moveDir = 1;

    // =========================================================================
    //  Constructor
    // =========================================================================
    public AnimatedObject(ObjType type, String filePath, float x, float y,
                          int renderW, int renderH) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = renderW;
        this.height = renderH;
        this.originX = x;
        loadSpritesheet(filePath);
    }

    // =========================================================================
    //  Spritesheet loading
    // =========================================================================
    private void loadSpritesheet(String path) {
        frameDurationMs = extractDuration(path);
        if (frameDurationMs <= 0) frameDurationMs = 100;

        try {
            BufferedImage sheet = ImageIO.read(new File(path));
            if (sheet != null) {
                int fc = extractFrameCount(path);
                if (fc <= 0) {
                    int iw = sheet.getWidth(), ih = sheet.getHeight();
                    if (ih > 0 && iw > ih && iw % ih == 0) fc = iw / ih;
                    else fc = 1;
                }
                int fw = sheet.getWidth() / fc;
                int fh = sheet.getHeight();
                frames = new BufferedImage[fc];
                for (int i = 0; i < fc; i++) {
                    frames[i] = sheet.getSubimage(i * fw, 0, fw, fh);
                }
                System.out.printf("[AnimObj] %s: %d frames at %.0fms - %s%n",
                    type, fc, frameDurationMs, new File(path).getName());
            }
        } catch (Exception e) {
            System.err.println("[AnimObj] Load failed: " + path + " - " + e.getMessage());
        }

        if (frames == null || frames.length == 0) {
            frames = new BufferedImage[0];
            active = false;
        }
    }

    private int extractFrameCount(String name) {
        java.util.regex.Matcher m =
            java.util.regex.Pattern.compile("(\\d+)Frames",
                java.util.regex.Pattern.CASE_INSENSITIVE).matcher(name);
        return m.find() ? Integer.parseInt(m.group(1)) : -1;
    }

    private float extractDuration(String name) {
        java.util.regex.Matcher m =
            java.util.regex.Pattern.compile("(\\d+)ms",
                java.util.regex.Pattern.CASE_INSENSITIVE).matcher(name);
        return m.find() ? Float.parseFloat(m.group(1)) : -1;
    }

    // =========================================================================
    //  Update
    // =========================================================================
    public void update(float delta) {
        if (!active || frames.length == 0) return;

        frameTimer += delta * 1000f;
        if (frameTimer >= frameDurationMs) {
            frameTimer -= frameDurationMs;
            frameIdx++;
            if (frameIdx >= frames.length) {
                if (type.loops) {
                    frameIdx = 0;
                } else {
                    frameIdx = frames.length - 1;
                    animFinished = true;
                }
            }
        }

        if (type == ObjType.MOVING_PLATFORM) {
            x += moveSpeed * moveDir * delta;
            if (Math.abs(x - originX) > moveRange) {
                moveDir *= -1;
                x = originX + moveRange * moveDir;
            }
        }
    }

    // =========================================================================
    //  Render
    // =========================================================================
    public void render(Graphics2D g, float cameraX, float cameraY) {
        if (!active || frames.length == 0) return;

        int idx = Math.min(frameIdx, frames.length - 1);
        BufferedImage img = frames[idx];
        if (img == null) return;

        int sx = (int)(x - cameraX);
        int sy = (int)(y - cameraY);

        if (sx + width < 0 || sx > 1600 || sy + height < 0 || sy > 900) return;

        g.drawImage(img, sx, sy, width, height, null);
    }

    // =========================================================================
    //  Interaction
    // =========================================================================
    public boolean overlaps(float px, float py, float pw, float ph) {
        return active && px + pw > x && px < x + width
            && py + ph > y && py < y + height;
    }

    public int collect() {
        if (!active) return 0;
        if (type.autoCollect) {
            active = false;
            return type.scoreValue;
        }
        return 0;
    }

    public int interact() {
        if (!active) return 0;
        if (type == ObjType.CHEST && !opened) {
            opened = true;
            frameIdx = 0;
            frameTimer = 0;
            return type.scoreValue;
        }
        return 0;
    }

    public boolean isDamaging() {
        if (type == ObjType.HAZARD_HAMMER) {
            return frameIdx >= 2 && frameIdx <= 4;
        }
        if (type == ObjType.HAZARD_TURRET) {
            return frameIdx >= frames.length / 2;
        }
        return false;
    }

    public int getDamage() { return type.scoreValue; }

    public float getConveyorPush() {
        if (type == ObjType.CONVEYOR) return conveyorSpeed;
        if (type == ObjType.CONVEYOR_REVERSE) return -conveyorSpeed;
        return 0;
    }

    public boolean isChestOpened() { return type == ObjType.CHEST && opened && animFinished; }

    // =========================================================================
    //  Getters / Setters
    // =========================================================================
    public ObjType getType()    { return type; }
    public float getX()         { return x; }
    public float getY()         { return y; }
    public int getWidth()       { return width; }
    public int getHeight()      { return height; }
    public boolean isActive()   { return active; }
    public void setActive(boolean a) { this.active = a; }
    public void setConveyorSpeed(float s) { this.conveyorSpeed = s; }
    public void setMoveRange(float r) { this.moveRange = r; }
    public void setMoveSpeed(float s) { this.moveSpeed = s; }
}
