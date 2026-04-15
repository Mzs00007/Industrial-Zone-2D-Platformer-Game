/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.imageio.ImageIO;
public class GUIAssetLoader {
    public static AnimationAndSpriteLoader.HorizontalSpritesheetLoader loadGUIElement(String string, String string2, int n) {
        AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader("GUI_" + string, string2, 0, 0, 0);
        if (horizontalSpritesheetLoader.load()) {
            System.out.println("[GUIAssetLoader] \u2713 Loaded: " + string + " (" + horizontalSpritesheetLoader.getFrameCount() + " frames)");
            return horizontalSpritesheetLoader;
        }
        System.out.println("[GUIAssetLoader] \u26a0 Failed to load: " + string + " from " + string2);
        return null;
    }

    public static Map<String, AnimationAndSpriteLoader.HorizontalSpritesheetLoader> loadGUIButtonStates(String string, String string2) {
        LinkedHashMap<String, AnimationAndSpriteLoader.HorizontalSpritesheetLoader> linkedHashMap = new LinkedHashMap<String, AnimationAndSpriteLoader.HorizontalSpritesheetLoader>();
        String string3 = string2 + "GUI_ButtonColorMap_" + string + ".png";
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(string3));
            if (bufferedImage == null) {
                System.out.println("[GUIAssetLoader] \u26a0 Button sheet not found: " + string3);
                return linkedHashMap;
            }
            String string4 = GUIAssetLoader.detectSpriteOrientation(bufferedImage);
            System.out.println("[GUIAssetLoader] Button orientation: " + string4 + " (" + bufferedImage.getWidth() + "x" + bufferedImage.getHeight() + ")");
            int[] nArray = GUIAssetLoader.detectGridDimensions(bufferedImage, string4);
            System.out.println("[GUIAssetLoader] Grid detected: " + nArray[0] + " rows \u00d7 " + nArray[1] + " cols");
            String[] stringArray = new String[]{"Idle", "Hover", "Press", "Disabled"};
            if ("VERTICAL".equals(string4)) {
                int n = nArray[1];
                int n2 = bufferedImage.getHeight() / nArray[0];
                int n3 = bufferedImage.getWidth() / nArray[1];
                for (int i = 0; i < Math.min(stringArray.length, nArray[0]); ++i) {
                    BufferedImage bufferedImage2 = GUIAssetLoader.extractStateFromVertical(bufferedImage, i, nArray[1], nArray[0]);
                    if (bufferedImage2 == null) continue;
                    File file = new File(System.getProperty("java.io.tmpdir") + "/game_assets");
                    file.mkdirs();
                    String string5 = file.getAbsolutePath() + "/Button_" + string + "_" + stringArray[i] + "_temp.png";
                    try {
                        ImageIO.write((RenderedImage)bufferedImage2, "PNG", new File(string5));
                        AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader("Button_" + string + "_" + stringArray[i], string5, n3, n2, n);
                        if (!horizontalSpritesheetLoader.load()) continue;
                        linkedHashMap.put(stringArray[i].toLowerCase(), horizontalSpritesheetLoader);
                        System.out.println("[GUIAssetLoader] \u2713 Extracted state: " + stringArray[i] + " (" + n + " frames, " + n3 + "x" + n2 + ")");
                        continue;
                    }
                    catch (IOException iOException) {
                        System.out.println("[GUIAssetLoader] \u2717 Failed to save temp state: " + stringArray[i] + " - " + iOException.getMessage());
                    }
                }
            } else {
                System.out.println("[GUIAssetLoader] \u26a0 Horizontal layout not yet implemented for buttons");
            }
            if (!linkedHashMap.isEmpty()) {
                System.out.println("[GUIAssetLoader] \u2713 Loaded button states: " + String.join((CharSequence)", ", linkedHashMap.keySet()));
            }
        }
        catch (IOException iOException) {
            System.out.println("[GUIAssetLoader] \u2717 Failed to load button sheet: " + iOException.getMessage());
        }
        return linkedHashMap;
    }

    public static BufferedImage loadStaticImage(String string, String string2) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(string2));
            if (bufferedImage != null) {
                System.out.println("[GUIAssetLoader] \u2713 Loaded image: " + string + " (" + bufferedImage.getWidth() + "x" + bufferedImage.getHeight() + ")");
                return bufferedImage;
            }
        }
        catch (IOException iOException) {
            System.out.println("[GUIAssetLoader] \u26a0 Failed to load image: " + string);
        }
        return null;
    }

    public static AnimationAndSpriteLoader.HorizontalSpritesheetLoader loadGUICard(String string, String string2) {
        String[] stringArray = new String[]{string2 + "Card_" + string + ".png", string2 + string + "_Card.png", string2 + string + ".png"};
        BufferedImage bufferedImage = null;
        String string3 = null;
        for (String string4 : stringArray) {
            bufferedImage = ImageIO.read(new File(string4));
            if (bufferedImage == null) continue;
            string3 = string4;
            break;
        }
        if (bufferedImage == null) {
            System.out.println("[GUIAssetLoader] \u26a0 Card not found with any naming convention: " + string);
            return null;
        }
        System.out.println("[GUIAssetLoader] Loaded card from: " + string3);
        String string5 = GUIAssetLoader.detectSpriteOrientation(bufferedImage);
        int n = GUIAssetLoader.detectFrameCount(bufferedImage, string5);
        System.out.println("[GUIAssetLoader] Card detected: " + string5 + " layout, " + n + " frames (" + bufferedImage.getWidth() + "x" + bufferedImage.getHeight() + ")");
        File file = new File(System.getProperty("java.io.tmpdir") + "/game_assets");
        file.mkdirs();
        String string6 = file.getAbsolutePath() + "/Card_" + string + "_temp.png";
        try {
            ImageIO.write((RenderedImage)bufferedImage, "PNG", new File(string6));
            int n2 = bufferedImage.getWidth() / n;
            int n3 = bufferedImage.getHeight();
            AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = new AnimationAndSpriteLoader.HorizontalSpritesheetLoader("Card_" + string, string6, n2, n3, n);
            if (horizontalSpritesheetLoader.load()) {
                System.out.println("[GUIAssetLoader] \u2713 Loaded card: " + string + " (" + n + " frames, " + n2 + "x" + n3 + ")");
                return horizontalSpritesheetLoader;
            }
            return null;
        }
        catch (IOException iOException) {
            try {
                System.out.println("[GUIAssetLoader] \u2717 Failed to save card temp file: " + iOException.getMessage());
                return null;
            }
            catch (IOException iOException2) {
                System.out.println("[GUIAssetLoader] \u2717 Failed to load card: " + string + " - " + iOException2.getMessage());
                return null;
            }
        }
    }

    public static AnimationAndSpriteLoader.HorizontalSpritesheetLoader loadTransition(String string, String string2) {
        String string3 = string2 + string + "_20Frames.png";
        return GUIAssetLoader.loadGUIElement("Transition_" + string, string3, 20);
    }

    public static Map<String, AnimationAndSpriteLoader.HorizontalSpritesheetLoader> loadBatchElements(String string, String ... stringArray) {
        LinkedHashMap<String, AnimationAndSpriteLoader.HorizontalSpritesheetLoader> linkedHashMap = new LinkedHashMap<String, AnimationAndSpriteLoader.HorizontalSpritesheetLoader>();
        for (String string2 : stringArray) {
            String string3 = string + string2 + "_Idle_4Frames.png";
            AnimationAndSpriteLoader.HorizontalSpritesheetLoader horizontalSpritesheetLoader = GUIAssetLoader.loadGUIElement(string2, string3, 4);
            if (horizontalSpritesheetLoader == null) continue;
            linkedHashMap.put(string2, horizontalSpritesheetLoader);
        }
        System.out.println("[GUIAssetLoader] \u2713 Batch loaded " + linkedHashMap.size() + "/" + stringArray.length + " elements");
        return linkedHashMap;
    }

    public static String detectSpriteOrientation(BufferedImage bufferedImage) {
        if (bufferedImage == null) {
            return "UNKNOWN";
        }
        float f = (float)bufferedImage.getWidth() / (float)bufferedImage.getHeight();
        if (f < 0.8f) {
            return "VERTICAL";
        }
        if (f > 1.25f) {
            return "HORIZONTAL";
        }
        return "SQUARE";
    }

    public static int[] detectGridDimensions(BufferedImage bufferedImage, String string) {
        if (bufferedImage == null) {
            return new int[]{1, 1};
        }
        float f = (float)bufferedImage.getWidth() / (float)bufferedImage.getHeight();
        if ("VERTICAL".equals(string)) {
            if (f >= 0.45f && f <= 0.55f) {
                return new int[]{4, 2};
            }
            if (f < 0.34f) {
                return new int[]{3, 1};
            }
            return new int[]{2, 1};
        }
        if ("HORIZONTAL".equals(string)) {
            float f2 = 1.0f / f;
            return new int[]{1, Math.max(2, (int)f2)};
        }
        return new int[]{1, 1};
    }

    public static BufferedImage extractStateFromVertical(BufferedImage bufferedImage, int n, int n2, int n3) {
        if (bufferedImage == null || n >= n3 || n2 <= 0) {
            return null;
        }
        int n4 = bufferedImage.getWidth();
        int n5 = bufferedImage.getHeight();
        int n6 = n4 / n2;
        int n7 = n5 / n3;
        int n8 = n * n7;
        System.out.println("[GUIAssetLoader] Extracting row " + n + ": " + n6 + "x" + n7 + " cells");
        BufferedImage bufferedImage2 = new BufferedImage(n4, n7, 2);
        bufferedImage.getRGB(0, n8, n4, n7, bufferedImage2.getRGB(0, 0, n4, n7, null, 0, n4), 0, n4);
        return bufferedImage2;
    }

    public static int detectFrameCount(BufferedImage bufferedImage, String string) {
        if (bufferedImage == null) {
            return 1;
        }
        if ("HORIZONTAL".equals(string)) {
            int n = bufferedImage.getWidth() / bufferedImage.getHeight();
            return Math.max(1, n);
        }
        if ("VERTICAL".equals(string)) {
            int[] nArray = GUIAssetLoader.detectGridDimensions(bufferedImage, string);
            return nArray[1];
        }
        return 1;
    }

    public static int getFrameCountFromMetadata(String string) {
        try {
            int n;
            BufferedImage bufferedImage = ImageIO.read(new File(string));
            if (bufferedImage == null) {
                return -1;
            }
            int n2 = bufferedImage.getWidth();
            if (n2 > (n = bufferedImage.getHeight()) * 2) {
                int n3 = n2 / n;
                return n3;
            }
            return 1;
        }
        catch (IOException iOException) {
            return -1;
        }
    }
}
