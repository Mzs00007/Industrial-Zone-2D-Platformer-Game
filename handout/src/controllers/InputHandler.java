package controllers;
import game2D.*;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import utils.Config;

/**
 * InputHandler - PRODUCTION INPUT MAPPING
 * 
 * RESPONSIBILITY: Translate raw keyboard input to game actions
 * 
 * NO hardcoding of game logic - ALL constants from Config
 * Clean separation: Input layer → Game layer
 * 
 * Tracks key state for frame-based queries (better than event-based for games)
 * 
 * Supported Actions:
 * - Movement: LEFT/RIGHT/A/D
 * - Jumping: SPACE
 * - Attacking: CTRL
 * - Pause: ESC
 * 
 * @since 2026-04-14
 */
public class InputHandler {
    
    private static final String TAG = "[InputHandler]";
    
    // ═══════════════════════════════════════════════════════════════════════════════════
    // KEY BINDING CONSTANTS - Can be customized in SettingsScreen later
    // ═══════════════════════════════════════════════════════════════════════════════════
    
    public static final int KEY_MOVE_LEFT_PRIMARY = KeyEvent.VK_LEFT;
    public static final int KEY_MOVE_LEFT_SECONDARY = KeyEvent.VK_A;
    
    public static final int KEY_MOVE_RIGHT_PRIMARY = KeyEvent.VK_RIGHT;
    public static final int KEY_MOVE_RIGHT_SECONDARY = KeyEvent.VK_D;
    
    public static final int KEY_JUMP = KeyEvent.VK_SPACE;
    public static final int KEY_SHOOT = KeyEvent.VK_CONTROL;
    public static final int KEY_PAUSE = KeyEvent.VK_ESCAPE;
    public static final int KEY_INTERACT = KeyEvent.VK_E;
    public static final int KEY_DASH = KeyEvent.VK_SHIFT;
    
    // ═══════════════════════════════════════════════════════════════════════════════════
    // INPUT STATE - Set of currently pressed keys
    // ═══════════════════════════════════════════════════════════════════════════════════
    
    private static final Set<Integer> keysPressed = new HashSet<>();
    private static boolean debugMode = false;
    
    // ═══════════════════════════════════════════════════════════════════════════════════
    // PUBLIC QUERY METHODS - Used by game logic to check player intentions
    // ═══════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Check if player is moving left
     * 
     * @return true if LEFT or A is pressed
     */
    public static boolean isMoveLeftPressed() {
        return keysPressed.contains(KEY_MOVE_LEFT_PRIMARY) || 
               keysPressed.contains(KEY_MOVE_LEFT_SECONDARY);
    }
    
    /**
     * Check if player is moving right
     * 
     * @return true if RIGHT or D is pressed
     */
    public static boolean isMoveRightPressed() {
        return keysPressed.contains(KEY_MOVE_RIGHT_PRIMARY) || 
               keysPressed.contains(KEY_MOVE_RIGHT_SECONDARY);
    }
    
    /**
     * Check if player wants to jump
     * 
     * @return true if SPACE is pressed
     */
    public static boolean isJumpPressed() {
        return keysPressed.contains(KEY_JUMP);
    }
    
    /**
     * Check if player wants to shoot weapon
     * 
     * @return true if CTRL is pressed
     */
    public static boolean isShootPressed() {
        return keysPressed.contains(KEY_SHOOT);
    }
    
    /**
     * Check if player wants to pause
     * 
     * @return true if ESC is pressed
     */
    public static boolean isPausePressed() {
        return keysPressed.contains(KEY_PAUSE);
    }
    
    /**
     * Check if player wants to interact with object
     * 
     * @return true if E is pressed
     */
    public static boolean isInteractPressed() {
        return keysPressed.contains(KEY_INTERACT);
    }
    
    /**
     * Check if player wants to dash
     * 
     * @return true if SHIFT is pressed
     */
    public static boolean isDashPressed() {
        return keysPressed.contains(KEY_DASH);
    }
    
    /**
     * Get current horizontal movement direction
     * 
     * @return -1 (left), 0 (stationary), +1 (right)
     */
    public static int getHorizontalDirection() {
        if (isMoveLeftPressed()) return -1;
        if (isMoveRightPressed()) return 1;
        return 0;
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════
    // EVENT HANDLERS - Called by GameCore/AWT event system
    // ═══════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Called by AWT when key pressed down
     * 
     * @param e KeyEvent from keyboard
     */
    public static void onKeyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
        
        if (debugMode) {
            System.out.println(TAG + " Key pressed: " + e.getKeyCode() + 
                             " (" + KeyEvent.getKeyText(e.getKeyCode()) + ")");
        }
    }
    
    /**
     * Called by AWT when key released
     * 
     * @param e KeyEvent from keyboard
     */
    public static void onKeyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
        
        if (debugMode) {
            System.out.println(TAG + " Key released: " + e.getKeyCode());
        }
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════
    // APPLICATION LAYER - Apply input to game entities
    // ═══════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Apply current input state to player entity
     * Called each frame from GameplayController.update()
     * 
     * @param player The player entity to update
     */
    public static void applyInputToPlayer(PlayerEntity player) {
        // Horizontal movement
        int direction = getHorizontalDirection();
        if (direction != 0) {
            player.velocityX = direction * Config.PLAYER_MOVE_SPEED;
            player.setFacingDirection(direction > 0);  // true = right, false = left
        } else {
            // Decelerate gradually
            player.velocityX *= 0.90f;
        }
        
        // Jumping (only if on ground)
        if (isJumpPressed() && player.isGrounded) {
            player.velocityY = Config.PLAYER_JUMP_POWER;
            player.isGrounded = false;
            
            // Play jump sound
            playSound(Config.SFX_JUMP);
        }
        
        // Weapon attack
        if (isShootPressed()) {
            player.shootWeapon();
        }
        
        // Dash ability (if available)
        if (isDashPressed() && player.canDash()) {
            player.dash(getHorizontalDirection());
            playSound(Config.SFX_DASH);
        }
        
        // Interaction (pickup weapons, activate switches)
        if (isInteractPressed()) {
            player.interactWithNearby();
        }
    }
    
    // ═══════════════════════════════════════════════════════════════════════════════════
    // UTILITY METHODS
    // ═══════════════════════════════════════════════════════════════════════════════════
    
    /**
     * Clear all accumulated input (used when menu opens, focus lost, etc.)
     * Prevents "stuck keys" bug
     */
    public static void clearAllInput() {
        keysPressed.clear();
        if (debugMode) {
            System.out.println(TAG + " All input cleared");
        }
    }
    
    /**
     * Enable/disable debug logging
     * 
     * @param enabled true to log all key events
     */
    public static void setDebugMode(boolean enabled) {
        debugMode = enabled;
    }
    
    /**
     * Play sound effect (delegates to AudioManager)
     * 
     * @param soundFilename Sound to play (e.g., Config.SFX_JUMP)
     */
    private static void playSound(String soundFilename) {
        try {
            // TODO: Wire to AudioManager
            // AudioManager.playEffect(Config.AUDIO_SFX + soundFilename);
        } catch (Exception e) {
            System.err.println(TAG + " Failed to play sound: " + soundFilename);
        }
    }
    
    /**
     * Get current key state (for UI display or debugging)
     * 
     * @return Set of currently pressed key codes
     */
    public static Set<Integer> getKeysPressed() {
        return new HashSet<>(keysPressed);  // Return copy for safety
    }
    
    /**
     * Check if any movement key is pressed
     * 
     * @return true if LEFT/RIGHT/A/D is pressed
     */
    public static boolean isMoving() {
        return isMoveLeftPressed() || isMoveRightPressed();
    }
}

// ═══════════════════════════════════════════════════════════════════════════════════
// PLACEHOLDER INTERFACES - Defined in their respective packages
// ═══════════════════════════════════════════════════════════════════════════════════

/**
 * PlayerEntity - Represents the player in the game world
 * Full implementation in 4_Entities/PlayerBase.java
 */
interface PlayerEntity {
    float velocityX = 0;
    float velocityY = 0;
    boolean isGrounded = true;
    
    void setFacingDirection(boolean facingRight);
    void shootWeapon();
    boolean canDash();
    void dash(int direction);
    void interactWithNearby();
}
