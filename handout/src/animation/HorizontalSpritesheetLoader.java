package animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

/**
 * Loads a horizontal spritesheet (one row, N equal-width frames).
 * All factory methods are static â€“ one line to load any animation:
 *
 *   HorizontalSpritesheetLoader anim =
 *       HorizontalSpritesheetLoader.fromFilename("Resources/.../Biker_Run_6Frames_120ms.png");
 */
public class HorizontalSpritesheetLoader {

    /** Only even frame counts are valid (avoids half-frame mis-detection). */
    public static final int[] EVEN_COUNTS = {2, 4, 6, 8, 10, 12, 14, 16, 20, 24};

    private final String name;
    private final String path;
    private final BufferedImage sheet;
    private final int frameCount;
    private final int frameWidth;
    private final int frameHeight;
    private final int msPerFrame;
    private final boolean loop;
    private final boolean loaded;

    // â”€â”€ core constructor â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private HorizontalSpritesheetLoader(String name, String path, BufferedImage sheet,
                                        int frameCount, int msPerFrame, boolean loop) {
        this.name      = name;
        this.path      = path;
        this.sheet     = sheet;
        int fc         = (sheet != null && frameCount > 0) ? frameCount : 1;
        this.frameCount  = fc;
        this.frameWidth  = (sheet != null) ? sheet.getWidth() / fc : 1;
        this.frameHeight = (sheet != null) ? sheet.getHeight() : 1;
        this.msPerFrame  = (msPerFrame > 0) ? msPerFrame : 100;
        this.loop   = loop;
        this.loaded = (sheet != null);
    }

    // â”€â”€ static factories â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    /**
     * One-liner: parse name, frame count and timing from the filename.
     * e.g. "Biker_Run_6Frames_120ms.png" â†’ name="Biker_Run", frames=6, ms=120, loop=true
     */
    public static HorizontalSpritesheetLoader fromFilename(String path) {
        File f = new File(path);
        String fn = f.getName();
        int fc = parseFrameCount(fn);
        int ms = parseMsPerFrame(fn);
        String nm = parseName(fn);
        boolean lp = !fn.toLowerCase().contains("playonce");
        BufferedImage img = null;
        try { img = ImageIO.read(f); }
        catch (Exception e) {
            System.err.println("[HorizontalSpritesheetLoader] Cannot load: " + path
                               + " â€“ " + e.getMessage());
        }
        if (img != null && fc <= 0) {
            fc = detectEvenFrameCount(img.getWidth(), img.getHeight());
        }
        return new HorizontalSpritesheetLoader(nm, path, img, fc, ms, lp);
    }

    /**
     * Build from an already-stitched BufferedImage (e.g. VFX sequences stitched in memory).
     */
    public static HorizontalSpritesheetLoader fromImage(String name, BufferedImage img,
                                                        int frameCount, int msPerFrame,
                                                        boolean loop) {
        return new HorizontalSpritesheetLoader(name, "<memory>", img, frameCount, msPerFrame, loop);
    }

    /**
     * Explicit override â€“ caller supplies exact frame count and timing.
     */
    public static HorizontalSpritesheetLoader fromFile(String path, String name,
                                                       int frameCount, int msPerFrame,
                                                       boolean loop) {
        BufferedImage img = null;
        try { img = ImageIO.read(new File(path)); }
        catch (Exception e) {
            System.err.println("[HorizontalSpritesheetLoader] Cannot load: " + path
                               + " â€“ " + e.getMessage());
        }
        return new HorizontalSpritesheetLoader(name, path, img, frameCount, msPerFrame, loop);
    }

    // â”€â”€ frame access â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    /** Returns frame i. Loops if loop=true, clamps to last frame otherwise. */
    public BufferedImage getFrame(int i) {
        if (!loaded || sheet == null) return null;
        int idx = loop ? (i % frameCount) : Math.min(i, frameCount - 1);
        if (idx < 0) idx = 0;
        return sheet.getSubimage(idx * frameWidth, 0, frameWidth, frameHeight);
    }

    // â”€â”€ static helpers â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€

    /**
     * Detect the best even frame count from image dimensions using squareness scoring.
     */
    public static int detectEvenFrameCount(int imgW, int imgH) {
        int best = 2;
        double bestScore = Double.MAX_VALUE;
        for (int n : EVEN_COUNTS) {
            if (imgW % n == 0) {
                int fw = imgW / n;
                double ratio  = (double) fw / imgH;
                double score  = Math.abs(ratio - 1.0);
                if (score < bestScore) { bestScore = score; best = n; }
            }
        }
        return best;
    }

    /** Parse frame count from filename, e.g. "_6Frames" or "_6frames" â†’ 6 */
    public static int parseFrameCount(String filename) {
        Matcher m = Pattern.compile("_(\\d+)[Ff]rames?").matcher(filename);
        return m.find() ? Integer.parseInt(m.group(1)) : -1;
    }

    /** Parse ms per frame from filename, e.g. "_120ms" â†’ 120 */
    public static int parseMsPerFrame(String filename) {
        Matcher m = Pattern.compile("_(\\d+)ms").matcher(filename);
        return m.find() ? Integer.parseInt(m.group(1)) : 100;
    }

    /** Strip extension, frame-count tag, ms tag, and PlayOnce to get a clean name. */
    public static String parseName(String filename) {
        String n = filename.replaceAll("\\.[^.]+$", "");           // strip extension
        n = n.replaceAll("_(\\d+)[Ff]rames?(_\\d+ms)?", "");      // strip Nframes / _ms
        n = n.replaceAll("[Pp]lay[Oo]nce", "");
        return n.isEmpty() ? "Unknown" : n;
    }

    // â”€â”€ getters â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public String getName()          { return name; }
    public String getPath()          { return path; }
    public BufferedImage getSheet()  { return sheet; }
    public int getFrameCount()       { return frameCount; }
    public int getFrameWidth()       { return frameWidth; }
    public int getFrameHeight()      { return frameHeight; }
    public int getMsPerFrame()       { return msPerFrame; }
    public boolean isLoop()          { return loop; }
    public boolean isLoaded()        { return loaded; }

    @Override
    public String toString() {
        return String.format(
            "HorizontalSpritesheetLoader[%s, %d frames @ %dms/f, loop=%b, loaded=%b]",
            name, frameCount, msPerFrame, loop, loaded);
    }
}

