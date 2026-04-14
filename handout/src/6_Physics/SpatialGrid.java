/*
 * Decompiled with CFR 0.152.
 */
package physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import physics.CollisionDetector;

public class SpatialGrid {
    private float minX;
    private float minY;
    private float maxX;
    private float maxY;
    private int cellSize;
    private int gridWidth;
    private int gridHeight;
    private Map<Integer, List<Integer>> grid = new HashMap<Integer, List<Integer>>();
    private Map<Integer, Set<Integer>> entityCells = new HashMap<Integer, Set<Integer>>();
    private Map<Integer, CollisionDetector.BoundingBox> entityBounds = new HashMap<Integer, CollisionDetector.BoundingBox>();

    public SpatialGrid(float f, float f2, float f3, float f4, int n) {
        this.minX = f;
        this.minY = f2;
        this.maxX = f3;
        this.maxY = f4;
        this.cellSize = n;
        this.gridWidth = (int)Math.ceil((f3 - f) / (float)n);
        this.gridHeight = (int)Math.ceil((f4 - f2) / (float)n);
    }

    private int getCellId(float f, float f2) {
        int n = (int)Math.floor((f - this.minX) / (float)this.cellSize);
        int n2 = (int)Math.floor((f2 - this.minY) / (float)this.cellSize);
        n = Math.max(0, Math.min(n, this.gridWidth - 1));
        n2 = Math.max(0, Math.min(n2, this.gridHeight - 1));
        return n2 * this.gridWidth + n;
    }

    private Set<Integer> getCellsForBox(CollisionDetector.BoundingBox boundingBox) {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int n = (int)Math.floor((boundingBox.getLeft() - this.minX) / (float)this.cellSize);
        int n2 = (int)Math.floor((boundingBox.getRight() - this.minX) / (float)this.cellSize);
        int n3 = (int)Math.floor((boundingBox.getTop() - this.minY) / (float)this.cellSize);
        int n4 = (int)Math.floor((boundingBox.getBottom() - this.minY) / (float)this.cellSize);
        n = Math.max(0, Math.min(n, this.gridWidth - 1));
        n2 = Math.max(0, Math.min(n2, this.gridWidth - 1));
        n3 = Math.max(0, Math.min(n3, this.gridHeight - 1));
        n4 = Math.max(0, Math.min(n4, this.gridHeight - 1));
        for (int i = n3; i <= n4; ++i) {
            for (int j = n; j <= n2; ++j) {
                hashSet.add(i * this.gridWidth + j);
            }
        }
        return hashSet;
    }

    public void addEntity(int n2, CollisionDetector.BoundingBox boundingBox) {
        this.entityBounds.put(n2, boundingBox);
        Set<Integer> set = this.getCellsForBox(boundingBox);
        for (int n3 : set) {
            this.grid.computeIfAbsent(n3, n -> new ArrayList()).add(n2);
        }
        this.entityCells.put(n2, set);
    }

    public void updateEntity(int n2, CollisionDetector.BoundingBox boundingBox) {
        int n3;
        Set set = this.entityCells.getOrDefault(n2, new HashSet());
        Set<Integer> set2 = this.getCellsForBox(boundingBox);
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            List<Integer> list;
            n3 = (Integer)iterator.next();
            if (set2.contains(n3) || (list = this.grid.get(n3)) == null) continue;
            list.remove((Object)n2);
            if (!list.isEmpty()) continue;
            this.grid.remove(n3);
        }
        iterator = set2.iterator();
        while (iterator.hasNext()) {
            n3 = (Integer)iterator.next();
            if (set.contains(n3)) continue;
            this.grid.computeIfAbsent(n3, n -> new ArrayList()).add(n2);
        }
        this.entityCells.put(n2, set2);
        this.entityBounds.put(n2, boundingBox);
    }

    public void removeEntity(int n) {
        Set<Integer> set = this.entityCells.get(n);
        if (set != null) {
            for (int n2 : set) {
                List<Integer> list = this.grid.get(n2);
                if (list == null) continue;
                list.remove((Object)n);
                if (!list.isEmpty()) continue;
                this.grid.remove(n2);
            }
        }
        this.entityCells.remove(n);
        this.entityBounds.remove(n);
    }

    public List<Integer> getNearbyEntities(int n) {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        Set<Integer> set = this.entityCells.get(n);
        if (set == null) {
            return new ArrayList<Integer>();
        }
        for (int n2 : set) {
            List<Integer> list = this.grid.get(n2);
            if (list != null) {
                hashSet.addAll(list);
            }
            int n3 = n2 % this.gridWidth;
            int n4 = n2 / this.gridWidth;
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    int n5;
                    List<Integer> list2;
                    if (j == 0 && i == 0) continue;
                    int n6 = n3 + j;
                    int n7 = n4 + i;
                    if (n6 < 0 || n6 >= this.gridWidth || n7 < 0 || n7 >= this.gridHeight || (list2 = this.grid.get(n5 = n7 * this.gridWidth + n6)) == null) continue;
                    hashSet.addAll(list2);
                }
            }
        }
        hashSet.remove(n);
        return new ArrayList<Integer>(hashSet);
    }

    public List<Integer> getAllEntities() {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        for (List<Integer> list : this.grid.values()) {
            hashSet.addAll(list);
        }
        return new ArrayList<Integer>(hashSet);
    }

    public void clear() {
        this.grid.clear();
        this.entityCells.clear();
        this.entityBounds.clear();
    }

    public String getStatistics() {
        return String.format("SpatialGrid[%dx%d cells, %d entities, %d occupied cells]", this.gridWidth, this.gridHeight, this.entityBounds.size(), this.grid.size());
    }
}
