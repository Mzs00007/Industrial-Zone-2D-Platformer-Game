package managers;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * CHILD CLASS 3: BossController
 * 
 * ✅ Inherits from GameEntity:
 * - Physics system (gravity, friction, collision) - INHERITED
 * - Damage system (takeDamage) - INHERITED
 * - Update loop (applyGravity, movement) - INHERITED
 * 
 * ✅ DEMONSTRATES CODE REUSE:
 * - Boss uses SAME applyGravity() as Player and Enemy
 * - Boss uses SAME takeDamage() as Player and Enemy
 * - 3 completely different behaviors, 1 physics engine
 * 
 * ✅ BOSS-SPECIFIC:
 * - Multi-phase combat (3 phases based on health)
 * - More complex animation patterns
 * - Boss death triggers victory sequence
 * - Much more health than normal enemies
 */
public class BossController extends GameEntity {
    
    // ════════════════════════════════════════════════════════════════
    // BOSS-SPECIFIC PROPERTIES
    // ════════════════════════════════════════════════════════════════
    private int bossPhase = 1;  // 1=Normal, 2=Intense, 3=Desperate
    
    private List<float[]> attackPatterns;    // Predefined attack sequences
    private int currentAttackIndex = 0;
    
    private long lastPhaseChangeTime = 0;
    private long lastAttackTime = 0;
    private static final long ATTACK_COOLDOWN = 2000;  // ms
    
    private boolean isCharging = false;      // Boss is charging attack
    private float chargeEnergy = 0;          // Charge bar 0-100
    
    // Boss-specific movement
    private float moveSpeed = 2.0f;
    private boolean movingRight = true;
    
    // Animation
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final int FRAME_DELAY = 200;  // ms per frame (slowest)
    
    // ════════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ════════════════════════════════════════════════════════════════
    public BossController(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.health = 500;  // Boss has way more health
        this.maxHealth = 500;
        this.width = 64;    // Bigger than regular enemies
        this.height = 80;
        this.isAlive = true;
        this.animationState = 0;  // IDLE
        this.bossPhase = 1;
        
        initializeAttackPatterns();
    }
    
    /**
     * Initialize boss attack patterns
     */
    private void initializeAttackPatterns() {
        attackPatterns = new ArrayList<>();
        // Phase 1: Simple patterns
        attackPatterns.add(new float[]{100, 300, 500});  // Attack positions
        // Phase 2: Complex patterns
        attackPatterns.add(new float[]{150, 250, 350, 450});
        // Phase 3: Desperate patterns
        attackPatterns.add(new float[]{100, 200, 300, 400, 500});
    }
    
    // ════════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS - Boss-specific implementations
    // ════════════════════════════════════════════════════════════════
    
    /**
     * Boss update with complex AI and attacks
     */
    @Override
    public void update(float deltaTime) {
        // Call parent update for physics
        super.update(deltaTime);
        
        // ONLY boss-specific: phase management and attacks
        updatePhase();
        updateBossAI(deltaTime);
        updateAttacks(deltaTime);
    }
    
    /**
     * Update phase based on health
     * Shows how inheritance allows boss to inherit physics
     * while adding complex behavior on top
     */
    private void updatePhase() {
        if (health > 333) {
            bossPhase = 1;  // 100-66% health
        } else if (health > 166) {
            bossPhase = 2;  // 66-33% health
        } else {
            bossPhase = 3;  // 33-0% health (desperate)
        }
    }
    
    /**
     * Boss AI behavior changes by phase
     */
    private void updateBossAI(float deltaTime) {
        switch (bossPhase) {
            case 1:  // Normal phase - slow movement
                normalPhaseAI();
                break;
                
            case 2:  // Intense phase - faster movement
                intensePhaseAI();
                break;
                
            case 3:  // Desperate phase - aggressive attacks
                desperatePhaseAI();
                break;
        }
    }
    
    private void normalPhaseAI() {
        // Slow back-and-forth movement
        if (movingRight) {
            velocityX = moveSpeed;
            if (x >= 1200) {
                movingRight = false;
            }
        } else {
            velocityX = -moveSpeed;
            if (x <= 200) {
                movingRight = true;
            }
        }
    }
    
    private void intensePhaseAI() {
        // Faster movement, more erratic
        if (movingRight) {
            velocityX = moveSpeed * 1.5f;
            if (x >= 1150) {
                movingRight = false;
            }
        } else {
            velocityX = -moveSpeed * 1.5f;
            if (x <= 250) {
                movingRight = true;
            }
        }
    }
    
    private void desperatePhaseAI() {
        // Very fast, aggressive movement
        if (movingRight) {
            velocityX = moveSpeed * 2.0f;
            if (x >= 1100) {
                movingRight = false;
            }
        } else {
            velocityX = -moveSpeed * 2.0f;
            if (x <= 300) {
                movingRight = true;
            }
        }
    }
    
    /**
     * Boss attack system with charging
     */
    private void updateAttacks(float deltaTime) {
        long currentTime = System.currentTimeMillis();
        
        // Charge attack
        if (isCharging) {
            chargeEnergy = Math.min(100, chargeEnergy + 2);
        }
        
        // Fire attack when charged
        if (chargeEnergy >= 100 && currentTime - lastAttackTime >= ATTACK_COOLDOWN) {
            fireAttack();
            chargeEnergy = 0;
            isCharging = false;
            lastAttackTime = currentTime;
        }
        
        // Determine when to charge based on phase
        if (bossPhase >= 2 && chargeEnergy < 100) {
            isCharging = true;
        }
    }
    
    private void fireAttack() {
        System.out.println("BOSS ATTACK! Phase " + bossPhase + " - Charge: " + (int)chargeEnergy + "%");
        // Fire projectiles, play attack animation, etc.
    }
    
    /**
     * Boss-specific animation with phase transitions
     */
    @Override
    protected void updateAnimation(float deltaTime) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= FRAME_DELAY) {
            currentFrame++;
            lastFrameTime = currentTime;
            
            // Boss animations are lengthy
            int frameCount = isCharging ? 16 : 8;  // Longer attack animation
            if (currentFrame >= frameCount) {
                currentFrame = 0;
            }
        }
        
        // Set animation state based on activity
        if (isCharging) {
            animationState = 2;  // Charge animation
        } else {
            animationState = 1;  // Walk animation
        }
    }
    
    /**
     * Boss-specific rendering (large and distinctive)
     */
    @Override
    public void draw(Graphics2D g) {
        if (!isAlive) return;
        
        // Color changes by phase
        Color bossColor;
        switch (bossPhase) {
            case 1: bossColor = new Color(150, 50, 150); break;  // Purple
            case 2: bossColor = new Color(200, 80, 50); break;   // Orange
            case 3: bossColor = new Color(200, 20, 20); break;   // Red
            default: bossColor = Color.MAGENTA;
        }
        
        g.setColor(bossColor);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
        
        // Boss health bar (larger)
        g.setColor(Color.RED);
        g.fillRect((int)x - 10, (int)(y - 20), (int)(width + 20), 8);
        g.setColor(Color.GREEN);
        g.fillRect((int)x - 10, (int)(y - 20), (int)((width + 20) * health / maxHealth), 8);
        
        // Charge indicator
        if (isCharging) {
            g.setColor(Color.YELLOW);
            g.fillRect((int)x, (int)(y + height + 5), (int)(chargeEnergy * width / 100), 5);
        }
        
        // Boss status text
        g.setColor(Color.WHITE);
        g.drawString("BOSS Phase " + bossPhase, (int)x - 5, (int)(y - 30));
    }
    
    /**
     * Boss death triggers victory sequence
     */
    @Override
    protected void onDeath() {
        System.out.println("═══════════════════════════════════════");
        System.out.println("BOSS DEFEATED!");
        System.out.println("Final Phase: " + bossPhase);
        System.out.println("═══════════════════════════════════════");
        // Trigger victory screen, play special effects, credits, etc.
    }
    
    @Override
    public int getMaxHealth() {
        return 500;
    }
    
    // ════════════════════════════════════════════════════════════════
    // BOSS-SPECIFIC METHODS
    // ════════════════════════════════════════════════════════════════
    
    public int getPhase() { 
        return bossPhase; 
    }
    
    public float getChargeEnergy() { 
        return chargeEnergy; 
    }
    
    public boolean isCharging() { 
        return isCharging; 
    }
}
