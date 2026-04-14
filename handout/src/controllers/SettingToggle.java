/*
 * Decompiled with CFR 0.152.
 */
package controllers;

private static class SettingsScreen.SettingToggle {
    String settingName;
    boolean isEnabled;

    public SettingsScreen.SettingToggle(String string, boolean bl) {
        this.settingName = string;
        this.isEnabled = bl;
    }

    public String toString() {
        return this.settingName + ": " + (this.isEnabled ? "ON" : "OFF");
    }
}
