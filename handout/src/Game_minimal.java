/*
* MINIMAL GAME.JAVA - PLAYER STATE MACHINE FOCUS
* Pure focus on player mechanics without complex Level/Enemy systems
*/
import animation.AnimationAndSpriteLoader;
import game2D.GameCore;
import game2D.TileMap;
import gui.GameState;
import gui.HUDPanel;
import gui.TopBarPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Game_minimal extends GameCore {
    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private int currentLevel = 1;
    private TileMap level1TileMap;
    private TileMap level2TileMap;
    private float cameraX = 0.0f;
    private float cameraY = 0.0f;
    private boolean isLevel1Active = true;
    
    private PlayerBase player = null;
    private List<Projectile> activeProjectiles = new ArrayList<Projectile>();
    private GameState gameState;
    private boolean isPaused = false;
    
    private TopBarPanel topBarPanel;
    private HUDPanel hudPanel;

    public static void main(String[] stringArray) {
        Game_minimal game = new Game_minimal();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int n = dimension.width;
        int n2 = dimension.height;
        game.run(false, n, n2);
    }

    public Game_minimal() {
        this.setTitle("PlayerBase State Machine Test - Minimal Game");
        this.setDefaultCloseOperation(3);
        this.setResizable(true);
        this.initializeLevels();
        this.initializeGUI();
        System.out.println("[\u2713] Minimal Game initialized");
    }

    private void initializeLevels() {
        this.level1TileMap = new TileMap();
        this.level2TileMap = new TileMap();
        Level1.initialize(this.level1TileMap);
        Level2.initialize(this.level2TileMap);
    }

    private void initializeGUI() {
        try {
            this.gameState = new GameState();
            this.gameState.currentLevel = "LEVEL_1";
            this.gameState.levelName = "TEST";
            this.gameState.health = 100;
            this.gameState.maxHealth = 100;
            
            this.player = new PlayerBase(PlayerBase.CharacterType.BIKER, 200.0f, 400.0f);
            System.out.println("[\u2713] Player initialized at (200, 400)");
            
            this.topBarPanel = new TopBarPanel(this.getWidth() > 0 ? this.getWidth() : 1080);
            this.topBarPanel.loadAssets();
            this.hudPanel = new HUDPanel(this.getWidth() > 0 ? this.getWidth() : 1080, this.getHeight() > 0 ? this.getHeight() : 720);
            this.hudPanel.loadAssets();
            
            // Register keyboard input handler
            this.addKeyListener(new java.awt.event.KeyListener() {
                @Override
                public void keyTyped(java.awt.event.KeyEvent keyEvent) {}
                @Override
                public void keyPressed(java.awt.event.KeyEvent keyEvent) {
                    if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        Game_minimal.this.togglePause();
                    }
                    if (Game_minimal.this.player != null) {
                        PlayerBase.setKeyPressed(keyEvent.getKeyCode(), true);
                    }
                }
                @Override
                public void keyReleased(java.awt.event.KeyEvent keyEvent) {
                    if (Game_minimal.this.player != null) {
                        PlayerBase.setKeyPressed(keyEvent.getKeyCode(), false);
                    }
                }
            });
            System.out.println("[\u2713] GUI initialized");
        } catch (Exception e) {
            System.err.println("[\u2717] Error initializing GUI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(long deltaTime) {
        if (this.isPaused) return;
        if (this.player == null) return;
        
        // Update player - this is where the state machine runs!
        this.player.update(deltaTime);
        
        // Update camera to follow player
        float playerX = this.player.getX();
        float playerY = this.player.getY();
        this.cameraX = Math.max(0, playerX - 400);
        this.cameraY = playerY - 300;
        
        // Update projectiles
        for (int i = this.activeProjectiles.size() - 1; i >= 0; i--) {
            Projectile proj = this.activeProjectiles.get(i);
            proj.update(deltaTime);
            if (!proj.isAlive()) {
                this.activeProjectiles.remove(i);
            }
        }
        
        // Sync player health with game state
        if (this.gameState != null && this.player != null) {
            this.gameState.health = this.player.getHealth();
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        if (graphics2D == null) return;
        
        try {
            // Render level
            // TODO: Implement level rendering
            // if (this.isLevel1Active && this.level1TileMap != null) {
            //     this.level1TileMap.render(graphics2D, (int)this.cameraX, (int)this.cameraY, this.getWidth(), this.getHeight());
            // } else if (this.level2TileMap != null) {
            //     this.level2TileMap.render(graphics2D, (int)this.cameraX, (int)this.cameraY, this.getWidth(), this.getHeight());
            // }
            
            // Render background
            graphics2D.setColor(new java.awt.Color(30, 30, 40));
            graphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
            
            // Render player
            if (this.player != null) {
                this.player.render(graphics2D, (int)this.cameraX, (int)this.cameraY);
            }
            
            // Render projectiles
            for (Projectile proj : this.activeProjectiles) {
                proj.render(graphics2D, (int)this.cameraX, (int)this.cameraY);
            }
            
            // Render GUI
            if (this.topBarPanel != null) this.topBarPanel.render(graphics2D);
            if (this.hudPanel != null) {
                this.hudPanel.render(graphics2D);
                this.hudPanel.updateWithGameState(graphics2D, this.gameState);
            }
            
            // Render pause screen if paused
            if (this.isPaused) {
                graphics2D.setColor(new java.awt.Color(0, 0, 0, 128));
                graphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
                graphics2D.setColor(java.awt.Color.WHITE);
                graphics2D.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 48));
                graphics2D.drawString("PAUSED", this.getWidth() / 2 - 100, this.getHeight() / 2);
            }
        } catch (Exception e) {
            System.err.println("[\u2717] Error in draw: " + e.getMessage());
        }
    }

    private void togglePause() {
        this.isPaused = !this.isPaused;
        System.out.println(this.isPaused ? "[\u23F8] PAUSED" : "[\u25B6] RESUMED");
    }

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

    public TopBarPanel getTopBarPanel() {
        return this.topBarPanel;
    }
}
