package animation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Loads a sequence of individual PNG frames from a directory, stitches them
 * into a single horizontal sheet, and returns a HorizontalSpritesheetLoader.
 *
 * One-liner usage:
 *   HorizontalSpritesheetLoader smoke =
 *       SequenceFrameAnimationLoader.fromDirectory("Resources/.../Smoke", "Smoke", 80);
 */
public class SequenceFrameAnimationLoader {

    private SequenceFrameAnimationLoader() {}  // static utility class

    /**
     * Load all PNG files from {@code dirPath}, sort by name, stitch horizontally,
     * return a ready-to-use HorizontalSpritesheetLoader (looping=true).
     *
     * @param dirPath    Path to directory containing the frame PNGs.
     * @param name       Name to assign to the resulting animation.
     * @param msPerFrame Milliseconds to display each frame.
     */
    public static HorizontalSpritesheetLoader fromDirectory(String dirPath,
                                                             String name,
                                                             int msPerFrame) {
        return fromDirectory(dirPath, name, msPerFrame, true);
    }

    /**
     * Same as {@link #fromDirectory(String, String, int)} but with explicit loop flag.
     */
    public static HorizontalSpritesheetLoader fromDirectory(String dirPath,
                                                             String name,
                                                             int msPerFrame,
                                                             boolean loop) {
        File dir = new File(dirPath);
        if (!dir.isDirectory()) {
            System.err.println("[SequenceFrameAnimationLoader] Not a directory: " + dirPath);
            return HorizontalSpritesheetLoader.fromImage(name, null, 1, msPerFrame, loop);
        }

        // Collect PNGs â€“ skip macOS resource-fork files that start with "._"
        File[] all = dir.listFiles(f ->
            f.isFile()
            && f.getName().toLowerCase().endsWith(".png")
            && !f.getName().startsWith("._"));

        if (all == null || all.length == 0) {
            System.err.println("[SequenceFrameAnimationLoader] No valid PNGs in: " + dirPath);
            return HorizontalSpritesheetLoader.fromImage(name, null, 1, msPerFrame, loop);
        }

        // Sort by name so frames are in the right order
        Arrays.sort(all, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        List<BufferedImage> frames = new ArrayList<>();
        for (File f : all) {
            try {
                BufferedImage img = ImageIO.read(f);
                if (img != null) frames.add(img);
            } catch (Exception e) {
                System.err.println("[SequenceFrameAnimationLoader] Skipping "
                                   + f.getName() + ": " + e.getMessage());
            }
        }

        if (frames.isEmpty()) {
            System.err.println("[SequenceFrameAnimationLoader] No loadable frames in: " + dirPath);
            return HorizontalSpritesheetLoader.fromImage(name, null, 1, msPerFrame, loop);
        }

        // Stitch all frames side-by-side using the first frame's dimensions
        int fw = frames.get(0).getWidth();
        int fh = frames.get(0).getHeight();
        int n  = frames.size();

        BufferedImage sheet = new BufferedImage(fw * n, fh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = sheet.createGraphics();
        for (int i = 0; i < n; i++) {
            g.drawImage(frames.get(i), i * fw, 0, fw, fh, null);
        }
        g.dispose();

        System.out.println("[SequenceFrameAnimationLoader] Stitched " + n
                           + " frames for '" + name + "' from " + dirPath);
        return HorizontalSpritesheetLoader.fromImage(name, sheet, n, msPerFrame, loop);
    }
}

