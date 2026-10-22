package com.provismet.tooltipscroll;

import com.provismet.tooltipscroll.config.OSLTooltipConfig;

public final class Options {
    private static final OSLTooltipConfig CONFIG = OSLTooltipConfig.INSTANCE;

    private Options() {}

    public static boolean canScroll() { return CONFIG.canScroll.get(); }
    public static boolean useWASD() { return CONFIG.useWASD.get(); }
    public static boolean startOnTop() { return CONFIG.startOnTop.get(); }
    public static boolean resetOnUnlock() { return CONFIG.resetOnUnlock.get(); }
    public static boolean useLShift() { return CONFIG.useLShift.get(); }
    public static boolean invertXScroll() { return CONFIG.invertXScroll.get(); }
    public static boolean invertYScroll() { return CONFIG.invertYScroll.get(); }
    public static boolean matrixMode() { return CONFIG.matrixCompatibilityMode.get(); }

    public static int scrollSpeed() { return CONFIG.scrollSpeed.get(); }
    public static int keyboardScrollSpeed() { return CONFIG.keyboardScrollSpeed.get(); }
    public static double smoothness() { return CONFIG.scrollSmoothness.get(); }
}
