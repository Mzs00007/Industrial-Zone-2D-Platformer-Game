/*
 * Decompiled with CFR 0.152.
 */
package managers;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class LevelCoordinator {
    private Level currentLevel = Level.INDUSTRIAL_ZONE_L1;
    private Map<String, Object> currentLevelSprites = new HashMap<String, Object>();

    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    public void switchLevel(Level level) {
        this.currentLevel = level;
    }

    public void update(long l) {
    }

    public void render(Graphics2D graphics2D) {
    }

    public List<String> getLoadedSprites() {
        return new ArrayList<String>(this.currentLevelSprites.keySet());
    }
public enum Level {
        INDUSTRIAL_ZONE_L1("Level 1: Industrial Zone", "level_1"),
        POWER_STATION_L2("Level 2: Power Station", "level_2");

        public final String displayName;
        public final String levelId;

        private Level(String string2, String string3) {
            this.displayName = string2;
            this.levelId = string3;
        }
    }
}
