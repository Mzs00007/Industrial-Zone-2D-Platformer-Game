/*
 * Decompiled with CFR 0.152.
 */
import animation.AnimationAndSpriteLoader;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.Timer;
import physics.CharacterPhysicsProfile;

private static class LiveCharacterPhysicsTester.TestCharacterPanel
extends JPanel {
    private LiveCharacterPhysicsTester.CharacterInstance[] characters = new LiveCharacterPhysicsTester.CharacterInstance[3];
    private CharacterPhysicsProfile.CharacterType selectedType = CharacterPhysicsProfile.CharacterType.BIKER;
    private AnimationAndSpriteLoader spriteLoader;
    private String currentAnimationState = "Idle_Default";
    private long lastAnimationChangeTime = 0L;
    private String lastKey = "IDLE";
    private Set<Integer> pressedKeys = new HashSet<Integer>();
    private static final int GRID_SIZE = 32;
    private static final int[][] PLATFORM_BLOCKS = new int[][]{{0, 550, 1100, 32}, {150, 482, 200, 32}, {350, 414, 200, 32}, {550, 346, 200, 32}, {750, 414, 200, 32}, {950, 482, 150, 32}};
    private static final String[] ANIMATION_KEYS = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
    private static final String[] ANIMATION_STATES = new String[]{"Idle_Default", "Idle_Breathing_Heavy", "Idle_Alert", "Walk_Forward", "Walk_Backward", "Run_Forward", "Sprint_Full_Speed", "Dodge_Left", "Dodge_Right", "Attack_Light_Punch", "Attack_Heavy_Combo", "Attack_Spinning_Slash", "Attack_Special_Focus", "Damage_Light_Hit", "Damage_Heavy_Hit", "Knockdown_Fall", "Recovery_From_Ground", "Ability_Dash_Charge", "Ability_Phase_Shift", "Ability_Blade_Mastery", "Death_Defeated", "Idle_Default", "Idle_Default", "Idle_Default"};

    public LiveCharacterPhysicsTester.TestCharacterPanel() {
        this.setBackground(new Color(30, 35, 50));
        this.setPreferredSize(new Dimension(1100, 700));
        this.setFocusable(true);
        try {
            this.spriteLoader = new AnimationAndSpriteLoader();
        }
        catch (Exception exception) {
            System.err.println("Failed to initialize sprite loader: " + exception.getMessage());
        }
        for (int i = 0; i < 3; ++i) {
            this.characters[i] = new LiveCharacterPhysicsTester.CharacterInstance(this.selectedType, 150 + i * 350, 200.0f, this.spriteLoader);
        }
        this.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                pressedKeys.add(keyEvent.getKeyCode());
                this.handleKeyPress(keyEvent);
                this.requestFocusInWindow();
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                pressedKeys.remove(keyEvent.getKeyCode());
            }
        });
        Timer timer = new Timer(16, actionEvent -> {
            this.applyMovementInput();
            for (LiveCharacterPhysicsTester.CharacterInstance characterInstance : this.characters) {
                characterInstance.updateAnimation();
                characterInstance.updatePhysics();
            }
            this.repaint();
        });
        timer.start();
    }

    private void applyMovementInput() {
        for (LiveCharacterPhysicsTester.CharacterInstance characterInstance : this.characters) {
            if (this.pressedKeys.contains(37) || this.pressedKeys.contains(65)) {
                characterInstance.velocityX = -characterInstance.profile.walkSpeed;
            } else if (this.pressedKeys.contains(39) || this.pressedKeys.contains(68)) {
                characterInstance.velocityX = characterInstance.profile.walkSpeed;
            }
            if (!this.pressedKeys.contains(32) || !characterInstance.isGrounded || !characterInstance.canJump) continue;
            characterInstance.velocityY = -characterInstance.profile.jumpPower;
            characterInstance.isGrounded = false;
            characterInstance.canJump = false;
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int n = this.getWidth();
        int n2 = this.getHeight();
        GradientPaint gradientPaint = new GradientPaint(0.0f, 0.0f, new Color(40, 60, 80), 0.0f, n2, new Color(20, 35, 50));
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRect(0, 0, n, n2);
        this.drawGrid(graphics2D, n, n2);
        this.drawPlatforms(graphics2D);
        for (LiveCharacterPhysicsTester.CharacterInstance characterInstance : this.characters) {
            characterInstance.draw(graphics2D);
        }
        this.drawControlLegend(graphics2D);
        this.drawAnimationInfo(graphics2D);
    }

    private void drawGrid(Graphics2D graphics2D, int n, int n2) {
        int n3;
        graphics2D.setColor(new Color(60, 70, 90, 80));
        graphics2D.setStroke(new BasicStroke(0.5f));
        for (n3 = 0; n3 < n; n3 += 32) {
            graphics2D.drawLine(n3, 0, n3, n2);
        }
        for (n3 = 0; n3 < n2; n3 += 32) {
            graphics2D.drawLine(0, n3, n, n3);
        }
    }

    private void drawPlatforms(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(120, 100, 60));
        for (int[] nArray : PLATFORM_BLOCKS) {
            graphics2D.fillRect(nArray[0], nArray[1], nArray[2], nArray[3]);
        }
        graphics2D.setColor(new Color(80, 65, 40));
        graphics2D.setStroke(new BasicStroke(2.0f));
        for (int[] nArray : PLATFORM_BLOCKS) {
            graphics2D.drawRect(nArray[0], nArray[1], nArray[2], nArray[3]);
        }
        graphics2D.setColor(new Color(100, 80, 50, 100));
        graphics2D.setStroke(new BasicStroke(0.5f));
        for (int[] nArray : PLATFORM_BLOCKS) {
            int n;
            int n2 = nArray[0];
            int n3 = nArray[1];
            int n4 = nArray[2];
            int n5 = nArray[3];
            for (n = n2; n <= n2 + n4; n += 32) {
                graphics2D.drawLine(n, n3, n, n3 + n5);
            }
            for (n = n3; n <= n3 + n5; n += 32) {
                graphics2D.drawLine(n2, n, n2 + n4, n);
            }
        }
    }

    private void drawControlLegend(Graphics2D graphics2D) {
        String[] stringArray;
        int n = 20;
        int n2 = 20;
        int n3 = 480;
        int n4 = 180;
        graphics2D.setColor(new Color(40, 50, 70, 200));
        graphics2D.fillRoundRect(n - 5, n2 - 5, n3, n4, 10, 10);
        graphics2D.setColor(new Color(100, 200, 150));
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRoundRect(n - 5, n2 - 5, n3, n4, 10, 10);
        graphics2D.setColor(Color.CYAN);
        graphics2D.setFont(new Font("Monospaced", 1, 12));
        graphics2D.drawString("MOVEMENT & ANIMATION CONTROLS", n + 10, n2 + 20);
        graphics2D.setColor(new Color(200, 220, 255));
        graphics2D.setFont(new Font("Monospaced", 0, 10));
        int n5 = n2 + 40;
        for (String string : stringArray = new String[]{"MOVEMENT: Arrow Keys or WASD", "JUMP: SPACE (when grounded)", "ANIMATIONS: 1-9, A-O", "CHARACTER: Shift+C (Cyborg), Shift+B (Biker), Shift+P (Punk)", "COLOR: Green=Grounded, Blue=Flying", "ARROW: Shows velocity direction & magnitude"}) {
            graphics2D.drawString(string, n + 10, n5);
            n5 += 18;
        }
    }

    private void drawAnimationInfo(Graphics2D graphics2D) {
        int n = this.getWidth() - 420;
        int n2 = 20;
        int n3 = 400;
        int n4 = 140;
        graphics2D.setColor(new Color(40, 50, 70, 200));
        graphics2D.fillRoundRect(n - 5, n2 - 5, n3, n4, 10, 10);
        graphics2D.setColor(new Color(200, 100, 150));
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRoundRect(n - 5, n2 - 5, n3, n4, 10, 10);
        graphics2D.setColor(Color.MAGENTA);
        graphics2D.setFont(new Font("Monospaced", 1, 11));
        graphics2D.drawString("ACTIVE STATE & PHYSICS", n + 10, n2 + 20);
        graphics2D.setColor(new Color(200, 220, 255));
        graphics2D.setFont(new Font("Monospaced", 0, 10));
        long l = System.currentTimeMillis() - this.lastAnimationChangeTime;
        String string = String.format("%.0f ms ago", l);
        graphics2D.drawString("Char: " + this.selectedType.displayName, n + 10, n2 + 40);
        graphics2D.drawString("Animation: " + this.currentAnimationState, n + 10, n2 + 55);
        graphics2D.drawString("Last Key: [" + this.lastKey + "] - " + string, n + 10, n2 + 70);
        graphics2D.setColor(new Color(150, 255, 150));
        graphics2D.setFont(new Font("Monospaced", 0, 9));
        CharacterPhysicsProfile characterPhysicsProfile = this.characters[0].profile;
        graphics2D.drawString("Walk:" + String.format("%.2f", Float.valueOf(characterPhysicsProfile.walkSpeed)) + " Jump:" + String.format("%.2f", Float.valueOf(characterPhysicsProfile.jumpPower)) + " Gravity:" + String.format("%.3f", Float.valueOf(characterPhysicsProfile.gravity)), n + 10, n2 + 90);
        graphics2D.drawString("Friction:" + String.format("%.2f", Float.valueOf(characterPhysicsProfile.friction)) + " MaxFall:" + String.format("%.1f", Float.valueOf(characterPhysicsProfile.maxFallSpeed)), n + 10, n2 + 105);
        graphics2D.drawString("MaxVel:" + String.format("%.2f", Float.valueOf(characterPhysicsProfile.maxVelocity)) + " AirFrict:" + String.format("%.2f", Float.valueOf(characterPhysicsProfile.airFriction)), n + 10, n2 + 120);
    }

    void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 37 || keyEvent.getKeyCode() == 39 || keyEvent.getKeyCode() == 38 || keyEvent.getKeyCode() == 40 || keyEvent.getKeyCode() == 32 || keyEvent.getKeyChar() == 'a' || keyEvent.getKeyChar() == 'A' || keyEvent.getKeyChar() == 'd' || keyEvent.getKeyChar() == 'D' || keyEvent.getKeyChar() == 'w' || keyEvent.getKeyChar() == 'W' || keyEvent.getKeyChar() == 's' || keyEvent.getKeyChar() == 'S') {
            return;
        }
        Object object = "" + keyEvent.getKeyChar();
        if (Character.isDigit(keyEvent.getKeyChar())) {
            object = ((String)object).toUpperCase();
        } else if (Character.isLetter(keyEvent.getKeyChar())) {
            object = String.valueOf(keyEvent.getKeyChar()).toUpperCase();
        } else {
            return;
        }
        int n = -1;
        for (int i = 0; i < ANIMATION_KEYS.length; ++i) {
            if (!ANIMATION_KEYS[i].equals(object)) continue;
            n = i;
            break;
        }
        if (n >= 0 && n < ANIMATION_STATES.length) {
            String string;
            this.currentAnimationState = string = ANIMATION_STATES[n];
            this.lastKey = object;
            this.lastAnimationChangeTime = System.currentTimeMillis();
            for (LiveCharacterPhysicsTester.CharacterInstance characterInstance : this.characters) {
                characterInstance.setAnimationState(string);
            }
        }
        if (keyEvent.isShiftDown()) {
            switch (Character.toUpperCase(keyEvent.getKeyChar())) {
                case 'C': {
                    this.setCharacterType(CharacterPhysicsProfile.CharacterType.CYBORG);
                    break;
                }
                case 'B': {
                    this.setCharacterType(CharacterPhysicsProfile.CharacterType.BIKER);
                    break;
                }
                case 'P': {
                    this.setCharacterType(CharacterPhysicsProfile.CharacterType.PUNK);
                }
            }
        }
        this.repaint();
    }

    void setCharacterType(CharacterPhysicsProfile.CharacterType characterType) {
        this.selectedType = characterType;
        for (int i = 0; i < 3; ++i) {
            this.characters[i] = new LiveCharacterPhysicsTester.CharacterInstance(characterType, 200 + i * 280, 400.0f, this.spriteLoader);
        }
    }

    void updatePhysics(CharacterPhysicsProfile characterPhysicsProfile) {
        for (LiveCharacterPhysicsTester.CharacterInstance characterInstance : this.characters) {
            characterInstance.setPhysicsProfile(characterPhysicsProfile);
        }
    }

    LiveCharacterPhysicsTester.CharacterInstance[] getCharacters() {
        return this.characters;
    }
}
