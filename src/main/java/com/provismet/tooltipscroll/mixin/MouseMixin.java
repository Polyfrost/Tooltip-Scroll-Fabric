package com.provismet.tooltipscroll.mixin;

import com.provismet.tooltipscroll.Options;
import com.provismet.tooltipscroll.ScrollTracker;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "next", at = @At("RETURN"))
    private static void onMouseEvent(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && Mouse.getEventDWheel() != 0) {
            if (!Options.canScroll) return;
            int wheel = Mouse.getEventDWheel();
            if (Options.useLShift && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) scrollX(wheel);
            else scrollY(wheel);
        }
    }

    @Unique
    private static void scrollX(double vertical) {
        if (Options.invertXScroll) {
            if (vertical > 0) ScrollTracker.scrollRight();
            else if (vertical < 0) ScrollTracker.scrollLeft();
        }
        else {
            if (vertical > 0) ScrollTracker.scrollLeft();
            else if (vertical < 0) ScrollTracker.scrollRight();
        }
    }

    @Unique
    private static void scrollY(double vertical) {
        if (Options.invertYScroll) {
            if (vertical > 0) ScrollTracker.scrollDown();
            else if (vertical < 0) ScrollTracker.scrollUp();
        }
        else {
            if (vertical > 0) ScrollTracker.scrollUp();
            else if (vertical < 0) ScrollTracker.scrollDown();
        }
    }
}
