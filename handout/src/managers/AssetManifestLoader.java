import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class AssetManifestLoader extends JFrame {
    
    private static final String MANIFEST_FILE = "assets-manifest.json";
    
    private Map<String, List<AssetEntry>> assetsByCategory = new LinkedHashMap<>();
    private List<AssetEntry> allAssets = new ArrayList<>();
    
    private JComboBox<String> categoryCombo;
    private JComboBox<String> fileCombo;
    private JSlider speedSlider;
    private JSlider zoomSlider;
    private JCheckBox flipCheckbox;
    private JLabel frameCounter;
    private JLabel pathLabel;
    private JLabel statsLabel;
    private DisplayPanel displayPanel;
    
    private BufferedImage currentImage;
    private String[] currentFiles = new String[0];
    private int currentFrame = 0;
    private int frameCount = 1;
    private long lastFrameTime = 0L;
    private float playbackSpeed = 1.0f;
    private float zoomLevel = 1.0f;
    private boolean shouldFlip = false;
    private boolean isPlaying = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AssetManifestLoader loader = new AssetManifestLoader();
            loader.setVisible(true);
        });
    }

    public AssetManifestLoader() {
        this.setTitle("🎮 ASSET MANIFEST VIEWER - 1174+ Assets");
        this.setDefaultCloseOperation(3);
        this.setSize(1400, 950);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        
        // Load manifest
        this.loadManifest();
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(30, 30, 40));
        
        JPanel controlPanel = this.createControlPanel();
        mainPanel.add(controlPanel, "North");
        
        this.displayPanel = new DisplayPanel();
        mainPanel.add(new JScrollPane(this.displayPanel), "Center");
        
        JPanel infoPanel = this.createInfoPanel();
        mainPanel.add(infoPanel, "South");
        
        this.add(mainPanel);
        
        this.updateCategoryCombo();
        
        // Animation timer
        Timer timer = new Timer(16, e -> {
            if (this.currentImage != null && this.frameCount > 1 && this.isPlaying) {
                long l2 = System.currentTimeMillis();
                if (this.lastFrameTime == 0L) {
                    this.lastFrameTime = l2;
                }
                long l = l2 - this.lastFrameTime;
                if ((float)l > (100.0f / this.playbackSpeed)) {
                    this.currentFrame = (this.currentFrame + 1) % this.frameCount;
                    this.lastFrameTime = l2;
                    this.frameCounter.setText("Frame: " + (this.currentFrame + 1) + " / " + this.frameCount);
                    this.displayPanel.repaint();
                }
            }
        });
        timer.start();
    }

    private void loadManifest() {
        try {
            File resourceDir = new File("Resources/industrial-zone");
            if (!resourceDir.exists()) {
                System.err.println("[ERROR] Resources directory not found at: " + resourceDir.getAbsolutePath());
                this.statsLabel.setText("❌ Resources not found at: " + resourceDir.getAbsolutePath());
                return;
            }
            
            System.out.println("[✓] Scanning Resources from: " + resourceDir.getAbsolutePath());
            
            // Scan directory recursively
            scanDirectory(resourceDir, resourceDir);
            
            System.out.println("[✓] Found " + this.allAssets.size() + " assets from " + this.assetsByCategory.size() + " categories");
            if (this.statsLabel != null) {
                this.statsLabel.setText("✅ Loaded " + this.allAssets.size() + " assets from Resources/industrial-zone");
            }
            
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load assets: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void scanDirectory(File baseDir, File currentDir) {
        File[] files = currentDir.listFiles();
        if (files == null) return;
        
        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(baseDir, file);
            } else if (file.isFile() && isPNGFile(file)) {
                String relativePath = file.getAbsolutePath()
                    .replace(baseDir.getAbsolutePath(), "")
                    .replaceFirst("^\\\\", ""); // Remove leading backslash
                String category = extractCategory(relativePath);
                
                AssetEntry entry = new AssetEntry(
                    file.getName(),
                    file.getAbsolutePath(),
                    relativePath,
                    category
                );
                
                this.assetsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(entry);
                this.allAssets.add(entry);
            }
        }
    }

    private boolean isPNGFile(File file) {
        return file.getName().toLowerCase().endsWith(".png");
    }

    private String extractCategory(String relativePath) {
        // Extract category from path like: \vfx\1 Smoke\ -> vfx (1 Smoke)
        String[] parts = relativePath.split("\\\\");
        if (parts.length > 1) {
            if (parts[1].equals("characters")) {
                return "characters/" + (parts.length > 2 ? parts[2] : "");
            }
            return parts[1];
        }
        return "other";
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Asset Selection & Controls"));
        panel.setBackground(new Color(40, 40, 50));
        
        panel.add(new JLabel("📂 Category:"));
        this.categoryCombo = new JComboBox<>();
        this.categoryCombo.addActionListener(e -> this.updateFileCombo());
        panel.add(this.categoryCombo);
        
        panel.add(new JLabel("📄 File:"));
        this.fileCombo = new JComboBox<>();
        this.fileCombo.addActionListener(e -> this.loadSelectedAsset());
        panel.add(this.fileCombo);
        
        panel.add(new JLabel("⚡ Speed (25%-200%):"));
        this.speedSlider = new JSlider(25, 200, 100);
        this.speedSlider.setMajorTickSpacing(25);
        this.speedSlider.setPaintTicks(true);
        this.speedSlider.addChangeListener(e -> {
            this.playbackSpeed = (float)this.speedSlider.getValue() / 100.0f;
        });
        panel.add(this.speedSlider);
        
        panel.add(new JLabel("🔍 Zoom (25%-400%):"));
        this.zoomSlider = new JSlider(25, 400, 100);
        this.zoomSlider.setMajorTickSpacing(50);
        this.zoomSlider.setPaintTicks(true);
        this.zoomSlider.addChangeListener(e -> {
            this.zoomLevel = (float)this.zoomSlider.getValue() / 100.0f;
            this.displayPanel.repaint();
        });
        panel.add(this.zoomSlider);
        
        this.flipCheckbox = new JCheckBox("🔄 Flip Horizontal");
        panel.add(this.flipCheckbox);
        
        JButton playButton = new JButton("▶️ Play Animation");
        playButton.addActionListener(e -> {
            this.isPlaying = !this.isPlaying;
            playButton.setText(this.isPlaying ? "⏸️ Pause" : "▶️ Play Animation");
        });
        panel.add(playButton);
        
        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Information"));
        panel.setBackground(new Color(40, 40, 50));
        
        this.frameCounter = new JLabel("📊 Frame: 0 / 0");
        this.frameCounter.setForeground(Color.WHITE);
        panel.add(this.frameCounter);
        
        this.pathLabel = new JLabel("📍 Path: None");
        this.pathLabel.setForeground(Color.CYAN);
        panel.add(this.pathLabel);
        
        this.statsLabel = new JLabel("⏳ Loading manifest...");
        this.statsLabel.setForeground(Color.YELLOW);
        panel.add(this.statsLabel);
        
        return panel;
    }

    private void updateCategoryCombo() {
        this.categoryCombo.removeAllItems();
        for (String category : this.assetsByCategory.keySet()) {
            int count = this.assetsByCategory.get(category).size();
            this.categoryCombo.addItem(category + " (" + count + ")");
        }
    }

    private void updateFileCombo() {
        this.fileCombo.removeAllItems();
        String selected = (String) this.categoryCombo.getSelectedItem();
        if (selected == null) return;
        
        String category = selected.split(" \\(")[0];
        List<AssetEntry> assets = this.assetsByCategory.get(category);
        
        if (assets != null) {
            for (AssetEntry asset : assets) {
                this.fileCombo.addItem(asset.name);
            }
        }
    }

    private void loadSelectedAsset() {
        String selected = (String) this.fileCombo.getSelectedItem();
        if (selected == null) return;
        
        String categorySelected = (String) this.categoryCombo.getSelectedItem();
        if (categorySelected == null) return;
        
        String category = categorySelected.split(" \\(")[0];
        List<AssetEntry> assets = this.assetsByCategory.get(category);
        
        for (AssetEntry asset : assets) {
            if (asset.name.equals(selected)) {
                try {
                    File file = new File(asset.fullPath);
                    if (file.exists()) {
                        this.currentImage = ImageIO.read(file);
                        this.currentFrame = 0;
                        this.frameCount = 1;
                        this.lastFrameTime = 0;
                        this.pathLabel.setText("📍 " + asset.fullPath);
                        this.frameCounter.setText("Frame: 1 / 1");
                        this.statsLabel.setText("✅ Loaded: " + asset.name + " (" + 
                            this.currentImage.getWidth() + "x" + this.currentImage.getHeight() + "px)");
                        this.displayPanel.repaint();
                    } else {
                        this.pathLabel.setText("❌ File not found: " + asset.fullPath);
                        this.statsLabel.setText("Path error - check filesystem");
                    }
                } catch (Exception e) {
                    this.statsLabel.setText("❌ Error: " + e.getMessage());
                }
                return;
            }
        }
    }

    private class DisplayPanel extends JPanel {
        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            
            if (currentImage != null) {
                int displayWidth = (int) (currentImage.getWidth() * zoomLevel);
                int displayHeight = (int) (currentImage.getHeight() * zoomLevel);
                int x = (this.getWidth() - displayWidth) / 2;
                int y = (this.getHeight() - displayHeight) / 2;
                
                if (shouldFlip) {
                    AffineTransform transform = new AffineTransform();
                    transform.translate(x + displayWidth, y);
                    transform.scale(-1, 1);
                    g2d.drawImage(currentImage, transform, null);
                } else {
                    g2d.drawImage(currentImage, x, y, displayWidth, displayHeight, null);
                }
            } else {
                g2d.setColor(Color.GRAY);
                g2d.drawString("No image loaded", this.getWidth() / 2 - 50, this.getHeight() / 2);
            }
        }
    }

    private static class AssetEntry {
        String name;
        String fullPath;
        String relativePath;
        String category;
        
        AssetEntry(String name, String fullPath, String relativePath, String category) {
            this.name = name;
            this.fullPath = fullPath;
            this.relativePath = relativePath;
            this.category = category;
        }
    }
}
