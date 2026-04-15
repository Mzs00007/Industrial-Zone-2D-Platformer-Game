/*
 * Decompiled with CFR 0.152.
 */
package utilities;
public class AudioListener {
    private float listenerX = 0.0f;
    private float listenerY = 0.0f;
    private float listenerZ = 0.0f;
    private float maxAudioDistance = 500.0f;
    private boolean spatialAudioEnabled = false;

    public void setPosition(float f, float f2, float f3) {
        this.listenerX = f;
        this.listenerY = f2;
        this.listenerZ = f3;
    }

    public float calculateVolumeFromDistance(float f, float f2) {
        if (!this.spatialAudioEnabled) {
            return 1.0f;
        }
        float f3 = f - this.listenerX;
        float f4 = f2 - this.listenerY;
        float f5 = (float)Math.sqrt(f3 * f3 + f4 * f4);
        if (f5 > this.maxAudioDistance) {
            return 0.0f;
        }
        return 1.0f - f5 / this.maxAudioDistance;
    }

    public float calculatePanFromPosition(float f) {
        if (!this.spatialAudioEnabled) {
            return 0.0f;
        }
        float f2 = f - this.listenerX;
        return Math.max(-1.0f, Math.min(1.0f, f2 / 200.0f));
    }

    public void enableSpatialAudio(boolean bl) {
        this.spatialAudioEnabled = bl;
    }

    public void setMaxAudioDistance(float f) {
        this.maxAudioDistance = Math.max(100.0f, f);
    }

    public float getListenerX() {
        return this.listenerX;
    }

    public float getListenerY() {
        return this.listenerY;
    }

    public float getListenerZ() {
        return this.listenerZ;
    }

    public boolean isSpatialAudioEnabled() {
        return this.spatialAudioEnabled;
    }
}
