/*
 * Decompiled with CFR 0.152.
 */
package core_game_entities.effects;

import managers.Core;
import entities.characters.PlayerBase;
import entities.Enemies;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VFXChainReaction {
    private static final float EXPLOSION_RADIUS = 150.0f;
    private static final float EXPLOSION_MAX_DAMAGE = 100.0f;
    private static final float EXPLOSION_DURATION_MS = 500.0f;
    private static final float SHOCKWAVE_EXPAND_SPEED = 300.0f;
    private static final float SHOCKWAVE_MAX_RADIUS = 200.0f;
    private static final float SHOCKWAVE_KNOCKBACK = 400.0f;
    private static final float SHAKE_INTENSITY_MAX = 2.0f;
    private static final float SHAKE_DURATION_MS = 300.0f;
    private List<ActiveEffect> activeEffects = new ArrayList<ActiveEffect>();

    public void createBombExplosion(float f, float f2, float f3, float f4) {
        ParticleEffect particleEffect;
        float f5;
        float f6;
        float f7;
        float f8;
        int n;
        ActiveEffect activeEffect = new ActiveEffect(EffectType.BOMB_EXPLOSION, f, f2, 500.0f);
        this.activeEffects.add(activeEffect);
        ActiveEffect activeEffect2 = new ActiveEffect(EffectType.SHOCKWAVE, f, f2, 0.6666667f);
        this.activeEffects.add(activeEffect2);
        for (n = 0; n < 12; ++n) {
            f8 = (float)n / 12.0f * 360.0f;
            f7 = 200.0f + (float)Math.random() * 200.0f;
            f6 = (float)Math.cos(Math.toRadians(f8)) * f7 / 1000.0f;
            f5 = (float)Math.sin(Math.toRadians(f8)) * f7 / 1000.0f;
            particleEffect = new ParticleEffect(f, f2, f6, f5, "debris");
            activeEffect.particles.add(particleEffect);
        }
        for (n = 0; n < 8; ++n) {
            f8 = (float)Math.random() * 360.0f;
            f7 = 50.0f + (float)Math.random() * 100.0f;
            f6 = (float)Math.cos(Math.toRadians(f8)) * f7 / 1000.0f;
            f5 = (float)Math.sin(Math.toRadians(f8)) * f7 / 1000.0f;
            particleEffect = new ParticleEffect(f, f2, f6, f5, "smoke");
            particleEffect.size = 8.0f;
            activeEffect.particles.add(particleEffect);
        }
        ActiveEffect activeEffect3 = new ActiveEffect(EffectType.SCREEN_SHAKE, f, f2, 300.0f);
        this.activeEffects.add(activeEffect3);
        if (Core.logger != null) {
            Core.logger.info(String.format("[VFXChain] Bomb explosion at (%.0f,%.0f) radius=%.0f damage=%.0f", Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4)));
        }
    }

    public void updateEffects(long l, List<Enemies.EnemyFactory.EnemyInstance> list, Object object) {
        ArrayList<ActiveEffect> arrayList = new ArrayList<ActiveEffect>();
        for (ActiveEffect activeEffect : this.activeEffects) {
            activeEffect.elapsedMs += (float)l;
            switch (activeEffect.type.ordinal()) {
                case 0: {
                    this.updateExplosion(activeEffect, l, list, object);
                    break;
                }
                case 1: {
                    this.updateShockwave(activeEffect, l, list, object);
                    break;
                }
                case 5: {
                    this.updateScreenShake(activeEffect, l);
                    break;
                }
                case 2: 
                case 3: 
                case 4: {
                    this.updateParticles(activeEffect, l);
                }
            }
            if (!(activeEffect.elapsedMs >= activeEffect.durationMs)) continue;
            activeEffect.isComplete = true;
            arrayList.add(activeEffect);
        }
        this.activeEffects.removeAll(arrayList);
    }

    private void updateExplosion(ActiveEffect activeEffect, long l, List<Enemies.EnemyFactory.EnemyInstance> list, Object object) {
        for (ParticleEffect particleEffect : activeEffect.particles) {
            particleEffect.x += particleEffect.vx * (float)l;
            particleEffect.y += particleEffect.vy * (float)l;
            float f = activeEffect.elapsedMs / activeEffect.durationMs;
            particleEffect.life = 1.0f - f;
        }
        if (activeEffect.elapsedMs <= (float)l) {
            this.applyExplosionDamage(activeEffect, list, object);
        }
    }

    private void updateShockwave(ActiveEffect activeEffect, long l, List<Enemies.EnemyFactory.EnemyInstance> list, Object object) {
        float f = activeEffect.elapsedMs / activeEffect.durationMs;
        activeEffect.currentRadius = 200.0f * Math.min(1.0f, f * 2.0f);
    }

    private void updateScreenShake(ActiveEffect activeEffect, long l) {
        float f = activeEffect.elapsedMs / activeEffect.durationMs;
        float f2 = 2.0f * (1.0f - f);
    }

    private void updateParticles(ActiveEffect activeEffect, long l) {
        for (ParticleEffect particleEffect : activeEffect.particles) {
            particleEffect.x += particleEffect.vx * (float)l;
            particleEffect.y += particleEffect.vy * (float)l;
            particleEffect.vy += 0.3f;
            float f = (float)(System.currentTimeMillis() - particleEffect.createdMs) / 1000.0f;
            particleEffect.life = Math.max(0.0f, 1.0f - f / 2.0f);
        }
    }

    private void applyExplosionDamage(ActiveEffect activeEffect, List<Enemies.EnemyFactory.EnemyInstance> list, Object object) {
        float f;
        float f2;
        float f3;
        float f4;
        Object object2 = list.iterator();
        while (object2.hasNext()) {
            Enemies.EnemyFactory.EnemyInstance enemyInstance = object2.next();
            if (enemyInstance == null || !enemyInstance.isAlive() || !((f4 = this.distance(activeEffect.x, activeEffect.y, enemyInstance.getX(), enemyInstance.getY())) <= 150.0f) || activeEffect.entitiesDamaged.contains(enemyInstance.getEnemyName())) continue;
            f3 = (150.0f - f4) / 150.0f;
            f2 = 100.0f * f3;
            int n = enemyInstance.getHealth();
            int n2 = Math.max(0, n - (int)f2);
            if (n2 != n) {
                // empty if block
            }
            activeEffect.entitiesDamaged.add(enemyInstance.getEnemyName());
            if (Core.logger == null) continue;
            Core.logger.info(String.format("[VFXChain] Explosion hit enemy: damage=%.0f, distance=%.0f", Float.valueOf(f2), Float.valueOf(f4)));
        }
        if (object != null && object instanceof PlayerBase && (f = this.distance(activeEffect.x, activeEffect.y, (object2 = (PlayerBase)object).getX(), object2.getY())) <= 150.0f) {
            f4 = (150.0f - f) / 150.0f;
            f3 = 100.0f * f4;
            object2.takeDamage((int)f3);
            f2 = this.getAngle(activeEffect.x, activeEffect.y, object2.getX(), object2.getY());
            float f5 = 400.0f * f4;
            float f6 = (float)Math.cos(f2) * f5 / 100.0f;
            float f7 = (float)Math.sin(f2) * f5 / 100.0f;
            object2.setX(object2.getX() + f6);
            object2.setY(object2.getY() + f7);
            if (Core.logger != null) {
                Core.logger.info(String.format("[VFXChain] Explosion hit player: damage=%.0f, distance=%.0f", Float.valueOf(f3), Float.valueOf(f)));
            }
        }
    }

    public void checkForChainReactions(float f, float f2) {
    }

    private float distance(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        return (float)Math.sqrt(f5 * f5 + f6 * f6);
    }

    private float getAngle(float f, float f2, float f3, float f4) {
        return (float)Math.atan2(f4 - f2, f3 - f);
    }

    public List<ActiveEffect> getActiveEffects() {
        return new ArrayList<ActiveEffect>(this.activeEffects);
    }

    public List<ParticleEffect> getVisibleParticles() {
        ArrayList<ParticleEffect> arrayList = new ArrayList<ParticleEffect>();
        for (ActiveEffect activeEffect : this.activeEffects) {
            for (ParticleEffect particleEffect : activeEffect.particles) {
                if (!(particleEffect.life > 0.0f)) continue;
                arrayList.add(particleEffect);
            }
        }
        return arrayList;
    }

    public void clear() {
        this.activeEffects.clear();
    }

    public static class ActiveEffect {
        public EffectType type;
        public float x;
        public float y;
        public float elapsedMs = 0.0f;
        public float durationMs;
        public float currentRadius = 0.0f;
        public boolean isComplete = false;
        public Set<String> entitiesDamaged = new HashSet<String>();
        public List<ParticleEffect> particles = new ArrayList<ParticleEffect>();

        public ActiveEffect(EffectType effectType, float f, float f2, float f3) {
            this.type = effectType;
            this.x = f;
            this.y = f2;
            this.durationMs = f3;
        }
    }

    public static enum EffectType {
        BOMB_EXPLOSION,
        SHOCKWAVE,
        DEBRIS_SCATTER,
        SMOKE_CLOUD,
        CHAIN_REACTION,
        SCREEN_SHAKE;

    }

    public static class ParticleEffect {
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
}
