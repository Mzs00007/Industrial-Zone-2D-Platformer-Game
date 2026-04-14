/*
 * Decompiled with CFR 0.152.
 */
package physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import physics.SpatialGrid;

public class CollisionDetector {
    private Map<Integer, BoundingBox> entityBounds = new HashMap<Integer, BoundingBox>();
    private int boxIdCounter = 0;
    private SpatialGrid spatialGrid = null;
    private boolean useSpatialOptimization = true;

    public CollisionDetector() {
        this(true);
    }

    public CollisionDetector(boolean bl) {
        this.useSpatialOptimization = bl;
        if (bl) {
            this.spatialGrid = new SpatialGrid(0.0f, 0.0f, 1280.0f, 720.0f, 32);
        }
    }

    public void setSpatialOptimization(boolean bl) {
        if (bl && this.spatialGrid == null) {
            this.spatialGrid = new SpatialGrid(0.0f, 0.0f, 1280.0f, 720.0f, 32);
        }
        this.useSpatialOptimization = bl;
    }

    public int registerBoundingBox(float f, float f2, float f3, float f4) {
        int n = this.boxIdCounter++;
        BoundingBox boundingBox = new BoundingBox(f, f2, f3, f4);
        this.entityBounds.put(n, boundingBox);
        if (this.useSpatialOptimization && this.spatialGrid != null) {
            this.spatialGrid.addEntity(n, boundingBox);
        }
        return n;
    }

    public void updatePosition(int n, float f, float f2) {
        BoundingBox boundingBox = this.entityBounds.get(n);
        if (boundingBox != null) {
            boundingBox.setPosition(f, f2);
            if (this.useSpatialOptimization && this.spatialGrid != null) {
                this.spatialGrid.updateEntity(n, boundingBox);
            }
        }
    }

    public void moveBox(int n, float f, float f2) {
        BoundingBox boundingBox = this.entityBounds.get(n);
        if (boundingBox != null) {
            boundingBox.move(f, f2);
            if (this.useSpatialOptimization && this.spatialGrid != null) {
                this.spatialGrid.updateEntity(n, boundingBox);
            }
        }
    }

    public BoundingBox getBoundingBox(int n) {
        return this.entityBounds.get(n);
    }

    public CollisionResult checkCollision(BoundingBox boundingBox, BoundingBox boundingBox2) {
        CollisionResult collisionResult = new CollisionResult();
        if (boundingBox.getRight() < boundingBox2.getLeft() || boundingBox.getLeft() > boundingBox2.getRight() || boundingBox.getBottom() < boundingBox2.getTop() || boundingBox.getTop() > boundingBox2.getBottom()) {
            collisionResult.colliding = false;
            return collisionResult;
        }
        collisionResult.colliding = true;
        float f = boundingBox.getRight() - boundingBox2.getLeft();
        float f2 = boundingBox2.getRight() - boundingBox.getLeft();
        float f3 = boundingBox.getBottom() - boundingBox2.getTop();
        float f4 = boundingBox2.getBottom() - boundingBox.getTop();
        float f5 = Math.min(f, f2);
        float f6 = Math.min(f3, f4);
        collisionResult.overlapX = f5;
        collisionResult.overlapY = f6;
        if (f6 < f5) {
            if (boundingBox.getCenterY() < boundingBox2.getCenterY()) {
                collisionResult.side = "top";
                collisionResult.penetrationY = -f3;
            } else {
                collisionResult.side = "bottom";
                collisionResult.penetrationY = f4;
            }
        } else if (boundingBox.getCenterX() < boundingBox2.getCenterX()) {
            collisionResult.side = "left";
            collisionResult.penetrationX = -f;
        } else {
            collisionResult.side = "right";
            collisionResult.penetrationX = f2;
        }
        return collisionResult;
    }

    public CollisionResult checkCollision(int n, int n2) {
        BoundingBox boundingBox = this.entityBounds.get(n);
        BoundingBox boundingBox2 = this.entityBounds.get(n2);
        if (boundingBox == null || boundingBox2 == null) {
            return new CollisionResult();
        }
        return this.checkCollision(boundingBox, boundingBox2);
    }

    public boolean pointInBox(float f, float f2, BoundingBox boundingBox) {
        return f >= boundingBox.getLeft() && f <= boundingBox.getRight() && f2 >= boundingBox.getTop() && f2 <= boundingBox.getBottom();
    }

    public boolean pointInBox(float f, float f2, int n) {
        BoundingBox boundingBox = this.entityBounds.get(n);
        return boundingBox != null && this.pointInBox(f, f2, boundingBox);
    }

    public float getDistance(BoundingBox boundingBox, BoundingBox boundingBox2) {
        float f = boundingBox.getCenterX() - boundingBox2.getCenterX();
        float f2 = boundingBox.getCenterY() - boundingBox2.getCenterY();
        return (float)Math.sqrt(f * f + f2 * f2);
    }

    public float getDistance(int n, int n2) {
        BoundingBox boundingBox = this.entityBounds.get(n);
        BoundingBox boundingBox2 = this.entityBounds.get(n2);
        if (boundingBox == null || boundingBox2 == null) {
            return Float.MAX_VALUE;
        }
        return this.getDistance(boundingBox, boundingBox2);
    }

    public boolean circleBoxCollision(float f, float f2, float f3, BoundingBox boundingBox) {
        float f4;
        float f5;
        float f6 = Math.max(boundingBox.getLeft(), Math.min(f, boundingBox.getRight()));
        float f7 = f - f6;
        float f8 = (float)Math.sqrt(f7 * f7 + (f5 = f2 - (f4 = Math.max(boundingBox.getTop(), Math.min(f2, boundingBox.getBottom())))) * f5);
        return f8 < f3;
    }

    public void resolveCollision(int n, int n2) {
        BoundingBox boundingBox = this.entityBounds.get(n);
        BoundingBox boundingBox2 = this.entityBounds.get(n2);
        if (boundingBox == null || boundingBox2 == null) {
            return;
        }
        CollisionResult collisionResult = this.checkCollision(boundingBox, boundingBox2);
        if (!collisionResult.colliding) {
            return;
        }
        if (collisionResult.side != null) {
            switch (collisionResult.side) {
                case "left": {
                    boundingBox.move(collisionResult.penetrationX, 0.0f);
                    break;
                }
                case "right": {
                    boundingBox.move(collisionResult.penetrationX, 0.0f);
                    break;
                }
                case "top": {
                    boundingBox.move(0.0f, collisionResult.penetrationY);
                    break;
                }
                case "bottom": {
                    boundingBox.move(0.0f, collisionResult.penetrationY);
                }
            }
        }
    }

    public List<Integer> getCollidingBoxes(int n) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        BoundingBox boundingBox = this.entityBounds.get(n);
        if (boundingBox == null) {
            return arrayList;
        }
        if (this.useSpatialOptimization && this.spatialGrid != null) {
            List<Integer> list = this.spatialGrid.getNearbyEntities(n);
            for (int n2 : list) {
                BoundingBox boundingBox2 = this.entityBounds.get(n2);
                if (boundingBox2 == null) continue;
                CollisionResult collisionResult = this.checkCollision(boundingBox, boundingBox2);
                if (!collisionResult.colliding) continue;
                arrayList.add(n2);
            }
        } else {
            for (Map.Entry<Integer, BoundingBox> entry : this.entityBounds.entrySet()) {
                if (entry.getKey() == n) continue;
                CollisionResult collisionResult = this.checkCollision(boundingBox, entry.getValue());
                if (!collisionResult.colliding) continue;
                arrayList.add(entry.getKey());
            }
        }
        return arrayList;
    }

    public void removeBoundingBox(int n) {
        this.entityBounds.remove(n);
        if (this.useSpatialOptimization && this.spatialGrid != null) {
            this.spatialGrid.removeEntity(n);
        }
    }

    public void clear() {
        this.entityBounds.clear();
        this.boxIdCounter = 0;
        if (this.useSpatialOptimization && this.spatialGrid != null) {
            this.spatialGrid.clear();
        }
    }

    public int getBoxCount() {
        return this.entityBounds.size();
    }

    public String getSpatialGridStats() {
        if (this.spatialGrid == null) {
            return "Spatial grid disabled";
        }
        return this.spatialGrid.getStatistics();
    }

    public boolean isSpatialOptimizationActive() {
        return this.useSpatialOptimization && this.spatialGrid != null;
    }

    public static class BoundingBox {
        public float x;
        public float y;
        public float width;
        public float height;

        public BoundingBox(float f, float f2, float f3, float f4) {
            this.x = f;
            this.y = f2;
            this.width = f3;
            this.height = f4;
        }

        public float getLeft() {
            return this.x;
        }

        public float getRight() {
            return this.x + this.width;
        }

        public float getTop() {
            return this.y;
        }

        public float getBottom() {
            return this.y + this.height;
        }

        public float getCenterX() {
            return this.x + this.width / 2.0f;
        }

        public float getCenterY() {
            return this.y + this.height / 2.0f;
        }

        public void move(float f, float f2) {
            this.x += f;
            this.y += f2;
        }

        public void setPosition(float f, float f2) {
            this.x = f;
            this.y = f2;
        }

        public BoundingBox copy() {
            return new BoundingBox(this.x, this.y, this.width, this.height);
        }
    }

    public static class CollisionResult {
        public boolean colliding = false;
        public float overlapX = 0.0f;
        public float overlapY = 0.0f;
        public float penetrationX = 0.0f;
        public float penetrationY = 0.0f;
        public String side = null;
    }
}
