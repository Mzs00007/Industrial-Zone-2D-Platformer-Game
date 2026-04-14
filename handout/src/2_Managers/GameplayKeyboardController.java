package core;

import core.assets.AssetManager_001_UnifiedLoader;
import core.assets.AssetMetadata;
import core.assets.AssetPathBridge;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * PHASE 3: KEYBOARD EVENT SYSTEM
 * 
 * Maps keyboard input to character state changes and asset triggers.
 * Processes multiple key presses simultaneously (WASD + SPACE).
 * 
 * KEY MAPPING:
 * - A/D: Move left/right (Walk animation + footstep particles + audio)
 * - W: Jump (Jump animation + aura VFX + audio, velocity.y = -12)
 * - SPACE: Attack/Slash (Attack animation + weapon, blood + sparks on hit)
 * - SHIFT: Sprint (Run animation 1.5× speed + enhanced particles)
 * - E: Interact (Hold idle pose + UI glow)
 * - ESC: Pause (Freeze animations, show menu, fade music)
 * - M: Map overlay
 * - I: Inventory menu
 * 
 * Timeline: Input → Processing (1ms) → State Change → Render (16ms)
 * Integration Points: Game.addKeyListener(), called from game loop update()
 */
public class GameplayKeyboardController extends KeyAdapter {
    
    // ===== KEY ACTION ENUM =====
    public enum KeyAction {
        MOVE_LEFT(KeyEvent.VK_A),
        MOVE_RIGHT(KeyEvent.VK_D),
        JUMP(KeyEvent.VK_W),
        ATTACK(KeyEvent.VK_SPACE),
        SPRINT(KeyEvent.VK_SHIFT),
        INTERACT(KeyEvent.VK_E),
        PAUSE(KeyEvent.VK_ESCAPE),
        MAP(KeyEvent.VK_M),
        INVENTORY(KeyEvent.VK_I);
        
        public final int keyCode;
        KeyAction(int keyCode) {
            this.keyCode = keyCode;
        }
        
        public static KeyAction fromKeyCode(int keyCode) {
            for (KeyAction action : KeyAction.values()) {
                if (action.keyCode == keyCode) {
                    return action;
                }
            }
            return null;
        }
    }
    
    // ===== INSTANCE VARIABLES =====
    
    // Track which keys are currently pressed (for continuous updates)
    private Set<Integer> keysPressed = new HashSet<>();
    
    // References to game systems
    private GameplayAnimationController animationController;
    private CharacterPhysicsProfile physicsProfile;
    private GameplayVFXCoordinator vfxCoordinator;
    private GameplayAudioVisualSynchronizer audioSynchronizer;
    private Game gameInstance;
    
    // Movement state tracking
    private float currentVelocityX = 0.0f;
    private float currentVelocityY = 0.0f;
    private boolean isGrounded = true;
    private boolean canJump = true;
    private boolean isAttacking = false;
    private long attackCooldownMs = 0;
    private static final long ATTACK_COOLDOWN_MS = 500;  // 500ms between attacks
    
    // Sprint tracking
    private boolean isSprinting = false;
    private static final float SPRINT_MULTIPLIER = 1.5f;
    
    // ===== CONSTRUCTOR =====
    public GameplayKeyboardController(GameplayAnimationController animationController,
                                      CharacterPhysicsProfile physicsProfile,
                                      GameplayVFXCoordinator vfxCoordinator,
                                      GameplayAudioVisualSynchronizer audioSynchronizer,
                                      Game gameInstance) {
        this.animationController = animationController;
        this.physicsProfile = physicsProfile;
        this.vfxCoordinator = vfxCoordinator;
        this.audioSynchronizer = audioSynchronizer;
        this.gameInstance = gameInstance;
        
        Core.logger.info("[GameplayKeyboardController] Initialized - Ready for Phase 3 input");
    }
    
    // ===== JAVA EVENT HANDLERS =====
    
    /**
     * Called when key is pressed (fired immediately, before keyTyped/keyReleased).
     * Timestamp: t=0ms in input timeline
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        // Prevent repeated events from key press and hold
        if (keysPressed.contains(keyCode)) {
            return;  // Already processing this key
        }
        
        keysPressed.add(keyCode);
        KeyAction action = KeyAction.fromKeyCode(keyCode);
        
        if (action != null) {
            Core.logger.debug("[KeyboardController] Key pressed: " + action.name() + " (VK_" + keyCode + ")");
            triggerKeyAction(action, true);  // true = key down
        }
    }
    
    /**
     * Called when key is released.
     * Timestamp: t=500ms+ (when user releases key)
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keysPressed.remove(keyCode);
        KeyAction action = KeyAction.fromKeyCode(keyCode);
        
        if (action != null) {
            Core.logger.debug("[KeyboardController] Key released: " + action.name());
            triggerKeyAction(action, false);  // false = key up
        }
    }
    
    // ===== FRAME UPDATE (Called every 16ms from game loop) =====
    /**
     * Process held keys and update character state each frame.
     * Called from Game.update() loop, not from KeyEvent handlers.
     */
    public void update(long deltaTimeMs) {
        // Reduce attack cooldown
        if (attackCooldownMs > 0) {
            attackCooldownMs -= deltaTimeMs;
        }
        
        // Process movement keys
        currentVelocityX = 0.0f;  // Reset velocity each frame
        boolean movingLeft = keysPressed.contains(KeyEvent.VK_A);
        boolean movingRight = keysPressed.contains(KeyEvent.VK_D);
        
        if (movingLeft && !movingRight) {
            moveLeft();
        } else if (movingRight && !movingLeft) {
            moveRight();
        }
        
        // Check sprint status
        isSprinting = keysPressed.contains(KeyEvent.VK_SHIFT) && currentVelocityX != 0.0f;
        if (isSprinting) {
            currentVelocityX *= SPRINT_MULTIPLIER;
        }
        
        // Update physics (if physics system exists)
        if (physicsProfile != null) {
            physicsProfile.velocityX = currentVelocityX;
        }
        
        // Update animation based on movement
        updateMovementAnimation();
    }
    
    // ===== ACTION HANDLERS =====
    
    /**
     * Execute action based on key press/release.
     */
    private void triggerKeyAction(KeyAction action, boolean isKeyDown) {
        switch (action) {
            case MOVE_LEFT:
                if (isKeyDown) moveLeft();
                break;
                
            case MOVE_RIGHT:
                if (isKeyDown) moveRight();
                break;
                
            case JUMP:
                if (isKeyDown) jump();
                break;
                
            case ATTACK:
                if (isKeyDown) attack();
                break;
                
            case SPRINT:
                if (isKeyDown) sprintStart();
                else sprintEnd();
                break;
                
            case INTERACT:
                if (isKeyDown) interact();
                break;
                
            case PAUSE:
                if (isKeyDown) pause();
                break;
                
            case MAP:
                if (isKeyDown) openMap();
                break;
                
            case INVENTORY:
                if (isKeyDown) openInventory();
                break;
        }
    }
    
    // ===== MOVEMENT HANDLERS =====
    
    /**
     * Handle A key (Move Left).
     * Timeline: t=0ms key press → velocity.x = -5.0
     */
    private void moveLeft() {
        currentVelocityX = -5.0f;
        animationController.setFacingRight(false);
        Core.logger.debug("[KeyboardController] Move LEFT triggered");
    }
    
    /**
     * Handle D key (Move Right).
     */
    private void moveRight() {
        currentVelocityX = 5.0f;
        animationController.setFacingRight(true);
        Core.logger.debug("[KeyboardController] Move RIGHT triggered");
    }
    
    /**
     * Update animation based on current movement.
     * Called every frame to synchronize animation with physics.
     */
    private void updateMovementAnimation() {
        GameplayAnimationController.CharacterState targetState;
        
        if (currentVelocityX == 0.0f && isGrounded) {
            // Standing still
            targetState = GameplayAnimationController.CharacterState.IDLE;
        } else if (currentVelocityX != 0.0f && isGrounded) {
            // Moving on ground
            if (isSprinting) {
                targetState = GameplayAnimationController.CharacterState.RUN;
            } else {
                targetState = GameplayAnimationController.CharacterState.WALK;
            }
        } else if (!isGrounded) {
            // In air
            targetState = GameplayAnimationController.CharacterState.JUMP;
        } else {
            targetState = animationController.getCurrentState();
        }
        
        // Only change state if different
        if (targetState != animationController.getCurrentState()) {
            animationController.setCharacterState(targetState);
        }
    }
    
    // ===== JUMP HANDLER =====
    
    /**
     * Handle W key (Jump).
     * 
     * Timeline: t=0ms W key press
     * t=0ms:  velocity.y = -12
     * t=0ms:  Animation: JUMP state (6 frames, 40ms each)
     * t=0ms:  VFX: Jump aura + particle burst at character position
     * t=0ms:  Audio: sfx/jump.mp3 plays (300ms duration)
     * t=240ms: Gravity pulls player down
     * t=500ms+: Player lands (isGrounded = true)
     * t=500ms: Animation: JUMP → IDLE
     * t=500ms: VFX: Land particles
     * t=500ms: Audio: sfx/landing.mp3
     */
    private void jump() {
        if (!canJump || !isGrounded) {
            return;  // Can't jump while already in air
        }
        
        // Update physics
        currentVelocityY = -12.0f;  // Upward velocity
        isGrounded = false;
        canJump = false;
        
        // Update animation
        animationController.setCharacterState(GameplayAnimationController.CharacterState.JUMP);
        
        // Trigger VFX: Jump aura particles
        if (vfxCoordinator != null) {
            vfxCoordinator.spawnVFX(GameplayVFXCoordinator.VFXType.JUMP_AURA, 0, 0);  // x, y from player position
        }
        
        // Trigger audio: Jump sound
        if (audioSynchronizer != null) {
            audioSynchronizer.playAudioAtTime("jump", 0, 100);  // asset, delay, duration
        }
        
        Core.logger.debug("[KeyboardController] JUMP triggered - velocity.y = -12");
    }
    
    /**
     * Called when player lands (collision with ground).
     * Must be called from physics system collision detection.
     */
    public void onLanded() {
        isGrounded = true;
        canJump = true;
        currentVelocityY = 0.0f;
        
        // Trigger landing VFX and audio
        if (vfxCoordinator != null) {
            vfxCoordinator.spawnVFX(GameplayVFXCoordinator.VFXType.FOOTSTEP_PARTICLES, 0, 0);
        }
        if (audioSynchronizer != null) {
            audioSynchronizer.playAudioAtTime("landing", 0, 150);
        }
        
        // Return to IDLE or WALK based on velocity
        updateMovementAnimation();
        
        Core.logger.debug("[KeyboardController] Player landed");
    }
    
    // ===== ATTACK HANDLER =====
    
    /**
     * Handle SPACE key (Attack/Slash).
     * 
     * Timeline: t=0ms SPACE key press
     * t=0ms:  Animation: ATTACK state (10 frames, 40ms each)
     * t=0ms:  Weapon: Sword_Swing animation synchronized
     * t=0ms:  VFX: Slash trail particles (120ms visual)
     * t=0ms:  Audio: Wind-up sound (100ms)
     * t=120ms: IMPACT FRAME
     * t=120ms: Check collision with enemies in hitbox
     * [If HIT]:
     *   t=120ms: Blood splatter VFX (320ms)
     *   t=120ms: Impact audio replaces wind-up
     *   t=140ms: Spark burst VFX (+20ms delay)
     *   t=140ms: Enemy hurt animation
     *   t=140ms: Knockback applied to enemy
     * t=400ms: Attack animation complete
     * t=500ms: Attack cooldown expires, can attack again
     */
    private void attack() {
        // Check cooldown
        if (attackCooldownMs > 0) {
            Core.logger.debug("[KeyboardController] Attack on cooldown - " + attackCooldownMs + "ms remaining");
            return;
        }
        
        // Check if already attacking
        if (isAttacking) {
            return;
        }
        
        isAttacking = true;
        attackCooldownMs = ATTACK_COOLDOWN_MS;
        
        // Update animation to ATTACK state
        animationController.setCharacterState(GameplayAnimationController.CharacterState.ATTACK);
        
        // Register frame callbacks for VFX/audio timing
        
        // Frame 4 (120ms) = Impact frame
        animationController.registerFrameCallback(3, (frameNum, timeMs) -> {
            Core.logger.debug("[KeyboardController] Attack frame 4 reached - Checking collision");
            checkAttackCollision();
        });
        
        // Trigger initial audio: Wind-up sound
        if (audioSynchronizer != null) {
            audioSynchronizer.playAudioAtTime("slash_wind", 0, 100);  // assetKey, delay, duration
        }
        
        // Trigger VFX: Slash trail (visual feedback immediately)
        if (vfxCoordinator != null) {
            vfxCoordinator.spawnVFX(GameplayVFXCoordinator.VFXType.SLASH_TRAIL, 0, 0);
        }
        
        Core.logger.debug("[KeyboardController] ATTACK triggered - Cooldown: " + ATTACK_COOLDOWN_MS + "ms");
        
        // Clear attacking flag when animation completes
        animationController.registerFrameCallback(9, (frameNum, timeMs) -> {
            isAttacking = false;
        });
    }
    
    /**
     * Check if attack collides with any enemies.
     * Called at impact frame (frame 4, t=120ms).
     * Must be integrated with collision detection system.
     */
    private void checkAttackCollision() {
        // TODO: Integrate with physics collision system
        // For now, simulate a hit for demonstration
        
        // Simulate hit detection
        boolean hitDetected = true;  // TODO: Get from actual collision system
        
        if (hitDetected) {
            // Spawn blood VFX at impact point
            if (vfxCoordinator != null) {
                vfxCoordinator.spawnVFX(GameplayVFXCoordinator.VFXType.BLOOD_SPLATTER, 0, 0);
                
                // Delayed spark burst (+20ms after blood)
                vfxCoordinator.spawnVFXDelayed(GameplayVFXCoordinator.VFXType.SPARK_BURST, 20, 0, 0);
            }
            
            // Play impact audio
            if (audioSynchronizer != null) {
                audioSynchronizer.playAudioAtTime("impact", 0, 200);
            }
            
            Core.logger.debug("[KeyboardController] Attack HIT - Blood + Sparks + Impact audio triggered");
        } else {
            // Miss: no effect
            Core.logger.debug("[KeyboardController] Attack MISS - No collision");
        }
    }
    
    // ===== SPRINT HANDLERS =====
    
    private void sprintStart() {
        isSprinting = true;
        Core.logger.debug("[KeyboardController] Sprint START (1.5× speed multiplier)");
    }
    
    private void sprintEnd() {
        isSprinting = false;
        Core.logger.debug("[KeyboardController] Sprint END");
    }
    
    // ===== INTERACTION HANDLERS =====
    
    /**
     * Handle E key (Interact with objects/NPCs).
     */
    private void interact() {
        // TODO: Check if player is near interactive object
        // TODO: Trigger interaction (dialogue, item pickup, etc.)
        
        animationController.setCharacterState(GameplayAnimationController.CharacterState.IDLE);
        Core.logger.debug("[KeyboardController] INTERACT event triggered");
    }
    
    /**
     * Handle ESC key (Pause Menu).
     * 
     * Timeline:
     * t=0ms: ESC pressed
     * t=1ms: Game.pause() called, all updates stop
     * t=1ms: UI overlay fades in (300ms)
     * t=1ms: Character animation freezes at current frame
     * t=1ms: Music fades to 50% (300ms)
     * t=300ms: Pause menu fully visible
     * t=800ms: User clicks Resume
     * t=800ms: Menu close animation (300ms)
     * t=800ms: Music fades from 50% to 100% (300ms)
     * t=1100ms: Game fully resumed
     */
    private void pause() {
        if (gameInstance != null) {
            boolean isPaused = gameInstance.isPaused();
            
            if (!isPaused) {
                // Pause game
                gameInstance.setPaused(true);
                animationController.pause();
                
                // Trigger pause audio/visual
                if (audioSynchronizer != null) {
                    audioSynchronizer.pauseGameAudio(300);  // Fade duration in ms
                }
                
                Core.logger.info("[KeyboardController] Game PAUSED");
            } else {
                // Resume game
                gameInstance.setPaused(false);
                animationController.resume();
                
                // Trigger resume audio/visual
                if (audioSynchronizer != null) {
                    audioSynchronizer.resumeGameAudio(300);
                }
                
                Core.logger.info("[KeyboardController] Game RESUMED");
            }
        }
    }
    
    /**
     * Handle M key (Open Map overlay).
     */
    private void openMap() {
        // TODO: Show map overlay
        Core.logger.debug("[KeyboardController] MAP opened");
    }
    
    /**
     * Handle I key (Open Inventory).
     */
    private void openInventory() {
        // TODO: Show inventory menu
        Core.logger.debug("[KeyboardController] INVENTORY opened");
    }
    
    // ===== PHYSICS STATE SETTERS =====
    
    /**
     * Called from physics system to update grounded state.
     */
    public void setGrounded(boolean grounded) {
        this.isGrounded = grounded;
        if (grounded && !keysPressed.contains(KeyEvent.VK_W)) {
            canJump = true;
        }
    }
    
    /**
     * Called from physics system to update vertical velocity (gravity effect).
     */
    public void updateVerticalVelocity(float deltaTime) {
        if (!isGrounded) {
            // Apply gravity (9.8 m/s² = 0.163 per frame @ 60fps)
            currentVelocityY += 0.163f * deltaTime;
        }
        
        if (physicsProfile != null) {
            physicsProfile.velocityY = currentVelocityY;
        }
    }
    
    // ===== GETTERS =====
    public float getCurrentVelocityX() {
        return currentVelocityX;
    }
    
    public float getCurrentVelocityY() {
        return currentVelocityY;
    }
    
    public boolean isGrounded() {
        return isGrounded;
    }
    
    public boolean isAttacking() {
        return isAttacking;
    }
    
    public boolean isSprinting() {
        return isSprinting;
    }
    
    public Set<Integer> getKeysPressed() {
        return new HashSet<>(keysPressed);
    }
}
