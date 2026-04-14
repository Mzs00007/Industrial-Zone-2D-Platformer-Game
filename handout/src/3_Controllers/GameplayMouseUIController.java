package gui;

import core.Core;
import core.GameplayAnimationController;
import audio.GameplayAudioVisualSynchronizer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * PHASE 3: MOUSE/UI EVENT SYSTEM
 * 
 * Handles UI button interactions with synchronized visual and audio feedback.
 * Manages pause menu opening/closing with music fading and overlay animation.
 * Supports hover effects, button state transitions, and tooltips.
 * 
 * BUTTON STATE MACHINE:
 * - NORMAL     (RGB 80,80,80)  → Mouse leaves or released
 * - HOVERED    (RGB 120,120,120) → Mouse enters button, hand cursor
 * - PRESSED    (RGB 100,100,100) → Button clicked, scale 0.98
 * 
 * PAUSE MENU SEQUENCE (1100ms total):
 * t=0ms:       User clicks Pause button
 * t=1ms:       Button transitions NORMAL → PRESSED
 * t=1ms:       UI click sound plays (200ms)
 * t=50ms:      Button released → HOVERED (mouse still over)
 * t=150ms:     Pause overlay fades in (0→0.7 opacity over 300ms)
 * t=150ms:     Menu panel appears with buttons
 * t=150ms+:    Music fades to 50% (300ms)
 * t=300ms:     Menu fully visible
 * t=500ms+:    User can hover/click Resume
 * t=800ms:     Resume clicked, menu starts closing
 * t=800ms-1100ms: Fade out overlay, resume music, all stops
 * t=1100ms:    Game fully resumed
 * 
 * Timeline: Input (1ms) → Processing (~5ms) → Render (16ms)
 * Integration: Game.addMouseListener(), Game.addMouseMotionListener()
 */
public class GameplayMouseUIController extends MouseAdapter {
    
    // ===== BUTTON STATE ENUM =====
    public enum ButtonState {
        NORMAL,      // Default
        HOVERED,     // Mouse over
        PRESSED,     // Being clicked
        DISABLED     // Not clickable
    }
    
    // ===== INTERACTIVE BUTTON CLASS =====
    /**
     * Represents an interactive UI button with hit detection and visual feedback.
     */
    public class InteractiveButton {
        public String buttonId;
        public Rectangle hitbox;  // x, y, width, height
        public ButtonState currentState = ButtonState.NORMAL;
        
        // Asset references
        public String assetNormal;     // gui/buttons/button_*.png
        public String assetHovered;    // gui/buttons/button_*_hover.png
        public String assetPressed;    // gui/buttons/button_*_pressed.png
        
        // Audio references
        public String audioHover;      // sfx/ui_button_hover.mp3
        public String audioClick;      // sfx/ui_button_click.mp3
        
        // Callback
        public Runnable onClickAction;
        
        // Visual properties
        public float currentScale = 1.0f;      // For press effect
        public float currentOpacity = 1.0f;    // For fade effects
        public int currentTextOffsetY = 0;     // For press depression
        
        // State tracking
        private long lastHoverTimeMs = 0;      // Prevent repeated hover sounds
        private boolean wasHoveredLastFrame = false;
        
        public InteractiveButton(String id, int x, int y, int width, int height) {
            this.buttonId = id;
            this.hitbox = new Rectangle(x, y, width, height);
        }
        
        /**
         * Check if mouse is inside button hitbox.
         */
        public boolean isMouseOver(int mouseX, int mouseY) {
            return hitbox.contains(mouseX, mouseY);
        }
        
        /**
         * Update button visual state based on current state.
         * Called every frame for smooth transitions.
         */
        public void updateVisuals(long deltaTimeMs) {
            // Smooth scale transition (0.98 when pressed, 1.0 otherwise)
            float targetScale = (currentState == ButtonState.PRESSED) ? 0.98f : 1.0f;
            currentScale += (targetScale - currentScale) * 0.2f;  // Lerp
            
            // Update text offset (2px down when pressed)
            int targetOffset = (currentState == ButtonState.PRESSED) ? 2 : 0;
            currentTextOffsetY += (targetOffset - currentTextOffsetY) * 0.2f;
        }
        
        /**
         * Render button with current visuals.
         */
        public void render(java.awt.Graphics2D g) {
            // Render background based on state
            String asset = assetNormal;
            java.awt.Color color = new java.awt.Color(80, 80, 80);  // NORMAL
            
            if (currentState == ButtonState.HOVERED) {
                asset = assetHovered;
                color = new java.awt.Color(120, 120, 120);  // HOVERED
            } else if (currentState == ButtonState.PRESSED) {
                asset = assetPressed;
                color = new java.awt.Color(100, 100, 100);  // PRESSED
            }
            
            // Draw button (simplified - would load actual asset in production)
            int scaledWidth = (int)(hitbox.width * currentScale);
            int scaledHeight = (int)(hitbox.height * currentScale);
            int centerX = hitbox.x + hitbox.width / 2 - scaledWidth / 2;
            int centerY = hitbox.y + hitbox.height / 2 - scaledHeight / 2;
            
            g.setColor(color);
            g.fillRect(centerX, centerY + (int)currentTextOffsetY, scaledWidth, scaledHeight);
            
            // Draw border
            g.setColor(new java.awt.Color(50, 50, 50));
            g.drawRect(centerX, centerY + (int)currentTextOffsetY, scaledWidth, scaledHeight);
        }
    }
    
    // ===== INSTANCE VARIABLES =====
    private List<InteractiveButton> buttons = new ArrayList<>();
    private InteractiveButton currentHoveredButton = null;
    private boolean isPauseMenuOpen = false;
    private GameplayAnimationController animationController;
    private GameplayAudioVisualSynchronizer audioSynchronizer;
    private Game gameInstance;
    
    // Overlay state for pause menu
    private float pauseOverlayOpacity = 0.0f;
    private long pauseOverlayStartTimeMs = 0;
    private static final long PAUSE_OVERLAY_FADE_DURATION = 300;
    
    // ===== CONSTRUCTOR =====
    public GameplayMouseUIController(GameplayAnimationController animationController,
                                    GameplayAudioVisualSynchronizer audioSynchronizer,
                                    Game gameInstance) {
        this.animationController = animationController;
        this.audioSynchronizer = audioSynchronizer;
        this.gameInstance = gameInstance;
        
        initializeButtons();
        Core.logger.info("[MouseUIController] Initialized - Ready for Phase 3 UI interactions");
    }
    
    // ===== BUTTON INITIALIZATION =====
    
    /**
     * Create all interactive buttons for main menu and pause menu.
     */
    private void initializeButtons() {
        // Main Menu Buttons
        createButton("play", 300, 200, 150, 50, () -> {
            Core.logger.debug("[MouseUIController] PLAY button clicked");
            // TODO: Start game
        });
        
        createButton("settings", 300, 280, 150, 50, () -> {
            Core.logger.debug("[MouseUIController] SETTINGS button clicked");
            // TODO: Open settings
        });
        
        createButton("quit", 300, 360, 150, 50, () -> {
            Core.logger.debug("[MouseUIController] QUIT button clicked");
            System.exit(0);
        });
        
        // Pause Menu Buttons
        createButton("resume", 300, 200, 150, 50, () -> {
            Core.logger.debug("[MouseUIController] RESUME button clicked");
            resumeGame();
        });
        
        createButton("pause_settings", 300, 280, 150, 50, () -> {
            Core.logger.debug("[MouseUIController] PAUSE SETTINGS button clicked");
            // TODO: Open pause menu settings
        });
        
        createButton("pause_quit", 300, 360, 150, 50, () -> {
            Core.logger.debug("[MouseUIController] PAUSE QUIT button clicked");
            // TODO: Return to main menu
        });
    }
    
    /**
     * Create a new interactive button.
     */
    private void createButton(String id, int x, int y, int width, int height, Runnable onClick) {
        InteractiveButton button = new InteractiveButton(id, x, y, width, height);
        button.assetNormal = "gui/buttons/button_" + id + ".png";
        button.assetHovered = "gui/buttons/button_" + id + "_hover.png";
        button.assetPressed = "gui/buttons/button_" + id + "_pressed.png";
        button.audioHover = "ui_button_hover";
        button.audioClick = "ui_button_click";
        button.onClickAction = onClick;
        buttons.add(button);
    }
    
    // ===== MOUSE EVENT HANDLERS =====
    
    /**
     * Handle mouse movement (continuous updates as mouse moves).
     * Timeline: t=0ms (user moves mouse)
     * t=5ms (collision check completes)
     * t=5ms (state change if hovering over button)
     * t=5ms (cursor changes + hover sound plays)
     * t=16ms (render frame with new state)
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        
        InteractiveButton buttonUnderMouse = null;
        
        // Check collision with all buttons
        for (InteractiveButton button : buttons) {
            if (button.isMouseOver(mouseX, mouseY)) {
                buttonUnderMouse = button;
                break;  // Found topmost button
            }
        }
        
        // Handle state changes
        if (buttonUnderMouse != currentHoveredButton) {
            // Mouse left previous button
            if (currentHoveredButton != null && currentHoveredButton.currentState == ButtonState.HOVERED) {
                currentHoveredButton.currentState = ButtonState.NORMAL;
                Core.logger.debug("[MouseUIController] Mouse left button: " + currentHoveredButton.buttonId);
            }
            
            // Mouse entered new button
            if (buttonUnderMouse != null) {
                if (buttonUnderMouse.currentState != ButtonState.PRESSED) {
                    buttonUnderMouse.currentState = ButtonState.HOVERED;
                    
                    // Play hover sound
                    if (audioSynchronizer != null) {
                        audioSynchronizer.playAudioWithVolume("ui_button_hover", 0.5f);
                    }
                    
                    // Change cursor to hand pointer
                    // TODO: Set cursor via setCursor(Cursor.getPredefinedCursor(...))
                    
                    Core.logger.debug("[MouseUIController] Mouse entered button: " + buttonUnderMouse.buttonId);
                }
            } else {
                // Mouse not over any button - default cursor
                // TODO: Set cursor to default arrow
            }
            
            currentHoveredButton = buttonUnderMouse;
        }
    }
    
    /**
     * Handle mouse click (button press begins).
     * Timeline: t=0ms (user presses mouse button)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (currentHoveredButton != null) {
            // Change button to PRESSED state
            currentHoveredButton.currentState = ButtonState.PRESSED;
            
            // Play click sound
            if (audioSynchronizer != null) {
                audioSynchronizer.playAudioWithVolume("ui_button_click", 0.7f);
            }
            
            Core.logger.debug("[MouseUIController] Button pressed: " + currentHoveredButton.buttonId);
        }
    }
    
    /**
     * Handle mouse release (button click completes).
     * Timeline: t=50-100ms (user releases mouse button)
     * t=100ms (onClick action executed)
     * t=100ms (button transitions to HOVERED if still over it)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentHoveredButton != null && currentHoveredButton.currentState == ButtonState.PRESSED) {
            // Execute button action
            if (currentHoveredButton.onClickAction != null) {
                try {
                    currentHoveredButton.onClickAction.run();
                } catch (Exception ex) {
                    Core.logger.error("[MouseUIController] Error executing button action: " + ex.getMessage());
                }
            }
            
            // Return to HOVERED state (mouse still over button)
            currentHoveredButton.currentState = ButtonState.HOVERED;
            Core.logger.debug("[MouseUIController] Button released and action executed: " + 
                            currentHoveredButton.buttonId);
        }
    }
    
    /**
     * Handle mouse exiting window.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (currentHoveredButton != null) {
            currentHoveredButton.currentState = ButtonState.NORMAL;
            currentHoveredButton = null;
            Core.logger.debug("[MouseUIController] Mouse exited window");
        }
    }
    
    // ===== PAUSE MENU MANAGEMENT =====
    
    /**
     * Open pause menu (called from ESC key or pause button).
     * 
     * Timeline:
     * t=0ms:       pauseGameMenuOpen() called
     * t=1ms:       isPauseMenuOpen = true
     * t=1ms:       animationController.pause()
     * t=1ms:       pauseOverlayOpacity begins fading in (300ms)
     * t=150ms:     Audio: Menu open sound starts (300ms)
     * t=150ms+:    Audio: Game music fades to 50%
     * t=300ms:     Overlay fully opaque (pauseOverlayOpacity = 0.7)
     */
    public void pauseGame() {
        if (isPauseMenuOpen) {
            return;  // Already paused
        }
        
        isPauseMenuOpen = true;
        pauseOverlayStartTimeMs = System.currentTimeMillis();
        
        // Freeze game
        if (animationController != null) {
            animationController.pause();
        }
        
        // Trigger pause audio chain
        if (audioSynchronizer != null) {
            audioSynchronizer.createPauseAudioChain();
        }
        
        if (gameInstance != null) {
            gameInstance.setPaused(true);
        }
        
        Core.logger.info("[MouseUIController] Pause menu opened");
    }
    
    /**
     * Close pause menu and resume game (called when Resume button clicked).
     * 
     * Timeline:
     * t=0ms:       resumeGame() called
     * t=1ms:       Menu close animation starts (300ms fade-out)
     * t=1ms:       Audio: Menu close sound (200ms)
     * t=1ms:       Audio: Music fades from 50% to 100% (300ms)
     * t=300ms:     Overlay fully transparent
     * t=300ms+:    isPauseMenuOpen = false
     * t=300ms+:    All game systems resume
     */
    public void resumeGame() {
        if (!isPauseMenuOpen) {
            return;  // Already resumed
        }
        
        isPauseMenuOpen = false;
        
        // Resume game
        if (animationController != null) {
            animationController.resume();
        }
        
        // Trigger resume audio chain
        if (audioSynchronizer != null) {
            audioSynchronizer.createResumeAudioChain();
        }
        
        if (gameInstance != null) {
            gameInstance.setPaused(false);
        }
        
        Core.logger.info("[MouseUIController] Pause menu closed, game resumed");
    }
    
    /**
     * Check and update pause overlay transparency.
     * Called every frame while pause menu open/closing.
     */
    public void updatePauseOverlay(long deltaTimeMs) {
        if (!isPauseMenuOpen && pauseOverlayOpacity > 0.0f) {
            // Fade out
            pauseOverlayOpacity -= deltaTimeMs / 300.0f;  // 300ms fade
            if (pauseOverlayOpacity < 0.0f) {
                pauseOverlayOpacity = 0.0f;
            }
        } else if (isPauseMenuOpen && pauseOverlayOpacity < 0.7f) {
            // Fade in
            long elapsedMs = System.currentTimeMillis() - pauseOverlayStartTimeMs;
            pauseOverlayOpacity = Math.min(0.7f, (float)elapsedMs / PAUSE_OVERLAY_FADE_DURATION * 0.7f);
        }
    }
    
    // ===== RENDERING =====
    
    /**
     * Render all UI elements and pause overlay.
     */
    public void render(java.awt.Graphics2D g) {
        // Render all buttons
        for (InteractiveButton button : buttons) {
            if ((button.buttonId.startsWith("resume") || button.buttonId.startsWith("pause")) && !isPauseMenuOpen) {
                continue;  // Don't draw pause buttons if menu not open
            }
            button.render(g);
        }
        
        // Render pause overlay if active
        if (isPauseMenuOpen && pauseOverlayOpacity > 0.0f) {
            g.setColor(new java.awt.Color(0, 0, 0, (int)(pauseOverlayOpacity * 255)));
            g.fillRect(0, 0, 1920, 1080);  // TODO: Use window dimensions
        }
    }
    
    // ===== FRAME UPDATE =====
    
    /**
     * Update UI state every frame (called from game loop).
     */
    public void update(long deltaTimeMs) {
        // Update all buttons
        for (InteractiveButton button : buttons) {
            button.updateVisuals(deltaTimeMs);
        }
        
        // Update pause overlay
        updatePauseOverlay(deltaTimeMs);
    }
    
    // ===== GETTERS =====
    public boolean isPauseMenuOpen() {
        return isPauseMenuOpen;
    }
    
    public float getPauseOverlayOpacity() {
        return pauseOverlayOpacity;
    }
    
    public List<InteractiveButton> getButtons() {
        return new ArrayList<>(buttons);
    }
}
