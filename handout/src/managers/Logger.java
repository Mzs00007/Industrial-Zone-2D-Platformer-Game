/*
 * Decompiled with CFR 0.152.
 */
package managers;
public class Logger {
    public void info(String string) {
        System.out.println("[INFO] " + string);
    }

    public void warn(String string) {
        System.out.println("[WARN] " + string);
    }

    public void error(String string) {
        System.err.println("[ERROR] " + string);
    }

    public void debug(String string) {
        System.out.println("[DEBUG] " + string);
    }
}
