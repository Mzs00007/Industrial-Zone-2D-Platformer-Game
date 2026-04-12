/*
 * Decompiled with CFR 0.152.
 */
import animation.AnimationAndSpriteLoader;
import animation.PlayerCharacterAnimations;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import physics.CharacterPhysicsProfile;

public class LiveCharacterPhysicsTester
extends JFrame {
    private Map<String, String> assetPaths = AnimationAndSpriteLoader.getAssetPathsMap();
    private TestCharacterPanel testPanel;
    private PhysicsControlPanel controlPanel;

    public LiveCharacterPhysicsTester() {
        this.setTitle("LIVE CHARACTER PHYSICS TESTER v5.0 - Real-Time Tuning");
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        JPanel jPanel = new JPanel(new BorderLayout(10, 10));
        jPanel.setBackground(new Color(20, 25, 35));
        this.testPanel = new TestCharacterPanel();
        JPanel jPanel2 = new JPanel(new BorderLayout());
        jPanel2.setBackground(new Color(20, 25, 35));
        jPanel2.add((Component)this.testPanel, "Center");
        this.controlPanel = new PhysicsControlPanel(this.testPanel);
        JScrollPane jScrollPane = new JScrollPane(this.controlPanel);
        jScrollPane.setPreferredSize(new Dimension(280, 700));
        jScrollPane.getVerticalScrollBar().setUnitIncrement(5);
        jPanel.add((Component)jPanel2, "Center");
        jPanel.add((Component)jScrollPane, "East");
        this.setContentPane(jPanel);
        this.setSize(1400, 800);
        this.setLocationRelativeTo(null);
        this.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                LiveCharacterPhysicsTester.this.testPanel.handleKeyPress(keyEvent);
            }
        });
        this.setVisible(true);
        this.setFocusable(true);
    }

    public static void main(String[] stringArray) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("\n\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
            System.out.println("\u2551  \ud83c\udfae LIVE CHARACTER PHYSICS TESTER v5.0                        \u2551");
            System.out.println("\u2560\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2563");
            System.out.println("\u2551  Real-Time Physics Tuning & Animation State Testing           \u2551");
            System.out.println("\u255a\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255d\n");
            System.out.println("\u2713 3 Synchronized Character Instances");
            System.out.println("\u2713 Live physics adjustment (no recompile needed)");
            System.out.println("\u2713 24 animation states (1-9, A-O)");
            System.out.println("\u2713 Real-time velocity & position updates");
            System.out.println("\u2713 Character type switching (Shift+C/B/P)");
            System.out.println("\u2713 Connected to AnimationAndSpriteLoader\n");
            new LiveCharacterPhysicsTester();
        });
    }

    private static class TestCharacterPanel
    extends JPanel {
        private CharacterInstance[] characters = new CharacterInstance[3];
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

        public TestCharacterPanel() {
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
                this.characters[i] = new CharacterInstance(this.selectedType, 150 + i * 350, 200.0f, this.spriteLoader);
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
                for (CharacterInstance characterInstance : this.characters) {
                    characterInstance.updateAnimation();
                    characterInstance.updatePhysics();
                }
                this.repaint();
            });
            timer.start();
        }

        private void applyMovementInput() {
            for (CharacterInstance characterInstance : this.characters) {
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
            for (CharacterInstance characterInstance : this.characters) {
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
                for (CharacterInstance characterInstance : this.characters) {
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
                this.characters[i] = new CharacterInstance(characterType, 200 + i * 280, 400.0f, this.spriteLoader);
            }
        }

        void updatePhysics(CharacterPhysicsProfile characterPhysicsProfile) {
            for (CharacterInstance characterInstance : this.characters) {
                characterInstance.setPhysicsProfile(characterPhysicsProfile);
            }
        }

        CharacterInstance[] getCharacters() {
            return this.characters;
        }
    }

    private static class PhysicsControlPanel
    extends JPanel {
        private TestCharacterPanel testPanel;
        private JTextField[] physicsFields;
        private String[] physicsLabels;
        private CharacterPhysicsProfile currentProfile;
        private JComboBox<String> characterSelector;

        public PhysicsControlPanel(TestCharacterPanel testCharacterPanel) {
            this.testPanel = testCharacterPanel;
            this.currentProfile = CharacterPhysicsProfile.createProfile(CharacterPhysicsProfile.CharacterType.BIKER);
            this.setLayout(new BoxLayout(this, 1));
            this.setBackground(new Color(40, 50, 70));
            this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            this.add(this.createCharacterSelector());
            this.add(Box.createVerticalStrut(10));
            this.physicsLabels = new String[]{"Walk Speed", "Run Speed", "Dash Speed", "Jump Power", "Gravity", "Max Fall Speed", "Acceleration", "Friction", "Air Friction", "Width", "Height", "Collision Width", "Collision Height", "Mass", "Max Health", "Armor", "Max Ammo", "Attack Range", "Attack Damage", "Climb Speed", "Swim Speed", "Wall Slide Speed", "Attack Cooldown", "Dash Cooldown"};
            this.physicsFields = new JTextField[this.physicsLabels.length];
            this.add(new JLabel("\u2550 PHYSICS PARAMETERS \u2550"));
            this.add(Box.createVerticalStrut(5));
            for (int i = 0; i < this.physicsLabels.length; ++i) {
                this.add(this.createPhysicsField(i));
                this.add(Box.createVerticalStrut(3));
            }
            this.add(Box.createVerticalStrut(20));
            this.add(this.createUpdateButton());
            this.add(Box.createVerticalGlue());
            this.updateFieldValues();
        }

        private JPanel createCharacterSelector() {
            JPanel jPanel = new JPanel(new BorderLayout());
            jPanel.setBackground(new Color(40, 50, 70));
            jPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            JLabel jLabel = new JLabel("Character Type:");
            jLabel.setForeground(new Color(100, 200, 150));
            jLabel.setFont(new Font("Arial", 1, 11));
            this.characterSelector = new JComboBox<String>(new String[]{"Biker", "Cyborg", "Punk"});
            this.characterSelector.setBackground(new Color(50, 60, 80));
            this.characterSelector.setForeground(new Color(150, 200, 255));
            this.characterSelector.addActionListener(actionEvent -> {
                String string = (String)this.characterSelector.getSelectedItem();
                CharacterPhysicsProfile.CharacterType characterType = null;
                switch (string) {
                    case "Biker": {
                        characterType = CharacterPhysicsProfile.CharacterType.BIKER;
                        break;
                    }
                    case "Cyborg": {
                        characterType = CharacterPhysicsProfile.CharacterType.CYBORG;
                        break;
                    }
                    case "Punk": {
                        characterType = CharacterPhysicsProfile.CharacterType.PUNK;
                    }
                }
                if (characterType != null) {
                    this.testPanel.setCharacterType(characterType);
                    this.currentProfile = CharacterPhysicsProfile.createProfile(characterType);
                    this.updateFieldValues();
                }
            });
            jPanel.add((Component)jLabel, "West");
            jPanel.add(this.characterSelector, "Center");
            return jPanel;
        }

        private JPanel createPhysicsField(int n) {
            JPanel jPanel = new JPanel(new BorderLayout());
            jPanel.setBackground(new Color(40, 50, 70));
            jPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            JLabel jLabel = new JLabel(this.physicsLabels[n]);
            jLabel.setForeground(new Color(150, 200, 255));
            jLabel.setFont(new Font("Arial", 0, 9));
            jLabel.setPreferredSize(new Dimension(120, 25));
            this.physicsFields[n] = new JTextField("0.00");
            this.physicsFields[n].setBackground(new Color(50, 60, 80));
            this.physicsFields[n].setForeground(new Color(100, 255, 100));
            this.physicsFields[n].setFont(new Font("Monospaced", 0, 10));
            this.physicsFields[n].setPreferredSize(new Dimension(100, 25));
            this.physicsFields[n].addActionListener(actionEvent -> this.applyPhysicsChanges());
            jPanel.add((Component)jLabel, "West");
            jPanel.add((Component)this.physicsFields[n], "Center");
            return jPanel;
        }

        private JButton createUpdateButton() {
            JButton jButton = new JButton("APPLY CHANGES");
            jButton.setBackground(new Color(100, 200, 150));
            jButton.setForeground(new Color(20, 30, 40));
            jButton.setFont(new Font("Arial", 1, 11));
            jButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
            jButton.addActionListener(actionEvent -> this.applyPhysicsChanges());
            return jButton;
        }

        private void updateFieldValues() {
            this.physicsFields[0].setText(String.format("%.3f", Float.valueOf(this.currentProfile.walkSpeed)));
            this.physicsFields[1].setText(String.format("%.3f", Float.valueOf(this.currentProfile.runSpeed)));
            this.physicsFields[2].setText(String.format("%.3f", Float.valueOf(this.currentProfile.dashSpeed)));
            this.physicsFields[3].setText(String.format("%.3f", Float.valueOf(this.currentProfile.jumpPower)));
            this.physicsFields[4].setText(String.format("%.5f", Float.valueOf(this.currentProfile.gravity)));
            this.physicsFields[5].setText(String.format("%.3f", Float.valueOf(this.currentProfile.maxFallSpeed)));
            this.physicsFields[6].setText(String.format("%.5f", Float.valueOf(this.currentProfile.acceleration)));
            this.physicsFields[7].setText(String.format("%.3f", Float.valueOf(this.currentProfile.friction)));
            this.physicsFields[8].setText(String.format("%.3f", Float.valueOf(this.currentProfile.airFriction)));
            this.physicsFields[9].setText(String.format("%.1f", Float.valueOf(this.currentProfile.width)));
            this.physicsFields[10].setText(String.format("%.1f", Float.valueOf(this.currentProfile.height)));
            this.physicsFields[11].setText(String.format("%.1f", Float.valueOf(this.currentProfile.collisionWidth)));
            this.physicsFields[12].setText(String.format("%.1f", Float.valueOf(this.currentProfile.collisionHeight)));
            this.physicsFields[13].setText(String.format("%.1f", Float.valueOf(this.currentProfile.mass)));
            this.physicsFields[14].setText(String.valueOf(this.currentProfile.maxHealth));
            this.physicsFields[15].setText(String.valueOf(this.currentProfile.armor));
            this.physicsFields[16].setText(String.valueOf(this.currentProfile.maxAmmo));
            this.physicsFields[17].setText(String.format("%.1f", Float.valueOf(this.currentProfile.attackRange)));
            this.physicsFields[18].setText(String.format("%.1f", Float.valueOf(this.currentProfile.attackDamage)));
            this.physicsFields[19].setText(String.format("%.3f", Float.valueOf(this.currentProfile.climbSpeed)));
            this.physicsFields[20].setText(String.format("%.3f", Float.valueOf(this.currentProfile.swimSpeed)));
            this.physicsFields[21].setText(String.format("%.3f", Float.valueOf(this.currentProfile.wallSlideSpeed)));
            this.physicsFields[22].setText(String.valueOf(this.currentProfile.attackCooldown));
            this.physicsFields[23].setText(String.valueOf(this.currentProfile.dashCooldown));
        }

        private void applyPhysicsChanges() {
            try {
                this.currentProfile.walkSpeed = Float.parseFloat(this.physicsFields[0].getText());
                this.currentProfile.runSpeed = Float.parseFloat(this.physicsFields[1].getText());
                this.currentProfile.dashSpeed = Float.parseFloat(this.physicsFields[2].getText());
                this.currentProfile.jumpPower = Float.parseFloat(this.physicsFields[3].getText());
                this.currentProfile.gravity = Float.parseFloat(this.physicsFields[4].getText());
                this.currentProfile.maxFallSpeed = Float.parseFloat(this.physicsFields[5].getText());
                this.currentProfile.acceleration = Float.parseFloat(this.physicsFields[6].getText());
                this.currentProfile.friction = Float.parseFloat(this.physicsFields[7].getText());
                this.currentProfile.airFriction = Float.parseFloat(this.physicsFields[8].getText());
                this.currentProfile.width = Float.parseFloat(this.physicsFields[9].getText());
                this.currentProfile.height = Float.parseFloat(this.physicsFields[10].getText());
                this.currentProfile.collisionWidth = Float.parseFloat(this.physicsFields[11].getText());
                this.currentProfile.collisionHeight = Float.parseFloat(this.physicsFields[12].getText());
                this.currentProfile.mass = Float.parseFloat(this.physicsFields[13].getText());
                this.currentProfile.maxHealth = Integer.parseInt(this.physicsFields[14].getText());
                this.currentProfile.armor = Integer.parseInt(this.physicsFields[15].getText());
                this.currentProfile.maxAmmo = Integer.parseInt(this.physicsFields[16].getText());
                this.currentProfile.attackRange = Float.parseFloat(this.physicsFields[17].getText());
                this.currentProfile.attackDamage = Float.parseFloat(this.physicsFields[18].getText());
                this.currentProfile.climbSpeed = Float.parseFloat(this.physicsFields[19].getText());
                this.currentProfile.swimSpeed = Float.parseFloat(this.physicsFields[20].getText());
                this.currentProfile.wallSlideSpeed = Float.parseFloat(this.physicsFields[21].getText());
                this.currentProfile.attackCooldown = Long.parseLong(this.physicsFields[22].getText());
                this.currentProfile.dashCooldown = Long.parseLong(this.physicsFields[23].getText());
                this.testPanel.updatePhysics(this.currentProfile);
                for (JTextField jTextField : this.physicsFields) {
                    jTextField.setBackground(new Color(80, 120, 60));
                }
                Timer timer = new Timer(200, actionEvent -> {
                    for (JTextField jTextField : this.physicsFields) {
                        jTextField.setBackground(new Color(50, 60, 80));
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
            catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + numberFormatException.getMessage(), "Physics Update Error", 0);
            }
        }
    }

    private static class CharacterInstance {
        public float x;
        public float y;
        public float velocityX = 0.0f;
        public float velocityY = 0.0f;
        public String currentAnimation = "Idle_Default";
        public CharacterPhysicsProfile profile;
        public CharacterPhysicsProfile.CharacterType type;
        public AnimationAndSpriteLoader spriteLoader;
        public BufferedImage currentFrame;
        public long animationStartTime;
        public int currentFrameIndex = 0;
        public static final float CHAR_WIDTH = 28.0f;
        public static final float CHAR_HEIGHT = 44.0f;
        public boolean isGrounded = false;
        public boolean canJump = true;

        public CharacterInstance(CharacterPhysicsProfile.CharacterType characterType, float f, float f2, AnimationAndSpriteLoader animationAndSpriteLoader) {
            this.type = characterType;
            this.x = f;
            this.y = f2;
            this.spriteLoader = animationAndSpriteLoader;
            this.profile = CharacterPhysicsProfile.createProfile(characterType);
            this.animationStartTime = System.currentTimeMillis();
            this.updateFrame();
        }

        void setAnimationState(String string) {
            if (!this.currentAnimation.equals(string)) {
                this.currentAnimation = string;
                this.animationStartTime = System.currentTimeMillis();
                this.currentFrameIndex = 0;
            }
        }

        void updateAnimation() {
            if (this.spriteLoader == null) {
                return;
            }
            try {
                LinkedHashMap<String, PlayerCharacterAnimations.AnimationConfig> linkedHashMap = null;
                switch (this.type) {
                    case BIKER: {
                        linkedHashMap = PlayerCharacterAnimations.BikerAnimations.animations;
                        break;
                    }
                    case CYBORG: {
                        linkedHashMap = PlayerCharacterAnimations.CyborgAnimations.animations;
                        break;
                    }
                    case PUNK: {
                        linkedHashMap = PlayerCharacterAnimations.PunkAnimations.animations;
                    }
                }
                if (linkedHashMap == null) {
                    return;
                }
                PlayerCharacterAnimations.AnimationConfig animationConfig = linkedHashMap.getOrDefault(this.currentAnimation, linkedHashMap.get("Idle_Default"));
                long l = System.currentTimeMillis() - this.animationStartTime;
                int n = animationConfig.timingMs;
                this.currentFrameIndex = (int)(l / (long)n % (long)animationConfig.frameCount);
                this.updateFrame();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }

        void updatePhysics() {
            this.isGrounded = false;
            this.velocityY += this.profile.gravity;
            if (this.velocityY > this.profile.maxFallSpeed) {
                this.velocityY = this.profile.maxFallSpeed;
            }
            float f = this.x + this.velocityX;
            float f2 = this.y + this.velocityY;
            for (int[] nArray : TestCharacterPanel.PLATFORM_BLOCKS) {
                if (!this.checkHorizontalCollision(f, this.y, nArray)) continue;
                if (this.velocityX > 0.0f) {
                    f = (float)nArray[0] - 14.0f;
                } else if (this.velocityX < 0.0f) {
                    f = (float)(nArray[0] + nArray[2]) + 14.0f;
                }
                this.velocityX *= 0.2f;
            }
            for (int[] nArray : TestCharacterPanel.PLATFORM_BLOCKS) {
                if (!this.checkVerticalCollision(f, f2, nArray)) continue;
                float f3 = f2 + 22.0f;
                float f4 = f2 - 22.0f;
                float f5 = nArray[1];
                float f6 = nArray[1] + nArray[3];
                if (this.velocityY > 0.0f && f3 >= f5 && f3 <= f5 + 15.0f) {
                    f2 = f5 - 22.0f;
                    this.velocityY = 0.0f;
                    this.isGrounded = true;
                    this.canJump = true;
                    this.velocityX *= this.profile.friction;
                    continue;
                }
                if (!(this.velocityY < 0.0f) || !(f4 <= f6) || !(f4 >= f6 - 15.0f)) continue;
                f2 = f6 + 22.0f;
                this.velocityY = 0.0f;
                this.velocityY = this.profile.gravity * 0.5f;
            }
            if (!this.isGrounded) {
                this.velocityX *= this.profile.airFriction;
            }
            this.x = f;
            this.y = f2;
            if (this.x < 14.0f) {
                this.x = 14.0f;
                this.velocityX = 0.0f;
            }
            if (this.x > 1086.0f) {
                this.x = 1086.0f;
                this.velocityX = 0.0f;
            }
            if (this.y > 700.0f) {
                this.y = 150.0f;
                this.velocityY = 0.0f;
                this.velocityX = 0.0f;
                this.isGrounded = false;
            }
        }

        private boolean checkHorizontalCollision(float f, float f2, int[] nArray) {
            return f + 14.0f > (float)nArray[0] && f - 14.0f < (float)(nArray[0] + nArray[2]) && f2 + 22.0f > (float)nArray[1] && f2 - 22.0f < (float)(nArray[1] + nArray[3]);
        }

        private boolean checkVerticalCollision(float f, float f2, int[] nArray) {
            return f + 14.0f > (float)nArray[0] && f - 14.0f < (float)(nArray[0] + nArray[2]) && f2 + 22.0f > (float)nArray[1] && f2 - 22.0f < (float)(nArray[1] + nArray[3]);
        }

        void updateFrame() {
        }

        void setPhysicsProfile(CharacterPhysicsProfile characterPhysicsProfile) {
            this.profile = characterPhysicsProfile;
        }

        void draw(Graphics2D graphics2D) {
            float f = this.x - 14.0f;
            float f2 = this.y - 22.0f;
            if (this.isGrounded) {
                graphics2D.setColor(new Color(100, 200, 100, 200));
            } else {
                graphics2D.setColor(new Color(100, 150, 255, 200));
            }
            graphics2D.fillRect((int)f, (int)f2, 28, 44);
            graphics2D.setColor(this.isGrounded ? new Color(50, 150, 50) : new Color(50, 100, 200));
            graphics2D.setStroke(new BasicStroke(2.0f));
            graphics2D.drawRect((int)f, (int)f2, 28, 44);
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(new Font("Arial", 1, 10));
            String string = this.type.displayName.substring(0, 1);
            graphics2D.drawString(string, (int)this.x - 5, (int)this.y + 5);
            if (Math.abs(this.velocityX) > 0.1f || Math.abs(this.velocityY) > 0.1f) {
                graphics2D.setColor(new Color(255, 200, 0));
                graphics2D.setStroke(new BasicStroke(2.0f));
                int n = (int)(this.velocityX * 3.0f);
                int n2 = (int)(this.velocityY * 3.0f);
                graphics2D.drawLine((int)this.x, (int)this.y, (int)this.x + n, (int)this.y + n2);
                double d = Math.atan2(n2, n);
                int n3 = 8;
                int n4 = (int)this.x + n;
                int n5 = (int)this.y + n2;
                int n6 = (int)((double)n4 - (double)n3 * Math.cos(d - 0.5235987755982988));
                int n7 = (int)((double)n5 - (double)n3 * Math.sin(d - 0.5235987755982988));
                int n8 = (int)((double)n4 - (double)n3 * Math.cos(d + 0.5235987755982988));
                int n9 = (int)((double)n5 - (double)n3 * Math.sin(d + 0.5235987755982988));
                graphics2D.drawLine(n4, n5, n6, n7);
                graphics2D.drawLine(n4, n5, n8, n9);
            }
            graphics2D.setColor(new Color(150, 200, 150));
            graphics2D.setFont(new Font("Monospaced", 0, 8));
            String string2 = this.currentAnimation.length() > 15 ? this.currentAnimation.substring(0, 12) + "..." : this.currentAnimation;
            graphics2D.drawString(string2, (int)f, (int)(f2 + 44.0f + 12.0f));
            graphics2D.setColor(new Color(200, 200, 100));
            graphics2D.setFont(new Font("Monospaced", 0, 7));
            graphics2D.drawString(String.format("vX:%.1f vY:%.1f", Float.valueOf(this.velocityX), Float.valueOf(this.velocityY)), (int)f, (int)(f2 + 44.0f + 22.0f));
            graphics2D.drawString(this.isGrounded ? "GROUNDED" : "FALLING", (int)f, (int)(f2 + 44.0f + 30.0f));
        }
    }
}
