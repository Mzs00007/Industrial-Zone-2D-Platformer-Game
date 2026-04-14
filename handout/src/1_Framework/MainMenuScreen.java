/*
 * Decompiled with CFR 0.152.
 */
import gui.FrameTiler;
import gui.GUIAssetManager;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MainMenuScreen {
    private GUIAssetManager assetManager;
    private FrameTiler frameTiler;
    private BufferedImage backgroundTile;
    private BufferedImage menuFrame;
    private BufferedImage buttonFrame;
    private int screenWidth;
    private int screenHeight;
    private static final int MENU_FRAME_WIDTH = 800;
    private static final int MENU_FRAME_HEIGHT = 600;
    private int startButtonX;
    private int startButtonY;
    private static final int START_BUTTON_WIDTH = 200;
    private static final int START_BUTTON_HEIGHT = 60;
    private boolean startButtonHovered = false;
    private Runnable onStartGameClicked;

    public MainMenuScreen(int n, int n2, Runnable runnable) {
        this.screenWidth = n;
        this.screenHeight = n2;
        this.onStartGameClicked = runnable;
        this.assetManager = GUIAssetManager.getInstance();
        this.frameTiler = new FrameTiler();
        int n3 = (n - 800) / 2;
        int n4 = (n2 - 600) / 2;
        this.startButtonX = n3 + 300;
        this.startButtonY = n4 + 270;
        this.loadAssets();
    }

    private void loadAssets() {
        System.out.println("=== LOADING MAIN MENU ASSETS ===");
        System.out.println("Loading background...");
        this.backgroundTile = this.assetManager.getImage("Resources/industrial-zone/1 Tiles/Level1/2 Background_level_1/part1.png");
        if (this.backgroundTile == null) {
            System.out.println("\u26a0 Background tile not found");
        }
        System.out.println("Building menu frame...");
        this.menuFrame = this.frameTiler.buildFrame(800, 600);
        if (this.menuFrame == null) {
            System.out.println("\u26a0 Menu frame assembly failed");
        }
        System.out.println("Building button frame...");
        this.buttonFrame = this.frameTiler.buildPanelFrame(200, 60);
        if (this.buttonFrame == null) {
            System.out.println("\u26a0 Button frame assembly failed");
        }
        System.out.println("\u2713 Main Menu assets loaded");
    }

    public void update(MouseEvent mouseEvent) {
        if (mouseEvent != null) {
            int n = mouseEvent.getX();
            int n2 = mouseEvent.getY();
            this.startButtonHovered = n >= this.startButtonX && n <= this.startButtonX + 200 && n2 >= this.startButtonY && n2 <= this.startButtonY + 60;
        }
    }

    public void handleClick(MouseEvent mouseEvent) {
        if (this.startButtonHovered && this.onStartGameClicked != null) {
            this.onStartGameClicked.run();
        }
    }

    public void render(Graphics2D graphics2D) {
        int n;
        int n2;
        int n3;
        if (this.backgroundTile != null) {
            for (n3 = 0; n3 < this.screenWidth; n3 += this.backgroundTile.getWidth()) {
                for (n2 = 0; n2 < this.screenHeight; n2 += this.backgroundTile.getHeight()) {
                    graphics2D.drawImage((Image)this.backgroundTile, n3, n2, null);
                }
            }
        } else {
            System.err.println("[MainMenuScreen] ERROR: Background tile not loaded");
        }
        n3 = (this.screenWidth - 800) / 2;
        n2 = (this.screenHeight - 600) / 2;
        if (this.menuFrame != null) {
            graphics2D.drawImage((Image)this.menuFrame, n3, n2, null);
        } else {
            System.err.println("[MainMenuScreen] ERROR: Menu frame not assembled");
        }
        BufferedImage bufferedImage = this.assetManager.getImage("Resources/industrial-zone/gui/5 Logo/logo_main.png");
        if (bufferedImage != null) {
            int n4 = n3 + (800 - bufferedImage.getWidth()) / 2;
            n = n2 + 30;
            graphics2D.drawImage((Image)bufferedImage, n4, n, null);
        } else {
            System.err.println("[MainMenuScreen] WARNING: Title logo PNG not found");
        }
        if (this.buttonFrame != null) {
            graphics2D.drawImage((Image)this.buttonFrame, this.startButtonX, this.startButtonY, null);
        } else {
            System.err.println("[MainMenuScreen] ERROR: Button frame not assembled");
        }
        BufferedImage bufferedImage2 = this.assetManager.getImage("Resources/industrial-zone/gui/text/label_startgame.png");
        if (bufferedImage2 != null) {
            n = this.startButtonX + (200 - bufferedImage2.getWidth()) / 2;
            int n5 = this.startButtonY + (60 - bufferedImage2.getHeight()) / 2;
            graphics2D.drawImage((Image)bufferedImage2, n, n5, null);
        } else {
            System.err.println("[MainMenuScreen] WARNING: Button label PNG not found");
        }
    }
}
