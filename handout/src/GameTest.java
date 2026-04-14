/*
* COMPLETE PLAYER STATE MACHINE TEST - NO LEVEL DEPENDENCIES
* Pure focus on PlayerBase state machine mechanics
*/
import game2D.GameCore;
import controllers.GameState;
import controllers.HUDPanel;
import controllers.TopBarPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameTest extends GameCore {
    private PlayerBase player = null;
    private List<Projectile> activeProjectiles = new ArrayList<Projectile>();
    private GameState gameState;
    private boolean isPaused = false;
    private float cameraX = 0.0f;
    private float cameraY = 0.0f;
    
    private TopBarPanel topBarPanel;
    private HUDPanel hudPanel;

    public static void main(String[] args) {
        GameTest game = new GameTest();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        game.run(false, dim.width, dim.height);
    }

    public GameTest() {
        this.setTitle("=== COMPLETE PLAYER STATE MACHINE TEST ===");
        this.setDefaultCloseOperation(3);
        this.setResizable(true);
        this.initialize();
    }

    private void initialize() {
        try {
            // Init game state
            this.gameState = new GameState();
            this.gameState.health = 100;
            this.gameState.maxHealth = 100;
            
            // Init player - THIS IS THE FOCUS
            this.player = new PlayerBase(PlayerBase.CharacterType.BIKER, 200.0f, 400.0f);
            System.out.println("[\u2713] Player created with complete state machine");
            System.out.println("  - IDLE/WALK states with animation");
            System.out.println("  - JUMP state with gravity physics");
            System.out.println("  - Direct KeyListener input integration");
            System.out.println("  - Per-state animation timing");
            System.out.println("");
            System.out.println("Controls:");
            System.out.println("  LEFT/A  - Move left");
            System.out.println("  RIGHT/D - Move right");
            System.out.println("  SPACE   - Jump");
            System.out.println("  SHIFT   - Dash");
            System.out.println("  CTRL    - Attack");
            System.out.println("  ESC     - Pause");
            System.out.println("");
            
            // Init UI
            this.topBarPanel = new TopBarPanel(this.getWidth() > 0 ? this.getWidth() : 1080);
            this.topBarPanel.loadAssets();
            this.hudPanel = new HUDPanel(this.getWidth() > 0 ? this.getWidth() : 1080, this.getHeight() > 0 ? this.getHeight() : 720);
            this.hudPanel.loadAssets();
            
            // Register keyboard
            this.addKeyListener(new java.awt.event.KeyListener() {
                @Override
                public void keyTyped(java.awt.event.KeyEvent e) {}
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        GameTest.this.isPaused = !GameTest.this.isPaused;
                    }
                    if (GameTest.this.player != null) {
                        PlayerBase.setKeyPressed(e.getKeyCode(), true);
                    }
                }
                @Override
                public void keyReleased(java.awt.event.KeyEvent e) {
                    if (GameTest.this.player != null) {
                        PlayerBase.setKeyPressed(e.getKeyCode(), false);
                    }
                }
            });
            System.out.println("[\u2713] Test game ready - press keys to trigger state transitions");
        } catch (Exception e) {
            System.err.println("[\u2717] Init error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(long deltaTime) {
        if (this.isPaused || this.player == null) return;
        
        // Update player state machine and physics
        this.player.update(deltaTime);
        
        // Update camera
        this.cameraX = Math.max(0, this.player.getX() - 400);
        this.cameraY = Math.max(0, this.player.getY() - 300);
        
        // Update projectiles
        for (int i = this.activeProjectiles.size() - 1; i >= 0; i--) {
            Projectile proj = this.activeProjectiles.get(i);
            proj.update(deltaTime);
            if (!proj.isAlive()) {
                this.activeProjectiles.remove(i);
            }
        }
        
        // Sync health
        if (this.gameState != null) {
            this.gameState.health = this.player.getHealth();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (g == null) return;
        
        // Background
        g.setColor(new java.awt.Color(50, 50, 60));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        // Grid lines for reference
        g.setColor(new java.awt.Color(100, 100, 120));
        for (int x = (int)(this.cameraX); x < this.cameraX + this.getWidth(); x += 50) {
            g.drawLine(x - (int)this.cameraX, 0, x - (int)this.cameraX, this.getHeight());
        }
        for (int y = (int)(this.cameraY); y < this.cameraY + this.getHeight(); y += 50) {
            g.drawLine(0, y - (int)this.cameraY, this.getWidth(), y - (int)this.cameraY);
        }
        
        // Platform
        g.setColor(new java.awt.Color(100, 80, 60));
        g.fillRect((int)(200 - this.cameraX), (int)(480 - this.cameraY), 800, 40);
        
        // Render player
        if (this.player != null) {
            this.player.render(g, (int)this.cameraX, (int)this.cameraY);
        }
        
        // Render projectiles
        for (Projectile proj : this.activeProjectiles) {
            proj.render(g, (int)this.cameraX, (int)this.cameraY);
        }
        
        // Render UI
        if (this.topBarPanel != null) this.topBarPanel.render(g);
        if (this.hudPanel != null) {
            this.hudPanel.render(g);
            this.hudPanel.updateWithGameState(g, this.gameState);
        }
        
        // Pause overlay
        if (this.isPaused) {
            g.setColor(new java.awt.Color(0, 0, 0, 180));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(java.awt.Color.WHITE);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 60));
            g.drawString("PAUSED", this.getWidth()/2 - 150, this.getHeight()/2 - 30);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 24));
            g.drawString("Press ESC to resume", this.getWidth()/2 - 100, this.getHeight()/2 + 40);
        }
    }
}
