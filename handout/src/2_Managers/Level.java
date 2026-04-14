/*
 * Decompiled with CFR 0.152.
 */
package core;

public static enum Core.LevelCoordinator.Level {
    INDUSTRIAL_ZONE_L1("Level 1: Industrial Zone", "level_1"),
    POWER_STATION_L2("Level 2: Power Station", "level_2");

    public final String displayName;
    public final String levelId;

    private Core.LevelCoordinator.Level(String string2, String string3) {
        this.displayName = string2;
        this.levelId = string3;
    }
}
