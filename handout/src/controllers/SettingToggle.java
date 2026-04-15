/*
 * Decompiled with CFR 0.152.
 */
package controllers;
class SettingToggle {
    String settingName;
    boolean isEnabled;

    public SettingToggle(String string, boolean bl) {
        this.settingName = string;
        this.isEnabled = bl;
    }

    public String toString() {
        return this.settingName + ": " + (this.isEnabled ? "ON" : "OFF");
    }
}
