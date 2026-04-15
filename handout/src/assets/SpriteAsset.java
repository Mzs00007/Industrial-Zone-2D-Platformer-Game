package assets;

/**
 * Descriptor for a sprite or spritesheet asset.
 * Stores metadata from the manifest and auto-calculates frame count
 * based on image dimensions.
 *
 * FRAME COUNT RULE:
 *   If totalWidth > 32px  →  spritesheet  →  frameCount = totalWidth / height
 *   If totalWidth <= 32px →  single sprite →  frameCount = 1
 *
 * This assumes horizontal spritesheets with square frames per row,
 * matching the HorizontalSpritesheetLoader convention used throughout
 * the Industrial Zone codebase.
 */
public class SpriteAsset {

    /** Original filename including extension */
    public final String filename;

    /** Directory path relative to project root, always forward-slash, always trailing slash */
    public final String directory;

    /** Total pixel width of the full image (all frames combined if spritesheet) */
    public final int totalWidth;

    /** Pixel height of the image (= single frame height for spritesheets) */
    public final int height;

    /** Suggested per-frame duration in milliseconds, -1 if not specified in metadata */
    public final int suggestedDurationMs;

    /** Whether this animation loops continuously or plays once */
    public final boolean looping;

    /** Human-readable description of the asset */
    public final String description;

    /**
     * @param filename           PNG filename with extension
     * @param directory          path from project root ending with /
     * @param totalWidth         full image width in pixels
     * @param height             image height in pixels (= frame height)
     * @param suggestedDurationMs per-frame duration hint, or -1
     * @param looping            true if animation loops
     * @param description        human-readable label
     */
    public SpriteAsset(String filename, String directory,
                       int totalWidth, int height,
                       int suggestedDurationMs, boolean looping,
                       String description) {
        this.filename           = filename;
        this.directory          = directory;
        this.totalWidth         = totalWidth;
        this.height             = height;
        this.suggestedDurationMs = suggestedDurationMs;
        this.looping            = looping;
        this.description        = description;
    }

    // ── Convenience factory methods ──────────────────────────────────────

    /** Shorthand for a looping spritesheet */
    public static SpriteAsset loop(String file, String dir,
                                   int w, int h, int ms, String desc) {
        return new SpriteAsset(file, dir, w, h, ms, true, desc);
    }

    /** Shorthand for a play-once spritesheet */
    public static SpriteAsset once(String file, String dir,
                                   int w, int h, int ms, String desc) {
        return new SpriteAsset(file, dir, w, h, ms, false, desc);
    }

    /** Shorthand for a static single-frame sprite (no animation) */
    public static SpriteAsset single(String file, String dir,
                                     int w, int h, String desc) {
        return new SpriteAsset(file, dir, w, h, -1, false, desc);
    }

    // ── Auto-calculated properties ───────────────────────────────────────

    /** Full path from project root:  directory + filename */
    public String getFullPath() {
        return directory + filename;
    }

    /** True if this image contains multiple frames (totalWidth > 32 and wider than tall) */
    public boolean isSheet() {
        return totalWidth > 32 && height > 0 && totalWidth > height;
    }

    /**
     * Auto-calculated frame count.
     *   Spritesheet (width > 32):  totalWidth / height  (square frames)
     *   Single sprite:             1
     */
    public int getFrameCount() {
        if (isSheet()) {
            return totalWidth / height;
        }
        return 1;
    }

    /** Width of one frame in pixels */
    public int getFrameWidth() {
        int count = getFrameCount();
        return (count > 1) ? totalWidth / count : totalWidth;
    }

    /** Total animation duration in ms (frameCount × suggestedDurationMs), or -1 */
    public int getTotalDurationMs() {
        if (suggestedDurationMs <= 0) return -1;
        return getFrameCount() * suggestedDurationMs;
    }

    @Override
    public String toString() {
        return String.format("SpriteAsset[%s | %dx%d | %d frames | %dms/f | %s]",
                filename, totalWidth, height, getFrameCount(),
                suggestedDurationMs, looping ? "loop" : "once");
    }
}
