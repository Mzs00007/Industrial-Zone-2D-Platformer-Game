/*
 * =============================================================================
 * CSCU9N6 ASSIGNMENT - INDUSTRIAL ZONE PLATFORMER
 * =============================================================================
 * A 2D side-scrolling platformer with:
 *   - 2 levels (Industrial Zone + Power Station)
 *   - Animated player (Biker / Punk / Cyborg sprite sheets)
 *   - Animated drone enemies with patrol AI
 *   - Shooting mechanic with projectiles
 *   - Smooth parallax multi-layer backgrounds
 *   - Camera that follows the player
 *   - Full HUD (health bar, score, timer, enemy count)
 *   - Pause / Game-Over overlays
 *   - Real tile images drawn as platforms
 *
 * Controls:
 *   A / LEFT  - Move left
 *   D / RIGHT - Move right
 *   SPACE     - Jump
 *   SHIFT     - Dash
 *   CTRL      - Shoot
 *   1 / 2     - Switch level
 *   ESC       - Pause / Resume
 *
 * Entry point: Game.main()
 * =============================================================================
 */

import game2D.GameCore;
import game2D.Animation;
import animation.HorizontalSpritesheetLoader;
import entities.*;
import managers.AudioManager;
import rendering.VfxSystem;
import rendering.ParallaxRenderer;
import rendering.HudRenderer;
import rendering.PanelRenderer;
import tiles.TileMap;
import assets.SpriteAsset;
import assets.weapons.WeaponSet1GunAssets;
import assets.weapons.WeaponSet1HandAssets;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Game extends GameCore {

    // =========================================================================
    //  CONSTANTS
    // =========================================================================
    private static final int SCREEN_W = 1500;
    private static final int SCREEN_H = 860;

    // =========================================================================
    //  LEVEL DATA — delegated to Level1 / Level2 via LevelData interface
    // =========================================================================
    private final LevelData level1Data = new Level1();
    private final LevelData level2Data = new Level2();
    private LevelData activeLevel;  // set in startGame() / switchLevel()

    // =========================================================================
    //  GAME STATE
    // =========================================================================
    private int    currentLevel = 1;
    private float  cameraX     = 0f;
    private float  cameraY     = 0f;
    private int    score       = 0;
    private boolean paused     = false;
    private boolean gameOver   = false;
    private long   startTime;

    // =========================================================================
    //  ENTITIES
    // =========================================================================
    private PlayerBase       player;
    private List<Enemy>      enemies     = new ArrayList<>();
    private List<Projectile> projectiles = new ArrayList<>();
    private List<AnimatedObject> animatedObjects = new ArrayList<>();

    // Checkpoint tracking
    private int   lastCheckpointIdx = -1;
    private float checkpointX  = 150f;
    private float checkpointY  = 456f;

    // Weapon pickups — tracks which weapon spawns have been collected
    private boolean[] weaponPickedUp;  // indexed by spawn order in activeLevel.getWeaponSpawns()

    // Screen shake
    private float shakeOffsetX = 0, shakeOffsetY = 0;
    private float shakeIntensity = 0;
    private float shakeTimer = 0;

    // Notifications (top-centre pop-up messages)
    private final List<GameNotification> notifications = new ArrayList<>();

    // Boss encounter tracking (per-level)
    private boolean bossEncounterTriggered = false;
    // Day/night transition notification (Level 2 only) — fires once per game session
    private boolean dayNightNotified = false;

    // =========================================================================
    //  VISUAL ASSETS
    // =========================================================================
    private BufferedImage[] bgLayers1 = new BufferedImage[5];
    private BufferedImage[] bgLayers2 = new BufferedImage[0]; // filled in loadAssets()

    // Level 2 day/night backgrounds (for crossfade transition)
    private BufferedImage[] bgLayers2Day   = new BufferedImage[0];
    private BufferedImage[] bgLayers2Night = new BufferedImage[0];
    private BufferedImage   bgOverlay2     = null;

    // Platform tile images
    private BufferedImage platformTile; // raised platforms
    private BufferedImage groundTile;   // main ground
    private BufferedImage groundTop;    // top edge of ground

    // Smoke VFX animation (plays at enemy positions when hit)
    private Animation smokeAnim;

    // Ladder PNG images (loaded from 3 Objects/ folder)
    private BufferedImage ladderTall, ladderTallAlt, ladderShort;

    // OTF font loaded from Resources
    private Font hudFont;

    // Audio
    private AudioManager audioManager;

    // =========================================================================
    //  SCREEN STATE
    // =========================================================================
    private enum GameScreen {
        SPLASH, MAIN_MENU, CONTROLS, CHARACTER_SELECT,
        LEVEL_SELECT, CREDITS, GAMEPLAY, PAUSE, SETTINGS, GAME_OVER,
        LEVEL_COMPLETE
    }
    private GameScreen currentScreen = GameScreen.SPLASH;
    private GameScreen prevScreen    = GameScreen.MAIN_MENU;

    // Objective / level-complete state
    private boolean levelComplete   = false;
    private float   levelCompleteTimer = 0f;
    private String  objectiveText   = "DEFEAT ALL ENEMIES";

    // Global GUI timer + mouse
    private float guiTime        = 0f;
    private int   mouseX         = 0, mouseY = 0;
    private int   menuHoveredIndex = -1;

    // SPLASH
    private float   splashTimer    = 0f;
    private float   splashAlpha    = 0f;
    private boolean splashFadingOut = false;

    // MAIN MENU
    private int   menuSelectedIndex = 0;
    private float menuAnimTime      = 0f;
    private float menuCamX          = 0f;  // auto-scrolling parallax offset for menu screens
    private static final String[] MENU_LABELS = {
        "PLAY GAME", "CONTROLS", "CREDITS", "EXIT"
    };

    // CHARACTER SELECT
    private int   charSelectIndex    = 1;       // 0=Biker,1=Cyborg,2=Punk
    private long  charAnimAccum      = 0L;
    private int[] charAnimFrame      = {0, 0, 0};
    private BufferedImage[][] charIdleFrames = new BufferedImage[3][4];

    // LEVEL SELECT
    private int   levelSelectIndex = 0;
    private float levelSlideAnim   = 0f;
    private int   levelSlideDir    = 0;

    // CREDITS
    private float   creditsScrollY     = 700f;
    private float   creditsScrollSpeed = 30f;
    private boolean creditsSpeedUp     = false;
    private float   creditsAutoReturn  = -1f;

    // PAUSE
    private int pauseMenuIndex = 0;  // 0=Resume,1=Settings,2=Controls,3=Quit

    // SETTINGS
    private int     settingsIndex    = 0;
    private float   settingsMusicVol = 0.7f;
    private float   settingsSfxVol   = 0.8f;
    private boolean settingsMusicOn  = true;
    private boolean settingsSfxOn    = true;

    // GAME OVER
    private int   gameOverMenuIndex    = 0; // 0=Retry,1=Menu,2=Exit
    private float gameOverAlpha        = 0f;
    private float gameOverTypeTimer    = 0f;
    private int   gameOverCharsShown   = 0;
    private float gameOverScoreTally   = 0f;
    private int   gameOverEnemiesKilled = 0;
    private long  gameOverTime          = 0L;

    // Rendering subsystems — created in initRenderers() once all assets are loaded
    private VfxSystem        vfx;           // particle VFX (sparks + smoke)
    private ParallaxRenderer parallaxGame1; // gameplay level-1 background
    private ParallaxRenderer parallaxGame2; // gameplay level-2 background (day)
    private ParallaxRenderer parallaxGame2Night; // gameplay level-2 background (night)
    private ParallaxRenderer parallaxMenu;  // menu screens background
    private HudRenderer      hudRenderer;   // HUD bars, score, timer
    private PanelRenderer    panelRenderer; // nine-patch panels + buttons
    // cursor image index: 0=default, 1=aim crosshair, 2=adjust, 3=hover
    private int   activeCursorIdx = 0;

    // Tile maps — loaded from maps/level_N/map.txt
    private TileMap tileMap1;  // Level 1 tile map
    private TileMap tileMap2;  // Level 2 tile map

    // =========================================================================
    //  GUI ASSETS
    // =========================================================================
    private static final String GUI_DIR = "Resources/industrial-zone/gui/";

    // Logos
    private BufferedImage logoFull, logoCompact, logoMinimal;
    // Cursors [0=white,1=blue,2=red,3=green]
    private BufferedImage[] cursorImgs = new BufferedImage[4];
    // Health bars [0=100%..6=empty]
    private BufferedImage[] hudBars  = new BufferedImage[7];
    // Energy bars [0=100%..6=empty]
    private BufferedImage[] enerBars = new BufferedImage[7];
    // Buttons
    private BufferedImage[] btnColors = new BufferedImage[10];
    private BufferedImage btnNormal, btnHover, btnPressed;
    // Frames
    private BufferedImage frmCornerTL, frmCornerTR, frmCornerBL, frmCornerBR;
    private BufferedImage frmEdgeTop, frmEdgeBot, frmEdgeLeft, frmEdgeRight;
    private BufferedImage frmFillNavy, frmFillDark, frmDivider;
    // Decors
    private BufferedImage decorGlowBars, decorRibbon, decorCableTwist;
    private BufferedImage decorCableCoil, decorCablePlug;
    // Number digit PNGs [0..9]
    private BufferedImage[] digitImgs = new BufferedImage[10];

    // Skill / HUD icons [hp=crosshair, en=shield, star=score, skull=enemies]
    private BufferedImage iconHp, iconEn, iconStar, iconSkull;

    // =========================================================================
    //  WEAPON ASSETS  — gun sprites for in-hand rendering and HUD slots
    // =========================================================================
    private static final String GUNS_DIR = "Resources/industrial-zone/weapons/1/2 Guns/";
    /** All gun PNGs sorted alphabetically from GUNS_DIR. */
    private BufferedImage[] gunImages = new BufferedImage[0];
    /** Returns the gunImages[] index to use for a given WeaponType. */
    private int gunIndexFor(PlayerBase.WeaponType w) {
        if (w == null || gunImages.length == 0) return 0;
        switch (w) {
            case PISTOL:  return Math.min(0,  gunImages.length - 1); // 01_Pistol_TypeA_Dark
            case SMG:     return Math.min(6,  gunImages.length - 1); // 07_Compact_TypeD_Dark
            case RIFLE:   return Math.min(12, gunImages.length - 1); // 13_Rifle_TypeG_Dark
            case SHOTGUN: return Math.min(10, gunImages.length - 1); // 11_Detail_TypeF_Dark
            default:      return Math.min(w.ordinal(), gunImages.length - 1);
        }
    }

    // =========================================================================
    //  NOTIFICATION SYSTEM — pop-up messages (boss alerts, pickups, etc.)
    // =========================================================================
    private static class GameNotification {
        String text;
        Color color;
        float timer;
        float duration;
        GameNotification(String text, Color color, float duration) {
            this.text = text; this.color = color; this.timer = 0; this.duration = duration;
        }
    }

    private void addNotification(String text, Color color, float duration) {
        notifications.add(new GameNotification(text, color, duration));
    }

    private void triggerShake(float intensity, float duration) {
        shakeIntensity = intensity;
        shakeTimer = duration;
    }

    // =========================================================================
    //  CINEMATIC SCENE SYSTEM — dialog overlays triggered at X positions
    // =========================================================================
    private static class CinematicScene {
        float triggerX;
        String speaker;
        String dialog;
        boolean triggered = false;
        CinematicScene(float triggerX, String speaker, String dialog) {
            this.triggerX = triggerX; this.speaker = speaker; this.dialog = dialog;
        }
    }

    private CinematicScene[] cinematicScenes = new CinematicScene[0];  // loaded per level
    private CinematicScene activeCinematic = null;
    private float cinematicCharTimer = 0;
    private int cinematicCharsShown = 0;
    private boolean cinematicFullText = false;  // true when SPACE pressed to reveal all

    private void loadCinematicScenes() {
        if (currentLevel == 1) {
            cinematicScenes = new CinematicScene[] {
                new CinematicScene(50,    "COMMAND",  "Agent, you've infiltrated the industrial zone. Proceed with caution."),
                new CinematicScene(3200,  "AGENT",    "The factory is getting more dangerous. I can hear machinery ahead."),
                new CinematicScene(6400,  "COMMAND",  "You're getting close to the core. Watch for automated defences."),
                new CinematicScene(9600,  "AGENT",    "Security systems just activated! This doesn't look good."),
                new CinematicScene(12800, "COMMAND",  "Tank approaching your position! Prepare for combat, Agent!"),
            };
        } else {
            cinematicScenes = new CinematicScene[] {
                new CinematicScene(50,    "COMMAND",  "Power station infiltration begins. This facility controls the entire grid."),
                new CinematicScene(3584,  "AGENT",    "Watch the power lines! One wrong step and it's over."),
                new CinematicScene(7168,  "COMMAND",  "Advanced security detected ahead. Switching to tactical frequency."),
                new CinematicScene(10752, "AGENT",    "I'm detecting a large mech signature nearby. Stay sharp."),
                new CinematicScene(14400, "COMMAND",  "Night falls. Visibility reduced. Stay alert, Agent."),
                new CinematicScene(17984, "AGENT",    "Going underground now. No backup possible down here."),
                new CinematicScene(21568, "COMMAND",  "Almost at the final reactor. The last guardian will be waiting."),
                new CinematicScene(26944, "COMMAND",  "Final guardian detected on scanners. This ends now!"),
            };
        }
    }

    // =========================================================================
    //  ENTRY POINT
    // =========================================================================
    public static void main(String[] args) {
        Game game = new Game();
        game.run(false, SCREEN_W, SCREEN_H);
    }

    // =========================================================================
    //  CONSTRUCTOR
    // =========================================================================
    public Game() {
        setTitle("[CSCU9N6] Industrial Zone Platformer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadAssets();
        initRenderers();   // wire up rendering subsystems (VFX, parallax, HUD, panels)

        audioManager = new AudioManager();
        audioManager.initialize();

        // Wire mouse listeners for GUI navigation
        addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY(), e.getButton());
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override public void mouseMoved(MouseEvent e)   { mouseX = e.getX(); mouseY = e.getY(); }
            @Override public void mouseDragged(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
        });
        addMouseWheelListener((MouseWheelEvent e) -> handleWheel(e.getWheelRotation()));

        // Game starts on SPLASH; player is created in startGame()
        currentScreen = GameScreen.SPLASH;
        System.out.println("[Game] Ready. Showing splash screen.");
    }

    // =========================================================================
    //  ASSET LOADING
    // =========================================================================
    private void loadAssets() {
        // --- Level-1 background layers ---
        String[] bg1Files = level1Data.getBgFiles();
        String   bg1Dir   = level1Data.getBgDir();
        if (bg1Files != null) {
            bgLayers1 = new BufferedImage[bg1Files.length];
            for (int i = 0; i < bg1Files.length; i++) {
                bgLayers1[i] = tryLoad(bg1Dir + bg1Files[i]);
            }
        }

        // --- Level-2 backgrounds (day + night sets for crossfade) ---
        String bg2Dir = level2Data.getBgDir();
        String[] dayFiles   = level2Data.getBgDayFiles();
        String[] nightFiles = level2Data.getBgNightFiles();
        String   overlay    = level2Data.getBgOverlay();

        if (dayFiles != null) {
            bgLayers2Day = new BufferedImage[dayFiles.length];
            for (int i = 0; i < dayFiles.length; i++) {
                bgLayers2Day[i] = tryLoad(bg2Dir + dayFiles[i]);
            }
            bgLayers2 = bgLayers2Day; // default to day
        }
        if (nightFiles != null) {
            bgLayers2Night = new BufferedImage[nightFiles.length];
            for (int i = 0; i < nightFiles.length; i++) {
                bgLayers2Night[i] = tryLoad(bg2Dir + nightFiles[i]);
            }
        }
        if (overlay != null) {
            bgOverlay2 = tryLoad(bg2Dir + overlay);
        }
        // Fallback: if no day/night, try explicit file list
        if (bgLayers2.length == 0) {
            String[] bg2Files = level2Data.getBgFiles();
            if (bg2Files != null) {
                bgLayers2 = new BufferedImage[bg2Files.length];
                for (int i = 0; i < bg2Files.length; i++) {
                    bgLayers2[i] = tryLoad(bg2Dir + bg2Files[i]);
                }
            }
        }

        // --- Platform tile images ---
        String tileDir =
            "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/";
        platformTile = tryLoad(tileDir +
            "01_Platform_SolidBlock_FlatTopFull_DarkPurple_PrimaryWalkableFloorTile.png");
        groundTile   = tryLoad(tileDir +
            "07_Panel_GridSurface_2x2QuadDivided_FlatIndustrialFace_WallOrFloorPanelFill.png");
        groundTop    = tryLoad(tileDir +
            "18_Edge_HorizontalShelfBar_NarrowCentreAligned_FlatTop_LedgeSurfaceOrPlatformEdge.png");

        // --- Smoke VFX animation ---
        String smokeDir = "Resources/industrial-zone/vfx/1 Smoke/";
        smokeAnim = new Animation();
        for (int i = 1; i <= 14; i++) {
            String frameName = String.format("%02d_VFX_Smoke_Frame%02d", i, i);
            File[] matches = new File(smokeDir).listFiles(
                f -> f.getName().startsWith(frameName));
            if (matches != null && matches.length > 0) {
                BufferedImage img = tryLoad(matches[0].getPath());
                if (img != null) smokeAnim.addFrame(img, 80L);
            }
        }
        if (smokeAnim != null) smokeAnim.setLoop(true);

        // --- Ladder PNG images ---
        String objDir = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/";
        ladderTall    = tryLoad(objDir + "Prop_Ladder_TallFullHeight_BlueGreyRungs_ShaftWallClimb_ClimbableA.png");
        ladderTallAlt = tryLoad(objDir + "Prop_Ladder_TallAltSpacing_BlueGreyRungs_ShaftWallClimb_ClimbableB.png");
        ladderShort   = tryLoad(objDir + "Prop_Ladder_ShortHorizontalRung_BlueCrossbar_PlatformConnector_Short.png");

        // Load OTF font
        try {
            hudFont = Font.createFont(Font.TRUETYPE_FONT,
                new File("Resources/industrial-zone/gui/10 Font/CyberpunkCraftpixPixel.otf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(hudFont);
            hudFont = hudFont.deriveFont(Font.PLAIN, 14f);
        } catch (Exception e) {
            System.err.println("[Game] OTF font not loaded, using fallback: " + e.getMessage());
            hudFont = new Font("Courier New", Font.BOLD, 14);
        }

        loadGuiAssets();
        loadTileMaps();
        System.out.println("[Game] Assets loaded.");
    }

    // -------------------------------------------------------------------------
    //  Tile map loading
    // -------------------------------------------------------------------------
    private void loadTileMaps() {
        tileMap1 = new TileMap(level1Data.getMapFilePath(), level1Data.getTileMapDirs(), level1Data.getTileMapOffsetY());
        tileMap2 = new TileMap(level2Data.getMapFilePath(), level2Data.getTileMapDirs(), level2Data.getTileMapOffsetY());
    }

    // -------------------------------------------------------------------------
    //  GUI asset loading
    // -------------------------------------------------------------------------
    private void loadGuiAssets() {
        // LOGOS
        logoFull    = tryLoad(GUI_DIR + "5 Logo/GUI_Logo_IndustrialZone_Full.png");
        logoCompact = tryLoad(GUI_DIR + "5 Logo/GUI_Logo_IndustrialZone_Compact.png");
        logoMinimal = tryLoad(GUI_DIR + "5 Logo/GUI_Logo_IndustrialZone_Minimal.png");

        // CURSORS
        cursorImgs[0] = tryLoad(GUI_DIR + "8 Cursors/01_GUI_Cursor_White_DefaultPointer.png");
        cursorImgs[1] = tryLoad(GUI_DIR + "8 Cursors/02_GUI_Cursor_Blue_TargetingPointer.png");
        cursorImgs[2] = tryLoad(GUI_DIR + "8 Cursors/03_GUI_Cursor_Red_AttackPointer.png");
        cursorImgs[3] = tryLoad(GUI_DIR + "8 Cursors/04_GUI_Cursor_Green_ConfirmPointer.png");

        // HEALTH BARS
        hudBars[0] = tryLoad(GUI_DIR + "2 Bars/01_GUI_Bar_HealthBar_Full100pct_RedOrangeFillDarkFrame_HUD.png");
        hudBars[1] = tryLoad(GUI_DIR + "2 Bars/02_GUI_Bar_HealthBar_80pct_RedOrangeFill_HUD.png");
        hudBars[2] = tryLoad(GUI_DIR + "2 Bars/03_GUI_Bar_HealthBar_60pct_RedOrangeFill_HUD.png");
        hudBars[3] = tryLoad(GUI_DIR + "2 Bars/04_GUI_Bar_HealthBar_40pct_RedOrangeFill_HUD.png");
        hudBars[4] = tryLoad(GUI_DIR + "2 Bars/05_GUI_Bar_HealthBar_20pct_RedOrangeFill_HUD.png");
        hudBars[5] = tryLoad(GUI_DIR + "2 Bars/06_GUI_Bar_HealthBar_5pctCritical_RedOrangeFill_HUD.png");
        hudBars[6] = tryLoad(GUI_DIR + "2 Bars/07_GUI_Bar_HealthBar_EmptyFrame_NoFillContainer_HUD.png");

        // ENERGY BARS
        enerBars[0] = tryLoad(GUI_DIR + "2 Bars/09_GUI_Bar_EnergyBar_Full100pct_BlueCyanFillDarkFrame_HUD.png");
        enerBars[1] = tryLoad(GUI_DIR + "2 Bars/10_GUI_Bar_EnergyBar_80pct_BlueCyanFill_HUD.png");
        enerBars[2] = tryLoad(GUI_DIR + "2 Bars/11_GUI_Bar_EnergyBar_60pct_BlueCyanFill_HUD.png");
        enerBars[3] = tryLoad(GUI_DIR + "2 Bars/12_GUI_Bar_EnergyBar_40pct_BlueCyanFill_HUD.png");
        enerBars[4] = tryLoad(GUI_DIR + "2 Bars/13_GUI_Bar_EnergyBar_20pct_BlueCyanFill_HUD.png");
        enerBars[5] = tryLoad(GUI_DIR + "2 Bars/14_GUI_Bar_EnergyBar_5pctCritical_BlueCyanFill_HUD.png");
        enerBars[6] = tryLoad(GUI_DIR + "2 Bars/15_GUI_Bar_EnergyBar_EmptyFrame_NoFillContainer_HUD.png");

        // BUTTONS
        for (int i = 0; i < 10; i++) {
            btnColors[i] = tryLoad(GUI_DIR + String.format("6 Buttons/GUI_ButtonColorMap_Variant_%02d.png", i + 1));
        }
        btnNormal  = tryLoad(GUI_DIR + "3 Icons/Buttons2/GUI_Button_State_Variant02_01.png");
        btnHover   = tryLoad(GUI_DIR + "3 Icons/Buttons2/GUI_Button_State_Variant02_02.png");
        btnPressed = tryLoad(GUI_DIR + "3 Icons/Buttons2/GUI_Button_State_Variant02_03.png");

        // FRAMES
        frmCornerTL = tryLoad(GUI_DIR + "1 Frames/01_GUI_Frame_CornerTopLeft_TallLShapePiece_WindowCorner.png");
        frmEdgeTop  = tryLoad(GUI_DIR + "1 Frames/02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png");
        frmCornerTR = tryLoad(GUI_DIR + "1 Frames/03_GUI_Frame_CornerTopRight_TallLShapeMirror_WindowCorner.png");
        frmEdgeLeft = tryLoad(GUI_DIR + "1 Frames/05_GUI_Frame_EdgeLeftStrip_TallNarrowVerticalBar_WindowLeftEdge.png");
        frmEdgeRight= tryLoad(GUI_DIR + "1 Frames/06_GUI_Frame_EdgeRightStrip_TallNarrowVerticalBar_WindowRightEdge.png");
        frmFillNavy = tryLoad(GUI_DIR + "1 Frames/07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png");
        frmDivider  = tryLoad(GUI_DIR + "1 Frames/16_GUI_Frame_PanelWideRect_TealCyanAccentStripe_DividerBar.png");
        frmCornerBL = tryLoad(GUI_DIR + "1 Frames/19_GUI_Frame_CornerBottomLeft_LShapeCorner_WindowCorner.png");
        frmEdgeBot  = tryLoad(GUI_DIR + "1 Frames/20_GUI_Frame_EdgeBottomBar_PlainDarkStrip_WindowBottomEdge.png");
        frmCornerBR = tryLoad(GUI_DIR + "1 Frames/27_GUI_Frame_CornerBottomRight_DiagonalAngleTrim_WindowCorner.png");
        frmFillDark = tryLoad(GUI_DIR + "1 Frames/40_GUI_Frame_FillSolidDarkNavy_FullBlock_WindowFill.png");

        // DECORS
        decorGlowBars   = tryLoad(GUI_DIR + "9 Other/1 Decor/01_GUI_Decor_GlowBars_FourVerticalNeonRods_Decoration.png");
        decorRibbon     = tryLoad(GUI_DIR + "9 Other/1 Decor/02_GUI_Decor_RibbonZigzag_PinkStackedLShape_Decoration.png");
        decorCableTwist = tryLoad(GUI_DIR + "9 Other/1 Decor/03_GUI_Decor_CableTwist_RedBlueHelixWire_Decoration.png");
        decorCableCoil  = tryLoad(GUI_DIR + "9 Other/1 Decor/08_GUI_Decor_CableCoil_BlueLoopedWire_Decoration.png");
        decorCablePlug  = tryLoad(GUI_DIR + "9 Other/1 Decor/07_GUI_Decor_CablePlug_SingleBlueConnector_Decoration.png");

        // DIGIT PNGs [0..9]
        digitImgs[0] = tryLoad(GUI_DIR + "7 Numbers/GUI_Number_Digit0_Zero.png");
        for (int i = 1; i <= 9; i++) {
            digitImgs[i] = tryLoad(GUI_DIR +
                String.format("7 Numbers/%02d_GUI_Number_Digit%d_StyledGlyph_Decorative.png", i, i));
        }

        // SKILL ICONS for HUD
        final String SKILL_DIR = GUI_DIR + "9 Other/2 Skill icons/";
        iconHp    = tryLoad(SKILL_DIR + "06_GUI_SkillIcon_Crosshair_TargetOrAim_SkillIcon.png");
        iconEn    = tryLoad(SKILL_DIR + "18_GUI_SkillIcon_Shield_DefenceOrBlock_SkillIcon.png");
        iconStar  = tryLoad(SKILL_DIR + "13_GUI_SkillIcon_Tick_ConfirmOrSelect_SkillIcon.png");
        iconSkull = tryLoad(SKILL_DIR + "19_GUI_SkillIcon_Skull_DeathOrDanger_SkillIcon.png");

        // CHARACTER IDLE SPRITE SHEETS
        loadCharIdleFrames();

        // GUN IMAGES — sorted alphabetically from weapons/1/2 Guns/
        File gunsDir = new File(GUNS_DIR);
        if (gunsDir.isDirectory()) {
            File[] gfiles = gunsDir.listFiles(f ->
                f.isFile() && f.getName().toLowerCase().endsWith(".png")
                           && !f.getName().startsWith("._"));
            if (gfiles != null && gfiles.length > 0) {
                java.util.Arrays.sort(gfiles);
                gunImages = new BufferedImage[gfiles.length];
                for (int i = 0; i < gfiles.length; i++) {
                    gunImages[i] = tryLoad(gfiles[i].getAbsolutePath());
                }
                System.out.println("[Assets] Loaded " + gfiles.length + " gun images.");
            }
        } else {
            System.err.println("[Assets] Gun images directory not found: " + GUNS_DIR);
        }

        System.out.println("[Game] GUI assets loaded.");
    }

    private void loadCharIdleFrames() {
        // Use HorizontalSpritesheetLoader to auto-detect frame counts from manifest metadata
        String[] sheets = {
            "Resources/industrial-zone/characters/player/biker/01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png",
            "Resources/industrial-zone/characters/player/cyborg/01_Player_Cyborg_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png",
            "Resources/industrial-zone/characters/player/punk/01_Player_Punk_Idle_5Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png",
        };
        for (int c = 0; c < 3; c++) {
            HorizontalSpritesheetLoader loader = HorizontalSpritesheetLoader.fromFilename(sheets[c]);
            if (!loader.isLoaded()) continue;
            BufferedImage[] allFrames = loader.getAllFrames();
            for (int f = 0; f < 4; f++) {
                int srcF = Math.min(f, allFrames.length - 1);
                charIdleFrames[c][f] = allFrames[srcF];
            }
        }
    }

    // =========================================================================
    //  RENDERING SUBSYSTEM INIT
    // =========================================================================
    /** Constructs all rendering helper objects after all image assets are loaded. */
    private void initRenderers() {
        // VFX particle system (loads its own smoke sprite frames internally)
        vfx = new VfxSystem();

        // Parallax renderers — one per context (menu, level-1 gameplay, level-2 gameplay)
        parallaxMenu  = new ParallaxRenderer(bgLayers1, level1Data.getScrollFactors(), new Color(18, 12, 36));
        parallaxGame1 = new ParallaxRenderer(bgLayers1, level1Data.getScrollFactors(), new Color(22, 14, 42));
        parallaxGame2 = new ParallaxRenderer(
            (bgLayers2Day != null && bgLayers2Day.length > 0) ? bgLayers2Day :
            (bgLayers2 != null && bgLayers2.length > 0) ? bgLayers2 : bgLayers1,
            level2Data.getScrollFactors(), new Color(8, 12, 30));

        // Night parallax for Level 2 day/night crossfade
        parallaxGame2Night = new ParallaxRenderer(
            (bgLayers2Night != null && bgLayers2Night.length > 0) ? bgLayers2Night : bgLayers1,
            level2Data.getScrollFactors(), new Color(4, 6, 16));

        // HUD renderer (health bar, energy bar, score, timer)
        hudRenderer = new HudRenderer(hudBars, enerBars, digitImgs, frmDivider, hudFont);
        hudRenderer.setIcons(iconHp, iconEn, iconStar, iconSkull);

        // Panel renderer (nine-patch panels + state-aware buttons)
        panelRenderer = new PanelRenderer(
            frmCornerTL, frmCornerTR, frmCornerBL, frmCornerBR,
            frmEdgeTop,  frmEdgeBot,  frmEdgeLeft, frmEdgeRight,
            frmFillNavy,
            btnColors, btnNormal, btnHover, btnPressed,
            hudFont);

        System.out.println("[Game] Rendering subsystems initialised.");
    }

    private BufferedImage tryLoad(String path) {
        try {
            File f = new File(path);
            if (f.exists()) {
                BufferedImage img = ImageIO.read(f);
                System.out.println("[Assets] OK: " + f.getName());
                return img;
            }
        } catch (Exception e) {
            System.err.println("[Assets] FAIL: " + path);
        }
        return null;
    }

    // =========================================================================
    //  ENTITY SPAWNING
    // =========================================================================
    private void spawnEnemies(float[][] positions) {
        enemies.clear();
        Enemy.EnemyType[] types = Enemy.EnemyType.values();
        for (float[] pos : positions) {
            int typeId = (pos.length > 2) ? (int) pos[2] : 0;
            if (typeId < 0 || typeId >= types.length) typeId = 0;
            enemies.add(new Enemy(pos[0], pos[1], types[typeId]));
        }
        int bosses = 0;
        for (Enemy e : enemies) if (e.getType().isBoss()) bosses++;
        System.out.println("[Game] Spawned " + enemies.size() + " enemies (" + bosses + " boss).");
    }

    // -------------------------------------------------------------------------
    //  Animated object spawning
    // -------------------------------------------------------------------------
    private void spawnAnimatedObjects() {
        animatedObjects.clear();
        float[][] data = activeLevel.getAnimatedObjects();
        AnimatedObject.ObjType[] types = AnimatedObject.ObjType.values();

        for (float[] d : data) {
            int tid = (int) d[0];
            if (tid < 0 || tid >= types.length) continue;
            AnimatedObject.ObjType type = types[tid];

            String path = activeLevel.getAnimAssetPath(type);
            if (path == null) continue;

            AnimatedObject obj = new AnimatedObject(type, path, d[1], d[2], (int) d[3], (int) d[4]);

            // Configure moving platforms
            if (type == AnimatedObject.ObjType.MOVING_PLATFORM) {
                obj.setMoveRange(200f);
                obj.setMoveSpeed(60f);
            }
            // Configure conveyor speeds
            if (type == AnimatedObject.ObjType.CONVEYOR) obj.setConveyorSpeed(100f);
            if (type == AnimatedObject.ObjType.CONVEYOR_REVERSE) obj.setConveyorSpeed(100f);

            animatedObjects.add(obj);
        }
        lastCheckpointIdx = -1;
        checkpointX = activeLevel.getPlayerStartX();
        checkpointY = activeLevel.getPlayerStartY();
        System.out.println("[Game] Spawned " + animatedObjects.size() + " animated objects.");
    }

    // =========================================================================
    //  LEVEL SWITCHING
    // =========================================================================
    private void switchLevel(int level) {
        if (level == currentLevel) return;
        currentLevel = level;
        activeLevel = (level == 1) ? level1Data : level2Data;

        float[][] platforms = activeLevel.getPlatforms();
        PlayerBase.setPlatforms(platforms);

        // Reset player to start of new level
        player.setPosition(activeLevel.getPlayerStartX(), activeLevel.getPlayerStartY());

        // Respawn enemies and objects
        spawnEnemies(activeLevel.getEnemySpawns());
        spawnAnimatedObjects();
        projectiles.clear();
        weaponPickedUp = new boolean[activeLevel.getWeaponSpawns().length];

        cameraX = 0;
        cameraY = 0;
        bossEncounterTriggered = false;
        loadCinematicScenes();
        activeCinematic = null;

        System.out.println("[Game] Switched to Level " + level);
    }

    // =========================================================================
    //  RUN — override GameCore.run() so we use a proper Swing JPanel canvas
    //  instead of GameCore.gameLoop() which draws via JFrame.getGraphics() and
    //  gets overwritten by Swing's own repaint (causing the blank white screen).
    //
    //  Pattern is identical to InteractiveGameTester.java:
    //    • GamePanel extends JPanel  → paintComponent() is the real paint path
    //    • javax.swing.Timer(16,…)   → ~60-fps game loop on the Swing EDT
    //    • pack() + setVisible()     → correct window size from the start
    // =========================================================================
    @Override
    public void run(boolean full, int w, int h) {
        // --- window sizing (GameCore.init() never calls setSize — root cause) ---
        setPreferredSize(new Dimension(w, h));

        // --- canvas panel: Swing calls paintComponent() so nothing overwrites us ---
        GamePanel canvas = new GamePanel();
        canvas.setPreferredSize(new Dimension(w, h));
        canvas.setBackground(Color.BLACK); // black base; draw() fills over it

        // replace content pane with our rendering canvas
        setContentPane(canvas);
        pack();                      // resize JFrame to exactly fit the canvas
        setLocationRelativeTo(null); // centre on screen

        // register key listener (GameCore.init() did this but we bypass it)
        addKeyListener(this);

        // make window visible after everything is set up
        setVisible(true);

        // hide the system cursor — draw() renders its own cursor PNG
        try {
            BufferedImage blank = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            setCursor(Toolkit.getDefaultToolkit()
                .createCustomCursor(blank, new Point(0, 0), "blank"));
        } catch (Exception ignored) {}

        // --- Swing Timer: update() + repaint() at ~60 fps, on the EDT ---
        // Same pattern as: new Timer(16, e -> { canvas.tick(); canvas.repaint(); }).start();
        final long[] lastTick = { System.currentTimeMillis() };
        new Timer(16, e -> {
            long now = System.currentTimeMillis();
            long dt  = Math.min(now - lastTick[0], 50); // cap delta so spiral-of-death can't occur
            lastTick[0] = now;
            update(dt);       // our @Override update() handles all screen logic
            canvas.repaint(); // triggers paintComponent() → draw() for all screens
        }).start();
        // run() returns immediately; Swing's EDT thread keeps the JVM alive
    }

    // =========================================================================
    //  GAME PANEL — inner JPanel that delegates painting to Game.draw()
    //  Using an inner class (not anonymous) lets us re-reference it cleanly.
    //  Mirrors GameCanvas in InteractiveGameTester.java.
    // =========================================================================
    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // fills background with Color.BLACK first
            Graphics2D g2 = (Graphics2D) g.create();
            // smooth rendering — same hints used in InteractiveGameTester
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                                RenderingHints.VALUE_RENDER_QUALITY);
            // delegate to all the draw* methods for whichever screen is active
            draw(g2);
            // debug: current screen name in top-right corner (small, always visible)
            g2.setFont(new Font("Consolas", Font.PLAIN, 11));
            g2.setColor(new Color(80, 255, 160, 180));
            String dbg = "[ " + currentScreen + " ]";
            g2.drawString(dbg, getWidth() - g2.getFontMetrics().stringWidth(dbg) - 6, 14);
            g2.dispose();
        }
    }

    // =========================================================================
    //  UPDATE  (called every frame by the Swing Timer above)
    // =========================================================================
    @Override
    public void update(long elapsedTime) {
        guiTime += elapsedTime / 1000f;
        // advance the parallax offset for all menu-style screens so the BG slowly scrolls
        switch (currentScreen) {
            case MAIN_MENU: case CHARACTER_SELECT: case LEVEL_SELECT:
            case CREDITS:   case CONTROLS:         case PAUSE:  case SETTINGS:
                menuCamX = (menuCamX + 0.55f * (elapsedTime / 16f)) % 4096f;
                break;
            default: break;
        }
        vfx.update(elapsedTime);  // tick VFX particles every frame
        switch (currentScreen) {
            case SPLASH:           updateSplash(elapsedTime);           break;
            case MAIN_MENU:        menuAnimTime = guiTime;               break;
            case CHARACTER_SELECT: updateCharSelect(elapsedTime);        break;
            case LEVEL_SELECT:
                if (levelSlideAnim != 0f) {
                    levelSlideAnim *= 0.8f;
                    if (Math.abs(levelSlideAnim) < 0.005f) levelSlideAnim = 0f;
                }
                break;
            case CREDITS:          updateCredits(elapsedTime);           break;
            case GAMEPLAY:         updateGameplay(elapsedTime);          break;
            case GAME_OVER:        updateGameOver(elapsedTime);          break;
            case LEVEL_COMPLETE:   levelCompleteTimer += elapsedTime / 1000f; break;
            default:               break; // PAUSE, SETTINGS, CONTROLS — frozen
        }
    }

    private void updateSplash(long ms) {
        splashTimer += ms;
        if (!splashFadingOut) {
            splashAlpha = Math.min(1f, splashTimer / 1000f);
            if (splashTimer >= 2500) { splashFadingOut = true; splashTimer = 0; }
        } else {
            splashAlpha = Math.max(0f, 1f - splashTimer / 800f);
            if (splashAlpha <= 0f) goTo(GameScreen.MAIN_MENU);
        }
    }

    private void updateCharSelect(long ms) {
        charAnimAccum += ms;
        if (charAnimAccum >= 150) {
            charAnimAccum -= 150;
            for (int i = 0; i < 3; i++) charAnimFrame[i] = (charAnimFrame[i] + 1) % 4;
        }
    }

    private void updateCredits(long ms) {
        float speed = creditsSpeedUp ? 80f : 30f;
        creditsScrollY -= speed * (ms / 1000f);
        if (creditsScrollY < -900f) {
            if (creditsAutoReturn < 0) creditsAutoReturn = 1000f;
        }
        if (creditsAutoReturn >= 0) {
            creditsAutoReturn -= ms;
            if (creditsAutoReturn < 0) {
                creditsScrollY = 700f;
                creditsAutoReturn = -1f;
                goTo(GameScreen.MAIN_MENU);
            }
        }
    }

    private void updateGameplay(long elapsedTime) {
        if (paused || gameOver) return;
        float delta = Math.min(elapsedTime / 1000.0f, 0.10f);

        // Cinematic scene: freeze gameplay while active
        if (activeCinematic != null) {
            cinematicCharTimer += delta;
            if (!cinematicFullText && cinematicCharTimer >= 0.03f) {
                cinematicCharTimer -= 0.03f;
                cinematicCharsShown++;
            }
            // Notification timers still tick during cinematics
            for (int i = notifications.size() - 1; i >= 0; i--) {
                notifications.get(i).timer += delta;
                if (notifications.get(i).timer >= notifications.get(i).duration)
                    notifications.remove(i);
            }
            return; // freeze all other gameplay
        }

        player.update(delta);
        updateCamera();

        // Screen shake decay
        if (shakeTimer > 0) shakeTimer -= delta;

        // Notification timers
        for (int i = notifications.size() - 1; i >= 0; i--) {
            notifications.get(i).timer += delta;
            if (notifications.get(i).timer >= notifications.get(i).duration)
                notifications.remove(i);
        }

        // Cinematic trigger check
        for (CinematicScene scene : cinematicScenes) {
            if (!scene.triggered && player.getX() >= scene.triggerX) {
                scene.triggered = true;
                activeCinematic = scene;
                cinematicCharsShown = 0;
                cinematicCharTimer = 0;
                cinematicFullText = false;
                break;
            }
        }

        TileMap map = (currentLevel == 1) ? tileMap1 : tileMap2;
        if (map != null && player.isAlive()) {
            float px = player.getX() + 32f, py = player.getY() + 56f; // feet centre
            if (map.isInstantKill(px, py)) {
                player.takeDamage(999);
                vfx.emitHitSparks(px, py);
                audioManager.playSoundEffect("explosion");
            } else if (map.isHazard(px, py)) {
                player.takeDamage(8);
                vfx.emitHitSparks(px, py);
                audioManager.playSoundEffect("bomb_drop");
            }
        }

        Projectile fired = player.getProjectileToFire();
        if (fired != null) {
            projectiles.add(fired);
            audioManager.playSoundEffect("laser_sword_1");
        }

        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile p = projectiles.get(i);
            p.update(delta);
            if (!p.isAlive()) { projectiles.remove(i); continue; }
            boolean hit = false;

            if (p.isEnemyProjectile()) {
                // Enemy projectile → can damage player
                if (player.isAlive() && aabbOverlap(
                        p.getX(), p.getY(), 10, 6,
                        player.getX() + 8, player.getY() + 8, 48, 54)) {
                    player.takeDamage((int) p.getDamage());
                    p.kill(); hit = true;
                    vfx.emitHitSparks(p.getX(), p.getY());
                    audioManager.playSoundEffect("karateka_attack");
                }
            } else {
                // Player projectile → can damage enemies
                for (int j = enemies.size() - 1; j >= 0; j--) {
                    Enemy e = enemies.get(j);
                    if (!e.isAlive()) continue;
                    if (aabbOverlap(p.getX(), p.getY(), 10, 6,
                                    e.getX(), e.getY(), e.getWidth(), e.getHeight())) {
                        e.takeDamage((int) p.getDamage());
                        p.kill(); hit = true;
                        vfx.emitHitSparks(p.getX(), p.getY());
                        vfx.emitBlood(p.getX(), p.getY());
                        audioManager.playSoundEffect("flying_platform_attak_1");
                        break;
                    }
                }
            }
            if (hit) projectiles.remove(i);
        }

        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy e = enemies.get(i);
            e.update(delta, player.getX(), player.getY());

            // Collect enemy projectiles
            Projectile ep = e.getPendingProjectile();
            if (ep != null) projectiles.add(ep);

            // Contact damage
            float ew = e.getWidth(), eh = e.getHeight();
            if (e.isAlive() && aabbOverlap(
                    player.getX() + 8, player.getY() + 8, 48, 54,
                    e.getX() + 4, e.getY() + 4, ew - 8, eh - 8)) {
                e.attackPlayer(player);
                audioManager.playSoundEffect("karateka_attack");
            }

            // Award score based on enemy type
            if (!e.isAlive()) {
                score += e.getScoreValue();
                gameOverEnemiesKilled++;
                vfx.emitHitSparks(e.getX() + ew/2, e.getY() + eh/2);
                audioManager.playSoundEffect("explosion");
                enemies.remove(i);
            }
        }

        // ------ Boss encounter detection ------
        if (!bossEncounterTriggered) {
            for (Enemy e : enemies) {
                if (e.getType().isBoss() && e.isAlive()) {
                    float dist = Math.abs(player.getX() - e.getX());
                    if (dist < 500) {
                        bossEncounterTriggered = true;
                        addNotification("BOSS APPEARED!", new Color(255, 60, 40), 3f);
                        triggerShake(6f, 0.8f);
                        audioManager.playSoundEffect("hovering_robot_sting");
                        audioManager.stopAllSounds();
                        audioManager.playSoundEffect("battle_theme_chinese_street"); // §15.2 boss encounter music
                        break;
                    }
                }
            }
        }

        // ------ Animated objects: update + player interaction ------
        float plX = player.getX(), plY = player.getY();
        for (int i = animatedObjects.size() - 1; i >= 0; i--) {
            AnimatedObject ao = animatedObjects.get(i);
            ao.update(delta);

            if (!ao.isActive()) continue;
            if (!ao.overlaps(plX + 8, plY + 8, 48, 54)) continue;

            AnimatedObject.ObjType ot = ao.getType();

            // Auto-collect (cards, money)
            if (ot.autoCollect) {
                int pts = ao.collect();
                if (pts > 0) {
                    score += pts;
                    // Track cash and cards separately for HUD
                    if (ot == AnimatedObject.ObjType.COLLECTIBLE_MONEY) {
                        player.addCash(pts);
                        audioManager.playSoundEffect("click_digital_1");
                    } else if (ot == AnimatedObject.ObjType.COLLECTIBLE_CARD) {
                        player.addCard();
                        audioManager.playSoundEffect("unlocked_chest"); // §15.1 card pickup SFX
                        // §15.3 card threshold unlock notifications
                        int cards = player.getCards();
                        if (cards == 3 && currentLevel == 1) {
                            addNotification("DASH UNLOCKED!  [SHIFT]", new Color(0, 255, 200), 3f);
                            audioManager.playSoundEffect("hovering_robot_sting");
                        } else if (cards == 5 && currentLevel == 2) {
                            addNotification("ENERGY BLAST UNLOCKED!", new Color(0, 255, 200), 3f);
                            audioManager.playSoundEffect("hovering_robot_sting");
                        }
                    }
                    vfx.emitHitSparks(ao.getX() + ao.getWidth() / 2,
                                      ao.getY() + ao.getHeight() / 2);
                }
            }

            // Conveyor push — offset player X when standing on it
            float push = ao.getConveyorPush();
            if (push != 0) {
                player.setPosition(plX + push * delta, plY);
            }

            // Hazard damage
            if (ao.isDamaging() && player.isAlive()) {
                player.takeDamage(ao.getDamage());
                vfx.emitHitSparks(plX + 32, plY + 32);
                audioManager.playSoundEffect("bomb_drop");
            }

            // Checkpoint portal — requires E-key press + card count
            if (ot == AnimatedObject.ObjType.PORTAL) {
                int portalIdx = i;
                if (portalIdx > lastCheckpointIdx) {
                    // Show interaction prompt when near
                    if (PlayerBase.isKeyDown(KeyEvent.VK_E)) {
                        int cardsNeeded = getCheckpointCardCost(portalIdx);
                        if (player.getCards() >= cardsNeeded) {
                            lastCheckpointIdx = portalIdx;
                            checkpointX = ao.getX();
                            checkpointY = ao.getY() - 16f;
                            if (cardsNeeded > 0) player.spendCards(cardsNeeded);
                            vfx.emitHitSparks(ao.getX() + 32, ao.getY());
                            addNotification("CHECKPOINT SAVED!", new Color(80, 255, 80), 2f);
                            triggerShake(2f, 0.3f);
                            audioManager.playSoundEffect("portal_1");
                            System.out.println("[Game] Checkpoint saved at x=" + (int) checkpointX);
                        } else {
                            addNotification("Need " + (cardsNeeded - player.getCards()) + " more cards!",
                                new Color(255, 80, 80), 1.5f);
                        }
                    }
                }
            }
        }

        // ------ Ladder zone detection ------
        float[][] ladders = activeLevel.getLadderZones();
        boolean touchingLadder = false;
        float plLX = player.getX() + 16, plLY = player.getY();
        for (float[] lz : ladders) {
            if (aabbOverlap(plLX, plLY, 32, 64, lz[0], lz[1], lz[2], lz[3])) {
                touchingLadder = true;
                break;
            }
        }
        // Auto-attach to ladder when pressing W/S while overlapping
        if (touchingLadder && !player.isOnLadder()) {
            if (PlayerBase.isKeyDown(KeyEvent.VK_W) || PlayerBase.isKeyDown(KeyEvent.VK_UP)
             || PlayerBase.isKeyDown(KeyEvent.VK_S) || PlayerBase.isKeyDown(KeyEvent.VK_DOWN)) {
                player.setOnLadder(true);
            }
        } else if (!touchingLadder && player.isOnLadder()) {
            player.setOnLadder(false);
        }

        // ------ Weapon pickup ------
        float[][] weaponSpawns = activeLevel.getWeaponSpawns();
        if (weaponPickedUp != null) {
            for (int i = 0; i < weaponSpawns.length; i++) {
                if (weaponPickedUp[i]) continue;
                float wx = weaponSpawns[i][1], wy = weaponSpawns[i][2];
                if (aabbOverlap(player.getX(), player.getY(), 64, 64,
                                wx, wy, 32, 32)) {
                    int typeId = (int) weaponSpawns[i][0];
                    PlayerBase.WeaponType[] types = PlayerBase.WeaponType.values();
                    if (typeId >= 0 && typeId < types.length) {
                        player.pickupWeapon(types[typeId]);
                        weaponPickedUp[i] = true;
                        vfx.emitHitSparks(wx + 16, wy + 16);
                        audioManager.playSoundEffect("click_digital_2");
                    }
                }
            }
        }

        // ------ Level completion: all bosses dead ------
        if (!levelComplete && !gameOver) {
            boolean bossExists = false;
            boolean bossAlive  = false;
            for (Enemy e : enemies) {
                if (e.getType().isBoss()) { bossExists = true; if (e.isAlive()) bossAlive = true; }
            }
            // Level complete when boss was spawned but is now dead
            if (bossExists && !bossAlive) {
                levelComplete = true;
                levelCompleteTimer = 0f;
                score += activeLevel.getCompletionScore();
                audioManager.stopAllSounds();
                audioManager.playSoundEffect("melody_of_the_win"); // §15.2 level complete jingle
                goTo(GameScreen.LEVEL_COMPLETE);
            }
            // Update objective text
            if (bossExists && bossAlive) objectiveText = "DEFEAT THE BOSS!";
            else if (enemies.size() > 3)  objectiveText = "CLEAR THE ENEMIES";
            else if (enemies.isEmpty())    objectiveText = "EXPLORE AHEAD →";
            else                           objectiveText = "ALMOST THERE...";
        }

        if (!player.isAlive() && !gameOver) {
            audioManager.stopAllSounds();
            audioManager.playSoundEffect("samurai_death"); // §15.2 player death SFX
            gameOver = true;
            gameOverTime = System.currentTimeMillis() - startTime;
            gameOverAlpha = 0f; gameOverCharsShown = 0; gameOverScoreTally = 0f;
            gameOverMenuIndex = 0; gameOverTypeTimer = 0f;
            goTo(GameScreen.GAME_OVER);
        }
    }

    private void updateGameOver(long ms) {
        gameOverAlpha     = Math.min(1f, gameOverAlpha + ms / 600f);
        gameOverTypeTimer += ms;
        if (gameOverCharsShown < 9 && gameOverTypeTimer >= 40f) {
            gameOverTypeTimer -= 40f;
            gameOverCharsShown++;
        }
        if (gameOverScoreTally < score) {
            gameOverScoreTally = Math.min(score, gameOverScoreTally + score * (ms / 800f));
        }
    }

    // -- navigation helper --
    private void goTo(GameScreen screen) {
        prevScreen = currentScreen;
        currentScreen = screen;
        // §15.2 screen-based background music
        if (screen == GameScreen.MAIN_MENU) {
            audioManager.stopAllSounds();
            audioManager.playSoundEffect("main_theme_chinese_street");
        } else if (screen == GameScreen.CHARACTER_SELECT) {
            audioManager.stopAllSounds();
            audioManager.playSoundEffect("melody_of_attraction_loopable");
        }
        if (screen == GameScreen.CREDITS) {
            creditsScrollY = 700f; creditsAutoReturn = -1f;
        }
    }

    // -- start a new game --
    private void startGame() {
        PlayerBase.CharacterType type;
        switch (charSelectIndex) {
            case 0:  type = PlayerBase.CharacterType.BIKER;  break;
            case 2:  type = PlayerBase.CharacterType.PUNK;   break;
            default: type = PlayerBase.CharacterType.CYBORG; break;
        }
        activeLevel = (levelSelectIndex == 0) ? level1Data : level2Data;
        float[][] platforms = activeLevel.getPlatforms();
        float[][] enemyData = activeLevel.getEnemySpawns();
        currentLevel = activeLevel.getLevelId();
        PlayerBase.setPlatforms(platforms);
        player = new PlayerBase(type, activeLevel.getPlayerStartX(), activeLevel.getPlayerStartY());
        enemies.clear(); projectiles.clear(); animatedObjects.clear();
        spawnEnemies(enemyData);
        spawnAnimatedObjects();
        weaponPickedUp = new boolean[activeLevel.getWeaponSpawns().length];
        score = 0; paused = false; gameOver = false;
        levelComplete = false; objectiveText = "DEFEAT ALL ENEMIES";
        cameraX = 0f; cameraY = 0f;
        bossEncounterTriggered = false;
        notifications.clear();
        gameOverEnemiesKilled = 0;
        loadCinematicScenes();
        activeCinematic = null;
        dayNightNotified = false; // reset day/night notification flag each game start
        startTime = System.currentTimeMillis();
        // Play level-appropriate background music (WAV SFX, not MIDI)
        // Plan §15.2: Level 1 → stealthy_theme_loopable; Level 2 → alternative_theme_chinese_street
        audioManager.stopAllSounds();
        if (currentLevel == 1) {
            audioManager.playSoundEffect("stealthy_theme_loopable");
        } else {
            audioManager.playSoundEffect("alternative_theme_chinese_street");
        }
        goTo(GameScreen.GAMEPLAY);
        System.out.println("[Game] Starting: " + type + " on Level " + currentLevel);
    }

    private boolean aabbOverlap(float x1, float y1, float w1, float h1,
                                 float x2, float y2, float w2, float h2) {
        return x1 < x2 + w2 && x1 + w1 > x2
            && y1 < y2 + h2 && y1 + h1 > y2;
    }

    /** Card cost for checkpoint portals — increases for later checkpoints. */
    private int getCheckpointCardCost(int portalIdx) {
        // Simple scaling: 1st portal=1 card, 2nd=2, 3rd+=3
        if (portalIdx <= 0) return 1;
        if (portalIdx <= 1) return 2;
        return 3;
    }

    // =========================================================================
    //  CAMERA  — deadzone smooth-follow with world boundary clamping
    // =========================================================================
    private static final float CAM_DEAD_X = 80f;
    private static final float CAM_DEAD_Y = 40f;
    private static final float CAM_LERP   = 0.08f;

    private void updateCamera() {
        float targetX = player.getX() - SCREEN_W * 0.35f;
        float targetY = player.getY() - SCREEN_H * 0.45f;

        // Deadzone: only move camera when player exceeds threshold from target
        float dx = targetX - cameraX;
        float dy = targetY - cameraY;
        if (Math.abs(dx) > CAM_DEAD_X) {
            cameraX += (dx - Math.signum(dx) * CAM_DEAD_X) * CAM_LERP;
        }
        if (Math.abs(dy) > CAM_DEAD_Y) {
            cameraY += (dy - Math.signum(dy) * CAM_DEAD_Y) * CAM_LERP;
        }

        // World boundary clamp
        float worldW = activeLevel.getWorldWidth();
        if (cameraX < 0) cameraX = 0;
        if (cameraX > worldW - SCREEN_W) cameraX = Math.max(0, worldW - SCREEN_W);
        if (cameraY < 0)   cameraY = 0;
        if (cameraY > 220) cameraY = 220;

        // Screen shake offset
        if (shakeTimer > 0) {
            shakeOffsetX = (float)(Math.random() * 2 - 1) * shakeIntensity;
            shakeOffsetY = (float)(Math.random() * 2 - 1) * shakeIntensity;
        } else {
            shakeOffsetX = 0;
            shakeOffsetY = 0;
        }
    }

    // =========================================================================
    //  DRAW  (called every frame by GameCore)
    // =========================================================================
    @Override
    public void draw(Graphics2D g) {
        int W = Math.max(getWidth(),  SCREEN_W);
        int H = Math.max(getHeight(), SCREEN_H);
        switch (currentScreen) {
            case SPLASH:           drawSplash(g, W, H);           break;
            case MAIN_MENU:        drawMainMenu(g, W, H);         break;
            case CONTROLS:         drawControls(g, W, H);         break;
            case CHARACTER_SELECT: drawCharSelect(g, W, H);       break;
            case LEVEL_SELECT:     drawLevelSelect(g, W, H);      break;
            case CREDITS:          drawCredits(g, W, H);          break;
            case GAMEPLAY:         drawGameplayScreen(g, W, H);   break;
            case PAUSE:
                drawGameplayScreen(g, W, H);
                drawPauseOverlay(g, W, H);
                break;
            case SETTINGS:
                drawGameplayScreen(g, W, H);
                drawPauseOverlay(g, W, H);
                drawSettingsOverlay(g, W, H);
                break;
            case GAME_OVER:
                drawGameplayScreen(g, W, H);
                drawGameOverScreen(g, W, H);
                break;
            case LEVEL_COMPLETE:
                drawGameplayScreen(g, W, H);
                drawLevelCompleteOverlay(g, W, H);
                break;
        }
        vfx.draw(g);     // VFX particles always render on top of everything
        drawCursor(g);  // custom cursor drawn last so it's always visible
    }

    // Renders the full in-game view (bg + platforms + entities + HUD)
    private void drawGameplayScreen(Graphics2D g, int W, int H) {
        // Apply screen shake offset
        if (shakeTimer > 0) {
            g.translate((int) shakeOffsetX, (int) shakeOffsetY);
        }

        drawBackground(g, W, H);
        drawPlatforms(g, W, H);

        // ── Ladder zones (drawn behind entities using real PNG assets) ──
        float[][] ladders = activeLevel.getLadderZones();
        if (ladders != null) {
            for (float[] lz : ladders) {
                int lx = (int)(lz[0] - cameraX);
                int ly = (int)(lz[1] - cameraY);
                int lw = (int) lz[2];
                int lh = (int) lz[3];
                if (lx + lw < 0 || lx > W || ly + lh < 0 || ly > H) continue;

                // Pick ladder image based on height
                BufferedImage ladderImg = (lh > 120) ? ladderTall :
                                          (lh > 60)  ? ladderTallAlt : ladderShort;
                if (ladderImg != null) {
                    // Tile the ladder image vertically to fill the zone
                    int imgH = ladderImg.getHeight();
                    int imgW = ladderImg.getWidth();
                    // Scale to fit the zone width
                    float scale = (float) lw / imgW;
                    int drawW = lw;
                    int drawSegH = (int)(imgH * scale);
                    if (drawSegH < 1) drawSegH = 1;
                    for (int ry = ly; ry < ly + lh; ry += drawSegH) {
                        int dh = Math.min(drawSegH, ly + lh - ry);
                        g.drawImage(ladderImg, lx, ry, lx + drawW, ry + dh,
                                    0, 0, imgW, (int)(dh / scale), null);
                    }
                } else {
                    // Fallback: brown rails + rungs
                    g.setColor(new Color(120, 90, 60));
                    g.fillRect(lx, ly, 4, lh);
                    g.fillRect(lx + lw - 4, ly, 4, lh);
                    g.setColor(new Color(160, 130, 80));
                    for (int ry = ly + 12; ry < ly + lh; ry += 18) {
                        g.fillRect(lx + 4, ry, lw - 8, 3);
                    }
                }
            }
        }

        // ── Weapon spawn pickups (drawn on ground before entities) ──
        float[][] wSpawns = activeLevel.getWeaponSpawns();
        if (wSpawns != null && weaponPickedUp != null) {
            for (int i = 0; i < wSpawns.length; i++) {
                if (weaponPickedUp.length > i && weaponPickedUp[i]) continue;
                float[] ws = wSpawns[i];
                // Format: {typeId, x, y}
                int typeIdx = (int) ws[0];
                int wx = (int)(ws[1] - cameraX);
                int wy = (int)(ws[2] - cameraY);
                if (wx < -40 || wx > W + 40 || wy < -40 || wy > H + 40) continue;
                // Glowing box
                Color wc;
                String wName;
                switch (typeIdx) {
                    case 0: wc = new Color(180, 180, 180); wName = "PISTOL";  break;
                    case 1: wc = new Color(60, 180, 255);  wName = "SMG";     break;
                    case 2: wc = new Color(255, 160, 40);  wName = "RIFLE";   break;
                    case 3: wc = new Color(255, 60, 60);   wName = "SHOTGUN"; break;
                    default: wc = Color.WHITE;              wName = "?";       break;
                }
                // Outer glow halo (pulsing)
                float pulse = (float) Math.abs(Math.sin(System.currentTimeMillis() * 0.003));
                int glowR = (int)(20 + pulse * 10);
                g.setColor(new Color(wc.getRed(), wc.getGreen(), wc.getBlue(), (int)(40 + pulse * 40)));
                g.fillOval(wx - glowR, wy - glowR, glowR * 2, glowR * 2);
                // Panel background (dark navy)
                int boxW = 44, boxH = 36;
                if (frmFillDark != null) {
                    g.drawImage(frmFillDark, wx - boxW / 2, wy - boxH / 2, boxW, boxH, null);
                } else {
                    g.setColor(new Color(10, 10, 30, 210));
                    g.fillRoundRect(wx - boxW / 2, wy - boxH / 2, boxW, boxH, 6, 6);
                }
                // Teal border
                g.setColor(new Color(wc.getRed(), wc.getGreen(), wc.getBlue(), 200));
                g.setStroke(new java.awt.BasicStroke(1.5f));
                g.drawRoundRect(wx - boxW / 2, wy - boxH / 2, boxW, boxH, 6, 6);
                g.setStroke(new java.awt.BasicStroke(1f));
                // Gun thumbnail
                PlayerBase.WeaponType[] wtypes = PlayerBase.WeaponType.values();
                BufferedImage pickupGun = (typeIdx < wtypes.length && gunImages.length > 0)
                    ? gunImages[gunIndexFor(wtypes[typeIdx])] : null;
                if (pickupGun != null) {
                    int iw = boxW - 8, ih = boxH - 8;
                    double sc = Math.min((double) iw / pickupGun.getWidth(),
                                        (double) ih / pickupGun.getHeight());
                    int dw2 = (int)(pickupGun.getWidth() * sc);
                    int dh2 = (int)(pickupGun.getHeight() * sc);
                    g.drawImage(pickupGun, wx - dw2 / 2, wy - dh2 / 2, dw2, dh2, null);
                }
                // Label above box
                g.setFont(hudFont != null ? hudFont.deriveFont(Font.BOLD, 9f) : new Font("Consolas", Font.BOLD, 9));
                g.setColor(wc);
                FontMetrics fmw = g.getFontMetrics();
                g.drawString(wName, wx - fmw.stringWidth(wName) / 2, wy - boxH / 2 - 3);
            }
        }

        // Animated objects (collectibles, chests, conveyors, portals, hazards)
        for (AnimatedObject ao : animatedObjects) ao.render(g, cameraX, cameraY);

        if (player != null) {
            for (Enemy e : enemies)      e.render(g, (int) cameraX, (int) cameraY);
            player.render(g, (int) cameraX, (int) cameraY);

            // ── Gun-in-hand: drawn on top of the player sprite, rotated toward mouse ──
            if (player.isAlive() && gunImages.length > 0) {
                PlayerBase.WeaponType wt = player.getActiveWeapon();
                if (wt != null) {
                    BufferedImage gImg = gunImages[gunIndexFor(wt)];
                    if (gImg != null) {
                        boolean facingRight = player.isFacingRight();
                        // Arm origin: player screen coordinates + arm offset
                        int sx = (int)(player.getX() - cameraX);
                        int sy = (int)(player.getY() - cameraY);
                        int armX = sx + (facingRight ? 42 : 22);  // right/left arm position
                        int armY = sy + 28;                        // mid-torso height
                        double angle = Math.atan2(mouseY - armY, mouseX - armX);
                        AffineTransform saved = g.getTransform();
                        g.translate(armX, armY);
                        g.rotate(angle);
                        // Flip Y when facing left so gun doesn't appear upside-down
                        if (!facingRight) g.scale(1.0, -1.0);
                        // Draw at 6× scale, centred vertically on the arm origin
                        int gsc = 6;
                        g.drawImage(gImg, 0, -gImg.getHeight() * gsc / 2,
                                    gImg.getWidth() * gsc, gImg.getHeight() * gsc, null);
                        g.setTransform(saved);
                    }
                }
            }

            for (Projectile p : projectiles) p.render(g, (int) cameraX, (int) cameraY);
        }
        hudRenderer.draw(g, W, H,
            player != null ? player.getHealth()    : 0,
            player != null ? player.getMaxHealth() : 100,
            score, startTime, currentLevel, enemies.size(), getFPS());

        // ── Weapon HUD bar (bottom-right, 4 slots with real gun thumbnail images) ──
        if (player != null) {
            int slotW = 64, slotH = 52, gap = 4;
            int totalW = 4 * slotW + 3 * gap;
            int hx = W - totalW - 16;
            int hy = H - slotH - 22;  // sit neatly within the 80px bottom HUD bar
            PlayerBase.WeaponType[] slots = player.getWeaponSlots();
            int[] ammo = player.getWeaponAmmo();
            int activeSlot = player.getActiveWeaponSlot();
            g.setFont(new Font("Consolas", Font.BOLD, 10));
            for (int s = 0; s < 4; s++) {
                int sx = hx + s * (slotW + gap);
                boolean active = (s == activeSlot);

                // Slot background — use frame fill at native size, scaled to slot
                if (frmFillDark != null) {
                    g.drawImage(frmFillDark, sx, hy, slotW, slotH, null);
                } else {
                    g.setColor(active ? new Color(0, 200, 255, 90) : new Color(0, 0, 0, 140));
                    g.fillRoundRect(sx, hy, slotW, slotH, 6, 6);
                }

                // Active-slot intense teal border; inactive-slot dim border
                if (active) {
                    // Double-line glow border for active slot
                    g.setColor(new Color(0, 240, 255, 80));
                    g.setStroke(new java.awt.BasicStroke(4f));
                    g.drawRoundRect(sx, hy, slotW, slotH, 6, 6);
                    g.setColor(new Color(0, 220, 255, 220));
                    g.setStroke(new java.awt.BasicStroke(1.5f));
                    g.drawRoundRect(sx + 1, hy + 1, slotW - 2, slotH - 2, 5, 5);
                    g.setStroke(new java.awt.BasicStroke(1f));
                    // Corner rivets (4 px squares)
                    g.setColor(new Color(0, 255, 255));
                    g.fillRect(sx,          hy,          4, 4);
                    g.fillRect(sx + slotW - 4, hy,       4, 4);
                    g.fillRect(sx,          hy + slotH - 4, 4, 4);
                    g.fillRect(sx + slotW - 4, hy + slotH - 4, 4, 4);
                } else {
                    g.setColor(new Color(80, 90, 110, 180));
                    g.drawRoundRect(sx, hy, slotW, slotH, 6, 6);
                }

                // Slot number badge (top-left)
                g.setFont(new Font("Consolas", Font.BOLD, 9));
                g.setColor(active ? new Color(0, 240, 255) : new Color(140, 140, 160));
                g.drawString(String.valueOf(s + 1), sx + 3, hy + 10);

                if (slots[s] != null) {
                    // Gun thumbnail — scale to fill inner slot, keep aspect ratio
                    BufferedImage gunImg = gunImages.length > 0 ? gunImages[gunIndexFor(slots[s])] : null;
                    if (gunImg != null) {
                        int maxW = slotW - 6, maxH = slotH - 20;
                        double scale = Math.min((double) maxW / gunImg.getWidth(),
                                                (double) maxH / gunImg.getHeight());
                        int dw = (int)(gunImg.getWidth()  * scale);
                        int dh = (int)(gunImg.getHeight() * scale);
                        int dx = sx + (slotW - dw) / 2;
                        int dy = hy + 4 + (maxH - dh) / 2;
                        g.drawImage(gunImg, dx, dy, dw, dh, null);
                    } else {
                        // Fallback: weapon name text if image unavailable
                        g.setFont(new Font("Consolas", Font.BOLD, 9));
                        g.setColor(Color.WHITE);
                        g.drawString(slots[s].displayName, sx + 4, hy + 30);
                    }
                    // Ammo count (bottom of slot)
                    g.setFont(new Font("Consolas", Font.BOLD, 10));
                    g.setColor(ammo[s] > 0 ? new Color(100, 255, 100) : new Color(255, 80, 80));
                    String ammoStr = String.valueOf(ammo[s]);
                    FontMetrics fma = g.getFontMetrics();
                    g.drawString(ammoStr, sx + slotW - fma.stringWidth(ammoStr) - 3, hy + slotH - 3);
                } else {
                    // Empty slot — dash
                    g.setFont(new Font("Consolas", Font.PLAIN, 11));
                    g.setColor(new Color(70, 70, 90));
                    g.drawString("---", sx + (slotW - g.getFontMetrics().stringWidth("---")) / 2, hy + slotH / 2 + 4);
                }
            }
        }

        // ── Cash & Card counters (above weapon HUD, right side) ──
        if (player != null) {
            int cx = W - 230;
            int cy = H - 90;
            g.setFont(hudFont != null ? hudFont.deriveFont(Font.BOLD, 13f) : new Font("Consolas", Font.BOLD, 13));
            // Cash
            g.setColor(new Color(255, 220, 50));
            g.drawString("$" + player.getCash(), cx, cy);
            // Cards
            g.setColor(new Color(100, 200, 255));
            g.drawString(player.getCards() + "/5 cards", cx + 70, cy);
        }

        // ── Heal cooldown indicator ──
        if (player != null) {
            float healPct = player.getHealCooldownPct();
            int hx2 = W - 230;
            int hy2 = H - 104;
            g.setFont(hudFont != null ? hudFont.deriveFont(Font.PLAIN, 10f) : new Font("Consolas", Font.PLAIN, 10));
            if (healPct >= 1f) {
                g.setColor(new Color(80, 255, 80));
                g.drawString("[H] Heal READY", hx2, hy2);
            } else {
                g.setColor(new Color(180, 180, 180));
                g.drawString("[H] " + (int)(healPct * 100) + "%", hx2, hy2);
                g.setColor(new Color(50, 50, 50));
                g.fillRect(hx2 + 46, hy2 - 9, 60, 7);
                g.setColor(new Color(80, 200, 80));
                g.fillRect(hx2 + 46, hy2 - 9, (int)(60 * healPct), 7);
            }
        }

        // Objective text — top center, below HUD
        if (objectiveText != null && !objectiveText.isEmpty()) {
            g.setFont(hudFont != null ? hudFont.deriveFont(Font.BOLD, 14f) : new Font("Consolas", Font.BOLD, 14));
            FontMetrics fm = g.getFontMetrics();
            int tw = fm.stringWidth(objectiveText);
            int ox = (W - tw) / 2;
            int oy = 56;
            g.setColor(new Color(0, 0, 0, 120));
            g.fillRoundRect(ox - 10, oy - 14, tw + 20, 22, 8, 8);
            g.setColor(new Color(255, 220, 60));
            g.drawString(objectiveText, ox, oy);
        }

        // ── Notifications (pop-up messages above centre) ──
        if (!notifications.isEmpty()) {
            g.setFont(hudFont != null ? hudFont.deriveFont(Font.BOLD, 18f) : new Font("Consolas", Font.BOLD, 18));
            int ny = 90;
            for (GameNotification n : notifications) {
                float alpha = 1f;
                if (n.timer < 0.3f) alpha = n.timer / 0.3f;           // fade in
                else if (n.timer > n.duration - 0.5f) alpha = (n.duration - n.timer) / 0.5f; // fade out
                alpha = Math.max(0, Math.min(1, alpha));
                FontMetrics fm2 = g.getFontMetrics();
                int tw2 = fm2.stringWidth(n.text);
                int nx = (W - tw2) / 2;
                g.setColor(new Color(0, 0, 0, (int)(160 * alpha)));
                g.fillRoundRect(nx - 14, ny - 16, tw2 + 28, 28, 10, 10);
                g.setColor(new Color(n.color.getRed(), n.color.getGreen(), n.color.getBlue(), (int)(255 * alpha)));
                g.drawString(n.text, nx, ny);
                ny += 34;
            }
        }

        // ── Cinematic scene overlay ──
        if (activeCinematic != null) {
            // Darken background
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, W, H);

            // Dialog box: 650×130, centred horizontally, bottom third
            int bw = 650, bh = 130;
            int bx = (W - bw) / 2;
            int by = H - bh - 80;

            // Box background
            g.setColor(new Color(10, 10, 30, 220));
            g.fillRoundRect(bx, by, bw, bh, 16, 16);
            // Box border
            g.setColor(new Color(0, 180, 255, 180));
            g.drawRoundRect(bx, by, bw, bh, 16, 16);

            // Speaker name
            g.setFont(hudFont != null ? hudFont.deriveFont(Font.BOLD, 14f) : new Font("Consolas", Font.BOLD, 14));
            g.setColor(new Color(0, 200, 255));
            g.drawString(activeCinematic.speaker, bx + 20, by + 24);

            // Dialog text (typewriter effect)
            g.setFont(hudFont != null ? hudFont.deriveFont(Font.PLAIN, 12f) : new Font("Consolas", Font.PLAIN, 12));
            g.setColor(new Color(220, 220, 240));
            String fullText = activeCinematic.dialog;
            int showChars = cinematicFullText ? fullText.length() : Math.min(cinematicCharsShown, fullText.length());
            String visibleText = fullText.substring(0, showChars);
            // Word-wrap at 70 chars
            FontMetrics cfm = g.getFontMetrics();
            int lineY = by + 48;
            StringBuilder line = new StringBuilder();
            for (String word : visibleText.split(" ")) {
                if (cfm.stringWidth(line + " " + word) > bw - 40 && line.length() > 0) {
                    g.drawString(line.toString(), bx + 20, lineY);
                    lineY += 18;
                    line = new StringBuilder();
                }
                if (line.length() > 0) line.append(" ");
                line.append(word);
            }
            if (line.length() > 0) g.drawString(line.toString(), bx + 20, lineY);

            // Skip hint
            g.setFont(new Font("Consolas", Font.PLAIN, 10));
            g.setColor(new Color(180, 180, 180, 160));
            g.drawString("[SPACE to continue]", bx + bw - 140, by + bh - 10);
        }

        // Reset screen shake translation
        if (shakeTimer > 0) {
            g.translate(-(int) shakeOffsetX, -(int) shakeOffsetY);
        }
    }

    // -------------------------------------------------------------------------
    //  Background & parallax
    // -------------------------------------------------------------------------
    private void drawBackground(Graphics2D g, int W, int H) {
        if (currentLevel == 1) {
            parallaxGame1.draw(g, W, H, cameraX, 1.0f);
        } else {
            // Level 2: day/night crossfade based on camera position (plan §24.2)
            // fadeStart = col 430 × 32 = 13760 px; fadeEnd = col 469 × 32 = 15008 px
            final float fadeStart = 430 * 32f; // 13760 px — transition begins here
            final float fadeEnd   = 469 * 32f; // 15008 px — fully night from here

            float nightAlpha;
            if (cameraX >= fadeEnd) {
                nightAlpha = 1f;                                          // pure night
            } else if (cameraX >= fadeStart) {
                nightAlpha = (cameraX - fadeStart) / (fadeEnd - fadeStart); // crossfade
            } else {
                nightAlpha = 0f;                                          // pure day
            }

            // Fire one-shot "THE DARKNESS FALLS" notification at transition boundary
            if (!dayNightNotified && cameraX >= fadeStart) {
                dayNightNotified = true;
                addNotification("THE DARKNESS FALLS", new Color(80, 120, 255), 3f);
            }

            if (nightAlpha <= 0f) {
                // Pure day
                parallaxGame2.draw(g, W, H, cameraX, 1.0f);
            } else if (nightAlpha >= 1f) {
                // Pure night
                parallaxGame2Night.draw(g, W, H, cameraX, 1.0f);
            } else {
                // Crossfade: draw day, then overlay night with alpha
                parallaxGame2.draw(g, W, H, cameraX, 1.0f);
                Composite saved = g.getComposite();
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, nightAlpha));
                parallaxGame2Night.draw(g, W, H, cameraX, 1.0f);
                g.setComposite(saved);
            }

            // Draw overlay on top (atmosphere gradient)
            if (bgOverlay2 != null) {
                float overlayAlpha = 0.35f + 0.25f * nightAlpha; // stronger at night
                Composite saved = g.getComposite();
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, overlayAlpha));
                g.drawImage(bgOverlay2, 0, 0, W, H, null);
                g.setComposite(saved);
            }
        }
    }

    // Renders the parallax background for menu/UI screens using menuCamX (auto-scrolls slowly left).
    // Uses bgLayers1 regardless of level so menus have a consistent industrial-zone look.
    private void drawMenuBG(Graphics2D g, int W, int H) {
        parallaxMenu.draw(g, W, H, menuCamX, 0.75f);
    }

    // -------------------------------------------------------------------------
    //  Platform rendering with tile map + fallback tile images
    // -------------------------------------------------------------------------
    private void drawPlatforms(Graphics2D g, int W, int H) {
        // === 1. Render tile map (the main ground area with rich tile detail) ===
        TileMap map = (currentLevel == 1) ? tileMap1 : tileMap2;
        if (map != null) {
            map.render(g, cameraX, cameraY, W, H);
            // Fill bedrock below the tile map grid (so no black gap shows at bottom)
            float[][] plats = activeLevel.getPlatforms();
            map.renderGroundFill(g, cameraX, cameraY, W, H,
                (int)(plats[0][1] + plats[0][3]));
        }

        // === 2. Render floating platforms (above tile map area) with tile images ===
        float[][] platforms = activeLevel.getPlatforms();

        for (int i = 1; i < platforms.length; i++) {  // skip index 0 (ground — handled by tile map)
            float[] plat = platforms[i];
            int px = (int)(plat[0] - cameraX);
            int py = (int)(plat[1] - cameraY);
            int pw = (int) plat[2];
            int ph = (int) plat[3];

            // Skip off-screen
            if (px + pw < 0 || px > W || py + ph < 0 || py > H) continue;

            BufferedImage tile = platformTile;

            if (tile != null) {
                int tw = tile.getWidth();
                int th = tile.getHeight();
                if (tw > 0 && th > 0) {
                    for (int tx = px; tx < px + pw; tx += tw) {
                        for (int ty = py; ty < py + ph; ty += th) {
                            int dw = Math.min(tw, (px + pw) - tx);
                            int dh = Math.min(th, (py + ph) - ty);
                            g.drawImage(tile, tx, ty, tx + dw, ty + dh,
                                         0,  0,  dw,  dh, null);
                        }
                    }
                    // Draw top edge accent
                    if (groundTop != null) {
                        int etw = groundTop.getWidth();
                        int eth = Math.min(groundTop.getHeight(), 6);
                        for (int tx = px; tx < px + pw; tx += etw) {
                            int dw = Math.min(etw, (px + pw) - tx);
                            g.drawImage(groundTop, tx, py - 2, tx + dw, py - 2 + eth,
                                         0, 0, dw, eth, null);
                        }
                    }
                    continue;
                }
            }

            // Fallback colours when tile images not loaded
            g.setColor(new Color(55, 50, 90));
            g.fillRect(px, py, pw, ph);
            g.setColor(new Color(110, 100, 180));
            g.fillRect(px, py, pw, 3);
        }
    }

    // =========================================================================
    //  PANEL & BUTTON HELPERS  — delegate to PanelRenderer
    // =========================================================================

    // Draws a framed panel using the nine-patch frame assets (delegates to PanelRenderer)
    private void drawPanel(Graphics2D g, int x, int y, int w, int h) {
        panelRenderer.drawPanel(g, x, y, w, h);
    }

    // state: 0=normal, 1=hover, 2=pressed (delegates to PanelRenderer)
    private void drawButton(Graphics2D g, int x, int y, int w, int h, String label, int state) {
        panelRenderer.drawButton(g, x, y, w, h, label, state);
    }

    // Renders a non-negative integer using digit PNG images (delegates to HudRenderer)
    private void drawDigits(Graphics2D g, int x, int y, int value, int digitW, int digitH) {
        hudRenderer.drawDigits(g, x, y, value, digitW, digitH);
    }

    // =========================================================================
    //  SCREEN DRAW METHODS
    // =========================================================================

    // ---- SPLASH ----
    private void drawSplash(Graphics2D g, int W, int H) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, W, H);

        Composite saved = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, splashAlpha));

        // Logo centred (it's a wide frame/panel container ~580×200 displayed)
        if (logoFull != null) {
            int lw = 580, lh = (int)(lw * (float) logoFull.getHeight() / logoFull.getWidth());
            int lx = (W - lw) / 2, ly = H / 2 - lh / 2 - 30;
            g.drawImage(logoFull, lx, ly, lw, lh, null);
        } else {
            g.setFont(new Font("Courier New", Font.BOLD, 52));
            g.setColor(new Color(0, 180, 255));
            String t = "INDUSTRIAL ZONE";
            g.drawString(t, (W - g.getFontMetrics().stringWidth(t)) / 2, H / 2 - 10);
        }

        // Subtitle
        g.setFont(hudFont.deriveFont(Font.PLAIN, 16f));
        g.setColor(new Color(180, 200, 230));
        String sub = "By Zaid Siddiqui  |  CSCU9N6";
        g.drawString(sub, (W - g.getFontMetrics().stringWidth(sub)) / 2, H / 2 + 80);

        g.setComposite(saved);

        // "Press SPACE" hint fades in after alpha is full
        if (splashAlpha >= 1f) {
            float blink = (float)(0.5 + 0.5 * Math.sin(guiTime * 4.0));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, blink));
            g.setFont(hudFont.deriveFont(Font.PLAIN, 14f));
            g.setColor(new Color(140, 180, 220));
            String hint = "PRESS  SPACE  TO  CONTINUE";
            g.drawString(hint, (W - g.getFontMetrics().stringWidth(hint)) / 2, H * 3 / 4);
            g.setComposite(saved);
        }
    }

    // ---- MAIN MENU ----
    private void drawMainMenu(Graphics2D g, int W, int H) {
        drawMenuBG(g, W, H);

        // Glow bar decors — bob vertically with opposite phase on each side
        if (decorGlowBars != null) {
            int dw = 48, dh = 120;
            int yBob = (int)(Math.sin(guiTime * 3.2f) * 7);
            g.drawImage(decorGlowBars, 16, H / 2 - dh + yBob, dw, dh * 2, null);
            g.drawImage(decorGlowBars, W - 16 - dw, H / 2 - dh - yBob, dw, dh * 2, null);
        }
        // Cable twist decor bottom-left — slow pendulum rotation around its centre
        if (decorCableTwist != null) {
            java.awt.geom.AffineTransform savedAt = g.getTransform();
            int cx = 84, cy = H - 70;
            g.rotate(Math.sin(guiTime * 0.6) * 0.20, cx, cy);
            g.drawImage(decorCableTwist, cx - 24, cy - 50, 48, 100, null);
            g.setTransform(savedAt);
        }
        // Cable coil decor bottom-right — gentle scale pulse
        if (decorCableCoil != null) {
            int pulse = (int)(Math.sin(guiTime * 2.0f) * 4);
            g.drawImage(decorCableCoil, W - 110 - pulse, H - 110 - pulse,
                        80 + pulse * 2, 90 + pulse * 2, null);
        }

        // Logo at top
        if (logoCompact != null) {
            int lw = 460, lh = (int)(lw * (float) logoCompact.getHeight() / logoCompact.getWidth());
            g.drawImage(logoCompact, (W - lw) / 2, 24, lw, lh, null);
        }

        // Menu panel background
        int panX = W / 2 - 200, panY = 170, panW = 400, panH = MENU_LABELS.length * 65 + 40;
        drawPanel(g, panX, panY, panW, panH);

        // Menu items
        int btnW = 320, btnH = 50;
        for (int i = 0; i < MENU_LABELS.length; i++) {
            int bx = W / 2 - btnW / 2;
            int by = panY + 20 + i * 65;
            boolean hovered = isInside(mouseX, mouseY, bx, by, btnW, btnH);
            int state = hovered ? 1 : 0;
            if (hovered) menuHoveredIndex = i;
            drawButton(g, bx, by, btnW, btnH, MENU_LABELS[i], state);

            // Selection arrow
            if (i == menuSelectedIndex) {
                g.setColor(new Color(0, 220, 255));
                g.setFont(hudFont.deriveFont(Font.BOLD, 18f));
                g.drawString(">", bx - 22, by + btnH / 2 + 6);
            }
        }

        // ESC hint at bottom
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.setColor(new Color(100, 110, 140));
        String hint = "UP/DOWN: Navigate   ENTER: Select   ESC: Exit";
        g.drawString(hint, (W - g.getFontMetrics().stringWidth(hint)) / 2, H - 18);
    }

    // ---- CONTROLS ----
    private void drawControls(Graphics2D g, int W, int H) {
        drawMenuBG(g, W, H);

        int panW = 560, panH = 560;
        int panX = (W - panW) / 2, panY = (H - panH) / 2 - 20;
        drawPanel(g, panX, panY, panW, panH);

        g.setFont(hudFont.deriveFont(Font.BOLD, 22f));
        g.setColor(new Color(0, 220, 255));
        String title = "CONTROLS";
        g.drawString(title, panX + (panW - g.getFontMetrics().stringWidth(title)) / 2, panY + 48);

        // Divider
        if (frmDivider != null) {
            g.drawImage(frmDivider, panX + 20, panY + 56, panW - 40, 6, null);
        } else {
            g.setColor(new Color(0, 140, 180));
            g.fillRect(panX + 20, panY + 58, panW - 40, 2);
        }

        String[][] controls = {
            { "A  /  LEFT",   "Move Left"           },
            { "D  /  RIGHT",  "Move Right"          },
            { "SPACE",        "Jump / Double Jump"   },
            { "SHIFT",        "Dash"                },
            { "CTRL",         "Shoot"               },
            { "H",            "Heal (+20 HP, 5s cd)" },
            { "1-4",          "Select Weapon Slot"   },
            { "Q  /  SCROLL", "Cycle Weapon"        },
            { "W / S",        "Climb Ladder"        },
            { "ESC",          "Pause / Resume"       },
        };

        int startY = panY + 88;
        for (int i = 0; i < controls.length; i++) {
            int row = startY + i * 42;
            // Key box
            g.setColor(new Color(25, 35, 70, 200));
            g.fillRoundRect(panX + 36, row - 22, 160, 30, 6, 6);
            g.setColor(new Color(0, 160, 220));
            g.drawRoundRect(panX + 36, row - 22, 160, 30, 6, 6);

            g.setFont(hudFont.deriveFont(Font.BOLD, 14f));
            g.setColor(new Color(0, 220, 255));
            g.drawString(controls[i][0], panX + 50, row);

            // Action
            g.setFont(hudFont.deriveFont(Font.PLAIN, 14f));
            g.setColor(new Color(210, 220, 240));
            g.drawString(controls[i][1], panX + 220, row);
        }

        // Back button
        int bx = (W - 200) / 2, by = panY + panH - 65;
        boolean hov = isInside(mouseX, mouseY, bx, by, 200, 42);
        drawButton(g, bx, by, 200, 42, "BACK", hov ? 1 : 0);

        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.setColor(new Color(100, 110, 140));
        g.drawString("ESC to go back", (W - g.getFontMetrics().stringWidth("ESC to go back")) / 2, H - 18);
    }

    // ---- CHARACTER SELECT ----
    private void drawCharSelect(Graphics2D g, int W, int H) {
        drawMenuBG(g, W, H);

        // Title panel
        int tpW = 480, tpH = 56, tpX = (W - tpW) / 2, tpY = 14;
        drawPanel(g, tpX, tpY, tpW, tpH);
        g.setFont(hudFont.deriveFont(Font.BOLD, 24f));
        g.setColor(new Color(0, 220, 255));
        String ttl = "SELECT  CHARACTER";
        g.drawString(ttl, tpX + (tpW - g.getFontMetrics().stringWidth(ttl)) / 2, tpY + 36);

        String[] names  = { "BIKER", "CYBORG", "PUNK" };
        String[] descs  = { "Fast + Agile", "Balanced", "Heavy + Strong" };
        int[]    speeds = { 90, 70, 55 };
        int[]    powers = { 55, 70, 90 };
        int[]    defs   = { 50, 70, 80 };

        int cardW    = 220, cardH    = 320;
        int selCardW = 250, selCardH = 360;
        int spacing  = 30;
        int totalW   = cardW * 2 + selCardW + spacing * 2;
        int startX   = (W - totalW) / 2;
        int baseY    = (H - selCardH) / 2 + 20;

        for (int c = 0; c < 3; c++) {
            boolean sel = (c == charSelectIndex);
            int cw = sel ? selCardW : cardW;
            int ch = sel ? selCardH : cardH;
            int cx = startX + (c > 0 ? cardW + spacing : 0) + (c > 1 ? selCardW - cardW + spacing : 0);
            int cy = baseY + (sel ? 0 : (selCardH - cardH) / 2);

            // Draw card panel
            drawPanel(g, cx, cy, cw, ch);

            // Character sprite
            if (charIdleFrames[c] != null && charIdleFrames[c][charAnimFrame[c]] != null) {
                BufferedImage frame = charIdleFrames[c][charAnimFrame[c]];
                int spW = sel ? 140 : 100, spH = (int)(spW * (float) frame.getHeight() / frame.getWidth());
                int spX = cx + (cw - spW) / 2, spY = cy + 40;

                if (!sel) {
                    // Greyscale for non-selected
                    Composite savedC = g.getComposite();
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f));
                    ColorConvertOp op = new ColorConvertOp(
                        java.awt.color.ColorSpace.getInstance(java.awt.color.ColorSpace.CS_GRAY), null);
                    BufferedImage grey = op.filter(frame, null);
                    g.drawImage(grey, spX, spY, spW, spH, null);
                    g.setComposite(savedC);
                } else {
                    g.drawImage(frame, spX, spY, spW, spH, null);
                }
            }

            // Name
            g.setFont(hudFont.deriveFont(sel ? Font.BOLD : Font.PLAIN, sel ? 18f : 14f));
            g.setColor(sel ? new Color(0, 220, 255) : new Color(130, 145, 175));
            String name = names[c];
            g.drawString(name, cx + (cw - g.getFontMetrics().stringWidth(name)) / 2,
                         cy + ch - (sel ? 165 : 100));

            // Stats (selected only)
            if (sel) {
                int statY = cy + ch - 155;
                drawStatBar(g, cx + 16, statY,      cw - 32, 18, "SPD", speeds[c], enerBars);
                drawStatBar(g, cx + 16, statY + 30, cw - 32, 18, "PWR", powers[c], hudBars);
                drawStatBar(g, cx + 16, statY + 60, cw - 32, 18, "DEF", defs[c],   enerBars);

                // Description
                g.setFont(hudFont.deriveFont(Font.PLAIN, 13f));
                g.setColor(new Color(170, 185, 215));
                g.drawString(descs[c], cx + (cw - g.getFontMetrics().stringWidth(descs[c])) / 2,
                             cy + ch - 24);

                // Selection arrow above
                g.setColor(new Color(0, 220, 255));
                g.setFont(hudFont.deriveFont(Font.BOLD, 20f));
                g.drawString("v", cx + cw / 2 - 6, cy - 10);
            }
        }

        // Navigation buttons
        int navY = H - 90;
        boolean bhov = isInside(mouseX, mouseY, W / 2 - 230, navY, 200, 46);
        boolean nhov = isInside(mouseX, mouseY, W / 2 + 30,  navY, 200, 46);
        drawButton(g, W / 2 - 230, navY, 200, 46, "< PREV", bhov ? 1 : 0);
        drawButton(g, W / 2 + 30,  navY, 200, 46, "NEXT >", nhov ? 1 : 0);
        boolean selHov = isInside(mouseX, mouseY, W / 2 - 90, navY - 56, 180, 46);
        drawButton(g, W / 2 - 90, navY - 56, 180, 46, "SELECT", selHov ? 1 : 0);

        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.setColor(new Color(100, 110, 140));
        g.drawString("A/D: Switch   ENTER: Confirm   ESC: Back",
                     (W - g.getFontMetrics().stringWidth("A/D: Switch   ENTER: Confirm   ESC: Back")) / 2, H - 18);
    }

    // Mini stat bar for char select
    private void drawStatBar(Graphics2D g, int x, int y, int w, int h,
                              String lbl, int val, BufferedImage[] bars) {
        g.setFont(hudFont.deriveFont(Font.PLAIN, 12f));
        g.setColor(new Color(160, 180, 210));
        g.drawString(lbl, x, y + h - 2);
        int bx = x + 40, bw = w - 44;
        hudRenderer.drawBarImage(g, val, 100, bars, bx, y, bw, h);
    }

    // ---- LEVEL SELECT ----
    private void drawLevelSelect(Graphics2D g, int W, int H) {
        drawMenuBG(g, W, H);

        // Title
        int tpW = 440, tpH = 54, tpX = (W - tpW) / 2;
        drawPanel(g, tpX, 14, tpW, tpH);
        g.setFont(hudFont.deriveFont(Font.BOLD, 22f));
        g.setColor(new Color(0, 220, 255));
        String ttl = "SELECT  LEVEL";
        g.drawString(ttl, tpX + (tpW - g.getFontMetrics().stringWidth(ttl)) / 2, 46);

        String[] lvlNames = { "INDUSTRIAL ZONE", "POWER STATION" };
        String[] lvlDescs = { "Classic factory floors.\nMedium difficulty.", "Electric hazards.\nHigh difficulty." };
        Color[] lvlCols   = { new Color(60, 120, 180), new Color(180, 80, 40) };

        int cardW = 300, cardH = 380, spacing = 60;
        int totalW = cardW * 2 + spacing, startX = (W - totalW) / 2;

        // Slide animation offset
        int slideOffset = (int)(levelSlideAnim * 80);

        for (int i = 0; i < 2; i++) {
            boolean sel = (i == levelSelectIndex);
            int cx = startX + i * (cardW + spacing) + (i == 0 ? slideOffset : -slideOffset);
            int cy = (H - cardH) / 2 + (sel ? 0 : 20);
            drawPanel(g, cx, cy, cardW, cardH);

            // Colour header
            g.setColor(lvlCols[i]);
            g.fillRect(cx + 42, cy + 42, cardW - 84, 80);

            // Level number
            g.setFont(hudFont.deriveFont(Font.BOLD, 48f));
            g.setColor(Color.WHITE);
            String num = "0" + (i + 1);
            g.drawString(num, cx + (cardW - g.getFontMetrics().stringWidth(num)) / 2, cy + 108);

            // Background thumbnail preview (small)
            BufferedImage[] previewLayers = (i == 0) ? bgLayers1 : bgLayers2;
            if (previewLayers != null && previewLayers.length > 1 && previewLayers[1] != null) {
                int pw = cardW - 84, ph = 80;
                g.drawImage(previewLayers[1], cx + 42, cy + 42, pw, ph, null);
            }

            // Name
            g.setFont(hudFont.deriveFont(Font.BOLD, 16f));
            g.setColor(sel ? new Color(0, 220, 255) : new Color(160, 175, 200));
            String name = lvlNames[i];
            g.drawString(name, cx + (cardW - g.getFontMetrics().stringWidth(name)) / 2, cy + 200);

            // Description
            g.setFont(hudFont.deriveFont(Font.PLAIN, 13f));
            g.setColor(new Color(170, 185, 210));
            String[] lines = lvlDescs[i].split("\n");
            for (int li = 0; li < lines.length; li++)
                g.drawString(lines[li], cx + (cardW - g.getFontMetrics().stringWidth(lines[li])) / 2,
                             cy + 228 + li * 22);

            // Difficulty
            g.setFont(hudFont.deriveFont(Font.BOLD, 13f));
            g.setColor(i == 0 ? new Color(80, 200, 80) : new Color(220, 100, 60));
            String diff = i == 0 ? "MEDIUM" : "HARD";
            g.drawString(diff, cx + (cardW - g.getFontMetrics().stringWidth(diff)) / 2, cy + 285);

            // Selection indicator
            if (sel) {
                g.setColor(new Color(0, 200, 255, 160));
                g.setStroke(new BasicStroke(2f));
                g.drawRect(cx + 2, cy + 2, cardW - 4, cardH - 4);
                g.setStroke(new BasicStroke(1f));
            }
        }

        // Buttons
        int navY = H - 80;
        boolean shov = isInside(mouseX, mouseY, W / 2 - 90, navY, 180, 46);
        drawButton(g, W / 2 - 90, navY, 180, 46, "START GAME", shov ? 1 : 0);

        boolean bhov = isInside(mouseX, mouseY, W / 2 - 300, navY, 160, 46);
        drawButton(g, W / 2 - 300, navY, 160, 46, "< BACK", bhov ? 1 : 0);

        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.setColor(new Color(100, 110, 140));
        g.drawString("A/D: Switch   ENTER: Play   ESC: Back",
                     (W - g.getFontMetrics().stringWidth("A/D: Switch   ENTER: Play   ESC: Back")) / 2, H - 18);
    }

    // ---- CREDITS ----
    private void drawCredits(Graphics2D g, int W, int H) {
        drawMenuBG(g, W, H);

        // Dark overlay
        g.setColor(new Color(0, 0, 0, 140));
        g.fillRect(0, 0, W, H);

        // Decor
        if (decorRibbon != null)
            g.drawImage(decorRibbon, W - 80, 30, 60, 120, null);
        if (decorCablePlug != null)
            g.drawImage(decorCablePlug, 20, H - 100, 50, 80, null);

        // Clip to visible area
        java.awt.Shape savedClip = g.getClip();
        g.setClip(60, 60, W - 120, H - 120);

        int sx = W / 2;
        int sy = (int) creditsScrollY;

        // Logo
        if (logoMinimal != null) {
            int lw = 300, lh = (int)(lw * (float) logoMinimal.getHeight() / logoMinimal.getWidth());
            g.drawImage(logoMinimal, sx - lw / 2, sy, lw, lh, null);
            sy += lh + 30;
        }

        String[][] sections = {
            { "DEVELOPER", "Mohammad Zaid Siddiqui [3359098]" },
            { "FRAMEWORK", "CSCU9N6 Game2D Framework" },
            { "ART ASSETS", "Industrial Zone Pixel Art Pack" },
            { "AUDIO", "Free Chiptune Tracks" },
            { "UNIVERSITY", "University of Stirling" },
            { "MODULE", "CSCU9N6 — 2D Graphics Programming" },
            { "YEAR", "April 2026" },
        };

        for (String[] section : sections) {
            // Section header
            g.setFont(hudFont.deriveFont(Font.BOLD, 18f));
            g.setColor(new Color(0, 200, 255));
            String header = section[0];
            g.drawString(header, sx - g.getFontMetrics().stringWidth(header) / 2, sy);
            sy += 28;

            // Divider
            if (frmDivider != null)
                g.drawImage(frmDivider, sx - 130, sy, 260, 5, null);
            else {
                g.setColor(new Color(0, 100, 140));
                g.fillRect(sx - 130, sy, 260, 2);
            }
            sy += 12;

            g.setFont(hudFont.deriveFont(Font.PLAIN, 16f));
            g.setColor(new Color(210, 220, 240));
            for (int k = 1; k < section.length; k++) {
                String line = section[k];
                g.drawString(line, sx - g.getFontMetrics().stringWidth(line) / 2, sy);
                sy += 26;
            }
            sy += 30;
        }

        // Thank you
        g.setFont(hudFont.deriveFont(Font.BOLD, 26f));
        g.setColor(new Color(255, 200, 60));
        String ty = "THANK  YOU  FOR  PLAYING !";
        g.drawString(ty, sx - g.getFontMetrics().stringWidth(ty) / 2, sy + 20);

        g.setClip(savedClip);

        // Bottom fade gradient
        GradientPaint fade = new GradientPaint(0, H - 80, new Color(0, 0, 0, 0), 0, H, new Color(0, 0, 0, 200));
        g.setPaint(fade);
        g.fillRect(0, H - 80, W, 80);
        g.setPaint(null);

        // Back button
        boolean bhov = isInside(mouseX, mouseY, W / 2 - 90, H - 55, 180, 38);
        drawButton(g, W / 2 - 90, H - 55, 180, 38, "BACK", bhov ? 1 : 0);

        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.setColor(new Color(100, 110, 140));
        g.drawString("SPACE: Speed Up   ESC: Back", 14, H - 8);
    }

    // ---- PAUSE OVERLAY ----
    private void drawPauseOverlay(Graphics2D g, int W, int H) {
        // Dark dim
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, W, H);

        int panW = 380, panH = 360;
        int panX = (W - panW) / 2, panY = (H - panH) / 2;
        drawPanel(g, panX, panY, panW, panH);

        // Title
        g.setFont(hudFont.deriveFont(Font.BOLD, 30f));
        g.setColor(new Color(0, 220, 255));
        String title = "PAUSED";
        g.drawString(title, panX + (panW - g.getFontMetrics().stringWidth(title)) / 2, panY + 52);

        // Divider
        if (frmDivider != null)
            g.drawImage(frmDivider, panX + 20, panY + 60, panW - 40, 5, null);

        // Pause menu options
        String[] pauseItems = { "RESUME", "SETTINGS", "CONTROLS", "QUIT TO MENU" };
        int btnW = 280, btnH = 46;
        for (int i = 0; i < pauseItems.length; i++) {
            int bx = panX + (panW - btnW) / 2;
            int by = panY + 82 + i * 60;
            boolean hov = isInside(mouseX, mouseY, bx, by, btnW, btnH)
                          || i == pauseMenuIndex;
            drawButton(g, bx, by, btnW, btnH, pauseItems[i], hov ? 1 : 0);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.setColor(new Color(100, 110, 140));
        g.drawString("ESC: Resume", panX + 10, panY + panH - 10);
    }

    // ---- SETTINGS OVERLAY ----
    private void drawSettingsOverlay(Graphics2D g, int W, int H) {
        int panW = 440, panH = 380;
        int panX = (W - panW) / 2, panY = (H - panH) / 2;

        // Dim previous (pause is behind, already drawn)
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, W, H);

        drawPanel(g, panX, panY, panW, panH);

        g.setFont(hudFont.deriveFont(Font.BOLD, 24f));
        g.setColor(new Color(0, 220, 255));
        String ttl = "SETTINGS";
        g.drawString(ttl, panX + (panW - g.getFontMetrics().stringWidth(ttl)) / 2, panY + 50);

        if (frmDivider != null)
            g.drawImage(frmDivider, panX + 20, panY + 58, panW - 40, 5, null);

        // Music volume
        drawSlider(g, panX + 40, panY + 90,  panW - 80, "MUSIC VOL", settingsMusicVol, settingsMusicOn, settingsIndex == 0);
        // SFX volume
        drawSlider(g, panX + 40, panY + 160, panW - 80, "SFX VOL",   settingsSfxVol,   settingsSfxOn,   settingsIndex == 1);
        // Toggles
        drawToggle(g, panX + 40, panY + 240, panW - 80, "MUSIC",     settingsMusicOn,  settingsIndex == 2);
        drawToggle(g, panX + 40, panY + 290, panW - 80, "SFX",       settingsSfxOn,    settingsIndex == 3);

        // Back button
        int bx = panX + (panW - 160) / 2, by = panY + panH - 68;
        boolean bhov = isInside(mouseX, mouseY, bx, by, 160, 44);
        drawButton(g, bx, by, 160, 44, "BACK", bhov ? 1 : 0);

        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.setColor(new Color(100, 110, 140));
        g.drawString("UP/DOWN: Navigate   LEFT/RIGHT: Adjust   ESC: Back",
                     panX + 14, panY + panH - 10);
    }

    private void drawSlider(Graphics2D g, int x, int y, int w,
                             String label, float val, boolean on, boolean active) {
        g.setFont(hudFont.deriveFont(Font.PLAIN, 14f));
        g.setColor(active ? new Color(0, 220, 255) : new Color(160, 180, 210));
        g.drawString(label, x, y + 16);

        int trackX = x + 130, trackW = w - 140, trackH = 8;
        // Track background
        g.setColor(new Color(20, 30, 60));
        g.fillRoundRect(trackX, y + 6, trackW, trackH, 4, 4);
        // Fill
        int fillW = (int)(trackW * (on ? val : 0f));
        g.setColor(on ? new Color(0, 180, 240) : new Color(80, 90, 110));
        g.fillRoundRect(trackX, y + 6, fillW, trackH, 4, 4);
        // Thumb
        int thumbX = trackX + fillW - 6;
        g.setColor(active ? Color.WHITE : new Color(160, 175, 200));
        g.fillOval(thumbX, y + 2, 14, 16);
        g.setColor(new Color(0, 120, 180));
        g.drawOval(thumbX, y + 2, 14, 16);

        // Percent
        g.setFont(hudFont.deriveFont(Font.PLAIN, 12f));
        g.setColor(new Color(170, 185, 215));
        g.drawString(on ? (int)(val * 100) + "%" : "OFF", trackX + trackW + 8, y + 16);
    }

    private void drawToggle(Graphics2D g, int x, int y, int w,
                             String label, boolean on, boolean active) {
        g.setFont(hudFont.deriveFont(Font.PLAIN, 14f));
        g.setColor(active ? new Color(0, 220, 255) : new Color(160, 180, 210));
        g.drawString(label + ":", x, y + 18);

        int tx = x + 130;
        g.setColor(on ? new Color(0, 180, 60) : new Color(120, 40, 40));
        g.fillRoundRect(tx, y + 2, 54, 22, 12, 12);
        g.setColor(active ? Color.WHITE : new Color(210, 220, 240));
        g.fillOval(on ? tx + 32 : tx + 2, y + 4, 18, 18);

        g.setFont(hudFont.deriveFont(Font.BOLD, 13f));
        g.setColor(on ? new Color(100, 240, 100) : new Color(240, 100, 100));
        g.drawString(on ? "ON" : "OFF", tx + 64, y + 18);
    }

    // ---- LEVEL COMPLETE OVERLAY ----
    private void drawLevelCompleteOverlay(Graphics2D g, int W, int H) {
        float alpha = Math.min(1f, levelCompleteTimer * 1.5f);
        Composite saved = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha * 0.82f));
        g.setColor(new Color(3, 8, 22));
        g.fillRect(0, 0, W, H);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.min(1f, alpha)));

        int cx = W / 2;

        // ── Panel background ──
        int panW = 580, panH = 380;
        int panX = cx - panW / 2, panY = H / 2 - panH / 2 - 20;
        drawPanel(g, panX, panY, panW, panH);

        // ── Title ──
        g.setFont(hudFont != null ? hudFont.deriveFont(Font.BOLD, 34f) : new Font("Consolas", Font.BOLD, 34));
        String title = activeLevel.getCompletionTitle();
        FontMetrics fm = g.getFontMetrics();
        g.setColor(new Color(0, 255, 160));
        g.drawString(title, cx - fm.stringWidth(title) / 2, panY + 46);

        // Divider
        if (frmDivider != null)
            g.drawImage(frmDivider, panX + 20, panY + 54, panW - 40, 5, null);

        // ── Story / narrative lines ──
        String[] story = activeLevel.getStoryLines();
        if (story.length > 0) {
            g.setFont(hudFont != null ? hudFont.deriveFont(Font.PLAIN, 16f) : new Font("Consolas", Font.PLAIN, 16));
            fm = g.getFontMetrics();
            int lineH = fm.getHeight() + 4;
            int storyY = panY + 78;
            for (int i = 0; i < story.length; i++) {
                // Reveal lines one by one as timer advances (0.4 s per line)
                float revealTime = 0.3f + i * 0.35f;
                if (levelCompleteTimer < revealTime) break;
                float lineAlpha = Math.min(1f, (levelCompleteTimer - revealTime) / 0.25f);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lineAlpha));
                // Leading arrow accent
                g.setColor(new Color(0, 200, 130));
                g.drawString("\u25BA", panX + 22, storyY + i * lineH);
                // Line text
                g.setColor(new Color(190, 215, 255));
                g.drawString(story[i], panX + 44, storyY + i * lineH);
            }
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.min(1f, alpha)));
        }

        // ── Stats ──
        int statsY = panY + panH - 110;
        if (frmDivider != null)
            g.drawImage(frmDivider, panX + 20, statsY - 8, panW - 40, 5, null);

        g.setFont(hudFont != null ? hudFont.deriveFont(Font.PLAIN, 16f) : new Font("Consolas", Font.PLAIN, 16));
        fm = g.getFontMetrics();
        g.setColor(new Color(160, 210, 255));
        long elapsed = System.currentTimeMillis() - startTime;
        String scoreStr = "SCORE:  " + score;
        String killStr  = "KILLS:  " + gameOverEnemiesKilled;
        String timeStr  = "TIME:   " + String.format("%d:%02d", elapsed / 60000, (elapsed / 1000) % 60);
        int col1 = panX + 30, col2 = panX + panW / 2 + 20;
        g.drawString(scoreStr, col1,  statsY + 18);
        g.drawString(killStr,  col2,  statsY + 18);
        g.drawString(timeStr,  col1,  statsY + 40);

        // ── Prompt ──
        if (levelCompleteTimer > 1.5f) {
            String prompt = activeLevel.getNextLevelPrompt();
            g.setFont(hudFont != null ? hudFont.deriveFont(Font.BOLD, 15f) : new Font("Consolas", Font.BOLD, 15));
            fm = g.getFontMetrics();
            float blink = (float)(0.5 + 0.5 * Math.sin(guiTime * 4));
            g.setColor(new Color(0, 200, 240, (int)(blink * 255)));
            g.drawString(prompt, cx - fm.stringWidth(prompt) / 2, panY + panH - 18);
        }

        g.setComposite(saved);
    }

    // ---- GAME OVER SCREEN ----
    private void drawGameOverScreen(Graphics2D g, int W, int H) {
        // Dark overlay with alpha fade-in
        Composite saved = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                        Math.min(1f, gameOverAlpha * 0.85f)));
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, W, H);
        g.setComposite(saved);

        if (gameOverAlpha < 0.1f) return;

        Composite fadeComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                              Math.min(1f, gameOverAlpha));
        g.setComposite(fadeComp);

        int panW = 520, panH = 480;
        int panX = (W - panW) / 2, panY = (H - panH) / 2 - 10;
        drawPanel(g, panX, panY, panW, panH);

        // GAME OVER text (typewriter effect)
        String fullText = "GAME OVER";
        String shown    = fullText.substring(0, Math.min(gameOverCharsShown, fullText.length()));
        g.setFont(hudFont.deriveFont(Font.BOLD, 40f));
        g.setColor(new Color(220, 50, 50));
        g.drawString(shown, panX + (panW - g.getFontMetrics().stringWidth(fullText)) / 2, panY + 68);

        // Divider
        if (frmDivider != null)
            g.drawImage(frmDivider, panX + 20, panY + 78, panW - 40, 5, null);

        // Stats section
        int sy = panY + 110;
        g.setFont(hudFont.deriveFont(Font.PLAIN, 15f));
        g.setColor(new Color(170, 185, 215));

        // Score with digit images
        g.drawString("SCORE", panX + 50, sy + 18);
        drawDigits(g, panX + 200, sy - 2, (int) gameOverScoreTally, 28, 36);
        sy += 54;

        // Enemies killed
        g.drawString("ENEMIES DEFEATED", panX + 50, sy + 18);
        drawDigits(g, panX + 290, sy - 2, gameOverEnemiesKilled, 28, 36);
        sy += 54;

        // Time
        long sec = gameOverTime / 1000;
        g.drawString("TIME", panX + 50, sy + 18);
        g.setFont(hudFont.deriveFont(Font.BOLD, 22f));
        g.setColor(new Color(200, 215, 240));
        g.drawString(String.format("%02d : %02d", sec / 60, sec % 60), panX + 200, sy + 20);
        sy += 54;

        // Divider
        if (frmDivider != null)
            g.drawImage(frmDivider, panX + 20, sy, panW - 40, 5, null);
        sy += 20;

        // Buttons: Retry, Menu, Exit
        String[] goLabels = { "RETRY", "MAIN MENU", "EXIT" };
        int btnW = 140, btnH = 46, btnSpacing = 16;
        int totalBtnW = goLabels.length * btnW + (goLabels.length - 1) * btnSpacing;
        int bx0 = panX + (panW - totalBtnW) / 2;
        for (int i = 0; i < goLabels.length; i++) {
            int bx = bx0 + i * (btnW + btnSpacing);
            boolean hov = isInside(mouseX, mouseY, bx, sy, btnW, btnH) || i == gameOverMenuIndex;
            drawButton(g, bx, sy, btnW, btnH, goLabels[i], hov ? 1 : 0);
        }

        g.setComposite(saved);
    }

    // =========================================================================
    //  INPUT HANDLERS
    // =========================================================================

    private void handleClick(int x, int y, int button) {
        vfx.emitSparks(x, y);  // click spark burst at the cursor position
        switch (currentScreen) {
            case MAIN_MENU: {
                String[] items = MENU_LABELS;
                int btnW = 320, btnH = 50, panY = 170;
                for (int i = 0; i < items.length; i++) {
                    int bx = getWidth() / 2 - btnW / 2, by = panY + 20 + i * 65;
                    if (isInside(x, y, bx, by, btnW, btnH)) {
                        activateMenuItem(i);
                        return;
                    }
                }
                break;
            }
            case CONTROLS:
                goTo(GameScreen.MAIN_MENU);
                break;
            case CHARACTER_SELECT: {
                int W = getWidth(), navY = H_NAV();
                if (isInside(x, y, W / 2 - 230, navY, 200, 46)) {
                    charSelectIndex = (charSelectIndex + 2) % 3;
                } else if (isInside(x, y, W / 2 + 30, navY, 200, 46)) {
                    charSelectIndex = (charSelectIndex + 1) % 3;
                } else if (isInside(x, y, W / 2 - 90, navY - 56, 180, 46)) {
                    goTo(GameScreen.LEVEL_SELECT);
                }
                break;
            }
            case LEVEL_SELECT: {
                int W = getWidth(), navY = H_NAV();
                if (isInside(x, y, W / 2 - 90, navY, 180, 46)) {
                    startGame();
                } else if (isInside(x, y, W / 2 - 300, navY, 160, 46)) {
                    goTo(GameScreen.CHARACTER_SELECT);
                } else {
                    // Click on a level card
                    int cardW = 300, spacing = 60;
                    int totalW = cardW * 2 + spacing, startX = (W - totalW) / 2;
                    for (int i = 0; i < 2; i++) {
                        int cx = startX + i * (cardW + spacing);
                        if (isInside(x, y, cx, 0, cardW, getHeight())) {
                            levelSelectIndex = i;
                            levelSlideAnim   = (i == 0) ? 0.4f : -0.4f;
                        }
                    }
                }
                break;
            }
            case CREDITS:
                goTo(GameScreen.MAIN_MENU);
                break;
            case PAUSE: {
                int W = getWidth(), H = getHeight();
                int panW = 380, panH = 360, panX = (W - panW) / 2, panY = (H - panH) / 2;
                int btnW = 280, btnH = 46;
                String[] items = { "RESUME", "SETTINGS", "CONTROLS", "QUIT TO MENU" };
                for (int i = 0; i < items.length; i++) {
                    int bx = panX + (panW - btnW) / 2, by = panY + 82 + i * 60;
                    if (isInside(x, y, bx, by, btnW, btnH)) {
                        activatePauseItem(i);
                        return;
                    }
                }
                break;
            }
            case SETTINGS: {
                int W = getWidth(), H = getHeight();
                int panW = 440, panH = 380;
                int panX = (W - panW) / 2, panY = (H - panH) / 2;
                int bx = panX + (panW - 160) / 2, by = panY + panH - 68;
                if (isInside(x, y, bx, by, 160, 44)) goTo(GameScreen.PAUSE);
                break;
            }
            case GAME_OVER: {
                int W = getWidth(), H = getHeight();
                int panW = 520, panH = 480;
                int panX = (W - panW) / 2, panY = (H - panH) / 2 - 10;
                int sy = panY + 110 + 54 * 3 + 20;
                String[] goLabels = { "RETRY", "MAIN MENU", "EXIT" };
                int btnW = 140, btnH = 46, btnSp = 16;
                int totalBW = goLabels.length * btnW + (goLabels.length - 1) * btnSp;
                int bx0 = panX + (panW - totalBW) / 2;
                for (int i = 0; i < goLabels.length; i++) {
                    int bx = bx0 + i * (btnW + btnSp);
                    if (isInside(x, y, bx, sy, btnW, btnH)) {
                        activateGameOverItem(i);
                        return;
                    }
                }
                break;
            }
            default: break;
        }
    }

    private void handleWheel(int rot) {
        switch (currentScreen) {
            case GAMEPLAY:
                if (player != null) {
                    if (rot < 0) player.cycleWeaponPrev();
                    else          player.cycleWeaponNext();
                }
                break;
            case CREDITS:
                creditsScrollY -= rot * 30;
                break;
            case SETTINGS:
                settingsIndex = Math.max(0, Math.min(3, settingsIndex + rot));
                break;
            default: break;
        }
    }

    // ---- Menu action helpers ----
    private void activateMenuItem(int idx) {
        switch (idx) {
            case 0: goTo(GameScreen.CHARACTER_SELECT); break;
            case 1: goTo(GameScreen.CONTROLS);         break;
            case 2: goTo(GameScreen.CREDITS);          break;
            case 3: System.exit(0);                    break;
        }
    }

    private void activatePauseItem(int idx) {
        switch (idx) {
            case 0: goTo(GameScreen.GAMEPLAY);          break;
            case 1: goTo(GameScreen.SETTINGS);          break;
            case 2: goTo(GameScreen.CONTROLS);          break;
            case 3:
                paused = false; gameOver = false; player = null;
                goTo(GameScreen.MAIN_MENU);
                break;
        }
    }

    private void activateGameOverItem(int idx) {
        switch (idx) {
            case 0: startGame();              break;
            case 1:
                player = null;
                goTo(GameScreen.MAIN_MENU);
                break;
            case 2: System.exit(0);           break;
        }
    }

    // Convenience: screen height minus nav bar offset
    private int H_NAV() { return Math.max(getHeight(), SCREEN_H) - 90; }
    private boolean isInside(int mx, int my, int x, int y, int w, int h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    // =========================================================================
    //  INPUT (KEY)
    // =========================================================================
    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        switch (currentScreen) {
            // ---- SPLASH ----
            case SPLASH:
                if (k == KeyEvent.VK_SPACE || k == KeyEvent.VK_ESCAPE)
                    goTo(GameScreen.MAIN_MENU);
                break;

            // ---- MAIN MENU ----
            case MAIN_MENU:
                if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W)
                    menuSelectedIndex = (menuSelectedIndex + MENU_LABELS.length - 1) % MENU_LABELS.length;
                else if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S)
                    menuSelectedIndex = (menuSelectedIndex + 1) % MENU_LABELS.length;
                else if (k == KeyEvent.VK_ENTER || k == KeyEvent.VK_SPACE)
                    activateMenuItem(menuSelectedIndex);
                else if (k == KeyEvent.VK_ESCAPE)
                    System.exit(0);
                break;

            // ---- CONTROLS ----
            case CONTROLS:
                if (k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_BACK_SPACE)
                    goTo(prevScreen == GameScreen.PAUSE ? GameScreen.PAUSE : GameScreen.MAIN_MENU);
                break;

            // ---- CHARACTER SELECT ----
            case CHARACTER_SELECT:
                if (k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT)
                    charSelectIndex = (charSelectIndex + 2) % 3;
                else if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT)
                    charSelectIndex = (charSelectIndex + 1) % 3;
                else if (k == KeyEvent.VK_ENTER || k == KeyEvent.VK_SPACE)
                    goTo(GameScreen.LEVEL_SELECT);
                else if (k == KeyEvent.VK_ESCAPE)
                    goTo(GameScreen.MAIN_MENU);
                break;

            // ---- LEVEL SELECT ----
            case LEVEL_SELECT:
                if (k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) {
                    levelSelectIndex = 0; levelSlideAnim = 0.5f;
                } else if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) {
                    levelSelectIndex = 1; levelSlideAnim = -0.5f;
                } else if (k == KeyEvent.VK_ENTER || k == KeyEvent.VK_SPACE) {
                    startGame();
                } else if (k == KeyEvent.VK_ESCAPE) {
                    goTo(GameScreen.CHARACTER_SELECT);
                }
                break;

            // ---- CREDITS ----
            case CREDITS:
                if (k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_BACK_SPACE)
                    goTo(GameScreen.MAIN_MENU);
                else if (k == KeyEvent.VK_SPACE)
                    creditsSpeedUp = true;
                break;

            // ---- GAMEPLAY ----
            case GAMEPLAY:
                // Cinematic scene: SPACE advances or closes
                if (activeCinematic != null) {
                    if (k == KeyEvent.VK_SPACE) {
                        if (!cinematicFullText && cinematicCharsShown < activeCinematic.dialog.length()) {
                            cinematicFullText = true; // reveal all text
                        } else {
                            activeCinematic = null; // close scene
                        }
                    }
                    break;
                }
                PlayerBase.setKeyPressed(k, true);
                if (k == KeyEvent.VK_ESCAPE) {
                    paused = true; goTo(GameScreen.PAUSE);
                } else if (k == KeyEvent.VK_H) {
                    // Heal — §15.2: bell_on_the_door SFX on heal
                    if (player != null && player.isAlive()) {
                        boolean healed = player.tryHeal();
                        if (healed) audioManager.playSoundEffect("bell_on_the_door");
                    }
                } else if (k == KeyEvent.VK_1) {
                    if (player != null) player.switchWeaponSlot(0);
                } else if (k == KeyEvent.VK_2) {
                    if (player != null) player.switchWeaponSlot(1);
                } else if (k == KeyEvent.VK_3) {
                    if (player != null) player.switchWeaponSlot(2);
                } else if (k == KeyEvent.VK_4) {
                    if (player != null) player.switchWeaponSlot(3);
                } else if (k == KeyEvent.VK_Q) {
                    if (player != null) player.cycleWeaponNext();
                } else if (k == KeyEvent.VK_E) {
                    // Interact with nearby chests
                    for (AnimatedObject ao : animatedObjects) {
                        if (ao.isActive() && ao.getType() == AnimatedObject.ObjType.CHEST
                            && ao.overlaps(player.getX(), player.getY(), 64, 64)) {
                            int pts = ao.interact();
                            if (pts > 0) {
                                score += pts;
                                vfx.emitHitSparks(ao.getX() + 24, ao.getY());
                                audioManager.playSoundEffect("unlocked_chest");
                            }
                        }
                    }
                }
                break;

            // ---- PAUSE ----
            case PAUSE:
                if (k == KeyEvent.VK_ESCAPE) {
                    paused = false; goTo(GameScreen.GAMEPLAY);
                } else if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W)
                    pauseMenuIndex = (pauseMenuIndex + 3) % 4;
                else if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S)
                    pauseMenuIndex = (pauseMenuIndex + 1) % 4;
                else if (k == KeyEvent.VK_ENTER)
                    activatePauseItem(pauseMenuIndex);
                break;

            // ---- SETTINGS ----
            case SETTINGS:
                if (k == KeyEvent.VK_ESCAPE)
                    goTo(GameScreen.PAUSE);
                else if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W)
                    settingsIndex = Math.max(0, settingsIndex - 1);
                else if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S)
                    settingsIndex = Math.min(3, settingsIndex + 1);
                else if (k == KeyEvent.VK_LEFT) {
                    if (settingsIndex == 0)      settingsMusicVol = Math.max(0f, settingsMusicVol - 0.05f);
                    else if (settingsIndex == 1) settingsSfxVol   = Math.max(0f, settingsSfxVol   - 0.05f);
                } else if (k == KeyEvent.VK_RIGHT) {
                    if (settingsIndex == 0)      settingsMusicVol = Math.min(1f, settingsMusicVol + 0.05f);
                    else if (settingsIndex == 1) settingsSfxVol   = Math.min(1f, settingsSfxVol   + 0.05f);
                } else if (k == KeyEvent.VK_ENTER || k == KeyEvent.VK_SPACE) {
                    if (settingsIndex == 2) settingsMusicOn = !settingsMusicOn;
                    else if (settingsIndex == 3) settingsSfxOn = !settingsSfxOn;
                }
                break;

            // ---- GAME OVER ----
            case GAME_OVER:
                if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A)
                    gameOverMenuIndex = Math.max(0, gameOverMenuIndex - 1);
                else if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D)
                    gameOverMenuIndex = Math.min(2, gameOverMenuIndex + 1);
                else if (k == KeyEvent.VK_ENTER || k == KeyEvent.VK_SPACE)
                    activateGameOverItem(gameOverMenuIndex);
                break;

            // ---- LEVEL COMPLETE ----
            case LEVEL_COMPLETE:
                if ((k == KeyEvent.VK_ENTER || k == KeyEvent.VK_SPACE) && levelCompleteTimer > 1.5f) {
                    if (activeLevel.hasNextLevel()) {
                        // Advance to next level
                        levelComplete = false;
                        levelSelectIndex = 1;
                        startGame();
                    } else {
                        // Game won — return to main menu
                        levelComplete = false;
                        goTo(GameScreen.MAIN_MENU);
                    }
                }
                break;

            default: break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if (currentScreen == GameScreen.GAMEPLAY)
            PlayerBase.setKeyPressed(k, false);
        if (k == KeyEvent.VK_SPACE && currentScreen == GameScreen.CREDITS)
            creditsSpeedUp = false;
        // Do NOT call super.keyReleased() — prevents ESC from quitting the JFrame
    }

    // =========================================================================
    //  CURSOR  — context-sensitive image rendered on top of every frame
    // =========================================================================

    /** Draws the correct cursor image for the current screen/context. */
    private void drawCursor(Graphics2D g) {
        if (cursorImgs == null) return;
        // choose cursor index based on active screen
        switch (currentScreen) {
            case GAMEPLAY:
            case PAUSE:
                activeCursorIdx = 1;  // crosshair / aim cursor during gameplay
                break;
            case SETTINGS:
                activeCursorIdx = 2;  // adjust cursor for sliders
                break;
            default:
                // show hover cursor when positioned over a menu button
                activeCursorIdx = (menuHoveredIndex >= 0) ? 3 : 0;
                break;
        }
        // reset hover tracker each frame so drawMainMenu can re-set it
        menuHoveredIndex = -1;

        int idx = Math.min(activeCursorIdx, cursorImgs.length - 1);
        BufferedImage cur = cursorImgs[idx];
        // fall back to index 0 if the chosen slot is null
        if (cur == null) cur = cursorImgs[0];
        if (cur != null) g.drawImage(cur, mouseX, mouseY, 22, 26, null);
    }

}
