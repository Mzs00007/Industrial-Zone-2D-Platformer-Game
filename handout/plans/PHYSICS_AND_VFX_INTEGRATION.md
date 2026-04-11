# PHYSICS & VFX INTEGRATION DESIGN
## Projectile Trajectory, Impact Effects, and Visual Feedback System

---

## 1. PROJECTILE PHYSICS MODELS

### 1.1 Trajectory Types

#### STRAIGHT Trajectory
**Used By**: Pistols (A, B), Rifles (G, H), Sci-Fi (I)

```java
class StraightTrajectory implements Trajectory {
    private Vector2D velocity;
    
    @Override
    public void update(Projectile proj, long deltaTime) {
        // Simple linear motion
        Point pos = proj.getPosition();
        pos.x += velocity.x;
        pos.y += velocity.y;
        
        // Bullet sprite rotates toward velocity
        proj.setRotation(Math.atan2(velocity.y, velocity.x));
    }
    
    @Override
    public Vector2D getVelocityAt(long time) {
        return velocity;  // Always same
    }
}
```

**Physics Parameters**:
- Velocity range: 6-16 px/frame (based on gun type)
- No gravity or air resistance
- Linear distance formula: distance = velocity × time

**Use Cases**:
- Realistic bullet behavior
- Hitscan-like precision
- Best for single-target weapons

#### ARC Trajectory
**Used By**: Special Weapons (J), Enemy rugby ball, explosion arcs

```java
class ArcTrajectory implements Trajectory {
    private Vector2D initialVelocity;
    private double gravity = 0.3;  // pixels/frame²
    private Vector2D currentVelocity;
    private long startTime;
    
    public ArcTrajectory(Vector2D initialVel, Point target) {
        this.initialVelocity = initialVel;
        this.currentVelocity = initialVel.copy();
        this.startTime = System.currentTimeMillis();
    }
    
    @Override
    public void update(Projectile proj, long deltaTime) {
        // Apply gravity to Y velocity only
        currentVelocity.y += gravity;
        
        // Update position
        Point pos = proj.getPosition();
        pos.x += currentVelocity.x;
        pos.y += currentVelocity.y;
        
        // Rotate bullet toward trajectory
        double speed = currentVelocity.length();
        proj.setRotation(Math.atan2(currentVelocity.y, currentVelocity.x));
        
        // Check for ground impact
        if (pos.y >= GROUND_LEVEL && currentVelocity.y > 0) {
            proj.onGroundImpact();
        }
    }
    
    @Override
    public Vector2D getVelocityAt(long time) {
        Vector2D vel = initialVelocity.copy();
        vel.y += gravity * (time - startTime) / 16.0;  // 16ms per frame
        return vel;
    }
    
    /**
     * Calculate launch angle needed to hit target
     * Using kinematic equations: y = x*tan(θ) - (g*x²)/(2*v²*cos²(θ))
     */
    public double calculateLaunchAngle(Point from, Point to, double speed) {
        double dx = to.x - from.x;
        double dy = to.y - from.y;
        
        // Solve quadratic equation for angle
        double v2 = speed * speed;
        double g = gravity;
        
        // Formula: θ = arctan((v² ± sqrt(v⁴ - g(g*dx² + 2*v²*dy))) / (g*dx))
        double discriminant = v2*v2 - g*(g*dx*dx + 2*v2*dy);
        
        if (discriminant < 0) {
            return 45.0;  // Can't reach - use fallback angle
        }
        
        double angle1 = Math.atan2(v2 + Math.sqrt(discriminant), g*dx);
        double angle2 = Math.atan2(v2 - Math.sqrt(discriminant), g*dx);
        
        // Return lower angle (longer arc)
        return Math.min(angle1, angle2);
    }
}
```

**Physics Parameters** (RugbyGuy ball):
- Initial velocity: 10 px/frame
- Gravity: 0.3 px/frame²
- Time to peak: 33 frames (0.55 seconds)
- Max range: ~200 pixels
- Arc angle: 45° for maximum range

**Use Cases**:
- Grenades and throwables
- Ball-based weapons
- Area denial attacks

#### HOMING Trajectory
**Used By**: Enemy orbs (ArmouredKnight), Returning projectiles

```java
class HomingTrajectory implements Trajectory {
    private Entity target;
    private Vector2D velocity;
    private double maxTurnSpeed = 0.1;  // radians per frame
    private double speed = 8.0;  // px/frame
    
    public HomingTrajectory(Entity target, Vector2D initialVel) {
        this.target = target;
        this.velocity = initialVel;
    }
    
    @Override
    public void update(Projectile proj, long deltaTime) {
        if (target == null || !target.isAlive()) {
            // Target lost - continue straight
            Point pos = proj.getPosition();
            pos.x += velocity.x;
            pos.y += velocity.y;
            return;
        }
        
        // Calculate direction to target
        Point projPos = proj.getPosition();
        Point targetPos = target.getPosition();
        Vector2D toTarget = new Vector2D(
            targetPos.x - projPos.x,
            targetPos.y - projPos.y
        );
        toTarget.normalize();
        
        // Smoothly turn toward target
        double currentAngle = Math.atan2(velocity.y, velocity.x);
        double targetAngle = Math.atan2(toTarget.y, toTarget.x);
        double angleDiff = targetAngle - currentAngle;
        
        // Normalize angle difference to [-π, π]
        while (angleDiff > Math.PI) angleDiff -= 2*Math.PI;
        while (angleDiff < -Math.PI) angleDiff += 2*Math.PI;
        
        // Apply turn with max speed constraint
        double turnAmount = Math.max(-maxTurnSpeed, Math.min(maxTurnSpeed, angleDiff));
        double newAngle = currentAngle + turnAmount;
        
        // Update velocity
        velocity.x = speed * Math.cos(newAngle);
        velocity.y = speed * Math.sin(newAngle);
        
        // Move projectile
        projPos.x += velocity.x;
        projPos.y += velocity.y;
        
        // Rotate sprite
        proj.setRotation(newAngle);
    }
}
```

**Physics Parameters**:
- Base speed: 8 px/frame
- Turn rate: 0.1 rad/frame (~5.7° per frame)
- Will pursue target until destroyed or timeout
- Loses lock if target moves out of range (500px)

#### CURVED Trajectory
**Used By**: Wave/spiral attacks, unique weapon effects

```java
class CurvedTrajectory implements Trajectory {
    private Vector2D baseVelocity;
    private double waveAmplitude = 3.0;  // Pixels
    private double waveFrequency = 0.1;   // Radians per frame
    private long startTime;
    
    public CurvedTrajectory(Vector2D baseVel) {
        this.baseVelocity = baseVel;
        this.startTime = System.currentTimeMillis();
    }
    
    @Override
    public void update(Projectile proj, long deltaTime) {
        long elapsed = System.currentTimeMillis() - startTime;
        double wavePhase = waveFrequency * elapsed / 16.0;  // 16ms per frame
        
        // Basic motion
        Point pos = proj.getPosition();
        pos.x += baseVelocity.x;
        
        // Add wave motion perpendicular to base velocity
        pos.y += baseVelocity.y + waveAmplitude * Math.sin(wavePhase);
        
        // Rotate to face direction of travel
        Vector2D perp = new Vector2D(-baseVelocity.y, baseVelocity.x);
        double waveOffset = waveAmplitude * Math.cos(wavePhase);
        double angle = Math.atan2(baseVelocity.y + waveOffset, baseVelocity.x);
        proj.setRotation(angle);
    }
}
```

---

## 2. IMPACT & COLLISION SYSTEM

### 2.1 Impact Effect Types

#### SPLAT Impact
**Used By**: Bullets hitting enemies

```java
class SplatImpactEffect extends ImpactEffect {
    private int particleCount = 12;
    private double spreadAngle = 2 * Math.PI;  // Full circle
    private double particleSpeed = 3.0;
    private Color splatColor;
    
    public void spawn(Point impactPoint, int damageDealt) {
        // Determine color based on cause
        if (this.cause == Cause.BLOOD) {
            splatColor = new Color(180, 0, 0);  // Red
        } else if (this.cause == Cause.METAL_SPARK) {
            splatColor = new Color(255, 165, 0);  // Orange
        }
        
        // Spawn particles radiating outward
        for (int i = 0; i < particleCount; i++) {
            double angle = (2 * Math.PI * i) / particleCount;
            double vx = particleSpeed * Math.cos(angle);
            double vy = particleSpeed * Math.sin(angle);
            
            Particle p = new Particle(
                impactPoint,
                new Vector2D(vx, vy),
                splatColor,
                500  // Lifetime 500ms
            );
            
            world.addParticle(p);
        }
        
        // Render static splat sprite at impact point
        BufferedImage splatSprite = getSplatSprite();
        world.addStaticEffect(impactPoint, splatSprite, 1000);  // 1 second display
    }
}
```

#### EXPLOSION Impact
**Used By**: Special weapons (TypeJ), area damage

```java
class ExplosionImpactEffect extends ImpactEffect {
    private int explosionRadius = 100;
    private int baseDamage = 50;
    private int particleCount = 24;
    
    public void spawn(Point epicenter, int damageMultiplier) {
        // Create expanding blast ring
        for (int i = 0; i < 30; i++) {
            // Expanding ring animation
            int ringRadius = (i + 1) * explosionRadius / 30;
            drawExplosionRing(epicenter, ringRadius, i * 16);  // 16ms per frame
        }
        
        // Spawn particles in all directions
        for (int i = 0; i < particleCount; i++) {
            double angle = (2 * Math.PI * i) / particleCount;
            double speed = 5.0 + Math.random() * 2.0;
            
            Particle p = new Particle(
                epicenter,
                new Vector2D(speed * Math.cos(angle), speed * Math.sin(angle)),
                new Color(255, 100, 0),  // Orange
                800
            );
            world.addParticle(p);
        }
        
        // Query all entities in explosion radius
        List<Entity> in BlastRadius = world.getEntitiesInRadius(epicenter, explosionRadius);
        for (Entity e : inBlastRadius) {
            // Damage scales by distance
            double distance = epicenter.distance(e.getPosition());
            double damageScaling = 1.0 - (distance / explosionRadius);
            int damage = (int)(baseDamage * damageScaling * damageMultiplier);
            e.takeDamage(damage);
            
            // Knockback scales by distance too
            Vector2D toEntity = new Vector2D(
                e.getPosition().x - epicenter.x,
                e.getPosition().y - epicenter.y
            );
            toEntity.normalize();
            double knockback = 10.0 * damageScaling;
            e.applyKnockback(toEntity.scale(knockback));
        }
    }
}
```

#### RICOCHET Impact
**Used By**: Bullets hitting walls/platforms

```java
class RicochetImpactEffect extends ImpactEffect {
    private int bounceCount = 3;
    private double bounceFriction = 0.6;
    
    public Projectile spawn(Projectile orig, Point impactPoint, Vector2D surfaceNormal) {
        // Play ricochet sound
        world.playSound("ricochet", impactPoint);
        
        // Calculate bounce velocity
        Vector2D reflected = reflect(orig.getVelocity(), surfaceNormal);
        reflected = reflected.scale(bounceFriction);
        
        // Create spark effects
        int sparkCount = 8;
        for (int i = 0; i < sparkCount; i++) {
            double angle = -Math.PI + (2 * Math.PI * i / sparkCount);
            Vector2D sparkVel = new Vector2D(
                2.0 * Math.cos(angle),
                1.0 * Math.sin(angle)
            );
            
            Particle spark = new Particle(
                impactPoint,
                sparkVel,
                new Color(255, 200, 100),
                300
            );
            world.addParticle(spark);
        }
        
        // Return bounced projectile if bounces remain
        if (bounceCount > 0) {
            orig.setVelocity(reflected);
            orig.setPosition(impactPoint);
            return orig;
        }
        return null;
    }
    
    private Vector2D reflect(Vector2D incoming, Vector2D normal) {
        // R = I - 2(I·N)N
        double dotProduct = incoming.dot(normal);
        return incoming.subtract(normal.scale(2 * dotProduct));
    }
}
```

#### SCREEN SHAKE
**Used By**: Heavy weapons, explosions

```java
class ScreenShakeEffect extends ImpactEffect {
    private double intensity;
    private long duration;
    
    public void apply(double intensity, long duration) {
        this.intensity = intensity;
        this.duration = duration;
        
        // Over the duration, gradually reduce intensity
        // Use perlin noise or sine wave for organic shake
        long startTime = System.currentTimeMillis();
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed >= duration) {
                executor.shutdown();
                return;
            }
            
            // Sine wave damping
            double damping = 1.0 - (double)elapsed / duration;
            double currentIntensity = intensity * damping;
            
            // Random offset
            double offsetX = (Math.random() - 0.5) * currentIntensity * 2;
            double offsetY = (Math.random() - 0.5) * currentIntensity * 2;
            
            camera.setOffset(offsetX, offsetY);
        }, 0, 16, TimeUnit.MILLISECONDS);  // Update every frame
    }
}
```

---

## 3. DAMAGE CALCULATION SYSTEM

### 3.1 Damage Formula

```java
class DamageCalculator {
    /**
     * Calculate final damage with all modifiers
     */
    public static int calculateDamage(
        Projectile projectile,
        Entity target,
        Point impactPoint,
        DamageContext context) {
        
        // Base damage from gun
        int baseDamage = projectile.getBaseDamage();
        
        // Modifier 1: Distance falloff (for arc/area attacks)
        double distanceFactor = 1.0;
        if (projectile.hasDistanceFalloff()) {
            double distance = projectile.getDistanceTraveled();
            double maxRange = projectile.getMaxRange();
            distanceFactor = 1.0 - Math.pow(distance / maxRange, 2);  // Quadratic falloff
        }
        
        // Modifier 2: Target armor/resistance
        double armorFactor = 1.0 - (target.getArmor() * 0.01);
        armorFactor = Math.max(0.25, armorFactor);  // Minimum 25% damage
        
        // Modifier 3: Hit location
        double locationFactor = 1.0;
        if (context.hitLocation == HitLocation.HEAD) {
            locationFactor = 1.5;  // Critical
        } else if (context.hitLocation == HitLocation.LIMB) {
            locationFactor = 0.75;
        }
        
        // Modifier 4: Difficulty scaling
        double difficultyFactor = 1.0;
        switch (game.getDifficulty()) {
            case EASY: difficultyFactor = 0.7; break;
            case NORMAL: difficultyFactor = 1.0; break;
            case HARD: difficultyFactor = 1.3; break;
            case NIGHTMARE: difficultyFactor = 1.7; break;
        }
        
        // Apply all modifiers
        int finalDamage = (int)(baseDamage * 
            distanceFactor * 
            armorFactor * 
            locationFactor * 
            difficultyFactor);
        
        // Damage number floating damage indicator
        context.world.createFloatingDamageNumber(
            impactPoint,
            finalDamage,
            locationFactor > 1.0  // Is it a crit?
        );
        
        return finalDamage;
    }
}
```

### 3.2 Gun-Specific Damage Scaling

| Gun Type | Base Damage | DPS (at fire rate) | Notes |
|----------|------------|------------------|-------|
| Pistol A | 15 HP | 30 DPS | Standard issue |
| Pistol B | 15 HP | 30 DPS | Alternative |
| Compact C-E | 12 HP | 36 DPS (3 fire/s) | High rate of fire |
| Detail F | 18 HP | 36 DPS | Balanced |
| Rifle G-H | 25 HP | 25 DPS | High single-shot |
| Sci-Fi I | 20 HP | 20 DPS (1 fire/s) | Penetrating |
| Special J | 35 HP (+ 50 area) | 17.5 DPS | Explosive |

---

## 4. KNOCKBACK & PHYSICS EFFECTS

### 4.1 Knockback Calculation

```java
class KnockbackSystem {
    public static void applyKnockback(
        Entity target,
        Projectile projectile,
        Point impactPoint,
        Vector2D impactNormal) {
        
        // Calculate knockback force
        double gunPower = projectile.getBaseKnockback();
        double mass = target.getMass();
        double force = gunPower / mass;
        
        // Direction: away from impact point
        Vector2D knockbackDir = new Vector2D(
            target.getPosition().x - impactPoint.x,
            target.getPosition().y - impactPoint.y
        );
        knockbackDir.normalize();
        
        // Apply knockback
        Vector2D knockbackVel = knockbackDir.scale(force);
        target.applyVelocity(knockbackVel);
        
        // Knockback modifiers by gun type
        switch (projectile.getGunType()) {
            case PISTOL_A:
            case PISTOL_B:
                force *= 0.8;  // Light knockback
                break;
            case RIFLE_G:
            case RIFLE_H:
                force *= 1.2;  // Heavy knockback
                break;
            case SPECIAL_J:
                force *= 1.5;  // Huge knockback
                break;
        }
        
        // Environmental interaction
        if (target.isOnGround() && knockbackVel.y > 0) {
            target.jump(knockbackVel.y);  // Launch airborne
        }
    }
}
```

---

## 5. VISUAL FEEDBACK SYSTEM

### 5.1 Damage Number Floating Text

```java
class FloatingDamageNumber {
    private Point position;
    private int damage;
    private boolean isCritical;
    private long spawnTime;
    private long duration = 2000;  // 2 seconds
    
    private Vector2D velocity = new Vector2D(0, -1);  // Float upward
    
    public void render(Graphics2D g, long currentTime) {
        long elapsed = currentTime - spawnTime;
        
        if (elapsed > duration) {
            return;  // Fade complete
        }
        
        // Calculate alpha fade
        float alpha = 1.0f - (float)elapsed / duration;
        
        // Move upward
        position.y += velocity.y;
        
        // Render text
        Font font = isCritical ? 
            new Font("Arial", Font.BOLD, 24) : 
            new Font("Arial", Font.PLAIN, 20);
        g.setFont(font);
        
        Color color = isCritical ? 
            new Color(255, 0, 0, (int)(255 * alpha)) :      // Red for crits
            new Color(255, 255, 0, (int)(255 * alpha));     // Yellow for normal
        
        g.setColor(color);
        g.drawString(
            String.format("%d", -damage),  // Negative to show damage
            (int)position.x,
            (int)position.y
        );
    }
}
```

### 5.2 Enemy Flinch Animation

```java
class FlinchAnimationSystem {
    public static void playFlinch(Enemy enemy, Vector2D hitDirection) {
        // Get flinch animation based on damage
        int damage = enemy.getLastDamageTaken();
        AnimationType flinchType;
        
        if (damage < 10) {
            flinchType = AnimationType.FLINCH_LIGHT;
            duration = 200;  // 200ms
        } else if (damage < 30) {
            flinchType = AnimationType.FLINCH_MEDIUM;
            duration = 400;
        } else {
            flinchType = AnimationType.FLINCH_HEAVY;
            duration = 600;
        }
        
        enemy.playAnimation(flinchType, duration);
        
        // Visual impact effect
        createImpactFlash(enemy.getPosition(), hitDirection);
    }
    
    private static void createImpactFlash(Point pos, Vector2D direction) {
        // White flash at impact point
        VisualEffect flash = new VisualEffect(
            pos,
            EffectType.IMPACT_FLASH,
            100  // 100ms duration
        );
        world.addEffect(flash);
        
        // Knockback stagger animation toward hit direction
        // Enemy leans back in response to hit
    }
}
```

---

## 6. ENEMY PROJECTILE VFX

### 6.1 RugbyGuy Ball Physics

```java
class RugbyBallProjectile extends Projectile {
    private Vector2D spin;  // Rotation for visual effect
    private boolean isBouncingOffGround;
    
    public RugbyBallProjectile(Point startPos, Point targetPos, double power) {
        super(startPos, RUGBY_BALL_SPRITE, 10.0);
        
        // Calculate arc trajectory
        double distance = startPos.distance(targetPos);
        double angle = calculateArcAngle(distance, power);  // 45° optimal
        
        Vector2D toTarget = new Vector2D(
            targetPos.x - startPos.x,
            targetPos.y - startPos.y
        );
        
        // Launch velocity
        velocity.x = 10.0 * Math.cos(angle);
        velocity.y = -10.0 * Math.sin(angle);  // Negative = upward
        
        // Ball spin
        spin = new Vector2D(0.05, 0);  // Rotates as it travels
    }
    
    @Override
    public void update(long deltaTime) {
        // Standard arc physics
        super.update(deltaTime);
        
        // Apply spin rotation
        rotation += spin.x;
        
        // Rugby ball trail particles
        if (random.nextInt(100) < 30) {  // 30% chance per frame
            spawnTrailParticle();
        }
    }
    
    @Override
    public void onHit(Entity target, Point impactPoint) {
        // Rugby ball creates area damage
        damageArea(impactPoint, 50, 80);  // 50px radius, 80 damage
        
        // Explosion effect
        world.playEffect(
            EffectType.RUGBY_IMPACT,
            impactPoint,
            velocity
        );
        
        isAlive = false;
    }
}
```

### 6.2 Armored Knight Ghost Orb

```java
class GhostOrbProjectile extends Projectile {
    private Entity target;
    private List<Point> trails = new ArrayList<>();  // Ghost trail
    
    public GhostOrbProjectile(Point startPos, Entity targetEntity) {
        super(startPos, GHOST_ORB_SPRITE, 8.0);
        this.target = targetEntity;
        
        // Homing trajectory
        trajectory = new HomingTrajectory(target, velocity);
    }
    
    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        
        // Record trailing positions for visual effect
        if (trails.size() > 10) {
            trails.remove(0);  // Keep last 10 positions
        }
        trails.add((Point)position.clone());
        
        // Pulsing transparency
        long time = System.currentTimeMillis();
        opacity = 0.7f + 0.3f * (float)Math.sin(time / 100.0);
    }
    
    @Override
    public void render(Graphics2D g) {
        // Draw ghost trail
        g.setColor(new Color(100, 150, 255, 50));
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(2));
        
        for (int i = 0; i < trails.size() - 1; i++) {
            Point p1 = trails.get(i);
            Point p2 = trails.get(i + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
        
        g.setStroke(oldStroke);
        
        // Draw orb with glow
        super.render(g);
        
        // Glow effect
        g.setColor(new Color(100, 150, 255, 30 * (int)opacity));
        g.fillOval(
            (int)(position.x - 15),
            (int)(position.y - 15),
            30, 30
        );
    }
    
    @Override
    public void onHit(Entity target, Point impactPoint) {
        // Creates blue explosion
        world.playEffect(EffectType.GHOST_EXPLOSION, impactPoint);
        
        // Homing orb splits into 3 smaller orbs?
        // Or returns to enemy for another shot?
        
        isAlive = false;
    }
}
```

### 6.3 HoverPlatform Capsule Attack

```java
class CapsuleProjectileAttack extends Projectile {
    private int animationFrame = 0;
    private int maxFrames = 7;  // From file: 7Frames1Row
    
    public CapsuleProjectileAttack(Point dropPoint, int fallDuration) {
        super(dropPoint, CAPSULE_SPRITE_SHEET, 0);  // No horizontal velocity
        
        // Capsule falls vertically
        trajectory = new StraightTrajectory(new Vector2D(0, 5));  // Falls at 5px/frame
        damageOnHit = 40;
    }
    
    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        
        // Animate capsule sprite
        animationFrame = (int)(System.currentTimeMillis() / 100) % maxFrames;
    }
    
    @Override
    public BufferedImage getSprite() {
        // Return frame from capsule spritesheet
        return spritesheet.getSubimage(
            animationFrame * FRAME_WIDTH,
            0,
            FRAME_WIDTH,
            FRAME_HEIGHT
        );
    }
    
    @Override
    public void onHit(Entity target, Point impactPoint) {
        // Creates impact burst
        for (int i = 0; i < 20; i++) {
            double angle = 2 * Math.PI * i / 20;
            Particle p = new Particle(
                impactPoint,
                new Vector2D(
                    3.0 * Math.cos(angle),
                    3.0 * Math.sin(angle)
                ),
                new Color(200, 150, 50),  // Orange/yellow
                500
            );
            world.addParticle(p);
        }
        
        // Screen shake
        world.shakeScreen(1.5, 200);
        
        isAlive = false;
    }
}
```

---

## 7. AUDIO FEEDBACK

### 7.1 Gun Fire Sounds

| Gun Type | Sound File | Pitch Variation | Volume |
|----------|-----------|-----------------|--------|
| Pistol A | `gunfire_pistol.wav` | ±5% | 0.8 |
| Pistol B | `gunfire_pistol_alt.wav` | ±5% | 0.8 |
| Compact | `gunfire_compact_rapid.wav` | ±3% | 0.7 |
| Rifle | `gunfire_rifle_heavy.wav` | ±3% | 1.0 |
| Sci-Fi | `gunfire_scifi_energy.wav` | ±10% | 0.9 |
| Special | `gunfire_special_explosive.wav` | None | 1.0 |

### 7.2 Impact Sounds

```java
class AudioFeedbackSystem {
    public static void playImpactSound(
        Projectile proj,
        Entity target,
        Point impactPoint) {
        
        String soundKey = null;
        float volume = 0.8f;
        float pitch = 1.0f + (float)(Math.random() - 0.5) * 0.1f;
        
        // Select sound by impact type
        if (target.isDome()) {
            soundKey = "impact_flesh_" + (1 + random.nextInt(3));
            volume = 0.9f;
        } else if (target instanceof Robot) {
            soundKey = "impact_metal_spark";
            volume = 1.0f;
            pitch = 1.2f;  // Higher pitched
        } else if (impactPoint.getType() == SurfaceType.STONE) {
            soundKey = "impact_stone_" + (1 + random.nextInt(2));
            volume = 0.7f;
        }
        
        if (soundKey != null) {
            audio.play(soundKey, impactPoint, volume, pitch);
        }
    }
}
```

---

## 8. PARTICLE SYSTEM

### 8.1 Particle Emitter

```java
class ParticleEmitter {
    private Point position;
    private Vector2D baseVelocity;
    private Vector2D velocityVariance;
    private Color[][] colors;  // Start color, end color
    private int particlesPerEmit;
    private long emissionDuration;
    
    public void emit() {
        for (int i = 0; i < particlesPerEmit; i++) {
            // Random velocity within variance
            double vx = baseVelocity.x + (Math.random() - 0.5) * velocityVariance.x;
            double vy = baseVelocity.y + (Math.random() - 0.5) * velocityVariance.y;
            
            // Random lifetime
            long lifetime = 200 + random.nextInt(300);  // 200-500ms
            
            // Select color based on index
            Color startColor = colors[0][i % colors[0].length];
            Color endColor = colors[1][i % colors[1].length];
            
            Particle p = new Particle(
                (Point)position.clone(),
                new Vector2D(vx, vy),
                startColor,
                endColor,
                lifetime
            );
            
            world.addParticle(p);
        }
    }
}

class Particle {
    private Point position;
    private Vector2D velocity;
    private Color startColor;
    private Color endColor;
    private long spawnTime;
    private long lifetime;
    private float size = 2.0f;
    private float gravity = 0.1f;  // Particles fall
    
    public void update(long currentTime) {
        long elapsed = currentTime - spawnTime;
        
        // Update position
        position.x += velocity.x;
        position.y += velocity.y;
        velocity.y += gravity;  // Fall
        
        // Fade out
        float progress = (float)elapsed / lifetime;
        if (progress > 1.0f) {
            markForRemoval();
        }
    }
    
    public void render(Graphics2D g, long currentTime) {
        long elapsed = currentTime - spawnTime;
        float progress = (float)elapsed / lifetime;
        
        // Interpolate color
        Color interpolated = interpolateColor(startColor, endColor, progress);
        g.setColor(interpolated);
        
        // Decrease size over time
        float currentSize = size * (1.0f - progress);
        g.fillOval(
            (int)(position.x - currentSize/2),
            (int)(position.y - currentSize/2),
            (int)currentSize,
            (int)currentSize
        );
    }
}
```

---

## 9. INTEGRATION CHECKLIST

- [ ] Implement straight trajectory physics
- [ ] Add arc trajectory with gravity
- [ ] Create homing/tracking algorithm
- [ ] Implement curved trajectory (wave effects)
- [ ] Build splat/impact particle systems
- [ ] Add explosion area damage
- [ ] Implement ricochet physics
- [ ] Create damage calculation engine
- [ ] Add knockback physics
- [ ] Build floating damage numbers
- [ ] Create enemy flinch animations
- [ ] Implement gun fire sounds (6 types)
- [ ] Add impact audio feedback
- [ ] Create particle emitters
- [ ] Render tracer effects dynamically
- [ ] Add screen shake for heavy impacts
- [ ] Test all gun×impact combinations
- [ ] Balance damage across difficulty levels

---

**Status**: Design Complete ✅  
**Ready for**: Physics Engine Implementation

