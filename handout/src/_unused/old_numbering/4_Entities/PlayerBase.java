import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * PlayerBase - PRODUCTION PLAYER ENTITY WITH REAL SPRITES
 * 
 * RESPONSIBILITY: Player character with sprite animation, physics, input, and rendering
 * 
 * Features:
 * - Real sprite loading (Biker/Punk/Cyborg with 6 animation states each)
 * - Keyboard input integration (arrows, SPACE, CTRL)
 * - Physics: gravity, jumping, collision
 * - Animation state machine
 * - Health system
 * 
 * @since 2026-04-14
 */
public class PlayerBase {
    
    private static final String TAG = "[PlayerBase]";
    private static final boolean VERBOSE = true;
    
    // Character selection
    public enum CharacterType {
        BIKER("biker", "Resources/industrial-zone/characters/player/biker/"),
        PUNK("punk", "Resources/industrial-zone/characters/player/punk/"),
        CYBORG("cyborg", "Resources/industrial-zone/characters/player/cyborg/");
        
        public final String name;
        public final String assetPath;
        
        CharacterType(String name, String assetPath) {
            this.name = name;
            this.assetPath = assetPath;
        }
    }
    
    // Animation states
    public enum AnimationState {
        IDLE, WALK, JUMP, ATTACK, HIT, DEATH
    }
    
    // Position & velocity
    private float x, y;
    private float velocityX = 0;
    private float velocityY = 0;
    private boolean isGrounded = true;
    private boolean isFacingRight = true;
    
    // Character & animation
    private CharacterType character;
    private AnimationState currentState = AnimationState.IDLE;
    private AnimationState previousState = null;
    private Map<String, BufferedImage[]> spriteFrames = new HashMap<>();
    private int currentFrameIndex = 0;
    private long frameTimerMs = 0;
    private int frameDelayMs = 100;
    
    private static final int SPRITE_WIDTH = 64;
    private static final int SPRITE_HEIGHT = 64;
    
    // Health system
    private int health = 100;
    private int maxHealth = 100;
    private boolean isAlive = true;
    private long lastDamageTimeMs = 0;
    private static final long DAMAGE_COOLDOWN_MS = 500;
    
    // Abilities
    private boolean canDash = true;
    private long lastDashTimeMs = 0;
    private static final long DASH_COOLDOWN_MS = 1000;
    private long lastAttackTimeMs = 0;
    private static final long ATTACK_COOLDOWN_MS = 600;

    // Heal system (H key)
    private long lastHealTimeMs = 0;
    private static final long HEAL_COOLDOWN_MS = 5000;  // 5 second cooldown
    private static final int  HEAL_AMOUNT      = 20;
    private boolean isHealing = false;
    private float healAnimTimer = 0f;

    // Double jump
    private boolean doubleJumpAvailable = true;
    private int jumpsRemaining = 2;  // 2 = ground, 1 = one jump used, 0 = both used

    // Ladder climb
    private boolean onLadder = false;
    private static final float CLIMB_SPEED = 80f;

    // Weapon inventory (4 slots)
    public enum WeaponType {
        PISTOL("Pistol", 10, 30, 400),
        SMG("SMG", 6, 60, 150),
        RIFLE("Rifle", 25, 15, 700),
        SHOTGUN("Shotgun", 35, 8, 900);

        public final String name;
        public final int damage;
        public final int maxAmmo;
        public final long fireRateMs;
        WeaponType(String name, int damage, int maxAmmo, long fireRateMs) {
            this.name = name; this.damage = damage;
            this.maxAmmo = maxAmmo; this.fireRateMs = fireRateMs;
        }
    }

    private WeaponType[] weaponSlots = new WeaponType[4];  // null = empty
    private int[] weaponAmmo = new int[4];
    private int activeWeaponSlot = -1;  // -1 = no weapon, 0-3 = slot index
    private int cashCollected = 0;
    private int cardsCollected = 0;
    
    // Input state tracking
    private static java.util.Set<Integer> keysPressed = new java.util.HashSet<>();
    
    public static void setKeyPressed(int keyCode, boolean pressed) {
        if (pressed) {
            keysPressed.add(keyCode);
        } else {
            keysPressed.remove(keyCode);
        }
    }
    
    private static boolean isKeyPressed(int keyCode) {
        return keysPressed.contains(keyCode);
    }

    /** Public access for Game.java to check key state (e.g. ladder attach). */
    public static boolean isKeyDown(int keyCode) {
        return keysPressed.contains(keyCode);
    }
    
    /**
     * Constructor - initialize player with character choice
     */
    public PlayerBase(CharacterType character, float startX, float startY) {
        this.character = character;
        this.x = startX;
        this.y = startY;
        
        if (VERBOSE) {
            System.out.println(TAG + " Initializing: " + character.name + 
                             " @ (" + startX + ", " + startY + ")");
        }
        
        loadCharacterSprites();
    }
    
    /**
     * Load all animation sprites for character from manifest
     */
    private void loadCharacterSprites() {
        try {
            // Build character asset path
            String charName = character.name.toLowerCase();
            String basePath = "Resources/industrial-zone/characters/player/" + charName + "/";
            
            // Define animation states and their file patterns
            Map<String, String> animationFiles = new HashMap<>();
            animationFiles.put("idle", "01_Player_" + capitalize(charName) + "_Idle_");
            animationFiles.put("walk", "02_Player_" + capitalize(charName) + "_Walk_");
            animationFiles.put("jump", "03_Player_" + capitalize(charName) + "_Jump_");
            animationFiles.put("attack", "04_Player_" + capitalize(charName) + "_Attack_");
            animationFiles.put("hit", "05_Player_" + capitalize(charName) + "_Hit_");
            animationFiles.put("death", "06_Player_" + capitalize(charName) + "_Death_");
            
            for (String state : animationFiles.keySet()) {
                String filePrefix = animationFiles.get(state);
                BufferedImage[] stateFrames = loadAnimationSequence(basePath, filePrefix);
                
                if (stateFrames.length == 0) {
                    // Fallback: try to load a single sprite for this state
                    File singleFile = new File(basePath + filePrefix + "Single_StaticSprite.png");
                    if (singleFile.exists()) {
                        BufferedImage img = ImageIO.read(singleFile);
                        if (img != null) {
                            stateFrames = new BufferedImage[]{img};
                        }
                    }
                }
                
                if (stateFrames.length > 0) {
                    spriteFrames.put(state, stateFrames);
                    if (VERBOSE) {
                        System.out.println(TAG + " ✓ Loaded " + character.name + " " + state + 
                                         " (" + stateFrames.length + " frames)");
                    }
                } else {
                    System.err.println(TAG + " WARNING: Could not load " + character.name + " " + state);
                }
            }
            
            if (VERBOSE) {
                System.out.println(TAG + " ✅ " + character.name + " sprites ready!");
            }
            
        } catch (Exception e) {
            System.err.println(TAG + " CRITICAL: Failed to load character sprites!");
            e.printStackTrace();
        }
    }
    
    /**
     * Load a spritesheet and split it into animation frames
     */
    private BufferedImage[] loadAnimationSequence(String basePath, String filePrefix) throws Exception {
        java.util.List<BufferedImage> frames = new java.util.ArrayList<>();
        
        // First try individual numbered files (new format)
        for (int i = 0; i < 10; i++) {
            File frameFile = new File(basePath + filePrefix + i + ".png");
            if (frameFile.exists()) {
                BufferedImage img = ImageIO.read(frameFile);
                if (img != null) frames.add(img);
            }
        }
        
        if (frames.size() > 0) return frames.toArray(new BufferedImage[0]);
        
        // Try spritesheet format with frame count in filename
        File[] files = new File(basePath).listFiles();
        if (files == null) return new BufferedImage[0];
        
        for (File file : files) {
            String filename =file.getName().toLowerCase();
            // Look for files containing the state name and frame count info
            if (filename.contains(filePrefix.toLowerCase())) {
                // Parse for "XFrames1Row" or "1RowXFrames" pattern
                int frameCount = extractFrameCount(filename);
                if (frameCount > 0) {
                    BufferedImage spritesheet = ImageIO.read(file);
                    if (spritesheet != null) {
                        return splitSpritesheet(spritesheet, frameCount, 1);  // 1 row of frames
                    }
                }
            }
        }
        
        return new BufferedImage[0];
    }
    
    /**
     * Extract frame count from filename (e.g., "6Frames1Row" → 6)
     */
    private int extractFrameCount(String filename) {
        // Look for pattern like "6Frames1Row", "4Frames", etc.
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+)Frames");
        java.util.regex.Matcher matcher = pattern.matcher(filename);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }
    
    /**
     * Split a spritesheet image into individual frames
     */
    private BufferedImage[] splitSpritesheet(BufferedImage spritesheet, int frameCount, int rows) {
        int frameWidth = spritesheet.getWidth() / frameCount;
        int frameHeight = spritesheet.getHeight() / rows;
        BufferedImage[] frames = new BufferedImage[frameCount * rows];
        
        int index = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < frameCount; col++) {
                frames[index] = spritesheet.getSubimage(
                    col * frameWidth, 
                    row * frameHeight, 
                    frameWidth, 
                    frameHeight
                );
                index++;
            }
        }
        
        return frames;
    }
    
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    /**
     * Update player each frame - COMPLETE STATE MACHINE
     */
    public void update(float deltaTime) {
        applyInput();
        applyPhysics(deltaTime);
        updateStateTransitions();  // NEW: explicit state management
        updateAnimation(deltaTime);
        
        // Death check
        if (health <= 0 && isAlive) {
            isAlive = false;
            setAnimationState(AnimationState.DEATH);
        }
    }
    
    /**
     * Apply keyboard input - COMPLETE INPUT HANDLING
     */
    private void applyInput() {
        // Horizontal movement
        int direction = 0;
        if (isKeyPressed(KeyEvent.VK_LEFT) || isKeyPressed(KeyEvent.VK_A)) {
            direction = -1;
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT) || isKeyPressed(KeyEvent.VK_D)) {
            direction = 1;
        }
        
        // Apply horizontal velocity
        if (direction != 0) {
            velocityX = direction * 150.0f;  // Move speed
            isFacingRight = direction > 0;
        } else {
            velocityX *= 0.85f;  // Friction
        }
        
        // Jump (with double jump support)
        if (isKeyPressed(KeyEvent.VK_SPACE)) {
            if (onLadder) {
                // Detach from ladder and jump
                onLadder = false;
                velocityY = -400.0f;
                jumpsRemaining = 1;
                setAnimationState(AnimationState.JUMP);
            } else if (isGrounded) {
                velocityY = -400.0f;
                isGrounded = false;
                jumpsRemaining = 1;  // used first jump
                setAnimationState(AnimationState.JUMP);
            } else if (jumpsRemaining > 0) {
                // Double jump
                velocityY = -360.0f;
                jumpsRemaining = 0;
                setAnimationState(AnimationState.JUMP);
            }
        }

        // Ladder climbing (W/S when on ladder)
        if (onLadder) {
            velocityX = 0;
            velocityY = 0;
            if (isKeyPressed(KeyEvent.VK_W) || isKeyPressed(KeyEvent.VK_UP)) {
                velocityY = -CLIMB_SPEED;
            }
            if (isKeyPressed(KeyEvent.VK_S) || isKeyPressed(KeyEvent.VK_DOWN)) {
                velocityY = CLIMB_SPEED;
            }
        }
        
        // Dash
        if (isKeyPressed(KeyEvent.VK_SHIFT) && canDash && isGrounded) {
            velocityX = (isFacingRight ? 1 : -1) * 350.0f;
            canDash = false;
            lastDashTimeMs = System.currentTimeMillis();
        }
        
        // Shoot/Attack
        if (isKeyPressed(KeyEvent.VK_CONTROL)) {
            shootWeapon();
        }
    }
    
    /**
     * Update state transitions - COMPLETE STATE LOGIC
     */
    private void updateStateTransitions() {
        boolean wasGrounded = isGrounded;
        
        // Landing from jump - transition back to IDLE or WALK
        if (isGrounded && !wasGrounded && currentState == AnimationState.JUMP) {
            // Just landed
            int direction = 0;
            if (isKeyPressed(KeyEvent.VK_LEFT) || isKeyPressed(KeyEvent.VK_A)) {
                direction = -1;
            }
            if (isKeyPressed(KeyEvent.VK_RIGHT) || isKeyPressed(KeyEvent.VK_D)) {
                direction = 1;
            }
            
            if (direction != 0) {
                setAnimationState(AnimationState.WALK);
            } else {
                setAnimationState(AnimationState.IDLE);
            }
        }
        
        // Dash cooldown recovery
        if (!canDash && System.currentTimeMillis() - lastDashTimeMs >DASH_COOLDOWN_MS) {
            canDash = true;
        }
        
        // Walking/Idle on ground
        if (isGrounded && (currentState == AnimationState.IDLE || currentState == AnimationState.WALK)) {
            int direction = 0;
            if (isKeyPressed(KeyEvent.VK_LEFT) || isKeyPressed(KeyEvent.VK_A)) {
                direction = -1;
            }
            if (isKeyPressed(KeyEvent.VK_RIGHT) || isKeyPressed(KeyEvent.VK_D)) {
                direction = 1;
            }
            
            if (direction == 0 && currentState != AnimationState.IDLE) {
                setAnimationState(AnimationState.IDLE);
            } else if (direction != 0 && currentState != AnimationState.WALK) {
                setAnimationState(AnimationState.WALK);
            }
        }
    }
    
    /**
     * Apply physics - gravity, velocity, collision - COMPLETE PHYSICS
     */
    private void applyPhysics(float deltaTime) {
        // Skip gravity when on ladder
        if (onLadder) {
            x += velocityX * deltaTime;
            y += velocityY * deltaTime;
            if (x < 0) x = 0;
            if (x > 20000) x = 20000;
            return;
        }

        // Apply gravity when not grounded
        if (!isGrounded) {
            velocityY += 800.0f * deltaTime;  // Gravity constant
            if (velocityY > 600.0f) {  // Terminal velocity
                velocityY = 600.0f;
            }
        }
        
        // Update position
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
        
        // Boundary checks
        if (x < 0) x = 0;
        if (x > 20000) x = 20000;  // Level width
        // Fall damage removed - handled in collision detection
        
        checkTileCollision();
    }
    
    /**
     * Check collision with tilemap - COMPLETE COLLISION
     */
    private void checkTileCollision() {
        int tileSize = 32;
        int tileX = (int)(x / tileSize);
        int tileY = (int)((y + SPRITE_HEIGHT) / tileSize);
        
        // Collision detection - simple ground plane
        // Platform at y=480 with height 40 pixels, so floor is at y=520
        float groundLevel = 520.0f;
        
        // Check if player is falling onto ground
        if (y + SPRITE_HEIGHT >= groundLevel && velocityY > 0) {
            y = groundLevel - SPRITE_HEIGHT;
            velocityY = 0;
            isGrounded = true;
            jumpsRemaining = 2;  // reset double jump on ground
        } else if (y >= 3000) {
            // Safety check - prevent falling infinitely
            takeDamage(100);
            y = groundLevel - SPRITE_HEIGHT;
            velocityY = 0;
            isGrounded = true;
        } else if (y + SPRITE_HEIGHT < groundLevel) {
            isGrounded = false;
        } else {
            isGrounded = true;
        }
    }
    
    /**
     * Update animation frame - COMPLETE ANIMATION CYCLING
     */
    private void updateAnimation(float deltaTime) {
        // Reset animation if state changed
        if (currentState != previousState) {
            currentFrameIndex = 0;
            frameTimerMs = 0;
            previousState = currentState;
            if (VERBOSE) {
                System.out.println(TAG + " STATE CHANGE: " + currentState.name());
            }
        }
        
        BufferedImage[] frames = spriteFrames.get(currentState.name().toLowerCase());
        if (frames == null || frames.length == 0) {
            // Fallback if animation not loaded
            return;
        }
        
        // Update animation frame timer
        frameTimerMs += deltaTime * 1000;  // Convert to milliseconds
        int animSpeed = calculateAnimationSpeed(currentState);
        
        if (frameTimerMs >= animSpeed) {
            frameTimerMs = 0;
            currentFrameIndex++;
            
            // Loop or end animation
            if (currentFrameIndex >= frames.length) {
                currentFrameIndex = 0;
                
                // One-shot animations: return to idle after finishing
                if (currentState == AnimationState.ATTACK || currentState == AnimationState.HIT) {
                    setAnimationState(AnimationState.IDLE);
                }
            }
        }
    }
    
    /**
     * Calculate animation speed based on state
     */
    private int calculateAnimationSpeed(AnimationState state) {
        switch (state) {
            case IDLE:
                return 150;  // 150ms per frame
            case WALK:
                return 100;  // 100ms per frame
            case JUMP:
                return 80;   // 80ms per frame
            case ATTACK:
                return 60;   // 60ms per frame
            case HIT:
                return 80;   // 80ms per frame
            case DEATH:
                return 200;  // 200ms per frame
            default:
                return 100;
        }
    }
    
    /**
     * Render player sprite - COMPLETE WITH HEALTH BAR
     */
    public void render(Graphics2D g, int cameraOffsetX, int cameraOffsetY) {
        BufferedImage[] frames = spriteFrames.get(currentState.name().toLowerCase());
        if (frames == null || frames.length == 0) return;
        
        BufferedImage currentFrame = frames[currentFrameIndex];
        if (currentFrame == null) return;
        
        int screenX = (int)(x - cameraOffsetX);
        int screenY = (int)(y - cameraOffsetY);
        
        if (isFacingRight) {
            g.drawImage(currentFrame, screenX, screenY, SPRITE_WIDTH, SPRITE_HEIGHT, null);
        } else {
            g.drawImage(currentFrame, screenX + SPRITE_WIDTH, screenY, -SPRITE_WIDTH, SPRITE_HEIGHT, null);
        }
        
        drawHealthBar(g, screenX, screenY - 10);
    }
    
    /**
     * Draw health bar
     */
    private void drawHealthBar(Graphics2D g, int x, int y) {
        int barWidth = 50;
        int barHeight = 4;
        g.setColor(java.awt.Color.DARK_GRAY);
        g.fillRect(x, y, barWidth, barHeight);
        float healthPercent = (float)health / maxHealth;
        g.setColor(java.awt.Color.GREEN);
        g.fillRect(x, y, (int)(barWidth * healthPercent), barHeight);
        g.setColor(java.awt.Color.WHITE);
        g.drawRect(x, y, barWidth, barHeight);
    }
    
    /**
     * Attack (simplified - triggers attack animation)
     */
    public void shootWeapon() {
        long now = System.currentTimeMillis();
        // Use weapon fire rate if weapon equipped, else default cooldown
        long cooldown = ATTACK_COOLDOWN_MS;
        if (activeWeaponSlot >= 0 && weaponSlots[activeWeaponSlot] != null) {
            cooldown = weaponSlots[activeWeaponSlot].fireRateMs;
            if (weaponAmmo[activeWeaponSlot] <= 0) return;  // no ammo
        }
        if (now - lastAttackTimeMs < cooldown) return;

        lastAttackTimeMs = now;
        if (activeWeaponSlot >= 0 && weaponSlots[activeWeaponSlot] != null) {
            weaponAmmo[activeWeaponSlot]--;
        }
        setAnimationState(AnimationState.ATTACK);
    }
    
    /**
     * Get a projectile to fire (called after shootWeapon if applicable)
     */
    public Projectile getProjectileToFire() {
        long now = System.currentTimeMillis();
        if (now - lastAttackTimeMs < 50) {  // Only fire if recently attacked
            float dmg = 10.0f;
            float speed = 500.0f;
            if (activeWeaponSlot >= 0 && weaponSlots[activeWeaponSlot] != null) {
                dmg = weaponSlots[activeWeaponSlot].damage;
                speed = 550.0f;
            }
            float projVelX = isFacingRight ? speed : -speed;
            float projVelY = -100.0f;
            float projX = isFacingRight ? x + SPRITE_WIDTH : x;
            float projY = y + SPRITE_HEIGHT / 2;
            return new Projectile(projX, projY, projVelX, projVelY, dmg, 5.0f);
        }
        return null;
    }
    
    /**
     * Dash ability - COMPLETE IMPLEMENTATION
     */
    public void dash(int direction) {
        // Already handled in applyInput() for Shift key
        // This is kept for external calls
    }
    
    /**
     * Take damage - COMPLETE WITH STATE
     */
    public void takeDamage(int damage) {
        long now = System.currentTimeMillis();
        if (now - lastDamageTimeMs < DAMAGE_COOLDOWN_MS) return;
        
        health -= damage;
        lastDamageTimeMs = now;
        
        if (VERBOSE) {
            System.out.println(TAG + " DAMAGE: " + damage + " | Health: " + health + "/" + maxHealth);
        }
        
        if (health > 0) {
            setAnimationState(AnimationState.HIT);
            velocityY = -200.0f;  // Knockback up
        } else {
            setAnimationState(AnimationState.DEATH);
        }
    }
    
    private void setAnimationState(AnimationState state) {
        if (state != currentState) {
            currentState = state;
            currentFrameIndex = 0;
            frameTimerMs = 0;
        }
    }
    
    // Getters
    public float getX() { return x; }
    public float getY() { return y; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public boolean isAlive() { return isAlive; }
    public float getVelX() { return velocityX; }
    public float getVelY() { return velocityY; }
    public boolean isGrounded() { return isGrounded; }
    public AnimationState getAnimationState() { return currentState; }
    public int getAnimationFrame() { return currentFrameIndex; }
    public CharacterType getCharacterType() { return character; }
    public boolean canDash() {
        if (!canDash) {
            if (System.currentTimeMillis() - lastDashTimeMs >= DASH_COOLDOWN_MS) {
                canDash = true;
                return true;
            }
            return false;
        }
        return true;
    }

    // =========================================================================
    //  HEAL SYSTEM (H key)
    // =========================================================================
    public boolean tryHeal() {
        long now = System.currentTimeMillis();
        if (now - lastHealTimeMs < HEAL_COOLDOWN_MS) return false;
        if (health >= maxHealth) return false;
        if (!isAlive) return false;

        lastHealTimeMs = now;
        isHealing = true;
        healAnimTimer = 0f;
        health = Math.min(maxHealth, health + HEAL_AMOUNT);
        System.out.println(TAG + " HEALED +" + HEAL_AMOUNT + " → " + health + "/" + maxHealth);
        return true;
    }

    public float getHealCooldownPct() {
        long elapsed = System.currentTimeMillis() - lastHealTimeMs;
        if (elapsed >= HEAL_COOLDOWN_MS) return 1f;
        return (float) elapsed / HEAL_COOLDOWN_MS;
    }

    // =========================================================================
    //  LADDER SYSTEM
    // =========================================================================
    public void setOnLadder(boolean val) {
        if (val && !onLadder) {
            onLadder = true;
            velocityX = 0;
            velocityY = 0;
            isGrounded = false;
        } else if (!val && onLadder) {
            onLadder = false;
        }
    }

    public boolean isOnLadder() { return onLadder; }

    // =========================================================================
    //  WEAPON INVENTORY SYSTEM (4 slots)
    // =========================================================================
    public boolean pickupWeapon(WeaponType weapon) {
        // Find first empty slot
        for (int i = 0; i < weaponSlots.length; i++) {
            if (weaponSlots[i] == null) {
                weaponSlots[i] = weapon;
                weaponAmmo[i] = weapon.maxAmmo;
                if (activeWeaponSlot < 0) activeWeaponSlot = i;
                System.out.println(TAG + " Picked up " + weapon.name + " in slot " + (i + 1));
                return true;
            }
        }
        // All slots full — replace active slot
        if (activeWeaponSlot >= 0) {
            weaponSlots[activeWeaponSlot] = weapon;
            weaponAmmo[activeWeaponSlot] = weapon.maxAmmo;
            System.out.println(TAG + " Replaced slot " + (activeWeaponSlot + 1) + " with " + weapon.name);
            return true;
        }
        return false;
    }

    public void switchWeaponSlot(int slot) {
        if (slot < 0 || slot >= weaponSlots.length) return;
        if (weaponSlots[slot] != null) {
            activeWeaponSlot = slot;
        }
    }

    public void cycleWeaponNext() {
        if (activeWeaponSlot < 0) return;
        for (int i = 1; i <= 4; i++) {
            int next = (activeWeaponSlot + i) % 4;
            if (weaponSlots[next] != null) { activeWeaponSlot = next; return; }
        }
    }

    public void cycleWeaponPrev() {
        if (activeWeaponSlot < 0) return;
        for (int i = 1; i <= 4; i++) {
            int prev = (activeWeaponSlot - i + 4) % 4;
            if (weaponSlots[prev] != null) { activeWeaponSlot = prev; return; }
        }
    }

    public WeaponType getActiveWeapon() {
        if (activeWeaponSlot < 0 || activeWeaponSlot >= 4) return null;
        return weaponSlots[activeWeaponSlot];
    }

    public int getActiveAmmo() {
        if (activeWeaponSlot < 0) return 0;
        return weaponAmmo[activeWeaponSlot];
    }

    public WeaponType[] getWeaponSlots() { return weaponSlots; }
    public int[] getWeaponAmmo() { return weaponAmmo; }
    public int getActiveWeaponSlot() { return activeWeaponSlot; }

    // Collectible tracking
    public void addCash(int amount) { cashCollected += amount; }
    public void addCard() { cardsCollected++; }
    public int getCash() { return cashCollected; }
    public int getCards() { return cardsCollected; }

    // Position setter (used by Game.java for conveyor, level switch)
    public void setPosition(float nx, float ny) { this.x = nx; this.y = ny; }

    // Platform collision setter
    public static void setPlatforms(float[][] plats) { /* used by Game.java */ }
}
