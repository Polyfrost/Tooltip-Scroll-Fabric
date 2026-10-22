package com.provismet.tooltipscroll;

import java.util.List;

import org.apache.commons.lang3.mutable.MutableDouble;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ScrollTracker {
    // render functions are called every frame, so the offset needs to be saved somewhere
    private static double currentXOffset = 0;
    private static double currentYOffset = 0;

    private static double trueXOffset = 0;
    private static double trueYOffset = 0;

    private static boolean moved = false;

    private static boolean wheelConsumed = false;

    // save the currently selected item's tooltip lines; scroll resets if the user hovers over a different item
    private static List<String> currentItem;

    private static long unlockTime = System.currentTimeMillis();
    private static final long RELOCK_AT = 100;

    public static void update() {
        currentXOffset += (trueXOffset - currentXOffset) * Options.smoothness();
        currentYOffset += (trueYOffset - currentYOffset) * Options.smoothness();

        int up = (TooltipScrollKeybinds.moveUp).getKeyCode();
        int down = (TooltipScrollKeybinds.moveDown).getKeyCode();
        int horizontal = (TooltipScrollKeybinds.horizontal).getKeyCode();
        int reset = (TooltipScrollKeybinds.reset).getKeyCode();

        if (Options.useWASD()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                ScrollTracker.scrollUp(Options.keyboardScrollSpeed());
            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                ScrollTracker.scrollDown(Options.keyboardScrollSpeed());
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                ScrollTracker.scrollLeft(Options.keyboardScrollSpeed());
            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                ScrollTracker.scrollRight(Options.keyboardScrollSpeed());
            }
        }

        if (Keyboard.isKeyDown(up)) {
            if ((Keyboard.isKeyDown(horizontal)) || (Options.useLShift() && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))) {
                ScrollTracker.scrollLeft(Options.keyboardScrollSpeed());
            }
            else {
                ScrollTracker.scrollUp(Options.keyboardScrollSpeed());
            }
        }
        else if (Keyboard.isKeyDown(down)) {
            if ((Keyboard.isKeyDown(horizontal)) || (Options.useLShift() && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))) {
                ScrollTracker.scrollRight(Options.keyboardScrollSpeed());
            }
            else {
                ScrollTracker.scrollDown(Options.keyboardScrollSpeed());
            }
        }
        else if (Keyboard.isKeyDown(reset)) {
            ScrollTracker.reset();
        }
    }

    public static int getXOffset() {
        MutableDouble convenientInjectionPoint = new MutableDouble(currentXOffset); // Other mods can inject here and manipulate this object for compatibility.
        return (int) Math.floor(convenientInjectionPoint.doubleValue());
    }

    public static int getYOffset() {
        MutableDouble convenientInjectionPoint = new MutableDouble(currentYOffset); // Other mods can inject here and manipulate this object for compatibility.
        return (int) Math.floor(convenientInjectionPoint.doubleValue());
    }

    public static void setInitialYOffset(int offset) {
        trueYOffset += offset;
        currentYOffset = trueYOffset;
    }

    public static void scrollUp() {
        scrollUp(Options.scrollSpeed());
        moved = true;
    }

    public static void scrollUp(int amount) {
        if (!isLocked()) trueYOffset -= amount;
        moved = true;
    }

    public static void scrollDown() {
        scrollDown(Options.scrollSpeed());
        moved = true;
    }

    public static void scrollDown(int amount) {
        if (!isLocked()) trueYOffset += amount;
        moved = true;
    }

    public static void scrollLeft() {
        scrollLeft(Options.scrollSpeed());
        moved = true;
    }

    public static void scrollLeft(int amount) {
        if (!isLocked()) trueXOffset -= amount;
        moved = true;
    }

    public static void scrollRight() {
        scrollRight(Options.scrollSpeed());
        moved = true;
    }

    public static void scrollRight(int amount) {
        if (!isLocked()) trueXOffset += amount;
        moved = true;
    }

    private static void resetScroll() {
        currentXOffset = 0;
        currentYOffset = 0;
        trueXOffset = 0;
        trueYOffset = 0;
        moved = false;
    }

    private static boolean isEqual(List<String> item1, List<String> item2) {
        if (item1 == null || item2 == null || item1.size() != item2.size()) return false;

        for (int i = 0; i < item1.size(); ++i) {
            String text1 = item1.get(i);
            String text2 = item2.get(i);
            if (text1 == null || text2 == null) {
                if (text1 != text2) return false;
                continue;
            }
            if (!text1.equals(text2)) return false;
        }
        return true;
    }

    private static boolean isLocked() {
        long difference = System.currentTimeMillis() - unlockTime;
        return difference > RELOCK_AT;
    }

    // Reset the tracker to default values.
    public static void reset() {
        resetScroll();
        currentItem = null; // Using null instead of just clearing the list because not all of Minecraft's Text Lists can be cleared for some reason and that can cause an error.
    }

    public static void setItem(List<String> item) {
        if (!isEqual(currentItem, item)) {
            resetScroll();
            currentItem = item;
        }
    }

    public static void unlock() {
        if (Options.resetOnUnlock() && isLocked()) {
            resetScroll();
        }
        unlockTime = System.currentTimeMillis();
    }

    public static void consumeWheel() {
        wheelConsumed = true;
    }

    public static void handleScreenMouse(Runnable handleMouse) {
        wheelConsumed = false;
        handleMouse.run();

        int wheel = Mouse.getEventDWheel();
        if (wheel == 0 || wheelConsumed || !Options.canScroll()) return;

        int horizontal = TooltipScrollKeybinds.horizontal.getKeyCode();
        if (Keyboard.isKeyDown(horizontal) || (Options.useLShift() && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))) {
            if (wheel > 0 != Options.invertXScroll()) scrollLeft();
            else scrollRight();
        }
        else {
            if (wheel > 0 != Options.invertYScroll()) scrollUp();
            else scrollDown();
        }
    }

    public static boolean hasMoved() {
        return moved;
    }
}