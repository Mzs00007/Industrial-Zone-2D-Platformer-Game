/*
 * Decompiled with CFR 0.152.
 */
package core_game_entities.effects;

import entities.effects.VFXChainReaction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class ActiveEffect {
    public VFXChainReaction.EffectType type;
    public float x;
    public float y;
    public float elapsedMs = 0.0f;
    public float durationMs;
    public float currentRadius = 0.0f;
    public boolean isComplete = false;
    public Set<String> entitiesDamaged = new HashSet<String>();
    public List<VFXChainReaction.ParticleEffect> particles = new ArrayList<VFXChainReaction.ParticleEffect>();

    public ActiveEffect(VFXChainReaction.EffectType effectType, float f, float f2, float f3) {
        this.type = effectType;
        this.x = f;
        this.y = f2;
        this.durationMs = f3;
    }
}
