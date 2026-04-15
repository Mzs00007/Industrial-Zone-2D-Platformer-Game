/*
 * rendering/VfxSystem.java
 *
 * Self-contained VFX particle system for the Industrial Zone game.
 * Manages two particle types:
 *   TYPE_SPARK  – teal/cyan click sparks and red enemy-hit sparks.
 *                 These are tiny coloured rectangles with gravity + alpha fade.
 *   TYPE_SMOKE  – 18-frame sprite animation played at 80 ms/frame.
 *                 Frames are individual PNGs inside Resources/.../vfx/1 Smoke/.
 *
 * Usage from Game.java:
 *   VfxSystem vfx = new VfxSystem();          // constructor loads smoke frames
 *   vfx.emitSparks(mouseX, mouseY);           // on button click
 *   vfx.emitHitSparks(enemy.getX(), ...);     // on enemy damage
 *   vfx.emitSmoke(enemy.getX(), ...);         // on enemy death
 *   vfx.update(elapsedMs);                    // every tick
 *   vfx.draw(g);                              // at top of every frame
 */
package rendering;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

public final class VfxSystem {

    // ── constants ─────────────────────────────────────────────────────────────
    /** Particle type: tiny coloured rectangle with gravity and alpha fade. */
    public static final int TYPE_SPARK = 0;
    /** Particle type: animated smoke sprite from 18-frame sequence. */
    public static final int TYPE_SMOKE = 1;

    /** Particle type: animated blood splatter (4-frame, play-once). */
    public static final int TYPE_BLOOD = 2;

    /** Directory containing the 18 individual smoke-frame PNGs. */
    private static final String SMOKE_DIR =
        "Resources/industrial-zone/vfx/1 Smoke/";

    /** Directory containing the 8 blood splatter spritesheets. */
    private static final String BLOOD_DIR =
        "Resources/industrial-zone/vfx/2 Blood/";

    /** Number of blood splatter variants (8 sheets). */
    private static final int BLOOD_SHEET_COUNT = 8;

    /** Frames per blood sheet (192×48 → 4 square 48px frames). */
    private static final int BLOOD_FRAMES = 4;

    /** Time to advance one blood frame (ms). */
    private static final int BLOOD_FRAME_MS = 80;

    /** Total smoke animation frames (numbered 01-18). */
    private static final int SMOKE_FRAMES = 18;

    /** Time to advance one smoke frame (ms) — matches the filename "80ms". */
    private static final int SMOKE_FRAME_MS = 80;

    /** Gravity applied to sparks (px / s²). */
    private static final float GRAVITY = 280f;

    // ── smoke asset storage ───────────────────────────────────────────────────
    /** Individual smoke frames loaded from the vfx/1 Smoke/ folder. */
    private final BufferedImage[] smokeFrames = new BufferedImage[SMOKE_FRAMES];

    /** Set to true once smoke frames were successfully loaded from disk. */
    private boolean smokeLoaded = false;

    // ── blood asset storage ───────────────────────────────────────────────────
    /**
     * 8 full blood spritesheet images, each 192×48 px (4 frames of 48×48).
     * Loaded from BloodVfxAssets paths.
     */
    private final BufferedImage[] bloodSheets = new BufferedImage[BLOOD_SHEET_COUNT];

    /** Set to true once at least one blood sheet loaded successfully. */
    private boolean bloodLoaded = false;

    // ── live particle list ────────────────────────────────────────────────────
    private final List<Particle> particles = new ArrayList<>();
    private final Random rng = new Random();

    // =========================================================================
    //  CONSTRUCTOR
    // =========================================================================

    /**
     * Constructs the VfxSystem and immediately tries to load all smoke frames.
     * Failures are logged loudly (filename + reason) and skipped gracefully —
     * the game still runs; smoke just won't appear.
     */
    public VfxSystem() {
        loadSmokeFrames();
        loadBloodSheets();
    }

    /** Loads VFX_Smoke_Frame01..18 PNGs from the vfx/1 Smoke/ folder. */
    private void loadSmokeFrames() {
        // Frame filenames follow the naming in assets-manifest.json:
        // NN_VFX_Smoke_FrameNN_<description>_SmokeAnim_Loop_80ms.png
        String[] frameNames = {
            "01_VFX_Smoke_Frame01_DenseThickCloud_SmokeAnim_Loop_80ms.png",
            "02_VFX_Smoke_Frame02_DenseCloud_SmokeAnim_Loop_80ms.png",
            "03_VFX_Smoke_Frame03_DenseCloud_SmokeAnim_Loop_80ms.png",
            "04_VFX_Smoke_Frame04_DenseWithWisps_SmokeAnim_Loop_80ms.png",
            "05_VFX_Smoke_Frame05_ThinnerCloud_SmokeAnim_Loop_80ms.png",
            "06_VFX_Smoke_Frame06_MediumCloud_SmokeAnim_Loop_80ms.png",
            "07_VFX_Smoke_Frame07_FineWisp_SmokeAnim_Loop_80ms.png",
            "08_VFX_Smoke_Frame08_SmallWisp_SmokeAnim_Loop_80ms.png",
            "09_VFX_Smoke_Frame09_FadingWisp_SmokeAnim_Loop_80ms.png",
            "10_VFX_Smoke_Frame10_TinyCloud_SmokeAnim_Loop_80ms.png",
            "11_VFX_Smoke_Frame11_LightWisp_SmokeAnim_Loop_80ms.png",
            "12_VFX_Smoke_Frame12_VeryLight_SmokeAnim_Loop_80ms.png",
            "13_VFX_Smoke_Frame13_AlmostGone_SmokeAnim_Loop_80ms.png",
            "14_VFX_Smoke_Frame14_FadingOut_SmokeAnim_Loop_80ms.png",
            "15_VFX_Smoke_Frame15_VerySparse_SmokeAnim_Loop_80ms.png",
            "16_VFX_Smoke_Frame16_NearlyGone_SmokeAnim_Loop_80ms.png",
            "17_VFX_Smoke_Frame17_LastWisp_SmokeAnim_Loop_80ms.png",
            "18_VFX_Smoke_Frame18_Empty_SmokeAnim_Loop_80ms.png"
        };

        int loaded = 0;
        for (int i = 0; i < SMOKE_FRAMES; i++) {
            // Build path relative to the handout/ working directory
            String path = SMOKE_DIR + frameNames[i];
            try {
                File f = new File(path);
                if (f.exists()) {
                    smokeFrames[i] = ImageIO.read(f);
                    loaded++;
                } else {
                    // Try a fallback: scan the folder for a file that starts with the NN_ prefix
                    smokeFrames[i] = loadSmokeFallback(i + 1);
                    if (smokeFrames[i] != null) loaded++;
                    else System.err.println("[VfxSystem] Smoke frame NOT FOUND: " + path);
                }
            } catch (Exception ex) {
                System.err.println("[VfxSystem] Error loading smoke frame " + path + ": " + ex.getMessage());
            }
        }
        smokeLoaded = (loaded > 0);
        System.out.println("[VfxSystem] Smoke frames loaded: " + loaded + "/" + SMOKE_FRAMES
                           + (smokeLoaded ? " ✓" : " ✗ smoke VFX disabled"));
    }

    /**
     * Fallback: scan the smoke directory for any file starting with the given
     * two-digit frame number ("NN_"). Handles varying filenames gracefully.
     */
    private BufferedImage loadSmokeFallback(int frameNum) {
        File dir = new File(SMOKE_DIR);
        if (!dir.exists()) return null;
        String prefix = String.format("%02d_", frameNum);
        File[] files = dir.listFiles((d, name) -> name.startsWith(prefix) && name.endsWith(".png"));
        if (files != null && files.length > 0) {
            try { return ImageIO.read(files[0]); }
            catch (Exception ex) { /* fall through */ }
        }
        return null;
    }

    /** Loads the 8 blood splatter spritesheets from BloodVfxAssets paths. */
    private void loadBloodSheets() {
        String[] filenames = {
            "01_VFX_Blood_Splatter_4Frames1Row_SmallWideSpread_Impact_PlayOnce_80ms.png",
            "02_VFX_Blood_Splatter_4Frames1Row_SmallLooseParticles_Impact_PlayOnce_80ms.png",
            "03_VFX_Blood_Splatter_4Frames1Row_MediumBlobShapes_Impact_PlayOnce_80ms.png",
            "04_VFX_Blood_Splatter_4Frames1Row_TinyFineDots_Impact_PlayOnce_80ms.png",
            "05_VFX_Blood_Splatter_4Frames1Row_MediumChunks_Impact_PlayOnce_80ms.png",
            "06_VFX_Blood_Splatter_4Frames1Row_LargeBoldImpact_Impact_PlayOnce_80ms.png",
            "07_VFX_Blood_Splatter_4Frames1Row_HorizontalElongated_Impact_PlayOnce_80ms.png",
            "08_VFX_Blood_Splatter_4Frames1Row_ArcSwipeShape_Impact_PlayOnce_80ms.png"
        };
        int loaded = 0;
        for (int i = 0; i < BLOOD_SHEET_COUNT; i++) {
            String path = BLOOD_DIR + filenames[i];
            try {
                File f = new File(path);
                if (f.exists()) {
                    bloodSheets[i] = ImageIO.read(f);
                    loaded++;
                } else {
                    System.err.println("[VfxSystem] Blood sheet not found: " + path);
                }
            } catch (Exception ex) {
                System.err.println("[VfxSystem] Error loading blood sheet " + path + ": " + ex.getMessage());
            }
        }
        bloodLoaded = loaded > 0;
        System.out.println("[VfxSystem] Blood sheets loaded: " + loaded + "/" + BLOOD_SHEET_COUNT);
    }

    // =========================================================================
    //  PUBLIC EMIT API
    // =========================================================================

    /**
     * Emits a burst of 10–15 teal/cyan spark particles at screen position (px, py).
     * Call this from handleClick() for every button press.
     */
    public void emitSparks(int px, int py) {
        int count = 10 + rng.nextInt(6);
        for (int i = 0; i < count; i++) {
            double angle = rng.nextDouble() * Math.PI * 2;
            float  speed = 60f + rng.nextFloat() * 130f;
            float  life  = 280f + rng.nextFloat() * 420f;
            // teal/cyan colour palette to match the industrial-zone UI
            int g = 160 + rng.nextInt(80);
            int b = 200 + rng.nextInt(55);
            particles.add(Particle.spark(px, py,
                (float) Math.cos(angle) * speed,
                (float) Math.sin(angle) * speed - 25f,
                life, 0, g, b));
        }
    }

    /**
     * Emits 5–8 orange/red hit sparks at (px, py).
     * Call this when a projectile hits an enemy.
     */
    public void emitHitSparks(float px, float py) {
        int count = 5 + rng.nextInt(4);
        for (int i = 0; i < count; i++) {
            double angle = rng.nextDouble() * Math.PI * 2;
            float  speed = 40f + rng.nextFloat() * 80f;
            float  life  = 180f + rng.nextFloat() * 200f;
            int r = 220 + rng.nextInt(35);
            int g = 60  + rng.nextInt(60);
            particles.add(Particle.spark(px, py,
                (float) Math.cos(angle) * speed,
                (float) Math.sin(angle) * speed - 15f,
                life, r, g, 0));
        }
    }

    /**
     * Emits an 18-frame smoke animation at (px, py) in world space.
     * Pass in the camera offset so the smoke draws at the correct screen position.
     * Call this on enemy death.
     */
    public void emitSmoke(float worldX, float worldY) {
        if (!smokeLoaded) return;  // no frames → skip silently
        particles.add(Particle.smoke(worldX, worldY));
    }

    /**
     * Emits a 4-frame blood splatter animation at screen position (sx, sy).
     * Picks a random variant from the 8 BloodVfxAssets sheets.
     * Call this when a projectile hits an enemy (pass screen coordinates).
     */
    public void emitBlood(float sx, float sy) {
        if (!bloodLoaded) return;
        // Pick a non-null sheet randomly
        int attempts = 0;
        int idx;
        do {
            idx = rng.nextInt(BLOOD_SHEET_COUNT);
            attempts++;
        } while (bloodSheets[idx] == null && attempts < BLOOD_SHEET_COUNT);
        if (bloodSheets[idx] == null) return;
        particles.add(Particle.blood(sx, sy, idx));
    }

    /**
     * Fires a burst of 3 teal sparks centred above a GUI panel.
     * Used by Game.java when the "GAME OVER" typewriter completes.
     */
    public void emitGameOverBurst(int centreX, int top) {
        for (int k = -1; k <= 1; k++) {
            emitSparks(centreX + k * 60, top);
        }
    }

    // =========================================================================
    //  UPDATE  (call every tick)
    // =========================================================================

    /**
     * Advances all live particles by elapsedMs milliseconds.
     * Removes particles whose lifetime has expired.
     *
     * @param elapsedMs milliseconds elapsed since last tick
     */
    public void update(long elapsedMs) {
        float dt = elapsedMs / 1000f;
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);

            if (p.type == TYPE_SPARK) {
                // apply velocity and gravity to spark position
                p.x  += p.vx * dt;
                p.y  += p.vy * dt;
                p.vy += GRAVITY * dt;
                p.life -= elapsedMs;
                if (p.life <= 0f) particles.remove(i);

            } else if (p.type == TYPE_BLOOD) {
                // advance blood frame counter; remove after all 4 frames play
                p.frameTimer += elapsedMs;
                if (p.frameTimer >= BLOOD_FRAME_MS) {
                    p.frameTimer -= BLOOD_FRAME_MS;
                    p.frame++;
                }
                if (p.frame >= BLOOD_FRAMES) particles.remove(i);

            } else {
                // advance smoke frame counter
                p.frameTimer += elapsedMs;
                if (p.frameTimer >= SMOKE_FRAME_MS) {
                    p.frameTimer -= SMOKE_FRAME_MS;
                    p.frame++;
                }
                if (p.frame >= SMOKE_FRAMES) particles.remove(i);
            }
        }
    }

    // =========================================================================
    //  DRAW  (call last — renders on top of everything)
    // =========================================================================

    /**
     * Renders all live particles.
     * Sparks are tiny alpha-fading squares at screen coordinates.
     * Smoke frames are drawn at world coordinates corrected by the camera offset.
     *
     * @param g    Graphics2D context
     * @param camX gameplay camera X (0 for GUI-space particles)
     * @param camY gameplay camera Y
     */
    public void draw(Graphics2D g, float camX, float camY) {
        if (particles.isEmpty()) return;
        Composite saved = g.getComposite();

        for (Particle p : particles) {
            if (p.type == TYPE_SPARK) {
                drawSpark(g, p);
            } else if (p.type == TYPE_BLOOD) {
                drawBlood(g, p);
            } else {
                drawSmoke(g, p, (int) camX, (int) camY);
            }
        }
        g.setComposite(saved);
    }

    /**
     * Convenience overload for GUI screens where camera offset is zero.
     */
    public void draw(Graphics2D g) {
        draw(g, 0f, 0f);
    }

    // ── private draw helpers ──────────────────────────────────────────────────

    private void drawSpark(Graphics2D g, Particle p) {
        // alpha fades linearly from 1 → 0 as life runs out
        float alpha = Math.max(0f, p.life / p.maxLife);
        int   size  = Math.max(2, (int)(5f * alpha));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(new Color(p.r, p.gCol, p.b));
        g.fillRect((int) p.x - size / 2, (int) p.y - size / 2, size, size);
    }

    private void drawSmoke(Graphics2D g, Particle p, int camX, int camY) {
        int fi = Math.min(p.frame, SMOKE_FRAMES - 1);
        if (smokeFrames[fi] == null) return;

        // smoke fades out over its second half
        float progress = (float) p.frame / SMOKE_FRAMES;
        float alpha = (progress < 0.5f) ? 1f : 2f * (1f - progress);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.max(0f, alpha)));

        // world → screen
        int sx = (int) p.x - camX - 32;
        int sy = (int) p.y - camY - 32;
        g.drawImage(smokeFrames[fi], sx, sy, 64, 64, null);
    }

    /**
     * Draws the current frame of the blood splatter spritesheet at screen position (p.x, p.y).
     * The 192×48px sheet has 4 frames of 48×48 each, read left-to-right.
     * Blood is drawn at 2× scale (96×96) centred on the impact point.
     */
    private void drawBlood(Graphics2D g, Particle p) {
        if (p.sheetIdx < 0 || p.sheetIdx >= BLOOD_SHEET_COUNT) return;
        BufferedImage sheet = bloodSheets[p.sheetIdx];
        if (sheet == null) return;

        int fi  = Math.min(p.frame, BLOOD_FRAMES - 1);
        int fw  = sheet.getWidth() / BLOOD_FRAMES;   // = 48 for a 192-wide sheet
        int fh  = sheet.getHeight();                  // = 48

        // slight fade-out on last frame
        float alpha = (fi == BLOOD_FRAMES - 1) ? 0.5f : 1f;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // draw at 2× scale, centred on impact
        int scale = 2;
        int dx = (int) p.x - fw * scale / 2;
        int dy = (int) p.y - fh * scale / 2;
        // source rect for this frame
        int sx1 = fi * fw, sy1 = 0, sx2 = sx1 + fw, sy2 = fh;
        g.drawImage(sheet, dx, dy, dx + fw * scale, dy + fh * scale, sx1, sy1, sx2, sy2, null);
    }

    // =========================================================================
    //  INNER DATA CLASS  — Particle
    // =========================================================================

    /**
     * Single particle instance.  Factory methods enforce required fields;
     * the class itself is just a plain data container.
     */
    public static final class Particle {
        public int   type;          // TYPE_SPARK or TYPE_SMOKE
        public float x, y;          // position (screen or world)
        public float vx, vy;        // velocity (px/s) — sparks only
        public float life, maxLife; // remaining / total lifetime (ms) — sparks
        public int   r, gCol, b;    // spark colour (no 'g' to avoid name clash)
        public int   frame;         // current animation frame — smoke / blood
        public long  frameTimer;    // ms accumulated toward next frame — smoke / blood
        public int   sheetIdx;      // index into bloodSheets[] — blood only

        /** Creates a spark particle. */
        static Particle spark(float x, float y, float vx, float vy,
                              float life, int r, int g, int b) {
            Particle p = new Particle();
            p.type = TYPE_SPARK;
            p.x = x;  p.y = y;
            p.vx = vx; p.vy = vy;
            p.life = p.maxLife = life;
            p.r = r;  p.gCol = g;  p.b = b;
            return p;
        }

        /** Creates a smoke particle anchored at the given world position. */
        static Particle smoke(float x, float y) {
            Particle p = new Particle();
            p.type = TYPE_SMOKE;
            p.x = x;  p.y = y;
            return p;
        }

        /** Creates a blood splatter particle at the given screen position. */
        static Particle blood(float x, float y, int sheetIdx) {
            Particle p = new Particle();
            p.type = TYPE_BLOOD;
            p.x = x;  p.y = y;
            p.sheetIdx = sheetIdx;
            return p;
        }
    }
}
