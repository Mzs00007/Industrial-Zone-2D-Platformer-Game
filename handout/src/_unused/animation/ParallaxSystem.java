/*
 * Decompiled with CFR 0.152.
 */
package animation;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
public class ParallaxSystem {
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
}
