/*
 * Decompiled with CFR 0.152.
 */
package gui.screens;

import gui.screens.Screen;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Phase8MinimapScreen
extends Screen {
    private static final int MINIMAP_WIDTH = 200;
    private static final int MINIMAP_HEIGHT = 150;
    private static final float WORLD_SCALE = 0.05f;
    private float playerX = 100.0f;
    private float playerY = 75.0f;
    private float cameraX = 0.0f;
    private float cameraY = 0.0f;
    private List<MapEntity> enemies = new ArrayList<MapEntity>();
    private List<MapEntity> items = new ArrayList<MapEntity>();
    private List<MapEntity> waypoints = new ArrayList<MapEntity>();

    public Phase8MinimapScreen() {
        super(null);
        this.initializeMapEntities();
    }

    private void initializeMapEntities() {
        this.enemies.add(new MapEntity(100.0f, 50.0f, new Color(255, 50, 50), 4));
        this.enemies.add(new MapEntity(150.0f, 120.0f, new Color(255, 100, 100), 4));
        this.enemies.add(new MapEntity(50.0f, 100.0f, new Color(255, 50, 50), 4));
        this.items.add(new MapEntity(75.0f, 75.0f, new Color(255, 255, 50), 3));
        this.items.add(new MapEntity(180.0f, 40.0f, new Color(255, 200, 50), 3));
        this.waypoints.add(new MapEntity(200.0f, 150.0f, new Color(50, 100, 255), 5));
        this.waypoints.add(new MapEntity(10.0f, 10.0f, new Color(100, 150, 255), 5));
    }

    @Override
    public void update(float f) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public BufferedImage render(int n, int n2) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 2);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        try {
            int n3 = n - 200 - 20;
            int n4 = 20;
            graphics2D.setColor(new Color(20, 20, 20, 220));
            graphics2D.fillRect(n3, n4, 200, 150);
            graphics2D.setColor(new Color(100, 150, 200, 255));
            graphics2D.setStroke(new BasicStroke(2.0f));
            graphics2D.drawRect(n3, n4, 200, 150);
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(new Font("Arial", 1, 11));
            graphics2D.drawString("MAP", n3 + 5, n4 + 12);
            int n5 = n3 + 2;
            int n6 = n4 + 15;
            int n7 = 196;
            int n8 = 133;
            graphics2D.setColor(new Color(40, 40, 60, 200));
            graphics2D.fillRect(n5, n6, n7, n8);
            graphics2D.setColor(new Color(60, 60, 80, 100));
            graphics2D.setStroke(new BasicStroke(0.5f));
            for (int i = 0; i <= 4; ++i) {
                int n9 = n5 + i * n7 / 4;
                int n10 = n6 + i * n8 / 4;
                graphics2D.drawLine(n9, n6, n9, n6 + n8);
                graphics2D.drawLine(n5, n10, n5 + n7, n10);
            }
            for (MapEntity mapEntity : this.waypoints) {
                this.renderMapEntity(graphics2D, mapEntity, n5, n6, n7, n8);
            }
            for (MapEntity mapEntity : this.items) {
                this.renderMapEntity(graphics2D, mapEntity, n5, n6, n7, n8);
            }
            for (MapEntity mapEntity : this.enemies) {
                this.renderMapEntity(graphics2D, mapEntity, n5, n6, n7, n8);
            }
            int n11 = n5 + n7 / 2;
            int n12 = n6 + n8 / 2;
            this.renderPlayerMarker(graphics2D, n11, n12);
            this.renderLegend(graphics2D, n3 + 5, n4 + 150 + 10);
        }
        finally {
            graphics2D.dispose();
        }
        return bufferedImage;
    }

    private void renderMapEntity(Graphics2D graphics2D, MapEntity mapEntity, int n, int n2, int n3, int n4) {
        int n5 = n + (int)(mapEntity.x * 0.05f);
        int n6 = n2 + (int)(mapEntity.y * 0.05f);
        if (n5 < n) {
            n5 = n;
        }
        if (n5 > n + n3) {
            n5 = n + n3 - mapEntity.size;
        }
        if (n6 < n2) {
            n6 = n2;
        }
        if (n6 > n2 + n4) {
            n6 = n2 + n4 - mapEntity.size;
        }
        graphics2D.setColor(mapEntity.color);
        graphics2D.fillOval(n5, n6, mapEntity.size, mapEntity.size);
        graphics2D.setColor(new Color(200, 200, 200, 150));
        graphics2D.setStroke(new BasicStroke(0.5f));
        graphics2D.drawOval(n5, n6, mapEntity.size, mapEntity.size);
    }

    private void renderPlayerMarker(Graphics2D graphics2D, int n, int n2) {
        int[] nArray = new int[]{n, n - 4, n + 4};
        int[] nArray2 = new int[]{n2 - 5, n2 + 3, n2 + 3};
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillPolygon(nArray, nArray2, 3);
        graphics2D.setColor(new Color(100, 200, 255));
        graphics2D.setStroke(new BasicStroke(1.0f));
        graphics2D.drawPolygon(nArray, nArray2, 3);
    }

    private void renderLegend(Graphics2D graphics2D, int n, int n2) {
        graphics2D.setFont(new Font("Arial", 0, 8));
        graphics2D.setColor(new Color(200, 200, 200, 200));
        int n3 = 4;
        int n4 = 12;
        int n5 = 0;
        graphics2D.setColor(Color.WHITE);
        int n6 = n;
        int n7 = n2 + n5 * n4;
        graphics2D.fillPolygon(new int[]{n6, n6 - 2, n6 + 2}, new int[]{n7 - 2, n7 + 1, n7 + 1}, 3);
        graphics2D.setColor(new Color(180, 180, 180));
        graphics2D.drawString("You", n + 12, n7 + 3);
        graphics2D.setColor(new Color(255, 50, 50));
        graphics2D.fillOval(n, n2 + ++n5 * n4 - 2, n3, n3);
        graphics2D.setColor(new Color(180, 180, 180));
        graphics2D.drawString("Enemy", n + 12, n2 + n5 * n4 + 3);
        graphics2D.setColor(new Color(255, 255, 50));
        graphics2D.fillOval(n, n2 + ++n5 * n4 - 2, n3, n3);
        graphics2D.setColor(new Color(180, 180, 180));
        graphics2D.drawString("Item", n + 12, n2 + n5 * n4 + 3);
        graphics2D.setColor(new Color(50, 150, 255));
        graphics2D.fillOval(n, n2 + ++n5 * n4 - 2, n3 + 1, n3 + 1);
        graphics2D.setColor(new Color(180, 180, 180));
        graphics2D.drawString("Waypoint", n + 12, n2 + n5 * n4 + 3);
    }

    public void setPlayerPosition(float f, float f2) {
        this.playerX = f * 0.05f;
        this.playerY = f2 * 0.05f;
    }

    public void addEnemy(float f, float f2) {
        this.enemies.add(new MapEntity(f, f2, new Color(255, 50, 50), 4));
    }

    public void addItem(float f, float f2) {
        this.items.add(new MapEntity(f, f2, new Color(255, 255, 50), 3));
    }

    public void clearEntities() {
        this.enemies.clear();
        this.items.clear();
        this.waypoints.clear();
    }

    private static class MapEntity {
        float x;
        float y;
        Color color;
        int size;

        MapEntity(float f, float f2, Color color, int n) {
            this.x = f;
            this.y = f2;
            this.color = color;
            this.size = n;
        }
    }
}
