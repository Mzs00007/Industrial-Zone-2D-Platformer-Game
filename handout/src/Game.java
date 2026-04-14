/*
 * INDUSTRIAL ZONE PLATFORMER - MAIN GAME CLASS
 * Extends GameCore with proper game loop implementation
 * Handles player movement, camera, projectiles, and UI rendering
 */

import animation.AnimationAndSpriteLoader;
import framework.GameLoopManager;
import game2D.GameCore;
import game2D.TileMap;
import controllers.GameState;
import controllers.HUDPanel;
import controllers.TopBarPanel;
import physics.PhysicsUpdateSystem;
import physics.CollisionDetector;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Game extends GameCore {
    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    
    // Game state
    private int currentLevel = 1;
    private TileMap level1TileMap;
    private TileMap level2TileMap;
    private float cameraX = 0.0f;
    private float cameraY = 0.0f;
    private boolean isLevel1Active = true;
    
    // Game entities
    private PlayerBase player = null;
    private List<Projectile> activeProjectiles = new ArrayList<Projectile>();
    private List<Enemy> activeEnemies = new ArrayList<Enemy>();
    private GameState gameState;
    private boolean isPaused = false;
    
    // UI panels
    private TopBarPanel topBarPanel;
    private HUDPanel hudPanel;
    
    // Game loop timing
    private GameLoopManager gameLoopManager;
    private long lastFrameTime = System.currentTimeMillis();

    public static void main(String[] args) {
        Game game = new Game();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = 1200;
        int screenHeight = 800;
        game.run(false, screenWidth, screenHeight);
    }

    public Game() {
        this.setTitle("[CSCU9N6] Industrial Zone Platformer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        
        // Initialize game loop timing system
        this.gameLoopManager = new GameLoopManager();
        
        this.initializeLevels();
        this.initializeGUI();
        
        System.out.println("[INIT] Game initialized successfully");
    }

    private void initializeLevels() {
        try {
            this.level1TileMap = new TileMap();
            this.level2TileMap = new TileMap();
            Level1.initialize(this.level1TileMap);
            Level2.initialize(this.level2TileMap);
            System.out.println("[INIT] Levels loaded");
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to initialize levels: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeGUI() {
        try {
            // Initialize game state
            this.gameState = new GameState();
            this.gameState.currentLevel = "LEVEL_1";
            this.gameState.levelName = "Industrial Zone";
            this.gameState.health = 100;
            this.gameState.maxHealth = 100;
            
            // Create player
            this.player = new PlayerBase(PlayerBase.CharacterType.BIKER, 200.0f, 400.0f);
            System.out.println("[INIT] Player spawned at (200, 400)");
            
            // Initialize UI panels
            this.topBarPanel = new TopBarPanel(1200);
            this.topBarPanel.loadAssets();
            
            this.hudPanel = new HUDPanel(1200, 800);
            this.hudPanel.loadAssets();
            
            // Setup keyboard input
            this.setupKeyboardInput();
            
            System.out.println("[INIT] GUI initialized");
        } catch (Exception e) {
            System.err.println("[ERROR] GUI initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupKeyboardInput() {
        this.addKeyListener(new java.awt.event.KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Game.this.togglePause();
                }
                if (Game.this.player != null) {
                    PlayerBase.setKeyPressed(e.getKeyCode(), true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (Game.this.player != null) {
                    PlayerBase.setKeyPressed(e.getKeyCode(), false);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}
        });
    }

    @Override
    public void update(long deltaTime) {
        if (this.isPaused || this.player == null) {
            return;
        }
        
        // Calculate frame timing
        this.gameLoopManager.calculateDeltaTime();
        float deltaSeconds = this.gameLoopManager.getDeltaSeconds();
        
        // ========== PHASE 1: UPDATE PLAYER INPUT & STATE ==========
        this.player.update(deltaTime);
        
        // ========== PHASE 2: APPLY PHYSICS TO PLAYER ==========
        this.updatePlayerPhysics(deltaSeconds);
        
        // ========== PHASE 3: DETECT COLLISIONS WITH LEVEL ==========
        this.detectPlayerLevelCollisions();
        
        // ========== PHASE 4: UPDATE ENEMIES ==========
        this.updateEnemies(deltaSeconds);
        
        // ========== PHASE 5: UPDATE PROJECTILES ==========
        this.updateProjectiles(deltaSeconds);
        
        // ========== PHASE 6: CHECK PROJECTILE COLLISIONS ==========
        this.detectProjectileCollisions();
        
        // ========== PHASE 7: UPDATE CAMERA FOLLOWING PLAYER ==========
        this.updateCamera();
        
        // ========== PHASE 8: SYNC GAME STATE ==========
        this.synchronizeGameState();
        
        // ========== PHASE 9: UPDATE FPS COUNTER ==========
        this.gameLoopManager.updateFPS();
    }
    
    /**
     * PHASE 2: Apply physics (gravity, velocity) to player
     */
    private void updatePlayerPhysics(float deltaSeconds) {
        if (this.player == null) return;
        
        // Get current player state
        float playerX = this.player.getX();
        float playerY = this.player.getY();
        float velocityX = this.player.getVelocityX();
        float velocityY = this.player.getVelocityY();
        
        // Apply gravity
        velocityY = PhysicsUpdateSystem.applyGravity(velocityY, deltaSeconds);
        
        // Update position based on velocity
        playerX = PhysicsUpdateSystem.updatePosition(playerX, velocityX, deltaSeconds);
        playerY = PhysicsUpdateSystem.updatePosition(playerY, velocityY, deltaSeconds);
        
        // Update player state
        this.player.setPosition(playerX, playerY);
        this.player.setVelocity(velocityX, velocityY);
    }
    
    /**
     * PHASE 3: Detect and resolve player-level collisions
     */
    private void detectPlayerLevelCollisions() {
        if (this.player == null) return;
        
        TileMap tileMap = this.getCurrentTileMap();
        if (tileMap == null) return;
        
        float playerX = this.player.getX();
        float playerY = this.player.getY();
        float playerW = 40;  // Player width estimate
        float playerH = 60;  // Player height estimate
        
        // Check collision with all tiles
        // This is simplified - you'll need actual tile collision data
        // For now, we'll check platform collisions
        
        // Set player as not falling initially, update based on collisions
        boolean isOnGround = false;
        
        // Simple ground collision (placeholder - replace with tile-based logic)
        if (playerY + playerH >= 500) {  // Simple ground level
            playerY = 500 - playerH;
            this.player.setVelocity(this.player.getVelocityX(), 0);
            this.player.setPosition(playerX, playerY);
            isOnGround = true;
        }
        
        this.player.setOnGround(isOnGround);
    }
    
    /**
     * PHASE 4: Update all active enemies
     */
    private void updateEnemies(float deltaSeconds) {
        for (int i = this.activeEnemies.size() - 1; i >= 0; i--) {
            Enemy enemy = this.activeEnemies.get(i);
            
            // Update enemy AI and physics
            enemy.update(deltaSeconds);
            
            // Apply gravity to enemies
            float vx = enemy.getVelocityX();
            float vy = PhysicsUpdateSystem.applyGravity(enemy.getVelocityY(), deltaSeconds);
            
            float ex = PhysicsUpdateSystem.updatePosition(enemy.getX(), vx, deltaSeconds);
            float ey = PhysicsUpdateSystem.updatePosition(enemy.getY(), vy, deltaSeconds);
            
            enemy.setPosition(ex, ey);
            enemy.setVelocity(vx, vy);
            
            // Remove dead enemies
            if (enemy.getHealth() <= 0) {
                this.activeEnemies.remove(i);
            }
        }
    }
    
    /**
     * PHASE 5: Update all projectiles
     */
    private void updateProjectiles(float deltaSeconds) {
        for (int i = this.activeProjectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = this.activeProjectiles.get(i);
            projectile.update(deltaTime);
            
            if (!projectile.isAlive()) {
                this.activeProjectiles.remove(i);
            }
        }
    }
    
    /**
     * PHASE 6: Check projectile collisions with enemies
     */
    private void detectProjectileCollisions() {
        for (int i = this.activeProjectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = this.activeProjectiles.get(i);
            
            for (int j = this.activeEnemies.size() - 1; j >= 0; j--) {
                Enemy enemy = this.activeEnemies.get(j);
                
                if (CollisionDetector.checkAABB(
                    projectile.getX(), projectile.getY(), 10, 10,
                    enemy.getX(), enemy.getY(), 40, 40)) {
                    
                    // Projectile hits enemy
                    enemy.takeDamage(projectile.getDamage());
                    projectile.destroy();
                }
            }
        }
    }
    
    /**
     * PHASE 7: Update camera position to follow player
     */
    private void updateCamera() {
        if (this.player == null) return;
        
        float playerX = this.player.getX();
        float playerY = this.player.getY();
        int screenWidth = this.getWidth() > 0 ? this.getWidth() : 1200;
        int screenHeight = this.getHeight() > 0 ? this.getHeight() : 800;
        
        // Keep player roughly center of screen
        this.cameraX = Math.max(0, playerX - (screenWidth / 3));
        this.cameraY = Math.max(0, playerY - (screenHeight / 2));
    }
    
    /**
     * PHASE 8: Synchronize UI with game state
     */
    private void synchronizeGameState() {
        if (this.gameState == null || this.player == null) return;
        
        this.gameState.health = (int) this.player.getHealth();
        this.gameState.maxHealth = (int) this.player.getMaxHealth();
        this.gameState.currentLevel = this.isLevel1Active ? "LEVEL_1" : "LEVEL_2";
    }
    
    private long deltaTime = 0;  // Add this field to track delta time

    @Override
    public void draw(Graphics2D g) {
        if (g == null) {
            return;
        }
        
        try {
            int screenWidth = this.getWidth() > 0 ? this.getWidth() : 1200;
            int screenHeight = this.getHeight() > 0 ? this.getHeight() : 800;
            
            // Clear screen with dark background
            g.setColor(new java.awt.Color(30, 30, 40));
            g.fillRect(0, 0, screenWidth, screenHeight);
            
            // ========== RENDER LAYER 1: BACKGROUND ==========
            if (this.isLevel1Active && this.level1TileMap != null) {
                this.level1TileMap.render(g, (int) this.cameraX, (int) this.cameraY, screenWidth, screenHeight);
            } else if (this.level2TileMap != null) {
                this.level2TileMap.render(g, (int) this.cameraX, (int) this.cameraY, screenWidth, screenHeight);
            }
            
            // ========== RENDER LAYER 2: ENEMIES ==========
            for (Enemy enemy : this.activeEnemies) {
                enemy.render(g, (int) this.cameraX, (int) this.cameraY);
            }
            
            // ========== RENDER LAYER 3: PLAYER ==========
            if (this.player != null) {
                this.player.render(g, (int) this.cameraX, (int) this.cameraY);
            }
            
            // ========== RENDER LAYER 4: PROJECTILES ==========
            for (Projectile projectile : this.activeProjectiles) {
                projectile.render(g, (int) this.cameraX, (int) this.cameraY);
            }
            
            // ========== RENDER LAYER 5: UI ==========
            if (this.topBarPanel != null) {
                this.topBarPanel.render(g);
            }
            
            if (this.hudPanel != null) {
                this.hudPanel.render(g);
                if (this.gameState != null) {
                    this.hudPanel.updateWithGameState(g, this.gameState);
                }
            }
            
            // ========== RENDER LAYER 6: OVERLAYS ==========
            if (this.isPaused) {
                renderPauseScreen(g, screenWidth, screenHeight);
            }
            
            // Render debug info
            renderDebugInfo(g);
            
        } catch (Exception e) {
            System.err.println("[ERROR] Render error: " + e.getMessage());
        }
    }

    private void renderPauseScreen(Graphics2D g, int screenWidth, int screenHeight) {
        // Semi-transparent overlay
        g.setColor(new java.awt.Color(0, 0, 0, 128));
        g.fillRect(0, 0, screenWidth, screenHeight);
        
        // Pause text
        g.setColor(java.awt.Color.WHITE);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 48));
        String pauseText = "PAUSED";
        int textX = (screenWidth - g.getFontMetrics().stringWidth(pauseText)) / 2;
        int textY = screenHeight / 2;
        g.drawString(pauseText, textX, textY);
        
        // Resume hint
        g.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16));
        String resumeText = "Press ESC to resume";
        int hintX = (screenWidth - g.getFontMetrics().stringWidth(resumeText)) / 2;
        g.drawString(resumeText, hintX, textY + 50);
    }

    private void renderDebugInfo(Graphics2D g) {
        g.setColor(java.awt.Color.YELLOW);
        g.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        
        int fps = this.gameLoopManager.getCurrentFPS();
        int enemies = this.activeEnemies.size();
        int projectiles = this.activeProjectiles.size();
        
        String debugText = String.format("FPS: %d | Level: %d | Enemies: %d | Projectiles: %d", 
            fps, this.currentLevel, enemies, projectiles);
        
        g.drawString(debugText, 10, 20);
        
        if (this.player != null) {
            String playerText = String.format("Player: (%.0f, %.0f) | Vel: (%.1f, %.1f) | Health: %.0f", 
                this.player.getX(), this.player.getY(),
                this.player.getVelocityX(), this.player.getVelocityY(),
                this.player.getHealth());
            g.drawString(playerText, 10, 35);
            
            String camText = String.format("Camera: (%.0f, %.0f) | DeltaTime: %.2fms",
                this.cameraX, this.cameraY, this.gameLoopManager.getDeltaSeconds() * 1000);
            g.drawString(camText, 10, 50);
        }
    }

    private void togglePause() {
        this.isPaused = !this.isPaused;
        System.out.println(this.isPaused ? "[PAUSE]  Game paused" : "[RESUME] Game resumed");
    }

    // Getters for external access
    public TileMap getCurrentTileMap() {
        return this.isLevel1Active ? this.level1TileMap : this.level2TileMap;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public float getCameraX() {
        return this.cameraX;
    }

    public float getCameraY() {
        return this.cameraY;
    }

    public PlayerBase getPlayer() {
        return this.player;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public HUDPanel getHUDPanel() {
        return this.hudPanel;
    }
}

    public TopBarPanel getTopBarPanel() {
        return this.topBarPanel;
    }

    public List<Projectile> getActiveProjectiles() {
        return this.activeProjectiles;
    }

    public void addProjectile(Projectile projectile) {
        this.activeProjectiles.add(projectile);
    }

    public void addEnemy(Enemy enemy) {
        this.activeEnemies.add(enemy);
    }

    public boolean isPaused() {
        return this.isPaused;
    }
    
    public GameLoopManager getGameLoopManager() {
        return this.gameLoopManager;
    }
    
    public List<Enemy> getActiveEnemies() {
        return this.activeEnemies;
    }