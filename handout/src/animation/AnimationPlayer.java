package animation;

import java.awt.image.BufferedImage;

/**
 * Drives a HorizontalSpritesheetLoader forward in time.
 *
 * Usage:
 *   AnimationPlayer player = new AnimationPlayer();
 *   player.play(myAnim);
 *   // Each tick:
 *   player.update(dtMs);
 *   g.drawImage(player.getCurrentFrame(), x, y, null);
 */
public class AnimationPlayer {

    private HorizontalSpritesheetLoader current;
    private int   frame;
    private long  accumulated;
    private boolean finished;

    public AnimationPlayer() {
        this.current     = null;
        this.frame       = 0;
        this.accumulated = 0;
        this.finished    = false;
    }

    // ── playback control ─────────────────────────────────────────────────────

    /** Switch to anim — no-op if it is already playing (prevents unwanted reset). */
    public void play(HorizontalSpritesheetLoader anim) {
        if (anim == null || anim == current) return;
        current     = anim;
        frame       = 0;
        accumulated = 0;
        finished    = false;
    }

    /** Always restart the animation even if it is already the current one. */
    public void forcePlay(HorizontalSpritesheetLoader anim) {
        current     = anim;
        frame       = 0;
        accumulated = 0;
        finished    = false;
    }

    /** Reset frame counter without changing the animation. */
    public void reset() {
        frame       = 0;
        accumulated = 0;
        finished    = false;
    }

    // ── update ───────────────────────────────────────────────────────────────

    /**
     * Advance by dtMs milliseconds. Call once per game tick.
     */
    public void update(long dtMs) {
        if (current == null || finished) return;
        accumulated += dtMs;
        int ms = current.getMsPerFrame();
        if (ms <= 0) ms = 100;
        while (accumulated >= ms) {
            accumulated -= ms;
            if (current.isLoop()) {
                frame = (frame + 1) % current.getFrameCount();
            } else {
                if (frame < current.getFrameCount() - 1) {
                    frame++;
                } else {
                    finished = true;
                    return;
                }
            }
        }
    }

    // ── frame access ─────────────────────────────────────────────────────────

    /** Returns the current frame, or null if no animation is loaded. */
    public BufferedImage getCurrentFrame() {
        if (current == null) return null;
        return current.getFrame(frame);
    }

    // ── state / getters ──────────────────────────────────────────────────────
    public boolean isFinished()     { return finished; }
    public int     getFrameIndex()  { return frame; }
    public int     getFrameCount()  { return current != null ? current.getFrameCount() : 0; }
    public String  getAnimName()    { return current != null ? current.getName() : "none"; }
    public boolean isLoop()         { return current != null && current.isLoop(); }
    public HorizontalSpritesheetLoader getCurrent() { return current; }
}

