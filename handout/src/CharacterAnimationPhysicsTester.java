/*
 * Decompiled with CFR 0.152.
 */
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class CharacterAnimationPhysicsTester
extends JFrame {
    private TestPanel panel;

    public CharacterAnimationPhysicsTester() {
        this.setTitle("Character Animation Physics & Enemy AI Tester");
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.panel = new TestPanel();
        this.add(this.panel);
        this.setSize(1400, 900);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] stringArray) {
        SwingUtilities.invokeLater(() -> new CharacterAnimationPhysicsTester());
    }
}
