/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public static class AnimationAndSpriteLoader.WeaponRenderingSystem {
    private Map<String, BufferedImage> gunImageCache = new HashMap<String, BufferedImage>();
    private Map<String, BufferedImage> bulletImageCache = new HashMap<String, BufferedImage>();

    public BufferedImage renderArmedCharacter(String string, BufferedImage bufferedImage, String string2, int n, int n2) {
        try {
            String string3;
            BufferedImage bufferedImage2;
            BufferedImage bufferedImage3 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 2);
            Graphics2D graphics2D = bufferedImage3.createGraphics();
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
            int[] nArray = this.getGunOffset(string, n2);
            BufferedImage bufferedImage4 = this.loadGunImage(string2);
            if (bufferedImage4 != null) {
                graphics2D.drawImage((Image)bufferedImage4, nArray[0], nArray[1], null);
            }
            if ((bufferedImage2 = this.loadHandPoseImage(string3 = AnimationAndSpriteLoader.HandGripSelector.selectGripPose(string, n))) != null) {
                int[] nArray2 = this.getHandOffset(string, n2);
                graphics2D.drawImage((Image)bufferedImage2, nArray2[0], nArray2[1], null);
            }
            this.applyAnimationStateEffects(graphics2D, n2, n);
            graphics2D.dispose();
            return bufferedImage3;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to render armed character: " + string);
            AnimationAndSpriteLoader.logError("Gun: " + string2 + ", State: " + n2);
            return bufferedImage;
        }
    }

    private int[] getGunOffset(String string, int n) {
        switch (n) {
            case 0: {
                return new int[]{40, 30};
            }
            case 1: {
                return new int[]{38, 25};
            }
            case 3: {
                return new int[]{50, 20};
            }
            case 4: {
                return new int[]{35, 40};
            }
            case 5: {
                return new int[]{45, 28};
            }
        }
        return new int[]{40, 30};
    }

    private int[] getHandOffset(String string, int n) {
        int[] nArray = this.getGunOffset(string, n);
        return new int[]{nArray[0] + 5, nArray[1] + 8};
    }

    private void applyAnimationStateEffects(Graphics2D graphics2D, int n, int n2) {
        switch (n) {
            case 3: {
                graphics2D.setColor(new Color(255, 150, 0, 80));
                graphics2D.fillOval(55, 15, 15, 15);
                break;
            }
            case 6: {
                graphics2D.setColor(new Color(255, 0, 0, 60));
                graphics2D.fillRect(0, 0, 64, 64);
            }
        }
    }

    private BufferedImage loadGunImage(String string) {
        if (this.gunImageCache.containsKey(string)) {
            return this.gunImageCache.get(string);
        }
        try {
            String string2 = AnimationAndSpriteLoader.WEAPONS_BASE + string;
            BufferedImage bufferedImage = ImageIO.read(new File(string2));
            this.gunImageCache.put(string, bufferedImage);
            return bufferedImage;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to load gun image: " + string);
            return null;
        }
    }

    private BufferedImage loadHandPoseImage(String string) {
        if (string == null) {
            return null;
        }
        if (this.gunImageCache.containsKey(string)) {
            return this.gunImageCache.get(string);
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(string));
            this.gunImageCache.put(string, bufferedImage);
            return bufferedImage;
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to load hand pose: " + string);
            return null;
        }
    }

    public BufferedImage renderBullet(AnimationAndSpriteLoader.BulletSpawner.BulletInstance bulletInstance, String string) {
        try {
            BufferedImage bufferedImage = this.loadBulletImage(string);
            if (bufferedImage == null) {
                return null;
            }
            double d = Math.toRadians(bulletInstance.directionAngle);
            return this.rotateBullet(bufferedImage, d);
        }
        catch (Exception exception) {
            AnimationAndSpriteLoader.logError("Failed to render bullet: " + string);
            return null;
        }
    }

    private BufferedImage loadBulletImage(String string) {
        if (this.bulletImageCache.containsKey(string)) {
            return this.bulletImageCache.get(string);
        }
        try {
            String string2 = "Resources/industrial-zone/projectiles/" + string;
            BufferedImage bufferedImage = ImageIO.read(new File(string2));
            this.bulletImageCache.put(string, bufferedImage);
            return bufferedImage;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private BufferedImage rotateBullet(BufferedImage bufferedImage, double d) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        BufferedImage bufferedImage2 = new BufferedImage(n, n2, 2);
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        graphics2D.translate(n / 2, n2 / 2);
        graphics2D.rotate(d);
        graphics2D.translate(-n / 2, -n2 / 2);
        graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        graphics2D.dispose();
        return bufferedImage2;
    }

    public void preloadCharacterAssets(String string, List<String> list) {
        String[] stringArray = AnimationAndSpriteLoader.HandGripSelector.getAllGripAngles(string);
        for (String string2 : stringArray) {
            this.loadHandPoseImage(string2);
        }
        for (String string3 : list) {
            this.loadGunImage(string3);
        }
    }
}
