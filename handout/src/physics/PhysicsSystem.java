package physics;

/**
 * PhysicsSystem - UNIFIED PHYSICS API - Complete Game Physics System
 * All Physics Classes Consolidated Here Only - Zero Duplication via Inheritance
 *
 * ARCHITECTURE: PhysicsSystem provides all physics-related functionality
 * including physics bodies, collision detection and resolution.
 *
 * NESTED STATIC CLASSES:
 *   [1] PhysicsUnitSystem - Core physics engine (Vector2D, PhysicsBody)
 *   [2] CollisionDetector - Circle-based collision detection
 *   [3] CollisionResolver - Impulse-based collision response
 *   [4] GravitySystem - World gravity and force management
 *   [5] ProjectilePhysics - Projectile trajectory calculations
 *
 * QUICK API USAGE:
 *   PhysicsSystem.PhysicsUnitSystem.PhysicsBody body = 
 *       new PhysicsSystem.PhysicsUnitSystem.PhysicsBody(x, y, mass, radius);
 *   body.applyForce(forceX, forceY);
 *   body.update(deltaTime);
 * @author 3359098
 */
public class PhysicsSystem {

    /**
     * Constructor: Initialize PhysicsSystem
     * Provides core physics calculations
     */
    public PhysicsSystem() {
        super();  // Initialize AnimationAndSpriteLoader → GameCore
    }

    // ==================== PHYSICS UNIT SYSTEM ====================
    // (Extracted from AnimationAndSpriteLoader, moved here for consolidation)
    // All physics calculations in SI units (meters, seconds, kg)
    
    public static class PhysicsUnitSystem {
        
        // ==================== WORLD SCALE & CONVERSION ====================
        public static final float PIXELS_PER_METER = 32.0f;
        public static final float METERS_PER_PIXEL = 1.0f / PIXELS_PER_METER;
        public static final float TILE_SIZE_METERS = 1.0f;      // 1 tile = 1 meter
        public static final float TILE_SIZE_PIXELS = 32.0f;     // 1 tile = 32 pixels
        
        // ==================== PHYSICS CONSTANTS ====================
        public static final float GRAVITY = -9.81f;            // m/s² - Negative for Y-up
        public static final float TIME_STEP = 1.0f / 60.0f;    // 0.01666...s for stable 60fps
        
        // ==================== DAMPING COEFFICIENTS ====================
        public static final float LINEAR_DAMPING = 0.85f;      // Ground friction
        public static final float ANGULAR_DAMPING = 0.1f;      // Rotational damping
        public static final float AIR_DAMPING = 0.15f;         // Air resistance
        
        // ==================== 2D VECTOR CLASS ====================
        public static class Vector2D {
            public float x, y;
            
            public Vector2D() { this.x = 0; this.y = 0; }
            public Vector2D(float x, float y) { this.x = x; this.y = y; }
            public Vector2D(Vector2D v) { this.x = v.x; this.y = v.y; }
            
            // Vector operations
            public void add(Vector2D v) { this.x += v.x; this.y += v.y; }
            public void add(float x, float y) { this.x += x; this.y += y; }
            public void subtract(Vector2D v) { this.x -= v.x; this.y -= v.y; }
            public void multiply(float scalar) { this.x *= scalar; this.y *= scalar; }
            public float dot(Vector2D v) { return this.x * v.x + this.y * v.y; }
            public float magnitude() { return (float)Math.sqrt(x*x + y*y); }
            public float magnitudeSquared() { return x*x + y*y; }
            public void normalize() {
                float mag = magnitude();
                if (mag > 0) { this.x /= mag; this.y /= mag; }
            }
            
            // Unit conversions
            public Vector2D toPixels() { return new Vector2D(x * PIXELS_PER_METER, y * PIXELS_PER_METER); }
            public Vector2D toMeters() { return new Vector2D(x * METERS_PER_PIXEL, y * METERS_PER_PIXEL); }
            
            @Override
            public String toString() { return String.format("[%.2f, %.2f]", x, y); }
        }
        
        // ==================== PHYSICS BODY CLASS ====================
        public static class PhysicsBody {
            public Vector2D position;      // meters
            public Vector2D velocity;      // m/s
            public Vector2D acceleration;  // m/s²
            public Vector2D forces;        // newtons
            
            public float mass;             // kg
            public float radius;           // meters
            public boolean isGrounded;
            public boolean isAffectedByGravity;
            
            // Constructor
            public PhysicsBody(float x, float y, float mass, float radius) {
                this.position = new Vector2D(x, y);
                this.velocity = new Vector2D();
                this.acceleration = new Vector2D();
                this.forces = new Vector2D();
                this.mass = mass > 0 ? mass : 1.0f;
                this.radius = radius;
                this.isGrounded = false;
                this.isAffectedByGravity = true;
            }
            
            // Force application: F = m·a
            public void applyForce(float fx, float fy) {
                forces.x += fx;
                forces.y += fy;
            }
            
            public void applyForce(Vector2D force) {
                forces.add(force);
            }
            
            // Clear forces
            public void clearForces() { forces.x = 0; forces.y = 0; }
            
            // Apply velocity cap
            public void setMaxVelocity(float max) {
                float mag = velocity.magnitude();
                if (mag > max) {
                    velocity.multiply(max / mag);
                }
            }
            
            // Apply time-independent friction
            public void applyDamping(float damping, float deltaTime) {
                float dampingFactor = 1.0f - (damping * deltaTime);
                dampingFactor = Math.max(0, dampingFactor);
                velocity.multiply(dampingFactor);
            }
            
            // Physics update: Euler integration
            public void update(float deltaTime) {
                if (deltaTime <= 0) return;
                
                // Apply gravity
                if (isAffectedByGravity) {
                    forces.y += mass * GRAVITY;
                }
                
                // Acceleration = F / m
                acceleration.x = forces.x / mass;
                acceleration.y = forces.y / mass;
                
                // Velocity += Acceleration * dt
                velocity.x += acceleration.x * deltaTime;
                velocity.y += acceleration.y * deltaTime;
                
                // Position += Velocity * dt
                position.x += velocity.x * deltaTime;
                position.y += velocity.y * deltaTime;
                
                // Clear forces for next frame
                clearForces();
            }
        }
    }
    
    // ==================== COLLISION DETECTOR ====================
    public static class CollisionDetector {
        
        /**
         * Check if two physics bodies collide
         */
        public static boolean checkCollision(PhysicsUnitSystem.PhysicsBody body1, 
                                            PhysicsUnitSystem.PhysicsBody body2) {
            float dx = body2.position.x - body1.position.x;
            float dy = body2.position.y - body1.position.y;
            float distSq = dx*dx + dy*dy;
            float minDist = body1.radius + body2.radius;
            return distSq < minDist * minDist;
        }
        
        /**
         * Get collision contact point
         */
        public static PhysicsUnitSystem.Vector2D getContactPoint(PhysicsUnitSystem.PhysicsBody body1, 
                                                                  PhysicsUnitSystem.PhysicsBody body2) {
            float dx = body2.position.x - body1.position.x;
            float dy = body2.position.y - body1.position.y;
            float dist = (float)Math.sqrt(dx*dx + dy*dy);
            if (dist == 0) dist = 1; // Prevent division by zero
            
            // Contact point at surface of body1
            float contactX = body1.position.x + (dx / dist) * body1.radius;
            float contactY = body1.position.y + (dy / dist) * body1.radius;
            return new PhysicsUnitSystem.Vector2D(contactX, contactY);
        }
    }
    
    // ==================== COLLISION RESOLVER ====================
    public static class CollisionResolver {
        
        /**
         * Resolve collision between two bodies with impulse-based resolution
         */
        public static void resolveCollision(PhysicsUnitSystem.PhysicsBody body1, 
                                           PhysicsUnitSystem.PhysicsBody body2) {
            if (body1 == null || body2 == null) return;
            
            // Relative velocity
            float relVelX = body2.velocity.x - body1.velocity.x;
            float relVelY = body2.velocity.y - body1.velocity.y;
            
            // If bodies are separating, exit
            float dx = body2.position.x - body1.position.x;
            float dy = body2.position.y - body1.position.y;
            float relDotNormal = relVelX * dx + relVelY * dy;
            if (relDotNormal >= 0) return;
            
            // Simple impulse resolution with 0.8 restitution
            float restitution = 0.8f;
            float totalMass = body1.mass + body2.mass;
            float impulseMag = -(1.0f + restitution) * relDotNormal / totalMass;
            
            // Normalize direction
            float dist = (float)Math.sqrt(dx*dx + dy*dy);
            if (dist == 0) dist = 1;
            float normX = dx / dist;
            float normY = dy / dist;
            
            // Apply impulse
            float impulseX = normX * impulseMag;
            float impulseY = normY * impulseMag;
            
            body1.velocity.x -= (impulseX / body1.mass);
            body1.velocity.y -= (impulseY / body1.mass);
            body2.velocity.x += (impulseX / body2.mass);
            body2.velocity.y += (impulseY / body2.mass);
        }
    }
    
    // ==================== GRAVITY SYSTEM ====================
    public static class GravitySystem {
        
        /**
         * Apply gravity to a physics body
         */
        public static void applyGravity(PhysicsUnitSystem.PhysicsBody body, float deltaTime) {
            if (body == null || !body.isAffectedByGravity || deltaTime <= 0) return;
            
            // Apply gravitational acceleration using physics constants
            // Gravity is typically around 9.81 m/s², scaled to game units
            final float GRAVITY_SCALAR = 1.0f;  // Adjust for game balance
            float gravityAccel = PhysicsUnitSystem.GRAVITY * GRAVITY_SCALAR;
            
            // Calculate gravity force: F = m * a, applied as velocity change
            float gravityForce = gravityAccel * deltaTime;
            body.velocity.y += gravityForce;  // Apply gravity to vertical velocity
            
            // Cap maximum fall velocity for realistic terminal velocity
            float maxFallVelocity = PhysicsUnitSystem.PIXELS_PER_METER * 25f;  // ~25 m/s max
            if (body.velocity.y > maxFallVelocity) {
                body.velocity.y = maxFallVelocity;
            }
        }
        
        /**
         * Check if body is within world bounds
         */
        public static boolean isOutOfBounds(PhysicsUnitSystem.PhysicsBody body, 
                                           float worldWidth, float worldHeight) {
            return body.position.x < 0 || body.position.x > worldWidth ||
                   body.position.y < 0 || body.position.y > worldHeight;
        }
    }

    // ==================== PROJECTILE PHYSICS ====================
    public static class ProjectilePhysics {
        
        /**
         * Simple projectile trajectory calculation
         */
        public static float getTrajectoryY(float startX, float startY, float velocityX, 
                                          float velocityY, float currentX, float deltaTime) {
            float t = (currentX - startX) / velocityX;
            if (velocityX == 0) t = deltaTime;
            return startY + velocityY * t + 0.5f * PhysicsUnitSystem.GRAVITY * t * t;
        }
    }

    // ==================== PHYSICS CONSTANTS ====================
    public static class PhysicsConstants {
        public static final float GRAVITY = -800f;
        public static final float MAX_FALL_SPEED = 500f;
        public static final float MAX_RISE_SPEED = 500f;
        public static final float JUMP_VELOCITY = 392f;
        public static final float TARGET_JUMP_HEIGHT = 96f;
        public static final float JUMP_HOLD_BOOST = 50f;
        public static final float JUMP_HOLD_TIME = 0.15f;
        public static final float COYOTE_TIME = 0.1f;
        public static final float JUMP_BUFFER_TIME = 0.05f;
        public static final float WALK_SPEED = 150f;
        public static final float RUN_SPEED = 250f;
        public static final float SPRINT_SPEED = 350f;
        public static final float ACCELERATION = 800f;
        public static final float TIME_TO_MAX_SPEED = 0.3125f;
        public static final float FRICTION = 0.85f;
        public static final float AIR_DRAG = 0.98f;
        public static final float ICE_FRICTION = 0.95f;
        public static final float GROUND_FRICTION = 2.0f;
        public static final float STICKY_FRICTION = 4.0f;
        public static final float AIR_CONTROL = 0.5f;
        public static final float AIR_ACCELERATION = 400f;
        public static final float WALL_SLIDE_SPEED = 50f;
        public static final float WALL_JUMP_SPEED = 250f;
        public static final float DASH_SPEED = 600f;
        public static final float DASH_DURATION = 0.2f;
        public static final float DASH_COOLDOWN = 0.3f;
        public static final float PLAYER_WIDTH = 32f;
        public static final float PLAYER_HEIGHT = 48f;
        public static final float PLAYER_MASS = 75f;
        public static final float GROUND_CHECK_DISTANCE = 4f;
        public static final float WALL_CHECK_DISTANCE = 6f;
        public static final float HAZARD_DAMAGE_INTERVAL = 0.2f;
        public static final float SPIKE_KNOCKBACK = 500f;
        public static final float ENEMY_KNOCKBACK = 300f;
        public static final float PROJECTILE_KNOCKBACK = 200f;
        public static final float DRONE_SPEED = 100f;
        public static final float DRONE_ACCELERATION = 400f;
        public static final float FAST_ENEMY_SPEED = 200f;
        public static final float FAST_ENEMY_ACCELERATION = 600f;
        public static final float HEAVY_ENEMY_SPEED = 120f;
        public static final float HEAVY_ENEMY_ACCELERATION = 300f;
        public static final float HEAVY_ENEMY_MASS = 500f;
        public static final float ENEMY_FRICTION = 0.90f;
        public static final float PROJECTILE_SPEED = 600f;
        public static final float PROJECTILE_MAX_RANGE = 1200f;
        public static final float PROJECTILE_TTL = 10f;
        public static final float PROJECTILE_WIDTH = 8f;
        public static final float PROJECTILE_HEIGHT = 4f;
        public static final float PROJECTILE_GRAVITY = 300f;
        public static final float PROJECTILE_BASE_DAMAGE = 10f;
        public static final float PROJECTILE_VELOCITY_SCALING = 0.01f;
        public static final float LEVEL1_GRAVITY = 1.0f;
        public static final float LEVEL2_GRAVITY = 1.1f;
        public static final float LEVEL1_FRICTION = 0.8f;
        public static final float LEVEL2_FRICTION = 0.85f;
        public static final int TARGET_FPS = 60;
        public static final float DELTA_TIME = 1f / TARGET_FPS;
        public static final long FRAME_TIME_MS = 16;
        public static final boolean DEBUG_PHYSICS = false;
        public static final boolean DEBUG_COLLISIONS = false;
        public static final boolean DEBUG_VELOCITY = false;
        public static final boolean VERBOSE_STARTUP = true;
        
        public static void printPhysicsAnalysis() {
            System.out.println("\n[PHYSICS CONSTANTS ANALYSIS]");
            System.out.println("Gravity: " + GRAVITY + " px/s²");
            System.out.println("Jump velocity: " + JUMP_VELOCITY + " px/s");
            System.out.println("Target jump height: " + TARGET_JUMP_HEIGHT + " px");
        }
        
        public static float calculateJumpHeight(float initialVelocity) {
            return (initialVelocity * initialVelocity) / (2 * GRAVITY);
        }
        
        public static float calculateRequiredVelocity(float targetHeight) {
            return (float) Math.sqrt(2 * GRAVITY * targetHeight);
        }
        
        public static float calculateTimeToHeight(float targetHeight) {
            float velocity = calculateRequiredVelocity(targetHeight);
            return velocity / GRAVITY;
        }
    }

    // ==================== PHYSICS BODY CLASS (Standalone version for compatibility) ====================
    public static class PhysicsBody {
        public float x;
        public float y;
        public float width;
        public float height;
        public float velocityX = 0;
        public float velocityY = 0;
        public float maxVelocityX = 8f;
        public float moveAcceleration = 0.5f;
        public boolean isOnGround = false;
        public float mass = 1f;
        
        public PhysicsBody(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        
        public float getCenterX() { return x + width / 2; }
        public float getCenterY() { return y + height / 2; }
        public float getLeft() { return x; }
        public float getRight() { return x + width; }
        public float getTop() { return y; }
        public float getBottom() { return y + height; }
        
        public boolean contains(float px, float py) {
            return px >= x && px <= x + width && py >= y && py <= y + height;
        }
        
        public boolean intersects(PhysicsBody other) {
            return !(x + width < other.x || x > other.x + other.width ||
                     y + height < other.y || y > other.y + other.height);
        }
        
        public void resetVelocity() {
            velocityX = 0;
            velocityY =0;
        }
        
        public void applyImpulse(float impulseX, float impulseY) {
            velocityX += impulseX / mass;
            velocityY += impulseY / mass;
        }
        
        @Override
        public String toString() {
            return String.format("PhysicsBody(x=%.1f, y=%.1f, vx=%.2f, vy=%.2f, ground=%b)",
                    x, y, velocityX, velocityY, isOnGround);
        }
    }

    // ==================== BOUNDING BOX & COLLISION RESULT ====================
    public static class BoundingBox {
        public float x, y;
        public float width, height;
        
        public BoundingBox(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        
        public float getLeft() { return x; }
        public float getRight() { return x + width; }
        public float getTop() { return y; }
        public float getBottom() { return y + height; }
        public float getCenterX() { return x + width / 2; }
        public float getCenterY() { return y + height / 2; }
        
        public void move(float dx, float dy) {
            x += dx;
            y += dy;
        }
        
        public void setSize(float width, float height) {
            this.width = width;
            this.height = height;
        }
        
        public void setPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class CollisionResult {
        public boolean colliding = false;
        public String side;
        public float penetrationX = 0;
        public float penetrationY = 0;
        public float overlapX = 0;
        public float overlapY = 0;
    }

    // ==================== BULLET PHYSICS SYSTEM ====================
    public static class BulletPhysicsSystem {
        public static final int DEFAULT_BULLET_SIZE = 8;
        public static final int MAX_BULLETS = 256;
        
        public static class Bullet {
            public float x, y;
            public float velocityX, velocityY;
            public int damageAmount;
            public float lifetime = 10f;
            public String type;
            public BulletDamageType damageType;
            public boolean active = true;
            
            public Bullet(float x, float y, float vx, float vy, int damage, String type) {
                this.x = x;
                this.y = y;
                this.velocityX = vx;
                this.velocityY = vy;
                this.damageAmount = damage;
                this.type = type;
                this.damageType = BulletDamageType.IMPACT;
            }
            
            public void update(float deltaTime) {
                x += velocityX * deltaTime;
                y += velocityY * deltaTime;
                lifetime -= deltaTime;
                if (lifetime <= 0) active = false;
            }
        }
        
        public enum BulletDamageType {
            IMPACT, EXPLOSION, PIERCING, BURN
        }
    }

    // ==================== COLLISION & INTERACTION SYSTEM ====================
    public static class CollisionAndInteractionSystem {
        public static class CollisionSystem {
            public void checkCollisions() {}
        }
        
        public static class InteractiveObject {
            public float x, y;
            public String type;
            public String state = "IDLE";
            public boolean isActive = true;
            public Runnable interactionCallback;
            
            public InteractiveObject(float x, float y, String type) {
                this.x = x;
                this.y = y;
                this.type = type;
            }
            
            public void setInteractionCallback(Runnable callback) {
                this.interactionCallback = callback;
            }
            
            public void setState(String state) {
                this.state = state;
            }
        }
        
        public static class WeaponObject {
            public float x, y;
            public String weaponType;
            public int damagePerShot;
            public float fireRate;
            public int ammoCapacity;
            public int ammoRemaining;
            public boolean isActive = true;
            
            public WeaponObject(float x, float y, String weaponType, int damagePerShot, float fireRate, int ammoCapacity) {
                this.x = x;
                this.y = y;
                this.weaponType = weaponType;
                this.damagePerShot = damagePerShot;
                this.fireRate = fireRate;
                this.ammoCapacity = ammoCapacity;
                this.ammoRemaining = ammoCapacity;
            }
        }
    }

    // ==================== CHARACTER PHYSICS PROFILE ====================
    public static class CharacterPhysicsProfile {
        public enum CharacterType {
            CYBORG, BIKER, PUNK
        }
        
        public CharacterType type;
        public float mass;
        public float acceleration;
        public float maxSpeed;
        public float jumpPower;
        public float frictionCoefficient;
        
        public static CharacterPhysicsProfile createProfile(CharacterType type) {
            CharacterPhysicsProfile profile = new CharacterPhysicsProfile();
            profile.type = type;
            
            switch(type) {
                case CYBORG:
                    profile.mass = 100f;
                    profile.acceleration = 200f;
                    profile.maxSpeed = 250f;
                    profile.jumpPower = 300f;
                    profile.frictionCoefficient = 0.85f;
                    break;
                case BIKER:
                    profile.mass = 120f;
                    profile.acceleration = 180f;
                    profile.maxSpeed = 200f;
                    profile.jumpPower = 280f;
                    profile.frictionCoefficient = 0.90f;
                    break;
                case PUNK:
                    profile.mass = 80f;
                    profile.acceleration = 250f;
                    profile.maxSpeed = 300f;
                    profile.jumpPower = 350f;
                    profile.frictionCoefficient = 0.75f;
                    break;
            }
            return profile;
        }
    }

    // ==================== CHARACTER FACTORY ====================
    public static class CharacterFactory {
        public static class CharacterInstance {
            public float x, y;
            public float vx, vy;  // velocity
            public float width, height;
            public int health, maxHealth;
            public int damage, armor, speed;
            public boolean isJumping, isFalling, isGrounded;
            public String characterType;
            public CharacterPhysicsProfile physicsProfile;
            
            public CharacterInstance(String type, float x, float y, float width, float height) {
                this.characterType = type;
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
                this.vx = 0;
                this.vy = 0;
                this.isJumping = false;
                this.isFalling = true;
                this.isGrounded = false;
                
                CharacterPhysicsProfile.CharacterType profType = CharacterPhysicsProfile.CharacterType.CYBORG;
                switch(type) {
                    case "CYBORG":
                        this.maxHealth = 100;
                        this.damage = 40;
                        this.armor = 30;
                        this.speed = 7;
                        profType = CharacterPhysicsProfile.CharacterType.CYBORG;
                        break;
                    case "PUNK":
                        this.maxHealth = 70;
                        this.damage = 60;
                        this.armor = 10;
                        this.speed = 9;
                        profType = CharacterPhysicsProfile.CharacterType.PUNK;
                        break;
                    case "BIKER":
                        this.maxHealth = 130;
                        this.damage = 50;
                        this.armor = 50;
                        this.speed = 5;
                        profType = CharacterPhysicsProfile.CharacterType.BIKER;
                        break;
                    default:
                        this.maxHealth = 100;
                        this.damage = 40;
                        this.armor = 20;
                        this.speed = 6;
                        profType = CharacterPhysicsProfile.CharacterType.CYBORG;
                }
                this.health = maxHealth;
                this.physicsProfile = CharacterPhysicsProfile.createProfile(profType);
            }
            
            public void takeDamage(int dmg) {
                this.health = Math.max(0, this.health - dmg);
            }
            
            public boolean isAlive() {
                return health > 0;
            }
            
            public float getX() { return x; }
            public float getY() { return y; }
            public float getVelocityX() { return vx; }
            public float getVelocityY() { return vy; }
            public float getWidth() { return width; }
            public float getHeight() { return height; }
            public int getHealth() { return health; }
            public int getMaxHealth() { return maxHealth; }
            public boolean isJumping() { return isJumping; }
            public boolean isFalling() { return isFalling; }
            public boolean isGrounded() { return isGrounded; }
            public CharacterPhysicsProfile getPhysicsProfile() { return physicsProfile; }
            
            public void setX(float x) { this.x = x; }
            public void setY(float y) { this.y = y; }
            public void setVelocityX(float vx) { this.vx = vx; }
            public void setVelocityY(float vy) { this.vy = vy; }
            public void setJumping(boolean j) { this.isJumping = j; }
            public void setFalling(boolean f) { this.isFalling = f; }
            public void setGrounded(boolean g) { this.isGrounded = g; }
        }
        
        public static CharacterInstance createCharacter(String type, float x, float y, float width, float height) {
            return new CharacterInstance(type, x, y, width, height);
        }
    }
}
