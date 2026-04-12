/*
 * Decompiled with CFR 0.152.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import physics.CharacterPhysicsProfile;

private static class LiveCharacterPhysicsTester.PhysicsControlPanel
extends JPanel {
    private LiveCharacterPhysicsTester.TestCharacterPanel testPanel;
    private JTextField[] physicsFields;
    private String[] physicsLabels;
    private CharacterPhysicsProfile currentProfile;
    private JComboBox<String> characterSelector;

    public LiveCharacterPhysicsTester.PhysicsControlPanel(LiveCharacterPhysicsTester.TestCharacterPanel testCharacterPanel) {
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
