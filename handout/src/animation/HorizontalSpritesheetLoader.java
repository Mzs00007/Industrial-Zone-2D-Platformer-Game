package animation;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
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

    // ── manifest metadata cache ──────────────────────────────────────────────
    private static Map<String, int[]> manifestDims;   // filename → [width, height]
    private static boolean manifestLoaded = false;

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
     * Priority chain for frame count:
     *   1. Manifest metadata (assets-manifest.json width/height)
     *   2. Actual loaded image dimensions (width/height for square frames)
     *   3. Filename parsing (e.g. "_6Frames") - may be wrong for ~98 assets
     *   4. Auto-detection via squareness scoring
     */
    public static HorizontalSpritesheetLoader fromFilename(String path) {
        loadManifest();
        File f = new File(path);
        String fn = f.getName();
        int ms = parseMsPerFrame(fn);
        String nm = parseName(fn);
        boolean lp = !fn.toLowerCase().contains("playonce");
        BufferedImage img = null;
        try { img = ImageIO.read(f); }
        catch (Exception e) {
            System.err.println("[HorizontalSpritesheetLoader] Cannot load: " + path
                               + " - " + e.getMessage());
        }

        int fc = -1;

        // 1. Try manifest metadata (most trusted source)
        if (manifestDims != null && manifestDims.containsKey(fn)) {
            int[] dims = manifestDims.get(fn);
            int mw = dims[0], mh = dims[1];
            if (mh > 0 && mw % mh == 0) {
                fc = mw / mh;
                System.out.println("[SpriteLoader] MANIFEST: " + fn
                    + " -> " + fc + " frames (" + mw + "x" + mh + ")");
            }
        }

        // 2. Try actual image dimensions (square-frame computation)
        if (fc <= 0 && img != null) {
            int iw = img.getWidth(), ih = img.getHeight();
            if (ih > 0 && iw % ih == 0 && iw / ih >= 2) {
                fc = iw / ih;
                System.out.println("[SpriteLoader] IMG_DIMS: " + fn
                    + " -> " + fc + " frames (" + iw + "x" + ih + ")");
            }
        }

        // 3. Filename parsing fallback (may be wrong for ~98 assets)
        if (fc <= 0) {
            fc = parseFrameCount(fn);
            if (fc > 0) {
                System.out.println("[SpriteLoader] FILENAME: " + fn
                    + " -> " + fc + " frames (from filename, unverified)");
            }
        }

        // 4. Auto-detection fallback (even counts only)
        if (fc <= 0 && img != null) {
            fc = detectEvenFrameCount(img.getWidth(), img.getHeight());
            System.out.println("[SpriteLoader] AUTO_DETECT: " + fn
                + " -> " + fc + " frames (squareness scoring)");
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

    // ── manifest loading ───────────────────────────────────────────────────

    /**
     * Lazily loads assets-manifest.json and caches width/height per filename.
     * The manifest metadata is the authoritative source for sprite dimensions.
     */
    private static synchronized void loadManifest() {
        if (manifestLoaded) return;
        manifestLoaded = true;
        manifestDims = new HashMap<>();

        File manifestFile = new File("assets-manifest.json");
        if (!manifestFile.exists()) {
            System.err.println("[SpriteLoader] assets-manifest.json not found at: "
                + manifestFile.getAbsolutePath());
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(manifestFile))) {
            String line;
            String currentName = null;
            int currentWidth = -1;
            int currentHeight = -1;

            Pattern namePat   = Pattern.compile("\"name\"\\s*:\\s*\"([^\"]+)\"");
            Pattern widthPat  = Pattern.compile("\"width\"\\s*:\\s*(\\d+)");
            Pattern heightPat = Pattern.compile("\"height\"\\s*:\\s*(\\d+)");

            while ((line = br.readLine()) != null) {
                String trimmed = line.trim();

                Matcher nm = namePat.matcher(trimmed);
                if (nm.find()) {
                    // save previous entry
                    if (currentName != null && currentWidth > 0 && currentHeight > 0) {
                        manifestDims.put(currentName, new int[]{currentWidth, currentHeight});
                    }
                    currentName = nm.group(1);
                    currentWidth = -1;
                    currentHeight = -1;
                    continue;
                }

                Matcher wm = widthPat.matcher(trimmed);
                if (wm.find()) {
                    currentWidth = Integer.parseInt(wm.group(1));
                    continue;
                }

                Matcher hm = heightPat.matcher(trimmed);
                if (hm.find()) {
                    currentHeight = Integer.parseInt(hm.group(1));
                }
            }
            // save last entry
            if (currentName != null && currentWidth > 0 && currentHeight > 0) {
                manifestDims.put(currentName, new int[]{currentWidth, currentHeight});
            }

            System.out.println("[SpriteLoader] Loaded manifest: "
                + manifestDims.size() + " entries with dimensions");
        } catch (Exception e) {
            System.err.println("[SpriteLoader] Failed to parse manifest: " + e.getMessage());
        }
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

    /** Returns all frames as an array. Useful for integration with game entity code. */
    public BufferedImage[] getAllFrames() {
        if (!loaded || sheet == null) return new BufferedImage[0];
        BufferedImage[] out = new BufferedImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
            out[i] = sheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
        return out;
    }

    @Override
    public String toString() {
        return String.format(
            "HorizontalSpritesheetLoader[%s, %d frames @ %dms/f, loop=%b, loaded=%b]",
            name, frameCount, msPerFrame, loop, loaded);
    }
}

