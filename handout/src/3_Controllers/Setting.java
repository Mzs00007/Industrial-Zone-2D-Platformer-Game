/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.screens.Phase15SettingsScreen;
import java.util.List;

public static class Phase15SettingsScreen.Setting {
    public String label;
    public String value;
    public Phase15SettingsScreen.SettingType type;
    public int minValue;
    public int maxValue;
    public List<String> options;

    public Phase15SettingsScreen.Setting(String string, String string2, Phase15SettingsScreen.SettingType settingType) {
        this.label = string;
        this.value = string2;
        this.type = settingType;
    }
}
