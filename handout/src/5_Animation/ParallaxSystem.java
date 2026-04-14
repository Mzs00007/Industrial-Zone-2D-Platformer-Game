/*
 * Decompiled with CFR 0.152.
 */
package animation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public static class AnimationAndSpriteLoader.ParallaxSystem {
    private List<ParallexLayer> layers = new ArrayList<ParallexLayer>();
    private float currentCameraX = 0.0f;

    public void addLayer(ParallexLayer parallexLayer3) {
        this.layers.add(parallexLayer3);
        this.layers.sort((parallexLayer, parallexLayer2) -> Integer.compare(parallexLayer.getLayerIndex(), parallexLayer2.getLayerIndex()));
    }

    public void updateCamera(float f) {
        this.currentCameraX = f;
        for (ParallexLayer parallexLayer : this.layers) {
            parallexLayer.update(f);
        }
    }

    public void render(Graphics2D graphics2D, int n, int n2) {
        for (ParallexLayer parallexLayer : this.layers) {
            parallexLayer.render(graphics2D, n, n2, this.currentCameraX);
        }
    }

    public void clearLayers() {
        this.layers.clear();
    }

    public int getLayerCount() {
        return this.layers.size();
    }

    public static class ParallexLayer {
        private BufferedImage image;
        private float parallaxDepth;
        private float currentOffsetX = 0.0f;
        private int layerIndex;
        private float imageWidth;
        private float imageHeight;

        public ParallexLayer(BufferedImage bufferedImage, float f, int n) {
            this.image = bufferedImage;
            this.parallaxDepth = Math.max(0.0f, Math.min(1.0f, f));
            this.layerIndex = n;
            this.imageWidth = bufferedImage != null ? (float)bufferedImage.getWidth() : 0.0f;
            this.imageHeight = bufferedImage != null ? (float)bufferedImage.getHeight() : 0.0f;
        }

        public void update(float f) {
            this.currentOffsetX = f * this.parallaxDepth;
        }

        public void render(Graphics2D graphics2D, int n, int n2, float f) {
            if (this.image == null) {
                return;
            }
            float f2 = f * this.parallaxDepth % this.imageWidth;
            if (f2 < 0.0f) {
                f2 += this.imageWidth;
            }
            int n3 = (int)(-f2);
            int n4 = (int)(this.imageWidth - f2);
            graphics2D.drawImage(this.image, n3, 0, (int)this.imageWidth, n2, null);
            graphics2D.drawImage(this.image, n4, 0, (int)this.imageWidth, n2, null);
        }

        public int getLayerIndex() {
            return this.layerIndex;
        }

        public float getParallaxDepth() {
            return this.parallaxDepth;
        }

        public float getCurrentOffset() {
            return this.currentOffsetX;
        }
    }
}
