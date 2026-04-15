package animation;

/**
 * Immutable descriptor for an animation clip.
 */
public class AnimationConfig {

    public final String  name;
    public final int     frameCount;
    public final int     msPerFrame;
    public final boolean loop;
    public final String  description;

    /** Full 5-arg constructor. */
    public AnimationConfig(String name, int frameCount, int msPerFrame,
                           boolean loop, String description) {
        this.name        = name;
        this.frameCount  = frameCount;
        this.msPerFrame  = msPerFrame;
        this.loop        = loop;
        this.description = description != null ? description : "";
    }

    /** 4-arg: loop inferred from name (contains "playonce" -> false). */
    public AnimationConfig(String name, int frameCount, int msPerFrame, String description) {
        this(name, frameCount, msPerFrame,
             !name.toLowerCase().contains("playonce"), description);
    }

    /**
     * Legacy 3-arg constructor for old callers:
     *   AnimationConfig(frameCount, timingMs, description)
     */
    public AnimationConfig(int frameCount, int msPerFrame, String description) {
        this("Unknown", frameCount, msPerFrame, true, description);
    }

    public String  getName()        { return name; }
    public int     getFrameCount()  { return frameCount; }
    public int     getMsPerFrame()  { return msPerFrame; }
    public boolean isLoop()         { return loop; }
    public String  getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("AnimationConfig[%s, %d frames @ %dms/f, loop=%b]",
                             name, frameCount, msPerFrame, loop);
    }
}

