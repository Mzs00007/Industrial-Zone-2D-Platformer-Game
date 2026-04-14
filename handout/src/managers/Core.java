/*
 * Decompiled with CFR 0.152.
 */
package managers;

import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Core {
    public static final Logger logger = new Logger();

    public static class Logger {
        public void info(String string) {
            System.out.println("[INFO] " + string);
        }

        public void warn(String string) {
            System.out.println("[WARN] " + string);
        }

        public void error(String string) {
            System.err.println("[ERROR] " + string);
        }

        public void debug(String string) {
            System.out.println("[DEBUG] " + string);
        }
    }

    public static class GameAnimationIntegrationComplete {
    }

    public static class LevelCoordinator {
        private Level currentLevel = Level.INDUSTRIAL_ZONE_L1;
        private Map<String, Object> currentLevelSprites = new HashMap<String, Object>();

        public Level getCurrentLevel() {
            return this.currentLevel;
        }

        public void switchLevel(Level level) {
            this.currentLevel = level;
        }

        public void update(long l) {
        }

        public void render(Graphics2D graphics2D) {
        }

        public List<String> getLoadedSprites() {
            return new ArrayList<String>(this.currentLevelSprites.keySet());
        }

        public static enum Level {
            INDUSTRIAL_ZONE_L1("Level 1: Industrial Zone", "level_1"),
            POWER_STATION_L2("Level 2: Power Station", "level_2");

            public final String displayName;
            public final String levelId;

            private Level(String string2, String string3) {
                this.displayName = string2;
                this.levelId = string3;
            }
        }
    }

    public static class GameplayEnhancementSystem {
        public static final int DIFFICULTY_EASY = 1;
        public static final int DIFFICULTY_NORMAL = 2;
        public static final int DIFFICULTY_HARD = 3;
        private int currentDifficulty = 2;

        public void setDifficulty(int n) {
            this.currentDifficulty = n;
        }

        public int getDifficulty() {
            return this.currentDifficulty;
        }

        public float getDamageMultiplier() {
            return 1.0f;
        }

        public float getHealthMultiplier() {
            return 1.0f;
        }
    }

    public static class EnhancedInputHandler
    extends KeyAdapter
    implements MouseListener,
    MouseMotionListener {
        private Set<Integer> keysPressed = new HashSet<Integer>();
        private Set<Integer> keysJustPressed = new HashSet<Integer>();
        private Set<Integer> keysJustReleased = new HashSet<Integer>();
        private int mouseX = 0;
        private int mouseY = 0;
        private boolean leftMousePressed = false;
        private boolean rightMousePressed = false;
        private boolean leftMouseJustPressed = false;
        private long lastUpdateTime = System.currentTimeMillis();

        public void update(long l) {
            this.keysJustPressed.clear();
            this.keysJustReleased.clear();
            this.leftMouseJustPressed = false;
            this.lastUpdateTime = System.currentTimeMillis();
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int n = keyEvent.getKeyCode();
            if (!this.keysPressed.contains(n)) {
                this.keysJustPressed.add(n);
            }
            this.keysPressed.add(n);
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            int n = keyEvent.getKeyCode();
            this.keysPressed.remove(n);
            this.keysJustReleased.add(n);
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() == 1) {
                this.leftMouseJustPressed = true;
                this.leftMousePressed = true;
            } else if (mouseEvent.getButton() == 3) {
                this.rightMousePressed = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() == 1) {
                this.leftMousePressed = false;
            } else if (mouseEvent.getButton() == 3) {
                this.rightMousePressed = false;
            }
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            this.mouseX = mouseEvent.getX();
            this.mouseY = mouseEvent.getY();
        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            this.mouseX = mouseEvent.getX();
            this.mouseY = mouseEvent.getY();
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
        }

        public boolean isKeyPressed(int n) {
            return this.keysPressed.contains(n);
        }

        public boolean isKeyJustPressed(int n) {
            return this.keysJustPressed.contains(n);
        }

        public boolean isKeyJustReleased(int n) {
            return this.keysJustReleased.contains(n);
        }

        public int getMouseX() {
            return this.mouseX;
        }

        public int getMouseY() {
            return this.mouseY;
        }
    }

    public static class LevelManager {
        private static LevelManager instance = null;
        private int currentLevel = 1;
        private Object tileMap;
        private Map<Character, String> tileCharacterMapping = new HashMap<Character, String>();
        private float playerSpawnX = 100.0f;
        private float playerSpawnY = 100.0f;
        private List<EnemySpawn> enemySpawns = new ArrayList<EnemySpawn>();

        public static synchronized LevelManager getInstance() {
            if (instance == null) {
                instance = new LevelManager();
            }
            return instance;
        }

        private LevelManager() {
            System.out.println("[LevelManager] Initialized");
        }

        public void loadLevel(int n) {
            this.currentLevel = n;
            if (n == 1) {
                String string = "maps/level_1/map.txt";
            } else if (n == 2) {
                String string = "maps/level_2/map.txt";
            } else {
                System.err.println("[LevelManager] Unknown level: " + n);
                return;
            }
            System.out.println("[LevelManager] Loaded level " + n);
        }

        public int getCurrentLevel() {
            return this.currentLevel;
        }

        public Object getTileMap() {
            return this.tileMap;
        }

        public float getPlayerSpawnX() {
            return this.playerSpawnX;
        }

        public float getPlayerSpawnY() {
            return this.playerSpawnY;
        }

        public List<EnemySpawn> getEnemySpawns() {
            return this.enemySpawns;
        }

        public static class EnemySpawn {
            public float x;
            public float y;
            public String type;

            public EnemySpawn(float f, float f2, String string) {
                this.x = f;
                this.y = f2;
                this.type = string;
            }
        }
    }

    public static class StateTransitionValidator {
        private static final Map<GameState, Set<GameState>> VALID_TRANSITIONS = new HashMap<GameState, Set<GameState>>();
        private static boolean initialized = false;

        private static void initializeTransitions() {
            if (initialized) {
                return;
            }
            StateTransitionValidator.add(GameState.SPLASH_SCREEN, GameState.STUDIO_IDENT);
            StateTransitionValidator.add(GameState.STUDIO_IDENT, GameState.MAIN_MENU);
            StateTransitionValidator.add(GameState.MAIN_MENU, GameState.LEVEL_SELECT);
            StateTransitionValidator.add(GameState.MAIN_MENU, GameState.HOW_TO_PLAY);
            StateTransitionValidator.add(GameState.MAIN_MENU, GameState.CREDITS);
            StateTransitionValidator.add(GameState.HOW_TO_PLAY, GameState.MAIN_MENU);
            StateTransitionValidator.add(GameState.CREDITS, GameState.MAIN_MENU);
            StateTransitionValidator.add(GameState.LEVEL_SELECT, GameState.CHARACTER_SELECT);
            StateTransitionValidator.add(GameState.CHARACTER_SELECT, GameState.STORY_INTRO);
            StateTransitionValidator.add(GameState.STORY_INTRO, GameState.LEVEL_TRANSITION);
            StateTransitionValidator.add(GameState.LEVEL_TRANSITION, GameState.GET_READY);
            StateTransitionValidator.add(GameState.GET_READY, GameState.PLAYING);
            StateTransitionValidator.add(GameState.GET_READY, GameState.BOSS_INTRO);
            StateTransitionValidator.add(GameState.PLAYING, GameState.PAUSED);
            StateTransitionValidator.add(GameState.PAUSED, GameState.PLAYING);
            StateTransitionValidator.add(GameState.PAUSED, GameState.MAIN_MENU);
            StateTransitionValidator.add(GameState.PLAYING, GameState.GAME_OVER);
            StateTransitionValidator.add(GameState.PLAYING, GameState.LEVEL_COMPLETE);
            StateTransitionValidator.add(GameState.LEVEL_COMPLETE, GameState.LEVEL_TRANSITION);
            StateTransitionValidator.add(GameState.LEVEL_COMPLETE, GameState.VICTORY);
            StateTransitionValidator.add(GameState.BOSS_INTRO, GameState.PLAYING);
            StateTransitionValidator.add(GameState.GAME_OVER, GameState.MAIN_MENU);
            StateTransitionValidator.add(GameState.VICTORY, GameState.CREDITS);
            StateTransitionValidator.add(GameState.VICTORY, GameState.MAIN_MENU);
            initialized = true;
        }

        private static void add(GameState gameState2, GameState gameState3) {
            VALID_TRANSITIONS.computeIfAbsent(gameState2, gameState -> new HashSet()).add(gameState3);
        }

        public static boolean isValidTransition(GameState gameState, GameState gameState2) {
            Set<GameState> set = VALID_TRANSITIONS.get((Object)gameState);
            return set != null && set.contains((Object)gameState2);
        }

        static {
            StateTransitionValidator.initializeTransitions();
        }
    }

    public static class AnimationInitializer {
        private AnimationPlayer animPlayer;

        public AnimationInitializer(AnimationPlayer animationPlayer) {
            this.animPlayer = animationPlayer;
        }

        public void initializeLevel1(AnimationPlayer animationPlayer) {
        }

        public void initializeLevel2(AnimationPlayer animationPlayer) {
        }
    }

    public static class AnimationPlayer {
        private Map<Integer, BufferedImage> entityFrames = new HashMap<Integer, BufferedImage>();
        private Map<Integer, Long> entityStartTimes = new HashMap<Integer, Long>();

        public void playAnimation(int n, String string, String string2, long l) {
            this.entityStartTimes.put(n, l);
        }

        public void switchAnimation(int n, String string, long l) {
            this.entityStartTimes.put(n, l);
        }

        public void stopAnimation(int n) {
            this.entityFrames.remove(n);
            this.entityStartTimes.remove(n);
        }

        public void update(long l) {
        }

        public BufferedImage getCurrentFrameImage(int n) {
            return this.entityFrames.get(n);
        }

        public String getMemoryStats() {
            return "Animations loaded: " + this.entityFrames.size();
        }
    }

    public static class GameStateManager {
        private GameState currentState;
        private GameState previousState = null;
        private Stack<GameState> stateHistory;
        private long stateChangeTime;
        private float stateElapsed;
        private List<StateListener> listeners = new ArrayList<StateListener>();

        public GameStateManager() {
            this.currentState = GameState.INITIALIZING;
            this.stateHistory = new Stack();
            this.stateChangeTime = System.currentTimeMillis();
        }

        public boolean transition(GameState gameState) {
            if (gameState == this.currentState) {
                return false;
            }
            this.previousState = this.currentState;
            this.stateHistory.push(this.currentState);
            this.currentState = gameState;
            this.stateChangeTime = System.currentTimeMillis();
            this.stateElapsed = 0.0f;
            return true;
        }

        public boolean revert() {
            if (this.stateHistory.isEmpty()) {
                return false;
            }
            GameState gameState = this.stateHistory.pop();
            return this.transition(gameState);
        }

        public void update(long l) {
            this.stateElapsed += (float)l / 1000.0f;
        }

        public GameState getCurrentState() {
            return this.currentState;
        }

        public GameState getPreviousState() {
            return this.previousState;
        }

        public float getStateElapsed() {
            return this.stateElapsed;
        }

        public static interface StateListener {
            public void onStateChanged(GameState var1, GameState var2);
        }
    }

    public static class ScoreManager {
        private int score = 0;
        private int lives = 3;
        private int collectibles = 0;
        private int kills = 0;
        private int highScore = 0;

        public void collectibleGathered() {
            this.score += 10;
            ++this.collectibles;
        }

        public void enemyKilled() {
            this.score += 50;
            ++this.kills;
        }

        public void bossDefeated() {
            this.score += 500;
            ++this.kills;
        }

        public boolean loseLife() {
            --this.lives;
            if (this.lives < 0) {
                this.lives = 0;
            }
            return this.lives <= 0;
        }

        public void addLife() {
            ++this.lives;
        }

        public void updateHighScore() {
            if (this.score > this.highScore) {
                this.highScore = this.score;
                System.out.println("[ScoreManager] New high score: " + this.highScore);
            }
        }

        public void reset() {
            this.score = 0;
            this.lives = 3;
            this.collectibles = 0;
            this.kills = 0;
        }

        public int getScore() {
            return this.score;
        }

        public int getLives() {
            return this.lives;
        }

        public int getCollectibles() {
            return this.collectibles;
        }

        public int getKills() {
            return this.kills;
        }

        public int getHighScore() {
            return this.highScore;
        }
    }

    public static class MouseHandler {
        public int x;
        public int y;
        public boolean leftHeld;
        public boolean rightHeld;
        public boolean leftJustClicked;
        public boolean rightJustClicked;
        public boolean leftJustPressed;
        public boolean rightJustPressed;
        public boolean leftJustReleased;
        public boolean rightJustReleased;
        public int clickX;
        public int clickY;

        public void moved(int n, int n2) {
            this.x = n;
            this.y = n2;
        }

        public void pressed(int n, int n2, int n3) {
            this.x = n;
            this.y = n2;
            if (n3 == 1) {
                this.leftHeld = true;
                this.leftJustPressed = true;
            }
            if (n3 == 3) {
                this.rightHeld = true;
                this.rightJustPressed = true;
            }
        }

        public void released(int n, int n2, int n3) {
            this.x = n;
            this.y = n2;
            if (n3 == 1) {
                this.leftHeld = false;
                this.leftJustReleased = true;
            }
            if (n3 == 3) {
                this.rightHeld = false;
                this.rightJustReleased = true;
            }
        }

        public void clicked(int n, int n2, int n3) {
            this.x = n;
            this.y = n2;
            this.clickX = n;
            this.clickY = n2;
            if (n3 == 1) {
                this.leftJustClicked = true;
            }
            if (n3 == 3) {
                this.rightJustClicked = true;
            }
        }

        public boolean isOver(int n, int n2, int n3, int n4) {
            return this.x >= n && this.x <= n + n3 && this.y >= n2 && this.y <= n2 + n4;
        }

        public boolean clickedIn(int n, int n2, int n3, int n4) {
            return this.leftJustClicked && this.clickX >= n && this.clickX <= n + n3 && this.clickY >= n2 && this.clickY <= n2 + n4;
        }

        public void clearOneShots() {
            this.rightJustClicked = false;
            this.leftJustClicked = false;
            this.rightJustPressed = false;
            this.leftJustPressed = false;
            this.rightJustReleased = false;
            this.leftJustReleased = false;
        }

        public void reset() {
            this.rightHeld = false;
            this.leftHeld = false;
            this.rightJustPressed = false;
            this.leftJustPressed = false;
            this.rightJustReleased = false;
            this.leftJustReleased = false;
            this.clearOneShots();
        }
    }

    public static class InputHandler {
        public boolean left;
        public boolean right;
        public boolean up;
        public boolean down;
        public boolean jump;
        public boolean dash;
        public boolean attack1;
        public boolean attack2;
        public boolean attack3;
        public boolean pause;
        public boolean restart;
        public boolean interact;
        public boolean special1;
        public boolean special2;
        public boolean special3;
        public boolean crouch;
        public boolean leftJustPressed;
        public boolean rightJustPressed;
        public boolean upJustPressed;
        public boolean downJustPressed;
        public boolean jumpJustPressed;
        public boolean jumpJustReleased;
        public boolean dashJustPressed;
        public boolean attack1JustPressed;
        public boolean attack2JustPressed;
        public boolean attack3JustPressed;
        public boolean pauseJustPressed;
        public boolean restartJustPressed;
        public boolean interactJustPressed;
        public boolean special1JustPressed;
        public boolean special2JustPressed;
        public boolean special3JustPressed;
        public boolean crouchJustPressed;
        public boolean crouchJustReleased;
        public boolean anyKeyJustPressed;
        public boolean debugJustPressed;

        public void keyPressed(int n) {
            this.anyKeyJustPressed = true;
            switch (n) {
                case 37: 
                case 65: {
                    if (!this.left) {
                        this.leftJustPressed = true;
                    }
                    this.left = true;
                    break;
                }
                case 39: 
                case 68: {
                    if (!this.right) {
                        this.rightJustPressed = true;
                    }
                    this.right = true;
                    break;
                }
                case 38: 
                case 87: {
                    if (!this.up) {
                        this.upJustPressed = true;
                    }
                    this.up = true;
                    break;
                }
                case 40: 
                case 83: {
                    if (!this.down) {
                        this.downJustPressed = true;
                    }
                    this.down = true;
                    break;
                }
                case 32: {
                    if (!this.jump) {
                        this.jumpJustPressed = true;
                    }
                    this.jump = true;
                    break;
                }
                case 16: {
                    if (!this.dash) {
                        this.dashJustPressed = true;
                    }
                    this.dash = true;
                    break;
                }
                case 90: {
                    if (!this.attack1) {
                        this.attack1JustPressed = true;
                    }
                    this.attack1 = true;
                    break;
                }
                case 88: {
                    if (!this.attack2) {
                        this.attack2JustPressed = true;
                    }
                    this.attack2 = true;
                    break;
                }
                case 67: {
                    if (!this.attack3) {
                        this.attack3JustPressed = true;
                    }
                    this.attack3 = true;
                    break;
                }
                case 27: {
                    if (!this.pause) {
                        this.pauseJustPressed = true;
                    }
                    this.pause = true;
                    break;
                }
                case 82: {
                    if (!this.restart) {
                        this.restartJustPressed = true;
                    }
                    this.restart = true;
                    break;
                }
                case 69: {
                    if (!this.interact) {
                        this.interactJustPressed = true;
                    }
                    this.interact = true;
                    break;
                }
                case 49: {
                    if (!this.special1) {
                        this.special1JustPressed = true;
                    }
                    this.special1 = true;
                    break;
                }
                case 50: {
                    if (!this.special2) {
                        this.special2JustPressed = true;
                    }
                    this.special2 = true;
                    break;
                }
                case 51: {
                    if (!this.special3) {
                        this.special3JustPressed = true;
                    }
                    this.special3 = true;
                    break;
                }
                case 17: {
                    if (!this.crouch) {
                        this.crouchJustPressed = true;
                    }
                    this.crouch = true;
                    break;
                }
                case 114: {
                    this.debugJustPressed = true;
                }
            }
        }

        public void keyReleased(int n) {
            switch (n) {
                case 37: 
                case 65: {
                    this.left = false;
                    break;
                }
                case 39: 
                case 68: {
                    this.right = false;
                    break;
                }
                case 38: 
                case 87: {
                    this.up = false;
                    break;
                }
                case 40: 
                case 83: {
                    this.down = false;
                    break;
                }
                case 32: {
                    this.jumpJustReleased = true;
                    this.jump = false;
                    break;
                }
                case 16: {
                    this.dash = false;
                    break;
                }
                case 90: {
                    this.attack1 = false;
                    break;
                }
                case 88: {
                    this.attack2 = false;
                    break;
                }
                case 67: {
                    this.attack3 = false;
                    break;
                }
                case 27: {
                    this.pause = false;
                    break;
                }
                case 82: {
                    this.restart = false;
                    break;
                }
                case 69: {
                    this.interact = false;
                    break;
                }
                case 49: {
                    this.special1 = false;
                    break;
                }
                case 50: {
                    this.special2 = false;
                    break;
                }
                case 51: {
                    this.special3 = false;
                    break;
                }
                case 17: {
                    this.crouchJustReleased = true;
                    this.crouch = false;
                }
            }
        }

        public void clearOneShots() {
            this.downJustPressed = false;
            this.upJustPressed = false;
            this.rightJustPressed = false;
            this.leftJustPressed = false;
            this.dashJustPressed = false;
            this.jumpJustReleased = false;
            this.jumpJustPressed = false;
            this.attack3JustPressed = false;
            this.attack2JustPressed = false;
            this.attack1JustPressed = false;
            this.interactJustPressed = false;
            this.restartJustPressed = false;
            this.pauseJustPressed = false;
            this.special3JustPressed = false;
            this.special2JustPressed = false;
            this.special1JustPressed = false;
            this.crouchJustReleased = false;
            this.crouchJustPressed = false;
            this.debugJustPressed = false;
            this.anyKeyJustPressed = false;
        }

        public void reset() {
            this.down = false;
            this.up = false;
            this.right = false;
            this.left = false;
            this.dash = false;
            this.jump = false;
            this.attack3 = false;
            this.attack2 = false;
            this.attack1 = false;
            this.special3 = false;
            this.special2 = false;
            this.special1 = false;
            this.crouch = false;
            this.interact = false;
            this.restart = false;
            this.pause = false;
            this.clearOneShots();
        }
    }

    public static class StateMachine<T extends Enum<T>> {
        private T currentState;
        private T previousState;
        private long stateTransitionTime;
        private int transitionCount;
        private StateChangeListener<T> stateChangeListener;

        public StateMachine(T t) {
            this.currentState = t;
            this.previousState = null;
            this.stateTransitionTime = 0L;
            this.transitionCount = 0;
        }

        public void setStateChangeListener(StateChangeListener<T> stateChangeListener) {
            this.stateChangeListener = stateChangeListener;
        }

        public boolean setState(T t) {
            if (t == this.currentState) {
                return false;
            }
            this.previousState = this.currentState;
            this.currentState = t;
            this.stateTransitionTime = 0L;
            ++this.transitionCount;
            if (this.stateChangeListener != null) {
                this.stateChangeListener.onStateChange(this.previousState, t);
            }
            return true;
        }

        public void update(long l) {
            this.stateTransitionTime += l;
        }

        public T getState() {
            return this.currentState;
        }

        public T getPreviousState() {
            return this.previousState;
        }

        public boolean isInState(T t) {
            return this.currentState == t;
        }

        public boolean wasInState(T t) {
            return this.previousState == t;
        }

        public long getStateTime() {
            return this.stateTransitionTime;
        }

        public int getTransitionCount() {
            return this.transitionCount;
        }

        public static interface StateChangeListener<E> {
            public void onStateChange(E var1, E var2);
        }
    }

    public static enum GameState {
        INITIALIZING("System startup"),
        SPLASH_SCREEN("Studio splash screen"),
        STUDIO_IDENT("University identification"),
        LOADING("Loading resources"),
        MAIN_MENU("Main menu"),
        CHARACTER_SELECT("Character selection"),
        LEVEL_SELECT("Level selection"),
        SETTINGS("Settings/Options"),
        HOW_TO_PLAY("Tutorial/Controls"),
        CREDITS("Credits screen"),
        STORY_INTRO("Pre-level story briefing"),
        LEVEL_TRANSITION("Level title transition"),
        GET_READY("Countdown before gameplay"),
        PLAYING("Active gameplay"),
        BOSS_INTRO("Boss encounter intro"),
        BOSS_ENCOUNTER("Boss battle special state"),
        DIALOGUE("Dialogue/narrative overlay"),
        PAUSED("Game paused"),
        LEVEL_COMPLETE("Level victory screen"),
        GAME_OVER("Defeat/Game Over"),
        VICTORY("Mission/Game victory"),
        CUTSCENE("Cinematic sequence"),
        TRANSITION("State transition effect"),
        ERROR("Error state"),
        DEBUG_MENU("Developer debug menu"),
        EXITING("Shutdown procedure");

        private final String description;

        private GameState(String string2) {
            this.description = string2;
        }

        public String getDescription() {
            return this.description;
        }

        public boolean isGameplay() {
            return this == PLAYING || this == BOSS_ENCOUNTER || this == DIALOGUE;
        }

        public boolean isMenu() {
            return this == MAIN_MENU || this == CHARACTER_SELECT || this == LEVEL_SELECT || this == SETTINGS || this == HOW_TO_PLAY;
        }

        public boolean canPause() {
            return this == PLAYING || this == BOSS_ENCOUNTER;
        }

        public boolean isInteractive() {
            return this != LOADING && this != CUTSCENE && this != TRANSITION;
        }

        public boolean allowsInput() {
            return this != LOADING && this != SPLASH_SCREEN && this != STUDIO_IDENT && this != CUTSCENE;
        }
    }

    public static enum PlayerState {
        IDLE,
        WALK,
        RUN,
        DASH,
        JUMP,
        DOUBLE_JUMP,
        FALL,
        ATTACK1,
        ATTACK2,
        ATTACK3,
        HURT,
        DEATH,
        LAND,
        COMBO1,
        COMBO2,
        COMBO3,
        AIR_ATTACK,
        BLOCK,
        DODGE,
        SPECIAL1,
        SPECIAL2,
        SPECIAL3,
        CROUCH,
        INTERACT,
        WALL_SLIDE,
        WALL_JUMP,
        LEDGE_GRAB,
        LEDGE_CLIMB,
        SLIDE;

    }

    public static interface Spatial {
        public float getX();

        public float getY();

        public float getWorldX();

        public float getWorldY();

        public void shiftX(float var1);

        public void shiftY(float var1);

        public boolean pointIn(float var1, float var2);
    }
}
