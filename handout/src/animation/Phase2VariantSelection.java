/*
 * Decompiled with CFR 0.152.
 */
package animation;
public class Phase2VariantSelection {
    public static final String PHASE_NAME = "PHASE 2: VARIANT SELECTION";
public final class Level2VariantLogic {
        public static final String LEVEL = "Power Station Level 2";
        public static final String LOGIC = "SELECT based on game time";
        public static final String DAY_PERIOD = "6:00 AM to 6:00 PM \u2192 Use Day variant";
        public static final String NIGHT_PERIOD = "6:00 PM to 6:00 AM \u2192 Use Night variant";
        public static final String TRANSITION_AT_6AM = "Sunrise: Fade from Night \u2192 Day over 45 seconds";
        public static final String TRANSITION_AT_6PM = "Sunset: Fade from Day \u2192 Night over 45 seconds";
    }
public final class Level1VariantLogic {
        public static final String LEVEL = "Industrial Zone Level 1";
        public static final String LOGIC = "SKIP PHASE 2: Use single consistent daylight variant always";
        public static final String REASON = "Level 1 has no time-of-day system; always displays industrial daylight";
    }
}
