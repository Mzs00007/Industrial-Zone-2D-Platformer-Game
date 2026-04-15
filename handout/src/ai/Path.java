/*
 * Decompiled with CFR 0.152.
 */
package ai;

import ai.AI;
import java.util.ArrayList;
import java.util.List;
public class Path {
    public List<AI.AIPathfinder.Waypoint> waypoints = new ArrayList<AI.AIPathfinder.Waypoint>();
    public boolean loop = false;
    public String name;

    public Path(String string) {
        this.name = string;
    }

    public void addWaypoint(AI.AIPathfinder.Waypoint waypoint) {
        this.waypoints.add(waypoint);
    }

    public void addWaypoint(float f, float f2) {
        this.waypoints.add(new AI.AIPathfinder.Waypoint(f, f2));
    }

    public AI.AIPathfinder.Waypoint getWaypoint(int n) {
        if (n >= 0 && n < this.waypoints.size()) {
            return this.waypoints.get(n);
        }
        return null;
    }

    public int getWaypointCount() {
        return this.waypoints.size();
    }
}
