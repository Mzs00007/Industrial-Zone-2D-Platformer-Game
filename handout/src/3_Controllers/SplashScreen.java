package ui;

import java.awt.image.BufferedImage;
import animation.InputController.PixelCopyHelper;
import ui.ManifestLoader;

/**
 * Splash Screen - Brief intro before menu
 */
public class SplashScreen {
    private BufferedImage splashImage;
    private long startTime;
    private static final long DURATION_MS = 3000; // 3 seconds
    
    public SplashScreen(int w, int h) {
        this.startTime = System.currentTimeMillis();
        // Try to load splash image
        BufferedImage splash = ManifestLoader.loadGuiAsset("splash");
        if (splash != null) {
            this.splashImage = splash;
        }
    }
    
    public void render(BufferedImage dest) {
        if (splashImage != null) {
            PixelCopyHelper.copyRaster(splashImage, dest, 0, 0);
        } else {
            // Fallback: dark background with text indication
            int[] pixels = new int[dest.getWidth() * dest.getHeight()];
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = 0xFF000000;
            }
            dest.setRGB(0, 0, dest.getWidth(), dest.getHeight(), pixels, 0, dest.getWidth());
        }
    }
    
    public boolean isFinished() {
        return System.currentTimeMillis() - startTime >= DURATION_MS;
    }
}
