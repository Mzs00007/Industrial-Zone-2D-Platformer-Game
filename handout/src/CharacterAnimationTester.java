/*
 * Decompiled with CFR 0.152.
 */
import animation.AnimationAndSpriteLoader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class CharacterAnimationTester
extends JFrame {
    private Map<String, String> assetPaths = AnimationAndSpriteLoader.getAssetPathsMap();
    private JComboBox<String> assetCombo;
    private JComboBox<String> fileCombo;
    private JSlider speedSlider;
    private JSlider zoomSlider;
    private JCheckBox flipCheckbox;
    private JCheckBox checkerboardCheckbox;
    private JLabel frameCounter;
    private JLabel pathLabel;
    private JLabel statsLabel;
    private DisplayPanel displayPanel;
    private JButton systemInfoButton;
    private BufferedImage currentImage;
    private String[] currentFiles = new String[0];
    private int currentFrame = 0;
    private int frameCount = 1;
    private long lastFrameTime = 0L;
    private float playbackSpeed = 1.0f;
    private float zoomLevel = 1.0f;
    private boolean shouldFlip = false;
    private boolean showCheckerboard = true;
    private boolean isPlaying = false;

    public CharacterAnimationTester() {
        this.setTitle("\ud83c\udfae COMPREHENSIVE ASSET & ANIMATION TESTER v3.0");
        this.setDefaultCloseOperation(3);
        this.setSize(1300, 900);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        JPanel jPanel = new JPanel(new BorderLayout(10, 10));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel.setBackground(new Color(30, 30, 40));
        JPanel jPanel2 = this.createControlPanel();
        jPanel.add((Component)jPanel2, "North");
        this.displayPanel = new DisplayPanel();
        jPanel.add((Component)new JScrollPane(this.displayPanel), "Center");
        JPanel jPanel3 = this.createInfoPanel();
        jPanel.add((Component)jPanel3, "South");
        this.add(jPanel);
        this.updateFileCombo();
        Timer timer = new Timer(16, actionEvent -> {
            if (this.currentImage != null && this.frameCount > 1 && this.isPlaying) {
                float f;
                long l;
                long l2 = System.currentTimeMillis();
                if (this.lastFrameTime == 0L) {
                    this.lastFrameTime = l2;
                }
                if ((float)(l = l2 - this.lastFrameTime) > (f = 100.0f / this.playbackSpeed)) {
                    this.currentFrame = (this.currentFrame + 1) % this.frameCount;
                    this.lastFrameTime = l2;
                    this.frameCounter.setText("Frame: " + (this.currentFrame + 1) + " / " + this.frameCount);
                    this.displayPanel.repaint();
                }
            }
        });
        timer.start();
    }

    private JPanel createControlPanel() {
        JPanel jPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        jPanel.setBorder(BorderFactory.createTitledBorder("Asset Selection & Controls"));
        jPanel.setBackground(new Color(40, 40, 50));
        jPanel.add(new JLabel("\ud83d\udcc2 Asset Type:"));
        this.assetCombo = new JComboBox<String>(this.assetPaths.keySet().toArray(new String[0]));
        this.assetCombo.addActionListener(actionEvent -> this.updateFileCombo());
        jPanel.add(this.assetCombo);
        jPanel.add(new JLabel(" "));
        jPanel.add(new JLabel("\ud83d\udcc4 File:"));
        this.fileCombo = new JComboBox();
        this.fileCombo.addActionListener(actionEvent -> this.loadSelectedImage());
        jPanel.add(this.fileCombo);
        this.systemInfoButton = new JButton("\u2139\ufe0f  System Info");
        this.systemInfoButton.addActionListener(actionEvent -> this.showSystemInfo());
        jPanel.add(this.systemInfoButton);
        jPanel.add(new JLabel("\u26a1 Speed (25%-200%):"));
        this.speedSlider = new JSlider(25, 200, 100);
        this.speedSlider.setMajorTickSpacing(25);
        this.speedSlider.setPaintTicks(true);
        this.speedSlider.addChangeListener(changeEvent -> {
            this.playbackSpeed = (float)this.speedSlider.getValue() / 100.0f;
        });
        jPanel.add(this.speedSlider);
        jPanel.add(new JLabel(" "));
        jPanel.add(new JLabel("\ud83d\udd0d Zoom (25%-400%):"));
        this.zoomSlider = new JSlider(25, 400, 100);
        this.zoomSlider.setMajorTickSpacing(50);
        this.zoomSlider.setPaintTicks(true);
        this.zoomSlider.addChangeListener(changeEvent -> {
            this.zoomLevel = (float)this.zoomSlider.getValue() / 100.0f;
            this.displayPanel.repaint();
        });
        jPanel.add(this.zoomSlider);
        jPanel.add(new JLabel(" "));
        this.flipCheckbox = new JCheckBox("\ud83d\udd04 Flip Horizontal");
        this.flipCheckbox.addChangeListener(changeEvent -> {
            this.shouldFlip = this.flipCheckbox.isSelected();
            this.displayPanel.repaint();
        });
        jPanel.add(this.flipCheckbox);
        jPanel.add(new JLabel(" "));
        this.checkerboardCheckbox = new JCheckBox("\ud83c\udfb2 Checkerboard BG");
        this.checkerboardCheckbox.setSelected(true);
        this.checkerboardCheckbox.addChangeListener(changeEvent -> {
            this.showCheckerboard = this.checkerboardCheckbox.isSelected();
            this.displayPanel.repaint();
        });
        jPanel.add(this.checkerboardCheckbox);
        return jPanel;
    }

    private JPanel createInfoPanel() {
        JPanel jPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        jPanel.setBorder(BorderFactory.createTitledBorder("Information"));
        jPanel.setBackground(new Color(40, 40, 50));
        this.frameCounter = new JLabel("\ud83d\udccd Frame: 0 / 0");
        this.frameCounter.setFont(new Font("Monospaced", 0, 12));
        jPanel.add(this.frameCounter);
        this.pathLabel = new JLabel("\ud83d\udcc1 Path: [No asset selected]");
        this.pathLabel.setFont(new Font("Monospaced", 0, 11));
        jPanel.add(this.pathLabel);
        this.statsLabel = new JLabel("\ud83d\udcca Stats: [Awaiting selection]");
        this.statsLabel.setFont(new Font("Monospaced", 0, 11));
        jPanel.add(this.statsLabel);
        return jPanel;
    }

    private void updateFileCombo() {
        this.fileCombo.removeAllItems();
        String string2 = (String)this.assetCombo.getSelectedItem();
        if (string2 == null) {
            this.fileCombo.addItem("[No asset selected]");
            return;
        }
        String string3 = this.assetPaths.get(string2);
        File file2 = new File(string3);
        if (!file2.exists() || !file2.isDirectory()) {
            this.fileCombo.addItem("[Directory not found: " + string3 + "]");
            this.currentFiles = new String[0];
            return;
        }
        Object[] objectArray = file2.listFiles((file, string) -> string.toLowerCase().endsWith(".png"));
        if (objectArray == null || objectArray.length == 0) {
            this.fileCombo.addItem("[No PNG files found]");
            this.currentFiles = new String[0];
            return;
        }
        this.currentFiles = new String[objectArray.length];
        Arrays.sort(objectArray);
        for (int i = 0; i < objectArray.length; ++i) {
            this.currentFiles[i] = ((File)objectArray[i]).getName();
            this.fileCombo.addItem(this.currentFiles[i]);
        }
    }

    private void loadSelectedImage() {
        String string = (String)this.assetCombo.getSelectedItem();
        String string2 = (String)this.fileCombo.getSelectedItem();
        if (string == null || string2 == null || string2.startsWith("[")) {
            this.currentImage = null;
            this.displayPanel.repaint();
            return;
        }
        String string3 = this.assetPaths.get(string) + string2;
        try {
            this.currentImage = ImageIO.read(new File(string3));
            this.currentFrame = 0;
            this.lastFrameTime = 0L;
            this.isPlaying = true;
            int n = this.currentImage.getWidth();
            int n2 = this.currentImage.getHeight();
            this.frameCount = Math.max(1, n / Math.max(1, n2));
            this.pathLabel.setText("\ud83d\udcc1 Path: " + string3);
            this.statsLabel.setText(String.format("\ud83d\udcca Image: %d\u00d7%d px | Est. Frames: %d", n, n2, this.frameCount));
            this.frameCounter.setText("Frame: 1 / " + this.frameCount);
            this.displayPanel.repaint();
        }
        catch (Exception exception) {
            this.pathLabel.setText("\u274c Error loading: " + exception.getMessage());
            this.currentImage = null;
        }
    }

    private void showSystemInfo() {
        String string = AnimationAndSpriteLoader.printDiagnostics();
        JTextArea jTextArea = new JTextArea(string);
        jTextArea.setEditable(false);
        jTextArea.setFont(new Font("Monospaced", 0, 10));
        jTextArea.setBackground(new Color(30, 30, 40));
        jTextArea.setForeground(Color.WHITE);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setPreferredSize(new Dimension(700, 600));
        JOptionPane.showMessageDialog(this, jScrollPane, "\ud83c\udfae System Diagnostics", 1);
    }

    public static void main(String[] stringArray) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("\n\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557");
            System.out.println("\u2551    \ud83c\udfae COMPREHENSIVE ASSET & ANIMATION TESTER v3.0             \u2551");
            System.out.println("\u255a\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255d\n");
            System.out.println("\u2713 All 100+ asset paths via AnimationAndSpriteLoader public API");
            System.out.println("\u2713 Browse, preview, and test ANY asset in the game");
            System.out.println("\u2713 Speed, zoom, flip, and transparency controls");
            System.out.println("\u2713 System diagnostics integrated\n");
            new CharacterAnimationTester().setVisible(true);
        });
    }

    private class DisplayPanel
    extends JPanel {
        private DisplayPanel() {
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D graphics2D = (Graphics2D)graphics;
            int n = this.getWidth();
            int n2 = this.getHeight();
            if (CharacterAnimationTester.this.showCheckerboard) {
                this.drawCheckerboard(graphics2D, n, n2);
            }
            if (CharacterAnimationTester.this.currentImage == null) {
                graphics2D.setColor(Color.WHITE);
                graphics2D.setFont(new Font("Arial", 1, 20));
                graphics2D.drawString("Select an asset to preview", n / 2 - 150, n2 / 2);
                return;
            }
            int n3 = CharacterAnimationTester.this.currentImage.getWidth() / CharacterAnimationTester.this.frameCount;
            int n4 = CharacterAnimationTester.this.currentImage.getHeight();
            int n5 = (int)((float)n3 * CharacterAnimationTester.this.zoomLevel);
            int n6 = (int)((float)n4 * CharacterAnimationTester.this.zoomLevel);
            int n7 = (n - n5) / 2;
            int n8 = (n2 - n6) / 2;
            int n9 = CharacterAnimationTester.this.currentFrame * n3;
            BufferedImage bufferedImage = CharacterAnimationTester.this.currentImage.getSubimage(n9, 0, n3, n4);
            if (CharacterAnimationTester.this.shouldFlip) {
                AffineTransform affineTransform = AffineTransform.getScaleInstance(-1.0, 1.0);
                affineTransform.translate(-n3, 0.0);
                graphics2D.drawImage(bufferedImage, affineTransform, this);
            } else {
                graphics2D.drawImage(bufferedImage, n7, n8, n5, n6, this);
            }
        }

        private void drawCheckerboard(Graphics2D graphics2D, int n, int n2) {
            int n3 = 16;
            for (int i = 0; i < n; i += n3) {
                for (int j = 0; j < n2; j += n3) {
                    if ((i / n3 + j / n3) % 2 == 0) {
                        graphics2D.setColor(new Color(50, 50, 60));
                    } else {
                        graphics2D.setColor(new Color(70, 70, 80));
                    }
                    graphics2D.fillRect(i, j, n3, n3);
                }
            }
        }
    }

    private static enum AssetCategory {
        CHARACTERS("\ud83c\udfad  Characters"),
        LEVELS("\ud83c\udfed  Levels"),
        GUI("\ud83c\udfa8  GUI Elements"),
        VFX("\u2728  Visual Effects"),
        WEAPONS("\ud83d\udd2b  Weapons"),
        AUDIO("\ud83d\udd0a  Audio"),
        INPUT("\u2328\ufe0f   Input Devices");

        final String label;

        private AssetCategory(String string2) {
            this.label = string2;
        }
    }
}
