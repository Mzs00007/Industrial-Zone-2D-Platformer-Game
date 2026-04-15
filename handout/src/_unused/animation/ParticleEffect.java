/*
 * Decompiled with CFR 0.152.
 */
package core_game_entities.effects;
public class ParticleEffect {
    public float x;
    public float y;
    public float vx;
    public float vy;
    public float life = 1.0f;
    public float size = 4.0f;
    public String spriteFrame;
    public long createdMs;

    public ParticleEffect(float f, float f2, float f3, float f4, String string) {
        this.x = f;
        this.y = f2;
        this.vx = f3;
        this.vy = f4;
        this.spriteFrame = string;
        this.createdMs = System.currentTimeMillis();
    }
}
