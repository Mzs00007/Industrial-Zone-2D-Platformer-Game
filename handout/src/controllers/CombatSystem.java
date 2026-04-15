package combat;
import game2D.*;

import animation.InputController;
import animation.InputController.PixelCopyHelper;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * UNIFIED COMBAT SYSTEM - Consolidated from 8 standalone combat-related classes
 * DESIGN: Provides complete combat API with weapons, damage, combat states
 * RASTER GRAPHICS ONLY - Uses PNG assets exclusively from Resources/industrial-zone/weapons/
 * @author 3359098
 */
public class CombatSystem extends InputController {
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // WEAPON - Represents a single weapon instance with properties and behavior
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    public static class Weapon {
        
        public enum WeaponType {
            PUNCH,          // Always available, melee
            PISTOL,         // Found weapon, ranged
            SHOTGUN,        // Found weapon, high damage, short range
            GRENADE,        // Found weapon, area damage
            LASER,          // Found weapon, piercing damage
            MINIGUN         // Boss loot, rapid fire
        }
        
        private final WeaponType type;
        private final String name;
        private final int baseDamage;
        private final float fireRate;
        private final int maxAmmo;
        private int currentAmmo;
        private final float range;
        private final int purchaseCost;
        private long lastFireTime = 0;
        
        public Weapon(WeaponType type, String name, int baseDamage, float fireRate, 
                      int maxAmmo, float range, int purchaseCost) {
            this.type = type;
            this.name = name;
            this.baseDamage = baseDamage;
            this.fireRate = fireRate;
            this.maxAmmo = maxAmmo;
            this.currentAmmo = maxAmmo;
            this.range = range;
            this.purchaseCost = purchaseCost;
        }
        
        public static Weapon createFromType(WeaponType type) {
            switch (type) {
                case PUNCH:
                    return new Weapon(WeaponType.PUNCH, "Punch", 5, 2.0f, -1, 32f, 0);
                case PISTOL:
                    return new Weapon(WeaponType.PISTOL, "Pistol", 15, 3.0f, 30, 400f, 100);
                case SHOTGUN:
                    return new Weapon(WeaponType.SHOTGUN, "Shotgun", 40, 0.8f, 20, 150f, 250);
                case GRENADE:
                    return new Weapon(WeaponType.GRENADE, "Grenade", 60, 0.3f, 10, 300f, 300);
                case LASER:
                    return new Weapon(WeaponType.LASER, "Laser", 25, 4.0f, -1, 600f, 400);
                case MINIGUN:
                    return new Weapon(WeaponType.MINIGUN, "Minigun", 8, 12.0f, 200, 350f, 0);
                default:
                    return new Weapon(WeaponType.PUNCH, "Punch", 5, 2.0f, -1, 32f, 0);
            }
        }
        
        public boolean canFire() {
            if (currentAmmo == 0) {
                return false;
            }
            
            long timeSinceLastFire = System.currentTimeMillis() - lastFireTime;
            long fireDelay = (long) (1000.0f / fireRate);
            
            return timeSinceLastFire >= fireDelay;
        }
        
        public void fire() {
            if (canFire()) {
                if (currentAmmo > 0) {
                    currentAmmo--;
                }
                lastFireTime = System.currentTimeMillis();
            }
        }
        
        public void addAmmo(int amount) {
            if (maxAmmo > 0) {
                currentAmmo = Math.min(currentAmmo + amount, maxAmmo);
            }
        }
        
        public void reload() {
            if (maxAmmo > 0) {
                currentAmmo = maxAmmo;
            }
        }
        
        public int calculateDamage(float distanceToTarget) {
            if (distanceToTarget > range) {
                return 0;
            }
            
            float damageMultiplier = 1.0f - (distanceToTarget / (range * 2.0f));
            damageMultiplier = Math.max(0.5f, damageMultiplier);
            
            return (int) (baseDamage * damageMultiplier);
        }
        
        // Getters
        public WeaponType getType() { return type; }
        public String getName() { return name; }
        public int getBaseDamage() { return baseDamage; }
        public float getFireRate() { return fireRate; }
        public int getCurrentAmmo() { return currentAmmo; }
        public int getMaxAmmo() { return maxAmmo; }
        public boolean hasInfiniteAmmo() { return maxAmmo == -1; }
        public float getRange() { return range; }
        public int getPurchaseCost() { return purchaseCost; }
        
        public String getAmmoDisplay() {
            if (hasInfiniteAmmo()) {
                return "âˆž";
            }
            return currentAmmo + "/" + maxAmmo;
        }
        
        @Override
        public String toString() {
            String ammo = hasInfiniteAmmo() ? "âˆž" : currentAmmo + "/" + maxAmmo;
            return String.format("Weapon{name=%s, dmg=%d, fireRate=%.1f/s, ammo=%s, range=%.0f px}", 
                name, baseDamage, fireRate, ammo, range);
        }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // WEAPON MANAGER - Manages weapons, inventory, and pickups
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    public static class WeaponManager {
        
        public static class WeaponPickup {
            public float x;
            public float y;
            public Weapon weapon;
            public boolean collected = false;
            
            public WeaponPickup(float x, float y, Weapon weapon) {
                this.x = x;
                this.y = y;
                this.weapon = weapon;
            }
            
            public boolean isNearPlayer(float playerX, float playerY) {
                float dx = playerX - x;
                float dy = playerY - y;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);
                return distance <= 50f;
            }
            
            @Override
            public String toString() {
                return String.format("WeaponPickup{%s @ (%.0f, %.0f)}", weapon.getName(), x, y);
            }
        }
        
        private java.util.List<Weapon> playerInventory = new ArrayList<>();
        private Weapon currentWeapon;
        private java.util.List<WeaponPickup> levelPickups = new ArrayList<>();
        private int currentWeaponIndex = 0;
        
        public WeaponManager() {
            Weapon punch = Weapon.createFromType(Weapon.WeaponType.PUNCH);
            playerInventory.add(punch);
            currentWeapon = punch;
        }
        
        public void addWeaponPickup(float x, float y, Weapon.WeaponType weaponType) {
            Weapon weapon = Weapon.createFromType(weaponType);
            WeaponPickup pickup = new WeaponPickup(x, y, weapon);
            levelPickups.add(pickup);
            System.out.println("[WeaponManager] Added pickup: " + pickup);
        }
        
        public void updatePickups(float playerX, float playerY) {
            for (WeaponPickup pickup : levelPickups) {
                if (!pickup.collected && pickup.isNearPlayer(playerX, playerY)) {
                    collectWeapon(pickup.weapon);
                    pickup.collected = true;
                    System.out.println("[WeaponManager] Picked up: " + pickup.weapon.getName());
                }
            }
        }
        
        public void collectWeapon(Weapon weapon) {
            for (Weapon w : playerInventory) {
                if (w.getType() == weapon.getType()) {
                    if (weapon.getMaxAmmo() > 0) {
                        w.addAmmo(weapon.getMaxAmmo() / 2);
                    }
                    return;
                }
            }
            
            playerInventory.add(weapon);
            System.out.println("[WeaponManager] New weapon added to inventory: " + weapon.getName());
        }
        
        public void switchToNextWeapon() {
            if (playerInventory.size() <= 1) return;
            
            currentWeaponIndex = (currentWeaponIndex + 1) % playerInventory.size();
            currentWeapon = playerInventory.get(currentWeaponIndex);
            System.out.println("[WeaponManager] Switched to: " + currentWeapon.getName());
        }
        
        public void switchToPreviousWeapon() {
            if (playerInventory.size() <= 1) return;
            
            currentWeaponIndex = (currentWeaponIndex - 1 + playerInventory.size()) % playerInventory.size();
            currentWeapon = playerInventory.get(currentWeaponIndex);
            System.out.println("[WeaponManager] Switched to: " + currentWeapon.getName());
        }
        
        public void switchToWeapon(Weapon.WeaponType type) {
            for (int i = 0; i < playerInventory.size(); i++) {
                if (playerInventory.get(i).getType() == type) {
                    currentWeaponIndex = i;
                    currentWeapon = playerInventory.get(i);
                    System.out.println("[WeaponManager] Switched to: " + currentWeapon.getName());
                    return;
                }
            }
            System.out.println("[WeaponManager] Weapon not in inventory: " + type);
        }
        
        public boolean fireCurrentWeapon() {
            if (currentWeapon != null && currentWeapon.canFire()) {
                currentWeapon.fire();
                return true;
            }
            return false;
        }
        
        public void reloadCurrentWeapon() {
            if (currentWeapon != null && currentWeapon.getMaxAmmo() > 0) {
                currentWeapon.reload();
                System.out.println("[WeaponManager] Reloaded: " + currentWeapon.getName());
            }
        }
        
        public void resetWeapons() {
            playerInventory.clear();
            Weapon punch = Weapon.createFromType(Weapon.WeaponType.PUNCH);
            playerInventory.add(punch);
            currentWeapon = punch;
            currentWeaponIndex = 0;
            System.out.println("[WeaponManager] Weapons reset to default");
        }
        
        public String getLoadoutSummary() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < playerInventory.size(); i++) {
                if (i == currentWeaponIndex) {
                    sb.append("*");
                }
                sb.append(playerInventory.get(i).getName());
                if (i < playerInventory.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
        
        // Getters
        public Weapon getCurrentWeapon() { return currentWeapon; }
        public java.util.List<Weapon> getInventory() { return new ArrayList<>(playerInventory); }
        public int getInventorySize() { return playerInventory.size(); }
        public Weapon getWeapon(int index) {
            if (index >= 0 && index < playerInventory.size()) {
                return playerInventory.get(index);
            }
            return currentWeapon;
        }
        public java.util.List<WeaponPickup> getAvailablePickups() {
            java.util.List<WeaponPickup> available = new ArrayList<>();
            for (WeaponPickup pickup : levelPickups) {
                if (!pickup.collected) {
                    available.add(pickup);
                }
            }
            return available;
        }
        public String getCurrentAmmoDisplay() {
            if (currentWeapon != null) {
                return currentWeapon.getAmmoDisplay();
            }
            return "0/0";
        }
        public int getDamageAtDistance(float distance) {
            if (currentWeapon != null) {
                return currentWeapon.calculateDamage(distance);
            }
            return 0;
        }
        
        @Override
        public String toString() {
            return String.format("WeaponManager{inventory=%d, current=%s, pickups=%d}", 
                playerInventory.size(), currentWeapon.getName(), levelPickups.size());
        }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // WEAPON CONFIGURATION - Weapon types, stats, and behavioral characteristics
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    public static class WeaponConfig {
        public enum WeaponType {
            PISTOL(10f, 10f, 300f, 2f, 1) {
                @Override
                public String getAssetPath() {
                    return "Resources/industrial-zone/weapons/1/2 Guns/";
                }
                
                @Override
                public String toString() {
                    return "Pistol";
                }
            },
            
            RIFLE(20f, 5f, 500f, 1f, 1) {
                @Override
                public String getAssetPath() {
                    return "Resources/industrial-zone/weapons/1/2 Guns/";
                }
                
                @Override
                public String toString() {
                    return "Rifle";
                }
            },
            
            SHOTGUN(40f, 1f, 80f, 15f, 8) {
                @Override
                public String getAssetPath() {
                    return "Resources/industrial-zone/weapons/1/2 Guns/";
                }
                
                @Override
                public String toString() {
                    return "Shotgun";
                }
            };
            
            private final float damage;
            private final float rateOfFire;
            private final float range;
            private final float spread;
            private final int pelletCount;
            
            WeaponType(float damage, float rateOfFire, float range, float spread, int pelletCount) {
                this.damage = damage;
                this.rateOfFire = rateOfFire;
                this.range = range;
                this.spread = spread;
                this.pelletCount = pelletCount;
            }
            
            public float getDamage() { return damage; }
            public float getRateOfFire() { return rateOfFire; }
            public float getRange() { return range; }
            public float getSpread() { return spread; }
            public int getPelletCount() { return pelletCount; }
            public long getFireCooldown() { return (long) (1000f / rateOfFire); }
            
            public float getProjectileSpeed() {
                switch (this) {
                    case PISTOL: return 400f;
                    case RIFLE: return 600f;
                    case SHOTGUN: return 300f;
                    default: return 400f;
                }
            }
            
            public abstract String getAssetPath();
        }
        
        public static WeaponType getWeapon(String name) {
            try {
                return WeaponType.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.err.println("âš  Unknown weapon: " + name + ", defaulting to PISTOL");
                return WeaponType.PISTOL;
            }
        }
        
        public static float degreesToRadians(float degrees) {
            return degrees * (float) Math.PI / 180f;
        }
        
        public static float applySpread(float baseDirection, float spreadDegrees) {
            float spreadRad = degreesToRadians(spreadDegrees);
            float randomVariance = (float) (Math.random() * spreadRad * 2 - spreadRad);
            return baseDirection + randomVariance;
        }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // PROJECTILE - Representation of a fired bullet/projectile
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    public static class Projectile {
        
        private float x, y;
        private float vx, vy;
        private float damage;
        private WeaponConfig.WeaponType sourceWeapon;
        private long creationTime;
        private float startX, startY;
        
        private static final float GRAVITY = 300f;
        
        public Projectile(float startX, float startY, float directionX, float directionY, WeaponConfig.WeaponType weapon) {
            this.x = startX;
            this.y = startY;
            this.startX = startX;
            this.startY = startY;
            this.sourceWeapon = weapon;
            this.damage = weapon.getDamage();
            this.creationTime = System.currentTimeMillis();
            
            float speed = weapon.getProjectileSpeed();
            float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);
            if (length > 0) {
                directionX /= length;
                directionY /= length;
            }
            
            this.vx = directionX * speed;
            this.vy = directionY * speed;
        }
        
        public void update(long deltaTimeMs) {
            float deltaSeconds = deltaTimeMs / 1000f;
            
            x += vx * deltaSeconds;
            y += vy * deltaSeconds;
            
            vy += GRAVITY * deltaSeconds;
        }
        
        public boolean isExpired() {
            float travelDistance = (float) Math.sqrt(
                (x - startX) * (x - startX) + 
                (y - startY) * (y - startY)
            );
            
            if (travelDistance > sourceWeapon.getRange()) {
                return true;
            }
            
            if (System.currentTimeMillis() - creationTime > 10000) {
                return true;
            }
            
            return false;
        }
        
        public float getX() { return x; }
        public float getY() { return y; }
        public float getDamage() { return damage; }
        public WeaponConfig.WeaponType getSourceWeapon() { return sourceWeapon; }
        
        public void setPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // PROJECTILE MANAGER - Manages list of active projectiles
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    public static class ProjectileManager {
        
        public static class ActiveProjectile {
            public float x, y, velocityX, velocityY, lifetime = 5000;
            public long createdAt;
            public String type;
            public float damage = 10;
            public int radius = 4;
            
            public ActiveProjectile(float x, float y, float vx, float vy, String t) {
                this.x = x; this.y = y;
                velocityX = vx; velocityY = vy;
                type = t;
                createdAt = System.currentTimeMillis();
            }
            
            public boolean isAlive(long now) {
                return (now - createdAt) < lifetime;
            }
            
            public void update(float dt) {
                x += velocityX * dt;
                y += velocityY * dt;
            }
        }
        
        private java.util.List<ActiveProjectile> projectiles = new ArrayList<>();
        
        public void fireProjectile(float sx, float sy, float dx, float dy, float speed, String type) {
            float len = (float)Math.sqrt(dx*dx + dy*dy);
            if (len > 0) { dx /= len; dy /= len; } else { dx = 1; }
            
            ActiveProjectile p = new ActiveProjectile(sx, sy, dx * speed, dy * speed, type);
            
            switch (type.toLowerCase()) {
                case "bullet":  p.damage = 10; p.radius = 3; p.lifetime = 3000; break;
                case "pellet":  p.damage = 5;  p.radius = 2; p.lifetime = 2500; break;
                case "laser":   p.damage = 20; p.radius = 2; p.lifetime = 2000; break;
                case "grenade": p.damage = 50; p.radius = 8; p.lifetime = 4000; p.velocityY += 200; break;
            }
            
            projectiles.add(p);
        }
        
        public void update(float dt) {
            long now = System.currentTimeMillis();
            for (ActiveProjectile p : projectiles) p.update(dt);
            projectiles.removeIf(p -> !p.isAlive(now));
        }
        
        public java.util.List<ActiveProjectile> getActiveProjectiles() { 
            return new ArrayList<>(projectiles); 
        }
        
        public int getProjectileCount() { return projectiles.size(); }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // PLAYER COMBAT - Player-specific attack and weapon mechanics
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    public static class PlayerCombat { //instead use the file : C:\Users\ZAID SIDDIQUI\OneDrive - University of Stirling\stir uni\SEMESTERS\sem6 2026\CSCU9N6\N6AssignmentCode\handout\src\animation\AnimationAndSpriteLoader.java to get more info on this system
        
        private WeaponConfig.WeaponType currentWeapon = WeaponConfig.WeaponType.PISTOL;
        private long lastFireTime = 0;
        private int ammoCount = 999;
        private float aimDirectionX = 1f;
        private float aimDirectionY = 0f;
        
        public PlayerCombat(String placeholder) {
            System.out.println("[PlayerCombat] âœ“ Initialized with weapon: " + currentWeapon);
        }
        
        public void update(long deltaTimeMs) {
            // Fire rate cooldown is tracked by lastFireTime
        }
        
        public boolean fireWeapon(long currentTime) {
            long timeSinceLastFire = currentTime - lastFireTime;
            long fireRateCooldown = currentWeapon.getFireCooldown();
            
            if (timeSinceLastFire < fireRateCooldown) {
                return false;
            }
            
            if (ammoCount <= 0) {
                System.out.println("[PlayerCombat] NO AMMO!");
                return false;
            }
            
            ammoCount--;
            lastFireTime = currentTime;
            
            return true;
        }
        
        public void switchWeapon(WeaponConfig.WeaponType weapon) {
            if (weapon == currentWeapon) {
                return;
            }
            
            currentWeapon = weapon;
            System.out.println("[PlayerCombat] Switched to: " + weapon);
        }
        
        public void setAimDirection(float dirX, float dirY) {
            this.aimDirectionX = dirX;
            this.aimDirectionY = dirY;
        }
        
        public void refillAmmo() {
            ammoCount = 999;
            System.out.println("[PlayerCombat] Ammo refilled!");
        }
        
        // Getters
        public WeaponConfig.WeaponType getCurrentWeapon() { return currentWeapon; }
        public int getAmmoCount() { return ammoCount; }
        public float getAimDirectionX() { return aimDirectionX; }
        public float getAimDirectionY() { return aimDirectionY; }
        public long getLastFireTime() { return lastFireTime; }
        
        public boolean isReadyToFire(long currentTime) {
            long timeSinceLastFire = currentTime - lastFireTime;
            return timeSinceLastFire >= currentWeapon.getFireCooldown() && ammoCount > 0;
        }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // WEAPON RENDERER - Renders weapons with sprite graphics
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    public static class WeaponRenderer {
        
        private static final java.util.Map<String, BufferedImage> cache = new HashMap<>();
        
        public void renderWeapon(BufferedImage dest, float px, float py, String name, boolean flip) {
            if (dest == null) return;
            float hx = px + 28, hy = py + 16;
            if (flip) hx = px - 12;
            
            BufferedImage sprite = getWeaponSprite(name);
            if (sprite != null) {
                if (flip) drawFlipped(dest, sprite, (int)hx - sprite.getWidth(), (int)hy);
                else PixelCopyHelper.copyRaster(sprite, dest, (int)hx, (int)hy);
            }
        }
        
        public void renderFireAnimation(BufferedImage dest, float px, float py, String name, int frame, boolean flip) {
            if (dest == null) return;
            // Animation frame rendering placeholder
        }
        
        private BufferedImage getWeaponSprite(String name) {
            return cache.getOrDefault(name, null);
        }
        
        private void drawFlipped(BufferedImage dest, BufferedImage img, int x, int y) {
            for (int sy = 0; sy < img.getHeight(); sy++) {
                for (int sx = 0; sx < img.getWidth(); sx++) {
                    int pixel = img.getRGB(sx, sy);
                    int destX = x + (img.getWidth() - 1 - sx);
                    int destY = y + sy;
                    if (destX >= 0 && destX < dest.getWidth() && destY >= 0 && destY < dest.getHeight()) {
                        dest.setRGB(destX, destY, pixel);
                    }
                }
            }
        }
        
        public static void clearCache() { cache.clear(); }
    }
    
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    // WEAPON ASSET REGISTRY - Asset path mappings for all weapon assets
    // â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
    
    public static class WeaponAssetRegistry {
        
        private static final String BASE = "Resources/industrial-zone/weapons";
        
        public static class BikerWeaponStates {
            private static final java.util.TreeMap<String, String> assets = new TreeMap<>();
            static { 
                assets.put("IDLE_A", BASE + "/1/1 characters/1 Biker/Idle_A.png"); 
            }
            public static int getAssetCount() { return assets.size(); }
        }
        
        public static class GunSprites {
            private static final java.util.TreeMap<String, String> assets = new TreeMap<>();
            static { 
                assets.put("PISTOL_DARK", BASE + "/1/2 Guns/Pistol_Dark.png"); 
            }
            public static int getAssetCount() { return assets.size(); }
        }
        
        public static class HandGrips {
            private static final java.util.TreeMap<String, String> assets = new TreeMap<>();
            static { 
                assets.put("GRIP_H", BASE + "/1/3 Hands/Horizontal.png"); 
            }
            public static int getAssetCount() { return assets.size(); }
        }
        
        public static class ShootEffects {
            private static final java.util.TreeMap<String, String> assets = new TreeMap<>();
            static { 
                assets.put("TRACER_A", BASE + "/1/4 Shoot_effects/Tracer_A.png"); 
            }
            public static int getAssetCount() { return assets.size(); }
        }
        
        public static class Bullets {
            private static final java.util.TreeMap<String, String> assets = new TreeMap<>();
            static { 
                assets.put("BULLET_A", BASE + "/1/5 Bullets/TypeA.png"); 
            }
            public static int getAssetCount() { return assets.size(); }
        }
    }
}

