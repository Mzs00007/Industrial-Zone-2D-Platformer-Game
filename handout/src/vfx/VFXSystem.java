package vfx;

import camera.CameraSystem;
import animation.AnimationAndSpriteLoader;
import animation.InputController;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;


/**
 * â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
 * UNIFIED VFX SYSTEM - All Visual Effects in One File
 * â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
 * 
 * CONSOLIDATED FROM 11 FILES:
 * â€¢ VFXManager.java
 * â€¢ ParticleEmitter.java
 * â€¢ SpriteParticle.java
 * â€¢ AssetBasedVFXRenderer.java (interface)
 * â€¢ SmokeEffectRenderer.java
 * â€¢ ImpactEffectRenderer.java
 * â€¢ VSXVisualEffectsSystem.java
 * â€¢ SparkEffectSystem.java
 * â€¢ ImpactVfxRenderer.java
 * â€¢ SmokeVfxRenderer.java
 * â€¢ VFXAssetRegistry.java (constants registry)
 * 
 * RASTER GRAPHICS ONLY - PNG IMAGE ASSETS ONLY
 * â€¢ All effects use real PNG raster images from Resources/industrial-zone/vfx/
 * â€¢ NO vector graphics, NO java.awt.Color fallbacks, NO drawable shapes
 * 
 * @author 3359098
 * @version 1.0
 */
public class VFXSystem extends audio.AudioSystem {
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // SECTION 1: VFX ASSET REGISTRY (Constants)
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    /**
     * Central catalog of all VFX asset paths and configuration
     */
    public static class AssetRegistry {
        // SMOKE EFFECTS
        public static final String SMOKE_FRAME_FORMAT = 
            "Resources/industrial-zone/vfx/1 Smoke/smoke_%02d.png";
        public static final int SMOKE_FRAME_COUNT = 18;
        public static final long SMOKE_FRAME_DURATION_MS = 80;
        public static final long SMOKE_DURATION_MS = SMOKE_FRAME_COUNT * SMOKE_FRAME_DURATION_MS;
        
        // BLOOD EFFECTS
        public static final String BLOOD_SPLAT_FORMAT = 
            "Resources/industrial-zone/vfx/2 Blood/blood_splat_%02d.png";
        public static final int BLOOD_SPLAT_FRAME_COUNT = 6;
        public static final long BLOOD_FRAME_DURATION_MS = 80;
        public static final long BLOOD_DURATION_MS = BLOOD_SPLAT_FRAME_COUNT * BLOOD_FRAME_DURATION_MS;
        
        // SPARK/IMPACT EFFECTS
        public static final String IMPACT_SPARK_FORMAT = 
            "Resources/industrial-zone/vfx/3 Sparks/impact_%02d.png";
        public static final int IMPACT_SPARK_FRAME_COUNT = 8;
        public static final long SPARK_FRAME_DURATION_MS = 60;
        public static final long IMPACT_DURATION_MS = IMPACT_SPARK_FRAME_COUNT * SPARK_FRAME_DURATION_MS;
        
        public static final String SPARK_BURST_FORMAT = 
            "Resources/industrial-zone/vfx/3 Sparks/burst_%02d.png";
        public static final int SPARK_BURST_FRAME_COUNT = 8;
        
        // GENERIC PARTICLES
        public static final String PARTICLE_FORMAT = 
            "Resources/industrial-zone/vfx/4 Particles/particle_%02d.png";
        public static final int PARTICLE_TEXTURE_COUNT = 6;
        
        // SPECIAL EFFECTS
        public static final String SHARD_FORMAT = 
            "Resources/industrial-zone/vfx/5 Other/shard_%02d.png";
        public static final int SHARD_FRAME_COUNT = 4;
        
        public static final String ENERGY_FORMAT = 
            "Resources/industrial-zone/vfx/5 Other/energy_%02d.png";
        public static final int ENERGY_FRAME_COUNT = 6;
        
        public static final String PORTAL_FORMAT = 
            "Resources/industrial-zone/vfx/5 Other/portal_%02d.png";
        public static final int PORTAL_FRAME_COUNT = 8;
        
        // CONFIGURATION
        public static final int MAX_ACTIVE_PARTICLES = 1000;
        public static final int MAX_ACTIVE_EFFECTS = 100;
        
        private AssetRegistry() {}
        
        public static long getEffectDuration(String effectType) {
            switch (effectType.toLowerCase()) {
                case "smoke":
                case "explosion":
                    return SMOKE_DURATION_MS;
                case "blood":
                case "blood_splat":
                    return BLOOD_DURATION_MS;
                case "spark":
                case "impact":
                    return IMPACT_DURATION_MS;
                case "burst":
                    return (long)(IMPACT_DURATION_MS * 1.5f);
                case "energy":
                    return ENERGY_FRAME_COUNT * SPARK_FRAME_DURATION_MS;
                case "portal":
                    return PORTAL_FRAME_COUNT * SMOKE_FRAME_DURATION_MS;
                default:
                    return 500;
            }
        }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // SECTION 2: RENDERER INTERFACES & IMPLEMENTATIONS
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    /**
     * Base interface for all VFX renderers
     */
    public interface AssetBasedVFXRenderer {
        BufferedImage getFrame(float x, float y, float progress, float opacity);
        long getDurationMs();
        boolean isReady();
        String getName();
        String getAssetKey();
        void cleanup();
    }
    
    /**
     * Smoke effect renderer (18-frame animation)
     */
    public static class SmokeEffectRenderer implements AssetBasedVFXRenderer {
        private BufferedImage[] frames;
        private boolean isReady = false;
        
        public SmokeEffectRenderer() {
            loadFrames();
        }
        
        private void loadFrames() {
            frames = new BufferedImage[AssetRegistry.SMOKE_FRAME_COUNT];
            try {
                for (int i = 0; i < AssetRegistry.SMOKE_FRAME_COUNT; i++) {
                    String path = String.format(AssetRegistry.SMOKE_FRAME_FORMAT, i);
                    File file = new File(path);
                    
                    if (!file.exists()) {
                        System.err.println("[VFX ERROR] Smoke frame not found: " + path);
                        return;
                    }
                    
                    frames[i] = ImageIO.read(file);
                    if (frames[i] == null) {
                        System.err.println("[VFX ERROR] Failed to load smoke frame: " + path);
                        return;
                    }
                }
                
                isReady = true;
                System.out.println("[VFX] SmokeEffectRenderer loaded " + 
                    AssetRegistry.SMOKE_FRAME_COUNT + " frames successfully");
                
            } catch (IOException e) {
                System.err.println("[VFX CRITICAL] IOException loading smoke frames:");
                e.printStackTrace();
                isReady = false;
            }
        }
        
        @Override
        public BufferedImage getFrame(float x, float y, float progress, float opacity) {
            if (!isReady || frames == null || frames.length == 0) {
                return null;
            }
            progress = Math.max(0.0f, Math.min(1.0f, progress));
            int frameIndex = (int)(progress * AssetRegistry.SMOKE_FRAME_COUNT);
            frameIndex = Math.min(frameIndex, AssetRegistry.SMOKE_FRAME_COUNT - 1);
            return frames[frameIndex];
        }
        
        @Override
        public long getDurationMs() {
            return AssetRegistry.SMOKE_DURATION_MS;
        }
        
        @Override
        public boolean isReady() {
            return isReady && frames != null && frames.length > 0;
        }
        
        @Override
        public String getName() {
            return "SmokeEffectRenderer";
        }
        
        @Override
        public String getAssetKey() {
            return "smoke";
        }
        
        @Override
        public void cleanup() {
            if (frames != null) {
                for (int i = 0; i < frames.length; i++) {
                    frames[i] = null;
                }
                frames = null;
            }
            isReady = false;
        }
    }
    
    /**
     * Impact effect renderer (8-frame animation)
     */
    public static class ImpactEffectRenderer implements AssetBasedVFXRenderer {
        private BufferedImage[] frames;
        private boolean isReady = false;
        
        public ImpactEffectRenderer() {
            loadFrames();
        }
        
        private void loadFrames() {
            frames = new BufferedImage[AssetRegistry.IMPACT_SPARK_FRAME_COUNT];
            try {
                for (int i = 0; i < AssetRegistry.IMPACT_SPARK_FRAME_COUNT; i++) {
                    String path = String.format(AssetRegistry.IMPACT_SPARK_FORMAT, i);
                    File file = new File(path);
                    
                    if (!file.exists()) {
                        System.err.println("[VFX ERROR] Impact frame not found: " + path);
                        return;
                    }
                    
                    frames[i] = ImageIO.read(file);
                    if (frames[i] == null) {
                        System.err.println("[VFX ERROR] Failed to load impact frame: " + path);
                        return;
                    }
                }
                
                isReady = true;
                System.out.println("[VFX] ImpactEffectRenderer loaded " + 
                    AssetRegistry.IMPACT_SPARK_FRAME_COUNT + " frames successfully");
                
            } catch (IOException e) {
                System.err.println("[VFX CRITICAL] IOException loading impact frames:");
                e.printStackTrace();
                isReady = false;
            }
        }
        
        @Override
        public BufferedImage getFrame(float x, float y, float progress, float opacity) {
            if (!isReady || frames == null || frames.length == 0) {
                return null;
            }
            progress = Math.max(0.0f, Math.min(1.0f, progress));
            int frameIndex = (int)(progress * AssetRegistry.IMPACT_SPARK_FRAME_COUNT);
            frameIndex = Math.min(frameIndex, AssetRegistry.IMPACT_SPARK_FRAME_COUNT - 1);
            return frames[frameIndex];
        }
        
        @Override
        public long getDurationMs() {
            return AssetRegistry.IMPACT_DURATION_MS;
        }
        
        @Override
        public boolean isReady() {
            return isReady && frames != null && frames.length > 0;
        }
        
        @Override
        public String getName() {
            return "ImpactEffectRenderer";
        }
        
        @Override
        public String getAssetKey() {
            return "impact";
        }
        
        @Override
        public void cleanup() {
            if (frames != null) {
                for (int i = 0; i < frames.length; i++) {
                    frames[i] = null;
                }
                frames = null;
            }
            isReady = false;
        }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // SECTION 3: SPRITE PARTICLE SYSTEM
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    /**
     * Individual sprite particle with animation
     */
    public static class SpriteParticle {
        
        public enum Type {
            BLOOD("blood", AssetRegistry.BLOOD_SPLAT_FORMAT, AssetRegistry.BLOOD_SPLAT_FRAME_COUNT),
            SPARK("spark", AssetRegistry.SPARK_BURST_FORMAT, AssetRegistry.SPARK_BURST_FRAME_COUNT),
            DUST("dust", AssetRegistry.PARTICLE_FORMAT, 6),
            EXPLOSION("explosion", AssetRegistry.PARTICLE_FORMAT, 6),
            COLLECTIBLE("collectible", AssetRegistry.ENERGY_FORMAT, AssetRegistry.ENERGY_FRAME_COUNT),
            HEAL("heal", AssetRegistry.ENERGY_FORMAT, AssetRegistry.ENERGY_FRAME_COUNT),
            DEBRIS("debris", AssetRegistry.PARTICLE_FORMAT, 6);
            
            public final String name;
            public final String assetFormat;
            public final int frameCount;
            
            Type(String name, String assetFormat, int frameCount) {
                this.name = name;
                this.assetFormat = assetFormat;
                this.frameCount = frameCount;
            }
        }
        
        public float x, y;
        public float velocityX, velocityY;
        public float opacity = 1.0f;
        public long lifespan;
        public long createdTime;
        public Type type;
        
        private BufferedImage[] frames;
        private static final Map<Type, BufferedImage[]> frameCache = new HashMap<>();
        
        public SpriteParticle(float x, float y, float velocityX, float velocityY, 
                             long lifespan, Type type) {
            this.x = x;
            this.y = y;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.lifespan = lifespan;
            this.type = type;
            this.createdTime = System.currentTimeMillis();
            loadFrames();
        }
        
        private void loadFrames() {
            if (frameCache.containsKey(type)) {
                frames = frameCache.get(type);
                return;
            }
            
            frames = new BufferedImage[type.frameCount];
            try {
                for (int i = 0; i < type.frameCount; i++) {
                    String path = String.format(type.assetFormat, i);
                    File file = new File(path);
                    
                    if (!file.exists()) {
                        System.err.println("[PARTICLE WARNING] Asset not found: " + path);
                        return;
                    }
                    
                    frames[i] = ImageIO.read(file);
                    if (frames[i] == null) {
                        System.err.println("[PARTICLE WARNING] Failed to load: " + path);
                        return;
                    }
                }
                frameCache.put(type, frames);
            } catch (IOException e) {
                System.err.println("[PARTICLE ERROR] IOException loading " + type.name + " frames:");
                e.printStackTrace();
                frames = null;
            }
        }
        
        public void update(float deltaSeconds, float gravity) {
            velocityY += gravity * deltaSeconds;
            x += velocityX * deltaSeconds;
            y += velocityY * deltaSeconds;
            
            long elapsed = System.currentTimeMillis() - createdTime;
            float progress = (float) elapsed / lifespan;
            opacity = Math.max(0, 1.0f - progress);
        }
        
        public boolean isAlive() {
            return (System.currentTimeMillis() - createdTime) < lifespan;
        }
        
        public boolean isDead() {
            return !isAlive();
        }
        
        public float getAnimationProgress() {
            long elapsed = System.currentTimeMillis() - createdTime;
            return Math.min(1.0f, (float) elapsed / lifespan);
        }
        
        public ParticleFrame getFrame() {
            if (frames == null || frames.length == 0) {
                return null;
            }
            
            float progress = getAnimationProgress();
            int frameIndex = (int)(progress * frames.length);
            frameIndex = Math.min(frameIndex, frames.length - 1);
            
            BufferedImage frame = frames[frameIndex];
            if (frame == null) {
                return null;
            }
            
            int displayX = (int)x - frame.getWidth() / 2;
            int displayY = (int)y - frame.getHeight() / 2;
            
            return new ParticleFrame(frame, displayX, displayY, opacity);
        }
        
        public static class ParticleFrame {
            public final BufferedImage image;
            public final int screenX;
            public final int screenY;
            public final float opacity;
            
            public ParticleFrame(BufferedImage image, int x, int y, float opacity) {
                this.image = image;
                this.screenX = x;
                this.screenY = y;
                this.opacity = opacity;
            }
        }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // SECTION 4: PARTICLE EMITTER
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    /**
     * Manages creation and rendering of particles
     */
    public static class ParticleEmitter {
        
        private List<SpriteParticle> activeParticles = new ArrayList<>();
        private static final int MAX_PARTICLES = 1000;
        
        private long totalSpawned = 0;
        private long totalRendered = 0;
        private long peakParticleCount = 0;
        
        public void update(long deltaTime) {
            float deltaSeconds = deltaTime / 1000.0f;
            float gravity = 300.0f;
            
            for (SpriteParticle p : activeParticles) {
                p.update(deltaSeconds, gravity);
            }
            
            activeParticles.removeIf(SpriteParticle::isDead);
            peakParticleCount = Math.max(peakParticleCount, activeParticles.size());
        }
        
        public List<SpriteParticle.ParticleFrame> getParticleFrames() {
            List<SpriteParticle.ParticleFrame> frames = new ArrayList<>();
            for (SpriteParticle p : activeParticles) {
                if (!p.isDead()) {
                    SpriteParticle.ParticleFrame frame = p.getFrame();
                    if (frame != null) {
                        frames.add(frame);
                        totalRendered++;
                    }
                }
            }
            return frames;
        }
        
        public boolean addParticle(SpriteParticle particle) {
            if (activeParticles.size() < MAX_PARTICLES) {
                activeParticles.add(particle);
                totalSpawned++;
                return true;
            }
            return false;
        }
        
        public void spawnBloodSpray(float x, float y, int count) {
            for (int i = 0; i < count; i++) {
                float angle = (float) (Math.random() * Math.PI * 2);
                float speed = 100 + (float) Math.random() * 150;
                
                float vx = (float) Math.cos(angle) * speed;
                float vy = (float) Math.sin(angle) * speed - 50;
                
                SpriteParticle p = new SpriteParticle(x, y, vx, vy, 
                    400 + (long)(Math.random() * 200), SpriteParticle.Type.BLOOD);
                addParticle(p);
            }
        }
        
        public void spawnSparkBurst(float x, float y, int count) {
            for (int i = 0; i < count; i++) {
                float angle = (float) (Math.random() * Math.PI * 2);
                float speed = 150 + (float) Math.random() * 200;
                
                float vx = (float) Math.cos(angle) * speed;
                float vy = (float) Math.sin(angle) * speed - 30;
                
                SpriteParticle p = new SpriteParticle(x, y, vx, vy, 
                    300 + (long)(Math.random() * 200), SpriteParticle.Type.SPARK);
                addParticle(p);
            }
        }
        
        public void spawnDustCloud(float x, float y, int count) {
            for (int i = 0; i < count; i++) {
                float angle = 45 + (float) Math.random() * 90;
                angle = (float) Math.toRadians(angle);
                float speed = 50 + (float) Math.random() * 100;
                
                float vx = (float) Math.cos(angle) * speed;
                float vy = (float) Math.sin(angle) * speed;
                
                SpriteParticle p = new SpriteParticle(x, y, vx, vy, 
                    500 + (long)(Math.random() * 300), SpriteParticle.Type.DUST);
                addParticle(p);
            }
        }
        
        public void spawnExplosion(float x, float y, int size) {
            int count = size;
            for (int i = 0; i < count; i++) {
                float angle = (float) (Math.random() * Math.PI * 2);
                float speed = 200 + (float) Math.random() * 250;
                
                float vx = (float) Math.cos(angle) * speed;
                float vy = (float) Math.sin(angle) * speed;
                
                SpriteParticle p = new SpriteParticle(x, y, vx, vy, 
                    400 + (long)(Math.random() * 200), SpriteParticle.Type.EXPLOSION);
                addParticle(p);
            }
        }
        
        public void spawnCollectiblePickup(float x, float y, int count) {
            for (int i = 0; i < count; i++) {
                float angle = (float) (Math.random() * Math.PI * 2);
                float speed = 80 + (float) Math.random() * 120;
                
                float vx = (float) Math.cos(angle) * speed;
                float vy = (float) Math.sin(angle) * speed - 80;
                
                SpriteParticle p = new SpriteParticle(x, y, vx, vy, 
                    500 + (long)(Math.random() * 200), SpriteParticle.Type.COLLECTIBLE);
                addParticle(p);
            }
        }
        
        public void spawnHealingEffect(float x, float y, int count) {
            for (int i = 0; i < count; i++) {
                float angle = (float) (Math.random() * Math.PI * 2);
                float speed = 70 + (float) Math.random() * 100;
                
                float vx = (float) Math.cos(angle) * speed;
                float vy = (float) Math.sin(angle) * speed - 100;
                
                SpriteParticle p = new SpriteParticle(x, y, vx, vy, 
                    600 + (long)(Math.random() * 200), SpriteParticle.Type.HEAL);
                addParticle(p);
            }
        }
        
        public void spawnDebris(float x, float y, int count) {
            for (int i = 0; i < count; i++) {
                float angle = (float) (Math.random() * Math.PI * 2);
                float speed = 100 + (float) Math.random() * 150;
                
                float vx = (float) Math.cos(angle) * speed;
                float vy = (float) Math.sin(angle) * speed;
                
                SpriteParticle p = new SpriteParticle(x, y, vx, vy, 
                    700 + (long)(Math.random() * 300), SpriteParticle.Type.DEBRIS);
                addParticle(p);
            }
        }
        
        public int getParticleCount() {
            return activeParticles.size();
        }
        
        public long getTotalSpawned() {
            return totalSpawned;
        }
        
        public long getTotalRendered() {
            return totalRendered;
        }
        
        public long getPeakParticleCount() {
            return peakParticleCount;
        }
        
        public boolean isAtCapacity() {
            return activeParticles.size() >= MAX_PARTICLES;
        }
        
        public void clear() {
            activeParticles.clear();
        }
        
        public void resetStats() {
            totalSpawned = 0;
            totalRendered = 0;
            peakParticleCount = 0;
        }
        
        public String getSummary() {
            return String.format(
                "Particles: %d/%d | Peak: %d | Spawned: %d",
                getParticleCount(), MAX_PARTICLES, peakParticleCount, totalSpawned
            );
        }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // SECTION 5: VFX MANAGER (Main coordinator)
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    /**
     * Central VFX manager coordinating effects and particles
     */
    public static class VFXManager {
        
        private List<VFXEffect> activeEffects = new ArrayList<>();
        private int effectIdCounter = 0;
        private ParticleEmitter particleEmitter;
        
        private AssetBasedVFXRenderer smokeRenderer;
        private AssetBasedVFXRenderer impactRenderer;
        private Map<String, AssetBasedVFXRenderer> rendererCache;
        
        public static final String EXPLOSION = "explosion";
        public static final String IMPACT = "impact";
        public static final String SMOKE = "smoke";
        public static final String BLOOD_SPLAT = "blood_splat";
        
        public VFXManager() {
            this.particleEmitter = new ParticleEmitter();
            this.rendererCache = new HashMap<>();
            
            this.smokeRenderer = new SmokeEffectRenderer();
            this.impactRenderer = new ImpactEffectRenderer();
            
            rendererCache.put("explosion", smokeRenderer);
            rendererCache.put("smoke", smokeRenderer);
            rendererCache.put("impact", impactRenderer);
            rendererCache.put("spark", impactRenderer);
        }
        
        public static class VFXEffect {
            public int effectId;
            public String effectType;
            public float x, y;
            public float vx, vy;
            public long startTime;
            public long duration;
            
            public VFXEffect(int id, String type, float x, float y, long duration) {
                this.effectId = id;
                this.effectType = type;
                this.x = x;
                this.y = y;
                this.startTime = System.currentTimeMillis();
                this.duration = duration;
                this.vx = 0;
                this.vy = 0;
            }
            
            public boolean isAlive() {
                long elapsed = System.currentTimeMillis() - startTime;
                return elapsed < duration;
            }
            
            public float getProgress() {
                long elapsed = System.currentTimeMillis() - startTime;
                return Math.min(1.0f, (float) elapsed / duration);
            }
            
            public float getOpacity() {
                float progress = getProgress();
                if (progress > 0.7f) {
                    return (1.0f - progress) / 0.3f;
                }
                return 1.0f;
            }
            
            public void update(long deltaMs) {
                x += vx * (deltaMs / 1000f);
                y += vy * (deltaMs / 1000f);
            }
        }
        
        public int spawnEffect(String effectType, float x, float y) {
            long duration = AssetRegistry.getEffectDuration(effectType);
            return spawnEffect(effectType, x, y, duration);
        }
        
        public int spawnEffect(String effectType, float x, float y, long duration) {
            if (activeEffects.size() >= AssetRegistry.MAX_ACTIVE_EFFECTS) {
                return -1;
            }
            
            VFXEffect effect = new VFXEffect(effectIdCounter++, effectType, x, y, duration);
            activeEffects.add(effect);
            return effect.effectId;
        }
        
        public int spawnEffectWithVelocity(String effectType, float x, float y, float vx, float vy) {
            int effectId = spawnEffect(effectType, x, y);
            VFXEffect effect = getEffect(effectId);
            if (effect != null) {
                effect.vx = vx;
                effect.vy = vy;
            }
            return effectId;
        }
        
        public VFXEffect getEffect(int effectId) {
            for (VFXEffect effect : activeEffects) {
                if (effect.effectId == effectId) {
                    return effect;
                }
            }
            return null;
        }
        
        public void update(long deltaMs) {
            for (VFXEffect effect : activeEffects) {
                effect.update(deltaMs);
            }
            
            activeEffects.removeIf(effect -> !effect.isAlive());
            particleEmitter.update(deltaMs);
        }
        
        public List<SpriteParticle.ParticleFrame> getRenderFrames() {
            return particleEmitter.getParticleFrames();
        }
        
        @Deprecated
        public void render(Object g) {
            // Deprecated - use getRenderFrames() instead
        }
        
        public int getActiveEffectCount() {
            return activeEffects.size();
        }
        
        public void clear() {
            activeEffects.clear();
            particleEmitter.clear();
        }
        
        public void removeEffect(int effectId) {
            activeEffects.removeIf(e -> e.effectId == effectId);
        }
        
        public List<VFXEffect> getActiveEffects() {
            return new ArrayList<>(activeEffects);
        }
        
        public void spawnBloodSpray(float x, float y, int count) {
            particleEmitter.spawnBloodSpray(x, y, count);
        }
        
        public void spawnSparkBurst(float x, float y, int count) {
            particleEmitter.spawnSparkBurst(x, y, count);
        }
        
        public void spawnDustCloud(float x, float y, int count) {
            particleEmitter.spawnDustCloud(x, y, count);
        }
        
        public void spawnExplosion(float x, float y, int size) {
            particleEmitter.spawnExplosion(x, y, size);
        }
        
        public void spawnCollectiblePickup(float x, float y, int count) {
            particleEmitter.spawnCollectiblePickup(x, y, count);
        }
        
        public void spawnHealingEffect(float x, float y, int count) {
            particleEmitter.spawnHealingEffect(x, y, count);
        }
        
        public String getParticleStats() {
            return particleEmitter.getSummary();
        }
        
        public int getParticleCount() {
            return particleEmitter.getParticleCount();
        }
        
        public String getRendererStatus() {
            StringBuilder sb = new StringBuilder();
            sb.append("[VFX Renderer Status]\n");
            sb.append("Active Effects: ").append(getActiveEffectCount()).append("/")
              .append(AssetRegistry.MAX_ACTIVE_EFFECTS).append("\n");
            sb.append("Active Particles: ").append(getParticleCount()).append("/")
              .append(AssetRegistry.MAX_ACTIVE_PARTICLES).append("\n");
            sb.append("Smoke Renderer: ").append(smokeRenderer.isReady() ? "âœ“ READY" : "âœ— LOADING").append("\n");
            sb.append("Impact Renderer: ").append(impactRenderer.isReady() ? "âœ“ READY" : "âœ— LOADING").append("\n");
            return sb.toString();
        }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // SECTION 6: SPARK EFFECT SYSTEM
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    /**
     * Spark visual effect system for UI and effects
     */
    public static class SparkEffectSystem {
        
        public static final int SPARK_TYPE_01 = 1;  // Yellow/Gold
        public static final int SPARK_TYPE_02 = 2;  // Blue/Cyan
        public static final int SPARK_TYPE_03 = 3;  // Red/Orange
        public static final int SPARK_TYPE_04 = 4;  // Green
        public static final int SPARK_TYPE_05 = 5;  // Purple
        public static final int SPARK_TYPE_06 = 6;  // White/Bright
        public static final int SPARK_TYPE_07 = 7;  // Pink/Magenta
        public static final int SPARK_TYPE_08 = 8;  // Cyan/Neon
        
        private static final String SPARK_BASE = "Resources/industrial-zone/vfx/3 Sparks/";
        private static final int FRAME_COUNT = 4;
        private static final long FRAME_DURATION_MS = 80;
        
        private Map<Integer, InputController.HorizontalSpritesheetLoader> sparkLoaders;
        private List<ActiveSparkEffect> activeEffects;
        
        public SparkEffectSystem() {
            this.sparkLoaders = new HashMap<>();
            this.activeEffects = Collections.synchronizedList(new ArrayList<>());
            loadAllSparkEffects();
        }
        
        private void loadAllSparkEffects() {
            System.out.println("\n[SparkEffectSystem] Loading spark effects...");
            
            int[] sparkTypes = {1, 2, 3, 4, 5, 6, 7, 8};
            int successCount = 0;
            
            for (int type : sparkTypes) {
                try {
                    String filename = String.format("%02d Spark.png", type);
                    String fullPath = SPARK_BASE + filename;
                    
                    File sparkFile = new File(fullPath);
                    if (!sparkFile.exists()) {
                        System.out.println("[SparkEffectSystem] âœ— Spark " + type + " not found: " + fullPath);
                        continue;
                    }
                    
                    InputController.HorizontalSpritesheetLoader loader = new InputController.HorizontalSpritesheetLoader(
                        "Spark_" + type,
                        fullPath,
                        64, 64,
                        FRAME_COUNT
                    );
                    
                    sparkLoaders.put(type, loader);
                    successCount++;
                    System.out.println("[SparkEffectSystem] âœ“ Loaded Spark Type " + type);
                    
                } catch (Exception e) {
                    System.out.println("[SparkEffectSystem] âœ— Failed to load Spark " + type + ": " + e.getMessage());
                }
            }
            
            System.out.println("[SparkEffectSystem] Loaded: " + successCount + " / 8 spark types\n");
        }
        
        public void triggerSpark(int sparkType, int screenX, int screenY) {
            if (!sparkLoaders.containsKey(sparkType)) {
                System.out.println("[SparkEffectSystem] âœ— Unknown spark type: " + sparkType);
                return;
            }
            
            try {
                InputController.HorizontalSpritesheetLoader loader = sparkLoaders.get(sparkType);
                ActiveSparkEffect effect = new ActiveSparkEffect(
                    loader,
                    screenX,
                    screenY,
                    FRAME_DURATION_MS,
                    System.currentTimeMillis()
                );
                
                activeEffects.add(effect);
                System.out.println("[SparkEffectSystem] âœ“ Triggered Spark " + sparkType + 
                                 " at (" + screenX + ", " + screenY + ")");
                
            } catch (Exception e) {
                System.out.println("[SparkEffectSystem] âœ— Error triggering spark: " + e.getMessage());
            }
        }
        
        public List<ActiveSparkEffect> getActiveEffects() {
            activeEffects.removeIf(effect -> effect.isExpired());
            return new ArrayList<>(activeEffects);
        }
        
        public void clearEffects() {
            activeEffects.clear();
        }
        
        public boolean hasActiveEffects() {
            return !getActiveEffects().isEmpty();
        }
        
        public static class ActiveSparkEffect {
            private InputController.HorizontalSpritesheetLoader loader;
            private int screenX, screenY;
            private long startTime;
            private long frameHoldMs;
            private boolean expired = false;
            
            public ActiveSparkEffect(InputController.HorizontalSpritesheetLoader loader, int x, int y, 
                                   long frameHoldMs, long startTime) {
                this.loader = loader;
                this.screenX = x;
                this.screenY = y;
                this.frameHoldMs = frameHoldMs;
                this.startTime = startTime;
            }
            
            public int getCurrentFrame() {
                long elapsed = System.currentTimeMillis() - startTime;
                int frame = (int) (elapsed / frameHoldMs);
                
                if (frame >= FRAME_COUNT) {
                    expired = true;
                    return FRAME_COUNT - 1;
                }
                
                return Math.min(frame, FRAME_COUNT - 1);
            }
            
            public boolean isExpired() {
                return expired || (System.currentTimeMillis() - startTime) > (FRAME_COUNT * frameHoldMs);
            }
            
            public int getScreenX() { return screenX; }
            public int getScreenY() { return screenY; }
            public InputController.HorizontalSpritesheetLoader getLoader() { return loader; }
        }
        
        public static int getSparkTypeByColor(String colorName) {
            switch (colorName.toLowerCase()) {
                case "gold":
                case "yellow":
                    return SPARK_TYPE_01;
                case "blue":
                case "cyan":
                    return SPARK_TYPE_02;
                case "red":
                case "orange":
                    return SPARK_TYPE_03;
                case "green":
                    return SPARK_TYPE_04;
                case "purple":
                case "violet":
                    return SPARK_TYPE_05;
                case "white":
                case "bright":
                    return SPARK_TYPE_06;
                case "pink":
                case "magenta":
                    return SPARK_TYPE_07;
                case "neon":
                    return SPARK_TYPE_08;
                default:
                    return SPARK_TYPE_06;
            }
        }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // SECTION 7: VSX VISUAL EFFECTS SYSTEM
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    /**
     * Advanced raster VFX system with direct PNG rendering
     */
    public static class VSXVisualEffectsSystem {
        
        public enum EffectType {
            MUZZLE_FLASH, GUNSMOKE, TRACER_THIN, TRACER_THICK, LASER_BEAM,
            HIT_SPARK, HIT_EXPLOSION, HIT_DUST, BLOOD_SPRAY,
            EXPLOSION_LARGE, EXPLOSION_SMALL, FIRE_BURST, SMOKE_CLOUD,
            PARTICLE_SCATTER, PARTICLE_RAIN, PARTICLE_SPARKLE,
            DAMAGE_NUMBER, HEAL_GLOW, CRITICAL_HIT, STATUS_BUFF
        }
        
        public static class VisualEffect {
            public String effectKey;
            public EffectType type;
            public float x, y;
            public float velocityX = 0;
            public float velocityY = 0;
            public float lifetime;
            public float maxLifetime;
            public float scale = 1.0f;
            public float rotation = 0;
            public float alpha = 1.0f;
            public String spriteKey;
            public int frameIndex = 0;
            public float frameTime = 0;
            public float frameDuration = 0.1f;
            public boolean isLooping = false;
            public Object data;
            public boolean isActive = true;
            
            public VisualEffect(String effectKey, EffectType type, float x, float y,
                               String spriteKey, float maxLifetime) {
                this.effectKey = effectKey;
                this.type = type;
                this.x = x;
                this.y = y;
                this.spriteKey = spriteKey;
                this.maxLifetime = maxLifetime;
                this.lifetime = 0;
            }
            
            public void update(float deltaTime) {
                if (!isActive) return;
                
                lifetime += deltaTime;
                x += velocityX * deltaTime;
                y += velocityY * deltaTime;
                
                float fadeStartTime = maxLifetime * 0.8f;
                if (lifetime >= fadeStartTime) {
                    float fadeProgress = (lifetime - fadeStartTime) / (maxLifetime * 0.2f);
                    alpha = Math.max(0, 1.0f - fadeProgress);
                }
                
                if (lifetime >= maxLifetime) {
                    isActive = false;
                }
            }
            
            public void setAnimation(float frameDuration, boolean looping) {
                this.frameDuration = frameDuration;
                this.isLooping = looping;
            }
            
            public void setDrift(float velocityX, float velocityY) {
                this.velocityX = velocityX;
                this.velocityY = velocityY;
            }
            
            public void setScale(float scale) {
                this.scale = scale;
            }
            
            public float getLifetimePercent() {
                return Math.max(0, 1.0f - (lifetime / maxLifetime));
            }
            
            public boolean isExpired() {
                return !isActive || lifetime >= maxLifetime;
            }
        }
        
        private List<VisualEffect> activeEffects;
        private Map<String, VisualEffect> effectMap;
        private Map<String, BufferedImage> spriteCache;
        private int effectIDCounter = 0;
        
        private String level1AssetPath = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/";
        private String level2AssetPath = "Resources/industrial-zone/1 Tiles/power-station-level-2/";
        private String currentLevelPath = level1AssetPath;
        
        private static final int MAX_EFFECTS = 1024;
        
        public VSXVisualEffectsSystem() {
            this.activeEffects = new ArrayList<>();
            this.effectMap = new HashMap<>();
            this.spriteCache = new HashMap<>();
        }
        
        public void setCurrentLevel(int levelNumber) {
            if (levelNumber == 1) {
                this.currentLevelPath = level1AssetPath;
            } else if (levelNumber == 2) {
                this.currentLevelPath = level2AssetPath;
            }
        }
        
        private BufferedImage loadSpriteFromFile(String spriteKey) {
            if (spriteCache.containsKey(spriteKey)) {
                return spriteCache.get(spriteKey);
            }
            
            String filePath = mapSpriteKeyToPath(spriteKey);
            if (filePath == null) {
                System.err.println("ERROR: No file mapping for sprite key: " + spriteKey);
                return null;
            }
            
            try {
                File imageFile = new File(filePath);
                if (!imageFile.exists()) {
                    System.err.println("ERROR: PNG file not found: " + filePath);
                    return null;
                }
                
                BufferedImage image = ImageIO.read(imageFile);
                spriteCache.put(spriteKey, image);
                System.out.println("âœ“ Loaded raster sprite: " + spriteKey + " from " + filePath);
                return image;
                
            } catch (Exception e) {
                System.err.println("ERROR loading sprite " + spriteKey + ": " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        
        private String mapSpriteKeyToPath(String spriteKey) {
            if (spriteKey.equals("MUZZLE_FLASH")) {
                return currentLevelPath + "2 Background_level_1/Effects/muzzle_flash.png";
            }
            if (spriteKey.equals("GUNSMOKE")) {
                return currentLevelPath + "2 Background_level_1/Effects/gunsmoke.png";
            }
            if (spriteKey.equals("TRACER_TYPE_A_NARROW")) {
                return currentLevelPath + "2 Background_level_1/Effects/tracer_narrow.png";
            }
            if (spriteKey.equals("TRACER_TYPE_B_THICK")) {
                return currentLevelPath + "2 Background_level_1/Effects/tracer_thick.png";
            }
            if (spriteKey.equals("HIT_SPARK")) {
                return currentLevelPath + "2 Background_level_1/Effects/hit_spark.png";
            }
            if (spriteKey.equals("HIT_EXPLOSION")) {
                return currentLevelPath + "2 Background_level_1/Effects/hit_explosion.png";
            }
            if (spriteKey.equals("HIT_DUST")) {
                return currentLevelPath + "2 Background_level_1/Effects/hit_dust.png";
            }
            if (spriteKey.equals("EXPLOSION_LARGE")) {
                return currentLevelPath + "2 Background_level_1/Effects/explosion_large.png";
            }
            if (spriteKey.equals("EXPLOSION_SMALL")) {
                return currentLevelPath + "2 Background_level_1/Effects/explosion_small.png";
            }
            if (spriteKey.equals("PARTICLE_SMALL")) {
                return currentLevelPath + "2 Background_level_1/Effects/particle_small.png";
            }
            if (spriteKey.equals("HEAL_SPARKLE")) {
                return currentLevelPath + "2 Background_level_1/Effects/heal_sparkle.png";
            }
            if (spriteKey.equals("TEXT_DAMAGE")) {
                return currentLevelPath + "2 Background_level_1/Effects/text_damage.png";
            }
            
            System.err.println("WARNING: Unknown sprite key, no mapping: " + spriteKey);
            return null;
        }
        
        public VisualEffect spawnEffect(EffectType type, float x, float y,
                                        String spriteKey, float duration) {
            if (activeEffects.size() >= MAX_EFFECTS) {
                return null;
            }
            
            String effectKey = "effect_" + (effectIDCounter++);
            VisualEffect effect = new VisualEffect(effectKey, type, x, y, spriteKey, duration);
            
            activeEffects.add(effect);
            effectMap.put(effectKey, effect);
            
            return effect;
        }
        
        public VisualEffect spawnMuzzleFlash(float x, float y) {
            VisualEffect effect = spawnEffect(EffectType.MUZZLE_FLASH, x, y,
                "MUZZLE_FLASH", 0.15f);
            if (effect != null) {
                effect.setScale(1.2f);
                effect.setAnimation(0.05f, false);
            }
            return effect;
        }
        
        public VisualEffect spawnTracer(float startX, float startY, float endX, float endY) {
            VisualEffect effect = spawnEffect(EffectType.TRACER_THIN, startX, startY,
                "TRACER_TYPE_A_NARROW", 0.1f);
            if (effect != null) {
                float dirX = endX - startX;
                float dirY = endY - startY;
                float distance = (float) Math.sqrt(dirX * dirX + dirY * dirY);
                if (distance > 0) {
                    effect.setDrift(dirX / 0.1f, dirY / 0.1f);
                }
            }
            return effect;
        }
        
        public VisualEffect spawnImpactEffect(float x, float y) {
            VisualEffect effect = spawnEffect(EffectType.HIT_SPARK, x, y,
                "HIT_SPARK", 0.4f);
            if (effect != null) {
                effect.setAnimation(0.08f, false);
                effect.setScale(0.8f);
            }
            return effect;
        }
        
        public VisualEffect spawnExplosion(float x, float y, boolean isLarge) {
            String spriteKey = isLarge ? "EXPLOSION_LARGE" : "EXPLOSION_SMALL";
            float duration = isLarge ? 0.6f : 0.4f;
            EffectType type = isLarge ? EffectType.EXPLOSION_LARGE : EffectType.EXPLOSION_SMALL;
            
            VisualEffect effect = spawnEffect(type, x, y, spriteKey, duration);
            if (effect != null) {
                effect.setAnimation(0.1f, false);
                effect.setScale(isLarge ? 1.5f : 1.0f);
            }
            return effect;
        }
        
        public void update(float deltaTime) {
            for (int i = activeEffects.size() - 1; i >= 0; i--) {
                VisualEffect effect = activeEffects.get(i);
                effect.update(deltaTime);
                
                if (effect.isExpired()) {
                    activeEffects.remove(i);
                    effectMap.remove(effect.effectKey);
                }
            }
        }
        
        public void render(java.awt.Graphics2D g) {
            for (VisualEffect effect : activeEffects) {
                if (!effect.isActive) continue;
                
                BufferedImage spriteImage = loadSpriteFromFile(effect.spriteKey);
                if (spriteImage == null) {
                    continue;
                }
                
                java.awt.Composite originalComposite = g.getComposite();
                java.awt.geom.AffineTransform originalTransform = g.getTransform();
                
                g.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, effect.alpha));
                
                int scaledWidth = (int)(spriteImage.getWidth() * effect.scale);
                int scaledHeight = (int)(spriteImage.getHeight() * effect.scale);
                
                if (effect.rotation != 0) {
                    double radians = Math.toRadians(effect.rotation);
                    g.translate(effect.x + scaledWidth / 2, effect.y + scaledHeight / 2);
                    g.rotate(radians);
                    g.translate(-scaledWidth / 2, -scaledHeight / 2);
                }
                
                g.drawImage(spriteImage, 
                    (int)effect.x, (int)effect.y, 
                    scaledWidth, scaledHeight, 
                    null);
                
                g.setTransform(originalTransform);
                g.setComposite(originalComposite);
            }
        }
        
        public void removeEffect(String effectKey) {
            VisualEffect effect = effectMap.remove(effectKey);
            if (effect != null) {
                activeEffects.remove(effect);
            }
        }
        
        public void clearAll() {
            activeEffects.clear();
            effectMap.clear();
        }
        
        public int getActiveEffectCount() {
            return activeEffects.size();
        }
        
        public void clearSpriteCache() {
            int cachedCount = spriteCache.size();
            spriteCache.clear();
            System.out.println("âœ“ Cleared " + cachedCount + " cached PNG sprites");
        }
    }
}


