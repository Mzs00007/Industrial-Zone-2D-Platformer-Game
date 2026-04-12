/*
 * Decompiled with CFR 0.152.
 */
import animation.AnimationAndSpriteLoader;
import game2D.GameCore;
import game2D.TileMap;
import gui.ButtonPanel;
import gui.GUIAssetManager;
import gui.GameState;
import gui.HUDPanel;
import gui.LeftSidebar;
import gui.MenuInputHandler;
import gui.MouseInputHandler;
import gui.TopBarPanel;
import gui.screens.Phase10TooltipScreen;
import gui.screens.Phase11NotificationScreen;
import gui.screens.Phase12QuestTrackerScreen;
import gui.screens.Phase13MainMenuScreen;
import gui.screens.Phase14PauseMenuScreen;
import gui.screens.Phase15SettingsScreen;
import gui.screens.Phase2CharacterIdleScreen;
import gui.screens.Phase3StatusBarScreen;
import gui.screens.Phase4NumericDisplayScreen;
import gui.screens.Phase5ButtonScreen;
import gui.screens.Phase6DecorationScreen;
import gui.screens.Phase7ItemInventoryScreen;
import gui.screens.Phase8MinimapScreen;
import gui.screens.Phase9DialogueScreen;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Game
extends GameCore {
    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private int currentLevel = 1;
    private TileMap level1TileMap;
    private TileMap level2TileMap;
    private AnimationAndSpriteLoader.ParallaxSystem level1Parallax;
    private AnimationAndSpriteLoader.ParallaxSystem level2ParallaxDay;
    private AnimationAndSpriteLoader.ParallaxSystem level2ParallaxNight;
    private float cameraX = 0.0f;
    private float cameraY = 0.0f;
    private boolean isLevel1Active = true;
    private GameState gameState;
    private TopBarPanel topBarPanel;
    private HUDPanel hudPanel;
    private LeftSidebar leftSidebar;
    private ButtonPanel buttonPanel;
    private MouseInputHandler mouseInputHandler;
    private Phase2CharacterIdleScreen characterIdleScreen;
    private Phase3StatusBarScreen statusBarScreen;
    private Phase4NumericDisplayScreen numericDisplayScreen;
    private Phase5ButtonScreen buttonScreen;
    private Phase6DecorationScreen decorationScreen;
    private Phase7ItemInventoryScreen itemInventoryScreen;
    private Phase8MinimapScreen minimapScreen;
    private Phase9DialogueScreen dialogueScreen;
    private Phase10TooltipScreen tooltipScreen;
    private Phase11NotificationScreen notificationScreen;
    private Phase12QuestTrackerScreen questTrackerScreen;
    private Phase13MainMenuScreen mainMenuScreen;
    private Phase14PauseMenuScreen pauseMenuScreen;
    private Phase15SettingsScreen settingsScreen;
    private MenuInputHandler menuInputHandler;
    private BufferedImage backgroundBlack;
    private BufferedImage hudPanelImage;

    public static void main(String[] stringArray) {
        Game game = new Game();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int n = dimension.width;
        int n2 = dimension.height;
        game.run(false, n, n2);
    }

    public Game() {
        this.setTitle("CSCU9N6 Industrial Zone Platformer - Option A Architecture");
        this.setDefaultCloseOperation(3);
        this.setResizable(true);
        this.initializeLevels();
        this.initializeParallaxSystems();
        this.initializeRasterAssets();
        this.initializeGUI();
        this.initializePhase2GUI();
        this.initializeScreenBasedGUI();
    }

    private void initializeGUI() {
        try {
            this.gameState = new GameState();
            this.gameState.currentLevel = "LEVEL_1";
            this.gameState.levelName = "INDUSTRIAL ZONE";
            this.gameState.health = 100;
            this.gameState.maxHealth = 100;
            this.gameState.energy = 80;
            this.gameState.maxEnergy = 100;
            this.gameState.armor = 50;
            this.gameState.ammo = 3;
            this.gameState.ammoMax = 12;
            this.topBarPanel = new TopBarPanel(this.getWidth() > 0 ? this.getWidth() : 1080);
            this.topBarPanel.loadAssets();
            this.hudPanel = new HUDPanel(this.getWidth() > 0 ? this.getWidth() : 1080, this.getHeight() > 0 ? this.getHeight() : 720);
            this.hudPanel.loadAssets();
            System.out.println("[\u2713] GUI components initialized successfully");
        }
        catch (Exception exception) {
            System.out.println("[\u2717] Error initializing GUI: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void initializePhase2GUI() {
        try {
            int n = this.getWidth() > 0 ? this.getWidth() : 1080;
            int n2 = this.getHeight() > 0 ? this.getHeight() : 720;
            GUIAssetManager gUIAssetManager = GUIAssetManager.getInstance();
            this.leftSidebar = new LeftSidebar(n, n2, gUIAssetManager);
            this.leftSidebar.loadAssets();
            this.buttonPanel = new ButtonPanel(n, n2, gUIAssetManager);
            this.buttonPanel.loadAssets();
            this.mouseInputHandler = new MouseInputHandler(this.buttonPanel);
            this.addMouseListener(this.mouseInputHandler);
            this.addMouseMotionListener(this.mouseInputHandler);
            System.out.println("[\u2713] Phase 2 GUI components initialized successfully");
        }
        catch (Exception exception) {
            System.out.println("[\u2717] Error initializing Phase 2 GUI: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void initializeScreenBasedGUI() {
        try {
            this.characterIdleScreen = new Phase2CharacterIdleScreen();
            System.out.println("[\u2713] Phase 2 Character Idle Screen initialized");
            this.statusBarScreen = new Phase3StatusBarScreen();
            this.statusBarScreen.setHealthPercent(100);
            this.statusBarScreen.setEnergyPercent(80);
            System.out.println("[\u2713] Phase 3 Status Bar Screen initialized");
            this.numericDisplayScreen = new Phase4NumericDisplayScreen();
            System.out.println("[\u2713] Phase 4 Numeric Display Screen initialized");
            this.buttonScreen = new Phase5ButtonScreen();
            System.out.println("[\u2713] Phase 5 Button Screen initialized");
            this.decorationScreen = new Phase6DecorationScreen();
            System.out.println("[\u2713] Phase 6 Decoration Screen initialized");
            this.itemInventoryScreen = new Phase7ItemInventoryScreen();
            System.out.println("[\u2713] Phase 7 Item Inventory Screen initialized");
            this.minimapScreen = new Phase8MinimapScreen();
            System.out.println("[\u2713] Phase 8 Minimap Screen initialized");
            this.dialogueScreen = new Phase9DialogueScreen();
            System.out.println("[\u2713] Phase 9 Dialogue Screen initialized");
            this.tooltipScreen = new Phase10TooltipScreen();
            System.out.println("[\u2713] Phase 10 Tooltip Screen initialized");
            this.notificationScreen = new Phase11NotificationScreen();
            System.out.println("[\u2713] Phase 11 Notification Screen initialized");
            this.questTrackerScreen = new Phase12QuestTrackerScreen();
            System.out.println("[\u2713] Phase 12 Quest Tracker Screen initialized");
            this.mainMenuScreen = new Phase13MainMenuScreen();
            System.out.println("[\u2713] Phase 13 Main Menu Screen initialized");
            this.pauseMenuScreen = new Phase14PauseMenuScreen();
            System.out.println("[\u2713] Phase 14 Pause Menu Screen initialized");
            this.settingsScreen = new Phase15SettingsScreen();
            System.out.println("[\u2713] Phase 15 Settings Screen initialized");
            this.menuInputHandler = new MenuInputHandler(this.mainMenuScreen, this.pauseMenuScreen, this.settingsScreen);
            this.menuInputHandler.setActionCallback(new MenuInputHandler.MenuActionCallback(){

                @Override
                public void onMainMenuAction(Phase13MainMenuScreen.MenuAction menuAction) {
                    Game.this.handleMainMenuAction(menuAction);
                }

                @Override
                public void onPauseMenuAction(Phase14PauseMenuScreen.PauseAction pauseAction) {
                    Game.this.handlePauseMenuAction(pauseAction);
                }

                @Override
                public void onSettingsChanged(String string, Object object) {
                    System.out.println("[Settings] " + string + " = " + String.valueOf(object));
                }

                @Override
                public void onMenuStateChanged(MenuInputHandler.MenuState menuState, MenuInputHandler.MenuState menuState2) {
                    System.out.println("[MenuState] " + String.valueOf((Object)menuState) + " \u2192 " + String.valueOf((Object)menuState2));
                }
            });
            this.addKeyListener(this.menuInputHandler);
            System.out.println("[\u2713] MenuInputHandler initialized and registered as KeyListener");
            System.out.println("[\u2713] Screen-based GUI components (Phases 2-15) - Complete menu and UI system initialized successfully");
        }
        catch (Exception exception) {
            System.out.println("[\u2717] Error initializing screen-based GUI: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void initializeLevels() {
        System.out.println("[\u2713] Initializing Level 1...");
        this.level1TileMap = new TileMap();
        Level1.initialize(this.level1TileMap);
        System.out.println("[\u2713] Level 1 initialized");
        System.out.println("[\u2713] Initializing Level 2...");
        this.level2TileMap = new TileMap();
        Level2.initialize(this.level2TileMap);
        System.out.println("[\u2713] Level 2 initialized");
    }

    private void initializeParallaxSystems() {
        System.out.println("[\u2713] Loading Level 1 parallax system...");
        this.level1Parallax = AnimationAndSpriteLoader.createLevel1ParallaxSystem();
        System.out.println("[\u2713] Level 1 parallax: " + this.level1Parallax.getLayerCount() + " layers");
        System.out.println("[\u2713] Loading Level 2 Day parallax system...");
        this.level2ParallaxDay = AnimationAndSpriteLoader.createLevel2ParallaxSystemDay();
        System.out.println("[\u2713] Level 2 Day parallax: " + this.level2ParallaxDay.getLayerCount() + " layers");
        System.out.println("[\u2713] Loading Level 2 Night parallax system...");
        this.level2ParallaxNight = AnimationAndSpriteLoader.createLevel2ParallaxSystemNight();
        System.out.println("[\u2713] Level 2 Night parallax: " + this.level2ParallaxNight.getLayerCount() + " layers");
        System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        System.out.println("GAME READY - Press ESC to switch levels, ARROW KEYS to move");
        System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
    }

    @Override
    public void update(long l) {
        this.updateCamera(l);
        if (this.isLevel1Active && this.level1Parallax != null) {
            this.level1Parallax.updateCamera(this.cameraX);
        } else if (!this.isLevel1Active && this.level2ParallaxDay != null) {
            this.level2ParallaxDay.updateCamera(this.cameraX);
        }
        this.updateGUIComponents(l);
    }

    private void updateGUIComponents(long l) {
        if (this.gameState == null) {
            return;
        }
        this.gameState.totalElapsedSeconds += (int)(l / 1000L);
        this.gameState.timeRemainingSeconds = Math.max(0, this.gameState.timeRemainingSeconds - (int)(l / 1000L));
        if (this.topBarPanel != null) {
            this.topBarPanel.update(l, this.gameState);
        }
        if (this.hudPanel != null) {
            this.hudPanel.update(l, this.gameState);
        }
        if (this.leftSidebar != null) {
            this.leftSidebar.update(l, this.gameState);
        }
        if (this.buttonPanel != null) {
            this.buttonPanel.update(l, this.gameState);
        }
        float f = (float)l / 1000.0f;
        if (this.characterIdleScreen != null) {
            this.characterIdleScreen.update(f);
        }
        if (this.statusBarScreen != null) {
            this.statusBarScreen.setHealthPercent(this.gameState.health);
            this.statusBarScreen.setEnergyPercent(this.gameState.energy);
            this.statusBarScreen.update(f);
        }
        if (this.numericDisplayScreen != null) {
            this.numericDisplayScreen.update(f);
        }
        if (this.buttonScreen != null) {
            this.buttonScreen.update(f);
        }
        if (this.decorationScreen != null) {
            this.decorationScreen.update(f);
        }
        if (this.itemInventoryScreen != null) {
            this.itemInventoryScreen.update(f);
        }
        if (this.minimapScreen != null) {
            this.minimapScreen.update(f);
        }
        if (this.dialogueScreen != null) {
            this.dialogueScreen.update(f);
        }
        if (this.tooltipScreen != null) {
            this.tooltipScreen.update(f);
        }
        if (this.notificationScreen != null) {
            this.notificationScreen.update(f);
        }
        if (this.questTrackerScreen != null) {
            this.questTrackerScreen.update(f);
        }
        if (this.mainMenuScreen != null) {
            this.mainMenuScreen.update(f);
        }
        if (this.pauseMenuScreen != null) {
            this.pauseMenuScreen.update(f);
        }
        if (this.settingsScreen != null) {
            this.settingsScreen.update(f);
        }
    }

    private void updateCamera(long l) {
        float f;
        float f2 = 200.0f;
        float f3 = (float)l / 1000.0f;
        float f4 = f2 * f3;
        this.cameraX += f4;
        if (this.cameraX < 0.0f) {
            this.cameraX = 0.0f;
        }
        if (this.cameraX > (f = (float)(22400 - this.getWidth()))) {
            this.cameraX = f;
        }
    }

    private void initializeRasterAssets() {
        this.backgroundBlack = new BufferedImage(32, 32, 1);
        int[] nArray = new int[1024];
        Arrays.fill(nArray, -16777216);
        this.backgroundBlack.setRGB(0, 0, 32, 32, nArray, 0, 32);
        this.hudPanelImage = new BufferedImage(1024, 50, 2);
        int[] nArray2 = new int[51200];
        Arrays.fill(nArray2, -869125582);
        this.hudPanelImage.setRGB(0, 0, 1024, 50, nArray2, 0, 1024);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        BufferedImage bufferedImage;
        this.renderBackgroundRaster(graphics2D);
        this.renderParallaxRaster(graphics2D);
        this.renderHUDRaster(graphics2D);
        if (this.topBarPanel != null) {
            this.topBarPanel.render(graphics2D);
        }
        if (this.hudPanel != null) {
            this.hudPanel.render(graphics2D);
            this.hudPanel.updateWithGameState(graphics2D, this.gameState);
        }
        if (this.leftSidebar != null) {
            this.leftSidebar.render(graphics2D);
        }
        if (this.buttonPanel != null) {
            this.buttonPanel.render(graphics2D);
        }
        if (this.characterIdleScreen != null && (bufferedImage = this.characterIdleScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.statusBarScreen != null && (bufferedImage = this.statusBarScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.numericDisplayScreen != null && (bufferedImage = this.numericDisplayScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.buttonScreen != null && (bufferedImage = this.buttonScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.decorationScreen != null && (bufferedImage = this.decorationScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.itemInventoryScreen != null && (bufferedImage = this.itemInventoryScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.minimapScreen != null && (bufferedImage = this.minimapScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.dialogueScreen != null && (bufferedImage = this.dialogueScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.tooltipScreen != null && (bufferedImage = this.tooltipScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.notificationScreen != null && (bufferedImage = this.notificationScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.questTrackerScreen != null && (bufferedImage = this.questTrackerScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.mainMenuScreen != null && this.mainMenuScreen.isDisplayed() && (bufferedImage = this.mainMenuScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.pauseMenuScreen != null && this.pauseMenuScreen.isPaused() && (bufferedImage = this.pauseMenuScreen.render(this.getWidth(), this.getHeight())) != null) {
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
        }
        if (this.settingsScreen == null || (bufferedImage = this.settingsScreen.render(this.getWidth(), this.getHeight())) != null) {
            // empty if block
        }
    }

    private void renderBackgroundRaster(Graphics2D graphics2D) {
        if (this.backgroundBlack == null) {
            return;
        }
        int n = this.backgroundBlack.getWidth();
        int n2 = this.backgroundBlack.getHeight();
        for (int i = 0; i < this.getHeight(); i += n2) {
            for (int j = 0; j < this.getWidth(); j += n) {
                graphics2D.drawImage((Image)this.backgroundBlack, j, i, null);
            }
        }
    }

    private void renderParallaxRaster(Graphics2D graphics2D) {
        try {
            if (this.isLevel1Active && this.level1Parallax != null) {
                this.level1Parallax.render(graphics2D, this.getWidth(), this.getHeight());
            } else if (!this.isLevel1Active && this.level2ParallaxDay != null) {
                this.level2ParallaxDay.render(graphics2D, this.getWidth(), this.getHeight());
            }
        }
        catch (Exception exception) {
            System.out.println("[ERROR] Parallax render failed: " + exception.getMessage());
        }
    }

    private void renderHUDRaster(Graphics2D graphics2D) {
        if (this.hudPanelImage != null) {
            int n = this.getHeight() - this.hudPanelImage.getHeight();
            graphics2D.drawImage(this.hudPanelImage, 0, n, this.getWidth(), this.hudPanelImage.getHeight(), null);
        }
    }

    private void handleMainMenuAction(Phase13MainMenuScreen.MenuAction menuAction) {
        if (menuAction == null) {
            return;
        }
        switch (menuAction) {
            case NEW_GAME: {
                System.out.println("[GAME] Starting new game...");
                break;
            }
            case CONTINUE: {
                System.out.println("[GAME] Loading saved game...");
                break;
            }
            case SETTINGS: {
                System.out.println("[GAME] Opened settings from main menu");
                break;
            }
            case CREDITS: {
                System.out.println("[GAME] Showing credits...");
                break;
            }
            case EXIT: {
                System.out.println("[GAME] Exiting game...");
                System.exit(0);
            }
        }
    }

    private void handlePauseMenuAction(Phase14PauseMenuScreen.PauseAction pauseAction) {
        if (pauseAction == null) {
            return;
        }
        switch (pauseAction) {
            case RESUME: {
                System.out.println("[GAME] Resuming game...");
                break;
            }
            case SETTINGS: {
                System.out.println("[GAME] Opened settings from pause menu");
                break;
            }
            case HELP: {
                System.out.println("[GAME] Showing help...");
                break;
            }
            case SAVE_EXIT: {
                System.out.println("[GAME] Saving game and exiting...");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 27) {
            this.isLevel1Active = !this.isLevel1Active;
            this.cameraX = 0.0f;
            String string = this.isLevel1Active ? "Level 1" : "Level 2";
            System.out.println("[GAME] Switched to " + string);
        } else {
            super.keyReleased(keyEvent);
        }
    }
}
