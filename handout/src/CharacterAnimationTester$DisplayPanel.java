/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

private class CharacterAnimationTester.DisplayPanel
extends JPanel {
    private CharacterAnimationTester.DisplayPanel() {
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;
        int n = this.getWidth();
        int n2 = this.getHeight();
        if (CharacterAnimationTester.this.showCheckerboard) {
            this.drawCheckerboard(graphics2D, n, n2);
        }
        if (CharacterAnimationTester.this.currentImage == null) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(new Font("Arial", 1, 20));
            graphics2D.drawString("Select an asset to preview", n / 2 - 150, n2 / 2);
            return;
        }
        int n3 = CharacterAnimationTester.this.currentImage.getWidth() / CharacterAnimationTester.this.frameCount;
        int n4 = CharacterAnimationTester.this.currentImage.getHeight();
        int n5 = (int)((float)n3 * CharacterAnimationTester.this.zoomLevel);
        int n6 = (int)((float)n4 * CharacterAnimationTester.this.zoomLevel);
        int n7 = (n - n5) / 2;
        int n8 = (n2 - n6) / 2;
        int n9 = CharacterAnimationTester.this.currentFrame * n3;
        BufferedImage bufferedImage = CharacterAnimationTester.this.currentImage.getSubimage(n9, 0, n3, n4);
        if (CharacterAnimationTester.this.shouldFlip) {
            AffineTransform affineTransform = AffineTransform.getScaleInstance(-1.0, 1.0);
            affineTransform.translate(-n3, 0.0);
            graphics2D.drawImage(bufferedImage, affineTransform, this);
        } else {
            graphics2D.drawImage(bufferedImage, n7, n8, n5, n6, this);
        }
    }

    private void drawCheckerboard(Graphics2D graphics2D, int n, int n2) {
        int n3 = 16;
        for (int i = 0; i < n; i += n3) {
            for (int j = 0; j < n2; j += n3) {
                if ((i / n3 + j / n3) % 2 == 0) {
                    graphics2D.setColor(new Color(50, 50, 60));
                } else {
                    graphics2D.setColor(new Color(70, 70, 80));
                }
                graphics2D.fillRect(i, j, n3, n3);
            }
        }
    }
}
