/*
 * Decompiled with CFR 0.152.
 */
package ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public static class AI.AIPathfinder {
    private Map<String, Path> paths = new HashMap<String, Path>();
    private float pathfindingDistance = 50.0f;
    private float stoppingDistance = 10.0f;

    public void registerPath(Path path) {
        this.paths.put(path.name, path);
    }

    public Path getPath(String string) {
        return this.paths.get(string);
    }

    public Path findPath(float f, float f2, float f3, float f4) {
        Path path = new Path("dynamic_path");
        float f5 = f3 - f;
        float f6 = f4 - f2;
        float f7 = (float)Math.sqrt(f5 * f5 + f6 * f6);
        if (f7 > 0.0f) {
            float f8 = f7 / this.pathfindingDistance;
            float f9 = f5 / f8;
            float f10 = f6 / f8;
            int n = 0;
            while ((float)n < f8) {
                path.addWaypoint(f + f9 * (float)n, f2 + f10 * (float)n);
                ++n;
            }
            path.addWaypoint(f3, f4);
        }
        return path;
    }

    public Waypoint getNextWaypoint(Path path, int n) {
        if (path == null || path.getWaypointCount() == 0) {
            return null;
        }
        int n2 = n + 1;
        if (n2 >= path.getWaypointCount()) {
            if (path.loop) {
                n2 = 0;
            } else {
                return null;
            }
        }
        return path.getWaypoint(n2);
    }

    public float[] getDirectionTo(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        float f7 = (float)Math.sqrt(f5 * f5 + f6 * f6);
        if (f7 > 0.0f) {
            return new float[]{f5 / f7, f6 / f7};
        }
        return new float[]{0.0f, 0.0f};
    }

    public boolean isCloseEnough(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        float f7 = (float)Math.sqrt(f5 * f5 + f6 * f6);
        return f7 <= this.stoppingDistance;
    }

    public Collection<Path> getAllPaths() {
        return this.paths.values();
    }

    public void clearPaths() {
        this.paths.clear();
    }

    public float getPathfindingDistance() {
        return this.pathfindingDistance;
    }

    public void setPathfindingDistance(float f) {
        this.pathfindingDistance = f;
    }

    public float getStoppingDistance() {
        return this.stoppingDistance;
    }

    public void setStoppingDistance(float f) {
        this.stoppingDistance = f;
    }

    public static class Path {
        public List<Waypoint> waypoints = new ArrayList<Waypoint>();
        public boolean loop = false;
        public String name;

        public Path(String string) {
            this.name = string;
        }

        public void addWaypoint(Waypoint waypoint) {
            this.waypoints.add(waypoint);
        }

        public void addWaypoint(float f, float f2) {
            this.waypoints.add(new Waypoint(f, f2));
        }

        public Waypoint getWaypoint(int n) {
            if (n >= 0 && n < this.waypoints.size()) {
                return this.waypoints.get(n);
            }
            return null;
        }

        public int getWaypointCount() {
            return this.waypoints.size();
        }
    }

    public static class Waypoint {
        public float x;
        public float y;
        public String name;
        public boolean isCheckpoint = false;

        public Waypoint(float f, float f2) {
            this.x = f;
            this.y = f2;
        }

        public Waypoint(float f, float f2, String string) {
            this.x = f;
            this.y = f2;
            this.name = string;
        }

        public float distanceTo(float f, float f2) {
            float f3 = this.x - f;
            float f4 = this.y - f2;
            return (float)Math.sqrt(f3 * f3 + f4 * f4);
        }
    }
}
