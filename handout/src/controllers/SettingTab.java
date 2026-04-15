/*
 * Decompiled with CFR 0.152.
 */
package controllers;

import controllers.Phase15SettingsScreen;
import java.util.ArrayList;
import java.util.List;
public class SettingTab {
    public String name;
    public String category;
    public List<Phase15SettingsScreen.Setting> settings;

    public SettingTab(String string, String string2) {
        this.name = string;
        this.category = string2;
        this.settings = new ArrayList<Phase15SettingsScreen.Setting>();
    }
}
